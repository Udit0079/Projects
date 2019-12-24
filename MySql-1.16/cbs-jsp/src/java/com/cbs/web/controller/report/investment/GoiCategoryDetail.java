/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.dto.YearEndDatePojo;
import com.cbs.dto.report.CrrSlrPojo;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GoiReportPojo;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.utils.CbsUtil;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author saurabhsipl
 */
public class GoiCategoryDetail extends BaseBean {
    private String message;
    private String asOnDt;    
    private String repType;
    private List<SelectItem> repTypeList;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    private InvestmentMgmtFacadeRemote obj = null;
    private HoReportFacadeRemote hoRemote = null;
    private RbiReportFacadeRemote rbiRemote =null;
    private final String jndiName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    
    public GoiCategoryDetail() {
        repTypeList = new ArrayList<SelectItem>();
        try {
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            obj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup("HoReportFacade");
            rbiRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            getReportTypeList();
            resetAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void getReportTypeList() {
        try {
            repTypeList.add(new SelectItem("--Select--"));
            repTypeList.add(new SelectItem("ALL", "All"));
            repTypeList.add(new SelectItem("HM", "HELD TO MATURITY"));
            repTypeList.add(new SelectItem("HT", "HELD FOR TRADING"));
            repTypeList.add(new SelectItem("AF", "AVAILABLE FOR SALE"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    public void onBlurAsOnDt() {
        this.setMessage("");
        if (this.asOnDt.length() < 10) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.asOnDt) == false) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
    }
    
    public void processAction() {
        this.setMessage("");
        if(this.repType.equalsIgnoreCase("")){
            this.setMessage("Please Select Report Type!!");
            return;
        }
        if (this.asOnDt.length() < 10) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        if (new Validator().validateDate_dd_mm_yyyy(this.asOnDt) == false) {
            this.setMessage("please fill correct report as on date !");
            return;
        }
        List<GoiReportPojo> reportList = new ArrayList<GoiReportPojo>();
        try {
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brList = common.getBranchNameandAddress(getOrgnBrCode());
            Date reportAsOnDt = dmy.parse(this.asOnDt);
            YearEndDatePojo yDate1 = new YearEndDatePojo();
            YearEndDatePojo yDate2 = new YearEndDatePojo();
            yDate1 = (YearEndDatePojo) rbiRemote.getYearEndDataAccordingToDate("90", ymd.format(dmy.parse(asOnDt)));
            String prevDate = CbsUtil.dateAdd(yDate1.getMinDate(), -1);
            String previousDate = dmy.format(ymd.parse(prevDate));
            List<InvestmentMaster> resultListPreFinYear = remoteObj.getIndivisualReportTypeAllSellarName("GOVERNMENT SECURITIES", dmy.parse(previousDate));
            if (this.repType.equalsIgnoreCase("All")) {
                List<InvestmentMaster> resultList = remoteObj.getIndivisualReportTypeAllSellarName("GOVERNMENT SECURITIES", reportAsOnDt);
                if (!resultList.isEmpty()) {
                    for (InvestmentMaster entity : resultList) {
                        GoiReportPojo pojo = new GoiReportPojo();
                        pojo.setCtrlNo(entity.getInvestmentMasterPK().getControlno());
                        pojo.setSecDtl(entity.getSecdesc());
                        pojo.setSellarName(entity.getSellername());
                        pojo.setSettleDt(dmy.format(entity.getSettledt()));
                        pojo.setMatDt(dmy.format(entity.getMatdt()));
                        pojo.setFaceValue(entity.getFacevalue());
                        double amortamt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt)));
                        pojo.setBookvalue(entity.getBookvalue() - amortamt);
                        pojo.setYtm(entity.getYtm());
                        
                        String marked = remoteObj.getMarkByControlNo(entity.getInvestmentMasterPK().getControlno(), "GOVERNMENT SECURITIES", ymd.format(dmy.parse(asOnDt)));
                         if (marked.equalsIgnoreCase("")) {
                                marked = entity.getMarking();
                            }
                            if (marked.equalsIgnoreCase("HM")) {
                                pojo.setMarking("HM");
                            } else if (marked.equalsIgnoreCase("HT")) {
                                pojo.setMarking("HT");
                            } else if (marked.equalsIgnoreCase("AF")) {
                                pojo.setMarking("AF");
                            } else {
                                pojo.setMarking("");
                            }
                        double rate =0d;
                        if(entity.getPricegsec() != null){
                            rate = entity.getPricegsec();
                        }
                        pojo.setRate(rate);
                        pojo.setCostPrice(entity.getBookvalue());
                        double amortAmtBetween = remoteObj.getAmortAmtBetweenDate(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(previousDate)), ymd.format(dmy.parse(asOnDt)));
                        pojo.setPremiumAmort(amortAmtBetween);                        
                        for(InvestmentMaster entity1 : resultListPreFinYear){
                            if(entity.getInvestmentMasterPK().getControlno()==entity1.getInvestmentMasterPK().getControlno()){
                                pojo.setBookValueOld(entity1.getBookvalue());
                            }
                        }
                        reportList.add(pojo);
                    }
                } else {
                    this.setMessage("Data does not exist !");
                    return;
                }
            } else {
                List<GoiReportPojo> resultList = remoteObj.getIndivisualReportTypeAllSellarNameAndMArking1("GOVERNMENT SECURITIES", this.repType, ymd.format(dmy.parse(asOnDt)));
                if (!resultList.isEmpty()) {
                    for (int i=0;i<resultList.size();i++){
                        GoiReportPojo pojo = new GoiReportPojo();
                        pojo.setCtrlNo(resultList.get(i).getCtrlNo());
                        pojo.setSecDtl(resultList.get(i).getSecDtl());
                        pojo.setSellarName(resultList.get(i).getSellarName());
                        pojo.setSettleDt(resultList.get(i).getSettleDt());
                        pojo.setMatDt(resultList.get(i).getMatDt());
                        pojo.setFaceValue(resultList.get(i).getFaceValue());
                        double amortamt = remoteObj.getAmortAmt(resultList.get(i).getCtrlNo(), ymd.format(dmy.parse(asOnDt)));
                        pojo.setBookvalue(resultList.get(i).getBookvalue()- amortamt);
                        pojo.setYtm(resultList.get(i).getYtm());
                        pojo.setMarking(resultList.get(i).getMarking());                    
                        double rate =0d;                  
                        rate = resultList.get(i).getRate();           
                        pojo.setCostPrice(resultList.get(i).getBookvalue());
                        pojo.setRate(rate);
                        for(InvestmentMaster entity1 : resultListPreFinYear){
                            if(resultList.get(i).getCtrlNo()==entity1.getInvestmentMasterPK().getControlno()){
                                pojo.setBookValueOld(entity1.getBookvalue());
                            }
                        }
                        double amortAmtBetween = remoteObj.getAmortAmtBetweenDate(resultList.get(i).getCtrlNo(), ymd.format(dmy.parse(previousDate)), ymd.format(dmy.parse(asOnDt)));
                        pojo.setPremiumAmort(amortAmtBetween);
                        reportList.add(pojo);
                    }
                } else {
                    this.setMessage("Data does not exist !");
                    return;
                }
            }
            List<InvestmentMaster> resultListParams = remoteObj.getIndivisualReportTypeAllSellarName("GOVERNMENT SECURITIES", reportAsOnDt);
            double bookValue=0d;
            double invst25=0d;
            double ndtl25=0d;
            if(!resultListParams.isEmpty()){
                for (InvestmentMaster entity : resultListParams) {
                    double amortamt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(asOnDt)));
                    bookValue = bookValue + entity.getBookvalue() - amortamt;                    
                }
                invst25 = (bookValue * 25 )/100 ;
            }
            List<CrrSlrPojo> ndtlList = hoRemote.getCrrDetails("0A", "R", asOnDt, asOnDt,"ALL");
            double ndtl =0d;
            if(!ndtlList.isEmpty()){
                CrrSlrPojo vect = ndtlList.get(0);
                ndtl= vect.getNtdl();
                ndtl25 = ( ndtl * 25 )/100;
            }
            String reportName ="";
            if(repType.equalsIgnoreCase("ALL")){
                reportName = "SECURITY HELD IN GOVERNMENT SECURITY(HTM,HFT& AFS)";
            } else if(repType.equalsIgnoreCase("HM")){
                reportName = "SECURITY HELD IN HTM(HELD TO MATURITY) CATEGORY";
            } else if(repType.equalsIgnoreCase("HT")){
                reportName = "SECURITY HELD IN HFT(HELD FOR TRADING) CATEGORY";
            } else if(repType.equalsIgnoreCase("AF")){
                reportName = "SECURITY HELD IN AFS(AVAILABLE FOR SALE) CATEGORY";
            }
            Map fillParams = new HashMap();
            String repName = "GOI Security Detail Report";
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", brList.get(0));
            fillParams.put("pBranchAddress", brList.get(1));
            fillParams.put("pReportName", repName);
            fillParams.put("pReportDate", dmy.format(dmy.parse(this.getAsOnDt())));
            fillParams.put("pTotalInvstmnt", bookValue);
            fillParams.put("pNDTL", ndtl);
            fillParams.put("pTotalInvstmnt25", invst25);
            fillParams.put("pNDTL25", ndtl25);
            fillParams.put("reportName", reportName);
            fillParams.put("pPrevYear", prevDate);
            fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            new ReportBean().openPdf("GOI DETAIL", "goiReportDetail", new JRBeanCollectionDataSource(reportList), fillParams);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpRequest().getSession();
            httpSession.setAttribute("ReportName", repName);
        } catch (ApplicationException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void resetAction() {
        this.setMessage("");
        this.setAsOnDt(dmy.format(new Date()));
    }

    public String exitAction() {
        try {
            resetAction();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

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
    
}
