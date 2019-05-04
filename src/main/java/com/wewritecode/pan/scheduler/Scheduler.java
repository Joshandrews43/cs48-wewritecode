/**
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.pan.schedule.Schedule;

import java.util.List;

public interface Scheduler {
    List<Schedule> generate();
}
