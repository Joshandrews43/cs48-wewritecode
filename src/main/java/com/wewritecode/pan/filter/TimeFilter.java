/**
 * @author Grant Clark
 */

package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wewritecode.pan.schedule.*;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("Time")
public class TimeFilter implements Filter<Schedule> {

    // Constants

    private static final int EARLIEST_TIME = 480;           // 08:00 AM in minutes since midnight.
    private static final int LATEST_TIME = 1320;            // 10:00 PM in minutes since midnight.
    private static final int TIME_RANGE = LATEST_TIME - EARLIEST_TIME;
    private static final int MID_TIME = TIME_RANGE / 2;

    private static final String OPTION_EARLY = "Early";
    private static final String OPTION_MID = "Mid";
    private static final String OPTION_LATE = "Late";

    private static final String[] OPTIONS = {OPTION_EARLY, OPTION_MID, OPTION_LATE};

    // Private Member Variable(s)

    private String option;

    // Getter(s) / Setter(s)

    public String getOption() { return option; }
    public void setOption(String option) { this.option = option; }
    public String[] getOptions() { return OPTIONS; }

    // Public Core Method(s)

    @Override
    public double getFitness(Schedule schedule) throws InvalidFilterOptionException {
        List<Integer> times = new ArrayList<>();
        List<Course> courses = schedule.getCourses();
        for (Course c : courses)
            times.addAll(getCourseTimes(c));

        return applyOption(avgTimes(times));
    }

    // Private Helper Method(s)

    private List<Integer> getCourseTimes(Course course) {
        List<Integer> times = new ArrayList<>();
        List<Lecture> lectures = course.getLectures();
        for (Lecture l : lectures)
            times.addAll(getLectureTimes(l));

        return times;
    }

    private List<Integer> getLectureTimes(Lecture lecture) {
        List<Integer> times = new ArrayList<>();
        int startHour = lecture.getTime().getStart().getHour();
        int startMinute = lecture.getTime().getStart().getMinute();
        int timeInMins = (60 * startHour) + startMinute;

        for (String days : lecture.getDays())
            times.add(timeInMins);

        for (Section s : lecture.getSections())
            times.addAll(getSectionTimes(s));

        return times;
    }

    private List<Integer> getSectionTimes(Section section) {
        List<Integer> times = new ArrayList<>();
        int startHour = section.getTime().getStart().getHour();
        int startMinute = section.getTime().getStart().getMinute();
        int timeInMins = (60 * startHour) + startMinute;

        for (String days : section.getDays())
            times.add(timeInMins);

        return times;
    }

    private double avgTimes(List<Integer> times) {
        double sum = 0;
        int count = times.size();
        for (Integer i : times)
            sum += i;
        return (sum / count);
    }

    private double applyOption(double avgTime) throws InvalidFilterOptionException {
        switch(option) {
            case OPTION_EARLY:
                return ((avgTime - EARLIEST_TIME) / TIME_RANGE);
            case OPTION_MID:
                return Math.abs((avgTime - MID_TIME) / TIME_RANGE);
            case OPTION_LATE:
                return ((LATEST_TIME - avgTime) / TIME_RANGE);
            default:
                String message = String.format("Option: \"%s\" not supported for TimeFilter.", option);
                throw new InvalidFilterOptionException(message);
        }
    }
}
