/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.ArrayList;
import java.util.List;

public class Lecture implements ILecture<Section> {
    private List<String> days;
    private Time start;
    private Time end;
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
        this.days = l.getDays();
        this.start = l.getStart();
        this.end = l.getEnd();
        sections = new ArrayList<>();
        sections.add(section);
    }

    public Lecture(List<String> days, Time start, Time end) {
        this.days = days;
        this.start = start;
        this.end = end;
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

    public String getDay(int index) throws IndexOutOfBoundsException { return days.get(index); }

    public List<String> getDays() { return days; }
    public void setDays(List<String> days) { this.days = days; }
    public Time getStart() { return start; }
    public void setStart(Time start) { this.start = start; }
    public Time getEnd() { return end; }
    public void setEnd(Time end) { this.end = end; }
}
