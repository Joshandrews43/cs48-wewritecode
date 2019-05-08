/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

public class Time implements Comparable {
    private int hour;
    private int minute;

    public Time() {
        hour = 0;
        minute = 0;
    }

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public int compareTo(Object o) {
        Time t = (Time) o;
        return (((60*this.hour) + this.minute) - ((60 * t.hour) + t.minute));
    }
}
