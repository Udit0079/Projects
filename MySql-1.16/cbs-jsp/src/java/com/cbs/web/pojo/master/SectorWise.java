/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.master;

import com.cbs.servlets.Init;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/*
 * @author root
 */

public class SectorWise {

    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



  
    public SectorWise() {
        try {
                req = getRequest();
                String orgnBrIp = WebUtil.getClientIP(req);
                InetAddress localhost = InetAddress.getByName(orgnBrIp);
                orgnBrCode = Init.IP2BR.getBranch(localhost);
                setUserName(req.getRemoteUser());
                setUserName("manager1");
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                setTodayDate(sdf.format(date));

            } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

}
