/**
 * @author Grant Clark
 */

package com.wewritecode.server.request;

import com.wewritecode.common.schedule.Course;
import com.wewritecode.pan.filter.Filter;

import java.util.*;

public class ScheduleRequest {
    private List<Course> mandatory;
    private List<Course> optional;
    private Set<Filter> filters;

    public ScheduleRequest() {
        mandatory = new ArrayList<>();
        optional = new ArrayList<>();
        filters = new HashSet<>();
    }

    public List<Course> getMandatory() { return mandatory; }
    public void setMandatory(List<Course> mandatory) { this.mandatory = mandatory; }
    public List<Course> getOptional() { return optional; }
    public void setOptional(List<Course> optional) { this.optional = optional; }
    public Set<Filter> getFilters() { return filters; }
    public void setFilters(Set<Filter> filters) { this.filters = filters; }
}
