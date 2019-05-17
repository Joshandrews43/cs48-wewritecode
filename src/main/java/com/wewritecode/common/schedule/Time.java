/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.common.schedule;

public class Time implements ITime<TimeObj> {
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

    @Override
    public TimeObj getStart() { return start; }
    @Override
    public void setStart(TimeObj start) { this.start = start; }
    @Override
    public TimeObj getEnd() { return end; }
    @Override
    public void setEnd(TimeObj end) { this.end = end; }
}
