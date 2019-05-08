package com.wewritecode.pan.schedule;

import java.io.Serializable;
import java.util.List;

public interface ISchedule<T> extends Serializable {
    List<T> getCourses();
    void setCourses(List<T> courses);
    void addCourse(T course);
}
