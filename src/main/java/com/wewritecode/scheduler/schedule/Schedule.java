package com.wewritecode.scheduler.schedule;

import java.util.List;

public class Schedule implements Comparable<Schedule> {
    private List<Course> courses;
    private int fitness;

    @Override
    public int compareTo(Schedule s) {
        return this.fitness - s.fitness;
    }
}
