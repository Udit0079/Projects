/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.FormNo15gPart1Pojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.other.Form15jrxmlPojo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class FormNo15gPart1 extends BaseBean {

    private String message;
    private String custId;
    private String newCustId;
    private String uniNo;
    private List<SelectItem> uniNoList;
    private String finYear;
    private String newFinYear;
    private String repType;
    private List<SelectItem> repList;
    private String newReport;
    private String optionType;
    private List<SelectItem> optionTypeList;
    private String estimatedTotalIncome;
    private String totalNoForm;
    private String aggregateAmount;
    private boolean incomeTaxChecking;
    private String assYear;
    private String newAssessmentYear;
    private boolean disableAssYear;
    private String fYear;
    private MiscReportFacadeRemote remoteFacade;
    private CommonReportMethodsRemote common;
    TermDepositMasterFacadeRemote tdRemote;
    List<FormNo15gPart1Pojo> dataList = new ArrayList<FormNo15gPart1Pojo>();
    List<FormNo15gPart1Pojo> dataList1 = new ArrayList<FormNo15gPart1Pojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public FormNo15gPart1() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            tdRemote = (TermDepositMasterFacadeRemote) ServiceLocator.getInstance().lookup("TermDepositMasterFacade");
            this.setMessage("Please fill customer Id !");
            fYearDropDownData();

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void fYearDropDownData() {
        try {
            List fYearList = new ArrayList();
            fYearList = tdRemote.fYearData(getOrgnBrCode());
            for (int i = 0; i < fYearList.size(); i++) {
                Vector ele = (Vector) fYearList.get(i);
                this.setFinYear(ele.get(0).toString());
            }
            this.fYear = getFinYear();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void assessmentControl() {
        setMessage("");
        if (incomeTaxChecking == true) {
            this.disableAssYear = false;
        } else {
            this.disableAssYear = true;
        }
    }

    public void getNewAssYear() {
        this.newAssessmentYear = assYear;

    }

    public void getReportType() {
        this.newReport = repType;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public void getNewcId() {
        setMessage("");
        this.newCustId = custId;
    }

    public void getNewfYear() {
        setMessage("");
        this.newFinYear = finYear;
        try {
            String tdsTable = "";
            if (fYear.equalsIgnoreCase(finYear)) {
                tdsTable = "tds_docdetail";
            } else {
                tdsTable = "tds_docdetail_his";
            }
            List docList = common.getDocDetail(custId, finYear, tdsTable);
            if (docList.isEmpty()) {
                throw new ApplicationException("This Id may be 	Previous Financial Year!");
            }
            Vector vtr = (Vector) docList.get(0);
            this.repType = vtr.get(0).toString();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepList() {
        return repList;
    }

    public void setRepList(List<SelectItem> repList) {
        this.repList = repList;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public List<SelectItem> getOptionTypeList() {
        return optionTypeList;
    }

    public void setOptionTypeList(List<SelectItem> optionTypeList) {
        this.optionTypeList = optionTypeList;
    }

    public String getNewReport() {
        return newReport;
    }

    public void setNewReport(String newReport) {
        this.newReport = newReport;
    }

    public String getEstimatedTotalIncome() {
        return estimatedTotalIncome;
    }

    public void setEstimatedTotalIncome(String estimatedTotalIncome) {
        this.estimatedTotalIncome = estimatedTotalIncome;
    }

    public String getTotalNoForm() {
        return totalNoForm;
    }

    public void setTotalNoForm(String totalNoForm) {
        this.totalNoForm = totalNoForm;
    }

    public String getAggregateAmount() {
        return aggregateAmount;
    }

    public void setAggregateAmount(String aggregateAmount) {
        this.aggregateAmount = aggregateAmount;
    }

    public boolean isIncomeTaxChecking() {
        return incomeTaxChecking;
    }

    public void setIncomeTaxChecking(boolean incomeTaxChecking) {
        this.incomeTaxChecking = incomeTaxChecking;
    }

    public String getAssYear() {
        return assYear;
    }

    public void setAssYear(String assYear) {
        this.assYear = assYear;
    }

    public boolean isDisableAssYear() {
        return disableAssYear;
    }

    public void setDisableAssYear(boolean disableAssYear) {
        this.disableAssYear = disableAssYear;
    }

    public String getNewAssessmentYear() {
        return newAssessmentYear;
    }

    public void setNewAssessmentYear(String newAssessmentYear) {
        this.newAssessmentYear = newAssessmentYear;
    }

    public String getUniNo() {
        return uniNo;
    }

    public void setUniNo(String uniNo) {
        this.uniNo = uniNo;
    }

    public List<SelectItem> getUniNoList() {
        return uniNoList;
    }

    public void setUniNoList(List<SelectItem> uniNoList) {
        this.uniNoList = uniNoList;
    }

    public void viewPdfReport() {
        setMessage("");
        String report = "FORM NO. 15G";
        try {
            if (custId == null || custId.equalsIgnoreCase("")) {
                setMessage("Please fill proper cust Id !");
                return;
            }
            if (finYear == null || finYear.equalsIgnoreCase("")) {
                setMessage("Please fill proper 4 digits financial year !");
                return;
            }

            if (repType.equalsIgnoreCase("S")) {
                setMessage("Please select Report Type !");
                return;
            }

//            if (estimatedTotalIncome.equalsIgnoreCase("")) {
//                estimatedTotalIncome = "0";
//            }
//
//            if (totalNoForm.equalsIgnoreCase("")) {
//                totalNoForm = "0";
//            }
//
//            if (aggregateAmount.equalsIgnoreCase("")) {
//                aggregateAmount = "0";
//            }
//            if (incomeTaxChecking == true) {
//                if (assYear == null || assYear.equalsIgnoreCase("")) {
//                    setMessage("Please fill proper 4 digits Latest assessment year !");
//                    return;
//                }
//            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportType", repType);

            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");

            List<Form15jrxmlPojo> jrxmlList = new ArrayList<Form15jrxmlPojo>();
            Form15jrxmlPojo jrxmlPojo = new Form15jrxmlPojo();

            dataList = remoteFacade.getForm15gPart1Data(getOrgnBrCode(), custId, finYear, repType, "PART I", estimatedTotalIncome, totalNoForm, aggregateAmount, incomeTaxChecking, assYear);
            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exist !!!");
            } else {
                jrxmlPojo.setPartI(new JRBeanCollectionDataSource(dataList));
            }
            //fillParams.put("pNewPage", "PART II");
            dataList1 = remoteFacade.getForm15gPart1Data(getOrgnBrCode(), custId, finYear, repType, "PART II", estimatedTotalIncome, totalNoForm, aggregateAmount, incomeTaxChecking, assYear);
            if (dataList1.isEmpty()) {
                throw new ApplicationException("Data does not exist !!!");
            } else {
                jrxmlPojo.setPartII(new JRBeanCollectionDataSource(dataList1));
            }
            jrxmlList.add(jrxmlPojo);

            new ReportBean().downloadPdf(repType + "_" + ymd.format(dmy.parse(getTodayDate())), "Form15GPatrtI_15HPartII", new JRBeanCollectionDataSource(jrxmlList), fillParams);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewPdfReport1() {
        setMessage("");
        String report = "FORM NO. 15G";
        try {
            if (custId == null || custId.equalsIgnoreCase("")) {
                setMessage("Please fill proper cust Id !");
                return;
            }
            if (finYear == null || finYear.equalsIgnoreCase("")) {
                setMessage("Please fill proper 4 digits financial year !");
                return;
            }
            String hColumn = "";
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportType", repType);

            if (repType.equalsIgnoreCase("Form 15H")) {
                fillParams.put("pHcolumn", "5.Date of Birth");
            } else {
                fillParams.put("pHcolumn", "5.Residential Status");
            }

            dataList = remoteFacade.getForm15gPart1Data(getOrgnBrCode(), custId, finYear, repType, optionType, estimatedTotalIncome, totalNoForm, aggregateAmount, incomeTaxChecking, assYear);

            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exist !!!");
            }

            if (optionType.equalsIgnoreCase("PART I")) {
                new ReportBean().openPdf("Form15G Part 1", "FormNo15G_Test", new JRBeanCollectionDataSource(dataList), fillParams);
            } else {
                new ReportBean().openPdf("Form15G Part 1", "FormNo15G_15H_PartII", new JRBeanCollectionDataSource(dataList), fillParams);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshPage() {
        setMessage("");
        setCustId("");
        setNewCustId("");
        // setFinYear("");
        setNewFinYear("");
        setRepType("");
        setNewReport("");
        this.disableAssYear = true;
        this.incomeTaxChecking = false;
        this.assYear = "";
        this.newAssessmentYear = "";
        this.estimatedTotalIncome = "";
        this.totalNoForm = "";
        this.aggregateAmount = "";
        uniNoList = new ArrayList<SelectItem>();

    }

    public String exitPage() {
        refreshPage();
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getNewCustId() {
        return newCustId;
    }

    public void setNewCustId(String newCustId) {
        this.newCustId = newCustId;
    }

    public String getNewFinYear() {
        return newFinYear;
    }

    public void setNewFinYear(String newFinYear) {
        this.newFinYear = newFinYear;
    }
}
