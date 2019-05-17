/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.common.schedule;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private List<String> days;
    private Time time;

    public Session() {
        days = new ArrayList<>();
        time = new Time();
    }

    public Session(List<String> days, Time time) {
        this.days = days;
        this.time = time;
    }

    public Session(List<String> days, int startHour, int startMin, int endHour, int endMin) {
        this.days = days;
        time = new Time(startHour, startMin, endHour, endMin);
    }

    public String getDay(int index) throws IndexOutOfBoundsException { return days.get(index); }
    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }
    public List<String> getDays() { return days; }
    public void setDays(List<String> days) { this.days = days; }
}
