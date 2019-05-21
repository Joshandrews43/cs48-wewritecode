/**
 * @author Alan Roddick
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.pan.schedule.Course;
import com.wewritecode.pan.schedule.Schedule;
import com.wewritecode.pan.schedule.Session;

import java.util.*;


/**
 * Filter Schedules by Gaps
 *
 * User selects to minimize or maximize gaps
 *
 */
@JsonTypeName("Gaps")
public class GapsFilter extends AbstractScheduleFilter {

    public static final String OPTION_MINIMIZE = "Minimize Gaps";
    public static final String OPTION_MAXIMIZE = "Maximize Gaps";
    private static final double TIME_RANGE = 610;
    private static final String[] OPTIONS = {OPTION_MINIMIZE, OPTION_MAXIMIZE};
    private HashMap<String, ArrayList<Session>> sortedSessions;
    private double fitness;
    private HashMap<String, Double> avgDayDifference;

    public GapsFilter() {
        sortedSessions = new HashMap<>();
        avgDayDifference = new HashMap<>();
        sortedSessions.put("M", new ArrayList<>());
        sortedSessions.put("T", new ArrayList<>());
        sortedSessions.put("W", new ArrayList<>());
        sortedSessions.put("R", new ArrayList<>());
        sortedSessions.put("F", new ArrayList<>());
    }

    @Override
    public String[] getOptions() { return OPTIONS; }

    @Override
    public double getFitness(Schedule o) throws InvalidFilterOptionException {

        sortedSessions = new HashMap<>();
        avgDayDifference = new HashMap<>();
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
            addByDay(course.getLecture(0).getSection(0));
        }
    }

    private void addByDay(Session session) {
        for (String day : session.getDays()) {
            insertSession(sortedSessions.get(day), session);
            calculateFitnessForDay(day);
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

    private void calculateFitnessForDay(String day) {
        int size = sortedSessions.get(day).size();
        ArrayList<Session> sessions = sortedSessions.get(day);
        if (size == 1) {
            avgDayDifference.put(day, 0.0);
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
        }
        return fitness / avgDayDifference.size();
    }
}
