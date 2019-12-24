package com.cbs.web.controller.intcal;

import com.cbs.dto.TempProjIntDetail;
import com.cbs.dto.YearEndDatePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.TDInterestCalulationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.intcal.ProjectedIntCalGrid;
import com.cbs.web.pojo.intcal.TdInterestCalGrid;
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
public class ProjectedInterestCalculation extends BaseBean {

    private String errorMessage;
    private String message;
    private String intOpt;
    private String acctType;
    private String fDate;
    private String tmpNat;
    private Date tillDate;
    private List<SelectItem> acctTypeList;
    private List<SelectItem> intOptionList;
    //private HttpServletRequest req;
    private List<ProjectedIntCalGrid> gridDetail;
    private final String projectedIntCalcJndiName = "TDInterestCalulationFacade";
    private TDInterestCalulationFacadeRemote projectIntCalcRemote = null;
    private final String commonJndiName = "CommonReportMethods";
    private CommonReportMethodsRemote commonReportMethodsRemote = null;
    private final String rbiJndiName = "RbiReportFacade";
    private RbiReportFacadeRemote rbiReportFacadeRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    String brnCode;
    List<SelectItem> brnList;

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public List<SelectItem> getBrnList() {
        return brnList;
    }

    public void setBrnList(List<SelectItem> brnList) {
        this.brnList = brnList;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

    public String getTmpNat() {
        return tmpNat;
    }

    public void setTmpNat(String tmpNat) {
        this.tmpNat = tmpNat;
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
//
//    public HttpServletRequest getReq() {
//        return req;
//    }
//
//    public void setReq(HttpServletRequest req) {
//        this.req = req;
//    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public List<SelectItem> getIntOptionList() {
        return intOptionList;
    }

    public void setIntOptionList(List<SelectItem> intOptionList) {
        this.intOptionList = intOptionList;
    }

    public List<ProjectedIntCalGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<ProjectedIntCalGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

    /**
     * Creates a new instance of ProjectedInterestCalculation
     */
    public ProjectedInterestCalculation() {
        try {
            projectIntCalcRemote = (TDInterestCalulationFacadeRemote) ServiceLocator.getInstance().lookup(projectedIntCalcJndiName);
            commonReportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonJndiName);
            rbiReportFacadeRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup(rbiJndiName);
            this.setErrorMessage("");
            this.setMessage("");
            intOptionList = new ArrayList<>();
            getAcctTypeValues();
            finYear();
            intOptionList.add(new SelectItem("--Select--"));
            List brncode = new ArrayList();
            if (this.getOrgnBrCode().equalsIgnoreCase("90") || this.getOrgnBrCode().equalsIgnoreCase("0A")) {
                brncode = commonReportMethodsRemote.getAlphacodeExcludingHo();
            } else {
                brncode = commonReportMethodsRemote.getBranchCodeList(this.getOrgnBrCode());
            }
            brnList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    brnList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            setTillDate(new Date());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void getAcctTypeValues() {
        try {
            List resultList = new ArrayList();
            resultList = projectIntCalcRemote.acctTypeLoad();
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                acctTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void setdescription() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            intOptionList = new ArrayList<>();
            intOptionList.add(new SelectItem("--Select--"));
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Account Type !!!");
                return;
            }
            List tempList = projectIntCalcRemote.setGLAcNoAndInterestOption(this.acctType);

            Vector ele = (Vector) tempList.get(0);
            String intopt = ele.get(3).toString();
            for (int i = 0; i < intopt.length(); i++) {
                if (intopt.charAt(i) == 'C') {
                    intOptionList.add(new SelectItem("CUMULATIVE"));
                }
                if (intopt.charAt(i) == 'Q') {
                    intOptionList.add(new SelectItem("QUARTERLY"));
                }
                if (intopt.charAt(i) == 'M') {
                    intOptionList.add(new SelectItem("MONTHLY"));
                }
                if (intopt.charAt(i) == 'S') {
                    intOptionList.add(new SelectItem("SIMPLE"));
                }
                if (intopt.charAt(i) == 'Y') {
                    intOptionList.add(new SelectItem("YEARLY"));
                }
            }

        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void finYear() {
        try {
            List resultList = new ArrayList();
            resultList = projectIntCalcRemote.finYear(getOrgnBrCode());
            Vector ele = (Vector) resultList.get(0);
            String finYear = ele.get(0).toString();
            fDate = "01/04/" + finYear;
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void acNat() {
        try {
            List resultList = new ArrayList();
            resultList = projectIntCalcRemote.acNat(this.acctType);
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                tmpNat = ele.get(1).toString();
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void calculation() {
        String bankName = "", bankAddress = "";
        this.setErrorMessage("");
        this.setMessage("");
        if (this.acctType.equalsIgnoreCase("--Select--")) {
            this.setErrorMessage("Please select Account Type.");
            return;
        }
        if (this.intOpt.equalsIgnoreCase("--Select--")) {
            this.setErrorMessage("Please select Interest Option.");
            return;
        }
        String tmpDate = ymd.format(this.tillDate);
        try {
            gridDetail = new ArrayList<>();
            List<TempProjIntDetail> tempProjIntDetail = projectIntCalcRemote.projectedIntCalTD(this.intOpt.substring(0, 1), this.acctType, brnCode, tmpDate);
            if (tempProjIntDetail.size() > 0) {
                for (int i = 0; i < tempProjIntDetail.size(); i++) {
                    TempProjIntDetail tableObj = tempProjIntDetail.get(i);
                    if (tableObj.getMsg().equalsIgnoreCase("true")) {
                        ProjectedIntCalGrid detail = new ProjectedIntCalGrid();
                        detail.setsNo(i + 1);
                        detail.setAcNo(tableObj.getAcno());
                        detail.setCustName(tableObj.getCustname());
                        detail.setfAmt(tableObj.getfAmt());
                        detail.setVoucherNo(tableObj.getVoucherNo());
                        detail.setInt1(tableObj.getInt1());
                        detail.setInt2(tableObj.getInt2());
                        detail.setInt3(tableObj.getInt3());
                        detail.setTds1(tableObj.getTds1());
                        detail.setTd2(tableObj.getTd2());
                        gridDetail.add(detail);
                    } else {
                        this.setErrorMessage(tableObj.getMsg());
                        return;
                    }
                }
            } else {
                this.setErrorMessage("Data does not exist");
                return;
            }
            if (!(brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90"))) {
                List dataList1 = commonReportMethodsRemote.getBranchNameandAddress(brnCode);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = commonReportMethodsRemote.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }
            String reportName = "Projected Interest Calculation";
            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAdd", bankAddress);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", reportName);
            YearEndDatePojo yDate = new YearEndDatePojo();
            yDate = (YearEndDatePojo) rbiReportFacadeRemote.getYearEndDataAccordingToDate(getOrgnBrCode().equalsIgnoreCase("0A") ? "90" : getOrgnBrCode(), ymd.format(new Date()));
            fillParams.put("pReportToDate", dmy.format(ymd.parse(yDate.getMinDate())) + " To " + dmy.format(this.tillDate));

            new ReportBean().jasperReport("TdIntCalProjected", "text/html", new JRBeanCollectionDataSource(gridDetail), fillParams, reportName);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", reportName);
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void resetForm() {
        try {
            this.setAcctType("--Select--");
            this.setIntOpt("--Select--");
            this.setErrorMessage("");
            this.setMessage("");
            gridDetail = new ArrayList<ProjectedIntCalGrid>();
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public String exitFrm() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
        return "case1";
    }
}
