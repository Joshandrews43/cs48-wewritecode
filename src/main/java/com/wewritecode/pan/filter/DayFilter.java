/**
 * @author Alan Roddick
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.pan.schedule.Lecture;
import com.wewritecode.pan.schedule.Schedule;
import com.wewritecode.pan.schedule.Session;

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

    private static final String[] OPTIONS = {"Minimize Days", "Maximize Days"};

    private List<String> days;

    public DayFilter() {
        days = new ArrayList<>();
    }

    @Override
    public String[] getOptions() { return OPTIONS; }

    @Override
    public double getFitness(Schedule s) throws InvalidFilterOptionException {
        // Traverse through courses
        for (int i = 0; i < s.getCourses().size(); i++) {
            Lecture courseLecture = s.getCourses().get(i).getLecture(0);

            // Add all the days from lectures
            addDaysFromSession(courseLecture);

            // Add all the days from sections
            addDaysFromSession(courseLecture.getSection(0));

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
            case "Minimize Days":
                return (5-size)/4;
            case "Maximize Days":
                return (size-1)/4;
            default:
                String message = String.format("Option: \"%s\" not supported for DayFilter.", option);
                throw new InvalidFilterOptionException(message);
        }
    }
}
