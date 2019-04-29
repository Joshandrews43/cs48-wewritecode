package com.wewritecode;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Test class for the com.wewritecode.GoldMiner class.
 *
 * @author  Grant Clark
 * @version 1.1
 * @since   2019-04-24
 */
public class GoldMinerTest {

    // Component Under Test (CUT)
    private static GoldMiner cut;

    private static final String JSON_RESOURCE_DIR = System.getProperty("user.dir")+"/src/test/resources/jsons/";

    @Before
    public void setup() {
        cut = new GoldMiner();
    }

    @After
    public void teardown() { cut.close(); }

    @Test
    public void getSubjectsTest() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        JSONObject actual = new JSONObject();
        JSONArray subjects = cut.getOnlySubjects();
        actual.put("subjects", subjects);
        File subjectFile = new File(JSON_RESOURCE_DIR + "subjects.json");

        assertTrue("Expected to be able to output subject list to .json file",
                GoldMiner.toJsonFile(actual, subjectFile));
    }

    @Test
    public void getQuartersTest() {
        JSONObject actual = new JSONObject();
        JSONArray quarters = cut.getOnlyQuarters();
        actual.put("quarters", quarters);
        File subjectFile = new File(JSON_RESOURCE_DIR + "quarters.json");
        assertTrue("Expected to be able to output quarter list to .json file",
                GoldMiner.toJsonFile(actual, subjectFile));
    }

    @Test
    public void getCourseLevelsTest() {
        JSONObject courseLevels = cut.getOnlyCourseLevels();
        File courseLevelFile = new File(JSON_RESOURCE_DIR + "courseLevels.json");

        assertTrue("Expected to be able to output course levels to .json file",
                GoldMiner.toJsonFile(courseLevels, courseLevelFile));
    }

    @Test
    @Ignore
    public void getAllSubjectsTest() {
        JSONObject allSubjects = cut.getAllData("Spring 2019");

        File quarterFile = new File(JSON_RESOURCE_DIR + "spring2019.json");

        assertTrue(GoldMiner.toJsonFile(allSubjects, quarterFile));
    }

    @Test
    @Ignore
    public void getAllCurrentData(){
        JSONArray allQuarters = cut.getOnlyQuarters();
        for (int i = 0; i < 3; i++) {
            JSONObject jObj = allQuarters.getJSONObject(i);
            String season = jObj.getString("season");
            String year = jObj.getString("year");
            String quarterStr = season + " " + year;
            JSONObject quarter = cut.getAllData(quarterStr);
            File quarterFile = new File(JSON_RESOURCE_DIR + season.toLowerCase() + year + ".json");
            assertTrue(GoldMiner.toJsonFile(quarter, quarterFile));
        }
    }

    @Test
    @Ignore
    public void getAllData() {
        JSONArray allQuarters = cut.getOnlyQuarters();
        for (Object obj : allQuarters) {
            JSONObject jObj = (JSONObject) obj;
            String season = jObj.getString("season");
            String year = jObj.getString("year");
            String quarterStr = season + " " + year;
            JSONObject quarter = cut.getAllData(quarterStr);
            File quarterFile = new File(JSON_RESOURCE_DIR + season.toLowerCase() + year + ".json");
            assertTrue(GoldMiner.toJsonFile(quarter, quarterFile));
        }
    }
}
