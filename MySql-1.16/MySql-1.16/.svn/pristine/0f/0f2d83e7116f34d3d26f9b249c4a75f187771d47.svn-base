/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentAmortPostHistory;
import com.cbs.entity.ho.investment.InvestmentAmortizationDetails;
import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.master.Gltable;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.investment.IntRecPostFdrPojo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class AmortPosting extends BaseBean {
    
    private String message;
    private String optTpDd;
    private List<SelectItem> optTypeList;
    private String ctrlNoDd;
    private List<SelectItem> ctrlNoList;
    private String dtDd;
    private List<SelectItem> dtList;
    private String freqDd;
    private List<SelectItem> freqList;
    private String postDate;
    private String creditGl;
    private String creditAmt;
    private String debitGl;
    private String debitAmt;
    private String ctrlFlag;
    private InvestmentMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");    
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private String crHeadDesc;
    private String drHeadDesc;
    NumberFormat formatter = new DecimalFormat("#.00");
    
    public String getCreditAmt() {
        return creditAmt;
    }
    
    public void setCreditAmt(String creditAmt) {
        this.creditAmt = creditAmt;
    }
    
    public String getCreditGl() {
        return creditGl;
    }
    
    public void setCreditGl(String creditGl) {
        this.creditGl = creditGl;
    }
    
    public String getCtrlNoDd() {
        return ctrlNoDd;
    }
    
    public void setCtrlNoDd(String ctrlNoDd) {
        this.ctrlNoDd = ctrlNoDd;
    }
    
    public List<SelectItem> getCtrlNoList() {
        return ctrlNoList;
    }
    
    public void setCtrlNoList(List<SelectItem> ctrlNoList) {
        this.ctrlNoList = ctrlNoList;
    }
    
    public String getDebitAmt() {
        return debitAmt;
    }
    
    public void setDebitAmt(String debitAmt) {
        this.debitAmt = debitAmt;
    }
    
    public String getDebitGl() {
        return debitGl;
    }
    
    public void setDebitGl(String debitGl) {
        this.debitGl = debitGl;
    }
    
    public String getDtDd() {
        return dtDd;
    }
    
    public void setDtDd(String dtDd) {
        this.dtDd = dtDd;
    }
    
    public List<SelectItem> getDtList() {
        return dtList;
    }
    
    public void setDtList(List<SelectItem> dtList) {
        this.dtList = dtList;
    }
    
    public String getFreqDd() {
        return freqDd;
    }
    
    public void setFreqDd(String freqDd) {
        this.freqDd = freqDd;
    }
    
    public List<SelectItem> getFreqList() {
        return freqList;
    }
    
    public void setFreqList(List<SelectItem> freqList) {
        this.freqList = freqList;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getOptTpDd() {
        return optTpDd;
    }
    
    public void setOptTpDd(String optTpDd) {
        this.optTpDd = optTpDd;
    }
    
    public List<SelectItem> getOptTypeList() {
        return optTypeList;
    }
    
    public void setOptTypeList(List<SelectItem> optTypeList) {
        this.optTypeList = optTypeList;
    }
    
    public String getPostDate() {
        return postDate;
    }
    
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    
    public String getCtrlFlag() {
        return ctrlFlag;
    }
    
    public void setCtrlFlag(String ctrlFlag) {
        this.ctrlFlag = ctrlFlag;
    }
    
    public String getCrHeadDesc() {
        return crHeadDesc;
    }
    
    public void setCrHeadDesc(String crHeadDesc) {
        this.crHeadDesc = crHeadDesc;
    }
    
    public String getDrHeadDesc() {
        return drHeadDesc;
    }
    
    public void setDrHeadDesc(String drHeadDesc) {
        this.drHeadDesc = drHeadDesc;
    }
    
    public AmortPosting() {
        try {
            optTypeList = new ArrayList<SelectItem>();
            optTypeList.add(new SelectItem("Individual", "Individual"));
            optTypeList.add(new SelectItem("All", "All"));
            
            dtList = new ArrayList<SelectItem>();
            dtList.add(new SelectItem("F", "Financial Year"));
            dtList.add(new SelectItem("C", "Calendar Year"));
            
            freqList = new ArrayList<SelectItem>();
            freqList.add(new SelectItem("Yearly", "Yearly"));
            freqList.add(new SelectItem("Half-Yearly", "Half-Yearly"));
            freqList.add(new SelectItem("Quarterly", "Quarterly"));
            
            this.setCtrlFlag("false");
            
            remoteInterface = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            
            optTypeLostFocus();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void optTypeLostFocus() {
        try {
            ctrlNoList = new ArrayList<SelectItem>();
            if (this.getOptTpDd().equalsIgnoreCase("Individual")) {
                this.setCtrlFlag("false");
                List<Long> ctrlList = new ArrayList<Long>();
                ctrlList = remoteInterface.getCtrlNo();
                for (int i = 0; i < ctrlList.size(); i++) {
                    String ctrlN = ctrlList.get(i).toString();
                    ctrlNoList.add(new SelectItem(ctrlN));
                }
            } else {
                this.setCtrlFlag("true");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public String exitButton() {
        return "case1";
    }
    
    public void calTxn() {        
        try {
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brList = common.getBranchNameandAddress(getOrgnBrCode());
            
            String matdt = "", purDt = "", secDesc = "";
            Long pmDiff = 0l;
            double faceValue = 0.0, bookValue = 0.0, totAmt = 0.0;            
            BigDecimal amortVal, amortValTot = new BigDecimal("0.0");
            
            if (this.optTpDd.equalsIgnoreCase("Individual")) {
                if (this.ctrlNoDd.equals("null")) {
                    this.setMessage("Control No Required to Calculate in Case of Individual");
                    return;
                }
            }
            
            String dd = this.getPostDate().substring(0, 2);
            String mm = this.getPostDate().substring(3, 5);
            
            List<IntRecPostFdrPojo> reportList = new ArrayList<IntRecPostFdrPojo>();
            
            if (optTpDd.equalsIgnoreCase("Individual")) {
                BigInteger ctrNo = new BigInteger(this.getCtrlNoDd());
                Object[] repList = remoteInterface.getRepData(ctrNo);
                if (repList.length <= 0) {
                    this.setMessage("Already Amortised");
                    return;
                } else {
                    faceValue = Double.parseDouble((repList[3].toString()));
                    bookValue = Double.parseDouble((repList[4].toString()));
                    matdt = (dmy.format(repList[2]));
                    purDt = (dmy.format(repList[1]));
                    secDesc = repList[5].toString();
                    pmDiff = CbsUtil.dayDiff(dmy.parse(purDt), dmy.parse(matdt));
                }
                
                List<InvestmentAmortizationDetails> entity = remoteInterface.getAmortMode(Long.parseLong(String.valueOf(ctrNo)));                
                if (!entity.isEmpty()) {
                    for (int i = 0; i < entity.size(); i++) {
                        IntRecPostFdrPojo reportPojo = new IntRecPostFdrPojo();
                        reportPojo.setSno((int) entity.get(i).getInvestmentAmortizationDetailsPK().getSlno());
                        reportPojo.setYear(dmy.format(entity.get(i).getYears()));
                        reportPojo.setDays(entity.get(i).getDays().intValue());
                        reportPojo.setRecAmt(entity.get(i).getAmortAmt().toString());
                        reportPojo.setCtrlNo(ctrNo.intValue());
                        reportPojo.setFaValue(String.valueOf(faceValue));
                        reportPojo.setBookvalue(String.valueOf(bookValue));
                        reportPojo.setMatdt(matdt);
                        reportPojo.setPurDt(purDt);
                        reportPojo.setIntopt(entity.get(i).getStatus());
                        reportPojo.setSellerName(secDesc);
                        long day = remoteInterface.getTotalSumOfDaysByCtrl(ctrNo.intValue());
//                          reportPojo.setPmDiff(pmDiff);
                        reportPojo.setPmDiff(day);
                        if ((entity.get(i).getYears().compareTo(dmy.parse(this.getPostDate())) <= 0) && entity.get(i).getStatus().equalsIgnoreCase("A")) {
                            BigDecimal BalAmt = new BigDecimal("0");
                            BalAmt = remoteInterface.getTotalAmortAmt(dmy.parse(this.getPostDate()), ctrNo, "P");
                            if (BalAmt == null) {
                                BalAmt = new BigDecimal("0");
                            }
                            reportPojo.setFdrNo(BalAmt.toString());
                            reportList.add(reportPojo);
                            totAmt = totAmt + entity.get(i).getAmortAmt().doubleValue();
                        }                        
                    }
                }
            } else {
                /*Call Report All With Passing These values as above Call report*/
                List<Long> ctrlList = new ArrayList<Long>();
                ctrlList = remoteInterface.getCtrlNo();                
                for (int i = 0; i < ctrlList.size(); i++) {
                    BigInteger ctrNo = new BigInteger(ctrlList.get(i).toString());
                    Object[] repList = remoteInterface.getRepData(ctrNo);
                    if (repList.length <= 0) {
                        this.setMessage("Already Amortised");
                        return;
                    } else {
                        faceValue = Double.parseDouble((repList[3].toString()));
                        bookValue = Double.parseDouble((repList[4].toString()));
                        matdt = (dmy.format(repList[2]));
                        purDt = (dmy.format(repList[1]));
                        secDesc = repList[5].toString();
                        pmDiff = CbsUtil.dayDiff(dmy.parse(purDt), dmy.parse(matdt));
                    }
                    
                    List<InvestmentAmortizationDetails> entity = remoteInterface.getAmortMode(Long.parseLong(String.valueOf(ctrNo)));                    
                    if (!entity.isEmpty()) {
                        for (int k = 0; k < entity.size(); k++) {
                            IntRecPostFdrPojo reportPojo = new IntRecPostFdrPojo();
                            reportPojo.setSno((int) entity.get(k).getInvestmentAmortizationDetailsPK().getSlno());
                            reportPojo.setYear(dmy.format(entity.get(k).getYears()));                            
                            reportPojo.setDays(entity.get(k).getDays().intValue());                            
                            reportPojo.setRecAmt(entity.get(k).getAmortAmt().toString());
                            reportPojo.setCtrlNo(ctrNo.intValue());
                            reportPojo.setFaValue(String.valueOf(faceValue));
                            reportPojo.setBookvalue(String.valueOf(bookValue));
                            reportPojo.setMatdt(matdt);
                            reportPojo.setPurDt(purDt);
                            reportPojo.setIntopt(entity.get(k).getStatus());
                            reportPojo.setSellerName(secDesc);
                            long day = remoteInterface.getTotalSumOfDaysByCtrl(ctrNo.intValue());
//                            reportPojo.setPmDiff(pmDiff);
                            reportPojo.setPmDiff(day);
                            if ((entity.get(k).getYears().compareTo(dmy.parse(this.getPostDate())) <= 0) && entity.get(k).getStatus().equalsIgnoreCase("A")) {
                                BigDecimal BalAmt = new BigDecimal("0");
                                BalAmt = remoteInterface.getTotalAmortAmt(dmy.parse(this.getPostDate()), ctrNo, "P");
                                if (BalAmt == null) {
                                    BalAmt = new BigDecimal("0");
                                }
                                reportPojo.setFdrNo(BalAmt.toString());
                                reportList.add(reportPojo);
                                totAmt = totAmt + entity.get(k).getAmortAmt().doubleValue();
                            }                            
                        }
                    }
                }
            }
            
            InvestmentCallHead entity = remoteInterface.getInvestCallHeadByCode("10");
            if (entity != null) {
                this.setDebitGl(entity.getDrGlhead());
                this.setDrHeadDesc(acDescVal(this.getDebitGl()));
                this.setDebitAmt(formatter.format(totAmt));
                this.setCreditGl(entity.getCrGlhead());
                this.setCrHeadDesc(acDescVal(this.getCreditGl()));
                this.setCreditAmt(formatter.format(totAmt));
            } else {
                this.setMessage("Data is not found in Investment Call Head Table for Code 10.");
            }            
            
            Map fillParams = new HashMap();
            String repName = "Amort Calculation";
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBranchName", brList.get(0));
            fillParams.put("pAddress", brList.get(1));
            fillParams.put("pReportName", repName);            
            fillParams.put("pReportDt", this.getPostDate());
            
            new ReportBean().jasperReport("AmortCalReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, repName);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void postTxn() {
        try {
            double totalDr = 0.0, strRecNo = 0.0, strTrsNo = 0.0;
            String msg = "", drGlHead = "", crGlHead = "", strMsg = "";
            
            InvestmentCallHead entity = remoteInterface.getInvestCallHeadByCode("10");
            if (entity == null) {
                this.setMessage("GL Head does not exists.Please enter valid GLhead");
                return;
            } else {
                drGlHead = entity.getDrGlhead();
                crGlHead = entity.getCrGlhead();
            }
            
            if (this.optTpDd.equalsIgnoreCase("All")) {
                BigInteger yrAmort = new BigInteger(this.getPostDate().substring(6));
                String monAmort = this.getPostDate().substring(3, 5);
                List<InvestmentAmortPostHistory> resultList = new ArrayList<InvestmentAmortPostHistory>();
                resultList = remoteInterface.getPostStatusAll(yrAmort, monAmort);
                if (!resultList.isEmpty()) {
                    this.setMessage("Data Already posted");
                    return;
                }
            } else {
                BigInteger yrAmort = new BigInteger(this.getPostDate().substring(6));
                String monAmort = this.getPostDate().substring(3, 5);
                BigInteger ctrNo = new BigInteger(this.getCtrlNoDd());
                List<InvestmentAmortPostHistory> resultList = new ArrayList<InvestmentAmortPostHistory>();
                resultList = remoteInterface.getPostStatusCtrl(yrAmort, monAmort, ctrNo);
                if (!resultList.isEmpty()) {
                    this.setMessage("Data Already posted");
                    return;
                }
            }
            
            if (this.optTpDd.equalsIgnoreCase("All")) {
                BigInteger yrAmort = new BigInteger(this.getPostDate().substring(6));
                String monAmort = this.getPostDate().substring(3, 5);
                List<Long> ctrlList = new ArrayList<Long>();
                ctrlList = remoteInterface.getCtrlNo();
                for (int i = 0; i < ctrlList.size(); i++) {                    
                    BigInteger ctrNo = new BigInteger(ctrlList.get(i).toString());
                    List<InvestmentAmortizationDetails> resultList = new ArrayList<InvestmentAmortizationDetails>();
                    resultList = remoteInterface.getAmortCtrlNo(ymd.parse(ymd.format(dmy.parse(this.getPostDate()))), ctrNo);
                    if (!resultList.isEmpty()) {
                        for (int j = 0; j < resultList.size(); j++) {
                            BigInteger slNo = BigInteger.valueOf(resultList.get(j).getInvestmentAmortizationDetailsPK().getSlno());
                            Long ctNo = resultList.get(j).getInvestmentAmortizationDetailsPK().getCtrlNo();
                            Date years = resultList.get(j).getYears();
                            
                            msg = remoteInterface.updateAmortDtl(getUserName(), new Date(), slNo, ctNo, years);
                            if (!msg.equalsIgnoreCase("True")) {
                                this.setMessage("Updation Failed For Amortization Details");
                                return;
                            }
                            totalDr = totalDr + resultList.get(j).getAmortAmt().doubleValue();
                        }
                    }
                }                
                
                BigInteger ctrNo = new BigInteger("999999");
                msg = remoteInterface.saveGlDataForAmort(crGlHead, drGlHead, getOrgnBrCode(), totalDr, getUserName(), ctrNo, monAmort, yrAmort, "I");
                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                    this.setMessage("Data Posted Successfully" + " And Posted Batch" + msg.substring(4));
                } else {
                    this.setMessage("Problem Occured In Posting");
                }
            } else {
                BigInteger yrAmort = new BigInteger(this.getPostDate().substring(6));
                BigInteger ctrNo = new BigInteger(this.getCtrlNoDd());
                List<InvestmentAmortizationDetails> resultList = new ArrayList<InvestmentAmortizationDetails>();
                resultList = remoteInterface.getAmortCtrlNo(ymd.parse(ymd.format(dmy.parse(this.getPostDate()))), ctrNo);
                String monAmort = this.getPostDate().substring(3, 5);
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        BigInteger slNo = BigInteger.valueOf(resultList.get(i).getInvestmentAmortizationDetailsPK().getSlno());
                        Long ctNo = resultList.get(i).getInvestmentAmortizationDetailsPK().getCtrlNo();
                        Date years = resultList.get(i).getYears();
                        
                        msg = remoteInterface.updateAmortDtl(getUserName(), new Date(), slNo, ctNo, years);
                        if (!msg.equalsIgnoreCase("True")) {
                            this.setMessage("Updation Failed For Amortization Details");
                            return;
                        }
                        totalDr = totalDr + resultList.get(i).getAmortAmt().doubleValue();
                    }
                    
                    msg = remoteInterface.saveGlDataForAmort(crGlHead, drGlHead, getOrgnBrCode(), totalDr, getUserName(), ctrNo, monAmort, yrAmort, "I");
                    if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                        this.setMessage("Data Posted Successfully" + " And Posted Batch" + msg.substring(4));
                    } else {
                        this.setMessage("Problem Occured In Posting");
                    }
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void ClearAll() {
        this.setMessage("");
        this.setCrHeadDesc("");
        this.setCreditGl("");
        this.setCreditAmt("");
        this.setDebitAmt("");
        this.setDebitGl("");
        this.setDrHeadDesc("");
    }
    
    public String acDescVal(String acno) {
        String acName = "";
        try {
            Gltable entityList1 = remoteInterface.getGltableByAcno(acno);
            if (entityList1 != null) {
                acName = entityList1.getAcName().toString();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return acName;
    }
}