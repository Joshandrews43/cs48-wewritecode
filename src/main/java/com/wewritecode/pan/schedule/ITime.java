/**
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

public interface ITime<T> {
    T getStart();
    void setStart(T start);
    T getEnd();
    void setEnd(T end);
}
