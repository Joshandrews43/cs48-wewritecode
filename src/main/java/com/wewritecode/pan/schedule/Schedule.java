/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.filter.InvalidFilterOptionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Schedule implements ISchedule<Course>, Comparable<Schedule> {
    private List<Course> courses;
    private double fitness;

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

    public double getFitness() { return fitness; }
    public void setFitness(int fitness) { this.fitness = fitness; }

    public void calcFitness(Set<Filter> filters) throws InvalidFilterOptionException {
        double sum = 0;
        for (Filter f : filters)
            sum += f.getFitness(this);
        fitness = (sum / filters.size());
    }

    @Override
    public int compareTo(Schedule s) {
        if (this.fitness < s.fitness)
            return -1;
        else if (s.fitness < this.fitness)
            return 1;
        return 0;
    }
}
