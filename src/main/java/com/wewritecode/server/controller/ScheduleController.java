/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.wewritecode.pan.scheduler.Scheduler;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Serves as the receiver for any requests related to scheduling
 */
@RestController
@ComponentScan("com.wewritecode.pan.scheduler")
public class ScheduleController {

    @Autowired
    Scheduler bruteForceScheduler;

    @PostMapping("/generateSchedules")
    public ScheduleResponse scheduleRequest(@RequestBody ScheduleRequest request) {
        ScheduleResponse response = bruteForceScheduler.generate(request);
        return response;
    }
}
