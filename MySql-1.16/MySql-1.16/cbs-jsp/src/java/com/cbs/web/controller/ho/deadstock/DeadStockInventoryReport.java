/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.deadstock;

import com.cbs.dto.ItemStatusReportTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.deadstock.DeadstockFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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

/**
 *
 * @author Admin
 */
public class DeadStockInventoryReport extends BaseBean {

    String message;
    Date calDate;
    private String branch;
    private List<SelectItem> branchList;
    Date dt = new Date();
    private CommonReportMethodsRemote common;
    private DeadstockFacadeRemote deadstockFacadeRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<ItemStatusReportTable> reportList = new ArrayList<ItemStatusReportTable>();

    public DeadStockInventoryReport() {
        try {
            this.setCalDate(dt);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            deadstockFacadeRemote = (DeadstockFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockFacade");
            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());

            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        String report = "Dead Stock Inventroy Report";
        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", sdf.format(this.calDate));
            fillParams.put("pReportName", report);

            reportList = deadstockFacadeRemote.getDeadStockData(branch, ymd.format(calDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("DeadStockInventroyReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Dead Stock Inventroy Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        String report = "Dead Stock Inventroy Report";
        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", sdf.format(this.calDate));
            fillParams.put("pReportName", report);

            reportList = deadstockFacadeRemote.getDeadStockData(branch, ymd.format(calDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf("Dead Stock Inventroy Report_"+ymd.format(dt) , "DeadStockInventroyReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
            
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
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

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }
}
