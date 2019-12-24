/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareDetailsReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class ShareDetails extends BaseBean implements Serializable {

    private String errorMsg, branch, status;
    private ShareReportFacadeRemote horfr = null;
    private CommonReportMethodsRemote commonRemote = null;
    private CertIssueFacadeRemote certRemote = null;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private Date frDt, toDt;
    List<SelectItem> branchList = new ArrayList<SelectItem>();
    private String orderBy;
    List<SelectItem> orderByList = new ArrayList<SelectItem>();
    private String reportType;
    List<SelectItem> reportTypeList;
    private String button;

    /**
     * Creates a new instance of ShareDetails
     */
    public ShareDetails() {
        try {
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            certRemote = (CertIssueFacadeRemote) ServiceLocator.getInstance().lookup("CertIssueFacade");

            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("--Select--"));
            orderByList.add(new SelectItem("Folio No", "Folio No"));
            orderByList.add(new SelectItem("Cert No", "Cert No"));
            orderByList.add(new SelectItem("Issue Date", "Issue Date"));
            orderByList.add(new SelectItem("Name", "Name"));

            reportTypeList = new ArrayList<>();
            reportTypeList.add(new SelectItem("PDF"));
            reportTypeList.add(new SelectItem("Excel"));

            setButton("PDF Download");

            init();
        } catch (Exception e) {
        }
    }

    private void init() {
        try {
            List result = certRemote.getBranchCode();
            Vector vtr;
            branchList.add(new SelectItem("ALL", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                branchList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
            }
        } catch (ApplicationException ex) {
            Logger.getLogger(ShareDetails.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void onReportTypeAction() {
        setErrorMsg("");

        if (reportType.equalsIgnoreCase("PDF")) {
            setButton("PDF Download");
        } else {
            setButton("Excel Download");
        }
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getFrDt() {
        return frDt;
    }

    public void setFrDt(Date frDt) {
        this.frDt = frDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public String viewReport() {

        if (validate()) {
            String conditionQuery = "";
            if (status.equalsIgnoreCase("C")) {
                conditionQuery = "";
            } else {
                conditionQuery = "and cs.issuedt  between '" + ymd.format(frDt) + "' and '" + ymd.format(toDt) + "'";
            }
            String query = "select foliono,cc.CustFullName,cc.perAddressLine1, ifnull(cc.perAddressLine2,''),ifnull(perVillage,''), ifnull(perBlock,''),"
                    + "ifnull(percitycode,''),min(shareNo),max(shareno),count(shareno), sharecertno,status,cs.Issuedt, alphaCode, "
                    + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,"
                    + "concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.customerid  "
                    + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid "
                    + "and cs.certificateno =sc.sharecertno and sc.foliono=sh.regfoliono " + conditionQuery + "";
            try {
                if (!branch.equalsIgnoreCase("ALL")) {
                    query = query + "and alphacode='" + branch + "'";
                }
                if (!status.equalsIgnoreCase("ALL")) {
                    if (status.equalsIgnoreCase("A")) {
                        query = query + " and (cs.paymentdt is null or cs.paymentdt ='1900-01-01 00:00:00' or cs.paymentdt ='' or cs.paymentdt > '" + ymd.format(toDt) + "')";
                    } else {
                        query = query + " and (cs.paymentdt <= '" + ymd.format(toDt) + "' and cs.paymentdt <> '1900-01-01 00:00:00')";
                    }
                }
                query = query + " group by foliono,cc.CustFullName,cc.perAddressLine1,cc.perAddressLine2, perVillage,perBlock,percitycode,"
                        + "shareCertno,cs.issueDt,status,paymentdt,alphacode";

                if (orderBy.equalsIgnoreCase("Folio No")) {
                    query = query + " order by foliono";
                } else if (orderBy.equalsIgnoreCase("Cert No")) {
                    query = query + " order by shareCertno";
                } else if (orderBy.equalsIgnoreCase("Issue Date")) {
                    query = query + " order by cs.issuedt";
                } else if (orderBy.equalsIgnoreCase("Name")) {
                    query = query + " order by fname,lname";
                }
                
                List<ShareDetailsReportPojo> resultList = horfr.getShareDetailsReport(query, ymd.format(toDt));
                if (!resultList.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", dmy.format(frDt) + " to " + dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Share Details Report");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    new ReportBean().jasperReport("ShareDetailsReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Share Details Report");
                    return "report";
                } else {
                    setErrorMsg("Data does not exist !");
                }
            } catch (Exception e) {
                setErrorMsg(e.getMessage());
            }
        }
        return null;
    }

    public String pdfDownLoad() {
        if (validate()) {
            String conditionQuery = "";
            if (status.equalsIgnoreCase("C")) {
                conditionQuery = "";
            } else {
                conditionQuery = "and cs.issuedt  between '" + ymd.format(frDt) + "' and '" + ymd.format(toDt) + "'";
            }
            String query = "select foliono,cc.CustFullName,cc.perAddressLine1, ifnull(cc.perAddressLine2,''),ifnull(perVillage,''), ifnull(perBlock,''),"
                    + "ifnull(percitycode,''),min(shareNo),max(shareno),count(shareno), sharecertno,status,cs.Issuedt, alphaCode, "
                    + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,"
                    + "concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.customerid "
                    + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid "
                    + "and cs.certificateno =sc.sharecertno and sc.foliono=sh.regfoliono " + conditionQuery + "";
            try {
                if (!branch.equalsIgnoreCase("ALL")) {
                    query = query + "and alphacode='" + branch + "'";
                }
                if (!status.equalsIgnoreCase("ALL")) {
                    query = query + " and (cs.paymentdt is null or cs.paymentdt ='' or cs.paymentdt > '" + ymd.format(toDt) + "')";
                }
                query = query + " group by foliono,cc.CustFullName,cc.perAddressLine1,cc.perAddressLine2, perVillage,perBlock,percitycode,"
                        + "shareCertno,cs.issueDt,status,paymentdt,alphacode";

                if (orderBy.equalsIgnoreCase("Folio No")) {
                    query = query + " order by foliono";
                } else if (orderBy.equalsIgnoreCase("Cert No")) {
                    query = query + " order by shareCertno";
                } else if (orderBy.equalsIgnoreCase("Issue Date")) {
                    query = query + " order by cs.issuedt";
                } else if (orderBy.equalsIgnoreCase("Name")) {
                    query = query + " order by fname,lname";
                }

                List<ShareDetailsReportPojo> resultList = horfr.getShareDetailsReport(query, ymd.format(toDt));
                if (!resultList.isEmpty()) {
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", dmy.format(frDt) + " to " + dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Share Details Report");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);
                    if (reportType.equalsIgnoreCase("Excel")) {
                        new ReportBean().dynamicExcelJasper("ShareDetailsReport", "excel", new JRBeanCollectionDataSource(resultList), fillParams, "Share Details Report");
                    } else {
                        new ReportBean().openPdf("Share Details Report", "ShareDetailsReport", new JRBeanCollectionDataSource(resultList), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", "Share Details Report");
                    }
                } else {
                    setErrorMsg("Data does not exist !");
                }
            } catch (Exception e) {
                setErrorMsg(e.getMessage());
            }
        }
        return null;
    }

    public void refreshPage() {
        setErrorMsg("");
        setBranch("ALL");
        setStatus("ALL");
        setOrderBy("--Select--");
        setButton("PDF Download");

    }

    private boolean validate() {
        if (orderBy.equalsIgnoreCase("--Select--")) {
            errorMsg = "Please Select Order By !";
            return false;
        }
        if (frDt == null) {
            errorMsg = "Please enter valid from date";
            return false;
        } else if (toDt == null) {
            errorMsg = "Please enter valid to date";
            return false;
        } else if (frDt.after(toDt)) {
            errorMsg = "Please enter from date should be less than to to date";
            return false;
        }
        return true;
    }
}
