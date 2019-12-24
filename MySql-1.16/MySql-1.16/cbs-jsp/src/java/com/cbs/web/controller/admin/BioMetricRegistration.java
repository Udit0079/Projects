/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.dto.FingerDataObj;
import com.cbs.constant.RoleName;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.utils.Util;
import com.google.gson.Gson;
import com.hrms.web.utils.WebUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author root
 */
public class BioMetricRegistration extends BaseBean {

    public BioMetricRegistration() {
        try {
            beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            objList = new ArrayList<FingerDataObj>();
            setConfirmationMsg("Biometric of this User already registered. Do you want to replace the existing Biometric");

            if (getHttpSession().getAttribute("RegMode") != null) {
                regMode = getHttpSession().getAttribute("RegMode").toString();
                if (regMode.equals("self")) {
                    List dataList = beanRemote.getUserDetails(getUserName());

                    Vector vect = (Vector) dataList.get(0);
                    setUserId(getUserName());
                    setName(vect.get(0).toString());
                    levelId = Integer.parseInt(vect.get(1).toString());

                    setRole(RoleName.getRole(levelId));
                    setAddress(vect.get(2).toString());
                    setBrName(vect.get(3).toString());
                    setTxtDisable(true);
                }
            }else{
                regMode = "";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getUserRegistration() {
        try {
            refresh();
            if (userId.equals("")) {
                throw new ApplicationException("Please fill the userId");
            }
            setIsRegistered(beanRemote.isBioMetricRegistred(userId));
            setBtnLabel("Save");
            if (isIsRegistered()) {
                setBtnLabel("Update");
            } else {
                getUserDetails();
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void getUserDetails() {
        try {
            refresh();
            if (userId.equals("")) {
                throw new ApplicationException("Please fill the userId");
            }
            List dataList = beanRemote.getUserDetails(userId);
            Vector vect = (Vector) dataList.get(0);

            setName(vect.get(0).toString());
            setRole(RoleName.getRole(Integer.parseInt(vect.get(1).toString())));

            setAddress(vect.get(2).toString());
            setBrName(vect.get(3).toString());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void captureFingerOne() {
        try {
            if (userId.equals("")) {
                throw new ApplicationException("Please fill the userId first then press the Capture Button.");
            }
            String data = capture();
            setImageDataOne("data:image/bmp;base64," + data);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void captureFingerTwo() {
        try {
            if (userId.equals("")) {
                throw new ApplicationException("Please fill the userId first then press the Capture Button.");
            }
            String data = capture();
            setImageDataTwo("data:image/bmp;base64," + data);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void captureFingerThree() {
        try {
            if (userId.equals("")) {
                throw new ApplicationException("Please fill the userId first then press the Capture Button.");
            }
            String data = capture();
            setImageDataThree("data:image/bmp;base64," + data);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void captureFingerFour() {
        try {
            if (userId.equals("")) {
                throw new ApplicationException("Please fill the userId first then press the Capture Button.");
            }
            String data = capture();
            setImageDataFour("data:image/bmp;base64," + data);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String capture() {
        try {
            if (userId.equals("")) {
                throw new ApplicationException("Please fill the userId first then press the Capture Button.");
            }
            if (gSon == null) {
                gSon = new Gson();
            }

            String data = "{\"Quality\":60,\"TimeOut\":\"10000\"}";
            String result = Util.postMFS100Client(WebUtil.getClientIP(this.getHttpRequest()), "capture", data);

            FingerDataObj dataObj = gSon.fromJson(result, FingerDataObj.class);
            // System.out.println(Base64.decode(dataObj.IsoTemplate));
            objList.add(dataObj);
            return dataObj.getBitmapData();
        } catch (MalformedURLException e) {
            setMessage(e.getMessage());
        } catch (IOException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return null;
    }

    public void saveUpdateBiometricDetails() {
        try {
            if (userId.equals("")) {
                throw new ApplicationException("Please fill the userId first then press the Capture Button.");
            }
            if (objList.isEmpty()) {
                throw new ApplicationException("Please Capture atleast one finger for Biometric Registration");
            }
            String rs = beanRemote.saveUpdateBiometricDetails(objList, getUserId(), getUserName());
            if (rs.equalsIgnoreCase("True")) {
                refreshPage();
                setMessage("Biometric successfully registered");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshPage() {
        setUserId("");
        refresh();
    }

    public void refresh() {
        setName("");
        setAddress("");
        setBrName("");

        setMessage("");
        setRole("");

        setImageDataOne("");
        setImageDataTwo("");

        setImageDataThree("");
        setImageDataFour("");
        objList = new ArrayList<FingerDataObj>();
    }

    public String exit() {
        try {
            if (regMode.equals("self")) {
                beanRemote.logOut(getUserName());
                getHttpResponse().sendRedirect("/cbs-jsp/j_spring_security_logout");
                return null;
            } else {
                refreshPage();
                return "case1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private final String jndiHomeName = "AdminManagementFacade";
    private AdminManagementFacadeRemote beanRemote = null;
    private String userId;
    private int levelId;
    private String name;
    private String role;
    private String address;
    private String brName;
    private String message;
    private String imageDataOne;
    private String imageDataTwo;
    private String imageDataThree;
    private String imageDataFour;
    private String confirmationMsg;
    private String regMode;
    private boolean isRegistered;
    private boolean txtDisable;
    private String btnLabel = "Save";
    Gson gSon;
    private List<FingerDataObj> objList;

    public boolean isTxtDisable() {
        return txtDisable;
    }

    public void setTxtDisable(boolean txtDisable) {
        this.txtDisable = txtDisable;
    }

    public List<FingerDataObj> getObjList() {
        return objList;
    }

    public void setObjList(List<FingerDataObj> objList) {
        this.objList = objList;
    }

    public String getBtnLabel() {
        return btnLabel;
    }

    public void setBtnLabel(String btnLabel) {
        this.btnLabel = btnLabel;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public boolean isIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }

    public String getImageDataOne() {
        return imageDataOne;
    }

    public void setImageDataOne(String imageDataOne) {
        this.imageDataOne = imageDataOne;
    }

    public String getImageDataTwo() {
        return imageDataTwo;
    }

    public void setImageDataTwo(String imageDataTwo) {
        this.imageDataTwo = imageDataTwo;
    }

    public String getImageDataThree() {
        return imageDataThree;
    }

    public void setImageDataThree(String imageDataThree) {
        this.imageDataThree = imageDataThree;
    }

    public String getImageDataFour() {
        return imageDataFour;
    }

    public void setImageDataFour(String imageDataFour) {
        this.imageDataFour = imageDataFour;
    }
}
