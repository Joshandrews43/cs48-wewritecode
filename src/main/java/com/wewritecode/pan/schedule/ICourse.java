package com.wewritecode.pan.schedule;

import java.io.Serializable;
import java.util.List;

public interface ICourse<T> extends Serializable {
    String getCourseId();
    List<T> getLectures();
    void setLectures(List<T> lectures);
    void addLecture(T lecture);
}
