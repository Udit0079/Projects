/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentCallMaster;
import com.cbs.entity.master.Gltable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Administrator
 */
public class CallMoneyClose extends BaseBean {
    private String message;
    private String ctrlNo;
    private List<SelectItem> ctrlNoList;
    private String roi;
    private String dealDt;
    private String compDt;
    private String prinAmt;
    private String intAmt;
    private String intDrHead;
    private String intDrAmt;
    private String intCrHead;
    private String intCrAmt;
    private String totCrHead;
    private String totCrAmt;
    private String totDrHead;
    private String totDrAmt;
    private String remark;
    private String intDrName;
    private String intCrName;
    private String totCrName;
    private String totDrName;
    
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getDealDt() {
        return dealDt;
    }

    public void setDealDt(String dealDt) {
        this.dealDt = dealDt;
    }

    public String getCompDt() {
        return compDt;
    }

    public void setCompDt(String compDt) {
        this.compDt = compDt;
    }

    public String getPrinAmt() {
        return prinAmt;
    }

    public void setPrinAmt(String prinAmt) {
        this.prinAmt = prinAmt;
    }

    public String getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(String intAmt) {
        this.intAmt = intAmt;
    }

    public String getIntDrHead() {
        return intDrHead;
    }

    public void setIntDrHead(String intDrHead) {
        this.intDrHead = intDrHead;
    }

    public String getIntDrAmt() {
        return intDrAmt;
    }

    public void setIntDrAmt(String intDrAmt) {
        this.intDrAmt = intDrAmt;
    }

    public String getIntCrHead() {
        return intCrHead;
    }

    public void setIntCrHead(String intCrHead) {
        this.intCrHead = intCrHead;
    }

    public String getIntCrAmt() {
        return intCrAmt;
    }

    public void setIntCrAmt(String intCrAmt) {
        this.intCrAmt = intCrAmt;
    }

    public String getTotCrHead() {
        return totCrHead;
    }

    public void setTotCrHead(String totCrHead) {
        this.totCrHead = totCrHead;
    }

    public String getTotCrAmt() {
        return totCrAmt;
    }

    public void setTotCrAmt(String totCrAmt) {
        this.totCrAmt = totCrAmt;
    }

    public String getTotDrHead() {
        return totDrHead;
    }

    public void setTotDrHead(String totDrHead) {
        this.totDrHead = totDrHead;
    }

    public String getTotDrAmt() {
        return totDrAmt;
    }

    public void setTotDrAmt(String totDrAmt) {
        this.totDrAmt = totDrAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }    

    public String getIntDrName() {
        return intDrName;
    }

    public void setIntDrName(String intDrName) {
        this.intDrName = intDrName;
    }

    public String getIntCrName() {
        return intCrName;
    }

    public void setIntCrName(String intCrName) {
        this.intCrName = intCrName;
    }

    public String getTotCrName() {
        return totCrName;
    }

    public void setTotCrName(String totCrName) {
        this.totCrName = totCrName;
    }

    public String getTotDrName() {
        return totDrName;
    }

    public void setTotDrName(String totDrName) {
        this.totDrName = totDrName;
    }
    
