/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import com.wewritecode.server.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Serves as the receiver for any requests related to scheduling
 */
@RestController
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/generateSchedules")
    public ScheduleResponse scheduleRequest(@RequestBody ScheduleRequest request) {
        ScheduleResponse response = scheduleService.generateSchedules(request);
        return response;
    }
}
