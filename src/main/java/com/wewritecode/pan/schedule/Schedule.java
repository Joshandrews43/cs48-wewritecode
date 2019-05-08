/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.ArrayList;
import java.util.List;

public class Schedule implements ISchedule<Course>, Comparable<Schedule> {
    private List<Course> courses;
    private int fitness;

    public Schedule() {
        courses = new ArrayList<>();
        fitness = 0;
    }

    public Schedule(Schedule s) {
        courses = new ArrayList<>();
        for (int i = 0; i < s.getCourses().size(); i++) {
            this.courses.add(s.getCourses().get(i));
        }
        this.fitness = s.fitness;
    }

    public void addToSchedule(Course c) { courses.add(c); }
    public void removeFromSchedule(Course c) {
        courses.remove(c);
    }

    @Override
    public List<Course> getCourses() { return courses; }
    @Override
    public void setCourses(List<Course> courses) { this.courses = courses; }
    @Override
    public void addCourse(Course course) { courses.add(course); }

    public int getFitness() { return fitness; }
    public void setFitness(int fitness) { this.fitness = fitness; }

    @Override
    public int compareTo(Schedule s) { return this.fitness - s.fitness; }

}
