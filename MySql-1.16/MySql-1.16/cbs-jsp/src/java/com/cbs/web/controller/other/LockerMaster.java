/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.other.LockerManagementFacadeRemote;
import com.cbs.web.pojo.other.LockerMasterTable;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
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
public class LockerMaster {

    String orgnBrCode;
    HttpServletRequest req;
    private String todayDate;
    private String userName;
    private String lockerType;
    private String cabinetNo;
    private String keyNo;
    private String lockerNo;
    private String finalMsg;
    private boolean addNewDisable;
    private boolean updateDisable;
    String message;
    private int currentRow;
    private String btnCaption;
    private Integer lastSno;
    private LockerMasterTable currentItem = new LockerMasterTable();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<SelectItem> lockerTypeList;
    private final String jndiHomeName = "LockerManagementFacade";
    private LockerManagementFacadeRemote beanRemote = null;
    private GeneralMasterFacadeRemote genFacadeRemote;

    public String getFinalMsg() {
        return finalMsg;
    }

    public void setFinalMsg(String finalMsg) {
        this.finalMsg = finalMsg;
    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public String getBtnCaption() {
        return btnCaption;
    }

    public void setBtnCaption(String btnCaption) {
        this.btnCaption = btnCaption;
    }

    public Integer getLastSno() {
        return lastSno;
    }

    public void setLastSno(Integer lastSno) {
        this.lastSno = lastSno;
    }

    public LockerMasterTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LockerMasterTable currentItem) {
        this.currentItem = currentItem;
    }

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
    private List<LockerMasterTable> locMaster;

    public List<SelectItem> getLockerTypeList() {
        return lockerTypeList;
    }

    public void setLockerTypeList(List<SelectItem> lockerTypeList) {
        this.lockerTypeList = lockerTypeList;
    }

    public boolean isAddNewDisable() {
        return addNewDisable;
    }

