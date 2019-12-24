/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.master.BranchMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
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
public class FdrCreation extends BaseBean {

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
    private String crHead;
    private String crHeadName;
    private String branchName;
    private String drAcno;
    private String drAmt;
    private String crAcno;
    private String crAmt;
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

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }

    public String getCrHeadName() {
        return crHeadName;
    }

    public void setCrHeadName(String crHeadName) {
        this.crHeadName = crHeadName;
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

    public String getCrAcno() {
        return crAcno;
    }

    public void setCrAcno(String crAcno) {
        this.crAcno = crAcno;
    }

    public String getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(String crAmt) {
        this.crAmt = crAmt;
    }

    public String getDrAcno() {
        return drAcno;
    }

    public void setDrAcno(String drAcno) {
        this.drAcno = drAcno;
    }

    public String getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(String drAmt) {
        this.drAmt = drAmt;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }   
    
    public FdrCreation() {
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
                this.setBranchName("HO");
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
            this.setMessage("Please fill proper date !");
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
        if (this.roi.toString().contains(".")) {
            if (this.roi.toString().indexOf(".") != this.roi.toString().lastIndexOf(".")) {
                this.setMessage("Invalid Roi.Please Fill The Roi Correctly.");
                return;
            } else if (this.roi.toString().substring(this.roi.toString().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Roi Upto Two Decimal Places.");
                return;
            } else {
                this.setMessage("");
                return;
            }
        }
    }

    public void onBlurfaceValue() {
        this.setMessage("");
        if (this.faceValue == null || this.faceValue.equals("")) {
            this.setMessage("Please fill face value !");
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
            //this.setIntAmt(Float.parseFloat(objInterest));
            this.setIntAmt(new BigDecimal(objInterest));
            this.setBookvalue(String.valueOf(formatter.format(Double.parseDouble(this.faceValue) + Double.parseDouble(objInterest))));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurCrHead() {
        this.setMessage("");
        if (this.crHead == null || this.crHead.equals("")) {
            this.setMessage("Please fill credited gL head !");
            return;
        }
        if (this.crHead.length() != 12) {
            this.setMessage("Please fill proper GL Haed !");
            return;
        }
        if (!this.crHead.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())) {
            this.setMessage("Please fill GL Haed of your branch!");
            return;
        }
        try {
            Gltable entity = remoteObj.getGltableByAcno(this.crHead);
            if (entity != null) {
                this.setCrHeadName(entity.getAcName());
            }
            
            List<Gltable> gltableList = remoteObj.getGltable(this.bank);
            if (!gltableList.isEmpty()) {
                for (Gltable glEntity : gltableList) {
                    this.setDrAcno(glEntity.getAcNo());
                }
            }
            
            this.setCrAcno(this.getCrHead());
            this.setCrAmt(formatter.format(Double.parseDouble(this.faceValue)));
            this.setDrAmt(formatter.format(Double.parseDouble(this.faceValue)));
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("No result fetched in the table")) {
                this.setMessage("Credied GL haed does not exist.");
            } else {
                this.setMessage(ex.getMessage());
            }
        }
    }

    public void onBlurBrName() {
        this.setMessage("");
        this.processBtnVisible = false;
    }

    public void processAction() {
        this.setMessage("");        
        try {
            if (validate()) {
                //Integer maxCtrlNo = remoteObj.getMaxCtrlNoFromInvestmentFdrDetails();
                String passDays = "0", passMonth = "0", passYear = "0";
                if (this.days != null && !this.days.equals("")) {
                    passDays = this.days;
                }
                if (this.months != null && !this.months.equals("")) {
                    passMonth = this.months;
                }
                if (this.years != null && !this.years.equals("")) {
                    passYear = this.years;
                }                
//                String msg = remoteObj.fdrSaveProcess(maxCtrlNo, dmy.parse(this.purDate), dmy.parse(this.matDate),
//                        this.intOpt, passDays, passMonth, passYear, this.tdDetails, this.roi, this.bank,
//                        Double.parseDouble(this.faceValue), Double.parseDouble(this.bookvalue), this.fdrNo, this.branch,
//                        getUserName(), this.crHead, getOrgnBrCode(),this.getSecType());
//                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
//                    resetForm();
//                    this.setMessage("Transaction has been made successfully, Controll No: " + maxCtrlNo.toString() + " And " + "Batch No: " + msg.substring(4));
//                    return;
//                }
                
                String msg = remoteObj.saveFDRCreateAuthDetail(secType, tdDetails, fdrNo,bank, branch,purDate,passDays, passMonth,passYear,
                matDate,roi, intOpt,faceValue, interest,bookvalue, crHead,drAcno,branchName,getUserName(),getOrgnBrCode());

            if (msg.equalsIgnoreCase("true")) {
                resetForm();
                this.setMessage("Data has been saved successfully");
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
            this.setMessage("Please fill proper date !");
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
        
        if (this.roi.toString().contains(".")) {
            if (this.roi.toString().indexOf(".") != this.roi.toString().lastIndexOf(".")) {
                this.setMessage("Invalid Roi.Please Fill The Roi Correctly.");
                return false;
            } else if (this.roi.toString().substring(this.roi.toString().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Roi Upto Two Decimal Places.");
                return false;
            }
        }       

        if (this.faceValue == null || this.faceValue.equals("")) {
            this.setMessage("Please fill face value !");
            return false;
        }

        if (this.crHead == null || this.crHead.equals("")) {
            this.setMessage("Please fill credited gL head !");
            return false;
        }
        if((this.days.equalsIgnoreCase("0")) && (this.months.equalsIgnoreCase("0")) && (this.years.equalsIgnoreCase("0"))){
            this.setMessage("Please Any Values In period !");
            return false;                    
        }
        
        if (this.crHead == null || this.crHead.equals("")) {
            this.setMessage("Please fill credited gL head !");
            return false;
        }
        if (this.crHead.length() != 12) {
            this.setMessage("Please fill proper GL Haed !");
            return false;
        }
        
        if (!this.crHead.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode())) {
            this.setMessage("Please fill GL Haed of your branch!");
            return false;
        }
        
        try {
            Gltable entity = remoteObj.getGltableByAcno(this.crHead);
            if (entity == null) {
                this.setMessage("Please fill proper GL Haed !");
                return false;
            }            
        }catch (Exception ex){
            this.setMessage("Please fill proper GL Haed !");
            return false;
        }
        return true;
    }

    public void resetForm() {
        this.setMessage("");
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
        this.setCrHead("");
        this.setCrHeadName("");
        this.setDrAcno("");
        this.setDrAmt("");
        this.setCrAcno("");
        this.setCrAmt("");
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