/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class SuperUser {
    
    private final String jndiHomeName = "AdminManagementFacade";
    private AdminManagementFacadeRemote beanLocal = null;

    private String orgBrnCode;
    
    HttpServletRequest request;
    
    private String todayDate;
    
    private String userName;
    
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

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of UserMaintenance1 */
    public SuperUser() {
        try {
            request = getRquest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName("System");
            this.setMessage("");
            createButtonValue = false;
            
            authorizationList = new ArrayList<SelectItem>();
            operBrnCodeList = new ArrayList<SelectItem>();
            operBrnCodeList.add(new SelectItem("--Select--"));
            this.setFlag1("true");
            List str = getHeadOfcBranchCode();
            fromToDateFlag = "none";
            deputXferFlag = "none";
            beanLocal = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            if (str.get(0).toString().equalsIgnoreCase("false")) {
                List list = beanLocal.getHeadOfcBranchFromCodeBook();
                Vector vector = (Vector) list.get(0);
                String branchCode = vector.get(0).toString();
                String alphaCodeWithBrnName = vector.get(1).toString();
                String alphaCode,branchNm;
                String array[] = null;
                if(alphaCodeWithBrnName.contains("->")){
                    array = alphaCodeWithBrnName.split("->");
                }
                alphaCode = array[0].toString();
                branchNm = array[1].toString();
                //branchNm = branchNm.substring(0, branchNm.indexOf("]"));

                brCodeList = new ArrayList<SelectItem>();
                brCodeList.add(new SelectItem(branchCode, alphaCode));
                operBrnCodeList = new ArrayList<SelectItem>();
                operBrnCodeList.add(new SelectItem("0", "--Select--"));
                this.setBrCode(branchCode);
            } else {
                brCodeList = new ArrayList<SelectItem>();
                brCodeList.add(new SelectItem(str.get(0).toString(), str.get(1).toString()));
                operBrnCodeList = new ArrayList<SelectItem>();
                operBrnCodeList.add(new SelectItem(str.get(0).toString(), str.get(1).toString()));
                brCode = str.get(0).toString();
            }

            authorizationList();


        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
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
        } catch (Exception ex) {
           setMessage(ex.getLocalizedMessage());
        }
        return msg;
    }

    public HttpServletRequest getRquest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void authorizationList() {
        authorizationList.add(new SelectItem("0", "--Select--"));
        authorizationList.add(new SelectItem("7", "Super User"));

    }

    public void onSelectListOption() {
        try {
            if (this.AuthorizationLevel.equalsIgnoreCase("0")) {
                this.setMessage("Please select the Authorization Level.");
                return;
            }
            this.setMessage("");

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void createButton() {
        try {
            setMessage("");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

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

            String newCashLimitSend = "";
            String newClearingLimitSend = "";
            String newTransfreDebitSend = "";
            Integer authLevel = 0;
            String newUserAddressSend = "";
            if (AuthorizationLevel.equalsIgnoreCase("7")) {
                authLevel = 7;
            }
            if (getCashLimit()==null) {
                newCashLimitSend = "";
            } 
            if (getClearingLimit()==null) {
                newClearingLimitSend = "";
            } 
            if (getTransferLimit()==null) {
                newTransfreDebitSend = "";
            } 
            if (getUserAddress().equalsIgnoreCase("") || getUserAddress()==null) {
                newUserAddressSend = "";
            } else {
                newUserAddressSend = getUserAddress();
            }
            String testflag = "create";
            String s1 = beanLocal.allButtonFunction(getUserId(), getUser(), getPassword(), authLevel, newCashLimitSend,
                    newClearingLimitSend, newTransfreDebitSend, newUserAddressSend, testflag, getUserName(), this.brCode, this.password, this.reTypePassword, "", "", this.operBrnCode, this.deputOrXfer,"");

            if (s1 == null || s1.equalsIgnoreCase("") || s1.length() == 0) {
                this.setMessage("System Error Occured !!!");
                refresh1();
            } else {
                this.setMessage(s1);
                refresh1();
            }
        } catch (Exception ex) {
            refreshPage();
          //  this.setMessage("Please check all the text field values entered and then try again to create UserId.");
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void userIdChange() {
        try {
            this.setMessage("");
            this.setUser("");
            this.setPassword("");
            this.setReTypePassword("");
            this.setUserAddress("");
            String host = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            fromToDateFlag = "none";
            deputXferFlag = "none";

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

            if (getUserId().equalsIgnoreCase(userName)) {
                setMessage("You could not be deleted or expired or taken any operation. You are login user: " + userName);
                this.setUserId("");
                flag = false;
                return;
            }
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
                    flag = false;

                }
            }
            List list = beanLocal.getAccountDetails(this.getUserId(), host, this.brCode);
            if (list.isEmpty()) {
                // this.setMessage("user does not exist");
                this.textRePasswordvalue = false;
                this.textpasswordvalue = false;
                this.createButtonValue = false;
                flag = true;
            } else {
                Vector v1 = (Vector) list.get(0);

                if (v1.get(2).toString().equalsIgnoreCase("D")) {
                    this.setMessage("Deleted userid cannot be created again.");
                    flag = false;
                    this.setUserId("");
                    this.createButtonValue = true;
                } else {
                    if (v1.get(2).toString().equalsIgnoreCase("A")) {
                        this.createButtonValue = true;
                    } else if (v1.get(2).toString().equalsIgnoreCase("C")) {
                        this.createButtonValue = true;
                    } else if (v1.get(2).toString().equalsIgnoreCase("I")) {
                        this.createButtonValue = true;
                    }

                    this.setUser(v1.get(0).toString());
                    this.setAuthorizationLevel(v1.get(4).toString());
                    this.setUserAddress(v1.get(3).toString());
                    flag = true;
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
            refreshPage();
            flag = false;
        }
    }


    public void refreshPage() {
        try {
            setAuthorizationLevel("0");
            this.setMessage("");
            this.setUserId("");
            this.setUser("");
            this.setPassword("");
            this.setReTypePassword("");
            this.setUserAddress("");
            createButtonValue = true;
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public String onBlurUserName() {
        try {
            if (this.getUser().equalsIgnoreCase("")) {
                this.setMessage("Please fill the User Name.");
                return "Please fill the User Name.";
            }
            if (this.getUser().length() < 3) {
                this.setMessage("User Name could not less than size 3.");
                this.setUser("");
                return "User Name could not less than size 3.";
            }
            if (this.getUser().length() > 40) {
                this.setMessage("User Name length is exceeding max limit.");
                this.setUser("");
                return "User Name length is exceeding max limit.";
            }
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher billNoCheck = p.matcher(this.getUser());
            if (!billNoCheck.matches()) {
                this.setMessage("UserName should contain only characters sequence.");
                this.setUser("");
                return "UserName should contain only characters sequence.";
            }
            this.setMessage("");
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public String onBlurPassword() {
        try {
            if (this.getPassword().equalsIgnoreCase("")) {
                this.setMessage("Password can not be blank.");
                return "Password can not be blank.";
            }
            if (this.getPassword().length() < 6) {
                this.setMessage("Password length can not be less than 6.");
                this.setPassword("");
                return "Password length can not be less than 6.";
            }
            if (this.getPassword().length() > 60) {
                this.setMessage("Password length is exceeding limit.");
                this.setPassword("");
                return "Password length is exceeding limit.";
            }
            if (this.getUser().equalsIgnoreCase(this.getPassword())) {
                this.setMessage("UserName and Password could not be same.");
                this.setPassword("");
                return "UserName and Password could not be same.";
            }
            this.setMessage("");
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
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
            setMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public void refresh1() {
        try {
            setAuthorizationLevel("0");
            setUserId("");
            this.setUser("");
            this.setPassword("");
            this.setReTypePassword("");
            this.setUserAddress("");
            this.createButtonValue = true;
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refreshPage();
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        return "case1";
    }
}
