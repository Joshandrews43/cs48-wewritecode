/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

public class Section extends Session {

    public Section(String[] days, Time start, Time end) {
        super();
        this.setDays(days);
        this.setStart(start);
        this.setEnd(end);
    }

}
