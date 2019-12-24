/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
public class UserMaintenance extends BaseBean {

    private final String jndiHomeName = "AdminManagementFacade";
    private AdminManagementFacadeRemote beanLocal = null;

    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;

    String message;

    String userId;

    String user;

    String password;

    String reTypePassword;

    String AuthorizationLevel;

    String cashLimit;

    String clearingLimit;

    String transferLimit;

    String userAddress;

    String npciUserName;

    List authorizationList;

    boolean cashLimitValue;

    boolean clearingLimitvalue;

    boolean transferLImitValue;

    boolean createButtonValue;

    boolean updateButtonValue;

    boolean expireButtonValue;

    boolean deleteButtonValue;

    boolean activeButtonValue;

    boolean textpasswordvalue;

    boolean textRePasswordvalue;

    String result;

    boolean flag;

    private String brCode;

    private List<SelectItem> brCodeList;

    String flag1 = "false";

    String deputOrXfer;

    private List<SelectItem> deputOrXferList;

    Date fromDt;

    Date toDate;

    String operBrnCode;

    private List<SelectItem> operBrnCodeList;

    private String fromToDateFlag = "none";

    private String deputXferFlag = "none";

    String operBranchFlag = "none";

    String authorizationLevelFlag = "false";

    public String getNpciUserName() {
        return npciUserName;
    }

