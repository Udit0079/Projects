/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;

public class ShadowBalancePosting {

    private String branch;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private String registerDt;
    private String postingDt;
    private String totalAmt;
    private String totalinstrument;
    private String emFlags;
    private List<SelectItem> clearingModeOption;
    private List<SelectItem> registerDateOption;
    private List<SelectItem> branchList;
    private HttpServletRequest req;
    private final String jndiHomeName = "OtherTransactionManagementFacade";
    private OtherTransactionManagementFacadeRemote otherTxnRemote = null;
    private CommonReportMethodsRemote commonRemote;

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTotalinstrument() {
        return totalinstrument;
    }

    public void setTotalinstrument(String totalinstrument) {
        this.totalinstrument = totalinstrument;
    }

    public String getPostingDt() {
        return postingDt;
    }

    public void setPostingDt(String postingDt) {
        this.postingDt = postingDt;
    }

    public String getRegisterDt() {
        return registerDt;
    }

    public void setRegisterDt(String registerDt) {
        this.registerDt = registerDt;
    }

    public List<SelectItem> getRegisterDateOption() {
        return registerDateOption;
    }

    public void setRegisterDateOption(List<SelectItem> registerDateOption) {
        this.registerDateOption = registerDateOption;
    }

    public String getEmFlags() {
        return emFlags;
    }

    public void setEmFlags(String emFlags) {
        this.emFlags = emFlags;
    }

    public List<SelectItem> getClearingModeOption() {
        return clearingModeOption;
    }

    public void setClearingModeOption(List<SelectItem> clearingModeOption) {
        this.clearingModeOption = clearingModeOption;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public ShadowBalancePosting() {
        registerDateOption = new ArrayList<SelectItem>();
        try {
            otherTxnRemote = (OtherTransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //setUserName("manager1");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            getClearingMode();
            registerBranches();
            this.setMessage("");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getClearingMode() {
        this.setMessage("");
        clearingModeOption = new ArrayList<SelectItem>();
        try {
            List resultList = otherTxnRemote.getClearingMode();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vecForcircleTyp = (Vector) resultList.get(i);
                    clearingModeOption.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void registerBranches() {
        try {
            branchList = new ArrayList<SelectItem>();
            List list = commonRemote.getAlphacodeBasedOnOrgnBranch(orgnBrCode);
            if (list.isEmpty()) {
                this.setMessage("Please define branchmaster detail.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                branchList.add(new SelectItem(ele.get(1).toString().trim().length() < 2 ? "0"
                        + ele.get(1).toString().trim() : ele.get(1).toString().trim(), ele.get(0).toString()));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getRegisterDate() {
        this.setMessage("");
        registerDateOption = new ArrayList<SelectItem>();
        try {
//            String registerDts = otherTxnRemote.clearingModeLostFocus(emFlags, orgnBrCode);
            if (this.branch == null || this.branch.equals("")) {
                this.setMessage("Please select branch.");
                return;
            }
            String registerDts = otherTxnRemote.clearingModeLostFocus(emFlags, this.branch);
            if (registerDts.equals("Register Date has not been set for this Clearing Mode.")) {
                this.setMessage(registerDts);
            } else {
                if (registerDts.contains("[")) {
                    String[] values = null;
                    registerDts = registerDts.replace("]", "");
                    registerDts = registerDts.replace("[", "");
                    String[] temp = registerDts.split(",");
                    for (String str : temp) {
                        if (!str.equals("") || str != null) {
                            registerDateOption.add(new SelectItem(str));
                        }
                    }
                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    public void getPostingDate() {
        this.setMessage("");
        resetAllValue();
        try {
//            String registerDts = otherTxnRemote.checkPostingDt(emFlags, registerDt, orgnBrCode);
            if (this.branch == null || this.branch.equals("")) {
                this.setMessage("Please select Branch.");
                return;
            }
            if (this.emFlags == null || this.emFlags.equals("--SELECT--") || this.emFlags.equals("")) {
                this.setMessage("Please select Clearing Mode.");
                return;
            }
            if (this.registerDt == null || this.registerDt.equals("--SELECT--") || this.registerDt.equals("")) {
                this.setMessage("Please select Register Date.");
                return;
            }
            String registerDts = otherTxnRemote.checkPostingDt(emFlags, registerDt, this.branch);
            if (registerDts.equals("Posting is not allowed in this Date")) {
                this.setMessage(registerDts);
            } else if (registerDts.equals("Sorry!Posting Date has not been set for this Register")) {
                this.setMessage(registerDts);
            } else {
                String[] values = null;
                String spliter = ": ";
                values = registerDts.split(spliter);
                String totalInstNo = values[0];
                String totalAmts = values[1];
                String postingD = values[3];
                setTotalinstrument(totalInstNo);
                setTotalAmt(formatter.format(Double.parseDouble(totalAmts)));
                setPostingDt(postingD);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void save() {
        this.setMessage("");
        try {
            if (this.branch == null || this.branch.equals("")) {
                this.setMessage("Please select Branch.");
                return;
            }
            if (this.emFlags == null || this.emFlags.equals("--SELECT--") || this.emFlags.equals("")) {
                this.setMessage("Please select Clearing Mode.");
                return;
            }
            if (this.registerDt == null || this.registerDt.equals("--SELECT--") || this.registerDt.equals("")) {
                this.setMessage("Please select Register Date.");
                return;
            }
            if ((postingDt == null) || (postingDt.equalsIgnoreCase(""))) {
                this.setMessage("Posting Date Is Not Set");
                return;
            }
            if ((totalAmt == null) || (totalAmt.equalsIgnoreCase(""))) {
                this.setMessage("Total Amount is Not Set");
                return;
            }

            registerDt = registerDt.toString().trim();
            String dd = registerDt.substring(0, 2);
            String mm = registerDt.substring(3, 5);
            String yy = registerDt.substring(6, 10);
            String txnDate = yy + "" + mm + "" + dd;
//            String saveData = otherTxnRemote.clgOutward2day(userName, txnDate, emFlags, orgnBrCode);
            String saveData = otherTxnRemote.clgOutward2day(userName, txnDate, emFlags, this.branch);
            this.setMessage(saveData);
            setEmFlags("--SELECT--");
            resetAllValue();
            setRegisterDt("--SELECT--");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void resetAllValue() {
        //this.setMessage("");
        setPostingDt("");
        setTotalAmt("");
        setTotalinstrument("");
        //setEmFlags("--SELECT--");
    }

    public void resetAllValue1() {
        setPostingDt("");
        setTotalAmt("");
        setTotalinstrument("");
        setRegisterDt("--SELECT--");
    }

    public String exitFrm() {
        setRegisterDt("--SELECT--");
        setEmFlags("--SELECT--");
        resetAllValue1();
        this.setMessage("");
        return "case1";
    }
}
