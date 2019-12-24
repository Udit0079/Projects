package com.cbs.web.controller;

import com.cbs.servlets.Init;
import com.hrms.web.utils.WebUtil;
import java.io.Serializable;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseBean implements Serializable {

    /**
     * Contain user information for all sub backing/managed beans
     */
    private FacesContext facesContext;
    private HttpSession httpSession;
    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;
    protected Map<String, Boolean> objAccessMap = new HashMap<String, Boolean>();
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String acNoLength;
    private String acNoMaxLen;

    public BaseBean() {
        try {
            facesContext = getFacesContext();
            httpSession = getHttpSession();
            this.setOrgnBrCode(Init.IP2BR.getBranch(InetAddress.getByName(WebUtil.getClientIP(getHttpRequest()))));
            this.setTodayDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            if (getHttpSession().getAttribute("userid") != null) {
                this.setUserName(getHttpSession().getAttribute("userid").toString());
            } else {
                this.setUserName(getHttpRequest().getRemoteUser());
            }
            if (getHttpSession().getAttribute("maxlen") != null) {
                this.setAcNoLength(getHttpSession().getAttribute("maxlen").toString());
            } else {
                this.setAcNoLength("12");
            }
            if (Integer.parseInt(acNoLength) < 12) {
                setAcNoMaxLen("12");
            } else {
                setAcNoMaxLen(acNoLength);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public Map<String, Boolean> getObjAccessMap() {
        return objAccessMap;
    }

    public void setObjAccessMap(Map<String, Boolean> objAccessMap) {
        this.objAccessMap = objAccessMap;
    }

    protected final HttpSession getHttpSession() {
        httpSession = (HttpSession) getFacesContext().getExternalContext().getSession(true);
        return httpSession;
    }

    protected final FacesContext getFacesContext() {
        facesContext = FacesContext.getCurrentInstance();
        return facesContext;
    }

    public final HttpServletRequest getHttpRequest() {
        httpRequest = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        if (httpRequest == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return httpRequest;
    }

    public HttpServletResponse getHttpResponse() {
        httpResponse = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        if (httpResponse == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return httpResponse;
    }

    public String getAcNoLength() {
        return acNoLength;
    }

    public void setAcNoLength(String acNoLength) {
        this.acNoLength = acNoLength;
    }

    public String getAcNoMaxLen() {
        return acNoMaxLen;
    }

    public void setAcNoMaxLen(String acNoMaxLen) {
        this.acNoMaxLen = acNoMaxLen;
    }
}
