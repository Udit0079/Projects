/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.PrioritySectorPojo;
import com.cbs.dto.report.ho.Form1UnencumAppSecPojo;
import com.cbs.dto.report.ho.Form2StmtUnSecAdvToDirFirmPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.PostfixEvaluator;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "RbiReportFacadeHalfYearly")
public class RbiReportFacadeHalfYearly implements RbiReportFacadeHalfYearlyRemote {

    @EJB
    private LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private RbiMonthlyReportFacadeRemote monthlyRemote;
    @EJB
    private LoanGenralFacadeRemote loanRemote;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat y_m_dFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private NumberFormat nft = new DecimalFormat("0.00");
    Date date = new Date();

    public List<Form2StmtUnSecAdvToDirFirmPojo> getForm2StmtUnSecAdvToDirFirm(String reportDt, String orgnCode, double repOpt) throws ApplicationException {
        try {
            List<Form2StmtUnSecAdvToDirFirmPojo> Form2StmtUnSecAdvToDirFirmTable = new ArrayList<Form2StmtUnSecAdvToDirFirmPojo>();
            List unSecQuery = em.createNativeQuery("select cb.cust_id, cb.acc_No, a.custname,a.openingdt,ifnull(a.odlimit,0), atm.AcctDesc, "
                    + " cb.PURPOSE_OF_ADVANCE as purposeOfAdvance, "
                    + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '190'  and REF_CODE = cb.PURPOSE_OF_ADVANCE),'') as purposeOfAdvanceDesc , "
                    + " a.custid1, ifnull((select custname from cbs_customer_master_detail where customerid =  a.custid1),'') as custIdName1, "
                    + " a.custid2, ifnull((select custname from cbs_customer_master_detail where customerid =  a.custid2),'') as custIdName2, "
                    + " a.custid3, ifnull((select custname from cbs_customer_master_detail where customerid =  a.custid3),'') as custIdName3, "
                    + " a.custid4, ifnull((select custname from cbs_customer_master_detail where customerid =  a.custid4),'') as custIdName4, "
                    + " a.acctCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '325'  and REF_CODE = a.acctCategory),''), "
                    + " cb.REMARKS, atm.acctNature from cbs_loan_borrower_details cb, accountmaster a, cbs_customer_master_detail b, accounttypemaster atm "
                    + " where cb.secured ='UNSEC' and cb.ACC_NO = a.acno and cb.CUST_ID = b.customerid and substring(a.acno,3,2) = atm.AcctCode "
                    + " and cb.relation ='DIR' and (ifnull(a.closingdate,'')='' or a.closingdate > '" + reportDt + "') ").getResultList();


            if (!unSecQuery.isEmpty()) {
                for (int i = 0; i < unSecQuery.size(); i++) {
                    Form2StmtUnSecAdvToDirFirmPojo unSecPojo = new Form2StmtUnSecAdvToDirFirmPojo();
                    Vector unSecVect = (Vector) unSecQuery.get(i);
                    String nameOfDir = "";
                    String custId = unSecVect.get(0).toString();
                    String acNo = unSecVect.get(1).toString();
                    String nameOfCompany = unSecVect.get(2).toString().concat("\n(").concat(acNo).concat(")");
                    String dateOfAdv = unSecVect.get(3).toString();
                    double LimitSanctioned = Double.parseDouble(unSecVect.get(4).toString());
                    String acNature = "(".concat(unSecVect.get(19).toString()).concat(") ").concat(unSecVect.get(5).toString());
                    String purOfAdv = unSecVect.get(6).toString();
                    String purOfAdvDesc = unSecVect.get(7).toString();
                    String jointCustId1 = unSecVect.get(8).toString();
                    String jointCustIdName1 = unSecVect.get(9).toString();
                    String jointCustId2 = unSecVect.get(10).toString();
                    String jointCustIdName2 = unSecVect.get(11).toString();
                    String jointCustId3 = unSecVect.get(12).toString();
                    String jointCustIdName3 = unSecVect.get(13).toString();
                    String jointCustId4 = unSecVect.get(14).toString();
                    String jointCustIdName4 = unSecVect.get(15).toString();
                    String acctCategory = unSecVect.get(16).toString();
                    String acctCategoryDesc = unSecVect.get(17).toString();
                    String remark = unSecVect.get(18).toString();
                    String relOfDirWithBank = "", dateOfRepayment = "";


                    if (acctCategory.equalsIgnoreCase("JLG") || acctCategory.equalsIgnoreCase("LC")
                            || acctCategory.equalsIgnoreCase("PC") || acctCategory.equalsIgnoreCase("PCS")
                            || acctCategory.equalsIgnoreCase("PF") || acctCategory.equalsIgnoreCase("PL")) {
                        /*JLG:	JOINT LIABILITIES GROUP 
                         * LC:	PUBLIC LIMITED COMPANY
                         * PC:	PROPRIETARY CONCERNS
                         * PCS:	PRIVATE/CORPORATE SECTOR
                         * PF:	PROPRIETARY / PARTNERSHIP FIRM
                         * PL:	PRIVATE LIMITED COMPANY*/
                        if (!jointCustId1.equalsIgnoreCase("")) {
                            List desigList = em.createNativeQuery("select desg,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = desg),'') as rel_name "
                                    + " from banks_dir_details where custId = " + jointCustId1).getResultList();
                            if (!desigList.isEmpty()) {
                                Vector desigVect = (Vector) desigList.get(0);
                                nameOfDir = "(".concat(jointCustId1).concat("): ").concat(jointCustIdName1);
                                relOfDirWithBank = relOfDirWithBank.concat(desigVect.get(1).toString().concat(" (").concat(jointCustIdName1).concat(") "));
                            }
                        }
                        if (!jointCustId2.equalsIgnoreCase("")) {
                            List desigList = em.createNativeQuery("select desg,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = desg),'') as rel_name "
                                    + " from banks_dir_details where custId = " + jointCustId2).getResultList();
                            if (!desigList.isEmpty()) {
                                Vector desigVect = (Vector) desigList.get(0);
                                nameOfDir = nameOfDir.concat("\n").concat("(".concat(jointCustId2).concat("): ").concat(jointCustIdName2));
                                relOfDirWithBank = relOfDirWithBank.concat("\n").concat(desigVect.get(1).toString().concat("( ").concat(jointCustIdName2).concat(") "));
                            }
                        }
                        if (!jointCustId3.equalsIgnoreCase("")) {
                            List desigList = em.createNativeQuery("select desg,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = desg),'') as rel_name "
                                    + " from banks_dir_details where custId = " + jointCustId3).getResultList();
                            if (!desigList.isEmpty()) {
                                Vector desigVect = (Vector) desigList.get(0);
                                nameOfDir = nameOfDir.concat("\n").concat("(".concat(jointCustId3).concat("): ").concat(jointCustIdName3));
                                relOfDirWithBank = relOfDirWithBank.concat("\n").concat(desigVect.get(1).toString().concat("( ").concat(jointCustIdName3).concat(") "));
                            }
                        }
                        if (!jointCustId4.equalsIgnoreCase("")) {
                            List desigList = em.createNativeQuery("select desg,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = desg),'') as rel_name "
                                    + " from banks_dir_details where custId = " + jointCustId4).getResultList();
                            if (!desigList.isEmpty()) {
                                Vector desigVect = (Vector) desigList.get(0);
                                nameOfDir = nameOfDir.concat("\n").concat("(".concat(jointCustId4).concat("): ").concat(jointCustIdName4));
                                relOfDirWithBank = relOfDirWithBank.concat("\n").concat(desigVect.get(1).toString().concat("( ").concat(jointCustIdName4).concat(") "));
                            }
                        }

                    } else if (acctCategory.equalsIgnoreCase("IND") || acctCategory.equalsIgnoreCase("UN")) {
                        /*IND:	INDIVIDUAL
                         * UN:	UNCLASSIFIED*/
                        List desigList = em.createNativeQuery("select desg,  ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = desg),'') as rel_name "
                                + " from banks_dir_details where custId = " + custId).getResultList();
                        if (!desigList.isEmpty()) {
                            Vector desigVect = (Vector) desigList.get(0);
                            nameOfDir = nameOfDir.concat("(".concat(custId).concat("): ").concat(nameOfCompany).concat("\n"));
                            relOfDirWithBank = desigVect.get(1).toString();
                        }
                    }

                    if (!nameOfDir.equalsIgnoreCase("")) {
                        /*If Director Name is not blank, Then it will consider it*/
                        String firstDateOfMonth = CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(reportDt));
                        String lastDateOfMonth = ymdFormat.format(CbsUtil.getLastDateOfMonth(ymdFormat.parse(reportDt)));
                        double amtOutStandOnLastDayOfMonth = Math.abs(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, lastDateOfMonth)));
                        double roi;
                        String roiGet = loanInterestCalculationBean.getRoiLoanAccount(amtOutStandOnLastDayOfMonth, lastDateOfMonth, acNo);
                        if (roiGet.substring(0, 2).equalsIgnoreCase("fa")) {
                            throw new ApplicationException(roiGet.substring(6) + " for account " + acNo + "." + "outstanding:" + amtOutStandOnLastDayOfMonth);
                        } else {
                            roi = Double.parseDouble(roiGet);
                        }

                        List emiList = em.createNativeQuery("select ifnull(DATE_FORMAT(min(duedt),'%D'),'19000101') from emidetails where acno ='" + acNo + "'").getResultList();
                        if (!emiList.isEmpty()) {
                            Vector emiVect = (Vector) emiList.get(0);
                            dateOfRepayment = (!emiVect.get(0).toString().equalsIgnoreCase("")) ? (emiVect.get(0).toString().equalsIgnoreCase("19000101") ? "" : emiVect.get(0).toString().concat(" of every month")) : "";
                        }

