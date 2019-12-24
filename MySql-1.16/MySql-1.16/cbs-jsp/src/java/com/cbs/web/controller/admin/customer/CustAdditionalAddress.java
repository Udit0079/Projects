/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CbsCustAdditionalAddressDetailsTo;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.facade.ckycr.CkycrViewMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class CustAdditionalAddress {

    CustomerDetail customerDetail;
    private String addressType;
    private String localAddressLine1;
    private String localAddressLine2;
    private String localAddressLine3;
    private String localAddressCityVillage;
    private String localAddressDistrict;
    private String districtNotIN;
    private String localAddressPINCode;
    private String localAddressState;
    private String stateNotIN;
    private String localAddressCountry;
    private String proofofAdd;
    private String resiTelSTDCode;
    private String resiTelNo;
    private String officeTelSTDCode;
    private String officeTelNo;
    private String mobISDCode;
    private String mobileNo;
    private String faxSTDCode;
    private String faxNo;
    private String emailID;
    private List<SelectItem> addressTypeList;
    private List<SelectItem> localAddressStateList;
    private List<SelectItem> localAddressCountryList;
    private List<SelectItem> proofofAddList;
    private String displayOnCntryIN = "";
    private String displayOnCntryNotIN = "none";
    private List<CbsCustAdditionalAddressDetailsTo> addressDetailsList;
    CustomerMasterDelegate masterDelegate;
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    private CkycrViewMgmtFacadeRemote ckycrRemote = null;

    public CustAdditionalAddress() {
        initializeForm();
    }

    private void initializeForm() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");
            ckycrRemote = (CkycrViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrViewMgmtFacade");
            List<CbsRefRecTypeTO> optionList;
            optionList = masterDelegate.getFunctionValues("002");
            this.setLocalAddressStateList(new ArrayList<SelectItem>());
            localAddressStateList.add(new SelectItem("0", "Select"));
            for (CbsRefRecTypeTO recTypeTO : optionList) {
                localAddressStateList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            optionList = masterDelegate.getFunctionValues("354");
            this.setAddressTypeList(new ArrayList<SelectItem>());
            addressTypeList.add(new SelectItem("0", "Select"));
            for (CbsRefRecTypeTO recTypeTO : optionList) {
                addressTypeList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            this.addressDetailsList = new ArrayList<CbsCustAdditionalAddressDetailsTo>();
            localAddressCountryList = new ArrayList<SelectItem>();
            localAddressCountryList.add(new SelectItem("0", "--Select--"));
            optionList = masterDelegate.getFunctionValues("302");
            for (CbsRefRecTypeTO recTypeTO : optionList) {
                localAddressCountryList.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            if (!optionList.isEmpty()) {
                localAddressCountry = "IN";
            }
            mobISDCode = "91";
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public List<String> getDistrictList(Object arg) {
        List optionList;
        String prefix = (String) arg;
        ArrayList<String> result = new ArrayList<String>();
        try {
            optionList = masterDelegate.getDistrictListLike(this.localAddressState, prefix, "S");
            if (!(prefix == null) || (prefix.length() == 0)) {
                for (int i = 0; i < optionList.size(); i++) {
                    Vector ele6 = (Vector) optionList.get(i);
                    result.add(ele6.get(0).toString());
                }
            }
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public void onAddAdditionalAddress() {
        CbsCustAdditionalAddressDetailsTo addtionalAddTo = new CbsCustAdditionalAddressDetailsTo();
        Pattern onlyNumeric = Pattern.compile("[0-9]*");
        Pattern onlyAlphabetWitSpace = Pattern.compile("[A-Za-z ]*");
        getCustomerDetail().setErrorMsg("");
        try {
            if (this.addressType == null || this.addressType.equalsIgnoreCase("0")) {
                getCustomerDetail().setErrorMsg("Please Select Address Type in Additional Address  Details tab");
                return;
            }
            for (CbsCustAdditionalAddressDetailsTo tempObj : addressDetailsList) {
                if (tempObj.getAddressType().equalsIgnoreCase(this.addressType)) {
                    customerDetail.setErrorMsg("Duplicate Entry Not Allowed. !");
                    return;
                }
            }
            if (this.localAddressLine1 == null || this.localAddressLine1.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please Fill Address Line 1 in Additional Address  Details tab");
                return;
            }
            if (this.localAddressCityVillage == null || this.localAddressCityVillage.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please Fill City/Town/Village in Additional Address  Details tab");
                return;
            }
            if (this.localAddressCountry == null || this.localAddressCountry.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please Select Country in Additional Address  Details tab");
                return;
            }
            if (this.localAddressState == null || this.localAddressState.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please Select State in Additional Address  Details tab");
                return;
            }
            if (this.localAddressCountry.equals("IN")) {
                if (this.localAddressState == null || this.localAddressState.equals("0")) {
                    getCustomerDetail().setErrorMsg("Please select State in Permanent Address Portion on Address Information tab");
                    return;
                }
                if (this.localAddressDistrict == null || this.localAddressDistrict.equals("")) {
                    getCustomerDetail().setErrorMsg("Please select District in Permanent Address Portion on Address Information tab");
                    return;
                }
                List option = masterDelegate.getDistrictListLike(this.localAddressState, this.localAddressDistrict, "V");
                if (option.size() <= 0) {
                    getCustomerDetail().setErrorMsg("Please select proper District in Permanent Address Portion on Address Information tab");
                    return;
                } else if (!this.localAddressDistrict.equalsIgnoreCase(((Vector) option.get(0)).get(0).toString())) {
                    getCustomerDetail().setErrorMsg("Please select proper District in Permanent Address Portion on Address Information tab");
                    return;
                }
            } else {
                if ((this.stateNotIN == null || this.stateNotIN.equals(""))) {
                    getCustomerDetail().setErrorMsg("Please fill State in Permanent Address Portion on Address Information tab");
                    return;
                } else {
                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.stateNotIN);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper State in Permanent Address Portion on Address Information tab");
                        return;
                    }
                }
                if ((this.districtNotIN == null || this.districtNotIN.equals(""))) {
                    getCustomerDetail().setErrorMsg("Please fill District in Permanent Address Portion on Address Information tab");
                    return;
                } else {

                    Matcher matcher = onlyAlphabetWitSpace.matcher(this.districtNotIN);
                    if (!matcher.matches()) {
                        getCustomerDetail().setErrorMsg("Please fill Proper District in Permanent Address Portion on Address Information tab");
                        return;
                    }
                }
            }
            if (this.localAddressPINCode == null || this.localAddressPINCode.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please fill Postal Code in Permanent Address Portion on Address Information tab");
                return;
            } else {
                Matcher matcher = onlyNumeric.matcher(this.localAddressPINCode);
                if (!matcher.matches()) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Permanent Address Portion on Address Information tab");
                    return;
                }
                if (this.localAddressPINCode.trim().length() != 6) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 6 digit Postal/Zip Code in Permanent Address Portion on Address Information tab");
                    return;
                }
                if (!ckycrRemote.isValidPostalCode(localAddressPINCode, localAddressState)) {
                    getCustomerDetail().setErrorMsg("Please fill Valid Postal/Zip Code in Permanent Address Portion on Address Information tab");
                    return;
                }
            }
            if (this.proofofAdd == null || this.proofofAdd.equalsIgnoreCase("0") || this.proofofAdd.equalsIgnoreCase("")) {
                getCustomerDetail().setErrorMsg("Please Select Proof Of Address in Additional Address  Details tab");
                return;
            }
            if (!(this.resiTelSTDCode == null || this.resiTelSTDCode.equalsIgnoreCase(""))) {
                Matcher matcher = onlyNumeric.matcher(this.resiTelSTDCode);
                if (!(matcher.matches() || this.resiTelSTDCode.trim().length() <= 4)) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 4 Digit Resident STD Code in Additional Address  Details tab");
                    return;
                }
                matcher = onlyNumeric.matcher(this.resiTelNo);
                if (!matcher.matches() || this.resiTelNo.trim().length() != 10) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 10 Digit Resident Tel. no. in Additional Address  Details tab");
                    return;
                }
            }
            if (!(this.officeTelSTDCode == null || this.officeTelSTDCode.equalsIgnoreCase(""))) {
                Matcher matcher = onlyNumeric.matcher(this.officeTelSTDCode);
                if (!(matcher.matches() || this.officeTelSTDCode.trim().length() <= 4)) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 4 Digit Office STD Code in Additional Address  Details tab");
                    return;
                }
                matcher = onlyNumeric.matcher(this.officeTelNo);
                if (!matcher.matches() || this.officeTelNo.trim().length() != 10) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 10 Digit Office Tel. no. in Additional Address  Details tab");
                    return;
                }
            }
            if (!(this.faxSTDCode == null || this.faxSTDCode.equalsIgnoreCase(""))) {
                Matcher matcher = onlyNumeric.matcher(this.faxSTDCode);
                if (!(matcher.matches() || this.faxSTDCode.trim().length() <= 4)) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 4 Digit FAX STD Code in Additional Address  Details tab");
                    return;
                }
                matcher = onlyNumeric.matcher(this.faxNo);
                if (!matcher.matches() || this.faxNo.trim().length() != 10) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 10 Digit FAX no. in Additional Address  Details tab");
                    return;
                }
            }

            if (!(this.mobileNo == null || this.mobileNo.equalsIgnoreCase(""))) {
                Matcher matcher = onlyNumeric.matcher(this.mobileNo);
                if (!matcher.matches() || this.mobileNo.trim().length() != 10) {
                    getCustomerDetail().setErrorMsg("Please fill Proper 10 Digit Mobile no. in Additional Address  Details tab");
                    return;
                }
            }

            addtionalAddTo.setAddressTypeDesc(masterDelegate.getRecRefDiscription("354", this.addressType));
            addtionalAddTo.setAddressType(this.addressType == null || this.addressType.equalsIgnoreCase("0") ? "" : this.addressType);
            addtionalAddTo.setLocalAddressLine1(this.localAddressLine1 == null || this.localAddressLine1.equalsIgnoreCase("") ? "" : this.localAddressLine1);
            addtionalAddTo.setLocalAddressLine2(this.localAddressLine2 == null || this.localAddressLine2.equalsIgnoreCase("") ? "" : this.localAddressLine2);
            addtionalAddTo.setLocalAddressLine3(this.localAddressLine3 == null || this.localAddressLine3.equalsIgnoreCase("") ? "" : this.localAddressLine3);
            addtionalAddTo.setLocalAddressCityVillage(this.localAddressCityVillage == null || this.localAddressCityVillage.equalsIgnoreCase("") ? "" : this.localAddressCityVillage);
            addtionalAddTo.setLocalAddressDistrict(this.localAddressDistrict == null || this.localAddressDistrict.equalsIgnoreCase("") ? "" : this.localAddressDistrict);
            addtionalAddTo.setLocalAddressPINCode(this.localAddressPINCode == null || this.localAddressPINCode.equalsIgnoreCase("") ? "" : this.localAddressPINCode);

            addtionalAddTo.setLocalAddressStateDesc(masterDelegate.getRecRefDiscription("002", this.localAddressState));

            addtionalAddTo.setLocalAddressState(this.localAddressState == null || this.localAddressState.equalsIgnoreCase("") ? "" : this.localAddressState);

            addtionalAddTo.setLocalAddressCountryDesc(masterDelegate.getRecRefDiscription("302", this.localAddressCountry));

            addtionalAddTo.setLocalAddressCountry(this.localAddressCountry == null || this.localAddressCountry.equalsIgnoreCase("") ? "" : this.localAddressCountry);
            addtionalAddTo.setProofofAdd(this.proofofAdd == null || this.proofofAdd.equalsIgnoreCase("0") ? "" : this.proofofAdd);
            addtionalAddTo.setProofofAddDesc(masterDelegate.getRecRefDiscription("312", this.proofofAdd));
            addtionalAddTo.setResiTelSTDCode(this.resiTelSTDCode == null || this.resiTelSTDCode.equalsIgnoreCase("") ? "" : this.resiTelSTDCode);
            addtionalAddTo.setResiTelNo(this.resiTelNo == null || this.resiTelNo.equalsIgnoreCase("") ? "" : this.resiTelNo);
            if (this.mobileNo == null || this.mobileNo.equalsIgnoreCase("")) {
                addtionalAddTo.setMobISDCode("");
            } else {
                addtionalAddTo.setMobISDCode(this.mobISDCode == null || this.mobISDCode.equalsIgnoreCase("") ? "" : this.mobISDCode);
            }
            addtionalAddTo.setMobileNo(this.mobileNo == null || this.mobileNo.equalsIgnoreCase("") ? "" : this.mobileNo);
            addtionalAddTo.setFaxSTDCode(this.faxSTDCode == null || this.faxSTDCode.equalsIgnoreCase("") ? "" : this.faxSTDCode);
            addtionalAddTo.setFaxNo(this.faxNo == null || this.faxNo.equalsIgnoreCase("") ? "" : this.faxNo);
            addtionalAddTo.setEmailID(this.emailID == null || this.emailID.equalsIgnoreCase("") ? "" : this.emailID);
            addtionalAddTo.setOfficeTelSTDCode(this.officeTelSTDCode == null || this.officeTelSTDCode.equalsIgnoreCase("") ? "" : this.officeTelSTDCode);
            addtionalAddTo.setOfficeTelNo(this.officeTelNo == null || this.officeTelNo.equalsIgnoreCase("") ? "" : this.officeTelNo);

            addressDetailsList.add(addtionalAddTo);

            this.addressType = "";
            this.localAddressLine1 = "";
            this.localAddressLine2 = "";
            this.localAddressLine3 = "";
            this.localAddressCityVillage = "";
            this.localAddressDistrict = "";
            this.localAddressPINCode = "";
            this.localAddressState = "";
            this.localAddressCountry = "";
            this.proofofAdd = "";
            this.resiTelSTDCode = "";
            this.resiTelNo = "";
            this.mobISDCode = "";
            this.mobileNo = "";
            this.faxSTDCode = "";
            this.faxNo = "";
            this.emailID = "";
            this.officeTelSTDCode = "";
            this.officeTelNo = "";
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public void onDeleteAddAddress(CbsCustAdditionalAddressDetailsTo custAddAdressDetails) {
        addressDetailsList.remove(custAddAdressDetails);
    }

    public void onChangeCountry() {
        if (!localAddressCountry.equalsIgnoreCase("IN")) {
            displayOnCntryIN = "none";
            displayOnCntryNotIN = "";
        } else {
            displayOnCntryIN = "";
            displayOnCntryNotIN = "none";
        }
    }

    public void refreshForm() {
        displayOnCntryIN = "";
        displayOnCntryNotIN = "none";
        this.addressDetailsList = new ArrayList<CbsCustAdditionalAddressDetailsTo>();
        this.addressType = "";
        this.localAddressLine1 = "";
        this.localAddressLine2 = "";
        this.localAddressLine3 = "";
        this.localAddressCityVillage = "";
        this.localAddressDistrict = "";
        this.localAddressPINCode = "";
        this.localAddressState = "";
        this.localAddressCountry = "";
        this.proofofAdd = "";
        this.resiTelSTDCode = "";
        this.resiTelNo = "";
        this.mobISDCode = "";
        this.mobileNo = "";
        this.faxSTDCode = "";
        this.faxNo = "";
        this.emailID = "";
        this.officeTelSTDCode = "";
        this.officeTelNo = "";
    }

    public void selectFieldValues() {
        try {
            List<CbsCustAdditionalAddressDetailsTo> custAddressDetailsList = masterDelegate.getCustAddressDetailsByCustId(getCustomerDetail().getCustId());
            if (custAddressDetailsList.size() > 0) {
                addressDetailsList = new ArrayList<CbsCustAdditionalAddressDetailsTo>();
                for (CbsCustAdditionalAddressDetailsTo obj : custAddressDetailsList) {
                    obj.setAddressTypeDesc(masterDelegate.getRecRefDiscription("354", obj.getAddressType()));
                    obj.setLocalAddressStateDesc(masterDelegate.getRecRefDiscription("002", obj.getLocalAddressState()));
                    obj.setLocalAddressCountryDesc(masterDelegate.getRecRefDiscription("302", obj.getLocalAddressCountry()));
                    obj.setProofofAddDesc(masterDelegate.getRecRefDiscription("312", obj.getProofofAdd()));
                    addressDetailsList.add(obj);
                }
            }
        } catch (ApplicationException ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getLocalAddressLine1() {
        return localAddressLine1;
    }

    public void setLocalAddressLine1(String localAddressLine1) {
        this.localAddressLine1 = localAddressLine1;
    }

    public String getLocalAddressLine2() {
        return localAddressLine2;
    }

    public void setLocalAddressLine2(String localAddressLine2) {
        this.localAddressLine2 = localAddressLine2;
    }

    public String getLocalAddressLine3() {
        return localAddressLine3;
    }

    public void setLocalAddressLine3(String localAddressLine3) {
        this.localAddressLine3 = localAddressLine3;
    }

    public String getLocalAddressCityVillage() {
        return localAddressCityVillage;
    }

    public void setLocalAddressCityVillage(String localAddressCityVillage) {
        this.localAddressCityVillage = localAddressCityVillage;
    }

    public String getLocalAddressDistrict() {
        return localAddressDistrict;
    }

    public void setLocalAddressDistrict(String localAddressDistrict) {
        this.localAddressDistrict = localAddressDistrict;
    }

    public String getLocalAddressPINCode() {
        return localAddressPINCode;
    }

    public void setLocalAddressPINCode(String localAddressPINCode) {
        this.localAddressPINCode = localAddressPINCode;
    }

    public String getLocalAddressState() {
        return localAddressState;
    }

    public void setLocalAddressState(String localAddressState) {
        this.localAddressState = localAddressState;
    }

    public String getLocalAddressCountry() {
        return localAddressCountry;
    }

    public void setLocalAddressCountry(String localAddressCountry) {
        this.localAddressCountry = localAddressCountry;
    }

    public String getProofofAdd() {
        return proofofAdd;
    }

    public void setProofofAdd(String proofofAdd) {
        this.proofofAdd = proofofAdd;
    }

    public String getResiTelSTDCode() {
        return resiTelSTDCode;
    }

    public void setResiTelSTDCode(String resiTelSTDCode) {
        this.resiTelSTDCode = resiTelSTDCode;
    }

    public String getResiTelNo() {
        return resiTelNo;
    }

    public void setResiTelNo(String resiTelNo) {
        this.resiTelNo = resiTelNo;
    }

    public String getOfficeTelSTDCode() {
        return officeTelSTDCode;
    }

    public void setOfficeTelSTDCode(String officeTelSTDCode) {
        this.officeTelSTDCode = officeTelSTDCode;
    }

    public String getOfficeTelNo() {
        return officeTelNo;
    }

    public void setOfficeTelNo(String officeTelNo) {
        this.officeTelNo = officeTelNo;
    }

    public String getMobISDCode() {
        return mobISDCode;
    }

    public void setMobISDCode(String mobISDCode) {
        this.mobISDCode = mobISDCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFaxSTDCode() {
        return faxSTDCode;
    }

    public void setFaxSTDCode(String faxSTDCode) {
        this.faxSTDCode = faxSTDCode;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public List<SelectItem> getAddressTypeList() {
        return addressTypeList;
    }

    public void setAddressTypeList(List<SelectItem> addressTypeList) {
        this.addressTypeList = addressTypeList;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public List<SelectItem> getLocalAddressStateList() {
        return localAddressStateList;
    }

    public void setLocalAddressStateList(List<SelectItem> localAddressStateList) {
        this.localAddressStateList = localAddressStateList;
    }

    public List<SelectItem> getLocalAddressCountryList() {
        return localAddressCountryList;
    }

    public void setLocalAddressCountryList(List<SelectItem> localAddressCountryList) {
        this.localAddressCountryList = localAddressCountryList;
    }

    public List<CbsCustAdditionalAddressDetailsTo> getAddressDetailsList() {
        return addressDetailsList;
    }

    public void setAddressDetailsList(List<CbsCustAdditionalAddressDetailsTo> addressDetailsList) {
        this.addressDetailsList = addressDetailsList;
    }

    public List<SelectItem> getProofofAddList() {
        return proofofAddList;
    }

    public void setProofofAddList(List<SelectItem> proofofAddList) {
        this.proofofAddList = proofofAddList;
    }

    public CustomerMasterDelegate getMasterDelegate() {
        return masterDelegate;
    }

    public void setMasterDelegate(CustomerMasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    public CustomerMgmtFacadeRemote getCustomerMgmtRemote() {
        return customerMgmtRemote;
    }

    public void setCustomerMgmtRemote(CustomerMgmtFacadeRemote customerMgmtRemote) {
        this.customerMgmtRemote = customerMgmtRemote;
    }

    public String getStateNotIN() {
        return stateNotIN;
    }

    public void setStateNotIN(String stateNotIN) {
        this.stateNotIN = stateNotIN;
    }

    public String getDisplayOnCntryIN() {
        return displayOnCntryIN;
    }

    public void setDisplayOnCntryIN(String displayOnCntryIN) {
        this.displayOnCntryIN = displayOnCntryIN;
    }

    public String getDisplayOnCntryNotIN() {
        return displayOnCntryNotIN;
    }

    public void setDisplayOnCntryNotIN(String displayOnCntryNotIN) {
        this.displayOnCntryNotIN = displayOnCntryNotIN;
    }

    public String getDistrictNotIN() {
        return districtNotIN;
    }

    public void setDistrictNotIN(String districtNotIN) {
        this.districtNotIN = districtNotIN;
    }
}
