package com.wewritecode.application.scheduler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wewritecode.application.common.schedule.*;
import com.wewritecode.application.pan.scheduler.BruteForceScheduler;
import com.wewritecode.application.server.request.ScheduleRequest;
import com.wewritecode.application.server.response.ScheduleResponse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Test class for the com.wewritecode.miner.GoldMiner class.
 *
 * @author  Alan Roddick
 * @version 1.1
 * @since   2019-05-07
 */

public class BruteForceSchedulerTest {

    private static ObjectMapper mapper;
    private BruteForceScheduler scheduler;

    @BeforeClass
    public static void setupClass() { mapper = new ObjectMapper(); }

    @Before
    public void setup() { scheduler = new BruteForceScheduler(); }

    // TODO: Tests to make
    // scheduleGenMandatoryOnlyNoSectionsTest
    // scheduleGenNoCoursesTest
    // scheduleGenAllConflictingTest
    // scheduleGenFullRequestTest (i.e. Mandatory, Optional, and Filter(s))
    // scheduleGenTimeFilterTest
    // scheduleGenDayFilterTest
    // scheduleGenEmptyRequestTest

    @Test
    public void scheduleGenMandatoryOnlyTest() throws IOException {
        InputStream is = BruteForceSchedulerTest.class.getResourceAsStream("/scheduleGenTest_1.json");
        ScheduleRequest request = mapper.readValue(is, ScheduleRequest.class);

        ScheduleResponse response = scheduler.generate(request);

        List<Schedule> schedules = response.getSchedules();

        assertEquals(8, schedules.size());
    }

    @Test
    public void scheduleGenMandatoryAndOptionalTest() throws IOException {
        InputStream is = BruteForceSchedulerTest.class.getResourceAsStream("/scheduleGenTest_2.json");
        ScheduleRequest request = mapper.readValue(is, ScheduleRequest.class);

        ScheduleResponse response = scheduler.generate(request);

        List<Schedule> schedules = response.getSchedules();
        assertEquals(1094, schedules.size());

        for (Schedule schedule : schedules) {
            assertEquals(6, schedule.getCourses().size());
        }
    }
}
