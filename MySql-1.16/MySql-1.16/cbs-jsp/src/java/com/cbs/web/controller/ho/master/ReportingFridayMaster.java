/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.master.ReportingFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

public class ReportingFridayMaster extends BaseBean {

    private String message;
    private String accDescType;
    private String balType;
    private List<SelectItem> accountDescList = new ArrayList<SelectItem>();
    private List<SelectItem> balanceTypeList = new ArrayList<SelectItem>();
    private ReportingFacadeRemote reportingFridayService = null;

    public List<SelectItem> getAccountDescList() {
        return accountDescList;
    }

    public void setAccountDescList(List<SelectItem> accountDescList) {
        this.accountDescList = accountDescList;
    }

    public List<SelectItem> getBalanceTypeList() {
        return balanceTypeList;
    }

    public void setBalanceTypeList(List<SelectItem> balanceTypeList) {
        this.balanceTypeList = balanceTypeList;
    }

    public String getAccDescType() {
        return accDescType;
    }

    public void setAccDescType(String accDescType) {
        this.accDescType = accDescType;
    }

    public String getBalType() {
        return balType;
    }

    public void setBalType(String balType) {
        this.balType = balType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getaccDescType() {
        return accDescType;
    }

    public void setaccDescType(String accDescType) {
        this.accDescType = accDescType;
    }

    public String getbalType() {
        return balType;
    }

    public void setbalType(String balType) {
        this.balType = balType;
    }

    /** Creates a new instance of ReportingFridayMaster */
    public ReportingFridayMaster() {
        try {
            reportingFridayService = (ReportingFacadeRemote) ServiceLocator.getInstance().lookup("ReportingFacade");
            getAcDesc();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getAcDesc() {
        try {
            accountDescList = new ArrayList<SelectItem>();
            balanceTypeList = new ArrayList<SelectItem>();
            fillBalanceTypeList();
            fillAccountDescList();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void fillBalanceTypeList() {
        try {
            balanceTypeList.add(new SelectItem("--SELECT--"));
            balanceTypeList.add(new SelectItem("NET BALANCE"));
            balanceTypeList.add(new SelectItem("CREDIT BALANCE"));
            balanceTypeList.add(new SelectItem("DEBIT BALANCE"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillAccountDescList() {
        try {
            List listForAcDesc = reportingFridayService.getTableDetails();
            accountDescList.add(new SelectItem("--SELECT--"));
            for (int i = 0; i < listForAcDesc.size(); i++) {
                Vector element = (Vector) listForAcDesc.get(i);
                String acdesc = element.get(0).toString();
                String acno = element.get(1).toString();
                String con = acdesc + "---" + acno;
                accountDescList.add(new SelectItem(con));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveData() {
        this.setMessage("");
        try {
            if (this.accDescType.equals("--SELECT--")) {
                this.setMessage("Please select account description !");
                return;
            }
            if (this.balType.equals("--SELECT--")) {
                this.setMessage("Please select balance type !");
                return;
            }
            String acDescr = this.getaccDescType();
            String balStr = this.getbalType();
            int a = acDescr.indexOf("-");
            int last = acDescr.length();
            String acDescr1 = acDescr.substring(0, a);
            String acDescr2 = acDescr.substring(a + 3, last);
            String result = reportingFridayService.saveRecord(acDescr1, getOrgnBrCode() + acDescr2, balStr);
            this.setbalType("--SELECT--");
            this.setaccDescType("--SELECT--");
            this.setMessage(result);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void deleteData() {
        this.setMessage("");
        try {
            if (this.accDescType.equals("--SELECT--")) {
                this.setMessage("Please select account description !");
                return;
            }
            if (this.balType.equals("--SELECT--")) {
                this.setMessage("Please select balance type !");
                return;
            }
            String acDescr = this.getaccDescType();
            String balStr = this.getbalType();
            int a = acDescr.indexOf("-");
            int last = acDescr.length();
            String acDescr2 = acDescr.substring(a + 3, last);

            String result = reportingFridayService.deleteRecord(getOrgnBrCode() + acDescr2, balStr);
            this.setbalType("--SELECT--");
            this.setaccDescType("--SELECT--");
            this.setMessage(result);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        this.setaccDescType("--SELECT--");
        this.setbalType("--SELECT--");
        this.setMessage("");
    }

    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}
