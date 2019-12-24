/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.CashHandlingChargeGridData;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.misc.MinBalanceChargesFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
//import com.cbs.web.pojo.misc.CashHandlingChargeGridData;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class CashHandlingCharge extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String actNature;
    private List<SelectItem> actNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    private Date frDt;
    private Date toDt;
    private Date date = new Date();
    private boolean fromDisable;
    private boolean toDisable;
    private boolean reportFlag;
    private boolean disablePost;
    private boolean disableCal;
    private double totalDebit;
    private List<CashHandlingChargeGridData> gridData;
    private String viewID = "/pages/master/sm/test.jsp";
    private CommonReportMethodsRemote common;
    private LoanInterestCalculationFacadeRemote beanRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#0.00");
    private MinBalanceChargesFacadeRemote minfacadeRemote;

    public CashHandlingCharge() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup("LoanInterestCalculationFacade");
            minfacadeRemote = (MinBalanceChargesFacadeRemote) ServiceLocator.getInstance().lookup("MinBalanceChargesFacade");

            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }
            List acNatureList = common.getAllAcNature();
            actNatureList = new ArrayList<SelectItem>();
            actNatureList.add(new SelectItem("S", "--Select--"));
            if (!acNatureList.isEmpty()) {
                for (int i = 0; i < acNatureList.size(); i++) {
                    Vector vtr = (Vector) acNatureList.get(i);
                    actNatureList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
            setDisablePost(true);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        setMessage("");
        try {
            gridData = new ArrayList<CashHandlingChargeGridData>();
            List resultList = common.getActCodeByAcNature(actNature);
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("ALL", "ALL"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    acTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setDateAllAccount() {
        try {
            this.setMessage("");
            String fromDt = "";
            String toDate = "";
            if (acType.equalsIgnoreCase("ALL")) {
                acType = actNature;
            }
            if (!acType.equals("0") && !acType.equals("")) {
                fromDt = beanRemote.allFromDtForCharge(acType, getOrgnBrCode(), "f", "CH");
                if (fromDt.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setFrDt(sdf.parse(sdf.format(ymd.parse(fromDt))));
                    setFromDisable(true);
                }
                toDate = beanRemote.allFromDtForCharge(acType, getOrgnBrCode(), "t", "CH");
                if (toDate.equalsIgnoreCase("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.")) {
                    this.setMessage("Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.");
                } else {
                    this.setToDt(sdf.parse(sdf.format(ymd.parse(toDate))));
                    setToDisable(true);
                }
            }
            this.setDisableCal(false);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void calculate() {
        setMessage("");
        try {
            viewID = "/pages/master/sm/test.jsp";
            gridData = new ArrayList<CashHandlingChargeGridData>();
            gridData = minfacadeRemote.calculateCashHandlingCharges(branch, actNature, acType, ymd.format(frDt), ymd.format(toDt));
            if (gridData.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            setDisablePost(false);
            String report = "Cash Handling Charge Report";
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", sdf.format(this.frDt) + " to " + sdf.format(this.toDt));
            fillParams.put("pReportName", report);
            new ReportBean().jasperReport("CashHandlingCharge", "text/html", new JRBeanCollectionDataSource(gridData), fillParams, report);
            this.setReportFlag(true);
            this.setViewID("/report/ReportPagePopUp.jsp");
            this.setDisableCal(true);
       

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void Post() {
        setMessage("");
        try {

            String result = minfacadeRemote.postCashChargeData(gridData, branch, actNature, acType, ymd.format(frDt), ymd.format(toDt), getUserName(), ymd.format(sdf.parse(getTodayDate())), "060610070401");
            if (result.contains("true")) {
                this.setMessage("Charge post successfully and Batch No . "+result.substring(4));
            }
            gridData = new ArrayList<CashHandlingChargeGridData>();
            this.setDisablePost(true);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        setMessage("");
        this.setActNature("S");
        this.setAcType("");
        this.setFrDt(date);
        this.setToDt(date);
        gridData = new ArrayList<CashHandlingChargeGridData>();
        //acTypeList = new ArrayList<SelectItem>();
        setDisablePost(true);
    }

    public String exitBtnAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getActNature() {
        return actNature;
    }

    public void setActNature(String actNature) {
        this.actNature = actNature;
    }

    public List<SelectItem> getActNatureList() {
        return actNatureList;
    }

    public void setActNatureList(List<SelectItem> actNatureList) {
        this.actNatureList = actNatureList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public Date getFrDt() {
        return frDt;
    }

    public void setFrDt(Date frDt) {
        this.frDt = frDt;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(Boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public Boolean getToDisable() {
        return toDisable;
    }

    public void setToDisable(Boolean toDisable) {
        this.toDisable = toDisable;
    }

    public boolean isFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public boolean isToDisable() {
        return toDisable;
    }

    public void setToDisable(boolean toDisable) {
        this.toDisable = toDisable;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public List<CashHandlingChargeGridData> getGridData() {
        return gridData;
    }

    public void setGridData(List<CashHandlingChargeGridData> gridData) {
        this.gridData = gridData;
    }

    public double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public boolean isDisableCal() {
        return disableCal;
    }

    public void setDisableCal(boolean disableCal) {
        this.disableCal = disableCal;
    }
}
