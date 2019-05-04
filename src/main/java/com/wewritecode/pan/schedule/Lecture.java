/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Lecture extends Session {

    @Id
    @GeneratedValue
    private int id;
    private List<Section> sections;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<Section> getSections() { return sections; }
    public void setSections(List<Section> sections) { this.sections = sections; }
}
