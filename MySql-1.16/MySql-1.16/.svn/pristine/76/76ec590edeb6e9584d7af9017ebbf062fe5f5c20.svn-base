/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.RdReschedulePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.AlmStmt;
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
 * @author sipl
 */
public class AlmStmtLiq extends BaseBean {
    
    private String message;
    private String selectStatus;
    private List<AlmStmt> selectStatusList;
    private final String rbiFacadeHomeName = "RbiMonthlyReportFacade";
    private final String commonFacadeHomeName = "CommonReportMethods";
    private RbiMonthlyReportFacadeRemote rbiFacadeRemote = null;
    private CommonReportMethodsRemote commonFacadeRemote = null;
    private ArrayList<RdReschedulePojo> gridDetail;
     private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyformatter = new SimpleDateFormat("dd/MM/yyyy");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(String selectStatus) {
        this.selectStatus = selectStatus;
    }    

    public List<AlmStmt> getSelectStatusList() {
        return selectStatusList;
    }

    public void setSelectStatusList(List<AlmStmt> selectStatusList) {
        this.selectStatusList = selectStatusList;
    }    

    public ArrayList<RdReschedulePojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(ArrayList<RdReschedulePojo> gridDetail) {
        this.gridDetail = gridDetail;
    }
    
    
    public AlmStmtLiq() {
        try {
            rbiFacadeRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup(rbiFacadeHomeName);
            commonFacadeRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonFacadeHomeName);
            this.setMessage("");
            setStatusList();            
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void setStatusList() {
        try {
            List statusLst = new ArrayList();
            statusLst = rbiFacadeRemote.getStmtValue("ALM-STR");
            selectStatusList = new ArrayList<AlmStmt>();
            if (statusLst.isEmpty()) {
                
            }
            AlmStmt aStmt;
            for (int i = 0; i < statusLst.size(); i++) {
                Vector stVec = (Vector) statusLst.get(i);
                aStmt = new AlmStmt(stVec.get(0).toString(),Integer.parseInt(stVec.get(1).toString()),Integer.parseInt(stVec.get(2).toString()),Integer.parseInt(stVec.get(3).toString()),Integer.parseInt(stVec.get(4).toString()),Integer.parseInt(stVec.get(5).toString()),stVec.get(6).toString(),stVec.get(7).toString());
                selectStatusList.add(aStmt);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());            
        } catch (Exception e) {
            this.setMessage(e.getMessage());            
        }
    }
    
    public void printAction(){
        Map fillParams = new HashMap();
        try{
            String report = "Statement of Structural Liquidity as on";
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", this.getUserName());            
            
            for (int i = 0; i < selectStatusList.size(); i++) {
                AlmStmt pojo = selectStatusList.get(i);
                //System.out.println(pojo.getClassification() + "," + pojo.getfBkt());
            }
            
            new ReportBean().jasperReport("Ho_Alm_Future_Assumption", "text/html",
                    new JRBeanCollectionDataSource(selectStatusList), fillParams, "Statement of Structural Liquidity as on");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);            
        } catch (Exception e) {
            this.setMessage(e.getMessage());            
        }
    }
    
    public void printPDF() {
        Map fillParams = new HashMap();
        try{
            
            String bnkName = "";
            String bnkAddress = "";
            List bankDetails = commonFacadeRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bankDetails.isEmpty()) {
                bnkName = bankDetails.get(0).toString();
                bnkAddress = bankDetails.get(1).toString();
            }
            
            String dt = ymdFormatter.format(dmyformatter.parse(this.getTodayDate()));
            String report = "Structural Liquidity as on "+dmyformatter.format(ymdFormatter.parse(dt));
            fillParams.put("report", report);
            fillParams.put("pPrintedDate", dmyformatter.format(ymdFormatter.parse(dt)));
            fillParams.put("pPrintedBy", this.getUserName());     
            
            fillParams.put("pBankName", bnkName);
            fillParams.put("pBankAddress", bnkAddress);
            
            new ReportBean().downloadPdf("ALM_Assumption" + ymdFormatter.format(dmyformatter.parse(this.getTodayDate())), "Ho_Alm_Future_Assumption", new JRBeanCollectionDataSource(selectStatusList), fillParams);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());            
        } catch (Exception e) {
            this.setMessage(e.getMessage());            
        }
    }
    
    
    
    
    
    public String exitAction() {
        return "case1";
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
}