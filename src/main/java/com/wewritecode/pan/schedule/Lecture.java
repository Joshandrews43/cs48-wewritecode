/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.List;

public class Lecture extends Session {
    private List<Section> sections;

    public Lecture(Lecture l) {
        this.setStart(l.getStart());
        this.setEnd(l.getEnd());
        this.setDays(this.getDays());
        for (int i = 0; i < l.sections.size(); i++) {
            this.addSection(l.getSection(i));
        }
    }
    public Lecture(Lecture l, Section section) {
        this.setStart(l.getStart());
        this.setEnd(l.getEnd());
        this.setDays(this.getDays());
        this.sections.add(section);
    }

    public int getNumSections() { return sections.size(); }
    public Section getSection(int index) { return sections.get(index); }
    public void addSection(Section section) { sections.add(section); }
    public void removeSections() { sections.clear(); }

    public List<Section> getSections() {
        return sections;
    }
}
