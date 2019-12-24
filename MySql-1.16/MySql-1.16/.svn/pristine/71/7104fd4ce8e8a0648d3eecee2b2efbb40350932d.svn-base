/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.master.CrrDailyEntryFacadeRemote;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.CrrSlrGrid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author saurabhsipl
 */
public class CrrSlrPerc extends BaseBean {

    private String message;
    private String wefdate;
    private String crrPercentage;
    private String slrPercentage;
    private List<CrrSlrGrid> crrslrData;
    private CrrDailyEntryFacadeRemote remoteObj = null;
    private DailyMasterFacadeRemote remoteBeanObj = null;
    private FtsPostingMgmtFacadeRemote ftsBeanRemote = null;
    Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

    public CrrSlrPerc() {
        this.setMessage("");
        try {
            remoteObj = (CrrDailyEntryFacadeRemote) ServiceLocator.getInstance().lookup("CrrDailyEntryFacade");
            remoteBeanObj = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            onLoadData();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onLoadData() {
        try {
            crrslrData = new ArrayList<CrrSlrGrid>();
            List list = remoteObj.gethoCrrSlrData();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    CrrSlrGrid obj = new CrrSlrGrid();
                    obj.setSrlNo(i+1);
                    obj.setWefDate(vtr.get(0).toString());
                    obj.setCrrPercent(Double.parseDouble(vtr.get(1).toString()));
                    obj.setSlrPercent(Double.parseDouble(vtr.get(2).toString()));
                    crrslrData.add(obj);
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCrrPercentage() {
        return crrPercentage;
    }

    public void setCrrPercentage(String crrPercentage) {
        this.crrPercentage = crrPercentage;
    }

    public String getSlrPercentage() {
        return slrPercentage;
    }

    public void setSlrPercentage(String slrPercentage) {
        this.slrPercentage = slrPercentage;
    }

    public String getWefdate() {
        return wefdate;
    }

    public void setWefdate(String wefdate) {
        this.wefdate = wefdate;
    }

    public List<CrrSlrGrid> getCrrslrData() {
        return crrslrData;
    }

    public void setCrrslrData(List<CrrSlrGrid> crrslrData) {
        this.crrslrData = crrslrData;
    }

    public void processSave() {
        this.setMessage("");
        try {
            String msg = remoteObj.saveCrrSlr(ymd.format(dmy.parse(wefdate)), crrPercentage, slrPercentage);
            if (msg.equalsIgnoreCase("true")) {
                this.setMessage("Data has been saved successfully !");
            } else {
                this.setMessage(msg);
            }
            onLoadData();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setWefdate("");
        this.setCrrPercentage("");
        this.setSlrPercentage("");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
