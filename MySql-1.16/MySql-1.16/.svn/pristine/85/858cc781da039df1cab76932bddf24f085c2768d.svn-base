/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.CbsCustKycDetailsTo;
import com.cbs.dto.customer.CBSCustMISInfoHisTO;
import com.cbs.dto.customer.CBSCustMISInfoTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailHisTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.master.ParameterinfoReportTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class MisInfo {

    CustomerDetail customerDetail;
    CustPersonalDetails custPersonalDetails;
    MinorInfo minorInfoObj;
    String misType;
    List<SelectItem> misTypeList;
    String groupId;
    List<SelectItem> groupIdList;
    String displayOfficePanal;
//    String status;
    Date statusDate;
    String misOccupation;
    String misConstitution;
    String misCast;
    String misCommunity;
    String healthCode;
    List<SelectItem> misOccupationOption;
    List<SelectItem> misConstitutionOption;
    List<SelectItem> misCastOption;
    List<SelectItem> healthCodeOption;
    List<SelectItem> misCommunityOption;
    String businessSegment;
    String salesTurnOver;
    CustomerMasterDelegate masterDelegate;
    private Date incopDt;
    private String incopPlace;
    private List<SelectItem> incopPlaceList;
    private Date dcbDt;
    private String misIsoResidence;
    private List<SelectItem> misIsoResidenceList;
    private String misJuriTin;
    private String misPlaceCity;
    private String misBirth;
    private List<SelectItem> misBirthList;
    private String opRiskRating;
    private List<SelectItem> opRiskRatingOption;
    private Date riskAsOn;
    private Date custAquiDate;
    private String threshOldTransLimit;
    private Date thresholdLimitUpdDate;
    private Date custUpdationDate;
    private boolean misIndDisable;
    private boolean misCompDisable;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    Pattern onlyAlphabetWitoutSpace = Pattern.compile("[A-Za-z]*");
    Pattern onlyAlphabetWitSpace = Pattern.compile("[A-Za-z ]*");
    Pattern alphaNumericWithoutSpace = Pattern.compile("[A-Za-z0-9]*");
    //Added on 28/10/2015
    private String misOccupationChgFlag;
    private String incopDtChgFlag;
    private String incopPlaceChgFlag;
    private String dcbDtChgFlag;
    private String misConstitutionChgFlag;
    private String misTypeChgFlag;
    private String misGroupIdChgFlag;
    private String misIsoResidenceChgFlag;
    private String misJuriTinChgFlag;
    private String misPlaceCityChgFlag;
    private String misBirthChgFlag;
    private String opRiskRatingChgFlag;
    private String riskAsOnChgFlag;
    private String custAquiDateChgFlag;
    private String threshOldTransLimitChgFlag;
    private String thresholdLimitUpdDateChgFlag;
    private String custUpdationDateChgFlag;
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    private CommonReportMethodsRemote common;
    AdvancesInformationTrackingRemote aitr;
    private String typeOfDocSubmit;
    private String typeOfDocSubmitChgFlag;
    private List<SelectItem> typeOfDocSubmitList;
    private List<SelectItem> stateOfIncorpList;
    private List<SelectItem> countryOfIncorpList;
    private List<SelectItem> countryOfResiForTaxList;
    private String stateOfIncorp;
    private String countryOfIncorp;
    private String countryOfResiForTax;
    private String stateOfIncorpChgFlag;
    private String countryOfIncorpChgFlag;
    private String countryOfResiForTaxChgFlag;
    private String displayOnIncrpCntryNotIN = "none";
    private String displayOnIncrpCntryIN = "";
    private String incopPlaceNotIN;
    private String stateOfIncorpNotIN;
    private String[] custImageCapture;
    private Map<String, String> custImageCaptureDesc;
    private List<SelectItem> custImageCaptureList;
    private String custImageCaptureChgFlag;
    private String custImageSelected;
    private String custImageCode;
    private String custImageSelectedChgFlag;

    public MisInfo() {
        initializeForm();
    }

    public void initializeForm() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");

            misOccupationOption = new ArrayList<SelectItem>();
            List<CbsRefRecTypeTO> functionList;

            incopPlaceList = new ArrayList<SelectItem>();

            misConstitutionOption = new ArrayList<SelectItem>();
            misIsoResidenceList = new ArrayList<SelectItem>();
            countryOfIncorpList = new ArrayList<SelectItem>();
            countryOfResiForTaxList = new ArrayList<SelectItem>();
            countryOfIncorpList.add(new SelectItem("0", "--Select--"));
            countryOfResiForTaxList.add(new SelectItem("0", "--Select--"));
            misIsoResidenceList.add(new SelectItem("0", "--Select--"));
            misBirthList = new ArrayList<SelectItem>();
            misBirthList.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("302");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                misIsoResidenceList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                countryOfIncorpList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                countryOfResiForTaxList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                misBirthList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            if (!functionList.isEmpty()) {
                this.setMisIsoResidence("IN");
                this.setCountryOfIncorp("IN");
                this.setCountryOfResiForTax("IN");
            }
            opRiskRatingOption = new ArrayList<SelectItem>();
            opRiskRatingOption.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("024");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                opRiskRatingOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            if (common.dmdAmtFlag().equalsIgnoreCase("Y")) {
                this.displayOfficePanal = "";
            } else {
                this.displayOfficePanal = "none";
            }
            List mangList = common.getAccountManager();
            misTypeList = new ArrayList<SelectItem>();
            if (!mangList.isEmpty()) {
                misTypeList.add(new SelectItem("S", "--Select--"));
                for (int i = 0; i < mangList.size(); i++) {
                    Vector ele = (Vector) mangList.get(i);
                    misTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            functionList = masterDelegate.getFunctionValues("357");
            typeOfDocSubmitList = new ArrayList<SelectItem>();
            typeOfDocSubmitList.add(new SelectItem("0", "--Select--"));
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                typeOfDocSubmitList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            functionList = masterDelegate.getFunctionValues("002");
            stateOfIncorpList = new ArrayList<SelectItem>();
            stateOfIncorpList.add(new SelectItem("0", "--Select--"));
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                stateOfIncorpList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public List<String> getPlaceofIncorpList(Object arg) {
        String prefix = (String) arg;
        ArrayList<String> result = new ArrayList<String>();
        try {
            List optionlist = masterDelegate.getDistrictListLike(this.stateOfIncorp, prefix, "S");
            if (!(prefix == null) || (prefix.length() == 0)) {
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    result.add(ele6.get(0).toString());
                }
            }
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public void onChangeIncorpState() {
        this.incopPlace = "";
    }

    public void onChangeIncorpCountry() {
        if (countryOfIncorp.equalsIgnoreCase("IN")) {
            displayOnIncrpCntryNotIN = "none";
            displayOnIncrpCntryIN = "";
        } else {
            displayOnIncrpCntryNotIN = "";
            displayOnIncrpCntryIN = "none";
        }
    }

    public void onChangeCustImageCapture() {
        this.custImageSelected = "";
        this.custImageCode = "";
        String image = "";
        String imageCode = "";
        if (custImageCapture.length == 1) {
            image = "*" + custImageCaptureDesc.get(custImageCapture[0]);
            imageCode = custImageCapture[0];
        } else {
            for (int i = 0; i < custImageCapture.length; i++) {
                if (i == 0) {
                    image = image.concat("*" + custImageCaptureDesc.get(custImageCapture[0]));
                    imageCode = imageCode.concat(custImageCapture[0]);

                } else {
                    image = image.concat("<br/>" + "*" + custImageCaptureDesc.get(custImageCapture[i]));
                    imageCode = imageCode.concat("|" + custImageCapture[i]);
                }
            }
        }
        this.custImageCode = imageCode;
        this.custImageSelected = image;
    }

    public void getGroupIDdata() {
        getCustomerDetail().setErrorMsg("");
        try {
            List groupList = new ArrayList();
            groupList = common.getGroupId(misType);
            groupIdList = new ArrayList<SelectItem>();
            groupIdList.add(new SelectItem("S", "--Select--"));
            if (!groupList.isEmpty()) {
                for (int i = 0; i < groupList.size(); i++) {
                    Vector eleId = (Vector) groupList.get(i);
                    groupIdList.add(new SelectItem(eleId.get(0).toString(), eleId.get(1).toString()));
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

//    public String StatusOn() {
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        Matcher statusCheck = p.matcher(this.status);
//        if (statusCheck.matches()) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Status.");
//            return "Please Enter Non Numeric Value in Status.";
//        } else if (this.status.contains("0") || this.status.contains("1") || this.status.contains("2") || this.status.contains("3") || this.status.contains("4") || this.status.contains("5")
//                || this.status.contains("6") || this.status.contains("7") || this.status.contains("8") || this.status.contains("9")) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Status.");
//            return "Please Enter Non Numeric Value in Status.";
//        } else {
//            getCustomerDetail().setErrorMsg("");
//            return "true";
//        }
//    }
//    public boolean onblurSalesTurnOver() {
//        if (!this.salesTurnOver.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher salesTurnOverCheck = p.matcher(this.salesTurnOver);
//            if (!salesTurnOverCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Sales Turnover.");
//                return false;
//            } else if (this.salesTurnOver.contains(".")) {
//                if (this.salesTurnOver.substring(this.salesTurnOver.indexOf(".") + 1).length() > 2) {
//                    getCustomerDetail().setErrorMsg("Please Fill The Sales Turnover Upto Two Decimal Places.");
//                    return false;
//                } else {
//                    getCustomerDetail().setErrorMsg("");
//                    return true;
//                }
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return true;
//            }
//        }
//        return true;
//    }
//    
//    public String onchangeStatusAsOn() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Date date = new Date();
//        String msg1 = "";
//        Calendar cal1 = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//        cal1.set(Integer.parseInt(sdf.format(statusDate).toString().substring(0, 4)), Integer.parseInt(sdf.format(statusDate).toString().substring(4, 6)), Integer.parseInt(sdf.format(statusDate).toString().substring(6)));
//        cal2.set(Integer.parseInt(sdf.format(date).toString().substring(0, 4)), Integer.parseInt(sdf.format(date).toString().substring(4, 6)), Integer.parseInt(sdf.format(date).toString().substring(6)));
//        if (cal1.after(cal2)) {
//            getCustomerDetail().setErrorMsg("You can't select the Status As On after the Current Date.");
//            return msg1 = "You can't select the Status As On after the Current Date.";
//        } else {
//            getCustomerDetail().setErrorMsg("");
//            msg1 = "true";
//        }
//        return msg1;
//    }
    public String misInfoValidation() {
        getCustomerDetail().setErrorMsg("");
        try {
//            Date date = new Date();
            //Old Occupation Validation
//            if (!(misOccupation == null || misOccupation.equals("0"))) {
//                if (misOccupation.equalsIgnoreCase("N")) {
//                    if (!getCustPersonalDetails().getTitleType().equalsIgnoreCase("M/S")) {
//                        ParameterinfoReportTO to = masterDelegate.getAgeLimit("MINOR-AGE-LIMIT");
//                        int minorAgeLimit = to.getCode();
//                        if (getCustPersonalDetails().getPerDateOfBirth() == null || getCustPersonalDetails().getPerDateOfBirth().equals("")) {
//                            return "Please Select Date of birth in Personal Information";
//                        }
//
//                        int strDateDiff = CbsUtil.yearDiff(getCustPersonalDetails().getPerDateOfBirth(), date);
//                        if (getCustPersonalDetails().getMinorType().equalsIgnoreCase("Y")) {
//                            if (strDateDiff <= 0) {
//                                return "Please select proper minor date of birth in Minor Information.";
//                            }
//                            if (strDateDiff > 0) {
//                                if (strDateDiff >= minorAgeLimit) {
//                                    return "Minor can't be equal to or greater than " + minorAgeLimit + " years.";
//                                }
//                            }
//                        } else if (getCustPersonalDetails().getMinorType().equalsIgnoreCase("N")) {
//                            if (strDateDiff < minorAgeLimit) {
//                                return "The Person Who is not Minor can't be less than " + minorAgeLimit + " years.";
//                            }
//                        }
//                    }
//                } else if (misOccupation.equalsIgnoreCase("S")) {
//                    if (!getCustPersonalDetails().getTitleType().equalsIgnoreCase("M/S")) {
//                        ParameterinfoReportTO to = masterDelegate.getAgeLimit("STUDENT-AGE-LIMIT");
//                        int studentAgeLimit = to.getCode();
//                        if (getCustPersonalDetails().getPerDateOfBirth() == null || getCustPersonalDetails().getPerDateOfBirth().equals("")) {
//                            return "Please Select Date of birth in Personal Information";
//                        }
//
//                        int strDateDiff = CbsUtil.yearDiff(getCustPersonalDetails().getPerDateOfBirth(), date);
//                        if (getCustPersonalDetails().getMinorType().equalsIgnoreCase("Y")) {
//                            if (strDateDiff <= 0) {
//                                return "Please select proper minor date of birth in Minor Information.";
//                            }
//                            if (strDateDiff > 0) {
//                                if (strDateDiff >= studentAgeLimit) {
//                                    return "Minor can't be equal to or greater than " + studentAgeLimit + " years.";
//                                }
//                            }
//                        } else if (getCustPersonalDetails().getMinorType().equalsIgnoreCase("N")) {
//                            if (strDateDiff < studentAgeLimit) {
//                                return "The Person Who is not Minor can't be less than " + studentAgeLimit + " years.";
//                            }
//                        }
//                    }
//                }
//            }

            if (this.misConstitution == null || this.misConstitution.equals("0")) {
                getCustomerDetail().setErrorMsg("Please select customer Constitution Type in MIS Information Tab.");
                return "Please select customer Constitution Type in MIS Information Tab.";
            }
            if (this.misConstitution.equalsIgnoreCase("01")) {
                if (this.misOccupation == null || this.misOccupation.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please select customer Occupation Type in MIS Information Tab.");
                    return "Please select customer Occupation Type in MIS Information Tab.";
                }
                if (this.misIsoResidence == null || this.misIsoResidence.equalsIgnoreCase("0")) {
                    getCustomerDetail().setErrorMsg("Please select Country Code of Jurisdiction of Residence in MIS Information Tab.");
                    return "Please select Country Code of Jurisdiction of Residence in MIS Information Tab.";
                }
                if (!this.misIsoResidence.equalsIgnoreCase("IN")) {
                    if ((this.misJuriTin == null || this.misJuriTin.equals(""))) {
                        getCustomerDetail().setErrorMsg("Please fill TIN issued By Juridiction in MIS Information Tab.");
                        return "Please fill TIN issued By Juridiction in MIS Information Tab.";
                    } else {
                        Matcher matcher = alphaNumericWithoutSpace.matcher(this.misJuriTin);
                        if (!matcher.matches()) {
                            getCustomerDetail().setErrorMsg("Tax Identification Number should be only aplhanumeric on Mis tab");
                            return "Tax Identification Number should be only aplhanumeric on Mis tab";
                        }
                    }
                    if ((this.misPlaceCity == null || this.misPlaceCity.equals("0"))) {
                        getCustomerDetail().setErrorMsg("Please fill Place/City of Birth in MIS Information Tab.");
                        return "Please fill Place/City of Birth in MIS Information Tab.";
                    } else {
                        Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.misPlaceCity);
                        if (!matcher.matches()) {
                            getCustomerDetail().setErrorMsg("Place/City of Birth should be only alphabet on Mis tab");
                            return "Place/City of Birth should be only alphabet on Mis tab";
                        }
                    }
                    if ((this.misBirth == null || this.misBirth.equals("0"))) {
                        getCustomerDetail().setErrorMsg("Please Select Country of Birth in MIS Information Tab.");
                        return "Please Select Country of Birth in MIS Information Tab.";
                    }
                }
            } else {
                if (/*this.incopDt == null ||*/this.incopPlace.equals("") || this.dcbDt == null) {
                    getCustomerDetail().setErrorMsg("Place of "
                            + "Incorporation / Date of Commencement on Mis tab are together mandatory");
                    return "Place of Incorporation / Date of "
                            + "Commencement on Mis tab are together mandatory";
                }
//                long diffr = CbsUtil.dayDiff(this.incopDt, new Date());
//                if (diffr < 0) {
//                    getCustomerDetail().setErrorMsg("Date of Incorporation or Formation can "
//                            + "not be greater than current date on Mis tab");
//                    return "Date of Incorporation or Formation can "
//                            + "not be greater than current date on Mis tab";
//                }
                long diffr = CbsUtil.dayDiff(this.dcbDt, ymd.parse(ymd.format(new Date())));
                if (diffr < 0) {
                    getCustomerDetail().setErrorMsg("Date of Commencement of Business can not be greater than current date on Mis tab");
                    return "Date of Commencement of Business can not be greater than current date on Mis tab";
                }
                if (this.countryOfIncorp.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please select Country of Incorporation or Formation on Mis tab");
                    return "Please select Country of Incorporation or Formation on Mis tab";
                }
                if (this.countryOfIncorp.equalsIgnoreCase("IN")) {
                    if (this.stateOfIncorp == null || this.stateOfIncorp.equals("0")) {
                        getCustomerDetail().setErrorMsg("Please select State of Incorporation or Formation on Mis tab");
                        return "Please select State of Incorporation or Formation on Mis tab";
                    }
                    if (this.incopPlace == null || this.incopPlace.equals("")) {
                        getCustomerDetail().setErrorMsg("Please select Place of Incorporation or Formation on Mis tab");
                        return "Please select Place of Incorporation or Formation on Mis tab";
                    }
                    List option = masterDelegate.getDistrictListLike(this.stateOfIncorp, this.incopPlace, "V");
                    if (option.size() <= 0) {
                        getCustomerDetail().setErrorMsg("Please select Proper Place of Incorporation or Formation on Mis tab");
                        return "Please select Proper Place of Incorporation or Formation on Mis tab";
                    } else if (!this.incopPlace.equalsIgnoreCase(((Vector) option.get(0)).get(0).toString())) {
                        getCustomerDetail().setErrorMsg("Please select Proper Place of Incorporation or Formation on Mis tab");
                        return "Please select Proper Place of Incorporation or Formation on Mis tab";
                    }
                } else {
                    if ((this.stateOfIncorpNotIN == null || this.stateOfIncorpNotIN.equals(""))) {
                        getCustomerDetail().setErrorMsg("Please Fill State of Incorporation on Mis tab");
                        return "Please Fill State of Incorporation on Mis tab";
                    }
                    if (!(this.stateOfIncorpNotIN == null || this.stateOfIncorpNotIN.equals(""))) {
                        Matcher matcher = onlyAlphabetWitSpace.matcher(this.stateOfIncorpNotIN);
                        if (!matcher.matches()) {
                            getCustomerDetail().setErrorMsg("State of Incorporation should be only alphabet on Mis tab");
                            return "State of Incorporation should be only alphabet on Mis tab";
                        }
                    }
                    if (!(this.incopPlaceNotIN == null || this.incopPlaceNotIN.equals(""))) {
                        getCustomerDetail().setErrorMsg("Please Fill Place of Incorporation on Mis tab");
                        return "Please Fill Place of Incorporation on Mis tab";

                    }
                    if (!(this.incopPlaceNotIN == null || this.incopPlaceNotIN.equals(""))) {
                        Matcher matcher = onlyAlphabetWitSpace.matcher(this.incopPlaceNotIN);
                        if (!matcher.matches()) {
                            getCustomerDetail().setErrorMsg("Place of Incorporation should be only alphabet on Mis tab");
                            return "Place of Incorporation should be only alphabet on Mis tab";
                        }
                    }
                }
                if (this.countryOfResiForTax == null || this.countryOfResiForTax.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please Select Country Of Residence For Tax on Mis tab");
                    return "Please Select Country Of Residence on Mis tab";
                }
            }
            if (this.opRiskRating == null) {
                getCustomerDetail().setErrorMsg("Risk Rating is mandatory on Mis Tab");
                return "Risk Rating is mandatory on Mis Tab";
            }
            if (this.opRiskRating.equals("0") || this.opRiskRating.equals("0") || this.opRiskRating == null) {
                getCustomerDetail().setErrorMsg("Risk Rating is mandatory on Mis Tab");
                return "Risk Rating is mandatory on Mis Tab";
            }
            if (!(this.opRiskRating == null || this.opRiskRating.equals("0") || this.opRiskRating.equals(""))) {
                if (riskAsOn == null) {
                    getCustomerDetail().setErrorMsg("Rating As On on Mis tab will mandatory");
                    return "Rating As On on Mis tab will mandatory";
                }
                long diff = CbsUtil.dayDiff(riskAsOn, ymd.parse(ymd.format(new Date())));
                if (diff < 0) {
                    getCustomerDetail().setErrorMsg("Rating As On can not be greater than current date on Mis tab");
                    return "Rating As On can not be greater than current date on Mis tab";
                }
            }
            long dayDiffr = CbsUtil.dayDiff(this.custUpdationDate, ymd.parse(ymd.format(new Date())));
            if (dayDiffr < 0) {
                getCustomerDetail().setErrorMsg("Customer Updation Date can not be greater than current date on Mis tab");
                return "Customer Updation Date can not be greater than current date on Mis tab";
            }
            if (this.typeOfDocSubmit == null || this.typeOfDocSubmit.equals("0")) {
                getCustomerDetail().setErrorMsg("Please select Type Of Doc Submit in MIS Information Tab.");
                return "Please select Type Of Doc Submit in MIS Information Tab.";
            }
            if (custImageCapture.length < 1) {
                getCustomerDetail().setErrorMsg("Please select Capture Image in MIS Information Tab.");
                return "Please select Capture Image in MIS Information Tab.";
            }

            if (misConstitution.equalsIgnoreCase("01")) {
                ParameterinfoReportTO to = masterDelegate.getAgeLimit("IND-MAX-IMAGE-COUNT");
                int maxCount = to.getCode();
                if (custImageCapture.length > maxCount) {
                    getCustomerDetail().setErrorMsg("Selected Capture Image exceeded the limit in MIS Information Tab.");
                    return "Selected Capture Image exceeded the limit in MIS Information Tab.";
                }
            } else {
                ParameterinfoReportTO to = masterDelegate.getAgeLimit("LEGAL-MAX-IMAGE-COUNT");
                int maxCount = to.getCode();
                if (custImageCapture.length > maxCount) {
                    getCustomerDetail().setErrorMsg("Selected Capture Image exceeded the limit in MIS Information Tab.");
                    return "Selected Capture Image exceeded the limit in MIS Information Tab.";
                }
            }
            if (this.custImageSelected == null || this.typeOfDocSubmit.equals("")) {
                getCustomerDetail().setErrorMsg("Please select Capture Image in MIS Information Tab.");
                return "Please select Capture Image in MIS Information Tab.";
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
        return "ok";
    }

    public void selectFieldValues() {
        Date date = new Date();

        try {
            CBSCustMISInfoTO misTO = masterDelegate.getMISInfoByCustId(getCustomerDetail().getCustId());
            CBSCustomerMasterDetailTO custTO = masterDelegate.getCustDetailsByCustId(getCustomerDetail().getCustId());
            CbsCustKycDetailsTo custKycDetailsTO = masterDelegate.getCustKycDetailsByCustId(getCustomerDetail().getCustId());
            if (misTO != null) {
//                if (mISTO.getType() == null || mISTO.getType().equalsIgnoreCase("")) {
//                    this.misType = "";
//                } else {
//                    this.setMisType(mISTO.getType());
//                }
//                if (mISTO.getGroupID() == null || mISTO.getGroupID().equalsIgnoreCase("")) {
//                    this.setGroupId("");
//                } else {
//                    this.setGroupId(mISTO.getGroupID());
//                }
//                if (mISTO.getStatusAsOn() == null) {
//                    this.setStatusDate(new Date());
//                } else {
//                    this.setStatusDate(mISTO.getStatusAsOn());
//                }
//                if (mISTO.getOccupationCode() == null || mISTO.getOccupationCode().equalsIgnoreCase("")) {
//                    this.setMisOccupation("0");
//                } else {
//                    this.setMisOccupation(mISTO.getOccupationCode());
//                }
//                if (mISTO.getConstitutionCode() == null || mISTO.getConstitutionCode().equalsIgnoreCase("")) {
//                    this.setMisConstitution("0");
//                } else {
//                    this.setMisConstitution(mISTO.getConstitutionCode());
//                }
//                if (mISTO.getCasteCode() == null || mISTO.getCasteCode().equalsIgnoreCase("")) {
//                    this.setMisCast("0");
//                } else {
//                    this.setMisCast(mISTO.getCasteCode());
//                }
//                if (mISTO.getCommunityCode() == null || mISTO.getCommunityCode().equalsIgnoreCase("")) {
//                    this.setMisCommunity("0");
//                } else {
//                    this.setMisCommunity(mISTO.getCommunityCode());
//                }
//                if (mISTO.getHealthCode() == null || mISTO.getHealthCode().equalsIgnoreCase("")) {
//                    this.setHealthCode("0");
//                } else {
//                    this.setHealthCode(mISTO.getHealthCode());
//                }
//                if (mISTO.getBusinessSegment() == null || mISTO.getBusinessSegment().equalsIgnoreCase("")) {
//                    this.setBusinessSegment("");
//                } else {
//                    this.setBusinessSegment(mISTO.getBusinessSegment());
//                }
//                if (mISTO.getSalesTurnover() == null) {
//                    this.setSalesTurnOver("0.0");
//                } else {
//                    this.setSalesTurnOver(mISTO.getSalesTurnover().toString());
//                }
                List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("357");
                typeOfDocSubmitList = new ArrayList<SelectItem>();
                typeOfDocSubmitList.add(new SelectItem("0", "--Select--"));
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    typeOfDocSubmitList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                }

                List optionlist = aitr.getListAsPerRequirement("011", misTO.getStateOfIncorp(), "DIST", "0", "0", "0", ymd.format(new Date()),0);
                incopPlaceList = new ArrayList<SelectItem>();
                incopPlaceList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    incopPlaceList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
                optionlist = aitr.getListAsPerRequirement("362", custTO.getCustEntityType(), "DOC", "0", "0", "0",ymd.format(new Date()),0);
                custImageCaptureList = new ArrayList<SelectItem>();
                custImageCaptureDesc = new HashMap<String, String>();
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    custImageCaptureDesc.put(ele6.get(0).toString(), ele6.get(1).toString());
                    custImageCaptureList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }

                this.misOccupation = (misTO.getOccupationCode() == null || misTO.getOccupationCode().equals(""))
                        ? "0" : misTO.getOccupationCode();
                this.incopDt = (misTO.getIncorpDt() == null || misTO.getIncorpDt().equals("")) ? null : ymd.parse(misTO.getIncorpDt());

                this.countryOfIncorp = (misTO.getCountryOfIncorp() == null || misTO.getCountryOfIncorp().equals("") || misTO.getCountryOfIncorp().equals("0"))
                        ? "0" : misTO.getCountryOfIncorp();


                if (misTO.getCountryOfIncorp().equalsIgnoreCase("IN")) {
                    displayOnIncrpCntryNotIN = "none";
                    displayOnIncrpCntryIN = "";
//                    incopPlaceNotIN = (misTO.getIncorpPlace() == null || misTO.getIncorpPlace().equals("") || misTO.getIncorpPlace().equals("0"))
//                            ? "0" : misTO.getIncorpPlace();
//                    stateOfIncorpNotIN = (misTO.getIncorpPlace() == null || misTO.getIncorpPlace().equals("") || misTO.getIncorpPlace().equals("0"))
//                            ? "0" : misTO.getIncorpPlace();
                    this.stateOfIncorp = (misTO.getStateOfIncorp() == null || misTO.getStateOfIncorp().equals("") || misTO.getStateOfIncorp().equals("0"))
                            ? "0" : misTO.getStateOfIncorp();
                    this.incopPlace = (misTO.getIncorpPlace() == null || misTO.getIncorpPlace().equals("") || misTO.getIncorpPlace().equals("0"))
                            ? "" : misTO.getIncorpPlace();
                } else {
                    displayOnIncrpCntryNotIN = "";
                    displayOnIncrpCntryIN = "none";
                    incopPlaceNotIN = (misTO.getIncorpPlace() == null || misTO.getIncorpPlace().equals("") || misTO.getIncorpPlace().equals("0"))
                            ? "" : misTO.getIncorpPlace();
                    stateOfIncorpNotIN = (misTO.getStateOfIncorp() == null || misTO.getStateOfIncorp().equals("") || misTO.getStateOfIncorp().equals("0"))
                            ? "" : misTO.getStateOfIncorp();
                }

                this.dcbDt = (misTO.getCommDt() == null || misTO.getCommDt().equals("")) ? null : ymd.parse(misTO.getCommDt());
                this.misConstitution = (misTO.getConstitutionCode() == null || misTO.getConstitutionCode().equals("") || misTO.getConstitutionCode().equals("0"))
                        ? "0" : misTO.getConstitutionCode();
                this.misIsoResidence = (misTO.getMisJuriResidence() == null || misTO.getMisJuriResidence().equals("") || misTO.getMisJuriResidence().equals("0"))
                        ? "0" : misTO.getMisJuriResidence();
                this.misJuriTin = (misTO.getMisTin() == null || misTO.getMisTin().equals(""))
                        ? "" : misTO.getMisTin();
                this.misPlaceCity = (misTO.getMisCity() == null || misTO.getMisCity().equalsIgnoreCase("0") || misTO.getMisCity().equals(""))
                        ? "" : misTO.getMisCity();
                this.misBirth = (misTO.getMisBirthCountry() == null || misTO.getMisBirthCountry().equals("") || misTO.getMisBirthCountry().equals("0"))
                        ? "0" : misTO.getMisBirthCountry();
                this.misType = (misTO.getType() == null || misTO.getType().equals("")) ? "S" : misTO.getType();
                this.groupId = (misTO.getGroupID() == null || misTO.getGroupID().equals("")) ? "" : misTO.getGroupID();
                List groupList = common.getGroupIdByTypeAndCustId(getCustomerDetail().getCustId(), misTO.getType());
                if (!groupList.isEmpty()) {
                    groupIdList = new ArrayList<SelectItem>();
                    Vector eleId = (Vector) groupList.get(0);
                    groupIdList.add(new SelectItem(eleId.get(0).toString(), eleId.get(1).toString()));
                }
            }
            if (custTO != null) {
                this.opRiskRating = (custTO.getOperationalRiskRating() == null || custTO.getOperationalRiskRating().equals(""))
                        ? "0" : custTO.getOperationalRiskRating();
                this.riskAsOn = custTO.getRatingAsOn();
                this.custAquiDate = custTO.getCustAcquistionDate();
                this.threshOldTransLimit = (custTO.getThresoldTransactionLimit() == null || custTO.getThresoldTransactionLimit().equals(""))
                        ? "0" : custTO.getThresoldTransactionLimit().toString();
                this.thresholdLimitUpdDate = custTO.getThresoldLimitUpdationDate();
                this.custUpdationDate = custTO.getCustUpdationDate();
                this.custImageCapture = (String[]) ((custTO.getCustImage() == null || custTO.getCustImage().equals(""))
                        ? custTO.getCustImage().split("\\|") : custTO.getCustImage().split("\\|"));

                List<String> list = Arrays.asList(custImageCapture);
                List<String> ImageCodeList = new ArrayList<String>();
                for (String imgCode : list) {
                    if ((custTO.getCustEntityType().equalsIgnoreCase("01")) && (imgCode.equalsIgnoreCase("02") || imgCode.equalsIgnoreCase("09"))) {
                        ImageCodeList.add(imgCode);
                    } else if ((custTO.getCustEntityType().equalsIgnoreCase("02")) && (imgCode.equalsIgnoreCase("03"))) {
                        ImageCodeList.add(imgCode);
                    }
                }
                this.custImageCapture = ImageCodeList.toArray(new String[0]);

                ///***To show  which document is captured****////
                this.custImageSelected = "";
                String image = "";
                String imageCode = "";
                if (custImageCapture.length == 1) {
                    if (custImageCaptureDesc.get(custImageCapture[0]) == null) {
                        image = "";
                    } else {
                        image = "*" + custImageCaptureDesc.get(custImageCapture[0]);
                        imageCode = custImageCapture[0];
                    }
                } else {
                    for (int i = 0; i < custImageCapture.length; i++) {
                        if (i == 0) {
                            image = image.concat("*" + custImageCaptureDesc.get(custImageCapture[0]));
                            imageCode = imageCode.concat(custImageCapture[0]);
                        } else {
                            image = image.concat("<br/>" + "*" + custImageCaptureDesc.get(custImageCapture[i]));
                            imageCode = imageCode.concat("|" + custImageCapture[i]);
                        }
                    }
                }
                this.custImageSelected = image;
                this.custImageCode = imageCode;
                ///*******////
            }
//
//            this.custImageCode = custTO.getCustImage() == null || custTO.getCustImage().equals("")
//                    ? "" : custTO.getCustImage();



            if (custKycDetailsTO != null) {
                this.typeOfDocSubmit = (custKycDetailsTO.getTypeOfDocSubmitted() == null || custKycDetailsTO.getTypeOfDocSubmitted().equals(""))
                        ? "0" : custKycDetailsTO.getTypeOfDocSubmitted();
            }
            //Addition on 27/10/2015
            makeChangeFlagToUnChange();
            if (getCustomerDetail().getFunction().equalsIgnoreCase("V")) {
                CBSCustMISInfoHisTO misHisTo = customerMgmtRemote.
                        getCustomerLastChangeDetailForMis(getCustomerDetail().getVerifyCustId());
                if (misHisTo != null) {
                    this.misOccupationChgFlag = this.misOccupation.equalsIgnoreCase(misHisTo.getOccupationCode() == null ? "0" : misHisTo.getOccupationCode()) ? "color: #000000" : "color: #FF0000";
                    Date tempManDt = null;
                    this.incopDtChgFlag = (tempManDt = (this.incopDt == null ? ymd.parse("19000101") : this.incopDt)).compareTo(ymd.parse((misHisTo.getIncorpDt() == null || misHisTo.getIncorpDt().equals("")) ? "19000101" : misHisTo.getIncorpDt())) == 0 ? "color: #000000" : "color: #FF0000";
                    this.incopPlaceChgFlag = this.incopPlace.equalsIgnoreCase(misHisTo.getIncorpPlace() == null ? "" : misHisTo.getIncorpPlace()) ? "color: #000000" : "color: #FF0000";
                    this.dcbDtChgFlag = (tempManDt = (this.dcbDt == null ? ymd.parse("19000101") : this.dcbDt)).compareTo(ymd.parse((misHisTo.getCommDt() == null || misHisTo.getCommDt().equals("")) ? "19000101" : misHisTo.getCommDt())) == 0 ? "color: #000000" : "color: #FF0000";
                    this.misConstitutionChgFlag = this.misConstitution.equalsIgnoreCase(misHisTo.getConstitutionCode() == null ? "" : misHisTo.getConstitutionCode()) ? "color: #000000" : "color: #FF0000";
                    this.misIsoResidenceChgFlag = this.misIsoResidence.equalsIgnoreCase(misHisTo.getMisJuriResidence() == null ? "" : misHisTo.getMisJuriResidence()) ? "color: #000000" : "color: #FF0000";
                    this.misJuriTinChgFlag = this.misJuriTin.equalsIgnoreCase(misHisTo.getMisTin() == null ? "" : misHisTo.getMisTin()) ? "color: #000000" : "color: #FF0000";
                    this.misPlaceCityChgFlag = this.misPlaceCity.equalsIgnoreCase(misHisTo.getMisCity() == null ? "" : misHisTo.getMisCity()) ? "color: #000000" : "color: #FF0000";
                    this.misBirthChgFlag = this.misBirth.equalsIgnoreCase(misHisTo.getMisBirthCountry() == null ? "" : misHisTo.getMisBirthCountry()) ? "color: #000000" : "color: #FF0000";
                    this.misTypeChgFlag = this.misType.equalsIgnoreCase(misHisTo.getType() == null ? "" : misHisTo.getType()) ? "color: #000000" : "color: #FF0000";
                    this.misGroupIdChgFlag = this.groupId.equalsIgnoreCase(misHisTo.getGroupID() == null ? "" : misHisTo.getGroupID()) ? "color: #000000" : "color: #FF0000";
                }

                CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.
                        getCustomerLastChangeDetail(getCustomerDetail().getVerifyCustId());
                if (hisTo != null) {
                    this.opRiskRatingChgFlag = this.opRiskRating.equalsIgnoreCase(hisTo.getOperationalRiskRating() == null ? "" : hisTo.getOperationalRiskRating()) ? "color: #000000" : "color: #FF0000";
                    Date tempManDt = null;
                    this.riskAsOnChgFlag = (tempManDt = (this.riskAsOn == null ? dmy.parse("01/01/1900") : this.riskAsOn)).compareTo(ymd.parse((hisTo.getRatingAsOn() == null || hisTo.getRatingAsOn().equals("")) ? "19000101" : hisTo.getRatingAsOn())) == 0 ? "color: #000000" : "color: #FF0000";
                    this.custAquiDateChgFlag = (tempManDt = (this.custAquiDate == null ? dmy.parse("01/01/1900") : this.custAquiDate)).compareTo(ymd.parse((hisTo.getCustAcquistionDate() == null || hisTo.getCustAcquistionDate().equals("")) ? "19000101" : hisTo.getCustAcquistionDate())) == 0 ? "color: #000000" : "color: #FF0000";
                    this.threshOldTransLimitChgFlag = this.threshOldTransLimit.equalsIgnoreCase(hisTo.getThresoldTransactionLimit() == null ? "0" : hisTo.getThresoldTransactionLimit().toString()) ? "color: #000000" : "color: #FF0000";
                    this.thresholdLimitUpdDateChgFlag = (tempManDt = (this.thresholdLimitUpdDate == null ? dmy.parse("01/01/1900") : this.thresholdLimitUpdDate)).compareTo(ymd.parse((hisTo.getThresoldLimitUpdationDate() == null || hisTo.getThresoldLimitUpdationDate().equals("")) ? "19000101" : hisTo.getThresoldLimitUpdationDate())) == 0 ? "color: #000000" : "color: #FF0000";
                    this.custUpdationDateChgFlag = (tempManDt = (this.custUpdationDate == null ? dmy.parse("01/01/1900") : this.custUpdationDate)).compareTo(ymd.parse((hisTo.getCustUpdationDate() == null || hisTo.getCustUpdationDate().equals("")) ? "19000101" : hisTo.getCustUpdationDate())) == 0 ? "color: #000000" : "color: #FF0000";
                    this.custImageCaptureChgFlag = this.custImageCode.equalsIgnoreCase(hisTo.getCustImage() == null ? "" : hisTo.getCustImage()) ? "color: #000000" : "color: #FF0000";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setMisOccupation("0");
        this.setIncopDt(null);
        this.setIncopPlace("");
        this.setDcbDt(null);
        this.setMisConstitution("0");
        this.setMisJuriTin("");
        this.setMisPlaceCity("");
        this.setOpRiskRating("0");
        this.setRiskAsOn(null);
        this.setCustAquiDate(null);
        this.setThreshOldTransLimit("");
        this.setThresholdLimitUpdDate(null);
        this.setCustUpdationDate(null);
        this.setMisType("S");
        this.setGroupId("S");
        this.setTypeOfDocSubmit("0");
        this.setCustImageCapture(new String[0]);
        this.setCustImageSelected("");
        groupIdList = new ArrayList<SelectItem>();
        //Add On 28/10/2015
        makeChangeFlagToUnChange();
    }

    //Add On 28/10/2015
    public void makeChangeFlagToUnChange() {
        this.setMisOccupationChgFlag("color: #000000");
        this.setIncopDtChgFlag("color: #000000");
        this.setIncopPlaceChgFlag("color: #000000");
        this.setDcbDtChgFlag("color: #000000");
        this.setMisConstitutionChgFlag("color: #000000");
        this.setMisIsoResidenceChgFlag("color: #000000");
        this.setMisJuriTinChgFlag("color: #000000");
        this.setMisPlaceCityChgFlag("color: #000000");
        this.setMisBirthChgFlag("color: #000000");
        this.setOpRiskRatingChgFlag("color: #000000");
        this.setRiskAsOnChgFlag("color: #000000");
        this.setCustAquiDateChgFlag("color: #000000");
        this.setThreshOldTransLimitChgFlag("color: #000000");
        this.setThresholdLimitUpdDateChgFlag("color: #000000");
        this.setCustUpdationDateChgFlag("color: #000000");
        this.setMisTypeChgFlag("color: #000000");
        this.setMisGroupIdChgFlag("color: #000000");
    }

    //Getter And Setter
    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMisType() {
        return misType;
    }

    public void setMisType(String misType) {
        this.misType = misType;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
    public String getMisCast() {
        return misCast;
    }

    public void setMisCast(String misCast) {
        this.misCast = misCast;
    }

    public String getMisCommunity() {
        return misCommunity;
    }

    public void setMisCommunity(String misCommunity) {
        this.misCommunity = misCommunity;
    }

    public String getMisConstitution() {
        return misConstitution;
    }

    public void setMisConstitution(String misConstitution) {
        this.misConstitution = misConstitution;
    }

    public String getMisOccupation() {
        return misOccupation;
    }

    public void setMisOccupation(String misOccupation) {
        this.misOccupation = misOccupation;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getHealthCode() {
        return healthCode;
    }

    public void setHealthCode(String healthCode) {
        this.healthCode = healthCode;
    }

    public List<SelectItem> getHealthCodeOption() {
        return healthCodeOption;
    }

    public void setHealthCodeOption(List<SelectItem> healthCodeOption) {
        this.healthCodeOption = healthCodeOption;
    }

    public List<SelectItem> getMisCastOption() {
        return misCastOption;
    }

    public void setMisCastOption(List<SelectItem> misCastOption) {
        this.misCastOption = misCastOption;
    }

    public List<SelectItem> getMisCommunityOption() {
        return misCommunityOption;
    }

    public void setMisCommunityOption(List<SelectItem> misCommunityOption) {
        this.misCommunityOption = misCommunityOption;
    }

    public List<SelectItem> getMisConstitutionOption() {
        return misConstitutionOption;
    }

    public void setMisConstitutionOption(List<SelectItem> misConstitutionOption) {
        this.misConstitutionOption = misConstitutionOption;
    }

    public List<SelectItem> getMisOccupationOption() {
        return misOccupationOption;
    }

    public void setMisOccupationOption(List<SelectItem> misOccupationOption) {
        this.misOccupationOption = misOccupationOption;
    }

    public String getBusinessSegment() {
        return businessSegment;
    }

    public void setBusinessSegment(String businessSegment) {
        this.businessSegment = businessSegment;
    }

    public String getSalesTurnOver() {
        return salesTurnOver;
    }

    public void setSalesTurnOver(String salesTurnOver) {
        this.salesTurnOver = salesTurnOver;
    }

    public CustPersonalDetails getCustPersonalDetails() {
        return custPersonalDetails;
    }

    public void setCustPersonalDetails(CustPersonalDetails custPersonalDetails) {
        this.custPersonalDetails = custPersonalDetails;
    }

    public MinorInfo getMinorInfoObj() {
        return minorInfoObj;
    }

    public void setMinorInfoObj(MinorInfo minorInfoObj) {
        this.minorInfoObj = minorInfoObj;
    }

    public Date getIncopDt() {
        return incopDt;
    }

    public void setIncopDt(Date incopDt) {
        this.incopDt = incopDt;
    }

    public String getIncopPlace() {
        return incopPlace;
    }

    public void setIncopPlace(String incopPlace) {
        this.incopPlace = incopPlace;
    }

    public List<SelectItem> getIncopPlaceList() {
        return incopPlaceList;
    }

    public void setIncopPlaceList(List<SelectItem> incopPlaceList) {
        this.incopPlaceList = incopPlaceList;
    }

    public Date getDcbDt() {
        return dcbDt;
    }

    public void setDcbDt(Date dcbDt) {
        this.dcbDt = dcbDt;
    }

    public String getMisIsoResidence() {
        return misIsoResidence;
    }

    public void setMisIsoResidence(String misIsoResidence) {
        this.misIsoResidence = misIsoResidence;
    }

    public List<SelectItem> getMisIsoResidenceList() {
        return misIsoResidenceList;
    }

    public void setMisIsoResidenceList(List<SelectItem> misIsoResidenceList) {
        this.misIsoResidenceList = misIsoResidenceList;
    }

    public String getMisJuriTin() {
        return misJuriTin;
    }

    public void setMisJuriTin(String misJuriTin) {
        this.misJuriTin = misJuriTin;
    }

    public String getMisPlaceCity() {
        return misPlaceCity;
    }

    public void setMisPlaceCity(String misPlaceCity) {
        this.misPlaceCity = misPlaceCity;
    }

    public String getMisBirth() {
        return misBirth;
    }

    public void setMisBirth(String misBirth) {
        this.misBirth = misBirth;
    }

    public List<SelectItem> getMisBirthList() {
        return misBirthList;
    }

    public void setMisBirthList(List<SelectItem> misBirthList) {
        this.misBirthList = misBirthList;
    }

    public String getOpRiskRating() {
        return opRiskRating;
    }

    public void setOpRiskRating(String opRiskRating) {
        this.opRiskRating = opRiskRating;
    }

    public List<SelectItem> getOpRiskRatingOption() {
        return opRiskRatingOption;
    }

    public void setOpRiskRatingOption(List<SelectItem> opRiskRatingOption) {
        this.opRiskRatingOption = opRiskRatingOption;
    }

    public Date getRiskAsOn() {
        return riskAsOn;
    }

    public void setRiskAsOn(Date riskAsOn) {
        this.riskAsOn = riskAsOn;
    }

    public Date getCustAquiDate() {
        return custAquiDate;
    }

    public void setCustAquiDate(Date custAquiDate) {
        this.custAquiDate = custAquiDate;
    }

    public String getThreshOldTransLimit() {
        return threshOldTransLimit;
    }

    public void setThreshOldTransLimit(String threshOldTransLimit) {
        this.threshOldTransLimit = threshOldTransLimit;
    }

    public Date getThresholdLimitUpdDate() {
        return thresholdLimitUpdDate;
    }

    public void setThresholdLimitUpdDate(Date thresholdLimitUpdDate) {
        this.thresholdLimitUpdDate = thresholdLimitUpdDate;
    }

    public Date getCustUpdationDate() {
        return custUpdationDate;
    }

    public void setCustUpdationDate(Date custUpdationDate) {
        this.custUpdationDate = custUpdationDate;
    }

    public boolean isMisIndDisable() {
        return misIndDisable;
    }

    public void setMisIndDisable(boolean misIndDisable) {
        this.misIndDisable = misIndDisable;
    }

    public boolean isMisCompDisable() {
        return misCompDisable;
    }

    public void setMisCompDisable(boolean misCompDisable) {
        this.misCompDisable = misCompDisable;
    }

    public String getMisOccupationChgFlag() {
        return misOccupationChgFlag;
    }

    public void setMisOccupationChgFlag(String misOccupationChgFlag) {
        this.misOccupationChgFlag = misOccupationChgFlag;
    }

    public String getIncopDtChgFlag() {
        return incopDtChgFlag;
    }

    public void setIncopDtChgFlag(String incopDtChgFlag) {
        this.incopDtChgFlag = incopDtChgFlag;
    }

    public String getIncopPlaceChgFlag() {
        return incopPlaceChgFlag;
    }

    public void setIncopPlaceChgFlag(String incopPlaceChgFlag) {
        this.incopPlaceChgFlag = incopPlaceChgFlag;
    }

    public String getDcbDtChgFlag() {
        return dcbDtChgFlag;
    }

    public void setDcbDtChgFlag(String dcbDtChgFlag) {
        this.dcbDtChgFlag = dcbDtChgFlag;
    }

    public String getMisConstitutionChgFlag() {
        return misConstitutionChgFlag;
    }

    public void setMisConstitutionChgFlag(String misConstitutionChgFlag) {
        this.misConstitutionChgFlag = misConstitutionChgFlag;
    }

    public String getMisIsoResidenceChgFlag() {
        return misIsoResidenceChgFlag;
    }

    public void setMisIsoResidenceChgFlag(String misIsoResidenceChgFlag) {
        this.misIsoResidenceChgFlag = misIsoResidenceChgFlag;
    }

    public String getMisJuriTinChgFlag() {
        return misJuriTinChgFlag;
    }

    public void setMisJuriTinChgFlag(String misJuriTinChgFlag) {
        this.misJuriTinChgFlag = misJuriTinChgFlag;
    }

    public String getMisPlaceCityChgFlag() {
        return misPlaceCityChgFlag;
    }

    public void setMisPlaceCityChgFlag(String misPlaceCityChgFlag) {
        this.misPlaceCityChgFlag = misPlaceCityChgFlag;
    }

    public String getMisBirthChgFlag() {
        return misBirthChgFlag;
    }

    public void setMisBirthChgFlag(String misBirthChgFlag) {
        this.misBirthChgFlag = misBirthChgFlag;
    }

    public String getOpRiskRatingChgFlag() {
        return opRiskRatingChgFlag;
    }

    public void setOpRiskRatingChgFlag(String opRiskRatingChgFlag) {
        this.opRiskRatingChgFlag = opRiskRatingChgFlag;
    }

    public String getRiskAsOnChgFlag() {
        return riskAsOnChgFlag;
    }

    public void setRiskAsOnChgFlag(String riskAsOnChgFlag) {
        this.riskAsOnChgFlag = riskAsOnChgFlag;
    }

    public String getCustAquiDateChgFlag() {
        return custAquiDateChgFlag;
    }

    public void setCustAquiDateChgFlag(String custAquiDateChgFlag) {
        this.custAquiDateChgFlag = custAquiDateChgFlag;
    }

    public String getThreshOldTransLimitChgFlag() {
        return threshOldTransLimitChgFlag;
    }

    public void setThreshOldTransLimitChgFlag(String threshOldTransLimitChgFlag) {
        this.threshOldTransLimitChgFlag = threshOldTransLimitChgFlag;
    }

    public String getThresholdLimitUpdDateChgFlag() {
        return thresholdLimitUpdDateChgFlag;
    }

    public void setThresholdLimitUpdDateChgFlag(String thresholdLimitUpdDateChgFlag) {
        this.thresholdLimitUpdDateChgFlag = thresholdLimitUpdDateChgFlag;
    }

    public String getCustUpdationDateChgFlag() {
        return custUpdationDateChgFlag;
    }

    public void setCustUpdationDateChgFlag(String custUpdationDateChgFlag) {
        this.custUpdationDateChgFlag = custUpdationDateChgFlag;
    }

    public List<SelectItem> getMisTypeList() {
        return misTypeList;
    }

    public void setMisTypeList(List<SelectItem> misTypeList) {
        this.misTypeList = misTypeList;
    }

    public List<SelectItem> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<SelectItem> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public String getDisplayOfficePanal() {
        return displayOfficePanal;
    }

    public void setDisplayOfficePanal(String displayOfficePanal) {
        this.displayOfficePanal = displayOfficePanal;
    }

    public String getMisTypeChgFlag() {
        return misTypeChgFlag;
    }

    public void setMisTypeChgFlag(String misTypeChgFlag) {
        this.misTypeChgFlag = misTypeChgFlag;
    }

    public String getMisGroupIdChgFlag() {
        return misGroupIdChgFlag;
    }

    public void setMisGroupIdChgFlag(String misGroupIdChgFlag) {
        this.misGroupIdChgFlag = misGroupIdChgFlag;
    }

    public String getTypeOfDocSubmit() {
        return typeOfDocSubmit;
    }

    public void setTypeOfDocSubmit(String typeOfDocSubmit) {
        this.typeOfDocSubmit = typeOfDocSubmit;
    }

    public String getTypeOfDocSubmitChgFlag() {
        return typeOfDocSubmitChgFlag;
    }

    public void setTypeOfDocSubmitChgFlag(String typeOfDocSubmitChgFlag) {
        this.typeOfDocSubmitChgFlag = typeOfDocSubmitChgFlag;
    }

    public List<SelectItem> getTypeOfDocSubmitList() {
        return typeOfDocSubmitList;
    }

    public void setTypeOfDocSubmitList(List<SelectItem> typeOfDocSubmitList) {
        this.typeOfDocSubmitList = typeOfDocSubmitList;
    }

    public List<SelectItem> getStateOfIncorpList() {
        return stateOfIncorpList;
    }

    public void setStateOfIncorpList(List<SelectItem> stateOfIncorpList) {
        this.stateOfIncorpList = stateOfIncorpList;
    }

    public List<SelectItem> getCountryOfIncorpList() {
        return countryOfIncorpList;
    }

    public void setCountryOfIncorpList(List<SelectItem> countryOfIncorpList) {
        this.countryOfIncorpList = countryOfIncorpList;
    }

    public List<SelectItem> getCountryOfResiForTaxList() {
        return countryOfResiForTaxList;
    }

    public void setCountryOfResiForTaxList(List<SelectItem> countryOfResiForTaxList) {
        this.countryOfResiForTaxList = countryOfResiForTaxList;
    }

    public String getStateOfIncorp() {
        return stateOfIncorp;
    }

    public void setStateOfIncorp(String stateOfIncorp) {
        this.stateOfIncorp = stateOfIncorp;
    }

    public String getCountryOfIncorp() {
        return countryOfIncorp;
    }

    public void setCountryOfIncorp(String countryOfIncorp) {
        this.countryOfIncorp = countryOfIncorp;
    }

    public String getCountryOfResiForTax() {
        return countryOfResiForTax;
    }

    public void setCountryOfResiForTax(String countryOfResiForTax) {
        this.countryOfResiForTax = countryOfResiForTax;
    }

    public String getCountryOfIncorpChgFlag() {
        return countryOfIncorpChgFlag;
    }

    public void setCountryOfIncorpChgFlag(String countryOfIncorpChgFlag) {
        this.countryOfIncorpChgFlag = countryOfIncorpChgFlag;
    }

    public String getCountryOfResiForTaxChgFlag() {
        return countryOfResiForTaxChgFlag;
    }

    public void setCountryOfResiForTaxChgFlag(String countryOfResiForTaxChgFlag) {
        this.countryOfResiForTaxChgFlag = countryOfResiForTaxChgFlag;
    }

    public String getDisplayOnIncrpCntryNotIN() {
        return displayOnIncrpCntryNotIN;
    }

    public void setDisplayOnIncrpCntryNotIN(String displayOnIncrpCntryNotIN) {
        this.displayOnIncrpCntryNotIN = displayOnIncrpCntryNotIN;
    }

    public String getDisplayOnIncrpCntryIN() {
        return displayOnIncrpCntryIN;
    }

    public void setDisplayOnIncrpCntryIN(String displayOnIncrpCntryIN) {
        this.displayOnIncrpCntryIN = displayOnIncrpCntryIN;
    }

    public String getStateOfIncorpChgFlag() {
        return stateOfIncorpChgFlag;
    }

    public void setStateOfIncorpChgFlag(String stateOfIncorpChgFlag) {
        this.stateOfIncorpChgFlag = stateOfIncorpChgFlag;
    }

    public String getIncopPlaceNotIN() {
        return incopPlaceNotIN;
    }

    public void setIncopPlaceNotIN(String incopPlaceNotIN) {
        this.incopPlaceNotIN = incopPlaceNotIN;
    }

    public String getStateOfIncorpNotIN() {
        return stateOfIncorpNotIN;
    }

    public void setStateOfIncorpNotIN(String stateOfIncorpNotIN) {
        this.stateOfIncorpNotIN = stateOfIncorpNotIN;
    }

    public String[] getCustImageCapture() {
        return custImageCapture;
    }

    public void setCustImageCapture(String[] custImageCapture) {
        this.custImageCapture = custImageCapture;
    }

    public List<SelectItem> getCustImageCaptureList() {
        return custImageCaptureList;
    }

    public void setCustImageCaptureList(List<SelectItem> custImageCaptureList) {
        this.custImageCaptureList = custImageCaptureList;
    }

    public String getCustImageCaptureChgFlag() {
        return custImageCaptureChgFlag;
    }

    public void setCustImageCaptureChgFlag(String custImageCaptureChgFlag) {
        this.custImageCaptureChgFlag = custImageCaptureChgFlag;
    }

    public String getCustImageSelected() {
        return custImageSelected;
    }

    public void setCustImageSelected(String custImageSelected) {
        this.custImageSelected = custImageSelected;
    }

    public String getCustImageSelectedChgFlag() {
        return custImageSelectedChgFlag;
    }

    public void setCustImageSelectedChgFlag(String custImageSelectedChgFlag) {
        this.custImageSelectedChgFlag = custImageSelectedChgFlag;
    }

    public Map<String, String> getCustImageCaptureDesc() {
        return custImageCaptureDesc;
    }

    public void setCustImageCaptureDesc(Map<String, String> custImageCaptureDesc) {
        this.custImageCaptureDesc = custImageCaptureDesc;
    }

    public String getCustImageCode() {
        return custImageCode;
    }

    public void setCustImageCode(String custImageCode) {
        this.custImageCode = custImageCode;
    }
}
