/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.dto.AtmCardMappGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
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
 * @author root
 */
public class ATMKitCardMapping extends BaseBean {

    public ATMKitCardMapping() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            functionList = new ArrayList<>();
            statusList = new ArrayList<>();
            functionList.add(new SelectItem("", "-Select-"));
            if (!getOrgnBrCode().equals("90")) {
                functionList.add(new SelectItem("1", "Add"));
            }

            functionList.add(new SelectItem("2", "Modify"));
            String levelId = remoteObject.getLevelId(getUserName(), getOrgnBrCode());
            if (levelId.equals("1") || levelId.equals("2")) {
                functionList.add(new SelectItem("3", "Verify"));
            }
            statusList.add(new SelectItem("", "-Select-"));
            statusList.add(new SelectItem("A", "Active"));
            statusList.add(new SelectItem("I", "Inactive"));
            this.setBtnFlag(false);
            if (getOrgnBrCode().equals("90")) {
                setCardDisplay("");
            } else {
                setCardDisplay("none");
            }
        } catch (ApplicationException ex) {
            setMessage(ex.getMessage());
        }
    }

    public void chgFunction() {
        try {
            gridDetail = new ArrayList<>();
            this.setMessage("");
            this.setAcNo("");
            this.setCardNo("");
            this.setIssueDt(date);
            this.setMinLmt("0.0");
            this.setStFlg("");
            this.setKitNo("");
            this.setKitIssueDt(date);
            this.setNewAcNo("");
            this.setCustName("");
            this.setEmbossedName("");
            if (function.equals("")) {
                setMessage("Please Select the function");
                return;
            } else if (function.equals("1")) {
                this.btnValue = "Save";
                this.setBtnFlag(false);
            } else if (function.equals("2")) {
                this.setBtnFlag(true);
            } else if (function.equals("3")) {
                this.setBtnFlag(true);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void chenageOperation() {
        try {
            setMessage("");
            if (this.acNo.equalsIgnoreCase("") || this.acNo == null || this.acNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Account Number.");
                return;
            }
            if (!this.acNo.equalsIgnoreCase("") && ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLength()))))) {
                this.setMessage("Please Fill Proper Account Number.");
                return;
            }
            newAcNo = ftsRemote.getNewAccountNumber(acNo);
            String msg = ftsRemote.ftsAcnoValidate(this.newAcNo, 1, "");
            if (!msg.equalsIgnoreCase("true")) {
                setMessage(msg);
                return;
            }
            if (!getOrgnBrCode().equals("90") && !getOrgnBrCode().equals(ftsRemote.getCurrentBrnCode(newAcNo))) {
                setMessage("Please fill the own Branch Account");
            }
            custName = ftsRemote.getCustName(acNo);
            if (function.equals("1")) {
                gridDetail = new ArrayList<>();
                setBtnValue("Save");
            } else if (function.equals("2")) {
                gridLoad(this.getFunction(), this.getAcNo());
                setBtnValue("Update");
            } else if (function.equals("3")) {
                gridLoad(this.getFunction(), this.getAcNo());
                setBtnValue("Verify");
            }
        } catch (Exception e) {
            setNewAcNo("");
            setMessage(e.getMessage());
        }
    }

    public void gridLoad(String opt, String accNo) {
        this.setMessage("");
        gridDetail = new ArrayList<>();
        try {
            List resultList = generalFacadeRemote.gridDetailATMCard(opt, accNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    AtmCardMappGrid atm = new AtmCardMappGrid();
                    Vector ele = (Vector) resultList.get(i);

                    atm.setAcNo(ele.get(0).toString());
                    atm.setCardNo(ele.get(1).toString());
                    if(!ele.get(1).toString().equals(""))
                        atm.setIssDt(ele.get(2).toString());
                    atm.setMinLmt(ele.get(3).toString());
                    atm.setStatus(ele.get(4).toString());
                    atm.setEnterBy(ele.get(5).toString());
                    atm.setKitNo(ele.get(6).toString());
                    atm.setKitIssueDt(ele.get(7).toString());
                    atm.setEmbossingName(ele.get(8).toString());
                    gridDetail.add(atm);
                }
            } else {
                this.setMessage("There is no entry.");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void select() {
        this.setMessage("");
        try {
            this.setAcNo(currentItem.getAcNo());
            this.setNewAcNo(currentItem.getAcNo());
            this.setKitNo(currentItem.getKitNo());
            this.setKitIssueDt(sdf.parse(currentItem.getKitIssueDt()));
            this.setCardNo(currentItem.getCardNo());
            if(!currentItem.getCardNo().equals(""))
                this.setIssueDt(sdf.parse(currentItem.getIssDt()));
            this.setMinLmt(currentItem.getMinLmt());
            this.setStFlg(currentItem.getStatus());
            enter = currentItem.getEnterBy();
            oldCard = currentItem.getCardNo();
            this.setEmbossedName(currentItem.getEmbossingName());
            setCustName(ftsRemote.getCustName(currentItem.getAcNo()));
            fieldDisEnable();
            this.setBtnFlag(false);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void fieldDisEnable() {
        if (this.getFunction().equalsIgnoreCase("3")) {
            this.setAcFlag(true);
            this.setCardFlag(true);
            this.setDtFlag(true);
            this.setLmtFlag(true);
            this.setStatusFlag(true);
            this.setKitFlag(true);
            this.setKitDtFlag(true);
            this.setEmbossedNameFlg(true);
        } else {
            this.setAcFlag(false);
            this.setCardFlag(false);
            this.setDtFlag(false);
            this.setLmtFlag(false);
            this.setStatusFlag(false);
            this.setKitFlag(false);
            this.setKitDtFlag(false);
            this.setEmbossedNameFlg(false);
        }
    }

    public String validateField() {
        this.setMessage("");
        Pattern p = Pattern.compile("[0-9]*");
//      Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-][a-zA-Z0-9,]+)*");
        Pattern pAmt = Pattern.compile("([0-9]+(\\.[0-9]+)?)+");

        try {

            if ((this.function == null) || (this.getFunction().equalsIgnoreCase(""))) {
                return "Please Select Function";
            }

            if (this.acNo.equalsIgnoreCase("")) {
                return "Please fill Account Number";
            }
            Matcher matcher = p.matcher(this.acNo);
            if (!matcher.matches()) {
                return "Account Number should be in numeric digits.";
            }
            if (this.getKitNo().equals("")) {
                return "Please fill the Kit Number";
            }
            if (!getKitNo().equals("") && getKitIssueDt() == null) {
                return "Kit issue Date must not Blank";
            }
            if (getOrgnBrCode().equals("90") && getCardNo().equals("")) {
                return "Please fill the Card Number";
            }
            if (!getCardNo().equals("") && getIssueDt() == null) {
                return "Card Issue Date must not Blank";
            }
            if (this.minLmt == null || this.minLmt.toString().equalsIgnoreCase("")) {
                return "Please Fill Min Limiit";
            } else {
                matcher = pAmt.matcher(this.minLmt);
                if (!matcher.matches()) {
                    return "Min Limit should be in numeric digits.";
                }
            }

            if ((this.stFlg == null) || (this.stFlg.equalsIgnoreCase(""))) {
                return "Please Select Status.";
            }
            
            if(this.getEmbossedName() == null || this.getEmbossedName().equals("")){
                return "Please fill ATM Embossed Name";
            }
            if(this.getEmbossedName().length() >18){
                return "Length of Embossed Name must be less than or equal to 18";
            }
            if (function.equalsIgnoreCase("3")) {
                if (this.enter.equalsIgnoreCase(getUserName())) {
                    return "You can not Verify your own transaction";
                }
            }

        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "true";
    }

    public void saveMasterDetail() {
        this.setMessage("");
        try {
            String result = validateField();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            result = generalFacadeRemote.saveUpdateVerifyATMKitCard(this.getBtnValue(), this.getAcNo(), this.getCardNo(), 
                    ymd.format(this.getIssueDt()), this.getMinLmt(),this.getStFlg(), this.getUserName(), getKitNo(), 
                    ymd.format(this.getKitIssueDt()),this.oldCard, getEmbossedName());
            if (!result.equalsIgnoreCase("true")) {
                this.setMessage(result);
                return;
            }
            if (function.equalsIgnoreCase("1")) {
                refreshForm();
                this.setMessage("Data has been saved successfully.");
            } else if (function.equalsIgnoreCase("2")) {
                refreshForm();
                this.setMessage("Data has been Updated successfully.");
            } else if (function.equalsIgnoreCase("3")) {
                refreshForm();
                this.setMessage("Data has been verified successfully.");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setAcNo("");
        this.setCardNo("");
        this.setIssueDt(date);
        this.setKitNo("");
        this.setKitIssueDt(date);
        this.setMinLmt("");
        this.setStFlg("");
        this.setFunction("");
        this.setNewAcNo("");
        this.setCustName("");
        this.setEmbossedName("");
        this.btnValue = "Save";
        gridDetail = new ArrayList<>();
        fieldDisEnable();
        this.setBtnFlag(false);
    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }

    private String message;
    private String function;
    private String acNo;
    private String kitNo;
    private String cardNo;
    private String minLmt;
    private String stFlg;
    private String btnValue = "Save";
    private Date issueDt = new Date();
    private Date kitIssueDt = new Date();
    private boolean acFlag;
    private boolean cardFlag;
    private boolean dtFlag;
    private boolean lmtFlag;
    private boolean statusFlag;
    private boolean btnFlag;
    private boolean kitFlag;
    private boolean kitDtFlag;
    private boolean embossedNameFlg;
    private String enter;
    private String oldCard;
    private String cardDisplay = "";
    private String newAcNo;
    private String custName;
    private String embossedName;

    private List<SelectItem> functionList;
    private List<SelectItem> statusList;
    ShareTransferFacadeRemote remoteObject;
    private GeneralMasterFacadeRemote generalFacadeRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private AtmCardMappGrid currentItem;
    private List<AtmCardMappGrid> gridDetail;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

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

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMinLmt() {
        return minLmt;
    }

    public void setMinLmt(String minLmt) {
        this.minLmt = minLmt;
    }

    public String getStFlg() {
        return stFlg;
    }

    public void setStFlg(String stFlg) {
        this.stFlg = stFlg;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public Date getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(Date issueDt) {
        this.issueDt = issueDt;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public boolean isAcFlag() {
        return acFlag;
    }

    public void setAcFlag(boolean acFlag) {
        this.acFlag = acFlag;
    }

    public boolean isCardFlag() {
        return cardFlag;
    }

    public void setCardFlag(boolean cardFlag) {
        this.cardFlag = cardFlag;
    }

    public boolean isDtFlag() {
        return dtFlag;
    }

    public void setDtFlag(boolean dtFlag) {
        this.dtFlag = dtFlag;
    }

    public boolean isLmtFlag() {
        return lmtFlag;
    }

    public void setLmtFlag(boolean lmtFlag) {
        this.lmtFlag = lmtFlag;
    }

    public boolean isStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(boolean statusFlag) {
        this.statusFlag = statusFlag;
    }

    public boolean isBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(boolean btnFlag) {
        this.btnFlag = btnFlag;
    }

    public AtmCardMappGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AtmCardMappGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<AtmCardMappGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AtmCardMappGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getKitNo() {
        return kitNo;
    }

    public void setKitNo(String kitNo) {
        this.kitNo = kitNo;
    }

    public Date getKitIssueDt() {
        return kitIssueDt;
    }

    public void setKitIssueDt(Date kitIssueDt) {
        this.kitIssueDt = kitIssueDt;
    }

    public boolean isKitFlag() {
        return kitFlag;
    }

    public void setKitFlag(boolean kitFlag) {
        this.kitFlag = kitFlag;
    }

    public boolean isKitDtFlag() {
        return kitDtFlag;
    }

    public void setKitDtFlag(boolean kitDtFlag) {
        this.kitDtFlag = kitDtFlag;
    }

    public String getCardDisplay() {
        return cardDisplay;
    }

    public void setCardDisplay(String cardDisplay) {
        this.cardDisplay = cardDisplay;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEmbossedName() {
        return embossedName;
    }

    public void setEmbossedName(String embossedName) {
        this.embossedName = embossedName;
    }

    public boolean isEmbossedNameFlg() {
        return embossedNameFlg;
    }

    public void setEmbossedNameFlg(boolean embossedNameFlg) {
        this.embossedNameFlg = embossedNameFlg;
    }
}
