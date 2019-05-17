/**
 * @author Alan Roddick
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.pan.schedule.Course;
import com.wewritecode.pan.schedule.Schedule;
import com.wewritecode.pan.schedule.Session;
import com.wewritecode.pan.schedule.Time;

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
    private HashMap<String, Double> dayFitness;

    public GapsFilter() {
        sortedSessions = new HashMap<>();
        dayFitness = new HashMap<>();
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
        if (sortedSessions.get(day).size() == 1) {
            dayFitness.put(day, 1.0);
        } else {
            ArrayList<Session> sessions = sortedSessions.get(day);
            for(int i = 0; i < sessions.size() - 1; i++) {
                if (dayFitness.get(day) == 1) {
                    dayFitness.put(day, calculateDifference(sessions.get(i), sessions.get(i+1)));
                } else {
                    int size = sortedSessions.get(day).size();
                    dayFitness.put(day, avg(size, dayFitness.get(day) * (size - 1),
                            calculateDifference(sessions.get(i), sessions.get(i+1))));
                }

            }
        }
    }

    private double calculateDifference(Session s1, Session s2) {
        double difference;
        if (s1.getTime().getStart().compareTo(s2.getTime().getStart()) < 1) {
            difference = s2.getTime().getStart().compareTo(s1.getTime().getEnd());
        } else {
            difference = s1.getTime().getStart().compareTo(s2.getTime().getEnd());
        }
        return 1 - (difference / TIME_RANGE);
    }
    private double avg(int size, double dayFitness, double newFitness) {
        return (dayFitness + newFitness) / size;
    }

    private double applyOption() {
        double fitness = 0;
        for (Map.Entry mapElement : dayFitness.entrySet()) {
            fitness += (double)mapElement.getValue();
        }
        return fitness / dayFitness.size();
    }
}
