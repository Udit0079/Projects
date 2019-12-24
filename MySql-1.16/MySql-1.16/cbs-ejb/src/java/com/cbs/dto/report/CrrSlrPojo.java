/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author root
 */
public class CrrSlrPojo {
    private String date;
    
    private double ntdl;
    
    private double reqToBeMaint;
    
    private double actMaint;
    
    private double deficit;
    
    private double surplus;
    
    private double interest;
    
    private double crrReqToBeMaint;
    
    private double crrActualMaint;

    public double getActMaint() {
        return actMaint;
    }

    public void setActMaint(double actMaint) {
        this.actMaint = actMaint;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDeficit() {
        return deficit;
    }

    public void setDeficit(double deficit) {
        this.deficit = deficit;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getNtdl() {
        return ntdl;
    }

    public void setNtdl(double ntdl) {
        this.ntdl = ntdl;
    }

    public double getReqToBeMaint() {
        return reqToBeMaint;
    }

    public void setReqToBeMaint(double reqToBeMaint) {
        this.reqToBeMaint = reqToBeMaint;
    }

    public double getSurplus() {
        return surplus;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }
    public double getCrrReqToBeMaint() {
        return crrReqToBeMaint;
    }
    public void setCrrReqToBeMaint(double crrReqToBeMaint) {
        this.crrReqToBeMaint = crrReqToBeMaint;
    }
    public double getCrrActualMaint() {
        return crrActualMaint;
    }
    public void setCrrActualMaint(double crrActualMaint) {
        this.crrActualMaint = crrActualMaint;
    }
    
}