    public void setNpciUserName(String npciUserName) {
        this.npciUserName = npciUserName;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public List<SelectItem> getBrCodeList() {
        return brCodeList;
    }

    public void setBrCodeList(List<SelectItem> brCodeList) {
        this.brCodeList = brCodeList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isActiveButtonValue() {
        return activeButtonValue;
    }

    public void setActiveButtonValue(boolean activeButtonValue) {
        this.activeButtonValue = activeButtonValue;
    }

    public boolean isCreateButtonValue() {
        return createButtonValue;
    }

    public void setCreateButtonValue(boolean createButtonValue) {
        this.createButtonValue = createButtonValue;
    }

    public boolean isDeleteButtonValue() {
        return deleteButtonValue;
    }

    public void setDeleteButtonValue(boolean deleteButtonValue) {
        this.deleteButtonValue = deleteButtonValue;
    }

    public boolean isExpireButtonValue() {
        return expireButtonValue;
    }

    public void setExpireButtonValue(boolean expireButtonValue) {
        this.expireButtonValue = expireButtonValue;
    }

    public boolean isTextRePasswordvalue() {
        return textRePasswordvalue;
    }

    public void setTextRePasswordvalue(boolean textRePasswordvalue) {
        this.textRePasswordvalue = textRePasswordvalue;
    }

    public boolean isTextpasswordvalue() {
        return textpasswordvalue;
    }

    public void setTextpasswordvalue(boolean textpasswordvalue) {
        this.textpasswordvalue = textpasswordvalue;
    }

    public boolean isUpdateButtonValue() {
        return updateButtonValue;
    }

    public void setUpdateButtonValue(boolean updateButtonValue) {
        this.updateButtonValue = updateButtonValue;
    }

    public boolean isCashLimitValue() {
        return cashLimitValue;
    }

    public void setCashLimitValue(boolean cashLimitValue) {
        this.cashLimitValue = cashLimitValue;
    }

    public boolean isClearingLimitvalue() {
        return clearingLimitvalue;
    }

    public void setClearingLimitvalue(boolean clearingLimitvalue) {
        this.clearingLimitvalue = clearingLimitvalue;
    }

    public boolean isTransferLImitValue() {
        return transferLImitValue;
    }

    public void setTransferLImitValue(boolean transferLImitValue) {
        this.transferLImitValue = transferLImitValue;
    }

    public String getAuthorizationLevel() {
        return AuthorizationLevel;
    }

    public void setAuthorizationLevel(String AuthorizationLevel) {
        this.AuthorizationLevel = AuthorizationLevel;
    }

    public String getCashLimit() {
        return cashLimit;
    }

    public void setCashLimit(String cashLimit) {
        this.cashLimit = cashLimit;
    }

    public String getClearingLimit() {
        return clearingLimit;
    }

    public void setClearingLimit(String clearingLimit) {
        this.clearingLimit = clearingLimit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReTypePassword() {
        return reTypePassword;
    }

    public void setReTypePassword(String reTypePassword) {
        this.reTypePassword = reTypePassword;
    }

    public String getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(String transferLimit) {
        this.transferLimit = transferLimit;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getAuthorizationList() {
        return authorizationList;
    }

    public void setAuthorizationList(List authorizationList) {
        this.authorizationList = authorizationList;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getDeputOrXfer() {
        return deputOrXfer;
    }

    public void setDeputOrXfer(String deputOrXfer) {
        this.deputOrXfer = deputOrXfer;
    }

    public List<SelectItem> getDeputOrXferList() {
        return deputOrXferList;
    }

    public void setDeputOrXferList(List<SelectItem> deputOrXferList) {
        this.deputOrXferList = deputOrXferList;
    }

    public Date getFromDt() {
        return fromDt;
    }

    public void setFromDt(Date fromDt) {
        this.fromDt = fromDt;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getOperBrnCode() {
        return operBrnCode;
    }

    public void setOperBrnCode(String operBrnCode) {
        this.operBrnCode = operBrnCode;
    }

    public List<SelectItem> getOperBrnCodeList() {
        return operBrnCodeList;
    }

    public void setOperBrnCodeList(List<SelectItem> operBrnCodeList) {
        this.operBrnCodeList = operBrnCodeList;
    }

    public String getFromToDateFlag() {
        return fromToDateFlag;
    }

    public void setFromToDateFlag(String fromToDateFlag) {
        this.fromToDateFlag = fromToDateFlag;
    }

    public String getDeputXferFlag() {
        return deputXferFlag;
    }

    public void setDeputXferFlag(String deputXferFlag) {
        this.deputXferFlag = deputXferFlag;
    }

    public String getOperBranchFlag() {
        return operBranchFlag;
    }

    public void setOperBranchFlag(String operBranchFlag) {
        this.operBranchFlag = operBranchFlag;
    }

    public String getAuthorizationLevelFlag() {
        return authorizationLevelFlag;
    }

    public void setAuthorizationLevelFlag(String authorizationLevelFlag) {
        this.authorizationLevelFlag = authorizationLevelFlag;
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Creates a new instance of UserMaintenance1
     */
    public UserMaintenance() {
        try {

            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            this.setMessage("");

            createButtonValue = false;
            updateButtonValue = true;
            activeButtonValue = true;
            expireButtonValue = true;
            deleteButtonValue = true;

            authorizationList = new ArrayList<SelectItem>();
            operBrnCodeList = new ArrayList<SelectItem>();
            operBrnCodeList.add(new SelectItem("--Select--"));

            beanLocal = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            List list = beanLocal.getSuperUserLevelId(getUserName());
            if (!list.isEmpty()) {
                Vector vector = (Vector) list.get(0);
                if (Integer.parseInt(vector.get(0).toString()) == 7) {
                    this.setFlag1("true");
                    List str = getHeadOfcBranchCode();
                    fromToDateFlag = "none";
                    deputXferFlag = "none";

                    if (str.get(0).toString().equalsIgnoreCase("false")) {
                        this.setMessage("Head Office branch code does not exist in BranchMaster table.");
                        brCodeList = new ArrayList<SelectItem>();
                        brCodeList.add(new SelectItem("0", "--Select--"));
                    } else {
                        brCodeList = new ArrayList<SelectItem>();
                        brCodeList.add(new SelectItem(str.get(0).toString(), str.get(1).toString()));
                        brCode = str.get(0).toString();
                    }
                } else {
                    // List str = getHeadOfcBranchCode();
                    if (Integer.parseInt(vector.get(0).toString()) == 1 || Integer.parseInt(vector.get(0).toString()) == 2) {
                        createButtonValue = true;
                        updateButtonValue = false;
                        activeButtonValue = true;
                        expireButtonValue = true;
                        deleteButtonValue = true;
                    }
                    /*if (Integer.parseInt(vector.get(0).toString()) == 2 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                     createButtonValue = true;
                     updateButtonValue = false;
                     activeButtonValue = true;
                     expireButtonValue = true;
                     deleteButtonValue = true;
                     }*/
                    fromToDateFlag = "";
                    deputXferFlag = "";
                    this.setFlag1("false");
                    branchSelect(Integer.parseInt(vector.get(0).toString()));

                }
            }
            authorizationList();
            deputOrXferList = new ArrayList<SelectItem>();
            deputOrXferList.add(new SelectItem("0", "--Select--"));
            deputOrXferList.add(new SelectItem("Deputation", "Deputation"));
            deputOrXferList.add(new SelectItem("Transfer", "Transfer"));

            this.setFromDt(dt);
            this.setToDate(dt);
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public List getHeadOfcBranchCode() {
        List msg = new ArrayList();
        try {
            List listHeadOfc = beanLocal.getHeadOfficeBranchCode();
            if (!listHeadOfc.isEmpty()) {
                Vector vectorHeadOfc = (Vector) listHeadOfc.get(0);
                msg.add(vectorHeadOfc.get(0).toString());
                msg.add(vectorHeadOfc.get(1).toString());
            } else {
                msg.add("false");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return msg;
    }

    public void branchSelect(int levelId) {
        try {
            List resultList = new ArrayList();
            resultList = beanLocal.branchCodeCombo(getOrgnBrCode(), levelId);
            if (!resultList.isEmpty()) {
                brCodeList = new ArrayList<SelectItem>();
                brCodeList.add(new SelectItem("--Select--"));
                operBrnCodeList = new ArrayList<SelectItem>();
                operBrnCodeList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    String tempBrCode = ele.get(0).toString();
                    if (Integer.parseInt(tempBrCode) < 10) {
                        tempBrCode = "0" + tempBrCode;
                    }
                    brCodeList.add(new SelectItem(tempBrCode, ele.get(1).toString()));
                    operBrnCodeList.add(new SelectItem(tempBrCode, ele.get(1).toString()));
                }
            } else {
                brCodeList = new ArrayList<SelectItem>();
                brCodeList.add(new SelectItem("--Select--"));
                operBrnCodeList = new ArrayList<SelectItem>();
                operBrnCodeList.add(new SelectItem("--Select--"));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void authorizationList() {
        try {
            authorizationList.add(new SelectItem("0", "--Select--"));
            if (flag1.equalsIgnoreCase("true")) {
                authorizationList.add(new SelectItem("5", "DBA"));
            } else {
                List roleList = common.getRefRecList("403");
                if(roleList.isEmpty()) throw new ApplicationException("Please define the the role");
                for(int i=0; i< roleList.size(); i++){
                    Vector vect = (Vector)roleList.get(i);
                    authorizationList.add(new SelectItem(vect.get(0).toString(), vect.get(1).toString()));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void onSelectListOption() {
        try {
            if (this.AuthorizationLevel.equalsIgnoreCase("0")) {
                this.setMessage("Please select the Authorization Level.");
                return;
            }
            this.setMessage("");
            if (this.AuthorizationLevel.equalsIgnoreCase("1") || this.AuthorizationLevel.equalsIgnoreCase("2")) {
                this.cashLimitValue = false;
                this.clearingLimitvalue = false;
                this.transferLImitValue = false;
//                this.cashLimit = "";
//                this.transferLimit = "";
//                this.clearingLimit = "";
            } else {
                this.cashLimitValue = true;
                this.clearingLimitvalue = true;
                this.transferLImitValue = true;
                this.cashLimit = "0";
                this.transferLimit = "0";
                this.clearingLimit = "0";
            }
            if (this.AuthorizationLevel.equalsIgnoreCase("5")) {
                fromToDateFlag = "none";
                deputXferFlag = "none";
                this.setDeputOrXfer("0");
            } else {
                if (this.updateButtonValue == true) {
                    fromToDateFlag = "none";
                    deputXferFlag = "none";
                    this.setDeputOrXfer("0");
                } else {
                    fromToDateFlag = "";
                    deputXferFlag = "";
                }

            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void createButton() {
        try {
            setMessage("");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (flag1.equalsIgnoreCase("false")) {
                if (this.brCode.equalsIgnoreCase("0")) {
                    this.setMessage("Please select branch for which you want to create the user id.");
                    return;
                }
            }
            List str = getHeadOfcBranchCode();
            if (str.get(0).toString().equalsIgnoreCase("false")) {
                this.setMessage("Head Office branch code does not exist in BranchMaster table.");
                return;
            }

            if (getUserId().equalsIgnoreCase("")) {
                this.setMessage("User Id field can not be empty");
                return;
            }
            Pattern p3 = Pattern.compile("[A-Za-z0-9]+[ \t\r\n]*");
            if (!getUserId().equalsIgnoreCase("")) {
                Matcher billNoCheck3 = p3.matcher(this.getUserId());
                if (!billNoCheck3.matches()) {
                    this.setMessage("Please enter valid value for userid.");
                    this.setUserId("");
                    return;
                }
            }
            String res = onBlurUserName();
            if (!res.equalsIgnoreCase("true")) {
                //this.setMessage(res);
                return;
            }
            res = onBlurPassword();
            if (!res.equalsIgnoreCase("true")) {
                //this.setMessage(res);
                return;
            }
            res = onBlurRePassword();
            if (!res.equalsIgnoreCase("true")) {
                //this.setMessage(res);
                return;
            }

            if (this.getAuthorizationLevel().equalsIgnoreCase("0")) {
                this.setMessage("Please select the Authorization Level.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (!getCashLimit().equalsIgnoreCase("")) {
                Matcher billNoCheck = p.matcher(this.getCashLimit());
                if (!billNoCheck.matches()) {
                    this.setMessage("Please enter numeric value for Cash Limit.");
                    this.setCashLimit("");
                    return;
                }
                if (Double.parseDouble(getCashLimit()) < 0) {
                    this.setMessage("Please enter positive value for Cash Limit.");
                    this.setCashLimit("");
                    return;
                }
            }
            if (!getClearingLimit().equalsIgnoreCase("")) {
                Matcher billNoCheck = p.matcher(this.getClearingLimit());
                if (!billNoCheck.matches()) {
                    this.setMessage("Please enter numeric value for Clearing Limit.");
                    this.setClearingLimit("");
                    return;
                }
                if (Double.parseDouble(getClearingLimit()) < 0) {
                    this.setMessage("Please enter positive value for Clearing Limit.");
                    this.setClearingLimit("");
                    return;
                }
            }
            if (!getTransferLimit().equalsIgnoreCase("")) {
                Matcher billNoCheck = p.matcher(this.getTransferLimit());
                if (!billNoCheck.matches()) {
                    this.setMessage("Please enter numeric value for Transfer Limit.");
                    this.setTransferLimit("");
                    return;
                }
                if (Double.parseDouble(getTransferLimit()) < 0) {
                    this.setMessage("Please enter positive value for TransferLimit..");
                    this.setTransferLimit("");
                    return;
                }
            }
            String newCashLimitSend = "";
            String newClearingLimitSend = "";
            String newTransfreDebitSend = "";
            //Integer authLevel = 0;
            String newUserAddressSend = "";
         //   authLevel = Integer.parseInt(AuthorizationLevel);
//            if (AuthorizationLevel.equalsIgnoreCase("1")) {
//                authLevel = 1;
//            } else if (AuthorizationLevel.equalsIgnoreCase("2")) {
//                authLevel = 2;
//            } else if (AuthorizationLevel.equalsIgnoreCase("3")) {
//                authLevel = 3;
//            } else if (AuthorizationLevel.equalsIgnoreCase("4")) {
//                authLevel = 4;
//            } else if (AuthorizationLevel.equalsIgnoreCase("6")) {
//                authLevel = 6;
//            } else if (AuthorizationLevel.equalsIgnoreCase("99")) {
//                authLevel = 99;
//            } else if (AuthorizationLevel.equalsIgnoreCase("5")) {
//                authLevel = 5;
//            } else {
//                authLevel = 4;
//            }
            if (getCashLimit().equalsIgnoreCase("")) {
                newCashLimitSend = "";
            } else {
                newCashLimitSend = getCashLimit();
            }
            if (getClearingLimit().equalsIgnoreCase("")) {
                newClearingLimitSend = "";
            } else {
                newClearingLimitSend = getClearingLimit();
            }
            if (getTransferLimit().equalsIgnoreCase("")) {
                newTransfreDebitSend = "";
            } else {
                newTransfreDebitSend = getTransferLimit();
            }
            if (getUserAddress().equalsIgnoreCase("")) {
                newUserAddressSend = "";
            } else {
                newUserAddressSend = getUserAddress();
            }
            String testflag = "create";
            String s1 = beanLocal.allButtonFunction(getUserId(), getUser(), getPassword(), Integer.parseInt(AuthorizationLevel), newCashLimitSend,
                    newClearingLimitSend, newTransfreDebitSend, newUserAddressSend, testflag, getUserName(), this.brCode, this.password, this.reTypePassword, sdf.format(toDate), sdf.format(fromDt), this.operBrnCode, this.deputOrXfer, this.getNpciUserName());

            if (s1 == null || s1.equalsIgnoreCase("") || s1.length() == 0) {
                this.setMessage("System Error Occured !!!");
                refresh1();
            } else {
                this.setMessage(s1);
                refresh1();
            }
        } catch (ApplicationException e) {
            refreshPage();
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            refreshPage();
            this.setMessage("Please check all the text field values entered and then try again to create UserId.");
        }
    }

    public void updateButton() {
        try {
            String newCashLimitSend = "";
            String newClearingLimitSend = "";
            String newTransfreDebitSend = "";
          //  Integer authLevel = 0;
            String newUserAddressSend = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            if (AuthorizationLevel.equalsIgnoreCase("1")) {
//                authLevel = 1;
//            } else if (AuthorizationLevel.equalsIgnoreCase("2")) {
//                authLevel = 2;
//            } else if (AuthorizationLevel.equalsIgnoreCase("3")) {
//                authLevel = 3;
//            } else if (AuthorizationLevel.equalsIgnoreCase("4")) {
//                authLevel = 4;
//            } else if (AuthorizationLevel.equalsIgnoreCase("6")) {
//                authLevel = 6;
//            } else if (AuthorizationLevel.equalsIgnoreCase("99")) {
//                authLevel = 99;
//            } else if (AuthorizationLevel.equalsIgnoreCase("5")) {
//                authLevel = 5;
//            } else {
//                authLevel = 4;
//            }
            if (flag1.equalsIgnoreCase("false")) {
                if (this.brCode.equalsIgnoreCase("0")) {
                    this.setMessage("Please select branch for which you want to update the record.");
                    return;
                }
            }

            if (getUserId().equals("")) {
                this.setMessage("User Id fField can not be empty");
                return;
            }
            String res = onBlurUserName();
            if (!res.equalsIgnoreCase("true")) {
                this.setMessage(res);
                return;
            }

            if (!this.password.equalsIgnoreCase("") || this.password != null) {
                if (!password.toUpperCase().equals(reTypePassword.toUpperCase())) {
                    this.setMessage("ReTypePassword Field  and NewPassword Should Be Same");
                    return;
                }
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (!getCashLimit().equalsIgnoreCase("")) {
                Matcher billNoCheck = p.matcher(this.getCashLimit());
                if (!billNoCheck.matches()) {
                    this.setMessage("Please enter numeric value for Cash Limit.");
                    this.setCashLimit("");
                    return;
                }
                if (Double.parseDouble(getCashLimit()) < 0) {
                    this.setMessage("Please enter positive value for Cash Limit.");
                    this.setCashLimit("");
                    return;
                }
            }
            if (!getClearingLimit().equalsIgnoreCase("")) {
                Matcher billNoCheck = p.matcher(this.getClearingLimit());
                if (!billNoCheck.matches()) {
                    this.setMessage("Please enter numeric value for Clearing Limit.");
                    this.setClearingLimit("");
                    return;
                }
                if (Double.parseDouble(getClearingLimit()) < 0) {
                    this.setMessage("Please enter positive value for Clearing Limit.");
                    this.setClearingLimit("");
                    return;
                }
            }
            if (!getTransferLimit().equalsIgnoreCase("")) {
                Matcher billNoCheck = p.matcher(this.getTransferLimit());
                if (!billNoCheck.matches()) {
                    this.setMessage("Please enter numeric value for Transfer Limit.");
                    this.setTransferLimit("");
                    return;
                }
                if (Double.parseDouble(getTransferLimit()) < 0) {
                    this.setMessage("Please enter positive value for Transfer Limit.");
                    this.setTransferLimit("");
                    return;
                }
            }
            if (getCashLimit().equalsIgnoreCase("")) {
                newCashLimitSend = "";
            } else {
                newCashLimitSend = getCashLimit();
            }
            if (getClearingLimit().equalsIgnoreCase("")) {
                newClearingLimitSend = "";
            } else {
                newClearingLimitSend = getClearingLimit();
            }
            if (getTransferLimit().equalsIgnoreCase("")) {
                newTransfreDebitSend = "";
            } else {
                newTransfreDebitSend = getTransferLimit();
            }
            if (getUserAddress().equalsIgnoreCase("")) {
                newUserAddressSend = "";
            } else {
                newUserAddressSend = getUserAddress();
            }

            if (AuthorizationLevel.equalsIgnoreCase("0")) {
                this.setMessage("Please select 	Authorization Level");
                return;
            }

            List list2 = beanLocal.getSuperUserLevelId(getUserName());
            Vector vector = (Vector) list2.get(0);
            List str = getHeadOfcBranchCode();
            if (Integer.parseInt(vector.get(0).toString()) == 1 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                if (this.AuthorizationLevel.equals("1")) {
                    this.setMessage("You can't change the authorization level as Manager.Please contact to the authorized person. ");
                    return;
                }
            }
            if (!this.AuthorizationLevel.equals("5")) {
                if (this.deputOrXfer.equalsIgnoreCase("Transfer") && this.operBrnCode.equalsIgnoreCase("--Select--")) {
                    this.setMessage("If you want to Transfer then please select operational branch");
                    return;
                }
                if (this.deputOrXfer.equalsIgnoreCase("Deputation")) {
                    String currentDate = sdf.format(new Date());
                    String fromDate = sdf.format(fromDt);
                    String toDt = sdf.format(toDate);
                    if (this.operBrnCode.equalsIgnoreCase("--Select--")) {
                        this.setMessage("If you want to Depute then please select operational branch");
                        return;
                    }
                    if (Long.parseLong(fromDate) < Long.parseLong(currentDate)) {
                        this.setMessage("From Date can not be less then Current Date.");
                        return;
                    }
                    if (Long.parseLong(toDt) < Long.parseLong(currentDate)) {
                        this.setMessage("To Date can not be less then Current Date.");
                        return;
                    }
                    if (Long.parseLong(toDt) < Long.parseLong(fromDate)) {
                        this.setMessage("To Date can not be less then From Date.");
                        return;
                    }
                }
            }
            if (this.AuthorizationLevel.equals("5") && !this.deputOrXfer.equalsIgnoreCase("0") && this.deputXferFlag.equalsIgnoreCase("")) {
                this.setMessage("Selected authorization level can not be depute or transfer.");
                return;
            }

            String testflag = "update";
            String s1 = beanLocal.allButtonFunction(getUserId(), getUser(), "", Integer.parseInt(AuthorizationLevel), newCashLimitSend, newClearingLimitSend,
                    newTransfreDebitSend, newUserAddressSend, testflag, getUserName(), this.brCode, this.password, this.reTypePassword, sdf.format(toDate), sdf.format(fromDt), this.operBrnCode, this.deputOrXfer, npciUserName);
            if (s1 == null || s1.equalsIgnoreCase("") || s1.length() == 0) {
                refresh1();
            } else {
                if (!s1.equalsIgnoreCase("User id updated successfully.")) {
                    this.setMessage(s1);
                    //refresh1();
                    this.setPassword("");
                    this.setReTypePassword("");
                    this.setAuthorizationLevelFlag("false");
                } else {
                    this.setMessage(s1);
                    refresh1();
                    this.setDeputOrXfer("0");
                    this.setOperBrnCode("0");
                    this.setFromDt(new Date());
                    this.setToDate(new Date());
                }
            }
        } catch (ApplicationException e) {
            refreshPage();
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            refreshPage();
            this.setMessage("Please check all the text fields value Entered ,and try to update again..");
        }
    }

    public void active() {
        try {
            //String hostName = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (this.brCode.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select branch.");
                return;
            }
            if (this.getUserId() == null || this.getUserId().equalsIgnoreCase("") || this.getUserId().length() == 0) {
                this.setMessage("Please enter user ID.");
                return;
            }
            String testflag = "Activate";
            String s1 = beanLocal.allButtonFunction(getUserId(), "", "", 0, "", "", "", "", testflag, getUserName(), this.brCode, null, null, sdf.format(toDate), sdf.format(fromDt), this.operBrnCode, this.deputOrXfer, npciUserName);
            if (s1 == null || s1.equalsIgnoreCase("") || s1.length() == 0) {
                this.setMessage("System error occured !!!");
                refreshPage();
                return;
            }
            if (s1.equalsIgnoreCase("User ID is Activated Successfully")) {
                this.setMessage("UserId is activated successfully");
                refresh1();
                this.setAuthorizationLevelFlag("false");
            } else {
                this.setMessage("UserId could not activated successfully");
                refresh1();
            }
        } catch (ApplicationException e) {
            refreshPage();
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            refreshPage();
            this.setMessage("Please check all the text fields values and then try to activate again.");
        }
    }

    public void expire() {
        try {
            //String hostName = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String testflag = "Expire";
            if (this.brCode.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select branch.");
                return;
            }
            if (this.getUserId() == null || this.getUserId().equalsIgnoreCase("") || this.getUserId().length() == 0) {
                this.setMessage("Please enter user ID.");
                return;
            }
            String s1 = beanLocal.allButtonFunction(getUserId(), "", "", 0, "", "", "", "", testflag, getUserName(), this.brCode, null, null, sdf.format(toDate), sdf.format(fromDt), this.operBrnCode, this.deputOrXfer, npciUserName);
            if (s1 == null || s1.equalsIgnoreCase("") || s1.length() == 0) {
                this.setMessage("System error occured !!!");
                refreshPage();
                return;
            }
            if (s1.equalsIgnoreCase("User is Already Login On System ")) {
                this.setMessage("User is already logged in.");
                refresh1();
            } else if (s1.equalsIgnoreCase("please check login status")) {
                this.setMessage("User is already logged in or check the login status.");
                refresh1();
            } else if (s1.equalsIgnoreCase("userid could not deactivated")) {
                this.setMessage("UserId could not deactivated");
                refresh1();
            } else if (s1.equalsIgnoreCase("User ID is DeActivated Successfully")) {
                refresh1();
                this.setMessage("User Id is deactivated successfully");
                this.setAuthorizationLevelFlag("false");
            } else {
                this.setMessage("User Id can not deactivated successfully");
                refresh1();
            }
        } catch (ApplicationException e) {
            refreshPage();
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            refreshPage();
            this.setMessage("Please check all the text fields value..and then try the Expire Operation again..");
        }
    }

    public void delete() {
        try {
            //String hostName = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String testflag = "delete";
            if (this.brCode.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select branch.");
                return;
            }
            if (this.getUserId() == null || this.getUserId().equalsIgnoreCase("") || this.getUserId().length() == 0) {
                this.setMessage("Please enter user ID.");
                return;
            }
            String s1 = beanLocal.allButtonFunction(getUserId(), "", "", 0, "", "", "", "", testflag, getUserName(), this.brCode, null, null,
                    sdf.format(toDate), sdf.format(fromDt), this.operBrnCode, this.deputOrXfer, npciUserName);
            if (s1 == null || s1.equalsIgnoreCase("") || s1.length() == 0) {
                this.setMessage("System error occured !!!");
                refreshPage();
                return;
            }
            if (s1.equalsIgnoreCase("User is Already Login On System")) {
                this.setMessage("User is already logged in.");
                refresh1();
            } else if (s1.equalsIgnoreCase("please check login status")) {
                this.setMessage("User is already logged in or check the login status.");
                refresh1();
            } else if (s1.equalsIgnoreCase("User ID is Deleted Successfully")) {
                this.setMessage("User Id is deleted successfully");
                refresh1();
                this.setAuthorizationLevelFlag("false");
            } else {
                this.setMessage(" Sorry, UserId can not be deleted ");
                refresh1();
            }
        } catch (ApplicationException e) {
            refreshPage();
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            refreshPage();
            this.setMessage("Please check all the text fields value...and then try to delete again..");
        }
    }

    public void userIdChange() {
        try {
            this.setMessage("");
            this.setUser("");
            this.setCashLimit("");
            this.setClearingLimit("");
            this.setTransferLimit("");
            this.setPassword("");
            this.setReTypePassword("");
            this.setUserAddress("");
            String host = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            fromToDateFlag = "none";
            deputXferFlag = "none";
            this.setAuthorizationLevelFlag("false");
            if (flag1.equalsIgnoreCase("false")) {
                if (this.brCode.equalsIgnoreCase("--Select--")) {
                    this.setMessage("Please select branch.");
                    return;
                }
            }
            if (getUserId().equalsIgnoreCase("")) {
                this.setMessage("Please fill the user id..");
                createButtonValue = true;
                flag = false;
                return;
            }
            Pattern p3 = Pattern.compile("[A-Za-z0-9]+[ \t\r\n]*");
            if (!getUserId().equalsIgnoreCase("")) {
                Matcher billNoCheck3 = p3.matcher(this.getUserId());
                if (!billNoCheck3.matches()) {
                    this.setMessage("Userid not valid,use (A-Z,a-z,0-9)..");
                    this.setUserId("");
                    flag = false;
                    return;
                }
            }
            if (getUserId().length() > 29) {
                this.setMessage("UserId is exceeding limit..");
                this.setUserId("");
                flag = false;
            }

            if (getUserId().equalsIgnoreCase(getUserName())) {
                setMessage("You could not be deleted or expired or taken any operation. You are login user: " + getUserName());
                this.setUserId("");
                this.updateButtonValue = true;
                this.expireButtonValue = true;
                flag = false;
                return;
            }
            List list2 = beanLocal.getSuperUserLevelId(getUserName());
            List str = getHeadOfcBranchCode();
            Vector vector = (Vector) list2.get(0);

            String res = beanLocal.userIdCheck(userId, this.brCode);
            if (res == null || res.equalsIgnoreCase("") || res.length() == 0) {
                this.setMessage("System error occured !!!");
                createButtonValue = true;
                flag = false;
                return;
            } else {
                if (!res.equalsIgnoreCase("true")) {
                    this.setMessage(res);
                    this.createButtonValue = true;
                    this.updateButtonValue = false;

                    if (Integer.parseInt(vector.get(0).toString()) == 1 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                        this.expireButtonValue = true;
                        this.deleteButtonValue = true;
                        this.activeButtonValue = true;
                    } else if (Integer.parseInt(vector.get(0).toString()) == 2 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                        this.expireButtonValue = true;
                        this.deleteButtonValue = true;
                        this.activeButtonValue = true;
                    } else {
                        this.expireButtonValue = false;
                        this.deleteButtonValue = false;
                        this.activeButtonValue = false;
                    }

                    flag = false;
                    //return;
                    fromToDateFlag = "";
                    deputXferFlag = "";

                }
            }
            List list = beanLocal.getAccountDetails(this.getUserId(), host, this.brCode);
            if (list.isEmpty()) {
                // this.setMessage("user does not exist");
                this.textRePasswordvalue = false;
                this.textpasswordvalue = false;
                if (Integer.parseInt(vector.get(0).toString()) == 1 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                    this.createButtonValue = true;
                } else if (Integer.parseInt(vector.get(0).toString()) == 2 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                    this.createButtonValue = true;
                } else {
                    this.createButtonValue = false;
                }

                this.updateButtonValue = true;
                this.expireButtonValue = true;
                this.deleteButtonValue = true;
                //this.expireButtonValue = true;
                flag = true;
            } else {
                Vector v1 = (Vector) list.get(0);

                if (v1.get(2).toString().equalsIgnoreCase("D")) {
                    this.setMessage("Deleted userid cannot be created again.");
                    flag = false;
                    this.setUserId("");
                    this.createButtonValue = true;
                    this.activeButtonValue = true;
                    this.expireButtonValue = true;
                    this.updateButtonValue = true;
                    this.deleteButtonValue = true;
                } else {
                    if (v1.get(2).toString().equalsIgnoreCase("A")) {
                        this.createButtonValue = true;
                        this.activeButtonValue = true;
                        if (Integer.parseInt(vector.get(0).toString()) == 1 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                            this.expireButtonValue = true;
                            this.updateButtonValue = false;
                            this.deleteButtonValue = true;
                        } else if (Integer.parseInt(vector.get(0).toString()) == 2 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                            this.expireButtonValue = true;
                            this.updateButtonValue = false;
                            this.deleteButtonValue = true;
                        } else {
                            this.expireButtonValue = false;
                            this.updateButtonValue = false;
                            this.deleteButtonValue = false;
                        }

                        fromToDateFlag = "";
                        deputXferFlag = "";
                    } else if (v1.get(2).toString().equalsIgnoreCase("C")) {
                        this.createButtonValue = true;
                        if (Integer.parseInt(vector.get(0).toString()) == 1 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                            this.activeButtonValue = true;
                        } else if (Integer.parseInt(vector.get(0).toString()) == 2 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                            this.activeButtonValue = true;
                        } else {
                            this.activeButtonValue = false;
                        }

                        this.expireButtonValue = true;
                        this.updateButtonValue = true;
                        this.deleteButtonValue = true;
                    } else if (v1.get(2).toString().equalsIgnoreCase("I")) {
                        this.createButtonValue = true;
                        if (Integer.parseInt(vector.get(0).toString()) == 1 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                            this.activeButtonValue = true;
                        } else if (Integer.parseInt(vector.get(0).toString()) == 2 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                            this.activeButtonValue = true;
                        } else {
                            this.activeButtonValue = false;
                        }

                        this.expireButtonValue = true;
                        this.updateButtonValue = true;
                        this.deleteButtonValue = true;
                    }
                    //this.textRePasswordvalue = true;
                    //this.textpasswordvalue = true;
                    this.setUser(v1.get(0).toString());

                    if (Integer.parseInt(vector.get(0).toString()) == 1 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                        if (v1.get(4).toString().equals("1")) {
                            this.setAuthorizationLevelFlag("true");
                        } else if (v1.get(4).toString().equals("5") || v1.get(4).toString().equals("7")) {
                            this.createButtonValue = true;
                            this.activeButtonValue = true;
                            this.expireButtonValue = true;
                            this.updateButtonValue = true;
                            this.deleteButtonValue = true;
                        }
                    } else if (Integer.parseInt(vector.get(0).toString()) == 2 && (!str.get(0).equals(vector.get(0)) || str.get(0).equals(vector.get(0)))) {
                        if (v1.get(4).toString().equals("1")) {
                            this.setAuthorizationLevelFlag("true");
                        } else if (v1.get(4).toString().equals("2")) {
                            this.setAuthorizationLevelFlag("true");
                        } else if (v1.get(4).toString().equals("5") || v1.get(4).toString().equals("7")) {
                            this.createButtonValue = true;
                            this.activeButtonValue = true;
                            this.expireButtonValue = true;
                            this.updateButtonValue = true;
                            this.deleteButtonValue = true;
                        }
                    }

                    this.setAuthorizationLevel(v1.get(4).toString());
                    this.setCashLimit(formatter.format(Double.parseDouble(v1.get(5).toString())));
                    this.setClearingLimit(formatter.format(Double.parseDouble(v1.get(6).toString())));
                    this.setTransferLimit(formatter.format(Double.parseDouble(v1.get(7).toString())));
                    this.setUserAddress(v1.get(3).toString());
                    this.setDeputOrXfer(v1.get(12).toString());
                    this.setOperBrnCode(v1.get(11).toString());
                    this.setNpciUserName(v1.get(13).toString());
                    String dt1 = v1.get(10).toString();
                    String dt2 = v1.get(9).toString();
                    this.setFromDt(sdf.parse(dt1.substring(0, 4) + dt1.substring(5, 7) + dt1.substring(8, 10)));
                    this.setToDate(sdf.parse(dt2.substring(0, 4) + dt2.substring(5, 7) + dt2.substring(8, 10)));
                    flag = true;
                }

            }
            if (Integer.parseInt(vector.get(0).toString()) == 3 || Integer.parseInt(vector.get(0).toString()) == 4 || Integer.parseInt(vector.get(0).toString()) == 6 || Integer.parseInt(vector.get(0).toString()) == 99) {
                this.createButtonValue = true;
                this.activeButtonValue = true;
                this.expireButtonValue = true;
                this.updateButtonValue = true;
                this.deleteButtonValue = true;
            }
        } catch (ApplicationException e) {
            refreshPage();
            this.setMessage(e.getExceptionCode().getExceptionMessage());
        } catch (Exception e) {
            refreshPage();
            this.setMessage("Please Check the userid Text field value.");
            flag = false;
        }
    }

    public void onblurDeputXfer() {
        try {
            if (this.deputOrXfer.equalsIgnoreCase("0")) {
                this.setOperBrnCode("0");
                this.setFromDt(new Date());
                this.setToDate(new Date());
            } else if (this.deputOrXfer.equalsIgnoreCase("Transfer")) {
                fromToDateFlag = "none";
                deputXferFlag = "";
            } else if (this.deputOrXfer.equalsIgnoreCase("Deputation")) {
                fromToDateFlag = "";
                deputXferFlag = "";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshPage() {
        try {
            setAuthorizationLevel("0");
            this.setMessage("");
            this.setUserId("");
            this.setUser("");

            this.setCashLimit("");
            this.setClearingLimit("");
            this.setTransferLimit("");
            this.setPassword("");
            this.setReTypePassword("");
            this.setUserAddress("");
            this.setNpciUserName("");
            createButtonValue = true;
            updateButtonValue = true;
            activeButtonValue = true;
            expireButtonValue = true;
            deleteButtonValue = true;

            this.setDeputOrXfer("0");
            this.setOperBrnCode("0");
            this.setFromDt(new Date());
            this.setToDate(new Date());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String onBlurUserName() {
        try {
            if (this.getUser().equalsIgnoreCase("")) {
                this.setMessage("Please fill the User Name.");
                return "Please fill the User Name.";
            }
            if (this.getUser().length() < 3) {
                this.setMessage("User Name should not be less than size 3.");
                this.setUser("");
                return "User Name should not be less than size 3.";
            }
            if (this.getUser().length() > 40) {
                this.setMessage("User Name length is exceeding max limit.");
                this.setUser("");
                return "User Name length is exceeding max limit.";
            }
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher billNoCheck = p.matcher(this.getUser());
            if (!billNoCheck.matches()) {
                this.setMessage("User Name should contain only characters sequence.");
                this.setUser("");
                return "User Name should contain only characters sequence.";
            }
            this.setMessage("");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public String onBlurPassword() {
        try {
            if (this.getPassword().equalsIgnoreCase("")) {
                this.setMessage("Password can not be blank.");
                return "Password can not be blank.";
            }
            if (this.getPassword().length() < 8) {
                this.setMessage("Password length can not be less than 8.");
                this.setPassword("");
                return "Password length can not be less than 8.";
            }
            if (this.getPassword().length() > 60) {
                this.setMessage("Password length is exceeding limit.");
                this.setPassword("");
                return "Password length is exceeding limit.";
            }
            if (this.getUser().equalsIgnoreCase(this.getPassword())) {
                this.setMessage("User Name and Password could not be same.");
                this.setPassword("");
                return "User Name and Password could not be same.";
            }
            this.setMessage("");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public String onBlurRePassword() {
        try {
            if (this.getReTypePassword().equalsIgnoreCase("")) {
                this.setMessage("Retype Password field could not be empty.");
                return "ReType Password field could not be empty.";
            }
            if (!this.getPassword().equalsIgnoreCase(this.getReTypePassword())) {
                this.setMessage("Password and Retype Password is mismatch.");
                this.setReTypePassword("");
                return "Password and Retype Password is mismatch.";
            }
            this.setMessage("");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public void refresh1() {
        try {
            setAuthorizationLevel("0");
            setUserId("");
            this.setUser("");
            this.setCashLimit("");
            this.setClearingLimit("");
            this.setTransferLimit("");
            this.setPassword("");
            this.setReTypePassword("");
            this.setUserAddress("");
            this.setNpciUserName("");
            this.expireButtonValue = true;
            this.deleteButtonValue = true;
            this.updateButtonValue = true;
            this.activeButtonValue = true;
            this.createButtonValue = true;
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void branchOnblur() {
        try {
            setAuthorizationLevel("0");
            setUserId("");
            this.setUser("");
            this.setCashLimit("");
            this.setClearingLimit("");
            this.setTransferLimit("");
            this.setPassword("");
            this.setReTypePassword("");
            this.setUserAddress("");
            this.setNpciUserName("");
            createButtonValue = true;
            updateButtonValue = true;
            activeButtonValue = true;
            expireButtonValue = true;
            deleteButtonValue = true;

            this.setMessage("");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refreshPage();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "case1";
    }
}
