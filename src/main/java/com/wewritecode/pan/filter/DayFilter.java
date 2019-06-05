/**
 * @author Alan Roddick
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;

import com.wewritecode.common.schedule.Lecture;
import com.wewritecode.common.schedule.Schedule;
import com.wewritecode.common.schedule.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter Schedules by Day
 *
 * User selects to minimize or maximize days
 *
 */
@JsonTypeName("Day")
public class DayFilter extends AbstractScheduleFilter {


    // If schedule has classes all 5 days, fitness is 0
    // If schedule has classes on one day, fitness is 1
    // Therefore, if schedule has class on k days, fitness is (5-k)/4

    // Precondition: schedule passed in has only 1 lecture and 1 section per course
    // Postcondition: returns fitness for that schedule

    public static final String OPTION_MINIMIZE = "Minimize";
    public static final String OPTION_MAXIMIZE = "Maximize";
    private static final String[] OPTIONS = {OPTION_MINIMIZE, OPTION_MAXIMIZE};

    private List<String> days;

    public DayFilter() {
        days = new ArrayList<>();
    }

    @Override
    public String[] getOptions() { return OPTIONS; }

    @Override
    public String getType() { return "Day"; }

    @Override
    public double getFitness(Schedule s) throws InvalidFilterOptionException {
        days = new ArrayList<>();
        // Traverse through courses
        for (int i = 0; i < s.getCourses().size(); i++) {

            Lecture courseLecture = s.getCourses().get(i).getLecture(0);

            // Add all the days from lectures
            addDaysFromSession(courseLecture);

            // Add all the days from sections
            if (courseLecture.getNumSections() != 0) {
                addDaysFromSession(courseLecture.getSection(0));
            }

        }

        // Apply option based on number of days with class
        return applyOption(days.size());

    }

    private void addDaysFromSession(Session session) {

        for (String day : session.getDays()) {
            if (!days.contains(day)) {
                days.add(day);
            }
        }
    }

    private double applyOption(double size) throws InvalidFilterOptionException {

        switch (option) {
            case OPTION_MINIMIZE:
                return (5-size)/4;
            case OPTION_MAXIMIZE:
                return (size-1)/4;
            default:
                String message = String.format("Option: \"%s\" not supported for DayFilter.", option);
                throw new InvalidFilterOptionException(message);
        }
    }
}
