/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.controller;

import com.wewritecode.pan.scheduler.Scheduler;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * Serves as the receiver for any requests related to scheduling
 */
@RestController
public class ScheduleController {

    @Autowired
    Scheduler bruteForceScheduler;

    // Scheduling Mappings

    @GetMapping(path = "/api/v1/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse getAllSchedules() {
        return bruteForceScheduler.createResponse();
    }

    @GetMapping(path = "/api/v1/schedule/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse getOneSchedule(@PathVariable int index) {
        return bruteForceScheduler.createResponse(index);
    }

    @GetMapping(path = "/api/v1/schedule/{first}/{second}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse getRangeSchedules(@PathVariable("first") int first,
                                              @PathVariable("second") int second) {
        return bruteForceScheduler.createResponse(first, second);
    }

    @PutMapping(path = "/api/v1/schedule/update/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putSchedules(@RequestBody ScheduleRequest request) {
        bruteForceScheduler.generate(request);
    }

    @PostMapping(path = "/api/v1/schedule/generate/all",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse generateAllSchedules(@RequestBody ScheduleRequest request) {
        ScheduleResponse response = bruteForceScheduler.generate(request);
        return response;
    }

    // TODO: Change path to "/api/v1/schedule/generate"
    @PostMapping(path = "/generateSchedules",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleResponse generate50Schedules(@RequestBody ScheduleRequest request) {
        bruteForceScheduler.generate(request);
        bruteForceScheduler.applyFilters(request.getFilters());
        return bruteForceScheduler.createResponse(0, 50);
    }

    // Filter Mappings

//    @PutMapping(path = "/api/v1/schedule/update/filters",
//            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public void applyFilters(@RequestBody FilterRequest request) {
//        bruteForceScheduler.applyFilters(request);
//    }
//
//    @PostMapping(path = "/api/v1/schedule/generate/filter",
//            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ScheduleResponse filterSchedules(@RequestBody FilterRequest request) {
//        bruteForceScheduler.applyFilters(request);
//        return bruteForceScheduler.createResponse(0, 50);
//    }

}
