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
// TODO: Implement and enable multi-threading
public class Miner {

    private static WebDriver driver;

    // Constants used by the Miner class to navigate.
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
    private static final String COURSE_TITLE_XPATH =
            "//*[contains(@id,'_HyperLinkPrimaryCourse')]";


    /**
     * Constructs a new Miner object.
     * Uses ChromeDriver to begin scraping the data.
     *
     * Change field: {@code PATH_TO_CHROME_DRIVER} if the ChromeDriver executable is located in a different
     * directory or location.
     */
    public Miner() {
        // Necessary property setting for ChromeDriver.
        System.setProperty(CHROME_DRIVER_PROPERTY, PATH_TO_CHROME_DRIVER);

        driver = new ChromeDriver();

        // Gets the html of the site requested.
        driver.get(URL);
    }

    /**
     * Overrides {@code finalize} to allow for closing the opened ChromeDriver application
     * before Java garbage collection.
     *
     * @throws Throwable the {@code Exception} raised by this method
     */
    @Override
    protected void finalize() throws Throwable {
        driver.close();
        super.finalize();
    }



    // Public Scraping Methods

    /**
     * Parses the course website for all current listed courses.
     *
     * @return JSONArray containing all the subjects listed on the course website.
     */
    public JSONArray getOnlySubjects() {
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
    public JSONArray getOnlyQuarters() {
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
    public JSONObject getOnlyCourseLevels() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (WebElement option : new Select(driver.findElement(By.id(COURSE_LEVEL_ID))).getOptions()) {
            jsonArray.put(option.getText().trim());
        }

        jsonObject.put("courseLevels", jsonArray);

        return jsonObject;
    }

    /**
     * Get all the subject/course information from the course website for a given quarter.
     *
     * @param quarter the quarter requested represented as a string (i.e. Spring 2019)
     * @return a JSONObject representing every piece of subject and course information for a given quarter.
     */
    public JSONObject getAllData(String quarter) {
        String extendedName, fullName, symbol;
        JSONObject subjectsObj = new JSONObject();

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
            JSONObject subjectObj = getAllCoursesFromSubject(fullName);
            subjectsObj.put(symbol, subjectObj); // Adds an individual subject to the group of subjects.
        }

        return subjectsObj;
    }

    // TODO: Implement another scraping method that can update an existing object based on what's changed.
    // However, this may not be needed if the scraping is fast enough, and if we will have to send the entire json
    // object to the client anyway rather than the changes, it's not needed.

    // getAllData Helper Method
    private JSONObject getAllCoursesFromSubject(String subjectName) {
        JSONObject subjectObj = new JSONObject();
        subjectObj.put("fullName", subjectName);

        JSONArray coursesArray = new JSONArray();

        // HTML element for the table containing the courses.
        WebElement courseTable;

        // If the course table does not exist, NoSuchElementException will be thrown.
        // This means that for that subject, there is NO course table, and thus no courses.
        // So we will just simply return a subjectObject containing the subject name and an
        // empty courses JSONArray.
        try {
            courseTable = driver.findElement(By.xpath(COURSE_TABLE_XPATH));
        } catch (NoSuchElementException e) {
            subjectObj.put("courses", coursesArray);
            return subjectObj;
        }

        // Session = Lecture or Section
        List<WebElement> sessions = courseTable.findElements(By.className("CourseInfoRow"));

        JSONObject courseObj = new JSONObject();
        JSONObject lecture = new JSONObject();

        // Populates the coursesArray with ALL data for that subject.
        String prevCourseID = "";
        for (WebElement session : sessions) {
            String currCourseID = getCourseID(session);

            if (isLecture(session)) {
                // Indicates if the previous session is the same course as the current session.
                boolean sameCourse = currCourseID.equals(prevCourseID);

                // If the lecture object is not empty, add it to the lecture array.
                if (!lecture.isEmpty()) {
                    JSONArray lectureArray = (JSONArray) lecture.get("lectures");
                    lectureArray.put(lecture);
                }

                // If this lecture is not the same lecture as the one that was just done,
                // put the current courseObj into the coursesArray, and make a new
                // instance of the courseObj to put the new data into.
                if (!sameCourse) {
                    // If the courseObj is empty, we don't want to add it to the courseArray.
                    if (!courseObj.isEmpty())
                        coursesArray.put(courseObj);
                    courseObj = getCourse(session);
                }

                // Replace the previous lecture with a new lecture.
                lecture = getLecture(session);
                prevCourseID = (String) lecture.get("courseID");
            } else {
                // Executes when the session is a section. Thus, add it to whatever the current lecture object is.
                JSONObject section = getSection(session);
                JSONArray sectionsArray = (JSONArray) lecture.get("sections");
                sectionsArray.put(section);
            }
        }

        // Last check. The last session that is retrieved can be section, not a lecture. Thus, since the previous
        // course has only been added to the coursesArray once you come across a new course/lecture, we must make
        // make sure the last courseObj in the table is added to the coursesArray.
        if (!courseObj.isEmpty())
            coursesArray.put(courseObj);

        return subjectObj;
    }



    // Lecture/Section Helper Methods

