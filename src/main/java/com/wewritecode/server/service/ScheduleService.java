/**
 * @author Grant Clark
 */

package com.wewritecode.server.service;

import com.wewritecode.pan.schedule.Schedule;
import com.wewritecode.server.repository.ScheduleRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    // TODO: Figure out what methods we should put here.
    public Schedule generateSchedules(JSONObject request) {
        Schedule test = new Schedule();
        test.setScheduleId(1);
        test.setFitness(1);
        return test;
    }
}
