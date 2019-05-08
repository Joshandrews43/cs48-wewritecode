/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

<<<<<<< HEAD
public class Section extends Session implements ISection {
=======
public class Section extends Session {

    public Section(String[] days, Time start, Time end) {
        super();
        this.setDays(days);
        this.setStart(start);
        this.setEnd(end);
    }

>>>>>>> scheduler_aroddick
}
