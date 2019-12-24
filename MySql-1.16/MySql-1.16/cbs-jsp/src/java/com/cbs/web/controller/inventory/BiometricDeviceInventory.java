/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.inventory;

import com.cbs.dto.BiometricDeviceInventoryTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class BiometricDeviceInventory extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String deviceSrlno;
    private String deviceModel;
    private String deviceMake;
    private String btnValue;
    private String branch;
    private String baseBranch;
    private List<SelectItem> branchList;
    private String confirmationMsg;
    private Boolean disableOnFun;
    private String invisible;
    private List<BiometricDeviceInventoryTable> gridDetail;
    private BiometricDeviceInventoryTable currentItem = new BiometricDeviceInventoryTable();
    private CommonReportMethodsRemote common;
    private InventorySplitAndMergeFacadeRemote invMove;

    public BiometricDeviceInventory() {

        try {

            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            invMove = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup("InventorySplitAndMergeFacade");

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("S", "--Select--"));
            functionList.add(new SelectItem("A", "Add"));
            functionList.add(new SelectItem("U", "Update"));
            functionList.add(new SelectItem("D", "Delete"));
            functionList.add(new SelectItem("M", "Move Out"));

            branchList = new ArrayList<SelectItem>();
            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }

            setBtnValue("Save");
            gridLoad();
            setInvisible("none");
            setBaseBranch("HO");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void chgFunction() {
        setMessage("");
        try {
            gridDetail = new ArrayList<BiometricDeviceInventoryTable>();
            if (function.equalsIgnoreCase("A")) {
                setDisableOnFun(false);
                setBtnValue("Save");
                setInvisible("none");
                setDeviceSrlno("");
                setDeviceModel("");
                setDeviceMake("");
            } else if (function.equalsIgnoreCase("U")) {
                setBtnValue("Update");
                setInvisible("none");
                gridLoad();
            } else if (function.equalsIgnoreCase("D")) {
                setBtnValue("Delete");
                setInvisible("none");
                gridLoad();
            } else if (function.equalsIgnoreCase("M")) {
                setBtnValue("Move Out");
                setInvisible("");
                gridLoad();
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.function.equals("A")) {
            this.setConfirmationMsg("Are you sure to add this detail ?");
        } else if (this.function.equals("U")) {
            this.setConfirmationMsg("Are you sure to modify this detail ?");
        } else if (this.function.equals("D")) {
            this.setConfirmationMsg("Are you sure to delete this detail ?");
        } else if (this.function.equals("M")) {
            this.setConfirmationMsg("Are you sure to Move Out this detail ?");
        }
    }

    public void saveBiometricDeviceData() {
        setMessage("");
        String result = "";
        try {

            if (validateField()) {
                result = invMove.saveBiometricDeviceRecord(function, deviceSrlno, deviceModel, deviceMake, getUserName(), branch);
            } else {
                if (!function.equalsIgnoreCase("A")) {
                    throw new ApplicationException("Please Click on select option !");
                }
            }
            if (function.equalsIgnoreCase("A") && result.equalsIgnoreCase("true")) {
                clear();
                // gridLoad();
                setMessage("Data save Successfuly !");
            } else if (function.equalsIgnoreCase("U") && result.equalsIgnoreCase("true")) {
                clear();
                setMessage("Data Update Successfuly !");
            } else if (function.equalsIgnoreCase("D") && result.equalsIgnoreCase("true")) {
                clear();
                setMessage("Data Delete Successfuly !");
            } else if (function.equalsIgnoreCase("M") && result.equalsIgnoreCase("true")) {
                clear();
                setMessage("Data Move Out Successfuly !");
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public boolean validateField() throws ApplicationException {
        setMessage("");
        //Pattern pc = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
        Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-][a-zA-Z0-9,]+)*");
        try {
            if ((this.function == null) || (this.getFunction().equalsIgnoreCase(""))) {
                throw new ApplicationException("Please Select Function.");
            }
            if (deviceSrlno == null || deviceSrlno.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill device Srl No.");
            }
            Matcher tiMatcher = pm.matcher(deviceSrlno);
            if (!tiMatcher.matches()) {
                throw new ApplicationException("Please Enter device Srl No. Correctly.");
            }

            if (deviceModel == null || deviceModel.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Device Model");
            }

            if (!this.deviceModel.equalsIgnoreCase("")) {
                Matcher naMatcher = pm.matcher(this.deviceModel);
                if (!naMatcher.matches()) {
                    throw new ApplicationException("Please Enter Device Model Correctly.");
                }
            }

            if (deviceMake == null || deviceMake.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Device Make.");
            }
            Matcher poMatcher = pm.matcher(deviceMake);
            if (!poMatcher.matches()) {
                throw new ApplicationException("Please fill Device Make Correctly.");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
            return false;
        }
        return true;
    }

    public void gridLoad() {
        setMessage("");
        try {
            List dataList = new ArrayList();
            dataList = invMove.gridBiometricData(this.deviceSrlno);
            gridDetail = new ArrayList<BiometricDeviceInventoryTable>();
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector vtr = (Vector) dataList.get(i);
                    BiometricDeviceInventoryTable obj = new BiometricDeviceInventoryTable();
                    obj.setDevSrlNo(vtr.get(0).toString());
                    obj.setDevModel(vtr.get(1).toString());
                    obj.setDevMake(vtr.get(2).toString());
                    obj.setDevEnterBy(vtr.get(3).toString());
                    obj.setDevEnterDt(vtr.get(4).toString());
                    obj.setStatus(vtr.get(5).toString());
                    obj.setBranch(vtr.get(6).toString());
                    gridDetail.add(obj);
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void select() {
        setMessage("");
        try {
            this.setDeviceSrlno(currentItem.getDevSrlNo());
            this.setDeviceModel(currentItem.getDevModel());
            this.setDeviceMake(currentItem.getDevMake());
            setDisableOnFun(true);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        setMessage("");
        setFunction("S");
        setDeviceSrlno("");
        setDeviceModel("");
        setDeviceMake("");
        setInvisible("none");
        gridDetail = new ArrayList<BiometricDeviceInventoryTable>();
    }

    public void clear() {
        setFunction("S");
        setDeviceSrlno("");
        setDeviceModel("");
        setDeviceMake("");
        gridDetail = new ArrayList<BiometricDeviceInventoryTable>();
    }

    public String exitBtnAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getDeviceSrlno() {
        return deviceSrlno;
    }

    public void setDeviceSrlno(String deviceSrlno) {
        this.deviceSrlno = deviceSrlno;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceMake() {
        return deviceMake;
    }

    public void setDeviceMake(String deviceMake) {
        this.deviceMake = deviceMake;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getBaseBranch() {
        return baseBranch;
    }

    public void setBaseBranch(String baseBranch) {
        this.baseBranch = baseBranch;
    }

    public List<BiometricDeviceInventoryTable> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<BiometricDeviceInventoryTable> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public BiometricDeviceInventoryTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(BiometricDeviceInventoryTable currentItem) {
        this.currentItem = currentItem;
    }

    public Boolean getDisableOnFun() {
        return disableOnFun;
    }

    public void setDisableOnFun(Boolean disableOnFun) {
        this.disableOnFun = disableOnFun;
    }

    public String getInvisible() {
        return invisible;
    }

    public void setInvisible(String invisible) {
        this.invisible = invisible;
    }
}
