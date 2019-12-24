/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

/**
 *
 * @author Admin
 */
public class BucketParameterGrid {

    String bucketNo;
    String bucketDesc;
    String startDay;
    String endDay;
    String profileParameter;

    public String getBucketDesc() {
        return bucketDesc;
    }

    public void setBucketDesc(String bucketDesc) {
        this.bucketDesc = bucketDesc;
    }

    public String getBucketNo() {
        return bucketNo;
    }

    public void setBucketNo(String bucketNo) {
        this.bucketNo = bucketNo;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getProfileParameter() {
        return profileParameter;
    }

    public void setProfileParameter(String profileParameter) {
        this.profileParameter = profileParameter;
    }
}
