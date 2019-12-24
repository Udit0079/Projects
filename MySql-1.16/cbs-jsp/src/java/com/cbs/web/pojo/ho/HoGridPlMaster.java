/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.ho;

/**
 *
 * @author root
 */
public class HoGridPlMaster {

    private String classfication;
    private int groupcode;
    private int subgroupcode;
    private String code;
    private String groupDescriptions;
    private String subGrpDesc;
    private String lastUpdatedBy;
    private String mode;

    public String getSubGrpDesc() {
        return subGrpDesc;
    }

    public void setSubGrpDesc(String subGrpDesc) {
        this.subGrpDesc = subGrpDesc;
    }

    public String getClassfication() {
        return classfication;
    }

    public void setClassfication(String classfication) {
        this.classfication = classfication;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupDescriptions() {
        return groupDescriptions;
    }

    public void setGroupDescriptions(String groupDescriptions) {
        this.groupDescriptions = groupDescriptions;
    }

    public int getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(int groupcode) {
        this.groupcode = groupcode;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getSubgroupcode() {
        return subgroupcode;
    }

    public void setSubgroupcode(int subgroupcode) {
        this.subgroupcode = subgroupcode;
    }
}