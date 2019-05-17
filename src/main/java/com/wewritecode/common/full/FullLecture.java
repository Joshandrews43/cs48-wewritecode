/**
 * @author Grant Clark
 */

package com.wewritecode.common.full;

import java.util.HashSet;
import java.util.Set;

public class FullLecture extends FullSession {

    private Set<FullSection> sections;

    public FullLecture() {
        super();
        sections = new HashSet<>();
    }

    public Set<FullSection> getSections() { return sections; }
    public void setSections(Set<FullSection> sections) { this.sections = sections; }
}
