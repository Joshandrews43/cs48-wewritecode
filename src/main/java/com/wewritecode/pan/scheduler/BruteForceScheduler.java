/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.pan.schedule.Course;
import com.wewritecode.pan.schedule.Schedule;

import java.util.*;

public class BruteForceScheduler implements Scheduler {

    // TODO: Should we distinguish between a selected Course and a Course with multiple lectures/sections?
    private Schedule baseSchedule;
    private List<Course> remainingCourses;
    private Map<String, String> filterOptions;
    private List<Schedule> fullSchedules;

    private BruteForceScheduler() {
        baseSchedule = new Schedule();
        remainingCourses = new ArrayList<>();
        filterOptions = new HashMap<>();
        fullSchedules = new ArrayList<>();
    }

    private static class BruteForceSchedulerHolder {
        private static final BruteForceScheduler INSTANCE = new BruteForceScheduler();
    }

    public static BruteForceScheduler getInstance() {
        return BruteForceSchedulerHolder.INSTANCE;
    }

    // Modifiers

    public void addToBaseSchedule() {
        // TODO: Implement and give appropriate parameters
    }

    public void addToRemainingCourses(Course course) { remainingCourses.add(course); }

    public void updateFilterOptions(Map<String, String> options) {
        filterOptions = options;
        sort();
    }

    // Core Methods

    @Override
    /**
     * Generates a list of non-conflicting schedules.
     *
     * @return list of schedules that do not conflict.
     */
    public List<Schedule> generate() {
        findViableSchedules();
        sort();
        return fullSchedules;
    }

    private void findViableSchedules() {
        // TODO: Implement
        // Brute-force algorithm for finding viable schedules.
        // Modifies fullSchedules by adding only viable combinations of baseSchedule and remainingCourses to the list.
    }

    private void sort() {
        // TODO: Implement
        // Assign fitness values to each schedule based on filter options.
        // Then, sort them by said fitness values.
        Collections.sort(fullSchedules);
    }
}
