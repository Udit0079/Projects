/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSCustMinorInfoHisTO;
import com.cbs.dto.customer.CBSCustMinorInfoTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailHisTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.master.ParameterinfoReportTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.customer.CustomerManagementFacadeRemote;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import org.apache.commons.validator.routines.checkdigit.VerhoeffCheckDigit;

public class CustPersonalDetails {

    private CustGeneralInfo custGeneralInfo;
    private CustAddressInfo custAddressInfo;
    private RelatedPersonInfo relatedInfo;
    private RelatedPerson relatedInformation;
    private MisInfo misInfo;
    private String titleType;
    private String titleType2;
    String custFullName;
    private String gstIdentificationNumber;
    String custFirstName;
    String shortName;
    private String genderType;
    private String maritalType;
    String custStatusInPersnlInfo;
    private String staffType;
    String salary;
    String acctLevelChanges;
    String custLevelChanges;
    boolean flagForNri;
    int flagForCreditRate;
    boolean flagForCreditCard;
    String tdsCode;
    Date perDateOfBirth;
    String tdsCustId;
    String TdsExRefNo;
    String custFloorLim;
    String remarks;
    String panGirNo;
    String tinNo;
    String salesTaxNo;
    String exciseNo;
    String voterIdNo;
    String fathername;
    String drivingLiscNo;
    String motherName;
    String msgForStaff;
    String staffNo;
    String dobFlag;
    String msgMinor;
    String msg2;
    String passFlag;
    String passportNumber;
    String msg3;
    String msg4;
    String msg5;
    String msg6;
    String passportIssuingAuthority;
    String passportIssuePlace;
    Date passPortExpirdate;
    Date passPortIssuedate;
    private String nriType;
    String UINCardNo;
    String mobNo;
    String virtualId;
    String msg;
    String commuPreference;
    String preferedLanguage;
    String nameInPreferredLanguage;
    List<SelectItem> commuPreferenceOption;
    boolean flagForsave;
    private String minorType;
    String chargeTurnOverFlag;
    String pureAllowedFlag;
    List<SelectItem> pureAllowedFlagOption;
    private List<SelectItem> minorTypeOption;
    List<SelectItem> chargeTurnOverFlagOption;
    List<SelectItem> preferedLanguageOption;
    private List<SelectItem> titleTypeOption;
    private List<SelectItem> genderTypeOption;
    private List<SelectItem> maritalTypeOption;
    private List<SelectItem> staffTypeOption;
    List<SelectItem> custStatusInPersnlInfoOption;
    List<SelectItem> titleType2Option;
    private List<SelectItem> nriTypeOption;
    private CustomerDetail customerDetail;
    CustomerMasterDelegate masterDelegate;
    String viewIdForNre = "/pages/master/sm/test.jsp";
    String viewIdForMinor = "/pages/master/sm/test.jsp";
    boolean minorFlag;
    boolean nreFlag;
    NreInfo nreInfo;
    MinorInfo minorInfo;
    String panNoOption;
    List<SelectItem> panNoOptionList;
    String panNoVisibility = "false";
    private String adhaarNo;
    private String lpgId;
    private String adhaarLpgAcno;
    private String mandateFlag;
    private List<SelectItem> mandateFlagOption;
    private Date mandateDt;
    private String regType;
    //  change by rahul
    private String aadhaarMode;
    private String aadhaarBankIin;
    private List<SelectItem> modeTypeOption;
    private List<SelectItem> bankOption;
    //
    private List<SelectItem> regTypeOption;
    Date date = new Date();
    boolean custNameDisable = false;
    private String middleName;
    private String lastName;
    private String spouseName;
    private String maidenName;
    private String nationality;
    private List<SelectItem> nationalityList;
    private boolean companyDisable;
    private String disableMinorGrd = "none";
    private String guardianCustId;
    private String guardianCustName;
    private Date minorMajorityDate;
    Pattern onlyAlphabetWitSpace = Pattern.compile("[A-Za-z ]*");
    Pattern onlyAlphabetWitSpaceAndSplChar = Pattern.compile("[A-Za-z &/.]*");
    Pattern onlyAlphabetWitoutSpace = Pattern.compile("[A-Za-z]*");
    Pattern onlyNumeric = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
    private CustomerManagementFacadeRemote customerRemote = null;
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private boolean namesDisable;
    //Change on 28/10/2015
    private String titleChgFlag;
    private String custNameChgFlag;
    private String middleNameChgFlag;
    private String lastNameChgFlag;
    private String genderChgFlag;
    private String fatherNameChgFlag;
    private String motherNameChgFlag;
    private String dobChgFlag;
    private String maritalStatusChgFlag;
    private String spouseChgFlag;
    private String maidenChgFlag;
    private String staffChgFlag;
    private String staffNoChgFlag;
    private String nationalityChgFlag;
    private String residenceStatusChgFlag;
    private String mobileNoChgFlag;
    private String commPrefChgFlag;
    private String panGirNoChgFlag;
    private String minorChgFlag;
    private String grdCustIdChgFlag;
    private String minorMajorDtChgFlag;
    private String regTypeChgFlag;
    private String adhaarNoChgFlag;
    private String lpgNoChgFlag;
    private String primaryAcChgFlag;
    private String manDtFlagChgFlag;
    private String manDatChgFlag;
    private String gstinChgFlag;
    //Addition on 05/01/2017
    private List<SelectItem> custEntityTypeList;
    private List<SelectItem> acHolderTypeFlagList;
    private List<SelectItem> acHolderTypeList;
    private List<SelectItem> acTypeList;
    private String custEntityType;
    private String acHolderTypeFlag;
    private String acHolderType;
    private String acType;
    private String cKYCNo;
    private String fatherMiddleName;
    private String fatherLastName;
    private String spouseMiddleName;
    private String spouseLastName;
    private String motherMiddleName;
    private String motherLastName;
    private String isdCode;
    private String custEntityTypeChgFlag;
    private String acHolderTypeFlagChgFlag;
    private String acHolderTypeChgFlag;
    private String acTypeChgFlag;
    private String fatherMiddleNameChgFlag;
    private String fatherLastNameChgFlag;
    private String spouseMiddleNameChgFlag;
    private String spouseLastNameChgFlag;
    private String motherMiddleNameChgFlag;
    private String motherLastNameChgFlag;
    private String isdCodeChgFlag;
    // add by rahul
    private String modeTypeChgFlag;
    private String bankChgFlag;
    //
    AdvancesInformationTrackingRemote aitr;
    boolean fatherSpouseFlag = false;
    boolean fatherSpouseFlagDisable;
    boolean custFullNameMismatchFlag;
//    private OperationCustomerMaster operationCustomerMaster;
    private String dobOrIncorpLebal;

    //end
    public CustPersonalDetails() {
        try {
            customerRemote = (CustomerManagementFacadeRemote) ServiceLocator.getInstance().lookup("CustomerManagementFacade");
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            masterDelegate = new CustomerMasterDelegate();
            staffTypeOption = new ArrayList<SelectItem>();
            staffTypeOption.add(new SelectItem("0", "--Select--"));
            staffTypeOption.add(new SelectItem("Y", "Yes"));
            staffTypeOption.add(new SelectItem("N", "No"));
            List<CbsRefRecTypeTO> functionList = null;
            genderTypeOption = new ArrayList<SelectItem>();
            genderTypeOption.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("012");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                genderTypeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            maritalTypeOption = new ArrayList<SelectItem>();
            maritalTypeOption.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("152");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                maritalTypeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

            minorTypeOption = new ArrayList<SelectItem>();
            minorTypeOption.add(new SelectItem("0", "--Select--"));
            minorTypeOption.add(new SelectItem("Y", "Yes"));
            minorTypeOption.add(new SelectItem("N", "No"));

            nriTypeOption = new ArrayList<SelectItem>();
            nriTypeOption.add(new SelectItem("0", "--Select--"));
//            nriTypeOption.add(new SelectItem("0", "--Select--"));
//            nriTypeOption.add(new SelectItem("Y", "Yes"));
//            nriTypeOption.add(new SelectItem("N", "No"));
            functionList = masterDelegate.getFunctionValues("303");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                nriTypeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            if (!functionList.isEmpty()) {
                this.setNriType("0");
            }

            commuPreferenceOption = new ArrayList<SelectItem>();
            commuPreferenceOption.add(new SelectItem("0", "--Select--"));
            commuPreferenceOption.add(new SelectItem("C", "Correspondence"));
            commuPreferenceOption.add(new SelectItem("E", "E-mail"));
            commuPreferenceOption.add(new SelectItem("P", "Permanent"));

            chargeTurnOverFlagOption = new ArrayList<SelectItem>();
            chargeTurnOverFlagOption.add(new SelectItem("0", "--Select--"));
            chargeTurnOverFlagOption.add(new SelectItem("Y", "Yes"));
            chargeTurnOverFlagOption.add(new SelectItem("N", "No"));

            pureAllowedFlagOption = new ArrayList<SelectItem>();
            pureAllowedFlagOption.add(new SelectItem("0", "--Select--"));
            pureAllowedFlagOption.add(new SelectItem("Y", "Yes"));
            pureAllowedFlagOption.add(new SelectItem("N", "No"));

            custStatusInPersnlInfoOption = new ArrayList<SelectItem>();
            custStatusInPersnlInfoOption.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("025");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                custStatusInPersnlInfoOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                        recTypeTO.getRefDesc()));
            }

            preferedLanguageOption = new ArrayList<SelectItem>();
            preferedLanguageOption.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("206");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                preferedLanguageOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                        recTypeTO.getRefDesc()));
            }

            panNoOptionList = new ArrayList<SelectItem>();
            panNoOptionList.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("356");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                panNoOptionList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                        recTypeTO.getRefDesc()));
            }

            mandateFlagOption = new ArrayList<SelectItem>();
            mandateFlagOption.add(new SelectItem(""));
            mandateFlagOption.add(new SelectItem("N"));
            mandateFlagOption.add(new SelectItem("Y"));

            regTypeOption = new ArrayList<SelectItem>();
            regTypeOption.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("007");
            for (CbsRefRecTypeTO regTo : functionList) {
                regTypeOption.add(new SelectItem(regTo.getCbsRefRecTypePKTO().getRefCode(), regTo.getRefDesc()));
            }
            //add by Rahul
            modeTypeOption = new ArrayList<SelectItem>();
            modeTypeOption.add(new SelectItem("0", "--Select--"));
            modeTypeOption.add(new SelectItem("F", "Fresh"));
            modeTypeOption.add(new SelectItem("R", "ReUpdation"));

            this.bankChgFlag = "none";
            bankOption = new ArrayList<>();
            List optionList = aitr.getBankiinDetail();
            bankOption.add(new SelectItem("", "--Select--"));
            for (int i = 0; i < optionList.size(); i++) {
                Vector ele7 = (Vector) optionList.get(i);
                bankOption.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
            }

            nationalityList = new ArrayList<SelectItem>();
            nationalityList.add(new SelectItem("0", "--Select--"));
            functionList = masterDelegate.getFunctionValues("302");
            for (CbsRefRecTypeTO regTo : functionList) {
                nationalityList.add(new SelectItem(regTo.getCbsRefRecTypePKTO().getRefCode(), regTo.getRefDesc()));
            }
            if (!functionList.isEmpty()) {
                this.nationality = "IN";
            }
            custEntityTypeList = new ArrayList<SelectItem>();
            custEntityTypeList.add(new SelectItem("0", "--Select--"));
            custEntityTypeList.add(new SelectItem("01", "Individual"));
            custEntityTypeList.add(new SelectItem("02", "Legal Entity"));
            custEntityTypeList.add(new SelectItem("03", "Not Categorized"));

            isdCode = "91";
            fatherSpouseFlagDisable = true;
//            custFullNameMismatchFlag = true;
            dobOrIncorpLebal = "Date Of Birth";
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public void onChangeCustEntityType() {
        try {
            List<CbsRefRecTypeTO> functionList = null;
            List optionlist = new ArrayList();

            custGeneralInfo.setCustomerEntityGn(this.custEntityType);

            custGeneralInfo.setIncomeRangeList(new ArrayList<SelectItem>());
            custGeneralInfo.getIncomeRangeList().add(new SelectItem("0", "--Select--"));
            //Addition on 23/11/2015
            custGeneralInfo.setLegelPoiDocList(new ArrayList<SelectItem>());
            custGeneralInfo.getLegelPoiDocList().add(new SelectItem("0", "--Select--"));
            //End here
            //Addition on 05/01/2017
            custAddressInfo.setPrefPoaAddList(new ArrayList<SelectItem>());
            custAddressInfo.getPrefPoaAddList().add(new SelectItem("0", "--Select--"));
            custAddressInfo.setMailPoaList(new ArrayList<SelectItem>());
            custAddressInfo.getMailPoaList().add(new SelectItem("0", "--Select--"));
            custAddressInfo.setJuriPoaList(new ArrayList<SelectItem>());
            custAddressInfo.getJuriPoaList().add(new SelectItem("0", "--Select--"));
            titleTypeOption = new ArrayList<SelectItem>();
            titleTypeOption.add(new SelectItem("0", "--Select--"));
            acHolderTypeFlagList = new ArrayList<SelectItem>();
            acHolderTypeFlagList.add(new SelectItem("0", "--Select--"));
            acHolderTypeList = new ArrayList<SelectItem>();
            acHolderTypeList.add(new SelectItem("0", "--Select--"));
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("0", "--Select--"));
            //End here
            relatedInfo.setRelPersonTypeList(new ArrayList<SelectItem>());

            optionlist = aitr.getListAsPerRequirement("045", this.custEntityType, "0", "0", "0", "0", ymd.format(new Date()),0);//title list of customer
            for (int i = 0; i < optionlist.size(); i++) {
                Vector ele6 = (Vector) optionlist.get(i);
                titleTypeOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
            }
            optionlist = aitr.getListAsPerRequirement("312", this.custEntityType, "POA", "0", "0", "0",ymd.format(new Date()),0); //POA Proof doc
            for (int i = 0; i < optionlist.size(); i++) {
                Vector ele6 = (Vector) optionlist.get(i);
                custAddressInfo.getPrefPoaAddList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                custAddressInfo.getMailPoaList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                custAddressInfo.getJuriPoaList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
            }

            List optionlist1 = aitr.getListAsPerRequirement("044", this.custEntityType, "CONS", "0", "0", "0",ymd.format(new Date()),0);//Constitution type of customer list
            misInfo.setMisConstitutionOption(new ArrayList<SelectItem>());
            misInfo.getMisConstitutionOption().add(new SelectItem("", "--Select--"));
            for (int i = 0; i < optionlist1.size(); i++) {
                Vector ele6 = (Vector) optionlist1.get(i);
                misInfo.getMisConstitutionOption().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
            }
            optionlist = aitr.getListAsPerRequirement("362", this.getCustEntityType(), "DOC", "0", "0", "0",ymd.format(new Date()),0);
            misInfo.setCustImageCaptureList(new ArrayList<SelectItem>());
            misInfo.setCustImageCaptureDesc(new HashMap<String, String>());
            for (int i = 0; i < optionlist.size(); i++) {
                Vector ele6 = (Vector) optionlist.get(i);
                misInfo.getCustImageCaptureDesc().put(ele6.get(0).toString(), ele6.get(1).toString());
                misInfo.getCustImageCaptureList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
            }
            if (this.custEntityType.equalsIgnoreCase("02")) {              //Treated as company or legal entity
                this.companyDisable = true;
                custGeneralInfo.setCustGeneralInfoComDisable(false);
                custGeneralInfo.setCustGeneralInfoIndDisable(true);
                misInfo.setMisCompDisable(false);
                misInfo.setMisIndDisable(true);
                relatedInfo.setRelIndView("none");
                relatedInfo.setRelComView("");
                misInfo.setMisConstitution("");

                functionList = masterDelegate.getFunctionValues("358"); //Account holder type flag list
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    acHolderTypeFlagList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                }
                if (!functionList.isEmpty()) {
                    this.setAcHolderTypeFlag("02");// to add other reportable by default in ac Holder Type Flag
                }
                optionlist = aitr.getListAsPerRequirement("359", this.custEntityType, this.acHolderTypeFlag, "0", "0", "0",ymd.format(new Date()),0);//ac Holder Type
                acHolderTypeList = new ArrayList<SelectItem>();
                acHolderTypeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    acHolderTypeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }

                if (!optionlist.isEmpty()) {
                    this.setAcHolderType("C2");// to add other reportable by default in ac Holder Type
                }

                //Setting Drop Down of Cust General Info
                functionList = masterDelegate.getFunctionValues("306"); //Legal Income Range
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    custGeneralInfo.getIncomeRangeList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }

                //Addition on 23/11/2015
                functionList = masterDelegate.getFunctionValues("304"); //Company POI Proof doc
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    custGeneralInfo.getLegelPoiDocList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }
                //End here

                //Setting Drop Down of Realted Info
                relatedInfo.getRelPersonTypeList().add(new SelectItem("0", "--Select--"));
                functionList = masterDelegate.getFunctionValues("310"); //Related Person Type-Legal
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    relatedInfo.getRelPersonTypeList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }

                misInfo.setMisOccupationOption(new ArrayList<SelectItem>());//Occupation type for legal entity
                misInfo.getMisOccupationOption().add(new SelectItem("0", ""));
                dobOrIncorpLebal = "Date Of Incorporation";
            } else if (this.custEntityType.equalsIgnoreCase("01")) {
                dobOrIncorpLebal = "Date Of Birth";
                //Treated as Individual
                this.companyDisable = false;
                custGeneralInfo.setCustGeneralInfoComDisable(true);
                custGeneralInfo.setCustGeneralInfoIndDisable(false);
                misInfo.setMisCompDisable(true);
                misInfo.setMisIndDisable(false);
                relatedInfo.setRelIndView("");
                relatedInfo.setRelComView("none");

                acHolderTypeFlagList = new ArrayList<SelectItem>();
                acHolderTypeFlagList.add(new SelectItem("", ""));
                acHolderTypeList = new ArrayList<SelectItem>();
                acHolderTypeList.add(new SelectItem("", ""));
                relatedInfo.setRelPersonTypeList(new ArrayList<SelectItem>());

                if (!optionlist1.isEmpty()) {
                    misInfo.setMisConstitution("01");
                }
                functionList = masterDelegate.getFunctionValues("361"); //Account type list
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    acTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                }
                functionList = masterDelegate.getFunctionValues("305"); //Individual Income Range
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    custGeneralInfo.getIncomeRangeList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }

                relatedInfo.getRelPersonTypeList().add(new SelectItem("0", "--Select--"));
                functionList = masterDelegate.getFunctionValues("309"); //Related Person Type-IND
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    relatedInfo.getRelPersonTypeList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }

                misInfo.setMisOccupationOption(new ArrayList<SelectItem>());//Occupation type for legal entity
                misInfo.getMisOccupationOption().add(new SelectItem("0", "--Select--"));
                functionList = masterDelegate.getFunctionValues("021");
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    misInfo.getMisOccupationOption().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                }

            }
            //Related Person Information
