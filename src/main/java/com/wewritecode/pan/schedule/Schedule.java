/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Schedule implements Comparable<Schedule> {

    @Id
    @GeneratedValue
    private int id;
    private List<Course> courses;
    private int fitness;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
    public int getFitness() { return fitness; }
    public void setFitness(int fitness) { this.fitness = fitness; }

    @Override
    public int compareTo(Schedule s) {
        return this.fitness - s.fitness;
    }
}
