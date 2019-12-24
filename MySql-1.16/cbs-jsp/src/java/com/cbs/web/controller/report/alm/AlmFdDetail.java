/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.alm;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.AlmFdShortedBybketWise;
import com.cbs.dto.report.AlmFddetailPojo;
import com.cbs.dto.report.AlmFdshortedByBktNo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ALMReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Athar Reza
 */
public class AlmFdDetail extends BaseBean {

    private String errorMsg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acnoNature;
    private List<SelectItem> acnoNatureList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String bucketNo;
    private List<SelectItem> bucketNoList;
    private String bucketBase;
    private List<SelectItem> bucketBaseList;
    private String dlCase;
    private List<SelectItem> dlCaseList;
    private String asOnDate;
    private String fromIo;
    private String fromType;
    private List<SelectItem> fromTypeList;
    private String toIo;
    private String toType;
    private List<SelectItem> toTypeList;
    private boolean bucketBool;
    private boolean fromToBool;
    private boolean dlCaseBool;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private ALMReportFacadeRemote almFacadeRemote = null;
    private final String almFacadeHomeName = "ALMReportFacade";
    private DDSReportFacadeRemote ddsRemote;
    List<AlmFddetailPojo> reportList = new ArrayList<AlmFddetailPojo>();

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getBucketBase() {
        return bucketBase;
    }

    public void setBucketBase(String bucketBase) {
        this.bucketBase = bucketBase;
    }

    public List<SelectItem> getBucketBaseList() {
        return bucketBaseList;
    }

    public void setBucketBaseList(List<SelectItem> bucketBaseList) {
        this.bucketBaseList = bucketBaseList;
    }

    public String getDlCase() {
        return dlCase;
    }

    public void setDlCase(String dlCase) {
        this.dlCase = dlCase;
    }

    public List<SelectItem> getDlCaseList() {
        return dlCaseList;
    }

    public void setDlCaseList(List<SelectItem> dlCaseList) {
        this.dlCaseList = dlCaseList;
    }

    public String getAcnoNature() {
        return acnoNature;
    }

    public void setAcnoNature(String acnoNature) {
        this.acnoNature = acnoNature;
    }

    public List<SelectItem> getAcnoNatureList() {
        return acnoNatureList;
    }

