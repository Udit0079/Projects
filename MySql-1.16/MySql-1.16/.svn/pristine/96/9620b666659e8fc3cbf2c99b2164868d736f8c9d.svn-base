/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.DisbursementSchedule;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Stateless(mappedName = "AdvancesInformationTrackingFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AdvancesInformationTrackingFacade implements AdvancesInformationTrackingRemote {

    @EJB
    CommonReportMethodsRemote common;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    LoanGenralFacadeRemote loanGenralFacade;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    LoanAccountsDetailsRemote loanAcDetails;
    @EJB
    AccountOpeningFacadeRemote accOpenFacade;
    @EJB
    LoanInterestCalculationFacadeRemote loanIntCalcFacade;
    @EJB
    InterBranchTxnFacadeRemote ibtFacade;

    public List getSector() throws ApplicationException {
        try {
            List listForSector = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='182' order by order_by").getResultList();
            return listForSector;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getSubSector(String sector) throws ApplicationException {
        try {
//            List listForSector = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='183'").getResultList();
            List listForSector;
            if (sector.equalsIgnoreCase("0")) {
                listForSector = em.createNativeQuery("select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.s_GNO and a.ref_rec_no = '183' order by a.order_by ").getResultList();
            } else {
//                listForSector = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='"+sector+"' order by order_by").getResultList();
                listForSector = em.createNativeQuery("select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.s_GNO and a.ref_rec_no = '183' and b.GNO = '" + sector + "' order by a.order_by").getResultList();
            }
            return listForSector;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getListAsPerRequirement(String refRecNo, String gNo, String sGNo, String ssGNo, String sssGNo, String ssssGNo, String date, float parameter) throws ApplicationException {
        try {
            String query = "";
            if (gNo.equalsIgnoreCase("0")) {
//                query = "select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='"+refRecNo+"' order by order_by";
                query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.GNO and a.ref_rec_no = '" + refRecNo + "' and '" + date + "' between a.eff_fr_dt and a.eff_to_dt  and '" + date + "' between b.eff_fr_dt and a.eff_to_dt and " + parameter + " between b.from_amt and b.to_amt order by b.order_by";
            } else {
                if (sGNo.equalsIgnoreCase("0")) {
                    query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.s_GNO and a.ref_rec_no = '" + refRecNo + "' and b.GNO = '" + gNo + "' and '" + date + "' between a.eff_fr_dt and a.eff_to_dt  and '" + date + "' between b.eff_fr_dt and a.eff_to_dt and " + parameter + " between b.from_amt and b.to_amt order by b.order_by";
                } else {
                    if (ssGNo.equalsIgnoreCase("0")) {
                        query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.ss_GNO and a.ref_rec_no = '" + refRecNo + "' and b.GNO = '" + gNo + "' and b.S_GNO = '" + sGNo + "' and '" + date + "' between a.eff_fr_dt and a.eff_to_dt  and '" + date + "' between b.eff_fr_dt and a.eff_to_dt  and " + parameter + " between b.from_amt and b.to_amt order by b.order_by";
                    } else {
                        if (sssGNo.equalsIgnoreCase("0")) {
                            query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.sss_GNO and a.ref_rec_no = '" + refRecNo + "' and b.GNO = '" + gNo + "' and b.S_GNO = '" + sGNo + "' and b.SS_GNO = '" + ssGNo + "' and '" + date + "' between a.eff_fr_dt and a.eff_to_dt  and '" + date + "' between b.eff_fr_dt and a.eff_to_dt  and " + parameter + " between b.from_amt and b.to_amt order by b.order_by";
                        } else {
                            if (ssssGNo.equalsIgnoreCase("0")) {
                                query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.ssss_GNO and a.ref_rec_no = '" + refRecNo + "' and b.GNO = '" + gNo + "' and b.S_GNO = '" + sGNo + "' and b.SS_GNO = '" + ssGNo + "' and b.SSS_GNO = '" + sssGNo + "' and '" + date + "' between a.eff_fr_dt and a.eff_to_dt  and '" + date + "' between b.eff_fr_dt and a.eff_to_dt  and " + parameter + " between b.from_amt and b.to_amt order by b.order_by";
                            } else {
                                query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.ssss_GNO and a.ref_rec_no = '" + refRecNo + "' and b.GNO = '" + gNo + "' and b.S_GNO = '" + sGNo + "' and b.SS_GNO = '" + ssGNo + "' and b.SSS_GNO = '" + sssGNo + "' and b.SSSS_GNO = '" + ssssGNo + "' and '" + date + "' between a.eff_fr_dt and a.eff_to_dt  and '" + date + "' between b.eff_fr_dt and a.eff_to_dt and " + parameter + " between b.from_amt and b.to_amt  order by b.order_by";
                            }
                        }
                    }
                }
            }
            List listForSector = em.createNativeQuery(query).getResultList();
            return listForSector;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List purposeOfAdvance(String refRecCode, String sector, String subSector, String modeOfAdvance) throws ApplicationException {
        try {
            String query = "";
            if (sector.equalsIgnoreCase("0")) {
                query = "select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='" + refRecCode + "' order by order_by";
            } else {
                if (subSector.equalsIgnoreCase("0")) {
                    query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.sss_GNO and a.ref_rec_no = '" + refRecCode + "' and b.GNO = '" + sector + "'  order by b.order_by";
                } else {
                    if (modeOfAdvance.equalsIgnoreCase("0")) {
                        query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.sss_GNO and a.ref_rec_no = '" + refRecCode + "' and b.GNO = '" + sector + "' and b.S_GNO = '" + subSector + "'  order by b.order_by";
                    } else {
                        query = "select distinct a.REF_CODE, a.REF_DESC from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.sss_GNO and a.ref_rec_no = '" + refRecCode + "' and b.GNO = '" + sector + "' and b.S_GNO = '" + subSector + "' and b.SS_GNO = '" + modeOfAdvance + "'  order by b.order_by";
                    }
                }
            }
            List listForpurpsAdvance = em.createNativeQuery(query).getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List modeOfAdvance() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='185'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getBankiinDetail() {
        return em.createNativeQuery("select iin,concat(bank_name,'---',iin) from bank_iin_detail where"
                + "(bank_name<>'' and bank_name is not null)and (iin<>'' and iin is not null) order by iin").getResultList();
    }

    public List getBankNameList() {
        List listforbankIin = em.createNativeQuery("select iin,concat(bank_name,'---',iin)  from bank_iin_detail where"
                + "(bank_name<>'' and bank_name is not null)and (iin<>'' and iin is not null) order by iin").getResultList();
        return listforbankIin;

    }

    public List typeOfAdvance() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='186' order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List secured() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='187'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List guarnteeCover() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='188'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List indusType() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='189'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List sickness() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='190'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List exposure() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='191' order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List exposureCategory() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='230' order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List exposureCategoryPurpose() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='231' order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List restructure() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='192'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List sanctionLevel() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='193'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List sanctionAuth() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='194'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List npaClass() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='195'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List courts() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='196'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List modeOfSetlement() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='197'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDWR() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='198'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List assetClassReason() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='199'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List populationGrp() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='200'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List interestTable() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='201'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List interestTableCode(String schemeCode) throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select interest_code,interest_code_description "
                    + "from cbs_loan_interest_code_master "
                    + "where interest_code = (select t.INTEREST_TABLE_CODE from cbs_scheme_loan_scheme_details l,cbs_scheme_tod_reference_details t where l.SCHEME_CODE='" + schemeCode + "' AND t.SCHEME_CODE='" + schemeCode + "')").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List interestType() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='202'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List schemeCode() throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='203'  order by order_by").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List schemeCodeDet(String schemeCode) throws ApplicationException {
        try {
            List listForpurpsAdvance = em.createNativeQuery("select scheme_code,scheme_description from cbs_scheme_general_scheme_parameter_master where scheme_code = '" + schemeCode + "'").getResultList();
            return listForpurpsAdvance;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List refRecCode(String refRecNo) throws ApplicationException {
        try {
            List listRefRecCode = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='" + refRecNo + "'  order by order_by").getResultList();
            return listRefRecCode;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List refRecDesc(String refRecCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no='416' and ref_code='" + refRecCode + "'  order by order_by").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List getDesignationPerson(String refRecNo, String designation) throws ApplicationException {
        try {
            List designationPersonList = em.createNativeQuery("select distinct b.PersonRefNo, concat( '[',b.PersonRefNo,'] ', trim(b.PersonName)) as name  "
                    + " from cbs_ref_rec_type a, member_Designation_mapping b where a.REF_CODE = b.DesignationCode and "
                    + " a.ref_rec_no = '" + refRecNo + "' and b.DesignationCode='" + designation + "' order by cast(b.PersonRefNo as decimal)").getResultList();
            return designationPersonList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List chkAcNo(String accNo) throws ApplicationException {
        try {
            List listRefRecCode = em.createNativeQuery("Select * from cbs_loan_borrower_details where ACC_NO='" + accNo + "'").getResultList();
            return listRefRecCode;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List custIdDetail(String customerId) throws ApplicationException {
        try {
            List listRefRecCode = em.createNativeQuery("select CustEntityType from cbs_customer_master_detail where customerid ='" + customerId + "' and SuspensionFlg<> 'N'").getResultList();
            return listRefRecCode;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List groupIdList() throws ApplicationException {
        try {
            List groupCustIdList = em.createNativeQuery("select distinct a.groupCustID, b.CustFullName from cbs_loan_borrower_details a,cbs_customer_master_detail b where a.groupCustID = b.customerid and b.SuspensionFlg <> 'N' and a.auth ='Y' and a.groupCustID is not null").getResultList();
            return groupCustIdList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List dirRelList(String custId, String dt) throws ApplicationException {
        try {
            List dirRelList = em.createNativeQuery("select custid ,dirRelation,desg,directorId,directorRelation,b.ref_desc as Relation from ( "
                    + " select custId,dirRelation,desg,ifnull(directorId,custId) as directorId ,b.ref_desc as directorRelation from banks_dir_details a, cbs_ref_rec_type b where a.dirRelation = b.ref_code  and b.ref_rec_no ='004'  and custid= '" + custId + "' and desg in ('DIR','DIRREL','SECREL','MGRREL') and (status ='A' or status is null) and '" + dt + "' between JoinDt and expDt "
                    + "union "
                    + " select custId,dirRelation,desg,ifnull(directorId,custId) as directorId ,b.ref_desc as directorRelation  from banks_dir_details_his a, cbs_ref_rec_type b  where  a.dirRelation = b.ref_code  and b.ref_rec_no ='004'  and  custid ='" + custId + "' and desg in ('DIR','DIRREL','SECREL','MGRREL') and status ='I'  and '" + dt + "' between JoinDt and expDt ) a, cbs_ref_rec_type b where a.desg= b.ref_code and   b.ref_rec_no ='210';").getResultList();
            return dirRelList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List isExist(String custId) throws ApplicationException {
        try {
            List acList = em.createNativeQuery("select distinct acc_no from cbs_loan_borrower_details where groupCustID = '" + custId + "' and groupid='2' and acc_no in (select acno from accountmaster where (closingdate is null or closingdate ='' or closingdate>'" + new Date() + "'))").getResultList();
            return acList;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }

    @Override
    public String saveCustDetails(String accNo, String sector, String subSector, String purposeOfAdvance, String modeOfAdvance, String typeOfAdvance,
            String secured, String guarnteeCover, String industryType, String sickness, String exposure, String restructuring,
            String sanctionLevel, String sanctionAuth, String courts, String modeOfSettle, String debtWaiverReason, String assetClassReason, String popuGroup, String intTable, String intType, String schemeCode, String npaClass, String userName, String netWorth, String marginMoney, String documentDate, String renewDate, String loanDuration,
            String documentExprDate, String relation, String sancAmount, String drawingPwrInd, String monthlyIncome, String applicantCategory,
            String categoryOpt, String minorCommunity, String remarks, String relName, String relOwner, String expCategory, String expCatPurpose, String dirCustId, String retirement_age, String industry, String groupType, String groupDetail, String businessIndustryType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        documentExprDate = documentExprDate.substring(6) + documentExprDate.substring(3, 5) + documentExprDate.substring(0, 2);
        documentDate = documentDate.substring(6) + documentDate.substring(3, 5) + documentDate.substring(0, 2);
        renewDate = renewDate.substring(6) + renewDate.substring(3, 5) + renewDate.substring(0, 2);
        try {
            ut.begin();
            String intAcOpenEnableInStaff = null;
            String acTypeOpenInStaff = null;
            String accCode = "";
            List chk = em.createNativeQuery("Select * from cbs_loan_borrower_details where ACC_NO='" + accNo + "'").getResultList();
            if (!chk.isEmpty()) {
                ut.rollback();
                throw new ApplicationException("Entries For this A/c. No. Already Exists,So You Can Only Modify  Entries By Selecting Function 'M'");
            }

            List selectId = em.createNativeQuery("select custid from customerid where acno='" + accNo + "'").getResultList();
            Vector vecId = (Vector) selectId.get(0);
            Query insertIntoLoanBorrowerDtls = em.createNativeQuery("insert into cbs_loan_borrower_details(cust_id,ACC_NO,SECTOR"
                    + ",SUB_SECTOR,PURPOSE_OF_ADVANCE,MODE_OF_ADVANCE,TYPE_OF_ADVANCE,SECURED,GUARANTEE_COVER,INDUSTRY_TYPE"
                    + ",SICKNESS,EXPOSURE,RESTRUCTURING,SANCTION_LEVEL,SANCTIONING_AUTHORITY,INTEREST_TABLE,INTEREST_TYPE,SCHEME_CODE"
                    + ",NPA_CLASSIFICATION,COURTS,MODE_OF_SETTLEMENT,DEBT_WAIVER_REASON,ASSET_CLASS_REASON,POPULATION_GROUP"
                    + ",CREATED_BY_USER_ID,CREATION_DATE,LAST_UPDATED_BY_USER_ID,TOTAL_MODIFICATIONS,NET_WORTH,MARGIN_MONEY,DOCUMENT_DATE"
                    + ",RENEWAL_DATE,LOAN_DURATION,DOCUMENT_EXP_DATE,RELATION,SANCTION_AMT,DRAWING_POWER_INDICATOR,MONTHLY_INCOME"
                    + ",APPLICANT_CATEGORY,CATEGORY_OPT,MINOR_COMMUNITY,REMARKS,REL_NAME,REL_WITH_AC_HOLDER,dir_cust_id,retirement_age,industry,groupID ,groupCustID,BUSINESS_INDUSTRY_TYPE ) values('" + vecId.get(0).toString() + "','" + accNo + "'"
                    + ",'" + sector + "','" + subSector + "','" + purposeOfAdvance + "','" + modeOfAdvance + "','" + typeOfAdvance + "','" + secured + "'"
                    + ",'" + guarnteeCover + "','" + industryType + "','" + sickness + "','" + exposure + "','" + restructuring + "','" + sanctionLevel + "'"
                    + ",'" + sanctionAuth + "','" + intTable + "','" + intType + "','" + schemeCode + "','" + npaClass + "','" + courts + "','" + modeOfSettle + "'"
                    + ",'" + debtWaiverReason + "','" + assetClassReason + "','" + popuGroup + "','" + userName + "','" + df.format(date) + "','','0'"
                    + "," + Double.parseDouble(netWorth) + "," + marginMoney + ",'" + documentDate + "','" + renewDate + "','" + loanDuration + "','" + documentExprDate + "'"
                    + ",'" + relation + "'," + Double.parseDouble(sancAmount) + ",'" + drawingPwrInd + "'," + Double.parseDouble(monthlyIncome) + ",'" + applicantCategory + "'"
                    + ",'" + categoryOpt + "','" + minorCommunity + "','" + remarks + "','" + relName + "','" + relOwner + "' ,'" + dirCustId + "' ,'" + retirement_age + "','" + industry + "','" + groupType + "','" + groupDetail + "','" + businessIndustryType + "')");
            int resultLoanBorrowerTable = insertIntoLoanBorrowerDtls.executeUpdate();
            if (resultLoanBorrowerTable <= 0) {
                throw new ApplicationException("Error in posting data in LoanBorrowerDetail table..");
            }
            Query upQry = em.createNativeQuery("update cbs_loan_acc_mast_sec set loan_pd_month = " + loanDuration + " where acno='" + accNo + "'");
            int rs = upQry.executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Error in updating data in cbs_loan_acc_mast_sec table..");
            }
            accCode = accNo.substring(2, 4);
            List chk10 = em.createNativeQuery(" select Int_Ac_Open_Enable_In_Staff,ifnull(Ac_Type_Open_In_Staff,'') as actypeOpenInstaff from accounttypemaster where acctcode ='" + accCode + "'").getResultList();
            Vector recLst10 = (Vector) chk10.get(0);
            intAcOpenEnableInStaff = recLst10.get(0).toString();
            if (!recLst10.get(1).toString().equalsIgnoreCase("")) {
                acTypeOpenInStaff = recLst10.get(1).toString();
                if (intAcOpenEnableInStaff.equalsIgnoreCase("Y")) {
                    List chkIntAcnoList = em.createNativeQuery("select ifnull(INT_TRF_ACNO,'') from cbs_loan_acc_mast_sec where acno = '" + accNo + "'").getResultList();
                    Vector chkIntAcnoVect = (Vector) chkIntAcnoList.get(0);
                    String intTrfAcno = chkIntAcnoVect.get(0).toString();
                    if (!(intTrfAcno.isEmpty() || intTrfAcno.equalsIgnoreCase(""))) {
                        Query insertIntoLoanBorrowerDtlsTrfAcno = em.createNativeQuery("insert into cbs_loan_borrower_details(cust_id,ACC_NO,SECTOR"
                                + ",SUB_SECTOR,PURPOSE_OF_ADVANCE,MODE_OF_ADVANCE,TYPE_OF_ADVANCE,SECURED,GUARANTEE_COVER,INDUSTRY_TYPE"
                                + ",SICKNESS,EXPOSURE,RESTRUCTURING,SANCTION_LEVEL,SANCTIONING_AUTHORITY,INTEREST_TABLE,INTEREST_TYPE,SCHEME_CODE"
                                + ",NPA_CLASSIFICATION,COURTS,MODE_OF_SETTLEMENT,DEBT_WAIVER_REASON,ASSET_CLASS_REASON,POPULATION_GROUP"
                                + ",CREATED_BY_USER_ID,CREATION_DATE,LAST_UPDATED_BY_USER_ID,TOTAL_MODIFICATIONS,NET_WORTH,MARGIN_MONEY,DOCUMENT_DATE"
                                + ",RENEWAL_DATE,LOAN_DURATION,DOCUMENT_EXP_DATE,RELATION,SANCTION_AMT,DRAWING_POWER_INDICATOR,MONTHLY_INCOME"
                                + ",APPLICANT_CATEGORY,CATEGORY_OPT,MINOR_COMMUNITY,REMARKS,REL_NAME,REL_WITH_AC_HOLDER,dir_cust_id,retirement_age,industry,groupID ,groupCustID,BUSINESS_INDUSTRY_TYPE ) values('" + vecId.get(0).toString() + "','" + intTrfAcno + "'"
                                + ",'" + sector + "','" + subSector + "','" + purposeOfAdvance + "','" + modeOfAdvance + "','" + typeOfAdvance + "','" + secured + "'"
                                + ",'" + guarnteeCover + "','" + industryType + "','" + sickness + "','" + exposure + "','" + restructuring + "','" + sanctionLevel + "'"
                                + ",'" + sanctionAuth + "','" + intTable + "','" + intType + "','" + schemeCode + "','" + npaClass + "','" + courts + "','" + modeOfSettle + "'"
                                + ",'" + debtWaiverReason + "','" + assetClassReason + "','" + popuGroup + "','" + userName + "','" + df.format(date) + "','','0'"
                                + "," + Double.parseDouble(netWorth) + "," + marginMoney + ",'" + documentDate + "','" + renewDate + "','" + loanDuration + "','" + documentExprDate + "'"
                                + ",'" + relation + "'," + Double.parseDouble(sancAmount) + ",'" + drawingPwrInd + "'," + Double.parseDouble(monthlyIncome) + ",'" + applicantCategory + "'"
                                + ",'" + categoryOpt + "','" + minorCommunity + "','" + remarks + "','" + relName + "','" + relOwner + "' ,'" + dirCustId + "' ,'" + retirement_age + "','" + industry + "','" + groupType + "','" + groupDetail + "','" + businessIndustryType + "')");
                        int resultLoanBorrowerTableTrfAcno = insertIntoLoanBorrowerDtlsTrfAcno.executeUpdate();
                        if (resultLoanBorrowerTableTrfAcno <= 0) {
                            throw new ApplicationException("Error in posting data of Staff LoanAcc in LoanBorrowerDetail table..");
                        }
                    }
                }
            } else {
                acTypeOpenInStaff = "";
            }
            ut.commit();
            return "Data has been posted successfully...";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String modifyCustDetails(String accNo, String sector, String subSector, String purposeOfAdvance, String modeOfAdvance, String typeOfAdvance,
            String secured, String guarnteeCover, String industryType, String sickness, String exposure, String restructuring,
            String sanctionLevel, String sanctionAuth, String courts, String modeOfSettle, String debtWaiverReason, String assetClassReason, String popuGroup, String intTable, String intType, String schemeCode, String npaClass, String userName, String netWorth, String marginMoney, String documentDate, String renewDate, String loanDuration,
            String documentExprDate, String relation, String sancAmount, String drawingPwrInd, String monthlyIncome, String applicantCategory,
            String categoryOpt, String minorCommunity, String remarks, String relName, String relOwner, String expCategory, String expCatPurpose, String dirCustId, String retirement_age, String industry, String groupType, String groupDetail, String businessIndustryType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        double oldOdLimit = 0d;
        documentExprDate = documentExprDate.substring(6) + documentExprDate.substring(3, 5) + documentExprDate.substring(0, 2);
        documentDate = documentDate.substring(6) + documentDate.substring(3, 5) + documentDate.substring(0, 2);
        renewDate = renewDate.substring(6) + renewDate.substring(3, 5) + renewDate.substring(0, 2);
        int maxMod;
        try {
            ut.begin();
            List selectCustId = em.createNativeQuery("select custid from customerid where acno='" + accNo + "'").getResultList();// Added on 27/12/2010 by Nishant Kansal
            Vector vecForCustId = (Vector) selectCustId.get(0);
            Query insertIntoLoanBorrowerDtls = em.createNativeQuery("insert into cbs_loan_borrower_details_history (select * from cbs_loan_borrower_details where cust_id = '" + vecForCustId.get(0).toString() + "' and acc_no = '" + accNo + "')");
            int resultLoanBorrowerTable = insertIntoLoanBorrowerDtls.executeUpdate();
            if (resultLoanBorrowerTable <= 0) {
                throw new ApplicationException("Error in posting data in LoanBorrowerDetail table..");
            }
            List selectMaxNoOfMod = em.createNativeQuery("select ifnull(max(TOTAL_MODIFICATIONS),0) from cbs_loan_borrower_details where cust_id='" + vecForCustId.get(0).toString() + "' and acc_no='" + accNo + "'").getResultList();
            Vector vecForMaxMod = (Vector) selectMaxNoOfMod.get(0);
            String strForMaxMod = vecForMaxMod.get(0).toString();
            maxMod = Integer.parseInt(strForMaxMod) + 1;

            Query updateLoanBorrowerDtls = em.createNativeQuery("update cbs_loan_borrower_details set SECTOR='" + sector + "',SUB_SECTOR='" + subSector + "',PURPOSE_OF_ADVANCE='" + purposeOfAdvance + "',MODE_OF_ADVANCE='" + modeOfAdvance + "',TYPE_OF_ADVANCE='" + typeOfAdvance + "',SECURED='" + secured + "',GUARANTEE_COVER='" + guarnteeCover + "',INDUSTRY_TYPE='" + industryType + "',SICKNESS='" + sickness + "',EXPOSURE='" + exposure + "',RESTRUCTURING='" + restructuring + "',SANCTION_LEVEL='" + sanctionLevel + "',SANCTIONING_AUTHORITY='" + sanctionAuth + "',INTEREST_TABLE='" + intTable + "',INTEREST_TYPE='" + intType + "',SCHEME_CODE='" + schemeCode + "',NPA_CLASSIFICATION='" + npaClass + "',COURTS='" + courts + "',MODE_OF_SETTLEMENT='" + modeOfSettle + "',DEBT_WAIVER_REASON='" + debtWaiverReason + "',ASSET_CLASS_REASON='" + assetClassReason + "',POPULATION_GROUP='" + popuGroup + "',LAST_UPDATED_BY_USER_ID='" + userName + "',LAST_UPDATED_DATE= now(),TOTAL_MODIFICATIONS=" + maxMod + ""
                    + ",NET_WORTH=" + Double.parseDouble(netWorth) + ",MARGIN_MONEY=" + Double.parseDouble(marginMoney) + ",DOCUMENT_DATE='" + documentDate + "',RENEWAL_DATE='" + renewDate + "',LOAN_DURATION='" + loanDuration + "'"
                    + ",DOCUMENT_EXP_DATE='" + documentExprDate + "',RELATION='" + relation + "',SANCTION_AMT=" + Double.parseDouble(sancAmount) + ""
                    + ",DRAWING_POWER_INDICATOR='" + drawingPwrInd + "',MONTHLY_INCOME=" + Double.parseDouble(monthlyIncome) + ",APPLICANT_CATEGORY='" + applicantCategory + "'"
                    + ",CATEGORY_OPT='" + categoryOpt + "',MINOR_COMMUNITY='" + minorCommunity + "'"
                    + ",REMARKS='" + remarks + "',REL_NAME='" + relName + "',REL_WITH_AC_HOLDER='" + relOwner + "',EXP_CAT='" + expCategory + "',EXP_CAT_PUR='" + expCatPurpose + "' , dir_cust_id ='" + dirCustId + "',retirement_age='" + retirement_age + "',industry ='" + industry + "' ,groupID ='" + groupType + "',"
                    + "groupCustID ='" + groupDetail + "',BUSINESS_INDUSTRY_TYPE = '" + businessIndustryType + "'  where cust_id='" + vecForCustId.get(0).toString() + "' and acc_no='" + accNo + "' ");
            int resultUpdateLoanBorroDtls = updateLoanBorrowerDtls.executeUpdate();
            if (resultUpdateLoanBorroDtls <= 0) {
                throw new ApplicationException("Error in updating data in LoanBorrowerDetail table..");
            }
            Query upQry = em.createNativeQuery("update cbs_loan_acc_mast_sec set loan_pd_month = " + loanDuration + " where acno='" + accNo + "'");
            int rs = upQry.executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Error in updating data in cbs_loan_acc_mast_sec table..");
            }
            List acctInfo = loanAcDetails.accountDetail(accNo);
            if (!acctInfo.isEmpty()) {
                Vector acInfoVect = (Vector) acctInfo.get(0);
                oldOdLimit = Double.parseDouble(acInfoVect.get(4).toString());
            }
            String tlLimitUpdation = "N";
            List tlLimitUpdationList = getCbsParameterinfoValue("TL_LIMIT_INCREASE");
            if (!tlLimitUpdationList.isEmpty()) {
                Vector tlLimitUpdationVect = (Vector) tlLimitUpdationList.get(0);
                tlLimitUpdation = tlLimitUpdationVect.get(0).toString();
            }
            if (tlLimitUpdation.equalsIgnoreCase("Y") && (common.getAcNatureByAcType(accNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                if (oldOdLimit != Double.parseDouble(sancAmount)) {
                    if (!restructuring.equalsIgnoreCase("0")) {
                        List schList = new ArrayList();
                        schList = accOpenFacade.chkSchDetails(schemeCode);
                        if (!schList.isEmpty()) {
                            Vector schLst = (Vector) schList.get(0);
                            String schFlg = schLst.get(0).toString();
                            String stOpt = schLst.get(1).toString();
                            String memFlg = schLst.get(2).toString();
                            String folioNo = "";
                            if (schFlg.equalsIgnoreCase("Y")) {
                                if (memFlg.equalsIgnoreCase("Y")) {
                                    List memChk = em.createNativeQuery("select * from share_holder where custid ='" + vecForCustId.get(0).toString() + "'").getResultList();
                                    if (memChk.isEmpty()) {
                                        //                            ut.rollback();
                                        throw new ApplicationException("SCHEME IS ONLY FOR THE MEMBER CUSTOMER !!!");
                                    }
                                }
                                /*Commented due to Army don't require at the time of account opening********/
                                /*List shareChk = em.createNativeQuery("select reg_pass_sheet_print_event from cbs_scheme_currency_details where scheme_Code ='" + schemeCode + "'").getResultList();
                                 if (!shareChk.isEmpty()) {
                                 Vector shareLst = (Vector) shareChk.get(0);
                                 shareFlg = shareLst.get(0).toString();
                                 if (shareFlg.equalsIgnoreCase("Y")) {
                                 String cmpSH = accOpenFacade.shareCompare(cust_id, Odlimit, schemeCode);
                                 if (!cmpSH.equalsIgnoreCase("true")) {
                                 //                                ut.rollback();
                                 message = "SHARE IS NOT SUFFICIENT FOR THE LOAN !!!";
                                 return message;
                                 }
                                 }
                                 }*/
                                double odAmt = 0, outstanding = 0;
                                String amtChk = null;
                                List sList = em.createNativeQuery("select ifnull(sum(odlimit),0), a.acno from accountmaster a, customerid cu where a.acno in ("
                                        + " select acno from cbs_loan_acc_mast_sec where scheme_code in ("
                                        + " select scheme_code from cbs_scheme_general_scheme_parameter_master where eefc_scheme_flag = 'Y' and scheme_code = '" + schemeCode + "')) and a.acno = cu.acno "
                                        + " and cu.custid in (select CustId from customerid where acno =  '" + accNo + "') and accstatus<>9 "
                                        + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','DL') and CrDbFlag in('B','D')) group by a.acno").getResultList();
                                if (!sList.isEmpty()) {
                                    for (int l = 0; l < sList.size(); l++) {
                                        Vector v1 = (Vector) sList.get(l);
                                        outstanding = Double.parseDouble(loanIntCalcFacade.outStandingAsOnDate(v1.get(1).toString(), ymd.format(new Date())));
                                        odAmt = odAmt + (outstanding < 0 ? Math.abs(outstanding) : (outstanding * -1));
                                    }
                                }
                                if (Double.parseDouble(sancAmount) >= odAmt) {
                                    amtChk = String.valueOf(Double.parseDouble(sancAmount) - odAmt);
                                }
                                String cCmp = accOpenFacade.loanAmtCompare(vecForCustId.get(0).toString(), Float.parseFloat(amtChk), schemeCode, folioNo.equalsIgnoreCase("") ? "msg" : "", accNo);
                                /**
                                 * **IF folioNo is blank THEN method return in
                                 * the form of Message ELSE method return in the
                                 * OD value as per calculation ****
                                 */
                                if (!folioNo.equalsIgnoreCase("")) {
                                    sancAmount = cCmp;
                                } else {
                                    if (!cCmp.equalsIgnoreCase("true")) {
                                        //                        ut.rollback();
                                        throw new ApplicationException(cCmp);
                                    } else {
                                        String result = loanGenralFacade.odEnhancementSave(accNo, fts.getAccountCode(accNo), 0, 0, 0, 0, Float.parseFloat(String.valueOf(oldOdLimit)), Float.parseFloat(sancAmount), Float.parseFloat(String.valueOf(oldOdLimit)), Float.parseFloat(sancAmount), 0, 0, 0, 0, "19000101", "19000101", "19000101", "19000101", 0, 0, "19000101", "19000101", "M", "", ymd.format(new Date()));
                                        if (!result.equalsIgnoreCase("Record Saved Successfully")) {
                                            throw new ApplicationException(result);
                                        }
                                    }
                                }
                            }
                        } else {
                            //                ut.rollback();
                            throw new ApplicationException("SCHEME CODE NOT DEFINED IN MASTER TABLE !!!");
                        }
                    }
                }
            }
            ut.commit();
            return "Data has been updated successfully...";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List selectCustDetails(String accNo) throws ApplicationException {
        try {
            List selectFromLoanBorrowerDetails = em.createNativeQuery("select SECTOR,SUB_SECTOR,PURPOSE_OF_ADVANCE,MODE_OF_ADVANCE,TYPE_OF_ADVANCE,SECURED,GUARANTEE_COVER,INDUSTRY_TYPE,SICKNESS,EXPOSURE,RESTRUCTURING,SANCTION_LEVEL,SANCTIONING_AUTHORITY,INTEREST_TABLE,INTEREST_TYPE,SCHEME_CODE,NPA_CLASSIFICATION,COURTS,MODE_OF_SETTLEMENT,DEBT_WAIVER_REASON,ASSET_CLASS_REASON,POPULATION_GROUP,CREATED_BY_USER_ID,CREATION_DATE,LAST_UPDATED_BY_USER_ID,ifnull(LAST_UPDATED_DATE,'')as LAST_UPDATED_DATE,TOTAL_MODIFICATIONS,ifnull(NET_WORTH,'0.0')as NET_WORTH,ifnull(MARGIN_MONEY,'0.0')as MARGIN_MONEY,coalesce(DOCUMENT_DATE,'')as DOCUMENT_DATE,coalesce(RENEWAL_DATE,'')as RENEWAL_DATE,ifnull(LOAN_DURATION,'')as LOAN_DURATION,coalesce(DOCUMENT_EXP_DATE,'')as DOCUMENT_EXP_DATE,ifnull(RELATION,'')as RELATION,ifnull(SANCTION_AMT,'0.0')as SANCTION_AMT,ifnull(DRAWING_POWER_INDICATOR,'')as DRAWING_POWER_INDICATOR,ifnull(MONTHLY_INCOME,'0.0')as MONTHLY_INCOME,ifnull(APPLICANT_CATEGORY,'')as APPLICANT_CATEGORY,ifnull(CATEGORY_OPT,'')as CATEGORY_OPT,ifnull(MINOR_COMMUNITY,'')as MINOR_COMMUNITY,ifnull(REMARKS,'')as REMARKS,ifnull(REL_NAME,''), ifnull(REL_WITH_AC_HOLDER,''), ifnull(EXP_CAT,''), ifnull(EXP_CAT_PUR,''), ifnull(industry,''),ifnull(groupID,''),ifnull(groupCustID,''),ifnull(BUSINESS_INDUSTRY_TYPE,'') from cbs_loan_borrower_details where acc_no='" + accNo + "'").getResultList();
            return selectFromLoanBorrowerDetails;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getReferenceCode1(String refRecNo) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select REF_CODE,ref_desc from cbs_ref_rec_type WHERE REF_REC_NO = '" + refRecNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getCbsParameterinfoValue(String name) throws ApplicationException {
        List cbsParameterList = null;
        try {
            cbsParameterList = em.createNativeQuery("select code from cbs_parameterinfo where name = '" + name + "'").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return cbsParameterList;
    }

    public List getDetailsFromAccMaster(String accNo) throws ApplicationException {
        List listForAccMaster = em.createNativeQuery("select acno,custname,OpeningDt,AccStatus from accountmaster where acno ='" + accNo + "'").getResultList();
        return listForAccMaster;
    }
    /**
     * *********FROM HERE THIS IS SHIPRA'S EJB CODE****************************
     */
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat d_my = new SimpleDateFormat("dd-MM-yyyy");

    public List getappLoanDetails(String acno) throws ApplicationException {
        List appLoanDetails = new ArrayList();
        try {
            Query resultlist = em.createNativeQuery("select amtsanctioned,date_format(sanctiondt,'%d/%m/%Y'),processingfee,remarks from apploandetails where acno = '" + acno + "'");
            appLoanDetails = resultlist.getResultList();
        } catch (Exception ex) {

            throw new ApplicationException(ex);
        }
        return appLoanDetails;
    }

    public List getDisbursementDetail(String acno) throws ApplicationException {
        List appLoanDetails = new ArrayList();
        try {
            Query resultlist = em.createNativeQuery("select sno,amtdisbursed,date_format(disbursementdt,'%d/%m/%Y') from loandisbursement  where acno = '" + acno + "'");
            appLoanDetails = resultlist.getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return appLoanDetails;
    }

    public List getDisbursementSchedule(String acno) throws ApplicationException {
        List appLoanDetails = new ArrayList();
        try {
            Query resultlist = em.createNativeQuery("select FLOW_ID,date_format(DATE,'%d/%m/%Y'),AMT,RMKS,DEL_FLG,SHDL_NUM from cbs_loan_disbursement_schedule  where acno = '" + acno + "'");
            appLoanDetails = resultlist.getResultList();

        } catch (Exception ex) {

            throw new ApplicationException(ex);
        }
        return appLoanDetails;
    }

    public List getReferenceCode(String refRecNo) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select REF_CODE,ref_desc from cbs_ref_rec_type WHERE REF_REC_NO = '" + refRecNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public String daybeginDate(String orgnBrCode) throws ApplicationException {
        try {
            String tempBd;
            List dateList = em.createNativeQuery("select date from bankdays where dayendflag = 'N'"/* and brncode = '"+orgnBrCode+"'"*/).getResultList();
            if (dateList.size() <= 0) {
                return "Check the Day Begin/Day End";
            } else {
                Vector dateVect = (Vector) dateList.get(0);
                tempBd = (String) dateVect.get(0);
            }
            return tempBd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveDisbursementSchedule(List table, String orgnBrCode, String todayDate, String acno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String tempBd = "";
        String result = "";
        Integer sno = 0;
        Integer snoUpdate = 0;
        String deleteFlag;
        String deleteFlagDisburest = null;
        String saveUpdateFlag = null;
        List<DisbursementSchedule> schedule = table;
        String INFO = "";
        int saveSno = 0;
        try {
            ut.begin();

            tempBd = daybeginDate(orgnBrCode);

            if (ymd.parse(tempBd).after(ymd.parse(todayDate)) || ymd.parse(tempBd).before(ymd.parse(todayDate))) {
                ut.rollback();
                return "Check the today date you have passed";
            }
            List updatesnoList = em.createNativeQuery("select SHDL_NUM,del_flg from cbs_loan_disbursement_schedule where ACNO = '" + acno + "'").getResultList();
            if (updatesnoList.isEmpty()) {
                saveSno = 1;
            } else {
                Vector snoVect = (Vector) updatesnoList.get(0);
                sno = Integer.parseInt(snoVect.get(0).toString());
                snoUpdate = sno + 1;
                saveSno = sno + 1;

                deleteFlag = snoVect.get(1).toString();
            }

            for (int i = 0; i < schedule.size(); i++) {
                Double disbursetmentAmt = Double.parseDouble(schedule.get(i).getDisbursetmentAmt());
                String disbursetmentDate = ymd.format(dmy.parse(schedule.get(i).getDisbursetmentDate()));
                deleteFlagDisburest = schedule.get(i).getDeleteFlagDisburest();

                String flowId = schedule.get(i).getFlowId();
                String remarksDisbursment = schedule.get(i).getRemarksDisbursment();

                saveUpdateFlag = schedule.get(i).getCounterSaveUpdate();
                if (saveUpdateFlag.equalsIgnoreCase("Save")) {
                    int entry = em.createNativeQuery("insert into cbs_loan_disbursement_schedule(ACNO,SHDL_NUM,FLOW_ID,DATE,AMT,RMKS,DEL_FLG) values ('" + acno + "','" + saveSno + "','" + flowId + "','" + disbursetmentDate + "'," + disbursetmentAmt + ",'" + remarksDisbursment + "','" + deleteFlagDisburest + "')").executeUpdate();

                    if (entry <= 0) {
                        ut.rollback();
                        result = "Data is not inserted cbs_loan_disbursement_schedule";
                        return (result);
                    }
                }

                if (saveUpdateFlag.equalsIgnoreCase("Update")) {
                    if (deleteFlagDisburest.equalsIgnoreCase("Y")) {
                        Integer upadteDisbursementList = em.createNativeQuery("update cbs_loan_disbursement_schedule "
                                + "set  DEL_FLG = '" + deleteFlagDisburest + "'   where ACNO = '" + acno + "' and FLOW_ID = '" + flowId + "' and DATE ='" + disbursetmentDate + "'").executeUpdate();
                        if (upadteDisbursementList <= 0) {
                            result = "Data is not Updated in cbs_loan_disbursement_schedule";
                            ut.rollback();
                            return (result);
                        }
                    } else {
                        Integer upadteHistoryList = em.createNativeQuery("insert into cbs_loan_disbursement_schedule_History select * "
                                + "from cbs_loan_disbursement_schedule where ACNO = '" + acno + "'").executeUpdate();
                        if (upadteHistoryList <= 0) {
                            result = "Data is not Updated in cbs_loan_disbursement_schedule_History";
                            ut.rollback();
                        }
                        //      sno = sno + 1;
                        Integer upadteDisbursementList = em.createNativeQuery("update cbs_loan_disbursement_schedule "
                                + "set RMKS = '" + remarksDisbursment + "',AMT =" + disbursetmentAmt + ", DEL_FLG = '" + deleteFlagDisburest + "' where ACNO = '" + acno + "' and FLOW_ID = '" + flowId + "' and DATE ='" + disbursetmentDate + "'").executeUpdate();
                        if (upadteDisbursementList <= 0) {
                            result = "Data is not Updated in cbs_loan_disbursement_schedule";
                            ut.rollback();
                        }
                    }
                }

            }
            if (saveUpdateFlag.equalsIgnoreCase("Update")) {
                if (!deleteFlagDisburest.equalsIgnoreCase("Y")) {
                    Integer upadteDisburseList = em.createNativeQuery("update cbs_loan_disbursement_schedule "
                            + "set SHDL_NUM = " + snoUpdate + " where ACNO = '" + acno + "'").executeUpdate();
                    if (upadteDisburseList <= 0) {
                        result = "Data is not Updated in cbs_loan_disbursement_schedule";
                        ut.rollback();
                        //  return (result);
                    }
                }
            }

            if (saveUpdateFlag.equalsIgnoreCase("save")) {
                if (saveSno != 1) {
                    Integer upadteDisburseList = em.createNativeQuery("update cbs_loan_disbursement_schedule "
                            + "set SHDL_NUM = " + snoUpdate + " where ACNO = '" + acno + "'").executeUpdate();
                    if (upadteDisburseList <= 0) {
                        result = "Data is not Updated in cbs_loan_disbursement_schedule";

                        ut.rollback();
                        //  return (result);
                    }
                }
            }

            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }

    }

    /**
     * ******************************************* RepaymentSchedule
     * ***********************************************************
     */
    public List getInstallmentPlan(String acno) throws ApplicationException {
        List appLoanDetails = new ArrayList();
        try {
            Query resultlist = em.createNativeQuery("select sno,coalesce(date_format(duedt,'%d/%m/%Y'),''),prinamt,interestamt,installamt,status,coalesce(date_format(PAYMENTDT,'%d/%m/%Y'),''),coalesce(enterby,''),coalesce(Remarks,'') from emidetails where acno = '" + acno + "' order by sno ");

            appLoanDetails = resultlist.getResultList();

        } catch (Exception ex) {

            throw new ApplicationException(ex);
        }
        return appLoanDetails;
    }

    public List getSanctionAmt(String acno) throws ApplicationException {
        List appLoanDetails = new ArrayList();
        try {
            Query resultlist = em.createNativeQuery("select cast(ifnull(odlimit,0) as decimal(25,2)) sanct from loan_appparameter where acno='" + acno + "'");
            appLoanDetails = resultlist.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return appLoanDetails;
    }

    public List getOsBalance(String acno) throws ApplicationException {
        List osDetailDetails = new ArrayList();
        try {
            Query loanlist = em.createNativeQuery("select  ifnull((sum(dramt) -sum(cramt)),0) from loan_recon  where acno='" + acno + "'");
            osDetailDetails = loanlist.getResultList();
        } catch (Exception ex) {

            throw new ApplicationException(ex);
        }
        return osDetailDetails;
    }

    public double repaymentOpeingPrinciple(String acNo) {
        double balance = 0;
        try {
            List checkEmi = em.createNativeQuery("select acno from emirescheduled where acno = '" + acNo + "'").getResultList();
            if (!checkEmi.isEmpty()) {
                List checkEmiPaid = em.createNativeQuery("select cast(ifnull(ODLimit,0) as decimal(25,2)), date_format(min(b.DUEDT), '%Y%m%d') from accountmaster a, emidetails b where a.acno = b.acno and a.acno ='" + acNo + "' ").getResultList();
                if (!checkEmiPaid.isEmpty()) {
                    Vector checkEmiPaidVect = (Vector) checkEmiPaid.get(0);
                    String dueDt = checkEmiPaidVect.get(1).toString();
                    balance = Double.parseDouble(loanIntCalcFacade.outStandingAsOnDate(acNo, dueDt)) * -1;
                    if (balance <= 0) {
                        balance = Double.parseDouble(checkEmiPaidVect.get(0).toString());
                    }
                }
            } else {
                checkEmi = em.createNativeQuery("select cast(ifnull(ODLimit,0) as decimal(25,2)) from accountmaster where acno = '" + acNo + "'").getResultList();
                Vector chkVect = (Vector) checkEmi.get(0);
                balance = Double.parseDouble(chkVect.get(0).toString());
            }

        } catch (Exception ex) {
            try {
                throw new ApplicationException(ex);
            } catch (ApplicationException ex1) {
                Logger.getLogger(AdvancesInformationTrackingFacade.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return balance;
    }

    public String CalculateEMI(Integer n1, Integer Pd, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, String Periodicity) throws ApplicationException {
        try {
            List arraylist = new ArrayList();
            int X = 0;
            double K1;
            double K2;
            double K3;
            double a1;
            double a2;
            double TmpPrin;
            double TmpInterest;
            double TmpTotal = 0;
            String EMIDate = EMIdt;
            double openingPrin = tmpAmt;

            int pd1 = Pd;
            K1 = TmpROI / (n1 * 100);
            K2 = 1 + K1;
            K3 = Math.pow(K2, n);
            a1 = (K1 * K3) / (K3 - 1);
            X = 1;
            a2 = tmpAmt * a1;
            //TmpInstall = tmpAmt * K1;

            String a[][] = new String[n][8];
            for (int i = 0; i < n; i++) {
                TmpInterest = Math.round(tmpAmt * K1);
                TmpPrin = Math.round(a2 - TmpInterest);
                tmpAmt = (float) (tmpAmt - a2 + TmpInterest);

                a[i][0] = Integer.toString(X);
                a[i][1] = EMIdt.substring(6, 8) + "/" + EMIdt.substring(4, 6) + "/" + EMIdt.substring(0, 4);
                a[i][2] = Double.toString(TmpPrin);
                a[i][3] = Double.toString(TmpInterest);
                a[i][4] = Double.toString(Math.round(a2));
                a[i][5] = Periodicity + ":";
                a[i][6] = Double.toString(Math.round(openingPrin));
                openingPrin = openingPrin - TmpPrin;
                a[i][7] = Double.toString(Math.round(openingPrin));

                TmpTotal = TmpTotal + Math.round(TmpInterest);
                List chkList7;
                if (Pd == 7 || (Pd == 1 && n1 == 365)) {
                    chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " DAY),'%Y%m%d')").getResultList();
                } else {
                    chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " MONTH ),'%Y%m%d')").getResultList();
                }
                Vector Lst7 = (Vector) chkList7.get(0);
                EMIdt = Lst7.get(0).toString();
                X = X + 1;
                pd1 = pd1 + Pd;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 8; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
            return arraylist.toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    // added by Rahul date 03/11/2018

    public String CalculateEmiAtSimpleInterest(Integer n1, Integer Pd, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, String Periodicity) throws ApplicationException {
        List arraylist = new ArrayList();
        try {
            int X = 0;
            int pd1 = Pd;
            int timerate = 1;
            double simpleInteresttmp = 0.0;
            double simpleInterest = 0.0;
            double totalAmt = 0.0;
            double divInstalment = 0.0;
            double tempprin = 0.0;
            double pendingAmt = 0.0;
            double TmpTotal = 0;
            String EMIDate = EMIdt;
            String TempEmi = EMIdt;
            double openingPrin = tmpAmt;
            double finalInstallment = 0.0, grandTotalPrin = 0.0, grandTotalInt = 0.0, grandInstallment = 0.0, grandDiff = 1;

            divInstalment = tmpAmt / n;
            totalAmt = simpleInterest + divInstalment;
            X = 1;
            String a[][] = new String[n][8];
            while (Math.round(grandDiff) < 0 || Math.round(grandDiff) > 0) {
                EMIdt = TempEmi;
                EMIDate = TempEmi;
                pd1 = Pd;
                arraylist = new ArrayList();
                grandTotalPrin = 0.0;
                grandTotalInt = 0.0;
                grandInstallment = 0.0;
                openingPrin = tmpAmt;
                totalAmt = divInstalment;
                grandTotalPrin = tmpAmt;
                for (int i = 0; i < n; i++) {
                    simpleInteresttmp = openingPrin * TmpROI * timerate;
                    simpleInterest = Math.round(simpleInteresttmp / (n1 * 100));
                    totalAmt = simpleInterest + divInstalment;


                    grandTotalInt = grandTotalInt + (simpleInterest < 0 ? 0 : simpleInterest);

                    tempprin = Math.round(totalAmt - (simpleInterest < 0 ? 0 : simpleInterest));
//                    tempprin = Math.round(totalAmt - simpleInterest);
//                    pendingAmt = Math.round(tmpAmt - totalAmt + simpleInterest);
                    pendingAmt = Math.round(tmpAmt - totalAmt + (simpleInterest < 0 ? 0 : simpleInterest));

                    a[i][0] = Integer.toString(X);
                    a[i][1] = EMIdt.substring(6, 8) + "/" + EMIdt.substring(4, 6) + "/" + EMIdt.substring(0, 4);
                    a[i][2] = Double.toString(tempprin);
                    a[i][3] = Double.toString(simpleInterest);
//              a[i][4] = Double.toString(Math.round(totalAmt));
                    a[i][5] = Periodicity + ":";
                    a[i][6] = Double.toString(Math.round(openingPrin));
                    openingPrin = openingPrin - tempprin;
                    a[i][7] = Double.toString(Math.round(openingPrin));

                    TmpTotal = TmpTotal + Math.round((simpleInterest < 0 ? 0 : simpleInterest));

                    List chkList7;
                    if (Pd == 7 || (Pd == 1 && n1 == 365)) {
                        chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " DAY),'%Y%m%d')").getResultList();
                    } else {
                        chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " MONTH ),'%Y%m%d')").getResultList();
                    }
                    Vector Lst7 = (Vector) chkList7.get(0);
                    EMIdt = Lst7.get(0).toString();
                    X = X + 1;
                    pd1 = pd1 + Pd;
                }
                finalInstallment = 0.0;
                for (int i = 0; i < n; i++) {
                    finalInstallment = divInstalment + TmpTotal / n;
                    grandInstallment = grandInstallment + divInstalment;
                    a[i][4] = Double.toString(Math.round(finalInstallment));
                    for (int j = 0; j < 8; j++) {
                        Object combinedArray = a[i][j];
                        arraylist.add(combinedArray);
                    }
                }

                grandDiff = ((grandTotalPrin + grandTotalInt) - grandInstallment) / n;
                divInstalment = divInstalment + grandDiff;
                System.out.println(Math.round(grandDiff) + "=====Installment>>>: " + divInstalment + ";Grand Prin: " + grandTotalPrin + ";Int : " + grandTotalInt + "; grandInstallment:" + grandInstallment);
                divInstalment = divInstalment;
            }

            System.out.println(Math.round(grandDiff) + "Installment>>>: " + divInstalment + ";Grand Prin: " + grandTotalPrin + ";Int : " + grandTotalInt + "; grandInstallment:" + grandInstallment);
            openingPrin = tmpAmt;
            EMIdt = TempEmi;
            EMIDate = TempEmi;
            pd1 = Pd;
            arraylist = new ArrayList();
            X = 1;
            for (int i = 0; i < n; i++) {
                simpleInteresttmp = openingPrin * TmpROI * timerate;
                simpleInterest = Math.round(simpleInteresttmp / (n1 * 100));
                totalAmt = simpleInterest + divInstalment;
                tempprin = Math.round(totalAmt - (simpleInterest < 0 ? 0 : simpleInterest));
                pendingAmt = Math.round(tmpAmt - totalAmt + (simpleInterest < 0 ? 0 : simpleInterest));

                a[i][0] = Integer.toString(X);
                a[i][1] = EMIdt.substring(6, 8) + "/" + EMIdt.substring(4, 6) + "/" + EMIdt.substring(0, 4);
                if (Double.parseDouble(String.valueOf(openingPrin)) < Double.parseDouble(String.valueOf(tempprin))) {
                    a[i][2] = Double.toString(openingPrin);
                    if (openingPrin != 0.0) {
                        a[i][3] = Double.toString(tempprin - openingPrin);
                    } else {
                        a[i][3] = Double.toString(tempprin);
                    }

                } else {
                    a[i][2] = Double.toString(tempprin);
                    a[i][3] = Double.toString(0.00);
                }

//              a[i][4] = Double.toString(Math.round(totalAmt));
                a[i][5] = Periodicity + ":";
                a[i][6] = Double.toString(Math.round(openingPrin));
                openingPrin = openingPrin - tempprin < 0 ? 0 : openingPrin - tempprin;
                a[i][7] = Double.toString(Math.round(openingPrin));


                TmpTotal = TmpTotal + Math.round(simpleInterest);

                List chkList7;
                if (Pd == 7 || (Pd == 1 && n1 == 365)) {
                    chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " DAY),'%Y%m%d')").getResultList();
                } else {
                    chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " MONTH ),'%Y%m%d')").getResultList();
                }
                Vector Lst7 = (Vector) chkList7.get(0);
                EMIdt = Lst7.get(0).toString();
                X = X + 1;
                pd1 = pd1 + Pd;

            }

            finalInstallment = 0.0;
            for (int i = 0; i < n; i++) {
                finalInstallment = divInstalment;//+ TmpTotal / n;
                grandInstallment = grandInstallment + divInstalment;
                a[i][4] = Double.toString(Math.round(finalInstallment));
                for (int j = 0; j < 8; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
            return arraylist.toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String CalculateEPRP(Integer n1, Integer Pd, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, String Periodicity) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            List arraylist = new ArrayList();
            int X = 0;
            double TmpInstall;
            double TmpPrin;
            double TmpInterest;
            double TmpTotal = 0;
            String EMIDate = EMIdt;
            int pd1 = Pd;
            X = 1;
            TmpPrin = tmpAmt / n;
            double openingPrin = tmpAmt;

            String a[][] = new String[n][8];
            for (int i = 0; i < n; i++) {
                //TmpInterest = Math.round((tmpAmt * TmpROI) / (n1 * 100));
                TmpInterest = 0;
                TmpInstall = Math.round(TmpPrin + TmpInterest);
                tmpAmt = (float) (tmpAmt - TmpPrin);

                a[i][0] = Integer.toString(X);
                a[i][1] = EMIdt.substring(6, 8) + "/" + EMIdt.substring(4, 6) + "/" + EMIdt.substring(0, 4);
                a[i][2] = Double.toString(Math.round(TmpPrin));
                a[i][3] = Double.toString(TmpInterest);
                a[i][4] = Double.toString(TmpInstall);
                a[i][5] = Periodicity + ":";
                a[i][6] = Double.toString(Math.round(openingPrin));
                openingPrin = openingPrin - TmpPrin;
                a[i][7] = Double.toString(Math.round(openingPrin));

                TmpTotal = TmpTotal + Math.round(TmpInterest);
                List chkList7;
                if (Periodicity.equalsIgnoreCase("D")) {
                    chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " day ),'%Y%m%d')").getResultList();
                } else {
                    chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " MONTH ),'%Y%m%d')").getResultList();
                }

                Vector Lst7 = (Vector) chkList7.get(0);
                EMIdt = Lst7.get(0).toString();
                X = X + 1;
                pd1 = pd1 + Pd;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 8; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
            return arraylist.toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /*For Dhanbad requirement*/
    public String CalculateEMIDailyBasis(Integer n1, Integer Pd, Float tmpAmt, Integer n, Float TmpROI, String EMIdt, String Periodicity) throws ApplicationException {
        try {
            List arraylist = new ArrayList();
            int X = 0;
            double K1;
            double K2;
            double K3;
            double a1;
            double a2;
            double TmpPrin;
            double TmpInterest = 0;
            double TmpTotal = 0;
            String EMIDate = EMIdt;
            double openingPrin = tmpAmt;

            int pd1 = Pd;
//            K1 = TmpROI / (n1 * 100);
//            K2 = 1 + K1;
//            K3 = Math.pow(K2, n);
//            a1 = (K1 * K3) / (K3 - 1);
            X = 1;
//            a2 = tmpAmt * a1;
            TmpPrin = tmpAmt / n;
            //TmpInstall = tmpAmt * K1;

            String a[][] = new String[n][8];
            for (int i = 0; i < n; i++) {
                List checkList11 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + n + " day),'%Y%m%d')").getResultList();
                Vector Lst8 = (Vector) checkList11.get(0);
                String newEMIDtMP = Lst8.get(0).toString();

                List checkList22 = em.createNativeQuery("select  timestampdiff(DAY, '" + EMIDate + "', '" + newEMIDtMP + "')").getResultList();
                Vector Lst9 = (Vector) checkList22.get(0);
                int noOfDays = Integer.parseInt(Lst9.get(0).toString());
                TmpInterest = (((tmpAmt * TmpROI * n) / (n1 * 100)) / n);
//                TmpInterest = Math.round(tmpAmt * K1);
//                TmpPrin = Math.round(a2 - TmpInterest);
//                tmpAmt = (float) (tmpAmt - a2 + TmpInterest);

                a[i][0] = Integer.toString(X);
                a[i][1] = EMIdt.substring(6, 8) + "/" + EMIdt.substring(4, 6) + "/" + EMIdt.substring(0, 4);
                a[i][2] = Double.toString(TmpPrin);
                a[i][3] = Double.toString(TmpInterest);
                a[i][4] = Double.toString(TmpPrin + TmpInterest);
                a[i][5] = Periodicity + ":";
                a[i][6] = Double.toString(Math.round(openingPrin));
                openingPrin = openingPrin - TmpPrin;
                a[i][7] = Double.toString(Math.round(openingPrin));

                TmpTotal = TmpTotal + Math.round(TmpInterest);
                List chkList7;
                if (Pd == 7 || (Pd == 1 && n1 == 365)) {
                    chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " DAY),'%Y%m%d')").getResultList();
                } else {
                    chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + EMIDate + "', INTERVAL " + pd1 + " MONTH ),'%Y%m%d')").getResultList();
                }
                Vector Lst7 = (Vector) chkList7.get(0);
                EMIdt = Lst7.get(0).toString();
                X = X + 1;
                pd1 = pd1 + Pd;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 8; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
            return arraylist.toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String Calculate(String acno, Integer instaltypeFlag, Float tmpAmt, Integer n, Float tmpROI, String emiDt, Float sancAmt, String periodicity,
            String manualFlag, String interestOption) throws ApplicationException {
        try {
            int n1 = 0;
            int Pd = 0;
            String EMIDate = emiDt;
            int TmpYears = 15;
            String result = "";
            List checkList = null;
            int MP = 0;

            if ((n == 0) || (n == null)) {
                return "Please Provide NO OF INSTALLMENTS";
            }
            if (!((instaltypeFlag == 6 && manualFlag.equalsIgnoreCase("PRDEM")) || (instaltypeFlag == 4) || (instaltypeFlag == 5))) {
                if ((tmpROI <= 0)) {
                    return "ROI Can not be Zero";
                }
            }

            if (!acno.equalsIgnoreCase("")) {
                String acNature = common.getAcNatureByAcType(acno.substring(2, 4));
                if (acNature.equalsIgnoreCase("CA")) {
                    return "EMI is not allowed for this type of account.";
                }
            }
            if (instaltypeFlag != 6) {
                List checkEmi = em.createNativeQuery("select acno,status from emidetails where acno = '" + acno + "'").getResultList();
                if (!checkEmi.isEmpty()) {
                    List checkEmiPaid = em.createNativeQuery("SELECT acno,status from emidetails where acno='" + acno + "' and status='PAID'").getResultList();
                    if (checkEmiPaid.isEmpty()) {
                        //                    if ((tmpAmt != 0.0) && (tmpAmt != null)) {
                        //                        tmpAmt = tmpAmt;
                        //                    } else {
                        if ((sancAmt != 0.0) && (sancAmt != null)) {
                            if (fts.getCodeForReportName("EMI-UNPAID") == 1) { /*22481	Fri Aug 03 12:05:22 IST 2018 "Reshudel emi on os balance in case of all emi unpaid (Khattri as per alok sir)"	*/
                                tmpAmt = tmpAmt;
                            } else {
                                tmpAmt = sancAmt;
                            }
                        } else {
                            return "Please Provide valid Sanction Amount";
                        }
                        //                    }
                    } else {
                        if ((tmpAmt != 0.0) && (tmpAmt != null)) {
                            tmpAmt = tmpAmt;
                        } else if ((tmpAmt == 0.0) || (tmpAmt.isNaN() == true) || (tmpAmt == null)) {
                            return "Please Provide valid OutStanding Amount";
                        }
                    }
                } else {
                    if ((sancAmt == 0.0) || (sancAmt.isNaN() == true) || (sancAmt == null)) {
                        //                    if ((tmpAmt != 0.0) && (tmpAmt != null)) {
                        //                        tmpAmt = tmpAmt;
                        //                    } else {
                        return "Please Provide valid Sanction Amount";
                        //                    }
                    } else {
                        //                    if ((tmpAmt != 0.0) && (tmpAmt != null)) {
                        //                    } else {
                        tmpAmt = sancAmt;
                        //                    }
                    }
                }
            }
            if (periodicity.equalsIgnoreCase("D")) {
                n1 = 365;
                Pd = 1;
            } else if (periodicity.equalsIgnoreCase("M")) {
                n1 = 12;
                Pd = 1;
            } else if (periodicity.equalsIgnoreCase("Q")) {
                n1 = 4;
                Pd = 3;
            } else if (periodicity.equalsIgnoreCase("HY")) {
                n1 = 2;
                Pd = 6;
            } else if (periodicity.equalsIgnoreCase("Y")) {
                n1 = 1;
                Pd = 12;
            } else if (periodicity.equalsIgnoreCase("W")) {
                n1 = 52;
                Pd = 7;
            }
            if (Pd == 0) {
                return "Periodicity Should not be zero";
            }
            if (instaltypeFlag == 1) {
                if (interestOption.equalsIgnoreCase("C")) {
                    result = CalculateEMI(n1, Pd, tmpAmt, n, tmpROI, emiDt, periodicity);
                } else if (interestOption.equalsIgnoreCase("S")) {
                    result = CalculateEmiAtSimpleInterest(n1, Pd, tmpAmt, n, tmpROI, emiDt, periodicity);
                }

            } else if (instaltypeFlag == 2) {
                if (interestOption.equalsIgnoreCase("C")) {
                    result = CalculateEMI(n1, Pd, tmpAmt, n, tmpROI, emiDt, periodicity);
                } else if (interestOption.equalsIgnoreCase("S")) {
                    result = CalculateEmiAtSimpleInterest(n1, Pd, tmpAmt, n, tmpROI, emiDt, periodicity);
                }

            } else if (instaltypeFlag == 3) {
                checkList = em.createNativeQuery("SELECT MORATORIUM_PD FROM cbs_loan_acc_mast_sec where acno = '" + acno + "'").getResultList();
                Vector Lst7 = (Vector) checkList.get(0);
                String MP1 = Lst7.get(0).toString();
                MP = Integer.parseInt(MP1);

                List checkList11 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + emiDt + "', INTERVAL " + MP + " MONTH),'%Y%m%d')").getResultList();
                Vector Lst8 = (Vector) checkList11.get(0);
                String newEMIDtMP = Lst8.get(0).toString();

                List checkList22 = em.createNativeQuery("select  timestampdiff(DAY, '" + emiDt + "', '" + newEMIDtMP + "')").getResultList();
                Vector Lst9 = (Vector) checkList22.get(0);
                int noOfDays = Integer.parseInt(Lst9.get(0).toString());
                Float MPI = 0.0f;
                MPI = (tmpAmt * tmpROI * noOfDays) / 36500;
                tmpAmt = tmpAmt + MPI;
                if (interestOption.equalsIgnoreCase("C")) {
                    result = CalculateEMI(n1, Pd, tmpAmt, n, tmpROI, newEMIDtMP, periodicity);
                } else if (interestOption.equalsIgnoreCase("S")) {
                    result = CalculateEmiAtSimpleInterest(n1, Pd, tmpAmt, n, tmpROI, newEMIDtMP, periodicity);
                }
            } else if (instaltypeFlag == 4) {
                result = CalculateEPRP(n1, Pd, tmpAmt, n, tmpROI, emiDt, periodicity);
            } else if (instaltypeFlag == 5) {
                checkList = em.createNativeQuery("select moratorium_pd from cbs_loan_acc_mast_sec where acno = '" + acno + "'").getResultList();
                Vector Lst7 = (Vector) checkList.get(0);
                String MP1 = Lst7.get(0).toString();
                MP = Integer.parseInt(MP1);
                n = n - MP;
                result = CalculateEPRP(n1, Pd, tmpAmt, n, tmpROI, emiDt, periodicity);
            } else if (instaltypeFlag == 6) {
                if (manualFlag.equalsIgnoreCase("INDEM")) {
                    try {
                        List arraylist = new ArrayList();
                        int X = 0;
                        int pd1 = Pd;
                        double SI;
                        X = 1;
                        if (periodicity.equalsIgnoreCase("D") || periodicity.equalsIgnoreCase("W")) {
                            List checkList11 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + emiDt + "', INTERVAL " + (periodicity.equalsIgnoreCase("D") ? n : n * Pd) + " day),'%Y%m%d')").getResultList();
                            Vector Lst8 = (Vector) checkList11.get(0);
                            String newEMIDtMP = Lst8.get(0).toString();

                            List checkList22 = em.createNativeQuery("select  timestampdiff(DAY, '" + emiDt + "', '" + newEMIDtMP + "')").getResultList();
                            Vector Lst9 = (Vector) checkList22.get(0);
                            int noOfDays = Integer.parseInt(Lst9.get(0).toString());
                            if (periodicity.equalsIgnoreCase("D")) {
                                SI = Math.round((((tmpAmt.doubleValue() == 0 ? sancAmt : tmpAmt) * tmpROI * n) / (n1 * 100)) / n);
                            } else {
                                SI = Math.round((((tmpAmt.doubleValue() == 0 ? sancAmt : tmpAmt) * tmpROI * 1) / (365 * 100)) * Pd);
                            }
                        } else {
                            List checkList11 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + emiDt + "', INTERVAL " + n + " MONTH),'%Y%m%d')").getResultList();
                            Vector Lst8 = (Vector) checkList11.get(0);
                            String newEMIDtMP = Lst8.get(0).toString();

                            List checkList22 = em.createNativeQuery("select  timestampdiff(DAY, '" + emiDt + "', '" + newEMIDtMP + "')").getResultList();
                            Vector Lst9 = (Vector) checkList22.get(0);
                            int noOfDays = Integer.parseInt(Lst9.get(0).toString());
                            SI = Math.round((((tmpAmt.doubleValue() == 0 ? sancAmt : tmpAmt) * tmpROI * n) / (n1 * 100)) / n);
                        }
                        String a[][] = new String[n][8];
                        for (int i = 0; i < n; i++) {
                            a[i][0] = Integer.toString(X);

                            a[i][2] = Double.toString(0);
                            a[i][3] = Double.toString(SI);
                            a[i][4] = Double.toString(SI);
                            a[i][5] = periodicity + ":";
                            a[i][6] = Double.toString(0);
                            a[i][7] = Double.toString(0);

                            List chkList7;
                            if (periodicity.equalsIgnoreCase("D") || periodicity.equalsIgnoreCase("W")) {
                                chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + emiDt + "', INTERVAL " + Pd + " day),'%Y%m%d')").getResultList();
                            } else {
                                chkList7 = em.createNativeQuery("SELECT Date_format(DATE_ADD('" + emiDt + "', INTERVAL " + Pd + " MONTH),'%Y%m%d')").getResultList();
                            }

                            Vector Lst7 = (Vector) chkList7.get(0);
                            emiDt = Lst7.get(0).toString();
                            a[i][1] = emiDt.substring(6, 8) + "/" + emiDt.substring(4, 6) + "/" + emiDt.substring(0, 4);
                            X = X + 1;
                            pd1 = pd1 + Pd;
                        }
                        for (int i = 0; i < n; i++) {
                            for (int j = 0; j < 8; j++) {
                                Object combinedArray = a[i][j];
                                arraylist.add(combinedArray);
                            }
                        }
                        result = arraylist.toString();
                    } catch (Exception e) {
                        throw new ApplicationException(e);
                    }
                }
                if (manualFlag.equalsIgnoreCase("PRDEM")) {
                    tmpAmt = (tmpAmt == 0) ? sancAmt : tmpAmt;
                    result = CalculateEPRP(n1, Pd, periodicity.equalsIgnoreCase("D") ? sancAmt : tmpAmt, n, tmpROI, emiDt, periodicity);
                }
                if (manualFlag.equalsIgnoreCase("EIDEM")) {
                    if (periodicity.equalsIgnoreCase("D")) {
                        result = CalculateEMIDailyBasis(n1, Pd, periodicity.equalsIgnoreCase("D") ? sancAmt : tmpAmt, n, tmpROI, emiDt, periodicity);
                    } else {
                        tmpAmt = (tmpAmt == 0) ? sancAmt : tmpAmt;
                        result = CalculateEMI(n1, Pd, tmpAmt, n, tmpROI, emiDt, periodicity);
                    }
                }
                if (manualFlag.equalsIgnoreCase("SPI") && periodicity.equalsIgnoreCase("M")) {
                    tmpAmt = (tmpAmt == 0) ? sancAmt : tmpAmt;;
                    result = CalculateEmiAtSimpleInterest(n1, Pd, tmpAmt, n, tmpROI, emiDt, periodicity);
                }
            }
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String UpdateReShedule(String acno, List AmorList, String Flag, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = "";
            int exec = 0;
            int exec1 = 0;
            int tmpSno = 0;
            int var = 0;
            int fl = 0;
            String accSeq = common.getAccseq();
            if ((AmorList.isEmpty())) {
                ut.rollback();
                return "No Installment is Calculated to Save";
            }
            String acNature = common.getAcNatureByAcType(acno.substring(2, 4));
            if (acNature.equalsIgnoreCase("CA")) {
                return "EMI creation/reschedule is not allowed for this type of account.";
            }
            if (Flag.equalsIgnoreCase("R")) {
                List osBalanceLst = getOsBalance(acno);
                Vector osBalanceVector = (Vector) osBalanceLst.get(0);
                // setOsBalance(osBalanceVector.get(0).toString());
                // setOsBalance(String.valueOf(CbsUtil.round(Double.parseDouble(osBalanceVector.get(0).toString()), 2)));
                double outStand = CbsUtil.round(Double.parseDouble(osBalanceVector.get(0).toString()), 2);
                Query InsertQuery = em.createNativeQuery("insert into emirescheduled(acno,totInstallment,startduedt,endduedt,TotInstallamt,totPrinAmt,TotIntAmt,Periodicity,EnterBy, PlanAbortDt) select  acno,count(sno) ,min(duedt) ,max(duedt) ," + outStand + " ,sum(prinamt) ,sum(interestamt) ,min(periodicity) ,'" + user + "', now() from emidetails where acno='" + acno + "' and status='unpaid' group by acno");
                exec1 = InsertQuery.executeUpdate();

            }

            List checkList0 = em.createNativeQuery("select * from emidetails where acno = '" + acno + "'").getResultList();
            if (checkList0.isEmpty()) {
                fl = 1;
            } else {
                checkList0 = em.createNativeQuery("select * from emidetails where acno = '" + acno + "'  and status = 'Unpaid'").getResultList();
                if (!checkList0.isEmpty()) {
                    Query DelQuery = em.createNativeQuery("delete from emidetails where acno = '" + acno + "' and status = 'Unpaid'");
                    exec = DelQuery.executeUpdate();
                    fl = 1;
                } else {
                    fl = 1;
                    exec1 = 1;
                    exec = 1;
                }
            }

            List checkList = em.createNativeQuery("select coalesce(max(sno),0)+1 from emidetails where acno = '" + acno + "'").getResultList();
            Vector Lst7 = (Vector) checkList.get(0);
            tmpSno = Integer.parseInt(Lst7.get(0).toString());

            for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7; a < AmorList.size(); a = a + 8, b = b + 8, c = c + 8, d = d + 8, e = e + 8, f = f + 8, g = g + 8, h = h + 8) {
                Query insertQuery = em.createNativeQuery("insert into emidetails (Acno,sno,DUEDT,prinamt,interestamt,installamt,periodicity,Status,RESCHEDULMENTDT)"
                        + "values ('" + acno + "','" + tmpSno + "', date_format(STR_TO_DATE('" + AmorList.get(b) + "', '%d/%m/%Y'),'%Y%m%d')," + AmorList.get(c) + "," + AmorList.get(d) + "," + AmorList.get(e) + "," + "'" + AmorList.get(f) + "'" + "," + "'Unpaid'" + ", now())");
                var = insertQuery.executeUpdate();

                tmpSno = tmpSno + 1;
            }
            if (accSeq.equalsIgnoreCase("M")) {
                int gVal = 0;
                List gauList = em.createNativeQuery("select * from loan_guarantordetails where acno = '" + acno + "'").getResultList();
                if (!gauList.isEmpty()) {
                    Query insertQuery = em.createNativeQuery("INSERT INTO loan_guarantordetails_his (ACNO, NAME, ADDRESS, PHNO, OCCUPATION, FIRMNAME, FIRMADDRESS, FIRMPHNO, DESIGNATION, FATHERSNAME, age, RETIREMENTAGE, OFFICEADDRESS, NETWORTH, AUTHBY, ENTERBY, TRANTIME, CUST_FLAG, GAR_ACNO, GAR_CUSTID) \n"
                            + "SELECT ACNO, NAME, ADDRESS, PHNO, OCCUPATION, FIRMNAME, FIRMADDRESS, FIRMPHNO, DESIGNATION, FATHERSNAME, age, RETIREMENTAGE, OFFICEADDRESS, NETWORTH, AUTHBY, ENTERBY, TRANTIME, CUST_FLAG, GAR_ACNO, GAR_CUSTID \n"
                            + "FROM loan_guarantordetails where acno = '" + acno + "'");
                    gVal = insertQuery.executeUpdate();
                    if (gVal <= 0) {
                        ut.rollback();
                        return "Insertion Problem";
                    }
                    Query delQuery = em.createNativeQuery("delete from loan_guarantordetails where acno = '" + acno + "'");
                    gVal = delQuery.executeUpdate();
                    if (gVal <= 0) {
                        ut.rollback();
                        return "Deletion Problem";
                    }
                }
            }
            if (Flag.equalsIgnoreCase("U")) {
                if ((exec > 0) && (var > 0) || ((var > 0) && (fl > 0))) {
                    ut.commit();
                    return "Installment Plan is sheduled";
                } else {
                    ut.rollback();
                    return "Error Installment Plan is not Updated / sheduled";
                }
            } else if (Flag.equalsIgnoreCase("R")) {
                if ((exec1 > 0) && (exec > 0) && (var > 0) && (fl > 0)) {
                    ut.commit();
                    return "Installment Plan is Resheduled";
                } else {
                    ut.rollback();
                    return "Error Installment Plan is not Rsheduled";
                }
            } else {
                ut.rollback();
                return "NO FLAG";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List getIntCodeIntTypeSchmCode(String accNo) throws ApplicationException {

        try {
            List list = em.createNativeQuery("select a.Scheme_code, a.interest_table_code, a.rate_code, a.LOAN_PD_MONTH, a.LOAN_PD_DAY, b.ODLimit from cbs_loan_acc_mast_sec a , accountmaster b where a.acno = '" + accNo + "' and a.acno = b.acno ").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    /**
     * *********END OF SHIPRA'S EJB CODE************************************
     */
    public List getManagementDtl(String status) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT name,Gender,desg,edu_details,addrLine1,addrLine2,village,block,CityCode,StateCode,PostalCode,"
                    + " CountryCode,PhoneNumber,EmailID,ifnull(JoinDt,'1900-01-01 00:00:00'),ifnull(expDt,'2099-03-31 00:00:00'),ifnull(status,'A'),enterby,sno,title,rem_desg,ifnull(custid,'')"
                    + ",ifnull(dirRelation,''),ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no ='004' and ref_code = dirRelation),''),"
                    + "ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no ='214' and ref_code = edu_details),'')"
                    + ",ifnull((select ref_desc from cbs_ref_rec_type where ref_rec_no ='210' and ref_code = desg),'') from banks_dir_details WHERE status='" + status + "' or status is null ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String SaveManagementDetail(String title, String name, String desg, String Gender, String edu_details, String addrLine1, String addrLine2,
            String village, String block, String CityCode, String StateCode, String PostalCode, String CountryCode, String PhoneNumber,
            String EmailID, String JoinDt, String expDt, String status, String enterby, String dsgRem, String custId, String dirRelation, String directorId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        Date date = new Date();
        try {
            ut.begin();

            List sNoList = em.createNativeQuery("SELECT IFNULL(MAX(SNO)+1,1) FROM banks_dir_details").getResultList();
            Vector sNoVect = (Vector) sNoList.get(0);
            int sNo = Integer.parseInt(sNoVect.get(0).toString());

            Query q1 = em.createNativeQuery("insert into banks_dir_details (name,Gender,desg,edu_details,addrLine1,addrLine2,village,"
                    + "block,CityCode,StateCode,PostalCode,CountryCode,PhoneNumber,EmailID,JoinDt,expDt,status,enterby,trantime,sno,title,rem_desg,custId,dirRelation,directorId) "
                    + "values('" + name + "','" + Gender + "','" + desg + "','" + edu_details + "','" + addrLine1 + "','" + addrLine2 + "','" + village + "','" + block
                    + "','" + CityCode + "','" + StateCode + "','" + PostalCode + "','" + CountryCode + "','" + PhoneNumber + "','" + EmailID + "','" + ymd.format(d_my.parse(JoinDt)) + "','" + ymd.format(d_my.parse(expDt))
                    + "','" + status + "','" + enterby + "',now()," + sNo + ",'" + title + "','" + dsgRem + "','" + custId + "','" + dirRelation + "','" + directorId + "')");
            int insertResult = q1.executeUpdate();
            if (insertResult <= 0) {
                throw new ApplicationException("Insertion problem in RD_Installment");
            }
            result = "true";
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return result;
    }

    public String UpdateManagementDetail(String title, String name, String desg, String Gender, String edu_details, String addrLine1, String addrLine2, String village, String block, String CityCode, String StateCode, String PostalCode, String CountryCode, String PhoneNumber, String EmailID, String JoinDt, String expDt, String status, String enterby, int sno, String dsgRem, String custId, String dirRelation, String directorId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        Date date = new Date();
        try {
            ut.begin();

            Query q2 = em.createNativeQuery("insert into banks_dir_details_his (title, name,Gender,desg,rem_desg,edu_details,addrLine1,addrLine2,village,"
                    + "block,CityCode,StateCode,PostalCode,CountryCode,PhoneNumber,EmailID,JoinDt,expDt,status,enterby,trantime,sno,modified_by,modified_dt,custId,dirRelation,directorId)"
                    + "select title,name,Gender,desg,rem_desg,edu_details,addrLine1,addrLine2,village,"
                    + " block,CityCode,StateCode,PostalCode,CountryCode,PhoneNumber,EmailID,JoinDt,expDt,status,enterby,trantime,sno,'" + enterby + "',now(),custId,dirRelation,directorId "
                    + " from banks_dir_details where sno ='" + sno + "'");
            int insertResult = q2.executeUpdate();
            if (insertResult <= 0) {
                throw new ApplicationException("Insertion problem in RD_Installment");
            }

            Query updateDtl = em.createNativeQuery("update banks_dir_details set rem_desg='" + dsgRem + "', title='" + title + "', name='" + name + "', Gender='" + Gender
                    + "',desg='" + desg + "',edu_details='" + edu_details + "',addrLine1='" + addrLine1 + "',addrLine2='" + addrLine2
                    + "',village='" + village + "',block='" + block + "',CityCode='" + CityCode + "',StateCode='" + StateCode + "', PostalCode='"
                    + PostalCode + "', CountryCode='" + CountryCode + "',PhoneNumber='" + PhoneNumber
                    + "',EmailID='" + EmailID + "',JoinDt='" + ymd.format(d_my.parse(JoinDt)) + "',expDt='" + ymd.format(d_my.parse(expDt)) + "',status='" + status
                    + "',enterby='" + enterby + "',trantime=now(),custId= '" + custId + "',dirRelation='" + dirRelation + "',directorId ='" + directorId + "' where sno='" + sno + "'");
            int updateResult = updateDtl.executeUpdate();
            if (updateResult <= 0) {
                throw new ApplicationException("Updation Problem into Td Account Master.");
            }

            result = "true";
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return result;
    }

    public List relNameDetail(String relCode) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT name from banks_dir_details WHERE desg='" + relCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getGuarantorIs(String schemeCode) throws ApplicationException {
        try {
            List guarantList = em.createNativeQuery("select ledger_folio_calc_event from cbs_scheme_currency_details where SCHEME_CODE = '" + schemeCode + "'").getResultList();
            if (guarantList.isEmpty()) {
                throw new ApplicationException("Guarantor code does not exits in table cbs_scheme_currency_details !");
            } else {
                Vector vtr = (Vector) guarantList.get(0);
                return vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getGuarantorDob(String custId) throws ApplicationException {
        try {
            List dobList = em.createNativeQuery("select date_format(dateofbirth,'%Y%m%d') from cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
            if (dobList.isEmpty()) {
                throw new ApplicationException("Guarantor date of birth does not exits in table cbs_customer_master_detail !");
            } else {
                Vector vtr = (Vector) dobList.get(0);
                return vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getGuarantorSalary(String custId) throws ApplicationException {
        try {
            List dobList = em.createNativeQuery("select ifnull(Salary,0) + ifnull(gradepay,0) from share_holder where custId = '" + custId + "'").getResultList();
            if (dobList.isEmpty()) {
                throw new ApplicationException("This Guarantor Is not Of The Member !");
            } else {
                Vector vtr = (Vector) dobList.get(0);
                return vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getCustIdAsPerFolioNo(String folioNo) throws ApplicationException {
        try {
            List dobList = em.createNativeQuery("select custid from share_holder where Regfoliono = '" + folioNo + "'").getResultList();
            if (dobList.isEmpty()) {
                throw new ApplicationException("This Guarantor is not eligible to take the guarantee!");
            } else {
                Vector vtr = (Vector) dobList.get(0);
                return vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getGuarantorLimit(String schemeCode) throws ApplicationException {
        try {
            List dobList = em.createNativeQuery("select ADHOC_INT_CERTIFICATE_PRINTING_EVENT from cbs_scheme_currency_details where SCHEME_CODE = '" + schemeCode + "'").getResultList();
            if (dobList.isEmpty()) {
                throw new ApplicationException("Guarantor limit No. does not exits in table cbs_scheme_currency_details !");
            } else {
                Vector vtr = (Vector) dobList.get(0);
                return vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getGuarantorNetworth(String custId) throws ApplicationException {
        try {
            List netWorthList = em.createNativeQuery("select ifnull(sum(NETWORTH),0) from loan_guarantordetails ag, accountmaster a where ag.gar_custid = '" + custId + "' and ag.acno = a.acno and a.accstatus <>9 ").getResultList();
            if (netWorthList.isEmpty()) {
                throw new ApplicationException("Guarantor NETWORTH does not exits in table loan_guarantordetails !");
            } else {
                Vector vtr = (Vector) netWorthList.get(0);
                return vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getLoanPerid(String acNo) throws ApplicationException {
        try {
            List dobList = em.createNativeQuery("select ifnull(LOAN_PD_MONTH,0) from cbs_loan_acc_mast_sec where acno = '" + acNo + "'").getResultList();
            if (dobList.isEmpty()) {
                throw new ApplicationException("LOAN_PD_MONTH does not exits in table cbs_loan_acc_mast_sec !");
            } else {
                Vector vtr = (Vector) dobList.get(0);
                return vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getGuarantorNo(String schemeCode) throws ApplicationException {
        try {
            List guarNoList = em.createNativeQuery("select ADHOC_PASS_SHEET_PRINT_EVENT from cbs_scheme_currency_details where SCHEME_CODE = '" + schemeCode + "'").getResultList();
            if (guarNoList.isEmpty()) {
                throw new ApplicationException("ADHOC_PASS_SHEET_PRINT_EVENT does not exits in table cbs_scheme_currency_details !");
            } else {
                Vector vtr = (Vector) guarNoList.get(0);
                return vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getMinGuarantorNo(String acNo) throws ApplicationException {
        try {
            List guarNoList = em.createNativeQuery("select count(*)from loan_guarantordetails where ACNO = '" + acNo + "'").getResultList();
            Vector vtr = (Vector) guarNoList.get(0);
            return vtr.get(0).toString();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String guarNetWorthChk(String guarFlag, String guarAc, String schemeCode, String acno) throws ApplicationException {
        double guarantorSalary = 0, salary = 0, guarantorNetworth = 0, guarantorAmt = 0;
        try {
            String custId = "";
            double guarantorLimit = Double.parseDouble(getGuarantorLimit(schemeCode));
            if (guarFlag.equalsIgnoreCase("YA")) {
                List custIdList = em.createNativeQuery("select CustId from customerid where acno = '" + acno + "'").getResultList();
                Vector vtr = (Vector) custIdList.get(0);
                custId = vtr.get(0).toString();
            } else {
                custId = acno;
            }
            guarantorSalary = Double.parseDouble(getGuarantorSalary(custId));
            salary = guarantorSalary * guarantorLimit;
            guarantorNetworth = Double.parseDouble(getGuarantorNetworth(custId));
            guarantorAmt = salary - guarantorNetworth;
            double installmentPaidAmt = getGuarantorPaticipateAmt(custId);

            return String.valueOf(guarantorAmt + installmentPaidAmt);

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getGuarCustId(String acNo) throws ApplicationException {
        try {
            List chk2 = em.createNativeQuery("SELECT CUSTID FROM customerid WHERE ACNO='" + acNo + "'").getResultList();
            Vector chk2Vec = (Vector) chk2.get(0);
            return chk2Vec.get(0).toString();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getGuarChk(String guarCustId) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from loan_guarantordetails where gar_custid = '" + guarCustId + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getAadhaarDetail(String custId) throws ApplicationException {
        try {
            List detail = em.createNativeQuery("select CUST_ID,STATUS,MAPPING_STATUS from cbs_aadhar_registration where CUST_ID = '" + custId + "'").getResultList();
            return detail;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getGuarantorPaticipateAmt(String custId) throws ApplicationException {
        double amt = 0d;
        try {
            List acnoList = em.createNativeQuery("select distinct acno from loan_guarantordetails where gar_custid = '" + custId + "'").getResultList();
            double totalInstAmt = 0d;
            if (!acnoList.isEmpty()) {
                int acnoNo = acnoList.size();
                for (int j = 0; j < acnoList.size(); j++) {
                    Vector acnoVector = (Vector) acnoList.get(j);
                    String acno = acnoVector.get(0).toString();
//                    List amtList = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno = '" + acno + "' and dt > "
//                            + "ifnull(ifnull((select date_format(max(trantime),'%Y%m%d') from loan_guarantordetails where gar_custid = '" + custId + "' and acno = '" + acno + "'),"
//                            + "(select date_format(max(dt),'%Y%m%d') from loan_recon where acno = '" + acno + "' and trandesc = 6 and dt <= '" + ymd.format(new Date()) + "')),"
//                            + "(select OpeningDt from accountmaster where acno = '" + acno + "' and AccStatus <> 9))").getResultList();
                    List amtList = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno = '" + acno + "' and dt > "
                            + "ifnull((select date_format(max(DISBURSEMENTDT),'%Y%m%d') from loandisbursement where acno = '" + acno + "'),"
                            + "(select date_format(max(dt),'%Y%m%d') from loan_recon where acno = '" + acno + "' and trandesc = 6 and dt <= '" + ymd.format(new Date()) + "'))").getResultList();

                    Vector amtVector = (Vector) amtList.get(0);
                    double installmentAmt = Double.parseDouble(amtVector.get(0).toString());
                    totalInstAmt = totalInstAmt + installmentAmt;
                }
                amt = totalInstAmt / acnoNo;
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return amt;
    }

    @Override
    public String SaveMoneyExchg(String tendName, String idProof, String idNo, double totVal, String place, String brCode, String enterBy,
            List<DDSDenominationGrid> cashDenoList, String authFlag) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            ut.begin();
            float recno = fts.getRecNo();
            Query insertIntoMonExchDtls = em.createNativeQuery("insert into money_exchange_details(tenderer_name,idproof,idno,den500,den1000,total,place,"
                    + "brcode,enterby,auth,entrydate,recno,denomination,noOfNotes) values('" + tendName + "','" + idProof + "','" + idNo + "',0,0," + totVal
                    + ",'" + place + "','" + brCode + "','" + enterBy + "','" + authFlag + "','" + df.format(date) + "'," + recno + " ,0,0)");
            int resultIntoMonExchTable = insertIntoMonExchDtls.executeUpdate();
            if (resultIntoMonExchTable <= 0) {
                throw new ApplicationException("Error in posting data in money_exchange_details table..");
            }
            if (fts.xchangeDenoModule(brCode)) {
                String msg = "";
                for (DDSDenominationGrid ddg : cashDenoList) {
                    msg = ibtFacade.insertDenominationDetail("99", recno, df.format(date), new BigDecimal(ddg.getDenoValue()), ddg.getDenoNo(),
                            Integer.parseInt(ddg.getTy()), brCode, enterBy, "N");
                    if (!msg.equals("true")) {
                        throw new ApplicationException(msg);
                    }
                    int cnVal = 0;
                    if (ddg.getTy().equalsIgnoreCase("0")) {
                        cnVal = ddg.getDenoNo();
                    } else if (ddg.getTy().equalsIgnoreCase("1")) {
                        cnVal = ddg.getDenoNo() * -1;
                    }
                    msg = ibtFacade.updateOpeningDenomination(brCode, new BigDecimal(ddg.getDenoValue()), cnVal, df.format(date), "N");
                    if (!msg.equals("true")) {
                        throw new ApplicationException(msg);
                    }
                }
            }
            ut.commit();
            return "Data has been posted successfully...";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List gridLoadForMoneyExchg(String brCode, String authFlag) throws ApplicationException {
        try {
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return em.createNativeQuery("select tenderer_name,idproof,idno,denomination,noOfNotes,total,place,"
                    + "enterby,SPNO,recno,entrydate from money_exchange_details where auth = '" + authFlag + "' and brcode = '" + brCode + "' "
                    + "and entrydate='" + df.format(date) + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String ModifyMoneyExchg(String tendName, String idProof, String idNo, double totVal, String place, String brCode, String authBy, int spNo,
            float recno, List<DDSDenominationGrid> cashDenoList) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            for (DDSDenominationGrid ddg : cashDenoList) {
                Query q1 = em.createNativeQuery("UPDATE money_exchange_details SET tenderer_name ='" + tendName + "', idproof='" + idProof + "', idno ='"
                        + idNo + "', den500 = 0, den1000 = 0,total =" + totVal + ", place='" + place + "', DENOMINATION ='0', noOfNotes ='0' "
                        + "WHERE SPNO=" + spNo + "");
                int resultIntoMonExchTable = q1.executeUpdate();
                if (resultIntoMonExchTable <= 0) {
                    throw new ApplicationException("Error in Updating data in money_exchange_details table..");
                }
            }
            if (fts.xchangeDenoModule(brCode)) {
                String msg = ibtFacade.deleteDenomination(recno, df.format(date));
                if (!msg.equals("true")) {
                    throw new ApplicationException(msg);
                }
                for (DDSDenominationGrid ddg : cashDenoList) {
                    msg = ibtFacade.insertDenominationDetail("99", recno, df.format(date), new BigDecimal(ddg.getDenoValue()), ddg.getDenoNo(), Integer.parseInt(ddg.getTy()), brCode, authBy, "N");
                    if (!msg.equals("true")) {
                        throw new ApplicationException(msg);
                    }
                    int cnVal = 0;

                    if (ddg.getTy().equalsIgnoreCase("0")) {
                        cnVal = ddg.getDenoNo();
                    } else if (ddg.getTy().equalsIgnoreCase("1")) {
                        cnVal = ddg.getDenoNo() * -1;
                    }
                    msg = ibtFacade.updateOpeningDenomination(brCode, new BigDecimal(ddg.getDenoValue()), cnVal, df.format(date), "N");
                    if (!msg.equals("true")) {
                        throw new ApplicationException(msg);
                    }
                }
            }
            ut.commit();
            return "Data has been Updated successfully...";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }

    }

    @Override
    public List<DDSDenominationGrid> getDenominationDetails(float recNo, String dt) throws ApplicationException {
        try {
            List<DDSDenominationGrid> denoGrid = new LinkedList<>();
            List list = em.createNativeQuery("select brncode,denomination,denomination_value,"
                    + "date_format(dt,'%Y%m%d'),new_old_flag,ty from denomination_detail where "
                    + "recno=" + recNo + " and dt='" + dt + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Denomination details does not exist");
            }
            DDSDenominationGrid denoObj;
            for (int i = 0; i < list.size(); i++) {
                Vector denoVec = (Vector) list.get(i);

                denoObj = new DDSDenominationGrid();
                double denomination = Double.parseDouble(denoVec.get(1).toString());
                int noOfNotes = Integer.parseInt(denoVec.get(2).toString());

                denoObj.setDenoNo(noOfNotes);
                denoObj.setDenoValue(denomination);
                denoObj.setDenoAmount(denomination * noOfNotes);

                int ty = Integer.parseInt(denoVec.get(5).toString());
                if (ty == 0) {
                    denoObj.setTy("0");
                    denoObj.setTySh("Received");
                } else {
                    denoObj.setTy("1");
                    denoObj.setTySh("Paid");
                }
                denoGrid.add(denoObj);
            }
            return denoGrid;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String verifyMoneyExchg(String authBy, int spNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insertIntoMonExchDtls = em.createNativeQuery("UPDATE money_exchange_details SET auth ='Y',authBy ='" + authBy + "' "
                    + " WHERE SPNO=" + spNo + "");
            int resultIntoMonExchTable = insertIntoMonExchDtls.executeUpdate();
            if (resultIntoMonExchTable <= 0) {
                throw new ApplicationException("Error in posting data in money_exchange_details table..");
            }
            ut.commit();
            return "Data has been Authorized successfully...";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    @Override
    public List getCustomerAadhaarDetail(String customerId) throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(reg_type,''),ifnull(aadhaar_no,'') from "
                    + "cbs_customer_master_detail where customerid='" + customerId + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List checkGroupId(String custId) throws ApplicationException {
        List list = new ArrayList();
        try {
            List lis = em.createNativeQuery("select CUST_ID from cbs_loan_borrower_details where groupID in (1,2) and CUST_ID = '" + custId + "'").getResultList();
            if (!lis.isEmpty()) {
                for (int i = 0; i < lis.size(); i++) {
                    Vector vec = (Vector) lis.get(i);
                    list.add(vec.get(0).toString());
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    @Override
    public List isEmirescheduled(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno from emirescheduled where acno = '" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getOpeningBalByRescheduleDate(String acno) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select date_format(StartDueDt,'%d/%m/%Y') as dueDt,TotInstallAmt as outbal from emirescheduled where acno = '" + acno + "' "
                    + "and date_format(PlanAbortDt,'%Y%m%d') = (select date_format(max(PlanAbortDt),'%Y%m%d') from emirescheduled where acno = '" + acno + "' and PlanAbortDt is not null) ").getResultList();
            if (list.isEmpty()) {
                list = em.createNativeQuery("select dueDt,outbal from(\n"
                        + "select date_format(max(StartDueDt),'%d/%m/%Y') as dueDt from emirescheduled where acno = '" + acno + "' and PlanAbortDt is null)a, "
                        + "(select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)) as outbal from loan_recon where acno = '" + acno + "' "
                        + "and dt<(select max(StartDueDt) from emirescheduled where acno = '" + acno + "' and PlanAbortDt is null))b").getResultList();
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
