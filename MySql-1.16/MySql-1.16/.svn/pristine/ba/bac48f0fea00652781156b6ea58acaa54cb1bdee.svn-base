/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.FiuDormantToOperative;
import com.cbs.pojo.JanDhanAcnoInfoPojo;
import com.cbs.pojo.ctrMoreThan1Crore;
import com.cbs.pojo.jdHavingDepositPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Spellar;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
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
public class JanDhanAccountInfo extends BaseBean {

    private String message;
    private String repType;
    private List<SelectItem> repTypeList;
    private String amtType;
    private List<SelectItem> amtTypeList;
    private String denominationType;
    private List<SelectItem> denominationTypeList;
    private String branch;
    private List<SelectItem> branchList;
    private String acType;
    private List<SelectItem> acTypeList;
    private String depositTranType;
    private List<SelectItem> depositTranTypeList;
    private String withdrawTranType;
    private List<SelectItem> withdrawTranTypeList;
    private String statusType;
    private List<SelectItem> statusTypeList;
    private double amount;
    private BigDecimal tillAmount;
    private BigDecimal amount1;
    private BigDecimal amount2;
    private String amtVisible;
    private String amtVisibleTill;
    private Boolean disableTranType;
    private String frDt;
    private String toDt;
    private Boolean disableStatus;
    private Boolean disableAmtType;
    private Date date = new Date();
    private TdReceiptManagementFacadeRemote RemoteCode;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dd_mm_yyyy = new SimpleDateFormat("dd-MM-yyyy");
    List<JanDhanAcnoInfoPojo> janDhanInfoList = new ArrayList<JanDhanAcnoInfoPojo>();
    List<jdHavingDepositPojo> janDhanHavingList = new ArrayList<jdHavingDepositPojo>();
    List<ctrMoreThan1Crore> ctrMoreThan1CroreList = new ArrayList<ctrMoreThan1Crore>();
    List<FiuDormantToOperative> dormantToOperativeList = new ArrayList<FiuDormantToOperative>();

