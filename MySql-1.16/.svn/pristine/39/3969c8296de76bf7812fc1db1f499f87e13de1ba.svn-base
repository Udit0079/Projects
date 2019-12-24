/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ankit Verma
 */
public class CustGeneralInformation {

    private CustomerDetail customerDetail;
    String introCusId;
    private String titleType2;
    List<SelectItem> titleType2Option;
    private String introName;
    private String introAdd1;
    private String introAdd2;
    private String introVill;
    private String introBlock;
    String introState;
    String introCity;
    String introCountry;
    String introPhone;
    List<SelectItem> introStateOption;
    List<SelectItem> introCityOption;
    List<SelectItem> introCountryOption;
    String introPostal;
    String introTelex;
    String introFax;
    String custFinanYear;
    String custFinanMon;
    String currencyCode;
    List<SelectItem> currencyCodeOption;
    String businessAssests;
    String propAssets;
    String investment;
    String netWorth;
    String deposits;
    String otherBnkCode;
    String limWithOtherBnk;
    String funfBasedLim;
    String nonFundBasedLim;
    String offLinecustDebitLim;
    String salary2;
    Date custFinandate;
    String creditCard;
    String cardNo;
    String bnkName;
    boolean flagForCreditCard;
    String accNo;
    String branchname;
    String cardIssuer;
    List<SelectItem> creditCardOption;
    CustomerMasterDelegate masterDelegate;

