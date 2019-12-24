package com.cbs.web.controller.intcal;

import com.cbs.constant.Months;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.CentralizedLoanInterestCalFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.intcal.LoanPenalCalculationFacadeRemote;
import com.cbs.facade.intcal.TDInterestCalulationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.pojo.IntCalTable;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.loan.InterestCalculationPojo;
import com.cbs.web.pojo.loan.SortedByBranch;
import com.cbs.web.pojo.loan.SortedByAcType;
import java.math.BigDecimal;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

public class CentralizedPenalInterestCalculation extends BaseBean {

    private String message;
    private Date fromDate;
    private Date toDate;
    String tmpDate = "";
    String tmpFDate;
    private String acType;
    private List<SelectItem> acTypeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String lblMonthEnd;
    private String monthEnd;
    private List<SelectItem> monthEndList;
    private String finYear;
    private List<SelectItem> finYearList;
    private String errorMessage;
    private boolean disablePost;
    private String glAcNo;
    private boolean reportFlag;
    private int maxDays;
    private String List;
    private List<LoanIntCalcList> intCalc1;
    private List<IntCalTable> intCalc;
    private final String loanPenalCalcJndiName = "LoanPenalCalculationFacade";
    private LoanPenalCalculationFacadeRemote loanPenalCalculationRemote = null;
    private final String jndiHomeName = "OtherReportFacade";
    private OtherReportFacadeRemote beanRemoteFacade = null;
    private CentralizedLoanInterestCalFacadeRemote centralizedLoanInterestCalMainRemote = null;
    private final String centralizedLoanInterestCalJndiName = "CentralizedLoanInterestCalFacade";
    private final String loanIntCalHomeName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote loanIntCalculationRemote = null;
    private final String tdInterestCalculationJndiName = "TDInterestCalulationFacade";
    private TDInterestCalulationFacadeRemote tdInterestCalculationMainRemote = null;
    private String viewID = "/pages/master/sm/test.jsp";
    CommonReportMethodsRemote RemoteCode;
    MiscReportFacadeRemote beanRemote;
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonRemote = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public String getList() {
        return List;
    }

    public void setList(String List) {
        this.List = List;
    }

    public String getAcType() {
        return acType;
    }

    public String getLblMonthEnd() {
        return lblMonthEnd;
    }

