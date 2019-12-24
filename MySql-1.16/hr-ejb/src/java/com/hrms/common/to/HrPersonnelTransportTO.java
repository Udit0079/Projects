package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

public class HrPersonnelTransportTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected HrPersonnelTransportPKTO hrPersonnelTransportPKTO;
    private String pickPnt;
    private Date pickTm;
    private Integer defaultComp;
    private String statFlag;
    private String statUpFlag;
    private Date modDate;
    private String authBy;
    private String enteredBy;
    private HrMstBusTO hrMstBusTO;
    private HrMstRouteTO hrMstRouteTO;
    private HrPersonnelDetailsTO hrPersonnelDetailsTO;

    public HrMstBusTO getHrMstBusTO() {
        return hrMstBusTO;
    }

    public void setHrMstBusTO(HrMstBusTO hrMstBusTO) {
        this.hrMstBusTO = hrMstBusTO;
    }

    public HrMstRouteTO getHrMstRouteTO() {
        return hrMstRouteTO;
    }

    public void setHrMstRouteTO(HrMstRouteTO hrMstRouteTO) {
        this.hrMstRouteTO = hrMstRouteTO;
    }

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

    public HrPersonnelTransportPKTO getHrPersonnelTransportPKTO() {
        return hrPersonnelTransportPKTO;
    }

    public void setHrPersonnelTransportPKTO(HrPersonnelTransportPKTO hrPersonnelTransportPKTO) {
        this.hrPersonnelTransportPKTO = hrPersonnelTransportPKTO;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getPickPnt() {
        return pickPnt;
    }

    public void setPickPnt(String pickPnt) {
        this.pickPnt = pickPnt;
    }

    public Date getPickTm() {
        return pickTm;
    }

    public void setPickTm(Date pickTm) {
        this.pickTm = pickTm;
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
