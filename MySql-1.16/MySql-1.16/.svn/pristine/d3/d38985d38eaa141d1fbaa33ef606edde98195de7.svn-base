package com.cbs.facade.report;

import com.cbs.dto.report.AcTypeWiseDisdreceiptPojo;
import com.cbs.dto.report.LienReportPojo;
import com.cbs.dto.report.WorkingStmtBorrowingPojo;
import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.loan.CCTurnOverReportPojo;
import com.cbs.dto.loan.StatementOfAlmPojo;
import com.cbs.dto.report.AbbStatementPojo;
import com.cbs.dto.report.LoanIntCalculationPojo;
import com.cbs.dto.report.LoanIntCertPojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.LoanAcDetailStatementPojo;
import com.cbs.dto.report.LoanAcDetailsTable;
import com.cbs.dto.report.LoanAccStmPojo;
import com.cbs.dto.report.LoanBorrowerModPojo;
import com.cbs.dto.report.LoanDisbursementPojo;
import com.cbs.dto.report.LoanIntCertPoJoAll;
import com.cbs.dto.report.LoanOutStandingBalancePojo;
import com.cbs.dto.report.LoanPeriodPojo;
import com.cbs.dto.report.LoanRecoveryTable;
import com.cbs.dto.report.LoanRepaymentPojo;
import com.cbs.dto.report.LoanSanctionDetailPojo;
import com.cbs.dto.report.NpaAccStmPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.dto.report.RbiInspectionPojo;
import com.cbs.dto.report.SortedDisbursementByacno;
import com.cbs.dto.report.SortedDisbursementDate;
import com.cbs.dto.report.StatemenrtOfRecoveriesPojo;
import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import com.cbs.dto.report.ho.LoanEmiDetailPojo;
import com.cbs.dto.report.ho.LoanSchemeWiseDetailPojo;
import com.cbs.dto.report.ho.NoticeToBorrowerPojo;
import com.cbs.dto.report.ho.StockStatementRepPojo;
import com.cbs.pojo.LoanPrincipalInterestPojo;
import com.cbs.pojo.RecoveryDetailPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.pojo.DateSlabPojo;
import com.cbs.pojo.FolioLedgerPojo;
import com.cbs.pojo.GuarantorDetailPojo;
import com.cbs.pojo.LoanRenewalSecurityPojo;
import com.cbs.pojo.OverDueListPojo;
import com.cbs.pojo.SavingIntRateChangePojo;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "LoanReportFacade")
public class LoanReportFacade implements LoanReportFacadeRemote {

    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    AccountOpeningFacadeRemote accountRemote;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private AccountOpeningFacadeRemote acOpenFacadeRemote;
    @EJB
    private PostalDetailFacadeRemote postalFacadeRemote;
    @EJB
    AdvancesInformationTrackingRemote aitr;
    @EJB
    ShareReportFacadeRemote shareRemote;
    @EJB
    NpaReportFacadeRemote npaRemote;
    @EJB
    RbiReportFacadeRemote rbiRemote;
    @EJB
    SbIntCalcFacadeRemote sbRemote;
    @EJB
    TermDepositMasterFacadeRemote tdRemote;
    @EJB
    DDSReportFacadeRemote ddsRemote;
    @EJB
    OverDueReportFacadeRemote overDueReportFacade;
    @EJB
    LoanReportFacadeRemote loanRemote;
    @PersistenceContext
    private EntityManager em;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat y_m_dFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat y_m_d_h_m_sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private NumberFormat nft = new DecimalFormat("0.00");
    Date date = new Date();

    /**
     * ********************* DATED SECURITY *************
     */
    /**
     *
     * @param dt1
     * @param days
     * @param security_type
     * @param brnCode
     * @return
     */
    @Override
    public List getSchemeType(String accType) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT 'ALL' as scheme_code,'ALL' as scheme_description  UNION (select scheme_code,scheme_description from cbs_scheme_general_scheme_parameter_master where scheme_type ='" + accType + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<LoanMisCellaniousPojo> cbsLoanMisReport(String branchCode, String acNature, String accType, String dt, String reportBasedOn, double from, double to, String reportBase, String sector, String subSector, String modeOfAdvance, String secured, String typeOfAdvance, String purposeOfAdvance, String guarnteeCover, String purOfAdv, String exposure, String exposureCategory, String exposureCategoryPurpose, String schemeCode, String intType, String applicantCategory, String categoryOpt, String minorCommunity, String relation, String relOwner, String drawingPwrInd, String popuGroup, String sanctionLevel, String sanctionAuth, String courts, String restructuring, String loanPeriod, String gender, String reportIn, String parameter, String industry, String overDue, String npaClass, String withInsurance, String withSecurity,
            String securityNature, String securityType, String securityDesc1, String securityDesc2) throws ApplicationException {
        /* Parameter basically used, when the report call from other sources the it's value is "N", other wise it will be "Y" */
        List sectorList = null;
        Vector sectorVect = null, tempVector = null;
        List<LoanMisCellaniousPojo> finalResult = new ArrayList<LoanMisCellaniousPojo>();
        String branchCodeQuery = "", accTypeQuery = "", reportBasedOnQuery = "",
                reportBaseQuery = "", sectorQuery = "", subSectorQuery = "", modeOfAdvanceQuery = "", securedQuery = "",
                typeOfAdvanceQuery = "", purposeOfAdvanceQuery = "", guarnteeCoverQuery = "", purOfAdvQuery = "", exposureQuery = "",
                exposureCategoryQuery = "", exposureCategoryPurposeQuery = "", schemeCodeQuery = "", intTypeQuery = "",
                applicantCategoryQuery = "", categoryOptQuery = "", minorCommunityQuery = "", relationQuery = "", relOwnerQuery = "",
                drawingPwrIndQuery = "", popuGroupQuery = "", sanctionLevelQuery = "", sanctionAuthQuery = "", courtsQuery = "", industryQuery = "",
                restructuringQuery = "", genderQuery = "", securityNatureQuery = "", securityTypeQuery = "", securityDesc1Query = "", securityDesc2Query = "";
        String brnCodeOrderBy = "", acNatureOrderBy = "", accTypeOrderBy = "", sectorOrderBy = "",
                subSectorOrderBy = "", modeOfAdvanceOrderBy = "", securedOrderBy = "",
                typeOfAdvanceOrderBy = "", purposeOfAdvanceOrderBy = "", guarnteeCoverOrderBy = "", purOfAdvOrderBy = "",
                exposureOrderBy = "", exposureCategoryOrderBy = "", exposureCategoryPurposeOrderBy = "", schemeCodeOrderBy = "",
                intTypeOrderBy = "", applicantCategoryOrderBy = "", categoryOptOrderBy = "", minorCommunityOrderBy = "",
                relationOrderBy = "", relOwnerOrderBy = "", drawingPwrIndOrderBy = "", popuGroupOrderBy = "", sanctionLevelOrderBy = "",
                sanctionAuthOrderBy = "", courtsOrderBy = "", restructuringOrderBy = "", GenderOrderBy = "", openingDt = "", IndustryOrderBy = "";
        List<OverdueEmiReportPojo> overDueDetails = new ArrayList<OverdueEmiReportPojo>();
        List<OverdueNonEmiResultList> resultList1 = new ArrayList<OverdueNonEmiResultList>();
        List<OverdueNonEmiResultList> resultList2 = new ArrayList<OverdueNonEmiResultList>();
        try {

            // New Code for Rbi Inspection Report
            Map<String, RbiInspectionPojo> mapRbiInspection = new HashMap<String, RbiInspectionPojo>();
            RbiInspectionPojo obj = null;
            if (securityNature == null) {
                securityNature = "0";
            }
            if (securityType == null) {
                securityType = "0";
            }
            if (securityDesc1 == null) {
                securityDesc1 = "0";
            }
            if (securityDesc2 == null) {
                securityDesc2 = "0";
            }

            if (withSecurity.equalsIgnoreCase("Y")) { //for RBI Inspection Report in Loan Account Details report
                String acCode = fts.getValueFromCbsparameterInfo("THRIFT-BAL-CODE");
                if (acCode.equalsIgnoreCase("")) {
                    acCode = "''";
                }
                List rbiInpectionList = em.createNativeQuery("select aa.foliono,aa.customerid,aa.shareBal,ifnull(bb.thriftbal,0) as thriftBalance,ifnull(bb.acno,'') as accountNo,date_format(aa.Regdate,'%d/%m/%Y') as menberDt from  "
                        + "(select a.foliono,a.customerid,sum(a.shval) as shareBal,Regdate from  "
                        + "(select distinct foliono,cc.customerid,sc.sharecertno,count(shareno)*  "
                        + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,sh.Regdate "
                        + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc "
                        + "where cs.issueDt<='" + dt + "' and sh.custid = cc.customerid and sc.foliono=sh.regfoliono "
                        + "and cs.certificateno=sc.sharecertno and (cs.paymentdt is null or cs.paymentdt ='' or cs.paymentdt > '" + dt + "') "
                        + "group by foliono,sc.sharecertno order by foliono) a group by a.foliono)aa "
                        + "left join "
                        + "(select b.custid,a.acno,cast(sum(cramt-dramt) as decimal(25,2)) as thriftbal from recon a,customerid b "
                        + "where substring(a.acno,3,2) in(" + acCode + ") and a.acno = b.acno and dt<='" + dt + "'"
                        + "group by a.acno)bb on cast(aa.customerid as unsigned) = bb.custid").getResultList();
                if (!rbiInpectionList.isEmpty()) {
                    for (int i = 0; i < rbiInpectionList.size(); i++) {
                        Vector vtr = (Vector) rbiInpectionList.get(i);
                        obj = new RbiInspectionPojo();
                        obj.setFolioNo(vtr.get(0).toString());
                        obj.setCustId(vtr.get(1).toString());
                        obj.setShareBal(new BigDecimal(vtr.get(2).toString()));
                        obj.setThriftBal(new BigDecimal(vtr.get(3).toString()));
                        obj.setMembershipDate(vtr.get(5).toString());
                        mapRbiInspection.put(vtr.get(1).toString(), obj);
                    }
                }
            }
            // End New Code for Rbi Inspection Report


            if (!(branchCode.equalsIgnoreCase("0A") || branchCode.equalsIgnoreCase("90"))) {
                branchCodeQuery = " substring(am.acno,1,2) = '" + branchCode + "' and ";
                brnCodeOrderBy = " brnCode, ";
            } else {
                Integer bankCode = fts.getCodeForReportName("LOAN_AT_HO");
                if (bankCode == 1 && branchCode.equalsIgnoreCase("90")) {
                    branchCodeQuery = " substring(am.acno,1,2) = '" + branchCode + "' and ";
                    brnCodeOrderBy = " brnCode, ";
                } else {
                    brnCodeOrderBy = " ";
                }
            }
            String notInQuery = "";
            List notInAcCode = common.getCode("ACNO_NOT_IN_MIS");
            if (!notInAcCode.isEmpty()) {
                String vect = notInAcCode.get(0).toString();
                notInQuery = " and acctcode not in ('" + notInAcCode.get(0).toString() + "')";
            }
            if (acNature.equalsIgnoreCase("0")) {
                accTypeQuery = " substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') " + notInQuery + " ) and ";
                acNatureOrderBy = " acNature, ";
                accTypeOrderBy = " acType, ";
                if ((parameter.equalsIgnoreCase("S") || parameter.equalsIgnoreCase("Y")) && overDue.equalsIgnoreCase("Y")) {
                    overDueDetails = overDueReportFacade.getOverdueEmiReport(1, 0, "", "ALL", 1, 5000, dt, branchCode, "", false, "", "N");
                    resultList1 = overDueReportFacade.getOverDueNonEmi("CA", "ALL", dt, branchCode, "N");
                    resultList2 = overDueReportFacade.getOverDueNonEmi("DL", "ALL", dt, branchCode, "N");
                }
            } else {
                if (!(acNature.equalsIgnoreCase("0") || acNature.equalsIgnoreCase("")) && (accType.equalsIgnoreCase("0") || accType.equalsIgnoreCase(""))) {
                    accTypeQuery = " substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and CrDbFlag in('B','D') " + notInQuery + " ) and ";
                    accTypeOrderBy = " acType, ";
                    if ((parameter.equalsIgnoreCase("S") || parameter.equalsIgnoreCase("Y")) && overDue.equalsIgnoreCase("Y")) {
                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            resultList1 = overDueReportFacade.getOverDueNonEmi("CA", "ALL", dt, branchCode, "N");
                        } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            resultList2 = overDueReportFacade.getOverDueNonEmi("DL", "ALL", dt, branchCode, "N");
                        } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            overDueDetails = overDueReportFacade.getOverdueEmiReport(1, 0, "", "ALL", 1, 5000, dt, branchCode, "", false, "", "N");
                        }
                    }
                } else {
                    accTypeQuery = " substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctcode in ('" + accType + "') ) and ";
                    if ((parameter.equalsIgnoreCase("S") || parameter.equalsIgnoreCase("Y")) && overDue.equalsIgnoreCase("Y")) {
                        if (common.getAcNatureByAcType(accType).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            resultList1 = overDueReportFacade.getOverDueNonEmi("CA", "ALL", dt, branchCode, "N");
                        } else if (common.getAcNatureByAcType(accType).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            resultList2 = overDueReportFacade.getOverDueNonEmi("DL", "ALL", dt, branchCode, "N");
                        } else if (common.getAcNatureByAcType(accType).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            overDueDetails = overDueReportFacade.getOverdueEmiReport(1, 0, "", "ALL", 1, 5000, dt, branchCode, "", false, "", "N");
                        }
                    }
                }
            }

            if (reportBasedOn.equalsIgnoreCase("P")) { //PERIOD
                reportBasedOnQuery = " a.LOAN_DURATION >=" + from + " and a.LOAN_DURATION <=" + to + " and ";
            } else if (reportBasedOn.equalsIgnoreCase("R")) { //ROI
                reportBasedOnQuery = " ";
            } else if (reportBasedOn.equalsIgnoreCase("A")) { //AMOUNT
                if (reportBase.equalsIgnoreCase("O")) {
                    reportBasedOnQuery = " ";
                } else if (reportBase.equalsIgnoreCase("S")) {//Sanction Amount
                    if (from != 0 && to != 0) {
                        reportBasedOnQuery = " la.ODLimit between " + from + " and " + to + " and ";
                    } else {
                        reportBasedOnQuery = " ";
                    }

                }
            }
            String effctiveQuery = " and '" + dt + "'  between eff_fr_dt and eff_to_dt ";

            String sectorDescJo = " bor.sector, bor.sectorDesc , ";
            String sectorDescCov = " r.sector as sector, r.sectorDesc , ";
            String sectorDesc = " a.sector as sector, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '182'  and REF_CODE = a.sector),'') as sectorDesc , ";
            if (!(sector.equalsIgnoreCase("0") || sector.equalsIgnoreCase(""))) { //182 - 
                sectorQuery = " a.sector in ('" + sector + "') and ";
                sectorOrderBy = " sector desc, ";

            }

            String subSectorDescJo = " bor.subSector, bor.subSectorDesc , ";
            String subSectorDescCov = " r.subSector, r.subSectorDesc , ";
            String subSectorDesc = " a.sub_sector  as subSector, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '183'  and REF_CODE = a.sub_sector " + effctiveQuery + " ),'') as subSectorDesc , ";
            if (!(subSector.equalsIgnoreCase("0") || subSector.equalsIgnoreCase(""))) { //183
                subSectorQuery = " a.sub_sector  in ('" + subSector + "') and ";
                subSectorOrderBy = " subSector, ";
            } else {
                if (!(sector.equalsIgnoreCase("0") || sector.equalsIgnoreCase(""))) {
                    subSectorOrderBy = " subSector, ";
                }
            }

            String modeOfAdvanceDescJo = " bor.modeOfAdvance, bor.modeOfAdvanceDesc , ";
            String modeOfAdvanceDescCov = " r.modeOfAdvance, r.modeOfAdvanceDesc , ";
            String modeOfAdvanceDesc = " a.MODE_OF_ADVANCE as modeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '185'  and REF_CODE = a.MODE_OF_ADVANCE),'') as modeOfAdvanceDesc , ";
            if (!(modeOfAdvance.equalsIgnoreCase("0") || modeOfAdvance.equalsIgnoreCase(""))) { //185 - Mode of Advance*
                modeOfAdvanceQuery = " a.MODE_OF_ADVANCE in ('" + modeOfAdvance + "') and ";
                modeOfAdvanceOrderBy = " modeOfAdvance, ";
            }

            String securedDescJo = " bor.secured, bor.securedDesc , ";
            String securedDescCov = " r.secured, r.securedDesc , ";
            String securedDesc = " a.SECURED as secured, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '187'  and REF_CODE = a.SECURED),'') as securedDesc , ";
            if (!(secured.equalsIgnoreCase("0") || secured.equalsIgnoreCase(""))) { //187 - Type of Advances*
                securedQuery = " a.SECURED in ('" + secured + "') and ";
                securedOrderBy = " secured, ";
            }

            String typeOfAdvanceDescJo = " bor.typeOfAdvance, bor.typeOfAdvanceDesc , ";
            String typeOfAdvanceDescCov = " r.typeOfAdvance, r.typeOfAdvanceDesc , ";
            String typeOfAdvanceDesc = " a.TYPE_OF_ADVANCE as typeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '186'  and REF_CODE = a.TYPE_OF_ADVANCE),'') as typeOfAdvanceDesc , ";
            if (!(typeOfAdvance.equalsIgnoreCase("0") || typeOfAdvance.equalsIgnoreCase(""))) { //186 - Term of Advance*
                typeOfAdvanceQuery = " a.TYPE_OF_ADVANCE in ('" + typeOfAdvance + "') and ";
                typeOfAdvanceOrderBy = " typeOfAdvance, ";
            }

            String purposeOfAdvanceDescJo = " bor.purposeOfAdvance, bor.purposeOfAdvanceDesc , ";
            String purposeOfAdvanceDescCov = " r.purposeOfAdvance, r.purposeOfAdvanceDesc , ";
            String purposeOfAdvanceDesc = " a.PURPOSE_OF_ADVANCE as purposeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '190'  and REF_CODE = a.PURPOSE_OF_ADVANCE),'') as purposeOfAdvanceDesc , ";
            if (!(purposeOfAdvance.equalsIgnoreCase("0") || purposeOfAdvance.equalsIgnoreCase(""))) { //190 - Sub-Sector's Category(for Quarterly) 
                purposeOfAdvanceQuery = " a.PURPOSE_OF_ADVANCE in ('" + purposeOfAdvance + "') and ";
                purposeOfAdvanceOrderBy = " purposeOfAdvance, ";
            }

            String guarnteeCoverDescJo = " bor.guarnteeCover, bor.guarnteeCoverDesc , ";
            String guarnteeCoverDescCov = " r.guarnteeCover, r.guarnteeCoverDesc , ";
            String guarnteeCoverDesc = " a.GUARANTEE_COVER as guarnteeCover, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '188'  and REF_CODE = a.GUARANTEE_COVER),'') as guarnteeCoverDesc , ";
            if (!(guarnteeCover.equalsIgnoreCase("0") || guarnteeCover.equalsIgnoreCase(""))) { //188 - Sub-Sector's Category(for Yearly)
                guarnteeCoverQuery = " a.GUARANTEE_COVER in ('" + guarnteeCover + "') and ";
                guarnteeCoverOrderBy = " guarnteeCover, ";
            }

            String purOfAdvDescJo = " bor.purOfAdv, bor.purOfAdvDesc , ";
            String purOfAdvDescCov = " r.purOfAdv, r.purOfAdvDesc , ";
            String purOfAdvDesc = " a.SICKNESS as purOfAdv, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '184'  and REF_CODE = a.SICKNESS " + effctiveQuery + "),'') as purOfAdvDesc , ";
            if (!(purOfAdv.equalsIgnoreCase("0") || purOfAdv.equalsIgnoreCase(""))) { //184 - Purpose of Advances 
                purOfAdvQuery = " a.SICKNESS in ('" + purOfAdv + "') and ";
                purOfAdvOrderBy = " purOfAdv, ";
            }

            String exposureDescJo = " bor.exposure, bor.exposureDesc , ";
            String exposureDescCov = " r.exposure, r.exposureDesc , ";
            String exposureDesc = " a.EXPOSURE as exposure, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '191'  and REF_CODE = a.EXPOSURE),'') as exposureDesc , ";
            if (!(exposure.equalsIgnoreCase("0") || exposure.equalsIgnoreCase(""))) { //191 - Exposure*
                exposureQuery = " a.EXPOSURE in ('" + exposure + "') and ";
                exposureOrderBy = " exposure, ";
            }

            String exposureCategoryDescJo = " bor.exposureCategory, bor.exposureCategoryDesc , ";
            String exposureCategoryDescCov = " r.exposureCategory, r.exposureCategoryDesc , ";
            String exposureCategoryDesc = " ifnull(a.EXP_CAT,'') as exposureCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '230'  and REF_CODE = a.EXP_CAT),'') as exposureCategoryDesc , ";
            if (!(exposureCategory.equalsIgnoreCase("0") || exposureCategory.equalsIgnoreCase(""))) { //230 - Exposure Categroy*
                exposureCategoryQuery = " a.EXP_CAT in ('" + exposureCategory + "') and ";
                exposureCategoryOrderBy = " exposureCategory, ";
            } else {
                if (!(exposure.equalsIgnoreCase("0") || exposure.equalsIgnoreCase(""))) {
                    exposureCategoryOrderBy = " exposureCategory, ";
                }
            }

            String exposureCategoryPurposeDescJo = " bor.exposureCategoryPurpose, bor.exposureCategoryPurposeDesc , ";
            String exposureCategoryPurposeDescCov = " r.exposureCategoryPurpose, r.exposureCategoryPurposeDesc , ";
            String exposureCategoryPurposeDesc = " ifnull(a.EXP_CAT_PUR,'') as exposureCategoryPurpose, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '231'  and REF_CODE = a.EXP_CAT_PUR),'') as exposureCategoryPurposeDesc , ";
            if (!(exposureCategoryPurpose.equalsIgnoreCase("0") || exposureCategoryPurpose.equalsIgnoreCase(""))) { //231 - Purpose of Exposure Category*
                exposureCategoryPurposeQuery = " a.EXP_CAT_PUR in ('" + exposureCategoryPurpose + "') and ";
                exposureCategoryPurposeOrderBy = " exposureCategoryPurpose, ";
            } else {
                if (!(exposure.equalsIgnoreCase("0") || exposure.equalsIgnoreCase(""))) {
                    if (!(exposureCategory.equalsIgnoreCase("0") || exposureCategory.equalsIgnoreCase(""))) {
                        exposureCategoryPurposeOrderBy = " exposureCategoryPurpose, ";
                    }
                }
            }

            String schemeCodeDescJo = " bor.schemeCode, bor.schemeCodeDesc , ";
            String schemeCodeDescCov = " r.schemeCode, r.schemeCodeDesc , ";
            String schemeCodeDesc = " a.SCHEME_CODE as schemeCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '203'  and REF_CODE = a.SCHEME_CODE),'') as schemeCodeDesc , ";
            if (!(schemeCode.equalsIgnoreCase("0") || schemeCode.equalsIgnoreCase("ALL") || schemeCode.equalsIgnoreCase(""))) { //203 - Scheme Code*
                schemeCodeQuery = " a.SCHEME_CODE in ('" + schemeCode + "') and ";
                schemeCodeOrderBy = " schemeCode, ";
            }

            String intTypeDescJo = " bor.intType, bor.intTypeDesc ,  ";
            String intTypeDescCov = " r.intType, r.intTypeDesc ,  ";
            String intTypeDesc = " a.INTEREST_TYPE as intType, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '202'  and REF_CODE = a.INTEREST_TYPE),'') as intTypeDesc ,  ";
            if (!(intType.equalsIgnoreCase("0") || intType.equalsIgnoreCase("ALL") || intType.equalsIgnoreCase(""))) { //202 - Interest Type*
                intTypeQuery = " a.INTEREST_TYPE in ('" + intType + "') and ";
                intTypeOrderBy = " intType, ";
            }

            String applicantCategoryDescJo = " bor.applicantCategory, bor.applicantCategoryDesc ,  ";
            String applicantCategoryDescCov = " r.applicantCategory, r.applicantCategoryDesc ,  ";
            String applicantCategoryDesc = " a.APPLICANT_CATEGORY as applicantCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '208'  and REF_CODE = a.APPLICANT_CATEGORY),'') as applicantCategoryDesc ,  ";
            if (!(applicantCategory.equalsIgnoreCase("0") || applicantCategory.equalsIgnoreCase(""))) { //208 - Applicant Category*
                applicantCategoryQuery = " a.APPLICANT_CATEGORY in ('" + applicantCategory + "') and ";
                applicantCategoryOrderBy = " applicantCategory, ";
            }

            String categoryOptDescJo = " bor.categoryOpt, bor.categoryOptDesc ,  ";
            String categoryOptDescCov = " r.categoryOpt, r.categoryOptDesc ,  ";
            String categoryOptDesc = " a.CATEGORY_OPT as categoryOpt, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '209'  and REF_CODE = a.CATEGORY_OPT),'') as categoryOptDesc ,  ";
            if (!(categoryOpt.equalsIgnoreCase("0") || categoryOpt.equalsIgnoreCase(""))) { //209 - Category Option
                categoryOptQuery = " a.CATEGORY_OPT in ('" + categoryOpt + "') and ";
                categoryOptOrderBy = " categoryOpt, ";
            }

            String minorCommunityDescJo = " bor.minorCommunity, bor.minorCommunityDesc ,  ";
            String minorCommunityDescCov = " r.minorCommunity, r.minorCommunityDesc ,  ";
            String minorCommunityDesc = " a.MINOR_COMMUNITY as minorCommunity, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '204'  and REF_CODE = a.MINOR_COMMUNITY),'') as minorCommunityDesc ,  ";
            if (!(minorCommunity.equalsIgnoreCase("0") || minorCommunity.equalsIgnoreCase(""))) { //204 - Minority Community
                minorCommunityQuery = " a.MINOR_COMMUNITY in ('" + minorCommunity + "') and ";
                minorCommunityOrderBy = " minorCommunity, ";
            }

            String relationDescJo = " bor.relation, bor.relationDesc ,  ";
            String relationDescCov = " r.relation, r.relationDesc ,  ";
            String relationDesc = " a.RELATION as relation, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = a.RELATION),'') as relationDesc ,  ";
            if (!(relation.equalsIgnoreCase("0") || relation.equalsIgnoreCase(""))) { //210 - Director/Staff
                relationQuery = " a.RELATION in ('" + relation + "') and ";
                relationOrderBy = " relation, ";
            }

            String relOwnerDescJo = " bor.relOwner, bor.relOwnerDesc ,  ";
            String relOwnerDescCov = " r.relOwner, r.relOwnerDesc ,  ";
            String relOwnerDesc = " ifnull(a.REL_WITH_AC_HOLDER,'') as relOwner, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '004'  and REF_CODE = a.REL_WITH_AC_HOLDER),'') as relOwnerDesc ,  ";
            if (!(relOwner.equalsIgnoreCase("0") || relOwner.equalsIgnoreCase(""))) { //004 - Relation
                relOwnerQuery = " a.REL_WITH_AC_HOLDER in ('" + relOwner + "') and ";
                relOwnerOrderBy = " relOwner, ";
            }

            String drawingPwrIndDescJo = " bor.drawingPwrInd, bor.drawingPwrIndDesc ,  ";
            String drawingPwrIndDescCov = " r.drawingPwrInd, r.drawingPwrIndDesc ,  ";
            String drawingPwrIndDesc = " a.DRAWING_POWER_INDICATOR  as drawingPwrInd, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '232'  and REF_CODE = a.DRAWING_POWER_INDICATOR),'') as drawingPwrIndDesc ,  ";
            if (!(drawingPwrInd.equalsIgnoreCase("0") || drawingPwrInd.equalsIgnoreCase(""))) { //232 - Drawing Power Indicator(DP)*
                drawingPwrIndQuery = " a.DRAWING_POWER_INDICATOR in ('" + drawingPwrInd + "') and ";
                drawingPwrIndOrderBy = " drawingPwrInd, ";
            }

            String popuGroupDescJo = " bor.popuGroup, bor.popuGroupDesc ,  ";
            String popuGroupDescCov = " r.popuGroup, r.popuGroupDesc ,  ";
            String popuGroupDesc = " a.POPULATION_GROUP as popuGroup, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '200'  and REF_CODE = a.POPULATION_GROUP),'') as popuGroupDesc ,  ";
            if (!(popuGroup.equalsIgnoreCase("0") || popuGroup.equalsIgnoreCase(""))) { //200 - Population Group*
                popuGroupQuery = " a.POPULATION_GROUP in ('" + popuGroup + "') and ";
                popuGroupOrderBy = " popuGroup, ";
            }

            String sanctionLevelDescJo = " bor.sanctionLevel, bor.sanctionLevelDesc ,  ";
            String sanctionLevelDescCov = " r.sanctionLevel, r.sanctionLevelDesc ,  ";
            String sanctionLevelDesc = " a.SANCTION_LEVEL as sanctionLevel, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '193'  and REF_CODE = a.SANCTION_LEVEL),'') as sanctionLevelDesc ,  ";
            if (!(sanctionLevel.equalsIgnoreCase("0") || sanctionLevel.equalsIgnoreCase(""))) { //193 - Sanction Level*
                sanctionLevelQuery = " a.SANCTION_LEVEL in ('" + sanctionLevel + "') and ";
                sanctionLevelOrderBy = " sanctionLevel, ";
            }

            String sanctionAuthDescJo = " bor.sanctionAuth, bor.sanctionAuthDesc ,  ";
            String sanctionAuthDescCov = " r.sanctionAuth, r.sanctionAuthDesc ,  ";
            String sanctionAuthDesc = " a.SANCTIONING_AUTHORITY as sanctionAuth, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '194'  and REF_CODE = a.SANCTIONING_AUTHORITY),'') as sanctionAuthDesc ,  ";
            if (!(sanctionAuth.equalsIgnoreCase("0") || sanctionAuth.equalsIgnoreCase(""))) { //194 - Sanctioning Authority*
                sanctionAuthQuery = " a.SANCTIONING_AUTHORITY in ('" + sanctionAuth + "') and ";
                sanctionAuthOrderBy = " sanctionAuth, ";
            }

            String courtsDescJo = " bor.courts, bor.courtsDesc , ";
            String courtsDescCov = " r.courts, r.courtsDesc , ";
            String courtsDesc = " a.COURTS  as courts, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '196'  and REF_CODE = a.COURTS ),'') as courtsDesc , ";
            if (!(courts.equalsIgnoreCase("0") || courts.equalsIgnoreCase(""))) { //196 - Courts
                courtsQuery = " a.COURTS  in ('" + courts + "') and ";
                courtsOrderBy = " courts, ";
            }

            String restructuringDescJo = " bor.restructuring, bor.restructuringDesc , ";
            String restructuringDescCov = " r.restructuring, r.restructuringDesc , ";
            String restructuringDesc = " a.RESTRUCTURING as restructuring, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '192'  and REF_CODE = a.RESTRUCTURING),'') as restructuringDesc , ";
            if (!(restructuring.equalsIgnoreCase("0") || restructuring.equalsIgnoreCase(""))) { //192 - restructuring
                restructuringQuery = " a.RESTRUCTURING in ('" + restructuring + "') and ";
                restructuringOrderBy = " restructuring, ";
            }

            String genderDescJo = " ,bor.gender, bor.genderDesc  ";
            String genderDescCov = " ,ifnull(cd.gender,'O') as gender, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '233'  and REF_CODE = ifnull(cd.gender,'O')),'') as genderDesc  ";
            //String genderDesc = " a.RESTRUCTURING as restructuring, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '192'  and REF_CODE = a.RESTRUCTURING),'') as restructuringDesc , ";
            if (!(gender.equalsIgnoreCase("0") || gender.equalsIgnoreCase(""))) { //233 - Gender
                genderQuery = " and cd.gender in ('" + gender + "') ";
                GenderOrderBy = " gender, ";
            }

            if (!(securityType.equalsIgnoreCase("0") || securityType.equalsIgnoreCase(""))) { //Dated; Non-Dated
                securityTypeQuery = " and ww.securityType in ('" + securityType + "') ";
            }

            if (!(securityNature.equalsIgnoreCase("0") || securityNature.equalsIgnoreCase(""))) { //P=Primary; C=Colletral
                securityNatureQuery = " and ww.security in ('" + securityNature + "') ";
            }

            if (!(securityDesc1.equalsIgnoreCase("0") || securityDesc1.equalsIgnoreCase(""))) {
                /*SECURED ADVANCES = SECURED ADVANCES, PLEDGE, REGISTERED MORTGAGE, SECOND CHARGE, ASSIGNMENT, HYPOTHECATION, LIEN
                 UNSECURED ADVANCES = UNSECURED ADVANCE, UNSECURED ADVANCES
                 BILL PURCHASED/DISCOUNTED = BILL PURCHASED/DISCOUNTED etc...*/
                securityDesc1Query = " and ww.securityChg in ('" + securityDesc1 + "') ";
            }

            if (!(securityDesc2.equalsIgnoreCase("0") || securityDesc2.equalsIgnoreCase(""))) { //FIXED AND OTHER DEPOSITS(SPECIFY) ;LIVE STOCKS etc
                securityDesc2Query = " and ww.SecurityOption in ('" + securityDesc2 + "') ";
            }


            String industryDescJo = " ,bor.industry, bor.industryDesc  ";
            String industryDescCov = " ,ifnull(r.industry,'0') as industry, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '445'  and REF_CODE = ifnull(r.industry,'O')),'') as industryDesc  ";
            if (!(industry.equalsIgnoreCase("ALL") || industry.equalsIgnoreCase("0"))) { //445 - Industry
                industryQuery = " and a.industry in ('" + industry + "') ";
                IndustryOrderBy = " industry, ";
            }
            String npaClassQuery = "", npaClassOrderBy = "";
            String npaClassDescJo = " ,bor.npaClass, bor.npaClassDesc ";
            String npaClassDescCov = " ,ifnull(r.NPA_CLASSIFICATION ,'0') as npaClass, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '195'  and REF_CODE = ifnull(r.npa_classification ,'0')),'') as npaClassDesc  ";
            if (!(npaClass.equalsIgnoreCase("0") || npaClass.equalsIgnoreCase("ALL"))) {
                npaClassQuery = " and a.NPA_CLASSIFICATION in ('" + npaClass + "') ";
                npaClassOrderBy = " npaClass , ";
            }

            String activeAcQuery = loanPeriod.equalsIgnoreCase("ALL") ? " " : loanPeriod.equalsIgnoreCase("Active") ? " and (ifnull(am.closingdate,'')='' or am.closingdate > '" + dt + "') " : " and am.accStatus = 9 ";

            String query = "select bor.acNo, bor.custName, bor.odLimit, bor.sanctionlimitdt, bor.loanDuration, "
                    + sectorDescJo + subSectorDescJo + modeOfAdvanceDescJo + securedDescJo + typeOfAdvanceDescJo + purposeOfAdvanceDescJo
                    + guarnteeCoverDescJo + purOfAdvDescJo + exposureDescJo + exposureCategoryDescJo + exposureCategoryPurposeDescJo + schemeCodeDescJo
                    + intTypeDescJo + applicantCategoryDescJo + categoryOptDescJo + minorCommunityDescJo + relationDescJo + relOwnerDescJo
                    + drawingPwrIndDescJo + popuGroupDescJo + sanctionLevelDescJo + sanctionAuthDescJo + courtsDescJo + restructuringDescJo + "bor.netWorth, /*r.balance*/  "
                    + "bor.acNature, bor.acType, bor.brnCode, bor.cid, bor.acTypeDesc "
                    + genderDescJo + ", bor.AMTDISBURSED, bor.mobilenumber, bor.openingdt, bor.updateDt, bor.version " + industryDescJo + " " + npaClassDescJo + " "
                    + " , bor.DISBURSEMENTDT,bor.dob , bor.fatherName ,bor.pan ,bor.address ,bor.email,bor.status,bor.renewDt,"
                    + " bor.income,bor.expDt,bor.TURN_OVER_DETAIL_FLAG,bor.dir_cust_id,"
                    + "ifnull((select CustFullName from cbs_customer_master_detail where customerid = bor.dir_cust_id),'') as directorName, "
                    + "bor.joinDetails,bor.SecurityOption, bor.securityDetails, bor.secAmt, bor.security   "
                    + " from "
                    + "(select r.acNo, r.custName, r.odLimit, r.sanctionlimitdt, r.loanDuration, "
                    + sectorDescCov + subSectorDescCov + modeOfAdvanceDescCov + securedDescCov + typeOfAdvanceDescCov + purposeOfAdvanceDescCov
                    + guarnteeCoverDescCov + purOfAdvDescCov + exposureDescCov + exposureCategoryDescCov + exposureCategoryPurposeDescCov + schemeCodeDescCov
                    + intTypeDescCov + applicantCategoryDescCov + categoryOptDescCov + minorCommunityDescCov + relationDescCov + relOwnerDescCov
                    + drawingPwrIndDescCov + popuGroupDescCov + sanctionLevelDescCov + sanctionAuthDescCov + courtsDescCov + restructuringDescCov + "r.netWorth, /*r.balance*/  "
                    + "r.acNature, r.acType, r.brnCode, r.cid, r.acTypeDesc "
                    + genderDescCov + ", sum(ifnull(ld.AMTDISBURSED,0)) as AMTDISBURSED, ifnull(cd.mobilenumber,'') as mobilenumber, r.openingdt, r.updateDt, r.version " + industryDescCov + " " + npaClassDescCov + " "
                    + " , ifnull(ld.DISBURSEMENTDT,date_format(openingdt,'%Y-%m-%d 00:00:00')) as DISBURSEMENTDT,ifnull(date_format(cd.DateOfBirth,'%Y-%m-%d'),openingdt) as dob , "
                    + "concat(ifnull(fathername,''),' ' ,ifnull(FatherMiddleName,'') ,' ' ,ifnull(FatherLastName,'') ) as fatherName, "
                    + "ifnull(cd.PAN_GIRNumber,'') as pan ,ifnull(cd.PerAddressLine1,'') as add1,concat(ifnull(PerAddressLine1,''),' ' ,ifnull(peraddressline2,'') ,' ' ,ifnull(PerVillage,''),' ' ,ifnull(PerBlock,''),' ' ,ifnull(PerDistrict,''),' ' ,ifnull(PerPostalCode,'') ) as address,"
                    + "ifnull(cd.mail_email,'') as email, r.PresentStatus as status, r.renewDt as renewDt, r.income as income, r.expDt as expDt, "
                    + "ifnull(r.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG,r.dir_cust_id as dir_cust_id, jj.joinDetails,"
                    + "ww.SecurityOption, ww.securityDetails, ww.secAmt, ww.security "
                    + " from ( "
                    + "select am.acno  as acNo, am.custName, ifnull(la.ODLimit,0) as odLimit, ifnull(la.Sanctionlimitdt, date_format(am.OpeningDt,'%Y-%m-%d')) as sanctionlimitdt, ifnull(a.loan_duration,0) as loanDuration, "
                    + sectorDesc + subSectorDesc + modeOfAdvanceDesc + securedDesc + typeOfAdvanceDesc + purposeOfAdvanceDesc
                    + guarnteeCoverDesc + purOfAdvDesc + exposureDesc + exposureCategoryDesc + exposureCategoryPurposeDesc + schemeCodeDesc
                    + intTypeDesc + applicantCategoryDesc + categoryOptDesc + minorCommunityDesc + relationDesc + relOwnerDesc
                    + drawingPwrIndDesc + popuGroupDesc + sanctionLevelDesc + sanctionAuthDesc + courtsDesc + restructuringDesc
                    + " a.net_worth as netWorth, /*fnull(sum(ifnull(tn.cramt,0)),0)-ifnull(sum(ifnull(tn.dramt,0)),0) as balance*/  "
                    + " atm.acctNature as acNature, atm.AcctCode as acType, substring(am.acno,1,2) as brnCode, ci.custid as cid, atm.AcctDesc as acTypeDesc, "
                    + " am.openingdt as openingdt, ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE) as updateDt, a.TOTAL_MODIFICATIONS as version , a.industry  ,"
                    + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '445'  and REF_CODE = ifnull(a.industry,'0')),'') as industryDesc "
                    + " ,a.NPA_CLASSIFICATION ,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '195'  and REF_CODE = ifnull(a.NPA_CLASSIFICATION,'0')),'') as npaClassDesc,la.PresentStatus,ifnull(a.RENEWAL_DATE,date_format(openingdt,'%Y-%m-%d 00:00:00')) as renewDt,ifnull(a.MONTHLY_INCOME,0) as income,ifnull(a.DOCUMENT_EXP_DATE,date_format(openingdt,'%Y-%m-%d 00:00:00')) as expDt,ifnull(sg.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG ,ifnull(a.dir_cust_id,'') as dir_cust_id"
                    + " from cbs_loan_borrower_details a , accountmaster am, loan_appparameter la, accounttypemaster atm "
                    + "/*, " + common.getTableName(accType) + " tn*/ "
                    + ", customerid ci, cbs_scheme_general_scheme_parameter_master sg where  sg.scheme_code= a.scheme_code and "
                    + branchCodeQuery + accTypeQuery + sectorQuery + subSectorQuery + modeOfAdvanceQuery
                    + securedQuery + typeOfAdvanceQuery + purposeOfAdvanceQuery + guarnteeCoverQuery + purOfAdvQuery + exposureQuery + exposureCategoryQuery
                    + exposureCategoryPurposeQuery + schemeCodeQuery + intTypeQuery + applicantCategoryQuery + categoryOptQuery + minorCommunityQuery
                    + relationQuery + relOwnerQuery + drawingPwrIndQuery + popuGroupQuery + sanctionLevelQuery + sanctionAuthQuery + courtsQuery + restructuringQuery
                    + reportBasedOnQuery + reportBaseQuery
                    + " substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno and a.acc_no = la.acno  and ci.Acno = am.ACNo /*and am.acno = tn.acno*/ " + industryQuery + " " + npaClassQuery + " "
                    + " and am.openingdt <= '" + dt + "' /*and tn.dt<='" + dt + "' */" + activeAcQuery
                    + " union all "
                    + " select am.acno  as acNo, am.custName, ifnull(la.ODLimit,0) as odLimit, ifnull(la.Sanctionlimitdt, date_format(am.OpeningDt,'%Y-%m-%d')) as sanctionlimitdt, ifnull(a.loan_duration,0) as loanDuration, "
                    + sectorDesc + subSectorDesc + modeOfAdvanceDesc + securedDesc + typeOfAdvanceDesc + purposeOfAdvanceDesc
                    + guarnteeCoverDesc + purOfAdvDesc + exposureDesc + exposureCategoryDesc + exposureCategoryPurposeDesc + schemeCodeDesc
                    + intTypeDesc + applicantCategoryDesc + categoryOptDesc + minorCommunityDesc + relationDesc + relOwnerDesc
                    + drawingPwrIndDesc + popuGroupDesc + sanctionLevelDesc + sanctionAuthDesc + courtsDesc + restructuringDesc
                    + " a.net_worth as netWorth, /*fnull(sum(ifnull(tn.cramt,0)),0)-ifnull(sum(ifnull(tn.dramt,0)),0) as balance*/  "
                    + " atm.acctNature as acNature, atm.AcctCode as acType, substring(am.acno,1,2) as brnCode, ci.custid as cid, atm.AcctDesc as acTypeDesc, "
                    + " am.openingdt as openingdt, ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE) as updateDt, a.TOTAL_MODIFICATIONS as version , a.industry  ,"
                    + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '445'  and REF_CODE = ifnull(a.industry,'0')),'') as industryDesc "
                    + " ,a.NPA_CLASSIFICATION ,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '195'  and REF_CODE = ifnull(a.NPA_CLASSIFICATION,'0')),'') as npaClassDesc,"
                    + " la.PresentStatus,ifnull(a.RENEWAL_DATE,date_format(openingdt,'%Y-%m-%d 00:00:00')) as renewDt,ifnull(a.MONTHLY_INCOME,0) as income ,"
                    + " ifnull(a.DOCUMENT_EXP_DATE,date_format(openingdt,'%Y-%m-%d 00:00:00')) as expDt,"
                    + " ifnull(sg.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG,ifnull(a.dir_cust_id,'') as dir_cust_id "
                    + " from cbs_loan_borrower_details_history a , accountmaster am, loan_appparameter la, accounttypemaster atm "
                    + "/*, " + common.getTableName(accType) + " tn*/ "
                    + ", customerid ci, cbs_scheme_general_scheme_parameter_master sg where  sg.scheme_code= a.scheme_code and "
                    + branchCodeQuery + accTypeQuery + sectorQuery + subSectorQuery + modeOfAdvanceQuery
                    + securedQuery + typeOfAdvanceQuery + purposeOfAdvanceQuery + guarnteeCoverQuery + purOfAdvQuery + exposureQuery + exposureCategoryQuery
                    + exposureCategoryPurposeQuery + schemeCodeQuery + intTypeQuery + applicantCategoryQuery + categoryOptQuery + minorCommunityQuery
                    + relationQuery + relOwnerQuery + drawingPwrIndQuery + popuGroupQuery + sanctionLevelQuery + sanctionAuthQuery + courtsQuery + restructuringQuery
                    + reportBasedOnQuery + reportBaseQuery
                    + " substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno and a.acc_no = la.acno  and ci.Acno = am.ACNo /*and am.acno = tn.acno*/ " + industryQuery + " " + npaClassQuery + " "
                    + " and am.openingdt <= '" + dt + "' /*and tn.dt<='" + dt + "' */" + activeAcQuery
                    + ") r "
                    + " left join loandisbursement ld on r.acNo = ld.ACNO and ld.DISBURSEMENTDT <='" + dt + "' "
                    + " left join "
                    + " (select yy.acno, yy.minSno, yy.noOfSec,  "
                    + " if(atm.acctcode<>'null','FIXED AND OTHER DEPOSITS(SPECIFY)',xx.SecurityOption) as SecurityOption,"
                    + " vv.securityDetails, vv.secAmt, vv.securityNature, vv.SecurityType,vv.securityChg,vv.security, atm.acctcode  from ( "
                    + " select zz.acno, min(zz.sno) as minSno, count(*) as noOfSec from  "
                    + " (select acno,  min(sno) as sno, count(*) from loansecurity  "
                    + " where  (ExpiryDate is null or ExpiryDate ='' or ExpiryDate >'" + dt + "') and security = 'P' "
                    + " and Entrydate<='" + dt + "' group by acno, sno order by acno, sno) zz group by zz.acno)yy  "
                    + " left join  "
                    + " loansecurity xx on yy.acno = xx.acno and yy.minSno = xx.sno "
                    + "  left join "
                    + " accounttypemaster atm on atm.acctcode = xx.SecurityOption"
                    + " left join  "
                    + " (select zz.acno, group_concat(zz.securityDesc) as securityDetails, sum(zz.secAmt) as secAmt, "
                    + " zz.securityNature, zz.SecurityType, zz.securityChg, zz.security from  "
                    + " (select acno, ifnull(Security,'C') as securityNature, "
                    + " ifnull(SecurityType,SUBSTRING_INDEX(remarks, ':', 1)) as securityType, "
                    + " if((securityChg = 'PLEDGE' OR securityChg = 'REGISTERED MORTGAGE' OR securityChg = 'SECOND CHARGE' "
                    + " OR securityChg = 'ASSIGNMENT' OR securityChg = 'HYPOTHECATION' OR securityChg = 'LIEN'),"
                    + " 'SECURED ADVANCES',"
                    + " if(securityChg = 'UNSECURED ADVANCE', 'UNSECURED ADVANCES', securityChg))as securityChg, security, "
                    + " concat(if(atm.acctcode<>'null','FIXED AND OTHER DEPOSITS(SPECIFY)',SecurityOption), ': Rs.',(cast(sum(matvalue) as decimal(25,2)))) as securityDesc,  "
                    + " (cast(sum(matvalue) as decimal(25,2))) as secAmt, atm.acctcode from loansecurity a "
                    + " left join "
                    + " accounttypemaster atm on atm.acctcode = a.SecurityOption  "
                    + " where  (ExpiryDate is null or ExpiryDate ='' or ExpiryDate >'" + dt + "')  "
                    + " and Entrydate<='" + dt + "' group by acno, SecurityOption ) zz group by zz.acno)vv on yy.acno = vv.acno) ww "
                    + " on ww.acno = r.acNo "
                    + " left join (select a.acno, a.custname, "
                    + " CONCAT(ifnull(if((a.custid1<>null or a.custid1<>''),CONCAT('[',a.custid1,'] ',aa.CustFullName,''),''),''), "
                    + " ifnull(if((a.custid2<>null or a.custid2<>''),CONCAT('[',a.custid2,'] ',bb.CustFullName,' '),''),''), "
                    + " ifnull(if((a.custid3<>null or a.custid3<>''),CONCAT('[',a.custid3,'] ',cc.CustFullName,' '),''),''), "
                    + " ifnull(if((a.custid4<>null or a.custid4<>''),CONCAT('[',a.custid4,'] ',dd.CustFullName,' '),''),'')) as joinDetails"
                    + " from accountmaster a "
                    + " left join cbs_customer_master_detail aa on a.custid1 = aa.customerid "
                    + " left join cbs_customer_master_detail bb on a.custid2 = bb.customerid "
                    + " left join cbs_customer_master_detail cc on a.custid3 = cc.customerid "
                    + " left join cbs_customer_master_detail dd on a.custid4 = dd.customerid "
                    + " where (a.custid1<> null or a.custid1<>'') or (a.custid2<> null or a.custid2<>'') "
                    + " or (a.custid3<> null or a.custid3<>'') or (a.custid4<> null or a.custid4<>'')) jj on r.acNo = jj.acno , "
                    + " cbs_customer_master_detail cd where r.cid = cd.customerid "
                    + genderQuery + securityTypeQuery + securityNatureQuery + securityDesc1Query + securityDesc2Query
                    + " group by r.acno, r.updateDt, r.version ) bor, "
                    + " (select mo.acno, max(mo.modi) as modifi from "
                    + " (select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details_history g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + dt + "' group by  g.ACC_NO "
                    + " union all "
                    + " select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + dt + "' group by  g.ACC_NO) mo group by mo.acno) ver "
                    + " where bor.acno = ver.acno and bor.version = ver.modifi"
                    + " order by " + sectorOrderBy + subSectorOrderBy + modeOfAdvanceOrderBy + securedOrderBy + typeOfAdvanceOrderBy
                    + purposeOfAdvanceOrderBy + guarnteeCoverOrderBy + purOfAdvOrderBy + exposureOrderBy
                    + exposureCategoryOrderBy + exposureCategoryPurposeOrderBy + schemeCodeOrderBy + intTypeOrderBy
                    + applicantCategoryOrderBy + categoryOptOrderBy + minorCommunityOrderBy + relationOrderBy
                    + relOwnerOrderBy + drawingPwrIndOrderBy + popuGroupOrderBy + sanctionLevelOrderBy + sanctionAuthOrderBy
                    + restructuringOrderBy + GenderOrderBy + courtsOrderBy + brnCodeOrderBy + acNatureOrderBy + accTypeOrderBy + IndustryOrderBy + npaClassOrderBy
                    + " acNo ";

            //frmDt		from	to	
            //System.out.println("Query>>>" + query);
            List queryList = em.createNativeQuery(query).getResultList();
            if (queryList.size() > 0) {
                for (int i = 0; i < queryList.size(); i++) {
                    String acNo, acctNature, sanctionDt = "";
                    BigDecimal ovrdueAmt = new BigDecimal(0), outStanding = new BigDecimal(0), odLimit = new BigDecimal(0), BigDecimal,
                            roi = new BigDecimal(0);
                    String loanDuration = "0";
                    Vector queryVect = (Vector) queryList.get(i);
                    LoanMisCellaniousPojo pojo = new LoanMisCellaniousPojo();
                    acNo = queryVect.get(0).toString();
                    openingDt = dmyFormat.format(ymdFormat.parse(queryVect.get(63).toString()));
                    String intCalcBasedOnSecurity = queryVect.get(80).toString();
                    if (parameter.equalsIgnoreCase("Y")) {
                        /*This is for ROI and Sanction Amount both*/
//                        if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {// ROI as per Security Wise For Khattri.
//                            List roiList = em.createNativeQuery("select  aa.SecurityRoi, aa.MargineROI, aa.AppRoi, aa.IntTableCode, aa.addRoi, lienvalue from "
//                                    + "(select  ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(addRoi,0) as addRoi, lienvalue from loansecurity where acno = '" + acNo + "' and status = 'Active'  and IntTableCode is not null and entrydate <= '" + dt + "' and IntTableCode <>''"
//                                    + " union all "
//                                    + " select  ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(addRoi,0) as addRoi, lienvalue from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and IntTableCode is not null and entrydate <= '" + dt + "' and ExpiryDate > '" + dt + "' and IntTableCode <>'') aa order by aa.AppRoi").getResultList();
//                            if (!roiList.isEmpty()) {
//                                Vector roiVector = (Vector) roiList.get(0);
//                                double securityRoi = Double.parseDouble(roiVector.get(0).toString());
//                                double additionalRoi = Double.parseDouble(roiVector.get(4).toString());
//                                double lienValue = Double.parseDouble(roiVector.get(5).toString());
//                                String intTableCode1 = roiVector.get(3).toString();
//                                double roi1 = securityRoi + additionalRoi + Double.parseDouble(loanInterestCalculationBean.getROI(intTableCode1, lienValue, dt));
//                                roi = new BigDecimal(roi1);
//                            }
//                        } else {
                        roi = new BigDecimal(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(queryVect.get(2).toString()), dt, acNo));
//                        }
//                        acctNature = common.getAcNatureByAcType(common.getAcTypeByAcNo(acNo));
                        List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                + "where acno =  '" + acNo + "' and txnid = "
                                + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + dt + "' )").getResultList();
                        if (!sanctionLimitDtList.isEmpty()) {
                            Vector vist = (Vector) sanctionLimitDtList.get(0);
                            sanctionDt = dmyFormat.format(y_m_dFormat.parse(vist.get(0).toString()));
                            odLimit = new BigDecimal(vist.get(1).toString());
                            pojo.setSanctionDt((openingDt.equalsIgnoreCase("") ? "--\n(".concat(sanctionDt).concat(")") : openingDt.concat("\n(").concat(sanctionDt).concat(")")));
                        } else {
                            sanctionDt = queryVect.get(3).toString().equalsIgnoreCase("") ? "" : dmyFormat.format(y_m_dFormat.parse(queryVect.get(3).toString()));
                            odLimit = new BigDecimal(queryVect.get(2).toString());
                            pojo.setSanctionDt((openingDt.equalsIgnoreCase("") ? "--\n(".concat(sanctionDt).concat(")") : openingDt.concat("\n(").concat(sanctionDt).concat(")")));

                        }
                        pojo.setBrnDesc(common.getBranchNameByBrncode(queryVect.get(56).toString()));
//                        pojo.setAcTypeDesc(common.getAcctDecription(common.getAcTypeByAcNo(acNo)));
                        pojo.setRoi(roi);
                    } else if (parameter.equalsIgnoreCase("S")) {
                        /*This is for only Sanction Amount*/
                        List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                + "where acno =  '" + acNo + "' and txnid = "
                                + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + dt + "' )").getResultList();
                        if (!sanctionLimitDtList.isEmpty()) {
                            Vector vist = (Vector) sanctionLimitDtList.get(0);
                            sanctionDt = dmyFormat.format(y_m_dFormat.parse(vist.get(0).toString()));
                            odLimit = new BigDecimal(vist.get(1).toString());
                            pojo.setSanctionDt((openingDt.equalsIgnoreCase("") ? "--\n(".concat(sanctionDt).concat(")") : openingDt.concat("\n(").concat(sanctionDt).concat(")")));
                        } else {
                            sanctionDt = queryVect.get(3).toString().equalsIgnoreCase("") ? "" : dmyFormat.format(y_m_dFormat.parse(queryVect.get(3).toString()));
                            odLimit = new BigDecimal(queryVect.get(2).toString());
                            pojo.setSanctionDt((openingDt.equalsIgnoreCase("") ? "--\n(".concat(sanctionDt).concat(")") : openingDt.concat("\n(").concat(sanctionDt).concat(")")));
                        }
                        pojo.setBrnDesc(common.getBranchNameByBrncode(queryVect.get(56).toString()));
//                        pojo.setAcTypeDesc(common.getAcctDecription(common.getAcTypeByAcNo(acNo)));
                        pojo.setRoi(roi);
                    }

                    pojo.setBrnCode(queryVect.get(56).toString());
                    pojo.setAcNature(queryVect.get(54).toString());
                    pojo.setAcType(queryVect.get(55).toString());
                    pojo.setAcTypeDesc(queryVect.get(58).toString());
                    pojo.setGender(queryVect.get(59).toString());
                    pojo.setGenderDesc(queryVect.get(60).toString());
                    pojo.setAcNo(acNo);
                    pojo.setCustName(queryVect.get(1).toString().concat("\n").concat(queryVect.get(62).toString()));
                    if (relation.equalsIgnoreCase("DIR") || relation.contains("RREL") || relation.equalsIgnoreCase("SECREL") || relation.equalsIgnoreCase("SEC") || relation.equalsIgnoreCase("MGR")) {
                        if (queryVect.get(39).toString().equalsIgnoreCase("Self")) {
                            pojo.setCustName(queryVect.get(1).toString().concat("\n").concat(queryVect.get(62).toString()).concat(" (").concat(queryVect.get(38).toString()).concat(")"));
                        } else {
                            pojo.setCustName(queryVect.get(1).toString().concat("\n").concat(queryVect.get(62).toString()).concat(" (").concat(queryVect.get(38).toString()).concat(" (").concat(queryVect.get(40).toString()).concat(" ) ").concat(" of ").concat(queryVect.get(82).toString()).concat(" )"));
                        }
                    }
                    pojo.setSanctionAmt(odLimit);
//                    List tempList = em.createNativeQuery("select ifnull(sum(amtdisbursed),0) from loandisbursement where acno='" + acNo + "'").getResultList();
//                    if (!tempList.isEmpty()) {
//                        tempVector = (Vector) tempList.get(0);
                    pojo.setDisbAmount(new BigDecimal(queryVect.get(61).toString()));
//                    }
                    /*
                     **OutStanding Handling pending
                     */
                    outStanding = new BigDecimal(common.getBalanceOnDate(acNo, dt));

//                    pojo.setOutstanding(new BigDecimal(tempVector.get(65).toString()).abs());
//                    if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//                        pojo.setCrDrFlag(outStanding.compareTo(new BigDecimal(0)) >=0?"Cr":"Dr");
//                    } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) ) {
//                        pojo.setCrDrFlag(outStanding.compareTo(new BigDecimal(0)) >=0?"Cr":"Dr");
//                    }
                    pojo.setNetWorth(new BigDecimal(queryVect.get(53).toString()));
                    pojo.setOdLimit(odLimit);

//                    List acNoList1 = em.createNativeQuery("select distinct (acno) from emidetails where status = 'unpaid' and acno ='" + queryVect.get(0).toString() + "'").getResultList();
//                    if (acNoList1.size() == 0) {
//                        List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("A/Cs Whose Plan Has Finished", queryVect.get(0).toString(), 1, 200, dt, common.getBrncodeByAcno(acNo));
//                        for (OverdueEmiReportPojo overPojo : overdueEmiList) {
//                            ovrdueAmt = overPojo.getAmountOverdue();
//                        }
//                    } else {
//                        List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("", acNo, 1, 200, dt, common.getBrncodeByAcno(acNo));
//                        for (OverdueEmiReportPojo overPojo : overdueEmiList) {
//                            ovrdueAmt = overPojo.getAmountOverdue();
//                        }
//                    }
                    pojo.setOverdueAmt(ovrdueAmt);
                    if (queryVect.get(4).toString().trim().equalsIgnoreCase("")) {
                        List list = aitr.getIntCodeIntTypeSchmCode(acNo);
                        if (!list.isEmpty()) {
                            Vector vec = (Vector) list.get(0);
                            loanDuration = vec.get(3).toString();
                        }
                    }
                    pojo.setDuration(Integer.parseInt(queryVect.get(4).toString().trim().equalsIgnoreCase("") ? loanDuration : queryVect.get(4).toString().trim()));
                    pojo.setSector(queryVect.get(5).toString());
                    pojo.setSectorDesc(queryVect.get(6).toString());
                    pojo.setSubSector(queryVect.get(7).toString());
                    pojo.setSubSectorDesc(queryVect.get(8).toString());
                    pojo.setModeOfAdvance(queryVect.get(9).toString());
                    pojo.setModeOfAdvanceDesc(queryVect.get(10).toString());
                    pojo.setSecured(queryVect.get(11).toString());
                    pojo.setSecuredDesc(queryVect.get(12).toString());
                    pojo.setTypeOfAdvance(queryVect.get(13).toString());
                    pojo.setTypeOfAdvanceDesc(queryVect.get(14).toString());
                    pojo.setPurOfAdv(queryVect.get(15).toString());
                    pojo.setPurOfAdvDesc(queryVect.get(16).toString());
                    pojo.setGuarnteeCover(queryVect.get(17).toString());
                    pojo.setGuarnteeCoverDesc(queryVect.get(18).toString());
                    pojo.setPurposeOfAdvance(queryVect.get(19).toString());
                    pojo.setPurposeOfAdvanceDesc(queryVect.get(20).toString());
                    pojo.setExposure(queryVect.get(21).toString());
                    pojo.setExposureDesc(queryVect.get(22).toString());
                    pojo.setExposureCategory(queryVect.get(23).toString());
                    pojo.setExposureCategoryDesc(queryVect.get(24).toString());
                    pojo.setExposureCategoryPurpose(queryVect.get(25).toString());
                    pojo.setExposureCategoryPurposeDesc(queryVect.get(26).toString());
                    pojo.setSchemeCode(queryVect.get(27).toString());
                    pojo.setSchemeCodeDesc(queryVect.get(28).toString());
//                    pojo.setIntTable(queryVect.get(29).toString());
//                    pojo.setIntTableDesc(queryVect.get(30).toString());
                    pojo.setIntType(queryVect.get(29).toString());
                    pojo.setIntTypeDesc(queryVect.get(30).toString());
                    pojo.setApplicantCategory(queryVect.get(31).toString());
                    pojo.setApplicantCategoryDesc(queryVect.get(32).toString());
                    pojo.setCategoryOpt(queryVect.get(33).toString());
                    pojo.setCategoryOptDesc(queryVect.get(34).toString());
                    pojo.setMinorCommunity(queryVect.get(35).toString());
                    pojo.setMinorCommunityDesc(queryVect.get(36).toString());
                    pojo.setRelation(queryVect.get(37).toString());
                    pojo.setRelationDesc(queryVect.get(38).toString());
                    pojo.setRelOwner(queryVect.get(39).toString());
                    pojo.setRelOwnerDesc(queryVect.get(40).toString());
                    pojo.setDrawingPwrInd(queryVect.get(41).toString());
                    pojo.setDrawingPwrIndDesc(queryVect.get(42).toString());
                    pojo.setPopuGroup(queryVect.get(43).toString());
                    pojo.setPopuGroupDesc(queryVect.get(44).toString());
                    pojo.setSanctionLevel(queryVect.get(45).toString());
                    pojo.setSanctionLevelDesc(queryVect.get(46).toString());
                    pojo.setSanctionAuth(queryVect.get(47).toString());
                    pojo.setSanctionAuthDesc(queryVect.get(48).toString());
//                    pojo.setNpaClass(queryVect.get(51).toString());
//                    pojo.setNpaClassDesc(queryVect.get(52).toString());
//                    pojo.setAssetClassReason(queryVect.get(53).toString());
//                    pojo.setAssetClassReasonDesc(queryVect.get(54).toString());
                    pojo.setCourts(queryVect.get(49).toString());
                    pojo.setCourtsDesc(queryVect.get(50).toString());
//                    pojo.setModeOfSettle(queryVect.get(53).toString());
//                    pojo.setModeOfSettleDesc(queryVect.get(58).toString());
//                    pojo.setDebtWaiverReason(queryVect.get(59).toString());
//                    pojo.setDebtWaiverReasonDesc(queryVect.get(60).toString());
                    pojo.setRestructuring(queryVect.get(51).toString());
                    pojo.setRestructuringDesc(queryVect.get(52).toString());
                    pojo.setIndustry(queryVect.get(66).toString());
                    pojo.setIndustryDesc(queryVect.get(67).toString());
                    BigDecimal lienValue = new BigDecimal("0");
                    BigDecimal matValue = new BigDecimal("0");
                    String natureOfSec = "", security = "", subq = "";
                    String matDt = "";
                    if (withSecurity.equalsIgnoreCase("Y")) {
                        List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(SecurityType,''),ifnull(Particulars,''),ifnull(Remarks,'') from loansecurity where acno ='" + acNo + "' and (status ='ACTIVE' or (status ='Expired' and '" + dt + "'< ExpiryDate  )) and Entrydate < '" + dt + "'").getResultList();
                        for (int l = 0; l < loanSecurity.size(); l++) {
                            Vector vect = (Vector) loanSecurity.get(l);
                            lienValue = lienValue.add(new BigDecimal(vect.get(2).toString()));
                            natureOfSec = natureOfSec.concat(subq).concat(vect.get(3).toString());
                            matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                            if (!vect.get(3).toString().equalsIgnoreCase("NON-DATED")) {
                                matDt = matDt.concat(subq).concat(vect.get(1).toString().equalsIgnoreCase("") ? "" : dmyFormat.format(y_m_dFormat.parse(vect.get(1).toString())));
                            }
                            security = security.concat(subq).concat(vect.get(4).toString()).concat(" ").concat(vect.get(5).toString());
                            subq = "\n";
                        }
                        // New Code for Rbi Inspection Report
                        if (!mapRbiInspection.isEmpty()) {
                            if (mapRbiInspection.containsKey(queryVect.get(57).toString())) {
                                // mapRbiInspection.get(acNo).getCustId();
                                pojo.setCustId(mapRbiInspection.get(queryVect.get(57).toString()).getCustId());
                                pojo.setFolioNo(mapRbiInspection.get(queryVect.get(57).toString()).getFolioNo());
                                pojo.setShareBal(mapRbiInspection.get(queryVect.get(57).toString()).getShareBal());
                                pojo.setThriftBal(mapRbiInspection.get(queryVect.get(57).toString()).getThriftBal());
                                pojo.setMembershipDate(mapRbiInspection.get(queryVect.get(57).toString()).getMembershipDate());
                            }
                        }
//                        for (int y = 0; y < overDueDetails.size(); y++) {
//                            if (acNo.equalsIgnoreCase(overDueDetails.get(y).getAccountNumber())) {
//                                pojo.setEmiAmt(overDueDetails.get(y).getInstallmentamt());
//                                break;
//                            }
//                        }

                        List emiList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where acno = '" + acNo + "' and sno = (select max(sno) from emidetails where acno = '" + acNo + "' and duedt <='" + dt + "')").getResultList();
                        if (!emiList.isEmpty()) {
                            Vector emiVector = (Vector) emiList.get(0);
                            pojo.setEmiAmt(new BigDecimal(emiVector.get(0).toString()));
                        }

                        // End New Code for Rbi Inspection Report
                    }
                    pojo.setSecurity(queryVect.get(84) == null ? "" : queryVect.get(84).toString());//security
                    pojo.setSecurityDesc(queryVect.get(85) == null ? "" : queryVect.get(85).toString());//natureOfSec
                    pojo.setSecurityValue(queryVect.get(86) == null ? matValue : new BigDecimal(queryVect.get(86).toString()));//matValue
                    pojo.setSecurityExpiry(matDt);
                    pojo.setDisbAmt(new BigDecimal(queryVect.get(61).toString()));
                    pojo.setMobile(queryVect.get(62).toString());
                    pojo.setSchemeCodeDesc(queryVect.get(28).toString());
                    pojo.setApplicantCategoryDesc(queryVect.get(32).toString());
                    String insurance = "", insExpDt = "";
                    BigDecimal insuredAmt = new BigDecimal("0");
                    if (withInsurance.equalsIgnoreCase("Y")) {
                        List insuranceList = em.createNativeQuery("select ifnull(particulars,''),ifnull(assetval,0),ifnull(todt,''),ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no ='053' and ref_code =InsComName),'')"
                                + "  from loan_insurance where acno ='" + acNo + "'  and dt<='" + dt + "' and (status='ACTIVE' or (status ='EXPIRED' and '" + dt + "' between fromdt and todt))").getResultList();
                        if (!insuranceList.isEmpty()) {
                            for (int l = 0; l < insuranceList.size(); l++) {
                                Vector vect = (Vector) insuranceList.get(l);
                                insurance = insurance.concat("\n").concat(vect.get(0).toString()).concat(" from Company").concat(vect.get(3).toString());
                                insuredAmt = insuredAmt.add(new BigDecimal(vect.get(1).toString()));
                                insExpDt = insExpDt.concat("\n").concat(vect.get(2).toString().equalsIgnoreCase("") ? "" : dmyFormat.format(y_m_dFormat.parse(vect.get(2).toString())));
                            }
                        }
                    }
                    pojo.setInsurance(insurance);
                    pojo.setInsuranceAmt(insuredAmt);
                    pojo.setInsuranceExpiry(insExpDt);
                    pojo.setDisbDt(queryVect.get(70) == null ? "" : (queryVect.get(70).toString() == "" ? "" : dmyFormat.format(y_m_dFormat.parse(queryVect.get(70).toString()))));
                    pojo.setDob(queryVect.get(71).toString());
                    pojo.setFatherName(queryVect.get(72).toString());
                    pojo.setPanNo(queryVect.get(73).toString());
                    pojo.setAddress(queryVect.get(74).toString());
                    pojo.setEmail(queryVect.get(75).toString());
                    pojo.setStatus(queryVect.get(76) == null ? "OPERATIVE" : queryVect.get(76).toString());
                    pojo.setSrNo(String.valueOf(i + 1));
                    String disburse = "", subqueryDisb = "";
                    if (withSecurity.equalsIgnoreCase("Y")) {
                        List disbList = em.createNativeQuery("select distinct dt from " + common.getTableName(common.getAcNatureByAcNo(acNo)) + " where acno ='" + acNo + "' and trandesc ='6' and dt <='" + dt + "'").getResultList();
                        if (!disbList.isEmpty()) {
                            for (int l = 0; l < disbList.size(); l++) {
                                Vector vect = (Vector) disbList.get(l);
                                disburse = disburse.concat(subqueryDisb).concat(vect.get(0).toString().equalsIgnoreCase("") ? "" : dmyFormat.format(y_m_d_h_m_sFormat.parse(vect.get(0).toString())));
                                subqueryDisb = "\n ";
                            }
                            if (disburse.equalsIgnoreCase("")) {
                                disburse = queryVect.get(70) == null ? "" : (queryVect.get(70).toString() == "" ? "" : dmyFormat.format(y_m_dFormat.parse(queryVect.get(70).toString())));
                            }
                        } else {
                            disburse = queryVect.get(70) == null ? "" : (queryVect.get(70).toString() == "" ? "" : dmyFormat.format(y_m_dFormat.parse(queryVect.get(70).toString())));
                        }
                    }
                    pojo.setDisbType(disburse);
                    pojo.setLoanExpiryDt(dmyFormat.format(ymdFormat.parse(CbsUtil.monthAdd(queryVect.get(63).toString(), Integer.parseInt(queryVect.get(4).toString().trim().equalsIgnoreCase("") ? loanDuration : queryVect.get(4).toString().trim())))));
                    if (common.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        pojo.setRenewDt(dmyFormat.format(y_m_d_h_m_sFormat.parse(queryVect.get(77).toString())));
                        pojo.setLoanExpiryDt(dmyFormat.format(y_m_d_h_m_sFormat.parse(queryVect.get(79).toString())));
                        if (withSecurity.equalsIgnoreCase("Y")) {
                            pojo.setDisbAmt(odLimit);
                        }
                    }
                    pojo.setMonthlyIncome(queryVect.get(78) == null ? new BigDecimal("0") : new BigDecimal(queryVect.get(78).toString()));
                    pojo.setJointDetails(queryVect.get(83) == null ? "" : (queryVect.get(83).toString()));
//                    if(!queryVect.get(54).toString().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                        String loanExpDt =  CbsUtil.dateAdd(sanctionDt, Integer.parseInt(loanDuration));
//                        pojo.setLoanExpiryDt(loanExpDt);
//                    }
//                    pojo.setNatureOfSecurity(natureOfSec);
//                    pojo.setValueOfSecurity(matValue);
//                    pojo.setMaturityDt(matDt);

//                    pojo.setAccountNumber(acNo);
//                    pojo.setAccountType(accountType);
//                    pojo.setActDesc(common.getAcctDecription(accountType));
//                    pojo.setAppCategory(appCategory);
//                    pojo.setBalance(new BigDecimal(balance));
//                    pojo.setCategoryOption(categoryOption);
//                    pojo.setCustName(custName);
//                    pojo.setDisbAmount(new BigDecimal(disbAmount));
//                    pojo.setDuration(loanPeriod);
//                    pojo.setMinCommunity(minCommunity);
//                    pojo.setOverdueAmt(new BigDecimal(overdueAmt));
//                    pojo.setRate(roi);
//                    pojo.setRelation(relation);
//                    pojo.setSanctionedamt(new BigDecimal(sensiodLimit));
//                    pojo.setSchemeCode(schemeCode);
//                    pojo.setSector(sectorp);
//                    pojo.setSecure(secure);
//                    pojo.setSubsector(subSectorp);
//                    pojo.setWeakerSection(weekersSection);
                    pojo.setInputvalues("Sector:" + sectorDesc + ",Sub Sector:" + subSectorDesc + ",Scheme Code:" + schemeCodeDesc + ", Security Type: " + securedDesc + ", Applicant Category: " + categoryOptDesc + "  Customer Type:" + applicantCategoryDesc + " , Customer Category: " + categoryOptDesc == null ? "ALL" : categoryOptDesc + " Community:" + minorCommunityDesc + ", Relation:" + relationDesc + ",Account Type:" + accType + "");
                    if (reportBasedOn.equalsIgnoreCase("A")) { //AMOUNT
                        if (reportBase.equalsIgnoreCase("O")) {//Based on Outsatnding Amount
//                            if( from!=0 && to!=0){
                            if ((outStanding.abs().compareTo(new BigDecimal(from)) >= 0) && (outStanding.abs().compareTo(new BigDecimal(to)) <= 0)) {
                                pojo.setOutstanding(outStanding);
                                pojo.setCrDrFlag(outStanding.compareTo(new BigDecimal(0)) >= 0 ? "Cr" : "Dr");
                                finalResult.add(pojo);
                            }
//                            }
                        } else {
                            pojo.setCrDrFlag(outStanding.compareTo(new BigDecimal(0)) >= 0 ? "Cr" : "Dr");
                            pojo.setOutstanding(outStanding);
                            finalResult.add(pojo);
                        }
                    } else if (reportBasedOn.equalsIgnoreCase("R")) { //ROI
                        if ((roi.compareTo(new BigDecimal(from)) >= 0) && (roi.compareTo(new BigDecimal(to)) <= 0)) {
                            pojo.setOutstanding(outStanding);
                            pojo.setCrDrFlag(outStanding.compareTo(new BigDecimal(0)) >= 0 ? "Cr" : "Dr");
                            finalResult.add(pojo);
                        }
                    } else {
                        pojo.setCrDrFlag(outStanding.compareTo(new BigDecimal(0)) >= 0 ? "Cr" : "Dr");
                        pojo.setOutstanding(outStanding);
                        finalResult.add(pojo);
                    }
                }
                if (parameter.equalsIgnoreCase("S") || parameter.equalsIgnoreCase("Y")) {
                    if (!finalResult.isEmpty()) {
                        for (int z = 0; z < finalResult.size(); z++) {
                            if (finalResult.get(z).getAcNature().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                for (int y = 0; y < resultList1.size(); y++) {
                                    if (finalResult.get(z).getAcNo().equalsIgnoreCase(resultList1.get(y).getAccountNo())) {
                                        finalResult.get(z).setOverdueAmt(new BigDecimal(resultList1.get(y).getOverDue()));
                                        break;
                                    }
                                }
                            } else if (finalResult.get(z).getAcNature().equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                for (int y = 0; y < resultList2.size(); y++) {
                                    if (finalResult.get(z).getAcNo().equalsIgnoreCase(resultList2.get(y).getAccountNo())) {
                                        finalResult.get(z).setOverdueAmt(new BigDecimal(resultList2.get(y).getOverDue()));
                                        break;
                                    }
                                }
                            } else if (finalResult.get(z).getAcNature().equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                for (int y = 0; y < overDueDetails.size(); y++) {
                                    if (finalResult.get(z).getAcNo().equalsIgnoreCase(overDueDetails.get(y).getAccountNumber())) {
                                        finalResult.get(z).setOverdueAmt(overDueDetails.get(y).getAmountOverdue());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return finalResult;
    }

    @Override
    public List<LoanIntCertPoJoAll> loanIntDetailsAll(String acType, String fromdt, String todt, String brncode, String schemeType) throws ApplicationException {
        List<LoanIntCertPoJoAll> finalList = new ArrayList<LoanIntCertPoJoAll>();
        try {
            SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String acTypeQuery = "", acTypeQuery1 = "";
            String schemeTypeQuery = "";
            List accountList = null;
            if (acType.equalsIgnoreCase("All")) {
                schemeTypeQuery = "";
                acTypeQuery = " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in('" + CbsConstant.CURRENT_AC + "') And CrDbFlag in('B','D')) ";
                acTypeQuery1 = " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','SS','" + CbsConstant.DEMAND_LOAN + "') And CrDbFlag in('B','D')) ";
                schemeTypeQuery = "";
            } else {
                acTypeQuery = " and substring(a.acno,3,2)= '" + acType + "' ";
                if (schemeType.equalsIgnoreCase("All")) {
                    schemeTypeQuery = "";
                } else {
                    schemeTypeQuery = " and c.SCHEME_CODE ='" + schemeType + "' ";
                }
            }
            String brnQuery = "";
            if (brncode.equalsIgnoreCase("0A") || brncode.equalsIgnoreCase("90")) {
                brnQuery = "";
            } else {
                brnQuery = " and substring(a.acno,1,2) ='" + brncode + "'";
            }
            if (acType.equalsIgnoreCase("All")) {
                accountList = em.createNativeQuery("select a.acno,a.custname, b.acctnature,b.roi,b.Sanctionlimit,b.Sanctionlimitdt,"
                        + " ifnull((SELECT cast(sum(cramt-dramt) as decimal(15,2))  FROM loan_recon WHERE dt<='" + todt + "' and acno =a.acno group by acno),0) AS balance , "
                        + " c.SCHEME_CODE ,(select SCHEME_DESCRIPTION From cbs_scheme_general_scheme_parameter_master where scheme_code = c.scheme_code), ifnull(a.closingdate,'') "
                        + " from accountmaster a, loan_appparameter b, cbs_loan_acc_mast_sec c  where a.acno = b.acno and a.acno =c.acno "
                        + " /*and (a.closingdate is null or a.closingdate ='' or a.closingdate >'" + todt + "')*/ and a.OpeningDt<='" + todt + "' "
                        + " " + brnQuery + " " + acTypeQuery1 + " " + schemeTypeQuery + " "
                        + " union "
                        + " select a.acno,a.custname, b.acctnature,b.roi,b.Sanctionlimit,b.Sanctionlimitdt,"
                        + " ifnull((SELECT cast(sum(cramt-dramt) as decimal(15,2))  FROM ca_recon WHERE dt<='" + todt + "' and acno =a.acno group by acno),0) AS balance , "
                        + " c.SCHEME_CODE,(select SCHEME_DESCRIPTION From cbs_scheme_general_scheme_parameter_master where scheme_code = c.scheme_code), ifnull(a.closingdate,'') "
                        + " from accountmaster a, loan_appparameter b, cbs_loan_acc_mast_sec c where a.acno = b.acno  "
                        + " /*and (a.closingdate is null or a.closingdate ='' or a.closingdate >'" + todt + "')*/ and a.OpeningDt<='" + todt + "'  "
                        + " " + brnQuery + " " + acTypeQuery + " " + schemeTypeQuery + "  group by acno").getResultList();
            } else {
                accountList = em.createNativeQuery("select a.acno,a.custname, b.acctnature,b.roi,b.Sanctionlimit,b.Sanctionlimitdt,"
                        + " ifnull((SELECT cast(sum(cramt-dramt) as decimal(15,2))  FROM " + common.getTableName(common.getAcctNature(acType)) + " WHERE dt<='" + todt + "' and acno =a.acno group by acno),0) AS balance,"
                        + " c.SCHEME_CODE ,(select SCHEME_DESCRIPTION From cbs_scheme_general_scheme_parameter_master where scheme_code = c.scheme_code), ifnull(a.closingdate,'')"
                        + " from accountmaster a, loan_appparameter b, cbs_loan_acc_mast_sec c where a.acno = b.acno and a.acno =c.acno"
                        + " /*and (a.closingdate is null or a.closingdate ='' or a.closingdate >'" + todt + "')*/ and a.OpeningDt<='" + todt + "'  "
                        + "  " + brnQuery + " " + acTypeQuery + " " + schemeTypeQuery + "  group by acno").getResultList();
            }
            if (!accountList.isEmpty()) {
                for (int i = 0; i < accountList.size(); i++) {
                    Vector vect1 = (Vector) accountList.get(i);
                    LoanIntCertPoJoAll pojo = new LoanIntCertPoJoAll();
                    pojo.setAcNo(vect1.get(0).toString());
                    pojo.setName(vect1.get(1).toString());
                    pojo.setRoi(new BigDecimal(vect1.get(3).toString()));
                    pojo.setSanctAmt(new BigDecimal(vect1.get(4).toString()));
                    pojo.setSancDt(dmyFormat.format(ymdh.parse(vect1.get(5).toString())));
                    List tempList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from " + common.getTableName(vect1.get(2).toString()) + " where ty=1 and trandesc in(3,4) and acno='" + vect1.get(0).toString() + "' and DATE_FORMAT(dt,'%Y%m%d') between '" + fromdt + "' and '" + todt + "'").getResultList();
                    if (tempList.isEmpty()) {
                        pojo.setIntAmt(new BigDecimal(0));
                    } else {
                        Vector tempVector = (Vector) tempList.get(0);
                        pojo.setIntAmt(new BigDecimal(nft.format(Double.parseDouble(tempVector.get(0).toString()))));
                    }
                    pojo.setOutsAmt(new BigDecimal(vect1.get(6).toString()));
                    pojo.setAcNature(vect1.get(8).toString());
                    if (!vect1.get(9).toString().equalsIgnoreCase("")) {
                        if (!ymdFormat.parse(vect1.get(9).toString()).before(ymdFormat.parse(fromdt))) {
                            finalList.add(pojo);
                        }
                    } else {
                        finalList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return finalList;
    }

    @Override
    public List<LoanIntCertPojo> cbsrepLoanIntCert(String acno, String fromdt, String todt, String brncode) throws ApplicationException {
        List tempList = null;
        Vector tempVector = null;
        List<LoanIntCertPojo> finalList = new ArrayList<LoanIntCertPojo>();
        try {
            String custFullName = "", mailAddressLine1 = "", mailAddressLine2 = "", mailDistrict = "", mailPostalCode = "", mailVillage = "", mailStateCode = "", mailBlock = "";
            //tempList = em.createNativeQuery("SELECT a.acno,ifnull(c.title,''),a.CUSTNAME,c.CrAddress FROM accountmaster a,customermaster c WHERE a.ACNO='" + acno + "' AND SUBSTRING(a.acno,5,6)=c.CustNo AND c.brncode = a.curbrcode AND SUBSTRING(a.acno,3,2)=c.actype  AND a.accttype IN( select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "')) AND a.curbrcode='" + brncode + "'").getResultList();
            tempList = em.createNativeQuery("SELECT a.acno,ifnull(c.title,''),a.CUSTNAME,c.CrAddress FROM accountmaster a,customermaster c WHERE a.ACNO='" + acno + "'  AND SUBSTRING(a.acno,5,6)=c.CustNo AND c.brncode = a.curbrcode AND SUBSTRING(a.acno,3,2)=c.actype  AND a.accttype  IN( select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "')) AND a.curbrcode='" + brncode + "'").getResultList();
            if (!tempList.isEmpty()) {
                LoanIntCertPojo pojo = new LoanIntCertPojo();
                tempVector = (Vector) tempList.get(0);
                pojo.setAcNo(acno);
//                pojo.setCustName(tempVector.get(2).toString());
//                pojo.setCrAdd(tempVector.get(3).toString());
                List l = em.createNativeQuery("select  trim(ccmd.CustFullName), concat(trim(ccmd.MailAddressLine1),' ',ifnull(trim(MailAddressLine2),'')) as custAdd,"
                        + "ifnull(trim(ccmd.MailDistrict),'') as dist, ifnull(trim(ccmd.MailPostalCode),'') pin, ifnull(trim(ccmd.MailVillage),'') village,"
                        + "ifnull(trim(ccmd.mailblock),'')block from cbs_customer_master_detail ccmd,cbs_ref_rec_type re "
                        + "where ccmd.customerid =(select custId from customerid where acno ='" + acno + "') and re.REF_REC_NO = '002' and re.REF_CODE = ccmd.MailStateCode").getResultList();
                if (!l.isEmpty()) {
                    Vector vector1 = (Vector) l.get(0);
                    custFullName = vector1.get(0).toString();
                    mailAddressLine1 = vector1.get(1).toString();
                    mailDistrict = vector1.get(2).toString();
                    mailPostalCode = vector1.get(3).toString();
//                    mailVillage = vector1.get(4).toString();
//                    mailBlock = vector1.get(5).toString();
                }
                pojo.setCustName(custFullName);
                pojo.setCrAdd(mailAddressLine1 + "," + mailDistrict + "," + mailPostalCode);
                tempList = em.createNativeQuery("select scheme_description from cbs_scheme_general_scheme_parameter_master where scheme_code =(select scheme_code from cbs_loan_acc_mast_sec where acno ='" + acno + "')").getResultList();
                if (tempList.isEmpty()) {
                    pojo.setSchemeType("NO SCHEME IS DEFINED");
                } else {
                    tempVector = (Vector) tempList.get(0);
                    pojo.setSchemeType(tempVector.get(0).toString());
                }

                tempList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from loan_recon where ty=1 and trandesc in(3,4) and acno='" + acno + "' and DATE_FORMAT(dt,'%Y%m%d') between '" + fromdt + "' and '" + todt + "'").getResultList();
                if (tempList.isEmpty()) {
                    pojo.setIntAmt(new BigDecimal(0));
                } else {
                    tempVector = (Vector) tempList.get(0);
                    pojo.setIntAmt(new BigDecimal(nft.format(Double.parseDouble(tempVector.get(0).toString()))));
                }

                tempList = em.createNativeQuery("select sanctionlimit from  loan_appparameter  where acno='" + acno + "' ").getResultList();
                if (tempList.isEmpty()) {
                    pojo.setSanctionLimit(0);
                } else {
                    tempVector = (Vector) tempList.get(0);
                    pojo.setSanctionLimit(Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString()))));
                }

                double prinamt = 0;
                //tempList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) from loan_recon where ty=0 and trandesc in(1,0) and acno='" + acno + "' and DATE_FORMAT(dt,'%Y%m%d') between '" + fromdt + "' and '" + todt + "' ").getResultList();
                tempList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) from loan_recon where ty=0 and acno='" + acno + "'and DATE_FORMAT(dt,'%Y%m%d') between '" + fromdt + "' and '" + todt + "'").getResultList();
                if (tempList.isEmpty()) {
                    prinamt = 0;
                } else {
                    tempVector = (Vector) tempList.get(0);
                    prinamt = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                }

                double charge = 0d;
                //tempList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) from loan_recon where ty=1 and trandesc in(0,7,8) and acno='" + acno + "' and DATE_FORMAT(dt,'%Y%m%d') between '" + fromdt + "' and '" + todt + "'").getResultList();
                tempList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0) from loan_recon where ty=1 and trandesc <> 6 and acno='" + acno + "'and DATE_FORMAT(dt,'%Y%m%d') between '" + fromdt + "' and '" + todt + "'").getResultList();
                if (tempList.isEmpty()) {
                    tempVector = (Vector) tempList.get(0);
                    charge = 0d;
                } else {
                    tempVector = (Vector) tempList.get(0);
                    charge = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                }

                pojo.setPrinAmt(new BigDecimal(prinamt - charge));
                pojo.setDuration((fromdt.substring(6, 8) + "/" + fromdt.substring(4, 6) + "/" + fromdt.substring(0, 4)) + " to " + (todt.substring(6, 8) + "/" + todt.substring(4, 6) + "/" + todt.substring(0, 4)));
                float roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(prinamt, todt, acno));
                tempList = em.createNativeQuery("select b.bankname,b.bankaddress,ifnull(br.city,'') as city ,br.Pincode as pinCode from bnkadd b,branchmaster br where b.alphacode=br.alphacode and br.brncode=cast('" + brncode + "' AS unsigned)").getResultList();
                tempVector = (Vector) tempList.get(0);
                pojo.setBnkName(tempVector.get(0).toString());
                if (tempVector.get(3).toString() == null) {
                    pojo.setBnkAddress(tempVector.get(1).toString() + "," + tempVector.get(2).toString());
                } else {
                    pojo.setBnkAddress(tempVector.get(1).toString() + "," + tempVector.get(2).toString() + "," + tempVector.get(3).toString());
                }
                pojo.setInterestRate(roi);
                finalList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        return finalList;
    }

    @Override
    public List<LoanIntCalculationPojo> loanInterestCalculation(String brncode) throws ApplicationException {
        Vector vtr = null;
        List<LoanIntCalculationPojo> finalResult = new ArrayList<LoanIntCalculationPojo>();
        try {
            List result = em.createNativeQuery("select c.acno,c.custname,"
                    + " c.fromdate,c.todate,c.roi,c.closing_bal,c.product,c.interest_amt,c.int_table_code "
                    + " from bnkadd a,branchmaster b, cbs_temp_loan_rep_table c,accountmaster am "
                    + " where c.acno=am.acno and am.curbrcode='" + brncode + "' and a.alphacode = b.alphacode"
                    + " and b.brncode = cast(am.curbrcode as unsigned) order by  c.acno asc").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    vtr = (Vector) result.get(i);
                    LoanIntCalculationPojo pojo = new LoanIntCalculationPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setName(vtr.get(1).toString());
                    pojo.setFrmDt((Date) vtr.get(2));
                    pojo.setToDt((Date) vtr.get(3));
                    pojo.setRoi(Float.parseFloat(vtr.get(4).toString()));
                    pojo.setClosingbal(Double.parseDouble(vtr.get(5).toString()));
                    pojo.setProduct(Double.parseDouble(vtr.get(6).toString()));
                    pojo.setIntTableCode(vtr.get(7).toString());
                    finalResult.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finalResult;
    }

    public List getDistinctAcCodeByAcNature() throws ApplicationException {
        List tableResult = null;
        try {
            tableResult = em.createNativeQuery("select distinct(acctcode) from accounttypemaster where acctnature in ('CA','DL','TL') And CrDbFlag in('B','D') order by acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public double fnBalosForAccountAsOn(String acno, String dt) throws ApplicationException {
        String acctNature;
        double balance = 0;
        List list2 = new ArrayList();
        try {
            List list1 = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = substring('" + acno + "',3,2)").getResultList();
            Vector element1 = (Vector) list1.get(0);
            acctNature = element1.get(0).toString();
            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from recon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from ca_recon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from td_recon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from loan_recon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from loan_recon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from rdrecon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from gl_recon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from of_recon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from npa_recon where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from td_recon where auth= 'y' and acno = '" + acno + "' and closeflag is null and trantype<>27 and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from td_recon where auth= 'y' and acno = '" + acno + "' and closeflag is null and trantype<>27 and dt <='" + dt + "'	").getResultList();
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                list2 = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from ddstransaction where auth= 'y' and acno = '" + acno + "' and dt <='" + dt + "'	").getResultList();
            }
            Vector element2 = (Vector) list2.get(0);

            if (element2.get(0) != null) {
                balance = Double.parseDouble(element2.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return balance;
    }

    public List<LoanAccStmPojo> loanAccountStatement(String acno, String frmdt, String todt) throws ApplicationException {
        List<AbbStatementPojo> abbstatement = null;
        List<LoanAccStmPojo> finalResult = new ArrayList<LoanAccStmPojo>();
        List tempList = null;
        String rdOpDt = "";
        Vector tempVector = null;
        int period = 0;
        float rdRoi = 0;
        double shadowbalance = 0, openingbalance = 0, totalBalance = 0, pendingBalance = 0d, instamt = 0d, rdMatAmt = 0d, amtSanc = 0d, odLimit = 0d;
        String custName = "", acctDesc = "", jtName = "", nomineeName = "", crAdd = "", prAdd = "", openingDt = "", rdMatDt = "", clDt = "";
        try {
            if (!acno.isEmpty()) {
                abbstatement = common.getAbbStatement(acno, frmdt, todt, "");
                String acctCode = fts.getAccountCode(acno);
                String acNat = fts.getAcNatureByCode(acctCode);
                List cldtList = em.createNativeQuery("select date_format(ifnull(ClosingDate,''),'%d/%m/%Y') from accountmaster where acno = '" + acno + "' and  ClosingDate<='" + todt + "'").getResultList();
                if (!cldtList.isEmpty()) {
                    tempVector = (Vector) cldtList.get(0);
                    clDt = tempVector.get(0).toString();
                }
                if (!abbstatement.isEmpty()) {
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        tempList = em.createNativeQuery("select a.acno,a.custname,b.acctdesc,a.jtname1,a.nomination,c.craddress,c.praddress,a.jtname2,"
                                + "a.jtname3,a.jtname4, a.OpeningDt from td_accountmaster a,accounttypemaster b,td_customermaster c where a.acno='" + acno
                                + "' and substring(a.acno,3,2)=b.acctcode and substring(a.acno,5,6)=c.custno and c.brncode = substring(a.acno,1,2) "
                                + "and substring(a.acno,3,2)=c.actype").getResultList();
                    } else {
                        tempList = em.createNativeQuery("select a.acno,a.custname,b.acctdesc,a.jtname1,a.nomination,c.craddress,c.praddress,a.jtname2,"
                                + "a.jtname3,a.jtname4, a.OpeningDt from accountmaster a,accounttypemaster b ,customermaster c where a.acno='" + acno
                                + "' and substring(a.acno,3,2)=b.acctcode and substring(a.acno,5,6)=c.custno and c.brncode = substring(a.acno,1,2) "
                                + "and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2) = c.agcode").getResultList();
                    }
                    tempVector = (Vector) tempList.get(0);
                    custName = tempVector.get(1).toString();
                    acctDesc = tempVector.get(2).toString();
                    if (tempVector.get(3) != null && !tempVector.get(3).toString().trim().equalsIgnoreCase("")) {
                        jtName = tempVector.get(3).toString();
                    }
                    if (tempVector.get(4) != null) {
                        nomineeName = tempVector.get(4).toString();
                    }
                    if (tempVector.get(5) != null) {
                        crAdd = tempVector.get(5).toString();
                    }
                    if (tempVector.get(6) != null) {
                        prAdd = tempVector.get(6).toString();
                    }
                    if (tempVector.get(7) != null && !tempVector.get(7).toString().trim().equalsIgnoreCase("")) {
                        jtName = jtName + " , " + tempVector.get(7).toString();
                    }
                    if (tempVector.get(8) != null && !tempVector.get(8).toString().trim().equalsIgnoreCase("")) {
                        jtName = jtName + " , " + tempVector.get(8).toString();
                    }
                    if (tempVector.get(9) != null && !tempVector.get(9).toString().trim().equalsIgnoreCase("")) {
                        jtName = jtName + " , " + tempVector.get(9).toString();
                    }
                    if (tempVector.get(10) != null && !tempVector.get(10).toString().trim().equalsIgnoreCase("")) {
                        openingDt = dmyFormat.format(ymdFormat.parse(tempVector.get(10).toString()));
                    }
                    tempList = em.createNativeQuery("select ifnull(sum(txninstamt) ,0) from clg_ow_shadowbal where acno= '" + acno + "'").getResultList();
                    if (!tempList.isEmpty()) {
                        tempVector = (Vector) tempList.get(0);
                        pendingBalance = Double.parseDouble(tempVector.get(0).toString());
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(ymdFormat.parse(frmdt));
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    openingbalance = openingBalance(acno, ymdFormat.format(calendar.getTime()));
                    totalBalance = openingbalance + shadowbalance;
                    totalBalance = totalBalance > 0 ? totalBalance : (totalBalance) * (-1);
                    tempList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where acno='" + acno + "'  and status='unpaid' order by duedt limit 1").getResultList();
                    if (!tempList.isEmpty()) {
                        tempVector = (Vector) tempList.get(0);
                        instamt = Double.parseDouble(tempVector.get(0).toString());
                    } else {
                        tempList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where acno='" + acno + "' order by duedt desc limit 1").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            instamt = Double.parseDouble(tempVector.get(0).toString());
                        }
                    }
                    if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        tempList = em.createNativeQuery("select ifnull(rdinstal,0),intdeposit,rdmatdate,openingdt from accountmaster where acno='" + acno + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            instamt = Double.parseDouble(tempVector.get(0).toString());

                            rdRoi = Float.parseFloat(tempVector.get(1).toString());
                            rdMatDt = y_m_dFormat.format(y_m_dFormat.parse(tempVector.get(2).toString()));
                            rdOpDt = tempVector.get(3).toString();
                            //tempList = em.createNativeQuery("Select COUNT(*) FROM rd_installment WHERE ACNO='" + acno + "'").getResultList();
                            tempList = em.createNativeQuery("Select TIMESTAMPDIFF(MONTH,DATE_FORMAT('" + rdOpDt + "','%Y-%m-%d'),'" + rdMatDt + "')").getResultList();
                            tempVector = (Vector) tempList.get(0);
                            period = Integer.parseInt(tempVector.get(0).toString());

                            rdMatAmt = Double.parseDouble(accountRemote.cbsRdCalculation(new Float(instamt), period, rdRoi));
                            // rdMatAmt = rdMatAmt + (period * instamt);
                            rdMatDt = dmyFormat.format(y_m_dFormat.parse(rdMatDt));
                        }
                    }
                    String accStatus = null;
                    if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        tempList = em.createNativeQuery("select a.sanctionlimitdt, b.LOAN_PD_MONTH,ifnull(sg.TURN_OVER_DETAIL_FLAG,'N'), ifnull(sg.LINK_TRAN_TO_PURCHASE_SALE, 'E') from loan_appparameter a, cbs_loan_acc_mast_sec b, cbs_scheme_general_scheme_parameter_master sg where "
                                + " b.SCHEME_CODE = sg.scheme_code  and a.acno = b.acno and a.acno='" + acno + "' and a.brcode='" + fts.getCurrentBrnCode(acno) + "'").getResultList();
                        tempVector = (Vector) tempList.get(0);
                        rdMatDt = dmyFormat.format(y_m_dFormat.parse(tempVector.get(0).toString()));
                        String intCalcBasedOnSecurity = tempVector.get(2).toString();
                        String orderByParameter = tempVector.get(3).toString();
//                        if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
//                            List roiList = em.createNativeQuery("select  aa.SecurityRoi, aa.MargineROI, aa.AppRoi, aa.IntTableCode, aa.addRoi, lienvalue from "
//                                    + "(select  ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(addRoi,0) as addRoi, lienvalue from loansecurity where acno = '" + acno + "' and status = 'Active'  and IntTableCode is not null and entrydate <= '" + todt + "' and IntTableCode <>''"
//                                    + " union all "
//                                    + " select  ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(addRoi,0) as addRoi, lienvalue from loansecurity where acno = '" + acno + "' and status = 'EXPIRED' and IntTableCode is not null and entrydate <= '" + todt + "' and ExpiryDate > '" + todt + "' and IntTableCode <>'') aa order by aa.AppRoi").getResultList();
//                            if (!roiList.isEmpty()) {
//                                Vector roiVector = (Vector) roiList.get(0);
//                                double securityRoi = Double.parseDouble(roiVector.get(0).toString());
//                                double additionalRoi = Double.parseDouble(roiVector.get(4).toString());
//                                double lienValue = Double.parseDouble(roiVector.get(5).toString());
//                                String intTableCode1 = roiVector.get(3).toString();
//                                double roi1 = securityRoi + additionalRoi + Double.parseDouble(loanInterestCalculationBean.getROI(intTableCode1, lienValue, todt));
//                                rdRoi = (float) roi1;
//                            }
//                        } else {
                        rdRoi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(totalBalance, todt, acno));
//                        }
                        period = Integer.parseInt(tempVector.get(1).toString());
                        String presentStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, todt);
                        if (presentStatus.equalsIgnoreCase("DOU")) {
                            accStatus = "DOUBTFUL";
                        } else if (presentStatus.equalsIgnoreCase("SUB")) {
                            accStatus = "SUB-STANDARD";
                        } else if (presentStatus.equalsIgnoreCase("LOS")) {
                            accStatus = "LOSS";
                        } else if (presentStatus.equalsIgnoreCase("INO")) {
                            accStatus = "INOPERATIVE";
                        } else if (presentStatus.equalsIgnoreCase("SUI")) {
                            accStatus = "SUIT FIELDS";
                        } else if (presentStatus.equalsIgnoreCase("DEC")) {
                            accStatus = "DECREED";
                        } else if (presentStatus.equalsIgnoreCase("WIT")) {
                            accStatus = "WITHDRAWAL STOPPED";
                        } else if (presentStatus.equalsIgnoreCase("PRO")) {
                            accStatus = "PROTESTED";
                        } else if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                            accStatus = "STANDARD/OPERATIVE";
                        } else {
                            accStatus = "STANDARD/OPERATIVE";
                        }
                    }

                    tempList = em.createNativeQuery("select ODLIMIT FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                            + "where acno =  '" + acno + "' and txnid = "
                            + "(select min(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + todt + "' "
                            + "and enterdate = (select min(enterdate) from loan_oldinterest where acno =  '" + acno + "' "
                            + "and enterdate>'" + todt + "' ))"
                            + ""
                            + "/*select "
                            + "(select acLimit from loan_oldinterest where acno =  '" + acno + "' "
                            + "and txnid = (select max(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + todt + "' "
                            + "and enterdate = (select min(enterdate) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + todt + "' ))) as acLimit, "
                            + "(select min(SanctionLimitDt) from loan_oldinterest where acno =  '" + acno + "' "
                            + "and txnid = (select min(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + todt + "' "
                            + "and enterdate = (select min(enterdate) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + todt + "' ))) as SanctionLimitDt*/").getResultList();
                    if (!sanctionLimitDtList.isEmpty()) {
                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                        if (vist.get(0) != null && vist.get(1) != null) {
                            rdMatDt = dmyFormat.format(y_m_dFormat.parse(vist.get(0).toString()));
                            amtSanc = Double.parseDouble(vist.get(1).toString());
                            odLimit = Double.parseDouble(vist.get(1).toString());
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            amtSanc = Double.parseDouble(tempVector.get(0).toString());
                            odLimit = Double.parseDouble(tempVector.get(0).toString());
                        }
                    } else {
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            amtSanc = Double.parseDouble(tempVector.get(0).toString());
                            odLimit = Double.parseDouble(tempVector.get(0).toString());
                        }
                    }
                    for (AbbStatementPojo row : abbstatement) {
                        LoanAccStmPojo pojo = new LoanAccStmPojo();
                        pojo.setAcNo(acno);
                        pojo.setDate(dmyFormat.parse(row.getDate1()));
                        pojo.setParticulars(row.getParticulars());
                        pojo.setChequeno(row.getChequeNo());
                        pojo.setValueDt(dmyFormat.parse(row.getValueDate()));
                        pojo.setWithDrawl(new BigDecimal(row.getWithdrawl()));
                        pojo.setDeposit(new BigDecimal(row.getDeposit()));
                        pojo.setAcType(acctDesc);
                        pojo.setBalance(new BigDecimal(row.getBalance()));
                        pojo.setJtName(jtName);
                        pojo.setCustName(custName);
                        pojo.setCrAdd(crAdd);
                        pojo.setPrAdd(prAdd);
                        pojo.setNomination(nomineeName);
                        pojo.setPendBal(new BigDecimal(pendingBalance));
                        pojo.setOpenBal(new BigDecimal(openingbalance));
                        pojo.setAmtInst(new BigDecimal(instamt));
                        pojo.setRoi(rdRoi);
                        pojo.setAmtSanc(new BigDecimal(amtSanc));
                        pojo.setOdLimit(new BigDecimal(odLimit));
                        pojo.setRdMatDate(acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC) ? rdMatDt : openingDt.concat(" (").concat(rdMatDt).concat(")"));
                        pojo.setRdMatAmt(new BigDecimal(rdMatAmt));
                        pojo.setAcNature(acNat);
                        pojo.setPeriod(period);
                        pojo.setAccStatus(accStatus);
                        pojo.setOpeningDt(openingDt);
                        pojo.setClosingDate(clDt);
                        finalResult.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finalResult;
    }

    public List<LoanAccStmPojo> noDuesCertificate(String acno, String frmdt) throws ApplicationException {
        List<AbbStatementPojo> abbstatement = null;
        List<LoanAccStmPojo> finalResult = new ArrayList<LoanAccStmPojo>();
        List tempList = null;
        Date closingDt = null;
        String rdOpDt = "", particular = "True", fatherName = "", jtName1 = "", jtName2 = "", jtName3 = "", jtName4 = "", rdMatDt = "";
        Vector tempVector = null;
        int period = 0;
        float rdRoi = 0;
        double shadowbalance = 0, openingbalance = 0, totalBalance = 0, pendingBalance = 0d, instamt = 0d, rdMatAmt = 0d, amtSanc = 0d, odLimit = 0d;
        String custName = "", acctDesc = "", jtName = "", nomineeName = "", crAdd = "", prAdd = "";
        try {
            if (!acno.isEmpty()) {
//                abbstatement = common.getAbbStatement(acno, frmdt, frmdt, "");
                String acctCode = fts.getAccountCode(acno);
                String acNat = fts.getAcNatureByCode(acctCode);
//                if (!abbstatement.isEmpty()) {
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    tempList = em.createNativeQuery("select a.acno,a.custname,b.acctdesc,a.jtname1,a.nomination,c.craddress,c.praddress,a.jtname2,"
                            + "a.jtname3,a.jtname4 from td_accountmaster a,accounttypemaster b,td_customermaster c where a.acno='" + acno
                            + "' and substring(a.acno,3,2)=b.acctcode and substring(a.acno,5,6)=c.custno and c.brncode = substring(a.acno,1,2) "
                            + "and substring(a.acno,3,2)=c.actype").getResultList();
                } else {
                    tempList = em.createNativeQuery("select a.acno,a.custname,b.acctdesc,a.jtname1,a.nomination,c.craddress,c.praddress,a.jtname2,"
                            + "a.jtname3,a.jtname4 from accountmaster a,accounttypemaster b ,customermaster c where a.acno='" + acno
                            + "' and substring(a.acno,3,2)=b.acctcode and substring(a.acno,5,6)=c.custno and c.brncode = substring(a.acno,1,2) "
                            + "and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2) = c.agcode").getResultList();
                }
                tempVector = (Vector) tempList.get(0);
                custName = tempVector.get(1).toString();
                acctDesc = tempVector.get(2).toString();
                if (tempVector.get(3) != null && !tempVector.get(3).toString().trim().equalsIgnoreCase("")) {
                    jtName = tempVector.get(3).toString();
                }
                if (tempVector.get(4) != null) {
                    nomineeName = tempVector.get(4).toString();
                }
                List addList = em.createNativeQuery("select ifnull(mailAdd,''), ifnull(MailCityCode,''),ifnull(mailstatecode,''),  ifnull(MailPostalCode,''), ifnull(perAdd,''), ifnull(PerCityCode,''), ifnull(Perstatecode,''), ifnull(PerPostalCode,''), LastChangeTime, fathername from "
                        + " (select CONCAT(ifnull(a.MailAddressLine1,''),ifnull(a.MailAddressLine2,''), ifnull(a.MailVillage,''), ifnull(a.MailBlock,'')) as mailAdd, "
                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.mailCityCode) as MailCityCode, "
                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = a.mailstatecode) as mailstatecode,a.MailPostalCode, "
                        + " CONCAT(ifnull(a.PerAddressLine1,''), ifnull(a.PerAddressLine2,''), ifnull(a.PerVillage,''), ifnull(a.PerBlock,'')) as perAdd, "
                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.PerCityCode) as PerCityCode, "
                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = a.Perstatecode) as Perstatecode,a.PerPostalCode, "
                        + " date_format(ifnull(LastChangeTime,'1900-01-01'),'%Y-%m-%d %T') AS LastChangeTime, a.TXNID, a.fathername from cbs_customer_master_detail_his a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acno + "' "
                        + " and a.TXNID =(select max(a.TXNID) from cbs_customer_master_detail_his a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acno + "' "
                        + " and date_format(ifnull(a.LastChangeTime,'19000101'),'%Y%m%d')<='" + frmdt + "') "
                        + " union all"
                        + " select CONCAT(ifnull(a.MailAddressLine1,''),ifnull(a.MailAddressLine2,''), ifnull(a.MailVillage,''), ifnull(a.MailBlock,'')) as mailAdd, "
                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.mailCityCode) as MailCityCode, "
                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = a.mailstatecode) as mailstatecode,a.MailPostalCode, "
                        + " CONCAT(ifnull(a.PerAddressLine1,''), ifnull(a.PerAddressLine2,''), ifnull(a.PerVillage,''), ifnull(a.PerBlock,'')) as perAdd, "
                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.PerCityCode) as PerCityCode, "
                        + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = a.Perstatecode) as Perstatecode,a.PerPostalCode, "
                        + " date_format(ifnull(LastChangeTime,'1900-01-01'),'%Y-%m-%d %T') AS LastChangeTime, 0 as TXNID, a.fathername from cbs_customer_master_detail a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acno + "' "
                        + " and date_format(ifnull(a.LastChangeTime,'19000101'),'%Y%m%d') <= '" + frmdt + "' ) a where a.LastChangeTime= "
                        + " (select max(LastChangeTime) from "
                        + " (select date_format(ifnull(LastChangeTime,'1900-01-01'),'%Y-%m-%d %T')  as LastChangeTime from cbs_customer_master_detail_his a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acno + "' "
                        + " and a.TXNID =(select max(a.TXNID) from cbs_customer_master_detail_his a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acno + "' "
                        + " and date_format(ifnull(a.LastChangeTime,'19000101'),'%Y%m%d')<='" + frmdt + "') "
                        + " union all "
                        + " select date_format(ifnull(LastChangeTime,'1900-01-01'),'%Y-%m-%d %T')  as LastChangeTime from cbs_customer_master_detail a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acno + "' "
                        + " and date_format(ifnull(a.LastChangeTime,'19000101'),'%Y%m%d')<='" + frmdt + "' ) a) ").getResultList();

                if (!addList.isEmpty()) {
                    Vector addVect = (Vector) addList.get(0);
                    if (addVect.get(0).toString().trim().contains(addVect.get(1).toString().trim())) {
                        crAdd = addVect.get(0).toString().trim();
                    } else {
                        crAdd = addVect.get(0).toString().trim().concat(", ").concat(addVect.get(1).toString().trim());
                    }
                    if (!addVect.get(2).toString().trim().equalsIgnoreCase("")) {
                        crAdd = crAdd.concat(", ").concat(addVect.get(2).toString().trim());
                    }
                    if (!addVect.get(3).toString().trim().equalsIgnoreCase("")) {
                        crAdd = crAdd.concat(", ").concat(addVect.get(3).toString().trim());
                    }

                    if (addVect.get(4).toString().trim().contains(addVect.get(5).toString().trim())) {
                        prAdd = addVect.get(4).toString().trim();
                    } else {
                        prAdd = addVect.get(4).toString().trim().concat(",").concat(addVect.get(5).toString().trim());
                    }
                    if (!addVect.get(6).toString().trim().equalsIgnoreCase("")) {
                        prAdd = prAdd.concat(", ").concat(addVect.get(6).toString().trim());
                    }
                    if (!addVect.get(7).toString().trim().equalsIgnoreCase("")) {
                        prAdd = prAdd.concat(", ").concat(addVect.get(7).toString().trim());
                    }

                    if (!addVect.get(9).toString().trim().equalsIgnoreCase("")) {
                        fatherName = ((addVect.get(9).toString().trim().contains("W/O") || addVect.get(9).toString().trim().contains("D/O") || addVect.get(9).toString().trim().contains("H/O") || addVect.get(9).toString().trim().contains("F/O") || addVect.get(9).toString().trim().contains("M/O")) ? (" ").concat(addVect.get(9).toString().trim()) : (" S/o ").concat(addVect.get(9).toString().trim()));
                    }

                }

                List joint1List = em.createNativeQuery(" select ifnull(custid1,''),ifnull(JtName1,'')  from acedithistory where acno = '" + acno + "' and date_format(UpdateDt,'%Y%m%d') <='" + frmdt + "'"
                        + " and txnid=(select max(txnid) from acedithistory where acno = '" + acno + "'  and date_format(UpdateDt,'%Y%m%d') <='" + frmdt + "')").getResultList();
                if (!joint1List.isEmpty()) {
                    Vector joint1Vect = (Vector) joint1List.get(0);
                    jtName1 = !joint1Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint1Vect.get(1).toString()) : "";
                    if (jtName1.equalsIgnoreCase("")) {
                        joint1List = em.createNativeQuery(" select ifnull(custid1,''),ifnull(JtName1,'') from accountmaster where acno = '" + acno + "' ").getResultList();
                        if (!joint1List.isEmpty()) {
                            joint1Vect = (Vector) joint1List.get(0);
                            jtName1 = !joint1Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint1Vect.get(1).toString()) : "";
                        } else {
                            jtName1 = "";
                        }
                    }
                } else {
                    joint1List = em.createNativeQuery(" select ifnull(custid1,''),ifnull(JtName1,'') from accountmaster where acno = '" + acno + "' ").getResultList();
                    if (!joint1List.isEmpty()) {
                        Vector joint1Vect = (Vector) joint1List.get(0);
                        jtName1 = !joint1Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint1Vect.get(1).toString()) : "";
                    } else {
                        jtName1 = "";
                    }
                }

                List joint2List = em.createNativeQuery(" select ifnull(custid2,''),ifnull(JtName2,'')  from acedithistory where acno = '" + acno + "' and date_format(UpdateDt,'%Y%m%d') <='" + frmdt + "'"
                        + " and txnid=(select max(txnid) from acedithistory where acno = '" + acno + "'  and date_format(UpdateDt,'%Y%m%d') <='" + frmdt + "')").getResultList();
                if (!joint2List.isEmpty()) {
                    Vector joint2Vect = (Vector) joint2List.get(0);
                    jtName2 = !joint2Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint2Vect.get(1).toString()) : "";
                    if (jtName2.equalsIgnoreCase("")) {
                        joint2List = em.createNativeQuery(" select ifnull(custid2,''),ifnull(JtName2,'') from accountmaster where acno = '" + acno + "' ").getResultList();
                        if (!joint2List.isEmpty()) {
                            joint2Vect = (Vector) joint2List.get(0);
                            jtName2 = !joint2Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint2Vect.get(1).toString()) : "";
                        } else {
                            jtName2 = "";
                        }
                    }
                } else {
                    joint2List = em.createNativeQuery(" select ifnull(custid2,''),ifnull(JtName2,'') from accountmaster where acno = '" + acno + "' ").getResultList();
                    if (!joint2List.isEmpty()) {
                        Vector joint2Vect = (Vector) joint2List.get(0);
                        jtName2 = !joint2Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint2Vect.get(1).toString()) : "";
                    } else {
                        jtName2 = "";
                    }
                }

                List joint3List = em.createNativeQuery(" select ifnull(custid3,''),ifnull(JtName3,'')  from acedithistory where acno = '" + acno + "' and date_format(UpdateDt,'%Y%m%d') <='" + frmdt + "'"
                        + " and txnid=(select max(txnid) from acedithistory where acno = '" + acno + "'  and date_format(UpdateDt,'%Y%m%d') <='" + frmdt + "')").getResultList();
                if (!joint3List.isEmpty()) {
                    Vector joint3Vect = (Vector) joint3List.get(0);
                    jtName3 = !joint3Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint3Vect.get(1).toString()) : "";
                    if (jtName3.equalsIgnoreCase("")) {
                        joint3List = em.createNativeQuery(" select ifnull(custid3,''),ifnull(JtName3,'') from accountmaster where acno = '" + acno + "' ").getResultList();
                        if (!joint3List.isEmpty()) {
                            joint3Vect = (Vector) joint3List.get(0);
                            jtName3 = !joint3Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint3Vect.get(1).toString()) : "";
                        } else {
                            jtName3 = "";
                        }
                    }
                } else {
                    joint3List = em.createNativeQuery(" select ifnull(custid3,''),ifnull(JtName3,'') from accountmaster where acno = '" + acno + "' ").getResultList();
                    if (!joint3List.isEmpty()) {
                        Vector joint3Vect = (Vector) joint3List.get(0);
                        jtName3 = !joint3Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint3Vect.get(1).toString()) : "";
                    } else {
                        jtName3 = "";
                    }
                }

                List joint4List = em.createNativeQuery(" select ifnull(custid4,''),ifnull(JtName4,'')  from acedithistory where acno = '" + acno + "' and date_format(UpdateDt,'%Y%m%d') <='" + frmdt + "'"
                        + " and txnid=(select max(txnid) from acedithistory where acno = '" + acno + "'  and date_format(UpdateDt,'%Y%m%d') <='" + frmdt + "')").getResultList();
                if (!joint4List.isEmpty()) {
                    Vector joint4Vect = (Vector) joint4List.get(0);
                    jtName4 = !joint4Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint4Vect.get(1).toString()) : "";
                    if (jtName4.equalsIgnoreCase("")) {
                        joint4List = em.createNativeQuery(" select ifnull(custid4,''),ifnull(JtName4,'') from accountmaster where acno = '" + acno + "' ").getResultList();
                        if (!joint4List.isEmpty()) {
                            joint4Vect = (Vector) joint4List.get(0);
                            jtName4 = !joint4Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint4Vect.get(1).toString()) : "";
                        } else {
                            jtName4 = "";
                        }
                    }
                } else {
                    joint4List = em.createNativeQuery(" select ifnull(custid4,''),ifnull(JtName4,'') from accountmaster where acno = '" + acno + "' ").getResultList();
                    if (!joint4List.isEmpty()) {
                        Vector joint4Vect = (Vector) joint4List.get(0);
                        jtName4 = !joint4Vect.get(1).toString().equalsIgnoreCase("") ? ", ".concat(joint4Vect.get(1).toString()) : "";
                    } else {
                        jtName4 = "";
                    }
                }

                jtName = (!jtName1.equalsIgnoreCase("") ? jtName1 : "").concat(
                        (!jtName2.equalsIgnoreCase("") ? jtName2 : "")).concat(
                        (!jtName3.equalsIgnoreCase("") ? jtName3 : "")).concat(
                        (!jtName4.equalsIgnoreCase("") ? jtName4 : ""));

                tempList = em.createNativeQuery("select ifnull(sum(txninstamt) ,0) from clg_ow_shadowbal where acno= '" + acno + "'").getResultList();
                if (!tempList.isEmpty()) {
                    tempVector = (Vector) tempList.get(0);
                    pendingBalance = Double.parseDouble(tempVector.get(0).toString());
                }
                if (totalBalance != 0) {
                    particular = "Some clearing balance is exist in this account.";
                } else {
                    particular = particular.equalsIgnoreCase("true") ? "True" : particular;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ymdFormat.parse(frmdt));
//                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                openingbalance = openingBalance(acno, ymdFormat.format(calendar.getTime()));
                List balnceList = em.createNativeQuery("select sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) from npa_recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                openingbalance = openingbalance + (vtr2.get(0) == null ? 0 : (Double.parseDouble(vtr2.get(0).toString()) > 0 ? Double.parseDouble(vtr2.get(0).toString()) : Double.parseDouble(vtr2.get(0).toString()) * -1));
                totalBalance = openingbalance + shadowbalance;
                totalBalance = totalBalance > 0 ? totalBalance : (totalBalance) * (-1);
                if (totalBalance != 0) {
                    particular = "Some balance is exist in this account.";
                } else {
                    particular = particular.equalsIgnoreCase("true") ? "True" : particular;
                }
                tempList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where acno='" + acno + "'  and status='unpaid' order by duedt limit 1").getResultList();
                if (!tempList.isEmpty()) {
                    particular = "Some EMI is not paid in this account.";
                } else {
                    particular = particular.equalsIgnoreCase("true") ? "True" : particular;
                }
                tempList = em.createNativeQuery("select acno, closingdate from accountmaster where acno='" + acno + "'  and AccStatus = 9 ").getResultList();
                if (!tempList.isEmpty()) {
                    particular = particular.equalsIgnoreCase("true") ? "True" : particular;
                    tempVector = (Vector) tempList.get(0);
                    closingDt = dmyFormat.parse(dmyFormat.format(ymdFormat.parse(tempVector.get(1).toString())));
                } else {
                    particular = "Status of this account is open.";
                }
                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    tempList = em.createNativeQuery("select sanctionlimitdt from loan_appparameter where acno='" + acno + "' and brcode='" + fts.getCurrentBrnCode(acno) + "'").getResultList();
                    tempVector = (Vector) tempList.get(0);
                    rdMatDt = dmyFormat.format(y_m_dFormat.parse(tempVector.get(0).toString()));
                    rdRoi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(totalBalance, frmdt, acno));
                }

                tempList = em.createNativeQuery("select ODLIMIT FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
                if (!tempList.isEmpty()) {
                    tempVector = (Vector) tempList.get(0);
                    amtSanc = Double.parseDouble(tempVector.get(0).toString());
                    odLimit = Double.parseDouble(tempVector.get(0).toString());
                }
//                    for (AbbStatementPojo row : abbstatement) {
                LoanAccStmPojo pojo = new LoanAccStmPojo();
                pojo.setAcNo(acno);
                pojo.setDate(closingDt);
                pojo.setParticulars(particular);
                pojo.setChequeno("");
                pojo.setValueDt(dmyFormat.parse(dmyFormat.format(ymdFormat.parse(frmdt))));
                pojo.setWithDrawl(new BigDecimal("0"));
                pojo.setDeposit(new BigDecimal("0"));
                pojo.setAcType(acctDesc);
                pojo.setBalance(new BigDecimal(totalBalance));
                pojo.setJtName(jtName);
                pojo.setCustName(custName.concat(!jtName.equalsIgnoreCase("") ? jtName : fatherName));
                pojo.setCrAdd(crAdd);
                pojo.setPrAdd(prAdd);
                pojo.setNomination(nomineeName);
                pojo.setPendBal(new BigDecimal(pendingBalance));
                pojo.setOpenBal(new BigDecimal(openingbalance));
                pojo.setAmtInst(new BigDecimal(instamt));
                pojo.setRoi(rdRoi);
                pojo.setAmtSanc(new BigDecimal(amtSanc));
                pojo.setOdLimit(new BigDecimal(odLimit));
                pojo.setRdMatDate(rdMatDt);
                pojo.setRdMatAmt(new BigDecimal(rdMatAmt));
                pojo.setPeriod(period);
                finalResult.add(pojo);

//                    }
//                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finalResult;
    }

    public double openingBalance(String acno, String date) {
        List balnceList;
        List acnatureList = em.createNativeQuery("select  distinct(acctnature) from accounttypemaster where acctcode='" + acno.substring(2, 4) + "'").getResultList();
        if (!acnatureList.isEmpty()) {
            Vector vtr = (Vector) acnatureList.get(0);
            String acctnature = vtr.get(0).toString();
            if (acctnature.equalsIgnoreCase("SB")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("CA")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from ca_recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("TD")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from td_recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("TL") || acctnature.equalsIgnoreCase("DL")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from loan_recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("RD")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from rdrecon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("PO")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from gl_recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("OF")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from of_recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("NP")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from npa_recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("MS")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from td_recon where auth= 'y' and acno = '" + acno + "' and trantype<>27 and closeflag is null  and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("FD")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from td_recon where auth= 'y' and acno = '" + acno + "' and trantype<>27 and closeflag is null and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            } else if (acctnature.equalsIgnoreCase("DS")) {
                balnceList = em.createNativeQuery("select cast(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)) as decimal(25,2)) from recon where auth= 'y' and acno = '" + acno + "' and dt <=DATE_FORMAT('" + date + "','%Y%m%d')").getResultList();
                Vector vtr2 = (Vector) balnceList.get(0);
                return vtr2.get(0) == null ? 0 : Double.parseDouble(vtr2.get(0).toString());
            }
        }
        return 0.0;
    }

    public List getAccList() throws ApplicationException {
        try {
            return em.createNativeQuery("select 'ALL' as acctcode union all select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getAccListType() throws ApplicationException {
        try {
            return em.createNativeQuery("select 'ALL' as acctcode union all select acctcode from accounttypemaster").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getLoanAccList() throws ApplicationException {
        try {
            /*
             * In Parameterinfo_report the ReportName = LOAN RECOVERY and 
             * if CODE = 1 Means AcctCode '01','02' NOT INVOLVED
             * if CODE = 0 Means AcctCode '01','02' INVOLVED
             */
            /*String query = " ";
             List codeList = em.createNativeQuery("select code from parameterinfo_report where reportname = 'LOAN RECOVERY'").getResultList();
             if (codeList.size() > 0) {
             Vector codeVect = (Vector) codeList.get(0);
             String code = codeVect.get(0).toString();
             if (code.equalsIgnoreCase("1")) {
             query = "and acctcode not in ('" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "','" + CbsAcCodeConstant.CASH_CREDIT.getAcctCode() + "') ";
             }
             }
             return em.createNativeQuery("select 'ALL' as acctcode union all select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') " + query + "").getResultList();*/
            return em.createNativeQuery("select 'ALL' as acctcode union all select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') And CrDbFlag in('B','D') ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getLoanTypeList() throws ApplicationException {
        try {
            /*
             * In Parameterinfo_report the ReportName = LOAN RECOVERY and 
             * if CODE = 1 Means AcctCode '01','02' NOT INVOLVED
             * if CODE = 0 Means AcctCode '01','02' INVOLVED
             */

            /* Code Comment 
             String query = " ";
             List codeList = em.createNativeQuery("select code from parameterinfo_report where reportname = 'LOAN AC DETAILS'").getResultList();
             if (codeList.size() > 0) {
             Vector codeVect = (Vector) codeList.get(0);
             String code = codeVect.get(0).toString();
             if (code.equalsIgnoreCase("1")) {
             query = "and acctcode not in ('" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "') ";
             }
             }
             return em.createNativeQuery("SELECT 'ALL' as acctcode union all select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') " + query + "").getResultList(); */
            return em.createNativeQuery("SELECT 'ALL' as acctcode union all select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') And CrDbFlag in('B','D') ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getGridDataList(String refrecNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select 'ALL' AS ref_code,'ALL' AS ref_desc UNION ALL select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='" + refrecNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<LoanRecoveryTable> loanRecoveryReport(String acctCode, String frmdt, String todt, String branchCode, String reportType, String acNature) throws ApplicationException {
        List<LoanRecoveryTable> finalList = new ArrayList<LoanRecoveryTable>();
        List<String> acctypeList = new ArrayList<String>();
        List<String> acctnaturList = new ArrayList<String>();
        String acType, acNo, custName;
        String acctNature;
        Vector vtrmain, vtrsub;
        DecimalFormat df = new DecimalFormat("#.##");
        Map<String, String> accnatureMap = new HashMap<String, String>();
        double amtCash = 0d, amtClearing = 0d, amtClearingDr = 0d, amtTransfer = 0d, amtDis = 0d, sansonLimit = 0d,
                balance = 0d, npaRec = 0, intRec = 0;
        String npaQuery = "";
        if (reportType.equalsIgnoreCase("0")) {
            npaQuery = "";
        } else if (reportType.equalsIgnoreCase("1")) {
            npaQuery = "and r.acno  in(select ast.acno as npaAcno from accountstatus ast, "
                    + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + "(select acno as ano, max(effdt) as dt from accountstatus where effdt  <= '" + todt + "' and SPFLAG IN ('11','12','13')  group by acno) b \n"
                    + "where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN ('11','12','13')  group by aa.acno,aa.effdt) c \n"
                    + "where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno)";
        } else if (reportType.equalsIgnoreCase("2")) {
            npaQuery = "and r.acno not in(select ast.acno as npaAcno from accountstatus ast, "
                    + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + "(select acno as ano, max(effdt) as dt from accountstatus where effdt  <= '" + todt + "' and SPFLAG IN ('11','12','13')  group by acno) b \n"
                    + "where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN ('11','12','13')  group by aa.acno,aa.effdt) c \n"
                    + "where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno)";
        }


        try {
            /*
             * In Parameterinfo_report the ReportName = LOAN RECOVERY and 
             * if CODE = 1 Means AcctCode '01','02' NOT INVOLVED
             * if CODE = 0 Means AcctCode '01','02' INVOLVED
             */
            /* Code Comment After Discussion With Alok 
             String query = " ";
             List codeList = em.createNativeQuery("select code from parameterinfo_report where reportname = 'LOAN RECOVERY'").getResultList();
             if (codeList.size() > 0) {
             Vector codeVect = (Vector) codeList.get(0);
             String code = codeVect.get(0).toString();
             if (code.equalsIgnoreCase("1")) {
             query = "and acctcode not in ('" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "','" + CbsAcCodeConstant.CASH_CREDIT.getAcctCode() + "') ";
             }
             }
             */
            String bnkCode = fts.getBankCode();
            List prinOpbalList;
            List intOpbalList;
            if (acctCode.equalsIgnoreCase("ALL")) {
//                List acctCodeNatureList = em.createNativeQuery("select acctcode,acctnature from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "') " + query + " order by acctcode").getResultList();
                List acctCodeNatureList = em.createNativeQuery("select acctcode,acctnature from accounttypemaster where acctnature in('" + acNature + "') And CrDbFlag in('B','D') order by acctcode").getResultList();
                for (int i = 0; i < acctCodeNatureList.size(); i++) {
                    Vector vtr = (Vector) acctCodeNatureList.get(i);
                    accnatureMap.put(vtr.get(0).toString(), vtr.get(1).toString());
                }
            } else {
                List acctCodeNatureList = em.createNativeQuery("select acctcode,acctnature from accounttypemaster where acctcode in('" + acctCode + "') order by acctcode").getResultList();
                for (int i = 0; i < acctCodeNatureList.size(); i++) {
                    Vector vtr = (Vector) acctCodeNatureList.get(i);
                    accnatureMap.put(vtr.get(0).toString(), vtr.get(1).toString());
                }
            }
            Set<Map.Entry<String, String>> set = accnatureMap.entrySet();
            for (Map.Entry<String, String> me : set) {
                acType = me.getKey();
                acctNature = me.getValue();

                if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List result;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        result = em.createNativeQuery("select r.acno ,l.sanctionlimit, am.custname,l.recover,ifnull(am.CUST_TYPE,''),am.accstatus from ca_recon r,loan_appparameter l,accountmaster am where r.acno=l.acno and r.acno=am.acno and  r.trantype in(0,1,2) and r.ty=0 " + npaQuery + "and dt between '" + frmdt + "' and '" + todt + "' "
                                + "and am.accttype='" + acType + "' group by r.acno,l.sanctionlimit, am.custname order by r.acno").getResultList();
                    } else {
                        result = em.createNativeQuery("select r.acno ,l.sanctionlimit, am.custname,l.recover,ifnull(am.CUST_TYPE,''),am.accstatus from ca_recon r,loan_appparameter l,accountmaster am where r.acno=l.acno and r.acno=am.acno and  r.trantype in(0,1,2) and r.ty=0 " + npaQuery + " and dt between '" + frmdt + "' and '" + todt + "' and am.accttype='" + acType + "' and am.curbrcode='" + branchCode + "' group by r.acno,l.sanctionlimit, am.custname order by r.acno").getResultList();
                    }
                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            LoanRecoveryTable pojo = new LoanRecoveryTable();
                            vtrmain = (Vector) result.get(j);
                            acNo = vtrmain.get(0).toString();
                            sansonLimit = Double.parseDouble(vtrmain.get(1).toString());
                            custName = vtrmain.get(2).toString();
                            String recoverType = vtrmain.get(3).toString();
                            String custType = vtrmain.get(4).toString();
                            String accStatus = vtrmain.get(5).toString();
                            balance = openingBalance(acNo, todt);

                            // opening princ bal
                            double princOpBal = 0, totalRecoverAmt = 0, princRec = 0, openingInt = 0, npaOpeningInt = 0;
                            if (recoverType.equalsIgnoreCase("PIC")) {

                                if (custType.equalsIgnoreCase("ST") && bnkCode.equalsIgnoreCase("hcbl")) {
                                    prinOpbalList = em.createNativeQuery("select cast(sum(pOpBal) as decimal(25,2)) as princBal from("
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' and trantype <> 8 "
                                            + "union all "
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' and trantype <> 8"
                                            + ")a").getResultList();
                                    Vector vtr = (Vector) prinOpbalList.get(0);
                                    princOpBal = Double.parseDouble(vtr.get(0).toString());
                                    if (princOpBal > 0) {
                                        princOpBal = 0;
                                    }
                                } else {
                                    prinOpbalList = em.createNativeQuery("select cast(sum(pOpBal) as decimal(25,2)) as princBal from("
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acNo + "' and dt< '" + frmdt + "'  "
                                            + "union all "
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' "
                                            + ")a").getResultList();

                                    Vector vtr = (Vector) prinOpbalList.get(0);
                                    princOpBal = Double.parseDouble(vtr.get(0).toString());
                                }
                            } else {
                                if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {
                                    intOpbalList = em.createNativeQuery("select sum(intOpBal) from ("
                                            + "select ifnull(sum(cramt-Dramt),0) intOpBal from npa_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' )b").getResultList();
                                    Vector vtr2 = (Vector) intOpbalList.get(0);
                                    npaOpeningInt = Math.abs(Double.parseDouble(vtr2.get(0).toString()));

                                } else {

                                    prinOpbalList = em.createNativeQuery("select sum(pOpBal) from("
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' and trantype <> 8 "
                                            + ")a").getResultList();
                                    Vector vtr1 = (Vector) prinOpbalList.get(0);
                                    princOpBal = Double.parseDouble(vtr1.get(0).toString());
                                    if (princOpBal > 0) {
                                        princOpBal = 0;
                                    }

                                    intOpbalList = em.createNativeQuery("select sum(pOpBal) from("
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acNo + "' and dt between '" + frmdt + "' and  '" + todt + "' and trantype=8 "
                                            + ")a").getResultList();
                                    vtr1 = (Vector) intOpbalList.get(0);
                                    openingInt = Math.abs(Double.parseDouble(vtr1.get(0).toString()));

//                                    prinOpbalList = em.createNativeQuery("select ifnull(sum(Cramt),0) as creditAmt from ca_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' ").getResultList();
//                                    vtr1 = (Vector) prinOpbalList.get(0);
//                                    double creditAmt = Double.parseDouble(vtr1.get(0).toString());
//                                    if (openingInt >= creditAmt) {
//                                        openingInt = openingInt - creditAmt;
//                                    } else {
//                                        openingInt = 0;
//                                    }
                                }
                            }

                            List result1 = em.createNativeQuery("select ifnull(sum(cramt),0) from ca_recon  where  trantype =0 and ty=0 and dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result1.isEmpty()) {
                                vtrsub = (Vector) result1.get(0);
                                amtCash = Double.parseDouble(vtrsub.get(0).toString());
                            }
                            List result2 = em.createNativeQuery("select ifnull(sum(cramt),0) from ca_recon  where   trantype =1 and ty=0 and dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result2.isEmpty()) {
                                vtrsub = (Vector) result2.get(0);
                                amtClearing = Double.parseDouble(vtrsub.get(0).toString());
                            }
                            List result3 = em.createNativeQuery("select ifnull(sum(dramt),0) from ca_recon  where   trantype =1 and ty=1 and details like '%ret%' and  dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result3.isEmpty()) {
                                vtrsub = (Vector) result3.get(0);
                                amtClearingDr = Double.parseDouble(vtrsub.get(0).toString());
                            }
                            List result4 = em.createNativeQuery("select ifnull(sum(cramt),0) from ca_recon  where  trantype =2 and ty=0 and dt between  '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result4.isEmpty()) {
                                vtrsub = (Vector) result4.get(0);
                                amtTransfer = Double.parseDouble(vtrsub.get(0).toString());
                            }

                            totalRecoverAmt = (amtCash + amtClearing + amtTransfer) - amtClearingDr;

                            List result5 = em.createNativeQuery("select ifnull(sum(dramt),0) from ca_recon   where dt <= '" + todt + "'  and trantype <> 8 and trandesc <15 and acno='" + acNo + "'").getResultList();
                            if (!result5.isEmpty()) {
                                vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                            }

                            List result6 = em.createNativeQuery("select ifnull(sum(cramt),0) from npa_recon  where  dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result6.isEmpty()) {
                                vtrsub = (Vector) result6.get(0);
                                npaRec = Double.parseDouble(vtrsub.get(0).toString());
                            }

                            List result7 = em.createNativeQuery("select ifnull(sum(a.intamt),0) as intamt from "
                                    + "(select sum(ifnull(dramt,0)) as intamt from ca_recon where  dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "' and (trantype in (8) or trandesc in (3,4))  "
                                    + "union all "
                                    + "select sum(ifnull(dramt,0)) as intamt from npa_recon where dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "' and (trantype in (8) or trandesc in (3,4)) ) a").getResultList();
                            if (!result7.isEmpty()) {
                                vtrsub = (Vector) result7.get(0);
                                double intPosted = Double.parseDouble(vtrsub.get(0).toString());
                                double totalLoanRec = ((amtCash + amtClearing - amtClearingDr + amtTransfer) - npaRec) <= 0 ? 0d : ((amtCash + amtClearing - amtClearingDr + amtTransfer) - npaRec);
                                intRec = (totalLoanRec >= intPosted) ? intPosted : totalLoanRec;
                            }

                            if (recoverType.equalsIgnoreCase("PIC")) {
                                intRec = 0;
                                npaRec = 0;
                                if (princOpBal == 0) {
                                    intRec = totalRecoverAmt;
                                } else {
                                    if (Math.abs(princOpBal) >= totalRecoverAmt) {
                                        princRec = totalRecoverAmt;
                                    } else {
                                        princRec = Math.abs(princOpBal);
                                        intRec = totalRecoverAmt - Math.abs(princOpBal);
                                    }
                                }
                            } else {

                                if (bnkCode.equalsIgnoreCase("hcbl")) {
                                    intRec = 0;
                                    npaRec = 0;
                                    if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {
                                        if (npaOpeningInt == 0) {
                                            //intRec = totalRecoverAmt;
                                            if (openingInt >= totalRecoverAmt) {
                                                intRec = totalRecoverAmt;
                                            } else {
                                                intRec = openingInt;
                                                princRec = totalRecoverAmt - openingInt;
                                            }
                                        } else {
                                            if (npaOpeningInt >= totalRecoverAmt) {
                                                npaRec = totalRecoverAmt;
                                            } else {
                                                if (openingInt == 0) {
                                                    npaRec = npaOpeningInt;
                                                    princRec = totalRecoverAmt - npaOpeningInt;
                                                } else {
                                                    npaRec = npaOpeningInt;
                                                    double leftRecoverAmt = totalRecoverAmt - npaOpeningInt;
                                                    if (openingInt >= leftRecoverAmt) {
                                                        intRec = leftRecoverAmt;
                                                    } else {
                                                        princRec = leftRecoverAmt - openingInt;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (openingInt == 0) {
                                            princRec = totalRecoverAmt;
                                        } else {
                                            if (openingInt >= totalRecoverAmt) {
                                                intRec = totalRecoverAmt;
                                            } else {
                                                intRec = Math.abs(openingInt);
                                                princRec = totalRecoverAmt - Math.abs(openingInt);
                                            }
                                        }
                                    }

                                } else {
                                    princRec = (((amtCash + amtClearing + amtTransfer) - amtClearingDr) - npaRec) - intRec < 0 ? 0d : (((amtCash + amtClearing + amtTransfer) - amtClearingDr) - npaRec) - intRec;

                                }
                            }
                            if (bnkCode.equalsIgnoreCase("hcbl")) {
                                if (intRec != 0 || npaRec != 0) {
                                    pojo.setAccountNumber(acNo);
                                    pojo.setAcDesc(common.getAcctDecription(acNo.substring(2, 4)));
                                    pojo.setName(custName);
                                    float roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(new BigDecimal(sansonLimit).abs().toString()), todt, acNo));
                                    pojo.setRoi(roi);
                                    pojo.setAmountSanc(BigDecimal.valueOf(sansonLimit));
                                    pojo.setAmtCash(BigDecimal.valueOf(amtCash));
                                    pojo.setAmtClearing(BigDecimal.valueOf(amtClearing));
                                    pojo.setAmtClearingdr(BigDecimal.valueOf(amtClearingDr));
                                    pojo.setAmtDis(BigDecimal.valueOf(amtDis));
                                    pojo.setAmtTransfer(BigDecimal.valueOf(amtTransfer));
                                    pojo.setBalance(BigDecimal.valueOf(Double.parseDouble(df.format(balance))));
                                    pojo.setNpaRec(BigDecimal.valueOf(npaRec));
                                    pojo.setIntRec(BigDecimal.valueOf(intRec));
                                    if (npaRec != 0) {
                                        pojo.setPrinRec(BigDecimal.valueOf(princRec));
                                    } else {
                                        pojo.setPrinRec(BigDecimal.valueOf(0));
                                    }
                                    pojo.setRecoverType(recoverType);
                                    finalList.add(pojo);
                                }
                            } else {
                                pojo.setAccountNumber(acNo);
                                pojo.setAcDesc(common.getAcctDecription(acNo.substring(2, 4)));
                                pojo.setName(custName);
                                float roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(new BigDecimal(sansonLimit).abs().toString()), todt, acNo));
                                pojo.setRoi(roi);
                                pojo.setAmountSanc(BigDecimal.valueOf(sansonLimit));
                                pojo.setAmtCash(BigDecimal.valueOf(amtCash));
                                pojo.setAmtClearing(BigDecimal.valueOf(amtClearing));
                                pojo.setAmtClearingdr(BigDecimal.valueOf(amtClearingDr));
                                pojo.setAmtDis(BigDecimal.valueOf(amtDis));
                                pojo.setAmtTransfer(BigDecimal.valueOf(amtTransfer));
                                pojo.setBalance(BigDecimal.valueOf(Double.parseDouble(df.format(balance))));
                                pojo.setNpaRec(BigDecimal.valueOf(npaRec));
                                pojo.setIntRec(BigDecimal.valueOf(intRec));
                                pojo.setPrinRec(BigDecimal.valueOf(princRec));
                                pojo.setRecoverType(recoverType);
                                finalList.add(pojo);
                            }
                        }
                    }

                } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List result;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        result = em.createNativeQuery("select r.acno ,l.sanctionlimit, am.custname,l.recover,ifnull(am.CUST_TYPE,''),am.accstatus from loan_recon r,loan_appparameter l, accountmaster am  where r.acno=l.acno and r.acno=am.acno and  r.trantype in(0,1,2) and r.ty=0 " + npaQuery + " and dt between '" + frmdt + "' and '" + todt + "' "
                                + "and am.accttype='" + acType + "' group by r.acno,l.sanctionlimit, am.custname order by r.acno").getResultList();
                    } else {
                        result = em.createNativeQuery("select r.acno ,l.sanctionlimit, am.custname,l.recover,ifnull(am.CUST_TYPE,''),am.accstatus from loan_recon r,loan_appparameter l, accountmaster am  where r.acno=l.acno and r.acno=am.acno and  r.trantype in(0,1,2) and r.ty=0 " + npaQuery + " and dt between '" + frmdt + "' and '" + todt + "' and am.accttype='" + acType + "' and am.curbrcode='" + branchCode + "' group by r.acno,l.sanctionlimit, am.custname order by r.acno").getResultList();
                    }
                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            LoanRecoveryTable pojo = new LoanRecoveryTable();
                            vtrmain = (Vector) result.get(j);
                            acNo = vtrmain.get(0).toString();
                            sansonLimit = Double.parseDouble(vtrmain.get(1).toString());
                            custName = vtrmain.get(2).toString();
                            String recoverType = vtrmain.get(3).toString();
                            String custType = vtrmain.get(4).toString();
                            String accStatus = vtrmain.get(5).toString();
                            balance = openingBalance(acNo, todt);
                            // opening princ bal
                            double princOpBal = 0, totalRecoverAmt = 0, princRec = 0, openingInt = 0, npaOpeningInt = 0;
                            if (recoverType.equalsIgnoreCase("PIC")) {
                                if (custType.equalsIgnoreCase("ST") && bnkCode.equalsIgnoreCase("hcbl")) {
                                    prinOpbalList = em.createNativeQuery("select cast(sum(pOpBal) as decimal(25,2)) as princBal from("
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' and trantype <> 8 "
                                            + "union all "
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' and trantype <> 8"
                                            + ")a").getResultList();
                                    Vector vtr = (Vector) prinOpbalList.get(0);
                                    princOpBal = Double.parseDouble(vtr.get(0).toString());
                                    if (princOpBal > 0) {
                                        princOpBal = 0;
                                    }
                                } else {
                                    prinOpbalList = em.createNativeQuery("select cast(sum(pOpBal) as decimal(25,2)) as princBal from("
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acNo + "' and dt< '" + frmdt + "'  "
                                            + "union all "
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' "
                                            + ")a").getResultList();

                                    Vector vtr = (Vector) prinOpbalList.get(0);
                                    princOpBal = Double.parseDouble(vtr.get(0).toString());
                                }
                            } else {
                                if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {

                                    intOpbalList = em.createNativeQuery("select sum(intOpBal) from ("
                                            + "select ifnull(sum(cramt-Dramt),0) intOpBal from npa_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' )b").getResultList();
                                    Vector vtr2 = (Vector) intOpbalList.get(0);
                                    npaOpeningInt = Math.abs(Double.parseDouble(vtr2.get(0).toString()));

                                } else {

                                    prinOpbalList = em.createNativeQuery("select sum(pOpBal) from("
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' and trantype <> 8 "
                                            + ")a").getResultList();
                                    Vector vtr1 = (Vector) prinOpbalList.get(0);
                                    princOpBal = Double.parseDouble(vtr1.get(0).toString());
                                    if (princOpBal > 0) {
                                        princOpBal = 0;
                                    }

                                    intOpbalList = em.createNativeQuery("select sum(pOpBal) from("
                                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' and trantype=8 "
                                            + ")a").getResultList();
                                    vtr1 = (Vector) intOpbalList.get(0);
                                    openingInt = Math.abs(Double.parseDouble(vtr1.get(0).toString()));

                                    prinOpbalList = em.createNativeQuery("select ifnull(sum(Cramt),0) as creditAmt from loan_recon where acno = '" + acNo + "' and dt< '" + frmdt + "' ").getResultList();
                                    vtr1 = (Vector) prinOpbalList.get(0);
                                    double creditAmt = Double.parseDouble(vtr1.get(0).toString());
                                    if (openingInt >= creditAmt) {
                                        openingInt = openingInt - creditAmt;
                                    } else {
                                        openingInt = 0;
                                    }
                                }
                            }

                            List result1 = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon  where  trantype =0 and ty=0 and dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result1.isEmpty()) {
                                vtrsub = (Vector) result1.get(0);
                                amtCash = Double.parseDouble(vtrsub.get(0).toString());
                            }
                            List result2 = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon  where   trantype =1 and ty=0 and dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result2.isEmpty()) {
                                vtrsub = (Vector) result2.get(0);
                                amtClearing = Double.parseDouble(vtrsub.get(0).toString());
                            }
                            List result3 = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon  where   trantype =1 and ty=1 and details like '%ret%' and  dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result3.isEmpty()) {
                                vtrsub = (Vector) result3.get(0);
                                amtClearingDr = Double.parseDouble(vtrsub.get(0).toString());
                            }
                            List result4 = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon  where  trantype =2 and ty=0 and dt between  '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result4.isEmpty()) {
                                vtrsub = (Vector) result4.get(0);
                                amtTransfer = Double.parseDouble(vtrsub.get(0).toString());
                            }

                            totalRecoverAmt = (amtCash + amtClearing + amtTransfer) - amtClearingDr;

                            List result5 = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon   where dt <= '" + todt + "'  and trantype <> 8 and trandesc <15 and acno='" + acNo + "'").getResultList();
                            if (!result5.isEmpty()) {
                                vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                            }

                            List result6 = em.createNativeQuery("select ifnull(sum(cramt),0) from npa_recon  where  dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "'").getResultList();
                            if (!result6.isEmpty()) {
                                vtrsub = (Vector) result6.get(0);
                                npaRec = Double.parseDouble(vtrsub.get(0).toString());
                            }

                            List result7 = em.createNativeQuery("select ifnull(sum(a.intamt),0) as intamt from "
                                    + "(select sum(ifnull(dramt,0)) as intamt from loan_recon where  dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "' and (trantype in (8) or trandesc in (3,4))  "
                                    + "union all "
                                    + "select sum(ifnull(dramt,0)) as intamt from npa_recon where dt between '" + frmdt + "' and '" + todt + "' and acno='" + acNo + "' and (trantype in (8) or trandesc in (3,4)) ) a").getResultList();
                            if (!result7.isEmpty()) {
                                vtrsub = (Vector) result7.get(0);
                                double intPosted = Double.parseDouble(vtrsub.get(0).toString());
                                double totalLoanRec = ((amtCash + amtClearing - amtClearingDr + amtTransfer) - npaRec) <= 0 ? 0d : ((amtCash + amtClearing - amtClearingDr + amtTransfer) - npaRec);
                                intRec = (totalLoanRec >= intPosted) ? intPosted : totalLoanRec;
                            }
                            if (recoverType.equalsIgnoreCase("PIC")) {
                                intRec = 0;
                                npaRec = 0;
                                if (princOpBal == 0) {
                                    intRec = totalRecoverAmt;
                                } else {
                                    if (Math.abs(princOpBal) >= totalRecoverAmt) {
                                        princRec = totalRecoverAmt;
                                    } else {
                                        princRec = Math.abs(princOpBal);
                                        intRec = totalRecoverAmt - Math.abs(princOpBal);
                                    }
                                }
                            } else {
                                if (bnkCode.equalsIgnoreCase("hcbl")) {
                                    intRec = 0;
                                    npaRec = 0;
                                    if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {
                                        if (npaOpeningInt == 0) {
                                            //intRec = totalRecoverAmt;
                                            if (openingInt >= totalRecoverAmt) {
                                                intRec = totalRecoverAmt;
                                            } else {
                                                intRec = openingInt;
                                                princRec = totalRecoverAmt - openingInt;
                                            }
                                        } else {
                                            if (npaOpeningInt >= totalRecoverAmt) {
                                                npaRec = totalRecoverAmt;
                                            } else {
                                                if (openingInt == 0) {
                                                    npaRec = npaOpeningInt;
                                                    princRec = totalRecoverAmt - npaOpeningInt;
                                                } else {
                                                    npaRec = npaOpeningInt;
                                                    double leftRecoverAmt = totalRecoverAmt - npaOpeningInt;
                                                    if (openingInt >= leftRecoverAmt) {
                                                        intRec = leftRecoverAmt;
                                                    } else {
                                                        princRec = leftRecoverAmt - openingInt;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (openingInt == 0) {
                                            princRec = totalRecoverAmt;
                                        } else {
                                            if (openingInt >= totalRecoverAmt) {
                                                intRec = totalRecoverAmt;
                                            } else {
                                                intRec = Math.abs(openingInt);
                                                princRec = totalRecoverAmt - Math.abs(openingInt);
                                            }
                                        }
                                    }

                                } else {
                                    //Jasper code implement
//                                ($F{amtCash}.add( $F{amtClearing}.add( $F{amtTransfer}.subtract( $F{amtClearingdr} ) ) ).subtract($F{npaRec}).subtract($F{intRec})).doubleValue()
//                                <0?new BigDecimal("0")
//                                :($F{amtCash}.add( $F{amtClearing}.add( $F{amtTransfer}.subtract( $F{amtClearingdr} ) ) ).subtract($F{npaRec}).subtract($F{intRec}))
                                    princRec = (((amtCash + amtClearing + amtTransfer) - amtClearingDr) - npaRec) - intRec < 0 ? 0d : (((amtCash + amtClearing + amtTransfer) - amtClearingDr) - npaRec) - intRec;
                                }
                            }

                            pojo.setAccountNumber(acNo);
                            pojo.setAcDesc(common.getAcctDecription(acNo.substring(2, 4)));
                            pojo.setName(custName);
                            float roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(new BigDecimal(sansonLimit).abs().toString()), todt, acNo));
                            pojo.setRoi(roi);
                            pojo.setAmountSanc(BigDecimal.valueOf(sansonLimit));
                            pojo.setAmtCash(BigDecimal.valueOf(amtCash));
                            pojo.setAmtClearing(BigDecimal.valueOf(amtClearing));
                            pojo.setAmtClearingdr(BigDecimal.valueOf(amtClearingDr));
                            pojo.setAmtDis(BigDecimal.valueOf(amtDis));
                            pojo.setAmtTransfer(BigDecimal.valueOf(amtTransfer));
                            pojo.setBalance(BigDecimal.valueOf(Double.parseDouble(df.format(balance))));
                            pojo.setNpaRec(BigDecimal.valueOf(npaRec));
                            pojo.setIntRec(BigDecimal.valueOf(intRec));
                            pojo.setPrinRec(BigDecimal.valueOf(princRec));
                            pojo.setRecoverType(recoverType);
                            finalList.add(pojo);
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return finalList;
    }

    public List<LoanRecoveryTable> getLoanRecoveryWithPercentage(String acType, String acNature, String frDt, String toDt, String Percent, String brnCode, String mode) throws ApplicationException {
        List<LoanRecoveryTable> finalList = new ArrayList<LoanRecoveryTable>();
        try {
            DecimalFormat df = new DecimalFormat("#.##");
            String brnQuery = "";
            if (brnCode.equalsIgnoreCase("A") || brnCode.equalsIgnoreCase("90")) {
                brnQuery = "";
            } else {
                brnQuery = " and substring(aa.acno,1,2)= '" + brnCode + "'";
            }
            String acCodeQuery = "";
            if (acNature.equalsIgnoreCase("ALL")) {
                acCodeQuery = "  acctnature in('CA','DL','TL') ";
            } else {
                if (acType.equalsIgnoreCase("All")) {
                    acCodeQuery = "  acctnature in ('" + acNature + "') ";
                } else {
                    acCodeQuery = "  acctnature in ('" + acNature + "') and AcctCode  ='" + acType + "' ";
                }
            }
            String modeQuery = "";
            if (mode.equalsIgnoreCase("R")) {
                modeQuery = " where aa.odPerc<= (aa.crAmt+aa.amtClearing+ aa.amtClearingDR+ aa.amtTransfer-aa.npaRec)";
            } else if (mode.equalsIgnoreCase("U")) {
                modeQuery = " where aa.odPerc > (aa.crAmt+aa.amtClearing+ aa.amtClearingDR+ aa.amtTransfer-aa.npaRec)";
            }
            List accountList = null;

            accountList = em.createNativeQuery("select aa.acno,aa.custname, aa.odlimit, aa.odPerc, aa.crAmt,aa.amtClearing, aa.amtClearingDR, aa.amtTransfer, aa.amtDisbursed,aa.npaRec,  aa.intResc from ( "
                    + "select acno,custname, odlimit, odlimit*'" + Percent + "'/100 as odPerc,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from ca_recon  where  trantype =0 and ty=0 and dt between '" + frDt + "' and '" + toDt + "' and acno=a.acno ) as crAmt,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from ca_recon   where  trantype =1 and ty=0 and dt between  '" + frDt + "' and '" + toDt + "' and acno=a.acno) as amtClearing,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from ca_recon   where  trantype =1 and ty=1 and dt between  '" + frDt + "' and '" + toDt + "' and acno=a.acno) as amtClearingDR,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from ca_recon   where  trantype =2 and ty=0 and dt between  '" + frDt + "' and '" + toDt + "' and acno=a.acno) as amtTransfer,"
                    + "(select cast(ifnull(sum(dramt),0) as decimal(25,2)) from ca_recon   where dt between '" + frDt + "' and '" + toDt + "'  and trantype <> 8 and trandesc <15 and acno=a.acno) as amtDisbursed,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from npa_recon  where dt between '" + frDt + "' and '" + toDt + "' and ACNo=a.acno) as npaRec,"
                    + "(select ifnull(sum(r.intamt),0) as intamt from "
                    + "(select acno, sum(ifnull(dramt,0)) as intamt from ca_recon where  dt between '" + frDt + "' and '" + toDt + "'  and (trantype in (8) or trandesc in (3,4)) group by acno  "
                    + "union all "
                    + "select acno, sum(ifnull(dramt,0)) as intamt from npa_recon where dt between '" + frDt + "' and '" + toDt + "' and (trantype in (8) or trandesc in (3,4)) group  by acno) r where r.acno = a.acno) as intResc"
                    + " from accountmaster a where  substring(acno,3,2) in (select acctcode from accounttypemaster where " + acCodeQuery + " and crdbflag in ('B','D') ) "
                    + "and OpeningDt <='" + toDt + "' and (ClosingDate is null or ClosingDate = '' or ClosingDate>'" + toDt + "') "
                    + "union all "
                    + "select acno,custname, odlimit, odlimit*'25'/100 as odPerc,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from loan_recon  where  trantype =0 and ty=0 and dt between '" + frDt + "' and '" + toDt + "' and acno=a.acno ) as crAmt,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from loan_recon   where  trantype =1 and ty=0 and dt between  '" + frDt + "' and '" + toDt + "' and acno=a.acno) as amtClearing,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from loan_recon   where  trantype =1 and ty=1 and dt between  '" + frDt + "' and '" + toDt + "' and acno=a.acno) as amtClearingDR,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from loan_recon   where  trantype =2 and ty=0 and dt between  '" + frDt + "' and '" + toDt + "' and acno=a.acno) as amtTransfer,"
                    + "(select cast(ifnull(sum(dramt),0) as decimal(25,2)) from loan_recon   where dt between '" + frDt + "' and '" + toDt + "'  and trantype <> 8 and trandesc <15 and acno=a.acno) as amtDisbursed,"
                    + "(select cast(ifnull(sum(cramt),0) as decimal(25,2)) from npa_recon  where dt between '" + frDt + "' and '" + toDt + "' and ACNo=a.acno) as npaRec,"
                    + "(select ifnull(sum(r.intamt),0) as intamt from "
                    + "(select acno, sum(ifnull(dramt,0)) as intamt from loan_recon where  dt between '" + frDt + "' and '" + toDt + "'  and (trantype in (8) or trandesc in (3,4)) group by acno  "
                    + " union all "
                    + "select acno, sum(ifnull(dramt,0)) as intamt from npa_recon where dt between '" + frDt + "' and '" + toDt + "' and (trantype in (8) or trandesc in (3,4)) group  by acno) r where r.acno = a.acno) as intResc"
                    + " from accountmaster a where  substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('TL','DL') and crdbflag in ('B','D') ) "
                    + "and OpeningDt <='" + toDt + "' and (ClosingDate is null or ClosingDate = '' or ClosingDate>'" + toDt + "') ) aa " + modeQuery + "  " + brnQuery + "").getResultList();

            if (!accountList.isEmpty()) {
                for (int i = 0; i < accountList.size(); i++) {
                    LoanRecoveryTable pojo = new LoanRecoveryTable();
                    Vector vtr = (Vector) accountList.get(i);
                    String accountNo = vtr.get(0).toString();
                    String customerName = vtr.get(1).toString();
                    String odlimit = vtr.get(2).toString();
                    String odPercentage = vtr.get(3).toString();
                    double creditAmt = Double.parseDouble(vtr.get(4).toString());
                    double clearingAmt = Double.valueOf(vtr.get(5).toString());
                    double clearingDebit = Double.valueOf(vtr.get(6).toString());
                    double transferAmt = Double.valueOf(vtr.get(7).toString());
                    double disbursedAmt = Double.valueOf(vtr.get(8).toString());
                    double npaRec = Double.parseDouble(vtr.get(9).toString());
                    double intPosted = Double.valueOf(vtr.get(10).toString());
                    double totalLoanRec = ((creditAmt + clearingAmt - clearingDebit + transferAmt) - npaRec) <= 0 ? 0d : ((creditAmt + clearingAmt - clearingDebit + transferAmt) - npaRec);
                    double intRec = (totalLoanRec >= intPosted) ? intPosted : totalLoanRec;
                    double balance = 0d;
                    balance = openingBalance(accountNo, toDt);

                    pojo.setAccountNumber(accountNo);
                    pojo.setAcDesc(common.getAcctDecription(accountNo.substring(2, 4)));
                    pojo.setAmountSanc(new BigDecimal(odlimit));
                    pojo.setName(customerName);
                    pojo.setAmtCash(new BigDecimal(creditAmt));
                    pojo.setAmtClearing(new BigDecimal(clearingAmt));
                    pojo.setAmtClearingdr(new BigDecimal(clearingDebit));
                    pojo.setAmtDis(new BigDecimal(disbursedAmt));
                    pojo.setAmtTransfer(new BigDecimal(transferAmt));
                    pojo.setBalance(new BigDecimal(Double.parseDouble(df.format(balance))));
                    pojo.setIntRec(new BigDecimal(intRec));
                    pojo.setNpaRec(new BigDecimal(npaRec));

                    finalList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finalList;
    }

    public List<LoanAcDetailsTable> loanAcDetailsReport(String acctCode, String tillDt, String branchCode) throws ApplicationException {
        List<LoanAcDetailsTable> finalList = new ArrayList<LoanAcDetailsTable>();
        List<String> acctypeList = new ArrayList<String>();
        List<String> acctnaturList = new ArrayList<String>();
        String acType, acNo, custName, sanctionDt, odSanctionChangeDt, presentStatus, expiryDt, disbDt = null, custId, sector, brnCode, productCode, category;
        String acctNature, docExpDt, renewalDt;
        int loanPd, jointNo = 0;
        Vector vtrmain, vtrsub;
        List acctCodeNatureList;
        DecimalFormat df = new DecimalFormat("#.##");
        Map<String, String> accnatureMap = new HashMap<String, String>();
        double amtCash = 0d, amtClearing = 0d, amtClearingDr = 0d, amtTransfer = 0d, amtDis = 0d, sansonLimit = 0d, balance = 0d, odLimit = 0d;
        try {
            /*
             * In Parameterinfo_report the ReportName = LOAN A/C DETAILS REPORT and 
             * if CODE = 1 Means AcctCode '01','02' NOT INVOLVED
             * if CODE = 0 Means AcctCode '01','02' INVOLVED
             */

            /* Code Comment 
             String query = " ";
             List codeList = em.createNativeQuery("select code from parameterinfo_report where reportname = 'LOAN AC DETAILS'").getResultList();
             if (codeList.size() > 0) {
             Vector codeVect = (Vector) codeList.get(0);
             String code = codeVect.get(0).toString();
             if (code.equalsIgnoreCase("1")) {
             query = "and acctcode not in ('" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "') ";
             }
             } */
            if (acctCode.equalsIgnoreCase("ALL")) {
//                List acctCodeNatureList = em.createNativeQuery("select acctcode,acctnature from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "') " + query + " order by acctcode").getResultList();
                acctCodeNatureList = em.createNativeQuery("select acctcode,acctnature from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "') And CrDbFlag in('B','D') order by acctnature, acctcode").getResultList();
//                for (int i = 0; i < acctCodeNatureList.size(); i++) {
//                    Vector vtr = (Vector) acctCodeNatureList.get(i);
//                    accnatureMap.put(vtr.get(0).toString(), vtr.get(1).toString());
//                }
            } else {
                acctCodeNatureList = em.createNativeQuery("select acctcode,acctnature from accounttypemaster where acctcode in('" + acctCode + "') order by acctcode").getResultList();
//                for (int i = 0; i < acctCodeNatureList.size(); i++) {
//                    Vector vtr = (Vector) acctCodeNatureList.get(i);
//                    accnatureMap.put(vtr.get(0).toString(), vtr.get(1).toString());
//                }
            }
//            Set<Map.Entry<String, String>> set = accnatureMap.entrySet();
//            for (Map.Entry<String, String> me : set) {
            for (int i = 0; i < acctCodeNatureList.size(); i++) {
                Vector vtr = (Vector) acctCodeNatureList.get(i);
                acType = vtr.get(0).toString();
                acctNature = vtr.get(1).toString();

                if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List result;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        result = em.createNativeQuery("select zz.acno, zz.Sanctionlimit,zz.OpeningDt,IFNULL(cbk.description,'OPERATIVE' ) as presentstatus,zz.custname,zz.loan_pd_month,zz.ODLimit,zz.sanctionlimitdt,zz.CustId, "
                                + " zz.REF_DESC,zz.INTEREST_TABLE_CODE,zz.branchcode,zz.APPLICANT_CATEGORY,zz.DOCUMENT_EXP_DATE,zz.RENEWAL_DATE,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '183'  and REF_CODE = zz.sub_sector  and '" + tillDt + "'  between eff_fr_dt and eff_to_dt  ),'') as subSector,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '186'  and REF_CODE = zz.type_of_advance),'') as typeOfAdvance  ,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '209'  and REF_CODE = zz.CATEGORY_OPT),'') as categoryOpt, zz.TURN_OVER_DETAIL_FLAG, jt.joinDetails   from ("
                                + " select r.acno ,l.sanctionlimit,ifnull(date_format(am.OpeningDt,'%Y-%m-%d'), l.sanctionlimitdt) as OpeningDt, ifnull(l.presentstatus,'') as presentstatus, am.custname, ifnull(cb.loan_pd_month,0) as loan_pd_month, ifnull(l.ODLimit,0) as ODLimit,  ifnull(date_format(l.sanctionlimitdt,'%Y-%m-%d'), '1900-01-01') as sanctionlimitdt, ci.CustId, ref.REF_DESC,cb.INTEREST_TABLE_CODE,substring(r.acno,1,2) as branchcode,clb.APPLICANT_CATEGORY, ifnull(date_format(clb.DOCUMENT_EXP_DATE,'%Y-%m-%d'), '1900-01-01') as DOCUMENT_EXP_DATE, ifnull(date_format(clb.RENEWAL_DATE,'%Y-%m-%d'), '1900-01-01')  as RENEWAL_DATE ,clb.sub_sector,clb.TYPE_OF_ADVANCE  ,clb.CATEGORY_OPT ,ifnull(sg.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG  from ca_recon r,loan_appparameter l,accountmaster am, cbs_loan_acc_mast_sec cb, customerid ci, cbs_loan_borrower_details clb, cbs_ref_rec_type ref, cbs_scheme_general_scheme_parameter_master sg where cb.SCHEME_CODE = sg.scheme_code and r.acno=l.acno and r.acno=am.acno and cb.acno = am.acno and am.acno = ci.acno and  am.ACNo = clb.ACC_NO and dt <= '" + tillDt + "' and am.accttype='" + acType + "' and am.OpeningDt <= '" + tillDt + "' and (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + tillDt + "') and ref.ref_rec_no='182' and clb.SECTOR = ref.REF_CODE group by r.acno ,l.sanctionlimit,l.sanctionlimitdt, l.presentstatus, am.custname, cb.loan_pd_month ,l.ODLimit) zz "
                                + " left join "
                                + " (select ast.acno as acno,ast.effdt as npaEffDt, cast(ast.spflag as decimal) as npaSpflag  from accountstatus ast, "
                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <='" + tillDt + "'  group by acno) b "
                                + " where aa.acno = b.ano and aa.effdt = b.dt "
                                + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno) npa "
                                + " on npa.acno = zz.acno "
                                + " left join codebook cbk on npa.npaSpflag = cbk.code and cbk.groupcode = 3 "
                                + " left join (select a.acno, a.custname, "
                                + " CONCAT(ifnull(if((a.custid1<>null or a.custid1<>''),CONCAT('[',a.custid1,'] ',aa.CustFullName,''),''),''),"
                                + " ifnull(if((a.custid2<>null or a.custid2<>''),CONCAT('\n[',a.custid2,'] ',bb.CustFullName,' '),''),''), "
                                + " ifnull(if((a.custid3<>null or a.custid3<>''),CONCAT('\n[',a.custid3,'] ',cc.CustFullName,' '),''),''), "
                                + " ifnull(if((a.custid4<>null or a.custid4<>''),CONCAT('\n[',a.custid4,'] ',dd.CustFullName,' '),''),'')) as joinDetails "
                                + " from accountmaster a "
                                + " left join cbs_customer_master_detail aa on a.custid1 = aa.customerid "
                                + " left join cbs_customer_master_detail bb on a.custid2 = bb.customerid "
                                + " left join cbs_customer_master_detail cc on a.custid3 = cc.customerid "
                                + " left join cbs_customer_master_detail dd on a.custid4 = dd.customerid "
                                + " where (a.custid1<> null or a.custid1<>'') or (a.custid2<> null or a.custid2<>'') or (a.custid3<> null or a.custid3<>'') or (a.custid4<> null or a.custid4<>'')) jt on jt.acno = zz.acno  "
                                + " order by zz.acno  ").getResultList();
                    } else {
                        result = em.createNativeQuery("select zz.acno, zz.Sanctionlimit,zz.OpeningDt,IFNULL(cbk.description,'OPERATIVE' ) as presentstatus,zz.custname,zz.loan_pd_month,zz.ODLimit,zz.sanctionlimitdt,zz.CustId, "
                                + " zz.REF_DESC,zz.INTEREST_TABLE_CODE,zz.branchcode,zz.APPLICANT_CATEGORY,zz.DOCUMENT_EXP_DATE,zz.RENEWAL_DATE,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '183'  and REF_CODE = zz.sub_sector  and '" + tillDt + "'  between eff_fr_dt and eff_to_dt  ),'') as subSector,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '186'  and REF_CODE = zz.type_of_advance),'') as typeOfAdvance  ,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '209'  and REF_CODE = zz.CATEGORY_OPT),'') as categoryOpt, zz.TURN_OVER_DETAIL_FLAG, jt.joinDetails   from ("
                                + " select r.acno ,l.sanctionlimit,ifnull(date_format(am.OpeningDt,'%Y-%m-%d'), l.sanctionlimitdt) as OpeningDt, ifnull(l.presentstatus,'') as presentstatus, am.custname, ifnull(cb.loan_pd_month,0) as loan_pd_month, ifnull(l.ODLimit,0) as ODLimit,  ifnull(date_format(l.sanctionlimitdt,'%Y-%m-%d'), '1900-01-01') as sanctionlimitdt, ci.CustId, ref.REF_DESC,cb.INTEREST_TABLE_CODE,substring(r.acno,1,2) as branchcode,clb.APPLICANT_CATEGORY, ifnull(date_format(clb.DOCUMENT_EXP_DATE,'%Y-%m-%d'), '1900-01-01') as DOCUMENT_EXP_DATE, ifnull(date_format(clb.RENEWAL_DATE,'%Y-%m-%d'), '1900-01-01')  as RENEWAL_DATE ,clb.sub_sector,clb.TYPE_OF_ADVANCE  ,clb.CATEGORY_OPT ,ifnull(sg.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG from ca_recon r,loan_appparameter l,accountmaster am, cbs_loan_acc_mast_sec cb, customerid ci, cbs_loan_borrower_details clb, cbs_ref_rec_type ref, cbs_scheme_general_scheme_parameter_master sg where cb.SCHEME_CODE = sg.scheme_code and r.acno=l.acno and r.acno=am.acno and cb.acno = am.acno and am.acno = ci.acno and  am.ACNo = clb.ACC_NO and dt <= '" + tillDt + "' and am.accttype='" + acType + "' and am.OpeningDt <= '" + tillDt + "' and (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + tillDt + "') and ref.ref_rec_no='182' and clb.SECTOR = ref.REF_CODE and am.curbrcode='" + branchCode + "' group by r.acno ,l.sanctionlimit,l.sanctionlimitdt, l.presentstatus, am.custname, cb.loan_pd_month ,l.ODLimit  ) zz "
                                + " left join "
                                + " (select ast.acno as acno,ast.effdt as npaEffDt, cast(ast.spflag as decimal) as npaSpflag  from accountstatus ast, "
                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <='" + tillDt + "'  group by acno) b "
                                + " where aa.acno = b.ano and aa.effdt = b.dt "
                                + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno) npa "
                                + " on npa.acno = zz.acno "
                                + " left join codebook cbk on npa.npaSpflag = cbk.code and cbk.groupcode = 3 "
                                + " left join (select a.acno, a.custname, "
                                + " CONCAT(ifnull(if((a.custid1<>null or a.custid1<>''),CONCAT('[',a.custid1,'] ',aa.CustFullName,''),''),''),"
                                + " ifnull(if((a.custid2<>null or a.custid2<>''),CONCAT('\n[',a.custid2,'] ',bb.CustFullName,' '),''),''), "
                                + " ifnull(if((a.custid3<>null or a.custid3<>''),CONCAT('\n[',a.custid3,'] ',cc.CustFullName,' '),''),''), "
                                + " ifnull(if((a.custid4<>null or a.custid4<>''),CONCAT('\n[',a.custid4,'] ',dd.CustFullName,' '),''),'')) as joinDetails "
                                + " from accountmaster a "
                                + " left join cbs_customer_master_detail aa on a.custid1 = aa.customerid "
                                + " left join cbs_customer_master_detail bb on a.custid2 = bb.customerid "
                                + " left join cbs_customer_master_detail cc on a.custid3 = cc.customerid "
                                + " left join cbs_customer_master_detail dd on a.custid4 = dd.customerid "
                                + " where (a.custid1<> null or a.custid1<>'') or (a.custid2<> null or a.custid2<>'') or (a.custid3<> null or a.custid3<>'') or (a.custid4<> null or a.custid4<>'')) jt on jt.acno = zz.acno  "
                                + " order by zz.acno  ").getResultList();
                    }

                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            LoanAcDetailsTable pojo = new LoanAcDetailsTable();
                            vtrmain = (Vector) result.get(j);
                            acNo = vtrmain.get(0).toString();
                            sansonLimit = Double.parseDouble(vtrmain.get(1).toString());
                            sanctionDt = vtrmain.get(2).toString();
                            String intCalcBasedOnSecurity = vtrmain.get(18).toString();
                            if (vtrmain.get(3).toString().equalsIgnoreCase("DOUBTFUL")) {
                                long dayDiff = 0;
                                presentStatus = "";
                                List limitList = em.createNativeQuery("select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acNo + "'").getResultList();
                                Vector limitVec = (Vector) limitList.get(0);
                                String npaDt = limitVec.get(0).toString();
                                limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                                        + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                                        + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                                        + "order by days").getResultList();
                                if (!limitList.isEmpty()) {
                                    dayDiff = CbsUtil.dayDiff(ymdFormat.parse(npaDt), ymdFormat.parse(tillDt));
                                    for (int t = 0; t < limitList.size(); t++) {
                                        limitVec = (Vector) limitList.get(t);
                                        if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                            if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                presentStatus = vtrmain.get(3).toString().concat("-" + "D1");
                                                break;
                                            }
                                        } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                            if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                presentStatus = vtrmain.get(3).toString().concat("-" + "D2");
                                                break;
                                            }
                                        } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                            presentStatus = vtrmain.get(3).toString().concat("-" + "D3");
                                            break;
                                        }
                                    }
                                }
                            } else {
                                presentStatus = vtrmain.get(3).toString();
                            }
                            custName = vtrmain.get(4).toString();
                            loanPd = Integer.parseInt(vtrmain.get(5).toString());
                            odLimit = Double.parseDouble(vtrmain.get(6).toString());
                            odSanctionChangeDt = vtrmain.get(7).toString();
                            balance = openingBalance(acNo, tillDt);
                            custId = vtrmain.get(8).toString();
                            sector = vtrmain.get(9).toString();
                            brnCode = vtrmain.get(11).toString();
                            productCode = vtrmain.get(10).toString();
                            category = vtrmain.get(12).toString();
                            docExpDt = vtrmain.get(13).toString();
                            renewalDt = vtrmain.get(14).toString();

                            // List result5 = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0), date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y')  from ca_recon   where dt <= '" + tillDt + "'   and trandesc in (6,0)  and acno='" + acNo + "'").getResultList();

                            List result5 = em.createNativeQuery("select amtDis,disbDt,JointNo from \n"
                                    + "(select ifnull(sum(ifnull(dramt,0)),0) amtDis, date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y') disbDt  from ca_recon  "
                                    + "where dt <= '" + tillDt + "'   and trandesc in (6,0)  and acno='" + acNo + "')a, "
                                    + "( "
                                    + "select  count(custid) as JointNo  from (select ifnull(custid1,'') as custid  from accountmaster where acno='" + acNo + "' "
                                    + "union all select ifnull(custid2,'') as custid  from accountmaster where acno='" + acNo + "' "
                                    + "union all select ifnull(custid3,'') as custid  from accountmaster where acno='" + acNo + "' "
                                    + "union all select ifnull(custid4,'') as custid  from accountmaster where acno='" + acNo + "' "
                                    + ") a where  custid <>'')b").getResultList();
                            if (!result5.isEmpty()) {
                                vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                disbDt = vtrsub.get(1).toString();
                                jointNo = Integer.parseInt(vtrsub.get(2).toString());
                            }

                            double instalAmt = 0d;
                            List result6 = em.createNativeQuery("select distinct(installamt) from emidetails where acno = '" + acNo + "' and  sno = (select max(sno) from emidetails where acno = '" + acNo + "'  and duedt<='" + tillDt + "')").getResultList();
                            if (!result6.isEmpty()) {
                                Vector vist = (Vector) result6.get(0);
                                instalAmt = Double.parseDouble(vist.get(0).toString());
                            } else {
                                result6 = em.createNativeQuery("select distinct(installamt) from emidetails where acno = '" + acNo + "' ").getResultList();
                                if (!result6.isEmpty()) {
                                    Vector vist = (Vector) result6.get(0);
                                    instalAmt = Double.parseDouble(vist.get(0).toString());
                                }
                            }
                            List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                    + "where acno =  '" + acNo + "' and txnid = "
                                    + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + tillDt + "' )").getResultList();
                            if (!sanctionLimitDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionLimitDtList.get(0);
                                odSanctionChangeDt = vist.get(0).toString();
                                odLimit = Double.parseDouble(vist.get(1).toString());
                            }
                            String lastCrDt = "";
                            List tempList = em.createNativeQuery("select max(dt) from ca_recon where acno='" + acNo + "' and  cramt>0 and dt <= '" + tillDt + "'").getResultList();
                            if (!tempList.isEmpty()) {
                                Vector ele = (Vector) tempList.get(0);
                                if (ele.get(0) != null) {
                                    lastCrDt = ymdFormat.format((Date) ele.get(0));
                                }
                            }
                            BigDecimal lastCrAmt = new BigDecimal("0");
                            BigDecimal npaOir = new BigDecimal("0");
                            tempList = em.createNativeQuery("select LastCrAmt,NpaOri from ( "
                                    + "(select ifnull(sum(cramt),0) LastCrAmt from ca_recon where acno='" + acNo + "' and dt='" + lastCrDt + "')a, "
                                    + "(select ifnull(sum(cramt-dramt),0) NpaOri from npa_recon where acno = '" + acNo + "' and dt <= '" + tillDt + "')b "
                                    + ")").getResultList();
                            if (!tempList.isEmpty()) {
                                Vector ele = (Vector) tempList.get(0);
                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                    lastCrAmt = new BigDecimal(ele.get(0).toString());
                                }
                                npaOir = new BigDecimal(ele.get(1).toString());
                            }
                            expiryDt = dmyFormat.format(ymdFormat.parse(CbsUtil.monthAdd(ymdFormat.format(y_m_dFormat.parse(sanctionDt).compareTo(y_m_dFormat.parse(odSanctionChangeDt)) < 0 ? y_m_dFormat.parse(odSanctionChangeDt) : y_m_dFormat.parse(sanctionDt)), loanPd)));
                            pojo.setAccountNumber(acNo);
                            pojo.setAcDesc(common.getAcctDecription(acNo.substring(2, 4)));
                            pojo.setName(custName);
                            //float roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(new BigDecimal(sansonLimit).abs().toString()), tillDt, acNo);
                            String roi = "0";
//                            if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
//                                List roiList = em.createNativeQuery("select  aa.SecurityRoi, aa.MargineROI, aa.AppRoi, aa.IntTableCode, aa.addRoi, lienvalue from "
//                                        + "(select  ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(addRoi,0) as addRoi, lienvalue from loansecurity where acno = '" + acNo + "' and status = 'Active'  and IntTableCode is not null and entrydate <= '" + tillDt + "' and IntTableCode <>''"
//                                        + " union all "
//                                        + " select  ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(addRoi,0) as addRoi, lienvalue from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and IntTableCode is not null and entrydate <= '" + tillDt + "' and ExpiryDate > '" + tillDt + "' and IntTableCode <>'') aa order by aa.AppRoi").getResultList();
//                                if (!roiList.isEmpty()) {
//                                    Vector roiVector = (Vector) roiList.get(0);
//                                    double securityRoi = Double.parseDouble(roiVector.get(0).toString());
//                                    double additionalRoi = Double.parseDouble(roiVector.get(4).toString());
//                                    double lienValue = Double.parseDouble(roiVector.get(5).toString());
//                                    String intTableCode1 = roiVector.get(3).toString();
//                                    double roi1 = securityRoi + additionalRoi + Double.parseDouble(loanInterestCalculationBean.getROI(intTableCode1, lienValue, tillDt));
//                                    roi = String.valueOf(roi1);
//                                }
//                            } else {
                            roi = loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(new BigDecimal(sansonLimit).abs().toString()), tillDt, acNo);
//                            }
                            pojo.setRoi(Float.parseFloat(roi));
                            pojo.setAmountSanc(BigDecimal.valueOf(sansonLimit));
                            pojo.setSanctionDt(dmyFormat.format(y_m_dFormat.parse(sanctionDt)));
                            pojo.setOdSanctionChangeDt(odSanctionChangeDt.equalsIgnoreCase("1900-01-01") ? "" : dmyFormat.format(y_m_dFormat.parse(odSanctionChangeDt)));
//                            pojo.setExpiryDt(expiryDt);
                            pojo.setExpiryDt(renewalDt.equalsIgnoreCase("1900-01-01") ? "" : dmyFormat.format(y_m_dFormat.parse(renewalDt)));
                            pojo.setDisbDt(disbDt);
                            pojo.setAmtDis(BigDecimal.valueOf(amtDis));
                            pojo.setBalance(BigDecimal.valueOf(Double.parseDouble(df.format(balance))));
                            pojo.setStatus(presentStatus);
                            pojo.setLoanDuration(loanPd);
                            pojo.setCustId(custId);
                            pojo.setSector(sector);
                            pojo.setOdLimit(BigDecimal.valueOf(odLimit));
                            pojo.setInstallAmt(new BigDecimal(instalAmt));
                            pojo.setLastCrDt(lastCrDt.equalsIgnoreCase("") ? "" : dmyFormat.format(ymdFormat.parse(lastCrDt)));
                            pojo.setLastCrAmt(lastCrAmt);
                            pojo.setBrnCode(brnCode);
                            pojo.setProductCode(productCode);
                            pojo.setDocExpDt(docExpDt.equalsIgnoreCase("1900-01-01") ? "" : dmyFormat.format(y_m_dFormat.parse(docExpDt)));
                            if (category.equals("WK")) {
                                category = "Weaker";
                            } else {
                                category = "Non Weaker";
                            }
                            pojo.setCategory(category);
                            if (npaOir.doubleValue() < 0) {
                                pojo.setNpaOir(npaOir.abs());
                            } else {
                                pojo.setNpaOir(new BigDecimal("0"));
                            }
                            pojo.setAcNature(acctNature);
                            pojo.setSubSector(vtrmain.get(15).toString());
                            pojo.setTermOfAdvnc(vtrmain.get(16).toString());
                            pojo.setCategroyOpt(vtrmain.get(17).toString());
                            pojo.setJointNo(jointNo);
                            pojo.setJointDetails(vtrmain.get(19) == null ? "" : vtrmain.get(19).toString());
                            finalList.add(pojo);
                        }
                    }

                } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List result;
                    if (branchCode.equalsIgnoreCase("0A")) {
                        result = em.createNativeQuery("select zz.acno, zz.Sanctionlimit,zz.OpeningDt,IFNULL(cbk.description,'OPERATIVE' ) as presentstatus,zz.custname,zz.loan_pd_month,zz.ODLimit,zz.sanctionlimitdt,zz.CustId, "
                                + " zz.REF_DESC,zz.INTEREST_TABLE_CODE,zz.branchcode,zz.APPLICANT_CATEGORY,zz.DOCUMENT_EXP_DATE,zz.RENEWAL_DATE, zz.TURN_OVER_DETAIL_FLAG,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '183'  and REF_CODE = zz.sub_sector  and '" + tillDt + "'  between eff_fr_dt and eff_to_dt  ),'') as subSector,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '186'  and REF_CODE = zz.type_of_advance),'') as typeOfAdvance  ,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '209'  and REF_CODE = zz.CATEGORY_OPT),'') as typeOfAdvance, jt.joinDetails   from ( "
                                + " select r.acno ,l.sanctionlimit,ifnull(date_format(am.OpeningDt,'%Y-%m-%d'),l.sanctionlimitdt) as OpeningDt, ifnull(l.presentstatus,'') as presentstatus, am.custname, ifnull(cb.loan_pd_month,0) as loan_pd_month, ifnull(l.ODLimit,0) as ODLimit, ifnull(date_format(l.sanctionlimitdt,'%Y-%m-%d'), '1900-01-01') as sanctionlimitdt, ci.CustId, ref.REF_DESC ,cb.INTEREST_TABLE_CODE,substring(r.acno,1,2) as branchcode,clb.APPLICANT_CATEGORY, ifnull(date_format(clb.DOCUMENT_EXP_DATE,'%Y-%m-%d'), '1900-01-01') as DOCUMENT_EXP_DATE, ifnull(date_format(clb.RENEWAL_DATE,'%Y-%m-%d'), '1900-01-01')  as RENEWAL_DATE,ifnull(sg.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG ,clb.sub_sector,clb.TYPE_OF_ADVANCE  ,clb.CATEGORY_OPT  "
                                + " from loan_recon r,loan_appparameter l,accountmaster am, cbs_loan_acc_mast_sec cb, customerid ci, cbs_loan_borrower_details clb, cbs_ref_rec_type ref, cbs_scheme_general_scheme_parameter_master sg where cb.SCHEME_CODE = sg.scheme_code and r.acno=l.acno and r.acno=am.acno and cb.acno = am.acno and am.acno = ci.acno and  am.ACNo = clb.ACC_NO and  dt <= '" + tillDt + "' and am.accttype='" + acType + "'  and  am.OpeningDt <= '" + tillDt + "' and (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + tillDt + "')  and ref.ref_rec_no='182' and clb.SECTOR = ref.REF_CODE group by r.acno ,l.sanctionlimit,l.sanctionlimitdt, l.presentstatus, am.custname, cb.loan_pd_month , l.ODLimit  ) zz "
                                + " left join "
                                + " (select ast.acno as acno,ast.effdt as npaEffDt, cast(ast.spflag as decimal) as npaSpflag  from accountstatus ast, "
                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <='" + tillDt + "'  group by acno) b "
                                + " where aa.acno = b.ano and aa.effdt = b.dt "
                                + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno) npa "
                                + " on npa.acno = zz.acno "
                                + " left join codebook cbk on npa.npaSpflag = cbk.code and cbk.groupcode = 3 "
                                + " left join (select a.acno, a.custname, "
                                + " CONCAT(ifnull(if((a.custid1<>null or a.custid1<>''),CONCAT('[',a.custid1,'] ',aa.CustFullName,''),''),''),"
                                + " ifnull(if((a.custid2<>null or a.custid2<>''),CONCAT('\n[',a.custid2,'] ',bb.CustFullName,' '),''),''), "
                                + " ifnull(if((a.custid3<>null or a.custid3<>''),CONCAT('\n[',a.custid3,'] ',cc.CustFullName,' '),''),''), "
                                + " ifnull(if((a.custid4<>null or a.custid4<>''),CONCAT('\n[',a.custid4,'] ',dd.CustFullName,' '),''),'')) as joinDetails "
                                + " from accountmaster a "
                                + " left join cbs_customer_master_detail aa on a.custid1 = aa.customerid "
                                + " left join cbs_customer_master_detail bb on a.custid2 = bb.customerid "
                                + " left join cbs_customer_master_detail cc on a.custid3 = cc.customerid "
                                + " left join cbs_customer_master_detail dd on a.custid4 = dd.customerid "
                                + " where (a.custid1<> null or a.custid1<>'') or (a.custid2<> null or a.custid2<>'') or (a.custid3<> null or a.custid3<>'') or (a.custid4<> null or a.custid4<>'')) jt on jt.acno = zz.acno  "
                                + " order by zz.acno ").getResultList();
                    } else {
                        result = em.createNativeQuery("select zz.acno, zz.Sanctionlimit,zz.OpeningDt,IFNULL(cbk.description,'OPERATIVE' ) as presentstatus,zz.custname,zz.loan_pd_month,zz.ODLimit,zz.sanctionlimitdt,zz.CustId, "
                                + " zz.REF_DESC,zz.INTEREST_TABLE_CODE,zz.branchcode,zz.APPLICANT_CATEGORY,zz.DOCUMENT_EXP_DATE,zz.RENEWAL_DATE, zz.TURN_OVER_DETAIL_FLAG , ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '183'  and REF_CODE = zz.sub_sector  and '" + tillDt + "'  between eff_fr_dt and eff_to_dt  ),'') as subSector,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '186'  and REF_CODE = zz.type_of_advance),'') as typeOfAdvance  ,ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '209'  and REF_CODE = zz.CATEGORY_OPT),'') as typeOfAdvance, jt.joinDetails   from ("
                                + " select r.acno ,l.sanctionlimit,ifnull(date_format(am.OpeningDt,'%Y-%m-%d'), l.sanctionlimitdt) as OpeningDt, ifnull(l.presentstatus,'') as presentstatus, am.custname, ifnull(cb.loan_pd_month,0) as loan_pd_month, ifnull(l.ODLimit,0) as ODLimit, ifnull(date_format(l.sanctionlimitdt,'%Y-%m-%d'), '1900-01-01') as sanctionlimitdt, ci.CustId, ref.REF_DESC ,cb.INTEREST_TABLE_CODE,substring(r.acno,1,2) as branchcode,clb.APPLICANT_CATEGORY, ifnull(date_format(clb.DOCUMENT_EXP_DATE,'%Y-%m-%d'), '1900-01-01') as DOCUMENT_EXP_DATE, ifnull(date_format(clb.RENEWAL_DATE,'%Y-%m-%d'), '1900-01-01')  as RENEWAL_DATE,ifnull(sg.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG ,clb.sub_sector,clb.TYPE_OF_ADVANCE  ,clb.CATEGORY_OPT  "
                                + " from loan_recon r,loan_appparameter l,accountmaster am, cbs_loan_acc_mast_sec cb, customerid ci, cbs_loan_borrower_details clb, cbs_ref_rec_type ref, cbs_scheme_general_scheme_parameter_master sg where cb.SCHEME_CODE = sg.scheme_code and r.acno=l.acno and r.acno=am.acno and cb.acno = am.acno and am.acno = ci.acno and  am.ACNo = clb.ACC_NO and  dt <= '" + tillDt + "' and am.accttype='" + acType + "'  and am.OpeningDt <= '" + tillDt + "' and (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + tillDt + "')  and ref.ref_rec_no='182' and clb.SECTOR = ref.REF_CODE and am.curbrcode='" + branchCode + "' group by r.acno ,l.sanctionlimit,l.sanctionlimitdt, l.presentstatus, am.custname, cb.loan_pd_month , l.ODLimit  ) zz "
                                + " left join "
                                + " (select ast.acno as acno,ast.effdt as npaEffDt, cast(ast.spflag as decimal) as npaSpflag  from accountstatus ast, "
                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <='" + tillDt + "'  group by acno) b "
                                + " where aa.acno = b.ano and aa.effdt = b.dt "
                                + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno) npa "
                                + " on npa.acno = zz.acno "
                                + " left join codebook cbk on npa.npaSpflag = cbk.code and cbk.groupcode = 3 "
                                + " left join (select a.acno, a.custname, "
                                + " CONCAT(ifnull(if((a.custid1<>null or a.custid1<>''),CONCAT('[',a.custid1,'] ',aa.CustFullName,''),''),''),"
                                + " ifnull(if((a.custid2<>null or a.custid2<>''),CONCAT('\n[',a.custid2,'] ',bb.CustFullName,' '),''),''), "
                                + " ifnull(if((a.custid3<>null or a.custid3<>''),CONCAT('\n[',a.custid3,'] ',cc.CustFullName,' '),''),''), "
                                + " ifnull(if((a.custid4<>null or a.custid4<>''),CONCAT('\n[',a.custid4,'] ',dd.CustFullName,' '),''),'')) as joinDetails "
                                + " from accountmaster a "
                                + " left join cbs_customer_master_detail aa on a.custid1 = aa.customerid "
                                + " left join cbs_customer_master_detail bb on a.custid2 = bb.customerid "
                                + " left join cbs_customer_master_detail cc on a.custid3 = cc.customerid "
                                + " left join cbs_customer_master_detail dd on a.custid4 = dd.customerid "
                                + " where (a.custid1<> null or a.custid1<>'') or (a.custid2<> null or a.custid2<>'') or (a.custid3<> null or a.custid3<>'') or (a.custid4<> null or a.custid4<>'')) jt on jt.acno = zz.acno  "
                                + " order by zz.acno ").getResultList();
                    }
                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            LoanAcDetailsTable pojo = new LoanAcDetailsTable();
                            vtrmain = (Vector) result.get(j);
                            acNo = vtrmain.get(0).toString();
                            sansonLimit = Double.parseDouble(vtrmain.get(1).toString());
                            sanctionDt = vtrmain.get(2).toString();
                            presentStatus = "";
                            if (vtrmain.get(3).toString().equalsIgnoreCase("DOUBTFUL")) {
                                long dayDiff = 0;
                                List limitList = em.createNativeQuery("select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acNo + "'").getResultList();
                                Vector limitVec = (Vector) limitList.get(0);
                                String npaDt = limitVec.get(0).toString();
                                limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                                        + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                                        + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                                        + "order by days").getResultList();
                                if (!limitList.isEmpty()) {
                                    dayDiff = CbsUtil.dayDiff(ymdFormat.parse(npaDt), ymdFormat.parse(tillDt));
                                    for (int t = 0; t < limitList.size(); t++) {
                                        limitVec = (Vector) limitList.get(t);
                                        if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                            if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                presentStatus = vtrmain.get(3).toString().concat("-" + "D1");
                                                break;
                                            }
                                        } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                            if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                presentStatus = vtrmain.get(3).toString().concat("-" + "D2");
                                                break;
                                            }
                                        } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                            presentStatus = vtrmain.get(3).toString().concat("-" + "D3");
                                            break;
                                        }
                                    }
                                }
                            } else {
                                presentStatus = vtrmain.get(3).toString();
                            }
                            custName = vtrmain.get(4).toString();
                            loanPd = Integer.parseInt(vtrmain.get(5).toString());
                            odLimit = Double.parseDouble(vtrmain.get(6).toString());
                            odSanctionChangeDt = vtrmain.get(7).toString();
                            brnCode = vtrmain.get(11).toString();
                            productCode = vtrmain.get(10).toString();
                            category = vtrmain.get(12).toString();
                            docExpDt = vtrmain.get(13).toString();
                            renewalDt = vtrmain.get(14).toString();
                            String intCalcBasedOnSecurity = vtrmain.get(15).toString();
                            //expiryDt = dmyFormat.format(ymdFormat.parse(CbsUtil.monthAdd(sanctionDt, loanPd)));

                            balance = openingBalance(acNo, tillDt);
                            custId = vtrmain.get(8).toString();
                            sector = vtrmain.get(9).toString();

                            // List result55 = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0), date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y')  from loan_recon   where dt <= '" + tillDt + "'  and trandesc  in (6,0)  and acno='" + acNo + "'").getResultList();
                            List result5 = em.createNativeQuery("select amtDis,disbDt,JointNo from "
                                    + "(select ifnull(sum(ifnull(dramt,0)),0) amtDis, date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y') disbDt  from loan_recon  "
                                    + "where dt <= '" + tillDt + "'   and trandesc in (6,0)  and acno='" + acNo + "')a, "
                                    + "( "
                                    + "select  count(custid) as JointNo  from (select ifnull(custid1,'') as custid  from accountmaster where acno='" + acNo + "' "
                                    + "union all select ifnull(custid2,'') as custid  from accountmaster where acno='" + acNo + "' "
                                    + "union all select ifnull(custid3,'') as custid  from accountmaster where acno='" + acNo + "' "
                                    + "union all select ifnull(custid4,'') as custid  from accountmaster where acno='" + acNo + "' "
                                    + ") a where  custid <>'')b").getResultList();
                            if (!result5.isEmpty()) {
                                vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                disbDt = vtrsub.get(1).toString();
                                jointNo = Integer.parseInt(vtrsub.get(2).toString());
                            }
                            double instalAmt = 0d;
                            List result6 = em.createNativeQuery("select distinct(installamt) from emidetails where acno = '" + acNo + "' and  sno = (select max(sno) from emidetails where acno = '" + acNo + "'  and duedt<='" + tillDt + "')").getResultList();
                            if (!result6.isEmpty()) {
                                Vector vist = (Vector) result6.get(0);
                                instalAmt = Double.parseDouble(vist.get(0).toString());
                            } else {
                                result6 = em.createNativeQuery("select distinct(installamt) from emidetails where acno = '" + acNo + "' ").getResultList();
                                if (!result6.isEmpty()) {
                                    Vector vist = (Vector) result6.get(0);
                                    instalAmt = Double.parseDouble(vist.get(0).toString());
                                }
                            }
                            List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt,acLimit from loan_oldinterest "
                                    + "where acno =  '" + acNo + "' and txnid = "
                                    + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + tillDt + "' )").getResultList();
                            if (!sanctionLimitDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionLimitDtList.get(0);
                                odSanctionChangeDt = vist.get(0).toString();
                                odLimit = Double.parseDouble(vist.get(1).toString());
                            }
                            expiryDt = dmyFormat.format(ymdFormat.parse(CbsUtil.monthAdd(ymdFormat.format(y_m_dFormat.parse(sanctionDt).compareTo(y_m_dFormat.parse(odSanctionChangeDt)) < 0 ? y_m_dFormat.parse(odSanctionChangeDt) : y_m_dFormat.parse(sanctionDt)), loanPd)));
                            String lastCrDt = "";
                            List tempList = em.createNativeQuery("select max(dt) from loan_recon where acno='" + acNo + "' and  cramt>0 and dt <= '" + tillDt + "'").getResultList();
                            if (!tempList.isEmpty()) {
                                Vector ele = (Vector) tempList.get(0);
                                if (ele.get(0) != null) {
                                    lastCrDt = ymdFormat.format((Date) ele.get(0));
                                }
                            }
                            BigDecimal lastCrAmt = new BigDecimal("0");
                            BigDecimal npaOir = new BigDecimal("0");
                            tempList = em.createNativeQuery("select LastCrAmt,NpaOri from ( "
                                    + "(select ifnull(sum(cramt),0) LastCrAmt from loan_recon where acno='" + acNo + "' and dt='" + lastCrDt + "')a, "
                                    + "(select ifnull(sum(cramt-dramt),0) NpaOri from npa_recon where acno = '" + acNo + "' and dt <= '" + tillDt + "')b "
                                    + ")").getResultList();

                            if (!tempList.isEmpty()) {
                                Vector ele = (Vector) tempList.get(0);
                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                    lastCrAmt = new BigDecimal(ele.get(0).toString());
                                }
                                npaOir = new BigDecimal(ele.get(1).toString());
                            }
                            pojo.setAccountNumber(acNo);
                            pojo.setAcDesc(common.getAcctDecription(acNo.substring(2, 4)));
                            pojo.setName(custName);
                            String roi = "0";
                            //float roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(new BigDecimal(sansonLimit).abs().toString()), tillDt, acNo);
//                            if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
//                                List roiList = em.createNativeQuery("select  aa.SecurityRoi, aa.MargineROI, aa.AppRoi, aa.IntTableCode, aa.addRoi, lienvalue from "
//                                        + "(select  ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(addRoi,0) as addRoi, lienvalue from loansecurity where acno = '" + acNo + "' and status = 'Active'  and IntTableCode is not null and entrydate <= '" + tillDt + "' and IntTableCode <>''"
//                                        + " union all "
//                                        + " select  ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(addRoi,0) as addRoi, lienvalue from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and IntTableCode is not null and entrydate <= '" + tillDt + "' and ExpiryDate > '" + tillDt + "' and IntTableCode <>'') aa order by aa.AppRoi").getResultList();
//                                if (!roiList.isEmpty()) {
//                                    Vector roiVector = (Vector) roiList.get(0);
//                                    double securityRoi = Double.parseDouble(roiVector.get(0).toString());
//                                    double additionalRoi = Double.parseDouble(roiVector.get(4).toString());
//                                    double lienValue = Double.parseDouble(roiVector.get(5).toString());
//                                    String intTableCode1 = roiVector.get(3).toString();
//                                    double roi1 = securityRoi + additionalRoi + Double.parseDouble(loanInterestCalculationBean.getROI(intTableCode1, lienValue, tillDt));
//                                    roi = String.valueOf(roi1);
//                                }
//                            } else {
                            roi = loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(new BigDecimal(sansonLimit).abs().toString()), tillDt, acNo);
//                            }

                            pojo.setRoi(Float.parseFloat(roi));
                            pojo.setAmountSanc(BigDecimal.valueOf(sansonLimit));
                            pojo.setSanctionDt(dmyFormat.format(y_m_dFormat.parse(sanctionDt)));
                            pojo.setOdSanctionChangeDt(odSanctionChangeDt.equalsIgnoreCase("1900-01-01") ? "" : dmyFormat.format(y_m_dFormat.parse(odSanctionChangeDt)));
                            pojo.setExpiryDt(expiryDt);
                            pojo.setDisbDt(disbDt);
                            pojo.setAmtDis(BigDecimal.valueOf(amtDis));
                            pojo.setBalance(BigDecimal.valueOf(Double.parseDouble(df.format(balance))));
                            pojo.setStatus(presentStatus);
                            pojo.setLoanDuration(loanPd);
                            pojo.setCustId(custId);
                            pojo.setSector(sector);
                            pojo.setOdLimit(BigDecimal.valueOf(odLimit));
                            pojo.setInstallAmt(new BigDecimal(instalAmt));
                            pojo.setLastCrDt(lastCrDt.equalsIgnoreCase("") ? "" : dmyFormat.format(ymdFormat.parse(lastCrDt)));
                            pojo.setLastCrAmt(lastCrAmt);
                            pojo.setBrnCode(brnCode);
                            pojo.setProductCode(productCode);
                            pojo.setDocExpDt(docExpDt.equalsIgnoreCase("1900-01-01") ? "" : dmyFormat.format(y_m_dFormat.parse(docExpDt)));
                            if (category.equals("WK")) {
                                category = "Weaker";
                            } else {
                                category = "Non Weaker";
                            }
                            pojo.setCategory(category);
                            if (npaOir.doubleValue() < 0) {
                                pojo.setNpaOir(npaOir.abs());
                            } else {
                                pojo.setNpaOir(new BigDecimal("0"));
                            }
                            pojo.setAcNature(acctNature);
                            List emiList = em.createNativeQuery("select ifnull(date_format(min(dueDt),'%Y%m%d'),'') from emidetails where acno='" + acNo + "'").getResultList();
                            if (!emiList.isEmpty()) {
                                Vector vet = (Vector) emiList.get(0);
                                pojo.setEmiStrtDt(vet.get(0).toString().equalsIgnoreCase("") ? "" : dmyFormat.format(ymdFormat.parse(vet.get(0).toString())));
                            }
                            pojo.setSubSector(vtrmain.get(16).toString());
                            pojo.setTermOfAdvnc(vtrmain.get(17).toString());
                            pojo.setCategroyOpt(vtrmain.get(18).toString());
                            pojo.setJointNo(jointNo);
                            pojo.setJointDetails(vtrmain.get(19) == null ? "" : vtrmain.get(19).toString());
                            finalList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return finalList;
    }

    public List<LoanRepaymentPojo> loanRepaymentSchedule(String acno, String brnCode) throws ApplicationException {
        List<LoanRepaymentPojo> finalList = new ArrayList<LoanRepaymentPojo>();
        List custtempList = null;
        Vector vtr = null;
        String custname = "", crAddress = "", secmDis = "", brnQuery = "";
        Date openDt = null;
        double odLimit = 0d, loandisamt = 0d;
        float roi = 0f;
        double osbal = 0d;
        try {

            if (!(brnCode.equalsIgnoreCase("90") || brnCode.equalsIgnoreCase("0A"))) {
                brnQuery = " and am.curbrcode='" + brnCode + "' ";
            }
            custtempList = em.createNativeQuery("select  am.acno,am.custname,am.openingdt,am.odlimit,c.craddress from accountmaster am,customermaster c where am.acno='" + acno + "' and substring(am.acno,5,6)=c.custno and c.brncode = am.curbrcode And c.actype = am.accttype " + brnQuery).getResultList();
            if (!custtempList.isEmpty()) {
                vtr = (Vector) custtempList.get(0);
                custname = vtr.get(1).toString();
                odLimit = Double.parseDouble(vtr.get(3).toString());
                openDt = ymdFormat.parse(vtr.get(2).toString());
                crAddress = vtr.get(4).toString();
            }
            custtempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code=(select scheme_code from cbs_loan_acc_mast_sec where acno='" + acno + "') and ref_rec_no='203'").getResultList();
            if (!custtempList.isEmpty()) {
                vtr = (Vector) custtempList.get(0);
                secmDis = vtr.get(0).toString();
            }
            custtempList = em.createNativeQuery("select ifnull(sum(ifnull(amtdisbursed,0)),0) from loandisbursement where acno='" + acno + "'").getResultList();
            if (!custtempList.isEmpty()) {
                vtr = (Vector) custtempList.get(0);
                loandisamt = Double.parseDouble(vtr.get(0).toString());
            }
            roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(loandisamt, ymdFormat.format(openDt), acno));
            List resultList = em.createNativeQuery("select ifnull(prinamt,0),ifnull(interestamt,0),ifnull(installamt,0),duedt,paymentdt,status from emidetails e,accountmaster am where e.acno='" + acno + "' and e.acno=am.acno " + brnQuery + " order by duedt").getResultList();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    vtr = (Vector) resultList.get(i);
                    LoanRepaymentPojo pojo = new LoanRepaymentPojo();
                    pojo.setAcno(acno);
                    pojo.setAmtDisbursment(loandisamt);
                    pojo.setAmtSanctioned(odLimit);
                    pojo.setCustAddress(crAddress);
                    pojo.setCustName(custname);
                    pojo.setDueDate((Date) vtr.get(3));
                    pojo.setIntallmentAmt(Double.parseDouble(vtr.get(2).toString()));
                    pojo.setInterestAmt(Double.parseDouble(vtr.get(1).toString()));
                    pojo.setOpeningDt(openDt);
                    pojo.setPaymentdt((Date) vtr.get(4));
                    pojo.setPrincipleAmt(Double.parseDouble(vtr.get(0).toString()));
                    pojo.setRoi(roi);
                    pojo.setSchemeCode(secmDis);
                    pojo.setStatus(vtr.get(5).toString());
                    if (i == 0) {
                        pojo.setOutstandingPrinciple(odLimit - pojo.getPrincipleAmt());
                        osbal = pojo.getOutstandingPrinciple();
                    } else {
                        pojo.setOutstandingPrinciple(osbal - pojo.getPrincipleAmt());
                        osbal = pojo.getOutstandingPrinciple();
                    }
                    finalList.add(pojo);
                }

            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finalList;
    }

    public List getTLCode() {
        List tlCodeList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('TL')").getResultList();
        return tlCodeList;
    }

    public List getNpaStatus(String acno) throws ApplicationException {
        List statusList;
        try {
            //return em.createNativeQuery("select description from codebook where code = (select accstatus from accountmaster where acno ='" + acno + "') and groupcode = 3 and description in ('SUIT FIELDS','RECALLED','DECREED','SUB STANDARD','DOUTFUL','LOSS' ,'PROTESTED')").getResultList();
//            statusList = em.createNativeQuery("select description from codebook where code = (select accstatus from accountmaster where acno ='" + acno + "') and groupcode = 3 and description in ('SUIT FIELDS','RECALLED','DECREED','SUB STANDARD','DOUBTFUL','LOSS' ,'PROTESTED')").getResultList();
//            if (statusList.isEmpty()) {
//                statusList = em.createNativeQuery("select SPFLAG from accountstatus where acno = '" + acno + "' and  SPFLAG <> 1").getResultList();
//            }
            statusList = em.createNativeQuery("select distinct SPFLAG from accountstatus where acno = '" + acno + "' and  SPFLAG in(11,12,13)").getResultList();
            return statusList;

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<NpaAccStmPojo> npaAccountStatement(String acno, String frmdt, String todt) throws ApplicationException {
        List<NpaAccStmPojo> npaList = new ArrayList<NpaAccStmPojo>();
        List tempList = new ArrayList();
        String particulars = "";
        Date dt = null, valueDt = null;
        double cramt = 0, dramt = 0, opbal = 0, balance = 0, bal = 0;
        try {
            List opList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)),0) from npa_recon where auth= 'y' and acno = '" + acno + "' and dt <'" + frmdt + "'").getResultList();
            Vector element = (Vector) opList.get(0);
            opbal = Double.parseDouble(element.get(0).toString());

            balance = opbal;

            tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,valuedt from npa_recon where acno = '" + acno + "' and (dt <= '" + todt + "' and dt >= '" + frmdt + "') and auth='y' order by dt,trantime,recno").getResultList();
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    NpaAccStmPojo pojo = new NpaAccStmPojo();
                    Vector vector = (Vector) tempList.get(i);
                    String instno = "";
                    if (vector.get(0) != null || !vector.get(0).toString().equalsIgnoreCase("")) {
                        dt = (Date) vector.get(0);
                    }
                    if (vector.get(1) != null) {
                        particulars = vector.get(1).toString();
                    }
                    if (vector.get(2) != null) {
                        instno = vector.get(2).toString();
                    }
                    if (vector.get(3) == null || vector.get(3).toString().equalsIgnoreCase("")) {
                        dramt = 0;
                    } else {
                        dramt = Double.parseDouble(vector.get(3).toString());
                    }
                    if (vector.get(4) == null || vector.get(4).toString().equalsIgnoreCase("")) {
                        cramt = 0;
                    } else {
                        cramt = Double.parseDouble(vector.get(4).toString());
                    }
                    if (vector.get(5) != null || !vector.get(5).toString().equalsIgnoreCase("")) {
                        valueDt = (Date) vector.get(5);
                    }

                    if (i == 0) {
                        bal = bal + opbal + cramt - dramt;
                    } else {
                        bal = bal + cramt - dramt;
                    }

                    pojo.setNpaAcno(acno);
                    pojo.setNpaOpenBal(new BigDecimal(opbal));
                    pojo.setNpaDate(dt);
                    pojo.setNpaParticulars(particulars);
                    pojo.setNpaChequeno(instno);
                    pojo.setNpaWithDrawl(new BigDecimal(dramt));
                    pojo.setNpaDeposit(new BigDecimal(cramt));
                    pojo.setNpaBalance(new BigDecimal(bal));
                    pojo.setNpaValueDt(valueDt);

                    npaList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return npaList;
    }

    public List<LoanPeriodPojo> npaInterestData(String repType, String AcctCode, String FromDt, String ToDt, String brncode) throws ApplicationException {
        List<LoanPeriodPojo> repList = new ArrayList<LoanPeriodPojo>();
        String accno = "", custname = "";
        double amt, bal;
        try {
            List custtempList, amtList = null;
            if (brncode.equalsIgnoreCase("0A")) {
                if (repType.equalsIgnoreCase("Memorandum")) {
                    if (AcctCode.equalsIgnoreCase("ALL")) {
                        custtempList = em.createNativeQuery("select npa.acno,acc.custname from npa_recon npa,accountmaster acc ,accountstatus a, "
                                + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag,ast.spno  from accountstatus ast, "
                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + ToDt + "' "
                                + " and SPFLAG IN (11,12,13)  group by acno) b where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13) "
                                + " group by aa.acno,aa.effdt ) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) np, "
                                + " (select acno,max(spno) as sno from accountstatus where effdt <='" + ToDt + "' group by acno) c , "
                                + " accounttypemaster atm, codebook cb where  a.acno = c.acno and a.acno = acc.acno  and a.acno = np.npaAcno "
                                + " and npa.acno=acc.acno and np.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in ('11','12','13') "
                                + " and substring(a.acno,3,2) = atm.acctcode and a.effdt = np.npaEffDt and a.spno = c.sno  and a.effdt <='" + ToDt + "' "
                                + " and np.npaAcno= acc.acno and npa.acno =np.npaAcno and npa.acno in "
                                + " (select acno from accountmaster where openingdt <='" + ToDt + "' and "
                                + " (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + ToDt + "')) group by npa.acno,acc.custname").getResultList();
                    } else {
                        //substring(npa.acno,3,2) in ('" + AcctCode + "') and
                        custtempList = em.createNativeQuery("select npa.acno,acc.custname from npa_recon npa,accountmaster acc ,accountstatus a, "
                                + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag,ast.spno  from accountstatus ast, "
                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + ToDt + "' "
                                + " and SPFLAG IN (11,12,13)  group by acno) b where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13) "
                                + " group by aa.acno,aa.effdt ) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) np, "
                                + " (select acno,max(spno) as sno from accountstatus where effdt <='" + ToDt + "' group by acno) c , "
                                + " accounttypemaster atm, codebook cb where  a.acno = c.acno and a.acno = acc.acno  and a.acno = np.npaAcno "
                                + " and npa.acno=acc.acno and np.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in ('11','12','13') "
                                + " and substring(a.acno,3,2) = atm.acctcode and a.effdt = np.npaEffDt and a.spno = c.sno  and a.effdt <='" + ToDt + "' "
                                + " and np.npaAcno= acc.acno and npa.acno =np.npaAcno and substring(npa.acno,3,2) in ('" + AcctCode + "') and npa.acno in "
                                + " (select acno from accountmaster where openingdt <='" + ToDt + "' and "
                                + " (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + ToDt + "')) group by npa.acno,acc.custname").getResultList();
                    }
                    if (!custtempList.isEmpty()) {
                        for (int i = 0; i < custtempList.size(); i++) {
                            LoanPeriodPojo pojo = new LoanPeriodPojo();
                            Vector vector = (Vector) custtempList.get(i);
                            if (vector.get(0) != null || !vector.get(0).toString().equalsIgnoreCase("")) {
                                accno = vector.get(0).toString();
                            }
                            if (vector.get(1) != null) {
                                custname = vector.get(1).toString();
                            }

                            amtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)-ifnull(dramt,0)),0) from npa_recon where acno='" + accno
                                    + "' and dt between '" + FromDt + "' and '" + ToDt + "'").getResultList();
                            Vector amVec = (Vector) amtList.get(0);

                            amt = Double.parseDouble(amVec.get(0).toString());
                            bal = fnBalosForAccountAsOn(accno, ToDt);

                            pojo.setAccountNumber(accno);
                            pojo.setCustName(custname);
                            pojo.setDisbAmount(amt);
                            pojo.setBalance(bal);
                            if (amt != 0) {
                                repList.add(pojo);
                            }
                        }
                    }
                } else {
                    if (AcctCode.equalsIgnoreCase("ALL")) {
                        custtempList = em.createNativeQuery("select npa.acno,acc.custname from npa_recon_uri npa,accountmaster acc where "
                                + "npa.acno in(select acno from accountmaster where  openingdt <='" + ToDt + "' and (ifnull(closingdate,'') = '' "
                                + "or closingdate = '' or closingdate > '" + ToDt + "')) and npa.acno=acc.acno "
                                + " group by npa.acno,acc.custname").getResultList();
                    } else {
                        custtempList = em.createNativeQuery("select npa.acno,acc.custname from npa_recon_uri npa,accountmaster acc where "
                                + " substring(npa.acno,3,2) in ('" + AcctCode + "') and npa.acno in(select acno from accountmaster where  "
                                + "openingdt <='" + ToDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + ToDt + "')) "
                                + "and npa.acno=acc.acno group by npa.acno,acc.custname").getResultList();
                    }

                    if (!custtempList.isEmpty()) {
                        for (int i = 0; i < custtempList.size(); i++) {
                            LoanPeriodPojo pojo = new LoanPeriodPojo();
                            Vector vector = (Vector) custtempList.get(i);
                            if (vector.get(0) != null || !vector.get(0).toString().equalsIgnoreCase("")) {
                                accno = vector.get(0).toString();
                            }
                            if (vector.get(1) != null) {
                                custname = vector.get(1).toString();
                            }

                            amtList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from npa_recon_uri where acno='" + accno + "' AND dt between '" + FromDt + "' and '" + ToDt + "' ").getResultList();
                            Vector amVec = (Vector) amtList.get(0);
                            amt = Double.parseDouble(amVec.get(0).toString());
                            bal = fnBalosForAccountAsOn(accno, ToDt);

                            pojo.setAccountNumber(accno);
                            pojo.setCustName(custname);
                            pojo.setDisbAmount(amt);
                            pojo.setBalance(bal);
                            if (amt != 0) {
                                repList.add(pojo);
                            }

                        }
                    }
                }
            } else {
                if (repType.equalsIgnoreCase("Memorandum")) {
                    if (AcctCode.equalsIgnoreCase("ALL")) {
                        custtempList = em.createNativeQuery("select npa.acno,acc.custname from npa_recon npa,accountmaster acc ,accountstatus a, "
                                + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag,ast.spno  from accountstatus ast, "
                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + ToDt + "' "
                                + " and SPFLAG IN (11,12,13)  group by acno) b where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13) "
                                + " group by aa.acno,aa.effdt ) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) np, "
                                + " (select acno,max(spno) as sno from accountstatus where effdt <='" + ToDt + "' group by acno) c , "
                                + " accounttypemaster atm, codebook cb where  a.acno = c.acno and a.acno = acc.acno  and a.acno = np.npaAcno "
                                + " and npa.acno=acc.acno and np.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in ('11','12','13') "
                                + " and substring(a.acno,3,2) = atm.acctcode and a.effdt = np.npaEffDt and a.spno = c.sno  and a.effdt <='" + ToDt + "' "
                                + " and np.npaAcno= acc.acno and npa.acno =np.npaAcno and acc.curbrcode = '" + brncode + "' and npa.acno in "
                                + " (select acno from accountmaster where openingdt <='" + ToDt + "' and "
                                + " (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + ToDt + "')) group by npa.acno,acc.custname").getResultList();
                    } else {
                        custtempList = em.createNativeQuery("select npa.acno,acc.custname from npa_recon npa,accountmaster acc ,accountstatus a, "
                                + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag,ast.spno  from accountstatus ast, "
                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + ToDt + "' "
                                + " and SPFLAG IN (11,12,13)  group by acno) b where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13) "
                                + " group by aa.acno,aa.effdt ) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) np, "
                                + " (select acno,max(spno) as sno from accountstatus where effdt <='" + ToDt + "' group by acno) c , "
                                + " accounttypemaster atm, codebook cb where  a.acno = c.acno and a.acno = acc.acno  and a.acno = np.npaAcno "
                                + " and npa.acno=acc.acno and np.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in ('11','12','13') "
                                + " and substring(a.acno,3,2) = atm.acctcode and a.effdt = np.npaEffDt and a.spno = c.sno  and a.effdt <='" + ToDt + "' "
                                + " and np.npaAcno= acc.acno and npa.acno =np.npaAcno and substring(npa.acno,3,2) in ('" + AcctCode + "') "
                                + " and acc.curbrcode = '" + brncode + "' and npa.acno in "
                                + " (select acno from accountmaster where openingdt <='" + ToDt + "' and "
                                + " (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + ToDt + "')) group by npa.acno,acc.custname").getResultList();
                    }

                    if (!custtempList.isEmpty()) {
                        for (int i = 0; i < custtempList.size(); i++) {
                            LoanPeriodPojo pojo = new LoanPeriodPojo();
                            Vector vector = (Vector) custtempList.get(i);
                            if (vector.get(0) != null || !vector.get(0).toString().equalsIgnoreCase("")) {
                                accno = vector.get(0).toString();
                            }
                            if (vector.get(1) != null) {
                                custname = vector.get(1).toString();
                            }

                            amtList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from npa_recon where acno='" + accno
                                    + "' and dt between '" + FromDt + "' and '" + ToDt + "'").getResultList();
                            Vector amVec = (Vector) amtList.get(0);

                            amt = Double.parseDouble(amVec.get(0).toString());
                            bal = fnBalosForAccountAsOn(accno, ToDt);

                            pojo.setAccountNumber(accno);
                            pojo.setCustName(custname);
                            pojo.setDisbAmount(amt);
                            pojo.setBalance(bal);
                            if (amt != 0) {
                                repList.add(pojo);
                            }
                        }
                    }
                } else {
                    if (AcctCode.equalsIgnoreCase("ALL")) {
                        custtempList = em.createNativeQuery("select npa.acno,acc.custname from npa_recon_uri npa,accountmaster acc where "
                                + "npa.acno in(select acno from accountmaster where  openingdt <='" + ToDt + "' and (ifnull(closingdate,'') = '' "
                                + "or closingdate = '' or closingdate > '" + ToDt + "')) and npa.acno=acc.acno and acc.curbrcode = '" + brncode
                                + "' group by npa.acno,acc.custname").getResultList();
                    } else {
                        custtempList = em.createNativeQuery("select npa.acno,acc.custname from npa_recon_uri npa,accountmaster acc where "
                                + ""
                                + "substring(npa.acno,3,2) in ('" + AcctCode + "') and npa.acno in(select acno from accountmaster where  "
                                + "openingdt <='" + ToDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + ToDt + "')) "
                                + "and npa.acno=acc.acno and acc.curbrcode = '" + brncode + "' group by npa.acno,acc.custname").getResultList();
                    }

                    if (!custtempList.isEmpty()) {
                        for (int i = 0; i < custtempList.size(); i++) {
                            LoanPeriodPojo pojo = new LoanPeriodPojo();
                            Vector vector = (Vector) custtempList.get(i);
                            if (vector.get(0) != null || !vector.get(0).toString().equalsIgnoreCase("")) {
                                accno = vector.get(0).toString();
                            }
                            if (vector.get(1) != null) {
                                custname = vector.get(1).toString();
                            }

                            amtList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from npa_recon_uri where acno='" + accno + "' and dt between '" + FromDt + "' and '" + ToDt + "' ").getResultList();
                            Vector amVec = (Vector) amtList.get(0);
                            amt = Double.parseDouble(amVec.get(0).toString());
                            bal = fnBalosForAccountAsOn(accno, ToDt);

                            pojo.setAccountNumber(accno);
                            pojo.setCustName(custname);
                            pojo.setDisbAmount(amt);
                            pojo.setBalance(bal);
                            if (amt != 0) {
                                repList.add(pojo);
                            }

                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return repList;
    }

    public List getBorrowAccList() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode,accttype from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.MS_AC + "') and CrDbFlag in('C','B') ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getFindate(String FromDt, String brncode) throws ApplicationException {
        String findate1 = null;
        try {
            List yearEndList = em.createNativeQuery("select mindate from yearend where mindate<='" + FromDt + "' and maxdate>='" + FromDt + "' AND brncode = '" + brncode + "'").getResultList();
            if (yearEndList.size() > 0) {
                Vector vectorYearEnd = (Vector) yearEndList.get(0);
                findate1 = (String) vectorYearEnd.get(0);
            }

            if ((findate1 == null) || (findate1.equals(""))) {
                List dataList1 = em.createNativeQuery("select min(mindate) from yearend where yearendflag='n' and brncode = '" + brncode + "'").getResultList();
                if (dataList1.size() > 0) {
                    Vector element1 = (Vector) dataList1.get(0);
                    findate1 = (String) (element1.get(0) != null ? element1.get(0) : "");
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return findate1;
    }

    public List<WorkingStmtBorrowingPojo> workingStmtBorrowData(String AcctCode, String FromDt, String brncode, String finDt, String PrevDt) throws ApplicationException {
        List acNoList, acAmtList, openingBalList, closBalList, intpaidList;
        List<WorkingStmtBorrowingPojo> repList = new ArrayList<WorkingStmtBorrowingPojo>();
        try {
            String acNat = fts.getAcNatureByCode(AcctCode);
            String tableName = common.getTableName(acNat);

            if (acNat.equalsIgnoreCase("FD") || acNat.equalsIgnoreCase("MS")) {
                if (brncode.equalsIgnoreCase("0A")) {
                    acNoList = em.createNativeQuery("select ta.acno,tc.custname from td_accountmaster ta, td_customermaster tc where ta.accttype='"
                            + AcctCode + "' and ta.openingdt <= '" + FromDt + "' and  ( ifnull(ta.closingdate,'')='' or  ta.closingdate >= '"
                            + finDt + "')  and tc.custno = substring(ta.acno,5,6) and  ta.accttype = tc.actype and ta.curbrcode = tc.brncode ").getResultList();
                } else {
                    acNoList = em.createNativeQuery("select ta.acno,tc.custname from td_accountmaster ta, td_customermaster tc where ta.accttype='"
                            + AcctCode + "' and ta.openingdt <= '" + FromDt + "' and  ( ifnull(ta.closingdate,'')='' or  ta.closingdate >= '"
                            + finDt + "') and ta.curbrcode= '" + brncode + "' and tc.custno = substring(ta.acno,5,6) and  ta.accttype = tc.actype and "
                            + "ta.curbrcode = tc.brncode ").getResultList();
                }

                if (!acNoList.isEmpty()) {
                    for (int i = 0; i < acNoList.size(); i++) {
                        WorkingStmtBorrowingPojo pojo = new WorkingStmtBorrowingPojo();
                        double cramt = 0, dramt = 0, openBal = 0, closBal = 0, intpaid = 0;
                        String accno = "", custname = "";
                        Vector vector = (Vector) acNoList.get(i);
                        if (vector.get(0) != null || !vector.get(0).toString().equalsIgnoreCase("")) {
                            accno = vector.get(0).toString();
                        }
                        if (vector.get(1) != null) {
                            custname = vector.get(1).toString();
                        }

                        acAmtList = em.createNativeQuery("select ifnull(sum(ifnull(r.cramt,0)),0),ifnull(sum(ifnull(r.dramt,0)),0) from " + tableName + " r where r.acno = '" + accno + "' and r.dt <='" + FromDt + "' and r.dt >='" + finDt + "'  and auth = 'Y' and r.closeflag is null and r.trantype <> 27 ").getResultList();
                        Vector acAmtVector = (Vector) acAmtList.get(0);

                        cramt = Double.parseDouble(acAmtVector.get(0).toString());
                        dramt = Double.parseDouble(acAmtVector.get(1).toString());

                        openingBalList = em.createNativeQuery("select ifnull(sum(ifnull(r.cramt,0)),0) - ifnull(sum(ifnull(r.dramt,0)),0) from " + tableName + "  r where r.acno = '" + accno + "' and r.dt <  '" + finDt + "' and auth = 'Y' and r.closeflag is null and r.trantype <> 27 ").getResultList();
                        Vector openingBalVector = (Vector) openingBalList.get(0);

                        openBal = Double.parseDouble(openingBalVector.get(0).toString());

                        closBalList = em.createNativeQuery("select ifnull(sum(ifnull(r.cramt,0)),0) - ifnull(sum(ifnull(r.dramt,0)),0) from " + tableName + "  r where r.acno = '" + accno + "' and r.dt <=  '" + FromDt + "' and auth = 'Y' and r.closeflag is null and r.trantype <> 27 ").getResultList();
                        Vector closBalVector = (Vector) closBalList.get(0);

                        closBal = Double.parseDouble(closBalVector.get(0).toString());

                        intpaidList = em.createNativeQuery("select ifnull(sum(r.cramt),0)  from " + tableName + "  r  where r.acno = '" + accno + "' and r.trantype = 8 and r.dt <='" + FromDt + "' and r.dt >='" + finDt + "' and r.closeflag is null and r.trantype <> 27").getResultList();
                        Vector intpaidVector = (Vector) intpaidList.get(0);

                        intpaid = Double.parseDouble(intpaidVector.get(0).toString());

                        pojo.setSno(i);
                        pojo.setAcno(accno);
                        pojo.setName(custname);
                        pojo.setOpeningBal(new BigDecimal(openBal));
                        pojo.setCredit(new BigDecimal(cramt));
                        pojo.setDebit(new BigDecimal(dramt));
                        pojo.setClosingBal(new BigDecimal(closBal));
                        pojo.setIntpaid(new BigDecimal(intpaid));

                        repList.add(pojo);
                    }
                }
            } else {
                if (brncode.equalsIgnoreCase("0A")) {
                    acNoList = em.createNativeQuery("select am.acno,cm.custname from accountmaster am,customermaster cm where am.accttype='" + AcctCode
                            + "' and am.openingdt <= '" + FromDt + "' and  ( ifnull(am.closingdate,'')='' or  am.closingdate >= '" + finDt + "')  and "
                            + "cm.custno = substring(am.acno,5,6) and  am.accttype = cm.actype and am.curbrcode = cm.brncode and "
                            + "substring(am.acno,11,2) = cm.agcode").getResultList();
                } else {
                    acNoList = em.createNativeQuery("select am.acno,cm.custname from accountmaster am,customermaster cm where am.accttype='" + AcctCode
                            + "' and am.openingdt <= '" + FromDt + "' and  ( ifnull(am.closingdate,'')='' or  am.closingdate >= '" + finDt + "') and "
                            + "am.curbrcode = '" + brncode + "' and cm.custno = substring(am.acno,5,6) and  am.accttype = cm.actype and "
                            + "am.curbrcode = cm.brncode and substring(am.acno,11,2) = cm.agcode").getResultList();
                }

                if (!acNoList.isEmpty()) {
                    for (int i = 0; i < acNoList.size(); i++) {
                        WorkingStmtBorrowingPojo pojo = new WorkingStmtBorrowingPojo();
                        double cramt = 0, dramt = 0, openBal = 0, closBal = 0, intpaid = 0;
                        String accno = "", custname = "";
                        Vector vector = (Vector) acNoList.get(i);
                        if (vector.get(0) != null || !vector.get(0).toString().equalsIgnoreCase("")) {
                            accno = vector.get(0).toString();
                        }
                        if (vector.get(1) != null) {
                            custname = vector.get(1).toString();
                        }

                        acAmtList = em.createNativeQuery("select ifnull(sum(ifnull(r.cramt,0)),0),ifnull(sum(ifnull(r.dramt,0)),0) from " + tableName + " r where r.acno = '" + accno + "' and r.dt <='" + FromDt + "' and r.dt >='" + finDt + "'  and auth = 'Y'").getResultList();
                        Vector acAmtVector = (Vector) acAmtList.get(0);

                        cramt = Double.parseDouble(acAmtVector.get(0).toString());
                        dramt = Double.parseDouble(acAmtVector.get(1).toString());

                        openBal = fnBalosForAccountAsOn(accno, PrevDt);
                        closBal = fnBalosForAccountAsOn(accno, FromDt);

                        intpaidList = em.createNativeQuery("select ifnull(sum(r.cramt),0)  from " + tableName + "  r  where r.acno = '" + accno + "' and r.trantype = 8 and r.dt <='" + FromDt + "' and r.dt >='" + finDt + "'").getResultList();
                        Vector intpaidVector = (Vector) intpaidList.get(0);

                        intpaid = Double.parseDouble(intpaidVector.get(0).toString());

                        pojo.setSno(i);
                        pojo.setAcno(accno);
                        pojo.setName(custname);
                        pojo.setOpeningBal(new BigDecimal(openBal));
                        pojo.setCredit(new BigDecimal(cramt));
                        pojo.setDebit(new BigDecimal(dramt));
                        pojo.setClosingBal(new BigDecimal(closBal));
                        pojo.setIntpaid(new BigDecimal(intpaid));

                        repList.add(pojo);
                    }
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return repList;
    }

    public List getNatureTypeList() throws ApplicationException {
        try {
            List natureList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in "
                    + "('" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "') and crDbFlag in ('B','D')").getResultList();
            if (!natureList.isEmpty()) {
                return natureList;
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getDisburseList(String acType, String acNature, double disFrAmount, double disToAmount, String disFromDate, String disToDate, String orderBy, String brCode) throws ApplicationException {
        List<LoanDisbursementPojo> returnList = new ArrayList<LoanDisbursementPojo>();
        String disbursementDt = "";
        String periodicity = "0";
        double installamt = 0d;
        String minDueDt = "";
        double disbAmt = 0d, outstandbal = 0d, roi = 0d;
        //double intDeposit = 0;
        // double odLimit = 0d;
        String acNo = "";
        String custName = "";
        Integer loanDuration = 0;
        String sectordesc = "";
        String applicantCatDesc = "";
        String catagoryOptDesc = "";
        String accTypeQuery = "";
        List result1 = new ArrayList();
        try {
            if (acNature.equalsIgnoreCase("0")) {
                accTypeQuery = " and  substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D'))  ";
            } else {
                if (!(acNature.equalsIgnoreCase("0") || acNature.equalsIgnoreCase("")) && (acType.equalsIgnoreCase("0") || acType.equalsIgnoreCase(""))) {
                    accTypeQuery = " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and CrDbFlag in('B','D')) ";
                } else {
                    accTypeQuery = " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctcode in ('" + acType + "') ) ";
                }
            }

            List<LoanMisCellaniousPojo> misList = loanRemote.cbsLoanMisReport(brCode.equalsIgnoreCase("90") ? "0A" : brCode, "0", "0", disToDate, "A", 0.0, 99999999999999.99, "O",
                    "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "ALL", "0", "N", "S", "0", "N", "0", "N", "N", "0", "0", "0", "0");

            Map<String, LoanMisCellaniousPojo> map = new HashMap<String, LoanMisCellaniousPojo>();
            for (int i = 0; i < misList.size(); i++) {
                LoanMisCellaniousPojo pojo = new LoanMisCellaniousPojo();
                pojo = misList.get(i);
                map.put(pojo.getAcNo(), pojo);
            }

            String bnkCode = fts.getBankCode();
            if (bnkCode.equalsIgnoreCase("army")) {
                String brnQuery = "";
                if (!(brCode.equalsIgnoreCase("0A") || (brCode.equalsIgnoreCase("90")))) {
                    brnQuery = " and substring(aa.acno,1,2) = '" + brCode + "' ";
                }
                result1 = em.createNativeQuery("select aa.Acno, aa.custname, aa.amtDisbursed, aa.intDeposit, aa.ODLimit, aa.sno, jt.joinDetails  from "
                        + " (select a.Acno, a.custname, cast((ifnull(l.amtDisbursed,0)) as decimal(25,2)) as amtDisbursed, a.intDeposit, "
                        + " cast(a.ODLimit as decimal(25,2)) as ODLimit, l.sno from accountmaster a, loandisbursement l "
                        + " where a.OpeningDt <='" + disToDate + "' "
                        + " and ((a.ClosingDate = '' or a.ClosingDate is null or a.ClosingDate>'" + disToDate + "') "
                        + " OR (a.ClosingDate between '" + disFromDate + "' and '" + disToDate + "')) "
                        + " and a.acno = l.acno "
                        + accTypeQuery
                        + " and l.DisbursementDt between '" + disFromDate + "' and '" + disToDate + "' "
                        + " group by a.Acno, l.sno ) aa "
                        + " left join (select a.acno, a.custname, "
                        + " CONCAT(ifnull(if((a.custid1<>null or a.custid1<>''),CONCAT('[',a.custid1,'] ',aa.CustFullName,''),''),''),"
                        + " ifnull(if((a.custid2<>null or a.custid2<>''),CONCAT('\n[',a.custid2,'] ',bb.CustFullName,' '),''),''), "
                        + " ifnull(if((a.custid3<>null or a.custid3<>''),CONCAT('\n[',a.custid3,'] ',cc.CustFullName,' '),''),''), "
                        + " ifnull(if((a.custid4<>null or a.custid4<>''),CONCAT('\n[',a.custid4,'] ',dd.CustFullName,' '),''),'')) as joinDetails "
                        + " from accountmaster a "
                        + " left join cbs_customer_master_detail aa on a.custid1 = aa.customerid "
                        + " left join cbs_customer_master_detail bb on a.custid2 = bb.customerid "
                        + " left join cbs_customer_master_detail cc on a.custid3 = cc.customerid "
                        + " left join cbs_customer_master_detail dd on a.custid4 = dd.customerid "
                        + " where (a.custid1<> null or a.custid1<>'') or (a.custid2<> null or a.custid2<>'') or (a.custid3<> null or a.custid3<>'') or (a.custid4<> null or a.custid4<>'')) jt on jt.acno = aa.Acno  , "
                        + " (select acno, max(sno) as sno from loandisbursement where DisbursementDt <='" + disToDate + "'  group by acno) bb "
                        + " where aa.acno = bb.acno and aa.sno = bb.sno " + brnQuery
                        + " and aa.amtDisbursed between '" + disFrAmount + "' and '" + disToAmount + "'").getResultList();
            } else {
                if (brCode.equalsIgnoreCase("0A")) {
                    result1 = em.createNativeQuery("select a.Acno,a.custname,cast(sum(ifnull(l.amtDisbursed,0)) as decimal(25,2)) as amtDisbursed,"
                            + " a.intDeposit,a.ODLimit, l.sno, jt.joinDetails from accountmaster a "
                            + " left join (select a.acno, a.custname, "
                            + " CONCAT(ifnull(if((a.custid1<>null or a.custid1<>''),CONCAT('[',a.custid1,'] ',aa.CustFullName,''),''),''),"
                            + " ifnull(if((a.custid2<>null or a.custid2<>''),CONCAT('\n[',a.custid2,'] ',bb.CustFullName,' '),''),''), "
                            + " ifnull(if((a.custid3<>null or a.custid3<>''),CONCAT('\n[',a.custid3,'] ',cc.CustFullName,' '),''),''), "
                            + " ifnull(if((a.custid4<>null or a.custid4<>''),CONCAT('\n[',a.custid4,'] ',dd.CustFullName,' '),''),'')) as joinDetails "
                            + " from accountmaster a "
                            + " left join cbs_customer_master_detail aa on a.custid1 = aa.customerid "
                            + " left join cbs_customer_master_detail bb on a.custid2 = bb.customerid "
                            + " left join cbs_customer_master_detail cc on a.custid3 = cc.customerid "
                            + " left join cbs_customer_master_detail dd on a.custid4 = dd.customerid "
                            + " where (a.custid1<> null or a.custid1<>'') or (a.custid2<> null or a.custid2<>'') or (a.custid3<> null or a.custid3<>'') or (a.custid4<> null or a.custid4<>'')) jt on jt.acno = a.Acno  , "
                            + " loandisbursement l where a.OpeningDt <='" + disToDate + "' "
                            + " and ((a.ClosingDate = '' or a.ClosingDate is null or a.ClosingDate>'" + disToDate + "')"
                            + " OR (a.ClosingDate between '" + disFromDate + "' and '" + disToDate + "')) "
                            + " and a.acno=l.acno  " + accTypeQuery
                            + " and l.DisbursementDt between '" + disFromDate + "' and '" + disToDate
                            + "' group by a.Acno having sum(ifnull(l.amtDisbursed,0)) between " + disFrAmount + " and " + disToAmount).getResultList();
                } else {
                    result1 = em.createNativeQuery("select a.Acno,a.custname,cast(sum(ifnull(l.amtDisbursed,0)) as decimal(25,2)) as amtDisbursed,"
                            + " a.intDeposit,a.ODLimit, l.sno, jt.joinDetails from accountmaster a left join (select a.acno, a.custname, "
                            + " CONCAT(ifnull(if((a.custid1<>null or a.custid1<>''),CONCAT('[',a.custid1,'] ',aa.CustFullName,''),''),''),"
                            + " ifnull(if((a.custid2<>null or a.custid2<>''),CONCAT('\n[',a.custid2,'] ',bb.CustFullName,' '),''),''), "
                            + " ifnull(if((a.custid3<>null or a.custid3<>''),CONCAT('\n[',a.custid3,'] ',cc.CustFullName,' '),''),''), "
                            + " ifnull(if((a.custid4<>null or a.custid4<>''),CONCAT('\n[',a.custid4,'] ',dd.CustFullName,' '),''),'')) as joinDetails "
                            + " from accountmaster a "
                            + " left join cbs_customer_master_detail aa on a.custid1 = aa.customerid "
                            + " left join cbs_customer_master_detail bb on a.custid2 = bb.customerid "
                            + " left join cbs_customer_master_detail cc on a.custid3 = cc.customerid "
                            + " left join cbs_customer_master_detail dd on a.custid4 = dd.customerid "
                            + " where (a.custid1<> null or a.custid1<>'') or (a.custid2<> null or a.custid2<>'') or (a.custid3<> null or a.custid3<>'') or (a.custid4<> null or a.custid4<>'')) jt on jt.acno = a.Acno  , "
                            + " loandisbursement l where a.OpeningDt <='" + disToDate + "' "
                            + " and ((a.ClosingDate = '' or a.ClosingDate is null or a.ClosingDate>'" + disToDate + "') "
                            + " OR (a.ClosingDate between '" + disFromDate + "' and '" + disToDate + "')) "
                            + " and a.acno=l.acno " + accTypeQuery + " and substring(a.acno,1,2)='" + brCode
                            + "' and l.DisbursementDt between '" + disFromDate + "' and '" + disToDate + "' "
                            + " group by a.Acno  having sum(ifnull(l.amtDisbursed,0)) between " + disFrAmount + " and " + disToAmount).getResultList();
                }
            }
            if (!result1.isEmpty()) {
                for (int j = 0; j < result1.size(); j++) {
                    LoanDisbursementPojo pojo = new LoanDisbursementPojo();
                    Vector resVect = (Vector) result1.get(j);
                    acNo = resVect.get(0).toString();

                    if (map.containsKey(acNo)) {
                        sectordesc = map.get(acNo).getSectorDesc();
                        applicantCatDesc = map.get(acNo).getApplicantCategoryDesc();
                        catagoryOptDesc = map.get(acNo).getCategoryOptDesc();
                    } else {
                        sectordesc = "";
                        applicantCatDesc = "";
                        catagoryOptDesc = "";
                    }

                    custName = resVect.get(1).toString();
                    disbAmt = Double.parseDouble(resVect.get(2).toString());
                    String acNat = fts.getAcNatureByCode(acNo.substring(2, 4));
                    String tableName = CbsUtil.getReconTableName(acNat);
                    String jointDetails = resVect.get(6) == null ? "" : resVect.get(6).toString();
                    // intDeposit = Double.parseDouble(resVect.get(3).toString());

                    List balList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from  " + tableName
                            + " where acno = '" + acNo + "' and dt <='" + disToDate + "'").getResultList();
                    Vector ele = (Vector) balList.get(0);
                    outstandbal = Double.parseDouble(ele.get(0).toString());
                    roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Math.abs(outstandbal), disToDate, acNo));

                    List result11 = em.createNativeQuery("select min(duedt) from emidetails where acno='" + acNo + "'").getResultList();
                    if (!result11.isEmpty()) {
                        Vector resVect1 = (Vector) result11.get(0);
                        if (resVect1.get(0) != null) {
                            minDueDt = resVect1.get(0).toString();
                        } else {
                            minDueDt = "";
                        }
                    }
                    List result12 = em.createNativeQuery("select ifnull(installamt,0) from emidetails where acno='" + acNo + "' and sno='1'").getResultList();
                    if (!result12.isEmpty()) {
                        Vector resVect2 = (Vector) result12.get(0);
                        if (resVect2.get(0) != null) {
                            installamt = Double.parseDouble(resVect2.get(0).toString());
                        }
                    }
                    List result13 = em.createNativeQuery("select distinct periodicity from emidetails where acno='" + acNo + "' and duedt= "
                            + "(select min(duedt) from emidetails where acno='" + acNo + "')").getResultList();
                    if (!result13.isEmpty()) {
                        Vector resVect3 = (Vector) result13.get(0);
                        if (resVect3.get(0) != null) {
                            periodicity = resVect3.get(0).toString();
                        }
                    }
                    List result14 = em.createNativeQuery("select max(disbursementdt) from loandisbursement where acno='" + acNo + "' and DISBURSEMENTDT <='" + disToDate + "'").getResultList();
                    if (!result14.isEmpty()) {
                        Vector resVect4 = (Vector) result14.get(0);
                        if (resVect4.get(0) != null) {
                            disbursementDt = resVect4.get(0).toString();
                        }
                    }
                    List result15 = em.createNativeQuery("select loan_duration from cbs_loan_borrower_details where acc_no = '" + acNo + "'").getResultList();
                    if (!result15.isEmpty()) {
                        Vector resVect5 = (Vector) result15.get(0);
                        if (resVect5.get(0) == null || resVect5.get(0).toString().equalsIgnoreCase("")) {
                            loanDuration = 0;
                        } else {
                            loanDuration = Integer.parseInt(resVect5.get(0).toString());
                        }
                    }

                    List result16 = em.createNativeQuery("select ifnull(Sanctionlimit,0) from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    Vector resVect6 = (Vector) result16.get(0);
                    String srlNo = "";
                    if (bnkCode.equalsIgnoreCase("army")) {
                        if (!disbursementDt.equalsIgnoreCase("")) {
                            List list3 = em.createNativeQuery("select SNO,DUEDT,INSTALLAMT from emidetails where sno =(select ifnull(max(SNO),1) from "
                                    + "(select SNO,min(date_format(DUEDT,'%Y%m%d')) duedt,INSTALLAMT from emidetails where acno = '" + acNo + "' and duedt <='" + disToDate + "' group by INSTALLAMT )a "
                                    + ") and acno = '" + acNo + "'").getResultList();
                            if (!list3.isEmpty()) {
                                Vector element = (Vector) list3.get(0);
                                srlNo = element.get(0).toString();
                                minDueDt = element.get(1).toString();
                                installamt = Double.parseDouble(element.get(2).toString());
                            } else {
                                minDueDt = disbursementDt;
                            }

                            if (y_m_d_h_m_sFormat.parse(minDueDt).before(y_m_d_h_m_sFormat.parse(disbursementDt))) {
                                list3 = em.createNativeQuery("select SNO,min(duedt),INSTALLAMT from emidetails where acno = '" + acNo + "' and DUEDT >=(select max(DISBURSEMENTDT) from loandisbursement where acno = '" + acNo + "') and STATUS = 'Unpaid'").getResultList();
                                Vector ele1 = (Vector) list3.get(0);
                                if (ele1.get(0) == null) {
                                    srlNo = srlNo;
                                } else {
                                    srlNo = ele1.get(0).toString();
                                }
                                if (ele1.get(1) == null) {
                                    minDueDt = minDueDt;
                                } else {
                                    minDueDt = ele1.get(1).toString();
                                }
                                if (ele1.get(2) == null) {
                                    installamt = installamt;
                                } else {
                                    installamt = Double.parseDouble(ele1.get(2).toString());
                                }
                            }
                        }
                    }

                    if (orderBy.equals("0")) {
                        SortedDisbursementByacno orderByAccNo = new SortedDisbursementByacno();
                        Collections.sort(returnList, orderByAccNo);
                        pojo.setAcno(acNo);
                        pojo.setCustName(custName);
                        pojo.setDisbAmt(disbAmt);
                        pojo.setRoi(roi);
                        if (result15.isEmpty()) {
                            pojo.setLoanPeriod(0);
                        } else {
                            pojo.setLoanPeriod(loanDuration);
                        }
                        pojo.setInstalAmt(installamt);
                        pojo.setLastAmtDisbDt(disbursementDt.substring(8, 10) + "/" + disbursementDt.substring(5, 7) + "/" + disbursementDt.substring(0, 4));
                        pojo.setPeriodicity(periodicity);
                        if (!minDueDt.equals("")) {
                            pojo.setFstDueOn(minDueDt.substring(8, 10) + "/" + minDueDt.substring(5, 7) + "/" + minDueDt.substring(0, 4));
                        } else {
                            pojo.setFstDueOn(minDueDt);
                        }
                        pojo.setSanctionAmt(Double.parseDouble(resVect6.get(0).toString()));
                        pojo.setOutBal(new BigDecimal(outstandbal));
                        pojo.setpFlag(bnkCode);
                        pojo.setJointDetails(jointDetails);
                        pojo.setSectorDesc(sectordesc);
                        pojo.setApplicantCatDesc(applicantCatDesc);
                        pojo.setCatagoryOptDesc(catagoryOptDesc);
                        returnList.add(pojo);
                    } else if (orderBy.equals("1")) {
                        pojo.setAcno(acNo);
                        pojo.setCustName(custName);
                        pojo.setDisbAmt(disbAmt);
                        if (!minDueDt.equals("")) {
                            pojo.setFstDueOn(minDueDt.substring(8, 10) + "/" + minDueDt.substring(5, 7) + "/" + minDueDt.substring(0, 4));
                        } else {
                            pojo.setFstDueOn(minDueDt);
                        }
                        pojo.setInstalAmt(installamt);
                        pojo.setLastAmtDisbDt(disbursementDt.substring(8, 10) + "/" + disbursementDt.substring(5, 7) + "/" + disbursementDt.substring(0, 4));
                        if (result15.isEmpty()) {
                            pojo.setLoanPeriod(0);
                        } else {
                            pojo.setLoanPeriod(loanDuration);
                        }
                        pojo.setPeriodicity(periodicity);
                        pojo.setRoi(roi);
                        pojo.setSanctionAmt(Double.parseDouble(resVect6.get(0).toString()));
                        pojo.setOutBal(new BigDecimal(outstandbal));
                        pojo.setpFlag(bnkCode);
                        pojo.setJointDetails(jointDetails);
                        pojo.setSectorDesc(sectordesc);
                        pojo.setApplicantCatDesc(applicantCatDesc);
                        pojo.setCatagoryOptDesc(catagoryOptDesc);
                        returnList.add(pojo);
                    } else if (orderBy.equals("2")) {
                        SortedDisbursementDate orderByDate = new SortedDisbursementDate();
                        Collections.sort(returnList, orderByDate);
                        pojo.setAcno(acNo);
                        pojo.setCustName(custName);
                        pojo.setDisbAmt(disbAmt);
                        pojo.setInstalAmt(installamt);
                        pojo.setLastAmtDisbDt(disbursementDt.substring(8, 10) + "/" + disbursementDt.substring(5, 7) + "/" + disbursementDt.substring(0, 4));
                        if (result15.isEmpty()) {
                            pojo.setLoanPeriod(0);
                        } else {
                            pojo.setLoanPeriod(loanDuration);
                        }
                        pojo.setPeriodicity(periodicity);
                        pojo.setRoi(roi);
                        if (!minDueDt.equals("")) {
                            pojo.setFstDueOn(minDueDt.substring(8, 10) + "/" + minDueDt.substring(5, 7) + "/" + minDueDt.substring(0, 4));
                        } else {
                            pojo.setFstDueOn(minDueDt);
                        }
                        pojo.setSanctionAmt(Double.parseDouble(resVect6.get(0).toString()));
                        pojo.setOutBal(new BigDecimal(outstandbal));
                        pojo.setpFlag(bnkCode);
                        pojo.setJointDetails(jointDetails);
                        pojo.setSectorDesc(sectordesc);
                        pojo.setApplicantCatDesc(applicantCatDesc);
                        pojo.setCatagoryOptDesc(catagoryOptDesc);
                        returnList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List getAdvancesAccList() throws ApplicationException {
        List returnList = new ArrayList();
        try {
            List acctCodeNatureList = em.createNativeQuery("select accttype from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "') And CrDbFlag in('B','D') order by acctcode").getResultList();
            if (!acctCodeNatureList.isEmpty()) {
                for (int k = 0; k < acctCodeNatureList.size(); k++) {
                    Vector vector1 = (Vector) acctCodeNatureList.get(k);
                    String AcctType = vector1.get(0).toString();
                    returnList = acctCodeNatureList;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List workingStmtAdvancesData(String SelectAcType, String Frdt, String orgnBrCode, String yearstart) throws ApplicationException {
        BigDecimal begOut, begDue, demand, recovered, adv, closeDue, closeArrear, closeOut;
        List<WorkingStmtLoansPojo> repLoansList = new ArrayList<WorkingStmtLoansPojo>();
        List list = new ArrayList();
        List list0 = new ArrayList();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        List list4 = new ArrayList();
        List list5 = new ArrayList();
        List list6 = new ArrayList();
        List list7 = new ArrayList();
        List list8 = new ArrayList();

        try {
            list0 = em.createNativeQuery("select acctnature,acctcode from accounttypemaster where accttype ='" + SelectAcType + "'").getResultList();
            if (!list0.isEmpty()) {
                for (int i = 0; i < list0.size(); i++) {
                    Vector vector0 = (Vector) list0.get(i);
                    String acctNature = vector0.get(0).toString();
                    String acctcode = vector0.get(1).toString();

                    String tableName = CbsUtil.getReconTableName(acctNature);
                    if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                            || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                            || acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)
                            || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)
                            || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {

                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                            list = em.createNativeQuery("select acno from accountmaster where openingdt <='" + yearstart + "' and (closingdate is null or closingdate = '' or closingdate >='" + Frdt + "' ) and substring(acno,3,2)='" + acctcode + "' ").getResultList();
                        } else {
                            list = em.createNativeQuery("select acno from accountmaster where openingdt <='" + yearstart + "' and (closingdate is null or closingdate = '' or closingdate >='" + Frdt + "' ) and substring(acno,3,2)='" + acctcode + "' and curbrcode='" + orgnBrCode + "'").getResultList();
                        }

                        if (!list.isEmpty()) {
                            for (int j = 0; j < list.size(); j++) {
                                String acno = "";
                                Vector vector6 = (Vector) list.get(j);
                                acno = vector6.get(0).toString();

                                list1 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where  acno= '" + acno + "' and duedt <='" + yearstart + "'").getResultList();
                                Vector vector1 = (Vector) list1.get(0);
                                BigDecimal begOut1 = new BigDecimal("0");
                                begOut1 = new BigDecimal(vector1.get(0).toString());

                                list1 = em.createNativeQuery("select ifnull(sum(cramt),0) as begout from " + tableName + " where acno='" + acno + "' and dt <='" + yearstart + "'and trantype<>8").getResultList();
                                Vector vector2 = (Vector) list1.get(0);
                                BigDecimal begOut2 = new BigDecimal("0");
                                begOut2 = new BigDecimal(vector2.get(0).toString());

                                list1 = em.createNativeQuery("select ifnull(sum(dramt),0) as begOut from " + tableName + " where acno='" + acno + "' and dt <='" + yearstart + "'and trantype<>8 ").getResultList();
                                Vector vector3 = (Vector) list1.get(0);
                                BigDecimal begOut3 = new BigDecimal("0");
                                begOut3 = new BigDecimal(vector3.get(0).toString());

                                begOut = ((begOut2.subtract(begOut1)).subtract(begOut3));

                                list2 = em.createNativeQuery("select ifnull(sum(dramt),0) as demand from " + tableName + " where acno= '" + acno + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and trantype<>8 ").getResultList();
                                Vector vector4 = (Vector) list2.get(0);
                                demand = new BigDecimal(vector4.get(0).toString());

                                list3 = em.createNativeQuery("select ifnull(sum(cramt),0) as recovered from " + tableName + " where acno= '" + acno + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and trantype<>8 ").getResultList();
                                Vector vector5 = (Vector) list3.get(0);
                                BigDecimal recovered1 = new BigDecimal("0");
                                recovered1 = new BigDecimal(vector5.get(0).toString());

                                list3 = em.createNativeQuery("select ifnull(sum(dramt),0) as recovered from " + tableName + " where acno= '" + acno + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and trantype<>8 ").getResultList();
                                Vector vector7 = (Vector) list3.get(0);
                                BigDecimal recovered2 = new BigDecimal("0");
                                recovered2 = new BigDecimal(vector7.get(0).toString());

                                recovered = recovered1.subtract(recovered2);

                                list4 = em.createNativeQuery("select ifnull(sum(dramt),0) as adv from " + tableName + " where acno= '" + acno + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and trantype<>8 ").getResultList();
                                Vector vector8 = (Vector) list4.get(0);
                                adv = new BigDecimal(vector8.get(0).toString());

                                list5 = em.createNativeQuery("select ifnull(sum(cramt),0)  as begDue from " + tableName + " where acno='" + acno + "' and dt <='" + yearstart + "'").getResultList();
                                Vector vector9 = (Vector) list5.get(0);
                                BigDecimal begDue1 = new BigDecimal("0");
                                begDue1 = new BigDecimal(vector9.get(0).toString());

                                list5 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where acno='" + acno + "' and duedt <='" + yearstart + "'").getResultList();
                                Vector vector10 = (Vector) list5.get(0);
                                BigDecimal begDue2 = new BigDecimal("0");
                                begDue2 = new BigDecimal(vector10.get(0).toString());

                                list5 = em.createNativeQuery("select ifnull(sum(dramt),0) as begDue from " + tableName + " where acno='" + acno + "' and dt <='" + yearstart + "'").getResultList();
                                Vector vector11 = (Vector) list5.get(0);
                                BigDecimal begDue3 = new BigDecimal("0");
                                begDue3 = new BigDecimal(vector11.get(0).toString());

                                begDue = ((begDue1.subtract(begDue2)).subtract(begDue3));

                                list6 = em.createNativeQuery("select ifnull(sum(cramt),0) as closedue from " + tableName + " where acno='" + acno + "' and dt <='" + Frdt + "'").getResultList();
                                Vector vector12 = (Vector) list6.get(0);
                                BigDecimal closeDue1 = new BigDecimal("0");
                                closeDue1 = new BigDecimal(vector12.get(0).toString());

                                list6 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where acno='" + acno + "' and duedt <='" + Frdt + "'").getResultList();
                                Vector vector13 = (Vector) list6.get(0);
                                BigDecimal closeDue2 = new BigDecimal("0");
                                closeDue2 = new BigDecimal(vector13.get(0).toString());

                                list6 = em.createNativeQuery("select ifnull(sum(dramt),0) as closedue from " + tableName + " where acno='" + acno + "' and dt <='" + Frdt + "'").getResultList();
                                Vector vector14 = (Vector) list6.get(0);
                                BigDecimal closeDue3 = new BigDecimal("0");
                                closeDue3 = new BigDecimal(vector14.get(0).toString());

                                closeDue = ((closeDue1.subtract(closeDue2)).subtract(closeDue3));

                                list7 = em.createNativeQuery("select ifnull(sum(dramt),0)as closeout from " + tableName + " where acno='" + acno + "' and dt <='" + Frdt + "' and trantype<>8").getResultList();
                                Vector vector15 = (Vector) list7.get(0);
                                BigDecimal closeOut1 = new BigDecimal("0");
                                closeOut1 = new BigDecimal(vector15.get(0).toString());

                                list7 = em.createNativeQuery("select ifnull(sum(cramt),0) as closeout from " + tableName + " where acno='" + acno + "' and dt <='" + Frdt + "' and trantype<>8 ").getResultList();
                                Vector vector16 = (Vector) list7.get(0);
                                BigDecimal closeOut2 = new BigDecimal("0");
                                closeOut2 = new BigDecimal(vector16.get(0).toString());

                                closeOut = closeOut1.subtract(closeOut2);

                                closeArrear = new BigDecimal("0");

                                WorkingStmtLoansPojo pojo = new WorkingStmtLoansPojo();
                                pojo.setAcno(acno);
                                pojo.setBegOut(begOut);
                                pojo.setDemand(demand);
                                pojo.setRecovered(recovered);
                                pojo.setAdv(adv);
                                pojo.setBegDue(begDue);
                                pojo.setCloseDue(closeDue);
                                pojo.setCloseOut(closeOut);
                                pojo.setCloseArrear(new BigDecimal(0));
                                repLoansList.add(pojo);

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return repLoansList;
    }

    public List workingStmtAbstractAdvancesData(String Frdt, String orgnBrCode, String yearstart) throws ApplicationException {
        List<AbstractWorkingStmtLoansPojo> repAbstractLoansList = new ArrayList<AbstractWorkingStmtLoansPojo>();
        List list = new ArrayList();
        List list0 = new ArrayList();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        List list4 = new ArrayList();
        List list5 = new ArrayList();
        List list6 = new ArrayList();
        List list7 = new ArrayList();
        String acctcode = "";
        String acDesc = "";
        BigDecimal begDue, begOut, demand, recovered, adv, closeDue, closeArrear, closeOut;
        try {
            List nature = em.createNativeQuery("select distinct acctnature from accounttypemaster  where acctnature in ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "') And CrDbFlag in('B','D') ").getResultList();
            if (!nature.isEmpty()) {
                for (int i = 0; i < nature.size(); i++) {
                    Vector vector0 = (Vector) nature.get(i);
                    String acctNature = vector0.get(0).toString();
                    String tableName = CbsUtil.getReconTableName(acctNature);
                    if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                            || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                            || acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)
                            || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)
                            || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        list0 = em.createNativeQuery("select acctcode from accounttypemaster where acctnature ='" + acctNature + "' And CrDbFlag in('B','D') ").getResultList();
                        if (!list0.isEmpty()) {
                            for (int k = 0; k < list0.size(); k++) {
                                Vector vector = (Vector) list0.get(k);
                                acctcode = vector.get(0).toString();

                                list = em.createNativeQuery("select acctdesc from  accounttypemaster where acctcode = '" + acctcode + "'").getResultList();
                                if (!list.isEmpty()) {
                                    for (int j = 0; j < list.size(); j++) {
                                        Vector vector6 = (Vector) list.get(j);
                                        acDesc = vector6.get(0).toString();
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list1 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where  substring(acno,3,2) = '" + acctcode + "'and duedt <='" + yearstart + "' ").getResultList();
                                        } else {
                                            list1 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where  substring(acno,3,2) = '" + acctcode + "'and duedt <='" + yearstart + "' and substring(acno,1,2) = '" + orgnBrCode + "'").getResultList();
                                        }

                                        Vector vector1 = (Vector) list1.get(0);
                                        BigDecimal begOut1 = new BigDecimal("0");
                                        begOut1 = new BigDecimal(vector1.get(0).toString());
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list1 = em.createNativeQuery("select  ifnull(sum(ifnull(cramt,0)),0) - ifnull(sum(ifnull(dramt,0)),0) as begOut from " + tableName + " where substring(acno,3,2) = '" + acctcode + "' and dt <='" + yearstart + "' and  trantype<>8 ").getResultList();
                                        } else {
                                            list1 = em.createNativeQuery("select  ifnull(sum(ifnull(cramt,0)),0) - ifnull(sum(ifnull(dramt,0)),0) as begOut from " + tableName + " where substring(acno,3,2) = '" + acctcode + "' and dt <='" + yearstart + "' and substring(acno,1,2) = '" + orgnBrCode + "' and  trantype<>8 ").getResultList();
                                        }

                                        Vector vector2 = (Vector) list1.get(0);
                                        BigDecimal begOut2 = new BigDecimal("0");
                                        begOut2 = new BigDecimal(vector2.get(0).toString());
                                        begOut = (begOut2.subtract(begOut1));
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list2 = em.createNativeQuery("select ifnull(sum(dramt),0) as demand from " + tableName + " where substring(acno,3,2) = '" + acctcode + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and  trantype<>8 ").getResultList();
                                        } else {
                                            list2 = em.createNativeQuery("select ifnull(sum(dramt),0) as demand from " + tableName + " where substring(acno,3,2) = '" + acctcode + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and substring(acno,1,2) = '" + orgnBrCode + "' and  trantype<>8 ").getResultList();
                                        }

                                        Vector vector4 = (Vector) list2.get(0);
                                        demand = new BigDecimal(vector4.get(0).toString());
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list3 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) - ifnull(sum(ifnull(dramt,0)),0) as recovered from " + tableName + " where substring(acno,3,2) = '" + acctcode + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "'  and  trantype<>8").getResultList();
                                        } else {
                                            list3 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) - ifnull(sum(ifnull(dramt,0)),0) as recovered from " + tableName + " where substring(acno,3,2) = '" + acctcode + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and substring(acno,1,2) = '" + orgnBrCode + "' and  trantype<>8").getResultList();
                                        }

                                        Vector vector5 = (Vector) list3.get(0);
                                        BigDecimal recovered1 = new BigDecimal("0");
                                        recovered = new BigDecimal(vector5.get(0).toString());
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list4 = em.createNativeQuery("select ifnull(sum(dramt),0) as adv from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and trantype = 2   and  trantype<>8 ").getResultList();
                                        } else {
                                            list4 = em.createNativeQuery("select ifnull(sum(dramt),0) as adv from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt > '" + yearstart + "'and dt <'" + Frdt + "' and trantype = 2  and substring(acno,1,2) = '" + orgnBrCode + "' and  trantype<>8 ").getResultList();
                                        }

                                        Vector vector8 = (Vector) list4.get(0);
                                        adv = new BigDecimal(vector8.get(0).toString());
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list5 = em.createNativeQuery("select ifnull(sum(cramt),0)  as begDue from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt <='" + yearstart + "' ").getResultList();
                                        } else {
                                            list5 = em.createNativeQuery("select ifnull(sum(cramt),0)  as begDue from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt <='" + yearstart + "' and substring(acno,1,2) = '" + orgnBrCode + "'").getResultList();
                                        }

                                        Vector vector9 = (Vector) list5.get(0);
                                        BigDecimal begDue1 = new BigDecimal("0");
                                        begDue1 = new BigDecimal(vector9.get(0).toString());
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list5 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where substring(acno,3,2) ='" + acctcode + "' and duedt <='" + yearstart + "'  ").getResultList();
                                        } else {
                                            list5 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where substring(acno,3,2) ='" + acctcode + "' and duedt <='" + yearstart + "' and substring(acno,1,2) = '" + orgnBrCode + "' ").getResultList();
                                        }

                                        Vector vector10 = (Vector) list5.get(0);
                                        BigDecimal begDue2 = new BigDecimal("0");
                                        begDue2 = new BigDecimal(vector10.get(0).toString());
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list5 = em.createNativeQuery("select ifnull(sum(dramt),0) as begDue from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt <='" + yearstart + "' and trantype <> 2  ").getResultList();
                                        } else {
                                            list5 = em.createNativeQuery("select ifnull(sum(dramt),0) as begDue from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt <='" + yearstart + "' and trantype <> 2  and substring(acno,1,2) = '" + orgnBrCode + "'").getResultList();
                                        }

                                        Vector vector11 = (Vector) list5.get(0);
                                        BigDecimal begDue3 = new BigDecimal("0");
                                        begDue3 = new BigDecimal(vector11.get(0).toString());

                                        begDue = ((begDue1.subtract(begDue2)).subtract(begDue3));
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list6 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) - ifnull(sum(ifnull(dramt,0)),0) as closeDue from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt <='" + Frdt + "' ").getResultList();
                                        } else {
                                            list6 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) - ifnull(sum(ifnull(dramt,0)),0) as closeDue from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt <='" + Frdt + "' and substring(acno,1,2) = '" + orgnBrCode + "'").getResultList();
                                        }

                                        Vector vector12 = (Vector) list6.get(0);
                                        BigDecimal closeDue1 = new BigDecimal("0");
                                        closeDue1 = new BigDecimal(vector12.get(0).toString());
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list6 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where substring(acno,3,2) ='" + acctcode + "' and duedt <='" + Frdt + "'").getResultList();
                                        } else {
                                            //list6 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where substring(acno,3,2) ='" + acctcode + "' and duedt < ='" + Frdt + "' and substring(acno,1,2) = '" + orgnBrCode + "'and substring(acno,1,2) = '" + orgnBrCode + "'").getResultList(); 
                                            list6 = em.createNativeQuery("select ifnull(sum(prinamt),0) from emidetails where substring(acno,3,2) ='" + acctcode + "' and duedt <='" + Frdt + "' and substring(acno,1,2) = '" + orgnBrCode + "'and substring(acno,1,2) = '" + orgnBrCode + "'").getResultList();
                                        }

                                        Vector vector13 = (Vector) list6.get(0);
                                        BigDecimal closeDue2 = new BigDecimal("0");
                                        closeDue2 = new BigDecimal(vector13.get(0).toString());

                                        closeDue = ((closeDue1.subtract(closeDue2)));
                                        if (orgnBrCode.equalsIgnoreCase("0A")) {
                                            list7 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) - ifnull(sum(ifnull(dramt,0)),0) as closeOut from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt <='" + Frdt + "'  and  trantype<>8 ").getResultList();
                                        } else {
                                            list7 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) - ifnull(sum(ifnull(dramt,0)),0) as closeOut from " + tableName + " where substring(acno,3,2) ='" + acctcode + "' and dt <='" + Frdt + "' and substring(acno,1,2) = '" + orgnBrCode + "' and  trantype<>8 ").getResultList();
                                        }

                                        Vector vector15 = (Vector) list7.get(0);
                                        BigDecimal closeOut1 = new BigDecimal("0");
                                        closeOut = new BigDecimal(vector15.get(0).toString());

                                        closeArrear = new BigDecimal("0");

                                        AbstractWorkingStmtLoansPojo pojo = new AbstractWorkingStmtLoansPojo();
                                        pojo.setAcDesc(acDesc);
                                        pojo.setBegOut(begOut);
                                        pojo.setDemand(demand);
                                        pojo.setRecovered(recovered);
                                        pojo.setAdv(adv);
                                        pojo.setBegDue(begDue);
                                        pojo.setCloseDue(closeDue);
                                        pojo.setCloseOut(closeOut);
                                        pojo.setCloseArrear(new BigDecimal(0));
                                        repAbstractLoansList.add(pojo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return repAbstractLoansList;

    }

    /* Athar Raja */
    public List<AcTypeWiseDisdreceiptPojo> getAcTypeWiseDisdreceipt(String tillDate, String acctCode, String branchCode, String reportType) throws ApplicationException {
        List<AcTypeWiseDisdreceiptPojo> returnlist = new ArrayList<AcTypeWiseDisdreceiptPojo>();
        String acType = null, acNo;
        String acctType, custName;
        String tableName = null;
        Vector vtrmain, vtrsub;
        DecimalFormat df = new DecimalFormat("#.##");
        double amtDis = 0d, sansonLimit = 0d, balance = 0d, rangeFrom = 0d, rangeTo = 0d;

        try {
            if (acctCode.equalsIgnoreCase("ALL")) {
                List acctCodeNatureList = em.createNativeQuery("select acctcode,acctnature from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "') And CrDbFlag in('B','D') order by acctcode").getResultList();
                for (int i = 0; i < acctCodeNatureList.size(); i++) {
                    Vector vtr = (Vector) acctCodeNatureList.get(i);
                    acType = vtr.get(0).toString();

                    List rangeList = em.createNativeQuery("select * from cbs_loan_consolidate_range_master order by ac_type, from_range, to_range").getResultList();
                    if (!rangeList.isEmpty()) {
                        for (int j = 0; j < rangeList.size(); j++) {
                            Vector rangV = (Vector) rangeList.get(j);
                            acctType = rangV.get(1).toString();
                            rangeFrom = Double.parseDouble(rangV.get(2).toString());
                            rangeTo = Double.parseDouble(rangV.get(3).toString());

                            if (acType.equalsIgnoreCase(acctType)) {
                                String acNat = fts.getAcNatureByCode(acctType);
                                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    tableName = "ca_recon";
                                } else {
                                    tableName = "loan_recon";
                                }

                                List result;
                                if (branchCode.equalsIgnoreCase("0A")) {
                                    result = em.createNativeQuery("select r.acno ,l.sanctionlimit,l.CustName from " + tableName + " r,loan_appparameter l,accountmaster am where r.acno=l.acno and r.acno=am.acno and  r.trantype in(0,1,2) and r.ty=0 and dt <='" + tillDate + "' and am.accttype='" + acctType + "'  group by  r.acno ,l.sanctionlimit,l.CustName  order by r.acno").getResultList();
                                } else {
                                    result = em.createNativeQuery("select r.acno ,l.sanctionlimit,l.custName from " + tableName + " r,loan_appparameter l,accountmaster am where r.acno=l.acno and r.acno=am.acno and  r.trantype in(0,1,2) and r.ty=0 and dt <='" + tillDate + "' and am.accttype='" + acctType + "' and am.curbrcode='" + branchCode + "' group by  r.acno ,l.sanctionlimit,l.CustName order by r.acno").getResultList();
                                }
                                if (!result.isEmpty()) {
                                    for (int k = 0; k < result.size(); k++) {
                                        AcTypeWiseDisdreceiptPojo pojo = new AcTypeWiseDisdreceiptPojo();
                                        vtrmain = (Vector) result.get(k);
                                        acNo = vtrmain.get(0).toString();
                                        sansonLimit = Double.parseDouble(vtrmain.get(1).toString());
                                        custName = vtrmain.get(2).toString();
                                        balance = openingBalance(acNo, tillDate);

                                        if ((sansonLimit > rangeFrom) && (sansonLimit <= rangeTo)) {
                                            List result1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + tableName + "   where dt <= '" + tillDate + "'  and trantype <> 8 and trandesc <15 and acno='" + acNo + "'").getResultList();
                                            if (!result1.isEmpty()) {
                                                vtrsub = (Vector) result1.get(0);
                                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                            }
                                            pojo.setAmountSanc(BigDecimal.valueOf(sansonLimit));
                                            pojo.setAmtDis(BigDecimal.valueOf(amtDis));
                                            pojo.setCustName(custName);
                                            pojo.setBalance(BigDecimal.valueOf(balance));
                                            pojo.setRangeFrom(BigDecimal.valueOf(rangeFrom));
                                            pojo.setRangeTo(BigDecimal.valueOf(rangeTo));
                                            pojo.setAcType(acType);
                                            pojo.setAccountNumber(acNo);
                                            returnlist.add(pojo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                List acctCodeNatureList = em.createNativeQuery("select acctcode,acctNature from accounttypemaster where acctcode in('" + acctCode + "') order by acctcode").getResultList();
                for (int i = 0; i < acctCodeNatureList.size(); i++) {
                    Vector vtr = (Vector) acctCodeNatureList.get(i);
                    acType = vtr.get(0).toString();
                    List rangeList = em.createNativeQuery("select * from cbs_loan_consolidate_range_master order by ac_type, from_range, to_range").getResultList();
                    if (!rangeList.isEmpty()) {
                        for (int j = 0; j < rangeList.size(); j++) {
                            Vector rangV = (Vector) rangeList.get(j);
                            acctType = rangV.get(1).toString();
                            rangeFrom = Double.parseDouble(rangV.get(2).toString());
                            rangeTo = Double.parseDouble(rangV.get(3).toString());

                            if (acctType.equalsIgnoreCase(acType)) {
                                String acNat = fts.getAcNatureByCode(acctType);
                                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    tableName = "ca_recon";
                                } else {
                                    tableName = "loan_recon";
                                }
                                List result;
                                if (branchCode.equalsIgnoreCase("0A")) {
                                    result = em.createNativeQuery("select r.acno ,l.sanctionlimit,l.CustName from " + tableName + " r,loan_appparameter l,accountmaster am where r.acno=l.acno and r.acno=am.acno and  r.trantype in(0,1,2) and r.ty=0 and dt <='" + tillDate + "' and am.accttype='" + acctType + "'  group by  r.acno ,l.sanctionlimit,l.CustName  order by r.acno").getResultList();
                                } else {
                                    result = em.createNativeQuery("select r.acno ,l.sanctionlimit,l.custName from " + tableName + " r,loan_appparameter l,accountmaster am where r.acno=l.acno and r.acno=am.acno and  r.trantype in(0,1,2) and r.ty=0 and dt <='" + tillDate + "' and am.accttype='" + acctType + "' and am.curbrcode='" + branchCode + "' group by  r.acno ,l.sanctionlimit,l.CustName order by r.acno").getResultList();
                                }
                                if (!result.isEmpty()) {
                                    for (int k = 0; k < result.size(); k++) {
                                        AcTypeWiseDisdreceiptPojo pojo = new AcTypeWiseDisdreceiptPojo();
                                        vtrmain = (Vector) result.get(k);
                                        acNo = vtrmain.get(0).toString();
                                        sansonLimit = Double.parseDouble(vtrmain.get(1).toString());
                                        custName = vtrmain.get(2).toString();
                                        balance = openingBalance(acNo, tillDate);
                                        if ((sansonLimit > rangeFrom) && (sansonLimit <= rangeTo)) {
                                            List result1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + tableName + "   where dt <= '" + tillDate + "'  and trantype <> 8 and trandesc <15 and acno='" + acNo + "'").getResultList();
                                            if (!result1.isEmpty()) {
                                                vtrsub = (Vector) result1.get(0);
                                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                            }
                                            pojo.setAmountSanc(BigDecimal.valueOf(sansonLimit));
                                            pojo.setAmtDis(BigDecimal.valueOf(amtDis));
                                            pojo.setCustName(custName);
                                            pojo.setBalance(BigDecimal.valueOf(balance));
                                            pojo.setRangeFrom(BigDecimal.valueOf(rangeFrom));
                                            pojo.setRangeTo(BigDecimal.valueOf(rangeTo));
                                            pojo.setAcType(acType);
                                            pojo.setAccountNumber(acNo);
                                            returnlist.add(pojo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage());
        }
        return returnlist;
    }

    public List getChargeName() throws ApplicationException {
        try {
            return em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no = '108'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List depositAcType() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("Select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.FIXED_AC + "') and CrDbFlag in ('C')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List<LienReportPojo> getDepositLienReport(String acctType, String asOnDate, String brCode) throws ApplicationException {
        List<LienReportPojo> reportList = new ArrayList<LienReportPojo>();
        String custName = "", Acno = "", remark = "", dateFormat = "", custid = "";
        int SrNo = 0;
        double voucherNo = 0d;
        double lienValue = 0d;
        List lienList = null;
        try {
            String AcTypeQuery = "";
            String BranchQuery = "";
            asOnDate = ymdFormat.format(dmyFormat.parse(asOnDate));
            if (!(brCode.equalsIgnoreCase("All") || brCode.equalsIgnoreCase("0A"))) {
                BranchQuery = " and substring(a.acno,1,2)= " + brCode + "";
            }
            if (!acctType.equalsIgnoreCase("All")) {
                AcTypeQuery = " and substring(a.acno,3,2) in (" + acctType + ")";
            } else {
                AcTypeQuery = "and substring(a.acno,3,2) in (Select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.FIXED_AC + "')and CrDbFlag in ('C'))";
            }
            lienList = em.createNativeQuery("select d.custid,c.custname,a.Acno,a.voucherno,date_format(txndate,'%d/%m/%Y') enterDate,"
                    + " a.Remarks, 0 lienvalue from td_lien_update a,td_vouchmst b,td_accountmaster c,customerid d "
                    + " where lienstatus = 'Y' and a.acno = b.acno and a.acno = c.acno and a.acno = d.acno"
                    + " and a.voucherno = b.VoucherNo " + BranchQuery + " " + AcTypeQuery + " and date_format(txndate,'%Y%m%d') <=" + asOnDate + " group by acno,voucherno"
                    + " union all"
                    + " select c.custid,b.custname,a.Acno,0 voucherno,date_format(Entrydate,'%d/%m/%Y') enterDate,Remarks,lienvalue from loansecurity a,accountmaster b,customerid c "
                    + "where Status = 'ACTIVE' and a.acno = b.acno and a.acno = c.acno " + BranchQuery + " " + AcTypeQuery + " and entrydate <= " + asOnDate + " order by 1,3,4 ").getResultList();

            if (!lienList.isEmpty()) {
                for (int i = 0; i < lienList.size(); i++) {
                    SrNo = i + 1;
                    LienReportPojo pojo = new LienReportPojo();
                    Vector vtr = (Vector) lienList.get(i);
                    custid = vtr.get(0).toString();
                    custName = vtr.get(1).toString();
                    Acno = vtr.get(2).toString();
                    voucherNo = Double.parseDouble(vtr.get(3).toString());
                    dateFormat = vtr.get(4).toString();
                    remark = vtr.get(5).toString();
                    lienValue = Double.parseDouble(vtr.get(6).toString());

                    pojo.setSrNo(SrNo);
                    pojo.setAcNo(Acno);
                    pojo.setCustName(custName);
                    pojo.setRemark(remark);
                    pojo.setVoucherNo(BigDecimal.valueOf(voucherNo));
                    pojo.setLienValues(BigDecimal.valueOf(lienValue));
                    pojo.setMatDt(dateFormat);
                    reportList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return reportList;
    }

    public List<LienReportPojo> getLienReport(String acctType, String asOnDate, String brCode) throws ApplicationException {
        List<LienReportPojo> reportList = new ArrayList<LienReportPojo>();
        List lienList1;
        List lienList2;
        String custName = null, disbDt = null;
        String oldAcNo = "", oldLienAcNo = "", reportParameter = "";
        double balance = 0d, disbAmt = 0d, matValue = 0d;
        try {
            //  lienList1 = em.createNativeQuery("select acno,lienAcno from loansecurity where substring(lienAcno,3,2) in (select acctcode from accounttypemaster where acctNature in('" + type + "')) and substring(acno,3,2)='" + acctType + "' and substring(acno,1,2)='" + brCode + "'and issuedate < '" + asOnDate + "'").getResultList();
            List parameterStatus = em.createNativeQuery("select code from cbs_parameterinfo where name = 'LIEN_PREVIOUS'").getResultList();
            if (!parameterStatus.isEmpty()) {
                Vector paraStatusvect = (Vector) parameterStatus.get(0);
                reportParameter = paraStatusvect.get(0).toString();
                if (ymdFormat.parse(reportParameter).after(ymdFormat.parse(asOnDate))) {
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (acctType.equalsIgnoreCase("ALL")) {
                            //   lienList1 = em.createNativeQuery("select acno,lienacno from loansecurity where substring(lienacno,3,2) in ('03','05','09','15','18','21','22') and status = 'Active' and entrydate <='" + asOnDate + "' order by substring(lienacno,3,2),substring(acno,3,2)").getResultList();
                            lienList1 = em.createNativeQuery("select distinct acno from loansecurity where substring(lienacno,3,2) in (select acctcode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) and status = 'ACTIVE' and entrydate<='" + asOnDate + "' "
                                    + " union all "
                                    + " select distinct acno  from loansecurity where substring(lienacno,3,2) in (select acctcode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) and ExpiryDate >='20141014' and entrydate<='" + asOnDate + "'  order by acno").getResultList();

                            //                            + "select distinct acno,lienacno from loansecurity where substring(lienacno,3,2) in (select acctcode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) and status = 'Active' and entrydate <='" + asOnDate + "'  "
                            //                            + " union all "
                            //                            + " select distinct acno,lienacno from loansecurity where substring(lienacno,3,2) in (select acctcode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) and status = 'EXPIRED' and entrydate <='" + asOnDate + "' and ExpiryDate >='20141014'  order by acno,lienacno").getResultList();
                        } else {
                            lienList1 = em.createNativeQuery("select distinct acno from loansecurity where substring(lienacno,3,2) in ('" + acctType + "') and status = 'ACTIVE' and entrydate<='" + asOnDate + "' "
                                    + " union all "
                                    + " select distinct acno  from loansecurity where substring(lienacno,3,2) in ('" + acctType + "') and ExpiryDate >='20141014' and entrydate<='" + asOnDate + "'  order by acno").getResultList();
                        }
                    } else {
                        if (acctType.equalsIgnoreCase("ALL")) {
                            lienList1 = em.createNativeQuery("select distinct acno,lienacno,matdate from loansecurity where substring(lienacno,3,2) in (select acctCode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) and status = 'Active' and substring(lienacno,1,2)='" + brCode + "' and entrydate <='" + asOnDate + "' order by acno,lienacno").getResultList();
                        } else {
                            lienList1 = em.createNativeQuery("select distinct acno,lienacno,matdate from loansecurity where substring(lienacno,3,2) in ('" + acctType + "') and status = 'Active' and substring(lienacno,1,2)='" + brCode + "' and entrydate <='" + asOnDate + "' order by acno,lienacno").getResultList();
                        }
                    }
                    if (lienList1.isEmpty()) {
                        throw new ApplicationException("Data does not Exit table in loansecurity");
                    }
                    String acNo = null;
                    //            int firstPrint = 0;

                    Double amt = 0d;
                    if (!lienList1.isEmpty()) {
                        for (int i = 0; i < lienList1.size(); i++) {
                            Double MatVal = 0d;
                            LienReportPojo pojo = new LienReportPojo();
                            Vector lienV = (Vector) lienList1.get(i);
                            acNo = lienV.get(0).toString();
                            //                    String lienAcNo = lienV.get(1).toString();
                            String acctCode = fts.getAccountCode(acNo);
                            String acNat = fts.getAcNatureByCode(acctCode);

                            //                    for (int j = 0; j < lienList1.size(); j++) {
                            //                        Vector lienV1 = (Vector) lienList1.get(j);
                            //                        acNo = lienV1.get(0).toString();
                            String acNature = common.getAcNatureByAcNo(acNo);
                            List amountList = null;
                            List lienSecAcNoList = null;
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from ca_recon where acno = '" + acNo + "' and dt <='" + asOnDate + "'").getResultList();
                                //                            lienSecAcNoList = em.createNativeQuery("select ifnull(sum(ifnull(matvalue,0)),0) from loansecurity where acno = '" + acNo + "' and status = 'ACTIVE' AND MATDATE >='" + asOnDate + "'  AND LIENACNO IS NOT NULL").getResultList();
                                lienSecAcNoList = em.createNativeQuery("select sum(a.amt) from (select ifnull(sum(ifnull(matvalue,0)),0) as amt from loansecurity where acno = '" + acNo + "' and status = 'ACTIVE' and matdate >='" + asOnDate + "' and lienacno is not null"
                                        + " union all "
                                        + " select ifnull(sum(ifnull(matvalue,0)),0) as amt from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and ExpiryDate >='20141014' and entrydate<='" + asOnDate + "' and lienacno is not null ) a"
                                        + "").getResultList();
                            } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                amountList = em.createNativeQuery("select (ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) from loan_recon where acno = '" + acNo + "' and dt <='" + asOnDate + "'").getResultList();
                                //                            lienSecAcNoList = em.createNativeQuery("select ifnull(sum(ifnull(matvalue,0)),0) from loansecurity where acno = '" + acNo + "' AND STATUS = 'ACTIVE' AND ENTRYDATE<='" + asOnDate + "' AND LIENACNO IS NOT NULL").getResultList();
                                lienSecAcNoList = em.createNativeQuery("select sum(a.amt) from (select ifnull(sum(ifnull(matvalue,0)),0) as amt from loansecurity where acno = '" + acNo + "' and status = 'ACTIVE' and entrydate<='" + asOnDate + "' and lienacno is not null "
                                        + " union all "
                                        + " select ifnull(sum(ifnull(matvalue,0)),0) as amt from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and ExpiryDate >='20141014' and entrydate<='" + asOnDate + "' and lienacno is not null ) a"
                                        + " ").getResultList();
                            }
                            Vector element = (Vector) amountList.get(0);
                            balance = Double.parseDouble(element.get(0).toString());
                            Double amt1 = balance;

                            Vector lienSecAcNoVect = (Vector) lienSecAcNoList.get(0);
                            Double lienSecAcNoAmt = Double.parseDouble(lienSecAcNoVect.get(0).toString());
                            if (lienSecAcNoAmt <= amt1) {
                                amt1 = lienSecAcNoAmt; //amt1 - lienSecAcNoAmt;
                            } else if (lienSecAcNoAmt > amt1) {
                                amt1 = amt1;
                            }

                            List custDetails = em.createNativeQuery("select am.custname, ifnull(sum(ca.dramt),0),ca.dt from " + common.getTableName(acNature) + " ca,accountmaster "
                                    + "am where ca.dt = (select min(dt)from " + common.getTableName(acNature) + " where acno='" + acNo + "') and ca.acno = '" + acNo
                                    + "' and ca.acno = am.acno  group by am.custname,ca.dt").getResultList();

                            Vector custVect = (Vector) custDetails.get(0);
                            custName = custVect.get(0).toString();
                            disbAmt = Double.parseDouble(custVect.get(1).toString());
                            disbDt = custVect.get(2).toString();

                            pojo.setAcNo(acNo);
                            pojo.setCustName(custName);
                            pojo.setDisbAmt(disbAmt);
                            pojo.setLienAcNo("");
                            pojo.setOutStandBal(balance);
                            pojo.setActualValue(amt1);
                            pojo.setDisbDt(dmyFormat.format(ymdFormat.parse(disbDt)));
                            pojo.setLienValue(lienSecAcNoAmt);
                            oldAcNo = acNo;
                            //                        oldLienAcNo = lienAcNo;
                            //                        firstPrint = 1;
                            reportList.add(pojo);

                            //                        pojo = new LienReportPojo();
                            //                        if (firstPrint == 0) {
                            //                            pojo.setAcNo(acNo);
                            //                            pojo.setCustName(custName);
                            //                            pojo.setDisbAmt(disbAmt);
                            //                            pojo.setLienAcNo(lienAcNo);
                            //                            pojo.setOutStandBal(balance);
                            //                            pojo.setActualValue(amt1);
                            //                            pojo.setDisbDt(dmyFormat.format(ymdFormat.parse(disbDt)));
                            //                            pojo.setLienValue(matValue);
                            //                            oldAcNo = acNo;
                            //                            oldLienAcNo = lienAcNo;
                            //                            firstPrint = 1;
                            //                            reportList.add(pojo);
                            //                        } else {
                            //                            if (oldAcNo.equalsIgnoreCase(acNo)) {
                            //                                if (oldLienAcNo.equalsIgnoreCase(lienAcNo)) {
                            //                                    pojo.setAcNo(acNo);
                            //                                    pojo.setCustName("");
                            //                                    pojo.setDisbAmt(0.00);
                            //                                    pojo.setLienAcNo(lienAcNo);
                            //                                    pojo.setOutStandBal(0.00);
                            //                                    pojo.setDisbDt("");
                            //                                    pojo.setLienValue(0.00);
                            //                                    pojo.setActualValue(0.00);
                            //                                    oldAcNo = acNo;
                            //                                    oldLienAcNo = lienAcNo;
                            //                                    reportList.add(pojo);
                            //                                } else {
                            //                                    pojo.setAcNo(acNo);
                            //                                    pojo.setCustName("");
                            //                                    pojo.setDisbAmt(0.00);
                            //                                    pojo.setLienAcNo(lienAcNo);
                            //                                    pojo.setActualValue(0.00);
                            //                                    pojo.setOutStandBal(0.00);
                            //                                    pojo.setDisbDt("");
                            //                                    pojo.setLienValue(matValue);
                            //                                    oldAcNo = acNo;
                            //                                    oldLienAcNo = lienAcNo;
                            //                                    reportList.add(pojo);
                            //                                }
                            //                            } else {
                            //                                pojo.setAcNo(acNo);
                            //                                pojo.setCustName(custName);
                            //                                pojo.setDisbAmt(disbAmt);
                            //                                pojo.setLienAcNo(lienAcNo);
                            //                                pojo.setOutStandBal(balance);
                            //                                pojo.setDisbDt(dmyFormat.format(ymdFormat.parse(disbDt)));
                            //                                pojo.setLienValue(matValue);
                            //                                pojo.setActualValue(amt1);
                            //                                oldAcNo = acNo;
                            //                                oldLienAcNo = lienAcNo;
                            //                                reportList.add(pojo);
                            //                            }
                            //                        }
                            //                        System.out.println("acNo:"+acNo+";custName:"+custName+";disbAmt:"+disbAmt+";outStandBal:"+balance+";disbDt:"+disbDt+";lienAcNo:"+lienAcNo+";lienValue:"+matValue);
                            //                    }
                        }
                    }
                } else {
                    reportList = getLienReportNew(acctType.substring(acctType.indexOf(".") + 1), asOnDate, brCode);
                }
            } else {
                reportList = getLienReportNewOptimized(acctType.substring(acctType.indexOf(".") + 1), asOnDate, brCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return reportList;
    }

    public List<LienReportPojo> getLienReportNew(String acctType, String asOnDate, String brCode) throws ApplicationException {
        List<LienReportPojo> reportList = new ArrayList<LienReportPojo>();
        List lienList1;
        List lienList2;
        List list;
        String custName = null, disbDt = null;
        String oldAcNo = "", oldLienAcNo = "", acType1 = acctType;
        double balance = 0d, disbAmt = 0d, matValue = 0d;
        try {
            if (acctType.equalsIgnoreCase("ALL")) {
                list = em.createNativeQuery("select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')").getResultList();
            } else {
                list = em.createNativeQuery("select acctCode from accounttypemaster where acctCode = '" + acctType + "'").getResultList();
            }
            for (int k = 0; k < (acType1.contains("ALL") ? 1 : list.size()); k++) {
                Vector vtr = (Vector) list.get(k);
                String acCode = vtr.get(0).toString();
                if (acType1.contains("ALL")) {
                    acctType = "ALL";
                } else {
                    acctType = acCode;
                }
                //  lienList1 = em.createNativeQuery("select acno,lienAcno from loansecurity where substring(lienAcno,3,2) in (select acctcode from accountTypeMaster where acctNature in('" + type + "')) and substring(acno,3,2)='" + acctType + "' and substring(acno,1,2)='" + brCode + "'and issuedate < '" + asOnDate + "'").getResultList();
                if (brCode.equalsIgnoreCase("0A") || brCode.equalsIgnoreCase("90")) {
                    if (acctType.equalsIgnoreCase("ALL")) {
                        //   lienList1 = em.createNativeQuery("select acno,lienacno from loansecurity where substring(lienacno,3,2) in ('03','05','09','15','18','21','22') and status = 'Active' and entrydate <='" + asOnDate + "' order by substring(lienacno,3,2),substring(acno,3,2)").getResultList();
                        lienList1 = em.createNativeQuery("select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0),ifnull(a.IntTableCode,''),ifnull(turn_over_detail_flag,'N'), "
                                + " (select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2)) "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c  "
                                + "where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) "
                                + "and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and a.entrydate <='" + asOnDate + "' "
                                + " union all "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0),ifnull(a.IntTableCode,''),ifnull(turn_over_detail_flag,'N'), "
                                + "(select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2)) "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c "
                                + "where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) "
                                + "and a.status = 'EXPIRED' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE  and a.entrydate <='" + asOnDate + "' and a.expirydate >='" + asOnDate + "' "
                                + " order by acno,lienacno").getResultList();
                    } else {
                        lienList1 = em.createNativeQuery("select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0),ifnull(a.IntTableCode,''),ifnull(turn_over_detail_flag,'N'),"
                                + "(select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2)) "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c "
                                + " where substring(a.lienacno,3,2) in ('" + acctType + "') and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and a.entrydate <='" + asOnDate + "'  "
                                + " union all "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0),ifnull(a.IntTableCode,''),ifnull(turn_over_detail_flag,'N'), "
                                + "(select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2)) "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c "
                                + " where substring(a.lienacno,3,2) in ('" + acctType + "') and a.status = 'EXPIRED' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE  and a.entrydate <='" + asOnDate + "' and a.expirydate >='" + asOnDate + "' "
                                + " order by acno,lienacno").getResultList();
                    }
                } else {
                    if (acctType.equalsIgnoreCase("ALL")) {
                        lienList1 = em.createNativeQuery("select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0),ifnull(a.IntTableCode,''),ifnull(turn_over_detail_flag,'N'), "
                                + "(select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2)) "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c "
                                + "where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) "
                                + "and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and substring(a.lienacno,1,2)='" + brCode + "' and a.entrydate <='" + asOnDate + "'  "
                                + " union all "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0),a.IntTableCode,ifnull(turn_over_detail_flag,'N'), "
                                + "(select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2)) "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c "
                                + "where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) "
                                + "and a.status = 'EXPIRED' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and substring(a.lienacno,1,2)='" + brCode + "'   and a.entrydate <='" + asOnDate + "' and a.expirydate >='" + asOnDate + "' "
                                + " order by acno,lienacno").getResultList();
                    } else {
                        lienList1 = em.createNativeQuery("select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0),ifnull(a.IntTableCode,''),ifnull(turn_over_detail_flag,'N'), "
                                + "(select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2)) "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c "
                                + "where substring(a.lienacno,3,2) in ('" + acctType + "') and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE "
                                + "and substring(a.lienacno,1,2)='" + brCode + "' and a.entrydate <='" + asOnDate + "'  "
                                + " union all "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0),a.IntTableCode,ifnull(turn_over_detail_flag,'N'), "
                                + "(select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2)) "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c "
                                + "where substring(a.lienacno,3,2) in ('" + acctType + "') and a.status = 'EXPIRED' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE "
                                + "and substring(a.lienacno,1,2)='" + brCode + "'   and a.entrydate <='" + asOnDate + "' and a.expirydate >='" + asOnDate + "' "
                                + " order by acno,lienacno").getResultList();
                    }
                }
//            if (lienList1.isEmpty()) {
//                throw new ApplicationException("Data does not Exit table in loansecurity");
//            }
                String acNo = null;
                int firstPrint = 0;

                Double amt = 0d;
                if (!lienList1.isEmpty()) {
                    for (int i = 0; i < lienList1.size(); i++) {
                        Double MatVal = 0d, securityRoi = 0d;
                        LienReportPojo pojo = new LienReportPojo();
                        Vector lienV = (Vector) lienList1.get(i);
                        acNo = lienV.get(0).toString();
                        String lienAcNo = lienV.get(1).toString();
                        securityRoi = Double.parseDouble(lienV.get(3).toString());
                        String intTableCode = lienV.get(4).toString();
                        String turnOverFlag = lienV.get(5).toString();
                        //String acctCode = fts.getAccountCode(acNo);
                        String acNat = lienV.get(6).toString();//fts.getAcNatureByCode(acctCode);
                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            if (brCode.equalsIgnoreCase("0A") || brCode.equalsIgnoreCase("90")) {
                                //      lienList2 = em.createNativeQuery("select am.custname, ifnull(ca.dramt,0),ca.dt from ca_recon ca,accountMaster am where ca.dt = (select min (dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno").getResultList();
                                //    lienList2 = em.createNativeQuery("select am.custname, ifnull(SUM(ca.dramt),0),ca.dt from ca_recon ca,accountMaster am where ca.dt = (select min (dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno group by am.custname,ca.dt").getResultList();
                                lienList2 = em.createNativeQuery("select am.custname, ifnull(SUM(ca.dramt),0),ca.dt,lo.MATVALUE from ca_recon ca,accountmaster am,loansecurity lo where ca.dt = (select min(dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno AND lo.STATUS = 'ACTIVE' and lo.lienacno ='" + lienAcNo + "' AND MATDATE >='" + asOnDate + "'  AND LIENACNO IS NOT NULL group by am.custname,ca.dt,lo.MATVALUE "
                                        + "union all "
                                        + " select am.custname, ifnull(SUM(ca.dramt),0),ca.dt,lo.MATVALUE from ca_recon ca,accountmaster am,loansecurity lo where ca.dt = (select min(dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno AND lo.STATUS = 'EXPIRED' AND lo.ENTRYDATE<='" + asOnDate + "' and lo.expirydate >='" + asOnDate + "' and lo.lienacno ='" + lienAcNo + "' AND MATDATE >='" + asOnDate + "'  AND LIENACNO IS NOT NULL group by am.custname,ca.dt,lo.MATVALUE").getResultList();
                            } else {
                                //    lienList2 = em.createNativeQuery("select am.custname, ifnull(SUM(ca.dramt),0),ca.dt from ca_recon ca,accountMaster am where ca.dt = (select min (dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno and substring(ca.acno,1,2)='" + brCode + "' group by am.custname,ca.dt").getResultList();
                                lienList2 = em.createNativeQuery("select am.custname, ifnull(SUM(ca.dramt),0),ca.dt,lo.MATVALUE from ca_recon ca,accountmaster am,loansecurity lo where ca.dt = (select min(dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno AND lo.STATUS = 'ACTIVE' and lo.lienacno ='" + lienAcNo + "' and substring(ca.acno,1,2)='" + brCode + "'AND MATDATE >='" + asOnDate + "'  AND LIENACNO IS NOT NULL group by am.custname,ca.dt,lo.MATVALUE "
                                        + " union all "
                                        + " select am.custname, ifnull(SUM(ca.dramt),0),ca.dt,lo.MATVALUE from ca_recon ca,accountmaster am,loansecurity lo where ca.dt = (select min(dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno AND lo.STATUS = 'EXPIRED' AND lo.ENTRYDATE<='" + asOnDate + "' and lo.expirydate >='" + asOnDate + "'  and lo.lienacno ='" + lienAcNo + "' and substring(ca.acno,1,2)='" + brCode + "'AND MATDATE >='" + asOnDate + "'  AND LIENACNO IS NOT NULL group by am.custname,ca.dt,lo.MATVALUE").getResultList();
                            }
                        } else {
                            if (brCode.equalsIgnoreCase("0A") || brCode.equalsIgnoreCase("90")) {
                                // lienList2 = em.createNativeQuery("select a.custname,sum(ifnull(l.amtDisbursed,0)),l.DISBURSEMENTDT from accountMaster a, LoanDisbursement l,loansecurity lo  where a.acno=lo.acno and a.acno=l.acno  and a.acno='" + acNo + "'group by a.custname,l.DISBURSEMENTDT").getResultList();
                                lienList2 = em.createNativeQuery("select a.custname,sum(ifnull(l.amtDisbursed,0)),l.DISBURSEMENTDT,lo.MATVALUE from accountmaster a, loandisbursement l,loansecurity lo  where a.acno=lo.acno and a.acno=l.acno and a.acno='" + acNo + "' and lo.lienacno = '" + lienAcNo + "' AND lo.ENTRYDATE<='" + asOnDate + "' AND lo.STATUS = 'ACTIVE' AND LIENACNO IS NOT NULL group by a.custname,l.DISBURSEMENTDT,lo.MATVALUE "
                                        + " union all "
                                        + " select a.custname,sum(ifnull(l.amtDisbursed,0)),l.DISBURSEMENTDT,lo.MATVALUE from accountmaster a, loandisbursement l,loansecurity lo  where a.acno=lo.acno and a.acno=l.acno and a.acno='" + acNo + "' and lo.lienacno = '" + lienAcNo + "' AND lo.ENTRYDATE<='" + asOnDate + "' AND lo.STATUS = 'EXPIRED' and lo.expirydate >='" + asOnDate + "'  AND LIENACNO IS NOT NULL group by a.custname,l.DISBURSEMENTDT,lo.MATVALUE ").getResultList();
                            } else {
                                //    lienList2 = em.createNativeQuery("select a.custname,sum(ifnull(l.amtDisbursed,0)),l.DISBURSEMENTDT from accountMaster a, LoanDisbursement l,loansecurity lo  where a.acno=lo.acno and a.acno=l.acno and substring(a.acno,1,2)='" + brCode + "' and a.acno='" + acNo + "'group by a.custname,l.DISBURSEMENTDT").getResultList();
                                lienList2 = em.createNativeQuery("select a.custname,sum(ifnull(l.amtDisbursed,0)),l.DISBURSEMENTDT,lo.MATVALUE from accountmaster a, loandisbursement l,loansecurity lo  where a.acno=lo.acno and a.acno=l.acno and substring(a.acno,1,2)='" + brCode + "' and a.acno='" + acNo + "' and lo.lienacno = '" + lienAcNo + "' AND ENTRYDATE<='" + asOnDate + "' AND lo.STATUS = 'ACTIVE' AND LIENACNO IS NOT NULL group by a.custname,l.DISBURSEMENTDT,lo.MATVALUE "
                                        + " union all "
                                        + " select a.custname,sum(ifnull(l.amtDisbursed,0)),l.DISBURSEMENTDT,lo.MATVALUE from accountmaster a, loandisbursement l,loansecurity lo  where a.acno=lo.acno and a.acno=l.acno and substring(a.acno,1,2)='" + brCode + "' and a.acno='" + acNo + "' and lo.lienacno = '" + lienAcNo + "' AND ENTRYDATE<='" + asOnDate + "' AND lo.STATUS = 'EXPIRED'  and lo.expirydate >='" + asOnDate + "'  AND LIENACNO IS NOT NULL group by a.custname,l.DISBURSEMENTDT,lo.MATVALUE").getResultList();
                            }
                        }
                        if (lienList2.isEmpty()) {
                            lienList2 = em.createNativeQuery("select distinct am.custname, ifnull(SUM(ca.dramt),0),ca.dt,0.00 from ca_recon ca,accountmaster am,loansecurity lo where ca.dt = (select min(dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno AND lo.STATUS = 'ACTIVE' and lo.lienacno ='" + lienAcNo + "' group by am.custname,ca.dt,lo.MATVALUE "
                                    + " union all "
                                    + " select distinct am.custname, ifnull(SUM(ca.dramt),0),ca.dt,0.00 from ca_recon ca,accountmaster am,loansecurity lo where ca.dt = (select min(dt)from ca_recon where acno='" + acNo + "') and ca.acno = '" + acNo + "' and ca.acno = am.acno AND lo.STATUS = 'EXPIRED' AND lo.ENTRYDATE<='" + asOnDate + "' and lo.expirydate >='" + asOnDate + "' and lo.lienacno ='" + lienAcNo + "' group by am.custname,ca.dt,lo.MATVALUE").getResultList();
                        }

                        if (!lienList2.isEmpty()) {
                            for (int j = 0; j < lienList2.size(); j++) {
                                Vector lienV1 = (Vector) lienList2.get(j);
                                custName = lienV1.get(0).toString();
                                disbAmt = Double.parseDouble(lienV1.get(1).toString());
                                disbDt = lienV1.get(2).toString();
                                matValue = Double.parseDouble(lienV1.get(3).toString());
                                String matDt = lienV.get(2).toString();
                                String acNature = acNat;//common.getAcNatureByAcNo(acNo);
                                List amountList = null;
                                List lienSecAcNoList = null;
                                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    amountList = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(DRAMT,0)),0)-ifnull(SUM(ifnull(CRAMT,0)),0)) FROM ca_recon WHERE ACNO = '" + acNo + "' AND DT <='" + asOnDate + "'").getResultList();
                                    lienSecAcNoList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(MATVALUE,0)),0) FROM loansecurity WHERE ACNO = '" + acNo + "' AND /*STATUS = 'ACTIVE' AND MATDATE >='" + asOnDate + "' */ ENTRYDATE<='" + asOnDate + "' AND (EXPIRYDATE>='" + asOnDate + "' or EXPIRYDATE IS NULL) AND LIENACNO IS NOT NULL").getResultList();
                                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                    amountList = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(DRAMT,0)),0)-ifnull(SUM(ifnull(CRAMT,0)),0)) FROM loan_recon WHERE ACNO = '" + acNo + "' AND DT <='" + asOnDate + "'").getResultList();
                                    lienSecAcNoList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(MATVALUE,0)),0) FROM loansecurity WHERE ACNO = '" + acNo + "' AND /*STATUS = 'ACTIVE' AND*/ ENTRYDATE<='" + asOnDate + "'  AND (EXPIRYDATE>='" + asOnDate + "'  or EXPIRYDATE IS NULL) AND LIENACNO IS NOT NULL ").getResultList();
                                }
                                Vector element = (Vector) amountList.get(0);
                                balance = Double.parseDouble(element.get(0).toString());
                                Double amt1 = balance;

                                Vector lienSecAcNoVect = (Vector) lienSecAcNoList.get(0);
                                Double lienSecAcNoAmt = Double.parseDouble(lienSecAcNoVect.get(0).toString());
                                if (lienSecAcNoAmt <= amt1) {
                                    amt1 = lienSecAcNoAmt; //amt1 - lienSecAcNoAmt;
                                } else if (lienSecAcNoAmt > amt1) {
                                    amt1 = amt1;
                                }
                                double roi = 0;
                                if (turnOverFlag.equalsIgnoreCase("Y")) {
                                    roi = securityRoi + Double.parseDouble(loanInterestCalculationBean.getROI(intTableCode, matValue, asOnDate));
                                }

                                pojo = new LienReportPojo();
                                if (firstPrint == 0) {
                                    pojo.setAcNo(acNo);
                                    pojo.setCustName(custName);
                                    pojo.setDisbAmt(disbAmt);
                                    pojo.setLienAcNo(lienAcNo);
                                    pojo.setOutStandBal(balance);
                                    pojo.setActualValue(amt1);
                                    pojo.setDisbDt(dmyFormat.format(ymdFormat.parse(disbDt)));
                                    pojo.setLienValue(matValue);
                                    pojo.setMatDt(dmyFormat.format(ymdFormat.parse(matDt)));
                                    pojo.setRoi(roi);

                                    oldAcNo = acNo;
                                    oldLienAcNo = lienAcNo;
                                    firstPrint = 1;
                                    reportList.add(pojo);
                                } else {
                                    if (oldAcNo.equalsIgnoreCase(acNo)) {
                                        if (oldLienAcNo.equalsIgnoreCase(lienAcNo)) {
                                            pojo.setAcNo(acNo);
                                            pojo.setCustName("");
                                            pojo.setDisbAmt(0.00);
                                            pojo.setLienAcNo(lienAcNo);
                                            pojo.setOutStandBal(0.00);
                                            pojo.setDisbDt("");
                                            pojo.setLienValue(0.00);
                                            pojo.setActualValue(0.00);
                                            pojo.setRoi(0.00);
                                            oldAcNo = acNo;
                                            oldLienAcNo = lienAcNo;
                                            reportList.add(pojo);
                                        } else {
                                            pojo.setAcNo(acNo);
                                            pojo.setCustName("");
                                            pojo.setDisbAmt(0.00);
                                            pojo.setLienAcNo(lienAcNo);
                                            pojo.setActualValue(0.00);
                                            pojo.setOutStandBal(0.00);
                                            pojo.setDisbDt("");
                                            pojo.setLienValue(matValue);
                                            pojo.setRoi(0.00);
                                            oldAcNo = acNo;
                                            oldLienAcNo = lienAcNo;
                                            reportList.add(pojo);
                                        }
                                    } else {
                                        pojo.setAcNo(acNo);
                                        pojo.setCustName(custName);
                                        pojo.setDisbAmt(disbAmt);
                                        pojo.setLienAcNo(lienAcNo);
                                        pojo.setOutStandBal(balance);
                                        pojo.setDisbDt(dmyFormat.format(ymdFormat.parse(disbDt)));
                                        pojo.setLienValue(matValue);
                                        pojo.setActualValue(amt1);
                                        pojo.setMatDt(dmyFormat.format(ymdFormat.parse(matDt)));
                                        pojo.setRoi(roi);
                                        oldAcNo = acNo;
                                        oldLienAcNo = lienAcNo;
                                        reportList.add(pojo);
                                    }
                                }
//                        System.out.println("acNo:"+acNo+";custName:"+custName+";disbAmt:"+disbAmt+";outStandBal:"+balance+";disbDt:"+disbDt+";lienAcNo:"+lienAcNo+";lienValue:"+matValue);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return reportList;
    }

    public List<LienReportPojo> getLienReportNewOptimized(String acctType, String asOnDate, String brCode) throws ApplicationException {
        List<LienReportPojo> reportList = new ArrayList<LienReportPojo>();
        List lienList1;
        List lienList2 = new ArrayList();
        List list;
        String custName = null, disbDt = null;
        String oldAcNo = "", oldLienAcNo = "", acType1 = acctType;
        double balance = 0d, disbAmt = 0d, matValue = 0d;
        try {
            if (acctType.equalsIgnoreCase("ALL")) {
                list = em.createNativeQuery("select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')").getResultList();
            } else {
                list = em.createNativeQuery("select acctCode from accounttypemaster where acctCode = '" + acctType + "'").getResultList();
            }
            String exludeQuery = "";
            List lienExclude = common.getCode("LIEN_EXCLUDE_AC_TYPE");
            if (!lienExclude.isEmpty()) {
                exludeQuery = " and substring(a.acno,3,2) not in ('" + lienExclude.get(0).toString() + "')";
            }
            for (int k = 0; k < (acType1.contains("ALL") ? 1 : list.size()); k++) {
                Vector vtr = (Vector) list.get(k);
                String acCode = vtr.get(0).toString();
                if (acType1.contains("ALL")) {
                    acctType = "ALL";
                } else {
                    acctType = acCode;
                }
                if (brCode.equalsIgnoreCase("0A") || brCode.equalsIgnoreCase("90")) {
                    if (acctType.equalsIgnoreCase("ALL")) {
                        lienList1 = em.createNativeQuery("select aa.acno,aa.lienacno,aa.matdate,aa.SecurityRoi,aa.IntTableCode,aa.turn_over_detail_flag,aa.acctnature, "
                                + " ifnull(cast(bb.disbAmt as decimal(25,2)),0) as disbAmt, ifnull(bb.disbDt,(select date_format(openingdt,'%Y-%m-%d %H:%i:%s') from accountmaster where acno =aa.acno)) as disbDt, ifnull(bb.outst,0),ifnull(bb.custname,(select custname from accountmaster where acno=aa.acno)) from "
                                + " (select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi,ifnull(a.IntTableCode,'') as IntTableCode, "
                                + " ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag,(select acctnature from accounttypemaster "
                                + " where acctcode = substring(a.acno,3,2) ) as acctnature from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c  "
                                + " where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) "
                                + " and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and a.entrydate <='" + asOnDate + "' " + exludeQuery + " "
                                + " union all  "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi,ifnull(a.IntTableCode,'') as IntTableCode,ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag, "
                                + " (select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2) ) as acctnature "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where substring(a.lienacno,3,2) in "
                                + " (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) and a.status = 'EXPIRED' "
                                + " and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and a.expirydate >='" + asOnDate + "'  and a.entrydate <='" + asOnDate + "' " + exludeQuery + ""
                                + "  order by acno,lienacno ) aa  "
                                + "left join  "
                                + " (select aa.acno, aa.mindt as disbDt, (select sum(dramt) from ca_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from ca_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst  from "
                                + " (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from ca_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from ca_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, (select acno, min(dt) as mindt "
                                + " from ca_recon group by acno )bb  where  aa.acno = bb.acno "
                                + " union all "
                                + " select aa.acno, aa.mindt as disbDt, (select sum(dramt) from loan_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from loan_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst "
                                + " from (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from loan_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno )bb  where  aa.acno = bb.acno ) bb on aa.acno =bb.acno order by acno").getResultList();
                    } else {
                        lienList1 = em.createNativeQuery("select aa.acno,aa.lienacno,aa.matdate,aa.SecurityRoi,aa.IntTableCode,aa.turn_over_detail_flag,aa.acctnature, "
                                + " ifnull(cast(bb.disbAmt as decimal(25,2)),0) as disbAmt, ifnull(bb.disbDt,(select date_format(openingdt,'%Y-%m-%d %H:%i:%s') from accountmaster where acno =aa.acno)) as disbDt , ifnull(bb.outst,0), ifnull(bb.custname,(select custname from accountmaster where acno=aa.acno)) from "
                                + " (select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi,ifnull(a.IntTableCode,'') as IntTableCode, "
                                + " ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag,(select acctnature from accounttypemaster "
                                + " where acctcode = substring(a.acno,3,2) ) as acctnature from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c  "
                                + " where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctcode in ('" + acctType + "')) "
                                + " and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and a.entrydate <='" + asOnDate + "'  " + exludeQuery + ""
                                + " union all  "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi,ifnull(a.IntTableCode,'') as IntTableCode,ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag, "
                                + " (select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2) ) as acctnature "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where substring(a.lienacno,3,2) in "
                                + " (select acctCode from accounttypemaster where acctcode in ('" + acctType + "')) and a.status = 'EXPIRED' "
                                + " and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and a.expirydate >='" + asOnDate + "'  and a.entrydate <='" + asOnDate + "' " + exludeQuery + ""
                                + "  order by acno,lienacno ) aa  "
                                + "left join  "
                                + " (select aa.acno, aa.mindt as disbDt, (select sum(dramt) from ca_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from ca_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst  from "
                                + " (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from ca_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from ca_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, (select acno, min(dt) as mindt "
                                + " from ca_recon group by acno )bb  where  aa.acno = bb.acno "
                                + " union all "
                                + " select aa.acno, aa.mindt as disbDt, (select sum(dramt) from loan_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from loan_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst "
                                + " from (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from loan_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno )bb  where  aa.acno = bb.acno ) bb on aa.acno =bb.acno order by acno").getResultList();
                    }
                } else {
                    if (acctType.equalsIgnoreCase("ALL")) {
                        lienList1 = em.createNativeQuery("select aa.acno,aa.lienacno,aa.matdate,aa.SecurityRoi,aa.IntTableCode,aa.turn_over_detail_flag,aa.acctnature, "
                                + " ifnull(cast(bb.disbAmt as decimal(25,2)),0) as disbAmt, ifnull(bb.disbDt,(select date_format(openingdt,'%Y-%m-%d %H:%i:%s') from accountmaster where acno =aa.acno)) as disbDt , ifnull(bb.outst,0), ifnull(bb.custname,(select custname from accountmaster where acno=aa.acno)) from "
                                + " (select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi,ifnull(a.IntTableCode,'') as IntTableCode , "
                                + " ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag,(select acctnature from accounttypemaster "
                                + " where acctcode = substring(a.acno,3,2) ) as acctnature from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c  "
                                + " where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) "
                                + " and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE  and substring(a.lienacno,1,2)='" + brCode + "' and substring(a.acno,1,2)='" + brCode + "'  and a.entrydate <='" + asOnDate + "' " + exludeQuery + " "
                                + " union all  "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi, ifnull(a.IntTableCode,'') as IntTableCode ,ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag, "
                                + " (select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2) ) as acctnature "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where substring(a.lienacno,3,2) in "
                                + " (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) and a.status = 'EXPIRED' "
                                + " and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and substring(a.lienacno,1,2)='" + brCode + "' and substring(a.acno,1,2)='" + brCode + "'  and a.expirydate >='" + asOnDate + "'  and a.entrydate <='" + asOnDate + "' " + exludeQuery + ""
                                + "  order by acno,lienacno ) aa  "
                                + "left join  "
                                + " (select aa.acno, aa.mindt as disbDt, (select sum(dramt) from ca_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from ca_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst  from "
                                + " (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from ca_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from ca_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, (select acno, min(dt) as mindt "
                                + " from ca_recon group by acno )bb  where  aa.acno = bb.acno "
                                + " union all "
                                + " select aa.acno, aa.mindt as disbDt, (select sum(dramt) from loan_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from loan_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst "
                                + " from (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from loan_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno )bb  where  aa.acno = bb.acno ) bb on aa.acno =bb.acno "
                                + " union all "
                                + " select aa.acno,aa.lienacno,aa.matdate,aa.SecurityRoi,aa.IntTableCode,aa.turn_over_detail_flag,aa.acctnature, "
                                + " ifnull(cast(bb.disbAmt as decimal(25,2)),0) as disbAmt, ifnull(bb.disbDt,(select date_format(openingdt,'%Y-%m-%d %H:%i:%s') from accountmaster where acno =aa.acno)) as disbDt , ifnull(bb.outst,0), ifnull(bb.custname,(select custname from accountmaster where acno=aa.acno)) from "
                                + " (select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi,ifnull(a.IntTableCode,'') as IntTableCode , "
                                + " ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag,(select acctnature from accounttypemaster "
                                + " where acctcode = substring(a.acno,3,2) ) as acctnature from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c  "
                                + " where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) "
                                + " and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE  and substring(a.acno,1,2)='" + brCode + "' and a.entrydate <='" + asOnDate + "'  " + exludeQuery + " "
                                + " union all  "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi, ifnull(a.IntTableCode,'') as IntTableCode ,ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag, "
                                + " (select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2) ) as acctnature "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where substring(a.lienacno,3,2) in "
                                + " (select acctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')) and a.status = 'EXPIRED' "
                                + " and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and substring(a.acno,1,2)='" + brCode + "' and a.expirydate >='" + asOnDate + "'  and a.entrydate <='" + asOnDate + "'  " + exludeQuery + " "
                                + "  order by acno,lienacno ) aa  "
                                + "left join  "
                                + " (select aa.acno, aa.mindt as disbDt, (select sum(dramt) from ca_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from ca_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst  from "
                                + " (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from ca_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from ca_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, (select acno, min(dt) as mindt "
                                + " from ca_recon group by acno )bb  where  aa.acno = bb.acno "
                                + " union all "
                                + " select aa.acno, aa.mindt as disbDt, (select sum(dramt) from loan_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from loan_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst "
                                + " from (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from loan_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno )bb  where  aa.acno = bb.acno ) bb on aa.acno =bb.acno where  substring(aa.acno,1,2) <> substring(aa.lienacno,1,2) order by acno").getResultList();
                    } else {
                        lienList1 = em.createNativeQuery("select aa.acno,aa.lienacno,aa.matdate,aa.SecurityRoi,aa.IntTableCode,aa.turn_over_detail_flag,aa.acctnature, "
                                + " ifnull(cast(bb.disbAmt as decimal(25,2)),0) as disbAmt, ifnull(bb.disbDt,(select date_format(openingdt,'%Y-%m-%d %H:%i:%s') from accountmaster where acno =aa.acno)) as disbDt , ifnull(bb.outst,0), ifnull(bb.custname,(select custname from accountmaster where acno=aa.acno)) from "
                                + " (select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi, ifnull(a.IntTableCode,'') as IntTableCode , "
                                + " ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag,(select acctnature from accounttypemaster "
                                + " where acctcode = substring(a.acno,3,2) ) as acctnature from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c  "
                                + " where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctcode in ('" + acctType + "')) "
                                + " and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE  and substring(a.lienacno,1,2)='" + brCode + "'  and substring(a.acno,1,2)='" + brCode + "'   and a.entrydate <='" + asOnDate + "' " + exludeQuery + " "
                                + " union all  "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi, ifnull(a.IntTableCode,'') as IntTableCode ,ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag, "
                                + " (select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2) ) as acctnature "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where substring(a.lienacno,3,2) in "
                                + " (select acctCode from accounttypemaster where acctcode in ('" + acctType + "')) and a.status = 'EXPIRED' "
                                + " and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and substring(a.lienacno,1,2)='" + brCode + "'  and substring(a.acno,1,2)='" + brCode + "'  and a.expirydate >='" + asOnDate + "'  and a.entrydate <='" + asOnDate + "' " + exludeQuery + ""
                                + "  order by acno,lienacno ) aa  "
                                + "left join  "
                                + " (select aa.acno, aa.mindt as disbDt, (select sum(dramt) from ca_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from ca_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst  from "
                                + " (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from ca_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from ca_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, (select acno, min(dt) as mindt "
                                + " from ca_recon group by acno )bb  where  aa.acno = bb.acno "
                                + " union all "
                                + " select aa.acno, aa.mindt as disbDt, (select sum(dramt) from loan_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from loan_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst "
                                + " from (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from loan_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno )bb  where  aa.acno = bb.acno ) bb on aa.acno =bb.acno "
                                + " union all "
                                + " select aa.acno,aa.lienacno,aa.matdate,aa.SecurityRoi,aa.IntTableCode,aa.turn_over_detail_flag,aa.acctnature, "
                                + " ifnull(cast(bb.disbAmt as decimal(25,2)),0) as disbAmt, ifnull(bb.disbDt,(select date_format(openingdt,'%Y-%m-%d %H:%i:%s') from accountmaster where acno =aa.acno)) as disbDt , ifnull(bb.outst,0), ifnull(bb.custname,(select custname from accountmaster where acno=aa.acno)) from "
                                + " (select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi, ifnull(a.IntTableCode,'') as IntTableCode , "
                                + " ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag,(select acctnature from accounttypemaster "
                                + " where acctcode = substring(a.acno,3,2) ) as acctnature from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c  "
                                + " where substring(a.lienacno,3,2) in (select acctCode from accounttypemaster where acctcode in ('" + acctType + "')) "
                                + " and a.status = 'Active' and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE  and substring(a.acno,1,2)='" + brCode + "'  and a.entrydate <='" + asOnDate + "'  " + exludeQuery + " "
                                + " union all  "
                                + " select distinct a.acno,a.lienacno,a.matdate,ifnull(SecurityRoi,0) as SecurityRoi, ifnull(a.IntTableCode,'') as IntTableCode ,ifnull(turn_over_detail_flag,'N') as turn_over_detail_flag, "
                                + " (select acctnature from accounttypemaster where acctcode = substring(a.acno,3,2) ) as acctnature "
                                + " from loansecurity a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where substring(a.lienacno,3,2) in "
                                + " (select acctCode from accounttypemaster where acctcode in ('" + acctType + "')) and a.status = 'EXPIRED' "
                                + " and a.acno = b.acno and b.SCHEME_CODE = c.SCHEME_CODE and substring(a.acno,1,2)='" + brCode + "'  and a.expirydate >='" + asOnDate + "'  and a.entrydate <='" + asOnDate + "'  " + exludeQuery + " "
                                + "  order by acno,lienacno ) aa  "
                                + "left join  "
                                + " (select aa.acno, aa.mindt as disbDt, (select sum(dramt) from ca_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from ca_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst  from "
                                + " (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from ca_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from ca_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, (select acno, min(dt) as mindt "
                                + " from ca_recon group by acno )bb  where  aa.acno = bb.acno "
                                + " union all "
                                + " select aa.acno, aa.mindt as disbDt, (select sum(dramt) from loan_recon where acno = aa.acno and dt = aa.mindt ) as disbAmt, "
                                + " (select custname from accountmaster where acno = aa.acno) as custname , "
                                + " (select cast(sum(cramt-dramt) as decimal(25,2))from loan_recon where acno = aa.acno and dt <='" + asOnDate + "' ) as outst "
                                + " from (select ca.acno, ifnull(SUM(ca.dramt),0) as drAmt, bb.mindt from loan_recon ca,loansecurity lo, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno ) bb where ca.acno = bb.acno and  ca.dt = bb.mindt "
                                + " and lo.Acno = ca.acno   AND LIENACNO IS NOT NULL group by ca.acno, ca.dt )aa, "
                                + " (select acno, min(dt) as mindt from loan_recon group by acno )bb  where  aa.acno = bb.acno ) bb on aa.acno =bb.acno where  substring(aa.acno,1,2) <> substring(aa.lienacno,1,2) order by acno").getResultList();
                    }
                }
                String acNo = null;
                int firstPrint = 0;
                Double amt = 0d;
                if (!lienList1.isEmpty()) {
                    for (int i = 0; i < lienList1.size(); i++) {
                        Double MatVal = 0d, securityRoi = 0d;
                        LienReportPojo pojo = new LienReportPojo();
                        Vector lienV = (Vector) lienList1.get(i);
                        acNo = lienV.get(0).toString();
                        String lienAcNo = lienV.get(1).toString();
                        securityRoi = Double.parseDouble(lienV.get(3).toString());
                        String turnOverFlag = lienV.get(5).toString();
                        String acNat = lienV.get(6).toString();
                        disbAmt = Double.parseDouble(lienV.get(7).toString());
                        disbDt = lienV.get(8).toString();
                        custName = lienV.get(10).toString();
                        String matDt = lienV.get(2).toString();
                        String acNature = acNat;
                        List amountList = null;
                        List lienSecAcNoList = null;
                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            amountList = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(DRAMT,0)),0)-ifnull(SUM(ifnull(CRAMT,0)),0)) FROM ca_recon WHERE ACNO = '" + acNo + "' AND DT <='" + asOnDate + "'").getResultList();
                            lienSecAcNoList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(MATVALUE,0)),0) FROM loansecurity WHERE ACNO = '" + acNo + "' AND /*STATUS = 'ACTIVE' AND MATDATE >='" + asOnDate + "' */ ENTRYDATE<='" + asOnDate + "' AND (EXPIRYDATE>='" + asOnDate + "' or EXPIRYDATE IS NULL) AND (LIENACNO IS NOT NULL  and lienacno <> '') ").getResultList();
                        } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            amountList = em.createNativeQuery("SELECT (ifnull(SUM(ifnull(DRAMT,0)),0)-ifnull(SUM(ifnull(CRAMT,0)),0)) FROM loan_recon WHERE ACNO = '" + acNo + "' AND DT <='" + asOnDate + "'").getResultList();
                            lienSecAcNoList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(MATVALUE,0)),0) FROM loansecurity WHERE ACNO = '" + acNo + "' AND /*STATUS = 'ACTIVE' AND*/ ENTRYDATE<='" + asOnDate + "'  AND (EXPIRYDATE>='" + asOnDate + "'  or EXPIRYDATE IS NULL) AND (LIENACNO IS NOT NULL  and lienacno <> '') ").getResultList();
                        }
                        Vector element = (Vector) amountList.get(0);
                        balance = Double.parseDouble(element.get(0).toString());
                        Double amt1 = balance;
                        Vector lienSecAcNoVect = (Vector) lienSecAcNoList.get(0);
                        Double lienSecAcNoAmt = Double.parseDouble(lienSecAcNoVect.get(0).toString());
                        matValue = Double.parseDouble(lienSecAcNoVect.get(0).toString());
                        if (lienSecAcNoAmt <= amt1) {
                            amt1 = lienSecAcNoAmt; //amt1 - lienSecAcNoAmt;
                        } else if (lienSecAcNoAmt > amt1) {
                            amt1 = amt1;
                        }
                        double roi = 0;
                        if (turnOverFlag.equalsIgnoreCase("Y")) {
                            String intTableCode = lienV.get(4).toString();
//                            System.out.println("Acno " + acNo);
                            roi = securityRoi + Double.parseDouble(loanInterestCalculationBean.getROI(intTableCode, matValue, asOnDate));
                        }
                        pojo = new LienReportPojo();
                        if (firstPrint == 0) {
                            pojo.setAcNo(acNo);
                            pojo.setCustName(custName);
                            pojo.setDisbAmt(disbAmt);
                            pojo.setLienAcNo(lienAcNo);
                            pojo.setOutStandBal(balance);
                            pojo.setActualValue(amt1);
                            pojo.setDisbDt(dmyFormat.format(ymdFormat.parse(disbDt)));
                            pojo.setLienValue(matValue);
                            pojo.setMatDt(dmyFormat.format(ymdFormat.parse(matDt)));
                            pojo.setRoi(roi);
                            oldAcNo = acNo;
                            oldLienAcNo = lienAcNo;
                            firstPrint = 1;
                            reportList.add(pojo);
                        } else {
                            if (oldAcNo.equalsIgnoreCase(acNo)) {
                                if (oldLienAcNo.equalsIgnoreCase(lienAcNo)) {
                                    pojo.setAcNo(acNo);
                                    pojo.setCustName("");
                                    pojo.setDisbAmt(0.00);
                                    pojo.setLienAcNo(lienAcNo);
                                    pojo.setOutStandBal(0.00);
                                    pojo.setDisbDt("");
                                    pojo.setLienValue(0.00);
                                    pojo.setActualValue(0.00);
                                    pojo.setRoi(0.00);
                                    oldAcNo = acNo;
                                    oldLienAcNo = lienAcNo;
                                    reportList.add(pojo);
                                } else {
                                    pojo.setAcNo(acNo);
                                    pojo.setCustName("");
                                    pojo.setDisbAmt(0.00);
                                    pojo.setLienAcNo(lienAcNo);
                                    pojo.setActualValue(0.00);
                                    pojo.setOutStandBal(0.00);
                                    pojo.setDisbDt("");
                                    pojo.setLienValue(matValue);
                                    pojo.setRoi(0.00);
                                    oldAcNo = acNo;
                                    oldLienAcNo = lienAcNo;
                                    reportList.add(pojo);
                                }
                            } else {
                                pojo.setAcNo(acNo);
                                pojo.setCustName(custName);
                                pojo.setDisbAmt(disbAmt);
                                pojo.setLienAcNo(lienAcNo);
                                pojo.setOutStandBal(balance);
                                pojo.setDisbDt(dmyFormat.format(ymdFormat.parse(disbDt)));
                                pojo.setLienValue(matValue);
                                pojo.setActualValue(amt1);
                                pojo.setMatDt(dmyFormat.format(ymdFormat.parse(matDt)));
                                pojo.setRoi(roi);
                                oldAcNo = acNo;
                                oldLienAcNo = lienAcNo;
                                reportList.add(pojo);
                            }
                        }
//                        System.out.println("acNo:"+acNo+";custName:"+custName+";disbAmt:"+disbAmt+";outStandBal:"+balance+";disbDt:"+disbDt+";lienAcNo:"+lienAcNo+";lienValue:"+matValue);
//                            }
//                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return reportList;
    }

    public List getAcctTypecadlList() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctCode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getFDMSRDnatureList() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct acctnature from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<LoanAcDetailStatementPojo> getLoanAcDetailStmt(String brCode, String acctType, String acNo, String frDt, String todt) throws ApplicationException {
        List<LoanAcDetailStatementPojo> dataList = new ArrayList<LoanAcDetailStatementPojo>();
        BigDecimal prinDr = new BigDecimal("0");
        BigDecimal prinCr = new BigDecimal("0");
        BigDecimal chargCr = new BigDecimal("0");
        BigDecimal chargDr = new BigDecimal("0");
        BigDecimal intCr = new BigDecimal("0");
        BigDecimal intDr = new BigDecimal("0");
        double bal, roi;
        String table = "", date = null;
        String acNat = "";
        List result2 = new ArrayList();
        List result4 = new ArrayList();
        try {
            if (!acctType.equalsIgnoreCase("0")) {
                acNat = fts.getAcNatureByCode(acctType);
            }
            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                table = "ca_recon";
            } else {
                table = "loan_recon";
            }
            if (acctType.equalsIgnoreCase(acctType) && !acctType.equalsIgnoreCase("0")) {
                /**
                 * ****************************
                 * Account Type Wise For All Account No
                 * ***************************
                 */
                List result1 = em.createNativeQuery("select distinct acno from accountmaster where acno in (select distinct acno from " + table + " where  "
                        + "substring(acno,3,2) = '" + acctType + "'  and auth = 'Y'and dt < '" + todt + "' group by acno union select distinct acno from npa_recon where  "
                        + "substring(acno,3,2) = '" + acctType + "'  and auth = 'Y'and dt < '" + todt + "' group by acno ) and  subString(acno,3,2)  = '" + acctType + "' "
                        + "and accStatus <> 9 and CurBrCode = '" + brCode + "' and (ClosingDate is null or ClosingDate > '" + todt + "')").getResultList();
                if (!result1.isEmpty()) {
                    for (int i = 0; i < result1.size(); i++) {
                        Vector ele1 = (Vector) result1.get(i);
                        LoanAcDetailStatementPojo pojo = new LoanAcDetailStatementPojo();
                        String acno = ele1.get(0).toString();
                        //Charges                       
                        result2 = em.createNativeQuery("select ifnull(sum(cramt),0), ifnull(sum(dramt),0) from " + table + " where trandesc not in('2','6','3','4') "
                                + "and acno='" + acno + "' and dt < '" + todt + "'").getResultList();

                        Vector ele2 = (Vector) result2.get(0);
                        chargCr = new BigDecimal(ele2.get(0).toString());
                        chargDr = new BigDecimal(ele2.get(1).toString());
                        //Principal
                        result2 = em.createNativeQuery("select ifnull(sum(cramt),0), ifnull(sum(dramt),0) from " + table + " where trandesc in('2','6') "
                                + "and acno='" + acno + "' and dt < '" + todt + "'").getResultList();
                        Vector ele3 = (Vector) result2.get(0);
                        prinCr = new BigDecimal(ele3.get(0).toString());
                        prinDr = new BigDecimal(ele3.get(1).toString());
                        // Interest
                        result2 = em.createNativeQuery("select ifnull(sum(cramt),0), ifnull(sum(dramt),0) from " + table + " where trandesc in('3','4') "
                                + "and acno='" + acno + "' and dt < '" + todt + "'").getResultList();
                        Vector ele4 = (Vector) result2.get(0);
                        intCr = new BigDecimal(ele4.get(0).toString());
                        intDr = new BigDecimal(ele4.get(1).toString());
                        //Balance
                        bal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, todt));
                        //Customer Name
                        result2 = em.createNativeQuery("select custName from accountmaster where acno = '" + acno + "'").getResultList();
                        Vector ele6 = (Vector) result2.get(0);
                        String custName = ele6.get(0).toString();

                        roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Math.abs(bal), todt, acno));

                        pojo.setAcNo(acno);
                        pojo.setCustname(custName);
                        pojo.setChrgCr(chargCr);
                        pojo.setChrgDr(chargDr);
                        pojo.setIntCr(intCr);
                        pojo.setIntDr(intDr);
                        pojo.setPrinCr(prinCr);
                        pojo.setPrinDr(prinDr);
                        pojo.setRoi(roi);
                        pojo.setBalance(Math.abs(bal));

                        dataList.add(pojo);
                    }
                }
            } else {
                /**
                 * ****************************
                 * Account No Wise Individual Account No.
                 * ***************************
                 */
                String acctCode = fts.getAccountCode(acNo);
                String acNat1 = fts.getAcNatureByCode(acctCode);

                if (!acNat1.equalsIgnoreCase(CbsConstant.TERM_LOAN) && !acNat1.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) && !acNat1.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    throw new ApplicationException("This Account No. is not TL,DL and CA nature!!!");
                }

                if (acNat1.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    table = "ca_recon";
                } else {
                    table = "loan_recon";
                }

                List resuult3 = em.createNativeQuery("select distinct date_format(dt,'%Y%m%d') from " + table + " where acno= '" + acNo + "' and dt between '" + frDt + "' and '" + todt + "'").getResultList();
                if (resuult3.isEmpty()) {
                    throw new ApplicationException("Data is not exist!!!");
                }
                for (int i = 0; i < resuult3.size(); i++) {
                    Vector ele1 = (Vector) resuult3.get(i);
                    date = ele1.get(0).toString();
                    //Balance
                    bal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, todt));
                    result4 = em.createNativeQuery("select cramt,dramt,trandesc,ty,details from " + table + " where acno= '" + acNo + "'and dt = '" + date + "'").getResultList();

                    if (!result4.isEmpty()) {
                        for (int j = 0; j < result4.size(); j++) {
                            Vector ele2 = (Vector) result4.get(j);
                            LoanAcDetailStatementPojo pojo = new LoanAcDetailStatementPojo();
                            BigDecimal cramt = new BigDecimal(ele2.get(0).toString());
                            BigDecimal dramt = new BigDecimal(ele2.get(1).toString());
                            String trandesc = ele2.get(2).toString();
                            String ty = ele2.get(3).toString();
                            String detail = ele2.get(4).toString();

                            if (trandesc.equalsIgnoreCase("2") || trandesc.equalsIgnoreCase("6")) {
                                if (ty.equalsIgnoreCase("0")) {
                                    pojo.setPrinCr(cramt);
                                }
                                if (ty.equalsIgnoreCase("1")) {
                                    pojo.setPrinDr(dramt);
                                }
                            } else {
                                pojo.setPrinCr(new BigDecimal(0.00));
                                pojo.setPrinDr(new BigDecimal(0.00));
                            }

                            if (!trandesc.equalsIgnoreCase("2") && !trandesc.equalsIgnoreCase("6") && !trandesc.equalsIgnoreCase("3") && !trandesc.equalsIgnoreCase("4")) {
                                if (ty.equalsIgnoreCase("0")) {
                                    pojo.setChrgCr(cramt);
                                } else {
                                    pojo.setChrgCr(new BigDecimal(0.00));
                                }
                                if (ty.equalsIgnoreCase("1")) {
                                    pojo.setChrgDr(dramt);
                                } else {
                                    pojo.setChrgDr(new BigDecimal(0.00));
                                }
                            } else {
                                pojo.setChrgCr(new BigDecimal(0.00));
                                pojo.setChrgDr(new BigDecimal(0.00));
                            }

                            if (trandesc.equalsIgnoreCase("3") || trandesc.equalsIgnoreCase("4")) {
                                if (ty.equalsIgnoreCase("0")) {
                                    pojo.setIntCr(cramt);
                                }
                                if (ty.equalsIgnoreCase("1")) {
                                    pojo.setIntDr(dramt);
                                }
                            } else {
                                pojo.setIntCr(new BigDecimal(0.00));
                                pojo.setIntDr(new BigDecimal(0.00));
                            }

                            pojo.setDate(date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4));
                            if (detail == null) {
                                pojo.setDetails("");
                            }
                            pojo.setDetails(detail);
                            pojo.setBalance(Math.abs(bal));

                            dataList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<VillageWiseEMIDetailPojo> getVillWeseEmiDetail(String repType, String brCode, String acctType, String acNo, String frDt, String toDt, String accountManager, String groupId) throws ApplicationException {
        List<VillageWiseEMIDetailPojo> dataList = new ArrayList<VillageWiseEMIDetailPojo>();
        String custId = "", depId = "", custName = "";
        double totalIntAmt = 0d, prinAmt = 0d, interestAmt = 0d, overDueIntAmt = 0d, crAmt = 0d, rdInstallPrin = 0d;
        List result1 = new ArrayList();
        List result3 = new ArrayList();
        List result5 = new ArrayList();
        try {
            String bnkCode = fts.getBankCode();
            int isExceessEmi = common.isExceessEmi(toDt);

            List acctList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "')").getResultList();
            Map<String, String> actMap = new HashMap<>();
            for (int i = 0; i < acctList.size(); i++) {
                Vector vtr = (Vector) acctList.get(i);
                actMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }
            if (brCode.equalsIgnoreCase("0A")) {
                if (groupId.equalsIgnoreCase("ALL")) {
                    if (acctType.equalsIgnoreCase("All")) {
                        result1 = em.createNativeQuery("select Acno,custname from accountmaster  where acno in(select acno from emidetails where "
                                + "acno in(select Acno from customerid where custid in(select CustomerId from cbs_cust_misinfo  where type = '"
                                + accountManager + "')) and duedt <= '" + toDt + "' /*and STATUS = 'unpaid'*/) and (closingdate = '' or "
                                + "closingdate is null or closingdate >'" + toDt + "') and penalty1=1 order by Acno").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select Acno,custname from accountmaster  where acno in(select acno from emidetails where "
                                + "acno in(select Acno from customerid where custid in(select CustomerId from cbs_cust_misinfo  where type = '"
                                + accountManager + "'))  and duedt <= '" + toDt + "' /*and STATUS = 'unpaid'*/) and accttype = '" + acctType
                                + "' and (closingdate = '' or closingdate is null or closingdate >'" + toDt + "') and penalty1=1 order by Acno").getResultList();
                    }
                } else {
                    if (acctType.equalsIgnoreCase("All")) {
                        result1 = em.createNativeQuery("select Acno,custname from accountmaster  where acno in(select acno from emidetails where "
                                + "acno in(select Acno from customerid where custid in(select CustomerId from cbs_cust_misinfo  where type = '"
                                + accountManager + "' and GroupID = '" + groupId + "')) and duedt <= '" + toDt + "' /*and STATUS = 'unpaid'*/) and "
                                + "(closingdate = '' or closingdate is null or closingdate >'" + toDt + "') and penalty1=1 order by Acno").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select Acno,custname from accountmaster  where acno in(select acno from emidetails where "
                                + "acno in(select Acno from customerid where custid in(select CustomerId from cbs_cust_misinfo  where type = '"
                                + accountManager + "' and GroupID = '" + groupId + "')) and duedt <= '" + toDt + "' /*and STATUS = 'unpaid'*/) "
                                + "and accttype = '" + acctType + "' and (closingdate = '' or closingdate is null or closingdate >'" + toDt + "') "
                                + "and penalty1=1 order by Acno").getResultList();
                    }
                }

                if (!result1.isEmpty()) {
                    for (int j = 0; j < result1.size(); j++) {
                        Vector ele1 = (Vector) result1.get(j);
                        VillageWiseEMIDetailPojo pojo = new VillageWiseEMIDetailPojo();
                        String acno = ele1.get(0).toString();
                        custName = ele1.get(1).toString();
                        String acctCode = fts.getAccountCode(acno);

                        // result3 = em.createNativeQuery("select CustId from customerid where Acno = '" + acno + "'").getResultList();
                        result3 = em.createNativeQuery("select CustomerId,GroupID from cbs_cust_misinfo where CustomerId in(select CustId from customerid where Acno = '" + acno + "')").getResultList();
                        if (!result3.isEmpty()) {
                            Vector ele3 = (Vector) result3.get(0);
                            custId = ele3.get(0).toString();
                            depId = ele3.get(1).toString();
                        }

                        //New Code
                        result5 = em.createNativeQuery("select ifnull(RdInstal,0) from accountmaster where acno = '" + acno + "'").getResultList();
                        Vector vtr5 = (Vector) result5.get(0);
                        rdInstallPrin = Double.parseDouble(vtr5.get(0).toString());
                        double lipAmt = 0d;
                        if (rdInstallPrin == 0) {
                            String fDt = "", tDt = "";
                            int noMonth = 0;

                            result5 = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) as overDueAmt,ifnull(sum(INTERESTAMT),0) as IntAmt,date_format(max(duedt),'%d/%m/%Y') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' and duedt<='" + toDt + "'").getResultList();
                            Vector ele5 = (Vector) result5.get(0);
                            prinAmt = Double.parseDouble(ele5.get(0).toString());
                            interestAmt = Double.parseDouble(ele5.get(1).toString());
                            if (prinAmt == 0) {
                                prinAmt = Math.abs(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, toDt)));
                            }
                            result5 = em.createNativeQuery("select ifnull(premiumpaid,0),date_format(FromDt,'%Y%m%d'),date_format(ToDt,'%Y%m%d') from loan_insurance where acno='" + acno + "'").getResultList();
                            if (!result5.isEmpty()) {
                                Vector element5 = (Vector) result5.get(0);
                                lipAmt = Double.parseDouble(element5.get(0).toString());
                                fDt = element5.get(1).toString();
                                tDt = element5.get(2).toString();
                                noMonth = CbsUtil.monthDiff(ymdFormat.parse(fDt), ymdFormat.parse(tDt));
                                lipAmt = lipAmt / noMonth;
                            }
                        } else {
                            result5 = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where acno = '" + acno + "' and trandesc in('3','4') and dt <= '" + toDt + "'").getResultList();
                            Vector overDueVector = (Vector) result5.get(0);
                            overDueIntAmt = Double.parseDouble(overDueVector.get(0).toString());

                            result5 = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno = '" + acno + "' and dt <= '" + toDt + "'").getResultList();
                            Vector crAmtVector = (Vector) result5.get(0);
                            crAmt = Double.parseDouble(crAmtVector.get(0).toString());

                            String fromDt = loanInterestCalculationBean.allFromDt(acno.substring(2, 4), brCode, "f");
                            String tDt = loanInterestCalculationBean.allFromDt(acno.substring(2, 4), brCode, "t");

                            LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(fromDt, tDt, acno, brCode);
                            interestAmt = it.getTotalInt() * -1;

                            if (overDueIntAmt > crAmt) {
                                overDueIntAmt = overDueIntAmt - crAmt;
                            } else {
                                overDueIntAmt = 0;
                            }

                            totalIntAmt = interestAmt + overDueIntAmt;
                            if (totalIntAmt >= rdInstallPrin) {
                                interestAmt = rdInstallPrin;
                                prinAmt = 0;
                            } else {
                                interestAmt = totalIntAmt;
                                prinAmt = rdInstallPrin - totalIntAmt;
                            }
                        }

                        pojo.setAcNo(acno);
                        pojo.setAcType(acctCode);
                        pojo.setCustId(custId);
                        pojo.setInstallment(prinAmt + interestAmt + lipAmt);
                        pojo.setName(custName);
                        pojo.setOutStandBal(Math.abs(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, toDt))));
                        pojo.setPrinAmt(prinAmt);
                        pojo.setIntAmt(interestAmt);

                        pojo.setAcTypeDesc(common.getAcctDecription(acctCode));
                        pojo.setAccMang(accountManager);
                        pojo.setAccMangDesc(common.getOfficeDecByType(accountManager));
                        pojo.setGroupId(depId);
                        pojo.setGroupIdDesc(common.getDeptDescByGroupId(depId));
                        pojo.setLipAmt(lipAmt);

                        dataList.add(pojo);
                    }
                }
            } else {
                if (groupId.equalsIgnoreCase("ALL")) {
                    if (acctType.equalsIgnoreCase("All")) {
                        result1 = em.createNativeQuery("select a.Acno,a.custname,ifnull(RdInstal,0),l.recover,a.accstatus from accountmaster a,loan_appparameter l,(select Acno as acn from customerid where custid in"
                                + "(select CustomerId from cbs_cust_misinfo  where type = '" + accountManager + "') and substring(acno,1,2)= '" + brCode
                                + "'and substring(acno,3,2) in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "'))) b where a.acno = b.acn and a.acno = l.acno "
                                + "and (a.closingdate = '' or a.closingdate is null or a.closingdate >'" + toDt + "') "
                                + "and penalty1=1 order by a.Acno").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select a.Acno,a.custname,ifnull(RdInstal,0),l.recover,a.accstatus from accountmaster a,loan_appparameter l,(select Acno as acn from customerid where custid in"
                                + "(select CustomerId from cbs_cust_misinfo  where type = '" + accountManager + "') and substring(acno,1,2)= '" + brCode
                                + "'and substring(acno,3,2) = '" + acctType + "') b where a.acno = b.acn and a.acno = l.acno and (a.closingdate = '' or a.closingdate is null or "
                                + "a.closingdate >'" + toDt + "') and penalty1=1 order by a.Acno").getResultList();
                    }
                } else {
                    if (acctType.equalsIgnoreCase("All")) {
                        result1 = em.createNativeQuery("select a.Acno,a.custname,ifnull(RdInstal,0),l.recover,a.accstatus from accountmaster a,loan_appparameter l,(select Acno as acn from customerid where custid in"
                                + "(select CustomerId from cbs_cust_misinfo  where type = '" + accountManager + "' and GroupID = '" + groupId + "') and substring(acno,1,2)= '" + brCode
                                + "'and substring(acno,3,2) in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "'))) b where a.acno = b.acn and a.acno = l.acno "
                                + "and (a.closingdate = '' or a.closingdate is null or a.closingdate >'" + toDt + "') "
                                + "and penalty1=1 order by a.Acno").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select a.Acno,a.custname,ifnull(RdInstal,0),l.recover,a.accstatus from accountmaster a,loan_appparameter l,(select Acno as acn from customerid where custid in"
                                + "(select CustomerId from cbs_cust_misinfo  where type = '" + accountManager + "' and GroupID = '" + groupId + "') and substring(acno,1,2)= '" + brCode
                                + "'and substring(acno,3,2) = '" + acctType + "') b where a.acno = b.acn and a.acno = l.acno and (a.closingdate = '' or a.closingdate is null or "
                                + "a.closingdate >'" + toDt + "') and penalty1=1 order by a.Acno").getResultList();
                    }
                }
                if (!result1.isEmpty()) {
                    for (int j = 0; j < result1.size(); j++) {
                        Vector ele1 = (Vector) result1.get(j);
                        VillageWiseEMIDetailPojo pojo = new VillageWiseEMIDetailPojo();
                        String acno = ele1.get(0).toString();
                        custName = ele1.get(1).toString();
                        rdInstallPrin = Double.parseDouble(ele1.get(2).toString());
                        String intFlag = ele1.get(3).toString();
                        String accStatus = ele1.get(4).toString();
                        String acctCode = acno.substring(2, 4);
                        double lipAmt = 0d, outBal = 0d, memorandumInterest = 0d, prinOverDue = 0d, emiOverDue = 0d;
                        String dueDt = "";
                        outBal = Math.abs(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, toDt)));
                        if (!bnkCode.equalsIgnoreCase("NABU")) {
                            if (rdInstallPrin == 0) {
                                String fDt = "", tDt = "";
                                int noMonth = 0;
                                result5 = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) as overDueAmt,/*ifnull(sum(INTERESTAMT),0) as IntAmt,*/ifnull(date_format(max(duedt),'%d/%m/%Y'),'') as maxDueDt "
                                        + "from emidetails where acno='" + acno + "' and status='Unpaid' and duedt<='" + toDt + "'").getResultList();
                                Vector ele5 = (Vector) result5.get(0);
                                prinAmt = Double.parseDouble(ele5.get(0).toString());
                                dueDt = ele5.get(1).toString();
                                // interestAmt = Double.parseDouble(ele5.get(1).toString());

                                fDt = loanInterestCalculationBean.allFromDt(acno.substring(2, 4), brCode, "f");
                                tDt = loanInterestCalculationBean.allFromDt(acno.substring(2, 4), brCode, "t");

                                LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(fDt, tDt, acno, brCode);
                                interestAmt = it.getTotalInt() * -1;
                                if (prinAmt == 0) {
                                    if (outBal > 0) {
                                        result5 = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) as overDueAmt,/*ifnull(sum(INTERESTAMT),0) as IntAmt,*/"
                                                + "ifnull(date_format(max(duedt),'%d/%m/%Y'),'') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' "
                                                + "and duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails where acno='" + acno + "' and status='Unpaid') ").getResultList();
                                    }
                                    Vector element5 = (Vector) result5.get(0);
                                    prinAmt = Double.parseDouble(element5.get(0).toString());
                                    dueDt = element5.get(1).toString();
                                    if (prinAmt == 0) {
                                        prinAmt = outBal;
                                    }
                                } else {
                                    if (prinAmt > outBal) {
                                        prinAmt = outBal;
                                        prinAmt = prinAmt + interestAmt;
                                    }
                                }

                                if (prinAmt < interestAmt) {
                                    if (intFlag.equalsIgnoreCase("CIP")) {
                                        prinAmt = 0;
                                        interestAmt = prinAmt;
                                    } else {
                                        prinAmt = prinAmt;
                                        interestAmt = 0;
                                    }
                                } else {
                                    if (intFlag.equalsIgnoreCase("CIP")) {
                                        prinAmt = prinAmt - interestAmt;
                                        interestAmt = interestAmt;
                                    } else {
                                        prinAmt = prinAmt;
                                        interestAmt = 0;
                                    }
                                }
                                if (dueDt.equalsIgnoreCase("")) {
                                    dueDt = tDt.substring(6, 8) + "/" + tDt.substring(4, 6) + "/" + tDt.substring(0, 4);
                                }
                                result5 = em.createNativeQuery("select ifnull(premiumpaid,0),date_format(FromDt,'%Y%m%d'),date_format(ToDt,'%Y%m%d') from loan_insurance where acno='" + acno + "'").getResultList();
                                if (!result5.isEmpty()) {
                                    Vector element5 = (Vector) result5.get(0);
                                    lipAmt = Double.parseDouble(element5.get(0).toString());
                                    fDt = element5.get(1).toString();
                                    tDt = element5.get(2).toString();
                                    noMonth = CbsUtil.monthDiff(ymdFormat.parse(fDt), ymdFormat.parse(tDt));
                                    lipAmt = lipAmt / noMonth;
                                }
                            } else {
                                //New Code
                                result5 = em.createNativeQuery("select ifnull(date_format(min(duedt),'%d/%m/%Y'),'') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid'").getResultList();
                                Vector dueDtVector = (Vector) result5.get(0);
                                dueDt = dueDtVector.get(0).toString();

                                result5 = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where acno = '" + acno + "' and trandesc in('3','4') and dt <= '" + toDt + "'").getResultList();
                                Vector overDueVector = (Vector) result5.get(0);
                                overDueIntAmt = Double.parseDouble(overDueVector.get(0).toString());

                                result5 = em.createNativeQuery("select sum(cramt) from loan_recon where acno = '" + acno + "' and dt <= '" + toDt + "'").getResultList();
                                Vector crAmtVector = (Vector) result5.get(0);
                                crAmt = Double.parseDouble(crAmtVector.get(0).toString());

                                String fromDt = loanInterestCalculationBean.allFromDt(acno.substring(2, 4), brCode, "f");
                                String tDt = loanInterestCalculationBean.allFromDt(acno.substring(2, 4), brCode, "t");
                                if (dueDt.equalsIgnoreCase("")) {
                                    dueDt = tDt.substring(6, 8) + "/" + tDt.substring(4, 6) + "/" + tDt.substring(0, 4);
                                }
                                LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(fromDt, tDt, acno, brCode);
                                interestAmt = it.getTotalInt() * -1;

                                if (overDueIntAmt > crAmt) {
                                    overDueIntAmt = overDueIntAmt - crAmt;
                                } else {
                                    overDueIntAmt = 0;
                                }

                                totalIntAmt = interestAmt + overDueIntAmt;
                                if (totalIntAmt >= rdInstallPrin) {
                                    interestAmt = rdInstallPrin;
                                    prinAmt = 0;
                                } else {
                                    interestAmt = totalIntAmt;
                                    prinAmt = rdInstallPrin - totalIntAmt;
                                }
                            }
                        } else {
                            String fDt = "", tDt = "";
                            prinAmt = rdInstallPrin;
                            fDt = loanInterestCalculationBean.allFromDt(acno.substring(2, 4), brCode, "f");
                            tDt = loanInterestCalculationBean.allFromDt(acno.substring(2, 4), brCode, "t");
                            LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(fDt, tDt, acno, brCode);
                            interestAmt = it.getTotalInt() * -1;
                            String prvDtEnd = CbsUtil.monthLast(toDt, -1);
                            String prvFirstDt = prvDtEnd.substring(0, 4) + prvDtEnd.substring(4, 6) + "01";
                            if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {
                                // List list = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from npa_recon where acno = '" + acno + "' and dt <='" + toDt + "'").getResultList();
                                List list = em.createNativeQuery("select NpaInt,PrimOverDue from (\n"
                                        + "(select ifnull(sum(cramt-dramt),0) NpaInt from npa_recon where acno = '" + acno + "' and dt <='" + prvFirstDt + "') a,\n"
                                        + "(select ifnull(sum(PRINAMT),0) - ifnull(sum(excessamt),0) PrimOverDue from emidetails \n"
                                        + "where acno = '" + acno + "' and duedt <='" + prvFirstDt + "' and status='UNPAID')b\n"
                                        + ")").getResultList();
                                if (!list.isEmpty()) {
                                    Vector vtr = (Vector) list.get(0);
                                    memorandumInterest = Double.parseDouble(vtr.get(0).toString());
                                    prinOverDue = Double.parseDouble(vtr.get(1).toString());
                                }
                            } else {
                                List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("A/Cs Whose Plan Has Finished", acno, 1, 200, frDt, acno.substring(0, 2), "", 0, isExceessEmi, null, 0);
                                if (!overdueEmiList.isEmpty()) {
                                    emiOverDue = overdueEmiList.get(0).getAmountOverdue().doubleValue();
                                }
                            }
                        }
                        pojo.setAcNo(acno);
                        pojo.setAcType(acctCode);
                        pojo.setCustId(custId);
                        if (bnkCode.equalsIgnoreCase("NABU")) {
                            if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {
                                pojo.setInstallment(prinAmt + interestAmt + Math.abs(memorandumInterest) + prinOverDue);
                            } else {
                                pojo.setInstallment(prinAmt + interestAmt + emiOverDue);
                            }
                        } else {
                            pojo.setInstallment(prinAmt + interestAmt + lipAmt);
                        }
                        pojo.setName(custName);
                        pojo.setOutStandBal(Math.abs(outBal));
                        pojo.setPrinAmt(prinAmt);
                        pojo.setIntAmt(interestAmt);
                        pojo.setDate(dueDt);
                        pojo.setAcTypeDesc(actMap.get(acctCode));
                        pojo.setAccMang(accountManager);
//                        pojo.setAccMangDesc(common.getOfficeDecByType(accountManager));
//                        pojo.setGroupId(depId);
//                        pojo.setGroupIdDesc(common.getDeptDescByGroupId(depId));
                        if (bnkCode.equalsIgnoreCase("NABU")) {
                            if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {
                                pojo.setLipAmt(Math.abs(memorandumInterest) + prinOverDue);
                            } else {
                                pojo.setLipAmt(emiOverDue);
                            }
                        } else {
                            pojo.setLipAmt(lipAmt);
                        }
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<LoanOutStandingBalancePojo> getLoanOutStandingReport(String brCode, String acType, String toDate, double frAmt, double toAmt, String acNature, String balType) throws ApplicationException {
        List<LoanOutStandingBalancePojo> dataList = new ArrayList<LoanOutStandingBalancePojo>();
        List result = new ArrayList();
        List intTblList = new ArrayList();
        double outStandBal = 0d, roi = 0d;
        try {
            String accountNatuer = "", acctCode = "";
            if (acType.equalsIgnoreCase("ALL")) {
                if (balType.equalsIgnoreCase("'C','B'")) {
                    accountNatuer = acNature;
                    acctCode = "select acctcode from accounttypemaster where acctnature in('" + accountNatuer + "') and CrDbFlag in('C','B')";
                } else {
                    accountNatuer = acNature;
                    acctCode = "select acctcode from accounttypemaster where acctnature in('" + accountNatuer + "') and CrDbFlag in('D','B')";
                }
            } else {
                accountNatuer = fts.getAcNatureByCode(acType);
                acctCode = "select acctcode from accounttypemaster where acctcode in ('" + acType + "')";
            }
            String tableName2 = "";
            String tableName = common.getTableName(accountNatuer);
            if (accountNatuer.equalsIgnoreCase(CbsConstant.FIXED_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.MS_AC)) {
                tableName2 = "td_accountmaster";
            } else {
                tableName2 = "accountmaster";
            }
            if (brCode.equalsIgnoreCase("90") || brCode.equalsIgnoreCase("0A")) {
                if (accountNatuer.equalsIgnoreCase("FD") || accountNatuer.equalsIgnoreCase("MS")) {
                    //result = em.createNativeQuery("select acno,custname,date_format(OpeningDt,'%d/%m/%Y') from td_accountmaster where acno in(select acno from td_recon where substring(acno,3,2) in(" + acctCode + ") and dt <= '" + toDate + "' and Trantype<>27  and closeflag is Null AND auth = 'Y'group by " + subQuery + ")").getResultList();
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(a.OpeningDt,'%d/%m/%Y'),a.accstatus,b.customerid,d.ref_desc,date_format(v.MatDt,'%d/%m/%Y') "
                            + "from td_accountmaster a,td_recon r,cbs_customer_master_detail b,customerid c,cbs_ref_rec_type d,td_vouchmst v "
                            + "where a.acno=r.acno and a.acno = c.acno and a.acno = v.acno /*and v.status = 'A'*/"
                            + "and b.customerid = c.CustId and d.ref_rec_no='024' and b.operationalRiskrating = d.ref_code and Trantype<>27 "
                            + "and closeflag is NUll and accttype in(" + acctCode + ")"
                            + "and r.dt <= '" + toDate + "' and (a.closingdate = '' or a.closingdate is null or a.closingdate >'" + toDate + "')"
                            + "group by r.acno having(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0))"
                            + "between " + frAmt + " and " + toAmt + " order by a.acno ").getResultList();
                } else if (accountNatuer.equalsIgnoreCase(CbsConstant.SAVING_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.CURRENT_AC) && balType.equalsIgnoreCase("'C','B'")) {
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(a.OpeningDt,'%d/%m/%Y'),a.accstatus,b.customerid,d.ref_desc,"
                            + "l.interest_table_code from accountmaster a," + tableName + " r,cbs_loan_acc_mast_sec l,cbs_customer_master_detail b,customerid c,"
                            + "cbs_ref_rec_type d where a.acno=l.acno and r.acno=l.acno and  a.acno = c.acno and b.customerid = c.CustId and d.ref_rec_no='024' "
                            + "and b.operationalRiskrating = d.ref_code and accttype in(" + acctCode + ")"
                            + "and r.dt <= '" + toDate + "' and (a.closingdate = '' or a.closingdate is null or a.closingdate >'" + toDate + "')"
                            + "group by r.acno having(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0))"
                            + "between " + frAmt + " and " + toAmt + " order by a.acno").getResultList();

                    intTblList = em.createNativeQuery("select distinct substring(acno,3,2),interest_table_code from "
                            + "cbs_loan_acc_mast_sec where substring(acno,3,2) in(" + acctCode + ")").getResultList();

                } else if (accountNatuer.equalsIgnoreCase(CbsConstant.RECURRING_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(a.OpeningDt,'%d/%m/%Y'),a.accstatus,b.customerid,d.ref_desc,date_format(a.Rdmatdate,'%d/%m/%Y')"
                            + "from accountmaster a," + tableName + " r,cbs_customer_master_detail b,customerid c,cbs_ref_rec_type d where a.acno=r.acno "
                            + "and  a.acno = c.acno and b.customerid = c.CustId and d.ref_rec_no='024' and b.operationalRiskrating = d.ref_code "
                            + "and accttype in(" + acctCode + ")and r.dt <= '" + toDate + "' "
                            + "and (a.closingdate = '' or a.closingdate is null or a.closingdate >'" + toDate + "')"
                            + "group by r.acno having(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0))"
                            + "between " + frAmt + " and " + toAmt + " order by a.acno").getResultList();
                } else if (accountNatuer.equalsIgnoreCase(CbsConstant.TERM_LOAN) || accountNatuer.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || accountNatuer.equalsIgnoreCase(CbsConstant.CURRENT_AC) && balType.equalsIgnoreCase("'D','B'")) {
                    result = em.createNativeQuery("select acno,custname,date_format(OpeningDt,'%d/%m/%Y') from " + tableName2 + " where acno in (select distinct(acno) from " + tableName + " "
                            + "where substring(acno,3,2) in( " + acctCode + " )  and dt <= '" + toDate + "' group by acno having(ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) between " + frAmt + " and " + toAmt + ")"
                            + "and (closingdate = '' or closingdate is null or closingdate >'" + toDate + "') order by acno ").getResultList();
                }
            } else {
                if (accountNatuer.equalsIgnoreCase("FD") || accountNatuer.equalsIgnoreCase("MS")) {
                    //result = em.createNativeQuery("select acno,custname,date_format(OpeningDt,'%d/%m/%Y') from td_accountmaster where acno in(select acno from td_recon where substring(acno,1,2) = '" + brCode + "' and substring(acno,3,2)in(" + acctCode + ")and dt <= '" + toDate + "' and Trantype<>27  and closeflag is Null AND auth = 'Y' group by " + subQuery + ")").getResultList();
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(a.OpeningDt,'%d/%m/%Y'),a.accstatus,b.customerid,d.ref_desc,date_format(v.MatDt,'%d/%m/%Y') "
                            + "from td_accountmaster a,td_recon r,cbs_customer_master_detail b,customerid c,cbs_ref_rec_type d,td_vouchmst v "
                            + "where a.curbrcode = '" + brCode + "' and  a.acno=r.acno and a.acno = c.acno and a.acno = v.acno /*and v.status = 'A' */"
                            + "and b.customerid = c.CustId and d.ref_rec_no='024' and b.operationalRiskrating = d.ref_code and Trantype<>27 "
                            + "and closeflag is NUll and accttype in(" + acctCode + ")"
                            + "and r.dt <= '" + toDate + "' and (a.closingdate = '' or a.closingdate is null or a.closingdate >'" + toDate + "')"
                            + "group by r.acno having(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0))"
                            + "between " + frAmt + " and " + toAmt + " order by a.acno").getResultList();
                } else if (accountNatuer.equalsIgnoreCase(CbsConstant.SAVING_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.CURRENT_AC) && balType.equalsIgnoreCase("'C','B'")) {
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(a.OpeningDt,'%d/%m/%Y'),a.accstatus,b.customerid,d.ref_desc, "
                            + "l.interest_table_code from accountmaster a," + tableName + " r,cbs_loan_acc_mast_sec l,cbs_customer_master_detail b,customerid c,"
                            + "cbs_ref_rec_type d where a.curbrcode = '" + brCode + "' and a.acno=l.acno and r.acno=l.acno and  a.acno = c.acno and b.customerid = c.CustId and d.ref_rec_no='024' "
                            + "and b.operationalRiskrating = d.ref_code and accttype in(" + acctCode + ")"
                            + "and r.dt <= '" + toDate + "' and (a.closingdate = '' or a.closingdate is null or a.closingdate >'" + toDate + "')"
                            + "group by r.acno having(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0))"
                            + "between " + frAmt + " and " + toAmt + " order by a.acno").getResultList();

                    intTblList = em.createNativeQuery("select distinct substring(acno,3,2),interest_table_code from "
                            + "cbs_loan_acc_mast_sec where substring(acno,3,2) in(" + acctCode + ")").getResultList();
                } else if (accountNatuer.equalsIgnoreCase(CbsConstant.RECURRING_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(a.OpeningDt,'%d/%m/%Y'),a.accstatus,b.customerid,d.ref_desc,date_format(a.Rdmatdate,'%d/%m/%Y')"
                            + "from accountmaster a," + tableName + " r,cbs_customer_master_detail b,customerid c,cbs_ref_rec_type d where a.curbrcode = '" + brCode + "' and a.acno=r.acno "
                            + "and  a.acno = c.acno and b.customerid = c.CustId and d.ref_rec_no='024' and b.operationalRiskrating = d.ref_code "
                            + "and accttype in(" + acctCode + ")and r.dt <= '" + toDate + "' "
                            + "and (a.closingdate = '' or a.closingdate is null or a.closingdate >'" + toDate + "')"
                            + "group by r.acno having(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0))"
                            + "between " + frAmt + " and " + toAmt + " order by a.acno").getResultList();
                } else if (accountNatuer.equalsIgnoreCase(CbsConstant.TERM_LOAN) || accountNatuer.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || accountNatuer.equalsIgnoreCase(CbsConstant.CURRENT_AC) && balType.equalsIgnoreCase("'D','B'")) {
                    result = em.createNativeQuery("select acno,custname,date_format(OpeningDt,'%d/%m/%Y') from " + tableName2 + " where acno in (select distinct(acno) from " + tableName + " "
                            + "where substring(acno,1,2) = '" + brCode + "' and substring(acno,3,2) in ( " + acctCode + " )  and dt <= '" + toDate + "' group by acno having(ifnull(sum(ifnull(dramt,0)),0)-ifnull(sum(ifnull(cramt,0)),0)) between " + frAmt + " and " + toAmt + ")"
                            + "and (closingdate = '' or closingdate is null or closingdate >'" + toDate + "')order by acno ").getResultList();
                }
            }
            if (!result.isEmpty()) {
                String acTypeDesc = common.getAcctDecription(acType);
                Double savingCommonRoi = 0.0;
                Map<String, Double> savingIntMap = new HashMap<String, Double>();
                if (accountNatuer.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    for (int z = 0; z < intTblList.size(); z++) {
                        Vector intVec = (Vector) intTblList.get(z);
                        String intAcType = intVec.get(0).toString();
                        String intTblCode = intVec.get(1).toString();

                        List<SavingIntRateChangePojo> intSlabList = sbRemote.getSavingRoiChangeDetail(intTblCode, toDate, toDate);
                        SavingIntRateChangePojo obj = intSlabList.get(0);
                        savingCommonRoi = obj.getRoi();

                        savingIntMap.put(intTblCode, savingCommonRoi);
                    }
                }
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    LoanOutStandingBalancePojo pojo = new LoanOutStandingBalancePojo();
                    String interestCode = "", matDate = "";
                    if (accountNatuer.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        interestCode = ele.get(6).toString();
                    }
                    outStandBal = common.getBalanceOnDate(ele.get(0).toString(), toDate);
                    if (accountNatuer.equalsIgnoreCase(CbsConstant.TERM_LOAN) || accountNatuer.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || accountNatuer.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        if (ele.get(0).toString().substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                            List roiList1 = em.createNativeQuery("select intdeposit from accountmaster where acno = '" + ele.get(0).toString() + "'").getResultList();
                            Vector vtr = (Vector) roiList1.get(0);
                            roi = Double.parseDouble(vtr.get(0).toString());
                        } else {
                            roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Math.abs(outStandBal), toDate, ele.get(0).toString()));
                        }

                    } else if (accountNatuer.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        roi = savingIntMap.get(interestCode);
                    } else if ((accountNatuer.equalsIgnoreCase(CbsConstant.FIXED_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.MS_AC))) {
                        List roiList = em.createNativeQuery("select ROI,date_format(MatDt,'%d/%m/%Y') from td_vouchmst where acno = '" + ele.get(0).toString() + "' and Status = 'A' and VoucherNo = (select max(VoucherNo) from td_vouchmst where acno = '" + ele.get(0).toString() + "' and status = 'A')").getResultList();
                        if (!roiList.isEmpty()) {
                            Vector vtr = (Vector) roiList.get(0);
                            roi = Double.parseDouble(vtr.get(0).toString());
                            matDate = vtr.get(1).toString();

                        }

                    } else if (accountNatuer.equalsIgnoreCase(CbsConstant.RECURRING_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        List roiList1 = em.createNativeQuery("select intdeposit from accountmaster where acno = '" + ele.get(0).toString() + "'").getResultList();
                        Vector vtr = (Vector) roiList1.get(0);
                        roi = Double.parseDouble(vtr.get(0).toString());
                    }
                    if (outStandBal != 0) {
                        pojo.setAcNo(ele.get(0).toString());
                        pojo.setCustName(ele.get(1).toString());
                        pojo.setRoi(roi);
                        pojo.setBal(Math.abs(outStandBal));
                        pojo.setOpeningDt(ele.get(2).toString());

                        if (!(accountNatuer.equalsIgnoreCase(CbsConstant.TERM_LOAN) || accountNatuer.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                || accountNatuer.equalsIgnoreCase(CbsConstant.CURRENT_AC) && balType.equalsIgnoreCase("'D','B'"))) {

                            if (ele.get(3).toString().equalsIgnoreCase("1")) {
                                pojo.setStatus("OPERATIVE");
                            } else if (ele.get(3).toString().equalsIgnoreCase("2")) {
                                pojo.setStatus("INOPERATIVE");
                            } else if (ele.get(3).toString().equalsIgnoreCase("3")) {
                                pojo.setStatus("SUIT FIELDS");
                            } else if (ele.get(3).toString().equalsIgnoreCase("4")) {
                                pojo.setStatus("FROZEN");
                            } else if (ele.get(3).toString().equalsIgnoreCase("5")) {
                                pojo.setStatus("RECALLED");
                            } else if (ele.get(3).toString().equalsIgnoreCase("6")) {
                                pojo.setStatus("DECREED");
                            } else if (ele.get(3).toString().equalsIgnoreCase("7")) {
                                pojo.setStatus("WITHDRAWAL STOPPED");
                            } else if (ele.get(3).toString().equalsIgnoreCase("8")) {
                                pojo.setStatus("OPERATION STOPPED");
                            } else if (ele.get(3).toString().equalsIgnoreCase("10")) {
                                pojo.setStatus("LIEN MARKED");
                            }

                            pojo.setCustId(ele.get(4).toString());
                            pojo.setRiskCategory(ele.get(5).toString());
                            if (accountNatuer.equalsIgnoreCase(CbsConstant.SAVING_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.CURRENT_AC) && balType.equalsIgnoreCase("'C','B'")) {
                                pojo.setProductCode(ele.get(6).toString());
                            } else {
                                if ((accountNatuer.equalsIgnoreCase(CbsConstant.FIXED_AC) || accountNatuer.equalsIgnoreCase(CbsConstant.MS_AC))) {
                                    pojo.setMatDate(matDate);
                                } else {
                                    pojo.setMatDate(ele.get(6).toString());
                                }
                            }
                        }
                        pojo.setBranch(ele.get(0).toString().substring(0, 2));
                        pojo.setActType(acType);
                        pojo.setActDesc(acTypeDesc);
                        pojo.setFrAmt(frAmt);
                        pojo.setToAmt(toAmt);
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public String getRefRecDesc(String refCode) throws ApplicationException {
        try {
            List dataList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where  ref_rec_no = 207 and ref_code = '" + refCode + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please fill data in cbs_ref_rec_type.");
            }
            Vector element = (Vector) dataList.get(0);
            return element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * Here acctType is CC for 02
     *
     * @param acno
     * @return
     * @throws ApplicationException
     */
    public BigDecimal getCheckLimit(String acno) throws ApplicationException {
        BigDecimal checkLimit = new BigDecimal("0");
        List limitList = null;
        try {
            String acctType = fts.getAcctTypeByCode(fts.getAccountCode(acno));
            if (acctType.equalsIgnoreCase(CbsConstant.CC_AC)) {
                limitList = em.createNativeQuery("select ifnull(dplimit,0) from loan_appparameter where acno = '" + acno + "'").getResultList();
            } else if (acctType.equalsIgnoreCase(CbsConstant.OD_AC)) {
                limitList = em.createNativeQuery("select ifnull(odlimit,0) from loan_appparameter where acno = '" + acno + "'").getResultList();
            }
            if (!(limitList == null)) {
                Vector limitVec = (Vector) limitList.get(0);
                checkLimit = new BigDecimal(limitVec.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return checkLimit;
    }

    public BigDecimal getOverDueAmount(String acno, String asOnDt) throws ApplicationException {
        BigDecimal overDueAmount = new BigDecimal("0");
        try {
            List limitList = em.createNativeQuery("select sanctionlimit, odlimit from loan_appparameter where acno = '" + acno + "'").getResultList();
            Vector limitVec = (Vector) limitList.get(0);
            BigDecimal sanctionLimit = new BigDecimal(limitVec.get(0).toString());
            BigDecimal odLimit = new BigDecimal(limitVec.get(1).toString());

            BigDecimal outStandBal = new BigDecimal(loanInterestCalculationBean.outStandingAsOnDate(acno, asOnDt)).abs();
            overDueAmount = (outStandBal.subtract(odLimit)).compareTo(new BigDecimal("0")) == -1 ? new BigDecimal("0") : outStandBal.subtract(odLimit);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return overDueAmount;
    }

    public List<DateSlabPojo> getLimitOnRangeOfDate(String fromDt, String toDt, String acno) throws ApplicationException {
        List<DateSlabPojo> list = new ArrayList<DateSlabPojo>();
        try {
            List dateList = em.createNativeQuery("select aclimit,date_format(enterdate,'%Y%m%d') from loan_oldinterest "
                    + "where acno='" + acno + "' and enterdate between '" + fromDt + "' and '" + toDt + "' order "
                    + "by enterdate").getResultList();
            if (!dateList.isEmpty()) {
//                DateSlabPojo pojo = new DateSlabPojo();
//                pojo.setFromDt(fromDt);
//                pojo.setToDt(toDt);
//                pojo.setLimit(getCheckLimit(acno));
//                list.add(pojo);
//            } else {
                String tempFromDt = fromDt;
                for (int i = 0; i < dateList.size(); i++) {
                    DateSlabPojo pojo = new DateSlabPojo();
                    Vector element = (Vector) dateList.get(i);
                    String tempLimit = element.get(0).toString();
                    String tempToDt = element.get(1).toString();

                    pojo.setFromDt(tempFromDt);
                    pojo.setToDt(tempToDt);
                    pojo.setLimit(new BigDecimal(tempLimit));
                    list.add(pojo);

                    tempFromDt = CbsUtil.dateAdd(toDt, 1);
                }

                DateSlabPojo pojo = new DateSlabPojo();
                pojo.setFromDt(tempFromDt);
                pojo.setToDt(toDt);
                pojo.setLimit(getCheckLimit(acno));
                list.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public NpaAccountDetailPojo npaAccountAddition(String accStatus, String tillDate, String acno, String custname, BigDecimal outStandBal, String acctNature) throws ApplicationException {
        NpaAccountDetailPojo pojo = new NpaAccountDetailPojo();
        String npaDt = "", newNpaDt = "", reportParameter = "";
        long dayDiff = 0, newDayDiff = 0, currentStatusDayDiff = 0;
        int noOfPendingEmi = 0;
        BigDecimal ovrdueAmt = new BigDecimal("0"), npaInt = new BigDecimal("0");
        List limitList = null;
        Vector limitVec = null;
        try {
            int isExceessEmi = common.isExceessEmi(tillDate);
            String npaDtQuery = "", presentStatus = "";
            List parameterStatus = em.createNativeQuery("select code from cbs_parameterinfo where name = 'NPA_STATUS'").getResultList();
            if (!parameterStatus.isEmpty()) {
                Vector paraStatusvect = (Vector) parameterStatus.get(0);
                reportParameter = paraStatusvect.get(0).toString();
            }

            if (accStatus.equalsIgnoreCase("11")) {
                npaDtQuery = "npadt";
                presentStatus = "SUB";
            } else if (accStatus.equalsIgnoreCase("12")) {
                npaDtQuery = "dbtdt";
                presentStatus = "DOU";
            } else if (accStatus.equalsIgnoreCase("13")) {
                npaDtQuery = "dcdt";
                presentStatus = "LOS";
            } else if (accStatus.equalsIgnoreCase("1")) {
                npaDtQuery = "STD";
                presentStatus = "STD";
            }
            if (accStatus.equalsIgnoreCase("11") || accStatus.equalsIgnoreCase("12") || accStatus.equalsIgnoreCase("13")) {
                if (reportParameter.equalsIgnoreCase("accountstatus")) {
                    limitList = em.createNativeQuery("SELECT ifnull(date_format(MAX(EFFDT),'%Y%m%d'),'19000101') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + tillDate + "'").getResultList();
                } else {
                    limitList = em.createNativeQuery("select ifnull(date_format(" + npaDtQuery + ",'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acno + "'").getResultList();
                }
                limitVec = (Vector) limitList.get(0);
                npaDt = limitVec.get(0).toString();
            }

            List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + tillDate + "'").getResultList();
            Vector minElement = (Vector) minEffList.get(0);
            String npaDate = minElement.get(0).toString();

            limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                    + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
            limitVec = (Vector) limitList.get(0);
            String lastCrDt = limitVec.get(0).toString();
            String minUnPaidDt = "19000101";
            if (lastCrDt.equalsIgnoreCase("19000101")) {
                limitList = em.createNativeQuery("select openingdt from  accountmaster where acno = '" + acno + "' ").getResultList();
                limitVec = (Vector) limitList.get(0);
                lastCrDt = limitVec.get(0).toString();
            }
            List npaAmtList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from npa_recon where acno='" + acno
                    + "' and dt <= '" + tillDate + "'").getResultList();
            if (!npaAmtList.isEmpty()) {
                Vector amVec = (Vector) npaAmtList.get(0);
                npaInt = new BigDecimal(amVec.get(0).toString()).multiply(new BigDecimal("-1"));
            }

//            String acctNature = fts.getAccountNature(acno);
            if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                List checklist = em.createNativeQuery("select * from emidetails where acno = '" + acno + "'").getResultList();
                if (!checklist.isEmpty()) {
                    List paidEmiList = em.createNativeQuery("select date_format(max(duedt),'%Y%m%d'), acno "
                            + "from emidetails where acno = '" + acno + "' and status = 'PAID' and "
                            + "duedt <= '" + tillDate + "' group by acno").getResultList();
                    if (!paidEmiList.isEmpty()) {
                        Vector emiPaidDtVect = (Vector) paidEmiList.get(0);
                        minUnPaidDt = emiPaidDtVect.get(0).toString();
                    }
//                    if(ymdFormat.parse(lastCrDt).compareTo(ymdFormat.parse(minUnPaidDt))<0 ){
//                        lastCrDt = minUnPaidDt;
//                    } 
//                    List acNoList1 = em.createNativeQuery("select distinct (acno) from emidetails where status = 'unpaid' and acno ='" + acno + "'").getResultList();
//                    if (acNoList1.size() == 0) {
//                        List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("A/Cs Whose Plan Has Finished", acno, 1, 200, tillDate, common.getBrncodeByAcno(acno), "", 0, isExceessEmi, null, 0);
//                        for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
//                            ovrdueAmt = pojo1.getAmountOverdue();
//                            noOfPendingEmi = pojo1.getNoOfEmiOverdue();
//                        }
//                    } else {
//                        List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("", acno, 1, 240, tillDate, common.getBrncodeByAcno(acno), "", 0, isExceessEmi, null, 0);
//                        for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
//                            ovrdueAmt = pojo1.getAmountOverdue();
//                            noOfPendingEmi = pojo1.getNoOfEmiOverdue();
//                        }
//                    }
                }
            }
            currentStatusDayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
            //Setting of common field for pojo.
            dayDiff = CbsUtil.dayDiff(ymdFormat.parse(npaDt), ymdFormat.parse(tillDate));
            pojo.setAcNo(acno);
            pojo.setCustName(custname);
            pojo.setOutstdBal(outStandBal);
            pojo.setNoOfPendingEmi(noOfPendingEmi);
//            System.out.println("acno:"+acno+"; CurrentStatus:"+presentStatus);            
            pojo.setCurrentStatusNoOfDays(currentStatusDayDiff);
            pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
            pojo.setLastEmiPaidDt(minUnPaidDt.equalsIgnoreCase("19000101") ? "" : dmyFormat.format(ymdFormat.parse(minUnPaidDt)));
            pojo.setOverDueAmt(ovrdueAmt);
            pojo.setNpaAmt(npaInt);
            pojo.setEmiExist("N");

            if (accStatus.equalsIgnoreCase("11")) {
                limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                        + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                        + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('SUB'))").getResultList();
                limitVec = (Vector) limitList.get(0);
//                            String cycle = limitVec.get(0).toString();
                long subDays = Long.parseLong(limitVec.get(1).toString());
                pojo.setNpaDate(npaDate);
//                pojo.setDueDt(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                pojo.setDueDt(npaDate);
                pojo.setCurrentStatus(getRefRecDesc("SUB"));
                pojo.setSortAcStatus("11"); //This is used for sorting 
                pojo.setStatus(getRefRecDesc("SUB"));
                pojo.setMarkFlag("U");                  //This account will not mark as NPA.
                pojo.setAcStatus("11");
                if (dayDiff > subDays) {
                    // Add for doubtful - upto 1 year
                    pojo.setAcStatus("12");
                    pojo.setStatus(getRefRecDesc("D1"));
                    pojo.setMarkFlag("M");              //This account will mark as NPA.
                    pojo.setTotalDayDiff(dayDiff);
                    pojo.setCurrentStatusNoOfDays(dayDiff - subDays);
//                    if(ymdFormat.parse(lastCrDt).compareTo(ymdFormat.parse(npaDt))<0){
//                        npaDt = lastCrDt;
//                    } 
                    pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(npaDt, (int) subDays))));
                }
            } else if (accStatus.equalsIgnoreCase("12")) {
                //Pick all three doubtfull days and compare with dayDiff. Based on that fill the pojo for report only.
//                if(ymdFormat.parse(lastCrDt).compareTo(ymdFormat.parse(npaDt))<0){
//                    npaDt = lastCrDt;
//                }
                dayDiff = CbsUtil.dayDiff(ymdFormat.parse(npaDt), ymdFormat.parse(tillDate));
                newNpaDt = npaDt;
                String cycle = "";
                limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                        + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                        + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                        + "order by days").getResultList();
                if (limitList.isEmpty()) {
                    throw new ApplicationException("Please fill data in cbs_ref_rec_type.");
                }
                for (int j = 0; j < limitList.size(); j++) {
                    limitVec = (Vector) limitList.get(j);
                    if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                        if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                            pojo.setCurrentStatus(getRefRecDesc("D1"));
                            pojo.setSortAcStatus("12D1"); //This is used for sorting 
                            cycle = limitVec.get(0).toString();
                            newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                            break;
                        }
                    } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                        if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                            pojo.setCurrentStatus(getRefRecDesc("D2"));
                            pojo.setSortAcStatus("12D2"); //This is used for sorting 
                            cycle = limitVec.get(0).toString();
                            newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                            break;
                        }
                    } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                        pojo.setCurrentStatus(getRefRecDesc("D3"));
                        pojo.setSortAcStatus("12D3"); //This is used for sorting 
                        cycle = limitVec.get(0).toString();
                        newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                        break;
                    }
//                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
//                        if (j == 0) {
//                            pojo.setCurrentStatus(getRefRecDesc("D1"));
//                        } else if (j == 1) {
//                            pojo.setCurrentStatus(getRefRecDesc("D2"));
//                        }
//                        cycle = limitVec.get(0).toString();
//                        newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
//                        break;
//                    } else {
//                        pojo.setCurrentStatus(getRefRecDesc("D3"));
//                        cycle = "D3";
//                    }
                    newDayDiff = Long.parseLong(limitVec.get(1).toString());
                }
                pojo.setStatus(getRefRecDesc(cycle));
                pojo.setMarkFlag("U");                      //This account will not mark as NPA.
                pojo.setCurrentStatusNoOfDays(dayDiff - newDayDiff);
                pojo.setTotalDayDiff(dayDiff);
                pojo.setDueDt(dmyFormat.format(ymdFormat.parse(newNpaDt)));
                pojo.setNpaDate(dmyFormat.format(ymdFormat.parse(newNpaDt)));
                pojo.setAcStatus("12");
            } else if (accStatus.equalsIgnoreCase("13")) {
                //Just add in the report.
//                if(ymdFormat.parse(lastCrDt).compareTo(ymdFormat.parse(npaDt))<0){
//                    npaDt = lastCrDt;
//                }
                pojo.setCurrentStatus(getRefRecDesc("L"));
                pojo.setSortAcStatus("13"); //This is used for sorting 
                pojo.setStatus(getRefRecDesc("L"));
                pojo.setMarkFlag("U");                      //This account will not mark as NPA.
                pojo.setDueDt(dmyFormat.format(ymdFormat.parse(npaDt)));
                pojo.setNpaDate(npaDate);
                pojo.setAcStatus("13");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }

    public List<StatemenrtOfRecoveriesPojo> getStatementRecoveriesData(String area, String tillDate, String brCode, String reportOption) throws ApplicationException {
        List<StatemenrtOfRecoveriesPojo> dataList = new ArrayList<StatemenrtOfRecoveriesPojo>();
        try {
            String premiumCode = "", premiumDt = "";
            List premiumList = em.createNativeQuery("select Acctcode from parameterinfo_miscincome where Purpose = 'LIP_PREMIUM'").getResultList();
            if (!premiumList.isEmpty()) {
                Vector vtr = (Vector) premiumList.get(0);
                premiumCode = vtr.get(0).toString();
            }
            premiumDt = fts.getCodeFromCbsParameterInfo("LIP_PREMIUM_DT");
            double tmpLipAmt = 0;
            List result2 = em.createNativeQuery("select charges from parameterinfo_miscincome where Purpose = 'LIP_PREMIUM' and Acctcode ='" + premiumCode + "'").getResultList();
            if (!result2.isEmpty()) {
                Vector ele3 = (Vector) result2.get(0);
                tmpLipAmt = Double.parseDouble(ele3.get(0).toString());
            }
            List list = em.createNativeQuery("select charges from parameterinfo_miscincome where Purpose = 'WELFARE_FUND' and Acctcode ='" + CbsAcCodeConstant.SF_AC.getAcctCode() + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            double tmpWellfareFund = Double.parseDouble(ele.get(0).toString());

            if (reportOption.equalsIgnoreCase("E")) {

                List tblList = em.createNativeQuery("select distinct substring(acno,3,2),interest_table_code from "
                        + "cbs_loan_acc_mast_sec where substring(acno,3,2) in(select AcctCode from accounttypemaster where acctNature = '" + CbsConstant.TERM_LOAN + "')").getResultList();
                Map<String, String> acTypeMap = new HashMap<String, String>();
                Map<String, String> acTypeMap1 = new HashMap<String, String>();
                for (int z = 0; z < tblList.size(); z++) {
                    Vector Vec = (Vector) tblList.get(z);
                    String AcType = Vec.get(0).toString();
                    String tblCode = Vec.get(1).toString();
                    String fromDt = loanInterestCalculationBean.allFromDt(AcType, brCode, "f");
                    String toDt = loanInterestCalculationBean.allFromDt(AcType, brCode, "t");
                    acTypeMap.put(AcType, fromDt);
                    acTypeMap1.put(AcType, toDt);
                }
                double grBalance = 0, grInt = 0;
                List result = em.createNativeQuery("select det.custid,det.acno,det.custfullname,det.FatherName,det.acctcode,det.AcctDesc,sum(det.bal),det.Regfoliono,det.empId from ("
                        + "select e.custid,a.acno,c.custfullname,trim(concat(ifnull(c.fathername,''),' ',ifnull(c.FatherMiddleName,''),' ',ifnull(c.FatherLastName,''))) as FatherName,d.acctcode,d.AcctDesc,sum(r.cramt-r.dramt) as bal,s.Regfoliono,ifnull(c.employeeNo,'') as empId "
                        + "from accountmaster a,cbs_customer_master_detail c,accounttypemaster d,customerid e,loan_recon r,share_holder s where a.acno in "
                        + "(select acno from customerid where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null))and cast(s.custid as unsigned) = e.custid "
                        + "and s.custid = c.customerid "
                        + "and a.acno = e.acno and a.acno = r.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "') "
                        + "and a.curbrcode = '" + brCode + "' and a.accttype in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "')) "
                        + "and  a.accttype  = d.acctcode and r.dt<='" + tillDate + "' group by r.acno having sum(r.cramt-r.dramt)<> 0 "
                        + "union "
                        + "select e.custid,a.acno,c.custfullname,trim(concat(ifnull(c.fathername,''),' ',ifnull(c.FatherMiddleName,''),' ',ifnull(c.FatherLastName,''))) as FatherName,d.acctcode,d.AcctDesc,0 as bal,s.Regfoliono,ifnull(c.employeeNo,'') as empId "
                        + "from accountmaster a,cbs_customer_master_detail c,accounttypemaster d,customerid e,share_holder s where a.acno in "
                        + "(select acno from customerid where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null)) and cast(s.custid as unsigned) = e.custid "
                        + "and s.custid = c.customerid "
                        + "and a.acno = e.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "') "
                        + "and a.curbrcode = '" + brCode + "' and a.accttype in('" + premiumCode + "','31')and  a.accttype  = d.acctcode "
                        + ")det "
                        + " group by det.custid order by det.custid").getResultList();

                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        StatemenrtOfRecoveriesPojo pojo = new StatemenrtOfRecoveriesPojo();
                        Vector element = (Vector) result.get(i);
                        String custId = element.get(0).toString();
                        String acno = element.get(1).toString();
                        String custName = element.get(2).toString();
                        String faName = element.get(3) == null ? "" : element.get(3).toString();
                        String tmpAcctCode = element.get(4).toString();
                        String acnoDesc = element.get(5).toString();
                        double outstandbal = Double.parseDouble(element.get(6).toString());
                        String folioNo = element.get(7).toString();
                        String empId = element.get(8).toString();
                        double wellfareFund = 0d, lipAmt = 0d;
                        double checkBal1 = getCustIdWiseBal(brCode, custId, premiumDt);
                        // double checkBal = getCustIdWiseBal(brCode, custId, tillDate);
                        double prinAmt = 0, intAmt = 0;
                        List result1 = em.createNativeQuery("select e.custid,a.acno,d.acctcode,sum(r.cramt-r.dramt) as bal\n"
                                + "from accountmaster a,accounttypemaster d,customerid e,loan_recon r where a.acno = e.acno and a.acno = r.acno\n"
                                + "and  e.custid = " + custId + " \n"
                                + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')\n"
                                + "and a.curbrcode = '" + brCode + "' and a.accttype in(select acctcode from accounttypemaster where acctnature in ('TL'))\n"
                                + "and  a.accttype  = d.acctcode and r.dt<='" + tillDate + "' group by r.acno having sum(r.cramt-r.dramt)<> 0\n"
                                + "union\n"
                                + "select e.custid,a.acno,d.acctcode,0 as bal\n"
                                + "from accountmaster a,accounttypemaster d,customerid e where e.custid =" + custId + " \n"
                                + "and a.acno = e.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')\n"
                                + "and a.curbrcode = '" + brCode + "' and a.accttype ='" + premiumCode + "'and  a.accttype  = d.acctcode\n"
                                + "order by 1,2").getResultList();
                        if (!result1.isEmpty()) {
                            for (int j = 0; j < result1.size(); j++) {
                                Vector vtr = (Vector) result1.get(j);
                                String dAcno = vtr.get(1).toString();
                                String acctCode = vtr.get(2).toString();
                                double tmpoutstandbal = Double.parseDouble(vtr.get(3).toString());
                                if (!(acctCode.equalsIgnoreCase(premiumCode)) && tmpoutstandbal != 0) {
                                    LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(acTypeMap.get(acctCode), acTypeMap1.get(acctCode), dAcno, brCode);
                                    double tmpIntAmt = it.getTotalInt();
                                    if (!(tmpIntAmt == 0)) {
                                        tmpIntAmt = tmpIntAmt * -1;
                                    }
                                    intAmt = intAmt + tmpIntAmt;
                                    List result11 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt "
                                            + "from emidetails where acno='" + dAcno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
                                    Vector ele2 = (Vector) result11.get(0);
                                    double tmpPrinAmt = Double.parseDouble(ele2.get(0).toString());
                                    if (tmpPrinAmt == 0) {
                                        if (Math.abs(tmpoutstandbal) > 0) {
                                            result11 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,"
                                                    + "date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails "
                                                    + "where acno='" + dAcno + "' and status='Unpaid' and "
                                                    + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                                                    + "where acno='" + dAcno + "' and status='Unpaid') ").getResultList();
                                        }
                                        ele2 = (Vector) result11.get(0);
                                        tmpPrinAmt = Double.parseDouble(ele2.get(0).toString());
                                        if (tmpPrinAmt == 0) {
                                            tmpPrinAmt = Math.abs(tmpoutstandbal);
                                        } else {
                                            if (tmpPrinAmt > Math.abs(tmpoutstandbal)) {
                                                tmpPrinAmt = Math.abs(tmpoutstandbal);
                                            } else {
                                                tmpPrinAmt = tmpPrinAmt;
                                            }
                                        }
                                    } else {
                                        if (tmpPrinAmt > Math.abs(tmpoutstandbal)) {
                                            tmpPrinAmt = Math.abs(tmpoutstandbal);
                                        } else {
                                            tmpPrinAmt = tmpPrinAmt;
                                        }
                                    }
                                    prinAmt = prinAmt + tmpPrinAmt;
                                }
                                if (acctCode.equalsIgnoreCase(premiumCode) && checkBal1 != 0) {
                                    lipAmt = tmpLipAmt;
                                }
                                if (outstandbal != 0) {
                                    String[] arr = postalFacadeRemote.getFYearRangeForGenerationDate(dmyFormat.format(ymdFormat.parse(tillDate)));
                                    List paidList = em.createNativeQuery("select * from insurance_details where acno='" + folioNo + "' and due_dt " + "between '" + arr[0] + "' and '" + arr[1] + "' and status='PAID'").getResultList();
                                    if (!paidList.isEmpty()) {
                                        wellfareFund = 0;
                                    } else {
                                        wellfareFund = tmpWellfareFund;
                                    }
                                }
                            }
                        }
                        grBalance = grBalance + outstandbal;
                        grInt = grInt + intAmt + prinAmt + wellfareFund + lipAmt;
                        if (outstandbal != 0) {
                            pojo.setCustId(custId);
                            pojo.setEmpId(empId);
                            pojo.setCustName(custName);
                            pojo.setFatherName(faName);
                            pojo.setOutStdBal(Math.abs(outstandbal));
                            pojo.setTotalAmt(intAmt + prinAmt + wellfareFund + lipAmt);
                            dataList.add(pojo);
                        }
                    }

                    StatemenrtOfRecoveriesPojo pojo1 = new StatemenrtOfRecoveriesPojo();
                    pojo1.setEmpId("");
                    pojo1.setCustName("");
                    pojo1.setFatherName("Grand Total");
                    pojo1.setOutStdBal(Math.abs(grBalance));
                    pojo1.setTotalAmt(grInt);
                    dataList.add(pojo1);

                }
            } else {
                List result = em.createNativeQuery("select det.custid,det.acno,det.custfullname,det.FatherName,det.acctcode,det.AcctDesc,det.bal,det.Regfoliono,det.empId from ("
                        + "select e.custid,a.acno,c.custfullname,trim(concat(ifnull(c.fathername,''),' ',ifnull(c.FatherMiddleName,''),' ',ifnull(c.FatherLastName,''))) as FatherName,d.acctcode,d.AcctDesc,sum(r.cramt-r.dramt) as bal,s.Regfoliono,ifnull(c.employeeNo,'') as empId "
                        + "from accountmaster a,cbs_customer_master_detail c,accounttypemaster d,customerid e,loan_recon r,share_holder s where a.acno in "
                        + "(select acno from customerid where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null))and cast(s.custid as unsigned) = e.custid "
                        + "and s.custid = c.customerid "
                        + "and a.acno = e.acno and a.acno = r.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "') "
                        + "and a.curbrcode = '" + brCode + "' and a.accttype in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "')) "
                        + "and  a.accttype  = d.acctcode and r.dt<='" + tillDate + "' group by r.acno having sum(r.cramt-r.dramt)<> 0 "
                        + "union "
                        + "select e.custid,a.acno,c.custfullname,trim(concat(ifnull(c.fathername,''),' ',ifnull(c.FatherMiddleName,''),' ',ifnull(c.FatherLastName,''))) as FatherName,d.acctcode,d.AcctDesc,0 as bal,s.Regfoliono,ifnull(c.employeeNo,'') as empId "
                        + "from accountmaster a,cbs_customer_master_detail c,accounttypemaster d,customerid e,share_holder s where a.acno in "
                        + "(select acno from customerid where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null)) and cast(s.custid as unsigned) = e.custid "
                        + "and s.custid = c.customerid "
                        + "and a.acno = e.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "') "
                        + "and a.curbrcode = '" + brCode + "' and a.accttype in('" + premiumCode + "','31')and  a.accttype  = d.acctcode "
                        + ")det "
                        + "order by det.custid,det.acno").getResultList();

//            List result = em.createNativeQuery("select det.custid,det.acno,det.custname,det.fathername,det.acctcode,det.AcctDesc,det.bal,det.Regfoliono  "
//                    + "from (select e.custid,a.acno,a.custname,c.fathername,d.acctcode,d.AcctDesc,sum(r.cramt-r.dramt) as bal,s.Regfoliono "
//                    + "from accountmaster a,customermaster c,accounttypemaster d,customerid e,loan_recon r,share_holder s where a.acno in"
//                    + "(select acno from customerid where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null))and s.custid = e.custid "
//                    + "and a.acno = e.acno and a.acno = r.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')"
//                    + "and substring(a.acno,5,6)=c.custno and c.brncode = a.curbrcode and substring(a.acno,3,2)=c.actype "
//                    + "and a.curbrcode = '" + brCode + "' and a.accttype in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "'))"
//                    + "and  a.accttype  = d.acctcode and r.dt<='" + tillDate + "' group by r.acno having sum(r.cramt-r.dramt)<> 0 "
//                    + "union "
//                    + "select e.custid,a.acno,a.custname,c.fathername,d.acctcode,d.AcctDesc,0 as bal,s.Regfoliono "
//                    + "from accountmaster a,customermaster c,accounttypemaster d,customerid e,share_holder s where a.acno in"
//                    + "(select acno from customerid where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null)) and s.custid = e.custid "
//                    + "and a.acno = e.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')"
//                    + "and substring(a.acno,5,6)=c.custno and c.brncode = a.curbrcode and substring(a.acno,3,2)=c.actype "
//                    + "and a.curbrcode = '" + brCode + "' and a.accttype ='" + premiumCode + "'and  a.accttype  = d.acctcode)det "
//                    + "order by det.custid,det.acno").getResultList();

                List tblList = em.createNativeQuery("select distinct substring(acno,3,2),interest_table_code from "
                        + "cbs_loan_acc_mast_sec where substring(acno,3,2) in(select AcctCode from accounttypemaster where acctNature = '" + CbsConstant.TERM_LOAN + "')").getResultList();
//            System.out.println("Start Time" + new Date());
                if (!result.isEmpty()) {

                    Map<String, String> acTypeMap = new HashMap<String, String>();
                    Map<String, String> acTypeMap1 = new HashMap<String, String>();
                    for (int z = 0; z < tblList.size(); z++) {
                        Vector Vec = (Vector) tblList.get(z);
                        String AcType = Vec.get(0).toString();
                        String tblCode = Vec.get(1).toString();

                        String fromDt = loanInterestCalculationBean.allFromDt(AcType, brCode, "f");
                        String toDt = loanInterestCalculationBean.allFromDt(AcType, brCode, "t");

                        acTypeMap.put(AcType, fromDt);
                        acTypeMap1.put(AcType, toDt);
                    }
                    String cId = "";
                    for (int j = 0; j < result.size(); j++) {
                        Vector element = (Vector) result.get(j);
                        StatemenrtOfRecoveriesPojo pojo = new StatemenrtOfRecoveriesPojo();
                        String custId = element.get(0).toString();
                        String acno = element.get(1).toString();
                        String custName = element.get(2).toString();
                        String faName = element.get(3) == null ? "" : element.get(3).toString();
                        String acctCode = element.get(4).toString();
                        String acnoDesc = element.get(5).toString();
                        double outstandbal = Double.parseDouble(element.get(6).toString());
                        String folioNo = element.get(7).toString();
                        String empId = element.get(8).toString();
                        //********************* For Interest ********************
                        double prinAmt = 0, intAmt = 0;
                        if (!(acctCode.equalsIgnoreCase(premiumCode)) && outstandbal != 0) {
//                        System.out.println(acno + " Start in Interest Time  " + new Date());
                            LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(acTypeMap.get(acctCode), acTypeMap1.get(acctCode), acno, brCode);
                            intAmt = it.getTotalInt();
                            if (!(intAmt == 0)) {
                                intAmt = intAmt * -1;
                            }
//                        System.out.println("End in Interest Time  " + new Date());

                            List result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt "
                                    + "from emidetails where acno='" + acno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
                            Vector ele2 = (Vector) result1.get(0);
                            prinAmt = Double.parseDouble(ele2.get(0).toString());
                            if (prinAmt == 0) {
                                if (Math.abs(outstandbal) > 0) {
                                    result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,"
                                            + "date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails "
                                            + "where acno='" + acno + "' and status='Unpaid' and "
                                            + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                                            + "where acno='" + acno + "' and status='Unpaid') ").getResultList();
                                }
                                ele2 = (Vector) result1.get(0);
                                prinAmt = Double.parseDouble(ele2.get(0).toString());
                                if (prinAmt == 0) {
                                    prinAmt = Math.abs(outstandbal);
                                } else {
                                    if (prinAmt > Math.abs(outstandbal)) {
                                        prinAmt = Math.abs(outstandbal);
                                    } else {
                                        prinAmt = prinAmt;
                                    }
                                }
                            } else {
                                if (prinAmt > Math.abs(outstandbal)) {
                                    prinAmt = Math.abs(outstandbal);
                                } else {
                                    prinAmt = prinAmt;
                                }
                            }
                        }

                        double lipAmt = 0d;

                        double checkBal = getCustIdWiseBal(brCode, custId, tillDate);
                        double checkBal1 = getCustIdWiseBal(brCode, custId, premiumDt); // previous concept change for lip demand New concept .
                        if (acctCode.equalsIgnoreCase(premiumCode) && checkBal1 != 0) {
                            lipAmt = tmpLipAmt;
//                        List result2 = em.createNativeQuery("select charges from parameterinfo_miscincome where Purpose = 'LIP_PREMIUM' and Acctcode ='" + premiumCode + "'").getResultList();
//                        if (!result2.isEmpty()) {
//                            Vector ele3 = (Vector) result2.get(0);
//                            lipAmt = Double.parseDouble(ele3.get(0).toString());
//                        }
                        }
                        double wellfareFund = 0d;
                        if (checkBal != 0) {
                            String[] arr = postalFacadeRemote.getFYearRangeForGenerationDate(dmyFormat.format(ymdFormat.parse(tillDate)));
                            List paidList = em.createNativeQuery("select * from insurance_details where acno='" + folioNo + "' and due_dt " + "between '" + arr[0] + "' and '" + arr[1] + "' and status='PAID'").getResultList();
                            if (!paidList.isEmpty()) {
                                wellfareFund = 0;
                            } else {
                                wellfareFund = tmpWellfareFund;
//                            List list = em.createNativeQuery("select charges from parameterinfo_miscincome where Purpose = 'WELFARE_FUND' and Acctcode ='" + CbsAcCodeConstant.SF_AC.getAcctCode() + "'").getResultList();
//                            element = (Vector) list.get(0);
//                            wellfareFund = Double.parseDouble(element.get(0).toString());
                            }
                        }

                        if (cId.equalsIgnoreCase("")) {
                            wellfareFund = wellfareFund;
                            cId = custId;
                        } else if (!cId.equalsIgnoreCase(custId)) {
                            wellfareFund = wellfareFund;
                            cId = custId;
                        } else if (cId.equalsIgnoreCase(custId)) {
                            wellfareFund = 0;
                            cId = custId;
                        }
                        if (checkBal != 0) {
                            double totalAmt = prinAmt + intAmt + lipAmt + wellfareFund;
                            pojo.setCustId(custId);
                            pojo.setPrinAmt(prinAmt);
                            pojo.setIntAmt(intAmt);
                            pojo.setLoanAcNo(acno);
                            pojo.setCustName(custName);
                            pojo.setFatherName(faName);
                            pojo.setLipAmt(lipAmt);
                            if (acctCode.equalsIgnoreCase(premiumCode)) {
                                pojo.setOutStdBal(Math.abs(0));
                            } else {
                                pojo.setOutStdBal(Math.abs(outstandbal));
                            }
                            pojo.setTotalAmt(totalAmt);
                            pojo.setAcnoDesc(acnoDesc);
                            pojo.setPlace(area);
                            pojo.setTheftFund(wellfareFund);
                            pojo.setEmpId(empId);
                            dataList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public String getSchemeCodeAcNoWise(String acNo) throws ApplicationException {
        try {
            List schemeCodeList = em.createNativeQuery("SELECT ifnull(SCHEME_CODE,'CA001') FROM cbs_loan_acc_mast_sec  WHERE ACNO = '" + acNo + "'").getResultList();
            Vector schemeCodeVect = (Vector) schemeCodeList.get(0);
            String schemeCode = schemeCodeVect.get(0).toString();
            return schemeCode;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<LoanSanctionDetailPojo> getLoanSanctionDetail(String brCode, String acttype, String fromDt, String toDt) throws ApplicationException {
        List<LoanSanctionDetailPojo> dataList = new ArrayList<LoanSanctionDetailPojo>();
        List LoanAcNoList = new ArrayList();
        List sancAmtList = new ArrayList();
        double outstandbal = 0d;
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                if (acttype.equalsIgnoreCase("A")) {
                    LoanAcNoList = em.createNativeQuery("select acno,custName,openingDt from accountmaster where (closingdate = '' or ifnull(closingdate,'') = '' or closingdate > '" + toDt + "') and openingDt between '" + fromDt + "' and '" + toDt + "' and accttype in (select acctcode from accounttypemaster where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "') and CrDbFlag in('B','D')) order by acno").getResultList();
                } else {
                    LoanAcNoList = em.createNativeQuery("select acno,custName,openingDt from accountmaster where accttype = '" + acttype + "' and (closingdate = '' or ifnull(closingdate,'') = '' or closingdate > '" + toDt + "') and openingDt between '" + fromDt + "' and '" + toDt + "' order by acno").getResultList();
                }
            } else {
                if (acttype.equalsIgnoreCase("A")) {
                    LoanAcNoList = em.createNativeQuery("select acno,custName,openingDt from accountmaster where curbrcode = '" + brCode + "' and (closingdate = '' or ifnull(closingdate,'') = '' or closingdate > '" + toDt + "') and openingDt between '" + fromDt + "' and '" + toDt + "' and accttype in (select acctcode from accounttypemaster where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "') and CrDbFlag in('B','D')) order by acno").getResultList();
                } else {
                    LoanAcNoList = em.createNativeQuery("select acno,custName,openingDt from accountmaster where curbrcode = '" + brCode + "' and accttype = '" + acttype + "' and (closingdate = '' or ifnull(closingdate,'') = '' or closingdate > '" + toDt + "') and openingDt between '" + fromDt + "' and '" + toDt + "' order by acno").getResultList();
                }
            }

            if (!LoanAcNoList.isEmpty()) {
                for (int i = 0; i < LoanAcNoList.size(); i++) {
                    Vector vtr = (Vector) LoanAcNoList.get(i);
                    LoanSanctionDetailPojo pojo = new LoanSanctionDetailPojo();
                    String acNo = vtr.get(0).toString();
                    String acctCode = fts.getAccountCode(acNo);
                    String acNature = fts.getAcNatureByCode(acctCode);
                    String actDesc = common.getAcctDecription(acctCode);
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        sancAmtList = em.createNativeQuery("select ODLimit from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    } else {
                        sancAmtList = em.createNativeQuery("select Sanctionlimit from loan_appparameter where acno = '" + acNo + "'").getResultList();
                    }
                    Vector ele = (Vector) sancAmtList.get(0);
                    outstandbal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, toDt));
                    pojo.setAcNo(acNo);
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setOpningDt(vtr.get(2).toString().substring(6, 8) + "/" + vtr.get(2).toString().substring(4, 6) + "/" + vtr.get(2).toString().substring(0, 4));
                    pojo.setSanctionAmt(Double.parseDouble(ele.get(0).toString()));
                    pojo.setOutStandBal(Math.abs(outstandbal));
                    pojo.setActDesc(actDesc);
                    pojo.setActType(acctCode);
                    dataList.add(pojo);
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<NoticeToBorrowerPojo> getNoticeToBorrowerData(String brCode, String repType, String acno, String acType, String asOnDt, String day) throws ApplicationException {
        List<NoticeToBorrowerPojo> dataList = new ArrayList<NoticeToBorrowerPojo>();
        double outbal = 0d;

        List acList = new ArrayList();
        try {
            int isExceessEmi = common.isExceessEmi(asOnDt);
            String noticeDays = "";
            List noticeList = em.createNativeQuery("select code from cbs_parameterinfo where name in ('NOTICE_BORROW')").getResultList();
            if (noticeList.isEmpty()) {
                throw new ApplicationException("Please Check data  is exists for NOTICE_BORROW in cbs_parameterinfo Table");
            } else {
                Vector noticeVector = (Vector) noticeList.get(0);
                noticeDays = noticeVector.get(0).toString();
            }

            int days = Integer.parseInt(day) + Integer.parseInt(noticeDays);
            String d = "-" + days;
            List dtList = em.createNativeQuery("select DATE_FORMAT(date_add(" + asOnDt + ", INTERVAL " + d + " DAY),'%Y%m%d')").getResultList();
            Vector dVector = (Vector) dtList.get(0);
            String date = dVector.get(0).toString();
            String accountNatuer = "";
            String tableName = "";
            if (repType.equalsIgnoreCase("I")) {
                String acctCode = fts.getAccountCode(acno);
                accountNatuer = fts.getAcNatureByCode(acctCode);
                tableName = common.getTableName(accountNatuer);
            } else {
                accountNatuer = fts.getAcNatureByCode(acType);
                tableName = common.getTableName(accountNatuer);
            }

            if (repType.equalsIgnoreCase("I")) {
                acList = em.createNativeQuery("select acno,custName from accountmaster where acno in(select distinct(acno) from " + tableName + " where acno = '" + acno + "') and accStatus not in('9','11','12','13') and OpeningDt < '" + asOnDt + "'").getResultList();
            } else {
                if (brCode.equalsIgnoreCase("0A")) {
                    acList = em.createNativeQuery("select acno,custName from accountmaster where acno in(select distinct(acno) from " + tableName + " where substring(acno,3,2) = '" + acType + "') and accStatus not in('9','11','12','13') and OpeningDt < '" + asOnDt + "'").getResultList();
                } else {
                    acList = em.createNativeQuery("select acno,custName from accountmaster where acno in(select distinct(acno) from " + tableName + " where substring(acno,1,2) = '" + brCode + "' and substring(acno,3,2) = '" + acType + "') and accStatus not in('9','11','12','13') and OpeningDt < '" + asOnDt + "'").getResultList();
                }
            }

            if (!acList.isEmpty()) {
                for (int i = 0; i < acList.size(); i++) {
                    Vector vtr = (Vector) acList.get(i);
                    NoticeToBorrowerPojo pojo = new NoticeToBorrowerPojo();
                    String acNo = vtr.get(0).toString();
                    String custName = vtr.get(1).toString();
                    List oList1 = em.createNativeQuery("select concat(PerAddressLine1,' ',PerAddressLine2) from cbs_customer_master_detail where customerid in(select CustId from customerid where acno = '" + acNo + "')").getResultList();
                    String add = "";
                    if (!oList1.isEmpty()) {
                        Vector vtr1 = (Vector) oList1.get(0);
                        add = vtr1.get(0).toString();
                    }
                    outbal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, asOnDt));
                    if (accountNatuer.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List oList2 = em.createNativeQuery("select * from ca_recon where acno = '" + acNo + "' and  dt > '" + date + "' and dt < '" + asOnDt + "' and ty = 0").getResultList();
                        if (oList2.isEmpty()) {
                            if (outbal < 0 && outbal != 0) {
                                pojo.setAcNo(acNo);
                                pojo.setCustName(custName);
                                pojo.setAddress(add);
                                pojo.setOutStandingBal(new BigDecimal(Math.abs(outbal)));
                                pojo.setOverDue(getOverDueAmount(acNo, asOnDt));
                                pojo.setAcType(acNo.substring(2, 4));
                                pojo.setAcDesc(common.getAcctDecription(acNo.substring(2, 4)));
                                dataList.add(pojo);
                            }
                        }
                    } else if (accountNatuer.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || accountNatuer.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        BigDecimal ovrdueAmt = new BigDecimal("0");
                        List oList2 = em.createNativeQuery("select * from loan_recon where acno = '" + acNo + "' and  dt > '" + date + "' and dt < '" + asOnDt + "' and ty = 0").getResultList();
                        if (oList2.isEmpty()) {
                            List eList = em.createNativeQuery("select * from emidetails where acno = '" + acNo + "' and duedt between '" + date + "' and '" + asOnDt + "' and status = 'paid'").getResultList();
                            if (eList.isEmpty()) {
                                //List eList1 = em.createNativeQuery("").getResultList();
                                if (outbal < 0 && outbal != 0) {
                                    pojo.setAcNo(acNo);
                                    pojo.setCustName(custName);
                                    pojo.setAddress(add);
                                    pojo.setOutStandingBal(new BigDecimal(Math.abs(outbal)));
                                    List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("", acNo, 1, 200, asOnDt, common.getBrncodeByAcno(acNo), "", 0, isExceessEmi, null, 0);
                                    for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
                                        ovrdueAmt = pojo1.getAmountOverdue();
                                    }
                                    pojo.setOverDue(ovrdueAmt);
                                    pojo.setAcType(acNo.substring(2, 4));
                                    pojo.setAcDesc(common.getAcctDecription(acNo.substring(2, 4)));
                                    dataList.add(pojo);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getStdAssect() throws ApplicationException {
        try {
            return em.createNativeQuery("select code from cbs_parameterinfo where name = 'STD_ASSETS'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<RecoveryDetailPojo> getLoanRecoveryDetail(String custId, String date, String area, String reportType, String brCode) throws ApplicationException {
        List<RecoveryDetailPojo> dataList = new ArrayList<RecoveryDetailPojo>();
        String customerId = "", brName = "", brAdd = "", month = "", yy = "";
        List customerIdList = new ArrayList();
        double theftBal = 0d;
        try {

            List result = em.createNativeQuery("select code from cbs_parameterinfo where name in('TH','SL','OL','EL') order by code").getResultList();
            List result2 = em.createNativeQuery("select code from cbs_parameterinfo where name in('SL','OL','EL','IN','OD') order by code").getResultList();
            List brNameAndAddList = common.getBranchNameandAddress(brCode);

            if (reportType.equalsIgnoreCase("A")) {
                customerIdList = em.createNativeQuery("select distinct(CustId) from customerid  where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null)order by custid").getResultList();
            } else {
                //customerIdList = em.createNativeQuery("select distinct(CustId) from customerid  where CustId = '" + custId + "'").getResultList();
                customerIdList = em.createNativeQuery("select distinct(CustId) from customerid  where CustId =(select CustId from share_holder where Regfoliono = '" + custId + "')").getResultList();
            }
            if (!customerIdList.isEmpty()) {
                for (int i = 0; i < customerIdList.size(); i++) {
                    Vector cutVector = (Vector) customerIdList.get(i);
                    customerId = cutVector.get(0).toString();
                    List folioList = em.createNativeQuery("select Regfoliono from share_holder where custid = '" + customerId + "'").getResultList();
                    Vector folioVec = (Vector) folioList.get(0);
                    List mwfList = postalFacadeRemote.getPrimiumAmtDetails("WL", folioVec.get(0).toString());
                    Vector mwfv = (Vector) mwfList.get(0);
                    double mwfAmt = Double.parseDouble(mwfv.get(0).toString());
                    // For Personal Token Number
                    List ListPerContNoList = em.createNativeQuery("select ifnull(employeeNo,''),concat(ifnull(custname,''),' ',ifnull(middle_name,''),' ',ifnull(last_name,'')) as custname from cbs_customer_master_detail where customerid = '" + customerId + "'").getResultList();
                    Vector ele = (Vector) ListPerContNoList.get(0);
                    String pTokenNo = ele.get(0).toString();
                    String custName = ele.get(1).toString();
                    //End For Personal Token Number
                    double shareMoneyBal = common.getshareMoneyBal(folioVec.get(0).toString(), date);
                    //For Theft Bal
                    String theftAcno = common.getTheftAcno(customerId);
                    if (!theftAcno.equalsIgnoreCase("")) {
                        theftBal = common.getBalanceOnDate(theftAcno, date);
                    }
                    //End For Theft Bal
                    // Detail of Loan
                    for (int m = 0; m < result.size(); m++) {
                        Vector resVector = (Vector) result.get(m);
                        String acctCodeRes = resVector.get(0).toString();
                        List custidList = em.createNativeQuery("select a.acno from customerid a,accountmaster b where custid = '" + customerId + "' and a.acno = b.acno and Substring(a.acno,3,2)='" + acctCodeRes + "' and Substring(a.acno,3,2) <> 31 and accstatus <> 9 ").getResultList();
                        if (custidList.isEmpty()) {
                            RecoveryDetailPojo pojo = new RecoveryDetailPojo();

                            pojo.setLoanDetail("DETAILS OF LOAN");
                            pojo.setBrNameAdd(brName + " /" + brAdd);
                            pojo.setMmYYYY(month + " / " + yy);
                            pojo.setCustId(folioVec.get(0).toString());
                            pojo.setMwfAmt(mwfAmt);
                            pojo.setTktNo(pTokenNo);
                            pojo.setAcDesc(common.getAcctDecription(acctCodeRes));
                            pojo.setName(custName);
                            pojo.setBal(Math.abs(0));
                            pojo.setAmount("");
                            if (reportType.equalsIgnoreCase("A")) {
                                pojo.setCheckRono(area);
                            } else {
                                pojo.setCheckRono(common.getArea(folioVec.get(0).toString()));
                            }
                            pojo.setSmBal(shareMoneyBal);
                            pojo.setTfBal(theftBal);
                            dataList.add(pojo);

                        }
                        for (int j = 0; j < custidList.size(); j++) {
                            Vector vtr = (Vector) custidList.get(j);
                            RecoveryDetailPojo pojo = new RecoveryDetailPojo();
                            String acNo = vtr.get(0).toString();
                            String acctCode = fts.getAccountCode(acNo);
//                            List nameList = em.createNativeQuery("select custname from accountmaster where acno ='" + acNo + "'").getResultList();
//                            Vector vtr1 = (Vector) nameList.get(0);
                            List disList = em.createNativeQuery("select date_format(ifnull(max(a.DISBURSEMENTDT),'19000101'),'%d/%m/%Y'),ifnull(sum(a.AMTDISBURSED),0) from loandisbursement a, accountmaster b "
                                    + "where a.acno = b.acno and a.acno =  '" + acNo + "' and a.disbursementdt =(select date_format(ifnull(max(DISBURSEMENTDT),'19000101'),'%Y%m%d')from loandisbursement where acno = '" + acNo + "')").getResultList();
                            //List disList = em.createNativeQuery("select date_format(Sanctionlimitdt,'%d/%m/%Y'),Sanctionlimit from loan_appparameter where acno = '" + acNo + "'").getResultList();
                            String disbDate = "";
                            double disbAmt = 0d;
                            if (!disList.isEmpty()) {
                                Vector vtr2 = (Vector) disList.get(0);
                                disbDate = vtr2.get(0).toString();
                                disbAmt = Double.parseDouble(vtr2.get(1).toString());
                                if (disbAmt == 0) {
                                    disbAmt = common.getBalanceOnDate(acNo, ymdFormat.format(dmyFormat.parse(disbDate)));
                                }
                            }

                            brName = brNameAndAddList.get(0).toString();
                            brAdd = brNameAndAddList.get(1).toString();

                            month = CbsUtil.getMonthName(Integer.parseInt(date.substring(4, 6)));
                            yy = date.substring(0, 4);
                            double bal = common.getBalanceOnDate(acNo, date);

                            pojo.setLoanDetail("DETAILS OF LOAN");
                            pojo.setBrNameAdd(brName + " /" + brAdd);
                            pojo.setMmYYYY(month + " / " + yy);
                            pojo.setCustId(folioVec.get(0).toString());
                            pojo.setMwfAmt(mwfAmt);
                            pojo.setTktNo(pTokenNo);
                            pojo.setAcDesc(common.getAcctDecription(acctCode));
                            pojo.setName(custName);
                            pojo.setBal(Math.abs(bal));
                            if (disbDate.equalsIgnoreCase("01/01/1900")) {
                                if (bal != 0) {
                                    pojo.setAmount("[ " + loanInterestCalculationBean.getRoiLoanAccount(Math.abs(bal), date, acNo) + " % ]");
                                }
                            } else {
                                if (bal != 0) {
                                    pojo.setAmount(disbDate.equalsIgnoreCase("01/01/1900") ? "" : disbDate + " [ " + Math.abs(disbAmt) + " ] [" + Double.parseDouble(loanInterestCalculationBean.getRoiLoanAccount(Math.abs(bal), date, acNo)) + " %]");
                                }
                            }
                            if (reportType.equalsIgnoreCase("A")) {
                                pojo.setCheckRono(area);
                            } else {
                                pojo.setCheckRono(common.getArea(folioVec.get(0).toString()));
                            }
                            pojo.setSmBal(shareMoneyBal);
                            pojo.setTfBal(theftBal);
                            dataList.add(pojo);
                        }
                    }
                    //Detail of Recovery
                    double totalIntAmt = 0d, totalOverDue = 0d;
                    for (int n = 0; n < result2.size(); n++) {
                        Vector vtr2 = (Vector) result2.get(n);
                        String acctType = vtr2.get(0).toString();
//                        System.out.println(acctType);
                        List custidList1 = new ArrayList();
                        if (acctType.equalsIgnoreCase("INTEREST")) {
                            custidList1 = em.createNativeQuery("select 'INTEREST' INTEREST").getResultList();
                        } else if (acctType.equalsIgnoreCase("OVER DUE")) {
                            custidList1 = em.createNativeQuery("select 'OVER DUE' OVERDUE").getResultList();
                        } else {
                            custidList1 = em.createNativeQuery("select a.acno from customerid a,accountmaster b where custid = '" + customerId + "' and a.acno = b.acno and Substring(a.acno,3,2)='" + acctType + "' and Substring(a.acno,3,2) <> 31 and accstatus <> 9").getResultList();
                        }

                        if (custidList1.isEmpty()) {
                            //throw new ApplicationException("Please fill customer id only Loan or Recuring Account !");
                            RecoveryDetailPojo pojo1 = new RecoveryDetailPojo();
                            pojo1.setBrNameAdd(brName + " /" + brAdd);
                            pojo1.setMmYYYY(month + " / " + yy);
                            pojo1.setCustId(folioVec.get(0).toString());
                            pojo1.setMwfAmt(mwfAmt);
                            pojo1.setTktNo(pTokenNo);
                            pojo1.setLoanDetail("DETAILS OF RECOVERY");
                            pojo1.setAcDesc(common.getAcctDecription(acctType));
                            pojo1.setAmount("");
                            if (reportType.equalsIgnoreCase("A")) {
                                pojo1.setCheckRono(area);
                            } else {
                                pojo1.setCheckRono(common.getArea(folioVec.get(0).toString()));
                            }
                            pojo1.setSmBal(shareMoneyBal);
                            pojo1.setTfBal(theftBal);
                            pojo1.setBal(0);
                            dataList.add(pojo1);
                        }
                        // double totalIntAmt = 0d;
                        for (int j = 0; j < custidList1.size(); j++) {
                            Vector vtr = (Vector) custidList1.get(j);
                            RecoveryDetailPojo pojo1 = new RecoveryDetailPojo();
                            String acNo = vtr.get(0).toString();
                            double intAmt = 0d, prinAmt = 0d;
                            if (acNo.contains("0")) {
                                String acctCode = acNo.substring(2, 4);
                                String acctNature = fts.getAcNatureByCode(acctCode);
                                brName = brNameAndAddList.get(0).toString();
                                brAdd = brNameAndAddList.get(1).toString();

                                month = CbsUtil.getMonthName(Integer.parseInt(date.substring(4, 6)));
                                yy = date.substring(0, 4);
                                double outSt = 0d, overDueAmt = 0d;
                                if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                    //Start Demand / Principal Amount

//                                    List result1 = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acNo + "' and status='Unpaid' and duedt<='" + date + "'").getResultList();
//                                    Vector ele2 = (Vector) result1.get(0);
//                                    prinAmt = Double.parseDouble(ele2.get(0).toString());
//                                    if (prinAmt == 0) {
//                                        double outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, date));
//                                        if (Math.abs(outSt) > 0) {
//                                            result1 = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) as overDueAmt,"
//                                                    + "date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails "
//                                                    + "where acno='" + acNo + "' and status='Unpaid' and "
//                                                    + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
//                                                    + "where acno='" + acNo + "' and status='Unpaid') ").getResultList();
//                                        }
//                                        ele2 = (Vector) result1.get(0);
//                                        prinAmt = Double.parseDouble(ele2.get(0).toString());
//                                        if (prinAmt == 0) {
//                                            prinAmt = Math.abs(outSt);
//                                        }
//                                    }
                                    List result1;
                                    outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, date));

//                                    List emeCheckingList = em.createNativeQuery("select ifnull(min(SNO),0),ifnull(max(SNO),0) from emidetails where acno = '" + acNo + "' and STATUS = 'UNPAID' ").getResultList();
//                                    Vector emiVector = (Vector) emeCheckingList.get(0);
//                                    int minSeqNo = Integer.parseInt(emiVector.get(0).toString());
//                                    int maxSeqNo = Integer.parseInt(emiVector.get(1).toString());
//                                    if (minSeqNo != maxSeqNo) {
//                                        List ovedueList = em.createNativeQuery("select count(sno) from emidetails where acno = '" + acNo + "' and duedt <='" + date + "' and status = 'unpaid'").getResultList();
//                                        Vector ele1 = (Vector) ovedueList.get(0);
//                                        int overDuesno = Integer.parseInt(ele1.get(0).toString());
//                                        if (overDuesno > 1) {
//                                            result1 = em.createNativeQuery("select ifnull(sum(installamt),0)- ifnull(sum(excessamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acNo + "' and status='Unpaid' and duedt<='" + date + "'").getResultList();
//                                        } else {
//                                            result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acNo + "' and status='Unpaid' and duedt<='" + date + "'").getResultList();
//                                        }
//                                    } else {
//                                        result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acNo + "' and status='Unpaid' /*and duedt<='" + date + "'*/").getResultList();
//                                    }

                                    result1 = em.createNativeQuery("select ifnull(installamt,0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acNo + "' and status='Unpaid' and duedt<='" + date + "'").getResultList();
                                    if (result1.isEmpty()) {
                                        result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acNo + "' and status='Unpaid' /*and duedt<='" + date + "'*/").getResultList();
                                    }

                                    Vector ele2 = (Vector) result1.get(0);
                                    prinAmt = Double.parseDouble(ele2.get(0).toString());
                                    if (prinAmt == 0) {
                                        //double outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, tillDate));
                                        if (Math.abs(outSt) > 0) {
                                            result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,"
                                                    + "date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails "
                                                    + "where acno='" + acNo + "' and status='Unpaid' and "
                                                    + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                                                    + "where acno='" + acNo + "' and status='Unpaid') ").getResultList();
                                        }
                                        ele2 = (Vector) result1.get(0);
                                        prinAmt = Double.parseDouble(ele2.get(0).toString());

                                        if (prinAmt == 0) {
                                            prinAmt = Math.abs(outSt);
                                        } else {
                                            if (prinAmt > Math.abs(outSt)) {
                                                prinAmt = Math.abs(outSt);
                                            } else {
                                                prinAmt = prinAmt;
                                            }
                                        }

                                    } else {
                                        if (prinAmt > Math.abs(outSt)) {
                                            prinAmt = Math.abs(outSt);
                                        } else {
                                            prinAmt = prinAmt;
                                        }
                                    }
                                    //End Demand / Principal Amount
                                    //Start Interest Amount code
                                    String fromDt = loanInterestCalculationBean.allFromDt(acctCode, brCode, "f");
                                    String toDt = loanInterestCalculationBean.allFromDt(acctCode, brCode, "t");
                                    LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(fromDt, toDt, acNo, brCode);
                                    intAmt = it.getTotalInt();
                                    if (!(intAmt == 0)) {
                                        intAmt = intAmt * -1;
                                    }
                                }
                                totalIntAmt = totalIntAmt + intAmt;
                                //For Over Due Amount new code
                                if (Math.abs(outSt) > 0) {
                                    List<OverDueListPojo> list = ddsRemote.getOverDueListData(acNo.substring(0, 2), acNo.substring(2, 4), date, acNo);
                                    if (!list.isEmpty()) {
                                        overDueAmt = list.get(0).getOveDue().doubleValue();
                                    }
                                    totalOverDue = totalOverDue + overDueAmt;
                                }
                                //END For Over Due Amount new code

                                pojo1.setBrNameAdd(brName + " /" + brAdd);
                                pojo1.setMmYYYY(month + " / " + yy);
                                pojo1.setCustId(folioVec.get(0).toString());
                                pojo1.setMwfAmt(mwfAmt);
                                pojo1.setTktNo(pTokenNo);
                                pojo1.setLoanDetail("DETAILS OF RECOVERY");
                                pojo1.setAcDesc(common.getAcctDecription(acctCode));
                                pojo1.setAmount("");
                                if (reportType.equalsIgnoreCase("A")) {
                                    pojo1.setCheckRono(area);
                                } else {
                                    pojo1.setCheckRono(common.getArea(folioVec.get(0).toString()));
                                }
                                pojo1.setSmBal(shareMoneyBal);
                                pojo1.setTfBal(theftBal);
                                pojo1.setBal(prinAmt);

                            } else if (acNo.equalsIgnoreCase("INTEREST")) {
                                pojo1.setBrNameAdd(brName + " /" + brAdd);
                                pojo1.setMmYYYY(month + " / " + yy);
                                pojo1.setCustId(folioVec.get(0).toString());
                                pojo1.setMwfAmt(mwfAmt);
                                pojo1.setTktNo(pTokenNo);
                                pojo1.setLoanDetail("DETAILS OF RECOVERY");
                                pojo1.setAcDesc(acNo);
                                pojo1.setBal(totalIntAmt);
                                pojo1.setAmount("");
                                if (reportType.equalsIgnoreCase("A")) {
                                    pojo1.setCheckRono(area);
                                } else {
                                    pojo1.setCheckRono(common.getArea(folioVec.get(0).toString()));
                                }
                                pojo1.setSmBal(shareMoneyBal);
                                pojo1.setTfBal(theftBal);
                            } else {
                                pojo1.setBrNameAdd(brName + " /" + brAdd);
                                pojo1.setMmYYYY(month + " / " + yy);
                                pojo1.setCustId(folioVec.get(0).toString());
                                pojo1.setMwfAmt(mwfAmt);
                                pojo1.setTktNo(pTokenNo);
                                pojo1.setLoanDetail("DETAILS OF RECOVERY");
                                pojo1.setAcDesc(acNo);
                                pojo1.setBal(totalOverDue);
                                pojo1.setAmount("");
                                if (reportType.equalsIgnoreCase("A")) {
                                    pojo1.setCheckRono(area);
                                } else {
                                    pojo1.setCheckRono(common.getArea(folioVec.get(0).toString()));
                                }
                                pojo1.setSmBal(shareMoneyBal);
                                pojo1.setTfBal(theftBal);
                            }
                            dataList.add(pojo1);
                        }
                    }
                    //For Theft Fund and Rd Installment
                    // List custIdList22 = em.createNativeQuery("select acno from customerid where custid = '" + customerId + "' and Substring(acno,3,2) in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "') and accttype = 'Tf')order by acno desc").getResultList();
                    List custIdList2 = em.createNativeQuery("select acno from accountmaster  where acno in(select acno from customerid where custid = '" + customerId + "')and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + date + "') and curbrcode = '" + brCode + "' and accttype in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "')and accttype = 'Tf')").getResultList();

                    if (!custIdList2.isEmpty()) {
                        for (int m = 0; m < custIdList2.size(); m++) {
                            Vector vtr = (Vector) custIdList2.get(m);
                            RecoveryDetailPojo pojo1 = new RecoveryDetailPojo();
                            String acNo = vtr.get(0).toString();
                            String acctCode = fts.getAccountCode(acNo);
                            double recoverAmt = 0d, intAmt = 0d;
                            List RecList;
                            RecList = em.createNativeQuery("select RdInstal as overDueAmt from accountmaster where acno  = '" + acNo + "'").getResultList();
                            if (!RecList.isEmpty()) {
                                Vector vtr1 = (Vector) RecList.get(0);
                                recoverAmt = Double.parseDouble(vtr1.get(0).toString());
                            }
                            pojo1.setBrNameAdd(brName + " /" + brAdd);
                            pojo1.setMmYYYY(month + " / " + yy);
                            pojo1.setCustId(folioVec.get(0).toString());
                            pojo1.setMwfAmt(mwfAmt);
                            pojo1.setTktNo(pTokenNo);
                            pojo1.setTfBal(theftBal);
                            pojo1.setLoanDetail("DETAILS OF RECOVERY");
                            pojo1.setAcDesc(common.getAcctDecription(acctCode));
                            pojo1.setAmount("");
                            if (reportType.equalsIgnoreCase("A")) {
                                pojo1.setCheckRono(area);
                            } else {
                                pojo1.setCheckRono(common.getArea(folioVec.get(0).toString()));
                            }
                            pojo1.setSmBal(shareMoneyBal);
                            pojo1.setBal(recoverAmt);
                            dataList.add(pojo1);
                        }
                    } else {
                        RecoveryDetailPojo pojo1 = new RecoveryDetailPojo();
                        pojo1.setBrNameAdd(brName + " /" + brAdd);
                        pojo1.setMmYYYY(month + " / " + yy);
                        pojo1.setCustId(folioVec.get(0).toString());
                        pojo1.setMwfAmt(mwfAmt);
                        pojo1.setTktNo(pTokenNo);
                        pojo1.setTfBal(theftBal);
                        pojo1.setLoanDetail("DETAILS OF RECOVERY");
                        pojo1.setAcDesc(common.getAcctDecription("28"));
                        pojo1.setAmount("");
                        if (reportType.equalsIgnoreCase("A")) {
                            pojo1.setCheckRono(area);
                        } else {
                            pojo1.setCheckRono(common.getArea(folioVec.get(0).toString()));
                        }
                        pojo1.setSmBal(shareMoneyBal);
                        pojo1.setBal(0);
                        dataList.add(pojo1);
                    }
                    //End New Code
                    //For  Rd Installment
//                    List custIdList3 = em.createNativeQuery("select acno from customerid where custid = '" + customerId + "' and Substring(acno,3,2) "
//                            + "in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "'))order by acno desc ").getResultList();
                    List custIdList3 = em.createNativeQuery("select acno from accountmaster  where acno in(select acno from customerid where custid = '" + customerId + "')and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + date + "') and curbrcode = '" + brCode + "' and accttype in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.RECURRING_AC + "'))").getResultList();
                    if (!custIdList3.isEmpty()) {
                        double recoverAmt = 0d, totalRecoverAmt = 0d;
                        for (int m = 0; m < custIdList3.size(); m++) {
                            Vector vtr = (Vector) custIdList3.get(m);
                            String acNo = vtr.get(0).toString();
                            List RecList = em.createNativeQuery("select ifnull((sum(installamt)),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment where acno='" + acNo + "' and status='Unpaid' and duedt<='" + date + "'").getResultList();
                            if (!RecList.isEmpty()) {
                                Vector vtr1 = (Vector) RecList.get(0);
                                recoverAmt = Double.parseDouble(vtr1.get(0).toString());
                                if (recoverAmt == 0) {
                                    List rdInstallmentList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0),date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment "
                                            + "where acno='" + acNo + "' and sno=(select min(SNO) from rd_installment where acno = '" + acNo + "' and STATUS = 'Unpaid')").getResultList();
                                    Vector rdvtr = (Vector) rdInstallmentList.get(0);
                                    recoverAmt = Double.parseDouble(rdvtr.get(0).toString());
                                }
                            }
                            totalRecoverAmt = totalRecoverAmt + recoverAmt;
                        }
                        RecoveryDetailPojo pojo1 = new RecoveryDetailPojo();
                        pojo1.setBrNameAdd(brName + " /" + brAdd);
                        pojo1.setMmYYYY(month + " / " + yy);
                        pojo1.setCustId(folioVec.get(0).toString());
                        pojo1.setMwfAmt(mwfAmt);
                        pojo1.setTktNo(pTokenNo);
                        pojo1.setTfBal(theftBal);
                        pojo1.setLoanDetail("DETAILS OF RECOVERY");
                        // pojo1.setAcDesc(common.getAcctDecription(acctCode));
                        pojo1.setAcDesc("R D");
                        pojo1.setAmount("");
                        if (reportType.equalsIgnoreCase("A")) {
                            pojo1.setCheckRono(area);
                        } else {
                            pojo1.setCheckRono(common.getArea(folioVec.get(0).toString()));
                        }
                        pojo1.setSmBal(shareMoneyBal);
                        pojo1.setBal(totalRecoverAmt);
                        dataList.add(pojo1);

                    } else {
                        RecoveryDetailPojo pojo1 = new RecoveryDetailPojo();
                        pojo1.setBrNameAdd(brName + " /" + brAdd);
                        pojo1.setMmYYYY(month + " / " + yy);
                        pojo1.setCustId(folioVec.get(0).toString());
                        pojo1.setMwfAmt(mwfAmt);
                        pojo1.setTktNo(pTokenNo);
                        pojo1.setTfBal(theftBal);
                        pojo1.setLoanDetail("DETAILS OF RECOVERY");
                        pojo1.setAcDesc("R D");
                        pojo1.setAmount("");
                        if (reportType.equalsIgnoreCase("A")) {
                            pojo1.setCheckRono(area);
                        } else {
                            pojo1.setCheckRono(common.getArea(folioVec.get(0).toString()));
                        }
                        pojo1.setSmBal(shareMoneyBal);
                        pojo1.setBal(0);
                        dataList.add(pojo1);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List schemeCodeCombo(String acctype) throws ApplicationException {
        List scheme = null;
        try {
            if (acctype.equalsIgnoreCase("ALL")) {
                scheme = em.createNativeQuery("select scheme_code from cbs_scheme_general_scheme_parameter_master").getResultList();
            } else {
                scheme = em.createNativeQuery("select scheme_code from cbs_scheme_general_scheme_parameter_master where scheme_type = '" + acctype + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public String schemeCodeDesc(String schemeCode) throws ApplicationException {
        List scheme = null;
        try {
            if (schemeCode.equalsIgnoreCase("ALL")) {
                return "ALL";
            } else {
                scheme = em.createNativeQuery("select scheme_description from cbs_scheme_general_scheme_parameter_master where scheme_code = '" + schemeCode + "'").getResultList();
                Vector vtr2 = (Vector) scheme.get(0);
                return vtr2.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<LoanSchemeWiseDetailPojo> getLoanSchemeDetail(String brCode, String asOnDt, String acttype, String schemeCode) throws ApplicationException {
        List<LoanSchemeWiseDetailPojo> dataList = new ArrayList<LoanSchemeWiseDetailPojo>();
        List acList = new ArrayList();
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                if (acttype.equalsIgnoreCase("ALL")) {
                    if (schemeCode.equalsIgnoreCase("ALL")) {
                        acList = em.createNativeQuery("select aa.acno,aa.custname,aa.openingDt,a.SCHEME_CODE,a.INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,a.RATE_CODE,a.PEGG_FREQ,a.LOAN_PD_MONTH,ifnull(a.MORATORIUM_PD,0) from (select aa.acno,aa.custname,aa.openingDt from accountmaster aa where aa.acno in(select acno from cbs_loan_acc_mast_sec where substring(acno,3,2) in(select Acctcode From accounttypemaster where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "') and acctcode <> '" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "')) and (aa.closingdate is null or aa.closingdate ='' or aa.closingdate >= '" + asOnDt + "') and aa.openingDt < '" + asOnDt + "' order by aa.acno ) aa, cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = aa.acno and b.EFF_FRM_DT <= '" + asOnDt + "' and a.acno = b.acno and b.ac_int_ver_no in (select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = aa.acno)").getResultList();
                    } else {
                        acList = em.createNativeQuery("select aa.acno,aa.custname,aa.openingDt,a.SCHEME_CODE,a.INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,a.RATE_CODE,a.PEGG_FREQ,a.LOAN_PD_MONTH,ifnull(a.MORATORIUM_PD,0) from (select aa.acno,aa.custname,aa.openingDt from accountmaster aa where aa.acno in(select acno from cbs_loan_acc_mast_sec where SCHEME_CODE = '" + schemeCode + "') and (aa.closingdate is null or aa.closingdate ='' or aa.closingdate >= '" + asOnDt + "') and aa.openingDt < '" + asOnDt + "' order by aa.acno ) aa , cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = aa.acno and b.EFF_FRM_DT <= '" + asOnDt + "' and a.acno = b.acno and b.ac_int_ver_no in (select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = aa.acno) ").getResultList();
                    }
                } else {
                    if (schemeCode.equalsIgnoreCase("ALL")) {
                        acList = em.createNativeQuery("select aa.acno,aa.custname,aa.openingDt,a.SCHEME_CODE,a.INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,a.RATE_CODE,a.PEGG_FREQ,a.LOAN_PD_MONTH,ifnull(a.MORATORIUM_PD,0) from (select aa.acno,aa.custname,aa.openingDt from accountmaster aa where aa.acno in(select acno from cbs_loan_acc_mast_sec where substring(acno,3,2) = '" + acttype + "') and (aa.closingdate is null or aa.closingdate ='' or aa.closingdate >= '" + asOnDt + "') and aa.openingDt < '" + asOnDt + "' order by aa.acno ) aa, cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = aa.acno and b.EFF_FRM_DT <= '" + asOnDt + "' and a.acno = b.acno and b.ac_int_ver_no in (select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = aa.acno) ").getResultList();
                    } else {
                        acList = em.createNativeQuery("select aa.acno,aa.custname,aa.openingDt,a.SCHEME_CODE,a.INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,a.RATE_CODE,a.PEGG_FREQ,a.LOAN_PD_MONTH,ifnull(a.MORATORIUM_PD,0) from (select aa.acno,aa.custname,aa.openingDt from accountmaster aa where aa.acno in (select acno from cbs_loan_acc_mast_sec where substring(acno,3,2) = '" + acttype + "' and SCHEME_CODE = '" + schemeCode + "') and (aa.closingdate is null or aa.closingdate ='' or aa.closingdate >= '" + asOnDt + "') and aa.openingDt < '" + asOnDt + "' order by aa.acno ) aa , cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = aa.acno and b.EFF_FRM_DT <= '" + asOnDt + "' and a.acno = b.acno and b.ac_int_ver_no in (select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = aa.acno) ").getResultList();
                    }
                }
            } else {
                if (acttype.equalsIgnoreCase("ALL")) {
                    if (schemeCode.equalsIgnoreCase("ALL")) {
                        acList = em.createNativeQuery("select aa.acno,aa.custname,aa.openingDt,a.SCHEME_CODE,a.INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,a.RATE_CODE,a.PEGG_FREQ,a.LOAN_PD_MONTH,ifnull(a.MORATORIUM_PD,0) from (select aa.acno,aa.custname,aa.openingDt from accountmaster aa where aa.acno in(select acno from cbs_loan_acc_mast_sec where substring(acno,1,2) = '" + brCode + "' and substring(acno,3,2) in(select Acctcode From accounttypemaster where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "') and acctcode <> '" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "')) and (aa.closingdate is null or aa.closingdate ='' or aa.closingdate >= '" + asOnDt + "') and aa.openingDt < '" + asOnDt + "' order by aa.acno) aa, cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = aa.acno and b.EFF_FRM_DT <= '" + asOnDt + "' and a.acno = b.acno and b.ac_int_ver_no in (select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = aa.acno) ").getResultList();
                    } else {
                        acList = em.createNativeQuery("select aa.acno,aa.custname,aa.openingDt,a.SCHEME_CODE,a.INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,a.RATE_CODE,a.PEGG_FREQ,a.LOAN_PD_MONTH,ifnull(a.MORATORIUM_PD,0) from (select aa.acno,aa.custname,aa.openingDt from accountmaster aa where aa.acno in(select acno from cbs_loan_acc_mast_sec where substring(acno,1,2) = '" + brCode + "' and SCHEME_CODE = '" + schemeCode + "')and (aa.closingdate is null or aa.closingdate ='' or aa.closingdate >= '" + asOnDt + "') and aa.openingDt < '" + asOnDt + "' order by aa.acno )aa , cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = aa.acno and b.EFF_FRM_DT <= '" + asOnDt + "' and a.acno = b.acno and b.ac_int_ver_no in (select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = aa.acno) ").getResultList();
                    }
                } else {
                    if (schemeCode.equalsIgnoreCase("ALL")) {
                        acList = em.createNativeQuery("select aa.acno,aa.custname,aa.openingDt,a.SCHEME_CODE,a.INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,a.RATE_CODE,a.PEGG_FREQ,a.LOAN_PD_MONTH,ifnull(a.MORATORIUM_PD,0) from (select aa.acno,aa.custname,aa.openingDt from accountmaster aa where aa.acno in (select acno from cbs_loan_acc_mast_sec where substring(acno,1,2) = '" + brCode + "'  and substring(acno,3,2) = '" + acttype + "') and (aa.closingdate is null or aa.closingdate ='' or aa.closingdate >= '" + asOnDt + "') and aa.openingDt < '" + asOnDt + "' order by aa.acno )aa , cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = aa.acno and b.EFF_FRM_DT <= '" + asOnDt + "' and a.acno = b.acno and b.ac_int_ver_no in (select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = aa.acno) ").getResultList();
                    } else {
                        acList = em.createNativeQuery("select aa.acno,aa.custname,aa.openingDt,a.SCHEME_CODE,a.INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,a.RATE_CODE,a.PEGG_FREQ,a.LOAN_PD_MONTH,ifnull(a.MORATORIUM_PD,0) from (select aa.acno,aa.custname,aa.openingDt from accountmaster aa where aa.acno in(select acno from cbs_loan_acc_mast_sec where substring(acno,1,2) = '" + brCode + "'  and substring(acno,3,2) = '" + acttype + "' and SCHEME_CODE = '" + schemeCode + "') and (aa.closingdate is null or aa.closingdate ='' or aa.closingdate >= '" + asOnDt + "') and aa.openingDt < '" + asOnDt + "' order by aa.acno) aa, cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = aa.acno and b.EFF_FRM_DT <= '" + asOnDt + "' and a.acno = b.acno and b.ac_int_ver_no in (select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = aa.acno) ").getResultList();
                    }
                }
            }
            if (!acList.isEmpty()) {
                for (int i = 0; i < acList.size(); i++) {
                    Vector vtr = (Vector) acList.get(i);
                    LoanSchemeWiseDetailPojo pojo = new LoanSchemeWiseDetailPojo();
                    String acno = vtr.get(0).toString();
                    String name = vtr.get(1).toString();
                    String openingDt = vtr.get(2).toString();

                    // List acDetailList = em.createNativeQuery("select SCHEME_CODE,INTEREST_TABLE_CODE,ACC_PREF_DR,ACC_PREF_CR,RATE_CODE,PEGG_FREQ,LOAN_PD_MONTH from cbs_loan_acc_mast_sec where acno = '" + acno + "'").getResultList(); 
//                    List acDetailList = em.createNativeQuery("select SCHEME_CODE,INTEREST_TABLE_CODE,b.AC_PREF_DR,b.ACC_PREF_CR,RATE_CODE,PEGG_FREQ,LOAN_PD_MONTH,ifnull(MORATORIUM_PD,0) "
//                            + "from cbs_loan_acc_mast_sec a,cbs_acc_int_rate_details b where a.acno = '" + acno + "'and b.EFF_FRM_DT <= '" + asOnDt + "' "
//                            + "and a.acno = b.acno and b.ac_int_ver_no in(select max(ac_int_ver_no) from cbs_acc_int_rate_details where acno = '" + acno + "')").getResultList();
//
//                    Vector vtr1 = (Vector) acDetailList.get(0);
                    String schemCode = vtr.get(3).toString();
                    String intTableCode = vtr.get(4).toString();
                    double accPrefDr = Double.parseDouble(vtr.get(5).toString());
                    double accPrefCr = Double.parseDouble(vtr.get(6).toString());
                    String rateCode = vtr.get(7).toString();
                    String paggFreq = vtr.get(8) == null ? "" : vtr.get(8).toString();
                    String loanPdMonth = vtr.get(9) == null ? "" : vtr.get(9).toString();
                    String moratoriumPeriod = vtr.get(10).toString();
                    String acctCode = fts.getAccountCode(acno);
                    String acNature = fts.getAcNatureByCode(acctCode);
                    List sancAmtList = null;
                    double appRoi = 0d, acRoi = 0d, outstandbal = 0d;
                    String schemeRoi = null;
                    if (!acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {

                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            sancAmtList = em.createNativeQuery("select ODLimit from loan_appparameter where acno = '" + acno + "'").getResultList();
                        } else {
                            sancAmtList = em.createNativeQuery("select Sanctionlimit from loan_appparameter where acno = '" + acno + "'").getResultList();
                        }

                        Vector ele = (Vector) sancAmtList.get(0);
                        schemeRoi = accountRemote.GetROIForLoanDLAcOpen(schemCode, Float.parseFloat(ele.get(0).toString()), asOnDt);

                        //String roi1 = acOpenFacadeRemote.getROI(vtr.get(0).toString(), outStandBal, toDate);
//                        outstandbal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, asOnDt)); 
//                        This change was done as per the requirement from Indraprastha Bank (there was one account in which NEFT credit was done but that was after Dayend.After Discussion with Saurabh And Alok Sir this change was done.
                        outstandbal = common.getBalanceOnDate(acno, asOnDt);
                        //double acRoi = 0d;
                        if (rateCode.equalsIgnoreCase("Fl")) {
                            acRoi = Double.parseDouble(schemeRoi);
                        } else {
                            acRoi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Math.abs(outstandbal), asOnDt, acno));
                        }
                        // double appRoi = 0d;
                        String fixDt = "";
                        if (rateCode.equalsIgnoreCase("Fi")) {
                            fixDt = CbsUtil.monthAdd(openingDt, Integer.parseInt(paggFreq));
                            if (ymdFormat.parse(fixDt).after(ymdFormat.parse(asOnDt))) {
                                appRoi = acRoi;
                            } else {
                                appRoi = acRoi + (accPrefDr - accPrefCr);
                            }
                        } else {
                            appRoi = acRoi + (accPrefDr - accPrefCr);
                        }

                    } else {

                        outstandbal = common.getBalanceOnDate(acno, asOnDt);
                        String roi = acOpenFacadeRemote.getROI(schemCode, outstandbal, asOnDt);
                        schemeRoi = roi;
                        acRoi = Double.parseDouble(roi);
                        appRoi = Double.parseDouble(roi);
                    }
                    //  double appRoi = acRoi + (accPrefDr - accPrefCr);
                    pojo.setAcNo(acno);
                    pojo.setActDesc(common.getAcctDecription(acctCode));
                    pojo.setCustName(name);
                    pojo.setOpeningDt(dmyFormat.format(ymdFormat.parse(openingDt)));
//                    if (rateCode.equalsIgnoreCase("Fi")) {
//                        pojo.setAcPrfCr(0);
//                        pojo.setAcPrfDr(0);
//                    } else {
//                        pojo.setAcPrfCr(accPrefCr);
//                        pojo.setAcPrfDr(accPrefDr);
//                    }
                    pojo.setAcPrfCr(accPrefCr);
                    pojo.setAcPrfDr(accPrefDr);

                    pojo.setSchemeRoi(Double.parseDouble(schemeRoi));

                    pojo.setAcRoi(acRoi);
                    pojo.setApplicableRoi(appRoi);
                    pojo.setPaggingFreq(String.valueOf(paggFreq));
                    pojo.setScmCodeIntTableCode(intTableCode);
                    pojo.setLoanDuration(loanPdMonth);
                    pojo.setAcType(acctCode);
                    if (rateCode.equalsIgnoreCase("Ab")) {
                        pojo.setFixedFloating("Absolute");
                    } else if (rateCode.equalsIgnoreCase("Fi")) {
                        pojo.setFixedFloating("Fixed");
                    } else if (rateCode.equalsIgnoreCase("Fl")) {
                        pojo.setFixedFloating("Floating");
                    }
                    if (rateCode.equalsIgnoreCase("Fi")) {
                        pojo.setFixedTillDt(dmyFormat.format(ymdFormat.parse(CbsUtil.monthAdd(openingDt, Integer.parseInt(paggFreq)))));
                    } else {
                        pojo.setFixedTillDt("");
                    }
                    pojo.setOutBal(new BigDecimal(outstandbal));
                    pojo.setMoratoriumPd(moratoriumPeriod);
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<LoanEmiDetailPojo> getLoanEmiDetail(String brCode, String acNature, String acType, String asOnDt) throws ApplicationException {
        List<LoanEmiDetailPojo> dataList = new ArrayList<LoanEmiDetailPojo>();
        List result1 = new ArrayList();
        try {
            if (acType.equalsIgnoreCase("ALL")) {
                result1 = em.createNativeQuery("select acno,custname from accountmaster where CurBrCode = '" + brCode + "' and accttype in(select acctCode from accounttypemaster where acctnature = '" + acNature + "')and(ClosingDate is null or ClosingDate = '' or ClosingDate > '" + asOnDt + "') and Openingdt < '" + asOnDt + "'").getResultList();
            } else {
                result1 = em.createNativeQuery("select acno,custname from accountmaster where CurBrCode = '" + brCode + "' and accttype = '" + acType + "' and(ClosingDate is null or ClosingDate = '' or ClosingDate > '" + asOnDt + "') and Openingdt < '" + asOnDt + "'").getResultList();
            }
            if (!result1.isEmpty()) {
                for (int i = 0; i < result1.size(); i++) {
                    Vector vtr = (Vector) result1.get(i);
                    LoanEmiDetailPojo pojo = new LoanEmiDetailPojo();
                    String acNo = vtr.get(0).toString();
                    String custName = vtr.get(1).toString();
                    double installAmt = 0d;
                    String dueDt = "", noOfemi = "";

                    List emiList = em.createNativeQuery("select distinct ifnull(INSTALLAMT,0),ifnull(duedt,''),count(INSTALLAMT) from emidetails where acno = '" + acNo + "' and duedt >= '" + asOnDt + "'").getResultList();
                    if (!emiList.isEmpty()) {
                        Vector element = (Vector) emiList.get(0);
                        installAmt = Double.parseDouble(element.get(0).toString());
                        dueDt = element.get(1).toString();
                        noOfemi = element.get(2).toString();
                    }
                    String loanPd = "";
                    List loanPeriodList = em.createNativeQuery("select loan_pd_month from cbs_loan_acc_mast_sec where acno = '" + acNo + "'").getResultList();
                    if (!loanPeriodList.isEmpty()) {
                        Vector ele = (Vector) loanPeriodList.get(0);
                        loanPd = ele.get(0).toString();
                    }

                    String acctCode = fts.getAccountCode(acNo);
                    String acTypeDesc = common.getAcctDecription(acctCode);
                    double outstandbal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, asOnDt));
                    pojo.setAcNo(acNo);
                    pojo.setName(custName);
                    pojo.setNoOfemiDue(noOfemi);
                    if (!dueDt.equalsIgnoreCase("")) {
                        pojo.setDuedtOfemi(dmyFormat.format(ymdFormat.parse(dueDt)));
                        pojo.setStatus("Emi");
                    } else {
                        pojo.setDuedtOfemi("Don't have emi");
                        pojo.setStatus("Not Emi");
                    }
                    pojo.setEmiAmt(installAmt);
                    pojo.setOutstandBal(Double.parseDouble(nft.format(Math.abs(outstandbal))));
                    pojo.setLoanPd(loanPd);
                    pojo.setAccountType(acType);
                    pojo.setAcTypeDesc(acTypeDesc);
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<LoanPrincipalInterestPojo> getLoanPrincipalInterest(String brCode, String acType, String frDt, String asOnDt) throws ApplicationException {
        List<LoanPrincipalInterestPojo> dataList = new ArrayList<LoanPrincipalInterestPojo>();
        List result = new ArrayList();
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select a.acno,custname,date_format(openingdt,'%d/%m/%Y'),LOAN_PD_MONTH,date_format(DATE_ADD(openingdt, INTERVAL LOAN_PD_MONTH month),'%d/%m/%Y'), ifnull(a.odlimit,0) from accountmaster a,cbs_loan_acc_mast_sec b where  Accttype = '" + acType + "' and a.acno=b.acno and (ClosingDate is null or ClosingDate = '' or ClosingDate > '" + asOnDt + "') and Openingdt < '" + asOnDt + "' order by a.acno").getResultList();
            } else {
                result = em.createNativeQuery("select a.acno,custname,date_format(openingdt,'%d/%m/%Y'),LOAN_PD_MONTH,date_format(DATE_ADD(openingdt, INTERVAL LOAN_PD_MONTH month),'%d/%m/%Y'), ifnull(a.odlimit,0) from accountmaster a,cbs_loan_acc_mast_sec b where  Accttype = '" + acType + "' and a.acno=b.acno and curbrcode = '" + brCode + "' and (ClosingDate is null or ClosingDate = '' or ClosingDate > '" + asOnDt + "') and Openingdt < '" + asOnDt + "' order by a.acno").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    LoanPrincipalInterestPojo pojo = new LoanPrincipalInterestPojo();
                    String acno = vtr.get(0).toString();
                    String name = vtr.get(1).toString();
                    String opDt = vtr.get(2).toString();
                    String period = vtr.get(3).toString();
                    String expiryDt = vtr.get(4).toString();
                    double sanctionAmt = Double.parseDouble(vtr.get(5).toString());
                    double recoverPriAmt = 0d, recoverIntAmt = 0d, recoverChgAmt = 0d;

                    double outstandbal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, asOnDt));

                    //List resList = em.createNativeQuery("select ifnull(sum(prinamt),0),ifnull(sum(interestamt),0),ifnull(sum(installamt),0) from emidetails where acno = '" + acno + "' and duedt <= '" + asOnDt + "'").getResultList();
                    //List resList = em.createNativeQuery("select ifnull(sum(amtdisbursed),0) from loandisbursement  where acno = '" + acno + "'").getResultList();
                    List resList = em.createNativeQuery("select ifnull(sum(dramt),0)as bal  from loan_recon where acno = '" + acno + "' and dt between'" + frDt + "' and '" + asOnDt + "' and trandesc  in('2','6')").getResultList();
                    Vector amtVector = (Vector) resList.get(0);
                    double principalAmt = Double.parseDouble(amtVector.get(0).toString());

                    //  List resList1 = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where acno = '" + acno + "' and dt <= '" + asOnDt + "' and trandesc  in('3','4'); ").getResultList();
                    List resList1 = em.createNativeQuery("select sum(bal) from (select ifnull(sum(dramt),0) as bal from npa_recon where acno = '" + acno + "' and dt between'" + frDt + "' and '" + asOnDt + "' and trandesc  in('3','4') "
                            + "union "
                            + "select ifnull(sum(dramt),0)as bal  from loan_recon where acno = '" + acno + "' and dt between'" + frDt + "' and '" + asOnDt + "' and trandesc  in('3','4'))a").getResultList();
                    Vector intVector = (Vector) resList1.get(0);
                    double interestAmt = Double.parseDouble(intVector.get(0).toString());

                    // List depositList = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno = '" + acno + "' and dt <= '" + asOnDt + "'").getResultList();
                    List depositList = em.createNativeQuery("select sum(bal) from (select ifnull(sum(cramt),0)as bal from loan_recon where acno = '" + acno + "' and dt between'" + frDt + "' and '" + asOnDt + "' "
                            + "union "
                            + "select ifnull(sum(cramt),0)as bal from npa_recon where acno = '" + acno + "' and dt between'" + frDt + "' and '" + asOnDt + "')a").getResultList();
                    Vector depVector = (Vector) depositList.get(0);
                    double depositAmt = Double.parseDouble(depVector.get(0).toString());

                    //  List chargeamtList = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where trandesc not in('6','3','4') and acno='" + acno + "' and dt < '" + asOnDt + "'").getResultList();
                    List chargeamtList = em.createNativeQuery("select sum(bal) from (select ifnull(sum(dramt),0) as bal from loan_recon where trandesc not in('2','6','3','4') and acno='" + acno + "' and dt between'" + frDt + "' and '" + asOnDt + "' "
                            + "union "
                            + "select ifnull(sum(dramt),0) as bal from npa_recon where trandesc not in('2','6','3','4') and acno='" + acno + "' and dt between'" + frDt + "' and '" + asOnDt + "') a").getResultList();
                    Vector chargVector = (Vector) chargeamtList.get(0);
                    double chargeAmt = Double.parseDouble(chargVector.get(0).toString());

                    List npaList = em.createNativeQuery("select ifnull(sum(a.dramt),0)-ifnull(sum(a.cramt),0)as bal"
                            + ",(select ifnull(sum(INSTALLAMT),0) from emidetails where acno = '" + acno + "' and duedt<='" + asOnDt + "') as emiDue  from npa_recon a where a.acno = '" + acno + "' and a.dt <='" + asOnDt + "' ").getResultList();
                    Vector npaVector = (Vector) npaList.get(0);
                    double npaAmt = Double.parseDouble(npaVector.get(0).toString());
                    System.out.println("Acno:" + acno);
                    double emiDueAmt = Double.parseDouble(npaVector.get(1).toString());
//                    System.out.print("Acno --> " + acno);
                    pojo.setAcNo(acno);
                    pojo.setCustName(name);
                    pojo.setOpningDt(opDt);
                    pojo.setPeriod(period);
                    pojo.setExpiryDt(expiryDt);
                    pojo.setSancAmt(sanctionAmt);
                    pojo.setEmiDueAmt(emiDueAmt);
                    pojo.setPrincipalAmt(principalAmt);
                    pojo.setInterestAmt(interestAmt);
                    pojo.setChargeAmt(chargeAmt);
                    if (depositAmt == 0) {
                        pojo.setRecoverChargeAmt(0);
                        pojo.setRecoverIntAmt(0);
                        pojo.setRecoverPriAmt(0);
                    } else {

                        if (chargeAmt == 0) {
                            pojo.setRecoverChargeAmt(0);
                        }

                        if ((depositAmt - chargeAmt) < 0) {
                            pojo.setRecoverChargeAmt(depositAmt);
                            pojo.setRecoverIntAmt(0);
                            pojo.setRecoverPriAmt(0);
                        } else if ((depositAmt - chargeAmt) == 0) {
                            pojo.setRecoverChargeAmt(depositAmt);
                            pojo.setRecoverIntAmt(0);
                            pojo.setRecoverPriAmt(0);
                        } else {
                            pojo.setRecoverChargeAmt(chargeAmt);
                            double dep1 = depositAmt - chargeAmt;
                            if ((dep1 - interestAmt) < 0) {
                                pojo.setRecoverIntAmt(dep1);
                                pojo.setRecoverPriAmt(0);
                            } else if ((dep1 - interestAmt) == 0) {
                                pojo.setRecoverIntAmt(dep1);
                                pojo.setRecoverPriAmt(0);
                            } else {
                                pojo.setRecoverIntAmt(interestAmt);
                                double dep2 = dep1 - interestAmt;
                                if ((dep2 - principalAmt) < 0) {
                                    pojo.setRecoverPriAmt(dep2);
                                } else if ((dep2 - principalAmt) == 0) {
                                    pojo.setRecoverPriAmt(dep2);
                                } else {
                                    pojo.setRecoverPriAmt(principalAmt);
                                }
                            }
                        }
                    }
                    pojo.setBal((Math.abs(outstandbal) - Math.abs(npaAmt)) < 0 ? 0 : (Math.abs(outstandbal) - Math.abs(npaAmt)));
                    pojo.setNpaBal(Math.abs(npaAmt));
                    pojo.setOutStandBal(Math.abs(outstandbal));
                    pojo.setDepositAmt(depositAmt);
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }

        return dataList;
    }

    public List<FolioLedgerPojo> getFolioLedgerData(String folioNo, String frDt, String toDt) throws ApplicationException {
        List<FolioLedgerPojo> dataList = new ArrayList<FolioLedgerPojo>();
        double totalBal = 0, emeOpeningbalance = 0, speOpeningbalance = 0, ordOpeningbalance = 0, thrFuOpeningbalance = 0, shaOpeningbalance = 0, givenLoan = 0;
        int g = 0;
        try {

            List result1 = em.createNativeQuery("select a.txndt from (select distinct date_format(dt,'%Y%m%d')as txndt from loan_recon where acno in(select acno from accountmaster where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')"
                    + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "'))) "
                    + "and(closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "')) and dt between '" + frDt + "' and '" + toDt + "' "
                    + "union "
                    + "select distinct date_format(dt,'%Y%m%d')as txndt from recon where acno in(select acno from accountmaster where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')"
                    + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctcode in('" + CbsAcCodeConstant.NF_AC.getAcctCode() + "','" + CbsAcCodeConstant.CL_AC.getAcctCode() + "'))) "
                    + "and(closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "')) and dt between '" + frDt + "' and '" + toDt + "')a order by a.txndt").getResultList();
            if (!result1.isEmpty()) {
                for (int j = 0; j < result1.size(); j++) {
                    Vector dtVector = (Vector) result1.get(j);
                    FolioLedgerPojo pojo = new FolioLedgerPojo();
                    String txnDt = dtVector.get(0).toString();
                    String txnFrDt = CbsUtil.dateAdd(txnDt, 1);
//                    System.out.println("FrDt==" + txnDt + "ToDt==" + txnFrDt);
                    g = g + 1;
                    if (g == 1) {
                        txnFrDt = frDt;
                    }
                    //Loan Givem
                    // List loanAmtList = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "'))) and dt <='" + txnDt + "'").getResultList();
                    List loanAmtList = em.createNativeQuery("select ifnull(sum(Sanctionlimit),0) from loan_appparameter where acno in(select acno from accountmaster where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('tl'))) and(closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "'))").getResultList();
                    Vector loanVector = (Vector) loanAmtList.get(0);
                    givenLoan = Double.parseDouble(loanVector.get(0).toString());
                    // Total Recovery Amount
                    List totalRecList = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "'))) and (dt >= '" + txnFrDt + "' and dt <= '" + txnDt + "' )").getResultList();
                    Vector totalRecVector = (Vector) totalRecList.get(0);
                    double totalRecAmt = Double.parseDouble(totalRecVector.get(0).toString());
                    // Interest due amount
                    List dueIntList = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "'))) and (dt >= '" + txnFrDt + "' and dt <= '" + txnDt + "' ) and trandesc in('3','4')").getResultList();
                    Vector dueIntVector = (Vector) dueIntList.get(0);
                    double dueIntAmt = Double.parseDouble(dueIntVector.get(0).toString());
                    if (totalRecAmt >= dueIntAmt) {
                        totalRecAmt = dueIntAmt;
                    } else {
                        totalRecAmt = totalRecAmt;
                    }
                    pojo.setTxnDt(txnDt.substring(6, 8) + "/" + txnDt.substring(4, 6) + "/" + txnDt.substring(0, 4));
                    if (g == 1) {
                        pojo.setLoanAmt(givenLoan);
                    }
                    pojo.setTotAmtRec(totalRecAmt);

                    //Share code here(Pending)
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(ymdFormat.parse(frDt));
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    List shaOpenBalList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from dividend_recon where acno='" + folioNo + "' and disdate < '" + ymdFormat.format(calendar.getTime()) + "'").getResultList();
                    Vector shaOpenv = (Vector) shaOpenBalList.get(0);
                    shaOpeningbalance = Double.parseDouble(shaOpenv.get(0).toString());

                    List shaList = em.createNativeQuery("select status,count(shareno),date_format(cs.Issuedt,'%Y%m%d') from share_capital_issue sc,"
                            + "share_holder sh,certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid and "
                            + "sc.foliono=sh.regfoliono and sh.regfoliono='" + folioNo + "' and cs.certificateNo=sc.sharecertNo and cs.Issuedt<='"
                            + txnDt + "' group by regfoliono,shareCertno,cs.issueDt,status").getResultList();

                    if (!shaList.isEmpty()) {
                        Vector shaVector = (Vector) shaList.get(0);
                        String status = shaVector.get(0).toString();
                        Integer shareNo = Integer.parseInt(shaVector.get(1).toString());

                        String issueDt = shaVector.get(2).toString();
                        Vector tempVector = (Vector) em.createNativeQuery("select shareAmt from share_value where effectivedt = "
                                + "(select max(effectivedt) from share_value where effectivedt <='" + issueDt + "')").getResultList().get(0);
                        double shareValue = Double.parseDouble(tempVector.get(0).toString());

                        if (status.equalsIgnoreCase("A")) {
                            pojo.setShRec(shareValue * shareNo);
                        } else {
                            pojo.setShPay(shareValue * shareNo);
                        }

                        List shaBalList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from dividend_recon where acno='" + folioNo + "' and disdate < '" + txnDt + "'").getResultList();
                        Vector tempVector1 = (Vector) shaBalList.get(0);
                        double balance = Double.parseDouble(tempVector1.get(0).toString());
                        pojo.setShBal(balance);

                    }

                    //end  Share code here(Pending)
                    // EMERGENT LOAN (RECOVERY AMOUNT,REST AMOUNT,BAL)
                    double emePrinAmt = 0, emeDemandAmt = 0, emeRestAmt = 0, emeRecAmt = 0, emeIntAmt = 0, emeBal = 0;
                    List emeAcnoList = em.createNativeQuery("select acno from accountmaster where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2)='31')and(closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "')").getResultList();
                    if (!emeAcnoList.isEmpty()) {
                        Vector emeacnoVector = (Vector) emeAcnoList.get(0);
                        String emeAcno = emeacnoVector.get(0).toString();
                        List emePrinList = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) from emidetails where acno = '" + emeAcno + "' and status = 'unpaid' and duedt <= '" + txnDt + "'").getResultList();
                        Vector ele2 = (Vector) emePrinList.get(0);
                        emePrinAmt = Double.parseDouble(ele2.get(0).toString());
                        emeBal = common.getBalanceOnDate(emeAcno, txnDt);
                        // totalBal = totalBal + outstandbal;
//                         String fromDt = loanInterestCalculationBean.allFromDt(emeAcno.substring(2, 4), common.getBrncodeByAcno(emeAcno), "f");
//                         String toDate = loanInterestCalculationBean.allFromDt(emeAcno.substring(2, 4), common.getBrncodeByAcno(emeAcno), "t");
                        LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(frDt, txnDt, emeAcno, common.getBrncodeByAcno(emeAcno));
                        emeIntAmt = it.getTotalInt();
                        if (!(emeIntAmt == 0)) {
                            emeIntAmt = emeIntAmt * -1;
                        }
                        emeDemandAmt = emePrinAmt + emeIntAmt;
                        List emeRecList = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno ='" + emeAcno + "' and (dt >= '" + txnFrDt + "' and dt <= '" + txnDt + "' )").getResultList();
                        Vector emeReVector = (Vector) emeRecList.get(0);
                        emeRecAmt = Double.parseDouble(emeReVector.get(0).toString());
                        emeRestAmt = emeDemandAmt - emeRecAmt;
                        int emeChk = Double.compare(emeDemandAmt, emeRecAmt);

                        pojo.setEmeRec(emeRecAmt);
                        if (emeChk == 1) {
                            pojo.setEmeRest(emeRestAmt);
                        } else {
                            pojo.setEmeRest(0);
                        }
//                        if (emeRecAmt != 0) {
//                            pojo.setEmeRest(emeRestAmt);
//                        } else {
//                            pojo.setEmeRest(0);
//                        }
                        pojo.setEmeBal(Math.abs(emeBal));
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(ymdFormat.parse(frDt));
//                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        emeOpeningbalance = openingBalance(emeAcno, ymdFormat.format(calendar.getTime()));
                    }
                    // End of EMERGENT LOAN (RECOVERY AMOUNT,REST AMOUNT,BAL)

                    // SPECIAL LOAN (RECOVERY AMOUNT,REST AMOUNT,BAL)
                    double spePrinAmt = 0, speDemandAmt = 0, speRestAmt = 0, speRecAmt = 0, speIntAmt = 0, speBal = 0;
                    List speAcnoList = em.createNativeQuery("select acno from accountmaster where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2)='29')and(closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "')").getResultList();
                    if (!speAcnoList.isEmpty()) {
                        Vector speacnoVector = (Vector) speAcnoList.get(0);
                        String speAcno = speacnoVector.get(0).toString();
                        List spePrinList = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) from emidetails where acno = '" + speAcno + "' and status = 'unpaid' and duedt <= '" + txnDt + "'").getResultList();
                        Vector ele3 = (Vector) spePrinList.get(0);
                        spePrinAmt = Double.parseDouble(ele3.get(0).toString());
                        speBal = common.getBalanceOnDate(speAcno, txnDt);

                        LoanIntCalcList it1 = loanInterestCalculationBean.accWiseLoanIntCalc(frDt, txnDt, speAcno, common.getBrncodeByAcno(speAcno));
                        speIntAmt = it1.getTotalInt();
                        if (!(speIntAmt == 0)) {
                            speIntAmt = speIntAmt * -1;
                        }
                        speDemandAmt = spePrinAmt + speIntAmt;
                        List speRecList = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno ='" + speAcno + "' and (dt >= '" + txnFrDt + "' and dt <= '" + txnDt + "' )").getResultList();
                        Vector speReVector = (Vector) speRecList.get(0);
                        speRecAmt = Double.parseDouble(speReVector.get(0).toString());
                        speRestAmt = speDemandAmt - speRecAmt;
                        int speChk = Double.compare(speDemandAmt, speRecAmt);

                        pojo.setSpeRec(speRecAmt);
                        if (speChk == 1) {
                            pojo.setSpeRest(speRestAmt);
                        } else {
                            pojo.setSpeRest(0);
                        }
                        pojo.setSpeBal(Math.abs(speBal));
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(ymdFormat.parse(frDt));
//                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        speOpeningbalance = openingBalance(speAcno, ymdFormat.format(calendar.getTime()));
                    }

                    // END OF SPECIAL LOAN (RECOVERY AMOUNT,REST AMOUNT,BAL)
                    // ORDINARY LOAN (RECOVERY AMOUNT,REST AMOUNT,BAL)
                    double ordPrinAmt = 0, ordDemandAmt = 0, ordRestAmt = 0, ordRecAmt = 0, ordIntAmt = 0, ordBal = 0;
                    List ordAcnoList = em.createNativeQuery("select acno from accountmaster where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2)='30')and(closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "')").getResultList();
                    if (!ordAcnoList.isEmpty()) {
                        Vector ordacnoVector = (Vector) ordAcnoList.get(0);
                        String ordAcno = ordacnoVector.get(0).toString();
                        List ordPrinList = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) from emidetails where acno = '" + ordAcno + "' and status = 'unpaid' and duedt <= '" + txnDt + "'").getResultList();
                        Vector ele4 = (Vector) ordPrinList.get(0);
                        ordPrinAmt = Double.parseDouble(ele4.get(0).toString());
                        ordBal = common.getBalanceOnDate(ordAcno, txnDt);

                        LoanIntCalcList it2 = loanInterestCalculationBean.accWiseLoanIntCalc(frDt, txnDt, ordAcno, common.getBrncodeByAcno(ordAcno));
                        ordIntAmt = it2.getTotalInt();
                        if (!(ordIntAmt == 0)) {
                            ordIntAmt = ordIntAmt * -1;
                        }
                        ordDemandAmt = ordPrinAmt + ordIntAmt;
                        List ordRecList = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno ='" + ordAcno + "' and (dt >= '" + txnFrDt + "' and dt <= '" + txnDt + "' )").getResultList();
                        Vector ordReVector = (Vector) ordRecList.get(0);
                        ordRecAmt = Double.parseDouble(ordReVector.get(0).toString());
                        ordRestAmt = ordDemandAmt - ordRecAmt;
                        int ordChk = Double.compare(ordDemandAmt, ordRecAmt);
                        pojo.setOrdRec(ordRecAmt);
                        if (ordChk == 1) {
                            pojo.setOrdRest(ordRestAmt);
                        } else {
                            pojo.setOrdRest(0);
                        }
                        pojo.setOrdBal(Math.abs(ordBal));
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(ymdFormat.parse(frDt));
//                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        ordOpeningbalance = openingBalance(ordAcno, ymdFormat.format(calendar.getTime()));
                    }
                    totalBal = emeBal + speBal + ordBal;
                    // END OF ORDINARY LOAN (RECOVERY AMOUNT,REST AMOUNT,BAL)

                    //VALUE SET FOR SHARE
                    // *********
                    // END FOR VALUE SET FOR SHARE
                    //VALUE SET FOR INTEREST
                    pojo.setIntValid(dueIntAmt);
                    pojo.setIntRec(totalRecAmt);
                    pojo.setIntBal(0);
                    //END VALUE SET FOR INTEREST
                    pojo.setTotalBalLoan(Math.abs(totalBal)); //  TOTAL BALANCE LOAN
                    //RECOVERY ,PAY AND BAL FOR THRIFT FUND
                    double thrfuRecAmt = 0, thrfuPayAmt = 0, thrfuBalAmt = 0;
                    List thrfuAcnoList = em.createNativeQuery("select acno from accountmaster where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2)='28')and(closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "')").getResultList();
                    if (!thrfuAcnoList.isEmpty()) {
                        Vector thrfuAcnoVector = (Vector) thrfuAcnoList.get(0);
                        String thrfuAcno = thrfuAcnoVector.get(0).toString();

                        List thrfuRecList = em.createNativeQuery("select ifnull(sum(cramt),0) from recon where acno ='" + thrfuAcno + "' and (dt >= '" + txnFrDt + "' and dt <= '" + txnDt + "' )").getResultList();
                        Vector thrfuRecVector = (Vector) thrfuRecList.get(0);
                        thrfuRecAmt = Double.parseDouble(thrfuRecVector.get(0).toString());

                        List thrfuPayList = em.createNativeQuery("select ifnull(sum(dramt),0) from recon where acno ='" + thrfuAcno + "' and (dt >= '" + txnFrDt + "' and dt <= '" + txnDt + "' )").getResultList();
                        Vector thrfuPayVector = (Vector) thrfuPayList.get(0);
                        thrfuPayAmt = Double.parseDouble(thrfuPayVector.get(0).toString());

                        thrfuBalAmt = common.getBalanceOnDate(thrfuAcno, txnDt);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(ymdFormat.parse(frDt));
//                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        thrFuOpeningbalance = openingBalance(thrfuAcno, ymdFormat.format(calendar.getTime()));
                        pojo.setThrfuRec(thrfuRecAmt);
                        pojo.setThrfuPay(thrfuPayAmt);
                        pojo.setThrfuBal(thrfuBalAmt);
                    }

                    pojo.setShaOpeningBal(String.valueOf(shaOpeningbalance));
//                    pojo.setEmeOpeningBal(String.valueOf(Math.abs(emeOpeningbalance)));
//                    pojo.setSpeOpeningBal(String.valueOf(Math.abs(speOpeningbalance)));
//                    pojo.setOrdOpeningBal(String.valueOf(Math.abs(ordOpeningbalance)));
                    pojo.setThrFuOpeningBal(String.valueOf(thrFuOpeningbalance));
                    //END RECOVERY ,PAY AND BAL FOR THRIFT FUND
                    // DIVIDEND CODE
                    pojo.setDivOpen("");
                    pojo.setDivDemand("");
                    pojo.setDivPay("");
                    pojo.setDivBal("");
                    // END DIVIDEND CODE

                    // THRIFT INTEREST CODE
                    pojo.setThrOpen("");
                    pojo.setThrDemand("");
                    pojo.setThrPay("");
                    pojo.setThrBal("");
                    // END THRIFT INTEREST CODE
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public double getshareBal(String folioNo, String dt) throws ApplicationException {
        double shareBal = 0d;
        //Integer totalShareNo = 0;
        try {
            List shaBalList = em.createNativeQuery("select ifnull(status,0),count(shareno),date_format(cs.Issuedt,'%Y%m%d') from share_capital_issue sc,share_holder sh,"
                    + "certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and "
                    + "sh.regfoliono='" + folioNo + "' and cs.certificateNo=sc.sharecertNo and cs.Issuedt <='" + dt + "' "
                    + "group by regfoliono,shareCertno,cs.issueDt,status").getResultList();
            for (int i = 0; i < shaBalList.size(); i++) {
                Vector shaBalVector = (Vector) shaBalList.get(i);
                String statusBal = shaBalVector.get(0).toString();
                Integer shareNoBal = Integer.parseInt(shaBalVector.get(1).toString());

                if (statusBal.equalsIgnoreCase("A")) {
                    String issueDt = shaBalVector.get(2).toString();
                    Vector tempVector = (Vector) em.createNativeQuery("select shareAmt from share_value where effectivedt = "
                            + "(select max(effectivedt) from share_value where effectivedt <='" + issueDt + "')").getResultList().get(0);
                    double shareValue = Double.parseDouble(tempVector.get(0).toString());

                    shareBal = shareBal + (shareNoBal * shareValue);
                }
            }
            //shareBal = shareValue * totalShareNo;
            return shareBal;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /*Getting the Rest Balance as per account and dmdFlowId*/
    public double getAcRestBalAsPerDmdFlowId(String acNo, String dt, String dmdFlowId) throws ApplicationException {
        double dmdAmt = 0, dmdRecAmt = 0;
        List dmdList = em.createNativeQuery("select a.SHDL_NUM, a.DMD_SRL_NUM, a.DMD_DATE, a.DMD_AMT from cbs_loan_dmd_table a where "
                + " a.acno ='" + acNo + "' and a.dmd_date <= '" + dt + "' and a.DMD_FLOW_ID = '" + dmdFlowId + "'  group by a.acno, a.DMD_SRL_NUM, a.SHDL_NUM").getResultList();
        if (!dmdList.isEmpty()) {
            for (int k = 0; k < dmdList.size(); k++) {
                Vector dmdVector = (Vector) dmdList.get(k);
                String schNo = dmdVector.get(0).toString();
                String slrNo = dmdVector.get(1).toString();
                String dmdDt = dmdVector.get(2).toString();
                dmdAmt = dmdAmt + Double.parseDouble(dmdVector.get(3).toString());

                List dmdRecList = em.createNativeQuery("select ifnull(sum(ifnull(a.ADJ_AMT,0)),0) from cbs_loan_dmd_adj_table a where "
                        + " a.acno ='" + acNo + "' and a.ADJ_DATE <= '" + dt + "' and  a.SHDL_NUM = '" + schNo + "' and a.DMD_SRL_NUM = '" + slrNo + "' and a.DMD_FLOW_ID = '" + dmdFlowId + "' ").getResultList();
                if (dmdRecList.size() > 0) {
                    Vector dmdRecVector = (Vector) dmdRecList.get(0);
                    dmdRecAmt = dmdRecAmt + Double.parseDouble(dmdRecVector.get(0).toString());
                }
            }
        }
        return dmdAmt - dmdRecAmt;
    }

    /*Account recovery as on date as per Flow Id*/
    public double getAcRecAsOnDateAsPerDmdFlowId(String acNo, String dt, String dmdFlowId) throws ApplicationException {
        double dmdAmt = 0, dmdRecAmt = 0;
        List dmdList = em.createNativeQuery("select a.SHDL_NUM, a.DMD_SRL_NUM, a.DMD_DATE, a.DMD_AMT from cbs_loan_dmd_table a where "
                + " a.acno ='" + acNo + "' and a.dmd_date <= '" + dt + "' and a.DMD_FLOW_ID = '" + dmdFlowId + "' ").getResultList();
        if (!dmdList.isEmpty()) {
            for (int k = 0; k < dmdList.size(); k++) {
                Vector dmdVector = (Vector) dmdList.get(k);
                String schNo = dmdVector.get(0).toString();
                String slrNo = dmdVector.get(1).toString();
                String dmdDt = dmdVector.get(2).toString();
//                dmdAmt = dmdAmt + Double.parseDouble(dmdVector.get(3).toString());

                List dmdRecList = em.createNativeQuery("select ifnull(sum(ifnull(a.ADJ_AMT,0)),0) from cbs_loan_dmd_adj_table a where "
                        + " a.acno ='" + acNo + "' and a.ADJ_DATE = '" + dt + "' and  a.SHDL_NUM = '" + schNo + "' and a.DMD_SRL_NUM = '" + slrNo + "' and a.DMD_FLOW_ID = '" + dmdFlowId + "' ").getResultList();
                if (dmdRecList.size() > 0) {
                    Vector dmdRecVector = (Vector) dmdRecList.get(0);
                    dmdRecAmt = dmdRecAmt + Double.parseDouble(dmdRecVector.get(0).toString());
                }
            }
        }
        return dmdRecAmt;
    }

    /*Account recovery as on date as per Flow Id*/
    public double getPostingDmdAmtAsPerDmdFlowId(String acNo, String dt, String dmdFlowId) throws ApplicationException {
        double dmdAmt = 0, dmdRecAmt = 0;
        List dmdList = em.createNativeQuery("select a.SHDL_NUM, a.DMD_SRL_NUM, a.DMD_DATE, a.DMD_AMT from cbs_loan_dmd_table a where "
                + " a.acno ='" + acNo + "' and a.dmd_date = '" + dt + "' and a.DMD_FLOW_ID = '" + dmdFlowId + "' ").getResultList();
        if (!dmdList.isEmpty()) {
            for (int k = 0; k < dmdList.size(); k++) {
                Vector dmdVector = (Vector) dmdList.get(k);
                String schNo = dmdVector.get(0).toString();
                String slrNo = dmdVector.get(1).toString();
                String dmdDt = dmdVector.get(2).toString();
                dmdAmt = dmdAmt + Double.parseDouble(dmdVector.get(3).toString());
            }
        }
        return dmdAmt;
    }

    public double getCrEntryAsOnDt(String acNo, String dt) throws ApplicationException {
        double dmdRecAmt = 0;
        List dmdList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) from loan_recon  where "
                + " acno ='" + acNo + "' and dt= '" + dt + "' and auth = 'Y' " //                +(dmdFlowId.equalsIgnoreCase("INDEM")? " and trandesc in (3,4) ":" and trandesc not in (3,4) ")
                ).getResultList();
        if (!dmdList.isEmpty()) {
            Vector dmdVector = (Vector) dmdList.get(0);
            dmdRecAmt = dmdRecAmt + Double.parseDouble(dmdVector.get(0).toString());
        }
        return dmdRecAmt;
    }

    public String getAcnoByFolio(String folio) throws ApplicationException {
        try {
            List result = em.createNativeQuery("select acno from customerid where custid = (select custId from share_holder where Regfoliono = '" + folio + "') and substring(acno,3,2)in(select acctcode from accounttypemaster where accttype = 'tf')").getResultList();
            if (result.size() > 0) {
                Vector element = (Vector) result.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Account does not exist!");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getLoanAcnoByFolio(String folio, Integer orNo) throws ApplicationException {
        try {
            String acno = "";
            List result = em.createNativeQuery("select acno from accountmaster where acno in(select acno from customerid where custid = (select custId from share_holder where Regfoliono = '" + folio + "'))and accttype = (select REF_CODE from cbs_ref_rec_type where REF_REC_NO = '400' and ORDER_BY = " + orNo + ")").getResultList();
            if (result.size() > 0) {
                Vector element = (Vector) result.get(0);
                acno = element.get(0).toString();
            }
            return acno;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getFolioLedgerHeaderData(String folioNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select custname,fathername,date_format(DateOfBirth,'%d/%m/%Y'),ifnull(employeeNo,'') from cbs_customer_master_detail where customerid = (select CustId from share_holder where Regfoliono = '" + folioNo + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List<GuarantorDetailPojo> getguarantorData(String brCode, String reportType, String guarantor, String acNo, String asOnDt) throws ApplicationException {
        List<GuarantorDetailPojo> dataList = new ArrayList<GuarantorDetailPojo>();
        List result = new ArrayList();
        try {
            String branch = "";
            if (brCode.equalsIgnoreCase("0A")) {
                branch = "";
            } else {
                branch = "and substring(a.acno,1,2)='" + brCode + "'";
            }
            List actCodeList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where acctnature = '" + CbsConstant.TERM_LOAN + "' order by acctcode").getResultList();
            Map<String, String> acTypeMap = new HashMap<String, String>();
            for (int j = 0; j < actCodeList.size(); j++) {
                Vector vtr = (Vector) actCodeList.get(j);
                acTypeMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }
            if (fts.getBankCode().equalsIgnoreCase("army")) {
                int limit = 0;
                if (reportType.equalsIgnoreCase("Account No.")) {
                    if (guarantor.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select a.acno,b.regfoliono,name,cast(gar_custid as unsigned),ifnull(networth,0),date_format(a.trantime,'%Y%m%d'),date_format(disbDate,'%Y%m%d')\n"
                                + "from loan_guarantordetails a,share_holder b,(select acno,date_format(max(DISBURSEMENTDT),'%Y%m%d') disbDate from loandisbursement "
                                + "where DISBURSEMENTDT <='" + ymdFormat.format(date) + "' and substring(acno,3,2)in('" + CbsAcCodeConstant.TRANS_LOAN.getAcctCode() + "','" + CbsAcCodeConstant.VECH_LOAN.getAcctCode() + "') group by acno)c \n"
                                + "where a.gar_custid = b.custid and a.acno = c.acno " + branch + " "
                                + "and date_format(a.trantime,'%Y%m%d') >= date_format(disbDate,'%Y%m%d')\n"
                                + "order by acno,cast(gar_custid as unsigned)").getResultList();
                    } else {
                        result = em.createNativeQuery("select a.acno,b.regfoliono,name,cast(gar_custid as unsigned),ifnull(networth,0),date_format(a.trantime,'%Y%m%d'),date_format(disbDate,'%Y%m%d')\n"
                                + "from loan_guarantordetails a,share_holder b,(select acno,date_format(max(DISBURSEMENTDT),'%Y%m%d') disbDate from loandisbursement where DISBURSEMENTDT <='" + ymdFormat.format(date) + "' and acno = '" + acNo + "' group by acno)c \n"
                                + "where a.gar_custid = b.custid and a.acno = c.acno and date_format(a.trantime,'%Y%m%d') >= date_format(disbDate,'%Y%m%d')\n"
                                + "order by acno,cast(gar_custid as unsigned)").getResultList();
                    }
                } else {
                    List limitList = em.createNativeQuery("select distinct ADHOC_INT_CERTIFICATE_PRINTING_EVENT from cbs_scheme_currency_details ").getResultList();
                    Vector limitVector = (Vector) limitList.get(0);
                    limit = Integer.parseInt(limitVector.get(0).toString());
                    if (guarantor.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select a.acno,b.regfoliono,name,cast(gar_custid as unsigned),a.networth,b.area,ifnull(b.Salary,0) + ifnull(b.gradepay,0)"
                                + "from loan_guarantordetails a,share_holder b where a.gar_custid = b.custid " + branch + " "
                                + "order by cast(gar_custid as unsigned),acno").getResultList();

                    } else if (guarantor.equalsIgnoreCase("A/c No.")) {
                        result = em.createNativeQuery("select acno,name,address,fathersname,networth,cust_flag,gar_acno,gar_custid from loan_guarantordetails where gar_custid in(select CustId from customerid where acno = '" + acNo + "') order by gar_acno").getResultList();
                    } else if (guarantor.equalsIgnoreCase("Folio No.")) {
                        result = em.createNativeQuery("select a.acno,b.regfoliono,name,cast(gar_custid as unsigned),ifnull(a.networth,0),b.area,ifnull(b.Salary,0) + ifnull(b.gradepay,0)"
                                + "from loan_guarantordetails a,share_holder b where a.gar_custid in(select custId from share_holder where regfoliono = '" + acNo + "') "
                                + "and a.gar_custid = b.custid order by acno,cast(gar_custid as unsigned)").getResultList();
                    } else if (guarantor.equalsIgnoreCase("Cust Id.")) {
                        result = em.createNativeQuery("select a.acno,b.regfoliono,name,cast(gar_custid as unsigned),ifnull(a.networth,0),b.area,ifnull(b.Salary,0) + ifnull(b.gradepay,0)"
                                + "from loan_guarantordetails a,share_holder b where a.gar_custid = '" + acNo + "' and a.gar_custid = b.custid "
                                + "order by acno,cast(gar_custid as unsigned)").getResultList();
                    }
                }
                int inct = 0;
                String acno = "";
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        GuarantorDetailPojo pojo = new GuarantorDetailPojo();
                        if (reportType.equalsIgnoreCase("Guarantor")) {
                            List dtList = em.createNativeQuery("select ifnull(amtdisbursed,0),ifnull(date_format(max(disbursementdt),'%d/%m/%Y'),'') from loandisbursement where acno ='" + vtr.get(0).toString() + "' and sno=(select max(SNO) from loandisbursement where acno ='" + vtr.get(0).toString() + "')").getResultList();
                            Vector dtVector = (Vector) dtList.get(0);
                            pojo.setDisbursementAmt(new BigDecimal(dtVector.get(0).toString()));
                            pojo.setDisbursementDt(dtVector.get(1).toString());

                            if (inct == 0) {
                                inct = inct + 1;
                                acno = vtr.get(3).toString();
                            } else {
                                if (vtr.get(3).toString().equalsIgnoreCase(acno)) {
                                    inct = inct + 1;
                                    acno = vtr.get(3).toString();
                                } else {
                                    inct = 1;
                                    acno = vtr.get(3).toString();
                                }
                            }
                            // pojo.setGuarantor(guarantor);
                            pojo.setAcNo(vtr.get(3).toString());
                            pojo.setGuarCustId(vtr.get(0).toString());
                            pojo.setOutStanding(new BigDecimal(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(vtr.get(0).toString(), ymdFormat.format(date)))).abs());
                            pojo.setAddress(acTypeMap.get(vtr.get(0).toString().substring(2, 4)));
                            pojo.setMemberName(vtr.get(2).toString());
                            pojo.setCustName(fts.getCustName(vtr.get(0).toString()));
                            pojo.setNetWorth(new BigDecimal(vtr.get(4).toString()));
                            pojo.setArea(vtr.get(5).toString());
                            pojo.setSalary(new BigDecimal(vtr.get(6).toString()));
                            pojo.setGuaPotential(new BigDecimal(vtr.get(6).toString()).multiply(new BigDecimal(limit)));
                            pojo.setSrlNo(inct);
                        } else {
                            if (inct == 0) {
                                inct = inct + 1;
                                acno = vtr.get(0).toString();
                            } else {
                                if (vtr.get(0).toString().equalsIgnoreCase(acno)) {
                                    inct = inct + 1;
                                    acno = vtr.get(0).toString();
                                } else {
                                    inct = 1;
                                    acno = vtr.get(0).toString();
                                }
                            }
//                        System.out.println(inct);
                            double gauDeduction = 0, bal = 0;
                            BigDecimal disburseAmt = new BigDecimal("0");
//                        List gauList = em.createNativeQuery("select ifnull(ifnull(sum(cramt),0)/(select count(*)NoofGaurntor from loan_guarantordetails  where ACNO = '" + vtr.get(0).toString() + "'),0) GaurantorDeductionAmt "
//                                + "from loan_recon where acno = '" + vtr.get(0).toString() + "' and dt > "
//                                + "ifnull((select date_format(max(DISBURSEMENTDT),'%Y%m%d') from loandisbursement where acno = '" + vtr.get(0).toString() + "'),"
//                                + "(select date_format(max(dt),'%Y%m%d') from loan_recon where acno = '" + vtr.get(0).toString() + "' and trandesc = 6 and dt <= '" + ymdFormat.format(date) + "'))").getResultList();
                            List gauList = em.createNativeQuery("select partOfLoan,disbursAmt,disbursDt,balance from(\n"
                                    + "(select ifnull(amtdisbursed,0) disbursAmt,ifnull(date_format(max(disbursementdt),'%d/%m/%Y'),'') disbursDt from loandisbursement \n"
                                    + "where acno ='" + vtr.get(0).toString() + "' and sno=(select max(SNO) from loandisbursement where acno ='" + vtr.get(0).toString() + "'))a,\n"
                                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) balance,ifnull(round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2)/\n"
                                    + "(select count(*)NoofGaurntor from loan_guarantordetails  \n"
                                    + "where ACNO = '" + vtr.get(0).toString() + "' and date_format(TRANTIME,'%Y%m%d') >=\n"
                                    + "ifnull((select date_format(max(DISBURSEMENTDT),'%Y%m%d') from loandisbursement where acno = '" + vtr.get(0).toString() + "'),\n"
                                    + "(select date_format(max(dt),'%Y%m%d') from loan_recon where acno = '" + vtr.get(0).toString() + "' and trandesc = 6 and dt <= '" + ymdFormat.format(date) + "'))),0) partOfLoan\n"
                                    + "from loan_recon where auth= 'Y' and acno = '" + vtr.get(0).toString() + "' and dt <= '" + ymdFormat.format(date) + "')b\n"
                                    + " )").getResultList();

                            if (!gauList.isEmpty()) {
                                Vector ele = (Vector) gauList.get(0);
                                gauDeduction = Double.parseDouble(ele.get(0).toString());
                                disburseAmt = new BigDecimal(ele.get(1).toString());
                                bal = Double.parseDouble(ele.get(3).toString());
                            }
                            pojo.setGuarAcno(fts.getCustName(vtr.get(0).toString()));
                            pojo.setAcNo(vtr.get(0).toString());
                            pojo.setDisbursementAmt(disburseAmt);
                            pojo.setOutStanding(new BigDecimal(Math.abs(bal)));
                            pojo.setGuaPotential(new BigDecimal(Math.abs(gauDeduction)));
                            pojo.setGuarCustId(vtr.get(1).toString());
                            pojo.setCustName(vtr.get(2).toString());
                            pojo.setNetWorth(new BigDecimal(Double.parseDouble(vtr.get(4).toString())));
                            pojo.setGuarDate(dmyFormat.format(ymdFormat.parse(vtr.get(6).toString())));
                            pojo.setSrlNo(inct);
                        }
                        dataList.add(pojo);
                    }
                }
            } else {
                int inct = 0;
                String acno = "";
                if (reportType.equalsIgnoreCase("Account No.")) {
                    if (guarantor.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select a.acno,name,ifnull(cast(gar_custid as unsigned),0),ifnull(networth,0),date_format(a.trantime,'%Y%m%d'),date_format(disbDate,'%Y%m%d') "
                                + "from loan_guarantordetails a,(select acno,date_format(min(DISBURSEMENTDT),'%Y%m%d') disbDate from loandisbursement "
                                + "where DISBURSEMENTDT <='" + asOnDt + "'  group by acno)c "
                                + "where a.acno = c.acno " + branch + " and date_format(a.trantime,'%Y%m%d') >= date_format(disbDate,'%Y%m%d') "
                                + "order by acno,cast(gar_custid as unsigned)").getResultList();
                    } else {
                        result = em.createNativeQuery("select a.acno,name,ifnull(cast(gar_custid as unsigned),0),ifnull(networth,0),date_format(a.trantime,'%Y%m%d'),date_format(disbDate,'%Y%m%d') "
                                + "from loan_guarantordetails a,(select acno,date_format(min(DISBURSEMENTDT),'%Y%m%d') disbDate from loandisbursement "
                                + "where DISBURSEMENTDT <='" + asOnDt + "'  group by acno)c "
                                + "where a.acno = c.acno and a.acno = '" + acNo + "' and date_format(a.trantime,'%Y%m%d') >= date_format(disbDate,'%Y%m%d') "
                                + "order by acno,cast(gar_custid as unsigned)").getResultList();
                    }
                } else {
                    if (guarantor.equalsIgnoreCase("ALL")) {
//                        result = em.createNativeQuery("select a.acno,b.regfoliono,name,cast(gar_custid as unsigned),a.networth,b.area,ifnull(b.Salary,0) + ifnull(b.gradepay,0)"
//                                + "from loan_guarantordetails a,share_holder b where a.gar_custid = b.custid " + branch + " "
//                                + "order by cast(gar_custid as unsigned),acno").getResultList();
                        result = em.createNativeQuery("select a.acno,'' regfoliono,Custfullname,cast(gar_custid as unsigned),a.networth,'' area,0 salary "
                                + "from loan_guarantordetails a,cbs_customer_master_detail b where a.gar_custid = b.customerid " + branch + " "
                                + "order by cast(customerid as unsigned),acno").getResultList();
                    } else if (guarantor.equalsIgnoreCase("A/c No.")) {
                        result = em.createNativeQuery("select acno,name,address,fathersname,networth,cust_flag,gar_acno,gar_custid from loan_guarantordetails where gar_custid in(select CustId from customerid where acno = '" + acNo + "') order by gar_acno").getResultList();
                    } else if (guarantor.equalsIgnoreCase("Folio No.")) {
                        result = em.createNativeQuery("select a.acno,b.regfoliono,name,cast(gar_custid as unsigned),ifnull(a.networth,0),b.area,ifnull(b.Salary,0) + ifnull(b.gradepay,0)"
                                + "from loan_guarantordetails a,share_holder b where a.gar_custid in(select custId from share_holder where regfoliono = '" + acNo + "') "
                                + "and a.gar_custid = b.custid order by acno,cast(gar_custid as unsigned)").getResultList();
                    } else if (guarantor.equalsIgnoreCase("Cust Id.")) {
//                        result = em.createNativeQuery("select a.acno,b.regfoliono,name,cast(gar_custid as unsigned),ifnull(a.networth,0),b.area,ifnull(b.Salary,0) + ifnull(b.gradepay,0)"
//                                + "from loan_guarantordetails a,share_holder b where a.gar_custid = '" + acNo + "' and a.gar_custid = b.custid "
//                                + "order by acno,cast(gar_custid as unsigned)").getResultList();
                        result = em.createNativeQuery("select a.acno,'' regfoliono,Custfullname,cast(gar_custid as unsigned),ifnull(a.networth,0),'' area,0 salary\n"
                                + "from loan_guarantordetails a,cbs_customer_master_detail b where a.gar_custid = '" + acNo + "' and a.gar_custid = b.customerid \n"
                                + "order by acno,cast(gar_custid as unsigned)").getResultList();
                    }
                }
                if (!result.isEmpty()) {
                    if (reportType.equalsIgnoreCase("Guarantor")) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector vtr = (Vector) result.get(i);
                            GuarantorDetailPojo pojo = new GuarantorDetailPojo();

                            List dtList = em.createNativeQuery("select ifnull(sum(amtdisbursed),0),ifnull(date_format(max(disbursementdt),'%d/%m/%Y'),'') from loandisbursement where acno ='" + vtr.get(0).toString() + "' /*and sno=(select max(SNO) from loandisbursement where acno ='" + vtr.get(0).toString() + "')*/").getResultList();
                            Vector dtVector = (Vector) dtList.get(0);
                            pojo.setDisbursementAmt(new BigDecimal(dtVector.get(0).toString()));
                            pojo.setDisbursementDt(dtVector.get(1).toString());

                            if (inct == 0) {
                                inct = inct + 1;
                                acno = vtr.get(3).toString();
                            } else {
                                if (vtr.get(3).toString().equalsIgnoreCase(acno)) {
                                    inct = inct + 1;
                                    acno = vtr.get(3).toString();
                                } else {
                                    inct = 1;
                                    acno = vtr.get(3).toString();
                                }
                            }
                            // pojo.setGuarantor(guarantor);
                            pojo.setAcNo(vtr.get(3).toString());
                            pojo.setGuarCustId(vtr.get(0).toString());
                            pojo.setOutStanding(new BigDecimal(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(vtr.get(0).toString(), ymdFormat.format(date)))).abs());
                            pojo.setAddress(acTypeMap.get(vtr.get(0).toString().substring(2, 4)));
                            pojo.setMemberName(vtr.get(2).toString());
                            pojo.setCustName(fts.getCustName(vtr.get(0).toString()));
                            pojo.setNetWorth(new BigDecimal(vtr.get(4).toString()));
                            pojo.setArea(vtr.get(5).toString());
                            pojo.setSalary(new BigDecimal(vtr.get(6).toString()));
                            //pojo.setGuaPotential(new BigDecimal(vtr.get(6).toString()).multiply(new BigDecimal(limit)));
                            pojo.setSrlNo(inct);
                            dataList.add(pojo);
                        }
                    } else {
                        for (int i = 0; i < result.size(); i++) {
                            Vector vtr = (Vector) result.get(i);
                            GuarantorDetailPojo pojo = new GuarantorDetailPojo();
                            pojo.setAcNo(vtr.get(0).toString());
                            pojo.setCustName(fts.getCustName(vtr.get(0).toString()));
                            pojo.setGuarCustId(vtr.get(2).toString());
                            pojo.setGuarAcno(vtr.get(1).toString());
                            pojo.setNetWorth(new BigDecimal(Double.parseDouble(vtr.get(3).toString())));
                            pojo.setGuarDate(dmyFormat.format(ymdFormat.parse(vtr.get(4).toString())));
                            dataList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public String bankCode(String orgBrncode) throws ApplicationException {
        String bankCode = "";
        try {
            List bankcode = em.createNativeQuery("select BankName from parameterinfo where brncode ='" + Integer.parseInt(orgBrncode) + "'").getResultList();
            if (!bankcode.isEmpty()) {
                Vector vect = (Vector) bankcode.get(0);
                bankCode = vect.get(0).toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bankCode;
    }

    public List<LoanRenewalSecurityPojo> getLoanRenwalData(String brCode, String acType, String repType, String asOnDt, int days) throws ApplicationException {
        List<LoanRenewalSecurityPojo> dataList = new ArrayList<LoanRenewalSecurityPojo>();
        try {
            List result = new ArrayList();

            if (repType.equalsIgnoreCase("R")) {
                if (brCode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(b.Sanctionlimitdt,'%d/%m/%Y'),cast(b.Sanctionlimit as decimal(14,2)),"
                            + "date_format(c.renewal_date,'%d/%m/%Y'),date_format(date_add(c.renewal_date,INTERVAL d.loan_pd_month month),'%d/%m/%Y'),"
                            + "c.scheme_code from accountmaster a,loan_appparameter b,cbs_loan_borrower_details c,cbs_loan_acc_mast_sec d "
                            + "where a.accttype = '" + acType + "' and accstatus <> 9 and a.acno=b.acno and a.acno=c.acc_no and a.acno=d.acno "
                            + "and c.renewal_date>= '" + asOnDt + "' and c.renewal_date<= date_format( DATE_ADD('" + asOnDt + "', INTERVAL " + days + " DAY),'%Y%m%d')"
                            + "order by a.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(b.Sanctionlimitdt,'%d/%m/%Y'),cast(b.Sanctionlimit as decimal(14,2)),"
                            + "date_format(c.renewal_date,'%d/%m/%Y'),date_format(date_add(c.renewal_date,INTERVAL d.loan_pd_month month),'%d/%m/%Y'),"
                            + "c.scheme_code from accountmaster a,loan_appparameter b,cbs_loan_borrower_details c,cbs_loan_acc_mast_sec d "
                            + "where a.curbrcode = '" + brCode + "' and a.accttype = '" + acType + "' and accstatus <> 9 and a.acno=b.acno and a.acno=c.acc_no "
                            + "and a.acno=d.acno and c.renewal_date>= '" + asOnDt + "' and c.renewal_date<= date_format( DATE_ADD('" + asOnDt + "', INTERVAL " + days + " DAY),'%Y%m%d')"
                            + "order by a.acno").getResultList();
                }
            } else {
                if (brCode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(b.Sanctionlimitdt,'%d/%m/%Y'),cast(b.Sanctionlimit as decimal(14,2)),c.security,date_format(c.matdate,'%d/%m/%Y'),d.scheme_code "
                            + "from accountmaster a,loan_appparameter b ,loansecurity c,cbs_loan_acc_mast_sec d where "
                            + "(c.expirydate is null or c.expirydate ='' or c.expirydate > '" + asOnDt + "')and a.accttype = '" + acType + "' and a.acno=b.acno "
                            + "and a.acno=d.acno and a.acno=c.acno and c.matdate>= '" + asOnDt + "' "
                            + "and c.matdate<= date_format( date_add('" + asOnDt + "', interval " + days + " day),'%y%m%d')"
                            + "order by a.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select a.acno,a.custname,date_format(b.Sanctionlimitdt,'%d/%m/%Y'),cast(b.Sanctionlimit as decimal(14,2)),c.security,date_format(c.matdate,'%d/%m/%Y'),d.scheme_code "
                            + "from accountmaster a,loan_appparameter b ,loansecurity c,cbs_loan_acc_mast_sec d where "
                            + "(c.expirydate is null or c.expirydate ='' or c.expirydate > '" + asOnDt + "') and a.curbrcode = '" + brCode + "' and a.accttype = '" + acType + "' "
                            + "and a.acno=b.acno and a.acno=d.acno and a.acno=c.acno and c.matdate>= '" + asOnDt + "' "
                            + "and c.matdate<= date_format(date_add('" + asOnDt + "', interval " + days + " day),'%y%m%d')"
                            + "order by a.acno").getResultList();
                }
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    LoanRenewalSecurityPojo pojo = new LoanRenewalSecurityPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setSansDt(vtr.get(2).toString());
                    pojo.setSansAmt(Double.parseDouble(vtr.get(3).toString()));
                    pojo.setSecurity(vtr.get(4).toString()); // Renewal Dt or security
                    pojo.setSecurityDt(vtr.get(5).toString()); // Next Renewal Dt or security Dt
                    double bal = common.getBalanceOnDate(vtr.get(0).toString(), asOnDt);
                    pojo.setBalance(Math.abs(bal));
                    pojo.setRoi(Double.parseDouble(loanInterestCalculationBean.getRoiLoanAccount(Math.abs(bal), asOnDt, vtr.get(0).toString())));
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<StatemenrtOfRecoveriesPojo> getAreaWiseRecoveryData(String area, String brCode, String repType, String frDt, String toDt, Integer srNo, String isOverDue) throws ApplicationException {
        List<StatemenrtOfRecoveriesPojo> dataList = new ArrayList<StatemenrtOfRecoveriesPojo>();
        try {

            String premiumCode = "";
            List premiumList = em.createNativeQuery("select Acctcode from parameterinfo_miscincome where Purpose = 'LIP_PREMIUM'").getResultList();
            if (!premiumList.isEmpty()) {
                Vector vtr = (Vector) premiumList.get(0);
                premiumCode = vtr.get(0).toString();
            }

            if (fts.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("P")) {
                List result = em.createNativeQuery("select det.acno,det.custname,det.acctcode,det.AcctDesc,det.bal from "
                        + "(select a.acno,a.custname,d.acctcode,d.AcctDesc,sum(r.cramt-r.dramt) as bal from accountmaster a,customermaster c,"
                        + "accounttypemaster d,customerid e,loan_recon r where a.acno in(select acno from customerid where CustId in"
                        + "(select custid from share_holder where area = '" + area + "' and custid is not null)) and a.acno = e.acno "
                        + "and a.acno = r.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "')"
                        + "and substring(a.acno,5,6)=c.custno and c.brncode = a.curbrcode and substring(a.acno,3,2)=c.actype "
                        + "and a.curbrcode = '" + brCode + "' and a.accttype in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "'))"
                        + "and  a.accttype  = d.acctcode and r.dt<='" + toDt + "' group by r.acno having sum(r.cramt-r.dramt)<> 0 "
                        + "union "
                        + "select a.acno,a.custname,d.acctcode,d.AcctDesc,sum(r.cramt-r.dramt) as bal from accountmaster a,customermaster c,"
                        + "accounttypemaster d,customerid e,ca_recon r where a.acno in(select acno from customerid where CustId in"
                        + "(select custid from share_holder where area = '" + area + "' and custid is not null)) and a.acno = e.acno "
                        + "and a.acno = r.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + toDt + "')"
                        + "and substring(a.acno,5,6)=c.custno and c.brncode = a.curbrcode and substring(a.acno,3,2)=c.actype "
                        + "and a.curbrcode = '" + brCode + "' and a.accttype ='" + premiumCode + "'and  a.accttype  = d.acctcode and r.dt<='" + toDt + "' "
                        + "group by a.acno )det order by det.acno").getResultList();
                if (!result.isEmpty()) {
                    for (int j = 0; j < result.size(); j++) {
                        Vector element = (Vector) result.get(j);
                        StatemenrtOfRecoveriesPojo pojo = new StatemenrtOfRecoveriesPojo();
                        String acno = element.get(0).toString();
                        String custName = element.get(1).toString();
                        String acctCode = element.get(2).toString();
                        String acctDesc = element.get(3).toString();
                        double outstandbal = Double.parseDouble(element.get(4).toString());

                        //List dmdAmtList = em.createNativeQuery("select ifnull(sum(DMD_AMT),0),ifnull(sum(TOT_ADJ_AMT),0) from cbs_loan_dmd_table where acno = '" + acno + "' and LAST_ADJ_DATE between '" + frDt + "' and '" + toDt + "'").getResultList();
                        List dmdAmtList = em.createNativeQuery("select a.dmdAmt,b.adjdmdAmt from"
                                + "(select ifnull(sum(DMD_AMT),0) as dmdAmt from cbs_loan_dmd_table where acno = '" + acno + "' "
                                + "and LAST_ADJ_DATE between '" + frDt + "' and '" + toDt + "' )a,"
                                + "(select ifnull(sum(ADJ_AMT),0) as adjdmdAmt from cbs_loan_dmd_adj_table where acno = '" + acno + "' "
                                + "and ADJ_DATE between '" + frDt + "' and '" + toDt + "')b").getResultList();
                        Vector dmdVector = (Vector) dmdAmtList.get(0);
                        if (Double.parseDouble(dmdVector.get(0).toString()) != 0) {
                            pojo.setLoanAcNo(acno);
                            pojo.setCustName(custName);
                            pojo.setPrinAmt(Double.parseDouble(dmdVector.get(0).toString())); // Individual a/c wise Total demand amount.
                            pojo.setLipAmt(Double.parseDouble(dmdVector.get(1).toString()));  // Individual a/c wise Total recovery amount.
                            pojo.setOutStdBal(Math.abs(outstandbal));
                            pojo.setAcctCode(acctCode);
                            pojo.setAcnoDesc(acctDesc);
                            dataList.add(pojo);
                        }
                    }
                }

            } else {
                List srNoList = new ArrayList();
                if (area.equalsIgnoreCase("ALL")) {
                    srNoList = em.createNativeQuery("select SNO,OFFICEID from cbs_loan_dmd_info where recoverydt between '" + frDt + "' and '" + toDt + "'").getResultList();
                } else {
                    srNoList = em.createNativeQuery("select SNO,OFFICEID from cbs_loan_dmd_info where OFFICEID = '" + area + "' and SNO = " + srNo + "").getResultList();
                }
                if (!srNoList.isEmpty()) {
                    for (int j = 0; j < srNoList.size(); j++) {
                        Vector srVector = (Vector) srNoList.get(j);
                        Integer selNo = Integer.parseInt(srVector.get(0).toString());
                        String srlArea = srVector.get(1).toString();
                        srNo = selNo;
                        area = srlArea;
                        List recoveryDtList = em.createNativeQuery("select date_format(recoverydt,'%Y%m%d') from cbs_loan_dmd_info where OFFICEID = '" + area + "' and sno = " + srNo).getResultList();
                        Vector dtVector = (Vector) recoveryDtList.get(0);
                        String recoveryDt = dtVector.get(0).toString();
                        List result = em.createNativeQuery("select a.acNo,a.intFlow,b.prinFlow,a.intDemand,b.prinDemand from "
                                + "(select acno acNo,dmd_flow_id intFlow,dmd_amt intDemand from cbs_loan_dmd_table where DMD_SRL_NUM = " + srNo + " and dmd_flow_id = 'INDEM')a,"
                                + "(select  acno acNo,dmd_flow_id prinFlow,dmd_amt prinDemand from cbs_loan_dmd_table where DMD_SRL_NUM = " + srNo + " and dmd_flow_id = 'PRDEM')b "
                                + "where a.acno = b.acno "
                                + "union "
                                + "select acno,'' intFlow,dmd_flow_id,'0' intDemand,dmd_amt from cbs_loan_dmd_table where DMD_SRL_NUM = " + srNo + " and dmd_flow_id = 'THDEM' "
                                + "union "
                                + "select acno,'' intFlow,dmd_flow_id,'0' intDemand,dmd_amt from cbs_loan_dmd_table where DMD_SRL_NUM = " + srNo + " and dmd_flow_id = 'RDDEM'").getResultList();
                        List recoverList = new ArrayList();
                        if (!result.isEmpty()) {
                            for (int i = 0; i < result.size(); i++) {
                                Vector vtr = (Vector) result.get(i);
                                StatemenrtOfRecoveriesPojo pojo = new StatemenrtOfRecoveriesPojo();
                                String acNo = vtr.get(0).toString();
                                String intFlow = vtr.get(1).toString();
                                String prinFlow = vtr.get(2).toString();
                                double intDemand = Double.parseDouble(vtr.get(3).toString());
                                double prinDemand = Double.parseDouble(vtr.get(4).toString());

                                String acctCode = acNo.substring(2, 4);
                                String acNat = fts.getAcNatureByCode(acctCode);
                                double outstandbal = 0d;
                                if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                    outstandbal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, toDt));
                                } else {
                                    outstandbal = common.getBalanceOnDate(acNo, toDt);
                                }

                                if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                    recoverList = em.createNativeQuery("select cramt from loan_recon where acno= '" + acNo + "' and dt = '" + recoveryDt + "' and trantype = 2 and details like '%Bulk Recovery%'").getResultList();
                                } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                    recoverList = em.createNativeQuery("select cramt from recon where acno= '" + acNo + "' and dt = '" + recoveryDt + "' and trantype = 2 and details like '%Bulk Recovery%'").getResultList();
                                } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                    recoverList = em.createNativeQuery("select cramt from rdrecon where acno= '" + acNo + "' and dt = '" + recoveryDt + "' and trantype = 2 and details like '%Bulk Recovery%'").getResultList();
                                }

                                double recoveryAmt = 0d, prinRecoveryAmt = 0d, intRecoveryAmt = 0d, totalDemand = 0d, overDue = 0d;
                                if (!recoverList.isEmpty()) {
                                    Vector amtVector = (Vector) recoverList.get(0);
                                    recoveryAmt = Double.parseDouble(amtVector.get(0).toString());
                                }

                                if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                    if (recoveryAmt > intDemand) {
                                        prinRecoveryAmt = recoveryAmt - intDemand;
                                        intRecoveryAmt = intDemand;
                                    } else {
                                        intRecoveryAmt = recoveryAmt;
                                        prinRecoveryAmt = 0;
                                    }
                                    //New code for ovdrdue
                                    totalDemand = intDemand + prinDemand;
                                    if (recoveryAmt < totalDemand) {
                                        overDue = totalDemand - recoveryAmt;
                                    } else {
                                        overDue = 0;
                                    }
                                }
                                if (isOverDue.equalsIgnoreCase("O")) {
                                    if (overDue != 0) {
                                        pojo.setLoanAcNo(acNo);
                                        pojo.setCustName(fts.getCustName(acNo));
                                        if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                            pojo.setIntAmt(intDemand); // Int Dmd Amt
                                            pojo.setPrinAmt(prinDemand); // prin Dmd Amt
                                            pojo.setTotalAmt(intDemand + prinDemand); // total Dmd Amt

                                            pojo.setInstallmentAmt(intRecoveryAmt); // Int Rec Amt
                                            pojo.setTheftFund(prinRecoveryAmt); // prin Rec Amt
                                            pojo.setLoanEmiAmt(intRecoveryAmt + prinRecoveryAmt); // Total Rec Amt
                                            pojo.setOverDue(overDue);
                                        }

                                        if (!acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                            pojo.setTotalAmt(prinDemand); // total Dmd Amt 
                                            pojo.setLoanEmiAmt(recoveryAmt); // Total Rec Amt

                                        }
                                        pojo.setOutStdBal(Math.abs(outstandbal));
                                        pojo.setAcctCode(acctCode);
                                        pojo.setAcnoDesc(common.getAcctDecription(acctCode));
                                        pojo.setRecoveryDt(recoveryDt.substring(6, 8) + "/" + recoveryDt.substring(4, 6) + "/" + recoveryDt.substring(0, 4));
                                        dataList.add(pojo);
                                    }
                                } else {
                                    pojo.setLoanAcNo(acNo);
                                    pojo.setCustName(fts.getCustName(acNo));
                                    if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                        pojo.setIntAmt(intDemand); // Int Dmd Amt
                                        pojo.setPrinAmt(prinDemand); // prin Dmd Amt
                                        pojo.setTotalAmt(intDemand + prinDemand); // total Dmd Amt
                                        pojo.setInstallmentAmt(intRecoveryAmt); // Int Rec Amt
                                        pojo.setTheftFund(prinRecoveryAmt); // prin Rec Amt
                                        pojo.setLoanEmiAmt(intRecoveryAmt + prinRecoveryAmt); // Total Rec Amt
                                        pojo.setOverDue(overDue);
                                    }

                                    if (!acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                        pojo.setTotalAmt(prinDemand); // total Dmd Amt 
                                        pojo.setLoanEmiAmt(recoveryAmt); // Total Rec Amt
                                    }
                                    pojo.setOutStdBal(Math.abs(outstandbal));
                                    pojo.setAcctCode(acctCode);
                                    pojo.setAcnoDesc(common.getAcctDecription(acctCode));
                                    pojo.setRecoveryDt(recoveryDt.substring(6, 8) + "/" + recoveryDt.substring(4, 6) + "/" + recoveryDt.substring(0, 4));
                                    dataList.add(pojo);
                                }

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public double getCustIdWiseBal(String brCode, String custid, String dt) throws ApplicationException {
        double bal = 0d;
        try {
            List balList = em.createNativeQuery("select ifnull(sum(b.bal),0) from (select sum(r.cramt-r.dramt) as bal "
                    + "from accountmaster a,customerid e,loan_recon r where a.acno in(select acno from customerid where CustId ='" + custid + "')"
                    + "and a.acno = e.acno and a.acno = r.acno and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + dt + "')"
                    + "and a.curbrcode = '" + brCode + "' and a.accttype in(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "')) "
                    + "and dt<='" + dt + "' group by r.acno having sum(r.cramt-r.dramt)<> 0 )b").getResultList();

            if (!balList.isEmpty()) {
                Vector vtr = (Vector) balList.get(0);
                bal = Math.abs(Double.parseDouble(vtr.get(0).toString()));
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return bal;
    }

    public List<OverdueEmiReportPojo> getGuarantorOverdueData(String brCode, String asOnDt) throws ApplicationException {
        List<OverdueEmiReportPojo> dataList = new ArrayList<OverdueEmiReportPojo>();

        List result = new ArrayList();
        try {
            int isExceessEmi = common.isExceessEmi(asOnDt);
            String bnkIdenty = fts.getCodeFromCbsParameterInfo("GRACE_PD_APP_IN_OVERDUE");
            if (brCode.equalsIgnoreCase("0A")) {
                //result = em.createNativeQuery("select gar_custid,acno from loan_guarantordetails where date_format(trantime,'%Y%m%d')<='"+asOnDt+"'  order by gar_custid").getResultList();
                result = em.createNativeQuery("select gar_custid,acno,b.custName,c.regfoliono from loan_guarantordetails a,cbs_customer_master_detail b,"
                        + "share_holder c where date_format(a.trantime,'%Y%m%d')<='" + asOnDt + "' and a.gar_custid = b.customerid and a.gar_custid = c.custid order by gar_custid").getResultList();
            } else {
                result = em.createNativeQuery("select gar_custid,acno,b.custName,c.regfoliono from loan_guarantordetails a,cbs_customer_master_detail b,"
                        + "share_holder c where substring(acno,1,2)='" + brCode + "' and date_format(a.trantime,'%Y%m%d')<='" + asOnDt + "' "
                        + "and a.gar_custid = b.customerid and a.gar_custid = c.custid order by gar_custid").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    String custId = vtr.get(0).toString();
                    String acNo = vtr.get(1).toString();
                    String folioCustName = vtr.get(2).toString();
                    String folioNo = vtr.get(3).toString();

                    List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("", acNo, 1, 200, asOnDt, brCode, bnkIdenty, 0, isExceessEmi, null, 0);
                    for (OverdueEmiReportPojo pojo : overdueEmiList) {
                        if (Math.abs(pojo.getBalance().doubleValue()) > 0) {
                            pojo.setCustId(folioNo);
                            pojo.setFolioName(folioCustName);
                            dataList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    @Override
    public List<LoanAccStmPojo> loanAccountStatementBifurficationPrinInt(String acno, String frmdt, String todt, String brnCode) throws ApplicationException {
        List<LoanAccStmPojo> result = new ArrayList<>();
        try {
            double openingbalance = 0, balance = 0, debit = 0, credit = 0, prinBalance = 0, intBalance = 0,
                    openingPrin = 0, openingInt = 0, totalBalance = 0,
                    prinRecovery = 0, intRecovery = 0;
            String status = "", brQuery = "", recoveryConcept = "", clDt = "";
            List opList = new ArrayList();
            List opList1 = new ArrayList();
            List vr = new ArrayList();
            if (!(brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90"))) {
                if (!brnCode.equalsIgnoreCase(acno.substring(0, 2))) {
                    throw new ApplicationException("Please fill self branch account only!!");
                }
            }
            vr = em.createNativeQuery("select acno from accountmaster where CUST_TYPE= 'ST' and acno= '" + acno + "'").getResultList();
            if (!vr.isEmpty()) {
                status = "Staff";
            }

            List cldtList = em.createNativeQuery("select date_format(ifnull(ClosingDate,''),'%d/%m/%Y') from accountmaster where acno = '" + acno + "' and  ClosingDate<='" + todt + "'").getResultList();
            if (!cldtList.isEmpty()) {
                Vector tempVector = (Vector) cldtList.get(0);
                clDt = tempVector.get(0).toString();
            }
            String npaStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(acno, todt);
            int picflag = 0;
            if (!npaStatus.equalsIgnoreCase("STANDARD")) {
                opList = em.createNativeQuery("select sum(pOpBal) from("
                        + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acno + "' and dt< '" + frmdt + "'  "
                        + "union all "
                        + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acno + "' and dt< '" + frmdt + "' "
                        + ")a").getResultList();
                Vector vtr1 = (Vector) opList.get(0);
                openingPrin = Double.parseDouble(vtr1.get(0).toString());

                opList1 = em.createNativeQuery("select sum(intOpBal) from ("
                        + "select ifnull(sum(cramt-Dramt),0) intOpBal from npa_recon where acno = '" + acno + "' and dt< '" + frmdt + "' )b").getResultList();
                Vector vtr2 = (Vector) opList1.get(0);
                openingInt = (Double.parseDouble(vtr2.get(0).toString()));
            } else if (status.equalsIgnoreCase("Staff")) {
                opList = em.createNativeQuery("select sum(pOpBal) from("
                        + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acno + "' and dt< '" + frmdt + "' and trantype <> 8 "
                        + "union all "
                        + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acno + "' and dt< '" + frmdt + "' and trantype <> 8"
                        + ")a").getResultList();
                Vector vtr1 = (Vector) opList.get(0);
                openingPrin = Double.parseDouble(vtr1.get(0).toString());
                if (openingPrin > 0) {
                    picflag = 1;
                }

                opList1 = em.createNativeQuery("select sum(intOpBal) from ("
                        + "select ifnull(sum(cramt-Dramt),0) intOpBal from loan_recon where acno = '" + acno + "' and dt< '" + frmdt + "' and trantype = 8 "
                        + "union all "
                        + "select ifnull(sum(cramt-Dramt),0) intOpBal from ca_recon where acno = '" + acno + "' and dt< '" + frmdt + "' and trantype = 8 "
                        + ")b").getResultList();
                Vector vtr2 = (Vector) opList1.get(0);
                openingInt = (Double.parseDouble(vtr2.get(0).toString()));
                if (picflag == 1) {
                    openingInt = openingInt + openingPrin;
                }

            }

            if (!status.equalsIgnoreCase("Staff")) {
                if (npaStatus.equalsIgnoreCase("STANDARD")) {
                    opList = em.createNativeQuery("select sum(pOpBal) from("
                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from loan_recon where acno = '" + acno + "' and dt< '" + frmdt + "' "
                            + "union all "
                            + "select ifnull(sum(cramt-Dramt),0) pOpBal from ca_recon where acno = '" + acno + "' and dt< '" + frmdt + "' "
                            + ")a").getResultList();
                    Vector vtr1 = (Vector) opList.get(0);
                    openingPrin = Double.parseDouble(vtr1.get(0).toString());
                }
            }

            double instamt = 0d;
            Vector tempVector;
            List tempList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where acno='" + acno + "'  and status='unpaid' order by duedt limit 1").getResultList();
            if (!tempList.isEmpty()) {
                tempVector = (Vector) tempList.get(0);
                instamt = Double.parseDouble(tempVector.get(0).toString());
            } else {
                tempList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where acno='" + acno + "' order by duedt desc limit 1").getResultList();
                if (!tempList.isEmpty()) {
                    tempVector = (Vector) tempList.get(0);
                    instamt = Double.parseDouble(tempVector.get(0).toString());
                }
            }

            List list = new ArrayList();
            if (status.equalsIgnoreCase("Staff")) {
                list = em.createNativeQuery("select a.dt,a.valuedt, a.type, a.acno, a.dramt, a.cramt,  a.trantype, a.ty, a.trsno, a.recno, b.recover ,a.details from "
                        + "(select acno, dt, dramt, cramt, valuedt, '1.PRIN' as type, trantype, ty, trsno, recno,details from ca_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "' and trantype <>8 "
                        + "union all  "
                        + "select acno, dt, dramt, cramt, valuedt, '1.PRIN' as type, trantype, ty, trsno, recno,details from loan_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "' and trantype <>8 "
                        + "union all "
                        + "select acno, dt, dramt, cramt, valuedt, '2.INT'   as type, trantype, ty, trsno, recno,details from loan_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "' and trantype =8 "
                        + "union all "
                        + "select acno, dt, dramt, cramt, valuedt, '2.INT'   as type, trantype, ty, trsno, recno,details from npa_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "' and trantype =8) a, "
                        + "loan_appparameter b  where a.acno = b.acno " + brQuery + " order by dt, trsno, recno, type").getResultList();
            } else {
//                list = em.createNativeQuery("select a.dt, a.valuedt, a.type, a.acno, a.dramt, a.cramt,  a.trantype, a.ty, a.trsno, a.recno, b.recover ,a.details from"
//                        + " (select acno, dt, dramt, cramt, valuedt, '1.Loan' as type, trantype, ty, trsno, recno,details from ca_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "'"
//                        + " union all"
//                        + " select acno, dt, dramt, cramt, valuedt, '1.Loan' as type, trantype, ty, trsno, recno,details from loan_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "'"
//                        + " union all"
//                        + " select acno, dt, dramt, cramt, valuedt, '2.NPA'   as type, trantype, ty, trsno, recno,details from npa_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "') a, loan_appparameter b "
//                        + " where a.acno = b.acno  " + brQuery + " order by dt, trsno, recno, type").getResultList();
                list = em.createNativeQuery("select a.dt, a.valuedt, a.type, a.acno, a.dramt, a.cramt,  a.trantype, a.ty, a.trsno, a.recno, b.recover ,a.details  from  "
                        + "(select aa.* from  "
                        + "(select aa.* from  "
                        + "(select acno, dt, dramt, cramt, valuedt, '1.Loan' as type, trantype, ty, trsno, recno,details, trandesc from ca_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "'   "
                        + "union all  "
                        + "select acno, dt, dramt, cramt, valuedt, '1.Loan' as type, trantype, ty, trsno, recno,details, trandesc from loan_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "' ) aa  "
                        + "left join   "
                        + "(select acno, dt, trsno from ca_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "' and (details like 'NPA RECOVERY%' OR  details like 'INTT. REC FOR MEM%') group by acno, dt, trsno "
                        + "union all  "
                        + "select acno, dt, trsno  from loan_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "'  and (details like 'NPA RECOVERY%' OR  details like 'INTT. REC FOR MEM%') group by acno, dt, trsno)bb "
                        + "on aa.acno = bb.acno and aa.dt = bb.dt and aa.trsno = bb.trsno "
                        + "where bb.trsno is null  group by aa.acno, aa.dt, aa.type, aa.trsno, aa.recno) aa  "
                        + "union all "
                        + "select bb.* from  "
                        + "(select a.acno, a.dt, 0 as dramt, sum(a.cramt - a.dramt ) as cramt, a.valuedt, a.type, a.trantype, a.ty, a.trsno, a.recno,a.details, a.trandesc  from  "
                        + "(select acno, dt, dramt, cramt, valuedt, '1.Loan' as type, trantype, ty, trsno, recno,details, trandesc from ca_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "' "
                        + "union all  "
                        + "select acno, dt, dramt, cramt, valuedt, '1.Loan' as type, trantype, ty, trsno, recno,details, trandesc from loan_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "') a,  "
                        + "(select acno, dt, trsno from ca_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "' and (details like 'NPA RECOVERY%' OR  details like 'INTT. REC FOR MEM%') group by acno, dt, trsno "
                        + "union all  "
                        + "select acno, dt, trsno  from loan_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "'  and (details like 'NPA RECOVERY%' OR  details like 'INTT. REC FOR MEM%') group by acno, dt, trsno)b  "
                        + "where a.acno = b.acno and a.dt = b.dt and a.trsno = b.trsno group by a.acno, a.dt, a.trsno) bb   "
                        + "union all  "
                        + "select acno, dt, dramt, cramt, valuedt, '2.NPA'   as type, trantype, ty, trsno, recno,details, trandesc from npa_recon where acno = '" + acno + "' and dt between '" + frmdt + "'and '" + todt + "') a, loan_appparameter b  "
                        + "where a.acno = b.acno   order by dt, type, trsno, recno").getResultList();
            }

            if (!list.isEmpty()) {
                int flag = 0;
                int piFlag = 0;
                LoanAccStmPojo pojo = new LoanAccStmPojo();
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    recoveryConcept = vtr.get(10).toString();
                    if (i == 0) {
                        pojo.setDate((y_m_d_h_m_sFormat.parse(vtr.get(0).toString())));
                        pojo.setValueDt(y_m_d_h_m_sFormat.parse(vtr.get(1).toString()));
                        pojo.setClosingDate(clDt);
                        if (vtr.get(2).toString().equalsIgnoreCase("1.PRIN") || vtr.get(2).toString().equalsIgnoreCase("1.Loan")) {
                            pojo.setpDebit(new BigDecimal(0.0));
                            pojo.setpCredit(new BigDecimal(0.0));
                            pojo.setpBalance(new BigDecimal(0.0));
                            pojo.setIntDebit(new BigDecimal(0.0));
                            pojo.setIntCredit(new BigDecimal(0.0));
                            pojo.setIntBalance(new BigDecimal(0.0));
                        } else {
                            pojo.setpDebit(new BigDecimal(0.0));
                            pojo.setpCredit(new BigDecimal(0.0));
                            pojo.setpBalance(new BigDecimal(0.0));
                            pojo.setIntDebit(new BigDecimal(0.0));
                            pojo.setIntCredit(new BigDecimal(0.0));
                            pojo.setIntBalance(new BigDecimal(0.0));
                        }
                        if (picflag == 1) {
                            pojo.setpBalance(new BigDecimal(0));
                        } else {
                            pojo.setpBalance(new BigDecimal(openingPrin));
                        }
                        pojo.setIntBalance(new BigDecimal(openingInt));
                        pojo.setParticulars("OPENING BALANCE :");
                        pojo.setAcNo(vtr.get(3).toString());
                        pojo.setPrinOpBal(new BigDecimal(openingPrin));
                        pojo.setIntOpBal(new BigDecimal(openingInt));
                        openingbalance = openingPrin + openingInt;
                        pojo.setBalance(new BigDecimal(openingbalance));
                        pojo.setAmtInst(new BigDecimal(instamt));
                        totalBalance = (pojo.getpBalance() == null ? 0d : pojo.getpBalance().doubleValue()) + (pojo.getIntBalance() == null ? 0d : pojo.getIntBalance().doubleValue());
                        pojo.setTotalBalance(new BigDecimal(Math.abs(totalBalance)));
                        result.add(pojo);
                    }

                    pojo = new LoanAccStmPojo();
                    debit = Double.parseDouble(vtr.get(4).toString());
                    credit = Double.parseDouble(vtr.get(5).toString());
//                    if (vtr.get(0).toString().equalsIgnoreCase("2018-02-02 00:00:00.0")) {
//                        System.out.println("");
//                    }
                    pojo.setDate((y_m_d_h_m_sFormat.parse(vtr.get(0).toString())));
                    pojo.setValueDt(y_m_d_h_m_sFormat.parse(vtr.get(1).toString()));

                    if (picflag == 1) {
                        pojo.setpDebit(new BigDecimal(0));
                        pojo.setpCredit(new BigDecimal(0));
                        pojo.setpBalance(new BigDecimal(0));
                        openingInt = openingInt - debit + credit;
                        pojo.setIntDebit(new BigDecimal(debit));
                        pojo.setIntCredit(new BigDecimal(credit));
                        pojo.setIntBalance(new BigDecimal(openingInt));
                    } else {
                        if (vtr.get(2).toString().equalsIgnoreCase("1.PRIN") || vtr.get(2).toString().equalsIgnoreCase("1.Loan")) {
                            openingPrin = openingPrin - debit + credit;
                            if (recoveryConcept.equalsIgnoreCase("CIP")) {
                                pojo.setpDebit(new BigDecimal(debit));
                                pojo.setpCredit(new BigDecimal(credit));
                                pojo.setpBalance(new BigDecimal(openingPrin));
                                pojo.setIntDebit(new BigDecimal(0.0));
                                pojo.setIntCredit(new BigDecimal(0.0));
                                pojo.setIntBalance(new BigDecimal(openingInt));
                            } else {
                                if (openingPrin == 0) {
                                    if (flag == 0) {
                                        pojo.setpDebit(new BigDecimal(debit));
                                        pojo.setpCredit(new BigDecimal(credit));
                                        pojo.setpBalance(new BigDecimal(openingPrin));

                                        pojo.setIntDebit(new BigDecimal(0));
                                        pojo.setIntCredit(new BigDecimal(0));
                                        pojo.setIntBalance(new BigDecimal(openingInt));
                                        flag = 1;
                                    } else {
                                        pojo.setpDebit(new BigDecimal(0));
                                        pojo.setpCredit(new BigDecimal(0));
                                        pojo.setpBalance(new BigDecimal(0));
                                        pojo.setIntDebit(new BigDecimal(debit));
                                        pojo.setIntCredit(new BigDecimal(credit));
                                        pojo.setIntBalance(new BigDecimal(openingInt));
                                    }
                                } else {
                                    if (flag == 1) {
                                        pojo.setpDebit(new BigDecimal(0));
                                        pojo.setpCredit(new BigDecimal(0));
                                        pojo.setpBalance(new BigDecimal(0));
                                        openingInt = openingInt - debit + credit;
                                        pojo.setIntDebit(new BigDecimal(debit));
                                        pojo.setIntCredit(new BigDecimal(credit));
                                        pojo.setIntBalance(new BigDecimal(openingInt));
                                    } else {
                                        if (openingPrin > 0) {
                                            if (piFlag == 0) {
                                                pojo.setpDebit(new BigDecimal(debit));
                                                pojo.setpCredit(new BigDecimal(credit - openingPrin));
                                                pojo.setpBalance(new BigDecimal(0));

                                                credit = openingPrin;
                                                openingInt = openingInt - debit + credit;

                                                pojo.setIntDebit(new BigDecimal(0));
                                                pojo.setIntCredit(new BigDecimal(credit));
                                                pojo.setIntBalance(new BigDecimal(openingInt));
                                                piFlag = 1;
                                            } else {
                                                pojo.setpDebit(new BigDecimal(0));
                                                pojo.setpCredit(new BigDecimal(0));
                                                pojo.setpBalance(new BigDecimal(0));

                                                openingInt = openingInt - debit + credit;

                                                pojo.setIntDebit(new BigDecimal(0));
                                                pojo.setIntCredit(new BigDecimal(credit));
                                                pojo.setIntBalance(new BigDecimal(openingInt));
                                            }
                                        } else {
                                            pojo.setpDebit(new BigDecimal(debit));
                                            pojo.setpCredit(new BigDecimal(credit));
                                            pojo.setpBalance(new BigDecimal(openingPrin));

                                            pojo.setIntDebit(new BigDecimal(0));
                                            pojo.setIntCredit(new BigDecimal(0));
                                            pojo.setIntBalance(new BigDecimal(openingInt));
                                        }
                                    }
                                }
                            }
                        } else {
                            openingInt = openingInt - debit + credit;
                            pojo.setpDebit(new BigDecimal(0.0));
                            pojo.setpCredit(new BigDecimal(0.0));
                            if (flag == 1) {
                                pojo.setpBalance(new BigDecimal(0));
                            } else {
                                if (openingPrin > 0) {
                                    openingPrin = 0;
                                }
                                pojo.setpBalance(new BigDecimal(openingPrin));
                            }
                            pojo.setIntDebit(new BigDecimal(debit));
                            pojo.setIntCredit(new BigDecimal(credit));
                            pojo.setIntBalance(new BigDecimal(openingInt));
                        }
                    }
                    openingbalance = openingbalance + credit - debit;
                    pojo.setParticulars(vtr.get(11).toString());
                    pojo.setAcNo(vtr.get(3).toString());
                    pojo.setBalance(new BigDecimal(openingbalance));
                    totalBalance = (pojo.getpBalance() == null ? 0d : pojo.getpBalance().doubleValue()) + (pojo.getIntBalance() == null ? 0d : pojo.getIntBalance().doubleValue());
                    pojo.setTotalBalance(new BigDecimal(Math.abs(totalBalance)));
                    result.add(pojo);
                }
            } else {
                LoanAccStmPojo pojo = new LoanAccStmPojo();
                pojo.setpDebit(new BigDecimal(0));
                pojo.setpCredit(new BigDecimal(0));
                pojo.setpBalance(new BigDecimal(openingPrin));

                pojo.setIntDebit(new BigDecimal(0.0));
                pojo.setIntCredit(new BigDecimal(0.0));
                pojo.setIntBalance(new BigDecimal(openingInt));
                pojo.setDate(ymdFormat.parse(todt));
                pojo.setClosingDate(clDt);
                //pojo.setValueDt(y_m_d_h_m_sFormat.parse(vtr.get(1).toString()));
                totalBalance = (pojo.getpBalance() == null ? 0d : pojo.getpBalance().doubleValue()) + (pojo.getIntBalance() == null ? 0d : pojo.getIntBalance().doubleValue());
                pojo.setTotalBalance(new BigDecimal(Math.abs(totalBalance)));
                pojo.setAmtInst(new BigDecimal(instamt));
                result.add(pojo);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return result;
    }

    public List<LoanBorrowerModPojo> getLoanBorrowerDtl(String branchCode, String acNature, String accType, String fromDate, String toDate, String acno) throws ApplicationException {
        List<LoanBorrowerModPojo> list1 = new ArrayList<LoanBorrowerModPojo>();
        String accno;
        List<LoanBorrowerModPojo> finalList = new ArrayList<LoanBorrowerModPojo>();
        List<LoanBorrowerModPojo> tempList = new ArrayList<LoanBorrowerModPojo>();
        if (!acno.equalsIgnoreCase("")) {
            accno = acno;
        } else {
            accno = "";
        }
        String acnoQuery1 = "";
        String acnoQuery2 = "";
        if (accno.equalsIgnoreCase("")) {
            acnoQuery1 = "a.ACC_NO in (select distinct b.ACC_NO from cbs_loan_borrower_details_history b order by b.ACC_NO ,b.cust_id ) and";
            acnoQuery2 = "";
        } else {
            acnoQuery1 = "a.ACC_NO ='" + accno + "' and";
            acnoQuery2 = "a.ACC_NO ='" + accno + "' and";
        }

        String branchCodeQuery = "";
        String accTypeQuery = "";
        if (!(branchCode.equalsIgnoreCase("0A") || branchCode.equalsIgnoreCase("90"))) {
            branchCodeQuery = " substring(a.acc_no,1,2) = '" + branchCode + "' and ";
        } else {
            Integer bankCode = fts.getCodeForReportName("LOAN_AT_HO");
            if (bankCode == 1 && branchCode.equalsIgnoreCase("90")) {
                branchCodeQuery = " substring(a.acc_no,1,2) = '" + branchCode + "' and ";
            }
        }
        String notInQuery = "";
        List notInAcCode = common.getCode("ACNO_NOT_IN_MIS");
        if (!notInAcCode.isEmpty()) {
            String vect = notInAcCode.get(0).toString();
            notInQuery = " and acctcode not in ('" + notInAcCode.get(0).toString() + "')";
        }
        if (acNature.equalsIgnoreCase("0")) {
            accTypeQuery = " substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') " + notInQuery + " ) and ";
        } else {
            if (!(acNature.equalsIgnoreCase("0") || acNature.equalsIgnoreCase("")) && (accType.equalsIgnoreCase("0") || accType.equalsIgnoreCase(""))) {
                accTypeQuery = " substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and CrDbFlag in('B','D') " + notInQuery + " ) and ";
            } else {
                accTypeQuery = " substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctcode in ('" + accType + "') ) and ";
            }
        }
        try {
            List list = em.createNativeQuery("select \n"
                    + "ifnull(CUST_ID,'') as custId,\n"
                    + "ifnull(ACC_NO,'') as Accno , ifnull(SECTOR,'') as sector,ifnull(sub_sector,'') as subSector,\n"
                    + "ifnull(PURPOSE_OF_ADVANCE,'') as purposeOfAdvance,ifnull(MODE_OF_ADVANCE,'') as modeOfAdvance\n"
                    + ",ifnull(TYPE_OF_ADVANCE,'') as typeOfAdvance,ifnull(SECURED,'') as secured,ifnull(GUARANTEE_COVER,'') as guaranteeCover\n"
                    + ",ifnull(INDUSTRY_TYPE,'') as industryType,ifnull(SICKNESS,'') as sickness,ifnull(EXPOSURE,'') as exposure\n"
                    + ",ifnull(RESTRUCTURING,'') as restructuring,ifnull(SANCTION_LEVEL,'') as sanctionLevel,ifnull(SANCTIONING_AUTHORITY,'') as sanctioningAuthority\n"
                    + ",ifnull(INTEREST_TABLE,'') as interestTable,ifnull(INTEREST_TYPE,'') as interestType,ifnull(SCHEME_CODE,'') as schemeCode\n"
                    + ",ifnull(NPA_CLASSIFICATION,'') as npaClassification,ifnull(COURTS,'') as courts,ifnull(MODE_OF_SETTLEMENT,'') as modeOfSettlement,\n"
                    + "ifnull(DEBT_WAIVER_REASON,'') as debtWaiverReason,ifnull(ASSET_CLASS_REASON,'') as assetClassReason,ifnull(POPULATION_GROUP,'') as populationGroup\n"
                    + ",ifnull(CREATED_BY_USER_ID,'') as createdByUserId,Date_format(ifnull(CREATION_DATE,''),'%Y%m%d') as creationDate,ifnull(LAST_UPDATED_BY_USER_ID,'') as lastUpdatedByUserId , \n"
                    + "ifnull(LAST_UPDATED_DATE,CREATION_DATE)  as lastUpdateDate,ifnull(TOTAL_MODIFICATIONS,'') as totalModification,ifnull(NET_WORTH,'') as netWorth\n"
                    + ",ifnull(MARGIN_MONEY,'') as marginMoney, Date_format(ifnull(DOCUMENT_DATE,''),'%Y%m%d')  as documentDate, Date_format(ifnull(RENEWAL_DATE,''),'%Y%m%d')  as renewalDate ,\n"
                    + "ifnull(LOAN_DURATION,'') as loanDuration,Date_format(ifnull(DOCUMENT_EXP_DATE,''),'%Y%m%d')  as documentExpDate,ifnull(RELATION,'') as relation\n"
                    + ",ifnull(SANCTION_AMT,'') as sactionAmt,ifnull(DRAWING_POWER_INDICATOR,'') as drawingPowerIndicator,ifnull(MONTHLY_INCOME,'') as monthlyIncome\n"
                    + ",ifnull(APPLICANT_CATEGORY,'') as applicantCategory,ifnull(CATEGORY_OPT,'') as categoryOpt,ifnull(MINOR_COMMUNITY,'') as minorCommunity,ifnull(REMARKS,'') as remarks,ifnull(AUTH,'') as auth,ifnull(REL_NAME,'') as relname\n"
                    + ",ifnull(REL_WITH_AC_HOLDER,'') as relWithAcHolder,ifnull(EXP_CAT,'') as expCat,ifnull(EXP_CAT_PUR,'') as expCatPur,ifnull(dir_cust_id,'') as dirCustId\n"
                    + ",ifnull(retirement_age,'') as retirementAge,ifnull(industry,'') as industry,ifnull(groupID,'') as groupId,ifnull(groupCustID,'') as groupCustId  ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '182'  and REF_CODE = SECTOR),'') as sectorDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '183'  and REF_CODE = sub_sector  and '20180630'  between eff_fr_dt and eff_to_dt  ),'') as subSectorDesc,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '190'  and REF_CODE = PURPOSE_OF_ADVANCE),'') as purposeOfAdvanceDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '185'  and REF_CODE = MODE_OF_ADVANCE),'') as modeOfAdvanceDesc , \n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '186'  and REF_CODE = TYPE_OF_ADVANCE),'') as typeOfAdvanceDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '187'  and REF_CODE = SECURED),'') as securedDesc,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '188'  and REF_CODE = GUARANTEE_COVER),'') as guarnteeCoverDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '184'  and REF_CODE = SICKNESS  and '20180630'  between eff_fr_dt and eff_to_dt ),'') as purOfAdvDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '191'  and REF_CODE = EXPOSURE),'') as exposureDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '192'  and REF_CODE = restructuring),'') as restructuringDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '193'  and REF_CODE = sanctionLevel),'') as sanctionLevelDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '194'  and REF_CODE = sanctioningAuthority),'') as sanctioningAuthorityDesc ,\n"
                    + "ifnull((select SCHEME_DESCRIPTION from cbs_scheme_general_scheme_parameter_master where SCHEME_CODE= interestTable),'') as interestTableDesc,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '202'  and REF_CODE = INTEREST_TYPE),'') as intTypeDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '230'  and REF_CODE = EXP_CAT),'') as exposureCategoryDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '203'  and REF_CODE = SCHEME_CODE),'') as schemeCodeDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '195'  and REF_CODE = npaClassification),'') as npaClassificationDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '196'  and REF_CODE = courts),'') as courtsDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '197'  and REF_CODE = modeOfSettlement),'') as modeOfSettlementDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '198'  and REF_CODE = debtWaiverReason),'') as debtWaiverReasonDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '199'  and REF_CODE = assetClassReason),'') as assetClassReasonDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '200'  and REF_CODE = populationGroup),'') as populationGroupDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = RELATION),'') as relationDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '232'  and REF_CODE = drawingPowerIndicator),'') as drawingPowerIndicatorDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '208'  and REF_CODE = APPLICANT_CATEGORY),'') as applicantCategoryDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '209'  and REF_CODE = CATEGORY_OPT),'') as categoryOptDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '204'  and REF_CODE = MINOR_COMMUNITY),'') as minorCommunityDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = relname),'') as relnameDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '004'  and REF_CODE = REL_WITH_AC_HOLDER),'') as relOwnerDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '231'  and REF_CODE = expCat),'') as exposureCategoryDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '231'  and REF_CODE = EXP_CAT_PUR),'') as exposureCategoryPurposeDesc ,\n"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '445'  and REF_CODE = ifnull(industry,'0')),'') as industryDesc  ,\n"
                    + "(case groupId when 1 then 'OWN AS A GROUP' when 2 then 'UNDER A GROUP' when 0 then '' when null then '' end ) as groupIdDesc\n"
                    + "from\n"
                    + "(\n"
                    + "select * from cbs_loan_borrower_details a  where " + branchCodeQuery + "  " + accTypeQuery + " " + acnoQuery1 + "  Date_format(ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE),'%Y%m%d') between '" + fromDate + "' and '" + toDate + "'\n"
                    + "union all\n"
                    + "select * from cbs_loan_borrower_details_history a where " + branchCodeQuery + "  " + accTypeQuery + " " + acnoQuery2 + " Date_format(ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE),'%Y%m%d') between '" + fromDate + "' and '" + toDate + "'\n"
                    + ")as table1 order by cust_id,ACC_NO,LAST_UPDATED_DATE").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    LoanBorrowerModPojo lbmp = new LoanBorrowerModPojo();
                    lbmp.setCustId(vec.get(0).toString());
                    lbmp.setAccno(vec.get(1).toString());
                    lbmp.setSector(vec.get(53).toString());
                    lbmp.setSubSector(vec.get(54).toString());
                    lbmp.setPurposeOfAdvance(vec.get(55).toString());
                    lbmp.setModeOfAdvance(vec.get(56).toString());
                    lbmp.setTypeOfAdvance(vec.get(57).toString());
                    lbmp.setSecured(vec.get(58).toString());
                    lbmp.setGuaranteeCover(vec.get(59).toString());
                    lbmp.setIndustryType(vec.get(9).toString());
                    lbmp.setSickness(vec.get(60).toString());
                    lbmp.setExposure(vec.get(61).toString());
                    lbmp.setRestructuring(vec.get(62).toString());
                    lbmp.setSanctionLevel(vec.get(63).toString());
                    lbmp.setSanctioningAuthority(vec.get(64).toString());
                    lbmp.setInterestTable(vec.get(65).toString());
                    lbmp.setInterestType(vec.get(66).toString());
                    lbmp.setSchemeCode(vec.get(68).toString());
                    lbmp.setNpaClassification(vec.get(69).toString());
                    lbmp.setCourts(vec.get(70).toString());
                    lbmp.setModeOfSettlement(vec.get(71).toString());
                    lbmp.setDebtWaiverReason(vec.get(72).toString());
                    lbmp.setAssetClassReason(vec.get(73).toString());
                    lbmp.setPopulationGroup(vec.get(74).toString());
                    lbmp.setCreatedByUserId(vec.get(24).toString());
                    lbmp.setCreationDate(dmyFormat.format(ymdFormat.parse(vec.get(25).toString())));
                    lbmp.setLastUpdatedByUserId(vec.get(26).toString());
                    lbmp.setLastUpdatedDate(y_m_dFormat.format(y_m_dFormat.parse(vec.get(27).toString())));
                    lbmp.setTotalModification(Integer.parseInt(vec.get(28).toString()));
                    lbmp.setNetWorth(Double.valueOf(vec.get(29).toString()));
                    lbmp.setMarginMoney(Double.valueOf(vec.get(30).toString()));
                    lbmp.setDocumentDate(dmyFormat.format(ymdFormat.parse(vec.get(31).toString())));
                    lbmp.setRenewalDate(dmyFormat.format(ymdFormat.parse(vec.get(32).toString())));
                    lbmp.setLoanDuration(vec.get(33).toString());
                    lbmp.setDocumentExpDate(dmyFormat.format(ymdFormat.parse(vec.get(34).toString())));
                    lbmp.setRelation(vec.get(75).toString());
                    lbmp.setSanctionAmt(Double.valueOf(vec.get(36).toString()));
                    lbmp.setDrawingPowerIndicator(vec.get(76).toString());
                    lbmp.setMonthlyIncome(Double.valueOf(vec.get(38).toString()));
                    lbmp.setApplicantCatagory(vec.get(77).toString());
                    lbmp.setCategoryOpt(vec.get(78).toString());
                    lbmp.setMinorCommunity(vec.get(79).toString());
                    lbmp.setRemarks(vec.get(42).toString());
                    lbmp.setAuth(vec.get(43).toString());
                    lbmp.setRelName(vec.get(80).toString());
                    lbmp.setRelWithAcHolder(vec.get(82).toString());
                    lbmp.setExpCat(vec.get(67).toString());
                    lbmp.setExpCatPur(vec.get(83).toString());
                    lbmp.setDirCustId(vec.get(48).toString());
                    lbmp.setRetirementAge(vec.get(49).toString());
                    lbmp.setIndustry(vec.get(84).toString());
                    lbmp.setGroupId(vec.get(85) != null ? vec.get(85).toString() : "");
                    lbmp.setGroupCustId(vec.get(52).toString());
                    list1.add(lbmp);
                }

                List<LoanBorrowerModPojo> newList = new ArrayList<LoanBorrowerModPojo>();

                for (int m = 0, n = 1; m < list1.size() - 1 && n < list1.size();) {
                    if (list1.get(m).getAccno().equalsIgnoreCase(list1.get(n).getAccno())) {
                        tempList.add(list1.get(m));
                        tempList.add(list1.get(n));
                        for (int x = 0, y = 1; x < tempList.size() - 1 && y < tempList.size(); x++, y++) {

                            if (!(tempList.get(x).getCustId().equalsIgnoreCase(tempList.get(y).getCustId()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("01.Account Number");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getCustId() + "\n" + "Changed Val:" + tempList.get(y).getCustId());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }


                            if (!(tempList.get(x).getSector().equalsIgnoreCase(tempList.get(y).getSector()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("02.Sector");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getSector() + "\n" + "Changed Val:" + tempList.get(y).getSector());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getSubSector().equalsIgnoreCase(tempList.get(y).getSubSector()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("03.Sub Sector");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getSubSector() + "\n" + "Changed Val:" + tempList.get(y).getSubSector());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getPurposeOfAdvance().equalsIgnoreCase(tempList.get(y).getPurposeOfAdvance()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("04.Purpose Of Advance");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getPurposeOfAdvance() + "\n" + "Changed Val:" + tempList.get(y).getPurposeOfAdvance());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getModeOfAdvance().equalsIgnoreCase(tempList.get(y).getModeOfAdvance()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("05.Mode Of Advance");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getModeOfAdvance() + "\n" + "Changed Val;" + tempList.get(y).getModeOfAdvance());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getTypeOfAdvance().equalsIgnoreCase(tempList.get(y).getTypeOfAdvance()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("06.Type Of Advance");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getTypeOfAdvance() + "\n" + "Changed Val:" + tempList.get(y).getTypeOfAdvance());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getSecured().equalsIgnoreCase(tempList.get(y).getSecured()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("07.Secured");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getSecured() + "\n" + "Changed Val:" + tempList.get(y).getSecured());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getGuaranteeCover().equalsIgnoreCase(tempList.get(y).getGuaranteeCover()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("08.Guarantee Cover");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getGuaranteeCover() + "\n" + "Changed Val:" + tempList.get(y).getGuaranteeCover());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getIndustryType().equalsIgnoreCase(tempList.get(y).getIndustryType()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("09.Industry Type");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getIndustryType() + "\n" + "Changed Val:" + tempList.get(y).getIndustryType());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getSickness().equalsIgnoreCase(tempList.get(y).getSickness()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("10.Sickness");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getSickness() + "\n" + "Changed Val:" + tempList.get(y).getSickness());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getExposure().equalsIgnoreCase(tempList.get(y).getExposure()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("11.Exposure");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getExposure() + "\n" + "Changed Val:" + tempList.get(y).getExposure());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getRestructuring().equalsIgnoreCase(tempList.get(y).getRestructuring()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("12.Restructuring");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getRestructuring() + "\n" + "Changed Val:" + tempList.get(y).getRestructuring());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getSanctionLevel().equalsIgnoreCase(tempList.get(y).getSanctionLevel()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("13.Sanction Level");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getSanctionLevel() + "\n" + "Changed Val:" + tempList.get(y).getSanctionLevel());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getSanctioningAuthority().equalsIgnoreCase(tempList.get(y).getSanctioningAuthority()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("14.Sanction Authority");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getSanctioningAuthority() + "\n" + "Changed Val:" + tempList.get(y).getSanctioningAuthority());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getInterestTable().equalsIgnoreCase(tempList.get(y).getInterestTable()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("15.Interest Table");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getInterestTable() + "\n" + "Changed Val:" + tempList.get(y).getInterestTable());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getInterestType().equalsIgnoreCase(tempList.get(y).getInterestType()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("16.Interest Type");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getInterestType() + "\n" + "Changed Val:" + tempList.get(y).getInterestType());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getSchemeCode().equalsIgnoreCase(tempList.get(y).getSchemeCode()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("17.Scheme Code");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getSchemeCode() + "\n" + "Changed Val:" + tempList.get(y).getSchemeCode());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getNpaClassification().equalsIgnoreCase(tempList.get(y).getNpaClassification()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("18.Npa Classification");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getNpaClassification() + "\n" + "Changed Val:" + tempList.get(y).getNpaClassification());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getCourts().equalsIgnoreCase(tempList.get(y).getCourts()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("19.Courts");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getCourts() + "\n" + "Changed Val:" + tempList.get(y).getCourts());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getModeOfSettlement().equalsIgnoreCase(tempList.get(y).getModeOfSettlement()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("20.Mode Of Settlement");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getModeOfSettlement() + "\n" + "Changed Val:" + tempList.get(y).getModeOfSettlement());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getDebtWaiverReason().equalsIgnoreCase(tempList.get(y).getDebtWaiverReason()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("21.Debt Waiver Reason");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getDebtWaiverReason() + "\n" + "Changed Val:" + tempList.get(y).getDebtWaiverReason());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getAssetClassReason().equalsIgnoreCase(tempList.get(y).getAssetClassReason()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("22.Asset Class Reason");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getAssetClassReason() + "\n" + "Changed Val:" + tempList.get(y).getAssetClassReason());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getPopulationGroup().equalsIgnoreCase(tempList.get(y).getPopulationGroup()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("23.Population Group");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getPopulationGroup() + "\n" + "Changed Val:" + tempList.get(y).getPopulationGroup());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getCreatedByUserId().equalsIgnoreCase(tempList.get(y).getCreatedByUserId()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("24.Created By UserId");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getCreatedByUserId() + "\n" + "Changed Val:" + tempList.get(y).getCreatedByUserId());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getCreationDate().equals(tempList.get(y).getCreationDate()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("25.Creation Date");
                                po.setNewValue("Pre.Val" + tempList.get(x).getCreationDate() + "\n" + "Changed Val:" + tempList.get(y).getCreationDate());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }



                            if ((tempList.get(x).getLastUpdatedByUserId().equals(tempList.get(y).getLastUpdatedByUserId())) || !(tempList.get(x).getLastUpdatedByUserId().equals(tempList.get(y).getLastUpdatedByUserId()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("26.Last UpdatedBy UserId");
                                po.setNewValue(tempList.get(y).getLastUpdatedByUserId());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }



                            if (!(String.valueOf(tempList.get(x).getNetWorth()).equalsIgnoreCase(String.valueOf(tempList.get(y).getNetWorth())))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("27.NetWorth");
                                po.setNewValue("Pre.Val:" + String.valueOf(tempList.get(x).getNetWorth()) + "\n" + "Changed Val:" + String.valueOf(tempList.get(y).getNetWorth()));
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);

                            }

                            if (!(String.valueOf(tempList.get(x).getMarginMoney()).equalsIgnoreCase(String.valueOf(tempList.get(y).getMarginMoney())))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("28.Margin Money");
                                po.setNewValue("Pre.Val:" + String.valueOf(tempList.get(x).getMarginMoney()) + "\n" + "Changed Val:" + String.valueOf(tempList.get(y).getMarginMoney()));
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getDocumentDate().equals(tempList.get(y).getDocumentDate()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("29.Document Date");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getDocumentDate() + "\n" + "Changed Val:" + tempList.get(y).getDocumentDate());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getRenewalDate().equals(tempList.get(y).getRenewalDate()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("30.Renewal Date");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getRenewalDate() + "\n" + "Changed Val:" + tempList.get(y).getRenewalDate());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getLoanDuration().equalsIgnoreCase(tempList.get(y).getLoanDuration()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("31.Loan Duration");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getLoanDuration() + "\n" + "Changed Val:" + tempList.get(y).getLoanDuration());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getDocumentExpDate().equals(tempList.get(y).getDocumentExpDate()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("32.Document Exp Date");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getDocumentExpDate() + "\n" + "Changed Val:" + tempList.get(y).getDocumentExpDate());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getRelation().equalsIgnoreCase(tempList.get(y).getRelation()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("33.Relation");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getRelation() + "\n" + "Changed Val:" + tempList.get(y).getRelation());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(String.valueOf(tempList.get(x).getSanctionAmt()).equalsIgnoreCase(String.valueOf(tempList.get(y).getSanctionAmt())))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("34.Sanction Amt");
                                po.setNewValue("Pre.Val:" + String.valueOf(tempList.get(x).getSanctionAmt()) + "\n" + "Changed Val:" + String.valueOf(tempList.get(y).getSanctionAmt()));
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getDrawingPowerIndicator().equalsIgnoreCase(tempList.get(y).getDrawingPowerIndicator()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("35.Drawing Power Indicator");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getDrawingPowerIndicator() + "\n" + "Changed Val:" + tempList.get(y).getDrawingPowerIndicator());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(String.valueOf(tempList.get(x).getMonthlyIncome()).equalsIgnoreCase(String.valueOf(tempList.get(y).getMonthlyIncome())))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("36.Monthly Income");
                                po.setNewValue("Pre.Val:" + String.valueOf(tempList.get(x).getMonthlyIncome()) + "\n" + "Changed Val:" + String.valueOf(tempList.get(y).getMonthlyIncome()));
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getApplicantCatagory().equalsIgnoreCase(tempList.get(y).getApplicantCatagory()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("37.Appliaction Catagory");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getApplicantCatagory() + "\n" + "Changed Val:" + tempList.get(y).getApplicantCatagory());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getCategoryOpt().equalsIgnoreCase(tempList.get(y).getCategoryOpt()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("38.Catagory Opt");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getCategoryOpt() + "\n" + "Changed Val:" + tempList.get(y).getCategoryOpt());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getMinorCommunity().equalsIgnoreCase(tempList.get(y).getMinorCommunity()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("39.Minor Community");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getMinorCommunity() + "\n" + "Changed Val:" + tempList.get(y).getMinorCommunity());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getRemarks().equalsIgnoreCase(tempList.get(y).getRemarks()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("40.Remarks");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getRemarks() + "\n" + "Changed Val:" + tempList.get(y).getRemarks());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getAuth().equalsIgnoreCase(tempList.get(y).getAuth()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("41.Auth");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getAuth() + "\n" + "Changed Val:" + tempList.get(y).getAuth());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getRelName().equalsIgnoreCase(tempList.get(y).getRelName()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("42.Rel Name");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getRelName() + "\n" + "Changed Val:" + tempList.get(y).getRelName());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getRelWithAcHolder().equalsIgnoreCase(tempList.get(y).getRelWithAcHolder()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("43.Relation With AcHolder");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getRelWithAcHolder() + "\n" + "Changed Val:" + tempList.get(y).getRelWithAcHolder());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getExpCat().equalsIgnoreCase(tempList.get(y).getExpCat()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("44.Exp Cat");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getExpCat() + "\n" + "Changed Val:" + tempList.get(y).getExpCat());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getExpCatPur().equalsIgnoreCase(tempList.get(y).getExpCatPur()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("45.Exp Cat Pur");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getExpCatPur() + "\n" + "Changed Val:" + tempList.get(y).getExpCatPur());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getDirCustId().equalsIgnoreCase(tempList.get(y).getDirCustId()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("46.Dir Cust Id");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getDirCustId() + "\n" + "Changed Val:" + tempList.get(y).getDirCustId());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getRetirementAge().equalsIgnoreCase(tempList.get(y).getRetirementAge()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("47.Retirement Age");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getRetirementAge() + "\n" + "Changed Val:" + tempList.get(y).getRetirementAge());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getIndustry().equalsIgnoreCase(tempList.get(y).getIndustry()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("48.Industry");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getIndustry() + "\n" + "Changed Val:" + tempList.get(y).getIndustry());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getGroupId().equalsIgnoreCase(tempList.get(y).getGroupId()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("49.Group Id");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getGroupId() + "\n" + "Changed Val:" + tempList.get(y).getGroupId());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                            if (!(tempList.get(x).getGroupCustId().equalsIgnoreCase(tempList.get(y).getGroupCustId()))) {
                                LoanBorrowerModPojo po = new LoanBorrowerModPojo();
                                po.setAccno(list1.get(m).getAccno());
                                po.setColoumName("50.Group CustId");
                                po.setNewValue("Pre.Val:" + tempList.get(x).getGroupCustId() + "\n" + "Changed Val:" + tempList.get(y).getGroupCustId());
                                po.setLastUpdateUserName(tempList.get(y).getLastUpdatedByUserId());
                                po.setNewLastUpdateDate(tempList.get(y).getLastUpdatedDate());
                                finalList.add(po);
                            }

                        }
                        tempList.clear();
                        m++;
                        n++;
                    } else {
                        m++;
                        n++;
                    }
                }
            }
            return finalList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getLoanStmaHeader(String acNo) throws ApplicationException {
        List list;
        try {
            list = em.createNativeQuery("select a.acno,a.custname,a.acctdesc,a.jtname1,ifnull(a.nomination,''),a.craddress,a.praddress,a.jtname2,a.jtname3,a.jtname4, a.OpeningDt,b.ODLIMIT,c.sanctionlimitdt, c.LOAN_PD_MONTH from \n"
                    + "(select a.acno,a.custname,b.acctdesc,a.jtname1,a.nomination,c.craddress,c.praddress,a.jtname2,a.jtname3,a.jtname4, a.OpeningDt \n"
                    + "from accountmaster a,accounttypemaster b ,customermaster c where a.acno='" + acNo + "' \n"
                    + "and substring(a.acno,3,2)=b.acctcode and substring(a.acno,5,6)=c.custno and c.brncode = substring(a.acno,1,2) \n"
                    + "and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2) = c.agcode)a,\n"
                    + "(select ACNO,ODLIMIT FROM accountmaster WHERE ACNO='" + acNo + "')b,\n"
                    + "(select a.acno,a.sanctionlimitdt, b.LOAN_PD_MONTH from loan_appparameter a, cbs_loan_acc_mast_sec b where a.acno = b.acno and a.acno='" + acNo + "' and a.brcode='" + acNo.substring(0, 2) + "')c\n"
                    + "where a.acno = b.acno and a.acno=c.acno").getResultList();
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<LoanIntCalcList> getMinMaxAvgBalance(String brnCode, String acNature, String acType, String frDate, String toDate) throws ApplicationException {
        List list;
        List<LoanIntCalcList> intCalc = new ArrayList<LoanIntCalcList>();
        try {
            String acctCode = "";
            String acno = "", days = "", custName = "";
            double bal = 0.0, minBal = 0.0, maxBal = 0.0, avgBal = 0.0;
            String acNatureQuery = "";
            String branchQuery = "";
            if (!(brnCode.equalsIgnoreCase("0A")) || (brnCode.equalsIgnoreCase("All"))) {
                branchQuery = "and substring(am.acno,1,2) ='" + brnCode + "' ";
            }
            if (acNature.equalsIgnoreCase("All")) {
                acNatureQuery = " and atm.acctNature in ('CA','DL','TL') and atm.crdbflag in ('D','B') ";
            } else {
                if (acType.equalsIgnoreCase("All")) {
                    acNatureQuery = " and atm.acctNature in ('" + acNature + "') and atm.crdbflag in ('D','B') ";
                } else {
                    acNatureQuery = " and atm.acctNature in ('" + acNature + "') and atm.crdbflag in ('D','B') and substring(am.acno,3,2) ='" + acType + "' ";
                }
            }
            String query;
            query = "select aa.AcctCode, aa.acno ,sum(aa.bal), avg(aa.bal), min(aa.bal), max(aa.bal) , DATEDIFF('" + toDate + "','" + frDate + "') as days , aa.custName from "
                    + " ( select substring(am.acno,1,2) as brnCode,atm.acctNature, atm.AcctCode, am.acno, s.INTEREST_TABLE_CODE, bk.date,"
                    + " (if((atm.acctnature = 'CA'), "
                    + " (select cast(ifnull(sum(dramt-cramt),0) as decimal(25,2))from ca_recon where acno = am.acno and dt<=bk.date),"
                    + " (select cast(ifnull(sum(dramt-cramt),0) as decimal(25,2)) from loan_recon where acno = am.acno and dt<=bk.date) )) as bal, "
                    + " (select cast(ifnull(sum(dramt-cramt),0) as decimal(25,2)) from npa_recon where acno = am.acno and dt<=bk.date) as npaBal, "
                    + " (if((atm.acctnature = 'CA'),(select cast(ifnull(sum(dramt-cramt),0) as decimal(25,2)) from ca_recon "
                    + " where acno = am.acno and trandesc not in (3,4) and dt<=bk.date), "
                    + " (select cast(ifnull(sum(dramt-cramt),0) as decimal(25,2)) from loan_recon where acno = am.acno  and trandesc not in (3,4) and dt<=bk.date) )) as simpleBal, "
                    + " s.INT_APP_FREQ, am.odlimit,"
                    + " am.custName, am.intdeposit, ifnull(sg.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG, s.CALC_LEVEL, s.PEGG_FREQ, s.CALC_ON, s.RATE_CODE, "
                    + " (select MAX(ai.AC_INT_VER_NO) as AC_INT_VER_NO FROM cbs_acc_int_rate_details ai where ai.acno = am.acno and ai.EFF_FRM_DT<=bk.date) as version, "
                    + " Date_Format(am.OpeningDt,'%Y-%m-%d'), am.AccStatus, ifnull(sg.LINK_TRAN_TO_PURCHASE_SALE, 'E') as LINK_TRAN_TO_PURCHASE_SALE from "
                    + " (select  Date_Format(v.selected_date,'%Y-%m-%d') as date from "
                    + " (select adddate(Date_Format(('" + frDate + "'),'%Y-%m-%d'),t4*10000 + t3*1000 + t2*100 + t1*10 + t0) selected_date from "
                    + " (select 0 t0 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,"
                    + " (select 0 t1 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1, "
                    + " (select 0 t2 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2, "
                    + " (select 0 t3 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,"
                    + " (select 0 t4 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) v "
                    + "  where v.selected_date between Date_Format(('" + frDate + "'),'%Y-%m-%d') and Date_Format(('" + toDate + "'),'%Y-%m-%d')) bk, accounttypemaster atm, accountmaster am "
                    + " left join  cbs_loan_acc_mast_sec s on am.acno = s.acno  "
                    + " left join cbs_scheme_general_scheme_parameter_master sg  on s.SCHEME_CODE = sg.SCHEME_CODE  "
                    + " where substring(am.acno,3,2) = atm.AcctCode " + branchQuery + "" + acNatureQuery + ""
                    + "  and (am.ClosingDate = '' or am.ClosingDate is null or am.ClosingDate>'" + toDate + "') and  "
                    + " bk.date between Date_Format(('" + frDate + "'),'%Y-%m-%d') and Date_Format(('" + toDate + "'),'%Y-%m-%d') "
                    + " and CONVERT(bk.date , DATE) >= CONVERT(am.OpeningDt , DATE)   /* and am.acno in( '050200120301' )*/ and am.acno"
                    + " and s.INTEREST_TABLE_CODE is not null group by am.acno, bk.date "
                    + " ) aa group by aa.acno  ";
            list = em.createNativeQuery(query).getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Data Not Exist");
            } else {
                for (int e = 0; e < list.size(); e++) {
                    Vector intVect = (Vector) list.get(e);
                    LoanIntCalcList pojo = new LoanIntCalcList();
                    acctCode = intVect.get(0).toString();
                    acno = intVect.get(1).toString();
                    bal = Double.parseDouble(intVect.get(2).toString());
                    avgBal = Double.parseDouble(intVect.get(3).toString());
                    days = intVect.get(6).toString();
                    minBal = Double.parseDouble(intVect.get(4).toString());
                    maxBal = Double.parseDouble(intVect.get(5).toString());
                    custName = intVect.get(7).toString();
                    pojo.setAcType(acctCode);
                    pojo.setAcNo(acno);
                    pojo.setCustName(custName);
                    pojo.setrProduct(new BigDecimal(bal));
                    pojo.setrPriAmt(new BigDecimal(avgBal));
                    pojo.setrClosingBal(new BigDecimal(maxBal));
                    pojo.setrFdProduct(new BigDecimal(minBal));
                    pojo.setFromDate(days);
                    intCalc.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return intCalc;
    }

    public List<StockStatementRepPojo> getStockStatementDetails(String brcode, String acnochoice, String acno, String frdt, String todt) throws ApplicationException {
        List<StockStatementRepPojo> list = new ArrayList();
        String brcodeQuery = "";
        String acnochoicequery = "";
        if (brcode.equalsIgnoreCase("0A")) {
            brcodeQuery = "";
        } else {
            brcodeQuery = "and  substring(Acno,1,2) = '" + brcode + "'";
        }

        if (acnochoice.equalsIgnoreCase("ALL")) {
            acnochoicequery = "";
        } else {
            acnochoicequery = "and Acno ='" + acno + "'";
        }

        List l = em.createNativeQuery("select Acno as Acno , securityChg as securityChg ,securityoption as securityoption , Particulars as Particulars, cast(lienvalue as Decimal) as lienvalue,"
                + "Status as Status ,Date_format(ReceivedSTMDt,'%d/%m/%Y') as ReceivedSTMDt,"
                + "ifnull(Date_format(ExpiryDate,'%d/%m/%Y') ,'') as ExpiryDate,Date_format(Entrydate,'%d/%m/%Y') as Entrydate,Enteredby as Enteredby "
                + "from  loan_stockstm where Entrydate between '" + frdt + "' and '" + todt + "'" + brcodeQuery + "" + acnochoicequery + "").getResultList();

        if (!l.isEmpty()) {
            for (int i = 0; i < l.size(); i++) {
                Vector vec = (Vector) l.get(i);
                StockStatementRepPojo pojo = new StockStatementRepPojo();
                pojo.setAcno(vec.get(0).toString());
                pojo.setStockdetail(vec.get(1).toString() + " " + vec.get(2).toString() + " " + vec.get(3).toString());
                pojo.setValue(BigDecimal.valueOf(Double.valueOf(vec.get(4).toString())));
                pojo.setStatus(vec.get(5).toString());
                pojo.setRecievedDt(vec.get(6).toString());
                pojo.setExpiryDt(vec.get(7).toString());
                pojo.setEntryDate(vec.get(8).toString());
                pojo.setEnterby(vec.get(9).toString());
                list.add(pojo);
            }
        }
        return list;
    }

    public List<CCTurnOverReportPojo> getccTurnOverDetails(String brncode, String actype, String fromdate, String toDate) throws ApplicationException {

        List<CCTurnOverReportPojo> dataList = new ArrayList();
        SimpleDateFormat ymdFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymdFormatt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

        String branchQuery = "";
        String actypeQuery = "";
        if (brncode.equalsIgnoreCase("0A")) {
            branchQuery = "";
        } else {
            branchQuery = "and b.curbrcode = '" + brncode + "'";
        }
        if (actype.equalsIgnoreCase("ALL")) {
            actypeQuery = "select AcctCode from accounttypemaster where acctnature = 'CA' and CrDbFlag <> 'C'";
        } else {
            actypeQuery = "'" + actype + "'";
        }
        try {
            List list = em.createNativeQuery("select aa.customerid,aa.acNo,aa.custfullname,Date_format(aa.renewalDt,'%d/%m/%Y'),aa.SanctionLimit,ifnull(bb.acLimit,aa.SanctionLimit),ifnull(cc.OutStandingBal,0),ifnull(dd.DebitTurnover,0),ifnull(dd.CreditTurnover,0),ifnull(Date_format(ee.LastCreditDate,'%d/%m/%Y'),'') from "
                    + "(select a.customerid,c.acNo,a.custfullname,c.renewalDt,cast(b.odlimit as decimal(25,2)) as SanctionLimit from cbs_customer_master_detail a,accountmaster b, "
                    + "(select CUST_ID as custId ,ACC_NO as acNo,DOCUMENT_DATE as renewalDt  from cbs_loan_borrower_details where date_format(DOCUMENT_DATE,'%Y%m%d') <='" + toDate + "' "
                    + "union "
                    + "select CUST_ID as custId ,ACC_NO as acNo,DOCUMENT_DATE as renewalDt  from cbs_loan_borrower_details_history where date_format(DOCUMENT_DATE,'%Y%m%d') <='" + toDate + "')c "
                    + "where b.acno=c.acNo and a.customerid = c.custId and b.accttype in(" + actypeQuery + ") "
                    + branchQuery
                    + "and (ifnull(b.closingDate,'') = '' or b.closingDate > '" + toDate + "'))aa "
                    + "left join "
                    + "(select a.acno,a.SanctionLimitDt, cast(a.acLimit as decimal(25,2)) as acLimit from loan_oldinterest a, "
                    + "(select acno,max(TXNID) as tId from loan_oldinterest where enterdate<='" + toDate + "' group by acno)b "
                    + "where a.acno = b.acno and a.TXNID = b.tId)bb "
                    + "on aa.acno = bb.acno "
                    + "left join "
                    + "(select acno,cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) as OutStandingBal from ca_recon where  dt<='" + toDate + "'group by acno)cc "
                    + "on aa.acno = cc.acno "
                    + "left join "
                    + "(select acno,cast(sum(dramt) as decimal(25,2)) as DebitTurnover,cast(sum(cramt) as decimal(25,2)) as CreditTurnover from ca_recon where  dt between '" + fromdate + "' and '" + toDate + "' group by acno) dd "
                    + "on aa.acno = dd.acno "
                    + "left join "
                    + "(select acno,max(dt) as LastCreditDate from ca_recon where ty=0 and  dt<='" + toDate + "'group by acno)ee "
                    + "on aa.acno = ee.acno order by 2,4").getResultList();

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    CCTurnOverReportPojo cctorp = new CCTurnOverReportPojo();
                    cctorp.setAcno(vec.get(1).toString());
                    cctorp.setAcholdername(vec.get(2).toString());
                    cctorp.setRenewalDate(vec.get(3).toString());
                    cctorp.setSanctionLimit(BigDecimal.valueOf(Double.valueOf(vec.get(5).toString())));
                    cctorp.setOutstandingBalance(BigDecimal.valueOf(Math.abs(Double.valueOf(vec.get(6).toString()))));
                    cctorp.setDebitTurnOver(BigDecimal.valueOf(Double.valueOf(vec.get(7).toString())));
                    cctorp.setCreditTurnOver(BigDecimal.valueOf(Double.valueOf(vec.get(8).toString())));
                    cctorp.setLastCreditDate(vec.get(9).toString());
                    dataList.add(cctorp);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<StatementOfAlmPojo> getStatementOfAlmDetails(String brncode, String acType, String fromDate, String toDate) throws ApplicationException {
        List<StatementOfAlmPojo> finalList = new ArrayList();
        String brnCodeQuery = "";
        String actypeQuery = "";
        if (brncode.equalsIgnoreCase("0A")) {
            brnCodeQuery = "";
        } else {
            brnCodeQuery = "and a.curbrcode = '" + brncode + "'";
        }

        if (acType.equalsIgnoreCase("ALL")) {
            actypeQuery = "select AcctCode from accounttypemaster where acctnature = 'CA' and CrDbFlag <> 'C'";
        } else {
            actypeQuery = "'" + acType + "'";
        }
        try {
            List list = em.createNativeQuery("select aa.acno,aa.custfullName,aa.SanctionLimit,ifnull(cc.acLimit,aa.SanctionLimit) as sanLimit,ifnull(bb.OutStandingBal,0) from "
                    + "(select a.acno,c.custfullName,cast(a.odlimit as decimal(25,2)) as SanctionLimit  from accountmaster a,customerid b,cbs_customer_master_detail c  "
                    + "where a.acno = b.acno and b.custid = cast(c.customerid as unsigned) " + brnCodeQuery
                    + "and a.accttype in(" + actypeQuery + ") "
                    + "and openingDt<='" + toDate + "' and (ifnull(ClosingDate,'')='' OR ClosingDate> '" + toDate + "'))aa "
                    + "left join  "
                    + "(select acno,cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) as OutStandingBal from ca_recon where  dt<='" + toDate + "'group by acno)bb "
                    + "on aa.acno = bb.acno  "
                    + "left join "
                    + "(select a.acno,a.SanctionLimitDt, cast(a.acLimit as decimal(25,2)) as acLimit from loan_oldinterest a, "
                    + "(select acno,max(TXNID) as tId from loan_oldinterest where enterdate<='" + toDate + "' group by acno)b "
                    + "where a.acno = b.acno and a.TXNID = b.tId) cc  "
                    + "on aa.acno = cc.acno  ").getResultList();

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    StatementOfAlmPojo soap = new StatementOfAlmPojo();
                    soap.setAcno(vec.get(0).toString());
                    soap.setName(vec.get(1).toString());
                    soap.setSanctionLimit(BigDecimal.valueOf(Double.valueOf(vec.get(3).toString())));
                    soap.setOutstandingBal(BigDecimal.valueOf(Math.abs(Double.valueOf(vec.get(4).toString()))));

                    List l = em.createNativeQuery("select ifnull(MaxBal,0),ifnull(avgBal,0) from "
                            + "(select max(a.bal) as MaxBal from  "
                            + "(select v.selected_date,  "
                            + "(select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2))  from ca_recon where acno = '" + soap.getAcno() + "' "
                            + "and dt<=v.selected_date ) as bal from "
                            + "(select Dt as selected_date from ca_recon where acno= '" + soap.getAcno() + "' and dt between '" + fromDate + "' and '" + toDate + "') v "
                            + "where v.selected_date between '" + fromDate + "' and '" + toDate + "')a) aa, "
                            + "(select avg(a.bal) as avgBal from  "
                            + "(select v.selected_date,  "
                            + "(select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2))  from ca_recon where acno = '" + soap.getAcno() + "' "
                            + "and dt<=v.selected_date ) as bal from  "
                            + "(select date as selected_date from cbs_bankdays where date between '" + fromDate + "' and '" + toDate + "') v "
                            + "where v.selected_date between '" + fromDate + "' and '" + toDate + "' )a) bb").getResultList();

                    if (!l.isEmpty()) {
                        for (int j = 0; j < l.size(); j++) {
                            Vector vector = (Vector) l.get(j);
                            BigDecimal max = BigDecimal.valueOf(Double.valueOf(vector.get(0).toString()));
                            BigDecimal avg = BigDecimal.valueOf(Double.valueOf(vector.get(1).toString()));
                            BigDecimal onetofurteen = max.subtract(avg);
                            BigDecimal inoneyear = soap.getOutstandingBal().subtract(onetofurteen);
                            soap.setMaxBal(BigDecimal.valueOf(Math.abs(max.doubleValue())));
                            soap.setAvgBal(BigDecimal.valueOf(Math.abs(avg.doubleValue())));
                            soap.setOneToFourteenDays(onetofurteen);
                            soap.setInOneYear(inoneyear);
                        }
                    }
                    finalList.add(soap);
                }
            }


        } catch (Exception ex) {
            throw new ApplicationException();
        }
        return finalList;
    }
}
