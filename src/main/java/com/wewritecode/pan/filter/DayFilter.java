/**
 * @author Alan Roddick
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.pan.schedule.Lecture;
import com.wewritecode.pan.schedule.Schedule;

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
    public double getFitness(Schedule s) {
        // Add all the days from lectures
        for (int i = 0; i < s.getCourses().size(); i++) {
            Lecture courseLecture = s.getCourses().get(i).getLecture(0);
            for (int j = 0; j < courseLecture.getDays().size(); j++) {
                String day = s.getCourses().get(i).getLecture(0).getDay(j);
                if (!days.contains(day)) {
                    days.add(day);
                }
            }
            // Add all the days from sections
            for (int j = 0; j < courseLecture.getSection(0).getDays().size(); j++) {
                String day = s.getCourses().get(i).getLecture(0).getSection(0).getDay(j);
                if (!days.contains(day)) {
                    days.add(day);
                }
            }

        }
        // Compute fitness from number of days of section and lecture
        return ((double)(5 - days.size())/ 4);
    }
}
