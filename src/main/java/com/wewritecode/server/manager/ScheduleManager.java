/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wewritecode.pan.scheduler.BruteForceScheduler;
import com.wewritecode.pan.schedule.Schedule;
import com.wewritecode.pan.scheduler.Scheduler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;

import java.util.List;

public class ScheduleManager implements RequestManager {
    private static Scheduler scheduler;

    // Construction

    private ScheduleManager() {}

    private static class ScheduleManagerHolder {
        private static final ScheduleManager INSTANCE = new ScheduleManager();
    }

    public static ScheduleManager getInstance() {
        scheduler = BruteForceScheduler.getInstance();
        return ScheduleManagerHolder.INSTANCE;
    }

    // Request Handlers

    @Override
    public void handleGet() {}

    // Add certain lectures/sections to baseSchedule of goldPan.
    // Add any lectures/sections not added to baseSchedule to remainingCourses of goldPan.
    @Override
    public void handlePost(JSONObject request) {
        JSONObject courses = (JSONObject) request.get("courses");
        JSONArray mandatoryCourses = (JSONArray) courses.get("mandatory");
        JSONArray optionalCourses = (JSONArray) courses.get("optional");
        JSONArray filterOptions = (JSONArray) courses.get("filters");
        addCoursesToScheduler(mandatoryCourses, optionalCourses);
        addFiltersToScheduler(filterOptions);

    }

    @Override
    public void handlePut(JSONObject request) {}

    @Override
    public void handleDelete() {}

    // Core Methods

    public JSONObject generateSchedules(JSONObject request) {
        handlePost(request);
        List<Schedule> schedules = scheduler.generate();
        JSONArray schedulesAsJson = listToJsonArray(schedules);

        JSONObject output = new JSONObject();
        output.put("schedules", schedulesAsJson);

        return output;
    }

    // TODO: Replace with different method to perform this conversion of Java objects -> JSONObject/JSONArray
    private static JSONArray listToJsonArray(List<Schedule> schedules) {
        JSONArray output = new JSONArray();

        for (Schedule schedule : schedules) {
            String scheduleAsJson = new Gson().toJson(schedule);
            System.out.println(scheduleAsJson);
            output.put(new JSONObject(scheduleAsJson));
        }

        /*
            DON'T DO THIS CURRENT IMPLEMENTATION
            Alternative option:
                define a toJson method in each of the classes under the "schedule" package, and create
                a properly formatted JSONObject or JSONArray of each structure. That way, we can just call
                schedule.toJson(), and by successive internal calls to the appropriate toJson functions
                of each component of a schedule, we can ensure the output schedule is EXACTLY what we want.
         */

        return output;
    }

    private static void addCoursesToScheduler(JSONArray mandatoryCourses, JSONArray optionalCourses) {

    }

    private static void addFiltersToScheduler(JSONArray filterOptions) {

    }

}
