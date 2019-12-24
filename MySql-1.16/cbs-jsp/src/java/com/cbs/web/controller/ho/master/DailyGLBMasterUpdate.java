/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.ho.DailyGLBMasterUpdateGrid;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class DailyGLBMasterUpdate {
    private HttpServletRequest request;
    private String newDate;
    private String userName;
    private String orgBrnCode;
    private String type;
    private List<SelectItem> typeList;
    private String groupCode;
    private String subGroupCode;
    private String subSubGroupCode;
    private String subSubSubGroupCode;
    private String description;
    private String subCode1;
    private String subCode2;
    private String subSubCode1;
    private String subSubCode2;
    private String subSubSubCode1;
    private String subSubSubCode2;
    private int currentRow;
    private String error;
    private String acStatus;
    private List<SelectItem> acStatusList;
    private List<DailyGLBMasterUpdateGrid> dglbMasUpdate = new ArrayList<DailyGLBMasterUpdateGrid>();
    DailyGLBMasterUpdateGrid currentItem;
    private DailyMasterFacadeRemote dglbmastUpdateRemote;
    private String newSubCode;
    private String newSubSubCode;
    private String newSubSubSubCode;
    private String acType;
    private boolean disableSubCodeText;
    private boolean disableSSCText;
    private boolean disableSSSCText;
    private List<SelectItem> acctDescOption;
    FtsPostingMgmtFacadeRemote ftsBeanRemote;

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    

    public DailyMasterFacadeRemote getDglbmastUpdateRemote() {
        return dglbmastUpdateRemote;
    }

    public void setDglbmastUpdateRemote(DailyMasterFacadeRemote dglbmastUpdateRemote) {
        this.dglbmastUpdateRemote = dglbmastUpdateRemote;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public FtsPostingMgmtFacadeRemote getFtsBeanRemote() {
        return ftsBeanRemote;
    }

    public void setFtsBeanRemote(FtsPostingMgmtFacadeRemote ftsBeanRemote) {
        this.ftsBeanRemote = ftsBeanRemote;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getNewSubCode() {
        return newSubCode;
    }

    public void setNewSubCode(String newSubCode) {
        this.newSubCode = newSubCode;
    }

    public String getNewSubSubCode() {
        return newSubSubCode;
    }

    public void setNewSubSubCode(String newSubSubCode) {
        this.newSubSubCode = newSubSubCode;
    }

    public String getNewSubSubSubCode() {
        return newSubSubSubCode;
    }

    public void setNewSubSubSubCode(String newSubSubSubCode) {
        this.newSubSubSubCode = newSubSubSubCode;
    }

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getSubCode1() {
        return subCode1;
    }

    public void setSubCode1(String subCode1) {
        this.subCode1 = subCode1;
    }

    public String getSubCode2() {
        return subCode2;
    }

    public void setSubCode2(String subCode2) {
        this.subCode2 = subCode2;
    }

    public String getSubGroupCode() {
        return subGroupCode;
    }

    public void setSubGroupCode(String subGroupCode) {
        this.subGroupCode = subGroupCode;
    }

    public String getSubSubCode1() {
        return subSubCode1;
    }

    public void setSubSubCode1(String subSubCode1) {
        this.subSubCode1 = subSubCode1;
    }

    public String getSubSubCode2() {
        return subSubCode2;
    }

    public void setSubSubCode2(String subSubCode2) {
        this.subSubCode2 = subSubCode2;
    }

    public String getSubSubGroupCode() {
        return subSubGroupCode;
    }

    public void setSubSubGroupCode(String subSubGroupCode) {
        this.subSubGroupCode = subSubGroupCode;
    }

    public String getSubSubSubCode1() {
        return subSubSubCode1;
    }

    public void setSubSubSubCode1(String subSubSubCode1) {
        this.subSubSubCode1 = subSubSubCode1;
    }

    public String getSubSubSubCode2() {
        return subSubSubCode2;
    }

    public void setSubSubSubCode2(String subSubSubCode2) {
        this.subSubSubCode2 = subSubSubCode2;
    }

    public String getSubSubSubGroupCode() {
        return subSubSubGroupCode;
    }

    public void setSubSubSubGroupCode(String subSubSubGroupCode) {
        this.subSubSubGroupCode = subSubSubGroupCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public List<SelectItem> getAcStatusList() {
        return acStatusList;
    }

    public void setAcStatusList(List<SelectItem> acStatusList) {
        this.acStatusList = acStatusList;
    }

    public DailyGLBMasterUpdateGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(DailyGLBMasterUpdateGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<DailyGLBMasterUpdateGrid> getDglbMasUpdate() {
        return dglbMasUpdate;
    }

    public void setDglbMasUpdate(List<DailyGLBMasterUpdateGrid> dglbMasUpdate) {
        this.dglbMasUpdate = dglbMasUpdate;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcctDescOption() {
        return acctDescOption;
    }

    public void setAcctDescOption(List<SelectItem> acctDescOption) {
        this.acctDescOption = acctDescOption;
    }

    public boolean isDisableSSCText() {
        return disableSSCText;
    }

    public void setDisableSSCText(boolean disableSSCText) {
        this.disableSSCText = disableSSCText;
    }

    public boolean isDisableSSSCText() {
        return disableSSSCText;
    }

    public void setDisableSSSCText(boolean disableSSSCText) {
        this.disableSSSCText = disableSSSCText;
    }

    public boolean isDisableSubCodeText() {
        return disableSubCodeText;
    }

    public void setDisableSubCodeText(boolean disableSubCodeText) {
        this.disableSubCodeText = disableSubCodeText;
    }   

    public DailyGLBMasterUpdate() {
        request = getRquest();
        try {
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setNewDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            dglbmastUpdateRemote = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            getDropdownValues();
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getLocalizedMessage();
        }
    }

    public HttpServletRequest getRquest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void getDropdownValues() {
        try {
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("S", "--Select--"));
            typeList.add(new SelectItem("L", "LIABLITIES"));
            typeList.add(new SelectItem("A", "ASSETS"));
            acStatusList = new ArrayList<SelectItem>();
            acStatusList.add(new SelectItem("1", "OPERATVE"));
            acStatusList.add(new SelectItem("2", "INOPERATVE"));
            acStatusList.add(new SelectItem("3", "ALL"));
            
            List listForAcDesc = dglbmastUpdateRemote.getTableDetails();
            acctDescOption = new ArrayList<SelectItem>();
            for (int i = 0; i < listForAcDesc.size(); i++) {
                Vector element1 = (Vector) listForAcDesc.get(i);
                String con = element1.get(0).toString();
                acctDescOption.add(new SelectItem(con));
            }           
        } catch (Exception e) {
            setError(e.getLocalizedMessage());
        }
    }    
    
    public void clickOnType() {
        try {
            if(!this.type.equalsIgnoreCase("S")){
                loadGrid();
            }
        } catch (Exception e) {
            error = e.getMessage();
        }
    }
    
    public void loadGrid() {
        try {
            dglbMasUpdate = new ArrayList<DailyGLBMasterUpdateGrid>();
            List resultSet = dglbmastUpdateRemote.getGlbDtl(this.type);
            if(!resultSet.isEmpty()) {
                for (int i = 0; i < resultSet.size(); i++) {
                    Vector vec = (Vector) resultSet.get(i);
                    DailyGLBMasterUpdateGrid dglbMas = new DailyGLBMasterUpdateGrid();                    
                    dglbMas.setsNo(String.valueOf(dglbMasUpdate.size() + 1));                   
                    dglbMas.setGrpCode(vec.get(0).toString());
                    dglbMas.setSubGrpCode(vec.get(2).toString());
                    dglbMas.setSubSubGrpCode(vec.get(3).toString());
                    dglbMas.setSubSubSubGrpCode(vec.get(4).toString());
                    dglbMas.setsCode(vec.get(5).toString());
                    dglbMas.setsCodeDesc(vec.get(6).toString());
                    dglbMas.setSsCode(vec.get(7).toString());
                    dglbMas.setSsCodeDesc(vec.get(8).toString());
                    dglbMas.setSssCode(vec.get(9).toString());
                    dglbMas.setSssCodeDesc(vec.get(10).toString());
                    dglbMas.setDesc(vec.get(1).toString());
                    dglbMas.setActType(vec.get(11).toString());
                    dglbMas.setActStatus(vec.get(12).toString());
                    dglbMas.setGlbActType(vec.get(13).toString());                    
                    dglbMasUpdate.add(dglbMas);
                }
            }            
        } catch (Exception e) {
            setError(e.getLocalizedMessage());
        }
    }
    
    public void setRowValues() {
        try {
            setType(currentItem.getGlbActType());
            setGroupCode(currentItem.getGrpCode());
            setSubGroupCode(currentItem.getSubGrpCode());
            setSubSubGroupCode(currentItem.getSubSubGrpCode());
            setSubSubSubGroupCode(currentItem.getSubSubSubGrpCode());
            setDescription(currentItem.getDesc());
            
            if(currentItem.getSubGrpCode().equalsIgnoreCase("0")){
                setSubCode1("");
                setNewSubCode("");
                setSubCode2("");
                disableSubCodeText = true;
            }else{
                disableSubCodeText = false;
                setSubCode1(currentItem.getsCode());
                setNewSubCode(currentItem.getsCode());
                setSubCode2(currentItem.getsCodeDesc());
            }
            
            if(currentItem.getSubSubGrpCode().equalsIgnoreCase("0")){
                setSubSubCode1("");
                setNewSubSubCode("");
                setSubSubCode2("");
                disableSSCText = true;
            }else{
                disableSSCText = false;
                setSubSubCode1(currentItem.getSsCode());
                setNewSubSubCode(currentItem.getSsCode());
                setSubSubCode2(currentItem.getSsCodeDesc());
            }
            
            if(currentItem.getSubSubSubGrpCode().equalsIgnoreCase("0")){
                setSubSubSubCode1("");
                setNewSubSubSubCode("");
                setSubSubSubCode2("");
                disableSSSCText = true;
            }else{
                disableSSSCText = false;
                setSubSubSubCode1(currentItem.getSssCode());
                setNewSubSubSubCode(currentItem.getSssCode());
                setSubSubSubCode2(currentItem.getSssCodeDesc());
            }
            
            setAcType(currentItem.getActType());
            setAcStatus(currentItem.getActStatus());         
        } catch (Exception e) {
            this.setError(e.getMessage());
        }
    }
    
    public void fetchCurrentRow(ActionEvent event) {
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (DailyGLBMasterUpdateGrid item : dglbMasUpdate) {
            currentItem = item;            
        }
    }
    
    public void updateRecord() {
        ArrayList<String> arraylst = new ArrayList<String>();
        
        arraylst.add(this.groupCode);
        arraylst.add(this.subGroupCode);
        arraylst.add(this.subSubGroupCode);
        arraylst.add(this.newSubCode);
        arraylst.add(this.newSubSubCode);
        arraylst.add(this.description);
        arraylst.add(this.acType);
        arraylst.add(this.acStatus);
        arraylst.add(this.type);
        arraylst.add(this.subCode2);
        arraylst.add(this.subSubCode2);
        arraylst.add(this.subSubSubGroupCode);
        arraylst.add(this.newSubSubSubCode);
        arraylst.add(this.subSubSubCode2);
            
        if (arraylst.isEmpty()) {
            this.setError("Error!! No record to update");
            return;
        } else {
            try {
                String updateResult = dglbmastUpdateRemote.updateRecord(arraylst, userName);
                loadGrid();
                refreshForm();
                this.setError(updateResult);
            } catch (ApplicationException e) {
                error = e.getMessage();
            } catch (Exception e) {
                error = e.getLocalizedMessage();
            }
        }
    }

    public void refreshForm() {
        try {
            this.setType("S");
            this.setGroupCode("");
            this.setSubGroupCode("");
            this.setSubSubGroupCode("");
            this.setSubSubSubGroupCode("");
            this.setDescription("");
            this.setSubCode1("");
            this.setSubCode2("");
            this.setSubSubCode1("");
            this.setSubSubCode2("");
            this.setSubSubSubCode1("");
            this.setSubSubSubCode2("");
            this.setNewSubCode("");
            this.setNewSubSubCode("");
            this.setNewSubSubSubCode("");
            dglbMasUpdate = new ArrayList<DailyGLBMasterUpdateGrid>();
            dglbMasUpdate.clear();
            this.setError("");
        } catch (Exception e) {
            setError(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        try {
            refreshForm();
        } catch (Exception e) {
            setError(e.getLocalizedMessage());
        }
        return "case1";
    }
    
    public void getGlSubCodeDesc() {
        try {
            this.setError("");
            if (this.subCode1.length() < 8) {
                setError("Entered Sub Code length cannot be less than 8");
                return;
            }
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String accountNumber = ftsBeanRemote.getNewAccountNumberForHo(this.subCode1);
            if (accountNumber.startsWith("Account Number does not exist")) {
                this.setError("Entered Sub code account number does not exist");
                return;
            } else {
                setError("");
                newSubCode = accountNumber.substring(2,10);
            }
            List list2 = dglbmastUpdateRemote.acNameForBankPurpose(newSubCode);
            if (!list2.isEmpty()) {
                Vector vector2 = (Vector) list2.get(0);
                String acname1 = vector2.get(0).toString();
                this.setError("");
                this.setSubCode2(acname1);
            } else {
                this.setSubCode2("");
                this.setError("Invalid account number.");
                return;
            }
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }
    
    public void getGlSubSubCodeDesc() {
        try {
            this.setError("");
            if (this.subSubCode1.length() < 8) {
                setError("Entered Sub Sub Sub code length cannot be less than 8");
                return;
            }
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String accountNumber = ftsBeanRemote.getNewAccountNumberForHo(this.subSubCode1);
            if (accountNumber.startsWith("Account Number does not exist")) {
                this.setError("Entered account number does not exist");
                return;
            } else {
                setError("");
                newSubSubCode = accountNumber.substring(2, 10);
            }
            List list2 = dglbmastUpdateRemote.acNameForBankPurpose(newSubSubCode);
            if (!list2.isEmpty()) {
                Vector vector2 = (Vector) list2.get(0);
                String acname1 = vector2.get(0).toString();
                this.setError("");
                this.setSubSubCode2(acname1);
            } else {
                this.setSubSubCode2("");
                this.setError("Invalid Account Number.");
                return;
            }
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    public void getGlSubSubSubCodeDesc() {
        try {
            this.setError("");
            if (this.subSubSubCode1.length() < 8) {
                setError("Entered Sub Sub Sub code account length cannot be less than 8");
                return;
            }
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String accountNumber = ftsBeanRemote.getNewAccountNumberForHo(this.subSubSubCode1);
            if (accountNumber.startsWith("Account Number does not exist")) {
                this.setError("Entered Sub Sub Sub account number does not exist");
                return;
            } else {
                setError("");
                newSubSubSubCode = accountNumber.substring(2, 10);
            }
            List list2 = dglbmastUpdateRemote.acNameForBankPurpose(newSubSubSubCode);
            if (!list2.isEmpty()) {
                Vector vector2 = (Vector) list2.get(0);
                String acname1 = vector2.get(0).toString();
                this.setError("");
                this.setSubSubSubCode2(acname1);
            } else {
                this.setSubSubSubCode2("");
                this.setError("Invalid account number.");
                return;
            }
        } catch (ApplicationException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }
}
