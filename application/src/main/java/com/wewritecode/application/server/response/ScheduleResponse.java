/**
 * @author Grant Clark
 */

package com.wewritecode.application.server.response;

import com.wewritecode.application.pan.filter.Filter;
import com.wewritecode.application.common.schedule.Schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScheduleResponse {
    private int numSchedules;
    private List<Schedule> schedules;
    private Set<Filter> filters;

    public ScheduleResponse() {
        numSchedules = 0;
        schedules = new ArrayList<>();
        filters = new HashSet<>();
    }

    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
        numSchedules = schedules.size();
    }
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        numSchedules++;
    }
    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        numSchedules--;
    }
    public void removeSchedule(int index) {
        schedules.remove(index);
        numSchedules--;
    }
    public void addSchedules(List<Schedule> schedules) {
        this.schedules.addAll(schedules);
        numSchedules += schedules.size();
    }

    public Set<Filter> getFilters() { return filters; }
    public void setFilters(Set<Filter> filters) { this.filters = filters; }
}
