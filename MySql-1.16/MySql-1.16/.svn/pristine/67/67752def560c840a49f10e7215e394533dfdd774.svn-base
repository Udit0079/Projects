/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareIssue {

    Context ctx;
    private HttpServletRequest req;
    ShareTransferFacadeRemote remoteObject;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private String shareIssueFrom;
    private String noOfShareIssue;
    private Date issueDate = new Date();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNoOfShareIssue() {
        return noOfShareIssue;
    }

    public void setNoOfShareIssue(String noOfShareIssue) {
        this.noOfShareIssue = noOfShareIssue;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getShareIssueFrom() {
        return shareIssueFrom;
    }

    public void setShareIssueFrom(String shareIssueFrom) {
        this.shareIssueFrom = shareIssueFrom;
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

    public ShareIssue() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            // setUserName("manager1");
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            setTodayDate(sdf.format(date));
            this.setMessage("");
            onloadShareIssueForm();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public String btnExit() {
        btnRefresh();
        return "case1";
    }

    public void btnRefresh() {
        this.setMessage("");
        setNoOfShareIssue("");
    }

    public void onloadShareIssueForm() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.noOfShare();
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        setShareIssueFrom(ele.get(0).toString());
                    } else {
                        setShareIssueFrom("1");
                    }
                }
            } else {
                setShareIssueFrom("1");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveBtnAction() {
        if(issueDate.after(date)){
           this.setMessage("Issue date can not be greator than current date");
           return; 
        }
        if (shareIssueFrom == null || shareIssueFrom.equalsIgnoreCase("")) {
            this.setMessage("Please Fill Share Issue From");
            return;
        } else {
            if (!shareIssueFrom.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Share Issue From");
                return;
            }
        }

        if (noOfShareIssue == null || noOfShareIssue.equalsIgnoreCase("")) {
            this.setMessage("Please Fill No.Of Share Issue");
            return;
        } else {
            if (!noOfShareIssue.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in No.Of Share Issue");
                return;
            }
        }
        if (issueDate == null) {
            this.setMessage("Please Fill Issue Date");
            return;
        }
        try {
            String result = remoteObject.saveShareIssueAction(Long.parseLong(shareIssueFrom), Integer.parseInt(noOfShareIssue), userName, ymd.format(issueDate),ymd.format(date), orgnBrCode);
            this.setMessage(result);
            onloadShareIssueForm();
            setNoOfShareIssue("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
