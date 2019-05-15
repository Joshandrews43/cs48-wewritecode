/**
 * @author Grant Clark
 */

package com.wewritecode.server.response;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.Schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScheduleResponse {
    private List<Schedule> schedules;
    private Set<Filter> filters;

    public ScheduleResponse() {
        schedules = new ArrayList<>();
        filters = new HashSet<>();
    }

    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) { this.schedules = schedules; }
    public void addSchedule(Schedule schedule) { schedules.add(schedule); }
    public void addSchedules(List<Schedule> schedules) { this.schedules.addAll(schedules); }
    public Set<Filter> getFilters() { return filters; }
    public void setFilters(Set<Filter> filters) { this.filters = filters; }
}
