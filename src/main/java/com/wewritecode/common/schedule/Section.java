/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.common.schedule;


import java.util.List;

public class Section extends Session implements ISection {
    public Section() {
        super();
    }
    public Section(List<String> days, Time time) {
        super(days, time);
    }
}
