package com.cbs.web.controller.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
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
 * @author Modify By Dhirendra Singh
 *
 */
public final class MiscParameters extends BaseBean {

    private GeneralMasterFacadeRemote generalFacadeRemote;
    List<SelectItem> acctDescOption;
    List<SelectItem> balTypeOption;
    List<SelectItem> gLHeadtype;
    String purpose;
    String accType;
    String charges;
    String charges1;
    String miscGLHead;
    String code1;
    String code2;
    String code3;
    String code4;
    String code5;
    Date issueDate;
    String errorMsg;
    boolean charge;
    boolean charge1;

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public boolean isCharge1() {
        return charge1;
    }

    public void setCharge1(boolean charge1) {
        this.charge1 = charge1;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getCharges1() {
        return charges1;
    }

    public void setCharges1(String charges1) {
        this.charges1 = charges1;
    }

    public String getCode5() {
        return code5;
    }

    public void setCode5(String code5) {
        this.code5 = code5;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public String getCode4() {
        return code4;
    }

    public void setCode4(String code4) {
        this.code4 = code4;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public List<SelectItem> getgLHeadtype() {
        return gLHeadtype;
    }

    public void setgLHeadtype(List<SelectItem> gLHeadtype) {
        this.gLHeadtype = gLHeadtype;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getMiscGLHead() {
        return miscGLHead;
    }

    public void setMiscGLHead(String miscGLHead) {
        this.miscGLHead = miscGLHead;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<SelectItem> getAcctDescOption() {
        return acctDescOption;
    }

    public void setAcctDescOption(List<SelectItem> acctDescOption) {
        this.acctDescOption = acctDescOption;
    }

    public List<SelectItem> getBalTypeOption() {
        return balTypeOption;
    }

    public void setBalTypeOption(List<SelectItem> balTypeOption) {
        this.balTypeOption = balTypeOption;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public MiscParameters() {
        try {
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            getAcDesc();
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void getAcDesc() {
        try {
            List l1 = generalFacadeRemote.getMiscParametersAcctCode();
            List l2 = generalFacadeRemote.getMiscGLHead();
            acctDescOption = new ArrayList<SelectItem>();
            acctDescOption.add(new SelectItem("--SELECT--"));
            acctDescOption.add(new SelectItem("INCIDENTAL CHARGES"));
            acctDescOption.add(new SelectItem("MIN. BAL. CHARGES"));
            acctDescOption.add(new SelectItem("CHEQUE BOOK CHARGES"));
            acctDescOption.add(new SelectItem("PASS BOOK CHARGES"));
            acctDescOption.add(new SelectItem("STOP PAYMENT CHARGES"));
            acctDescOption.add(new SelectItem("INSPECTION CHARGES"));
            acctDescOption.add(new SelectItem("LOCKER RENT"));
            acctDescOption.add(new SelectItem("INOPERATIVE"));
            balTypeOption = new ArrayList<SelectItem>();
            balTypeOption.add(new SelectItem("--SELECT--"));
            for (int i = 0; i < l1.size(); i++) {
                Vector v2 = (Vector) l1.get(i);
                balTypeOption.add(new SelectItem(v2.get(0).toString(), v2.get(0).toString()));
            }
            gLHeadtype = new ArrayList<SelectItem>();
            gLHeadtype.add(new SelectItem("--SELECT--"));
            for (int i = 0; i < l2.size(); i++) {
                Vector v2 = (Vector) l2.get(i);
                gLHeadtype.add(new SelectItem(v2.get(0).toString(), v2.get(1).toString()));
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void onLoadAcctTypeAndPurpose() {
        try {
            List l5 = new ArrayList();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            l5 = generalFacadeRemote.getExistingDetailMiscParameters(this.purpose, this.accType);
            if (!l5.isEmpty()) {
                Vector v5 = (Vector) l5.get(0);
                this.setCode1(v5.get(0).toString());
                this.setCode2(v5.get(1).toString());
                String date = v5.get(2).toString();
                date = date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
                this.setIssueDate(sdf.parse(date));
                String acno = v5.get(3).toString();
                this.setMiscGLHead(acno.substring(2));
                String desc = getGLHeadDesc(acno.substring(2));
                this.setCode5(desc);
                this.setCode4(acno);
            } else {
                if (this.getPurpose().equals("LOCKER RENT")) {
                    this.setCode1("0.0");
                    this.setCode2("0.0");
                }
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void setGLHeadAndDesc() {
        try {
            setCode4(getMiscGLHead());
            setCode5(getGLHeadDesc(getMiscGLHead()));
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void createSaveOrUpdate() {
        String glHead = null;
        if (this.code1 == null || code1.equalsIgnoreCase("")) {
            this.setErrorMsg("Please enter the charges field.");
            return;
        }
        if (code2 == null || code2.equalsIgnoreCase("")) {
            this.setErrorMsg("Please enter the Charges1 field.");
            return;
        }
        if (issueDate == null) {
            this.setErrorMsg("Please enter the date.");
            return;
        }
        int j = onChangeCalendar();
        if (j == 1) {
            this.setErrorMsg("Entered date is incorrect, Please select date less than current date.");
            return;
        }
        onblurCharges();
        onblurCharges1();
        try {
            if (this.code1.contains(".")) {
                if (this.code1.indexOf(".") != this.code1.lastIndexOf(".")) {
                    this.setErrorMsg("Invalid charges. Please fill the charges correctly.");
                    return;
                } else if (this.code1.substring(code1.indexOf(".") + 1).length() > 2) {
                    this.setErrorMsg("Please fill the Charges upto two decimal places.");
                    return;
                }
            }
            if (this.code2.contains(".")) {
                if (this.code2.indexOf(".") != this.code2.lastIndexOf(".")) {
                    this.setErrorMsg("Invalid charges. Please fill the charges correctly.");
                    return;
                } else if (this.code2.substring(code2.indexOf(".") + 1).length() > 2) {
                    this.setErrorMsg("Invalid charges. Please fill the charges correctly.");
                    return;
                }
            }
            glHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + this.miscGLHead;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String saveResult = generalFacadeRemote.saveMiscParametersData(purpose, this.accType, glHead, code1, formatter.format(issueDate), getUserName(), code2);
            this.setErrorMsg(saveResult);
            if (saveResult.equals("Record Saved Successfully.") || saveResult.equals("Record Updated Successfully.")) {
                this.setPurpose("--SELECT--");
                this.setAccType("--SELECT--");
                this.setMiscGLHead("--SELECT--");
                this.setCode1("");
                this.setCode2("");
                this.setCode4("");
                this.setCode5("");
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    private String getGLHeadDesc(String acNo) {
        try {
            for (SelectItem selectItem : gLHeadtype) {
                if (selectItem.getValue().toString().equals(acNo)) {
                    return selectItem.getLabel();
                }
            }
            return "";
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "";
        }
    }

    public void refresh() {
        this.setPurpose("--SELECT--");
        this.setAccType("--SELECT--");
        this.setMiscGLHead("--SELECT--");
        this.setCode1("");
        this.setCode2("");
        this.setErrorMsg("");
        this.setCode4("");
        this.setCode5("");
    }

    public int onChangeCalendar() {
        try {
            this.setErrorMsg("");
            if (getIssueDate().after(new Date())) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return Integer.MIN_VALUE;
        }
    }

    public void onChangeListBox() {
        try {
            this.setErrorMsg("");
            if (this.getPurpose().equals("LOCKER RENT")) {
                charge = true;
                charge1 = true;
            } else {
                charge = false;
                charge1 = false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void onblurCharges() {
        try {
            if (this.code1.contains(".")) {
                if (this.code1.indexOf(".") != this.code1.lastIndexOf(".")) {
                    this.setErrorMsg("Invalid charges. Please fill the charges correctly.");
                    return;
                } else if (this.code1.substring(code1.indexOf(".") + 1).length() > 2) {
                    this.setErrorMsg("Please fill the charges upto two decimal places.");
                    return;
                }
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getCode1());
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter numeric value for the charges.");
                this.setCode1("");
                return;
            }
            if (this.getCode1() == null || this.getCode1().equals("")) {
                this.setErrorMsg("Please enter the value for charges.");
                return;
            } else if (Float.parseFloat(this.getCode1()) < 0) {
                this.setErrorMsg("Please enter some positive value for charges.");
                this.setCode1("");
                return;
            } else {
                this.setErrorMsg("");
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void onblurCharges1() {
        try {
            if (this.code2.contains(".")) {
                if (this.code2.indexOf(".") != this.code2.lastIndexOf(".")) {
                    this.setErrorMsg("nvalid charges. Please fill the charges correctly.");
                    return;
                } else if (this.code2.substring(code2.indexOf(".") + 1).length() > 2) {
                    this.setErrorMsg("Please fill the charges upto two decimal places.");
                    return;
                }
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getCode2());
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter numeric value for the charges.");
                this.setCode2("");
                return;
            }
            if (this.getCode2() == null || this.getCode2().equals("")) {
                this.setErrorMsg("Please enter the value for charges.");
            } else if (Float.parseFloat(this.getCode2()) < 0) {
                this.setErrorMsg("Please enter some positive value for charges.");
                this.setCode2("");
                return;
            } else {
                this.setErrorMsg("");
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        refresh();
        return "case1";
    }
}
