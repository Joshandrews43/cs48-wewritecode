/**
 * @author Grant Clark
 */

package com.wewritecode.pan.filter;

import com.wewritecode.pan.schedule.*;

import java.util.List;

public class TimeFilter implements Filter<Schedule> {

    private static final int EARLIEST_TIME = 480;           // 08:00 AM in minutes since midnight.
    private static final int LATEST_TIME = 1320;            // 10:00 PM in minutes since midnight.
    private static final int TIME_RANGE = LATEST_TIME - EARLIEST_TIME;

    private String option;

    public String getOption() { return option; }
    public void setOption(String option) { this.option = option; }

    @Override
    public double getFitness(Schedule schedule) {
        List<Course> courses = schedule.getCourses();
        double fitness = 0;
        for (Course c : courses) {
//            fitness += getCourseFitness(c);
        }

        // FIXME: if courses.size() == 0, which shouldn't ever happen.
        return fitness / courses.size();
    }

//    private double getCourseFitness(Course course) {
//        List<Lecture> lectures = course.getLectures();
//        double fitness = 0;
//        for (Lecture l : lectures) {
//            fitness += getLectureFitness(l);
//        }
//    }
//
//    private double getLectureFitness(Lecture lecture) {
//
//    }
//
//    private double getSectionFitness(Section section) {
//
//    }

}
