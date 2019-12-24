/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSCustDeliveryChannelDetailsTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.utils.CbsUtil;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ankit Verma
 */
public class OtherInfo {

    CustomerDetail customerDetail;
    CustomerMasterDelegate masterDelegate;
    SchemeMasterManagementDelegate schemeMasterManagementDelegate;
    String operRiskRate;
    String creditOperRiskrate;
    String exRatingShortterm;
    String exRatingLongterm;
    String threshOldTransLim;
    Date ratingAsOn1;
    Date ratingAsOn2;
    Date ratingAsOn3;
    Date ratingAsOn4;
    private String atmCardType;
    private String telBnkType;
    private String iNetBnkType;
    private String smsBnkType;
    private String selfSrvType;
    private String delFlg;
    private List<SelectItem> operRiskRateOption;
    private List<SelectItem> atmCardTypeOption;
    private List<SelectItem> telBnkTypeOption;
    private List<SelectItem> iNetBnkTypeOption;
    private List<SelectItem> smsBnkTypeOption;
    private List<SelectItem> selfSrvTypeOption;
    String creditCardHolder;
    List<SelectItem> creditCardHolderOption;
    List<SelectItem> mobBankingEnableOption;
    List<SelectItem> ecsEnableOption;
    String mobBankingEnable;
    String ecsEnable;
    Date custAquiDate;
    Date thresholdLimUpdDate;
    Date custUpdationDate;
    Date date = new Date();

    /** Creates a new instance of OtherInfo */
    public OtherInfo() {
        initializeForm();
    }

    public String getCreditOperRiskrate() {
        return creditOperRiskrate;
    }

    public void setCreditOperRiskrate(String creditOperRiskrate) {
        this.creditOperRiskrate = creditOperRiskrate;
    }

    public String getExRatingLongterm() {
        return exRatingLongterm;
    }

    public void setExRatingLongterm(String exRatingLongterm) {
        this.exRatingLongterm = exRatingLongterm;
    }

    public String getExRatingShortterm() {
        return exRatingShortterm;
    }

    public void setExRatingShortterm(String exRatingShortterm) {
        this.exRatingShortterm = exRatingShortterm;
    }

    public String getOperRiskRate() {
        return operRiskRate;
    }

    public void setOperRiskRate(String operRiskRate) {
        this.operRiskRate = operRiskRate;
    }

    public String getThreshOldTransLim() {
        return threshOldTransLim;
    }

    public void setThreshOldTransLim(String threshOldTransLim) {
        this.threshOldTransLim = threshOldTransLim;
    }

    public Date getRatingAsOn1() {
        return ratingAsOn1;
    }

    public void setRatingAsOn1(Date ratingAsOn1) {
        this.ratingAsOn1 = ratingAsOn1;
    }

    public Date getRatingAsOn2() {
        return ratingAsOn2;
    }

    public void setRatingAsOn2(Date ratingAsOn2) {
        this.ratingAsOn2 = ratingAsOn2;
    }

    public Date getRatingAsOn3() {
        return ratingAsOn3;
    }

    public void setRatingAsOn3(Date ratingAsOn3) {
        this.ratingAsOn3 = ratingAsOn3;
    }

    public Date getRatingAsOn4() {
        return ratingAsOn4;
    }

    public void setRatingAsOn4(Date ratingAsOn4) {
        this.ratingAsOn4 = ratingAsOn4;
    }

    public String getAtmCardType() {
        return atmCardType;
    }

