/**
 * @author Alan Roddick
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.common.schedule.Course;
import com.wewritecode.common.schedule.Schedule;
import com.wewritecode.common.schedule.Session;

import java.util.*;


/**
 * Filter Schedules by Gaps
 *
 * User selects to minimize or maximize gaps
 *
 */
@JsonTypeName("Gaps")
public class GapsFilter extends AbstractScheduleFilter {

    public static final String OPTION_MINIMIZE = "Minimize";
    public static final String OPTION_MAXIMIZE = "Maximize";
    private static final double TIME_RANGE = 610;
    private static final String[] OPTIONS = {OPTION_MINIMIZE, OPTION_MAXIMIZE, "1 Hour", "2 Hours", "3 Hours",
    "4 Hours",};
    private HashMap<String, ArrayList<Session>> sortedSessions;
    private HashMap<String, Double> avgDayDifference;
    private ArrayList<Double> dayFitness;

    public GapsFilter() {
        sortedSessions = new HashMap<>();
        avgDayDifference = new HashMap<>();
        dayFitness = new ArrayList<>();
        sortedSessions.put("M", new ArrayList<>());
        sortedSessions.put("T", new ArrayList<>());
        sortedSessions.put("W", new ArrayList<>());
        sortedSessions.put("R", new ArrayList<>());
        sortedSessions.put("F", new ArrayList<>());
    }

    @Override
    public String[] getOptions() { return OPTIONS; }

    @Override
    public String getType() { return "Gaps"; }

    @Override
    public double getFitness(Schedule o) {

        sortedSessions = new HashMap<>();
        avgDayDifference = new HashMap<>();
        dayFitness = new ArrayList<>();
        sortedSessions.put("M", new ArrayList<>());
        sortedSessions.put("T", new ArrayList<>());
        sortedSessions.put("W", new ArrayList<>());
        sortedSessions.put("R", new ArrayList<>());
        sortedSessions.put("F", new ArrayList<>());
        // Arrange classes in arraylist of arraylist of sessions based on day
        populateArray((ArrayList<Course>)o.getCourses());

        return applyOption();

    }

    private void populateArray(ArrayList<Course> courses) {
        for (Course course : courses) {
            addByDay(course.getLecture(0));
            if (course.getLecture(0).getNumSections() != 0) {
                addByDay(course.getLecture(0).getSection(0));
            }
        }
    }

    private void addByDay(Session session) {
        for (String day : session.getDays()) {
            insertSession(sortedSessions.get(day), session);
            calculateDifferenceForDay(day);
        }
    }

    private void insertSession(ArrayList<Session> sessions, Session newSession) {
        for (int i = 0; i < sessions.size(); i++) {
            Session session = sessions.get(i);
            if (session.getTime().getStart().compareTo(newSession.getTime().getStart()) > 0) {
                sessions.add(i, newSession);
                return;
            }
        }
        sessions.add(newSession);
    }

    private void calculateDifferenceForDay(String day) {
        int size = sortedSessions.get(day).size();
        ArrayList<Session> sessions = sortedSessions.get(day);
        if (size == 1) {
//            avgDayDifference.put(day, 0.0);
            return;
        } else if (size == 2) {
            avgDayDifference.put(day, (double)calculateDifference(sessions.get(0), sessions.get(1)));
            return;
        } else {

            for(int i = 0; i < sessions.size() - 1; i++) {

                avgDayDifference.put(day, avg(size, avgDayDifference.get(day) * (size - 1),
                        calculateDifference(sessions.get(i), sessions.get(i+1))));


            }
        }
    }

    private int calculateDifference(Session s1, Session s2) {
        int difference;
        if (s1.getTime().getStart().compareTo(s2.getTime().getStart()) < 1) {
            difference = s2.getTime().getStart().compareTo(s1.getTime().getEnd());
        } else {
            difference = s1.getTime().getStart().compareTo(s2.getTime().getEnd());
        }
        return difference;
    }
    private double avg(int size, double totalDifference, double newDifference) {
        return (totalDifference + newDifference) / size;
    }

    private double applyOption() {
        double averageDifference = 0.0;
        for (Map.Entry mapElement : avgDayDifference.entrySet()) {
            averageDifference += (double)mapElement.getValue();
        }
        averageDifference /= avgDayDifference.size();
        switch(option) {
            case OPTION_MINIMIZE:
                return (TIME_RANGE - averageDifference) / TIME_RANGE;
            case OPTION_MAXIMIZE:
                return averageDifference / TIME_RANGE;
            default:
                try {
                    int hour = Integer.parseInt("" + option.charAt(0));
                    int minutes = 60 * hour;
                    calculateFitnessForDays(minutes);
                    double fitness = 0.0;
                    for (Double dayFitness : dayFitness) {
                        fitness += dayFitness;
                    }
                    return fitness / dayFitness.size();
                } catch (NumberFormatException e) {
                    return 0.0;
                }

        }
    }

    private void calculateFitnessForDays(double minutes) {
        double fitness;
        for (Map.Entry mapElement : avgDayDifference.entrySet()) {
            fitness = Math.abs((double)mapElement.getValue() - minutes);
            fitness = (TIME_RANGE - fitness) / TIME_RANGE;
            dayFitness.add(fitness);
        }
    }
}
