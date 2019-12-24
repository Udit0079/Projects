/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.dao.loan.CbsLoanBorrowerDetailsDAO;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.sun.xml.registry.common.tools.bindings_v3.Command;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "LoanAuthorizationManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LoanAuthorizationManagementFacade implements LoanAuthorizationManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat y_m_dFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public List getAccountNoInformation(String BRCODE) throws ApplicationException {
        List checkList = new ArrayList();
        try {
            checkList = em.createNativeQuery("select ACC_NO from cbs_loan_borrower_details bd,accountmaster am where bd.ACC_NO = am.ACNo and am.CurBrCode='" + BRCODE + "' and (AUTH='N' or AUTH is null)").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return checkList;
    }

    public List getAccountNoAuthData(String acno ,String todayDate) throws ApplicationException {
        try {
            String brCode = ftsPosting.getCurrentBrnCode(acno);            
//            List chkList = em.createNativeQuery("select am.acno  as acNo, am.custName, la.Sanctionlimit, "
//                    + "ifnull(la.Sanctionlimitdt, date_format(am.OpeningDt,'%Y-%m-%d')), ifnull(a.loan_duration,0),  "
//                    + "a.sector as sector, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '182'  and REF_CODE = a.sector),'') as sectorDesc ,  "
//                    + "a.sub_sector  as subSector, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '183'  and REF_CODE = a.sub_sector ),'') as subSectorDesc ,  "
//                    + "a.MODE_OF_ADVANCE as modeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '185'  and REF_CODE = a.MODE_OF_ADVANCE),'') as modeOfAdvanceDesc ,  "
//                    + "a.SECURED as secured, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '187'  and REF_CODE = a.SECURED),'') as securedDesc ,  "
//                    + "a.TYPE_OF_ADVANCE as typeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '186'  and REF_CODE = a.TYPE_OF_ADVANCE),'') as typeOfAdvanceDesc ,  "
//                    + "a.PURPOSE_OF_ADVANCE as purposeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '190'  and REF_CODE = a.PURPOSE_OF_ADVANCE),'') as purposeOfAdvanceDesc ,  "
//                    + "a.GUARANTEE_COVER as guarnteeCover, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '188'  and REF_CODE = a.GUARANTEE_COVER),'') as guarnteeCoverDesc ,  "
//                    + "a.SICKNESS as purOfAdv, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '184'  and REF_CODE = a.SICKNESS),'') as purOfAdvDesc ,  "
//                    + "a.EXPOSURE as exposure, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '191'  and REF_CODE = a.EXPOSURE),'') as exposureDesc ,  "
//                    + "ifnull(a.EXP_CAT,'') as exposureCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '230'  and REF_CODE = a.EXP_CAT),'') as exposureCategoryDesc ,  "
//                    + "ifnull(a.EXP_CAT_PUR,'') as exposureCategoryPurpose, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '231'  and REF_CODE = a.EXP_CAT_PUR),'') as exposureCategoryPurposeDesc ,  "
//                    + "a.SCHEME_CODE as schemeCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '203'  and REF_CODE = a.SCHEME_CODE),'') as schemeCodeDesc ,  "
//                    + "a.INTEREST_TYPE as intType, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '202'  and REF_CODE = a.INTEREST_TYPE),'') as intTypeDesc ,   "
//                    + "a.APPLICANT_CATEGORY as applicantCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '208'  and REF_CODE = a.APPLICANT_CATEGORY),'') as applicantCategoryDesc ,   "
//                    + "a.CATEGORY_OPT as categoryOpt, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '209'  and REF_CODE = a.CATEGORY_OPT),'') as categoryOptDesc ,   "
//                    + "a.MINOR_COMMUNITY as minorCommunity, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '204'  and REF_CODE = a.MINOR_COMMUNITY),'') as minorCommunityDesc ,   "
//                    + "a.RELATION as relation, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210'  and REF_CODE = a.RELATION),'') as relationDesc ,   "
//                    + "ifnull(a.REL_WITH_AC_HOLDER,'') as relOwner, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '004'  and REF_CODE = a.REL_WITH_AC_HOLDER),'') as relOwnerDesc ,   "
//                    + "a.DRAWING_POWER_INDICATOR  as drawingPwrInd, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '232'  and REF_CODE = a.DRAWING_POWER_INDICATOR),'') as drawingPwrIndDesc ,   "
//                    + "a.POPULATION_GROUP as popuGroup, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '200'  and REF_CODE = a.POPULATION_GROUP),'') as popuGroupDesc ,   "
//                    + "a.SANCTION_LEVEL as sanctionLevel, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '193'  and REF_CODE = a.SANCTION_LEVEL),'') as sanctionLevelDesc ,   "
//                    + "a.SANCTIONING_AUTHORITY as sanctionAuth, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '194'  and REF_CODE = a.SANCTIONING_AUTHORITY),'') as sanctionAuthDesc ,   "
//                    + "a.COURTS  as courts, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '196'  and REF_CODE = a.COURTS ),'') as courtsDesc ,  "
//                    + "a.RESTRUCTURING as restructuring, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '192'  and REF_CODE = a.RESTRUCTURING),'') as restructuringDesc ,  "
//                    + "a.net_worth, ifnull(la.ODLimit,0), /*fnull(sum(ifnull(tn.cramt,0)),0)-ifnull(sum(ifnull(tn.dramt,0)),0) as balance*/   "
//                    + "atm.acctNature as acNature, "
//                    + "atm.AcctCode as acType, substring(am.acno,1,2) as brnCode, ifnull(a.CUST_ID,''), "
//                    + "ifnull(a.CREATED_BY_USER_ID,''), ifnull(a.CREATION_DATE,''), ifnull(a.LAST_UPDATED_BY_USER_ID,''), ifnull(a.LAST_UPDATED_DATE,''), " 
//                    + "ifnull(a.TOTAL_MODIFICATIONS,''), ifnull(a.MARGIN_MONEY,'0'), ifnull(a.DOCUMENT_DATE,''), ifnull(a.DOCUMENT_EXP_DATE,''), ifnull(a.RENEWAL_DATE,''), " 
//                    + "ifnull(a.MONTHLY_INCOME,'0'), ifnull(a.REMARKS,''),  " 
//                    + "a.INTEREST_TABLE as intTableCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '201'  and REF_CODE = a.INTEREST_TABLE),'') as schemeCodeDesc   "
//                    +"from cbs_loan_borrower_details a , accountmaster am, loan_appparameter la, accounttypemaster atm/*, null tn*/" 
//                    +"where   am.acno = '"+acno+"' and substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno and a.acc_no = la.acno ").getResultList();
            
            
             List chkList = em.createNativeQuery("select am.acno  as acNo, am.custName, la.Sanctionlimit,"
                     + "ifnull(la.Sanctionlimitdt, date_format(am.OpeningDt,'%Y-%m-%d')), ifnull(a.loan_duration,0),"
                     + "a.sector as sector, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '182' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.sector),'') as sectorDesc ,"
                     + "a.sub_sector  as subSector, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '183' and '"+todayDate+"' between eff_fr_dt and eff_to_dt and REF_CODE = a.sub_sector ),'') as subSectorDesc ,"
                     + "a.MODE_OF_ADVANCE as modeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '185' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.MODE_OF_ADVANCE),'') as modeOfAdvanceDesc ,"
                     + "a.SECURED as secured, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '187' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.SECURED),'') as securedDesc ,"
                     + "a.TYPE_OF_ADVANCE as typeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '186' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.TYPE_OF_ADVANCE),'') as typeOfAdvanceDesc ,"
                     + "a.PURPOSE_OF_ADVANCE as purposeOfAdvance, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '190' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.PURPOSE_OF_ADVANCE),'') as purposeOfAdvanceDesc ,"
                     + "a.GUARANTEE_COVER as guarnteeCover, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '188' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.GUARANTEE_COVER),'') as guarnteeCoverDesc ,"
                     + "a.SICKNESS as purOfAdv, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '184' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.SICKNESS),'') as purOfAdvDesc ,"
                     + "a.EXPOSURE as exposure, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '191' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.EXPOSURE),'') as exposureDesc ,"
                     + "ifnull(a.EXP_CAT,'') as exposureCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '230' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.EXP_CAT),'') as exposureCategoryDesc ,"
                     + "ifnull(a.EXP_CAT_PUR,'') as exposureCategoryPurpose, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '231' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.EXP_CAT_PUR),'') as exposureCategoryPurposeDesc ,"
                     + "a.SCHEME_CODE as schemeCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '203' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.SCHEME_CODE),'') as schemeCodeDesc ,"
                     + "a.INTEREST_TYPE as intType, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '202'  and REF_CODE = a.INTEREST_TYPE),'') as intTypeDesc ,"
                     + "a.APPLICANT_CATEGORY as applicantCategory, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '208' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.APPLICANT_CATEGORY),'') as applicantCategoryDesc ,"
                     + "a.CATEGORY_OPT as categoryOpt, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '209' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.CATEGORY_OPT),'') as categoryOptDesc ,"
                     + "a.MINOR_COMMUNITY as minorCommunity, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '204' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.MINOR_COMMUNITY),'') as minorCommunityDesc ,"
                     + "a.RELATION as relation, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '210' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.RELATION),'') as relationDesc ,"
                     + "ifnull(a.REL_WITH_AC_HOLDER,'') as relOwner, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '004' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.REL_WITH_AC_HOLDER),'') as relOwnerDesc ,"
                     + "a.DRAWING_POWER_INDICATOR  as drawingPwrInd, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '232' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.DRAWING_POWER_INDICATOR),'') as drawingPwrIndDesc ,"
                     + "a.POPULATION_GROUP as popuGroup, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '200' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.POPULATION_GROUP),'') as popuGroupDesc ,"
                     + "a.SANCTION_LEVEL as sanctionLevel, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '193' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.SANCTION_LEVEL),'') as sanctionLevelDesc ,"
                     + "a.SANCTIONING_AUTHORITY as sanctionAuth, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '194' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.SANCTIONING_AUTHORITY),'') as sanctionAuthDesc ,"
                     + "a.COURTS  as courts, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '196' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.COURTS ),'') as courtsDesc ,"
                     + "a.RESTRUCTURING as restructuring, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '192' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.RESTRUCTURING),'') as restructuringDesc ,"
                     + "a.net_worth, ifnull(la.ODLimit,0), /*fnull(sum(ifnull(tn.cramt,0)),0)-ifnull(sum(ifnull(tn.dramt,0)),0) as balance*/  "
                     + "atm.acctNature as acNature,"
                     + "atm.AcctCode as acType, substring(am.acno,1,2) as brnCode, ifnull(a.CUST_ID,''),"
                     + "ifnull(a.CREATED_BY_USER_ID,''), ifnull(a.CREATION_DATE,''), ifnull(a.LAST_UPDATED_BY_USER_ID,''), ifnull(a.LAST_UPDATED_DATE,''),"
                     + "ifnull(a.TOTAL_MODIFICATIONS,''), ifnull(a.MARGIN_MONEY,'0'), ifnull(a.DOCUMENT_DATE,''), ifnull(a.DOCUMENT_EXP_DATE,''), ifnull(a.RENEWAL_DATE,''),"
                     + "ifnull(a.MONTHLY_INCOME,'0'), ifnull(a.REMARKS,''),"
                     + "a.INTEREST_TABLE as intTableCode, ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '201' and '"+todayDate+"' between eff_fr_dt and eff_to_dt  and REF_CODE = a.INTEREST_TABLE),'') as schemeCodeDesc  "
                     + "from cbs_loan_borrower_details a , accountmaster am, loan_appparameter la, accounttypemaster atm/*, null tn*/ "
                     + "where   am.acno = '"+acno+"' and substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno and a.acc_no = la.acno").getResultList();
            List <LoanMisCellaniousPojo> acList = new ArrayList<LoanMisCellaniousPojo>();
            if(chkList.size()>0){
                for(int i = 0;i<chkList.size();i++){
                    Vector queryVect = (Vector) chkList.get(i);                    
                    LoanMisCellaniousPojo pojo = new LoanMisCellaniousPojo();
                    pojo.setCreatedUsrId(queryVect.get(59).toString());
                    pojo.setCreationDt(queryVect.get(60).toString());
                    pojo.setLastUpdtUsrId(queryVect.get(61).toString());
                    pojo.setLastUpdtDt(queryVect.get(62).toString());
                    pojo.setTotalModification(queryVect.get(63).toString());
                    pojo.setMarginMoney(new BigDecimal(queryVect.get(64).toString()));
                    pojo.setDocumentDt(queryVect.get(65).toString());
                    pojo.setDocumentExpDt(queryVect.get(66).toString());
                    pojo.setRenewalDt(queryVect.get(67).toString());
                    pojo.setMonthlyIncome(new BigDecimal(queryVect.get(68).toString()));
                    pojo.setRemarks(queryVect.get(69).toString());
                    
                    pojo.setCustId(queryVect.get(58).toString());
                    pojo.setBrnCode(queryVect.get(57).toString());
                    pojo.setAcNature(queryVect.get(55).toString());
                    pojo.setAcType(queryVect.get(56).toString());
                    pojo.setAcNo(acno);
                    pojo.setCustName(queryVect.get(1).toString());
                    pojo.setSanctionDt(queryVect.get(3).toString().equalsIgnoreCase("")?"":dmyFormat.format(ymdFormat.parse(queryVect.get(3).toString())));
                    pojo.setSanctionAmt(new BigDecimal(queryVect.get(2).toString()));
                    pojo.setNetWorth(new BigDecimal(queryVect.get(53).toString()));
                    pojo.setOdLimit(new BigDecimal(queryVect.get(54).toString()));
                    
                    pojo.setOverdueAmt(new BigDecimal("0"));
                    pojo.setDuration(Integer.parseInt(queryVect.get(4).toString().trim().equalsIgnoreCase("")?"0":queryVect.get(4).toString().trim()));
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
                    pojo.setPurposeOfAdvance(queryVect.get(15).toString());
                    pojo.setPurposeOfAdvanceDesc(queryVect.get(16).toString());
                    pojo.setGuarnteeCover(queryVect.get(17).toString());
                    pojo.setGuarnteeCoverDesc(queryVect.get(18).toString());
                    pojo.setPurOfAdv(queryVect.get(19).toString());
                    pojo.setPurOfAdvDesc(queryVect.get(20).toString());
                    pojo.setExposure(queryVect.get(21).toString());
                    pojo.setExposureDesc(queryVect.get(22).toString());
                    pojo.setExposureCategory(queryVect.get(23).toString());
                    pojo.setExposureCategoryDesc(queryVect.get(24).toString());
                    pojo.setExposureCategoryPurpose(queryVect.get(25).toString());
                    pojo.setExposureCategoryPurposeDesc(queryVect.get(26).toString());
                    pojo.setSchemeCode(queryVect.get(27).toString());
                    pojo.setSchemeCodeDesc(queryVect.get(28).toString());
                    pojo.setIntTable(queryVect.get(70).toString());
                    pojo.setIntTableDesc(queryVect.get(71).toString());
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
                    pojo.setCourts(queryVect.get(49).toString());
                    pojo.setCourtsDesc(queryVect.get(50).toString());
                    pojo.setRestructuring(queryVect.get(51).toString());
                    pojo.setRestructuringDesc(queryVect.get(52).toString());
                    
                    pojo.setCrDrFlag("");
                    pojo.setOutstanding(new BigDecimal(0));
                    acList.add(pojo);
                }
            }
            return acList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    
    public String authorizeAction(String acno, String enterBy, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (acno.equals("--SELECT--")) {
                ut.rollback();
                return "Acno Does Not Exist";
            }
            if ((enterBy == null) || (enterBy.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Data Does not Exist";
            }
            if ((user == null) || (user.equalsIgnoreCase(""))) {
                ut.rollback();
                return "User Name Does not Exist";
            }
            if (enterBy.equalsIgnoreCase(user)) {
                ut.rollback();
                return "You cannot Authorize your own entry";
            }
            Query updateQuery = em.createNativeQuery("Update cbs_loan_borrower_details set AUTH='Y' ,LAST_UPDATED_BY_USER_ID = '" + user.toUpperCase() + "' where ACC_NO = '" + acno + "'");
            int var = updateQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "AUTHORIZATION SUCCESSFUL";
            } else {
                ut.rollback();
                return "YOU CAN NOT AUTHORIZATION";
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getListAccountsStock(String BRCODE) throws ApplicationException {
        List checkList = new ArrayList();
        try {
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            checkList = em.createNativeQuery("select acno,Sno from loansecurity where (upper(auth) <> 'Y' OR  upper(auth) is null) and entryDate ='" + Tempbd + "' and substring(AcNo,1,2) = '"+ BRCODE +"' ORDER BY AcNo").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return checkList;
    }

    public List getListAccountAuth(String acno, int seqNo) throws ApplicationException {
        List checkList = new ArrayList();
        try {
            checkList = em.createNativeQuery("select am.CustName,ls.ACNO,Sno,ifnull(Particulars,'') ,ifnull(SecurityType,'') ,ifnull(SecurityOption,''),"
                    + " ifnull(SecurityChg,'') ,ifnull(MatValue,'') ,ifnull(Lienvalue,'') , ifnull(Security,'') ,ifnull(MARGIN,0) , "
                    + "ifnull(date_format(ReceivedStmDt,'%d/%m/%Y'),'') , ifnull(date_format(NextStmDt,'%d/%m/%Y'),'') ,"
                    + "ifnull(date_format(IssueDate,'%d/%m/%Y'),'') AS IssueDate ,ifnull(date_format(matDate,'%d/%m/%Y'),'') AS matDate , ls.EnteredBy,"
                    + "ifnull(AUTH,'N'), ifnull(ls.lienacno,'') From loansecurity ls, accountmaster am where ls.acno='" + acno + "' and sno=" + seqNo 
                    + " and ls.acno=am.acno and am.accstatus <>9 and (ls.auth is null OR ls.auth = 'N') order by ls.acno").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return checkList;
    }

    public String authorizeAction(String acno, String seqNo, String enterBy, String user, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            if (enterBy.equalsIgnoreCase(user)) {
                ut.rollback();
                return "You cannot Authorize your own entry";
            }

            Query updateQuery = em.createNativeQuery("Update loansecurity set Auth='Y' ,authby = '" + user.toUpperCase() + "' where acno = '" + acno + "' AND Sno=" + seqNo + "");
            int var = updateQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "AUTHORIZATION SUCCESSFUL";
            } else {
                ut.rollback();
                return "YOU CAN NOT AUTHORIZATION";
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List gridLoad(String brCode) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select CabNo,LockerType,LockerNo,KeyNo,Acno,CustCat,SECURITY,rent,mode,nomination,Remarks,enterby,Auth "
                    + "From lockeracmaster Where Auth='N' and brncode='" + brCode + "' Order By LockerNo");
            gridList = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gridList;
    }

    public String lockerIssueAuthorization(String lockerType, String cabNo, String lockerNo, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            List recList = em.createNativeQuery("Select lockerNo From lockeracmaster Where cabNo= " + cabNo + " and "
                    + " lockerType= '" + lockerType + "' and lockerNo= " + lockerNo + " and BrnCode = '" + brcode + "' and auth='N'").getResultList();
            if (recList.isEmpty()) {
                throw new ApplicationException("This transaction has been authorized.");
            }
            Query updateQuery = em.createNativeQuery("update lockeracmaster Set AuthBy= '" + enterBy + "',auth='Y' where cabNo= " + Float.parseFloat(cabNo)
                    + " and lockerType= '" + lockerType + "' and lockerNo= " + Float.parseFloat(lockerNo) + " and BrnCode = '" + brcode + "'  and auth='N'");
            int var = updateQuery.executeUpdate();
            if ((var <= 0)) {
                throw new ApplicationException("Sorry, Authorization Not Completed.");
            }
            ut.commit();
            return "Authorized Successfully!!!";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String lockerIssueDeletion(String lockerType, String cabNo, String lockerNo, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            List recList = em.createNativeQuery("Select lockerNo From lockeracmaster_his Where cabNo= " + cabNo + " and "
                    + " lockerType= '" + lockerType + "' and lockerNo= " + lockerNo + " and BrnCode = '" + brcode + "'and status='M'").getResultList();
            if (!recList.isEmpty()) {
                throw new ApplicationException("This transaction can not be deleted because it is modification authorization. You can only authorize this transaction.");
            }
            Query insertQuery = em.createNativeQuery("Insert Into lockeracmaster_his(cabno,LockerType,LockerNo,KeyNo,Acno,Rent,SECURITY,CustCat,mode,"
                    + "nomination,Remarks,Auth,enterby,ADOPERATOR1,ADOPERATOR2,ADOPERATOR3,ADOPERATOR4,brncode,trantime,status) "
                    + "select cabno,LockerType,LockerNo,KeyNo,Acno,Rent,SECURITY,CustCat,mode,nomination,Remarks,Auth,enterby,ADOPERATOR1,ADOPERATOR2,"
                    + "ADOPERATOR3,ADOPERATOR4,brncode,trantime,'D' from lockeracmaster where cabno=" + cabNo + " and lockerno=" + lockerNo
                    + " and lockertype='" + lockerType + "' and brncode='" + brcode + "'");
            int var1 = insertQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data updation.");
            }
            Query insertQuery1 = em.createNativeQuery("delete from lockeracmaster Where LockerType ='" + lockerType
                    + "' And LockerNo = " + lockerNo + " and cabno = " + cabNo + " and brncode='" + brcode + "'");
            var1 = insertQuery1.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data deletion.");
            }
            Query insertQuery2 = em.createNativeQuery("update lockerrent set status='D' Where LockerType ='" + lockerType
                    + "' And LockerNo = " + lockerNo + " and cabno = " + cabNo + " and brncode='" + brcode + "'");
            var1 = insertQuery2.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data deletion.");
            }
            Query updateQuery = em.createNativeQuery("Update lockermaster Set OcFlag='N' Where LockerType ='" + lockerType
                    + "' And LockerNo = " + lockerNo + " and cabno = " + cabNo + " and brncode='" + brcode + "'");
            var1 = updateQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data deletion.");
            }
            ut.commit();
            return "Transaction deleted successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }
    
    public List getTotLienOfAccount(String acno) throws ApplicationException {
        List totLienList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select ifnull(sum(lienvalue),0) from loansecurity Where Auth='Y' and status = 'ACTIVE' "
                    + " and acno ='" + acno + "'");
            totLienList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return totLienList;
    }
}
