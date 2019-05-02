package com.wewritecode.scheduler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoldPan {

    private JSONObject post;

    private List<Schedule> finalSchedules;
    private Map<String, String> filters;

    public GoldPan() {
        finalSchedules = new ArrayList<>();
    }

    public JSONObject run() {
        JSONObject response = new JSONObject();
        // run brute force non-conflict algorithm (ScheduleGen)
        // run sort schedules by filters (ScheduleFilter)
        // combine all filtered schedules into a JSONObject
        return response;
    }
}
