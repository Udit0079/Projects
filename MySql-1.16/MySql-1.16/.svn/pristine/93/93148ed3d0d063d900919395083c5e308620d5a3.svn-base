/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSCustMinorInfoTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.dto.master.ParameterinfoReportTO;
import com.cbs.utils.CbsUtil;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author ANKIT VERMA
 */
public class MinorInfo {

    CustomerDetail customerDetail;
    Date minorDob;
    Date majoritydate;
    String guardianCode;
    String guagdianName;
    String guarAdd1;
    String guarAdd2;
    String guarVillage;
    String guarTehsilCode;
    String guarpostal;
    String guarPhone;
    String guarMob;
    String guarEmail;
    String titleTypeGuar;
    List<SelectItem> titleTypeGuarOption;
    String cityForMinorAddress;
    List<SelectItem> cityForMinorAddressOption;
    String countryForMinorAddress;
    String stateForMinorAddress;
    List<SelectItem> countryForMinorAddressOption;
    List<SelectItem> stateForMinorAddressOption;
    CustomerMasterDelegate masterDelegate;
    Date date = new Date();

    /** Creates a new instance of MinorInfo */
    public MinorInfo() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            titleTypeGuarOption = new ArrayList<SelectItem>();
            titleTypeGuarOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> titleOptionList = masterDelegate.getFunctionValues("045");
            for (CbsRefRecTypeTO recTypeTO : titleOptionList) {
                titleTypeGuarOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            cityForMinorAddressOption = new ArrayList<SelectItem>();
            cityForMinorAddressOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> cityOptionList = masterDelegate.getFunctionValues("001");
            for (CbsRefRecTypeTO recTypeTO : cityOptionList) {
                cityForMinorAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            stateForMinorAddressOption = new ArrayList<SelectItem>();
            stateForMinorAddressOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> stateOptionList = masterDelegate.getFunctionValues("002");
            for (CbsRefRecTypeTO recTypeTO : stateOptionList) {
                stateForMinorAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            countryForMinorAddressOption = new ArrayList<SelectItem>();
            countryForMinorAddressOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> countryOptionList = masterDelegate.getFunctionValues("003");
            for (CbsRefRecTypeTO recTypeTO : countryOptionList) {
                countryForMinorAddressOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public String getCityForMinorAddress() {
        return cityForMinorAddress;
    }

    public void setCityForMinorAddress(String cityForMinorAddress) {
        this.cityForMinorAddress = cityForMinorAddress;
    }

    public List<SelectItem> getCityForMinorAddressOption() {
        return cityForMinorAddressOption;
    }

    public void setCityForMinorAddressOption(List<SelectItem> cityForMinorAddressOption) {
        this.cityForMinorAddressOption = cityForMinorAddressOption;
    }

    public String getGuagdianName() {
        return guagdianName;
    }

    public void setGuagdianName(String guagdianName) {
        this.guagdianName = guagdianName;
    }

    public String getGuarAdd1() {
        return guarAdd1;
    }

    public void setGuarAdd1(String guarAdd1) {
        this.guarAdd1 = guarAdd1;
    }

    public String getGuarAdd2() {
        return guarAdd2;
    }

    public void setGuarAdd2(String guarAdd2) {
        this.guarAdd2 = guarAdd2;
    }

    public String getGuarEmail() {
        return guarEmail;
    }

    public void setGuarEmail(String guarEmail) {
        this.guarEmail = guarEmail;
    }

    public String getGuarMob() {
        return guarMob;
    }

    public void setGuarMob(String guarMob) {
        this.guarMob = guarMob;
    }

    public String getGuarPhone() {
        return guarPhone;
    }

    public void setGuarPhone(String guarPhone) {
        this.guarPhone = guarPhone;
    }

    public String getGuarTehsilCode() {
        return guarTehsilCode;
    }

    public void setGuarTehsilCode(String guarTehsilCode) {
        this.guarTehsilCode = guarTehsilCode;
    }

    public String getGuarVillage() {
        return guarVillage;
    }

    public void setGuarVillage(String guarVillage) {
        this.guarVillage = guarVillage;
    }

    public String getGuardianCode() {
        return guardianCode;
    }

    public void setGuardianCode(String guardianCode) {
        this.guardianCode = guardianCode;
    }

    public String getGuarpostal() {
        return guarpostal;
    }

    public void setGuarpostal(String guarpostal) {
        this.guarpostal = guarpostal;
    }

    public Date getMajoritydate() {
        return majoritydate;
    }

    public void setMajoritydate(Date majoritydate) {
        this.majoritydate = majoritydate;
    }

    public Date getMinorDob() {
        return minorDob;
    }

    public void setMinorDob(Date minorDob) {
        this.minorDob = minorDob;
    }

    public String getTitleTypeGuar() {
        return titleTypeGuar;
    }

    public void setTitleTypeGuar(String titleTypeGuar) {
        this.titleTypeGuar = titleTypeGuar;
    }

    public List<SelectItem> getTitleTypeGuarOption() {
        return titleTypeGuarOption;
    }

    public void setTitleTypeGuarOption(List<SelectItem> titleTypeGuarOption) {
        this.titleTypeGuarOption = titleTypeGuarOption;
    }

    public String getCountryForMinorAddress() {
        return countryForMinorAddress;
    }

    public void setCountryForMinorAddress(String countryForMinorAddress) {
        this.countryForMinorAddress = countryForMinorAddress;
    }

    public List<SelectItem> getCountryForMinorAddressOption() {
        return countryForMinorAddressOption;
    }

    public void setCountryForMinorAddressOption(List<SelectItem> countryForMinorAddressOption) {
        this.countryForMinorAddressOption = countryForMinorAddressOption;
    }

    public String getStateForMinorAddress() {
        return stateForMinorAddress;
    }

    public void setStateForMinorAddress(String stateForMinorAddress) {
        this.stateForMinorAddress = stateForMinorAddress;
    }

    public List<SelectItem> getStateForMinorAddressOption() {
        return stateForMinorAddressOption;
    }

    public void setStateForMinorAddressOption(List<SelectItem> stateForMinorAddressOption) {
        this.stateForMinorAddressOption = stateForMinorAddressOption;
    }

    public String onchangeMinorDateofBirth() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String msg1 = "";
        try {
            ParameterinfoReportTO to = masterDelegate.getAgeLimit("MINOR-AGE-LIMIT");
            int minorAgeLimit = to.getCode();
            int strDateDiff = CbsUtil.yearDiff(minorDob, date);
            if (strDateDiff <= 0) {
                getCustomerDetail().setErrorMsg("The selected date is after the current date.");
                return msg1 = "The selected date is after the current date.";
            } else if (strDateDiff > 0) {
                if (strDateDiff >= to.getCode()) {
                    getCustomerDetail().setErrorMsg("Minor can't be equal to or greater than " + minorAgeLimit + " years.");
                    return msg1 = "The selected date is after the current date.";
                } else {
                    getCustomerDetail().setErrorMsg("");
                    return msg1 = "true";
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
        return msg1;
    }

    public String onchangeMajorityDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String msg1 = "";
        try {
            ParameterinfoReportTO to = masterDelegate.getAgeLimit("MINOR-AGE-LIMIT");
            int minorAgeLimit = to.getCode();
            int strDateDiff = CbsUtil.yearDiff(minorDob, majoritydate);
            if (strDateDiff > 0) {
                if (strDateDiff < to.getCode()) {
                    getCustomerDetail().setErrorMsg("Majority Date ca't be less than " + minorAgeLimit + " years.");
                    return msg1 = "Majority Date ca't be less than " + minorAgeLimit + " years.";
                } else {
                    getCustomerDetail().setErrorMsg("");
                    return msg1 = "true";
                }
            } else {
                getCustomerDetail().setErrorMsg("Invalid Majority Date.");
                return msg1 = "Invalid Majority Date.";
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
        return msg1;
    }

    public String guardianName() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher guagdianNameCheck = p.matcher(this.guagdianName);
        if (guagdianNameCheck.matches()) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Guardian Name.");
            return "Please Enter Non Numeric Value in Guardian Name.";
        } else if (this.guagdianName.contains("0") || this.guagdianName.contains("1") || this.guagdianName.contains("2") || this.guagdianName.contains("3") || this.guagdianName.contains("4") || this.guagdianName.contains("5")
                || this.guagdianName.contains("6") || this.guagdianName.contains("7") || this.guagdianName.contains("8") || this.guagdianName.contains("9")) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Guardian Name.");
            return "Please Enter Non Numeric Value in Guardian Name.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
    }

    public String onblurGuarTehsilCode() {
        if (!this.guarTehsilCode.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher guarTehsilCodeCheck = p.matcher(this.guarTehsilCode);
            if (!guarTehsilCodeCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Tehsil Code in Minor Information.");
                return "Please Enter Numeric Value in Tehsil Code in Minor Information.";
            } else {
                getCustomerDetail().setErrorMsg("");

            }
        }
        return "true";
    }

    public String onblurMinorPostalCode() {
        if (!this.guarpostal.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher minorPostalCodeCheck = p.matcher(this.guarpostal);
            if (!minorPostalCodeCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Postal Code in Minor Information.");
                return "Please Enter Numeric Value in Postal Code in Minor Information.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        }
        return "true";
    }

    public String onblurMinorPhone() {
        if (!this.guarPhone.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher minorPhoneCheck = p.matcher(this.guarPhone);
            if (!minorPhoneCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Phone No. in Minor Information.");
                return "Please Enter Numeric Value in Phone No. in Minor Information.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        }
        return "true";
    }

    public String onblurMinorMobile() {
        if (!this.guarMob.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher minorMobileCheck = p.matcher(this.guarMob);
            if (!minorMobileCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in Mobile No. in Minor Information.");
                return "Please Enter Numeric Value in Mobile No. in Minor Information.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        }
        return "true";
    }

    public String onblurMinorEmail() {
        if (this.guarEmail.indexOf("@") <= 0) {
            getCustomerDetail().setErrorMsg("Please Enter Valid  Email Id in Minor Information.");
            return "Please Enter Valid  Email Id in Minor Information.";
        } else {
            return "true";
        }
    }

    public void selectFieldValues() {
        try {
            CBSCustMinorInfoTO minorTO = masterDelegate.getMinorInfoByCustId(getCustomerDetail().getCustId());
            if (minorTO != null) {
                if (minorTO.getGuardianCode() == null || minorTO.getGuardianCode().equalsIgnoreCase("")) {
                    this.setGuardianCode(guardianCode);
                } else {
                    this.setGuardianCode(minorTO.getGuardianCode());
                }
                if (minorTO.getGuardianTitle() == null || minorTO.getGuardianTitle().equalsIgnoreCase("")) {
                    this.setTitleTypeGuar("0");
                } else {
                    this.setTitleTypeGuar(minorTO.getGuardianTitle());
                }
                if (minorTO.getGuardianName() == null || minorTO.getGuardianName().equalsIgnoreCase("")) {
                    this.setGuagdianName("");
                } else {
                    this.setGuagdianName(minorTO.getGuardianName());
                }
                if (minorTO.getDateOfBirth() == null) {
                    this.setMinorDob(date);
                } else {
                    this.setMinorDob(minorTO.getDateOfBirth());
                }
                if (minorTO.getLocalAddress1() == null || minorTO.getLocalAddress1().equalsIgnoreCase("")) {
                    this.setGuarAdd1("");
                } else {
                    this.setGuarAdd1(minorTO.getLocalAddress1());
                }
                if (minorTO.getLocalAddress2() == null || minorTO.getLocalAddress2().equalsIgnoreCase("")) {
                    this.setGuarAdd2("");
                } else {
                    this.setGuarAdd2(minorTO.getLocalAddress2());
                }
                if (minorTO.getVillage() == null || minorTO.getVillage().equalsIgnoreCase("")) {
                    this.setGuarVillage("");
                } else {
                    this.setGuarVillage(minorTO.getVillage());
                }
                if (minorTO.getBlock() == null || minorTO.getBlock().equalsIgnoreCase("")) {
                    this.setGuarTehsilCode("");
                } else {
                    this.setGuarTehsilCode(minorTO.getBlock());
                }
                if (minorTO.getCityCode() == null || minorTO.getCityCode().equalsIgnoreCase("")) {
                    this.setCityForMinorAddress("0");
                } else {
                    this.setCityForMinorAddress(minorTO.getCityCode());
                }
                if (minorTO.getStateCode() == null || minorTO.getStateCode().equalsIgnoreCase("")) {
                    this.setStateForMinorAddress("0");
                } else {
                    this.setStateForMinorAddress(minorTO.getStateCode());
                }

                if (minorTO.getPostalCode() == null || minorTO.getPostalCode().equalsIgnoreCase("")) {
                    this.setGuarpostal("");
                } else {
                    this.setGuarpostal(minorTO.getPostalCode());
                }
                if (minorTO.getCountryCode() == null || minorTO.getCountryCode().equalsIgnoreCase("")) {
                    this.setCountryForMinorAddress("0");
                } else {
                    this.setCountryForMinorAddress(minorTO.getCountryCode());
                }
                if (minorTO.getPhoneNumber() == null || minorTO.getPhoneNumber().equalsIgnoreCase("")) {
                    this.setGuarPhone("");
                } else {
                    this.setGuarPhone(minorTO.getPhoneNumber());
                }
                if (minorTO.getMobileNumber() == null || minorTO.getMobileNumber().equalsIgnoreCase("")) {
                    this.setGuarMob("");
                } else {
                    this.setGuarMob(minorTO.getMobileNumber());
                }
                if (minorTO.getMajorityDate() == null) {
                    this.setMajoritydate(date);
                } else {
                    this.setMajoritydate(minorTO.getMajorityDate());
                }
                if (minorTO.getEmailID() == null || minorTO.getEmailID().equalsIgnoreCase("")) {
                    this.setGuarEmail("");
                } else {
                    this.setGuarEmail(minorTO.getEmailID());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }


    }

    public void refreshForm() {
        this.setGuardianCode("");
        this.setTitleTypeGuar("0");
        this.setGuagdianName("");
        this.setGuarAdd1("");
        this.setGuarAdd2("");
        this.setGuarVillage("");
        this.setGuarTehsilCode("");
        this.setCityForMinorAddress("0");
        this.setStateForMinorAddress("0");
        this.setGuarpostal("");
        this.setCountryForMinorAddress("0");
        this.setGuarPhone("");
        this.setGuarMob("");
        this.setGuarEmail("");
        this.setMinorDob(date);
        this.setMajoritydate(date);
    }

    public String validation() {
        String msg1 = onblurGuarTehsilCode();
        if (!msg1.equalsIgnoreCase("true")) {
            return msg1;
        }
        String msg2 = onblurMinorPostalCode();

        if (!msg2.equalsIgnoreCase("true")) {
            return msg2;
        }
        String msg3 = onblurMinorPhone();
        if (!msg3.equalsIgnoreCase("true")) {
            return msg3;
        }
        String msg4 = onblurMinorMobile();
        if (!msg4.equalsIgnoreCase("true")) {
            return msg4;
        }
        String msg5 = onblurMinorEmail();
        if (!msg5.equalsIgnoreCase("true")) {
            return msg5;
        }
        return "ok";
    }
}
