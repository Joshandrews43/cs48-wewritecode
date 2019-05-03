/**
 * @author Grant Clark
 */

package com.wewritecode.server.handler;

import org.json.JSONObject;

public class UpdateHandler implements RequestHandler {

    private static final UpdateHandler INSTANCE = new UpdateHandler();

    private UpdateHandler() {}

    // Visit https://www.baeldung.com/java-constructors-vs-static-factory-methods for more information on this
    // usage. This implementation of getInstance() allows for thread-safety.
    public static UpdateHandler getInstance() { return INSTANCE; }

    @Override
    public void handleGet() {

    }

    @Override
    public void handlePost(JSONObject request) {

    }

    @Override
    public void handlePut(JSONObject request) {

    }

    @Override
    public void handleDelete() {

    }
}
