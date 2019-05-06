/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.google.gson.JsonObject;
import com.wewritecode.pan.schedule.Schedule;
import com.wewritecode.server.service.ScheduleService;
import com.wewritecode.util.JSONUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    // TODO: Switch the RequestBody to be an object wrapping a List<Course> mandatory, List<Course> optional, and List<Filter> filters
    // TODO: Switch the Response to be an object wrapping a List<Schedule>
    @PostMapping("/generateSchedules")
    public Schedule scheduleRequest(@RequestBody JsonObject requestedClasses) {
        JSONObject request = JSONUtils.gsonToJson(requestedClasses);
        //JSONObject response = ScheduleManager.getInstance().generateSchedules(request);
        Schedule response = scheduleService.generateSchedules(request);
        return response;
    }
}
