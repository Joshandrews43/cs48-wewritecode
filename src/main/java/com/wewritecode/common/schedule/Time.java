/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.common.schedule;

public class Time {
    private TimeObj start;
    private TimeObj end;

    public Time() {
        start = new TimeObj();
        end = new TimeObj();
    }

    public Time(int startHour, int startMinute, int endHour, int endMinute) {
        start = new TimeObj(startHour, startMinute);
        end = new TimeObj(endHour, endMinute);
    }

    public TimeObj getStart() { return start; }
    public void setStart(TimeObj start) { this.start = start; }
    public TimeObj getEnd() { return end; }
    public void setEnd(TimeObj end) { this.end = end; }
}
