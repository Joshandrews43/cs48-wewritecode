/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

public class Time implements Comparable {
    private int hour;
    private int minute;

    @Override
    public int compareTo(Object o) {
        Time t = (Time) o;
        return ((60*this.hour + this.minute) - (60 * t.hour + t.minute));
    }
}
