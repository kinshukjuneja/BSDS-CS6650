package edu.neu.server.model;

import java.io.Serializable;

/**
 *
 * Simple class to wrap the data in a RFID lift pass reader record
 */
public class SkierData implements Serializable, Comparable<SkierData> {
    private int resortID;
    private int dayNum;
    private int skierID;
    private int liftID;
    private int time;

    public SkierData() {}

    public SkierData(int resortID, int dayNum, int skierID, int liftID, int time) {
        this.resortID = resortID;
        this.dayNum = dayNum;
        this.skierID = skierID;
        this.liftID = liftID;
        this.time = time;
    }

    public int getResortID() {
        return resortID;
    }

    public void setResortID(int resortID) {
        this.resortID = resortID;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public int getSkierID() {
        return skierID;
    }

    public void setSkierID(int skierID) {
        this.skierID = skierID;
    }

    public int getLiftID() {
        return liftID;
    }

    public void setLiftID(int liftID) {
        this.liftID = liftID;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int compareTo(SkierData compareData) {
        int compareTime = ((SkierData) compareData).getTime();
        //ascending order
        return this.time - compareTime ;
    }

    @Override
    public String toString() {
        return "skierId=" + skierID + "dayNum=" + dayNum;
    }
}
