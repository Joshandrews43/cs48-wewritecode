/**
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

public interface ITime extends Comparable<ITime> {
    int getHour();
    void setHour(int hour);
    int getMinute();
    void setMinute(int minute);
}
