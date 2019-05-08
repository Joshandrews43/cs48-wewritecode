/**
 * @author Grant Clark
 */

package com.wewritecode.server.response;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.Schedule;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class ScheduleResponse {
    @Id
    @GeneratedValue
    private int id;
    @ElementCollection
    private List<Schedule> schedules;
    @ElementCollection
    private List<Filter> filters;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) { this.schedules = schedules; }
    public List<Filter> getFilters() { return filters; }
    public void setFilters(List<Filter> filters) { this.filters = filters; }
}
