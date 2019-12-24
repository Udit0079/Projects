/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.other.LockerManagementFacadeRemote;
import com.cbs.web.pojo.other.LockerRentMasterGrid;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class LockerRentMaster extends BaseBean{

    private String errorMessage;
    private String message;
    private String lockerType;
    private String custCat;
    private Date effDate;
    private boolean flag;
    private String rent;
    private List<SelectItem> lockerTypeList;
    private List<SelectItem> custCatList;
    private List<LockerRentMasterGrid> gridDetail;
    private final String jndiHomeName = "LockerManagementFacade";
    private LockerManagementFacadeRemote beanRemote = null;
    private GeneralMasterFacadeRemote genFacadeRemote;

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

    public List<SelectItem> getCustCatList() {
        return custCatList;
    }

    public void setCustCatList(List<SelectItem> custCatList) {
        this.custCatList = custCatList;
    }

    public List<SelectItem> getLockerTypeList() {
        return lockerTypeList;
    }

    public void setLockerTypeList(List<SelectItem> lockerTypeList) {
        this.lockerTypeList = lockerTypeList;
    }

    public String getCustCat() {
        return custCat;
    }

    public void setCustCat(String custCat) {
        this.custCat = custCat;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public List<LockerRentMasterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<LockerRentMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    /** Creates a new instance of LockerRentMaster */
    public LockerRentMaster() {
        try {
            beanRemote = (LockerManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            genFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            Date date = new Date();
            setEffDate(date);
            this.setErrorMessage("");
            this.setMessage("");
            this.setFlag(true);
            lockerTypeList = new ArrayList<SelectItem>();
            lockerTypeList.add(new SelectItem("--Select--"));
            List glList = genFacadeRemote.getRefCodeAndDescByNo("1500");
            if (glList.isEmpty()) {
                this.setMessage("Please fill data in CBS REF REC TYPE for 1500");
                return;
            } else {
                for (int i = 0; i < glList.size(); i++) {
                    Vector v1 = (Vector) glList.get(i);
                    lockerTypeList.add(new SelectItem(v1.get(0).toString()));
                }
            }
            
//            lockerTypeList.add(new SelectItem("A"));
//            lockerTypeList.add(new SelectItem("B"));
//            lockerTypeList.add(new SelectItem("C"));
//            lockerTypeList.add(new SelectItem("D"));
//            lockerTypeList.add(new SelectItem("E"));
//            lockerTypeList.add(new SelectItem("F"));
            
            
            custCatList = new ArrayList<SelectItem>();
            custCatList.add(new SelectItem("--Select--"));
            custCatList.add(new SelectItem("Customer"));
            custCatList.add(new SelectItem("Staff"));
            custCatList.add(new SelectItem("Special"));
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        this.setErrorMessage("");
        this.setMessage("");
        gridDetail = new ArrayList<LockerRentMasterGrid>();
        try {
            if (this.effDate == null) {
                this.setErrorMessage("Please enter effective date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            List resultList = new ArrayList();
            resultList = beanRemote.gridLoad(this.flag, ymd.format(effDate));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    LockerRentMasterGrid detail = new LockerRentMasterGrid();
                    detail.setEffDate(ele.get(0).toString());
                    detail.setLockerType(ele.get(1).toString());
                    detail.setCustCat(ele.get(2).toString());
                    detail.setRent(Double.parseDouble(ele.get(3).toString()));
                    detail.setEnterBy(ele.get(4).toString());
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void addDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setFlag(false);
        try {
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type.");
                return;
            }
            if (this.custCat.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select customer category.");
                return;
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.rent.equalsIgnoreCase("") || this.rent.length() == 0) {
                this.setErrorMessage("Please enter associated rent.");
                return;
            }
            Matcher rentCheck = p.matcher(rent);
            if (!rentCheck.matches()) {
                this.setErrorMessage("Please enter valid rent (Numbers Only).");
                return;
            }
            if (this.rent.contains(".")) {
                if (this.rent.indexOf(".") != this.rent.lastIndexOf(".")) {
                    this.setErrorMessage("Please enter valid Rent (Numbers Only).");
                    return;
                } else if (this.rent.substring(rent.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please enter valid Rent (Numbers Only), Rent should be upto two decimal places.");
                    return;
                }
            }
            if (Double.parseDouble(rent) < 0) {
                this.setErrorMessage("Associated Rent can not be less than zero !!!");
                return;
            }
            if (this.effDate == null) {
                this.setErrorMessage("Please enter Effective Date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = beanRemote.addDetail(this.flag, this.lockerType, this.custCat, Double.parseDouble(this.rent), ymd.format(effDate), getUserName());
            if (result.equals("Record Inserted Successfully")) {
                this.setMessage("Record inserted successfully.");
            } else {
                this.setErrorMessage(result);
                return;
            }
            gridLoad();
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setFlag(true);
            this.setErrorMessage("");
            this.setMessage("");
            this.setCustCat("--Select--");
            this.setLockerType("--Select--");
            this.setRent("");
            Date date = new Date();
            this.setEffDate(date);
            gridDetail = new ArrayList<LockerRentMasterGrid>();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
