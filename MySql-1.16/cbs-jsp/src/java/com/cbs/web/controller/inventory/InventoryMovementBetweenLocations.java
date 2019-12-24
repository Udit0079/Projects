package com.cbs.web.controller.inventory;

import com.cbs.dto.InventoryMovementGridDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class InventoryMovementBetweenLocations extends BaseBean {

    private String message;
    private String function;
    private String processButtonValue;
    private String confirmationMsg;
    private String fromBrnId;
    private String toBrnId;
    private String fromBrnName;
    private String toBrnName;
    private String fromBrnLocClass;
    private String fromNo;
    private String toNo;
    private String quantity;
    private String trfParticular;
    private String valueOfInvt;
    private String toBrnLocClass;
    private String invtClass;
    private String invtType;
    private String gridOperation;
    private List<SelectItem> fromBrnLocClassList;
    private List<SelectItem> toBrnLocClassList;
    private List<SelectItem> invtClassList;
    private List<SelectItem> invtTypeList;
    private List<SelectItem> functionList;
    private boolean addFlag;
    private boolean enquiryFlag;
    private boolean processVisible;
    private boolean quantityVisible;
    private List<InventoryMovementGridDetail> gridDetail;
    private InventoryMovementGridDetail currentItem;
    private InventorySplitAndMergeFacadeRemote invMove;
    private FtsPostingMgmtFacadeRemote fts;

    public String getGridOperation() {
        return gridOperation;
    }

    public void setGridOperation(String gridOperation) {
        this.gridOperation = gridOperation;
    }

    public boolean isQuantityVisible() {
        return quantityVisible;
    }

    public void setQuantityVisible(boolean quantityVisible) {
        this.quantityVisible = quantityVisible;
    }

    public String getProcessButtonValue() {
        return processButtonValue;
    }

    public void setProcessButtonValue(String processButtonValue) {
        this.processButtonValue = processButtonValue;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public boolean isProcessVisible() {
        return processVisible;
    }

    public void setProcessVisible(boolean processVisible) {
        this.processVisible = processVisible;
    }

    public boolean isEnquiryFlag() {
        return enquiryFlag;
    }

    public void setEnquiryFlag(boolean enquiryFlag) {
        this.enquiryFlag = enquiryFlag;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public String getValueOfInvt() {
        return valueOfInvt;
    }

    public void setValueOfInvt(String valueOfInvt) {
        this.valueOfInvt = valueOfInvt;
    }

    public String getTrfParticular() {
        return trfParticular;
    }

    public void setTrfParticular(String trfParticular) {
        this.trfParticular = trfParticular;
    }

    public String getFromBrnName() {
        return fromBrnName;
    }

    public void setFromBrnName(String fromBrnName) {
        this.fromBrnName = fromBrnName;
    }

    public String getToBrnName() {
        return toBrnName;
    }

    public void setToBrnName(String toBrnName) {
        this.toBrnName = toBrnName;
    }

    public String getFromBrnLocClass() {
        return fromBrnLocClass;
    }

    public void setFromBrnLocClass(String fromBrnLocClass) {
        this.fromBrnLocClass = fromBrnLocClass;
    }

    public List<SelectItem> getFromBrnLocClassList() {
        return fromBrnLocClassList;
    }

    public void setFromBrnLocClassList(List<SelectItem> fromBrnLocClassList) {
        this.fromBrnLocClassList = fromBrnLocClassList;
    }

    public String getToBrnLocClass() {
        return toBrnLocClass;
    }

    public void setToBrnLocClass(String toBrnLocClass) {
        this.toBrnLocClass = toBrnLocClass;
    }

    public List<SelectItem> getToBrnLocClassList() {
        return toBrnLocClassList;
    }

    public void setToBrnLocClassList(List<SelectItem> toBrnLocClassList) {
        this.toBrnLocClassList = toBrnLocClassList;
    }

    public List<SelectItem> getInvtTypeList() {
        return invtTypeList;
    }

    public void setInvtTypeList(List<SelectItem> invtTypeList) {
        this.invtTypeList = invtTypeList;
    }

    public InventoryMovementGridDetail getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InventoryMovementGridDetail currentItem) {
        this.currentItem = currentItem;
    }

    public List<InventoryMovementGridDetail> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<InventoryMovementGridDetail> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getToNo() {
        return toNo;
    }

    public void setToNo(String toNo) {
        this.toNo = toNo;
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

    public String getFromBrnId() {
        return fromBrnId;
    }

    public void setFromBrnId(String fromBrnId) {
        this.fromBrnId = fromBrnId;
    }

    public String getToBrnId() {
        return toBrnId;
    }

    public void setToBrnId(String toBrnId) {
        this.toBrnId = toBrnId;
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

    public InventoryMovementBetweenLocations() {
        this.setMessage("");
        this.setMessage("Fill all the required fields to add an entry !");
        this.setFunction("A");
        this.setProcessButtonValue("Save");
        this.setGridOperation("");
        this.processVisible = true;
        this.addFlag = false;
        this.enquiryFlag = false;
        this.quantityVisible = true;
        this.setConfirmationMsg("");
        try {
            invMove = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup("InventorySplitAndMergeFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            gridDetail = new ArrayList<InventoryMovementGridDetail>();
            addFunctionList();
            //setInventoryType();
            inventoryClassListDetail();
            this.setFromBrnId(getOrgnBrCode());
        } catch (ApplicationException e) {
            this.setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void addFunctionList() {
        functionList = new ArrayList<SelectItem>();
        functionList.add(new SelectItem("A", "ADD"));

        functionList.add(new SelectItem("E", "ENQUIRY"));
        functionList.add(new SelectItem("V", "VERIFY"));
        functionList.add(new SelectItem("ACK", "ACKNOWLEDGEMENT"));
    }

    public void inventoryClassListDetail() {
        try {
            invtClassList = new ArrayList<SelectItem>();
            invtClassList.add(new SelectItem("--Select--"));
            List inventoryClassList = invMove.inventoryClassComboList();

            if (!inventoryClassList.isEmpty()) {
                for (int i = 0; i < inventoryClassList.size(); i++) {
                    Vector ele = (Vector) inventoryClassList.get(i);
                    invtClassList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            fromBrnLocClassList = new ArrayList<SelectItem>();
            fromBrnLocClassList.add(new SelectItem("--Select--"));

            toBrnLocClassList = new ArrayList<SelectItem>();
            toBrnLocClassList.add(new SelectItem("--Select--"));

            List locationList = invMove.locationClass();
            if (!locationList.isEmpty()) {
                for (int i = 0; i < locationList.size(); i++) {
                    Vector ele = (Vector) locationList.get(i);
                    fromBrnLocClassList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                    toBrnLocClassList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setMessage("");
        this.setFromBrnId(getOrgnBrCode());
        this.setToBrnId("");
        this.setFromBrnName("");
        this.setToBrnName("");
        this.setFromBrnLocClass("--Select--");
        this.setToBrnLocClass("--Select--");
        this.setInvtClass("--Select--");
        this.setInvtType("--Select--");
        this.setFromNo("");
        this.setToNo("");
        this.setQuantity("");
        this.setTrfParticular("");
        this.setValueOfInvt("");

        setProcessButtonCaption();

        gridDetail = new ArrayList<InventoryMovementGridDetail>();
        if (this.function.equals("E") || this.function.equals("V") || this.function.equals("ACK")) {
            this.enquiryFlag = true;
            this.addFlag = false;
            this.setGridOperation("Select");
            getGridForEnquiryVerificationAndAck(onChangeMessage());
        } else if (this.function.equals("A")) {
            this.enquiryFlag = false;
            this.addFlag = true;
            this.setMessage("Fill all the required fields to add an entry !");
            this.setGridOperation("Delete");
        }
    }

    public String onChangeMessage() {
        String onChangeMsg = "";
        if (this.function.equals("E")) {
            onChangeMsg = "Select a row from table to delete an entry !";
        } else if (this.function.equals("V")) {
            onChangeMsg = "Select a row from table to verify an entry !";
        } else if (this.function.equals("ACK")) {
            onChangeMsg = "Select a row from table to acknowledge an entry !";
        }
        return onChangeMsg;
    }

    public void setProcessButtonCaption() {
        this.setProcessVisible(true);
        if (this.function.equals("A")) {
            this.setProcessButtonValue("Save");
        } else if (this.function.equals("E")) {
            this.setProcessButtonValue("Delete");
        } else if (this.function.equals("V")) {
            this.setProcessButtonValue("Verify");
        } else if (this.function.equals("ACK")) {
            this.setProcessButtonValue("Acknowledge");
        }
    }

    public void setInventoryType() {
        this.setMessage("");
        try {
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory class.");
                return;
            }
            fillInventoryType(this.invtClass);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setFromBranchName() {
        try {
            this.setMessage("");
            this.setFromBrnName("");
            if (this.fromBrnId == null || this.fromBrnId.equalsIgnoreCase("") || this.fromBrnId.length() == 0) {
                this.setMessage("Please enter from branch ID.");
                return;
            }
            if (this.fromBrnId.length() == 1) {
                this.setFromBrnId("0" + this.fromBrnId);
            }
            if (!this.fromBrnId.equalsIgnoreCase(getOrgnBrCode())) {
                this.setMessage("From branch Id should be your branch ID.");
                return;
            }
            if (!this.fromBrnId.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in from branch ID.");
                return;
            }

            List resultList = invMove.branchName(this.fromBrnId);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                this.setFromBrnName(ele.get(0).toString());
            } else {
                this.setMessage("Your from branch does not exist.");
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setToBranchName() {
        try {
            this.setMessage("");
            this.setToBrnName("");
            if (this.toBrnId == null || this.toBrnId.equalsIgnoreCase("") || this.toBrnId.length() == 0) {
                this.setMessage("Please enter to branch ID.");
                return;
            }
            if (!this.toBrnId.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in to branch ID.");
                return;
            }
            List resultList = invMove.branchName(this.toBrnId);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                this.setToBrnName(ele.get(0).toString());
            } else {
                this.setMessage("This to branch does not exist.");
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onBlurFrBrLocation() {
        this.setMessage("");
        if (this.fromBrnLocClass.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select from location.");
            return;
        }
    }

    public void onBlurToBrLocation() {
        this.setMessage("");
        if (this.toBrnLocClass.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select to location");
            return;
        }
    }

    public void setTrfParticular() {
        this.setMessage("");
        if (this.trfParticular.equalsIgnoreCase("")) {
            this.setMessage("Please enter transfer particular.");
            return;
        }
    }

    public void setFromNo() {
        this.setMessage("");
        if (this.fromNo.equalsIgnoreCase("")) {
            this.setMessage("Please enter from no.");
            return;
        }
    }

    public boolean validations() {
        try {
            if (this.function.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select function.");
                return false;
            }
            if (this.fromBrnId == null || this.fromBrnId.equalsIgnoreCase("") || this.fromBrnId.length() == 0) {
                this.setMessage("Please enter from branch ID.");
                return false;
            }
            if (this.fromBrnId.length() == 1) {
                this.setFromBrnId("0" + this.fromBrnId);
            }
            if (!this.fromBrnId.equalsIgnoreCase(getOrgnBrCode())) {
                this.setMessage("From branch Id should be your branch ID.");
                return false;
            }
            if (!this.fromBrnId.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in from branch ID.");
                return false;
            }
            if (this.fromBrnName == null || this.fromBrnName.equalsIgnoreCase("") || this.fromBrnName.length() == 0) {
                this.setMessage("Please enter correct branch ID in from branch ID.");
                return false;
            }
            if (this.toBrnId == null || this.toBrnId.equalsIgnoreCase("") || this.toBrnId.length() == 0) {
                this.setMessage("Please enter to branch ID.");
                return false;
            }
            if (!this.toBrnId.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in branch ID.");
                return false;
            }
            if (this.toBrnName == null || this.toBrnName.equalsIgnoreCase("") || this.toBrnName.length() == 0) {
                this.setMessage("Please enter correct branch ID in to branch ID.");
                return false;
            }
            if (this.fromBrnLocClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select from branch.");
                return false;
            }
            if (this.toBrnLocClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select to branch.");
                return false;
            }
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory class.");
                return false;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory type.");
                return false;
            }
            if (this.fromNo == null || this.fromNo.equalsIgnoreCase("") || this.fromNo.length() == 0) {
                this.setMessage("Please enter from no.");
                return false;
            }
            if (!this.fromNo.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in from no.");
                return false;
            }
            if (this.toNo == null || this.toNo.equalsIgnoreCase("") || this.toNo.length() == 0) {
                this.setMessage("Please enter to no.");
                return false;
            }
            if (!this.toNo.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in to no.");
                return false;
            }
            if (this.toNo.length() < this.fromNo.length()) {
                this.setMessage("From number should be less than to number.");
                this.setToNo("");
                return false;
            }
            if (Integer.parseInt(toNo) < Integer.parseInt(fromNo)) {
                this.setMessage("From number should be less than to number.");
                this.setToNo("");
                return false;
            }
            if (this.quantity == null || this.quantity.equalsIgnoreCase("") || this.quantity.length() == 0) {
                this.setMessage("Please enter quantity.");
                return false;
            }
            if (!this.quantity.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in quantity.");
                return false;
            }
            if (this.trfParticular == null || this.trfParticular.equalsIgnoreCase("") || this.trfParticular.length() == 0) {
                this.setMessage("Please enter transfer particulars.");
                return false;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.valueOfInvt == null || this.valueOfInvt.equalsIgnoreCase("") || this.valueOfInvt.length() == 0) {
                this.setValueOfInvt("0");
            }
            Matcher limitCheck = p.matcher(this.valueOfInvt);
            if (!limitCheck.matches()) {
                this.setValueOfInvt("0");
                this.setMessage("Please enter only numeric values in inventory value field.");
                return false;
            }
            if (this.valueOfInvt.contains(".")) {
                if (this.valueOfInvt.indexOf(".") != this.valueOfInvt.lastIndexOf(".")) {
                    this.setMessage("Please enter only numeric values in inventory value field.");
                    return false;
                } else if (this.valueOfInvt.substring(valueOfInvt.indexOf(".") + 1).length() > 2) {
                    this.setMessage("Please enter inventory value upto two decimal places.");
                    return false;
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return true;
    }

    private InventoryMovementGridDetail createGridDetailObj() {
        InventoryMovementGridDetail txnBean = new InventoryMovementGridDetail();
        try {
            if (this.fromBrnId.length() == 1) {
                this.setFromBrnId("0" + this.fromBrnId);
            }
            if (this.toBrnId.length() == 1) {
                this.setToBrnId("0" + this.toBrnId);
            }
            txnBean.setFromBrnId(this.fromBrnId);
            txnBean.setToBrnId(this.toBrnId);
            txnBean.setFromBrnLocClass(this.fromBrnLocClass);
            txnBean.setToBrnLocClass(this.toBrnLocClass);
            txnBean.setInvtClass(this.invtClass);
            txnBean.setInvtType(this.invtType);

            txnBean.setAlpha("");
            txnBean.setStartNo(this.fromNo);
            txnBean.setEndNo(this.toNo);
            txnBean.setQuantity(this.quantity);
            txnBean.setTrfParticular(this.trfParticular);
            if (this.valueOfInvt == null || this.valueOfInvt.equalsIgnoreCase("") || this.valueOfInvt.length() == 0) {
                this.setValueOfInvt("0");
            }
            txnBean.setInvtValue(this.valueOfInvt);
            txnBean.setEnterBy(getUserName());
            txnBean.setEnterDate(getTodayDate().substring(6) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2));
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return txnBean;
    }
    String msg = "";
    public void addCaseOperation() {
        this.setMessage("");
        if (this.function.equals("A")) {
            if (!validations()) {
                return;
            }
            if (this.fromBrnId.length() == 1) {
                this.setFromBrnId("0" + this.fromBrnId);
            }
            if (!this.fromBrnId.equalsIgnoreCase(getOrgnBrCode())) {
                this.setMessage("From branch ID should be your branch ID.");
                return;
            }
            
//            Float divVal10 = Float.parseFloat(quantity) % 10;
//            Float divVal15 = Float.parseFloat(quantity) % 15;
//            Float divVal20 = Float.parseFloat(quantity) % 20;
//            Float divVal25 = Float.parseFloat(quantity) % 25;
//            Float divVal50 = Float.parseFloat(quantity) % 50;
//
//
//            if (divVal10 != 0 && divVal20 != 0 && divVal25 != 0 && divVal50 != 0 && divVal15 != 0) {
//                this.setMessage("Enter correct From No to To No.");
//                return;
//            }
            
            if(this.msg.equalsIgnoreCase("true")){
               this.setMessage("Quantity should be the multiple of Inventory Type !");
                return; 
            }
            this.setGridOperation("Delete");
            this.addFlag = true;
            this.enquiryFlag = false;
            this.setProcessButtonValue("Save");
            this.processVisible = false;
            InventoryMovementGridDetail txnBean = createGridDetailObj();
            gridDetail.add(txnBean);
            fieldReset();
            this.setMessage("Now you can do either delete or save operation !");
        }
    }

    public void delete() {
        try {
            this.setMessage("");
            gridDetail.remove(currentItem);
            //resetForm();
            fieldReset();
            this.setMessage("Record deleted successfully.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void toNoOnBlur() {
        try {
            this.setMessage("");
            if (this.fromNo == null || this.fromNo.equalsIgnoreCase("") || this.fromNo.length() == 0) {
                this.setMessage("Please enter from no.");
                return;
            }
            if (!this.fromNo.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in from no.");
                return;
            }
            if (this.toNo == null || this.toNo.equalsIgnoreCase("") || this.toNo.length() == 0) {
                this.setMessage("Please enter to no.");
                return;
            }
            if (!this.toNo.matches("[0-9]*")) {
                this.setMessage("Please enter only numeric values in to no.");
                return;
            }
            if (this.toNo.length() < this.fromNo.length()) {
                this.setMessage("From number should be less than to number.");
                this.setToNo("");
                return;
            }
            if (Integer.parseInt(toNo) < Integer.parseInt(fromNo)) {
                this.setMessage("From number should be less than to number.");
                this.setToNo("");
                return;
            }
            int tempQuan = Integer.parseInt(toNo) - Integer.parseInt(fromNo);
            String diffToNoFrNo = String.valueOf(tempQuan + 1);
            this.setQuantity(diffToNoFrNo);
            String invType = this.invtType.substring(2, 4);
            Float m = Float.parseFloat(diffToNoFrNo) % Float.parseFloat(invType);
            if (m != 0) {
                msg = "true";  
            }else{
                msg = "";
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setTableDataToForm() {
        this.setMessage("");
        if (currentItem != null) {
            fieldReset();
            fillInventoryType(currentItem.getInvtClass());
            this.setFromBrnId(currentItem.getFromBrnId());
            this.setToBrnId(currentItem.getToBrnId());
            this.setFromBrnLocClass(currentItem.getFromBrnLocClass());
            this.setToBrnLocClass(currentItem.getToBrnLocClass());
            this.setInvtClass(currentItem.getInvtClass());
            this.setInvtType(currentItem.getInvtType());
            this.setTrfParticular(currentItem.getTrfParticular());
            this.setFromNo(currentItem.getStartNo());
            this.setToNo(currentItem.getEndNo());
            this.setQuantity(currentItem.getQuantity());
            this.setValueOfInvt(currentItem.getInvtValue());
            this.processVisible = false;
            if (this.function.equals("E")) {
                this.setProcessButtonValue("Delete");
                this.setMessage("Now you can delete the selected row from table !");
            } else if (this.function.equals("V")) {
                this.setProcessButtonValue("Verify");
                this.setMessage("Now you can verify the selected row from table !");
            } else if (this.function.equals("ACK")) {
                this.setProcessButtonValue("Acknowledge");
                this.setMessage("Now you can acknowledge the selected row from table !");
            }
        } else {
            this.setMessage("Please select a row to operate!");
        }
    }

    public void fillInventoryType(String invtClass) {
        try {
            List resultList = new ArrayList();
            resultList = invMove.inventoryTypeCombo(invtClass);
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    invtTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void populateMessage() {
        //this.setMessage("");
        this.setConfirmationMsg("");
        if (this.function.equals("A")) {
            this.setConfirmationMsg("Are you sure to save this entry!");
            return;
        } else if (this.function.equals("E")) {
            this.setConfirmationMsg("Are you sure to delete this entry!");
            return;
        } else if (this.function.equals("V")) {
            this.setConfirmationMsg("Are you sure to verify this entry!");
            return;
        } else if (this.function.equals("ACK")) {
            this.setConfirmationMsg("Are you sure to acknowlwdge this entry!");
            return;
        }
    }

    public void processAction() {
        this.setMessage("");
        if (this.function.equals("A")) {
            saveRecord();
        } else if (this.function.equals("E")) {
            deleteRecord();
        } else if (this.function.equals("V")) {
            verifyRecord();
        } else if (this.function.equals("ACK")) {
            acknowledgeRecord();
        }
    }

    public void saveRecord() {
        this.setMessage("");
        try {
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setMessage("There should be data in the table to save.");
                return;
            }
            String result = invMove.saveMovementDetail(gridDetail);
            if (result.substring(0, 4).equalsIgnoreCase("true")) {
                resetForm();
                this.setMessage("Record saved succesfully and generated transaction number is : " + result.substring(4));
            } else {
                this.setMessage(result);
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void deleteRecord() {
        this.setMessage("");
        try {
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setMessage("Please select table row to delete the entry !");
                return;
            }
            String result = invMove.deleteInvtTransactionNumber(currentItem.getInvtTranNo(), getUserName(), currentItem.getInvtTranSrNo());
            if (result.equalsIgnoreCase("true")) {
                String resultMsg = "Record deleted successfully; ";
                fieldReset();
                getGridForEnquiryVerificationAndAck(resultMsg);
                this.processVisible = true;
            } else {
                this.setMessage(result);
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void verifyRecord() {
        this.setMessage("");
        try {
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setMessage("Please select table row to verify the entry !");
                return;
            }
            String result = invMove.verifyTranNumber(currentItem.getInvtTranNo(), getUserName(), currentItem.getInvtTranSrNo());
            if (result.equalsIgnoreCase("true")) {
                String resultMsg = "Record verified successfully; ";
                fieldReset();
                getGridForEnquiryVerificationAndAck(resultMsg);
                this.processVisible = true;
            } else {
                this.setMessage(result);
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void acknowledgeRecord() {
        this.setMessage("");
        try {
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setMessage("Please select table row to verify the entry !");
                return;
            }
            String result = invMove.acknowledgeTranNumber(currentItem.getAlpha(), Integer.parseInt(currentItem.getStartNo()), Integer.parseInt(currentItem.getEndNo()), currentItem.getEnterDate(), currentItem.getToBrnId(), currentItem.getInvtClass(), currentItem.getInvtType(), Integer.parseInt(currentItem.getQuantity()), currentItem.getInvtTranNo(), getUserName(), currentItem.getInvtTranSrNo());
            if (result.substring(0, 4).equalsIgnoreCase("true")) {
                String resultMsg = "Record acknowledged successfully And Cheque Serial No is: " + result.substring(4) + " ";
                fieldReset();
                getGridForEnquiryVerificationAndAck(resultMsg);
                this.processVisible = true;
            } else {
                this.setMessage(result);
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getGridForEnquiryVerificationAndAck(String msg) {
        try {
            gridDetail = new ArrayList<InventoryMovementGridDetail>();
            List resultList = new ArrayList();

            if (this.function.equalsIgnoreCase("V")) {
                resultList = invMove.gridLoadOnMod(getOrgnBrCode());
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        InventoryMovementGridDetail detail = new InventoryMovementGridDetail();
                        detail.setFromBrnId(ele.get(0).toString());
                        detail.setToBrnId(ele.get(1).toString());
                        detail.setFromBrnLocClass(ele.get(2).toString());
                        detail.setToBrnLocClass(ele.get(3).toString());
                        detail.setInvtClass(ele.get(4).toString());
                        detail.setInvtType(ele.get(5).toString());
                        detail.setAlpha(ele.get(6).toString());
                        detail.setStartNo(ele.get(7).toString());
                        detail.setEndNo(ele.get(8).toString());
                        detail.setQuantity(ele.get(9).toString());
                        detail.setTrfParticular(ele.get(10).toString());
                        detail.setInvtValue(ele.get(11).toString());
                        detail.setEnterDate(ele.get(12).toString());
                        detail.setInvtTranNo(ele.get(13).toString());
                        detail.setInvtTranSrNo(ele.get(14).toString());
                        gridDetail.add(detail);
                    }
                    this.setMessage(msg);
                } else {
                    gridDetail = new ArrayList<InventoryMovementGridDetail>();
                    if (msg.contains("successfully")) {
                        msg = msg + "Now, There is no record to verify.";
                    } else {
                        msg = "Now, There is no record to verify.";
                    }
                    this.setMessage(msg);
                    return;
                }
            } else if (this.function.equalsIgnoreCase("E")) {
                resultList = invMove.gridLoadOnEnquiry(getOrgnBrCode());
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        InventoryMovementGridDetail detail = new InventoryMovementGridDetail();
                        detail.setFromBrnId(ele.get(0).toString());
                        detail.setToBrnId(ele.get(1).toString());
                        detail.setFromBrnLocClass(ele.get(2).toString());
                        detail.setToBrnLocClass(ele.get(3).toString());
                        detail.setInvtClass(ele.get(4).toString());
                        detail.setInvtType(ele.get(5).toString());
                        detail.setAlpha(ele.get(6).toString());
                        detail.setStartNo(ele.get(7).toString());
                        detail.setEndNo(ele.get(8).toString());
                        detail.setQuantity(ele.get(9).toString());
                        detail.setTrfParticular(ele.get(10).toString());
                        detail.setInvtValue(ele.get(11).toString());
                        detail.setEnterDate(ele.get(12).toString());
                        detail.setInvtTranNo(ele.get(13).toString());
                        detail.setInvtTranSrNo(ele.get(14).toString());
                        gridDetail.add(detail);
                    }
                    this.setMessage(msg);
                } else {
                    gridDetail = new ArrayList<InventoryMovementGridDetail>();
                    if (msg.contains("successfully")) {
                        msg = msg + "Now, There is no record to enquiry.";
                    } else {
                        msg = "Now, There is no record to enquiry.";
                    }
                    this.setMessage(msg);
                    return;
                }
            } else if (this.function.equalsIgnoreCase("ACK")) {
                resultList = invMove.gridLoadOnAcknowledge(getOrgnBrCode());
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        InventoryMovementGridDetail detail = new InventoryMovementGridDetail();
                        detail.setFromBrnId(ele.get(0).toString());
                        detail.setToBrnId(ele.get(1).toString());
                        detail.setFromBrnLocClass(ele.get(2).toString());
                        detail.setToBrnLocClass(ele.get(3).toString());
                        detail.setInvtClass(ele.get(4).toString());
                        detail.setInvtType(ele.get(5).toString());
                        detail.setAlpha(ele.get(6).toString());
                        detail.setStartNo(ele.get(7).toString());
                        detail.setEndNo(ele.get(8).toString());
                        detail.setQuantity(ele.get(9).toString());
                        detail.setTrfParticular(ele.get(10).toString());
                        detail.setInvtValue(ele.get(11).toString());
                        detail.setEnterDate(ele.get(12).toString());
                        detail.setInvtTranNo(ele.get(13).toString());
                        detail.setInvtTranSrNo(ele.get(14).toString());
                        gridDetail.add(detail);
                    }
                    this.setMessage(msg);
                } else {
                    gridDetail = new ArrayList<InventoryMovementGridDetail>();
                    if (msg.contains("successfully")) {
                        msg = msg + "Now, There is no record to acknowledge.";
                    } else {
                        msg = "Now, There is no record to acknowledge.";
                    }
                    this.setMessage(msg);
                    return;
                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void fieldReset() {
        this.setFromBrnId(getOrgnBrCode());
        this.setToBrnId("");
        this.setFromBrnName("");
        this.setToBrnName("");
        this.setFromBrnLocClass("--Select--");
        this.setToBrnLocClass("--Select--");
        this.setInvtClass("--Select--");
        this.setInvtType("--Select--");
        this.setFromNo("");
        this.setToNo("");
        this.setQuantity("");
        this.setTrfParticular("");
        this.setValueOfInvt("");
    }

    public void resetForm() {
        try {
            this.setMessage("");
            this.setFunction("A");
            this.setFromBrnId(getOrgnBrCode());
            this.setToBrnId("");
            this.setFromBrnName("");
            this.setToBrnName("");
            this.setFromBrnLocClass("--Select--");
            this.setToBrnLocClass("--Select--");
            this.setInvtClass("--Select--");
            this.setInvtType("--Select--");
            this.setFromNo("");
            this.setToNo("");
            this.setQuantity("");
            this.setTrfParticular("");
            this.setValueOfInvt("");
            this.setProcessButtonValue("Save");
            this.processVisible = true;
            gridDetail = new ArrayList<InventoryMovementGridDetail>();
            this.setConfirmationMsg("");
            this.setGridOperation("Delete");
            this.setMessage("Fill all the required fields to add an entry !");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
