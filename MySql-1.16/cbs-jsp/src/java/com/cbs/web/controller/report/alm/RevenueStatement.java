/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.alm;

import com.cbs.dto.report.RevenueStatementPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Sudhir
 */
public class RevenueStatement extends BaseBean{

    private String message;
    private String fromDate;   
    private String toDate;   
    private String repOpt;
    private String branch;
    private String repType;
    private List<SelectItem> branchList;
    private List<SelectItem> repOptionList;
    private List<SelectItem> repTypeList;
    private final String rbiFacadeHomeName = "RbiMonthlyReportFacade";
    private final String commonFacadeHomeName = "CommonReportMethods";
    private RbiMonthlyReportFacadeRemote rbiFacadeRemote = null;
    private CommonReportMethodsRemote commonFacadeRemote = null;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
    
    private String flag;

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepOpt() {
        return repOpt;
    }

    public void setRepOpt(String repOpt) {
        this.repOpt = repOpt;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    
    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /** Creates a new instance of RevenueStatement */
    public RevenueStatement() {
        try {            
            rbiFacadeRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup(rbiFacadeHomeName);
            commonFacadeRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonFacadeHomeName);
            this.setMessage("");
            List brnLst = new ArrayList();
            brnLst = commonFacadeRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("","--Select--"));
            repOptionList.add(new SelectItem("1", "in Rs."));
//            repOptionList.add(new SelectItem("T", "Thousand"));
//            repOptionList.add(new SelectItem("L", "Lacs"));
//            repOptionList.add(new SelectItem("C", "Crore"));
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("GL", "GL With Consolidate PL"));
            repTypeList.add(new SelectItem("PL", "Indivisual PL Heads"));
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void printReport() {
        try {
            setMessage("");
            if (fromDate == null) {
                setMessage("Please fill the From Date");
                flag = "false";
                return;
            }
            if(!Validator.validateDate(fromDate)){
                setMessage("Please fill the valid From Date");
                flag = "false";
                return;
            }
            if(dmyformatter.parse(fromDate).after(new Date())){
                setMessage("From Date must be less than Current Date");
                flag = "false";
                return;
            }
            if (toDate == null) {
                setMessage("Please fill the To Date");
                flag = "false";
                return;
            }
            if(!Validator.validateDate(toDate)){
                setMessage("Please fill the valid To Date");
                flag = "false";
                return;
            }
            if(dmyformatter.parse(toDate).after(new Date())){
                setMessage("To Date must be less than Current Date");
                flag = "false";
                return;
            }
            if(dmyformatter.parse(fromDate).after(dmyformatter.parse(toDate))){
                setMessage("From Date must be less than To Date");
                flag = "false";
                return;
            }
            if (this.repOpt == null || this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            flag = "true";
            setMessage("");
            String fromDt = ymdFormatter.format(dmyformatter.parse(fromDate));
            String toDt = ymdFormatter.format(dmyformatter.parse(toDate));
            List<RevenueStatementPojo> almAnnextureTable  = rbiFacadeRemote.revenueStatement(fromDt, toDt, getBranch(), new BigDecimal(repOpt),repType);
            if (almAnnextureTable.isEmpty()) {
                throw new ApplicationException("No proper data available!!!");
            }
            for(RevenueStatementPojo pojo : almAnnextureTable){
              //  System.out.print("Nature = "+pojo.getAcNature() + " \t Type = "+ pojo.getAcType() +" \t GLHead = "+pojo.getDescription());
              //  System.out.println("Opening Balance = "+pojo.getOpBal() + " \t Total Cr = "+ pojo.getCrAmt() +" \t Total Dr = "+pojo.getDrAmt() +" \t Closing = "+ pojo.getClosingBal());
             //   System.out.println(pojo.getsNo()+":"+pojo.getReportName()+":"+pojo.getClassification()+":"+pojo.getgNo()+":"+pojo.getsGNo()+":"+pojo.getSsGNo()+":"+pojo.getSssGNo()+":"+pojo.getSsssGNo()+":"+pojo.getDescription()+":"+pojo.getAcNature()+":"+pojo.getAcType()+":"+pojo.getGlHeadFrom()+":"+pojo.getGlHeadTo()+":"+pojo.getFormula()+":"+pojo.getOpBal()+":"+pojo.getDrAmt()+":"+pojo.getCrAmt()+":"+pojo.getClosingBal()+":"+pojo.getCountApplicable()+":"+pojo.getNpaClassification());
            }
            String bnkName = "";
            String bnkAddress = "";
            List bankDetails = commonFacadeRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bankDetails.isEmpty()) {
                bnkName = bankDetails.get(0).toString();
                bnkAddress = bankDetails.get(1).toString();
            }
            String amtIn = null ;
            if(repOpt.equalsIgnoreCase("T")){
                amtIn = "Amounts (Rs. in Thousand)";
            }else if(repOpt.equalsIgnoreCase("L")){
                amtIn = "Amounts (Rs. in Lac)";
            }else if(repOpt.equalsIgnoreCase("C")){
                amtIn = "Amounts (Rs. in Crore)";
            }else if(repOpt.equalsIgnoreCase("1")){
                amtIn = "Amounts (Rs.)";
            }
            String report = "Revenue Statement";
            //String fdt, tdt;
            Map fillParams = new HashMap();

            fillParams.put("pReportName", report);
            fillParams.put("pFromDate", fromDate);
            fillParams.put("pToDate", toDate);
            fillParams.put("pReportDate", fromDate+" To "+toDate);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", bnkName);
            fillParams.put("pBankAddress", bnkAddress);
            fillParams.put("pAmtIn", amtIn);
            new ReportBean().openPdf("REVENUE_STATEMENT_"+fromDt+"_"+toDt, "REVENUE_DETAILS", new JRBeanCollectionDataSource(almAnnextureTable), fillParams);

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

     public void textReport() {
        try {
            setMessage("");
            if (fromDate == null) {
                setMessage("Please fill the From Date");
                flag = "false";
                return;
            }
            if(!Validator.validateDate(fromDate)){
                setMessage("Please fill the valid From Date");
                flag = "false";
                return;
            }
            if(dmyformatter.parse(fromDate).after(new Date())){
                setMessage("From Date must be less than Current Date");
                flag = "false";
                return;
            }
            if (toDate == null) {
                setMessage("Please fill the To Date");
                flag = "false";
                return;
            }
            if(!Validator.validateDate(toDate)){
                setMessage("Please fill the valid To Date");
                flag = "false";
                return;
            }
            if(dmyformatter.parse(toDate).after(new Date())){
                setMessage("To Date must be less than Current Date");
                flag = "false";
                return;
            }
            if(dmyformatter.parse(fromDate).after(dmyformatter.parse(toDate))){
                setMessage("From Date must be less than To Date");
                flag = "false";
                return;
            }
            if (this.repOpt == null || this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            flag = "true";
            setMessage("");
            String fromDt = ymdFormatter.format(dmyformatter.parse(fromDate));
            String toDt = ymdFormatter.format(dmyformatter.parse(toDate));
            List<RevenueStatementPojo> almAnnextureTable  = rbiFacadeRemote.revenueStatement(fromDt, toDt, getBranch(), new BigDecimal(repOpt),repType);
            if (almAnnextureTable.isEmpty()) {
                throw new ApplicationException("No proper data available!!!");
            }
            String bnkName = "";
            String bnkAddress = "";
            List bankDetails = commonFacadeRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bankDetails.isEmpty()) {
                bnkName = bankDetails.get(0).toString();
                bnkAddress = bankDetails.get(1).toString();
            }
            String amtIn = null ;
            if(repOpt.equalsIgnoreCase("T")){
                amtIn = "Amounts (Rs. in Thousand)";
            }else if(repOpt.equalsIgnoreCase("L")){
                amtIn = "Amounts (Rs. in Lac)";
            }else if(repOpt.equalsIgnoreCase("C")){
                amtIn = "Amounts (Rs. in Crore)";
            }else if(repOpt.equalsIgnoreCase("1")){
                amtIn = "Amounts (Rs.)";
            }
            String report = "Revenue Statement";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pFromDate", fromDate);
            fillParams.put("pToDate", toDate);
            fillParams.put("pReportDate", fromDate+" To "+toDate);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", bnkName);
            fillParams.put("pBankAddress", bnkAddress);
            fillParams.put("pAmtIn", amtIn);
            new ReportBean().jasperReport("REVENUE_DETAILS", "text/html", new JRBeanCollectionDataSource(almAnnextureTable), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            System.out.println("Error is "+e );
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }
    public void refresh(){
        setFromDate("");
        setToDate("");
        setMessage("");
    }
    public String exitFrm() {
        return "case1";
    }
}
