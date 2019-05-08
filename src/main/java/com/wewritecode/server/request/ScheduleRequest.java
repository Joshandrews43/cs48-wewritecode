/**
 * @author Grant Clark
 */

package com.wewritecode.server.request;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.ICourse;

import java.util.List;

public class ScheduleRequest {
    private List<ICourse> mandatory;
    private List<ICourse> optional;
    private List<Filter> filters;

    public List<ICourse> getMandatory() { return mandatory; }
    public void setMandatory(List<ICourse> mandatory) { this.mandatory = mandatory; }
    public List<ICourse> getOptional() { return optional; }
    public void setOptional(List<ICourse> optional) { this.optional = optional; }
    public List<Filter> getFilters() { return filters; }
    public void setFilters(List<Filter> filters) { this.filters = filters; }
}
