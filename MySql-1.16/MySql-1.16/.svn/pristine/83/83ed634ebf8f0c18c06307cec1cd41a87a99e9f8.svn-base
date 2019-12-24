/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.RelatedPersonsInfoTable;
import com.cbs.dto.customer.CBSCustIdentityDetailsTo;
import com.cbs.dto.customer.CBSCustMISInfoTO;
import com.cbs.dto.customer.CBSCustMinorInfoTO;
import com.cbs.dto.customer.CBSCustNREInfoTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.customer.CBSRelatedPersonsDetailsPKTO;
import com.cbs.dto.customer.CBSRelatedPersonsDetailsTO;
import com.cbs.dto.customer.CbsCustAdditionalAddressDetailsTo;
import com.cbs.entity.master.Securityinfo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountEditCloseFacadeRemote;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.checkdigit.VerhoeffCheckDigit;

public class OperationCustomerMaster {

    BsLimitInfo bsLimitInfo;
    CurrencyInfo currencyInfo;
    CustAddressInfo custAddressInfo;
    CustGeneralInfo custGeneralInfo;
    CustGeneralInformation custGeneralInformation;
    CustPersonalDetails custPersonalDetails;
    KycDetails kycDetails;
    MinorInfo minorInfo;
    MisInfo misInfo;
    NreInfo nreInfo;
    OtherInfo otherInfo;
    RelatedPersonInfo relatedPersonInfo;
    //added by rahul
    RelatedPerson relatedperson;
    //
    TradeFinanceInfo tradeFinanceInfo;
    CustomerDetail customerDetail;
    CustAdditionalAddress custAdditionalAddress;
    CustomerMasterDelegate masterDelegate;
    private String str;
    private final String ftsJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    private CommonReportMethodsRemote commanRemot = null;
    private AccountEditCloseFacadeRemote actEditRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern alphaNumericWithoutSpace = Pattern.compile("[A-Za-z0-9]*");
    Pattern amtPattern = Pattern.compile("\\d+([.]\\d{1,2})?"); //two digit after decimal
    Pattern onlyAlphabetWitSpace = Pattern.compile("[A-Za-z ]*");
    Pattern onlyAlphabetWitoutSpace = Pattern.compile("[A-Za-z]*");
    Pattern onlyNumeric = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

