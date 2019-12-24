package com.cbs.web.controller.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public final class ClearingInfo extends BaseBean {

    private GeneralMasterFacadeRemote generalFacadeRemote;
    private String circleDescription;
    private String circleMicr;
    private String bankMicr;
    private String branchMicr;
    private String owClgHead;
    private String owClgReturnHead;
    private String clgReturnCharge;
    private String owReturnCharges;
    private String iwClgHead;
    private String iwClgReturnHead;
    private String iwClgReturnCharge;
    private String iwReturnCharges;
    private String item;
    private List<SelectItem> list1;
    String message;
    boolean disableSave;
    boolean disableUpdate;
    private String result;
    private boolean flag;
    private String owclghead;
    private String owclgreturnhead;
    private String clgreturncharge;
    private String iwclghead;
    private String iwclgreturnhead;
    private String iwclgreturncharge;

    public String getOwClgReturnHead() {
        return owClgReturnHead;
    }

    public void setOwClgReturnHead(String owClgReturnHead) {
        this.owClgReturnHead = owClgReturnHead;
    }

    public String getOwClgHead() {
        return owClgHead;
    }

    public void setOwClgHead(String owClgHead) {
        this.owClgHead = owClgHead;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getIwReturnCharges() {
        return iwReturnCharges;
    }

    public void setIwReturnCharges(String iwReturnCharges) {
        this.iwReturnCharges = iwReturnCharges;
    }

    public String getOwReturnCharges() {
        return owReturnCharges;
    }

    public void setOwReturnCharges(String owReturnCharges) {
        this.owReturnCharges = owReturnCharges;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getList1() {
        return list1;
    }

    public void setList1(List<SelectItem> list1) {
        this.list1 = list1;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCircleDescription() {
        return circleDescription;
    }

    public void setCircleDescription(String circleDescription) {
        this.circleDescription = circleDescription;
    }

    public String getCircleMicr() {
        return circleMicr;
    }

    public void setCircleMicr(String circleMicr) {
        this.circleMicr = circleMicr;
    }

    public String getClgReturnCharge() {
        return clgReturnCharge;
    }

    public void setClgReturnCharge(String clgReturnCharge) {
        this.clgReturnCharge = clgReturnCharge;
    }

    public String getIwClgHead() {
        return iwClgHead;
    }

    public void setIwClgHead(String iwClgHead) {
        this.iwClgHead = iwClgHead;
    }

    public String getIwClgReturnCharge() {
        return iwClgReturnCharge;
    }

    public void setIwClgReturnCharge(String iwClgReturnCharge) {
        this.iwClgReturnCharge = iwClgReturnCharge;
    }

    public String getIwClgReturnHead() {
        return iwClgReturnHead;
    }

    public void setIwClgReturnHead(String iwClgReturnHead) {
        this.iwClgReturnHead = iwClgReturnHead;
    }

    public String getBankMicr() {
        return bankMicr;
    }

    public void setBankMicr(String bankMicr) {
        this.bankMicr = bankMicr;
    }

    public String getBranchMicr() {
        return branchMicr;
    }

    public void setBranchMicr(String branchMicr) {
        this.branchMicr = branchMicr;
    }

    public ClearingInfo() {
        try {
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            onLoad();
            this.disableSave = true;
            this.disableUpdate = true;
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onLoad() {
        try {
            list1 = new ArrayList<SelectItem>();
            list1.add(new SelectItem("--Select--"));
            List circleData = generalFacadeRemote.getCircleTypesClearingInfo();
            if(circleData.isEmpty()){
                this.setMessage("Please fill data in codebook for 101 groupcode.");
                return;
            }
            for (int i = 0; i < circleData.size(); i++) {
                Vector ele = (Vector) circleData.get(i);
                list1.add(new SelectItem(ele.get(1).toString()));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void loadCircleData() {
        try {
            this.disableSave = false;
            this.disableUpdate = false;
            this.circleDescription = "";
            this.circleMicr = "";
            this.bankMicr = "";
            this.branchMicr = "";
            this.owClgHead = "";
            this.owClgReturnHead = "";
            this.clgReturnCharge = "";
            this.owReturnCharges = "";
            this.iwClgHead = "";
            this.iwClgReturnHead = "";
            this.iwClgReturnCharge = "";
            this.iwReturnCharges = "";
            this.setMessage("");
            if (this.item.equals("--Select--")) {
                this.setMessage("Please select appropriate circle type.");
                this.disableSave = true;
                this.disableUpdate = true;
                return;
            }
            List circlData = new ArrayList();
            circlData = generalFacadeRemote.loadCircleTypeClearingInfo(this.item);
            if (circlData.isEmpty()) {
                this.setMessage("Data is not available for the selected option, Please fill the entries for cicle type. ");
                this.disableSave = false;
                this.disableUpdate = true;
            } else {
                Vector ele = (Vector) circlData.get(0);
                setCircleDescription(ele.get(1).toString());
                setCircleMicr(ele.get(2).toString());
                setBankMicr(ele.get(3).toString());
                setBranchMicr(ele.get(4).toString());
                setOwClgHead(ele.get(5).toString());
                setOwClgReturnHead(ele.get(6).toString());
                setClgReturnCharge(ele.get(7).toString());
                setOwReturnCharges(ele.get(8).toString());
                setIwClgHead(ele.get(9).toString());
                setIwClgReturnHead(ele.get(10).toString());
                setIwClgReturnCharge(ele.get(11).toString());
                setIwReturnCharges(ele.get(12).toString());
                this.disableSave = true;
                this.disableUpdate = false;
                this.flag = true;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void update() {
        try {
            this.setMessage("");
            if (this.item.equals("--Select--")) {
                this.setMessage("Selected item is invalid ,Please select appropriate option.");
                return;
            }
            if (this.circleDescription.equals("")) {
                this.setMessage("Please fill the circle description.");
                return;
            }
            if (this.circleMicr.equals("")) {
                this.setMessage("Please fill the circle micr.");
                return;
            }
            Pattern p1 = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher descCheck = p1.matcher(this.getCircleDescription());
            if (!descCheck.matches()) {
                this.setMessage("Please enter Character value for Circle Description.");
                this.setCircleDescription("");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getCircleMicr());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for circle micr.");
                this.setCircleMicr("");
                return;
            }
            if (Double.parseDouble(this.getCircleMicr()) < 0) {
                this.setMessage("Please enter positive numeric value for circle micr.");
                this.setCircleMicr("");
                return;
            }
            if (this.bankMicr.equals("")) {
                this.setMessage("Please fill the Bank Micr.");
                return;
            }
            billNoCheck = p.matcher(this.getBankMicr());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for Bank Micr.");
                this.setBankMicr("");
                return;
            }
            if (Double.parseDouble(this.getBankMicr()) < 0) {
                this.setMessage("Please enter positive numeric value for Bank Micr.");
                this.setBankMicr("");
                return;
            }
            if (this.branchMicr.equals("")) {
                this.setMessage("PLease fill the Branch Micr.");
                return;
            }
            billNoCheck = p.matcher(this.getBranchMicr());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for Branch Micr.");
                this.setBranchMicr("");
                return;
            }
            if (Double.parseDouble(this.getBranchMicr()) < 0) {
                this.setMessage("Please enter positive numeric value for Branch Micr.");
                this.setBranchMicr("");
                return;
            }
            String msg = onBlur0wClgHead();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            msg = owClgReturnHead();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            msg = owClgReturnCharge();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            if (this.getOwReturnCharges().equals("")) {
                this.setMessage("PLease fill the O/w return charges.");
                return;
            }
            billNoCheck = p.matcher(this.getOwReturnCharges());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for O/W return charges.");
                this.setOwReturnCharges("");
                return;
            }
            if (Double.parseDouble(this.getOwReturnCharges()) < 0) {
                this.setMessage("Please enter positive numeric value for O/W return charges.");
                this.setOwReturnCharges("");
                return;
            }
            msg = iwClgHead();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            msg = iwClgRetunHead();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            msg = iwClgReturnCharge();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            if (this.getIwReturnCharges().equals("")) {
                this.setMessage("Please fill the I/w return charges.");
                return;
            }
            billNoCheck = p.matcher(this.getIwReturnCharges());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for I/w return charges.");
                this.setIwReturnCharges("");
                return;
            }
            if (Double.parseDouble(this.getIwReturnCharges()) < 0) {
                this.setMessage("Please enter positive numeric value for I/w return charges.");
                this.setIwReturnCharges("");
                return;
            }
            circleDescription = this.getCircleDescription();
            circleMicr = this.getCircleMicr();
            bankMicr = this.getBankMicr();
            branchMicr = this.getBranchMicr();
            owClgHead = this.getOwClgHead();
            owClgReturnHead = this.getOwClgReturnHead();
            clgReturnCharge = this.getClgReturnCharge();
            owReturnCharges = this.getOwReturnCharges();
            iwClgHead = this.getIwClgHead();
            iwClgReturnHead = this.getIwClgReturnHead();
            iwClgReturnCharge = this.getIwClgReturnCharge();
            iwReturnCharges = this.getIwReturnCharges();
            String S1 = generalFacadeRemote.updateButtonClearingInfo(circleDescription, circleMicr, bankMicr, branchMicr, owclghead, owclgreturnhead, clgreturncharge,
                    Float.parseFloat(owReturnCharges), iwclghead, iwclgreturnhead, iwclgreturncharge, Float.parseFloat(iwReturnCharges), this.item);
            if (S1.equals("transaction successful")) {
                this.setMessage("Data has successfully updated.");
                this.circleDescription = "";
                this.circleMicr = "";
                this.bankMicr = "";
                this.branchMicr = "";
                this.owClgHead = "";
                this.owClgReturnHead = "";
                this.clgReturnCharge = "";
                this.owReturnCharges = "";
                this.iwClgHead = "";
                this.iwClgReturnHead = "";
                this.iwClgReturnCharge = "";
                this.iwReturnCharges = "";
                this.disableSave = true;
                this.disableUpdate = true;
                owclghead = "";
                owclgreturnhead = "";
                clgreturncharge = "";
                iwclghead = "";
                iwclgreturnhead = "";
                iwclgreturncharge = "";
                this.setItem("--Select--");
            } else {
                this.setMessage("Updation Not Successful.");
            }
        } catch (NumberFormatException e) {
            this.setMessage("Exception in Number Values, Please check all the Figures.");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void save() {
        try {
            this.setMessage("");
            if (this.item.equals("--Select--")) {
                this.setMessage("Please select appropriate circle type.");
                return;
            }
            if (this.circleDescription.equals("")) {
                this.setMessage("Please fill the circle description.");
                return;
            }
            if (this.circleMicr.equals("")) {
                this.setMessage("Please fill the circle micr.");
                return;
            }
            Pattern p1 = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher descCheck = p1.matcher(this.getCircleDescription());
            if (!descCheck.matches()) {
                this.setMessage("Please enter Character value for Circle Description.");
                this.setCircleDescription("");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getCircleMicr());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for circle micr.");
                this.setCircleMicr("");
                return;
            }
            if (Double.parseDouble(this.getCircleMicr()) < 0) {
                this.setMessage("Please enter positive numeric value for circle micr.");
                this.setCircleMicr("");
                return;
            }
            if (this.bankMicr.equals("")) {
                this.setMessage("Please fill the Bank Micr.");
                return;
            }
            billNoCheck = p.matcher(this.getBankMicr());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for Bank Micr.");
                this.setBankMicr("");
                return;
            }
            if (Double.parseDouble(this.getBankMicr()) < 0) {
                this.setMessage("Please enter positive numeric value for Bank Micr.");
                this.setBankMicr("");
                return;
            }
            if (this.branchMicr.equals("")) {
                this.setMessage("PLease fill the Branch Micr.");
                return;
            }
            billNoCheck = p.matcher(this.getBranchMicr());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for Branch Micr.");
                this.setBranchMicr("");
                return;
            }
            if (Double.parseDouble(this.getBranchMicr()) < 0) {
                this.setMessage("Please enter positive numeric value for Branch Micr.");
                this.setBranchMicr("");
                return;
            }
            String msg = onBlur0wClgHead();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            msg = owClgReturnHead();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            msg = owClgReturnCharge();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            if (this.getOwReturnCharges().equals("")) {
                this.setMessage("PLease fill the O/w return charges.");
                return;
            }
            billNoCheck = p.matcher(this.getOwReturnCharges());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for O/W return charges.");
                this.setOwReturnCharges("");
                return;
            }
            if (Double.parseDouble(this.getOwReturnCharges()) < 0) {
                this.setMessage("Please enter positive numeric value for O/W return charges.");
                this.setOwReturnCharges("");
                return;
            }
            msg = iwClgHead();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            msg = iwClgRetunHead();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            msg = iwClgReturnCharge();
            if (!msg.equals("true")) {
                this.setMessage(msg);
                return;
            }
            if (this.getIwReturnCharges().equals("")) {
                this.setMessage("Please fill the I/w return charges.");
                return;
            }
            billNoCheck = p.matcher(this.getIwReturnCharges());
            if (!billNoCheck.matches()) {
                this.setMessage("Please enter numeric value for I/w return charges.");
                this.setIwReturnCharges("");
                return;
            }
            if (Double.parseDouble(this.getIwReturnCharges()) < 0) {
                this.setMessage("Please enter positive numeric value for I/w return charges.");
                this.setIwReturnCharges("");
                return;
            }
            circleDescription = this.getCircleDescription();
            circleMicr = this.getCircleMicr();
            bankMicr = this.getBankMicr();
            branchMicr = this.getBranchMicr();
            owClgHead = this.getOwClgHead();
            owClgReturnHead = this.getOwClgReturnHead();
            clgReturnCharge = this.getClgReturnCharge();
            owReturnCharges = this.getOwReturnCharges();
            iwClgHead = this.getIwClgHead();
            iwClgReturnHead = this.getIwClgReturnHead();
            iwClgReturnCharge = this.getIwClgReturnCharge();
            iwReturnCharges = this.getIwReturnCharges();
            String S1 = generalFacadeRemote.saveButtonClearingInfo(this.item, circleDescription, circleMicr, bankMicr, branchMicr, owclghead, owclgreturnhead, clgreturncharge,
                    Float.parseFloat(owReturnCharges), iwclghead, iwclgreturnhead, iwclgreturncharge, Float.parseFloat(iwReturnCharges));
            if (S1.equals("transaction successful")) {
                this.setMessage("Data has successfully saved. ");
                this.circleDescription = "";
                this.circleMicr = "";
                this.bankMicr = "";
                this.branchMicr = "";
                this.owClgHead = "";
                this.owClgReturnHead = "";
                this.clgReturnCharge = "";
                this.owReturnCharges = "";
                this.iwClgHead = "";
                this.iwClgReturnHead = "";
                this.iwClgReturnCharge = "";
                this.iwReturnCharges = "";
                this.disableSave = true;
                this.disableUpdate = true;
                owclghead = "";
                owclgreturnhead = "";
                clgreturncharge = "";
                iwclghead = "";
                iwclgreturnhead = "";
                iwclgreturncharge = "";
                this.setItem("--Select--");
            } else {
                this.setMessage("Sorry,Data Could Not Be Saved !!!");
            }
        } catch (NumberFormatException e) {
            this.setMessage("Exception in Number Values, Please check all the Figures.");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        this.disableSave = false;
        this.disableUpdate = false;
        this.circleDescription = "";
        this.circleMicr = "";
        this.bankMicr = "";
        this.branchMicr = "";
        this.owClgHead = "";
        this.owClgReturnHead = "";
        this.clgReturnCharge = "";
        this.owReturnCharges = "";
        this.iwClgHead = "";
        this.iwClgReturnHead = "";
        this.iwClgReturnCharge = "";
        this.iwReturnCharges = "";
        this.setMessage("");
        this.disableSave = true;
        this.disableUpdate = true;
        this.setItem("--Select--");
    }

    public String onBlur0wClgHead() {
        try {
            this.setMessage("");
            if (getOwClgHead().equals("")) {
                result = "Please enter the O/w Clearing Head.";
                return result;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getOwClgHead());
            if (!billNoCheck.matches()) {
                result = "Please enter numeric value for O/w Clearing Head.";
                this.setOwClgHead("");
                return result;
            }
            if (Integer.parseInt(getOwClgHead()) < 0) {
                result = "O/w Clearing head can not be negative.";
                this.setOwClgHead("");
                return result;
            }
            if (Integer.parseInt(getOwClgHead()) >= 2000 && Integer.parseInt(getOwClgHead()) <= 3000) {
                result = "O/w Clearing head should not be between 2000 to 3000.";
                this.setOwClgHead("");
                return result;
            }
            Integer b = this.getOwClgHead().length();
            String cod1 = "";
            if (b == 1) {
                cod1 = "00000" + this.getOwClgHead();

            } else if (b == 2) {
                cod1 = "0000" + this.getOwClgHead();

            } else if (b == 3) {
                cod1 = "000" + this.getOwClgHead();

            } else if (b == 4) {
                cod1 = "00" + this.getOwClgHead();

            } else if (b == 5) {
                cod1 = "0" + this.getOwClgHead();

            } else {
                cod1 =  this.getOwClgHead();
            }
            String cod = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + cod1;
            String acno1 = cod + "01";

            String formName = "";
            List l1 = generalFacadeRemote.getGlTableValueClearingInfo(acno1);
            if (!l1.isEmpty()) {
                Vector v1 = (Vector) l1.get(0);
                int tmpPostFlag = Integer.parseInt(v1.get(0).toString());
                boolean postFlagValue = postFlagMast(formName, tmpPostFlag, acno1);
                if (postFlagValue == true) {
                    result = "Entry for O/w Clearing head account no is not allowed.";
                    this.setOwClgHead("");
                    return result;
                }
            } else {
                result = "O/w Clearing head account no. does not exist.";
                this.setOwClgHead("");
                return result;
            }
            owclghead = cod1;
            return "true";
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }

    public String owClgReturnHead() {
        try {
            this.setMessage("");
            if (getOwClgReturnHead().equals("")) {
                result = "Please enter the O/w Clearing return head.";
                return result;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getOwClgReturnHead());
            if (!billNoCheck.matches()) {
                result = "Please enter numeric value for O/w Clearing return head.";
                this.setOwClgReturnHead("");
                return result;
            }
            if (Integer.parseInt(getOwClgReturnHead()) < 0) {
                result = "O/w Clg return head can not be negative.";
                this.setOwClgReturnHead("");
                return result;
            }
            if (Integer.parseInt(getOwClgReturnHead()) >= 2000 && Integer.parseInt(getOwClgReturnHead()) <= 3000) {
                result = "O/w Clearing head should not be between 2000 to 3000.";
                this.setOwClgReturnHead("");
                return result;
            }
            Integer b = this.getOwClgReturnHead().length();
            String cod1 = "";
            if (b == 1) {
                cod1 = "00000" + this.getOwClgReturnHead();

            } else if (b == 2) {
                cod1 = "0000" + this.getOwClgReturnHead();

            } else if (b == 3) {
                cod1 = "000" + this.getOwClgReturnHead();

            } else if (b == 4) {
                cod1 = "00" + this.getOwClgReturnHead();

            } else if (b == 5) {
                cod1 = "0" + this.getOwClgReturnHead();

            } else {
                cod1 = this.getOwClgReturnHead();
            }
            String cod = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + cod1;
            String acno1 = cod + "01";
            String formName = "";
            List l1 = generalFacadeRemote.getGlTableValueClearingInfo(acno1);
            if (!l1.isEmpty()) {
                Vector v1 = (Vector) l1.get(0);
                int tmpPostFlag = Integer.parseInt(v1.get(0).toString());
                boolean postFlagValue = postFlagMast(formName, tmpPostFlag, acno1);
                if (postFlagValue == true) {
                    result = "Entry for this O/w Clering head account no is not allowed..";
                    this.setOwClgReturnHead("");
                    return result;
                }
            } else {
                result = "O/w Clearing head account no does not exist.";
                this.setOwClgReturnHead("");
                return result;
            }
            owclgreturnhead = cod1;
            return "true";
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            return "false";
        } catch (Exception e) {
            setMessage(e.getMessage());
            return "false";
        }
    }

    public String owClgReturnCharge() {
        try {
            this.setMessage("");
            if (getClgReturnCharge().equals("")) {
                result = "Please enter the Clearing return charge.";
                return result;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getClgReturnCharge());
            if (!billNoCheck.matches()) {
                result = "Please enter numeric value for Clearing return charge.";
                this.setClgReturnCharge("");
                return result;
            }
            if (Integer.parseInt(getClgReturnCharge()) < 0) {
                result = "Clg return charge can not be negative.";
                this.setClgReturnCharge("");
                return result;
            }
            if (Integer.parseInt(getClgReturnCharge()) <= 2500 && Integer.parseInt(getClgReturnCharge()) >= 3000) {
                result = "Clg return charge must be between 2500 to 3000.";
                this.setClgReturnCharge("");
                return result;
            }
            Integer b = this.getClgReturnCharge().length();
            String cod1 = "";
            if (b == 1) {
                cod1 = "00000" + this.getClgReturnCharge();

            } else if (b == 2) {
                cod1 = "0000" + this.getClgReturnCharge();

            } else if (b == 3) {
                cod1 = "000" + this.getClgReturnCharge();

            } else if (b == 4) {
                cod1 = "00" + this.getClgReturnCharge();

            } else if (b == 5) {
                cod1 = "0" + this.getClgReturnCharge();

            } else {
                cod1 = this.getClgReturnCharge();
            }
            String cod = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + cod1;
            String acno1 = cod + "01";
            String formName = "";
            List l1 = generalFacadeRemote.getGlTableValueClearingInfo(acno1);
            if (!l1.isEmpty()) {
                Vector v1 = (Vector) l1.get(0);
                int tmpPostFlag = Integer.parseInt(v1.get(0).toString());
                boolean postFlagValue = postFlagMast(formName, tmpPostFlag, acno1);
                if (postFlagValue == true) {
                    result = "Entry for the Clearing return charge is not allowed.";
                    this.setClgReturnCharge("");
                    return result;
                }
            } else {
                result = "Clg return charge does not exist.";
                this.setClgReturnCharge("");
                return result;
            }
            clgreturncharge = cod1;
            return "true";
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            return "false";
        } catch (Exception e) {
            setMessage(e.getMessage());
            return "false";
        }
    }

    public String iwClgHead() {
        try {
            this.setMessage("");
            if (getIwClgHead().equals("")) {
                result = "Please enter the I/W Clg head.";
                return result;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getIwClgHead());
            if (!billNoCheck.matches()) {
                result = "Please enter numeric value for I/W Clg head.";
                this.setIwClgHead("");
                return result;
            }
            if (Integer.parseInt(getIwClgHead()) < 0) {
                result = "I/W Clg head charge can not be negative.";
                this.setIwClgHead("");
                return result;
            }
            Integer b = this.getIwClgHead().length();
            String cod1 = "";
            if (b == 1) {
                cod1 = "00000" + this.getIwClgHead();

            } else if (b == 2) {
                cod1 = "0000" + this.getIwClgHead();

            } else if (b == 3) {
                cod1 = "000" + this.getIwClgHead();

            } else if (b == 4) {
                cod1 = "00" + this.getIwClgHead();

            } else if (b == 5) {
                cod1 = "0" + this.getIwClgHead();

            } else {
                cod1 = this.getIwClgHead();
            }
            String cod = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + cod1;
            String acno1 = cod + "01";
            String formName = "";
            List l1 = generalFacadeRemote.getGlTableValueClearingInfo(acno1);
            if (!l1.isEmpty()) {
                Vector v1 = (Vector) l1.get(0);
                int tmpPostFlag = Integer.parseInt(v1.get(0).toString());
                boolean postFlagValue = postFlagMast(formName, tmpPostFlag, acno1);
                if (postFlagValue == true) {
                    result = "Entry for this I/W Clg head charge is not allowed.";
                    this.setIwClgHead("");
                    return result;
                }
            } else {
                result = "I/W Clg head charge does not exist.";
                this.setIwClgHead("");
                return result;
            }
            iwclghead = cod1;
            return "true";
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            return "false";
        } catch (Exception e) {
            setMessage(e.getMessage());
            return "false";
        }
    }

    public String iwClgRetunHead() {
        try {
            this.setMessage("");
            if (getIwClgReturnHead().equals("")) {
                result = "Please enter the I/W Clg retun head.";
                return result;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getIwClgReturnHead());
            if (!billNoCheck.matches()) {
                result = "Please enter numeric value for I/W Clg return head.";
                this.setIwClgReturnHead("");
                return result;
            }
            if (Integer.parseInt(getIwClgReturnHead()) < 0) {
                result = "I/W Clg retun head charge can not be negative.";
                this.setIwClgReturnHead("");
                return result;
            }
            Integer b = this.getIwClgReturnHead().length();
            String cod1 = "";
            if (b == 1) {
                cod1 = "00000" + this.getIwClgReturnHead();

            } else if (b == 2) {
                cod1 = "0000" + this.getIwClgReturnHead();

            } else if (b == 3) {
                cod1 = "000" + this.getIwClgReturnHead();

            } else if (b == 4) {
                cod1 = "00" + this.getIwClgReturnHead();

            } else if (b == 5) {
                cod1 = "0" + this.getIwClgReturnHead();

            } else {
                cod1 = this.getIwClgReturnHead();
            }
            String cod = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + cod1;
            String acno1 = cod + "01";
            String formName = "";
            List l1 = generalFacadeRemote.getGlTableValueClearingInfo(acno1);
            if (!l1.isEmpty()) {
                Vector v1 = (Vector) l1.get(0);
                int tmpPostFlag = Integer.parseInt(v1.get(0).toString());
                boolean postFlagValue = postFlagMast(formName, tmpPostFlag, acno1);
                if (postFlagValue == true) {
                    result = "Entry for the I/W Clg retun head charge is not allowed.";
                    this.setIwClgReturnHead("");
                    return result;
                }
            } else {
                result = ("I/W Clg retun head charge does not exist.");
                this.setIwClgReturnHead("");
                return result;
            }
            iwclgreturnhead = cod1;
            return "true";
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            return "false";
        } catch (Exception e) {
            setMessage(e.getMessage());
            return "false";
        }
    }

    public String iwClgReturnCharge() {
        try {
            this.setMessage("");
            if (getIwClgReturnCharge().equals("")) {
                result = "Please enter the I/W Clg return charge.";
                return result;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getIwClgReturnCharge());
            if (!billNoCheck.matches()) {
                result = "Please enter numeric value for I/W Clg return charge.";
                this.setIwClgReturnCharge("");
                return result;
            }
            if (Integer.parseInt(getIwClgReturnCharge()) < 0) {
                result = "I/W Clg return charge head charge can not be negative.";
                this.setIwClgReturnCharge("");
                return result;
            }
            Integer b = this.getIwClgReturnCharge().length();
            String cod1 = "";
            if (b == 1) {
                cod1 = "00000" + this.getIwClgReturnCharge();

            } else if (b == 2) {
                cod1 = "0000" + this.getIwClgReturnCharge();

            } else if (b == 3) {
                cod1 = "000" + this.getIwClgReturnCharge();

            } else if (b == 4) {
                cod1 = "00" + this.getIwClgReturnCharge();

            } else if (b == 5) {
                cod1 = "0" + this.getIwClgReturnCharge();

            } else {
                cod1 =this.getIwClgReturnCharge();
            }
            String cod = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + cod1;
            String acno1 = cod + "01";
            String formName = "";
            List l1 = generalFacadeRemote.getGlTableValueClearingInfo(acno1);
            if (!l1.isEmpty()) {
                Vector v1 = (Vector) l1.get(0);
                int tmpPostFlag = Integer.parseInt(v1.get(0).toString());
                boolean postFlagValue = postFlagMast(formName, tmpPostFlag, acno1);
                if (postFlagValue == true) {
                    result = "Entry for  the I/W Clg return charge head is not allowed.";
                    this.setIwClgReturnCharge("");
                    return result;
                }
            } else {
                result = "I/W Clg return charge head does not exist.";
                this.setIwClgReturnCharge("");
                return result;
            }
            iwclgreturncharge = cod1;
            return "true";
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            return "false";
        } catch (Exception e) {
            setMessage(e.getMessage());
            return "false";
        }
    }

    public boolean postFlagMast(String postScreen, Integer postFlag, String acNo) {
        try {
            boolean postFlagValue = false;
            List l3 = generalFacadeRemote.getPostFlagClearingInfo(postScreen, postFlag, acNo);
            if (!l3.isEmpty()) {
                postFlagValue = true;
            }
            l3 = generalFacadeRemote.getPostFlag2ClearingInfo(postScreen, postFlag, acNo);
            if (l3.isEmpty()) {
                postFlagValue = true;
            } else {
                Vector v1 = (Vector) l3.get(0);
                if (Integer.parseInt(v1.get(0).toString()) == 0) {
                    postFlagValue = true;
                } else if (Integer.parseInt(v1.get(0).toString()) > 0) {
                    postFlagValue = false;
                }
            }
            l3 = generalFacadeRemote.getPostFlag1ClearingInfo(postScreen, postFlag, acNo);
            if (!l3.isEmpty()) {
                postFlagValue = true;
                return postFlagValue;
            }
            return postFlagValue;
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            return false;
        } catch (Exception e) {
            setMessage(e.getMessage());
            return false;
        }
    }

    public String exitForm() {
        refresh();
        return "case1";
    }
}
