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
    private HashMap<String, Integer> dayDifference;
    private ArrayList<Double> dayFitness;

    public GapsFilter() {
        sortedSessions = new HashMap<>();
        dayDifference = new HashMap<>();
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
        dayDifference = new HashMap<>();
        dayFitness = new ArrayList<>();
        sortedSessions.put("M", new ArrayList<>());
        sortedSessions.put("T", new ArrayList<>());
        sortedSessions.put("W", new ArrayList<>());
        sortedSessions.put("R", new ArrayList<>());
        sortedSessions.put("F", new ArrayList<>());
        // Arrange classes in arraylist of arraylist of sessions based on day
        populateArray((ArrayList<Course>)o.getCourses());

        for (String day : sortedSessions.keySet()) {
            calculateDifferenceForDay(day);
        }

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
//            calculateDifferenceForDay(day);
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

//    private void calculateDifferenceForDay(String day) {
//        int size = sortedSessions.get(day).size();
//        ArrayList<Session> sessions = sortedSessions.get(day);
//        if (size == 1) {
////            dayDifference.put(day, 0.0);
//            return;
//        } else if (size == 2) {
//            dayDifference.put(day, (double)calculateDifference(sessions.get(0), sessions.get(1)));
//            return;
//        } else {
//
//            for(int i = 0; i < sessions.size() - 1; i++) {
//
//                dayDifference.put(day, avg(size, dayDifference.get(day) * (size - 1),
//                        calculateDifference(sessions.get(i), sessions.get(i+1))));
//
//
//            }
//        }
//    }
    private void calculateDifferenceForDay(String day) {
        int size = sortedSessions.get(day).size();
        ArrayList<Session> sessions = sortedSessions.get(day);
        int difference = 0;
        if (size > 1) {
            for (int i = 0; i < sessions.size() - 1; i++) {
                difference += calculateDifference(sessions.get(i), sessions.get(i+1));
            }
            dayDifference.put(day, difference);
        }
    }

    private int calculateDifference(Session s1, Session s2) {
        int difference;
        if (s1.getTime().getStart().compareTo(s2.getTime().getStart()) < 1) {
            difference = s2.getTime().getStart().compareTo(s1.getTime().getEnd());
        } else {
            difference = s1.getTime().getStart().compareTo(s2.getTime().getEnd());
        }
        difference = round(difference);
        return difference;
    }
    private double avg(int size, double totalDifference, double newDifference) {
        return (totalDifference + newDifference) / size;
    }

    // Rounds to nearest 60
    private int round(int n) {
        // Smaller multiple
        int a = (n / 60) * 60;

        // Larger multiple
        int b = a + 60;

        // Return of closest of two
        return (n - a > b - n)? b : a;
    }

//    private double applyOption() {
//        double averageDifference = 0.0;
//        for (Map.Entry mapElement : dayDifference.entrySet()) {
//            averageDifference += (double)mapElement.getValue();
//        }
//        averageDifference /= dayDifference.size();
//        switch(option) {
//            case OPTION_MINIMIZE:
//                return (TIME_RANGE - averageDifference) / TIME_RANGE;
//            case OPTION_MAXIMIZE:
//                return averageDifference / TIME_RANGE;
//            default:
//                try {
//                    int hour = Integer.parseInt("" + option.charAt(0));
//                    int minutes = 60 * hour;
//                    calculateFitnessForDays(minutes);
//                    double fitness = 0.0;
//                    for (Double dayFitness : dayFitness) {
//                        fitness += dayFitness;
//                    }
//                    return fitness / dayFitness.size();
//                } catch (NumberFormatException e) {
//                    return 0.0;
//                }
//
//        }
//    }
    private double applyOption() {
        double averageDifference = 0.0;
        ArrayList<Double> averageDayDifference = new ArrayList<>();
        for (String day : dayDifference.keySet()) {
            averageDayDifference.add(((double) dayDifference.get(day)) / (sortedSessions.get(day).size()-1));
        }
        for (Double difference : averageDayDifference) {
            averageDifference += difference;
        }
        averageDifference /= averageDayDifference.size();

        switch(option) {
            case OPTION_MINIMIZE:
                return (TIME_RANGE - averageDifference) / TIME_RANGE;
            case OPTION_MAXIMIZE:
                return averageDifference / TIME_RANGE;
            default:
                try {
                    int hour = Integer.parseInt("" + option.charAt(0));
                    int minutes = 60 * hour;
                    calculateFitnessForDays(minutes, averageDayDifference);
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



    private void calculateFitnessForDays(double minutes, ArrayList<Double> averageDayDifference) {
        double fitness;
        for (double difference : averageDayDifference) {
            fitness = Math.abs(difference - minutes);
            fitness = (TIME_RANGE - fitness) / TIME_RANGE;
            dayFitness.add(fitness);
        }
    }
}
