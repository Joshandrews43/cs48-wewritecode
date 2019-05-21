/**
 * @author Grant Clark
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wewritecode.common.schedule.Schedule;

/**
 * Abstract class for {@code Filter.java} using {@code Schedule.java} as the Schedule object.
 *
 * Any sub-classes must be annotated with {@code @JsonTypeName()} and the class names must be added to the
 * {@code @JsonSubTypes({})} annotation using the {@code @Type()} annotation.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @Type(value = TimeFilter.class),
        @Type(value = DayFilter.class),
        @Type(value = GapsFilter.class)
    })
public abstract class AbstractScheduleFilter implements Filter<Schedule> {
    protected String option;

    public String getOption() { return option; }
    public void setOption(String option) { this.option = option; }
    public abstract String[] getOptions();
}