    public void setAddNewDisable(boolean addNewDisable) {
        this.addNewDisable = addNewDisable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public List<LockerMasterTable> getLocMaster() {
        return locMaster;
    }

    public void setLocMaster(List<LockerMasterTable> locMaster) {
        this.locMaster = locMaster;
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

    public LockerMaster() {
        req = getRequest();
        try {
            beanRemote = (LockerManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            genFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            setTodayDate(sdf.format(date));
            setLockerTypeList(new ArrayList<SelectItem>());
            lockerTypeList.add(new SelectItem("0", "--SELECT--"));
            List glList = genFacadeRemote.getRefCodeAndDescByNo("1500");
            if (glList.isEmpty()) {
                this.setMessage("Please fill data in CBS REF REC TYPE for 1500");
                return;
            } else {
                for (int i = 0; i < glList.size(); i++) {
                    Vector v1 = (Vector) glList.get(i);
                    lockerTypeList.add(new SelectItem(v1.get(0).toString(),v1.get(0).toString()));
                }
            }
            
//            lockerTypeList.add(new SelectItem("A", "A"));
//            lockerTypeList.add(new SelectItem("B", "B"));
//            lockerTypeList.add(new SelectItem("C", "C"));
//            lockerTypeList.add(new SelectItem("D", "D"));
//            lockerTypeList.add(new SelectItem("E", "E"));
//            lockerTypeList.add(new SelectItem("F", "F"));
            setAddNewDisable(true);
            setUpdateDisable(true);
            resetValue();
            setMessage("");
            setFinalMsg("");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableValues() {
        try {
            locMaster = new ArrayList<LockerMasterTable>();
            List resultList = new ArrayList();
            resultList = beanRemote.getLockerMasterData(lockerType, orgnBrCode);
            if (resultList.size() <= 0) {
                return;
            }
            for (int i = 0; i < resultList.size(); i++) {
                Vector tableVector = (Vector) resultList.get(i);
                LockerMasterTable mt = new LockerMasterTable();
                mt.setLockerType(tableVector.get(0).toString());
                mt.setCabinetNo(tableVector.get(1).toString());
                mt.setLockerNo(tableVector.get(2).toString());
                mt.setKeyNo(tableVector.get(3).toString());
                if (tableVector.get(4).toString().equalsIgnoreCase("Y")) {
                    mt.setStatus("Full");
                } else {
                    mt.setStatus("Empty");
                }
                mt.setEnterBy(tableVector.get(5).toString());
                mt.setSno(Integer.parseInt(tableVector.get(7).toString()));
                locMaster.add(mt);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void checkCabinetNo() {
        try {
            setMessage("");
            setFinalMsg("");
            if (cabinetNo == null || cabinetNo.equals("")) {
                message = "Please enter the cabinet no. properly";
                setMessage(message);
                return;
            } else {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                Matcher grCodeCheck = p.matcher(cabinetNo);
                if (!grCodeCheck.matches()) {
                    this.setMessage("Please enter the cabinet no. properly");
                    return;
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void checkKeyNo() {
        try {
            setMessage("");
            if (lockerNo == null || lockerNo.equals("")) {
                message = "Please enter locker number properly";
                setMessage(message);
                return;
            }
            if (keyNo == null || keyNo.equals("")) {
                message = "Please enter the key no properly";
                setMessage(message);
                return;
            } else {
                if (isUpdateDisable() == true) {
                    setAddNewDisable(false);
                    btnCaption = "Add";
                } else if (isUpdateDisable() == false) {
                    btnCaption = "Update";
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void checkLockerNo() {
        try {
            setMessage("");
            setFinalMsg("");
            if (lockerNo == null || lockerNo.equals("")) {
                message = "Please enter locker number properly";
                setMessage(message);
                return;
            } else {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                Matcher grCodeCheck = p.matcher(lockerNo);
                if (!grCodeCheck.matches()) {
                    setMessage("Please enter locker number properly");
                    return;
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void select() {
        try {
            this.setMessage("");
            this.setFinalMsg("");
            this.setAddNewDisable(true);
            this.setUpdateDisable(false);
            String statustmp = currentItem.getStatus();
            if (statustmp.equalsIgnoreCase("full")) {
                setMessage("This locker is already full");
                btnCaption = "Add";
                return;
            }
            btnCaption = "Update";
            this.setLockerType(currentItem.getLockerType());
            this.setCabinetNo(currentItem.getCabinetNo().toString());
            this.setLockerNo(currentItem.getLockerNo().toString());
            this.setKeyNo(currentItem.getKeyNo().toString());
            this.lastSno = Integer.parseInt(currentItem.getSno().toString());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        currentRow = currentRow + 1;
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (LockerMasterTable item : locMaster) {
            if (item.getLockerType().equals(lockerType)) {
                currentItem = item;
                break;
            }
        }
    }

    public void saveAction() {
        this.setMessage("");
        this.setFinalMsg("");
        String msg = "";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            if (this.cabinetNo == null || this.cabinetNo.equals("")) {
                msg = "Please enter the Cabinet No. properly.";
            } else {
                Matcher grCodeCheck = p.matcher(this.cabinetNo);
                if (!grCodeCheck.matches()) {
                    msg = "Cabinet No. should be numeric.";
                    setCabinetNo("");
                }
                if (!this.cabinetNo.matches("[0-9]*")) {
                    msg = "Please enter numeric value for Cabinet No.";
                }
            }
            if (this.lockerNo == null || this.lockerNo.equals("")) {
                msg = msg + "Please enter Locker Number properly.";
            } else {
                Matcher grCodeCheck = p.matcher(this.lockerNo);
                if (!grCodeCheck.matches()) {
                    msg = msg + "Locker Number should be numeric.";
                    setLockerNo("");
                }
                if (!this.lockerNo.matches("[0-9]*")) {
                    msg = msg + "Locker Number should be numeric.";
                }
            }
            if (this.keyNo == null || this.keyNo.equals("")) {
                msg = msg + "Please enter the Key No. properly.";
            } else {
                Matcher grCodeCheck = p.matcher(this.keyNo);
                if (!grCodeCheck.matches()) {
                    msg = msg + "Key No. should be numeric.";
                    setKeyNo("");
                }
                if (!this.keyNo.matches("[0-9]*")) {
                    msg = msg + "Key No. should be numeric.";
                }
            }
            if (msg.equalsIgnoreCase("")) {
            } else {
                setMessage(msg);
                return;
            }
            msg = beanRemote.saveLockerMaster(this.lockerType, Float.parseFloat(this.lockerNo), this.btnCaption, this.lastSno,
                    Float.parseFloat(this.cabinetNo), Float.parseFloat(this.keyNo), this.userName, this.orgnBrCode,
                    ymd.format(sdf.parse(this.todayDate)));
            this.setMessage(msg);
            if (msg.equalsIgnoreCase("true")) {
                this.setMessage("");
                this.setFinalMsg("Data saved successfully");
                this.setAddNewDisable(true);
            } else if (msg.equalsIgnoreCase("Check the today date you have passed")) {
                this.setMessage("Check the today date you have passed");
            } else if (msg.equalsIgnoreCase("Record Updated Successfully")) {
                this.setMessage("");
                this.setFinalMsg(msg);
                this.setUpdateDisable(true);
                this.setAddNewDisable(false);
            }

            locMaster.clear();
            getTableValues();
            setLockerNo("");
            setKeyNo("");
            setCabinetNo("");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void resetValue() {
        try {
            this.setLockerType("0");
            this.setLockerNo("");
            this.setKeyNo("");
            this.setCabinetNo("");
            this.setMessage("");
            this.setFinalMsg("");
            locMaster = new ArrayList<LockerMasterTable>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        try {
            resetValue();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
