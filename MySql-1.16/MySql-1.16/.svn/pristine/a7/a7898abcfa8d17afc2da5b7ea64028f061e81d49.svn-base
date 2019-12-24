/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.constant.MonthEndDate;
import com.cbs.constant.Months;
import com.cbs.dto.RdInterestDTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.intcal.RdProvisionInterestTable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ProvisionInterestCalculationRd extends BaseBean {

    private List<SelectItem> acctTypeOption;
    private List<SelectItem> intOptionList;
    private List<SelectItem> forTheMonthList;
    private List<SelectItem> quarterEndOption;
    private String acctCode;
    private String interestOpt;
    private String acToBeDebited;
    private String labelForMonth;
    private String forTheMonth;
    private String year;
    private String acToBeCredited;
    private String crAmount;
    private String calFlag;
    private String message;
    private boolean reportFlag;
    private String viewID = "/pages/master/sm/test.jsp";
    NumberFormat formatter = new DecimalFormat("#0.00");
    private List<RdProvisionInterestTable> rdProvision;
    private final String provisionInterestCalRdJndiName = "RDIntCalFacade";
    private RDIntCalFacadeRemote provisionInterestCalRdRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(String crAmount) {
        this.crAmount = crAmount;
    }

    public List<RdProvisionInterestTable> getRdProvision() {
        return rdProvision;
    }

    public void setRdProvision(List<RdProvisionInterestTable> rdProvision) {
        this.rdProvision = rdProvision;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getQuarterEndOption() {
        return quarterEndOption;
    }

    public void setQuarterEndOption(List<SelectItem> quarterEndOption) {
        this.quarterEndOption = quarterEndOption;
    }

    public String getCalFlag() {
        return calFlag;
    }

    public void setCalFlag(String calFlag) {
        this.calFlag = calFlag;
    }

    public String getAcToBeCredited() {
        return acToBeCredited;
    }

    public void setAcToBeCredited(String acToBeCredited) {
        this.acToBeCredited = acToBeCredited;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getForTheMonth() {
        return forTheMonth;
    }

    public void setForTheMonth(String forTheMonth) {
        this.forTheMonth = forTheMonth;
    }

    public List<SelectItem> getForTheMonthList() {
        return forTheMonthList;
    }

    public void setForTheMonthList(List<SelectItem> forTheMonthList) {
        this.forTheMonthList = forTheMonthList;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public String getLabelForMonth() {
        return labelForMonth;
    }

    public void setLabelForMonth(String labelForMonth) {
        this.labelForMonth = labelForMonth;
    }

    public String getAcToBeDebited() {
        return acToBeDebited;
    }

    public void setAcToBeDebited(String acToBeDebited) {
        this.acToBeDebited = acToBeDebited;
    }

    public String getInterestOpt() {
        return interestOpt;
    }

    public void setInterestOpt(String interestOpt) {
        this.interestOpt = interestOpt;
    }

    public List<SelectItem> getIntOptionList() {
        return intOptionList;
    }

    public void setIntOptionList(List<SelectItem> intOptionList) {
        this.intOptionList = intOptionList;
    }

    public ProvisionInterestCalculationRd() {
        try {
            provisionInterestCalRdRemote = (RDIntCalFacadeRemote) ServiceLocator.getInstance().lookup(provisionInterestCalRdJndiName);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));

            forTheMonthList = new ArrayList<SelectItem>();
            setLabelForMonth("For The Month Of");
            getAccountTypes();
            intOptionList = new ArrayList<SelectItem>();
            intOptionList.add(new SelectItem("M", "Monthly"));
            intOptionList.add(new SelectItem("Q", "Quaterly"));
            intOptionList.add(new SelectItem("H", "Half Yearly"));
            this.reportFlag = false;
            getQuaterEndingYear();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getAccountTypes() {
        acctTypeOption = new ArrayList<SelectItem>();
        try {
            List accountTypeList = new ArrayList();
            accountTypeList = provisionInterestCalRdRemote.getAccountTypes();
            for (int i = 0; i < accountTypeList.size(); i++) {
                Vector ele = (Vector) accountTypeList.get(i);
                acctTypeOption.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getInterestGLHeads() {
        try {
            List interestGLHeads = provisionInterestCalRdRemote.getInterestGLHeads(getAcctCode());
            Vector ele = (Vector) interestGLHeads.get(0);
            if (!ele.elementAt(1).toString().equals("")) {
                this.setAcToBeCredited(getOrgnBrCode() + ele.elementAt(1).toString() + "01");
            }
            if (!ele.elementAt(0).toString().equals("")) {
                this.setAcToBeDebited(getOrgnBrCode() + ele.elementAt(0).toString() + "01");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getQuaterEndingYear() {
        quarterEndOption = new ArrayList<SelectItem>();
        try {
            String currentDt = provisionInterestCalRdRemote.getCurrentDate(getOrgnBrCode());
            String curYear = currentDt.substring(0, 4);
            int curYearInt = Integer.parseInt(curYear);
            curYearInt = curYearInt + 1;
            for (int i = 0; i <= 5; i++) {
                curYearInt = curYearInt - 1;
                quarterEndOption.add(new SelectItem(curYearInt));
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setInterestOption() {
        forTheMonthList = new ArrayList<SelectItem>();
        forTheMonthList.add(new SelectItem("--SELECT--"));
        if (interestOpt.equals("Q")) {
            setLabelForMonth("Quater Ending Month");
            forTheMonthList.add(new SelectItem(Months.MARCH.getName()));
            forTheMonthList.add(new SelectItem(Months.JUNE.getName()));
            forTheMonthList.add(new SelectItem(Months.SEPTEMBER.getName()));
            forTheMonthList.add(new SelectItem(Months.DECEMBER.getName()));
        } else if (interestOpt.equals("M")) {
            setLabelForMonth("For The Month Of");
            forTheMonthList.add(new SelectItem(Months.JANUARY.getName()));
            forTheMonthList.add(new SelectItem(Months.FEBRUARY.getName()));
            forTheMonthList.add(new SelectItem(Months.MARCH.getName()));

            forTheMonthList.add(new SelectItem(Months.APRIL.getName()));
            forTheMonthList.add(new SelectItem(Months.MAY.getName()));
            forTheMonthList.add(new SelectItem(Months.JUNE.getName()));

            forTheMonthList.add(new SelectItem(Months.JULY.getName()));
            forTheMonthList.add(new SelectItem(Months.AUGUST.getName()));
            forTheMonthList.add(new SelectItem(Months.SEPTEMBER.getName()));

            forTheMonthList.add(new SelectItem(Months.OCTOBER.getName()));
            forTheMonthList.add(new SelectItem(Months.NOVEMBER.getName()));
            forTheMonthList.add(new SelectItem(Months.DECEMBER.getName()));
        } else if (interestOpt.equals("H")) {
            this.setLabelForMonth("Half Year Ending Month");
            forTheMonthList.add(new SelectItem(Months.SEPTEMBER.getName()));
            forTheMonthList.add(new SelectItem(Months.MARCH.getName()));
        }
    }

    public void calculate() {
        this.setMessage("");
        this.setCrAmount("0.00");
        rdProvision = new ArrayList<RdProvisionInterestTable>();
        if (this.forTheMonth.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please select the value of the month");
            return;
        }
        try {
            String fromDate = "", toDate = "";
            if (interestOpt.equals("Q")) {
                fromDate = year + Months.getMonthValue(CbsUtil.getMonthName(Integer.parseInt(Months.getMonthValue(forTheMonth)) - 2).toUpperCase()) + "01";
                toDate = year + Months.getMonthValue(forTheMonth) + MonthEndDate.getMonthValue(forTheMonth);
            } else if (interestOpt.equals("M")) {
                fromDate = year + Months.getMonthValue(forTheMonth) + "01";
                String endDt = MonthEndDate.getMonthValue(forTheMonth);
                if (forTheMonth.equalsIgnoreCase("FEBRUARY")) {
                    String[] arr = endDt.split(":");
                    if (((Integer.parseInt(year.trim())) % 4) == 0) {
                        endDt = arr[1];
                    } else {
                        endDt = arr[0];
                    }
                }
                toDate = year + Months.getMonthValue(forTheMonth) + endDt;
            } else if (interestOpt.equals("H")) {
                if (this.forTheMonth.equalsIgnoreCase("MARCH")) {
                    fromDate = String.valueOf(Integer.parseInt(year) - 1) + Months.OCTOBER.getValue() + "01";
                } else {
                    fromDate = year + Months.getMonthValue(CbsUtil.getMonthName(Integer.parseInt(Months.getMonthValue(forTheMonth)) - 5).toUpperCase()) + "01";
                }
                toDate = year + Months.getMonthValue(forTheMonth) + MonthEndDate.getMonthValue(forTheMonth);
            }
            List<RdInterestDTO> rdInterestDTOs = provisionInterestCalRdRemote.interestCalculation(acctCode, fromDate, toDate, getOrgnBrCode(), interestOpt);
            int seqNo = 1;
            double totalInt = 0d;
            if (!rdInterestDTOs.isEmpty()) {
                for (RdInterestDTO rdInterestDTO : rdInterestDTOs) {
                    RdProvisionInterestTable rdProvisionInterestTable = new RdProvisionInterestTable();
                    rdProvisionInterestTable.setSeqNumber(seqNo);
                    rdProvisionInterestTable.setAccountNumber(rdInterestDTO.getAcNo());
                    rdProvisionInterestTable.setCustomerName(rdInterestDTO.getCustName());

                    rdProvisionInterestTable.setStartingDate(dmy.format(ymd.parse(rdInterestDTO.getOpeningDt())));
                    rdProvisionInterestTable.setInstallment(String.valueOf(rdInterestDTO.getInstallment()));
                    rdProvisionInterestTable.setRoi(String.valueOf(rdInterestDTO.getRoi()));

                    rdProvisionInterestTable.setProduct(rdInterestDTO.getBalance());
                    rdProvisionInterestTable.setInterest(rdInterestDTO.getInterest());
                    System.out.println("Account No = " + rdInterestDTO.getAcNo() + "   Interest = " + rdInterestDTO.getInterest());
                    totalInt = totalInt + rdInterestDTO.getInterest();
                    rdProvision.add(rdProvisionInterestTable);
                    seqNo++;
                }

                this.setCrAmount(formatter.format(totalInt));
                this.setMessage("Interest successfully calculated.");
                String repName = "RD Interest Provision";
                CommonReportMethodsRemote reportFacade = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List branchList = reportFacade.getBranchNameandAddress(getOrgnBrCode());

                Map fillParams = new HashMap();
                fillParams.put("RpName", repName);
                fillParams.put("PntBy", getUserName());
                fillParams.put("RpDate", dmy.format(ymd.parse(fromDate)) + " to " + dmy.format(ymd.parse(toDate)));
                fillParams.put("pBankName", branchList.get(0));
                fillParams.put("pBankAdd", branchList.get(1));
                new ReportBean().jasperReport("RdIntCal", "text/html", new JRBeanCollectionDataSource(rdProvision), fillParams, repName);
                reportFlag = true;
                this.setViewID("/report/ReportPagePopUp.jsp");
            } else {
                reportFlag = false;
                this.setMessage("There is no account for interest calculation");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void calculationPost() {
        this.setMessage("");
        if (this.forTheMonth.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please select For The Month Of / Quater Ending Month / Half Year Ending Month.");
            return;
        }
        if (Double.parseDouble(this.crAmount) <= 0.0) {
            this.setMessage("Amount does not exist for posting.");
            return;
        }
        try {
            year = year.trim();
            int fyear = Integer.parseInt(year);
            String fromDate = "", toDate = "";
            if (interestOpt.equals("Q")) {
                fromDate = year + Months.getMonthValue(CbsUtil.getMonthName(Integer.parseInt(Months.getMonthValue(forTheMonth)) - 2).toUpperCase()) + "01";
                toDate = year + Months.getMonthValue(forTheMonth) + MonthEndDate.getMonthValue(forTheMonth);
            } else if (interestOpt.equals("M")) {
                fromDate = year + Months.getMonthValue(forTheMonth) + "01";
                String endDt = MonthEndDate.getMonthValue(forTheMonth);
                if (forTheMonth.equalsIgnoreCase("FEBRUARY")) {
                    String[] arr = endDt.split(":");
                    if ((fyear % 4) == 0) {
                        endDt = arr[1];
                    } else {
                        endDt = arr[0];
                    }
                }
                toDate = year + Months.getMonthValue(forTheMonth) + endDt;
            } else if (interestOpt.equals("H")) {
                if (this.forTheMonth.equalsIgnoreCase("MARCH")) {
                    fromDate = String.valueOf(Integer.parseInt(year) - 1) + Months.OCTOBER.getValue() + "01";
                } else {
                    fromDate = year + Months.getMonthValue(CbsUtil.getMonthName(Integer.parseInt(Months.getMonthValue(forTheMonth)) - 5).toUpperCase()) + "01";
                }
                toDate = year + Months.getMonthValue(forTheMonth) + MonthEndDate.getMonthValue(forTheMonth);
            }
            String calculation = provisionInterestCalRdRemote.interestPosting(acctCode, fromDate, toDate, getUserName(), getOrgnBrCode(), interestOpt);
            if (calculation.substring(0, 4).equalsIgnoreCase("true")) {
                this.setCrAmount("0.00");
                rdProvision = new ArrayList<RdProvisionInterestTable>();
                this.setMessage("Interest has been successfully posted. Batch No is:" + calculation.substring(4));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void clearText() {
        try {
            setForTheMonth("--SELECT--");
            setCrAmount("0.00");
            setAcToBeCredited("");
            setAcToBeDebited("");
            setMessage("");
            this.reportFlag = false;
            rdProvision = new ArrayList<RdProvisionInterestTable>();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String exitFrm() {
        try {
            clearText();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}