    public void setAtmCardType(String atmCardType) {
        this.atmCardType = atmCardType;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getiNetBnkType() {
        return iNetBnkType;
    }

    public void setiNetBnkType(String iNetBnkType) {
        this.iNetBnkType = iNetBnkType;
    }

    public CustomerMasterDelegate getMasterDelegate() {
        return masterDelegate;
    }

    public void setMasterDelegate(CustomerMasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    public String getSelfSrvType() {
        return selfSrvType;
    }

    public void setSelfSrvType(String selfSrvType) {
        this.selfSrvType = selfSrvType;
    }

    public String getSmsBnkType() {
        return smsBnkType;
    }

    public void setSmsBnkType(String smsBnkType) {
        this.smsBnkType = smsBnkType;
    }

    public String getTelBnkType() {
        return telBnkType;
    }

    public void setTelBnkType(String telBnkType) {
        this.telBnkType = telBnkType;
    }

    public List<SelectItem> getAtmCardTypeOption() {
        return atmCardTypeOption;
    }

    public void setAtmCardTypeOption(List<SelectItem> atmCardTypeOption) {
        this.atmCardTypeOption = atmCardTypeOption;
    }

    public List<SelectItem> getiNetBnkTypeOption() {
        return iNetBnkTypeOption;
    }

    public void setiNetBnkTypeOption(List<SelectItem> iNetBnkTypeOption) {
        this.iNetBnkTypeOption = iNetBnkTypeOption;
    }

    public List<SelectItem> getSelfSrvTypeOption() {
        return selfSrvTypeOption;
    }

    public void setSelfSrvTypeOption(List<SelectItem> selfSrvTypeOption) {
        this.selfSrvTypeOption = selfSrvTypeOption;
    }

    public List<SelectItem> getSmsBnkTypeOption() {
        return smsBnkTypeOption;
    }

    public void setSmsBnkTypeOption(List<SelectItem> smsBnkTypeOption) {
        this.smsBnkTypeOption = smsBnkTypeOption;
    }

    public List<SelectItem> getTelBnkTypeOption() {
        return telBnkTypeOption;
    }

    public void setTelBnkTypeOption(List<SelectItem> telBnkTypeOption) {
        this.telBnkTypeOption = telBnkTypeOption;
    }

    public String getCreditCardHolder() {
        return creditCardHolder;
    }

    public void setCreditCardHolder(String creditCardHolder) {
        this.creditCardHolder = creditCardHolder;
    }

    public List<SelectItem> getCreditCardHolderOption() {
        return creditCardHolderOption;
    }

    public void setCreditCardHolderOption(List<SelectItem> creditCardHolderOption) {
        this.creditCardHolderOption = creditCardHolderOption;
    }

    public List<SelectItem> getEcsEnableOption() {
        return ecsEnableOption;
    }

    public void setEcsEnableOption(List<SelectItem> ecsEnableOption) {
        this.ecsEnableOption = ecsEnableOption;
    }

    public List<SelectItem> getMobBankingEnableOption() {
        return mobBankingEnableOption;
    }

    public void setMobBankingEnableOption(List<SelectItem> mobBankingEnableOption) {
        this.mobBankingEnableOption = mobBankingEnableOption;
    }

    public Date getCustAquiDate() {
        return custAquiDate;
    }

    public void setCustAquiDate(Date custAquiDate) {
        this.custAquiDate = custAquiDate;
    }

    public Date getCustUpdationDate() {
        return custUpdationDate;
    }

    public void setCustUpdationDate(Date custUpdationDate) {
        this.custUpdationDate = custUpdationDate;
    }

    public String getEcsEnable() {
        return ecsEnable;
    }

    public void setEcsEnable(String ecsEnable) {
        this.ecsEnable = ecsEnable;
    }

    public String getMobBankingEnable() {
        return mobBankingEnable;
    }

    public void setMobBankingEnable(String mobBankingEnable) {
        this.mobBankingEnable = mobBankingEnable;
    }

    public Date getThresholdLimUpdDate() {
        return thresholdLimUpdDate;
    }

    public void setThresholdLimUpdDate(Date thresholdLimUpdDate) {
        this.thresholdLimUpdDate = thresholdLimUpdDate;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public List<SelectItem> getOperRiskRateOption() {
        return operRiskRateOption;
    }

    public void setOperRiskRateOption(List<SelectItem> operRiskRateOption) {
        this.operRiskRateOption = operRiskRateOption;
    }

    public SchemeMasterManagementDelegate getSchemeMasterManagementDelegate() {
        return schemeMasterManagementDelegate;
    }

    public void setSchemeMasterManagementDelegate(SchemeMasterManagementDelegate schemeMasterManagementDelegate) {
        this.schemeMasterManagementDelegate = schemeMasterManagementDelegate;
    }

    public void onblurCustUpdateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            long strDateDiff = CbsUtil.dayDiff(custUpdationDate, new Date());
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

    public void onblurRatingsAsOn1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            long strDateDiff = CbsUtil.dayDiff(ratingAsOn1, new Date());
            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can't select the date after the current date.");
                return;
            } else {
                getCustomerDetail().setErrorMsg("");
            }
            if (!operRiskRate.equals("0")) {
                if (ratingAsOn1 == null) {
                   getCustomerDetail().setErrorMsg("Please select Rating As On at Other Information Tab in front of Operational Risk Rating.");
                return; 
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void onblurRatingsAsOn2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            long strDateDiff = CbsUtil.dayDiff(ratingAsOn2, new Date());
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

    public void onblurRatingsAsOn3() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            long strDateDiff = CbsUtil.dayDiff(ratingAsOn3, new Date());
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

    public void onblurRatingsAsOn4() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            long strDateDiff = CbsUtil.dayDiff(ratingAsOn4, new Date());
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

    public void onblurCustAquiDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            long strDateDiff = CbsUtil.dayDiff(custAquiDate, new Date());
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

    public String onblurThreshUpdateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            long strDateDiff = CbsUtil.dayDiff(thresholdLimUpdDate, new Date());
            if (strDateDiff < 0) {
                getCustomerDetail().setErrorMsg("You can't select the date after the current date.");
                return "You can't select the Thresold Limit Updation Date after the current date.";
            } else {
                getCustomerDetail().setErrorMsg("");
                return "ok";
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
            return e.getMessage();
        }
    }

    public void initializeForm() {
        try {
            schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsRefRecTypeTO> CbsRefRecTypeTOs = schemeMasterManagementDelegate.getCurrencyCode("024");
            if (CbsRefRecTypeTOs.size() > 0) {
                operRiskRateOption = new ArrayList<SelectItem>();
                //operRiskRateOption.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    operRiskRateOption.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            } else {
                operRiskRateOption = new ArrayList<SelectItem>();
                operRiskRateOption.add(new SelectItem("0", ""));
            }


            masterDelegate = new CustomerMasterDelegate();
            // Code To Set The Value Of ATM CARD Drop Down
            atmCardTypeOption = new ArrayList<SelectItem>();
            atmCardTypeOption.add(new SelectItem("0", "--Select--"));
            atmCardTypeOption.add(new SelectItem("Y", "Yes"));
            atmCardTypeOption.add(new SelectItem("N", "No"));

            // Code To Set The Value Of Tele Banking Drop Down
            telBnkTypeOption = new ArrayList<SelectItem>();
            telBnkTypeOption.add(new SelectItem("0", "--Select--"));
            telBnkTypeOption.add(new SelectItem("Y", "Yes"));
            telBnkTypeOption.add(new SelectItem("N", "No"));

            // Code To Set The Value Of I NET Banking Drop Down
            iNetBnkTypeOption = new ArrayList<SelectItem>();
            iNetBnkTypeOption.add(new SelectItem("0", "--Select"));
            iNetBnkTypeOption.add(new SelectItem("Y", "Yes"));
            iNetBnkTypeOption.add(new SelectItem("N", "No"));

            // Code To Set The Value Of SMS Banking Drop Down
            smsBnkTypeOption = new ArrayList<SelectItem>();
            smsBnkTypeOption.add(new SelectItem("0", "--Select--"));
            smsBnkTypeOption.add(new SelectItem("Y", "Yes"));
            smsBnkTypeOption.add(new SelectItem("N", "No"));

            // Code To Set The Value Of Self Service Drop Down
            selfSrvTypeOption = new ArrayList<SelectItem>();
            selfSrvTypeOption.add(new SelectItem("0", "--Select--"));
            selfSrvTypeOption.add(new SelectItem("Y", "Yes"));
            selfSrvTypeOption.add(new SelectItem("N", "No"));

            mobBankingEnableOption = new ArrayList<SelectItem>();
            mobBankingEnableOption.add(new SelectItem("0", "--Select--"));
            mobBankingEnableOption.add(new SelectItem("Y", "Yes"));
            mobBankingEnableOption.add(new SelectItem("N", "No"));
            ecsEnableOption = new ArrayList<SelectItem>();
            ecsEnableOption.add(new SelectItem("0", "--Select--"));
            ecsEnableOption.add(new SelectItem("Y", "Yes"));
            ecsEnableOption.add(new SelectItem("N", "No"));
            creditCardHolderOption = new ArrayList<SelectItem>();
            creditCardHolderOption.add(new SelectItem("0", "--Select--"));
            creditCardHolderOption.add(new SelectItem("Y", "Yes"));
            creditCardHolderOption.add(new SelectItem("N", "No"));
            setRatingAsOn1(date);
            setThreshOldTransLim("0");
            setThresholdLimUpdDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectFieldValues() {
        try {
            CBSCustomerMasterDetailTO custTO = masterDelegate.getCustDetailsByCustId(getCustomerDetail().getCustId());
            if (custTO != null) {
                if (custTO.getOperationalRiskRating() == null || custTO.getOperationalRiskRating().equalsIgnoreCase("")) {
                    this.setOperRiskRate("0");
                } else {
                    this.setOperRiskRate(custTO.getOperationalRiskRating());
                }
                if (custTO.getRatingAsOn() == null) {
                    this.setRatingAsOn1(date);
                } else {
                    this.setRatingAsOn1(custTO.getRatingAsOn());
                }
                if (custTO.getCreditRiskRatingInternal() == null || custTO.getCreditRiskRatingInternal().equalsIgnoreCase("")) {
                    this.setCreditOperRiskrate("");
                } else {
                    this.setCreditOperRiskrate(custTO.getCreditRiskRatingInternal());
                }
                if (custTO.getCreditRatingAsOn() == null) {
                    this.setRatingAsOn2(date);
                } else {
                    this.setRatingAsOn2(custTO.getCreditRatingAsOn());
                }
                if (custTO.getExternalRatingShortTerm() == null || custTO.getExternalRatingShortTerm().equalsIgnoreCase("")) {
                    this.setExRatingShortterm("");
                } else {
                    this.setExRatingShortterm(custTO.getExternalRatingShortTerm());
                }
                if (custTO.getExternShortRatingAsOn() == null) {
                    this.setRatingAsOn4(date);
                } else {
                    this.setRatingAsOn4(custTO.getExternShortRatingAsOn());
                }
                if (custTO.getCustAcquistionDate() == null) {
                    this.setCustAquiDate(date);
                } else {
                    this.setCustAquiDate(custTO.getCustAcquistionDate());
                }
                if (custTO.getThresoldTransactionLimit() == null) {
                    this.setThreshOldTransLim("0.0");
                } else {
                    this.setThreshOldTransLim(custTO.getThresoldTransactionLimit().toString());
                }
                if (custTO.getThresoldLimitUpdationDate()== null) {
                    this.setThresholdLimUpdDate(date);
                } else {
                    this.setThresholdLimUpdDate(custTO.getThresoldLimitUpdationDate());
                }
                if (custTO.getCustUpdationDate() == null) {
                    this.setCustUpdationDate(date);
                } else {
                    this.setCustUpdationDate(custTO.getCustUpdationDate());
                }
            }
            CBSCustDeliveryChannelDetailsTO detailsTO = masterDelegate.getCusDeliveryChannelDetailsByCustId(getCustomerDetail().getCustId());
            if (detailsTO != null) {
                if (detailsTO.getaTMDebitCardHolder() == null || detailsTO.getaTMDebitCardHolder().equalsIgnoreCase("")) {
                    this.setAtmCardType("0");
                } else {
                    this.setAtmCardType(detailsTO.getaTMDebitCardHolder());
                }
                if (detailsTO.getCreditCardEnabled() == null || detailsTO.getCreditCardEnabled().equalsIgnoreCase("")) {
                    this.setCreditCardHolder("0");
                } else {
                    this.setCreditCardHolder(detailsTO.getCreditCardEnabled());
                }
                if (detailsTO.getTeleBankingEnabled() == null || detailsTO.getTeleBankingEnabled().equalsIgnoreCase("")) {
                    this.setTelBnkType("0");
                } else {
                    this.setTelBnkType(detailsTO.getTeleBankingEnabled());
                }
                if (detailsTO.getSmsBankingEnabled() == null || detailsTO.getSmsBankingEnabled().equalsIgnoreCase("")) {
                    this.setSmsBnkType("0");
                } else {
                    this.setSmsBnkType(detailsTO.getSmsBankingEnabled());
                }
                if (detailsTO.getiNetBankingEnabled() == null || detailsTO.getiNetBankingEnabled().equalsIgnoreCase("")) {
                    this.setiNetBnkType("0");
                } else {
                    this.setiNetBnkType(detailsTO.getiNetBankingEnabled());
                }
                if (detailsTO.getMobileBankingEnabled() == null || detailsTO.getMobileBankingEnabled().equalsIgnoreCase("")) {
                    this.setMobBankingEnable("0");
                } else {
                    this.setMobBankingEnable(detailsTO.getMobileBankingEnabled());
                }
                if (detailsTO.getSelfServiceEnabled() == null || detailsTO.getSelfServiceEnabled().equalsIgnoreCase("")) {
                    this.setSelfSrvType("0");
                } else {
                    this.setSelfSrvType(detailsTO.getSelfServiceEnabled());
                }
                if (detailsTO.geteCSEnabled() == null || detailsTO.geteCSEnabled().equalsIgnoreCase("")) {
                    this.setEcsEnable("0");
                } else {
                    this.setEcsEnable(detailsTO.geteCSEnabled());
                }
            }
        } catch (Exception e) {
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        this.setOperRiskRate("0");
        this.setCreditOperRiskrate("");

        this.setExRatingShortterm("");
        this.setExRatingLongterm("");
        this.setThreshOldTransLim("0.0");//correction 0.0 date-03/11/2010
        this.setAtmCardType("0");
        this.setCreditCardHolder("0");
        this.setTelBnkType("0");
        this.setSmsBnkType("0");
        this.setiNetBnkType("0");
        this.setMobBankingEnable("0");

        this.setSelfSrvType("0");
        this.setEcsEnable("0");

        this.setRatingAsOn1(date);
        this.setRatingAsOn2(date);
        this.setRatingAsOn3(date);
        this.setRatingAsOn4(date);
        this.setCustAquiDate(date);
        this.setThresholdLimUpdDate(date);
        this.setCustUpdationDate(date);
    }
}
