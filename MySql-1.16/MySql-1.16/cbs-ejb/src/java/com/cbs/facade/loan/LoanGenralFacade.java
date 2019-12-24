/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.loan;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.loan.RepaymentShedulePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.utils.CbsUtil;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.spi.DirStateFactory.Result;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author admin
 */
@Stateless(mappedName = "LoanGenralFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LoanGenralFacade implements LoanGenralFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    @EJB
    FtsPostingMgmtFacadeRemote facadeRemote;
    @EJB
    private LoanInterestCalculationFacadeRemote loanIntCalcRemote;

    /**
     * DemandMarkingBean start
     *
     */
    public List getCustname(String accNo) throws ApplicationException {
        try {
            List selectCustname = em.createNativeQuery("select custname from accountmaster where  acno='" + accNo + "'").getResultList();
            return selectCustname;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String saveGridData(List arraylist) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String entryDate = sdf.format(date);
        try {
            ut.begin();
            if (arraylist.isEmpty()) {
                ut.rollback();
                return result = "No Data was found to save.";
            } else {
                for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5; a < arraylist.size(); a = a + 6, b = b + 6, c = c + 6, d = d + 6, e = e + 6, f = f + 6) {
                    String demandDate = arraylist.get(c).toString().substring(6) + arraylist.get(c).toString().substring(3, 5) + arraylist.get(c).toString().substring(0, 2);
                    Query insertIntoEmiDetails = em.createNativeQuery("Insert into emidetails_sub (Acno,DemandDt,PRN,INST,CHG,EnteredBy,Dt,Auth) Values("
                            + "'" + arraylist.get(a).toString() + "','" + demandDate + "','" + arraylist.get(d).toString() + "','" + arraylist.get(e).toString() + "','" + arraylist.get(f).toString() + "',' ','" + entryDate + "','N')");
                    int resultInsertEmidetails = insertIntoEmiDetails.executeUpdate();
                    if (resultInsertEmidetails <= 0) {
                        ut.rollback();
                        return result = "Error in data posting.Try again.";
                    }
                }
                ut.commit();
                return result = "Data has been saved successfully.";
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

    public List selectDataForChkAuthClick(String accNo) throws ApplicationException {
        try {
            List selectDataForAuth = em.createNativeQuery("select DemandDt,PRN,INST,CHG from emidetails_sub where acno='" + accNo + "' and AUTH='N' ORDER BY DemandDT desc").getResultList();
            return selectDataForAuth;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectDemandDt(String accNo) throws ApplicationException {
        try {
            List selectDmndDate = em.createNativeQuery("select distinct demandDt from emidetails_sub where acno='" + accNo + "'").getResultList();
            return selectDmndDate;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String FnOtgChg(String accNo, String entryDate) throws ApplicationException {
        String result = null;
        try {
            String accountCode = facadeRemote.getAccountCode(accNo);
            List selectAccNature = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + accountCode + "'").getResultList();
            if (!selectAccNature.isEmpty()) {
                Vector vecAccNature = (Vector) selectAccNature.get(0);
                String strAccNature = vecAccNature.get(0).toString();
                if (strAccNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List selectAmtCA = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from ca_recon where trantype <> 8 and ifnull(trandesc,0) between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v1 = (Vector) selectAmtCA.get(0);
                    return v1.get(0).toString();
                } else if (strAccNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List selectAmtTLDL = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from loan_recon where trantype <> 8 and ifnull(trandesc,0) between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v2 = (Vector) selectAmtTLDL.get(0);
                    return v2.get(0).toString();
                } else if (strAccNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    List selectAmtNP = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from npa_recon where trantype <> 8 and ifnull(trandesc,0) between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v3 = (Vector) selectAmtNP.get(0);
                    return v3.get(0).toString();
                }
            } else {
                return "Data Not Find!!!";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String fnOtgInst(String accNo, String entryDate) throws ApplicationException {
        String result = null;
        try {
            String accountCode = facadeRemote.getAccountCode(accNo);
            List selectAccNature = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + accountCode + "'").getResultList();
            if (!selectAccNature.isEmpty()) {
                Vector vecAccNature = (Vector) selectAccNature.get(0);
                String strAccNature = vecAccNature.get(0).toString();
                if (strAccNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List selectAmtCA = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from ca_recon where trantype =8 and ifnull(trandesc,0) not between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v1 = (Vector) selectAmtCA.get(0);
                    return v1.get(0).toString();
                } else if (strAccNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List selectAmtTLDL = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from loan_recon where trantype = 8 and ifnull(trandesc,0) not between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v2 = (Vector) selectAmtTLDL.get(0);
                    return v2.get(0).toString();
                } else if (strAccNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    List selectAmtNP = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from npa_recon where trantype = 8 and ifnull(trandesc,0) not between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v3 = (Vector) selectAmtNP.get(0);
                    return v3.get(0).toString();
                }
            } else {
                return "Data Not Find!!!";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String fnOtgPrn(String accNo, String entryDate) throws ApplicationException {
        String accountCode = facadeRemote.getAccountCode(accNo);
        List selectAccNature = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + accountCode + "'").getResultList();
        String result = null;
        try {
            result = "";
            if (!selectAccNature.isEmpty()) {
                Vector vecAccNature = (Vector) selectAccNature.get(0);
                String strAccNature = vecAccNature.get(0).toString();
                if (strAccNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List selectAmtCA = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from ca_recon where trantype <>8 and ifnull(trandesc,0) not between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v1 = (Vector) selectAmtCA.get(0);
                    return result = v1.get(0).toString();
                } else if (strAccNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List selectAmtTLDL = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from loan_recon where trantype <>8 and ifnull(trandesc,0) not between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v2 = (Vector) selectAmtTLDL.get(0);
                    return result = v2.get(0).toString();
                } else if (strAccNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    List selectAmtNP = em.createNativeQuery("select ifnull((sum(CrAmt)-sum(DrAmt)),0) from npa_recon where trantype <>8 and ifnull(trandesc,0) not between 21 and 50 and dt<='" + entryDate + "' and acno='" + accNo + "'").getResultList();
                    Vector v3 = (Vector) selectAmtNP.get(0);
                    return result = v3.get(0).toString();
                }
            } else {
                return result = "Data Not Find!!!";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String finYear(String currentDate, String brncode) throws ApplicationException {
        String result;
        try {
            List selectFinYear = em.createNativeQuery("select f_year from yearend where mindate<= '" + currentDate + "' and maxdate >= '" + currentDate + "' and brncode = '" + brncode + "'").getResultList();
            if (!selectFinYear.isEmpty()) {
                Vector vecFinYear = (Vector) selectFinYear.get(0);
                return result = vecFinYear.get(0).toString();
            } else {
                return result = "0";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * DemandMarkingBean end Start code EditAccIntRateBean
     */
    public List tableData(String acno) throws ApplicationException {
        List tableResult = null;
        try {           
            Query selectQuery = em.createNativeQuery("Select a.ACNO,a.AC_INT_VER_NO,a.INT_TABLE_CODE,a.ACC_PREF_CR,a.MIN_INT_RATE_CR,a.MAX_INT_RATE_CR,a.AC_PREF_DR,a.MIN_INT_RATE_DR,a.MAX_INT_RATE_DR,a.INT_PEG_FLG,a.PEG_FREQ_MON,a.PEG_FREQ_DAYS,a.EFF_FRM_DT,ifnull(a.EFF_TO_DT,''),ifnull(a.EFF_NO_OF_DAYS,0),a.CREATED_BY,a.CREATION_DT,a.MOD_CNT,a.UPDATED_BY,a.UPDATED_DT,b.custname,a.penal_apply from cbs_acc_int_rate_details a ,accountmaster b   where a.ACNO='" + acno + "' and a.ACNO=b.acno  and AC_INT_VER_NO=(select max(AC_INT_VER_NO) from cbs_acc_int_rate_details where ACNO='" + acno + "')");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public List tableDataUnverified(String acno) throws ApplicationException {
        List tableResult = null;
        try {
            Query selectQuery = em.createNativeQuery("Select a.ACNO,a.AC_INT_VER_NO,a.INT_TABLE_CODE,a.ACC_PREF_CR,a.MIN_INT_RATE_CR,a.MAX_INT_RATE_CR,a.AC_PREF_DR,a.MIN_INT_RATE_DR,a.MAX_INT_RATE_DR,a.INT_PEG_FLG,a.PEG_FREQ_MON,a.PEG_FREQ_DAYS,a.EFF_FRM_DT,ifnull(a.EFF_TO_DT,''),ifnull(a.EFF_NO_OF_DAYS,0),a.CREATED_BY,a.CREATION_DT,a.MOD_CNT,a.UPDATED_BY,a.UPDATED_DT,b.custname,a.penal_apply from cbs_acc_int_rate_details a ,accountmaster b   where a.ACNO='" + acno + "' and a.ACNO=b.acno  and AC_INT_VER_NO=(select max(AC_INT_VER_NO) from cbs_acc_int_rate_details where ACNO='" + acno + "' and auth ='N')");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public List acctTypeCombo() throws ApplicationException {
        List actype = null;
        try {
            actype = em.createNativeQuery("select acctCode from accounttypemaster where acctNature in('TL','CA','DL')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
    }

    public List interestCodeCombo() throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("select interest_code,interest_code_description from cbs_loan_interest_code_master").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public String interestVerify(String acno, String auth, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String result = null;
            ut.begin();
            List userNamechk = em.createNativeQuery("select UPDATED_BY from cbs_acc_int_rate_details where acno ='" + acno + "' and auth ='N' ").getResultList();
            if (!userNamechk.isEmpty()) {
                Vector vect = (Vector) userNamechk.get(0);
                String user = vect.get(0).toString().toUpperCase();
                if (user.equalsIgnoreCase(userName)) {
                    ut.rollback();
                    result = "Sorry You can not Verify Your Own Entry!!";
                    return result;
                }
            }
            Query q1 = em.createNativeQuery("update cbs_acc_int_rate_details set auth ='" + auth + "' ,UPDATED_BY='" + userName + "',UPDATED_DT= now()  where acno ='" + acno + "' and auth ='N'");
            Integer q = q1.executeUpdate();
            if (q > 0) {
                ut.commit();
                result = "Record Save Successfully";
            } else {
                ut.rollback();
                result = "Data could not be Inserted.";
            }
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String interestSaveUpdate(String acno, String inttabcode, double accPrefCr, double minIntRateCr, double maxIntRateCr, double accPrefDr, double minIntRateDr, double maxIntRateDr, String intPegflag, int pegFreqMon, int pegFreqDays, String fromDate, String toDate, int effNoOfDays, String user, String curDate, String createBy, String createdt, String auth,String penalApply) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = null;
            Integer versionNo = 0;
            Integer recordModificationCount = 0;
            List unAuthAcno = em.createNativeQuery("select * from cbs_acc_int_rate_details where acno ='" + acno + "' and auth ='N' ").getResultList();
            if (!unAuthAcno.isEmpty()) {
                ut.rollback();
                result = "Please Authorize previous entry before modification..";
                return result;
            } else {

                List codeCheck = em.createNativeQuery("select AC_INT_VER_NO,MOD_CNT from cbs_acc_int_rate_details where ACNO='" + acno + "' and AC_INT_VER_NO=(select max(AC_INT_VER_NO) from cbs_acc_int_rate_details where ACNO='" + acno + "')").getResultList();
                Vector elem = (Vector) codeCheck.get(0);
                versionNo = Integer.parseInt(elem.get(0).toString());
                recordModificationCount = Integer.parseInt(elem.get(1).toString());
                if (codeCheck.size() <= 0) {
                    versionNo = 1;
                    recordModificationCount = 0;
                } else {
                    versionNo += 1;
                    recordModificationCount = recordModificationCount + 1;

                }
                //Query updateCbsloanAcctMstRate = em.createNativeQuery("update cbs_loan_acc_mast_sec set INTEREST_TABLE_CODE='" + inttabcode + "' where acno='" + acno + "'");
                //int resultupdate = updateCbsloanAcctMstRate.executeUpdate();


                Query CBS_LOAN_INTEREST_MASTER = em.createNativeQuery("insert into cbs_acc_int_rate_details(ACNO,AC_INT_VER_NO,INT_TABLE_CODE,ACC_PREF_CR,MIN_INT_RATE_CR,MAX_INT_RATE_CR,AC_PREF_DR,MIN_INT_RATE_DR, MAX_INT_RATE_DR,INT_PEG_FLG,PEG_FREQ_MON,PEG_FREQ_DAYS,EFF_FRM_DT,EFF_TO_DT,EFF_NO_OF_DAYS,CREATED_BY,CREATION_DT,MOD_CNT,UPDATED_BY,UPDATED_DT,auth,penal_apply)"
                        + " values('" + acno + "'," + versionNo + ",'" + inttabcode + "'," + accPrefCr + "," + minIntRateCr + "," + maxIntRateCr + "," + accPrefDr + "," + minIntRateDr + "," + maxIntRateDr + ",'" + intPegflag + "'," + pegFreqMon + "," + pegFreqDays + ",'" + fromDate + "','" + toDate + "'," + effNoOfDays + ",'" + createBy + "','" + createdt + "'," + recordModificationCount + ",'" + user + "','" + curDate + "','" + auth + "','"+penalApply+"')");
                Integer CBS_LOAN_INTEREST_MASTERVarient = CBS_LOAN_INTEREST_MASTER.executeUpdate();
                if (CBS_LOAN_INTEREST_MASTERVarient > 0) {
                    ut.commit();
                    result = "Record Save Successfully";
                } else {
                    ut.rollback();
                    result = "Data could not be Inserted.";
                }
                return result;
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

    public java.util.List getUnVerifiedAcNo(String branchCode) throws com.cbs.exception.ApplicationException {
        List acNoList = null;
        try {
            acNoList = em.createNativeQuery(" select ACNO,AC_INT_VER_NO from cbs_acc_int_rate_details where auth ='N' and substring(acno,1,2)='" + branchCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acNoList;
    }

    public List dateDiff(String fromDt, String toDt) throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY, '" + fromDt + "', '" + toDt + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public String acNature(String acType) throws ApplicationException {
        String message = "";
        try {
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
            if (acNatList.isEmpty() || acNatList.size() < 0) {
                message = "ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT";
                return message;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }
    /**
     *
     * End code EditAccIntRateBean
     *
     * Start the code LoanDemandPostBean
     */
    SimpleDateFormat yMMd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List post(String acctType, String acno, String user, String date, String frDt, String toDt, String orgnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List resultList = new ArrayList();
        String message = "";
        Integer sno = null;
        Integer dmdSrl = null;
        String accountNo = null;
        double installamt;
        double prinamt;
        double interestamt;
        String dmdPFlag = null;
        String dmdIFlag;
        String lgFreq = "M";
        String ovduDate = null;
        Integer ShdlNo = null;
        double elAmt;
        String dueDate;

        List emiList = new ArrayList();
        try {
            ut.begin();

            List loanList = em.createNativeQuery("select ifnull(max(Sno),0) from cbs_loan_dmd_info").getResultList();
            if (loanList.size() > 0) {
                Vector ele = (Vector) loanList.get(0);
                sno = Integer.parseInt(ele.get(0).toString());
                if (sno == 0) {
                    sno = 1;
                } else {
                    sno = sno + 1;
                }
            }
            List loanDList = em.createNativeQuery("select dmd_srl_num from cbs_loan_dmd_table  where DMD_DATE = '" + date + "'").getResultList();
            if (loanDList.size() > 0) {
                Vector ele = (Vector) loanDList.get(0);
                dmdSrl = Integer.parseInt(ele.get(0).toString());
            } else {
                List loanDMList = em.createNativeQuery("select ifnull(max(dmd_srl_num),0) from cbs_loan_dmd_table ").getResultList();
                if (loanDMList.size() > 0) {
                    Vector ele = (Vector) loanDMList.get(0);
                    dmdSrl = Integer.parseInt(ele.get(0).toString()) + 1;
                }
            }
            if (acctType.equalsIgnoreCase("A")) {
                emiList = em.createNativeQuery("select a.acno,ifnull(a.installamt,0),"
                        + "ifnull(a.prinamt,0),ifnull(a.interestamt,0),a.duedt, b.accstatus from "
                        + "emidetails a,accountmaster b where a.acno = b.acno and b.accstatus <>9 "
                        + "and a.status = 'unpaid' and a.duedt <= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                        + "where acno=a.acno and status='Unpaid')  "
                        + "and a.acno=b.acno and b.curBrcode = '" + orgnCode + "' "
                        + "order by a.acno").getResultList();
                if (emiList.isEmpty()) {
                    ut.rollback();
                    resultList.add("EMI Schedule does not exists Between " + sdf.format(ymd.parse(frDt)) + " to " + sdf.format(ymd.parse(toDt)));
                    return resultList;
                }

                List loanDemandList = em.createNativeQuery("select * from cbs_loan_dmd_info where FromDT = '" + frDt + "' and ToDT = "
                        + "'" + toDt + "' and flag = 'A'").getResultList();
                if (!loanDemandList.isEmpty()) {
                    ut.rollback();
                    resultList.add("Demand for this period already Posted");
                    return resultList;
                }
                Integer demandInfoList = em.createNativeQuery("Insert into cbs_loan_dmd_info(sno,acno,flag,brnCode,postingDt,FromDT,ToDT) Values"
                        + "(" + sno + ",'','" + acctType + "','" + orgnCode + "',now(),'" + frDt + "','" + toDt + "')").executeUpdate();
                if (demandInfoList < 0) {
                    ut.rollback();
                    resultList.add("Data is not inserted into cbs_loan_demand_info");
                    return resultList;
                }
            } else if (acctType.equalsIgnoreCase("S")) {
                emiList = em.createNativeQuery("select a.acno,ifnull(a.installamt,0),"
                        + "ifnull(a.prinamt,0),ifnull(a.interestamt,0),a.duedt, b.accstatus "
                        + "from emidetails a,accountmaster b where a.acno = b.acno and b.accstatus"
                        + " <>9 and a.status = 'unpaid' and a.duedt between '" + frDt + "' and"
                        + " '" + toDt + "'  "
                        + "and a.acno = b.acno and b.curBrcode = '" + orgnCode + "' and a.acno = '" + acno + "'  order "
                        + "by a.acno").getResultList();
                if (emiList.isEmpty()) {
                    ut.rollback();
                    resultList.add("EMI Schedule does not exists Between " + sdf.format(ymd.parse(frDt)) + " to " + sdf.format(ymd.parse(toDt)));
                    return resultList;
                }

                List loanDemandList = em.createNativeQuery("select * from cbs_loan_dmd_info where FromDT = '" + frDt + "' and ToDT = "
                        + "'" + toDt + "' and flag = 'A'").getResultList();
                if (!loanDemandList.isEmpty()) {
                    ut.rollback();
                    resultList.add("Demand for this period already Posted");
                    return resultList;
                }
                List loanDemanList = em.createNativeQuery("select * from cbs_loan_dmd_info where postingDt = '" + date + "'  and acno = '" + acno + "' and flag = 'S'").getResultList();
                if (!loanDemanList.isEmpty()) {
                    ut.rollback();
                    resultList.add("Demand for this period already Posted againt this Account No.");
                    return resultList;
                }

                Integer demandInfoList = em.createNativeQuery("Insert into cbs_loan_dmd_info(sno,acno,flag,brnCode,postingDt,FromDT,ToDT) Values"
                        + "(" + sno + ",'" + acno + "','" + acctType + "','" + orgnCode + "','" + date + "','" + frDt + "','" + toDt + "')").executeUpdate();
                if (demandInfoList < 0) {
                    ut.rollback();
                    resultList.add("Data is not inserted into cbs_loan_demand_info");
                    return resultList;
                }
            }
            String schemeCode = null;
            for (int i = 0; i < emiList.size(); i++) {
                Vector ele = (Vector) emiList.get(i);
//                if (acctType.equalsIgnoreCase("A")) {
//                    accountNo = "";
//                } else if (acctType.equalsIgnoreCase("S")) {
//                    accountNo = ele.get(0).toString();
//                }

                accountNo = ele.get(0).toString();
                List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE from "
                        + "cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND "
                        + "A.ACNO ='" + accountNo + "'").getResultList();
                if (SecDetailsList.isEmpty()) {
                    ut.rollback();
                    resultList.add("Account No Does Not Exists in Secondary Details table of Loan.");
                    return resultList;
                } else {
                    for (int j = 0; j < SecDetailsList.size(); j++) {
                        Vector SecDetailsVect = (Vector) SecDetailsList.get(j);
                        schemeCode = (String) SecDetailsVect.get(1);
                    }
                }
                List flowDetailList = em.createNativeQuery("select ei_flow_id, principal_flow_id,"
                        + " disbursement_flow_id, collection_flow_id, int_demand_flow_id, "
                        + "penal_int_demand_flow_id, overdue_int_demand_flow_id, past_due_collection_flow_id,"
                        + " charge_demand_flow_id from cbs_scheme_loan_prepayment_details where "
                        + "scheme_code =  '" + schemeCode + "'").getResultList();
                String eiFlowId = null;
                String prinDemFlowId = null;
                String disbFlowId;
                String colFlowId = null;
                String intDemFlowId = null;
                String penalIntDemFlowId = null;
                String overdueIntFlowId;
                String pastDueColNPAFlowId = null;
                String chgDemFlowId = null;
                double overFlowAmt = 0;

                if (flowDetailList.isEmpty()) {
                    ut.rollback();
                    resultList.add("Flow Id Does Not Exists in Scheme Master.");
                    return resultList;
                } else {
                    for (int k = 0; k < flowDetailList.size(); k++) {
                        Vector flowDetailVect = (Vector) flowDetailList.get(k);
                        eiFlowId = flowDetailVect.get(0).toString();
                        prinDemFlowId = flowDetailVect.get(1).toString();
                        disbFlowId = flowDetailVect.get(2).toString();
                        colFlowId = flowDetailVect.get(3).toString();
                        intDemFlowId = flowDetailVect.get(4).toString();
                        penalIntDemFlowId = flowDetailVect.get(5).toString();
                        overdueIntFlowId = flowDetailVect.get(6).toString();
                        pastDueColNPAFlowId = flowDetailVect.get(7).toString();
                        chgDemFlowId = flowDetailVect.get(8).toString();

                    }
                }
                String fromDt = loanIntCalcRemote.allFromDt(accountNo.substring(2, 4), orgnCode, "f");
                String tooDt = loanIntCalcRemote.allFromDt(accountNo.substring(2, 4), orgnCode, "t");

                LoanIntCalcList it = loanIntCalcRemote.accWiseLoanIntCalc(frDt, toDt, accountNo, orgnCode);
                double intAmt = it.getTotalInt() * -1;

                installamt = Double.parseDouble(ele.get(1).toString());
                prinamt = Double.parseDouble(ele.get(2).toString());
                interestamt = Double.parseDouble(ele.get(3).toString()) != 0 ? Double.parseDouble(ele.get(3).toString()) : intAmt;
                dueDate = ele.get(4).toString();
                if ((prinamt + interestamt) == prinamt) {
                    elAmt = (float) 0;
                } else if ((prinamt + interestamt) == interestamt) {
                    elAmt = (float) 0;
                } else {
                    elAmt = prinamt + interestamt;
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(yMMd.parse(dueDate));
                cal.add(cal.DATE, 1);
                String overdueDt = ymd.format(cal.getTime());
                //  System.out.println("Acno: "+accountNo+",dueDate: "+dueDate+"overdueDt:"+overdueDt+";");
//                List convertList = em.createNativeQuery("select convert(datetime,DateAdd(d, 1,'" + date + "'),103)").getResultList();
//                if (convertList.size() > 0) {
//                    Vector secVec = (Vector) convertList.get(0);
//                    ovduDate = secVec.get(0).toString();
//                }


                List snoList = em.createNativeQuery("select ifnull(max(SHDL_NUM),0) from cbs_loan_dmd_table where DMD_SRL_NUM = " + sno).getResultList();

                if (snoList.size() > 0) {
                    Vector secVec = (Vector) snoList.get(0);
                    ShdlNo = Integer.parseInt(secVec.get(0).toString());
                    if (ShdlNo == 0) {
                        ShdlNo = 1;
                    } else {
                        ShdlNo = ShdlNo + 1;
                    }

                }

//                List mastSecList = em.createNativeQuery("select calc_method from  cbs_loan_acc_mast_sec where acno = '" + accountNo + "'").getResultList();
//                if (mastSecList.size() > 0) {
//                    Vector secVec = (Vector) mastSecList.get(0);
//                    lgFreq = secVec.get(0).toString();
//                }

//                List loanDmdTableList = em.createNativeQuery("select * from cbs_loan_dmd_table where acno = '" + accountNo + "' and DMD_DATE = '" + date + "'").getResultList();
//                if (loanDmdTableList.isEmpty()) {
                if (acctType.equalsIgnoreCase("A")) {
                    if (prinamt != 0) {
                        Integer loanDmdList = em.createNativeQuery("insert into cbs_loan_dmd_table(acno,shdl_num,dmd_flow_id,dmd_date,"
                                + "del_flg,ld_freq_type,dmd_eff_date,dmd_ovdu_date,dmd_amt,rcre_user_id,"
                                + "rcre_time,ei_amt,ts_cnt,latefee_status_flg,dmd_srl_num) Values"
                                + "('" + accountNo + "'," + ShdlNo + ",'" + prinDemFlowId + "','" + date + "','N','" + lgFreq + "','" + dueDate + "','" + overdueDt + "'," + prinamt + ","
                                + "'" + user + "',now()," + elAmt + ",0,'N'," + sno + ")").executeUpdate();
                        if (loanDmdList < 0) {
                            ut.rollback();
                            resultList.add("Data is not inserted into cbs_loan_dmd_table");
                            return resultList;

                        }
                    }
                    if (interestamt != 0) {

                        Integer loanDmdList = em.createNativeQuery("insert into cbs_loan_dmd_table(acno,shdl_num,dmd_flow_id,dmd_date,"
                                + "del_flg,ld_freq_type,dmd_eff_date,dmd_ovdu_date,dmd_amt,rcre_user_id,"
                                + "rcre_time,ei_amt,ts_cnt,latefee_status_flg,dmd_srl_num) values"
                                + "('" + accountNo + "'," + ShdlNo + ",'" + intDemFlowId + "','" + date + "','N','" + lgFreq + "','" + dueDate + "','" + overdueDt + "'," + interestamt + ","
                                + "'" + user + "',now()," + elAmt + ",0,'N'," + sno + ")").executeUpdate();
                        if (loanDmdList < 0) {
                            ut.rollback();
                            resultList.add("Data is not inserted into cbs_loan_dmd_table");
                            return resultList;
                        }
                    }
                }
//                } else {
                if (acctType.equalsIgnoreCase("S")) {
                    ut.rollback();
                    resultList.add("Demand for this period already Posted againt this Account No.");
                    return resultList;
                }
//                }

            }
            ut.commit();
            resultList = em.createNativeQuery("select acno,shdl_num,dmd_flow_id,dmd_date,ld_freq_type,"
                    + "dmd_eff_date,dmd_ovdu_date,dmd_amt,ei_amt,latefee_status_flg from cbs_loan_dmd_table  where dmd_srl_num = " + dmdSrl + " order by ACNO,dmd_eff_date").getResultList();
        } catch (Exception e) {
            try {
                ut.rollback();
                e.printStackTrace();
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
        return resultList;
    }

    public List accountDetail(String acctNo) throws ApplicationException {
        List selectQuery = null;
        try {
            selectQuery = em.createNativeQuery("select acno,custname,Date_format(openingdt,'%d/%m/%Y'),accstatus from accountmaster where acno='" + acctNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return selectQuery;
    }

    /**
     * ********
     * end LoanDemandPost
     *
     * Start ODDPLimitEnhancementBean
     *
     *
     *
     ********
     */
    public List accountTypeDropDown() throws ApplicationException {
        List brcode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctCode from accounttypemaster where acctNature in ('TL','CA','DL','SS')");
            brcode = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return brcode;
    }

    public List detailRoi(String acctNo, String brnCode) throws ApplicationException {
        List bilType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select ap.Acno,ap.CustName,coalesce(ap.ROI,0)as ROI,coalesce(ap.PenalROI,0)as PenalROI,coalesce(ap.Sanctionlimit,0) as Sanctionlimit,coalesce(ap.ODLimit,0)as ODLimit,coalesce(ap.dpLimit,0) as dpLimit,coalesce(ap.Adhoclimit,0)as Adhoclimit,coalesce(ap.AdhocROI,0) as AdhocROI,coalesce(replace(date_format(ifnull(ap.Adhocapplicabledt,'01/01/1900'),'%d/%m/%Y'),'01/01/1900',''),'')as Adhocapplicabledt,coalesce(replace(date_format(ifnull(ap.AdhocExpiry,'01/01/1900'),'%d/%m/%Y'),'01/01/1900',''),'')as AdhocExpiry,coalesce(ap.MaxLimit,0)as MaxLimit,coalesce(ap.SubsidyAmt,0)as SubsidyAmt,coalesce(replace(Date_format(ifnull(ap.SubsidyExpdt,'01/01/1900'),'%d/%m/%Y'),'01/01/1900',''),'') as SubsidyExpdt,ifnull(ap.int_opt,'N') int_opt "
                    + "from loan_appparameter ap,accountmaster am where ap.acno='" + acctNo + "' and ap.acno=am.acno "
                    + "and accStatus <> 9 and am.curBrcode='" + brnCode + "'");
            bilType = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return bilType;
    }

    public String odEnhancementSave(String ACNO, String actype, float roi, float newroi, float penalroi,
            float penalty, float sanctionlimit, float newsanctionlimit, float maxlimit, float newmaxlimit,
            float adhoclimit, float newadhoclimit, float adhocroi, float newadhocroi, String adhocappdt,
            String newadhocappdt, String adhoctilldt, String newadhoctilldt, float subsidyamt,
            float newsubsidyamt, String subsidyexpdt, String newsubsidyexpdt, String intopt, String enterby, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            String result = null;
            String tempbd = "";
            float SAN_LIMIT = 0;
            String SAN_DT = "";
            String tmpANat = "";
            String currentBrnCode = facadeRemote.getCurrentBrnCode(ACNO);
            String accountCode = facadeRemote.getAccountCode(ACNO);
            List matDateList = em.createNativeQuery("SELECT date_format(date,'%Y%m%d') FROM bankdays WHERE DAYENDFLAG='N' and brncode ='" + currentBrnCode + "'").getResultList();
            if (matDateList.size() > 0) {
                Vector descVect = (Vector) matDateList.get(0);
                tempbd = descVect.get(0).toString();
            }
            List acctNatureList = em.createNativeQuery("SELECT acctnature FROM accounttypemaster WHERE ACCTCODE = '" + accountCode + "'").getResultList();
            if (acctNatureList.size() > 0) {
                Vector acctNatureVec = (Vector) acctNatureList.get(0);
                tmpANat = acctNatureVec.get(0).toString();
            }
            List tmppostList = em.createNativeQuery("Select acno From loan_appparameter Where acno ='" + ACNO + "'").getResultList();
            if (tmppostList.size() > 0) {
                List sqList = em.createNativeQuery("SELECT COALESCE(SANCTIONLIMIT,0),replace(date_format(ifnull(sanctionlimitdt,'19000101'),'%Y%m%d'),'19000101','') FROM loan_appparameter Where acno ='" + ACNO + "'").getResultList();
                if (sqList.size() > 0) {
                    Vector sqdsVec = (Vector) sqList.get(0);
                    SAN_LIMIT = Float.parseFloat(sqdsVec.get(0).toString());
                    SAN_DT = sqdsVec.get(1).toString();
                }
            }

            if (tmpANat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                float pRoi = ((penalty + roi) / 12);

                Integer insertinfo = em.createNativeQuery("insert loan_oldinterest(Acno,Roi,PenalRoi,acLimit,enterBy,enterdate,adhocLimit,adhocTillDt,adhocInterest,AdhocApplicableDt,MaxLimit,SanctionLimitDt,SANCTIONLIMIT)"
                        + " values('" + ACNO + "'," + roi + "," + pRoi + "," + sanctionlimit + ",'" + enterby + "','" + date + "'," + adhoclimit + ",'" + adhoctilldt + "'," + adhocroi + ",'" + adhocappdt + "' ," + maxlimit + ",'" + SAN_DT + "'," + SAN_LIMIT + ")").executeUpdate();
                if (insertinfo <= 0) {
                    ut.rollback();
                    result = "Data Not Saved in loan_oldinterest";
                    return result;
                }

                List tmppostList1 = em.createNativeQuery("Select acno From loan_appparameter Where acno ='" + ACNO + "'").getResultList();
                if (tmppostList1.size() > 0) {
                    Integer AccountMaster = em.createNativeQuery("Update accountmaster set AdhocInterest = " + newadhocroi + ",adhocLimit =" + newadhoclimit + ", adhoctilldt ='" + newadhoctilldt + "', /*IntDeposit = " + newroi + ",penalty = " + penalroi + ",*/lastUpdateDt = '" + tempbd + "',ODLimit = " + newsanctionlimit + " Where Acno ='" + ACNO + "'").executeUpdate();
                    if (AccountMaster <= 0) {
                        ut.rollback();
                        result = "Data Not Updated in accountmaster";
                        return result;
                    }
                    if (sanctionlimit != newsanctionlimit) {
                        Integer loanapprameter = em.createNativeQuery("Update loan_appparameter Set /*Roi=" + newroi + ", PenalROI=" + penalroi + ",*/maxLimit=" + newmaxlimit + ",Sanctionlimitdt='" + tempbd + "',odLimit=" + newsanctionlimit + ",Adhoclimit=" + newadhoclimit + ", AdhocROI=" + newadhocroi + ",AdhocApplicableDt='" + newadhocappdt + "' ,AdhocExpiry='" + newadhoctilldt + "' Where Acno ='" + ACNO + "'").executeUpdate();
                        if (loanapprameter <= 0) {
                            ut.rollback();
                            result = "Data Not Updated in loan_appparameter";
                            return result;
                        }
                    } else {
                        Integer loanapprameter1 = em.createNativeQuery("Update loan_appparameter Set /*Roi=" + newroi + ", PenalROI=" + penalroi + ",*/maxLimit=" + newmaxlimit + ",odLimit=" + newsanctionlimit + ",Adhoclimit=" + newadhoclimit + ", AdhocROI=" + newadhocroi + ",AdhocApplicableDt='" + newadhocappdt + "',AdhocExpiry='" + newadhoctilldt + "' Where Acno ='" + ACNO + "'").executeUpdate();
                        if (loanapprameter1 <= 0) {
                            ut.rollback();
                            result = "Data Not Updated in loan_appparameter";
                            return result;
                        }
                    }


                    if (!actype.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                        Integer loanapprameter = em.createNativeQuery("Update loan_appparameter Set SubsidyAmt =" + newsubsidyamt + ", SubsidyExpdt='" + newsubsidyexpdt + "' Where Acno = '" + ACNO + "'").executeUpdate();
                        if (loanapprameter <= 0) {
                            ut.rollback();
                            result = "Data Not Updated in loan_appparameter";
                            return result;
                        }
                    }

                    if (actype.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode())) {
                        if (sanctionlimit != newsanctionlimit) {
                            Integer loanapprameter = em.createNativeQuery("Update loan_appparameter Set /*Roi=" + newroi + ", PenalROI=" + penalroi + ",*/maxLimit=" + newmaxlimit + ",Sanctionlimitdt='" + tempbd + "',odLimit=" + newsanctionlimit + ",Adhoclimit=" + newadhoclimit + ", AdhocROI=" + newadhocroi + " ,AdhocApplicableDt='" + newadhocappdt + "' ,AdhocExpiry='" + newadhoctilldt + "' Where Acno = '" + ACNO + "'").executeUpdate();
                            if (loanapprameter <= 0) {
                                ut.rollback();
                                result = "Data Not Updated in loan_appparameter";
                                return result;
                            }
                        } else {
                            Integer loanapprameter = em.createNativeQuery("Update loan_appparameter Set /*Roi=" + newroi + ", PenalROI=" + penalroi + ",*/maxLimit=" + newmaxlimit + ",odLimit=" + newsanctionlimit + ",Adhoclimit=" + newadhoclimit + ", AdhocROI=" + newadhocroi + " ,AdhocApplicableDt='" + newadhocappdt + "' ,AdhocExpiry='" + newadhoctilldt + "' Where Acno = '" + ACNO + "'").executeUpdate();
                            if (loanapprameter <= 0) {
                                ut.rollback();
                                result = "Data Not Updated in loan_appparameter";
                                return result;
                            }
                        }

                    }
                }

            } else {
                List sqList = em.createNativeQuery("Select acno From loan_appparameter Where acno ='" + ACNO + "'").getResultList();
                if (sqList.size() > 0) {
                    if (tmpANat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        float penalRoi = (penalty / 12);
                        if (subsidyexpdt.equals("")) {
                            subsidyexpdt = "19000101";
                        }
                        if (newsubsidyexpdt.equals("")) {
                            newsubsidyexpdt = "19000101";
                        }
                        Integer insertinfo = em.createNativeQuery("Insert loan_oldinterest(Acno,/*Roi,PenalRoi,*/acLimit,enterBy,enterdate,MaxLimit,SanctionLimitDt,sanctionLimit,SubsidyAmt,SubsidyExpDt)"
                                + " values('" + ACNO + "',/*" + roi + "," + penalRoi + ",*/" + sanctionlimit + ",'" + enterby + "','" + tempbd + "'," + maxlimit + ",'" + SAN_DT + "'," + sanctionlimit + "," + subsidyamt + " ,'" + subsidyexpdt + "')").executeUpdate();
                        if (insertinfo <= 0) {
                            ut.rollback();
                            result = "Data Not Saved in loan_oldinterest";
                            return result;
                        }
                        if (sanctionlimit != newsanctionlimit) {
                            Integer loanapprameter = em.createNativeQuery("Update loan_appparameter Set /*ROI=" + newroi + ", PenalROI=" + penalroi + ",*/MaxLimit=" + newmaxlimit + ",Sanctionlimitdt='" + tempbd + "',ODLimit=" + newsanctionlimit + ",sanctionLimit=" + newsanctionlimit + ", SubsidyAmt =" + newsubsidyamt + ", SubsidyExpdt='" + newsubsidyexpdt + "' Where Acno = '" + ACNO + "'").executeUpdate();
                            if (loanapprameter <= 0) {
                                ut.rollback();
                                result = "Data Not Updated in loan_appparameter";
                                return result;
                            }

                        } else {
                            Integer loanapprameter = em.createNativeQuery("Update loan_appparameter Set /*ROI=" + newroi + ", PenalROI=" + penalroi + ",*/MaxLimit=" + newmaxlimit + ",ODLimit=" + newsanctionlimit + ",sanctionLimit=" + newsanctionlimit + ", SubsidyAmt = " + newsubsidyamt + ", SubsidyExpdt='" + newsubsidyexpdt + "' Where Acno = '" + ACNO + "'").executeUpdate();
                            if (loanapprameter <= 0) {
                                ut.rollback();
                                result = "Data Not Updated in loan_appparameter";
                                return result;
                            }

                        }

                        Integer loanapprameter = em.createNativeQuery("Update accountmaster set /*IntDeposit =" + newroi + ",*/ Adhoclimit =" + newsubsidyamt + ",AdhocTillDt = '" + newsubsidyexpdt + "',Penalty = " + penalroi + ",LastUpdateDt = '" + tempbd + "',ODLimit =" + newsanctionlimit + " Where Acno ='" + ACNO + "'").executeUpdate();
                        if (loanapprameter <= 0) {
                            ut.rollback();
                            result = "Data Not Updated in accountmaster";
                            return result;
                        }

                        Integer loanappra = em.createNativeQuery("Update apploandetails set AmtSanctioned=" + newsanctionlimit + ",Sanctiondt='" + tempbd + "'/*, intRate=" + newroi + "*/ Where Acno = '" + ACNO + "'").executeUpdate();
                        if (loanappra <= 0) {
                            ut.rollback();
                            result = "Data Not Updated in apploandetails";
                            return result;
                        }
                    } else {
                        float penalRoi = (penalty / 12);
                        Integer insertinfo = em.createNativeQuery("Insert loan_oldinterest(Acno,/*Roi,PenalRoi,*/acLimit,enterBy,enterdate,MaxLimit,SanctionLimitDt,SANCTIONLIMIT)"
                                + " values('" + ACNO + "',/*" + roi + "," + penalRoi + ",*/" + sanctionlimit + ",'" + enterby + "','" + tempbd + "'," + maxlimit + ",'" + SAN_DT + "'," + SAN_LIMIT + ")").executeUpdate();
                        if (insertinfo <= 0) {
                            ut.rollback();
                            result = "Data Not Saved in loan_oldinterest";
                            return result;
                        }
                        if (sanctionlimit != newsanctionlimit) {
                            Integer loanapprameter = em.createNativeQuery("Update loan_appparameter Set /*ROI=" + newroi + ", PenalROI=" + penalroi + ",*/MaxLimit=" + newmaxlimit + ",Sanctionlimitdt='" + tempbd + "',ODLimit=" + newsanctionlimit + " Where Acno ='" + ACNO + "'").executeUpdate();
                            if (loanapprameter <= 0) {
                                ut.rollback();
                                result = "Data Not Updated in loan_appparameter";
                                return result;
                            }

                        } else {
                            Integer loanapprameter = em.createNativeQuery("Update loan_appparameter Set /*ROI=" + newroi + ", PenalROI=" + penalroi + ",*/MaxLimit=" + newmaxlimit + ",ODLimit=" + newsanctionlimit + " Where Acno ='" + ACNO + "'").executeUpdate();
                            if (loanapprameter <= 0) {
                                ut.rollback();
                                result = "Data Not Updated in loan_appparameter";
                                return result;
                            }

                        }

                        Integer loanapprameter = em.createNativeQuery("Update accountmaster set AdhocInterest = " + newadhocroi + ",AdhocLimit = " + newadhoclimit + ",AdhocTillDt ='" + newadhoctilldt + "',/* IntDeposit =" + newroi + ",Penalty = " + penalroi + ",*/LastUpdateDt = '" + tempbd + "',ODLimit = " + newsanctionlimit + " Where Acno = '" + ACNO + "'").executeUpdate();
                        if (loanapprameter <= 0) {
                            ut.rollback();
                            result = "Data Not Updated in accountmaster";
                            return result;
                        }

                        Integer loanappra = em.createNativeQuery("Update apploandetails set AmtSanctioned=" + newsanctionlimit + ",Sanctiondt='" + tempbd + "'/*, intRate=" + newroi + "*/ Where Acno ='" + ACNO + "'").executeUpdate();
                        if (loanappra <= 0) {
                            ut.rollback();
                            result = "Data Not Updated in apploandetails";
                            return result;
                        }
                    }

                }
            }

            if (!intopt.equalsIgnoreCase("")) {
                Integer loanappra = em.createNativeQuery("Update loan_appparameter Set int_opt='" + intopt + "' Where Acno ='" + ACNO + "'").executeUpdate();
                if (loanappra <= 0) {
                    ut.rollback();
                    result = "Data Not Updated in loan_appparameter";
                    return result;
                }

            }
            Integer loanapprameter1 = em.createNativeQuery("Update loan_appparameter Set Adhocapplicabledt=null Where Adhocapplicabledt=''").executeUpdate();
            Integer loanapprameter2 = em.createNativeQuery("Update loan_appparameter Set AdhocExpiry=null Where AdhocExpiry =''").executeUpdate();
            Integer loanapprameter3 = em.createNativeQuery("Update loan_appparameter Set SubsidyExpDt=null Where SubsidyExpdt=''").executeUpdate();
            ut.commit();
            result = "Record Saved Successfully";
            return result;
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
     * ****
     *
     *
     *
     * Start code LienMarkingFromSecurityBean ******
     */
    public List acctTypeComboLI() throws ApplicationException {
        List actype = null;
        try {
            actype = em.createNativeQuery("Select AcctCode From accounttypemaster where acctnature in ('FD','MS','OF')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
    }

    public String dlAcOpen() throws ApplicationException {
        String desc = null;
        try {
            List lt = em.createNativeQuery("Select Description From codebook Where GroupCode=51 and code=61").getResultList();
            if (!lt.isEmpty()) {
                Vector ele = (Vector) lt.get(0);
                desc = ele.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return desc;
    }

    public String auth(String userName, String brCode) throws ApplicationException {
        String msg = null;
        try {
            List lt = em.createNativeQuery("select levelId from securityinfo where userid='" + userName + "' and brncode='" + brCode + "' and levelId in (1,2)").getResultList();
            if (lt.isEmpty()) {
                msg = "False";
            } else {
                msg = "True";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public List customerDetail(String acno) throws ApplicationException {
        List lt = null;
        try {
            lt = em.createNativeQuery("Select am.Custname,cb.Description,am.JtName1, c.CustId, ifnull(am.custid1,''),ifnull(am.custid2,''),ifnull(am.custid3,''),ifnull(am.custid4,'') From td_accountmaster am, codebook cb, customerid c where am.ACNO = c.Acno and am.acno='" + acno + "' and am.OperMode=cb.code and cb.groupcode=4 ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return lt;
    }

    public List gridDetailLoad(String acno, String acnature) throws ApplicationException {
        List lt = null;
        try {
            if (acnature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                lt = em.createNativeQuery("Select Acno, voucherno, receiptNo, prinamt, date_format(fddt,'%d/%m/%Y'), date_format(matDt,'%d/%m/%Y'), date_format(TD_MadeDT,'%d/%m/%Y'), IntOpt, roi, status, seqno, coalesce(lien,'') From td_vouchmst "
                        + " where OFacno='" + acno + "' and status='c' AND LIEN='Y' order by status, voucherno").getResultList();
            } else if (acnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                lt = em.createNativeQuery("Select Acno, voucherno, receiptNo, prinamt, date_format(fddt,'%d/%m/%Y'), date_format(matDt,'%d/%m/%Y'), date_format(TD_MadeDT,'%d/%m/%Y'), IntOpt, roi, status, seqno, coalesce(lien,'') From td_vouchmst "
                        + " where acno='" + acno + "' and status<>'c' order by status, voucherno").getResultList();
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return lt;
    }

    public String tdLienPresentAmount(String acno, Float voucherno, Float prinamount) throws ApplicationException {
        Float pretds = 0.0f;
        Float preint = 0.0f;
        String remarks = "";
        Float presentamt = null;
        try {

            List chk1 = em.createNativeQuery("select VOUCHERNO,REMARKS from td_lien_update WHERE VOUCHERNO=" + voucherno + " AND TXNDATE IN (select MAX(TXNDATE) from td_lien_update  WHERE VOUCHERNO=" + voucherno + " and acno = '" + acno + "') and acno = '" + acno + "'").getResultList();
            if (!chk1.isEmpty()) {
                List chk2 = em.createNativeQuery("select REMARKS from td_lien_update WHERE VOUCHERNO=" + voucherno + " AND TXNDATE IN (select MAX(TXNDATE) from td_lien_update  WHERE VOUCHERNO=" + voucherno + " and acno = '" + acno + "') and acno = '" + acno + "'").getResultList();
                if (!chk2.isEmpty()) {
                    Vector ele = (Vector) chk2.get(0);
                    remarks = ele.get(0).toString();
                }
            }
            List chk3 = em.createNativeQuery("select sum(ifnull(Interest,0)) From td_interesthistory where acno='" + acno + "' and voucherno=" + voucherno + "").getResultList();
            if (!chk3.isEmpty()) {
                List chk4 = em.createNativeQuery("select coalesce(sum(ifnull(Interest,0)),0) From td_interesthistory where acno='" + acno + "' and voucherno=" + voucherno + " ").getResultList();
                if (!chk4.isEmpty()) {
                    Vector ele = (Vector) chk4.get(0);
                    preint = Float.parseFloat(ele.get(0).toString());
                }
            }
            List chk5 = em.createNativeQuery("select sum(ifnull(TDS,0)) From tdshistory where acno='" + acno + "' and voucherno=" + voucherno + "").getResultList();
            if (!chk5.isEmpty()) {
                List chk6 = em.createNativeQuery("select coalesce(sum(ifnull(TDS,0)),0) From tdshistory where acno='" + acno + "' and voucherno=" + voucherno + " ").getResultList();
                if (!chk6.isEmpty()) {
                    Vector ele = (Vector) chk6.get(0);
                    pretds = Float.parseFloat(ele.get(0).toString());
                }
            }
            presentamt = prinamount + preint - pretds;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return remarks + "*" + presentamt.toString();
    }

    public String saveLienMarkingDetail(Float ReceiptNo, Float VchNo, String Actype, String AcNO, String LAcNO, String chkLien, String AUTH, String enteredby, String remark, String Loan_Lien_Call, String tmpSecType, String DLAccOpen_Lien, String BillLcBg_Lien, String brnCode, String roiOnSecurity, String secODScheme, String margin, String additionalRoi) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            int var1 = 0, var2 = 0, var3 = 0, var4 = 0;
            String LienD = null;
            Float prinAmtD = 0.0f;
            String FdDtD = null;
            String MatDtD = null;
            Float RoiD = 0.0f;
            String IntOptD = null, intToAcNo = "";
            String Tempbd = null;
            String acctNo = null;
            Float VoucherNo = 0.0f;
            Float Vreceiptno = 0.0f;
            Float prinAmt = 0.0f;
            String FDDt = null;
            String TDCodeDesc = null;
            String MatDt = null;
            Integer AutoSno = null;
            Float ROI = 0.0f;
            String TmpMsg = null;
            String accountCode = "";
            String type = "";
            if (additionalRoi.equalsIgnoreCase("") || additionalRoi.equalsIgnoreCase(null)) {
                additionalRoi = "0";
            }
            if (roiOnSecurity.equalsIgnoreCase("") || roiOnSecurity.equalsIgnoreCase(null)) {
                roiOnSecurity = "0";
            }
            if (margin.equalsIgnoreCase("") || margin.equalsIgnoreCase(null)) {
                margin = "0";
            }
            if (ReceiptNo == null) {
                ut.rollback();
                msg = "PLEASE SELECT RECEIPT NO FROM GRID !!!";
                return msg;
            }
            if (VchNo == null) {
                ut.rollback();
                msg = "VOUCHER NO PASSED BLANK !!!";
                return msg;
            }

            if (Actype == null) {
                ut.rollback();
                msg = "ACCOUNT TYPE PASSED BLANK !!!";
                return msg;
            }

            if (AcNO == null) {
                ut.rollback();
                msg = "ACCOUNT NO. PASSED BLANK !!!";
                return msg;
            }
            String currentBrnCode = facadeRemote.getCurrentBrnCode(AcNO);
            String accttype = facadeRemote.getAccountCode(AcNO);
            List dtList = em.createNativeQuery("SELECT date_format(DATE,'%Y-%m-%d') FROM bankdays WHERE DAYENDFLAG = 'N' and brncode = '" + currentBrnCode + "'").getResultList();
            if (!dtList.isEmpty()) {
                Vector dtv = (Vector) dtList.get(0);
                Tempbd = dtv.get(0).toString();
            } else {
                ut.rollback();
                msg = "SERVER DATE NOT FOUND !!!";
                return msg;
            }

            if (Actype.equalsIgnoreCase(CbsAcCodeConstant.OF_AC.getAcctCode())) {
                List chk1 = em.createNativeQuery("Select coalesce(Lien,''),prinAmt,FdDt,MatDt,Roi,IntOpt From td_vouchmst Where Voucherno=" + VchNo + " and OFacno='" + AcNO + "'").getResultList();
                if (!chk1.isEmpty()) {
                    for (int i = 0; i < chk1.size(); i++) {
                        Vector ele = (Vector) chk1.get(i);
                        LienD = ele.get(0).toString();
                        prinAmtD = Float.parseFloat(ele.get(1).toString());
                        FdDtD = ele.get(2).toString();
                        MatDtD = ele.get(3).toString();
                        RoiD = Float.parseFloat(ele.get(4).toString());
                        IntOptD = ele.get(5).toString();
                    }
                    if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("Yes")) {
                        ut.rollback();
                        msg = "Lien Is Already Marked Against- " + AcNO + " / " + VchNo;
                        return msg;
                    } else if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("No")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("No"))) {
                        ut.rollback();
                        msg = "Lien Is Already Removed Against- " + AcNO + " / " + VchNo;
                        return msg;
                    }

                    if (DLAccOpen_Lien.equalsIgnoreCase("True")) {
                        if (LienD.equalsIgnoreCase("Y")) {
                            ut.rollback();
                            msg = "Sorry, You Cannot Remove Lien From Here !!!";
                            return msg;
                        } else {
                            ut.rollback();
                            msg = "";
                            return msg;
                        }
                    } else if (BillLcBg_Lien.equalsIgnoreCase("True")) {
                        if (LienD.equalsIgnoreCase("Y")) {
                            ut.rollback();
                            msg = "Lien Is Already Marked Against- " + AcNO + " / " + VchNo;
                            return msg;
                        }
                    }

                    if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("No")) {
                        if (AUTH.equalsIgnoreCase("True")) {
                            if (Actype.equalsIgnoreCase(CbsAcCodeConstant.OF_AC.getAcctCode())) {
                                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and OFacno='" + AcNO + "'").getResultList();
                                if (!chk2.isEmpty()) {
                                    for (int i = 0; i < chk2.size(); i++) {
                                        Vector ele = (Vector) chk2.get(i);
                                        acctNo = ele.get(0).toString();
                                        accountCode = facadeRemote.getAccountCode(acctNo);
                                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                                    }

                                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + " "
                                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + ")").getResultList();

                                    String lStat = "", mBrn = "";
                                    if (!chLienBrn.isEmpty()) {
                                        Vector ele = (Vector) chLienBrn.get(0);
                                        lStat = ele.get(0).toString();
                                        mBrn = ele.get(1).toString();
                                    }

                                    if (lStat.equalsIgnoreCase("Y")) {
                                        if (mBrn.equalsIgnoreCase(brnCode)) {
                                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                                    + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',now(),SUBSTRING('" + chkLien + "',1,1),'" + accountCode + "','" + brnCode + "','" + remark + "')");
                                            var1 = insertQuery.executeUpdate();
                                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = '' WHERE OFacno='" + AcNO + "' and voucherno=" + VchNo + "");
                                            var2 = updateQuery.executeUpdate();
                                        } else {
                                            ut.rollback();
                                            msg = "Lien Can Be Removed Only From Marked Branch";
                                            return msg;
                                        }
                                    } else {
                                        ut.rollback();
                                        msg = "Lien Status Is Removed In Td_lien_update";
                                        return msg;
                                    }
                                }
                            } else {
                                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                                if (!chk2.isEmpty()) {
                                    for (int i = 0; i < chk2.size(); i++) {
                                        Vector ele = (Vector) chk2.get(i);
                                        acctNo = ele.get(0).toString();
                                        accountCode = facadeRemote.getAccountCode(acctNo);
                                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                                    }

                                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + " "
                                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + ")").getResultList();

                                    String lStat = "", mBrn = "";
                                    if (!chLienBrn.isEmpty()) {
                                        Vector ele = (Vector) chLienBrn.get(0);
                                        lStat = ele.get(0).toString();
                                        mBrn = ele.get(1).toString();
                                    }

                                    if (lStat.equalsIgnoreCase("Y")) {
                                        if (mBrn.equalsIgnoreCase(brnCode)) {
                                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                                    + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',now(),SUBSTRING('" + chkLien + "',1,1),'" + accountCode + "','" + brnCode + "','" + remark + "')");
                                            var1 = insertQuery.executeUpdate();
                                            Query updateQuery = em.createNativeQuery("UPDATE TD_VouchMst SET Lien = '' WHERE acno='" + AcNO + "' and voucherno=" + VchNo + "");
                                            var2 = updateQuery.executeUpdate();
                                        } else {
                                            ut.rollback();
                                            msg = "Lien Can Be Removed Only From Marked Branch";
                                            return msg;
                                        }
                                    } else {
                                        ut.rollback();
                                        msg = "Lien Status Is Removed In Td_lien_update";
                                        return msg;
                                    }
                                }
                            }
                            msg = "Lien Mark Is Removed Against- " + AcNO + " / " + VchNo;
                        } else {
                            ut.rollback();
                            msg = "Lien Mark Can Be Removed By Manager Login !!!";
                            return msg;
                        }
                    } else if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("Yes")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("Yes"))) {
                        List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                        for (int i = 0; i < chk2.size(); i++) {
                            Vector ele = (Vector) chk2.get(i);
                            acctNo = ele.get(0).toString();
                            accountCode = facadeRemote.getAccountCode(acctNo);
                            VoucherNo = Float.parseFloat(ele.get(1).toString());
                            Vreceiptno = Float.parseFloat(ele.get(2).toString());
                        }
                        Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',now(),SUBSTRING('" + chkLien + "',1,1),'" + accountCode + "','" + brnCode + "','" + remark + "')");
                        var1 = insertQuery.executeUpdate();
                        Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien =SUBSTRING('" + chkLien + "',1,1) WHERE acno='" + AcNO + "' and voucherno=" + VchNo + "");
                        var2 = updateQuery.executeUpdate();
                        msg = "Lien Is Marked Against- " + AcNO + " / " + VchNo;
                    }
                    if (chkLien.equalsIgnoreCase("Yes")) {
                        if (Loan_Lien_Call.equalsIgnoreCase("True")) {
                            List chk2 = em.createNativeQuery("Select a.prinAmt,a.FDDt,a.MatDt,a.ROI From td_vouchmst a,td_accountmaster b Where a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + AcNO + "' and a.acno=b.acno").getResultList();
                            if (chk2.isEmpty()) {
                                ut.rollback();
                                msg = "";

                                return msg;
                            } else {
                                List chk3 = em.createNativeQuery("SELECT a.prinAmt,a.FDDt,a.MatDt,a.ROI FROM td_vouchmst a, td_accountmaster b"
                                        + " WHERE a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + AcNO + "' and a.acno=b.acno").getResultList();
                                if (!chk3.isEmpty()) {
                                    for (int i = 0; i < chk3.size(); i++) {
                                        Vector ele = (Vector) chk3.get(i);
                                        prinAmt = Float.parseFloat(ele.get(0).toString());
                                        FDDt = ele.get(1).toString();
                                        MatDt = ele.get(2).toString();
                                        ROI = Float.parseFloat(ele.get(3).toString());
                                    }
                                }
                                List chk4 = em.createNativeQuery("SELECT ifnull(Description,'') FROM codebook WHERE GroupCode=51 and code=61").getResultList();
                                if (!chk4.isEmpty()) {
                                    Vector ele = (Vector) chk4.get(0);
                                    TDCodeDesc = ele.get(0).toString();
                                }
                                List chk5 = em.createNativeQuery("SELECT ifnull(max(Sno),0) From loansecurity Where Acno= '" + LAcNO + "'").getResultList();
                                if (!chk5.isEmpty()) {
                                    Vector ele = (Vector) chk5.get(0);
                                    AutoSno = Integer.parseInt(ele.get(0).toString());
                                    AutoSno = AutoSno + 1;
                                }
                                TmpMsg = "DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY): ";
                                Query insertQuery = em.createNativeQuery("INSERT INTO loansecurity (acno,sno,security,particulars,matdate,lienvalue,matValue,issuedate,"
                                        + " status,Remarks,enteredby,entrydate,SecurityOption,SecurityChg,lienacno, SecurityRoi,MargineROI,AppRoi,IntTableCode,Margin, AddRoi,voucherNo)"
                                        + " VALUES('" + LAcNO + "'," + AutoSno + ",'" + tmpSecType + "','" + accttype + "','" + MatDt + "'," + prinAmt + "," + prinAmt + ",'" + FDDt + "',"
                                        + " 'Active','" + TmpMsg + "','" + enteredby + "','" + Tempbd + "','" + TDCodeDesc + "','Lien','" + AcNO + "'," + Double.valueOf(ROI) + "," 
                                        + Double.parseDouble(roiOnSecurity) + "," + Double.valueOf(ROI + Float.parseFloat(roiOnSecurity) + Float.parseFloat(additionalRoi)) + ",'" 
                                        + secODScheme + "','" + margin + "','" + additionalRoi + "','" + VchNo + "')");
                                var3 = insertQuery.executeUpdate();

                            }
                        }
                    }
                }
            } else {
                List chk1 = em.createNativeQuery("Select coalesce(Lien,''),prinAmt,FdDt,MatDt,Roi,IntOpt, ifnull(IntToAcno,'')"
                        + " From td_vouchmst Where Voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                //System.out.println("chk1:======="+chk1);
                if (!chk1.isEmpty()) {
                    for (int i = 0; i < chk1.size(); i++) {
                        Vector ele = (Vector) chk1.get(i);
                        LienD = ele.get(0).toString();
                        prinAmtD = Float.parseFloat(ele.get(1).toString());
                        FdDtD = ele.get(2).toString();
                        MatDtD = ele.get(3).toString();
                        RoiD = Float.parseFloat(ele.get(4).toString());
                        IntOptD = ele.get(5).toString();
                        intToAcNo = ele.get(6).toString();
                    }
                    if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("Yes")) {
                        ut.rollback();
                        msg = "Lien Is Already Marked Against- " + AcNO + " / " + VchNo;
                        return msg;
                    } else if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("No")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("No"))) {
                        ut.rollback();
                        msg = "Lien Is Already Removed Against- " + AcNO + " / " + VchNo;
                        return msg;
                    }
                    //System.out.println("DLAccOpen_Lien:======="+DLAccOpen_Lien);
//                    if (DLAccOpen_Lien.equalsIgnoreCase("True")) {
//                        System.out.println("LienD:======="+LienD);
//                        if (LienD.equalsIgnoreCase("Y")) {
//                            ut.rollback();
//                            msg = "Sorry, You Cannot Remove Lien From Here !!!";
//                            return msg;
//                        } else {
//                            ut.rollback();
//                            msg = "";
//                            return msg;
//                        }
//                    } else if (BillLcBg_Lien.equalsIgnoreCase("True")) {
//                        if (LienD.equalsIgnoreCase("Y")) {
//                            ut.rollback();
//                            msg = "Lien Is Already Marked Against- " + AcNO + " / " + VchNo;
//                            return msg;
//                        }
//                    }
                    if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("No")) {
                        if (AUTH.equalsIgnoreCase("True")) {
                            if (Actype.equalsIgnoreCase(CbsAcCodeConstant.OF_AC.getAcctCode())) {
                                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and OFacno='" + AcNO + "'").getResultList();
                                if (!chk2.isEmpty()) {
                                    for (int i = 0; i < chk2.size(); i++) {
                                        Vector ele = (Vector) chk2.get(i);
                                        acctNo = ele.get(0).toString();
                                        type = facadeRemote.getAccountCode(acctNo);
                                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                                    }

                                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + " "
                                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + ")").getResultList();

                                    String lStat = "", mBrn = "";
                                    if (!chLienBrn.isEmpty()) {
                                        Vector ele = (Vector) chLienBrn.get(0);
                                        lStat = ele.get(0).toString();
                                        mBrn = ele.get(1).toString();
                                    }

                                    if (lStat.equalsIgnoreCase("Y")) {
                                        if (mBrn.equalsIgnoreCase(brnCode)) {
                                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                                    + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',now(),SUBSTRING('" + chkLien + "',1,1),'" + type + "','" + brnCode + "','" + remark + "')");
                                            var1 = insertQuery.executeUpdate();
                                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = '' WHERE OFacno='" + AcNO + "' and voucherno=" + VchNo + "");
                                            var2 = updateQuery.executeUpdate();
                                        } else {
                                            ut.rollback();
                                            msg = "Lien Can Be Removed Only From Marked Branch";
                                            return msg;
                                        }
                                    } else {
                                        ut.rollback();
                                        msg = "Lien Status Is Removed In Td_lien_update";
                                        return msg;
                                    }
                                }
                            } else {
                                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                                if (!chk2.isEmpty()) {
                                    for (int i = 0; i < chk2.size(); i++) {
                                        Vector ele = (Vector) chk2.get(i);
                                        acctNo = ele.get(0).toString();
                                        type = facadeRemote.getAccountCode(acctNo);
                                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                                    }

                                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + " "
                                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + ")").getResultList();

                                    String lStat = "", mBrn = "";
                                    if (!chLienBrn.isEmpty()) {
                                        Vector ele = (Vector) chLienBrn.get(0);
                                        lStat = ele.get(0).toString();
                                        mBrn = ele.get(1).toString();
                                    }

                                    if (lStat.equalsIgnoreCase("Y")) {
                                        if (mBrn.equalsIgnoreCase(brnCode)) {
                                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                                    + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',now(),SUBSTRING('" + chkLien + "',1,1),'" + type + "','" + brnCode + "','" + remark + "')");
                                            var1 = insertQuery.executeUpdate();
                                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = '' WHERE acno='" + AcNO + "' and voucherno=" + VchNo + "");
                                            var2 = updateQuery.executeUpdate();
                                        } else {
                                            ut.rollback();
                                            msg = "Lien Can Be Removed Only From Marked Branch";
                                            return msg;
                                        }
                                    } else {
                                        ut.rollback();
                                        msg = "Lien Status Is Removed In Td_lien_update";
                                        return msg;
                                    }
                                }
                            }
                            msg = "Lien Mark Is Removed Against- " + AcNO + " / " + VchNo;
                        } else {
                            ut.rollback();
                            msg = "Lien Mark Can Be Removed By Manager Login !!!";
                            return msg;
                        }
                    } else if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("Yes")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("Yes"))) {
                        List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                        for (int i = 0; i < chk2.size(); i++) {
                            Vector ele = (Vector) chk2.get(i);
                            acctNo = ele.get(0).toString();
                            type = facadeRemote.getAccountCode(acctNo);
                            VoucherNo = Float.parseFloat(ele.get(1).toString());
                            Vreceiptno = Float.parseFloat(ele.get(2).toString());
                        }
                        Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',now(),SUBSTRING('" + chkLien + "',1,1),'" + type + "','" + brnCode + "','" + remark + "')");
                        var1 = insertQuery.executeUpdate();
                        Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien =SUBSTRING('" + chkLien + "',1,1) WHERE acno='" + AcNO + "' and voucherno=" + VchNo + "");
                        var2 = updateQuery.executeUpdate();
                        msg = "Lien Is Marked Against- " + AcNO + " / " + VchNo;
                    }
                    //    System.out.println("Loan_Lien_Call:======="+Loan_Lien_Call);
                    if (chkLien.equalsIgnoreCase("Yes")) {
                        if (Loan_Lien_Call.equalsIgnoreCase("True")) {
                            List chk2 = em.createNativeQuery("Select a.prinAmt,a.FDDt,a.MatDt,a.ROI From td_vouchmst a,td_accountmaster b Where a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + AcNO + "' and a.acno=b.acno").getResultList();

                            if (chk2.isEmpty()) {
                                ut.rollback();
                                msg = "";
                                return msg;
                            } else {
                                List chk3 = em.createNativeQuery("SELECT a.prinAmt,a.FDDt,a.MatDt,a.ROI FROM td_vouchmst a,td_accountmaster b"
                                        + " WHERE a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + AcNO + "' and a.acno=b.acno").getResultList();
                                if (!chk3.isEmpty()) {
                                    for (int i = 0; i < chk3.size(); i++) {
                                        Vector ele = (Vector) chk3.get(i);
                                        prinAmt = Float.parseFloat(ele.get(0).toString());
                                        List flagList = em.createNativeQuery("select ifnull(ADHOC_PASS_SHEET_PRINT_EVENT,'N'), ifnull(COMMITMENT_EVENT,0) from cbs_scheme_currency_details where SCHEME_CODE = '" + secODScheme + "'").getResultList();
                                        if (!flagList.isEmpty()) {
                                            Vector flagVect = (Vector) flagList.get(0);
                                            if (margin.equalsIgnoreCase("")) {
                                                margin = flagVect.get(0).toString();
                                            }
                                            if (flagVect.get(0).toString().equalsIgnoreCase("Y")) {
                                                if (intToAcNo.equalsIgnoreCase("")) {
                                                    String result = tdLienPresentAmount(AcNO, VchNo, prinAmt);
                                                    if (result == null) {
                                                        return "PROBLEM IN GETTING PRESENT AMOUNT !!!";
                                                    } else {
                                                        int n = result.indexOf("*");
                                                        prinAmt = Float.parseFloat(result.substring(n + 1));
                                                    }
                                                }
                                            }
                                        }
                                        prinAmtD = prinAmt - ((prinAmt * Float.parseFloat(margin)) / 100);
                                        FDDt = ele.get(1).toString();
                                        MatDt = ele.get(2).toString();
                                        ROI = Float.parseFloat(ele.get(3).toString());
                                    }
                                }
                                List chk4 = em.createNativeQuery("SELECT ifnull(Description,'') FROM codebook WHERE GroupCode=51 and code=61").getResultList();
                                if (!chk4.isEmpty()) {
                                    Vector ele = (Vector) chk4.get(0);
                                    TDCodeDesc = ele.get(0).toString();
                                }
                                List chk5 = em.createNativeQuery("SELECT ifnull(max(Sno),0) From loansecurity Where Acno= '" + LAcNO + "'").getResultList();
                                if (!chk5.isEmpty()) {
                                    Vector ele = (Vector) chk5.get(0);
                                    AutoSno = Integer.parseInt(ele.get(0).toString());
                                    AutoSno = AutoSno + 1;
                                }
                                TmpMsg = "DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY): ";
                                Query insertQuery = em.createNativeQuery("INSERT INTO loansecurity (acno,sno,security,particulars,matdate,lienvalue,matValue,issuedate,"
                                        + " status,Remarks,enteredby,entrydate,SecurityOption,SecurityChg,lienacno, SecurityRoi,MargineROI,AppRoi,IntTableCode,Margin, AddRoi,voucherNo)"
                                        + " VALUES('" + LAcNO + "'," + AutoSno + ",'" + tmpSecType + "','" + accttype + "','" + MatDt + "'," + prinAmtD + "," + prinAmt + ",'" + FDDt + "',"
                                        + " 'Active',CONCAT('DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):', '" + AcNO + "','; VchNo:" + VchNo + "', '; ROI:' "
                                        + ", cast(" + ROI + " as char(10)) , '; Present Amt:' , cast(" + prinAmt + " as char(20))), '" + enteredby + "','" + Tempbd + "','" + TDCodeDesc 
                                        + "','Lien','" + AcNO + "'," + Double.valueOf(ROI) + "," + Double.parseDouble(roiOnSecurity) + "," + Double.valueOf(ROI + Float.parseFloat(roiOnSecurity) 
                                                + Float.parseFloat(additionalRoi)) + ",'" + secODScheme + "','" + margin + "','" + additionalRoi + "','"+ VchNo +"')");
                                var3 = insertQuery.executeUpdate();
                                // System.out.println("var3 :======="+var3);
                            }
                        }
                    }
                }
            }
            if (var1 > 0 && var2 > 0) {
                ut.commit();
                return msg;
            } else {
                ut.rollback();
                return "RECORD NOT SAVED !!!";
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
        //return msg;
    }

    public String saveLoanRepaymentDetails(RepaymentShedulePojo pojo) throws ApplicationException {
        String msg = "";
        String dueDt;
        int period = Integer.parseInt(pojo.getPeriod());
        int incrementedMonth = 0;
        if (pojo.getSelectPeriodicity().equalsIgnoreCase("M")) {
            incrementedMonth = 1;
        } else if (pojo.getSelectPeriodicity().equalsIgnoreCase("Q")) {
            incrementedMonth = 3;
        } else {
            incrementedMonth = 6;
        }
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            dueDt = ymd.format(pojo.getEmiDate());
            UserTransaction ut = context.getUserTransaction();
            int updatedRecords = 0;
            ut.begin();
            List resultList = em.createNativeQuery("select max(sno) from emidetails where acno like '" + pojo.getAcno() + "'").getResultList();
            int srno = 0;
            if (resultList.get(0) != null) {
                Vector vec = (Vector) resultList.get(0);
                if (vec.get(0) != null) {
                    srno = Integer.parseInt(vec.get(0).toString()) + 1;
                } else {
                    srno = 1;
                }
            } else {
                srno = 1;
            }

            for (int sno = srno; sno < (period + srno); sno++) {
                Query insertQuery = em.createNativeQuery("insert into emidetails (Acno,sno,DUEDT,prinamt,interestamt,installamt,periodicity,Status)"
                        + "values (" + "'" + pojo.getAcno() + "'" + "," + "'" + sno + "'" + "," + "date_format('" + dueDt + "','%Y-%m-%d')" + "," + 0.0 + "," + 0.0 + "," + pojo.getAmount() + "," + "'" + pojo.getSelectPeriodicity() + "'" + "," + "'Unpaid'" + ")");
                updatedRecords = insertQuery.executeUpdate();
                dueDt = CbsUtil.monthAdd(dueDt, incrementedMonth);
            }
            if (updatedRecords > 0) {
                ut.commit();
                msg = "Installments posted successfully.";
            } else {
                ut.rollback();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public boolean checkAcno(String acno, String brncode) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select acno from accountmaster where acno like '" + acno + "' and curbrcode='" + brncode + "'").getResultList();
            if (!resultList.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<RepaymentShedulePojo> getSheduleList(String acno) throws ApplicationException {
        List<RepaymentShedulePojo> list = new ArrayList<RepaymentShedulePojo>();
        {
            try {
                List resultList = em.createNativeQuery("select acno,sno,duedt,installamt,status,periodicity from emidetails where acno like '" + acno + "' and status like 'Unpaid'").getResultList();
                if (!resultList.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    for (int i = 0; i < resultList.size(); i++) {
                        RepaymentShedulePojo pojo = new RepaymentShedulePojo();
                        Vector vec = (Vector) resultList.get(i);
                        pojo.setAcno(vec.get(0).toString());
                        pojo.setSno(Integer.parseInt(vec.get(1).toString()));
                        pojo.setDueDate(sdf.format((Date) vec.get(2)));
                        pojo.setAmount(Double.parseDouble(vec.get(3).toString()));
                        pojo.setStatus(vec.get(4).toString());
                        pojo.setSelectPeriodicity(vec.get(5).toString().equalsIgnoreCase("M") ? "Monthly" : vec.get(5).toString().equalsIgnoreCase("Q") ? "Quaterly" : "Half Yearly");
                        list.add(pojo);
                    }
                }
            } catch (Exception e) {
                throw new ApplicationException(e.getLocalizedMessage());
            }
        }
        return list;
    }
}
