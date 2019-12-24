/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSCustNREInfoHisTO;
import com.cbs.dto.customer.CBSCustNREInfoTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.facade.admin.customer.CustomerManagementFacadeRemote;
import com.cbs.facade.admin.customer.CustomerMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class NreInfo {

    CustomerMasterDelegate masterDelegate;
    CustomerDetail customerDetail;
    String nationality;
    String countryForNre;
    String countryTypeForNre;
    String countryForNre2;
    List<SelectItem> nationalityOption;
    List<SelectItem> countryForNreOption;
    List<SelectItem> countryTypeForNreOption;
    List<SelectItem> countryForNre2Option;
    Date dateOfBeingNre;
    String nreRelativeCode;
    String nreName;
    String nreAdd1;
    String nreVillCode;
    String nreTehsilCode;
    String nrePostal;
    String nrePhoneNo;
    String nreMob;
    String nreEmail;
    String nreAdd2;
    String titleTypeForNre;
    List<SelectItem> titleTypeForNreOption;
    String cityForNre;
    List<SelectItem> cityForNreOption;
    String stateForNre;
    Date nreBecomResident;
    List<SelectItem> stateForNreOption;
    private String relativeCustName;
    Date date = new Date();
    NreInfo nreInfoObj;
    private CustomerManagementFacadeRemote customerRemote = null;
    //Addition on 28/10/2015
    private String dateOfBeingNreChgFlag;
    private String countryForNreChgFlag;
    private String nreRelativeCodeChgFlag;
    private String nreAdd1ChgFlag;
    private String nreAdd2ChgFlag;
    private String nreVillCodeChgFlag;
    private String cityForNreChgFlag;
    private String stateForNreChgFlag;
    private String nrePostalChgFlag;
    private String nrePhoneNoChgFlag;
    private String nreMobChgFlag;
    private String nreEmailChgFlag;
    private String nreBecomResidentChgFlag;
    private CustomerMgmtFacadeRemote customerMgmtRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public NreInfo() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            customerRemote = (CustomerManagementFacadeRemote) ServiceLocator.getInstance().lookup("CustomerManagementFacade");
            customerMgmtRemote = (CustomerMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CustomerMgmtFacade");

            nationalityOption = new ArrayList<SelectItem>();
            nationalityOption.add(new SelectItem("0", "--Select--"));
            countryForNreOption = new ArrayList<SelectItem>();
            countryForNreOption.add(new SelectItem("0", "--Select--"));
            stateForNreOption = new ArrayList<SelectItem>();
            stateForNreOption.add(new SelectItem("0", "--Select--"));
            cityForNreOption = new ArrayList<SelectItem>();
            cityForNreOption.add(new SelectItem("0", "--Select--"));
            titleTypeForNreOption = new ArrayList<SelectItem>();
            titleTypeForNreOption.add(new SelectItem("0", "--Select--"));

            List<CbsRefRecTypeTO> cityList = masterDelegate.getFunctionValues("001");
            for (CbsRefRecTypeTO recTypeTO : cityList) {
                cityForNreOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

            List<CbsRefRecTypeTO> stateOptionList = masterDelegate.getFunctionValues("002");
            for (CbsRefRecTypeTO recTypeTO : stateOptionList) {
                stateForNreOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

            List<CbsRefRecTypeTO> countryOptionList = masterDelegate.getFunctionValues("302");
            for (CbsRefRecTypeTO recTypeTO : countryOptionList) {
                countryForNreOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                nationalityOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            countryTypeForNreOption = new ArrayList<SelectItem>();
            countryTypeForNreOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> countryTypeOptionList = masterDelegate.getFunctionValues("66");
            for (CbsRefRecTypeTO recTypeTO : countryTypeOptionList) {
                countryTypeForNreOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));

            }
            List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("045");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                titleTypeForNreOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String onblurDateofNre() {
        try {
            long strDateDiff = CbsUtil.dayDiff(dateOfBeingNre, date);
            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can not select the Date Of Becoming NRE after the current date.");
                return "You can not select the Date Of Becoming NRE after the current date.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
        return "true";
    }

    public void onblurGuardianCustId() {
        this.relativeCustName = "";
        try {
            this.relativeCustName = customerRemote.getCustomerName(this.nreRelativeCode);
        } catch (Exception ex) {
            getCustomerDetail().setErrorMsg(ex.getMessage());
        }
    }

    public String onblurNrePostalCode() {
        if (!(this.nrePostal == null || this.nrePostal.trim().equals(""))) {
            if (this.nrePostal.trim().length() != 6) {
                getCustomerDetail().setErrorMsg("Please fill 6 numeric digit for NRE Postal Code.");
                return "Please fill 6 numeric digit for NRE Postal Code.";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher nrePostalCodeCheck = p.matcher(this.nrePostal);
            if (!nrePostalCodeCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in NRE Postal Code.");
                return "Please Enter Numeric Value in NRE Postal Code.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        }
        return "true";
    }

    public String onblurNrePhoneNo() {
        if (!(this.nrePhoneNo == null || this.nrePhoneNo.trim().equals(""))) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher nrePhoneNoCheck = p.matcher(this.nrePhoneNo);
            if (!nrePhoneNoCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in NRE Phone No.");
                return "Please Enter Numeric Value in NRE Phone No.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        }
        return "true";
    }

    public String onblurNreMobNo() {
        if (!(this.nreMob == null || this.nreMob.trim().equals(""))) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher nreMobCheck = p.matcher(this.nreMob);
            if (!nreMobCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in NRE Mobile No.");
                return "Please Enter Numeric Value in NRE Mobile No.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        }
        return "true";
    }

    public String onblurNreEmail() {
        if (!(this.nreEmail == null || this.nreEmail.equals(""))) {
            if (this.nreEmail.indexOf("@") <= 0) {
                getCustomerDetail().setErrorMsg("Please Enter Valid  Email Id in NRE Information.");
                return "Please Enter Valid  Email Id in NRE Information.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        }
        return "true";
    }

    public String onblurNreBecomeResident() {
        try {
            long strDateDiff = CbsUtil.dayDiff(nreBecomResident, date);
            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can not select the NRE Becoming Resident after the current date.");
                return "You can not select the NRE Becoming Resident after the current date.";
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
        return "true";
    }

    //Old
//    public String onblurNreTehsilCode() {
//        if (!this.nreTehsilCode.equalsIgnoreCase("")) {
//            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//            Matcher nreTehsilCodeCheck = p.matcher(this.nreTehsilCode);
//            if (!nreTehsilCodeCheck.matches()) {
//                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in NRE Tehsil Code.");
//                return "Please Enter Numeric Value in NRE Tehsil Code.";
//            } else {
//                getCustomerDetail().setErrorMsg("");
//                return "true";
//            }
//        }
//        return "true";
//    }
//    public String onblurNreRelativeName() {
//        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        Matcher nreRelativeNameCheck = p.matcher(this.nreName);
//        if (nreRelativeNameCheck.matches()) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in NRE Relative Name.");
//            return "Please Enter Non Numeric Value in NRE Relative Name.";
//        } else if (this.nreName.contains("0") || this.nreName.contains("1") || this.nreName.contains("2") || this.nreName.contains("3") || this.nreName.contains("4") || this.nreName.contains("5")
//                || this.nreName.contains("6") || this.nreName.contains("7") || this.nreName.contains("8") || this.nreName.contains("9")) {
//            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in NRE Relative Name.");
//            return "Please Enter Non Numeric Value in NRE Relative Name.";
//        } else {
//            getCustomerDetail().setErrorMsg("");
//            return "true";
//        }
//    }
    public void saveNreDetails() {
        nreInfoObj = new NreInfo();
        nreInfoObj.setNationality(nationality);
        getCustomerDetail().setErrorMsg("Please select nationality !");
        if (dateOfBeingNre != null) {
            onblurDateofNre();
        } else {
            getCustomerDetail().setErrorMsg("Please insert date of become NRE !");
            return;
        }
        nreInfoObj.setCountryForNre(countryForNre);
        nreInfoObj.setCountryTypeForNre(countryTypeForNre);
        nreInfoObj.setNreRelativeCode(nreRelativeCode);
        nreInfoObj.setTitleTypeForNre(titleTypeForNre);
        nreInfoObj.setNreName(nreName);
        nreInfoObj.setNreAdd1(nreAdd1);
        nreInfoObj.setNreAdd2(nreAdd2);
        nreInfoObj.setNreVillCode(nreVillCode);
        nreInfoObj.setNreTehsilCode(nreTehsilCode);
    }

    public String validation() {
        String valMsg = onblurDateofNre();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = onblurNrePostalCode();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = onblurNrePhoneNo();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = onblurNreMobNo();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = onblurNreEmail();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        valMsg = onblurNreBecomeResident();
        if (!valMsg.equalsIgnoreCase("true")) {
            return valMsg;
        }
        return "ok";
    }

    public void selectFieldValues() {
        try {
            CBSCustNREInfoTO nreTO = masterDelegate.getNreInfoByCustId(getCustomerDetail().getCustId());
            if (nreTO != null) {
//                if (nreTO.getNationality() == null || nreTO.getNationality().equalsIgnoreCase("")) {
//                    this.setNationality("0");
//                } else {
//                    this.setNationality(nreTO.getNationality());
//                }
//                if (nreTO.getCountryType() == null || nreTO.getCountryType().equalsIgnoreCase("")) {
//                    this.setCountryTypeForNre("0");
//                } else {
//                    this.setCountryTypeForNre(nreTO.getCountryType());
//                }
//                if (nreTO.getLocalPersonTitle() == null || nreTO.getLocalPersonTitle().equalsIgnoreCase("")) {
//                    this.setTitleTypeForNre("");
//                } else {
//                    this.setTitleTypeForNre(nreTO.getLocalPersonTitle());
//                }
//                if (nreTO.getLocalContPersonName() == null || nreTO.getLocalContPersonName().equalsIgnoreCase("")) {
//                    this.setNreName("");
//                } else {
//                    this.setNreName(nreTO.getLocalContPersonName());
//                }
//                if (nreTO.getBlock() == null || nreTO.getBlock().equalsIgnoreCase("")) {
//                    this.setNreTehsilCode("");
//                } else {
//                    this.setNreTehsilCode(nreTO.getBlock());
//                }

                if (nreTO.getCountryCode() == null || nreTO.getCountryCode().equals("0") || nreTO.getCountryCode().equals("")) {
                    this.setCountryForNre("0");
                } else {
                    this.setCountryForNre(nreTO.getCountryCode());
                }
                if (nreTO.getNonResidentDate() == null) {
                    this.setDateOfBeingNre(date);
                } else {
                    this.setDateOfBeingNre(nreTO.getNonResidentDate());
                }
                if (nreTO.getLocalContPersonCode() == null || nreTO.getLocalContPersonCode().equalsIgnoreCase("")) {
                    this.setNreRelativeCode("");
                    this.setRelativeCustName("");
                } else {
                    this.setNreRelativeCode(nreTO.getLocalContPersonCode());
                    this.setRelativeCustName(customerRemote.getCustomerName(nreTO.getLocalContPersonCode()));
                }
                if (nreTO.getLocalAddress1() == null || nreTO.getLocalAddress1().equalsIgnoreCase("")) {
                    this.setNreAdd1("");
                } else {
                    this.setNreAdd1(nreTO.getLocalAddress1());
                }
                if (nreTO.getLocalAddress2() == null || nreTO.getLocalAddress2().equalsIgnoreCase("")) {
                    this.setNreAdd2("");
                } else {
                    this.setNreAdd2(nreTO.getLocalAddress2());
                }
                if (nreTO.getVillage() == null || nreTO.getVillage().equalsIgnoreCase("")) {
                    this.setNreVillCode("");
                } else {
                    this.setNreVillCode(nreTO.getVillage());
                }
                if (nreTO.getCityCode() == null || nreTO.getCityCode().equalsIgnoreCase("")) {
                    this.setCityForNre("");
                } else {
                    this.setCityForNre(nreTO.getCityCode());
                }
                if (nreTO.getStateCode() == null || nreTO.getStateCode().equalsIgnoreCase("")) {
                    this.setStateForNre("");
                } else {
                    this.setStateForNre(nreTO.getStateCode());
                }
                if (nreTO.getPostalCode() == null || nreTO.getPostalCode().equalsIgnoreCase("")) {
                    this.setNrePostal("");
                } else {
                    this.setNrePostal(nreTO.getPostalCode());
                }
                if (nreTO.getPhoneNumber() == null || nreTO.getPhoneNumber().equalsIgnoreCase("")) {
                    this.setNrePhoneNo("");
                } else {
                    this.setNrePhoneNo(nreTO.getPhoneNumber());
                }
                if (nreTO.getMobileNumber() == null || nreTO.getMobileNumber().equalsIgnoreCase("")) {
                    this.setNreMob("");
                } else {
                    this.setNreMob(nreTO.getMobileNumber());
                }
                if (nreTO.getNreEmial() == null || nreTO.getNreEmial().equalsIgnoreCase("")) {
                    this.setNreEmail("");
                } else {
                    this.setNreEmail(nreTO.getNreEmial());
                }
                if (nreTO.getNonResidentEndDate() == null) {
                    this.setNreBecomResident(date);
                } else {
                    this.setNreBecomResident(nreTO.getNonResidentEndDate());
                }
            }
            //Addition on 27/10/2015
            makeChangeFlagToUnChange();
            if (getCustomerDetail().getFunction().equalsIgnoreCase("V")) {
                CBSCustNREInfoHisTO hisTo = customerMgmtRemote.
                        getCustomerLastChangeDetailForNre(getCustomerDetail().getVerifyCustId());
                if (hisTo != null) {
                    this.dateOfBeingNreChgFlag = this.dateOfBeingNre.compareTo(hisTo.getNonResidentDate() == null ? ymd.parse("19000101") : hisTo.getNonResidentDate()) == 0 ? "color: #000000" : "color: #FF0000";
                    this.countryForNreChgFlag = this.countryForNre.equalsIgnoreCase((hisTo.getCountryCode() == null || hisTo.getCountryCode().equals("") ? "0" : hisTo.getCountryCode())) ? "color: #000000" : "color: #FF0000";
                    this.nreRelativeCodeChgFlag = this.nreRelativeCode.equalsIgnoreCase((hisTo.getLocalContPersonCode() == null || hisTo.getLocalContPersonCode().equals("")) ? "" : hisTo.getLocalContPersonCode()) ? "color: #000000" : "color: #FF0000";
                    this.nreAdd1ChgFlag = this.nreAdd1.equalsIgnoreCase((hisTo.getLocalAddress1() == null || hisTo.getLocalAddress1().equals("")) ? "" : hisTo.getLocalAddress1()) ? "color: #000000" : "color: #FF0000";
                    this.nreAdd2ChgFlag = this.nreAdd2.equalsIgnoreCase((hisTo.getLocalAddress2() == null || hisTo.getLocalAddress2().equals("")) ? "" : hisTo.getLocalAddress2()) ? "color: #000000" : "color: #FF0000";
                    this.nreVillCodeChgFlag = this.nreVillCode.equalsIgnoreCase((hisTo.getVillage() == null || hisTo.getVillage().equals("")) ? "" : hisTo.getVillage()) ? "color: #000000" : "color: #FF0000";
                    this.cityForNreChgFlag = this.cityForNre.equalsIgnoreCase((hisTo.getCityCode() == null || hisTo.getCityCode().equals("")) ? "" : hisTo.getCityCode()) ? "color: #000000" : "color: #FF0000";
                    this.stateForNreChgFlag = this.stateForNre.equalsIgnoreCase((hisTo.getStateCode() == null || hisTo.getStateCode().equals("")) ? "" : hisTo.getStateCode()) ? "color: #000000" : "color: #FF0000";
                    this.nrePostalChgFlag = this.nrePostal.equalsIgnoreCase((hisTo.getPostalCode() == null || hisTo.getPostalCode().equals("")) ? "" : hisTo.getPostalCode()) ? "color: #000000" : "color: #FF0000";
                    this.nrePhoneNoChgFlag = this.nrePhoneNo.equalsIgnoreCase((hisTo.getPhoneNumber() == null || hisTo.getPhoneNumber().equals("")) ? "" : hisTo.getPhoneNumber()) ? "color: #000000" : "color: #FF0000";
                    this.nreMobChgFlag = this.nreMob.equalsIgnoreCase((hisTo.getMobileNumber() == null || hisTo.getMobileNumber().equals("")) ? "" : hisTo.getMobileNumber()) ? "color: #000000" : "color: #FF0000";
                    this.nreEmailChgFlag = this.nreEmail.equalsIgnoreCase((hisTo.getNreEmail() == null || hisTo.getNreEmail().equals("")) ? "" : hisTo.getNreEmail()) ? "color: #000000" : "color: #FF0000";
                    this.nreBecomResidentChgFlag = this.nreBecomResident.compareTo(hisTo.getNonResidentEndDate() == null ? ymd.parse("19000101") : hisTo.getNonResidentEndDate()) == 0 ? "color: #000000" : "color: #FF0000";
                }
            }
            //End here
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public void refreshForm() {
//        this.setNationality("0");
        this.setCountryForNre("0");

//        this.setCountryTypeForNre("0");
        this.setNreRelativeCode("");
//        this.setTitleTypeForNre("0");
//        this.setNreName("");

        this.setNreAdd1("");
        this.setNreAdd2("");
        this.setNreVillCode("");
//        this.setNreTehsilCode("");

        this.setCityForNre("0");
        this.setStateForNre("0");
        this.setNrePostal("");
        this.setNrePhoneNo("");

        this.setNreMob("");
        this.setNreBecomResident(date);
        this.setDateOfBeingNre(date);
        this.setRelativeCustName("");
        this.setNreEmail("");
        //Change On 28/10/2015
        makeChangeFlagToUnChange();
    }

    //Add On 28/10/2015
    public void makeChangeFlagToUnChange() {
        this.setDateOfBeingNreChgFlag("color: #000000");
        this.setCountryForNreChgFlag("color: #000000");
        this.setNreRelativeCodeChgFlag("color: #000000");
        this.setNreAdd1ChgFlag("color: #000000");
        this.setNreAdd2ChgFlag("color: #000000");
        this.setNreVillCodeChgFlag("color: #000000");
        this.setCityForNreChgFlag("color: #000000");
        this.setStateForNreChgFlag("color: #000000");
        this.setNrePostalChgFlag("color: #000000");
        this.setNrePhoneNoChgFlag("color: #000000");
        this.setNreMobChgFlag("color: #000000");
        this.setNreEmailChgFlag("color: #000000");
        this.setNreBecomResidentChgFlag("color: #000000");
    }

    //Getter And Setter
    public NreInfo getNreInfoObj() {
        return nreInfoObj;
    }

    public void setNreInfoObj(NreInfo nreInfoObj) {
        this.nreInfoObj = nreInfoObj;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public String getCountryForNre() {
        return countryForNre;
    }

    public void setCountryForNre(String countryForNre) {
        this.countryForNre = countryForNre;
    }

    public String getCountryForNre2() {
        return countryForNre2;
    }

    public void setCountryForNre2(String countryForNre2) {
        this.countryForNre2 = countryForNre2;
    }

    public String getCountryTypeForNre() {
        return countryTypeForNre;
    }

    public void setCountryTypeForNre(String countryTypeForNre) {
        this.countryTypeForNre = countryTypeForNre;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<SelectItem> getCountryForNre2Option() {
        return countryForNre2Option;
    }

    public void setCountryForNre2Option(List<SelectItem> countryForNre2Option) {
        this.countryForNre2Option = countryForNre2Option;
    }

    public List<SelectItem> getCountryForNreOption() {
        return countryForNreOption;
    }

    public void setCountryForNreOption(List<SelectItem> countryForNreOption) {
        this.countryForNreOption = countryForNreOption;
    }

    public List<SelectItem> getCountryTypeForNreOption() {
        return countryTypeForNreOption;
    }

    public void setCountryTypeForNreOption(List<SelectItem> countryTypeForNreOption) {
        this.countryTypeForNreOption = countryTypeForNreOption;
    }

    public List<SelectItem> getNationalityOption() {
        return nationalityOption;
    }

    public void setNationalityOption(List<SelectItem> nationalityOption) {
        this.nationalityOption = nationalityOption;
    }

    public Date getDateOfBeingNre() {
        return dateOfBeingNre;
    }

    public void setDateOfBeingNre(Date dateOfBeingNre) {
        this.dateOfBeingNre = dateOfBeingNre;
    }

    public String getNreAdd1() {
        return nreAdd1;
    }

    public void setNreAdd1(String nreAdd1) {
        this.nreAdd1 = nreAdd1;
    }

    public String getNreEmail() {
        return nreEmail;
    }

    public void setNreEmail(String nreEmail) {
        this.nreEmail = nreEmail;
    }

    public String getNreMob() {
        return nreMob;
    }

    public void setNreMob(String nreMob) {
        this.nreMob = nreMob;
    }

    public String getNreName() {
        return nreName;
    }

    public void setNreName(String nreName) {
        this.nreName = nreName;
    }

    public String getNrePhoneNo() {
        return nrePhoneNo;
    }

    public void setNrePhoneNo(String nrePhoneNo) {
        this.nrePhoneNo = nrePhoneNo;
    }

    public String getNrePostal() {
        return nrePostal;
    }

    public void setNrePostal(String nrePostal) {
        this.nrePostal = nrePostal;
    }

    public String getNreRelativeCode() {
        return nreRelativeCode;
    }

    public void setNreRelativeCode(String nreRelativeCode) {
        this.nreRelativeCode = nreRelativeCode;
    }

    public String getNreTehsilCode() {
        return nreTehsilCode;
    }

    public void setNreTehsilCode(String nreTehsilCode) {
        this.nreTehsilCode = nreTehsilCode;
    }

    public String getNreVillCode() {
        return nreVillCode;
    }

    public void setNreVillCode(String nreVillCode) {
        this.nreVillCode = nreVillCode;
    }

    public String getTitleTypeForNre() {
        return titleTypeForNre;
    }

    public void setTitleTypeForNre(String titleTypeForNre) {
        this.titleTypeForNre = titleTypeForNre;
    }

    public List<SelectItem> getTitleTypeForNreOption() {
        return titleTypeForNreOption;
    }

    public void setTitleTypeForNreOption(List<SelectItem> titleTypeForNreOption) {
        this.titleTypeForNreOption = titleTypeForNreOption;
    }

    public String getCityForNre() {
        return cityForNre;
    }

    public void setCityForNre(String cityForNre) {
        this.cityForNre = cityForNre;
    }

    public List<SelectItem> getCityForNreOption() {
        return cityForNreOption;
    }

    public void setCityForNreOption(List<SelectItem> cityForNreOption) {
        this.cityForNreOption = cityForNreOption;
    }

    public String getStateForNre() {
        return stateForNre;
    }

    public void setStateForNre(String stateForNre) {
        this.stateForNre = stateForNre;
    }

    public Date getNreBecomResident() {
        return nreBecomResident;
    }

    public void setNreBecomResident(Date nreBecomResident) {
        this.nreBecomResident = nreBecomResident;
    }

    public String getNreAdd2() {
        return nreAdd2;
    }

    public void setNreAdd2(String nreAdd2) {
        this.nreAdd2 = nreAdd2;
    }

    public List<SelectItem> getStateForNreOption() {
        return stateForNreOption;
    }

    public void setStateForNreOption(List<SelectItem> stateForNreOption) {
        this.stateForNreOption = stateForNreOption;
    }

    public String getRelativeCustName() {
        return relativeCustName;
    }

    public void setRelativeCustName(String relativeCustName) {
        this.relativeCustName = relativeCustName;
    }

    public String getDateOfBeingNreChgFlag() {
        return dateOfBeingNreChgFlag;
    }

    public void setDateOfBeingNreChgFlag(String dateOfBeingNreChgFlag) {
        this.dateOfBeingNreChgFlag = dateOfBeingNreChgFlag;
    }

    public String getCountryForNreChgFlag() {
        return countryForNreChgFlag;
    }

    public void setCountryForNreChgFlag(String countryForNreChgFlag) {
        this.countryForNreChgFlag = countryForNreChgFlag;
    }

    public String getNreRelativeCodeChgFlag() {
        return nreRelativeCodeChgFlag;
    }

    public void setNreRelativeCodeChgFlag(String nreRelativeCodeChgFlag) {
        this.nreRelativeCodeChgFlag = nreRelativeCodeChgFlag;
    }

    public String getNreAdd1ChgFlag() {
        return nreAdd1ChgFlag;
    }

    public void setNreAdd1ChgFlag(String nreAdd1ChgFlag) {
        this.nreAdd1ChgFlag = nreAdd1ChgFlag;
    }

    public String getNreAdd2ChgFlag() {
        return nreAdd2ChgFlag;
    }

    public void setNreAdd2ChgFlag(String nreAdd2ChgFlag) {
        this.nreAdd2ChgFlag = nreAdd2ChgFlag;
    }

    public String getNreVillCodeChgFlag() {
        return nreVillCodeChgFlag;
    }

    public void setNreVillCodeChgFlag(String nreVillCodeChgFlag) {
        this.nreVillCodeChgFlag = nreVillCodeChgFlag;
    }

    public String getCityForNreChgFlag() {
        return cityForNreChgFlag;
    }

    public void setCityForNreChgFlag(String cityForNreChgFlag) {
        this.cityForNreChgFlag = cityForNreChgFlag;
    }

    public String getStateForNreChgFlag() {
        return stateForNreChgFlag;
    }

    public void setStateForNreChgFlag(String stateForNreChgFlag) {
        this.stateForNreChgFlag = stateForNreChgFlag;
    }

    public String getNrePostalChgFlag() {
        return nrePostalChgFlag;
    }

    public void setNrePostalChgFlag(String nrePostalChgFlag) {
        this.nrePostalChgFlag = nrePostalChgFlag;
    }

    public String getNrePhoneNoChgFlag() {
        return nrePhoneNoChgFlag;
    }

    public void setNrePhoneNoChgFlag(String nrePhoneNoChgFlag) {
        this.nrePhoneNoChgFlag = nrePhoneNoChgFlag;
    }

    public String getNreMobChgFlag() {
        return nreMobChgFlag;
    }

    public void setNreMobChgFlag(String nreMobChgFlag) {
        this.nreMobChgFlag = nreMobChgFlag;
    }

    public String getNreEmailChgFlag() {
        return nreEmailChgFlag;
    }

    public void setNreEmailChgFlag(String nreEmailChgFlag) {
        this.nreEmailChgFlag = nreEmailChgFlag;
    }

    public String getNreBecomResidentChgFlag() {
        return nreBecomResidentChgFlag;
    }

    public void setNreBecomResidentChgFlag(String nreBecomResidentChgFlag) {
        this.nreBecomResidentChgFlag = nreBecomResidentChgFlag;
    }
}
