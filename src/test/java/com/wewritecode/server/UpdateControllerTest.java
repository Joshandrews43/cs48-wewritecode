package com.wewritecode.server;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the com.wewritecode.server.UpdateController class.
 *
 * @author  Alan Roddick
 * @version 1.1
 * @since   2019-05-01
 */

public class UpdateControllerTest {



    @Test
    public void getQuarterTest() {

        assertEquals(UpdateController.getQuarter("bad case").toString(), new JSONObject().toString());

    }

}
