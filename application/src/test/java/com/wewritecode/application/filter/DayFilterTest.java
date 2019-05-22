/**
 * @author Alan Roddick
 */
package com.wewritecode.application.filter;

import com.wewritecode.application.common.schedule.*;
import com.wewritecode.application.pan.filter.DayFilter;
import com.wewritecode.application.pan.filter.InvalidFilterOptionException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DayFilterTest {

    private DayFilter dayFilter;
    private Schedule schedule;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        dayFilter = new DayFilter();
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
        dayFilter.setOption("Minimize Days");
        double fitness = 0;
        fitness = dayFilter.getFitness(schedule);
        assertEquals(0.25, fitness);
    }

    @Test
    public void dayFilterTestMaximize() throws InvalidFilterOptionException {
        dayFilter.setOption("Maximize Days");
        double fitness = 0;
        fitness = dayFilter.getFitness(schedule);
        assertEquals(0.75, fitness);
    }

    @Test
    public void dayFilterTestException() throws InvalidFilterOptionException {
        dayFilter.setOption("Random");
        double fitness = 0;
        thrown.expect(InvalidFilterOptionException.class);
        fitness = dayFilter.getFitness(schedule);
    }
}
