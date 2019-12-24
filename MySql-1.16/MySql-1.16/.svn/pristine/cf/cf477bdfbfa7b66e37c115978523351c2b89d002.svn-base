/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.customer.CBSTradeFinanceInformationTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
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
 * @author Ankit Verma
 */
public class TradeFinanceInfo {

    CustomerDetail customerDetail;
    CustomerMasterDelegate masterDelegate;
    String tfi1;
    String tfi2;
    String tfiName;
    String tfiAdd1;
    String tfiAdd2;
    String tfiPostal;
    String tfiPhone;
    String tfiFax;
    String tfiTelex;
    String tfiNative;
    String tfiPresentOutStandLiab;
    String tfiPartyType;
    String tfiProdCycle;
    String tfiCodeGivenByCentralBnk;
    String tfiCodeGivenByCentralAuth;
    String cityForTFI;
    String countryForTFI;
    String inlandTradeAllowed;
    List<SelectItem> inlandTradeAllowedOption;
    Date reviewDate;
    String industryType;
    List<SelectItem> industryTypeOption;
    String typeExpImp;
    List<SelectItem> typeExpImpOption;
    List<SelectItem> cityForTFIOption;
    List<SelectItem> countryForTFIOption;
    String exportUnit;
    List<SelectItem> exportUnitOption;
    String statusTFI;
    List<SelectItem> statusTFIOption;
    String partyConstitution;
    List<SelectItem> partyConstitutionOption;
    String specialParty;
    List<SelectItem> specialPartyOption;
    String tradeExpoCode;
    List<SelectItem> tradeExpoCodeOption;
    String dcMargin100;
    String dcSactioningAuth;
    String dcNextNoCode;
    String dcCreditLim;
    String fcMargin100;
    String fcSactioningAuth;
    String fcNextNoCode;
    String fcCreditLim;
    String pcMargin100;
    String pcSactioningAuth;
    String pcNextNoCode;
    String pcCreditLim;
    String bgMargin100;
    String bgSactioningAuth;
    String bgNextNoCode;
    String bgCreditLim;
    Date fcSancExpDate;
    Date pcSancExpDate;
    Date dcSancExpDate;
    Date bgSancExpDate;
    Date date = new Date();

    /** Creates a new instance of TradeFinanceInfo */
    public TradeFinanceInfo() {
        initializeForm();
    }

    public Date getBgSancExpDate() {
        return bgSancExpDate;
    }

    public void setBgSancExpDate(Date bgSancExpDate) {
        this.bgSancExpDate = bgSancExpDate;
    }

    public Date getFcSancExpDate() {
        return fcSancExpDate;
    }

    public void setFcSancExpDate(Date fcSancExpDate) {
        this.fcSancExpDate = fcSancExpDate;
    }

    public CustomerMasterDelegate getMasterDelegate() {
        return masterDelegate;
    }

