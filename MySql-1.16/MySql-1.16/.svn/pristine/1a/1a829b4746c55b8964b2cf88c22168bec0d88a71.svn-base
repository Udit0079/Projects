/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.io.*;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class BackUp {

    private String userName;
    private String todayDate;
    private String msg;
    private String description;
    private String code;
    private String databasename;
    private HttpServletRequest req;
    File file;
    private final String jndiHomeName = "BankProcessManagementFacade";
    private BankProcessManagementFacadeRemote beanRemote = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDatabasename() {
        return databasename;
    }

    public void setDatabasename(String databasename) {
        this.databasename = databasename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public BackUp() {
        try {
            beanRemote = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            req = getRequest();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            setUserName(req.getRemoteUser());
            List searchList = new ArrayList();
            searchList = beanRemote.getPath();
            Vector val1 = (Vector) searchList.get(0);
            code = val1.get(0).toString();
            description = val1.get(1).toString();
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public void callBackUp() {
        try {
            file = new File(description);
            file.setWritable(true);
            msg = beanRemote.execProc(Integer.parseInt(code));
            file.setWritable(false);
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public void reset() {
        msg = "";
    }

    public String ExitForm() {
        return "case1";
    }
}
