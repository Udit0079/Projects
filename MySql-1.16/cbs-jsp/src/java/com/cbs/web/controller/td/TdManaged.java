package com.cbs.web.controller.td;

import com.cbs.dto.AccountDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class TdManaged extends BaseBean {

    String accType;
    List<SelectItem> acctTypeOption;
    String accNo;
    String name;
    Double totPrntAmt;
    String balTd;
    String tdsDeduct;
    String totIntTransfr;
    List<AccountDetail> acd;
    List<AccountDetail> accdtl;
    String msg;
    List<SelectItem> dtlOnDblClk;
    List dtlOnClick;
    int currentRow;
    AccountDetail accDtl;
    AccountDetail accDtl2;
    Float rtNo;
    Double prinAmt;
    Float roi;
    String matdate;
    String issueDate;
    String tdDateWef;
    Double rcptNo;
    double matAmt;
    Double blTd, blTd2;
    List lst1 = new ArrayList();
    String msngTotalInt1;
    double totalTdAmt;
    boolean flag = true;
    String acNoLabel;
    boolean flagForModify;
    boolean flagForRoi;
    String years;
    String months;
    String days;
    String Tmpperiod;
    String captionModify;
    String changeModifyCaption;
    String strperiod;
    String initialMatDate;
    String acNoFlag;
    String operMode;
    String jtName, acNoLen;
    private final String jndiHomeNameTdRcpt = "TdReceiptManagementFacade";
    private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getOperMode() {
        return operMode;
    }

    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    public Double getTotPrntAmt() {
        return totPrntAmt;
    }

    public void setTotPrntAmt(Double totPrntAmt) {
        this.totPrntAmt = totPrntAmt;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public double getTotalTdAmt() {
        return totalTdAmt;
    }

    public void setTotalTdAmt(double totalTdAmt) {
        this.totalTdAmt = totalTdAmt;
    }

    public List<AccountDetail> getAccdtl() {
        return accdtl;
    }

    public void setAccdtl(List<AccountDetail> accdtl) {
        this.accdtl = accdtl;
    }

    public double getMatAmt() {
        return matAmt;
    }

    public void setMatAmt(double matAmt) {
        this.matAmt = matAmt;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getMatdate() {
        return matdate;
    }

    public void setMatdate(String matdate) {
        this.matdate = matdate;
    }

    public Double getPrinAmt() {
        return prinAmt;
    }

    public void setPrinAmt(Double prinAmt) {
        this.prinAmt = prinAmt;
    }

    public Double getRcptNo() {
        return rcptNo;
    }

    public void setRcptNo(Double rcptNo) {
        this.rcptNo = rcptNo;
    }

    public Float getRoi() {
        return roi;
    }

    public void setRoi(Float roi) {
        this.roi = roi;
    }

    public Float getRtNo() {
        return rtNo;
    }

    public void setRtNo(Float rtNo) {
        this.rtNo = rtNo;
    }

    public String getTdDateWef() {
        return tdDateWef;
    }

    public void setTdDateWef(String tdDateWef) {
        this.tdDateWef = tdDateWef;
    }

    public AccountDetail getAccDtl() {
        return accDtl;
    }

    public void setAccDtl(AccountDetail accDtl) {
        this.accDtl = accDtl;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List getDtlOnClick() {
        return dtlOnClick;
    }

    public void setDtlOnClick(List dtlOnClick) {
        this.dtlOnClick = dtlOnClick;
    }

    public List<SelectItem> getDtlOnDblClk() {
        return dtlOnDblClk;
    }

    public void setDtlOnDblClk(List<SelectItem> dtlOnDblClk) {
        this.dtlOnDblClk = dtlOnDblClk;
    }

    public List<AccountDetail> getAcd() {
        return acd;
    }

    public void setAcd(List<AccountDetail> acd) {
        this.acd = acd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBalTd() {
        return balTd;
    }

    public void setBalTd(String balTd) {
        this.balTd = balTd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTdsDeduct() {
        return tdsDeduct;
    }

    public void setTdsDeduct(String tdsDeduct) {
        this.tdsDeduct = tdsDeduct;
    }

    public String getTotIntTransfr() {
        return totIntTransfr;
    }

    public void setTotIntTransfr(String totIntTransfr) {
        this.totIntTransfr = totIntTransfr;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public String getAcNoLabel() {
        return acNoLabel;
    }

    public void setAcNoLabel(String acNoLabel) {
        this.acNoLabel = acNoLabel;
    }

    public boolean isFlagForModify() {
        return flagForModify;
    }

    public void setFlagForModify(boolean flagForModify) {
        this.flagForModify = flagForModify;
    }

    public boolean isFlagForRoi() {
        return flagForRoi;
    }

    public void setFlagForRoi(boolean flagForRoi) {
        this.flagForRoi = flagForRoi;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getTmpperiod() {
        return Tmpperiod;
    }

    public void setTmpperiod(String Tmpperiod) {
        this.Tmpperiod = Tmpperiod;
    }

    public String getCaptionModify() {
        return captionModify;
    }

    public void setCaptionModify(String captionModify) {
        this.captionModify = captionModify;
    }

    public String getChangeModifyCaption() {
        return changeModifyCaption;
    }

    public void setChangeModifyCaption(String changeModifyCaption) {
        this.changeModifyCaption = changeModifyCaption;
    }

    public String getStrperiod() {
        return strperiod;
    }

    public void setStrperiod(String strperiod) {
        this.strperiod = strperiod;
    }

    public AccountDetail getAccDtl2() {
        return accDtl2;
    }

    public void setAccDtl2(AccountDetail accDtl2) {
        this.accDtl2 = accDtl2;
    }

    public String getInitialMatDate() {
        return initialMatDate;
    }

    public void setInitialMatDate(String initialMatDate) {
        this.initialMatDate = initialMatDate;
    }

    public String getAcNoFlag() {
        return acNoFlag;
    }

    public void setAcNoFlag(String acNoFlag) {
        this.acNoFlag = acNoFlag;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public TdManaged() {

        try {
            tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdRcpt);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            this.setAcNoLen(getAcNoLength());
            getAcType();
        } catch (ApplicationException ex) {
            this.setMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void getAcType() {
        try {
            List resultList = new ArrayList();
            resultList = tdRcptMgmtRemote.getAcctType();
            acctTypeOption = new ArrayList<SelectItem>();
            if (resultList.size() > 0) {
                for (Object obj : resultList) {
                    Vector element = (Vector) obj;
                    acctTypeOption.add(new SelectItem(element.get(0).toString()));
                }
            }
        } catch (ApplicationException ex) {
            this.setMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void getAccDetail() {
        this.setMsg("");
        String[] values = null;
        if (this.accNo == null || this.accNo.equals("")) {
            setMsg("Please fill account no.");
            return;
        }
        //if (this.accNo.length() < 12) {
        if (!this.accNo.equalsIgnoreCase("") && ((this.accNo.length() != 12) && (this.accNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            setMsg("Please fill proper account no.");
            return;
        }

        try {
            acNoLabel = ftsPostRemote.getNewAccountNumber(accNo);
            acd = new ArrayList<AccountDetail>();
            String result = tdRcptMgmtRemote.getAcctDetail(acNoLabel);
            if (result.contains(":")) {
                String spliter = ":";
                values = result.split(spliter);
            }
            if (values[1].equals("9")) {
                setName(" ");
                setBalTd(" ");
                setTdsDeduct("0.00");
                setTotIntTransfr("0.00");
                setTotPrntAmt(0.0d);
                gridDetails();
                setMsg("This account has been closed.");
                acNoFlag = "true";
            } else {
                acNoFlag = "false";
                setName(values[2]);
                setBalTd(formatNum(Double.parseDouble(values[6].toString())));
                if (values[3].equals("")) {
                    setJtName(values[4]);
                } else if (values[4].trim().equals("")) {
                    setJtName(values[3]);
                } else {
                    setJtName(values[3] + ", " + values[4]);
                }
                setOperMode(values[5]);
                if (values[7].toString().equals("0.0")) {
                    setTdsDeduct("0.00");
                } else {
                    setTdsDeduct(values[7].toString());
                }
                if (values[8].toString().equals("0.0")) {
                    setTotIntTransfr("0.00");
                } else {
                    setTotIntTransfr(formatNum(Double.parseDouble(values[8].toString())));
                }
                if (values[9].toString().equals("0.0")) {
                    setTotPrntAmt(0.0d);
                } else {
                    setTotPrntAmt(Double.parseDouble(values[9].toString()));
                }
                gridDetails();
            }

        } catch (ApplicationException ex) {
            this.setMsg(ex.getMessage());
            setName(" ");
            setBalTd(" ");
            setTdsDeduct("0.00");
            setTotPrntAmt(0.0d);
            setTotIntTransfr("");
            acNoFlag = "true";
            acd = new ArrayList<AccountDetail>();

        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
            setName(" ");
            setBalTd(" ");
            setTdsDeduct("0.00");
            setTotPrntAmt(0.0d);
            setTotIntTransfr("");
            acNoFlag = "true";
            acd = new ArrayList<AccountDetail>();

        }
    }

    public void getDetlOnClick() {
        try {
            accdtl = new ArrayList<AccountDetail>();
            String acType = ftsPostRemote.getAccountCode(acNoLabel);
            accdtl = tdRcptMgmtRemote.getDetailOnClick(acNoLabel, acType);
        } catch (ApplicationException ex) {
            this.setMsg(ex.getMessage());

        } catch (Exception ex) {
            this.setMsg(ex.getMessage());

        }
    }

    public void setRowValues() {
        try {
            if ((accDtl.getStatus().toString()).equals("CLOSED")) {
                flagForRoi = true;
                captionModify = "Modify";
                setMsg("TD INSTRUMENT IS CLOSED");
                goBack();
            } else {
                flagForRoi = true;
                captionModify = "Modify";
                setRtNo(Float.parseFloat(accDtl.getRtNo().toString()));
                setPrinAmt(Double.parseDouble(accDtl.getPrinAmt().toString()));
                setRoi(Float.parseFloat(accDtl.getRoi().toString()));
                setMatdate(accDtl.getMatDate().toString());
                setIssueDate(accDtl.getTdDate().toString());
                setTdDateWef(accDtl.getTdDateWef().toString());
                setRcptNo(Double.parseDouble(accDtl.getRcptNo().toString()));
                setMatAmt(accDtl.getTotalTdAmt());
                setMsg("");

            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());

        }
    }

    public void codeForModifyButton() {
        try {
            setRowValues();
            if ((accDtl.getStatus().toString()).equals("CLOSED")) {
                flagForModify = true;
                return;
            }
            initialMatDate = matdate;
            List list = tdRcptMgmtRemote.selectFromTdRecon(rtNo, acNoLabel);
            if (!list.isEmpty()) {
                flagForModify = true;
                return;
            }
            List list3 = tdRcptMgmtRemote.selectFromSecurityInfo(getUserName());
            if (list3.isEmpty()) {
                flagForModify = true;
                return;
            }
            List list4 = tdRcptMgmtRemote.selectFromParameterInforeport();
            if (list4.isEmpty()) {
                flagForModify = true;
                return;
            }
            flagForModify = false;
            List list2 = tdRcptMgmtRemote.selectFromTdVouchMast(rtNo, acNoLabel);
            Vector v2 = (Vector) list2.get(0);
            if (!list2.isEmpty()) {
                this.setYears(v2.get(0).toString());
                this.setMonths(v2.get(1).toString());
                this.setDays(v2.get(2).toString());
            } else {
                this.setYears("0");
                this.setMonths("0");
                this.setDays("0");
            }
            strperiod = "";
            if (!this.years.equalsIgnoreCase("0")) {
                strperiod = strperiod+ this.years + "Years";
            }
            if (!this.months.equalsIgnoreCase("0")) {
                strperiod = strperiod+ this.months + "Months";
            }
            if (!this.days.equalsIgnoreCase("0")) {
                strperiod = strperiod+ this.days + "Days";
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    /*
    //after Discuss with Raghib & Dhiru Sir code By athar
    
     * 
     */
    public boolean validate() {
        try {
            if (roi > 25) {
                setMsg("Roi can not be greater than 25 !");
                return false;
            }

            if (!years.equalsIgnoreCase("0")) {
                if (years.length() > 2) {
                    setMsg("Year fill only two digits");
                    return false;
                }
                if (years.contains(".")) {
                    setMsg(" Invalid year. Please fill the year correctly.");
                    return false;
                }
            }
            if (!months.equalsIgnoreCase("0")) {
                if (months.length() > 2) {
                    setMsg("Month fill only two digits");
                    return false;
                }
                if (months.contains(".")) {
                    setMsg(" Invalid month. Please fill the month correctly.");
                    return false;
                }
            }
            if (!days.equalsIgnoreCase("0")) {
                if (days.length() > 3) {
                    setMsg("Days fill only two digits");
                    return false;
                }
                if (days.contains(".")) {
                    setMsg(" Invalid days. Please fill the days correctly.");
                    return false;
                }
            }
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }

        return true;
    }

    public void clickOnModifyButton() {
        try {
            if (validate()) {
                flagForRoi = false;
                if (this.getCaptionModify().equalsIgnoreCase("Modify")) {
                    flagForRoi = false;
                    this.setCaptionModify("Update");
                } else {
                    this.setMsg("");
                    String updateResult = tdRcptMgmtRemote.updateTdVouchMaster(roi, matdate, strperiod, years, months, days, acNoLabel, rtNo);
                    this.setCaptionModify("Modify");
                    this.setMsg(updateResult);
                }
            }
        } catch (ApplicationException ex) {
            this.setMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void onblurMatDate() {
        try {
            strperiod = "";
            if (!this.years.equalsIgnoreCase("0")) {
                strperiod = strperiod + years + "Years";
                matdate = sdf.format(ymd.parse(CbsUtil.yearAdd(ymd.format(sdf.parse(tdDateWef)), Integer.parseInt(years))));
            }
            if (!this.months.equalsIgnoreCase("0")) {
                if (this.years.equalsIgnoreCase("0")) {
                    strperiod = strperiod + months + "Months";
                    matdate = sdf.format(ymd.parse(CbsUtil.monthAdd(ymd.format(sdf.parse(tdDateWef)), Integer.parseInt(months))));
                } else {
                    strperiod = strperiod + months + "Months";
                    matdate = sdf.format(ymd.parse(CbsUtil.monthAdd(ymd.format(sdf.parse(matdate)), Integer.parseInt(months))));
                }
            }
            if (!this.days.equalsIgnoreCase("0")) {
                if (this.years.equalsIgnoreCase("0") && (this.months.equalsIgnoreCase("0"))) {
                    strperiod = strperiod + days + "Days";
                    matdate = sdf.format(ymd.parse(CbsUtil.dateAdd(ymd.format(sdf.parse(tdDateWef)), Integer.parseInt(days))));
                } else {
                    strperiod = strperiod + days + "Days";
                    matdate = sdf.format(ymd.parse(CbsUtil.dateAdd(ymd.format(sdf.parse(matdate)), Integer.parseInt(days))));
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }
    private static double tempamt;
    private static double rtemp;
    private static double tamt;

    public static String formatNum(double amnt) {
        if (amnt == 0.0) {
            return "0.00";
        }
        double amt = amnt;
        if (amt < 0) {
            tempamt = Math.abs(amt);

        } else {
            tempamt = amt;
        }
        rtemp = tempamt;
        tamt = tempamt % 1;//value of after point
        tempamt = tempamt - tamt;//value of before point
        if (amt < 0) {
            if (tamt == 0) {
                return ((new DecimalFormat("#0.00")).format(rtemp));
            } else {
                tempamt = Math.round(rtemp * 100);
                if (tempamt % 10 == 0) {
                    return ((new DecimalFormat("#.#")).format(rtemp) + "0");
                }
                return ((new DecimalFormat("#.##")).format(rtemp));
            }
        } else {
            if (tamt == 0) {
                return ((new DecimalFormat("#0.00")).format(rtemp));
            } else {
                tempamt = Math.round(rtemp * 100);
                if (tempamt % 10 == 0) {
                    return ((new DecimalFormat("#.#")).format(rtemp) + "0");
                }
                return ((new DecimalFormat("#.##")).format(rtemp));
            }
        }
    }

    public void goBack() {
        setRtNo(Float.parseFloat("0"));
        setPrinAmt(Double.parseDouble("0.00"));
        setRoi(Float.parseFloat("0"));
        setMatdate("");
        setIssueDate("");
        setTdDateWef("");
        setRcptNo(Double.parseDouble("0"));
        setMatAmt(Double.parseDouble(formatNum(0)));
    }

    public void gridDetails() {
        flag = false;
        try {
            lst1 = tdRcptMgmtRemote.getTableDetails(acNoLabel);
            if (lst1.isEmpty()) {
                this.setMsg("No Voucher exist in this Account no.");
            } else {
                for (int j = 0; j < lst1.size(); j++) {
                    Vector element = (Vector) lst1.get(j);
                    accDtl = new AccountDetail();
                    accDtl.setRtNo(Float.parseFloat(element.get(0).toString()));
                    accDtl.setRoi(Float.parseFloat(element.get(1).toString()));
                    accDtl.setTdDate(element.get(2).toString());
                    accDtl.setTdDateWef(element.get(3).toString());
                    
                    String TdDtWf = element.get(3).toString().substring(6, 10) + element.get(3).toString().substring(3, 5) + element.get(3).toString().substring(0, 2);
                    accDtl.setMatDate(element.get(4).toString());
                    String MatDt = element.get(4).toString().substring(6, 10) + element.get(4).toString().substring(3, 5) + element.get(4).toString().substring(0, 2);
                    accDtl.setPrinAmt(Double.parseDouble(element.get(5).toString()));
                    accDtl.setIntOpt(element.get(6).toString());
                    accDtl.setRcptNo(Double.parseDouble(element.get(7).toString()));
                    accDtl.setIntToAcno(element.get(8).toString());
                    
                    accDtl.setPeriod(element.get(9).toString());
                    accDtl.setOdfdrAccNo(element.get(10).toString());
                    accDtl.setCtrlNo(Float.parseFloat(element.get(11).toString()));
                    String str = "0";
                    if(element.get(12).toString().equals("A")){
                        str = tdRcptMgmtRemote.orgFDInterest(element.get(6).toString(), Float.parseFloat(element.get(1).toString()), TdDtWf, 
                            MatDt, Double.parseDouble(element.get(5).toString()), element.get(9).toString(), this.getOrgnBrCode());
                    }else{
                        str = tdRcptMgmtRemote.getTotalIntPaid(accNo, Float.parseFloat(element.get(0).toString()));
                    }
                    accDtl.setIntAtMat(Double.parseDouble(str));
                    if (element.get(12).toString().equals("C")) {
                        accDtl.setStatus("CLOSED");
                    } else {
                        accDtl.setStatus("ACTIVE");
                    }
                    accDtl.setFinalAmt(Double.parseDouble(element.get(13).toString()));
                    accDtl.setNetRoi(Float.parseFloat(element.get(14).toString()));
                    if (element.get(6).toString().equals("C") || element.get(6).toString().equals("S")) {
                        accDtl.setTotalTdAmt(Double.parseDouble(element.get(5).toString()) + Double.parseDouble(str));
                    } else if (element.get(6).toString().equals("M") || element.get(6).toString().equals("Q")) {
                        accDtl.setTotalTdAmt(Double.parseDouble(element.get(5).toString()));
                    }
                    acd.add(accDtl);
                    this.setMsg("");
                }
            }
        } catch (ApplicationException ex) {
            this.setMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }

    }

    public void refreshForm() {
        acd = new ArrayList<AccountDetail>();
        this.setAccNo("");
        this.setAcNoLabel("");
        this.setName(null);
        this.setTotPrntAmt(0.0d);
        this.setBalTd("");
        this.setTdsDeduct("");
        this.setTotIntTransfr("");
        this.setRtNo(0f);
        this.setPrinAmt(0d);
        this.setRoi(0f);
        this.setMatAmt(0d);
        this.setIssueDate("");
        this.setTdDateWef("");
        this.setMatdate("");
        this.setYears("0");
        this.setMonths("0");
        this.setDays("0");
        setJtName("");
        setOperMode("");
        acd.clear();
        setMsg("");
    }

    public String exitFrm() {
        refreshForm();
        this.setMsg("");
        return "case1";
    }

    public void loadGridonBack() {
        acd.clear();
        flag = false;
        try {
            List list = tdRcptMgmtRemote.getTableDetails(acNoLabel);
            for (int j = 0; j < list.size(); j++) {
                Vector element = (Vector) list.get(j);
                accDtl2 = new AccountDetail();
                accDtl2.setRtNo(Float.parseFloat(element.get(0).toString()));
                accDtl2.setRoi(Float.parseFloat(element.get(1).toString()));
                accDtl2.setTdDate(element.get(2).toString());
                accDtl2.setTdDateWef(element.get(3).toString());
                String TdDtWf = element.get(3).toString().substring(6, 10) + element.get(3).toString().substring(3, 5) + element.get(3).toString().substring(0, 2);
                accDtl2.setMatDate(element.get(4).toString());
                String MatDt = element.get(4).toString().substring(6, 10) + element.get(4).toString().substring(3, 5) + element.get(4).toString().substring(0, 2);
                accDtl2.setPrinAmt(Double.parseDouble(element.get(5).toString()));
                accDtl2.setIntOpt(element.get(6).toString());
                accDtl2.setRcptNo(Double.parseDouble(element.get(7).toString()));
                accDtl2.setIntToAcno(element.get(8).toString());
                accDtl2.setPeriod(element.get(9).toString());
                accDtl2.setOdfdrAccNo(element.get(10).toString());
                accDtl2.setCtrlNo(Float.parseFloat(element.get(11).toString()));
                String str = tdRcptMgmtRemote.orgFDInterest(element.get(6).toString(), Float.parseFloat(element.get(1).toString()), TdDtWf, MatDt, Float.parseFloat(element.get(5).toString()), element.get(9).toString(), this.getOrgnBrCode());
                accDtl2.setIntAtMat(Double.parseDouble(str));
                if (element.get(12).toString().equals("C")) {
                    accDtl2.setStatus("CLOSED");
                } else {
                    accDtl2.setStatus("ACTIVE");
                }
                accDtl2.setFinalAmt(Double.parseDouble(element.get(13).toString()));
                accDtl2.setNetRoi(Float.parseFloat(element.get(14).toString()));
                if (element.get(6).toString().equals("C") || element.get(6).toString().equals("S")) {
                    accDtl2.setTotalTdAmt(Double.parseDouble(element.get(5).toString()) + Double.parseDouble(str));
                } else if (element.get(6).toString().equals("M") || element.get(6).toString().equals("Q")) {
                    accDtl2.setTotalTdAmt(Double.parseDouble(element.get(5).toString()));
                }
                acd.add(accDtl2);
            }
            this.setMsg("");

        } catch (ApplicationException ex) {
            this.setMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }
}
