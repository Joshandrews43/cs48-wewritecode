/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Schedule implements Comparable<Schedule> {

    @Id
    @GeneratedValue
    private int scheduleId;

    @OneToMany(targetEntity = Course.class, mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses = new ArrayList<>();

    private int fitness;

    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
    public int getFitness() { return fitness; }
    public void setFitness(int fitness) { this.fitness = fitness; }

    @Override
    public int compareTo(Schedule s) { return this.fitness - s.fitness; }
}