    public OperationCustomerMaster() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(ftsJndiName);
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
            actEditRemote = (AccountEditCloseFacadeRemote) ServiceLocator.getInstance().lookup("AccountEditCloseFacade");
            commanRemot = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public void inquiryResult() {
        custPersonalDetails.setNamesDisable(false);
        customerDetail.setErrorMsg("");
        try {
            //Related Person Information
            relatedPersonInfo.setFieldDisable(true);
            relatedPersonInfo.setOtherFieldDisable(true);
            relatedPersonInfo.setSelVisible(false);
            relatedPersonInfo.setDelVisible(false);
            //end here
            //related person added by rahul
            relatedperson.setOtherFieldDisable(false);
            relatedperson.setSelVisible(false);
            relatedperson.setDelVisible(false);

            if (getCustomerDetail().getFunction().equalsIgnoreCase("v")) {
                if (customerDetail.getVerifyCustId().equalsIgnoreCase("0")) {
                    customerDetail.setErrorMsg("Please select a customer id first !");
                    return;
                }
                customerDetail.setMapMessage("");
                customerDetail.setCustId(customerDetail.getVerifyCustId());
                customerDetail.setMapMessage(customerDetail.getMap().get(customerDetail.getCustId()));
                if (!isLevelIdMatched()) {
                    customerDetail.setErrorMsg("Sorry! You are not authorized person for verifing this customer!");
                    return;
                }
                CBSCustomerMasterDetailTO custDetailsByCustId = masterDelegate.getCustDetailsByCustId(customerDetail.getCustId());
                if (custDetailsByCustId.getLastChangeUserID().equalsIgnoreCase(customerDetail.getUserName())) {
                    customerDetail.setErrorMsg("Sorry! You can not verify your own entry!");
                    return;
                }

            } else if (getCustomerDetail().getFunction().equalsIgnoreCase("i")) {
                if (customerDetail.getCustId() == null || customerDetail.getCustId().equalsIgnoreCase("")) {
                    customerDetail.setErrorMsg("Please enter customer id ! ");
                    return;
                }
            } else if (getCustomerDetail().getFunction().equalsIgnoreCase("s")
                    || getCustomerDetail().getFunction().equalsIgnoreCase("u")
                    || getCustomerDetail().getFunction().equalsIgnoreCase("m")) {
                CBSCustomerMasterDetailTO custDetailsByCustId = masterDelegate.getCustDetailsByCustId(customerDetail.getCustId());
                if (customerDetail.getCustId() == null || customerDetail.getCustId().equalsIgnoreCase("")) {
                    customerDetail.setErrorMsg("Please enter customer id ! ");
                    return;
                }
                if (!customerDetail.getOrgnBrCode().equalsIgnoreCase(custDetailsByCustId.getPrimaryBrCode())) {
                    customerDetail.setErrorMsg("Sorry! This customer id is not related to your branch. Primary Branch--> "
                            + commanRemot.getBranchNameByBrncode(custDetailsByCustId.getPrimaryBrCode()));
                    return;
                }
                if (custDetailsByCustId.getSuspensionFlg().equals("S") || custDetailsByCustId.getSuspensionFlg().equals("Y")) {
                    customerDetail.setErrorMsg("Sorry! This customer is suspended.");
                    return;
                }
//                if (ftsRemote.getBankCode().equalsIgnoreCase("ccbl")) {
//                    custPersonalDetails.setCustNameDisable(true);
//                }
                if (ftsRemote.getCodeForReportName("CUSTNAME-CHANGE") == 0) { //0- Name will not change
                    custPersonalDetails.setCustNameDisable(true);
                }

                //Related Person Information
                if (getCustomerDetail().getFunction().equalsIgnoreCase("M")) {
                    relatedPersonInfo.setFieldDisable(false);
                    relatedPersonInfo.setOtherFieldDisable(false);
                    //Retrieve all related Persons
//                    relatedPersonInfo.setRpiTableList(customerMgmtRemote.getAllRelatedPersons(getCustomerDetail().getCustId()));
                    relatedPersonInfo.setSelVisible(true);
                    relatedPersonInfo.setDelVisible(true);

                    relatedperson.setOtherFieldDisable(false);

                    //CCBL CustName Updation Stop
//                    String bankCode = customerMgmtRemote.getBankCode();
//                    if (bankCode.equalsIgnoreCase("CCBL")) {
//                        custPersonalDetails.setNamesDisable(true);
//                    }

                    if (ftsRemote.getCodeForReportName("CUSTNAME-CHANGE") == 0) {
                        custPersonalDetails.setNamesDisable(true);
                    }
                    /*End Here*/
                }
            }
            inquiryResult1();
            if (custPersonalDetails.getNriType().equalsIgnoreCase("N")) {
                custPersonalDetails.flagForsave = true;
            } else {
                custPersonalDetails.flagForsave = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public void inquiryResult1() {
        try {
            custPersonalDetails.selectFieldValues();
            nreInfo.selectFieldValues();
            custGeneralInfo.selectFieldValues();
            custAddressInfo.selectFieldValues();
            misInfo.selectFieldValues();
            relatedPersonInfo.selectFieldValues();
            custAdditionalAddress.selectFieldValues();
            //added by rahul
            relatedperson.selectFieldValues();

//            minorInfo.selectFieldValues();
//            custPersonalDetails.selectFieldValues();
//            custGeneralInformation.selectFieldValues();
//            currencyInfo.selectFieldValues();
//            tradeFinanceInfo.selectFieldValues();
//            bsLimitInfo.selectFieldValues();
//            otherInfo.selectFieldValues();
//            kycDetails.selectFieldValues();
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());

        }
    }

    public void callFromMainButton() {
        try {
            if (customerDetail.getFunction().equalsIgnoreCase("M")) {
                if (custPersonalDetails.getcustomerFullNameMismatchFlag()) {
                    setFormsDataInTos("mainBtn");
                }
            } else {
                custPersonalDetails.custFullNameMismatchFlag = true;
                setFormsDataInTos("mainBtn");
            }
        } catch (Exception e) {
            e.printStackTrace();
            customerDetail.setErrorMsg(e.getMessage());
        }
    }

    public void callFromAlertButton() {
        try {
            setFormsDataInTos("alertBtn");
        } catch (Exception e) {
            e.printStackTrace();
            customerDetail.setErrorMsg(e.getMessage());
        }
    }

    public void callFromBadPersonAlertButton() {
        try {
            setFormsDataInTos("BadPersonAlertBtn");
        } catch (Exception e) {
            e.printStackTrace();
            customerDetail.setErrorMsg(e.getMessage());
        }
    }

    public void callFromCustFullNameMismatch() {
        custPersonalDetails.custFullNameMismatchFlag = true;
        if (custPersonalDetails.custFullNameMismatchFlag) {
            this.setFormsDataInTos("mainBtn");
        }
    }

    public void setFormsDataInTos(String btnPosition) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String errMsg = "";
        getCustomerDetail().setChqAlertFlag(false);
        getCustomerDetail().setConfirmationMsg("");
        getCustomerDetail().setBadPersonAlertFlag(false);
        try {
            if (getCustomerDetail().getFunction().equalsIgnoreCase("A")
                    || getCustomerDetail().getFunction().equalsIgnoreCase("M")) {
                if (getCustomerDetail().getFunction().equalsIgnoreCase("M")) {
                    CBSCustomerMasterDetailTO custDetailsByCustId = masterDelegate.getCustDetailsByCustId(customerDetail.getCustId());
                    if (!customerDetail.getOrgnBrCode().equalsIgnoreCase(custDetailsByCustId.getPrimaryBrCode())) {
                        customerDetail.setErrorMsg("Sorry! This customer id is not related to your branch. Primary Branch--> "
                                + commanRemot.getBranchNameByBrncode(custDetailsByCustId.getPrimaryBrCode()));
                        return;
                    }
                    //Addition on 20/10/2015
                    String custCurrentStatus = customerMgmtRemote.retrieveCurrentCustomerStatus(customerDetail.getCustId());
                    if (custCurrentStatus.equalsIgnoreCase("Y") || custDetailsByCustId.getSuspensionFlg().equals("S")) {    //Suspended
                        customerDetail.setErrorMsg("Sorry! This customer is suspended.");
                        return;
                    }
                    //End here
                }
                //Addition on 20/10/2015
                String alertMsg = custPersonalDetails.validateCustomerExistence(getCustomerDetail().getFunction().trim(),
                        customerDetail.getCustId().trim());
                if (!alertMsg.equalsIgnoreCase("ok") && btnPosition.equalsIgnoreCase("mainBtn")) {
                    getCustomerDetail().setChqAlertFlag(true);
                    getCustomerDetail().setConfirmationMsg(alertMsg);
                    return;
                } else {
                    getCustomerDetail().setChqAlertFlag(false);
                    getCustomerDetail().setConfirmationMsg("");
                }
                //End here
                errMsg = custPersonalDetails.validation();
                if (errMsg.equalsIgnoreCase("ok")) {
                    errMsg = nreInfo.validation();
                }
                if (errMsg.equalsIgnoreCase("ok")) {
                    errMsg = validateCustGeneralInfo();
                }
                if (errMsg.equalsIgnoreCase("ok")) {
                    errMsg = custAddressInfo.custAddressValidation();
                }
                if (errMsg.equalsIgnoreCase("ok")) {
                    errMsg = misInfo.misInfoValidation();
                }
//                if (errMsg.equalsIgnoreCase("ok")) {
//                    errMsg = otherInfo.onblurThreshUpdateDate();
//                }
                if (errMsg.equalsIgnoreCase("ok")) {
                    //FOR PERSONAL INFORMATION
                    CBSCustomerMasterDetailTO customerMasterDetailTO = new CBSCustomerMasterDetailTO();
                    Set customerImageCaptureSet = new HashSet();
                    customerMasterDetailTO.setCustomerid(customerDetail.getCustId());
                    customerMasterDetailTO.setTitle(custPersonalDetails.getTitleType());
                    customerMasterDetailTO.setCustname(custPersonalDetails.getCustFirstName());
                    customerMasterDetailTO.setMiddleName(custPersonalDetails.getMiddleName() == null ? "" : custPersonalDetails.getMiddleName());
                    customerMasterDetailTO.setLastName(custPersonalDetails.getLastName() == null ? "" : custPersonalDetails.getLastName());
                    customerMasterDetailTO.setCustFullName(custPersonalDetails.getCustFullName() == null ? "" : custPersonalDetails.getCustFullName());
                    customerMasterDetailTO.setGstIdentificationNumber(custPersonalDetails.getGstIdentificationNumber() == null ? "" : custPersonalDetails.getGstIdentificationNumber());
                    //                    customerMasterDetailTO.setShortname(custPersonalDetails.getShortName());
                    customerMasterDetailTO.setGender(custPersonalDetails.getGenderType());
                    customerMasterDetailTO.setFathername(custPersonalDetails.getFathername() == null ? "" : custPersonalDetails.getFathername());
                    customerMasterDetailTO.setMothername(custPersonalDetails.getMotherName() == null ? "" : custPersonalDetails.getMotherName());
                    Date perDob = null;
                    if (custPersonalDetails.getPerDateOfBirth() == null) {
                        perDob = new Date();
                    } else {
                        perDob = custPersonalDetails.getPerDateOfBirth();
                    }
                    customerMasterDetailTO.setDateOfBirth(perDob);
                    customerMasterDetailTO.setMaritalstatus(custPersonalDetails.getCustEntityType().equals("01") ? custPersonalDetails.getMaritalType() == null || custPersonalDetails.getMaritalType().equals("0") ? "" : custPersonalDetails.getMaritalType() : "");
                    customerMasterDetailTO.setSpouseName(custPersonalDetails.getSpouseName() == null ? "" : custPersonalDetails.getSpouseName());
                    customerMasterDetailTO.setMaidenName(custPersonalDetails.getMaidenName());
//                    customerMasterDetailTO.setCustStatus(custPersonalDetails.getCustStatusInPersnlInfo());
                    customerMasterDetailTO.setStaffflag(custPersonalDetails.getStaffType());
                    customerMasterDetailTO.setStaffid(custPersonalDetails.getStaffNo());
                    customerMasterDetailTO.setNationality(custPersonalDetails.getNationality() == null || custPersonalDetails.getNationality().equals("0") ? "" : custPersonalDetails.getNationality());
                    customerMasterDetailTO.setNriflag(custPersonalDetails.getNriType() == null || custPersonalDetails.getNriType().equals("0") ? "" : custPersonalDetails.getNriType());
                    customerMasterDetailTO.setMobilenumber(custPersonalDetails.getMobNo());
                    customerMasterDetailTO.setVirtualId(custPersonalDetails.getVirtualId() == null ? "" : custPersonalDetails.getVirtualId());
                    customerMasterDetailTO.setCommunicationPreference(custPersonalDetails.getCommuPreference());
                    customerMasterDetailTO.setpANGIRNumber(custPersonalDetails.getPanGirNo());
                    customerMasterDetailTO.setMinorflag(custPersonalDetails.getMinorType());
                    //Addition 05/01/2016                 
                    customerMasterDetailTO.setCustEntityType(custPersonalDetails.getCustEntityType());
                    customerMasterDetailTO.setAcHolderTypeFlag(custPersonalDetails.getAcHolderTypeFlag() == null || custPersonalDetails.getAcHolderTypeFlag().equals("0") ? "" : custPersonalDetails.getAcHolderTypeFlag());
                    customerMasterDetailTO.setAcHolderType(custPersonalDetails.getAcHolderType() == null || custPersonalDetails.getAcHolderType().equals("0") ? "" : custPersonalDetails.getAcHolderType());
                    customerMasterDetailTO.setAcType(custPersonalDetails.getAcType() == null || custPersonalDetails.getAcType().equals("0") ? "" : custPersonalDetails.getAcType());
                    customerMasterDetailTO.setcKYCNo(custPersonalDetails.getcKYCNo() == null ? "" : custPersonalDetails.getcKYCNo());
                    customerMasterDetailTO.setFatherMiddleName(custPersonalDetails.getFatherMiddleName() == null ? "" : custPersonalDetails.getFatherMiddleName());
                    customerMasterDetailTO.setFatherLastName(custPersonalDetails.getFatherLastName() == null ? "" : custPersonalDetails.getFatherLastName());
                    customerMasterDetailTO.setSpouseMiddleName(custPersonalDetails.getSpouseMiddleName() == null ? "" : custPersonalDetails.getSpouseMiddleName());
                    customerMasterDetailTO.setSpouseLastName(custPersonalDetails.getSpouseLastName() == null ? "" : custPersonalDetails.getSpouseLastName());
                    customerMasterDetailTO.setMotherMiddleName(custPersonalDetails.getMotherMiddleName() == null ? "" : custPersonalDetails.getMotherMiddleName());
                    customerMasterDetailTO.setMotherLastName(custPersonalDetails.getMotherLastName() == null ? "" : custPersonalDetails.getMotherLastName());
                    customerMasterDetailTO.setIsdCode(custPersonalDetails.getIsdCode());
                    customerMasterDetailTO.setFatherSpouseFlag(custPersonalDetails.getCustEntityType().equalsIgnoreCase("01") ? custPersonalDetails.isFatherSpouseFlag() ? "Y" : "N" : custPersonalDetails.isFatherSpouseFlag() ? "" : "");
                    //AADHAAR NO ADDITION
                    customerMasterDetailTO.setRegType(custPersonalDetails.getRegType() == null ? "0" : custPersonalDetails.getRegType());
                    customerMasterDetailTO.setAdhaarNo(custPersonalDetails.getAdhaarNo());
                    customerMasterDetailTO.setLpgId(custPersonalDetails.getLpgId());
                    customerMasterDetailTO.setAdhaarLpgAcno("");
                    if (getCustomerDetail().getFunction().equalsIgnoreCase("M")) {
                        customerMasterDetailTO.setAdhaarLpgAcno(custPersonalDetails.getAdhaarLpgAcno());
                        if ((custPersonalDetails.getAdhaarNo() == null || custPersonalDetails.getAdhaarNo().equals("") || custPersonalDetails.getAdhaarNo().length() == 0)
                                && (custPersonalDetails.getLpgId() == null || custPersonalDetails.getLpgId().equals("") || custPersonalDetails.getLpgId().length() == 0)) {
                            customerMasterDetailTO.setAdhaarLpgAcno("");
                        }
                    }

                    customerMasterDetailTO.setMandateFlag(custPersonalDetails.getMandateFlag());
                    customerMasterDetailTO.setMandateDt(custPersonalDetails.getMandateDt() == null ? "" : sdf.format(custPersonalDetails.getMandateDt()));
                    customerMasterDetailTO.setAadhaarMode(custPersonalDetails.getAadhaarMode() == null ? "" : custPersonalDetails.getAadhaarMode());
                    customerMasterDetailTO.setAadhaarBankIin(custPersonalDetails.getAadhaarBankIin() == null ? "" : custPersonalDetails.getAadhaarBankIin());

                    //FOR CUSTGENERAL INFO
//                    customerMasterDetailTO.setAccountManager(custGeneralInfo.getAccountManager());
//                    customerMasterDetailTO.setAllowSweeps(custGeneralInfo.getAllowSweeps());
//                    customerMasterDetailTO.setTradeFinanceFlag(custGeneralInfo.getTradeFinanceCustomer());
//                    customerMasterDetailTO.setSwiftCodeStatus(custGeneralInfo.getSwiftCodeStatus());
//                    customerMasterDetailTO.setSwiftCode(custGeneralInfo.getSwiftCode());
//                    customerMasterDetailTO.setBcbfid(custGeneralInfo.getBcbfId());
//                    customerMasterDetailTO.setCombinedStmtFlag(custGeneralInfo.getCombinedStatement());
//                    customerMasterDetailTO.setStmtFreqType(custGeneralInfo.getStatementFrequency());
//                    customerMasterDetailTO.setStmtFreqWeekNo(custGeneralInfo.getStatementFreqWeekNo());
//                    customerMasterDetailTO.setStmtFreqWeekDay(custGeneralInfo.getStatementFreWeekDay());
//                    customerMasterDetailTO.setStmtFreqStartDate(custGeneralInfo.getStatementFrequencyDate() != null ? ymd.format(custGeneralInfo.getStatementFrequencyDate()) : "");
//                    customerMasterDetailTO.setStmtFreqNP(custGeneralInfo.getStatementFreNp());
//                    customerMasterDetailTO.setSalary(custGeneralInfo.getSalary() != null && !custGeneralInfo.getSalary().toString().equalsIgnoreCase("") ? Double.parseDouble(custGeneralInfo.getSalary()) : 0.0);
                    //-----------------------Not in Use----------------------------
                    customerMasterDetailTO.setVoterIDNo(custGeneralInfo.getVoterIdNo());
                    customerMasterDetailTO.setNregaJobCard(custGeneralInfo.getNregaJobCard());
                    customerMasterDetailTO.setDrivingLicenseNo(custGeneralInfo.getDrivingLiscNo());
                    customerMasterDetailTO.setDlExpiry(
                            (custGeneralInfo.getDlExpiry() == null)
                            ? dmy.format(date) : dmy.format(custGeneralInfo.getDlExpiry()));
                    customerMasterDetailTO.setPassportNo(custGeneralInfo.getPassportNo());
                    customerMasterDetailTO.setExpirydate(
                            (custGeneralInfo.getPassportExpDt() == null)
                            ? date : custGeneralInfo.getPassportExpDt());
                    customerMasterDetailTO.setTan(custGeneralInfo.getTanNo());
                    customerMasterDetailTO.setCin(custGeneralInfo.getCinNo());
                    customerMasterDetailTO.settINNumber(custGeneralInfo.getTinNo());

                    //-----------------------Not in Use--End----------------------------
                    customerMasterDetailTO.setLegalDocument(custGeneralInfo.getLegelPoiDoc());

                    customerMasterDetailTO.setIncomeRange(custGeneralInfo.getIncomeRange());
                    customerMasterDetailTO.setNetworth((custGeneralInfo.getNetworth() == null || custGeneralInfo.getNetworth().equals("")) ? Double.parseDouble("0")
                            : Double.parseDouble(custGeneralInfo.getNetworth()));
                    customerMasterDetailTO.setNetworthAsOn((custGeneralInfo.getNetworthAsOn() == null || custGeneralInfo.getNetworthAsOn().equals("")) ? "" : dmy.format(custGeneralInfo.getNetworthAsOn()));
                    customerMasterDetailTO.setQualification(custGeneralInfo.getQualification());
                    customerMasterDetailTO.setPoliticalExposed(custGeneralInfo.getExposed());
                    customerMasterDetailTO.setSalary((custGeneralInfo.getSalary() == null
                            || custGeneralInfo.getSalary().equals("") ? Double.parseDouble("0") : Double.parseDouble(custGeneralInfo.getSalary())));
                    //Addition on 23/11/2015
                    customerMasterDetailTO.setOtherIdentity(custGeneralInfo.getOtherIdentity() == null ? "" : custGeneralInfo.getOtherIdentity());
                    customerMasterDetailTO.setIdentityNo(custGeneralInfo.getIdentityNo());
                    customerMasterDetailTO.setIdExpiryDate(custGeneralInfo.getIdExpiryDate() == null ? "" : ymd.format(custGeneralInfo.getIdExpiryDate()));
                    customerMasterDetailTO.setTinIssuingCountry(custGeneralInfo.getTinIssuingCountry() == null || custGeneralInfo.getTinIssuingCountry().equals("0") ? "" : custGeneralInfo.getTinIssuingCountry());          //End here
                    //ADD ABOVE
//                    customerMasterDetailTO.setChargeStatus(custGeneralInfo.getChargeStatus());
//                    customerMasterDetailTO.setChargeLevelCode(custGeneralInfo.getChargeLevelCode());
//                    customerMasterDetailTO.setaBBChargeCode(custGeneralInfo.getAbbChargeCode());
//                    customerMasterDetailTO.setePSChargeCode(custGeneralInfo.getEpsChargeCode());
//                    customerMasterDetailTO.setPaperRemittance(custGeneralInfo.getPaperRemitence());
//                    customerMasterDetailTO.setDeliveryChannelChargeCode(custGeneralInfo.getDeliveryChannelChargeCode());
//                    customerMasterDetailTO.setAccountLevelCharges(custGeneralInfo.getAcctLevelChanges());
//                    customerMasterDetailTO.setCustLevelCharges(custGeneralInfo.getCustLevelChanges());
//                    customerMasterDetailTO.setTaxSlab(custGeneralInfo.getTaxSlab());
//                    customerMasterDetailTO.setiTFileNo(custGeneralInfo.getItFileNo());
//                    customerMasterDetailTO.settDSCode(custGeneralInfo.getTdsCode());
//                    customerMasterDetailTO.settDSCustomerId(custGeneralInfo.getTdsCustId());
//                    customerMasterDetailTO.settDSFormReceiveDate(custGeneralInfo.getTdsFormRcvdate());
//                    customerMasterDetailTO.settDSExemptionReferenceNo(custGeneralInfo.getTdsExempRefNo());
//                    customerMasterDetailTO.settDSExemptionEndDate(custGeneralInfo.getTdsExemEndDate());
//                    customerMasterDetailTO.settDSFloorLimit(custGeneralInfo.getCustFloorLim() != null && !custGeneralInfo.getCustFloorLim().toString().equalsIgnoreCase("") ? Double.parseDouble(custGeneralInfo.getCustFloorLim()) : 0.0);
//
//                    customerMasterDetailTO.settINNumber(custGeneralInfo.getTinNo());
//                    customerMasterDetailTO.setSalesTaxNo(custGeneralInfo.getSalesTaxNo());
//                    customerMasterDetailTO.setExciseNo(custGeneralInfo.getExciseNo());
//                    customerMasterDetailTO.setSalesTaxNo(custGeneralInfo.getSalesTaxNo());
                    //for custGeneralInformation Tab
//                    customerMasterDetailTO.setIntroCustomerId(custGeneralInformation.getIntroCusId());
//                    customerMasterDetailTO.setCustTitle(custGeneralInformation.getTitleType2());
//                    customerMasterDetailTO.setName(custGeneralInformation.getIntroName());
//                    customerMasterDetailTO.setAddressLine1(custGeneralInformation.getIntroAdd1());
//                    customerMasterDetailTO.setAddressLine2(custGeneralInformation.getIntroAdd2());
//                    customerMasterDetailTO.setVillage(custGeneralInformation.getIntroVill());
//                    customerMasterDetailTO.setBlock(custGeneralInformation.getIntroBlock());
//                    customerMasterDetailTO.setCityCode(custGeneralInformation.getIntroCity());
//                    customerMasterDetailTO.setStateCode(custGeneralInformation.getIntroState());
//                    customerMasterDetailTO.setPostalCode(custGeneralInformation.getIntroPostal());
//                    customerMasterDetailTO.setCountryCode(custGeneralInformation.getIntroCountry());
//
//                    customerMasterDetailTO.setPhoneNumber(custGeneralInformation.getIntroPhone());
//                    customerMasterDetailTO.setTelexNumber(custGeneralInformation.getIntroTelex());
//                    customerMasterDetailTO.setFaxNumber(custGeneralInformation.getIntroFax());
//                    customerMasterDetailTO.setFinancialYearAndMonth(custGeneralInformation.getCustFinanYear() + " " + custGeneralInformation.getCustFinanMon());
//                    customerMasterDetailTO.setFinancialYearAndMonth(null);
//                    customerMasterDetailTO.setCurrencyCodeType(custGeneralInformation.getCurrencyCode());
//                    customerMasterDetailTO.setBusinessAssets(Double.parseDouble(custGeneralInformation.getBusinessAssests() != null && !custGeneralInformation.getBusinessAssests().equalsIgnoreCase("") ? custGeneralInformation.getBusinessAssests() : "0.0"));
//                    customerMasterDetailTO.setPropertyAssets(Double.parseDouble(custGeneralInformation.getPropAssets() != null && !custGeneralInformation.getPropAssets().equalsIgnoreCase("") ? custGeneralInformation.getPropAssets() : "0.0"));
//                    customerMasterDetailTO.setInvestments(Double.parseDouble(custGeneralInformation.getInvestment() != null && !custGeneralInformation.getInvestment().equalsIgnoreCase("") ? custGeneralInformation.getInvestment() : "0.0"));
//                    customerMasterDetailTO.setNetworth(Double.parseDouble(custGeneralInformation.getNetWorth() != null && !custGeneralInformation.getNetWorth().equalsIgnoreCase("") ? custGeneralInformation.getNetWorth() : "0.0"));
//                    customerMasterDetailTO.setDeposits(Double.parseDouble(custGeneralInformation.getDeposits() != null && !custGeneralInformation.getDeposits().equalsIgnoreCase("") ? custGeneralInformation.getDeposits() : "0.0"));
//                    customerMasterDetailTO.setOtherBankCode(custGeneralInformation.getOtherBnkCode());
//                    customerMasterDetailTO.setLimitsWithOtherBank(Double.parseDouble(custGeneralInformation.getLimWithOtherBnk() != null && !custGeneralInformation.getLimWithOtherBnk().equalsIgnoreCase("") ? custGeneralInformation.getLimWithOtherBnk() : "0.0"));
//                    customerMasterDetailTO.setFundBasedLimit(Double.parseDouble(custGeneralInformation.getFunfBasedLim() != null && !custGeneralInformation.getFunfBasedLim().equalsIgnoreCase("") ? custGeneralInformation.getFunfBasedLim() : "0.0"));
//                    customerMasterDetailTO.setNonFundBasedLimit(Double.parseDouble(custGeneralInformation.getNonFundBasedLim() != null && !custGeneralInformation.getNonFundBasedLim().equalsIgnoreCase("") ? custGeneralInformation.getNonFundBasedLim() : "0.0"));
//                    customerMasterDetailTO.setOffLineCustDebitLimit(Double.parseDouble(custGeneralInformation.getOffLinecustDebitLim() != null && !custGeneralInformation.getOffLinecustDebitLim().equalsIgnoreCase("") ? custGeneralInformation.getOffLinecustDebitLim() : "0.0"));
//                    customerMasterDetailTO.setSalary(Double.parseDouble(custGeneralInformation.getSalary2() != null && !custGeneralInformation.getSalary2().equalsIgnoreCase("") ? custGeneralInformation.getSalary2() : "0.0"));
//                    customerMasterDetailTO.setCustFinancialDate(custGeneralInformation.getCustFinandate());
//                    customerMasterDetailTO.setCreditCard(custGeneralInformation.getCreditCard());
//                    customerMasterDetailTO.setCardNumber(custGeneralInformation.getCardNo());
//                    customerMasterDetailTO.setCardIssuer(custGeneralInformation.getCardIssuer());
//                    customerMasterDetailTO.setBankName(custGeneralInformation.getBnkName());
//                    customerMasterDetailTO.setAcctId(custGeneralInformation.getAccNo());
                    //FOR ADDRESS INFO TAB
                    //addition on 03/01/2017
                    customerMasterDetailTO.setPoa(custAddressInfo.getPrefPoaAdd());
                    customerMasterDetailTO.setPerAddType(custAddressInfo.getPerAddType());
                    //end
                    customerMasterDetailTO.setPerAddressLine1(custAddressInfo.getPerAdd1());
                    customerMasterDetailTO.setPerAddressLine2(custAddressInfo.getPerAdd2());
                    customerMasterDetailTO.setPerVillage(custAddressInfo.getPerVillage());
                    customerMasterDetailTO.setPerBlock(custAddressInfo.getPerBlock());
                    try {
                        String citycode = commanRemot.getRefRecCode("011", custAddressInfo.getPerDistrict());
                        this.custAddressInfo.setCityForPermanentAddress(citycode);
                    } catch (ApplicationException e) {
                        getCustomerDetail().setErrorMsg(e.getMessage());
                    }
                    customerMasterDetailTO.setPerCityCode(custAddressInfo.getCityForPermanentAddress());

                    customerMasterDetailTO.setPerCountryCode(custAddressInfo.getCountryForPermanentAddress());
                    if (custAddressInfo.getCountryForPermanentAddress().equalsIgnoreCase("IN")) {
                        customerMasterDetailTO.setPerStateCode(custAddressInfo.getStateForPermanentAddress());
                        customerMasterDetailTO.setPerDistrict(custAddressInfo.getPerDistrict());
                    } else {
                        customerMasterDetailTO.setPerStateCode(custAddressInfo.getPerStateNotIN());
                        customerMasterDetailTO.setPerDistrict(custAddressInfo.getPerDistrictNotIN());
                    }
                    customerMasterDetailTO.setPerPostalCode(custAddressInfo.getPerpostalCode());
                    customerMasterDetailTO.setPerPhoneNumber(custAddressInfo.getPerPhoneNo());
                    customerMasterDetailTO.setPerFaxNumber(custAddressInfo.getPerFaxNo());
                    customerMasterDetailTO.setPerEmail(custAddressInfo.getPerEmail());

                    //addition on 03/01/2017
                    customerMasterDetailTO.setPerMailAddSameFlagIndicate(custAddressInfo.isMailAddSameAsPerAdd() ? "Y" : "N");
                    customerMasterDetailTO.setMailPoa(custAddressInfo.getMailPoa());



                    customerMasterDetailTO.setMailAddType(custAddressInfo.getMailAddType());
                    //end
                    customerMasterDetailTO.setMailAddressLine1(custAddressInfo.getMailAdd1());
                    customerMasterDetailTO.setMailAddressLine2(custAddressInfo.getMailAdd2());
                    customerMasterDetailTO.setMailVillage(custAddressInfo.getMailVillage());
                    customerMasterDetailTO.setMailBlock(custAddressInfo.getMailBlock());
                    try {
                        String citycode = commanRemot.getRefRecCode("011", custAddressInfo.getMailDistrict());
                        this.custAddressInfo.setCityForMailAddress(citycode);
                    } catch (ApplicationException e) {
                        getCustomerDetail().setErrorMsg(e.getMessage());
                    }
                    customerMasterDetailTO.setMailCityCode(custAddressInfo.getCityForMailAddress());
                    customerMasterDetailTO.setMailCountryCode(custAddressInfo.getCountryForMailAddress());
                    if (custAddressInfo.getCountryForMailAddress().equalsIgnoreCase("IN")) {
                        customerMasterDetailTO.setMailStateCode(custAddressInfo.getStateForMailAddress());
                        customerMasterDetailTO.setMailDistrict(custAddressInfo.getMailDistrict());
                    } else {
                        customerMasterDetailTO.setMailStateCode(custAddressInfo.getMailStateNotIN());
                        customerMasterDetailTO.setMailDistrict(custAddressInfo.getMailDistrictNotIN());
                    }
                    customerMasterDetailTO.setMailPostalCode(custAddressInfo.getMailPostalCode());
                    customerMasterDetailTO.setMailPhoneNumber(custAddressInfo.getMailPhoneNo());
                    customerMasterDetailTO.setMailFaxNumber(custAddressInfo.getMailFax());
                    customerMasterDetailTO.setMailEmail(custAddressInfo.getMailEmail());

                    customerMasterDetailTO.setEmployerid(custAddressInfo.getEmpCode());
                    customerMasterDetailTO.setEmployeeNo(custAddressInfo.getEmpNo());
                    customerMasterDetailTO.setEmpAddressLine1(custAddressInfo.getEmpAdd1());
                    customerMasterDetailTO.setEmpAddressLine2(custAddressInfo.getEmpAdd2());
                    customerMasterDetailTO.setEmpVillage(custAddressInfo.getEmpVillage());
                    customerMasterDetailTO.setEmpBlock(custAddressInfo.getEmpTehsil());
                    customerMasterDetailTO.setEmpCityCode(custAddressInfo.getCityForEmpAddress());
                    customerMasterDetailTO.setEmpCountryCode(custAddressInfo.getCountryForEmpAddress());
                    if (custAddressInfo.getCountryForEmpAddress().equalsIgnoreCase("IN")) {
                        customerMasterDetailTO.setEmpStateCode(custAddressInfo.getStateForEmpAddress() == null || custAddressInfo.getStateForEmpAddress().equalsIgnoreCase("0") ? "" : custAddressInfo.getStateForEmpAddress());
                        customerMasterDetailTO.setEmpDistrict(custAddressInfo.getEmpDistrict() == null || custAddressInfo.getEmpDistrict().equalsIgnoreCase("0") ? "" : custAddressInfo.getEmpDistrict());
                    } else {
                        customerMasterDetailTO.setEmpStateCode(custAddressInfo.getEmpStateNotIN());
                        customerMasterDetailTO.setEmpDistrict(custAddressInfo.getEmpDistrictNotIN());
                    }
                    customerMasterDetailTO.setEmpPostalCode(custAddressInfo.getEmpPostalCode());
                    customerMasterDetailTO.setEmpPhoneNumber(custAddressInfo.getEmpPhoneNo());
                    customerMasterDetailTO.setEmpTelexNumber(custAddressInfo.getEmpTelexNo());
                    customerMasterDetailTO.setEmpFaxNumber(custAddressInfo.getEmpFax());
                    customerMasterDetailTO.setEmailID(custAddressInfo.getEmpEmail());
                    //addition on 03/01/2017
                    customerMasterDetailTO.setJuriAddBasedOnFlag(custAddressInfo.getJuriAddBasedOnFlag());
                    customerMasterDetailTO.setJuriPoa(custAddressInfo.getJuriPoa());
                    customerMasterDetailTO.setJuriAddType(custAddressInfo.getJuriAddType());
                    //end
                    customerMasterDetailTO.setJuriAdd1(custAddressInfo.getJuriAdd1());
                    customerMasterDetailTO.setJuriAdd2(custAddressInfo.getJuriAdd2());
                    customerMasterDetailTO.setJuriCity(custAddressInfo.getJuriCity());
                    customerMasterDetailTO.setJuriCountry(custAddressInfo.getJuriCountryCode());
                    if (custAddressInfo.getJuriCountryCode().equalsIgnoreCase("IN")) {
                        customerMasterDetailTO.setJuriState(custAddressInfo.getJuriState());
                        customerMasterDetailTO.setJuriDistrict(custAddressInfo.getJuriDistrict());
                    } else {
                        customerMasterDetailTO.setJuriState(custAddressInfo.getJuriStateNotIN());
                        customerMasterDetailTO.setJuriDistrict(custAddressInfo.getJuriDistrictNotIN());
                    }
                    customerMasterDetailTO.setJuriPostal(custAddressInfo.getJuriPostalCode());

                    customerMasterDetailTO.setPerOtherPOA(custAddressInfo.getPerOtherPOA());
                    customerMasterDetailTO.setMailOtherPOA(custAddressInfo.getMailOtherPOA());
                    customerMasterDetailTO.setJuriOtherPOA(custAddressInfo.getJuriOtherPOA());

                    //FOR CURRENCY INFO TAB
//                    customerMasterDetailTO.setCurrencyCodeType(currencyInfo.getCurrencyCodeForCurrInformation());
//                    List<CBSCustCurrencyInfoTO> currencyInfoTOs = new ArrayList<CBSCustCurrencyInfoTO>();
//                    for (CurrencyInfoTable pojo : currencyInfo.getListCurrInfoTab() == null ? new ArrayList<CurrencyInfoTable>() : currencyInfo.getListCurrInfoTab()) {
//                        CBSCustCurrencyInfoTO currencyInfoTO = new CBSCustCurrencyInfoTO();
//                        CBSCustCurrencyInfoPKTO custCurrencyInfoPKTO = new CBSCustCurrencyInfoPKTO();
//                        custCurrencyInfoPKTO.setCurrencyCodeType(pojo.getCurrencyCode());
//                        custCurrencyInfoPKTO.setCustomerId(customerDetail.getCustId());
//                        currencyInfoTO.setcBSCustCurrencyInfoPKTO(custCurrencyInfoPKTO);
//                        currencyInfoTO.setWithHoldingTaxLevel(pojo.getWithHoldingTaxLevel());
//                        currencyInfoTO.setWithHoldingTax(Double.parseDouble(pojo.getWithHoldTaxPer() != null && !pojo.getWithHoldTaxPer().equalsIgnoreCase("") ? pojo.getWithHoldTaxPer() : "0.0"));
//                        currencyInfoTO.setWithHoldingTaxLimit(Double.parseDouble(pojo.getWithHoldTaxLim() != null && !pojo.getWithHoldTaxLim().equalsIgnoreCase("") ? pojo.getWithHoldTaxLim() : "0.0"));
//                        currencyInfoTO.setPreferentialInterestTillDate(sdf.parse(pojo.getPreferInterestTillDate()));
//                        currencyInfoTO.settDSOperativeAccountID(pojo.getTdsOperativeAccIdEdit());
//                        currencyInfoTO.setCustinterestRateCredit(Double.parseDouble(pojo.getCustIntRateCredit() != null && !pojo.getCustIntRateCredit().equalsIgnoreCase("") ? pojo.getCustIntRateCredit() : "0.0"));
//                        currencyInfoTO.setCustInterestRateDebit(Double.parseDouble(pojo.getCustIntRateDebit() != null && !pojo.getCustIntRateDebit().equalsIgnoreCase("") ? pojo.getCustIntRateDebit() : "0.0"));
//                        currencyInfoTOs.add(currencyInfoTO);
//                    }
                    //for MisInfo tab
                    CBSCustMISInfoTO mISInfoTO = new CBSCustMISInfoTO();

                    mISInfoTO.setCustomerId(customerDetail.getCustId());
                    mISInfoTO.setOccupationCode(misInfo.getMisOccupation() == null || misInfo.getMisOccupation().equalsIgnoreCase("0") ? "" : misInfo.getMisOccupation());

//                    mISInfoTO.setIncorpDt(misInfo.getIncopDt() == null ? "" : ymd.format(misInfo.getIncopDt()));
                    if (custPersonalDetails.getCustEntityType().equalsIgnoreCase("02")) {
                        mISInfoTO.setIncorpDt(custPersonalDetails.getPerDateOfBirth() == null ? "" : ymd.format(custPersonalDetails.getPerDateOfBirth()));
                    } else {
                        mISInfoTO.setIncorpDt("");
                    }
                    if (misInfo.getCountryOfIncorp().equalsIgnoreCase("IN")) {
                        mISInfoTO.setStateOfIncorp(misInfo.getStateOfIncorp() == null || misInfo.getStateOfIncorp().equalsIgnoreCase("0") ? "" : misInfo.getStateOfIncorp());
                        mISInfoTO.setIncorpPlace(misInfo.getIncopPlace() == null || misInfo.getIncopPlace().equalsIgnoreCase("0") ? "" : misInfo.getIncopPlace());
                    } else {
                        mISInfoTO.setStateOfIncorp(misInfo.getStateOfIncorpNotIN() == null ? "" : misInfo.getStateOfIncorpNotIN());
                        mISInfoTO.setIncorpPlace(misInfo.getIncopPlaceNotIN() == null ? "" : misInfo.getIncopPlaceNotIN());
                    }
                    mISInfoTO.setCommDt(misInfo.getDcbDt() == null ? "" : ymd.format(misInfo.getDcbDt()));
                    mISInfoTO.setConstitutionCode(misInfo.getMisConstitution() == null || misInfo.getMisConstitution().equalsIgnoreCase("0") ? "" : misInfo.getMisConstitution());
                    mISInfoTO.setMisJuriResidence(misInfo.getMisIsoResidence() == null || misInfo.getMisIsoResidence().equalsIgnoreCase("0") ? "" : misInfo.getMisIsoResidence());
                    if (mISInfoTO.getMisJuriResidence().equalsIgnoreCase("IN")) {
                        mISInfoTO.setMisTin("");
                        mISInfoTO.setMisCity("");
                        mISInfoTO.setMisBirthCountry("");
                    } else {
                        mISInfoTO.setMisTin((misInfo.getMisJuriTin() == null || misInfo.getMisJuriTin().trim().length() == 0) ? "" : misInfo.getMisJuriTin().trim());
                        mISInfoTO.setMisCity((misInfo.getMisPlaceCity() == null || misInfo.getMisPlaceCity().equals("0") || misInfo.getMisPlaceCity().trim().length() == 0) ? "" : misInfo.getMisPlaceCity());
                        mISInfoTO.setMisBirthCountry(misInfo.getMisBirth() == null || misInfo.getMisBirth().equalsIgnoreCase("0") ? "" : misInfo.getMisBirth());
                    }

                    mISInfoTO.setType(misInfo.getMisType() == null ? "" : misInfo.getMisType());
                    mISInfoTO.setGroupID(misInfo.getGroupId() == null ? "" : misInfo.getGroupId());
                    customerMasterDetailTO.setOperationalRiskRating(misInfo.getOpRiskRating() == null || misInfo.getOpRiskRating().equalsIgnoreCase("0") ? "" : misInfo.getOpRiskRating());
                    customerMasterDetailTO.setRatingAsOn(misInfo.getRiskAsOn());
                    customerMasterDetailTO.setCustAcquistionDate(misInfo.getCustAquiDate());
                    customerMasterDetailTO.setThresoldTransactionLimit(Double.parseDouble(misInfo.getThreshOldTransLimit() != null
                            && !misInfo.getThreshOldTransLimit().equalsIgnoreCase("") ? misInfo.getThreshOldTransLimit() : "0.0"));
                    customerMasterDetailTO.setThresoldLimitUpdationDate(misInfo.getThresholdLimitUpdDate());
                    customerMasterDetailTO.setCustUpdationDate(misInfo.getCustUpdationDate());
                    mISInfoTO.setTypeOfDocSubmit(misInfo.getTypeOfDocSubmit() == null || misInfo.getTypeOfDocSubmit().equalsIgnoreCase("0") ? "" : misInfo.getTypeOfDocSubmit());

                    mISInfoTO.setResidenceCountryTaxLaw(misInfo.getCountryOfResiForTax() == null || misInfo.getCountryOfResiForTax().equalsIgnoreCase("0") ? "" : misInfo.getCountryOfResiForTax());
                    mISInfoTO.setStateOfIncorp(misInfo.getStateOfIncorp() == null || misInfo.getStateOfIncorp().equalsIgnoreCase("0") ? "" : misInfo.getStateOfIncorp());
                    mISInfoTO.setCountryOfIncorp(misInfo.getCountryOfIncorp() == null || misInfo.getCountryOfIncorp().equalsIgnoreCase("0") ? "" : misInfo.getCountryOfIncorp());



                    //to add in the list of image required to captured
                    if (custPersonalDetails.getCustEntityType().equalsIgnoreCase("01")) {
                        customerImageCaptureSet.add(masterDelegate.getCustomerImageMapp("311", custGeneralInfo.getLegelPoiDoc()));
                    } else if (custPersonalDetails.getCustEntityType().equalsIgnoreCase("02")) {
                        customerImageCaptureSet.add(masterDelegate.getCustomerImageMapp("304", custGeneralInfo.getLegelPoiDoc()));
                    }
                    //to add in the list of image required to captured
                    customerImageCaptureSet.add(masterDelegate.getCustomerImageMapp("312", custAddressInfo.getMailPoa()));



                    if (!custGeneralInfo.getCustIdList().isEmpty()) {
                        for (CBSCustIdentityDetailsTo idTypeList : custGeneralInfo.getCustIdList()) {
                            if (custPersonalDetails.getCustEntityType().equalsIgnoreCase("01")) {
                                customerImageCaptureSet.add(masterDelegate.getCustomerImageMapp("311", idTypeList.getIdentificationType()));
                            } else if (custPersonalDetails.getCustEntityType().equalsIgnoreCase("02")) {
                                customerImageCaptureSet.add(masterDelegate.getCustomerImageMapp("304", idTypeList.getIdentificationType()));
                            }
                        }
                    }

                    if (!custAdditionalAddress.getAddressDetailsList().isEmpty()) {
                        for (CbsCustAdditionalAddressDetailsTo addressTypeList : custAdditionalAddress.getAddressDetailsList()) {
                            customerImageCaptureSet.add(masterDelegate.getCustomerImageMapp("312", addressTypeList.getProofofAdd()));
                        }
                    }

                    customerImageCaptureSet.add(masterDelegate.getCustomerImageMapp("312", custAddressInfo.getMailPoa()));
                    if (!customerImageCaptureSet.isEmpty()) {
                        String imageCode = "";
                        int i = 0;
                        for (Object obj : customerImageCaptureSet) {

                            String imageCodeInd = obj.toString();
                            if (imageCodeInd.equals("")) {
                                continue;
                            }
                            if (customerImageCaptureSet.size() == 1) {
                                imageCode = imageCodeInd;
                            } else {
                                if (i == 0) {
                                    imageCode = imageCodeInd;
                                    i++;
                                } else {
                                    imageCode = imageCode.concat("|" + imageCodeInd);
                                    i++;
                                }
                            }
                        }
                        customerMasterDetailTO.setCustImage(imageCode);
                        // to add pan card in document to scan list by default for legal entity type customer
//                        if (custPersonalDetails.getCustEntityType().equalsIgnoreCase("02") && imageCode.equals("")) {
//                            customerMasterDetailTO.setCustImage("03");
//                        } else {
//                            customerMasterDetailTO.setCustImage(imageCode);
//                        }
                    }
                    if (customerMasterDetailTO.getCustImage().equals("") && !(misInfo.getCustImageCode().equals(""))) {
                        customerMasterDetailTO.setCustImage(misInfo.getCustImageCode());
                    } else if (!(customerMasterDetailTO.getCustImage().equals("") && misInfo.getCustImageCode().equals(""))) {
                        customerMasterDetailTO.setCustImage(customerMasterDetailTO.getCustImage() + "|" + misInfo.getCustImageCode());
                    } else if (!customerMasterDetailTO.getCustImage().equals("") && misInfo.getCustImageCode().equals("")) {
                        customerMasterDetailTO.setCustImage(customerMasterDetailTO.getCustImage());
                    }


//                    mISInfoTO.setType(misInfo.getMisType());
//                    mISInfoTO.setGroupID(misInfo.getGroupId());
//                    mISInfoTO.setStatusCode(misInfo.getStatus());
//                    mISInfoTO.setStatusAsOn(misInfo.getStatusDate());
//                    mISInfoTO.setOccupationCode(misInfo.getMisOccupation());
//                    mISInfoTO.setConstitutionCode(misInfo.getMisConstitution());
//                    mISInfoTO.setCasteCode(misInfo.getMisCast());
//                    mISInfoTO.setCommunityCode(misInfo.getMisCommunity());
//                    mISInfoTO.setHealthCode(misInfo.getHealthCode());
//                    mISInfoTO.setBusinessSegment(misInfo.getBusinessSegment());
//                    mISInfoTO.setSalesTurnover(Double.parseDouble(misInfo.getSalesTurnOver() != null && !misInfo.getSalesTurnOver().equalsIgnoreCase("") ? misInfo.getSalesTurnOver() : "0.0"));
                    //For Minor Info Tab
                    CBSCustMinorInfoTO minorInfoTO = new CBSCustMinorInfoTO();

                    minorInfoTO.setCustomerId(customerDetail.getCustId());
                    if (custPersonalDetails.getMinorType()
                            .trim().equalsIgnoreCase("Y")) {
                        minorInfoTO.setGuardianCode(custPersonalDetails.getGuardianCustId());
                        minorInfoTO.setMajorityDate(custPersonalDetails.getMinorMajorityDate());
                    } else {
                        if (getCustomerDetail().getFunction().equalsIgnoreCase("M")) {
                            if (ftsRemote.isMinorCustomerId(customerDetail.getCustId()).equalsIgnoreCase("Y")
                                    && custPersonalDetails.getMinorType().trim().equalsIgnoreCase("N")) {
                                minorInfoTO.setGuardianCode("");
                                minorInfoTO.setMajorityDate(new Date());
                            } else {
                                minorInfoTO.setGuardianCode("");
                                //minorInfoTO.setMajorityDate(new Date());
                            }
                        }
                    }

//                    minorInfoTO.setDateOfBirth(minorInfo.getMinorDob());
//                    minorInfoTO.setGuardianTitle(minorInfo.getTitleTypeGuar());
//                    minorInfoTO.setGuardianName(minorInfo.getGuagdianName());
//                    minorInfoTO.setLocalAddress1(minorInfo.getGuarAdd1());
//                    minorInfoTO.setLocalAddress2(minorInfo.getGuarAdd2());
//                    minorInfoTO.setVillage(minorInfo.getGuarVillage());
//                    minorInfoTO.setBlock(minorInfo.getGuarTehsilCode());
//                    minorInfoTO.setCityCode(minorInfo.getCityForMinorAddress());
//                    minorInfoTO.setStateCode(minorInfo.getStateForMinorAddress());
//                    minorInfoTO.setPostalCode(minorInfo.getGuarpostal());
//                    minorInfoTO.setCountryCode(minorInfo.getCountryForMinorAddress());
//                    minorInfoTO.setPhoneNumber(minorInfo.getGuarPhone());
//                    minorInfoTO.setMobileNumber(minorInfo.getGuarMob());
//                    minorInfoTO.setEmailID(minorInfo.getGuarEmail());


                    //For Related person Tab Added by Rahul
//                    RelatedPersonsInfoTable pojosetN = new RelatedPersonsInfoTable();
//                    pojosetN.setPersonCustomerid(relatedperson.getRelatedCustId()==null ? "": relatedperson.getRelatedCustId());
//                    pojosetN.setRelationshipCode(relatedperson.getRelPersonType()==null ? "" : relatedperson.getRelPersonType());
//                    pojosetN.setDelFlag(relatedperson.getFlagType()==null ? "" : relatedperson.getFlagType());

//                    CBSRelatedPersonsDetailsTO relatedPerson = new CBSRelatedPersonsDetailsTO();
//                    relatedPerson.setPersonCustomerId(relatedperson.getRelatedCustId());
//                    relatedPerson.setRelationshipCode(relatedperson.getRelPersonType());
//                    relatedPerson.setDelFlag(relatedperson.getFlagType());
//                    relatedPerson.setPersonFirstName(pojosetN.getPersonFirstName());
//                    relatedPerson.setPersonMiddleName(pojosetN.getPersonMiddleName());
//                    relatedPerson.setPersonLastName(pojosetN.getPersonLastName());

                    System.out.println("Size of Related List-->" + relatedperson.getRpiTableList().size() + "\n");
                    for (int k = 0; k < relatedperson.getRpiTableList().size(); k++) {
                        RelatedPersonsInfoTable pojosetN = new RelatedPersonsInfoTable();
                        pojosetN = relatedperson.getRpiTableList().get(k);
                    }
                    List<CBSRelatedPersonsDetailsTO> reletedPersonDetailsTos = new ArrayList<CBSRelatedPersonsDetailsTO>();
                    int i = 1;
                    for (RelatedPersonsInfoTable pojoset : relatedperson.getRpiTableList()) {
                        CBSRelatedPersonsDetailsPKTO PKTO = new CBSRelatedPersonsDetailsPKTO();
                        PKTO.setCustomerId(pojoset.getCustomerId());
                        PKTO.setPersonSrNo(i++);
                        CBSRelatedPersonsDetailsTO details = new CBSRelatedPersonsDetailsTO();
                        details.setcBSRelatedPersonsDetailsPKTO(PKTO);

                        details.setPersonCustomerId(pojoset.getPersonCustomerid());
                        details.setRelationshipCode(pojoset.getRelationshipCode());
                        details.setDelFlag(pojoset.getDelFlag());
                        details.setPersonFirstName(pojoset.getPersonFirstName());
                        details.setPersonMiddleName(pojoset.getPersonMiddleName());
                        details.setPersonLastName(pojoset.getPersonLastName());
                        details.setPan(pojoset.getPersonPan());
                        details.setUid(pojoset.getPersonUid());
                        details.setVoterId(pojoset.getPersonVoterId());
                        details.setNrega(pojoset.getPersonNrega());
                        details.setDl(pojoset.getPersonDl());
                        details.setDlExpiry(pojoset.getPersonDlExpiry());
                        details.setPassportNo(pojoset.getPersonPassportNo());
                        details.setPassportExpiry(pojoset.getPersonPassportExpiry());
                        details.setDin(pojoset.getPersonDin());
                        details.setExposed(pojoset.getPersonPoliticalExposed());
                        details.setAdd1(pojoset.getPersonAdd1());
                        details.setAdd2(pojoset.getPersonAdd2());
                        details.setCity(pojoset.getPersonCity());
                        details.setState(pojoset.getPersonState());
                        details.setCountry(pojoset.getPersonCountry());
                        details.setPin(pojoset.getPersonPin());
//                       
                        reletedPersonDetailsTos.add(details);
                    }
                    // 
                    //For NRI INFO TAB
                    CBSCustNREInfoTO nREInfoTO = new CBSCustNREInfoTO();

                    nREInfoTO.setCustomerId(customerDetail.getCustId());
//                    nREInfoTO.setNationality(nreInfo.getNationality());
                    nREInfoTO.setNonResidentDate(nreInfo.getDateOfBeingNre());
                    nREInfoTO.setCountryCode(
                            (nreInfo.getCountryForNre() == null || nreInfo.getCountryForNre().equals("0")) ? "0" : nreInfo.getCountryForNre());
//                    nREInfoTO.setCountryType(nreInfo.getCountryTypeForNre());
                    nREInfoTO.setLocalContPersonCode(
                            (nreInfo.getNreRelativeCode() == null || nreInfo.getNreRelativeCode().equals("")) ? "" : nreInfo.getNreRelativeCode());
//                    nREInfoTO.setLocalPersonTitle(nreInfo.getTitleTypeForNre());
//                    nREInfoTO.setLocalContPersonName(nreInfo.getNreName());
                    nREInfoTO.setLocalAddress1(
                            (nreInfo.getNreAdd1() == null || nreInfo.getNreAdd1().equals("")) ? "" : nreInfo.getNreAdd1());
                    nREInfoTO.setLocalAddress2(
                            (nreInfo.getNreAdd2() == null || nreInfo.getNreAdd2().equals("")) ? "" : nreInfo.getNreAdd2());
                    nREInfoTO.setVillage(
                            (nreInfo.getNreVillCode() == null || nreInfo.getNreVillCode().equals("")) ? "" : nreInfo.getNreVillCode());
//                    nREInfoTO.setBlock(nreInfo.getNreTehsilCode());
                    nREInfoTO.setCityCode(
                            (nreInfo.getCityForNre() == null || nreInfo.getCityForNre().equals("")) ? "" : nreInfo.getCityForNre());
                    nREInfoTO.setStateCode(
                            (nreInfo.getStateForNre() == null || nreInfo.getStateForNre().equals("")) ? "" : nreInfo.getStateForNre());
                    nREInfoTO.setPostalCode(
                            (nreInfo.getNrePostal() == null || nreInfo.getNrePostal().equals("")) ? "" : nreInfo.getNrePostal());
//                    nREInfoTO.setCountryCode(nreInfo.getCountryForNre());
                    nREInfoTO.setPhoneNumber(
                            (nreInfo.getNrePhoneNo() == null || nreInfo.getNrePhoneNo().equals("")) ? "" : nreInfo.getNrePhoneNo());
                    nREInfoTO.setMobileNumber(
                            (nreInfo.getNreMob() == null || nreInfo.getNreMob().equals("")) ? "" : nreInfo.getNreMob());
                    nREInfoTO.setNreEmial(
                            (nreInfo.getNreEmail() == null || nreInfo.getNreEmail().equals("")) ? "" : nreInfo.getNreEmail());
                    nREInfoTO.setNonResidentEndDate(nreInfo.getNreBecomResident());

                    //for trade finance info tab
//                    CBSTradeFinanceInformationTO informationTO = new CBSTradeFinanceInformationTO();
//                    informationTO.setCustomerId(customerDetail.getCustId());
//                    informationTO.setName(tradeFinanceInfo.getTfiName());
//                    informationTO.setAddressLine1(tradeFinanceInfo.getTfiAdd1());
//                    informationTO.setAddressLine2(tradeFinanceInfo.getTfiAdd2());
//                    informationTO.setCityCode(tradeFinanceInfo.getCityForTFI());
//                    informationTO.setPostalCode(tradeFinanceInfo.getTfiPostal());
//                    informationTO.setCountryCode(tradeFinanceInfo.getCountryForTFI());
//                    informationTO.setPhoneNumber(tradeFinanceInfo.getTfiPhone());
//                    informationTO.setFaxNumber(tradeFinanceInfo.getTfiFax());
//                    informationTO.setTelexNumber(tradeFinanceInfo.getTfiTelex());
//                    informationTO.setNative1(tradeFinanceInfo.getTfiNative());
//                    informationTO.setInlandTradeAllowed(tradeFinanceInfo.getInlandTradeAllowed());
//                    informationTO.setReviewDate(tradeFinanceInfo.getReviewDate());
//                    informationTO.setPresentOutstandingLiability(Double.parseDouble(tradeFinanceInfo.getTfiPresentOutStandLiab() != null && !tradeFinanceInfo.getTfiPresentOutStandLiab().equalsIgnoreCase("") ? tradeFinanceInfo.getTfiPresentOutStandLiab() : "0.0"));
//                    informationTO.setIndustryType(tradeFinanceInfo.getIndustryType());
//                    informationTO.setType(tradeFinanceInfo.getTypeExpImp());
//                    informationTO.setExportUnitFlag(tradeFinanceInfo.getExportUnit());
//                    informationTO.setStatus(tradeFinanceInfo.getStatusTFI());
//                    informationTO.setPartyConstitution(tradeFinanceInfo.getPartyConstitution());
//                    informationTO.setSpecialParty(tradeFinanceInfo.getSpecialParty());
//                    informationTO.setPartyType(tradeFinanceInfo.getTfiPartyType());
//                    informationTO.setProductionCycle(tradeFinanceInfo.getTfiProdCycle());
//                    informationTO.setTradeExpCode(tradeFinanceInfo.getTradeExpoCode());
//                    informationTO.setCodeGivenByCentralBANK(tradeFinanceInfo.getTfiCodeGivenByCentralBnk());
//                    informationTO.setCodeGivenByTradeAuthority(tradeFinanceInfo.getTfiCodeGivenByCentralAuth());
//                    informationTO.setdCMargin(Double.parseDouble(tradeFinanceInfo.getDcMargin100() != null && !tradeFinanceInfo.getDcMargin100().equalsIgnoreCase("") ? tradeFinanceInfo.getDcMargin100() : "0.0"));
//                    informationTO.setdCSanctioningAuthority(tradeFinanceInfo.getDcSactioningAuth());
//                    informationTO.setdCSanctionExpiryDate(tradeFinanceInfo.getDcSancExpDate());
//                    informationTO.setdCNextNumberCode(tradeFinanceInfo.getDcNextNoCode());
//                    informationTO.setDocumentCreditLimit(Double.parseDouble(tradeFinanceInfo.getDcCreditLim() != null && !tradeFinanceInfo.getDcCreditLim().equalsIgnoreCase("") ? tradeFinanceInfo.getDcCreditLim() : "0.0"));
//                    informationTO.setfCMargin(Double.parseDouble(tradeFinanceInfo.getFcMargin100() != null && !tradeFinanceInfo.getFcMargin100().equalsIgnoreCase("") ? tradeFinanceInfo.getFcMargin100() : "0.0"));
//                    informationTO.setfCSanctioningAuthority(tradeFinanceInfo.getFcSactioningAuth());
//                    informationTO.setfCSanctionExpiryDate(tradeFinanceInfo.getFcSancExpDate());
//                    informationTO.setfCNextNumberCode(tradeFinanceInfo.getFcNextNoCode());
//                    informationTO.setForwardContractLimit(Double.parseDouble(tradeFinanceInfo.getFcCreditLim() != null && !tradeFinanceInfo.getFcCreditLim().equalsIgnoreCase("") ? tradeFinanceInfo.getFcCreditLim() : "0.0"));
//                    informationTO.setpCMargin(Double.parseDouble(tradeFinanceInfo.getPcMargin100() != null && !tradeFinanceInfo.getPcMargin100().equalsIgnoreCase("") ? tradeFinanceInfo.getPcMargin100() : "0.0"));
//                    informationTO.setpCSanctioningAuthority(tradeFinanceInfo.getPcSactioningAuth());
//                    informationTO.setpCSanctionExpiryDate(tradeFinanceInfo.getPcSancExpDate());
//                    informationTO.setpCNextNumberCode(tradeFinanceInfo.getPcNextNoCode());
//                    informationTO.setPackingContractLimit(Double.parseDouble(tradeFinanceInfo.getPcCreditLim() != null && !tradeFinanceInfo.getPcCreditLim().equalsIgnoreCase("") ? tradeFinanceInfo.getPcCreditLim() : "0.0"));
//                    informationTO.setbGMargin(Double.parseDouble(tradeFinanceInfo.getBgMargin100() != null && !tradeFinanceInfo.getBgMargin100().equalsIgnoreCase("") ? tradeFinanceInfo.getBgMargin100() : "0.0"));
//                    informationTO.setbGSanctioningAuthority(tradeFinanceInfo.getTfiName());
//                    informationTO.setbGSanctionExpiryDate(tradeFinanceInfo.getBgSancExpDate());
//                    informationTO.setbGNextNumberCode(tradeFinanceInfo.getBgNextNoCode());
//                    informationTO.setBankGuaranteeLimit(Double.parseDouble(tradeFinanceInfo.getBgCreditLim() != null && !tradeFinanceInfo.getBgCreditLim().equalsIgnoreCase("") ? tradeFinanceInfo.getBgCreditLim() : "0.0"));
                    //for Related personInfo tab
                    System.out.println("Size of Related List-->" + relatedPersonInfo.getRpiTableList().size() + "\n");
                    for (int j = 0; j < relatedPersonInfo.getRpiTableList().size(); j++) {
                        RelatedPersonsInfoTable pojo = relatedPersonInfo.getRpiTableList().get(j);
                        System.out.println("::Id-->" + pojo.getPersonCustomerid() + "::Fisrt Name-->" + pojo.getPersonFirstName());
                    }
                    List<CBSRelatedPersonsDetailsTO> reletedPersonDetailsTos1 = new ArrayList<CBSRelatedPersonsDetailsTO>();
                    i = 1;
                    for (RelatedPersonsInfoTable pojo : relatedPersonInfo.getRpiTableList()) {
                        CBSRelatedPersonsDetailsPKTO pKTO = new CBSRelatedPersonsDetailsPKTO();
                        pKTO.setCustomerId(pojo.getCustomerId());
//                        pKTO.setPersonSrNo(Integer.parseInt(pojo.getPersonSrNo()));
                        pKTO.setPersonSrNo(i++);

                        CBSRelatedPersonsDetailsTO detailsTo = new CBSRelatedPersonsDetailsTO();
                        detailsTo.setcBSRelatedPersonsDetailsPKTO(pKTO);

                        detailsTo.setPersonCustomerId(pojo.getPersonCustomerid());
                        detailsTo.setPersonFirstName(pojo.getPersonFirstName());
                        detailsTo.setPersonMiddleName(pojo.getPersonMiddleName());
                        detailsTo.setPersonLastName(pojo.getPersonLastName());
                        detailsTo.setRelationshipCode(pojo.getRelationshipCode());
                        detailsTo.setPan(pojo.getPersonPan());
                        detailsTo.setUid(pojo.getPersonUid());
                        detailsTo.setVoterId(pojo.getPersonVoterId());
                        detailsTo.setNrega(pojo.getPersonNrega());
                        detailsTo.setDl(pojo.getPersonDl());
                        detailsTo.setDlExpiry(pojo.getPersonDlExpiry());
                        detailsTo.setPassportNo(pojo.getPersonPassportNo());
                        detailsTo.setPassportExpiry(pojo.getPersonPassportExpiry());
                        detailsTo.setDin(pojo.getPersonDin());
                        detailsTo.setExposed(pojo.getPersonPoliticalExposed());
                        detailsTo.setAdd1(pojo.getPersonAdd1());
                        detailsTo.setAdd2(pojo.getPersonAdd2());
                        detailsTo.setCity(pojo.getPersonCity());
                        detailsTo.setState(pojo.getPersonState());
                        detailsTo.setCountry(pojo.getPersonCountry());
                        detailsTo.setPin(pojo.getPersonPin());
                        detailsTo.setDelFlag(pojo.getDelFlag());

                        reletedPersonDetailsTos1.add(detailsTo);
                    }





                    //for BuyerSellerLimit tab
//                    CBSBuyerSellerLimitDetailsTO buyerSellerLimitDetailsTO = new CBSBuyerSellerLimitDetailsTO();
//                    buyerSellerLimitDetailsTO.setCustomerId(customerDetail.getCustId());
//                    buyerSellerLimitDetailsTO.setDraweeCode(bsLimitInfo.getDraweeCode());
//                    buyerSellerLimitDetailsTO.setBillType(bsLimitInfo.getBillType());
//                    buyerSellerLimitDetailsTO.setStaus(bsLimitInfo.getStatusBuyerSeller());
//                    buyerSellerLimitDetailsTO.setImpCurrencyCodeCCY(bsLimitInfo.getImportCurrCode());
//                    buyerSellerLimitDetailsTO.setImportLimit(Double.parseDouble(bsLimitInfo.getImportLim() != null && !bsLimitInfo.getImportLim().equalsIgnoreCase("") ? bsLimitInfo.getImportLim() : "0.0"));
//                    buyerSellerLimitDetailsTO.setUtilisedImportLimit(Double.parseDouble(bsLimitInfo.getUtillisedImpLim() != null && !bsLimitInfo.getUtillisedImpLim().equalsIgnoreCase("") ? bsLimitInfo.getUtillisedImpLim() : "0.0"));
//                    buyerSellerLimitDetailsTO.setBuyLimit(Double.parseDouble(bsLimitInfo.getBuyLim() != null && !bsLimitInfo.getBuyLim().equalsIgnoreCase("") ? bsLimitInfo.getBuyLim() : "0.0"));
//                    buyerSellerLimitDetailsTO.setUtilisedBuyLimit(Double.parseDouble(bsLimitInfo.getUtillisedBuyLim() != null && !bsLimitInfo.getUtillisedBuyLim().equalsIgnoreCase("") ? bsLimitInfo.getUtillisedBuyLim() : "0.0"));
//                    buyerSellerLimitDetailsTO.setExpCurrencyCodeCCY(bsLimitInfo.getExportCurrCode());
//                    buyerSellerLimitDetailsTO.setExportLimit(Double.parseDouble(bsLimitInfo.getExportlim() != null && !bsLimitInfo.getExportlim().equalsIgnoreCase("") ? bsLimitInfo.getExportlim() : "0.0"));
//                    buyerSellerLimitDetailsTO.setUtilisedExportLimit(Double.parseDouble(bsLimitInfo.getUtillisedExpLim() != null && !bsLimitInfo.getUtillisedExpLim().equalsIgnoreCase("") ? bsLimitInfo.getUtillisedExpLim() : "0.0"));
//                    buyerSellerLimitDetailsTO.setSellLimit(Double.parseDouble(bsLimitInfo.getSellLimit() != null && !bsLimitInfo.getSellLimit().equalsIgnoreCase("") ? bsLimitInfo.getSellLimit() : "0.0"));
//                    buyerSellerLimitDetailsTO.setUtilisedSellLimit(Double.parseDouble(bsLimitInfo.getUtillisedSellLim() != null && !bsLimitInfo.getUtillisedSellLim().equalsIgnoreCase("") ? bsLimitInfo.getUtillisedSellLim() : "0.0"));
                    //FOR OTHER INFORMATION TAB
//                    customerMasterDetailTO.setOperationalRiskRating(otherInfo.getOperRiskRate());
//                    customerMasterDetailTO.setRatingAsOn(otherInfo.getRatingAsOn1());
//                    customerMasterDetailTO.setCreditRiskRatingInternal(otherInfo.getCreditOperRiskrate());
//                    customerMasterDetailTO.setCreditRatingAsOn(otherInfo.getRatingAsOn2());
//                    customerMasterDetailTO.setExternalRatingShortTerm(otherInfo.getExRatingShortterm());
//                    customerMasterDetailTO.setExternShortRatingAsOn(otherInfo.getRatingAsOn3());
//                    customerMasterDetailTO.setExternalRatingLongTerm(otherInfo.getExRatingLongterm());
//                    customerMasterDetailTO.setExternLongRatingAsOn(otherInfo.getRatingAsOn4());
//                    CBSCustDeliveryChannelDetailsTO channelDetailsTO = new CBSCustDeliveryChannelDetailsTO();
//                    channelDetailsTO.setCustomerId(customerDetail.getCustId());
//                    channelDetailsTO.setaTMDebitCardHolder(otherInfo.getAtmCardType());
//                    channelDetailsTO.setCreditCardEnabled(otherInfo.getCreditCardHolder());
//                    channelDetailsTO.setMobileBankingEnabled(otherInfo.getMobBankingEnable());
//                    channelDetailsTO.setSmsBankingEnabled(otherInfo.getSmsBnkType());
//                    channelDetailsTO.setiNetBankingEnabled(otherInfo.getiNetBnkType());
//                    channelDetailsTO.setTeleBankingEnabled(otherInfo.getTelBnkType());
//                    channelDetailsTO.setSelfServiceEnabled(otherInfo.getSelfSrvType());
//                    channelDetailsTO.seteCSEnabled(otherInfo.getEcsEnable());
                    //for KYCDetails tab
//                    CbsKycDetailsTO kycDetailsTO = new CbsKycDetailsTO();
//                    kycDetailsTO.setCustomerId(customerDetail.getCustId());
//                    kycDetailsTO.setEduDetails(kycDetails.getEducationDetails());
//                    String dependents = "";
//                    if (kycDetails.spouse == false && kycDetails.parents == false && kycDetails.children == false) {
//                        dependents = "NONE";
//                    } else if (kycDetails.spouse == true && kycDetails.parents == false && kycDetails.children == false) {
//                        dependents = "SPOUSE";
//                    } else if (kycDetails.spouse == true && kycDetails.parents == true && kycDetails.children == false) {
//                        dependents = "SPOUSE" + "~PARENTS";
//                    } else if (kycDetails.spouse == true && kycDetails.parents == true && kycDetails.children == true) {
//                        dependents = "SPOUSE" + "~PARENTS" + "~CHILDREN";
//                    } else if (kycDetails.spouse == false && kycDetails.parents == true && kycDetails.children == false) {
//                        dependents = "PARENTS";
//                    } else if (kycDetails.spouse == false && kycDetails.parents == true && kycDetails.children == true) {
//                        customerDetail.setErrorMsg("Please Uncheck The Children As You Have No Spouse");
//                        return;
//                    }
//                    kycDetailsTO.setDependents(dependents);
//                    kycDetailsTO.setNoMales10(kycDetails.getMale1() == null || kycDetails.getMale1().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getMale1()));
//                    kycDetailsTO.setNoMales20(kycDetails.getMale2() == null || kycDetails.getMale2().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getMale2()));
//                    kycDetailsTO.setNoMales45(kycDetails.getMale3() == null || kycDetails.getMale3().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getMale3()));
//                    kycDetailsTO.setNoMales60(kycDetails.getMale4() == null || kycDetails.getMale4().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getMale4()));
//                    kycDetailsTO.setNoMalesAbove61(kycDetails.getMale5() == null || kycDetails.getMale5().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getMale5()));
//
//                    kycDetailsTO.setNoFem10(kycDetails.getFemale1() == null || kycDetails.getFemale1().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getFemale1()));
//                    kycDetailsTO.setNoFem20(kycDetails.getFemale2() == null || kycDetails.getFemale2().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getFemale2()));
//                    kycDetailsTO.setNoFem45(kycDetails.getFemale3() == null || kycDetails.getFemale3().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getFemale3()));
//                    kycDetailsTO.setNoFem60(kycDetails.getFemale4() == null || kycDetails.getFemale4().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getFemale4()));
//                    kycDetailsTO.setNoFemAbove61(kycDetails.getFemale5() == null || kycDetails.getFemale5().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getFemale5()));
//                    kycDetailsTO.setNoChild(kycDetails.getNoOfChildren() == null || kycDetails.getNoOfChildren().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getNoOfChildren()));
//
//                    kycDetailsTO.setAbRelName1(kycDetails.getName1());
//                    kycDetailsTO.setAbRelName2(kycDetails.getName2());
//                    kycDetailsTO.setAbRelName3(kycDetails.getName3());
//                    kycDetailsTO.setAbRelAdd1(kycDetails.getName1());
//                    kycDetailsTO.setAbRelAdd2(kycDetails.getName2());
//                    kycDetailsTO.setAbRelAdd3(kycDetails.getName3());
//
//                    kycDetailsTO.setAbVisited(kycDetails.getAbroadTime() == null || kycDetails.getAbroadTime().equalsIgnoreCase("") ? 0 : Integer.parseInt(kycDetails.getAbroadTime()));
//
//                    kycDetailsTO.setBankRel(kycDetails.getRelativeInBank() != null ? kycDetails.getRelativeInBank().toCharArray()[0] : '\0');
//                    kycDetailsTO.setCreditcard(kycDetails.getCreditCardDetails() != null ? kycDetails.getCreditCardDetails().toCharArray()[0] : '\0');
//                    kycDetailsTO.setDirRel(kycDetails.getRelationWithDirector() != null ? kycDetails.getRelationWithDirector().toCharArray()[0] : '\0');
//                    kycDetailsTO.setMedinsurence(kycDetails.getMedicalInsurance() != null ? kycDetails.getMedicalInsurance().toCharArray()[0] : '\0');
//
//                    List<CbsKycAssetsTO> assetsTOs = new ArrayList<CbsKycAssetsTO>();
//                    for (AssetsTable assetsTable : kycDetails.getListAssetsTable() == null ? new ArrayList<AssetsTable>() : kycDetails.getListAssetsTable()) {
//                        CbsKycAssetsTO table = new CbsKycAssetsTO();
//                        CbsKycAssetsPKTO pkto = new CbsKycAssetsPKTO();
//                        pkto.setCustomerId(customerDetail.getCustId());
//                        table.setAssets(assetsTable.getAssets());
//                        table.setAssetstype(assetsTable.getAstType());
//                        table.setAssetsvalue(assetsTable.getAstValue());
//                        table.setTsCnt(assetsTable.getSaveUpdateCounter());
//                        assetsTOs.add(table);
//                    }
//                    List<CbsKycLoanTO> loanTOs = new ArrayList<CbsKycLoanTO>();
//                    for (LoanTable loanTable : kycDetails.getListLoanTable() == null ? new ArrayList<LoanTable>() : kycDetails.getListLoanTable()) {
//                        CbsKycLoanTO loanTO = new CbsKycLoanTO();
//                        CbsKycLoanPKTO pkto = new CbsKycLoanPKTO();
//                        pkto.setCustomerId(customerDetail.getCustId());
//                        pkto.setTxnId(0);
//                        loanTO.setCbsKycLoanPKTO(pkto);
//                        loanTO.setLoanamt(loanTable.getLoanValue());
//                        loanTO.setLoantype(loanTable.getLoanType());
//                        loanTO.setRecordCreaterID(customerDetail.getUserName());
//                        loanTOs.add(loanTO);
//                    }
//                    String msg = masterDelegate.saveUpdateCustomer(customerMasterDetailTO, currencyInfoTOs, minorInfoTO, nREInfoTO, mISInfoTO,
//                            informationTO, reletedPersonDetailsTos, buyerSellerLimitDetailsTO, channelDetailsTO, kycDetailsTO, assetsTOs,
//                            loanTOs, getCustomerDetail().getFunction(), customerDetail.getUserName(), customerDetail.getOrgnBrCode());
                    if (getCustomerDetail().getFunction().equalsIgnoreCase("A") && btnPosition.equalsIgnoreCase("mainBtn")
                            || getCustomerDetail().getFunction().equalsIgnoreCase("A") && btnPosition.equalsIgnoreCase("alertBtn")
                            || getCustomerDetail().getFunction().equalsIgnoreCase("M") && btnPosition.equalsIgnoreCase("mainBtn")
                            || getCustomerDetail().getFunction().equalsIgnoreCase("M") && btnPosition.equalsIgnoreCase("alertBtn")) {

                        String nationality = actEditRemote.introducerCity("302", customerMasterDetailTO.getNationality());

                        List chkList = ftsRemote.chkBadPerson(customerMasterDetailTO.getTitle(), customerMasterDetailTO.getCustname(), dmy.format(customerMasterDetailTO.getDateOfBirth()).substring(6),
                                mISInfoTO.getMisCity() == null ? "" : mISInfoTO.getMisCity(), "", customerMasterDetailTO.getPerAddressLine1(), "",
                                "", customerMasterDetailTO.getPassportNo() == null ? "" : customerMasterDetailTO.getPassportNo(), nationality);

                        if (!chkList.isEmpty()) {
                            this.str = "WL1.1";
                            Vector alertVector = (Vector) chkList.get(0);
//                            String msg = "This Customer information may be illegal activities such as Title:" + alertVector.get(0).toString() + ",Name:" + alertVector.get(1).toString() + ",Dob:" + alertVector.get(2).toString() + ",Pob:"
//                                    + alertVector.get(3).toString() + ",Designation:" + alertVector.get(4).toString() + ",Address:" + alertVector.get(5).toString()
//                                    + ",Good Quality:" + alertVector.get(6).toString() + ",Low Quality:" + alertVector.get(7).toString() + ",Passport No." + alertVector.get(8).toString()
//                                    + ",Nationality:" + alertVector.get(9).toString() + ",";
                            String msg = "This Customer information may be illegal activities such as Title:" + alertVector.get(0).toString();
                            msg += ",Name:" + alertVector.get(1).toString();
                            msg += ",Dob:" + alertVector.get(2).toString();
                            msg += ",Pob:" + alertVector.get(3).toString();
                            msg += ",Designation:" + alertVector.get(4).toString();
                            msg += ",Address:" + alertVector.get(5).toString();
                            msg += ",Good Quality:" + alertVector.get(6).toString();
                            msg += ",Low Quality:" + alertVector.get(7).toString();
                            msg += ",Passport No." + alertVector.get(8).toString();
                            msg += ",Nationality:" + alertVector.get(9).toString() + ",";

                            getCustomerDetail().setBadPersonAlertFlag(true);
                            getCustomerDetail().setIllegalMsg(msg);
                            return;
                        }
                    } else {
                        getCustomerDetail().setBadPersonAlertFlag(false);
                        getCustomerDetail().setConfirmationMsg("");
                    }

                    customerMasterDetailTO.setStrFlag(str);
                    String msg = masterDelegate.saveUpdateCustomer(customerMasterDetailTO, minorInfoTO, nREInfoTO,
                            mISInfoTO, reletedPersonDetailsTos, custGeneralInfo.getCustIdList(), custAdditionalAddress.getAddressDetailsList(), getCustomerDetail().getFunction(),
                            customerDetail.getUserName(), customerDetail.getOrgnBrCode());

                    refreshAllTabs();

                    customerDetail.setErrorMsg(msg);
                } else {
                    customerDetail.setErrorMsg(errMsg);
                }
            } else {
                if (getCustomerDetail().getFunction().equalsIgnoreCase("v")) {
                    customerDetail.setMapMessage("");
                    customerDetail.setCustId(customerDetail.getVerifyCustId());
                    customerDetail.setMapMessage(customerDetail.getMap().get(customerDetail.getCustId()));
                    if (!isLevelIdMatched()) {
                        customerDetail.setErrorMsg("Sorry! You are not authorized person for verifing this customer!");
                        return;
                    }
                    CBSCustomerMasterDetailTO custDetailsByCustId = masterDelegate.getCustDetailsByCustId(customerDetail.getCustId());
                    if (custDetailsByCustId.getLastChangeUserID().equalsIgnoreCase(customerDetail.getUserName())) {
                        customerDetail.setErrorMsg("Sorry! You can not verify your own entry!");
                        return;
                    }
                }

//                String msg = masterDelegate.suspendUnsuspendVerifyCustomer(customerDetail.getCustId(), customerDetail.getFunction(), customerDetail.getUserName());
                String midCustomerId = "";
                if (customerDetail.getFunction().equalsIgnoreCase("S") || customerDetail.getFunction().equalsIgnoreCase("U")) {
                    midCustomerId = customerDetail.getCustId();
                } else {
                    midCustomerId = customerDetail.getVerifyCustId();
                }

                if (midCustomerId.equals("")) {
                    customerDetail.setErrorMsg("Customer Id is blank.");
                    return;
                }
                String msg = masterDelegate.suspendUnsuspendVerifyCustomer(midCustomerId, customerDetail.getFunction(), customerDetail.getUserName());
                refreshAllTabs();
                customerDetail.setErrorMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            customerDetail.setErrorMsg(e.getMessage());
        }
    }

    public void refreshAllTabs() {
        customerDetail.cacelButton();
        custPersonalDetails.refreshForm();
        custGeneralInfo.refreshForm();
        custAddressInfo.refreshForm();
        misInfo.refreshForm();
        minorInfo.refreshForm();
        nreInfo.refreshForm();
        relatedPersonInfo.fieldFresh();
        custAdditionalAddress.refreshForm();
        relatedperson.fieldFresh();
        this.str = "";
    }

    private boolean isLevelIdMatched() {
        boolean flag = false;
        try {
            Securityinfo entity = masterDelegate.getSecurityInfoDetailsByUserid(customerDetail.getUserName());
            if (entity != null) {
                if (entity.getLevelId() == 1 || entity.getLevelId() == 2) {
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            customerDetail.setErrorMsg(e.getMessage());
        }
        return flag;
    }

    //Validation of different tabs
    public String validateCustGeneralInfo() {
//        Matcher matcher = null;
//        if (!(getCustGeneralInfo().getDrivingLiscNo() == null
//                || getCustGeneralInfo().getDrivingLiscNo().trim().length() == 0)) {
//            matcher = alphaNumericWithoutSpace.matcher(getCustGeneralInfo().getDrivingLiscNo());
//            if (!matcher.matches()) {
//                customerDetail.setErrorMsg("Only alphanumeric values are allowed in Driving License No on Proof of identity tab");
//                return "Only alphanumeric values are allowed in Driving License No on Proof of identity tab";
//            }
//            if (getCustGeneralInfo().getDlExpiry() == null) {
//                customerDetail.setErrorMsg("Please fill Driving License Expiry Date on Proof of identity tab");
//                return "Please fill Driving License Expiry Date on Proof of identity tab";
//            }
//
//        }
//        if (!(getCustGeneralInfo().getTanNo() == null
//                || getCustGeneralInfo().getTanNo().trim().length() == 0)) {
//            matcher = alphaNumericWithoutSpace.matcher(getCustGeneralInfo().getTanNo());
//            if (!matcher.matches()) {
//                customerDetail.setErrorMsg("Only alphanumeric values are allowed in TAN on Proof of identity tab");
//                return "Only alphanumeric values are allowed in TAN on Proof of identity tab";
//            }
//        }
        if (!(getCustGeneralInfo().getNetworth() == null || getCustGeneralInfo().getNetworth().equals(""))) {
            Matcher matcher = amtPattern.matcher(getCustGeneralInfo().getNetworth());
            if (!matcher.matches()) {
                customerDetail.setErrorMsg("Only two digits are allowed in Netwoth on Proof of identity tab");
                return "Only two digits are allowed in Netwoth on Proof of identity tab";
            }
            if (getCustGeneralInfo().getNetworthAsOn() == null) {
                customerDetail.setErrorMsg("Please fill Networth As On on Proof of identity tab");
                return "Please fill Networth As On on Proof of identity tab";
            }
        }
        if (getCustGeneralInfo().getLegelPoiDoc() == null || getCustGeneralInfo().getLegelPoiDoc().equalsIgnoreCase("0")) {
            customerDetail.setErrorMsg("Please Select POI on Proof of identity tab");
            return "Please Select POI on Proof of identity tab";
        }
        if (getCustGeneralInfo().getIdentityNo() == null || getCustGeneralInfo().getIdentityNo().equalsIgnoreCase("")) {
            customerDetail.setErrorMsg("Please Fill Identity No on Proof of identity tab");
            return "Please Fill Identity No on Proof of identity tab";
        }
        if (getCustGeneralInfo().getLegelPoiDoc().equalsIgnoreCase("O") || getCustGeneralInfo().getLegelPoiDoc().equalsIgnoreCase("Z")) {
            if (getCustGeneralInfo().getOtherIdentity() == null || getCustGeneralInfo().getOtherIdentity().equalsIgnoreCase("")) {
                customerDetail.setErrorMsg("Please Fill Other POI on Proof of identity tab");
                return "Please Fill Other POI on Proof of identity tab";
            }
        }
        if (getCustGeneralInfo().getIdExpiryDate() != null) {
            long diff = CbsUtil.dayDiff(getCustGeneralInfo().getIdExpiryDate(), new Date());
            if (diff > 0) {
                getCustomerDetail().setErrorMsg("Id. Expiry Date can not be less than current date on Proof of identity tab");
                return "Id. Expiry Date  can not be less than current date on Proof of identity tab";
            }
        }
        if (custPersonalDetails.getCustEntityType().equalsIgnoreCase("02")) { //For company
            if (getCustGeneralInfo().getLegelPoiDoc().equalsIgnoreCase("T")) {
                if ((getCustGeneralInfo().getTinIssuingCountry() == null || getCustGeneralInfo().getTinIssuingCountry().equalsIgnoreCase("0")
                        || getCustGeneralInfo().getTinIssuingCountry().equalsIgnoreCase(""))) {
                    customerDetail.setErrorMsg("Please Select Tin Issueing Country on Proof of identity tab");
                    return "Please Select Tin Issueing Country on Proof of identity tab";
                }
            }
        } else if (custPersonalDetails.getCustEntityType().equalsIgnoreCase("01")) {
            if (getCustGeneralInfo().getLegelPoiDoc().equalsIgnoreCase("A") || getCustGeneralInfo().getLegelPoiDoc().equalsIgnoreCase("D")) {
                if (getCustGeneralInfo().getIdExpiryDate() == null) {
                    customerDetail.setErrorMsg("Please Fill Id Expiry Date on Proof of identity tab");
                    return "Please Fill Id Expiry Date on Proof of identity tab";
                }
            }
            if (!(getCustGeneralInfo().getSalary() == null || getCustGeneralInfo().getSalary().trim().equals(""))) {
                Matcher matcherAmt = amtPattern.matcher(getCustGeneralInfo().getSalary());
                if (!matcherAmt.matches()) {
                    customerDetail.setErrorMsg("Only two digits are allowed in Salary on Proof of identity tab");
                    return "Only two digits are allowed in Salary on Proof of identity tab";
                }
            }
            //Addition on 23/11/2015
            String valProofMsg = "";
            if (getCustGeneralInfo().getLegelPoiDoc() != null) {
                if (!(getCustGeneralInfo().getLegelPoiDoc().equals("") && getCustGeneralInfo().getLegelPoiDoc().equals("0"))) {
                    valProofMsg = validateProofDocument(getCustGeneralInfo().getLegelPoiDoc());
                    if (!valProofMsg.equalsIgnoreCase("ok")) {
                        customerDetail.setErrorMsg(valProofMsg);
                        return valProofMsg;
                    }
                }
            }
        }
        return "ok";
    }

    public String validateProofDocument(String doc) {
        if (doc.equalsIgnoreCase("A")) {
            if (getCustGeneralInfo().getIdentityNo().length() != 8) {
                getCustomerDetail().setErrorMsg("Please Fill 8 digit Passport No. in Identity No. on Proof of Identity Tab.");
                return "Please Fill 8 digit Passport No. in Identity No. on Proof of Identity Tab.";
            }
            Pattern passportNoPattern = Pattern.compile("(([a-zA-Z]{1})\\d{7})");
            Matcher matcher = passportNoPattern.matcher(getCustGeneralInfo().getIdentityNo());
            if (!matcher.matches()) {
                return "Please fill valid Passport No. in Identity No. on Proof of Identity Tab.";
            }
        } else if (doc.equalsIgnoreCase("E")) {
            if (getCustGeneralInfo().getIdentityNo().length() != 12) {
                getCustomerDetail().setErrorMsg("Please Fill 12 digit Aadhaar No. in Identity No. on Proof of Identity Tab.");
                return "Please Fill 12 digit Aadhaar No. in Identity No. on Proof of Identity Tab.";
            }
            if (!VerhoeffCheckDigit.VERHOEFF_CHECK_DIGIT.isValid(getCustGeneralInfo().getIdentityNo())) {
                getCustomerDetail().setErrorMsg("Please Fill Valid Aadhaar No. in Identity No. on Proof of Identity Tab.");
                return "Please Fill Valid Aadhaar No. in Identity No. on Proof of Identity Tab.";
            }
        } else if (doc.equalsIgnoreCase("C")) {
            if (getCustGeneralInfo().getIdentityNo().length() != 10) {
                getCustomerDetail().setErrorMsg("Please Fill 10 digit PAN/GIR No. in Identity No. on Proof of Identity Tab.");
                return "Please Fill 10 digit PAN/GIR No. in Identity No. on Proof of Identity Tab.";
            }
            Pattern panNoPattern = Pattern.compile("((([a-zA-Z]{5})\\d{4})[a-zA-Z]{1})");
            Matcher matcher = panNoPattern.matcher(getCustGeneralInfo().getIdentityNo());
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please Fill Valid PAN/GIR No. in Identity No. on Personal Information Tab.");
                return "Please Fill Valid PAN/GIR No. in Identity No. on Personal Information Tab.";
            }
        }
        return "ok";
    }

    //Getter And Setter
    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public BsLimitInfo getBsLimitInfo() {
        return bsLimitInfo;
    }

    public void setBsLimitInfo(BsLimitInfo bsLimitInfo) {
        this.bsLimitInfo = bsLimitInfo;
    }

    public CurrencyInfo getCurrencyInfo() {
        return currencyInfo;
    }

    public void setCurrencyInfo(CurrencyInfo currencyInfo) {
        this.currencyInfo = currencyInfo;
    }

    public CustAddressInfo getCustAddressInfo() {
        return custAddressInfo;
    }

    public void setCustAddressInfo(CustAddressInfo custAddressInfo) {
        this.custAddressInfo = custAddressInfo;
    }

    public CustGeneralInfo getCustGeneralInfo() {
        return custGeneralInfo;
    }

    public void setCustGeneralInfo(CustGeneralInfo custGeneralInfo) {
        this.custGeneralInfo = custGeneralInfo;
    }

    public CustGeneralInformation getCustGeneralInformation() {
        return custGeneralInformation;
    }

    public void setCustGeneralInformation(CustGeneralInformation custGeneralInformation) {
        this.custGeneralInformation = custGeneralInformation;
    }

    public CustPersonalDetails getCustPersonalDetails() {
        return custPersonalDetails;
    }

    public void setCustPersonalDetails(CustPersonalDetails custPersonalDetails) {
        this.custPersonalDetails = custPersonalDetails;
    }

    public KycDetails getKycDetails() {
        return kycDetails;
    }

    public void setKycDetails(KycDetails kycDetails) {
        this.kycDetails = kycDetails;
    }

    public MinorInfo getMinorInfo() {
        return minorInfo;
    }

    public void setMinorInfo(MinorInfo minorInfo) {
        this.minorInfo = minorInfo;
    }

    public MisInfo getMisInfo() {
        return misInfo;
    }

    public void setMisInfo(MisInfo misInfo) {
        this.misInfo = misInfo;
    }

    public NreInfo getNreInfo() {
        return nreInfo;
    }

    public void setNreInfo(NreInfo nreInfo) {
        this.nreInfo = nreInfo;
    }

    public OtherInfo getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(OtherInfo otherInfo) {
        this.otherInfo = otherInfo;
    }

    public RelatedPersonInfo getRelatedPersonInfo() {
        return relatedPersonInfo;
    }

    public void setRelatedPersonInfo(RelatedPersonInfo relatedPersonInfo) {
        this.relatedPersonInfo = relatedPersonInfo;
    }

    public TradeFinanceInfo getTradeFinanceInfo() {
        return tradeFinanceInfo;
    }

    public void setTradeFinanceInfo(TradeFinanceInfo tradeFinanceInfo) {
        this.tradeFinanceInfo = tradeFinanceInfo;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public CustAdditionalAddress getCustAdditionalAddress() {
        return custAdditionalAddress;
    }

    public void setCustAdditionalAddress(CustAdditionalAddress custAdditionalAddress) {
        this.custAdditionalAddress = custAdditionalAddress;
    }

    public RelatedPerson getRelatedperson() {
        return relatedperson;
    }

    public void setRelatedperson(RelatedPerson relatedperson) {
        this.relatedperson = relatedperson;
    }
}
