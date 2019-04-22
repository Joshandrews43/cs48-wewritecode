package com.wewritecode;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
    private static final String PATH_TO_CHROME_DRIVER =
            System.getProperty("user.dir")+"/src/main/resources/drivers/chromedriver";

    private static final String SUBJECT_ID = "ctl00_pageContent_courseList";
    private static final String QUARTER_ID = "ctl00_pageContent_quarterList";
    private static final String COURSE_LEVEL_ID = "ctl00_pageContent_dropDownCourseLevels";
    private static final String SEARCH_ID = "ctl00_pageContent_searchButton";

    private static final String COURSE_TABLE_XPATH =
            "//*[@id=\"aspnetForm\"]/table/tbody/tr[3]/td/div/center/table/tbody";



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
    public static JSONArray getOnlySubjects() {
        String subject, fullName, symbol;

        JSONArray jsonArray = new JSONArray();

        for (WebElement option : new Select(driver.findElement(By.id(SUBJECT_ID))).getOptions()) {
            subject = option.getText().replaceAll("^ +| +$|( )+", "$1");
            fullName = subject.substring(0, subject.indexOf('-')-1);
            symbol = subject.substring(subject.indexOf("-")+2);

            JSONObject jsonSubject = new JSONObject();
            jsonSubject.put("fullName", fullName);
            jsonSubject.put("symbol", symbol);

            jsonArray.put(jsonSubject);
        }

        return jsonArray;
    }

    /**
     * Parses the course website for all current listed quarters.
     *
     * @return JSONArray containing all the quarters listed on the course website.
     */
    public static JSONArray getOnlyQuarters() {
        String quarter, season, year;
        JSONArray quartersArray = new JSONArray();

        for (WebElement option : new Select(driver.findElement(By.id(QUARTER_ID))).getOptions()) {
            JSONObject quarterObj = new JSONObject();

            quarter = option.getText().trim();
            season = quarter.substring(0, quarter.indexOf(' '));
            year = quarter.substring(quarter.indexOf(' '));

            quarterObj.put("season", season);
            quarterObj.put("year", year);

            quartersArray.put(quarterObj);
        }

        return quartersArray;
    }

    /**
     * Returns a JSON object containing the current listed course levels.
     *
     * @return JSONObject containing all the course levels listed on the course website.
     */
    public static JSONObject getOnlyCourseLevels() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (WebElement option : new Select(driver.findElement(By.id(COURSE_LEVEL_ID))).getOptions()) {
            jsonArray.put(option.getText().trim());
        }

        jsonObject.put("courseLevels", jsonArray);

        return jsonObject;
    }

    public static JSONObject getAllSubjects(String quarter) {
        String extendedName, fullName, symbol;

        JSONObject subjectsObj = new JSONObject();
        JSONArray coursesArray = new JSONArray();

        // Search Button
        WebElement search = driver.findElement(By.id(SEARCH_ID));

        // Quarter Selector
        Select quarterMenu = new Select(driver.findElement(By.id(QUARTER_ID)));
        quarterMenu.selectByVisibleText(quarter.toUpperCase());

        // Course Level Selection (selects "All")
        Select courseLevelMenu = new Select(driver.findElement(By.id(COURSE_LEVEL_ID)));
        courseLevelMenu.selectByVisibleText("All");

        // Subject Selector
        Select subjectMenu = new Select(driver.findElement(By.id(SUBJECT_ID)));
        List<WebElement> subjectOptions = subjectMenu.getOptions();

        // Iterate over each subject/option in the subjectMenu
        for (int i = 0; i < subjectOptions.size(); i++) {
            // Re-get the subject selection menu
            // (for some reason, when it navigates to a new page, it errors without this)
            subjectMenu = new Select(driver.findElement(By.id(SUBJECT_ID)));
            // Select one of the subjects, and click search
            WebElement subject = subjectOptions.get(i);
            subjectMenu.selectByIndex(i);
            extendedName = subject.getText().replaceAll("^ +| +$|( )+", "$1"); // i.e. "Anthropology - ANTH"
            fullName = extendedName.substring(0, extendedName.indexOf("-")-1); // i.e. "Anthropology"
            symbol = extendedName.substring(extendedName.indexOf("-")+2); // i.e. "ANTH"
            search.click();

            System.out.println(fullName);

            // Get the data from the individual subject area.
            // JSONObject subjectObj = getCoursesBySubjectArea(fullName);
            // subjectsObj.put(symbol, subjectObj); // Adds an individual subject to the group of subjects.
        }

        return subjectsObj;
    }



