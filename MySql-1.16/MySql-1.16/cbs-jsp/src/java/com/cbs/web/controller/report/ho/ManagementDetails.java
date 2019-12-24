/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.ManagementDetailsPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author sipl
 */
public class ManagementDetails extends BaseBean {

    private String titleType;
    private String message;
    String custName;
    String dsgVal;
    String desgRem;
    List<SelectItem> dsgList;
    private String genderType;
    private List<SelectItem> genderTypeOption;
    String qualVal;
    List<SelectItem> qualList;
    String add1;
    String add2;
    String pVillage;
    String pBlock;
    String city;
    List<SelectItem> cityOption;
    String state;
    List<SelectItem> stateOption;
    String country;
    List<SelectItem> countryOption;
    String postalCode;
    String phoneNo;
    String empEmail;
    private Date fromDate;
    private Date toDate;
    String custStatus;
    List<SelectItem> statusOption;
    private List<SelectItem> titleTypeOption;
    AdvancesInformationTrackingRemote aitr;
    CommonReportMethodsRemote comm;
    private boolean sflag;
    private boolean uflag;
    private List<ManagementDetailsPojo> tableList;
    private ManagementDetailsPojo currentItem = new ManagementDetailsPojo();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat d_m_y = new SimpleDateFormat("dd-MM-yyyy");
    private int gSno;
    public String custid;
    SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String directorRelative;
    private List<SelectItem> directorRelList;
    private String directorId;
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<SelectItem> getCityOption() {
        return cityOption;
    }

