/**
 * @author Alan Roddick
 */
package com.wewritecode.filter;

import com.wewritecode.pan.filter.DayFilter;
import com.wewritecode.pan.filter.GapsFilter;
import com.wewritecode.pan.filter.InvalidFilterOptionException;
import com.wewritecode.pan.schedule.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class GapsFilterTest {

    private GapsFilter gapsFilter;
    private Schedule schedule;

    @Before
    public void setup() {
        gapsFilter = new GapsFilter();
        schedule = new Schedule();

        List<String> lectureCS64Days = new ArrayList<>();
        lectureCS64Days.add("M");
        lectureCS64Days.add("R");
        Time lectureCS64Time = new Time(12, 30, 13, 45);
        List<String> section1CS64Days = new ArrayList<>();
        section1CS64Days.add("F");
        Time section1CS64Time = new Time(11, 0, 11, 50);
        Section section1CS64 = new Section(section1CS64Days, section1CS64Time);
        Lecture CS64Lecture = new Lecture(lectureCS64Days, lectureCS64Time);
        CS64Lecture.addSection(section1CS64);
        Course CS64 = new Course("CS64");
        CS64.addLecture(CS64Lecture);

        List<String> lectureCS130ADays = new ArrayList<>();
        lectureCS130ADays.add("M");
        lectureCS130ADays.add("W");
        Time lectureCS130ATime = new Time(11, 0, 12, 15);
        List<String> section1CS130ADays = new ArrayList<>();
        section1CS130ADays.add("F");
        Time section1CS130ATime = new Time(9, 0, 9, 50);
        Section section1CS130A = new Section(section1CS130ADays, section1CS130ATime);
        Lecture CS130ALecture = new Lecture(lectureCS130ADays, lectureCS130ATime);
        CS130ALecture.addSection(section1CS130A);
        Course CS130A = new Course("CS130A");
        CS130A.addLecture(CS130ALecture);
        schedule.addToSchedule(CS64);
        schedule.addToSchedule(CS130A);
    }

    @Test
    public void dayFilterTestMinimize() throws InvalidFilterOptionException {
        gapsFilter.setOption("Minimize Gaps");
        double fitness = 0;
        fitness = gapsFilter.getFitness(schedule);
        assertEquals(0.25, fitness);
    }

//    @Test
//    public void dayFilterTestMaximize() throws InvalidFilterOptionException {
//        dayFilter.setOption("Maximize Days");
//        double fitness = 0;
//        fitness = dayFilter.getFitness(schedule);
//        assertEquals(0.75, fitness);
//    }
//
//    @Test
//    public void dayFilterTestException() throws InvalidFilterOptionException {
//        dayFilter.setOption("Random");
//        double fitness = 0;
//        thrown.expect(InvalidFilterOptionException.class);
//        fitness = dayFilter.getFitness(schedule);
//    }
}
