/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.report.MinBalanceChargesPostPojo;
import com.cbs.facade.misc.MinBalanceChargesFacadeRemote;
import com.cbs.facade.other.DailyProcessManagementRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.AtmChargePostingPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AtmChargePosting extends BaseBean {

    private String branchOpt;
    private String frDt;
    private String toDt;
    private String totalChg;
    private String crHead;
    private String msg;
    private String trsNo;
    private boolean calcFlag1;
    private boolean dtEnable;
    private boolean btnCalFlag;
    private boolean btnPostFlag;
    private String atmChgDuration;
    private List<SelectItem> branchOptionList;
    private List<AtmChargePostingPojo> chargePostingList;
    DailyProcessManagementRemote dpRemote;
    MinBalanceChargesFacadeRemote miscRemote;
    CommonReportMethodsRemote commonReportRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String viewID = "/pages/master/sm/test.jsp";
    private static final Object LOCK = new Object();

    public String getBranchOpt() {
        return branchOpt;
    }

    public void setBranchOpt(String branchOpt) {
        this.branchOpt = branchOpt;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
    }

    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }

    public boolean isDtEnable() {
        return dtEnable;
    }

    public void setDtEnable(boolean dtEnable) {
        this.dtEnable = dtEnable;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getTotalChg() {
        return totalChg;
    }

    public void setTotalChg(String totalChg) {
        this.totalChg = totalChg;
    }

    public boolean isBtnCalFlag() {
        return btnCalFlag;
    }

    public void setBtnCalFlag(boolean btnCalFlag) {
        this.btnCalFlag = btnCalFlag;
    }

    public boolean isBtnPostFlag() {
        return btnPostFlag;
    }

    public void setBtnPostFlag(boolean btnPostFlag) {
        this.btnPostFlag = btnPostFlag;
    }

    public List<AtmChargePostingPojo> getChargePostingList() {
        return chargePostingList;
    }

    public void setChargePostingList(List<AtmChargePostingPojo> chargePostingList) {
        this.chargePostingList = chargePostingList;
    }

    public String getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(String trsNo) {
        this.trsNo = trsNo;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isCalcFlag1() {
        return calcFlag1;
    }

    public void setCalcFlag1(boolean calcFlag1) {
        this.calcFlag1 = calcFlag1;
    }

    public AtmChargePosting() {
        try {
            dpRemote = (DailyProcessManagementRemote) ServiceLocator.getInstance().lookup("DailyProcessManagementFacade");
            miscRemote = (MinBalanceChargesFacadeRemote) ServiceLocator.getInstance().lookup("MinBalanceChargesFacade");
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setMsg("");

            branchOptionList = new ArrayList<>();
            List list = commonReportRemote.getAtmChgDuration();
            Vector v = (Vector) list.get(0);
            atmChgDuration = v.get(0).toString();


            //New Addition
            List allBranchCodeList = null;
            Integer code = commonReportRemote.getCodeByReportName("ATM-CHARGE-LOCATION");
            if (code == 0) {
                allBranchCodeList = dpRemote.getAllBranchCode();
                if (allBranchCodeList.isEmpty()) {
                    this.setMsg("Please define branchmaster.");
                    return;
                }

                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchOptionList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString()
                            : vec.get(0).toString(), vec.get(1).toString()));
                }
            } else if (code == 1) {
                String alphaCode = commonReportRemote.getAlphacodeByBrncode(getOrgnBrCode());
                if (alphaCode.equalsIgnoreCase("HO")) {
                    allBranchCodeList = dpRemote.getAllBranchCode();
                    if (allBranchCodeList.isEmpty()) {
                        this.setMsg("Please define branchmaster.");
                        return;
                    }

                    for (int i = 0; i < allBranchCodeList.size(); i++) {
                        Vector vec = (Vector) allBranchCodeList.get(i);
                        branchOptionList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString()
                                : vec.get(0).toString(), vec.get(1).toString()));
                    }
                } else {
                    allBranchCodeList = commonReportRemote.getAlphacodeBasedOnBranch(getOrgnBrCode());
                    if (allBranchCodeList.isEmpty()) {
                        this.setMsg("Please define branchmaster.");
                        return;
                    }

                    for (int i = 0; i < allBranchCodeList.size(); i++) {
                        Vector vec = (Vector) allBranchCodeList.get(i);
                        branchOptionList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString()
                                : vec.get(1).toString(), vec.get(0).toString()));
                    }
                }
            }

            //End Here

