/**
 * @author Grant Clark
 */

package com.wewritecode.application.common.full;


import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public class FullQuarter {
    private Map<String, FullSubject> subjects;

    public FullQuarter() {
        subjects = new HashMap<>();
    }

    @JsonCreator
    public FullQuarter(Map<String, FullSubject> subjects) {
        this.subjects = subjects;
    }

    public Map<String, FullSubject> getSubjects() { return subjects; }
    public void setSubjects(Map<String, FullSubject> subjects) { this.subjects = subjects; }
}
