package com.cbs.web.controller.admin;

import com.cbs.dto.npa.NPAChargesPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.npa.NPAFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Manish
 */
public class NPAChargesMaster extends BaseBean {

    private String successMsg;
    private String errorMsg;
    private String msgStyle; // pass value for Susccess = "msg" Or error = "error"
    private String acNature;
    private String acType;
    private String charge;
    private String effectiveFromDt;
    private String effectiveToDt;
    private String glHead;
    private String glHeadName;
    private String amount;
    private String buttonLabel;
    private int currentIndex;
    private boolean disableAcNature;
    private boolean disableAcType;
    private boolean disableCharge;
    private boolean disableButton;
    private List<SelectItem> acNatureOption;
    private List<SelectItem> acTypeOption;
    private List<SelectItem> chargesOption;
    private List<NPAChargesPojo> npaList;
    private CommonReportMethodsRemote common;
    private NPAFacadeRemote npaFacadeRemote;
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMsgStyle() {
        return msgStyle;
    }

    public void setMsgStyle(String msgStyle) {
        this.msgStyle = msgStyle;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getEffectiveFromDt() {
        return effectiveFromDt;
    }

    public void setEffectiveFromDt(String effectiveFromDt) {
        this.effectiveFromDt = effectiveFromDt;
    }

    public String getEffectiveToDt() {
        return effectiveToDt;
    }

    public void setEffectiveToDt(String effectiveToDt) {
        this.effectiveToDt = effectiveToDt;
    }

    public String getGlHead() {
        return glHead;
    }

    public void setGlHead(String glHead) {
        this.glHead = glHead;
    }

    public String getGlHeadName() {
        return glHeadName;
    }

    public void setGlHeadName(String glHeadName) {
        this.glHeadName = glHeadName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<SelectItem> getAcNatureOption() {
        return acNatureOption;
    }

    public void setAcNatureOption(List<SelectItem> acNatureOption) {
        this.acNatureOption = acNatureOption;
    }

    public List<SelectItem> getAcTypeOption() {
        return acTypeOption;
    }

    public void setAcTypeOption(List<SelectItem> acTypeOption) {
        this.acTypeOption = acTypeOption;
    }

    public List<SelectItem> getChargesOption() {
        return chargesOption;
    }

    public void setChargesOption(List<SelectItem> chargesOption) {
        this.chargesOption = chargesOption;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public List<NPAChargesPojo> getNpaList() {
        return npaList;
    }

    public void setNpaList(List<NPAChargesPojo> npaList) {
        this.npaList = npaList;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public boolean isDisableAcNature() {
        return disableAcNature;
    }

    public void setDisableAcNature(boolean disableAcNature) {
        this.disableAcNature = disableAcNature;
    }

    public boolean isDisableAcType() {
        return disableAcType;
    }

    public void setDisableAcType(boolean desableAcType) {
        this.disableAcType = desableAcType;
    }

    public boolean isDisableCharge() {
        return disableCharge;
    }

    public void setDisableCharge(boolean disableCharge) {
        this.disableCharge = disableCharge;
    }

    public boolean isDisableButton() {
        return disableButton;
    }

    public void setDisableButton(boolean disableButton) {
        this.disableButton = disableButton;
    }

    public NPAChargesMaster() throws ServiceLocatorException {
        common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        npaFacadeRemote = (NPAFacadeRemote) ServiceLocator.getInstance().lookup("NPAFacade");
        try {
            this.setButtonLabel("Save");
            //this.setDisableButton(true);
            getNPA();
            List tempList = common.getAcctNatureOnlyDB();
            Vector tempVector;
            if (!tempList.isEmpty()) {
                acNatureOption = new ArrayList<SelectItem>();
                acNatureOption.add(new SelectItem("--Select--"));
                acNatureOption.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acNatureOption.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void initData() {

        try {
            this.msgStyle = "";
            this.setErrorMsg("");
            acTypeOption = new ArrayList<SelectItem>();
            List tempList = common.getAcctTypeAsAcNatureOnlyDB(acNature);
            if (!tempList.isEmpty()) {
                acTypeOption.add(new SelectItem("--Select--"));
                acTypeOption.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    Vector vtr = (Vector) tempList.get(i);
                    acTypeOption.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                }
            } else {
                if (this.acNature.endsWith("0")) {
                    acTypeOption.add(new SelectItem("0", "ALL"));
                } else {
                    this.msgStyle = "error";
                    this.setErrorMsg("Acount type not found corresponding Acount nature !");
                }
            }

            List resultList2 = common.getRefRecList("402");
            if (!resultList2.isEmpty()) {
                chargesOption = new ArrayList<SelectItem>();
                chargesOption.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList2.size(); i++) {
                    Vector ele1 = (Vector) resultList2.get(i);
                    chargesOption.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void initGlName() {
        try {
            this.msgStyle = "";
            this.setErrorMsg("");
            if (!this.glHead.equals("") || this.glHead != null || this.glHead.length() != 0) {
                this.glHeadName = npaFacadeRemote.getGlName(this.glHead);
            }
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void dropDownDisable(boolean flag) {
        this.disableAcNature = flag;
        this.disableAcType = flag;
        this.disableCharge = flag;
    }

    public void changeButtonLabel() {
    }

    public void saveOrUpdate() {
        try {
            Pattern pattern = Pattern.compile("[0-9]+([.][0-9]{1,2})?");
            if (this.acNature.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setErrorMsg("Please select account nature !");
                return;
            }
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setErrorMsg("Please select account type !");
                return;
            }
            if (this.charge.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setErrorMsg("Please select charge !");
                return;
            }
            if (this.effectiveFromDt.equalsIgnoreCase("") || this.effectiveFromDt.length() == 0 || this.effectiveFromDt == null) {
                this.msgStyle = "error";
                this.setErrorMsg("Please enter effective from date !");
                return;
            }
            if (this.effectiveToDt.equalsIgnoreCase("") || this.effectiveToDt.length() == 0 || this.effectiveToDt == null) {
                this.msgStyle = "error";
                this.setErrorMsg("Please enter effective to date !");
                return;
            }
            if (!Validator.validateDate(this.effectiveFromDt)) {
                this.msgStyle = "error";
                this.setErrorMsg("Please select proper From Date");
                return;
            }
            if (!Validator.validateDate(this.effectiveToDt)) {
                this.msgStyle = "error";
                this.setErrorMsg("Please select proper To Date");
                return;
            }
            if (dmyFormat.parse(this.effectiveFromDt).compareTo(dmyFormat.parse(this.getTodayDate())) < 0 && !this.getButtonLabel().equalsIgnoreCase("Update")) {
                this.msgStyle = "error";
                this.setErrorMsg("Effective from date can not be less than current date !");
                return;
            }
            if (dmyFormat.parse(this.effectiveToDt).compareTo(dmyFormat.parse(this.getTodayDate())) < 0 && !this.getButtonLabel().equalsIgnoreCase("Update")) {
                this.msgStyle = "error";
                this.setErrorMsg("Effective to date can not be less than current date !");
                return;
            }
            if (dmyFormat.parse(this.effectiveToDt).compareTo(dmyFormat.parse(this.effectiveFromDt)) < 0) {
                this.msgStyle = "error";
                this.setErrorMsg("Effective to date can not be less than effective from date !");
                return;
            }
            if (dmyFormat.parse(this.effectiveToDt).compareTo(dmyFormat.parse(this.effectiveFromDt)) == 0) {
                this.msgStyle = "error";
                this.setErrorMsg("Effective to date can not be equal to effective from date !");
                return;
            }
            if (this.amount.equalsIgnoreCase("") || this.amount.length() == 0 || this.amount == null) {
                this.msgStyle = "error";
                this.setErrorMsg("Please enter amount !");
                return;
            }
            if (!pattern.matcher(this.amount).matches()) {
                    this.msgStyle="error";
                    this.setErrorMsg("Please enter value in currency format !");
                    return;
            }
            if (this.glHead.equalsIgnoreCase("") || this.glHead.length() == 0 || this.glHead == null) {
                this.msgStyle = "error";
                this.setErrorMsg("Please enter gl head !");
                return;
            }
            if (this.glHeadName.equalsIgnoreCase("") || this.glHeadName.length() == 0 || this.glHeadName == null) {
                this.msgStyle = "error";
                this.setErrorMsg("Gl Head name can not be null or blank !");
                return;
            }
            String efDate = this.effectiveFromDt.substring(6, 10) + "-" + this.effectiveFromDt.substring(3, 5) + "-" + this.effectiveFromDt.substring(0, 2);
            String etDate = this.effectiveToDt.substring(6, 10) + "-" + this.effectiveToDt.substring(3, 5) + "-" + this.effectiveToDt.substring(0, 2);

            if (this.buttonLabel.equalsIgnoreCase("Save") && this.disableAcNature == false && this.disableAcType == false && this.disableCharge == false) {//  and no one check box is checked
                int flag = npaFacadeRemote.insertNPAChargeMaster(this.acNature,this.acType, this.charge, efDate, etDate, this.glHead, Double.parseDouble(this.amount), this.getUserName());
                if (flag > 0) {
                    refreshForm();
                    this.msgStyle = "msg";
                    this.setErrorMsg("NPA Charge inserted successfully.");
                } else {
                    this.msgStyle = "error";
                    this.setErrorMsg("NPA Charge insertion problem !");
                }
            } else {
                boolean flag = npaFacadeRemote.updateNPAChargeMaster(npaList, this.currentIndex - 1, efDate, etDate, this.glHead, Double.parseDouble(this.amount), this.getUserName(), this.getTodayDate());
                if (flag == true) {
                    refreshForm();
                    this.msgStyle = "msg";
                    this.setErrorMsg("Updation successfully completed.");
                } else {
                    this.msgStyle = "error";
                    this.setErrorMsg("Updation problem !");
                }
            }
        } catch (ApplicationException ex) {
            this.msgStyle = "error";
            this.setErrorMsg(ex.getMessage());
        }catch(ParseException ex){
            this.msgStyle = "error";
            this.setErrorMsg(ex.getMessage());
        }

    }

    public void getNPA() {
        try {
            npaList = npaFacadeRemote.getNPAChargesMaster(this.getOrgnBrCode());
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void setNPA() {
        try {
            int index = this.currentIndex - 1;
            String acNature = common.getAcNatureByAcType(npaList.get(index).getAcType());
            if(acNature == null){
                acNature = "0";
            }
            this.setAcNature(acNature);
            initData();
            this.setAcType(npaList.get(index).getAcType());
            this.setCharge(npaList.get(index).getCharge());
            this.setEffectiveFromDt(npaList.get(index).getEffFromDt());
            this.setEffectiveToDt(npaList.get(index).getEffToDt());
            this.setAmount(npaList.get(index).getAmount() + "");
            this.setGlHead(npaList.get(index).getGlHead());
            this.setGlHeadName(npaList.get(index).getGlHeadDesc());
            this.setButtonLabel("Update");
            dropDownDisable(true);
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setErrorMsg(ex.getMessage());
        }
    }

    public int check() {
        return 0;
    }

    public void unCheck() {
    }

    public void refreshForm() {
        this.acNature = "0";
        this.acTypeOption = null;
        this.chargesOption = null;
        this.effectiveFromDt = "";
        this.effectiveToDt = "";
        this.amount = "";
        this.glHead = "";
        this.glHeadName = "";
        this.disableAcNature = false;
        this.disableAcType = false;
        this.disableCharge = false;
        this.buttonLabel = "Save";
        this.msgStyle = "";
        this.setErrorMsg("");
        getNPA();
    }

    public void clear() {
        this.msgStyle = "";
        this.setErrorMsg("");
    }

    public String exitForm() {
        return "case1";
    }
}
