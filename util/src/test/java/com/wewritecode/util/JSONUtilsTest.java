package com.wewritecode.util;

import org.json.JSONObject;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the com.wewritecode.util.JSONUtils class.
 *
 * @author  Alan Roddick
 * @version 1.0
 * @since   2019-05-01
 */

public class JSONUtilsTest {

    @Test
    public void getFromDataDirTest_InvalidQuarter() {
        assertEquals(JSONUtils.getFromDataDir("bad case").toString(), new JSONObject().toString());
    }
}
