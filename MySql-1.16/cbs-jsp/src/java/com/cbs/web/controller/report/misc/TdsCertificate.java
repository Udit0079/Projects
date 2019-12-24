/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.Td15HEntryCertificationPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class TdsCertificate extends BaseBean {

    private String message;
    private String acno;
    private String newAcno;
    private String finYear;
    private String newFinYear;
    private String repType;
    private String field;
    private List<SelectItem> repTypeList;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public TdsCertificate() {
        try {
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("Select", "--Select--"));
            repTypeList.add(new SelectItem("A", "Account No."));
            repTypeList.add(new SelectItem("C", "Customer ID"));
            setField("Account No.");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void onValueReportType() {
        if (repType.equalsIgnoreCase("Select")) {
            setMessage("Please Select Report Type");
            return;
        }
        if (repType.equalsIgnoreCase("A")) {
            this.setField("Account No.");
        } else {
            this.setField("Customer Id.");
        }
        setMessage("");
    }

    public void acnoValidate() {
        try {
            if (repType.equalsIgnoreCase("A")) {
                if (this.acno.equalsIgnoreCase("") || this.acno == null) {
                    setMessage("Please fill Account No.");
                    return;
                }
                if (this.acno.length() < 12) {
                    setMessage("Account should be 12 digits");
                    return;
                }

                String acctNature = ftsPostRemote.getAccountNature(acno);
                
                if (!(acctNature.equalsIgnoreCase(CbsConstant.TD_AC) 
                        || acctNature.equalsIgnoreCase(CbsConstant.MS_AC) 
                        || acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC))) {
                    setMessage("Please fill only FD , MS and Rd Account No.");
                    return;
                }

                newAcno = "[" + ftsPostRemote.getNewAccountNumber(acno) + "]";
            } else {
                if (this.acno.equalsIgnoreCase("") || this.acno == null) {
                    setMessage("Please fill Customer Id");
                    return;
                }
                newAcno = "[" + acno + "]";
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void yearValidate() {
        try {
            if (this.finYear.equalsIgnoreCase("") || this.finYear == null) {
                setMessage("Please fill Year");
                return;
            }
            if (this.finYear.length() < 4) {
                setMessage("Year should be 4 digits");
                return;
            }

            if (!this.finYear.matches("[0-9]*")) {
                setMessage("Year only Numeric form");
                return;
            }

            newFinYear = "[" + this.finYear + "]";
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void printAction() {
        String report = "Tds Certificate Report";
        try {

            GLReportFacadeRemote beanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            if (repType.equalsIgnoreCase("Select")) {
                setMessage("Please Select Report Type");
                return;
            }

            if (repType.equalsIgnoreCase("C")) {
                if ((acno == null) || (acno.equalsIgnoreCase(""))) {
                    this.setMessage("Please Enter Customer Id");
                    return;
                }
            } else {
                if ((acno == null) || (acno.equalsIgnoreCase(""))) {
                    this.setMessage("Please Enter Account Number");
                    return;
                }
                if (this.acno.length() != 12) {
                    this.setMessage("Please Enter Correct Account No");
                    return;
                }
                String acnature = ftsPostRemote.getAccountNature(acno);
                    if (!(acnature.equalsIgnoreCase(CbsConstant.TD_AC) 
                        || acnature.equalsIgnoreCase(CbsConstant.MS_AC) 
                        || acnature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acnature.equalsIgnoreCase(CbsConstant.RECURRING_AC))) {
                    setMessage("Please fill only FD , MS and Rd Account No.");
                    return;
                }
            }

            if ((finYear == null) || (finYear.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter Fyear");
                return;
            }
            // String brncode = ftsPostRemote.getCurrentBrnCode(newAcno);

            List<Td15HEntryCertificationPojo> reportList = beanRemote.getTdsCertificateData(repType, acno, this.finYear);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);

            new ReportBean().openPdf("TdsCertificate_" + ymd.format(dmy.parse(this.getTodayDate())), "TdsCertificate", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshPage() {
        setMessage("");
        setAcno("");
        setNewAcno("");
        setFinYear("");
        setNewFinYear("");
        setRepType("Select");
    }

    public String btnExitAction() {
        refreshPage();
        return "case1";
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getNewFinYear() {
        return newFinYear;
    }

    public void setNewFinYear(String newFinYear) {
        this.newFinYear = newFinYear;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }
}