    public void setMasterDelegate(CustomerMasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    public Date getPcSancExpDate() {
        return pcSancExpDate;
    }

    public void setPcSancExpDate(Date pcSancExpDate) {
        this.pcSancExpDate = pcSancExpDate;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public String getTfi1() {
        return tfi1;
    }

    public void setTfi1(String tfi1) {
        this.tfi1 = tfi1;
    }

    public String getTfi2() {
        return tfi2;
    }

    public void setTfi2(String tfi2) {
        this.tfi2 = tfi2;
    }

    public String getTfiAdd1() {
        return tfiAdd1;
    }

    public void setTfiAdd1(String tfiAdd1) {
        this.tfiAdd1 = tfiAdd1;
    }

    public String getTfiAdd2() {
        return tfiAdd2;
    }

    public void setTfiAdd2(String tfiAdd2) {
        this.tfiAdd2 = tfiAdd2;
    }

    public String getTfiCodeGivenByCentralAuth() {
        return tfiCodeGivenByCentralAuth;
    }

    public void setTfiCodeGivenByCentralAuth(String tfiCodeGivenByCentralAuth) {
        this.tfiCodeGivenByCentralAuth = tfiCodeGivenByCentralAuth;
    }

    public String getTfiCodeGivenByCentralBnk() {
        return tfiCodeGivenByCentralBnk;
    }

    public void setTfiCodeGivenByCentralBnk(String tfiCodeGivenByCentralBnk) {
        this.tfiCodeGivenByCentralBnk = tfiCodeGivenByCentralBnk;
    }

    public String getTfiFax() {
        return tfiFax;
    }

    public void setTfiFax(String tfiFax) {
        this.tfiFax = tfiFax;
    }

    public String getTfiName() {
        return tfiName;
    }

    public void setTfiName(String tfiName) {
        this.tfiName = tfiName;
    }

    public String getTfiNative() {
        return tfiNative;
    }

    public void setTfiNative(String tfiNative) {
        this.tfiNative = tfiNative;
    }

    public String getTfiPartyType() {
        return tfiPartyType;
    }

    public void setTfiPartyType(String tfiPartyType) {
        this.tfiPartyType = tfiPartyType;
    }

    public String getTfiPhone() {
        return tfiPhone;
    }

    public void setTfiPhone(String tfiPhone) {
        this.tfiPhone = tfiPhone;
    }

    public String getTfiPostal() {
        return tfiPostal;
    }

    public void setTfiPostal(String tfiPostal) {
        this.tfiPostal = tfiPostal;
    }

    public String getTfiPresentOutStandLiab() {
        return tfiPresentOutStandLiab;
    }

    public void setTfiPresentOutStandLiab(String tfiPresentOutStandLiab) {
        this.tfiPresentOutStandLiab = tfiPresentOutStandLiab;
    }

    public String getTfiProdCycle() {
        return tfiProdCycle;
    }

    public void setTfiProdCycle(String tfiProdCycle) {
        this.tfiProdCycle = tfiProdCycle;
    }

    public String getTfiTelex() {
        return tfiTelex;
    }

    public void setTfiTelex(String tfiTelex) {
        this.tfiTelex = tfiTelex;
    }

    public String getCityForTFI() {
        return cityForTFI;
    }

    public void setCityForTFI(String cityForTFI) {
        this.cityForTFI = cityForTFI;
    }

    public String getCountryForTFI() {
        return countryForTFI;
    }

    public void setCountryForTFI(String countryForTFI) {
        this.countryForTFI = countryForTFI;
    }

    public String getBgCreditLim() {
        return bgCreditLim;
    }

    public void setBgCreditLim(String bgCreditLim) {
        this.bgCreditLim = bgCreditLim;
    }

    public String getBgMargin100() {
        return bgMargin100;
    }

    public void setBgMargin100(String bgMargin100) {
        this.bgMargin100 = bgMargin100;
    }

    public String getBgNextNoCode() {
        return bgNextNoCode;
    }

    public void setBgNextNoCode(String bgNextNoCode) {
        this.bgNextNoCode = bgNextNoCode;
    }

    public String getBgSactioningAuth() {
        return bgSactioningAuth;
    }

    public void setBgSactioningAuth(String bgSactioningAuth) {
        this.bgSactioningAuth = bgSactioningAuth;
    }

    public List<SelectItem> getCityForTFIOption() {
        return cityForTFIOption;
    }

    public void setCityForTFIOption(List<SelectItem> cityForTFIOption) {
        this.cityForTFIOption = cityForTFIOption;
    }

    public List<SelectItem> getCountryForTFIOption() {
        return countryForTFIOption;
    }

    public void setCountryForTFIOption(List<SelectItem> countryForTFIOption) {
        this.countryForTFIOption = countryForTFIOption;
    }

    public String getDcCreditLim() {
        return dcCreditLim;
    }

    public void setDcCreditLim(String dcCreditLim) {
        this.dcCreditLim = dcCreditLim;
    }

    public String getDcMargin100() {
        return dcMargin100;
    }

    public void setDcMargin100(String dcMargin100) {
        this.dcMargin100 = dcMargin100;
    }

    public String getDcNextNoCode() {
        return dcNextNoCode;
    }

    public void setDcNextNoCode(String dcNextNoCode) {
        this.dcNextNoCode = dcNextNoCode;
    }

    public String getDcSactioningAuth() {
        return dcSactioningAuth;
    }

    public void setDcSactioningAuth(String dcSactioningAuth) {
        this.dcSactioningAuth = dcSactioningAuth;
    }

    public String getExportUnit() {
        return exportUnit;
    }

    public void setExportUnit(String exportUnit) {
        this.exportUnit = exportUnit;
    }

    public List<SelectItem> getExportUnitOption() {
        return exportUnitOption;
    }

    public void setExportUnitOption(List<SelectItem> exportUnitOption) {
        this.exportUnitOption = exportUnitOption;
    }

    public String getFcCreditLim() {
        return fcCreditLim;
    }

    public void setFcCreditLim(String fcCreditLim) {
        this.fcCreditLim = fcCreditLim;
    }

    public String getFcMargin100() {
        return fcMargin100;
    }

    public void setFcMargin100(String fcMargin100) {
        this.fcMargin100 = fcMargin100;
    }

    public String getFcNextNoCode() {
        return fcNextNoCode;
    }

    public void setFcNextNoCode(String fcNextNoCode) {
        this.fcNextNoCode = fcNextNoCode;
    }

    public String getFcSactioningAuth() {
        return fcSactioningAuth;
    }

    public void setFcSactioningAuth(String fcSactioningAuth) {
        this.fcSactioningAuth = fcSactioningAuth;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public List<SelectItem> getIndustryTypeOption() {
        return industryTypeOption;
    }

    public void setIndustryTypeOption(List<SelectItem> industryTypeOption) {
        this.industryTypeOption = industryTypeOption;
    }

    public String getInlandTradeAllowed() {
        return inlandTradeAllowed;
    }

    public void setInlandTradeAllowed(String inlandTradeAllowed) {
        this.inlandTradeAllowed = inlandTradeAllowed;
    }

    public List<SelectItem> getInlandTradeAllowedOption() {
        return inlandTradeAllowedOption;
    }

    public void setInlandTradeAllowedOption(List<SelectItem> inlandTradeAllowedOption) {
        this.inlandTradeAllowedOption = inlandTradeAllowedOption;
    }

    public String getPartyConstitution() {
        return partyConstitution;
    }

    public void setPartyConstitution(String partyConstitution) {
        this.partyConstitution = partyConstitution;
    }

    public List<SelectItem> getPartyConstitutionOption() {
        return partyConstitutionOption;
    }

    public void setPartyConstitutionOption(List<SelectItem> partyConstitutionOption) {
        this.partyConstitutionOption = partyConstitutionOption;
    }

    public String getPcCreditLim() {
        return pcCreditLim;
    }

    public void setPcCreditLim(String pcCreditLim) {
        this.pcCreditLim = pcCreditLim;
    }

    public String getPcMargin100() {
        return pcMargin100;
    }

    public void setPcMargin100(String pcMargin100) {
        this.pcMargin100 = pcMargin100;
    }

    public String getPcNextNoCode() {
        return pcNextNoCode;
    }

    public void setPcNextNoCode(String pcNextNoCode) {
        this.pcNextNoCode = pcNextNoCode;
    }

    public String getPcSactioningAuth() {
        return pcSactioningAuth;
    }

    public void setPcSactioningAuth(String pcSactioningAuth) {
        this.pcSactioningAuth = pcSactioningAuth;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getSpecialParty() {
        return specialParty;
    }

    public void setSpecialParty(String specialParty) {
        this.specialParty = specialParty;
    }

    public List<SelectItem> getSpecialPartyOption() {
        return specialPartyOption;
    }

    public void setSpecialPartyOption(List<SelectItem> specialPartyOption) {
        this.specialPartyOption = specialPartyOption;
    }

    public String getStatusTFI() {
        return statusTFI;
    }

    public void setStatusTFI(String statusTFI) {
        this.statusTFI = statusTFI;
    }

    public List<SelectItem> getStatusTFIOption() {
        return statusTFIOption;
    }

    public void setStatusTFIOption(List<SelectItem> statusTFIOption) {
        this.statusTFIOption = statusTFIOption;
    }

    public String getTradeExpoCode() {
        return tradeExpoCode;
    }

    public void setTradeExpoCode(String tradeExpoCode) {
        this.tradeExpoCode = tradeExpoCode;
    }

    public List<SelectItem> getTradeExpoCodeOption() {
        return tradeExpoCodeOption;
    }

    public void setTradeExpoCodeOption(List<SelectItem> tradeExpoCodeOption) {
        this.tradeExpoCodeOption = tradeExpoCodeOption;
    }

    public String getTypeExpImp() {
        return typeExpImp;
    }

    public void setTypeExpImp(String typeExpImp) {
        this.typeExpImp = typeExpImp;
    }

    public List<SelectItem> getTypeExpImpOption() {
        return typeExpImpOption;
    }

    public void setTypeExpImpOption(List<SelectItem> typeExpImpOption) {
        this.typeExpImpOption = typeExpImpOption;
    }

    public Date getDcSancExpDate() {
        return dcSancExpDate;
    }

    public void setDcSancExpDate(Date dcSancExpDate) {
        this.dcSancExpDate = dcSancExpDate;
    }

    public String onblurTFIName() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher tfiNameCheck = p.matcher(this.tfiName);
        if (tfiNameCheck.matches()) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in TFI Name.");
            return "Please Enter Non Numeric Value in TFI Name.";
        } else if (this.tfiName.contains("0") || this.tfiName.contains("1") || this.tfiName.contains("2") || this.tfiName.contains("3") || this.tfiName.contains("4") || this.tfiName.contains("5")
                || this.tfiName.contains("6") || this.tfiName.contains("7") || this.tfiName.contains("8") || this.tfiName.contains("9")) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in TFI Name.");
            return "Please Enter Non Numeric Value in TFI Name.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
    }

    public boolean onblurTFIFaxNo() {
        if (!this.tfiFax.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher tfiFaxNoCheck = p.matcher(this.tfiFax);
            if (!tfiFaxNoCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in TFI Fax No.");
                return false;
            } else {
                getCustomerDetail().setErrorMsg("");
                return true;
            }
        }
        return true;
    }

    public boolean onblurTFITelNo() {
        if (!this.tfiPhone.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher tfiTelNoCheck = p.matcher(this.tfiPhone);
            if (!tfiTelNoCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in TFI Phone No.");
                return false;
            } else {
                getCustomerDetail().setErrorMsg("");
                return true;
            }
        }
        return true;
    }

    public void onblurTFITelexNo() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher tfiTelexNoCheck = p.matcher(this.tfiTelex);
        if (!tfiTelexNoCheck.matches()) {
            getCustomerDetail().setErrorMsg("Please Enter Numeric Value in TFI Telex No.");
            return;
        } else {
            getCustomerDetail().setErrorMsg("");
        }
    }

    public String onblurTFINativeNo() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher tfiNativeCheck = p.matcher(this.tfiNative);
        if (tfiNativeCheck.matches()) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in TFI Native.");
            return "Please Enter Non Numeric Value in TFI Native.";
        } else if (this.tfiNative.contains("0") || this.tfiNative.contains("1") || this.tfiNative.contains("2") || this.tfiNative.contains("3") || this.tfiNative.contains("4") || this.tfiNative.contains("5")
                || this.tfiNative.contains("6") || this.tfiNative.contains("7") || this.tfiNative.contains("8") || this.tfiNative.contains("9")) {
            getCustomerDetail().setErrorMsg("Please Enter Non Numeric Value in TFI Native.");
            return "Please Enter Non Numeric Value in TFI Native.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
    }

