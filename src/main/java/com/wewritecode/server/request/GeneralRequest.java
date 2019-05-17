/**
 * @author Grant Clark
 */

package com.wewritecode.server.request;

import com.wewritecode.common.general.General;

public class GeneralRequest {
    private General general;

    public GeneralRequest() {
        general = new General();
    }

    public General getGeneral() { return general; }
    public void setGeneral(General general) { this.general = general; }
}
