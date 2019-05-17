/**
 * @author Grant Clark
 */

package com.wewritecode.common.full;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wewritecode.common.schedule.Time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullSession {
    private String status;
    private List<String> instructors;
    private List<String> days;
    private Time time;
    private String location;
    private Map<String, Integer> enrollment;

    public FullSession() {
        instructors = new ArrayList<>();
        days = new ArrayList<>();
        time = new Time();
        enrollment = new HashMap<>();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<String> getInstructors() { return instructors; }
    public void setInstructors(List<String> instructors) { this.instructors = instructors; }
    public List<String> getDays() { return days; }
    public void setDays(List<String> days) { this.days = days; }
    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Map<String, Integer> getEnrollment() { return enrollment; }
    public void setEnrollment(Map<String, Integer> enrollment) { this.enrollment = enrollment; }

    public Integer getCurrent() { return enrollment.get("current"); }
    public void setCurrent(Integer current) { enrollment.put("current", current); }
    public Integer getMax() { return enrollment.get("max"); }
    public void setMax(Integer max) { enrollment.put("max", max); }
}
