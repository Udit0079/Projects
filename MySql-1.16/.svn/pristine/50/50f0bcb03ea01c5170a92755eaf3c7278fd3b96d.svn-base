package com.cbs.web.controller.report.other;

import com.cbs.dto.report.LienReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class DepositLienReport extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String errorMsg;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String asOnDate;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private final String jndiHomeNameLoanReportFacade = "LoanReportFacade";
    private LoanReportFacadeRemote loanRemote = null;
    List<LienReportPojo> resultlist = new ArrayList<LienReportPojo>();
    List acList = null;
    Vector acVector = null;
    List tempList = null;
    Vector tempVector = null;

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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

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

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public CommonReportMethodsRemote getCommon() {
        return common;
    }

    public void setCommon(CommonReportMethodsRemote common) {
        this.common = common;
    }

    public LoanReportFacadeRemote getLoanRemote() {
        return loanRemote;
    }

    public void setLoanRemote(LoanReportFacadeRemote loanRemote) {
        this.loanRemote = loanRemote;
    }

    public List<LienReportPojo> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<LienReportPojo> resultlist) {
        this.resultlist = resultlist;
    }

    public List getAcList() {
        return acList;
    }

    public void setAcList(List acList) {
        this.acList = acList;
    }

    public Vector getAcVector() {
        return acVector;
    }

    public void setAcVector(Vector acVector) {
        this.acVector = acVector;
    }

    public List getTempList() {
        return tempList;
    }

    public void setTempList(List tempList) {
        this.tempList = tempList;
    }

    public Vector getTempVector() {
        return tempVector;
    }

    public void setTempVector(Vector tempVector) {
        this.tempVector = tempVector;
    }

    public DepositLienReport() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameLoanReportFacade);
            asOnDate = dmy.format(date);

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            blurAcctNature();
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void blurAcctNature() {
        try {
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("ALL"));
            tempList = loanRemote.depositAcType();
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                acctTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        String branchName = "";
        String address = "";
        try {
            String ReportName = "Deposit Lien Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            LienReportPojo pojo = new LienReportPojo();
            List resultList = new ArrayList();
            Map fillParams = new HashMap();
            fillParams.put("pReportName", ReportName);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pBranchName", branchName);
            fillParams.put("pBranchAdd",address);
            fillParams.put("pTodayDate", getTodayDate());
            fillParams.put("pPrintedDate",asOnDate);
            resultList = loanRemote.getDepositLienReport(acctType, asOnDate, branchCode);
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exists !");
            }
            new ReportBean().jasperReport("Deposit_Lien_Report", "text/html",
                    new JRBeanCollectionDataSource(resultList), fillParams, "Deposit Lien Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", ReportName);
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void pdfDownLoad() {
        String branchName = "";
        String address = "";
        try {
            String ReportName = "Deposit Lien Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            LienReportPojo pojo = new LienReportPojo();
            List resultList = new ArrayList();
            Map fillParams = new HashMap();
            fillParams.put("pReportName", ReportName);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pBranchName", branchName);
            fillParams.put("pBranchAdd", address);
            fillParams.put("pTodayDate", getTodayDate());
            fillParams.put("pPrintedDate",asOnDate);
            resultList = loanRemote.getDepositLienReport(acctType, asOnDate, branchCode);
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exists !");
            }
            new ReportBean().openPdf("Deposit Lien Report" + ymd.format(dmy.parse(getTodayDate())), "Deposit_Lien_Report", new JRBeanCollectionDataSource(resultList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", ReportName);
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        setBranchCode("0");
        asOnDate = dmy.format(new Date());
        setAcctType("0");
    }

    public String exitAction() {
        return "case1";
    }
}