    public void setCityOption(List<SelectItem> cityOption) {
        this.cityOption = cityOption;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<SelectItem> getCountryOption() {
        return countryOption;
    }

    public void setCountryOption(List<SelectItem> countryOption) {
        this.countryOption = countryOption;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public List<SelectItem> getDsgList() {
        return dsgList;
    }

    public void setDsgList(List<SelectItem> dsgList) {
        this.dsgList = dsgList;
    }

    public String getDsgVal() {
        return dsgVal;
    }

    public void setDsgVal(String dsgVal) {
        this.dsgVal = dsgVal;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public List<SelectItem> getGenderTypeOption() {
        return genderTypeOption;
    }

    public void setGenderTypeOption(List<SelectItem> genderTypeOption) {
        this.genderTypeOption = genderTypeOption;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getpBlock() {
        return pBlock;
    }

    public void setpBlock(String pBlock) {
        this.pBlock = pBlock;
    }

    public String getpVillage() {
        return pVillage;
    }

    public void setpVillage(String pVillage) {
        this.pVillage = pVillage;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<SelectItem> getQualList() {
        return qualList;
    }

    public void setQualList(List<SelectItem> qualList) {
        this.qualList = qualList;
    }

    public String getQualVal() {
        return qualVal;
    }

    public void setQualVal(String qualVal) {
        this.qualVal = qualVal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<SelectItem> getStateOption() {
        return stateOption;
    }

    public void setStateOption(List<SelectItem> stateOption) {
        this.stateOption = stateOption;
    }

    public List<SelectItem> getStatusOption() {
        return statusOption;
    }

    public void setStatusOption(List<SelectItem> statusOption) {
        this.statusOption = statusOption;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isSflag() {
        return sflag;
    }

    public void setSflag(boolean sflag) {
        this.sflag = sflag;
    }

    public boolean isUflag() {
        return uflag;
    }

    public void setUflag(boolean uflag) {
        this.uflag = uflag;
    }

    public List<ManagementDetailsPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<ManagementDetailsPojo> tableList) {
        this.tableList = tableList;
    }

    public int getgSno() {
        return gSno;
    }

    public void setgSno(int gSno) {
        this.gSno = gSno;
    }

    public ManagementDetailsPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ManagementDetailsPojo currentItem) {
        this.currentItem = currentItem;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public List<SelectItem> getTitleTypeOption() {
        return titleTypeOption;
    }

    public void setTitleTypeOption(List<SelectItem> titleTypeOption) {
        this.titleTypeOption = titleTypeOption;
    }

    public String getDesgRem() {
        return desgRem;
    }

    public void setDesgRem(String desgRem) {
        this.desgRem = desgRem;
    }

    public String getCustid() {
        return custid;
    }
    public void setCustid(String custid) {
        this.custid = custid;
    }
    public String getDirectorId() {
        return directorId;
    }
    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }
    public String getDirectorRelative() {
        return directorRelative;
    }
    public void setDirectorRelative(String directorRelative) {
        this.directorRelative = directorRelative;
    }
    public List<SelectItem> getDirectorRelList() {
        return directorRelList;
    }
    public void setDirectorRelList(List<SelectItem> directorRelList) {
        this.directorRelList = directorRelList;
    }
    public ManagementDetails() {
        try {

            tableList = new ArrayList<ManagementDetailsPojo>();
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            comm = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");

            genderTypeOption = new ArrayList<SelectItem>();
            genderTypeOption.add(new SelectItem("0", "--Select--"));
            genderTypeOption.add(new SelectItem("M", "Male"));
            genderTypeOption.add(new SelectItem("F", "Female"));
            genderTypeOption.add(new SelectItem("C", "Company"));
            genderTypeOption.add(new SelectItem("O", "Others"));

            List titleTable = aitr.refRecCode("045");
            titleTypeOption = new ArrayList<SelectItem>();
            titleTypeOption.add(new SelectItem("0", ""));
            for (int i = 0; i < titleTable.size(); i++) {
                Vector element = (Vector) titleTable.get(i);
                titleTypeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            List dsgTable = aitr.refRecCode("210");
            dsgList = new ArrayList<SelectItem>();
            dsgList.add(new SelectItem("0", ""));
            for (int i = 0; i < dsgTable.size(); i++) {
                Vector element = (Vector) dsgTable.get(i);
                dsgList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            List qualTable = aitr.refRecCode("214");
            qualList = new ArrayList<SelectItem>();
            qualList.add(new SelectItem("0", ""));
            for (int i = 0; i < qualTable.size(); i++) {
                Vector element = (Vector) qualTable.get(i);
                qualList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            List cityTable = aitr.refRecCode("001");
            cityOption = new ArrayList<SelectItem>();
            cityOption.add(new SelectItem("0", ""));
            for (int i = 0; i < cityTable.size(); i++) {
                Vector element = (Vector) cityTable.get(i);
                cityOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            List stTable = aitr.refRecCode("002");
            stateOption = new ArrayList<SelectItem>();
            stateOption.add(new SelectItem("0", ""));
            for (int i = 0; i < stTable.size(); i++) {
                Vector element = (Vector) stTable.get(i);
                stateOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            List countryTable = aitr.refRecCode("003");
            countryOption = new ArrayList<SelectItem>();
            countryOption.add(new SelectItem("0", ""));
            for (int i = 0; i < countryTable.size(); i++) {
                Vector element = (Vector) countryTable.get(i);
                countryOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            statusOption = new ArrayList<SelectItem>();
            statusOption.add(new SelectItem("0", "--Select--"));
            statusOption.add(new SelectItem("A", "Active"));
            statusOption.add(new SelectItem("I", "Inactive"));

            this.setSflag(false);
            this.setUflag(true);
            List<SelectItem> directorRelList = new ArrayList<>();

            getDataToUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCustomerIdInformation() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher custIdCheck = p.matcher(this.custid);
        if (!custIdCheck.matches()) {
            this.setMessage("Please Enter Numeric Customer ID.");
            return;
        } else {
            this.setMessage("");
        }
        try {
            List custinfo = acOpenFacadeRemote.customerDetail(custid);
            if (!custinfo.isEmpty()) {
                Vector vect = (Vector) custinfo.get(0);
                String title = vect.get(0).toString();
                String custName = vect.get(25).toString();
                String add1 = vect.get(5).toString();
                String add2 = vect.get(6).toString();
                String fatherName = vect.get(2).toString();
                String mobileNos = vect.get(7).toString();
                String city = vect.get(17).toString();
                String gender = vect.get(16).toString();
                String block = vect.get(18).toString();
                String district = vect.get(19).toString();
                String email = vect.get(20).toString();
                String stateCode = vect.get(21).toString();
                String pinCode = vect.get(22).toString();
                String village = vect.get(23).toString();
                String countryCode = vect.get(24).toString();
                setTitleType(title);
                setCustName(custName);
                setGenderType(gender);
                setAdd1(add1);
                setAdd2(add2);
                setpVillage(village);
                setpBlock(block);
                setCity(city);                
                setState(stateCode);
                setCountry(countryCode);                
                setPostalCode(pinCode);
                setPhoneNo(mobileNos);
                setEmpEmail(email);
            } else {
                setMessage("Data Does not Exist For Customer ID "+custid);
                return;
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getDataToUpdate() {
        try {
            tableList = new ArrayList<ManagementDetailsPojo>();
            List resultList = new ArrayList();
            resultList = aitr.getManagementDtl("A");
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    ManagementDetailsPojo detail = new ManagementDetailsPojo();

                    detail.setName(ele.get(0) != null ? ele.get(0).toString() : "");
                    detail.setGender(ele.get(1) != null ? ele.get(1).toString() : "");
                    detail.setDesg(ele.get(2) != null ? ele.get(2).toString() : "");
                    detail.setEdu_details(ele.get(3) != null ? ele.get(3).toString() : "");
                    detail.setAddrLine1(ele.get(4) != null ? ele.get(4).toString() : "");
                    detail.setAddrLine2(ele.get(5) != null ? ele.get(5).toString() : "");
                    detail.setVillage(ele.get(6) != null ? ele.get(6).toString() : "");
                    detail.setBlock(ele.get(7) != null ? ele.get(7).toString() : "");
                    detail.setCityCode(ele.get(8) != null ? ele.get(8).toString() : "");
                    detail.setStateCode(ele.get(9) != null ? ele.get(9).toString() : "");
                    detail.setPostalCode(ele.get(10) != null ? ele.get(10).toString() : "");
                    detail.setCountryCode(ele.get(11) != null ? ele.get(11).toString() : "");
                    detail.setPhoneNumber(ele.get(12) != null ? ele.get(12).toString() : "");
                    detail.setEmailID(ele.get(13) != null ? ele.get(13).toString() : "");
                    detail.setJoinDt(ele.get(14) != null ? ele.get(14).toString() : "");
                    detail.setExpDt(ele.get(15) != null ? ele.get(15).toString() : "");
                    detail.setStatus(ele.get(16) != null ? ele.get(16).toString() : "");
                    detail.setEnterby(ele.get(17) != null ? ele.get(17).toString() : "");
                    detail.setSno(ele.get(18) != null ? Integer.parseInt(ele.get(18).toString()) : 0);
                    detail.setTitle(ele.get(19) != null ? ele.get(19).toString() : "");
                    detail.setRem_desg(ele.get(20) != null ? ele.get(20).toString() : "");
                    detail.setCustid(ele.get(21) != null ? ele.get(21).toString() : "");
                    detail.setDirRelation(ele.get(22) != null ? ele.get(22).toString() : "");
                    detail.setDirRelativeDetail(ele.get(23) != null ? ele.get(23).toString() : "");
                    detail.setDesgDetail(ele.get(25) != null ? ele.get(25).toString() : "");
                    detail.setEduDetail(ele.get(24) != null ? ele.get(24).toString() : "");

                    tableList.add(detail);
                }
            } else {
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        this.setMessage("");
        try {
            if (currentItem != null) {
                this.setTitleType(currentItem.getTitle());
                this.setCustName(currentItem.getName());
                this.setDsgVal(currentItem.getDesg());
                this.setGenderType(currentItem.getGender());
                this.setQualVal(currentItem.getEdu_details());
                this.setAdd1(currentItem.getAddrLine1());
                this.setAdd2(currentItem.getAddrLine2());
                this.setpVillage(currentItem.getVillage());
                this.setpBlock(currentItem.getBlock());
                this.setCity(currentItem.getCityCode());
                this.setState(currentItem.getStateCode());
                this.setCountry(currentItem.getCountryCode());
                this.setPostalCode(currentItem.getPostalCode());
                this.setEmpEmail(currentItem.getEmailID());
                this.setPhoneNo(currentItem.getPhoneNumber());
                this.setFromDate(df.parse(currentItem.getJoinDt()));
                this.setToDate(df.parse(currentItem.getExpDt()));
                this.setCustStatus(currentItem.getStatus());
                this.setSflag(true);
                this.setUflag(false);
                this.setgSno(currentItem.getSno());
                this.setDesgRem(currentItem.getRem_desg());
                this.setDirectorRelative(currentItem.getDirRelation());
                this.setCustid(currentItem.getCustid());                
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onblurDesgValue() {
        this.setDesgRem("");
        try {
            directorRelList = new ArrayList<>();
            List dsgTypeDesc = new ArrayList();
            if (this.getDsgVal().equalsIgnoreCase("DIRREL") || this.getDsgVal().equalsIgnoreCase("MGRREL") || this.getDsgVal().equalsIgnoreCase("SECREL")) {
                dsgTypeDesc = comm.getRefRecList("004");
            }
            if (!dsgTypeDesc.isEmpty()) {
                for (int i = 0; i < dsgTypeDesc.size(); i++) {
                    Vector vect = (Vector) dsgTypeDesc.get(i);
                    directorRelList.add(new SelectItem(vect.get(0).toString(), vect.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void saveDetail() {
        this.setMessage("");
        try {

            String chkVal = validation();

            if (!chkVal.equalsIgnoreCase("true")) {
                this.setMessage(chkVal);
                return;
            }

            String result = aitr.SaveManagementDetail(this.getTitleType(), this.getCustName(), this.getDsgVal(), this.getGenderType(), this.getQualVal(), this.getAdd1(), this.getAdd2(),
                    this.getpVillage(), this.getpBlock(), this.getCity(), this.getState(), this.getPostalCode(), this.getCountry(), this.getPhoneNo(), this.getEmpEmail(),
                    d_m_y.format(this.getFromDate()), d_m_y.format(this.getToDate()), this.getCustStatus(), this.getUserName(), this.getDesgRem(), this.getCustid(), this.getDirectorRelative(), this.getDirectorId());
            if (result.equalsIgnoreCase("true")) {
                this.setMessage("Data Saved Successfully !!!");
                this.setUflag(true);
                this.setSflag(false);
            } else if (result == null || result.equalsIgnoreCase("")) {
                this.setMessage("System error occured !!!");
                return;
            } else {
                this.setMessage("System error occured !!!");
                return;
            }
            reFresh1();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void updateDetail() {
        this.setMessage("");
        try {

            String chkVal = validation();

            if (!chkVal.equalsIgnoreCase("true")) {
                this.setMessage(chkVal);
                return;
            }

            String result = aitr.UpdateManagementDetail(this.getTitleType(), this.getCustName(), this.getDsgVal(), this.getGenderType(), this.getQualVal(), this.getAdd1(), this.getAdd2(),
                    this.getpVillage(), this.getpBlock(), this.getCity(), this.getState(), this.getPostalCode(), this.getCountry(), this.getPhoneNo(), this.getEmpEmail(),
                    d_m_y.format(this.getFromDate()), d_m_y.format(this.getToDate()), this.getCustStatus(), this.getUserName(), this.getgSno(), this.getDesgRem(), this.getCustid(), this.getDirectorRelative(), this.getDirectorId());
            if (result.equalsIgnoreCase("true")) {
                this.setMessage("Data Updated Successfully !!!");
                this.setUflag(true);
                this.setSflag(false);
            } else if (result == null || result.equalsIgnoreCase("")) {
                this.setMessage("System error occured !!!");
                return;
            } else {
                this.setMessage("System error occured !!!");
                return;
            }
            reFresh1();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String validation() {

        Pattern p = Pattern.compile("[A-Za-z0-9.]+([ '-][A-Za-z0-9.]+)*");
        Pattern p1 = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (this.getTitleType().equalsIgnoreCase("0")) {
            return "Please Select Title";
        }

        if (this.custName.equalsIgnoreCase("")) {
            return "Please fill Customer Name";
        }

        Matcher custfullNameCheck = p.matcher(this.custName);
        if (!custfullNameCheck.matches()) {
            return "Please Enter Characters in Customer Full Name.";
        }

        if (this.getDsgVal().equalsIgnoreCase("0")) {
            return "Please Select Designation";
        }

        if (this.dsgVal.equalsIgnoreCase("")) {
            return "Please Select Designation";
        }

        if (this.getGenderType().equalsIgnoreCase("0")) {
            return "Please Select Gender";
        }

        if (this.getQualVal().equalsIgnoreCase("0")) {
            return "Please Select Qualification";
        }

        if (this.getAdd1().equalsIgnoreCase("")) {
            return "Please Fill Address Line1";
        }

        if (this.getCity().equalsIgnoreCase("0")) {
            return "Please Select City/District";
        }

        if (this.getState().equalsIgnoreCase("0")) {
            return "Please Select State";
        }

        if (this.getCountry().equalsIgnoreCase("0")) {
            return "Please Select Country";
        }

        if (!this.getPostalCode().equalsIgnoreCase("")) {
            Matcher postalCodeCheck = p1.matcher(this.getPostalCode());
            if (!postalCodeCheck.matches()) {
                return "Please Enter Numeric Value in Postal Code";
            }
        }

        if (!this.phoneNo.equalsIgnoreCase("")) {
            Matcher phCodeCheck = p1.matcher(this.getPhoneNo());
            if (!phCodeCheck.matches()) {
                return "Please Enter Numeric Value in Phone No";
            }
        }

        if (!this.getEmpEmail().equalsIgnoreCase("")) {
            boolean check = new Validator().validateEmail(this.getEmpEmail());
            if (!check == true) {
                return "Please Enter Proper Email ID.";
            }
        }
        if (this.dsgVal.equalsIgnoreCase("DIRREL") || this.getDsgVal().equalsIgnoreCase("MGRREL") || this.getDsgVal().equalsIgnoreCase("SECREL")) {
            if (directorRelative == null || directorRelative.equalsIgnoreCase("")) {
                return "Please Select The Relation.";
            }
            if (directorId == null || directorId.equalsIgnoreCase("")) {
                return "Please Enter the Director's Customer Id";
            }
        }

        if (this.getCustStatus().equalsIgnoreCase("0")) {
            return "Please Select Customer Status";
        }

        if (this.getFromDate() == null) {
            return "Flease Fill Term Since";
        }

        if (this.getToDate() == null) {
            return "Flease Fill Term Upto";
        }

        if (this.getFromDate().compareTo(this.getToDate()) > 0) {
            return "Term Since is Greater than Term Upto";
        }

        return "true";
    }

    public void reFresh() {
        try {
            this.setTitleType("0");
            this.setMessage("");
            this.setCustName("");
            this.setDsgVal("0");
            this.setDesgRem("");
            this.setGenderType("0");
            this.setQualVal("0");
            this.setAdd1("");
            this.setAdd2("");
            this.setpVillage("");
            this.setpBlock("");
            this.setCity("0");
            this.setState("0");
            this.setCountry("0");
            this.setPostalCode("");
            this.setEmpEmail("");
            this.setPhoneNo("");
            this.setFromDate(dmy.parse(this.getTodayDate()));
            this.setToDate(dmy.parse(this.getTodayDate()));
            this.setCustStatus("");
            this.setSflag(false);
            this.setUflag(true);
            this.setCustid("");
            this.setDirectorId("");
            this.setDirectorRelList(new ArrayList<SelectItem>());
            getDataToUpdate();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void reFresh1() {
        try {
            this.setTitleType("0");
            this.setCustName("");
            this.setDsgVal("0");
            this.setDesgRem("");
            this.setGenderType("0");
            this.setQualVal("0");
            this.setAdd1("");
            this.setAdd2("");
            this.setpVillage("");
            this.setpBlock("");
            this.setCity("0");
            this.setState("0");
            this.setCountry("0");
            this.setPostalCode("");
            this.setEmpEmail("");
            this.setPhoneNo("");
            this.setFromDate(dmy.parse(this.getTodayDate()));
            this.setToDate(dmy.parse(this.getTodayDate()));
            this.setCustStatus("");
            this.setSflag(false);
            this.setUflag(true);
            getDataToUpdate();
            this.setCustid("");
            this.setDirectorId("");
            this.setDirectorRelList(new ArrayList<SelectItem>());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        reFresh();
        return "case1";
    }
}