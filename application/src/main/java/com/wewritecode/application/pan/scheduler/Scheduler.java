/**
 * @author Grant Clark
 */

package com.wewritecode.application.pan.scheduler;

import com.wewritecode.application.server.request.FilterRequest;
import com.wewritecode.application.server.request.ScheduleRequest;
import com.wewritecode.application.server.response.ScheduleResponse;

public interface Scheduler {
    ScheduleResponse generate(ScheduleRequest request);
    void applyFilters(FilterRequest request);

    ScheduleResponse createResponse();
    ScheduleResponse createResponse(int index);
    ScheduleResponse createResponse(int start, int end);
}
