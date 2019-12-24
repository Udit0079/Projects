package com.hrms.common.to;

import com.hrms.entity.hr.HrMstStruct;
import java.io.Serializable;
import java.util.Date;

public class HrMstDeptSubdeptTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected HrMstDeptSubdeptPKTO hrMstDeptSubdeptPKTO;
    private String statFlag;
    private String statUpFlag;
    private Date modDate;
    private int defaultComp;
    private String authBy;
    private String enteredBy;
    private HrMstStruct hrMstStruct;
    private HrMstStruct hrMstStruct1;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrMstDeptSubdeptPKTO getHrMstDeptSubdeptPKTO() {
        return hrMstDeptSubdeptPKTO;
    }

    public void setHrMstDeptSubdeptPKTO(HrMstDeptSubdeptPKTO hrMstDeptSubdeptPKTO) {
        this.hrMstDeptSubdeptPKTO = hrMstDeptSubdeptPKTO;
    }

    public HrMstStruct getHrMstStruct() {
        return hrMstStruct;
    }

    public void setHrMstStruct(HrMstStruct hrMstStruct) {
        this.hrMstStruct = hrMstStruct;
    }

    public HrMstStruct getHrMstStruct1() {
        return hrMstStruct1;
    }

    public void setHrMstStruct1(HrMstStruct hrMstStruct1) {
        this.hrMstStruct1 = hrMstStruct1;
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
