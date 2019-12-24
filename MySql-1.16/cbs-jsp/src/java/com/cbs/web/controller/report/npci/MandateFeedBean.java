/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.npci;

import com.cbs.dto.other.CbsMandateDetailPojo;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MandateFeedBean extends BaseBean {

    private String message;
    private String branch;
    private String txnType;
    private String option;
    private String frdt;
    private String todt;
    private List<SelectItem> optionList;
    private List<SelectItem> branchList;
    private List<SelectItem> txnTypeList;
    private MiscMgmtFacadeS1Remote miscMgmtRemote;
    private TdReceiptManagementFacadeRemote tdMgmtRemote;
    private CommonReportMethodsRemote commonReportRemote;
    private final static String REPORT_NAME = "Outward Mandate Feeding Report";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public MandateFeedBean() {
        try {
            miscMgmtRemote = (MiscMgmtFacadeS1Remote) ServiceLocator.getInstance().lookup("MiscMgmtFacadeS1");
            tdMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            initData();
            btnRefreshAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void initData() {
        branchList = new ArrayList<>();
        txnTypeList = new ArrayList<>();
        try {
            branchList.add(new SelectItem("0", "--Select--"));
            List list = tdMgmtRemote.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                branchList.add(new SelectItem(ele.get(0).toString().length() < 2 ? "0" + ele.get(0).toString()
                        : ele.get(0).toString(), ele.get(1).toString()));
            }

            txnTypeList.add(new SelectItem("0", "--Select--"));
            txnTypeList.add(new SelectItem("ACH"));
            txnTypeList.add(new SelectItem("ECS"));
            
            optionList = new ArrayList();
            optionList.add(new SelectItem("0","--Select--"));
            optionList.add(new SelectItem("U","Update Date Wise"));
            optionList.add(new SelectItem("E","Enter Date Wise"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        try {
            if (this.branch == null || this.branch.equals("0") || this.branch.equals("")) {
                this.setMessage("Please select Branch.");
                return;
            }
            if (this.txnType == null || this.txnType.equals("0") || this.txnType.equals("")) {
                this.setMessage("Please select Txn Type.");
                return;
            }

            List<CbsMandateDetailPojo> dataList = miscMgmtRemote.getOwMandateFeedReport(this.branch, this.txnType ,this.option,
                   ymd.format(sdf.parse(this.frdt)),ymd.format(sdf.parse(this.todt)));
            if (dataList.isEmpty()) {
                this.setMessage("There is no data to show the report.");
                return;
            }

            System.out.println("List Size Is-->" + dataList.size());

            Map fillParams = new HashMap();
            fillParams.put("pReportName", REPORT_NAME);
            fillParams.put("pReportDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());
            
            List bankList = commonReportRemote.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", bankList.get(0));
            fillParams.put("pBankAddress", bankList.get(1));
            fillParams.put("pOption", this.option);
            
            new ReportBean().jasperReport("owMandateFeedReport", "text/html", new JRBeanCollectionDataSource(dataList),
                    fillParams, REPORT_NAME);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", REPORT_NAME);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setBranch("0");
        this.setTxnType("0");
    }

    public String btnExitAction() {
        btnRefreshAction();
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

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<SelectItem> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<SelectItem> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }
    
}
