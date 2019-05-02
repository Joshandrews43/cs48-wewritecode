/**
 * @author Grant Clark
 */

package com.wewritecode.scheduler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ScheduleSort {

    // TODO: Find some object that can associate String keys from the filter options with actual method calls.

    /**
     * Generates a Map of filter options. The key will be the category of filter,
     * and the value is the specific filter chosen. (i.e. time: early)
     *
     * @param post JSONObject containing the filters selected.
     * @return map of filter options.
     */
    private static Map<String, String> parseFilterOptions(JSONObject post) {
        Map<String, String> filters = new HashMap<>();
        // TODO: Implement logic here
        return filters;
    }
}
