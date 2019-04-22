package com.wewritecode;

import org.json.JSONArray;
import org.json.JSONObject;
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
        JSONArray subjects = Miner.getOnlySubjects();
        actual.put("subjects", subjects);
        File subjectFile = new File(JSON_RESOURCE_DIR + "subjects.json");

        assertTrue("Expected to be able to output subject list to .json file",
                Miner.toJsonFile(actual, subjectFile));
    }

    @Test
    public void getQuartersTest() {
        JSONObject actual = new JSONObject();
        JSONArray quarters = Miner.getOnlyQuarters();
        actual.put("quarters", quarters);
        File subjectFile = new File(JSON_RESOURCE_DIR + "quarters.json");
        assertTrue("Expected to be able to output quarter list to .json file",
                Miner.toJsonFile(actual, subjectFile));
    }

    @Test
    public void getCourseLevelsTest() {
        JSONObject courseLevels = Miner.getOnlyCourseLevels();
        File courseLevelFile = new File(JSON_RESOURCE_DIR + "courseLevels.json");

        assertTrue("Expected to be able to output course levels to .json file",
                Miner.toJsonFile(courseLevels, courseLevelFile));
    }

    @Test
    public void getCoursesBySubjectAreaTest() {
        JSONObject subject = Miner.getCoursesBySubjectArea("Anthropology");
        File anthFile = new File(JSON_RESOURCE_DIR + "anthropology.json");
        assertTrue(Miner.toJsonFile(subject, anthFile));
    }

    @Test
    public void getAllSubjects() {
        Miner.getAllSubjects("Winter 2019");
    }

    @AfterClass
    public static void teardown() {
        Miner.close();
    }
}
