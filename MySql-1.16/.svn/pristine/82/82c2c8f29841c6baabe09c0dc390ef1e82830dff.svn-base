/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.inventory;

import com.cbs.dto.InventorySplitMergeGridFile;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class InventorySplitAndMerge {

    Context ctx;
    InventorySplitAndMergeFacadeRemote invtSplitMerge;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String function;
    private List<SelectItem> functionList;
    private HttpServletRequest req;
    private String brnId;
    private String brnName;
//    private String locClass;
//    private List<SelectItem> locClassList;
    private String invtClass;
    private List<SelectItem> invtClassList;
    private String invtType;
    private List<SelectItem> invtTypeList;
    private String startNo;
    private String endNo;
    private String quantity;
    private boolean quanDisFlag;
    private String itemsPerUnit;
    private List<InventorySplitMergeGridFile> gridDetail;
    int currentRow;
    private InventorySplitMergeGridFile currentItem = new InventorySplitMergeGridFile();
    private boolean splitFlag;
    private boolean mergeFlag;
    private String invtSrNo;
    //private boolean itemFlag;
    private boolean disFlag;
    private List<SelectItem> chequeSeriesList;

    public List<SelectItem> getChequeSeriesList() {
        return chequeSeriesList;
    }

    public void setChequeSeriesList(List<SelectItem> chequeSeriesList) {
        this.chequeSeriesList = chequeSeriesList;
    }

    public boolean isDisFlag() {
        return disFlag;
    }

    public void setDisFlag(boolean disFlag) {
        this.disFlag = disFlag;
    }

    public String getInvtSrNo() {
        return invtSrNo;
    }

    public void setInvtSrNo(String invtSrNo) {
        this.invtSrNo = invtSrNo;
    }

    public boolean isMergeFlag() {
        return mergeFlag;
    }

    public void setMergeFlag(boolean mergeFlag) {
        this.mergeFlag = mergeFlag;
    }

    public boolean isSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(boolean splitFlag) {
        this.splitFlag = splitFlag;
    }

    public InventorySplitMergeGridFile getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InventorySplitMergeGridFile currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<InventorySplitMergeGridFile> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<InventorySplitMergeGridFile> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public String getItemsPerUnit() {
        return itemsPerUnit;
    }

    public void setItemsPerUnit(String itemsPerUnit) {
        this.itemsPerUnit = itemsPerUnit;
    }

    public boolean isQuanDisFlag() {
        return quanDisFlag;
    }

    public void setQuanDisFlag(boolean quanDisFlag) {
        this.quanDisFlag = quanDisFlag;
    }

    public String getBrnId() {
        return brnId;
    }

    public void setBrnId(String brnId) {
        this.brnId = brnId;
    }

    public String getBrnName() {
        return brnName;
    }

    public void setBrnName(String brnName) {
        this.brnName = brnName;
    }

    public String getInvtClass() {
        return invtClass;
    }

    public void setInvtClass(String invtClass) {
        this.invtClass = invtClass;
    }

    public List<SelectItem> getInvtClassList() {
        return invtClassList;
    }

    public void setInvtClassList(List<SelectItem> invtClassList) {
        this.invtClassList = invtClassList;
    }

    public String getInvtType() {
        return invtType;
    }

    public void setInvtType(String invtType) {
        this.invtType = invtType;
    }

    public List<SelectItem> getInvtTypeList() {
        return invtTypeList;
    }

    public void setInvtTypeList(List<SelectItem> invtTypeList) {
        this.invtTypeList = invtTypeList;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    /** Creates a new instance of InventorySplitAndMerge */
    public InventorySplitAndMerge() {
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
            invtSplitMerge = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup("InventorySplitAndMergeFacade");
            this.setMessage("");
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("--Select--"));
            functionList.add(new SelectItem("S", "SPLIT"));
            functionList.add(new SelectItem("M", "MERGE"));
            locationClassListDetail();
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            this.setQuanDisFlag(true);
            gridDetail = new ArrayList<InventorySplitMergeGridFile>();
            this.setSplitFlag(false);
            this.setMergeFlag(false);
            this.setBrnId(this.orgnBrCode);
            this.setDisFlag(true);
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

    public void locationClassListDetail() {
        try {
            List resultList = new ArrayList();
            resultList = invtSplitMerge.inventoryClassCombo();
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

    public void functionOnBlurMethod() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setSplitFlag(true);
            this.setMergeFlag(true);
            this.setDisFlag(true);
            this.setBrnName("");
            this.setInvtSrNo("--Select--");
            this.setInvtClass("--Select--");
            this.setInvtType("--Select--");
            this.setStartNo("");
            this.setEndNo("");
            this.setQuantity("");
            this.setItemsPerUnit("");
            gridDetail = new ArrayList<InventorySplitMergeGridFile>();
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select function.");
                this.setSplitFlag(false);
                this.setMergeFlag(false);
                return;
            } else if (this.function.equalsIgnoreCase("S")) {
                this.setSplitFlag(true);
                this.setMergeFlag(false);
                this.setDisFlag(true);
                return;
            } else if (this.function.equalsIgnoreCase("M")) {
                this.setSplitFlag(false);
                this.setMergeFlag(true);
                this.setDisFlag(false);
                return;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setInventoryType() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory class.");
                return;
            }
            List resultList = new ArrayList();
            resultList = invtSplitMerge.inventoryTypeCombo(invtClass);
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    invtTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setFromBranchName() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.brnId == null || this.brnId.equalsIgnoreCase("") || this.brnId.length() == 0) {
                this.setErrorMessage("Please enter branch ID.");
                return;
            }
            if (this.brnId.length() == 1) {
                this.setBrnId("0" + this.brnId);
            }
            if (!this.brnId.equalsIgnoreCase(this.orgnBrCode)) {
                this.setErrorMessage("Branch ID should be your branch ID.");
                return;
            }
            if (!this.brnId.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in branch ID.");
                return;
            }
            List resultList = new ArrayList();
            resultList = invtSplitMerge.branchName(this.brnId);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                this.setBrnName(ele.get(0).toString());
            } else {
                this.setErrorMessage("This branch does not exists.");
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoadForSplittingAndMerging() {
        try {
            if (errorMessage.equalsIgnoreCase("No Cheque Series available corresponding this Inventory Type")) {
                setErrorMessage("No Cheque Series available corresponding this Inventory Type");
                return;
            }
            if (invtSrNo.equalsIgnoreCase("--Select--")) {
                setErrorMessage("Please select Inventory Series No");
                return;
            }
            this.setErrorMessage("");
            this.setMessage("");
            this.setStartNo("");
            gridDetail = new ArrayList<InventorySplitMergeGridFile>();
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select function.");
                return;
            }
            if (this.brnId == null || this.brnId.equalsIgnoreCase("") || this.brnId.length() == 0) {
                this.setErrorMessage("Please enter branch ID.");
                return;
            }
            if (this.brnId.length() == 1) {
                this.setBrnId("0" + this.brnId);
            }
            if (!this.brnId.equalsIgnoreCase(this.orgnBrCode)) {
                this.setErrorMessage("Branch ID should be your branch ID.");
                return;
            }
            if (this.invtSrNo == null || this.invtSrNo.equalsIgnoreCase("") || this.invtSrNo.length() == 0) {
                this.setErrorMessage("Please enter inventory serial number.");
                return;
            }
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory class.");
                return;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory type.");
                return;
            }
            if (this.brnId.length() == 1) {
                this.setBrnId("0" + this.brnId);
            }
            List resultList = new ArrayList();
            if (this.function.equalsIgnoreCase("S")) {
                resultList = invtSplitMerge.gridLoadForSplitDetail(this.brnId, this.invtClass, this.invtType, this.invtSrNo);
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        InventorySplitMergeGridFile detail = new InventorySplitMergeGridFile();
                        detail.setInvtSrNo(ele.get(0).toString());
                        detail.setStartNo(ele.get(1).toString());
                        detail.setEndno(ele.get(2).toString());
                        if (ele.get(3) == null || ele.get(3).toString().equalsIgnoreCase("")) {
                            detail.setStatus("");
                        } else if (ele.get(3).toString().equalsIgnoreCase("F")) {
                            detail.setStatus("Unused");
                        } else {
                            detail.setStatus(ele.get(3).toString());
                        }
                        detail.setStockDt(ele.get(4).toString());
                        detail.setInvtClass(ele.get(5).toString());
                        detail.setInvtType(ele.get(6).toString());
                        detail.setInvtQuantity(ele.get(7).toString());
                        detail.setTxnId(ele.get(8).toString());
                        gridDetail.add(detail);
                    }
                } else {
                    gridDetail = new ArrayList<InventorySplitMergeGridFile>();
                    this.setErrorMessage("No record exists for split.");
                    return;
                }
            } else if (this.function.equalsIgnoreCase("M")) {
                resultList = invtSplitMerge.gridLoadForMergeDetail(this.brnId, this.invtClass, this.invtType, this.invtSrNo);
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        InventorySplitMergeGridFile detail = new InventorySplitMergeGridFile();
                        detail.setInvtSrNo(ele.get(0).toString());
                        detail.setStartNo(ele.get(1).toString());
                        detail.setEndno(ele.get(2).toString());
                        if (ele.get(3) == null || ele.get(3).toString().equalsIgnoreCase("")) {
                            detail.setStatus("");
                        } else if (ele.get(3).toString().equalsIgnoreCase("F")) {
                            detail.setStatus("Unused");
                        } else {
                            detail.setStatus(ele.get(3).toString());
                        }
                        detail.setStockDt(ele.get(4).toString());
                        detail.setInvtClass(ele.get(5).toString());
                        detail.setInvtType(ele.get(6).toString());
                        detail.setInvtQuantity(ele.get(7).toString());
                        detail.setTxnId(ele.get(8).toString());
                        gridDetail.add(detail);
                    }
                } else {
                    gridDetail = new ArrayList<InventorySplitMergeGridFile>();
                    this.setErrorMessage("No record exists for merging.");
                    return;
                }
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
            this.setStartNo(currentItem.getStartNo());
            this.setEndNo(currentItem.getEndno());
            this.setQuantity(currentItem.getInvtQuantity());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void splitRecord() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select function.");
                return;
            }
            if (this.brnId == null || this.brnId.equalsIgnoreCase("") || this.brnId.length() == 0) {
                this.setErrorMessage("Please enter branch ID.");
                return;
            }
            if (this.brnId.length() == 1) {
                this.setBrnId("0" + this.brnId);
            }
            if (!this.brnId.equalsIgnoreCase(this.orgnBrCode)) {
                this.setErrorMessage("Branch ID should be your branch ID.");
                return;
            }
            if (this.invtSrNo == null || this.invtSrNo.equalsIgnoreCase("") || this.invtSrNo.length() == 0) {
                this.setErrorMessage("Please enter inventory series number.");
                return;
            }
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory class.");
                return;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory type.");
                return;
            }
            if (this.startNo == null || this.startNo.equalsIgnoreCase("") || this.startNo.length() == 0) {
                this.setErrorMessage("Please enter start number.");
                return;
            }
            if (!this.startNo.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in start number.");
                return;
            }
            if (this.endNo == null || this.endNo.equalsIgnoreCase("") || this.endNo.length() == 0) {
                this.setErrorMessage("Please enter end number.");
                return;
            }
            if (!this.endNo.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in end number.");
                return;
            }
            if (this.quantity == null || this.quantity.equalsIgnoreCase("") || this.quantity.length() == 0) {
                this.setErrorMessage("Please enter quantity.");
                return;
            }
            if (!this.quantity.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in quantity.");
                return;
            }
            if (this.itemsPerUnit == null || this.itemsPerUnit.equalsIgnoreCase("") || this.itemsPerUnit.length() == 0) {
                this.setErrorMessage("Please enter items per unit.");
                return;
            }
            if (!this.itemsPerUnit.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in items per unit.");
                return;
            }
            String enterDt = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
            String result = invtSplitMerge.splitInventory(this.brnId, currentItem.getInvtSrNo(), this.invtClass, this.invtType, this.startNo, this.endNo, this.itemsPerUnit, this.quantity, currentItem.getTxnId(), enterDt, this.userName);
            if (result == null || result.equalsIgnoreCase("") || result.length() == 0) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
                return;
            } else {
                this.setMessage(result);
            }
            this.setFunction("--Select--");
            this.setBrnId(this.orgnBrCode);
            this.setBrnName("");
            this.setInvtSrNo("--Select--");
            this.setInvtClass("--Select--");
            this.setInvtType("--Select--");
            this.setStartNo("");
            this.setEndNo("");
            this.setQuantity("");
            this.setItemsPerUnit("");
            this.setSplitFlag(false);
            this.setMergeFlag(false);
            this.setDisFlag(true);
            gridDetail = new ArrayList<InventorySplitMergeGridFile>();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void toNoOnBlur() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.startNo == null || this.startNo.equalsIgnoreCase("") || this.startNo.length() == 0) {
                this.setErrorMessage("Please enter from no.");
                return;
            }
            if (!this.startNo.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in from no.");
                return;
            }
            if (this.endNo == null || this.endNo.equalsIgnoreCase("") || this.endNo.length() == 0) {
                this.setErrorMessage("Please enter to no.");
                return;
            }
            if (!this.endNo.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in to no.");
                return;
            }
            if (this.endNo.length() < this.startNo.length()) {
                this.setErrorMessage("From number should be less than to number.");
                this.setEndNo("");
                return;
            }
            if (Integer.parseInt(endNo) < Integer.parseInt(startNo)) {
                this.setErrorMessage("From number should be less than to number.");
                this.setEndNo("");
                return;
            }
            int tempQuan = Integer.parseInt(endNo) - Integer.parseInt(startNo);
            this.setQuantity(String.valueOf(tempQuan + 1));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void mergeRecord() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select function.");
                return;
            }
            if (this.brnId == null || this.brnId.equalsIgnoreCase("") || this.brnId.length() == 0) {
                this.setErrorMessage("Please enter branch ID.");
                return;
            }
            if (this.brnId.length() == 1) {
                this.setBrnId("0" + this.brnId);
            }
            if (!this.brnId.equalsIgnoreCase(this.orgnBrCode)) {
                this.setErrorMessage("Branch ID should be your branch ID.");
                return;
            }
            if (this.invtSrNo == null || this.invtSrNo.equalsIgnoreCase("") || this.invtSrNo.length() == 0) {
                this.setErrorMessage("Please enter inventory series number.");
                return;
            }
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory class.");
                return;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory type.");
                return;
            }
            if (this.startNo == null || this.startNo.equalsIgnoreCase("") || this.startNo.length() == 0) {
                this.setErrorMessage("Please enter start number.");
                return;
            }
            if (!this.startNo.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in start number.");
                return;
            }
            if (this.endNo == null || this.endNo.equalsIgnoreCase("") || this.endNo.length() == 0) {
                this.setErrorMessage("Please enter end number.");
                return;
            }
            if (!this.endNo.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in end number.");
                return;
            }
            if (this.quantity == null || this.quantity.equalsIgnoreCase("") || this.quantity.length() == 0) {
                this.setErrorMessage("Please enter quantity.");
                return;
            }
            if (!this.quantity.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in quantity.");
                return;
            }
            if (this.itemsPerUnit == null || this.itemsPerUnit.equalsIgnoreCase("") || this.itemsPerUnit.length() == 0) {
                this.setErrorMessage("Please enter items per unit.");
                return;
            }
            if (!this.itemsPerUnit.matches("[0-9]*")) {
                this.setErrorMessage("Please enter only numeric values in items per unit.");
                return;
            }
            String enterDt = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
            //String result = invtSplitMerge.mergeInventory(gridDetail, this.brnId, enterDt, this.userName);
            String result = invtSplitMerge.mergingInventoryDetail(this.brnId, this.invtSrNo, this.invtClass, this.invtType, this.startNo, this.endNo, this.itemsPerUnit, this.quantity, enterDt, this.userName);
            if (result == null || result.equalsIgnoreCase("") || result.length() == 0) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.contains("!")) {
                this.setErrorMessage(result);
                return;
            } else {
                this.setMessage(result);
            }
            this.setFunction("--Select--");
            this.setBrnId(this.orgnBrCode);
            this.setBrnName("");
            this.setInvtSrNo("--Select--");
            this.setInvtClass("--Select--");
            this.setInvtType("--Select--");
            this.setStartNo("");
            this.setEndNo("");
            this.setQuantity("");
            this.setItemsPerUnit("");
            this.setSplitFlag(false);
            this.setMergeFlag(false);
            this.setDisFlag(true);
            gridDetail = new ArrayList<InventorySplitMergeGridFile>();
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
            this.setFunction("--Select--");
            this.setBrnId(this.orgnBrCode);
            this.setBrnName("");
            this.setInvtSrNo("--Select--");
            this.setInvtClass("--Select--");
            this.setInvtType("--Select--");
            this.setStartNo("");
            this.setEndNo("");
            this.setQuantity("");
            this.setItemsPerUnit("");
            this.setSplitFlag(false);
            this.setMergeFlag(false);
            this.setDisFlag(true);
            gridDetail = new ArrayList<InventorySplitMergeGridFile>();
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

    public void setChequeSeriesList() {
        try {
            this.setErrorMessage("");
            chequeSeriesList = new ArrayList<SelectItem>();
            chequeSeriesList.add(new SelectItem("--Select--"));
            if (function.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select Function");
                return;
            }
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select inventory class.");
                return;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select Inventory Type.");
                return;
            }
            List resultList = new ArrayList();
            String fun = "";
            if (function.equalsIgnoreCase("S")) {
                fun = "ENT";
            } else if (function.equalsIgnoreCase("M")) {
                fun = "SPLIT";
            }
            resultList = invtSplitMerge.chequeSeriesList(orgnBrCode, invtClass, invtType, fun);
            if (!resultList.isEmpty()) {
                chequeSeriesList = new ArrayList<SelectItem>();
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        chequeSeriesList.add(new SelectItem(ele.get(0).toString()));
                    }
                }
            } else {
                this.setErrorMessage("No Cheque Series available corresponding this Inventory Type");
                 gridDetail = new ArrayList<InventorySplitMergeGridFile>();
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
