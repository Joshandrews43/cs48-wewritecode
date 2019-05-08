/**
 * @author Grant Clark
 */

package com.wewritecode.server.request;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.Course;

import javax.persistence.*;
import java.util.List;

@Entity
public class ScheduleRequest {
    @Id
    @GeneratedValue
    private int id;
    @ElementCollection
    private List<Course> mandatory;
    @ElementCollection
    private List<Course> optional;
    @ElementCollection
    private List<Filter> filters;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<Course> getMandatory() { return mandatory; }
    public void setMandatory(List<Course> mandatory) { this.mandatory = mandatory; }
    public List<Course> getOptional() { return optional; }
    public void setOptional(List<Course> optional) { this.optional = optional; }
    public List<Filter> getFilters() { return filters; }
    public void setFilters(List<Filter> filters) { this.filters = filters; }
}
