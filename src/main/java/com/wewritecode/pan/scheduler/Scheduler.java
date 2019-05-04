/**
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.pan.schedule.Course;
import com.wewritecode.pan.schedule.Schedule;

import java.util.List;
import java.util.Map;

public interface Scheduler {
    List<Schedule> generate();

}
