/*
 * CREATED BY      :    ROHIT KRISHNA GUPTA
 */
package com.cbs.web.controller.intcal;

import com.cbs.constant.Months;
import com.cbs.dto.RdInterestDTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.intcal.QuarterlyIntCalGrid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class QuarterlyInterestCalculation extends BaseBean {

    private String errorMessage;
    private String message;
    private String acctType;
    private String acNoDr;
    private String acNoCr;
    private String crAmt;
    private String drAmt;
    private String intOpt;
    private Date quarEndDt;
    private String list;
    private boolean calcFlag;
    private List<QuarterlyIntCalGrid> gridDetail;
    private List<SelectItem> acctTypeList;
    private List<SelectItem> intOptionList;
    private String reportMonth;
    private String reportName;
    private final String quarterlyInterestCalJndiName = "RDIntCalFacade";
    private RDIntCalFacadeRemote quarterlyInterestCalculationRemote = null;
    private String viewID = "/pages/master/sm/test.jsp";

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public Date getQuarEndDt() {
        return quarEndDt;
    }

    public void setQuarEndDt(Date quarEndDt) {
        this.quarEndDt = quarEndDt;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAcNoCr() {
        return acNoCr;
    }

    public void setAcNoCr(String acNoCr) {
        this.acNoCr = acNoCr;
    }

    public String getAcNoDr() {
        return acNoDr;
    }

    public void setAcNoDr(String acNoDr) {
        this.acNoDr = acNoDr;
    }

    public String getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(String crAmt) {
        this.crAmt = crAmt;
    }

    public String getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(String drAmt) {
        this.drAmt = drAmt;
    }

    public List<SelectItem> getIntOptionList() {
        return intOptionList;
    }

    public void setIntOptionList(List<SelectItem> intOptionList) {
        this.intOptionList = intOptionList;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public List<QuarterlyIntCalGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<QuarterlyIntCalGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    /** Creates a new instance of QuarterlyInterestCalculation */
    public QuarterlyInterestCalculation() {
        try {
            quarterlyInterestCalculationRemote = (RDIntCalFacadeRemote) ServiceLocator.getInstance().lookup(quarterlyInterestCalJndiName);
            this.setErrorMessage("");
            this.setMessage("");
            this.setCalcFlag(false);

            getAcctTypeValues();

            intOptionList = new ArrayList<SelectItem>();
            intOptionList.add(new SelectItem("--Select--"));
            intOptionList.add(new SelectItem("Q", "Quarterly"));
            intOptionList.add(new SelectItem("H", "Half Yearly"));
            setQuarEndDt(new Date());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void getAcctTypeValues() {
        try {
            List resultList = new ArrayList();
            resultList = quarterlyInterestCalculationRemote.getAccountTypes();
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("--Select--"));

            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                acctTypeList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void acTypeLostFocus() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAcNoCr("");
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Type.");
                return;
            }
            List glHeadList = quarterlyInterestCalculationRemote.getInterestGLHeads(this.acctType);
            Vector ele = (Vector) glHeadList.get(0);
            this.setAcNoCr(ele.get(1).toString());
            this.setAcNoDr(ele.get(0).toString());
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    String finYear;

    public void getFinancialYear() {
        try {
            if (this.quarEndDt == null) {
                this.setErrorMessage("Please Enter Quarter Ending Date.");
                return;
            }
            List finYearList = new ArrayList();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            finYearList = quarterlyInterestCalculationRemote.getFinYear(this.tmpPreDate, ymd.format(this.quarEndDt), getOrgnBrCode());
            for (int i = 0; i < finYearList.size(); i++) {
                Vector ele = (Vector) finYearList.get(i);
                finYear = ele.get(0).toString();
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    String tmpPreDate;

    public void calculation() throws ParseException {
        //calcFlag = false;
        try {
            if (this.quarEndDt == null) {
                this.setErrorMessage("Please Enter Quarter Ending Date.");
                return;
            }
            this.setCalcFlag(false);
            this.setErrorMessage("");
            this.setMessage("");
            getFinancialYear();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String tdate = sdf.format(this.quarEndDt);

            Calendar cal = Calendar.getInstance();
            cal.setTime(quarEndDt);
            Integer maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            if (!this.intOpt.equalsIgnoreCase("Q")) {
                if (cal.get(Calendar.MONTH) != 8 && cal.get(Calendar.MONTH) != 2) {
                    this.setErrorMessage("Please, select proper month for half year end.");

                    // maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    // cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),maxDay);
                    // this.setQuarEndDt(cal.getTime());
                    return;
                } else {
                    maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    switch (cal.get(Calendar.MONTH)) {
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.SEPTEMBER), maxDay);
                            tmpPreDate = finYear + "0401";
                            break;
                        case 9:
                        case 10:
                        case 11:
                        case 0:
                        case 1:
                        case 2:
                            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MARCH), maxDay);
                            tmpPreDate = finYear + "1001";
                            break;
                    }
                    this.setQuarEndDt(cal.getTime());
                }
            } else {
                switch (cal.get(Calendar.MONTH)) {
                    case 0:
                    case 1:
                    case 2:
                        maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                        cal.set(cal.get(Calendar.YEAR), Calendar.MARCH, maxDay);
                        this.setQuarEndDt(cal.getTime());
                        tmpPreDate = tdate.substring(6) + "01" + "01";
                        break;
                    case 3:
                    case 4:
                    case 5:
                        maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                        cal.set(cal.get(Calendar.YEAR), Calendar.JUNE, maxDay);
                        this.setQuarEndDt(cal.getTime());
                        tmpPreDate = tdate.substring(6) + "04" + "01";
                        break;
                    case 6:
                    case 7:
                    case 8:
                        maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                        cal.set(cal.get(Calendar.YEAR), Calendar.SEPTEMBER, maxDay);
                        this.setQuarEndDt(cal.getTime());
                        tmpPreDate = tdate.substring(6) + "07" + "01";
                        break;
                    case 9:
                    case 10:
                    case 11:
                        maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                        cal.set(cal.get(Calendar.YEAR), Calendar.DECEMBER, maxDay);
                        this.setQuarEndDt(cal.getTime());
                        tmpPreDate = tdate.substring(6) + "10" + "01";
                        break;
                }
            }
            this.setErrorMessage("");
            this.setMessage("");
            this.setCrAmt("");
            this.setDrAmt("");
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Type.");
                return;
            }
            if (this.intOpt.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Interest Option.");
                return;
            }
            if (this.quarEndDt == null) {
                this.setErrorMessage("Please Enter Quarter Ending Date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            gridDetail = new ArrayList<QuarterlyIntCalGrid>();

            List<RdInterestDTO> rdInterestDTOs = quarterlyInterestCalculationRemote.quarterlyRdIntCal(getOrgnBrCode(), this.acctType, this.tmpPreDate, ymd.format(this.quarEndDt), intOpt);
            QuarterlyIntCalGrid detail;
            int seqNo = 1;
            double totalInt = 0d;
            for (RdInterestDTO rdInterestDTO : rdInterestDTOs) {
                detail = new QuarterlyIntCalGrid();
                detail.setsNo(seqNo);
                detail.setAcno(rdInterestDTO.getAcNo());
                detail.setCustName(rdInterestDTO.getCustName());

                detail.setStDate(rdInterestDTO.getOpeningDt());
                detail.setInstalment(new BigDecimal(rdInterestDTO.getInstallment()));
                detail.setRoi(new BigDecimal(rdInterestDTO.getRoi()));

                detail.setBalance(new BigDecimal(rdInterestDTO.getBalance()));
                detail.setInterest(new BigDecimal(rdInterestDTO.getInterest()));
                System.out.println("Account No = " + rdInterestDTO.getAcNo() + "   Interest = " + rdInterestDTO.getInterest());
                totalInt = totalInt + rdInterestDTO.getInterest();
                gridDetail.add(detail);
                seqNo++;
            }
            this.setCrAmt(formatter.format(totalInt));
            this.setDrAmt(formatter.format(totalInt));
            this.setMessage("Interest successfully calculated.");

            this.setCalcFlag(true);

            String repName;
            String report = "RD Quarterly Interest Calculation Report";
            Map fillParams = new HashMap();
            String month = Months.getMonthName(CbsUtil.lPadding(2, cal.get(Calendar.MONTH) + 1));
            if (intOpt.equalsIgnoreCase("Q")) {
                repName = "RD Quarterly Int. Cal. Report For " + month + "-" + cal.get(Calendar.YEAR);
                reportName = repName;

            } else {
                repName = "RD Half Yearly Int. Cal. Report For " + month + "-" + cal.get(Calendar.YEAR);
                reportName = repName;
            }
            CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List vec = beanLocal.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", repName);
            fillParams.put("pBankName", vec.get(0).toString());
            fillParams.put("pBankAdd", vec.get(1).toString());
            new ReportBean().jasperReport("RdQuaterlyInterest", "text/html", new JRBeanCollectionDataSource(gridDetail), fillParams, report);
            this.setViewID("/report/ReportPagePopUp.jsp");
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void postInterest() {
        try {
            if (calcFlag == false) {
                this.setErrorMessage("Please Calculate First.");
                return;
            }
            if (this.quarEndDt == null) {
                this.setErrorMessage("Please Enter Quarter Ending Date.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String result = quarterlyInterestCalculationRemote.postInterest(getUserName(), acctType, ymd.format(this.quarEndDt), this.intOpt, this.tmpPreDate, getOrgnBrCode());
            if (result.equalsIgnoreCase("Interest has been successfully posted.")) {
                this.setMessage("Interest has been successfully posted.");
            } else {
                this.setErrorMessage(result);
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setAcctType("--Select--");
            this.setIntOpt("--Select--");

            this.setQuarEndDt(quarEndDt);
            this.setAcNoCr("");
            this.setAcNoDr("");

            this.setCrAmt("");
            this.setDrAmt("");
            Date date = new Date();
            this.setQuarEndDt(date);
            getAcctTypeValues();
            gridDetail = new ArrayList<QuarterlyIntCalGrid>();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
