/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.dds;

import com.cbs.dto.report.DdsAccountExpiryPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
 * @author Athar Reza
 */
public class DdsAccountExpiry extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String agentCode;
    private List<SelectItem> agentCodeList;
    private String errorMsg;
    private String tillDt;
    private Date date = new Date();
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<DdsAccountExpiryPojo> resultList = new ArrayList<DdsAccountExpiryPojo>();
    private DDSReportFacadeRemote DDSReportRemote = null;
    private final String jndiHomeName = "DDSReportFacade";

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public List<SelectItem> getAgentCodeList() {
        return agentCodeList;
    }

    public void setAgentCodeList(List<SelectItem> agentCodeList) {
        this.agentCodeList = agentCodeList;
    }

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

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public DdsAccountExpiry() {

        try {

            tillDt = dmy.format(date);
            DDSReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            if (this.getOrgnBrCode().equals("90")) {
                List agent = new ArrayList();
                agent = DDSReportRemote.getAgentCodeHo();
                agentCodeList = new ArrayList<SelectItem>();
                agentCodeList.add(new SelectItem("ALL"));
                if (!agent.isEmpty()) {
                    for (int j = 0; j < agent.size(); j++) {
                        Vector acVector = (Vector) agent.get(j);
                        agentCodeList.add(new SelectItem(acVector.get(0).toString(), "("+acVector.get(0).toString()+")"+acVector.get(1).toString()));
                    }
                }
            } else {
                List agent = new ArrayList();
                agent = common.getAgentCode(this.getOrgnBrCode());
                agentCodeList = new ArrayList<SelectItem>();
                agentCodeList.add(new SelectItem("ALL"));
                if (!agent.isEmpty()) {
                    for (int j = 0; j < agent.size(); j++) {
                        Vector acVector = (Vector) agent.get(j);
                        agentCodeList.add(new SelectItem(acVector.get(0).toString(), "("+acVector.get(0).toString()+")"+acVector.get(1).toString()));
                    }
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        String branchName = "";
        String address = "";
        String brCode = "";
        String agName = "Agent Name:";
        if (!Validator.validateDate(tillDt)) {
            errorMsg = "Please fill Proper Till Date.";
            return;
        }
        String brnCode = this.branchCode;
        if (brnCode.equalsIgnoreCase("0A")) {
            brCode = "ALL";
        } else {
            brCode = brnCode;
        }

        try {
            String TillDate = ymd.format(dmy.parse(this.tillDt));
            String report = "DDS Account Expiry Date Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            Map fillParams = new HashMap();
            if (!agentCode.equals("ALL")) {
                fillParams.put("pAgentName", agName);
            }
            fillParams.put("pBranchCode", brCode);
            fillParams.put("pAgentCode", agentCode);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pReportDate", this.tillDt);
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());
            resultList = DDSReportRemote.getDdsAccountExpiryDate(branchCode, agentCode, TillDate);
            if (resultList.isEmpty()) {
                errorMsg = "Data does not exit";
                return;
            }
            new ReportBean().jasperReport("DdsAccountExpiryDate", "text/html",
                    new JRBeanCollectionDataSource(resultList), fillParams, "DDS Account Expiry Date Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }
    
    public void btnPdfAction(){
      String branchName = "";
        String address = "";
        String brCode = "";
        String agName = "Agent Name:";
        if (!Validator.validateDate(tillDt)) {
            errorMsg = "Please fill Proper Till Date.";
            return;
        }
        String brnCode = this.branchCode;
        if (brnCode.equalsIgnoreCase("0A")) {
            brCode = "ALL";
        } else {
            brCode = brnCode;
        }

        try {
            String TillDate = ymd.format(dmy.parse(this.tillDt));
            String report = "DDS Account Expiry Date Report";
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            Map fillParams = new HashMap();
            if (!agentCode.equals("ALL")) {
                fillParams.put("pAgentName", agName);
            }
            fillParams.put("pBranchCode", brCode);
            fillParams.put("pAgentCode", agentCode);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pReportDate", this.tillDt);
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());
            resultList = DDSReportRemote.getDdsAccountExpiryDate(branchCode, agentCode, TillDate);
            if (resultList.isEmpty()) {
                errorMsg = "Data does not exit";
                return;
            }
            new ReportBean().openPdf("DDS Account Expiry Date Report_"+ ymd.format(dmy.parse(getTodayDate())), "DdsAccountExpiryDate",new JRBeanCollectionDataSource(resultList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }  
    }

    public void refresh() {
        setErrorMsg("");
        setAgentCode("ALL");
        tillDt = dmy.format(new Date());
    }

    public String exitAction() {
        return "case1";
    }
}
