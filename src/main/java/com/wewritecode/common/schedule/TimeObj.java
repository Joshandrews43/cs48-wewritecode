/**
 * @author Grant Clark
 */

package com.wewritecode.common.schedule;

public class TimeObj implements Comparable<TimeObj> {
    private int hour;
    private int minute;

    public TimeObj() {
        this.hour = 0;
        this.minute = 0;
    }

    public TimeObj(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }

    @Override
    public int compareTo(TimeObj t) {
        int thisTime = (hour * 60) + minute;
        int thatTime = (t.getHour() * 60) + t.getMinute();
        return thisTime - thatTime;
    }
}
