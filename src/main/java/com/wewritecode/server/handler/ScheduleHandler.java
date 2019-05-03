/**
 * @author Grant Clark
 */

package com.wewritecode.server.handler;

public class ScheduleHandler implements RequestHandler {

    private static final ScheduleHandler INSTANCE = new ScheduleHandler();

    private ScheduleHandler() {}

    public static ScheduleHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void handleGet() {

    }

    @Override
    public void handlePost() {

    }

    @Override
    public void handlePut() {

    }

    @Override
    public void handleDelete() {

    }
}
