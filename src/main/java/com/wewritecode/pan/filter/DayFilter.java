/**
 * @author Grant Clark
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.pan.schedule.Lecture;
import com.wewritecode.pan.schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("Day")
public class DayFilter implements Filter<Schedule> {

    // Two ways I could see this going. Either user selects a certain day they don't want class or they
    // want the schedule that has classes on the least number of days. I will implement the latter

    // If schedule has classes all 5 days, fitness is 0
    // If schedule has classes on one day, fitness is 1
    // Therefore, if schedule has class on k days, fitness is (5-k)/4

    // Precondition: schedule passed in has only 1 lecture and 1 section per course
    // Postcondition: returns fitness for that course

    private List<String> days;

    public DayFilter() {
        days = new ArrayList<>();
    }

    @Override
    public double getFitness(Schedule s) {
        for (int i = 0; i < s.getCourses().size(); i++) {
            Lecture courseLecture = s.getCourses().get(i).getLecture(0);
            for (int j = 0; j < courseLecture.getDays().size(); j++) {
                String day = s.getCourses().get(i).getLecture(0).getDay(j);
                if (!days.contains(day)) {
                    days.add(day);
                }
            }
            for (int j = 0; j < courseLecture.getSection(0).getDays().size(); j++) {
                String day = s.getCourses().get(i).getLecture(0).getSection(0).getDay(j);
                if (!days.contains(day)) {
                    days.add(day);
                }
            }

        }
        return ((double)(5 - days.size())/ 4);
    }
}
