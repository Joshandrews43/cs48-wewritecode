/**
 * @author Grant Clark
 */

package com.wewritecode.server.manager;

import com.wewritecode.scheduler.GoldPan;
import com.wewritecode.server.handler.ScheduleHandler;
import org.json.JSONObject;

public class ScheduleManager implements RequestManager {

    private static final ScheduleManager INSTANCE = new ScheduleManager();
    private static ScheduleHandler handler;

    private ScheduleManager() {}

    public static ScheduleManager getInstance() {
        handler = ScheduleHandler.getInstance();
        return INSTANCE;
    }

    public JSONObject generateSchedules(JSONObject request) {
        // Handle the request by figuring out what information it has and storing it in Java objects.
        handler.handlePost(request);
        GoldPan.getInstance().generate();
        return null;
    }

}
