/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Administrator
 */
public class AuthDdPayCancel extends BaseBean {
    
    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String instNo;
    private String disableFlag;
    private List<SelectItem> instNoList;
    private boolean disInstNo;
    private String trnMode;
    private List<SelectItem> trnList;
    private String custName;
    private String seqNo;
    private String fYear;
    private String issueDt;
    private String payableAt;
    private String inFvrOf;
    private String amt;
    private String orgBranch;
    private String crHead;
    private boolean disCrHead;
    private List<SelectItem> crHeadList;
    private String crHeadname;
    private String btnLabel = "Save";
    private boolean disDelete;
    private String ddBranch;
    private String drAcNo;
    private String stat;
    private String enterBy;
    ShareTransferFacadeRemote remoteObject;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    private TransactionManagementFacadeRemote txnMgmt;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
    
    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }
    
    public String getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }
    
    public List<SelectItem> getInstNoList() {
        return instNoList;
    }

    public void setInstNoList(List<SelectItem> instNoList) {
        this.instNoList = instNoList;
    }
    
    public boolean isDisInstNo() {
        return disInstNo;
    }

    public void setDisInstNo(boolean disInstNo) {
        this.disInstNo = disInstNo;
    }

    public String getTrnMode() {
        return trnMode;
    }

    public void setTrnMode(String trnMode) {
        this.trnMode = trnMode;
    }

    public List<SelectItem> getTrnList() {
        return trnList;
    }

    public void setTrnList(List<SelectItem> trnList) {
        this.trnList = trnList;
    }
    
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }
    
    public String getPayableAt() {
        return payableAt;
    }

    public void setPayableAt(String payableAt) {
        this.payableAt = payableAt;
    }

    public String getInFvrOf() {
        return inFvrOf;
    }

    public void setInFvrOf(String inFvrOf) {
        this.inFvrOf = inFvrOf;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
    
    public String getOrgBranch() {
        return orgBranch;
    }

    public void setOrgBranch(String orgBranch) {
        this.orgBranch = orgBranch;
    }
    
    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }
    
    public boolean isDisCrHead() {
        return disCrHead;
    }

    public void setDisCrHead(boolean disCrHead) {
        this.disCrHead = disCrHead;
    }
    
    public List<SelectItem> getCrHeadList() {
        return crHeadList;
    }

    public void setCrHeadList(List<SelectItem> crHeadList) {
        this.crHeadList = crHeadList;
    }
    
    public String getCrHeadname() {
        return crHeadname;
    }

    public void setCrHeadname(String crHeadname) {
        this.crHeadname = crHeadname;
    }

    public String getBtnLabel() {
        return btnLabel;
    }

    public void setBtnLabel(String btnLabel) {
        this.btnLabel = btnLabel;
    }
    
    public boolean isDisDelete() {
        return disDelete;
    }

    public void setDisDelete(boolean disDelete) {
        this.disDelete = disDelete;
    }
    
    public String getDdBranch() {
        return ddBranch;
    }

    public void setDdBranch(String ddBranch) {
        this.ddBranch = ddBranch;
    }

    public String getDrAcNo() {
        return drAcNo;
    }

    public void setDrAcNo(String drAcNo) {
        this.drAcNo = drAcNo;
    }
    
    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }    
    
    public AuthDdPayCancel() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            txnMgmt = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");
            getOnLoadDroupDownValue();
            this.setMessage("");
            this.setDisInstNo(false);
            this.setDisCrHead(false);
            this.setDisDelete(true);
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getOnLoadDroupDownValue() {
        try {
            String levelId = remoteObject.getLevelId(getUserName(), getOrgnBrCode());
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("P", "Payment"));
            functionList.add(new SelectItem("C", "Cancel"));
            if (levelId.equals("1") || levelId.equals("2")) {
                functionList.add(new SelectItem("V", "Verify"));
            }                        
            setFunction("P"); 
            
            trnList = new ArrayList<SelectItem>();            
            trnList.add(new SelectItem("2", "Transfer"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void changeFunction(){
        try {
            instNoList = new ArrayList<SelectItem>();
            this.setCustName("");
            this.setSeqNo("");
            this.setfYear("");
            this.setIssueDt("");
            this.setPayableAt("");
            this.setInFvrOf("");
            this.setAmt("");
            this.setDdBranch("");
            this.setOrgBranch("");
            crHeadList = new ArrayList<SelectItem>();
            this.setCrHeadname("");
            this.setMessage("");
            this.setDrAcNo("");
            this.setStat("");
            this.setDisInstNo(false);
            this.setDisCrHead(false);
            this.setDisDelete(true);
            
            List inst = new ArrayList();
            inst = ftsPostRemote.getIssuedDdList(this.getFunction());
            instNoList = new ArrayList<SelectItem>();
            if(!inst.isEmpty()){
                for (int i = 0; i < inst.size(); i++) {
                    Vector ele = (Vector) inst.get(i);
                    instNoList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getInstNoInfo(){
        try {
            if(this.getInstNo()==null || this.getInstNo().equalsIgnoreCase("")){
                this.setMessage("Please Select Inst No");
                return;
            }
            List instDtl = new ArrayList();
            instDtl = ftsPostRemote.getDdInstNoDetails(this.getFunction(), this.getInstNo());
            Vector vec = (Vector) instDtl.get(0);
            if(!instDtl.isEmpty()){
                if(this.getFunction().equalsIgnoreCase("P") || this.getFunction().equalsIgnoreCase("C")){
                    this.setCustName(vec.get(0).toString());  
                    this.setSeqNo(vec.get(1).toString());
                    this.setfYear(vec.get(2).toString());
                    this.setIssueDt(vec.get(3).toString());
                    this.setPayableAt(vec.get(4).toString());
                    this.setInFvrOf(vec.get(5).toString());
                    this.setAmt(vec.get(6).toString());
                    this.setOrgBranch(vec.get(8).toString());
                    this.setDdBranch(vec.get(7).toString());
                    this.setDrAcNo(vec.get(9).toString());
                    getCrHeadNo(); 
                    this.setDisInstNo(false);
                    this.setDisCrHead(false);
                    this.setDisDelete(true);
                } else if(this.getFunction().equalsIgnoreCase("V")){
                    crHeadList = new ArrayList<SelectItem>();
                    this.setfYear(vec.get(0).toString());
                    this.setSeqNo(vec.get(1).toString());
                    this.setCustName(vec.get(3).toString());
                    this.setIssueDt(vec.get(4).toString());
                    this.setPayableAt(vec.get(5).toString());  
                    this.setInFvrOf(vec.get(6).toString());
                    this.setAmt(vec.get(7).toString());
                    this.setDdBranch(vec.get(8).toString());
                    this.setOrgBranch(vec.get(9).toString());
                    this.setStat(vec.get(10).toString());
                    this.setDrAcNo(vec.get(11).toString());
                    crHeadList.add(new SelectItem(vec.get(12).toString(), vec.get(12).toString()));
                    this.setEnterBy(vec.get(13).toString());
                    List selectGLTable = txnMgmt.selectFromGLTable(vec.get(12).toString());
                    if (selectGLTable.isEmpty()) {
                        this.setMessage("GL Head Problem");
                        this.setCrHeadname("");
                    } else {
                        Vector vecGLTable = (Vector) selectGLTable.get(0);
                        this.setCrHeadname(vecGLTable.get(0).toString());
                    }
                    this.setDisInstNo(true);
                    this.setDisCrHead(true);
                    this.setDisDelete(false);
                }
            }else{
                this.setMessage("Details Does Not Exist");
            }          
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getCrHeadNo(){
        try {            
            List crLst = new ArrayList();
            crHeadList = new ArrayList<SelectItem>();
            if(this.getFunction().equalsIgnoreCase("P")){
                String acChk = ftsPostRemote.getAcnoByPurpose("DD_CR_HEAD");
                if (!acChk.equalsIgnoreCase("")) {
                    crHeadList.add(new SelectItem(this.getOrgnBrCode() + acChk, this.getOrgnBrCode() + acChk));
                }
            }else if(this.getFunction().equalsIgnoreCase("C")){
                crLst = ftsPostRemote.getAllDdGlhead("DD");
                if(!crLst.isEmpty()){
                    for(int k=0; k<crLst.size(); k++){
                        Vector ele = (Vector) crLst.get(k);
                        crHeadList.add(new SelectItem(ddBranch + ele.get(0).toString() + "01", ddBranch + ele.get(0).toString() + "01"));
                    }
                }
            }           
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getGlHeadName(){
        try{
            if(this.getCrHead()==null || this.getCrHead().equalsIgnoreCase("")){
                this.setMessage("Please Select Credit Head");
                return;
            }
            List selectGLTable = txnMgmt.selectFromGLTable(this.getCrHead());
            if (selectGLTable.isEmpty()) {
                this.setMessage("GL Head Problem");
                this.setCrHeadname("");
            } else {
                Vector vecGLTable = (Vector) selectGLTable.get(0);
                this.setCrHeadname(vecGLTable.get(0).toString());
            }            
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }        
    }
    
    public void pageRefresh(){
        this.setFunction("P");
            instNoList = new ArrayList<SelectItem>();
            this.setCustName("");
            this.setSeqNo("");
            this.setfYear("");
            this.setIssueDt("");
            this.setPayableAt("");
            this.setInFvrOf("");
            this.setAmt("");
            this.setDdBranch("");
            this.setOrgBranch("");
            crHeadList = new ArrayList<SelectItem>();
            this.setCrHeadname("");
            this.setMessage("");
            this.setDrAcNo("");
            this.setStat("");
            this.setDisInstNo(false);
            this.setDisCrHead(false);
            this.setDisDelete(true);
    }
    
    public String btnExit_action(){
        pageRefresh();
        return "case1";
    }
    
    public void saveAction(){
        try{
            if(this.getInstNo()==null || this.getInstNo().equalsIgnoreCase("")){
                this.setMessage("Please Select Inst No");
                return;
            }
            
            if(this.getCrHeadname()==null || this.getCrHeadname().equalsIgnoreCase("")){
                this.setMessage("Credit Head Not Exist");
                return;
            }
            
            String result = "";
            if(this.getFunction().equalsIgnoreCase("P")|| this.getFunction().equalsIgnoreCase("C")){
                String stat = "";
                if(this.getFunction().equalsIgnoreCase("P")){
                    stat = "Paid";
                }else{
                    stat = "CANCELLED"; 
                }
                result = txnMgmt.saveDdPaymentCancelDetails(Integer.parseInt(this.getfYear()), Double.parseDouble(this.getSeqNo()), 
                        this.getInstNo(), this.getCustName(), this.getIssueDt(), this.getPayableAt(), this.getInFvrOf(), 
                        Double.parseDouble(this.getAmt()), this.ddBranch, this.getOrgBranch(), 
                        stat, this.getDrAcNo(), this.getCrHead(), this.getUserName(), this.getFunction(), this.getOrgnBrCode());
                pageRefresh();
            }else if (this.getFunction().equalsIgnoreCase("V")){
                if(enterBy.equalsIgnoreCase(this.getUserName())){
                    this.setMessage("You Can't Verify Your Own Entry");
                    return;
                }
                result = txnMgmt.verifyDdPaymentCancelDetails(this.getInstNo(), Double.parseDouble(this.getSeqNo()), Integer.parseInt(this.getfYear()), this.getTrnMode(),this.getOrgnBrCode(), 
                        getTodayDate(), this.getUserName());
                pageRefresh();
            } 
            this.setMessage(result);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void deleteAction(){
        try{
            if(this.getInstNo()==null || this.getInstNo().equalsIgnoreCase("")){
                this.setMessage("Please Select Inst No");
                return;
            }
            String result = "";
            result = txnMgmt.deleteDdPaymentCancelDetails(Integer.parseInt(this.getfYear()), Double.parseDouble(this.getSeqNo()), this.getInstNo(), this.getUserName());
            pageRefresh();
            this.setMessage(result);
            
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
}
