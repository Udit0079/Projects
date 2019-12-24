/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.facade.clg.OutwardClearingManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.pojo.ChbookDetailPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
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
 * @author Admin
 */
public class PdcProcess extends BaseBean {
    
    private String message;
    private String acNoLen;
    private String crAccountNo;
    private String crAcno;
    private String custName;
    private String drAccNo;
    private String bankName;
    private String branchName;
    private String ifscCode;
    private String amt;
    private String freq;
    private List<SelectItem> freqList;
    private String chqFrm;
    private String chqTo;
    private String chqDt;
    private String disSave;
    private String opt;
    private List<SelectItem> optList;
    private String pendingRef;
    private List<SelectItem> pRefList;
    private String disRef;
    private List<ChbookDetailPojo> tableDataList;
    private String disAccount;
    private String disDrAccount;
    private String disBankName;
    private String disBranchName;
    private String disIfscCode;
    private String disAmt;
    private String disFreq;
    private String disChqFrm;
    private String disChqTo;
    private String disChqDt;
    private String disDelete;
    private String btnValue;
    private final String jndiHomeName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    
    private final String jndiLoanHomeName = "BankAndLoanMasterFacade";
    private BankAndLoanMasterFacadeRemote bnkLoanFacade = null;
    
    private final String jndiHomeNameOutward = "OutwardClearingManagementFacade";
    private OutwardClearingManagementFacadeRemote outwardClgRemote = null;
    
    private String btnMsg;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private String focusId;
    private String frmEnter;
    String bankCode1;
    String bankCode2;
    String bankCode3;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getCrAccountNo() {
        return crAccountNo;
    }

    public void setCrAccountNo(String crAccountNo) {
        this.crAccountNo = crAccountNo;
    }

    public String getCrAcno() {
        return crAcno;
    }