    public JanDhanAccountInfo() {
        try {
            setFrDt("08/11/2016");
            setToDt("30/12/2016");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            repTypeList = new ArrayList<SelectItem>();
            depositTranTypeList = new ArrayList<SelectItem>();
            withdrawTranTypeList = new ArrayList<SelectItem>();
            amtTypeList = new ArrayList<SelectItem>();
            denominationTypeList = new ArrayList<SelectItem>();
            statusTypeList = new ArrayList<SelectItem>();

            repTypeList.add(new SelectItem("S", "--Select--"));
            repTypeList.add(new SelectItem("1", "Details of JanDhan accounts"));
            repTypeList.add(new SelectItem("2", "JD having Deposit <1000"));
            repTypeList.add(new SelectItem("4", "DormantToOperative"));
            repTypeList.add(new SelectItem("3", "CTR >10000000"));
            repTypeList.add(new SelectItem("5", "CTR >=8000000"));
            repTypeList.add(new SelectItem("6", "CTR >=500000"));
            repTypeList.add(new SelectItem("7", "CTR between 200000 to 8000000"));
            repTypeList.add(new SelectItem("8", "Demand Drafts/Bankers'Cheque >=45000"));
            repTypeList.add(new SelectItem("9", "Specified Bank Notes is Rs.5000 or more"));
            repTypeList.add(new SelectItem("10", "Demand Drafts/Bankers cheques for Rs. 5.00 lakhs or more"));
            repTypeList.add(new SelectItem("11", "REPORT-33A:Loan Accounts(Cumulative Deposits Exceeds Rs.25Lakh)"));
            repTypeList.add(new SelectItem("12", "REPORT-34A:Reactivated Dormant Accounts(Cumulative Deposits Exceed Rs.40K)"));

            depositTranTypeList.add(new SelectItem("0,1,2", "ALL"));
            depositTranTypeList.add(new SelectItem("0", "Cash"));
            depositTranTypeList.add(new SelectItem("1", "Clearing"));
            depositTranTypeList.add(new SelectItem("2", "Transfer"));

            withdrawTranTypeList.add(new SelectItem("0,1,2", "ALL"));
            withdrawTranTypeList.add(new SelectItem("0", "Cash"));
            withdrawTranTypeList.add(new SelectItem("1", "Clearing"));
            withdrawTranTypeList.add(new SelectItem("2", "Transfer"));

            amtTypeList.add(new SelectItem("S", "--Select--"));
            amtTypeList.add(new SelectItem("Between", "Amount Between"));
            amtTypeList.add(new SelectItem("Till", "Till Amount"));

            denominationTypeList.add(new SelectItem("S", "--Select--"));
            denominationTypeList.add(new SelectItem("Y", "With Denomination"));
            denominationTypeList.add(new SelectItem("N", "Without Denomination"));

            statusTypeList.add(new SelectItem("A", "ALL"));
            statusTypeList.add(new SelectItem("ISSUED", "ISSUED"));
            statusTypeList.add(new SelectItem("PAID", "PAID"));
            statusTypeList.add(new SelectItem("CANCELLED", "CANCELLED"));

            branchList = new ArrayList<SelectItem>();
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            setAmtVisibleTill("");
            setAmtVisible("none");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void onReportType() {
        setMessage("");
        setAmount1(new BigDecimal("0.00"));
        setAmount2(new BigDecimal("0.00"));
        setTillAmount(new BigDecimal("0.00"));

        if (repType.equalsIgnoreCase("3")) {
            this.disableTranType = false;
            this.disableAmtType = false;
            //this.amount1 = new BigDecimal("10000000.01");
            this.tillAmount = new BigDecimal("10000000.01");
            setAmtType("Till");
            setDenominationType("S");
//            setAmtVisibleTill("");
            setDepositTranType("0");
            setWithdrawTranType("0,1,2");
            this.disableStatus = true;
        } else if (repType.equalsIgnoreCase("5")) {
            this.disableTranType = false;
            this.disableAmtType = false;
            setAmtType("Till");
            setDenominationType("S");
//            setAmtVisibleTill("");
            //this.amount1 = new BigDecimal("8000000");
            this.tillAmount = new BigDecimal("8000000");
            setDepositTranType("0");
            setWithdrawTranType("0");
            this.disableStatus = true;
        } else if (repType.equalsIgnoreCase("7")) {
            this.disableTranType = false;
            this.disableAmtType = false;
            setAmtType("Between");
            setDenominationType("S");
//            setAmtVisibleTill("");
            this.amount1 = new BigDecimal("200000");
            this.amount2 = new BigDecimal("8000000");
            setDepositTranType("0");
            setWithdrawTranType("0");
            this.disableStatus = true;
        } else if (repType.equalsIgnoreCase("9")) {
            this.disableTranType = false;
            this.disableAmtType = false;
//            setAmtVisibleTill("");
            this.tillAmount = new BigDecimal("5000");
            setAmtType("Till");
            setDenominationType("Y");
            setDepositTranType("0");
            setWithdrawTranType("0");
            this.disableStatus = true;
        } else if (repType.equalsIgnoreCase("6")) {
            this.disableTranType = false;
            this.disableAmtType = false;
            // this.amount1 = new BigDecimal("500000");
            this.tillAmount = new BigDecimal("500000");
            setAmtType("Till");
            setDenominationType("S");
//            setAmtVisibleTill("");
            setDepositTranType("0");
            setWithdrawTranType("0");
            this.disableStatus = true;
        } else if (repType.equalsIgnoreCase("2")) {
            //this.amount1 = new BigDecimal("1000");
            this.tillAmount = new BigDecimal("1000");
            this.disableTranType = true;
            this.disableAmtType = true;
            setAmtType("Till");
            setDenominationType("S");
            setAmtVisibleTill("");
            this.disableStatus = true;
        } else if (repType.equalsIgnoreCase("8")) {
            //this.amount1 = new BigDecimal("45000");
            this.tillAmount = new BigDecimal("45000");
            this.disableTranType = true;
            this.disableAmtType = true;
            setAmtType("Till");
            setDenominationType("S");
            setAmtVisibleTill("");
            setAmtVisible("none");
            this.disableStatus = true;
        } else if (repType.equalsIgnoreCase("10")) {
            this.tillAmount = new BigDecimal("500000");
            this.disableTranType = false;
            this.disableAmtType = true;
            this.disableStatus = false;
            setDepositTranType("0,1,2");
            setWithdrawTranType("0,1,2");
            setAmtType("Till");
            setDenominationType("S");
            setAmtVisibleTill("");
            this.setDisableStatus(false);
            setAmtVisible("none");
        } else if (repType.equalsIgnoreCase("11")) {
            setDepositTranType("0,1,2");
            setWithdrawTranType("0,1,2");
            this.tillAmount = new BigDecimal("2500000");
            this.disableTranType = true;
            this.disableAmtType = true;
            this.disableStatus = true;            
            setAmtType("Till");
            setDenominationType("S");
            setAmtVisibleTill("");
            this.setDisableStatus(true);
            setAmtVisible("none");
        } else if (repType.equalsIgnoreCase("12")) {
            setDepositTranType("0,1,2");
            setWithdrawTranType("0,1,2");
            this.tillAmount = new BigDecimal("40000");
            this.disableTranType = true;
            this.disableAmtType = true;
            this.disableStatus = true;            
            setAmtType("Till");
            setDenominationType("S");
            setAmtVisibleTill("");
            this.setDisableStatus(true);
            setAmtVisible("none");
        } else {
            this.disableTranType = true;
            this.disableAmtType = true;
            this.disableStatus = true;
            setAmtVisibleTill("none");
            setAmtVisible("none");
        }
        if (repType.equalsIgnoreCase("9")) {
            setFrDt("19/12/2016");
        } else {
            setFrDt("08/11/2016");
        }
        onAmtType();
    }

    public void onAmtType() {
        setMessage("");
        if (amtType.equalsIgnoreCase("Till")) {
            setAmtVisibleTill("");
            setAmtVisible("none");
            if (repType.equalsIgnoreCase("3")) {
                this.amount1 = new BigDecimal("10000000.01");
            } else if (repType.equalsIgnoreCase("5")) {
                this.amount1 = new BigDecimal("8000000");
            } else if (repType.equalsIgnoreCase("6")) {
                this.amount1 = new BigDecimal("500000");
            } else if (repType.equalsIgnoreCase("2")) {
                this.amount1 = new BigDecimal("1000");
            } else if (repType.equalsIgnoreCase("9")) {
                this.amount1 = new BigDecimal("5000");
            } else if (repType.equalsIgnoreCase("10")) {
                this.amount1 = new BigDecimal("500000");
            } else if (repType.equalsIgnoreCase("11")) {
                this.amount1 = new BigDecimal("2500000");
            } else if (repType.equalsIgnoreCase("12")) {
                this.amount1 = new BigDecimal("40000");
            }
        } else {
            setAmtVisibleTill("none");
            setAmtVisible("");
            if (repType.equalsIgnoreCase("7")) {
                this.amount1 = new BigDecimal("200000");
                this.amount2 = new BigDecimal("8000000");
            } else {
                this.amount1 = new BigDecimal("0.0");
                this.amount2 = new BigDecimal("0.0");
            }
        }
    }

    public void printAction() {
        setMessage("");
        try {

            BigDecimal amt = new BigDecimal(amount);
            if (repType == null || repType.equalsIgnoreCase("S")) {
                setMessage("Please Select Report Type !");
                return;
            }

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(frDt)) {
                setMessage("Please fill proper from date ! ");
                return;
            }

            if (dmy.parse(frDt).after(getDate())) {
                setMessage("From date should be less than current date !");
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(toDt)) {
                setMessage("Please fill proper to date ! ");
                return;
            }

            if (dmy.parse(toDt).after(getDate())) {
                setMessage("To date should be less than current date !");
                return;
            }

            if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less than current To date !");
                return;
            }

            if (repType.equalsIgnoreCase("9") && denominationType.equalsIgnoreCase("S")) {
                setMessage("Please choose the Denomination Type value first");
                return;
            }
//            if (repType.equalsIgnoreCase("2") || repType.equalsIgnoreCase("4")) {
//                String dt = "08/11/2016";
//                if (dmy.parse(toDt).before(dmy.parse(dt)) || dmy.parse(toDt).equals(dmy.parse(dt))) {
//                    setMessage("As On date should be greater than 08/11/2016 !");
//                    return;
//                }
//            }

            if (repType.equalsIgnoreCase("3") || repType.equalsIgnoreCase("5") || repType.equalsIgnoreCase("6") || repType.equalsIgnoreCase("7") || repType.equalsIgnoreCase("9") || repType.equalsIgnoreCase("11")) {
                List acNatureList = null;
                String acType = "";
//                Spellar inWord = new Spellar();
//                this.amount = amount1.doubleValue();
//                inWord.spellAmount(amount).toUpperCase();
                if (!amtType.equalsIgnoreCase("Between")) {
                    if (!repType.equalsIgnoreCase("7")) {
                        this.amount1 = tillAmount;
                    }
                }


                if (repType.equalsIgnoreCase("11")) {
                    acNatureList = common.getAcctNatureOnlyDB();
                } else {
                acNatureList = common.getCASBAcNatureList();
                }
                for (int i = 0; i < acNatureList.size(); i++) {
                    Vector acnatureVect = (Vector) acNatureList.get(i);
                    List acTypeList ;
                    if (repType.equalsIgnoreCase("11")) {
                        acTypeList = common.getAccTypeOnlyCrDbFlag(acnatureVect.get(0).toString());
                    } else {
                        acTypeList = common.getAccType(acnatureVect.get(0).toString());
                    }
                    if (acTypeList.size() > 0) {
                        for (int j = 0; j < acTypeList.size(); j++) {
                            Vector actypevect = (Vector) acTypeList.get(j);
                            if (i == 0 && j == 0) {
                                acType = "'" + actypevect.get(0).toString() + "'";
                            } else {
                                acType = acType.concat(",'" + actypevect.get(0).toString() + "'");
                            }
                        }
                    }
                }

                //System.out.println("AcTypeList:" + acType);
                String brncode = "";
                if (branch.equalsIgnoreCase("0A")) {
                    List dataList = common.getAlphacodeExcludingHo();
                    if (!dataList.isEmpty()) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Vector element = (Vector) dataList.get(i);
                            if (i == 0) {
                                brncode = element.get(0).toString().length() == 1 ? "'" + "0" + element.get(0).toString() + "'" : "'" + element.get(0).toString() + "'";
                            } else {
                                brncode = brncode.concat(element.get(0).toString().length() == 1 ? ",'" + "0" + element.get(0).toString() + "'" : ",'" + element.get(0).toString() + "'");
                            }
                        }
                    }
                } else {
                    brncode = "'" + branch + "'";
                }
                if (repType.equalsIgnoreCase("11")) {
                    ctrMoreThan1CroreList = remoteFacade.loanDeposit25LakhsOrMoreReport(acNatureList, "", depositTranType, withdrawTranType, this.amount1, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), brncode, repType, getUserName(), amount2, amtType, denominationType);
                    FastReportBuilder ctrMoreThan1CroreReprot = loanDeposit25LakhsOrMoreReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(ctrMoreThan1CroreList), ctrMoreThan1CroreReprot, common.getBankName()+"_Loan_deposit_Report33A_" + this.amount1 + "_Date_" + ymd.format(dmy.parse(frDt)) + "_" + ymd.format(dmy.parse(toDt)));
                } else {
                    ctrMoreThan1CroreList = remoteFacade.ctrMoreThan1CroreReport(acNatureList, "", depositTranType, withdrawTranType, this.amount1, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), brncode, repType, getUserName(), amount2, amtType, denominationType);
                    FastReportBuilder ctrMoreThan1CroreReprot = genrateCtrMoreThan1CroreReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(ctrMoreThan1CroreList), ctrMoreThan1CroreReprot, "CTR_MORE_THAN_ " + this.amount1 + " _" + ymd.format(dmy.parse(toDt)));
                }
            } else if (repType.equalsIgnoreCase("2")) {
                this.amount1 = tillAmount;
                janDhanHavingList = remoteFacade.getJdHavingData(repType, branch, acType, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), this.amount1);
                FastReportBuilder janDhanMore1000Reprot = genrateJanDhanHavingDeposit1000Report();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(janDhanHavingList), janDhanMore1000Reprot, "JD HAVING DEPOSIT LESS THAN _ " + this.amount1 + " _" + ymd.format(dmy.parse(toDt)));
                if (janDhanHavingList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
            } else if (repType.equalsIgnoreCase("1")) {
                janDhanInfoList = remoteFacade.getJanDhanAcnoInfoData(repType, branch, acType, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)));
                FastReportBuilder janDhanInfoReprot = genrateJanDhanAcnoDetailReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(janDhanInfoList), janDhanInfoReprot, "DETAILS OF JANDHAN ACCOUNTS_" + ymd.format(dmy.parse(toDt)));
                if (janDhanInfoList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
            } else if (repType.equalsIgnoreCase("4")) {
                String brncode = "";
                if (branch.equalsIgnoreCase("0A")) {
                    List dataList = common.getAlphacodeExcludingHo();
                    if (!dataList.isEmpty()) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Vector element = (Vector) dataList.get(i);
                            if (i == 0) {
                                brncode = element.get(0).toString().length() == 1 ? "'" + "0" + element.get(0).toString() + "'" : "'" + element.get(0).toString() + "'";
                            } else {
                                brncode = brncode.concat(element.get(0).toString().length() == 1 ? ",'" + "0" + element.get(0).toString() + "'" : ",'" + element.get(0).toString() + "'");
                            }
                        }
                    }
                } else {
                    brncode = "'" + branch + "'";
                }
                dormantToOperativeList = remoteFacade.dormantToOperativeReport(ymd.format(dmy.parse(toDt)), brncode);
                if (dormantToOperativeList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                FastReportBuilder FiuDormantToOperativeReprot = genrateFiuDormantToOperativeReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dormantToOperativeList), FiuDormantToOperativeReprot, "DORMANT TO OPERATIVE_" + ymd.format(dmy.parse(toDt)));

            } else if (repType.equalsIgnoreCase("8")) {
                this.amount1 = tillAmount;
                String brncode = "";
                if (branch.equalsIgnoreCase("0A")) {
                    List dataList = common.getAlphacodeExcludingHo();
                    if (!dataList.isEmpty()) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Vector element = (Vector) dataList.get(i);
                            if (i == 0) {
                                brncode = element.get(0).toString().length() == 1 ? "'" + "0" + element.get(0).toString() + "'" : "'" + element.get(0).toString() + "'";
                            } else {
                                brncode = brncode.concat(element.get(0).toString().length() == 1 ? ",'" + "0" + element.get(0).toString() + "'" : ",'" + element.get(0).toString() + "'");
                            }
                        }
                    }
                } else {
                    brncode = "'" + branch + "'";
                }

                ctrMoreThan1CroreList = remoteFacade.getDemandBankersChecqueData(repType, brncode, "", ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), amount1);
                if (ctrMoreThan1CroreList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }

                FastReportBuilder cancelledDdPo = genrateCancelPoDoReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(ctrMoreThan1CroreList), cancelledDdPo, "Cancel Demand Drafts/Bankers'Cheque >=45000");
            } else if (repType.equalsIgnoreCase("10")) {
                this.amount1 = tillAmount;
                ctrMoreThan1CroreList = remoteFacade.getDemandBankersChecqueAllStatusData(repType, branch, statusType, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), amount1, depositTranType, withdrawTranType);
                if (ctrMoreThan1CroreList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }

                FastReportBuilder generateDdPo = genratePoDoReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(ctrMoreThan1CroreList), generateDdPo, common.getBankName() + "_DDREPORT_32");
            } else if (repType.equalsIgnoreCase("12")) {
                this.amount1 = tillAmount;
                String brncode = "";
                if (branch.equalsIgnoreCase("0A")) {
                    List dataList = common.getAlphacodeExcludingHo();
                    if (!dataList.isEmpty()) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Vector element = (Vector) dataList.get(i);
                            if (i == 0) {
                                brncode = element.get(0).toString().length() == 1 ? "'" + "0" + element.get(0).toString() + "'" : "'" + element.get(0).toString() + "'";
                            } else {
                                brncode = brncode.concat(element.get(0).toString().length() == 1 ? ",'" + "0" + element.get(0).toString() + "'" : ",'" + element.get(0).toString() + "'");
                            }
                        }
                    }
                } else {
                    brncode = "'" + branch + "'";
                }

                ctrMoreThan1CroreList = remoteFacade.getReactivatedDormantAccountData(repType, brncode, statusType, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), amount1, depositTranType, withdrawTranType);
                if (ctrMoreThan1CroreList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }

                FastReportBuilder generateReactivatedDormant = genrateReactivatedDormantReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(ctrMoreThan1CroreList), generateReactivatedDormant,common.getBankName()+"_ReactivatedDormant_Report34A");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public FastReportBuilder genrateJanDhanAcnoDetailReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S. No.", 60);
            AbstractColumn reportingEntityName = ReportBean.getColumn("reportingEntityName", String.class, "Reporting Entity Name", 120);
            AbstractColumn fIuReid = ReportBean.getColumn("fIuReid", String.class, "FIUREID", 60);
            AbstractColumn noOfAcnoOnPrevDay = ReportBean.getColumn("noOfAcnoOnPrevDay", String.class, "No. of Jan Dhan accounts as on previous day", 120);
            AbstractColumn totalCashOnPrevDays = ReportBean.getColumn("totalCashOnPrevDays", BigDecimal.class, "Total Cash Credit  of Jan Dhan accounts as on previous day ( Figures in Rs)", 180);
            AbstractColumn depositDuringdDay = ReportBean.getColumn("depositDuringdDay", BigDecimal.class, "Out of column (4) no. of Jan Dhan Accounts receiving cash  Deposits During the day", 120);
            AbstractColumn totalAmountDuringdDay = ReportBean.getColumn("totalAmountDuringdDay", BigDecimal.class, "Total Amount  of Cash deposited  in  Jan Dhan Accounts (in column 4) During the day ( Figures in Rs)", 120);
            AbstractColumn totalCashOnEndOfDay = ReportBean.getColumn("totalCashOnEndOfDay", BigDecimal.class, "Total Cash Credit  of Jan Dhan accounts as on end of day ( Figures in Rs)", 120);
            AbstractColumn kycUpdateDuringday = ReportBean.getColumn("kycUpdateDuringday", String.class, "No. of Jandhan accounts converted to full KYC account during the day", 60);
            AbstractColumn kycUpdateTillday = ReportBean.getColumn("kycUpdateTillday", String.class, "No. of Jandhan accounts converted to full KYC account till date", 120);

            fastReport.addColumn(sNo);
            width = width + sNo.getWidth();

            fastReport.addColumn(reportingEntityName);
            width = width + reportingEntityName.getWidth();

            fastReport.addColumn(fIuReid);
            width = width + fIuReid.getWidth();

            fastReport.addColumn(noOfAcnoOnPrevDay);
            width = width + noOfAcnoOnPrevDay.getWidth();

            fastReport.addColumn(totalCashOnPrevDays);
            totalCashOnPrevDays.setStyle(style);
            width = width + totalCashOnPrevDays.getWidth();

            fastReport.addColumn(depositDuringdDay);
            depositDuringdDay.setStyle(style);
            width = width + depositDuringdDay.getWidth();

            fastReport.addColumn(totalAmountDuringdDay);
            totalAmountDuringdDay.setStyle(style);
            width = width + totalAmountDuringdDay.getWidth();

            fastReport.addColumn(totalCashOnEndOfDay);
            totalCashOnEndOfDay.setStyle(style);
            width = width + totalCashOnEndOfDay.getWidth();

            fastReport.addColumn(kycUpdateDuringday);
            kycUpdateDuringday.setStyle(style);
            width = width + kycUpdateDuringday.getWidth();

            fastReport.addColumn(kycUpdateTillday);
            kycUpdateTillday.setStyle(style);
            width = width + kycUpdateTillday.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Details of Cumulative Deposits Jan Dhan Accounts");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateJanDhanHavingDeposit1000Report() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            String titleHeader = "";
            Spellar inWord = new Spellar();
            this.amount = amount1.doubleValue();
            inWord.spellAmount(amount).toUpperCase();

            String dthfr = ymd.format(dmy.parse(frDt));
            String headerFoDt = dthfr.substring(6, 8) + "-" + dthfr.substring(4, 6) + "-" + dthfr.substring(0, 4);
            titleHeader = headerFoDt + ", Rs." + amount + "/- (" + inWord.spellAmount(amount).toUpperCase() + ")";


            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S. No.", 60);
            AbstractColumn reportingEntityName = ReportBean.getColumn("reportingEntityName", String.class, "Reporting Entity Name", 120);
            AbstractColumn fIuReid = ReportBean.getColumn("fIuReid", String.class, "FIUREID", 60);
            AbstractColumn noOfAcnoLessThan1000 = ReportBean.getColumn("noOfAcnoLessThan1000", String.class, "Number of jandhan accounts which  were having deposits less than Rs. 1000 as on " + headerFoDt + "  and are now receiving cash deposits during the day", 180);
            AbstractColumn totalAmtCashDuringDays = ReportBean.getColumn("totalAmtCashDuringDays", BigDecimal.class, "Total Amount of Cash Deposited during the day in accounts reported in column 4 ( Figures in Rupees) ", 120);
            AbstractColumn withdrawDuringDays = ReportBean.getColumn("withdrawDuringDays", BigDecimal.class, "Total Amount of Cash withdrawn during the day from the accounts reported in column 4 ( Figures in Rupees)", 120);
            AbstractColumn cumulativeAmtDeposit = ReportBean.getColumn("cumulativeAmtDeposit", BigDecimal.class, "Cumulative Amount of Cash Deposited since " + headerFoDt + " in accounts reported in column 4 ( Figures in Rupees) ", 120);
            AbstractColumn cumulativeWithdraw = ReportBean.getColumn("cumulativeWithdraw", BigDecimal.class, "Cumulative Amount of Cash withdrawn since " + headerFoDt + " from  accounts reported in column 4 ( Figures in Rupees) ", 120);


            fastReport.addColumn(sNo);
            width = width + sNo.getWidth();

            fastReport.addColumn(reportingEntityName);
            width = width + reportingEntityName.getWidth();

            fastReport.addColumn(fIuReid);
            width = width + fIuReid.getWidth();

            fastReport.addColumn(noOfAcnoLessThan1000);
            width = width + noOfAcnoLessThan1000.getWidth();

            fastReport.addColumn(totalAmtCashDuringDays);
            totalAmtCashDuringDays.setStyle(style);
            width = width + totalAmtCashDuringDays.getWidth();

            fastReport.addColumn(withdrawDuringDays);
            withdrawDuringDays.setStyle(style);
            width = width + withdrawDuringDays.getWidth();

            fastReport.addColumn(cumulativeAmtDeposit);
            cumulativeAmtDeposit.setStyle(style);
            width = width + cumulativeAmtDeposit.getWidth();

            fastReport.addColumn(cumulativeWithdraw);
            cumulativeWithdraw.setStyle(style);
            width = width + cumulativeWithdraw.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Details of  Number of jandhan accounts which  were having deposits less than " + titleHeader + " and are now receiving cash deposits during the day");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateCtrMoreThan1CroreReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            String header1 = "", header2 = "", titleHeader = "";
            Spellar inWord = new Spellar();
            this.amount = amount1.doubleValue();
            String dthfr = ymd.format(dmy.parse(frDt));
            String headerFoDt = dthfr.substring(6, 8) + "-" + dthfr.substring(4, 6) + "-" + dthfr.substring(0, 4);
            titleHeader = headerFoDt + ", Rs." + amount1 + "/- (" + inWord.spellAmount(amount1.doubleValue()).toUpperCase() + ") and above";

            String dth = ymd.format(dmy.parse(toDt));
            String headerToDt = dth.substring(6, 8) + "-" + dth.substring(4, 6) + "-" + dth.substring(0, 4);
            if (repType.equalsIgnoreCase("3")) {
                header1 = "Amount of CASH Deposited during the day";
                header2 = "Amount withdrawn during the day by any means (Cheque, transfer, cash etc.)";
            }
            if (repType.equalsIgnoreCase("5")) {
                header1 = " Cumulative Amount of Cash Deposited since " + dd_mm_yyyy.format(dmy.parse(frDt)) + " (Rs. " + amount1 + " and above) ";
                header2 = " Cumulative Amount of Cash withdrawn since " + dd_mm_yyyy.format(dmy.parse(frDt)) + " (Rs. " + amount1 + " and above)";
            }
            if (repType.equalsIgnoreCase("6")) {
                header1 = " Cumulative Amount of Cash Deposited from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + headerToDt;
                header2 = " Cumulative Amount of Cash withdrawn from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + headerToDt;
            }
            if (repType.equalsIgnoreCase("7")) {
                header1 = " Cumulative Amount of Cash Deposited (" + amount1 + " to " + amount2 + ") during " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt));
                header2 = " Cumulative Amount of Cash Withdrawn  (" + amount1 + " to " + amount2 + ") during " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt));
            }
            if (repType.equalsIgnoreCase("9")) {
                header1 = " Amont deposited in the account from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " onwards  in Specified Bank Notes  (INR 500 & 1000), only if deposition is Rs." + amount1 + "/- or more AND  such depostion is more than once from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt));
