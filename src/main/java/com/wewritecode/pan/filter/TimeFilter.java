/**
 * @author Grant Clark
 */

package com.wewritecode.pan.filter;

import com.wewritecode.pan.schedule.*;

import java.util.ArrayList;
import java.util.List;

public class TimeFilter implements Filter<Schedule> {

    private static final int EARLIEST_TIME = 480;           // 08:00 AM in minutes since midnight.
    private static final int LATEST_TIME = 1320;            // 10:00 PM in minutes since midnight.
    private static final int TIME_RANGE = LATEST_TIME - EARLIEST_TIME;
    private static final int MID_TIME = TIME_RANGE / 2;

    private static final String OPTION_EARLY = "Early";
    private static final String OPTION_MID = "Mid";
    private static final String OPTION_LATE = "Late";

    private static final String[] OPTIONS = {OPTION_EARLY, OPTION_MID, OPTION_LATE};

    private String option;
    List<Integer> times;

    public TimeFilter() {
        times = new ArrayList<>();
    }

    public String getOption() { return option; }
    public void setOption(String option) { this.option = option; }
    public String[] getOptions() { return OPTIONS; }

    @Override
    public double getFitness(Schedule schedule) {
        List<Course> courses = schedule.getCourses();
        for (Course c : courses)
            addCourseTimes(c);

        return applyOption(avgTimes());
    }

    private void addCourseTimes(Course course) {
        List<Lecture> lectures = course.getLectures();
        for (Lecture l : lectures)
            addLectureTimes(l);
    }

    private void addLectureTimes(Lecture lecture) {
        int startHour = lecture.getTime().getStart().getHour();
        int startMinute = lecture.getTime().getStart().getMinute();
        int timeInMins = (60 * startHour) + startMinute;

        for (String days : lecture.getDays())
            times.add(timeInMins);

        for (Section s : lecture.getSections())
            addSectionTimes(s);
    }

    private void addSectionTimes(Section section) {
        int startHour = section.getTime().getStart().getHour();
        int startMinute = section.getTime().getStart().getMinute();
        int timeInMins = (60 * startHour) + startMinute;

        for (String days : section.getDays())
            times.add(timeInMins);
    }

    private double avgTimes() {
        double sum = 0;
        int count = times.size();
        for (Integer i : times)
            sum += i;
        return (sum / count);
    }

    private double applyOption(double avgTime) {
        switch(option) {
            case OPTION_EARLY:
                return ((avgTime - EARLIEST_TIME) / TIME_RANGE);
            case OPTION_MID:
                return Math.abs((avgTime - MID_TIME) / TIME_RANGE);
            case OPTION_LATE:
                return ((LATEST_TIME - avgTime) / TIME_RANGE);
            default:
                return -1;
        }
    }
}