    public void setLblMonthEnd(String lblMonthEnd) {
        this.lblMonthEnd = lblMonthEnd;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getMonthEnd() {
        return monthEnd;
    }

    public String getGlAcNo() {
        return glAcNo;
    }

    public void setGlAcNo(String glAcNo) {
        this.glAcNo = glAcNo;
    }

    public List<IntCalTable> getIntCalc() {
        return intCalc;
    }

    public void setIntCalc(List<IntCalTable> intCalc) {
        this.intCalc = intCalc;
    }

    public void setMonthEnd(String monthEnd) {
        this.monthEnd = monthEnd;
    }

    public List<SelectItem> getMonthEndList() {
        return monthEndList;
    }

    public void setMonthEndList(List<SelectItem> monthEndList) {
        this.monthEndList = monthEndList;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public List<SelectItem> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<SelectItem> finYearList) {
        this.finYearList = finYearList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public List<LoanIntCalcList> getIntCalc1() {
        return intCalc1;
    }

    public void setIntCalc1(List<LoanIntCalcList> intCalc1) {
        this.intCalc1 = intCalc1;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public OtherReportFacadeRemote getBeanRemoteFacade() {
        return beanRemoteFacade;
    }

    public void setBeanRemoteFacade(OtherReportFacadeRemote beanRemoteFacade) {
        this.beanRemoteFacade = beanRemoteFacade;
    }

    public CommonReportMethodsRemote getRemoteCode() {
        return RemoteCode;
    }

    public void setRemoteCode(CommonReportMethodsRemote RemoteCode) {
        this.RemoteCode = RemoteCode;
    }

    public MiscReportFacadeRemote getBeanRemote() {
        return beanRemote;
    }

    public String getTmpDate() {
        return tmpDate;
    }

    public void setTmpDate(String tmpDate) {
        this.tmpDate = tmpDate;
    }

    public String getTmpFDate() {
        return tmpFDate;
    }

    public void setTmpFDate(String tmpFDate) {
        this.tmpFDate = tmpFDate;
    }

    public void setBeanRemote(MiscReportFacadeRemote beanRemote) {
        this.beanRemote = beanRemote;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public CentralizedPenalInterestCalculation() {
        try {
            branchCodeList = new ArrayList<SelectItem>();
            acNatureList = new ArrayList<SelectItem>();
            acTypeList = new ArrayList<SelectItem>();
            finYearList = new ArrayList<SelectItem>();
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemoteFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            loanIntCalculationRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(loanIntCalHomeName);
            tdInterestCalculationMainRemote = (TDInterestCalulationFacadeRemote) ServiceLocator.getInstance().lookup(tdInterestCalculationJndiName);
            loanPenalCalculationRemote = (LoanPenalCalculationFacadeRemote) ServiceLocator.getInstance().lookup(loanPenalCalcJndiName);
            centralizedLoanInterestCalMainRemote = (CentralizedLoanInterestCalFacadeRemote) ServiceLocator.getInstance().lookup(centralizedLoanInterestCalJndiName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);
            // mansi add
            intCalc = new ArrayList<IntCalTable>();
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            List accNture = RemoteCode.getAcctNatureOnlyDB();
            if (!accNture.isEmpty()) {
                acNatureList.add(new SelectItem("ALL", "ALL"));
                for (int j = 0; j < accNture.size(); j++) {
                    Vector vect = (Vector) accNture.get(j);
                    acNatureList.add(new SelectItem(vect.get(0).toString(), vect.get(0).toString()));
                }
            }

            this.setReportFlag(false);
            setDisablePost(true);
            setFinYears();
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void blurAcctNature() {
        try {
            if (acTypeList != null) {
                acTypeList.clear();
            }
            Vector vtr = null;
            intCalc = new ArrayList<IntCalTable>();
            List result = null;
            result = RemoteCode.getAcctTypeAsAcNatureOnlyDB(acNature);
            acTypeList.add(new SelectItem("ALL", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                acTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setFinYears() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setReportFlag(false);
        try {
            List tempList = new ArrayList();
            finYearList = new ArrayList<SelectItem>();
            tempList = tdInterestCalculationMainRemote.finYearTD(getOrgnBrCode());
            if (!tempList.isEmpty()) {
                Vector ele = (Vector) tempList.get(0);
                finYearList.add(new SelectItem(ele.get(0).toString()));
                finYearList.add(new SelectItem(ele.get(1).toString()));
                //}
            }

        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void setMonthEnd() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setReportFlag(false);
        intCalc = new ArrayList<IntCalTable>();
        try {
            setLblMonthEnd("Month End :");
            monthEndList = new ArrayList<SelectItem>();
            monthEndList.add(new SelectItem("--Select--"));
            monthEndList.add(new SelectItem("1", Months.JANUARY.getName()));
            // }else if(mDay==1){
            monthEndList.add(new SelectItem("2", Months.FEBRUARY.getName()));
            // }else if(mDay==2){
            monthEndList.add(new SelectItem("3", Months.MARCH.getName()));
            // }else if(mDay==3){
            monthEndList.add(new SelectItem("4", Months.APRIL.getName()));
            // }else if(mDay==4){
            monthEndList.add(new SelectItem("5", Months.MAY.getName()));
            // }else if(mDay==5){
            monthEndList.add(new SelectItem("6", Months.JUNE.getName()));
            // }else if(mDay==6){
            monthEndList.add(new SelectItem("7", Months.JULY.getName()));
            // }else if(mDay==7){
            monthEndList.add(new SelectItem("8", Months.AUGUST.getName()));
            // }else if(mDay==8){
            monthEndList.add(new SelectItem("9", Months.SEPTEMBER.getName()));
            //  }else if(mDay==9){
            monthEndList.add(new SelectItem("10", Months.OCTOBER.getName()));
            // }else if(mDay==10){
            monthEndList.add(new SelectItem("11", Months.NOVEMBER.getName()));
            // }else if(mDay==11){
            monthEndList.add(new SelectItem("12", Months.DECEMBER.getName()));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void monthEndlostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setReportFlag(false);
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
//     public void ddAccountNo(String acctNo,String acNature) {
//        try {
//            List glHeadList = new ArrayList();
//            glHeadList = loanIntCalculationRemote.getGlHeads1(acctNo,acNature);
//            setCreditAmt(glHeadList);
//        } catch (ApplicationException e) {
//            this.setMessage(e.getMessage());
//        } catch (Exception e) {
//            this.setMessage(e.getLocalizedMessage());
//        }
//    }

    public void centralizedPenalInterestCalculation() {
        String tmpMonth;
        this.setErrorMessage("");
        this.setReportFlag(false);
        this.setMessage("");
        try {
            HashMap<String, Integer> map = new HashMap();
            map.put("1", 01);
            map.put("2", 02);
            map.put("3", 03);
            map.put("4", 04);
            map.put("5", 05);
            map.put("6", 06);
            map.put("7", 07);
            map.put("8", 8);
            map.put("9", 9);
            map.put("10", 10);
            map.put("11", 11);
            map.put("12", 12);

            int year = Integer.parseInt(this.finYear);
            int date5 = CbsUtil.getEndDate(Integer.parseInt(monthEnd), year);
            tmpMonth = (this.monthEnd).toString();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, map.get(tmpMonth) - 1, 1);
            maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int length = tmpMonth.length();
            int addedZero = 2 - length;
            for (int i = 1; i <= addedZero; i++) {
                tmpMonth = "0" + tmpMonth;
            }

            tmpDate = maxDays + "/" + tmpMonth + "/" + year;
            tmpFDate = "01" + "/" + tmpMonth + "/" + year;
            String fromDate = tmpFDate.substring(6) + tmpFDate.substring(3, 5) + tmpFDate.substring(0, 2);
            String toDate = tmpDate.substring(6) + tmpDate.substring(3, 5) + tmpDate.substring(0, 2);
            NumberFormat formatter = new DecimalFormat("#0.00");
            double amount = 0d;
            intCalc = new ArrayList<IntCalTable>();
            List<List<LoanIntCalcList>> resultList = new ArrayList<>();
            resultList = loanPenalCalculationRemote.cbsCentralizedPenalCalculation(branchCode, this.acNature, acType, fromDate, toDate);
            List<InterestCalculationPojo> reportList = new ArrayList<InterestCalculationPojo>();
            if (resultList.isEmpty()) {
                setMessage("Data does not exist");
                setDisablePost(true);
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    List<LoanIntCalcList> loanIntCalTable = resultList.get(i);
                    for (int j = 0; j < loanIntCalTable.size(); j++) {
                        LoanIntCalcList it = loanIntCalTable.get(j);
                        IntCalTable mt = new IntCalTable();
                        InterestCalculationPojo pojo = new InterestCalculationPojo();
                        mt.setsNo(Integer.toString(i + 1));
                        mt.setAcNo(it.getAcNo());
                        mt.setCustName(it.getCustName());

                        pojo.setAcNo(it.getAcNo());
                        pojo.setCustName(it.getCustName());

                        double closingBal = it.getClosingBal();
                        double acctProduct = it.getProduct();
                        double acctInt = it.getTotalInt();
                        if (closingBal < 0.0) {
                            closingBal = -1 * closingBal;
                        }
                        if (acctProduct < 0.0) {
                            acctProduct = -1 * acctProduct;
                        }
                        if (acctInt < 0.0) {
                            acctInt = -1 * acctInt;
                        }
                        mt.setFirstDt(it.getFirstDt());
                        mt.setLastDt(it.getLastDt());
                        pojo.setFromDate(it.getFirstDt());
                        pojo.setToDate(it.getLastDt());
                        mt.setClosingBal(new BigDecimal(formatter.format(it.getClosingBal())));
                        pojo.setClosingBal(new BigDecimal(formatter.format(closingBal)));
                        mt.setProduct(new BigDecimal(formatter.format(acctProduct)));
                        pojo.setProduct(new BigDecimal(formatter.format(acctProduct)));
                        mt.setRoi(new BigDecimal(formatter.format(it.getRoi())));
                        pojo.setRoi(new BigDecimal(formatter.format(it.getRoi())));
                        mt.setTotalInt(new BigDecimal(formatter.format(acctInt)));
                        pojo.setInterestAmt(new BigDecimal(formatter.format(acctInt)));
                        amount = amount + acctInt;
                        mt.setIntTableCode(it.getIntTableCode());
                        mt.setDetails(it.getDetails());
                        pojo.setIntTableCode(it.getIntTableCode());
                        intCalc.add(mt);
                        reportList.add(pojo);
                    }
                }
            }
//            ddAccountNo(acType, acNature);
            printReport(reportList);
            setDisablePost(false);
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
            setDisablePost(true);
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
            setDisablePost(true);
        }
    }

    public void Post() {
        String tmpMonth;
        this.setErrorMessage("");
        this.setMessage("");
        this.setReportFlag(false);
        try {
            HashMap<String, Integer> map = new HashMap();
            map.put("1", 01);
            map.put("2", 02);
            map.put("3", 03);
            map.put("4", 04);
            map.put("5", 05);
            map.put("6", 06);
            map.put("7", 07);
            map.put("8", 8);
            map.put("9", 9);
            map.put("10", 10);
            map.put("11", 11);
            map.put("12", 12);

            int year = Integer.parseInt(this.finYear);
            int date5 = CbsUtil.getEndDate(Integer.parseInt(monthEnd), year);
            tmpMonth = (this.monthEnd).toString();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, map.get(tmpMonth) - 1, 1);
            maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int length = tmpMonth.length();
            int addedZero = 2 - length;
            for (int i = 1; i <= addedZero; i++) {
                tmpMonth = "0" + tmpMonth;
            }
            tmpDate = maxDays + "/" + tmpMonth + "/" + year;
            tmpFDate = "01" + "/" + tmpMonth + "/" + year;
            String fromDate = tmpFDate.substring(6) + tmpFDate.substring(3, 5) + tmpFDate.substring(0, 2);
            String toDate = tmpDate.substring(6) + tmpDate.substring(3, 5) + tmpDate.substring(0, 2);
            setDisablePost(true);
            String msg = loanPenalCalculationRemote.cbsCentralizedPenalPosting(intCalc, getOrgnBrCode(), this.acNature, acType, fromDate, toDate, getUserName());
            if (msg.equalsIgnoreCase("Yes")) {
                this.setMessage("Interest posted successfully");
                System.out.println("End Dt:" + new Date());
            } else {
                this.setErrorMessage(msg);
            }

        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }

    }

    public void printReport(List<InterestCalculationPojo> resultList) {
        try {
            List bankDetailsList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (bankDetailsList.size() > 0) {
                String bankName = bankDetailsList.get(0).toString();
                String bankAddress = bankDetailsList.get(1).toString();
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "Centralized Penal Interest Calculation");
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pFromDt", tmpFDate);
                fillParams.put("pToDt", tmpDate);
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);

                new ReportBean().jasperReport("LOAN_INT_CAL_REPORT", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Interest Calculation");
                this.setReportFlag(true);
                this.setViewID("/report/ReportPagePopUp.jsp");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshAction() {
        try {
            this.setMessage("");
            setBranchCode("0");
            setAcNature("0");
            setAcType("0");
            errorMessage = "";
            intCalc = new ArrayList<IntCalTable>();
            this.setMonthEnd("--Select--");
            this.setFinYear("--Select--");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
