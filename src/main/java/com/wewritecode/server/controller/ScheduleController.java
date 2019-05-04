/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.google.gson.JsonObject;
import com.wewritecode.server.manager.ScheduleManager;
import com.wewritecode.util.JSONUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    // TODO: Switch the RequestBody to be an object wrapping a List<Course> mandatory, List<Course> optional, and List<Filter> filters
    // TODO: Switch the Response to be an object wrapping a List<Schedule>
    @PostMapping(value = "/generateSchedules")
    public JsonObject scheduleRequest(@RequestBody JsonObject requestedClasses) {
        JSONObject request = JSONUtils.gsonToJson(requestedClasses);
        JSONObject response = ScheduleManager.getInstance().generateSchedules(request);
        return JSONUtils.jsonToGson(response);
    }
}
