/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.inventory;

import com.cbs.dto.AtmMasterGrid;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class ATMMaster extends BaseBean {

    private String atmIdentifier;
    private String atmBranch;
    private String atmCashHead;
    private String atmStatus;
    private String message;
    private String city;
    private String atmAddress;
    private String sno;
    private String disableId;
    private String atmName;
    private String state;
    private String locationType;
    private String site;
    private boolean addFlag;
    private boolean modFlag;
    private boolean delFlag;
    private boolean flag;
    private boolean codeDisable;
    private boolean indFlagDisable;
    private boolean disFlag1;
    private boolean addNewFlag;
    private boolean flag1;
    private int currentRow;
    private AtmMasterGrid currentItem;
    private List<AtmMasterGrid> gridDetail;
    private List<SelectItem> atmBranchList;
    private List<SelectItem> atmStatusList;
    private List<SelectItem> stateList;
    private List<SelectItem> locationTypeList;
    private List<SelectItem> siteList;
    private InventorySplitAndMergeFacadeRemote invMaster;
    private CertIssueFacadeRemote certRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private CommonReportMethodsRemote reportRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public ATMMaster() throws UnknownHostException {
        this.setMessage("");
        try {
            invMaster = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup("InventorySplitAndMergeFacade");
            certRemote = (CertIssueFacadeRemote) ServiceLocator.getInstance().lookup("CertIssueFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setOnLoadData();
            gridLoad();
            this.setFlag(true);
            this.disableId = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setOnLoadData() {
        this.setMessage("");
        atmBranchList = new ArrayList<SelectItem>();
        atmStatusList = new ArrayList<SelectItem>();
        locationTypeList = new ArrayList<SelectItem>();
        siteList = new ArrayList<SelectItem>();
        stateList = new ArrayList<SelectItem>();
        try {
            /*Setting of branch list*/
            List result = certRemote.getAllBranchExcludingHoAndCell();
            if (result.isEmpty()) {
                this.setMessage("There is no branch in branchmaster.");
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                atmBranchList.add(new SelectItem(vtr.get(1).toString(), vtr.get(0).toString()));
            }
            /*Setting of status list*/
            atmStatusList.add(new SelectItem("A", "ACTIVE"));
            atmStatusList.add(new SelectItem("I", "INACTIVE"));
            /*Setting of location list*/
            locationTypeList.add(new SelectItem("URBAN", "URBAN"));
            locationTypeList.add(new SelectItem("RURAL", "RURAL"));
            locationTypeList.add(new SelectItem("METRO", "METRO"));
            /*Setting of site list*/
            siteList.add(new SelectItem("ON-SITE", "ON-SITE"));
            siteList.add(new SelectItem("OFF-SITE", "OFF-SITE"));
            /*Setting of state list*/
            result = certRemote.getAllRefRecNoData("002");
            if (result.isEmpty()) {
                this.setMessage("There should be data into Cbs Ref Rec Type for 002.");
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                stateList.add(new SelectItem(vtr.get(1).toString(), vtr.get(1).toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void gridLoad() {
        this.setMessage("");
        gridDetail = new ArrayList<AtmMasterGrid>();
        try {
            List resultList = invMaster.gridDetailATMMaster();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    AtmMasterGrid atm = new AtmMasterGrid();
                    Vector ele = (Vector) resultList.get(i);
                    atm.setAtmIdentifier(ele.get(0).toString());
                    atm.setAtmName(ele.get(1).toString());
                    String brCode = ele.get(2).toString();
                    atm.setAtmBranch(invMaster.getBranchName(brCode));
                    atm.setAtmBrCode(brCode);
                    atm.setAtmCashHead(ele.get(3).toString());
                    atm.setAtmStatus(ele.get(4).toString());
                    atm.setAtmAddress(ele.get(5).toString());
                    atm.setCity(ele.get(6).toString());
                    atm.setState(ele.get(7).toString());
                    atm.setLocationType(ele.get(8).toString());
                    atm.setSite(ele.get(9).toString());
                    atm.setEnterBy(ele.get(10).toString());
                    atm.setEnterDate(ele.get(11).toString());
                    gridDetail.add(atm);
                }
            } else {
                this.setMessage("There is no entry.");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void saveMasterDetail() {
        this.setMessage("");
        flag = true;
        try {
            String result = validateField();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            result = invMaster.SaveupdateRecordATM("Save", atmIdentifier, atmBranch, atmCashHead, atmStatus,
                    city, atmAddress, getUserName(), ymd.format(new Date()), atmName, state, locationType, site);
            if (!result.equalsIgnoreCase("true")) {
                this.setMessage(result);
                return;
            }
            gridLoad();
            refreshForm();
            this.setMessage("Data has been saved successfully.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void updateATMTypeDetail() {
        this.setMessage("");
        try {
            String result = validateField();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            result = invMaster.SaveupdateRecordATM("Update", atmIdentifier, atmBranch, atmCashHead,
                    atmStatus, city, atmAddress, getUserName(), ymd.format(new Date()), atmName, state, locationType, site);
            if (!result.equalsIgnoreCase("true")) {
                this.setMessage(result);
                return;
            }
            gridLoad();
            refreshForm();
            this.setMessage("Data has been updated successfully.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String validateField() {
        this.setMessage("");
        Pattern p = Pattern.compile("[0-9]*");
        Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-][a-zA-Z0-9,]+)*");
        try {
            if (this.atmIdentifier == null || this.atmIdentifier.equalsIgnoreCase("") || this.atmIdentifier.length() == 0) {
                return "Please enter Identifier.";
            }
            Matcher matcher = pm.matcher(this.atmIdentifier);
            if (!matcher.matches()) {
                return "Identifier should not contain special characters.";
            }
            if (this.atmName == null || this.atmName.equals("")) {
                return "Please fill ATM Name.";
            }
            if (this.atmCashHead == null || this.atmCashHead.equalsIgnoreCase("") || this.atmCashHead.length() != 12) {
                return "Please enter ATM Cash Head of 12 digit. It will be a HO account.";
            }
            matcher = pm.matcher(this.atmCashHead);
            if (!matcher.matches()) {
                return "Cash head should not contain special characters.";
            }
            matcher = p.matcher(this.atmCashHead);
            if (!matcher.matches()) {
                return "Cash head should be in numeric digits.";
            }
            if (!reportRemote.getBrncodeByAlphacode("HO").equalsIgnoreCase(ftsRemote.getCurrentBrnCode(this.atmCashHead))) {
                return "Please enter ATM Cash Head of 12 digit. It will be a HO account.";
            }
            String msg = ftsRemote.ftsAcnoValidate(this.atmCashHead, 1, "");
            if (!msg.equalsIgnoreCase("true")) {
                return msg;
            }
            if (this.atmAddress == null || this.atmAddress.equalsIgnoreCase("") || this.atmAddress.length() == 0) {
                return "Please enter location .";
            }
            if (this.city == null || this.city.equalsIgnoreCase("") || this.city.length() == 0) {
                return "Please enter city.";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "true";
    }

    public void select() {
        this.setMessage("");
        try {
            flag = false;
            this.disableId = "true";
            this.setAtmIdentifier(currentItem.getAtmIdentifier());
            this.setAtmBranch(currentItem.getAtmBrCode());
            this.setAtmName(currentItem.getAtmName());
            this.setAtmStatus(currentItem.getAtmStatus());
            this.setAtmCashHead(currentItem.getAtmCashHead());
            this.setAtmAddress(currentItem.getAtmAddress());
            this.setCity(currentItem.getCity());
            this.setState(currentItem.getState());
            this.setLocationType(currentItem.getLocationType());
            this.setSite(currentItem.getSite());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setAtmIdentifier("");
        this.setAtmName("");
        this.setAtmStatus("A");
        this.setAtmCashHead("");
        this.setAtmAddress("");
        this.setCity("");
        this.setLocationType("URBAN");
        this.setState("ON-SITE");
        this.setFlag(true);
        this.disableId = "false";
    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }

    /*Getter And Setter*/
    public List<SelectItem> getAtmBranchList() {
        return atmBranchList;
    }

    public boolean isCodeDisable() {
        return codeDisable;
    }

    public void setCodeDisable(boolean codeDisable) {
        this.codeDisable = codeDisable;
    }

    public boolean isIndFlagDisable() {
        return indFlagDisable;
    }

    public void setIndFlagDisable(boolean indFlagDisable) {
        this.indFlagDisable = indFlagDisable;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setAtmBranchList(List<SelectItem> atmBranchList) {
        this.atmBranchList = atmBranchList;
    }

    public String getSno() {
        return sno;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public AtmMasterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AtmMasterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public List<AtmMasterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AtmMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isAddNewFlag() {
        return addNewFlag;
    }

    public void setAddNewFlag(boolean addNewFlag) {
        this.addNewFlag = addNewFlag;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isDisFlag1() {
        return disFlag1;
    }

    public void setDisFlag1(boolean disFlag1) {
        this.disFlag1 = disFlag1;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public CertIssueFacadeRemote getCertRemote() {
        return certRemote;
    }

    public void setCertRemote(CertIssueFacadeRemote certRemote) {
        this.certRemote = certRemote;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isModFlag() {
        return modFlag;
    }

    public void setModFlag(boolean modFlag) {
        this.modFlag = modFlag;
    }

    public String getAtmBranch() {
        return atmBranch;
    }

    public void setAtmBranch(String atmBranch) {
        this.atmBranch = atmBranch;
    }

    public String getAtmCashHead() {
        return atmCashHead;
    }

    public void setAtmCashHead(String atmCashHead) {
        this.atmCashHead = atmCashHead;
    }

    public String getAtmIdentifier() {
        return atmIdentifier;
    }

    public void setAtmIdentifier(String atmIdentifier) {
        this.atmIdentifier = atmIdentifier;
    }

    public String getAtmStatus() {
        return atmStatus;
    }

    public void setAtmStatus(String atmStatus) {
        this.atmStatus = atmStatus;
    }

    public List<SelectItem> getAtmStatusList() {
        return atmStatusList;
    }

    public void setAtmStatusList(List<SelectItem> atmStatusList) {
        this.atmStatusList = atmStatusList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAtmAddress() {
        return atmAddress;
    }

    public void setAtmAddress(String atmAddress) {
        this.atmAddress = atmAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDisableId() {
        return disableId;
    }

    public void setDisableId(String disableId) {
        this.disableId = disableId;
    }

    public String getAtmName() {
        return atmName;
    }

    public void setAtmName(String atmName) {
        this.atmName = atmName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<SelectItem> getStateList() {
        return stateList;
    }

    public void setStateList(List<SelectItem> stateList) {
        this.stateList = stateList;
    }

    public List<SelectItem> getLocationTypeList() {
        return locationTypeList;
    }

    public void setLocationTypeList(List<SelectItem> locationTypeList) {
        this.locationTypeList = locationTypeList;
    }

    public List<SelectItem> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<SelectItem> siteList) {
        this.siteList = siteList;
    }
}