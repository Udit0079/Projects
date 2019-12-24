/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.InvestmentFDRCloseRenewAuthPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author sipl
 */
public class fdrClosing extends BaseBean {

    private String todayDate;
    private String loggedInUser;
    private String message;
    private String ctrl;
    private List<SelectItem> ctrlList;
    private String fdrNo;
    private String sPurDate;
    private String sMatDate;
    private String sFaceValue;
    private String sBookvalue;
    private String sellerNmVal;
    private String sRoi;
    private String sIntOpt;
    private String sBranch;
    private String renClose;
    private String renCloseVal;
    private String close;
    private String renPurDt;
    private String purchaseDate;
    private String days;
    private String months;
    private String years;
    private String matDate;
    private String roi;
    private String intOpt;
    private List<SelectItem> intOptList;
    private String faceValue;
    private String bookvalue;
    private String interest;
    private String intCrGlHead;
    private String intCrGlHeadDesc;
    private String intCrGlHeadVal;
    private String intRecCrGlHead;
    private String intRecCrGlHeadDesc;
    private String intRecCrGlHeadVal;
    private String faceValCrGlHead;
    private String faceValCrGlHeadDesc;
    private String faceValCrGlHeadVal;
    private String bookValDrGlHead;
    private String bookValDrGlHeadDesc;
    private String bookValDrGlHeadVal;   
    private String intAccGlHead;
    private String intAccGlHeadDesc;
    private String intAccGlHeadVal;    
    private boolean disRenPurDt;
    private boolean disPurDt;
    private boolean disDays;
    private boolean disMonths;
    private boolean disYears;
    private boolean disMatDt;
    private boolean disRoi;
    private boolean disIntOpt;
    private boolean disFaceValue;
    private boolean disInt;
    private boolean disBookValue;
    private boolean disClose;
    private boolean disRecCrGlHeadVal;
    private String flag1;
    private InvestmentMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    private TdReceiptManagementFacadeRemote remoteTdInterface = null;
    private final String jndiHomeNameTd = "TdReceiptManagementFacade";    
    
    NumberFormat formatter = new DecimalFormat("#.##");
    
    /* NEW ADDED CODE */
    
    private String function;
    private List<SelectItem> functionList;
    private boolean disRadioClose;
    private boolean disIntAccGlHead;
    private boolean disIntRecGlHead;
    private boolean disCrGlHead;
    private boolean disFCrGlHead;
    private boolean disBookGlHead;
    private boolean disSave;
    private boolean disAuth;
    private boolean disDelete;
    private boolean disFunction;
    private boolean disCtrl;
    private String pEnterBy;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public boolean isDisRadioClose() {
        return disRadioClose;
    }

    public void setDisRadioClose(boolean disRadioClose) {
        this.disRadioClose = disRadioClose;
    }

    public boolean isDisIntAccGlHead() {
        return disIntAccGlHead;
    }

    public void setDisIntAccGlHead(boolean disIntAccGlHead) {
        this.disIntAccGlHead = disIntAccGlHead;
    }

    public boolean isDisIntRecGlHead() {
        return disIntRecGlHead;
    }

    public void setDisIntRecGlHead(boolean disIntRecGlHead) {
        this.disIntRecGlHead = disIntRecGlHead;
    }

    public boolean isDisCrGlHead() {
        return disCrGlHead;
    }

    public void setDisCrGlHead(boolean disCrGlHead) {
        this.disCrGlHead = disCrGlHead;
    }

    public boolean isDisFCrGlHead() {
        return disFCrGlHead;
    }

    public void setDisFCrGlHead(boolean disFCrGlHead) {
        this.disFCrGlHead = disFCrGlHead;
    }

    public boolean isDisBookGlHead() {
        return disBookGlHead;
    }

    public void setDisBookGlHead(boolean disBookGlHead) {
        this.disBookGlHead = disBookGlHead;
    }

    public boolean isDisSave() {
        return disSave;
    }

    public void setDisSave(boolean disSave) {
        this.disSave = disSave;
    }

    public boolean isDisAuth() {
        return disAuth;
    }

    public void setDisAuth(boolean disAuth) {
        this.disAuth = disAuth;
    }

    public boolean isDisDelete() {
        return disDelete;
    }

    public void setDisDelete(boolean disDelete) {
        this.disDelete = disDelete;
    }
    
    public boolean isDisFunction() {
        return disFunction;
    }

    public void setDisFunction(boolean disFunction) {
        this.disFunction = disFunction;
    }

    public boolean isDisCtrl() {
        return disCtrl;
    }

    public void setDisCtrl(boolean disCtrl) {
        this.disCtrl = disCtrl;
    }

    public String getpEnterBy() {
        return pEnterBy;
    }

    public void setpEnterBy(String pEnterBy) {
        this.pEnterBy = pEnterBy;
    }
    
    /* END */
    
    public String getBookvalue() {
        return bookvalue;
    }

