package com.hrms.common.to;

import com.hrms.entity.hr.HrMstBusPK;
import com.hrms.entity.personnel.HrPersonnelTransport;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class HrMstBusTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected HrMstBusPKTO hrMstBusPKTO;
    private Date busStartTm;
    private Date busEndTm;
    private String busDriver;
    private String remarks;
    private Integer defaultComp;
    private String statFlag;
    private String statUpFlag;
    private Date modDate;
    private String authBy;
    private String enteredBy;
    private Collection<HrPersonnelTransport> hrPersonnelTransportCollection;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getBusDriver() {
        return busDriver;
    }

    public void setBusDriver(String busDriver) {
        this.busDriver = busDriver;
    }

    public Date getBusEndTm() {
        return busEndTm;
    }

    public void setBusEndTm(Date busEndTm) {
        this.busEndTm = busEndTm;
    }

    public Date getBusStartTm() {
        return busStartTm;
    }

    public void setBusStartTm(Date busStartTm) {
        this.busStartTm = busStartTm;
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

    public HrMstBusPKTO getHrMstBusPKTO() {
        return hrMstBusPKTO;
    }

    public void setHrMstBusPKTO(HrMstBusPKTO hrMstBusPKTO) {
        this.hrMstBusPKTO = hrMstBusPKTO;
    }

    public Collection<HrPersonnelTransport> getHrPersonnelTransportCollection() {
        return hrPersonnelTransportCollection;
    }

    public void setHrPersonnelTransportCollection(Collection<HrPersonnelTransport> hrPersonnelTransportCollection) {
        this.hrPersonnelTransportCollection = hrPersonnelTransportCollection;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
