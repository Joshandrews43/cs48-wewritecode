/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.ArrayList;
import java.util.List;

public class Lecture extends Session implements ILecture<ISection> {
    private List<ISection> sections;

    public Lecture() {
        sections = new ArrayList<>();
    }

    @Override
    public List<ISection> getSections() { return sections; }
    @Override
    public void setSections(List<ISection> sections) { this.sections = sections; }
    @Override
    public void addSection(ISection section) { sections.add(section); }
}
