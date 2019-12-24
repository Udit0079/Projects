/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class InvoicenoGenerateMaster extends BaseBean {

    private String message;
    private String acNo;
    private String newAcno;
    private String customerName;
    private String generateDt;
    private String invoiceNo;
    private String finYear;
    private String option;
    private List<SelectItem> optionList;
    private boolean acnoDisable;
    private OtherMasterFacadeRemote remoteMasterFacade;
    private FtsPostingMgmtFacadeRemote ftsPosting;
    private TermDepositMasterFacadeRemote tdRemote;

    public InvoicenoGenerateMaster() {
        try {

            remoteMasterFacade = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            ftsPosting = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            tdRemote = (TermDepositMasterFacadeRemote) ServiceLocator.getInstance().lookup("TermDepositMasterFacade");

            optionList = new ArrayList<>();
            optionList.add(new SelectItem("0", "--Select--"));
            optionList.add(new SelectItem("I", "Individual A/ c No."));
            optionList.add(new SelectItem("B", "All GST Reg A/ c No."));

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void actionOnOption() {
        try {
            setMessage("");
            if (option == null || option.equalsIgnoreCase("0")) {
                setMessage("Please select option !!!");
                return;
            }
            if (option.equalsIgnoreCase("B")) {
                setAcnoDisable(true);
            } else {
                setAcnoDisable(false);
            }

            List fYearList = tdRemote.fYearData(getOrgnBrCode());
            Vector fVector = (Vector) fYearList.get(0);
            this.setFinYear(fVector.get(0).toString());

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void getAccountDetails() {
        try {
            if (acNo == null || acNo.equalsIgnoreCase("")) {
                setMessage("Please fill Account No.");
                return;
            }
            if (acNo.length() != 12) {
                setMessage("Please fill 12 digit Account No.");
                return;
            }
            String msg = ftsPosting.ftsAcnoValidate(this.acNo, 1, "");
            if (!msg.equalsIgnoreCase("true")) {
                setMessage(msg);
                return;
            }
            this.setNewAcno(acNo);
            this.setCustomerName(ftsPosting.getCustName(acNo));
            List fYearList = tdRemote.fYearData(getOrgnBrCode());
            Vector fVector = (Vector) fYearList.get(0);
            this.setFinYear(fVector.get(0).toString());
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void GenerateInvoiceNoProcess() {
        setMessage("");
        try {
            String result = remoteMasterFacade.savedGenerateInvoiceData(acNo, finYear, getOrgnBrCode(), getUserName(), option,"","");
            if (result.contains("true")) {
                String v[] = result.split(":");
                setMessage("Invoice No. Generate Sucessfully and Invoice No is : " + v[1]);
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        this.setAcNo("");
        this.setNewAcno("");
        this.setCustomerName("");
        this.setFinYear("");
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getGenerateDt() {
        return generateDt;
    }

    public void setGenerateDt(String generateDt) {
        this.generateDt = generateDt;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }

    public boolean isAcnoDisable() {
        return acnoDisable;
    }

    public void setAcnoDisable(boolean acnoDisable) {
        this.acnoDisable = acnoDisable;
    }
}
