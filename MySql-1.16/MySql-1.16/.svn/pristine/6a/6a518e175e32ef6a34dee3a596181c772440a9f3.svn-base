/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.dto.InterestProvisionTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ankit
 */
public class InterestProvision extends BaseBean {

    //private String fromDate;
    //private String toDate;
    private Date fromDate;
    private Date toDate;
    private String selectMonth;
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String year;
   // private String interestFrom;
    private DDSManagementFacadeRemote ipr;
    private String typeBox;
    private List<SelectItem> typeList;
    private int totalAc;
    private double totalAmount;
    private String msg;
    List<InterestProvisionTable> datagrid;
    private boolean cal, post, report;
    private String msgBox;
    private boolean flagMsgBox;
    private boolean Reportflag;
    private String viewID = "/pages/master/sm/test.jsp";
    Date date = new Date();
    boolean fromDisable;
    boolean toDisable;
    private final String jndiHomeName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote beanRemote = null;

    /**
     * Creates a new instance of InterestProvision
     */
    public InterestProvision() {
        try {
            ipr = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            Reportflag = false;

            typeList = new ArrayList<SelectItem>();
            getType();
            Date date = new Date();
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(dmyFormat.format(date));
            post = true;
            report = true;
            flagMsgBox = false;
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isReportflag() {
        return Reportflag;
    }

    public void setReportflag(boolean Reportflag) {
        this.Reportflag = Reportflag;
    }

    public boolean isFlagMsgBox() {
        return flagMsgBox;
    }

    public void setFlagMsgBox(boolean flagMsgBox) {
        this.flagMsgBox = flagMsgBox;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public String getMsgBox() {
        return msgBox;
    }

    public void setMsgBox(String msgBox) {
        this.msgBox = msgBox;
    }

    public boolean isCal() {
        return cal;
    }

    public void setCal(boolean cal) {
        this.cal = cal;
    }

    public boolean isPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalAc() {
        return totalAc;
    }

    public void setTotalAc(int totalAc) {
        this.totalAc = totalAc;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTypeBox() {
        return typeBox;
    }

    public void setTypeBox(String typeBox) {
        this.typeBox = typeBox;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSelectMonth() {
        return selectMonth;
    }

    public void setSelectMonth(String selectMonth) {
        this.selectMonth = selectMonth;
    }

//    public String getFromDate() {
//        return fromDate;
//    }
//
//    public void setFromDate(String fromDate) {
//        this.fromDate = fromDate;
//    }
//
//    public String getToDate() {
//        return toDate;
//    }
//
//    public void setToDate(String toDate) {
//        this.toDate = toDate;
//    }
    
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    
    public List<InterestProvisionTable> getDatagrid() {
        return datagrid;
    }

    public void setDatagrid(List<InterestProvisionTable> datagrid) {
        this.datagrid = datagrid;
    }

    public boolean isFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public boolean isToDisable() {
        return toDisable;
    }

    public void setToDisable(boolean toDisable) {
        this.toDisable = toDisable;
    }
    
    public void refresh() {
        //setYear("");
        //setSelectMonth("MARCH");
        post = true;
        cal = false;
        datagrid = null;
        totalAc = 0;
        totalAmount = 0.0;
        Reportflag = false;
        fromDate = null;
        toDate = null;
    }

    public void btnRefresh() {
        try {
            msg = "";
            refresh();
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void getType() {
        try {
            List resultList = ipr.getAcctType();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                typeList.add(new SelectItem(ele.get(0)));
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

//    public void calclulateDate() throws Exception {
//        try {
//            if (selectMonth.equalsIgnoreCase("MARCH")) {
//                fromDate = year + "0101";
//                toDate = year + "0331";
//            }
//            if (selectMonth.equalsIgnoreCase("JUNE")) {
//                fromDate = year + "0401";
//                toDate = year + "0630";
//            }
//            if (selectMonth.equalsIgnoreCase("SEPTEMBER")) {
//                fromDate = year + "0701";
//                toDate = year + "0930";
//            }
//            if (selectMonth.equalsIgnoreCase("DECEMBER")) {
//                fromDate = year + "1001";
//                toDate = year + "1231";
//            }
//        } catch (Exception e) {
//            setMsg(e.getLocalizedMessage());
//        }
//    }

    public void btnCalculate() throws Exception {
        try {
            msg = validation();
            if (msg.equalsIgnoreCase("ok")) {
                //calclulateDate();
                datagrid = new ArrayList<InterestProvisionTable>();
                datagrid = ipr.calculate(fromDate, toDate, typeBox, getOrgnBrCode());
                if (datagrid.isEmpty()) {
                    throw new ApplicationException("No data to calculate");
                }
                setMsg("Interest Calculated Successfully");
                totalAc = datagrid.size() - 1;
                for (InterestProvisionTable intObj : datagrid) {
                    totalAmount = totalAmount + intObj.getIamt();
                }
                Reportflag = true;
                post = false;
                cal = true;
                report = false;
                Vector ele = ipr.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Interest Provision Report");
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pBankname", ele.get(0).toString());
                fillParams.put("pBranchAddress", ele.get(1).toString());
                new ReportBean().jasperReport("Calculation_Report", "text/html", new JRBeanCollectionDataSource(datagrid), fillParams, "Interest Provision Report");
                this.setViewID("/report/ReportPagePopUp.jsp");
                setToDisable(true);
            }
        } catch (Exception e) {
            refresh();
            setMsg(e.getMessage());
        }
    }

    public void btnPosting() throws Exception {
        try {
            //calclulateDate();
            msg = ipr.post(datagrid, typeBox, fromDate, toDate, getOrgnBrCode(), getUserName());
            post = true;
            cal = true;
            refresh();
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public String validation() {
        try {
            if (fromDate == null) {
                Reportflag = false;
                return "From Date can not be blank";
            }
            if (toDate == null) {
                Reportflag = false;
                return "To Date can not be blank";
            } else if (toDate.after(date)) {
                Reportflag = false;
                return "To Date Can't Be Greater Current Date";
            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "ok";
    }

    public String exitAction() {
        try {
            btnRefresh();
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
        return "case1";
    }
    
    public void setDateAcTypeWise() {
        this.setMsg("");
        totalAc = 0;
        totalAmount = 0.0;
        datagrid = null;
        try {
            if (typeBox == null || typeBox.equalsIgnoreCase("")) {
                setMsg("Please Select Account Type.");
                return;
            }
            String fromDt = "";
            String toDt = "";
            if (!(typeBox == null) && !typeBox.equals("")) {
                fromDt = beanRemote.allFromDt(typeBox, getOrgnBrCode(), "f");
                if (fromDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMsg("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setFromDate(sdf.parse(sdf.format(ymdFormat.parse(fromDt))));
                    setFromDisable(true);
                }
                toDt = beanRemote.allFromDt(typeBox, getOrgnBrCode(), "t");
                if (toDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMsg("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setToDate(sdf.parse(sdf.format(ymdFormat.parse(toDt))));
                    setToDisable(true);
                }
            }
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }
}
