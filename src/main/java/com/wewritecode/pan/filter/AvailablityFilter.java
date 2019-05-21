/**
 * @author Grant Clark
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.pan.schedule.Schedule;

@JsonTypeName("Availability")
public class AvailablityFilter extends AbstractScheduleFilter {

    private static final String[] OPTIONS = {};

    @Override
    public String[] getOptions() { return OPTIONS; }

    @Override
    public double getFitness(Schedule schedule) {
        return 0;
    }
}
