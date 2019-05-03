/**
 * @author Grant Clark
 */

package com.wewritecode.server.handler;

import com.wewritecode.scheduler.GoldPan;
import org.json.JSONObject;

public class ScheduleHandler implements RequestHandler {
    private static GoldPan goldPan;

    private ScheduleHandler() {}

    private static class ScheduleHandlerHolder {
        private static final ScheduleHandler INSTANCE = new ScheduleHandler();
    }

    public static ScheduleHandler getInstance() {
        goldPan = GoldPan.getInstance();
        return ScheduleHandlerHolder.INSTANCE;
    }

    @Override
    public void handleGet() {}

    // Add certain lectures/sections to baseSchedule of goldPan.
    // Add any lectures/sections not added to baseSchedule to remainingCourses of goldPan.
    @Override
    public void handlePost(JSONObject request) {

    }

    @Override
    public void handlePut(JSONObject request) {}

    @Override
    public void handleDelete() {}
}
