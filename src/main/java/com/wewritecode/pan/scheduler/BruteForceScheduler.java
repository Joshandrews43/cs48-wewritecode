/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.*;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BruteForceScheduler implements Scheduler {
    private List<Course> mandatoryCourses;
    private List<Course> optionalCourses;
    private List<Filter> filterOptions;
    private List<Schedule> fullSchedules;

    public BruteForceScheduler() {
        mandatoryCourses = new ArrayList<>();
        optionalCourses = new ArrayList<>();
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
        parseRequest(request);
        findViableSchedules(mandatoryCourses.size() - 1, new Schedule());
        sort();
        return createResponse();
    }

    private void findViableSchedules(int index, Schedule s) {
        Schedule copy = new Schedule(s);
        if (index < 0) {
            fullSchedules.add(copy);
            return;
        }
        Course c = mandatoryCourses.get(index);
        for (Lecture lecture : c.getLectures()) {
            for (Section section : lecture.getSections()) {
                Course finalCourse = new Course(c, lecture, section);
                try {
                    if (!isConflicting(copy, finalCourse)) {
                        copy.addToSchedule(finalCourse);
                        int fullScheduleSize = fullSchedules.size();
                        findViableSchedules(index - 1, copy);
                        if (fullScheduleSize != fullSchedules.size()) {
                            copy.removeFromSchedule(finalCourse);
                        }
                    }
                } catch (InvalidScheduleException e) {
                    System.out.println("Invalid Schedule");
                }
            }
        }
    }

    private void parseRequest(ScheduleRequest request) {
        mandatoryCourses = request.getMandatory();
        optionalCourses = request.getOptional();
        filterOptions = request.getFilters();
    }

    private ScheduleResponse createResponse() {
        ScheduleResponse response = new ScheduleResponse();
        response.setSchedules(fullSchedules);
        response.setFilters(filterOptions);
        return response;
    }

    // TODO: Switch to private
    public void addToRemainingCourses(Course course) { mandatoryCourses.add(course); }

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

    private boolean isConflicting(Schedule schedule, Lecture lecture) throws InvalidScheduleException {
        for (int i = 0; i < schedule.getCourses().size(); i++) {
            if (schedule.getCourses().get(i).getNumLectures() > 1) {
                throw new InvalidScheduleException();
            } else if (schedule.getCourses().get(i).getLecture(0).getNumSections() > 1) {
                throw new InvalidScheduleException();
            } else if (isConflicting(schedule.getCourses().get(i).getLecture(0), lecture)) {
                return true;
            } else if (isConflicting(schedule.getCourses().get(i).getLecture(0).getSection(0), lecture)) {
                return true;
            }
        }
        return false;
    }

    private boolean isConflicting(Schedule schedule, Section section) throws InvalidScheduleException {
        for (int i = 0; i < schedule.getCourses().size(); i++) {
            if (schedule.getCourses().get(i).getNumLectures() > 1) {
                throw new InvalidScheduleException();
            } else if (schedule.getCourses().get(i).getLecture(0).getNumSections() > 1) {
                throw new InvalidScheduleException();
            } else if (isConflicting(schedule.getCourses().get(i).getLecture(0), section)) {
                return true;
            } else if (isConflicting(schedule.getCourses().get(i).getLecture(0).getSection(0), section)) {
                return true;
            }
        }
        return false;
    }

    private boolean isConflicting(Lecture first, Lecture second) {
        boolean sameDay = false;
        for (int i = 0; i < first.getDays().size(); i++) {
            for (int j = 0; j < second.getDays().size(); j++) {
                if (first.getDay(i).equals(second.getDay(j))) {
                    sameDay = true;
                }
            }
        }
        if (!sameDay) {
            return false;
        }
        return ((first.getStart().compareTo(second.getEnd()) < 0) && (first.getStart().compareTo(second.getStart()) >= 0)
                || (second.getStart().compareTo(first.getEnd()) < 0) && (second.getStart().compareTo(first.getStart()) >= 0));
    }

    private boolean isConflicting(Section first, Section second) {
        boolean sameDay = false;
        for (int i = 0; i < first.getDays().size(); i++) {
            for (int j = 0; j < second.getDays().size(); j++) {
                if (first.getDay(i).equals(second.getDay(j))) {
                    sameDay = true;
                }
            }
        }
        if (!sameDay) {
            return false;
        }
        return ((first.getStart().compareTo(second.getEnd()) < 0) && (first.getStart().compareTo(second.getStart()) >= 0)
                || (second.getStart().compareTo(first.getEnd()) < 0) && (second.getStart().compareTo(first.getStart()) >= 0));
    }

    private boolean isConflicting(Lecture first, Section second) {
        boolean sameDay = false;
        for (int i = 0; i < first.getDays().size(); i++) {
            for (int j = 0; j < second.getDays().size(); j++) {
                if (first.getDay(i).equals(second.getDay(j))) {
                    sameDay = true;
                }
            }
        }
        if (!sameDay) {
            return false;
        }
        return ((first.getStart().compareTo(second.getEnd()) < 0) && (first.getStart().compareTo(second.getStart()) >= 0)
                || (second.getStart().compareTo(first.getEnd()) < 0) && (second.getStart().compareTo(first.getStart()) >= 0));
    }

    private boolean isConflicting(Section first, Lecture second) {
        boolean sameDay = false;
        for (int i = 0; i < first.getDays().size(); i++) {
            for (int j = 0; j < second.getDays().size(); j++) {
                if (first.getDay(i).equals(second.getDay(j))) {
                    sameDay = true;
                }
            }
        }
        if (!sameDay) {
            return false;
        }
        return ((first.getStart().compareTo(second.getEnd()) < 0) && (first.getStart().compareTo(second.getStart()) >= 0)
                || (second.getStart().compareTo(first.getEnd()) < 0) && (second.getStart().compareTo(first.getStart()) >= 0));
    }

    public List<Schedule> getFullSchedules() {
        return fullSchedules;
    }
}
