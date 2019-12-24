/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.pojo.SortedByAcType;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Administrator
 */
public class DemandMarkAndPost extends BaseBean {

    private String message;
    private String actType;
    private List<SelectItem> actList;
    private String offId;
    private List<SelectItem> offIdList;
    private String acctIdStyle;
    private String demandIdStyle;
    private String acctId;
    private List<SelectItem> accountIdList;
    private String demandId;
    private List<SelectItem> demandIdList;
    private String month;
    private String year;
    private List<SelectItem> monthYearList;
    private String recoveryType;
    private List<SelectItem> recoveryList;
    private String acctNo, acNoLen;
    //private String orgDate;
    //private String seqNo;
    boolean disableMark;
    boolean disablePost;
    boolean disablePrn;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    ShareTransferFacadeRemote remoteObject;
    private final String jndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private CommonReportMethodsRemote common;
    private LoanReportFacadeRemote loanRemote;
    boolean disableOffId;
    boolean disableAcctId;
    boolean disableDemId;
    boolean disableMonth;
    boolean disableYear;
    boolean disableRecTp;
    boolean disableAccNo;
    //boolean disableOrgDt;
    //boolean disableSeqNo;
    private String chqNo;
    boolean chqNoFlag;
    private Date chqueDate;
    boolean chqDateFlag;
    List<VillageWiseEMIDetailPojo> reportList = new ArrayList<VillageWiseEMIDetailPojo>();
    private List<VillageWiseEMIDetailPojo> tableList;
    private String viewID = "/pages/master/sm/test.jsp";
    private boolean calcFlag;
    private boolean disableActFn;
    private String gridStyle;
    private String acFlag;
    private String totAmt;
    NumberFormat formatter = new DecimalFormat("#.00");
    private VillageWiseEMIDetailPojo currentItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public List<SelectItem> getActList() {
        return actList;
    }

    public void setActList(List<SelectItem> actList) {
        this.actList = actList;
    }

