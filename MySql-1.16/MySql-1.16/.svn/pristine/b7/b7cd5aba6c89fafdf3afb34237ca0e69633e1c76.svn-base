/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.AadharLpgStatusPojo;
import com.cbs.pojo.OnlinePigmeInfoPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class OnlinePigmeInfo extends BaseBean {

    private String message;
    private String status;
    private List<SelectItem> statusList;
    private String frdt;
    private String todt;
    private String ddAgent;
    private List<SelectItem> ddAgentList;
    private CommonReportMethodsRemote common;
    private OtherMasterFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<OnlinePigmeInfoPojo> reportList = new ArrayList<OnlinePigmeInfoPojo>();
    private String brnCode;
    private List<SelectItem> brnCodeList;

    public OnlinePigmeInfo() {
        try {
            frdt = getTodayDate();
            todt = getTodayDate();
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0","--Select--"));
            statusList.add(new SelectItem("ALL", "ALL"));
            statusList.add(new SelectItem("0000", "Success"));
            statusList.add(new SelectItem("0001", "Un-Success"));
            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            brnCodeList = new ArrayList<SelectItem>();
            brnCodeList.add(new SelectItem("0","--Select--"));
            if (!brnLst.isEmpty()) {
                if(getOrgnBrCode().equalsIgnoreCase("90")) {
                    brnLst.remove(0);
                }
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    brnCodeList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }
    
    public void brCodeOperation(){
        try{
            ddAgentList = new ArrayList<>();
            List agentList = remoteFacade.getddAgent(brnCode);
            ddAgentList.add(new SelectItem("0","--Select--"));
            for(int i =0 ; i< agentList.size(); i++){
            Vector cet = (Vector) agentList.get(i);
            ddAgentList.add(new SelectItem(cet.get(0).toString(),"("+cet.get(0).toString()+")"+cet.get(1).toString()));   
            }
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
        
    public void btnHtmlAction() {
        setMessage("");
        try {
            String report = "Online Pigme Detail";
            if (frdt == null || frdt.equalsIgnoreCase("")) {
                setMessage("Please fill From Date");
                return;
            }

            if (!Validator.validateDate(frdt)) {
                setMessage("Please fill Proper from date ");
                return;
            }
            if (dmy.parse(frdt).after(dmy.parse(getTodayDate()))) {
                setMessage("From Date should be less than current date !");
                return;
            }

            if (todt == null || todt.equalsIgnoreCase("")) {
                setMessage("Please fill To Date");
                return;
            }

            if (!Validator.validateDate(todt)) {
                setMessage("Please fill Proper to date ");
                return;
            }
            if (dmy.parse(todt).after(dmy.parse(getTodayDate()))) {
                setMessage("To Date should be less than current date !");
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", frdt + " to " + todt);

            reportList = remoteFacade.getOlinePigmeInfoData(ymd.format(dmy.parse(frdt)), ymd.format(dmy.parse(todt)), status, this.brnCode,this.ddAgent);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            new ReportBean().jasperReport("OnlinePigmeInfo", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Online Pigme Detail");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");


        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void viewPdfReport() {
        setMessage("");
        try {

            String report = "Online Pigme Detail";
            if (frdt == null || frdt.equalsIgnoreCase("")) {
                setMessage("Please fill From Date");
                return;
            }

            if (!Validator.validateDate(frdt)) {
                setMessage("Please fill Proper from date ");
                return;
            }
            if (dmy.parse(frdt).after(dmy.parse(getTodayDate()))) {
                setMessage("From Date should be less than current date !");
                return;
            }

            if (todt == null || todt.equalsIgnoreCase("")) {
                setMessage("Please fill To Date");
                return;
            }

            if (!Validator.validateDate(todt)) {
                setMessage("Please fill Proper to date ");
                return;
            }
            if (dmy.parse(todt).after(dmy.parse(getTodayDate()))) {
                setMessage("To Date should be less than current date !");
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", frdt + " to " + todt);

            reportList = remoteFacade.getOlinePigmeInfoData(ymd.format(dmy.parse(frdt)), ymd.format(dmy.parse(todt)), status, this.brnCode,this.ddAgent);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            new ReportBean().openPdf("Online Pigme Detail", "OnlinePigmeInfo", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);


        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void btnRefreshAction() {
        setMessage("");

        setFrdt(getTodayDate());
        setTodt(getTodayDate());
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
    public String getBrnCode() {
        return brnCode;
    }
    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }
    public List<SelectItem> getBrnCodeList() {
        return brnCodeList;
    }
    public void setBrnCodeList(List<SelectItem> brnCodeList) {
        this.brnCodeList = brnCodeList;
    }

    public String getDdAgent() {
        return ddAgent;
    }

    public void setDdAgent(String ddAgent) {
        this.ddAgent = ddAgent;
    }

    public List<SelectItem> getDdAgentList() {
        return ddAgentList;
    }

    public void setDdAgentList(List<SelectItem> ddAgentList) {
        this.ddAgentList = ddAgentList;
    }
    
}
