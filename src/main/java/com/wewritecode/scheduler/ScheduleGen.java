/**
 * @author Grant Clark
 */

package com.wewritecode.scheduler;

import com.wewritecode.scheduler.schedule.Course;
import com.wewritecode.scheduler.schedule.Schedule;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGen {

    private Schedule baseSchedule;
    private List<Course> remainingCourses;

    /**
     * Generates a list of non-conflicting schedules.
     *
     * @param post JSONObject containing the courses selected.
     * @return list of schedules that do not conflict.
     */
    public static List<Schedule> generate(JSONObject post) {
        List<Schedule> schedules = new ArrayList<>();
        // TODO: Implement logic here
        return schedules;
    }
}
