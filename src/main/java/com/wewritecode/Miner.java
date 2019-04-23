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
            year = quarter.substring(quarter.indexOf(' ') + 1);

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
            subjectOptions = subjectMenu.getOptions();

            // Select one of the subjects, and click search
            WebElement subject = subjectOptions.get(i);
            subjectMenu.selectByIndex(i);
            extendedName = subject.getText().replaceAll("^ +| +$|( )+", "$1"); // i.e. "Anthropology - ANTH"
            fullName = extendedName.substring(0, extendedName.indexOf("-")-1); // i.e. "Anthropology"
            symbol = extendedName.substring(extendedName.indexOf("-")+2); // i.e. "ANTH"

            // Search Button
            WebElement search = driver.findElement(By.id(SEARCH_ID));
            search.click();

            // Get the data from the individual subject area.
            JSONObject subjectObj = getCoursesBySubjectArea(fullName);
            subjectsObj.put(symbol, subjectObj); // Adds an individual subject to the group of subjects.
        }

        return subjectsObj;
    }

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

        WebElement courseTable;
        try {
            courseTable = driver.findElement(By.xpath(COURSE_TABLE_XPATH));
        } catch (NoSuchElementException e) {
            return subjectObj;
        }

        List<WebElement> sessions = courseTable.findElements(By.className("CourseInfoRow"));

        int currentCount = 0;
        while (currentCount < sessions.size() - 1) {
            JSONObject courseObj = new JSONObject();
            WebElement session = sessions.get(currentCount);
            List<WebElement> lectureInfo = session.findElements(By.cssSelector(".Header.Clickable"));

            String courseID = session.findElement(By.id("CourseTitle")).getText().trim();
            courseObj.put("courseID", courseID);
            courseObj.put("title", lectureInfo.get(2).getText());
            courseObj.put("fullTitle", getFullTitle(session));
            courseObj.put("description", getDescription(session));
            courseObj.put("college", getCollege(session));
            courseObj.put("units", getUnits(session));
            courseObj.put("gradingOptions", getGradingOptions(session));

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

                    // Get lecture-specific information
                    lectureObj.put("status", getStatus(session));
                    lectureObj.put("instructors", getInstructors(session));
                    lectureObj.put("days", getDays(session));
                    lectureObj.put("time", getTimes(session));
                    lectureObj.put("location", getLocation(session));
                    lectureObj.put("enrollment", getEnrollment(session));


                } else {
                    // Get section-specific information
                    JSONObject sectionObj = new JSONObject();
                    sectionObj.put("status", getStatus(session));
                    sectionObj.put("instructors", getInstructors(session));
                    sectionObj.put("days", getDays(session));
                    sectionObj.put("time", getTimes(session));
                    sectionObj.put("location", getLocation(session));
                    sectionObj.put("enrollment", getEnrollment(session));
                    sectionsArray.put(sectionObj);
                }

                currentCount++;
                // Get the next lecture or section
                session = sessions.get(currentCount);
                // Set the courseID for the next lecture or section
                courseID = session.findElement(By.id("CourseTitle")).getText().trim();
            }

            if (!lectureObj.isEmpty()) {
                lectureObj.put("sections", sectionsArray);
                lecturesArray.put(lectureObj);
            }
            courseObj.put("lectures", lecturesArray);

            // Course has been populated with all information, now add to courses array.
            coursesArray.put(courseObj);
        }

        // All courses have been added to the courses array, now add to subject object.
        subjectObj.put("courses", coursesArray);

        return subjectObj;
    }

    private static String getFullTitle(WebElement element) {
        WebElement fullTitle = element.findElement(By.xpath("//*[contains(@id,'_labelTitle')]"));
        return fullTitle.getAttribute("innerHTML").trim();
    }

    private static String getDescription(WebElement element) {
        WebElement description = element.findElement(By.xpath("//*[contains(@id,'_labelDescription')]"));
        return description.getAttribute("innerHTML").trim();
    }

    private static String getPrerequisites(WebElement element) {
        WebElement prereqs = element.findElement(By.xpath("//*[contains(@id,'_labelPreReqComment')]"));
        return prereqs.getAttribute("innerHTML").trim();
    }

    private static String getCollege(WebElement element) {
        WebElement college = element.findElement(By.xpath("//*[contains(@id,'_labelCollege')]"));
        String collegeStr = college.getAttribute("innerHTML").trim();
        if (collegeStr.equals("L&amp;S"))
            collegeStr = "L&S";
        return collegeStr;
    }

    private static JSONObject getUnits(WebElement element) {
        JSONObject unitsObj = new JSONObject();
        WebElement units = element.findElement(By.xpath("//*[contains(@id,'_labelUnits')]"));
        String unitsStr = units.getAttribute("innerHTML");

        double minUnits, maxUnits;
        try {
            minUnits = Double.parseDouble(unitsStr.substring(0, unitsStr.indexOf("-") - 1));
            maxUnits = Double.parseDouble(unitsStr.substring(unitsStr.indexOf("-") + 2));
        } catch (StringIndexOutOfBoundsException e) {
            minUnits = Double.parseDouble(unitsStr);
            maxUnits = minUnits;
        }

        unitsObj.put("min", minUnits);
        unitsObj.put("max", maxUnits);

        return unitsObj;
    }

    private static String getGradingOptions(WebElement element) {
        WebElement options = element.findElement(By.xpath("//*[contains(@id,'_labelQuarter')]"));
        return options.getAttribute("innerHTML");
    }

    private static String getStatus(WebElement element) {
        String status = element.findElement(By.className("Status")).getText().trim();
        return status;
    }

    private static JSONArray getInstructors(WebElement element) {
        JSONArray instructorsArray = new JSONArray();
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String instructorData = allInfo.get(42).getText();
        String[] instructors = instructorData.split("\\r?\\n");
        for (String instructor : instructors) {
            instructorsArray.put(instructor);
        }
        return instructorsArray;
    }

    private static JSONArray getDays(WebElement element) {
        JSONArray daysArray = new JSONArray();
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String days = allInfo.get(43).getText().replaceAll("\\s","");
        for (char c : days.toCharArray()) {
            daysArray.put("" + c);
        }
        return daysArray;
    }

    private static JSONObject getTimes(WebElement element) {
        JSONObject timesObj = new JSONObject();
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String timeData = allInfo.get(44).getText();
        if (timeData.isEmpty())
            return timesObj;
        String startString = timeData.substring(0, timeData.indexOf("-")).trim();
        String endString = timeData.substring(timeData.indexOf("-") + 2).trim();

        int startHour = getHour(startString);
        int startMin = getMinute(startString);
        int endHour = getHour(endString);
        int endMin = getMinute(endString);

        JSONObject startObj = new JSONObject();
        startObj.put("hour", startHour);
        startObj.put("minute", startMin);
        JSONObject endObj = new JSONObject();
        endObj.put("hour", endHour);
        endObj.put("minute", endMin);

        timesObj.put("start", startObj);
        timesObj.put("end", endObj);

        return timesObj;
    }

    private static int getHour(String time) {
        int hour = Integer.parseInt(time.substring(0,time.indexOf(":")));
        if (time.substring(time.length() - 2).toLowerCase().equals("pm") && hour != 12)
            hour += 12;
        return hour;
    }

    private static int getMinute(String time) {
        return Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length() - 2).trim());
    }

    private static String getLocation(WebElement element) {
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String location = allInfo.get(45).getText().trim();
        return location;
    }

    private static JSONObject getEnrollment(WebElement element) {
        JSONObject enrollmentObj = new JSONObject();
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String enrollment = allInfo.get(46).getText().trim();

        int current = Integer.parseInt(enrollment.substring(0, enrollment.indexOf("/")).trim());
        int max = Integer.parseInt(enrollment.substring(enrollment.indexOf("/") + 2));

        enrollmentObj.put("current", current);
        enrollmentObj.put("max", max);

        return enrollmentObj;
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
