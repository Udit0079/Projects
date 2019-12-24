/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.facade.ckycr.CkycrProcessMgmtFacadeRemote;
import com.cbs.facade.clg.h2h.ClgH2hMgmtFacadeRemote;
import com.cbs.facade.npci.h2h.H2HNpciMgmtFacadeRemote;
import com.cbs.facade.other.OtherMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class H2hParameterBean extends BaseBean {

    private String message;
    private String module;
    private String field;
    private String value;
    private String neftBankName;
    private String isAuto;
    private String valueDis = "none";
    private String neftDis = "none";
    private List<SelectItem> moduleList;
    private List<SelectItem> fieldList;
    private List<SelectItem> neftBankList;
    private List<SelectItem> isAutoList;
    private OtherMgmtFacadeRemote otherRemote;
    private CommonReportMethodsRemote commonReport;
    private CkycrProcessMgmtFacadeRemote ckycrFacade;
    private ClgH2hMgmtFacadeRemote ctsFacade;
    private H2HNpciMgmtFacadeRemote h2hNpciFacade;

    public H2hParameterBean() {
        try {
            otherRemote = (OtherMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherMgmtFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            ckycrFacade = (CkycrProcessMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrProcessMgmtFacade");
            ctsFacade = (ClgH2hMgmtFacadeRemote) ServiceLocator.getInstance().lookup("ClgH2hMgmtFacade");
            h2hNpciFacade = (H2HNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("H2HNpciMgmtFacade");

            moduleList = new ArrayList<>();
            moduleList.add(new SelectItem("0", "--Select--"));
            List searchList = this.commonReport.getRefRecList("1911");
            for (int i = 0; i < searchList.size(); i++) {
                Vector vec = (Vector) searchList.get(i);
                moduleList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
            }

            fieldList = new ArrayList<>();
            fieldList.add(new SelectItem("0", "--Select--"));

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void moduleOptionOnblur() {
        try {
            String gNo = "H2HMAP";
            fieldList = new ArrayList<>();
            fieldList.add(new SelectItem("0", "--Select--"));
            List fieldPrirortyList = this.otherRemote.cbsAutoNeftDetailFieldListBasesOnModulePrirorty(gNo, this.module);
            for (int i = 0; i < fieldPrirortyList.size(); i++) {
                Vector ele = (Vector) fieldPrirortyList.get(i);
                fieldList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            if (this.module.equalsIgnoreCase("neft")) {
                this.neftDis = "";
                neftBankList = new ArrayList<>();
                neftBankList.add(new SelectItem("0", "--Select--"));
                List bankList = this.otherRemote.cbsNeftBankNameList();
                for (int i = 0; i < bankList.size(); i++) {
                    Vector ele = (Vector) bankList.get(i);
                    neftBankList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }

                isAutoList = new ArrayList<>();
                isAutoList.add(new SelectItem("0", "--Select--"));
                isAutoList.add(new SelectItem("AUTO", "AUTO"));
                isAutoList.add(new SelectItem("MANUAL", "MANUAL"));

            } else {
                this.neftDis = "none";
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void fieldOptionOnblur() {
        this.valueDis = "";

    }

    public void updateBtnAction() {
        try {
            if (this.module.equalsIgnoreCase("0") || this.module == null) {
                this.setMessage("Please select the module option.");
                return;
            }
            if (this.field.equalsIgnoreCase("0") || this.field == null || this.field.equalsIgnoreCase("")) {
                this.setMessage("Please select the fiald option.");
                return;
            }

            if (this.value.equalsIgnoreCase("") || this.value == null) {
                this.setMessage("Please fill the field of value.");
                return;
            }
            String result = "";
            if (this.module.equalsIgnoreCase("neft")) {
                if (this.neftBankName.equalsIgnoreCase("0") || this.neftBankName == null) {
                    this.setMessage("Please select the neft bank name option");
                    return;
                }
                if (this.isAuto.equalsIgnoreCase("0") || this.isAuto == null || this.isAuto.equalsIgnoreCase("")) {
                    this.setMessage("Please select the field auto option");
                    return;
                }

                result = this.otherRemote.updateH2hParameter(this.field, this.value, this.neftBankName, this.isAuto);

            } else if (this.module.equalsIgnoreCase("CTS")) {
                result = ctsFacade.updateModuleConfigProperty(field, value);
            } else if (this.module.equalsIgnoreCase("NPCI")) {
                result = h2hNpciFacade.updateModuleConfigProperty(field, value);
            } else if (this.module.equalsIgnoreCase("CKYCR")) {
                result = ckycrFacade.updateModuleConfigProperty(field, value);
            } else {
                this.setMessage("Please Select Module Name");
            }
            if (result.equalsIgnoreCase("true")) {
                this.btnRefreshAction();
                this.setMessage("Detail successfully updated.");
            } else {
                this.setMessage(result);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getLocalizedMessage());
        }

    }

    public void btnRefreshAction() {
        this.setField("0");
        this.setModule("0");
        this.setValue("");
        this.setMessage("");
        this.setNeftBankName("0");
        this.setIsAuto("0");
        this.valueDis = "none";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<SelectItem> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<SelectItem> moduleList) {
        this.moduleList = moduleList;
    }

    public List<SelectItem> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<SelectItem> fieldList) {
        this.fieldList = fieldList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueDis() {
        return valueDis;
    }

    public void setValueDis(String valueDis) {
        this.valueDis = valueDis;
    }

    public String getNeftBankName() {
        return neftBankName;
    }

    public void setNeftBankName(String neftBankName) {
        this.neftBankName = neftBankName;
    }

    public String getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto;
    }

    public String getNeftDis() {
        return neftDis;
    }

    public void setNeftDis(String neftDis) {
        this.neftDis = neftDis;
    }

    public List<SelectItem> getNeftBankList() {
        return neftBankList;
    }

    public void setNeftBankList(List<SelectItem> neftBankList) {
        this.neftBankList = neftBankList;
    }

    public List<SelectItem> getIsAutoList() {
        return isAutoList;
    }

    public void setIsAutoList(List<SelectItem> isAutoList) {
        this.isAutoList = isAutoList;
    }
}
