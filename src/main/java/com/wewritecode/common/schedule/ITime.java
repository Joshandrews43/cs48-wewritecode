/**
 * @author Grant Clark
 */

package com.wewritecode.common.schedule;

public interface ITime<T> {
    T getStart();
    void setStart(T start);
    T getEnd();
    void setEnd(T end);
}
