/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Lecture extends Session implements ILecture<Section> {
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

    @JsonIgnore
    public int getNumSections() { return sections.size(); }
    @JsonIgnore
    public Section getSection(int index) { return sections.get(index); }
    @JsonIgnore
    public void removeSections() { sections.clear(); }

    @Override
    public List<Section> getSections() { return sections; }
    @Override
    public void setSections(List<Section> sections) { this.sections = sections; }
    @Override
    public void addSection(Section section) { sections.add(section); }
}
