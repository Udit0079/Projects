/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.PortFolioClassificationPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.Soss3PortFolioPojo;
import com.cbs.dto.report.PortFolioConPojo;
import com.cbs.dto.report.PortFolioLoanAdvPojo;
import com.cbs.dto.report.PortFolioOibaPojo;
import com.cbs.dto.report.ProvDetailOfLoanAccounts;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.RbiSoss1And2ReportFacadeRemote;
import com.cbs.facade.report.RbiSoss3ReportFacadeRemote;
import com.cbs.pojo.SortedByCategory;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.Soss3PortFolio;
import com.cbs.web.pojo.investment.GoiReportPojo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author sipl
 */
public class Oss3Controller extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String calDate;
    private String reportIn;
    private String curdate;
    private List<SelectItem> reportInList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private GLReportFacadeRemote glBeanRemote;
    private RbiReportFacadeRemote ossBeanRemote;
    private RbiSoss1And2ReportFacadeRemote rbiSoss1And2Remote; 
    private RbiSoss3ReportFacadeRemote oss3BeanRemote;
    private CommonReportMethodsRemote common = null;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    private HoReportFacadeRemote hoRemote = null;
    private final String jndiName = "CommonReportMethods";
    Date dt = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getCurdate() {
        return curdate;
    }

    public void setCurdate(String curdate) {
        this.curdate = curdate;
    }

    public void setCommon(CommonReportMethodsRemote common) {
        this.common = common;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public NumberFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(NumberFormat formatter) {
        this.formatter = formatter;
    }

    public GLReportFacadeRemote getGlBeanRemote() {
        return glBeanRemote;
    }

    public void setGlBeanRemote(GLReportFacadeRemote glBeanRemote) {
        this.glBeanRemote = glBeanRemote;
    }

    public HoReportFacadeRemote getHoRemote() {
        return hoRemote;
    }

    public void setHoRemote(HoReportFacadeRemote hoRemote) {
        this.hoRemote = hoRemote;
    }

    public RbiReportFacadeRemote getOssBeanRemote() {
        return ossBeanRemote;
    }

    public void setOssBeanRemote(RbiReportFacadeRemote ossBeanRemote) {
        this.ossBeanRemote = ossBeanRemote;
    }

    public InvestmentReportMgmtFacadeRemote getRemoteObj() {
        return remoteObj;
    }

    public void setRemoteObj(InvestmentReportMgmtFacadeRemote remoteObj) {
        this.remoteObj = remoteObj;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
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

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportIn() {
        return reportIn;
    }

    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }

    public List<SelectItem> getReportInList() {
        return reportInList;
    }

    public void setReportInList(List<SelectItem> reportInList) {
        this.reportInList = reportInList;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public Oss3Controller() {
        reportTypeList = new ArrayList<SelectItem>();
        reportInList = new ArrayList<SelectItem>();
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            ossBeanRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            rbiSoss1And2Remote = (RbiSoss1And2ReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiSoss1And2ReportFacade");
            oss3BeanRemote = (RbiSoss3ReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiSoss3ReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            List brnLst = new ArrayList();
            brnLst = glBeanRemote.getBranchCodeListExt(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            reportTypeList.add(new SelectItem("", "--Select--"));
            reportTypeList.add(new SelectItem("SOSS3_PORTFOLIO", "PORTFOLIO ANALYSIS"));
            reportTypeList.add(new SelectItem("Provision_Acc_Loan", "PROVISION ACCORDING TO LOAN"));
            reportTypeList.add(new SelectItem("XB", "XBRL OSS3"));

            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "Thousand"));
            reportInList.add(new SelectItem("L", "Lacs"));
            reportInList.add(new SelectItem("C", "Crore"));

            this.setCalDate(calDate);

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void downloadPdf() {
        this.setMessage("");
        try {
            String reportName = "";
            Map fillParams = new HashMap();
            BigDecimal reptOpt = null;
            if (this.reportType.equalsIgnoreCase("SOSS3_PORTFOLIO") || this.reportType.equalsIgnoreCase("XB")) {
                /**
                 * SOSS3 PORTFOLIO ANALYSIS Processing
                 */
                if (this.reportIn.equalsIgnoreCase("T")) {
                    reptOpt = new BigDecimal("1000");
                } else if (this.reportIn.equalsIgnoreCase("L")) {
                    reptOpt = new BigDecimal("100000");
                } else if (this.reportIn.equalsIgnoreCase("C")) {
                    reptOpt = new BigDecimal("10000000");
                } else if (this.reportIn.equalsIgnoreCase("R")) {
                    reptOpt = new BigDecimal("1");
                }
                String reptName = "";
                if (reportType.equalsIgnoreCase("SOSS3_PORTFOLIO")) {
                    reptName = "SOSS3";
                    reportName = "SOSS - 3 Statement on Asset Quality";
                } else if(reportType.equalsIgnoreCase("XB")) {
                    reptName = "SOSS3";
                    reportName = "XBRL OSS3";
                }
                List<Soss3PortFolioPojo> resultList = oss3BeanRemote.getSOSS3(reptName, ymd.format(dmy.parse(calDate)), branch, reptOpt);
                List<Soss3PortFolio> soss3PortFoliotable = new ArrayList<Soss3PortFolio>();//Actual
                Soss3PortFolio soss3PortFolioPojo = new Soss3PortFolio();
                Soss3PortFolioPojo classPojo = null;
                for (int i = 0; i < resultList.size(); i++) {
                    classPojo = resultList.get(i);
                    List<PortFolioLoanAdvPojo> loanAdvPojo  = classPojo.getPortFolioLoanAdvList();
                    List<PortFolioOibaPojo> oibaPojo        = classPojo.getPortFolioOibaList();
                    List<PortFolioConPojo> consolidatePojo  = classPojo.getPortFolioConList();
                    List<PortFolioClassificationPojo> classificationPojo  = classPojo.getPortFolioClassificationList();
                    List<PortFolioConPojo> investmentPojo  = classPojo.getPortFolioInvestmentList();
                    List<PortFolioConPojo> nonSlrSecurityPojo  = classPojo.getPortFolioNonSlrSecurityList();
                    List<PortFolioConPojo> balanceSheetPojo  = classPojo.getPortFolioBalanceSheetList();
//                    for(int j=0;j<classPojo.getPortFolioClassificationList().size();j++){
//                        System.out.println("hi: "+i+j+" : "+classPojo.getPortFolioClassificationList().get(j).getsNo()+", amt:"+classPojo.getPortFolioClassificationList().get(j).getGrossOutstd()+", desc:"+classPojo.getPortFolioClassificationList().get(j).getDescription());
//                    }
                    soss3PortFolioPojo.setPortFolioConList(new JRBeanCollectionDataSource(consolidatePojo));
                    soss3PortFolioPojo.setPortFolioLoanAdvList(new JRBeanCollectionDataSource(loanAdvPojo));
                    soss3PortFolioPojo.setPortFolioOibaList(new JRBeanCollectionDataSource(oibaPojo));
                    soss3PortFolioPojo.setPortFolioClassificationList(new JRBeanCollectionDataSource(classificationPojo));
                    soss3PortFolioPojo.setPortFolioBalanceSheetList(new JRBeanCollectionDataSource(balanceSheetPojo));
                    soss3PortFolioPojo.setPortFolioInvestmentList(new JRBeanCollectionDataSource(investmentPojo));
                    soss3PortFolioPojo.setPortFolioNonSlrSecurityList(new JRBeanCollectionDataSource(nonSlrSecurityPojo));
                    soss3PortFoliotable.add(soss3PortFolioPojo);
                }

                if (!resultList.isEmpty()) {
                    //It is report printing.
                    List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                    // for treasue bill                  
                    double treBill =0d;
                    double goi =0d;
                    double stGovmt =0d;
                    List goiList = common.getGOIParameter();
                    if(goiList.isEmpty()) {
                        List<InvestmentMaster> resultList1 = remoteObj.getIndivisualReportTypeAllSellarName("TREASURY BILLS",dmy.parse(calDate));
                        if (!resultList1.isEmpty()) {
                            for (InvestmentMaster entity : resultList1) {
                                GoiReportPojo pojo = new GoiReportPojo();
                                // pojo.setBookvalue(entity.getBookvalue());
                                double tresurBill = entity.getBookvalue();
                                double amortizationAmt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(calDate)));
                                double bal =tresurBill - amortizationAmt;
                                treBill = treBill + bal;
                            }
                        }
                        //For GOI Security
                        
                        List<InvestmentMaster> resultList2 = remoteObj.getIndivisualReportTypeAllSellarName("GOVERNMENT SECURITIES",dmy.parse(calDate));
                        if (!resultList2.isEmpty()) {
                            for (InvestmentMaster entity : resultList2) {
                                GoiReportPojo pojo = new GoiReportPojo();
                                pojo.setBookvalue(entity.getBookvalue());
                                pojo.setYtm(entity.getYtm());
                                double goind = entity.getBookvalue();
                                double amortizationAmt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(calDate)));
                                double bal =goind - amortizationAmt;
                                goi = goi + bal;
                            }
                        }
                        // For StateGovernment Security                        

                        List<InvestmentMaster> resultList3 = remoteObj.getIndivisualReportTypeAllSellarName("STATE GOVT. BONDS",dmy.parse(calDate));
                        if (!resultList3.isEmpty()) {
                            for (InvestmentMaster entity : resultList3) {
                                GoiReportPojo pojo = new GoiReportPojo();
                                pojo.setBookvalue(entity.getBookvalue());
                                pojo.setYtm(entity.getYtm());
                                double stateGovmt = entity.getBookvalue();
                                double amortizationAmt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(dmy.parse(calDate)));
                                double bal = stateGovmt - amortizationAmt;
                                stGovmt = stGovmt +bal;
                            }

                        }
                    } else if (!goiList.isEmpty()) {
                        List<RbiSossPojo> resultListSoss1 = rbiSoss1And2Remote.getSOSS1("OSS3TBILL", ymd.format(dmy.parse(calDate)), branch, reptOpt, "Y");
                        if(!resultListSoss1.isEmpty()){
                            for( int z=0; z<resultListSoss1.size();z++) {
                                treBill = treBill + new Double(resultListSoss1.get(z).getAmt().multiply(reptOpt).doubleValue());
                            }
                        }
                        List<RbiSossPojo> resultListSoss2 = rbiSoss1And2Remote.getSOSS1("OSS3GOIBONDS", ymd.format(dmy.parse(calDate)), branch, reptOpt, "Y");
                        if(!resultListSoss2.isEmpty()){
                            for( int z=0; z<resultListSoss2.size();z++){
                                goi = goi + new Double(resultListSoss2.get(z).getAmt().multiply(reptOpt).doubleValue());
                            }
                        }
                        List<RbiSossPojo> resultListSoss3 = rbiSoss1And2Remote.getSOSS1("OSS3SGS", ymd.format(dmy.parse(calDate)), branch, reptOpt, "Y");
                        if(!resultListSoss3.isEmpty()){
                            for( int z=0; z<resultListSoss3.size();z++){
                                stGovmt = stGovmt + new Double(resultListSoss3.get(z).getAmt().multiply(reptOpt).doubleValue());
                            }
                        }
                    }
                    // For NPA Amount to be shown
//                    curdate = ymd.format(getLastDateOfMonth(ymd.parse(monthAdd(calDate, -3))));
//                    BigDecimal Npaamtcur= new BigDecimal("0");
//                    BigDecimal Npaamtpre= new BigDecimal("0");
//                    
//                      List<NpaStatusReportPojo> resultList4 = npaRemote.getNpaStatusReportData1("0", "ALL", "19000101", ymd.format(dmy.parse(calDate)),  "", branch.equalsIgnoreCase("90") ? "0A" : branch, "Y");
//                     if(!resultList4.isEmpty()){
//                         
//                         for(NpaStatusReportPojo entity : resultList4){
//                         NpaStatusReportPojo pojo = new NpaStatusReportPojo();
//                         pojo.setBalance(entity.getBalance());
//                         BigDecimal npa= entity.getBalance();
//                         Npaamtcur = Npaamtcur.add(npa);
//                         }
//                     }
                    //      To Implement the Memorandm Items in SOSS3 
                    BigDecimal currMemo = new BigDecimal("0");
                    List<RbiSossPojo> resultListSoss1 = rbiSoss1And2Remote.getSOSS1("SOSS3MEMO", ymd.format(dmy.parse(calDate)), branch, reptOpt, "Y");
                     if(!resultListSoss1.isEmpty()){                         
                         for( int z=0; z<resultListSoss1.size();z++){
                            currMemo = currMemo.add(resultListSoss1.get(z).getAmt());
                        }
                    }

                    BigDecimal preMemo = new BigDecimal("0");
                    resultListSoss1 = rbiSoss1And2Remote.getSOSS1("SOSS3MEMO", ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(CbsUtil.monthAdd(ymd.format(dmy.parse(calDate)), -3)))), branch, reptOpt, "Y");
                     if(!resultListSoss1.isEmpty()){                         
                         for( int z=0; z<resultListSoss1.size();z++){
                            preMemo = preMemo.add(resultListSoss1.get(z).getAmt());
                        }
                    }
                    fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                    fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                    fillParams.put("username", getUserName());
                    fillParams.put("report", reportName);
                    fillParams.put("pPrintedDate", calDate);
                    fillParams.put("pIntLoanAdvArrerOnNpaLastQtr", preMemo.abs());
                    fillParams.put("pIntLoanAdvArrerOnNpaCurrQtr", currMemo.abs());
                    fillParams.put("pOibaTreBill", new BigDecimal(treBill).divide(reptOpt));
                    fillParams.put("pOibaGoi", new BigDecimal(goi).divide(reptOpt));
                    fillParams.put("pOibaStateGovtSec", new BigDecimal(stGovmt).divide(reptOpt));

                    String reportDesc = "";
                    if (this.reportIn.equals("T")) {
                        reportDesc = "Amount (Rs. in Thousand)";
                    } else if (this.reportIn.equals("L")) {
                        reportDesc = "Amount (Rs. in Lac)";
                    } else if (this.reportIn.equals("C")) {
                        reportDesc = "Amount (Rs. in Crore)";
                    } else if (this.reportIn.equals("R")) {
                        reportDesc = "Amount (Rs.)";
                    }
                    fillParams.put("pAmtIn", reportDesc);
                    fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                    new ReportBean().downloadPdf("SOSS3_" + ymd.format(dmy.parse(calDate)), "SOSS3_PORTFOLIO_1", new JRBeanCollectionDataSource(soss3PortFoliotable), fillParams);
                }
            } else if (reportType.equalsIgnoreCase("Provision_Acc_Loan")) {
                String reptName = "";
                if (reportType.equalsIgnoreCase("Provision_Acc_Loan")) {
                    reptName = "Provision According to Loan";
                    reportName = "Provision According to Loan";
                }
                if (this.reportIn.equalsIgnoreCase("T")) {
                    reptOpt = new BigDecimal("1000");
                } else if (this.reportIn.equalsIgnoreCase("L")) {
                    reptOpt = new BigDecimal("100000");
                } else if (this.reportIn.equalsIgnoreCase("C")) {
                    reptOpt = new BigDecimal("10000000");
                } else if (this.reportIn.equalsIgnoreCase("R")) {
                    reptOpt = new BigDecimal("1");
                }
                
                List<ProvDetailOfLoanAccounts> resultlist = ossBeanRemote.getProvisionAccordingToLoan("SOSS3.PART-A", calDate, branch, reptOpt);
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByCategory());
                Collections.sort(resultlist, chObj);
                for(int i=0;i<resultlist.size();i++){
                    ProvDetailOfLoanAccounts obj = resultlist.get(i);                    
                     System.out.println("Acno is-->" + obj.getAcno()+"::outstanding is"+ obj.getOutstanding()+ "ProvAm Is "+ obj.getProvamt()+ "Category is "+ obj.getCategory() + "NPA DT IS : "+obj.getNpadt() + "\n");
                }
                String reportDesc = "";
                if (this.reportIn.equals("T")) {
                    reportDesc = "Amount (Rs. in Thousand)";
                } else if (this.reportIn.equals("L")) {
                    reportDesc = "Amount (Rs. in Lac)";
                } else if (this.reportIn.equals("C")) {
                    reportDesc = "Amount (Rs. in Crore)";
                } else if (this.reportIn.equals("R")) {
                    reportDesc = "Amount (Rs.)";
                }
                if (!resultlist.isEmpty()) {
                    List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                    fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                    fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                    fillParams.put("username", getUserName());
                    fillParams.put("report", reportName);
                    fillParams.put("pPrintedDate", calDate);
                    fillParams.put("pAmtIn", reportDesc);
                    fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                    new ReportBean().downloadPdf("Provision According to Loan_" + ymd.format(dmy.parse(calDate)), "ProvissionAccordingToLoan", new JRBeanCollectionDataSource(resultlist), fillParams);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("");
        this.setReportIn("R");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}