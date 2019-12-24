/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSCustomerMasterDetailHisTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.facade.ckycr.CkycrViewMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class CustAddressInfo {

    CustomerDetail customerDetail;
    String perAdd1;
    String perAdd2;
    String perVillage;
    String perBlock;
    String perpostalCode;
    String perPhoneNo;
    String perFaxNo;
//    String pertelexNo;
    String mailAdd1;
    String mailAdd2;
    String mailVillage;
    String mailBlock;
    String mailPostalCode;
    String mailPhoneNo;
    String mailFax;
//    String mailTelexNo;
    String empAdd1;
    String empAdd2;
    String empVillage;
    String empBlock;
    String empPostalCode;
    String empPhoneNo;
    String empFax;
    String empTelexNo;
    String cityForPermanentAddress;
    List<SelectItem> districtForPermanentAddressOption;
    String countryForPermanentAddress;
    String stateForPermanentAddress;
    List<SelectItem> countryForPermanentAddressOption;
    List<SelectItem> stateForPermanentAddressOption;
    String cityForMailAddress;
    List<SelectItem> districtForMailAddressOption;
    String countryForMailAddress;
    String stateForMailAddress;
    List<SelectItem> countryForMailAddressOption;
    List<SelectItem> stateForMailAddressOption;
    String cityForEmpAddress;
    List<SelectItem> districtForEmpAddressOption;
    String countryForEmpAddress;
    String stateForEmpAddress;
    List<SelectItem> countryForEmpAddressOption;
    List<SelectItem> stateForEmpAddressOption;
    boolean mailAddSameAsPerAdd;
    String empCode;
    String empTehsil;
    String empNo;
    String empEmail;
    CustomerMasterDelegate masterDelegate;
    private String juriAdd1;
    private String juriAdd2;
    private String juriCity;
    private String juriState;
    private String juriPostalCode;
    private String juriCountryCode;
    private List<SelectItem> juriCountryCodeList;
    private String perEmail;
    private String mailEmail;
    Pattern onlyNumeric = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
    Pattern alphaNumericWithoutSpace = Pattern.compile("[A-Za-z0-9]*");
    Pattern onlyAlphabetWitoutSpace = Pattern.compile("[A-Za-z]*");
    Pattern onlyAlphabetWitSpace = Pattern.compile("[A-Za-z ]*");
    //Change on 28/10/2015
    private String perAdd1ChgFlag;
    private String perAdd2ChgFlag;
    private String perVillageChgFlag;
    private String perBlockChgFlag;
    private String perCityChgFlag;
    private String perStateChgFlag;
    private String perCountryChgFlag;
    private String perPostalChgFlag;
    private String perPhoneChgFlag;
    private String perFaxChgFlag;
    private String perMailChgFlag;
    private String mailAdd1ChgFlag;
    private String mailAdd2ChgFlag;
    private String mailVillageChgFlag;
    private String mailBlockChgFlag;
    private String mailCityChgFlag;
    private String mailStateChgFlag;
    private String mailCountryChgFlag;
    private String mailPostalChgFlag;
    private String mailPhoneChgFlag;
    private String mailFaxChgFlag;
    private String mailMailChgFlag;
    private String empCodeChgFlag;
    private String empNoChgFlag;
    private String empAdd1ChgFlag;
    private String empAdd2ChgFlag;
    private String empVillageChgFlag;
    private String empTehsilChgFlag;
    private String empCityChgFlag;
    private String empStateChgFlag;
    private String empPostalChgFlag;
    private String empCountryChgFlag;
    private String empPhoneChgFlag;
    private String empTelexChgFlag;
    private String empFaxChgFlag;
    private String empEmailChgFlag;
    private String juriAdd1ChgFlag;
    private String juriAdd2ChgFlag;
    private String juriCityChgFlag;
    private String juriStateChgFlag;
    private String juriPostalChgFlag;
    private String juriCountryChgFlag;
    //Added  on 02/01/2016
    private String perAddTypeChgFlag;
    private String mailAddTypeChgFlag;
    private String mailPoaChgFlag;
    private String juriAddBasedOnFlagChgFlag;
    private String juriAddTypeChgFlag;
    private String juriPoaChgFlag;
    private String prefPoaAddChgFlag;
    private String prefPoaAdd;
    List<SelectItem> prefPoaAddList;
    List<SelectItem> perAddTypeList;
    private String perAddType;
    List<SelectItem> mailAddTypeList;
    private String mailAddType;
    List<SelectItem> mailPoaList;
    private String mailPoa;
    List<SelectItem> juriAddBasedList;
    private String juriAddBasedOnFlag;
    List<SelectItem> juriAddTypeList;
    private String juriAddType;
    List<SelectItem> juriPoaList;
    private String juriPoa;
    private String othrPOADisplay;
    private String othrPOA;
    private String othrPOAChgFlag;
    AdvancesInformationTrackingRemote aitr;
    private String perDistrict;
    private String mailDistrict;
    private String empDistrict;
    private String juriDistrict;
    private String perDistrictChgFlag;
    private String mailDistrictChgFlag;
    private String empDistrictChgFlag;
    private String perOtherPOA;
    private String mailOtherPOA;
    private String juriOtherPOA;
    private String juriOtherPOAChgFlag;
    private String juriOtherPOADisplay;
    private String perOtherPOAChgFlag;
    private String mailOtherPOAChgFlag;
    private String perOtherPOADisplay;
    private String mailOtherPOADisplay;
    List optionlist;
    private List<SelectItem> perPostalCodeList;
    private List<SelectItem> mailPostalCodeList;
    private List<SelectItem> juriPostalCodeList;
    private List<SelectItem> empPostalCodeList;
    private List<SelectItem> districtForJuriAddressOption;
    private String displayOnPerCntryIN = "";
    private String displayOnMailCntryIN = "";
    private String displayOnEmpCntryIN = "";
    private String displayOnJuriCntryIN = "";
    private String displayOnPerCntryNotIN = "none";
    private String displayOnMailCntryNotIN = "none";
    private String displayOnEmpCntryNotIN = "none";
    private String displayOnJuriCntryNotIN = "none";
    private String perStateNotIN;
    private String perDistrictNotIN;
//    private String perpostalCodeNotIN;
    private String mailStateNotIN;
    private String mailDistrictNotIN;
//    private String mailPostalCodeNotIN;
    private String empDistrictNotIN;
    private String empStateNotIN;
//    private String empPostalCodeNotIN;
    private String juriDistrictNotIN;
    private String juriStateNotIN;
    private CkycrViewMgmtFacadeRemote ckycrRemote = null;
//    private String juriPostalCodeNotIN;
    //end
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public CustAddressInfo() {
        initializeForm();
    }

    public void initializeForm() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            ckycrRemote = (CkycrViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrViewMgmtFacade");
            optionlist = new ArrayList();
            perOtherPOADisplay = "none";
            mailOtherPOADisplay = "none";
            juriOtherPOADisplay = "none";
            districtForPermanentAddressOption = new ArrayList<SelectItem>();
            districtForPermanentAddressOption.add(new SelectItem("0", "--Select--"));
            districtForMailAddressOption = new ArrayList<SelectItem>();
            districtForMailAddressOption.add(new SelectItem("0", "--Select--"));
            districtForEmpAddressOption = new ArrayList<SelectItem>();
            districtForEmpAddressOption.add(new SelectItem("0", "--Select--"));
            stateForPermanentAddressOption = new ArrayList<SelectItem>();
            stateForPermanentAddressOption.add(new SelectItem("0", "--Select--"));
            stateForMailAddressOption = new ArrayList<SelectItem>();
            stateForMailAddressOption.add(new SelectItem("0", "--Select--"));
            stateForEmpAddressOption = new ArrayList<SelectItem>();
            stateForEmpAddressOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> stateOptionList = masterDelegate.getFunctionValues("002");
            for (CbsRefRecTypeTO recTypeTO : stateOptionList) {
                stateForPermanentAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                stateForMailAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                stateForEmpAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            countryForPermanentAddressOption = new ArrayList<SelectItem>();
            countryForPermanentAddressOption.add(new SelectItem("0", "--Select--"));
            countryForMailAddressOption = new ArrayList<SelectItem>();
            countryForMailAddressOption.add(new SelectItem("0", "--Select--"));
            countryForEmpAddressOption = new ArrayList<SelectItem>();
            countryForEmpAddressOption.add(new SelectItem("0", "--Select--"));
            juriCountryCodeList = new ArrayList<SelectItem>();
            juriCountryCodeList.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> countryOptionList = masterDelegate.getFunctionValues("302");
            for (CbsRefRecTypeTO recTypeTO : countryOptionList) {
                countryForPermanentAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                countryForMailAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                countryForEmpAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                juriCountryCodeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            this.juriCountryCode = (juriCountryCodeList.isEmpty() || juriCountryCodeList == null) ? "0" : "IN";
            this.countryForPermanentAddress = (countryForPermanentAddressOption.isEmpty() || countryForPermanentAddressOption == null) ? "0" : "IN";
            this.countryForMailAddress = (countryForMailAddressOption.isEmpty() || countryForMailAddressOption == null) ? "0" : "IN";
            this.countryForEmpAddress = (countryForEmpAddressOption.isEmpty() || countryForEmpAddressOption == null) ? "0" : "IN";
            //Added  on 02/01/2016
            perAddTypeList = new ArrayList<SelectItem>();
            perAddTypeList.add(new SelectItem("0", "--Select--"));
            mailAddTypeList = new ArrayList<SelectItem>();
            mailAddTypeList.add(new SelectItem("0", "--Select--"));
            juriAddTypeList = new ArrayList<SelectItem>();
            juriAddTypeList.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> addTypeList = masterDelegate.getFunctionValues("354");

            for (CbsRefRecTypeTO recTypeTO : addTypeList) {
                perAddTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                mailAddTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                juriAddTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            juriAddBasedList = new ArrayList<SelectItem>();
            juriAddBasedList.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> juriAddBaseList = masterDelegate.getFunctionValues("355");
            for (CbsRefRecTypeTO recTypeTO : juriAddBaseList) {
                juriAddBasedList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            prefPoaAddList = new ArrayList<SelectItem>();
            mailPoaList = new ArrayList<SelectItem>();
            juriPoaList = new ArrayList<SelectItem>();
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public List<String> getDistrictList(Object arg) {
        String prefix = (String) arg;
        ArrayList<String> result = new ArrayList<String>();
        try {
            optionlist = masterDelegate.getDistrictListLike(this.stateForPermanentAddress, prefix,"S");
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

    public List<String> getMailDistrictList(Object arg) {
        String prefix = (String) arg;
        ArrayList<String> result = new ArrayList<String>();
        try {
            optionlist = masterDelegate.getDistrictListLike(this.stateForMailAddress, prefix,"S");
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

    public List<String> getEmpDistrictList(Object arg) {
        String prefix = (String) arg;
        ArrayList<String> result = new ArrayList<String>();
        try {
            optionlist = masterDelegate.getDistrictListLike(this.stateForEmpAddress, prefix,"S");
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

    public List<String> getJuriDistrictList(Object arg) {
        String prefix = (String) arg;
        ArrayList<String> result = new ArrayList<String>();
        try {
            optionlist = masterDelegate.getDistrictListLike(this.juriState, prefix,"S");
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

    public void onclickCheckBox() {
        if (mailAddSameAsPerAdd == true) {
            if (this.perAddType.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Address Type in Permanent Address.");
                return;
            }
            if (this.prefPoaAdd.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Proof of Address in Permanent Address.");
                return;
            }
            if (this.getPerOtherPOA().equalsIgnoreCase("99")) {
                if (this.getPerOtherPOA() == null || this.getPerOtherPOA().equalsIgnoreCase("")) {
                    getCustomerDetail().setErrorMsg("Please Fill Other POI  in Permanent Address.");
                    return;
                }
            }
            if (this.perAdd1 == null || this.perAdd1.equals("")) {
                getCustomerDetail().setErrorMsg("Please Fill Permanent Address Line1 Field.");
                return;
            }
            if (this.perVillage == null || this.perVillage.equals("")) {
                getCustomerDetail().setErrorMsg("Please fill City / Town / Village in Permanent Address Portion on Address Information tab");
                return;
            }
            if (this.countryForPermanentAddress.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Country in Permanent Address.");
                return;
            }

            if (this.countryForPermanentAddress.equalsIgnoreCase("IN")) {
                if (this.stateForPermanentAddress.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Select State in Permanent Address.");
                    return;
                }
                if (this.perDistrict.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Select District in Permanent Address.");
                    return;
                }
            } else {
                if (this.perStateNotIN.equals("")) {
                    getCustomerDetail().setErrorMsg("Please fill State in Permanent Address.");
                    return;
                }
                if (this.perDistrictNotIN.equals("")) {
                    getCustomerDetail().setErrorMsg("Please fill District in Permanent Address.");
                    return;
                }
            }

            try {
                optionlist = aitr.getListAsPerRequirement("011", this.stateForPermanentAddress, "DIST", "0", "0", "0",ymd.format(new Date()),0);
                districtForMailAddressOption = new ArrayList<SelectItem>();
                districtForMailAddressOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    districtForMailAddressOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            } catch (ApplicationException ex) {
                getCustomerDetail().setErrorMsg(ex.getMessage());
            }

            getCustomerDetail().setErrorMsg("");
            this.setMailAddType(this.getPerAddType());
            this.setMailPoa(this.getPrefPoaAdd());
            if (this.getMailPoa().equalsIgnoreCase("99")) {
                this.mailOtherPOADisplay = "";
                this.setMailOtherPOA(this.getPerOtherPOA());
            } else {
                this.mailOtherPOADisplay = "none";
                this.setMailOtherPOA("");
            }
            this.setMailAdd1(this.getPerAdd1());
            this.setMailAdd2(this.getPerAdd2());
            this.setMailVillage(this.getPerVillage());
            this.setMailBlock(this.getPerBlock());
            this.setCountryForMailAddress(this.getCountryForPermanentAddress());
            if (this.getCountryForPermanentAddress().equalsIgnoreCase("IN")) {
                displayOnMailCntryIN = "";
                displayOnMailCntryNotIN = "none";
                this.setMailDistrict(this.getPerDistrict());
                this.setStateForMailAddress(this.getStateForPermanentAddress());
            } else {
                displayOnMailCntryIN = "none";
                displayOnMailCntryNotIN = "";
                this.setMailDistrictNotIN(this.getPerDistrictNotIN());
                this.setMailStateNotIN(this.getPerStateNotIN());
            }
            this.setMailPostalCode(this.getPerpostalCode());
            this.setMailPhoneNo(this.getPerPhoneNo());
            this.setMailFax(this.getPerFaxNo());
            this.setMailEmail(this.getPerEmail());
//            this.setMailTelexNo(this.getPertelexNo());
        } else {
            getCustomerDetail().setErrorMsg("");
            this.setMailAddType("0");
            this.setMailPoa("0");
            this.setMailOtherPOA("");
            this.setMailAdd1("");
            this.setMailAdd2("");
            this.setMailVillage("");
            this.setMailBlock("");
            this.setMailDistrict("");
            this.setStateForMailAddress("0");
            this.setCountryForMailAddress("0");
            this.setMailPostalCode("");
            this.setMailPhoneNo("");
            this.setMailFax("");
            this.setMailEmail("");
            this.setMailDistrictNotIN("");
            this.setMailStateNotIN("");
//            this.setMailTelexNo("");
        }
    }

    public void onchangeJuriAddBasedFlag() {
        if (juriAddBasedOnFlag.equalsIgnoreCase("01")) {

            if (this.perAddType.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Address Type in Permanent Address.");
                return;
            }
            if (this.prefPoaAdd.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Proof of Address in Permanent Address.");
                return;
            }
            if (this.countryForPermanentAddress.equalsIgnoreCase("IN")) {

                if (this.perDistrict.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please Select District in Permanent Address.");
                    return;
                }
                if (this.stateForPermanentAddress.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please Select State in Permanent Address.");
                    return;
                }
            } else {
                if (this.perDistrictNotIN == null || this.perDistrictNotIN.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Fill District in Permanent Address.");
                    return;
                }
                if (this.perStateNotIN == null || this.perStateNotIN.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Fill State in Permanent Address.");
                    return;
                }
            }
            if (this.perpostalCode.equals("")) {
                getCustomerDetail().setErrorMsg("Please Fill Postal Code in Permanent Address.");
                return;
            }


            try {
                optionlist = aitr.getListAsPerRequirement("011", this.stateForPermanentAddress, "DIST", "0", "0", "0",ymd.format(new Date()),0);
                districtForJuriAddressOption = new ArrayList<SelectItem>();
                districtForJuriAddressOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    districtForJuriAddressOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            } catch (ApplicationException ex) {
                getCustomerDetail().setErrorMsg(ex.getMessage());
            }

            getCustomerDetail().setErrorMsg("");
            this.setJuriAddType(this.getPerAddType());
            this.setJuriPoa(this.getPrefPoaAdd());
            if (this.getJuriPoa().equalsIgnoreCase("99")) {
                this.juriOtherPOADisplay = "";
                this.setJuriOtherPOA(this.getPerOtherPOA());
            } else {
                this.juriOtherPOADisplay = "none";
                this.setJuriOtherPOA("");
            }
            this.setJuriPostalCode(this.getPerpostalCode());
            this.setJuriCountryCode(this.getCountryForPermanentAddress());
            if (this.getCountryForPermanentAddress().equalsIgnoreCase("IN")) {
                displayOnJuriCntryIN = "";
                displayOnJuriCntryNotIN = "none";
                this.setJuriDistrict(this.getPerDistrict());
                this.setJuriState(this.getStateForPermanentAddress());
            } else {
                displayOnJuriCntryIN = "none";
                displayOnJuriCntryNotIN = "";
                this.setJuriDistrictNotIN(this.getPerDistrictNotIN());
                this.setJuriStateNotIN(this.getPerStateNotIN());
            }
            this.setJuriAdd1(this.getPerAdd1());
            this.setJuriCity(this.getPerVillage());
        } else if (juriAddBasedOnFlag.equalsIgnoreCase("02")) {

            if (this.mailAddType.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Address Type in Mailing Address.");
                return;
            }
            if (this.mailPoa.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Proof of Address in Mailing Address.");
                return;
            }
            if (countryForMailAddress.equalsIgnoreCase("IN")) {
                if (this.mailDistrict.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please Select District in Mailing Address.");
                    return;
                }
                if (this.stateForMailAddress.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please Select State in Mailing Address.");
                    return;
                }
            } else {
                if (this.mailDistrict.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Fill District in Mailing Address.");
                    return;
                }
                if (this.stateForMailAddress.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Fill State in Mailing Address.");
                    return;
                }
            }
            if (this.mailPostalCode.equals("")) {
                getCustomerDetail().setErrorMsg("Please Fill Postal Code in Mailing Address.");
                return;
            }
            try {
                optionlist = aitr.getListAsPerRequirement("011", this.stateForPermanentAddress, "DIST", "0", "0", "0",ymd.format(new Date()),0);
                districtForJuriAddressOption = new ArrayList<SelectItem>();
                districtForJuriAddressOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    districtForJuriAddressOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            } catch (ApplicationException ex) {
                getCustomerDetail().setErrorMsg(ex.getMessage());
            }
            getCustomerDetail().setErrorMsg("");
            this.setJuriAddType(this.getMailAddType());
            this.setJuriPoa(this.getMailPoa());
            if (this.getJuriPoa().equalsIgnoreCase("99")) {
                this.juriOtherPOADisplay = "";
                this.setJuriOtherPOA(this.getMailOtherPOA());
            } else {
                this.juriOtherPOADisplay = "none";
                this.setJuriOtherPOA("");
            }
            this.setJuriPostalCode(this.getMailPostalCode());
            this.setJuriCountryCode(this.getCountryForPermanentAddress());
            if (this.getCountryForPermanentAddress().equalsIgnoreCase("IN")) {
                displayOnJuriCntryIN = "";
                displayOnJuriCntryNotIN = "none";
                this.setJuriState(this.getStateForMailAddress());
                this.setJuriDistrict(this.getMailDistrict());
            } else {
                displayOnJuriCntryIN = "none";
                displayOnJuriCntryNotIN = "";
                this.setJuriStateNotIN(this.getMailStateNotIN());
                this.setJuriDistrictNotIN(this.getMailDistrictNotIN());
            }
            this.setJuriAdd1(this.getMailAdd1());
            this.setJuriCity(this.getMailVillage());
        } else if (juriAddBasedOnFlag.equalsIgnoreCase("03") || juriAddBasedOnFlag.equalsIgnoreCase("0")) {
            getCustomerDetail().setErrorMsg("");
            this.juriOtherPOADisplay = "none";
            this.setJuriAddType("0");
            this.setJuriPoa("0");
            this.setJuriOtherPOA("");
            this.setJuriState("0");
            this.setJuriDistrict("");
            this.setJuriStateNotIN("");
            this.setJuriDistrictNotIN("");
            this.setJuriPostalCode("0");
            this.setJuriCountryCode("0");
            this.setJuriAdd1("");
            this.setJuriCity("");
        }
    }

    public void onChangePerPOA() {
        perOtherPOADisplay = "none";
        perOtherPOA = "";
        if (this.prefPoaAdd.equalsIgnoreCase("99")) {
            perOtherPOADisplay = "";
        }
    }

    public void onChangeMailPOA() {
        mailOtherPOA = "";
        mailOtherPOADisplay = "none";
        if (this.mailPoa.equalsIgnoreCase("99")) {
            mailOtherPOADisplay = "";
        }
    }

    public void onChangeJuriPOA() {
        juriOtherPOA = "";
        juriOtherPOADisplay = "none";
        if (this.juriPoa.equalsIgnoreCase("99")) {
            juriOtherPOADisplay = "";
        }

    }

    public void onChangePerCountry() {
        if (!countryForPermanentAddress.equalsIgnoreCase("IN")) {
            displayOnPerCntryIN = "none";
            displayOnPerCntryNotIN = "";
        } else {
            displayOnPerCntryIN = "";
            displayOnPerCntryNotIN = "none";
        }
    }

    public void onChangeMailCountry() {
        if (!countryForMailAddress.equalsIgnoreCase("IN")) {
            displayOnMailCntryIN = "none";
            displayOnMailCntryNotIN = "";
        } else {
            displayOnMailCntryIN = "";
            displayOnMailCntryNotIN = "none";
        }
    }

    public void onChangeEmpCountry() {
        if (!countryForEmpAddress.equalsIgnoreCase("IN")) {
            displayOnEmpCntryIN = "none";
            displayOnEmpCntryNotIN = "";
        } else {
            displayOnEmpCntryIN = "";
            displayOnEmpCntryNotIN = "none";
        }
    }

    public void onChangeJuriCountry() {
        if (!juriCountryCode.equalsIgnoreCase("IN")) {
            displayOnJuriCntryIN = "none";
            displayOnJuriCntryNotIN = "";
        } else {
            displayOnJuriCntryIN = "";
            displayOnJuriCntryNotIN = "none";
        }
    }

    public void onChangePerState() {
        this.perDistrict = "";
    }

    public void onChangeMailState() {
        this.mailDistrict = "";

    }

    public void onChangeEmpState() {
        this.empDistrict = "";
    }

    public void onChangeJuriState() {
        this.juriDistrict = "";
    }

//    public void onChangePerDist() {
//        try {
//            optionlist = aitr.getListAsPerRequirement("009", this.perDistrict, "POST", "0", "0", "0");
//            perPostalCodeList = new ArrayList<SelectItem>();
//            perPostalCodeList.add(new SelectItem("0", "--Select--"));
//            for (int i = 0; i < optionlist.size(); i++) {
//                Vector ele6 = (Vector) optionlist.get(i);
//                perPostalCodeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
//            }
//        } catch (ApplicationException ex) {
//            getCustomerDetail().setErrorMsg(ex.getMessage());
//        }
//    }
//
//    public void onChangeMailDist() {
//        try {
//            optionlist = aitr.getListAsPerRequirement("011", this.mailDistrict, "POST", "0", "0", "0");
//            mailPostalCodeList = new ArrayList<SelectItem>();
//            mailPostalCodeList.add(new SelectItem("565656", "--Select--"));
//            for (int i = 0; i < optionlist.size(); i++) {
//                Vector ele6 = (Vector) optionlist.get(i);
//                mailPostalCodeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
//            }
//        } catch (ApplicationException ex) {
//            getCustomerDetail().setErrorMsg(ex.getMessage());
//        }
//    }
//
//    public void onChangeEmpDist() {
//        try {
//            optionlist = aitr.getListAsPerRequirement("011", this.empDistrict, "POST", "0", "0", "0");
//            empPostalCodeList = new ArrayList<SelectItem>();
//            empPostalCodeList.add(new SelectItem("565656", "--Select--"));
//            for (int i = 0; i < optionlist.size(); i++) {
//                Vector ele6 = (Vector) optionlist.get(i);
//                empPostalCodeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
//            }
//        } catch (ApplicationException ex) {
//            getCustomerDetail().setErrorMsg(ex.getMessage());
//        }
//    }
//
//    public void onChangejuriDist() {
//        try {
//            optionlist = aitr.getListAsPerRequirement("011", this.empDistrict, "POST", "0", "0", "0");
//            juriPostalCodeList = new ArrayList<SelectItem>();
//            juriPostalCodeList.add(new SelectItem("565656", "--Select--"));
//            for (int i = 0; i < optionlist.size(); i++) {
//                Vector ele6 = (Vector) optionlist.get(i);
//                juriPostalCodeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
//            }
//        } catch (ApplicationException ex) {
//            getCustomerDetail().setErrorMsg(ex.getMessage());
//        }
//    }
    public String validatePermanentPortion() {
        try {
            getCustomerDetail().setErrorMsg("");
            if (this.perAddType == null || this.perAddType.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Address Type in Permanent Address Portion on Address Information tab");
                return "Please Select Address Type in Permanent Address Portion on Address Information tab";
            }
            if (this.prefPoaAdd == null || this.prefPoaAdd.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Proof of Address in Permanent Address Portion on Address Information tab");
                return "Please Select Proof of Address in Permanent Address Portion on Address Information tab";
            }
            if (this.prefPoaAdd.equals("99")) {
                if (this.perOtherPOA == null || this.perOtherPOA.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Fill Other Type POA in Permanent Address Portion on Address Information tab");
                    return "Please Fill Other Type POA in Permanent Address Portion on Address Information tab";
                } else {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.perOtherPOA);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please Fill Proper Other Type POA in Permanent Address Portion on Address Information tab");
                        return "Please Fill Proper Other Type POA in Permanent Address Portion on Address Information tab";
                    }
                }
            }
            if (this.perAdd1 == null || this.perAdd1.equals("")) {
                getCustomerDetail().setErrorMsg("Please fill Address Line1 in Permanent Address Portion on Address Information tab");
                return "Please fill Address Line1 in Permanent Address Portion on Address Information tab";
            }
            if (this.perVillage == null || this.perVillage.equals("")) {
                getCustomerDetail().setErrorMsg("Please fill City / Town / Village in Permanent Address Portion on Address Information tab");
                return "Please fill City / Town / Village in Permanent Address Portion on Address Information tab";
            } else {
                Matcher matcher = onlyAlphabetWitSpace.matcher(this.perVillage);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please fill Proper City / Town / Village in Permanent Address Portion on Address Information tab");
                    return "Please fill Proper City / Town / Village in Permanent Address Portion on Address Information tab";
                }
            }
            if (this.countryForPermanentAddress == null || this.countryForPermanentAddress.equals("0")) {
                getCustomerDetail().setErrorMsg("Please select Country in Permanent Address Portion on Address Information tab");
                return "Please select Country in Permanent Address Portion on Address Information tab";
            }
            if (this.countryForPermanentAddress.equals("IN")) {

                if (this.stateForPermanentAddress == null || this.stateForPermanentAddress.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please select State in Permanent Address Portion on Address Information tab");
                    return "Please select State in Permanent Address Portion on Address Information tab";
                }
                if (this.perDistrict == null || this.perDistrict.equals("")) {
                    getCustomerDetail().setErrorMsg("Please select District in Permanent Address Portion on Address Information tab");
                    return "Please select District in Permanent Address Portion on Address Information tab";
                }
                List option = masterDelegate.getDistrictListLike(this.stateForPermanentAddress, this.perDistrict,"V");
                if (option.size() <= 0) {
                    getCustomerDetail().setErrorMsg("Please select proper District in Permanent Address Portion on Address Information tab");
                    return "Please select proper District in Permanent Address Portion on Address Information tab";
                } else if (!this.perDistrict.equalsIgnoreCase(((Vector) option.get(0)).get(0).toString())) {
                    getCustomerDetail().setErrorMsg("Please select proper District in Permanent Address Portion on Address Information tab");
                    return "Please select proper District in Permanent Address Portion on Address Information tab";
                }
            } else {
                if ((this.perStateNotIN == null || this.perStateNotIN.equals(""))) {
                    getCustomerDetail().setErrorMsg("Please fill State in Permanent Address Portion on Address Information tab");
                    return "Please fill State in Permanent Address Portion on Address Information tab";
                } else {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.perStateNotIN);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper State in Permanent Address Portion on Address Information tab");
                        return "Please fill Proper State in Permanent Address Portion on Address Information tab";
                    }
                }
                if ((this.perDistrictNotIN == null || this.perDistrictNotIN.equals(""))) {
                    getCustomerDetail().setErrorMsg("Please fill District in Permanent Address Portion on Address Information tab");
                    return "Please fill District in Permanent Address Portion on Address Information tab";
                } else {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.perDistrictNotIN);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper District in Permanent Address Portion on Address Information tab");
                        return "Please fill Proper District in Permanent Address Portion on Address Information tab";
                    }
                }
            }
            if (this.perpostalCode == null || this.perpostalCode.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please fill Postal Code in Permanent Address Portion on Address Information tab");
                return "Please fill Postal Code in Permanent Address Portion on Address Information tab";
            } else {

                Matcher matcher = onlyNumeric.matcher(this.perpostalCode);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Permanent Address Portion on Address Information tab");
                    return "Please fill Proper 6 digit Postal/Zip Code in Permanent Address Portion on Address Information tab";
                }
              if(this.countryForPermanentAddress.equals("IN")){
                if (this.perpostalCode.trim().length() != 6) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Permanent Address Portion on Address Information tab");
                    return "Please fill Proper 6 digit Postal/Zip Code in Permanent Address Portion on Address Information tab";
                }
                if (!ckycrRemote.isValidPostalCode(perpostalCode, stateForPermanentAddress)) {
                    getCustomerDetail().setErrorMsg("Please fill Valid Postal/Zip Code in Permanent Address Portion on Address Information tab");
                    return "Please fill Valid Postal/Zip Code in Permanent Address Portion on Address Information tab";
                }}

            }
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        if (!(this.perPhoneNo == null || this.perPhoneNo.equalsIgnoreCase(""))) {
            Matcher matcher = onlyNumeric.matcher(this.perPhoneNo);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please fill numeric value Phone No in Permanent Address Portion on Address Information tab");
                return "Please fill numeric value Phone No in Permanent Address Portion on Address Information tab";
            }
        }
        if (!(this.perFaxNo == null || this.perFaxNo.equalsIgnoreCase(""))) {
            Matcher matcher = onlyNumeric.matcher(this.perFaxNo);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please fill numeric value Fax No in Permanent Address Portion on Address Information tab");
                return "Please fill numeric value Fax No in Permanent Address Portion on Address Information tab";
            }
        }
        if (!(this.perEmail == null || this.perEmail.equalsIgnoreCase(""))) {
            if (!new Validator().validateEmail(this.perEmail)) {
                getCustomerDetail().setErrorMsg("Please fill proper Email Id in Permanent Address Portion on Address Information tab");
                return "Please fill proper Email Id in Permanent Address Portion on Address Information tab";
            }
        }
        return "true";
    }

    public String validateMailingPortion() {
        try {
            getCustomerDetail().setErrorMsg("");
            if (this.mailAddType == null || this.mailAddType.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Address Type in Mailing Address Portion on Address Information tab");
                return "Please Select Address Type in Mailing Address Portion on Address Information tab";
            }
            if (this.mailPoa == null || this.mailPoa.equals("0")) {
                getCustomerDetail().setErrorMsg("Please Select Proof of Address in Mailing Address Portion on Address Information tab");
                return "Please Select Proof of Address in Mailing Address Portion on Address Information tab";
            }
            if (this.mailPoa.equals("99")) {
                if (this.mailOtherPOA == null || this.mailOtherPOA.equals("")) {
                    getCustomerDetail().setErrorMsg("Please Fill Other Type POA in Mailing Address Portion on Address Information tab");
                    return "Please Fill Other Type POA in Mailing Address Portion on Address Information tab";
                } else {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.mailOtherPOA);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please Fill Proper Other Type POA in Mailing Address Portion on Address Information tab");
                        return "Please Fill Proper Other Type POA in Mailing Address Portion on Address Information tab";
                    }
                }
            }
            if (this.mailAdd1 == null || this.mailAdd1.equals("")) {
                getCustomerDetail().setErrorMsg("Please fill Address Line1 in Mailing Address Portion on Address Information tab");
                return "Please fill Address Line1 in Mailing Address Portion on Address Information tab";
            }
            if (this.mailVillage == null || this.mailVillage.equals("")) {
                getCustomerDetail().setErrorMsg("Please fill City / Town / Village in Mailing Address Portion on Address Information tab");
                return "Please fill City / Town / Village in Mailing Address Portion on Address Information tab";
            } else {
                Matcher matcher = onlyAlphabetWitSpace.matcher(this.mailVillage);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please fill Proper City / Town / Village in Mailing Address Portion on Address Information tab");
                    return "Please fill Proper City / Town / Village in Mailing Address Portion on Address Information tab";
                }
            }
            if (this.countryForMailAddress == null || this.countryForMailAddress.equals("0")) {
                getCustomerDetail().setErrorMsg("Please select Country in Mailing Address Portion on Address Information tab");
                return "Please select Country in Mailing Address Portion on Address Information tab";
            }
            if (this.countryForPermanentAddress.equals("IN")) {

                if (this.stateForMailAddress == null || this.stateForMailAddress.equals("")) {
                    getCustomerDetail().setErrorMsg("Please select State in Mailing Address Portion on Address Information tab");
                    return "Please select State in Mailing Address Portion on Address Information tab";
                }
                if (this.mailDistrict == null || this.mailDistrict.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please select District in Mailing Address Portion on Address Information tab");
                    return "Please select District in Mailing Address Portion on Address Information tab";
                }
                List option = masterDelegate.getDistrictListLike(this.stateForMailAddress, this.mailDistrict,"V");
                if (option.size() <= 0) {
                    getCustomerDetail().setErrorMsg("Please select proper District in Mailing Address Portion on Address Information tab");
                    return "Please select proper District in Mailing Address Portion on Address Information tab";
                } else if (!this.mailDistrict.equalsIgnoreCase(((Vector) option.get(0)).get(0).toString())) {
                    getCustomerDetail().setErrorMsg("Please select proper District in Mailing Address Portion on Address Information tab");
                    return "Please select proper District in Mailing Address Portion on Address Information tab";
                }

            } else {
                if ((this.mailStateNotIN == null || this.mailStateNotIN.equals(""))) {
                    getCustomerDetail().setErrorMsg("Please fill State in Mailing Address Portion on Address Information tab");
                    return "Please fill State in Mailing Address Portion on Address Information tab";
                } else {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.mailStateNotIN);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper State in Mailing Address Portion on Address Information tab");
                        return "Please fill Proper State in Mailing Address Portion on Address Information tab";
                    }
                }
                if ((this.mailDistrictNotIN == null || this.mailDistrictNotIN.equals(""))) {
                    getCustomerDetail().setErrorMsg("Please fill District in Mailing Address Portion on Address Information tab");
                    return "Please fill District in Mailing Address Portion on Address Information tab";
                } else {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.mailDistrictNotIN);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper District in Mailing Address Portion on Address Information tab");
                        return "Please fill Proper District in Mailing Address Portion on Address Information tab";
                    }
                }
            }
            if (this.mailPostalCode == null || this.mailPostalCode.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please select Postal Code in Mailing Address Portion on Address Information tab");
                return "Please select Postal Code in Mailing Address Portion on Address Information tab";
            } else {

                Matcher matcher = onlyNumeric.matcher(this.mailPostalCode);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Mailing Address Portion on Address Information tab");
                    return "Please fill Proper 6 digit Postal/Zip Code in Mailing Address Portion on Address Information tab";
                }
                if(this.countryForMailAddress.equalsIgnoreCase("IN")){
                if (this.mailPostalCode.trim().length() != 6) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Mailing Address Portion on Address Information tab");
                    return "Please fill Proper 6 digit Postal/Zip Code in Mailing Address Portion on Address Information tab";
                }
                if (!ckycrRemote.isValidPostalCode(mailPostalCode, stateForMailAddress)) {
                    getCustomerDetail().setErrorMsg("Please fill Valid Postal/Zip Code in Mailing Address Portion on Address Information tab");
                    return "Please fill Valid Postal/Zip Code in Mailing Address Portion on Address Information tab";
                }
            }}
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        if (!(this.mailPhoneNo == null || this.mailPhoneNo.equalsIgnoreCase(""))) {
            Matcher matcher = onlyNumeric.matcher(this.mailPhoneNo);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please fill numeric value Phone No in Mailing Address Portion on Address Information tab");
                return "Please fill numeric value Phone No in Mailing Address Portion on Address Information tab";
            }
        }
        if (!(this.mailFax == null || this.mailFax.equalsIgnoreCase(""))) {
            Matcher matcher = onlyNumeric.matcher(this.mailFax);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please fill numeric value Fax No in Mailing Address Portion on Address Information tab");
                return "Please fill numeric value Fax No in Mailing Address Portion on Address Information tab";
            }
        }
        if (!(this.mailEmail == null || this.mailEmail.equalsIgnoreCase(""))) {
            if (!new Validator().validateEmail(this.mailEmail)) {
                getCustomerDetail().setErrorMsg("Please fill proper Email Id in Mailing Address Portion on Address Information tab");
                return "Please fill proper Email Id in Mailing Address Portion on Address Information tab";
            }
        }
        return "true";
    }

    public String validateEmployerPortion() {
        try {
            getCustomerDetail().setErrorMsg("");
            if (!(this.empVillage == null || this.empVillage.equals(""))) {
                Matcher matcher = onlyAlphabetWitSpace.matcher(this.empVillage);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please fill Proper City / Town / Village in Employer Address Portion on Address Information tab");
                    return "Please fill Proper City / Town / Village in Employer Address Portion on Address Information tab";
                }
            }
            if (!this.countryForEmpAddress.equals("IN")) {
                if (!(this.empStateNotIN == null || this.empStateNotIN.equals(""))) {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.empStateNotIN);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper State in Employer Address Portion on Address Information tab");
                        return "Please fill Proper State in Employer Address Portion on Address Information tab";
                    }
                }
                if (!(this.empDistrictNotIN == null || this.empDistrictNotIN.equals(""))) {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.empDistrictNotIN);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper District in Employer Address Portion on Address Information tab");
                        return "Please fill Proper District in Employer Address Portion on Address Information tab";
                    }
                }
            }
            if (!(this.empPostalCode == null || this.empPostalCode.equalsIgnoreCase(""))) {
                Matcher matcher = onlyNumeric.matcher(this.empPostalCode);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please fill numeric value of 6 digit in Postal Code on Employer Address Portion on Address Information tab");
                    return "Please fill numeric value of 6 digit in Postal Code on Employer Address Portion on Address Information tab";
                }
                if(this.countryForEmpAddress.equalsIgnoreCase("IN")){
                if (this.empPostalCode.trim().length() != 6) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Employer Address Portion on Address Information tab");
                    return "Please fill Proper 6 digit Postal/Zip Code in Employer Address Portion on Address Information tab";
                }
                if (!ckycrRemote.isValidPostalCode(empPostalCode, stateForEmpAddress)) {
                    getCustomerDetail().setErrorMsg("Please fill Valid Postal/Zip Code in Employer Address Portion on Address Information tab");
                    return "Please fill Valid Postal/Zip Code in Employer Address Portion on Address Information tab";
                }
            }}
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        if (!(this.empPhoneNo == null || this.empPhoneNo.equalsIgnoreCase(""))) {
            Matcher matcher = onlyNumeric.matcher(this.empPhoneNo);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please fill numeric value Phone No in Employer Address Portion on Address Information tab");
                return "Please fill numeric value Phone No in Employer Address Portion on Address Information tab";
            }
        }
        if (!(this.empFax == null || this.empFax.equalsIgnoreCase(""))) {
            Matcher matcher = onlyNumeric.matcher(this.empFax);
            if (!matcher.matches()) {
                getCustomerDetail().setErrorMsg("Please fill numeric value Fax No in Employer Address Portion on Address Information tab");
                return "Please fill numeric value Fax No in Employer Address Portion on Address Information tab";
            }
        }
        if (!(this.empEmail == null || this.empEmail.equalsIgnoreCase(""))) {
            if (!new Validator().validateEmail(this.empEmail)) {
                getCustomerDetail().setErrorMsg("Please fill proper Email Id in Employer Address Portion on Address Information tab");
                return "Please fill proper Email Id in Employer Address Portion on Address Information tab";
            }
        }
        return "true";
    }

    public String validateJuriAddressPortion() {
        try {
            getCustomerDetail().setErrorMsg("");
            if ((this.juriAddBasedOnFlag == null || this.juriAddBasedOnFlag.equalsIgnoreCase("0"))) {
                getCustomerDetail().setErrorMsg("Please Select Juridiction Address As in Juridiction Address Portion on Address Information tab");
                return "Please Select Juridiction Address As in Juridiction Address Portion on Address Information tab";
            }
            if ((this.juriAddBasedOnFlag == null || this.juriAddBasedOnFlag.equalsIgnoreCase("0"))) {
                if (!((this.juriAddType == null || this.juriAddType.equalsIgnoreCase("0"))
                        && (this.juriPoa == null || this.juriPoa.equalsIgnoreCase("0"))
                        && (this.juriCountryCode == null || this.juriCountryCode.equalsIgnoreCase("0"))
                        && (this.juriState == null || this.juriState.equalsIgnoreCase("0"))
                        && (this.juriPostalCode == null || this.juriPostalCode.equalsIgnoreCase("0")))) {
                    getCustomerDetail().setErrorMsg("Please Select Juridiction Address As in Juridiction Address Portion on Address Information tab");
                    return "Please Select Juridiction Address As in Juridiction Address Portion on Address Information tab";
                }
            } else {
                if (this.juriAddType == null || this.juriAddType.equalsIgnoreCase("0")) {
                    getCustomerDetail().setErrorMsg("Please Select Address Type in Juridiction Address Portion on Address Information tab");
                    return "Please Select Address Type in Juridiction Address Portion on Address Information tab";
                }
                if (this.juriPoa == null || this.juriPoa.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please Select Proof of Address in Juridiction Address Portion on Address Information tab");
                    return "Please Select Proof of Address in Juridiction Address Portion on Address Information tab";
                }
                if (this.juriPoa.equals("99")) {
                    if (this.juriOtherPOA == null || this.juriOtherPOA.equals("")) {
                        getCustomerDetail().setErrorMsg("Please Fill Other Type POA in Juridiction Address Portion on Address Information tab");
                        return "Please Fill Other Type POA in Juridiction Address Portion on Address Information tab";
                    } else {
                        Matcher matcher = onlyAlphabetWitSpace.matcher(this.juriOtherPOA);
                        if (!matcher.matches()) {
                            getCustomerDetail().setErrorMsg("Please Fill Proper Other Type POA in Juridiction Address Portion on Address Information tab");
                            return "Please Fill Proper Other Type POA in Juridiction Address Portion on Address Information tab";
                        }
                    }
                }
                if (this.juriAdd1 == null || this.juriAdd1.equals("")) {
                    getCustomerDetail().setErrorMsg("Please fill Address Line1 in Juridiction Address Portion on Address Information tab");
                    return "Please fill Address in Juridiction Address Portion on Address Information tab";
                }
                if (this.juriCity == null || this.juriCity.equals("")) {
                    getCustomerDetail().setErrorMsg("Please fill City / Town / Village in Juridiction Address Portion on Address Information tab");
                    return "Please fill City / Town / Village in Juridiction Address Portion on Address Information tab";
                }
                if (this.juriCountryCode == null || this.juriCountryCode.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please Select Country in Juridiction Address Portion on Address Information tab");
                    return "Please Select Country in Juridiction Address Portion on Address Information tab";
                }
                if (this.juriCountryCode.equals("IN")) {

                    if (this.juriState == null || this.juriState.equals("0")) {
                        getCustomerDetail().setErrorMsg("Please Select State in Juridiction Address Portion on Address Information tab");
                        return "Please select State in Juridiction Address Portion on Address Information tab";
                    }
                    if (this.juriDistrict == null || this.juriDistrict.equals("0")) {
                        getCustomerDetail().setErrorMsg("Please Select District in Juridiction Address Portion on Address Information tab");
                        return "Please select District in Juridiction Address Portion on Address Information tab";
                    }
                    List option = masterDelegate.getDistrictListLike(this.juriState, this.juriDistrict,"V");
                    if (option.size() <= 0) {
                        getCustomerDetail().setErrorMsg("Please select proper District in Juridiction Address Portion on Address Information tab");
                        return "Please select proper District in Juridiction Address Portion on Address Information tab";
                    } else if (!this.juriDistrict.equalsIgnoreCase(((Vector) option.get(0)).get(0).toString())) {
                        getCustomerDetail().setErrorMsg("Please select proper District in Juridiction Address Portion on Address Information tab");
                        return "Please select proper District in Juridiction Address Portion on Address Information tab";
                    }
                } else {
                    if (!(this.juriStateNotIN == null || this.juriStateNotIN.equals(""))) {
                        Matcher matcher = onlyAlphabetWitSpace.matcher(this.juriStateNotIN);
                        if (!matcher.matches()) {
                            getCustomerDetail().setErrorMsg("Please fill Proper State in Juridiction Address Portion on Address Information tab");
                            return "Please fill Proper State in Juridiction Address Portion on Address Information tab";
                        }
                    }
                    if (!(this.juriDistrictNotIN == null || this.juriDistrictNotIN.equals(""))) {
                        Matcher matcher = onlyAlphabetWitSpace.matcher(this.juriDistrictNotIN);
                        if (!matcher.matches()) {
                            getCustomerDetail().setErrorMsg("Please fill Proper District in Juridiction Address Portion on Address Information tab");
                            return "Please fill Proper District in Juridiction Address Portion on Address Information tab";
                        }
                    }
                }
                if (this.juriPostalCode == null || this.juriPostalCode.equalsIgnoreCase("")) {
                    getCustomerDetail().setErrorMsg("Please fill Postal Code in Juridiction Address Portion on Address Information tab");
                    return "Please fill Postal Code in Juridiction Address Portion on Address Information tab";
                } else {
                    Matcher matcher = onlyNumeric.matcher(this.juriPostalCode);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Juridiction Address Portion on Address Information tab");
                        return "Please fill Proper 6 digit Postal/Zip Code in Juridiction Address Portion on Address Information tab";
                    }
                    if(this.countryForPermanentAddress.equalsIgnoreCase("IN")){
                    if (this.juriPostalCode.trim().length() != 6) {
                        getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Juridiction Address Portion on Address Information tab");
                        return "Please fill Proper 6 digit Postal/Zip Code in Juridiction Address Portion on Address Information tab";
                    }
                    if (!ckycrRemote.isValidPostalCode(juriPostalCode, juriState)) {
                        getCustomerDetail().setErrorMsg("Please fill Valid Postal/Zip Code in Juridiction Address Portion on Address Information tab");
                        return "Please fill Valid Postal/Zip Code in Juridiction Address Portion on Address Information tab";
                    }
                }}

            }
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        return "true";
    }

    //    public String onblurAddressVillage() {
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        Matcher addressVillCheck = p.matcher(this.perVillage);
//        if (addressVillCheck.matches()) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Customer Permanent Address Village.");
//            return "Please Enter Non Numeric Value in Customer Permanent Address Village.";
//        } else if (this.perVillage.contains("0") || this.perVillage.contains("1") || this.perVillage.contains("2") || this.perVillage.contains("3") || this.perVillage.contains("4") || this.perVillage.contains("5")
//                || this.perVillage.contains("6") || this.perVillage.contains("7") || this.perVillage.contains("8") || this.perVillage.contains("9")) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Customer Permanent Address Village.");
//            return "Please Enter Non Numeric Value in Customer Permanent Address Village.";
//        } else {
//            getCustomerDetail().setErrorMsg("");
//            return "true";
//        }
//        getCustomerDetail().setErrorMsg("");
//        return "true";
//    }
//    public String onblurMailAddressPostalCode() {
//        if (this.mailAdd1 == null || this.mailAdd1.equals("")) {
//            getCustomerDetail().setErrorMsg("Please Fill Address Line1 in Mailing Address Portion on Address Information tab.");
//            return "Please Fill Address Line1 in Mailing Address Portion on Address Information tab.";
//        }
//        if (this.cityForMailAddress.equals("0")) {
//            getCustomerDetail().setErrorMsg("Please Select City in Mailing Address Portion on Address Information tab.");
//            return "Please Select City in Mailing Address Portion on Address Information tab.";
//        }
//        if (this.stateForMailAddress.equals("0")) {
//            getCustomerDetail().setErrorMsg("Please Select State in Mailing Address Portion on Address Information tab.");
//            return "Please Select State in Mailing Address Portion on Address Information tab.";
//        }
//        if (this.countryForMailAddress.equals("0")) {
//            getCustomerDetail().setErrorMsg("Please Select Country in Mailing Address Portion on Address Information tab.");
//            return "Please Select Country in Mailing Address Portion on Address Information tab.";
//        }
//        if (!this.mailPostalCode.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher mailPostalCodeCheck = p.matcher(this.mailPostalCode);
//            if (!mailPostalCodeCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Postal Code of Mailing Address Information.");
//                return "Please Enter Numeric Value in Postal Code of Mailing Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurEmpAddressPostalCode() {
//        if (!this.empPostalCode.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher mailPostalCodeCheck = p.matcher(this.empPostalCode);
//            if (!mailPostalCodeCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Postal Code of Employer Address Information.");
//                return "Please Enter Numeric Value in Postal Code of Employer Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurAddressPhonenoCode() {
//        if (!this.perPhoneNo.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher addressPostalCodeCheck = p.matcher(this.perPhoneNo);
//            if (!addressPostalCodeCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Phone No. of Address Information.");
//                return "Please Enter Numeric Value in Phone No. of Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurAddressFaxnoCode() {
//        if (!this.perFaxNo.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher addressFaxCheck = p.matcher(this.perFaxNo);
//            if (!addressFaxCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Fax No. of Address Information.");
//                return "Please Enter Numeric Value in Fax No. of Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurAddressTelexnoCode() {
//        if (!this.pertelexNo.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher pertelexNoCheck = p.matcher(this.pertelexNo);
//            if (!pertelexNoCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Telex No. of Address Information.");
//                return "Please Enter Numeric Value in Telex No. of Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurMailAddressPhoneCode() {
//        if (!this.mailPhoneNo.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher mailPhoneNoCheck = p.matcher(this.mailPhoneNo);
//            if (!mailPhoneNoCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Phone No. of Mailing Address Information.");
//                return "Please Enter Numeric Value in Phone No. of Mailing Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurMailAddressFaxnoCode() {
//        if (!this.mailFax.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher mailFaxCheck = p.matcher(this.mailFax);
//            if (!mailFaxCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Fax No. of Mailing Address Information.");
//                return "Please Enter Numeric Value in Fax No. of Mailing Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurMailAddressTelexnoCode() {
//        if (!this.mailTelexNo.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher mailFaxCheck = p.matcher(this.mailTelexNo);
//            if (!mailFaxCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Telex No. of Mailing Address Information.");
//                return "Please Enter Numeric Value in Telex No. of Mailing Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurEmpAddressPhoneCode() {
//        if (!this.empPhoneNo.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher mailPhoneNoCheck = p.matcher(this.empPhoneNo);
//            if (!mailPhoneNoCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Phone No. of Employer Address Information.");
//                return "Please Enter Numeric Value in Phone No. of Employer Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//
//    public String onblurEmpAddressFaxnoCode() {
//        if (!this.empFax.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher mailFaxCheck = p.matcher(this.empFax);
//            if (!mailFaxCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Fax No. of Employer Address Information.");
//                return "Please Enter Numeric Value in Fax No. of Employer Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//
//    public String onblurEmpAddressTelexnoCode() {
//        if (!this.empTelexNo.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher mailFaxCheck = p.matcher(this.empTelexNo);
//            if (!mailFaxCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Telex No. of Employer Address Information.");
//                return "Please Enter Numeric Value in Telex No. of Employer Address Information.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//
//    public String onblurEmpEmailId() {
//        if (!this.empEmail.equalsIgnoreCase("")) {
//            boolean check = new Validator().validateEmail(this.empEmail);
//            if (!check == true) {
//                getCustomerDetail().setErrorMsg("Please Enter Proper Email ID.");
//                return "Please Enter Proper Email ID.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurMailAddressVillage() {
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        Matcher addressVillCheck = p.matcher(this.mailVillage);
//        if (addressVillCheck.matches()) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Customer Mailing Address Village.");
//            return "Please Enter Non Numeric Value in Customer Mailing Address Village.";
//        } else if (this.mailVillage.contains("0") || this.mailVillage.contains("1") || this.mailVillage.contains("2") || this.mailVillage.contains("3") || this.mailVillage.contains("4") || this.mailVillage.contains("5")
//                || this.mailVillage.contains("6") || this.mailVillage.contains("7") || this.mailVillage.contains("8") || this.mailVillage.contains("9")) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Customer Mailing Address Village.");
//            return "Please Enter Non Numeric Value in Customer Mailing Address Village.";
//        } else {
//            getCustomerDetail().setErrorMsg("");
//            return "true";
//        }
//        getCustomerDetail().setErrorMsg("");
//        return "true";
//    }
//    public String onblurEmpAddressVillage() {
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        Matcher addressVillCheck = p.matcher(this.empVillage);
//        if (addressVillCheck.matches()) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Employer Address Village.");
//            return "Please Enter Non Numeric Value in Employer Address Village.";
//        } else if (this.empVillage.contains("0") || this.empVillage.contains("1") || this.empVillage.contains("2") || this.empVillage.contains("3") || this.empVillage.contains("4") || this.empVillage.contains("5")
//                || this.empVillage.contains("6") || this.empVillage.contains("7") || this.empVillage.contains("8") || this.empVillage.contains("9")) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Employer Address Village.");
//            return "Please Enter Non Numeric Value in Employer Address Village.";
//        } else {
//            getCustomerDetail().setErrorMsg("");
//            return "true";
//        }
//        getCustomerDetail().setErrorMsg("");
//        return "true";
//    }
    public void selectFieldValues() {
        try {
            CBSCustomerMasterDetailTO custTO = masterDelegate.getCustDetailsByCustId(getCustomerDetail().getCustId());
            if (custTO.getPerCountryCode().equalsIgnoreCase("IN")) {
                optionlist = aitr.getListAsPerRequirement("011", custTO.getPerStateCode(), "DIST", "0", "0", "0",ymd.format(new Date()),0);
                districtForPermanentAddressOption = new ArrayList<SelectItem>();
                districtForPermanentAddressOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    districtForPermanentAddressOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            }
            if (custTO.getMailCountryCode().equalsIgnoreCase("IN")) {
                optionlist = aitr.getListAsPerRequirement("011", custTO.getMailStateCode(), "DIST", "0", "0", "0",ymd.format(new Date()),0);
                districtForMailAddressOption = new ArrayList<SelectItem>();
                districtForMailAddressOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    districtForMailAddressOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            }
            if (custTO.getEmpCountryCode().equalsIgnoreCase("IN")) {
                optionlist = aitr.getListAsPerRequirement("011", custTO.getEmpStateCode(), "DIST", "0", "0", "0",ymd.format(new Date()),0);
                districtForEmpAddressOption = new ArrayList<SelectItem>();
                districtForEmpAddressOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    districtForEmpAddressOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            }
            if (custTO.getJuriCountry().equalsIgnoreCase("IN")) {
                optionlist = aitr.getListAsPerRequirement("011", custTO.getJuriState(), "DIST", "0", "0", "0",ymd.format(new Date()),0);
                districtForJuriAddressOption = new ArrayList<SelectItem>();
                districtForJuriAddressOption.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < optionlist.size(); i++) {
                    Vector ele6 = (Vector) optionlist.get(i);
                    districtForJuriAddressOption.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            }
            perOtherPOADisplay = "none";
            mailOtherPOADisplay = "none";
            juriOtherPOADisplay = "none";
            if (custTO.getPerAddressLine1() == null || custTO.getPerAddressLine1().equalsIgnoreCase("")) {
                this.setPerAdd1("");
            } else {
                this.setPerAdd1(custTO.getPerAddressLine1());
            }
            if (custTO.getPerAddressLine2() == null || custTO.getPerAddressLine2().equalsIgnoreCase("")) {
                this.setPerAdd2("");
            } else {
                this.setPerAdd2(custTO.getPerAddressLine2());
            }
            if (custTO.getPerVillage() == null || custTO.getPerVillage().equalsIgnoreCase("")) {
                this.setPerVillage("");
            } else {
                this.setPerVillage(custTO.getPerVillage());
            }
            if (custTO.getPerBlock() == null || custTO.getPerBlock().equalsIgnoreCase("")) {
                this.setPerBlock("");
            } else {
                this.setPerBlock(custTO.getPerBlock());
            }
            if (custTO.getPerCityCode() == null || custTO.getPerCityCode().equalsIgnoreCase("")) {
                this.setCityForPermanentAddress("0");
            } else {
                this.setCityForPermanentAddress(custTO.getPerCityCode());
            }
            if (custTO.getPerCountryCode() == null || custTO.getPerCountryCode().equalsIgnoreCase("")) {
                this.setCountryForPermanentAddress("0");
            } else {
                this.setCountryForPermanentAddress(custTO.getPerCountryCode());
            }
            if (custTO.getPerCountryCode().equalsIgnoreCase("IN")) {
                displayOnPerCntryIN = "";
                displayOnPerCntryNotIN = "none";
                if (custTO.getPerStateCode() == null || custTO.getPerStateCode().equalsIgnoreCase("")) {
                    this.setStateForPermanentAddress("0");
                } else {
                    this.setStateForPermanentAddress(custTO.getPerStateCode());
                }
                this.perDistrict = (custTO.getPerDistrict() == null || custTO.getPerDistrict().equals("0"))
                        ? "" : custTO.getPerDistrict();
            } else {
                displayOnPerCntryIN = "none";
                displayOnPerCntryNotIN = "";
                this.perStateNotIN = (custTO.getPerStateCode() == null || custTO.getPerStateCode().equals(""))
                        ? "" : custTO.getPerStateCode();
                this.perDistrictNotIN = (custTO.getPerDistrict() == null || custTO.getPerDistrict().equals(""))
                        ? "" : custTO.getPerDistrict();
            }
            this.perpostalCode = (custTO.getPerPostalCode() == null || custTO.getPerPostalCode().equals(""))
                    ? "" : custTO.getPerPostalCode();
            if (custTO.getPerPhoneNumber() == null || custTO.getPerPhoneNumber().equalsIgnoreCase("")) {
                this.setPerPhoneNo("");
            } else {
                this.setPerPhoneNo(custTO.getPerPhoneNumber());
            }
            if (custTO.getPerFaxNumber() == null || custTO.getPerFaxNumber().equalsIgnoreCase("")) {
                this.setPerFaxNo("");
            } else {
                this.setPerFaxNo(custTO.getPerFaxNumber());
            }
            if (custTO.getPerEmail() == null || custTO.getPerEmail().equalsIgnoreCase("")) {
                this.setPerEmail("");
            } else {
                this.setPerEmail(custTO.getPerEmail());
            }
            if (custTO.getMailAddressLine1() == null || custTO.getMailAddressLine1().equalsIgnoreCase("")) {
                this.setMailAdd1("");
            } else {
                this.setMailAdd1(custTO.getMailAddressLine1());
            }
            if (custTO.getMailAddressLine2() == null || custTO.getMailAddressLine2().equalsIgnoreCase("")) {
                this.setMailAdd2("");
            } else {
                this.setMailAdd2(custTO.getMailAddressLine2());
            }
            if (custTO.getMailVillage() == null || custTO.getMailVillage().equalsIgnoreCase("")) {
                this.setMailVillage("");
            } else {
                this.setMailVillage(custTO.getMailVillage());
            }
            if (custTO.getMailBlock() == null || custTO.getMailBlock().equalsIgnoreCase("")) {
                this.setMailBlock("");
            } else {
                this.setMailBlock(custTO.getMailBlock());
            }
            if (custTO.getMailCityCode() == null || custTO.getMailCityCode().equalsIgnoreCase("")) {
                this.setCityForMailAddress("");
            } else {
                this.setCityForMailAddress(custTO.getMailCityCode());
            }
            if (custTO.getMailCountryCode() == null || custTO.getMailCountryCode().equalsIgnoreCase("")) {
                this.setCountryForMailAddress("0");
            } else {
                this.setCountryForMailAddress(custTO.getMailCountryCode());
            }

            if (custTO.getMailCountryCode().equalsIgnoreCase("IN")) {
                displayOnMailCntryIN = "";
                displayOnMailCntryNotIN = "none";
                if (custTO.getMailStateCode() == null || custTO.getMailStateCode().equalsIgnoreCase("")) {
                    this.setStateForMailAddress("0");
                } else {
                    this.setStateForMailAddress(custTO.getMailStateCode());
                }
                this.mailDistrict = (custTO.getMailDistrict() == null || custTO.getMailDistrict().equals(""))
                        ? "" : custTO.getMailDistrict();
            } else {
                displayOnMailCntryIN = "none";
                displayOnMailCntryNotIN = "";
                this.mailStateNotIN = (custTO.getMailStateCode() == null || custTO.getMailStateCode().equals(""))
                        ? "" : custTO.getMailStateCode();
                this.mailDistrictNotIN = (custTO.getMailDistrict() == null || custTO.getMailDistrict().equals(""))
                        ? "" : custTO.getMailDistrict();

            }
            this.mailPostalCode = (custTO.getMailPostalCode() == null || custTO.getMailPostalCode().equals(""))
                    ? "" : custTO.getMailPostalCode();
            if (custTO.getMailPhoneNumber() == null || custTO.getMailPhoneNumber().equalsIgnoreCase("")) {
                this.setMailPhoneNo("");
            } else {
                this.setMailPhoneNo(custTO.getMailPhoneNumber());
            }
            if (custTO.getMailFaxNumber() == null || custTO.getMailFaxNumber().equalsIgnoreCase("")) {
                this.setMailFax("");
            } else {
                this.setMailFax(custTO.getMailFaxNumber());
            }
            if (custTO.getMailEmail() == null || custTO.getMailEmail().equalsIgnoreCase("")) {
                this.setMailEmail("");
            } else {
                this.setMailEmail(custTO.getMailEmail());
            }
            if (custTO.getEmployerid() == null || custTO.getEmployerid().equalsIgnoreCase("")) {
                this.setEmpCode("");
            } else {
                this.setEmpCode(custTO.getEmployerid());
            }
            if (custTO.getEmployeeNo() == null || custTO.getEmployeeNo().equalsIgnoreCase("")) {
                this.setEmpNo("");
            } else {
                this.setEmpNo(custTO.getEmployeeNo());
            }
            if (custTO.getEmpAddressLine1() == null || custTO.getEmpAddressLine1().equalsIgnoreCase("")) {
                this.setEmpAdd1("");
            } else {
                this.setEmpAdd1(custTO.getEmpAddressLine1());
            }
            if (custTO.getEmpAddressLine2() == null || custTO.getEmpAddressLine2().equalsIgnoreCase("")) {
                this.setEmpAdd2("");
            } else {
                this.setEmpAdd2(custTO.getEmpAddressLine2());
            }
            if (custTO.getEmpVillage() == null || custTO.getEmpVillage().equalsIgnoreCase("")) {
                this.setEmpVillage("");
            } else {
                this.setEmpVillage(custTO.getEmpVillage());
            }
            if (custTO.getEmpBlock() == null || custTO.getEmpBlock().equalsIgnoreCase("")) {
                this.setEmpTehsil("");
            } else {
                this.setEmpTehsil(custTO.getEmpBlock());
            }
            if (custTO.getEmpCityCode() == null || custTO.getEmpCityCode().equalsIgnoreCase("")) {
                this.setCityForEmpAddress("");
            } else {
                this.setCityForEmpAddress(custTO.getEmpCityCode());
            }

            if (custTO.getEmpCountryCode() == null || custTO.getEmpCountryCode().equalsIgnoreCase("")) {
                this.setCountryForEmpAddress("0");
            } else {
                this.setCountryForEmpAddress(custTO.getEmpCountryCode());
            }


            if (custTO.getEmpCountryCode().equalsIgnoreCase("IN")) {
                displayOnEmpCntryIN = "";
                displayOnEmpCntryNotIN = "none";
                if (custTO.getEmpStateCode() == null || custTO.getEmpStateCode().equalsIgnoreCase("")) {
                    this.setStateForEmpAddress("0");
                } else {
                    this.setStateForEmpAddress(custTO.getEmpStateCode());
                }
                this.empDistrict = (custTO.getEmpDistrict() == null || custTO.getEmpDistrict().equals(""))
                        ? "" : custTO.getEmpDistrict();
            } else {
                displayOnEmpCntryIN = "none";
                displayOnEmpCntryNotIN = "";
                this.empStateNotIN = (custTO.getEmpStateCode() == null || custTO.getEmpStateCode().equals(""))
                        ? "" : custTO.getEmpStateCode();
                this.empDistrictNotIN = (custTO.getEmpDistrict() == null || custTO.getEmpDistrict().equals(""))
                        ? "" : custTO.getEmpDistrict();
            }
            this.empPostalCode = (custTO.getEmpPostalCode() == null || custTO.getEmpPostalCode().equals(""))
                    ? "" : custTO.getEmpPostalCode();
            if (custTO.getEmpPhoneNumber() == null || custTO.getEmpPhoneNumber().equalsIgnoreCase("")) {
                this.setEmpPhoneNo("");
            } else {
                this.setEmpPhoneNo(custTO.getEmpPhoneNumber());
            }
            if (custTO.getEmpTelexNumber() == null || custTO.getEmpTelexNumber().equalsIgnoreCase("")) {
                this.setEmpTelexNo("");
            } else {
                this.setEmpTelexNo(custTO.getEmpTelexNumber());
            }
            if (custTO.getEmpFaxNumber() == null || custTO.getEmpFaxNumber().equalsIgnoreCase("")) {
                this.setEmpFax("");
            } else {
                this.setEmpFax(custTO.getEmpFaxNumber());
            }
            if (custTO.getEmailID() == null || custTO.getEmailID().equalsIgnoreCase("")) {
                this.setEmpEmail("");
            } else {
                this.setEmpEmail(custTO.getEmailID());
            }
            this.juriAdd1 = (custTO.getJuriAdd1() == null || custTO.getJuriAdd1().equalsIgnoreCase(""))
                    ? "" : custTO.getJuriAdd1();
            this.juriAdd2 = (custTO.getJuriAdd2() == null || custTO.getJuriAdd2().equalsIgnoreCase(""))
                    ? "" : custTO.getJuriAdd2();
            this.juriCity = (custTO.getJuriCity() == null || custTO.getJuriCity().equalsIgnoreCase(""))
                    ? "" : custTO.getJuriCity();

            this.juriCountryCode = (custTO.getJuriCountry() == null || custTO.getJuriCountry().equalsIgnoreCase(""))
                    ? "0" : custTO.getJuriCountry();

            if (custTO.getJuriCountry().equalsIgnoreCase("IN")) {
                displayOnJuriCntryIN = "";
                displayOnJuriCntryNotIN = "none";
                this.juriState = (custTO.getJuriState() == null || custTO.getJuriState().equalsIgnoreCase(""))
                        ? "0" : custTO.getJuriState();
                this.juriDistrict = (custTO.getJuriDistrict() == null || custTO.getJuriDistrict().equalsIgnoreCase(""))
                        ? "0" : custTO.getJuriDistrict();
            } else {
                displayOnJuriCntryIN = "none";
                displayOnJuriCntryNotIN = "";
                this.juriStateNotIN = (custTO.getJuriState() == null || custTO.getJuriState().equalsIgnoreCase(""))
                        ? "" : custTO.getJuriState();
                this.juriDistrictNotIN = (custTO.getJuriDistrict() == null || custTO.getJuriDistrict().equalsIgnoreCase(""))
                        ? "" : custTO.getJuriDistrict();
            }
            this.juriPostalCode = (custTO.getJuriPostal() == null || custTO.getJuriPostal().equalsIgnoreCase(""))
                    ? "" : custTO.getJuriPostal();
            this.prefPoaAdd = (custTO.getPoa() == null || custTO.getPoa().equals(""))
                    ? "0" : custTO.getPoa();
            if (prefPoaAdd.equalsIgnoreCase("99")) {
                this.perOtherPOADisplay = "";
                this.perOtherPOA = (custTO.getPerOtherPOA() == null || custTO.getPerOtherPOA().equalsIgnoreCase(""))
                        ? "" : custTO.getPerOtherPOA();
            }
            this.perAddType = (custTO.getPerAddType() == null || custTO.getPerAddType().equals(""))
                    ? "0" : custTO.getPerAddType();
            this.mailPoa = (custTO.getMailPoa() == null || custTO.getMailPoa().equals(""))
                    ? "0" : custTO.getMailPoa();
            if (mailPoa.equalsIgnoreCase("99")) {
                this.mailOtherPOADisplay = "";
                this.mailOtherPOA = (custTO.getMailOtherPOA() == null || custTO.getMailOtherPOA().equalsIgnoreCase(""))
                        ? "" : custTO.getMailOtherPOA();
            }
            this.mailAddType = (custTO.getMailAddType() == null || custTO.getMailAddType().equals(""))
                    ? "0" : custTO.getMailAddType();
            this.juriAddBasedOnFlag = (custTO.getJuriAddBasedOnFlag() == null || custTO.getJuriAddBasedOnFlag().equals(""))
                    ? "0" : custTO.getJuriAddBasedOnFlag();
            this.juriAddType = (custTO.getJuriAddType() == null || custTO.getJuriAddType().equals(""))
                    ? "0" : custTO.getJuriAddType();
            this.juriPoa = (custTO.getJuriPoa() == null || custTO.getJuriPoa().equals(""))
                    ? "0" : custTO.getJuriPoa();
            if (juriPoa.equalsIgnoreCase("99")) {
                this.juriOtherPOADisplay = "";
                this.juriOtherPOA = (custTO.getJuriOtherPOA() == null || custTO.getJuriOtherPOA().equalsIgnoreCase(""))
                        ? "" : custTO.getJuriOtherPOA();
            }
            this.mailAddSameAsPerAdd = (custTO.getPerMailAddSameFlagIndicate() == null)
                    ? false : custTO.getPerMailAddSameFlagIndicate().equalsIgnoreCase("Y") ? true : false;
            //Addition on 27/10/2015
            makeChangeFlagToUnChange();
            if (getCustomerDetail().getFunction().equalsIgnoreCase("V")) {
                CBSCustomerMasterDetailHisTO hisTo = customerMgmtRemote.
                        getCustomerLastChangeDetail(getCustomerDetail().getVerifyCustId());
                if (hisTo != null) {
                    this.perAdd1ChgFlag = this.perAdd1.equalsIgnoreCase(hisTo.getPerAddressLine1() == null ? "" : hisTo.getPerAddressLine1()) ? "color: #000000" : "color: #FF0000";
                    this.perAdd2ChgFlag = this.perAdd2.equalsIgnoreCase(hisTo.getPerAddressLine2() == null ? "" : hisTo.getPerAddressLine2()) ? "color: #000000" : "color: #FF0000";
                    this.perVillageChgFlag = this.perVillage.equalsIgnoreCase(hisTo.getPerVillage() == null ? "" : hisTo.getPerVillage()) ? "color: #000000" : "color: #FF0000";
                    this.perBlockChgFlag = this.perBlock.equalsIgnoreCase(hisTo.getPerBlock() == null ? "" : hisTo.getPerBlock()) ? "color: #000000" : "color: #FF0000";
                    this.perCityChgFlag = this.cityForPermanentAddress.equalsIgnoreCase(hisTo.getPerCityCode() == null ? "" : hisTo.getPerCityCode()) ? "color: #000000" : "color: #FF0000";
                    this.perStateChgFlag = this.stateForPermanentAddress.equalsIgnoreCase(hisTo.getPerStateCode() == null ? "" : hisTo.getPerStateCode()) ? "color: #000000" : "color: #FF0000";
                    this.perCountryChgFlag = this.countryForPermanentAddress.equalsIgnoreCase(hisTo.getPerCountryCode() == null ? "" : hisTo.getPerCountryCode()) ? "color: #000000" : "color: #FF0000";
                    this.perPostalChgFlag = this.perpostalCode.equalsIgnoreCase(hisTo.getPerPostalCode() == null ? "" : hisTo.getPerPostalCode()) ? "color: #000000" : "color: #FF0000";
                    this.perPhoneChgFlag = this.perPhoneNo.equalsIgnoreCase(hisTo.getPerPhoneNumber() == null ? "" : hisTo.getPerPhoneNumber()) ? "color: #000000" : "color: #FF0000";
                    this.perFaxChgFlag = this.perFaxNo.equalsIgnoreCase(hisTo.getPerFaxNumber() == null ? "" : hisTo.getPerFaxNumber()) ? "color: #000000" : "color: #FF0000";
                    this.perMailChgFlag = this.perEmail.equalsIgnoreCase(hisTo.getPerEmail() == null ? "" : hisTo.getPerEmail()) ? "color: #000000" : "color: #FF0000";
                    this.mailAdd1ChgFlag = this.mailAdd1.equalsIgnoreCase(hisTo.getMailAddressLine1() == null ? "" : hisTo.getMailAddressLine1()) ? "color: #000000" : "color: #FF0000";
                    this.mailAdd2ChgFlag = this.mailAdd2.equalsIgnoreCase(hisTo.getMailAddressLine2() == null ? "" : hisTo.getMailAddressLine2()) ? "color: #000000" : "color: #FF0000";
                    this.mailVillageChgFlag = this.mailVillage.equalsIgnoreCase(hisTo.getMailVillage() == null ? "" : hisTo.getMailVillage()) ? "color: #000000" : "color: #FF0000";
                    this.mailBlockChgFlag = this.mailBlock.equalsIgnoreCase(hisTo.getMailBlock() == null ? "" : hisTo.getMailBlock()) ? "color: #000000" : "color: #FF0000";
                    this.mailCityChgFlag = this.cityForMailAddress.equalsIgnoreCase(hisTo.getMailCityCode() == null ? "" : hisTo.getMailCityCode()) ? "color: #000000" : "color: #FF0000";
                    this.mailStateChgFlag = this.stateForMailAddress.equalsIgnoreCase(hisTo.getMailStateCode() == null ? "" : hisTo.getMailStateCode()) ? "color: #000000" : "color: #FF0000";
                    this.mailCountryChgFlag = this.countryForMailAddress.equalsIgnoreCase(hisTo.getMailCountryCode() == null ? "" : hisTo.getMailCountryCode()) ? "color: #000000" : "color: #FF0000";
                    this.mailPostalChgFlag = this.mailPostalCode.equalsIgnoreCase(hisTo.getMailPostalCode() == null ? "" : hisTo.getMailPostalCode()) ? "color: #000000" : "color: #FF0000";
                    this.mailPhoneChgFlag = this.mailPhoneNo.equalsIgnoreCase(hisTo.getMailPhoneNumber() == null ? "" : hisTo.getMailPhoneNumber()) ? "color: #000000" : "color: #FF0000";
                    this.mailFaxChgFlag = this.mailFax.equalsIgnoreCase(hisTo.getMailFaxNumber() == null ? "" : hisTo.getMailFaxNumber()) ? "color: #000000" : "color: #FF0000";
                    this.mailMailChgFlag = this.mailEmail.equalsIgnoreCase(hisTo.getMailEmail() == null ? "" : hisTo.getMailEmail()) ? "color: #000000" : "color: #FF0000";
                    this.empCodeChgFlag = this.empCode.equalsIgnoreCase(hisTo.getEmployerid() == null ? "" : hisTo.getEmployerid()) ? "color: #000000" : "color: #FF0000";
                    this.empNoChgFlag = this.empNo.equalsIgnoreCase(hisTo.getEmployeeNo() == null ? "" : hisTo.getEmployeeNo()) ? "color: #000000" : "color: #FF0000";
                    this.empAdd1ChgFlag = this.empAdd1.equalsIgnoreCase(hisTo.getEmpAddressLine1() == null ? "" : hisTo.getEmpAddressLine1()) ? "color: #000000" : "color: #FF0000";
                    this.empAdd2ChgFlag = this.empAdd2.equalsIgnoreCase(hisTo.getEmpAddressLine2() == null ? "" : hisTo.getEmpAddressLine2()) ? "color: #000000" : "color: #FF0000";
                    this.empVillageChgFlag = this.empVillage.equalsIgnoreCase(hisTo.getEmpVillage() == null ? "" : hisTo.getEmpVillage()) ? "color: #000000" : "color: #FF0000";
                    this.empTehsilChgFlag = this.empTehsil.equalsIgnoreCase(hisTo.getEmpBlock() == null ? "" : hisTo.getEmpBlock()) ? "color: #000000" : "color: #FF0000";
                    this.empCityChgFlag = this.cityForEmpAddress.equalsIgnoreCase(hisTo.getEmpCityCode() == null ? "" : hisTo.getEmpCityCode()) ? "color: #000000" : "color: #FF0000";
                    this.empStateChgFlag = this.stateForEmpAddress.equalsIgnoreCase(hisTo.getEmpStateCode() == null ? "" : hisTo.getEmpStateCode()) ? "color: #000000" : "color: #FF0000";
                    this.empPostalChgFlag = this.empPostalCode.equalsIgnoreCase(hisTo.getEmpPostalCode() == null ? "" : hisTo.getEmpPostalCode()) ? "color: #000000" : "color: #FF0000";
                    this.empCountryChgFlag = this.countryForEmpAddress.equalsIgnoreCase(hisTo.getEmpCountryCode() == null ? "" : hisTo.getEmpCountryCode()) ? "color: #000000" : "color: #FF0000";
                    this.empPhoneChgFlag = this.empPhoneNo.equalsIgnoreCase(hisTo.getEmpPhoneNumber() == null ? "" : hisTo.getEmpPhoneNumber()) ? "color: #000000" : "color: #FF0000";
                    this.empTelexChgFlag = this.empTelexNo.equalsIgnoreCase(hisTo.getEmpTelexNumber() == null ? "" : hisTo.getEmpTelexNumber()) ? "color: #000000" : "color: #FF0000";
                    this.empFaxChgFlag = this.empFax.equalsIgnoreCase(hisTo.getEmpFaxNumber() == null ? "" : hisTo.getEmpFaxNumber()) ? "color: #000000" : "color: #FF0000";
                    this.empEmailChgFlag = this.empEmail.equalsIgnoreCase(hisTo.getEmailID() == null ? "" : hisTo.getEmailID()) ? "color: #000000" : "color: #FF0000";
                    this.juriAdd1ChgFlag = this.juriAdd1.equalsIgnoreCase(hisTo.getJuriAdd1() == null ? "" : hisTo.getJuriAdd1()) ? "color: #000000" : "color: #FF0000";
                    this.juriAdd2ChgFlag = this.juriAdd2.equalsIgnoreCase(hisTo.getJuriAdd2() == null ? "" : hisTo.getJuriAdd2()) ? "color: #000000" : "color: #FF0000";
                    this.juriCityChgFlag = this.juriCity.equalsIgnoreCase(hisTo.getJuriCity() == null ? "" : hisTo.getJuriCity()) ? "color: #000000" : "color: #FF0000";
                    this.juriStateChgFlag = this.juriState.equalsIgnoreCase(hisTo.getJuriState() == null ? "" : hisTo.getJuriState()) ? "color: #000000" : "color: #FF0000";
                    this.juriPostalChgFlag = this.juriPostalCode.equalsIgnoreCase(hisTo.getJuriPostal() == null ? "" : hisTo.getJuriPostal()) ? "color: #000000" : "color: #FF0000";
                    this.juriCountryChgFlag = this.juriCountryCode.equalsIgnoreCase(hisTo.getJuriCountry() == null ? "" : hisTo.getJuriCountry()) ? "color: #000000" : "color: #FF0000";
                    //add on 02/01/2017
                    this.perAddTypeChgFlag = this.perAddType.equalsIgnoreCase(hisTo.getPerAddType() == null ? "" : hisTo.getPerAddType()) ? "color: #000000" : "color: #FF0000";
                    this.mailAddTypeChgFlag = this.mailAddType.equalsIgnoreCase(hisTo.getMailAddType() == null ? "" : hisTo.getMailAddType()) ? "color: #000000" : "color: #FF0000";
                    this.mailPoaChgFlag = this.mailPoa.equalsIgnoreCase(hisTo.getMailPoa() == null ? "" : hisTo.getMailPoa()) ? "color: #000000" : "color: #FF0000";
                    this.juriAddBasedOnFlagChgFlag = this.juriAddBasedOnFlag.equalsIgnoreCase(hisTo.getJuriAddBasedOnFlag() == null ? "" : hisTo.getJuriAddBasedOnFlag()) ? "color: #000000" : "color: #FF0000";
                    this.juriAddTypeChgFlag = this.juriAddType.equalsIgnoreCase(hisTo.getJuriAddType() == null ? "" : hisTo.getJuriAddType()) ? "color: #000000" : "color: #FF0000";
                    this.juriPoaChgFlag = this.juriPoa.equalsIgnoreCase(hisTo.getJuriPoa() == null ? "" : hisTo.getJuriPoa()) ? "color: #000000" : "color: #FF0000";
                    this.prefPoaAddChgFlag = this.prefPoaAdd.equalsIgnoreCase((hisTo.getPoa() == null || hisTo.getPoa().equals("")) ? "" : hisTo.getPoa()) ? "color: #000000" : "color: #FF0000";
                    this.perDistrictChgFlag = this.perDistrict.equalsIgnoreCase((hisTo.getPerDistrict() == null || hisTo.getPerDistrict().equals("")) ? "" : hisTo.getPerDistrict()) ? "color: #000000" : "color: #FF0000";
                    this.mailDistrictChgFlag = this.mailDistrict.equalsIgnoreCase((hisTo.getMailDistrict() == null || hisTo.getMailDistrict().equals("")) ? "" : hisTo.getMailDistrict()) ? "color: #000000" : "color: #FF0000";
                    this.empDistrictChgFlag = this.empDistrict.equalsIgnoreCase((hisTo.getEmpDistrict() == null || hisTo.getEmpDistrict().equals("")) ? "" : hisTo.getEmpDistrict()) ? "color: #000000" : "color: #FF0000";
                    this.perOtherPOAChgFlag = this.perOtherPOA.equalsIgnoreCase((hisTo.getPerOtherPOA() == null || hisTo.getPerOtherPOA().equals("")) ? "" : hisTo.getPerOtherPOA()) ? "color: #000000" : "color: #FF0000";
                    this.mailOtherPOAChgFlag = this.mailOtherPOA.equalsIgnoreCase((hisTo.getMailOtherPOA() == null || hisTo.getMailOtherPOA().equals("")) ? "" : hisTo.getMailOtherPOA()) ? "color: #000000" : "color: #FF0000";
                    this.juriOtherPOAChgFlag = this.juriOtherPOA.equalsIgnoreCase((hisTo.getJuriOtherPOA() == null || hisTo.getJuriOtherPOA().equals("")) ? "" : hisTo.getJuriOtherPOA()) ? "color: #000000" : "color: #FF0000";
                }
            }
            //Addition end here
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setPerAdd1("");
        this.setPerAdd2("");
        this.setPerVillage("");
        this.setPerBlock("");
        this.setCityForPermanentAddress("0");
        this.setStateForPermanentAddress("0");
        this.setCountryForPermanentAddress("0");
        this.setPerpostalCode("");
        this.setPerPhoneNo("");
        this.setPerFaxNo("");
        this.setPerEmail("");
        this.setMailAdd1("");
        this.setMailAdd2("");
        this.setMailVillage("");
        this.setMailBlock("");
        this.setCityForMailAddress("0");
        this.setStateForMailAddress("0");
        this.setCountryForMailAddress("0");
        this.setMailPostalCode("");
        this.setMailPhoneNo("");
        this.setMailFax("");
        this.setMailEmail("");
        this.setEmpCode("");
        this.setEmpNo("");
        this.setEmpAdd1("");
        this.setEmpAdd2("");
        this.setEmpVillage("");
        this.setEmpTehsil("");
        this.setCityForEmpAddress("0");
        this.setStateForEmpAddress("0");
        this.setEmpPostalCode("");
        this.setCountryForEmpAddress("");
        this.setEmpPhoneNo("");
        this.setEmpTelexNo("");
        this.setEmpFax("");
        this.setEmpEmail("");
        this.setJuriAdd1("");
        this.setJuriAdd2("");
        this.setJuriCity("");
        this.setJuriState("");
        this.setJuriCountryCode("0");
        this.setJuriPostalCode("");
        //Addition on 28/10/2015
        makeChangeFlagToUnChange();
        //Addition on 02/012017
        this.setPrefPoaAdd("0");
        this.setPerAddType("0");
        this.setMailAddType("0");
        this.setMailPoa("0");
        this.setJuriAddBasedOnFlag("0");
        this.setJuriAddType("0");
        this.setJuriPoa("0");
        this.setMailAddSameAsPerAdd(false);
        this.setPerDistrict("");
        this.setMailDistrict("0");
        this.setEmpDistrict("0");
        this.setPerOtherPOA("");
        this.setMailOtherPOA("");
        this.setJuriOtherPOA("");
        this.setJuriDistrict("");
        //end
    }

    public String custAddressValidation() {
        String valMsg = validatePermanentPortion();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }

        valMsg = validateMailingPortion();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }

        valMsg = validateEmployerPortion();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }

        valMsg = validateJuriAddressPortion();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }


