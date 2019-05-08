/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private List<String> days;
    private ITime start;
    private ITime end;

    public Session() {
        days = new ArrayList<>();
        start = new Time();
        end = new Time();
    }

    public Session(List<String> days, ITime start, ITime end) {
        this.days = days;
        this.start = start;
        this.end = end;
    }

    public Session(List<String> days, int startHour, int startMin, int endHour, int endMin) {
        this.days = days;
        start = new Time(startHour, startMin);
        end = new Time(endHour, endMin);
    }

    public String getDay(int index) throws IndexOutOfBoundsException { return days.get(index); }

    public List<String> getDays() { return days; }
    public void setDays(List<String> days) { this.days = days; }
    public ITime getStart() { return start; }
    public void setStart(ITime start) { this.start = start; }
    public ITime getEnd() { return end; }
    public void setEnd(ITime end) { this.end = end; }
}
