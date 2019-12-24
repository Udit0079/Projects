/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.UserReportTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Sudhir Kr bisht
 */
public class UserReport extends BaseBean {
    
    private String messsage;
    private final String othersReportjndiName = "OtherReportFacade";
    private OtherReportFacadeRemote otherFacadeRemote = null;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private CommonReportMethodsRemote commonRemote;
    TdReceiptManagementFacadeRemote remoteFacade;
    private String selectCriteria;
    private List<SelectItem> selectCriteriaList = new ArrayList<SelectItem>();
    // private String inputId;
    //  private boolean disableIDInput;
    private List<SelectItem> branchList;
    private String branch;
    
    public List<SelectItem> getBranchList() {
        return branchList;
    }
    
    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }
    
    public String getBranch() {
        return branch;
    }
    
    public void setBranch(String branch) {
        this.branch = branch;
    }

//    public boolean isDisableIDInput() {
//        return disableIDInput;
//    }
//
//    public void setDisableIDInput(boolean disableIDInput) {
//        this.disableIDInput = disableIDInput;
//    }
//    public String getInputId() {
//        return inputId;
//    }
//
//    public void setInputId(String inputId) {
//        this.inputId = inputId;
//    }
    public String getSelectCriteria() {
        return selectCriteria;
    }
    
    public void setSelectCriteria(String selectCriteria) {
        this.selectCriteria = selectCriteria;
    }
    
    public List<SelectItem> getSelectCriteriaList() {
        return selectCriteriaList;
    }
    
    public void setSelectCriteriaList(List<SelectItem> selectCriteriaList) {
        this.selectCriteriaList = selectCriteriaList;
    }
    
    public String getMesssage() {
        return messsage;
    }
    
    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    /**
     * Creates a new instance of UserReport
     */
    public UserReport() {
        try {
            selectCriteriaList = new ArrayList<SelectItem>();
            selectCriteriaList.add(new SelectItem("--SELECT--", "--SELECT--"));
            selectCriteriaList.add(new SelectItem("ALL", "ALL"));
            selectCriteriaList.add(new SelectItem("A", "ACTIVE"));
            selectCriteriaList.add(new SelectItem("I", "INACTIVE"));
            selectCriteriaList.add(new SelectItem("C", "CANCELED"));
            selectCriteriaList.add(new SelectItem("D", "DELETED"));
            // disableIDInput = true;
            otherFacadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup(othersReportjndiName);
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List brnCodeList = remoteFacade.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnCodeList.isEmpty()) {
                for (int i = 0; i < brnCodeList.size(); i++) {
                    Vector brnVector = (Vector) brnCodeList.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMesssage(e.getLocalizedMessage());
        }
    }
    
    public void printReport() {
        try {
            setMesssage("");
            if (selectCriteria.equalsIgnoreCase("--SELECT--")) {
                setMesssage("Select the selection criteria");
                return;
            }
            String bankName = null, bankAddress = null;
            
            List bankdetails = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bankdetails.isEmpty()) {
                bankName = bankdetails.get(0).toString();
                bankAddress = bankdetails.get(1).toString();
            }
            List<UserReportTable> userDetails = otherFacadeRemote.getUserInfo(branch, selectCriteria);
            if (userDetails.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAddress", bankAddress);
            fillParams.put("pPrintedDate", dmyFormatter.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", " CBS-User Report");
            
            new ReportBean().jasperReport("UserReport", "text/html", new JRBeanCollectionDataSource(userDetails), fillParams, "CBS User Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            
        } catch (ApplicationException e) {
            setMesssage(e.getMessage());
        } catch (Exception e) {
            setMesssage(e.getMessage());
        }
    }
    
    public void pdfDownLoad() {
        try {
            setMesssage("");
            if (selectCriteria.equalsIgnoreCase("--SELECT--")) {
                setMesssage("Select the selection criteria");
                return;
            }
            String bankName = null, bankAddress = null;
            
            List bankdetails = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bankdetails.isEmpty()) {
                bankName = bankdetails.get(0).toString();
                bankAddress = bankdetails.get(1).toString();
            }
            List<UserReportTable> userDetails = otherFacadeRemote.getUserInfo(branch, selectCriteria);
            if (userDetails.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAddress", bankAddress);
            fillParams.put("pPrintedDate", dmyFormatter.format(new Date()));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", " CBS-User Report");
            
            new ReportBean().openPdf("CBS-User Report_" + ymdFormatter.format(dmyFormatter.parse(getTodayDate())), "UserReport", new JRBeanCollectionDataSource(userDetails), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "CBS User Report");
            
        } catch (ApplicationException e) {
            setMesssage(e.getMessage());
        } catch (Exception e) {
            setMesssage(e.getMessage());
        }
    }

//    public void onchangeOption() {
//        try {
//            setInputId("");
//            if (selectCriteria.equalsIgnoreCase("ID")) {
//                disableIDInput = false;
//            } else {
//                disableIDInput = true;
//            }
//        } catch (Exception e) {
//            setMesssage(e.getLocalizedMessage());
//        }
//    }
    public void refreshFrm() {
        setMesssage("");
        setSelectCriteria("--SELECT--");
        setBranch("0A");
    }
    
    public String exitFrm() {
        return "case1";
    }
}
