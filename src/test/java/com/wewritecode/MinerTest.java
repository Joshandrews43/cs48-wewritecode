package com.wewritecode;

import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * Test class for the com.wewritecode.Miner class.
 *
 * @author  Grant Clark
 * @version 1.0
 * @since   2019-04-17
 */
public class MinerTest {
    @Test
    public void getSubjectsTest() {
        Miner.init();
        JSONObject subjects = Miner.getSubjects();
        Miner.toJsonFile(subjects, "subjects.json");
        Miner.close();
    }

    @Test
    public void getQuartersTest() {
        Miner.init();
        JSONObject quarters = Miner.getQuarters();
        Miner.toJsonFile(quarters, "quarter.json");
        Miner.close();
    }
}
