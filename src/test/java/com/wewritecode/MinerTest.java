package com.wewritecode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Test class for the com.wewritecode.Miner class.
 *
 * @author  Grant Clark
 * @version 1.0
 * @since   2019-04-17
 */
public class MinerTest {

    private static final String JSON_RESOURCE_DIR = System.getProperty("user.dir")+"/src/test/resources/jsons/";

    @BeforeClass
    public static void setup() {
        Miner.init();
    }

    @Test
    public void getSubjectsTest() {
        JSONObject actual = new JSONObject();
        JSONArray subjects = Miner.getSubjects();
        actual.put("subjects", subjects);
        File subjectFile = new File(JSON_RESOURCE_DIR + "subjects.json");

        assertTrue("Expected to be able to output subject list to .json file",
                Miner.toJsonFile(actual, subjectFile));
    }

    @Test
    public void getQuartersTest() {
        JSONObject actual = new JSONObject();
        JSONArray quarters = Miner.getQuarters();
        actual.put("quarters", quarters);
        File subjectFile = new File(JSON_RESOURCE_DIR + "quarters.json");
        assertTrue("Expected to be able to output quarter list to .json file",
                Miner.toJsonFile(actual, subjectFile));
    }

    @Test
    public void getCourseLevelsTest() {
        JSONObject courseLevels = Miner.getCourseLevels();
        File courseLevelFile = new File(JSON_RESOURCE_DIR + "courseLevels.json");

        assertTrue("Expected to be able to output course levels to .json file",
                Miner.toJsonFile(courseLevels, courseLevelFile));
    }

    @AfterClass
    public static void teardown() {
        Miner.close();
    }
}