    public CallMoneyClose() {
        ctrlNoList = new ArrayList<SelectItem>();
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void unAuthorizeCtrlNo() {
        ctrlNoList = new ArrayList<SelectItem>();
        try {
            List<InvestmentCallMaster> dataList = remoteObj.getUnAuthorizeCallCtrlNo();
            if (!dataList.isEmpty()) {
                ctrlNoList.add(new SelectItem("--Select--"));
                for (InvestmentCallMaster entity : dataList) {
                    ctrlNoList.add(new SelectItem(entity.getCtrlNo()));
                }
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
            InvestmentCallMaster entity = remoteObj.getCallCtrlNoDetails(Integer.parseInt(this.ctrlNo));
            if (entity != null) {
                this.setRoi(String.valueOf(entity.getRoi()));
                this.setDealDt(dmy.format(entity.getDealDt()));
                this.setCompDt(dmy.format(entity.getCompletionDt()));
                this.setPrinAmt(formatter.format(entity.getAmt()));
                this.setIntAmt(formatter.format(entity.getIntAmt()));
                
                InvestmentCallHead entityGl = remoteObj.getInvestCallHeadByCode("1");
                if (entityGl != null) {   
                    this.setIntDrHead(entityGl.getDrGlhead());
                    this.setIntDrName(acDescVal(entityGl.getDrGlhead()));
                    this.setIntDrAmt(formatter.format(entity.getIntAmt()));
                    
                    this.setIntCrHead(entityGl.getIntGlhead());
                    this.setIntCrName(acDescVal(entityGl.getIntGlhead()));
                    this.setIntCrAmt(formatter.format(entity.getIntAmt())); 
                   
                    this.setTotCrHead(entityGl.getDrGlhead());
                    this.setTotCrName(acDescVal(entityGl.getDrGlhead()));
                    this.setTotCrAmt(formatter.format(Double.parseDouble(this.getPrinAmt())+ Double.parseDouble(this.getIntAmt())));
                    
                    this.setTotDrHead(entity.getDrGlHead());
                    this.setTotDrName(acDescVal(entity.getDrGlHead()));
                    this.setTotDrAmt(formatter.format(Double.parseDouble(this.getPrinAmt())+ Double.parseDouble(this.getIntAmt()))); } else {
                    this.setMessage("Data is not found in Investment Call Head Table for Code 1.");
                }
            }else{
                resetForm();
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void resetForm() {
        this.setMessage("");
        this.setRoi("");
        this.setDealDt("");
        this.setCompDt("");
        this.setPrinAmt("");
        this.setIntAmt("");
        this.setIntDrHead("");
        this.setIntDrAmt("");
        this.setIntCrHead("");
        this.setIntCrAmt("");
        this.setTotCrHead("");
        this.setTotCrAmt("");
        this.setTotDrHead("");
        this.setTotDrAmt("");
        this.setRemark("");
        this.setIntCrName("");
        this.setIntDrName("");
        this.setTotCrName("");
        this.setTotDrName("");
        unAuthorizeCtrlNo();
        this.setCtrlNo("--Select--");
        this.setMessage("please select a control no to close !");
    }
    
    public void saveAction(){
        this.setMessage("");
        try {
            if (validate()) {                
                String msg = remoteObj.closeCallMoney(Integer.parseInt(this.ctrlNo), this.intDrHead, Double.parseDouble(this.intDrAmt), this.intCrHead, 
                 Double.parseDouble(this.intCrAmt), this.totDrHead, Double.parseDouble(this.totDrAmt), this.totCrHead, 
                 Double.parseDouble(this.totCrAmt), getUserName(), getOrgnBrCode(), this.getRemark());
                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                    resetForm();
                    this.setMessage("Record has been close successfully, Batch No: " + msg.substring(4));
                    return;
                }
            }
        }catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public boolean validate() {
        if (this.intDrAmt == null || this.intDrAmt.equals("")) {
            this.setMessage("Interest (Dr) Amount Not Exist");
            return false;
        }
        
        if (this.intCrAmt == null || this.intCrAmt.equals("")) {
            this.setMessage("Interest (Cr) Amount Not Exist");
            return false;
        }
        
        if (this.totCrAmt == null || this.totCrAmt.equals("")) {
            this.setMessage("Total (Cr) Amount Not Exist");
            return false;
        }
        
        if (this.totDrAmt == null || this.totDrAmt.equals("")) {
            this.setMessage("Total (Dr) Amount Not Exist");
            return false;
        }       
        return true;
    }
    
    public String exitBtnAction(){
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
    
    public String acDescVal(String acno) {
        String acName = "";
        try {
            Gltable entityList1 = remoteObj.getGltableByAcno(acno);
            if (entityList1 != null) {
                acName = entityList1.getAcName().toString();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return acName;
    }
}
