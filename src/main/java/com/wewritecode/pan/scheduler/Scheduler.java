/**
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.server.request.FilterRequest;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;

public interface Scheduler {
    ScheduleResponse generate(ScheduleRequest request);
    void applyFilters(FilterRequest request);

    ScheduleResponse createResponse();
    ScheduleResponse createResponse(int index);
    ScheduleResponse createResponse(int start, int end);
}
