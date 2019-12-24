/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.constant.CbsConstant;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.pojo.NpciInputPojo;
import com.cbs.utils.ParseFileUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Administrator
 */
public class NpciInputDetail extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String micr;
    private String acType;
    private String acNo;
    private String name;
    private String amt;
    private String type;
    private String ownAcNo, accountNo, acNoLen;
    private List<SelectItem> typeList;
    private String btnLbl = "Save";
    private boolean disableBtn;
    private String btnPopUp;
    private String gridStyle;
    private List<NpciInputPojo> tableList;
    private final String jndiHomeName = "NpciMgmtFacade";
    private NpciMgmtFacadeRemote npciRemote = null;
    private CommonReportMethodsRemote commonRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private NpciInputPojo currentItem;
    private String oldUrefNo;
    private boolean disableType;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public NpciInputDetail() {
        try {
            npciRemote = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            getListValues();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void funcAction() {
        this.setMicr("");
        this.setAcType("");
        this.setAcNo("");
        this.setName("");
        this.setAmt("");
        this.setOwnAcNo("");
        this.setAccountNo("");
        this.setType("ECT");
        this.setBtnPopUp("");
        this.setMessage("");
        this.setDisableType(false);
        tableList = new ArrayList<NpciInputPojo>();
        if (this.getFunction().equalsIgnoreCase("A")) {
            this.setBtnLbl("Save");
            this.setDisableBtn(false);
            this.setBtnPopUp("Are You Sure To Save The Detail");
            this.setGridStyle("none");
        } else if (this.getFunction().equalsIgnoreCase("M")) {
            this.setBtnLbl("Update");
            this.setDisableBtn(true);
            this.setBtnPopUp("Are You Sure To Modify The Detail");
            setGridStyle("block");
        } else if (this.getFunction().equalsIgnoreCase("D")) {
            this.setBtnLbl("Delete");
            this.setDisableBtn(true);
            this.setBtnPopUp("Are You Sure To Delete The Detail");
            setGridStyle("block");
        }
    }

    public void gridDetail() {
        tableList = new ArrayList<NpciInputPojo>();
        try {
            if (function.equalsIgnoreCase("M") || function.equalsIgnoreCase("D")) {
                List<NpciInputPojo> resultList = npciRemote.getNpciInputDetails(this.getType());
                if (resultList.isEmpty()) {
                    this.setMessage("Data does not exist");
                } else {
                    for (int i = 0; i < resultList.size(); i++) {
                        NpciInputPojo npciTable = new NpciInputPojo();
                        npciTable.setuRefNo(resultList.get(i).getuRefNo());
                        npciTable.setType(resultList.get(i).getType());
                        npciTable.setMicr(resultList.get(i).getMicr());
                        npciTable.setAcType(resultList.get(i).getAcType());
                        npciTable.setAcNo(resultList.get(i).getAcNo());
                        npciTable.setName(resultList.get(i).getName());
                        npciTable.setAmount(formatter.format(Double.parseDouble(resultList.get(i).getAmount()) / 100));
                        npciTable.setOwnAcno(resultList.get(i).getOwnAcno());

                        tableList.add(npciTable);
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void processData() {
        try {
            if (currentItem != null) {
                setOldUrefNo(currentItem.getuRefNo());
                this.setMicr(currentItem.getMicr());
                this.setAcType(currentItem.getAcType());
                this.setAcNo(currentItem.getAcNo());
                this.setName(currentItem.getName());
                this.setAmt(currentItem.getAmount());
                this.setOwnAcNo(currentItem.getOwnAcno());
                this.setType(currentItem.getType());
                if ((this.getFunction().equalsIgnoreCase("M")) || (this.getFunction().equalsIgnoreCase("D"))) {
                    this.setDisableBtn(false);
                    this.setDisableType(true);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void Post() {
        this.setMessage("");
        try {
            String alphaCode = commonRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (alphaCode.equalsIgnoreCase("HO")) {
                this.setMessage("You can process from Head Office");
                return;
            }
            String result = validateDetail();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }

            String amtPaise = "";
            if (this.amt.contains(".")) {
                int pAfterDec = Integer.parseInt(this.amt.substring(this.amt.indexOf(".") + 1));
                int pBeforeDec = Integer.parseInt(this.amt.substring(0, this.amt.indexOf("."))) * 100;
                amtPaise = Integer.toString(pBeforeDec + pAfterDec);
            } else {
                amtPaise = Integer.toString(Integer.parseInt(this.amt) * 100);
            }

            result = npciRemote.npciInputSave(this.getFunction(), oldUrefNo, this.getMicr(), this.getAcType(),
                    this.getAcNo(), this.getName(), ParseFileUtil.addTrailingZeros(amtPaise, 13), this.getType(),
                    this.getUserName(), this.ownAcNo);
            if (result.equalsIgnoreCase("true")) {
                String resMsg = "";
                if (this.getFunction().equalsIgnoreCase("A")) {
                    resMsg = "Data Inserted Successfully";
                } else if (this.getFunction().equalsIgnoreCase("M")) {
                    resMsg = "Data Modified Successfully";
                } else if (this.getFunction().equalsIgnoreCase("D")) {
                    resMsg = "Data Deleted Successfully";
                }

                refresh();
                this.setMessage(resMsg);
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void retrieveValidAcno() {
        this.setMessage("");
        ownAcNo = "";
        try {
            if (this.accountNo == null || this.accountNo.equals("")) {
                this.setMessage("Own A/c No can not be blank.");
                return;
            }

            if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill proper Own A/c No.");
                return;
            }

            ownAcNo = ftsRemote.getNewAccountNumber(accountNo);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateDetail() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\[0-9]+)?)+");
        Pattern p3 = Pattern.compile("[A-Za-z0-9]+[ \t\r\n]*");
        Pattern p4 = Pattern.compile("[A-Za-z ]*");
        Pattern p5 = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        String result = "true";
        //Validate Micr Field
        if (this.micr == null || this.micr.equalsIgnoreCase("")) {
            return result = "Please fill Micr";
        }
        if (this.micr.length() < 9) {
            return result = "Please fill 9 Digit Micr";
        }

        Matcher micrCheck = p.matcher(this.micr);
        if (!micrCheck.matches()) {
            return result = "Invalid Micr";
        }

        //Validate Account Type Field
        if (this.acType == null || this.acType.equalsIgnoreCase("")) {
            return result = "Please fill A/c Type";
        }

        Matcher acTpCheck = p.matcher(this.acType);
        if (!acTpCheck.matches()) {
            return result = "Invalid A/c Type";
        }

        //Validate Account No Field
        if (this.acNo == null || this.acNo.equalsIgnoreCase("")) {
            return result = "Please fill A/c No";
        }

        Matcher acNoCheck = p3.matcher(this.acNo);
        if (!acNoCheck.matches()) {
            return result = "Invalid A/c No";
        }

        /**
         * ********
         * Validate Name Field ********
         */
        if (this.name == null || this.name.equalsIgnoreCase("")) {
            return result = "Please fill Name";
        }

        Matcher nameCheck = p4.matcher(this.name);
        if (!nameCheck.matches()) {
            return result = "Invalid Name";
        }

        /**
         * ********
         * Validate Amount Field ********
         */
        if (this.amt == null || this.amt.equalsIgnoreCase("")) {
            return result = "Please fill Amount";
        }

        if (this.amt.equalsIgnoreCase("0") || this.amt.equalsIgnoreCase("0.0")) {
            return result = "Please fill Amount";
        }

        Matcher amountCheck = p5.matcher(this.amt);
        if (!amountCheck.matches()) {
            return "Invalid amount entry.";
        }

        if (this.amt.contains(".")) {
            if (this.amt.indexOf(".") != this.amt.lastIndexOf(".")) {
                return "Invalid amount. Please fill the amount correctly.";
            } else if (this.amt.substring(this.amt.indexOf(".") + 1).length() != 2) {
                return "Please fill the amount with two decimal places.";
            } else if (this.amt.substring(this.amt.indexOf(".") + 1).length() > 2) {
                return "Please fill the amount upto two decimal places.";
            }
        }
        //Own Bank A/c No Validation
        if (this.ownAcNo == null || this.ownAcNo.equals("") || this.ownAcNo.trim().length() != 12) {
            return "Please fill Own Bank. A/c No.";
        }
        try {
            if (!ftsRemote.getCurrentBrnCode(this.ownAcNo).equalsIgnoreCase(getOrgnBrCode())) {
                return "Please fill self branch a/c no.";
            }
            if (type.equalsIgnoreCase("ECT")) { //Ecs Credit
                String valResult = ftsRemote.ftsAcnoValidate(this.ownAcNo, 0, "");
                if (!valResult.equalsIgnoreCase("true")) {
                    return valResult;
                }
                if (!(ftsRemote.getAccountNature(this.ownAcNo).equalsIgnoreCase(CbsConstant.SAVING_AC)
                        || (ftsRemote.getAccountNature(this.ownAcNo).equalsIgnoreCase(CbsConstant.CURRENT_AC)
                        && ftsRemote.getAcctTypeByCode(ftsRemote.getAccountCode(this.ownAcNo)).equalsIgnoreCase("CA"))
                        || ftsRemote.getAccountNature(this.ownAcNo).equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                    return "In case of ECS credit only saving, current and gl nature a/c are allowed in Own Bank. A/c No.";
                }
            } else if (type.equalsIgnoreCase("EDT")) {//Ecs Debit
                String valResult = ftsRemote.ftsAcnoValidate(this.ownAcNo, 1, "");
                if (!valResult.equalsIgnoreCase("true")) {
                    return valResult;
                }
                if (!(ftsRemote.getAccountNature(this.ownAcNo).equalsIgnoreCase(CbsConstant.SAVING_AC)
                        || ftsRemote.getAccountNature(this.ownAcNo).equalsIgnoreCase(CbsConstant.CURRENT_AC)
                        || ftsRemote.getAccountNature(this.ownAcNo).equalsIgnoreCase(CbsConstant.TERM_LOAN)
                        || ftsRemote.getAccountNature(this.ownAcNo).equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                    return "In case of ECS debit only saving, current and loan nature a/c are allowed in Own Bank. A/c No.";
                }
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;
    }

    public void getListValues() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("A", "Add"));
            functionList.add(new SelectItem("M", "Modify"));
            functionList.add(new SelectItem("D", "Delete"));

            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("ECT", "Credit"));
            typeList.add(new SelectItem("EDT", "Debit"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        this.setFunction("A");
        this.setMicr("");
        this.setAcType("");
        this.setAcNo("");
        this.setName("");
        this.setAmt("");
        this.setOwnAcNo("");
        this.setType("ECT");
        this.setBtnLbl("Save");
        this.setDisableBtn(false);
        this.setBtnPopUp("");
        this.setMessage("");
        this.setGridStyle("none");
        this.setOldUrefNo("");
        this.setAccountNo("");
        ownAcNo = "";
        tableList = new ArrayList<NpciInputPojo>();
        this.setDisableType(false);
    }

    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    //Getter And Setter
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

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getBtnLbl() {
        return btnLbl;
    }

    public void setBtnLbl(String btnLbl) {
        this.btnLbl = btnLbl;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public String getBtnPopUp() {
        return btnPopUp;
    }

    public void setBtnPopUp(String btnPopUp) {
        this.btnPopUp = btnPopUp;
    }

    public String getGridStyle() {
        return gridStyle;
    }

    public void setGridStyle(String gridStyle) {
        this.gridStyle = gridStyle;
    }

    public List<NpciInputPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<NpciInputPojo> tableList) {
        this.tableList = tableList;
    }

    public NpciInputPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciInputPojo currentItem) {
        this.currentItem = currentItem;
    }

    public String getOldUrefNo() {
        return oldUrefNo;
    }

    public void setOldUrefNo(String oldUrefNo) {
        this.oldUrefNo = oldUrefNo;
    }

    public boolean isDisableType() {
        return disableType;
    }

    public void setDisableType(boolean disableType) {
        this.disableType = disableType;
    }

    public String getOwnAcNo() {
        return ownAcNo;
    }

    public void setOwnAcNo(String ownAcNo) {
        this.ownAcNo = ownAcNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
}
