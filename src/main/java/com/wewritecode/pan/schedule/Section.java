/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.List;

public class Section extends Session implements ISection {
    public Section(List<String> days, Time start, Time end) {
        super();
        this.setDays(days);
        this.setStart(start);
        this.setEnd(end);
    }
}
