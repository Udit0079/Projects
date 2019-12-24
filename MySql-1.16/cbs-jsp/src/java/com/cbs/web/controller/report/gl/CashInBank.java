/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.CashInBankPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class CashInBank extends BaseBean {

    private String message;
    Date calDate;
    Date dt = new Date();
    private String branch;
    private List<SelectItem> branchList;
    private GLReportFacadeRemote glBeanRemote;
    private TdReceiptManagementFacadeRemote tdFacade;
    private CommonReportMethodsRemote common;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<CashInBankPojo> repList = new ArrayList<CashInBankPojo>();

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
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

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CashInBank() {
        try {
            this.setCalDate(dt);
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            tdFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            List brncode = new ArrayList();
            brncode = tdFacade.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void btnHtmlAction() {
        String report = "Cash Position Report";
        String branchName = "", address = "";
        try {
            Date date = this.calDate;
            if (date == null) {
                this.setMessage("Please Enter Date");
            }
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAdd", address);
            fillParams.put("SysDate", sdf.format(calDate));
            fillParams.put("pPrintedBy", getUserName());

            repList = glBeanRemote.getCashInBankData(ymd.format(calDate), this.getBranch());
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            new ReportBean().jasperReport("CashInBank", "text/html",
                    new JRBeanCollectionDataSource(repList), fillParams, "Cash Position Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String btnExitAction() {
        return "case1";
    }
}
