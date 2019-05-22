/**
 * @author Alan Roddick
 */
package com.wewritecode.application.filter;

import com.wewritecode.application.common.schedule.*;
import com.wewritecode.application.pan.filter.GapsFilter;
import com.wewritecode.application.pan.filter.InvalidFilterOptionException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GapsFilterTest {

    private GapsFilter gapsFilter;
    private Schedule schedule;

    @Before
    public void setup() {
        gapsFilter = new GapsFilter();
        schedule = new Schedule();

        List<String> lectureCS64Days = new ArrayList<>();
        lectureCS64Days.add("M");
        lectureCS64Days.add("W");
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
        String format = String.format("%.3f", fitness);
        assertEquals("0.945", format);
    }

//    @Test
//    public void dayFilterTestMaximize() throws InvalidFilterOptionException {
//        gapsFilter.setOption("Maximize Days");
//        double fitness = 0;
//        fitness = gapsFilter.getFitness(schedule);
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
