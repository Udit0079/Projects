/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.mis;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class MarketingStatusPojo implements Serializable {

    private String assignedUser;
    private int newS;
    private int followup;
    private int transfer;
    private int booked;
    private int reject;
    private int pending;
    private int srno;

    public int getNewS() {
        return newS;
    }

    public void setNewS(int newS) {
        this.newS = newS;
    }

    public int getFollowup() {
        return followup;
    }

    public void setFollowup(int followup) {
        this.followup = followup;
    }

    public int getTransfer() {
        return transfer;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public int getReject() {
        return reject;
    }

    public void setReject(int reject) {
        this.reject = reject;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }
}
