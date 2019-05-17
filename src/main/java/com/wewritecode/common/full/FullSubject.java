/**
 * @author Grant Clark
 */

package com.wewritecode.common.full;

import java.util.ArrayList;
import java.util.List;

public class FullSubject {
    private String fullName;
    private List<FullCourse> courses;

    public FullSubject() {
        courses = new ArrayList<>();
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public List<FullCourse> getCourses() { return courses; }
    public void setCourses(List<FullCourse> courses) { this.courses = courses; }
}
