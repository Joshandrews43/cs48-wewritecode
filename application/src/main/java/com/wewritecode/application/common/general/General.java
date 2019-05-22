/**
 * @author Grant Clark
 */

package com.wewritecode.application.common.general;

import java.util.ArrayList;
import java.util.List;

public class General {
    private List<GeneralQuarter> quarters;
    private List<GeneralSubject> subjects;

    public General() {
        quarters = new ArrayList<>();
        subjects = new ArrayList<>();
    }

    public List<GeneralQuarter> getQuarters() { return quarters; }
    public void setQuarters(List<GeneralQuarter> quarters) { this.quarters = quarters; }
    public List<GeneralSubject> getSubjects() { return subjects; }
    public void setSubjects(List<GeneralSubject> subjects) { this.subjects = subjects; }
}
