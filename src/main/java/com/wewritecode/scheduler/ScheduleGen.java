/**
 * @author Grant Clark
 */

package com.wewritecode.scheduler;

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
        // Use GSON if you want, but I recommend JSON if you are able to get that working.
        // TODO: Implement logic here
        return schedules;
    }
}
