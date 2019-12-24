/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class HrPersonnelLangTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected HrPersonnelLangPKTO hrPersonnelLangPKTO;

    private char langRead;

    private char langWrite;

    private char langSpeak;

    private Integer defaultComp;

    private String statFlag;

    private String statUpFlag;

    private Date modDate;

    private String authBy;

    private String enteredBy;

    private HrPersonnelDetailsTO hrPersonnelDetailsTO;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrPersonnelDetailsTO getHrPersonnelDetailsTO() {
        return hrPersonnelDetailsTO;
    }

    public void setHrPersonnelDetailsTO(HrPersonnelDetailsTO hrPersonnelDetailsTO) {
        this.hrPersonnelDetailsTO = hrPersonnelDetailsTO;
    }

    public HrPersonnelLangPKTO getHrPersonnelLangPKTO() {
        return hrPersonnelLangPKTO;
    }

    public void setHrPersonnelLangPKTO(HrPersonnelLangPKTO hrPersonnelLangPKTO) {
        this.hrPersonnelLangPKTO = hrPersonnelLangPKTO;
    }

    public char getLangRead() {
        return langRead;
    }

    public void setLangRead(char langRead) {
        this.langRead = langRead;
    }

    public char getLangSpeak() {
        return langSpeak;
    }

    public void setLangSpeak(char langSpeak) {
        this.langSpeak = langSpeak;
    }

    public char getLangWrite() {
        return langWrite;
    }

    public void setLangWrite(char langWrite) {
        this.langWrite = langWrite;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }


}
