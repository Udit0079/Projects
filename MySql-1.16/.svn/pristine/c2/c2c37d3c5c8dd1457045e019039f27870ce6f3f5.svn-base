package com.cbs.web.controller.report.audit;

import com.cbs.dto.report.SearchReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.AuditReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
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
 * @author Zeeshan Waris
 */
public final class SearchReport extends BaseBean {

    private String message;
    private Date calFromDate;
    private Date caltoDate;
    private Date date = new Date();
    AuditReportFacadeRemote beanRemote;
    private String ddEnterBy, ddAuthby, ddTransaction, ddType, instNo, fromAmt, toAmt;
    private boolean chenterBy, chAuthBy, chTransaction, chType, chInstNo, chenterByDisable, chAuthByDisable, chTransactionDisable, chTypeDisable, chInstNoDisable;
    private List<SelectItem> enterByList;
    private List<SelectItem> transactionList;
    private List<SelectItem> typeList;
    private String branch;
    private List<SelectItem> branchList;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

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

    public boolean isChAuthByDisable() {
        return chAuthByDisable;
    }

    public void setChAuthByDisable(boolean chAuthByDisable) {
        this.chAuthByDisable = chAuthByDisable;
    }

    public boolean isChInstNoDisable() {
        return chInstNoDisable;
    }

    public void setChInstNoDisable(boolean chInstNoDisable) {
        this.chInstNoDisable = chInstNoDisable;
    }

    public boolean isChTransactionDisable() {
        return chTransactionDisable;
    }

    public void setChTransactionDisable(boolean chTransactionDisable) {
        this.chTransactionDisable = chTransactionDisable;
    }

    public boolean isChTypeDisable() {
        return chTypeDisable;
    }

    public void setChTypeDisable(boolean chTypeDisable) {
        this.chTypeDisable = chTypeDisable;
    }

    public boolean isChenterByDisable() {
        return chenterByDisable;
    }

    public void setChenterByDisable(boolean chenterByDisable) {
        this.chenterByDisable = chenterByDisable;
    }

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public boolean isChAuthBy() {
        return chAuthBy;
    }

    public void setChAuthBy(boolean chAuthBy) {
        this.chAuthBy = chAuthBy;
    }

    public boolean isChInstNo() {
        return chInstNo;
    }

    public void setChInstNo(boolean chInstNo) {
        this.chInstNo = chInstNo;
    }

    public boolean isChTransaction() {
        return chTransaction;
    }

    public void setChTransaction(boolean chTransaction) {
        this.chTransaction = chTransaction;
    }

    public boolean isChType() {
        return chType;
    }

    public void setChType(boolean chType) {
        this.chType = chType;
    }

    public boolean isChenterBy() {
        return chenterBy;
    }

    public void setChenterBy(boolean chenterBy) {
        this.chenterBy = chenterBy;
    }

    public String getDdAuthby() {
        return ddAuthby;
    }

    public void setDdAuthby(String ddAuthby) {
        this.ddAuthby = ddAuthby;
    }

    public String getDdEnterBy() {
        return ddEnterBy;
    }

    public void setDdEnterBy(String ddEnterBy) {
        this.ddEnterBy = ddEnterBy;
    }

    public String getDdTransaction() {
        return ddTransaction;
    }

    public void setDdTransaction(String ddTransaction) {
        this.ddTransaction = ddTransaction;
    }

    public String getDdType() {
        return ddType;
    }

    public void setDdType(String ddType) {
        this.ddType = ddType;
    }

    public List<SelectItem> getEnterByList() {
        return enterByList;
    }

    public void setEnterByList(List<SelectItem> enterByList) {
        this.enterByList = enterByList;
    }

    public String getFromAmt() {
        return fromAmt;
    }

    public void setFromAmt(String fromAmt) {
        this.fromAmt = fromAmt;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getToAmt() {
        return toAmt;
    }

    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }

