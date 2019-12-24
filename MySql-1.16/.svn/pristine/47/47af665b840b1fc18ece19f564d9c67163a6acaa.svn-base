/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.BranchMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class FdrUpdation extends BaseBean {

    private String ctrlNo;
    private String message;
    private String secType;
    private String tdDetails;
    private String fdrNo;
    private String bank;
    private String branch;
    private String purDate;
    private String days;
    private String months;
    private String years;
    private String matDate;
    private String roi;
    private String intOpt;
    private String faceValue;
    private String interest;
    private String bookvalue;
    private List<SelectItem> secTypeList;
    private List<SelectItem> bankList;
    private List<SelectItem> branchList;
    private List<SelectItem> intOptList;
    private boolean visibleMatDt;
    private boolean processBtnVisible;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    private TdReceiptManagementFacadeRemote obj = null;
    private final String jndiName = "TdReceiptManagementFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd"); 
    NumberFormat formatter = new DecimalFormat("#.00");
    Date dt = new Date();
    private BigDecimal intAmt;
    
    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(String ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public List<SelectItem> getBankList() {
        return bankList;
    }

    public void setBankList(List<SelectItem> bankList) {
        this.bankList = bankList;
    }

    public String getBookvalue() {
        return bookvalue;
    }

    public void setBookvalue(String bookvalue) {
        this.bookvalue = bookvalue;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public boolean isProcessBtnVisible() {
        return processBtnVisible;
    }

    public void setProcessBtnVisible(boolean processBtnVisible) {
        this.processBtnVisible = processBtnVisible;
    }

    public String getPurDate() {
        return purDate;
    }

    public void setPurDate(String purDate) {
        this.purDate = purDate;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public List<SelectItem> getSecTypeList() {
        return secTypeList;
    }

    public void setSecTypeList(List<SelectItem> secTypeList) {
        this.secTypeList = secTypeList;
    }

    public String getTdDetails() {
        return tdDetails;
    }

    public void setTdDetails(String tdDetails) {
        this.tdDetails = tdDetails;
    }

    public boolean isVisibleMatDt() {
        return visibleMatDt;
    }

    public void setVisibleMatDt(boolean visibleMatDt) {
        this.visibleMatDt = visibleMatDt;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }  
    
    public FdrUpdation() {
        secTypeList = new ArrayList<SelectItem>();
        intOptList = new ArrayList<SelectItem>();
        branchList = new ArrayList<SelectItem>();
        bankList = new ArrayList<SelectItem>();
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            obj = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            setBranchList();
            setIntOption();
            setSecurityList();
            setBankNameList();
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setBranchList() {
        try {
            List<BranchMaster> entityList = remoteObj.getAllAlphaCode();
            if (!entityList.isEmpty()) {
                for (BranchMaster entity : entityList) {
                    branchList.add(new SelectItem(entity.getAlphaCode()));
                }
                this.setBranch("HO");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setIntOption() {
        intOptList.add(new SelectItem("S", "SIMPLE"));
        intOptList.add(new SelectItem("M", "MONTHLY"));
        intOptList.add(new SelectItem("Q", "QUARTERLY"));
        intOptList.add(new SelectItem("C", "CUMULATIVE"));
    }

    public void setSecurityList() {
        try {
            List<GlDescRange> resultList = new ArrayList<GlDescRange>();
            resultList = remoteObj.getGlRange("F");
            for (int i = 0; i < resultList.size(); i++) {
                GlDescRange entity = resultList.get(i);
                secTypeList.add(new SelectItem(entity.getGlname()));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }        
    }

    public void setBankNameList() {
        try {
            List<GlDescRange> entityList = remoteObj.getGlRange("F");
            if (!entityList.isEmpty()) {
                for (GlDescRange entity : entityList) {
                    String fromNo = String.format("%06d", Integer.parseInt(entity.getFromno()));
                    String toNo = String.format("%06d", Integer.parseInt(entity.getTono()));
                    List<String> securityList = remoteObj.getSecurityType(fromNo, toNo);
                    if (!securityList.isEmpty()) {
                        for (String security : securityList) {
                            bankList.add(new SelectItem(security));
                        }
                    } else {
                        this.setMessage("Data is not found for government security in GL DESC RANGE table !");
                        return;
                    }
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

    public void onBlurCtrlNo() {
        this.setMessage("");
        try {
            Object[] dataArray = remoteObj.getInvestmentDetailsAndDates("ACTIVE", Integer.parseInt(this.ctrlNo));
            if (dataArray.length > 0) {
                InvestmentFdrDates entity = (InvestmentFdrDates) dataArray[0];
                if (entity != null) {
                    this.setPurDate(dmy.format(entity.getPurchaseDt()));
                    this.setDays(entity.getDays().toString());
                    this.setMonths(entity.getMonths().toString());
                    this.setYears(entity.getYears().toString());
                    this.setMatDate(dmy.format(entity.getMatDt()));
                    this.setIntOpt(entity.getIntOpt());
                }
                InvestmentFdrDetails entityObj = (InvestmentFdrDetails) dataArray[1];
                if (entityObj != null) {
                    this.setSecType(entityObj.getSecType());
                    this.setTdDetails(entityObj.getSecDesc());
                    this.setFdrNo(entityObj.getFdrNo());
                    this.setBank(entityObj.getSellerName());
                    this.setBranch(entityObj.getBranch());
                    this.setRoi(String.valueOf(entityObj.getRoi()));
                    this.setFaceValue(formatter.format(entityObj.getFaceValue()));
                    this.setInterest(formatter.format(entityObj.getBookValue() - entityObj.getFaceValue()));
                    this.setIntAmt(new BigDecimal(formatter.format(entityObj.getBookValue() - entityObj.getFaceValue())));
                    this.setBookvalue(formatter.format(entityObj.getBookValue()));
                }
                this.processBtnVisible = false;
            } else {
                this.setMessage("There is no data for control number: " + this.ctrlNo);
                return;
            }
        } catch (ApplicationException ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("There is no data for control number: " + this.ctrlNo);
            } else {
                this.setMessage(ex.getMessage());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurFdrNo() {
        this.setMessage("");
        if (this.fdrNo == null || this.fdrNo.equals("") || this.fdrNo.equals("0") || this.fdrNo.equals("0.0")) {
            this.setMessage("Please fill correct frd no !");
            return;
        }
    }

    public void onBlurPurDate() {
        this.setMessage("");
        if (this.purDate == null || this.purDate.equals("") || this.purDate.length() < 10) {
            this.setMessage("Please fill correct purchase date !");
            return;
        }

        boolean var = new Validator().validateDate_dd_mm_yyyy(this.purDate);
        if (var == false) {
            this.setMessage("Please fill proper purchase date !");
            return;
        }
    }

    public void onBlurYears() {
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
                String addedYear = CbsUtil.yearAdd(ymd.format(dmy.parse(this.purDate)), Integer.parseInt(this.years));
                totalDaysInYear = CbsUtil.dayDiff(dmy.parse(this.purDate), ymd.parse(addedYear));
            }

            if (this.months != null && !this.months.equals("") && !this.months.equals("0") && !this.months.equals("0.0")) {
                String addedMonth = CbsUtil.monthAdd(ymd.format(dmy.parse(this.purDate)), Integer.parseInt(this.months));
                totalDaysInMonth = CbsUtil.dayDiff(dmy.parse(this.purDate), ymd.parse(addedMonth));
            }

            if (this.days == null || this.days.equals("")) {
                totalDaysByUser = 0;
            } else {
                totalDaysByUser = Long.parseLong(this.days);
            }
            totalDays = totalDaysInYear + totalDaysInMonth + totalDaysByUser;
            String matureDate = CbsUtil.dateAdd(ymd.format(dmy.parse(this.purDate)), (int) totalDays);
            this.setMatDate(dmy.format(ymd.parse(matureDate)));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurRoi() {
        this.setMessage("");
        if (this.roi == null || this.roi.equals("")) {
            this.setMessage("Please fill roi !");
            return;
        }
    }

    public void onBlurIntOpt() {
        this.setMessage("");
        if (this.faceValue == null || this.faceValue.equals("")) {
            this.setMessage("There should be face value !");
            return;
        }
        try {
            String day = "", month = "", year = "", period = "";
            if (this.days == null || this.days.equals("")) {
                day = "0Days";
            } else {
                day = this.days + "Days";
            }
            if (this.months == null || this.months.equals("")) {
                month = "0Months";
            } else {
                month = this.months + "Months";
            }
            if (this.years == null || this.years.equals("")) {
                year = "0Years";
            } else {
                year = this.years + "Years";
            }
            period = year + month + day;
            String objInterest = obj.orgFDInterest(this.intOpt, Float.parseFloat(this.roi), ymd.format(dmy.parse(this.purDate)), ymd.format(dmy.parse(this.matDate)), Double.parseDouble(this.faceValue), period, this.getOrgnBrCode());
            this.setInterest(formatter.format(Double.parseDouble(objInterest)));
            this.setIntAmt(new BigDecimal(objInterest));
            this.setBookvalue(String.valueOf(formatter.format(Double.parseDouble(this.faceValue) + Double.parseDouble(objInterest))));
            this.processBtnVisible = false;
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        try {
            if (validate()) {
                /**
                 * FdrDates Entity
                 */
                InvestmentFdrDates invFdrDates = remoteObj.getInvestmentFdrDatesByCtrlNo(Integer.parseInt(this.ctrlNo));
                invFdrDates.setPurchaseDt(dmy.parse(this.purDate));
                invFdrDates.setMatDt(dmy.parse(this.matDate));

                invFdrDates.setLastIntPaidDt(dmy.parse(this.purDate));
                invFdrDates.setIntOpt(this.intOpt);
                invFdrDates.setDays(new BigInteger(this.days));
                invFdrDates.setMonths(new BigInteger(this.months));
                invFdrDates.setYears(new BigInteger(this.years));

                /**
                 * FdrDetails Entity
                 */
                InvestmentFdrDetails invFdrDetails = remoteObj.getInvestmentFdrDetailsByCtrlNo(Integer.parseInt(this.ctrlNo));
                invFdrDetails.setSecType(this.secType);
                invFdrDetails.setSecDesc(this.tdDetails);

                invFdrDetails.setRoi(Double.parseDouble(this.roi));
                invFdrDetails.setSellerName(this.bank);
                invFdrDetails.setFaceValue(Double.parseDouble(this.faceValue));
                invFdrDetails.setBookValue(Double.parseDouble(this.bookvalue));

                invFdrDetails.setIntOpt(this.intOpt);
                invFdrDetails.setEnterBy(getUserName());
                invFdrDetails.setBranch(this.branch);
                invFdrDetails.setFdrNo(this.fdrNo);

                String msg = remoteObj.fdrUpdation(invFdrDates, invFdrDetails);
                if (msg.equals("true")) {
                    resetForm();
                    this.setMessage("Data has been updated successfully !");
                    return;
                }
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validate() {
        if (this.fdrNo == null || this.fdrNo.equals("") || this.fdrNo.equals("0") || this.fdrNo.equals("0.0")) {
            this.setMessage("Please fill correct frd no !");
            return false;
        }

        if (this.purDate == null || this.purDate.equals("") || this.purDate.length() < 10) {
            this.setMessage("Please fill correct purchase date !");
            return false;
        }

        boolean var = new Validator().validateDate_dd_mm_yyyy(this.purDate);
        if (var == false) {
            this.setMessage("Please fill proper purchase date !");
            return false;
        }
        
        try{
            if (dmy.parse(this.getPurDate()).after(dt)) {
                this.setMessage("Purchase date should be less or Equal to Current date");
                return false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

        if (this.days == null && this.months == null && this.years == null) {
            this.setMessage("Please fill period !");
            return false;
        }

        if (this.days.equals("") && this.months.equals("") && this.years.equals("")) {
            this.setMessage("Please fill period !");
            return false;
        }

        if (this.roi == null || this.roi.equals("")) {
            this.setMessage("Please fill roi !");
            return false;
        }

        if (this.faceValue == null || this.faceValue.equals("")) {
            this.setMessage("face value is missing !");
            return false;
        }

        return true;
    }

    public void resetForm() {
        this.setMessage("");
        this.setFdrNo("");
        this.setTdDetails("");
        this.setFdrNo("");
        this.setPurDate("");
        this.setDays("");
        this.setMonths("");
        this.setYears("");
        this.setMatDate("");
        this.setRoi("");
        this.setFaceValue("");
        this.setInterest("");
        this.setBookvalue("");
        this.setIntAmt(new BigDecimal("0"));
        this.setCtrlNo("");
        this.visibleMatDt = true;
        this.processBtnVisible = true;
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
    
    public void onBlurInterest() {
        this.setMessage("");
        if (this.faceValue == null || this.faceValue.equals("")) {
            this.setMessage("Please fill face value !");
            return;
        }
        try {
            BigDecimal interestAmt = this.getIntAmt();
            BigDecimal intAmtVal = new BigDecimal(formatter.format(Double.parseDouble(this.getInterest())));
            BigDecimal bookval = new BigDecimal(this.getBookvalue());
            if(interestAmt.compareTo(intAmtVal)== -1){
                this.setBookvalue(bookval.add((intAmtVal.subtract(interestAmt))).toString());                
                this.setIntAmt(new BigDecimal(this.getInterest()));
            }else{
                this.setBookvalue(bookval.subtract((interestAmt.subtract(intAmtVal))).toString());                
                this.setIntAmt(new BigDecimal(this.getInterest()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}