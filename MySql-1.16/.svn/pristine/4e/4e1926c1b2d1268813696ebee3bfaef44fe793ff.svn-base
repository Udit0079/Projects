/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.facade.ho.master.CrrDailyEntryFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class DailyUpdationOfCRR extends BaseBean {

    private String message;
    private String fromDt;
    private String toDt;
    Date dt = new Date();
    private CrrDailyEntryFacadeRemote rfr;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public DailyUpdationOfCRR() {
        try {
            rfr = (CrrDailyEntryFacadeRemote) ServiceLocator.getInstance().lookup("CrrDailyEntryFacade");
            refresh();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    private void getDate() {
        try {
            List maxFridayList = rfr.getDateData();
            if (!maxFridayList.isEmpty()) {
                Vector element = (Vector) maxFridayList.get(0);
                this.setToDt(element.get(0).toString());
            } else {
                this.setMessage("Friday date not found !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void updateProcess() {
        try {
            if (this.fromDt == null || this.fromDt.equals("")) {
                this.setMessage("Please fill From Date !");
                return;
            }
            if (this.toDt == null || this.toDt.equals("")) {
                this.setMessage("Please fill To Date !");
                return;
            }
            String result = rfr.dataUpdate(ymd.format(dmy.parse(this.fromDt)), ymd.format(dmy.parse(this.toDt)), getUserName(), getOrgnBrCode());
            this.setMessage(result);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refresh() {
        try {
            this.setMessage("");
            this.setFromDt("");
            this.setToDt("");
            getDate();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String exitForm() {
        try {
            refresh();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "case1";
    }
}
