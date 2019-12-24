/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.pojo.VoucherPrintingPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class VoucherPrinting extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String txnMode;
    private List<SelectItem> txnModeList;
    private String prinType;
    private List<SelectItem> prinTypList;
    private String lableButtom;
    private String lable;
    private List<SelectItem> lableList;
    private String curDate;
    private Date date = new Date();
    private CommonReportMethodsRemote common;
    private DDSReportFacadeRemote ddsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<VoucherPrintingPojo> reportList = new ArrayList<VoucherPrintingPojo>();

    public VoucherPrinting() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            branchList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString() : vec.get(1).toString(), vec.get(0).toString()));
                }
            }
            txnModeList = new ArrayList<SelectItem>();
            txnModeList.add(new SelectItem("S", "--Select--"));
            txnModeList.add(new SelectItem("0", "Cash"));
            txnModeList.add(new SelectItem("1", "Clearing"));
            txnModeList.add(new SelectItem("2", "Transfer"));
            setLableButtom("Voucher");
//            prinTypList = new ArrayList<SelectItem>();
//            prinTypList.add(new SelectItem("S", "--Select--"));
//            prinTypList.add(new SelectItem("B", "Batch No"));
//            prinTypList.add(new SelectItem("V", "Voucher"));
            refreshPage();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void onReportAction() {
        setMessage("");
        if (txnMode.equalsIgnoreCase("S")) {
            setMessage("Please select Txn mode !");
            return;
        }
        if (txnMode.equalsIgnoreCase("2")) {
            this.lableButtom = "Batch No";
        } else {
            this.lableButtom = "Voucher";
        }
        try {
            lableList = new ArrayList<SelectItem>();
            List list = common.getBatchVoucherNo(this.txnMode, branch, ymd.format(date));
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    lableList.add(new SelectItem(vec.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void printReport() {
        setMessage("");
        try {
            if (txnMode.equalsIgnoreCase("S")) {
                setMessage("Please select Txn mode !");
                return;
            }
            reportList = ddsRemote.getVoucherPrintingData(branch, txnMode, ymd.format(date), lable);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            Map fillParams = new HashMap();
            String alphCode = common.getAlphacodeByBrncode(branch);
            List brNameAndAddList = common.getBnkNameAdd(alphCode);
            Vector vtr = (Vector) brNameAndAddList.get(0);
            fillParams.put("pBankName", vtr.get(0).toString().toUpperCase() + " - " + vtr.get(1).toString().toUpperCase());
            fillParams.put("pTxnMode", txnMode);
            fillParams.put("pTxnNo", lable);

            new ReportBean().openPdf("Voucher Printing_" + alphCode + "_" + ymd.format(date), "VoucherPrinting", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Voucher Printing");
            setTxnMode("S");


        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void refreshPage() {
        setMessage("");
        setTxnMode("S");
    }

    public String exitPage() {

        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrinType() {
        return prinType;
    }

    public void setPrinType(String prinType) {
        this.prinType = prinType;
    }

    public List<SelectItem> getPrinTypList() {
        return prinTypList;
    }

    public void setPrinTypList(List<SelectItem> prinTypList) {
        this.prinTypList = prinTypList;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public List<SelectItem> getLableList() {
        return lableList;
    }

    public void setLableList(List<SelectItem> lableList) {
        this.lableList = lableList;
    }

    public String getLableButtom() {
        return lableButtom;
    }

    public void setLableButtom(String lableButtom) {
        this.lableButtom = lableButtom;
    }

    public String getTxnMode() {
        return txnMode;
    }

    public void setTxnMode(String txnMode) {
        this.txnMode = txnMode;
    }

    public List<SelectItem> getTxnModeList() {
        return txnModeList;
    }

    public void setTxnModeList(List<SelectItem> txnModeList) {
        this.txnModeList = txnModeList;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }
}
