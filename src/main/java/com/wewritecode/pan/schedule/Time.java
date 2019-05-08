/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

public class Time implements ITime {
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
    public int getHour() { return hour; }
    @Override
    public void setHour(int hour) { this.hour = hour; }
    @Override
    public int getMinute() { return minute; }
    @Override
    public void setMinute(int minute) { this.minute = minute; }


    @Override
    public int compareTo(ITime t) {
        int thisTime = (hour * 60) + minute;
        int thatTime = (t.getHour() * 60) + t.getMinute();
        return thisTime - thatTime;
    }
}
