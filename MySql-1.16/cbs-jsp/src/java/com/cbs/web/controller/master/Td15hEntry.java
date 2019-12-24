package com.cbs.web.controller.master;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.TdIntDetail;
import com.cbs.dto.TdsDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.SortBySeqNo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.pojo.TdEntry;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public final class Td15hEntry extends BaseBean {

    private String fnDetails;
    private List<SelectItem> fnDetailsList;
    private String custId;
    private String tdsDetails;
    private List<SelectItem> tdsDetailsList;
    private String custName;
    private String fatherName;
    private String dob;
    private String panNo;
    private String totalNoForm;
    private String aggregateAmount;
    private String otherIncome;
    private String deductionAmt;
    private double estimatedIncome;
    Date dates;
    private String fYear;
    private String message;
    private List<TdEntry> tdEntries;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    TermDepositMasterFacadeRemote tdRemote;
    private CommonReportMethodsRemote common;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private boolean saveDisable;
    private String btnValue;
    private String addVisible = "";
    private String othVisible = "none";
    private String custIdStyle;
    private String verifyStyle;
    private List<SelectItem> custList;
    private String custNumber;
    TdEntry currentItem;
    private boolean docDisable;
    private String focusId;
    private String disMsg;
    private String confirmationMsg;
    private boolean incomeTaxChecking;
    private String assYear;
    private boolean disableAssYear;
    private String popupCustFlag = "false";
    private boolean disableForExampt;
    private String legalEntity;
    private double totalProjectedInt;
    private String taxOption;
    private List<SelectItem> taxOptionList;
    private boolean taxDisable;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public double getTotalProjectedInt() {
        return totalProjectedInt;
    }

    public void setTotalProjectedInt(double totalProjectedInt) {
        this.totalProjectedInt = totalProjectedInt;
    }

    public String getFnDetails() {
        return fnDetails;
    }

    public void setFnDetails(String fnDetails) {
        this.fnDetails = fnDetails;
    }

    public List<SelectItem> getFnDetailsList() {
        return fnDetailsList;
    }

    public void setFnDetailsList(List<SelectItem> fnDetailsList) {
        this.fnDetailsList = fnDetailsList;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getTdsDetails() {
        return tdsDetails;
    }

    public void setTdsDetails(String tdsDetails) {
        this.tdsDetails = tdsDetails;
    }

    public List<SelectItem> getTdsDetailsList() {
        return tdsDetailsList;
    }

    public void setTdsDetailsList(List<SelectItem> tdsDetailsList) {
        this.tdsDetailsList = tdsDetailsList;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TdEntry> getTdEntries() {
        return tdEntries;
    }

    public void setTdEntries(List<TdEntry> tdEntries) {
        this.tdEntries = tdEntries;
    }

    public TermDepositMasterFacadeRemote getTdRemote() {
        return tdRemote;
    }

    public void setTdRemote(TermDepositMasterFacadeRemote tdRemote) {
        this.tdRemote = tdRemote;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getAddVisible() {
        return addVisible;
    }

    public void setAddVisible(String addVisible) {
        this.addVisible = addVisible;
    }

    public String getOthVisible() {
        return othVisible;
    }

    public void setOthVisible(String othVisible) {
        this.othVisible = othVisible;
    }

    public String getCustIdStyle() {
        return custIdStyle;
    }

    public void setCustIdStyle(String custIdStyle) {
        this.custIdStyle = custIdStyle;
    }

    public String getVerifyStyle() {
        return verifyStyle;
    }

    public void setVerifyStyle(String verifyStyle) {
        this.verifyStyle = verifyStyle;
    }

    public List<SelectItem> getCustList() {
        return custList;
    }

    public void setCustList(List<SelectItem> custList) {
        this.custList = custList;
    }

    public String getCustNumber() {
        return custNumber;
    }

    public void setCustNumber(String custNumber) {
        this.custNumber = custNumber;
    }

    public TdEntry getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TdEntry currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isDocDisable() {
        return docDisable;
    }

    public void setDocDisable(boolean docDisable) {
        this.docDisable = docDisable;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getDisMsg() {
        return disMsg;
    }

    public void setDisMsg(String disMsg) {
        this.disMsg = disMsg;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public String getTotalNoForm() {
        return totalNoForm;
    }

    public void setTotalNoForm(String totalNoForm) {
        this.totalNoForm = totalNoForm;
    }

    public String getAggregateAmount() {
        return aggregateAmount;
    }

    public void setAggregateAmount(String aggregateAmount) {
        this.aggregateAmount = aggregateAmount;
    }

    public String getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(String otherIncome) {
        this.otherIncome = otherIncome;
    }

    public String getDeductionAmt() {
        return deductionAmt;
    }

    public void setDeductionAmt(String deductionAmt) {
        this.deductionAmt = deductionAmt;
    }

    public double getEstimatedIncome() {
        return estimatedIncome;
    }

    public void setEstimatedIncome(double estimatedIncome) {
        this.estimatedIncome = estimatedIncome;
    }

    public boolean isIncomeTaxChecking() {
        return incomeTaxChecking;
    }

    public void setIncomeTaxChecking(boolean incomeTaxChecking) {
        this.incomeTaxChecking = incomeTaxChecking;
    }

    public String getAssYear() {
        return assYear;
    }

    public void setAssYear(String assYear) {
        this.assYear = assYear;
    }

    public boolean isDisableAssYear() {
        return disableAssYear;
    }

    public void setDisableAssYear(boolean disableAssYear) {
        this.disableAssYear = disableAssYear;
    }

    public String getPopupCustFlag() {
        return popupCustFlag;
    }

    public void setPopupCustFlag(String popupCustFlag) {
        this.popupCustFlag = popupCustFlag;
    }

    public boolean isDisableForExampt() {
        return disableForExampt;
    }

    public void setDisableForExampt(boolean disableForExampt) {
        this.disableForExampt = disableForExampt;
    }

    public String getTaxOption() {
        return taxOption;
    }

    public void setTaxOption(String taxOption) {
        this.taxOption = taxOption;
    }

    public List<SelectItem> getTaxOptionList() {
        return taxOptionList;
    }

    public void setTaxOptionList(List<SelectItem> taxOptionList) {
        this.taxOptionList = taxOptionList;
    }

    public boolean isTaxDisable() {
        return taxDisable;
    }

    public void setTaxDisable(boolean taxDisable) {
        this.taxDisable = taxDisable;
    }

    public Td15hEntry() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            tdRemote = (TermDepositMasterFacadeRemote) ServiceLocator.getInstance().lookup("TermDepositMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            this.setMessage("");
            setDates(new Date());
            fYearDropDownData();
            tdsDetailsList = new ArrayList<SelectItem>();
//            tdsDetailsList.add(new SelectItem("Form 15H", "Form 15H"));
//            tdsDetailsList.add(new SelectItem("Form 15G", "Form 15G"));
//            tdsDetailsList.add(new SelectItem("Cooperative Society", "Cooperative Society"));
//            if (fts.getCodeFromCbsParameterInfo("EXEMPTION_CERTIFICATE").equalsIgnoreCase("Y")) {
//                tdsDetailsList.add(new SelectItem("Exemption certificate", "Exemption certificate"));
//            }
            List list = common.getTdsDoctype("380");
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    tdsDetailsList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
                }
            }
            fnDetailsList = new ArrayList<SelectItem>();
            fnDetailsList.add(new SelectItem("A", "ADD"));
//            fnDetailsList.add(new SelectItem("M", "MODIFY"));
//            fnDetailsList.add(new SelectItem("D", "DELETE"));
            fnDetailsList.add(new SelectItem("V", "VERIFY"));
            fnDetailsList.add(new SelectItem("E", "ENQUIRY"));

            setBtnValue("Save");
            setSaveDisable(true);

            setCustIdStyle("block");
            setVerifyStyle("none");
            setDocDisable(false);
            //setDisMsg("Double Click On Status To Receive The Documents And Save");
            this.disableAssYear = true;
            this.incomeTaxChecking = false;
            this.assYear = "";

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setFnOption() {
        this.setMessage("");
        this.setCustId("");
        setCustName("");
        setPanNo("");
        setFatherName("");
        setDob("");
        tdEntries = new ArrayList<TdEntry>();
        if (this.getFnDetails().equalsIgnoreCase("A")) {
            setBtnValue("Save");
            setAddVisible("");
            setOthVisible("none");
            setCustIdStyle("block");
            setVerifyStyle("none");
            setDocDisable(false);
            setFocusId("ddDocument");
            //setDisMsg("Double Click On Status To Receive The Documents And Save");
        } //        else if (this.getFnDetails().equalsIgnoreCase("M")){
        //            setBtnValue("Update");
        //            setAddVisible("none");
        //            setOthVisible("");
        //            setCustIdStyle("none");
        //            setVerifyStyle("block");
        //            setDocDisable(false);
        //            setFocusId("ddCustID");
        //            getDetails();
        //            setDisMsg("Double Click On Status To Receive The Documents And Save");
        //        } else if (this.getFnDetails().equalsIgnoreCase("D")){
        //            setBtnValue("Delete");
        //            setAddVisible("none");
        //            setOthVisible("");
        //            setCustIdStyle("none");
        //            setVerifyStyle("block");
        //            setDocDisable(false);
        //            setFocusId("ddCustID");
        //            getDetails();
        //        } 
        else if (this.getFnDetails().equalsIgnoreCase("E")) {
            setBtnValue("Save");
            setAddVisible("none");
            setOthVisible("");
            setCustIdStyle("block");
            setVerifyStyle("none");
            setDocDisable(false);
            setSaveDisable(true);
            setFocusId("txtCustId");
            setDisMsg("");
        } else {
            setBtnValue("Verify");
            setAddVisible("none");
            setOthVisible("");
            setCustIdStyle("none");
            setVerifyStyle("block");
            setDocDisable(false);
            setFocusId("ddCustID");
            getDetails();
            //setDisMsg("Double Click On Auth To Receive The Documents And Save");
            this.disableAssYear = true;
            setDisMsg("");
        }
    }

    public void assessmentControl() {
        setMessage("");
        if (incomeTaxChecking == true) {
            this.disableAssYear = false;
        } else {
            this.disableAssYear = true;
        }
    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.getFnDetails().equals("A")) {
            this.setConfirmationMsg("Are you sure to add this detail ?");
        } else if (this.getFnDetails().equals("V")) {
            this.setConfirmationMsg("Are you sure to verify this detail ?");
        } else if (this.getFnDetails().equals("E")) {
            this.setConfirmationMsg("Are you sure to enquiry this detail ?");
        }
    }

    public void getAccountDetails() {
        try {
            tdEntries = new ArrayList<TdEntry>();
            this.setMessage("");
            if (this.getFnDetails().equalsIgnoreCase("A")) {
                if (tdsDetails.equalsIgnoreCase("Form 15H")) {
                    if (taxOption == null || taxOption.equalsIgnoreCase("")) {
                        this.setMessage("Please Select Tax Option !");
                        return;
                    }
                }
            }
            if (this.getFnDetails().equalsIgnoreCase("E")) {
                setSaveDisable(true);
            } else {
                setSaveDisable(false);
            }
            if (this.getFnDetails().equalsIgnoreCase("A")) {
                if (custId == null || custId.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter Customer ID");
                    return;
                }
            }
            List custTypeList = new ArrayList();
            if (this.getFnDetails().equalsIgnoreCase("A") || this.getFnDetails().equalsIgnoreCase("E")) {
                custTypeList = tdRemote.getcustEntityType(custId);
            } else {
                custTypeList = tdRemote.getcustEntityType(custNumber);
            }
            Vector custEntityVector = (Vector) custTypeList.get(0);
            legalEntity = custEntityVector.get(0).toString();

            List resultLt = new ArrayList();
            if (this.getFnDetails().equalsIgnoreCase("A")) {
                if (custId == null || custId.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter Customer ID");
                    return;
                }

                List formcheckingList = tdRemote.form15G15HChecking(custId);
                if (!formcheckingList.isEmpty()) {
                    throw new ApplicationException("Form fill up already this customer !");
                }
                popupCustFlag = "false";
                resultLt = tdRemote.customerValidateData(custId);
                Vector vtr = (Vector) resultLt.get(0);
                String dob = vtr.get(0).toString();
                String panNo = vtr.get(1).toString();
                String nriflag = vtr.get(2).toString();
                String mobilenumber = vtr.get(3).toString();
                String PerAddressLine1 = vtr.get(4).toString();
                String PerBlock = vtr.get(5).toString();
                String PerCityCode = vtr.get(6).toString();
                String PerStateCode = vtr.get(7).toString();
                String PerCountryCode = vtr.get(8).toString();
                String PerVillage = vtr.get(9).toString();
                String PerPhoneNumber = vtr.get(10).toString();
                String PerPostalCode = vtr.get(11).toString();
                String minorFlag = vtr.get(12).toString();
                String email = vtr.get(13).toString();
                String district = vtr.get(14).toString();
                String custEntityType = vtr.get(15).toString();
                //legalEntity = custEntityType;

                List<String> checkList = new ArrayList<String>();

                if (!(tdsDetails.equalsIgnoreCase("Exemption certificate") || tdsDetails.equalsIgnoreCase("Cooperative Society"))) {
                    if (panNo.equals("") || panNo.length() != 10) {
                        setMessage("Please fill customer Id of pan no holder only.");
                        return;
                    }
                } else if (tdsDetails.equalsIgnoreCase("Form 15G") || tdsDetails.equalsIgnoreCase("Form 15H")) {
                    if (panNo.equals("")) {
                        setMessage("Please fill Pan No !");
                        return;
                    }
                    if (panNo.equals("") || panNo.length() != 10) {
                        throw new ApplicationException("Pan No is not proper !");
                    }
                }

                if (minorFlag.equalsIgnoreCase("Y")) {
                    setMessage("This customer is minor, So minor guardian customer Id fill");
                    return;
                }

                if (dob.equalsIgnoreCase("")) {
                    checkList.add("Date of birth");
//                    popupCustFlag = "true";
//                    return;
                }
                if (panNo.equalsIgnoreCase("")) {
                    checkList.add("Pan No.");
//                    popupCustFlag = "true";
//                    return;
                }

                if (nriflag.equalsIgnoreCase("") || nriflag.equalsIgnoreCase("0")) {
                    checkList.add("Residential Status");
//                    popupCustFlag = "true";
//                    return;
                }
                if (mobilenumber.equalsIgnoreCase("")) {
                    checkList.add("Mobilenumber");
//                    popupCustFlag = "true";
//                    return;
                }
                if (PerAddressLine1.equalsIgnoreCase("")) {
                    checkList.add("Permanent address line1");
//                    popupCustFlag = "true";
//                    return;
                }
                if (PerBlock.equalsIgnoreCase("")) {
                    checkList.add("Flat/Door/Block No.");
//                    popupCustFlag = "true";
//                    return;
                }
                if (PerCityCode.equalsIgnoreCase("")) {
                    checkList.add("City/District");
//                    popupCustFlag = "true";
//                    return;
                }
                if (PerStateCode.equalsIgnoreCase("")) {
                    checkList.add("State");
//                    popupCustFlag = "true";
//                    return;
                }
                if (PerCountryCode.equalsIgnoreCase("")) {
                    checkList.add("Country");
//                    popupCustFlag = "true";
//                    return;
                }
                if (PerVillage.equalsIgnoreCase("")) {
                    checkList.add("Village");
//                    popupCustFlag = "true";
//                    return;
                }

                if (PerPostalCode.equalsIgnoreCase("")) {
                    checkList.add("Postal Code");
//                    popupCustFlag = "true";
//                    return;
                }

//                if (email.equalsIgnoreCase("")) {
//                    checkList.add("Email Id");
//                }
                if (district.equalsIgnoreCase("")) {
                    checkList.add("District");
                }

                if (tdsDetails.equalsIgnoreCase("Form 15G") || tdsDetails.equalsIgnoreCase("Form 15H")) {
                    if (dob.equalsIgnoreCase("") || panNo.equalsIgnoreCase("") || nriflag.equalsIgnoreCase("") || nriflag.equalsIgnoreCase("0")
                            || mobilenumber.equalsIgnoreCase("") || PerAddressLine1.equalsIgnoreCase("") || PerBlock.equalsIgnoreCase("") || PerCityCode.equalsIgnoreCase("")
                            || PerStateCode.equalsIgnoreCase("") || PerCountryCode.equalsIgnoreCase("") || PerVillage.equalsIgnoreCase("") || PerPostalCode.equalsIgnoreCase("")) {
                        setMessage("Mandatory fields for 15G / 15H fill up " + checkList.toString() + " So please check / verify fields from Customer Detail Form,then fill up !");
                        return;
                    }
                } else {
                    if (dob.equalsIgnoreCase("") || nriflag.equalsIgnoreCase("") || nriflag.equalsIgnoreCase("0")
                            || mobilenumber.equalsIgnoreCase("") || PerAddressLine1.equalsIgnoreCase("") || PerBlock.equalsIgnoreCase("") || PerCityCode.equalsIgnoreCase("")
                            || PerStateCode.equalsIgnoreCase("") || PerCountryCode.equalsIgnoreCase("") || PerVillage.equalsIgnoreCase("") || PerPostalCode.equalsIgnoreCase("")) {
                        setMessage("Mandatory fields for 15G / 15H fill up " + checkList.toString() + " So please check / verify fields from Customer Detail Form,then fill up !");
                        return;
                    }
                }
                setAddVisible("");
                setOthVisible("none");
            } else {
                setAddVisible("none");
                setOthVisible("");
            }
            //List resultLt = new ArrayList();
            if (this.getFnDetails().equalsIgnoreCase("A") || this.getFnDetails().equalsIgnoreCase("E")) {
                if ((custId == null) || (custId.equalsIgnoreCase(""))) {
                    setCustName("");
                    setPanNo("");
                    setFatherName("");
                    setDob("");
                    this.setMessage("Please Enter Customer ID");
                    return;
                }
                if (!custId.matches("[0-9]*")) {
                    setMessage("Please Enter Valid Customer ID.");
                    return;
                }
                if (this.getFnDetails().equalsIgnoreCase("A")) {
                    resultLt = tdRemote.accountDetails(custId, "A");
                } else {
                    resultLt = tdRemote.accountDetails(custId, "E");
                }

            } else {
                if ((custNumber == null) || (custNumber.equalsIgnoreCase(""))) {
                    setCustName("");
                    setPanNo("");
                    setFatherName("");
                    setDob("");
                    this.setMessage("Please Select Customer ID");
                    return;
                }
                if (!custNumber.matches("[0-9]*")) {
                    setMessage("Please Select Valid Customer ID.");
                    return;
                }

                resultLt = tdRemote.accountDetails(custNumber, "V");
            }

            for (int i = 0; i < resultLt.size(); i++) {
                Vector ele = (Vector) resultLt.get(i);
                this.setCustName(ele.get(0).toString());
                this.setFatherName(ele.get(1).toString());
                String dateOfBirth = ele.get(2) == null ? " " : ele.get(2).toString();
                if (dateOfBirth.equals("")) {
                    dateOfBirth = "";
                } else {
                    dateOfBirth = sdf.format(ele.get(2));
                }
                this.setDob(dateOfBirth);

                String panNo = ele.get(3) == null ? " " : ele.get(3).toString();
                if (panNo.equals("") || panNo.length() != 10) {
                    panNo = "";
                } else {
                    if (!ParseFileUtil.isAlphabet(panNo.substring(0, 5))
                            || !ParseFileUtil.isNumeric(panNo.substring(5, 9))
                            || !ParseFileUtil.isAlphabet(panNo.substring(9))) {
                        panNo = "";
                    } else {
                    }
                }
                this.setPanNo(panNo);
                if (!this.getFnDetails().equalsIgnoreCase("A")) {
                    this.setTotalNoForm(ele.get(4).toString());
                    this.setAggregateAmount(ele.get(5).toString());
                    this.setOtherIncome(ele.get(6).toString());
                    this.setDeductionAmt(ele.get(7).toString());
                    this.setAssYear(ele.get(8).toString());
                }
            }

            if (fts.getCodeForReportName("SOCIETY-BANK") == 0) {
                if (this.getFnDetails().equalsIgnoreCase("A")) {
                    if (tdsDetails.equalsIgnoreCase("Form 15H")) {
                        String gDt = CbsUtil.yearAdd(ymd.format(sdf.parse(dob)), 60);
                        if (ymd.parse(gDt).after(new Date())) {
                            throw new ApplicationException("Customer Age should be greater than 60.");
                        }
                        if (!panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                            throw new ApplicationException("Customer Status should be individual.");
                        }
                    } else if (tdsDetails.equalsIgnoreCase("Form 15G")) {
                        String gDt = CbsUtil.yearAdd(ymd.format(sdf.parse(dob)), 60);
                        if (sdf.parse(getTodayDate()).after(ymd.parse(gDt)) && panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                            throw new ApplicationException("Customer Age should be less than 60.");
                        }
                        if (sdf.parse(getTodayDate()).getTime() >= ymd.parse(gDt).getTime() && panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                            throw new ApplicationException("Customer Age should be less than 60.");
                        }
                        if (!(panNo.trim().substring(3, 4).equalsIgnoreCase("P") || panNo.trim().substring(3, 4).equalsIgnoreCase("H"))) {
                            throw new ApplicationException("Customer Status should be individual or HUF.");
                        }
                    }
                }
            } else {
                if (!legalEntity.equalsIgnoreCase("02")) {
                    if (this.getFnDetails().equalsIgnoreCase("A")) {
                        if (tdsDetails.equalsIgnoreCase("Form 15H")) {
                            String gDt = CbsUtil.yearAdd(ymd.format(sdf.parse(dob)), 60);
                            if (ymd.parse(gDt).after(new Date())) {
                                throw new ApplicationException("Customer Age should be greater than 60.");
                            }
                            if (!panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                throw new ApplicationException("Customer Status should be individual.");
                            }
                        } else if (tdsDetails.equalsIgnoreCase("Form 15G")) {
                            String gDt = CbsUtil.yearAdd(ymd.format(sdf.parse(dob)), 60);
                            if (sdf.parse(getTodayDate()).after(ymd.parse(gDt)) && panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                throw new ApplicationException("Customer Age should be less than 60.");
                            }
                            if (sdf.parse(getTodayDate()).getTime() >= ymd.parse(gDt).getTime() && panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                throw new ApplicationException("Customer Age should be less than 60.");
                            }
                            if (!(panNo.trim().substring(3, 4).equalsIgnoreCase("P") || panNo.trim().substring(3, 4).equalsIgnoreCase("H"))) {
                                throw new ApplicationException("Customer Status should be individual or HUF.");
                            }
                        }
                    }
                }
            }

            // For grid data
            int finEndYear = Integer.parseInt(fYear) + 1;
            String finStartDt = fYear + "0401";
            String finEndDt = String.valueOf(finEndYear) + "0331";
            List tableList = new ArrayList();
            double totalProjInt = 0;
            if (this.getFnDetails().equalsIgnoreCase("A")) {
                List minorIdList = tdRemote.minorIdData(custId);
                if (minorIdList.isEmpty()) {
                    tableList = tdRemote.customerData(custId,finStartDt);
                    if (!tableList.isEmpty()) {
                        for (int i = 0; i < tableList.size(); i++) {
                            Vector ele = (Vector) tableList.get(i);
                            TdEntry entry = new TdEntry();
                            entry.setAccountNumber(ele.get(0).toString());
                            entry.setVchNo(ele.get(1).toString());
                            entry.setDateOfSub(ele.get(2).toString());
                            entry.setDateOfmat(ele.get(3).toString());
                            entry.setrFlag("Y");
                            float voncherNo = Float.parseFloat(ele.get(1).toString());
                            String acNature = fts.getAcNatureByCode(ele.get(0).toString().substring(2, 4));
                            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                String intAmt = tdRemote.getTotalIntAmtDuringFinYear(ele.get(0).toString(), finStartDt, finEndDt, voncherNo);
                                double fdInt = Double.parseDouble(intAmt);
                                totalProjInt = totalProjInt + fdInt;
                                entry.setProjectedInt(formatter.format(fdInt));
                            } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                double rdInt = tdRemote.getRdProjectedInt(ele.get(0).toString(), finStartDt, finEndDt);
                                entry.setProjectedInt(formatter.format(rdInt));
                                totalProjInt = totalProjInt + rdInt;
//                                List<TdIntDetail> rdSubList = tdRemote.rdAcWiseIntCalc(ele.get(0).toString(), finStartDt, finEndDt, ele.get(0).toString().substring(0, 2));
//                                double rdInt = 0;
//                                if (!rdSubList.isEmpty()) {
//                                    rdInt = rdSubList.get(0).getInterest();
//                                    totalProjInt = totalProjInt + rdInt;
//                                    entry.setProjectedInt(formatter.format(rdInt));
//                                } 
                            }
                            tdEntries.add(entry);
                        }
                        totalProjectedInt = totalProjInt;
                    } else {
                        setMessage("Data does not exist !");
                    }
                } else {
                    for (int j = 0; j < minorIdList.size(); j++) {
                        Vector minorVector = (Vector) minorIdList.get(j);
                        String minorCustId = minorVector.get(0).toString();
                        tableList = tdRemote.customerData(minorCustId,finStartDt);
                        if (!tableList.isEmpty()) {
                            for (int i = 0; i < tableList.size(); i++) {
                                Vector ele = (Vector) tableList.get(i);
                                TdEntry entry = new TdEntry();
                                entry.setAccountNumber(ele.get(0).toString());
                                entry.setVchNo(ele.get(1).toString());
                                entry.setDateOfSub(ele.get(2).toString());
                                entry.setDateOfmat(ele.get(3).toString());
                                entry.setrFlag("Y");
                                float voncherNo = Float.parseFloat(ele.get(1).toString());
                                String acNature = fts.getAcNatureByCode(ele.get(0).toString().substring(2, 4));
                                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    String intAmt = tdRemote.getTotalIntAmtDuringFinYear(ele.get(0).toString(), finStartDt, finEndDt, voncherNo);
                                    double fdInt = Double.parseDouble(intAmt);
                                    totalProjInt = totalProjInt + fdInt;
                                    entry.setProjectedInt(formatter.format(fdInt));
                                } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                    double rdInt = tdRemote.getRdProjectedInt(ele.get(0).toString(), finStartDt, finEndDt);
                                    entry.setProjectedInt(formatter.format(rdInt));
                                    totalProjInt = totalProjInt + rdInt;
//                                    List<TdIntDetail> rdSubList = tdRemote.rdAcWiseIntCalc(ele.get(0).toString(), finStartDt, finEndDt, ele.get(0).toString().substring(0, 2));
//                                    double rdInt = 0;
//                                    if (!rdSubList.isEmpty()) {
//                                        rdInt = rdSubList.get(0).getInterest();
//                                        totalProjInt = totalProjInt + rdInt;
//                                        entry.setProjectedInt(formatter.format(rdInt));
//                                    }
                                }
                                tdEntries.add(entry);
                            }
                            totalProjectedInt = totalProjInt;
                        } else {
                            if(tdEntries.isEmpty()){
                               setMessage("Data does not exist !");  
                            }
                        }
                    }
                }

            } else {
                String aFlag = tdRemote.authFlagChk(custNumber);
                if (aFlag.equalsIgnoreCase("Y")) {
                    setDocDisable(true);
                } else {
                    setDocDisable(false);
                }
                if (this.getFnDetails().equalsIgnoreCase("E")) {
                    tableList = tdRemote.tableData(custId, getOrgnBrCode(), "E");
                } else if (this.getFnDetails().equalsIgnoreCase("V")) {
                    tableList = tdRemote.tableData(custNumber, getOrgnBrCode(), "V");
                } else {
                    tableList = tdRemote.tableData(custNumber, getOrgnBrCode(), "M");
                }
                if (!tableList.isEmpty()) {
                    for (int i = 0; i < tableList.size(); i++) {
                        Vector ele = (Vector) tableList.get(i);
                        TdEntry entry = new TdEntry();
                        entry.setCustomerId(ele.get(0).toString());
                        entry.setAccountNumber(ele.get(1).toString());
                        entry.setSeqNumber(Integer.parseInt(ele.get(2).toString()));
                        entry.setDateOfSub(ele.get(3).toString());
                        entry.setfYear(Integer.parseInt(ele.get(4).toString()));
                        entry.setVchNo(ele.get(5).toString());
                        entry.setTdDtl(ele.get(6).toString());
                        entry.setrFlag(ele.get(7).toString());
                        entry.setAuthStatus(ele.get(9).toString());
                        entry.setEnterBy(ele.get(10).toString());
                        entry.setUni(ele.get(11).toString());
                        tdEntries.add(entry);
                        setTdsDetails(ele.get(6).toString());
                    }
                }
            }
        } catch (ApplicationException e) {
            setCustName("");
            setPanNo("");
            setFatherName("");
            setfYear("");
            setCustId("");
            tdEntries = new ArrayList<TdEntry>();
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fYearDropDownData() {
        try {
            List fYearList = new ArrayList();
            fYearList = tdRemote.fYearData(getOrgnBrCode());
            for (int i = 0; i < fYearList.size(); i++) {
                Vector ele = (Vector) fYearList.get(i);
                this.setfYear(ele.get(0).toString());
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void changeStatus() {
        this.setMessage("");
        try {
            String flag = currentItem.getrFlag();
            if (flag.equalsIgnoreCase("N")) {
                currentItem.setrFlag("Y");
            } else {
                currentItem.setrFlag("N");
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void setGridDocDetails() {
        setMessage("");
        try {

            if (tdsDetails.equalsIgnoreCase("Exemption certificate") || tdsDetails.equalsIgnoreCase("Cooperative Society")) {
                this.disableForExampt = true;
            } else {
                this.disableForExampt = false;
            }
            // For Taxable or Non Taxable Add on  As Per Khattri Bank.
            if (tdsDetails.equalsIgnoreCase("Form 15H")) {
                this.taxDisable = false;
            } else {
                this.taxDisable = true;
            }
            taxOptionList = new ArrayList<>();

            List list = common.getTdsDoctype("448");
            taxOptionList.add(new SelectItem("", "--Select--"));
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    taxOptionList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
                }
            }
            if (!tdsDetails.equalsIgnoreCase("Form 15H")) {
                this.taxOption ="" ;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public int getIndex(List<TdEntry> list, int seqNo) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSeqNumber() == (seqNo)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void save() {
        try {

            if (totalNoForm.equalsIgnoreCase("")) {
                totalNoForm = "0";
            }

            if (aggregateAmount.equalsIgnoreCase("")) {
                aggregateAmount = "0";
            }

            if (totalNoForm.equalsIgnoreCase("0")) {
                aggregateAmount = "0";
            } else {
                if (aggregateAmount.equalsIgnoreCase("0")) {
                    setMessage("Please fill Aggregate Amount of income for which Form No. 15G/15H filed !");
                    return;
                }
            }

            if (otherIncome.equalsIgnoreCase("")) {
                otherIncome = "0";
            }

            if (deductionAmt.equalsIgnoreCase("")) {
                deductionAmt = "0";
            }
            if (this.getFnDetails().equalsIgnoreCase("A")) {
                if ((custId == null) || (custId.equalsIgnoreCase(""))) {
                    this.setMessage("Please Enter Customer Id");
                    return;
                }
            } else {
                if ((custNumber == null) || (custNumber.equalsIgnoreCase(""))) {
                    this.setMessage("Please Select Customer Id");
                    return;
                }
            }

            if (dates == null) {
                this.setMessage("Please Enter Date Of Submission.");
                return;
            }
            if ((fYear == null) || (fYear.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Fyear.");
                return;
            }
            long dayDiff = CbsUtil.dayDiff(dates, new Date());
            if (dayDiff < 0) {
                this.setMessage("You can't select the date after the current date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            //String saveData = tdRemote.saveData(ymd.format(this.dates), details, accountNo, getUserName(), getOrgnBrCode(), fYear);
            if (this.getBtnValue().equalsIgnoreCase("Save")) {
                List resultLt = new ArrayList();
                if (incomeTaxChecking == true) {
                    if (assYear == null || assYear.equalsIgnoreCase("")) {
                        setMessage("Please fill proper 4 digits Latest assessment year !");
                        return;
                    }
                }
                List formcheckingList = tdRemote.form15G15HChecking(custId);
                if (!formcheckingList.isEmpty()) {
                    throw new ApplicationException("Form fill up already this customer !");
                }
                double taxableIncome = 0d;
                resultLt = tdRemote.customerValidateData(custId);
                Vector vtr = (Vector) resultLt.get(0);
                String dob = vtr.get(0).toString();
                String panNo = vtr.get(1).toString();
                //|| fts.getCodeForReportName("SOCIETY-BANK") == 1 && !legalEntity.equalsIgnoreCase("02")
                if (!(tdsDetails.equalsIgnoreCase("Exemption certificate") || tdsDetails.equalsIgnoreCase("Cooperative Society"))) {

                    if (fts.getCodeForReportName("SOCIETY-BANK") == 0) {
                        if (tdsDetails.equalsIgnoreCase("Form 15H")) {
                            taxableIncome = 300000;
                            String gDt = CbsUtil.yearAdd(ymd.format(sdf.parse(dob)), 60);
                            if (ymd.parse(gDt).after(new Date())) {
                                throw new ApplicationException("Customer Age should be greater than 60.");
                            }

                            if (!panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                throw new ApplicationException("Customer Status should be individual.");
                            }

                            int age = CbsUtil.yearDiff(sdf.parse(dob), dates);

                            if (age >= 80) {
                                taxableIncome = 500000;
                            }
                        } else {
                            taxableIncome = 250000;
                            String gDt = CbsUtil.yearAdd(ymd.format(sdf.parse(dob)), 60);
                            if (sdf.parse(getTodayDate()).after(ymd.parse(gDt)) && panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                throw new ApplicationException("Customer Age should be less than 60.");
                            }
                            if (sdf.parse(getTodayDate()).getTime() >= ymd.parse(gDt).getTime() && panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                throw new ApplicationException("Customer Age should be less than 60.");
                            }

                            if (!(panNo.trim().substring(3, 4).equalsIgnoreCase("P") || panNo.trim().substring(3, 4).equalsIgnoreCase("H"))) {
                                throw new ApplicationException("Customer Status should be individual or HUF.");
                            }
                        }
                        int finEndYear = Integer.parseInt(fYear) + 1;
                        String finStartDt = fYear + "0401";
                        String finEndDt = String.valueOf(finEndYear) + "0331";
                        List minorIdList = tdRemote.minorIdData(custId);
                        double totalIntAmt = 0d;
                        if (minorIdList.isEmpty()) {
                            List acnoList = tdRemote.customerData(custId,finStartDt);
                            // double totalIntAmt = 0d;
                            if (!acnoList.isEmpty()) {
                                float voncherNo = 0;
                                for (int i = 0; i < acnoList.size(); i++) {
                                    Vector ele = (Vector) acnoList.get(i);
                                    String tdAcno = ele.get(0).toString();
                                    if (!ele.get(1).toString().equalsIgnoreCase("")) {
                                        voncherNo = Float.parseFloat(ele.get(1).toString());
                                    }
                                    String acNature = fts.getAcNatureByCode(tdAcno.substring(2, 4));
                                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                        String intAmt = tdRemote.getTotalIntAmtDuringFinYear(tdAcno, finStartDt, finEndDt, voncherNo);
                                        double fdInt = Double.parseDouble(intAmt);
                                        totalIntAmt = totalIntAmt + fdInt;
                                    } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                        double rdInt = tdRemote.getRdProjectedInt(tdAcno, finStartDt, finEndDt);
                                        totalIntAmt = totalIntAmt + rdInt;
//                                        List<TdIntDetail> rdSubList = tdRemote.rdAcWiseIntCalc(tdAcno, finStartDt, finEndDt, tdAcno.substring(0, 2));
//                                        double rdInt = 0;
//                                        if (!rdSubList.isEmpty()) {
//                                            rdInt = rdSubList.get(0).getInterest();
//                                        }
//                                        totalIntAmt = totalIntAmt + rdInt;
                                    }
                                }
                            }
                        } else {
                            for (int j = 0; j < minorIdList.size(); j++) {
                                Vector minorVector = (Vector) minorIdList.get(j);
                                String minorCustId = minorVector.get(0).toString();
                                List acnoList = tdRemote.customerData(minorCustId,finStartDt);
                                //  double totalIntAmt = 0d;
                                if (!acnoList.isEmpty()) {
                                    float voncherNo = 0;
                                    for (int i = 0; i < acnoList.size(); i++) {
                                        Vector ele = (Vector) acnoList.get(i);
                                        String tdAcno = ele.get(0).toString();
                                        if (!ele.get(1).toString().equalsIgnoreCase("")) {
                                            voncherNo = Float.parseFloat(ele.get(1).toString());
                                        }
                                        String acNature = fts.getAcNatureByCode(tdAcno.substring(2, 4));
                                        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                            String intAmt = tdRemote.getTotalIntAmtDuringFinYear(tdAcno, finStartDt, finEndDt, voncherNo);
                                            double fdInt = Double.parseDouble(intAmt);
                                            totalIntAmt = totalIntAmt + fdInt;
                                        } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                            double rdInt = tdRemote.getRdProjectedInt(tdAcno, finStartDt, finEndDt);
                                            totalIntAmt = totalIntAmt + rdInt;

//                                            List<TdIntDetail> rdSubList = tdRemote.rdAcWiseIntCalc(tdAcno, finStartDt, finEndDt, getOrgnBrCode());
//                                            double rdInt = 0;
//                                            if (!rdSubList.isEmpty()) {
//                                                rdInt = rdSubList.get(0).getInterest();
//                                            }
//                                            totalIntAmt = totalIntAmt + rdInt;
                                        }
                                    }
                                }
                            }
                        }
                        this.estimatedIncome = totalIntAmt;
                        double totalIncome = 0d;
                        if (!(taxOption.equalsIgnoreCase("02") && tdsDetails.equalsIgnoreCase("Form 15H"))) {
                            if (totalIntAmt > taxableIncome) {
                                throw new ApplicationException("This Customer is not eligibility for " + tdsDetails + " !");
                            }
                            totalIncome = (totalIntAmt + Double.parseDouble(otherIncome) + Double.parseDouble(aggregateAmount)) - Double.parseDouble(deductionAmt);
                            if (totalIncome > taxableIncome) {
                                throw new ApplicationException("This Customer is not eligibility for " + tdsDetails + " !");
                            }
                        }
                    } else {
                        if (!legalEntity.equalsIgnoreCase("02")) {
                            if (tdsDetails.equalsIgnoreCase("Form 15H")) {
                                taxableIncome = 300000;
                                String gDt = CbsUtil.yearAdd(ymd.format(sdf.parse(dob)), 60);
                                if (ymd.parse(gDt).after(new Date())) {
                                    throw new ApplicationException("Customer Age should be greater than 60.");
                                }

                                if (!panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                    throw new ApplicationException("Customer Status should be individual.");
                                }

                                int age = CbsUtil.yearDiff(sdf.parse(dob), dates);

                                if (age >= 80) {
                                    taxableIncome = 500000;
                                }
                            } else {
                                taxableIncome = 250000;
                                String gDt = CbsUtil.yearAdd(ymd.format(sdf.parse(dob)), 60);
                                if (sdf.parse(getTodayDate()).after(ymd.parse(gDt)) && panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                    throw new ApplicationException("Customer Age should be less than 60.");
                                }
                                if (sdf.parse(getTodayDate()).getTime() >= ymd.parse(gDt).getTime() && panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                    throw new ApplicationException("Customer Age should be less than 60.");
                                }

                                if (!(panNo.trim().substring(3, 4).equalsIgnoreCase("P") || panNo.trim().substring(3, 4).equalsIgnoreCase("H"))) {
                                    throw new ApplicationException("Customer Status should be individual or HUF.");
                                }
                            }
                            int finEndYear = Integer.parseInt(fYear) + 1;
                            String finStartDt = fYear + "0401";
                            String finEndDt = String.valueOf(finEndYear) + "0331";
                            List minorIdList = tdRemote.minorIdData(custId);
                            double totalIntAmt = 0d;
                            if (minorIdList.isEmpty()) {
                                List acnoList = tdRemote.customerData(custId,finStartDt);
                                // double totalIntAmt = 0d;
                                if (!acnoList.isEmpty()) {
                                    float voncherNo = 0;
                                    for (int i = 0; i < acnoList.size(); i++) {
                                        Vector ele = (Vector) acnoList.get(i);
                                        String tdAcno = ele.get(0).toString();
                                        if (!ele.get(1).toString().equalsIgnoreCase("")) {
                                            voncherNo = Float.parseFloat(ele.get(1).toString());
                                        }
                                        String acNature = fts.getAcNatureByCode(tdAcno.substring(2, 4));
                                        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                            String intAmt = tdRemote.getTotalIntAmtDuringFinYear(tdAcno, finStartDt, finEndDt, voncherNo);
                                            double fdInt = Double.parseDouble(intAmt);
                                            totalIntAmt = totalIntAmt + fdInt;
                                        } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                            double rdInt = tdRemote.getRdProjectedInt(tdAcno, finStartDt, finEndDt);
                                            totalIntAmt = totalIntAmt + rdInt;
//                                            List<TdIntDetail> rdSubList = tdRemote.rdAcWiseIntCalc(tdAcno, finStartDt, finEndDt, tdAcno.substring(0, 2));
//                                            double rdInt = 0;
//                                            if (!rdSubList.isEmpty()) {
//                                                rdInt = rdSubList.get(0).getInterest();
//                                            }
//                                            totalIntAmt = totalIntAmt + rdInt;
                                        }
                                    }
                                }
                            } else {
                                for (int j = 0; j < minorIdList.size(); j++) {
                                    Vector minorVector = (Vector) minorIdList.get(j);
                                    String minorCustId = minorVector.get(0).toString();
                                    List acnoList = tdRemote.customerData(minorCustId,finStartDt);
                                    //  double totalIntAmt = 0d;
                                    if (!acnoList.isEmpty()) {
                                        float voncherNo = 0;
                                        for (int i = 0; i < acnoList.size(); i++) {
                                            Vector ele = (Vector) acnoList.get(i);
                                            String tdAcno = ele.get(0).toString();
                                            if (!ele.get(1).toString().equalsIgnoreCase("")) {
                                                voncherNo = Float.parseFloat(ele.get(1).toString());
                                            }
                                            String acNature = fts.getAcNatureByCode(tdAcno.substring(2, 4));
                                            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                                String intAmt = tdRemote.getTotalIntAmtDuringFinYear(tdAcno, finStartDt, finEndDt, voncherNo);
                                                double fdInt = Double.parseDouble(intAmt);
                                                totalIntAmt = totalIntAmt + fdInt;
                                            } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                                double rdInt = tdRemote.getRdProjectedInt(tdAcno, finStartDt, finEndDt);
                                                totalIntAmt = totalIntAmt + rdInt;
//                                                List<TdIntDetail> rdSubList = tdRemote.rdAcWiseIntCalc(tdAcno, finStartDt, finEndDt, getOrgnBrCode());
//                                                double rdInt = 0;
//                                                if (!rdSubList.isEmpty()) {
//                                                    rdInt = rdSubList.get(0).getInterest();
//                                                }
//                                                totalIntAmt = totalIntAmt + rdInt;
                                            }
                                        }
                                    }
                                }
                            }
                            this.estimatedIncome = totalIntAmt;
                            double totalIncome = 0d;
                            if (!(taxOption.equalsIgnoreCase("02") && tdsDetails.equalsIgnoreCase("Form 15H"))) {
                                if (totalIntAmt > taxableIncome) {
                                    throw new ApplicationException("This Customer is not eligibility for " + tdsDetails + " !");
                                }
                                totalIncome = (totalIntAmt + Double.parseDouble(otherIncome) + Double.parseDouble(aggregateAmount)) - Double.parseDouble(deductionAmt);
                                if (totalIncome > taxableIncome) {
                                    throw new ApplicationException("This Customer is not eligibility for " + tdsDetails + " !");
                                }
                            }
                        }
                    }
                }
                if (tdEntries.isEmpty()) {
                    this.setMessage("No Data Exist To Save");
                    return;
                }
                // Dhiru Sir ask
                if (tdsDetails.equalsIgnoreCase("Exemption certificate") || tdsDetails.equalsIgnoreCase("Cooperative Society")) {
                    this.estimatedIncome = totalProjectedInt;
                }
                String saveData = tdRemote.saveData(ymd.format(this.dates), tdEntries, custId, getUserName(), getOrgnBrCode(), fYear, this.getTdsDetails(), Double.parseDouble(totalNoForm), Double.parseDouble(aggregateAmount), Double.parseDouble(otherIncome), Double.parseDouble(deductionAmt), estimatedIncome, assYear, taxOption);
                if (saveData.equals("Please Enter Correct FYear")) {
                    this.setMessage(saveData);
                    setfYear("");
                    return;
                }
                this.setMessage(saveData);
            }
            if (this.getBtnValue().equalsIgnoreCase("Update")) {
                String upDateList = tdRemote.upDateData(ymd.format(this.dates), tdEntries, fYear, custNumber, getUserName());
                if (upDateList.equals("Please Enter Correct FYear")) {
                    this.setMessage(upDateList);
                    setfYear("");
                    return;
                }
                this.setMessage(upDateList);
            }
            if (this.getBtnValue().equalsIgnoreCase("Delete")) {
                String aFlag = tdRemote.authFlagChk(custNumber);
                if (aFlag.equalsIgnoreCase("Y")) {
                    this.setMessage("You Can't Delete Partial Marked Entry");
                    return;
                }

                String delList = tdRemote.deleteData(ymd.format(this.dates), fYear, custNumber);
                if (delList.equals("Please Enter Correct FYear")) {
                    this.setMessage(delList);
                    setfYear("");
                    return;
                }
                this.setMessage(delList);
            }
            if (this.getBtnValue().equalsIgnoreCase("Verify")) {
                if (fts.isUserAuthorized(getUserName(), getOrgnBrCode())) {
                    for (int i = 0; i < tdEntries.size(); i++) {
                        if (tdEntries.get(i).getEnterBy().equalsIgnoreCase(this.getUserName())) {
                            this.setMessage("You Can't Authorize Your Own Entry");
                            return;
                        }
                    }

                    String delList = tdRemote.verifyData(tdEntries, custNumber, this.getUserName(), this.tdsDetails);
                    if (delList.equals("Please Enter Correct FYear")) {
                        this.setMessage(delList);
                        setfYear("");
                        return;
                    }
                    this.setMessage(delList);
                } else {
                    this.setMessage("You are not authorized person for verifing this detail.");
                    return;
                }
            }

            tdEntries = new ArrayList<TdEntry>();
            custList = new ArrayList<SelectItem>();

            setCustId("");
            setCustName("");
            setPanNo("");
            setFatherName("");
            setfYear("");
            setDob("");
            setTotalNoForm("");
            setAggregateAmount("");
            setOtherIncome("");
            setDeductionAmt("");
            this.disableAssYear = true;
            this.incomeTaxChecking = false;
            this.assYear = "";
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetAllValue() {
        try {
            setFnDetails("A");
            setCustName("");
            setPanNo("");
            setFatherName("");
            setDob("");
            setCustId("");
            setDates(new Date());
            setMessage("");
            this.taxOption = "";
            this.tdsDetails = "Form 15H";
            tdEntries = new ArrayList<TdEntry>();
            fYearDropDownData();
            setSaveDisable(true);
            setBtnValue("Save");
            setAddVisible("");
            setOthVisible("none");
            setDocDisable(false);
            setCustIdStyle("block");
            setVerifyStyle("none");
            setDisMsg("");
            setTotalNoForm("");
            setAggregateAmount("");
            setOtherIncome("");
            setDeductionAmt("");
            this.disableAssYear = true;
            this.incomeTaxChecking = false;
            this.assYear = "";
            custList = new ArrayList<SelectItem>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getDetails() {
        try {
            custList = new ArrayList<SelectItem>();
            List list = tdRemote.getDetailsList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                custList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitFrm() {
        resetAllValue();
        return "case1";
    }
}
