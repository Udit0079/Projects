/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;


import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.FdDdsAccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.web.pojo.admin.CustomerDocument;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.utils.Util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class FinInclusionAcctOpening extends BaseBean {
    private List<SelectItem> occupationOption;
    private List<SelectItem> acctTypeOption;
    private List<SelectItem> operatingModeOption;
    private List<SelectItem> documentListOption;
    private List<SelectItem> introducerOption;
    private List<SelectItem> acctNoOption;
    private List<SelectItem> agcodeOption;
    private List<SelectItem> custTypeOption;
    private List<SelectItem> nameTitleOption;
    private List<CustomerDocument> dataList;
    private String customerType = "0";
    private String acctCode;
    private String midAccNo;
    private String lastAccNo = "01";
    private int customerId;
    private String newAcctCode;
    private String agentCode;
    private String accNo;
    private Date acctOpeningDate = new Date();
    private String nameTitle;
    private String customerName;
    private String fatherHusbandName;
    private String permtAddress;
    private String corresAddress;
    private String phone;
    private String panNo;
    private Date dob = new Date();
    private String occupation;
    private String guardianName;
    private String relationShip;
    private String operatingMode = "1";
    private String jtName1;
    private String jtName2;
    private String jtName3;
    private String jtName4;
    private String nominee;
    private String nomineeRelationShip;
    private String document;
    private String documentDetails;
    private String intrducerAcctCode;
    private String intrducerMidAcNo;
    private String introducerLastAcNo = "01";
    private String intrducerName;
    private String intrducerAcNo;
    private String intrducerOpMode;
    private String instAmount;
    private String months;
    private String roi;
    private int currentRow;
    private String custImgUri;
    private String addProofImgUri;
    private String idProofImgUri;
    private String message;
    private boolean roiCheck;
    private boolean amountCheck;
    private boolean saveDisable;
    private boolean flagDisable;
    private CustomerDocument currentItem = new CustomerDocument();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    CommonReportMethodsRemote reportMethodsRemote;
    FdDdsAccountOpeningFacadeRemote openingFacadeRemote;
    FtsPostingMgmtFacadeRemote fts;

    public boolean isFlagDisable() {
        return flagDisable;
    }

    public void setFlagDisable(boolean flagDisable) {
        this.flagDisable = flagDisable;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isAmountCheck() {
        return amountCheck;
    }

    public void setAmountCheck(boolean amountCheck) {
        this.amountCheck = amountCheck;
    }

    public boolean isRoiCheck() {
        return roiCheck;
    }

    public void setRoiCheck(boolean roiCheck) {
        this.roiCheck = roiCheck;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddProofImgUri() {
        return addProofImgUri;
    }

    public void setAddProofImgUri(String addProofImgUri) {
        this.addProofImgUri = addProofImgUri;
    }

    public String getCustImgUri() {
        return custImgUri;
    }

    public void setCustImgUri(String custImgUri) {
        this.custImgUri = custImgUri;
    }

    public String getIdProofImgUri() {
        return idProofImgUri;
    }

    public void setIdProofImgUri(String idProofIngUri) {
        this.idProofImgUri = idProofIngUri;
    }

    public CustomerDocument getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CustomerDocument currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<CustomerDocument> getDataList() {
        return dataList;
    }

    public List<SelectItem> getAcctNoOption() {
        return acctNoOption;
    }

    public void setAcctNoOption(List<SelectItem> acctNoOption) {
        this.acctNoOption = acctNoOption;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public List<SelectItem> getAgcodeOption() {
        return agcodeOption;
    }

    public void setAgcodeOption(List<SelectItem> agcodeOption) {
        this.agcodeOption = agcodeOption;
    }

    public List<SelectItem> getDocumentListOption() {
        return documentListOption;
    }

    public void setDocumentListOption(List<SelectItem> documentListOption) {
        this.documentListOption = documentListOption;
    }

    public List<SelectItem> getIntroducerOption() {
        return introducerOption;
    }

    public void setIntroducerOption(List<SelectItem> introducerOption) {
        this.introducerOption = introducerOption;
    }

    public List<SelectItem> getOccupationOption() {
        return occupationOption;
    }

    public void setOccupationOption(List<SelectItem> occupationOption) {
        this.occupationOption = occupationOption;
    }

    public List<SelectItem> getOperatingModeOption() {
        return operatingModeOption;
    }

    public void setOperatingModeOption(List<SelectItem> operatingModeOption) {
        this.operatingModeOption = operatingModeOption;
    }

    public List<SelectItem> getCustTypeOption() {
        return custTypeOption;
    }

    public void setCustTypeOption(List<SelectItem> custTypeOption) {
        this.custTypeOption = custTypeOption;
    }

    public List<SelectItem> getNameTitleOption() {
        return nameTitleOption;
    }

    public void setNameTitleOption(List<SelectItem> nameTitleOption) {
        this.nameTitleOption = nameTitleOption;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public Date getAcctOpeningDate() {
        return acctOpeningDate;
    }

    public void setAcctOpeningDate(Date acctOpeningDate) {
        this.acctOpeningDate = acctOpeningDate;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getCorresAddress() {
        return corresAddress;
    }

    public void setCorresAddress(String corresAddress) {
        this.corresAddress = corresAddress;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentDetails() {
        return documentDetails;
    }

    public void setDocumentDetails(String documentDetails) {
        this.documentDetails = documentDetails;
    }

    public String getFatherHusbandName() {
        return fatherHusbandName;
    }

    public void setFatherHusbandName(String fatherHusbandName) {
        this.fatherHusbandName = fatherHusbandName;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getInstAmount() {
        return instAmount;
    }

    public void setInstAmount(String instAmount) {
        this.instAmount = instAmount;
    }

    public String getIntrducerAcNo() {
        return intrducerAcNo;
    }

    public void setIntrducerAcNo(String intrducerAcNo) {
        this.intrducerAcNo = intrducerAcNo;
    }

    public String getIntrducerAcctCode() {
        return intrducerAcctCode;
    }

    public void setIntrducerAcctCode(String intrducerAcctCode) {
        this.intrducerAcctCode = intrducerAcctCode;
    }

    public String getIntrducerMidAcNo() {
        return intrducerMidAcNo;
    }

    public void setIntrducerMidAcNo(String intrducerMidAcNo) {
        this.intrducerMidAcNo = intrducerMidAcNo;
    }

    public String getIntrducerName() {
        return intrducerName;
    }

    public void setIntrducerName(String intrducerName) {
        this.intrducerName = intrducerName;
    }

    public String getIntrducerOpMode() {
        return intrducerOpMode;
    }

    public void setIntrducerOpMode(String intrducerOpMode) {
        this.intrducerOpMode = intrducerOpMode;
    }

    public String getIntroducerLastAcNo() {
        return introducerLastAcNo;
    }

    public void setIntroducerLastAcNo(String introducerLastAcNo) {
        this.introducerLastAcNo = introducerLastAcNo;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getLastAccNo() {
        return lastAccNo;
    }

    public void setLastAccNo(String lastAccNo) {
        this.lastAccNo = lastAccNo;
    }

    public String getMidAccNo() {
        return midAccNo;
    }

    public void setMidAccNo(String midAccNo) {
        this.midAccNo = midAccNo;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getNewAcctCode() {
        return newAcctCode;
    }

    public void setNewAcctCode(String newAcctCode) {
        this.newAcctCode = newAcctCode;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getNomineeRelationShip() {
        return nomineeRelationShip;
    }

    public void setNomineeRelationShip(String nomineeRelationShip) {
        this.nomineeRelationShip = nomineeRelationShip;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(String operatingMode) {
        this.operatingMode = operatingMode;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPermtAddress() {
        return permtAddress;
    }

    public void setPermtAddress(String permtAddress) {
        this.permtAddress = permtAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public FinInclusionAcctOpening() {
        dataList = new ArrayList<CustomerDocument>();
        try {
            reportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            openingFacadeRemote=(FdDdsAccountOpeningFacadeRemote)ServiceLocator.getInstance().lookup("FdDdsAccountOpeningFacade");
            fts=(FtsPostingMgmtFacadeRemote)ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            getDropDownDetails();
            setSaveDisable(true);
            setFlagDisable(true);
            //dataList = Util.getCustomers();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }

    public void getDropDownDetails() {
        try {

            occupationOption = new ArrayList<SelectItem>();
            acctTypeOption = new ArrayList<SelectItem>();
            operatingModeOption = new ArrayList<SelectItem>();
            documentListOption = new ArrayList<SelectItem>();
            introducerOption = new ArrayList<SelectItem>();
            acctNoOption = new ArrayList<SelectItem>();
            agcodeOption = new ArrayList<SelectItem>();
            custTypeOption = new ArrayList<SelectItem>();
            nameTitleOption = new ArrayList<SelectItem>();

            nameTitleOption.add(new SelectItem("--", "--SELECT--"));
            nameTitleOption.add(new SelectItem("MR.", "MR."));
            nameTitleOption.add(new SelectItem("MRS.", "MRS."));
            nameTitleOption.add(new SelectItem("SMT.", "SMT."));
            nameTitleOption.add(new SelectItem("MISS", "MISS"));
            nameTitleOption.add(new SelectItem("KUMARI", "KUMARI"));
            nameTitleOption.add(new SelectItem("MASTER", "MASTER"));
            nameTitleOption.add(new SelectItem("M/S", "M/S"));
            List resultList = new ArrayList();
            resultList = reportMethodsRemote.getDocumentDetails();
            if (resultList.size() < 0) {
                documentListOption = new ArrayList<SelectItem>();
                documentListOption.add(new SelectItem("0", " "));
                return;
            } else {
                documentListOption = new ArrayList<SelectItem>();
                documentListOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    documentListOption.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            List accountTyperesultList = openingFacadeRemote.getAcctType();
            if (accountTyperesultList.size() < 0) {
                acctTypeOption = new ArrayList<SelectItem>();
                acctTypeOption.add(new SelectItem("0", " "));
                return;
            } else {
                acctTypeOption = new ArrayList<SelectItem>();
                acctTypeOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < accountTyperesultList.size(); i++) {
                    Vector ele = (Vector) accountTyperesultList.get(i);
                    acctTypeOption.add(new SelectItem(ele.get(0).toString()));
                }
            }

            List operatingModeresultList = reportMethodsRemote.getOperatingModeDetails();
            if (operatingModeresultList.size() < 0) {
                operatingModeOption = new ArrayList<SelectItem>();
                operatingModeOption.add(new SelectItem("0", ""));
                return;
            } else {
                operatingModeOption = new ArrayList<SelectItem>();
                operatingModeOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < operatingModeresultList.size(); i++) {
                    Vector ele = (Vector) operatingModeresultList.get(i);
                    operatingModeOption.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            List documentresultList = reportMethodsRemote.getOcupationDetails();
            if (documentresultList.size() < 0) {
                occupationOption = new ArrayList<SelectItem>();
                occupationOption.add(new SelectItem("0", " "));
                return;
            } else {
                occupationOption = new ArrayList<SelectItem>();
                occupationOption.add(new SelectItem("0", "--Select--"));

                for (int i = 0; i < documentresultList.size(); i++) {
                    Vector ele = (Vector) documentresultList.get(i);
                    occupationOption.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            List introresultList1 = openingFacadeRemote.getAgCode(this.getOrgnBrCode());
            if (introresultList1.size() < 0) {
                agcodeOption = new ArrayList<SelectItem>();
                agcodeOption.add(new SelectItem("0", " "));
                return;
            } else {
                agcodeOption = new ArrayList<SelectItem>();
                agcodeOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < introresultList1.size(); i++) {
                    Vector ele = (Vector) introresultList1.get(i);
                    agcodeOption.add(new SelectItem(ele.get(0).toString()));
                }
            }

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String custId = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("custId"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (CustomerDocument item : dataList) {
                if (item.getCustId().equals(custId)) {
                    currentItem = item;
                    break;
                }
            }

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setRowValues() {
        try {
            saveDisable=false;
            resetForm();
            DateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
//            this.setCustImgUri("http://" + Util.HOST + ":" + Util.PORT + "/" + Util.DATABASE_NAME + "/" + currentItem.getId() + "/" + currentItem.getCustomerImgName());
  //          this.setAddProofImgUri("http://" + Util.HOST + ":" + Util.PORT + "/" + Util.DATABASE_NAME + "/" + currentItem.getId() + "/" + currentItem.getAddProofImgName());
 //           this.setIdProofImgUri("http://" + Util.HOST + ":" + Util.PORT + "/" + Util.DATABASE_NAME + "/" + currentItem.getId() + "/" + currentItem.getIdProofImgName());

            this.setCustomerName(currentItem.getName());
            this.setDob(adf.parse(currentItem.getDob()));
            this.setCorresAddress(currentItem.getLocation());
            this.setPermtAddress(currentItem.getLocation());

            if (currentItem.getGender().equalsIgnoreCase("M")) {
                this.setNameTitle("MR.");
            } else {
                this.setNameTitle("MISS");
            }
            this.setCustomerType("1");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }

    public void getRateOfInterest() {
        setMessage("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher amt = p.matcher(instAmount);
        Matcher daysval = p.matcher(months);
        try {
            roiCheck = true;
            amountCheck = true;
            if (!amt.matches()) {
                setMessage("Please fill the Inst. Amount And it Should Be Numeric ");
                amountCheck = false;
                return;
            } else if (months.equalsIgnoreCase("") || !daysval.matches()) {
                setMessage("Please fill the Peroid(Months) And it Should Be Numeric");
                roiCheck = false;
                return;
            } else {
                List resultList;
                int days = Integer.parseInt(months) * 30;
                months = Integer.toString(days);
                resultList = openingFacadeRemote.getROIForFinInclusion(Integer.parseInt(months), Float.parseFloat(instAmount), getTodayDate());
                if (resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        setRoi(ele.get(0).toString());

                    }
                }

            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    private String getDobStatus(Date dobdt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dtStr = sdf.format(dobdt);

        int dayDOB = Integer.parseInt(dtStr.substring(0, 2));
        int monthDOB = Integer.parseInt(dtStr.substring(3, 5));
        int yearDOB = Integer.parseInt(dtStr.substring(6));

        Calendar cd = Calendar.getInstance();
        int thisYear = cd.get(Calendar.YEAR);
        int thisMonth = cd.get(Calendar.MONTH);
        int thisDay = cd.get(Calendar.DAY_OF_MONTH);
        int age = thisYear - yearDOB;
        if (thisMonth < monthDOB) {
            age = age - 1;
        }
        if (thisMonth == monthDOB && thisDay < dayDOB) {
            age = age - 1;
        }

        String dobStatus = "";
        if (age >= 18) {
            dobStatus = "MJ";
        } else if (age < 18) {
            dobStatus = "MN";
        }
        return dobStatus;
    }

    public void saveActionDS() throws ParseException {
        if (valiadtions().equalsIgnoreCase("false")) {
            return;
        }

        String statusNom = null;
        String nomAge = "";
        try {
            if (nominee.equalsIgnoreCase("") || nominee == null || nominee.equalsIgnoreCase("")) {
                setNominee("");
            }

            String cust = "";
            String nomineeAdd = "";
            String nomineeDob = "";
            String Jt1CustomerId = "";
            String Jt2CustomerId = "";
            String Jt3CustomerId = "";
            String Jt4CustomerId = "";
            String resultList;
            resultList = openingFacadeRemote.saveAccountOpenDDS(cust, this.newAcctCode, this.nameTitle, this.customerName, this.corresAddress, this.permtAddress, this.phone,
                    ymd.format(this.dob), Integer.parseInt(this.occupation), Integer.parseInt(this.operatingMode), this.getDobStatus(this.dob), this.panNo,
                    this.guardianName, this.relationShip, this.agentCode, ymd.format(sdf.parse(getTodayDate())), getUserName(), this.fatherHusbandName, this.intrducerAcNo, this.jtName1,
                    this.jtName2, this.jtName3, this.jtName4, this.getOrgnBrCode(), nominee, this.nomineeRelationShip, Integer.parseInt(this.document), this.documentDetails, Integer.parseInt(String.valueOf(this.months)), 
                    Float.parseFloat(String.valueOf(this.instAmount)), Float.parseFloat(String.valueOf(this.roi)),
                    nominee, nomineeAdd, this.nomineeRelationShip, statusNom, nomineeDob, nomAge, Jt1CustomerId, Jt2CustomerId, Jt3CustomerId, Jt4CustomerId);
            if (resultList.substring(0, 4).equalsIgnoreCase("true")) {
                String acno = resultList.substring(4, 16);
                String acid = resultList.substring(16);
                currentItem.setAccountId(acno);
                currentItem.setEnrollment("TRUE");
                currentItem.setEnrollmentDate(sdf.format(new Date()));
              //  Util.updateDocument(currentItem);
                resetForm();
                setMessage("Data saved successfully. Generated Customer Account No is --> " + resultList.substring(4, 16) + " And Customer Id is --> " + resultList.substring(16));
              //  dataList = Util.getCustomers();
            } else {
                setMessage("Data is not saved.");
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void saveActionSB() throws ParseException {
        if (valiadtions().equalsIgnoreCase("false")) {
            return;
        }

        String cust_type = "";
        String nomineeAdd = "";
        String nomineeDob = "";
        String Jt1CustomerId = "";
        String Jt2CustomerId = "";
        String Jt3CustomerId = "";
        String Jt4CustomerId = "";

        try {
            
            if (!fts.getAcNatureByCode(newAcctCode).equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                months = "0";
                instAmount = "0";
                roi = "0";
            }
           
            String save = openingFacadeRemote.saveAccountOpenSbRd(cust_type, this.newAcctCode, this.nameTitle, this.customerName, this.corresAddress, this.permtAddress, this.phone,
                    ymd.format(this.dob), Integer.parseInt(this.occupation), Integer.parseInt(this.operatingMode), this.panNo,
                    this.guardianName, this.relationShip, this.agentCode, ymd.format(sdf.parse(getTodayDate())), getUserName(), this.fatherHusbandName, this.intrducerAcNo, this.jtName1,
                    this.jtName2, this.getOrgnBrCode(), nominee, this.nomineeRelationShip, this.jtName3, this.jtName4, Integer.parseInt(String.valueOf(this.months)), Float.parseFloat(String.valueOf(this.instAmount)), Float.parseFloat(String.valueOf(this.roi)),
                    Integer.parseInt(this.document), this.documentDetails, nomineeAdd,
                    nomineeDob, Jt1CustomerId, Jt2CustomerId, Jt3CustomerId, Jt4CustomerId);
            if (save.substring(0, 4).equalsIgnoreCase("true")) {
                String acno = save.substring(4, 16);
                String acid = save.substring(16);
                currentItem.setAccountId(acno);
                currentItem.setEnrollment("TRUE");
                currentItem.setEnrollmentDate(sdf.format(new Date()));
               // Util.updateDocument(currentItem);
                resetForm();
                setMessage("DATA SAVED SUCCESSFULLY. Generated Customer Account No is --> " + save.substring(4, 16) + " And Customer Id is --> " + save.substring(16));
               // dataList = Util.getCustomers();
            } else {
                setMessage("DATA IS NOT SAVED");
            }

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    private void resetForm() {
        this.setCustomerType("");
        this.setAcctCode("--SELECT--");
        this.setMidAccNo("");

        this.setLastAccNo("01");
        this.setCustomerId(0);
        this.setNewAcctCode("--SELECT--");

        this.setAgentCode("--SELECT--");
        this.setAccNo("");
        this.setAcctOpeningDate(new Date());

        this.setNameTitle("--");
        this.setCustomerName("");
        this.setFatherHusbandName("");

        this.setPermtAddress("");
        this.setCorresAddress("");
        this.setPhone("");

        this.setPanNo("");
        this.setDob(new Date());
        this.setOccupation("0");

        this.setGuardianName("");
        this.setRelationShip("");
        this.setOperatingMode("0");

        this.setJtName1("");
        this.setJtName2("");
        this.setJtName3("");
        this.setJtName4("");

        this.setNominee("");
        this.setNomineeRelationShip("");
        this.setDocument("0");
        this.setDocumentDetails("");

        this.setIntrducerAcctCode("-SELECT-");
        this.setIntrducerMidAcNo("");
        this.setIntroducerLastAcNo("01");

        this.setIntrducerName("");
        this.setIntrducerAcNo("");
        this.setIntrducerOpMode("");

        this.setInstAmount("0");
        this.setMonths("0");
        this.setRoi("0");
        this.setMessage("");
        this.setCustImgUri("");
        this.setIdProofImgUri("");
        this.setAddProofImgUri("");
    }

    public void cancelAccDetail() {
        resetForm();
        try {
           // dataList = Util.getCustomers();
        } catch(Exception e)
        {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String close() {
        this.setMessage("");
        resetForm();
        return "case1";
    }

    public void changeValue() {
        CustomerDocument item = dataList.get(currentRow);
        if (item.getName().equalsIgnoreCase("Rohit")) {
            item.setName("Mohit");
            dataList.remove(currentRow);
            dataList.add(currentRow, item);
        } else if (item.getName().equalsIgnoreCase("Mohit")) {
            item.setName("Rohit");
            dataList.remove(currentRow);
            dataList.add(currentRow, item);
        }
    }

    public void changeLocationValue() {
        CustomerDocument item = dataList.get(currentRow);
        if (item.getLocation().equalsIgnoreCase("Delhi")) {
            item.setLocation("New Delhi");
            dataList.remove(currentRow);
            dataList.add(currentRow, item);
        } else if (item.getLocation().equalsIgnoreCase("New Delhi")) {
            item.setLocation("Delhi");
            dataList.remove(currentRow);
            dataList.add(currentRow, item);
        }
    }

    public void saveAccDetail() throws ParseException {
        if (this.newAcctCode.equalsIgnoreCase(CbsAcCodeConstant.DS_AC.getAcctCode())) {
            saveActionDS();
        } else {
            saveActionSB();
        }

    }

    public String valiadtions() throws ParseException {

        if (newAcctCode == null || newAcctCode.equalsIgnoreCase("0")) {
            setMessage("Please Select New A/C Type.");
            return "false";
        }

        if (agentCode == null || agentCode.equalsIgnoreCase("0")) {
            setMessage("Please Select the Agent Code.");
            return "false";
        }

        if (nameTitle == null || nameTitle.equalsIgnoreCase("--")) {
            setMessage("Please Select the Name.");
            return "false";
        }
        if (customerName == null || customerName.equalsIgnoreCase("")) {
            setMessage("Please Enter the Name.");
            return "false";
        }
        if (fatherHusbandName == null || fatherHusbandName.equalsIgnoreCase("")) {
            setMessage("Please Enter Father/Husband's Name.");
            return "false";
        }
        //} else {
//                if (!fatherHusbandName.matches("[a-zA-Z ]*")) {
//                    setMessage("Father/Husband's should be alpha numeric.");
//                    return "false";
//                }
//
//        }

        if (permtAddress == null || permtAddress.equalsIgnoreCase("")) {
            setMessage("Please Enter Permanent Address.");
            return "false";
        }
        if (corresAddress == null || corresAddress.equalsIgnoreCase("")) {
            setMessage("Please Enter Correspondence Address.");
            return "false";
        }

        if (dob == null) {
            setMessage("Please Enter Date Of Birth.");
            return "false";
        } else {
            if (this.dob.after(sdf.parse(getTodayDate()))) {
                setMessage("'Date Of Birth' Can't be greater than Current Date");
                return "false";
            }
        }


        if (document.equalsIgnoreCase("0")) {
            this.setMessage("Please Select The  Document");
            return "false";
        }
        if (!document.equalsIgnoreCase("0") && ((documentDetails == null) || (documentDetails.equalsIgnoreCase("")))) {
            this.setMessage("Please Enter the Document Details");
            return "false";
        }

        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher amt = p.matcher(instAmount);
        Matcher rate = p.matcher(roi);
        Matcher month = p.matcher(months);

        if (instAmount.equalsIgnoreCase("") || !amt.matches()) {
            setMessage("Please fill the Inst. Amount  And it Should Be Numeric ");
            return "false";
        }
        if (months.equalsIgnoreCase("") || !month.matches()) {
            setMessage("Please fill the Peroid(Months) And it Should Be Numeric");
            return "false";
        }
        if (roi.equalsIgnoreCase("") || !rate.matches()) {
            setMessage("Please fill the ROI And it Should Be Numeric");
            return "false";
        }
        return "true";
    }
}
