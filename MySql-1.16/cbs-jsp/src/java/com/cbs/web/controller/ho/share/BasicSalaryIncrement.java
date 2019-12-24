/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class BasicSalaryIncrement extends BaseBean {

    private String message;
    private String placeOfWorking;
    private List<SelectItem> placeOfWorkingList;
    private String basicAmt;
    private Date appDt = new Date();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    ShareTransferFacadeRemote remoteObject;
    private CommonReportMethodsRemote common;

    public BasicSalaryIncrement() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            placeOfWorkingList = new ArrayList<SelectItem>();
            placeOfWorkingList.add(new SelectItem("ALL", "ALL"));
            List result = common.getRefRecList("019");
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    placeOfWorkingList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void saveBtnAction() {
        setMessage("");
        try {
            if (appDt == null) {
                this.setMessage("Please Fill Applicable Date");
                return;
            }
            if (appDt.after(sdf.parse(sdf.format(date)))) {
                this.setMessage("Applicable Date can not be greator than current date");
                return;
            }
            if (appDt.before(sdf.parse(sdf.format(date)))) {
                this.setMessage("Applicable Date can not be less than current date");
                return;
            }
            if (basicAmt == null || basicAmt.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Basic Amount");
                return;
            }
            String result = remoteObject.saveBasicIncrementAmtAction(placeOfWorking, ymd.format(appDt), basicAmt, getUserName());
            this.setMessage(result);
            this.setBasicAmt("");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefresh() {
        setMessage("");
        setAppDt(date);
        setBasicAmt("");
    }

    public String btnExit() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlaceOfWorking() {
        return placeOfWorking;
    }

    public void setPlaceOfWorking(String placeOfWorking) {
        this.placeOfWorking = placeOfWorking;
    }

    public List<SelectItem> getPlaceOfWorkingList() {
        return placeOfWorkingList;
    }

    public void setPlaceOfWorkingList(List<SelectItem> placeOfWorkingList) {
        this.placeOfWorkingList = placeOfWorkingList;
    }

    public Date getAppDt() {
        return appDt;
    }

    public void setAppDt(Date appDt) {
        this.appDt = appDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBasicAmt() {
        return basicAmt;
    }

    public void setBasicAmt(String basicAmt) {
        this.basicAmt = basicAmt;
    }
}
