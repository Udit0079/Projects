/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import com.cbs.dto.LoanPrequistitsList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanIntProductTestFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class StaffLoanPerquisits extends BaseBean {

    private String message;
    private float interestRate;
    private String fromDate;
   // private String toDate;
    private LoanIntProductTestFacadeRemote remoteFacade;
    private CommonReportMethodsRemote common;
    private String schemeName;
    private List<SelectItem> schemeNameList;
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    
    public StaffLoanPerquisits() {
        try {
            remoteFacade = (LoanIntProductTestFacadeRemote) ServiceLocator.getInstance().lookup("LoanIntProductTestFacade");
            common = (CommonReportMethodsRemote)ServiceLocator.getInstance().lookup("CommonReportMethods");
            this.setMessage("");
            
            schemeNameList = new ArrayList<SelectItem>();
            List dataList = remoteFacade.getStaffScheme();
            
            schemeNameList.add(new SelectItem("", "--SELECT--"));
            for(int i=0; i< dataList.size(); i++){
                Vector vect = (Vector) dataList.get(i);
                 schemeNameList.add(new SelectItem(vect.get(0).toString(), vect.get(1).toString()));
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

  
    public void viewReport() {
        try {
            validate();
            List<LoanPrequistitsList>  dataList = remoteFacade.staffLoanPerquisits(ymmd.format(dmy.parse(fromDate)), schemeName, getOrgnBrCode(), interestRate);
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());

            Map fillParams = new HashMap();
            fillParams.put("pReportName", "Staff Loan Perquisits");
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", fromDate);
            fillParams.put("pSchemeCode", getSchemeDesc(schemeName));
            fillParams.put("pRoi", String.valueOf(interestRate));

            new ReportBean().jasperReport("LoanPrequisitsReport", "text/html", new JRBeanCollectionDataSource(dataList), fillParams, "Staff Loan Perquisits");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        try {
            validate();
            List<LoanPrequistitsList>  dataList = remoteFacade.staffLoanPerquisits(ymmd.format(dmy.parse(fromDate)), schemeName, getOrgnBrCode(), interestRate);
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            
            Map fillParams = new HashMap();
            fillParams.put("pReportName", "Staff Loan Perquisits");
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", fromDate);
            fillParams.put("pSchemeCode", getSchemeDesc(schemeName));
            fillParams.put("pRoi", String.valueOf(interestRate));

             new ReportBean().openPdf("Staff Loan Perquisits_" + ymmd.format(dmy.parse(getTodayDate())), "LoanPrequisitsReport", new JRBeanCollectionDataSource(dataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Staff Loan Perquisits");
        } catch (Exception ex) {
           setMessage(ex.getMessage());
        }
    }

    private void validate() throws ApplicationException{
        if(schemeName.equals("")) throw new ApplicationException("Please select the Scheme");
        
        if(interestRate == 0)throw new ApplicationException("Please fill valid Interest Rate");
        
        if(!new Validator().validateDoublePositive(String.valueOf(interestRate)))
            throw new ApplicationException("Please fill valid Interest Rate");
        
        if(fromDate.equals(""))throw new ApplicationException("Please fill valid Till Date");
        
        if(!Validator.validateDate(fromDate)) throw new ApplicationException("Please fill valid Till Date");
    }
    
    
    private String getSchemeDesc(String schemeCode){
        for(SelectItem item : schemeNameList){
            if(item.getValue().toString().equals(schemeCode)) return item.getLabel();
        }
        return "";
    }
    public void refresh() {
        setMessage("");
        setFromDate("");
        setSchemeName("");
        setInterestRate(0);
    }

    public String exitBtnAction() {
        return "case1";

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public List<SelectItem> getSchemeNameList() {
        return schemeNameList;
    }

    public void setSchemeNameList(List<SelectItem> schemeNameList) {
        this.schemeNameList = schemeNameList;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

//    public String getToDate() {
//        return toDate;
//    }
//
//    public void setToDate(String toDate) {
//        this.toDate = toDate;
//    }

}
