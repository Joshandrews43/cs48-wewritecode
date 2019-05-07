/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Section {

    @Id
    @GeneratedValue
    private int sectionId;

    @ManyToOne
    @JoinColumn(name = "lectureId")
    private Lecture lecture;

    @ElementCollection
    private List<String> sectionDays = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "SECTION_START")
    private Time start;

    @ManyToOne
    @JoinColumn(name = "SECTION_END")
    private Time end;

    public int getSectionId() { return sectionId; }
    public void setSectionId(int sectionId) { this.sectionId = sectionId; }
    public Lecture getLecture() { return lecture; }
    public void setLecture(Lecture lecture) { this.lecture = lecture; }
    public List<String> getSectionDays() { return sectionDays; }
    public void setSectionDays(List<String> sectionDays) { this.sectionDays = sectionDays; }
    public Time getStart() { return start; }
    public void setStart(Time start) { this.start = start; }
    public Time getEnd() { return end; }
    public void setEnd(Time end) { this.end = end; }
}
