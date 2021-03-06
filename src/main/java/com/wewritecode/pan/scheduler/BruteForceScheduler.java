/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.google.common.collect.Sets;
import com.wewritecode.pan.filter.Filter;
import com.wewritecode.common.schedule.*;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BruteForceScheduler implements Scheduler {
    private List<Course> mandatoryCourses;
    private List<Course> optionalCourses;
    private Set<Filter> filterOptions;
    private List<Schedule> fullSchedules;

    public BruteForceScheduler() {
        mandatoryCourses = new ArrayList<>();
        optionalCourses = new ArrayList<>();
        filterOptions = new HashSet<>();
        fullSchedules = new ArrayList<>();
    }

    // Public Core Method(s)

    /**
     * Generates a list of non-conflicting schedules.
     * @return list of schedules that do not conflict.
     */
    @Override
    public ScheduleResponse generate(ScheduleRequest request) {
        mandatoryCourses = new ArrayList<>();
        optionalCourses = new ArrayList<>();
        filterOptions = new HashSet<>();
        fullSchedules = new ArrayList<>();
        parseRequest(request);
        findFullSchedules();
        return createResponse();
    }

    @Override
    public void applyFilters(Set<Filter> filters) {
        filterOptions = (filters != null) ? filters : new HashSet<>();
        BruteForceFilterer.sort(fullSchedules, filterOptions);
    }

    @Override
    public ScheduleResponse createResponse() {
        ScheduleResponse response = new ScheduleResponse();
        response.setSchedules(fullSchedules);
        response.setFilters(filterOptions);
        return response;
    }

    @Override
    public ScheduleResponse createResponse(int scheduleIndex) {
        ScheduleResponse response = new ScheduleResponse();
        response.addSchedule(fullSchedules.get(scheduleIndex));
        response.setFilters(filterOptions);
        return response;
    }

    @Override
    public ScheduleResponse createResponse(int start, int end) {
        if (fullSchedules.size() == 0) {
            return new ScheduleResponse();
        }
        ScheduleResponse response = new ScheduleResponse();
        List<Schedule> subList;
        if (fullSchedules.size() > end)
            subList = fullSchedules.subList(start, end);
        else if (fullSchedules.size() == 1) {
            subList = new ArrayList<>();
            subList.add(fullSchedules.get(0));
        } else
            subList = fullSchedules.subList(start, fullSchedules.size() - 1);
        response.setSchedules(subList);
        response.setFilters(filterOptions);
        return response;
    }

    // Private Request/Response Methods

    private void parseRequest(ScheduleRequest request) {
        List<Course> mandatory = request.getMandatory();
        List<Course> optional = request.getOptional();

        mandatoryCourses = (mandatory != null) ? mandatory : new ArrayList<>();
        optionalCourses = (optional != null) ? optional : new ArrayList<>();
    }

    // Private Schedule Conflict Resolution Method(s)

    private void findFullSchedules() {
        findMandatorySchedules(mandatoryCourses.size() - 1, new Schedule());
        addOptionalCourses(optionalCourses.size());
    }

    private void findViableSchedules(List<Schedule> schedules, int index, Schedule s, Set<Course> courses) {
        findViableSchedules(schedules, index, s, new ArrayList<>(courses));
    }

    private void findViableSchedules(List<Schedule> schedules, int index, Schedule s, List<Course> courses) {
        Schedule scheduleCopy = new Schedule(s);
        if (index < 0) {
            schedules.add(scheduleCopy);
            return;
        }
        Course c = courses.get(index);
        for (Lecture lecture : c.getLectures()) {
            if (lecture.getNumSections() == 0) {
                Course finalCourse = new Course(c, lecture, null);
                tryToAddCourseToSchedule(schedules, index, scheduleCopy, courses, finalCourse);
            } else {
                for (Section section : lecture.getSections()) {
                    Course finalCourse = new Course(c, lecture, section);
                    tryToAddCourseToSchedule(schedules, index, scheduleCopy, courses, finalCourse);
                }
            }
        }
    }

    private void tryToAddCourseToSchedule(List<Schedule> schedules, int index,
                                          Schedule schedule, List<Course> courses, Course course) {
        try {
            if (!isConflicting(schedule, course.getLecture(0))) {
                if (course.getLecture(0).getSections().size() > 0
                        && isConflicting(schedule, course.getLecture(0).getSection(0)))
                    return;
                else {
                    schedule.addToSchedule(course);
                    int schedulesSize = schedules.size();
                    int scheduleSize = schedule.getCourses().size();
                    findViableSchedules(schedules, index - 1, schedule, courses);
                    // Check if a schedule is added to list of schedules or if a course was added.
                    if (schedulesSize != schedules.size() || scheduleSize == schedule.getCourses().size())
                        schedule.removeFromSchedule(course);
                }
            }
        } catch (InvalidScheduleException e) {
            e.printStackTrace();
        }
    }

    private void findMandatorySchedules(int index, Schedule s) {
        findViableSchedules(fullSchedules, index, s, mandatoryCourses);
    }

    private void addOptionalCourses(int numOptional) {
        if (optionalCourses.isEmpty())
            return;
        List<Schedule> newFullSchedules = new ArrayList<>();
        // Creates a Set of Set<Course> containing each combination possible of optionalCourses.size() choose numOptional.
        Set<Set<Course>> combinations = Sets.combinations(new HashSet<>(optionalCourses), numOptional);

        for (Schedule mandatory : fullSchedules) {
            for (Set<Course> combination : combinations)
                // Tries each combination with each mandatory/fullSchedule to find non-conflicts.
                newFullSchedules.addAll(findOptionalSchedules(numOptional - 1, mandatory, combination));
        }

        // After every combination of size "numOptional" has been tried, if newFullSchedules is not empty, then
        // this means that there were viable schedules of size "numOptional" found.
        if (!newFullSchedules.isEmpty())
            fullSchedules = newFullSchedules;
        // Recursive Call
        // Stop when either newFullSchedules is NOT empty, or numOptional > 1, since we will make no attempts
        // to add 0 optional courses to the viable mandatory schedules.
        else if (numOptional > 1)
            addOptionalCourses(numOptional - 1);
    }

    private List<Schedule> findOptionalSchedules(int index, Schedule mandatorySchedule, Set<Course> optionalCourses) {
        List<Schedule> newFullSchedules = new ArrayList<>();
        findViableSchedules(newFullSchedules, index, mandatorySchedule, optionalCourses);
        return newFullSchedules;
    }

    // Private Conflict Checking Methods

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
            } else if (schedule.getCourses().get(i).getLecture(0).getNumSections() != 0 &&
                    isConflicting(schedule.getCourses().get(i).getLecture(0).getSection(0), session)) {
                return true;
            }
        }
        return false;
    }

    private boolean isConflicting(Session first, Session second) {
        boolean sameDay = false;
        for (String firstDay : first.getDays()) {
            for (String secondDay : second.getDays()) {
                if (firstDay.equals(secondDay))
                    sameDay = true;
            }
        }
        if (!sameDay)
            return false;

        return ((first.getTime().getStart().compareTo(second.getTime().getEnd()) < 0)
                && (first.getTime().getStart().compareTo(second.getTime().getStart()) >= 0)
                || (second.getTime().getStart().compareTo(first.getTime().getEnd()) < 0)
                && (second.getTime().getStart().compareTo(first.getTime().getStart()) >= 0));
    }
}
