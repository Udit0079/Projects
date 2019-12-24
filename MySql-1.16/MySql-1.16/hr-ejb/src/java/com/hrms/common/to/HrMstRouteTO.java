package com.hrms.common.to;

import com.hrms.entity.personnel.HrPersonnelTransport;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class HrMstRouteTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected HrMstRoutePKTO hrMstRoutePKTO;
    private String routeStart;
    private String routeEnd;
    private String routeVia;
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

    public HrMstRoutePKTO getHrMstRoutePKTO() {
        return hrMstRoutePKTO;
    }

    public void setHrMstRoutePKTO(HrMstRoutePKTO hrMstRoutePKTO) {
        this.hrMstRoutePKTO = hrMstRoutePKTO;
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

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public String getRouteStart() {
        return routeStart;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }

    public String getRouteVia() {
        return routeVia;
    }

    public void setRouteVia(String routeVia) {
        this.routeVia = routeVia;
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
