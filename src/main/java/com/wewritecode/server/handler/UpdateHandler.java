/**
 * @author Grant Clark
 */

package com.wewritecode.server.handler;

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
    public void handlePost() {

    }

    @Override
    public void handlePut() {

    }

    @Override
    public void handleDelete() {

    }
}
