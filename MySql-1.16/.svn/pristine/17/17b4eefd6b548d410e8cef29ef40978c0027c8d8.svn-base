/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.npci;

import com.cbs.dto.other.AutoMandateTo;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class StopUmrnReport extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private CommonReportMethodsRemote common;
    private NpciMandateFacadeRemote npciRemote;

    public StopUmrnReport() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            npciRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            initData();
            refreshForm();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("--Select--"));
            String alphaCode = common.getAlphacodeByBrncode(getOrgnBrCode());
            if (alphaCode.equalsIgnoreCase("HO")) {
                branchList.add(new SelectItem("ALL"));
            }
            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void printReport() {
        this.setMessage("");
        try {
            if (this.branch == null || this.branch.equals("--Select--")) {
                this.setMessage("Please select branch.");
                return;
            }
            List<AutoMandateTo> dataList = npciRemote.getStopUmrnDetails(this.branch, getOrgnBrCode());
            if (dataList.isEmpty()) {
                this.setMessage("There is no data to print.");
                return;
            }
            Map map = new HashMap();
            String reportName = "Stop UMRN Report";
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            map.put("pBankName", brNameAndAddList.get(0).toString());
            map.put("pBankAddress", brNameAndAddList.get(1).toString());
            map.put("pPrintedBy", getUserName());
            map.put("pReportName", reportName);
            new ReportBean().jasperReport("stopumrnreport", "text/html", new JRBeanCollectionDataSource(dataList), map, reportName);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("Please select branch...");
        this.setBranch("--Select--");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
