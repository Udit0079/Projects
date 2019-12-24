/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.facade.master.GlMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class HeadCreation extends BaseBean {

    private String message;
    private String code;
    private String groupCode;
    private String subGroupCode;
    private String subSubGroupCode;
    private String headNumber;
    private String headName;
    private String headType;
    private List<SelectItem> codeList;
    private List<SelectItem> groupCodeList;
    private List<SelectItem> subGroupCodeList;
    private List<SelectItem> subSubGroupCodeList;
    private List<SelectItem> headTypeList;
    private GlMasterFacadeRemote remoteFacade;
    private String tranType;
    private List<SelectItem> tranTypeList;

    /**
     * Creates a new instance of GlHeadCreation
     */
    public HeadCreation() {
        try {
            remoteFacade = (GlMasterFacadeRemote) ServiceLocator.getInstance().lookup("GlMasterFacade");

            headTypeList = new ArrayList<SelectItem>();
            headTypeList.add(new SelectItem("S", "System"));
            headTypeList.add(new SelectItem("M", "Manual"));

            fillCode();
            btnRefreshAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillCode() {
        try {
            codeList = new ArrayList<SelectItem>();
            codeList.add(new SelectItem("--Select--"));
            List dataList = remoteFacade.getGlCreationCode();
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    codeList.add(new SelectItem(element.get(0).toString(), element.get(0).toString()));
                }
            } else {
                this.setMessage("Please fill data in CBS HEAD RANGE MASTER for Code.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processCodeAction(ValueChangeEvent e) {
        this.setMessage("");
        try {
            groupCodeList = new ArrayList<SelectItem>();
            groupCodeList.add(new SelectItem("--Select--"));
            code = e.getNewValue().toString();
            List dataList = remoteFacade.retrieveGroupCode(code);
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    groupCodeList.add(new SelectItem(element.get(0).toString(), element.get(0).toString()));
                }
                getTranList();
            } else {
                this.setMessage("Please fill data in CBS HEAD RANGE MASTER for Group Code.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getTranList(){
        this.setMessage("");
        try {
            tranTypeList = new ArrayList<SelectItem>();
            if(code.trim().equalsIgnoreCase("ASSET")){
                tranTypeList.add(new SelectItem("D", "Debit"));
                tranTypeList.add(new SelectItem("B", "Both"));
            } else if(code.trim().equalsIgnoreCase("EXPENDITURE")){
                tranTypeList.add(new SelectItem("D", "Debit"));
            } else if(code.trim().equalsIgnoreCase("INCOME")){
                tranTypeList.add(new SelectItem("C", "Credit"));                
            } else if(code.trim().equalsIgnoreCase("LIAB")){
                tranTypeList.add(new SelectItem("C", "Credit"));
                tranTypeList.add(new SelectItem("B", "Both"));                                
            }            
        }catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processGroupCodeAction(ValueChangeEvent e) {
        this.setMessage("");
        try {
            subGroupCodeList = new ArrayList<SelectItem>();
            subGroupCodeList.add(new SelectItem("--Select--"));
            groupCode = e.getNewValue().toString();
            List dataList = remoteFacade.retrieveSubGroupCode(code, groupCode);
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    subGroupCodeList.add(new SelectItem(element.get(0).toString(), element.get(0).toString()));
                }
            } else {
                this.setMessage("Please fill data in CBS HEAD RANGE MASTER for Sub Group Code.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processSubGroupCodeAction(ValueChangeEvent e) {
        this.setMessage("");
        try {
            subSubGroupCodeList = new ArrayList<SelectItem>();
            subSubGroupCodeList.add(new SelectItem("--Select--"));
            subGroupCode = e.getNewValue().toString();
            List dataList = remoteFacade.retrieveSSGroupCode(code, groupCode, subGroupCode);
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    subSubGroupCodeList.add(new SelectItem(element.get(0).toString(), element.get(0).toString()));
                }
            } else {
                this.setMessage("Please fill data in CBS HEAD RANGE MASTER for Sub Sub Group Code.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processMaxHeadAction(ValueChangeEvent e) {
        this.setMessage("");
        try {
            subSubGroupCode = e.getNewValue().toString();
            String maxNumber = remoteFacade.getMaxHeadNumber(code, groupCode, subGroupCode, subSubGroupCode);
            if (maxNumber != null || !maxNumber.equals("")) {
                this.setHeadNumber(maxNumber);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        try {
            if (this.headNumber == null || this.headNumber.equals("") || this.headNumber.length() == 0) {
                this.setMessage("There should be a proper Head Number.");
                return;
            }
            if (this.headName == null || this.headName.equals("") || this.headName.length() == 0) {
                this.setMessage("Please fill Head Name.");
                return;
            }

            int headLength = headNumber.length();
            if (headLength < 6) {
                for (int i = 0; i < (6 - headLength); i++) {
                    headNumber = "0" + headNumber;
                }
            }

            String result = remoteFacade.createGlHead(this.code, headNumber, headName, this.headType, this.tranType);
            if (result.equalsIgnoreCase("true")) {
                btnRefreshAction();
                this.setMessage("Account has been created successfully.");
            } else {
                this.setMessage(result);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setHeadNumber("");
        this.setHeadName("");
        this.setCode("--Select--");        
        groupCodeList = new ArrayList<SelectItem>();
        subGroupCodeList = new ArrayList<SelectItem>();
        subSubGroupCodeList = new ArrayList<SelectItem>();
        tranTypeList = new ArrayList<SelectItem>();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    /**
     * Getter And Setter
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SelectItem> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<SelectItem> codeList) {
        this.codeList = codeList;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public List<SelectItem> getGroupCodeList() {
        return groupCodeList;
    }

    public void setGroupCodeList(List<SelectItem> groupCodeList) {
        this.groupCodeList = groupCodeList;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadNumber() {
        return headNumber;
    }

    public void setHeadNumber(String headNumber) {
        this.headNumber = headNumber;
    }

    public String getHeadType() {
        return headType;
    }

    public void setHeadType(String headType) {
        this.headType = headType;
    }

    public List<SelectItem> getHeadTypeList() {
        return headTypeList;
    }

    public void setHeadTypeList(List<SelectItem> headTypeList) {
        this.headTypeList = headTypeList;
    }

    public String getSubGroupCode() {
        return subGroupCode;
    }

    public void setSubGroupCode(String subGroupCode) {
        this.subGroupCode = subGroupCode;
    }

    public List<SelectItem> getSubGroupCodeList() {
        return subGroupCodeList;
    }

    public void setSubGroupCodeList(List<SelectItem> subGroupCodeList) {
        this.subGroupCodeList = subGroupCodeList;
    }

    public String getSubSubGroupCode() {
        return subSubGroupCode;
    }

    public void setSubSubGroupCode(String subSubGroupCode) {
        this.subSubGroupCode = subSubGroupCode;
    }

    public List<SelectItem> getSubSubGroupCodeList() {
        return subSubGroupCodeList;
    }

    public void setSubSubGroupCodeList(List<SelectItem> subSubGroupCodeList) {
        this.subSubGroupCodeList = subSubGroupCodeList;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public List<SelectItem> getTranTypeList() {
        return tranTypeList;
    }

    public void setTranTypeList(List<SelectItem> tranTypeList) {
        this.tranTypeList = tranTypeList;
    }    
}
