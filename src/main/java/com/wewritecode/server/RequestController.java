package com.wewritecode.server;

import com.google.gson.*;
import com.wewritecode.server.manager.ScheduleManager;
import com.wewritecode.server.manager.UpdateManager;
import com.wewritecode.util.JSONUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {

    @PostMapping(value = "/generateSchedules")
    public JsonObject scheduleRequest(@RequestBody JsonObject requestedClasses) {
        JSONObject request = JSONUtils.gsonToJson(requestedClasses);
        JSONObject response = ScheduleManager.getInstance().generateSchedules(request);
        return JSONUtils.jsonToGson(response);
    }

    @PostMapping(value = "/getUpdated")
    public JsonObject updateRequest(@RequestBody JsonObject requestedQuarters) {
        JSONObject request = JSONUtils.gsonToJson(requestedQuarters);
        JSONObject response = UpdateManager.getInstance().updateQuarters(request);
        return JSONUtils.jsonToGson(response);
    }
}