    public void setCrAcno(String crAcno) {
        this.crAcno = crAcno;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDrAccNo() {
        return drAccNo;
    }

    public void setDrAccNo(String drAccNo) {
        this.drAccNo = drAccNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public List<SelectItem> getFreqList() {
        return freqList;
    }

    public void setFreqList(List<SelectItem> freqList) {
        this.freqList = freqList;
    }

    public String getChqFrm() {
        return chqFrm;
    }

    public void setChqFrm(String chqFrm) {
        this.chqFrm = chqFrm;
    }

    public String getChqTo() {
        return chqTo;
    }

    public void setChqTo(String chqTo) {
        this.chqTo = chqTo;
    }

    public String getChqDt() {
        return chqDt;
    }

    public void setChqDt(String chqDt) {
        this.chqDt = chqDt;
    }

    public String getDisSave() {
        return disSave;
    }

    public void setDisSave(String disSave) {
        this.disSave = disSave;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public List<SelectItem> getOptList() {
        return optList;
    }

    public void setOptList(List<SelectItem> optList) {
        this.optList = optList;
    }

    public String getPendingRef() {
        return pendingRef;
    }

    public void setPendingRef(String pendingRef) {
        this.pendingRef = pendingRef;
    }

    public List<SelectItem> getpRefList() {
        return pRefList;
    }

    public void setpRefList(List<SelectItem> pRefList) {
        this.pRefList = pRefList;
    }

    public String getDisRef() {
        return disRef;
    }

    public void setDisRef(String disRef) {
        this.disRef = disRef;
    }

    public List<ChbookDetailPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<ChbookDetailPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public String getDisAccount() {
        return disAccount;
    }

    public void setDisAccount(String disAccount) {
        this.disAccount = disAccount;
    }

    public String getDisDrAccount() {
        return disDrAccount;
    }

    public void setDisDrAccount(String disDrAccount) {
        this.disDrAccount = disDrAccount;
    }

    public String getDisBankName() {
        return disBankName;
    }

    public void setDisBankName(String disBankName) {
        this.disBankName = disBankName;
    }

    public String getDisBranchName() {
        return disBranchName;
    }

    public void setDisBranchName(String disBranchName) {
        this.disBranchName = disBranchName;
    }

    public String getDisIfscCode() {
        return disIfscCode;
    }

    public void setDisIfscCode(String disIfscCode) {
        this.disIfscCode = disIfscCode;
    }

    public String getDisAmt() {
        return disAmt;
    }

    public void setDisAmt(String disAmt) {
        this.disAmt = disAmt;
    }

    public String getDisFreq() {
        return disFreq;
    }

    public void setDisFreq(String disFreq) {
        this.disFreq = disFreq;
    }

    public String getDisChqFrm() {
        return disChqFrm;
    }

    public void setDisChqFrm(String disChqFrm) {
        this.disChqFrm = disChqFrm;
    }

    public String getDisChqTo() {
        return disChqTo;
    }

    public void setDisChqTo(String disChqTo) {
        this.disChqTo = disChqTo;
    }

    public String getDisChqDt() {
        return disChqDt;
    }

    public void setDisChqDt(String disChqDt) {
        this.disChqDt = disChqDt;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getBtnMsg() {
        return btnMsg;
    }

    public void setBtnMsg(String btnMsg) {
        this.btnMsg = btnMsg;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getFrmEnter() {
        return frmEnter;
    }

    public void setFrmEnter(String frmEnter) {
        this.frmEnter = frmEnter;
    }

    public String getDisDelete() {
        return disDelete;
    }

    public void setDisDelete(String disDelete) {
        this.disDelete = disDelete;
    }   

    public String getBankCode1() {
        return bankCode1;
    }

    public void setBankCode1(String bankCode1) {
        this.bankCode1 = bankCode1;
    }

    public String getBankCode2() {
        return bankCode2;
    }

    public void setBankCode2(String bankCode2) {
        this.bankCode2 = bankCode2;
    }

    public String getBankCode3() {
        return bankCode3;
    }

    public void setBankCode3(String bankCode3) {
        this.bankCode3 = bankCode3;
    }
    
    public PdcProcess() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            bnkLoanFacade = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup(jndiLoanHomeName);
            outwardClgRemote = (OutwardClearingManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOutward);
            
            this.setAcNoLen(getAcNoLength());
            optList = new ArrayList<SelectItem>();
            optList.add(new SelectItem("A", "ADD"));
            optList.add(new SelectItem("V", "Verify"));
            
            freqList = new ArrayList<SelectItem>();
            freqList.add(new SelectItem("M", "Monthly"));
            freqList.add(new SelectItem("Q", "Quarterly"));
            freqList.add(new SelectItem("H", "Half-Yearly"));
            freqList.add(new SelectItem("Y", "Yearly"));
            
            this.setBtnValue("Save");
            this.setDisDelete("true");
            
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void optProcess(){
        this.setMessage("");
        this.setCrAccountNo("");
        this.setCrAcno("");
        this.setCustName("");
        this.setDrAccNo("");
        this.setBankName("");
        this.setBranchName("");
        this.setIfscCode("");
        this.setAmt("");
        this.setFreq("M");
        this.setChqFrm("");
        this.setChqTo("");
        this.setChqDt("");
        if(opt.equalsIgnoreCase("A")){
            this.setDisRef("true");
            this.setDisAccount("false");
            this.setDisDrAccount("false");
            this.setDisBankName("true");
            this.setDisBranchName("true");
            this.setDisIfscCode("false");
            this.setDisAmt("false");
            this.setDisFreq("false");
            this.setDisChqFrm("false");
            this.setDisChqTo("false");
            this.setDisChqDt("false");
            this.setBtnValue("Save");
            setFocusId("txtCrAcno");
            this.setDisDelete("true");
        } else if(opt.equalsIgnoreCase("V")){
            this.setDisRef("false");
            this.setDisAccount("true");
            this.setDisDrAccount("true");
            this.setDisBankName("true");
            this.setDisBranchName("true");
            this.setDisIfscCode("true");
            this.setDisAmt("true");
            this.setDisFreq("true");
            this.setDisChqFrm("true");
            this.setDisChqTo("true");
            this.setDisChqDt("true");
            this.setBtnValue("Verify");
            setFocusId("ddPendingRef");
            this.setDisDelete("false");
            getPendingReference(getOrgnBrCode());
        }
    }
    
    public void getPendingReference(String brnCode){
        try {
            List resultList = new ArrayList();
            resultList = bnkLoanFacade.getPendingRefLst(brnCode);
            pRefList = new ArrayList<SelectItem>();
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    pRefList.add(new SelectItem(ele.get(0).toString()));
                }
            } else {
                this.setMessage("There is No Pending Data exist");
            }
        }catch (Exception e){
            this.setMessage(e.getMessage());
        }
    }
    
    public void refProcess(){
        try {
            if(!(this.getPendingRef()==null || this.getPendingRef().equalsIgnoreCase(""))){
                List resultList = new ArrayList();
                resultList = bnkLoanFacade.getRefDetailList(this.getPendingRef());
                if (resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);

                        this.setCrAccountNo(ele.get(0) == null ? "" : ele.get(0).toString());
                        this.setCrAcno(ele.get(0) == null ? "" : ele.get(0).toString());
                        this.setCustName(ele.get(1) == null ? "" : ele.get(1).toString());
                        this.setDrAccNo(ele.get(2) == null ? "" : ele.get(2).toString());
                        this.setBankName(ele.get(3) == null ? "" : ele.get(3).toString());
                        this.setBranchName(ele.get(4) == null ? "" : ele.get(4).toString());
                        this.setIfscCode(ele.get(5) == null ? "" : ele.get(5).toString());
                        this.setChqFrm(ele.get(6) == null ? "" : ele.get(6).toString());
                        this.setChqTo(ele.get(7) == null ? "" : ele.get(7).toString());
                        this.setFreq(ele.get(8) == null ? "" : ele.get(8).toString());
                        this.setChqDt(ele.get(9) == null ? "" : ele.get(9).toString());
                        this.setAmt(ele.get(10) == null ? "" : ele.get(10).toString());
                        this.setFrmEnter(ele.get(11) == null ? "" : ele.get(11).toString());
                        this.setBankCode1(ele.get(13) == null ? "" : ele.get(13).toString());
                        this.setBankCode2(ele.get(14) == null ? "" : ele.get(14).toString());
                        this.setBankCode3(ele.get(15) == null ? "" : ele.get(15).toString());
                        
                        int m = 1;
                        if(freq.equalsIgnoreCase("M")){
                            m = 1;
                        } else if(freq.equalsIgnoreCase("Q")){
                            m = 3;
                        } else if(freq.equalsIgnoreCase("H")){
                            m = 6;
                        } else if(freq.equalsIgnoreCase("H")){
                            m = 12;
                        }
            
                        tableDataList = new ArrayList<ChbookDetailPojo>();
                        String chDt = ymd.format(dmy.parse(chqDt));
                        String chFr = chqFrm;
                        for(int j=0;j<(Integer.parseInt(chqTo)-Integer.parseInt(chqFrm)+1);j++){
                            ChbookDetailPojo pojo = new ChbookDetailPojo();
                            pojo.setStatus(j + 1);
                            pojo.setChqNo(chFr);
                            pojo.setChqDate(dmy.format(ymd.parse(chDt)));
                            tableDataList.add(pojo);
                            
                            chDt = CbsUtil.monthAdd(chDt, m);
                            chFr = Integer.toString(Integer.parseInt(chFr)+1);                
                        }
                    }                    
                    this.setDisRef("true");
                    this.setDisAccount("true");
                    this.setDisDrAccount("true");
                    this.setDisBankName("true");
                    this.setDisBranchName("true");
                    this.setDisIfscCode("true");
                    this.setDisAmt("true");
                    this.setDisFreq("true");
                    this.setDisChqDt("true");
                    this.setDisChqFrm("true");
                    this.setDisChqTo("true");
                    this.setDisDelete("false");
                }
            }
        }catch (Exception e){
            this.setMessage(e.getMessage());
        }
    }
    
    public void crAccountProcess(){
        this.setMessage("");
        try {
            if(this.crAccountNo == null || this.crAccountNo.equalsIgnoreCase("")){
                this.setMessage("Please fill Credit account no.");
                return;
            }
            
            if (!this.crAccountNo.equalsIgnoreCase("") && ((this.crAccountNo.length() != 12) && (this.crAccountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill proper account no.");
                return;                
            }
            
            crAcno = ftsRemote.getNewAccountNumber(this.crAccountNo);
            
            String custname = ftsRemote.ftsGetCustName(this.crAcno);
            if (custname == null || custname.equals("")) {
                this.setMessage("Custname is blank.");
                return;
            } else {
                this.setCustName(custname);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getGridToSave(){
        this.setMessage("");
        try{
            if(chqTo==null || chqTo.equalsIgnoreCase("")){
                this.setMessage("Please Fill Cheque No. To.");
                return;
            }
            
            if(Integer.parseInt(chqTo)< Integer.parseInt(chqFrm)){
                this.setMessage("Cheque No. To. Can't Be Less Cheque No. From");
                return;
            }
            
            int m = 1;
            if(freq.equalsIgnoreCase("M")){
                m = 1;
            } else if(freq.equalsIgnoreCase("Q")){
                m = 3;
            } else if(freq.equalsIgnoreCase("H")){
                m = 6;
            } else if(freq.equalsIgnoreCase("H")){
                m = 12;
            }
            
            tableDataList = new ArrayList<ChbookDetailPojo>();
            String chDt = ymd.format(dmy.parse(chqDt));
            String chFr = chqFrm;
            for(int i=0;i<(Integer.parseInt(chqTo)-Integer.parseInt(chqFrm)+1);i++){
                ChbookDetailPojo pojo = new ChbookDetailPojo();
                pojo.setStatus(i + 1);
                pojo.setChqNo(chFr);
                pojo.setChqDate(dmy.format(ymd.parse(chDt)));
                tableDataList.add(pojo);
                
                chDt = CbsUtil.monthAdd(chDt, m);
                chFr = Integer.toString(Integer.parseInt(chFr)+1);                
            }
        } catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnDeleteAction(){
        try {
            if(!(this.getPendingRef()==null || this.getPendingRef().equalsIgnoreCase(""))){
                String msg = bnkLoanFacade.deleteReference(this.getPendingRef(), getUserName());
                if(msg.equalsIgnoreCase("true")){
                    btnRefreshAction();
                    this.setMessage("Data Deleted Successfully ");
                }else{
                    this.setMessage(msg);
                }
            }else {
                this.setMessage("Please Select Pending Reference To Delete");
            }
        } catch (Exception e){
            this.setMessage(e.getMessage());
        }
    }
    
    
    public void btnSaveAction(){
        try{
            if(opt.equalsIgnoreCase("A")){
                if(this.crAcno == null || this.crAcno.equalsIgnoreCase("")){
                    this.setMessage("Please Fill Credit account no.");
                    return;
                }
            
                if (!this.crAcno.equalsIgnoreCase("") && ((this.crAcno.length() != 12))) {
                    this.setMessage("Please Fill proper account no.");
                    return;                
                }
                
                if(this.bankName == null || this.bankName.equalsIgnoreCase("")){
                    this.setMessage("Please Fill Bank Name.");
                    return;
                }
                
                if(this.branchName == null || this.branchName.equalsIgnoreCase("")){
                    this.setMessage("Please Fill Branch Name.");
                    return;
                }
                
                if(this.ifscCode == null || this.ifscCode.equalsIgnoreCase("") || ifscCode.length() !=11){
                    this.setMessage("Please Fill Proper IFSC Code.");
                    return;
                }
                
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                if (this.amt == null || this.amt.toString().equalsIgnoreCase("")) {
                    this.setMessage("Please Fill Amount Field.");
                    return;
                } else if (this.amt.toString().equalsIgnoreCase("0") || this.amt.toString().equalsIgnoreCase("0.0")) {
                    this.setMessage("Please Fill Amount Field.");
                    return;
                } else {
                    Matcher amountTxnCheck = p.matcher(this.amt.toString());
                    if (!amountTxnCheck.matches()) {
                        this.setMessage("Invalid Amount Entry.");
                        return;
                    }
                }
                if (validateAmount() != true) {
                    return;
                }
                
                if(this.chqDt == null){
                    this.setMessage("Please Fill Cheque Date.");
                    return;
                } else{
                    String chqDate = ymd.format(dmy.parse(this.chqDt));
                    if (Integer.parseInt(chqDate.substring(6)) > 31 || Integer.parseInt(chqDate.substring(6)) <= 0) {
                        this.setMessage("Invalid cheque date.");
                        return;
                    }
                    if (Integer.parseInt(chqDate.substring(4, 6)) > 12 || Integer.parseInt(chqDate.substring(4, 6)) <= 0) {
                        this.setMessage("Invalid cheque date.");
                        return;
                    }
                    
                    Long longChqDate = Long.parseLong(ymd.format(dmy.parse(chqDt)));
                    Long longTempDate = Long.parseLong(ymd.format(new Date()));
                    if (longChqDate.compareTo(longTempDate) < 0) {
                        this.setMessage("Pre Dated Cheque Is Not Allowed");
                        return;
                    }
                }
                
                if(this.chqFrm.equals("") || this.chqFrm.equals("0") || this.chqFrm.length() == 0){
                    this.setMessage("Please Fill Proper Cheque No. From");
                    return;
                }
                
                if(this.chqTo.equals("") || this.chqTo.equals("0") || this.chqTo.length() == 0){
                    this.setMessage("Please Fill Proper Cheque No. To");
                    return;
                }
                
                String msg = bnkLoanFacade.savePDCDetail(this.crAcno, custName, drAccNo, bankName, branchName, ifscCode, amt, freq, chqDt, chqFrm, chqTo, getUserName(), getOrgnBrCode(), 
                        this.getBankCode1(),this.getBankCode2(),this.getBankCode3());
                if(msg.substring(0, 4).equalsIgnoreCase("true")){
                    btnRefreshAction();
                    this.setMessage("Data Saved Successfully And Reference Number is" + msg.substring(4));
                }else{
                    this.setMessage(msg);
                }
            } else{
                if(frmEnter.equalsIgnoreCase(getUserName())){
                    this.setMessage("You Can't Authorize Your Own Entry");
                    return;
                }
                
                if(this.getPendingRef()==null || this.getPendingRef().equalsIgnoreCase("")){
                    this.setMessage("Please Select Reference To Authorize");
                    return;
                }
                
                if(tableDataList.size()>0){
                    String msg = bnkLoanFacade.verifyPDCDetail(this.pendingRef, tableDataList, this.crAcno, custName, drAccNo, bankName, 
                        branchName, ifscCode, amt, frmEnter, getUserName(), getOrgnBrCode(),this.getBankCode1(),this.getBankCode2(),this.getBankCode3());
                    if(msg.equalsIgnoreCase("true")){
                        btnRefreshAction();
                        this.setMessage("Data Verified Successfully");
                    }else{
                        this.setMessage(msg);
                    }
                } else {
                    this.setMessage("No Record To Authorize");
                    return;                    
                }
            }
        } catch (Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public boolean validateAmount() {        
        if (this.amt.toString().contains(".")) {
            if (this.amt.toString().indexOf(".") != this.amt.toString().lastIndexOf(".")) {
                this.setMessage("Invalid Amount.Please Fill The Amount Correctly.");
                return false;
            } else if (this.amt.toString().substring(amt.toString().indexOf(".") + 1).length() > 2) {
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
    
    public void btnRefreshAction(){
        this.setMessage("");
        this.setOpt("A");
        this.setBtnValue("Save");
        this.setDisRef("true");        
        this.setCrAccountNo("");
        this.setCrAcno("");
        this.setCustName("");
        this.setDrAccNo("");
        this.setBankName("");
        this.setBranchName("");
        this.setIfscCode("");
        this.setAmt("");
        this.setFreq("M");
        this.setChqFrm("");
        this.setChqTo("");
        this.setChqDt("");
        this.setDisDelete("true");
        this.setBtnMsg("Are you sure to Save the Detail");
        this.setFrmEnter("");        
        tableDataList = new ArrayList<ChbookDetailPojo>();
    }
    
    public String btnExitAction(){
        btnRefreshAction();
        return "case1";
    }
    
    public void createPopUpAlert(){
        try{
            if(opt.equalsIgnoreCase("A")){
                this.setBtnMsg("Are you sure to Save the Detail");
            } else if(opt.equalsIgnoreCase("V")){
                this.setBtnMsg("Are you sure to Verify the Detail");
            }
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void bankCode1LostFocus() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher bankCode1Check = p.matcher(this.bankCode1);
        if (!bankCode1Check.matches()) {
            this.setMessage("Please enter some Integer Value in Bank Code.");
            return;
        } else {
            this.setMessage("");
        }
    }

    public void bankCode2LostFocus() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher bankCode2Check = p.matcher(this.bankCode2);
        if (!bankCode2Check.matches()) {
            this.setMessage("Please enter some Integer Value in Bank Code.");
            return;
        } else {
            this.setMessage("");
        }
    }
    
    public boolean bankCode3LostFocus() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher bankCode3Check = p.matcher(this.bankCode3);
            if (!bankCode3Check.matches()) {
                this.setMessage("Please enter some Integer Value in Bank Code.");
                return false;
            } else {
                this.setMessage("");
            }
            List listForBankBranch = outwardClgRemote.instrUpdtDelRegBankDetail(bankCode1, bankCode2, bankCode3);
            if (listForBankBranch.isEmpty()) {
                this.setMessage("Code No. Does Not Exist,Please Add This Information Into Bank Directory.");
                return false;
            } else {
                Vector vecBankBranch = (Vector) listForBankBranch.get(0);
                this.setBankName(vecBankBranch.get(0).toString());
                this.setBranchName(vecBankBranch.get(1).toString());
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());            
        }
        return true;
    }
}