    public CustGeneralInformation() {
        initializeForm();
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public String getIntroCusId() {
        return introCusId;
    }

    public void setIntroCusId(String introCusId) {
        this.introCusId = introCusId;
    }

    public String getIntroAdd1() {
        return introAdd1;
    }

    public void setIntroAdd1(String introAdd1) {
        this.introAdd1 = introAdd1;
    }

    public String getIntroAdd2() {
        return introAdd2;
    }

    public void setIntroAdd2(String introAdd2) {
        this.introAdd2 = introAdd2;
    }

    public String getIntroBlock() {
        return introBlock;
    }

    public void setIntroBlock(String introBlock) {
        this.introBlock = introBlock;
    }

    public String getIntroCity() {
        return introCity;
    }

    public void setIntroCity(String introCity) {
        this.introCity = introCity;
    }

    public List<SelectItem> getIntroCityOption() {
        return introCityOption;
    }

    public void setIntroCityOption(List<SelectItem> introCityOption) {
        this.introCityOption = introCityOption;
    }

    public String getIntroCountry() {
        return introCountry;
    }

    public void setIntroCountry(String introCountry) {
        this.introCountry = introCountry;
    }

    public List<SelectItem> getIntroCountryOption() {
        return introCountryOption;
    }

    public void setIntroCountryOption(List<SelectItem> introCountryOption) {
        this.introCountryOption = introCountryOption;
    }

    public String getIntroName() {
        return introName;
    }

    public void setIntroName(String introName) {
        this.introName = introName;
    }

    public String getIntroPostal() {
        return introPostal;
    }

    public void setIntroPostal(String introPostal) {
        this.introPostal = introPostal;
    }

    public String getIntroState() {
        return introState;
    }

    public void setIntroState(String introState) {
        this.introState = introState;
    }

    public List<SelectItem> getIntroStateOption() {
        return introStateOption;
    }

    public void setIntroStateOption(List<SelectItem> introStateOption) {
        this.introStateOption = introStateOption;
    }

    public String getIntroVill() {
        return introVill;
    }

    public void setIntroVill(String introVill) {
        this.introVill = introVill;
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

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBusinessAssests() {
        return businessAssests;
    }

    public void setBusinessAssests(String businessAssests) {
        this.businessAssests = businessAssests;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public List<SelectItem> getCreditCardOption() {
        return creditCardOption;
    }

    public void setCreditCardOption(List<SelectItem> creditCardOption) {
        this.creditCardOption = creditCardOption;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<SelectItem> getCurrencyCodeOption() {
        return currencyCodeOption;
    }

    public void setCurrencyCodeOption(List<SelectItem> currencyCodeOption) {
        this.currencyCodeOption = currencyCodeOption;
    }

    public String getCustFinanMon() {
        return custFinanMon;
    }

    public void setCustFinanMon(String custFinanMon) {
        this.custFinanMon = custFinanMon;
    }

    public String getCustFinanYear() {
        return custFinanYear;
    }

    public void setCustFinanYear(String custFinanYear) {
        this.custFinanYear = custFinanYear;
    }

    public Date getCustFinandate() {
        return custFinandate;
    }

    public void setCustFinandate(Date custFinandate) {
        this.custFinandate = custFinandate;
    }

    public String getDeposits() {
        return deposits;
    }

    public void setDeposits(String deposits) {
        this.deposits = deposits;
    }

    public boolean isFlagForCreditCard() {
        return flagForCreditCard;
    }

    public void setFlagForCreditCard(boolean flagForCreditCard) {
        this.flagForCreditCard = flagForCreditCard;
    }

    public String getFunfBasedLim() {
        return funfBasedLim;
    }

    public void setFunfBasedLim(String funfBasedLim) {
        this.funfBasedLim = funfBasedLim;
    }

    public String getIntroFax() {
        return introFax;
    }

    public void setIntroFax(String introFax) {
        this.introFax = introFax;
    }

    public String getIntroPhone() {
        return introPhone;
    }

    public void setIntroPhone(String introPhone) {
        this.introPhone = introPhone;
    }

    public String getIntroTelex() {
        return introTelex;
    }

    public void setIntroTelex(String introTelex) {
        this.introTelex = introTelex;
    }

    public String getInvestment() {
        return investment;
    }

    public void setInvestment(String investment) {
        this.investment = investment;
    }

    public String getLimWithOtherBnk() {
        return limWithOtherBnk;
    }

    public void setLimWithOtherBnk(String limWithOtherBnk) {
        this.limWithOtherBnk = limWithOtherBnk;
    }

    public String getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(String netWorth) {
        this.netWorth = netWorth;
    }

    public String getNonFundBasedLim() {
        return nonFundBasedLim;
    }

    public void setNonFundBasedLim(String nonFundBasedLim) {
        this.nonFundBasedLim = nonFundBasedLim;
    }

    public String getOffLinecustDebitLim() {
        return offLinecustDebitLim;
    }

    public void setOffLinecustDebitLim(String offLinecustDebitLim) {
        this.offLinecustDebitLim = offLinecustDebitLim;
    }

    public String getOtherBnkCode() {
        return otherBnkCode;
    }

    public void setOtherBnkCode(String otherBnkCode) {
        this.otherBnkCode = otherBnkCode;
    }

    public String getPropAssets() {
        return propAssets;
    }

    public void setPropAssets(String propAssets) {
        this.propAssets = propAssets;
    }

    public String getSalary2() {
        return salary2;
    }

    public void setSalary2(String salary2) {
        this.salary2 = salary2;
    }

    public void onblurcustIntroId() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher introCustIdCheck = p.matcher(this.introCusId);
            if (!introCustIdCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please enter numeric value in intro Customer Id.");
                return;
            }
            CBSCustomerMasterDetailTO custDetailsByCustIdTo = masterDelegate.getCustDetailsByCustId(introCusId);
            getCustomerDetail().setErrorMsg("");
            this.setTitleType2(custDetailsByCustIdTo.getTitle());
            this.setIntroName(custDetailsByCustIdTo.getCustname());
            this.setIntroAdd1(custDetailsByCustIdTo.getMailAddressLine1());
            this.setIntroAdd2(custDetailsByCustIdTo.getMailAddressLine2());
            this.setIntroVill(custDetailsByCustIdTo.getMailVillage());
            this.setIntroBlock(custDetailsByCustIdTo.getMailBlock());
            this.setIntroCity(custDetailsByCustIdTo.getMailCityCode());
            this.setIntroState(custDetailsByCustIdTo.getMailStateCode());
            this.setIntroPostal(custDetailsByCustIdTo.getMailPostalCode());
            this.setIntroCountry(custDetailsByCustIdTo.getMailCountryCode());
            this.setIntroPhone(custDetailsByCustIdTo.getMailPhoneNumber());
            this.setIntroTelex(custDetailsByCustIdTo.getMailTelexNumber());
            this.setIntroFax(custDetailsByCustIdTo.getMailFaxNumber());

        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String onblurIntroName() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if(introName!=null)
        {
             Matcher introNameCheck = p.matcher(this.introName);
        if (introNameCheck.matches()) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Introducer Name.");
            return "Please Enter Non Numeric Value in Introducer Name.";
        } else if (this.introName.contains("0") || this.introName.contains("1") || this.introName.contains("2") || this.introName.contains("3") || this.introName.contains("4") || this.introName.contains("5")
                || this.introName.contains("6") || this.introName.contains("7") || this.introName.contains("8") || this.introName.contains("9")) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Introducer Name.");
            return "Please Enter Non Numeric Value in Introducer Name.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
        }
       return "true";
    }

    public String onblurIntroPostalCode() {
        if (introPostal!=null&&!this.introPostal.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher introPoatalCheck = p.matcher(this.introPostal);
            if (!introPoatalCheck.matches()) {
                return "Please Enter Numeric Value in Introducer Postal Code.";
            }
        }
        return "true";
    }

    public String onblurIntroPhone() {
        if (introPhone!=null&&!this.introPhone.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher introPhoneCheck = p.matcher(this.introPhone);
            if (!introPhoneCheck.matches()) {
                return "Please Enter Numeric Value in Introducer Phone No.";
            }
        }
        return "true";
    }

    public String onblurIntroTelex() {
        if (introTelex!=null&&!this.introTelex.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher introTelexCheck = p.matcher(this.introTelex);
            if (!introTelexCheck.matches()) {
                return "Please Enter Numeric Value in Introducer Telex No.";
            }
        }
        return "true";
    }

    public String onblurIntroFax() {
        if (introFax!=null&&!this.introFax.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher introFaxCheck = p.matcher(this.introFax);
            if (!introFaxCheck.matches()) {
                return "Please Enter Numeric Value in Introducer Fax No.";
            }
        }
        return "true";
    }

    public String onblurFinYear() {
        if (custFinanYear!=null&&!this.custFinanYear.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher custFinanYearCheck = p.matcher(this.custFinanYear);
            if (!custFinanYearCheck.matches()) {
                return "Please Enter Numeric Value in Customer Financial Year.";

            }
        }
        return "true";
    }

    public String onblurFinMonth() {
        if (custFinanMon!=null&&!this.custFinanMon.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher custFinanMonthCheck = p.matcher(this.custFinanMon);
            if (!custFinanMonthCheck.matches()) {
                return "Please Enter Numeric Value in Customer Financial Month.";

            }
        }
        return "true";
    }

    public void onblurCreditCard() {
        if (this.creditCard.equalsIgnoreCase("Y")) {
            flagForCreditCard = false;
        } else {
            flagForCreditCard = true;
        }
    }

    public String onblurBankName() {
        if(bnkName!=null&&!bnkName.equalsIgnoreCase(""))
        {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher bankNameCheck = p.matcher(this.bnkName);
        if (bankNameCheck.matches()) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Bank Name.");
            return "Please Enter Non Numeric Value in Bank Name.";
        } else if (this.bnkName.contains("0") || this.bnkName.contains("1") || this.bnkName.contains("2") || this.bnkName.contains("3") || this.bnkName.contains("4") || this.bnkName.contains("5")
                || this.bnkName.contains("6") || this.bnkName.contains("7") || this.bnkName.contains("8") || this.bnkName.contains("9")) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Bank Name.");
            return "Please Enter Non Numeric Value in Bank Name.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
        }
        return "true";
    }

    public String onblurBranchName() {
        if(branchname!=null&&!branchname.equalsIgnoreCase(""))
        {
             Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher branchNameCheck = p.matcher(this.branchname);
        if (branchNameCheck.matches()) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Branch Name.");
            return "Please Enter Non Numeric Value in Branch Name.";
        } else if (this.branchname.contains("0") || this.branchname.contains("1") || this.branchname.contains("2") || this.branchname.contains("3") || this.branchname.contains("4") || this.branchname.contains("5")
                || this.branchname.contains("6") || this.branchname.contains("7") || this.branchname.contains("8") || this.branchname.contains("9")) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in Branch Name.");
            return "Please Enter Non Numeric Value in Branch Name.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
        }
       return "true";
    }

