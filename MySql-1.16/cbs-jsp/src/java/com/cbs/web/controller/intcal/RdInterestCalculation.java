/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.intcal.RdIntCalTable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class RdInterestCalculation extends BaseBean {

    private String message;
    private String acno;
    private String name;
    private String balance;
    private String matDate;
    private String installment;
    private String period;
    private String ContractedRoi;
    private String ApplicableRate;
    private String PenaltyApplicate;
    private String NetContractedRoi;
    private String interestAmtBeforePenalty;
    private Date FromDate;
    private Date ToDate;
    private String PenaltyAmt;
    private String InterestAmtAfterPenalty;
    private String provision;
    private String remainingIntAmt;
    private String intOption;
    private boolean flagDisable;
    private String d1;
    private String d2;
    private boolean optionDisable;
    private boolean postDisable;
    private List<RdIntCalTable> rdTable;
    private List<SelectItem> intOptionList;
    private boolean btnDisabled = true;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiName = "RDIntCalFacade";
    private RDIntCalFacadeRemote rdIntCalFacadeRemote = null;
    private FtsPostingMgmtFacadeRemote fts = null;
    String oldAccNo, acNoLen;
    private String tdsToBeDeducted;
    private String tdsDeducted;
    private String deductForLastFinalFear;
    private String closeActTdsToBeDeducted;
    private String closeActTdsDeducted;
    private String closeActIntFinYear;
    private String intPaid;
    private String function;
    private List<SelectItem> functionList;
    private String bntButton;
    private String inputDisFlag;
    private String inputDisFlag1;
    private String pendingAcNo;
    private List<SelectItem> pendingAcNoList;
    private String enterBy;

    public String getRemainingIntAmt() {
        return remainingIntAmt;
    }

    public void setRemainingIntAmt(String remainingIntAmt) {
        this.remainingIntAmt = remainingIntAmt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getInputDisFlag() {
        return inputDisFlag;
    }

    public void setInputDisFlag(String inputDisFlag) {
        this.inputDisFlag = inputDisFlag;
    }

    public String getInputDisFlag1() {
        return inputDisFlag1;
    }

    public void setInputDisFlag1(String inputDisFlag1) {
        this.inputDisFlag1 = inputDisFlag1;
    }

    public String getPendingAcNo() {
        return pendingAcNo;
    }

    public void setPendingAcNo(String pendingAcNo) {
        this.pendingAcNo = pendingAcNo;
    }

    public List<SelectItem> getPendingAcNoList() {
        return pendingAcNoList;
    }

    public void setPendingAcNoList(List<SelectItem> pendingAcNoList) {
        this.pendingAcNoList = pendingAcNoList;
    }

    public boolean isBtnDisabled() {
        return btnDisabled;
    }

    public void setBtnDisabled(boolean btnDisabled) {
        this.btnDisabled = btnDisabled;
    }

    public boolean isFlagDisable() {
        return flagDisable;
    }

    public void setFlagDisable(boolean flagDisable) {
        this.flagDisable = flagDisable;
    }

    public boolean isPostDisable() {
        return postDisable;
    }

    public void setPostDisable(boolean postDisable) {
        this.postDisable = postDisable;
    }

    public boolean isOptionDisable() {
        return optionDisable;
    }

    public void setOptionDisable(boolean optionDisable) {
        this.optionDisable = optionDisable;
    }

    public String getProvision() {
        return provision;
    }

    public void setProvision(String provision) {
        this.provision = provision;
    }

    public String getIntOption() {
        return intOption;
    }

    public void setIntOption(String intOption) {
        this.intOption = intOption;
    }

    public List<SelectItem> getIntOptionList() {
        return intOptionList;
    }

    public void setIntOptionList(List<SelectItem> intOptionList) {
        this.intOptionList = intOptionList;
    }

    public List<RdIntCalTable> getRdTable() {
        return rdTable;
    }

    public void setRdTable(List<RdIntCalTable> rdTable) {
        this.rdTable = rdTable;
    }

    public String getApplicableRate() {
        return ApplicableRate;
    }

    public void setApplicableRate(String ApplicableRate) {
        this.ApplicableRate = ApplicableRate;
    }

    public String getContractedRoi() {
        return ContractedRoi;
    }

    public void setContractedRoi(String ContractedRoi) {
        this.ContractedRoi = ContractedRoi;
    }

    public Date getFromDate() {
        return FromDate;
    }

    public void setFromDate(Date FromDate) {
        this.FromDate = FromDate;
    }

    public String getInterestAmtAfterPenalty() {
        return InterestAmtAfterPenalty;
    }

    public void setInterestAmtAfterPenalty(String InterestAmtAfterPenalty) {
        this.InterestAmtAfterPenalty = InterestAmtAfterPenalty;
    }

    public String getNetContractedRoi() {
        return NetContractedRoi;
    }

    public void setNetContractedRoi(String NetContractedRoi) {
        this.NetContractedRoi = NetContractedRoi;
    }

    public String getPenaltyAmt() {
        return PenaltyAmt;
    }

    public void setPenaltyAmt(String PenaltyAmt) {
        this.PenaltyAmt = PenaltyAmt;
    }

    public String getPenaltyApplicate() {
        return PenaltyApplicate;
    }

    public void setPenaltyApplicate(String PenaltyApplicate) {
        this.PenaltyApplicate = PenaltyApplicate;
    }

    public Date getToDate() {
        return ToDate;
    }

    public void setToDate(Date ToDate) {
        this.ToDate = ToDate;
    }

    public String getInterestAmtBeforePenalty() {
        return interestAmtBeforePenalty;
    }

    public void setInterestAmtBeforePenalty(String interestAmtBeforePenalty) {
        this.interestAmtBeforePenalty = interestAmtBeforePenalty;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getTdsToBeDeducted() {
        return tdsToBeDeducted;
    }

    public void setTdsToBeDeducted(String tdsToBeDeducted) {
        this.tdsToBeDeducted = tdsToBeDeducted;
    }

    public String getTdsDeducted() {
        return tdsDeducted;
    }

    public void setTdsDeducted(String tdsDeducted) {
        this.tdsDeducted = tdsDeducted;
    }

    public String getCloseActTdsToBeDeducted() {
        return closeActTdsToBeDeducted;
    }

    public void setCloseActTdsToBeDeducted(String closeActTdsToBeDeducted) {
        this.closeActTdsToBeDeducted = closeActTdsToBeDeducted;
    }

    public String getCloseActTdsDeducted() {
        return closeActTdsDeducted;
    }

    public void setCloseActTdsDeducted(String closeActTdsDeducted) {
        this.closeActTdsDeducted = closeActTdsDeducted;
    }

    public String getCloseActIntFinYear() {
        return closeActIntFinYear;
    }

    public void setCloseActIntFinYear(String closeActIntFinYear) {
        this.closeActIntFinYear = closeActIntFinYear;
    }

    public String getDeductForLastFinalFear() {
        return deductForLastFinalFear;
    }

    public void setDeductForLastFinalFear(String deductForLastFinalFear) {
        this.deductForLastFinalFear = deductForLastFinalFear;
    }

    public String getIntPaid() {
        return intPaid;
    }

    public void setIntPaid(String intPaid) {
        this.intPaid = intPaid;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getBntButton() {
        return bntButton;
    }

    public void setBntButton(String bntButton) {
        this.bntButton = bntButton;
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    public RdInterestCalculation() {
        try {
            this.inputDisFlag1 = "none";
            this.inputDisFlag = "";
            rdIntCalFacadeRemote = (RDIntCalFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            setMessage("");
            resetValues();
            rdTable = new ArrayList<RdIntCalTable>();
            intOptionList = new ArrayList<SelectItem>();
            intOptionList.add(new SelectItem("0", " "));
//            intOptionList.add(new SelectItem("1", "No Interest"));
//            intOptionList.add(new SelectItem("2", "Simple Interest Prevailing SB Rate"));
            intOptionList.add(new SelectItem("3", "Pre Mature Interest @ FD Rate"));
            intOptionList.add(new SelectItem("4", "Regular installment Maturity Interest"));
            intOptionList.add(new SelectItem("5", "Irregular installment Maturity Interest"));

            functionList = new ArrayList<>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("1", "Payment"));
            functionList.add(new SelectItem("2", "Verify"));
            functionList.add(new SelectItem("3", "Delete"));

            resetValues();
            setFlagDisable(true);
            setPostDisable(true);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void changeFunction() {
        setMessage("");
        if (function.equalsIgnoreCase("1")) {
            this.inputDisFlag = "";
            this.inputDisFlag1 = "none";
            this.setBntButton("Saved");
        } else if (function.equalsIgnoreCase("2")) {
            this.inputDisFlag1 = "";
            this.inputDisFlag = "none";
            this.setBntButton("Verify");
            getUnAuthData();
        } else if (function.equalsIgnoreCase("3")) {
            this.inputDisFlag1 = "";
            this.inputDisFlag = "none";
            this.setBntButton("Delete");
            getUnAuthData();
        }
    }

    public void getUnAuthData() {
        setMessage("");
        try {
            List unAuthAcnoList = rdIntCalFacadeRemote.getUnAuthRdAcNo(getOrgnBrCode());
            pendingAcNoList = new ArrayList<>();
            if (!unAuthAcnoList.isEmpty()) {
                for (int i = 0; i < unAuthAcnoList.size(); i++) {
                    Vector vtr = (Vector) unAuthAcnoList.get(i);
                    pendingAcNoList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getVerifyDeleteData() {
        setMessage("");
        try {

            List verifyDataList = rdIntCalFacadeRemote.getRdVerifyDeleteAccountDetail(pendingAcNo, getOrgnBrCode());
            if (verifyDataList.isEmpty()) {
                throw new ApplicationException("Data does not exist for verify");
            }
            Vector vtr = (Vector) verifyDataList.get(0);
            String curDt = vtr.get(0).toString();
            String matDt = vtr.get(1).toString(); // rdMat Date
            String rdInstall = vtr.get(2).toString();
            String intDeppsit = vtr.get(3).toString();
            String rdOpeningDt = vtr.get(4).toString();
            String custName = vtr.get(5).toString();
            String contROI = vtr.get(6).toString();
            String AppRate = vtr.get(7).toString();
            String netContRoi = vtr.get(8).toString();
            String matbalance = vtr.get(9).toString();
            String IntAmtAfetrPenalty = vtr.get(10).toString();
            String IntPaid = vtr.get(11).toString();
            String TdsToBeDed = vtr.get(12).toString();
            String TDSDeducted = vtr.get(13).toString();
            String frDt = vtr.get(14).toString();
            String toDt = vtr.get(15).toString();
            String PenaltyAmount = vtr.get(16).toString();
            String IntPaidCurrentYear = vtr.get(17).toString();

            setName(custName);
            setInstallment(rdInstall);
            setBalance(matbalance);
            int monDiff = CbsUtil.monthDiff(sdf.parse(rdOpeningDt), sdf.parse(matDt));
            setPeriod(String.valueOf(monDiff));
            setMatDate(matDt);
            setFromDate(sdf.parse(frDt));
            setToDate(sdf.parse(toDt));
            setContractedRoi(contROI);
            setApplicableRate(AppRate);
            setPenaltyAmt(PenaltyAmount);
            setNetContractedRoi(netContRoi);
            setProvision(IntPaid);
            setInterestAmtAfterPenalty(IntAmtAfetrPenalty);
            setRemainingIntAmt(String.valueOf(Double.parseDouble(IntAmtAfetrPenalty) - Double.parseDouble(IntPaid)));
            setTdsToBeDeducted(TdsToBeDed);
            setTdsDeducted(TDSDeducted);
            setIntPaid(IntPaidCurrentYear);
            setEnterBy(vtr.get(18).toString());
            String status = vtr.get(19).toString();
            if (status.equalsIgnoreCase("P")) {
                setIntOption("3");
            } else if (status.equalsIgnoreCase("M")) {
                setIntOption("4");
            } else {
                setIntOption("5");
            }
            setCloseActTdsToBeDeducted(vtr.get(20).toString());
            setCloseActTdsDeducted(vtr.get(21).toString());
            setPostDisable(false);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAccontDetails() {
        this.setBtnDisabled(true);
        try {
            List isRdAcno = rdIntCalFacadeRemote.isRdPaymentAcno(oldAccNo);
            if (!isRdAcno.isEmpty()) {
                Vector vtr = (Vector) isRdAcno.get(0);
                String auth = vtr.get(0).toString();
                if (auth.equalsIgnoreCase("Y")) {
                    setMessage("This A/c No. payment already to the customer.!");
                } else {
                    setMessage("Account No. are pending for authorization of this customer !");
                }
                return;
            }
            setApplicableRate("");
            setBalance("");
            setContractedRoi("");
            setFromDate(new Date());
            setToDate(new Date());

            setInstallment("");
            setIntOption("");
            setMatDate("");
            setName("");
            setNetContractedRoi("");
            setPenaltyAmt("");

            setPenaltyApplicate("");
            setPeriod("");
            setProvision("");
            setInterestAmtAfterPenalty("");
            setInterestAmtBeforePenalty("");
            if (validation().equalsIgnoreCase("false")) {
                return;
            }
            List resultList = rdIntCalFacadeRemote.getRdAccountDetail(oldAccNo, getOrgnBrCode());
            setMessage("");
            setAcno(resultList.get(0).toString());
            setOptionDisable(false);
            String sysDate = resultList.get(1).toString();
            setMatDate(resultList.get(2).toString());
            setInstallment(formatter.format(Double.parseDouble(resultList.get(3).toString())));
            setContractedRoi(resultList.get(4).toString());

            setFromDate(sdf.parse(resultList.get(5).toString()));
            setName(resultList.get(6).toString());
            setPeriod(resultList.get(7).toString());
            setProvision(formatter.format(Double.parseDouble(resultList.get(8).toString())));
            setBalance(formatter.format(Double.parseDouble(resultList.get(9).toString())));

            setApplicableRate(resultList.get(10).toString());
            setNetContractedRoi("0");
            setPenaltyAmt("0");
            setPenaltyApplicate("0");

            Date matDateTmp = sdf.parse(matDate);
            Date sysDateTmp = sdf.parse(sysDate);
            if (sysDateTmp.after(matDateTmp)) {
                setToDate(sdf.parse(matDate));
                setIntOption("2");
            } else if (sysDateTmp.before(matDateTmp)) {
                setToDate(sdf.parse(sysDate));
                setIntOption("2");
            } else {
                setToDate(sdf.parse(matDate));
                setIntOption("2");
            }
            d1 = ymd.format(ToDate);
            d2 = ymd.format(FromDate);
            setApplicableRate();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setApplicableRoi() {
        setMessage("");
        try {
            String result = rdIntCalFacadeRemote.sngGetPrevailingRoi(fts.getAccountCode(acno));
            setApplicableRate(result);
            setApplicableRate();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void btnCalculation() {
        setMessage("");
        try {
            if (validationCalculate().equalsIgnoreCase("false")) {
                return;
            }
            List resultList = rdIntCalFacadeRemote.rdInterestCalculationProductWise(intOption, Float.parseFloat(balance), Float.parseFloat(NetContractedRoi),
                    ymd.format(ToDate), d2, d1, acno, Float.parseFloat(installment), Float.parseFloat(PenaltyAmt), ymd.format(sdf.parse(getMatDate())), getProvision());

            setInterestAmtAfterPenalty(formatter.format(Double.parseDouble(resultList.get(1).toString())));
            setInterestAmtBeforePenalty(formatter.format(Double.parseDouble(resultList.get(0).toString())));
            double remainInt = Double.parseDouble(resultList.get(1).toString()) - Double.parseDouble(provision);
            setRemainingIntAmt(formatter.format(remainInt));
            setTdsToBeDeducted(formatter.format(Double.parseDouble(resultList.get(2).toString())));
            setTdsDeducted(formatter.format(Double.parseDouble(resultList.get(3).toString())));
            setIntPaid(formatter.format(Double.parseDouble(resultList.get(4).toString())));
            setCloseActTdsToBeDeducted(formatter.format(Double.parseDouble(resultList.get(5).toString())));
            setCloseActTdsDeducted(formatter.format(Double.parseDouble(resultList.get(6).toString())));
            setPostDisable(false);
            setBtnDisabled(true);

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void saveAction() {
        setMessage("");
        if (function.equalsIgnoreCase("1")) {
            bntSave();
        } else if (function.equalsIgnoreCase("2")) {
            btnPost();
        } else if (function.equalsIgnoreCase("3")) {
            deleteRecord();
        }
    }

    public void bntSave() {
        setMessage("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setMessage("Please select Function !");
                return;
            }
            if (acno == null || acno.equalsIgnoreCase("")) {
                this.setMessage("No Data for Saved !");
                return;
            }
            String result = rdIntCalFacadeRemote.saveRdrecord(Double.parseDouble(InterestAmtAfterPenalty), Double.parseDouble(provision),
                    ymd.format(sdf.parse(getTodayDate())), getOrgnBrCode(), getOrgnBrCode(), Integer.parseInt(intOption), getUserName(),
                    acno, message, Double.parseDouble(tdsToBeDeducted), Double.parseDouble(tdsDeducted),
                    ymd.format(getFromDate()), ymd.format(getToDate()), Float.parseFloat(NetContractedRoi), Double.parseDouble(balance),
                    Float.parseFloat(ApplicableRate), Float.parseFloat(ContractedRoi), Float.parseFloat(PenaltyAmt),
                    Double.parseDouble(intPaid), Double.parseDouble(closeActTdsToBeDeducted), Double.parseDouble(closeActTdsDeducted));
            if (result.equalsIgnoreCase("true")) {
                this.setMessage("Data successfully saved");
                this.setPostDisable(true);
                resetValues();
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void deleteRecord() {
        setMessage("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setMessage("Please select Function !");
                return;
            }
            if (pendingAcNo == null || pendingAcNo.equalsIgnoreCase("")) {
                this.setMessage("No Data for Delete !");
                return;
            }
            String result = rdIntCalFacadeRemote.deleteRdrecord(pendingAcNo);
            this.setMessage(result);
            resetValues();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPost() {
        setMessage("");
        if (InterestAmtAfterPenalty == null || InterestAmtAfterPenalty.equalsIgnoreCase("")) {
            setMessage("Interest Amount after penalty can not be Blank");
            return;
        }
        try {
            if (getEnterBy().equalsIgnoreCase(getUserName())) {
                setMessage("You can not verify your own entry");
                return;
            }
            int rdSimplepostflag = rdIntCalFacadeRemote.rdPostFlag();
            if (closeActTdsToBeDeducted == null || closeActTdsToBeDeducted.equalsIgnoreCase("")) {
                closeActTdsToBeDeducted = "0";
            }

            float trsNo = rdIntCalFacadeRemote.rdIntPosting(Double.parseDouble(InterestAmtAfterPenalty),
                    Double.parseDouble(provision),
                    ymd.format(sdf.parse(getTodayDate())), getOrgnBrCode(), getOrgnBrCode(), rdSimplepostflag, enterBy, getUserName(),
                    pendingAcNo, "true", Double.parseDouble(tdsToBeDeducted), Double.parseDouble(closeActTdsToBeDeducted), ymd.format(ToDate));
            setMessage("Transfer Batch No is :" + trsNo);
            setPostDisable(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setApplicableRate() {
        try {
            setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (ApplicableRate != null || !ApplicableRate.equalsIgnoreCase("")) {
                Matcher ApplicableRateCheck = p.matcher(ApplicableRate);
                if (!ApplicableRateCheck.matches()) {
                    setMessage("Enter Numeric Value for Applicable.");
                    return;
                }
            }
            if (PenaltyApplicate != null || !PenaltyApplicate.equalsIgnoreCase("")) {
                Matcher PenaltyApplicateRateCheck = p.matcher(PenaltyApplicate);
                if (!PenaltyApplicateRateCheck.matches()) {
                    setMessage("Enter Numeric Value for Penalty Applicate.");
                    return;
                }
            }
            Float roi = Float.parseFloat(ApplicableRate) - Float.parseFloat(PenaltyApplicate);
            setNetContractedRoi(roi.toString());
            if (roi < 0) {
                setNetContractedRoi(roi.toString());
            }
            if (Float.parseFloat(ContractedRoi) == 0) {
            } else {
                if (Float.parseFloat(ContractedRoi) < Float.parseFloat(NetContractedRoi)) {
                    setNetContractedRoi(ContractedRoi);
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String validation() {
        try {
            if (oldAccNo == null || oldAccNo.equalsIgnoreCase("")) {
                setMessage("Please fill the account no");
                return "false";
            }
            //if (oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please fill proper account no");
                return "false";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "true";
    }

    public String validationCalculate() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (ContractedRoi != null || !ContractedRoi.equalsIgnoreCase("")) {
                Matcher ContractedRoiCheck = p.matcher(ContractedRoi);
                if (!ContractedRoiCheck.matches()) {
                    setMessage("Contracted Roi should be Numerical.");
                    return "false";
                }
            }
            if (ApplicableRate != null || !ApplicableRate.equalsIgnoreCase("")) {
                Matcher ApplicableRateCheck = p.matcher(ApplicableRate);
                if (!ApplicableRateCheck.matches()) {
                    setMessage("Applicable Rate should be Numerical.");
                    return "false";
                }
                if (!ApplicableRate.matches("[0-9.]*")) {
                    setApplicableRate("");
                    setMessage("Please Enter positive Value for Applicable Rate.");
                    return "false";
                }
            }
            if (PenaltyApplicate != null || !PenaltyApplicate.equalsIgnoreCase("")) {
                Matcher PenaltyApplicateCheck = p.matcher(PenaltyApplicate);
                if (!PenaltyApplicateCheck.matches()) {
                    setMessage("Penalty Applicate should be Numerical.");
                    return "false";
                }
                if (!PenaltyApplicate.matches("[0-9.]*")) {
                    setPenaltyApplicate("");
                    setMessage("Please Enter positive Value for PenaltyApplicate.");
                    return "false";
                }
            }

            if (PenaltyAmt != null || !PenaltyAmt.equalsIgnoreCase("")) {
                Matcher PenaltyAmtCheck = p.matcher(PenaltyAmt);
                if (!PenaltyAmtCheck.matches()) {
                    setMessage("Penalty Amt should be Numerical.");
                    return "false";
                }
                if (!PenaltyAmt.matches("[0-9.]*")) {
                    setPenaltyAmt("");
                    setMessage("Please Enter positive Value for Penalty Amt.");
                    return "false";
                }
            }


            if (NetContractedRoi != null || !NetContractedRoi.equalsIgnoreCase("")) {
                Matcher NetContractedRoiCheck = p.matcher(PenaltyAmt);
                if (!NetContractedRoiCheck.matches()) {
                    setMessage("NetContracted Roi should be Numerical.");
                    return "false";
                } else {
                    if (Float.parseFloat(NetContractedRoi) <= 0) {
                        setMessage("NetContracted can not be less than Zero.");
                        return "false";
                    }

                }
            }
            if (oldAccNo == null || oldAccNo.equalsIgnoreCase("")) {
                setMessage("Please fill the account no");
                return "false";
            }
            //if (oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please fill proper account no");
                return "false";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "true";
    }

    public void resetValues() {
        try {
            //setAcctType("0");
            setAcno("");
            setOldAccNo("");
            setApplicableRate("");
            setBalance("");
            setContractedRoi("");
            setFromDate(sdf.parse(getTodayDate()));
            setToDate(sdf.parse(getTodayDate()));
            setInstallment("");
            setIntOption("");
            setMatDate("");
            setName("");
            setNetContractedRoi("");
            setPenaltyAmt("");
            setPenaltyApplicate("");
            setPeriod("");
            setProvision("");
            setInterestAmtAfterPenalty("");
            setInterestAmtBeforePenalty("");
            rdTable = new ArrayList<RdIntCalTable>();
            setTdsDeducted("");
            setTdsToBeDeducted("");
            setIntPaid("");
            setCloseActTdsToBeDeducted("");
            setCloseActTdsDeducted("");
            this.setBtnDisabled(true);
            setPostDisable(true);
            setFunction("0");
            setInputDisFlag("");
            setInputDisFlag1("none");
            setBntButton("Saved");
            setRemainingIntAmt("");
            // setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshButton() {
        try {
            //setAcctType("0");
            setAcno("");
            setOldAccNo("");
            setApplicableRate("");
            setBalance("");
            setContractedRoi("");
            setFromDate(sdf.parse(getTodayDate()));
            setToDate(sdf.parse(getTodayDate()));
            setInstallment("");
            setIntOption("");
            setMatDate("");
            setName("");
            setApplicableRate("0");
            setNetContractedRoi("");
            setPenaltyAmt("");
            setPenaltyApplicate("");
            setPeriod("");
            setProvision("");
            setInterestAmtAfterPenalty("");
            setInterestAmtBeforePenalty("");
            setTdsDeducted("");
            setTdsToBeDeducted("");
            setIntPaid("");
            setCloseActTdsToBeDeducted("");
            setCloseActTdsDeducted("");
            rdTable = new ArrayList<RdIntCalTable>();
            setMessage("");
            this.setBtnDisabled(true);
            setPostDisable(true);
            setFunction("0");
            setInputDisFlag("");
            setInputDisFlag1("none");
            setBntButton("Saved");
            setRemainingIntAmt("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void changeOnOption() {
        this.setBtnDisabled(false);
        setApplicableRate("0");
        setPenaltyApplicate("0");
        setNetContractedRoi("0");
        setPenaltyAmt("0");
        setInterestAmtAfterPenalty("");
        setInterestAmtBeforePenalty("");
        setMessage("");
        try {
            long diff = CbsUtil.dayDiff(ymd.parse(d2), ymd.parse(d1));
            if (diff < 91) {
                setMessage("Sorry Cannot Calculate Interest, Please Verify the Maturity Date");
                return;
            }
            setApplicableRate(this.getContractedRoi());

//            List resultListRate = rdIntCalFacadeRemote.getRate(d2, diff, Float.parseFloat(installment));
//            if (intOption.equalsIgnoreCase("3") || intOption.equalsIgnoreCase("4")) {
//                if (resultListRate.size() > 0) {
//                    Vector resultListVect = (Vector) resultListRate.get(0);
//                    setApplicableRate(resultListVect.get(0).toString());
//                }
//            } else if (intOption.equalsIgnoreCase("2")) {
//                setApplicableRoi();
//            }


//        } catch (ApplicationException e) {
//            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getInstallmentDetail() {
        try {
            rdTable = new ArrayList<RdIntCalTable>();
            List resultList = rdIntCalFacadeRemote.getInstallment(acno);
            for (int i = 0; i < resultList.size(); i++) {
                Vector vect = (Vector) resultList.get(i);
                RdIntCalTable rdIntCalTable = new RdIntCalTable();
                rdIntCalTable.setSrNo(i + 1);

                rdIntCalTable.setDueDate(vect.elementAt(0).toString());
                rdIntCalTable.setInstallAmt(Double.parseDouble(vect.elementAt(1).toString()));
                rdIntCalTable.setStatus(vect.elementAt(2).toString());
                if (vect.elementAt(3) == null) {
                    rdIntCalTable.setPaymentDate("");
                } else {
                    rdIntCalTable.setPaymentDate(vect.elementAt(3).toString());
                }
                rdTable.add(rdIntCalTable);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refreshButton();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
