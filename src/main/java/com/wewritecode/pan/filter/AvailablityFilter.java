/**
 * @author Grant Clark
 */

package com.wewritecode.pan.filter;

import com.wewritecode.pan.schedule.Schedule;

public class AvailablityFilter implements Filter<Schedule> {
    @Override
    public double getFitness(Schedule schedule) {
        return 0;
    }
}
