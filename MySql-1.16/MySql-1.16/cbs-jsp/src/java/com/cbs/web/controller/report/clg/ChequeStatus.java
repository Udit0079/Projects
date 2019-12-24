/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.clg;

import com.cbs.dto.report.CtsChequeStatusReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsReportManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ChequeStatus extends BaseBean {

    Date dt = new Date();
    private String status;
    private String reportDt;
    private String message;
    private List<SelectItem> statusList;
    private final String jndiHomeName = "CtsReportManagementFacade";
    private CtsReportManagementFacadeRemote remoteInterface = null;
    List<CtsChequeStatusReportPojo> resultList = new ArrayList<CtsChequeStatusReportPojo>();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private TdReceiptManagementFacadeRemote beanFacade;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private int ctsSponsor = 0;
    private String clgType;
    private List<SelectItem> clgTypeList;
    private String npciCtsDisplayFlag = "none";
    private String normalCtsDisplayFlag = "none";
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private CommonReportMethodsRemote reportRemote;

    public ChequeStatus() {
        try {
            remoteInterface = (CtsReportManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "ALL"));
            statusList.add(new SelectItem("1", "ENTERED"));
            statusList.add(new SelectItem("2", "PASSED"));
            statusList.add(new SelectItem("3", "HOLD"));
            statusList.add(new SelectItem("4", "RETURNED"));

            clgTypeList = new ArrayList<SelectItem>();

            this.setReportDt(sdf.format(dt));
            ctsSponsor = ftsRemote.getCodeForReportName("CTS-SPONSOR");
            if (ctsSponsor == 2) {
                List clgList = reportRemote.getRefRecList("016");
                if (clgList.isEmpty()) {
                    this.setMessage("Please define clearing type in Cbs Ref Rec Type.");
                    return;
                }
                for (int x = 0; x < clgList.size(); x++) {
                    Vector clgVec = (Vector) clgList.get(x);
                    clgTypeList.add(new SelectItem(clgVec.get(0).toString(), clgVec.get(1).toString()));
                }
                this.npciCtsDisplayFlag = "";
            } else {
                this.normalCtsDisplayFlag = "";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(ChequeStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        boolean validateResult = validateField();
        if (validateResult == false) {
            return;
        }
        String report = "CTS Report";
        if (ctsSponsor == 2 && this.clgType.equalsIgnoreCase("11")) { //Non-CTS
            report = "Non-CTS Report";
        }
        String reportDate = this.getReportDt().substring(6) + this.getReportDt().substring(3, 5) + this.getReportDt().substring(0, 2);
        Map fillParams = new HashMap();
        try {
            fillParams.put("printedBy", getUserName());
            fillParams.put("repoertDt", this.getReportDt());
            fillParams.put("reportName", report);

            int brLength = this.branchCode.trim().length();
            if (brLength < 2) {
                branchCode = "0" + branchCode.trim();
            }

            resultList = remoteInterface.getCtsChequeStatusReportDetails(this.branchCode, reportDate, Integer.parseInt(this.getStatus()), this.clgType, ctsSponsor);
            new ReportBean().jasperReport("CtsReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "CTS Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        this.setMessage("");
        boolean validateResult = validateField();
        if (validateResult == false) {
            return;
        }
        String report = "CTS Report";
        if (ctsSponsor == 2 && this.clgType.equalsIgnoreCase("11")) {
            report = "Non-CTS Report";
        }
        String reportDate = this.getReportDt().substring(6) + this.getReportDt().substring(3, 5) + this.getReportDt().substring(0, 2);
        Map fillParams = new HashMap();
        try {
            fillParams.put("printedBy", getUserName());
            fillParams.put("repoertDt", this.getReportDt());
            fillParams.put("reportName", report);

            int brLength = this.branchCode.trim().length();
            if (brLength < 2) {
                branchCode = "0" + branchCode.trim();
            }

            resultList = remoteInterface.getCtsChequeStatusReportDetails(this.branchCode, reportDate, Integer.parseInt(this.getStatus()), this.clgType, ctsSponsor);

            new ReportBean().openPdf("CTS Report_" + ymd.format(sdf.parse(getTodayDate())), "CtsReport", new JRBeanCollectionDataSource(resultList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        try {
            if (branchCode == null || branchCode.equals("")) {
                this.setMessage("Please select Branch.");
                return false;
            }
            if (ctsSponsor == 2 && (this.clgType == null || this.clgType.equals(""))) {
                this.setMessage("Please select Clearing Type.");
                return false;
            }
            if (status == null || status.equals("")) {
                this.setMessage("Please select Status.");
                return false;
            }
            if (reportDt == null || reportDt.equals("")) {
                this.setMessage("Please fill Date.");
                return false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void btnRefresh() {
        this.setMessage("");
        this.setReportDt(sdf.format(dt));
    }

    public String btnExitAction() {
        return "case1";
    }
    //Getter And Setter

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCtsSponsor() {
        return ctsSponsor;
    }

    public void setCtsSponsor(int ctsSponsor) {
        this.ctsSponsor = ctsSponsor;
    }

    public String getClgType() {
        return clgType;
    }

    public void setClgType(String clgType) {
        this.clgType = clgType;
    }

    public List<SelectItem> getClgTypeList() {
        return clgTypeList;
    }

    public void setClgTypeList(List<SelectItem> clgTypeList) {
        this.clgTypeList = clgTypeList;
    }

    public String getNpciCtsDisplayFlag() {
        return npciCtsDisplayFlag;
    }

    public void setNpciCtsDisplayFlag(String npciCtsDisplayFlag) {
        this.npciCtsDisplayFlag = npciCtsDisplayFlag;
    }

    public String getNormalCtsDisplayFlag() {
        return normalCtsDisplayFlag;
    }

    public void setNormalCtsDisplayFlag(String normalCtsDisplayFlag) {
        this.normalCtsDisplayFlag = normalCtsDisplayFlag;
    }
}
