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
    private static String url = "https://my.sa.ucsb.edu/public/curriculum/coursesearch.aspx";
    private static String chromeDriverLocation = "~/Desktop/chromedriver";

    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";


    /**
     * Initialize any private fields needed for GOLDMiner to operate.
     * MUST call before using GOLDMiner.
     */
    public static void init() {
        driver = new ChromeDriver();
        System.setProperty(CHROME_DRIVER_PROPERTY, chromeDriverLocation);
    }

    /**
     * Closes the Chrome Driver.
     * (Should/Must) call after using GOLDMiner.
     */
    public static void close() {
        driver.close();
    }

    /**
     * Sets the url for the GOLDMiner to scrape from.
     *
     * @param courseUrl Course website URL.
     *                  If not called, the url will default to:
     *                  "https://my.sa.ucsb.edu/public/curriculum/coursesearch.aspx"
     */
    public static void setUrl(String courseUrl) {
        url = courseUrl;
    }

    /**
     * Sets the location of the Chrome Driver executable.
     *
     * @param location Path to executable.
     *                 If not called, the location will default to: "~/Desktop/chromedriver"
     */
    public static void setChromeDriverLocation(String location) {
        chromeDriverLocation = location;
    }

    /**
     * Parses the course website for all current listed courses.
     *
     * @param subjectID String representation of the html id used to reference the subject area list.
     * @return JSONObject containing all the subjects listed on the course website.
     */
    public static JSONObject getSubjects(String subjectID) {
        String subject, fullName, symbol;

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        // Gets the html of the site requested.
        driver.get(url);

        for (WebElement option : new Select(driver.findElement(By.id(subjectID))).getOptions()) {
            subject = option.getText();
            fullName = subject.substring(0, subject.indexOf('-')-1).trim();
            symbol = subject.substring(subject.indexOf("-")+1).trim();

            JSONObject jsonSubject = new JSONObject();
            jsonSubject.put("fullName", fullName);
            jsonSubject.put("symbol", symbol);

            jsonArray.add(jsonSubject);
        }
        jsonObject.put("subjects", jsonArray);

        return jsonObject;
    }

    /**
     * Parses the course website for all current listed courses into a JSONObject.
     *
     * @return JSONObject containing all the subjects listed on the course website.
     */
    public static JSONObject getSubjects() {
        return getSubjects("ctl00_pageContent_courseList");
    }

    /**
     * Parses the course website for all current listed quarters into a JSONObject.
     *
     * @param quarterID String representation of the html id used to reference the quarter list.
     * @return JSONObject containing all the quarters listed on the course website.
     */
    public static JSONObject getQuarters(String quarterID) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        // Gets the html of the site requested.
        driver.get(url);

        for (WebElement option : new Select(driver.findElement(By.id(quarterID))).getOptions()) {
            jsonArray.add(option.getText().trim());
        }

        jsonObject.put("quarters", jsonArray);

        return jsonObject;
    }

    /**
     * Parses the course website for all current listed quarters into a JSONObject.
     *
     * @return JSONObject containing all the quarters listed on the course website.
     */
    public static JSONObject getQuarters() {
        return getQuarters("ctl00_pageContent_quarterList");
    }

    /**
     * Formats a JSON object nicely into a file output.
     * @param jsonObject Object to convert to string and output into file (formatted nicely).
     * @param filename Target file name (existing or new) for the output.
     * @return True if it was able to write to the file, false if an IOException occurred.
     */
    public static boolean toJsonFile(JSONObject jsonObject, String filename) {
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
