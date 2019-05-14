/**
 * @author Grant Clark
 */

package com.wewritecode.server.response;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.Schedule;

import java.util.List;
import java.util.Set;

public class ScheduleResponse {
    private List<Schedule> schedules;
    private Set<Filter> filters;

    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) { this.schedules = schedules; }
    public Set<Filter> getFilters() { return filters; }
    public void setFilters(Set<Filter> filters) { this.filters = filters; }
}