//            relatedInfo.setFieldDisable(true);
//            relatedInfo.setOtherFieldDisable(true);
//            relatedInfo.setSelVisible(false);
//            relatedInfo.setSelVisible(false);
//            if (getCustomerDetail().getFunction().equals("M")) {
//                relatedInfo.setFieldDisable(false);
//                relatedInfo.setOtherFieldDisable(false);
//
//                //Retrieve all related Persons
//                relatedInfo.setRpiTableList(customerMgmtRemote.getAllRelatedPersons(getCustomerDetail().getCustId()));
//                relatedInfo.setSelVisible(true);
//                relatedInfo.setSelVisible(true);
//            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public void onChangeMaritalStatus() {
        if (maritalType.equalsIgnoreCase("01")) {
            fatherSpouseFlagDisable = false;
        } else if (maritalType.equalsIgnoreCase("02")) {
            fatherSpouseFlagDisable = true;
            fatherSpouseFlag = false;

        }
    }

    public void onChangeFatherSpouseFlag() {
        if (fatherSpouseFlag == true) {
        }
    }

    public void onChangeAccType() {
        try {
            if (custEntityType.equalsIgnoreCase("01")) {
                List optionlist = aitr.getListAsPerRequirement("311", this.custEntityType, this.acType, "0", "0", "0",ymd.format(new Date()),0);//Ind POI Proof doc
                custGeneralInfo.setLegelPoiDocList(new ArrayList<SelectItem>());
                custGeneralInfo.getLegelPoiDocList().add(new SelectItem("", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    custGeneralInfo.getLegelPoiDocList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public void onChangeAccHolderTypeFlag() {
        try {
            if (custEntityType.equalsIgnoreCase("02")) {
                List optionlist = aitr.getListAsPerRequirement("359", this.custEntityType, this.acHolderTypeFlag, "0", "0", "0",ymd.format(new Date()),0);//ac Holder Type
                acHolderTypeList = new ArrayList<SelectItem>();
                acHolderTypeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    acHolderTypeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }

                if (!optionlist.isEmpty()) {
                    this.setAcHolderType("C2");// to add other reportable by default in ac Holder Type
                }
            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    public String validateFirstName() {
        getCustomerDetail().setErrorMsg("");
        if (this.custEntityType.equalsIgnoreCase("01")) {
            if (this.custFirstName == null || this.custFirstName.equals("") || this.custFirstName.trim().length() == 0) {
                getCustomerDetail().setErrorMsg("Please Fill First Name On Personal Information");
                return "Please Fill First Name On Personal Information";
            }
            Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.custFirstName);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please Fill First Name(In Only Alphabet) On Personal Information");
                return "Please Fill First Name(In Only Alphabet) On Personal Information";
            }
        } else {
            if (this.custFirstName == null || this.custFirstName.equals("") || this.custFirstName.trim().length() == 0) {
                getCustomerDetail().setErrorMsg("Please Fill First Name On Personal Information");
                return "Please Fill First Name On Personal Information";
            }
            Pattern onlyAlphabetWitSpaceAndNumber = Pattern.compile("[A-Za-z0-9 ]*");
            Matcher matcher = onlyAlphabetWitSpaceAndNumber.matcher(this.custFirstName);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please Fill First Name(In Only Alphabet) On Personal Information");
                return "Please Fill First Name(In Only Alphabet) On Personal Information";
            }
        }
        return "true";
    }

    public String validateMiddleName() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.middleName == null || this.middleName.trim().equals("") || this.middleName.trim().length() == 0)) {
            Matcher matcher = onlyAlphabetWitSpace.matcher(this.middleName);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please Fill Proper Middle Name(In Only Alphabet) On Personal Information");
                return "Please Fill Proper Middle Name(In Only Alphabet) On Personal Information";
            }
//            int n = this.middleName.trim().indexOf(" ");
//            if (n != -1) {
//                getCustomerDetail().setErrorMsg("Spaces Are Not Allowed In Middle Name(In Only Alphabet) On Personal Information");
//                return "Spaces Are Not Allowed In Middle Name(In Only Alphabet) On Personal Information";
//            }
        }
        return "true";
    }

    public String validateLastName() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.lastName == null || this.lastName.trim().equals("") || this.lastName.trim().length() == 0)) {
            Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.lastName);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please Fill Proper Last Name(In Only Alphabet) On Personal Information");
                return "Please Fill Proper Last Name(In Only Alphabet) On Personal Information";
            }
        }
//            getCustomerDetail().setErrorMsg("Please Fill Customer Last Name On Personal Information");
//            return "Please Fill Customer Last Name On Personal Information";
//        } else {
//            int n = this.lastName.trim().indexOf(" ");
//            if (n != -1) {
//                getCustomerDetail().setErrorMsg("Spaces Are Not Allowed In Last Name(In Only Alphabet) On Personal Information");
//                return "Spaces Are Not Allowed In Last Name(In Only Alphabet) On Personal Information";
//            }
//            Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.lastName);
//            if (!matcher.matches()) {
//                getCustomerDetail().setErrorMsg("Please Fill Proper Last Name(In Only Alphabet) On Personal Information");
//                return "Please Fill Proper Last Name(In Only Alphabet) On Personal Information";
//            }
//        }
        return "true";
    }

    public String validateCustomerFullName() {
        getCustomerDetail().setErrorMsg("");
        if (!(this.custFullName == null || this.custFullName.trim().equals("") || this.custFullName.trim().length() == 0)) {
            Matcher matcher = onlyAlphabetWitSpace.matcher(this.custFullName);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please Fill Proper Name(In Only Alphabet) On Personal Information");
                return "Please Fill Proper Name(In Only Alphabet) On Personal Information";
            }
//            String custConcatFullName = this.custFirstName == null ? "" : this.custFirstName + " " + this.middleName == null ? "" : this.middleName + " " + this.lastName == null ? "" : this.lastName;
//            custFullNameMismatchFlag = new Siplmatcher().equals(this.custFullName, custConcatFullName);
//            if (custFullNameMismatchFlag) {
//            }
        }
        return "true";
    }

    public boolean getcustomerFullNameMismatchFlag() {
        String custConcatFullName = (this.custFirstName == null ? "" : this.custFirstName).trim() + " " + (this.middleName == null ? "" : this.middleName).trim();
        custConcatFullName = custConcatFullName.trim() + " " + (this.lastName == null ? "" : this.lastName).trim();
        this.custFullNameMismatchFlag = this.custFullName.equalsIgnoreCase(custConcatFullName.trim());
        return custFullNameMismatchFlag;
    }

    public String validateGender() {
        getCustomerDetail().setErrorMsg("");
        if (this.genderType == null || this.genderType.equals("0") || this.genderType.equals("")) {
            getCustomerDetail().setErrorMsg("Please Select Gender On Personal Information");
            return "Please Select Gender On Personal Information";
        }
        if (this.custEntityType.endsWith("01") && (this.genderType.equals("O"))) {
            getCustomerDetail().setErrorMsg("Gender not be Other for Individual On Personal Information");
            return "Gender not be Other for Individual On Personal Information";
        }
        if (this.custEntityType.endsWith("02") && (this.genderType.equals("M") || this.genderType.equals("F") || this.genderType.equals("T"))) {
            getCustomerDetail().setErrorMsg("Please Select Gender As Other For Legal Entity On Personal Information");
            return "Please Select Gender As Other For Legal Entity On Personal Information";
        }
        return "true";
    }

    public String validateFatherName() {
        getCustomerDetail().setErrorMsg("");
        if (this.custEntityType.equalsIgnoreCase("01")) {
            if ((this.fathername == null || this.fathername.trim().length() == 0 || this.fathername.trim().equals(""))) {
                getCustomerDetail().setErrorMsg("Please Fill Father First Name On Personal Information");
                return "Please Fill Father First Name On Personal Information";
            } else {
                Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.fathername);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Father First Name (In Only Alphabet) On Personal Information");
                    return "Please Fill Proper Father First Name (In Only Alphabet) On Personal Information";
                }
            }
            if (!(this.fatherMiddleName == null || this.fatherMiddleName.trim().length() == 0 || this.fatherMiddleName.trim().equals(""))) {
                Matcher matcher = onlyAlphabetWitSpace.matcher(this.fatherMiddleName);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Father Middle Name(In Only Alphabet) On Personal Information");
                    return "Please Fill Proper Father Middle Name(In Only Alphabet) On Personal Information";
                }
            }
            if (!(this.fatherLastName == null || this.fatherLastName.trim().length() == 0 || this.fatherLastName.trim().equals(""))) {
                Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.fatherLastName);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Father Last Name(In Only Alphabet) On Personal Information");
                    return "Please Fill Proper Father Last Name(In Only Alphabet) On Personal Information";
                }
            }
        }
        return "true";
    }

    public String validateMotherName() {
        getCustomerDetail().setErrorMsg("");
        if (this.custEntityType.equalsIgnoreCase("01")) {
            if ((this.motherName == null || this.motherName.trim().length() == 0 || this.motherName.trim().equals(""))) {
                getCustomerDetail().setErrorMsg("Please Fill Mother First Name On Personal Information");
                return "Please Fill Mother First Name On Personal Information";
            } else {
                Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.motherName);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Spaces Are Not Allowed In Mother First Name(In Only Alphabet) On Personal Information");
                    return "Spaces Are Not Allowed In Mother First Name(In Only Alphabet) On Personal Information";
                }
            }
            if (!(this.motherMiddleName == null || this.motherMiddleName.trim().length() == 0 || this.motherMiddleName.trim().equals(""))) {
                Matcher matcher = onlyAlphabetWitSpace.matcher(this.motherMiddleName);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Mother Middle Name(In Only Alphabet) On Personal Information");
                    return "Please Fill Proper Mother Middle Name(In Only Alphabet) On Personal Information";
                }
            }
            if (!(this.motherLastName == null || this.motherLastName.trim().length() == 0 || this.motherLastName.trim().equals(""))) {
                Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.motherLastName);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Mother Last Name(In Only Alphabet) On Personal Information");
                    return "Please Fill Proper Mother Last Name(In Only Alphabet) On Personal Information";
                }
            }
        }
        return "true";
    }

    public String validateDateOfBirth() {
        getCustomerDetail().setErrorMsg("");
        try {
            if (this.custEntityType.equalsIgnoreCase("01")) {
                if (this.perDateOfBirth == null) {
                    getCustomerDetail().setErrorMsg("Please Select Date of Birth in Personal Information");
                    dobFlag = "true";
                    return "Please Select Date of Birth in Personal Information";
                }
                int strDateDif = CbsUtil.yearDiff(perDateOfBirth, date);
                if (strDateDif <= 0) {
                    long strDayDiff = CbsUtil.dayDiff(perDateOfBirth, date);
                    if (strDayDiff <= 0) {
                        getCustomerDetail().setErrorMsg("Please select proper date of birth !");
                        return "Please select proper date of birth !";
                    }
                }
                ParameterinfoReportTO to = masterDelegate.getAgeLimit("MINOR-AGE-LIMIT");
                int minorAgeLimit = to.getCode();
                int strDateDiff = CbsUtil.yearDiff(perDateOfBirth, date);
                if (this.minorType.equalsIgnoreCase("Y")) {
                    if (strDateDiff <= 0) {
                        long strDayDiff = CbsUtil.dayDiff(perDateOfBirth, date);
                        if (strDayDiff <= 0) {
                            getCustomerDetail().setErrorMsg("Please select proper date of birth !");
                            return "Please select proper date of birth !";
                        }
                    }
                    if (strDateDiff > 0) {
                        if (strDateDiff >= minorAgeLimit) {
                            getCustomerDetail().setErrorMsg("Minor can't be equal to or greater than " + minorAgeLimit + " years.");
                            return "Minor can't be equal to or greater than " + minorAgeLimit + " years.";
                        }
                    }
                } else if (this.minorType.equalsIgnoreCase("N") && strDateDiff < minorAgeLimit) {
                    getCustomerDetail().setErrorMsg("The Person Who is not Minor can't be less than " + minorAgeLimit + " years.");
                    return "The Person Who is not Minor can't be less than " + minorAgeLimit + " years.";
                }
            } else if (this.custEntityType.equalsIgnoreCase("02")) {
                if (this.perDateOfBirth == null) {
                    getCustomerDetail().setErrorMsg("Please Select Date of Birth as(Date of Incorporation) in Personal Information");
                    dobFlag = "true";
                    return "Please Select Date of Birth as(Date of Incorporation) in Personal Information";
                }
                int strDateDif = CbsUtil.yearDiff(perDateOfBirth, date);
                if (strDateDif <= 0) {
                    long strDayDiff = CbsUtil.dayDiff(perDateOfBirth, date);
                    if (strDayDiff <= 0) {
                        getCustomerDetail().setErrorMsg("Please Select Proper Date of Birth as(Date of Incorporation)!");
                        return "Please Select Proper Date of Birth as(Date of Incorporation) !";
                    }
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
        return "true";
    }

    public String validateMaidenName() {
        getCustomerDetail().setErrorMsg("");
        if (this.custEntityType.equalsIgnoreCase("01")) {
            if (this.maritalType == null || this.maritalType.equalsIgnoreCase("0")) {
                getCustomerDetail().setErrorMsg("Please select Mrital Status in Personal Information Tab !");
                return "Please select Mrital Status in Personal Information Tab !";
            }
            if (this.maritalType.equalsIgnoreCase("01")) {
                if (this.spouseName == null || this.spouseName.trim().length() == 0 || this.spouseName.trim().equals("")) {
                    getCustomerDetail().setErrorMsg("Spouse Name Is Mandatory If Marital Status Is Married");
                    return "Spouse Name Is Mandatory If Marital Status Is Married";
                }
                Matcher matcher = onlyAlphabetWitoutSpace.matcher(this.spouseName);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Spouse First Name On Personal Information");
                    return "Please Fill Proper Spouse First Name On Personal Information";
                }
                matcher = onlyAlphabetWitSpace.matcher(this.spouseMiddleName);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Spouse Middle Name On Personal Information");
                    return "Please Fill Proper Spouse Middle Name On Personal Information";
                }
                matcher = onlyAlphabetWitoutSpace.matcher(this.spouseLastName);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Spouse Last Name On Personal Information");
                    return "Please Fill Proper Spouse Last Name On Personal Information";
                }
                if (this.titleType.equalsIgnoreCase("MRS")) {
                    if (this.maidenName == null || this.maidenName.trim().length() == 0 || this.maidenName.trim().equals("")) {
                        getCustomerDetail().setErrorMsg("Maiden Name Is Mandatory If Marital Status Is Married");
                        return "Maiden Name Is Mandatory If Marital Status Is Married";
                    }
                    matcher = onlyAlphabetWitSpace.matcher(this.maidenName);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please Fill Proper Maiden Name On Personal Information");
                        return "Please Fill Proper Maiden Name On Personal Information";
                    }
                }
            }
        }
        return "true";
    }

    public void onblurStaff() {
        getCustomerDetail().setErrorMsg("");
        this.setMsgForStaff("");
        this.setFlagForsave(true);
        if (this.staffType.equalsIgnoreCase("Y")) {
            this.setFlagForsave(false);
            this.setMsgForStaff("Please Fill The Staff No On Personal Information");
        }
    }

    public String validateStaff() {
        getCustomerDetail().setErrorMsg("");
        if (this.staffType.equalsIgnoreCase("Y")) {
            if (this.staffNo == null || this.staffNo.equalsIgnoreCase("")
                    || this.staffNo.equalsIgnoreCase("0") || this.staffNo.trim().length() == 0) {
                getCustomerDetail().setErrorMsg("Please Fill The Staff No On Personal Information");
                return "Please Fill The Staff No On Personal Information";
            }
        } else if (this.staffType.equalsIgnoreCase("N")) {
            this.setStaffNo("");
        }
        return "true";
    }

    public void onChangeResidentialStatus() {
        getCustomerDetail().setErrorMsg("");
        if (this.nriType.equalsIgnoreCase("N")) { //For only NRI
            flagForsave = false;
//            this.setMsg("Please Fill UIN No.");
//            this.setMsg2("*");
//            this.setMsg3("*");
//            this.setMsg4("*");
//            this.setMsg5("*");
//            this.setMsg6("*");
            nreFlag = true;
            setViewIdForNre("/pages/admin/customer/nreInfo.jsp");
        } else {
//            this.setMsg("");
//            this.setMsg2("");
//            this.setMsg3("");
//            this.setMsg4("");
//            this.setMsg5("");
//            this.setMsg6("");
//            this.setPassportNumber("");
//            this.setPassportIssuingAuthority("");
//            this.setPassportIssuePlace("");
//            this.setPassPortIssuedate(null);
//            this.setPassPortExpirdate(null);
            flagForsave = true;
            nreFlag = false;
        }
    }

    public String validateResidentialStatus() {
        getCustomerDetail().setErrorMsg("");
        if (this.custEntityType.equalsIgnoreCase("01")) {
            if (this.nationality == null || this.nationality.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Nationality  On Personal Information");
                return "Please Select Nationality On Personal Information";
            }
            if (this.nriType == null || this.nriType.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Residential Status On Personal Information");
                return "Please Select Residential Status On Personal Information";
            }
        }
        return "true";
    }

    public String validateMobileNo() {
        getCustomerDetail().setErrorMsg("");
        if ((this.isdCode == null || this.isdCode.trim().length() == 0 || this.isdCode.trim().equals(""))) {
            getCustomerDetail().setErrorMsg("Please Fill  3 Digit  ISD Code On Personal Information");
            return "Please Fill 3 Digit ISD Code On Personal Information";
        } else {
            Matcher matcher = onlyNumeric.matcher(this.isdCode);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please Fill Numeric Value In ISD Code On Personal Information");
                return "Please Fill Numeric Value In ISD Code On Personal Information";
            }
            if (this.isdCode.trim().length() >= 3) {
                getCustomerDetail().setErrorMsg("Please Fill Only 3 Digit In ISD Code On Personal Information");
                return "Please Fill Only 3 Digit In ISD Code On Personal Information";
            }
        }
        if ((this.mobNo == null || this.mobNo.trim().length() == 0 || this.mobNo.trim().equals(""))) {
            getCustomerDetail().setErrorMsg("Please Fill  10 Digit Mobile No On Personal Information");
            return "Please Fill 10 Digit Mobile No On Personal Information";
        } else {
            Matcher matcher = onlyNumeric.matcher(this.mobNo);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please Fill Numeric Value In Mobile No On Personal Information");
                return "Please Fill Numeric Value In Mobile No On Personal Information";
            }
            if (this.mobNo.trim().length() != 10) {
                getCustomerDetail().setErrorMsg("Please Fill Only 10 Digit In Mobile No On Personal Information");
                return "Please Fill Only 10 Digit In Mobile No On Personal Information";
            }
        }
        return "true";
    }

    public String validateCommPref() {
        getCustomerDetail().setErrorMsg("");
        if (this.commuPreference == null || this.commuPreference.equals("0") || this.commuPreference.equals("")) {
            getCustomerDetail().setErrorMsg("Please Select Communication Preference On Personal Information");
            return "Please Select Communication Preference On Personal Information";
        }
        return "true";
    }

