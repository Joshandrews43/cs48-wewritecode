/**
 * @author Grant Clark
 */

package com.wewritecode.server.manager;

public class ScheduleManager implements RequestManager {

    private static final ScheduleManager INSTANCE = new ScheduleManager();

    private ScheduleManager() {}

    public static ScheduleManager getInstance() {
        return INSTANCE;
    }

}
