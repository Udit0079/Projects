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
public class HrDetailsProgramTO implements Serializable {
 private static final long serialVersionUID = 1L;

    protected HrDetailsProgramPKTO hrDetailsProgramPKTO;
   
    private Integer defaultComp;
    
    private String statFlag;
   
    private String statUpFlag;
   
    private Date modDate;
   
    private String authBy;
    
    private String enteredBy;
   
    private HrMstProgramTO hrMstProgramTO;
   
    private HrMstStructTO hrMstStructTO;

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

    public HrDetailsProgramPKTO getHrDetailsProgramPKTO() {
        return hrDetailsProgramPKTO;
    }

    public void setHrDetailsProgramPKTO(HrDetailsProgramPKTO hrDetailsProgramPKTO) {
        this.hrDetailsProgramPKTO = hrDetailsProgramPKTO;
    }

    public HrMstProgramTO getHrMstProgramTO() {
        return hrMstProgramTO;
    }

    public void setHrMstProgramTO(HrMstProgramTO hrMstProgramTO) {
        this.hrMstProgramTO = hrMstProgramTO;
    }

    public HrMstStructTO getHrMstStructTO() {
        return hrMstStructTO;
    }

    public void setHrMstStructTO(HrMstStructTO hrMstStructTO) {
        this.hrMstStructTO = hrMstStructTO;
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
