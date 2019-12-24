/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author root
 */
public class AdditionalStmtPojo implements Serializable{
    private String classification;
    
    private String nature;
    
    private String acType;
    
    private String glFromHead;
    
    private String glToHead;
    
    private String fromRange;
    
    private String toRange;
    
    private String rangeBasedOn;
    
    private String brnCode;
    
    private String orgBrCode;
    
    private String date;
    
    private String toDate;
    
    private String npaClassification;
    
    private String flag;
    
    List<NpaStatusReportPojo> npaAcList;
    public String refer_index;
    public String refer_content;
   
    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getRefer_content() {
        return refer_content;
    }

    public void setRefer_content(String refer_content) {
        this.refer_content = refer_content;
    }

    public String getRefer_index() {
        return refer_index;
    }

    public void setRefer_index(String refer_index) {
        this.refer_index = refer_index;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getGlFromHead() {
        return glFromHead;
    }

    public void setGlFromHead(String glFromHead) {
        this.glFromHead = glFromHead;
    }

    public String getGlToHead() {
        return glToHead;
    }

    public void setGlToHead(String glToHead) {
        this.glToHead = glToHead;
    }

    public String getFromRange() {
        return fromRange;
    }

    public void setFromRange(String fromRange) {
        this.fromRange = fromRange;
    }

    public String getToRange() {
        return toRange;
    }

    public void setToRange(String toRange) {
        this.toRange = toRange;
    }

    public String getRangeBasedOn() {
        return rangeBasedOn;
    }

    public void setRangeBasedOn(String rangeBasedOn) {
        this.rangeBasedOn = rangeBasedOn;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public String getOrgBrCode() {
        return orgBrCode;
    }

    public void setOrgBrCode(String orgBrCode) {
        this.orgBrCode = orgBrCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public String getNpaClassification() {
        return npaClassification;
    }

    public void setNpaClassification(String npaClassification) {
        this.npaClassification = npaClassification;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }     

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<NpaStatusReportPojo> getNpaAcList() {
        return npaAcList;
    }

    public void setNpaAcList(List<NpaStatusReportPojo> npaAcList) {
        this.npaAcList = npaAcList;
    }
    
}
