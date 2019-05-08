/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

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
        this.setStart(l.getStart());
        this.setEnd(l.getEnd());
        this.setDays(this.getDays());
        for (int i = 0; i < l.sections.size(); i++) {
            this.addSection(l.getSection(i));
        }
    }

    public Lecture(Lecture l, Section section) {
        super(l.getDays(), l.getStart(), l.getEnd());
        sections = new ArrayList<>();
        sections.add(section);
    }

    public Lecture(List<String> days, Time start, Time end) {
        super(days, start, end);
        sections = new ArrayList<>();
    }

    public int getNumSections() { return sections.size(); }
    public Section getSection(int index) { return sections.get(index); }
    public void removeSections() { sections.clear(); }

    @Override
    public List<Section> getSections() { return sections; }
    @Override
    public void setSections(List<Section> sections) { this.sections = sections; }
    @Override
    public void addSection(Section section) { sections.add(section); }
}
