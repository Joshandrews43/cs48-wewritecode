package com.wewritecode.server.handler;

import org.json.JSONObject;

/**
 * Interface for handling the specific HTTP requests by a part of the Application.
 *
 * {@code handlePost} and {@code handlePut} methods will turn the JSON request object into
 * objects that can be used by the rest of the program more readily.
 */
public interface RequestHandler {
    void handleGet();
    void handlePost(JSONObject request);
    void handlePut(JSONObject request);
    void handleDelete();
}
