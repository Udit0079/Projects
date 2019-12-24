/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.BankIinDetailPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class BankIinDetail extends BaseBean {

    private String message;
    private String bankName;
    private String iinNo;
    private String bankType;
    private String ifscCode;
    private String micr;
    private List<SelectItem> bankList;
    AdvancesInformationTrackingRemote aitr;
    private CommonReportMethodsRemote commonReport;
    private BankIinDetailPojo currentItem = new BankIinDetailPojo();

    public BankIinDetail() {
        try {
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            List optionList = aitr.getBankNameList();
            bankList = new ArrayList<SelectItem>();
            bankList.add(new SelectItem("000000", "--Select--"));
            for (int i = 0; i < optionList.size(); i++) {
                Vector ele7 = (Vector) optionList.get(i);
                bankList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
                bankOptionOnblur();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void bankOptionOnblur() {
        try {
            this.setIinNo(this.bankName);
            List dataList = commonReport.getBankDetails(iinNo);
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele7 = (Vector) dataList.get(i);
                this.setBankType(ele7.get(0).toString());
                this.setIfscCode(ele7.get(1).toString());
                this.setMicr(ele7.get(2).toString());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setBankName("0");
        this.setIinNo("");
        this.setBankType("");
        this.setIfscCode("");
        this.setMicr("");
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<SelectItem> getBankList() {
        return bankList;
    }

    public void setBankList(List<SelectItem> bankList) {
        this.bankList = bankList;
    }

    public String getIinNo() {
        return iinNo;
    }

    public void setIinNo(String iinNo) {
        this.iinNo = iinNo;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public BankIinDetailPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(BankIinDetailPojo currentItem) {
        this.currentItem = currentItem;
    }
}