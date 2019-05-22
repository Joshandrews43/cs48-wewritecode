/**
 * @author Grant Clark
 */

package com.wewritecode.application.common.full;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    public int getNumSections() { return sections.size(); }
    @JsonIgnore
    public FullSection getSection(int index) { return (FullSection) sections.toArray()[index]; }
    public void addSection(FullSection section) { sections.add(section); }
}
