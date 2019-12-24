/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.OutwardClearingManagementFacadeRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class RegisterMaintenance {

    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private String message;
    private List<SelectItem> circleTypeOption;
    private String flag;
    private String emFlags;
    private String buttonTab;
    private String loginDt;
    Date entryDate;
    Date postingDate;
    Date clearingDate;
    Date closeCleRegisterDts;
    Date otherCleRegisterDts;
    Date otherOldClearing;
    Date otherNewClearing;
    Date otherOldPosting;
    Date otherNewPosting;
    private final String jndiHomeNameOutward = "OutwardClearingManagementFacade";
    private OutwardClearingManagementFacadeRemote outwardClgRemote = null;
    private final String jndiHomeNameOther = "OtherTransactionManagementFacade";
    private OtherTransactionManagementFacadeRemote otherRemote = null;

    public String getButtonTab() {
        return buttonTab;
    }

    public void setButtonTab(String buttonTab) {
        this.buttonTab = buttonTab;
    }

    public Date getClearingDate() {
        return clearingDate;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public Date getCloseCleRegisterDts() {
        return closeCleRegisterDts;
    }

    public void setCloseCleRegisterDts(Date closeCleRegisterDts) {
        this.closeCleRegisterDts = closeCleRegisterDts;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getOtherCleRegisterDts() {
        return otherCleRegisterDts;
    }

    public void setOtherCleRegisterDts(Date otherCleRegisterDts) {
        this.otherCleRegisterDts = otherCleRegisterDts;
    }

    public Date getOtherNewClearing() {
        return otherNewClearing;
    }

    public void setOtherNewClearing(Date otherNewClearing) {
        this.otherNewClearing = otherNewClearing;
    }

    public Date getOtherNewPosting() {
        return otherNewPosting;
    }

    public void setOtherNewPosting(Date otherNewPosting) {
        this.otherNewPosting = otherNewPosting;
    }

    public Date getOtherOldClearing() {
        return otherOldClearing;
    }

    public void setOtherOldClearing(Date otherOldClearing) {
        this.otherOldClearing = otherOldClearing;
    }

    public Date getOtherOldPosting() {
        return otherOldPosting;
    }

    public void setOtherOldPosting(Date otherOldPosting) {
        this.otherOldPosting = otherOldPosting;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public String getLoginDt() {
        return loginDt;
    }

    public void setLoginDt(String loginDt) {
        this.loginDt = loginDt;
    }

    public String getEmFlags() {
        return emFlags;
    }

    public void setEmFlags(String emFlags) {
        this.emFlags = emFlags;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<SelectItem> getCircleTypeOption() {
        return circleTypeOption;
    }

    public void setCircleTypeOption(List<SelectItem> circleTypeOption) {
        this.circleTypeOption = circleTypeOption;
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

    /** Creates a new instance of RegisterMaintenance */
    public RegisterMaintenance() {
        buttonTab = "--SELECT--";
        try {
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            outwardClgRemote = (OutwardClearingManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOutward);
            otherRemote = (OtherTransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOther);
            getCircleType();
            this.setMessage("");
            getCurrentDates();

            
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

    public void getCurrentDates() {
        try {
            String dates = outwardClgRemote.getCurrentDate(orgnBrCode);
            setLoginDt(dates);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void getCircleType() {
        this.setMessage("");
        circleTypeOption = new ArrayList<SelectItem>();
        try {
            List circleTyp = otherRemote.getClearingOption();
            for (int i = 0; i < circleTyp.size(); i++) {
                Vector vecForcircleTyp = (Vector) circleTyp.get(i);
                circleTypeOption.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void open() {
        this.setMessage("");
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Circle Type");
            return;
        }
        if (!this.buttonTab.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Posting And Clearing");
            return;
        }
        flag = "1";
    }

    public void close() {
        this.setMessage("");
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Circle Type");
            return;
        }
        if (!this.buttonTab.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Posting And Clearing");
            return;
        }
        flag = "2";
    }

    public void others() {
        this.setMessage("");
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Circle Type");
            return;
        }
        flag = "3";
    }

    public void allDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.setMessage("");
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Circle Type");
            return;
        }
        try {
            String date = outwardClgRemote.getEntryOpenDate(emFlags, orgnBrCode);
            String[] values = null;
            String spliter = ":";
            values = date.split(spliter);
            String currentDt = values[0];
            String postingDt = values[1];
            String ClearingDt = values[2];
            String currentDts = currentDt.substring(6, 8) + "/" + currentDt.substring(4, 6) + "/" + currentDt.substring(0, 4);
            setEntryDate(sdf.parse(currentDts));
            setCloseCleRegisterDts(sdf.parse(currentDts));
            setOtherCleRegisterDts(sdf.parse(currentDts));
            String postingDts = postingDt.substring(6, 8) + "/" + postingDt.substring(4, 6) + "/" + postingDt.substring(0, 4);
            setPostingDate(sdf.parse(postingDts));
            String ClearingDts = ClearingDt.substring(6, 8) + "/" + ClearingDt.substring(4, 6) + "/" + ClearingDt.substring(0, 4);
            setClearingDate(sdf.parse(ClearingDts));

            String returnDt = outwardClgRemote.otherButton(emFlags, orgnBrCode);
            String[] values1 = null;
            String spliter1 = ": ";
            values1 = returnDt.split(spliter1);
            String post = values1[0];
            String clear = values1[1];
            if (post.equals("null") && clear.equals("null")) {
                setOtherOldClearing(sdf.parse(currentDts));
                setOtherOldPosting(sdf.parse(currentDts));
                setOtherNewClearing(sdf.parse(currentDts));
                setOtherNewPosting(sdf.parse(currentDts));
            } else {
                SimpleDateFormat ddmmyy = new SimpleDateFormat("dd/MM/yyyy");
                setOtherNewPosting(ddmmyy.parse(post));
                setOtherOldPosting(ddmmyy.parse(post));
                setOtherNewClearing(ddmmyy.parse(clear));
                setOtherOldClearing(ddmmyy.parse(clear));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void saveRegisterMaintenance() {
        this.setMessage("");
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (entryDate == null) {
                this.setMessage("Please Enter Entry Date");
                return;
            }
            if (postingDate == null) {
                this.setMessage("Please Enter Posting Date");
                return;
            }
            if (clearingDate == null) {
                this.setMessage("Please Enter Clearing Date");
                return;
            }
            String entryD = ymd.format(this.entryDate);
            String postingD = ymd.format(this.postingDate);
            String clearingD = ymd.format(this.clearingDate);
            String save = outwardClgRemote.saveDataRegisterMaintenance(emFlags, entryD, postingD, clearingD, userName, orgnBrCode);
            this.setMessage(save);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void yesButtonRegisterMaintenance() {
        this.setMessage("");
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (closeCleRegisterDts == null) {
                this.setMessage("Please Enter Clearing Register Dated");
                return;
            }
            String clearRD = ymd.format(this.closeCleRegisterDts);
            String close = outwardClgRemote.yesButtonRegisterClose(emFlags, clearRD, userName, orgnBrCode);
            this.setMessage(close);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void changeButtonRegisterMaintenance() {
        if (this.buttonTab.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Posting And Clearing");
            return;
        }
        flag = "3";
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        this.setMessage("");
        try {
            if (otherCleRegisterDts == null) {
                this.setMessage("Please Enter Clearing Register Dated");
                return;
            }
            if (buttonTab.equals("Posting")) {
                if (otherNewPosting == null) {
                    this.setMessage("Please Enter New Posting Date");
                    return;
                }
            }
            if (buttonTab.equals("Clearing")) {
                if (otherNewClearing == null) {
                    this.setMessage("Please Enter New Clearing Date");
                    return;
                }
            }
            String otherCleRDts = ymd.format(this.otherCleRegisterDts);
            String newPosting = ymd.format(this.otherNewPosting);
            String oldClearing = ymd.format(this.otherOldClearing);
            String newClearing = ymd.format(this.otherNewClearing);
            String oldposting = ymd.format(this.otherOldPosting);
            String change = outwardClgRemote.changeButtonRegisterMaintenance(emFlags, otherCleRDts, buttonTab, newPosting, oldClearing, newClearing, oldposting, orgnBrCode);
            this.setMessage(change);
            if (change.equals("Date Changed Successfully")) {
                String returnDt = outwardClgRemote.onChangeOfOtherDateCalander(emFlags, orgnBrCode, otherCleRDts);
                if (returnDt.contains(":")) {
                    String[] values1 = null;
                    String spliter1 = ": ";
                    values1 = returnDt.split(spliter1);
                    String post = values1[0];
                    String clear = values1[1];
                    SimpleDateFormat ddmmyy = new SimpleDateFormat("dd/MM/yyyy");
                    setOtherNewPosting(ddmmyy.parse(post));
                    setOtherOldPosting(ddmmyy.parse(post));
                    setOtherNewClearing(ddmmyy.parse(clear));
                    setOtherOldClearing(ddmmyy.parse(clear));
                } else if (returnDt.equalsIgnoreCase("Posting Date Cannot Be Changed.")) {
                    String returnDt1 = outwardClgRemote.onChangeOfOtherDateCalander1(emFlags, orgnBrCode, otherCleRDts);
                    String[] values1 = null;
                    String spliter1 = ": ";
                    values1 = returnDt1.split(spliter1);
                    String post = values1[0];
                    String clear = values1[1];
                    SimpleDateFormat ddmmyy = new SimpleDateFormat("dd/MM/yyyy");
                    setOtherNewPosting(ddmmyy.parse(post));
                    setOtherOldPosting(ddmmyy.parse(post));
                    setOtherNewClearing(ddmmyy.parse(clear));
                    setOtherOldClearing(ddmmyy.parse(clear));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void onChangeOtherButtonCalender() {
        this.setMessage("");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            if (otherCleRegisterDts == null) {
                this.setMessage("Please Enter Clearing Register Dated");
                return;
            }
            String otherCleRDts = ymd.format(this.otherCleRegisterDts);
            String returnDt = outwardClgRemote.onChangeOfOtherDateCalander(emFlags, orgnBrCode, otherCleRDts);
            if (returnDt.contains(":")) {
                String[] values1 = null;
                String spliter1 = ": ";
                values1 = returnDt.split(spliter1);
                String post = values1[0];
                String clear = values1[1];
                SimpleDateFormat ddmmyy = new SimpleDateFormat("dd/MM/yyyy");
                setOtherNewPosting(ddmmyy.parse(post));
                setOtherOldPosting(ddmmyy.parse(post));
                setOtherNewClearing(ddmmyy.parse(clear));
                setOtherOldClearing(ddmmyy.parse(clear));
            } else if (returnDt.equalsIgnoreCase("Posting Date Cannot Be Changed.")) {
                String returnDt1 = outwardClgRemote.onChangeOfOtherDateCalander1(emFlags, orgnBrCode, otherCleRDts);
                String[] values1 = null;
                String spliter1 = ": ";
                values1 = returnDt1.split(spliter1);
                String post = values1[0];
                String clear = values1[1];
                SimpleDateFormat ddmmyy = new SimpleDateFormat("dd/MM/yyyy");
                setOtherNewPosting(ddmmyy.parse(post));
                setOtherOldPosting(ddmmyy.parse(post));
                setOtherNewClearing(ddmmyy.parse(clear));
                setOtherOldClearing(ddmmyy.parse(clear));
                this.setMessage(returnDt);
            } else {
                this.setMessage(returnDt);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void resetMessage() {    
        this.setMessage("");
    }

    public void resetAllValue() {
        this.setMessage("");
        setEmFlags("--SELECT--");
        setButtonTab("--SELECT--");
        flag = "10";
    }

    public void resetAllValueNo() {
        this.setMessage("");
        setEmFlags("--SELECT--");
        setButtonTab("--SELECT--");
        flag = "10";
    }

    public String exitFrm() {
        this.setMessage("");
        setEmFlags("--SELECT--");
        setButtonTab("--SELECT--");
        flag = "10";
        return "case1";
    }
}
