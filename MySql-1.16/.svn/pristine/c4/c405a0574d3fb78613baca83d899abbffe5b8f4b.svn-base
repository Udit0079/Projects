/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
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
public class AreaWiseMasterForm extends BaseBean {

    Context ctx;
    private HttpServletRequest req;
    ShareTransferFacadeRemote remoteObject;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private String folioNo;
    private String Name;
    private String fatherName;
    private String address;
    private String stxtArea;
    private String area;
    private String mode;
    private String folioNumber, folioNoShow, acNoLen;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getFolioNoShow() {
        return folioNoShow;
    }

    public void setFolioNoShow(String folioNoShow) {
        this.folioNoShow = folioNoShow;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getStxtArea() {
        return stxtArea;
    }

    public void setStxtArea(String stxtArea) {
        this.stxtArea = stxtArea;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public AreaWiseMasterForm() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            this.setAcNoLen(getAcNoLength());
            // setUserName("manager1");
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            setTodayDate(sdf.format(date));
            setMode("Save");
            this.setMessage("");
            //onloadCheckAction();
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

    public void onblurFolioDetails() {
        setMessage("");
        if (folioNoShow == null || folioNoShow.equalsIgnoreCase("")) {
            setMessage("Please fill Folio No.");
            return;
        }
        //if (folioNoShow.length() < 12) {
        if (!this.folioNoShow.equalsIgnoreCase("") && ((this.folioNoShow.length() != 12) && (this.folioNoShow.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            setMessage("Please fill proper Folio No.");
            return;
        }
        try {
            folioNo = ftsPostRemote.getNewAccountNumber(folioNoShow);
            onblurFolioHolderDetails();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onblurFolioHolderDetails() {
        try {
            this.setMessage("");
            if (folioNo == null || folioNo.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Folio No.");
                return;
            }
            if (folioNo.length() < 12) {
                this.setMessage("Please enter 12 digit  Folio No.");
                return;
            }

            this.setMessage("");
            List resultList = new ArrayList();
            resultList = remoteObject.folioDetail(folioNo);
             if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        folioNumber = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        setName(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                        setFatherName(ele.get(2).toString());
                    }
                    String sector = "";
                    String city = "";
                    if (ele.get(3) != null) {
                        sector = ele.get(3).toString();
                    }
                    if (ele.get(4) != null) {
                        city = ele.get(4).toString();
                    }
                    setAddress(sector + city);

                    if (ele.get(5) != null) {
                        if (ele.get(5).toString().equalsIgnoreCase("")) {
                            setMode("Save");
                            setStxtArea(ele.get(5).toString());
                        } else {
                            setMode("Update");
                            setStxtArea(ele.get(5).toString());
                        }
                    }
                }
            } else {
                this.setMessage("Folio No. Does Not Exist");
            }

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String btnExit() {
        btnRefresh();
        return "case1";
    }

    public void btnRefresh() {
        this.setMessage("");
        setName("");
        setArea("");
        setAddress("");
        setFatherName("");
        setStxtArea("");
        setFolioNo("");
        setMode("Save");
    }

    public void clear() {
        setName("");
        setArea("");
        setAddress("");
        setFatherName("");
        setStxtArea("");
        setFolioNo("");
        setMode("Save");
    }

    public String onblurChecking() {
        try {

            if (folioNo == null || folioNo.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Folio No.");
                return "false";
            }
            if (folioNo.length() < 12) {
                this.setMessage("Please Enter Numeric Value in Folio No.");
                return "false";
            }

            if (area == null || area.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Area");
                return "false";
            } else {
                if (!area.matches("[0-9a-zA-Z,_/ ]*")) {
                    this.setMessage("Please enter alpha numeric value in Area");
                    return "false";
                }
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "true";
    }

    public void saveBtnAction() {
        if (onblurChecking().equalsIgnoreCase("false")) {
            return;
        }
        if (folioNumber == null || folioNumber.equalsIgnoreCase("")) {
            this.setMessage("Folio No. IS BLANK !");
            return;
        }
        try {
            String result = remoteObject.saveUpdateAction(area, folioNumber);
            if (result.equalsIgnoreCase("true") && mode.equalsIgnoreCase("Update")) {
                this.setMessage("Data has been updated successfully");
            } else if (result.equalsIgnoreCase("true") && mode.equalsIgnoreCase("Save")) {
                this.setMessage("Data has been saved successfully");
            } else {
                this.setMessage(result);
            }
            clear();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

//    public void onloadCheckAction() {
//        try {
//            //remoteObject.onloadAction();
//        } catch (ApplicationException e) {
//            setMessage(e.getLocalizedMessage());
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }
}
