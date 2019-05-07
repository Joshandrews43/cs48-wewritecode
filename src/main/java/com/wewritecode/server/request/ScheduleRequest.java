/**
 * @author Grant Clark
 */

package com.wewritecode.server.request;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.Course;

import java.util.List;

public class ScheduleRequest {
    private List<Course> mandatory;
    private List<Course> optional;
    private List<Filter> filters;

    public List<Course> getMandatory() { return mandatory; }
    public void setMandatory(List<Course> mandatory) { this.mandatory = mandatory; }
    public List<Course> getOptional() { return optional; }
    public void setOptional(List<Course> optional) { this.optional = optional; }
    public List<Filter> getFilters() { return filters; }
    public void setFilters(List<Filter> filters) { this.filters = filters; }
}
