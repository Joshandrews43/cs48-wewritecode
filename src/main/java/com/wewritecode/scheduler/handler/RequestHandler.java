package com.wewritecode.scheduler.handler;

public interface RequestHandler {
    void handleGet();
    void handlePost();
    void handlePut();
    void handleDelete();
}
