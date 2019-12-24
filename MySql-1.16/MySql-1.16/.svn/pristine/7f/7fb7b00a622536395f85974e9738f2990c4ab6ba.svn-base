/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  22 NOV 2010
 */
package com.cbs.web.controller.clg;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.OutwardClearingManagementFacadeRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.clg.OutwardClearingVerificationGrid;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class OutwardClearingVerification {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private HttpServletRequest req;
    private boolean statusChk;
    private String status;
    private List<SelectItem> statusList;
    private String by;
    private List<SelectItem> byList;
    private boolean userChk;
    private String user;
    private List<SelectItem> userList;
    private boolean terminalChk;
    private String terminal;
    private List<SelectItem> terminalList;
    private String clearingMode;
    private List<SelectItem> clearingModeList;
    private String regDate;
    private List<SelectItem> regDateList;
    private boolean userDisFlag;
    private boolean terminalDisFlag;
    private boolean statusDisFlag;
    private List<OutwardClearingVerificationGrid> gridDetail;
    int currentRow;
    private OutwardClearingVerificationGrid currentItem = new OutwardClearingVerificationGrid();
    private String pendingStatus1;
    private String pendingStatus2;
    private String verifiedStatus1;
    private String verifiedStatus2;
    private String netStatus1;
    private String netStatus2;
    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private final String jndiHomeNameOutward = "OutwardClearingManagementFacade";
    private OutwardClearingManagementFacadeRemote outwardClgRemote = null;
    private final String jndiHomeNameOther = "OtherTransactionManagementFacade";
    private OtherTransactionManagementFacadeRemote otherRemote = null;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
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

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public List<SelectItem> getByList() {
        return byList;
    }

    public void setByList(List<SelectItem> byList) {
        this.byList = byList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public boolean isUserChk() {
        return userChk;
    }

    public void setUserChk(boolean userChk) {
        this.userChk = userChk;
    }

    public boolean isStatusChk() {
        return statusChk;
    }

    public void setStatusChk(boolean statusChk) {
        this.statusChk = statusChk;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public boolean isTerminalChk() {
        return terminalChk;
    }

    public void setTerminalChk(boolean terminalChk) {
        this.terminalChk = terminalChk;
    }

    public List<SelectItem> getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(List<SelectItem> terminalList) {
        this.terminalList = terminalList;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<SelectItem> getUserList() {
        return userList;
    }

    public void setUserList(List<SelectItem> userList) {
        this.userList = userList;
    }

    public String getClearingMode() {
        return clearingMode;
    }

    public void setClearingMode(String clearingMode) {
        this.clearingMode = clearingMode;
    }

    public List<SelectItem> getClearingModeList() {
        return clearingModeList;
    }

    public void setClearingModeList(List<SelectItem> clearingModeList) {
        this.clearingModeList = clearingModeList;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public List<SelectItem> getRegDateList() {
        return regDateList;
    }

    public void setRegDateList(List<SelectItem> regDateList) {
        this.regDateList = regDateList;
    }

    public boolean isStatusDisFlag() {
        return statusDisFlag;
    }

    public void setStatusDisFlag(boolean statusDisFlag) {
        this.statusDisFlag = statusDisFlag;
    }

    public boolean isTerminalDisFlag() {
        return terminalDisFlag;
    }

    public void setTerminalDisFlag(boolean terminalDisFlag) {
        this.terminalDisFlag = terminalDisFlag;
    }

    public boolean isUserDisFlag() {
        return userDisFlag;
    }

    public void setUserDisFlag(boolean userDisFlag) {
        this.userDisFlag = userDisFlag;
    }

    public OutwardClearingVerificationGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(OutwardClearingVerificationGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<OutwardClearingVerificationGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<OutwardClearingVerificationGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getNetStatus1() {
        return netStatus1;
    }

    public void setNetStatus1(String netStatus1) {
        this.netStatus1 = netStatus1;
    }

    public String getNetStatus2() {
        return netStatus2;
    }

    public void setNetStatus2(String netStatus2) {
        this.netStatus2 = netStatus2;
    }

    public String getPendingStatus1() {
        return pendingStatus1;
    }

    public void setPendingStatus1(String pendingStatus1) {
        this.pendingStatus1 = pendingStatus1;
    }

    public String getPendingStatus2() {
        return pendingStatus2;
    }

    public void setPendingStatus2(String pendingStatus2) {
        this.pendingStatus2 = pendingStatus2;
    }

    public String getVerifiedStatus1() {
        return verifiedStatus1;
    }

    public void setVerifiedStatus1(String verifiedStatus1) {
        this.verifiedStatus1 = verifiedStatus1;
    }

    public String getVerifiedStatus2() {
        return verifiedStatus2;
    }

    public void setVerifiedStatus2(String verifiedStatus2) {
        this.verifiedStatus2 = verifiedStatus2;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public boolean isFlag3() {
        return flag3;
    }

    public void setFlag3(boolean flag3) {
        this.flag3 = flag3;
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of OutwardClearingVerification */
    public OutwardClearingVerification() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            outwardClgRemote = (OutwardClearingManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOutward);
            otherRemote = (OtherTransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOther);
            this.setErrorMessage("");
            this.setMessage("");
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("PENDING"));
            statusList.add(new SelectItem("VERIFIED"));
            byList = new ArrayList<SelectItem>();
            byList.add(new SelectItem("Vtot", "Voucher No."));
            byList.add(new SelectItem("Acno", "Account No."));
            regDateList = new ArrayList<SelectItem>();
            regDateList.add(new SelectItem("--Select--"));
            userList = new ArrayList<SelectItem>();
            userList.add(new SelectItem("--Select--"));
            clearingMode();
            terminalCombo();
            this.setUserDisFlag(true);
            this.setTerminalDisFlag(true);
            this.setStatusDisFlag(true);
            this.setFlag1(true);
            this.setFlag2(true);
            this.setFlag3(true);

            
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void selectUserChkBoxValue(ValueChangeEvent event) {
        try {
            HtmlSelectBooleanCheckbox chk = (HtmlSelectBooleanCheckbox) event.getComponent();
            this.setFlag1(true);
            this.setFlag2(true);
            if (chk.getValue().toString().equalsIgnoreCase("true")) {
                this.setUserChk(true);
                this.setUserDisFlag(false);
            } else {
                this.setUserChk(false);
                this.setUserDisFlag(true);
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void selectTerminalChkBoxValue(ValueChangeEvent event) {
        try {
            HtmlSelectBooleanCheckbox chk1 = (HtmlSelectBooleanCheckbox) event.getComponent();
            this.setFlag1(true);
            this.setFlag2(true);
            if (chk1.getValue().toString().equalsIgnoreCase("true")) {
                this.setTerminalChk(true);
                this.setTerminalDisFlag(false);
            } else {
                this.setTerminalChk(false);
                this.setTerminalDisFlag(true);
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void selectStatusChkBoxValue(ValueChangeEvent event) {
        try {
            HtmlSelectBooleanCheckbox chk2 = (HtmlSelectBooleanCheckbox) event.getComponent();
            this.setFlag1(true);
            this.setFlag2(true);
            if (chk2.getValue().toString().equalsIgnoreCase("true")) {
                this.setStatusChk(true);
                this.setStatusDisFlag(false);
            } else {
                this.setStatusChk(false);
                this.setStatusDisFlag(true);
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void clearingMode() {
        try {
            List resultList = new ArrayList();
            resultList = otherRemote.getClearingOption();
            if (!resultList.isEmpty()) {
                clearingModeList = new ArrayList<SelectItem>();
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vecForcircleTyp = (Vector) resultList.get(i);
                    clearingModeList.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
                }
            } else {
                clearingModeList = new ArrayList<SelectItem>();
                clearingModeList.add(new SelectItem("--Select--"));
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void registerDate() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag1(true);
        this.setFlag2(true);
        this.setFlag3(true);
        try {
            if (this.clearingMode == null || this.clearingMode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Clearing Mode.");
                return;
            }
            List resultList = new ArrayList();
            resultList = outwardClgRemote.regDateCombo(this.orgnBrCode, this.clearingMode);
            if (!resultList.isEmpty()) {
                regDateList = new ArrayList<SelectItem>();
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        regDateList.add(new SelectItem(ee.toString()));
                        //this.setFlag3(false);
                    }
                }
            } else {
                regDateList = new ArrayList<SelectItem>();
                regDateList.add(new SelectItem("--Select--"));
                this.setFlag3(true);
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void userCombo() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag1(true);
        this.setFlag2(true);
        this.setFlag3(true);
        try {
            gridDetail = new ArrayList<OutwardClearingVerificationGrid>();
            if (this.clearingMode == null || this.clearingMode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Clearing Mode.");
                return;
            }
            if (this.regDate == null || this.regDate.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Register Date");
                return;
            }
            List resultList = new ArrayList();
            resultList = outwardClgRemote.usersCombo(this.orgnBrCode, this.clearingMode, this.regDate.substring(6) + this.regDate.substring(3, 5) + this.regDate.substring(0, 2));
            if (!resultList.isEmpty()) {
                userList = new ArrayList<SelectItem>();
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        userList.add(new SelectItem(ee.toString()));
                        this.setFlag3(false);
                    }
                }
            } else {
                userList = new ArrayList<SelectItem>();
                userList.add(new SelectItem("--Select--"));
                this.setFlag3(true);
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void terminalCombo() {
        try {
            List resultList = new ArrayList();
            resultList = outwardClgRemote.terminalCombo(this.orgnBrCode);
            if (!resultList.isEmpty()) {
                terminalList = new ArrayList<SelectItem>();
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        terminalList.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                terminalList = new ArrayList<SelectItem>();
                terminalList.add(new SelectItem("--Select--"));
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void gridLoad() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setPendingStatus1("");
        this.setVerifiedStatus1("");
        this.setNetStatus1("");
        this.setPendingStatus2("");
        this.setVerifiedStatus2("");
        this.setNetStatus2("");
        this.setFlag1(true);
        this.setFlag2(true);
        try {
            if (this.clearingMode == null || this.clearingMode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Clearing Mode.");
                return;
            }
            if (this.regDate == null || this.regDate.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Register Date");
                return;
            }
            if (this.userChk == true) {
                if (this.user == null || this.user.equalsIgnoreCase("--Select--")) {
                    this.setErrorMessage("Please Select User");
                    return;
                }
            }
            if (this.terminalChk == true) {
                if (this.terminal == null || this.terminal.equalsIgnoreCase("--Select--")) {
                    this.setErrorMessage("Please Select Terminal.");
                    return;
                }
            }
            if (this.statusChk == true) {
                if (this.status == null || this.status.equalsIgnoreCase("--Select--")) {
                    this.setErrorMessage("Please Select Status.");
                    return;
                }
            }
            gridDetail = new ArrayList<OutwardClearingVerificationGrid>();
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            List resultList2 = new ArrayList();
            //System.out.println("this.clearingMode:======="+this.clearingMode);
            resultList = outwardClgRemote.loadInstrumentDetails(this.orgnBrCode, this.clearingMode, this.regDate.substring(6) + this.regDate.substring(3, 5) + this.regDate.substring(0, 2), this.user, this.terminal, this.status, this.by, this.userChk, this.terminalChk, this.statusChk);
            resultList1 = outwardClgRemote.glTableInstLoad(this.orgnBrCode, this.clearingMode, this.regDate.substring(6) + this.regDate.substring(3, 5) + this.regDate.substring(0, 2), this.user, this.terminal, this.status, this.by, this.userChk, this.terminalChk, this.statusChk);
            resultList2 = outwardClgRemote.loadFDInstrumentDetails(this.orgnBrCode, this.clearingMode, this.regDate.substring(6) + this.regDate.substring(3, 5) + this.regDate.substring(0, 2), this.user, this.terminal, this.status, this.by, this.userChk, this.terminalChk, this.statusChk);

            if (resultList.isEmpty() && resultList1.isEmpty() && resultList2.isEmpty()) {
                this.setErrorMessage("No Record Exists !!!");
                return;
            }
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    OutwardClearingVerificationGrid detail = new OutwardClearingVerificationGrid();
                    detail.setAcctno(ele.get(0).toString());
                    detail.setCustName(ele.get(1).toString());
                    detail.setInstNo(ele.get(2).toString());
                    detail.setAmount(Double.parseDouble(ele.get(3).toString()));
                    if (ele.get(4).toString().equalsIgnoreCase("V")) {
                        detail.setStatus("VERIFIED");
                    } else {
                        detail.setStatus("PENDING");
                    }

                    detail.setDate(ele.get(5).toString());
                    detail.setBankName(ele.get(6).toString());
                    detail.setBankAdd(ele.get(7).toString());
                    detail.setDrwAcctNo(ele.get(8).toString());
                    detail.setDrwName(ele.get(9).toString());
                    detail.setEnterBy(ele.get(10).toString());
                    detail.setVtot(ele.get(11).toString());
                    detail.setJtName1(ele.get(12).toString());
                    detail.setJtName2(ele.get(13).toString());
                    detail.setJtName3(ele.get(14).toString());
                    detail.setJtName4(ele.get(15).toString());
                    detail.setMicrCode(ele.get(16).toString() + ele.get(17).toString() + ele.get(18).toString());
                    detail.setCheckFlag("N");
                    gridDetail.add(detail);
                }
                statusDescription();
            }
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele = (Vector) resultList1.get(i);
                    OutwardClearingVerificationGrid detail = new OutwardClearingVerificationGrid();
                    detail.setAcctno(ele.get(0).toString());
                    detail.setCustName(ele.get(1).toString());
                    detail.setInstNo(ele.get(2).toString());
                    detail.setAmount(Double.parseDouble(ele.get(3).toString()));
                    if (ele.get(4).toString().equalsIgnoreCase("V")) {
                        detail.setStatus("VERIFIED");
                    } else {
                        detail.setStatus("PENDING");
                    }

                    detail.setDate(ele.get(5).toString());
                    detail.setBankName(ele.get(6).toString());
                    detail.setBankAdd(ele.get(7).toString());
                    detail.setDrwAcctNo(ele.get(8).toString());
                    detail.setDrwName(ele.get(9).toString());
                    detail.setEnterBy(ele.get(10).toString());
                    detail.setVtot(ele.get(11).toString());
                    detail.setJtName1(ele.get(12).toString());
                    detail.setJtName2(ele.get(13).toString());
                    detail.setJtName3(ele.get(14).toString());
                    detail.setJtName4(ele.get(15).toString());
                    detail.setMicrCode(ele.get(16).toString() + ele.get(17).toString() + ele.get(18).toString());
                    detail.setCheckFlag("N");
                    gridDetail.add(detail);
                }
                statusDescription();
            }
            if (!resultList2.isEmpty()) {
                for (int i = 0; i < resultList2.size(); i++) {
                    Vector ele = (Vector) resultList2.get(i);
                    OutwardClearingVerificationGrid detail = new OutwardClearingVerificationGrid();
                    detail.setAcctno(ele.get(0).toString());
                    detail.setCustName(ele.get(1).toString());
                    detail.setInstNo(ele.get(2).toString());
                    detail.setAmount(Double.parseDouble(ele.get(3).toString()));
                    if (ele.get(4).toString().equalsIgnoreCase("V")) {
                        detail.setStatus("VERIFIED");
                    } else {
                        detail.setStatus("PENDING");
                    }

                    detail.setDate(ele.get(5).toString());
                    detail.setBankName(ele.get(6).toString());
                    detail.setBankAdd(ele.get(7).toString());
                    detail.setDrwAcctNo(ele.get(8).toString());
                    detail.setDrwName(ele.get(9).toString());
                    detail.setEnterBy(ele.get(10).toString());
                    detail.setVtot(ele.get(11).toString());
                    detail.setJtName1(ele.get(12).toString());
                    detail.setJtName2(ele.get(13).toString());
                    detail.setJtName3(ele.get(14).toString());
                    detail.setJtName4(ele.get(15).toString());
                    detail.setMicrCode(ele.get(16).toString() + ele.get(17).toString() + ele.get(18).toString());
                    detail.setCheckFlag("N");
                    gridDetail.add(detail);
                }
                statusDescription();
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void statusDescription() {
        try {
            List resultList = new ArrayList();
            resultList = outwardClgRemote.statusDetail(this.orgnBrCode, this.clearingMode, this.regDate.substring(6) + this.regDate.substring(3, 5) + this.regDate.substring(0, 2));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setPendingStatus1(ele.get(0).toString());
                    this.setVerifiedStatus1(ele.get(2).toString());
                    this.setNetStatus1(ele.get(4).toString());
                    this.setPendingStatus2(formatter.format(Double.parseDouble(ele.get(1).toString())));
                    this.setVerifiedStatus2(formatter.format(Double.parseDouble(ele.get(3).toString())));
                    this.setNetStatus2(formatter.format(Double.parseDouble(ele.get(5).toString())));
                }
            } else {
                this.setErrorMessage("Status Description Not Found !!!");
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acctno"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (OutwardClearingVerificationGrid item : gridDetail) {
            if (item.getAcctno().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            OutwardClearingVerificationGrid item = gridDetail.get(currentRow);
            gridLoad();
            this.setErrorMessage("You Have Selected Instrument No. " + currentItem.getInstNo() + " For A/C. No. " + currentItem.getAcctno());
            if (currentItem.getStatus().equalsIgnoreCase("VERIFIED")) {
                this.setFlag1(false);
                this.setFlag2(true);
            } else if (currentItem.getStatus().equalsIgnoreCase("PENDING")) {
                this.setFlag2(false);
                this.setFlag1(true);
            }
            if (item.getCheckFlag().equalsIgnoreCase("Y")) {
                this.setErrorMessage("");
                item.setCheckFlag("N");
                this.setFlag2(true);
                this.setFlag1(true);
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
            } else if (item.getCheckFlag().equalsIgnoreCase("N")) {
                item.setCheckFlag("Y");
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void verifyInstruments() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag1(true);
        this.setFlag2(true);
        try {
            if (this.userName.equalsIgnoreCase(currentItem.getEnterBy())) {
                this.setErrorMessage("SORRY YOU CANNOT VERIFY YOUR OWN ENTRY !!!");
                return;
            }
            String result = outwardClgRemote.verificationOfInstrument(this.orgnBrCode, this.clearingMode, currentItem.getVtot(), currentItem.getInstNo(), this.userName);
            if (result == null) {
                this.setErrorMessage("SYSTEM PROBLEM OCCURED!!!");
                return;
            } else {
                if (result.equalsIgnoreCase("true")) {
                    gridLoad();
                    this.setMessage("INSTRUMENT VERIFIED SUCCESSFULLY.");
                    return;
                } else {
                    this.setErrorMessage(result);
                    return;
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void unverifyInstruments() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag1(true);
        this.setFlag2(true);
        try {
            String result = outwardClgRemote.unverificationOfInstrument(this.orgnBrCode, this.clearingMode, currentItem.getVtot(), currentItem.getInstNo());
            if (result == null) {
                this.setErrorMessage("SYSTEM PROBLEM OCCURED!!!");
                return;
            } else {
                if (result.equalsIgnoreCase("true")) {
                    gridLoad();
                    this.setMessage("INSTRUMENT UNVERIFIED SUCCESSFULLY.");
                    return;
                } else {
                    this.setErrorMessage(result);
                    return;
                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setPendingStatus1("");
            this.setVerifiedStatus1("");
            this.setNetStatus1("");
            this.setPendingStatus2("");
            this.setVerifiedStatus2("");
            this.setNetStatus2("");
            gridDetail = new ArrayList<OutwardClearingVerificationGrid>();
            clearingMode();
            terminalCombo();
            this.setUserDisFlag(true);
            this.setTerminalDisFlag(true);
            this.setStatusDisFlag(true);
            this.setFlag1(true);
            this.setFlag2(true);
            this.setUserChk(false);
            this.setTerminalChk(false);
            this.setStatusChk(false);
//            regDateList = new ArrayList<SelectItem>();
//            regDateList.add(new SelectItem("--Select--"));
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
        return "case1";
    }
}
