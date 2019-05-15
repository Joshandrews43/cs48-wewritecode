package com.wewritecode.pan.schedule;

import java.util.List;

public interface ICourse<T> {
    String getCourseID();
    List<T> getLectures();
    void setLectures(List<T> lectures);
    void addLecture(T lecture);
}
