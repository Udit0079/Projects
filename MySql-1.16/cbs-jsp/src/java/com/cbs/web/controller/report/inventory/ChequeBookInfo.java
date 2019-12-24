/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.inventory;

import com.cbs.dto.report.IssueChequeBookRegisterPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.InventoryReportFacadeRemote;
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
 * @author root
 */
public class ChequeBookInfo extends BaseBean {

    private String message;
    private String repOption;
    private List<SelectItem> repOptionList;
    private String acNo;
    private String issueDt;
    private Date dt = new Date();
    private String lableButton;
    private String newAcNo;
    private String chequeNoType;
    private List<SelectItem> chequeNoTypeList;
    private InventoryReportFacadeRemote facadeRemote;
    private CommonReportMethodsRemote common;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public ChequeBookInfo() {
        try {
            this.lableButton = "Account No.";
            this.setIssueDt(sdf.format(dt));
            facadeRemote = (InventoryReportFacadeRemote) ServiceLocator.getInstance().lookup("InventoryReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            repOptionList = new ArrayList<>();
            repOptionList.add(new SelectItem("S", "--Select--"));
            repOptionList.add(new SelectItem("A", "Account Wise"));
            repOptionList.add(new SelectItem("C", "Customer Wise"));

            List list = common.getTdsDoctype("449");
            chequeNoTypeList = new ArrayList<>();
            chequeNoTypeList.add(new SelectItem("ALL", "ALL"));
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    chequeNoTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
                }
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void reportAction() {
        this.message = "";
        if (repOption.equalsIgnoreCase("A")) {
            this.lableButton = "Account No.";
        } else {
            this.lableButton = "Customer Id";
        }
    }

    public void accountAction() {
        this.newAcNo = acNo;
    }

    public void viewReport() {
        try {
            String branchName = "", address = "", report = "";
            List<IssueChequeBookRegisterPojo> reportList = facadeRemote.getChequeBookData(repOption, acNo, ymd.format(sdf.parse(issueDt)), chequeNoType);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            String repName = "Cheque Book Detail";
            Map fillParams = new HashMap();
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pReportName", repName);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", issueDt);

            new ReportBean().jasperReport("ChequeBookInfo", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, repName);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        try {
            String branchName = "", address = "", report = "";
            List<IssueChequeBookRegisterPojo> reportList = facadeRemote.getChequeBookData(repOption, acNo, ymd.format(sdf.parse(issueDt)), chequeNoType);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            String repName = "Cheque Book Detail";
            Map fillParams = new HashMap();
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pReportName", repName);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", issueDt);
            new ReportBean().openPdf("Cheque Book Detail_" + ymd.format(dt), "ChequeBookInfo", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", repName);
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        setMessage("");
        setRepOption("S");
        setAcNo("");
        setNewAcNo("");
        this.lableButton = "Account No.";
        this.setIssueDt(sdf.format(dt));
    }

    public String exitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepOption() {
        return repOption;
    }

    public void setRepOption(String repOption) {
        this.repOption = repOption;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getLableButton() {
        return lableButton;
    }

    public void setLableButton(String lableButton) {
        this.lableButton = lableButton;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getChequeNoType() {
        return chequeNoType;
    }

    public void setChequeNoType(String chequeNoType) {
        this.chequeNoType = chequeNoType;
    }

    public List<SelectItem> getChequeNoTypeList() {
        return chequeNoTypeList;
    }

    public void setChequeNoTypeList(List<SelectItem> chequeNoTypeList) {
        this.chequeNoTypeList = chequeNoTypeList;
    }
}
