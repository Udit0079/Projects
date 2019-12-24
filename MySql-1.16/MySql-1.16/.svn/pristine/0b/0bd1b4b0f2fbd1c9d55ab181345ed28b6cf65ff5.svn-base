/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentAmortizationDetails;
import com.cbs.entity.ho.investment.InvestmentGoidates;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.BranchMaster;
import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.investment.AmortDates;
import com.cbs.web.pojo.investment.GoiAccrutinterestTable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class MasterClose extends BaseBean {

    private String message;
    private String securityType;
    private String ctrlNo;
    private String sellerName;
    private String maturityDate;
    private String sellingStatus;
    private String sellingDate;
    private String crGLHead;
    private String alphacode;
    private String faceValue;
    private String bookValue;
    private String accruedInterest;
    private String amortizationValue;
    private String issuingPrice;
    private String sellingAmount;
    private boolean sellAmtVisible;
    private double intAccAmt = 0d;
    private List<SelectItem> securityTypeList;
    private List<SelectItem> ctrlNoList;
    private List<SelectItem> sellingStatusList;
    private List<SelectItem> alphacodeList;
    private List<AmortDates> amortList;
    private Date dt = new Date();
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.00");
    private String balIntValue;
    private String crHeadName;
    private String repDesc;
    private List<SelectItem> repDescList;

    public double getIntAccAmt() {
        return intAccAmt;
    }

    public void setIntAccAmt(double intAccAmt) {
        this.intAccAmt = intAccAmt;
    }

    public List<AmortDates> getAmortList() {
        return amortList;
    }

    public void setAmortList(List<AmortDates> amortList) {
        this.amortList = amortList;
    }

    public String getAlphacode() {
        return alphacode;
    }

    public void setAlphacode(String alphacode) {
        this.alphacode = alphacode;
    }

    public List<SelectItem> getAlphacodeList() {
        return alphacodeList;
    }

    public void setAlphacodeList(List<SelectItem> alphacodeList) {
        this.alphacodeList = alphacodeList;
    }

    public String getCrGLHead() {
        return crGLHead;
    }

    public void setCrGLHead(String crGLHead) {
        this.crGLHead = crGLHead;
    }

    public String getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(String ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public List<SelectItem> getCtrlNoList() {
        return ctrlNoList;
    }

    public void setCtrlNoList(List<SelectItem> ctrlNoList) {
        this.ctrlNoList = ctrlNoList;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public List<SelectItem> getSecurityTypeList() {
        return securityTypeList;
    }

    public void setSecurityTypeList(List<SelectItem> securityTypeList) {
        this.securityTypeList = securityTypeList;
    }

    public boolean isSellAmtVisible() {
        return sellAmtVisible;
    }

    public void setSellAmtVisible(boolean sellAmtVisible) {
        this.sellAmtVisible = sellAmtVisible;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate = sellingDate;
    }

    public String getSellingStatus() {
        return sellingStatus;
    }

    public void setSellingStatus(String sellingStatus) {
        this.sellingStatus = sellingStatus;
    }

    public List<SelectItem> getSellingStatusList() {
        return sellingStatusList;
    }

    public void setSellingStatusList(List<SelectItem> sellingStatusList) {
        this.sellingStatusList = sellingStatusList;
    }

    public String getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(String accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public String getAmortizationValue() {
        return amortizationValue;
    }

    public void setAmortizationValue(String amortizationValue) {
        this.amortizationValue = amortizationValue;
    }

    public String getBookValue() {
        return bookValue;
    }

    public void setBookValue(String bookValue) {
        this.bookValue = bookValue;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public String getIssuingPrice() {
        return issuingPrice;
    }

    public void setIssuingPrice(String issuingPrice) {
        this.issuingPrice = issuingPrice;
    }

    public String getSellingAmount() {
        return sellingAmount;
    }

    public void setSellingAmount(String sellingAmount) {
        this.sellingAmount = sellingAmount;
    }

    public String getBalIntValue() {
        return balIntValue;
    }

    public void setBalIntValue(String balIntValue) {
        this.balIntValue = balIntValue;
    }

    public String getCrHeadName() {
        return crHeadName;
    }

    public void setCrHeadName(String crHeadName) {
        this.crHeadName = crHeadName;
    }

    public String getRepDesc() {
        return repDesc;
    }

    public void setRepDesc(String repDesc) {
        this.repDesc = repDesc;
    }

    public List<SelectItem> getRepDescList() {
        return repDescList;
    }

    public void setRepDescList(List<SelectItem> repDescList) {
        this.repDescList = repDescList;
    }
    
    public MasterClose() {
        try {
            securityTypeList = new ArrayList<SelectItem>();
            ctrlNoList = new ArrayList<SelectItem>();
            sellingStatusList = new ArrayList<SelectItem>();
            alphacodeList = new ArrayList<SelectItem>();
            amortList = new ArrayList<AmortDates>();
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getSecurityTypeData();
            getSellingStatusData();
            getBranchData();
            fieldReset();
            this.setMessage("");
            this.setSellingDate(dmy.format(dt));
            this.setSellingStatus("Full");
            this.setSellAmtVisible(true);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void optTypeLostFocus() {
        try {
            repDescList = new ArrayList<SelectItem>();
            repDescList.add(new SelectItem(""));
            List<String> entityList = remoteObj.getSecurityDesc(this.securityType);
            if (!entityList.isEmpty()) {
                for (String entity : entityList) {
                    repDescList.add(new SelectItem(entity));
                }
            } else {
                this.setMessage("There is no Security Description !");
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void optDescLostFocus() {
        try {
            ctrlNoList = new ArrayList<SelectItem>();
            List<InvestmentGoidates> entityList = remoteObj.getControllNoSecWise(this.securityType, this.getRepDesc());
            if (!entityList.isEmpty()) {
                for (InvestmentGoidates entity : entityList) {
                    ctrlNoList.add(new SelectItem(entity.getCtrlno()));
                }
            } else {
                this.setMessage("There is no control no !");
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onBlurSecurity() {
        this.setMessage("");
        if (this.getSecurityType().equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select security type !");
            return;
        }
        /***
         * Because of Investment_Goidates And Investment_Master combination for getting ctrl no.
         */
        optTypeLostFocus();        
    }

    public void onBlurCtrlNo() {
        this.setMessage("");
        int serialNo = 1, controllNo = 0;
        String purchaseDt = "", matDt = "", sellerNameVal = "", dtF = "", dtH = "", mode = "", secDesc = "";
        String dtpInt1 = "", dtpInt2 = "";
        double faceVal = 0.0, bookVal = 0.0, roiVal = 0.0, dayss = 0, totaldays = 0, aa = 0, aaa = 0, pricegSec = 0;
        BigDecimal bbb = new BigDecimal("0.0");

        try {
            if (this.getCtrlNo().equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Ctrl.No !");
                return;
            }
            Object[] obj = remoteObj.blurOnControllNo(Integer.parseInt(this.getCtrlNo()), this.getSecurityType());
            if (obj.length > 0) {
                InvestmentGoidates investmentGoidatesObj = (InvestmentGoidates) obj[0];
                if (investmentGoidatesObj != null) {
                    if (investmentGoidatesObj.getCtrlno() == null || investmentGoidatesObj.getCtrlno().toString().equals("")) {
                        controllNo = 0;
                    } else {
                        controllNo = investmentGoidatesObj.getCtrlno();
                    }
                    if (investmentGoidatesObj.getPurchasedt() == null || investmentGoidatesObj.getPurchasedt().toString().equals("")) {
                        purchaseDt = "";
                    } else {
                        purchaseDt = ymd.format(investmentGoidatesObj.getPurchasedt());
                    }
                    if (investmentGoidatesObj.getMatdt() == null || investmentGoidatesObj.getMatdt().toString().equals("")) {
                        matDt = "";
                    } else {
                        matDt = ymd.format(investmentGoidatesObj.getMatdt());
                    }
                    this.setAccruedInterest(Double.toString(investmentGoidatesObj.getTotAmtIntAccr()));                    
                }
                InvestmentMaster investmentMasterObj = (InvestmentMaster) obj[1];
                if (investmentMasterObj != null) {
                    faceVal = investmentMasterObj.getFacevalue();
                    bookVal = investmentMasterObj.getBookvalue();
                    if (investmentMasterObj.getSellername() == null || investmentMasterObj.getSellername().toString().equals("")) {
                        sellerNameVal = "";
                    } else {
                        sellerNameVal = investmentMasterObj.getSellername();
                    }
                    roiVal = investmentMasterObj.getRoi();

                    if (investmentMasterObj.getPricegsec() == null || investmentMasterObj.getPricegsec().toString().equals("")) {
                        pricegSec = 0;
                    } else {
                        pricegSec = investmentMasterObj.getPricegsec();
                    }
                    if (investmentMasterObj.getSecdesc() == null || investmentMasterObj.getSecdesc().toString().equals("")) {
                        secDesc = "";
                    } else {
                        secDesc = investmentMasterObj.getSecdesc();
                    }
                }

                List <InvestmentAmortizationDetails> entity = remoteObj.getAmortMode(Long.parseLong(String.valueOf(controllNo)));
                if (!entity.isEmpty()) {
                    mode = entity.get(0).getMode();
                }else{
                    mode = "";
                }

                Date currentDt = dmy.parse(getTodayDate());
                int compareDt = ymd.parse(matDt).compareTo(currentDt);
                if (compareDt < 0) {
                    matDt = matDt;
                } else {
                    matDt = ymd.format(currentDt);
                }

                /***Assignment of form field***/
                this.setSellerName(sellerNameVal);
                this.setFaceValue(formatter.format(faceVal));
                this.setBookValue(formatter.format(bookVal));
                this.setMaturityDate(dmy.format(ymd.parse(matDt)));
                this.setIssuingPrice(formatter.format(pricegSec));
                this.setSellingAmount(formatter.format(faceVal));
                
                BigDecimal BalAmt = new BigDecimal("0");
                if(!mode.equalsIgnoreCase("")){
                    BalAmt = remoteObj.getTotalAmortAmtBal(Integer.parseInt(ctrlNo),"A");
                    if(BalAmt == null){
                        int doubleCompare = Double.compare(faceVal, bookVal);
                        if (doubleCompare > 0) {
                            BalAmt = new BigDecimal(0);
                        }else{
                            this.setMessage("There is problem in getting total sum of amort amount from INVESTMENT AMORTIZATION DETAILS table!");
                            return;                        
                        }                    
                    }
                }
                /***Setting amortization value of form***/
                this.setAmortizationValue(formatter.format((Object) (BalAmt)));  
                
                dtpInt1 = dmy.format(investmentGoidatesObj.getLastintpaiddt());
                dtpInt1 = ymd.format(dmy.parse(dtpInt1));
                dtpInt2 = CbsUtil.monthAdd(dtpInt1, 6);                
                List<GoiAccrutinterestTable> resultDataList = goiAccrutinterest(controllNo, getUserName(), dtpInt1, dtpInt2, ymd.format(dt), faceVal, roiVal);
                if (!resultDataList.isEmpty()) {
                    for (GoiAccrutinterestTable table : resultDataList) {
                        intAccAmt = table.getIntAmt();
                    }
                }
                this.setBalIntValue(String.valueOf(formatter.format(intAccAmt)));
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

//    public double amortDays(String amortDateT, String amortDateF, String amortMode) {
//        double days = 0, fullMonth, part1;
//        String dateBegin = "", dateEnd = "";
//        try {
//            Date amortdateT = ymd.parse(amortDateT);
//            Date amortdateF = ymd.parse(amortDateF);
//            String dmyAmortDateT = dmy.format(amortdateT);
//            String dmyAmortDateF = dmy.format(amortdateF);
//
//            if (amortMode.equalsIgnoreCase("FH")) {
//                int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
//                if (monDiff == 6) {
//                    return days = 180;
//                }
//                if (!dmyAmortDateF.substring(0, 5).equals("30/09") && !dmyAmortDateF.substring(0, 5).equals("31/03")) {
//                    int mDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
//                    if (mDiff < 12) {
//                        dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
//                        int compare = amortdateF.compareTo(dmy.parse(dateBegin));
//                        if (compare != 0) {
//                            dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
//                            fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
//                            part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
//                            days = (fullMonth * 30) + part1;
//                        } else {
//                            fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
//                            days = (fullMonth * 30);
//                        }
//                    }
//                }
//                if (!dmyAmortDateT.substring(0, 5).equals("30/09") && !dmyAmortDateT.substring(0, 5).equals("31/03")) {
//                    if (monDiff < 12) {
//                        dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
//                        dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
//                        fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
//                        part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
//                        days = (fullMonth * 30) + part1;
//                    }
//                }
//            }
//            if (amortMode.equalsIgnoreCase("FC")) {
//                int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
//                if (monDiff == 6) {
//                    return days = 180;
//                }
//                if (!dmyAmortDateF.substring(0, 5).equals("30/06") && !dmyAmortDateF.substring(0, 5).equals("31/12")) {
//                    int mDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
//                    if (mDiff < 12) {
//                        dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
//                        int compare = amortdateF.compareTo(dmy.parse(dateBegin));
//                        if (compare != 0) {
//                            dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
//                            fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
//                            part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
//                            days = (fullMonth * 30) + part1;
//                        } else {
//                            fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
//                            days = (fullMonth * 30);
//                        }
//                    }
//                }
//                if (!dmyAmortDateT.substring(0, 5).equals("30/06") && !dmyAmortDateT.substring(0, 5).equals("31/12")) {
//                    int moDiff = CbsUtil.monthDiff(amortdateT, amortdateF);
//                    if (moDiff < 12) {
//                        dateEnd = "01" + dmyAmortDateT.substring(3, 5) + dmyAmortDateT.substring(6);
//                        dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
//                        fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
//                        part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
//                        days = (fullMonth * 30) + part1;
//                    }
//                }
//            }
//            if (amortMode.equalsIgnoreCase("FY")) {
//                int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
//                if (monDiff == 12) {
//                    return days = 360;
//                }
//                if (!dmyAmortDateF.substring(0, 5).equals("31/03")) {
//                    if (monDiff < 12) {
//                        dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
//                        int compare = amortdateF.compareTo(dmy.parse(dateBegin));
//                        if (compare != 0) {
//                            dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
//                            fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
//                            part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
//                            days = (fullMonth * 30) + part1;
//                        } else {
//                            fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
//                            days = (fullMonth * 30);
//                        }
//                    }
//                }
//                if (!dmyAmortDateT.substring(0, 5).equals("31/03")) {
//                    int moDiff = CbsUtil.monthDiff(amortdateT, amortdateF);
//                    if (moDiff < 12) {
//                        dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
//                        dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
//                        fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
//                        part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
//                        days = (fullMonth * 30) + part1;
//                    }
//                }
//            }
//            if (amortMode.equalsIgnoreCase("CY")) {
//                int monDiff = CbsUtil.monthDiff(amortdateF, amortdateT);
//                if (monDiff == 12) {
//                    return days = 360;
//                }
//                if (!dmyAmortDateF.substring(0, 5).equals("31/12")) {
//                    if (monDiff < 12) {
//                        dateBegin = "01" + dmyAmortDateF.substring(2, 6) + dmyAmortDateF.substring(6);
//                        int compare = amortdateF.compareTo(dmy.parse(dateBegin));
//                        if (compare != 0) {
//                            dateBegin = CbsUtil.monthAdd(ymd.format(dmy.parse(dateBegin)), 1);
//                            fullMonth = CbsUtil.monthDiff(ymd.parse(dateBegin), amortdateT) + 1;
//                            part1 = CbsUtil.dayDiff(amortdateF, ymd.parse(dateBegin)) - 1;
//                            days = (fullMonth * 30) + part1;
//                        } else {
//                            fullMonth = CbsUtil.monthDiff(dmy.parse(dateBegin), amortdateT) + 1;
//                            days = (fullMonth * 30);
//                        }
//                    }
//                }
//                if (!dmyAmortDateT.substring(0, 5).equals("31/12")) {
//                    int moDiff = CbsUtil.monthDiff(amortdateT, amortdateF);
//                    if (moDiff < 12) {
//                        dateEnd = "01" + dmyAmortDateT.substring(2, 6) + dmyAmortDateT.substring(6);
//                        dateEnd = CbsUtil.dateAdd(ymd.format(dmy.parse(dateEnd)), -1);
//                        fullMonth = CbsUtil.monthDiff(amortdateF, ymd.parse(dateEnd));
//                        part1 = CbsUtil.dayDiff(ymd.parse(dateEnd), amortdateT);
//                        days = (fullMonth * 30) + part1;
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            this.setMessage(ex.getMessage());
//        }
//        return days;
//    }

    public List<GoiAccrutinterestTable> goiAccrutinterest(Integer controllNo, String user, String dtpInt1, String dtpInt2, String purchaseDt, double faceVal, double roiVal) {
        List<GoiAccrutinterestTable> result = new ArrayList<GoiAccrutinterestTable>();
        String date1 = "", firstDt = "", firstDt1 = "", secondDt = "";
        int fullMonth = 0;
        long partDays1 = 0, partDays2 = 0;
        double totaldays = 0, intAmt = 0;
        try {
            Date intDt1 = ymd.parse(dtpInt1);
            Date intDt2 = ymd.parse(dtpInt2);
            Date purDt = ymd.parse(purchaseDt);


            if (CbsUtil.dayDiff(purDt, intDt1) > 0) {
                date1 = CbsUtil.monthAdd(dtpInt1, -6);
                if (CbsUtil.monthDiff(ymd.parse(date1), purDt) == 0) {
                    fullMonth = CbsUtil.monthDiff(ymd.parse(date1), purDt);
                    partDays1 = CbsUtil.dayDiff(ymd.parse(date1), purDt);
                    partDays2 = 0;
                }
                if (CbsUtil.monthDiff(ymd.parse(date1), purDt) != 0) {
                    fullMonth = CbsUtil.monthDiff(ymd.parse(date1), purDt) - 1;
                    firstDt = CbsUtil.monthAdd(date1, 1);
                    firstDt1 = firstDt.substring(0, 4) + firstDt.substring(4, 6) + "01";
                    if (fullMonth >= 0) {
                        partDays1 = CbsUtil.dayDiff(ymd.parse(date1), ymd.parse(firstDt1)) - 1;
                    } else {
                        partDays1 = 0;
                    }
                    secondDt = purchaseDt.substring(0, 4) + purchaseDt.substring(4, 6) + "01";
                    partDays2 = CbsUtil.dayDiff(ymd.parse(secondDt), purDt) + 1;
                }
                totaldays = partDays1 + partDays2 + (30 * fullMonth);
                intAmt = (faceVal * roiVal * totaldays) / 36000;
                GoiAccrutinterestTable tableObj = new GoiAccrutinterestTable();
                tableObj.setTotalDays(totaldays);
                tableObj.setIntAmt(intAmt);
                result.add(tableObj);
            }
            /***Second Case***/
            if (CbsUtil.dayDiff(intDt1, purDt) > 0 && CbsUtil.dayDiff(purDt, intDt2) > 0) {
                if (CbsUtil.monthDiff(intDt1, purDt) == 0) {
                    fullMonth = CbsUtil.monthDiff(intDt1, purDt);
                    partDays1 = CbsUtil.dayDiff(intDt1, purDt);
                    partDays2 = 0;
                }
                if (CbsUtil.monthDiff(intDt1, purDt) != 0) {
                    fullMonth = CbsUtil.monthDiff(intDt1, purDt) - 1;
                    firstDt = CbsUtil.monthAdd(dtpInt1, 1);
                    firstDt1 = firstDt.substring(0, 4) + firstDt.substring(4, 6) + "01";
                    if (fullMonth >= 0) {
                        partDays1 = CbsUtil.dayDiff(intDt1, ymd.parse(firstDt1)) - 1;
                    } else {
                        partDays1 = 0;
                    }
                    secondDt = purchaseDt.substring(0, 4) + purchaseDt.substring(4, 6) + "01";
                    partDays2 = CbsUtil.dayDiff(ymd.parse(secondDt), purDt) + 1;
                }
                totaldays = partDays1 + partDays2 + (30 * fullMonth);
                intAmt = (faceVal * roiVal * totaldays) / 36000;
                GoiAccrutinterestTable tableObj = new GoiAccrutinterestTable();
                tableObj.setTotalDays(totaldays);
                tableObj.setIntAmt(intAmt);
                result.add(tableObj);
            }
            /***Third Case***/
            if (CbsUtil.dayDiff(intDt2, purDt) > 0) {
                if (CbsUtil.monthDiff(intDt2, purDt) == 0) {
                    fullMonth = CbsUtil.monthDiff(intDt2, purDt);
                    partDays1 = CbsUtil.dayDiff(intDt2, purDt);
                    partDays2 = 0;
                }
                if (CbsUtil.monthDiff(intDt2, purDt) != 0) {
                    fullMonth = CbsUtil.monthDiff(intDt2, purDt) - 1;
                    firstDt = CbsUtil.monthAdd(dtpInt2, 1);
                    firstDt1 = firstDt.substring(0, 4) + firstDt.substring(4, 6) + "01";
                    if (fullMonth >= 0) {
                        partDays1 = CbsUtil.dayDiff(intDt2, ymd.parse(firstDt1)) - 1;
                    } else {
                        partDays1 = 0;
                    }
                    secondDt = purchaseDt.substring(0, 4) + purchaseDt.substring(4, 6) + "01";
                    partDays2 = CbsUtil.dayDiff(ymd.parse(secondDt), purDt) + 1;
                }
                totaldays = partDays1 + partDays2 + (30 * fullMonth);
                intAmt = (faceVal * roiVal * totaldays) / 36000;
                GoiAccrutinterestTable tableObj = new GoiAccrutinterestTable();
                tableObj.setTotalDays(totaldays);
                tableObj.setIntAmt(intAmt);
                result.add(tableObj);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return result;
    }

    public void onBlurSellingStatus() {
        this.setMessage("");
        if (this.getSellingStatus().equalsIgnoreCase("Full")) {
            this.sellAmtVisible = true;
            this.setSellingAmount(this.getFaceValue());
        } else if (this.getSellingStatus().equalsIgnoreCase("Partial")) {
            this.sellAmtVisible = false;
            this.setSellingAmount("");
        }
    }

    public void onBlurCrHead() {
        this.setMessage("");
        this.setCrHeadName("");
        try {
            if (this.getCrGLHead() == null || this.getCrGLHead().equals("")) {
                this.setMessage("Please select Debited GL Head !");
                return;
            }
            if (!this.getCrGLHead().matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in Debited GL Head !");
                return;
            }
            if (this.getCrGLHead().length() < 12) {
                this.setMessage("Please enter 12 digit Debited GL Head !");
                return;
            }
            
            if (!this.getCrGLHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())) {
                this.setMessage("Please enter Debited GL Head of your branch!");
                return;
            }
            
            Gltable entity = remoteObj.getGltableByAcno(this.crGLHead);
            if (entity != null) {
                this.setCrHeadName(entity.getAcName());
            }
            
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveAction() {
        this.setMessage("");
        int maxSec = 0;
        String msg = "";
        if (this.securityType == null || this.securityType.equalsIgnoreCase("") || this.securityType.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select security type !");
            return;
        }
        if (this.ctrlNo == null || this.ctrlNo.equalsIgnoreCase("") || this.ctrlNo.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select Ctrl.No !");
            return;
        }
        if (this.sellingAmount == null || this.sellingAmount.equals("")) {
            this.setMessage("Please fill selling amount !");
            return;
        }
        
        if(sellingStatus.equalsIgnoreCase("Full")){
            if (Double.parseDouble(this.sellingAmount) > Double.parseDouble(this.faceValue)) {
                this.setMessage("Selling amount should not be greater than face vale !");
                return;
            }
        }
        
        if (this.crGLHead == null || this.crGLHead.equalsIgnoreCase("")) {
            this.setMessage("Please fill credited gl head !");
            return;
        }
        
        if(!this.getCrGLHead().substring(0, 2).equalsIgnoreCase(this.getOrgnBrCode())){
            this.setMessage("Please credited gl head of your branch!");
            return;
        }
        
        if(this.crHeadName == null || this.crHeadName.equalsIgnoreCase("")){
            this.setMessage("Please check Debit gl head !");
            return;
        }
        try {            
            msg = remoteObj.saveSecurityCloseAuthDetail(securityType, repDesc, ctrlNo,sellerName, faceValue,bookValue,
                maturityDate,accruedInterest, amortizationValue,balIntValue, issuingPrice,sellingStatus, sellingAmount,
                sellingDate, crGLHead, alphacode, getUserName(), getOrgnBrCode());
            if (msg.equalsIgnoreCase("true")) {
                getControlNoData();
                fieldReset();
                this.setMessage("Data has been saved successfully");
                return;
            }            
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getSecurityTypeData() {
        securityTypeList.add(new SelectItem("--Select--"));
        try {
            List<GlDescRange> entityList = remoteObj.getGlRange("G");
            if (!entityList.isEmpty()) {
                for (GlDescRange entity : entityList) {
                    securityTypeList.add(new SelectItem(entity.getGlname()));                  
                }
            } else {
                this.setMessage("Range is not found for government security !");
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getControlNoData() {
        ctrlNoList = new ArrayList<SelectItem>();
        ctrlNoList.add(new SelectItem("--Select--"));
        try {
            List<InvestmentGoidates> entityList = remoteObj.getControllNo(this.securityType);
            if (!entityList.isEmpty()) {
                for (InvestmentGoidates entity : entityList) {
                    ctrlNoList.add(new SelectItem(entity.getCtrlno()));
                }
            } else {
                this.setMessage("There is no control no !");
                return;
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getSellingStatusData() {
        try {
            sellingStatusList.add(new SelectItem("Full","Mature"));
            sellingStatusList.add(new SelectItem("Partial","Premature"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getBranchData() {
        alphacodeList = new ArrayList<SelectItem>();
        try {
            List<BranchMaster> branchMasterList = remoteObj.getAllAlphaCode();
            if (!branchMasterList.isEmpty()) {
                for (BranchMaster entity : branchMasterList) {
                    alphacodeList.add(new SelectItem(entity.getAlphaCode()));
                }
                this.setAlphacode("HO");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fieldReset() {
        this.setSellerName("");
        this.setFaceValue("");
        this.setBookValue("");
        this.setMaturityDate("");
        this.setAccruedInterest("");
        this.setAmortizationValue("");
        this.setIssuingPrice("");
        this.setSellingAmount("");
        this.setCrGLHead("");        
        this.setCrHeadName("");
    }

    public void resetForm() {
        this.setMessage("");
        fieldReset();
        this.setSellingDate(dmy.format(dt));
        this.setSecurityType("--Select--");
        //this.setCtrlNo("--Select--");
        this.setSellingStatus("Full");
        this.setSellAmtVisible(true);
        amortList = new ArrayList<AmortDates>();
        repDescList = new ArrayList<SelectItem>();
        this.intAccAmt = 0;
        this.setRepDesc("");
        getControlNoData();
        this.setMessage("Please select Security Type !");
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}