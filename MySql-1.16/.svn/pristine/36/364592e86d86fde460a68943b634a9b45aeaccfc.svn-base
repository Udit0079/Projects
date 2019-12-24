/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.master.CrrDailyEntryFacadeRemote;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class CrrEntryForm extends BaseBean {

    private String message;
    private String acdesc;
    private String acCategory;
    private String fromHead;
    private String newFromGLHead;
    private String toHead;
    private String newToGLHead;
    private String acnoItem;
    private String cmbview;
    private String altOption;
    private String alternateValue;
    private boolean seriesDisable;
    private boolean indivisualDisable;
    private List<SelectItem> acdescList;
    private List<SelectItem> acCategoryList;
    private List<SelectItem> acnoItemList;
    private List<SelectItem> cmbviewList;
    private List<SelectItem> altOptionList;
    private List<SelectItem> alternateList;
    private CrrDailyEntryFacadeRemote remoteObj = null;
    private DailyMasterFacadeRemote remoteBeanObj = null;
    private FtsPostingMgmtFacadeRemote ftsBeanRemote = null;

    public String getAltOption() {
        return altOption;
    }

    public void setAltOption(String altOption) {
        this.altOption = altOption;
    }

    public List<SelectItem> getAltOptionList() {
        return altOptionList;
    }

    public void setAltOptionList(List<SelectItem> altOptionList) {
        this.altOptionList = altOptionList;
    }

    public boolean isIndivisualDisable() {
        return indivisualDisable;
    }

    public void setIndivisualDisable(boolean indivisualDisable) {
        this.indivisualDisable = indivisualDisable;
    }

    public boolean isSeriesDisable() {
        return seriesDisable;
    }

    public void setSeriesDisable(boolean seriesDisable) {
        this.seriesDisable = seriesDisable;
    }

    public List<SelectItem> getAcCategoryList() {
        return acCategoryList;
    }

    public void setAcCategoryList(List<SelectItem> acCategoryList) {
        this.acCategoryList = acCategoryList;
    }

    public String getAcCategory() {
        return acCategory;
    }

    public void setAcCategory(String acCategory) {
        this.acCategory = acCategory;
    }

    public String getAcdesc() {
        return acdesc;
    }

    public void setAcdesc(String acdesc) {
        this.acdesc = acdesc;
    }

    public String getFromHead() {
        return fromHead;
    }

    public void setFromHead(String fromHead) {
        this.fromHead = fromHead;
    }

    public String getToHead() {
        return toHead;
    }

    public void setToHead(String toHead) {
        this.toHead = toHead;
    }

    public String getNewFromGLHead() {
        return newFromGLHead;
    }

    public void setNewFromGLHead(String newFromGLHead) {
        this.newFromGLHead = newFromGLHead;
    }

    public String getNewToGLHead() {
        return newToGLHead;
    }

    public void setNewToGLHead(String newToGLHead) {
        this.newToGLHead = newToGLHead;
    }

    public List<SelectItem> getCmbviewList() {
        return cmbviewList;
    }

    public void setCmbviewList(List<SelectItem> cmbviewList) {
        this.cmbviewList = cmbviewList;
    }

    public String getCmbview() {
        return cmbview;
    }

    public void setCmbview(String cmbview) {
        this.cmbview = cmbview;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getAlternateList() {
        return alternateList;
    }

    public void setAlternateList(List<SelectItem> alternateList) {
        this.alternateList = alternateList;
    }

    public String getAlternateValue() {
        return alternateValue;
    }

    public void setAlternateValue(String alternateValue) {
        this.alternateValue = alternateValue;
    }

    public String getAcnoItem() {
        return acnoItem;
    }

    public void setAcnoItem(String acnoItem) {
        this.acnoItem = acnoItem;
    }

    public List<SelectItem> getAcnoItemList() {
        return acnoItemList;
    }

    public void setAcnoItemList(List<SelectItem> acnoItemList) {
        this.acnoItemList = acnoItemList;
    }

    public List<SelectItem> getAcdescList() {
        return acdescList;
    }

    public void setAcdescList(List<SelectItem> acdescList) {
        this.acdescList = acdescList;
    }

    public CrrEntryForm() {
        this.setMessage("");
        try {
            remoteObj = (CrrDailyEntryFacadeRemote) ServiceLocator.getInstance().lookup("CrrDailyEntryFacade");
            remoteBeanObj = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            acdescList = new ArrayList<SelectItem>();
            acCategoryList = new ArrayList<SelectItem>();
            acnoItemList = new ArrayList<SelectItem>();
            cmbviewList = new ArrayList<SelectItem>();
            alternateList = new ArrayList<SelectItem>();
            altOptionList = new ArrayList<SelectItem>();

            fillAcDescAndAlternateColumnList();
            fillAccountCategoryList();
            fillAcnoItemList();
            fillComboViewReportList();
            fillAltOptionList();
            refreshForm();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillAcDescAndAlternateColumnList() {
        try {
            List resultList = remoteObj.fillAcDescAndAlternateColumn();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector element = (Vector) resultList.get(i);
                    acdescList.add(new SelectItem(element.get(0).toString()));
                    alternateList.add(new SelectItem(element.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillAccountCategoryList() {
        try {
            acCategoryList.add(new SelectItem("0", "--Select--"));
            acCategoryList.add(new SelectItem("I", "Individual A/c No"));
            acCategoryList.add(new SelectItem("S", "Series of A/c No"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillAcnoItemList() {
        try {
            List resultList = remoteObj.acnoItem();
            for (int i = 0; i < resultList.size(); i++) {
                Vector element = (Vector) resultList.get(i);
                String acDesc = element.get(0).toString();
                String acno = element.get(1).toString();
                acnoItemList.add(new SelectItem(acDesc + "***" + acno));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillComboViewReportList() {
        cmbviewList.add(new SelectItem("Not Visible On Report"));
        cmbviewList.add(new SelectItem("Visible On Report"));
    }

    public void fillAltOptionList() {
        altOptionList.add(new SelectItem("Non-Alternate"));
        altOptionList.add(new SelectItem("Alternate"));
    }

    public void processAccountCategory() {
        this.setMessage("");
        this.setNewFromGLHead("");
        this.setNewToGLHead("");
        try {
            if (this.acCategory.equals("0")) {
                this.setMessage("Please select account category !");
                return;
            }
            if (this.acCategory.equals("I")) {
                this.indivisualDisable = false;
                this.seriesDisable = true;
            } else if (this.acCategory.equals("S")) {
                this.indivisualDisable = true;
                this.seriesDisable = false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fromGLHeadAction() {
        this.setMessage("");
        this.setNewFromGLHead("");
        try {
            String fromGlHead = ftsBeanRemote.getNewAccountNumberForHo(this.getFromHead());
            this.newFromGLHead = fromGlHead.substring(2, 10);
            List checkList = remoteBeanObj.acNameForBankPurpose(this.newFromGLHead);
            if (checkList.isEmpty()) {
                this.setMessage("Entered From GL Head is not present !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void toGLHeadAction() {
        this.setMessage("");
        this.setNewToGLHead("");
        try {
            String toGlHead = ftsBeanRemote.getNewAccountNumberForHo(getToHead());
            this.newToGLHead = toGlHead.substring(2, 10);
            List checkList = remoteBeanObj.acNameForBankPurpose(this.newToGLHead);
            if (checkList.isEmpty()) {
                this.setMessage("Entered From GL Head is not present !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processSaveAction() {
        this.setMessage("");
        try {
            if (this.acCategory.equals("0")) {
                this.setMessage("Please select account category !");
                return;
            }
            if (this.acCategory.equals("I")) {
                String msg = remoteObj.saveIndivisual(acdesc, acnoItem, cmbview, altOption, alternateValue);
                if (msg.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been saved successfully !");
                }
            } else if (this.acCategory.equals("S")) {
                if (this.fromHead == null || this.fromHead.equalsIgnoreCase("") || this.fromHead.length() < 8) {
                    this.setMessage("Please fill 8 digit account number: Which contains 2 digit account type plus 6 digit account number !");
                    return;
                }
                if (this.toHead == null || this.toHead.equalsIgnoreCase("") || this.toHead.length() < 8) {
                    this.setMessage("Please fill 8 digit account number: Which contains 2 digit account type plus 6 digit account number !");
                    return;
                }
                String msg = remoteObj.saveSeries(acdesc, fromHead, toHead, altOption, alternateValue);
                if (msg.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been saved successfully !");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processDeleteAction() {
        this.setMessage("");
        try {
            if (this.acCategory.equals("0")) {
                this.setMessage("Please select account category !");
                return;
            }
            if (this.acCategory.equals("I")) {
                int firstHifenIndex = acnoItem.indexOf("*");
                String acnoItemDesc = acnoItem.substring(0, firstHifenIndex);
                String acnoItemAccount = acnoItem.substring(firstHifenIndex + 3);

                String msg = remoteObj.deleteIndivisual(acdesc, acnoItemAccount, acnoItemDesc, getUserName());
                if (msg.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been deleted successfully !");
                }
            } else if (this.acCategory.equals("S")) {
                if (this.fromHead == null || this.fromHead.equalsIgnoreCase("")) {
                    if (this.toHead != null || (!this.toHead.equalsIgnoreCase(""))) {
                        this.setMessage("Either both From GL Head and To GL Head will be blank or filled together !");
                    }
                }
                if (this.toHead == null || this.toHead.equalsIgnoreCase("")) {
                    if (this.fromHead != null || (!this.fromHead.equalsIgnoreCase(""))) {
                        this.setMessage("Either both From GL Head and To GL Head will be blank or filled together !");
                    }
                }
                String msg = remoteObj.deleteSeries(acdesc, fromHead, toHead);
                if (msg.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been deleted successfully !");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setFromHead("");
        this.setNewFromGLHead("");
        this.setToHead("");
        this.setNewToGLHead("");
        this.indivisualDisable = true;
        this.seriesDisable = true;
        this.setAltOption("Non-Alternate");
        this.setAcCategory("--Select--");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
