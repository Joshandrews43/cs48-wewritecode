/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private List<String> days;
    private Time start;
    private Time end;

    public Session() {
        days = new ArrayList<>();
        start = new Time();
        end = new Time();
    }

    public Session(List<String> days, Time start, Time end) {
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
    public Time getStart() { return start; }
    public void setStart(Time start) { this.start = start; }
    public Time getEnd() { return end; }
    public void setEnd(Time end) { this.end = end; }
}
