package com.wewritecode;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Test class for the com.wewritecode.Miner class.
 *
 * @author  Grant Clark
 * @version 1.0
 * @since   2019-04-17
 */
public class MinerTest {

    // Component Under Test (CUT)
    private static Miner cut;

    private static final String JSON_RESOURCE_DIR = System.getProperty("user.dir")+"/src/test/resources/jsons/";

    @Before
    public static void setup() {
        cut = new Miner();
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
    public void getAllSubjectsTest() {
        long startTime = System.currentTimeMillis();
        JSONObject allSubjects = Miner.getAllData("Spring 2019");
        long endTime = System.currentTimeMillis();

        File quarterFile = new File(JSON_RESOURCE_DIR + "spring2019.json");

        System.out.println("Time in seconds: " + ((endTime - startTime) / 1000));
        assertTrue(Miner.toJsonFile(allSubjects, quarterFile));
    }

    @Test
    public void getAllCurrentData(){
        long startTime = System.currentTimeMillis();
        JSONArray allQuarters = Miner.getOnlyQuarters();
        for (int i = 0; i < 3; i++) {
            JSONObject jObj = (JSONObject) allQuarters.get(i);
            String season = (String) jObj.get("season");
            String year = (String) jObj.get("year");
            String quarterStr = season + " " + year;
            JSONObject quarter = Miner.getAllData(quarterStr);
            File quarterFile = new File(JSON_RESOURCE_DIR + season.toLowerCase() + year + ".json");
            assertTrue(Miner.toJsonFile(quarter, quarterFile));
        }
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;

        System.out.println(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(elapsed),
                TimeUnit.MILLISECONDS.toSeconds(elapsed)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed))
        ));
    }

    @Test
    public void getAllData() {
        long startTime = System.currentTimeMillis();
        JSONArray allQuarters = Miner.getOnlyQuarters();
        for (Object obj : allQuarters) {
            JSONObject jObj = (JSONObject) obj;
            String season = (String) jObj.get("season");
            String year = (String) jObj.get("year");
            String quarterStr = season + " " + year;
            JSONObject quarter = Miner.getAllData(quarterStr);
            File quarterFile = new File(JSON_RESOURCE_DIR + season.toLowerCase() + year + ".json");
            assertTrue(Miner.toJsonFile(quarter, quarterFile));
        }
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;

        System.out.println(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(elapsed),
                TimeUnit.MILLISECONDS.toSeconds(elapsed)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed))
        ));
    }
}