    public void setAcnoNatureList(List<SelectItem> acnoNatureList) {
        this.acnoNatureList = acnoNatureList;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
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

    public String getBucketNo() {
        return bucketNo;
    }

    public void setBucketNo(String bucketNo) {
        this.bucketNo = bucketNo;
    }

    public List<SelectItem> getBucketNoList() {
        return bucketNoList;
    }

    public void setBucketNoList(List<SelectItem> bucketNoList) {
        this.bucketNoList = bucketNoList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFromIo() {
        return fromIo;
    }

    public void setFromIo(String fromIo) {
        this.fromIo = fromIo;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public List<SelectItem> getFromTypeList() {
        return fromTypeList;
    }

    public void setFromTypeList(List<SelectItem> fromTypeList) {
        this.fromTypeList = fromTypeList;
    }

    public String getToIo() {
        return toIo;
    }

    public void setToIo(String toIo) {
        this.toIo = toIo;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public List<SelectItem> getToTypeList() {
        return toTypeList;
    }

    public void setToTypeList(List<SelectItem> toTypeList) {
        this.toTypeList = toTypeList;
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

    public boolean isDlCaseBool() {
        return dlCaseBool;
    }

    public void setDlCaseBool(boolean dlCaseBool) {
        this.dlCaseBool = dlCaseBool;
    }

    public AlmFdDetail() {
        try {
            asOnDate = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            almFacadeRemote = (ALMReportFacadeRemote) ServiceLocator.getInstance().lookup(almFacadeHomeName);
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getAcctNature();
            // getAllBucketNo();
            getBucketBased();
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void getBucketBased() {
        bucketBaseList = new ArrayList<SelectItem>();
        bucketBaseList.add(new SelectItem("S", "--Select--"));
        bucketBaseList.add(new SelectItem("L", "Residul Maturity"));
        bucketBaseList.add(new SelectItem("I", "Interest Rate Sensivity"));
        bucketBaseList.add(new SelectItem("N", "No of Day/Month/Year"));

        dlCaseList = new ArrayList<SelectItem>();
        dlCaseList.add(new SelectItem("S", "--Select--"));
        dlCaseList.add(new SelectItem("D", "DL Account Closing Date Wise"));
        dlCaseList.add(new SelectItem("M", "Attached Security's Maturity Date Wise"));
        
        fromTypeList = new ArrayList<SelectItem>();
        fromTypeList.add(new SelectItem("S", "--Select--"));
        fromTypeList.add(new SelectItem("D", "Day"));
//        fromTypeList.add(new SelectItem("M", "Month"));
//        fromTypeList.add(new SelectItem("Y", "Year"));

        toTypeList = new ArrayList<SelectItem>();
        toTypeList.add(new SelectItem("S", "--Select--"));
        toTypeList.add(new SelectItem("D", "Day"));
//        toTypeList.add(new SelectItem("M", "Month"));
//        toTypeList.add(new SelectItem("Y", "Year"));

        this.bucketBool = true;
        this.fromToBool = true;
        this.dlCaseBool = true;

    }

    public void getAcctNature() {
        List acctNature = new ArrayList();
        try {
            acctNature = common.getAcctfdRdMsNatList();
            acnoNatureList = new ArrayList<SelectItem>();
            acnoNatureList.add(new SelectItem("N", "--Select--"));
            if (!acctNature.isEmpty()) {
                for (int i = 0; i < acctNature.size(); i++) {
                    Vector acctVector = (Vector) acctNature.get(i);
                    acnoNatureList.add(new SelectItem(acctVector.get(0).toString()));
                }
            }            
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void initData() {
        try {
            acctTypeList = new ArrayList<SelectItem>();
            List tempList = ddsRemote.getAcctCodeByNature(acnoNature);
            acctTypeList.add(new SelectItem("ALL", "ALL"));
            for (int i = 0; i < tempList.size(); i++) {
                Vector tempVector = (Vector) tempList.get(i);
                acctTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
            if(acnoNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                dlCaseBool = false;
                this.setDlCase("S");
            } else {
                dlCaseBool = true;
                this.setDlCase("S");
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void getAllBucketNo() {
        setErrorMsg("");
        List bucketList = new ArrayList();
        try {
            if (bucketBase.equalsIgnoreCase("N")) {
                bucketBool = true;
                fromToBool = false;
            } else {
                bucketBool = false;
                fromToBool = true;                

                bucketList = common.getBucketList(bucketBase);
                bucketNoList = new ArrayList<SelectItem>();
                bucketNoList.add(new SelectItem("ALL", "ALL"));
                if (!bucketList.isEmpty()) {
                    for (int i = 0; i < bucketList.size(); i++) {
                        Vector acctVector = (Vector) bucketList.get(i);
                        bucketNoList.add(new SelectItem(acctVector.get(0).toString(),acctVector.get(1).toString()));
                    }
                }
            }

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
    
    public void viewReport() {

        String report = "Bucket Wise Detail Report";
        try {
            if (asOnDate == null || asOnDate.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(asOnDate)) {
                setErrorMsg("Please select Proper from date ");
                return;
            }

            if (dmy.parse(asOnDate).after(getDate())) {
                setErrorMsg("As on date should be less than current date !");
                return;
            }

            if (acnoNature.equalsIgnoreCase("N")) {
                setErrorMsg("Please select A/c Nature !");
                return;
            }

            if (bucketBase.equalsIgnoreCase("S")) {
                setErrorMsg("Please select ALM Base !");
                return;
            }
            if (acnoNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) && dlCase.equalsIgnoreCase("S")) {
                setErrorMsg("Please select ALM Base 2nd Value");
                return;
            }
            String frDt = "", toDt = "";            
            String asOnDt = ymd.format(dmy.parse(asOnDate));
            if(bucketBase.equalsIgnoreCase("N")){
                if (fromType.equalsIgnoreCase("D")) {
                    frDt = CbsUtil.dateAdd(asOnDt, Integer.parseInt(fromIo));
                } else if (fromType.equalsIgnoreCase("M")) {
                    frDt = CbsUtil.monthAdd(asOnDt, Integer.parseInt(fromIo));
                } else if (fromType.equalsIgnoreCase("Y")) {
                    frDt = CbsUtil.yearAdd(asOnDt, Integer.parseInt(fromIo));
                }

                if (fromType.equalsIgnoreCase("D")) {
                    if (!toType.equalsIgnoreCase("D")) {
                        setErrorMsg("From and To should be same !");
                        return;
                    }
                }

                if (fromType.equalsIgnoreCase("M")) {
                    if (!toType.equalsIgnoreCase("M")) {
                        setErrorMsg("From and To should be same !");
                        return;
                    }
                }

                if (fromType.equalsIgnoreCase("Y")) {
                    if (!toType.equalsIgnoreCase("Y")) {
                        setErrorMsg("From and To should be same !");
                        return;
                    }
                }

                if (toType.equalsIgnoreCase("D")) {
                    toDt = CbsUtil.dateAdd(asOnDt, Integer.parseInt(toIo));
                } else if (toType.equalsIgnoreCase("M")) {
                    toDt = CbsUtil.monthAdd(asOnDt, Integer.parseInt(toIo));
                } else if (toType.equalsIgnoreCase("Y")) {
                    toDt = CbsUtil.yearAdd(asOnDt, Integer.parseInt(toIo));
                }

                if (ymd.parse(toDt).after(ymd.parse(frDt))) {
                    setErrorMsg("From Date should be less than To Date !");
                    return;
                }
                if(fromType.equalsIgnoreCase("D")){
                    bucketNo = fromIo.concat(" To ").concat(toIo).concat(" Days");
                } else if(fromType.equalsIgnoreCase("M")){
                    bucketNo = fromIo.concat(" To ").concat(toIo).concat(" Months");
                } else if (fromType.equalsIgnoreCase("Y")){
                    bucketNo = fromIo.concat(" To ").concat(toIo).concat(" Years");
                }
            }

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pReportDt", this.asOnDate);
            fillParams.put("pBucketNo", this.bucketNo);

            reportList = almFacadeRemote.getAlmFdDetail(branchCode, acnoNature, bucketNo, asOnDt, bucketBase, acctType, frDt, toDt, dlCase);
            if(!bucketBase.equals("N")){
                ComparatorChain chObj = new ComparatorChain();
                //chObj.addComparator(new AlmFdShortedBybketWise());
                chObj.addComparator(new AlmFdshortedByBktNo());
                Collections.sort(reportList, chObj);
            }

//            for (int i = 0; i < reportList.size(); i++) {
//                AlmFddetailPojo val = reportList.get(i);
//                System.out.println("bket Desc->" + val.getBktDesc() + "Acno ->" + val.getAcNo() + "name ->" + val.getCustname() + "mat date ->" + val.getMatDate() + "Amount->" + val.getAmount());
//            }
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            new ReportBean().jasperReport("BccketWiseFdDetail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Bucket Wise Fd Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorMsg(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        String report = "Bucket Wise Detail Report";
        try {
            if (asOnDate == null || asOnDate.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(asOnDate)) {
                setErrorMsg("Please select Proper from date ");
                return;
            }

            if (dmy.parse(asOnDate).after(getDate())) {
                setErrorMsg("As on date should be less than current date !");
                return;
            }

            if (acnoNature.equalsIgnoreCase("N")) {
                setErrorMsg("Please select A/c Nature !");
                return;
            }

            if (bucketBase.equalsIgnoreCase("S")) {
                setErrorMsg("Please select Bucket Base !");
                return;
            }

            if (acnoNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) && dlCase.equalsIgnoreCase("S")) {
                setErrorMsg("Please select ALM Base 2nd Value");
                return;
            }
            String frDt = "", toDt = "";
            String asOnDt = ymd.format(dmy.parse(asOnDate));
            
            if(bucketBase.equalsIgnoreCase("N")){
                if (fromType.equalsIgnoreCase("D")) {
                    frDt = CbsUtil.dateAdd(asOnDt, Integer.parseInt(fromIo));
                } else if (fromType.equalsIgnoreCase("M")) {
                    frDt = CbsUtil.monthAdd(asOnDt, Integer.parseInt(fromIo));
                } else if (fromType.equalsIgnoreCase("Y")) {
                    frDt = CbsUtil.yearAdd(asOnDt, Integer.parseInt(fromIo));
                }

                if (fromType.equalsIgnoreCase("D")) {
                    if (!toType.equalsIgnoreCase("D")) {
                        setErrorMsg("From and To should be same !");
                        return;
                    }
                }

                if (fromType.equalsIgnoreCase("M")) {
                    if (!toType.equalsIgnoreCase("M")) {
                        setErrorMsg("From and To should be same !");
                        return;
                    }
                }

                if (fromType.equalsIgnoreCase("Y")) {
                    if (!toType.equalsIgnoreCase("Y")) {
                        setErrorMsg("From and To should be same !");
                        return;
                    }
                }

                if (toType.equalsIgnoreCase("D")) {
                    toDt = CbsUtil.dateAdd(asOnDt, Integer.parseInt(toIo));
                } else if (toType.equalsIgnoreCase("M")) {
                    toDt = CbsUtil.monthAdd(asOnDt, Integer.parseInt(toIo));
                } else if (toType.equalsIgnoreCase("Y")) {
                    toDt = CbsUtil.yearAdd(asOnDt, Integer.parseInt(toIo));
                }
                if(fromType.equalsIgnoreCase("D")){
                    bucketNo = fromIo.concat(" To ").concat(toIo).concat(" Days");
                } else if(fromType.equalsIgnoreCase("M")){
                    bucketNo = fromIo.concat(" To ").concat(toIo).concat(" Months");
                } else if (fromType.equalsIgnoreCase("Y")){
                    bucketNo = fromIo.concat(" To ").concat(toIo).concat(" Years");
                }
//                System.out.println("Bucket Number"+ bucketNo);
            }

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pReportDt", this.asOnDate);
            fillParams.put("pBucketNo", this.bucketNo);

            reportList = almFacadeRemote.getAlmFdDetail(branchCode, acnoNature, bucketNo, asOnDt, bucketBase, acctType, frDt, toDt, dlCase);
            if(!bucketBase.equals("N")){
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new AlmFdshortedByBktNo());
                Collections.sort(reportList, chObj);
            }

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            new ReportBean().openPdf("Bucket Wise Detail Report_" + ymd.format(dmy.parse(getTodayDate())), "BccketWiseFdDetail", new JRBeanCollectionDataSource(reportList), fillParams);

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Bucket Wise Fd Detail Report");
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        asOnDate = dmy.format(new Date());
        this.setAcnoNature("N");
        this.setBranchCode("ALL");
        this.setBucketNo("ALL");
        this.setFromIo("");
        this.setFromType("S");
        this.setToIo("");
        this.setToType("S");
        this.bucketBool = true;
        this.fromToBool = true;
        this.dlCaseBool = true;        
        this.setDlCase("S");
    }

    public String exitAction() {
        return "case1";
    }
}
