/**
 * @author Grant Clark
 */

package com.wewritecode.application.server.request;

import com.wewritecode.application.common.schedule.Course;

import java.util.*;

public class ScheduleRequest {
    private List<Course> mandatory;
    private List<Course> optional;

    public ScheduleRequest() {
        mandatory = new ArrayList<>();
        optional = new ArrayList<>();
    }

    public List<Course> getMandatory() { return mandatory; }
    public void setMandatory(List<Course> mandatory) { this.mandatory = mandatory; }
    public List<Course> getOptional() { return optional; }
    public void setOptional(List<Course> optional) { this.optional = optional; }
}
