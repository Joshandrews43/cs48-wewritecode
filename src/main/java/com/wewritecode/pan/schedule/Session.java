/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Session {

    @Id
    @GeneratedValue
    private int id;
    private List<String> days;
    private Time start;
    private Time end;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<String> getDays() { return days; }
    public void setDays(List<String> days) { this.days = days; }
    public Time getStart() { return start; }
    public void setStart(Time start) { this.start = start; }
    public Time getEnd() { return end; }
    public void setEnd(Time end) { this.end = end; }
}