//                header2 = " Cumulative Amount of Cash Withdrawn  (" + amount1 + " to " + amount2 + ") during " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt));
            }

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            AbstractColumn accountType = null;
            AbstractColumn identificationDocumentType = null;
            AbstractColumn identificationDocumentNumber = null;
            AbstractColumn dateOfTransaction = null;
            AbstractColumn acOpenDuringDt = null;
            AbstractColumn balanceInAcno = null;

            AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S. No.", 60);
            AbstractColumn reportingEntityName = ReportBean.getColumn("reportingEntityName", String.class, "Reporting Entity Name*", 120);
            AbstractColumn fiureid = ReportBean.getColumn("fiureid", String.class, "FIUREID*", 60);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "Account Number*", 120);
            if (repType.equalsIgnoreCase("3") || repType.equalsIgnoreCase("6")) {
                accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 180);
            }
            AbstractColumn brachName = ReportBean.getColumn("brachName", String.class, "Brach Name", 120);
            AbstractColumn branchCity = ReportBean.getColumn("branchCity", String.class, "Branch City with Pincode ", 120);
            AbstractColumn accountHolderName = ReportBean.getColumn("accountHolderName", String.class, "Account Holder Name*", 120);
            AbstractColumn accountHoldersDateofBirth = ReportBean.getColumn("accountHoldersDateofBirth", String.class, "Account Holders Date of Birth", 120);
            if (repType.equalsIgnoreCase("3") || repType.equalsIgnoreCase("6")) {
                identificationDocumentType = ReportBean.getColumn("identificationDocumentType", String.class, "Identification Document Type", 120);
                identificationDocumentNumber = ReportBean.getColumn("identificationDocumentNumber", String.class, "Identification Document Number", 120);
            }

            AbstractColumn accountHoldersPAN = ReportBean.getColumn("accountHoldersPAN", String.class, "Account Holders PAN", 120);
            AbstractColumn accountHoldersAddress = ReportBean.getColumn("accountHoldersAddress", String.class, "Account Holders Address with Pincode", 120);
            AbstractColumn accountHoldersCity = ReportBean.getColumn("accountHoldersCity", String.class, "Account Holders  City", 120);
            AbstractColumn accountHoldersMobile = ReportBean.getColumn("accountHoldersMobile", String.class, "Account HoldersMobile", 120);
            if (repType.equalsIgnoreCase("5") || repType.equalsIgnoreCase("7") || repType.equalsIgnoreCase("9")) {
                balanceInAcno = ReportBean.getColumn("balanceInAcno", BigDecimal.class, "Balance in account as on 07.11.2016", 120);
            }

            AbstractColumn cumulativeAmountOfCashDeposited = ReportBean.getColumn("cumulativeAmountOfCashDeposited", BigDecimal.class, header1, 120);
            AbstractColumn cumulativeAmountOfCashWithdrawn = ReportBean.getColumn("cumulativeAmountOfCashWithdrawn", BigDecimal.class, header2, 120);
            if (repType.equalsIgnoreCase("3")) {
                dateOfTransaction = ReportBean.getColumn("dateOfTransaction", String.class, "Date of transaction (DD/MM/YYYY)", 120);
            }
            if (repType.equalsIgnoreCase("7")) {
                acOpenDuringDt = ReportBean.getColumn("acOpenDuringDt", String.class, "Report 'Y' for accounts opened on or after " + dd_mm_yyyy.format(dmy.parse(frDt)) + ". For old accounts report 'N'", 120);
            } else if (repType.equalsIgnoreCase("9")) {
                acOpenDuringDt = ReportBean.getColumn("acOpenDuringDt", String.class, "Indicate whether the A/c is KYC compliant or not. Reply  (Y)Yes or (N)No.", 120);
            }

            fastReport.addColumn(sNo);
            width = width + sNo.getWidth();

            fastReport.addColumn(reportingEntityName);
            width = width + reportingEntityName.getWidth();

            fastReport.addColumn(fiureid);
            width = width + fiureid.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();
            if (repType.equalsIgnoreCase("3") || repType.equalsIgnoreCase("6")) {
                fastReport.addColumn(accountType);
                width = width + accountType.getWidth();
            }

            fastReport.addColumn(brachName);
            width = width + brachName.getWidth();

            fastReport.addColumn(branchCity);
            width = width + branchCity.getWidth();

            fastReport.addColumn(accountHolderName);
            width = width + accountHolderName.getWidth();

            if (!repType.equalsIgnoreCase("9")) {
                fastReport.addColumn(accountHoldersDateofBirth);
                width = width + accountHoldersDateofBirth.getWidth();
            }
            if (repType.equalsIgnoreCase("3") || repType.equalsIgnoreCase("6")) {
                fastReport.addColumn(identificationDocumentType);
                width = width + identificationDocumentType.getWidth();

                fastReport.addColumn(identificationDocumentNumber);
                width = width + identificationDocumentNumber.getWidth();

            }

            fastReport.addColumn(accountHoldersPAN);
            width = width + accountHoldersPAN.getWidth();

            fastReport.addColumn(accountHoldersAddress);
            width = width + accountHoldersAddress.getWidth();

            fastReport.addColumn(accountHoldersCity);
            width = width + accountHoldersCity.getWidth();

            fastReport.addColumn(accountHoldersMobile);
            width = width + accountHoldersMobile.getWidth();

            if (repType.equalsIgnoreCase("5") || repType.equalsIgnoreCase("7") || repType.equalsIgnoreCase("9")) {
                fastReport.addColumn(balanceInAcno);
                balanceInAcno.setStyle(style);
                width = width + balanceInAcno.getWidth();
            }

            fastReport.addColumn(cumulativeAmountOfCashDeposited);
            cumulativeAmountOfCashDeposited.setStyle(style);
            width = width + cumulativeAmountOfCashDeposited.getWidth();

            if (!repType.equalsIgnoreCase("9")) {
                fastReport.addColumn(cumulativeAmountOfCashWithdrawn);
                cumulativeAmountOfCashWithdrawn.setStyle(style);
                width = width + cumulativeAmountOfCashWithdrawn.getWidth();
            }

            if (repType.equalsIgnoreCase("3")) {
                fastReport.addColumn(dateOfTransaction);
                width = width + dateOfTransaction.getWidth();
            }

            if (repType.equalsIgnoreCase("7") || repType.equalsIgnoreCase("9")) {
                fastReport.addColumn(acOpenDuringDt);
                width = width + acOpenDuringDt.getWidth();
            }

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            if (repType.equalsIgnoreCase("3")) {
                fastReport.setTitle("Details of accounts ( Any Type) in cash deposited/withdrawn since " + titleHeader + "  (multiple or single transactions)");
            } else if (repType.equalsIgnoreCase("5")) {
                fastReport.setTitle("Details of   accounts ( Any Type) in which cash deposited since " + titleHeader + " ( multiple or single transactions)");
            } else if (repType.equalsIgnoreCase("6")) {
                fastReport.setTitle("Details of   A/Cs ( Any Type) in which cash deposited from " + titleHeader + " ( multiple or single transactions)");
            } else if (repType.equalsIgnoreCase("7")) {
                fastReport.setTitle("Report-28: Details of accounts (Any Type) in which cumulative cash Deposited/Withdrawn during " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + ", between Rs." + amount1 + "/- to Rs." + amount2 + "/- (Rs. " + inWord.spellAmount(amount1.doubleValue()).toUpperCase() + " to Rs. " + inWord.spellAmount(amount2.doubleValue()).toUpperCase() + ") in Single and/or Multiple Transactions ");
            } else if (repType.equalsIgnoreCase("9")) {
                fastReport.setTitle("Report-31: Daily Report of  accounts ( Any Type) in which amount  deposited  from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + "  in Specified Bank  Notes (INR 500 & 1000) is Rs." + amount1 + " or more and such deposition is more than once during this period.".concat(denominationType.equalsIgnoreCase("N") ? " (Without Denomination)" : ""));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateReactivatedDormantReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);


            AbstractColumn reportingEntityName = ReportBean.getColumn("reportingEntityName", String.class, "Reporting Entity Name", 120);
            AbstractColumn fiureid = ReportBean.getColumn("fiureid", String.class, "FIUREID*", 60);
            AbstractColumn brachDistrict = ReportBean.getColumn("brachDistrict", String.class, "DISTRICT/METRO Name", 120);
            AbstractColumn brachState = ReportBean.getColumn("brachState", String.class, "State", 120);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "Account Number", 120);
            AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 180);
            AbstractColumn brachName = ReportBean.getColumn("brachName", String.class, "Brach Name", 120);
            AbstractColumn branchCity = ReportBean.getColumn("branchCity", String.class, "Branch City", 120);
            AbstractColumn brachPinCode = ReportBean.getColumn("brachPinCode", String.class, "Branch PIN Code", 120);
            AbstractColumn accountHolderName = ReportBean.getColumn("accountHolderName", String.class, "Account Holder Name", 120);
            AbstractColumn identificationDocumentType = ReportBean.getColumn("identificationDocumentType", String.class, "Identification Document Type", 120);
            AbstractColumn identificationDocumentNumber = ReportBean.getColumn("identificationDocumentNumber", String.class, "Identification Document Number", 120);
            AbstractColumn accountHoldersPAN = ReportBean.getColumn("accountHoldersPAN", String.class, "PAN of Account holder", 120);
            AbstractColumn accountHoldersAddress = ReportBean.getColumn("accountHoldersAddress", String.class, "Address of Account Holder", 120);
            AbstractColumn accountHoldersCity = ReportBean.getColumn("accountHoldersCity", String.class, "Account Holders  City", 120);
            AbstractColumn balanceInAcno = ReportBean.getColumn("balanceInAcno", BigDecimal.class, "Cash deposited from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + " ", 120);
            AbstractColumn cumulativeAmountOfCashDeposited = ReportBean.getColumn("cumulativeAmountOfCashDeposited", BigDecimal.class, " Non-cash  deposited by other means (i.e Transfer, Clearing, RTGS, NEFT, IMPS etc) from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + "", 120);
            AbstractColumn totalValueddPo = ReportBean.getColumn("totalValueddPo", BigDecimal.class, "Cumulative Amount of ALL Deposits fom " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + " (Column 16 + 17)", 120);
            AbstractColumn cumulativeAmountOfCashWithdrawn = ReportBean.getColumn("cumulativeAmountOfCashWithdrawn", BigDecimal.class, " Cumulative Amount of withdrawals fom " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + "", 120);



            fastReport.addColumn(reportingEntityName);
            width = width + reportingEntityName.getWidth();

            fastReport.addColumn(fiureid);
            width = width + fiureid.getWidth();

            fastReport.addColumn(brachDistrict);
            width = width + brachDistrict.getWidth();

            fastReport.addColumn(brachState);
            width = width + brachState.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(accountType);
            width = width + accountType.getWidth();


            fastReport.addColumn(brachName);
            width = width + brachName.getWidth();

            fastReport.addColumn(branchCity);
            width = width + branchCity.getWidth();

            fastReport.addColumn(brachPinCode);
            width = width + brachPinCode.getWidth();

            fastReport.addColumn(accountHolderName);
            width = width + accountHolderName.getWidth();

            fastReport.addColumn(identificationDocumentType);
            width = width + identificationDocumentType.getWidth();

            fastReport.addColumn(identificationDocumentNumber);
            width = width + identificationDocumentNumber.getWidth();

            fastReport.addColumn(accountHoldersPAN);
            width = width + accountHoldersPAN.getWidth();

            fastReport.addColumn(accountHoldersAddress);
            width = width + accountHoldersAddress.getWidth();

            fastReport.addColumn(accountHoldersCity);
            width = width + accountHoldersCity.getWidth();

            fastReport.addColumn(balanceInAcno);
            balanceInAcno.setStyle(style);
            width = width + balanceInAcno.getWidth();

            fastReport.addColumn(cumulativeAmountOfCashDeposited);
            cumulativeAmountOfCashDeposited.setStyle(style);
            width = width + cumulativeAmountOfCashDeposited.getWidth();

            fastReport.addColumn(totalValueddPo);
            totalValueddPo.setStyle(style);
            width = width + totalValueddPo.getWidth();

            fastReport.addColumn(cumulativeAmountOfCashWithdrawn);
            cumulativeAmountOfCashWithdrawn.setStyle(style);
            width = width + cumulativeAmountOfCashWithdrawn.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle("REPORT 34A: Details of Dormant Accounts Reactivated in which cumulative deposits from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + " are greater than Rs."+amount1+"/-  ( multiple or single transactions)" );



        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder loanDeposit25LakhsOrMoreReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            String header1 = "", header2 = "", titleHeader = "";
            Spellar inWord = new Spellar();
            this.amount = amount1.doubleValue();
            String dthfr = ymd.format(dmy.parse(frDt));
            String headerFoDt = dd_mm_yyyy.format(dmy.parse(frDt));
            titleHeader = "Report No. 33A: Details of Loan Accounts in which cumulative deposits from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + " exceeds Rs." + amount1 + "/- (" + inWord.spellAmount(amount1.doubleValue()).toUpperCase() + ")  ( multiple or single transactions)";

            String dth = ymd.format(dmy.parse(toDt));
            String headerToDt = dd_mm_yyyy.format(dmy.parse(toDt));

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn dateOfTransaction = null;
            AbstractColumn acOpenDuringDt = null;

            AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S. No.", 60);
            AbstractColumn reportingEntityName = ReportBean.getColumn("reportingEntityName", String.class, "Reporting Entity Name", 100);
            AbstractColumn fiureid = ReportBean.getColumn("fiureid", String.class, "FIUREID", 100);
            AbstractColumn district = ReportBean.getColumn("brachDistrict", String.class, "DISTRICT/METRO Name", 90);
            AbstractColumn state = ReportBean.getColumn("brachState", String.class, "State", 100);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "Account No.", 120);
            AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 180);
            AbstractColumn brachName = ReportBean.getColumn("brachName", String.class, "Brach Name", 120);
            AbstractColumn branchCity = ReportBean.getColumn("branchCity", String.class, "Branch City", 120);
            AbstractColumn branchPin = ReportBean.getColumn("brachPinCode", String.class, "Branch PIN code", 120);
            AbstractColumn accountHolderName = ReportBean.getColumn("accountHolderName", String.class, "Account Holder Name", 180);
            AbstractColumn identificationDocumentType = ReportBean.getColumn("identificationDocumentType", String.class, "Identification Document type", 120);
            AbstractColumn identificationDocumentNumber = ReportBean.getColumn("identificationDocumentNumber", String.class, "Identification Document No.", 120);
            AbstractColumn accountHoldersPAN = ReportBean.getColumn("accountHoldersPAN", String.class, "PAN of Account holder", 120);
            AbstractColumn accountHoldersAddress = ReportBean.getColumn("accountHoldersAddress", String.class, "Address of Account Holder", 200);
            AbstractColumn accountHoldersCity = ReportBean.getColumn("accountHoldersCity", String.class, "Account Holder's City", 120);
            AbstractColumn cumulativeAmountOfCashDeposited = ReportBean.getColumn("cumulativeAmountOfCashDeposited", BigDecimal.class, "Cash deposited from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)), 120);
            AbstractColumn cumulativeAmountOfCashWithdrawn = ReportBean.getColumn("cumulativeAmountOfCashWithdrawn", BigDecimal.class, "Non-cash  deposited by other means (i.e Transfer, Clearing, RTGS, NEFT, IMPS etc) from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)), 120);
            AbstractColumn balanceInAcno = ReportBean.getColumn("balanceInAcno", BigDecimal.class, "Cumulative Amount of ALL Deposits from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + " (Column 16 + 17)", 120);

            fastReport.addColumn(sNo);
            width = width + sNo.getWidth();

            fastReport.addColumn(reportingEntityName);
            width = width + reportingEntityName.getWidth();

            fastReport.addColumn(fiureid);
            width = width + fiureid.getWidth();

            fastReport.addColumn(district);
            width = width + district.getWidth();

            fastReport.addColumn(state);
            width = width + state.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(accountType);
            width = width + accountType.getWidth();

            fastReport.addColumn(brachName);
            width = width + brachName.getWidth();

            fastReport.addColumn(branchCity);
            width = width + branchCity.getWidth();

            fastReport.addColumn(branchPin);
            width = width + branchPin.getWidth();

            fastReport.addColumn(accountHolderName);
            width = width + accountHolderName.getWidth();

            fastReport.addColumn(identificationDocumentType);
            width = width + identificationDocumentType.getWidth();

            fastReport.addColumn(identificationDocumentNumber);
            width = width + identificationDocumentNumber.getWidth();

            fastReport.addColumn(accountHoldersPAN);
            width = width + accountHoldersPAN.getWidth();

            fastReport.addColumn(accountHoldersAddress);
            width = width + accountHoldersAddress.getWidth();

            fastReport.addColumn(accountHoldersCity);
            width = width + accountHoldersCity.getWidth();

            fastReport.addColumn(cumulativeAmountOfCashDeposited);
            cumulativeAmountOfCashDeposited.setStyle(style);
            width = width + cumulativeAmountOfCashDeposited.getWidth();

            fastReport.addColumn(cumulativeAmountOfCashWithdrawn);
            cumulativeAmountOfCashWithdrawn.setStyle(style);
            width = width + cumulativeAmountOfCashWithdrawn.getWidth();

            fastReport.addColumn(balanceInAcno);
            balanceInAcno.setStyle(style);
            width = width + balanceInAcno.getWidth();

            Page page = new Page(2210, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle(titleHeader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateCancelPoDoReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            //AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S. No.", 60);
            AbstractColumn reportingEntityName = ReportBean.getColumn("reportingEntityName", String.class, "Reporting Entity Name", 120);
            AbstractColumn fiureid = ReportBean.getColumn("fiureid", String.class, "FIUREID", 120);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "Account Number", 120);
            AbstractColumn brachName = ReportBean.getColumn("brachName", String.class, "Brach Name", 120);
            AbstractColumn brachAdd = ReportBean.getColumn("brachAdd", String.class, "Branch Address", 200);
            AbstractColumn branchCity = ReportBean.getColumn("branchCity", String.class, "Branch City", 120);
            AbstractColumn brachPinCode = ReportBean.getColumn("brachPinCode", String.class, "Branch City Pincode", 120);
            AbstractColumn accountHolderName = ReportBean.getColumn("accountHolderName", String.class, "Account Holder Name", 120);
            AbstractColumn identificationDocumentType = ReportBean.getColumn("identificationDocumentType", String.class, "Identification Document Type", 120);
            AbstractColumn identificationDocumentNumber = ReportBean.getColumn("identificationDocumentNumber", String.class, "Identification Document Number", 120);
            AbstractColumn accountHoldersPAN = ReportBean.getColumn("accountHoldersPAN", String.class, "PAN of Account holder", 120);
            AbstractColumn accountHoldersAddress = ReportBean.getColumn("accountHoldersAddress", String.class, "Address of Account Holder", 120);
            AbstractColumn accountHoldersCity = ReportBean.getColumn("accountHoldersCity", String.class, "Account Holders  City", 120);
            AbstractColumn noOfCancelPoDo = ReportBean.getColumn("noOfCancelPoDo", String.class, "Number of DDs/BCs Cancelled", 120);
            AbstractColumn valueOfCancelPoDo = ReportBean.getColumn("valueOfCancelPoDo", BigDecimal.class, "Value of DDs/BCs cancelled", 120);

//            fastReport.addColumn(sNo);
//            width = width + sNo.getWidth();

            fastReport.addColumn(reportingEntityName);
            width = width + reportingEntityName.getWidth();

            fastReport.addColumn(fiureid);
            width = width + fiureid.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(brachName);
            width = width + brachName.getWidth();

            fastReport.addColumn(brachAdd);
            width = width + brachAdd.getWidth();

            fastReport.addColumn(branchCity);
            width = width + branchCity.getWidth();

            fastReport.addColumn(brachPinCode);
            width = width + brachPinCode.getWidth();

            fastReport.addColumn(accountHolderName);
            width = width + accountHolderName.getWidth();

            fastReport.addColumn(identificationDocumentType);
            width = width + identificationDocumentType.getWidth();

            fastReport.addColumn(identificationDocumentNumber);
            width = width + identificationDocumentNumber.getWidth();

            fastReport.addColumn(accountHoldersPAN);
            width = width + accountHoldersPAN.getWidth();

            fastReport.addColumn(accountHoldersAddress);
            width = width + accountHoldersAddress.getWidth();

            fastReport.addColumn(accountHoldersCity);
            width = width + accountHoldersCity.getWidth();

            fastReport.addColumn(noOfCancelPoDo);
            noOfCancelPoDo.setStyle(style);
            width = width + noOfCancelPoDo.getWidth();

            fastReport.addColumn(valueOfCancelPoDo);
            valueOfCancelPoDo.setStyle(style);
            width = width + valueOfCancelPoDo.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle("Deatails of cases involving cancellation of three or more Demand Drafts/Bankers' Cheque of individual value of Rs. " + amount1 + " or more ordered by cash deposit from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + " (REPORT-25)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genratePoDoReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            //For FIU
            AbstractColumn reportingEntityName = ReportBean.getColumn("reportingEntityName", String.class, "Reporting Entity Name", 120);
            AbstractColumn fiureid = ReportBean.getColumn("fiureid", String.class, "FIUREID", 120);
            //For Branch
            AbstractColumn brachName = ReportBean.getColumn("brachName", String.class, "Branch", 120);
            AbstractColumn brachAdd = ReportBean.getColumn("brachAdd", String.class, "Branch Address", 200);
            AbstractColumn brachPinCode = ReportBean.getColumn("brachPinCode", String.class, "PINCODE", 120);
            //DETAILS OF A/C HOLDER / APPLICANT
            AbstractColumn accountHolderName = ReportBean.getColumn("accountHolderName", String.class, "Applicant Name", 120);
            AbstractColumn accountHoldersAddress = ReportBean.getColumn("accountHoldersAddress", String.class, "Applicant complete address", 120);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "Applicant's A/C No.(if available)", 120);
            AbstractColumn accountHoldersPAN = ReportBean.getColumn("accountHoldersPAN", String.class, "PAN", 120);
            //DETAILS OF PAYEE
            AbstractColumn payeeName = ReportBean.getColumn("payeeName", String.class, "Payee name", 200);
            AbstractColumn payeeCity = ReportBean.getColumn("payeeCity", String.class, "City/Town Payable at", 120);
            AbstractColumn payeeAdd = ReportBean.getColumn("payeeAdd", String.class, "Address", 120);
            // Details of DD
            AbstractColumn acOpenDuringDt = ReportBean.getColumn("acOpenDuringDt", String.class, "Date(s) of issue", 80);
            AbstractColumn noOfCancelPoDo = ReportBean.getColumn("noOfCancelPoDo", String.class, "Number of DDs/BCs issued (count)", 120);
            AbstractColumn valueOfCancelPoDo = ReportBean.getColumn("valueOfCancelPoDo", BigDecimal.class, " Value of DDs/BCs issued( in Rs.)", 120);
            AbstractColumn totalValueddPo = ReportBean.getColumn("totalValueddPo", BigDecimal.class, " Total value Of DDS/BCS Issued to the applicant", 120);



            fastReport.addColumn(reportingEntityName);
            width = width + reportingEntityName.getWidth();

            fastReport.addColumn(fiureid);
            width = width + fiureid.getWidth();

            fastReport.addColumn(brachName);
            width = width + brachName.getWidth();

            fastReport.addColumn(brachAdd);
            width = width + brachAdd.getWidth();

            fastReport.addColumn(brachPinCode);
            width = width + brachPinCode.getWidth();

            fastReport.addColumn(accountHolderName);
            width = width + accountHolderName.getWidth();

            fastReport.addColumn(accountHoldersAddress);
            width = width + accountHoldersAddress.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(accountHoldersPAN);
            width = width + accountHoldersPAN.getWidth();

            fastReport.addColumn(payeeName);
            width = width + payeeName.getWidth();

            fastReport.addColumn(payeeCity);
            width = width + payeeCity.getWidth();

            fastReport.addColumn(payeeAdd);
            width = width + payeeAdd.getWidth();

            fastReport.addColumn(acOpenDuringDt);
            width = width + acOpenDuringDt.getWidth();

            fastReport.addColumn(noOfCancelPoDo);
            noOfCancelPoDo.setStyle(style);
            width = width + noOfCancelPoDo.getWidth();

            fastReport.addColumn(valueOfCancelPoDo);
            valueOfCancelPoDo.setStyle(style);
            width = width + valueOfCancelPoDo.getWidth();

            fastReport.addColumn(totalValueddPo);
            totalValueddPo.setStyle(style);
            width = width + totalValueddPo.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle("Details of cases involving issuance of Demand Drafts/Bankers cheques for Rs. " + amount1 + " lakhs or more, single or multiple issues,from " + dd_mm_yyyy.format(dmy.parse(frDt)) + " to " + dd_mm_yyyy.format(dmy.parse(toDt)) + "(REPORT-32)");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public FastReportBuilder genrateFiuDormantToOperativeReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            String dth = ymd.format(dmy.parse(toDt));
            String headerToDt = dth.substring(6, 8) + "-" + dth.substring(4, 6) + "-" + dth.substring(0, 4);
            AbstractColumn date = ReportBean.getColumn("date", String.class, "Date", 60);
            AbstractColumn nameOfReportingEntity = ReportBean.getColumn("nameOfReportingEntity", String.class, "Name of Reporting entity", 120);
            AbstractColumn fiuReId = ReportBean.getColumn("fiuReId", String.class, "FIUREID", 60);
            AbstractColumn noOfDormantAccountPrevDay = ReportBean.getColumn("noOfDormantAccountPrevDay", Integer.class, "No. of dormant account as on 07-11-2016", 120);
            AbstractColumn balanceOnPrevDay = ReportBean.getColumn("balanceOnPrevDay", BigDecimal.class, "Balance in these accounts as on 07-11-2016", 120);
            AbstractColumn noOfDormantAcReactivatedBetDate = ReportBean.getColumn("noOfDormantAcReactivatedBetDate", Integer.class, "No. of dormant accounts reactivated between 08-11-2016 to " + headerToDt, 120);
            AbstractColumn amountDepositedBetDate = ReportBean.getColumn("amountDepositedBetDate", BigDecimal.class, "Amount deposited  between 08-11-2016 to " + headerToDt + " by any means (i.e. transfer, clearing, cash, etc.)", 120);
            AbstractColumn amountWithdrawnBetDate = ReportBean.getColumn("amountWithdrawnBetDate", BigDecimal.class, "Amount of money withdrawn between 09-11-2016 to " + headerToDt + " by any means (i.e. transfer, clearing, cash, etc. ", 120);


            fastReport.addColumn(date);
            width = width + date.getWidth();

            fastReport.addColumn(nameOfReportingEntity);
            width = width + nameOfReportingEntity.getWidth();

            fastReport.addColumn(fiuReId);
            width = width + fiuReId.getWidth();

            fastReport.addColumn(noOfDormantAccountPrevDay);
            width = width + noOfDormantAccountPrevDay.getWidth();

            fastReport.addColumn(balanceOnPrevDay);
            balanceOnPrevDay.setStyle(style);
            width = width + balanceOnPrevDay.getWidth();

            fastReport.addColumn(noOfDormantAcReactivatedBetDate);
            width = width + noOfDormantAcReactivatedBetDate.getWidth();

            fastReport.addColumn(amountDepositedBetDate);
            amountDepositedBetDate.setStyle(style);
            width = width + amountDepositedBetDate.getWidth();

            fastReport.addColumn(amountWithdrawnBetDate);
            amountWithdrawnBetDate.setStyle(style);
            width = width + amountWithdrawnBetDate.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle("Report on transactions in dormant account operated between 8th  and " + dth + "th Novembver 2016");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void btnRefreshAction() {
        setMessage("");
        setRepType("S");
        setDepositTranType("ALL");
        setWithdrawTranType("ALL");
        setAmtType("S");
        setAmount(0.0);
        setAmount1(new BigDecimal("0.00"));
        setAmount2(new BigDecimal("0.00"));
        setTillAmount(new BigDecimal("0.00"));
        setFrDt("08/11/2016");
        setToDt("30/12/2016");
        setAmtVisibleTill("none");
        setAmtVisible("none");
        setDisableTranType(true);
    }

    public String btnExitAction() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public String getDepositTranType() {
        return depositTranType;
    }

    public void setDepositTranType(String depositTranType) {
        this.depositTranType = depositTranType;
    }

    public List<SelectItem> getDepositTranTypeList() {
        return depositTranTypeList;
    }

    public void setDepositTranTypeList(List<SelectItem> depositTranTypeList) {
        this.depositTranTypeList = depositTranTypeList;
    }

    public String getWithdrawTranType() {
        return withdrawTranType;
    }

    public void setWithdrawTranType(String withdrawTranType) {
        this.withdrawTranType = withdrawTranType;
    }

    public List<SelectItem> getWithdrawTranTypeList() {
        return withdrawTranTypeList;
    }

    public void setWithdrawTranTypeList(List<SelectItem> withdrawTranTypeList) {
        this.withdrawTranTypeList = withdrawTranTypeList;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Boolean getDisableTranType() {
        return disableTranType;
    }

    public void setDisableTranType(Boolean disableTranType) {
        this.disableTranType = disableTranType;


    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    public List<SelectItem> getAmtTypeList() {
        return amtTypeList;
    }

    public void setAmtTypeList(List<SelectItem> amtTypeList) {
        this.amtTypeList = amtTypeList;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public String getAmtVisible() {
        return amtVisible;
    }

    public void setAmtVisible(String amtVisible) {
        this.amtVisible = amtVisible;
    }

    public String getAmtVisibleTill() {
        return amtVisibleTill;
    }

    public void setAmtVisibleTill(String amtVisibleTill) {
        this.amtVisibleTill = amtVisibleTill;
    }

    public BigDecimal getTillAmount() {
        return tillAmount;
    }

    public void setTillAmount(BigDecimal tillAmount) {
        this.tillAmount = tillAmount;
    }

    public String getDenominationType() {
        return denominationType;
    }

    public void setDenominationType(String denominationType) {
        this.denominationType = denominationType;
    }

    public List<SelectItem> getDenominationTypeList() {
        return denominationTypeList;
    }

    public void setDenominationTypeList(List<SelectItem> denominationTypeList) {
        this.denominationTypeList = denominationTypeList;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public List<SelectItem> getStatusTypeList() {
        return statusTypeList;
    }

    public void setStatusTypeList(List<SelectItem> statusTypeList) {
        this.statusTypeList = statusTypeList;
    }

    public Boolean getDisableStatus() {
        return disableStatus;
    }

    public void setDisableStatus(Boolean disableStatus) {
        this.disableStatus = disableStatus;
    }

    public Boolean getDisableAmtType() {
        return disableAmtType;
    }

    public void setDisableAmtType(Boolean disableAmtType) {
        this.disableAmtType = disableAmtType;
    }
}
