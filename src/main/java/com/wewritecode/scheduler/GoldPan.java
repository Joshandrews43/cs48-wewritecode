/**
 * @author Grant Clark
 */

package com.wewritecode.scheduler;

import com.wewritecode.scheduler.schedule.Course;
import com.wewritecode.scheduler.schedule.Schedule;

import java.util.*;

public class GoldPan {

    // TODO: Should we distinguish between a selected Course and a Course with multiple lectures/sections?
    private Schedule baseSchedule;
    private List<Course> remainingCourses;
    private Map<String, String> filterOptions;
    private List<Schedule> fullSchedules;

    private GoldPan() {
        baseSchedule = new Schedule();
        remainingCourses = new ArrayList<>();
        filterOptions = new HashMap<>();
        fullSchedules = new ArrayList<>();
    }

    private static class GoldPanHolder {
        private static final GoldPan INSTANCE = new GoldPan();
    }

    public static GoldPan getInstance() {
        return GoldPanHolder.INSTANCE;
    }

    // Modifiers

    public Schedule getBaseSchedule() { return baseSchedule; }
    public void addToBaseSchedule() {} // TODO: Implement and give appropriate parameters

    public List<Course> getRemainingCourses() { return remainingCourses; }
    public void addToRemainingCourses(Course course) { remainingCourses.add(course); }

    public Map<String, String> getFilterOptions() { return filterOptions; }
    public void updateFilterOptions(Map<String, String> options) {} // TODO: Implement

    // Core Methods

    /**
     * Generates a list of non-conflicting schedules.
     *
     * @return list of schedules that do not conflict.
     */
    public List<Schedule> generate() {
        // TODO: Implement
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