    public List<SelectItem> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<SelectItem> transactionList) {
        this.transactionList = transactionList;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public SearchReport() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (AuditReportFacadeRemote) ServiceLocator.getInstance().lookup("AuditReportFacade");
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("0", "Cr"));
            typeList.add(new SelectItem("1", "Dr"));
            transactionList = new ArrayList<SelectItem>();
            transactionList.add(new SelectItem("0", "Cash"));
            transactionList.add(new SelectItem("1", "Clearing"));
            transactionList.add(new SelectItem("2", "Transfer"));
            chenterByDisable = true;
            chAuthByDisable = true;
            chTransactionDisable = true;
            chTypeDisable = true;
            chInstNoDisable = true;
            calFromDate = date;
            caltoDate = date;

            branchList = new ArrayList<SelectItem>();
            List list = common.getAlphacodeBasedOnOrgnBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }

            setMessage("");
            // getAccountypeList();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        try {
            if (chInstNo == true) {
                if (instNo == null || instNo.equalsIgnoreCase("")) {
                    this.setMessage("Please Fill Inst No.");
                    return null;
                }
            }
            if (ddTransaction == null) {
                this.setMessage("Please Select Transaction");
                return null;
            }
            if (fromAmt == null || fromAmt.equalsIgnoreCase("")) {
                this.setMessage("Please Fill the From Amount");
                return null;

            }
            if (toAmt == null || toAmt.equalsIgnoreCase("")) {
                this.setMessage("Please Fill the To Amount");
                return null;
            }

            if (!fromAmt.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in From Amount");
                return null;
            }
            if (!toAmt.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in To Amount");
                return null;
            }

            if (Double.parseDouble(fromAmt) > Double.parseDouble(toAmt)) {
                this.setMessage("From Amount Should Be Less Then To Amount");
                return null;
            }
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (calFromDate.after(caltoDate)) {
                this.setMessage("From Date can not be greater than To Date.");
                return null;
            }
            String EnterBy;
            String AuthBy;
            String typeTrn;
            String tran;
            String insType;
            String chEnterby;
            String chAuthby;
            String chTrn;
            String checkBoxType;
            String checkBoxInstNo;
            if (chenterBy == true) {
                chEnterby = "true";
                EnterBy = ddEnterBy;
            } else {
                EnterBy = "";
                chEnterby = "false";
            }
            if (chAuthBy == true) {
                chAuthby = "true";
                AuthBy = ddAuthby;
            } else {
                chAuthby = "false";
                AuthBy = "";
            }
            if (chTransaction == true) {
                chTrn = "true";
                tran = ddTransaction;
            } else {
                chTrn = "false";
                tran = "";
            }
            if (chType == true) {
                checkBoxType = "true";
                typeTrn = ddType;
            } else {
                checkBoxType = "false";
                typeTrn = "";
            }
            if (chInstNo == true) {
                checkBoxInstNo = "true";
                insType = instNo;
            } else {
                checkBoxInstNo = "false";
                insType = "";
            }
            List<SearchReportPojo> searchReport = beanRemote.searchReport(EnterBy, chEnterby, AuthBy, chAuthby, tran, chTrn, typeTrn,
                    checkBoxType, insType, checkBoxInstNo, Double.parseDouble(fromAmt),
                    Double.parseDouble(toAmt), ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branch);
            if (!searchReport.isEmpty()) {

                if (ddTransaction.equalsIgnoreCase("0")) {
                    ddTransaction = "Cash";
                } else if (ddTransaction.equalsIgnoreCase("1")) {
                    ddTransaction = "Clearing";
                } else if (ddTransaction.equalsIgnoreCase("2")) {
                    ddTransaction = "Transfer";
                }
                String repName = "Search Report";
                Map fillParams = new HashMap();
                fillParams.put("repName", repName);
                fillParams.put("printedBy", getUserName());
                fillParams.put("amtFrm", Float.parseFloat(toAmt));
                fillParams.put("amtTo", Float.parseFloat(toAmt));
                fillParams.put("reportDt", sdf.format(calFromDate));
                fillParams.put("tooDate", sdf.format(caltoDate));
                fillParams.put("pType", ddTransaction);
                new ReportBean().jasperReport("searchReport", "text/html", new JRBeanCollectionDataSource(searchReport), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void btnPdfAction() {
        setMessage("");
        try {
            if (chInstNo == true) {
                if (instNo == null || instNo.equalsIgnoreCase("")) {
                    this.setMessage("Please Fill Inst No.");
                    return;
                }
            }
            if (ddTransaction == null) {
                this.setMessage("Please Select Transaction");
                return;
            }
            if (fromAmt == null || fromAmt.equalsIgnoreCase("")) {
                this.setMessage("Please Fill the From Amount");
                return;

            }
            if (toAmt == null || toAmt.equalsIgnoreCase("")) {
                this.setMessage("Please Fill the To Amount");
                return;
            }

            if (!fromAmt.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in From Amount");
                return;
            }
            if (!toAmt.matches("[0-9.]*")) {
                this.setMessage("Please Enter Numeric Value in To Amount");
                return;
            }

            if (Double.parseDouble(fromAmt) > Double.parseDouble(toAmt)) {
                this.setMessage("From Amount Should Be Less Then To Amount");
                return;
            }
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (calFromDate.after(caltoDate)) {
                this.setMessage("From Date can not be greater than To Date.");
                return;
            }
            String EnterBy;
            String AuthBy;
            String typeTrn;
            String tran;
            String insType;
            String chEnterby;
            String chAuthby;
            String chTrn;
            String checkBoxType;
            String checkBoxInstNo;
            if (chenterBy == true) {
                chEnterby = "true";
                EnterBy = ddEnterBy;
            } else {
                EnterBy = "";
                chEnterby = "false";
            }
            if (chAuthBy == true) {
                chAuthby = "true";
                AuthBy = ddAuthby;
            } else {
                chAuthby = "false";
                AuthBy = "";
            }
            if (chTransaction == true) {
                chTrn = "true";
                tran = ddTransaction;
            } else {
                chTrn = "false";
                tran = "";
            }
            if (chType == true) {
                checkBoxType = "true";
                typeTrn = ddType;
            } else {
                checkBoxType = "false";
                typeTrn = "";
            }
            if (chInstNo == true) {
                checkBoxInstNo = "true";
                insType = instNo;
            } else {
                checkBoxInstNo = "false";
                insType = "";
            }
            List<SearchReportPojo> searchReport = beanRemote.searchReport(EnterBy, chEnterby, AuthBy, chAuthby, tran, chTrn, typeTrn,
                    checkBoxType, insType, checkBoxInstNo, Double.parseDouble(fromAmt),
                    Double.parseDouble(toAmt), ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), branch);
            if (!searchReport.isEmpty()) {

                if (ddTransaction.equalsIgnoreCase("0")) {
                    ddTransaction = "Cash";
                } else if (ddTransaction.equalsIgnoreCase("1")) {
                    ddTransaction = "Clearing";
                } else if (ddTransaction.equalsIgnoreCase("2")) {
                    ddTransaction = "Transfer";
                }
                String repName = "Search Report";
                Map fillParams = new HashMap();
                fillParams.put("repName", repName);
                fillParams.put("printedBy", getUserName());
                fillParams.put("amtFrm", Float.parseFloat(toAmt));
                fillParams.put("amtTo", Float.parseFloat(toAmt));
                fillParams.put("reportDt", sdf.format(calFromDate));
                fillParams.put("tooDate", sdf.format(caltoDate));
                fillParams.put("pType", ddTransaction);
                new ReportBean().openPdf("Search Report_" + ymdFormat.format(calFromDate) + " to " + ymdFormat.format(caltoDate), "searchReport", new JRBeanCollectionDataSource(searchReport), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
            } else {
                setMessage("No data to print");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return;
    }

    public void getAccountypeList() {
        try {
            List levelId = beanRemote.getLevelId(branch);
            enterByList = new ArrayList<SelectItem>();
            if (!levelId.isEmpty()) {
                for (int i = 0; i < levelId.size(); i++) {
                    Vector ele = (Vector) levelId.get(i);
                    enterByList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }
            } else {
                enterByList.add(new SelectItem("0", "--Select--"));
            }
            enterByList.add(new SelectItem("SYSTEM", "SYSTEM"));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void chkEnterbyprocessValueChange() {
        if (chenterBy == true) {
            chenterByDisable = false;
        } else {
            chenterByDisable = true;
        }
    }

    public void chkAuthbyprocessValueChange() {
        if (chAuthBy == true) {
            chAuthByDisable = false;
        } else {
            chAuthByDisable = true;
        }
    }

    public void chkTransactionprocessValueChange() {
        if (chTransaction == true) {
            chTransactionDisable = false;
        } else {
            chTransactionDisable = true;
        }
    }

    public void chkTypeprocessValueChange() {
        if (chType == true) {
            chTypeDisable = false;
        } else {
            chTypeDisable = true;
        }
    }

    public void chkInstNoprocessValueChange() {
        if (chInstNo == true) {
            chInstNoDisable = false;
        } else {
            chInstNoDisable = true;
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            chenterBy = false;
            chAuthBy = false;
            chTransaction = false;
            chType = false;
            chInstNo = false;
            chenterByDisable = true;
            chAuthByDisable = true;
            chTransactionDisable = true;
            chTypeDisable = true;
            chInstNoDisable = true;
            calFromDate = date;
            caltoDate = date;
            fromAmt = "";
            toAmt = "";
            enterByList = new ArrayList<SelectItem>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
