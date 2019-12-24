/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.ComparatorForAcStatusInString;
import com.cbs.dto.report.EmiExist;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.SortedByAcStatus;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OverDueReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.loan.ComparatorForAcno;
import com.cbs.web.pojo.loan.ComparatorForMarkFlag;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Athar Reza
 */
public class NpaAccountDetail extends BaseBean {

    private String errorMsg;
    private String branchCode;
    private String acctType;
    private String tillDate;
    private String typeOfNpa;
    private String Amt;
    boolean disableBntPost;
    boolean disableBntPrint;
    private boolean reportFlag;
    private String acNature;
    private List tempList;
    private Vector tempVector;
    private List<SelectItem> acNatureList;
    private List<SelectItem> typeOfNpaList;
    private List<SelectItem> acctTypeList;
    private List<SelectItem> branchCodeList;
    private List<NpaAccountDetailPojo> gridDetail;
    List<NpaAccountDetailPojo> resultlist;
    private AccountAuthorizationManagementFacadeRemote otherMasterRemote = null;
    private CommonReportMethodsRemote common = null;
    private LoanReportFacadeRemote beanRemote = null;
    private OverDueReportFacadeRemote overDueRemote = null;
    private TdReceiptManagementFacadeRemote remoteCode = null;
    private LoanInterestCalculationFacadeRemote localRemote = null;
    private FtsPostingMgmtFacadeRemote fts = null;
    private Date date = new Date();
    private String viewID = "/pages/master/sm/test.jsp";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isDisableBntPrint() {
        return disableBntPrint;
    }

    public void setDisableBntPrint(boolean disableBntPrint) {
        this.disableBntPrint = disableBntPrint;
    }

    public boolean isDisableBntPost() {
        return disableBntPost;
    }

    public void setDisableBntPost(boolean disableBntPost) {
        this.disableBntPost = disableBntPost;
    }

    public List<NpaAccountDetailPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpaAccountDetailPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getTypeOfNpa() {
        return typeOfNpa;
    }

