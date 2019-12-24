/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.CrrSlrPojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.facade.report.RbiQuarterlyReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.StmtOneToTenFacadeRemote;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.Oss4JrxmlPojo;
import java.math.BigDecimal;
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
 * @author SAMY
 */
public class MisSheet extends BaseBean {
    
    private String toDate;
    private String message;
    private String repOpt;
    private String repName;
    private List<SelectItem> repOptionList;
    private List<SelectItem> repNameList;
    Date dt = new Date();
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private LoanReportFacadeRemote loanRemote = null;
    private NpaReportFacadeRemote npaRemote = null;
    private RbiMonthlyReportFacadeRemote monthlyRemote = null;
    private StmtOneToTenFacadeRemote stmt;
    private RbiReportFacadeRemote ossBeanRemote;
    private RbiQuarterlyReportFacadeRemote rbiQtrRemote;
    private final String jndiHomeNameInv = "InvestmentReportMgmtFacade";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public MisSheet() {
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameInv);
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            npaRemote = (NpaReportFacadeRemote) ServiceLocator.getInstance().lookup("NpaReportFacade");
            monthlyRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiMonthlyReportFacade");
            stmt = (StmtOneToTenFacadeRemote) ServiceLocator.getInstance().lookup("StmtOneToTenFacade");
            ossBeanRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            rbiQtrRemote = (RbiQuarterlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiQuarterlyReportFacade");
            
            setMessage("");
            repOptionList = new ArrayList<>();
            repOptionList.add(new SelectItem("", "--Select--"));
            repOptionList.add(new SelectItem("R", "Rs."));
            repOptionList.add(new SelectItem("T", "Thousand"));
            repOptionList.add(new SelectItem("L", "Lacs"));
            repOptionList.add(new SelectItem("C", "Crore"));
            repNameList = new ArrayList<>();
            repNameList.add(new SelectItem("", "--Select--"));
            repNameList.add(new SelectItem("1", "Part 1"));
            repNameList.add(new SelectItem("2", "Part 2"));
            repNameList.add(new SelectItem("3", "Part 3"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    public void viewPdf() {
        Map map = new HashMap();
        try {
            if (this.toDate == null) {
                setMessage("Please Fill To Date");
                return;
            }
            if (this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            if (this.repName.equalsIgnoreCase("")) {
                setMessage("Please select the Report Name!!! ");
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
            BigDecimal reptOpt = new BigDecimal("1");
            if (repOpt.equalsIgnoreCase("T")) {
                amtIn = "Amount (Rs. in Thousand)";
                reptOpt = new BigDecimal("1000");
            } else if (repOpt.equalsIgnoreCase("L")) {
                amtIn = "Amount (Rs. in Lac)";
                reptOpt = new BigDecimal("100000");
            } else if (repOpt.equalsIgnoreCase("C")) {
                amtIn = "Amount (Rs. in Crore)";
                reptOpt = new BigDecimal("10000000");
            } else if (repOpt.equalsIgnoreCase("R")) {                
                amtIn = "Amount (Rs.)";
            }
            map.put("repToDate", this.toDate);
            map.put("sysDate", this.dt);
            map.put("username", this.getUserName());
            map.put("report", "MIS SHEET");
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            map.put("pAmtIn", amtIn);
            map.put("pPrintedDate", sdf.format(this.dt));
            BigDecimal bookValueHM = new BigDecimal("0"),faceValueHM = new BigDecimal("0");
            BigDecimal bookValueAFS = new BigDecimal("0"),faceValueAFS = new BigDecimal("0");
            BigDecimal bookValueHT = new BigDecimal("0"),faceValueHT = new BigDecimal("0");
            BigDecimal indExposure = new BigDecimal("0"),groupExposure = new BigDecimal("0");
            BigDecimal totalAsset = new BigDecimal("0"),tenPercOfTotAsset = new BigDecimal("0"),fivePercOfTotAsset = new BigDecimal("0");
            BigDecimal hsBelow25Lkh = new BigDecimal("0"),hsAbove25Lkh = new BigDecimal("0"),cre = new BigDecimal("0");
            BigDecimal unSecured = new BigDecimal("0"), totalLoanAmt = new BigDecimal("0");
            BigDecimal totalPriorAmt = new BigDecimal("0"),priorPerc = new BigDecimal("0"),totalPriorPrev = new BigDecimal("0");
            BigDecimal totalWKAmt = new BigDecimal("0"),wKPerc = new BigDecimal("0");
            BigDecimal npaopeningBal = new BigDecimal("0"),addInMonth= new BigDecimal("0"),reduceInMonth = new BigDecimal("0"), closingBal = new BigDecimal("0");
            int noOfAcs = 0;
            String jrXMLName = "MIS_Sheet_RBI_Cover";
            List<CrrSlrPojo> crrList = new ArrayList<>();
            List<Oss4JrxmlPojo> jrxmlList = new ArrayList<>();     //Actual List To Print
            Oss4JrxmlPojo jrxmlPojo = new Oss4JrxmlPojo();
            //1. Call Money
            if (repName.equalsIgnoreCase("1")) {
                System.out.println("Start Time"+new Date());
                List reportList = new ArrayList<>();
                reportList = stmt.misSheetPart1Data(CbsUtil.getFirstDateOfGivenDate((sdf.parse(toDate))), ymd.format(sdf.parse(toDate)), reptOpt);
                if (!reportList.isEmpty()) {
                    Vector dcmVect = (Vector) reportList.get(0);
                    map.put("pTotalCallMoney", new BigDecimal(dcmVect.get(0).toString()).divide(reptOpt).toString());
                    map.put("pMinRoi", dcmVect.get(1).toString());
                    map.put("pMaxRoi", dcmVect.get(2).toString());
                    map.put("pIntRec", new BigDecimal(dcmVect.get(3).toString()).divide(reptOpt).toString());
                }
                jrxmlPojo.setPartD(new JRBeanCollectionDataSource(reportList));
                //3. Investment G Sec
                List<InvestmentMaster> resultListHM = remoteObj.getIndivisualReportTypeAllSellarNameAndMArking("GOVERNMENT SECURITIES", "HM", sdf.parse(toDate));
                if (!resultListHM.isEmpty()) {
                    for (InvestmentMaster entity : resultListHM) {
                        BigDecimal tresurBill = new BigDecimal(entity.getBookvalue());
                        double amortizationAmt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(sdf.parse(toDate)));
                        bookValueHM = bookValueHM.add(tresurBill.subtract(new BigDecimal(amortizationAmt)));
                        faceValueHM = faceValueHM.add(new BigDecimal(entity.getFacevalue()));
                    }
                }
                List<InvestmentMaster> resultListAF = remoteObj.getIndivisualReportTypeAllSellarNameAndMArking("GOVERNMENT SECURITIES", "AF", sdf.parse(toDate));
                if (!resultListAF.isEmpty()) {
                    for (InvestmentMaster entity : resultListAF) {BigDecimal tresurBill = new BigDecimal(entity.getBookvalue());
                    double amortizationAmt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(sdf.parse(toDate)));
                    bookValueAFS = bookValueAFS.add(tresurBill.subtract(new BigDecimal(amortizationAmt)));
                    faceValueAFS = faceValueAFS.add(new BigDecimal(entity.getFacevalue()));
                    }
                }
                /* This is for the future .in future if there is requirement of Held For trading Values in the report then this can be uncommented .
//                List<InvestmentMaster> resultListHT = remoteObj.getIndivisualReportTypeAllSellarNameAndMArking("GOVERNMENT SECURITIES", "HT", sdf.parse(toDate));
//                if (!resultListHT.isEmpty()) {
//                    for (InvestmentMaster entity : resultListHT) {
//                        BigDecimal tresurBill = new BigDecimal(entity.getBookvalue());
//                        double amortizationAmt = remoteObj.getAmortAmt(entity.getInvestmentMasterPK().getControlno(), ymd.format(sdf.parse(toDate)));
//                        bookValueHT = bookValueHT.add(tresurBill.subtract(new BigDecimal(amortizationAmt)));
//                        faceValueHT = faceValueHT.add(new BigDecimal(entity.getFacevalue()));
//                    }
//                }*/
                // G- Sec Values
                map.put("pBookValueHM", bookValueHM.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pFaceValueHM", faceValueHM.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pBookValueAFS", bookValueAFS.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pFaceValueAFS", faceValueAFS.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pBookValueHT", bookValueHT.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pFaceValueHT", faceValueHT.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                List<String> dates = new ArrayList<String>();
                
                String preYearLastDt = rbiQtrRemote.getMinFinYear(ymd.format(sdf.parse(toDate)));
                dates.add(CbsUtil.dateAdd(preYearLastDt, -1));
                List osBlancelist = monthlyRemote.getAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode(), dates);
                List part5List = stmt.misSheetPart5Data(osBlancelist,"MIS_SHEET_Part_5",reptOpt,dates, getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode());
                if (!part5List.isEmpty()) {
                    indExposure = new BigDecimal(part5List.get(1).toString());
                    groupExposure = new BigDecimal(part5List.get(0).toString());
                }                
                List part6List = stmt.misSheetPart6Data(osBlancelist,"MIS_SHEET_Part_6",reptOpt,dates, getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : getOrgnBrCode());
                if (!part6List.isEmpty()) {
                    totalAsset = new BigDecimal(part6List.get(0).toString());
                    tenPercOfTotAsset = new BigDecimal(part6List.get(1).toString());
                    fivePercOfTotAsset = new BigDecimal(part6List.get(2).toString());
                }
                //Exposure Values to be set 
                map.put("pIndExposure", indExposure.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pGroupExp", groupExposure.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                List<LoanMisCellaniousPojo> housingLoan1 = loanRemote.cbsLoanMisReport(getOrgnBrCode().equalsIgnoreCase("90")? "0A" : getOrgnBrCode(), "0", "0",
                        ymd.format(sdf.parse(toDate)), "A", 25000000.01, 999999999999.99, "S", "PRIOR","HS", "0", "0", "0", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N","0","N","0","N","N", "0", "0", "0", "0");
                if (!housingLoan1.isEmpty()) {
                    for (int k = 0;k<housingLoan1.size();k++ ) {
                        if (housingLoan1.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            hsAbove25Lkh = hsAbove25Lkh.add(housingLoan1.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : housingLoan1.get(k).getOutstanding());
                        } else {
                            hsAbove25Lkh = hsAbove25Lkh.add(housingLoan1.get(k).getOutstanding());
                        }
                    }
                }
                List<LoanMisCellaniousPojo> housingLoan2 = loanRemote.cbsLoanMisReport(getOrgnBrCode().equalsIgnoreCase("90")? "0A" : getOrgnBrCode(), "0", "0",
                        ymd.format(sdf.parse(toDate)), "A", 0.01, 25000000.00, "S", "PRIOR","HS", "0", "0", "0", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N","0","N","0","N","N", "0", "0", "0", "0");
                if (!housingLoan2.isEmpty()) {
                    for (int k = 0;k<housingLoan2.size();k++ ) {
                        if (housingLoan2.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            hsBelow25Lkh = hsBelow25Lkh.add(housingLoan2.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : housingLoan2.get(k).getOutstanding());
                        } else {
                            hsBelow25Lkh = hsBelow25Lkh.add(housingLoan2.get(k).getOutstanding());
                        }
                    }
                }
                List<LoanMisCellaniousPojo> creList = loanRemote.cbsLoanMisReport(getOrgnBrCode().equalsIgnoreCase("90")? "0A" : getOrgnBrCode(), "0", "0",
                        ymd.format(sdf.parse(toDate)), "A", 0.0, 999999999999.99, "S", "0","0", "0", "0", "0", "HLCRE", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N","0","N","0","N","N", "0", "0", "0", "0");
                if (!creList.isEmpty()) {
                    for (int k = 0;k<creList.size();k++ ) {
                        if (creList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            cre = cre.add(creList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : creList.get(k).getOutstanding());
                        } else {
                            cre = cre.add(creList.get(k).getOutstanding());
                        }
                    }
                }
                // exposure to Housing loan
                map.put("pTotalAssets", totalAsset.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pTenPercOfAsset", tenPercOfTotAsset.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pFivePercOfAsset", fivePercOfTotAsset.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pHSBelow25Lkh", hsBelow25Lkh.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pHSAbove25Lkh", hsAbove25Lkh.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pCRE", cre.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));                
                List<LoanMisCellaniousPojo>  loanList = loanRemote.cbsLoanMisReport(getOrgnBrCode().equalsIgnoreCase("90")? "0A" : getOrgnBrCode(), "0", "0",
                        ymd.format(sdf.parse(toDate)), "A", 0.0, 999999999999.99, "S", "0","0", "0", "0", "0", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "N","0","N","0","N","N", "0", "0", "0", "0");
                if (!loanList.isEmpty()) {
                    for (int k = 0;k<loanList.size();k++ ) {
                        if (loanList.get(k).getSecured().equalsIgnoreCase("UNSEC")) {
                            if (loanList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                unSecured = unSecured.add(loanList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : loanList.get(k).getOutstanding());
                            } else {
                                unSecured = unSecured.add(loanList.get(k).getOutstanding());
                            }
                        }
                    }
                    map.put("unsecured", unSecured.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                }
                if (!loanList.isEmpty()) {
                    for (int k = 0;k<loanList.size();k++ ) {
                        if (loanList.get(k).getSector().equalsIgnoreCase("PRIOR")) {
                            if (loanList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                totalPriorAmt = totalPriorAmt.add(loanList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : loanList.get(k).getOutstanding());
                            } else {
                                totalPriorAmt = totalPriorAmt.add(loanList.get(k).getOutstanding());
                            }
                        }
                    }
                    for (int k = 0;k<loanList.size();k++ ) {
                        if (loanList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            totalLoanAmt = totalLoanAmt.add(loanList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : loanList.get(k).getOutstanding());
                        } else {
                            totalLoanAmt = totalLoanAmt.add(loanList.get(k).getOutstanding());
                        }
                    }
                    priorPerc = totalPriorAmt.divide(totalLoanAmt, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                    for (int k = 0;k<loanList.size();k++ ) {
                        if (loanList.get(k).getSector().equalsIgnoreCase("PRIOR") && loanList.get(k).getApplicantCategory().equalsIgnoreCase("WK")) {
                            if (loanList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                totalWKAmt = totalWKAmt.add(loanList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : loanList.get(k).getOutstanding());
                            } else {
                                totalWKAmt = totalWKAmt.add(loanList.get(k).getOutstanding());
                            }
                        }
                    }
                    if (totalWKAmt.compareTo(new BigDecimal("0"))!=0 ) {
                        wKPerc = totalWKAmt.multiply(new BigDecimal("100")).divide(totalPriorAmt, 2, BigDecimal.ROUND_HALF_UP);
                    }
                    map.put("pTotPriorAmt", totalPriorAmt.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                    map.put("pTotPriorAmtPerc", priorPerc);
                    map.put("pTotPriorAmtPrev", totalPriorPrev.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                    map.put("pTotWKAMT", totalWKAmt.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                    map.put("pTotWKPerc", wKPerc);
                }
                List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", ymd.format(sdf.parse(toDate)), "", getOrgnBrCode().equalsIgnoreCase("90")? "0A" : getOrgnBrCode(), "Y");
                for (int n = 0; n <resultList.size() ; n++) {
                    NpaStatusReportPojo vect =  resultList.get(n);
                    closingBal = closingBal.add(vect.getBalance());
                }
                noOfAcs= resultList.size();
                List<NpaStatusReportPojo> resultList1 = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", CbsUtil.monthAdd(ymd.format(sdf.parse(toDate)),-1), "", getOrgnBrCode().equalsIgnoreCase("90")? "0A" : getOrgnBrCode(), "Y");
                for (int n = 0; n <resultList1.size() ; n++) {
                    NpaStatusReportPojo vect =  resultList1.get(n);
                    npaopeningBal = npaopeningBal.add(vect.getBalance());                    
                }
                noOfAcs= resultList1.size();
                map.put("pOpeningNpa", npaopeningBal.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pClosingNpa", closingBal.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pAddNPA", bookValueHT.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                map.put("pRemoveNPA", faceValueHT.divide(reptOpt, 2, BigDecimal.ROUND_HALF_UP));
                System.out.println("End Time"+new Date());
                jrXMLName = "MIS_Sheet_RBI_Cover";
            } else if (repName.equalsIgnoreCase("2")) {
                //2. CRR/SLR
                System.out.println("Start Time"+new Date());
                crrList = hoRemote.getSlrDetailsMis(getOrgnBrCode(), repOpt, sdf.format(ymd.parse(CbsUtil.getFirstDateOfGivenDate((sdf.parse(toDate))))), toDate,"ALL");                
                System.out.println("End Time"+new Date());
                jrXMLName = "MIS_PART_1";
            } else if (repName.equalsIgnoreCase("3")) {
                //4.Director Related Loans
                List<LoanMisCellaniousPojo> dirListFB = new ArrayList<>();
                List<LoanMisCellaniousPojo> dirListNFB = new ArrayList<>();
                List<LoanMisCellaniousPojo> dirRelList = new ArrayList<>();
                List<LoanMisCellaniousPojo> loanMISList = new ArrayList<LoanMisCellaniousPojo>();
                loanMISList = loanRemote.cbsLoanMisReport(getOrgnBrCode().equalsIgnoreCase("90")? "0A" : getOrgnBrCode(), "0", "0",
                        ymd.format(sdf.parse(toDate)), "A", 0, 999999999999.99, "S", "0",
                        "0", "0", "0", "0", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "N", "S","0","N","0","N","N", "0", "0", "0", "0");
                if (!loanMISList.isEmpty()) {
                    for (int i=0;i<loanMISList.size();i++ ) {
                        if (loanMISList.get(i).getRelation().equalsIgnoreCase("DIR") ||loanMISList.get(i).getRelation().equalsIgnoreCase("DIRREL")) {
                            if (loanMISList.get(i).getExposureCategory().equalsIgnoreCase("NFB") || loanMISList.get(i).getExposureCategory().equalsIgnoreCase("NFBOLG")) {
                                LoanMisCellaniousPojo dirPojo= new LoanMisCellaniousPojo();
                                dirPojo.setCustName(loanMISList.get(i).getCustName());
                                dirPojo.setAcNo(loanMISList.get(i).getAcNo());
                                dirPojo.setSanctionDt(loanMISList.get(i).getSanctionDt());
                                dirPojo.setSanctionAmt(loanMISList.get(i).getSanctionAmt().divide(reptOpt));
                                dirPojo.setOutstanding(loanMISList.get(i).getOutstanding().divide(reptOpt).abs());
                                dirPojo.setLoanExpiryDt(loanMISList.get(i).getLoanExpiryDt());
                                dirListNFB.add(dirPojo);
                            } else {
                                LoanMisCellaniousPojo dirPojo= new LoanMisCellaniousPojo();
                                dirPojo.setCustName(loanMISList.get(i).getCustName());
                                dirPojo.setAcNo(loanMISList.get(i).getAcNo());
                                dirPojo.setSanctionDt(loanMISList.get(i).getSanctionDt());
                                dirPojo.setSanctionAmt(loanMISList.get(i).getSanctionAmt().divide(reptOpt));
                                dirPojo.setOutstanding(loanMISList.get(i).getOutstanding().divide(reptOpt).abs());
                                dirPojo.setLoanExpiryDt(loanMISList.get(i).getLoanExpiryDt());
                                dirListFB.add(dirPojo);
                            }                            
                        }
                        if (loanMISList.get(i).getRelation().equalsIgnoreCase("DIRREL") || loanMISList.get(i).getRelation().equalsIgnoreCase("SECREL") || loanMISList.get(i).getRelation().equalsIgnoreCase("MGRREL")) {
                            LoanMisCellaniousPojo dirPojo= new LoanMisCellaniousPojo();
                            dirPojo.setCustName(loanMISList.get(i).getCustName());
                            dirPojo.setAcNo(loanMISList.get(i).getAcNo());
                            dirPojo.setSanctionDt(loanMISList.get(i).getSanctionDt());
                            dirPojo.setSanctionAmt(loanMISList.get(i).getSanctionAmt().divide(reptOpt));
                            dirPojo.setOutstanding(loanMISList.get(i).getOutstanding().divide(reptOpt).abs());
                            dirPojo.setLoanExpiryDt(loanMISList.get(i).getLoanExpiryDt());
                            dirRelList.add(dirPojo);
                        }
                    }
                }
                jrXMLName="MIS_Sheet_Cover";
                jrxmlPojo.setPartGExpPojo(new JRBeanCollectionDataSource(dirListFB));
                jrxmlPojo.setPartGSecAdvPojo(new JRBeanCollectionDataSource(dirListNFB));
                jrxmlPojo.setPartGUnSecAdvPojo(new JRBeanCollectionDataSource(dirRelList));
            }
            jrxmlList.add(jrxmlPojo);
            report = "MIS SHEET";
            map.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            if (repName.equalsIgnoreCase("1") || repName.equalsIgnoreCase("3") ) {
                new ReportBean().openPdf("MIS_As_On_"+ymd.format(sdf.parse(toDate)), jrXMLName, new JRBeanCollectionDataSource(jrxmlList), map);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else if (repName.equalsIgnoreCase("2")) {
                new ReportBean().openPdf("MIS_As_On_"+ymd.format(sdf.parse(toDate)), jrXMLName, new JRBeanCollectionDataSource(crrList), map);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }            
        } catch (Exception e) {
            setMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    public void refresh(){
        setMessage("");
        setRepOpt("");
    }
    
    public String exit() {
        return "case1";
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
    public String getToDate() {
        return toDate;
    }
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    public String getRepName() {
        return repName;
    }
    public void setRepName(String repName) {
        this.repName = repName;
    }
    public List<SelectItem> getRepNameList() {
        return repNameList;
    }
    public void setRepNameList(List<SelectItem> repNameList) {
        this.repNameList = repNameList;
    }    
}
