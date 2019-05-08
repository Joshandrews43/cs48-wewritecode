/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

// TODO: Modify to be an interface with just public (unimplemented) getters/setters. Leave out private fields.
public class Session {
    private String[] days;
    private Time start;
    private Time end;

    public String[] getDays() {
        return days;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public void setDays(String[] days) {
        for (int i = 0; i < days.length; i++) {
            this.days[i] = days[i];
        }
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public void setEnd(Time end) {
        this.end = end;
    }
}
