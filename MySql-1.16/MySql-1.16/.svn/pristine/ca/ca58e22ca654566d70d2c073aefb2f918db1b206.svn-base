/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.pojo.BadPersonInfoPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
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
public class BadPersonInfo extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String sections;
    private List<SelectItem> sectionsList;
    private String badPerId;
    private String title;
    private String name;
    private String dob;
    private String pob;
    private String designation;
    private String goodQuality;
    private String lowQuality;
    private String nationality;
    private List<SelectItem> nationalityList;
    private String nationalName;
    private String national;
    private List<SelectItem> nationalList;
    private String passportNo;
    private String nationalIdNo;
    private String address;
    private String listedNo;
    private String otherInfo;
    private String enterBy;
    private String enterDate;
    private String updateBy;
    private String updateDate;
    private String goodFlag;
    private String lowFlag;
    private String btnValue;
    private boolean btnFlag;
    private boolean disableNational;
    private String confirmationMsg;
    private List<BadPersonInfoPojo> gridDetail;
    private AdvancesInformationTrackingRemote advInfoRemote;
    private DDSManagementFacadeRemote ddsMgmtRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public BadPersonInfo() {
        try {
            advInfoRemote = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            ddsMgmtRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("S", "--Select--"));
            functionList.add(new SelectItem("A", "Add"));
            functionList.add(new SelectItem("U", "Update"));
            functionList.add(new SelectItem("D", "Delete"));

            sectionsList = new ArrayList<SelectItem>();
            sectionsList.add(new SelectItem("S", "--Select--"));
            sectionsList.add(new SelectItem("I", "Individuals"));
            sectionsList.add(new SelectItem("E", "Entities and other groups"));


            nationalList = new ArrayList<SelectItem>();
            nationalList.add(new SelectItem("N", "Nationality"));

            setNational("");
            this.disableNational = true;


            List countryTable = advInfoRemote.refRecCode("302");
            nationalityList = new ArrayList<SelectItem>();
            nationalityList.add(new SelectItem("S", "--Select--"));
            for (int i = 0; i < countryTable.size(); i++) {
                Vector element = (Vector) countryTable.get(i);
                nationalityList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            this.setBtnValue("Save");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void chgFunction() {
        setMessage("");
        try {
            gridDetail = new ArrayList<BadPersonInfoPojo>();
            if (function.equalsIgnoreCase("A")) {
                setBtnValue("Save");
            } else if (function.equalsIgnoreCase("U")) {
                setBtnValue("Update");
            } else if (function.equalsIgnoreCase("D")) {
                setBtnValue("Delete");
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void sectionsAction() {
        setMessage("");
        try {
            gridDetail = new ArrayList<BadPersonInfoPojo>();
            if (!function.equalsIgnoreCase("A")) {
                List listData = ddsMgmtRemote.getBadPersonSecWiseData(sections);
                if (!listData.isEmpty()) {
                    for (int i = 0; i < listData.size(); i++) {
                        Vector vtr = (Vector) listData.get(i);
                        BadPersonInfoPojo pojo = new BadPersonInfoPojo();
                        if (vtr.get(0).toString().equalsIgnoreCase("I")) {
                            pojo.setSec("Individuals");
                        } else {
                            pojo.setSec("Entities and other groups");
                        }
                        pojo.setPerId(vtr.get(1).toString());
                        pojo.setTitle(vtr.get(2).toString());
                        pojo.setCustName(vtr.get(3).toString());
                        pojo.setDob(vtr.get(4).toString());
                        pojo.setPob(vtr.get(5).toString());
                        pojo.setDesignation(vtr.get(6).toString());
                        pojo.setAddress(vtr.get(7).toString());
                        pojo.setGoodQuality(vtr.get(8).toString());
                        pojo.setLowQuality(vtr.get(9).toString());
                        pojo.setPassportNo(vtr.get(10).toString());
                        pojo.setNationality(ddsMgmtRemote.getnatioality(vtr.get(11).toString()));
                        pojo.setEnterBy(vtr.get(12).toString());
                        pojo.setEnterDate(vtr.get(13).toString());

                        gridDetail.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void chgSections() {
        setMessage("");
        try {
            if (!function.equalsIgnoreCase("A")) {
                List listData = ddsMgmtRemote.getBadPersonData(sections, badPerId);
                if (!listData.isEmpty()) {
                    Vector vtr = (Vector) listData.get(0);
                    setTitle(vtr.get(0).toString());
                    setName(vtr.get(1).toString());
                    setDob(vtr.get(2).toString());
                    setPob(vtr.get(3).toString());
                    setDesignation(vtr.get(4).toString());
                    setAddress(vtr.get(5).toString());
                    setListedNo(vtr.get(6).toString());
                    setGoodQuality(vtr.get(7).toString());
                    setLowQuality(vtr.get(8).toString());
                    setPassportNo(vtr.get(9).toString());
                    setNationality(vtr.get(10).toString());
                    setNationalIdNo(vtr.get(11).toString());
                    setOtherInfo(vtr.get(12).toString());

                } else {
                    setMessage("Data does not for Modify/Update");
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void checkBoxFunc() {
        setMessage("");
        if (national.equalsIgnoreCase("N")) {
            this.disableNational = false;
            this.nationality = nationalName;
        } else {
            this.disableNational = true;
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
        }
    }

    public boolean validateField() throws ApplicationException {
        setMessage("");
        Pattern pc = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
        try {
            if ((this.function == null) || (this.getFunction().equalsIgnoreCase(""))) {
                throw new ApplicationException("Please Select Function.");
            }

            if ((this.sections == null) || (this.sections.equalsIgnoreCase(""))) {
                throw new ApplicationException("Please Select Sections.");
            }

            if (title == null || title.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Title.");
            }
            Matcher tiMatcher = pc.matcher(title);
            if (!tiMatcher.matches()) {
                throw new ApplicationException("Please Enter Title Correctly.");
            }

            if (name == null || name.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Name.");
            }

            if (!this.name.equalsIgnoreCase("")) {
                Matcher naMatcher = pc.matcher(this.name);
                if (!naMatcher.matches()) {
                    throw new ApplicationException("Please Enter Name Correctly.");
                }
            }
            if (dob == null || dob.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Dob.");
            }

            if (pob == null || pob.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Pob.");
            }
            Matcher poMatcher = pc.matcher(pob);
            if (!poMatcher.matches()) {
                throw new ApplicationException("Please fill Pob Correctly.");
            }

            if (designation == null || designation.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Designation.");
            }

            Matcher desiMatcher = pc.matcher(designation);
            if (!desiMatcher.matches()) {
                throw new ApplicationException("Please fill Designation Correctly.");
            }
            if (address == null || address.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Address.");
            }

            if (!this.address.matches("[a-zA-Z0-9,-/ ]*")) {
                throw new ApplicationException("Please fill  Address Correctly");
            }
            if (listedNo == null || listedNo.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Listed No.");
            }

            if (!this.listedNo.matches("[a-zA-Z0-9,-/ ]*")) {
                throw new ApplicationException("Please fill  Listed No. Correctly");
            }

            if (this.goodQuality == null || this.goodQuality.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Good Quality.");
            }

            if (this.lowQuality == null || this.lowQuality.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Low Quality.");
            }

            if (this.passportNo == null || this.passportNo.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Passport No.");
            }
            if (nationality == null || nationality.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Select Nationality.");
            }

            if (nationalIdNo == null || nationalIdNo.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill National Identification No.");
            }

            if (otherInfo == null || otherInfo.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill Other Information");
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return true;
    }

    public void saveMasterDetail() {
        setMessage("");
        String result = "";
        try {
            if (validateField()) {
                result = ddsMgmtRemote.badPersonDataPost(function, sections, badPerId, title, name, dob, pob, designation, address, goodQuality, lowQuality, nationality, passportNo, nationalIdNo, listedNo, otherInfo, getUserName(), enterDate, getUserName(), updateDate);
            }
            if (function.equalsIgnoreCase("A") && result.equalsIgnoreCase("true")) {
                clear();
                setMessage("Data save Successfuly !");
            } else if (function.equalsIgnoreCase("U") && result.equalsIgnoreCase("true")) {
                clear();
                setMessage("Data Update Successfuly !");
            } else if (function.equalsIgnoreCase("D") && result.equalsIgnoreCase("true")) {
                clear();
                setMessage("Data Delete Successfuly !");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        setMessage("");
        setFunction("S");
        setSections("S");
        setBadPerId("");
        setTitle("");
        setName("");
        setDob("");
        setPob("");
        setDesignation("");
        setAddress("");
        setListedNo("");
        setGoodQuality("");
        setLowQuality("");
        setPassportNo("");
        setNationality("");
        setNationalIdNo("");
        setOtherInfo("");
        setNational("");
        this.disableNational = true;
        gridDetail = new ArrayList<BadPersonInfoPojo>();
    }

    public void clear() {
        setFunction("S");
        setSections("S");
        setBadPerId("");
        setTitle("");
        setName("");
        setDob("");
        setPob("");
        setDesignation("");
        setAddress("");
        setListedNo("");
        setGoodQuality("");
        setLowQuality("");
        setPassportNo("");
        setNationality("");
        setNationalIdNo("");
        setOtherInfo("");
        gridDetail = new ArrayList<BadPersonInfoPojo>();
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

    public String getBadPerId() {
        return badPerId;
    }

    public void setBadPerId(String badPerId) {
        this.badPerId = badPerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGoodQuality() {
        return goodQuality;
    }

    public void setGoodQuality(String goodQuality) {
        this.goodQuality = goodQuality;
    }

    public String getLowQuality() {
        return lowQuality;
    }

    public void setLowQuality(String lowQuality) {
        this.lowQuality = lowQuality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<SelectItem> getNationalityList() {
        return nationalityList;
    }

    public void setNationalityList(List<SelectItem> nationalityList) {
        this.nationalityList = nationalityList;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getNationalIdNo() {
        return nationalIdNo;
    }

    public void setNationalIdNo(String nationalIdNo) {
        this.nationalIdNo = nationalIdNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getListedNo() {
        return listedNo;
    }

    public void setListedNo(String listedNo) {
        this.listedNo = listedNo;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getGoodFlag() {
        return goodFlag;
    }

    public void setGoodFlag(String goodFlag) {
        this.goodFlag = goodFlag;
    }

    public String getLowFlag() {
        return lowFlag;
    }

    public void setLowFlag(String lowFlag) {
        this.lowFlag = lowFlag;
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

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public List<BadPersonInfoPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<BadPersonInfoPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public List<SelectItem> getSectionsList() {
        return sectionsList;
    }

    public void setSectionsList(List<SelectItem> sectionsList) {
        this.sectionsList = sectionsList;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public List<SelectItem> getNationalList() {
        return nationalList;
    }

    public void setNationalList(List<SelectItem> nationalList) {
        this.nationalList = nationalList;
    }

    public boolean isDisableNational() {
        return disableNational;
    }

    public void setDisableNational(boolean disableNational) {
        this.disableNational = disableNational;
    }

    public String getNationalName() {
        return nationalName;
    }

    public void setNationalName(String nationalName) {
        this.nationalName = nationalName;
    }
}