    private JSONObject getLecture(WebElement lecture) {
        JSONObject lectureObj = new JSONObject();

        // Get lecture-specific information
        lectureObj.put("status", getStatus(lecture));
        lectureObj.put("instructors", getInstructors(lecture));
        lectureObj.put("days", getDays(lecture));
        lectureObj.put("time", getTimes(lecture));
        lectureObj.put("location", getLocation(lecture));
        lectureObj.put("enrollment", getEnrollment(lecture));
        lectureObj.put("sections", new JSONArray());

        return lectureObj;
    }

    private JSONObject getSection(WebElement section) {
        JSONObject sectionObj = new JSONObject();

        // Get section-specific information
        sectionObj.put("status", getStatus(section));
        sectionObj.put("instructors", getInstructors(section));
        sectionObj.put("days", getDays(section));
        sectionObj.put("time", getTimes(section));
        sectionObj.put("location", getLocation(section));
        sectionObj.put("enrollment", getEnrollment(section));

        return sectionObj;
    }

    private String getStatus(WebElement element) {
        String status = element.findElement(By.className("Status")).getText().trim();
        return status;
    }

    private JSONArray getInstructors(WebElement element) {
        JSONArray instructorsArray = new JSONArray();
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String instructorData = allInfo.get(42).getText();
        String[] instructors = instructorData.split("\\r?\\n");
        for (String instructor : instructors) {
            instructorsArray.put(instructor);
        }
        return instructorsArray;
    }

    private JSONArray getDays(WebElement element) {
        JSONArray daysArray = new JSONArray();
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String days = allInfo.get(43).getText().replaceAll("\\s","");
        for (char c : days.toCharArray()) {
            daysArray.put("" + c);
        }
        return daysArray;
    }

    private JSONObject getTimes(WebElement element) {
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

    private int getHour(String time) {
        int hour = Integer.parseInt(time.substring(0,time.indexOf(":")));
        if (time.substring(time.length() - 2).toLowerCase().equals("pm") && hour != 12)
            hour += 12;
        return hour;
    }

    private int getMinute(String time) {
        return Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length() - 2).trim());
    }

    private String getLocation(WebElement element) {
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String location = allInfo.get(45).getText().trim();
        return location;
    }

    private JSONObject getEnrollment(WebElement element) {
        int current, max;
        JSONObject enrollmentObj = new JSONObject();
        List<WebElement> allInfo = element.findElements(By.tagName("td"));
        String enrollment = allInfo.get(46).getText().trim();
        try {
            current = Integer.parseInt(enrollment.substring(0, enrollment.indexOf("/")).trim());
            max = Integer.parseInt(enrollment.substring(enrollment.indexOf("/") + 2));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("ERROR at element: " + element.findElement(By.id("CourseTitle")).getText().trim());
            current = 0;
            max = 0;
        }

        enrollmentObj.put("current", current);
        enrollmentObj.put("max", max);

        return enrollmentObj;
    }



    // Course Helper Methods

    private JSONObject getCourse(WebElement session) {
        JSONObject courseObj = new JSONObject();

        courseObj.put("courseID", getCourseID(session));
        courseObj.put("title", getCourseTitle(session));
        courseObj.put("fullTitle", getCourseFullTitle(session));
        courseObj.put("description", getCourseDescription(session));
        courseObj.put("prerequisites", getCoursePrerequisites(session));
        courseObj.put("college", getCourseCollege(session));
        courseObj.put("units", getCourseUnits(session));
        courseObj.put("gradingOptions", getCourseGradingOptions(session));
        courseObj.put("lectures", new JSONArray());

        return courseObj;
    }

    private String getCourseID(WebElement session) {
        return session.findElement(By.id("CourseTitle")).getText().trim();
    }

    private String getCourseTitle(WebElement session) {
        return session.findElement(By.xpath(COURSE_TITLE_XPATH)).getText().trim();
    }

    private String getCourseFullTitle(WebElement element) {
        WebElement fullTitle = element.findElement(By.xpath("//*[contains(@id,'_labelTitle')]"));
        return fullTitle.getAttribute("innerHTML").trim();
    }

    private String getCourseDescription(WebElement element) {
        WebElement description = element.findElement(By.xpath("//*[contains(@id,'_labelDescription')]"));
        return description.getAttribute("innerHTML").trim();
    }

    private String getCoursePrerequisites(WebElement element) {
        WebElement prereqs = element.findElement(By.xpath("//*[contains(@id,'_labelPreReqComment')]"));
        return prereqs.getAttribute("innerHTML").trim();
    }

    private String getCourseCollege(WebElement element) {
        WebElement college = element.findElement(By.xpath("//*[contains(@id,'_labelCollege')]"));
        String collegeStr = college.getAttribute("innerHTML").trim();
        if (collegeStr.equals("L&amp;S"))
            collegeStr = "L&S";
        return collegeStr;
    }

    private JSONObject getCourseUnits(WebElement element) {
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

    private String getCourseGradingOptions(WebElement element) {
        WebElement options = element.findElement(By.xpath("//*[contains(@id,'_labelQuarter')]"));
        return options.getAttribute("innerHTML");
    }

    private boolean isLecture(WebElement session) {
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