    public void setTypeOfNpa(String typeOfNpa) {
        this.typeOfNpa = typeOfNpa;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public String getAmt() {
        return Amt;
    }

    public void setAmt(String Amt) {
        this.Amt = Amt;
    }

    public List<SelectItem> getTypeOfNpaList() {
        return typeOfNpaList;
    }

    public void setTypeOfNpaList(List<SelectItem> typeOfNpaList) {
        this.typeOfNpaList = typeOfNpaList;
    }

    public NpaAccountDetail() {
        try {
            remoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            overDueRemote = (OverDueReportFacadeRemote) ServiceLocator.getInstance().lookup("OverDueReportFacade");
            localRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup("LoanInterestCalculationFacade");
            otherMasterRemote = (AccountAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("AccountAuthorizationManagementFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            resultlist = new ArrayList<NpaAccountDetailPojo>();

            List brncode = new ArrayList();
            brncode = remoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            typeOfNpaList = new ArrayList<SelectItem>();
            typeOfNpaList.add(new SelectItem("0", "--Select--"));
            typeOfNpaList.add(new SelectItem("2", "Already NPA"));
            typeOfNpaList.add(new SelectItem("1", "Probable NPA"));

            tempList = common.getAcctNatureOnlyDB();
            acNatureList = new ArrayList<SelectItem>();
            if (!tempList.isEmpty()) {
                acNatureList.add(new SelectItem("ALL", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
            List acList = new ArrayList();
            acList = localRemote.getOnlyLoanAcctType();
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("ALL", "ALL"));
            if (!acList.isEmpty()) {
                for (int i = 0; i < acList.size(); i++) {
                    Vector brV = (Vector) acList.get(i);
                    acctTypeList.add(new SelectItem(brV.get(0), brV.get(0).toString()));
                }
            }
            setDisableBntPost(true);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public boolean validate() {
        this.setErrorMsg("");
        try {
            if (branchCode == null || branchCode.equals("")) {
                setErrorMsg("There should be your branch name.");
                return false;
            }
            if (acctType == null || acctType.equalsIgnoreCase("0")) {
                setErrorMsg("Please select Account Type.");
                return false;
            }
            if (tillDate == null || tillDate.equalsIgnoreCase("")) {
                setErrorMsg("Please fill Till Date.");
                return false;
            }
            if (typeOfNpa == null || typeOfNpa.equalsIgnoreCase("0")) {
                setErrorMsg("Please select Type of NPA.");
                return false;
            }
            if (!(new Validator().validateDate_dd_mm_yyyy(tillDate))) {
                setErrorMsg("Please fill proper till date!");
                return false;
            }

            if (typeOfNpa.equalsIgnoreCase("2")) {
                if (dmy.parse(tillDate).compareTo(dmy.parse(getTodayDate())) > 0) {
                    setErrorMsg("Till date can not be greater than today date !");
                    return false;
                }
            }
            setDisableBntPost(true);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
        return true;
    }

    public void blurAcctNature() {
        setErrorMsg("");
        if (acctTypeList != null) {
            acctTypeList.clear();
        }
        Vector vtr = null;
        try {
            List result = null;
            if (!acNature.equalsIgnoreCase("DL")) {
                result = common.getAcctTypeAsAcNatureOnlyDB(acNature);
                acctTypeList.add(new SelectItem("ALL", "ALL"));
                for (int i = 0; i < result.size(); i++) {
                    vtr = (Vector) result.get(i);
                    acctTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                }
            } else {
                if (typeOfNpa.equalsIgnoreCase("1")) {
                    result = common.getActypeOnlyForDLProbableNPA();
                    String space = " ";
                    for (int i = 0; i < result.size(); i++) {
                        vtr = (Vector) result.get(i);
                        if (vtr.get(0).toString().contains("','")) {
                            space = "','";
                            StringBuilder sb = new StringBuilder();
                            String[] pairs = vtr.get(0).toString().split("','");
                            for (int t = 0; t < pairs.length; ++t) {
                                String pair = pairs[t];
                                acctTypeList.add(new SelectItem(pair, pair));
                            }
                        } else {
                            acctTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                        }
                    }
                } else {
                    result = common.getAcctTypeAsAcNatureOnlyDB(acNature);
                    acctTypeList.add(new SelectItem("ALL", "ALL"));
                    for (int i = 0; i < result.size(); i++) {
                        vtr = (Vector) result.get(i);
                        acctTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                    }
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void blurTypeOfNpaList() {
        try {
            setErrorMsg("");
            if (typeOfNpa.equalsIgnoreCase("1")) {
                if (acNatureList != null) {
                    acNatureList.clear();
                }
                tempList = common.getAcctNatureOnlyDB();
                if (!tempList.isEmpty()) {
                    for (int i = 0; i < tempList.size(); i++) {
                        tempVector = (Vector) tempList.get(i);
                        acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                    }
                }
            } else {
                if (acNatureList != null) {
                    acNatureList.clear();
                }
                tempList = common.getAcctNatureOnlyDB();
                if (!tempList.isEmpty()) {
                    acNatureList.add(new SelectItem("ALL", "ALL"));
                    for (int i = 0; i < tempList.size(); i++) {
                        tempVector = (Vector) tempList.get(i);
                        acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                    }
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewReport() {
        this.setErrorMsg("");
        String branchName = "", address = "", header1 = "Pending EMI", header2 = "No Of Days (Overdue)";
        resultlist = new ArrayList<NpaAccountDetailPojo>();
        reportFlag = false;
        if (validate()) {
            try {
//                if (fts.existInParameterInfoReport("NPA-AUTO-POSTING") /*&& typeOfNpa.equalsIgnoreCase("1")*/) {
//                    setDisableBntPost(false);
//                } 
//                else { //This is commented due the Already NPA will auto mark through system.
//                    setDisableBntPost(true);
//                }
                String report = "";
                List brNameAdd = new ArrayList();
                brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportDate", this.tillDate);
                fillParams.put("pbankName", branchName);
                fillParams.put("pbankAddress", address);
//                String nature = common.getAcNatureByAcType(acctType);
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    fillParams.put("pHeader", header2);
                } else {
                    fillParams.put("pHeader", header1);
                }
                if (typeOfNpa.equalsIgnoreCase("2")) {
                    fillParams.put("pProvAmt", typeOfNpa);
                }

                if (typeOfNpa.equalsIgnoreCase("1")) {
                    report = "Probable Npa Account's Detail Report";
                    resultlist = overDueRemote.getProbableNpaDetail(acNature, acctType, ymd.format(dmy.parse(this.tillDate)), branchCode);

                } else {
                    report = "Npa Account Detail Report";
                    resultlist = overDueRemote.getNpaDetailOptimized(acNature, acctType, ymd.format(dmy.parse(this.tillDate)), branchCode,"Y","N");
                }
                fillParams.put("pReportName", report);
                ComparatorChain chain = new ComparatorChain();
                if (!typeOfNpa.equalsIgnoreCase("1")) {
                    chain.addComparator(new SortedByAcStatus());
                }
                chain.addComparator(new ComparatorForMarkFlag());
                chain.addComparator(new EmiExist());
                chain.addComparator(new ComparatorForAcStatusInString());
                chain.addComparator(new ComparatorForAcno());

                Collections.sort(resultlist, chain);

//                ComparatorChain chObj = new ComparatorChain();
//                //chObj.addComparator(new AlmFdShortedBybketWise());
//                chObj.addComparator(new EmiExist());
//                Collections.sort(resultlist, chObj);

//                for (int i = 0; i < resultlist.size(); i++) {
//                    System.out.println(resultlist.get(i).getAcNo() + ";  emi:::>>" + resultlist.get(i).getEmiExist()+"; MarkFlag:"+resultlist.get(i).getMarkFlag()+" ;Ac Status:"+resultlist.get(i).getAcStatus()+" ;Curent Status:"+resultlist.get(i).getCurrentStatus()+" ; Status:"+resultlist.get(i).getSortAcStatus());
//                }

                if (resultlist.isEmpty()) {
                    this.setDisableBntPost(true);
                    throw new ApplicationException("Data does not exist !");
                }
                if (fts.existInParameterInfoReport("NPA-AUTO-POSTING") && (!resultlist.isEmpty())/*&& typeOfNpa.equalsIgnoreCase("1")*/) {
                    setDisableBntPost(false);
                }
//                else { //This is commented due the Already NPA will auto mark through system.
//                    setDisableBntPost(true);
//                }

                if (typeOfNpa.equalsIgnoreCase("1")) {
                    if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        new ReportBean().jasperReport("NpaAccountDetailProbable", "text/html", new JRBeanCollectionDataSource(resultlist), fillParams, report);
                    } else {
                        new ReportBean().jasperReport("NpaAccountDetail", "text/html", new JRBeanCollectionDataSource(resultlist), fillParams, report);
                    }
                } else {
                    new ReportBean().jasperReport("NpaAccountDetail", "text/html", new JRBeanCollectionDataSource(resultlist), fillParams, report);
                }
                reportFlag = true;
                this.setViewID("/report/ReportPagePopUp.jsp");
            } catch (Exception e) {
                setDisableBntPost(true);
//                e.printStackTrace();
                setErrorMsg(e.getMessage());
            }
        }
    }

    public void excelDownload() {
        this.setErrorMsg("");
        String branchName = "", address = "", header1 = "Pending EMI", header2 = "No Of Days (Overdue)";
        resultlist = new ArrayList<NpaAccountDetailPojo>();
        reportFlag = false;
        if (validate()) {
            try {
                String report = "";
                List brNameAdd = new ArrayList();
                brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }
                if (typeOfNpa.equalsIgnoreCase("1")) {
                    report = "Probable NPA Account's Detail Report";
                    resultlist = overDueRemote.getProbableNpaDetail(acNature, acctType, ymd.format(dmy.parse(this.tillDate)), branchCode);

                } else {
                    report = "NPA Account Detail Report";
                    resultlist = overDueRemote.getNpaDetailOptimized(acNature, acctType, ymd.format(dmy.parse(this.tillDate)), branchCode,"Y","Y");
                }

                ComparatorChain chain = new ComparatorChain();
                if (!typeOfNpa.equalsIgnoreCase("1")) {
                    chain.addComparator(new SortedByAcStatus());
                }
                chain.addComparator(new ComparatorForMarkFlag());
                chain.addComparator(new EmiExist());
                chain.addComparator(new ComparatorForAcStatusInString());
                chain.addComparator(new ComparatorForAcno());
                Collections.sort(resultlist, chain);
                FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(resultlist), fastReportBuilder, report + "_" + acNature + "_" + acctType + "_" + ymd.format(dmy.parse(this.tillDate)));
                reportFlag = true;
            } catch (Exception e) {
                setDisableBntPost(true);
                setErrorMsg(e.getMessage());
            }
        }
    }

    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            String branchName = "", add = "";
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.CENTER);
            style.setBorder(Border.THIN);

            AbstractColumn accountNumber = ReportBean.getColumn("acNo", String.class, "Loan A/c No.", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name", 100);
            AbstractColumn address = ReportBean.getColumn("address", String.class, "Permanent Address", 400);
            AbstractColumn currAddress = ReportBean.getColumn("currAddress", String.class, "Current Address", 400);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father Name", 100);
            AbstractColumn contactNo = ReportBean.getColumn("contactNo", String.class, "Contact No.", 80);
            AbstractColumn drawalDt = ReportBean.getColumn("disburseDt", String.class, "Drawal Date", 80);
            AbstractColumn drawalAmt = ReportBean.getColumn("disburseAmt", BigDecimal.class, "Drawal Amount", 80);
            AbstractColumn sanctionDt = ReportBean.getColumn("sancDate", String.class, "Sanction/Limit Modification date", 100);
            AbstractColumn amountSanc = ReportBean.getColumn("sancAmt", BigDecimal.class, "Sanction Amount/Modified Limit", 60);
            AbstractColumn currStatus = ReportBean.getColumn("currentStatus", String.class, "Current Status", 200);
            AbstractColumn lastCrDt = ReportBean.getColumn("lastCrDate", String.class, "Next dt of Last Unpaid EMI/ Int Post/ Last EMI PAID Dt", 100);
            AbstractColumn lastCrAmt = ReportBean.getColumn("lastCrAmt", BigDecimal.class, "Last Cr. Amount", 80);
            AbstractColumn dayDiff = ReportBean.getColumn("currentStatusNoOfDays", Long.class, "Day Diff.", 90);
            AbstractColumn npaDt = ReportBean.getColumn("npaDate", String.class, "", 0);
            AbstractColumn subStdDt = ReportBean.getColumn("subDt", String.class, "", 0);
            AbstractColumn retirementAge = ReportBean.getColumn("retirementAge", String.class, "Retirement Age", 0);
            if (!typeOfNpa.equalsIgnoreCase("1")) {
                npaDt = ReportBean.getColumn("npaDate", String.class, "NPA Date as per Category", 80);
                subStdDt = ReportBean.getColumn("subDt", String.class, "Sub-Standard Date", 80);
                retirementAge = ReportBean.getColumn("retirementAge", String.class, "Retirement Age", 100);
            }
            AbstractColumn odAmt = ReportBean.getColumn("overDueAmt", BigDecimal.class, "OverDue Amount", 60);
            AbstractColumn balance = ReportBean.getColumn("outstdBal", BigDecimal.class, "Outstanding Balance", 100);
            AbstractColumn noOfEMI = ReportBean.getColumn("noOfPendingEmi", Long.class, "No. Of pending EMI", 100);
            AbstractColumn npaAmt = ReportBean.getColumn("npaAmt", BigDecimal.class, "NPA Interest Balance", 100);
            AbstractColumn provPerc = ReportBean.getColumn("provPerc", BigDecimal.class, "Provision Percentage", 100);
            AbstractColumn provAmt = ReportBean.getColumn("provAmt", BigDecimal.class, "Provision Amount", 100);
            AbstractColumn nature = ReportBean.getColumn("nature", String.class, "Nature", 100);
            AbstractColumn pin = ReportBean.getColumn("pin", String.class, "Pin", 100);

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(address);
            width = width + address.getWidth();

            fastReport.addColumn(currAddress);
            width = width + currAddress.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(contactNo);
            width = width + contactNo.getWidth();

            fastReport.addColumn(drawalDt);
            width = width + drawalDt.getWidth();

            fastReport.addColumn(drawalAmt);
            width = width + drawalAmt.getWidth();

            fastReport.addColumn(sanctionDt);
            width = width + sanctionDt.getWidth();

            fastReport.addColumn(amountSanc);
            width = width + amountSanc.getWidth();

            fastReport.addColumn(currStatus);
            width = width + currStatus.getWidth();

            fastReport.addColumn(lastCrDt);
            width = width + lastCrDt.getWidth();

            fastReport.addColumn(lastCrAmt);
            width = width + lastCrAmt.getWidth();

            fastReport.addColumn(dayDiff);
            width = width + dayDiff.getWidth();

            if (!typeOfNpa.equalsIgnoreCase("1")) {
                fastReport.addColumn(npaDt);
                width = width + npaDt.getWidth();
                fastReport.addColumn(subStdDt);
                width = width + subStdDt.getWidth();
                fastReport.addColumn(retirementAge);
                width = width + retirementAge.getWidth();
            }

            fastReport.addColumn(odAmt);
            width = width + odAmt.getWidth();

            fastReport.addColumn(balance);
            width = width + balance.getWidth();

            fastReport.addColumn(noOfEMI);
            width = width + noOfEMI.getWidth();

            fastReport.addColumn(npaAmt);
            width = width + npaAmt.getWidth();

            fastReport.addColumn(provPerc);
            width = width + provPerc.getWidth();

            fastReport.addColumn(provAmt);
            width = width + provAmt.getWidth();

            fastReport.addColumn(nature);
            width = width + nature.getWidth();
            
            fastReport.addColumn(pin);
            width = width + pin.getWidth();

            Page page = new Page(1342, width, false);
            fastReport.setPrintBackgroundOnOddRows(true);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            if (typeOfNpa.equalsIgnoreCase("1")) {
                fastReport.setTitle("Probable Npa Account's Detail Report as on ( " + tillDate + " )");
            } else {
                fastReport.setTitle("Npa Account Detail Report as on ( " + tillDate + " )");
            }
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                add = (String) brNameAdd.get(1);
            }
            fastReport.setSubtitleStyle(style);
            fastReport.setSubtitle(branchName.concat("").concat(add));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void pdfDownLoad() {
        this.setErrorMsg("");
        String branchName = "", address = "", header1 = "Pending EMI", header2 = "No Of Days (Overdue)";
        resultlist = new ArrayList<NpaAccountDetailPojo>();
        reportFlag = false;
        if (validate()) {
            try {
//                if (fts.existInParameterInfoReport("NPA-AUTO-POSTING") /*&& typeOfNpa.equalsIgnoreCase("1")*/) {
//                    setDisableBntPost(false);
//                } 
//                else { //This is commented due the Already NPA will auto mark through system.
//                    setDisableBntPost(true);
//                }
                String report = "";
                List brNameAdd = new ArrayList();
                brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportDate", this.tillDate);
                fillParams.put("pbankName", branchName);
                fillParams.put("pbankAddress", address);

//                    String nature = common.getAcNatureByAcType(acctType);
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    fillParams.put("pHeader", header2);
                } else {
                    fillParams.put("pHeader", header1);
                }
                if (typeOfNpa.equalsIgnoreCase("2")) {
                    fillParams.put("pProvAmt", typeOfNpa);
                }

                if (typeOfNpa.equalsIgnoreCase("1")) {
                    report = "Probable Npa Account's Detail Report";
                    resultlist = overDueRemote.getProbableNpaDetail(acNature, acctType, ymd.format(dmy.parse(this.tillDate)), branchCode);

                } else {
                    report = "Npa Account Detail Report";
                    resultlist = overDueRemote.getNpaDetailOptimized(acNature, acctType, ymd.format(dmy.parse(this.tillDate)), branchCode,"Y","N");
                }
                fillParams.put("pReportName", report);
                ComparatorChain chain = new ComparatorChain();
                if (!typeOfNpa.equalsIgnoreCase("1")) {
                    chain.addComparator(new SortedByAcStatus());
                }
                chain.addComparator(new ComparatorForMarkFlag());
                chain.addComparator(new EmiExist());
                chain.addComparator(new ComparatorForAcStatusInString());
                chain.addComparator(new ComparatorForAcno());
                Collections.sort(resultlist, chain);

//                ComparatorChain chObj = new ComparatorChain();
//                //chObj.addComparator(new AlmFdShortedBybketWise());
//                chObj.addComparator(new EmiExist());
//                Collections.sort(resultlist, chObj);

//                for (int i = 0; i < resultlist.size(); i++) {
//                    System.out.println(resultlist.get(i).getAcNo() + ";  emi:::>>" + resultlist.get(i).getEmiExist()+"; MarkFlag:"+resultlist.get(i).getMarkFlag());
//
//                }

                if (resultlist.isEmpty()) {
                    this.setDisableBntPost(true);
                    throw new ApplicationException("Data does not exist !");
                }
                if (fts.existInParameterInfoReport("NPA-AUTO-POSTING") && (!resultlist.isEmpty())/*&& typeOfNpa.equalsIgnoreCase("1")*/) {
                    setDisableBntPost(false);
                }
//                else { //This is commented due the Already NPA will auto mark through system.
//                    setDisableBntPost(true);
//                }

                if (typeOfNpa.equalsIgnoreCase("1")) {
                    if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {

                        new ReportBean().openPdf("NpaAccountDetailProbable_" + ymd.format(dmy.parse(this.tillDate)), "NpaAccountDetailProbable", new JRBeanCollectionDataSource(resultlist), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);
                    } else {

                        new ReportBean().openPdf("NpaAccountDetail_" + ymd.format(dmy.parse(this.tillDate)), "NpaAccountDetail", new JRBeanCollectionDataSource(resultlist), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", report);
                    }
                } else {
                    new ReportBean().openPdf("NpaAccountDetail_" + ymd.format(dmy.parse(this.tillDate)), "NpaAccountDetail", new JRBeanCollectionDataSource(resultlist), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);

                }
                reportFlag = true;
                //this.setViewID("/report/ReportPagePopUp.jsp");
            } catch (Exception e) {
                setDisableBntPost(true);
                e.printStackTrace();
                setErrorMsg(e.getMessage());
            }
        }
    }

    public void npaMarkPost() {
        this.setErrorMsg("");
        this.setDisableBntPrint(true);
        try {
            String tillDt = ymd.format(dmy.parse(this.tillDate));
            if (resultlist.isEmpty()) {
                throw new ApplicationException("Please first find Npa Status then npa mark !");
            }
            String result = otherMasterRemote.npaPosting(resultlist, tillDt, getUserName(), getOrgnBrCode());
            if (result.equalsIgnoreCase("Npa marking is successfull.")) {
                refresh();
                setDisableBntPost(true);
            }
            setErrorMsg(result);
            //resultlist = new ArrayList<NpaAccountDetailPojo>();
        } catch (Exception e) {
            setDisableBntPost(true);
            setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        setDisableBntPost(true);
        setDisableBntPrint(false);
        setTillDate("");
        setErrorMsg("");
        acctType = "0";
        typeOfNpa = "0";
        reportFlag = false;
        resultlist = new ArrayList<NpaAccountDetailPojo>();
    }

    public String exitAction() {
        return "case1";
    }
}
