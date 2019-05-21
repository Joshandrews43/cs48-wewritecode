package com.wewritecode.pan.schedule;

import java.util.List;

public interface ISchedule<T> {
    List<T> getCourses();
    void setCourses(List<T> courses);
    void addCourse(T course);
}
