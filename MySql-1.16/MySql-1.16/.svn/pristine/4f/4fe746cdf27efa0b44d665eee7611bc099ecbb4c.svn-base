/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Zeeshan Waris
 */
public class CategoryForm extends BaseBean{

    ShareTransferFacadeRemote remoteObject;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private String message;
    private String folioNo, folioNoShow, acNoLen;
    private String Name;
    private String fatherName;
    private String address;
    private String stxtArea;
    private String category;
    private String mode;
    Date date = new Date();
    private List<SelectItem> categoryType;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getFolioNoShow() {
        return folioNoShow;
    }

    public void setFolioNoShow(String folioNoShow) {
        this.folioNoShow = folioNoShow;
    }

    public List<SelectItem> getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(List<SelectItem> categoryType) {
        this.categoryType = categoryType;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getStxtArea() {
        return stxtArea;
    }

    public void setStxtArea(String stxtArea) {
        this.stxtArea = stxtArea;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public CategoryForm() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            setMode("Save");
            this.setMessage("");
            getDropDownList();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getDropDownList() {
        try {
            List resultList = new ArrayList();
            resultList = remoteObject.categoryDetail();
            if (!resultList.isEmpty()) {
                categoryType = new ArrayList<SelectItem>();
                categoryType.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        categoryType.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                categoryType = new ArrayList<SelectItem>();
                categoryType.add(new SelectItem("--Select--"));
            }

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
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
                this.setMessage("Please fill Folio No.");
                return;
            }
            if (folioNo.length() < 12) {
                this.setMessage("Please enter 12 digit  Folio No.");
                return;
            }
            List resultList = new ArrayList();
            resultList = remoteObject.CategoryfolioDetail(folioNo);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    String firstName = "";
                    String lastName = "";
                    if (ele.get(0) != null) {
                        firstName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        lastName = ele.get(1).toString();
                    }
                    setName(firstName + " " + lastName);
                    if (ele.get(2) != null) {
                        setFatherName(ele.get(2).toString());
                    }
                    String houseNo = "";
                    String sector = "";
                    String city = "";
                    String perAddress = "";
                    if (ele.get(3) != null) {
                        houseNo = ele.get(3).toString();
                    }
                    if (ele.get(4) != null) {
                        sector = ele.get(4).toString();
                    }
                    if (ele.get(5) != null) {
                        city = ele.get(5).toString();
                    }
                    if (ele.get(6) != null) {
                        perAddress = ele.get(6).toString();
                    }
                    setAddress(houseNo + "," + sector + "," + city + "," + perAddress);

                    if (ele.get(7) != null) {
                        if (ele.get(7).toString().equalsIgnoreCase("")) {
                            setMode("Save");
                            setStxtArea(ele.get(7).toString());
                        } else {
                            setMode("Update");
                            setStxtArea(ele.get(7).toString());
                        }
                    }
                }
            } else {
                this.setMessage("Folio No. Does Not Exist");
                clear();
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
        setCategory("--Select--");
        setAddress("");
        setFatherName("");
        setStxtArea("");
        setFolioNo("");
        setMode("Save");
    }

    public void clear() {
        setName("");
        setCategory("--Select--");
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
                this.setMessage("Please enter 12 digit Folio No.");
                return "false";
            }
            if (category.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Category");
                return "false";
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
        try {
            String result = remoteObject.CategorySaveUpdateAction(mode, category, folioNo);
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

    public void deleteBtnAction() {
        if (stxtArea == null || stxtArea.equalsIgnoreCase("")) {
            this.setMessage("Category Does Not Exists For This Folio No.");
            return;
        }
        try {
            String result = remoteObject.deleteCategoryMaintenanceAction(folioNo);
            this.setMessage(result);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
