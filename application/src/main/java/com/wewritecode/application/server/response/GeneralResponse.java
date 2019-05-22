/**
 * @author Grant Clark
 */

package com.wewritecode.application.server.response;

import com.wewritecode.application.common.full.FullQuarter;
import com.wewritecode.application.common.general.General;

import java.util.HashMap;
import java.util.Map;

public class GeneralResponse {
    private General general;
    private Map<String, FullQuarter> quarters;

    public GeneralResponse() {
        general = new General();
        quarters = new HashMap<>();
    }

    public General getGeneral() { return general; }
    public void setGeneral(General general) { this.general = general; }
    public Map<String, FullQuarter> getQuarters() { return quarters; }
    public void setQuarters(Map<String, FullQuarter> quarters) { this.quarters = quarters; }
}
