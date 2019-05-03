package com.wewritecode.server.handler;

public interface RequestHandler {
    void handleGet();
    void handlePost();
    void handlePut();
    void handleDelete();
}
