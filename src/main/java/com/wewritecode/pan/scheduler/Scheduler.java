/**
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.server.request.FilterRequest;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;

import java.util.Set;

public interface Scheduler {
    ScheduleResponse generate(ScheduleRequest request);
    void applyFilters(Set<Filter> filters);

    ScheduleResponse createResponse();
    ScheduleResponse createResponse(int index);
    ScheduleResponse createResponse(int start, int end);
}
