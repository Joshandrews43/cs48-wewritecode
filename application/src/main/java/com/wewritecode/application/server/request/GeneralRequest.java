/**
 * @author Grant Clark
 */

package com.wewritecode.application.server.request;

import com.wewritecode.application.common.general.General;

public class GeneralRequest {
    private General general;

    public GeneralRequest() {
        general = new General();
    }

    public General getGeneral() { return general; }
    public void setGeneral(General general) { this.general = general; }
}