    public String getOffId() {
        return offId;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public List<SelectItem> getOffIdList() {
        return offIdList;
    }

    public void setOffIdList(List<SelectItem> offIdList) {
        this.offIdList = offIdList;
    }

    public String getAcctIdStyle() {
        return acctIdStyle;
    }

    public void setAcctIdStyle(String acctIdStyle) {
        this.acctIdStyle = acctIdStyle;
    }

    public String getDemandIdStyle() {
        return demandIdStyle;
    }

    public void setDemandIdStyle(String demandIdStyle) {
        this.demandIdStyle = demandIdStyle;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public List<SelectItem> getAccountIdList() {
        return accountIdList;
    }

    public void setAccountIdList(List<SelectItem> accountIdList) {
        this.accountIdList = accountIdList;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public List<SelectItem> getDemandIdList() {
        return demandIdList;
    }

    public void setDemandIdList(List<SelectItem> demandIdList) {
        this.demandIdList = demandIdList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<SelectItem> getMonthYearList() {
        return monthYearList;
    }

    public void setMonthYearList(List<SelectItem> monthYearList) {
        this.monthYearList = monthYearList;
    }

    public String getRecoveryType() {
        return recoveryType;
    }

    public void setRecoveryType(String recoveryType) {
        this.recoveryType = recoveryType;
    }

    public List<SelectItem> getRecoveryList() {
        return recoveryList;
    }

    public void setRecoveryList(List<SelectItem> recoveryList) {
        this.recoveryList = recoveryList;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

//    public String getOrgDate() {
//        return orgDate;
//    }
//
//    public void setOrgDate(String orgDate) {
//        this.orgDate = orgDate;
//    }
//
//    public String getSeqNo() {
//        return seqNo;
//    }
//
//    public void setSeqNo(String seqNo) {
//        this.seqNo = seqNo;
//    }
    public boolean isDisableMark() {
        return disableMark;
    }

    public void setDisableMark(boolean disableMark) {
        this.disableMark = disableMark;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public boolean isDisablePrn() {
        return disablePrn;
    }

    public void setDisablePrn(boolean disablePrn) {
        this.disablePrn = disablePrn;
    }

    public boolean isDisableOffId() {
        return disableOffId;
    }

    public void setDisableOffId(boolean disableOffId) {
        this.disableOffId = disableOffId;
    }

    public boolean isDisableAcctId() {
        return disableAcctId;
    }

    public void setDisableAcctId(boolean disableAcctId) {
        this.disableAcctId = disableAcctId;
    }

    public boolean isDisableDemId() {
        return disableDemId;
    }

    public void setDisableDemId(boolean disableDemId) {
        this.disableDemId = disableDemId;
    }

    public boolean isDisableMonth() {
        return disableMonth;
    }

    public void setDisableMonth(boolean disableMonth) {
        this.disableMonth = disableMonth;
    }

    public boolean isDisableYear() {
        return disableYear;
    }

    public void setDisableYear(boolean disableYear) {
        this.disableYear = disableYear;
    }

    public boolean isDisableRecTp() {
        return disableRecTp;
    }

    public void setDisableRecTp(boolean disableRecTp) {
        this.disableRecTp = disableRecTp;
    }

    public boolean isDisableAccNo() {
        return disableAccNo;
    }

    public void setDisableAccNo(boolean disableAccNo) {
        this.disableAccNo = disableAccNo;
    }

//    public boolean isDisableOrgDt() {
//        return disableOrgDt;
//    }
//
//    public void setDisableOrgDt(boolean disableOrgDt) {
//        this.disableOrgDt = disableOrgDt;
//    }
//
//    public boolean isDisableSeqNo() {
//        return disableSeqNo;
//    }
//
//    public void setDisableSeqNo(boolean disableSeqNo) {
//        this.disableSeqNo = disableSeqNo;
//    }
    public String getChqNo() {
        return chqNo;
    }

    public void setChqNo(String chqNo) {
        this.chqNo = chqNo;
    }

    public boolean isChqNoFlag() {
        return chqNoFlag;
    }

    public void setChqNoFlag(boolean chqNoFlag) {
        this.chqNoFlag = chqNoFlag;
    }

    public Date getChqueDate() {
        return chqueDate;
    }

    public void setChqueDate(Date chqueDate) {
        this.chqueDate = chqueDate;
    }

    public boolean isChqDateFlag() {
        return chqDateFlag;
    }

    public void setChqDateFlag(boolean chqDateFlag) {
        this.chqDateFlag = chqDateFlag;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }

    public boolean isDisableActFn() {
        return disableActFn;
    }

    public void setDisableActFn(boolean disableActFn) {
        this.disableActFn = disableActFn;
    }

    public List<VillageWiseEMIDetailPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<VillageWiseEMIDetailPojo> tableList) {
        this.tableList = tableList;
    }

    public String getGridStyle() {
        return gridStyle;
    }

    public void setGridStyle(String gridStyle) {
        this.gridStyle = gridStyle;
    }

    public String getAcFlag() {
        return acFlag;
    }

    public void setAcFlag(String acFlag) {
        this.acFlag = acFlag;
    }

    public String getTotAmt() {
        return totAmt;
    }

    public void setTotAmt(String totAmt) {
        this.totAmt = totAmt;
    }

    public VillageWiseEMIDetailPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(VillageWiseEMIDetailPojo currentItem) {
        this.currentItem = currentItem;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public DemandMarkAndPost() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            this.setAcNoLen(getAcNoLength());
            setTodayDate(sdf.format(date));
            getListValues();
            getOfficeIdData();
            setAcctIdStyle("block");
            setDemandIdStyle("none");
            setGridStyle("none");
            setDisableOffId(false);
            setDisableAcctId(false);
            setDisableMonth(false);
            setDisableYear(false);
            setDisableRecTp(true);
            setDisableAccNo(true);
//            setDisableOrgDt(true);
//            setDisableSeqNo(true);
            setChqNoFlag(true);
            setChqDateFlag(true);
            setDisablePost(true);
            setDisablePrn(false);
            setDisableMark(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getListValues() {
        try {
            String levelId = remoteObject.getLevelId(getUserName(), getOrgnBrCode());

            actList = new ArrayList<SelectItem>();
            actList.add(new SelectItem("G", "Generation"));
            if (levelId.equals("1") || levelId.equals("2")) {
                actList.add(new SelectItem("P", "Posting"));
            }

            recoveryList = new ArrayList<SelectItem>();
            recoveryList.add(new SelectItem("C", "Cash"));
            recoveryList.add(new SelectItem("T", "Transfer"));

            monthYearList = new ArrayList<SelectItem>();
            monthYearList.add(new SelectItem("S", "--SELECT--"));
            monthYearList.add(new SelectItem("1", "January"));
            monthYearList.add(new SelectItem("2", "February"));
            monthYearList.add(new SelectItem("3", "March"));
            monthYearList.add(new SelectItem("4", "April"));
            monthYearList.add(new SelectItem("5", "May"));
            monthYearList.add(new SelectItem("6", "June"));
            monthYearList.add(new SelectItem("7", "july"));
            monthYearList.add(new SelectItem("8", "August"));
            monthYearList.add(new SelectItem("9", "September"));
            monthYearList.add(new SelectItem("10", "October"));
            monthYearList.add(new SelectItem("11", "November"));
            monthYearList.add(new SelectItem("12", "December"));

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void eventAction() {
        if (this.actType.equalsIgnoreCase("G")) {
            tableList = new ArrayList<VillageWiseEMIDetailPojo>();
            setDisableOffId(false);
            setDisableAcctId(false);
            setDisableMonth(false);
            setDisableYear(false);
            setDisableRecTp(true);
            setDisableAccNo(true);
//            setDisableOrgDt(true);
//            setDisableSeqNo(true);
            setChqNoFlag(true);
            setChqDateFlag(true);
            setDisablePost(true);
            setDisablePrn(false);
            setDisableMark(true);
            setAcctIdStyle("block");
            setDemandIdStyle("none");
            setGridStyle("none");
        } else {
            this.setRecoveryType("C");
            setDisableOffId(false);
            setDisableAcctId(false);
            setDisableMonth(true);
            setDisableYear(true);
            setDisableRecTp(false);
            setDisableAccNo(true);
//            setDisableOrgDt(true);
//            setDisableSeqNo(true);
            setChqNoFlag(true);
            setChqDateFlag(true);
            setDisablePrn(true);
            setDisableMark(true);
            setDisablePost(false);
            setAcctIdStyle("none");
            setDemandIdStyle("block");
            setGridStyle("block");
        }
    }

    public void getOfficeIdData() {
        setMessage("");
        try {
            List mangList = new ArrayList();
            mangList = common.getAccountManager();
            offIdList = new ArrayList<SelectItem>();
            offIdList.add(new SelectItem("S", "--Select--"));
            if (!mangList.isEmpty()) {
                for (int i = 0; i < mangList.size(); i++) {
                    Vector ele = (Vector) mangList.get(i);
                    offIdList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAcctIdData() {
        setMessage("");
        try {
            if (offId == null || offId.equalsIgnoreCase("S")) {
                setMessage("Please select Office Id");
                return;
            }
            if (this.getActType().equalsIgnoreCase("G")) {
                List groupList = new ArrayList();
                groupList = common.getGroupId(this.getOffId());
                accountIdList = new ArrayList<SelectItem>();
                if (!groupList.isEmpty()) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Vector eleId = (Vector) groupList.get(i);
                        accountIdList.add(new SelectItem(eleId.get(0).toString(), eleId.get(1).toString()));
                    }
                }
            } else {
                List demList = new ArrayList();
                demList = common.getUnRecoveredDemand(this.getOffId(), this.getOrgnBrCode());
                demandIdList = new ArrayList<SelectItem>();
                if (!demList.isEmpty()) {
                    for (int i = 0; i < demList.size(); i++) {
                        Vector eleId = (Vector) demList.get(i);
                        demandIdList.add(new SelectItem(eleId.get(0).toString(), eleId.get(0).toString()));
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void demandAction() {
        tableList = new ArrayList<VillageWiseEMIDetailPojo>();
        double tAmt = 0.0;
        try {
            List<VillageWiseEMIDetailPojo> resultList = common.getDemandDetails(this.getDemandId());
            if (resultList.isEmpty()) {
                this.setMessage("Data does not exist");
                this.setDisablePost(true);
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    VillageWiseEMIDetailPojo demandTable = new VillageWiseEMIDetailPojo();
                    demandTable.setsNo(Integer.toString(i + 1));
                    demandTable.setAcNo(resultList.get(i).getAcNo());
                    demandTable.setName(resultList.get(i).getName());
                    demandTable.setInstallment(resultList.get(i).getInstallment());
                    demandTable.setSchdlNo(resultList.get(i).getSchdlNo());
                    demandTable.setDate(resultList.get(i).getDate());
                    demandTable.setDemEffDt(resultList.get(i).getDemEffDt());
                    demandTable.setOutStandBal(resultList.get(i).getInstallment());
                    demandTable.setDemandAmt(resultList.get(i).getDemandAmt());
                    demandTable.setAcTypeDesc(resultList.get(i).getAcTypeDesc());

                    tableList.add(demandTable);
                    tAmt = tAmt + Double.parseDouble(resultList.get(i).getDemandAmt());
                }
            }
            totAmt = formatter.format(tAmt);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void gridAmtAction() {
        String amtVal = currentItem.getDemandAmt();

        if (validateAmount(amtVal) != true) {
            return;
        }

        try {
            if (currentItem != null) {
                if (currentItem.getOutStandBal() < Double.parseDouble(currentItem.getDemandAmt())) {
                    double difAmt = Double.parseDouble(currentItem.getDemandAmt()) - currentItem.getOutStandBal();
                    totAmt = formatter.format(Double.parseDouble(totAmt) + difAmt);
                } else if (currentItem.getOutStandBal() > Double.parseDouble(currentItem.getDemandAmt())) {
                    double difAmt = currentItem.getOutStandBal() - Double.parseDouble(currentItem.getDemandAmt());
                    totAmt = formatter.format(Double.parseDouble(totAmt) - difAmt);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validateAmount(String amtVal) {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

        if (amtVal == null || amtVal.equalsIgnoreCase("")) {
            this.setMessage("Please Fill Amount Field.");
            return false;
        } else if (amtVal.equalsIgnoreCase("0") || amtVal.equalsIgnoreCase("0.0")) {
            this.setMessage("Please Fill Amount Field.");
            return false;
        } else {
            Matcher amountTxnCheck = p.matcher(amtVal);
            if (!amountTxnCheck.matches()) {
                this.setMessage("Invalid Entry.");
                return false;
            }
        }

        if (amtVal.contains(".")) {
            if (amtVal.indexOf(".") != amtVal.lastIndexOf(".")) {
                this.setMessage("Invalid Amount.Please Fill The Amount Correctly.");
                return false;
            } else if (amtVal.substring(amtVal.indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }

    public void recoveryOptAction() {
        this.setMessage("");
        if (this.actType.equalsIgnoreCase("G")) {
            setDisableOffId(false);
            setDisableAcctId(false);
            setDisableMonth(false);
            setDisableYear(false);
            setDisableRecTp(true);
            setDisableAccNo(true);
//            setDisableOrgDt(true);
//            setDisableSeqNo(true);
            setChqNoFlag(true);
            setChqDateFlag(true);
            setDisablePost(true);
            setDisablePrn(false);
            setDisableMark(true);
            setAcctIdStyle("block");
            setDemandIdStyle("none");
        } else {
            setAcctIdStyle("none");
            setDemandIdStyle("block");
            if (this.recoveryType.equalsIgnoreCase("C")) {
                this.setAcctNo("");
                this.setChqNo("");
                this.setChqueDate(null);
//                this.setOrgDate("");
//                this.setSeqNo("");
                setDisableOffId(false);
                setDisableAcctId(false);
                setDisableMonth(true);
                setDisableYear(true);
                setDisableRecTp(false);
                setDisableAccNo(true);
//                setDisableOrgDt(true);
//                setDisableSeqNo(true);
                setChqNoFlag(true);
                setChqDateFlag(true);
                setDisablePost(false);
                setDisablePrn(true);
                setDisableMark(true);
            } else {
                setDisableOffId(false);
                setDisableAcctId(false);
                setDisableMonth(true);
                setDisableYear(true);
                setDisableRecTp(false);
                setDisableAccNo(false);
//                setDisableOrgDt(true);
//                setDisableSeqNo(true);
                setChqNoFlag(true);
                setChqDateFlag(true);
                setDisablePost(false);
                setDisablePrn(true);
                setDisableMark(true);
            }
        }
    }

    public void accNoAction() {
        try {
            if (this.acctNo.equalsIgnoreCase("") || this.acctNo == null || this.acctNo.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter 12 Digit Account Number.");
                return;
            }
            //if (!this.acctNo.equalsIgnoreCase("") && this.acctNo.length() < 12) {
            if (!this.acctNo.equalsIgnoreCase("") && ((this.acctNo.length() != 12) && (this.acctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please Enter Proper Account Number.");
                return;
            }
            String accNo = ftsPostRemote.getNewAccountNumber(acctNo);
            String chkAccNo = txnRemote.checkForAccountNo(this.acctNo);
            if (!chkAccNo.equals("TRUE")) {
                this.setMessage(chkAccNo);
                return;
            }
            String acNature = ftsPostRemote.getAccountNature(acctNo);
            if (acNature.equalsIgnoreCase(CbsConstant.GL_AC) || acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                acFlag = "S";
//                setDisableOrgDt(false);
//                setDisableSeqNo(false);
                setChqNoFlag(true);
                setChqDateFlag(true);
            } else {
                acFlag = "C";
                setChqNoFlag(false);
                setChqDateFlag(false);
//                setDisableOrgDt(true);
//                setDisableSeqNo(true);
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void selectChqDate() {
        try {
            if (chqueDate == null) {
                this.setMessage("Please Fill Cheque No And Cheque Date");
                return;
            }

            String chqDt = dmy.format(chqueDate);
            String Tempbd = dmy.format(date);
            Calendar chqDate = Calendar.getInstance();
            Calendar Tmpbd = Calendar.getInstance();
            chqDate.set(Integer.parseInt(chqDt.substring(0, 4)), Integer.parseInt(chqDt.substring(4, 6)), Integer.parseInt(chqDt.substring(6)));
            Tmpbd.set(Integer.parseInt(Tempbd.substring(0, 4)), Integer.parseInt(Tempbd.substring(4, 6)), Integer.parseInt(Tempbd.substring(6)));

            String tempDate = Tempbd.substring(0, 4) + Tempbd.substring(4, 6) + Tempbd.substring(6);
            Long longTempDate = Long.parseLong(tempDate);
            Long longChqDate = Long.parseLong(chqDt);

            if (!validateInstNoDtAgainstOpenDt(chqueDate).equalsIgnoreCase("true")) {
                this.setMessage("Cheque date should not be less than account opening date !");
                return;
            }

            if (longChqDate.compareTo(longTempDate) > 0) {
                this.setMessage("Post dated" + "  " + "is not allowed");
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String validateInstNoDtAgainstOpenDt(Date chequeDt) {
        String result = "true";
        try {
            String openDate = "";
            List listForDSFDMSOF = txnRemote.selectFromAcctMaster(acctNo);
            if (!listForDSFDMSOF.isEmpty()) {
                Vector v1 = (Vector) listForDSFDMSOF.get(0);
                openDate = v1.get(9).toString().substring(6) + "/" + v1.get(9).toString().substring(4, 6) + "/" + v1.get(9).toString().substring(0, 4);
            }
            String acNature = ftsPostRemote.getAccountNature(acctNo);
            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                Long longChqDate = Long.parseLong(dmy.format(chqueDate));
                if (longChqDate.compareTo(Long.parseLong(dmy.format(sdf.parse(openDate)))) < 0) {
                    result = "false";
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

        return result;
    }

    public void refresh() {
        this.setActType("G");
        setAcctIdStyle("block");
        setDemandIdStyle("none");
        this.setOffId("");
        accountIdList = new ArrayList<SelectItem>();
        tableList = new ArrayList<VillageWiseEMIDetailPojo>();
        this.setYear("");
        this.setMonth("");
        this.setRecoveryType("C");
        this.setAcctNo("");
//        this.setOrgDate("");
//        this.setSeqNo("");
        setDisableOffId(false);
        setDisableAcctId(false);
        setDisableMonth(false);
        setDisableYear(false);
        setDisableRecTp(true);
        setDisableAccNo(true);
//        setDisableOrgDt(true);
//        setDisableSeqNo(true);
        setChqNoFlag(true);
        setChqDateFlag(true);
        setDisablePost(true);
        setDisablePrn(false);
        setDisableMark(true);
        setDisableActFn(false);
        this.setMessage("");
        setGridStyle("none");
        setChqNo("");
        setChqueDate(null);
        setTotAmt("");
    }

    public void btnHtmlAction() {
        calcFlag = true;
        setMessage("");
        String report = "Village Wise EMI Detail Report";
        String acType = "ALL";
        String bankName = "", bankAddress = "";
        String frDt;
        String toDt;
        try {
            if (offId == null || offId.equalsIgnoreCase("S")) {
                setMessage("Please select Office Id");
                calcFlag = false;
                return;
            }
            if ((acctId == null) || (acctId.equalsIgnoreCase(""))) {
                this.setMessage("Account Id can not be Blank!!!");
                calcFlag = false;
                return;
            }
            if (month == null || month.equalsIgnoreCase("S")) {
                setMessage("Please select Month");
                calcFlag = false;
                return;
            }
            if (year == null || year.equalsIgnoreCase("")) {
                setMessage("Please Fill Year");
                calcFlag = false;
                return;
            }

            frDt = "01" + "/" + month + "/" + year;
            toDt = sdf.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month),
                    Integer.parseInt(this.year)));
            List dataList1 = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pActDesc", acType);
            fillParams.put("pReportName", common.getEmiReportName() + " Emi Detail Report");
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAddress", bankAddress);
            fillParams.put("pReportDt", frDt + " To " + toDt);
            fillParams.put("pfrDt", frDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pAcctType", acType);
            fillParams.put("pAccountManager", this.offId);
            fillParams.put("pGroupId", this.acctId);
            List phoneList = common.getPhoneNo(this.getOrgnBrCode());
            Vector vtr = (Vector) phoneList.get(0);
            fillParams.put("pPhoneNo", vtr.get(0).toString());
            fillParams.put("pDeptId", "Dept Id : [" + this.acctId + "] " + common.getDeptDescByGroupId(acctId));

            String yyyy = dmy.format(sdf.parse(toDt)).substring(0, 4);
            String mm = dmy.format(sdf.parse(toDt)).substring(4, 6);
            fillParams.put("pMonthYear", CbsUtil.getMonthName(Integer.parseInt(mm)) + " " + yyyy);

            List officeIdList = common.getOfficeDeptHead(this.offId, this.acctId);
            Vector vtr1 = (Vector) officeIdList.get(0);
            fillParams.put("pOfficeHead", vtr1.get(0).toString());
            fillParams.put("pOfficeName", vtr1.get(1).toString());
            fillParams.put("pOfficeHeadAdd", vtr1.get(2).toString());
            String bnkCode = ftsPostRemote.getBankCode();
            if (bnkCode.equalsIgnoreCase("NABU")) {
                fillParams.put("pHeader", "Over Due Prin/Interest");
            } else {
                fillParams.put("pHeader", "Premium Amount");
            }

            reportList = loanRemote.getVillWeseEmiDetail("T", this.getOrgnBrCode(), acType, "", dmy.format(sdf.parse(frDt)), dmy.format(sdf.parse(toDt)), offId, acctId);
            if (reportList.isEmpty()) {
                calcFlag = false;
                throw new ApplicationException("Data does not exist!!!");
            }

            ComparatorChain chObj = new ComparatorChain();
            //chObj.addComparator(new SortedByDeptId());
            chObj.addComparator(new SortedByAcType());
            Collections.sort(reportList, chObj);

            new ReportBean().jasperReport("VillageWiseEmiDetail_Letter_AllAcno_Text", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Village Wise EMI Detail Report");
            this.setViewID("/report/ReportPagePopUp.jsp");
            setDisableOffId(true);
            setDisableAcctId(true);
            setDisableMonth(true);
            setDisableYear(true);
            setDisableMark(false);
            setDisableActFn(true);
        } catch (Exception e) {
            calcFlag = false;
            setMessage(e.getMessage());
        }
    }

    public void btnMarkAction() {
        try {
            if (reportList.isEmpty()) {
                this.setMessage("No Data To SAVE!!!");
                return;
            } else {
                String frDt = "01" + "/" + month + "/" + year;
                String toDt = sdf.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month),
                        Integer.parseInt(this.year)));

                String result = txnRemote.markDemand(reportList, this.getUserName(), toDt, this.getOrgnBrCode(), dmy.format(sdf.parse(frDt)), dmy.format(sdf.parse(toDt)), offId, acctId);
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    refresh();
                    this.setMessage(result.substring(4));
                } else {
                    this.setMessage(result);
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void Post() {
        try {
            if (tableList.isEmpty()) {
                this.setMessage("No Data To Post!!!");
                return;
            } else {

                String chqDt = "";
                if (recoveryType.equalsIgnoreCase("T")) {
                    if ((acctNo == null) || (acctNo.equalsIgnoreCase(""))) {
                        this.setMessage("Account No can not be Blank!!!");
                        return;
                    }

                    String acNature = ftsPostRemote.getAccountNature(acctNo);
                    if (!(acNature.equalsIgnoreCase(CbsConstant.GL_AC) || acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                        if ((chqNo == null) || (chqNo.equalsIgnoreCase(""))) {
                            this.setMessage("Cheque No can not be Blank!!!");
                            return;
                        }

                        if (chqueDate == null) {
                            this.setMessage("Cheque Date can not be Blank!!!");
                            return;
                        }

                        chqDt = dmy.format(chqueDate);
                    }
                }

                for (int i = 0; i < tableList.size(); i++) {
                    if (validateAmount(tableList.get(i).getDemandAmt()) != true) {
                        return;
                    }
                }

                String result = txnRemote.recoverDemand(tableList, this.recoveryType, this.acctNo, this.chqNo, chqDt, demandId, this.getOrgnBrCode(), this.getUserName(), dmy.format(date));
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    if (this.recoveryType.equalsIgnoreCase("C")) {
                        refresh();
                        this.setMessage("Demand Posted Successfully");
                    } else {
                        refresh();
                        this.setMessage("Demand Posted Successfully And Posted Batch No is " + result.substring(4));
                    }
                } else {
                    this.setMessage(result);
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
