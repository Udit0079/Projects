package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.TableDetails;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public final class TermDepositSlab extends BaseBean {

    private String orgBrnCode = getOrgBrnCode();
    private String totalFromDays;
    private String totalToDays;
    private TableDetails tableDt;
    private List<TableDetails> tableDtList;
    Date dtOfEffect;
    private String intRate;
    private String frmYear;
    private String frmMonth;
    private String frmDays;
    private String toYear;
    private String toMonth;
    private String toDays;
    private String sc;
    private String st;
    private String ot;
    private String mg;
    private String fromAmount;
    private String toAmount;
    private List CurrentDate;
    String errorMsg;
    private int currentRow;
    private Iterable<TableDetails> griddata;
    private TableDetails authorized;
    boolean saveValue;
    boolean updateValue;
    private String SeqenceNo;
    TermDepositMasterFacadeRemote tdRemote;
    OtherMasterFacadeRemote otherMasterRemote;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote fts;
    private String nature;
    private List<SelectItem> natureList;
    int totalDays = 0;
    int fromY = 0;
    int fromM = 0;
    int fromD = 0;
    int toY = 0;
    int toM = 0;
    int toD = 0;
    boolean disMg;

    public String getSeqenceNo() {
        return SeqenceNo;
    }

    public void setSeqenceNo(String SeqenceNo) {
        this.SeqenceNo = SeqenceNo;
    }

    public Date getDtOfEffect() {
        return dtOfEffect;
    }

    public void setDtOfEffect(Date dtOfEffect) {
        this.dtOfEffect = dtOfEffect;
    }

    public boolean isSaveValue() {
        return saveValue;
    }

    public void setSaveValue(boolean saveValue) {
        this.saveValue = saveValue;
    }

    public boolean isUpdateValue() {
        return updateValue;
    }

    public void setUpdateValue(boolean updateValue) {
        this.updateValue = updateValue;
    }

    public List getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(List CurrentDate) {
        this.CurrentDate = CurrentDate;
    }

    public TableDetails getAuthorized() {
        return authorized;
    }

    public void setAuthorized(TableDetails authorized) {
        this.authorized = authorized;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getFrmDays() {
        return frmDays;
    }

    public void setFrmDays(String frmDays) {
        this.frmDays = frmDays;
    }

    public String getFrmMonth() {
        return frmMonth;
    }

    public void setFrmMonth(String frmMonth) {
        this.frmMonth = frmMonth;
    }

    public String getFrmYear() {
        return frmYear;
    }

    public void setFrmYear(String frmYear) {
        this.frmYear = frmYear;
    }

    public String getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(String fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Iterable<TableDetails> getGriddata() {
        return griddata;
    }

    public void setGriddata(Iterable<TableDetails> griddata) {
        this.griddata = griddata;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getToAmount() {
        return toAmount;
    }

    public void setToAmount(String toAmount) {
        this.toAmount = toAmount;
    }

    public String getToDays() {
        return toDays;
    }

    public void setToDays(String toDays) {
        this.toDays = toDays;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public String getToYear() {
        return toYear;
    }

    public void setToYear(String toYear) {
        this.toYear = toYear;
    }

    public String getTotalFromDays() {
        return totalFromDays;
    }

    public void setTotalFromDays(String totalFromDays) {
        this.totalFromDays = totalFromDays;
    }

    public String getTotalToDays() {
        return totalToDays;
    }

    public void setTotalToDays(String totalToDays) {
        this.totalToDays = totalToDays;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public TableDetails getTableDt() {
        return tableDt;
    }

    public void setTableDt(TableDetails tableDt) {
        this.tableDt = tableDt;
    }

    public List<TableDetails> getTableDtList() {
        return tableDtList;
    }

    public void setTableDtList(List<TableDetails> tableDtList) {
        this.tableDtList = tableDtList;
    }

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public List<SelectItem> getNatureList() {
        return natureList;
    }

    public void setNatureList(List<SelectItem> natureList) {
        this.natureList = natureList;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public boolean isDisMg() {
        return disMg;
    }

    public void setDisMg(boolean disMg) {
        this.disMg = disMg;
    }

    public TermDepositSlab() {
        try {

            tdRemote = (TermDepositMasterFacadeRemote) ServiceLocator.getInstance().lookup("TermDepositMasterFacade");
            otherMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            tableDtList = new ArrayList<TableDetails>();
            this.updateValue = true;
            setDtOfEffect(new Date());
            setFrmYear("0");
            setToYear("0");
            setSc("0.00");
            setFrmMonth("0");
            setToMonth("0");
            setSt("0.00");
            setFrmDays("0");
            setToDays("0");
            setOt("0.00");
            setIntRate("0.00");
            setMg("0.00");
            getdata();
            int codeVal = common.getCodeByReportName("TD_MINOR_GIRL");
            if (codeVal == 1) {
                disMg = false;
            } else {
                disMg = true;
            }

        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void getdata() {
        try {
            List acnoDataList = new ArrayList();
            acnoDataList = otherMasterRemote.getData();
            natureList = new ArrayList<SelectItem>();
            for (int i = 0; i < acnoDataList.size(); i++) {
                Vector ele = (Vector) acnoDataList.get(i);
                for (Object ee : ele) {
                    natureList.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void changeFunction() {
        gridData(this.getNature());
    }

    private void gridData(String acNat) {
        try {
            tableDtList = new ArrayList();
            List list = new ArrayList();
            list = tdRemote.getTableDetails(acNat);
            if (list.isEmpty()) {
            } else {
                for (int j = 0; j < list.size(); j++) {
                    Vector element = (Vector) list.get(j);
                    tableDt = new TableDetails();
                    tableDt.setSeqNo(element.get(0).toString());
                    tableDt.setApplicableDate(element.get(1).toString());
                    tableDt.setIntRate((element.get(2).toString()));
                    tableDt.setFromPeriod(element.get(3).toString());
                    tableDt.setToPeriod(element.get(4).toString());
                    tableDt.setFromAmt(new BigDecimal(Float.parseFloat(element.get(5).toString())));
                    tableDt.setToAmt(new BigDecimal(Float.parseFloat(element.get(6).toString())));
                    tableDt.setAddIntSr((element.get(7).toString()));
                    tableDt.setAddIntSt((element.get(8).toString()));
                    tableDt.setAddIntOt((element.get(9).toString()));
                    tableDt.setAcNature((element.get(11).toString()));
                    tableDt.setAddIntMg((element.get(12).toString()));
                    tableDtList.add(tableDt);
                }
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String ctrlNumber = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ctrlNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (TableDetails item4 : griddata) {
                if (item4.getAddIntOt().equals(ctrlNumber)) {
                    authorized = item4;
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void setRowValues() throws ParseException {
        try {
            this.setErrorMsg("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            updateValue = false;
            saveValue = true;
            this.setDtOfEffect(sdf.parse(authorized.getApplicableDate()));
            this.setIntRate(authorized.getIntRate());
            String st3 = authorized.getFromPeriod().toUpperCase();
            String s[] = {"0"};
            String s1[] = {"0"};
            String s4[] = {"0"};
            String s2 = null;
            String s3 = null;
            if ((st3.contains("YRS") && (st3.contains("MTS")) && (st3.contains("DAYS")))) {
                s = st3.split("YRS");
                s2 = s[1];
                s1 = s2.split("MTS");
                s3 = s1[1];
                s4 = s3.split("DAYS");
                this.setFrmYear(s[0]);
                this.setFrmMonth(s1[0]);
                this.setFrmDays(s4[0]);
            }
            if ((st3.contains("YRS") && (st3.contains("MTS")) && (!st3.contains("DAYS")))) {
                s = st3.split("YRS");
                s2 = s[1];
                s1 = s2.split("MTS");
                this.setFrmYear(s[0]);
                this.setFrmMonth(s1[0]);
                this.setFrmDays(s4[0]);
            }
            if ((st3.contains("YRS") && (st3.contains("DAYS")) && (!st3.contains("MTS")))) {
                s = st3.split("YRS");
                s3 = s[1];
                s4 = s3.split("DAYS");
                this.setFrmYear(s[0]);
                this.setFrmMonth(s1[0]);
                this.setFrmDays(s4[0]);
            }
            if ((st3.contains("MTS") && (st3.contains("DAYS")) && (!st3.contains("YRS")))) {
                s1 = st3.split("MTS");
                s3 = s1[1];
                s4 = s3.split("DAYS");
                this.setFrmYear(s[0]);
                this.setFrmMonth(s1[0]);
                this.setFrmDays(s4[0]);
            }
            if (st3.contains("MTS") && !st3.contains("YRS") && !st3.contains("DAYS")) {
                s1 = st3.split("MTS");
                this.setFrmYear(s[0]);
                this.setFrmMonth(s1[0]);
                this.setFrmDays(s4[0]);
            }
            if (st3.contains("DAYS") && !st3.contains("YRS") && !st3.contains("MTS")) {
                s4 = st3.split("DAYS");
                this.setFrmYear(s[0]);
                this.setFrmMonth(s1[0]);
                this.setFrmDays(s4[0]);
            }
            if (st3.contains("YRS") && !st3.contains("MTS") && !st3.contains("DAYS")) {
                s = st3.split("YRS");
                this.setFrmYear(s[0]);
                this.setFrmMonth(s1[0]);
                this.setFrmDays(s4[0]);
            }
            totalDays = Integer.parseInt(s[0]) * 365 + Integer.parseInt(s1[0]) * 30 + Integer.parseInt(s4[0]);
            this.setTotalFromDays(String.valueOf(totalDays));
            String string = authorized.getToPeriod().toUpperCase();
            String a[] = {"0"};
            String a1[] = {"0"};
            String a4[] = {"0"};
            String a2 = null;
            String a3 = null;
            if ((string.contains("YRS") && (string.contains("MTS")) && (string.contains("DAYS")))) {
                a = string.split("YRS");
                a2 = a[1];
                a1 = a2.split("MTS");
                a3 = a1[1];
                a4 = a3.split("DAYS");
                this.setToYear(a[0]);
                this.setToMonth(a1[0]);
                this.setToDays(a4[0]);
            }
            if ((string.contains("YRS") && (string.contains("MTS")) && (!string.contains("DAYS")))) {
                a = string.split("YRS");
                a2 = a[1];
                a1 = a2.split("MTS");
                this.setToYear(a[0]);
                this.setToMonth(a1[0]);
                this.setToDays(a4[0]);
            }
            if ((string.contains("YRS") && (string.contains("DAYS")) && (!string.contains("MTS")))) {
                a = string.split("YRS");
                a3 = a[1];
                a4 = a3.split("DAYS");
                this.setToYear(a[0]);
                this.setToMonth(a1[0]);
                this.setToDays(a4[0]);
            }
            if ((string.contains("MTS") && (string.contains("DAYS")) && (!string.contains("YRS")))) {
                a = string.split("MTS");
                a3 = a[1];
                a4 = a3.split("DAYS");
                this.setToYear(a[0]);
                this.setToMonth(a1[0]);
                this.setToDays(a4[0]);
            }
            if (string.contains("MTS") && !string.contains("YRS") && !string.contains("DAYS")) {
                a1 = string.split("MTS");
                this.setToYear(a[0]);
                this.setToMonth(a1[0]);
                this.setToDays(a4[0]);
            }
            if (string.contains("DAYS") && !string.contains("YRS") && !string.contains("MTS")) {
                a4 = string.split("DAYS");
                this.setToYear(a[0]);
                this.setToMonth(a1[0]);
                this.setToDays(a4[0]);
            }
            if (string.contains("YRS") && !string.contains("MTS") && !string.contains("DAYS")) {
                a = string.split("YRS");
                this.setToYear(a[0]);
                this.setToMonth(a1[0]);
                this.setToDays(a4[0]);
            }
            totalDays = Integer.parseInt(a[0]) * 365 + Integer.parseInt(a1[0]) * 30 + Integer.parseInt(a4[0]);
            this.setTotalToDays(String.valueOf(totalDays));
            this.setFromAmount(String.valueOf(authorized.getFromAmt()));
            this.setToAmount(String.valueOf(authorized.getToAmt()));
            this.setSc(authorized.getAddIntSr());
            this.setSt(authorized.getAddIntSt());
            this.setOt(authorized.getAddIntOt());
            this.setMg(authorized.getAddIntMg());
            this.setSeqenceNo(authorized.getSeqNo());
            this.setNature(authorized.getAcNature());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void upDateRecord() {
        try {
            this.setErrorMsg("");
            tableDtList = new ArrayList();
            String ss = onblurInterestRate();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurFromYear();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurFromMOnth();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurFromDays();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurToYear();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurToMonth();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurToDays();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurFromAmount();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurToAmount();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurSeniorCitizen();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurStaff();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurOthers();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurDayComperision();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurAmountComperision();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String dts = formatter.format(this.dtOfEffect);
            String interestRt = this.getIntRate();
            fromY = Integer.parseInt(this.getFrmYear());
            fromM = Integer.parseInt(this.getFrmMonth());
            fromD = Integer.parseInt(this.getFrmDays());
            toY = Integer.parseInt(this.getToYear());
            toM = Integer.parseInt(this.getToMonth());
            toD = Integer.parseInt(this.getToDays());
            String totalFrmDays = this.getTotalFromDays();
            String totalTDays = this.getTotalToDays();
            String frmAmt = this.getFromAmount();
            String toAmt = this.getToAmount();
            String scn = this.getSc();
            String stn = this.getSt();
            String otn = this.getOt();
            String mgn = this.getMg();
            String st1 = String.valueOf(fromY) + "Yrs" + String.valueOf(fromM) + "Mts" + String.valueOf(fromD) + "Days";
            String st2 = String.valueOf(toY) + "Yrs" + String.valueOf(toM) + "Mts" + String.valueOf(toD) + "Days";
            String msg = tdRemote.saveRecord(dts, interestRt, totalFrmDays, totalTDays, frmAmt, toAmt, scn, stn, otn, st1, st2, this.getUserName(), this.updateValue, SeqenceNo, this.getNature(), mgn);
            retuCurrentDateIte();
            this.setErrorMsg(msg);
//            List listForCurrentItems = new ArrayList();
//            listForCurrentItems = tdRemote.returnCurrentItem();
//            for (int i = 0; i < listForCurrentItems.size(); i++) {
//                Vector v = (Vector) listForCurrentItems.get(0);
//                tableDt = new TableDetails();
//                tableDt.setSeqNo(v.get(0).toString());
//                tableDt.setApplicableDate(v.get(1).toString());
//                tableDt.setIntRate((v.get(2).toString()));
//                tableDt.setFromPeriod(v.get(3).toString());
//                tableDt.setToPeriod(v.get(4).toString());
//                tableDt.setFromAmt(new BigDecimal(Float.parseFloat(v.get(5).toString())));
//                tableDt.setToAmt(new BigDecimal(Float.parseFloat(v.get(6).toString())));
//                tableDt.setAddIntSr((v.get(7).toString()));
//                tableDt.setAddIntSt((v.get(8).toString()));
//                tableDt.setAddIntOt((v.get(9).toString()));
//                tableDtList.add(tableDt);
//            }
            {
                setIntRate("0.00");
                setFromAmount("");
                setToAmount("");
                setFrmDays("0");
                setToDays("0");
                setFrmMonth("0");
                setToMonth("0");
                setFrmYear("0");
                setToYear("0");
                setTotalFromDays("");
                setTotalToDays("");
                setSc("0.00");
                setSt("0.00");
                setOt("0.00");
                setMg("0.00");
            }
            msg = msg.toString().trim();
            if (msg.equals("Data has been successfully updated")) {
                saveValue = false;
                updateValue = true;
            } else {
                saveValue = true;
                updateValue = false;
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void save() {
        try {
            updateValue = true;
            this.setErrorMsg("");
            tableDtList = new ArrayList();
            String ss = onblurInterestRate();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurFromYear();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurFromMOnth();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurFromDays();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurToYear();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurToMonth();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurToDays();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurFromAmount();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurToAmount();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurSeniorCitizen();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurStaff();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurOthers();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurDayComperision();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            ss = onblurAmountComperision();
            if (!ss.equals("true")) {
                this.setErrorMsg(ss);
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String dts = formatter.format(this.dtOfEffect);
            String interestRt = this.getIntRate();
            fromY = Integer.parseInt(this.getFrmYear());
            fromM = Integer.parseInt(this.getFrmMonth());
            fromD = Integer.parseInt(this.getFrmDays());
            toY = Integer.parseInt(this.getToYear());
            toM = Integer.parseInt(this.getToMonth());
            toD = Integer.parseInt(this.getToDays());
            String totalFrmDays = this.getTotalFromDays();
            String totalTDays = this.getTotalToDays();
            String frmAmt = this.getFromAmount();
            String toAmt = this.getToAmount();
            String scn = this.getSc();
            String stn = this.getSt();
            String otn = this.getOt();
            String mgn = this.getMg();
            String st1 = String.valueOf(fromY) + "Yrs" + String.valueOf(fromM) + "Mts" + String.valueOf(fromD) + "Days";
            String st2 = String.valueOf(toY) + "Yrs" + String.valueOf(toM) + "Mts" + String.valueOf(toD) + "Days";
            List dateDif = tdRemote.dateDiff(dts);
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            // new Code
            List isHolyDaysList = tdRemote.isHolyDays(dts);
            if (!isHolyDaysList.isEmpty()) {
                this.setErrorMsg("Bank is holiday.");
                return;
            }
            String businessDt = fts.daybeginDate(getOrgnBrCode());
            long dif = CbsUtil.dayDiff(formatter.parse(businessDt), formatter.parse(dts));
            //After Holy date
            String holtDt = tdRemote.getAfterHolyDate();
            long dif1 = CbsUtil.dayDiff(formatter.parse(holtDt), formatter.parse(dts));
            // End new Code
            if (!(Integer.parseInt(strDateDiff) == 0 || dif == 1 || dif1 == 0)) {
                this.setErrorMsg("dates are not valid.");
                return;
            }
            String msg = tdRemote.saveRecord(dts, interestRt, totalFrmDays, totalTDays, frmAmt, toAmt, scn, stn, otn, st1, st2,
                    this.getUserName(), this.updateValue, SeqenceNo, this.getNature(), mgn);
            retuCurrentDateIte();
            this.setErrorMsg(msg);
//            List listForCurrentItems = new ArrayList();
//            listForCurrentItems = tdRemote.returnCurrentItem();
//            for (int i = 0; i < listForCurrentItems.size(); i++) {
//                Vector v = (Vector) listForCurrentItems.get(0);
//                tableDt = new TableDetails();
//                tableDt.setSeqNo(v.get(0).toString());
//                tableDt.setApplicableDate(v.get(1).toString());
//                tableDt.setIntRate((v.get(2).toString()));
//                tableDt.setFromPeriod(v.get(3).toString());
//                tableDt.setToPeriod(v.get(4).toString());
//                tableDt.setFromAmt(new BigDecimal(Float.parseFloat(v.get(5).toString())));
//                tableDt.setToAmt(new BigDecimal(Float.parseFloat(v.get(6).toString())));
//                tableDt.setAddIntSr((v.get(7).toString()));
//                tableDt.setAddIntSt((v.get(8).toString()));
//                tableDt.setAddIntOt((v.get(9).toString()));
//                tableDtList.add(tableDt);
//            }
            setIntRate("0.00");
            setFromAmount("");
            setToAmount("");
            setFrmDays("0");
            setToDays("0");
            setFrmMonth("0");
            setToMonth("0");
            setFrmYear("0");
            setToYear("0");
            setTotalFromDays("");
            setTotalToDays("");
            setSc("0.00");
            setSt("0.00");
            setOt("0.00");
            setMg("0.00");
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void getTableHistory() {
        try {
            tableDtList = new ArrayList();
            this.setErrorMsg("");
            List list = new ArrayList();
            list = tdRemote.getTableHistry(this.getNature());
            if (list.isEmpty()) {
            } else {
                for (int j = 0; j < list.size(); j++) {
                    Vector element = (Vector) list.get(j);
                    tableDt = new TableDetails();
                    tableDt.setSeqNo(element.get(0).toString());
                    tableDt.setApplicableDate(element.get(1).toString());
                    tableDt.setIntRate((element.get(2).toString()));
                    tableDt.setFromPeriod(element.get(3).toString());
                    tableDt.setToPeriod(element.get(4).toString());
                    tableDt.setFromAmt(new BigDecimal(Double.parseDouble(element.get(5).toString())));
                    tableDt.setToAmt(new BigDecimal(Double.parseDouble(element.get(6).toString())));
                    tableDt.setAddIntSr((element.get(7).toString()));
                    tableDt.setAddIntSt((element.get(8).toString()));
                    tableDt.setAddIntOt((element.get(9).toString()));
                    tableDt.setAcNature((element.get(10).toString()));
                    tableDt.setAddIntMg((element.get(11).toString()));
                    tableDtList.add(tableDt);
                }
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void calTotalFromDays() {
        try {
            fromY = Integer.parseInt(this.getFrmYear());
            fromM = Integer.parseInt(this.getFrmMonth());
            fromD = Integer.parseInt(this.getFrmDays());
            totalDays = fromY * 365 + fromM * 30 + fromD;
            setTotalFromDays(String.valueOf(totalDays));
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void calTotalToDays() {
        try {
            toY = Integer.parseInt(this.getToYear());
            toM = Integer.parseInt(this.getToMonth());
            toD = Integer.parseInt(this.getToDays());
            totalDays = (toY * 365) + (toM * 30) + toD;
            setTotalToDays(String.valueOf(totalDays));
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        try {
            updateValue = true;
            saveValue = false;
            this.setIntRate("0.00");
            this.setFromAmount("");
            this.setToAmount("");
            setTotalFromDays("");
            setTotalToDays("");
            setSc("0.00");
            setSt("0.00");
            setOt("0.00");
            setMg("0.00");
            this.setErrorMsg("");
            setFrmYear("0");
            setToYear("0");
            setFrmMonth("0");
            setToMonth("0");
            setFrmDays("0");
            setToDays("0");
            setDtOfEffect(new Date());
            tableDtList = new ArrayList();
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public List retuCurrentDateIte() {
        try {
            this.setErrorMsg("");
            tableDtList = new ArrayList();
            List list = new ArrayList();
            list = tdRemote.returnCurrentDateItem(this.getNature());
            if (list.isEmpty()) {
                this.setErrorMsg("Data does not exist");
            } else {
                for (int j = 0; j < list.size(); j++) {
                    Vector element = (Vector) list.get(j);
                    tableDt = new TableDetails();
                    tableDt.setSeqNo(element.get(0).toString());
                    tableDt.setApplicableDate(element.get(1).toString());
                    tableDt.setIntRate((element.get(2).toString()));
                    tableDt.setFromPeriod(element.get(3).toString());
                    tableDt.setToPeriod(element.get(4).toString());
                    tableDt.setFromAmt(new BigDecimal(Double.parseDouble(element.get(5).toString())));
                    tableDt.setToAmt(new BigDecimal(Double.parseDouble(element.get(6).toString())));
                    tableDt.setAddIntSr((element.get(7).toString()));
                    tableDt.setAddIntSt((element.get(8).toString()));
                    tableDt.setAddIntOt((element.get(9).toString()));
                    tableDt.setAcNature((element.get(11).toString()));
                    tableDt.setAddIntMg((element.get(12).toString()));
                    tableDtList.add(tableDt);
                }
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return CurrentDate;
    }

    public void delete() {
        this.setErrorMsg("");
        try {
            String deleteEntry = new String();
            deleteEntry = tdRemote.deleteData(Integer.parseInt(authorized.getSeqNo()));
            retuCurrentDateIte();
            this.setErrorMsg(deleteEntry);
//            tableDtList.remove(tableDt);
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String onblurInterestRate() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getIntRate()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid Interest rate.");
                return "Please enter valid Interest rate.";
            } else if (Float.parseFloat(intRate) < 0.0) {
                this.setErrorMsg("Please enter valid Interest rate.");
                return "Please enter valid Interest rate.";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurFromYear() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getFrmYear()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid From year");
                return "Please enter valid From year";
            } else {
                if (Integer.parseInt(frmYear) < 0) {
                    this.setErrorMsg("Please enter valid From year");
                    return "Please enter valid From year";
                }
                fromY = Integer.parseInt(this.getFrmYear());
                totalDays = fromY * 365;
                setTotalFromDays(String.valueOf(totalDays));
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurFromMOnth() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getFrmMonth()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid From month");
                return "Please enter valid From month";
            } else {
                fromY = Integer.parseInt(this.getFrmYear());
                if ((Integer.parseInt(frmMonth) >= 13) || (Integer.parseInt(frmMonth) < 0)) {
                    this.setErrorMsg("Please enter valid From month");
                    return "Please enter valid From month";
                }
                fromM = Integer.parseInt(this.getFrmMonth());
                totalDays = (int) (fromY * 365 + fromM * 30);
                setTotalFromDays(String.valueOf(totalDays));
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurFromDays() {
        try {
            if ((frmYear == null) || (frmYear.equalsIgnoreCase(""))) {
                this.setErrorMsg("Please enter From year");
                return "Please enter From year";
            }
            if ((frmMonth == null) || (frmMonth.equalsIgnoreCase(""))) {
                this.setErrorMsg("Please enter From months");
                return "Please enter From months";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getFrmDays()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid  effective From days");
                return "Please enter valid  effective From days";
            } else {
                fromY = Integer.parseInt(this.getFrmYear());
                fromM = Integer.parseInt(this.getFrmMonth());
                fromD = Integer.parseInt(this.getFrmDays());
                totalDays = (int) (fromY * 365 + fromM * 30 + fromD);
                setTotalFromDays(String.valueOf(totalDays));
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurToYear() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getToYear()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid To year");
                return "Please enter valid To year";
            } else {
                if (Integer.parseInt(toYear) < 0) {
                    this.setErrorMsg("Please enter valid To year");
                    return "Please enter valid To year";
                }
                toY = Integer.parseInt(this.getToYear());
                totalDays = toY * 365;
                setTotalToDays(String.valueOf(totalDays));
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurToMonth() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getToMonth()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid  effective To Month");
                return "Please enter valid  effective To Month";
            } else {
                if ((Integer.parseInt(toMonth) >= 13) || (Integer.parseInt(toMonth) < 0)) {
                    this.setErrorMsg("Please enter valid To month");
                    return "Please enter valid To month";
                }
                toY = Integer.parseInt(this.getToYear());
                toM = Integer.parseInt(this.getToMonth());
                totalDays = (int) (toY * 365 + toM * 30);
                setTotalToDays(String.valueOf(totalDays));
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurToDays() {
        try {
            if ((toYear == null) || (toYear.equalsIgnoreCase(""))) {
                this.setErrorMsg("Please enter To Year");
                return "Please enter To Year";
            }
            if ((toMonth == null) || (toMonth.equalsIgnoreCase(""))) {
                this.setErrorMsg("Please enter To Months");
                return "Please enter To Months";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getToDays()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid  effective To Days");
                return "Please enter valid  effective To Days";
            } else {
                toY = Integer.parseInt(this.getToYear());
                toM = Integer.parseInt(this.getToMonth());
                toD = Integer.parseInt(this.getToDays());
                totalDays = (int) (toY * 365 + toM * 30) + toD;
                setTotalToDays(String.valueOf(totalDays));
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurFromAmount() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getFromAmount()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid From Amount");
                return "Please enter valid From Amount";
            } else if (Float.parseFloat(fromAmount) < 0) {
                this.setErrorMsg("Please enter valid From Amount");
                return "Please enter valid From Amount";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurToAmount() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getToAmount()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid  To Amount");
                return "Please enter valid  To Amount";
            } else if (Float.parseFloat(toAmount) < 0) {
                this.setErrorMsg("Please enter valid  To Amount");
                return "Please enter valid  To Amount";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurOthers() {
        try {
            Pattern p = Pattern.compile("((-|\\.+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getOt()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid Others");
                return "Please enter valid Others";
            } else if (Float.parseFloat(ot) < 0.0) {
                this.setErrorMsg("Please enter valid Others");
                return "Please enter valid Others";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurSeniorCitizen() {
        try {
            Pattern p = Pattern.compile("((-|\\.+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getSc()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid  Senior Citizen.");
                return "Please enter valid  Senior Citizen.";
            } else if (Float.parseFloat(sc) < 0.0) {
                this.setErrorMsg("Please enter valid  Senior Citizen.");
                return "Please enter valid  Senior Citizen.";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurStaff() {
        try {
            Pattern p = Pattern.compile("((-|\\.+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getSt()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid  staff.");
                return "Please enter valid  staff.";
            } else if (Float.parseFloat(st) < 0.0) {
                this.setErrorMsg("Please enter valid  staff");
                return "Please enter valid  staff.";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurMinorGirl() {
        try {
            Pattern p = Pattern.compile("((-|\\.+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(String.valueOf(this.getMg()));
            if (!billNoCheck.matches()) {
                this.setErrorMsg("Please enter valid minor girls roi");
                return "Please enter valid  minor girls roi";
            } else if (Float.parseFloat(ot) < 0.0) {
                this.setErrorMsg("Please enter valid  minor girls roi");
                return "Please enter valid  minor girls roi";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurDayComperision() {
        try {
            String[] values = null;
            String spliter = ": ";
            values = totalFromDays.split(spliter);
            String fromPeriod = (values[0]);
            String[] values1 = null;
            String spliter1 = ": ";
            values1 = totalToDays.split(spliter1);
            String toPeriod = (values1[0]);
            if (Integer.parseInt(fromPeriod) > Integer.parseInt(toPeriod)) {
                this.setErrorMsg("To Period must not be less than From Period");
                return "To Period must not be less than From Period";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public String onblurAmountComperision() {
        try {
            if (Float.parseFloat(fromAmount) > Float.parseFloat(toAmount)) {
                this.setErrorMsg("To Amount must not be less than the From Amount");
                return "To Amount must not be less than the From Amount";
            } else {
                this.setErrorMsg("");
                return "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
            return "false";
        }
    }

    public void onblurDate() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String dts = formatter.format(this.dtOfEffect);
            List dateDif = tdRemote.dateDiff(dts);
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            // new Code
            List isHolyDaysList = tdRemote.isHolyDays(dts);
            if (!isHolyDaysList.isEmpty()) {
                this.setErrorMsg("Bank is holiday.");
                return;
            }
            String businessDt = fts.daybeginDate(getOrgnBrCode());
            long dif = CbsUtil.dayDiff(formatter.parse(businessDt), formatter.parse(dts));
            //After Holy date
            String holtDt = tdRemote.getAfterHolyDate();
            long dif1 = CbsUtil.dayDiff(formatter.parse(holtDt), formatter.parse(dts));
            // End new Code
            if (Integer.parseInt(strDateDiff) == 0 || dif == 1 || dif1 == 0) {
                setErrorMsg("");
                return;
            } else {
                this.setErrorMsg("Dates are not valid.");
                return;
            }
        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
