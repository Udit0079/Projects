/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.dto.AcTransferAuthPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.TDLienMarkingFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ankit Verma
 */
public class AcTransferAuthorization extends BaseBean {

    List<AcTransferAuthPojo> acTrfList;
    private String msg;
    int currentRow;
    TDLienMarkingFacadeRemote tdFacadeRemote;

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<AcTransferAuthPojo> getAcTrfList() {
        return acTrfList;
    }

    public void setAcTrfList(List<AcTransferAuthPojo> acTrfList) {
        this.acTrfList = acTrfList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AcTransferAuthorization() {
        try {
            tdFacadeRemote = (TDLienMarkingFacadeRemote) ServiceLocator.getInstance().lookup("TDLienMarkingFacade");
            getUnAuthDetails();
        } catch (Exception e) {
        }
    }
    public void getUnAuthDetails()
    {
        try {
            acTrfList=tdFacadeRemote.getUnAuthTrfList(getOrgnBrCode());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void changeValue() {
        AcTransferAuthPojo item = acTrfList.get(currentRow);
        if (item.getAuth().equalsIgnoreCase("N")) {
            boolean found = false;
            for (AcTransferAuthPojo item1 : acTrfList) {
                if (item1.getAuth().equalsIgnoreCase("Y")) {
                    found = true;
                }
            }
            if (found) {
                this.setMsg("Only one entry can be authorize at one time.");
            } else {
                item.setAuth("Y");
                acTrfList.remove(currentRow);
                acTrfList.add(currentRow, item);
            }
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            item.setAuth("N");
            acTrfList.remove(currentRow);
            acTrfList.add(currentRow, item);
            this.setMsg("");
        }
    }

    public void authorizeAcTransfer() {
        System.out.println("in ac transfer");
        int count=0;
        AcTransferAuthPojo authItem = new AcTransferAuthPojo();
        for (AcTransferAuthPojo item : acTrfList) {
            if (item.getAuth().equalsIgnoreCase("Y")) {
                authItem = item;
                ++count;
            }
        }
        if (count==0) {
            this.setMsg("Please select at least one entry for authorization");
        } else {
            try {
                if (this.getUserName().equalsIgnoreCase(authItem.getEnterBy())) {
                    this.setMsg("Please login with the different user.");
                } else {
                    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                    String result = tdFacadeRemote.authorizeAcTransfer(authItem, ymd.format(new Date()), getUserName(), getOrgnBrCode());
                    if (result.equalsIgnoreCase("TRUE")) {
                        this.setMsg("Authorization over");
                        getUnAuthDetails();
                    } else {
                        this.setMsg(result);
                    }
                }
            }  catch (Exception e) {
                e.printStackTrace();
                this.setMsg(e.getLocalizedMessage());
            }
        }
    }

    public void refreshForm() {
        msg="";
        getUnAuthDetails();
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }
}
