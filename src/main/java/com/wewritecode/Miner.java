package com.wewritecode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Miner (a.k.a. GOLDMiner) utility is responsible for scraping a UCSB course website for course information.
 * Methods and fields included are all static, since only one instance of the Miner utility will be used.
 *
 * @author  Grant Clark
 * @version 1.0
 * @since   2019-04-17
 */
public class Miner {

    private static WebDriver driver;

    private static final String URL = "https://my.sa.ucsb.edu/public/curriculum/coursesearch.aspx";
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.drivers.driver";

    private ClassLoader temp = Miner.class.getClassLoader();

    private static final String PATH_TO_CHROME_DRIVER =
            System.getProperty("user.dir")+"/src/main/resources/drivers/chromedriver";
    //        Miner.class.getClassLoader().getResource("/drivers/chromedriver").getPath();
    // Change the below IDs if the html ids ever change.
    private static final String SUBJECT_ID = "ctl00_pageContent_courseList";
    private static final String QUARTER_ID = "ctl00_pageContent_quarterList";
    private static final String COURSE_LEVEL_ID = "ctl00_pageContent_dropDownCourseLevels";
    private static final String SEARCH_ID = "ctl00_pageContent_searchButton";



    // Initialization and closing methods for the GOLDMiner utility.
    // Call init before using any of the below methods.
    // Call close after GOLDMiner is finished scraping.

    /**
     * Initialize any private fields needed for GOLDMiner to operate.
     * Establishes connection to {@code URL}, so use the setUrl method before calling if you wish to connect to a
     * non-default URL.
     * MUST call before using get methods of GOLDMiner.
     */
    public static void init() {
        // Necessary property setting for ChromeDriver.
        System.setProperty(CHROME_DRIVER_PROPERTY, PATH_TO_CHROME_DRIVER);

        driver = new ChromeDriver();

        // Gets the html of the site requested.
        driver.get(URL);
    }

    /**
     * Closes the Chrome Driver.
     * (Should/Must) call after using GOLDMiner.
     */
    public static void close() {
        driver.close();
    }



    // Scraping Methods

    /**
     * Parses the course website for all current listed courses.
     *
     * @return JSONArray containing all the subjects listed on the course website.
     */
    public static JSONArray getSubjects() {
        String subject, fullName, symbol;

        JSONArray jsonArray = new JSONArray();

        for (WebElement option : new Select(driver.findElement(By.id(SUBJECT_ID))).getOptions()) {
            subject = option.getText();
            fullName = subject.substring(0, subject.indexOf('-')-1).trim();
            symbol = subject.substring(subject.indexOf("-")+1).trim();

            JSONObject jsonSubject = new JSONObject();
            jsonSubject.put("fullName", fullName);
            jsonSubject.put("symbol", symbol);

            jsonArray.add(jsonSubject);
        }

        return jsonArray;
    }

    /**
     * Parses the course website for all current listed quarters.
     *
     * @return JSONArray containing all the quarters listed on the course website.
     */
    public static JSONArray getQuarters() {
        String quarter, season, year;
        JSONArray quartersArray = new JSONArray();

        for (WebElement option : new Select(driver.findElement(By.id(QUARTER_ID))).getOptions()) {
            JSONObject quarterObj = new JSONObject();

            quarter = option.getText().trim();
            season = quarter.substring(0, quarter.indexOf(' '));
            year = quarter.substring(quarter.indexOf(' '));

            quarterObj.put("season", season);
            quarterObj.put("year", year);

            quartersArray.add(quarterObj);
        }

        return quartersArray;
    }

    /**
     * Returns a JSON object containing the current listed course levels.
     *
     * @return JSONObject containing all the course levels listed on the course website.
     */
    public static JSONObject getCourseLevels() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (WebElement option : new Select(driver.findElement(By.id(COURSE_LEVEL_ID))).getOptions()) {
            jsonArray.add(option.getText().trim());
        }

        jsonObject.put("courseLevels", jsonArray);

        return jsonObject;
    }

    public static JSONObject getAllCourses(String quarter) {
        JSONObject jsonObject = new JSONObject();
        JSONArray coursesArray = new JSONArray();

        // Search Button
        WebElement search = driver.findElement(By.id(SEARCH_ID));

        // Quarter Selection
        Select quarterMenu = new Select(driver.findElement(By.id(QUARTER_ID)));
        quarterMenu.selectByVisibleText(quarter.toUpperCase());

        // Course Level Selection (select "All")
        Select courseLevelMenu = new Select(driver.findElement(By.id(COURSE_LEVEL_ID)));
        courseLevelMenu.selectByVisibleText("All");

        // Subject List Menu
        Select subjectMenu = new Select(driver.findElement(By.id(SUBJECT_ID)));

        // Iterate over each option in the subjectMenu
        for (int i = 0; i < subjectMenu.getOptions().size(); i++) {
            // Navigate to one of the subjects, and click search
            subjectMenu.selectByIndex(i); // selects one of the subjects
            WebElement subject = subjectMenu.getOptions().get(i);
            String subjectName = subject.getText();
            String symbol = subjectName.substring(subjectName.indexOf("-")+1).trim();
            search.click();

            // Get the data from the individual subject area.
            getCoursesBySubjectArea(symbol);

        }

        return jsonObject;
    }

    /**
     *
     * @param subjectArea short symbol representing the subject area.
     * @return
     */
    public static JSONObject getCoursesBySubjectArea(String subjectArea) {
        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }


    /**
     * Formats a JSON object nicely into a file output.
     * @param jsonObject Object to convert to string and output into file (formatted nicely).
     * @param filename Target file name (existing or new) for the output.
     * @return True if it was able to write to the file, false if an IOException occurred.
     */
    public static boolean toJsonFile(JSONObject jsonObject, File filename) {
        // Creates a new instance of Gson with "pretty printing" enabled.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonObject.toJSONString());

        // Write to output file
        try {
            FileWriter file = new FileWriter(filename);
            file.write(gson.toJson(je));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