    public void initializeForm() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            titleType2Option = new ArrayList<SelectItem>();
            titleType2Option.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> functionList = masterDelegate.getFunctionValues("045");
            for (CbsRefRecTypeTO recTypeTO : functionList) {
                titleType2Option.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            creditCardOption = new ArrayList<SelectItem>();
            creditCardOption.add(new SelectItem("0", "--Select--"));
            creditCardOption.add(new SelectItem("Y", "Yes"));
            creditCardOption.add(new SelectItem("N", "No"));
            introCityOption = new ArrayList<SelectItem>();
            introCityOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> introducerCityList = masterDelegate.getFunctionValues("001");
            for (CbsRefRecTypeTO recTypeTO : introducerCityList) {
                introCityOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            introStateOption = new ArrayList<SelectItem>();
            introStateOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> introStateOptionList = masterDelegate.getFunctionValues("002");
            for (CbsRefRecTypeTO recTypeTO : introStateOptionList) {
                introStateOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            introCountryOption = new ArrayList<SelectItem>();
            introCountryOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> introCountryOptionList = masterDelegate.getFunctionValues("003");
            for (CbsRefRecTypeTO recTypeTO : introCountryOptionList) {
                introCountryOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            currencyCodeOption = new ArrayList<SelectItem>();
            currencyCodeOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> currencyCodeOptionList = masterDelegate.getFunctionValues("176");
            for (CbsRefRecTypeTO recTypeTO : currencyCodeOptionList) {
                introCountryOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectFieldValues() {
        try {
            CBSCustomerMasterDetailTO custTO = masterDelegate.getCustDetailsByCustId(getCustomerDetail().getCustId());
            if (custTO.getIntroCustomerId() == null || custTO.getIntroCustomerId().equalsIgnoreCase("")) {
                this.setIntroCusId("");
            } else {
                this.setIntroCusId(custTO.getIntroCustomerId());
            }
            if (custTO.getCustTitle() == null || custTO.getCustTitle().equalsIgnoreCase("")) {
                this.setTitleType2("0");
            } else {
                this.setTitleType2(custTO.getCustTitle());
            }
            if (custTO.getName() == null || custTO.getName().equalsIgnoreCase("")) {
                this.setIntroName("");
            } else {
                this.setIntroName(custTO.getName());
            }
            if (custTO.getAddressLine1() == null || custTO.getAddressLine1().equalsIgnoreCase("")) {
                this.setIntroAdd1("");
            } else {
                this.setIntroAdd1(custTO.getAddressLine1());
            }
            if (custTO.getAddressLine2() == null || custTO.getAddressLine2().equalsIgnoreCase("")) {
                this.setIntroAdd2("");
            } else {
                this.setIntroAdd2(custTO.getAddressLine2());
            }
            if (custTO.getVillage() == null || custTO.getVillage().equalsIgnoreCase("")) {
                this.setIntroVill("");
            } else {
                this.setIntroVill(custTO.getVillage());
            }
            if (custTO.getBlock() == null || custTO.getBlock().equalsIgnoreCase("")) {
                this.setIntroBlock("");
            } else {
                this.setIntroBlock(custTO.getBlock());
            }
            if (custTO.getCityCode() == null || custTO.getCityCode().equalsIgnoreCase("")) {
                this.setIntroCity("0");
            } else {
                this.setIntroCity(custTO.getCityCode());
            }
            if (custTO.getStateCode() == null || custTO.getStateCode().equalsIgnoreCase("")) {
                this.setIntroState("0");
            } else {
                this.setIntroState(custTO.getStateCode());
            }
            if (custTO.getPostalCode() == null || custTO.getPostalCode().equalsIgnoreCase("")) {
                this.setIntroPostal("");
            } else {
                this.setIntroPostal(custTO.getPostalCode());
            }
            if (custTO.getCountryCode() == null || custTO.getCountryCode().equalsIgnoreCase("")) {
                this.setIntroCountry("0");
            } else {
                this.setIntroCountry(custTO.getCountryCode());
            }
            if (custTO.getPhoneNumber() == null || custTO.getPhoneNumber().equalsIgnoreCase("")) {
                this.setIntroPhone("");
            } else {
                this.setIntroPhone(custTO.getPhoneNumber());
            }
            if (custTO.getTelexNumber() == null || custTO.getTelexNumber().equalsIgnoreCase("")) {
                this.setIntroTelex("");
            } else {
                this.setIntroTelex(custTO.getTelexNumber());
            }
            if (custTO.getFaxNumber() == null || custTO.getFaxNumber().equalsIgnoreCase("")) {
                this.setIntroFax("");
            } else {
                this.setIntroFax(custTO.getFaxNumber());
            }
            if (custTO.getCustFinancialDetails() == null || custTO.getCustFinancialDetails().equalsIgnoreCase("")) {
                this.setCustFinanYear("");
            } else {
                this.setCustFinanYear(custTO.getCustFinancialDetails());
            }
            if (custTO.getFinancialYearAndMonth() == null || custTO.getFinancialYearAndMonth().equalsIgnoreCase("")) {
                this.setCustFinanMon("");
            } else {
                this.setCustFinanMon(custTO.getFinancialYearAndMonth());
            }
            if (custTO.getCurrencyCodeType() == null || custTO.getCurrencyCodeType().equalsIgnoreCase("")) {
                this.setCurrencyCode("0");
            } else {
                this.setCurrencyCode(custTO.getCurrencyCodeType());
            }
            if (custTO.getPropertyAssets() == null) {
                this.setPropAssets("0.0");
            } else {
                this.setPropAssets(custTO.getPropertyAssets().toString());
            }
            if (custTO.getBusinessAssets() == null) {
                this.setBusinessAssests("");
            } else {
                this.setBusinessAssests(custTO.getBusinessAssets().toString());
            }
            if (custTO.getInvestments() == null) {
                this.setInvestment("");
            } else {
                this.setInvestment(custTO.getInvestments().toString());
            }
            if (custTO.getNetworth() == null) {
                this.setNetWorth("0.0");
            } else {
                this.setNetWorth(custTO.getNetworth().toString());
            }
            if (custTO.getDeposits() == null) {
                this.setDeposits("0.0");
            } else {
                this.setDeposits(custTO.getDeposits().toString());
            }
            if (custTO.getOtherBankCode() == null || custTO.getOtherBankCode().equalsIgnoreCase("")) {
                this.setOtherBnkCode("");
            } else {
                this.setOtherBnkCode(custTO.getOtherBankCode());
            }
            if (custTO.getLimitsWithOtherBank() == null) {
                this.setLimWithOtherBnk("0.0");
            } else {
                this.setLimWithOtherBnk(custTO.getLimitsWithOtherBank().toString());
            }
            if (custTO.getFundBasedLimit() == null) {
                this.setFunfBasedLim("0.0");
            } else {
                this.setFunfBasedLim(custTO.getFundBasedLimit().toString());
            }
            if (custTO.getNonFundBasedLimit() == null) {
                this.setNonFundBasedLim("0.0");
            } else {
                this.setNonFundBasedLim(custTO.getNonFundBasedLimit().toString());
            }
            if (custTO.getOffLineCustDebitLimit() == null) {
                this.setOffLinecustDebitLim("0.0");
            } else {
                this.setOffLinecustDebitLim(custTO.getOffLineCustDebitLimit().toString());
            }
            if (custTO.getSalary() == null) {
                this.setSalary2("0.0");
            } else {
                this.setSalary2(custTO.getSalary().toString());
            }
            if (custTO.getCustFinancialDate() == null) {
                this.setCustFinandate(new Date());
            } else {
                this.setCustFinandate(custTO.getCustFinancialDate());
            }
            if (custTO.getCreditCard() == null || custTO.getCreditCard().equalsIgnoreCase("")) {
                this.setCreditCard("0");
            } else {
                this.setCreditCard(custTO.getCreditCard());
            }
            if (custTO.getCardNumber() == null || custTO.getCardNumber().equalsIgnoreCase("")) {
                this.setCardNo("");
            } else {
                this.setCardNo(custTO.getCardNumber());
            }

            if (custTO.getCardIssuer() == null || custTO.getCardIssuer().equalsIgnoreCase("")) {
                this.setCardIssuer("");
            } else {
                this.setCardIssuer(custTO.getCardIssuer());
            }
            if (custTO.getBankName() == null || custTO.getBankName().equalsIgnoreCase("")) {
                this.setBnkName("");
            } else {
                this.setBnkName(custTO.getBankName());
            }
            if (custTO.getAcctId() == null || custTO.getAcctId().equalsIgnoreCase("")) {
                this.setAccNo("");
            } else {
                this.setAccNo(custTO.getAcctId());
            }
            if (custTO.getBranchName() == null || custTO.getBranchName().equalsIgnoreCase("")) {
                this.setBranchname("");
            } else {
                this.setBranchname(custTO.getBranchName());
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getMessage());
        }


    }

    public void refreshForm() {
        this.setIntroCusId("");
        this.setTitleType2("0");

        this.setIntroName("");
        this.setIntroAdd1("");
        this.setIntroAdd2("");
        this.setIntroVill("");

        this.setIntroBlock("");
        this.setIntroCity("0");
        this.setIntroState("0");
        this.setIntroPostal("");

        this.setIntroCountry("0");
        this.setIntroPhone("");
        this.setIntroTelex("");
        this.setIntroFax("");

        this.setCustFinanYear("");
        this.setCustFinanMon("");
        this.setCurrencyCode("0");
        this.setOtherBnkCode("");
        this.setCreditCard("0");
        this.setCardNo("");
        this.setCardIssuer("");
        this.setBnkName("");
        this.setAccNo("");
        this.setBranchname("");

        this.setBusinessAssests("0.0");
        this.setPropAssets("0.0");
        this.setInvestment("0.0");
        this.setNetWorth("0.0");
        this.setDeposits("0.0");
        this.setLimWithOtherBnk("0.0");
        this.setFunfBasedLim("0.0");
        this.setNonFundBasedLim("0.0");
        this.setOffLinecustDebitLim("0.0");
        this.setSalary2("0.0");

    }

    public String validation() {
        String s8 = onblurIntroName();
        if (!s8.equalsIgnoreCase("true")) {
            return s8;
        }
        String msg1 = onblurIntroPostalCode();
        if (!msg1.equalsIgnoreCase("true")) {
            return msg1;
        }
        String msg2 = onblurIntroPhone();
        if (!msg2.equalsIgnoreCase("true")) {
            return msg2;
        }
        String msg3 = onblurIntroTelex();
        if (!msg3.equalsIgnoreCase("true")) {
            return msg3;
        }
        String msg4 = onblurIntroFax();
        if (!msg4.equalsIgnoreCase("true")) {
            return msg4;
        }

        String s9 = onblurBankName();
        if (!s9.equalsIgnoreCase("true")) {
            return s9;
        }

        String s10 = onblurBranchName();
        if (!s10.equalsIgnoreCase("true")) {
            return s10;
        }

        return "ok";
    }
}
