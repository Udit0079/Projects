/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.constant.CbsConstant;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.pojo.PremiumDetailsPojo;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class InsuranceDetails extends BaseBean {

    private String message;
    private String txnType;
    private String acno;
    private String memNo;
    private String startDt;
    private String tillDt;
    private String memRegDt;
    private String memRetiredDt;
    private String policyNo;
    private String odLimit;
    private String acnoEnable = "none";
    private String odLimitEnable = "none";
    private String memEnable = "none";
    private String insEnable = "none";
    private String wellEnable = "none";
    private String insuredAmount;
    private String insurancePremium;
    private boolean btnActive;
    private List<SelectItem> txnTypeList;
    private List<PremiumDetailsPojo> tableDataList;
    private final String jndiHomeName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiName = "PostalDetailFacade";
    private PostalDetailFacadeRemote remote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String bnkIdenStr;
    private boolean disFrDt;
    private boolean disToDt;
    private String custName;

    public InsuranceDetails() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            remote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            bnkIdenStr = ftsRemote.getCodeFromCbsParameterInfo("BNK_IDENTIFIER");
            fillCombos();
            btnRefreshAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillCombos() {
        txnTypeList = new ArrayList<SelectItem>();
        try {
            txnTypeList.add(new SelectItem("S", "--Select--"));
            /* bnkIdenStr   P : POSTAL
             A : ARMY
             */
            if (bnkIdenStr.equalsIgnoreCase("P")) {
                txnTypeList.add(new SelectItem("WL", "WellFare"));
                txnTypeList.add(new SelectItem("WL-D", "WellFare Details"));
            } else if (bnkIdenStr.equalsIgnoreCase("A")) {
                txnTypeList.add(new SelectItem("THA", "Theft Account No."));
                txnTypeList.add(new SelectItem("WL", "WellFare"));
                txnTypeList.add(new SelectItem("WL-D", "WellFare Details"));
            } else {
                txnTypeList.add(new SelectItem("INS", "Insurance Premium"));
                txnTypeList.add(new SelectItem("INS-D", "Insurance Details"));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processTxnAction() {
        this.setMessage("");
        this.setDisFrDt(false);
        this.setDisToDt(false);
        try {
            if (this.txnType.equals("INS")) {
                this.acnoEnable = "";
                this.insEnable = "";
                this.memEnable = "none";
                this.wellEnable = "none";
                this.odLimitEnable = "none";
                this.custName = "";
            } else if (this.txnType.equals("WL")) {
                this.acnoEnable = "none";
                this.insEnable = "none";
                this.memEnable = "";
                this.wellEnable = "";
                if (bnkIdenStr.equalsIgnoreCase("P")) {
                    this.odLimitEnable = "none";
                } else {
                    this.odLimitEnable = "";
                    this.setDisFrDt(true);
                    this.setDisToDt(true);
                }
                this.custName = "";
            } else if (this.txnType.equals("INS-D")) {
                this.acnoEnable = "";
                this.insEnable = "none";
                this.memEnable = "none";
                this.wellEnable = "none";
                this.odLimitEnable = "none";
                this.custName = "";
            } else if (this.txnType.equals("WL-D")) {
                this.acnoEnable = "none";
                this.insEnable = "none";
                this.memEnable = "";
                this.wellEnable = "none";
                this.odLimitEnable = "none";
                this.custName = "";
            } else if (this.txnType.equals("THA")) {
                this.acnoEnable = "";
                this.odLimitEnable = "";
                this.insEnable = "none";
                this.memEnable = "none";
                this.wellEnable = "none";
                this.custName = "";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAccountAction() {
        this.setMessage("");
        try {
            String valMsg = validateAccount();
            if (valMsg.equals("true")) {
                if (this.txnType.equals("INS-D")) {
                    tableDataList = new ArrayList<PremiumDetailsPojo>();
                    List dataList = remote.getPrimiumAndWellFareDetails("INS", this.acno);
                    if (dataList.isEmpty()) {
                        this.setMessage("There is no data to show.");
                    } else {
                        createTableToShow(dataList);
                    }
                } else if (this.txnType.equals("THA")) {
                    this.btnActive = false;
                    this.custName = ftsRemote.getCustName(acno);
                    this.odLimit = remote.getTheftAmt(acno);

                }
            } else {
                this.setMessage(valMsg);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateAccount() {
        String result = "true";
        try {
            if (this.acno == null || this.acno.equals("")) {
                return result = "Please fill account no.";
            } else if (this.acno.length() < 12) {
                return result = "Please fill 12 digit account no.";
            } else if (!ftsRemote.getCurrentBrnCode(acno).equalsIgnoreCase(getOrgnBrCode())) {
                return result = "Please fill self branch loan a/c no.";
            }

            if (!this.txnType.equals("THA")) {
                if (!ftsRemote.getAccountNature(this.acno).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    return result = "Please fill loan nature a/c no.";
                }
            } else {
                if (!ftsRemote.getAccountNature(this.acno).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    return result = "Please fill saving nature a/c no.";
                }
            }
            String checkAcno = ftsRemote.getNewAccountNumber(this.acno);
            if (checkAcno == null || checkAcno.equals("")) {
                return result = "Account number does not exist.";
            }
            checkAcno = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
            if (!checkAcno.equalsIgnoreCase("true")) {
                return checkAcno;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return result;
    }

    public void createTableToShow(List dataList) {
        try {
            for (int i = 0; i < dataList.size(); i++) {
                PremiumDetailsPojo pojo = new PremiumDetailsPojo();
                Vector element = (Vector) dataList.get(i);
                pojo.setAcno(element.get(0).toString());
                pojo.setSno(Integer.parseInt(element.get(1).toString()));
                pojo.setDueDt(element.get(2).toString());
                pojo.setAmount(Double.parseDouble(element.get(3).toString()));
                pojo.setPremiumAmount(Double.parseDouble(element.get(4).toString()));
                pojo.setStatus(element.get(5).toString());
                pojo.setPaymentDt(element.get(6).toString());
                pojo.setEnteryBy(element.get(7).toString());
                pojo.setPeriodicity(element.get(8).toString());
                pojo.setExcessAmount(Double.parseDouble(element.get(9).toString()));
                pojo.setPolicyNo(element.get(10).toString());

                tableDataList.add(pojo);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processMemberNo() {
        this.setMessage("");
        try {
            if (this.memNo == null || this.memNo.equals("")) {
                this.setMessage("Please fill Member No.");
                return;
            } else if (this.memNo.length() < 12) {
                this.setMessage("Please fill 12 digit Member No.");
                return;
            }
            if (this.txnType.equals("WL-D")) {
                this.custName = remote.getCustNameByFolioNo(memNo);
                tableDataList = new ArrayList<PremiumDetailsPojo>();
                List dataList = remote.getPrimiumAndWellFareDetails("WL", this.memNo);
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data to show.");
                } else {
                    createTableToShow(dataList);
                }
            } else if (this.txnType.equals("WL")) {
                this.custName = remote.getCustNameByFolioNo(memNo);
                this.btnActive = true;
                //Check for already existence insurance premium.
//                List dataList = remote.getPrimiumAndWellFareDetails(this.txnType, this.memNo);
//                if (!dataList.isEmpty()) {
//                    this.setMessage("This menber has alreay wellfare details.");
//                    return;
//                }

                List dataList = remote.getMemberRegistrationDt(this.memNo);
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data in share holder corresponding to the member no:- " + this.memNo);
                } else {
                    Vector element = (Vector) dataList.get(0);
                    memRegDt = element.get(0).toString();
                    String memDob = element.get(1).toString();

                    int year = remote.getCodeByReportName("MEMBER-WORKING-PERIOD");
                    if (year == 0) {
                        this.setMessage("Please define Member Working Period.");
                        return;
                    }
                    memRetiredDt = dmy.format(ymd.parse(CbsUtil.yearAdd(ymd.format(dmy.parse(memDob)), year)));

                    double wellFareFund = 0.0;

                    List wLst = remote.getWellFareFund(this.txnType);
                    if (wLst.isEmpty()) {
                        this.setMessage("Please define Welfare Fund Master");
                        return;
                    }

                    Vector element1 = (Vector) wLst.get(0);
                    wellFareFund = Double.parseDouble(element1.get(0).toString());
                    String fre = element1.get(1).toString();

                    generateWellFareDetails(this.txnType, this.memNo, memRegDt, memRetiredDt, wellFareFund, fre);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void generateWellFareDetails(String type, String no, String registrationDt, String retirDt, double fund, String fre) {
        tableDataList = new ArrayList<PremiumDetailsPojo>();
        try {
            String regMonth = registrationDt.substring(3, 5);
            String regYear = registrationDt;
            Integer totalWellFareCount = 0;
            String firstDueDt = "";
            if (fre.equalsIgnoreCase("Y")) {
                totalWellFareCount = CbsUtil.yearDiff(dmy.parse(registrationDt), dmy.parse(retirDt));

                if (Integer.parseInt(regMonth) >= 4 && Integer.parseInt(regMonth) <= 12) {
                    regYear = dmy.format(ymd.parse(CbsUtil.yearAdd(ymd.format(dmy.parse(registrationDt)), 1)));
                }
                firstDueDt = "31/03/" + regYear.substring(6);
            } else if (fre.equalsIgnoreCase("O")) {//Only one entry will show 
                totalWellFareCount = 1;

                if (Integer.parseInt(regMonth) >= 4 && Integer.parseInt(regMonth) <= 12) {
                    regYear = dmy.format(ymd.parse(CbsUtil.yearAdd(ymd.format(dmy.parse(registrationDt)), 1)));
                }
                firstDueDt = "31/03/" + regYear.substring(6);
            } else {
                totalWellFareCount = CbsUtil.monthDiff(dmy.parse(registrationDt), dmy.parse(retirDt));
                String mDate = CbsUtil.monthLast(ymd.format(dmy.parse(registrationDt)), 1);
                firstDueDt = mDate.substring(6) + "/" + mDate.substring(4, 6) + "/" + mDate.substring(0, 4);
            }
            if (!fre.equalsIgnoreCase("O")) {
                //Create First Well Fare Fund Details.
                PremiumDetailsPojo pojo = new PremiumDetailsPojo();
                pojo.setTxnType(type);
                pojo.setAcno(no);
                pojo.setSno(1);
                pojo.setDueDt(firstDueDt);
                pojo.setAmount(fund);
                pojo.setPremiumAmount(0.0);
                pojo.setStatus("UNPAID");
                pojo.setPaymentDt("01/01/1900");
                pojo.setEnteryBy(getUserName());
                pojo.setPeriodicity("Y");
                pojo.setExcessAmount(0.0);
                pojo.setPolicyNo("");

                tableDataList.add(pojo);

                String tempStartDt = ymd.format(dmy.parse(firstDueDt));
                for (int i = 0; i < (totalWellFareCount - 1); i++) {
                    if (fre.equalsIgnoreCase("Y")) {
                        tempStartDt = CbsUtil.yearAdd(tempStartDt, 1);
                    } else {
                        tempStartDt = CbsUtil.monthLast(tempStartDt, 1);
                    }

                    PremiumDetailsPojo obj = new PremiumDetailsPojo();
                    obj.setTxnType(type);
                    obj.setAcno(no);
                    obj.setSno(i + 2);
                    obj.setAmount(fund);
                    obj.setPremiumAmount(0.0);
                    obj.setStatus("UNPAID");
                    obj.setPaymentDt("01/01/1900");
                    obj.setEnteryBy(getUserName());
                    obj.setPeriodicity("Y");
                    obj.setExcessAmount(0.0);
                    obj.setPolicyNo("");
                    if (ymd.parse(tempStartDt).compareTo(dmy.parse(retirDt)) > 0) {
                        obj.setDueDt(retirDt);
                    } else {
                        obj.setDueDt(dmy.format(ymd.parse(tempStartDt)));
                    }
                    tableDataList.add(obj);
                }
            } else {
                double preAmtPaid = 0.0;
                String pymtDt = "01/01/1900";
                List dataList = this.remote.getPrimiumAmtDetails(this.txnType, no);
                if (!dataList.isEmpty()) {
                    Vector dataVect = (Vector) dataList.get(0);
                    preAmtPaid = Double.parseDouble(dataVect.get(0).toString());
                    pymtDt = this.dmy.format(this.ymd.parse(dataVect.get(1).toString()));
                }
                PremiumDetailsPojo pojo = new PremiumDetailsPojo();
                pojo.setTxnType(type);
                pojo.setAcno(no);
                pojo.setSno(1);
                pojo.setDueDt(firstDueDt);
                pojo.setAmount(fund);
                pojo.setPremiumAmount(preAmtPaid);
                pojo.setStatus(preAmtPaid >= fund ? "PAID" : "UNPAID");
                pojo.setPaymentDt(pymtDt.equalsIgnoreCase("01/01/1900") ? "" : pymtDt);
                pojo.setEnteryBy(getUserName());
                pojo.setPeriodicity("O");
                pojo.setExcessAmount(0.0);
                pojo.setPolicyNo("");

                tableDataList.add(pojo);
            }
            this.btnActive = false;
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String insuranceFieldValidationProcess() {
        String result = "true";
        try {
            String msg = validateAccount();
            if (!msg.equals("true")) {
                return result = msg;
            }
            if (this.insuredAmount == null || insuredAmount.equals("")) {
                return result = "Please fill Insured Amount.";

            }
            msg = validateAmount(insuredAmount);
            if (!msg.equals("true")) {
                return result = msg;
            }
            if (this.insurancePremium == null || insurancePremium.equals("")) {
                return result = "Please fill Insurance Premium.";

            }
            msg = validateAmount(insurancePremium);
            if (!msg.equals("true")) {
                return result = msg;
            }
            if (this.policyNo == null || policyNo.equals("")) {
                return result = "Please fill Policy No.";

            }
            msg = validateDt(this.startDt);
            if (!msg.equals("true")) {
                return result = msg;
            }
            msg = validateDt(this.tillDt);
            if (!msg.equals("true")) {
                return result = msg;
            }
            if (dmy.parse(tillDt).compareTo(dmy.parse(startDt)) <= 0) {
                return result = "Please fill proper till date.";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return result;
    }

    public String validateAmount(String amount) {
        String result = "true";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            Matcher m = p.matcher(amount);
            if (amount == null || amount.equals("")) {
                return result = "Amount can not be blank.";
            } else if (m.matches() != true) {
                return result = "Please fill proper amount.";
            } else if (amount.equalsIgnoreCase("0") || amount.equalsIgnoreCase("0.0")) {
                return result = "Amount can not be zero.";
            } else if (new BigDecimal(amount).compareTo(new BigDecimal("0")) == -1) {
                return result = "Amount can not be negative.";
            }
            if (amount.contains(".")) {
                if (amount.indexOf(".") != amount.lastIndexOf(".")) {
                    return result = "Invalid amount. Please fill the amount correctly.";
                } else if (amount.substring(amount.indexOf(".") + 1).length() > 2) {
                    return result = "Please fill the amount upto two decimal places.";
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return result;
    }

    public void createInsuranceDetails() {
        this.setMessage("");
        tableDataList = new ArrayList<PremiumDetailsPojo>();
        try {
            String checkVal = insuranceFieldValidationProcess();
            if (checkVal.equals("true")) {
                this.btnActive = true;
                //Check for already existence insurance premium.
                List dataList = remote.getPrimiumAndWellFareDetails(this.txnType, this.acno);
                if (!dataList.isEmpty()) {
                    this.setMessage("This A/c has alreay insurance premium.");
                    return;
                }
                //Create First Insurance Premiums.
                PremiumDetailsPojo pojo = new PremiumDetailsPojo();
                pojo.setTxnType(this.txnType);
                pojo.setAcno(this.acno);
                pojo.setSno(1);
                pojo.setDueDt(startDt);
                pojo.setAmount(Double.parseDouble(this.insuredAmount));
                pojo.setPremiumAmount(Double.parseDouble(this.insurancePremium));
                pojo.setStatus("UNPAID");
                pojo.setPaymentDt("01/01/1900");
                pojo.setEnteryBy(getUserName());
                pojo.setPeriodicity("M");
                pojo.setExcessAmount(0.0);
                pojo.setPolicyNo(this.policyNo);

                tableDataList.add(pojo);

                Integer totalInsPremiumCount = CbsUtil.monthDiff(dmy.parse(startDt), dmy.parse(tillDt));
                String tempStartDt = ymd.format(dmy.parse(startDt));
                for (int i = 0; i < (totalInsPremiumCount - 1); i++) {
                    tempStartDt = CbsUtil.monthAdd(tempStartDt, 1);
                    if (ymd.parse(tempStartDt).compareTo(dmy.parse(tillDt)) > 0) {
                        break;
                    } else {
                        PremiumDetailsPojo obj = new PremiumDetailsPojo();
                        obj.setTxnType(this.txnType);
                        obj.setAcno(this.acno);
                        obj.setSno(i + 2);
                        obj.setDueDt(dmy.format(ymd.parse(tempStartDt)));
                        obj.setAmount(Double.parseDouble(this.insuredAmount));
                        obj.setPremiumAmount(Double.parseDouble(this.insurancePremium));
                        obj.setStatus("UNPAID");
                        obj.setPaymentDt("01/01/1900");
                        obj.setEnteryBy(getUserName());
                        obj.setPeriodicity("M");
                        obj.setExcessAmount(0.0);
                        obj.setPolicyNo(this.policyNo);

                        tableDataList.add(obj);
                    }
                }
                this.btnActive = false;
            } else {
                this.setMessage(checkVal);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateDt(String passedDt) {
        String result = "true";
        try {
            if (passedDt == null || passedDt.equals("")) {
                return "Please fill Start/Till Date.";
            }
            boolean chqVal = new Validator().validateDate_dd_mm_yyyy(passedDt);
            if (chqVal == false) {
                return "Please fill proper Start/Till date.";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return result;
    }

    public void btnSaveAction() {
        this.setMessage("");

        try {
            if (txnType.equalsIgnoreCase("S")) {
                setMessage("Please select Transaction Type !");
                return;
            }
            if (this.txnType.equals("THA")) {
                String valMsg = validateAccount();
                if (!valMsg.equals("true")) {
                    setMessage(valMsg);
                    return;
                }
            } else {
                if (tableDataList.isEmpty()) {
                    this.setMessage("There is no data to generate details.");
                    return;
                }
            }

            String result = remote.generateInsuranceAndWellFare(tableDataList, this.acno, this.odLimit, txnType);
            if (!result.equals("true")) {
                this.setMessage(result);
            } else {
                btnRefreshAction();
                if (this.txnType.equals("THA")) {
                    this.setMessage("Theft Amount update successfully.");
                } else {
                    this.setMessage("Premium / WellFare is generated successfully.");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setTxnType("S");
        this.setAcno("");
        this.setCustName("");
        this.setPolicyNo("");
        this.setInsuredAmount("");
        this.setInsurancePremium("");
        this.setStartDt("");
        this.setTillDt("");
        this.setMemNo("");
        this.setMemRegDt("");
        this.setMemRetiredDt("");
        this.btnActive = true;
        this.acnoEnable = "none";
        this.memEnable = "none";
        this.insEnable = "none";
        this.wellEnable = "none";
        this.odLimit = "";
        this.odLimitEnable = "none";
        tableDataList = new ArrayList<PremiumDetailsPojo>();
        this.setDisFrDt(false);
        this.setDisToDt(false);
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    /**
     * Getter And Setter
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public List<SelectItem> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<SelectItem> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public boolean isBtnActive() {
        return btnActive;
    }

    public void setBtnActive(boolean btnActive) {
        this.btnActive = btnActive;
    }

    public FtsPostingMgmtFacadeRemote getFtsRemote() {
        return ftsRemote;
    }

    public void setFtsRemote(FtsPostingMgmtFacadeRemote ftsRemote) {
        this.ftsRemote = ftsRemote;
    }

    public List<PremiumDetailsPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<PremiumDetailsPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public String getAcnoEnable() {
        return acnoEnable;
    }

    public void setAcnoEnable(String acnoEnable) {
        this.acnoEnable = acnoEnable;
    }

    public String getMemEnable() {
        return memEnable;
    }

    public void setMemEnable(String memEnable) {
        this.memEnable = memEnable;
    }

    public String getInsEnable() {
        return insEnable;
    }

    public void setInsEnable(String insEnable) {
        this.insEnable = insEnable;
    }

    public String getWellEnable() {
        return wellEnable;
    }

    public void setWellEnable(String wellEnable) {
        this.wellEnable = wellEnable;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getMemRegDt() {
        return memRegDt;
    }

    public void setMemRegDt(String memRegDt) {
        this.memRegDt = memRegDt;
    }

    public String getMemRetiredDt() {
        return memRetiredDt;
    }

    public void setMemRetiredDt(String memRetiredDt) {
        this.memRetiredDt = memRetiredDt;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(String insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public String getInsurancePremium() {
        return insurancePremium;
    }

    public void setInsurancePremium(String insurancePremium) {
        this.insurancePremium = insurancePremium;
    }

    public String getOdLimit() {
        return odLimit;
    }

    public void setOdLimit(String odLimit) {
        this.odLimit = odLimit;
    }

    public String getOdLimitEnable() {
        return odLimitEnable;
    }

    public void setOdLimitEnable(String odLimitEnable) {
        this.odLimitEnable = odLimitEnable;
    }

    public boolean isDisFrDt() {
        return disFrDt;
    }

    public void setDisFrDt(boolean disFrDt) {
        this.disFrDt = disFrDt;
    }

    public boolean isDisToDt() {
        return disToDt;
    }

    public void setDisToDt(boolean disToDt) {
        this.disToDt = disToDt;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}