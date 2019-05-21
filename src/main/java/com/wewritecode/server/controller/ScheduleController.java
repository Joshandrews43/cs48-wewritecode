/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.wewritecode.pan.schedule.Course;
import com.wewritecode.pan.scheduler.Scheduler;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Serves as the receiver for any requests related to scheduling
 */
@RestController
public class ScheduleController {

    @Autowired
    Scheduler bruteForceScheduler;

    @GetMapping(path = "/schedule/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse getAllSchedules() {
        return bruteForceScheduler.createResponse();
    }

    @GetMapping(path = "/schedule/get/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse getOneSchedule(@PathVariable int index) {
        return bruteForceScheduler.createResponse(index);
    }

    @GetMapping(path = "/schedule/get/{first}/{second}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse getRangeSchedules(@PathVariable("first") int first,
                                              @PathVariable("second") int second) {
        return bruteForceScheduler.createResponse(first, second);
    }

    @PutMapping(path = "/schedule/put", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putSchedules(@RequestBody ScheduleRequest request) {
        bruteForceScheduler.generate(request);
    }

    @PostMapping(path = {"/schedule/post", "/generateSchedules"},
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse generateSchedules(@RequestBody ScheduleRequest request) {
        ScheduleResponse response = bruteForceScheduler.generate(request);
        return response;
    }
}
