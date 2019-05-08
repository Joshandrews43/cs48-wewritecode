/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.Course;
import com.wewritecode.pan.schedule.ICourse;
import com.wewritecode.pan.schedule.ISchedule;
import com.wewritecode.pan.schedule.Schedule;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BruteForceScheduler implements Scheduler {
    private ISchedule baseSchedule;
    private List<ICourse> remainingCourses;
    private List<Filter> filterOptions;
    private List<ISchedule> fullSchedules;

    public BruteForceScheduler() {
        baseSchedule = new Schedule();
        remainingCourses = new ArrayList<>();
        filterOptions = new ArrayList<>();
        fullSchedules = new ArrayList<>();
    }

    // Core Methods

    /**
     * Generates a list of non-conflicting schedules.
     *
     * @return list of schedules that do not conflict.
     */
    @Override
    public ScheduleResponse generate(ScheduleRequest request) {
        splitRequest(request);
        findViableSchedules();
        sort();
        return new ScheduleResponse();
    }

    private void splitRequest(ScheduleRequest request) {

    }

    private void findViableSchedules() {
        // TODO: Implement
        // Brute-force algorithm for finding viable schedules.
        // Modifies fullSchedules by adding only viable combinations of baseSchedule and remainingCourses to the list.
    }

    private void addToBaseSchedule() {
        // TODO: Implement and give appropriate parameters
    }

    private void addToRemainingCourses(Course course) { remainingCourses.add(course); }

    // TODO: Check if needed / possible to just update filter options.
    private void updateFilterOptions(List<Filter> options) {
        filterOptions = options;
        sort();
    }

    private void sort() {
        // TODO: Implement
        // Assign fitness values to each schedule based on filter options.
        // Then, sort them by said fitness values.
        //Collections.sort(fullSchedules);
    }
}
