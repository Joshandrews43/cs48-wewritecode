/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.common.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Lecture extends Session {
    private List<Section> sections;

    public Lecture() {
        sections = new ArrayList<>();
    }

    public Lecture(Lecture l) {
        super();
        sections = new ArrayList<>();
        this.setTime(l.getTime());
        this.setDays(this.getDays());
        for (int i = 0; i < l.sections.size(); i++) {
            this.addSection(l.getSection(i));
        }
    }

    public Lecture(Lecture l, Section section) {
        super(l.getDays(), l.getTime());
        sections = new ArrayList<>();
        sections.add(section);
    }

    public Lecture(List<String> days, Time time) {
        super(days, time);
        sections = new ArrayList<>();
    }

    public List<Section> getSections() { return sections; }
    public void setSections(List<Section> sections) { this.sections = sections; }

    @JsonIgnore
    public int getNumSections() { return sections.size(); }
    @JsonIgnore
    public Section getSection(int index) { return sections.get(index); }
    public void addSection(Section section) { sections.add(section); }
}
