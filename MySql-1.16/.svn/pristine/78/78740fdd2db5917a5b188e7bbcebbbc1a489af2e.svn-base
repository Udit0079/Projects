/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.master.CrrDailyEntryFacadeRemote;
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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class UpdatePlMaster {
    
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private HttpServletRequest req;
    private CrrDailyEntryFacadeRemote plmast;
    private List<SelectItem> grpCodeList;
    private String grpitem;
    private List<SelectItem> SubGrpCodeList;
    private String subgrpitem;
    private List<SelectItem> grpDesc;
    private String grpDescItem;
    private String subgrpdescitem;
    private List<SelectItem> subglcode;
    private String subGlCodeItem;
    private String message;
    private String textvalue1;
    private String textgrpdesc;
    private boolean listsubglcode;
    private boolean newValueGrpDesc;
    private boolean valueSubDesc;
    private boolean update;
    
    public boolean isUpdate() {
        return update;
    }
    
    public void setUpdate(boolean update) {
        this.update = update;
    }
    
    public boolean isNewValueGrpDesc() {
        return newValueGrpDesc;
    }
    
    public void setNewValueGrpDesc(boolean newValueGrpDesc) {
        this.newValueGrpDesc = newValueGrpDesc;
    }
    
    public boolean isValueSubDesc() {
        return valueSubDesc;
    }
    
    public void setValueSubDesc(boolean valueSubDesc) {
        this.valueSubDesc = valueSubDesc;
    }
    
    public boolean isListsubglcode() {
        return listsubglcode;
    }
    
    public void setListsubglcode(boolean listsubglcode) {
        this.listsubglcode = listsubglcode;
    }
    
    public String getTextgrpdesc() {
        return textgrpdesc;
    }
    
    public void setTextgrpdesc(String textgrpdesc) {
        this.textgrpdesc = textgrpdesc;
    }
    
    public String getTextvalue1() {
        return textvalue1;
    }
    
    public void setTextvalue1(String textvalue1) {
        this.textvalue1 = textvalue1;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getSubGlCodeItem() {
        return subGlCodeItem;
    }
    
    public void setSubGlCodeItem(String subGlCodeItem) {
        this.subGlCodeItem = subGlCodeItem;
    }
    
    public List<SelectItem> getSubglcode() {
        return subglcode;
    }
    
    public void setSubglcode(List<SelectItem> subglcode) {
        this.subglcode = subglcode;
    }
    
    public String getSubgrpdescitem() {
        return subgrpdescitem;
    }
    
    public void setSubgrpdescitem(String subgrpdescitem) {
        this.subgrpdescitem = subgrpdescitem;
    }
    
    public String getGrpDescItem() {
        return grpDescItem;
    }
    
    public void setGrpDescItem(String grpDescItem) {
        this.grpDescItem = grpDescItem;
    }
    
    public List<SelectItem> getGrpDesc() {
        return grpDesc;
    }
    
    public void setGrpDesc(List<SelectItem> grpDesc) {
        this.grpDesc = grpDesc;
    }
    
    public List<SelectItem> getSubGrpCodeList() {
        return SubGrpCodeList;
    }
    
    public void setSubGrpCodeList(List<SelectItem> SubGrpCodeList) {
        this.SubGrpCodeList = SubGrpCodeList;
    }
    
    public String getSubgrpitem() {
        return subgrpitem;
    }
    
    public void setSubgrpitem(String subgrpitem) {
        this.subgrpitem = subgrpitem;
    }
    
    public String getOrgnBrCode() {
        return orgnBrCode;
    }
    
    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
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
    
    public List<SelectItem> getGrpCodeList() {
        return grpCodeList;
    }
    
    public void setGrpCodeList(List<SelectItem> grpCodeList) {
        this.grpCodeList = grpCodeList;
    }
    
    public HttpServletRequest getReq() {
        return req;
    }
    
    public void setReq(HttpServletRequest req) {
        this.req = req;
    }
    
    public String getGrpitem() {
        return grpitem;
    }
    
    public void setGrpitem(String grpitem) {
        this.grpitem = grpitem;
    }

    /** Creates a new instance of UpdatePlMaster */
    public UpdatePlMaster() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            plmast = (CrrDailyEntryFacadeRemote) ServiceLocator.getInstance().lookup("CrrDailyEntryFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            grpCodeList = new ArrayList<SelectItem>();
            SubGrpCodeList = new ArrayList<SelectItem>();
            grpDesc = new ArrayList<SelectItem>();
            subglcode = new ArrayList<SelectItem>();
            listsubglcode = true;
            this.newValueGrpDesc = true;
            this.valueSubDesc = true;
            update = true;
            this.setMessage("");
            getgrpcode();
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
    
    public void getgrpcode() {
        try {
            this.setMessage("");
            List groupcode = new ArrayList();
            groupcode = plmast.getgrpcode();
            grpCodeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < groupcode.size(); i++) {
                Vector ele = (Vector) groupcode.get(i);
                for (Object o : ele) {
                    this.grpCodeList.add(new SelectItem(o.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void getsubgrpcode() {
        try {
            this.setMessage("");
            SubGrpCodeList = new ArrayList<SelectItem>();
            List Subgrpcode = new ArrayList();
            if(this.grpitem.equalsIgnoreCase("--Select--")){
                this.setMessage("Please Select valid group code");
                return;
            }
            Subgrpcode = plmast.getsubgrpcode(this.grpitem);
            SubGrpCodeList.add(new SelectItem("--Select--"));
            if (Subgrpcode.isEmpty()) {
                this.setMessage("No sub group code corresponding to this Group code");
                return;
            }
            for (int i = 0; i < Subgrpcode.size(); i++) {
                Vector ele = (Vector) Subgrpcode.get(i);
                for (Object o : ele) {
                    SubGrpCodeList.add(new SelectItem(o.toString()));
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getLocalizedMessage();
        }
        
    }
    
    public void getGroupDesc() {
        this.setMessage("");
        try {            
            grpDesc = new ArrayList<SelectItem>();
            
            if(this.grpitem.equalsIgnoreCase("--Select--")){
                this.setMessage("Please Select valid group code");
                return;
            }
            
            if(this.subgrpitem.equalsIgnoreCase("--Select--")){
                this.setMessage("Please Select valid sub group code");
                return;
            }
            
            List grpDescription = plmast.getGrpDesc(grpitem, subgrpitem);
            if (grpDescription.isEmpty()) {
                this.setMessage("No Group description corresponding to this Sub Group code...");
                return;
            }
            grpDesc.add(new SelectItem("--Select--"));
            for (int i = 0; i < grpDescription.size(); i++) {
                Vector ele = (Vector) grpDescription.get(i);
                for (Object o : ele) {
                    this.grpDesc.add(new SelectItem(o.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void subGlCode() {
        this.setMessage("");
        try {
            if(this.grpitem.equalsIgnoreCase("--Select--")){
                this.setMessage("Please Select valid group code");
                return;
            }
            
            if((this.subgrpitem.equalsIgnoreCase("--Select--")) || (this.subgrpitem.equals("null"))){
                this.setMessage("Please Select valid Sub group code");
                return;
            }
            
            if((this.grpDescItem.equalsIgnoreCase("--Select--")) || (this.grpDescItem.equals("null"))){
                this.setMessage("Please Select valid group Description");
                return;
            }
            
            this.newValueGrpDesc = true;
            setTextgrpdesc("");
            setTextvalue1("");
            List glcode = plmast.subGlCode(grpitem, subgrpitem, this.grpDescItem);
            if (glcode.isEmpty()) {
                this.setMessage("No GL code corresponding to this combination..");
                this.valueSubDesc = true;
                this.listsubglcode = true;
                valueSubDesc = true;
                update = true;
            } else {
                update = false;
                this.listsubglcode = false;
                this.valueSubDesc = false;
                subglcode.add(new SelectItem("--Select--"));
                for (int i = 0; i < glcode.size(); i++) {
                    Vector ele = (Vector) glcode.get(i);
                    for (Object o : ele) {
                        this.subglcode.add(new SelectItem(o.toString()));
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void subgrpdesc() {
        this.setMessage("");
        try {
            update = false;
            List subgrp = plmast.subgrpdesc(grpitem, subgrpitem, this.grpDescItem, this.subGlCodeItem);
            if (subgrp.isEmpty()) {
                this.setMessage("No Sub description corresponding to the selected description...");
                return;
            }
            Vector vector1 = (Vector) subgrp.get(0);
            this.setTextvalue1(vector1.get(0).toString());
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void update() {
        try {
            if (newValueGrpDesc == false) {
                if (this.getTextgrpdesc().equals("")) {
                    this.setMessage("Fill the description field");
                    return;
                }
                updatedescription();
            } else {
                String ss = this.subGlCodeItem;
                if((ss == null) || (this.subGlCodeItem.equalsIgnoreCase("--Select--"))){
                    this.setMessage("Select Sub GL Code");
                    return;
                }
                if (this.getTextvalue1().equals("")) {
                    this.setMessage("Enter the value for Sub description..");
                    return;
                }
                String st1 = plmast.updatePlMaster(this.getTextvalue1(), grpitem, subgrpitem, this.grpDescItem, this.subGlCodeItem);
                if (st1.equals("transaction successful")) {
                    this.setMessage("Updation Successful");
                    update = true;
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void changegrpdesc() {
        try {
            update = false;
            this.newValueGrpDesc = false;
            listsubglcode = true;
            this.valueSubDesc = true;
            setTextgrpdesc("");
            setTextvalue1("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void updatedescription() {
        try {
            String st1 = plmast.updatedesc(grpitem, subgrpitem, getTextgrpdesc());
            if (st1.equals("transaction successful")) {
                this.setMessage("Updation Successful");
                update = true;
            } else {
                this.setMessage("Not updated successfully");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void refresh() {
        try {
            grpCodeList = new ArrayList<SelectItem>();
            
            SubGrpCodeList = new ArrayList<SelectItem>();
            grpDesc = new ArrayList<SelectItem>();
            subglcode = new ArrayList<SelectItem>();
            listsubglcode = true;
            this.newValueGrpDesc = true;
            this.valueSubDesc = true;
            setTextgrpdesc("");
            setTextvalue1("");
            update = true;
            this.setMessage("");
            getgrpcode();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
