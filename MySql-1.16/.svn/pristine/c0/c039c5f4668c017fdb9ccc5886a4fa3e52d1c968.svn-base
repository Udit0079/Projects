/*
 * CREATED BY    :   ROIT KRISHNA GUPTA
 * CREATION DATE :   21 JULY 2011
 */
package com.cbs.web.controller.inventory;

import com.cbs.dto.InventoryMasterGridFile;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
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
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class InventoryTypeMaster {

    Context ctx;
    InventorySplitAndMergeFacadeRemote invMaster;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String invtClass;
    private String invtClassDesc;
    private String invtType;
    //private String invtTypeDesc;
    private List<InventoryMasterGridFile> gridDetail;
    int currentRow;
    private InventoryMasterGridFile currentItem = new InventoryMasterGridFile();
    private HttpServletRequest req;
    private boolean addFlag;
    private boolean modFlag;
    private boolean delFlag;
    private String invtClassDd;
    private List<SelectItem> invtClassList;
    private boolean disFlag1;
    private boolean addNewFlag;
    private String acNature;
    private List<SelectItem> acNatureList;

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public boolean isAddNewFlag() {
        return addNewFlag;
    }

    public void setAddNewFlag(boolean addNewFlag) {
        this.addNewFlag = addNewFlag;
    }

    public boolean isDisFlag1() {
        return disFlag1;
    }

    public void setDisFlag1(boolean disFlag1) {
        this.disFlag1 = disFlag1;
    }

    public String getInvtClassDd() {
        return invtClassDd;
    }

    public void setInvtClassDd(String invtClassDd) {
        this.invtClassDd = invtClassDd;
    }

    public List<SelectItem> getInvtClassList() {
        return invtClassList;
    }

    public void setInvtClassList(List<SelectItem> invtClassList) {
        this.invtClassList = invtClassList;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isModFlag() {
        return modFlag;
    }

    public void setModFlag(boolean modFlag) {
        this.modFlag = modFlag;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public InventoryMasterGridFile getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InventoryMasterGridFile currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<InventoryMasterGridFile> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<InventoryMasterGridFile> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getInvtClass() {
        return invtClass;
    }

    public void setInvtClass(String invtClass) {
        this.invtClass = invtClass;
    }

    public String getInvtClassDesc() {
        return invtClassDesc;
    }

    public void setInvtClassDesc(String invtClassDesc) {
        this.invtClassDesc = invtClassDesc;
    }

    public String getInvtType() {
        return invtType;
    }

    public void setInvtType(String invtType) {
        this.invtType = invtType;
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

    /** Creates a new instance of InventoryTypeMaster */
    public InventoryTypeMaster() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            invMaster = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup("InventorySplitAndMergeFacade");
            this.setMessage("");
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("--Select--"));
            functionList.add(new SelectItem("A", "ADD"));
            functionList.add(new SelectItem("M", "MODIFY"));
            //functionList.add(new SelectItem("E", "ENQUIRY"));
            invtClassList = new ArrayList<SelectItem>();
            invtClassList.add(new SelectItem("--Select--"));
            this.setAddFlag(false);
            this.setModFlag(false);
            this.setDelFlag(false);
            this.setDisFlag1(false);
            this.setAddNewFlag(false);
            gridDetail = new ArrayList<InventoryMasterGridFile>();
            getAccountNatureList();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getAccountNatureList() {
        acNatureList = new ArrayList<SelectItem>();
        try {
            List resultList = invMaster.getAccountNatureList();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector element = (Vector) resultList.get(i);
                    acNatureList.add(new SelectItem(element.get(0).toString()));
                }
            }
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
    Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-][a-zA-Z0-9,]+)*");

    public void inventoryClassListDetail() {
        try {
            List resultList = new ArrayList();
            resultList = invMaster.inventoryClassCombo();
            invtClassList = new ArrayList<SelectItem>();
            invtClassList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    invtClassList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private InventoryMasterGridFile createGridDetailObj() {
        InventoryMasterGridFile txnBean = new InventoryMasterGridFile();
        try {
            txnBean.setInvtClass(this.invtClass);
            txnBean.setInvtClassDesc(this.invtClassDesc);
            txnBean.setInvtType(this.invtType);
            txnBean.setAcNature(acNature);
            txnBean.setEnterBy(this.userName);
            txnBean.setEnterDate(this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return txnBean;
    }

    public void gridLoadOnSubmitBtn() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (!validations()) {
                return;
            }
            InventoryMasterGridFile txnBean = createGridDetailObj();
            gridDetail.add(txnBean);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if ((this.function.equalsIgnoreCase("A") && this.addFlag == true) || this.addNewFlag == true) {
                gridDetail.remove(currentRow);
            } else {
                String result = invMaster.deleteRecord(currentItem.getInvtClass(), currentItem.getInvtType(), currentItem.getAcNature());
                if (result == null || result.equalsIgnoreCase("")) {
                    this.setErrorMessage("System error occured !!!");
                    return;
                } else if (result.contains("!")) {
                    this.setErrorMessage(result);
                    gridLoadAfterSaveUpdateDelete();
                } else {
                    this.setMessage(result);
                    this.setInvtClassDd(currentItem.getInvtClass());
                    gridLoadAfterSaveUpdateDelete();
                }
                this.setInvtClass("");
                this.setInvtClassDesc("");
                this.setInvtType("");
                //this.setInvtTypeDesc("");
            }

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public boolean validations() {
        try {
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select function.");
                return false;
            }
            if (this.invtClass == null || this.invtClass.equalsIgnoreCase("") || this.invtClass.length() == 0) {
                this.setErrorMessage("Please enter inventory class.");
                return false;
            }
            Matcher invtClassCheck = pm.matcher(this.invtClass);
            if (!invtClassCheck.matches()) {
                this.setErrorMessage("Inventory class should not contain special characters !!!");
                return false;
            }
            if (this.invtClassDesc == null || this.invtClassDesc.equalsIgnoreCase("") || this.invtClassDesc.length() == 0) {
                this.setErrorMessage("Please enter inventory class description.");
                return false;
            }
            Matcher invtClassDescCheck = pm.matcher(this.invtClassDesc);
            if (!invtClassDescCheck.matches()) {
                this.setErrorMessage("Inventory class description should not contain special characters !!!");
                return false;
            }
            if (this.invtType == null || this.invtType.equalsIgnoreCase("") || this.invtType.length() == 0) {
                this.setErrorMessage("Please enter inventory type.");
                return false;
            }
            Matcher invtTypeCheck = pm.matcher(this.invtType);
            if (!invtTypeCheck.matches()) {
                this.setErrorMessage("Inventory type should not contain special characters !!!");
                return false;
            }
//            if (this.invtTypeDesc == null || this.invtTypeDesc.equalsIgnoreCase("") || this.invtTypeDesc.length() == 0) {
//                this.setInvtTypeDesc("");
//            }
//            Matcher invtTypeDescCheck = pm.matcher(this.invtTypeDesc);
//            if (!invtTypeDescCheck.matches()) {
//                this.setErrorMessage("Inventory type description should not contain special characters !!!");
//                return false;
//            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return true;
    }

    public void saveMasterDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (!validations()) {
                return;
            }
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setErrorMessage("Please submit record to grid first then press enter.");
                return;
            }
            String result = invMaster.saveRecord(gridDetail, orgnBrCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
                return;
            } else {
                this.setMessage(result);
            }
            this.setAddFlag(false);
            this.setInvtClass("");
            this.setInvtClassDesc("");
            this.setInvtType("");
            //this.setInvtTypeDesc("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updateInvtTypeDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (!this.invtClass.equalsIgnoreCase(currentItem.getInvtClass())) {
                this.setErrorMessage("Sorry,inventory class cannot be changed !!!");
                return;
            }
            if (!this.invtClassDesc.equalsIgnoreCase(currentItem.getInvtClassDesc())) {
                this.setErrorMessage("Sorry,inventory class description cannot be changed !!!");
                return;
            }
            if (this.invtType == null || this.invtType.equalsIgnoreCase("") || this.invtType.length() == 0) {
                this.setErrorMessage("Please enter inventory type.");
                return;
            }
            Matcher invtTypeCheck = pm.matcher(this.invtType);
            if (!invtTypeCheck.matches()) {
                this.setErrorMessage("Inventory type should not contain special characters !!!");
                return;
            }
//            if (this.invtTypeDesc == null || this.invtTypeDesc.equalsIgnoreCase("") || this.invtTypeDesc.length() == 0) {
//                this.setInvtTypeDesc("");
//            }
            String result = invMaster.updateRecord(this.invtClass, currentItem.getInvtType(), currentItem.getAcNature(), this.invtType, this.acNature, this.userName);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
                gridLoadAfterSaveUpdateDelete();
            } else {
                this.setMessage(result);
                gridLoadAfterSaveUpdateDelete();
            }
            this.setInvtType("");
            //this.setInvtTypeDesc("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void functionOnBlurMethod() {
        try {
            this.setAddFlag(false);
            this.setModFlag(false);
            this.setDelFlag(false);
            this.setDisFlag1(false);
            this.setInvtClass("");
            this.setInvtClassDesc("");
            this.setInvtType("");
            //this.setInvtTypeDesc("");
            this.setErrorMessage("");
            this.setMessage("");
            gridDetail = new ArrayList<InventoryMasterGridFile>();
            this.setInvtClassDd("--Select--");
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select function.");
                return;
            } else {
                if (this.function.equalsIgnoreCase("A")) {
                    this.setAddFlag(true);
                    this.setModFlag(false);
                    this.setDelFlag(false);
                    this.setDisFlag1(false);
                } else if (this.function.equalsIgnoreCase("M")) {
                    this.setAddFlag(false);
                    this.setModFlag(true);
                    this.setDelFlag(false);
                    this.setDisFlag1(true);
                    inventoryClassListDetail();
                } else {
                    this.setAddFlag(false);
                    this.setModFlag(false);
                    this.setDelFlag(true);
                }
            }

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void invtClassComboOnblur() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setInvtClass("");
            this.setInvtClassDesc("");
            this.setInvtType("");
            //this.setInvtTypeDesc("");
            gridDetail = new ArrayList<InventoryMasterGridFile>();
            if (this.invtClassDd.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory class.");
                return;
            }
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            resultList = invMaster.gridDetailOnModify(this.invtClassDd);
            resultList1 = invMaster.inventoryClassAndDescription(this.invtClassDd);
            if (!resultList1.isEmpty()) {
                Vector ele1 = (Vector) resultList1.get(0);
                this.setInvtClass(ele1.get(0).toString());
                this.setInvtClassDesc(ele1.get(1).toString());
            }
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    InventoryMasterGridFile detail = new InventoryMasterGridFile();
                    detail.setInvtClass(ele.get(0).toString());
                    detail.setInvtClassDesc(ele.get(1).toString());
                    detail.setInvtType(ele.get(2).toString());
                    detail.setAcNature(ele.get(3).toString());
                    detail.setEnterBy(ele.get(4).toString());
                    detail.setEnterDate(ele.get(5).toString());
                    gridDetail.add(detail);
                }
                this.setAddNewFlag(false);
            } else {
                gridDetail = new ArrayList<InventoryMasterGridFile>();
                this.setErrorMessage("No record exists for this inventory class.");
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoadAfterSaveUpdateDelete() {
        try {
            gridDetail = new ArrayList<InventoryMasterGridFile>();
            if (this.invtClassDd.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory class.");
                return;
            }
            List resultList = new ArrayList();
            resultList = invMaster.gridDetailOnModify(this.invtClassDd);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    InventoryMasterGridFile detail = new InventoryMasterGridFile();
                    detail.setInvtClass(ele.get(0).toString());
                    detail.setInvtClassDesc(ele.get(1).toString());
                    detail.setInvtType(ele.get(2).toString());
                    detail.setAcNature(ele.get(3).toString());
                    detail.setEnterBy(ele.get(4).toString());
                    detail.setEnterDate(ele.get(5).toString());
                    gridDetail.add(detail);
                }
            } else {
                gridDetail = new ArrayList<InventoryMasterGridFile>();
                this.setErrorMessage("No record exists for this inventory class.");
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setInvtClass("");
            this.setInvtClassDesc("");
            this.setInvtType("");
            //this.setInvtTypeDesc("");
            this.setInvtClass(currentItem.getInvtClass());
            this.setInvtClassDesc(currentItem.getInvtClassDesc());
            this.setInvtType(currentItem.getInvtType());
            this.setAcNature(currentItem.getAcNature());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void invtClassDescOnblur() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.function.equalsIgnoreCase("A")) {
                this.setErrorMessage("Please ensure that you have entered correct inventory class and description because in future these could not be updated !!!");
                if (this.invtClass == null || this.invtClass.equalsIgnoreCase("") || this.invtClass.length() == 0) {
                    this.setErrorMessage("Please enter inventory class carefully.");
                    return;
                }
                Matcher invtClassCheck = pm.matcher(this.invtClass);
                if (!invtClassCheck.matches()) {
                    this.setErrorMessage("Inventory class should not contain special characters !!!");
                    return;
                }
                if (this.invtClassDesc == null || this.invtClassDesc.equalsIgnoreCase("") || this.invtClassDesc.length() == 0) {
                    this.setErrorMessage("Please enter inventory class description carefully.");
                    return;
                }
                Matcher invtClassDescCheck = pm.matcher(this.invtClassDesc);
                if (!invtClassDescCheck.matches()) {
                    this.setErrorMessage("Inventory class description should not contain special characters !!!");
                    return;
                }
                this.setDisFlag1(true);
            } else {
                this.setDisFlag1(false);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void addNewRecordBtn() {
        try {
            gridDetail = new ArrayList<InventoryMasterGridFile>();
            this.setErrorMessage("");
            this.setMessage("");
            if (this.function.equalsIgnoreCase("M") && this.modFlag == true) {
                this.setAddNewFlag(true);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveNewInventoryTypes() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (!validations()) {
                return;
            }
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setErrorMessage("Please submit record to grid first then press enter.");
                return;
            }
            String result = invMaster.addNewInvtTypeInExistingInvtClass(gridDetail, orgnBrCode);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
                return;
            } else {
                this.setMessage(result);
                gridLoadAfterSaveUpdateDelete();
            }
            this.setInvtType("");
            //this.setInvtTypeDesc("");
            this.setAddNewFlag(false);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setInvtClass("");
            this.setInvtClassDesc("");
            this.setInvtType("");
            //this.setInvtTypeDesc("");
            this.setFunction("--Select--");
            this.setInvtClassDd("--Select--");
            this.setAddFlag(false);
            this.setModFlag(false);
            this.setDelFlag(false);
            this.setDisFlag1(false);
            this.setAddNewFlag(false);
            gridDetail = new ArrayList<InventoryMasterGridFile>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
