/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.dto.OfficeDeptMappGrid;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
 * @author Administrator
 */
public class OfficeDeptMaster extends BaseBean {

    private String message;
    private String offId;
    private List<SelectItem> offIdList;
    private String offName;
    private String deptId;
    private List<SelectItem> deptIdList;
    private String deptName;
    private String deptHead;
    private String addr;
    private String btnValue = "Save";
    private boolean btnFlag;
    private boolean offDisable;
    private boolean deptDisable;
    
    private OfficeDeptMappGrid currentItem;
    private List<OfficeDeptMappGrid> gridDetail;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private GeneralMasterFacadeRemote generalFacadeRemote;
    private CommonReportMethodsRemote common;
    Date date = new Date();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOffId() {
        return offId;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public List<SelectItem> getOffIdList() {
        return offIdList;
    }

    public void setOffIdList(List<SelectItem> offIdList) {
        this.offIdList = offIdList;
    }

    public String getOffName() {
        return offName;
    }

    public void setOffName(String offName) {
        this.offName = offName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<SelectItem> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<SelectItem> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptHead() {
        return deptHead;
    }

    public void setDeptHead(String deptHead) {
        this.deptHead = deptHead;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public boolean isBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(boolean btnFlag) {
        this.btnFlag = btnFlag;
    }

    public OfficeDeptMappGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(OfficeDeptMappGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<OfficeDeptMappGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<OfficeDeptMappGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isOffDisable() {
        return offDisable;
    }

    public void setOffDisable(boolean offDisable) {
        this.offDisable = offDisable;
    }

    public boolean isDeptDisable() {
        return deptDisable;
    }

    public void setDeptDisable(boolean deptDisable) {
        this.deptDisable = deptDisable;
    }
    
    public OfficeDeptMaster(){
        try {
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setTodayDate(sdf.format(date));
            
            getOfficeIdData();
            getDeptIdData();
            this.setBtnFlag(false);
            this.setOffDisable(false);
            this.setDeptDisable(false);
            gridLoad();
            
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void getOfficeIdData() {
        setMessage("");
        try {
            List resultList = generalFacadeRemote.getRefCodeAndDescByNo("233");
            offIdList = new ArrayList<SelectItem>();
            offIdList.add(new SelectItem("S", "--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    offIdList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString() + " - " + ele.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void getDeptIdData() {
        setMessage("");
        try {
            List resultList = generalFacadeRemote.getRefCodeAndDescByNo("234");
            deptIdList = new ArrayList<SelectItem>();
            deptIdList.add(new SelectItem("S", "--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    deptIdList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString() + " - " + ele.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void offIdFn(){
        setMessage("");
        this.setOffName("");
        try {
            String offIdDesc = "";
            offIdDesc = common.getRefRecDesc("233", this.getOffId());
            if (!offIdDesc.equalsIgnoreCase("")) {
                this.setOffName(offIdDesc);
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }
    
    public void deptIdFn(){
        setMessage("");
        try {
            String deptIdDesc = common.getRefRecDesc("234", this.getDeptId());
            if (!deptIdDesc.equalsIgnoreCase("")) {
                this.setDeptName(deptIdDesc);
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }
    
    public void gridLoad() {
        gridDetail = new ArrayList<OfficeDeptMappGrid>();
        try {
            List resultList = generalFacadeRemote.gridOfficeDeptMapp();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    OfficeDeptMappGrid offDept = new OfficeDeptMappGrid();
                    Vector ele = (Vector) resultList.get(i);
                    
                    offDept.setOfficeId(ele.get(0).toString());
                    offDept.setOfficeName(ele.get(1).toString());
                    offDept.setDepartmentId(ele.get(2).toString());
                    offDept.setDepartmentName(ele.get(3).toString());
                    offDept.setDepartmentHead(ele.get(4).toString());
                    offDept.setAddress(ele.get(5).toString());
                    
                    gridDetail.add(offDept);
                }
            } else {
                this.setMessage("There is no entry.");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void select() {
        this.setMessage("");
        try {
            this.setOffId(currentItem.getOfficeId());
            this.setOffName(currentItem.getOfficeName());
            this.setDeptId(currentItem.getDepartmentId());
            this.setDeptName(currentItem.getDepartmentName());
            this.setDeptHead(currentItem.getDepartmentHead());
            this.setAddr(currentItem.getAddress());
            this.setBtnFlag(false);
            this.setBtnValue("Update");
            this.setOffDisable(true);
            this.setDeptDisable(true);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public String validateField() {
        this.setMessage("");
        try {
            if((this.offId == null) || (this.offId.equalsIgnoreCase(""))){
                return "Please Select Office Id";
            }
            
            if((this.deptId == null) || (this.deptId.equalsIgnoreCase(""))){
                return "Please Select Department Id";
            }
            
            if((this.deptHead == null) || (this.deptHead.equalsIgnoreCase(""))){
                return "Please Fill Department Head";
            }
            
            if((this.addr == null) || (this.addr.equalsIgnoreCase(""))){
                return "Please Fill Address";
            }
            
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "true";
    }
    
    public void saveMasterDetail() {
        this.setMessage("");
        try {
            String result = validateField();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            
            result = generalFacadeRemote.SaveUpdateVerifyOfficeDept(this.getBtnValue(),Integer.parseInt(this.getOffId()),
                    this.getOffName(),Integer.parseInt(this.getDeptId()),this.getDeptName(),this.getDeptHead(),this.getAddr(),
                    this.getUserName());
            
            if (!result.equalsIgnoreCase("true")) {
                this.setMessage(result);
                return;
            }
            if(btnValue.equalsIgnoreCase("Save")){
                refreshForm();
                this.setMessage("Data has been saved successfully.");
            }else if(btnValue.equalsIgnoreCase("Update")){
                refreshForm();
                this.setMessage("Data has been Updated successfully.");
            }
            gridLoad();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void refreshForm() {
        this.setMessage("");
        this.setOffId("");
        this.setOffName("");
        this.setDeptId("");
        this.setDeptName("");
        this.setDeptHead("");
        this.btnValue = "Save";
        gridDetail = new ArrayList<OfficeDeptMappGrid>();
        this.setAddr("");
        this.setBtnFlag(false);
        this.setOffDisable(false);
        this.setDeptDisable(false);
    }
    
    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }
}
