/**
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;

public interface Scheduler {
    ScheduleResponse generate(ScheduleRequest request);
    ScheduleResponse createResponse();
    ScheduleResponse createResponse(int index);
    ScheduleResponse createResponse(int start, int end);
}
