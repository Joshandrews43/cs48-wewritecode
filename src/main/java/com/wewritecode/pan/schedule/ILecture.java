/**
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.List;

public interface ILecture<T> {
    List<T> getSections();
    void setSections(List<T> sections);
    void addSection(T section);
}
