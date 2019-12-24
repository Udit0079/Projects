/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.InoperativeReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.pojo.InoperativeToOperativePoJo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class InoperativeReport extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    Date frdt;
    Date todt;
    private OtherReportFacadeRemote OtherRepFacade;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote miscRemote;
    private String repType;
    private List<SelectItem> repTypeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    private String dateType;
    private List<SelectItem> dateTypeList;
    private boolean bucketBool;
    private boolean fromToBool;
    private DDSReportFacadeRemote ddsRemote;
    Date txnFromDt;
    Date txnToDt;
    Date dt = new Date();
    String visible ;
    String showFrDt ;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<InoperativeReportPojo> repList = new ArrayList<InoperativeReportPojo>();
    List<InoperativeToOperativePoJo> dormantToOperativeList = new ArrayList<InoperativeToOperativePoJo>();

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

    public Date getFrdt() {
        return frdt;
    }

    public void setFrdt(Date frdt) {
        this.frdt = frdt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTodt() {
        return todt;
    }

    public void setTodt(Date todt) {
        this.todt = todt;
    }

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

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public List<SelectItem> getDateTypeList() {
        return dateTypeList;
    }

    public void setDateTypeList(List<SelectItem> dateTypeList) {
        this.dateTypeList = dateTypeList;
    }

    public boolean isBucketBool() {
        return bucketBool;
    }

    public void setBucketBool(boolean bucketBool) {
        this.bucketBool = bucketBool;
    }

    public boolean isFromToBool() {
        return fromToBool;
    }

    public void setFromToBool(boolean fromToBool) {
        this.fromToBool = fromToBool;
    }
    public Date getTxnFromDt() {
        return txnFromDt;
    }
    public void setTxnFromDt(Date txnFromDt) {
        this.txnFromDt = txnFromDt;
    }
    public Date getTxnToDt() {
        return txnToDt;
    }
    public void setTxnToDt(Date txnToDt) {
        this.txnToDt = txnToDt;
    }
    public String getVisible() {
        return visible;
    }
    public void setVisible(String visible) {
        this.visible = visible;
    }
    public String getShowFrDt() {
        return showFrDt;
    }
    public void setShowFrDt(String showFrDt) {
        this.showFrDt = showFrDt;
    }

    public InoperativeReport() {
        try {
            setFrdt(dt);
            setTodt(dt);
            OtherRepFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            miscRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("", "--Select--"));
            repTypeList.add(new SelectItem("1", "Inoperative"));
            repTypeList.add(new SelectItem("2", "Inoperative to Operative"));

            acNatureList = new ArrayList();
            acNatureList.add(new SelectItem("s", "--Select--"));
            acNatureList.add(new SelectItem("All", "ALL"));
            acNatureList.add(new SelectItem("CA", "CA"));
            acNatureList.add(new SelectItem("SB", "SB"));
            this.bucketBool = true;
            visible ="none";
            showFrDt="";
            setMessage("");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getAcTypeForNature() {
        acTypeList = new ArrayList<SelectItem>();
        try {
            List list;
            acTypeList.add(new SelectItem("All"));
            if(!acNature.equalsIgnoreCase("ALL")){
                if (acNature.equalsIgnoreCase("CA")) {
                    list = ddsRemote.getAccountCodeByClassificationAndNature("'C','B','D'", this.acNature);
                } else {
                    list = ddsRemote.getAccountCodeByClassificationAndNature("'C'", this.acNature);
                }
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    acTypeList.add(new SelectItem(element.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getDateTypeOnBlurr() {
        setMessage("");
        try {
            dateTypeList = new ArrayList<SelectItem>();
            if(repType.equalsIgnoreCase("1")){
                dateTypeList.add(new SelectItem("1", "Till Date"));
                dateTypeList.add(new SelectItem("2", "Between Date"));
            } else {
                dateTypeList.add(new SelectItem("1", "Till Date"));
                dateTypeList.add(new SelectItem("2", "Between Date"));
            }
            this.bucketBool = true;
            if(dateType.equalsIgnoreCase("1")){
                fromToBool = true;
            } else {
              fromToBool = false;
            }
            if(repType.equalsIgnoreCase("2") && dateType.equalsIgnoreCase("1")) {
                visible ="";
            } else {
                visible ="none";
            }
            if(dateType.equalsIgnoreCase("2")){
                showFrDt="";
            } else {
                showFrDt="none";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        if (this.frdt == null) {
            setMessage("Please Fill From Date.");
            return;
        }
        if (this.todt == null) {
            setMessage("Please Fill To Date.");
            return;
        }
        if(repType.equalsIgnoreCase("")){
            setMessage("Please select the Report Type!!");
            return;
        }
        if(acNature.equalsIgnoreCase("s")){
            setMessage("Please select the Account Nature!!");
            return;
        }
        if(acType.equalsIgnoreCase("")){
            setMessage("Please select the Account type!!");
            return;
        }
        if(todt.after(dt) || frdt.after(dt)){
            setMessage("Date can not be greater than Today date!!!!!!");
            return;
        }
        if(repType.equalsIgnoreCase("2") && dateType.equalsIgnoreCase("1")){
            if(this.txnFromDt == null || this.txnToDt == null){
                setMessage("Please Fill Transaction Date!!");
                return;
            }
            if(todt.after(txnFromDt) || todt.after(txnToDt)){
                setMessage("Transaction Date can not be less than the Till Date!!!");
                return;
            }
        }
        if(!(repType.equalsIgnoreCase("2") && dateType.equalsIgnoreCase("1"))){
            txnFromDt= new Date();
            txnToDt= new Date();
        }
        Map fillParams = new HashMap();
        String report = "Inoperative Report";
        try {
            if (!this.branchCode.equalsIgnoreCase("0A")) {
                List dataList1 = common.getBranchNameandAddress(this.branchCode);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }
            String repName ="";
            String visible= "";
            if(repType.equalsIgnoreCase("1")){
                repName = "Inoperative Report";
                visible= "1";
            } else {
                repName ="Inoperative to Operative Report";
                visible= "2";
            }

            if(repType.equalsIgnoreCase("2") && (dateType.equalsIgnoreCase("1"))){
                fillParams.put("pReportFrDate", "Inoperative Till Date ".concat(dmy.format(this.todt).concat(" And Transaction Date From ")));
                fillParams.put("pReportToDate", dmy.format(this.txnFromDt).concat(" To ").concat(dmy.format(txnToDt)));
            } else if(repType.equalsIgnoreCase("1") && (dateType.equalsIgnoreCase("1"))){
                fillParams.put("pReportFrDate", "Till Date ".concat(dmy.format(this.todt)));
                fillParams.put("pReportToDate", " ");
            } else {
                fillParams.put("pReportFrDate", dmy.format(this.frdt).concat(" To "));
                fillParams.put("pReportToDate", dmy.format(this.todt));
            }
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", repName);
            fillParams.put("pBranchName", bankName);
            fillParams.put("branchAddr", bankAddress);
            fillParams.put("visible", visible);
            
            dormantToOperativeList = miscRemote.dormantToOperativeReportDetails(branchCode,repType,acNature,acType,dateType,ymd.format(this.getFrdt()), ymd.format(this.getTodt()),ymd.format(this.getTxnFromDt()),ymd.format(this.getTxnToDt()));
            if (dormantToOperativeList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
//            repList = OtherRepFacade.getInoperativeReport(branchCode, ymd.format(this.getFrdt()), ymd.format(this.getTodt()));
//            new ReportBean().openPdf("Inoperative Report_" + ymd.format(this.getFrdt()) + " to " + ymd.format(this.getTodt()), "OperativeToInoperative", new JRBeanCollectionDataSource(dormantToOperativeList), fillParams);
            new ReportBean().jasperReport("OperativeToInoperative", "text/html",new JRBeanCollectionDataSource(dormantToOperativeList), fillParams, "Inoperative Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        String bankName = "ALL", bankAddress = "ALL";
        if (this.frdt == null) {
            setMessage("Please Fill From Date.");
            return;
        }
        if (this.todt == null) {
            setMessage("Please Fill To Date.");
            return;
        }
        if(repType.equalsIgnoreCase("")){
            setMessage("Please select the Report Type!!");
            return;
        }
        if(acNature.equalsIgnoreCase("s")){
            setMessage("Please select the Account Nature!!");
            return;
        }
        if(acType.equalsIgnoreCase("")){
            setMessage("Please select the Account type!!");
            return;
        }
        if(todt.after(dt) || frdt.after(dt)){
            setMessage("Date can not be greater than Today date!!!!!!");
            return;
        }
        if(repType.equalsIgnoreCase("2") && dateType.equalsIgnoreCase("1")){
            if(this.txnFromDt == null || this.txnToDt == null){
                setMessage("Please Fill Transaction Date!!");
                return;
            }
            if(todt.after(txnFromDt) || todt.after(txnToDt)){
                setMessage("Transaction Date can not be less than the Till Date!!!");
                return;
            }
        }
        if(!(repType.equalsIgnoreCase("2") && dateType.equalsIgnoreCase("1"))){
            txnFromDt= new Date();
            txnToDt= new Date();
        }
        Map fillParams = new HashMap();
        String report = "Inoperative Report";
        try {
            if (!this.branchCode.equalsIgnoreCase("0A")) {
                List dataList1 = common.getBranchNameandAddress(this.branchCode);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }
            String repName ="";
            String visible= "";
            if(repType.equalsIgnoreCase("1")){
                repName = "Inoperative Report";
                visible= "1";
            } else {
                repName ="Inoperative to Operative Report";
                visible= "2";
            }
            
            if(repType.equalsIgnoreCase("2") && (dateType.equalsIgnoreCase("1"))){
                fillParams.put("pReportFrDate", "Inoperative Till Date ".concat(dmy.format(this.todt).concat(" And Transaction Date From ")));
                fillParams.put("pReportToDate", dmy.format(this.txnFromDt).concat(" To ").concat(dmy.format(txnToDt)));
            } else if(repType.equalsIgnoreCase("1") && (dateType.equalsIgnoreCase("1"))){
                fillParams.put("pReportFrDate", "Till Date ".concat(dmy.format(this.todt)));
                fillParams.put("pReportToDate", " ");
            } else {
                fillParams.put("pReportFrDate", dmy.format(this.frdt).concat(" To "));
                fillParams.put("pReportToDate", dmy.format(this.todt));
            }
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", repName);
            fillParams.put("pBranchName", bankName);
            fillParams.put("branchAddr", bankAddress);
            fillParams.put("visible", visible);
            dormantToOperativeList = miscRemote.dormantToOperativeReportDetails(branchCode,repType,acNature,acType,dateType,ymd.format(this.getFrdt()), ymd.format(this.getTodt()),this.getTxnFromDt().toString().equalsIgnoreCase(null) ? "" : ymd.format(this.getTxnFromDt()), this.getTxnToDt().toString().equalsIgnoreCase(null) ? "" :ymd.format(this.getTxnToDt()));

//            repList = OtherRepFacade.getInoperativeReport(branchCode, ymd.format(this.getFrdt()), ymd.format(this.getTodt()));
//
            if (dormantToOperativeList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            new ReportBean().openPdf("Inoperative Report_" + ymd.format(this.getFrdt()) + " to " + ymd.format(this.getTodt()), "OperativeToInoperative", new JRBeanCollectionDataSource(dormantToOperativeList), fillParams);
//            new ReportBean().openPdf("Inoperative Report_" + ymd.format(this.getFrdt()) + " to " + ymd.format(this.getTodt()), "InoperativeReport", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            this.setMessage("");            
            setRepType("1");
            setAcNature("ALL");
            setAcType("All");
            setDateType("1");
            setTxnFromDt(new Date());
            setTxnToDt(new Date());
            bucketBool= true;
            setFrdt(dt);
            setTodt(dt);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}