//        String valMsg = validatePermanentPortion();
//        if (!valMsg.equalsIgnoreCase("true")) {
//            return valMsg;
//        }
//        String s42 = onblurMailAddressPostalCode();
//        if (!s42.equalsIgnoreCase("true")) {
//            return s42;
//        }
//        String s43 = onblurEmpAddressPostalCode();
//        if (!s43.equalsIgnoreCase("true")) {
//            return s43;
//        }
//        String s32 = onblurAddressPhonenoCode();
//        if (!s32.equalsIgnoreCase("true")) {
//            return s32;
//        }
//        String s33 = onblurAddressFaxnoCode();
//        if (!s33.equalsIgnoreCase("true")) {
//            return s33;
//        }
//        String s34 = onblurAddressTelexnoCode();
//        if (!s34.equalsIgnoreCase("true")) {
//            return s34;
//        }
////        String s35 = onblurMailAddressPhoneCode();
////        if (!s35.equalsIgnoreCase("true")) {
////            return s35;
////        }
////        String s36 = onblurMailAddressFaxnoCode();
////        if (!s36.equalsIgnoreCase("true")) {
////            return s36;
////        }
//        String s37 = onblurMailAddressTelexnoCode();
//        if (!s37.equalsIgnoreCase("true")) {
//            return s37;
//        }
//        String s38 = onblurEmpAddressPhoneCode();
//        if (!s38.equalsIgnoreCase("true")) {
//            return s38;
//        }
//        String s39 = onblurEmpAddressFaxnoCode();
//        if (!s39.equalsIgnoreCase("true")) {
//            return s39;
//        }
//        String s40 = onblurEmpAddressTelexnoCode();
//        if (!s40.equalsIgnoreCase("true")) {
//            return s40;
//        }
        return "ok";
    }

    //Add On 28/10/2015
    public void makeChangeFlagToUnChange() {
        this.setPerAdd1ChgFlag("color: #000000");
        this.setPerAdd2ChgFlag("color: #000000");
        this.setPerVillageChgFlag("color: #000000");
        this.setPerBlockChgFlag("color: #000000");
        this.setPerCityChgFlag("color: #000000");
        this.setPerStateChgFlag("color: #000000");
        this.setPerCountryChgFlag("color: #000000");
        this.setPerPostalChgFlag("color: #000000");
        this.setPerPhoneChgFlag("color: #000000");
        this.setPerFaxChgFlag("color: #000000");
        this.setPerMailChgFlag("color: #000000");
        this.setMailAdd1ChgFlag("color: #000000");
        this.setMailAdd2ChgFlag("color: #000000");
        this.setMailVillageChgFlag("color: #000000");
        this.setMailBlockChgFlag("color: #000000");
        this.setMailCityChgFlag("color: #000000");
        this.setMailStateChgFlag("color: #000000");
        this.setMailCountryChgFlag("color: #000000");
        this.setMailPostalChgFlag("color: #000000");
        this.setMailPhoneChgFlag("color: #000000");
        this.setMailFaxChgFlag("color: #000000");
        this.setMailMailChgFlag("color: #000000");
        this.setEmpCodeChgFlag("color: #000000");
        this.setEmpNoChgFlag("color: #000000");
        this.setEmpAdd1ChgFlag("color: #000000");
        this.setEmpAdd2ChgFlag("color: #000000");
        this.setEmpVillageChgFlag("color: #000000");
        this.setEmpTehsilChgFlag("color: #000000");
        this.setEmpCityChgFlag("color: #000000");
        this.setEmpStateChgFlag("color: #000000");
        this.setEmpPostalChgFlag("color: #000000");
        this.setEmpCountryChgFlag("color: #000000");
        this.setEmpPhoneChgFlag("color: #000000");
        this.setEmpTelexChgFlag("color: #000000");
        this.setEmpFaxChgFlag("color: #000000");
        this.setEmpEmailChgFlag("color: #000000");
        this.setJuriAdd1ChgFlag("color: #000000");
        this.setJuriAdd2ChgFlag("color: #000000");
        this.setJuriCityChgFlag("color: #000000");
        this.setJuriStateChgFlag("color: #000000");
        this.setJuriPostalChgFlag("color: #000000");
        this.setJuriCountryChgFlag("color: #000000");
        // added on 02/01/2017
        this.setPerAddTypeChgFlag("color: #000000");
        this.setMailAddTypeChgFlag("color: #000000");
        this.setMailPoaChgFlag("color: #000000");
        this.setJuriAddBasedOnFlagChgFlag("color: #000000");
        this.setJuriAddTypeChgFlag("color: #000000");
        this.setJuriPoaChgFlag("color: #000000");
        this.setPrefPoaAddChgFlag("color: #000000");

        this.setPerDistrictChgFlag("color: #000000");
        this.setMailDistrictChgFlag("color: #000000");
        this.setEmpDistrictChgFlag("color: #000000");
        this.setPerOtherPOAChgFlag("color: #000000");
        this.setMailOtherPOAChgFlag("color: #000000");
        this.setJuriOtherPOAChgFlag("color: #000000");
        //end
    }

    //Getter And Setter
    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpCode() {
        return empCode;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpTehsil() {
        return empTehsil;
    }

    public void setEmpTehsil(String empTehsil) {
        this.empTehsil = empTehsil;
    }

    public boolean isMailAddSameAsPerAdd() {
        return mailAddSameAsPerAdd;
    }

    public void setMailAddSameAsPerAdd(boolean mailAddSameAsPerAdd) {
        this.mailAddSameAsPerAdd = mailAddSameAsPerAdd;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public String getCityForEmpAddress() {
        return cityForEmpAddress;
    }

    public void setCityForEmpAddress(String cityForEmpAddress) {
        this.cityForEmpAddress = cityForEmpAddress;
    }

    public String getCityForMailAddress() {
        return cityForMailAddress;
    }

    public void setCityForMailAddress(String cityForMailAddress) {
        this.cityForMailAddress = cityForMailAddress;
    }

    public String getCityForPermanentAddress() {
        return cityForPermanentAddress;
    }

    public void setCityForPermanentAddress(String cityForPermanentAddress) {
        this.cityForPermanentAddress = cityForPermanentAddress;
    }

    public String getCountryForEmpAddress() {
        return countryForEmpAddress;
    }

    public void setCountryForEmpAddress(String countryForEmpAddress) {
        this.countryForEmpAddress = countryForEmpAddress;
    }

    public List<SelectItem> getCountryForEmpAddressOption() {
        return countryForEmpAddressOption;
    }

    public void setCountryForEmpAddressOption(List<SelectItem> countryForEmpAddressOption) {
        this.countryForEmpAddressOption = countryForEmpAddressOption;
    }

    public String getCountryForMailAddress() {
        return countryForMailAddress;
    }

    public void setCountryForMailAddress(String countryForMailAddress) {
        this.countryForMailAddress = countryForMailAddress;
    }

    public List<SelectItem> getCountryForMailAddressOption() {
        return countryForMailAddressOption;
    }

    public void setCountryForMailAddressOption(List<SelectItem> countryForMailAddressOption) {
        this.countryForMailAddressOption = countryForMailAddressOption;
    }

    public String getCountryForPermanentAddress() {
        return countryForPermanentAddress;
    }

    public void setCountryForPermanentAddress(String countryForPermanentAddress) {
        this.countryForPermanentAddress = countryForPermanentAddress;
    }

    public List<SelectItem> getCountryForPermanentAddressOption() {
        return countryForPermanentAddressOption;
    }

    public void setCountryForPermanentAddressOption(List<SelectItem> countryForPermanentAddressOption) {
        this.countryForPermanentAddressOption = countryForPermanentAddressOption;
    }

    public String getEmpAdd1() {
        return empAdd1;
    }

    public void setEmpAdd1(String empAdd1) {
        this.empAdd1 = empAdd1;
    }

    public String getEmpAdd2() {
        return empAdd2;
    }

    public void setEmpAdd2(String empAdd2) {
        this.empAdd2 = empAdd2;
    }

    public String getEmpBlock() {
        return empBlock;
    }

    public void setEmpBlock(String empBlock) {
        this.empBlock = empBlock;
    }

    public String getEmpFax() {
        return empFax;
    }

    public void setEmpFax(String empFax) {
        this.empFax = empFax;
    }

    public String getEmpPhoneNo() {
        return empPhoneNo;
    }

    public void setEmpPhoneNo(String empPhoneNo) {
        this.empPhoneNo = empPhoneNo;
    }

    public String getEmpPostalCode() {
        return empPostalCode;
    }

    public void setEmpPostalCode(String empPostalCode) {
        this.empPostalCode = empPostalCode;
    }

    public String getEmpTelexNo() {
        return empTelexNo;
    }

    public void setEmpTelexNo(String empTelexNo) {
        this.empTelexNo = empTelexNo;
    }

    public String getEmpVillage() {
        return empVillage;
    }

    public void setEmpVillage(String empVillage) {
        this.empVillage = empVillage;
    }

    public String getMailAdd1() {
        return mailAdd1;
    }

    public void setMailAdd1(String mailAdd1) {
        this.mailAdd1 = mailAdd1;
    }

    public String getMailAdd2() {
        return mailAdd2;
    }

    public void setMailAdd2(String mailAdd2) {
        this.mailAdd2 = mailAdd2;
    }

    public String getMailBlock() {
        return mailBlock;
    }

    public void setMailBlock(String mailBlock) {
        this.mailBlock = mailBlock;
    }

    public String getMailFax() {
        return mailFax;
    }

    public void setMailFax(String mailFax) {
        this.mailFax = mailFax;
    }

    public String getMailPhoneNo() {
        return mailPhoneNo;
    }

    public void setMailPhoneNo(String mailPhoneNo) {
        this.mailPhoneNo = mailPhoneNo;
    }

    public String getMailPostalCode() {
        return mailPostalCode;
    }

    public void setMailPostalCode(String mailPostalCode) {
        this.mailPostalCode = mailPostalCode;
    }

    public String getMailVillage() {
        return mailVillage;
    }

    public void setMailVillage(String mailVillage) {
        this.mailVillage = mailVillage;
    }

    public String getPerAdd1() {
        return perAdd1;
    }

    public void setPerAdd1(String perAdd1) {
        this.perAdd1 = perAdd1;
    }

    public String getPerAdd2() {
        return perAdd2;
    }

    public void setPerAdd2(String perAdd2) {
        this.perAdd2 = perAdd2;
    }

    public String getPerBlock() {
        return perBlock;
    }

    public void setPerBlock(String perBlock) {
        this.perBlock = perBlock;
    }

    public String getPerFaxNo() {
        return perFaxNo;
    }

    public void setPerFaxNo(String perFaxNo) {
        this.perFaxNo = perFaxNo;
    }

    public String getPerPhoneNo() {
        return perPhoneNo;
    }

    public void setPerPhoneNo(String perPhoneNo) {
        this.perPhoneNo = perPhoneNo;
    }

    public String getPerVillage() {
        return perVillage;
    }

    public void setPerVillage(String perVillage) {
        this.perVillage = perVillage;
    }

    public String getPerpostalCode() {
        return perpostalCode;
    }

    public void setPerpostalCode(String perpostalCode) {
        this.perpostalCode = perpostalCode;
    }

    public String getStateForEmpAddress() {
        return stateForEmpAddress;
    }

    public void setStateForEmpAddress(String stateForEmpAddress) {
        this.stateForEmpAddress = stateForEmpAddress;
    }

    public List<SelectItem> getStateForEmpAddressOption() {
        return stateForEmpAddressOption;
    }

    public void setStateForEmpAddressOption(List<SelectItem> stateForEmpAddressOption) {
        this.stateForEmpAddressOption = stateForEmpAddressOption;
    }

    public String getStateForMailAddress() {
        return stateForMailAddress;
    }

    public void setStateForMailAddress(String stateForMailAddress) {
        this.stateForMailAddress = stateForMailAddress;
    }

    public List<SelectItem> getStateForMailAddressOption() {
        return stateForMailAddressOption;
    }

    public void setStateForMailAddressOption(List<SelectItem> stateForMailAddressOption) {
        this.stateForMailAddressOption = stateForMailAddressOption;
    }

    public String getStateForPermanentAddress() {
        return stateForPermanentAddress;
    }

    public void setStateForPermanentAddress(String stateForPermanentAddress) {
        this.stateForPermanentAddress = stateForPermanentAddress;
    }

    public List<SelectItem> getStateForPermanentAddressOption() {
        return stateForPermanentAddressOption;
    }

    public void setStateForPermanentAddressOption(List<SelectItem> stateForPermanentAddressOption) {
        this.stateForPermanentAddressOption = stateForPermanentAddressOption;
    }

    public String getJuriAdd1() {
        return juriAdd1;
    }

    public void setJuriAdd1(String juriAdd1) {
        this.juriAdd1 = juriAdd1;
    }

    public String getJuriAdd2() {
        return juriAdd2;
    }

    public void setJuriAdd2(String juriAdd2) {
        this.juriAdd2 = juriAdd2;
    }

    public String getJuriCity() {
        return juriCity;
    }

    public void setJuriCity(String juriCity) {
        this.juriCity = juriCity;
    }

    public String getJuriState() {
        return juriState;
    }

    public void setJuriState(String juriState) {
        this.juriState = juriState;
    }

    public String getJuriPostalCode() {
        return juriPostalCode;
    }

    public void setJuriPostalCode(String juriPostalCode) {
        this.juriPostalCode = juriPostalCode;
    }

    public String getJuriCountryCode() {
        return juriCountryCode;
    }

    public void setJuriCountryCode(String juriCountryCode) {
        this.juriCountryCode = juriCountryCode;
    }

    public List<SelectItem> getJuriCountryCodeList() {
        return juriCountryCodeList;
    }

    public void setJuriCountryCodeList(List<SelectItem> juriCountryCodeList) {
        this.juriCountryCodeList = juriCountryCodeList;
    }

    public String getPerEmail() {
        return perEmail;
    }

    public void setPerEmail(String perEmail) {
        this.perEmail = perEmail;
    }

    public String getMailEmail() {
        return mailEmail;
    }

    public void setMailEmail(String mailEmail) {
        this.mailEmail = mailEmail;
    }

    public String getPerAdd1ChgFlag() {
        return perAdd1ChgFlag;
    }

    public void setPerAdd1ChgFlag(String perAdd1ChgFlag) {
        this.perAdd1ChgFlag = perAdd1ChgFlag;
    }

    public String getPerAdd2ChgFlag() {
        return perAdd2ChgFlag;
    }

    public void setPerAdd2ChgFlag(String perAdd2ChgFlag) {
        this.perAdd2ChgFlag = perAdd2ChgFlag;
    }

    public String getPerVillageChgFlag() {
        return perVillageChgFlag;
    }

    public void setPerVillageChgFlag(String perVillageChgFlag) {
        this.perVillageChgFlag = perVillageChgFlag;
    }

    public String getPerBlockChgFlag() {
        return perBlockChgFlag;
    }

    public void setPerBlockChgFlag(String perBlockChgFlag) {
        this.perBlockChgFlag = perBlockChgFlag;
    }

    public String getPerCityChgFlag() {
        return perCityChgFlag;
    }

    public void setPerCityChgFlag(String perCityChgFlag) {
        this.perCityChgFlag = perCityChgFlag;
    }

    public String getPerStateChgFlag() {
        return perStateChgFlag;
    }

    public void setPerStateChgFlag(String perStateChgFlag) {
        this.perStateChgFlag = perStateChgFlag;
    }

    public String getPerCountryChgFlag() {
        return perCountryChgFlag;
    }

    public void setPerCountryChgFlag(String perCountryChgFlag) {
        this.perCountryChgFlag = perCountryChgFlag;
    }

    public String getPerPostalChgFlag() {
        return perPostalChgFlag;
    }

    public void setPerPostalChgFlag(String perPostalChgFlag) {
        this.perPostalChgFlag = perPostalChgFlag;
    }

    public String getPerPhoneChgFlag() {
        return perPhoneChgFlag;
    }

    public void setPerPhoneChgFlag(String perPhoneChgFlag) {
        this.perPhoneChgFlag = perPhoneChgFlag;
    }

    public String getPerFaxChgFlag() {
        return perFaxChgFlag;
    }

    public void setPerFaxChgFlag(String perFaxChgFlag) {
        this.perFaxChgFlag = perFaxChgFlag;
    }

    public String getPerMailChgFlag() {
        return perMailChgFlag;
    }

    public void setPerMailChgFlag(String perMailChgFlag) {
        this.perMailChgFlag = perMailChgFlag;
    }

    public String getMailAdd1ChgFlag() {
        return mailAdd1ChgFlag;
    }

    public void setMailAdd1ChgFlag(String mailAdd1ChgFlag) {
        this.mailAdd1ChgFlag = mailAdd1ChgFlag;
    }

    public String getMailAdd2ChgFlag() {
        return mailAdd2ChgFlag;
    }

    public void setMailAdd2ChgFlag(String mailAdd2ChgFlag) {
        this.mailAdd2ChgFlag = mailAdd2ChgFlag;
    }

    public String getMailVillageChgFlag() {
        return mailVillageChgFlag;
    }

    public void setMailVillageChgFlag(String mailVillageChgFlag) {
        this.mailVillageChgFlag = mailVillageChgFlag;
    }

    public String getMailBlockChgFlag() {
        return mailBlockChgFlag;
    }

    public void setMailBlockChgFlag(String mailBlockChgFlag) {
        this.mailBlockChgFlag = mailBlockChgFlag;
    }

    public String getMailCityChgFlag() {
        return mailCityChgFlag;
    }

    public void setMailCityChgFlag(String mailCityChgFlag) {
        this.mailCityChgFlag = mailCityChgFlag;
    }

    public String getMailStateChgFlag() {
        return mailStateChgFlag;
    }

    public void setMailStateChgFlag(String mailStateChgFlag) {
        this.mailStateChgFlag = mailStateChgFlag;
    }

    public String getMailCountryChgFlag() {
        return mailCountryChgFlag;
    }

    public void setMailCountryChgFlag(String mailCountryChgFlag) {
        this.mailCountryChgFlag = mailCountryChgFlag;
    }

    public String getMailPostalChgFlag() {
        return mailPostalChgFlag;
    }

    public void setMailPostalChgFlag(String mailPostalChgFlag) {
        this.mailPostalChgFlag = mailPostalChgFlag;
    }

    public String getMailPhoneChgFlag() {
        return mailPhoneChgFlag;
    }

    public void setMailPhoneChgFlag(String mailPhoneChgFlag) {
        this.mailPhoneChgFlag = mailPhoneChgFlag;
    }

    public String getMailFaxChgFlag() {
        return mailFaxChgFlag;
    }

    public void setMailFaxChgFlag(String mailFaxChgFlag) {
        this.mailFaxChgFlag = mailFaxChgFlag;
    }

    public String getMailMailChgFlag() {
        return mailMailChgFlag;
    }

    public void setMailMailChgFlag(String mailMailChgFlag) {
        this.mailMailChgFlag = mailMailChgFlag;
    }

    public String getEmpCodeChgFlag() {
        return empCodeChgFlag;
    }

    public void setEmpCodeChgFlag(String empCodeChgFlag) {
        this.empCodeChgFlag = empCodeChgFlag;
    }

    public String getEmpNoChgFlag() {
        return empNoChgFlag;
    }

    public void setEmpNoChgFlag(String empNoChgFlag) {
        this.empNoChgFlag = empNoChgFlag;
    }

    public String getEmpAdd1ChgFlag() {
        return empAdd1ChgFlag;
    }

    public void setEmpAdd1ChgFlag(String empAdd1ChgFlag) {
        this.empAdd1ChgFlag = empAdd1ChgFlag;
    }

    public String getEmpAdd2ChgFlag() {
        return empAdd2ChgFlag;
    }

    public void setEmpAdd2ChgFlag(String empAdd2ChgFlag) {
        this.empAdd2ChgFlag = empAdd2ChgFlag;
    }

    public String getEmpVillageChgFlag() {
        return empVillageChgFlag;
    }

    public void setEmpVillageChgFlag(String empVillageChgFlag) {
        this.empVillageChgFlag = empVillageChgFlag;
    }

    public String getEmpTehsilChgFlag() {
        return empTehsilChgFlag;
    }

    public void setEmpTehsilChgFlag(String empTehsilChgFlag) {
        this.empTehsilChgFlag = empTehsilChgFlag;
    }

    public String getEmpCityChgFlag() {
        return empCityChgFlag;
    }

    public void setEmpCityChgFlag(String empCityChgFlag) {
        this.empCityChgFlag = empCityChgFlag;
    }

    public String getEmpStateChgFlag() {
        return empStateChgFlag;
    }

    public void setEmpStateChgFlag(String empStateChgFlag) {
        this.empStateChgFlag = empStateChgFlag;
    }

    public String getEmpPostalChgFlag() {
        return empPostalChgFlag;
    }

    public void setEmpPostalChgFlag(String empPostalChgFlag) {
        this.empPostalChgFlag = empPostalChgFlag;
    }

    public String getEmpCountryChgFlag() {
        return empCountryChgFlag;
    }

    public void setEmpCountryChgFlag(String empCountryChgFlag) {
        this.empCountryChgFlag = empCountryChgFlag;
    }

    public String getEmpPhoneChgFlag() {
        return empPhoneChgFlag;
    }

    public void setEmpPhoneChgFlag(String empPhoneChgFlag) {
        this.empPhoneChgFlag = empPhoneChgFlag;
    }

    public String getEmpTelexChgFlag() {
        return empTelexChgFlag;
    }

    public void setEmpTelexChgFlag(String empTelexChgFlag) {
        this.empTelexChgFlag = empTelexChgFlag;
    }

    public String getEmpFaxChgFlag() {
        return empFaxChgFlag;
    }

    public void setEmpFaxChgFlag(String empFaxChgFlag) {
        this.empFaxChgFlag = empFaxChgFlag;
    }

    public String getEmpEmailChgFlag() {
        return empEmailChgFlag;
    }

    public void setEmpEmailChgFlag(String empEmailChgFlag) {
        this.empEmailChgFlag = empEmailChgFlag;
    }

    public String getJuriAdd1ChgFlag() {
        return juriAdd1ChgFlag;
    }

    public void setJuriAdd1ChgFlag(String juriAdd1ChgFlag) {
        this.juriAdd1ChgFlag = juriAdd1ChgFlag;
    }

    public String getJuriAdd2ChgFlag() {
        return juriAdd2ChgFlag;
    }

    public void setJuriAdd2ChgFlag(String juriAdd2ChgFlag) {
        this.juriAdd2ChgFlag = juriAdd2ChgFlag;
    }

    public String getJuriCityChgFlag() {
        return juriCityChgFlag;
    }

    public void setJuriCityChgFlag(String juriCityChgFlag) {
        this.juriCityChgFlag = juriCityChgFlag;
    }

    public String getJuriStateChgFlag() {
        return juriStateChgFlag;
    }

    public void setJuriStateChgFlag(String juriStateChgFlag) {
        this.juriStateChgFlag = juriStateChgFlag;
    }

    public String getJuriPostalChgFlag() {
        return juriPostalChgFlag;
    }

    public void setJuriPostalChgFlag(String juriPostalChgFlag) {
        this.juriPostalChgFlag = juriPostalChgFlag;
    }

    public String getJuriCountryChgFlag() {
        return juriCountryChgFlag;
    }

    public void setJuriCountryChgFlag(String juriCountryChgFlag) {
        this.juriCountryChgFlag = juriCountryChgFlag;
    }

    public List<SelectItem> getPerAddTypeList() {
        return perAddTypeList;
    }

    public void setPerAddTypeList(List<SelectItem> perAddTypeList) {
        this.perAddTypeList = perAddTypeList;
    }

    public String getPerAddType() {
        return perAddType;
    }

    public void setPerAddType(String perAddType) {
        this.perAddType = perAddType;
    }

    public List<SelectItem> getMailAddTypeList() {
        return mailAddTypeList;
    }

    public void setMailAddTypeList(List<SelectItem> mailAddTypeList) {
        this.mailAddTypeList = mailAddTypeList;
    }

    public String getMailAddType() {
        return mailAddType;
    }

    public void setMailAddType(String mailAddType) {
        this.mailAddType = mailAddType;
    }

    public List<SelectItem> getMailPoaList() {
        return mailPoaList;
    }

    public void setMailPoaList(List<SelectItem> mailPoaList) {
        this.mailPoaList = mailPoaList;
    }

    public String getMailPoa() {
        return mailPoa;
    }

    public void setMailPoa(String mailPoa) {
        this.mailPoa = mailPoa;
    }

    public String getJuriAddBasedOnFlag() {
        return juriAddBasedOnFlag;
    }

    public void setJuriAddBasedOnFlag(String juriAddBasedOnFlag) {
        this.juriAddBasedOnFlag = juriAddBasedOnFlag;
    }

    public List<SelectItem> getJuriAddBasedList() {
        return juriAddBasedList;
    }

    public void setJuriAddBasedList(List<SelectItem> juriAddBasedList) {
        this.juriAddBasedList = juriAddBasedList;
    }

    public List<SelectItem> getJuriAddTypeList() {
        return juriAddTypeList;
    }

    public void setJuriAddTypeList(List<SelectItem> juriAddTypeList) {
        this.juriAddTypeList = juriAddTypeList;
    }

    public String getJuriAddType() {
        return juriAddType;
    }

    public void setJuriAddType(String juriAddType) {
        this.juriAddType = juriAddType;
    }

    public List<SelectItem> getJuriPoaList() {
        return juriPoaList;
    }

    public void setJuriPoaList(List<SelectItem> juriPoaList) {
        this.juriPoaList = juriPoaList;
    }

    public String getJuriPoa() {
        return juriPoa;
    }

    public void setJuriPoa(String juriPoa) {
        this.juriPoa = juriPoa;
    }

    public String getPerAddTypeChgFlag() {
        return perAddTypeChgFlag;
    }

    public void setPerAddTypeChgFlag(String perAddTypeChgFlag) {
        this.perAddTypeChgFlag = perAddTypeChgFlag;
    }

    public String getMailAddTypeChgFlag() {
        return mailAddTypeChgFlag;
    }

    public void setMailAddTypeChgFlag(String mailAddTypeChgFlag) {
        this.mailAddTypeChgFlag = mailAddTypeChgFlag;
    }

    public String getMailPoaChgFlag() {
        return mailPoaChgFlag;
    }

    public void setMailPoaChgFlag(String mailPoaChgFlag) {
        this.mailPoaChgFlag = mailPoaChgFlag;
    }

    public String getJuriAddBasedOnFlagChgFlag() {
        return juriAddBasedOnFlagChgFlag;
    }

    public void setJuriAddBasedOnFlagChgFlag(String juriAddBasedOnFlagChgFlag) {
        this.juriAddBasedOnFlagChgFlag = juriAddBasedOnFlagChgFlag;
    }

    public String getJuriAddTypeChgFlag() {
        return juriAddTypeChgFlag;
    }

    public void setJuriAddTypeChgFlag(String juriAddTypeChgFlag) {
        this.juriAddTypeChgFlag = juriAddTypeChgFlag;
    }

    public String getJuriPoaChgFlag() {
        return juriPoaChgFlag;
    }

    public void setJuriPoaChgFlag(String juriPoaChgFlag) {
        this.juriPoaChgFlag = juriPoaChgFlag;
    }

    public String getPrefPoaAddChgFlag() {
        return prefPoaAddChgFlag;
    }

    public void setPrefPoaAddChgFlag(String prefPoaAddChgFlag) {
        this.prefPoaAddChgFlag = prefPoaAddChgFlag;
    }

    public String getPrefPoaAdd() {
        return prefPoaAdd;
    }

    public void setPrefPoaAdd(String prefPoaAdd) {
        this.prefPoaAdd = prefPoaAdd;
    }

    public List<SelectItem> getPrefPoaAddList() {
        return prefPoaAddList;
    }

    public void setPrefPoaAddList(List<SelectItem> prefPoaAddList) {
        this.prefPoaAddList = prefPoaAddList;
    }

    public String getOthrPOADisplay() {
        return othrPOADisplay;
    }

    public void setOthrPOADisplay(String othrPOADisplay) {
        this.othrPOADisplay = othrPOADisplay;
    }

    public String getOthrPOA() {
        return othrPOA;
    }

    public void setOthrPOA(String othrPOA) {
        this.othrPOA = othrPOA;
    }

    public String getOthrPOAChgFlag() {
        return othrPOAChgFlag;
    }

    public void setOthrPOAChgFlag(String othrPOAChgFlag) {
        this.othrPOAChgFlag = othrPOAChgFlag;
    }

    public List<SelectItem> getDistrictForPermanentAddressOption() {
        return districtForPermanentAddressOption;
    }

    public void setDistrictForPermanentAddressOption(List<SelectItem> districtForPermanentAddressOption) {
        this.districtForPermanentAddressOption = districtForPermanentAddressOption;
    }

    public List<SelectItem> getDistrictForMailAddressOption() {
        return districtForMailAddressOption;
    }

    public void setDistrictForMailAddressOption(List<SelectItem> districtForMailAddressOption) {
        this.districtForMailAddressOption = districtForMailAddressOption;
    }

    public List<SelectItem> getDistrictForEmpAddressOption() {
        return districtForEmpAddressOption;
    }

    public void setDistrictForEmpAddressOption(List<SelectItem> districtForEmpAddressOption) {
        this.districtForEmpAddressOption = districtForEmpAddressOption;
    }

    public String getPerDistrict() {
        return perDistrict;
    }

    public void setPerDistrict(String perDistrict) {
        this.perDistrict = perDistrict;
    }

    public String getMailDistrict() {
        return mailDistrict;
    }

    public void setMailDistrict(String mailDistrict) {
        this.mailDistrict = mailDistrict;
    }

    public String getEmpDistrict() {
        return empDistrict;
    }

    public void setEmpDistrict(String empDistrict) {
        this.empDistrict = empDistrict;
    }

    public String getPerDistrictChgFlag() {
        return perDistrictChgFlag;
    }

    public void setPerDistrictChgFlag(String perDistrictChgFlag) {
        this.perDistrictChgFlag = perDistrictChgFlag;
    }

    public String getMailDistrictChgFlag() {
        return mailDistrictChgFlag;
    }

    public void setMailDistrictChgFlag(String mailDistrictChgFlag) {
        this.mailDistrictChgFlag = mailDistrictChgFlag;
    }

    public String getEmpDistrictChgFlag() {
        return empDistrictChgFlag;
    }

    public void setEmpDistrictChgFlag(String empDistrictChgFlag) {
        this.empDistrictChgFlag = empDistrictChgFlag;
    }

    public String getPerOtherPOA() {
        return perOtherPOA;
    }

    public void setPerOtherPOA(String perOtherPOA) {
        this.perOtherPOA = perOtherPOA;
    }

    public String getMailOtherPOA() {
        return mailOtherPOA;
    }

    public void setMailOtherPOA(String mailOtherPOA) {
        this.mailOtherPOA = mailOtherPOA;
    }

    public String getPerOtherPOAChgFlag() {
        return perOtherPOAChgFlag;
    }

    public void setPerOtherPOAChgFlag(String perOtherPOAChgFlag) {
        this.perOtherPOAChgFlag = perOtherPOAChgFlag;
    }

    public String getMailOtherPOAChgFlag() {
        return mailOtherPOAChgFlag;
    }

    public void setMailOtherPOAChgFlag(String mailOtherPOAChgFlag) {
        this.mailOtherPOAChgFlag = mailOtherPOAChgFlag;
    }

    public String getPerOtherPOADisplay() {
        return perOtherPOADisplay;
    }

    public void setPerOtherPOADisplay(String perOtherPOADisplay) {
        this.perOtherPOADisplay = perOtherPOADisplay;
    }

    public String getMailOtherPOADisplay() {
        return mailOtherPOADisplay;
    }

    public void setMailOtherPOADisplay(String mailOtherPOADisplay) {
        this.mailOtherPOADisplay = mailOtherPOADisplay;
    }

    public String getJuriOtherPOA() {
        return juriOtherPOA;
    }

    public void setJuriOtherPOA(String juriOtherPOA) {
        this.juriOtherPOA = juriOtherPOA;
    }

    public String getJuriOtherPOAChgFlag() {
        return juriOtherPOAChgFlag;
    }

    public void setJuriOtherPOAChgFlag(String juriOtherPOAChgFlag) {
        this.juriOtherPOAChgFlag = juriOtherPOAChgFlag;
    }

    public String getJuriOtherPOADisplay() {
        return juriOtherPOADisplay;
    }

    public void setJuriOtherPOADisplay(String juriOtherPOADisplay) {
        this.juriOtherPOADisplay = juriOtherPOADisplay;
    }

    public List<SelectItem> getPerPostalCodeList() {
        return perPostalCodeList;
    }

    public void setPerPostalCodeList(List<SelectItem> perPostalCodeList) {
        this.perPostalCodeList = perPostalCodeList;
    }

    public List<SelectItem> getMailPostalCodeList() {
        return mailPostalCodeList;
    }

    public void setMailPostalCodeList(List<SelectItem> mailPostalCodeList) {
        this.mailPostalCodeList = mailPostalCodeList;
    }

    public List<SelectItem> getJuriPostalCodeList() {
        return juriPostalCodeList;
    }

    public void setJuriPostalCodeList(List<SelectItem> juriPostalCodeList) {
        this.juriPostalCodeList = juriPostalCodeList;
    }

    public List<SelectItem> getEmpPostalCodeList() {
        return empPostalCodeList;
    }

    public void setEmpPostalCodeList(List<SelectItem> empPostalCodeList) {
        this.empPostalCodeList = empPostalCodeList;
    }

    public List<SelectItem> getDistrictForJuriAddressOption() {
        return districtForJuriAddressOption;
    }

    public void setDistrictForJuriAddressOption(List<SelectItem> districtForJuriAddressOption) {
        this.districtForJuriAddressOption = districtForJuriAddressOption;
    }

    public String getJuriDistrict() {
        return juriDistrict;
    }

    public void setJuriDistrict(String juriDistrict) {
        this.juriDistrict = juriDistrict;
    }

    public String getDisplayOnPerCntryIN() {
        return displayOnPerCntryIN;
    }

    public void setDisplayOnPerCntryIN(String displayOnPerCntryIN) {
        this.displayOnPerCntryIN = displayOnPerCntryIN;
    }

    public String getDisplayOnMailCntryIN() {
        return displayOnMailCntryIN;
    }

    public void setDisplayOnMailCntryIN(String displayOnMailCntryIN) {
        this.displayOnMailCntryIN = displayOnMailCntryIN;
    }

    public String getDisplayOnEmpCntryIN() {
        return displayOnEmpCntryIN;
    }

    public void setDisplayOnEmpCntryIN(String displayOnEmpCntryIN) {
        this.displayOnEmpCntryIN = displayOnEmpCntryIN;
    }

    public String getDisplayOnJuriCntryIN() {
        return displayOnJuriCntryIN;
    }

    public void setDisplayOnJuriCntryIN(String displayOnJuriCntryIN) {
        this.displayOnJuriCntryIN = displayOnJuriCntryIN;
    }

    public String getDisplayOnPerCntryNotIN() {
        return displayOnPerCntryNotIN;
    }

    public void setDisplayOnPerCntryNotIN(String displayOnPerCntryNotIN) {
        this.displayOnPerCntryNotIN = displayOnPerCntryNotIN;
    }

    public String getDisplayOnMailCntryNotIN() {
        return displayOnMailCntryNotIN;
    }

    public void setDisplayOnMailCntryNotIN(String displayOnMailCntryNotIN) {
        this.displayOnMailCntryNotIN = displayOnMailCntryNotIN;
    }

    public String getDisplayOnEmpCntryNotIN() {
        return displayOnEmpCntryNotIN;
    }

    public void setDisplayOnEmpCntryNotIN(String displayOnEmpCntryNotIN) {
        this.displayOnEmpCntryNotIN = displayOnEmpCntryNotIN;
    }

    public String getDisplayOnJuriCntryNotIN() {
        return displayOnJuriCntryNotIN;
    }

    public void setDisplayOnJuriCntryNotIN(String displayOnJuriCntryNotIN) {
        this.displayOnJuriCntryNotIN = displayOnJuriCntryNotIN;
    }

    public String getPerStateNotIN() {
        return perStateNotIN;
    }

    public void setPerStateNotIN(String perStateNotIN) {
        this.perStateNotIN = perStateNotIN;
    }

    public String getPerDistrictNotIN() {
        return perDistrictNotIN;
    }

    public void setPerDistrictNotIN(String perDistrictNotIN) {
        this.perDistrictNotIN = perDistrictNotIN;
    }

    public String getMailStateNotIN() {
        return mailStateNotIN;
    }

    public void setMailStateNotIN(String mailStateNotIN) {
        this.mailStateNotIN = mailStateNotIN;
    }

    public String getMailDistrictNotIN() {
        return mailDistrictNotIN;
    }

    public void setMailDistrictNotIN(String mailDistrictNotIN) {
        this.mailDistrictNotIN = mailDistrictNotIN;
    }

    public String getJuriDistrictNotIN() {
        return juriDistrictNotIN;
    }

    public void setJuriDistrictNotIN(String juriDistrictNotIN) {
        this.juriDistrictNotIN = juriDistrictNotIN;
    }

    public String getJuriStateNotIN() {
        return juriStateNotIN;
    }

    public void setJuriStateNotIN(String juriStateNotIN) {
        this.juriStateNotIN = juriStateNotIN;
    }

    public String getEmpDistrictNotIN() {
        return empDistrictNotIN;
    }

    public void setEmpDistrictNotIN(String empDistrictNotIN) {
        this.empDistrictNotIN = empDistrictNotIN;
    }

    public String getEmpStateNotIN() {
        return empStateNotIN;
    }

    public void setEmpStateNotIN(String empStateNotIN) {
        this.empStateNotIN = empStateNotIN;
    }
}