                        ArrayList<Double> arraylist = new ArrayList<Double>();
                        arraylist.add(Math.abs(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, firstDateOfMonth))));
                        arraylist.add(Math.abs(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, lastDateOfMonth))));
                        List tranList = em.createNativeQuery("select date_format(dt,'%Y%m%d') from " + common.getTableName(common.getAcNatureByAcType(acNo.substring(2, 4))) + " where acno ='" + acNo + "' and dt between '" + firstDateOfMonth + "' and '" + lastDateOfMonth + "'").getResultList();
                        if (!tranList.isEmpty()) {
                            for (int z = 0; z < tranList.size(); z++) {
                                Vector tranVect = (Vector) tranList.get(z);
                                arraylist.add(Math.abs(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, tranVect.get(0).toString()))));
                            }
                        }
                        Collections.sort(arraylist);
                        double lowestOutStandDuringMonth = arraylist.get(0);

                        unSecPojo.setGroupNo("1");
                        unSecPojo.setSrNo(i + 1);
                        unSecPojo.setNameOfDir(nameOfDir);
                        unSecPojo.setNameOfCompany(nameOfCompany);
                        unSecPojo.setRelOfDirWithBank(relOfDirWithBank);
                        unSecPojo.setLimitSanctioned(LimitSanctioned / repOpt);
                        unSecPojo.setAmtOutStandOnLastDayOfMonth(amtOutStandOnLastDayOfMonth / repOpt);
                        unSecPojo.setLowestOutStandDuringMonth(lowestOutStandDuringMonth / repOpt);
                        unSecPojo.setRoi(roi);
                        unSecPojo.setDateOfAdv(dateOfAdv);
                        unSecPojo.setPurOfAdv(purOfAdvDesc);
                        unSecPojo.setDateOfRepayment(dateOfRepayment);
                        unSecPojo.setAcNature(acNature);
                        unSecPojo.setRemark(remark);
                        Form2StmtUnSecAdvToDirFirmTable.add(unSecPojo);
                    } else {
                        unSecPojo.setGroupNo("1");
                        unSecPojo.setSrNo(i + 1);
                        unSecPojo.setNameOfDir("NIL");
                        unSecPojo.setNameOfCompany("NIL");
                        unSecPojo.setRelOfDirWithBank("");
                        unSecPojo.setLimitSanctioned(0);
                        unSecPojo.setAmtOutStandOnLastDayOfMonth(0);
                        unSecPojo.setLowestOutStandDuringMonth(0);
                        unSecPojo.setRoi(0);
                        unSecPojo.setDateOfAdv("");
                        unSecPojo.setPurOfAdv("");
                        unSecPojo.setDateOfRepayment("");
                        unSecPojo.setAcNature("");
                        unSecPojo.setRemark("");
                        Form2StmtUnSecAdvToDirFirmTable.add(unSecPojo);
                    }
                }
            } else {
                Form2StmtUnSecAdvToDirFirmPojo unSecPojo = new Form2StmtUnSecAdvToDirFirmPojo();
                unSecPojo.setGroupNo("1");
                unSecPojo.setSrNo(1);
                unSecPojo.setNameOfDir("NIL");
                unSecPojo.setNameOfCompany("NIL");
                unSecPojo.setRelOfDirWithBank("");
                unSecPojo.setLimitSanctioned(0);
                unSecPojo.setAmtOutStandOnLastDayOfMonth(0);
                unSecPojo.setLowestOutStandDuringMonth(0);
                unSecPojo.setRoi(0);
                unSecPojo.setDateOfAdv("");
                unSecPojo.setPurOfAdv("");
                unSecPojo.setDateOfRepayment("");
                unSecPojo.setAcNature("");
                unSecPojo.setRemark("");
                Form2StmtUnSecAdvToDirFirmTable.add(unSecPojo);
            }
            return Form2StmtUnSecAdvToDirFirmTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<PrioritySectorPojo> getPrioritySector(String reportName, String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<PrioritySectorPojo> rbiPojoTable = new ArrayList<PrioritySectorPojo>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION,REFER_INDEX  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reportName + "' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            List<LoanMisCellaniousPojo> resultList = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> resultList1 = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> resultList2 = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> resultList3 = new ArrayList<LoanMisCellaniousPojo>();
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                for (int i = 0; i < oss1Query.size(); i++) {
//                    System.out.println("I "+i);
                    Long totAccountNo = 0l, nAccountNoSC = 0l, nAccountNoST = 0l, nAccountNoMinor = 0l;
                    Long totAccountNoForm = 0l, nAccountNoSCForm = 0l, nAccountNoSTForm = 0l, nAccountNoMinorForm = 0l;
                    BigDecimal totAmount = new BigDecimal(0), nAmount = new BigDecimal(0), nAmountSC = new BigDecimal(0), nAmountST = new BigDecimal(0), nAmountMinor = new BigDecimal(0);
                    BigDecimal totAmountForm = new BigDecimal(0), nAmountForm = new BigDecimal(0), nAmountSCForm = new BigDecimal(0), nAmountSTForm = new BigDecimal(0), nAmountMinorForm = new BigDecimal(0);
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
//                    String reportName = oss1Vect.get(1).toString();
                    String description = oss1Vect.get(2).toString();
                    Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                    Integer sGNo = Integer.parseInt(oss1Vect.get(4).toString());
                    Integer ssGNo = Integer.parseInt(oss1Vect.get(5).toString());
                    Integer sssGNo = Integer.parseInt(oss1Vect.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(oss1Vect.get(7).toString());
                    String classification = oss1Vect.get(8).toString();
                    String countApplicable = oss1Vect.get(9).toString();
                    String acNature = oss1Vect.get(10).toString();
                    String acType = oss1Vect.get(11).toString();
                    String glHeadFrom = oss1Vect.get(12).toString();
                    String glHeadTo = oss1Vect.get(13).toString();
                    String rangeBaseOn = oss1Vect.get(14).toString();
                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();  //Sector
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString(); //SubSector
                    String referIndex = oss1Vect.get(24).toString();
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setNature(acNature);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(reportDt);
                    params.setToDate(reportDt);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setFromRange(rangeFrom);
                    params.setToRange(rangeTo);
                    params.setGlFromHead(fGNo);
                    params.setGlToHead(npaClassification);
                    params.setFlag(fSGNo);
                    if (sGNo != 0) {
                        if (!fGNo.equalsIgnoreCase("") || !fSGNo.equalsIgnoreCase("") || !fSsGNo.equalsIgnoreCase("") || !fSssGNo.equalsIgnoreCase("") || !fSsssGNo.equalsIgnoreCase("") || !npaClassification.equalsIgnoreCase("")) {
                            /* For total */
                            resultList = new ArrayList<LoanMisCellaniousPojo>();
                            resultList = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", referIndex.equalsIgnoreCase("") ? "0" : referIndex, "0", "N", "0", "N","0","N","N", "0", "0", "0", "0");
                            for (int k = 0; k < resultList.size(); k++) {
                                LoanMisCellaniousPojo val = resultList.get(k);
                                if (resultList.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    totAmount = totAmount.add(resultList.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList.get(k).getOutstanding());
                                } else {
                                    totAmount = totAmount.add(resultList.get(k).getOutstanding());
                                }
                                String acno = val.getAcNo();
                                if (!acno.isEmpty()) {
                                    totAccountNo = totAccountNo + 1;
                                }
                            }
                            /* For SC */
                            resultList1 = new ArrayList<LoanMisCellaniousPojo>();
                            resultList1 = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "SC", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", referIndex.equalsIgnoreCase("") ? "0" : referIndex, "0", "N", "0", "N","0","N","N", "0", "0", "0", "0");
                            for (int k = 0; k < resultList1.size(); k++) {
                                LoanMisCellaniousPojo val = resultList1.get(k);
                                if (resultList1.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    nAmountSC = nAmountSC.add(resultList1.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList1.get(k).getOutstanding());
                                } else {
                                    nAmountSC = nAmountSC.add(resultList1.get(k).getOutstanding());
                                }
                                String acno = val.getAcNo();
                                if (!acno.isEmpty()) {
                                    nAccountNoSC = nAccountNoSC + 1;
                                }
                            }
                            /* For ST */
                            resultList2 = new ArrayList<LoanMisCellaniousPojo>();
                            resultList2 = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "ST", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", referIndex.equalsIgnoreCase("") ? "0" : referIndex, "0", "N", "0", "N","0","N","N", "0", "0", "0", "0");
                            for (int k = 0; k < resultList2.size(); k++) {
                                LoanMisCellaniousPojo val = resultList2.get(k);
                                if (resultList2.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    nAmountST = nAmountST.add(resultList2.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList2.get(k).getOutstanding());
                                } else {
                                    nAmountST = nAmountST.add(resultList2.get(k).getOutstanding());
                                }
                                String acno = val.getAcNo();
                                if (!acno.isEmpty()) {
                                    nAccountNoST = nAccountNoST + 1;
                                }
                            }
                            /* For Minorities */
                            resultList3 = new ArrayList<LoanMisCellaniousPojo>();
                            resultList3 = loanReportFacade.cbsLoanMisReport(orgnCode, acNature.equalsIgnoreCase("") ? "0" : acNature, acType.equalsIgnoreCase("") ? "0" : acType,
                                    reportDt, "A", Double.parseDouble(rangeFrom), Double.parseDouble(rangeTo), "S", fGNo.equalsIgnoreCase("") ? "0" : fGNo,
                                    npaClassification.equalsIgnoreCase("") ? "0" : npaClassification, fSGNo.equalsIgnoreCase("") ? "0" : fSGNo, "0", "0", "0", "0",
                                    "0", "0", "0", "0", "0", "0", fSssGNo.equalsIgnoreCase("") ? "0" : fSssGNo, "MC", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", referIndex.equalsIgnoreCase("") ? "0" : referIndex, "0", "N", "0", "N","0","N","N", "0", "0", "0", "0");
                            for (int k = 0; k < resultList3.size(); k++) {
                                LoanMisCellaniousPojo val = resultList3.get(k);
                                if (resultList3.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    nAmountMinor = nAmountMinor.add(resultList3.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(k).getOutstanding());
                                } else {
                                    nAmountMinor = nAmountMinor.add(resultList3.get(k).getOutstanding());
                                }
                                String acno = val.getAcNo();
                                if (!acno.isEmpty()) {
                                    nAccountNoMinor = nAccountNoMinor + 1;
                                }
                            }
                        }
                    }
                    PrioritySectorPojo pojo = new PrioritySectorPojo();
                    pojo.setReportName(reportName);
                    pojo.setsNo(i);
                    pojo.setDescription(description);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setClassification(classification);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setFormula(formula);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNpaClassification(npaClassification);
                    pojo.setTotAccountNo(totAccountNo);
                    pojo.setTotAmount(totAmount.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setnAccountNoSC(nAccountNoSC);
                    pojo.setnAmountSC(nAmountSC.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setnAccountNoST(nAccountNoST);
                    pojo.setnAmountST(nAmountST.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setnAccountNoWk(nAccountNoMinor);
                    pojo.setnAmountWk(nAmountMinor.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    rbiPojoTable.add(pojo);
                }
                for (int k = 0; k < rbiPojoTable.size(); k++) {
                    PrioritySectorPojo priorPojo = rbiPojoTable.get(k);
                    BigDecimal bal = new BigDecimal("0.0");
                    if (!rbiPojoTable.get(k).getFormula().isEmpty()) {
                        List<PrioritySectorPojo> pojo1 = getValueFromFormula1(rbiPojoTable, priorPojo.getFormula());
//                        bal = bal.add(getValueFromFormula1(rbiPojoTable, priorPojo.getFormula()));
                        for (int l = 0; l < pojo1.size(); l++) {
                            bal = bal.add(pojo1.get(l).getTotAmount());
//                            priorPojo.setTotAmount(pojo1.get(l).getTotAmount());
//                            priorPojo.setnAmountSC(pojo1.get(l).getnAmountSC());
//                            priorPojo.setnAmountST(pojo1.get(l).getnAmountST());
//                            priorPojo.setnAmountMinor(pojo1.get(l).getnAmountMinor());
//                            priorPojo.setTotAccountNo(pojo1.get(l).getTotAccountNo());
//                            priorPojo.setnAccountNoSC(pojo1.get(l).getnAccountNoSC());
//                            priorPojo.setnAccountNoST(pojo1.get(l).getnAccountNoST());
//                            priorPojo.setnAccountNoMinor(pojo1.get(l).getnAccountNoMinor());
                        }
                        priorPojo.setTotAmount(bal);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<PrioritySectorPojo> getPrioritySectorOSS(String reportName, String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<PrioritySectorPojo> rbiPojoTable = new ArrayList<PrioritySectorPojo>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION,REFER_INDEX,REFER_CONTENT  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reportName + "' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();
            List<LoanMisCellaniousPojo> resultList3 = new ArrayList<LoanMisCellaniousPojo>();
            String odFlag = "N";
            if (reportName.equalsIgnoreCase("PRIOR_OSS")) {
                odFlag = "Y";
            }
//            String qtrStrtDt = CbsUtil.getQuarterFirstAndLastDate(reportDt.substring(5, 6), reportDt.substring(0, 4), "F");
            String finStrtDt = "";
            if (reportName.equalsIgnoreCase("PRIOR_OSS")) {
                List finStrtList = em.createNativeQuery("select mindate from cbs_yearend where '" + reportDt + "' between mindate and maxdate").getResultList();
                if (!finStrtList.isEmpty()) {
                    Vector vec = (Vector) finStrtList.get(0);
                    finStrtDt = vec.get(0).toString();
                }
            } else if (reportName.equalsIgnoreCase("PRIOR_XBRL")) {
                finStrtDt = CbsUtil.getQuarterFirstAndLastDate(reportDt.substring(4, 6), reportDt.substring(0, 4), "F");
            }
            resultList3 = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0", reportDt, "A", 0.0, 99999999999.99, "S", "0", "0",
                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "S", "0", odFlag,"0","N","N", "0", "0", "0", "0");
            List<LoanMisCellaniousPojo> disbList = new ArrayList<LoanMisCellaniousPojo>() ;
            if(ymdFormat.parse(reportDt).after(dmyFormat.parse("09/05/2018"))) {
                disbList =  loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0", reportDt, "A", 0.0, 99999999999.99, "S", "0", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "ALL", "0", "0", "S", "0", odFlag,"0","N","N", "0", "0", "0", "0");
            }
//            List<OverdueEmiReportPojo> overDueDetails = loanReportFacade.getOverdueEmiReport(1, 0, "", "ALL", 1, 500, reportDt, "0A");
//            List<OverdueNonEmiResultList> resultList1 = loanReportFacade.getOverDueNonEmi("CA", "ALL", reportDt, "0A");
//            List<OverdueNonEmiResultList> resultList2 = loanReportFacade.getOverDueNonEmi("DL", "ALL", reportDt, "0A");

            //List<OverdueEmiReportPojo> finalList = ListUtils.union(overDueDetails, resultList1);
//            for (int z = 0; z < resultList3.size(); z++) {
//                if (resultList3.get(z).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                    for (int y = 0; y < resultList1.size(); y++) {
//                        if (resultList3.get(z).getAcNo().equalsIgnoreCase(resultList1.get(y).getAccountNo())) {
//                            resultList3.get(z).setOverdueAmt(new BigDecimal(resultList1.get(y).getOverDue()));
//                            break;
//                        }
//                    }
//                } else if (resultList3.get(z).getAcNature().equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//                    for (int y = 0; y < resultList2.size(); y++) {
//                        if (resultList3.get(z).getAcNo().equalsIgnoreCase(resultList2.get(y).getAccountNo())) {
//                            resultList3.get(z).setOverdueAmt(new BigDecimal(resultList2.get(y).getOverDue()));
//                            break;
//                        }
//                    }
//                } else if (resultList3.get(z).getAcNature().equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
//                    for (int y = 0; y < overDueDetails.size(); y++) {
//                        if (resultList3.get(z).getAcNo().equalsIgnoreCase(overDueDetails.get(y).getAccountNumber())) {
//                            resultList3.get(z).setOverdueAmt(overDueDetails.get(y).getAmountOverdue());
//                            break;
//                        }
//                    }
//                }
//            }
            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_HO_RBI_STMT_REPORT");
            } else {
                for (int i = 0; i < oss1Query.size(); i++) {
                    Long noOfAc = 0l, noOfAcWk = 0l, noOfAcSc = 0l, noOfAcSt = 0l, noOfAcWo = 0l, noOfAcOth = 0l, noOfAcMinComm = 0l;
                    BigDecimal sanAmount = new BigDecimal(0), sanAmountWk = new BigDecimal(0), sanAmountSc = new BigDecimal(0), sanAmountSt = new BigDecimal(0), sanAmountWo = new BigDecimal(0), sanAmountOth = new BigDecimal(0), sanAmountMinComm = new BigDecimal(0),
                            advAmount = new BigDecimal(0), advAmountWk = new BigDecimal(0), advAmountSc = new BigDecimal(0), advAmountSt = new BigDecimal(0), advAmountWo = new BigDecimal(0), advAmountOth = new BigDecimal(0), advAmountMinComm = new BigDecimal(0),
                            outStandAmount = new BigDecimal(0), outStandAmountWk = new BigDecimal(0), outStandAmountSc = new BigDecimal(0), outStandAmountSt = new BigDecimal(0), outStandAmountWo = new BigDecimal(0), outStandAmountOth = new BigDecimal(0), outStandAmountMinComm = new BigDecimal(0),
                            overDueAmount = new BigDecimal(0), overDueAmountWk = new BigDecimal(0), overDueAmountSc = new BigDecimal(0), overDueAmountSt = new BigDecimal(0), overDueAmountWo = new BigDecimal(0), overDueAmountOth = new BigDecimal(0), overDueAmountMinComm = new BigDecimal(0);
                    BigDecimal disbAMt = new BigDecimal(0),disbAmtSc= new BigDecimal("0"),disbAmtSt= new BigDecimal("0");
                    long disbAcs = 0l,disbAcsSc = 0l, disbAcsSt = 0l;
                    String openDt = "";
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    String description = oss1Vect.get(2).toString();
                    Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                    Integer sGNo = Integer.parseInt(oss1Vect.get(4).toString());
                    Integer ssGNo = Integer.parseInt(oss1Vect.get(5).toString());
                    Integer sssGNo = Integer.parseInt(oss1Vect.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(oss1Vect.get(7).toString());
                    String classification = oss1Vect.get(8).toString();
                    String countApplicable = oss1Vect.get(9).toString();
                    String acNature = oss1Vect.get(10).toString();
                    String acType = oss1Vect.get(11).toString();
                    String glHeadFrom = oss1Vect.get(12).toString();
                    String glHeadTo = oss1Vect.get(13).toString();
                    String rangeBaseOn = oss1Vect.get(14).toString();
                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();  //Sector
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();//Guarntee Cover
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString(); //SubSector
                    String referIndex = oss1Vect.get(24).toString();
                    String referContent = oss1Vect.get(25).toString();//For Number Of Accounts to be shown classification wise
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setNature(acNature);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(reportDt);
                    params.setToDate(reportDt);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setFromRange(rangeFrom);
                    params.setToRange(rangeTo);
                    params.setGlFromHead(fGNo);
                    params.setGlToHead(npaClassification);
                    params.setFlag(fSGNo);
                    if ((sGNo != 0) && (!fGNo.equalsIgnoreCase("") && !fSGNo.equalsIgnoreCase(""))) {
                        for (int j = 0; j < resultList3.size(); j++) {
                            if (fGNo.equalsIgnoreCase(resultList3.get(j).getSector())) {
                                if (fSGNo.equalsIgnoreCase(resultList3.get(j).getSubSector())) { //Sub-Sector Checking
                                    if (fSsGNo.equalsIgnoreCase(resultList3.get(j).getModeOfAdvance())) { //Mode of Advance Checking                                        
                                        if (reportName.equalsIgnoreCase("PRIOR_OSS") ? (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSsssGNo.equalsIgnoreCase(resultList3.get(j).getGuarnteeCover()) : fSssGNo.equalsIgnoreCase(resultList3.get(j).getPurposeOfAdvance())) : fSssGNo.equalsIgnoreCase(resultList3.get(j).getPurposeOfAdvance())) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAc = noOfAc + 1;
                                                            }
                                                        } else {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                    sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                    advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                } else {
                                                    noOfAc = noOfAc + 1;
                                                }
                                                sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                            }
//
                                        /*Weaker value setting*/
                                            if (resultList3.get(j).getApplicantCategory().equalsIgnoreCase("WK")) {
                                                if (reportName.equalsIgnoreCase("PRIOR_OSS")) {
                                                    if (resultList3.get(j).getGender().equalsIgnoreCase("F")) {
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcWo = noOfAcWo + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                                sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                            } else {
                                                                noOfAcWo = noOfAcWo + 1;
                                                            }
                                                            sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                } else {
                                                    if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                }
                                            }
                                        } else if (reportName.equalsIgnoreCase("PRIOR_OSS") ? (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSsssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(resultList3.get(j).getGuarnteeCover())) : fSssGNo.equalsIgnoreCase("")) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAc = noOfAc + 1;
                                                            }
                                                        } else {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                    sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                    advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                } else {
                                                    noOfAc = noOfAc + 1;
                                                }
                                                sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                            }
                                            /*Weaker value setting*/
                                            if (resultList3.get(j).getApplicantCategory().equalsIgnoreCase("WK")) {
                                                if (reportName.equalsIgnoreCase("PRIOR_OSS")) {
                                                    if (resultList3.get(j).getGender().equalsIgnoreCase("F")) {
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcWo = noOfAcWo + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                                sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                            } else {
                                                                noOfAcWo = noOfAcWo + 1;
                                                            }
                                                            sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                } else {
                                                    if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                }
                                            }
                                        }
                                    } else if (fSsGNo.equalsIgnoreCase("")) { //Mode of Advance Checking
                                        if (reportName.equalsIgnoreCase("PRIOR_OSS") ? (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSsssGNo.equalsIgnoreCase(resultList3.get(j).getGuarnteeCover()) : fSssGNo.equalsIgnoreCase(resultList3.get(j).getPurposeOfAdvance())) : fSssGNo.equalsIgnoreCase(resultList3.get(j).getPurposeOfAdvance())) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAc = noOfAc + 1;
                                                            }
                                                        } else {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                    sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                    advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                } else {
                                                    noOfAc = noOfAc + 1;
                                                }
                                                sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                            }
                                            /*Weaker value setting*/
                                            if (resultList3.get(j).getApplicantCategory().equalsIgnoreCase("WK")) {
                                                if (reportName.equalsIgnoreCase("PRIOR_OSS")) {
                                                    if (resultList3.get(j).getGender().equalsIgnoreCase("F")) {
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcWo = noOfAcWo + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                                sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                            } else {
                                                                noOfAcWo = noOfAcWo + 1;
                                                            }
                                                            sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                } else {
                                                    if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                }
                                            }
                                        } else if (reportName.equalsIgnoreCase("PRIOR_OSS") ? (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSsssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(resultList3.get(j).getGuarnteeCover())) : (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(resultList3.get(j).getGuarnteeCover()))) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAc = noOfAc + 1;
                                                            }
                                                        } else {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                    sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                    advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                } else {
                                                    noOfAc = noOfAc + 1;
                                                }
                                                sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                            }
                                            /*Weaker value setting*/
                                            if (resultList3.get(j).getApplicantCategory().equalsIgnoreCase("WK")) {
                                                if (reportName.equalsIgnoreCase("PRIOR_OSS")) {
                                                    if (resultList3.get(j).getGender().equalsIgnoreCase("F")) {
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcWo = noOfAcWo + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                                sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                            } else {
                                                                noOfAcWo = noOfAcWo + 1;
                                                            }
                                                            sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                } else {
                                                    if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                }

                                            }
                                        } else if (ymdFormat.parse(reportDt).after(dmyFormat.parse("09/05/2018")) && (reportName.equalsIgnoreCase("PRIOR_OSS") ? fSssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(""))) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAc = noOfAc + 1;
                                                            }
                                                        } else {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                    sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                    advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                } else {
                                                    noOfAc = noOfAc + 1;
                                                }
                                                sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                            }
                                            /*Weaker value setting*/
                                            if (resultList3.get(j).getApplicantCategory().equalsIgnoreCase("WK")) {
                                                if (reportName.equalsIgnoreCase("PRIOR_OSS")) {
                                                    if (resultList3.get(j).getGender().equalsIgnoreCase("F")) {
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcWo = noOfAcWo + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                                sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountWo = outStandAmountWo.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcWo = noOfAcWo + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcWo = noOfAcWo + 1;
                                                                }
                                                            } else {
                                                                noOfAcWo = noOfAcWo + 1;
                                                            }
                                                            sanAmountWo = sanAmountWo.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountWo = advAmountWo.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountWo = overDueAmountWo.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                } else {
                                                    if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                        /*SC value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSc = noOfAcSc + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSc = noOfAcSc + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                            sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                        /*ST value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcSt = noOfAcSt + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcSt = noOfAcSt + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                            sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                        /*Minority Community value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                            sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    } else {
                                                        /*Wk value setting*/
                                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                                } else {
                                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                                }
                                                                if (referContent.equalsIgnoreCase("A")) {
                                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                            noOfAcOth = noOfAcOth + 1;
                                                                        }
                                                                    } else {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                            }
                                                        } else {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                            } else {
                                                                outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                            }
                                                            if (referContent.equalsIgnoreCase("A")) {
                                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                        noOfAcOth = noOfAcOth + 1;
                                                                    }
                                                                } else {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                            sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                            advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                            overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                        }
                                                    }

                                                    noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                                    sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                                    advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                                    outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                                    overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if ((reportName.equalsIgnoreCase("PRIOR_XBRL") || (reportName.equalsIgnoreCase("PRIOR_OSS") && ymdFormat.parse(reportDt).after(dmyFormat.parse("09/05/2018")))) && /*!fSGNo.equalsIgnoreCase("") &&*/ fSsssGNo.equalsIgnoreCase("Wk")) {
                        for (int j = 0; j < resultList3.size(); j++) {
                            if (resultList3.get(j).getSector().equalsIgnoreCase(fGNo)) {
                                if (resultList3.get(j).getApplicantCategory().equalsIgnoreCase("WK")) {
                                    if (referIndex.equalsIgnoreCase("")) {
                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                            if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                } else {
                                                    noOfAc = noOfAc + 1;
                                                }
                                                sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                            }
                                        } else {
                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                            } else {
                                                outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                            }
                                            if (referContent.equalsIgnoreCase("A")) {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                } else {
                                                    noOfAc = noOfAc + 1;
                                                }
                                            } else {
                                                noOfAc = noOfAc + 1;
                                            }
                                            sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                            advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                            overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                        }
                                        if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                            /*SC value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                        } else {
                                                            noOfAcSc = noOfAcSc + 1;
                                                        }
                                                    } else {
                                                        noOfAcSc = noOfAcSc + 1;
                                                    }
                                                    sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                    advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAcSc = noOfAcSc + 1;
                                                        }
                                                    } else {
                                                        noOfAcSc = noOfAcSc + 1;
                                                    }
                                                } else {
                                                    noOfAcSc = noOfAcSc + 1;
                                                }
                                                sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                            }
                                        } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                            /*ST value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                        } else {
                                                            noOfAcSt = noOfAcSt + 1;
                                                        }
                                                    } else {
                                                        noOfAcSt = noOfAcSt + 1;
                                                    }
                                                    sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                    advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAcSt = noOfAcSt + 1;
                                                        }
                                                    } else {
                                                        noOfAcSt = noOfAcSt + 1;
                                                    }
                                                } else {
                                                    noOfAcSt = noOfAcSt + 1;
                                                }
                                                sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                            }
                                        } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                            /*Minority Community value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                        } else {
                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                        }
                                                    } else {
                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                    }
                                                    sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                    advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                        }
                                                    } else {
                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                    }
                                                } else {
                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                }
                                                sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                            }
                                        } else {
                                            /*Wk value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                        } else {
                                                            noOfAcOth = noOfAcOth + 1;
                                                        }
                                                    } else {
                                                        noOfAcOth = noOfAcOth + 1;
                                                    }
                                                    sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                    advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAcOth = noOfAcOth + 1;
                                                        }
                                                    } else {
                                                        noOfAcOth = noOfAcOth + 1;
                                                    }
                                                } else {
                                                    noOfAcOth = noOfAcOth + 1;
                                                }
                                                sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                            }
                                        }

                                        noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                        sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                        advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                        outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                        overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                    } else if (referIndex.equalsIgnoreCase("F")) {
                                        if (resultList3.get(j).getGender().equalsIgnoreCase("F")) {
                                            /*Female Weaker Section Advances Checking*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAc = noOfAc + 1;
                                                            }
                                                        } else {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                    sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                    advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                } else {
                                                    outStandAmount = outStandAmount.add(resultList3.get(j).getOutstanding());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAc = noOfAc + 1;
                                                        }
                                                    } else {
                                                        noOfAc = noOfAc + 1;
                                                    }
                                                } else {
                                                    noOfAc = noOfAc + 1;
                                                }
                                                sanAmount = sanAmount.add(resultList3.get(j).getSanctionAmt());
                                                advAmount = advAmount.add(resultList3.get(j).getDisbAmount());
                                                overDueAmount = overDueAmount.add(resultList3.get(j).getOverdueAmt());
                                            }
                                            if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("SC")) {
                                                /*SC value setting*/
                                                if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                    if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                        } else {
                                                            outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                        }
                                                        if (referContent.equalsIgnoreCase("A")) {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                    noOfAcSc = noOfAcSc + 1;
                                                                }
                                                            } else {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                        } else {
                                                            noOfAcSc = noOfAcSc + 1;
                                                        }
                                                        sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                        advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                        overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                    }
                                                } else {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmountSc = outStandAmountSc.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAcSc = noOfAcSc + 1;
                                                            }
                                                        } else {
                                                            noOfAcSc = noOfAcSc + 1;
                                                        }
                                                    } else {
                                                        noOfAcSc = noOfAcSc + 1;
                                                    }
                                                    sanAmountSc = sanAmountSc.add(resultList3.get(j).getSanctionAmt());
                                                    advAmountSc = advAmountSc.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmountSc = overDueAmountSc.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("ST")) {
                                                /*ST value setting*/
                                                if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                    if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                        } else {
                                                            outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                        }
                                                        if (referContent.equalsIgnoreCase("A")) {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                    noOfAcSt = noOfAcSt + 1;
                                                                }
                                                            } else {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                        } else {
                                                            noOfAcSt = noOfAcSt + 1;
                                                        }
                                                        sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                        advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                        overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                    }
                                                } else {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmountSt = outStandAmountSt.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAcSt = noOfAcSt + 1;
                                                            }
                                                        } else {
                                                            noOfAcSt = noOfAcSt + 1;
                                                        }
                                                    } else {
                                                        noOfAcSt = noOfAcSt + 1;
                                                    }
                                                    sanAmountSt = sanAmountSt.add(resultList3.get(j).getSanctionAmt());
                                                    advAmountSt = advAmountSt.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmountSt = overDueAmountSt.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else if (resultList3.get(j).getCategoryOpt().equalsIgnoreCase("Mc")) {
                                                /*Minority Community value setting*/
                                                if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                    if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                        } else {
                                                            outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                        }
                                                        if (referContent.equalsIgnoreCase("A")) {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                    noOfAcMinComm = noOfAcMinComm + 1;
                                                                }
                                                            } else {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                        } else {
                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                        }
                                                        sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                        advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                        overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                    }
                                                } else {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmountMinComm = outStandAmountMinComm.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAcMinComm = noOfAcMinComm + 1;
                                                            }
                                                        } else {
                                                            noOfAcMinComm = noOfAcMinComm + 1;
                                                        }
                                                    } else {
                                                        noOfAcMinComm = noOfAcMinComm + 1;
                                                    }
                                                    sanAmountMinComm = sanAmountMinComm.add(resultList3.get(j).getSanctionAmt());
                                                    advAmountMinComm = advAmountMinComm.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmountMinComm = overDueAmountMinComm.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            } else {
                                                /*Wk value setting*/
                                                if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                    if ((resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeFrom)) > 0) && (resultList3.get(j).getOutstanding().compareTo(new BigDecimal(rangeTo)) <= 0)) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                        } else {
                                                            outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                        }
                                                        if (referContent.equalsIgnoreCase("A")) {
                                                            if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                                if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                    noOfAcOth = noOfAcOth + 1;
                                                                }
                                                            } else {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                        } else {
                                                            noOfAcOth = noOfAcOth + 1;
                                                        }
                                                        sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                        advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                        overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                    }
                                                } else {
                                                    if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(j).getOutstanding());
                                                    } else {
                                                        outStandAmountOth = outStandAmountOth.add(resultList3.get(j).getOutstanding());
                                                    }
                                                    if (referContent.equalsIgnoreCase("A")) {
                                                        if (resultList3.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                            if (!(resultList3.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                                noOfAcOth = noOfAcOth + 1;
                                                            }
                                                        } else {
                                                            noOfAcOth = noOfAcOth + 1;
                                                        }
                                                    } else {
                                                        noOfAcOth = noOfAcOth + 1;
                                                    }
                                                    sanAmountOth = sanAmountOth.add(resultList3.get(j).getSanctionAmt());
                                                    advAmountOth = advAmountOth.add(resultList3.get(j).getDisbAmount());
                                                    overDueAmountOth = overDueAmountOth.add(resultList3.get(j).getOverdueAmt());
                                                }
                                            }

                                            noOfAcWk = noOfAcSc + noOfAcSt + noOfAcWo + noOfAcOth + noOfAcMinComm;
                                            sanAmountWk = sanAmountSc.add(sanAmountSt).add(sanAmountWo).add(sanAmountOth).add(sanAmountMinComm);
                                            advAmountWk = advAmountSc.add(advAmountSt).add(advAmountWo).add(advAmountOth).add(advAmountMinComm);
                                            outStandAmountWk = outStandAmountSc.add(outStandAmountSt).add(outStandAmountWo).add(outStandAmountOth).add(outStandAmountMinComm);
                                            overDueAmountWk = overDueAmountSc.add(overDueAmountSt).add(overDueAmountWo).add(overDueAmountOth).add(overDueAmountMinComm);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    disbAcs = 0l;
                    disbAMt = new BigDecimal("0");
                    disbAmtSc= new BigDecimal("0");
                    disbAmtSt= new BigDecimal("0");
                    for (int l = 0; l < disbList.size(); l++) {
                        if (disbList.get(l).getSector().equalsIgnoreCase(fGNo) && disbList.get(l).getSubSector().equalsIgnoreCase(fSGNo)) {
                            if (fSsGNo.equalsIgnoreCase(disbList.get(l).getModeOfAdvance())) {
                                if ((reportName.equalsIgnoreCase("PRIOR_OSS")) ? (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSsssGNo.equalsIgnoreCase(disbList.get(l).getGuarnteeCover()) : fSssGNo.equalsIgnoreCase(disbList.get(l).getPurposeOfAdvance())) : fSssGNo.equalsIgnoreCase(disbList.get(l).getPurposeOfAdvance())) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                    openDt = disbList.get(l).getSanctionDt().substring(0, 10);
                                    if ((!dmyFormat.parse(openDt).before(ymdFormat.parse(finStrtDt))) && (!dmyFormat.parse(openDt).after(ymdFormat.parse(reportDt)))) {
                                        if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getOdLimit());
                                        } else {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getDisbAmount());
                                        }
                                        if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("SC")) {
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }                                                
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            }
                                        } else if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("ST")) {
                                            /*ST value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }                                                
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            }
                                        }
                                    }
                                } else if (reportName.equalsIgnoreCase("PRIOR_OSS") ? (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSsssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(disbList.get(l).getGuarnteeCover())) : fSssGNo.equalsIgnoreCase("")) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                    openDt = disbList.get(l).getSanctionDt().substring(0, 10);
                                    if ((!dmyFormat.parse(openDt).before(ymdFormat.parse(finStrtDt))) && (!dmyFormat.parse(openDt).after(ymdFormat.parse(reportDt)))) {
                                        if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getOdLimit());
                                        } else {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getDisbAmount());
                                        }
                                        if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("SC")) {
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            }
                                        } else if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("ST")) {
                                            /*ST value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            }
                                        }
                                    }
                                } else if (disbList.get(l).getApplicantCategory().equalsIgnoreCase(fSsssGNo)) {
                                    openDt = disbList.get(l).getSanctionDt().substring(0, 10);
                                    if ((!dmyFormat.parse(openDt).before(ymdFormat.parse(finStrtDt))) && (!dmyFormat.parse(openDt).after(ymdFormat.parse(reportDt)))) {
                                        if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getOdLimit());
                                        } else {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getDisbAmount());
                                        }
                                        if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("SC")) {
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            }
                                        } else if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("ST")) {
                                            /*ST value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            noOfAcSt = noOfAcSt + 1;
                                                        }
                                                    } else {
                                                        noOfAcSt = noOfAcSt + 1;
                                                    }
                                                } else {
                                                    noOfAcSt = noOfAcSt + 1;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (fSsGNo.equalsIgnoreCase("")) {
                                if (reportName.equalsIgnoreCase("PRIOR_OSS") ? (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSsssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(disbList.get(l).getPurposeOfAdvance())) : (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(disbList.get(l).getPurposeOfAdvance()))) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                    openDt = disbList.get(l).getSanctionDt().substring(0, 10);
                                    if ((!dmyFormat.parse(openDt).before(ymdFormat.parse(finStrtDt))) && (!dmyFormat.parse(openDt).after(ymdFormat.parse(reportDt)))) {
                                        if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getOdLimit());
                                        } else {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getDisbAmount());
                                        }
                                        if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("SC")) {
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            }
                                        } else if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("ST")) {
                                            /*ST value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            }
                                        }
                                    }
                                } else if (reportName.equalsIgnoreCase("PRIOR_OSS") ? (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSsssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(disbList.get(l).getPurposeOfAdvance())) : (ymdFormat.parse(reportDt).before(dmyFormat.parse("09/05/2018")) ? fSssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(disbList.get(l).getPurposeOfAdvance()))) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                    openDt = disbList.get(l).getSanctionDt().substring(0, 10);
                                    if ((!dmyFormat.parse(openDt).before(ymdFormat.parse(finStrtDt))) && (!dmyFormat.parse(openDt).after(ymdFormat.parse(reportDt)))) {
                                        if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getOdLimit());
                                        } else {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getDisbAmount());
                                        }
                                        if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("SC")) {
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            }
                                        } else if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("ST")) {
                                            /*ST value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            }
                                        }
                                    }
                                } else if (ymdFormat.parse(reportDt).after(dmyFormat.parse("09/05/2018")) && (reportName.equalsIgnoreCase("PRIOR_OSS") ? fSssGNo.equalsIgnoreCase("") : fSssGNo.equalsIgnoreCase(""))) { ////Sub Sector Category (Yearly/Quarterly) Checking --188/184
                                    openDt = disbList.get(l).getSanctionDt().substring(0, 10);
                                    if ((!dmyFormat.parse(openDt).before(ymdFormat.parse(finStrtDt))) && (!dmyFormat.parse(openDt).after(ymdFormat.parse(reportDt)))) {
                                        if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getOdLimit());
                                        } else {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getDisbAmount());
                                        }
                                        if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("SC")) {
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            }
                                        } else if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("ST")) {
                                            /*ST value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            }
                                        }
                                    }
                                } else if (disbList.get(l).getApplicantCategory().equalsIgnoreCase(fSsssGNo)) {
                                    openDt = disbList.get(l).getSanctionDt().substring(0, 10);
                                    if ((!dmyFormat.parse(openDt).before(ymdFormat.parse(finStrtDt))) && (!dmyFormat.parse(openDt).after(ymdFormat.parse(reportDt)))) {
                                        if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getOdLimit());
                                        } else {
                                            disbAcs = disbAcs + 1;
                                            disbAMt = disbAMt.add(disbList.get(l).getDisbAmount());
                                        }
                                        if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("SC")) {
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSc = disbAcsSc + 1;
                                                        }
                                                    } else {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            }
                                        } else if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("ST")) {
                                            /*ST value setting*/
                                            if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            } else {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                                } else {
                                                    disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                                }
                                                if (referContent.equalsIgnoreCase("A")) {
                                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                        if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                            disbAcsSt = disbAcsSt + 1;
                                                        }
                                                    } else {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (disbList.get(l).getSector().equalsIgnoreCase(fGNo)) {// Weaker section Values setting
                            if (disbList.get(l).getApplicantCategory().equalsIgnoreCase(fSsssGNo)) {
                                openDt = disbList.get(l).getSanctionDt().substring(0, 10);
                                if ((!dmyFormat.parse(openDt).before(ymdFormat.parse(finStrtDt))) && (!dmyFormat.parse(openDt).after(ymdFormat.parse(reportDt)))) {
                                    if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        disbAcs = disbAcs + 1;
                                        disbAMt = disbAMt.add(disbList.get(l).getOdLimit());
                                        
                                    } else {
                                        disbAcs = disbAcs + 1;
                                        disbAMt = disbAMt.add(disbList.get(l).getDisbAmount());
                                    }
                                    if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("SC")) {
                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                            if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                            } else {
                                                disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                            }
                                            if (referContent.equalsIgnoreCase("A")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            } else {
                                                disbAcsSc = disbAcsSc + 1;
                                            }
                                        } else {
                                            if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                disbAmtSc = disbAmtSc.add(disbList.get(l).getOdLimit());
                                            } else {
                                                disbAmtSc = disbAmtSc.add(disbList.get(l).getDisbAmount());
                                            }
                                            if (referContent.equalsIgnoreCase("A")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                        disbAcsSc = disbAcsSc + 1;
                                                    }
                                                } else {
                                                    disbAcsSc = disbAcsSc + 1;
                                                }
                                            } else {
                                                disbAcsSc = disbAcsSc + 1;
                                            }
                                        }
                                    } else if (disbList.get(l).getCategoryOpt().equalsIgnoreCase("ST")) {
                                        /*ST value setting*/
                                        if (rangeBaseOn.equalsIgnoreCase("Rs")) {
                                            if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                            } else {
                                                disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                            }
                                            if (referContent.equalsIgnoreCase("A")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                        disbAcsSt = disbAcsSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            } else {
                                                disbAcsSt = disbAcsSt + 1;
                                            }
                                        } else {
                                            if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                disbAmtSt = disbAmtSt.add(disbList.get(l).getOdLimit());
                                            } else {
                                                disbAmtSt = disbAmtSt.add(disbList.get(l).getDisbAmount());
                                            }
                                            if (referContent.equalsIgnoreCase("A")) {
                                                if (disbList.get(l).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    if (!(disbList.get(l).getOutstanding().compareTo(new BigDecimal("0")) >= 0)) {
                                                        noOfAcSt = noOfAcSt + 1;
                                                    }
                                                } else {
                                                    disbAcsSt = disbAcsSt + 1;
                                                }
                                            } else {
                                                disbAcsSt = disbAcsSt + 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    PrioritySectorPojo pojo = new PrioritySectorPojo();
                    pojo.setReportName(reportName);
                    pojo.setsNo(i);
                    pojo.setDescription(description);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setClassification(classification);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setFormula(formula);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNpaClassification(npaClassification);

                    /*Total Priority Accounts*/
                    pojo.setTotAccountNo(noOfAc);
                    pojo.setSancLimit(sanAmount.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setAdvAmt(advAmount.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setOutstanding(outStandAmount.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));  //OSS
//                    pojo.setTotAmount(outStandAmount.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));    //XBRL
                    pojo.setOverDue(overDueAmount.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));                    

                    /*Total Weaker Accounts*/
                    if (reportName.equalsIgnoreCase("PRIOR_XBRL")) {
                        pojo.setnAccountNoWk(noOfAcMinComm);
                        pojo.setSancLimitWK(sanAmountMinComm.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                        pojo.setAdvAmtWK(advAmountMinComm.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                        pojo.setOutstandingWK(outStandAmountMinComm.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                        pojo.setOverDueWK(overDueAmountMinComm.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    } else {
                        pojo.setnAccountNoWk(noOfAcWk);
                        pojo.setSancLimitWK(sanAmountWk.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                        pojo.setAdvAmtWK(advAmountWk.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                        pojo.setOutstandingWK(outStandAmountWk.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                        pojo.setOverDueWK(overDueAmountWk.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    }


                    /*Total SC Accounts*/
                    pojo.setnAccountNoSC(noOfAcSc);
                    pojo.setnOfDisbAc(disbAcs);
                    pojo.setDisbAmt(disbAMt.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setSancLimitSC(sanAmountSc.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setAdvAmtSC(advAmountSc.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setOutstandingSC(outStandAmountSc.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));  //OSS
//                    pojo.setnAmountSC(outStandAmountSc.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));      //XBRL
                    pojo.setOverDueSC(overDueAmountSc.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));

                    /*Total ST Accounts*/
                    pojo.setnAccountNoST(noOfAcSt);
                    pojo.setSancLimitST(sanAmountSt.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setAdvAmtST(advAmountSt.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setOutstandingST(outStandAmountSt.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));  //OSS
//                    pojo.setnAmountST(outStandAmountSt.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));      //XBRL
                    pojo.setOverDueST(overDueAmountSt.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));

                    /*Total Woman Accounts*/
                    pojo.setnAccountNoWO(noOfAcWo);
                    pojo.setSancLimitWO(sanAmountWo.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setAdvAmtWO(advAmountWo.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setOutstandingWO(outStandAmountWo.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setOverDueWO(overDueAmountWo.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));

                    /*Total Oth Accounts*/
                    pojo.setnAccountNoOth(noOfAcOth + noOfAcMinComm);
                    pojo.setSancLimitOth((sanAmountOth.add(sanAmountMinComm)).abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setAdvAmtOth((advAmountOth.add(advAmountMinComm)).abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setOutstandingOth((outStandAmountOth.add(outStandAmountMinComm)).abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setOverDueOth((overDueAmountOth.add(overDueAmountMinComm)).abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setnAccountNoScSt(noOfAcSt+noOfAcSc);
                    pojo.setOutstandingScSt(outStandAmountSc.add(outStandAmountSt).abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setDisbAmtScSt(disbAmtSt.add(disbAmtSc).abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setnOfDisbAcScSt(disbAcsSc+disbAcsSt);
                    rbiPojoTable.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<PrioritySectorPojo> getMinorityCommCreditFlow(String reportName, String fromDt, String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException {
        List<PrioritySectorPojo> rbiPojoTable = new ArrayList<PrioritySectorPojo>();
        try {
            List oss1Query = em.createNativeQuery("SELECT SNO, REPORT_NAME, DESCRIPTION, GNO, S_GNO, SS_GNO, SSS_GNO, SSSS_GNO, CLASSIFICATION, "
                    + "COUNT_APPLICABLE,AC_NATURE, AC_TYPE, GL_HEAD_FROM, GL_HEAD_TO, RANGE_BASE_ON, IFNULL(RANGE_FROM,0), IFNULL(RANGE_TO,0), FORMULA, "
                    + "F_GNO, F_S_GNO,F_SS_GNO, F_SSS_GNO, F_SSSS_GNO, NPA_CLASSIFICATION,REFER_INDEX  FROM cbs_ho_rbi_stmt_report WHERE REPORT_NAME = '" + reportName + "' and '" + reportDt + "' between EFF_FR_DT and EFF_TO_DT ORDER BY "
                    + "REPORT_NAME, CAST(GNO AS UNSIGNED),CAST(S_GNO AS UNSIGNED),CAST(SS_GNO AS UNSIGNED), CAST(SSS_GNO AS UNSIGNED),"
                    + "CAST(SSSS_GNO AS UNSIGNED)").getResultList();

            if (oss1Query.isEmpty()) {
                throw new ApplicationException("Data does not exist in RBI Master");
            } else {
                List<LoanMisCellaniousPojo> resultList3 = new ArrayList<LoanMisCellaniousPojo>();
                List<LoanMisCellaniousPojo> resultList1 = new ArrayList<LoanMisCellaniousPojo>();
                resultList1 = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0", CbsUtil.dateAdd(fromDt, -1), "A", 0.0, 99999999999.99, "S", "PRIOR", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "MC", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "N", "0", "N","0","N","N", "0", "0", "0", "0");
                resultList3 = loanReportFacade.cbsLoanMisReport(orgnCode, "0", "0", reportDt, "A", 0.0, 99999999999.99, "S", "PRIOR", "0",
                        "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "MC", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "S", "0", "N","0","N","N", "0", "0", "0", "0");

                for (int i = 0; i < oss1Query.size(); i++) {
                    Long noOfAc = 0l, noOfAcWk = 0l, noOfAcSc = 0l, noOfAcSt = 0l, noOfAcWo = 0l, noOfAcOth = 0l, noOfAcMinComm = 0l;
                    BigDecimal sanAmount = new BigDecimal(0), sanAmountWk = new BigDecimal(0), sanAmountSc = new BigDecimal(0), sanAmountSt = new BigDecimal(0), sanAmountWo = new BigDecimal(0), sanAmountOth = new BigDecimal(0), sanAmountMinComm = new BigDecimal(0),
                            advAmount = new BigDecimal(0), advAmountWk = new BigDecimal(0), advAmountSc = new BigDecimal(0), advAmountSt = new BigDecimal(0), advAmountWo = new BigDecimal(0), advAmountOth = new BigDecimal(0), advAmountMinComm = new BigDecimal(0),
                            outStandAmount = new BigDecimal(0), outStandAmountWk = new BigDecimal(0), outStandAmountSc = new BigDecimal(0), outStandAmountSt = new BigDecimal(0), outStandAmountWo = new BigDecimal(0), outStandAmountOth = new BigDecimal(0), outStandAmountMinComm = new BigDecimal(0),
                            overDueAmount = new BigDecimal(0), overDueAmountWk = new BigDecimal(0), overDueAmountSc = new BigDecimal(0), overDueAmountSt = new BigDecimal(0), overDueAmountWo = new BigDecimal(0), overDueAmountOth = new BigDecimal(0), overDueAmountMinComm = new BigDecimal(0);
                    Vector oss1Vect = (Vector) oss1Query.get(i);
                    Integer sNo = Integer.parseInt(oss1Vect.get(0).toString());
                    String description = oss1Vect.get(2).toString();
                    Integer gNo = Integer.parseInt(oss1Vect.get(3).toString());
                    Integer sGNo = Integer.parseInt(oss1Vect.get(4).toString());
                    Integer ssGNo = Integer.parseInt(oss1Vect.get(5).toString());
                    Integer sssGNo = Integer.parseInt(oss1Vect.get(6).toString());
                    Integer ssssGNo = Integer.parseInt(oss1Vect.get(7).toString());
                    String classification = oss1Vect.get(8).toString();
                    String countApplicable = oss1Vect.get(9).toString();
                    String acNature = oss1Vect.get(10).toString();
                    String acType = oss1Vect.get(11).toString();
                    String glHeadFrom = oss1Vect.get(12).toString();
                    String glHeadTo = oss1Vect.get(13).toString();
                    String rangeBaseOn = oss1Vect.get(14).toString();
                    String rangeFrom = oss1Vect.get(15).toString();
                    String rangeTo = oss1Vect.get(16).toString();
                    String formula = oss1Vect.get(17).toString();
                    String fGNo = oss1Vect.get(18).toString();  //Sector
                    String fSGNo = oss1Vect.get(19).toString();
                    String fSsGNo = oss1Vect.get(20).toString();
                    String fSssGNo = oss1Vect.get(21).toString();//Guarntee Cover
                    String fSsssGNo = oss1Vect.get(22).toString();
                    String npaClassification = oss1Vect.get(23).toString(); //SubSector
                    String referIndex = oss1Vect.get(24).toString();
                    AdditionalStmtPojo params = new AdditionalStmtPojo();
                    params.setAcType(acType);
                    params.setNature(acNature);
                    params.setBrnCode(orgnCode);
                    params.setClassification(classification);
                    params.setDate(reportDt);
                    params.setToDate(reportDt);
                    params.setRangeBasedOn(rangeBaseOn);
                    params.setFromRange(rangeFrom);
                    params.setToRange(rangeTo);
                    params.setGlFromHead(fGNo);
                    params.setGlToHead(npaClassification);
                    params.setFlag(fSGNo);
                    List acOpenList = em.createNativeQuery("select a.acno from accountmaster a, cbs_loan_borrower_details b where a.ACNo = b.ACC_NO "
                            + " and substring(a.acno,3,2) in (select acctcode  from accounttypemaster where acctnature in ('TL','DL','CA') and crdbflag in ('D','B')) "
                            + " and a.OpeningDt between '" + fromDt + "' and '" + reportDt + "' "
                            + " and (a.ClosingDate is null or a.ClosingDate = '' or a.ClosingDate>'" + reportDt + "')"
                            + " and b.sector in ('" + fGNo + "') and b.APPLICANT_CATEGORY in ('" + fSGNo + "') and b.CATEGORY_OPT in ('" + fSsGNo + "')  "
                            + " and b.MINOR_COMMUNITY in ('" + fSssGNo + "')").getResultList();

                    for (int j = 0; j < resultList1.size(); j++) {
                        if (resultList1.get(j).getSector().equalsIgnoreCase(fGNo)) {
                            if (resultList1.get(j).getApplicantCategory().equalsIgnoreCase(fSGNo)) {
                                if (resultList1.get(j).getCategoryOpt().equalsIgnoreCase(fSsGNo)) {
                                    if (resultList1.get(j).getMinorCommunity().equalsIgnoreCase(fSssGNo)) {
                                        if (resultList1.get(j).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            outStandAmount = outStandAmount.add(resultList1.get(j).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList1.get(j).getOutstanding());
                                        } else {
                                            outStandAmount = outStandAmount.add(resultList1.get(j).getOutstanding());
                                        }
                                        noOfAc = noOfAc + 1;
                                    }
                                }
                            }
                        }
                    }
                    for (int k = 0; k < resultList3.size(); k++) {
                        if (resultList3.get(k).getSector().equalsIgnoreCase(fGNo)) {
                            if (resultList3.get(k).getApplicantCategory().equalsIgnoreCase(fSGNo)) {
                                if (resultList3.get(k).getCategoryOpt().equalsIgnoreCase(fSsGNo)) {
                                    if (resultList3.get(k).getMinorCommunity().equalsIgnoreCase(fSssGNo)) {
                                        if (resultList3.get(k).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            outStandAmountWk = outStandAmountWk.add(resultList3.get(k).getOutstanding().compareTo(new BigDecimal("0")) >= 0 ? new BigDecimal("0") : resultList3.get(k).getOutstanding());
                                        } else {
                                            outStandAmountWk = outStandAmountWk.add(resultList3.get(k).getOutstanding());
                                        }
                                        noOfAcWk = noOfAcWk + 1;
                                        if (!acOpenList.isEmpty()) {
                                            for (int z = 0; z < acOpenList.size(); z++) {
                                                Vector acOpenVect = (Vector) acOpenList.get(z);
                                                String openAc = acOpenVect.get(0).toString();
                                                if (openAc.equalsIgnoreCase(resultList3.get(k).getAcNo())) {
                                                    noOfAcSc = noOfAcSc + 1;
                                                    sanAmountSc = sanAmountSc.add(resultList3.get(k).getSanctionAmt());
                                                    advAmountSc = advAmountSc.add(resultList3.get(k).getDisbAmount());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    PrioritySectorPojo pojo = new PrioritySectorPojo();
                    pojo.setReportName(reportName);
                    pojo.setsNo(i);
                    pojo.setDescription(description);
                    pojo.setgNo(gNo);
                    pojo.setsGNo(sGNo);
                    pojo.setSsGNo(ssGNo);
                    pojo.setSssGNo(sssGNo);
                    pojo.setSsssGNo(ssssGNo);
                    pojo.setClassification(classification);
                    pojo.setCountApplicable(countApplicable);
                    pojo.setAcNature(acNature);
                    pojo.setAcType(acType);
                    pojo.setRangeBaseOn(rangeBaseOn);
                    pojo.setRangeFrom(rangeFrom);
                    pojo.setRangeTo(rangeTo);
                    pojo.setFormula(formula);
                    pojo.setfGNo(fGNo);
                    pojo.setfSGNo(fSGNo);
                    pojo.setfSsGNo(fSsGNo);
                    pojo.setfSssGNo(fSssGNo);
                    pojo.setfSsssGNo(fSsssGNo);
                    pojo.setNpaClassification(npaClassification);
                    pojo.setCountApplicable(countApplicable);

                    /*Total Priority Accounts*/
                    pojo.setTotAccountNo(noOfAc);
                    pojo.setOutstanding(outStandAmount.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));  //OSS
                    pojo.setnAccountNoWk(noOfAcWk);
                    pojo.setOutstandingWK(outStandAmountWk.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));
                    pojo.setnAccountNoSC(noOfAcSc);
                    pojo.setOutstandingSC(sanAmountSc.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));  //OSS
                    pojo.setOutstandingST(advAmountSc.abs().divide(repOpt, 2, BigDecimal.ROUND_HALF_UP));  //OSSDecimal.ROUND_HALF_UP));
                    rbiPojoTable.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return rbiPojoTable;
    }

    public List<PrioritySectorPojo> getValueFromFormula1(List<PrioritySectorPojo> dataList, String formula) throws ApplicationException {
        try {

            String exp = "", exp1 = "", exp2 = "", exp3 = "", exp4 = "", exp5 = "", exp6 = "", exp7 = "";
            List<PrioritySectorPojo> rbiPojoTable = new ArrayList<PrioritySectorPojo>();
            List<PrioritySectorPojo> rbiPojoTab = new ArrayList<PrioritySectorPojo>();
            String[] arr = formula.split(" ");
            for (int i = 0; i < arr.length; i++) {
                PrioritySectorPojo pojo2 = new PrioritySectorPojo();
                BigDecimal totAmt = new BigDecimal("0.0"), nAmtSc = new BigDecimal("0.0");
                BigDecimal nAmtST = new BigDecimal("0.0"), nAmtMinor = new BigDecimal("0.0");
                long toAc = 0l, nAcSc = 0l, nAcST = 0l, nAcMin = 0l;
                if (arr[i].contains(",")) {
                    String[] strArr = arr[i].split(",");
                    int[] arr1 = new int[strArr.length];
                    for (int l = 0; l < strArr.length; l++) {
                        arr1[l] = Integer.parseInt(strArr[l]);
                    }
                    rbiPojoTable = getOperandBalance1(dataList, arr[i]);
//                    for (int k = 0; k < rbiPojoTable.size(); k++) {
                    totAmt = rbiPojoTable.get(27).getTotAmount();
//                        totAmt = rbiPojoTable.get(k).getTotAmount();
//                    System.out.println("::Total Amount" + totAmt);
//                        }  
                    if (exp.equals("")) {
                        exp = exp + totAmt.toString();
                    } else {
                        exp = exp + " " + totAmt.toString();
                    }
                    totAmt = PostfixEvaluator.evaluate(exp);
//                        System.out.println("SC Amount Inside "+nAmtSc + "::ST Amount" +nAmtST);

//                    System.out.println("::Total Amount After" + totAmt);
                    pojo2.setTotAmount(totAmt);
                    rbiPojoTab.add(pojo2);
                } else {
                    if (exp.equals("")) {
                        exp = exp + arr[i];

                    } else {
                        exp = exp + " " + arr[i];
                    }
                    totAmt = PostfixEvaluator.evaluate(exp);
//                        System.out.println("SC Amount Inside "+nAmtSc + "::ST Amount" +nAmtST);

//                    System.out.println("::Total Amount After Formula" + totAmt);
                    pojo2.setTotAmount(totAmt);
                    rbiPojoTab.add(pojo2);
                }
            }
            return rbiPojoTab;
//            return PostfixEvaluator.evaluate(exp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<PrioritySectorPojo> getOperandBalance1(List<PrioritySectorPojo> dataList, String csvExp) throws ApplicationException {
        try {
            List<PrioritySectorPojo> rbiPojoTable = new ArrayList<PrioritySectorPojo>();
            List<PrioritySectorPojo> rbiPojoTab = new ArrayList<PrioritySectorPojo>();
            BigDecimal totAmount = new BigDecimal("0"), nAmountSc = new BigDecimal("0"), nAmountST = new BigDecimal("0"), nAmountMinor = new BigDecimal("0");
            long totAcno = 0l, nAcNoSC = 0l, nAcNoST = 0l, nAcNoMinor = 0l;
            String[] strArr = csvExp.split(",");
            int[] arr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0 && arr[4] != 0) {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3]) && (pojo.getSsssGNo() == arr[4])) {
                        totAmount = totAmount.add(pojo.getTotAmount());

                    }
                    pojo.setTotAmount(totAmount);
                    rbiPojoTable.add(pojo);
                }
            } else if (arr[1] != 0 && arr[2] != 0 && arr[3] != 0) {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2]) && (pojo.getSssGNo() == arr[3])) {
                        totAmount = totAmount.add(pojo.getTotAmount());


                    }
                    pojo.setTotAmount(totAmount);
                    rbiPojoTable.add(pojo);
                }
            } else if (arr[1] != 0 && arr[2] != 0) {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1]) && (pojo.getSsGNo() == arr[2])) {
                        totAmount = totAmount.add(pojo.getTotAmount());
                    }
                    pojo.setTotAmount(totAmount);
                    rbiPojoTable.add(pojo);
                }
            } else if (arr[1] != 0) {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0]) && (pojo.getsGNo() == arr[1])) {
                        totAmount = totAmount.add(pojo.getTotAmount());
                    }
                    rbiPojoTable.add(pojo);
                }
            } else {
                for (PrioritySectorPojo pojo : dataList) {
                    if ((pojo.getgNo() == arr[0])) {
                        totAmount = totAmount.add(pojo.getTotAmount());
                    }
                    pojo.setTotAmount(totAmount);
                    rbiPojoTable.add(pojo);
                }
            }
            return rbiPojoTable;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<LoanMisCellaniousPojo> getAdvToDirRelative(String branchCode, String dt, BigDecimal rptOpt) throws ApplicationException {
        /* Parameter basically used, when the report call from other sources the it's value is "N", other wise it will be "Y" */
        List sectorList = null;
        Vector sectorVect = null, tempVector = null;
        List<LoanMisCellaniousPojo> finalResult = new ArrayList<LoanMisCellaniousPojo>();
        try {
            List<LoanMisCellaniousPojo> finalResult1 = new ArrayList<LoanMisCellaniousPojo>();
            List<LoanMisCellaniousPojo> finalResult2 = new ArrayList<LoanMisCellaniousPojo>();
            finalResult1 = loanReportFacade.cbsLoanMisReport("0A", "0", "", dt, "A", 0, 9999999999.99, "S", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "DIRREL", "", "", "", "", "", "", "", "Active", "0", "N", "S", "0", "N","0","N","N", "0", "0", "0", "0");
            finalResult2 = loanReportFacade.cbsLoanMisReport("0A", "0", "", dt, "A", 0, 9999999999.99, "S", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "DIR", "", "", "", "", "", "", "", "Active", "0", "N", "S", "0", "N","0","N","N", "0", "0", "0", "0");
//            finalResult = loanReportFacade.cbsLoanMisReport("0A", "0", "", dt, "A", 0, 9999999999.99, "S", "", "", "", "", "", "", "",
//                    "", "", "", "", "", "", "", "", "", "DIRREL", "", "", "", "", "", "", "", "Active", "0", "N", "S");
            finalResult.addAll(finalResult1);
            finalResult.addAll(finalResult2);
            if (!finalResult.isEmpty()) {
                for (int i = 0; i < finalResult.size(); i++) {
                    BigDecimal lienValue = new BigDecimal("0");
                    BigDecimal matValue = new BigDecimal("0");
                    String natureOfSec = "";
                    String matDt = "";
                    String dirName = "";
                    Float prinAmt = 0.0f;
                    String FDDt = null;
                    Float prinAmtD = 0.0f;
                    List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,''),ifnull(lienacno,''),ifnull(IntTableCode,''),ifnull(MargineROI,0) from loansecurity where acno ='" + finalResult.get(i).getAcNo() + "'").getResultList();
                    if (!loanSecurity.isEmpty()) {
                        for (int l = 0; l < loanSecurity.size(); l++) {
                            Vector vect = (Vector) loanSecurity.get(l);
                            lienValue = lienValue.add(new BigDecimal(vect.get(2).toString()));
                            natureOfSec = natureOfSec.concat(vect.get(3).toString());
                            String secODScheme = vect.get(5).toString();
                            String margin = vect.get(6).toString();
                            List lm = em.createNativeQuery("select VoucherNo from td_vouchmst where acno ='" + vect.get(4).toString() + "' and lien ='Y';").getResultList();
                            if (!lm.isEmpty()) {
                                Vector vec = (Vector) lm.get(0);
                                String Actype = vect.get(4).toString().substring(2, 4);
                                List chk3 = em.createNativeQuery("SELECT a.prinAmt,a.FDDt,a.MatDt,a.ROI FROM td_vouchmst a,td_accountmaster b"
                                        + " WHERE a.voucherNo = " + vec.get(0).toString() + " and b.accttype = '" + Actype + "' and a.acno = '" + vect.get(4).toString() + "' and a.acno=b.acno").getResultList();
                                if (!chk3.isEmpty()) {
                                    for (int j = 0; j < chk3.size(); j++) {
                                        Vector ele = (Vector) chk3.get(j);
                                        prinAmt = Float.parseFloat(ele.get(0).toString());
                                        List flagList = em.createNativeQuery("select ifnull(ADHOC_PASS_SHEET_PRINT_EVENT,'N'), ifnull(COMMITMENT_EVENT,0) from cbs_scheme_currency_details where SCHEME_CODE = '" + secODScheme + "'").getResultList();
                                        if (!flagList.isEmpty()) {
                                            Vector flagVect = (Vector) flagList.get(0);
                                            if (margin.equalsIgnoreCase("")) {
                                                margin = flagVect.get(0).toString();
                                            }
                                            if (flagVect.get(0).toString().equalsIgnoreCase("Y")) {
                                                String result = loanRemote.tdLienPresentAmount(vect.get(4).toString(), Float.valueOf(vec.get(0).toString()), prinAmt);
                                                if (result == null) {
                                                    prinAmt = prinAmt;
                                                } else {
                                                    int n = result.indexOf("*");
                                                    prinAmt = Float.parseFloat(result.substring(n + 1));
                                                }
                                            }
                                        }
                                        prinAmtD = prinAmt - ((prinAmt * Float.parseFloat(margin)) / 100);
                                    }
                                }
                            }
                            matValue = matValue.add(new BigDecimal(prinAmtD));
//                            matDt = matDt.concat("\n").concat(vect.get(1).toString().equalsIgnoreCase("") ? "" : dmyFormat.format(y_m_dFormat.parse(vect.get(1).toString())));
                        }
                    }
                    if (!finalResult.get(i).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List emiDetail = em.createNativeQuery("select ifnull(max(duedt),'2099-03-31') from emidetails details where acno ='" + finalResult.get(i).getAcNo() + "'").getResultList();
                        if (!emiDetail.isEmpty()) {
                            Vector vect = (Vector) emiDetail.get(0);
                            matDt = dmyFormat.format(y_m_dFormat.parse(vect.get(0).toString()));
                        }
                    }
                    List dirNameList = em.createNativeQuery("select dir_cust_id from cbs_loan_borrower_details where acc_no ='" + finalResult.get(i).getAcNo() + "'").getResultList();
                    if (!dirNameList.isEmpty()) {
                        Vector vect = (Vector) dirNameList.get(0);
                        List dirNameLst = em.createNativeQuery("select custname from cbs_customer_master_detail where customerid ='" + vect.get(0).toString() + "'").getResultList();
                        if (!dirNameLst.isEmpty()) {
                            Vector vect1 = (Vector) dirNameLst.get(0);
                            dirName = vect1.get(0).toString();
                        }
                    }
                    finalResult.get(i).setDirName(dirName);
                    finalResult.get(i).setNatureOfSecurity(natureOfSec);
                    finalResult.get(i).setValueOfSecurity(matValue.divide(rptOpt));
                    finalResult.get(i).setSanctionAmt(finalResult.get(i).getSanctionAmt().divide(rptOpt));
                    finalResult.get(i).setOutstanding(finalResult.get(i).getOutstanding().divide(rptOpt));
                    finalResult.get(i).setExposureCategory(String.valueOf(finalResult.get(i).getOutstanding()));
                    finalResult.get(i).setMaturityDt(matDt);
                }
            } else {
                LoanMisCellaniousPojo pojo = new LoanMisCellaniousPojo();
                pojo.setCustName("");
                pojo.setAcNo("");
                finalResult.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return finalResult;
    }

    public List<Form1UnencumAppSecPojo> getUnencumberedApprovedSecurities(String dt, BigDecimal repOpt) throws ApplicationException {
        List<Form1UnencumAppSecPojo> finalResult = new ArrayList<Form1UnencumAppSecPojo>();
        try {
            List friDate = em.createNativeQuery("select date_format(REPFRIDATE,'%Y%m%d') from ho_reportingfriday "
                    + " where (REPFRIDATE between  DATE_FORMAT('" + dt + "' ,'%Y-%m-01') AND '" + dt + "')").getResultList();
            if (!friDate.isEmpty()) {
                for (int l = 0; l < friDate.size(); l++) {
                    Vector vect = (Vector) friDate.get(l);
                    String curDate = vect.get(0).toString();

                    BigDecimal aFaceCloseBal = new BigDecimal("0"), aBookCloseBal = new BigDecimal("0");
                    BigDecimal aDepCloseBal = new BigDecimal("0"), aNetCloseBal = new BigDecimal("0");
                    BigDecimal bFaceCloseBal, bBookCloseBal, bDepCloseBal, bNetCloseBal;

                    List prevDate = em.createNativeQuery("select date_format(max(REPFRIDATE),'%Y%m%d') from ho_reportingfriday "
                            + "where repfridate <'" + curDate + "'").getResultList();

                    if (!prevDate.isEmpty()) {
                        Vector vect1 = (Vector) prevDate.get(0);
                        String preDate = vect1.get(0).toString();

                        Form1UnencumAppSecPojo pojoTable = new Form1UnencumAppSecPojo();

                        pojoTable.setSrNo(1);
                        pojoTable.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable.setDescription("Part - I(Government Securities)");
                        pojoTable.setFaceValue(BigDecimal.ZERO);
                        pojoTable.setBookValue(BigDecimal.ZERO);
                        pojoTable.setDepr(BigDecimal.ZERO);
                        pojoTable.setNetValue(BigDecimal.ZERO);
                        finalResult.add(pojoTable);

                        aFaceCloseBal = aFaceCloseBal.add(BigDecimal.ZERO);
                        aBookCloseBal = aBookCloseBal.add(BigDecimal.ZERO);
                        aDepCloseBal = aDepCloseBal.add(BigDecimal.ZERO);
                        aNetCloseBal = aNetCloseBal.add(BigDecimal.ZERO);


                        List faceValLst = em.createNativeQuery("select cast(ifnull(sum(ifnull(FACEVALUE,0)),0) as decimal(15,2)) from investment_master "
                                + " where sectype in ('GOVERNMENT SECURITIES','TREASURY BILLS') and settledt <='" + preDate + "' and matdt>='" + preDate + "' "
                                + " and (closedt is null or closedt>='" + preDate + "')").getResultList();
                        BigDecimal faceVal = new BigDecimal("0");
                        if (!faceValLst.isEmpty()) {
                            Vector faceVect = (Vector) faceValLst.get(0);
                            faceVal = new BigDecimal(faceVect.get(0).toString()).divide(repOpt, 2, BigDecimal.ROUND_HALF_UP);
                        }


                        List bookValLst = em.createNativeQuery("select cast(ifnull(sum(ifnull(c.bookAmt,0)),0) as decimal(25,2)) from ("
                                + " select a.controlno,(cast(a.bookVALUE as decimal(25,2)) - ifnull(b.amt,0)) as bookAmt from "
                                + " investment_master a left join (select ctrl_no,sum(amort_amt) as amt from "
                                + " investment_amortization_details where status = 'p' and LAST_UPDATE_DT <= '" + preDate + "' "
                                + " group by ctrl_no) b on a.controlno = b.ctrl_no where a.sectype in ('GOVERNMENT SECURITIES','TREASURY BILLS') "
                                + " and a.settledt <='" + preDate + "' and a.matdt>='" + preDate + "' "
                                + " and (a.closedt is null or a.closedt>='" + preDate + "')) c").getResultList();
                        BigDecimal bookVal = new BigDecimal("0");
                        if (!faceValLst.isEmpty()) {
                            Vector bookVect = (Vector) bookValLst.get(0);
                            bookVal = new BigDecimal(bookVect.get(0).toString()).divide(repOpt, 2, BigDecimal.ROUND_HALF_UP);
                        }

                        BigDecimal depVal = new BigDecimal("0");

                        Form1UnencumAppSecPojo pojoTable1 = new Form1UnencumAppSecPojo();

                        pojoTable1.setSrNo(2);
                        pojoTable1.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable1.setDescription("Opening Balance");
                        pojoTable1.setFaceValue(faceVal);
                        pojoTable1.setBookValue(bookVal);
                        pojoTable1.setDepr(depVal);
                        pojoTable1.setNetValue(bookVal.subtract(depVal));
                        finalResult.add(pojoTable1);

                        aFaceCloseBal = aFaceCloseBal.add(faceVal);
                        aBookCloseBal = aBookCloseBal.add(bookVal);
                        aDepCloseBal = aDepCloseBal.add(depVal);
                        aNetCloseBal = aNetCloseBal.add(bookVal.subtract(depVal));

                        faceValLst = em.createNativeQuery("select ifnull(cast(sum(ifnull(FACEVALUE,0)) as decimal(15,2)),0) "
                                + " from investment_master where sectype in ('GOVERNMENT SECURITIES','TREASURY BILLS') and "
                                + " settledt between '" + CbsUtil.dateAdd(preDate, 1) + "' and '" + curDate + "'").getResultList();
                        faceVal = new BigDecimal("0");
                        if (!faceValLst.isEmpty()) {
                            Vector faceVect = (Vector) faceValLst.get(0);
                            faceVal = new BigDecimal(faceVect.get(0).toString()).divide(repOpt, 2, BigDecimal.ROUND_HALF_UP);
                        }


                        bookValLst = em.createNativeQuery("select ifnull(cast(sum(ifnull(c.bookAmt,0)) as decimal(25,2)),0) from ("
                                + " select a.controlno,(cast(a.bookVALUE as decimal(25,2)) - ifnull(b.amt,0)) as bookAmt "
                                + " from investment_master a left join (select ctrl_no,sum(amort_amt) as amt from "
                                + " investment_amortization_details where status = 'p' and LAST_UPDATE_DT "
                                + " between '" + CbsUtil.dateAdd(preDate, 1) + "' and '" + curDate + "' group by ctrl_no) b on a.controlno = b.ctrl_no where "
                                + " a.sectype in ('GOVERNMENT SECURITIES','TREASURY BILLS') and a.settledt between '" + CbsUtil.dateAdd(preDate, 1) + "' and '" + curDate + "')c").getResultList();

                        bookVal = new BigDecimal("0");
                        if (!faceValLst.isEmpty()) {
                            Vector bookVect = (Vector) bookValLst.get(0);
                            bookVal = new BigDecimal(bookVect.get(0).toString()).divide(repOpt, 2, BigDecimal.ROUND_HALF_UP);
                        }


                        depVal = new BigDecimal("0");

                        Form1UnencumAppSecPojo pojoTable2 = new Form1UnencumAppSecPojo();
                        pojoTable2.setSrNo(3);
                        pojoTable2.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable2.setDescription("Addition during the fortnights (+)");
                        pojoTable2.setFaceValue(faceVal);
                        pojoTable2.setBookValue(bookVal);
                        pojoTable2.setDepr(depVal);
                        pojoTable2.setNetValue(bookVal.subtract(depVal));
                        finalResult.add(pojoTable2);

                        aFaceCloseBal = aFaceCloseBal.add(faceVal);
                        aBookCloseBal = aBookCloseBal.add(bookVal);
                        aDepCloseBal = aDepCloseBal.add(depVal);
                        aNetCloseBal = aNetCloseBal.add(bookVal.subtract(depVal));

                        faceValLst = em.createNativeQuery("select ifnull(cast(sum(ifnull(FACEVALUE,0)) as decimal(15,2)),0) "
                                + " from investment_master where sectype  in ('GOVERNMENT SECURITIES','TREASURY BILLS') and "
                                + " closedt between '" + preDate + "' and '" + curDate + "'").getResultList();
                        faceVal = new BigDecimal("0");
                        if (!faceValLst.isEmpty()) {
                            Vector faceVect = (Vector) faceValLst.get(0);
                            faceVal = new BigDecimal(faceVect.get(0).toString()).divide(repOpt, 2, BigDecimal.ROUND_HALF_UP);
                        }


                        bookValLst = em.createNativeQuery("select ifnull(cast(sum(ifnull(c.bookAmt,0)) as decimal(25,2)),0) from ("
                                + " select a.controlno,(cast(a.bookVALUE as decimal(25,2)) - ifnull(b.amt,0)) as bookAmt "
                                + " from investment_master a left join (select ctrl_no,sum(amort_amt) as amt from "
                                + " investment_amortization_details where status = 'p' and LAST_UPDATE_DT "
                                + " between '" + preDate + "' and '" + curDate + "' group by ctrl_no) b on a.controlno = b.ctrl_no where "
                                + " a.sectype in ('GOVERNMENT SECURITIES','TREASURY BILLS') and a.closedt between '" + preDate + "' and '" + curDate + "')c").getResultList();

                        bookVal = new BigDecimal("0");
                        if (!faceValLst.isEmpty()) {
                            Vector bookVect = (Vector) bookValLst.get(0);
                            bookVal = new BigDecimal(bookVect.get(0).toString()).divide(repOpt, 2, BigDecimal.ROUND_HALF_UP);
                        }


                        depVal = new BigDecimal("0");
                        List depValList = em.createNativeQuery("select ifnull(sum(amort_amt),0) from investment_amortization_details "
                                + "where status='P' and last_update_dt between '" + CbsUtil.dateAdd(preDate, 1) + "' and '" + curDate + "'").getResultList();
                        if (!depValList.isEmpty()) {
                            Vector depVect = (Vector) depValList.get(0);
                            depVal = new BigDecimal(depVect.get(0).toString()).divide(repOpt, 2, BigDecimal.ROUND_HALF_UP);
                        }

                        Form1UnencumAppSecPojo pojoTable3 = new Form1UnencumAppSecPojo();

                        pojoTable3.setSrNo(4);
                        pojoTable3.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable3.setDescription("Deduction during the fortnights (-)");
                        pojoTable3.setFaceValue(faceVal);
                        pojoTable3.setBookValue(bookVal);
                        pojoTable3.setDepr(depVal);
                        pojoTable3.setNetValue(bookVal.subtract(depVal));
                        finalResult.add(pojoTable3);

                        aFaceCloseBal = aFaceCloseBal.subtract(faceVal);
                        aBookCloseBal = aBookCloseBal.subtract(bookVal);
                        aDepCloseBal = aDepCloseBal.add(depVal);
                        aNetCloseBal = aNetCloseBal.subtract(bookVal.add(depVal));

                        Form1UnencumAppSecPojo pojoTable4 = new Form1UnencumAppSecPojo();
                        pojoTable4.setSrNo(5);
                        pojoTable4.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable4.setDescription("Closing Balance (a)");
                        pojoTable4.setFaceValue(aFaceCloseBal);
                        pojoTable4.setBookValue(aBookCloseBal);
                        pojoTable4.setDepr(aDepCloseBal);
                        pojoTable4.setNetValue(aNetCloseBal);
                        finalResult.add(pojoTable4);

                        Form1UnencumAppSecPojo pojoTable5 = new Form1UnencumAppSecPojo();
                        pojoTable5.setSrNo(6);
                        pojoTable5.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable5.setDescription("Part -II (Other Approved Securities");
                        pojoTable5.setFaceValue(new BigDecimal("0"));
                        pojoTable5.setBookValue(new BigDecimal("0"));
                        pojoTable5.setDepr(new BigDecimal("0"));
                        pojoTable5.setNetValue(new BigDecimal("0"));
                        finalResult.add(pojoTable5);

                        Form1UnencumAppSecPojo pojoTable6 = new Form1UnencumAppSecPojo();
                        pojoTable6.setSrNo(7);
                        pojoTable6.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable6.setDescription("Opening Balance");
                        pojoTable6.setFaceValue(new BigDecimal("0"));
                        pojoTable6.setBookValue(new BigDecimal("0"));
                        pojoTable6.setDepr(new BigDecimal("0"));
                        pojoTable6.setNetValue(new BigDecimal("0"));
                        finalResult.add(pojoTable6);

                        Form1UnencumAppSecPojo pojoTable7 = new Form1UnencumAppSecPojo();
                        pojoTable7.setSrNo(8);
                        pojoTable7.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable7.setDescription("Addition during the fortnights (+)");
                        pojoTable7.setFaceValue(new BigDecimal("0"));
                        pojoTable7.setBookValue(new BigDecimal("0"));
                        pojoTable7.setDepr(new BigDecimal("0"));
                        pojoTable7.setNetValue(new BigDecimal("0"));
                        finalResult.add(pojoTable7);

                        Form1UnencumAppSecPojo pojoTable8 = new Form1UnencumAppSecPojo();
                        pojoTable8.setSrNo(9);
                        pojoTable8.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable8.setDescription("Deduction during the fortnights (-)");
                        pojoTable8.setFaceValue(new BigDecimal("0"));
                        pojoTable8.setBookValue(new BigDecimal("0"));
                        pojoTable8.setDepr(new BigDecimal("0"));
                        pojoTable8.setNetValue(new BigDecimal("0"));
                        finalResult.add(pojoTable8);

                        Form1UnencumAppSecPojo pojoTable9 = new Form1UnencumAppSecPojo();
                        pojoTable9.setSrNo(10);
                        pojoTable9.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable9.setDescription("Closing Balance (b)");
                        pojoTable9.setFaceValue(new BigDecimal("0"));
                        pojoTable9.setBookValue(new BigDecimal("0"));
                        pojoTable9.setDepr(new BigDecimal("0"));
                        pojoTable9.setNetValue(new BigDecimal("0"));
                        finalResult.add(pojoTable9);

                        Form1UnencumAppSecPojo pojoTable10 = new Form1UnencumAppSecPojo();
                        pojoTable10.setSrNo(11);
                        pojoTable10.setDate(dmyFormat.format(ymdFormat.parse(curDate)));
                        pojoTable10.setDescription("Closing Balance (a+b)");
                        pojoTable10.setFaceValue(aFaceCloseBal);
                        pojoTable10.setBookValue(aBookCloseBal);
                        pojoTable10.setDepr(aDepCloseBal);
                        pojoTable10.setNetValue(aNetCloseBal);
                        finalResult.add(pojoTable10);
                    } else {
                        throw new ApplicationException("Please Check Reporting Friday Date");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return finalResult;
    }
}
