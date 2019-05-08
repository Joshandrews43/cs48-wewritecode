package com.wewritecode.scheduler;


import com.wewritecode.pan.schedule.*;
import com.wewritecode.pan.scheduler.BruteForceScheduler;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for the com.wewritecode.miner.GoldMiner class.
 *
 * @author  Alan Roddick
 * @version 1.1
 * @since   2019-05-07
 */

public class BruteForceSchedulerTest {

    private BruteForceScheduler scheduler;

    @Before
    public void setup() { scheduler = new BruteForceScheduler(); }

    @Test
    public void scheduleGenTest() {
        List<String> lectureCS64Days = new ArrayList<>();
        lectureCS64Days.add("M");
        lectureCS64Days.add("W");
        Time lectureCS64Time = new Time(12, 30, 13, 45);
        List<String> section1CS64Days = new ArrayList<>();
        section1CS64Days.add("F");
        Time section1CS64Time = new Time(11, 0, 11, 50);
        List<String> section2CS64Days = new ArrayList<>();
        section2CS64Days.add("F");
        Time section2CS64Time = new Time(12, 0, 12, 50);
        List<String> section3CS64Days = new ArrayList<>();
        section3CS64Days.add("F");
        Time section3CS64Time = new Time(13, 0, 13, 50);
        Section section1CS64 = new Section(section1CS64Days, section1CS64Time);
        Section section2CS64 = new Section(section2CS64Days, section2CS64Time);
        Section section3CS64 = new Section(section3CS64Days, section3CS64Time);
        Lecture CS64Lecture = new Lecture(lectureCS64Days, lectureCS64Time);
        CS64Lecture.addSection(section1CS64);
        CS64Lecture.addSection(section2CS64);
        CS64Lecture.addSection(section3CS64);
        Course CS64 = new Course("CS64");
        CS64.addLecture(CS64Lecture);

        List<String> lectureCS130ADays = new ArrayList<>();
        lectureCS130ADays.add("M");
        lectureCS130ADays.add("W");
        Time lectureCS130ATime = new Time(11, 0, 12, 15);
        List<String> section1CS130ADays = new ArrayList<>();
        section1CS130ADays.add("F");
        Time section1CS130ATime = new Time(9, 0, 9, 50);
        List<String> section2CS130ADays = new ArrayList<>();
        section2CS130ADays.add("F");
        Time section2CS130ATime = new Time(10, 0, 10, 50);
        Section section1CS130A = new Section(section1CS130ADays, section1CS130ATime);
        Section section2CS130A = new Section(section2CS130ADays, section2CS130ATime);
        Lecture CS130ALecture = new Lecture(lectureCS130ADays, lectureCS130ATime);
        CS130ALecture.addSection(section1CS130A);
        CS130ALecture.addSection(section2CS130A);
        Course CS130A = new Course("CS130A");
        CS130A.addLecture(CS130ALecture);

        List<String> lectureCS138Days = new ArrayList<>();
        lectureCS138Days.add("M");
        lectureCS138Days.add("W");
        Time lectureCS138Time = new Time(9, 30, 10, 45);
        List<String> section1CS138Days = new ArrayList<>();
        section1CS138Days.add("F");
        Time section1CS138Time = new Time(11, 0, 11, 50);
        List<String> section2CS138Days = new ArrayList<>();
        section2CS138Days.add("F");
        Time section2CS138Time = new Time(12, 0, 12, 50);
        Section section1CS138 = new Section(section1CS138Days, section1CS138Time);
        Section section2CS138 = new Section(section2CS138Days, section2CS138Time);
        Lecture CS138Lecture = new Lecture(lectureCS138Days, lectureCS138Time);
        CS138Lecture.addSection(section1CS138);
        CS138Lecture.addSection(section2CS138);
        Course CS138 = new Course("CS138");
        CS138.addLecture(CS138Lecture);

        List<Course> requestCourses = new ArrayList<>();
        requestCourses.add(CS64);
        requestCourses.add(CS130A);
        requestCourses.add(CS138);
        ScheduleRequest request = new ScheduleRequest();
        request.setMandatory(requestCourses);

        ScheduleResponse response = scheduler.generate(request);

        List<Schedule> schedules = response.getSchedules();;
        assertEquals(8, schedules.size());
    }
}
