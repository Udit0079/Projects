/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.ho.AnnualNPAStmtConsolidate;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.Form2StmtUnSecAdvToDirFirm;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author saurabhsipl
 */
public class AnnualNPAStmt extends BaseBean {
    private String asOnDt;
    private String message;
    private String repOpt;
    private List<SelectItem> repOptionList;
    Date dt = new Date();
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    private RbiReportFacadeRemote rbiRemote = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
      public AnnualNPAStmt() {
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup("HoReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            rbiRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            setMessage("");
            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("", "--Select--"));
            repOptionList.add(new SelectItem("R", "Rs."));
            repOptionList.add(new SelectItem("T", "Thousand"));
            repOptionList.add(new SelectItem("L", "Lacs"));
            repOptionList.add(new SelectItem("C", "Crore"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
      
       public void viewPdf() {
        Map map = new HashMap();
        try {
             if (this.asOnDt == null) {
                setMessage("Please Fill To Date");
                return;
            }
            if (sdf.parse(asOnDt).compareTo(new Date()) > 0) {
                setMessage("Date can not be greater than Current Date.");
                return;
            }
            if (this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            setMessage("");
            String bankName = "";
            String bankAddress = "";
            String report = "";
            List dataList1;
            dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            String amtIn = null;
            if (repOpt.equalsIgnoreCase("T")) {
                amtIn = "Amount (Rs. in Thousand)";
            } else if (repOpt.equalsIgnoreCase("L")) {
                amtIn = "Amount (Rs. in Lac)";
            } else if (repOpt.equalsIgnoreCase("C")) {
                amtIn = "Amount (Rs. in Crore)";
            } else if (repOpt.equalsIgnoreCase("R")) {
                amtIn = "Amount (Rs.)";
            }
            report = "ANNUAL NPA STATEMENT";
//            map.put("repDate", this.asOnDt);
//            map.put("pPrintedDate", this.dt);
            String[] arr = Util.getReportOptionAndDescription(this.repOpt);
            String beginDate = CbsUtil.dateAdd(rbiRemote.getMinFinYear(ymd.format(sdf.parse(asOnDt))), -1);
            
            map.put("pPrintedBy", this.getUserName());
            map.put("pReportDate", this.asOnDt);
            map.put("pReportName", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            map.put("pAmtIn", amtIn);
            map.put("pPreFinDate", sdf.format(ymd.parse(beginDate)));
            map.put("pCurFinDate", sdf.format(sdf.parse(asOnDt)));
            map.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            System.out.println("Start"+new Date());
            List<AnnualNPAStmtConsolidate> annualNPAList = hoRemote.getAnnualNPAStmt(getOrgnBrCode(), ymd.format(sdf.parse(asOnDt)), new BigDecimal(arr[0]),"ANNUAL_NPA");
//            for(int k=0;k<annualNPAList.get(0).getMainList().size();k++){
//                System.out.println("GNo: "+annualNPAList.get(0).getMainList().get(k).getgNo()
//                                +"; sGNo: "+annualNPAList.get(0).getMainList().get(k).getsGNo()
//                                +"; ssGNo: "+annualNPAList.get(0).getMainList().get(k).getSsGNo()
//                                +"; sssGNo: "+annualNPAList.get(0).getMainList().get(k).getSssGNo()
//                                +"; ssssGNo: "+annualNPAList.get(0).getMainList().get(k).getSsssGNo()
//                                +"; Count: "+annualNPAList.get(0).getMainList().get(k).getCountApplicable()
//                                +"; Desc: "+annualNPAList.get(0).getMainList().get(k).getDescription()
//                                +"; PriAmt: "+annualNPAList.get(0).getMainList().get(k).getPrincipalAmt()
//                                +"; IntAmt: "+annualNPAList.get(0).getMainList().get(k).getInterestAmt());
//            }
            List<Form2StmtUnSecAdvToDirFirm> dirAdvList = new ArrayList<Form2StmtUnSecAdvToDirFirm>();
            Form2StmtUnSecAdvToDirFirm pojo1 = new Form2StmtUnSecAdvToDirFirm();
            if (!annualNPAList.isEmpty()) {
                AnnualNPAStmtConsolidate partAPojo = annualNPAList.get(0);
                pojo1.setDirAdvList(new JRBeanCollectionDataSource(partAPojo.getMainList()));
                pojo1.setDirAdvList1(new JRBeanCollectionDataSource(partAPojo.getPartBList()));
                dirAdvList.add(pojo1);
            }                
            
//            List<AnnualNPAStmtJrxmlPojo> jrxmlList = new ArrayList<AnnualNPAStmtJrxmlPojo>();
//            AnnualNPAStmtJrxmlPojo jrxmlPojo = new AnnualNPAStmtJrxmlPojo();
//            if (!annualNPAList.isEmpty()) {
//                AnnualNPAStmtConsolidate partAPojo = annualNPAList.get(0);
//                jrxmlPojo.setPojoList(new JRBeanCollectionDataSource(partAPojo.getMainList()));  //Part-A-I
//                jrxmlPojo.setPartBList(new JRBeanCollectionDataSource(partAPojo.getPartBList()));       //Part-A-II
//                jrxmlList.add(jrxmlPojo);
//            }
            System.out.println("END"+new Date());
            new ReportBean().openPdf("ANNUAL_NPA_"+ymd.format(sdf.parse(asOnDt)), "ANNUAL_NPA_COVER", new JRBeanCollectionDataSource(dirAdvList), map);

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);            
            
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh(){
        setMessage("");
        setRepOpt("");
    }
    
    public String exit() {
        return "case1";
    }
    
    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
