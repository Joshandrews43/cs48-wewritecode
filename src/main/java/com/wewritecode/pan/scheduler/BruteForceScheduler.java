/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.pan.schedule.*;

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

    public void addToBaseSchedule(Course course) {
        baseSchedule.addToSchedule(course);
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
        findViableSchedules(remainingCourses.size() - 1, baseSchedule);
        sort();
        return fullSchedules;
    }

//    private void findViableSchedules() {
//        // TODO: Implement
//        // Brute-force algorithm for finding viable schedules.
//        // Modifies fullSchedules by adding only viable combinations of baseSchedule and remainingCourses to the list.
//        if (remainingCourses.size() == 0) {
//            fullSchedules.add(baseSchedule);
//            return;
//        }
//        Schedule s = new Schedule(baseSchedule);
//        boolean exhaustedResources = false;
//        boolean createdSchedule = false;
//        while (!exhaustedResources) {
//            for (int i = 0; i < remainingCourses.size(); i++) {
//                for (int j = 0; j < remainingCourses.get(i).getNumLectures(); i++) {
//                    if (!isConflicting(s, remainingCourses.get(i).getLecture(j))) {
//                        for (int k = 0; k < remainingCourses.get(i).getLecture(j).getNumSections(); k++) {
//                            if (!isConflicting(s, remainingCourses.get(i).getLecture(j).getSection(k))) {
//                                Course finalCourse = new Course(remainingCourses.get(i), j, k);
//                                s.addToSchedule(finalCourse);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    private void findViableSchedules(int index, Schedule s) {
        Schedule copy = new Schedule(s);
        if (index < 0) {
            fullSchedules.add(copy);
            return;
        }
        Course c = remainingCourses.get(index);
        for (Lecture lecture : c.getLectures()) {
            for (Section section : lecture.getSections()) {
                Course finalCourse = new Course(c, lecture, section);
                try {
                    if (!isConflicting(copy, finalCourse)) {
                        copy.addToSchedule(finalCourse);
                        findViableSchedules(index - 1, copy);
                    }
                } catch (InvalidScheduleException e) {
                    System.out.println("Invalid Schedule");
                }
            }
        }
    }

    private void sort() {
        // TODO: Implement
        // Assign fitness values to each schedule based on filter options.
        // Then, sort them by said fitness values.
        Collections.sort(fullSchedules);
    }

    private boolean isConflicting(Schedule schedule, Course course) throws InvalidScheduleException {
        if (course.getNumLectures() > 1 || course.getLecture(0).getNumSections() > 1) {
            throw new InvalidScheduleException();
        }
        if (isConflicting(schedule, course.getLecture(0))) {
            return true;
        } else if (isConflicting(schedule, course.getLecture(0).getSection(0))) {
            return true;
        }
        return false;
    }

    private boolean isConflicting(Schedule schedule, Session session) throws InvalidScheduleException {
        for (int i = 0; i < schedule.getCourses().size(); i++) {
            if (schedule.getCourses().get(i).getNumLectures() > 1) {
                throw new InvalidScheduleException();
            } else if (schedule.getCourses().get(i).getLecture(0).getNumSections() > 1) {
                throw new InvalidScheduleException();
            } else if (isConflicting(schedule.getCourses().get(i).getLecture(0), session)) {
                return true;
            } else if (isConflicting(schedule.getCourses().get(i).getLecture(0).getSection(0), session)) {
                return true;
            }
        }
        return false;

    }
    private boolean isConflicting(Session first, Session second) {
        return ((first.getStart().compareTo(second.getEnd()) < 0) && (first.getStart().compareTo(second.getStart()) > 0)
                || (second.getStart().compareTo(first.getEnd()) < 0) && (second.getStart().compareTo(first.getStart()) > 0));
    }
}
