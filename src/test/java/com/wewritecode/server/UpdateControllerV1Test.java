package com.wewritecode.server;

import org.json.JSONObject;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the com.wewritecode.server.UpdateController_v1 class.
 *
 * @author  Alan Roddick
 * @version 1.1
 * @since   2019-05-01
 */

public class UpdateControllerV1Test {



    @Test
    public void getQuarterTest() {

        assertEquals(UpdateController_v1.getQuarter("bad case").toString(), new JSONObject().toString());

    }

}
