/**
 * @author Grant Clark
 */

package com.wewritecode.server.response;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.Schedule;

import java.util.List;

public class ScheduleResponse {
    private List<Schedule> schedules;
    private List<Filter> filters;

    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) { this.schedules = schedules; }
    public List<Filter> getFilters() { return filters; }
    public void setFilters(List<Filter> filters) { this.filters = filters; }
}