//    private static JSONObject getCoursesBySubjectArea_REVISED(String subjectName) {
//        JSONObject subjectObj = new JSONObject();
//        subjectObj.put("fullname", subjectName);
//
//        JSONArray coursesArray = new JSONArray();
//
//        WebElement courseTable = driver.findElement(By.xpath(COURSE_TABLE_XPATH));
//
//        List<WebElement> sessions = courseTable.findElements(By.className("CourseInfoRow"));
//
//        JSONObject lecture = new JSONObject();
//
//        for (WebElement session : sessions) {
//            if (isLecture(session)) {
//                if (!lecture.isEmpty()) {
//
//                }
//                getLecture(session);
//            } else {
//                getSection(session);
//            }
//        }
//
//    }
//
//    private static JSONObject getCourse(WebElement course) {
//        if (isLecture(course))
//    }
//
//    private static JSONObject getLecture(WebElement lecture) {
//
//    }
//
//    private static JSONObject getSection(WebElement section) {
//
//    }


    /**
     * Gets all the courses of a certain subject area, and returns a JSONObject with such information.
     *
     * @param subjectName Full subject name, without symbol (i.e. "Anthropology")
     * @return JSONObject representing the course.
     */
    public static JSONObject getCoursesBySubjectArea(String subjectName) {
        // TODO: Split function into multiple function calls for readability.
        JSONObject subjectObj = new JSONObject();
        subjectObj.put("fullName", subjectName);

        JSONArray coursesArray = new JSONArray();

        WebElement courseTable = driver.findElement(By.xpath(COURSE_TABLE_XPATH));

        List<WebElement> sessions = courseTable.findElements(By.className("CourseInfoRow"));

        int currentCount = 0;
        while (currentCount < sessions.size() - 1) {
            JSONObject courseObj = new JSONObject();
            WebElement session = sessions.get(currentCount);
            List<WebElement> lectureInfo = session.findElements(By.cssSelector(".Header.Clickable"));

            String courseID = session.findElement(By.id("CourseTitle")).getText().trim();
            courseObj.put("courseID", courseID);
            courseObj.put("title", lectureInfo.get(2).getText());

            // TODO: Get/set full title, description, prerequisites, college, units, and grading options for other html.
            String prev = courseID;
            JSONArray lecturesArray = new JSONArray();

            // Executes as long as the current courseID matches the previous courseID,
            // and we haven't yet reached the end of the table.
            // If above is true, then keep adding the next session to either the
            // lectures or sections list for that course.
            // Continue until the courseID is not the same as the previous courseID,
            // indicating we need to create a new course object.
            JSONObject lectureObj = new JSONObject();
            JSONArray sectionsArray = new JSONArray();
            while (courseID.equals(prev) && currentCount < sessions.size() - 1) {
                if (isLecture(session)) {

                    // If the current lecture object has information in it, add the sections array to it, then
                    // add the lecture object to the lecture array before creating a new lecture.
                    if (!lectureObj.isEmpty()) {
                        lectureObj.put("sections", sectionsArray);
                        lecturesArray.put(lectureObj);
                    }

                    lectureObj = new JSONObject();
                    sectionsArray = new JSONArray();

                    // TODO: Get/set status, instructor, days, time, location, enrollment
                    // REPLACE ME
                    lectureObj.put("lectureInfo", "some lecture info");


                } else {
                    JSONObject sectionObj = new JSONObject();
                    // TODO: Get/set status, instructor, days, time, location, enrollment
                    // REPLACE ME
                    sectionObj.put("sectionInfo", "some section info");
                    sectionsArray.put(sectionObj);
                }

                currentCount++;
                // Get the next lecture or section
                session = sessions.get(currentCount);
                // Set the courseID for the next lecture or section
                courseID = session.findElement(By.id("CourseTitle")).getText().trim();
            }

            // Course has been populated with all information, now add to courses array.
            coursesArray.put(courseObj);
        }

        // All courses have been added to the courses array, now add to subject object.
        subjectObj.put("courses", coursesArray);

        return subjectObj;

            /*
                int currentCount = 0;
                While currentCount < sessions.size()

                    Construct new course JSONObject
                        set courseID, title, fullTitle, description
                        prev = courseID
                        construct new lectures JSONArray

                    While the courseID == prev and currentCount < sessions.size() (indicates same course, different lecture/section)
                        If session is a lecture
                            add current non-empty lecture JSONObject to lectures JSONArray
                            Construct new lecture JSONObject
                            set status, instructor, days, time, location, enrollment
                            construct new sections JSONArray
                        Else
                            Construct new section JSONObject
                            set status, instructor, days, time, location, enrollment
                            add section JSONObject to sections JSONArray

                        session = sessions.get(currentCount++);
                        courseID = session.findElements(By.cssSelector(".Header.Clickable").get(1).getText();

                    add course JSONObject (full) to coursesArray
                add coursesArray to subjectObj
             */
    }

    private static String getStatus(WebElement element) {
        // TODO: Implement
        return "";
    }

    private static JSONArray getInstructors(WebElement element) {
        JSONArray instructorsArray = new JSONArray();
        // TODO: Implement
        return instructorsArray;
    }

    private static String getDays(WebElement element) {
        // TODO: Implement
        return "";
    }

    private static String getLocation(WebElement element) {
        // TODO: Implement
        return "";
    }

    private static String getEnrollment(WebElement element) {
        // TODO: Implement
        return "";
    }


    /**
     * Find whether or not a given WebElement is a lecture or a section.
     *
     * @param session WebElement that may be a lecture or a section.
     * @return true - if session is a lecture
     *         false - if it is a section.
     */
    private static boolean isLecture(WebElement session) {
        try {
            session.findElement(By.cssSelector(".PrimaryCourse.Header.Clickable"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
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
        JsonElement je = jp.parse(jsonObject.toString());

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
