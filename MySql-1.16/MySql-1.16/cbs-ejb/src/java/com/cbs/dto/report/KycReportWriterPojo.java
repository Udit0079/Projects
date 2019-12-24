/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class KycReportWriterPojo implements Serializable {

    private String custId;
    private String dependents;
    private Integer noChild;
    private Integer noMales10;
    private Integer noMales20;
    private Integer noMales45;
    private Integer noMales60;
    private Integer noMalesAbove61;
    private Integer noFem10;
    private Integer noFem20;
    private Integer noFem45;
    private Integer noFem60;
    private Integer noFemAbove61;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getDependents() {
        return dependents;
    }

    public void setDependents(String dependents) {
        this.dependents = dependents;
    }

    public Integer getNoChild() {
        return noChild;
    }

    public void setNoChild(Integer noChild) {
        this.noChild = noChild;
    }

    public Integer getNoMales10() {
        return noMales10;
    }

    public void setNoMales10(Integer noMales10) {
        this.noMales10 = noMales10;
    }

    public Integer getNoMales20() {
        return noMales20;
    }

    public void setNoMales20(Integer noMales20) {
        this.noMales20 = noMales20;
    }

    public Integer getNoMales45() {
        return noMales45;
    }

    public void setNoMales45(Integer noMales45) {
        this.noMales45 = noMales45;
    }

    public Integer getNoMales60() {
        return noMales60;
    }

    public void setNoMales60(Integer noMales60) {
        this.noMales60 = noMales60;
    }

    public Integer getNoMalesAbove61() {
        return noMalesAbove61;
    }

    public void setNoMalesAbove61(Integer noMalesAbove61) {
        this.noMalesAbove61 = noMalesAbove61;
    }

    public Integer getNoFem10() {
        return noFem10;
    }

    public void setNoFem10(Integer noFem10) {
        this.noFem10 = noFem10;
    }

    public Integer getNoFem20() {
        return noFem20;
    }

    public void setNoFem20(Integer noFem20) {
        this.noFem20 = noFem20;
    }

    public Integer getNoFem45() {
        return noFem45;
    }

    public void setNoFem45(Integer noFem45) {
        this.noFem45 = noFem45;
    }

    public Integer getNoFem60() {
        return noFem60;
    }

    public void setNoFem60(Integer noFem60) {
        this.noFem60 = noFem60;
    }

    public Integer getNoFemAbove61() {
        return noFemAbove61;
    }

    public void setNoFemAbove61(Integer noFemAbove61) {
        this.noFemAbove61 = noFemAbove61;
    }
}
