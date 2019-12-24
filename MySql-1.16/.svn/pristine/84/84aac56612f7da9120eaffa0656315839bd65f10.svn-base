/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class MutualFundCreate extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String focusId;
    private String inputDisFlag = "none";
    private String pendingSeqNo;
    private List<SelectItem> pendingSeqNoList;
    private String maxDay;
    private String amt;
    private String crAcNo;
    private String crAcNewNo;
    private String crAcDesc;
    private String drAcNo;
    private String drAcNewNo;
    private String drAcDesc;
    private String remark;
    private String btnValue = "Save";
    private boolean saveDisable;
    private String acNoLen;
    private boolean disMaxDays;
    private boolean disAmt;
    private boolean disCrAcNo;
    private boolean disDrAcNo;
    private boolean disRemark;
    private boolean deleteDisable;
    private ShareTransferFacadeRemote shareRemoteObject = null;
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private MiscReportFacadeRemote beanRemote = null;
    private InvestmentMgmtFacadeRemote invFacadeRemote = null;
    private String genDate;
    private String enterBy;
    private boolean disFunction;
    private String markingDd;
    private List<SelectItem> markList;
    private String markFlag;

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

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getInputDisFlag() {
        return inputDisFlag;
    }

    public void setInputDisFlag(String inputDisFlag) {
        this.inputDisFlag = inputDisFlag;
    }

    public String getPendingSeqNo() {
        return pendingSeqNo;
    }

    public void setPendingSeqNo(String pendingSeqNo) {
        this.pendingSeqNo = pendingSeqNo;
    }

    public List<SelectItem> getPendingSeqNoList() {
        return pendingSeqNoList;
    }

    public void setPendingSeqNoList(List<SelectItem> pendingSeqNoList) {
        this.pendingSeqNoList = pendingSeqNoList;
    }

    public String getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(String maxDay) {
        this.maxDay = maxDay;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getCrAcNo() {
        return crAcNo;
    }

    public void setCrAcNo(String crAcNo) {
        this.crAcNo = crAcNo;
    }

    public String getCrAcNewNo() {
        return crAcNewNo;
    }

    public void setCrAcNewNo(String crAcNewNo) {
        this.crAcNewNo = crAcNewNo;
    }

    public String getCrAcDesc() {
        return crAcDesc;
    }

    public void setCrAcDesc(String crAcDesc) {
        this.crAcDesc = crAcDesc;
    }

    public String getDrAcNo() {
        return drAcNo;
    }

    public void setDrAcNo(String drAcNo) {
        this.drAcNo = drAcNo;
    }

    public String getDrAcNewNo() {
        return drAcNewNo;
    }

    public void setDrAcNewNo(String drAcNewNo) {
        this.drAcNewNo = drAcNewNo;
    }

    public String getDrAcDesc() {
        return drAcDesc;
    }

    public void setDrAcDesc(String drAcDesc) {
        this.drAcDesc = drAcDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public boolean isDisMaxDays() {
        return disMaxDays;
    }

    public void setDisMaxDays(boolean disMaxDays) {
        this.disMaxDays = disMaxDays;
    }

    public boolean isDisAmt() {
        return disAmt;
    }

    public void setDisAmt(boolean disAmt) {
        this.disAmt = disAmt;
    }

    public boolean isDisCrAcNo() {
        return disCrAcNo;
    }

    public void setDisCrAcNo(boolean disCrAcNo) {
        this.disCrAcNo = disCrAcNo;
    }

    public boolean isDisDrAcNo() {
        return disDrAcNo;
    }

    public void setDisDrAcNo(boolean disDrAcNo) {
        this.disDrAcNo = disDrAcNo;
    }

    public boolean isDisRemark() {
        return disRemark;
    }

    public void setDisRemark(boolean disRemark) {
        this.disRemark = disRemark;
    }

    public boolean isDeleteDisable() {
        return deleteDisable;
    }

    public void setDeleteDisable(boolean deleteDisable) {
        this.deleteDisable = deleteDisable;
    }

    public String getGenDate() {
        return genDate;
    }

    public void setGenDate(String genDate) {
        this.genDate = genDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public boolean isDisFunction() {
        return disFunction;
    }

    public void setDisFunction(boolean disFunction) {
        this.disFunction = disFunction;
    }

    public String getMarkingDd() {
        return markingDd;
    }

    public void setMarkingDd(String markingDd) {
        this.markingDd = markingDd;
    }

    public List<SelectItem> getMarkList() {
        return markList;
    }

    public void setMarkList(List<SelectItem> markList) {
        this.markList = markList;
    }

    public String getMarkFlag() {
        return markFlag;
    }

    public void setMarkFlag(String markFlag) {
        this.markFlag = markFlag;
    }
    
    public MutualFundCreate() {
        try {
            shareRemoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            invFacadeRemote = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup("InvestmentMgmtFacade");
            this.setAcNoLen(getAcNoLength());

            String levelId = shareRemoteObject.getLevelId(getUserName(), getOrgnBrCode());

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("", "-Select-"));
            functionList.add(new SelectItem("1", "Add"));
            if (levelId.equals("1") || levelId.equals("2")) {
                functionList.add(new SelectItem("3", "Verify"));
            }
            
            markList = new ArrayList<SelectItem>();
            markList.add(new SelectItem("0", "-- Select --"));            
            markList.add(new SelectItem("HT", "Held for Trading"));
            markList.add(new SelectItem("AF", "Available for Sale"));
            
            setDeleteDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chenageOperation() {
        try {
            setMessage("");
            setMaxDay("");
            setAmt("");
            setCrAcNo("");
            setCrAcNewNo("");
            setCrAcDesc("");               
            setDrAcNo("");
            setDrAcNewNo("");
            setDrAcDesc("");
            setRemark("");
            setMarkingDd("0");
            if (function.equals("")) {
                setMessage("Please select Function.");
                return;
            } else if (function.equals("1")) {
                setInputDisFlag("none");
                setBtnValue("Save");
                setFocusId("txtMaxDays");
                setSaveDisable(false);
                setDisMaxDays(false);
                setDisAmt(false);
                setDisCrAcNo(false);
                setDisDrAcNo(false);
                setDisRemark(false);
                setDisFunction(true);
                markFlag = "false";
            } else if (function.equals("3")) {
                setInputDisFlag("");
                getUnAuthSeqNo();
                setBtnValue("Verify");
                setFocusId("ddPAcNo");
                setSaveDisable(false);
                setDisMaxDays(true);
                setDisAmt(true);
                setDisCrAcNo(true);
                setDisDrAcNo(true);
                setDisRemark(true);
                setDeleteDisable(false);
                setDisFunction(true);
                markFlag = "true";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getUnAuthSeqNo() {
        try {
            List statusList = new ArrayList();
            statusList.add(new String ("A"));
            statusList.add(new String ("A"));
            statusList.add(new String ("A"));   
            statusList.add(new String("A"));
            
            pendingSeqNoList = new ArrayList<SelectItem>();
            List list = invFacadeRemote.getUnAuthSeqList(statusList,"");            
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                pendingSeqNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getUnAuthSeqNoDetail() {
        try {
            if (!pendingSeqNo.equalsIgnoreCase("")) {
                pendingSeqNoList = new ArrayList<SelectItem>();
                List list = invFacadeRemote.getUnAuthSeqDetailList(Integer.parseInt(pendingSeqNo));
                Vector ele = (Vector) list.get(0);
                if (ele.get(0) != null) {
                    drAcNo = ele.get(0).toString();
                    drAcNewNo = ele.get(0).toString();
                    drAcDesc = beanRemote.checkAcno(ele.get(0).toString());
                }

                if (ele.get(1) != null) {
                    crAcNo = ele.get(1).toString();
                    crAcNewNo = ele.get(1).toString();
                    crAcDesc = beanRemote.checkAcno(ele.get(1).toString());
                }

                if (ele.get(2) != null) {
                    maxDay = ele.get(2).toString();
                }

                if (ele.get(3) != null) {
                    amt = ele.get(3).toString();
                }

                if (ele.get(4) != null) {
                    genDate = ele.get(4).toString();
                }

                if (ele.get(5) != null) {
                    enterBy = ele.get(5).toString();
                }

                if (ele.get(6) != null) {
                    remark = ele.get(6).toString();
                }
                
                if (ele.get(7) != null) {
                    markingDd = ele.get(7).toString();                    
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshButtonAction() {
        this.setMessage("");
        this.setFunction("");
        setInputDisFlag("none");
        setBtnValue("Save");
        setSaveDisable(false);
        setMaxDay("");
        setDisMaxDays(false);
        setAmt("");
        setDisAmt(false);
        setCrAcNo("");
        setCrAcNewNo("");
        setCrAcDesc("");
        setDisCrAcNo(false);
        setDrAcNo("");
        setDrAcNewNo("");
        setDrAcDesc("");
        setDisDrAcNo(false);
        setRemark("");
        setDisRemark(false);
        setDeleteDisable(true);
        setDisFunction(false);
        markFlag = "false";
        setMarkingDd("0");
    }

    public String btnExit() {
        refreshButtonAction();
        return "case1";
    }

    public void getNewCrAcNo() {
        try {
            if (this.crAcNo.equalsIgnoreCase("") || this.crAcNo == null || this.crAcNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Credit Account No.");
                return;
            }

            if (!this.crAcNo.equalsIgnoreCase("") && ((this.crAcNo.length() != 12) && (this.crAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please Fill Proper Credit Account Number.");
                return;
            }
            crAcNewNo = ftsPostRemote.getNewAccountNumber(crAcNo);
            crAcDesc = beanRemote.checkAcno(crAcNewNo);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getNewDrAcNo() {
        try {
            if (this.drAcNo.equalsIgnoreCase("") || this.drAcNo == null || this.drAcNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Debit Account No.");
                return;
            }

            if (!this.drAcNo.equalsIgnoreCase("") && ((this.drAcNo.length() != 12) && (this.drAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please Fill Proper Debit Account Number.");
                return;
            }
            drAcNewNo = ftsPostRemote.getNewAccountNumber(drAcNo);
            drAcDesc = beanRemote.checkAcno(drAcNewNo);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void saveAction() {
        try {
            if (function == null || function.equalsIgnoreCase("")) {
                this.setMessage("Please Select Function");
                return;
            }

            if (maxDay.equalsIgnoreCase("") || Integer.parseInt(maxDay) == 0) {
                this.setMessage("Please Fill Proper Max Days");
                return;
            }

            if (amt.equalsIgnoreCase("") || Double.parseDouble(amt) == 0.0) {
                this.setMessage("Please Fill Proper Amount");
                return;
            }

            if (this.crAcNewNo.equalsIgnoreCase("") || this.crAcNewNo == null || this.crAcNewNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Credit Account No.");
                return;
            }

            if (this.drAcNewNo.equalsIgnoreCase("") || this.drAcNewNo == null || this.drAcNewNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Debit Account No.");
                return;
            }
            
            
            // For Self Branch GL Head 
            if(!crAcNewNo.substring(0, 2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Credit Account No. of your branch");
                return;
            }
            
            if(!drAcNewNo.substring(0, 2).equalsIgnoreCase(this.getOrgnBrCode())){
                this.setMessage("Please Enter Debit Account No. of your branch");
                return;
            }
            
            if (function.equalsIgnoreCase("3") && btnValue.equalsIgnoreCase("Verify")) {
                if (pendingSeqNo == null || pendingSeqNo.equalsIgnoreCase("")) {
                    this.setMessage("Please Select Pending Sequence No.");
                    return;
                }
            }
            
            if(markingDd.equalsIgnoreCase("")){
                this.setMessage("Please Select Marking.");
                return;
            }

            if (function.equalsIgnoreCase("1") && btnValue.equalsIgnoreCase("Save")) {
                String msg = invFacadeRemote.saveMutualFundDetail(drAcNewNo, crAcNewNo, Integer.parseInt(maxDay), Double.parseDouble(amt), getUserName(), remark, markingDd);
                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                    refreshButtonAction();
                    String msg1 = "Data has been Saved successfully and Generated Seq. No is " + msg.substring(4);
                    this.setMessage(msg1);
                }
            } else if (function.equalsIgnoreCase("3") && btnValue.equalsIgnoreCase("Verify")) {
                if (enterBy.equalsIgnoreCase(getUserName())) {
                    this.setMessage("You Can't Authorize Your Own Entry.");
                    return;
                } else {
                    String msg = invFacadeRemote.verifyMutualFundDetail(Integer.parseInt(pendingSeqNo), drAcNewNo, crAcNewNo, Integer.parseInt(maxDay), Double.parseDouble(amt), genDate,
                            enterBy, getUserName(), remark, getOrgnBrCode(),markingDd);
                    if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                        refreshButtonAction();
                        String msg1 = "Data has been Verified successfully and Generated Control No is " + msg.substring(4);
                        this.setMessage(msg1);
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void deleteAction() {
        try {
            if (function.equalsIgnoreCase("")) {
                this.setMessage("Please Select Function");
                return;
            }

            if (pendingSeqNo == null || (pendingSeqNo.equalsIgnoreCase("")) || pendingSeqNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Select Pending Sequence No.");
                return;
            }

            if (maxDay.equalsIgnoreCase("") || Integer.parseInt(maxDay) == 0) {
                this.setMessage("Please Fill Proper Max Days");
                return;
            }

            if (amt.equalsIgnoreCase("") || Double.parseDouble(amt) == 0.0) {
                this.setMessage("Please Fill Proper Amount");
                return;
            }

            if (this.crAcNewNo.equalsIgnoreCase("") || this.crAcNewNo == null || this.crAcNewNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Credit Account No.");
                return;
            }

            if (this.drAcNewNo.equalsIgnoreCase("") || this.drAcNewNo == null || this.drAcNewNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Debit Account No.");
                return;
            }

            String msg = invFacadeRemote.deleteMutualFundDetail(Integer.parseInt(pendingSeqNo), getUserName());
            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                refreshButtonAction();
                String msg1 = "Data has been Deleted successfully";
                this.setMessage(msg1);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}