//            List allBranchCodeList = dpRemote.getAllBranchCode();
//            if (!allBranchCodeList.isEmpty()) {
//                for (int i = 0; i < allBranchCodeList.size(); i++) {
//                    Vector vec = (Vector) allBranchCodeList.get(i);
//                    branchOptionList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString() : vec.get(0).toString(), vec.get(1).toString()));
//                }
//            }
            setBtnCalFlag(false);
            setBtnPostFlag(true);
            setDtEnable(true);
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public void setBranchOptions() {
        try {
            this.setMsg("");
            setCrHead("");
            setTotalChg("");
            chargePostingList = new ArrayList<AtmChargePostingPojo>();
            String fromDt = "";
            String toDt = "";
            this.setFrDt(fromDt);
            this.setToDt(toDt);

            setBtnCalFlag(false);
            setBtnPostFlag(true);

            fromDt = miscRemote.getAllFromDt(this.branchOpt, "f");
            toDt = miscRemote.getAllFromDt(this.branchOpt, "t");

            this.setFrDt(sdf.format(ymd.parse(fromDt)));
            this.setToDt(sdf.format(ymd.parse(toDt)));
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public String validation() {
        try {
            if (this.frDt == null || this.toDt == null || this.frDt.equals("") || this.toDt.equals("")) {
                return "Please fill from and/or to date properly.";
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.frDt) == false) {
                return "Please fill proper from date.";
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.toDt) == false) {
                return "Please fill proper to date.";
            }
            if (sdf.parse(this.frDt).compareTo(sdf.parse(this.toDt)) == 0) {
                return "From and to date can not be same.";
            }
            if (sdf.parse(this.frDt).compareTo(sdf.parse(this.toDt)) == 1) {
                return "From date can not be greater than to date.";
            }
            if (!atmChgDuration.equalsIgnoreCase("M")) {
                if (sdf.parse(this.toDt).compareTo(sdf.parse(sdf.format(new Date()))) == 1) {
                    return "To date can not be greater than current date.";
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
        return "true";
    }

    public void calculateAction() {
        this.setMsg("");
        try {
            if (validation().equalsIgnoreCase("true")) {
                String creditedGlHead = "";
                double chgAmount = 0.0, totalChgAmount = 0.0;
                String code = miscRemote.getAtmChargeCode();
                List list = miscRemote.getAtmChargeParam();
                if (list.isEmpty()) {
                    this.setMsg("Please fill data into Parameterinfo_Miscincome for ATM Charge.");
                    return;
                } else {
                    Vector chgVec = (Vector) list.get(0);
                    creditedGlHead = chgVec.get(0).toString();
                    chgAmount = Double.parseDouble(chgVec.get(1).toString());
                }
                if (code.equalsIgnoreCase("0")) {
                    creditedGlHead = this.getBranchOpt() + creditedGlHead + "01";
                } else {
                    if (this.getBranchOpt().equalsIgnoreCase(this.getOrgnBrCode())) {
                        creditedGlHead = this.getBranchOpt() + creditedGlHead + "01";
                    } else {
                        creditedGlHead = "90" + creditedGlHead + "01";
                    }
                }
                int i = 0;
                chargePostingList = new ArrayList<>();
                List dataList = miscRemote.getAllAtmAccount(this.getBranchOpt(), ymd.format(sdf.parse(frDt)), ymd.format(sdf.parse(toDt)));
                if (dataList.isEmpty()) {
                    this.setMsg("There is no data to post.");
                    return;
                } else {
                    for (int j = 0; j < dataList.size(); j++) {
                        Vector ele = (Vector) dataList.get(j);
                        AtmChargePostingPojo pojo = new AtmChargePostingPojo();
                        pojo.setSno(++i);
                        pojo.setAcno(ele.get(0).toString());
                        int cnt = Integer.parseInt(ele.get(1).toString());
                        pojo.setNoOfMsg(cnt);
                        double cAmt = cnt * chgAmount;
                        pojo.setCharges(Double.toString(cAmt));
                        totalChgAmount += cAmt;
                        chargePostingList.add(pojo);
                    }
                    this.setCrHead(creditedGlHead);
                    this.setTotalChg(Double.toString(totalChgAmount));
                }
                this.setBtnCalFlag(true);
                this.setBtnPostFlag(false);
            } else {
                this.setMsg(validation());
            }
            System.out.println(branchOpt);
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void postAction() {
        System.out.println(branchOpt);
        msg = validation();
        Date dt = new Date();
        try {
            if (msg.equalsIgnoreCase("true")) {
                msg = "";
                synchronized (LOCK) {
                    msg = miscRemote.atmChargesPost(chargePostingList, this.getCrHead(), this.getUserName(), this.getBranchOpt(), this.getOrgnBrCode(), Double.parseDouble(this.getTotalChg()), ymd.format(dt), ymd.format(sdf.parse(frDt)), ymd.format(sdf.parse(toDt)));
                }
                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                    calcFlag1 = true;
                    trsNo = msg.substring(4);
                    this.setMsg("Transaction Posted Successfully. " + "Transfer Batch No :- " + trsNo);
                    String dateString = sdf.format(dt);
                    List<MinBalanceChargesPostPojo> minBalanceChargesPostReport = miscRemote.atmChargesPostReport(Float.parseFloat(trsNo), this.getUserName(), ymd.format(dt), this.crHead.substring(0, 2), this.branchOpt);
                    String report = "ATM CHARGE REPORT";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", report);
                    fillParams.put("pReportDate", dateString);
                    fillParams.put("pPrintedBy", this.getUserName());
                    fillParams.put("pAcType", "ALL");
                    if (!minBalanceChargesPostReport.isEmpty()) {
                        new ReportBean().jasperReport("ATM_CHARGE_REPORT", "text/html", new JRBeanCollectionDataSource(minBalanceChargesPostReport), fillParams, report);
                        this.setViewID("/report/ReportPagePopUp.jsp");
                    }
                    chargePostingList = new ArrayList<>();
                    this.setFrDt(getTodayDate());
                    this.setToDt(getTodayDate());
                    this.setTotalChg("");
                    this.setCrHead("");
                    this.setBtnCalFlag(false);
                    this.setBtnPostFlag(true);
                } else {
                    calcFlag1 = false;
                    this.setMsg(msg);
                }
            } else {
                this.setMsg(msg);
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void refreshForm() {
        chargePostingList = new ArrayList<>();
        this.setMsg("");
        this.setFrDt(getTodayDate());
        this.setToDt(getTodayDate());
        this.setTotalChg("");
        this.setCrHead("");
        this.setBtnCalFlag(false);
        this.setBtnPostFlag(true);
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}