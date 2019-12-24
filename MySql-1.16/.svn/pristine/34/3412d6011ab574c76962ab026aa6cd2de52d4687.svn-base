/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanDisbursementPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
 * @author Athar Reza
 */
public class LoanDisbursement extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acType;
    List<SelectItem> acTypeList;
    private String orderBy;
    List<SelectItem> orderByList;
    private String disFromDate;
    private String disToDate;
    private double disFrAmount;
    private double disToAmount;
    private String message;
    Date date = new Date();
    private String display = "";
    private boolean amountDisable;
    private CommonReportMethodsRemote comman;
    private LoanReportFacadeRemote disburseFacade;
    private TdReceiptManagementFacadeRemote RemoteCode;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/mm/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyymmdd");
    List<LoanDisbursementPojo> disburseList = new ArrayList<LoanDisbursementPojo>();
    private String  acNature;
    List<SelectItem> acNatureList;
    private List tempList;
    private Vector tempVector;

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }
    
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getDisFrAmount() {
        return disFrAmount;
    }

    public void setDisFrAmount(double disFrAmount) {
        this.disFrAmount = disFrAmount;
    }

    public double getDisToAmount() {
        return disToAmount;
    }

    public void setDisToAmount(double disToAmount) {
        this.disToAmount = disToAmount;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public boolean isAmountDisable() {
        return amountDisable;
    }

    public void setAmountDisable(boolean amountDisable) {
        this.amountDisable = amountDisable;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDisFromDate() {
        return disFromDate;
    }

    public void setDisFromDate(String disFromDate) {
        this.disFromDate = disFromDate;
    }

    public String getDisToDate() {
        return disToDate;
    }

    public void setDisToDate(String disToDate) {
        this.disToDate = disToDate;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public LoanDisbursement() {

        try {
            acNatureList = new ArrayList<SelectItem>();
            acTypeList= new ArrayList<SelectItem>();
            acNatureList.clear();
            acTypeList.clear();
            this.setDisFromDate(getTodayDate());
            this.setDisToDate(getTodayDate());
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            comman = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            disburseFacade = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");

            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("Select", "--Select--"));
            orderByList.add(new SelectItem("0", "A/c No"));
            orderByList.add(new SelectItem("1", "Disbursed Amount"));
            orderByList.add(new SelectItem("2", "Disbursement Date"));

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            tempList = comman.getAcctNatureOnlyDB();
            if (!tempList.isEmpty()) {
                acNatureList.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
              tempList = comman.getAcctTypeAsAcNatureOnlyDB(acType);
              
              
            if (!tempList.isEmpty()) {
                acTypeList.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acTypeList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
            

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }


       public void blurAcctNature() {
        if (acTypeList != null) {
            acTypeList.clear();            
        }
        Vector vtr = null;
        try {
            List result = null;
            result = comman.getAcctTypeAsAcNatureOnlyDB(acNature);
            acTypeList.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                acTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));                
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
     
       
       
    
    public void printView() {
        String branchName = "";
        String address = "";
        try {
            List brNameAdd = new ArrayList();
            brNameAdd = comman.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            if (this.disFromDate == null) {
                setMessage("Please insert Disbursement From Date !");
                return;
            }
            boolean res = new Validator().validateDate_dd_mm_yyyy(this.disFromDate);
            if (res == false) {
                this.setMessage("Please select proper from date !");
                return;
            }
            if (this.disToDate == null) {
                setMessage("Please insert Disbursement To Date !");
                return;
            }
            res = new Validator().validateDate_dd_mm_yyyy(this.disToDate);
            if (res == false) {
                this.setMessage("Please select proper to date !");
                return;
            }

//            if(dmy.parse(disFromDate).after(dmy.parse(disToDate))){
//                this.setMessage("Please check from date !");
//                return;
//            }

//            int comValue = dmy.parse(disFromDate).compareTo(dmy.parse(disToDate));
//            if (comValue > 0) {
//                this.setMessage("Please check from date !")
//                return;
//            }

            if (disFrAmount == 0) {
                setMessage("Please fill the proper Disbursed From Amount!");
                return;
            }
            if (disToAmount == 0) {
                setMessage("Please fill the proper Disbresed To Amount!");
                return;
            }
            try {
                if (this.acType.equalsIgnoreCase("--Select--")) {
                    setMessage("Please fill the A/C Type!");
                    return;
                }
                if (this.orderBy.equalsIgnoreCase("Select")) {
                    setMessage("Please fill the Order By!");
                    return;
                }
            } catch (Exception e) {
                setMessage(e.getMessage());
            }
            String report = "Loan Disbursement Report";
            Map fillmap = new HashMap();
            fillmap.put("pDisfrDate", this.disFromDate);
            fillmap.put("pDistoDate", this.disToDate);
            fillmap.put("pSysDate", this.getTodayDate());
            fillmap.put("pBranchName", branchName);
            fillmap.put("pAddress", address);
            fillmap.put("pRepName", report);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pDisbfrAmt", disFrAmount);
            fillmap.put("pDisbtoAmt", disToAmount);

            String dd = this.disFromDate.substring(0, 2);
            String mm = this.disFromDate.substring(3, 5);
            String yy = this.disFromDate.substring(6, 10);
            String disfrmDt = yy + mm + dd;
            String dd1 = this.disToDate.substring(0, 2);
            String mm1 = this.disToDate.substring(3, 5);
            String yy1 = this.disToDate.substring(6, 10);
            String distoDt = yy1 + mm1 + dd1;

            disburseList = disburseFacade.getDisburseList(this.acType,this.acNature, this.disFrAmount, this.disToAmount, disfrmDt, distoDt, this.orderBy, branchCode);
            if (disburseList.isEmpty()) {
                message = "No Data found in databse";
                return;
            }
            new ReportBean().jasperReport("LoanDisbursement", "text/html",
                    new JRBeanCollectionDataSource(disburseList), fillmap, "Loan Disbursement Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        String branchName = "";
        String address = "";
        try {
            List brNameAdd = new ArrayList();
            brNameAdd = comman.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            if (this.disFromDate == null) {
                setMessage("Please insert Disbursement From Date !");
                return;
            }
            boolean res = new Validator().validateDate_dd_mm_yyyy(this.disFromDate);
            if (res == false) {
                this.setMessage("Please select proper from date !");
                return;
            }
            if (this.disToDate == null) {
                setMessage("Please insert Disbursement To Date !");
                return;
            }
            res = new Validator().validateDate_dd_mm_yyyy(this.disToDate);
            if (res == false) {
                this.setMessage("Please select proper to date !");
                return;
            }

            if (disFrAmount == 0) {
                setMessage("Please fill the proper Disbursed From Amount!");
                return;
            }
            if (disToAmount == 0) {
                setMessage("Please fill the proper Disbresed To Amount!");
                return;
            }
            try {
                if (this.acType.equalsIgnoreCase("--Select--")) {
                    setMessage("Please fill the A/C Type!");
                    return;
                }
                if (this.orderBy.equalsIgnoreCase("Select")) {
                    setMessage("Please fill the Order By!");
                    return;
                }
            } catch (Exception e) {
                setMessage(e.getMessage());
            }
            String report = "Loan Disbursement Report";
            Map fillmap = new HashMap();
            fillmap.put("pDisfrDate", this.disFromDate);
            fillmap.put("pDistoDate", this.disToDate);
            fillmap.put("pSysDate", this.getTodayDate());
            fillmap.put("pBranchName", branchName);
            fillmap.put("pAddress", address);
            fillmap.put("pRepName", report);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pDisbfrAmt", disFrAmount);
            fillmap.put("pDisbtoAmt", disToAmount);

            String dd = this.disFromDate.substring(0, 2);
            String mm = this.disFromDate.substring(3, 5);
            String yy = this.disFromDate.substring(6, 10);
            String disfrmDt = yy + mm + dd;
            String dd1 = this.disToDate.substring(0, 2);
            String mm1 = this.disToDate.substring(3, 5);
            String yy1 = this.disToDate.substring(6, 10);
            String distoDt = yy1 + mm1 + dd1;

            disburseList = disburseFacade.getDisburseList(this.acType,this.acNature, this.disFrAmount, this.disToAmount, disfrmDt, distoDt, this.orderBy, branchCode);
            if (disburseList.isEmpty()) {
                message = "No Data found in databse";
                return;
            }
            new ReportBean().openPdf("Loan Disbursement Report_" + disfrmDt + " to " + distoDt, "LoanDisbursement", new JRBeanCollectionDataSource(disburseList), fillmap);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void printExcelReport() {
        String branchName = "";
        String address = "";
        try {
            List brNameAdd = new ArrayList();
            brNameAdd = comman.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            if (this.disFromDate == null) {
                setMessage("Please insert Disbursement From Date !");
                return;
            }
            boolean res = new Validator().validateDate_dd_mm_yyyy(this.disFromDate);
            if (res == false) {
                this.setMessage("Please select proper from date !");
                return;
            }
            if (this.disToDate == null) {
                setMessage("Please insert Disbursement To Date !");
                return;
            }
            res = new Validator().validateDate_dd_mm_yyyy(this.disToDate);
            if (res == false) {
                this.setMessage("Please select proper to date !");
                return;
            }

//            if(dmy.parse(disFromDate).after(dmy.parse(disToDate))){
//                this.setMessage("Please check from date !");
//                return;
//            }

//            int comValue = dmy.parse(disFromDate).compareTo(dmy.parse(disToDate));
//            if (comValue > 0) {
//                this.setMessage("Please check from date !")
//                return;
//            }

            if (disFrAmount == 0) {
                setMessage("Please fill the proper Disbursed From Amount!");
                return;
            }
            if (disToAmount == 0) {
                setMessage("Please fill the proper Disbresed To Amount!");
                return;
            }
            try {
                if (this.acType.equalsIgnoreCase("--Select--")) {
                    setMessage("Please fill the A/C Type!");
                    return;
                }
                if (this.orderBy.equalsIgnoreCase("Select")) {
                    setMessage("Please fill the Order By!");
                    return;
                }
            } catch (Exception e) {
                setMessage(e.getMessage());
            }
            String report = "Loan Disbursement Report";
            Map fillmap = new HashMap();
            fillmap.put("pDisfrDate", this.disFromDate);
            fillmap.put("pDistoDate", this.disToDate);
            fillmap.put("pSysDate", this.getTodayDate());
            fillmap.put("pBranchName", branchName);
            fillmap.put("pAddress", address);
            fillmap.put("pRepName", report);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pDisbfrAmt", disFrAmount);
            fillmap.put("pDisbtoAmt", disToAmount);

            String dd = this.disFromDate.substring(0, 2);
            String mm = this.disFromDate.substring(3, 5);
            String yy = this.disFromDate.substring(6, 10);
            String disfrmDt = yy + mm + dd;
            String dd1 = this.disToDate.substring(0, 2);
            String mm1 = this.disToDate.substring(3, 5);
            String yy1 = this.disToDate.substring(6, 10);
            String distoDt = yy1 + mm1 + dd1;

            disburseList = disburseFacade.getDisburseList(this.acType,this.acNature, this.disFrAmount, this.disToAmount, disfrmDt, distoDt, this.orderBy, branchCode);
            if (disburseList.isEmpty()) {
                message = "No Data found in databse";
                return;
            }
            new ReportBean().dynamicExcelJasper("LoanDisbursement_Excel", "excel",
                    new JRBeanCollectionDataSource(disburseList), fillmap, "Loan Disbursement Report");
//            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
//            HttpSession httpSession = getHttpSession();
//            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
      //  this.setAcType("0");
        this.setDisFrAmount(0.0d);
        this.setDisToAmount(0.0d);
        this.setDisFromDate(getTodayDate());
        this.setDisToDate(getTodayDate());
        this.setMessage("");
        this.setOrderBy("Select");
    }

    public String closeAction() {
        return "case1";
    }
}
