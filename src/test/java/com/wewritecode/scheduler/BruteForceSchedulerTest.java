package com.wewritecode.scheduler;


import com.wewritecode.pan.schedule.*;
import com.wewritecode.pan.scheduler.BruteForceScheduler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        String[] lectureCS64Days = {"M", "W"};
        Time lectureCS64Start = new Time(12, 30);
        Time lectureCS64End = new Time(13, 45);
        String[] section1CS64Days = {"F"};
        Time section1CS64Start = new Time(11, 0);
        Time section1CS64End = new Time(11, 50);
        String[] section2CS64Days = {"F"};
        Time section2CS64Start = new Time(12, 0);
        Time section2CS64End = new Time(12, 50);
        String[] section3CS64Days = {"F"};
        Time section3CS64Start = new Time(13, 0);
        Time section3CS64End = new Time(13, 50);
        Section section1CS64 = new Section(section1CS64Days, section1CS64Start, section1CS64End);
        Section section2CS64 = new Section(section2CS64Days, section2CS64Start, section2CS64End);
        Section section3CS64 = new Section(section3CS64Days, section3CS64Start, section3CS64End);
        Lecture CS64Lecture = new Lecture(lectureCS64Days, lectureCS64Start, lectureCS64End);
        CS64Lecture.addSection(section1CS64);
        CS64Lecture.addSection(section2CS64);
        CS64Lecture.addSection(section3CS64);
        Course CS64 = new Course("CS64");
        CS64.addLecture(CS64Lecture);

        String[] lectureCS130ADays = {"M", "W"};
        Time lectureCS130AStart = new Time(12, 0);
        Time lectureCS130AEnd = new Time(13, 45);
        String[] section1CS130ADays = {"F"};
        Time section1CS130AStart = new Time(9, 0);
        Time section1CS130AEnd = new Time(9, 50);
        String[] section2CS130ADays = {"F"};
        Time section2CS130AStart = new Time(10, 0);
        Time section2CS130AEnd = new Time(10, 50);
        Section section1CS130A = new Section(section1CS130ADays, section1CS130AStart, section1CS130AEnd);
        Section section2CS130A = new Section(section2CS130ADays, section2CS130AStart, section2CS130AEnd);
        Lecture CS130ALecture = new Lecture(lectureCS130ADays, lectureCS130AStart, lectureCS130AEnd);
        CS130ALecture.addSection(section1CS130A);
        CS130ALecture.addSection(section2CS130A);
        Course CS130A = new Course("CS130A");
        CS130A.addLecture(CS130ALecture);

        scheduler.addToRemainingCourses(CS64);
        scheduler.addToRemainingCourses(CS130A);
        scheduler.generate();

        List<Schedule> schedules = scheduler.getFullSchedules();
        System.out.println(schedules.size());
    }


}
