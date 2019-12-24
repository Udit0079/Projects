/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.DtlRegisterPojo;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.ho.investment.InvestmentSecurityAuthDetail;
import com.cbs.entity.ho.investment.InvestmentSecurityMaster;
import com.cbs.entity.master.BranchMaster;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.investment.GoiAccrutinterestTable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sipl
 */
public class SecurityMaster extends BaseBean {

    private String message;
    private String secTpDd;
    private List<SelectItem> secTypeList;
    private String secDtlVal;
    private String sellerNmVal;
    Date matDate = null;
    private String issueFrDd;
    private List<SelectItem> issueFrmList;
    private String codeNoVal;
    private String prnIssueDate;
    private String purchaseDate;
    Date transactionDate = null;
    Date settlementDate = null;
    private String markingDd;
    private List<SelectItem> markList;
    private String fInttPayDate;
    private String sInttPayDate;
    private String roiVal;
    private String conAcNoVal;
    private String issuePrVal;
    private String priceVal;
    private String faceValue;
    private String bookValue;
    private String accrIntVal;
    private String brokerAcVal;
    private String brokerageAmtVal;
    private String crGlVal;
    private String crBranchDd;
    private String conSgAccountDd;
    private List<SelectItem> conSgAcList;
    private int noOfShrVal;
    private List<SelectItem> crBrnList;
    private String secDetailDd;
    private List<SelectItem> secDtlList;
    private String shareFlag;
    private String secDtlFlag;
    private String secTxtFlag;
    private String matFlag;
    private String setFlag;
    private String trnFlag;
    private String issDtFlag;
    private String markFlag;
    private String fstFlag;
    private String secIFlag;
    private String conAcFlag;
    private String issPFlag;
    private String prFlag;
    private String bookFlag;
    private String sglFlag;
    private String conFlag;
    private String crGlFlag;
    private String crBrFlag;
    private String selFlag;
    private String particularVal;
    private String crHeadName;
    private String ytmVal;
    private Date dt = new Date();
    int getCode;
    private InvestmentMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsObj = null;
    private final String ftsJndiHomeName = "FtsPostingMgmtFacade";
    
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String invRepjndiHomeName = "InvestmentReportMgmtFacade";
    
    private HoReportFacadeRemote hoRep = null;
    private final String hoRepJndiHomeName = "HoReportFacade";
    
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getAccrIntVal() {
        return accrIntVal;
    }

    public void setAccrIntVal(String accrIntVal) {
        this.accrIntVal = accrIntVal;
    }

    public String getBrokerAcVal() {
        return brokerAcVal;
    }

    public void setBrokerAcVal(String brokerAcVal) {
        this.brokerAcVal = brokerAcVal;
    }

    public String getBrokerageAmtVal() {
        return brokerageAmtVal;
    }

    public void setBrokerageAmtVal(String brokerageAmtVal) {
        this.brokerageAmtVal = brokerageAmtVal;
    }

    public String getCodeNoVal() {
        return codeNoVal;
    }

    public void setCodeNoVal(String codeNoVal) {
        this.codeNoVal = codeNoVal;
    }

    public String getConAcNoVal() {
        return conAcNoVal;
    }

    public void setConAcNoVal(String conAcNoVal) {
        this.conAcNoVal = conAcNoVal;
    }

    public String getCrBranchDd() {
        return crBranchDd;
    }

    public void setCrBranchDd(String crBranchDd) {
        this.crBranchDd = crBranchDd;
    }

    public List<SelectItem> getCrBrnList() {
        return crBrnList;
    }

    public void setCrBrnList(List<SelectItem> crBrnList) {
        this.crBrnList = crBrnList;
    }

    public String getCrGlVal() {
        return crGlVal;
    }

    public void setCrGlVal(String crGlVal) {
        this.crGlVal = crGlVal;
    }

    public String getIssueFrDd() {
        return issueFrDd;
    }

    public void setIssueFrDd(String issueFrDd) {
        this.issueFrDd = issueFrDd;
    }

    public List<SelectItem> getIssueFrmList() {
        return issueFrmList;
    }

    public void setIssueFrmList(List<SelectItem> issueFrmList) {
        this.issueFrmList = issueFrmList;
    }

    public String getIssuePrVal() {
        return issuePrVal;
    }

    public void setIssuePrVal(String issuePrVal) {
        this.issuePrVal = issuePrVal;
    }

    public List<SelectItem> getMarkList() {
        return markList;
    }

    public void setMarkList(List<SelectItem> markList) {
        this.markList = markList;
    }

