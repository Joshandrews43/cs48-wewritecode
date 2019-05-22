/**
 * @author Grant Clark
 */

package com.wewritecode.application.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.application.common.schedule.Schedule;

@JsonTypeName("RateMyProfessor")
public class RateProfessorFilter extends AbstractScheduleFilter {

    private static final String[] OPTIONS = {};

    @Override
    public String[] getOptions() { return OPTIONS; }

    @Override
    public double getFitness(Schedule schedule) {
        return 0;
    }
}
