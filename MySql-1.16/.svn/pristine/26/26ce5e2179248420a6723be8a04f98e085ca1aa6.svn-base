/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.pojo.deaf.DeafMarkPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class SingleAcnoDeafMark extends BaseBean {

    private String message;
    private String acNo;
    private String intOption;
    private List<SelectItem> intOptionList;
    private String deafAmt;
    private String voucherNo;
    private String effectDt;
    private String savingRoi;
    private boolean vouchDis;
    private boolean savingDis;
    private boolean deafdis;
    private boolean intOptdis;
    private boolean acnoDisable;
    private String month;
    private List<SelectItem> monthList;
    private String monthStyle;
    private String effectStyle;
    private String frToDtstyle;
    private String lableButton;
    private String year;
    private String newAcno;
    private String function;
    private List<SelectItem> functionList;
    private String funButton;
    private Date date = new Date();
    private DeafMarkPojo currentItem = new DeafMarkPojo();
    private List<DeafMarkPojo> gridDetail;
    int currentRow;
    private String frDt;
    private String toDt;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private FtsPostingMgmtFacadeRemote fts = null;
    private DDSManagementFacadeRemote ddsRemote = null;
    private DDSReportFacadeRemote ddsReportRemote;
    private CommonReportMethodsRemote common;
    String intCalcDt = "";
    String finalDeafEffDt = "";

    public SingleAcnoDeafMark() {
        try {
            effectDt = dmy.format(date);
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ddsRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            ddsReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("S", "--Select--"));
            functionList.add(new SelectItem("A", "ADD"));
            functionList.add(new SelectItem("U", "UPDATE"));

            intOptionList = new ArrayList<SelectItem>();
            intOptionList.add(new SelectItem("S", "--Select--"));
            intOptionList.add(new SelectItem("I", "Int. Is Already Posted"));
            intOptionList.add(new SelectItem("D", "Int. To Be Posted"));

            monthList = new ArrayList<SelectItem>();
            //Set all months
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }
            this.setEffectStyle("none");
            this.setFrToDtstyle("none");
            this.setLableButton("Month");
            this.setFunButton("Deaf Mark");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onblurFunction() {
        setMessage("");
        try {
            if (function == null || function.equalsIgnoreCase("S")) {
                setMessage("Please select function !");
                return;
            }
            if (function.equalsIgnoreCase("U")) {
                this.setFrDt("");
                this.setToDt("");
                this.setFunButton("Update");
                this.setIntOption("I");
                this.intOptdis = true;
                this.lableButton = "Interest Effect Date";
                this.monthStyle = "none";
                this.effectStyle = "";
                this.savingDis = true;
                this.frToDtstyle = "";
            } else {
                this.setFunButton("Deaf Mark");
                setIntOption("S");
                this.intOptdis = false;
                this.frToDtstyle = "none";
                this.acnoDisable = false;
                gridDetail = new ArrayList<DeafMarkPojo>();
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onblurAcnoOption() {
        this.setMessage("");
        try {
            if (acNo == null || acNo.equalsIgnoreCase("")) {
                setMessage("Please fill Account No.");
                return;
            }
            if (acNo.length() != 12) {
                setMessage("Please fill 12 digit Account No.");
                return;
            }
            String msg = fts.ftsAcnoValidate(this.acNo, 1, "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            if (!acNo.substring(0, 2).equals(getOrgnBrCode())) {
                throw new ApplicationException("Registration allow only from base branch.");
            }
            newAcno = fts.getCustName(acNo);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onblurIntOption() {
        if (intOption.equalsIgnoreCase("S")) {
            setMessage("Please select Interest Option");
            return;
        }
        String actNature = "";
        try {
            actNature = fts.getAccountNature(acNo);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
            if (intOption.equalsIgnoreCase("D")) {
                this.vouchDis = true;
                this.savingDis = false;
            } else {
                this.vouchDis = false;
                this.savingDis = true;
            }
        } else {
            this.vouchDis = true;
            this.savingDis = true;
        }
        if (intOption.equalsIgnoreCase("D")) {
            this.deafdis = true;
            this.lableButton = "Month";
            this.monthStyle = "";
            this.effectStyle = "none";
        } else {
            this.deafdis = false;
            this.lableButton = "Interest Effect Date";
            this.monthStyle = "none";
            this.effectStyle = "";
        }
        this.setMessage("");
    }

    public void onLoadGrid() {
        setMessage("");
        try {
            gridDetail = new ArrayList<DeafMarkPojo>();
            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date");
                return;
            }
            if (!Validator.validateDate(frDt)) {
                setMessage("Please select Proper from date ");
                return;
            }
            if (dmy.parse(frDt).after(getDate())) {
                setMessage("from date should be less than current date !");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill to date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper to date ");
                return;
            }
            if (dmy.parse(toDt).after(getDate())) {
                setMessage("to date should be less than current date !");
                return;
            }
            List list = ddsRemote.getSingleAcnoData(getOrgnBrCode(), ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)));
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    DeafMarkPojo obj = new DeafMarkPojo();
                    obj.setTxnId(element.get(0).toString());
                    obj.setAcNo(element.get(1).toString());
                    obj.setCustName(fts.getCustName(element.get(1).toString()));
                    obj.setEffectDate(element.get(2).toString());
                    obj.setDeafAmt(Double.parseDouble(element.get(3).toString()));
                    obj.setDeafDate(element.get(4).toString());
                    obj.setReceiptNo(element.get(5).toString());
                    gridDetail.add(obj);
                }
            } else {
                setMessage("Data is not exist for update !");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onLoadField() {
        setMessage("");
        try {
            setAcNo(currentItem.getAcNo());
            setAcnoDisable(true);
            setDeafAmt(String.valueOf(currentItem.getDeafAmt()));
            setEffectDt(currentItem.getEffectDate());
            setVoucherNo(currentItem.getReceiptNo());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void processAction() {
        setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (function == null || function.equalsIgnoreCase("S")) {
                setMessage("Please select function ! ");
                return;
            }
            if (acNo == null || acNo.equalsIgnoreCase("")) {
                setMessage("Please fill Account No.");
                return;
            }
            if (acNo.length() != 12) {
                setMessage("Please fill 12 digit Account No.");
                return;
            }

            if (function.equalsIgnoreCase("A")) {
                String msg = fts.ftsAcnoValidate(this.acNo, 1, "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                if (!acNo.substring(0, 2).equals(getOrgnBrCode())) {
                    throw new ApplicationException("Registration allow only from base branch.");
                }
            }

            if (!function.equalsIgnoreCase("U")) {
                String accountStatus = ddsRemote.getAccStatus(acNo);
                if (accountStatus.equalsIgnoreCase("15")) {
                    setMessage("This Account No. " + acNo + " is already deaf mark !");
                    return;
                }
            }
            if (intOption.equalsIgnoreCase("S")) {
                setMessage("Please select Interest Option");
                return;
            }

            if (intOption.equalsIgnoreCase("I")) {
                if (deafAmt.equalsIgnoreCase("") || deafAmt == null) {
                    setMessage("Please fill Deaf Amount");
                    return;
                }
                Matcher deafAmtChk = p.matcher(this.deafAmt);
                if (!deafAmtChk.matches()) {
                    setMessage("Please fill numeric value !");
                    return;
                }

                if (effectDt == null || effectDt.equalsIgnoreCase("")) {
                    setMessage("Please fill effect date");
                    return;
                }

                if (!Validator.validateDate(effectDt)) {
                    setMessage("Please select Proper As on date ");
                    return;
                }

                if (dmy.parse(effectDt).after(getDate())) {
                    setMessage("As on date should be less than current date !");
                    return;
                }
            } else {
                if (this.month == null || this.month.equals("0")) {
                    this.setMessage("Please select Month.");
                    return;
                }
                if (this.year == null || year.equals("") || year.trim().length() != 4) {
                    this.setMessage("Please fill proper Year.");
                    return;
                }
                Pattern pp = Pattern.compile("[0-9]*");
                Matcher m = pp.matcher(this.year);
                if (m.matches() != true) {
                    this.setMessage("Please fill proper Year in numeric");
                    return;
                }
            }
            String actNature = fts.getAccountNature(acNo);
            if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                if (intOption.equalsIgnoreCase("I")) {
                    if (voucherNo == null || voucherNo.equalsIgnoreCase("")) {
                        setMessage("Please fill Voucher No.");
                        return;
                    }
                    Matcher vouchNoChk = p.matcher(this.voucherNo);
                    if (!vouchNoChk.matches()) {
                        setMessage("Please fill numeric value !");
                        return;
                    }
                }
                if (intOption.equalsIgnoreCase("D")) {
                    if (savingRoi == null || savingRoi.equalsIgnoreCase("")) {
                        setMessage("Please fill Saving Roi.");
                        return;
                    }
                    Matcher savChk = p.matcher(this.savingRoi);
                    if (!savChk.matches()) {
                        setMessage("Please fill numeric value !");
                        return;
                    }
                }

            }
            if (intOption.equalsIgnoreCase("D")) {
                intCalcDt = "";
                finalDeafEffDt = "";
                //Get Month Deaf Effective Date
                String deafEffDate = ddsReportRemote.getDeafEfectiveDate();
                if (deafEffDate.equals("") || deafEffDate.equals("")) {
                    this.setMessage("Please define Deaf Effective Date");
                    return;
                }
                //Get Final Interest Calculation Date
//                intCalcDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month),
//                        Integer.parseInt(this.year)));

                intCalcDt = dmy.format(ymd.parse(CbsUtil.dateAdd(ymd.format(CbsUtil.
                        calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year))), -1)));

                String prevMonthDt = CbsUtil.monthAdd(ymd.format(dmy.parse(intCalcDt)), -1);

                //Final Deaf Effective Date
                finalDeafEffDt = prevMonthDt.substring(0, 6) + deafEffDate;
            }

            List<UnclaimedAccountStatementPojo> unClaimedList = new ArrayList<UnclaimedAccountStatementPojo>();
            if (intOption.equalsIgnoreCase("D")) {
                unClaimedList = ddsReportRemote.unClaimedMarkingSingle(getOrgnBrCode(), acNo, finalDeafEffDt,
                        intCalcDt, getTodayDate(), Double.parseDouble(savingRoi));//savingRoi parameter added by manish
                if (unClaimedList.isEmpty()) {
                    throw new ApplicationException("Data does not exits !");
                }
            }

            String deafmarkPost = "";
            if (function.equalsIgnoreCase("A") && intOption.equalsIgnoreCase("D")) {
                deafmarkPost = ddsReportRemote.insertDeafTransaction(getOrgnBrCode(), fts.getAccountNature(acNo),
                        unClaimedList, intCalcDt, getUserName(), "R", fts.getAccountCode(acNo), savingRoi,
                        finalDeafEffDt, getTodayDate());
            } else {
                deafmarkPost = ddsRemote.singleAcnoDeafAmtPost(function, currentItem.getTxnId(), acNo, deafAmt,
                        voucherNo, ymd.format(dmy.parse(effectDt)), getUserName());
            }

            if (!deafmarkPost.equalsIgnoreCase("true")) {
                this.setMessage("Problem in Deaf Marking! ");
                return;
            }
            resetForm();
            if (function.equalsIgnoreCase("U")) {
                this.setMessage("Update successfully !");
                setFunction("S");
            } else {
                this.setMessage("Deaf Marking post successfully !");
                setFunction("S");
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        setMessage("");
        setAcNo("");
        setIntOption("S");
        setDeafAmt("");
        setVoucherNo("");
        setEffectDt(dmy.format(date));
        setSavingRoi("");
        setMonth("");
        setYear("");
        setNewAcno("");
        //setFunction("S");
        setFunButton("Deaf Mark");
        setFrDt("");
        setToDt("");
        this.acnoDisable = false;
        gridDetail = new ArrayList<DeafMarkPojo>();
    }

    public String exitForm() {
        return "case1";
    }

    public boolean isSavingDis() {
        return savingDis;
    }

    public void setSavingDis(boolean savingDis) {
        this.savingDis = savingDis;
    }

    public String getEffectStyle() {
        return effectStyle;
    }

    public void setEffectStyle(String effectStyle) {
        this.effectStyle = effectStyle;
    }

    public String getLableButton() {
        return lableButton;
    }

    public void setLableButton(String lableButton) {
        this.lableButton = lableButton;
    }

    public String getMonthStyle() {
        return monthStyle;
    }

    public void setMonthStyle(String monthStyle) {
        this.monthStyle = monthStyle;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
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

    public String getDeafAmt() {
        return deafAmt;
    }

    public void setDeafAmt(String deafAmt) {
        this.deafAmt = deafAmt;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getEffectDt() {
        return effectDt;
    }

    public void setEffectDt(String effectDt) {
        this.effectDt = effectDt;
    }

    public boolean isVouchDis() {
        return vouchDis;
    }

    public void setVouchDis(boolean vouchDis) {
        this.vouchDis = vouchDis;
    }

    public boolean isDeafdis() {
        return deafdis;
    }

    public void setDeafdis(boolean deafdis) {
        this.deafdis = deafdis;
    }

    public String getSavingRoi() {
        return savingRoi;
    }

    public void setSavingRoi(String savingRoi) {
        this.savingRoi = savingRoi;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
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

    public boolean isIntOptdis() {
        return intOptdis;
    }

    public void setIntOptdis(boolean intOptdis) {
        this.intOptdis = intOptdis;
    }

    public String getFunButton() {
        return funButton;
    }

    public void setFunButton(String funButton) {
        this.funButton = funButton;
    }

    public DeafMarkPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(DeafMarkPojo currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getFrToDtstyle() {
        return frToDtstyle;
    }

    public void setFrToDtstyle(String frToDtstyle) {
        this.frToDtstyle = frToDtstyle;
    }

    public List<DeafMarkPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<DeafMarkPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isAcnoDisable() {
        return acnoDisable;
    }

    public void setAcnoDisable(boolean acnoDisable) {
        this.acnoDisable = acnoDisable;
    }
}
