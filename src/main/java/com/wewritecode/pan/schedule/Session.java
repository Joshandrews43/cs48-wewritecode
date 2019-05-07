
// TODO: Revisit to try to re-implement into program to work with in-memory database.

///**
// * @author Alan Roddick
// * @author Grant Clark
// */
//
//package com.wewritecode.pan.schedule;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//public class Session {
//    @Id
//    @GeneratedValue
//    private int sessionId;
//
//    @OneToMany(targetEntity = String.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<String> days = new ArrayList<>();
//    private Time start;
//    private Time end;
//
//    public List<String> getLectureDays() { return days; }
//    public void setLectureDays(List<String> days) { this.days = days; }
//    public Time getStart() { return start; }
//    public void setStart(Time start) { this.start = start; }
//    public Time getEnd() { return end; }
//    public void setEnd(Time end) { this.end = end; }
//}
