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
