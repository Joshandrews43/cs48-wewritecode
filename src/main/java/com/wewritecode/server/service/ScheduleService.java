/**
 * @author Grant Clark
 */

package com.wewritecode.server.service;

import com.wewritecode.server.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    // TODO: Figure out what methods we should put here.
}