    public String onblurDcMarginPer() {
        if (this.dcMargin100.contains("%")) {
            getCustomerDetail().setErrorMsg("% Mark is not excepted in DC Margin%.");
            return "% Mark is not excepted in DC Margin%.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
    }

    public String onblurFcMarginPer() {
        if (this.fcMargin100.contains("%")) {
            getCustomerDetail().setErrorMsg("% Mark is not excepted in FC Margin%.");
            return "% Mark is not excepted in FC Margin%.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
    }

    public String onblurBGMarginPer() {
        if (this.bgMargin100.contains("%")) {
            getCustomerDetail().setErrorMsg("% Mark is not excepted in BG Margin%.");
            return "% Mark is not excepted in BG Margin%.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
    }

    public String onblurPCMarginPer() {
        if (this.pcMargin100.contains("%")) {
            getCustomerDetail().setErrorMsg("% Mark is not excepted in PC Margin%.");
            return "% Mark is not excepted in PC Margin%.";
        } else {
            getCustomerDetail().setErrorMsg("");
            return "true";
        }
    }

    public boolean onblurTfiPostal() {
        if (!this.tfiPostal.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher tfiPostalCheck = p.matcher(this.tfiPostal);
            if (!tfiPostalCheck.matches()) {
                getCustomerDetail().setErrorMsg("Please Enter Numeric Value in TFI Postal Code.");
                return false;
            } else {
                getCustomerDetail().setErrorMsg("");
                return true;
            }
        }
        return true;
    }

    public void onblurReviewDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            long strDateDiff = CbsUtil.dayDiff(reviewDate, new Date());

            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can't select the date after the current date.");
                return;
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void onblurFcSancExpdate() {
        try {
            long strDateDiff = CbsUtil.dayDiff(fcSancExpDate, new Date());

            if (strDateDiff < 0) {
                if (strDateDiff < 0) {
                    getCustomerDetail().setErrorMsg("You can't select the date after the current date.");
                    return;
                } else {
                    getCustomerDetail().setErrorMsg("");
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void onblurPcSancExpdate() {
        try {
            long strDateDiff = CbsUtil.dayDiff(pcSancExpDate, new Date());
            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can't select the date after the current date.");
                return;
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());

        }
    }

    public void onblurBGSancExpdate() {
        try {
            long strDateDiff = CbsUtil.dayDiff(bgSancExpDate, new Date());
            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can't select the date after the current date.");
                return;
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void onblurDcSancExpDate() {
        try {
            long strDateDiff = CbsUtil.dayDiff(dcSancExpDate, new Date());
            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can't select the date after the current date.");
                return;
            } else {
                getCustomerDetail().setErrorMsg("");
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void initializeForm() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            inlandTradeAllowedOption = new ArrayList<SelectItem>();
            inlandTradeAllowedOption.add(new SelectItem("0", "--Select--"));
            inlandTradeAllowedOption.add(new SelectItem("Y", "Yes"));
            inlandTradeAllowedOption.add(new SelectItem("N", "No"));
            typeExpImpOption = new ArrayList<SelectItem>();
            typeExpImpOption.add(new SelectItem("0", "--Select--"));
            typeExpImpOption.add(new SelectItem("Y", "Yes"));
            typeExpImpOption.add(new SelectItem("N", "No"));
            exportUnitOption = new ArrayList<SelectItem>();
            exportUnitOption.add(new SelectItem("0", "--Select--"));
            exportUnitOption.add(new SelectItem("Y", "Yes"));
            exportUnitOption.add(new SelectItem("N", "No"));
            statusTFIOption = new ArrayList<SelectItem>();
            statusTFIOption.add(new SelectItem("0", "--Select--"));
            statusTFIOption.add(new SelectItem("I"));
            statusTFIOption.add(new SelectItem("E"));
            statusTFIOption.add(new SelectItem("B"));
            specialPartyOption = new ArrayList<SelectItem>();
            specialPartyOption.add(new SelectItem("0", "--Select--"));
            specialPartyOption.add(new SelectItem("Y", "Yes"));
            specialPartyOption.add(new SelectItem("N", "No"));
            tradeExpoCodeOption = new ArrayList<SelectItem>();
            tradeExpoCodeOption.add(new SelectItem("0", "--Select--"));
            tradeExpoCodeOption.add(new SelectItem("DC"));
            tradeExpoCodeOption.add(new SelectItem("BG"));
            tradeExpoCodeOption.add(new SelectItem("PC"));
            cityForTFIOption = new ArrayList<SelectItem>();
            cityForTFIOption.add(new SelectItem("0", "--Select--"));
            countryForTFIOption = new ArrayList<SelectItem>();
            countryForTFIOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> cityList = masterDelegate.getFunctionValues("001");
            for (CbsRefRecTypeTO recTypeTO : cityList) {
                cityForTFIOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            List<CbsRefRecTypeTO> countryList = masterDelegate.getFunctionValues("001");
            for (CbsRefRecTypeTO recTypeTO : countryList) {
                countryForTFIOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            industryTypeOption = new ArrayList<SelectItem>();
            industryTypeOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> industryTypeList = masterDelegate.getFunctionValues("018");
            for (CbsRefRecTypeTO recTypeTO : industryTypeList) {
                industryTypeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }
            partyConstitutionOption = new ArrayList<SelectItem>();
            partyConstitutionOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> partyConstitutionList = masterDelegate.getFunctionValues("044");
            for (CbsRefRecTypeTO recTypeTO : partyConstitutionList) {
                partyConstitutionOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectFieldValues() {
        try {
            CBSTradeFinanceInformationTO tdFInfoTO = masterDelegate.gettdFInfoByCustId(getCustomerDetail().getCustId());
            if (tdFInfoTO != null) {
                if (tdFInfoTO.getName() == null || tdFInfoTO.getName().equalsIgnoreCase("")) {
                    this.setTfiName("");
                } else {
                    this.setTfiName(tdFInfoTO.getName());
                }
                if (tdFInfoTO.getAddressLine1() == null || tdFInfoTO.getAddressLine1().equalsIgnoreCase("")) {
                    this.setTfiAdd1("");
                } else {
                    this.setTfiAdd1(tdFInfoTO.getAddressLine1());
                }
                if (tdFInfoTO.getAddressLine2() == null || tdFInfoTO.getAddressLine2().equalsIgnoreCase("")) {
                    this.setTfiAdd2("");
                } else {
                    this.setTfiAdd2(tdFInfoTO.getAddressLine2());
                }
                if (tdFInfoTO.getCityCode() == null || tdFInfoTO.getCityCode().equalsIgnoreCase("")) {
                    this.setCityForTFI("0");
                } else {
                    this.setCityForTFI(tdFInfoTO.getCityCode());
                }
                if (tdFInfoTO.getPostalCode() == null || tdFInfoTO.getPostalCode().equalsIgnoreCase("")) {
                    this.setTfiPostal("");
                } else {
                    this.setTfiPostal(tdFInfoTO.getPostalCode());
                }
                if (tdFInfoTO.getCountryCode() == null || tdFInfoTO.getCountryCode().equalsIgnoreCase("")) {
                    this.setCountryForTFI("0");
                } else {
                    this.setCountryForTFI(tdFInfoTO.getCountryCode());
                }
                if (tdFInfoTO.getPhoneNumber() == null || tdFInfoTO.getPhoneNumber().equalsIgnoreCase("")) {
                    this.setTfiPhone("");
                } else {
                    this.setTfiPhone(tdFInfoTO.getPhoneNumber());
                }
                if (tdFInfoTO.getFaxNumber() == null || tdFInfoTO.getFaxNumber().equalsIgnoreCase("")) {
                    this.setTfiFax("");
                } else {
                    this.setTfiFax(tdFInfoTO.getFaxNumber());
                }
                if (tdFInfoTO.getTelexNumber() == null || tdFInfoTO.getTelexNumber().equalsIgnoreCase("")) {
                    this.setTfiTelex("");
                } else {
                    this.setTfiTelex(tdFInfoTO.getTelexNumber());
                }
                if (tdFInfoTO.getNative1() == null || tdFInfoTO.getNative1().equalsIgnoreCase("")) {
                    this.setTfiNative("");
                } else {
                    this.setTfiNative(tdFInfoTO.getNative1());
                }
                if (tdFInfoTO.getInlandTradeAllowed() == null || tdFInfoTO.getInlandTradeAllowed().equalsIgnoreCase("")) {
                    this.setInlandTradeAllowed("0");
                } else {
                    this.setInlandTradeAllowed(tdFInfoTO.getInlandTradeAllowed());
                }
                if (tdFInfoTO.getReviewDate() == null) {
                    this.setReviewDate(date);
                } else {
                    this.setReviewDate(tdFInfoTO.getReviewDate());
                }
                if (tdFInfoTO.getIndustryType() == null || tdFInfoTO.getIndustryType().equalsIgnoreCase("")) {
                    this.setIndustryType("0");
                } else {
                    this.setIndustryType(tdFInfoTO.getIndustryType());
                }
                if (tdFInfoTO.getExportUnitFlag() == null || tdFInfoTO.getExportUnitFlag().equalsIgnoreCase("")) {
                    this.setExportUnit("0");
                } else {
                    this.setExportUnit(tdFInfoTO.getExportUnitFlag());
                }
                if (tdFInfoTO.getStatus() == null || tdFInfoTO.getStatus().equalsIgnoreCase("")) {
                    this.setStatusTFI("0");
                } else {
                    this.setStatusTFI(tdFInfoTO.getStatus());
                }
                if (tdFInfoTO.getTradeExpCode() == null || tdFInfoTO.getTradeExpCode().equalsIgnoreCase("")) {
                    this.setTradeExpoCode("");
                } else {
                    this.setTradeExpoCode(tdFInfoTO.getTradeExpCode());
                }
                if (tdFInfoTO.getPartyConstitution() == null || tdFInfoTO.getPartyConstitution().equalsIgnoreCase("")) {
                    this.setPartyConstitution("0");
                } else {
                    this.setPartyConstitution(tdFInfoTO.getPartyConstitution());
                }
                if (tdFInfoTO.getSpecialParty() == null || tdFInfoTO.getSpecialParty().equalsIgnoreCase("")) {
                    this.setSpecialParty("");
                } else {
                    this.setSpecialParty(tdFInfoTO.getSpecialParty());
                }
                if (tdFInfoTO.getPartyType() == null || tdFInfoTO.getPartyType().equalsIgnoreCase("")) {
                    this.setTfiPartyType("0");
                } else {
                    this.setTfiPartyType(tdFInfoTO.getPartyType());
                }

                if (tdFInfoTO.getProductionCycle() == null || tdFInfoTO.getProductionCycle().equalsIgnoreCase("")) {
                    this.setTfiProdCycle("");
                } else {
                    this.setTfiProdCycle(tdFInfoTO.getProductionCycle());
                }

                if (tdFInfoTO.getCodeGivenByCentralBANK() == null || tdFInfoTO.getCodeGivenByCentralBANK().equalsIgnoreCase("")) {
                    this.setTfiCodeGivenByCentralBnk("");
                } else {
                    this.setTfiCodeGivenByCentralBnk(tdFInfoTO.getCodeGivenByCentralBANK());
                }
                if (tdFInfoTO.getCodeGivenByTradeAuthority() == null || tdFInfoTO.getCodeGivenByTradeAuthority().equalsIgnoreCase("")) {
                    this.setTfiCodeGivenByCentralAuth("");
                } else {
                    this.setTfiCodeGivenByCentralAuth(tdFInfoTO.getCodeGivenByTradeAuthority());
                }
                if (tdFInfoTO.getType() == null || tdFInfoTO.getType().equalsIgnoreCase("")) {
                    this.setTypeExpImp("");
                } else {
                    this.setTypeExpImp(tdFInfoTO.getType());
                }
                if (tdFInfoTO.getForwardContractLimit() == null) {
                    this.setFcCreditLim("0.0");
                } else {
                    this.setFcCreditLim(tdFInfoTO.getForwardContractLimit().toString());
                }
                if (tdFInfoTO.getfCMargin() == null) {
                    this.setFcMargin100("");
                } else {
                    this.setFcMargin100(tdFInfoTO.getfCMargin().toString());
                }
                if (tdFInfoTO.getfCSanctioningAuthority() == null || tdFInfoTO.getfCSanctioningAuthority().equalsIgnoreCase("")) {
                    this.setFcSactioningAuth("");
                } else {
                    this.setFcSactioningAuth(tdFInfoTO.getfCSanctioningAuthority());
                }
                if (tdFInfoTO.getfCSanctionExpiryDate() == null) {
                    this.setFcSancExpDate(date);
                } else {
                    this.setFcSancExpDate(tdFInfoTO.getfCSanctionExpiryDate());
                }
                if (tdFInfoTO.getfCNextNumberCode() == null || tdFInfoTO.getfCNextNumberCode().equalsIgnoreCase("")) {
                    this.setFcNextNoCode("");
                } else {
                    this.setFcNextNoCode(tdFInfoTO.getfCNextNumberCode());
                }
                if (tdFInfoTO.getDocumentCreditLimit() == null) {
                    this.setDcCreditLim("");
                } else {
                    this.setDcCreditLim(tdFInfoTO.getDocumentCreditLimit().toString());
                }
                if (tdFInfoTO.getdCMargin() == null) {
                    this.setDcMargin100("");
                } else {
                    this.setDcMargin100(tdFInfoTO.getdCMargin().toString());
                }
                if (tdFInfoTO.getdCSanctioningAuthority() == null || tdFInfoTO.getdCSanctioningAuthority().equalsIgnoreCase("")) {
                    this.setDcSactioningAuth("");
                } else {
                    this.setDcSactioningAuth(tdFInfoTO.getdCSanctioningAuthority());
                }
                if (tdFInfoTO.getdCSanctionExpiryDate() == null) {
                    this.setDcSancExpDate(date);
                } else {
                    this.setDcSancExpDate(tdFInfoTO.getdCSanctionExpiryDate());
                }
                if (tdFInfoTO.getdCNextNumberCode() == null || tdFInfoTO.getdCNextNumberCode().equalsIgnoreCase("")) {
                    this.setDcNextNoCode("");
                } else {
                    this.setDcNextNoCode(tdFInfoTO.getdCNextNumberCode());
                }
                if (tdFInfoTO.getPackingContractLimit() == null) {
                    this.setPcCreditLim("0.0");
                } else {
                    this.setPcCreditLim(tdFInfoTO.getPackingContractLimit().toString());
                }
                if (tdFInfoTO.getpCMargin() == null) {
                    this.setPcMargin100("0.0");
                } else {
                    this.setPcMargin100(tdFInfoTO.getpCMargin().toString());
                }
                if (tdFInfoTO.getpCSanctioningAuthority() == null || tdFInfoTO.getpCSanctioningAuthority().equalsIgnoreCase("")) {
                    this.setPcSactioningAuth("");
                } else {
                    this.setPcSactioningAuth(tdFInfoTO.getpCSanctioningAuthority());
                }
                if (tdFInfoTO.getpCSanctionExpiryDate() == null) {
                    this.setPcSancExpDate(date);
                } else {
                    this.setPcSancExpDate(tdFInfoTO.getpCSanctionExpiryDate());
                }
                if (tdFInfoTO.getpCNextNumberCode() == null || tdFInfoTO.getpCNextNumberCode().equalsIgnoreCase("")) {
                    this.setPcNextNoCode("");
                } else {
                    this.setPcNextNoCode(tdFInfoTO.getpCNextNumberCode());
                }
                if (tdFInfoTO.getBankGuaranteeLimit() == null) {
                    this.setBgCreditLim("0.0");
                } else {
                    this.setBgCreditLim(tdFInfoTO.getBankGuaranteeLimit().toString());
                }
                if (tdFInfoTO.getbGMargin() == null) {
                    this.setBgMargin100("0.0");
                } else {
                    this.setBgMargin100(tdFInfoTO.getbGMargin().toString());
                }
                if (tdFInfoTO.getbGSanctioningAuthority() == null || tdFInfoTO.getbGSanctioningAuthority().equalsIgnoreCase("")) {
                    this.setBgSactioningAuth("");
                } else {
                    this.setBgSactioningAuth(tdFInfoTO.getbGSanctioningAuthority());
                }
                if (tdFInfoTO.getbGSanctionExpiryDate() == null) {
                    this.setBgSancExpDate(date);
                } else {
                    this.setBgSancExpDate(tdFInfoTO.getbGSanctionExpiryDate());
                }
                if (tdFInfoTO.getbGNextNumberCode() == null || tdFInfoTO.getbGNextNumberCode().equalsIgnoreCase("")) {
                    this.setBgNextNoCode("");
                } else {
                    this.setBgNextNoCode(tdFInfoTO.getbGNextNumberCode());
                }
                if (tdFInfoTO.getPresentOutstandingLiability() == null) {
                    this.setTfiPresentOutStandLiab("");
                } else {
                    this.setTfiPresentOutStandLiab(tdFInfoTO.getPresentOutstandingLiability().toString());
                }
            }

        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }

    }

    public void refreshForm() {
        this.setTfiPresentOutStandLiab("0.0");
        this.setDcMargin100("0.0");
        this.setDcCreditLim("0.0");
        this.setFcMargin100("0.0");
        this.setFcCreditLim("0.0");
        this.setPcMargin100("0.0");
        this.setPcCreditLim("0.0");
        this.setBgMargin100("0.0");
        this.setBgCreditLim("0.0");
        this.setTfiName("");
        this.setTfiAdd1("");

        this.setTfiAdd2("");
        this.setCityForTFI("0");
        this.setTfiPostal("");
        this.setCountryForTFI("0");

        this.setTfiPhone("");
        this.setTfiFax("");
        this.setTfiTelex("");
        this.setTfiNative("");

        this.setInlandTradeAllowed("0");
        this.setIndustryType("0");
        this.setExportUnit("0");
        this.setStatusTFI("0");

        this.setTradeExpoCode("0");
        this.setPartyConstitution("0");
        this.setSpecialParty("0");
        this.setTfiPartyType("");

        this.setTfiProdCycle("");
        this.setTfiCodeGivenByCentralBnk("");
        this.setTfiCodeGivenByCentralAuth("");
        this.setTypeExpImp("0");

        this.setFcSactioningAuth("");
        this.setFcNextNoCode("");
        this.setDcSactioningAuth("");
        this.setDcNextNoCode("");

        this.setPcSactioningAuth("");
        this.setPcNextNoCode("");
        this.setBgSactioningAuth("");
        this.setBgNextNoCode("");
        this.setReviewDate(date);
        this.setDcSancExpDate(date);
        this.setFcSancExpDate(date);
        this.setPcSancExpDate(date);
        this.setBgSancExpDate(date);
    }
    public String validation()
    {
         
//                if (!onblurTFIFaxNo()) {
//                    return;
//                }
//
//                if (!onblurTFITelNo()) {
//                    return;
//                }
//
//                if (!onblurTfiPostal()) {
//                    return;
//                }
//
        return "ok";
    }
}