    public String getMarkingDd() {
        return markingDd;
    }

    public void setMarkingDd(String markingDd) {
        this.markingDd = markingDd;
    }

    public Date getMatDate() {
        return matDate;
    }

    public void setMatDate(Date matDate) {
        this.matDate = matDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSecDtlVal() {
        return secDtlVal;
    }

    public void setSecDtlVal(String secDtlVal) {
        this.secDtlVal = secDtlVal;
    }

    public String getSecTpDd() {
        return secTpDd;
    }

    public void setSecTpDd(String secTpDd) {
        this.secTpDd = secTpDd;
    }

    public List<SelectItem> getSecTypeList() {
        return secTypeList;
    }

    public void setSecTypeList(List<SelectItem> secTypeList) {
        this.secTypeList = secTypeList;
    }

    public String getSellerNmVal() {
        return sellerNmVal;
    }

    public void setSellerNmVal(String sellerNmVal) {
        this.sellerNmVal = sellerNmVal;
    }

    public List<SelectItem> getConSgAcList() {
        return conSgAcList;
    }

    public void setConSgAcList(List<SelectItem> conSgAcList) {
        this.conSgAcList = conSgAcList;
    }

    public String getConSgAccountDd() {
        return conSgAccountDd;
    }

    public void setConSgAccountDd(String conSgAccountDd) {
        this.conSgAccountDd = conSgAccountDd;
    }

    public String getfInttPayDate() {
        return fInttPayDate;
    }

    public void setfInttPayDate(String fInttPayDate) {
        this.fInttPayDate = fInttPayDate;
    }

    public String getPrnIssueDate() {
        return prnIssueDate;
    }

    public void setPrnIssueDate(String prnIssueDate) {
        this.prnIssueDate = prnIssueDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getsInttPayDate() {
        return sInttPayDate;
    }

    public void setsInttPayDate(String sInttPayDate) {
        this.sInttPayDate = sInttPayDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getNoOfShrVal() {
        return noOfShrVal;
    }

    public void setNoOfShrVal(int noOfShrVal) {
        this.noOfShrVal = noOfShrVal;
    }

    public String getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(String shareFlag) {
        this.shareFlag = shareFlag;
    }

    public String getSecDetailDd() {
        return secDetailDd;
    }

    public void setSecDetailDd(String secDetailDd) {
        this.secDetailDd = secDetailDd;
    }

    public List<SelectItem> getSecDtlList() {
        return secDtlList;
    }

    public void setSecDtlList(List<SelectItem> secDtlList) {
        this.secDtlList = secDtlList;
    }

    public String getSecDtlFlag() {
        return secDtlFlag;
    }

    public void setSecDtlFlag(String secDtlFlag) {
        this.secDtlFlag = secDtlFlag;
    }

    public String getSecTxtFlag() {
        return secTxtFlag;
    }

    public void setSecTxtFlag(String secTxtFlag) {
        this.secTxtFlag = secTxtFlag;
    }

    public String getMatFlag() {
        return matFlag;
    }

    public void setMatFlag(String matFlag) {
        this.matFlag = matFlag;
    }

    public String getSetFlag() {
        return setFlag;
    }

    public void setSetFlag(String setFlag) {
        this.setFlag = setFlag;
    }

    public String getTrnFlag() {
        return trnFlag;
    }

    public void setTrnFlag(String trnFlag) {
        this.trnFlag = trnFlag;
    }

    public String getIssDtFlag() {
        return issDtFlag;
    }

    public void setIssDtFlag(String issDtFlag) {
        this.issDtFlag = issDtFlag;
    }

    public String getFstFlag() {
        return fstFlag;
    }

    public void setFstFlag(String fstFlag) {
        this.fstFlag = fstFlag;
    }

    public String getMarkFlag() {
        return markFlag;
    }

    public void setMarkFlag(String markFlag) {
        this.markFlag = markFlag;
    }

    public String getSecIFlag() {
        return secIFlag;
    }

    public void setSecIFlag(String secIFlag) {
        this.secIFlag = secIFlag;
    }

    public String getBookFlag() {
        return bookFlag;
    }

    public void setBookFlag(String bookFlag) {
        this.bookFlag = bookFlag;
    }

    public String getConAcFlag() {
        return conAcFlag;
    }

    public void setConAcFlag(String conAcFlag) {
        this.conAcFlag = conAcFlag;
    }

    public String getConFlag() {
        return conFlag;
    }

    public void setConFlag(String conFlag) {
        this.conFlag = conFlag;
    }

    public String getCrBrFlag() {
        return crBrFlag;
    }

    public void setCrBrFlag(String crBrFlag) {
        this.crBrFlag = crBrFlag;
    }

    public String getCrGlFlag() {
        return crGlFlag;
    }

    public void setCrGlFlag(String crGlFlag) {
        this.crGlFlag = crGlFlag;
    }

    public String getIssPFlag() {
        return issPFlag;
    }

    public void setIssPFlag(String issPFlag) {
        this.issPFlag = issPFlag;
    }

    public String getPrFlag() {
        return prFlag;
    }

    public void setPrFlag(String prFlag) {
        this.prFlag = prFlag;
    }

    public String getSglFlag() {
        return sglFlag;
    }

    public void setSglFlag(String sglFlag) {
        this.sglFlag = sglFlag;
    }

    public int getGetCode() {
        return getCode;
    }

    public void setGetCode(int getCode) {
        this.getCode = getCode;
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

    public String getPriceVal() {
        return priceVal;
    }

    public void setPriceVal(String priceVal) {
        this.priceVal = priceVal;
    }

    public String getRoiVal() {
        return roiVal;
    }

    public void setRoiVal(String roiVal) {
        this.roiVal = roiVal;
    }

    public String getSelFlag() {
        return selFlag;
    }

    public void setSelFlag(String selFlag) {
        this.selFlag = selFlag;
    }

    public String getParticularVal() {
        return particularVal;
    }

    public void setParticularVal(String particularVal) {
        this.particularVal = particularVal;
    }

    public String getCrHeadName() {
        return crHeadName;
    }

    public void setCrHeadName(String crHeadName) {
        this.crHeadName = crHeadName;
    }

    public String getYtmVal() {
        return ytmVal;
    }

    public void setYtmVal(String ytmVal) {
        this.ytmVal = ytmVal;
    }    
    
    /** Creates a new instance of SecurityMaster */
    public SecurityMaster() {
        try {

            this.setShareFlag("true");
            this.setSecDtlFlag("true");
            this.setSecTxtFlag("false");
            this.setIssPFlag("false");
            this.setPrFlag("false");
            this.setBookFlag("false");
            this.setSelFlag("true");
            setFlag = "false";
            
            this.setTransactionDate(new Date());
            this.setMatDate(new Date());

            crBrnList = new ArrayList<SelectItem>();
            secTypeList = new ArrayList<SelectItem>();

            conSgAcList = new ArrayList<SelectItem>();
            conSgAcList.add(new SelectItem("NSCCL", "NSCCL"));
            conSgAcList.add(new SelectItem("RBI SGL A/C", "RBI SGL A/C"));
            conSgAcList.add(new SelectItem("OTHERS", "OTHERS"));

            markList = new ArrayList<SelectItem>();
            markList.add(new SelectItem("", "-- Select --"));
            markList.add(new SelectItem("HM", "Held For Maturity"));
            markList.add(new SelectItem("HT", "Held for Trading"));
            markList.add(new SelectItem("AF", "Available for Sale"));

            remoteInterface = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsObj = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(ftsJndiHomeName);
            hoRep = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(hoRepJndiHomeName);
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(invRepjndiHomeName);

            List<BranchMaster> resultList = new ArrayList<BranchMaster>();
            resultList = remoteInterface.getAllAlphaCode();
            for (int i = 0; i < resultList.size(); i++) {
                BranchMaster entity = resultList.get(i);
                crBrnList.add(new SelectItem(entity.getAlphaCode()));
            }
            this.setCrBranchDd("HO");
            loadSecurityType();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void secDtlLostFocus() {
        try {
            String securityName = this.getSecDetailDd();
            List<InvestmentSecurityMaster> resultList = new ArrayList<InvestmentSecurityMaster>();
            resultList = remoteInterface.getSecRoi(securityName);
            for (int i = 0; i < resultList.size(); i++) {
                InvestmentSecurityMaster entity = resultList.get(i);
                String ipDt1 = entity.getIntPayableFirstDate();
                String ipDt2 = entity.getIntPayableSecondDate();
                String matYr = entity.getMaturityYear();
                double roi = entity.getRoi();
                if (ipDt1.isEmpty()) {
                    ipDt1 = "01/01";
                }
                if (ipDt2.isEmpty()) {
                    ipDt2 = "01/07";
                }
                String Dt = ipDt1 + matYr;
                setRoiVal(Double.toString(roi));
                ipDt1 = getFdDt1(ipDt1, ipDt2, dmy.format(dt));
                ipDt2 = CbsUtil.monthAdd(ymd.format(dmy.parse(ipDt1)), 6);                
                setfInttPayDate(ipDt1);
                setsInttPayDate(ipDt2.substring(6)+"/"+ipDt2.substring(4,6)+"/"+ipDt2.substring(0,4));
                setMatDate(dmy.parse(matYr));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void loadSecurityType() {
        try {
            List<GlDescRange> resultList = new ArrayList<GlDescRange>();
            resultList = remoteInterface.getGlRange("G");
            for (int i = 0; i < resultList.size(); i++) {
                GlDescRange entity = resultList.get(i);
                secTypeList.add(new SelectItem(entity.getGlname()));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void secTypeLostFocus() {
        getCode = SecCode(this.getSecTpDd());

        if (getCode == 1) {
            this.setSecDtlFlag("false");
            this.setSecTxtFlag("true");
            this.setSecDtlVal("");
            loadGoi();
        } else {
            this.setSecTxtFlag("false");
            this.setSecDtlFlag("true");
            this.setSecDetailDd("");
            loadEquity(getCode);
        }
        loadSName();
    }
    
    public void secSelLostFocus(){
        if(this.getIssueFrDd().equalsIgnoreCase("OTHERS")){
            this.setSelFlag("false");
            this.setSellerNmVal("");
        }else{
            this.setSellerNmVal("");
            this.setSelFlag("true");
        }
    }

    public void loadSName() {
        issueFrmList = new ArrayList<SelectItem>();
        try {
             List<String> resultList = remoteInterface.getAllSellerName(this.getSecTpDd().trim());
             for (int i = 0; i < resultList.size(); i++) {
                issueFrmList.add(new SelectItem(resultList.get(i)));
            }
            issueFrmList.add(new SelectItem("OTHERS", "OTHERS"));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void loadEquity(int argCode) {
        if ((argCode == 6) || (argCode == 8)) {
            this.setSecDtlFlag("true");
            matFlag = "true";
            issDtFlag = "true";
            trnFlag = "true";
            setFlag = "true";
            markFlag = "true";
            fstFlag = "true";
            secIFlag = "true";
            conAcFlag = "true";
            this.setIssPFlag("true");
            this.setPrFlag("true");
            this.setBookFlag("true");
            sglFlag = "true";
            conFlag = "true";
            crGlFlag = "true";
            crBrFlag = "true";
            shareFlag = "false";
            if (argCode == 8) {
                shareFlag = "true";
            }
        } else {
            matFlag = "false";
            trnFlag = "false";
            setFlag = "false";
            shareFlag = "true";
            conAcFlag = "false";
            conFlag = "false";
            this.setBookFlag("false");
            this.setPrFlag("false");
        }
    }

    public void loadGoi() {
        secDtlList = new ArrayList<SelectItem>();
        try {
            List<InvestmentSecurityMaster> resultList = new ArrayList<InvestmentSecurityMaster>();
            resultList = remoteInterface.getAllSecName();
            secDtlList.add(new SelectItem("", "-- Select --"));
            for (int i = 0; i < resultList.size(); i++) {
                InvestmentSecurityMaster entity = resultList.get(i);
                secDtlList.add(new SelectItem(entity.getSecurityName()));
            }
            secDtlList.add(new SelectItem("OTHERS", "OTHERS"));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public int SecCode(String sec) {
        int code = 0;
        try {
            GlDescRange codeVal = remoteInterface.getCodeFrmCodeBook(sec);
            if (codeVal != null) {
                code = codeVal.getCode();
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return code;
    }

    public void ClearAll() {
        this.setMessage("");
        this.setSecDtlVal("");
        this.setMatDate(new Date());
        this.setSellerNmVal("");
        this.setCodeNoVal("");
        this.setPrnIssueDate("");
        this.setPurchaseDate("");
        this.setTransactionDate(new Date());
        this.setSettlementDate(new Date());
        this.setfInttPayDate("");
        this.setsInttPayDate("");
        this.setRoiVal("");
        this.setConAcNoVal("");
        this.setIssuePrVal("");
        this.setPriceVal("");
        this.setFaceValue("");
        this.setBookValue("");
        this.setAccrIntVal("");
        this.setBrokerAcVal("");
        this.setBrokerageAmtVal("");
        this.setCrGlVal("");
        this.setSelFlag("true");
        this.setSecDetailDd("");
        this.setMarkingDd("");
        this.setParticularVal("");
        this.setYtmVal("0.0");
        this.setCrHeadName("");
    }

    public void saveTxn() {
        double brokerage = 0.0, acdint = 0.0;
        String sName = "", secDetail = "", msg = "";
        
        if(this.getCrGlVal().length() != 12){
             setMessage("Please Enter 12 Digit Credit GL Head");
             return;
        }
        
        if(!this.getCrGlVal().substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())){
            setMessage("Please Enter Credit GL Head Of Your Branch");
            return;
        }
        
        if ((getCode == 6) || (getCode == 8)) {
            if (getSecDetailDd().equalsIgnoreCase("")) {
                setMessage("Please fill the Security Details");
                return;
            }
            if (getTransactionDate().equals("")) {
                setMessage("Please check the Date of Purcahse");
                return;
            }
            if (getFaceValue().equalsIgnoreCase("")) {
                setMessage("Please fill the Face Value");
                return;
            }
        } else {
            if ((getCode != 1) && (getSecDtlVal().equalsIgnoreCase(""))) {
                setMessage("Please fill the Security Details");
                return;
            }
            if (getTransactionDate().equals("")) {
                setMessage("Please check the Date of Purcahse");
                return;
            }
            if (getSettlementDate().equals("")) {
                setMessage("Please check the Date of Settlement");
                return;
            }
            if (getTransactionDate().compareTo(getSettlementDate()) > 0) {
                setMessage("Settlement date can't be before Transaction date");
                return;
            }
            if ((getMatDate().compareTo(getSettlementDate()) < 0) || (getMatDate().compareTo(getSettlementDate()) == 0)) {
                setMessage("Settlement Date must be less than Maturity date");
                return;
            }
            if (getFaceValue().equalsIgnoreCase("")) {
                setMessage("Please fill the Face Value");
                return;
            }
            if (getBookValue().equals("")) {
                setMessage("Please fill the Book/ Maturity Value");
                return;
            }
        }

        if (getBrokerageAmtVal().equals("")) {
            brokerage = 0.0;
        } else {
            brokerage = Double.parseDouble(getBrokerageAmtVal());
        }

        if (getAccrIntVal().equals("")) {
            acdint = 0.0;
        } else {
            acdint = Double.parseDouble(getAccrIntVal());
        }

        if (getCode == 1) {
            secDetail = this.getSecDetailDd().toString();
        } else {
            secDetail = this.getSecDtlVal().toString();
        }       
        
        if (validateRoi() != true) {
            return;
        }
        
        if(validateYtm () != true){
            return;
        }

        if (this.getIssPFlag().equals("false")) {
            if (validateIssuedAmt() != true) {
                return;
            }
        }

        if (this.getPrFlag().equals("false")) {
            if (validatePrice() != true) {
                return;
            }
        }

        if (validateFaceAmt() != true) {
            return;
        }

        if (this.getBookFlag().equals("false")) {
            if (validateBookAmt() != true) {
                return;
            }
        }
        
        if(this.getIssueFrDd().equalsIgnoreCase("OTHERS")){
            sName = this.getSellerNmVal().toString();
        }else{
            sName = this.getIssueFrDd().toString();
        }       
        

        try {
            if(getCode == 1 && this.getMarkingDd().equalsIgnoreCase("HM")){
                double ndtl = 0, hmTot=0;
                List<DtlRegisterPojo> secNdtl = hoRep.getDtlForInvestment("90",ymd.format(dt) , "R");
                DtlRegisterPojo classPojo = null;
                for (int i = 0; i < secNdtl.size(); i++) {
                    classPojo = secNdtl.get(i);
                    List<RbiSossPojo> ndtlList  = classPojo.getNdtlList();
                    for (int j = 0; j < ndtlList.size(); j++) {
                        ndtl = ndtl + ndtlList.get(j).getAmt().doubleValue();
                    }
                }                
                
                List<Object[]> secList = remoteObj.getAllInvestmentMasterSecurityMaster(this.secTpDd, dt);
                
                for (int k = 0; k < secList.size(); k++) {
                    Object[] obj = secList.get(k);
                    InvestmentMaster im = (InvestmentMaster) obj[0];
                    if(im.getMarking().equalsIgnoreCase("HM")){
                        hmTot = hmTot + im.getBookvalue();
                    }
                }
                
                if((hmTot + Double.parseDouble(this.getBookValue())) > ((ndtl*25.0)/100.0)){
                    setMessage("Investment in government security under HTM category can't exceed 25% of ndtl");
                    return;
                }
            }            
            
            int maxSec = recNoGenerateSec();
            
            InvestmentSecurityAuthDetail invstSecAuthObj = new InvestmentSecurityAuthDetail();

            invstSecAuthObj.setMaxSec(maxSec);
            invstSecAuthObj.setCrGlVal(this.getCrGlVal());
            invstSecAuthObj.setSecTpDd(this.getSecTpDd());
            invstSecAuthObj.setSecDtl(secDetail);
            invstSecAuthObj.setFaceValue(new BigDecimal(this.getFaceValue()));
            invstSecAuthObj.setOrgnBrCode(getOrgnBrCode());
            invstSecAuthObj.setCrBranchDd(this.getCrBranchDd());
            invstSecAuthObj.setEnterBy(getUserName());
            invstSecAuthObj.setBookValue(new BigDecimal(this.getBookValue()));
            invstSecAuthObj.setAccrIntVal(new BigDecimal(this.getAccrIntVal()));
            invstSecAuthObj.setCodeNoVal(this.getCodeNoVal());
            invstSecAuthObj.setRoiVal(Double.valueOf(this.getRoiVal()));
            invstSecAuthObj.setTransactionDate(this.getTransactionDate());
            invstSecAuthObj.setSetFlag(this.setFlag);
            invstSecAuthObj.setSettlementDate(this.getSettlementDate());
            invstSecAuthObj.setSellerNmVal(sName);
            invstSecAuthObj.setConAcNoVal(this.getConAcNoVal());
            invstSecAuthObj.setBrokerAcVal(this.getBrokerAcVal());
            invstSecAuthObj.setBrokerageAmtVal(new BigDecimal(this.getBrokerageAmtVal()));
            invstSecAuthObj.setNoOfShrVal(this.getNoOfShrVal());
            invstSecAuthObj.setMatDate(this.getMatDate());
            invstSecAuthObj.setConSgAccountDd(this.getConSgAccountDd());
            invstSecAuthObj.setPriceVal(new BigDecimal(this.getPriceVal()));
            invstSecAuthObj.setPurchaseDate(this.getPurchaseDate());
            invstSecAuthObj.setPrnIssueDate(this.getPrnIssueDate());
            invstSecAuthObj.setFInttPayDate(this.getfInttPayDate());
            invstSecAuthObj.setSinttPaydate(this.getsInttPayDate());
            invstSecAuthObj.setIssuePrVal(new BigDecimal(this.getIssuePrVal()));
            invstSecAuthObj.setDetail(this.getParticularVal());
            invstSecAuthObj.setYtm(Double.valueOf(this.getYtmVal()));
            invstSecAuthObj.setAuth("N");
            invstSecAuthObj.setEnterDate(new Date());
            invstSecAuthObj.setMarking(this.getMarkingDd());
            
            remoteInterface.saveInvestmentSecurityAuthDetail(invstSecAuthObj);
            
            this.setMessage("Sequence No. is  " + maxSec + "Transaction Made Successfully");
            
            this.setSecDtlVal("");
            this.setMatDate(new Date());
            this.setSellerNmVal("");
            this.setCodeNoVal("");
            this.setPrnIssueDate("");
            this.setPurchaseDate("");
            this.setTransactionDate(new Date());
            this.setSettlementDate(new Date());
            this.setfInttPayDate("");
            this.setsInttPayDate("");
            this.setRoiVal("");
            this.setConAcNoVal("");
            this.setIssuePrVal("");
            this.setPriceVal("");
            this.setFaceValue("");
            this.setBookValue("");
            this.setAccrIntVal("");
            this.setBrokerAcVal("");
            this.setBrokerageAmtVal("");
            this.setCrGlVal("");
            this.setSelFlag("true");
            this.setSecDetailDd("");
            this.setMarkingDd("");
            this.setParticularVal("");
            this.setYtmVal("0.0");
            this.setCrHeadName("");

//            msg = remoteInterface.saveGoiSecurity(maxSec, this.getCrGlVal(), this.getSecTpDd(), this.getFaceValue(), getOrgnBrCode(), this.getCrBranchDd(),
//                    getUserName(), this.getBookValue(), this.getAccrIntVal(), secDetail, this.getCodeNoVal(), Double.valueOf(this.getRoiVal()),
//                    this.getTransactionDate(), this.setFlag, this.getSettlementDate(), sName , this.getConAcNoVal(), this.getBrokerAcVal(),
//                    Double.valueOf(this.getBrokerageAmtVal()), this.getNoOfShrVal(), this.getMatDate(), this.getConSgAccountDd(),
//                    Double.valueOf(this.getPriceVal()), this.getPurchaseDate(), this.getPrnIssueDate(), this.getfInttPayDate(), this.getsInttPayDate(),
//                    this.getIssuePrVal(),this.getParticularVal(),Double.valueOf(this.getYtmVal()));
//
//            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
//                if(msg.contains("GOIPURCHASE")){
//                    this.setMessage("Control No. is  " + maxSec + "Transaction Made Successfully");
//                }else{
//                    String tr = msg.substring(4);
//                    this.setMessage("Control No. is  " + maxSec + "Batch No.-" + tr + "Transaction Made Successfully");
//                }                
//            } else {
//                this.setMessage(msg);
//            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public int recNoGenerateSec() {
        int code = 0;
        try {
            code = remoteInterface.getMaxSeqNoInvestmentSecAuth();
            if (code == 0) {
                code = 1;
            } else {
                code = code + 1;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return code;
    }

//    public void purDateLostFocus() {
//        try {
//            String secName = this.getSecDetailDd();
//            String ipDt1 = "01/01";
//            List<InvestmentSecurityMaster> resultList = new ArrayList<InvestmentSecurityMaster>();
//            resultList = remoteInterface.getAllSecDtlBySecName(secName);
//            for (int i = 0; i < resultList.size(); i++) {
//                InvestmentSecurityMaster entity = resultList.get(i);
//                ipDt1 = entity.getIntPayableFirstDate();
//            }
//
//            ipDt1 = ipDt1 + "/" + this.getPurchaseDate().substring(6);
//            String ipDt2 = CbsUtil.monthAdd(ymd.format(dmy.parse(ipDt1)), 6);
//            this.setfInttPayDate(ipDt1);
//            ipDt2 = ipDt2.substring(6, 8) + "/" + ipDt2.substring(4, 6) + "/" + ipDt2.substring(0, 4);
//            this.setsInttPayDate(ipDt2);
//        } catch (Exception e) {
//            this.setMessage(e.getMessage());
//        }
//    }
    
    public void purDateLostFocus() {
        try {
            String secName = this.getSecDetailDd();
            String ipDt1 = "01/01";
            String ipDt2 = "01/07";
            List<InvestmentSecurityMaster> resultList = new ArrayList<InvestmentSecurityMaster>();
            resultList = remoteInterface.getAllSecDtlBySecName(secName);
            for (int i = 0; i < resultList.size(); i++) {
                InvestmentSecurityMaster entity = resultList.get(i);
                ipDt1 = entity.getIntPayableFirstDate();
                ipDt2 = entity.getIntPayableSecondDate();
            }

            ipDt1 = getFdDt1(ipDt1, ipDt2, this.getPurchaseDate());
            ipDt2 = CbsUtil.monthAdd(ymd.format(dmy.parse(ipDt1)), 6);                
            setfInttPayDate(ipDt1);
            setsInttPayDate(ipDt2.substring(6)+"/"+ipDt2.substring(4,6)+"/"+ipDt2.substring(0,4));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void bookLostFocus() {
        try {
            List<GoiAccrutinterestTable> resultDataList = goiAccrutinterest(1, getUserName(), ymd.format(dmy.parse(this.getfInttPayDate())), ymd.format(dmy.parse(this.getsInttPayDate())), ymd.format(dmy.parse(this.getPurchaseDate())), Double.valueOf(this.getFaceValue()), Double.valueOf(roiVal));
            if (!resultDataList.isEmpty()) {
                for (GoiAccrutinterestTable table : resultDataList) {
                    this.setAccrIntVal(formatter.format(table.getIntAmt()));                    
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

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

    public String exitButton() {
        ClearAll();
        return "case1";
    }

    public boolean validateIssuedAmt() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.getIssuePrVal() == null || this.getIssuePrVal().equalsIgnoreCase("")) {
            this.setMessage("Please Fill Issued Price Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(this.getIssuePrVal());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid Issued Price.");
                return false;
            }
        }

        if (this.getIssuePrVal().contains(".")) {
            if (this.getIssuePrVal().indexOf(".") != this.getIssuePrVal().lastIndexOf(".")) {
                this.setMessage("Invalid Issued Price .Please Fill The Amount Correctly.");
                return false;
            } else if (this.getIssuePrVal().substring(getIssuePrVal().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }

    public boolean validatePrice() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.getPriceVal() == null || this.getPriceVal().equalsIgnoreCase("")) {
            this.setMessage("Please Fill Price Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(this.getPriceVal());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid Price.");
                return false;
            }
        }

        if (this.getPriceVal().contains(".")) {
            if (this.getPriceVal().indexOf(".") != this.getPriceVal().lastIndexOf(".")) {
                this.setMessage("Invalid Price .Please Fill The Amount Correctly.");
                return false;
            } else if (this.getPriceVal().substring(getPriceVal().indexOf(".") + 1).length() > 3) {
                this.setMessage("Please Fill The Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }

    public boolean validateFaceAmt() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.getFaceValue() == null || this.getFaceValue().equalsIgnoreCase("")) {
            this.setMessage("Please Fill Face Value Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(this.getPriceVal());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid Face Value.");
                return false;
            }
        }

        if (this.getFaceValue().contains(".")) {
            if (this.getFaceValue().indexOf(".") != this.getFaceValue().lastIndexOf(".")) {
                this.setMessage("Invalid Face Value.Please Fill The Amount Correctly.");
                return false;
            } else if (this.getFaceValue().substring(getFaceValue().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }

    public boolean validateBookAmt() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.getBookValue() == null || this.getBookValue().equalsIgnoreCase("")) {
            this.setMessage("Please Fill Book Value Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(this.getBookValue());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid Book Value.");
                return false;
            }
        }

        if (this.getBookValue().contains(".")) {
            if (this.getBookValue().indexOf(".") != this.getBookValue().lastIndexOf(".")) {
                this.setMessage("Invalid Book Value.Please Fill The Amount Correctly.");
                return false;
            } else if (this.getBookValue().substring(getBookValue().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }

    public boolean validateRoi() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.getRoiVal() == null || this.getRoiVal().equalsIgnoreCase("")) {
            this.setMessage("Please Fill Rate Of Interest Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(this.getRoiVal());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid Rate Of Interest.");
                return false;
            }
        }

        if (this.getRoiVal().contains(".")) {
            if (this.getRoiVal().indexOf(".") != this.getRoiVal().lastIndexOf(".")) {
                this.setMessage("Invalid Rate Of Interest. Please Fill Correctly.");
                return false;
            } else if (this.getRoiVal().substring(getRoiVal().indexOf(".") + 1).length() > 4) {
                this.setMessage("Please Fill The Rate Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }
    
    public void onBlurCrHead() {
        this.setMessage("");
        if (this.crGlVal == null || this.crGlVal.equals("")) {
            this.setMessage("Please fill credited gL head !");
            return;
        }
        if (this.crGlVal.length() != 12) {
            this.setMessage("Please fill proper GL Haed !");
            return;
        }
        
        if(!crGlVal.substring(0, 2).equalsIgnoreCase(this.getOrgnBrCode())){
            this.setMessage("Please fill Credit GL Haed ! Of Your Branch");
            return;
        }
        
        try {
            Gltable entity = remoteInterface.getGltableByAcno(this.crGlVal);
            if (entity != null) {
                this.setCrHeadName(entity.getAcName());
            }
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("Credied GL haed does not exist.");
            } else {
                this.setMessage(ex.getMessage());
            }
        }
    }
    
    public boolean validateYtm () {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.getYtmVal() == null || this.getYtmVal().equalsIgnoreCase("")) {
            this.setMessage("Please Fill YTM Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(this.getYtmVal());
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid YTM.");
                return false;
            }
        }

        if (this.getYtmVal().contains(".")) {
            if (this.getYtmVal().indexOf(".") != this.getYtmVal().lastIndexOf(".")) {
                this.setMessage("Invalid YTM. Please Fill Correctly.");
                return false;
            } else if (this.getYtmVal().substring(getYtmVal().indexOf(".") + 1).length() > 4) {
                this.setMessage("Please Fill The Rate Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }
    
    public String getFdDt1(String dt1, String dt2, String trnDt) {
        String fddt1 = "",fddt3 = "";
        String fdt1 = dt1 + "/" + dmy.format(dt).substring(6);
        String sdt1 = dt2 + "/" + dmy.format(dt).substring(6);        
        try{
            if(dmy.parse(fdt1).compareTo(dmy.parse(sdt1))<0){
                fdt1 = fdt1;
                sdt1 = sdt1;       
            }else{
                fddt3 = fdt1;
                fdt1 = sdt1;
                sdt1 = fddt3;
            }
            if(dmy.parse(dmy.format(dt)).compareTo(dmy.parse(fdt1))<=0){
                fddt1 = fdt1;               
            }else{
                if(dmy.parse(dmy.format(dt)).compareTo(dmy.parse(sdt1))<=0){
                    fddt1 = sdt1;
                }else{
                    fddt1 = CbsUtil.monthAdd(ymd.format(dmy.parse(sdt1)), 6);                    
                    fddt1 = fddt1.substring(6)+"/"+fddt1.substring(4,6)+"/"+fddt1.substring(0,4);
                }                                
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return fddt1;
    }
}