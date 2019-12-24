/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.master.HoCircularDetailsRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.ho.CircularDetailPojo;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author deepshikha
 */
public class CircularDetails {

    private String orgBrnCode;
    private HttpServletRequest request;
    private String todayDate;
    private String userName;
    private String messagea;
    private String circularno;
    private String circulardetail;
    private List<CircularDetailPojo> circularList;
    HoCircularDetailsRemote hoCircularDetailsRem;

    public List<CircularDetailPojo> getCircularList() {
        return circularList;
    }

    public void setCircularList(List<CircularDetailPojo> circularList) {
        this.circularList = circularList;
    }

    public String getCirculardetail() {
        return circulardetail;
    }

    public void setCirculardetail(String circulardetail) {
        this.circulardetail = circulardetail;
    }

    public String getCircularno() {
        return circularno;
    }

    public void setCircularno(String circularno) {
        this.circularno = circularno;
    }

    public String getMessagea() {
        return messagea;
    }

    public void setMessagea(String messagea) {
        this.messagea = messagea;
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

    public CircularDetails() {
        try {
            hoCircularDetailsRem = (HoCircularDetailsRemote) ServiceLocator.getInstance().lookup("HoCircularDetails");
            request = getRequest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            circularList = new ArrayList<CircularDetailPojo>();
            getDetails();
        } catch (Exception e) {
            this.setMessagea(e.getLocalizedMessage());
        }
    }

    private HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    private void getDetails() {
        try {
            circularList = new ArrayList<CircularDetailPojo>();
            List issPassListl = hoCircularDetailsRem.selectRecord();
            Vector vtr;
            for (int i = 0; i < issPassListl.size(); i++) {
                vtr = (Vector) issPassListl.get(i);
                CircularDetailPojo detailObj = new CircularDetailPojo();
                detailObj.setNo(vtr.get(0).toString());
                detailObj.setCirNo(vtr.get(1).toString());
                detailObj.setCirDtl(vtr.get(2).toString());
                circularList.add(detailObj);
            }
        } catch (ApplicationException ex) {
            this.setMessagea(ex.getMessage());
        } catch (Exception ex) {
            this.setMessagea(ex.getMessage());
        }
    }

    public void saveDetails() {
        if(validate()){
        try {
            
            String msg = hoCircularDetailsRem.saveData(circularno, circulardetail);
            if (!msg.equalsIgnoreCase("Data has been saved.")) {
                this.setMessagea(msg);
                return;
            } else {
                this.setMessagea(msg);
                getDetails();
            }
        } catch (ApplicationException ex) {
            this.setMessagea(ex.getMessage());
        } catch (Exception ex) {
            this.setMessagea(ex.getMessage());
        }
    }
    }
    public String exitBtn() {
        return "case1";

    }

    private boolean validate() {
        if (circularno == null || circularno.trim().equals("")) {
     messagea="Enter Circular No.";
            return false;
        } else if (circulardetail == null || circulardetail.trim().equals("")) {
     messagea="Enter Circular Detail";
            return false;
        }
        return true;
    }
    


}
