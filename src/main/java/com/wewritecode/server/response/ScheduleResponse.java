/**
 * @author Grant Clark
 */

package com.wewritecode.server.response;

import com.wewritecode.pan.schedule.Schedule;

import java.util.List;

public class ScheduleResponse {
    private List<Schedule> schedules;

    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) { this.schedules = schedules; }
}
