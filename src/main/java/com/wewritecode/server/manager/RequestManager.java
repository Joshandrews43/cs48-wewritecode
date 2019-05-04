/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.server.manager;

import org.json.JSONObject;

public interface RequestManager {
    void handleGet();
    void handlePost(JSONObject request);
    void handlePut(JSONObject request);
    void handleDelete();
}