    public void setBookvalue(String bookvalue) {
        this.bookvalue = bookvalue;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getCtrl() {
        return ctrl;
    }

    public void setCtrl(String ctrl) {
        this.ctrl = ctrl;
    }

    public List<SelectItem> getCtrlList() {
        return ctrlList;
    }

    public void setCtrlList(List<SelectItem> ctrlList) {
        this.ctrlList = ctrlList;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public String getFdrNo() {
        return fdrNo;
    }

    public void setFdrNo(String fdrNo) {
        this.fdrNo = fdrNo;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public List<SelectItem> getIntOptList() {
        return intOptList;
    }

    public void setIntOptList(List<SelectItem> intOptList) {
        this.intOptList = intOptList;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getRenClose() {
        return renClose;
    }

    public void setRenClose(String renClose) {
        this.renClose = renClose;
    }

    public String getRenCloseVal() {
        return renCloseVal;
    }

    public void setRenCloseVal(String renCloseVal) {
        this.renCloseVal = renCloseVal;
    }

    public String getRenPurDt() {
        return renPurDt;
    }

    public void setRenPurDt(String renPurDt) {
        this.renPurDt = renPurDt;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getsBookvalue() {
        return sBookvalue;
    }

    public void setsBookvalue(String sBookvalue) {
        this.sBookvalue = sBookvalue;
    }

    public String getsBranch() {
        return sBranch;
    }

    public void setsBranch(String sBranch) {
        this.sBranch = sBranch;
    }

    public String getsFaceValue() {
        return sFaceValue;
    }

    public void setsFaceValue(String sFaceValue) {
        this.sFaceValue = sFaceValue;
    }

    public String getsIntOpt() {
        return sIntOpt;
    }

    public void setsIntOpt(String sIntOpt) {
        this.sIntOpt = sIntOpt;
    }

    public String getsMatDate() {
        return sMatDate;
    }

    public void setsMatDate(String sMatDate) {
        this.sMatDate = sMatDate;
    }

    public String getsPurDate() {
        return sPurDate;
    }

    public void setsPurDate(String sPurDate) {
        this.sPurDate = sPurDate;
    }

    public String getsRoi() {
        return sRoi;
    }

    public void setsRoi(String sRoi) {
        this.sRoi = sRoi;
    }

    public String getSellerNmVal() {
        return sellerNmVal;
    }

    public void setSellerNmVal(String sellerNmVal) {
        this.sellerNmVal = sellerNmVal;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getBookValDrGlHead() {
        return bookValDrGlHead;
    }

    public void setBookValDrGlHead(String bookValDrGlHead) {
        this.bookValDrGlHead = bookValDrGlHead;
    }

    public String getBookValDrGlHeadDesc() {
        return bookValDrGlHeadDesc;
    }

    public void setBookValDrGlHeadDesc(String bookValDrGlHeadDesc) {
        this.bookValDrGlHeadDesc = bookValDrGlHeadDesc;
    }

    public String getBookValDrGlHeadVal() {
        return bookValDrGlHeadVal;
    }

    public void setBookValDrGlHeadVal(String bookValDrGlHeadVal) {
        this.bookValDrGlHeadVal = bookValDrGlHeadVal;
    }

    public String getFaceValCrGlHead() {
        return faceValCrGlHead;
    }

    public void setFaceValCrGlHead(String faceValCrGlHead) {
        this.faceValCrGlHead = faceValCrGlHead;
    }

    public String getFaceValCrGlHeadDesc() {
        return faceValCrGlHeadDesc;
    }

    public void setFaceValCrGlHeadDesc(String faceValCrGlHeadDesc) {
        this.faceValCrGlHeadDesc = faceValCrGlHeadDesc;
    }

    public String getFaceValCrGlHeadVal() {
        return faceValCrGlHeadVal;
    }

    public void setFaceValCrGlHeadVal(String faceValCrGlHeadVal) {
        this.faceValCrGlHeadVal = faceValCrGlHeadVal;
    }

    public String getIntCrGlHead() {
        return intCrGlHead;
    }

    public void setIntCrGlHead(String intCrGlHead) {
        this.intCrGlHead = intCrGlHead;
    }

    public String getIntCrGlHeadDesc() {
        return intCrGlHeadDesc;
    }

    public void setIntCrGlHeadDesc(String intCrGlHeadDesc) {
        this.intCrGlHeadDesc = intCrGlHeadDesc;
    }

    public String getIntCrGlHeadVal() {
        return intCrGlHeadVal;
    }

    public void setIntCrGlHeadVal(String intCrGlHeadVal) {
        this.intCrGlHeadVal = intCrGlHeadVal;
    }

    public String getIntRecCrGlHead() {
        return intRecCrGlHead;
    }

    public void setIntRecCrGlHead(String intRecCrGlHead) {
        this.intRecCrGlHead = intRecCrGlHead;
    }

    public String getIntRecCrGlHeadDesc() {
        return intRecCrGlHeadDesc;
    }

    public void setIntRecCrGlHeadDesc(String intRecCrGlHeadDesc) {
        this.intRecCrGlHeadDesc = intRecCrGlHeadDesc;
    }

    public String getIntRecCrGlHeadVal() {
        return intRecCrGlHeadVal;
    }

    public void setIntRecCrGlHeadVal(String intRecCrGlHeadVal) {
        this.intRecCrGlHeadVal = intRecCrGlHeadVal;
    }

    public boolean isDisRenPurDt() {
        return disRenPurDt;
    }

    public void setDisRenPurDt(boolean disRenPurDt) {
        this.disRenPurDt = disRenPurDt;
    }

    public boolean isDisPurDt() {
        return disPurDt;
    }

    public void setDisPurDt(boolean disPurDt) {
        this.disPurDt = disPurDt;
    }

    public boolean isDisDays() {
        return disDays;
    }

    public void setDisDays(boolean disDays) {
        this.disDays = disDays;
    }

    public boolean isDisMonths() {
        return disMonths;
    }

    public void setDisMonths(boolean disMonths) {
        this.disMonths = disMonths;
    }

    public boolean isDisYears() {
        return disYears;
    }

    public void setDisYears(boolean disYears) {
        this.disYears = disYears;
    }

    public boolean isDisBookValue() {
        return disBookValue;
    }

    public void setDisBookValue(boolean disBookValue) {
        this.disBookValue = disBookValue;
    }

    public boolean isDisFaceValue() {
        return disFaceValue;
    }

    public void setDisFaceValue(boolean disFaceValue) {
        this.disFaceValue = disFaceValue;
    }

    public boolean isDisInt() {
        return disInt;
    }

    public void setDisInt(boolean disInt) {
        this.disInt = disInt;
    }

    public boolean isDisIntOpt() {
        return disIntOpt;
    }

    public void setDisIntOpt(boolean disIntOpt) {
        this.disIntOpt = disIntOpt;
    }

    public boolean isDisMatDt() {
        return disMatDt;
    }

    public void setDisMatDt(boolean disMatDt) {
        this.disMatDt = disMatDt;
    }

    public boolean isDisRoi() {
        return disRoi;
    }

    public void setDisRoi(boolean disRoi) {
        this.disRoi = disRoi;
    }

    public boolean isDisClose() {
        return disClose;
    }

    public void setDisClose(boolean disClose) {
        this.disClose = disClose;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public boolean isDisRecCrGlHeadVal() {
        return disRecCrGlHeadVal;
    }

    public void setDisRecCrGlHeadVal(boolean disRecCrGlHeadVal) {
        this.disRecCrGlHeadVal = disRecCrGlHeadVal;
    }       

    public String getIntAccGlHeadDesc() {
        return intAccGlHeadDesc;
    }

    public void setIntAccGlHeadDesc(String intAccGlHeadDesc) {
        this.intAccGlHeadDesc = intAccGlHeadDesc;
    }

    public String getIntAccGlHead() {
        return intAccGlHead;
    }

    public void setIntAccGlHead(String intAccGlHead) {
        this.intAccGlHead = intAccGlHead;
    }

    public String getIntAccGlHeadVal() {
        return intAccGlHeadVal;
    }

    public void setIntAccGlHeadVal(String intAccGlHeadVal) {
        this.intAccGlHeadVal = intAccGlHeadVal;
    }
    
    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public fdrClosing() {
        try {
            this.setFlag1("true");
            this.setTodayDate(sdf.format(dt));
            this.setLoggedInUser(this.getUserName());
            this.setRenPurDt(sdf.format(dt));

            remoteInterface = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            remoteTdInterface = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTd);            

            intOptList = new ArrayList<SelectItem>();
            intOptList.add(new SelectItem("S", "SIMPLE"));
            intOptList.add(new SelectItem("C", "CUMULATIVE"));
            intOptList.add(new SelectItem("M", "MONTHLY"));
            intOptList.add(new SelectItem("Q", "QUARTERLY"));
            
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("A", "ADD"));
            functionList.add(new SelectItem("V", "VERIFY"));

//            loadctrlno();
            investValueSet();
            this.disRenPurDt = true;
            this.disSave = false;
            this.disAuth = true;
            this.disDelete = true;
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void functionAction(){
        if(function.equalsIgnoreCase("A")){
            this.disSave = false;
            this.disAuth = true;
            this.disDelete = true;
            loadctrlno();
        }else{
            this.disSave = true;
            this.disAuth = false;
            this.disDelete = false;
            loadUnAuthCtrlno();
        }
    }

//    public void loadctrlno() {
//        try {
//            ctrlList = new ArrayList<SelectItem>();
//            List<Integer> ctrList = new ArrayList<Integer>();
//            ctrList = remoteInterface.getCtrlNoFromInvestmentFdrDetails();
//            for (int i = 0; i < ctrList.size(); i++) {
//                String ctrlN = ctrList.get(i).toString();
//                ctrlList.add(new SelectItem(ctrlN));
//            }
//        } catch (Exception e) {
//            this.setMessage(e.getMessage());
//        }
//    }
    public void loadctrlno() {
        try {
            ctrlList = new ArrayList<SelectItem>();
            List ctrList = remoteInterface.getCtrlNoFromFdrDetails();
            for (int i = 0; i < ctrList.size(); i++) {
                Vector ele = (Vector) ctrList.get(i);
                ctrlList.add(new SelectItem(ele.get(0).toString()));                
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void loadUnAuthCtrlno() {
        try {
            ctrlList = new ArrayList<SelectItem>();
            List ctrList = remoteInterface.getUnAuthFDRCloseCtrlNo();
            for (int i = 0; i < ctrList.size(); i++) {
                Vector ele = (Vector) ctrList.get(i);
                ctrlList.add(new SelectItem(ele.get(0).toString()));                
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void investValueSet() {
        try {
            InvestmentCallHead entity = remoteInterface.getInvestCallHeadByCode("6");
            if (entity != null) {
                this.setBookValDrGlHead(entity.getCrGlhead());
                this.setIntRecCrGlHead(entity.getDrGlhead());
                this.setIntAccGlHead(entity.getIntGlhead());

                this.setBookValDrGlHeadDesc(acDescVal(this.getBookValDrGlHead()));
                this.setIntRecCrGlHeadDesc(acDescVal(this.getIntRecCrGlHead()));
                this.setIntAccGlHeadDesc(acDescVal(this.getIntAccGlHead()));
            }                     
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void ctrlAction() {
        this.setMessage("");
        this.setRenClose("");
        this.setClose("");
        this.setRenCloseVal("");
        this.disFunction = true;
        this.disCtrl = true;
        try {            
            if(function.equalsIgnoreCase("A")){
//                InvestmentCallHead entity = remoteInterface.getInvestCallHeadByCode("6");
//                if (entity != null) {
//                    this.setBookValDrGlHead(entity.getCrGlhead());
//                    this.setIntRecCrGlHead(entity.getDrGlhead());
//                }
                investValueSet();

                Object[] dataArray = remoteInterface.getInvestmentDetailsAndDates("ACTIVE", Integer.parseInt(this.getCtrl()));
                if (dataArray.length > 0) {
                    InvestmentFdrDates entityFdr = (InvestmentFdrDates) dataArray[0];
                    if (entityFdr != null) {
                        this.setsPurDate(sdf.format(entityFdr.getPurchaseDt()));
                        this.setsMatDate(sdf.format(entityFdr.getMatDt()));
                        this.setDays(entityFdr.getDays().toString());
                        this.setMonths(entityFdr.getMonths().toString());
                        this.setYears(entityFdr.getYears().toString());
                        this.setsIntOpt(entityFdr.getIntOpt());
                    }
                    
                    InvestmentFdrDetails entityObj = (InvestmentFdrDetails) dataArray[1];
                    if (entityObj != null) {
                        this.setFdrNo(entityObj.getFdrNo());
                        this.setSellerNmVal(entityObj.getSellerName());
                        this.setsBranch(entityObj.getBranch());
                        this.setsRoi(String.valueOf(entityObj.getRoi()));
                        if(entityFdr.getIntOpt().equalsIgnoreCase("S")){
                            this.setIntAccGlHeadVal(formatter.format(entityFdr.getTotAmtIntRec()));
                        }else{
                            this.setIntAccGlHeadVal(formatter.format((entityFdr.getTotAmtIntRec()+ entityFdr.getAmtIntAccr())));
                        }                        
                        this.setsFaceValue(formatter.format(entityObj.getFaceValue()));
                        this.setsBookvalue(formatter.format(entityObj.getBookValue()));
                        this.setBookValDrGlHeadVal(formatter.format(entityObj.getBookValue()));
                        if(entityFdr.getIntOpt().equalsIgnoreCase("S")){
                         this.setFaceValCrGlHeadVal(formatter.format(entityObj.getFaceValue())); 
                         this.setBookValDrGlHeadVal(formatter.format(entityObj.getFaceValue()));
                        }else{
                        this.setFaceValCrGlHeadVal(formatter.format(entityObj.getBookValue())); 
                        this.setBookValDrGlHeadVal(formatter.format(entityObj.getBookValue()));
                        }
//                        if(entityFdr.getIntOpt().equalsIgnoreCase("S")){
//                            this.setIntRecCrGlHeadVal(formatter.format(entityFdr.getTotAmtIntRec()));
//                            this.setIntCrGlHeadVal(formatter.format((entityFdr.getTotAmtIntRec()) + (entityObj.getBookValue() - entityObj.getFaceValue() - (entityFdr.getTotAmtIntRec()))));                    
//                        }else{
                            this.setIntRecCrGlHeadVal(formatter.format(entityObj.getBookValue() - entityObj.getFaceValue() - (entityFdr.getTotAmtIntRec()+ entityFdr.getAmtIntAccr())));
                            this.setIntCrGlHeadVal(formatter.format((entityFdr.getTotAmtIntRec()+ entityFdr.getAmtIntAccr()) + (entityObj.getBookValue() - entityObj.getFaceValue() - (entityFdr.getTotAmtIntRec()+ entityFdr.getAmtIntAccr()))));                    
                      // }                        
                    }
                    
                    List<Gltable> gltableList = remoteInterface.getGltable(this.getSellerNmVal());
                    if (!gltableList.isEmpty()) {
                        for (Gltable glEntity : gltableList) {
                            this.setIntCrGlHead(glEntity.getAcNo());
                            this.setFaceValCrGlHead(glEntity.getAcNo());
                            
                            this.setIntCrGlHeadDesc(acDescVal(this.getIntCrGlHead()));
                            this.setFaceValCrGlHeadDesc(acDescVal(this.getFaceValCrGlHead()));                        
                        }
                    }
                } else {
                    this.setMessage("There is no data for control number: " + this.getCtrl());
                    return;
                }                
            } else{
                List<InvestmentFDRCloseRenewAuthPojo> dataList1 = remoteInterface.getUnAuthFDRCloseRenew(this.getCtrl());
                if (!dataList1.isEmpty()) {
                    for (InvestmentFDRCloseRenewAuthPojo entity1 : dataList1) {
                        
                        this.setFdrNo(entity1.getFdrNo().toString());
                        this.setsPurDate(entity1.getsPurchaseDate().toString());
                        this.setsMatDate(entity1.getsMaturityDate().toString());
                        this.setsFaceValue(entity1.getsFaceValue().toString());
                        this.setsBookvalue(entity1.getsBookValue().toString());
                        this.setSellerNmVal(entity1.getSellerName().toString());
                        this.setsRoi(entity1.getsRoi().toString());
                        this.setsIntOpt(entity1.getsIntOpt().toString());
                        this.setsBranch(entity1.getsBranch().toString());
                        this.setRenClose(entity1.getCloseRenew().toString());
                        this.setClose(entity1.getManualAuto().toString());
                        this.setRenPurDt(entity1.getRenPurDate().toString());
                        this.setPurchaseDate(entity1.getfPurchaseDate().toString());
                        this.setDays(entity1.getDays().toString());
                        this.setMonths(entity1.getMonths().toString());
                        this.setYears(entity1.getYears().toString());
                        this.setMatDate(entity1.getMatDate().toString());
                        this.setRoi(entity1.getrRoi().toString());
                        this.setIntOpt(entity1.getrIntOpt().toString());
                        this.setFaceValue(entity1.getrFaceValue().toString());
                        this.setInterest(entity1.getrInterest().toString());
                        this.setBookvalue(entity1.getrMaturityValue().toString());
                        
                        this.setIntAccGlHead(entity1.getIntAccrGlHead().toString());
                        Gltable entityIntAccCr = remoteInterface.getGltableByAcno(entity1.getIntAccrGlHead().toString());
                        if (entityIntAccCr != null) {
                            this.setIntAccGlHeadDesc(entityIntAccCr.getAcName());
                        }else{
                            this.setIntAccGlHeadDesc("");
                        }
                        this.setIntAccGlHeadVal(entity1.getIntAccrValue().toString());
                        
                        
                        this.setIntRecCrGlHead(entity1.getIntRecCrGlHead().toString());
                        Gltable entityIntRecCr = remoteInterface.getGltableByAcno(entity1.getIntRecCrGlHead().toString());
                        if (entityIntRecCr != null) {
                            this.setIntRecCrGlHeadDesc(entityIntRecCr.getAcName());
                        }else{
                            this.setIntRecCrGlHeadDesc("");
                        }
                        this.setIntRecCrGlHeadVal(entity1.getIntRecCrValue().toString());
                        
                        
                        this.setIntCrGlHead(entity1.getIntDrGlHead().toString());
                        Gltable entityIntCr = remoteInterface.getGltableByAcno(entity1.getIntDrGlHead().toString());
                        if (entityIntCr != null) {
                            this.setIntCrGlHeadDesc(entityIntCr.getAcName());
                        }else{
                            this.setIntCrGlHeadDesc("");
                        }
                        this.setIntCrGlHeadVal(entity1.getIntDrValue().toString());
                        
                        
                        
                        this.setFaceValCrGlHead(entity1.getFaceValueCrGlHead().toString());
                        Gltable entityFaceCr = remoteInterface.getGltableByAcno(entity1.getFaceValueCrGlHead().toString());
                        if (entityFaceCr != null) {
                            this.setFaceValCrGlHeadDesc(entityFaceCr.getAcName());
                        }else{
                            this.setFaceValCrGlHeadDesc("");
                        }
                        this.setFaceValCrGlHeadVal(entity1.getFaceValueCrValue().toString());
                    
                        
                        this.setBookValDrGlHead(entity1.getMatDrGlHead().toString());
                        Gltable entityBookCr = remoteInterface.getGltableByAcno(entity1.getMatDrGlHead().toString());
                        if (entityBookCr != null) {
                            this.setBookValDrGlHeadDesc(entityBookCr.getAcName());
                        }else{
                            this.setBookValDrGlHeadDesc("");
                        }
                        this.setBookValDrGlHeadVal(entity1.getMatDrValue().toString());
                        
                        pEnterBy = entity1.getEnterBy().toString();
                        
                    }
                }
                
                this.setDisRadioClose(true);
                this.setDisClose(true);
                this.setDisRenPurDt(true);
                this.setDisPurDt(true);
                this.setDisDays(true);
                this.setDisMonths(true);
                this.setDisYears(true);
                this.setDisMatDt(true);
                this.setDisRoi(true);
                this.setDisIntOpt(true);
                this.setDisFaceValue(true);
                this.setDisInt(true);
                this.setDisBookValue(true);
                this.setDisIntAccGlHead(true);
                this.setDisIntRecGlHead(true);
                this.setDisRecCrGlHeadVal(true);
                this.setDisCrGlHead(true);
                this.setDisFCrGlHead(true);
                this.setDisBookGlHead(true);
            }
        } catch (ApplicationException ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("There is no data for control number: " + this.getCtrl());
            } else {
                this.setMessage(ex.getMessage());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void disableType() {
        if (this.getRenClose().equalsIgnoreCase("C")) {
            this.disPurDt = true;
            this.disDays = true;
            this.disMonths = true;
            this.disYears = true;
            this.disMatDt = true;
            this.disRoi = true;
            this.disIntOpt = true;
            this.disFaceValue = true;
            this.disInt = true;
            this.disBookValue = true;
            this.disClose = false;
            this.disRenPurDt = true;
            this.setClose("A");
            this.setPurchaseDate("");
            this.setMatDate("");
            this.setRoi("0");
            this.setFaceValue("0");
            this.setInterest("0");
            this.setBookvalue("0");
            try {
                if (sdf.parse(this.getTodayDate()).compareTo(sdf.parse(this.getsMatDate())) < 0) {
                    this.setFlag1("true");
                    Double dblInterest = 0.0;
                    this.setRenCloseVal("Premature");
                    this.disRecCrGlHeadVal = false;
                    this.setMessage("You are trying to close this FD before Mat. Date, So Book value and Int amount get changed");
                    String pDt = this.getsPurDate().substring(6, 10) + this.getsPurDate().substring(3, 5) + this.getsPurDate().substring(0, 2);
                    String mDt = this.getTodayDate().substring(6, 10) + this.getTodayDate().substring(3, 5) + this.getTodayDate().substring(0, 2);
                    String gPrd = Period();
                    dblInterest = Double.parseDouble(remoteTdInterface.orgFDInterest(this.getsIntOpt(), Float.parseFloat(this.getsRoi()), pDt, mDt, Double.parseDouble(this.getsFaceValue()), gPrd, this.getOrgnBrCode()));
                    this.setIntRecCrGlHeadVal(formatter.format(dblInterest));
                    this.setIntCrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.getIntRecCrGlHeadVal()) + Double.parseDouble(this.getIntAccGlHeadVal()))));
                    if(getIntOpt().equalsIgnoreCase("S")){
                      this.setFaceValCrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.sFaceValue))));
                    this.setBookValDrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.sFaceValue))));                    
                        
                    }else{
                    this.setFaceValCrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.sFaceValue) + Double.parseDouble(this.getIntRecCrGlHeadVal()) + Double.parseDouble(this.getIntAccGlHeadVal()))));
                    this.setBookValDrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.sFaceValue) + Double.parseDouble(this.getIntRecCrGlHeadVal()) + Double.parseDouble(this.getIntAccGlHeadVal()))));                    
                    }
                    } else {
                    this.disRecCrGlHeadVal = false;
                    this.setRenCloseVal("Mature");
                }
            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        } else {
            this.setPurchaseDate(this.getsMatDate());
            this.setIntOpt(this.getsIntOpt());
            this.setFaceValue(this.getsBookvalue());
            this.setRenCloseVal("");
            this.setClose("");
            this.disClose = true;
            this.disRenPurDt = true;
            this.disPurDt = false;
            this.disDays = false;
            this.disMonths = false;
            this.disYears = false;
            this.disMatDt = true;
            this.disRoi = false;
            this.disIntOpt = false;
            this.disFaceValue = false;
            this.disInt = false;
            this.disBookValue = false;
        }
    }

    public void intOnBlurAction() {
        if (!(this.getInterest().equalsIgnoreCase("") || this.getInterest() == null || this.getInterest().equalsIgnoreCase("null"))) {
            this.setBookvalue(String.valueOf(formatter.format(Double.parseDouble(this.getInterest()) + Double.parseDouble(this.getFaceValue()))));
        }
    }

    public void onBlurMatDate() {
        this.setMessage("");
        if (this.days == null && this.months == null && this.years == null) {
            this.setMessage("Please fill period !");
            return;
        }

        if (this.days.equals("") && this.months.equals("") && this.years.equals("")) {
            this.setMessage("Please fill period !");
            return;
        }

        try {
            long totalDaysInYear = 0, totalDaysInMonth = 0, totalDaysByUser = 0, totalDays = 0;
            if (this.years != null && !this.years.equals("") && !this.years.equals("0") && !this.years.equals("0.0")) {
                String addedYear = CbsUtil.yearAdd(ymd.format(sdf.parse(this.getPurchaseDate())), Integer.parseInt(this.years));
                totalDaysInYear = CbsUtil.dayDiff(sdf.parse(this.getPurchaseDate()), ymd.parse(addedYear));
            }

            if (this.months != null && !this.months.equals("") && !this.months.equals("0") && !this.months.equals("0.0")) {
                String addedMonth = CbsUtil.monthAdd(ymd.format(sdf.parse(this.getPurchaseDate())), Integer.parseInt(this.months));
                totalDaysInMonth = CbsUtil.dayDiff(sdf.parse(this.getPurchaseDate()), ymd.parse(addedMonth));
            }

            if (this.days == null || this.days.equals("")) {
                totalDaysByUser = 0;
            } else {
                totalDaysByUser = Long.parseLong(this.days);
            }
            totalDays = totalDaysInYear + totalDaysInMonth + totalDaysByUser;
            String matureDate = CbsUtil.dateAdd(ymd.format(sdf.parse(this.getPurchaseDate())), (int) totalDays);
            this.setMatDate(sdf.format(ymd.parse(matureDate)));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurIntOp() {
        try {
            Double dblInterest = 0.0;
            String pDt = this.getPurchaseDate().substring(6, 10) + this.getPurchaseDate().substring(3, 5) + this.getPurchaseDate().substring(0, 2);
            String mDt = this.getMatDate().substring(6, 10) + this.getMatDate().substring(3, 5) + this.getMatDate().substring(0, 2);
            String gPrd = Period();
            dblInterest = Double.parseDouble(remoteTdInterface.orgFDInterest(this.getIntOpt(), Float.parseFloat(this.getRoi()), pDt, mDt, Double.parseDouble(this.getFaceValue()), gPrd, this.getOrgnBrCode()));
            this.setInterest(String.valueOf(dblInterest));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String Period() {
        String period = "";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.getYears() != null || !this.getYears().equalsIgnoreCase("")) {
            Matcher yearCheck = p.matcher(this.getYears());
            if (!yearCheck.matches()) {
                setMessage("Please Enter Valid Years.");
                return "false";
            }
            if (!this.getYears().matches("[0-9]*")) {
                this.setYears("");
                setMessage("Please Enter Valid Year.");
                return "false";
            }
        }
        if (months != null || !months.equalsIgnoreCase("")) {
            Matcher monthsCheck = p.matcher(months);
            if (!monthsCheck.matches()) {
                setMessage("Please Enter Valid Months.");
                return "false";
            }
            if (!months.matches("[0-9]*")) {
                setMonths("");
                setMessage("Please Enter Valid Months.");
                return "false";
            }
        }
        if (days != null || !days.equalsIgnoreCase("")) {
            Matcher daysCheck = p.matcher(days);
            if (!daysCheck.matches()) {
                setMessage("Please Enter Valid Days.");
                return "false";
            }
            if (!days.matches("[0-9]*")) {
                setDays("");
                setMessage("Please Enter Valid Days.");
                return "false";
            }
        }

        if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (years.equalsIgnoreCase("0") || years.equalsIgnoreCase("")) && (months.equalsIgnoreCase("0") || months.equalsIgnoreCase(""))) {
            months = "0";
            days = "0";
            years = "0";
            period = "0";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (months.equalsIgnoreCase("0") || months.equalsIgnoreCase("")) && (years.equalsIgnoreCase("0") || years.equalsIgnoreCase(""))) {
            months = "0";
            years = "0";
            period = days + "Days";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (!months.equalsIgnoreCase("0") || !months.equalsIgnoreCase("")) && (years.equalsIgnoreCase("0") || years.equalsIgnoreCase(""))) {
            days = "0";
            years = "0";
            period = months + "Months";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (months.equalsIgnoreCase("0") || months.equalsIgnoreCase("")) && (!years.equalsIgnoreCase("0") || years.equalsIgnoreCase(""))) {
            months = "0";
            days = "0";
            period = years + "Years";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (!years.equalsIgnoreCase("0") || !years.equalsIgnoreCase("")) && (months.equalsIgnoreCase("0") || months.equalsIgnoreCase(""))) {
            months = "0";
            period = years + "Years" + days + "Days";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (years.equalsIgnoreCase("0") || years.equalsIgnoreCase("")) && (!months.equalsIgnoreCase("0") || !months.equalsIgnoreCase(""))) {
            years = "0";
            period = months + "Months" + days + "Days";
        } else if ((days.equalsIgnoreCase("0") || days.equalsIgnoreCase("")) && (!years.equalsIgnoreCase("0") || !years.equalsIgnoreCase("")) && (!months.equalsIgnoreCase("0") || !months.equalsIgnoreCase(""))) {
            days = "0";
            period = years + "Years" + months + "Months";
        } else if ((!days.equalsIgnoreCase("0") || !days.equalsIgnoreCase("")) && (!years.equalsIgnoreCase("0") || !years.equalsIgnoreCase("")) && (!months.equalsIgnoreCase("0") || !months.equalsIgnoreCase(""))) {
            period = years + "Years" + months + "Months" + days + "Days";
        }
        return period;
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

        public void intOnBlurCrGlAction() {            if (!(this.getIntCrGlHead().equalsIgnoreCase("") || this.getIntCrGlHead() == null || this.getIntCrGlHead().equalsIgnoreCase("null"))) {
            if (this.getIntCrGlHead().length() != 12) {
                this.setMessage("Please Enter 12 Digit Account Number.");
            } else {
                String val = acDescVal(this.getIntCrGlHead());
                if (val.equalsIgnoreCase("")) {
                    this.setMessage("Gl Head Does Not Exists");
                } else {
                    this.setIntCrGlHeadDesc(val);
                }
            }
        }
    }

    public void intOnBlurRecCrGlAction() {
        if (!(this.getIntRecCrGlHead().equalsIgnoreCase("") || this.getIntRecCrGlHead() == null || this.getIntRecCrGlHead().equalsIgnoreCase("null"))) {
                if (this.getIntRecCrGlHead().length() != 12) {
                this.setMessage("Please Enter 12 Digit Account Number.");
            } else {
                String val = acDescVal(this.getIntRecCrGlHead());
                if (val.equalsIgnoreCase("")) {
                    this.setMessage("Gl Head Does Not Exists");
                } else {
                    this.setIntRecCrGlHeadDesc(val);
                }
            }
        }
    }

    public void intOnBlurFaceValCrGlAction() {
        if (!(this.getFaceValCrGlHead().equalsIgnoreCase("") || this.getFaceValCrGlHead() == null || this.getFaceValCrGlHead().equalsIgnoreCase("null"))) {
            if (this.getFaceValCrGlHead().length() != 12) {
                this.setMessage("Please Enter 12 Digit Account Number.");
            } else {
                String val = acDescVal(this.getFaceValCrGlHead());
                if (val.equalsIgnoreCase("")) {
                    this.setMessage("Gl Head Does Not Exists");
                } else {
                    this.setFaceValCrGlHeadDesc(val);
                }
            }
        }
    }

    public void intOnBlurBookValDrGlAction() {
        if (!(this.getBookValDrGlHead().equalsIgnoreCase("") || this.getBookValDrGlHead() == null || this.getBookValDrGlHead().equalsIgnoreCase("null"))) {
            if (this.getBookValDrGlHead().length() != 12) {
                this.setMessage("Please Enter 12 Digit Account Number.");
            } else {
                String val = acDescVal(this.getBookValDrGlHead());
                if (val.equalsIgnoreCase("")) {
                    this.setMessage("Gl Head Does Not Exists");
                } else {
                    this.setBookValDrGlHeadDesc(val);
                }
            }
        }
    }

    public void ClearAll() {
        this.setFdrNo("");
        this.setsPurDate("");
        this.setsMatDate("");
        this.setsFaceValue("");
        this.setsBookvalue("");
        this.setSellerNmVal("");
        this.setsRoi("");
        this.setsIntOpt("");
        this.setsBranch("");
        this.setRenClose("");
        this.setClose("");
        this.setRenPurDt(sdf.format(dt));
        this.setPurchaseDate("");
        this.setDays("0");
        this.setMonths("0");
        this.setYears("0");
        this.setMatDate("");
        this.setRoi("");
        this.setIntOpt("S");
        this.setFaceValue("");
        this.setInterest("");
        this.setBookvalue("");
        this.setIntAccGlHead("");
        this.setIntAccGlHeadDesc("");
        this.setIntAccGlHeadVal("");
        this.setIntCrGlHead("");
        this.setIntCrGlHeadDesc("");
        this.setIntCrGlHeadVal("");
        this.setIntRecCrGlHead("");
        this.setIntRecCrGlHeadDesc("");
        this.setIntRecCrGlHeadVal("");
        this.setFaceValCrGlHead("");
        this.setFaceValCrGlHeadDesc("");
        this.setFaceValCrGlHeadVal("");
        this.setBookValDrGlHead("");
        this.setBookValDrGlHeadDesc("");
        this.setBookValDrGlHeadVal("");
        this.disRecCrGlHeadVal = false;
        this.disFunction = false;
        this.disCtrl = false;        
        this.setDisRadioClose(false);
        this.setDisClose(false);
        this.setDisRenPurDt(false);
        this.setDisPurDt(false);
        this.setDisDays(false);
        this.setDisMonths(false);
        this.setDisYears(false);
        this.setDisMatDt(false);
        this.setDisRoi(false);
        this.setDisIntOpt(false);
        this.setDisFaceValue(false);
        this.setDisInt(false);
        this.setDisBookValue(false);
        this.setDisIntAccGlHead(false);
        this.setDisIntRecGlHead(false);
        this.setDisRecCrGlHeadVal(false);
        this.setDisCrGlHead(false);
        this.setDisFCrGlHead(false);
        this.setDisBookGlHead(false);
        this.setFunction("A");
        this.setCtrl("");
        ctrlList = new ArrayList<SelectItem>();
    }

    public String exitButton() {
        ClearAll();
        this.setMessage("");
        return "case1";
    } 
    
    public void saveTxn(){
        this.setMessage("");
        try {
            if (this.getIntAccGlHead().equalsIgnoreCase("") || this.getIntAccGlHead() == null || this.getIntAccGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Int. Accrued Gl Head Does Not Exists");
                return;                
            }
            if(this.getIntAccGlHead().length()!=12){
                this.setMessage("Please Enter 12 Digit Int. Accrued Gl Head.");
                return;
            }
            
            if(!this.getIntAccGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Int. Accrued Gl Head Of your Branch.");
                return;
            }
            
            if (this.getIntRecCrGlHead().equalsIgnoreCase("") || this.getIntRecCrGlHead() == null || this.getIntRecCrGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Int.Rec.(Balance) Credit Gl Head Does Not Exists");
                return;                
            }
            if(this.getIntRecCrGlHead().length()!=12){
                this.setMessage("Please Enter 12 Digit Int.Rec.(Balance) Credit Gl Head");
                return;
            }
            
            if(!this.getIntRecCrGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Int.Rec.(Balance) Credit Gl Head Of your Branch.");
                return;
            }
            
            
            if (this.getIntCrGlHead().equalsIgnoreCase("") || this.getIntCrGlHead() == null || this.getIntCrGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Int. Debit Gl Head Does Not Exists");
                return;                
            }
            if(this.getIntCrGlHead().length()!=12){
                this.setMessage("Please Enter 12 Int. Debit Gl Head");
                return;
            }
            
            if(!this.getIntCrGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Int. Debit Gl Head Of your Branch.");
                return;
            }
            
            if (this.getFaceValCrGlHead().equalsIgnoreCase("") || this.getFaceValCrGlHead() == null || this.getFaceValCrGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Face Value Credit Gl Head Does Not Exists");
                return;                
            }
            if(this.getFaceValCrGlHead().length()!=12){
                this.setMessage("Please Enter 12 Face Value Credit Gl Head");
                return;
            }
            
            if(!this.getFaceValCrGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Face Value Credit Gl Head Of your Branch.");
                return;
            }
            
            if (this.getBookValDrGlHead().equalsIgnoreCase("") || this.getBookValDrGlHead() == null || this.getBookValDrGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Gl Head Does Not Exists");
                return;                
            }
            if(this.getBookValDrGlHead().length()!=12){
                this.setMessage("Please Enter 12 Digit Account Number.");
                return;
            }
            
            if(!this.getBookValDrGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Maturity Value Debit Gl Head Of your Branch.");
                return;
            }
       
             if(this.getsFaceValue().equalsIgnoreCase("") || this.getsFaceValue() == null || this.getsFaceValue().equalsIgnoreCase("null") 
                    || (Double.parseDouble(this.getsFaceValue())==0)){
                this.setMessage("Invalid Face Value, Press Tab After Selecting the Control No.");
                return;
            }
            
            if(this.getsBookvalue().equalsIgnoreCase("") || this.getsBookvalue() == null || this.getsBookvalue().equalsIgnoreCase("null") 
                    || (Double.parseDouble(this.getsBookvalue())==0)){
                this.setMessage("Invalid Book Value, Press Tab After Selecting the Control No.");
                return;
            }
            
            if(this.getRenClose() == null || this.getRenClose().equalsIgnoreCase("") || this.getRenClose().equalsIgnoreCase("null")){
                this.setMessage("Please Choose One Option From Closing or Renewal");
                return;
            }
            
            if(this.getRenClose().equalsIgnoreCase("C")){
                if(this.getClose() == null || this.getClose().equalsIgnoreCase("") || this.getClose().equalsIgnoreCase("null")){
                    this.setMessage("Please Choose a Mode of Closing");
                    return;
                }
            }else{
                if(this.getDays().equalsIgnoreCase("") || this.getDays() == null || this.getDays().equalsIgnoreCase("null") 
                    || (Integer.parseInt(this.getDays())==0)){
                    if(this.getMonths().equalsIgnoreCase("") || this.getMonths() == null || this.getMonths().equalsIgnoreCase("null") 
                    || (Integer.parseInt(this.getMonths())==0)){
                        if(this.getYears().equalsIgnoreCase("") || this.getYears() == null || this.getYears().equalsIgnoreCase("null") 
                    || (Integer.parseInt(this.getYears())==0)){
                           this.setMessage("Fill The TD Period");
                           return; 
                        }                            
                    }
                }
            }
            
            if(this.getIntRecCrGlHeadVal() == null || this.getIntRecCrGlHeadVal().equalsIgnoreCase("") || this.getIntRecCrGlHeadVal().equalsIgnoreCase("null")){
                this.setMessage("Int.Rec.(Balance) Credit Gl Head Value Can't Be Blank");
                return; 
            }
            
            if(this.getRenClose().equalsIgnoreCase("R")){
                if(this.getRoi().equalsIgnoreCase("") || this.getRoi() == null || this.getRoi().equalsIgnoreCase("null") 
                    || (Float.parseFloat(this.getRoi())==0)){
                        this.setMessage("Fill The ROI");
                        return; 
                }
                
                if(this.getFaceValue().equalsIgnoreCase("") || this.getFaceValue() == null || this.getFaceValue().equalsIgnoreCase("null") 
                    || (Float.parseFloat(this.getFaceValue())==0)){
                        this.setMessage("Fill The Face Value");
                        return; 
                }
                
                if (sdf.parse(this.getPurchaseDate()).after(dt)) {
                    this.setMessage("Purchase date should be less or Equal to Current date");
                    return;
                }
            }          
            
//            String msg = remoteInterface.fdrCloseProcess(Integer.parseInt(this.getCtrl()),this.getRenClose(),this.getClose(),
//                    this.getPurchaseDate(),this.getDays() != null ? Integer.parseInt(this.getDays()) : 0,
//                    this.getMonths() != null ? Integer.parseInt(this.getMonths()) : 0,this.getYears() != null ? Integer.parseInt(this.getYears()) : 0,
//                    this.getMatDate(),Float.parseFloat(this.getRoi()),this.getIntOpt() != null ? this.getIntOpt() : "",Double.parseDouble(this.getFaceValue()),
//                    Double.parseDouble(this.getBookvalue()),this.getIntCrGlHead(),Double.parseDouble(this.getIntCrGlHeadVal()),this.getIntRecCrGlHead(),
//                    Double.parseDouble(this.getIntRecCrGlHeadVal()),this.getFaceValCrGlHead(),Double.parseDouble(this.getFaceValCrGlHeadVal()),
//                    this.getBookValDrGlHead(),Double.parseDouble(this.getBookValDrGlHeadVal()),this.getFlag1(),this.getUserName(),this.getOrgnBrCode(),
//                    this.getSellerNmVal(),this.getsMatDate() != null ? this.getsMatDate() : "", this.getIntAccGlHead(), Double.parseDouble(this.getIntAccGlHeadVal()));
//            
//            if(this.getRenClose().equalsIgnoreCase("R")){
//                if(msg.substring(0,4).equalsIgnoreCase("true")){
//                    this.setMessage("Generated Control No. is "+ msg.substring(4));
//                }else{
//                    this.setMessage("An Error Occured,Action can not be completed");
//                }
//            }else{
//                if(msg.equalsIgnoreCase("true")){
//                    this.setMessage("FDR Closed Successfully");
//                }else{
//                    this.setMessage("An Error Occured,Action can not be completed");
//                }
//            }
            String msg = remoteInterface.saveFDRCloseRenewAuthDetail(this.getCtrl(),this.getFdrNo(),this.getsPurDate(), this.getsMatDate(), 
                    this.getsFaceValue(), this.getsBookvalue(), this.getSellerNmVal(), this.getsRoi(), this.getsIntOpt(), this.getsBranch(), this.getRenClose(),
                    this.getClose(),this.getRenPurDt(), this.getPurchaseDate(),this.getDays() != null ? this.getDays() : "0",
                    this.getMonths()!= null ? this.getMonths(): "0", this.getYears()!= null ? this.getYears(): "0",this.getMatDate(), this.getRoi(),
                    this.getIntOpt() != null ? this.getIntOpt() : "",this.getFaceValue(),this.getInterest(), this.getBookvalue(), this.getIntAccGlHead(), this.getIntAccGlHeadVal(),
                    this.getIntRecCrGlHead(), this.getIntRecCrGlHeadVal(), this.getIntCrGlHead(), this.getIntCrGlHeadVal(), this.getFaceValCrGlHead(),
                    this.getFaceValCrGlHeadVal(), this.getBookValDrGlHead(), this.getBookValDrGlHeadVal(), this.getUserName(), getOrgnBrCode());
            if (msg.equalsIgnoreCase("true")) {
                ClearAll();
                this.setMessage("Data has been saved successfully");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void authTxn(){
        this.setMessage("");
        try {
            if (this.getIntAccGlHead().equalsIgnoreCase("") || this.getIntAccGlHead() == null || this.getIntAccGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Int. Accrued Gl Head Does Not Exists");
                return;                
            }
            if(this.getIntAccGlHead().length()!=12){
                this.setMessage("Please Enter 12 Digit Int. Accrued Gl Head.");
                return;
            }
            
            if(!this.getIntAccGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Int. Accrued Gl Head Of your Branch.");
                return;
            }
            
            if (this.getIntRecCrGlHead().equalsIgnoreCase("") || this.getIntRecCrGlHead() == null || this.getIntRecCrGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Int.Rec.(Balance) Credit Gl Head Does Not Exists");
                return;                
            }
            if(this.getIntRecCrGlHead().length()!=12){
                this.setMessage("Please Enter 12 Digit Int.Rec.(Balance) Credit Gl Head");
                return;
            }
            
            if(!this.getIntRecCrGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Int.Rec.(Balance) Credit Gl Head Of your Branch.");
                return;
            }
            
            
            if (this.getIntCrGlHead().equalsIgnoreCase("") || this.getIntCrGlHead() == null || this.getIntCrGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Int. Debit Gl Head Does Not Exists");
                return;                
            }
            if(this.getIntCrGlHead().length()!=12){
                this.setMessage("Please Enter 12 Int. Debit Gl Head");
                return;
            }
            
            if(!this.getIntCrGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Int. Debit Gl Head Of your Branch.");
                return;
            }
            
            if (this.getFaceValCrGlHead().equalsIgnoreCase("") || this.getFaceValCrGlHead() == null || this.getFaceValCrGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Face Value Credit Gl Head Does Not Exists");
                return;                
            }
            if(this.getFaceValCrGlHead().length()!=12){
                this.setMessage("Please Enter 12 Face Value Credit Gl Head");
                return;
            }
            
            if(!this.getFaceValCrGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Face Value Credit Gl Head Of your Branch.");
                return;
            }
            
            if (this.getBookValDrGlHead().equalsIgnoreCase("") || this.getBookValDrGlHead() == null || this.getBookValDrGlHead().equalsIgnoreCase("null")) {
                this.setMessage("Gl Head Does Not Exists");
                return;                
            }
            if(this.getBookValDrGlHead().length()!=12){
                this.setMessage("Please Enter 12 Digit Account Number.");
                return;
            }
            
            if(!this.getBookValDrGlHead().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Maturity Value Debit Gl Head Of your Branch.");
                return;
            }
       
             if(this.getsFaceValue().equalsIgnoreCase("") || this.getsFaceValue() == null || this.getsFaceValue().equalsIgnoreCase("null") 
                    || (Double.parseDouble(this.getsFaceValue())==0)){
                this.setMessage("Invalid Face Value, Press Tab After Selecting the Control No.");
                return;
            }
            
            if(this.getsBookvalue().equalsIgnoreCase("") || this.getsBookvalue() == null || this.getsBookvalue().equalsIgnoreCase("null") 
                    || (Double.parseDouble(this.getsBookvalue())==0)){
                this.setMessage("Invalid Book Value, Press Tab After Selecting the Control No.");
                return;
            }
            
            if(this.getRenClose().equalsIgnoreCase("") || this.getRenClose() == null || this.getRenClose().equalsIgnoreCase("null")){
                this.setMessage("Please Choose One Option From Closing or Renewal");
                return;
            }
            
            if(this.getRenClose().equalsIgnoreCase("C")){
                if(this.getClose().equalsIgnoreCase("") || this.getClose() == null || this.getClose().equalsIgnoreCase("null")){
                    this.setMessage("Please Choose a Mode of Closing");
                    return;
                }
            }else{
                if(this.getDays().equalsIgnoreCase("") || this.getDays() == null || this.getDays().equalsIgnoreCase("null") 
                    || (Integer.parseInt(this.getDays())==0)){
                    if(this.getMonths().equalsIgnoreCase("") || this.getMonths() == null || this.getMonths().equalsIgnoreCase("null") 
                    || (Integer.parseInt(this.getMonths())==0)){
                        if(this.getYears().equalsIgnoreCase("") || this.getYears() == null || this.getYears().equalsIgnoreCase("null") 
                    || (Integer.parseInt(this.getYears())==0)){
                           this.setMessage("Fill The TD Period");
                           return; 
                        }                            
                    }
                }
            }
            
            if(this.getRenClose().equalsIgnoreCase("R")){
                if(this.getRoi().equalsIgnoreCase("") || this.getRoi() == null || this.getRoi().equalsIgnoreCase("null") 
                    || (Float.parseFloat(this.getRoi())==0)){
                        this.setMessage("Fill The ROI");
                        return; 
                }
                
                if(this.getFaceValue().equalsIgnoreCase("") || this.getFaceValue() == null || this.getFaceValue().equalsIgnoreCase("null") 
                    || (Float.parseFloat(this.getFaceValue())==0)){
                        this.setMessage("Fill The Face Value");
                        return; 
                }
                
                if (sdf.parse(this.getPurchaseDate()).after(dt)) {
                    this.setMessage("Purchase date should be less or Equal to Current date");
                    return;
                }
            } 
            
            if (pEnterBy.equalsIgnoreCase(getUserName())) {
                this.setMessage("You Can't Authorize Your Own Entry.");
                return;
            } else {
                String msg = remoteInterface.fdrCloseProcess(Integer.parseInt(this.getCtrl()),this.getRenClose(),this.getClose(),
                    this.getPurchaseDate(),this.getDays() != null ? Integer.parseInt(this.getDays()) : 0,
                    this.getMonths() != null ? Integer.parseInt(this.getMonths()) : 0,this.getYears() != null ? Integer.parseInt(this.getYears()) : 0,
                    this.getMatDate(),Float.parseFloat(this.getRoi()),this.getIntOpt() != null ? this.getIntOpt() : "",Double.parseDouble(this.getFaceValue()),
                    Double.parseDouble(this.getBookvalue()),this.getIntCrGlHead(),Double.parseDouble(this.getIntCrGlHeadVal()),this.getIntRecCrGlHead(),
                    Double.parseDouble(this.getIntRecCrGlHeadVal()),this.getFaceValCrGlHead(),Double.parseDouble(this.getFaceValCrGlHeadVal()),
                    this.getBookValDrGlHead(),Double.parseDouble(this.getBookValDrGlHeadVal()),this.getFlag1(),pEnterBy,this.getOrgnBrCode(),
                    this.getSellerNmVal(),this.getsMatDate() != null ? this.getsMatDate() : "", this.getIntAccGlHead(), Double.parseDouble(this.getIntAccGlHeadVal()), this.getUserName());
            
                if(this.getRenClose().equalsIgnoreCase("R")){
                    if(msg.substring(0,4).equalsIgnoreCase("true")){
                        ClearAll();
                        this.setMessage("Generated Control No. is "+ msg.substring(4));
                    }else{
                        this.setMessage("An Error Occured,Action can not be completed");
                    }
                }else{
                    if(msg.equalsIgnoreCase("true")){
                        ClearAll();
                        this.setMessage("FDR Closed Successfully");
                    }else{
                        this.setMessage("An Error Occured,Action can not be completed");
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void DeleteTxn(){
        try {
            if (function == null || (function.equalsIgnoreCase("")) || function.equalsIgnoreCase("null")) {
                this.setMessage("Please Select The Function");
                return;
            }
            
            if (function.equals("A")) {
                this.setMessage("You can't delete in ADD Mode");
                return;
            }
            
            if (ctrl == null || (ctrl.equalsIgnoreCase("")) || ctrl.equalsIgnoreCase("null")) {
                this.setMessage("Please Select The Control No");
                return;
            }

            String msg = remoteInterface.deleteFdrCloseRenewAuth(this.getCtrl());
            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                ClearAll();
                String msg1 = "Data has been Deleted successfully";
                this.setMessage(msg1);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void intOnBlurRecCrGlHeadVal() {
                    this.setIntRecCrGlHeadVal(String.valueOf(Double.parseDouble(this.getIntRecCrGlHeadVal())));
//        if(this.getsIntOpt().equalsIgnoreCase("S")){
//            this.setIntCrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.getIntRecCrGlHeadVal()) + Double.parseDouble(this.getIntAccGlHeadVal()))));            
//        }else{
                   this.setIntCrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.getIntRecCrGlHeadVal()) + Double.parseDouble(this.getIntAccGlHeadVal()))));
           
            if(this.getsIntOpt().equalsIgnoreCase("S")){
              this.setFaceValCrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.sFaceValue))));    
              this.setBookValDrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.sFaceValue))));
            }else{
            this.setFaceValCrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.sFaceValue) + Double.parseDouble(this.getIntRecCrGlHeadVal()) + Double.parseDouble(this.getIntAccGlHeadVal()))));
            this.setBookValDrGlHeadVal(String.valueOf(formatter.format(Double.parseDouble(this.sFaceValue) + Double.parseDouble(this.getIntRecCrGlHeadVal()) + Double.parseDouble(this.getIntAccGlHeadVal()))));
            }
            
      // }        
    }
    
    public void intOnBlurAccGlAction() {
            if (!(this.getIntAccGlHead().equalsIgnoreCase("") || this.getIntAccGlHead() == null || this.getIntAccGlHead().equalsIgnoreCase("null"))) {
            if (this.getIntAccGlHead().length() != 12) {
                this.setMessage("Please Enter 12 Digit Account Number.");
            } else {
                String val = acDescVal(this.getIntAccGlHead());
                if (val.equalsIgnoreCase("")) {
                    this.setMessage("Gl Head Does Not Exists");
                } else {
                    this.setIntAccGlHeadDesc(val);
                }
            }
        }
    }
}