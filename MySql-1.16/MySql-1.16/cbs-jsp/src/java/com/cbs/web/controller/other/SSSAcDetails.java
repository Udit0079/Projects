/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.SSSFileGeneratorFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.PMBSystemGrid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class SSSAcDetails extends BaseBean {

    private String message;
    private String schemeCode;
    private List<SelectItem> schemeCodeList;
    private String vendorCode;
    private List<SelectItem> vendorCodeList;
    private String premiumAmt;
    private String insuredAamt;
    private String schemeGl;
    private String glAmt;
    private String schemePl;
    private String plAmt;
    private String agentAmt;
    private String effectiveDate;
    private String enterBy;
    private String enterDate;
    private String policyNo;
    private String autoDebitFreq;
    private List<SelectItem> autoDebitFreqList;
    private List<PMBSystemGrid> gridDetail;
    private PMBSystemGrid currentItem = new PMBSystemGrid();
    private boolean gridFlag;
    private boolean updateDisable;
    private boolean addNewDisable;
    private SSSFileGeneratorFacadeRemote reportRemote = null;
    private CommonReportMethodsRemote sssRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    int currentRow;

    public SSSAcDetails() {
        try {
            reportRemote = (SSSFileGeneratorFacadeRemote) ServiceLocator.getInstance().lookup("SSSFileGeneratorFacade");
            sssRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            autoDebitFreqList = new ArrayList<SelectItem>();
            autoDebitFreqList.add(new SelectItem("S", "--Select--"));
            autoDebitFreqList.add(new SelectItem("M", "Monthly"));
            autoDebitFreqList.add(new SelectItem("Y", "Yearly"));

            getScheme();
            getVendor();
            gridLoad();

            setUpdateDisable(true);
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getScheme() {
        try {
            schemeCodeList = new ArrayList<SelectItem>();
            List list = sssRemote.getRefRecList("215");
            schemeCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                schemeCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void getVendor() {
        try {
            vendorCodeList = new ArrayList<SelectItem>();
            List list = sssRemote.getRefRecList("216");
            vendorCodeList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                vendorCodeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void fillValuesofGridInFields() {
        setMessage("");
        try {
            setAgentAmt(currentItem.getAgentAmt());
            setEffectiveDate(currentItem.getEffectiveDate());
            setGlAmt(currentItem.getGlAmt());
            setInsuredAamt(currentItem.getInsuredAamt());
            setPlAmt(currentItem.getPlAmt());
            setPremiumAmt(currentItem.getPremiumAmt());
            setSchemeCode(currentItem.getSchemeCode());
            setSchemeGl(currentItem.getSchemeGl());
            setSchemePl(currentItem.getSchemePl());
            setVendorCode(currentItem.getVendorCode());
            setPolicyNo(currentItem.getPolicyNo());
            setAutoDebitFreq(currentItem.getAutoFreq());
            setUpdateDisable(false);
            setAddNewDisable(true);
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    private void gridLoad() {
        setMessage("");
        try {
            gridDetail = new ArrayList<PMBSystemGrid>();
            List list = reportRemote.getPmbsData();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    PMBSystemGrid obj = new PMBSystemGrid();
                    obj.setTxnId(vtr.get(0).toString());
                    obj.setSchemeCode(vtr.get(1).toString());
                    obj.setVendorCode(vtr.get(2).toString());
                    obj.setPremiumAmt(vtr.get(3).toString());
                    obj.setInsuredAamt(vtr.get(4).toString());
                    obj.setSchemeGl(vtr.get(5).toString());
                    obj.setGlAmt(vtr.get(6).toString());
                    obj.setSchemePl(vtr.get(7).toString());
                    obj.setPlAmt(vtr.get(8).toString());
                    obj.setAgentAmt(vtr.get(9).toString());
                    obj.setEffectiveDate(vtr.get(10).toString());
                    obj.setEnterBy(vtr.get(11).toString());
                    obj.setEnterDate(vtr.get(12).toString());
                    obj.setPolicyNo(vtr.get(13).toString());
                    obj.setAutoFreq(vtr.get(14).toString());
                    gridDetail.add(obj);
                }
            }
            this.setUpdateDisable(true);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onblurVendorCode() {
        setMessage("");
        try {
            List list = reportRemote.getSSSDetail(schemeCode, vendorCode);
            gridDetail = new ArrayList<PMBSystemGrid>();
            if (!list.isEmpty()) {
                for (int j = 0; j < list.size(); j++) {
                    Vector ele = (Vector) list.get(j);
                    PMBSystemGrid pojo = new PMBSystemGrid();
                    pojo.setTxnId(ele.get(0).toString());
                    pojo.setSchemeCode(ele.get(1).toString());
                    pojo.setVendorCode(ele.get(2).toString());
                    pojo.setPremiumAmt(ele.get(3).toString());
                    pojo.setInsuredAamt(ele.get(4).toString());
                    pojo.setSchemeGl(ele.get(5).toString());
                    pojo.setGlAmt(ele.get(6).toString());
                    pojo.setSchemePl(ele.get(7).toString());
                    pojo.setPlAmt(ele.get(8).toString());
                    pojo.setAgentAmt(ele.get(9).toString());
                    pojo.setEffectiveDate(ele.get(10).toString());
                    pojo.setEnterBy(ele.get(11).toString());
                    pojo.setEnterDate(ele.get(12).toString());
                    pojo.setPolicyNo(ele.get(13).toString());
                    pojo.setAutoFreq(ele.get(14).toString());
                    gridDetail.add(pojo);
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public boolean validate() throws ApplicationException {
        setMessage("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {

            if (schemeCode.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select Scheme Code !");
            }
            if (vendorCode.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select Scheme Code !");
            }

            if (this.autoDebitFreq.equalsIgnoreCase("S")) {
                throw new ApplicationException("Please select Auto Debit Freq");
            }

            if (this.policyNo.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Policy No / Acno");
            }
            if (!this.policyNo.matches("[a-zA-Z0-9,-/ ]*")) {
                throw new ApplicationException("Please Enter Policy No / Acno Correctly");
            }
            if (insuredAamt == null || insuredAamt.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Insure Amount");
            }
            if (!this.insuredAamt.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                throw new ApplicationException("Please enter only numeric values in Insured Amount !");
            }
            if (glAmt == null || glAmt.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill GL Amount");
            }
            if (!this.glAmt.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                throw new ApplicationException("Please enter only numeric values in GL Amount !");
            }
            if (schemeGl == null || schemeGl.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Scheme GL Head");
            }
            if (!this.schemeGl.matches("[0-9]*")) {
                throw new ApplicationException("Please enter only numeric values in Scheme GL Head !");
            }
            if (this.schemeGl.length() != 12) {
                throw new ApplicationException("Scheme GL Head should be 12 digits !");
            }
            String msg = ftsRemote.ftsAcnoValidate(this.schemeGl, 1, "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            String chkAcno = ftsRemote.getNewAccountNumber(this.schemeGl);
            if (!chkAcno.equalsIgnoreCase(this.schemeGl)) {
                throw new ApplicationException("Acount No invalid");
            }

            if (premiumAmt == null || premiumAmt.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Premium Amount");
            }
            if (!this.premiumAmt.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                throw new ApplicationException("Please enter only numeric values in Premium Amount !");
            }
            if (plAmt == null || plAmt.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill PL Amount");
            }
            if (!this.plAmt.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                throw new ApplicationException("Please enter only numeric values in PL Amount !");
            }

            if (schemePl == null || schemePl.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Scheme PL Head");
            }
            if (!schemePl.matches("[0-9]*")) {
                throw new ApplicationException("Please enter only numeric values in Scheme PL Head  !");
            }
            if (this.schemePl.length() != 6) {
                throw new ApplicationException("Scheme PL Head should be 6 digits !");
            }
            String plAcno = getOrgnBrCode() + "06" + this.schemePl + "01";
            String msg1 = ftsRemote.ftsAcnoValidate(plAcno, 1, "");
            if (!msg1.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            if (agentAmt == null || agentAmt.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Agent Amount");
            }
            if (!this.agentAmt.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                throw new ApplicationException("Please enter only numeric values in Agement Amount !");
            }

            if (effectiveDate == null || effectiveDate.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Effect date");
            }
            if (!Validator.validateDate(effectiveDate)) {
                throw new ApplicationException("Please fill Proper date");
            }

            if ((dmy.parse(effectiveDate)).after(dt)) {
                throw new ApplicationException("Effective date should be less than current date");
            }

            if (updateDisable == true) {
                List list = reportRemote.getMaxEffectDate();
                Vector vtr = (Vector) list.get(0);
                String maxEffectDate = vtr.get(0).toString();
                if (!maxEffectDate.equalsIgnoreCase("")) {
                    if (dmy.parse(maxEffectDate).after(dmy.parse(this.effectiveDate))) {
                        throw new ApplicationException("Effective date should be greater than previous effect date.");
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return true;
    }

    public void saveBtn() {
        try {
            setMessage("");
            if (validate()) {
                String result = reportRemote.pmbsDetailSave(this.schemeCode, this.vendorCode, this.premiumAmt, this.insuredAamt, this.schemeGl, this.glAmt,
                        this.schemePl, this.plAmt, this.agentAmt, ymd.format(dmy.parse(this.effectiveDate)), getUserName(), ymd.format(dt), this.policyNo, this.autoDebitFreq);
                if (result.equalsIgnoreCase("true")) {
                    onblurVendorCode();
                    resetForm1();
                    setMessage("Data has been saved successfully.");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void updateBtn() {
        setMessage("");
        try {
            if (validate()) {
                String result = reportRemote.pmbsDetailUpdate(currentItem.getTxnId(), schemeCode, vendorCode, premiumAmt, insuredAamt, schemeGl, glAmt, schemePl, plAmt,
                        agentAmt, ymd.format(dmy.parse(this.effectiveDate)), getUserName(), ymd.format(dt), this.policyNo);

                if (result.equalsIgnoreCase("true")) {
                    onblurVendorCode();
                    resetForm();
                    setMessage("Data has been Updated successfully.");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        setMessage("");
        setSchemeCode("S");
        setVendorCode("S");
        setAgentAmt("");
        setGlAmt("");
        setPlAmt("");
        setPremiumAmt("");
        setInsuredAamt("");
        setSchemeGl("");
        setSchemePl("");
        setPolicyNo("");
        setAutoDebitFreq("S");
        setEffectiveDate(dmy.format(dt));
        setAddNewDisable(false);
        setUpdateDisable(true);
        gridDetail = new ArrayList<PMBSystemGrid>();
    }

    public void resetForm1() {
        setMessage("");
        setSchemeCode("S");
        setVendorCode("S");
        setAgentAmt("");
        setGlAmt("");
        setPlAmt("");
        setPremiumAmt("");
        setInsuredAamt("");
        setSchemeGl("");
        setSchemePl("");
        setPolicyNo("");
        setAutoDebitFreq("S");
        setEffectiveDate(dmy.format(dt));
        setAddNewDisable(false);
        setUpdateDisable(true);
    }

    public String exitForm() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }

    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }

    public List<SelectItem> getVendorCodeList() {
        return vendorCodeList;
    }

    public void setVendorCodeList(List<SelectItem> vendorCodeList) {
        this.vendorCodeList = vendorCodeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getPremiumAmt() {
        return premiumAmt;
    }

    public void setPremiumAmt(String premiumAmt) {
        this.premiumAmt = premiumAmt;
    }

    public String getInsuredAamt() {
        return insuredAamt;
    }

    public void setInsuredAamt(String insuredAamt) {
        this.insuredAamt = insuredAamt;
    }

    public String getSchemeGl() {
        return schemeGl;
    }

    public void setSchemeGl(String schemeGl) {
        this.schemeGl = schemeGl;
    }

    public String getGlAmt() {
        return glAmt;
    }

    public void setGlAmt(String glAmt) {
        this.glAmt = glAmt;
    }

    public String getSchemePl() {
        return schemePl;
    }

    public void setSchemePl(String schemePl) {
        this.schemePl = schemePl;
    }

    public String getPlAmt() {
        return plAmt;
    }

    public void setPlAmt(String plAmt) {
        this.plAmt = plAmt;
    }

    public String getAgentAmt() {
        return agentAmt;
    }

    public void setAgentAmt(String agentAmt) {
        this.agentAmt = agentAmt;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public List<PMBSystemGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<PMBSystemGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public PMBSystemGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(PMBSystemGrid currentItem) {
        this.currentItem = currentItem;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public boolean isGridFlag() {
        return gridFlag;
    }

    public void setGridFlag(boolean gridFlag) {
        this.gridFlag = gridFlag;

    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public boolean isAddNewDisable() {
        return addNewDisable;
    }

    public void setAddNewDisable(boolean addNewDisable) {
        this.addNewDisable = addNewDisable;
    }

    public String getAutoDebitFreq() {
        return autoDebitFreq;
    }

    public void setAutoDebitFreq(String autoDebitFreq) {
        this.autoDebitFreq = autoDebitFreq;
    }

    public List<SelectItem> getAutoDebitFreqList() {
        return autoDebitFreqList;
    }

    public void setAutoDebitFreqList(List<SelectItem> autoDebitFreqList) {
        this.autoDebitFreqList = autoDebitFreqList;
    }
}
