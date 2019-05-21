/**
 * @author Grant Clark
 */

package com.wewritecode.common.full;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wewritecode.common.schedule.Lecture;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FullCourse {
    private String courseID;
    private String title;
    private String fullTitle;
    private String description;
    private String college;
    private String prerequisites;
    private String gradingOptions;
    private Map<String, Integer> units;
    private Set<FullLecture> lectures;

    public FullCourse() {
        units = new HashMap<>();
        lectures = new HashSet<>();
    }

    public String getCourseID() { return courseID; }
    public void setCourseID(String courseID) { this.courseID = courseID; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getFullTitle() { return fullTitle; }
    public void setFullTitle(String fullTitle) { this.fullTitle = fullTitle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }
    public String getPrerequisites() { return prerequisites; }
    public void setPrerequisites(String prerequisites) { this.prerequisites = prerequisites; }
    public String getGradingOptions() { return gradingOptions; }
    public void setGradingOptions(String gradingOptions) { this.gradingOptions = gradingOptions; }
    public Map<String, Integer> getUnits() { return units; }
    public void setUnits(Map<String, Integer> units) { this.units = units; }
    public Set<FullLecture> getLectures() { return lectures; }
    public void setLectures(Set<FullLecture> lectures) { this.lectures = lectures; }

    @JsonIgnore
    public int getNumLectures() { return lectures.size(); }
    @JsonIgnore
    public FullLecture getLecture(int index) { return (FullLecture) lectures.toArray()[index]; }
    public void addLecture(FullLecture lecture) { lectures.add(lecture); }

    public boolean equals(FullCourse c) {
        return this.courseID.equalsIgnoreCase(c.courseID);
    }
}