//    public void onblurAndValidatePanNo() {
//        getCustomerDetail().setErrorMsg("");
//        try {
//            this.setPanGirNo("");
//            if (this.panNoOption == null || this.panNoOption.equals("")) {
//                getCustomerDetail().setErrorMsg("Please Select Correct Value In PAN/GIR No");
//                return;
//            }
//            if (this.panNoOption.equalsIgnoreCase("Form 60")) {
//                this.setPanGirNo("Form 60");
//                panNoVisibility = "true";
//            } else if (this.panNoOption.equalsIgnoreCase("Form 61")) {
//                this.setPanGirNo("Form 61");
//                panNoVisibility = "true";
//            } else if (this.panNoOption.equalsIgnoreCase("PAN No")) {
//                this.setPanGirNo("");
//                panNoVisibility = "false";
//            }
//            if (this.panGirNo == null || this.panGirNo.equals("")) {
//                getCustomerDetail().setErrorMsg("Please Select Correct Value In PAN/GIR No");
//                return;
//            }
//            if (this.panNoOption.equalsIgnoreCase("PAN No") && this.panGirNo.length() < 10) {
//                getCustomerDetail().setErrorMsg("Please Fill 10 Digit PAN No on Personal Information Tab.");
//                return;
//            }
//        } catch (Exception e) {
//            getCustomerDetail().setErrorMsg(e.getMessage());
//        }
//    }
    public String onblurAndValidatePanNo() {
        getCustomerDetail().setErrorMsg("");
        try {
//            this.setPanGirNo("");
            if (this.panNoOption == null || this.panNoOption.equals("") || this.panNoOption.equals("0")) {
                getCustomerDetail().setErrorMsg("Please select correct value in PAN/GIR No on Personal Information Tab");
                return "Please select correct value in PAN/GIR No on Personal Information Tab";
            }
            if (this.panNoOption.equalsIgnoreCase("Form 60")) {
                this.setPanGirNo("Form 60");
                panNoVisibility = "true";
            } else if (this.panNoOption.equalsIgnoreCase("Form 61")) {
                this.setPanGirNo("Form 61");
                panNoVisibility = "true";
            } else if (this.panNoOption.equalsIgnoreCase("NA")) {
                this.setPanGirNo("NA");
                panNoVisibility = "true";
            } else if (this.panNoOption.equalsIgnoreCase("PAN No")) {
//                this.setPanGirNo("");
                panNoVisibility = "false";
                getCustomerDetail().setErrorMsg("Please fill 10 PAN/GIR No on Personal Information Tab");
            }
//            if (this.panGirNo == null || this.panGirNo.equals("")) {
//                getCustomerDetail().setErrorMsg("Please Select Correct Value In PAN/GIR No");
//                return "Please Select Correct Value In PAN/GIR No";
//            }
            if (this.panNoOption.equalsIgnoreCase("PAN No")) {
                if (this.panGirNo == null || this.panGirNo.equals("") || this.panGirNo.length() != 10) {
                    getCustomerDetail().setErrorMsg("Please fill 10 digit PAN/GIR No on Personal Information Tab");
                    return "Please fill 10 digit PAN/GIR No on Personal Information Tab";
                }
                //Pan No Validation.
                if (!ParseFileUtil.isAlphabet(this.panGirNo.substring(0, 5))) {
                    getCustomerDetail().setErrorMsg("Please fill proper 10 digit PAN/GIR No on Personal Information Tab");
                    return "Please fill proper 10 digit PAN/GIR No on Personal Information Tab";
                }
                if (!ParseFileUtil.isNumeric(this.panGirNo.substring(5, 9))) {
                    getCustomerDetail().setErrorMsg("Please fill proper 10 digit PAN/GIR No on Personal Information Tab");
                    return "Please fill proper 10 digit PAN/GIR No on Personal Information Tab";
                }
                if (!ParseFileUtil.isAlphabet(this.panGirNo.substring(9))) {
                    getCustomerDetail().setErrorMsg("Please fill proper 10 digit PAN/GIR No on Personal Information Tab");
                    return "Please fill proper 10 digit PAN/GIR No on Personal Information Tab";
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
        return "true";
    }

    public void onChangeMinor() {
        getCustomerDetail().setErrorMsg("");
//        this.guardianCustName = "";
//        this.guardianCustId = "";
        if (this.minorType.equalsIgnoreCase("Y")) {
            this.disableMinorGrd = "";
        } else if (this.minorType.equalsIgnoreCase("N")) {
            this.disableMinorGrd = "none";
        }
    }

    public void onblurGuardianCustId() {
        this.guardianCustName = "";
        try {
            this.guardianCustName = customerRemote.getCustomerName(this.guardianCustId);
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public String validateMinor() {
        getCustomerDetail().setErrorMsg("");
        try {
            if (this.minorType == null || this.minorType.equalsIgnoreCase("0")) {
                getCustomerDetail().setErrorMsg("Please Select Minor On Personal Information Tab");
                return "Please Select Minor On Personal Information Tab";
            }

            if (this.minorType.equalsIgnoreCase("Y")) {
                if (this.guardianCustId == null || this.guardianCustId.trim().equals("")
                        || this.guardianCustId.trim().length() == 0) {
                    getCustomerDetail().setErrorMsg("Please Fill Guardian Customer Id On Personal Information Tab");
                    return "Please Fill Guardian Customer Id On Personal Information Tab";
                }
                if (this.guardianCustName == null || this.guardianCustName.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Fill Proper Guardian Customer Id On Personal Information Tab");
                    return "Please Fill Proper Guardian Customer Id On Personal Information Tab";
                }
                if (this.minorMajorityDate == null) {
                    getCustomerDetail().setErrorMsg("Please Fill Minor Majority Date On Personal Information Tab");
                    return "Please Fill Minor Majority Date On Personal Information Tab";
                }
                if (ymd.parse(ymd.format(this.minorMajorityDate)).compareTo(ymd.parse(ymd.format(date))) <= 0) {
                    getCustomerDetail().setErrorMsg("Minor Majority Date On Personal Information Tab should be greater than current date");
                    return "Minor Majority Date On Personal Information Tab should be greater than current date";
                }
            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        return "true";
    }

    public String onblurValidateGstin() {
        getCustomerDetail().setErrorMsg("");
        try {
            if (!(this.gstIdentificationNumber == null
                    || this.gstIdentificationNumber.equalsIgnoreCase(""))) {

                if (this.gstIdentificationNumber.length() != 15) {
                    getCustomerDetail().setErrorMsg("Please fill 15 digit GST Identification Number on Personal Information Tab");
                    return "Please fill 10 digit GST Identification Number on Personal Information Tab";
                }
                if (!ParseFileUtil.isNumeric(this.gstIdentificationNumber.substring(0, 2))) {
                    getCustomerDetail().setErrorMsg("The first two digits of this number should be state code in GST Identification Number on Personal Information Tab");
                    return "The first two digits of this number should be state code in GST Identification Number on Personal Information Tab";
                }
                //Pan No Validation.
                String panNo = this.gstIdentificationNumber.substring(2, 12);
                if (!ParseFileUtil.isAlphabet(panNo.substring(0, 5))) {
                    getCustomerDetail().setErrorMsg("Please fill proper 10 digit PAN/GIR No with GSTIN on Personal Information Tab");
                    return "Please fill proper 15 digit GST Identification Number on Personal Information Tab";
                }
                if (!ParseFileUtil.isNumeric(panNo.substring(5, 9))) {
                    getCustomerDetail().setErrorMsg("Please fill proper 10 digit PAN/GIR No with GSTIN on Personal Information Tab");
                    return "Please fill proper 15 digit GST Identification Number on Personal Information Tab";
                }
                if (!ParseFileUtil.isAlphabet(panNo.substring(9))) {
                    getCustomerDetail().setErrorMsg("Please fill proper 10 digit PAN/GIR No with GSTIN on Personal Information Tab");
                    return "Please fill proper 15 digit GST Identification Number on Personal Information Tab";
                }
                if (!this.gstIdentificationNumber.substring(13, 14).equalsIgnoreCase("Z")) {
                    //The fourteenth digit will be Z by default
                    getCustomerDetail().setErrorMsg("Please fill the fourteenth digit of GSTIN should be Z on Personal Information Tab");
                    return "Please fill proper 15 digit GST Identification Number on Personal Information Tab";
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
        return "true";
    }

    public String onblurAadhaarDetails() {
        getCustomerDetail().setErrorMsg("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            if (this.regType == null || this.regType.equals("0")) {
                if (getCustomerDetail().getFunction().equalsIgnoreCase("A")) {
                    this.regType = "0";
                    this.adhaarNo = "";
                    this.lpgId = "";
                    this.adhaarLpgAcno = "";
                    this.mandateFlag = "";
                    this.mandateDt = null;
                    this.aadhaarMode = "";
                    this.aadhaarBankIin = "";
                } else if (getCustomerDetail().getFunction().equalsIgnoreCase("M")) {
                    CBSCustomerMasterDetailTO custTo = masterDelegate.getCustDetailsByCustId(customerDetail.getCustId());
                    String custRegType = custTo.getRegType() == null ? "0" : custTo.getRegType();
                    if (!custRegType.equalsIgnoreCase("0") && this.regType.equals("0")) {
                        getCustomerDetail().setErrorMsg("This customer is already registered. So "
                                + "registration type should not be omitted.");
                        return "This customer is already registered. So "
                                + "registration type should not be omitted.";
                    }
                }
            } else {
                if (this.regType.equalsIgnoreCase("AD")) {
                    //Aadhaar Case.
                    if (this.adhaarNo == null || this.adhaarNo.equals("") || adhaarNo.trim().length() != 12) {
                        getCustomerDetail().setErrorMsg("Please Fill 12 digit Aadhaar No.");
                        return "Please Fill 12 digit Aadhaar No.";
                    }
                    Matcher adhaarNoCheck = p.matcher(this.adhaarNo);
                    if (!adhaarNoCheck.matches()) {
                        getCustomerDetail().setErrorMsg("Please Fill Numeric Value in Aadhaar No.");
                        return "Please Fill Numeric Value in Aadhaar No.";
                    }
                    if (!VerhoeffCheckDigit.VERHOEFF_CHECK_DIGIT.isValid(this.adhaarNo)) {

                        getCustomerDetail().setErrorMsg("Please Fill Valid Aadhaar No.");
                        return "Please Fill Valid Aadhaar No.";
                    }
                    if (this.lpgId != null && !this.lpgId.equals("")) {
                        Matcher lpgIdCheck = p.matcher(this.lpgId);
                        if (!lpgIdCheck.matches()) {
                            getCustomerDetail().setErrorMsg("Please Fill Numeric Value in LPG ID.");
                            return "Please Fill Numeric Value in LPG ID.";
                        }
                        if (lpgId.trim().length() != 17) {
                            getCustomerDetail().setErrorMsg("Please Fill 17 digit LPG No.");
                            return "Please Fill 17 digit LPG No.";
                        }
                    }


                    if (getCustomerDetail().getFunction().equalsIgnoreCase("M")) {
                        if (!(this.adhaarLpgAcno == null || this.adhaarLpgAcno.equals(""))) {
                            String result = validateAadhaarAcNo(adhaarLpgAcno);
                            if (!result.equalsIgnoreCase("true")) {
                                getCustomerDetail().setErrorMsg(result);
                                return result;
                            }
                        }
                        List aadharRegistrationDetail = aitr.getAadhaarDetail(customerDetail.getCustId());
                        if (!aadharRegistrationDetail.isEmpty()) {
                            Vector vec = (Vector) aadharRegistrationDetail.get(0);
                            String Status = vec.get(1).toString();
                            String mappingStatus = vec.get(2).toString();

                            aadharRegistrationDetail = aitr.getCustomerAadhaarDetail(customerDetail.getCustId());
                            vec = (Vector) aadharRegistrationDetail.get(0);
                            String regType = vec.get(0).toString();
                            String aadharNo = vec.get(1).toString().trim();
                            if (regType.equalsIgnoreCase("AD") && !aadharNo.equalsIgnoreCase(this.adhaarNo.trim())) {
                                if ((Status.equalsIgnoreCase("D") && mappingStatus.equalsIgnoreCase(""))
                                        || (Status.equalsIgnoreCase("R") && mappingStatus.equalsIgnoreCase(""))) {
                                    getCustomerDetail().setErrorMsg("Aadhaar No. can not be modified.");
                                    return "Aadhaar can not be modified.";
                                } else if (Status.equalsIgnoreCase("D") && mappingStatus.equalsIgnoreCase("I")) {
                                    getCustomerDetail().setErrorMsg("Aadhaar can not be modified.");
                                    return "Aadhaar can not be modified.";
                                } else if ((Status.equalsIgnoreCase("U") || Status.equalsIgnoreCase("E"))
                                        && mappingStatus.equalsIgnoreCase("I")) {
                                    getCustomerDetail().setErrorMsg("Aadhaar can not be modified.");
                                    return "Aadhaar can not be modified.";
                                }
                            }
                        }
                        if (aadhaarMode.equalsIgnoreCase("R") && (aadhaarBankIin == null || aadhaarBankIin.equalsIgnoreCase("0")
                                || aadhaarBankIin.equalsIgnoreCase(""))) {
                            getCustomerDetail().setErrorMsg("Please Select the AadhaarBank in Personal Information");
                            return "Please Select the AadhaarBank in Personal Information";
                        }
                    }
                } else if (this.regType.equalsIgnoreCase("NA")) {
                    //Non-Aadhaar Case.
                    if (this.lpgId == null || this.lpgId.equals("") || lpgId.trim().length() != 17) {
                        getCustomerDetail().setErrorMsg("Please Fill 17 digit LPG No.");
                        return "Please Fill 17 digit LPG No.";
                    }
                    Matcher lpgIdCheck = p.matcher(this.lpgId);
                    if (!lpgIdCheck.matches()) {
                        getCustomerDetail().setErrorMsg("Please Fill Numeric Value in LPG ID.");
                        return "Please Fill Numeric Value in LPG ID.";
                    }
                    if (this.adhaarNo != null && !this.adhaarNo.equals("")) {
                        Matcher adhaarNoCheck = p.matcher(this.adhaarNo);
                        if (!adhaarNoCheck.matches()) {
                            getCustomerDetail().setErrorMsg("Please Fill Numeric Value in Aadhaar No.");
                            return "Please Fill Numeric Value in Aadhaar No.";
                        }
                        if (adhaarNo.length() != 12) {
                            getCustomerDetail().setErrorMsg("Please Fill 12 digit Aadhaar No.");
                            return "Please Fill 12 digit Aadhaar No.";
                        }
                        if (!VerhoeffCheckDigit.VERHOEFF_CHECK_DIGIT.isValid(this.adhaarNo)) {
                            getCustomerDetail().setErrorMsg("Please Fill Valid Aadhaar No.");
                            return "Please Fill Valid Aadhaar No.";
                        }
                    }
                    this.mandateFlag = "";
                    this.mandateDt = null;
                    if (getCustomerDetail().getFunction().equalsIgnoreCase("M")) {
                        //Already Aadhaar Registration.
                        CBSCustomerMasterDetailTO custTo = masterDelegate.getCustDetailsByCustId(customerDetail.getCustId());
                        String custRegType = custTo.getRegType() == null ? "0" : custTo.getRegType();
                        if (custRegType.equalsIgnoreCase("AD")) {
                            getCustomerDetail().setErrorMsg("This customer is already registered with aadhaar. So "
                                    + "registration type should be aadhaar only.");
                            return "This customer is already registered with aadhaar. So "
                                    + "registration type should be aadhaar only.";
                        }
                        //Primary A/c Checking
                        String result = validateAadhaarAcNo(adhaarLpgAcno);
                        if (!result.equalsIgnoreCase("true")) {
                            getCustomerDetail().setErrorMsg(result);
                            return result;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
            return ex.getMessage();
        }
        return "true";
    }

    public String validateAadhaarAcNo(String acno) throws ApplicationException {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            if (acno == null || acno.equals("") || acno.length() == 0) {
                return "Please Fill 12 digit Primary A/c.";
            }
            Matcher adhaarNoCheck = p.matcher(acno);
            if (!adhaarNoCheck.matches()) {
                return "Please Fill Numeric Value in Primary A/c.";
            }
            if (acno.length() != 12) {
                return "Please Fill 12 digit Primary A/c.";
            }
            masterDelegate.isPrimaryAc(getCustomerDetail().getCustId(), acno);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String aadharAndLpgIdDplicateChecking() {
        try {
            if (!(this.adhaarNo == null || this.adhaarNo.equals(""))) {
                masterDelegate.isAadharExists(getCustomerDetail().getCustId(), this.adhaarNo);
            }
            if (!(this.lpgId == null || this.lpgId.equals(""))) {
                masterDelegate.isLpgIdExists(getCustomerDetail().getCustId(), this.lpgId);
            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
            return ex.getMessage();
        }
        return "true";
    }

    public String onChangeMandateDt() {
        try {
            if ((this.regType.equalsIgnoreCase("AD"))) {
                if (this.mandateFlag == null || this.mandateFlag.equals("") || this.mandateFlag.equals("N")) {
                    getCustomerDetail().setErrorMsg("Please Select Proper Mandate Flag in Personal Information.");
                    return "Please Select ProperMandate Flag in Personal Information.";
                }
                if (this.mandateDt == null || this.mandateDt.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Select Mandate Date in Personal Information.");
                    return "Please Select Mandate Date in Personal Information.";
                }
            } else {
                this.mandateDt = null;
            }
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        return "true";
    }

    public String validateAccountHoldertypeFlagAndType() {
        getCustomerDetail().setErrorMsg("");
        if (this.custEntityType.equalsIgnoreCase("02")) {
            if (this.acHolderTypeFlag == null || this.acHolderTypeFlag.equalsIgnoreCase("0")) {
                getCustomerDetail().setErrorMsg("Please Select Account Holder Type Flag In Personal Information");
                return "Please Select Account Holder Type Flag In Personal Information";
            }
            if (this.acHolderType == null || this.acHolderType.equalsIgnoreCase("0")) {
                getCustomerDetail().setErrorMsg("Please Select Account Holder Type In Personal Information");
                return "Please Select Account Holder Type In Personal Information";
            }
        }
        return "true";
    }

    //Added By Rahul
    public void validatemodeType() {
        try {
            getCustomerDetail().setErrorMsg("");
            if (aadhaarMode.equalsIgnoreCase(null) || aadhaarMode.equalsIgnoreCase("")) {
                this.setAadhaarMode("");
            }
            if (aadhaarMode.equalsIgnoreCase("F") || aadhaarMode.equalsIgnoreCase("")) {
                this.bankChgFlag = "none";
            } else {
                this.bankChgFlag = "";
            }



//            if (aadhaarMode.equalsIgnoreCase("R")) {
//                List optionList = aitr.getBankiinDetail();
//                bankOption = new ArrayList<SelectItem>();
//                bankOption.add(new SelectItem("000000", "--Select--"));
//                for (int i = 0; i < optionList.size(); i++) {
//                    Vector ele7 = (Vector) optionList.get(i);
//                    bankOption.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
//                }
//            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }
    //added by Rahul

    public void validateAadhaarBankType() {
        getCustomerDetail().setErrorMsg("");
        if (aadhaarBankIin.equalsIgnoreCase(null) || aadhaarBankIin.equalsIgnoreCase("")) {
            this.setAadhaarBankIin("");
        }

    }

    public String validateAccounttype() {
        getCustomerDetail().setErrorMsg("");
        if (this.custEntityType.equalsIgnoreCase("01")) {
            if (this.acType == null || this.acType.equalsIgnoreCase("0")) {
                getCustomerDetail().setErrorMsg("Please Select Account Type  In Personal Information");
                return "Please Select Account Type In Personal Information";
            }
        }
        return "true";
    }

    public String validation() {
        if (this.custEntityType == null || this.custEntityType.equals("0")) {
            return "Please select customer Type In Personal Information.";
        }
        if (this.custEntityType.equals("03")) {
            return "Please select customer Type as Individual or Legal Entity In Personal Information.";
        }
        if (this.titleType == null || this.titleType.equals("0")) {
            return "Please select customer Title In Personal Information.";
        }

        String valMsg = validateFirstName();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateMiddleName();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateLastName();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
//        valMsg = validateCustomerFullName();
//        if (!valMsg.equalsIgnoreCase("true")) {
//            return valMsg;
//        }
        valMsg = validateFatherName();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateMotherName();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateGender();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateDateOfBirth();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateMaidenName();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateStaff();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateResidentialStatus();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateMobileNo();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateCommPref();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = onblurAndValidatePanNo();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateMinor();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = onblurAadhaarDetails();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = onChangeMandateDt();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = aadharAndLpgIdDplicateChecking();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateAccountHoldertypeFlagAndType();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = validateAccounttype();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }

        return "ok";
    }

//    public void callFromCustFullNameMismatch() {
//        this.custFullNameMismatchFlag = true;
//        operationCustomerMaster.callFromMainButton();
////        this.custFirstName = "";
////        this.middleName = "";
////        this.lastName = "";
//    }
    public void selectFieldValues() {
        List optionlist;
        try {
            CBSCustomerMasterDetailTO custTO = masterDelegate.getCustDetailsByCustId(getCustomerDetail().getCustId());

            //Enable Proof of identity tab fields
            custGeneralInfo.setIncomeRangeList(new ArrayList<SelectItem>());
            custGeneralInfo.getIncomeRangeList().add(new SelectItem("0", "--Select--"));
            relatedInfo.setRelPersonTypeList(new ArrayList<SelectItem>());
            //Addition on 02/01/2017
            custGeneralInfo.setLegelPoiDocList(new ArrayList<SelectItem>());
            custGeneralInfo.getLegelPoiDocList().add(new SelectItem("0", "--Select--"));
            custAddressInfo.setPrefPoaAddList(new ArrayList<SelectItem>());
            custAddressInfo.getPrefPoaAddList().add(new SelectItem("0", "--Select--"));
            custAddressInfo.setMailPoaList(new ArrayList<SelectItem>());
            custAddressInfo.getMailPoaList().add(new SelectItem("0", "--Select--"));
            custAddressInfo.setJuriPoaList(new ArrayList<SelectItem>());
            custAddressInfo.getJuriPoaList().add(new SelectItem("0", "--Select--"));
            titleTypeOption = new ArrayList<SelectItem>();
            titleTypeOption.add(new SelectItem("0", "--Select--"));
            acHolderTypeFlagList = new ArrayList<SelectItem>();
            acHolderTypeFlagList.add(new SelectItem("0", "--Select--"));
            acHolderTypeList = new ArrayList<SelectItem>();
            acHolderTypeList.add(new SelectItem("0", "--Select--"));
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("0", "--Select--"));
            custEntityTypeList = new ArrayList<SelectItem>();
            custEntityTypeList.add(new SelectItem("0", "--Select--"));
            custEntityTypeList.add(new SelectItem("01", "Individual"));
            custEntityTypeList.add(new SelectItem("02", "Legal Entity"));
            custEntityTypeList.add(new SelectItem("03", "Not Categorized"));
            optionlist = aitr.getListAsPerRequirement("044", custTO.getCustEntityType(), "CONS", "0", "0", "0",ymd.format(new Date()),0);// list of Constitution type of individual customer
            misInfo.setMisConstitutionOption(new ArrayList<SelectItem>());
            misInfo.getMisConstitutionOption().add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < optionlist.size(); i++) {
                Vector ele6 = (Vector) optionlist.get(i);
                misInfo.getMisConstitutionOption().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
            }

            optionlist = aitr.getListAsPerRequirement("312", custTO.getCustEntityType(), "POA", "0", "0", "0",ymd.format(new Date()),0); //Company POA Proof doc
            for (int i = 0; i < optionlist.size(); i++) {
                Vector ele6 = (Vector) optionlist.get(i);
                custAddressInfo.getPrefPoaAddList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                custAddressInfo.getMailPoaList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                custAddressInfo.getJuriPoaList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
            }

            optionlist = aitr.getListAsPerRequirement("045", custTO.getCustEntityType(), "0", "0", "0", "0",ymd.format(new Date()),0);//individual customer title list
            for (int i = 0; i < optionlist.size(); i++) {
                Vector ele6 = (Vector) optionlist.get(i);
                titleTypeOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
            }
            //End here
            if (custTO.getCustEntityType().equalsIgnoreCase("02")) {
                custGeneralInfo.setCustGeneralInfoComDisable(false);
                custGeneralInfo.setCustGeneralInfoIndDisable(true);
                misInfo.setMisCompDisable(false);
                misInfo.setMisIndDisable(true);
                relatedInfo.setRelIndView("none");
                relatedInfo.setRelComView("");

                List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("306");
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    custGeneralInfo.getIncomeRangeList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }

                //Setting Drop Down of Realted Info
                relatedInfo.getRelPersonTypeList().add(new SelectItem("0", "--Select--"));
                functionList = masterDelegate.getFunctionValues("310"); //Related Person Type-Legal
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    relatedInfo.getRelPersonTypeList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }
                //Addition on 23/11/2015
                functionList = masterDelegate.getFunctionValues("304"); //Company POI Proof doc
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    custGeneralInfo.getLegelPoiDocList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }
                //Addition on 03/01/2017


                functionList = masterDelegate.getFunctionValues("358"); //Account holder type flag list
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    acHolderTypeFlagList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                }

                optionlist = aitr.getListAsPerRequirement("359", custTO.getCustEntityType(), custTO.getAcHolderTypeFlag(), "0", "0", "0",ymd.format(new Date()),0);//ac Holder Type
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    acHolderTypeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
                this.dobOrIncorpLebal = "Date Of Incorporation";
                //end
                //End here
            } else if (custTO.getCustEntityType().equalsIgnoreCase("01")) {
                this.dobOrIncorpLebal = "Date Of Birth";
                custGeneralInfo.setCustGeneralInfoComDisable(true);
                custGeneralInfo.setCustGeneralInfoIndDisable(false);
                misInfo.setMisCompDisable(true);
                misInfo.setMisIndDisable(false);
                relatedInfo.setRelIndView("");
                relatedInfo.setRelComView("none");

                List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("305");
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    custGeneralInfo.getIncomeRangeList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }

                //Setting Drop Down of Realted Info
                relatedInfo.getRelPersonTypeList().add(new SelectItem("0", "--Select--"));
                functionList = masterDelegate.getFunctionValues("309"); //Related Person Type-IND
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    relatedInfo.getRelPersonTypeList().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(),
                            recTypeTO.getRefDesc()));
                }
                //Addition on 23/11/2015
                optionlist = aitr.getListAsPerRequirement("311", custTO.getCustEntityType(), custTO.getAcType(), "0", "0", "0",ymd.format(new Date()),0);//Ind POI Proof doc
                custGeneralInfo.setLegelPoiDocList(new ArrayList<SelectItem>());
                custGeneralInfo.getLegelPoiDocList().add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    custGeneralInfo.getLegelPoiDocList().add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
                //End here
                //Addition on 05/01/2017
                functionList = masterDelegate.getFunctionValues("361"); //Account  type list
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    acTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                }

                misInfo.setMisOccupationOption(new ArrayList<SelectItem>());//Occupation type for legal entity
                misInfo.getMisOccupationOption().add(new SelectItem("0", "--Select--"));
                functionList = masterDelegate.getFunctionValues("021");
                for (CbsRefRecTypeTO recTypeTO : functionList) {
                    misInfo.getMisOccupationOption().add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                }
                //end
            }

            if (custTO.getCustEntityType() == null || custTO.getTitle().equalsIgnoreCase("")) {
                this.setCustEntityType("0");
            } else {
                this.setCustEntityType(custTO.getCustEntityType());
            }
            custGeneralInfo.setCustomerEntityGn(this.custEntityType);
            if (custTO.getTitle() == null || custTO.getTitle().equalsIgnoreCase("")) {
                this.setTitleType("0");
            } else {
                this.setTitleType(custTO.getTitle());
            }

            if (custTO.getCustname() == null || custTO.getCustname().equalsIgnoreCase("")) {
                this.setCustFirstName("");
            } else {
                this.setCustFirstName(custTO.getCustname());
            }
            if (custTO.getShortname() == null || custTO.getShortname().equalsIgnoreCase("")) {
                this.setShortName("");
            } else {
                this.setShortName(custTO.getShortname());
            }
            if (custTO.getGender() == null || custTO.getGender().equalsIgnoreCase("")) {
                this.setGenderType("0");
            } else {
                this.setGenderType(custTO.getGender());
            }
            if (custTO.getStaffid() == null || custTO.getStaffid().equalsIgnoreCase("")) {
                this.setStaffNo("0");
            } else {
                this.setStaffNo(custTO.getStaffid());
            }
            if (custTO.getStaffflag() == null || custTO.getStaffflag().equalsIgnoreCase("")) {
                this.setStaffType("0");
            } else {
                this.setStaffType(custTO.getStaffflag());
            }
            if (custTO.getFathername() == null || custTO.getFathername().equalsIgnoreCase("")) {
                this.setFathername("");
            } else {
                this.setFathername(custTO.getFathername());
            }
            if (custTO.getMothername() == null || custTO.getMothername().equalsIgnoreCase("")) {
                this.setMotherName("");
            } else {
                this.setMotherName(custTO.getMothername());
            }
            if (custTO.getMaritalstatus() == null || custTO.getMaritalstatus().equalsIgnoreCase("")) {
                this.setMaritalType("0");
            } else {
                this.setMaritalType(custTO.getMaritalstatus());
            }
            // if (custTO.getMaritalstatus().equalsIgnoreCase("01")) {
            //     this.fatherSpouseFlagDisable = false;
            //  }
            if (custTO.getMinorflag() == null || custTO.getMinorflag().equalsIgnoreCase("")) {
                this.setMinorType("0");
            } else {
                this.setMinorType(custTO.getMinorflag());
            }
            if (custTO.getNriflag() == null || custTO.getNriflag().equalsIgnoreCase("")) {
                this.setNriType("0");
            } else {
                this.setNriType(custTO.getNriflag());
            }
            if (custTO.getMothername() == null || custTO.getMothername().equalsIgnoreCase("")) {
                this.setMotherName("");
            } else {
                this.setMotherName(custTO.getMothername());
            }
            if (custTO.getuINCardNo() == null || custTO.getuINCardNo().equalsIgnoreCase("")) {
                this.setUINCardNo("");
            } else {
                this.setUINCardNo(custTO.getuINCardNo());
            }
            if (custTO.getCommunicationPreference() == null || custTO.getCommunicationPreference().equalsIgnoreCase("")) {
                this.setCommuPreference("0");
            } else {
                this.setCommuPreference(custTO.getCommunicationPreference());
            }
            if (custTO.getMobilenumber() == null || custTO.getMobilenumber().equalsIgnoreCase("")) {
                this.setMobNo("");
            } else {
                this.setMobNo(custTO.getMobilenumber());
            }
            if (custTO.getCustStatus() == null || custTO.getCustStatus().equalsIgnoreCase("")) {
                this.setCustStatusInPersnlInfo("");
            } else {
                this.setCustStatusInPersnlInfo(custTO.getCustStatus());
            }
            if (custTO.getPreferredLanguage() == null || custTO.getPreferredLanguage().equalsIgnoreCase("")) {
                this.setPreferedLanguage("0");
            } else {
                this.setPreferedLanguage(custTO.getPreferredLanguage());
            }
            if (custTO.getNameInPreferredLanguage() == null || custTO.getNameInPreferredLanguage().equalsIgnoreCase("")) {
                this.setNameInPreferredLanguage("");
            } else {
                this.setNameInPreferredLanguage(custTO.getNameInPreferredLanguage());
            }
            if (custTO.getChgTurnOver() == null || custTO.getChgTurnOver().equalsIgnoreCase("")) {
                this.setChargeTurnOverFlag("0");
            } else {
                this.setChargeTurnOverFlag(custTO.getChgTurnOver());
            }
            if (custTO.getPurgeAllowed() == null || custTO.getPurgeAllowed().equalsIgnoreCase("")) {
                this.setPureAllowedFlag("0");
            } else {
                this.setPureAllowedFlag(custTO.getPurgeAllowed());
            }
            if (custTO.getPassportNo() == null || custTO.getPassportNo().equalsIgnoreCase("")) {
                this.setPassportNumber("");
            } else {
                this.setPassportNumber(custTO.getPassportNo());
            }
            if (custTO.getIssueDate() == null) {
                this.setPassPortIssuedate(date);
            } else {
                this.setPassPortIssuedate(custTO.getIssueDate());
            }
            if (custTO.getIssuingAuthority() == null || custTO.getIssuingAuthority().equalsIgnoreCase("")) {
                this.setPassportIssuingAuthority("");
            } else {
                this.setPassportIssuingAuthority(custTO.getIssuingAuthority());
            }
            if (custTO.getExpirydate() == null) {
                this.setPassPortExpirdate(date);
            } else {
                this.setPassPortExpirdate(custTO.getExpirydate());
            }
            if (custTO.getPlaceOfIssue() == null || custTO.getPlaceOfIssue().equalsIgnoreCase("")) {
                this.setPassportIssuePlace("");
            } else {
                this.setPassportIssuePlace(custTO.getPlaceOfIssue());
            }
            if (custTO.getDateOfBirth() == null) {
                this.setPerDateOfBirth(date);
            } else {
                this.setPerDateOfBirth(custTO.getDateOfBirth());
            }
            if (custTO.getpANGIRNumber() == null || custTO.getpANGIRNumber().equalsIgnoreCase("")) {
                this.setPanGirNo("");
            } else {
                this.setPanGirNo(custTO.getpANGIRNumber());

            }
            if(custTO.getVirtualId() == null || custTO.getVirtualId().equalsIgnoreCase("")){
                this.setVirtualId("");
            }else{
                this.setVirtualId(custTO.getVirtualId());
            }
            //New Addition of Aadhaar
            if (custTO.getAdhaarNo() == null || custTO.getAdhaarNo().equalsIgnoreCase("")) {
                this.setAdhaarNo("");
            } else {
                this.setAdhaarNo(custTO.getAdhaarNo());
            }
            if (custTO.getLpgId() == null || custTO.getLpgId().equalsIgnoreCase("")) {
                this.setLpgId("");
            } else {
                this.setLpgId(custTO.getLpgId());
            }
            if (custTO.getAdhaarLpgAcno() == null || custTO.getAdhaarLpgAcno().equalsIgnoreCase("")) {
                this.setAdhaarLpgAcno("");
            } else {
                this.setAdhaarLpgAcno(custTO.getAdhaarLpgAcno());
            }
            if (custTO.getMandateFlag() == null || custTO.getMandateFlag().equalsIgnoreCase("")) {
                this.setMandateFlag("");
            } else {
                this.setMandateFlag(custTO.getMandateFlag());
            }
            if (custTO.getMandateDt() == null || custTO.getMandateDt().equals("")) {
                this.setMandateDt(null);
            } else {
                this.setMandateDt(dmy.parse(custTO.getMandateDt()));
            }
            if (custTO.getRegType() == null || custTO.getRegType().equals("")) {
                this.setRegType("0");
            } else {
                this.setRegType(custTO.getRegType());
            }
            //Added By Rahul
            this.aadhaarMode = (custTO.getAadhaarMode() == null || custTO.getAadhaarMode().equals("")) ? "" : custTO.getAadhaarMode();
            this.aadhaarBankIin = (custTO.getAadhaarBankIin() == null || custTO.getAadhaarBankIin().equals("")) ? "" : custTO.getAadhaarBankIin();
            if (custTO.getAadhaarBankIin() == null || custTO.getAadhaarBankIin().equals("")) {
                this.setAadhaarBankIin("");
                this.bankChgFlag = "none";
            } else {
                this.setAadhaarBankIin(custTO.getAadhaarBankIin());
                this.bankChgFlag = "";
            }

//            if (custTO.getAadhaarBankIin() == null || custTO.getAadhaarBankIin().equals("")) {
//                this.setAadhaarBankIin("000000");
//            } else {
//                this.setBankChgFlag("");
//                List optionList = aitr.getBankiinDetail();
//                bankOption = new ArrayList<SelectItem>();
//                bankOption.add(new SelectItem("000000", "--Select--"));
//                for (int i = 0; i < optionList.size(); i++) {
//                    Vector ele7 = (Vector) optionList.get(i);
//                    bankOption.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
//                    this.setAadhaarBankIin(custTO.getAadhaarBankIin());
//                }
//            }


            //End here
            if (custTO.getpANGIRNumber().equalsIgnoreCase("Form 60")) {
                this.setPanNoOption("Form 60");
            } else if (custTO.getpANGIRNumber().equalsIgnoreCase("Form 61")) {
                this.setPanNoOption("Form 61");
            } else if (custTO.getpANGIRNumber().equalsIgnoreCase("0")) {
                this.setPanNoOption("0");
            } else if (custTO.getpANGIRNumber().equalsIgnoreCase("NA")) {
                this.setPanNoOption("NA");
            } else {
                this.setPanNoOption("PAN NO");
            }

            //New Addition


            this.middleName = (custTO.getMiddleName() == null || custTO.getMiddleName().equals("")) ? "" : custTO.getMiddleName();
            this.lastName = (custTO.getLastName() == null || custTO.getLastName().equals("")) ? "" : custTO.getLastName();
            this.custFullName = (custTO.getCustFullName() == null || custTO.getCustFullName().equals("")) ? "" : custTO.getCustFullName();
            this.spouseName = (custTO.getSpouseName() == null || custTO.getSpouseName().equals("")) ? "" : custTO.getSpouseName();
            this.maidenName = (custTO.getMaidenName() == null || custTO.getMaidenName().equals("")) ? "" : custTO.getMaidenName();
            this.nationality = (custTO.getNationality() == null || custTO.getNationality().equals("")) ? "IN" : custTO.getNationality();

            if (custTO.getGstIdentificationNumber() == null || custTO.getGstIdentificationNumber().equalsIgnoreCase("")) {
                this.setGstIdentificationNumber("");
            } else {
                this.setGstIdentificationNumber(custTO.getGstIdentificationNumber());
            }




            if (this.getMinorType().equalsIgnoreCase("Y")) {
                this.disableMinorGrd = "";
                CBSCustMinorInfoTO minorTO = masterDelegate.getMinorInfoByCustId(getCustomerDetail().getCustId());
                this.guardianCustId = (minorTO.getGuardianCode() == null || minorTO.getGuardianCode().equals("")) ? ""
                        : minorTO.getGuardianCode();
                this.guardianCustName = customerRemote.getCustomerName(this.guardianCustId);
                this.minorMajorityDate = (minorTO.getMajorityDate() == null) ? date
                        : minorTO.getMajorityDate();
            } else {
                this.disableMinorGrd = "none";
                this.guardianCustId = "";
                this.guardianCustName = "";
                this.setMinorMajorityDate(date);
            }
            //Addition on 27/10/2015
            //Addition on 05/01/2017
            this.custEntityType = (custTO.getCustEntityType() == null || custTO.getCustEntityType().equals("") || custTO.getCustEntityType().equals("0")) ? "0" : custTO.getCustEntityType();
            this.acHolderTypeFlag = (custTO.getAcHolderTypeFlag() == null || custTO.getAcHolderTypeFlag().equals("") || custTO.getAcHolderTypeFlag().equals("0")) ? "0" : custTO.getAcHolderTypeFlag();
            this.acHolderType = (custTO.getAcHolderType() == null || custTO.getAcHolderType().equals("") || custTO.getAcHolderType().equals("0")) ? "0" : custTO.getAcHolderType();
            this.acType = (custTO.getAcType() == null || custTO.getAcType().equals("") || custTO.getAcType().equals("0")) ? "0" : custTO.getAcType();
            this.cKYCNo = (custTO.getcKYCNo() == null || custTO.getcKYCNo().equals("")) ? "" : custTO.getcKYCNo();
            this.fatherMiddleName = (custTO.getFatherMiddleName() == null || custTO.getFatherMiddleName().equals("")) ? "" : custTO.getFatherMiddleName();
            this.fatherLastName = (custTO.getFatherLastName() == null || custTO.getFatherLastName().equals("")) ? "" : custTO.getFatherLastName();
            this.motherMiddleName = (custTO.getMotherMiddleName() == null || custTO.getMotherMiddleName().equals("")) ? "" : custTO.getMotherMiddleName();
            this.motherLastName = (custTO.getMotherLastName() == null || custTO.getMotherLastName().equals("")) ? "" : custTO.getMotherLastName();
            this.spouseMiddleName = (custTO.getSpouseMiddleName() == null || custTO.getSpouseMiddleName().equals("")) ? "" : custTO.getSpouseMiddleName();
            this.spouseLastName = (custTO.getSpouseLastName() == null || custTO.getSpouseLastName().equals("")) ? "" : custTO.getSpouseLastName();
            this.fatherSpouseFlag = (custTO.getFatherSpouseFlag() == null || custTO.getFatherSpouseFlag().equals("")) ? false : custTO.getFatherSpouseFlag().equalsIgnoreCase("Y") ? true : false;
            this.fatherSpouseFlagDisable = !this.fatherSpouseFlag;

            misInfo.setIncopDt(this.perDateOfBirth);


            //end
            makeChangeFlagToUnChange();
            if (getCustomerDetail().getFunction().equalsIgnoreCase("V")) {
                CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.getCustomerLastChangeDetail(getCustomerDetail().getVerifyCustId());
                if (hisTo != null) {
                    this.titleChgFlag = this.titleType.equalsIgnoreCase(hisTo.getTitle() == null ? "" : hisTo.getTitle()) ? "color: #000000" : "color: #FF0000";
                    this.custNameChgFlag = this.custFirstName.equalsIgnoreCase(hisTo.getCustname() == null ? "" : hisTo.getCustname()) ? "color: #000000" : "color: #FF0000";
                    this.middleNameChgFlag = this.middleName.equalsIgnoreCase(hisTo.getMiddleName() == null ? "" : hisTo.getMiddleName()) ? "color: #000000" : "color: #FF0000";
                    this.lastNameChgFlag = this.lastName.equalsIgnoreCase(hisTo.getLastName() == null ? "" : hisTo.getLastName()) ? "color: #000000" : "color: #FF0000";
                    this.genderChgFlag = this.genderType.equalsIgnoreCase(hisTo.getGender() == null ? "" : hisTo.getGender()) ? "color: #000000" : "color: #FF0000";
                    this.fatherNameChgFlag = this.fathername.equalsIgnoreCase(hisTo.getFathername() == null ? "" : hisTo.getFathername()) ? "color: #000000" : "color: #FF0000";
                    this.motherNameChgFlag = this.motherName.equalsIgnoreCase(hisTo.getMothername() == null ? "" : hisTo.getMothername()) ? "color: #000000" : "color: #FF0000";
                    this.dobChgFlag = this.perDateOfBirth.compareTo(ymd.parse(hisTo.getDateOfBirth())) == 0 ? "color: #000000" : "color: #FF0000";
                    this.maritalStatusChgFlag = this.maritalType.equalsIgnoreCase(hisTo.getMaritalstatus() == null ? "" : hisTo.getMaritalstatus()) ? "color: #000000" : "color: #FF0000";
                    this.spouseChgFlag = this.spouseName.equalsIgnoreCase(hisTo.getSpouseName() == null ? "" : hisTo.getSpouseName()) ? "color: #000000" : "color: #FF0000";
                    this.maidenChgFlag = this.maidenName.equalsIgnoreCase(hisTo.getMaidenName() == null ? "" : hisTo.getMaidenName()) ? "color: #000000" : "color: #FF0000";
                    this.staffChgFlag = this.staffType.equalsIgnoreCase(hisTo.getStaffflag() == null ? "" : hisTo.getStaffflag()) ? "color: #000000" : "color: #FF0000";
                    this.staffNoChgFlag = this.staffNo.equalsIgnoreCase(hisTo.getStaffid() == null ? "" : hisTo.getStaffid()) ? "color: #000000" : "color: #FF0000";
                    this.nationalityChgFlag = this.nationality.equalsIgnoreCase(hisTo.getNationality() == null ? "" : hisTo.getNationality()) ? "color: #000000" : "color: #FF0000";
                    this.residenceStatusChgFlag = this.nriType.equalsIgnoreCase(hisTo.getNriflag() == null ? "" : hisTo.getNriflag()) ? "color: #000000" : "color: #FF0000";
                    this.mobileNoChgFlag = this.mobNo.equalsIgnoreCase(hisTo.getMobilenumber() == null ? "" : hisTo.getMobilenumber()) ? "color: #000000" : "color: #FF0000";
                    this.commPrefChgFlag = this.commuPreference.equalsIgnoreCase(hisTo.getCommunicationPreference() == null ? "" : hisTo.getCommunicationPreference()) ? "color: #000000" : "color: #FF0000";
                    this.panGirNoChgFlag = this.panGirNo.equalsIgnoreCase(hisTo.getpANGIRNumber() == null ? "" : hisTo.getpANGIRNumber()) ? "color: #000000" : "color: #FF0000";
                    this.minorChgFlag = this.minorType.equalsIgnoreCase(hisTo.getMinorflag() == null ? "" : hisTo.getMinorflag()) ? "color: #000000" : "color: #FF0000";

                    CBSCustMinorInfoHisTO minorHisTo = customerMgmtRemote.getCustomerLastChangeDetailForMinor(getCustomerDetail().getVerifyCustId());
                    if (minorHisTo != null) {
                        this.grdCustIdChgFlag = this.guardianCustId.equalsIgnoreCase(minorHisTo.getGuardianCode() == null ? "" : minorHisTo.getGuardianCode()) ? "color: #000000" : "color: #FF0000";
                        this.minorMajorDtChgFlag = this.minorMajorityDate.compareTo(minorHisTo.getMajorityDate() == null ? ymd.parse(dmy.format(new Date())) : minorHisTo.getMajorityDate()) == 0 ? "color: #000000" : "color: #FF0000";
                    }

                    this.regTypeChgFlag = this.regType.equalsIgnoreCase(hisTo.getRegType() == null ? "" : hisTo.getRegType()) ? "color: #000000" : "color: #FF0000";
                    this.adhaarNoChgFlag = this.adhaarNo.equalsIgnoreCase(hisTo.getAdhaarNo() == null ? "" : hisTo.getAdhaarNo()) ? "color: #000000" : "color: #FF0000";
                    this.lpgNoChgFlag = this.lpgId.equalsIgnoreCase(hisTo.getLpgId() == null ? "" : hisTo.getLpgId()) ? "color: #000000" : "color: #FF0000";
                    this.primaryAcChgFlag = this.adhaarLpgAcno.equalsIgnoreCase(hisTo.getAdhaarLpgAcno() == null ? "" : hisTo.getAdhaarLpgAcno()) ? "color: #000000" : "color: #FF0000";
                    this.manDtFlagChgFlag = this.mandateFlag.equalsIgnoreCase(hisTo.getMandateFlag() == null ? "" : hisTo.getMandateFlag()) ? "color: #000000" : "color: #FF0000";
                    Date tempManDt = null;
                    this.manDatChgFlag = (tempManDt = (this.mandateDt == null || this.mandateDt.equals("") ? dmy.parse("01/01/1900") : this.mandateDt)).compareTo(dmy.parse((hisTo.getMandateDt() == null || hisTo.getMandateDt().equals("")) ? "01/01/1900" : hisTo.getMandateDt())) == 0 ? "color: #000000" : "color: #FF0000";
                    // add by rahul
                    this.modeTypeChgFlag = this.aadhaarMode.equalsIgnoreCase(hisTo.getAadhaarMode() == null ? "" : hisTo.getAadhaarMode()) ? "color: #000000" : "color: #FF0000";
                    this.bankChgFlag = this.aadhaarBankIin.equalsIgnoreCase(hisTo.getAadhaarBankIin() == null ? "" : hisTo.getAadhaarBankIin()) ? "color: #000000" : "color: #FF0000";
                    //Addition 05/01/2017
                    this.custEntityTypeChgFlag = this.custEntityType.equalsIgnoreCase(hisTo.getCustEntityType() == null ? "" : hisTo.getCustEntityType()) ? "color: #000000" : "color: #FF0000";
                    this.acHolderTypeFlagChgFlag = this.acHolderTypeFlag.equalsIgnoreCase(hisTo.getAcHolderTypeFlag() == null ? "" : hisTo.getAcHolderTypeFlag()) ? "color: #000000" : "color: #FF0000";
                    this.acHolderTypeChgFlag = this.acHolderType.equalsIgnoreCase(hisTo.getAcHolderType() == null ? "" : hisTo.getAcHolderType()) ? "color: #000000" : "color: #FF0000";
                    this.acTypeChgFlag = this.acType.equalsIgnoreCase(hisTo.getAcType() == null ? "" : hisTo.getAcType()) ? "color: #000000" : "color: #FF0000";
                    this.fatherMiddleNameChgFlag = this.fatherMiddleName.equalsIgnoreCase(hisTo.getFatherMiddleName() == null ? "" : hisTo.getFatherMiddleName()) ? "color: #000000" : "color: #FF0000";
                    this.fatherLastNameChgFlag = this.fatherLastName.equalsIgnoreCase(hisTo.getFatherLastName() == null ? "" : hisTo.getFatherLastName()) ? "color: #000000" : "color: #FF0000";
                    this.motherMiddleNameChgFlag = this.motherMiddleName.equalsIgnoreCase(hisTo.getMotherMiddleName() == null ? "" : hisTo.getMotherMiddleName()) ? "color: #000000" : "color: #FF0000";
                    this.motherLastNameChgFlag = this.motherLastName.equalsIgnoreCase(hisTo.getMotherLastName() == null ? "" : hisTo.getMotherLastName()) ? "color: #000000" : "color: #FF0000";
                    this.spouseMiddleNameChgFlag = this.spouseMiddleName.equalsIgnoreCase(hisTo.getSpouseMiddleName() == null ? "" : hisTo.getSpouseMiddleName()) ? "color: #000000" : "color: #FF0000";
                    this.spouseLastNameChgFlag = this.spouseLastName.equalsIgnoreCase(hisTo.getSpouseLastName() == null ? "" : hisTo.getSpouseLastName()) ? "color: #000000" : "color: #FF0000";
                    //end
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    //Addition on 20/10/2015
    public String validateCustomerExistence(String function, String custId) throws ApplicationException {
        //PAN Validation
        String panMsg = "ok", nameMsg = "ok";
        if (this.panNoOption.equalsIgnoreCase("PAN No") && this.panGirNo != null
                && !this.panGirNo.equals("") && this.panGirNo.trim().length() == 10) {
            panMsg = customerMgmtRemote.isCustIdExistBasedOnPan(function, custId.trim(), this.panGirNo.trim()); //Either should return ok return message
        }
        //Name,Father And Dob Validation
        String name = (this.custFirstName == null ? "" : this.custFirstName.trim()) + " " + (this.middleName == null ? ""
                : this.middleName.trim());
        name = name.trim() + " " + (this.lastName == null ? "" : this.lastName.trim());

        String fName = this.fathername == null ? "" : this.fathername.trim();
        if (this.perDateOfBirth != null) {
            nameMsg = customerMgmtRemote.isCustIdExistBasedOnNameFatherAndDob(function, custId.trim(), name.trim(),
                    fName, ymd.format(this.perDateOfBirth)); //Either should return ok or return message
        }
        if (!panMsg.equalsIgnoreCase("ok") && !nameMsg.equalsIgnoreCase("ok")) {
            return (panMsg.trim() + "\n" + nameMsg.trim()).trim();
        } else if (!panMsg.equalsIgnoreCase("ok")) {
            return panMsg.trim();
        } else if (!nameMsg.equalsIgnoreCase("ok")) {
            return nameMsg.trim();
        }
        return "ok";
    }
    //End here

    public void refreshForm() {
        this.setTitleType("0");
        this.setCustFirstName("");
        this.setShortName("");
        this.setGenderType("0");
        this.setMaritalType("0");
        this.setCustStatusInPersnlInfo("0");
        this.setFathername("");
        this.setMotherName("");
        this.setStaffNo("");
        this.setMsgForStaff("");
        this.setStaffType("0");
        this.setMinorType("0");
        this.setMsgMinor("");
        this.setNriType("0");
        this.setUINCardNo("");
        this.setVirtualId("");
        this.setMsg("");
        this.setMsg2("");
        this.setMsg3("");
        this.setMsg4("");
        this.setMsg5("");
        this.setMsg6("");
        this.setCommuPreference("0");
        this.setMobNo("");
        this.setPreferedLanguage("0");
        this.setNameInPreferredLanguage("");
        this.setChargeTurnOverFlag("0");
        this.setPureAllowedFlag("0");
        this.setPassportNumber("");
        this.setPerDateOfBirth(null);
        this.setPassPortIssuedate(date);
        this.setPassPortExpirdate(date);
        this.setPassportIssuePlace("");
        this.setPassportIssuingAuthority("");
        this.setSalary("0.0");
        this.setCustFloorLim("0.0");
        this.setPanGirNo("");
        this.setPanNoOption("0");
        this.setPanNoVisibility("false");
        minorFlag = false;
        this.setAdhaarNo("");
        this.setLpgId("");
        this.setAdhaarLpgAcno("");
        this.setMandateFlag(" ");
        this.setMandateDt(null);
        this.setAadhaarMode("0");
        this.setAadhaarBankIin("");
        this.setRegType("0");
        this.setCustNameDisable(false);
        this.setMiddleName("");
        this.setLastName("");
        this.setSpouseName("");
        this.setMaidenName("");
        this.setBankChgFlag("none");
        this.disableMinorGrd = "none";
        this.setGuardianCustId("");
        this.setGuardianCustName("");
        this.setMinorMajorityDate(date);
        this.setNamesDisable(false);
        //Addition 05/01/2017
        this.setCustEntityType("0");
        this.setAcHolderTypeFlag("0");
        this.setAcHolderType("0");
        this.setAcType("0");
        this.setcKYCNo("");
        this.setFatherMiddleName("");
        this.setFatherLastName("");
        this.setSpouseMiddleName("");
        this.setSpouseLastName("");
        this.setMotherMiddleName("");
        this.setMotherLastName("");
        this.setFatherSpouseFlag(false);
        this.setcKYCNo("");
        this.setCustFullName("");
        this.dobOrIncorpLebal = "Date Of Birth";
        this.setGstIdentificationNumber("");
        //end
        //Change On 28/10/2015
        makeChangeFlagToUnChange();
    }

    //Add On 28/10/2015
    public void makeChangeFlagToUnChange() {
        this.setTitleChgFlag("color: #000000");
        this.setCustNameChgFlag("color: #000000");
        this.setMiddleNameChgFlag("color: #000000");
        this.setLastNameChgFlag("color: #000000");
        this.setGenderChgFlag("color: #000000");
        this.setFatherNameChgFlag("color: #000000");
        this.setMotherNameChgFlag("color: #000000");
        this.setDobChgFlag("color: #000000");
        this.setMaritalStatusChgFlag("color: #000000");
        this.setSpouseChgFlag("color: #000000");
        this.setMaidenChgFlag("color: #000000");
        this.setStaffChgFlag("color: #000000");
        this.setStaffNoChgFlag("color: #000000");
        this.setNationalityChgFlag("color: #000000");
        this.setResidenceStatusChgFlag("color: #000000");
        this.setMobileNoChgFlag("color: #000000");
        this.setCommPrefChgFlag("color: #000000");
        this.setPanGirNoChgFlag("color: #000000");
        this.setMinorChgFlag("color: #000000");
        this.setGrdCustIdChgFlag("color: #000000");
        this.setMinorMajorDtChgFlag("color: #000000");
        this.setRegTypeChgFlag("color: #000000");
        this.setAdhaarNoChgFlag("color: #000000");
        this.setLpgNoChgFlag("color: #000000");
        this.setPrimaryAcChgFlag("color: #000000");
        this.setManDtFlagChgFlag("color: #000000");
        this.setManDatChgFlag("color: #000000");
        //Addition 05/01/2017
        this.setCustEntityTypeChgFlag("color: #000000");
        this.setAcHolderTypeFlagChgFlag("color: #000000");
        this.setAcHolderTypeChgFlag("color: #000000");
        this.setAcTypeChgFlag("color: #000000");
        this.setFatherMiddleNameChgFlag("color: #000000");
        this.setFatherLastNameChgFlag("color: #000000");
        this.setSpouseMiddleNameChgFlag("color: #000000");
        this.setSpouseLastNameChgFlag("color: #000000");
        this.setMotherMiddleNameChgFlag("color: #000000");
        this.setMotherLastNameChgFlag("color: #000000");
        this.setGstinChgFlag("color: #000000");
        //end
    }
    //    public String onblurNamePreferdLang() {
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        Matcher namePrefrdLangCheck = p.matcher(this.nameInPreferredLanguage);
//        if (namePrefrdLangCheck.matches()) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Name in Preferred Language.");
//            return "Please Enter Non Numeric Value in Name in Preferred Language.";
//        } else if (this.nameInPreferredLanguage.contains("0") || this.nameInPreferredLanguage.contains("1") || this.nameInPreferredLanguage.contains("2") || this.nameInPreferredLanguage.contains("3") || this.nameInPreferredLanguage.contains("4") || this.nameInPreferredLanguage.contains("5")
//                || this.nameInPreferredLanguage.contains("6") || this.nameInPreferredLanguage.contains("7") || this.nameInPreferredLanguage.contains("8") || this.nameInPreferredLanguage.contains("9")) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Name in Preferred Language.");
//            return "Please Enter Non Numeric Value in Name in Preferred Language.";
//        } else {
//            getCustomerDetail().setErrorMsg("");
//            return "true";
//        }
//    }
//
//    public String onchangePassportIssueDate() {
//        try {
//            long strDateDiff = CbsUtil.dayDiff(passPortIssuedate, new Date());
//            if (strDateDiff < 0) {
//                return "You can't select the passport Issue Date after the current date.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "True";
//            }
//        } catch (Exception e) {
//            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
//        }
//        return "True";
//    }
//
//    public String onchangePassportExpiryDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String msg1 = "";
//        Calendar cal1 = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//        String passExpDt = sdf.format(this.passPortExpirdate);
//        String passIssueDt = sdf.format(this.passPortIssuedate);
//        cal1.set(Integer.parseInt(passExpDt.substring(0, 4)), Integer.parseInt(passExpDt.substring(4, 6)), Integer.parseInt(passExpDt.substring(6)));
//        cal2.set(Integer.parseInt(passIssueDt.substring(0, 4)), Integer.parseInt(passIssueDt.substring(4, 6)), Integer.parseInt(passIssueDt.substring(6)));
//        if (cal1.equals(cal2) || cal1.before(cal2)) {
//            getCustomerDetail().setErrorMsg("Passport Expiry Date can not be equal or less than the Passport Issue Date.");
//            passFlag = "false";
//            return msg1 = "Passport Expiry Date can not be equal or less than the Passport Issue Date.";
//        } else {
//            getCustomerDetail().setErrorMsg("");
//            passFlag = "true";
//            return msg1 = "true";
//        }
//    }
    //Getter And Setter

    public boolean isCustNameDisable() {
        return custNameDisable;
    }

    public void setCustNameDisable(boolean custNameDisable) {
        this.custNameDisable = custNameDisable;
    }

    public MinorInfo getMinorInfo() {
        return minorInfo;
    }

    public void setMinorInfo(MinorInfo minorInfo) {
        this.minorInfo = minorInfo;
    }

    public NreInfo getNreInfo() {
        return nreInfo;
    }

    public void setNreInfo(NreInfo nreInfo) {
        this.nreInfo = nreInfo;
    }

    public String getMsg5() {
        return msg5;
    }

    public void setMsg5(String msg5) {
        this.msg5 = msg5;
    }

    public String getMsg6() {
        return msg6;
    }

    public void setMsg6(String msg6) {
        this.msg6 = msg6;
    }

    public String getTdsExRefNo() {
        return TdsExRefNo;
    }

    public void setTdsExRefNo(String TdsExRefNo) {
        this.TdsExRefNo = TdsExRefNo;
    }

    public String getAcctLevelChanges() {
        return acctLevelChanges;
    }

    public void setAcctLevelChanges(String acctLevelChanges) {
        this.acctLevelChanges = acctLevelChanges;
    }

    public String getCustFloorLim() {
        return custFloorLim;
    }

    public void setCustFloorLim(String custFloorLim) {
        this.custFloorLim = custFloorLim;
    }

    public String getCustFullName() {
        return custFullName;
    }

    public void setCustFullName(String custFullName) {
        this.custFullName = custFullName;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLevelChanges() {
        return custLevelChanges;
    }

    public void setCustLevelChanges(String custLevelChanges) {
        this.custLevelChanges = custLevelChanges;
    }

    public String getCustStatusInPersnlInfo() {
        return custStatusInPersnlInfo;
    }

    public void setCustStatusInPersnlInfo(String custStatusInPersnlInfo) {
        this.custStatusInPersnlInfo = custStatusInPersnlInfo;
    }

    public String getDrivingLiscNo() {
        return drivingLiscNo;
    }

    public void setDrivingLiscNo(String drivingLiscNo) {
        this.drivingLiscNo = drivingLiscNo;
    }

    public String getExciseNo() {
        return exciseNo;
    }

    public void setExciseNo(String exciseNo) {
        this.exciseNo = exciseNo;
    }

    public boolean isFlagForCreditCard() {
        return flagForCreditCard;
    }

    public void setFlagForCreditCard(boolean flagForCreditCard) {
        this.flagForCreditCard = flagForCreditCard;
    }

    public int getFlagForCreditRate() {
        return flagForCreditRate;
    }

    public void setFlagForCreditRate(int flagForCreditRate) {
        this.flagForCreditRate = flagForCreditRate;
    }

    public boolean isFlagForNri() {
        return flagForNri;
    }

    public void setFlagForNri(boolean flagForNri) {
        this.flagForNri = flagForNri;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public String getMaritalType() {
        return maritalType;
    }

    public void setMaritalType(String maritalType) {
        this.maritalType = maritalType;
    }

    public String getPanGirNo() {
        return panGirNo;
    }

    public void setPanGirNo(String panGirNo) {
        this.panGirNo = panGirNo;
    }

    public Date getPerDateOfBirth() {
        return perDateOfBirth;
    }

    public void setPerDateOfBirth(Date perDateOfBirth) {
        this.perDateOfBirth = perDateOfBirth;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSalesTaxNo() {
        return salesTaxNo;
    }

    public void setSalesTaxNo(String salesTaxNo) {
        this.salesTaxNo = salesTaxNo;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getTdsCode() {
        return tdsCode;
    }

    public void setTdsCode(String tdsCode) {
        this.tdsCode = tdsCode;
    }

    public String getTdsCustId() {
        return tdsCustId;
    }

    public void setTdsCustId(String tdsCustId) {
        this.tdsCustId = tdsCustId;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getTitleType2() {
        return titleType2;
    }

    public void setTitleType2(String titleType2) {
        this.titleType2 = titleType2;
    }

    public List<SelectItem> getTitleType2Option() {
        return titleType2Option;
    }

    public void setTitleType2Option(List<SelectItem> titleType2Option) {
        this.titleType2Option = titleType2Option;
    }

    public String getVoterIdNo() {
        return voterIdNo;
    }

    public void setVoterIdNo(String voterIdNo) {
        this.voterIdNo = voterIdNo;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public List<SelectItem> getTitleTypeOption() {
        return titleTypeOption;
    }

    public void setTitleTypeOption(List<SelectItem> titleTypeOption) {
        this.titleTypeOption = titleTypeOption;
    }

    public List<SelectItem> getCustStatusInPersnlInfoOption() {
        return custStatusInPersnlInfoOption;
    }

    public void setCustStatusInPersnlInfoOption(List<SelectItem> custStatusInPersnlInfoOption) {
        this.custStatusInPersnlInfoOption = custStatusInPersnlInfoOption;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public List<SelectItem> getGenderTypeOption() {
        return genderTypeOption;
    }

    public void setGenderTypeOption(List<SelectItem> genderTypeOption) {
        this.genderTypeOption = genderTypeOption;
    }

    public List<SelectItem> getMaritalTypeOption() {
        return maritalTypeOption;
    }

    public void setMaritalTypeOption(List<SelectItem> maritalTypeOption) {
        this.maritalTypeOption = maritalTypeOption;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMsgForStaff() {
        return msgForStaff;
    }

    public void setMsgForStaff(String msgForStaff) {
        this.msgForStaff = msgForStaff;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public List<SelectItem> getStaffTypeOption() {
        return staffTypeOption;
    }

    public void setStaffTypeOption(List<SelectItem> staffTypeOption) {
        this.staffTypeOption = staffTypeOption;
    }

    public String getDobFlag() {
        return dobFlag;
    }

    public void setDobFlag(String dobFlag) {
        this.dobFlag = dobFlag;
    }

    public String getMinorType() {
        return minorType;
    }

    public void setMinorType(String minorType) {
        this.minorType = minorType;
    }

    public boolean isFlagForsave() {
        return flagForsave;
    }

    public void setFlagForsave(boolean flagForsave) {
        this.flagForsave = flagForsave;
    }

    public boolean isMinorFlag() {
        return minorFlag;
    }

    public void setMinorFlag(boolean minorFlag) {
        this.minorFlag = minorFlag;
    }

    public boolean isNreFlag() {
        return nreFlag;
    }

    public void setNreFlag(boolean nreFlag) {
        this.nreFlag = nreFlag;
    }

    public String getPanNoOption() {
        return panNoOption;
    }

    public void setPanNoOption(String panNoOption) {
        this.panNoOption = panNoOption;
    }

    public List<SelectItem> getPanNoOptionList() {
        return panNoOptionList;
    }

    public void setPanNoOptionList(List<SelectItem> panNoOptionList) {
        this.panNoOptionList = panNoOptionList;
    }

    public String getPanNoVisibility() {
        return panNoVisibility;
    }

    public void setPanNoVisibility(String panNoVisibility) {
        this.panNoVisibility = panNoVisibility;
    }

    public String getAdhaarNo() {
        return adhaarNo;
    }

    public void setAdhaarNo(String adhaarNo) {
        this.adhaarNo = adhaarNo;
    }

    public String getLpgId() {
        return lpgId;
    }

    public void setLpgId(String lpgId) {
        this.lpgId = lpgId;
    }

    public String getAdhaarLpgAcno() {
        return adhaarLpgAcno;
    }

    public void setAdhaarLpgAcno(String adhaarLpgAcno) {
        this.adhaarLpgAcno = adhaarLpgAcno;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getUINCardNo() {
        return UINCardNo;
    }

    public void setUINCardNo(String UINCardNo) {
        this.UINCardNo = UINCardNo;
    }

    public String getChargeTurnOverFlag() {
        return chargeTurnOverFlag;
    }

    public void setChargeTurnOverFlag(String chargeTurnOverFlag) {
        this.chargeTurnOverFlag = chargeTurnOverFlag;
    }

    public List<SelectItem> getChargeTurnOverFlagOption() {
        return chargeTurnOverFlagOption;
    }

    public void setChargeTurnOverFlagOption(List<SelectItem> chargeTurnOverFlagOption) {
        this.chargeTurnOverFlagOption = chargeTurnOverFlagOption;
    }

    public String getCommuPreference() {
        return commuPreference;
    }

    public void setCommuPreference(String commuPreference) {
        this.commuPreference = commuPreference;
    }

    public List<SelectItem> getCommuPreferenceOption() {
        return commuPreferenceOption;
    }

    public void setCommuPreferenceOption(List<SelectItem> commuPreferenceOption) {
        this.commuPreferenceOption = commuPreferenceOption;
    }

    public List<SelectItem> getMinorTypeOption() {
        return minorTypeOption;
    }

    public void setMinorTypeOption(List<SelectItem> minorTypeOption) {
        this.minorTypeOption = minorTypeOption;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNameInPreferredLanguage() {
        return nameInPreferredLanguage;
    }

    public void setNameInPreferredLanguage(String nameInPreferredLanguage) {
        this.nameInPreferredLanguage = nameInPreferredLanguage;
    }

    public String getNriType() {
        return nriType;
    }

    public void setNriType(String nriType) {
        this.nriType = nriType;
    }

    public List<SelectItem> getNriTypeOption() {
        return nriTypeOption;
    }

    public void setNriTypeOption(List<SelectItem> nriTypeOption) {
        this.nriTypeOption = nriTypeOption;
    }

    public String getPreferedLanguage() {
        return preferedLanguage;
    }

    public void setPreferedLanguage(String preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public List<SelectItem> getPreferedLanguageOption() {
        return preferedLanguageOption;
    }

    public void setPreferedLanguageOption(List<SelectItem> preferedLanguageOption) {
        this.preferedLanguageOption = preferedLanguageOption;
    }

    public String getPureAllowedFlag() {
        return pureAllowedFlag;
    }

    public void setPureAllowedFlag(String pureAllowedFlag) {
        this.pureAllowedFlag = pureAllowedFlag;
    }

    public List<SelectItem> getPureAllowedFlagOption() {
        return pureAllowedFlagOption;
    }

    public void setPureAllowedFlagOption(List<SelectItem> pureAllowedFlagOption) {
        this.pureAllowedFlagOption = pureAllowedFlagOption;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public String getMsg3() {
        return msg3;
    }

    public void setMsg3(String msg3) {
        this.msg3 = msg3;
    }

    public String getMsg4() {
        return msg4;
    }

    public void setMsg4(String msg4) {
        this.msg4 = msg4;
    }

    public String getMsgMinor() {
        return msgMinor;
    }

    public void setMsgMinor(String msgMinor) {
        this.msgMinor = msgMinor;
    }

    public String getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(String passFlag) {
        this.passFlag = passFlag;
    }

    public Date getPassPortExpirdate() {
        return passPortExpirdate;
    }

    public void setPassPortExpirdate(Date passPortExpirdate) {
        this.passPortExpirdate = passPortExpirdate;
    }

    public Date getPassPortIssuedate() {
        return passPortIssuedate;
    }

    public void setPassPortIssuedate(Date passPortIssuedate) {
        this.passPortIssuedate = passPortIssuedate;
    }

    public String getPassportIssuePlace() {
        return passportIssuePlace;
    }

    public void setPassportIssuePlace(String passportIssuePlace) {
        this.passportIssuePlace = passportIssuePlace;
    }

    public String getPassportIssuingAuthority() {
        return passportIssuingAuthority;
    }

    public void setPassportIssuingAuthority(String passportIssuingAuthority) {
        this.passportIssuingAuthority = passportIssuingAuthority;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getViewIdForMinor() {
        return viewIdForMinor;
    }

    public void setViewIdForMinor(String viewIdForMinor) {
        this.viewIdForMinor = viewIdForMinor;
    }

    public String getViewIdForNre() {
        return viewIdForNre;
    }

    public void setViewIdForNre(String viewIdForNre) {
        this.viewIdForNre = viewIdForNre;
    }

    public Date getMandateDt() {
        return mandateDt;
    }

    public void setMandateDt(Date mandateDt) {
        this.mandateDt = mandateDt;
    }

    public String getMandateFlag() {
        return mandateFlag;
    }

    public void setMandateFlag(String mandateFlag) {
        this.mandateFlag = mandateFlag;
    }

    public List<SelectItem> getMandateFlagOption() {
        return mandateFlagOption;
    }

    public void setMandateFlagOption(List<SelectItem> mandateFlagOption) {
        this.mandateFlagOption = mandateFlagOption;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public List<SelectItem> getRegTypeOption() {
        return regTypeOption;
    }

    public void setRegTypeOption(List<SelectItem> regTypeOption) {
        this.regTypeOption = regTypeOption;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
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

    public boolean isCompanyDisable() {
        return companyDisable;
    }

    public void setCompanyDisable(boolean companyDisable) {
        this.companyDisable = companyDisable;
    }

    public String getDisableMinorGrd() {
        return disableMinorGrd;
    }

    public void setDisableMinorGrd(String disableMinorGrd) {
        this.disableMinorGrd = disableMinorGrd;
    }

    public String getGuardianCustId() {
        return guardianCustId;
    }

    public void setGuardianCustId(String guardianCustId) {
        this.guardianCustId = guardianCustId;
    }

    public String getGuardianCustName() {
        return guardianCustName;
    }

    public void setGuardianCustName(String guardianCustName) {
        this.guardianCustName = guardianCustName;
    }

    public Date getMinorMajorityDate() {
        return minorMajorityDate;
    }

    public void setMinorMajorityDate(Date minorMajorityDate) {
        this.minorMajorityDate = minorMajorityDate;
    }

    public CustGeneralInfo getCustGeneralInfo() {
        return custGeneralInfo;
    }

    public void setCustGeneralInfo(CustGeneralInfo custGeneralInfo) {
        this.custGeneralInfo = custGeneralInfo;
    }

    public CustAddressInfo getCustAddressInfo() {
        return custAddressInfo;
    }

    public void setCustAddressInfo(CustAddressInfo custAddressInfo) {
        this.custAddressInfo = custAddressInfo;
    }

    public MisInfo getMisInfo() {
        return misInfo;
    }

    public void setMisInfo(MisInfo misInfo) {
        this.misInfo = misInfo;
    }

    public RelatedPersonInfo getRelatedInfo() {
        return relatedInfo;
    }

    public void setRelatedInfo(RelatedPersonInfo relatedInfo) {
        this.relatedInfo = relatedInfo;
    }

    public boolean isNamesDisable() {
        return namesDisable;
    }

    public void setNamesDisable(boolean namesDisable) {
        this.namesDisable = namesDisable;
    }

    public String getTitleChgFlag() {
        return titleChgFlag;
    }

    public void setTitleChgFlag(String titleChgFlag) {
        this.titleChgFlag = titleChgFlag;
    }

    public String getCustNameChgFlag() {
        return custNameChgFlag;
    }

    public void setCustNameChgFlag(String custNameChgFlag) {
        this.custNameChgFlag = custNameChgFlag;
    }

    public String getMiddleNameChgFlag() {
        return middleNameChgFlag;
    }

    public void setMiddleNameChgFlag(String middleNameChgFlag) {
        this.middleNameChgFlag = middleNameChgFlag;
    }

    public String getLastNameChgFlag() {
        return lastNameChgFlag;
    }

    public void setLastNameChgFlag(String lastNameChgFlag) {
        this.lastNameChgFlag = lastNameChgFlag;
    }

    public String getGenderChgFlag() {
        return genderChgFlag;
    }

    public void setGenderChgFlag(String genderChgFlag) {
        this.genderChgFlag = genderChgFlag;
    }

    public String getFatherNameChgFlag() {
        return fatherNameChgFlag;
    }

    public void setFatherNameChgFlag(String fatherNameChgFlag) {
        this.fatherNameChgFlag = fatherNameChgFlag;
    }

    public String getMotherNameChgFlag() {
        return motherNameChgFlag;
    }

    public void setMotherNameChgFlag(String motherNameChgFlag) {
        this.motherNameChgFlag = motherNameChgFlag;
    }

    public String getDobChgFlag() {
        return dobChgFlag;
    }

    public void setDobChgFlag(String dobChgFlag) {
        this.dobChgFlag = dobChgFlag;
    }

    public String getSpouseChgFlag() {
        return spouseChgFlag;
    }

    public void setSpouseChgFlag(String spouseChgFlag) {
        this.spouseChgFlag = spouseChgFlag;
    }

    public String getMaidenChgFlag() {
        return maidenChgFlag;
    }

    public void setMaidenChgFlag(String maidenChgFlag) {
        this.maidenChgFlag = maidenChgFlag;
    }

    public String getStaffChgFlag() {
        return staffChgFlag;
    }

    public void setStaffChgFlag(String staffChgFlag) {
        this.staffChgFlag = staffChgFlag;
    }

    public String getStaffNoChgFlag() {
        return staffNoChgFlag;
    }

    public void setStaffNoChgFlag(String staffNoChgFlag) {
        this.staffNoChgFlag = staffNoChgFlag;
    }

    public String getNationalityChgFlag() {
        return nationalityChgFlag;
    }

    public void setNationalityChgFlag(String nationalityChgFlag) {
        this.nationalityChgFlag = nationalityChgFlag;
    }

    public String getResidenceStatusChgFlag() {
        return residenceStatusChgFlag;
    }

    public void setResidenceStatusChgFlag(String residenceStatusChgFlag) {
        this.residenceStatusChgFlag = residenceStatusChgFlag;
    }

    public String getMobileNoChgFlag() {
        return mobileNoChgFlag;
    }

    public void setMobileNoChgFlag(String mobileNoChgFlag) {
        this.mobileNoChgFlag = mobileNoChgFlag;
    }

    public String getPanGirNoChgFlag() {
        return panGirNoChgFlag;
    }

    public void setPanGirNoChgFlag(String panGirNoChgFlag) {
        this.panGirNoChgFlag = panGirNoChgFlag;
    }

    public String getMinorChgFlag() {
        return minorChgFlag;
    }

    public void setMinorChgFlag(String minorChgFlag) {
        this.minorChgFlag = minorChgFlag;
    }

    public String getGrdCustIdChgFlag() {
        return grdCustIdChgFlag;
    }

    public void setGrdCustIdChgFlag(String grdCustIdChgFlag) {
        this.grdCustIdChgFlag = grdCustIdChgFlag;
    }

    public String getMinorMajorDtChgFlag() {
        return minorMajorDtChgFlag;
    }

    public void setMinorMajorDtChgFlag(String minorMajorDtChgFlag) {
        this.minorMajorDtChgFlag = minorMajorDtChgFlag;
    }

    public String getRegTypeChgFlag() {
        return regTypeChgFlag;
    }

    public void setRegTypeChgFlag(String regTypeChgFlag) {
        this.regTypeChgFlag = regTypeChgFlag;
    }

    public String getAdhaarNoChgFlag() {
        return adhaarNoChgFlag;
    }

    public void setAdhaarNoChgFlag(String adhaarNoChgFlag) {
        this.adhaarNoChgFlag = adhaarNoChgFlag;
    }

    public String getLpgNoChgFlag() {
        return lpgNoChgFlag;
    }

    public void setLpgNoChgFlag(String lpgNoChgFlag) {
        this.lpgNoChgFlag = lpgNoChgFlag;
    }

    public String getPrimaryAcChgFlag() {
        return primaryAcChgFlag;
    }

    public void setPrimaryAcChgFlag(String primaryAcChgFlag) {
        this.primaryAcChgFlag = primaryAcChgFlag;
    }

    public String getManDtFlagChgFlag() {
        return manDtFlagChgFlag;
    }

    public void setManDtFlagChgFlag(String manDtFlagChgFlag) {
        this.manDtFlagChgFlag = manDtFlagChgFlag;
    }

    public String getManDatChgFlag() {
        return manDatChgFlag;
    }

    public void setManDatChgFlag(String manDatChgFlag) {
        this.manDatChgFlag = manDatChgFlag;
    }

    public String getCommPrefChgFlag() {
        return commPrefChgFlag;
    }

    public void setCommPrefChgFlag(String commPrefChgFlag) {
        this.commPrefChgFlag = commPrefChgFlag;
    }

    public String getMaritalStatusChgFlag() {
        return maritalStatusChgFlag;
    }

    public void setMaritalStatusChgFlag(String maritalStatusChgFlag) {
        this.maritalStatusChgFlag = maritalStatusChgFlag;
    }

    public List<SelectItem> getAcHolderTypeFlagList() {
        return acHolderTypeFlagList;
    }

    public void setAcHolderTypeFlagList(List<SelectItem> acHolderTypeFlagList) {
        this.acHolderTypeFlagList = acHolderTypeFlagList;
    }

    public List<SelectItem> getAcHolderTypeList() {
        return acHolderTypeList;
    }

    public void setAcHolderTypeList(List<SelectItem> acHolderTypeList) {
        this.acHolderTypeList = acHolderTypeList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAcHolderTypeFlag() {
        return acHolderTypeFlag;
    }

    public void setAcHolderTypeFlag(String acHolderTypeFlag) {
        this.acHolderTypeFlag = acHolderTypeFlag;
    }

    public String getAcHolderType() {
        return acHolderType;
    }

    public void setAcHolderType(String acHolderType) {
        this.acHolderType = acHolderType;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getcKYCNo() {
        return cKYCNo;
    }

    public void setcKYCNo(String cKYCNo) {
        this.cKYCNo = cKYCNo;
    }

    public String getFatherMiddleName() {
        return fatherMiddleName;
    }

    public void setFatherMiddleName(String fatherMiddleName) {
        this.fatherMiddleName = fatherMiddleName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getSpouseMiddleName() {
        return spouseMiddleName;
    }

    public void setSpouseMiddleName(String spouseMiddleName) {
        this.spouseMiddleName = spouseMiddleName;
    }

    public String getSpouseLastName() {
        return spouseLastName;
    }

    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }

    public String getMotherMiddleName() {
        return motherMiddleName;
    }

    public void setMotherMiddleName(String motherMiddleName) {
        this.motherMiddleName = motherMiddleName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getAcHolderTypeFlagChgFlag() {
        return acHolderTypeFlagChgFlag;
    }

    public void setAcHolderTypeFlagChgFlag(String acHolderTypeFlagChgFlag) {
        this.acHolderTypeFlagChgFlag = acHolderTypeFlagChgFlag;
    }

    public String getAcHolderTypeChgFlag() {
        return acHolderTypeChgFlag;
    }

    public void setAcHolderTypeChgFlag(String acHolderTypeChgFlag) {
        this.acHolderTypeChgFlag = acHolderTypeChgFlag;
    }

    public String getAcTypeChgFlag() {
        return acTypeChgFlag;
    }

    public void setAcTypeChgFlag(String acTypeChgFlag) {
        this.acTypeChgFlag = acTypeChgFlag;
    }

    public String getFatherMiddleNameChgFlag() {
        return fatherMiddleNameChgFlag;
    }

    public void setFatherMiddleNameChgFlag(String fatherMiddleNameChgFlag) {
        this.fatherMiddleNameChgFlag = fatherMiddleNameChgFlag;
    }

    public String getFatherLastNameChgFlag() {
        return fatherLastNameChgFlag;
    }

    public void setFatherLastNameChgFlag(String fatherLastNameChgFlag) {
        this.fatherLastNameChgFlag = fatherLastNameChgFlag;
    }

    public String getSpouseMiddleNameChgFlag() {
        return spouseMiddleNameChgFlag;
    }

    public void setSpouseMiddleNameChgFlag(String spouseMiddleNameChgFlag) {
        this.spouseMiddleNameChgFlag = spouseMiddleNameChgFlag;
    }

    public String getSpouseLastNameChgFlag() {
        return spouseLastNameChgFlag;
    }

    public void setSpouseLastNameChgFlag(String spouseLastNameChgFlag) {
        this.spouseLastNameChgFlag = spouseLastNameChgFlag;
    }

    public String getMotherMiddleNameChgFlag() {
        return motherMiddleNameChgFlag;
    }

    public void setMotherMiddleNameChgFlag(String motherMiddleNameChgFlag) {
        this.motherMiddleNameChgFlag = motherMiddleNameChgFlag;
    }

    public String getMotherLastNameChgFlag() {
        return motherLastNameChgFlag;
    }

    public void setMotherLastNameChgFlag(String motherLastNameChgFlag) {
        this.motherLastNameChgFlag = motherLastNameChgFlag;
    }

    public List<SelectItem> getCustEntityTypeList() {
        return custEntityTypeList;
    }

    public void setCustEntityTypeList(List<SelectItem> custEntityTypeList) {
        this.custEntityTypeList = custEntityTypeList;
    }

    public String getCustEntityType() {
        return custEntityType;
    }

    public void setCustEntityType(String custEntityType) {
        this.custEntityType = custEntityType;
    }

    public String getCustEntityTypeChgFlag() {
        return custEntityTypeChgFlag;
    }

    public void setCustEntityTypeChgFlag(String custEntityTypeChgFlag) {
        this.custEntityTypeChgFlag = custEntityTypeChgFlag;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getIsdCodeChgFlag() {
        return isdCodeChgFlag;
    }

    public void setIsdCodeChgFlag(String isdCodeChgFlag) {
        this.isdCodeChgFlag = isdCodeChgFlag;
    }

    public boolean isFatherSpouseFlag() {
        return fatherSpouseFlag;
    }

    public void setFatherSpouseFlag(boolean fatherSpouseFlag) {
        this.fatherSpouseFlag = fatherSpouseFlag;
    }

    public boolean isFatherSpouseFlagDisable() {
        return fatherSpouseFlagDisable;
    }

    public void setFatherSpouseFlagDisable(boolean fatherSpouseFlagDisable) {
        this.fatherSpouseFlagDisable = fatherSpouseFlagDisable;
    }

    public boolean isCustFullNameMismatchFlag() {
        return custFullNameMismatchFlag;
    }

    public void setCustFullNameMismatchFlag(boolean custFullNameMismatchFlag) {
        this.custFullNameMismatchFlag = custFullNameMismatchFlag;
    }

    public String getDobOrIncorpLebal() {
        return dobOrIncorpLebal;
    }

    public void setDobOrIncorpLebal(String dobOrIncorpLebal) {
        this.dobOrIncorpLebal = dobOrIncorpLebal;
    }

    public String getGstIdentificationNumber() {
        return gstIdentificationNumber;
    }

    public void setGstIdentificationNumber(String gstIdentificationNumber) {
        this.gstIdentificationNumber = gstIdentificationNumber;
    }

    public String getGstinChgFlag() {
        return gstinChgFlag;
    }

    public void setGstinChgFlag(String gstinChgFlag) {
        this.gstinChgFlag = gstinChgFlag;
    }

    public List<SelectItem> getModeTypeOption() {
        return modeTypeOption;
    }

    public void setModeTypeOption(List<SelectItem> modeTypeOption) {
        this.modeTypeOption = modeTypeOption;
    }

    public String getModeTypeChgFlag() {
        return modeTypeChgFlag;
    }

    public void setModeTypeChgFlag(String modeTypeChgFlag) {
        this.modeTypeChgFlag = modeTypeChgFlag;
    }

    public List<SelectItem> getBankOption() {
        return bankOption;
    }

    public void setBankOption(List<SelectItem> bankOption) {
        this.bankOption = bankOption;
    }

    public String getBankChgFlag() {
        return bankChgFlag;
    }

    public void setBankChgFlag(String bankChgFlag) {
        this.bankChgFlag = bankChgFlag;
    }

    public String getAadhaarMode() {
        return aadhaarMode;
    }

    public void setAadhaarMode(String aadhaarMode) {
        this.aadhaarMode = aadhaarMode;
    }

    public String getAadhaarBankIin() {
        return aadhaarBankIin;
    }

    public void setAadhaarBankIin(String aadhaarBankIin) {
        this.aadhaarBankIin = aadhaarBankIin;
    }

    public RelatedPerson getRelatedInformation() {
        return relatedInformation;
    }

    public void setRelatedInformation(RelatedPerson relatedInformation) {
        this.relatedInformation = relatedInformation;
    }

    public String getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }

    
    
}
