/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.Months;
import com.cbs.dto.DateByComparator;
import com.cbs.dto.RdIntDetail;
import com.cbs.dto.RdInterestDTO;
import com.cbs.dto.TdIntDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.AutoTermDepositRenewalRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.pojo.SavingIntRateChangePojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author Sudhir
 */
@Stateless(mappedName = "RDIntCalFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class RDIntCalFacade implements RDIntCalFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    RbiReportFacadeRemote rbiReportFacade;
    @EJB
    AutoTermDepositRenewalRemote autoTermDepositFacade;
    @EJB
    SbIntCalcFacadeRemote sbRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /**
     *
     * @return @throws ApplicationException
     */
    public List getAccountTypes() throws ApplicationException {
        List accountResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select Acctcode From accounttypemaster " + "where acctNature = '" + CbsConstant.RECURRING_AC + "'");
            accountResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accountResult;
    }

    /**
     *
     * @param brCode
     * @return
     * @throws ApplicationException
     */
    public String getCurrentDate(String brCode) throws ApplicationException {
        try {
            List currentDt = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
            if (currentDt.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            } else {
                Vector datePart = (Vector) currentDt.get(0);
                return datePart.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     *
     * @param acType
     * @return
     * @throws ApplicationException
     */
    public List getInterestGLHeads(String acType) throws ApplicationException {
        try {
            List paramInfo = em.createNativeQuery("select rd_simplepostflag from td_parameterinfo").getResultList();
            Vector ele = (Vector) paramInfo.get(0);
            if (ele.get(0).toString().equals("1")) {
                throw new ApplicationException("As Per List");
            }
            /* zero for account to be debited 1 for account to be credited */
            List glHead = em.createNativeQuery("select GlHeadInt, GLHeadprov from accounttypemaster where acctcode ='" + acType + "'").getResultList();
            return glHead;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     *
     * @param toDt
     * @param brCode
     * @return
     * @throws ApplicationException
     */
    public String checkMonthEndForRdProvision(String fromDt, String toDt, String brCode, String intOpt) throws ApplicationException {
        String result = "";
        try {
            List checkList = new ArrayList();
            if (intOpt.equalsIgnoreCase("M")) {
                checkList = em.createNativeQuery("select MONTH(date) from bankdays where mendflag='N' and date <= '" + toDt + "' and "
                        + "Brncode='" + brCode + "'").getResultList();
            } else if (intOpt.equalsIgnoreCase("Q") || intOpt.equalsIgnoreCase("H")) {
                checkList = em.createNativeQuery("select MONTH(date) from bankdays where mendflag='N' and brnCode='" + brCode
                        + "' and date between '" + fromDt + "' and '" + toDt + "'").getResultList();
            }

            if (checkList.isEmpty()) {
                result = "true";
            } else {
                Vector vect = (Vector) checkList.get(0);
                return "Month end process were not executed for :" + Months.getMonthName(CbsUtil.lPadding(2, Integer.parseInt(vect.elementAt(0).toString())));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    /**
     *
     * @param tmpPreDate
     * @param quarEndDt
     * @param brCode
     * @return
     * @throws ApplicationException
     */
    public String checkMonthEnd(String tmpPreDate, String quarEndDt, String brCode) throws ApplicationException {
        try {
            List monthEnd = em.createNativeQuery("select MONTH(date) from bankdays where mendflag='N' and brnCode='" + brCode
                    + "' and date between '" + tmpPreDate + "' and '" + quarEndDt + "'").getResultList();
            String result = "Month end process were not executed for :";
            if (monthEnd.isEmpty()) {
                return "TRUE";
            } else {
                Vector vect = (Vector) monthEnd.get(0);
                return result + Months.getMonthName(CbsUtil.lPadding(2, Integer.parseInt(vect.elementAt(0).toString())));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @param acType
     * @param fromDate
     * @param toDate
     * @param brCode
     * @return
     * @throws ApplicationException
     */
    public List<RdInterestDTO> interestCalculation(String acType, String fromDate, String toDate, String brCode, String intOption) throws ApplicationException {
        try {
            String msg = checkMonthEndForRdProvision(fromDate, toDate, brCode, intOption);
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            return interestCalculate(acType, fromDate, toDate, brCode);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @param brCode
     * @param acType
     * @param tmpPreDate
     * @param quarEndDate
     * @return
     * @throws ApplicationException
     */
    public List<RdInterestDTO> quarterlyRdIntCal(String brCode, String acType, String tmpPreDate, String quarEndDate, String intOpt) throws ApplicationException {

        try {
            String tdsPostEnable = "";
            List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
            if (!tdsPostEnableList.isEmpty()) {
                Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                tdsPostEnable = tdsPostEnableVect.get(0).toString();
                if (tdsPostEnable.equalsIgnoreCase("Y")) {
                    String acNature = fts.getAcNatureByCode(acType);
                    List tdsPostList = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'TDS POSTED' and fromdt = '" + tmpPreDate + "' and todt = '" + quarEndDate + "' and brncode = '" + brCode + "' and actype in ('" + acNature + "')").getResultList();
                    if (tdsPostList.isEmpty()) {
                        throw new ApplicationException("Please post/reserve the TDS first.");
                    }
                }
            }
            String msg = checkMonthEnd(tmpPreDate, quarEndDate, brCode);
            if (!msg.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException(msg);
            }
            return interestCalculate(acType, tmpPreDate, quarEndDate, brCode);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     *
     * @param acType
     * @param fromDate
     * @param toDate
     * @param user
     * @param brcode
     * @return
     * @throws ApplicationException
     */
    public String interestPosting(String acType, String fromDate, String toDate, String user, String brcode, String intOption) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List businessDtList = em.createNativeQuery("select date from bankdays where dayendflag='N' and brncode='" + brcode + "'").getResultList();
            if (businessDtList.isEmpty()) {
                throw new ApplicationException("Data was not found in bankdays for: " + brcode);
            }
            Vector tempCurrent = (Vector) businessDtList.get(0);
            String currentDate = tempCurrent.get(0).toString();
            int month = CbsUtil.datePart("M", toDate);

            List mendFlag = em.createNativeQuery("select mflag from parameterinfo_int where actype ='" + acType + "' and Tmonth ='" + month
                    + "' and brncode='" + brcode + "'").getResultList();
            if (!mendFlag.isEmpty()) {
                Vector mendFlags = (Vector) mendFlag.get(0);
                if (mendFlags.get(0).toString().equalsIgnoreCase("Y")) {
                    if (intOption.equalsIgnoreCase("M")) {
                        throw new ApplicationException("Interest has been already posted for this month.");
                    } else if (intOption.equalsIgnoreCase("Q")) {
                        throw new ApplicationException("Interest has been already posted for this quarter.");
                    } else if (intOption.equalsIgnoreCase("H")) {
                        throw new ApplicationException("Interest has been already posted for this half year.");
                    }
                }
            } else {
                throw new ApplicationException("Data was not found in Parameterinfo Int table.");
            }

            String result = rdInterestPosting(acType, user, fromDate, toDate, brcode, intOption);
            if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(result);
            } else {
                Query updateQuery = em.createNativeQuery("update parameterinfo_int set mflag='Y',dt='" + currentDate + "' where actype='" + acType
                        + "' and mflag='N' and tmonth='" + month + "' and brncode='" + brcode + "'");
                int var1 = updateQuery.executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Problem in data updation in ParameterInfo Int table.");
                }
                String nextMonth = CbsUtil.monthAdd(toDate, 1);
                int nextDtMonth = Integer.parseInt(nextMonth.substring(4, 6));
                Query updateQuery1 = em.createNativeQuery("Update parameterinfo_int Set mFlag='N' Where acType='" + acType + "' and fmonth = "
                        + nextDtMonth + " and brnCode='" + brcode + "'");
                var1 = updateQuery1.executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Problem in data updation in parameterinfo_int");
                } else {
                    ut.commit();
                    return "true" + result.substring(4);
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String postInterest(String userName, String actype, String quarEndDt, String intOpt, String tmpPreDt1, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List date = em.createNativeQuery("select DATE from bankdays where dayendflag='N' and brnCode='" + brCode + "'").getResultList();
            Vector recLst = (Vector) date.get(0);
            String currentDt = recLst.get(0).toString();

            List paramInfo = em.createNativeQuery("select ifnull(mflag,'') from parameterinfo_int where " + " actype = '" + actype
                    + "' and Tmonth = " + quarEndDt.substring(4, 6) + " and brnCode='" + brCode + "'").getResultList();
            if (paramInfo.size() > 0) {
                Vector recLst1 = (Vector) paramInfo.get(0);
                if (recLst1.get(0).toString().equalsIgnoreCase("Y")) {
                    if (!intOpt.equalsIgnoreCase("Q")) {
                        throw new ApplicationException("Interest Already Posted for this Half Year End.");
                    } else {
                        throw new ApplicationException("Interest Already Posted for this Quarter.");
                    }
                }
            }

            String result = rdInterestPosting(actype, userName, tmpPreDt1, quarEndDt, brCode, intOpt);
            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                ut.rollback();
                throw new ApplicationException(result);
            }
            String nextMonth = CbsUtil.monthAdd(quarEndDt, 1);
            int nextDtMonth = Integer.parseInt(nextMonth.substring(4, 6));
            Query updateQuery = em.createNativeQuery("Update parameterinfo_int Set mFlag='Y',Dt='" + currentDt + "' where actype='" + actype
                    + "' and Mflag='N' and TMonth=" + quarEndDt.substring(4, 6) + " and brnCode='" + brCode + "'");
            int var1 = updateQuery.executeUpdate();
            if (var1 <= 0) {
                //ut.rollback();
                throw new ApplicationException("Problem in data updation in parameterinfo_int");
            }
            Query updateQuery1 = em.createNativeQuery("Update parameterinfo_int Set mFlag='N' Where acType='" + actype + "' and fmonth = "
                    + nextDtMonth + " and brnCode='" + brCode + "'");
            var1 = updateQuery1.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data updation in parameterinfo_int");
            } else {
                ut.commit();
                return "Interest has been successfully posted.";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String rdInterestPosting(String acType, String AuthBy, String fromdt, String todt, String brcode, String intOption) throws ApplicationException {
        try {
            float trsNo = 0.0f;
            float recNo = 0.0f;
            int trantype;
            double totalTds = 0d;

            List chkList1 = em.createNativeQuery("select rd_simplepostflag from td_parameterinfo").getResultList();
            if (chkList1.isEmpty()) {
                throw new ApplicationException("Please Check for SimplePostFlag In td_parameterinfo.");
            }
            Vector Lst1 = (Vector) chkList1.get(0);
            int intPostFlag = Integer.parseInt(Lst1.get(0).toString());

            List chkList2 = em.createNativeQuery("select GlHeadInt,GlHeadProv from accounttypemaster where acctcode='" + acType + "'").getResultList();
            if (chkList2.isEmpty()) {
                throw new ApplicationException("Please Check for GlHeadInt or GlHeadProv In accounttypemaster for RD.");
            }

            String tdsPostEnable = "";
            List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
            if (!tdsPostEnableList.isEmpty()) {
                Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                tdsPostEnable = tdsPostEnableVect.get(0).toString();
                if (tdsPostEnable.equalsIgnoreCase("Y")) {
                    String acNature = fts.getAcNatureByCode(acType);
                    List tdsPostList = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'TDS POSTED' and todt = '" + todt + "' and brncode = '" + brcode + "' and actype in ('" + acNature + "')").getResultList();
                    if (tdsPostList.isEmpty()) {
                        throw new ApplicationException("Please post/reserve the TDS first.");
                    }
                }
            }

            Vector list2 = (Vector) chkList2.get(0);
            String glHeadInt = list2.get(0).toString();
            String glHeadProv = list2.get(1).toString();

            if ((glHeadInt == null) || (glHeadProv == null) || (glHeadInt.equalsIgnoreCase("")) || (glHeadProv.equalsIgnoreCase(""))) {
                throw new ApplicationException("Please Check for GlHeadInt or GlHeadProv In accounttypemaster for RD");
            }
            glHeadInt = brcode + glHeadInt + "01";
            glHeadProv = brcode + glHeadProv + "01";

            trsNo = fts.getTrsNo();
            List<RdInterestDTO> rdIntDetails = interestCalculate(acType, fromdt, todt, brcode);
            double totalInt = 0d;
            for (RdInterestDTO rdInterestDTO : rdIntDetails) {
                totalInt = totalInt + rdInterestDTO.getInterest();
                Query InsertQuery = em.createNativeQuery("insert into rd_interesthistory(acno,Interest,Todt,dt,Enterby) values('"
                        + rdInterestDTO.getAcNo() + "'," + rdInterestDTO.getInterest() + ",'" + todt + "',(DATE_FORMAT(CURDATE(),'%Y%m%d')),'" + AuthBy + "')");
                int var = InsertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in data insertion into Rd_InterestHistory Table");
                }
            }
            String details = "Interest from " + fromdt + " to " + todt;
            if ((intPostFlag == 0) || (intPostFlag == 1) || (intPostFlag == 2)) {
                if ((intPostFlag == 0) || (intPostFlag == 2)) {
                    trantype = 2;
                } else {
                    trantype = 8;
                }
                recNo = fts.getRecNo();
                Query insertQuery2 = em.createNativeQuery("insert into gl_recon(acno,dt,ValueDt,Dramt,ty,trantype,PayBy,iy,details,TranDesc,"
                        + "auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                        + "values ('" + glHeadInt + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d')," + totalInt + ",1,"
                        + trantype + ",3,1,'" + details + "',3,'Y','" + AuthBy + "','" + AuthBy + "'," + recNo + "," + trsNo + ",'" + brcode + "','"
                        + brcode + "')");
                int var2 = insertQuery2.executeUpdate();
                if (var2 <= 0) {
                    throw new ApplicationException("Problem in data insertion in Gl_Recon for AcNo:- '" + glHeadInt + "'");
                }

                List chkList4 = em.createNativeQuery("Select Acno from reconbalan where acno='" + glHeadInt + "' and substring(acno,1,2) = '" + brcode + "'").getResultList();
                if (chkList4.size() > 0) {
                    Query updateQuery4 = em.createNativeQuery("update reconbalan Set balance=ifnull(balance,0)-" + totalInt
                            + ",dt=DATE_FORMAT(CURDATE(),'%Y%m%d') where acno='" + glHeadInt + "' and substring(acno,1,2) = '" + brcode + "'");
                    int var3 = updateQuery4.executeUpdate();
                    if (var3 <= 0) {
                        throw new ApplicationException("Problem in data updation Recon Balan in for AcNo:- '" + glHeadInt + "'");
                    }
                } else {
                    Query insertQuery3 = em.createNativeQuery("Insert reconbalan (Acno,Dt,balance) values ('" + glHeadInt
                            + "',DATE_FORMAT(CURDATE(),'%Y%m%d')" + "," + (-totalInt) + ")");
                    int var4 = insertQuery3.executeUpdate();
                    if (var4 <= 0) {
                        throw new ApplicationException("Problem in data insertion Recon Balan in for AcNo:- '" + glHeadInt + "'");
                    }
                }
            }

            if ((intPostFlag == 0) || (intPostFlag == 2)) {
                recNo = fts.getRecNo();
                Query insertQuery2 = em.createNativeQuery("insert into gl_recon(acno,dt,ValueDt,Cramt,ty,trantype,PayBy,iy,details,TranDesc,auth,authby,"
                        + "enterby,recno,trsno,org_brnid,dest_brnid) values ('" + glHeadProv + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                        + "DATE_FORMAT(CURDATE(),'%Y%m%d')," + totalInt + ",0,2,3,1,'" + details + "',3,'Y','" + AuthBy + "','" + AuthBy + "'," + recNo
                        + "," + trsNo + ",'" + brcode + "','" + brcode + "')");
                int var2 = insertQuery2.executeUpdate();
                if (var2 <= 0) {
                    throw new ApplicationException("Problem In Insertion In gl_recon for AcNo:- '" + glHeadProv + "'");
                }
                List chkList4 = em.createNativeQuery("Select Acno from reconbalan where acno='" + glHeadProv + "' and substring(acno,1,2) = '" + brcode + "'").getResultList();
                if (chkList4.size() > 0) {
                    Query updateQuery4 = em.createNativeQuery("update reconbalan Set balance=ifnull(balance,0)+" + totalInt + ",dt=DATE_FORMAT(CURDATE(),'%Y%m%d') where acno='" + glHeadProv + "' and substring(acno,1,2) = '" + brcode + "'");
                    int var3 = updateQuery4.executeUpdate();
                    if (var3 <= 0) {
                        throw new ApplicationException("Problem in data updation Recon Balan in for AcNo:- '" + glHeadInt + "'");
                    }
                } else {
                    Query insertQuery3 = em.createNativeQuery("Insert reconbalan (Acno,Dt,balance)"
                            + "values (" + "'" + glHeadProv + "'" + "," + "DATE_FORMAT(CURDATE(),'%Y%m%d')" + "," + totalInt + ")");
                    int var4 = insertQuery3.executeUpdate();
                    if (var4 <= 0) {
                        throw new ApplicationException("Problem in data insertion Recon Balan in for AcNo:- '" + glHeadInt + "'");
                    }
                }
            }

            if ((intPostFlag == 1) || (intPostFlag == 2)) {
                for (RdInterestDTO rdInterestDTO : rdIntDetails) {
                    recNo = fts.getRecNo();
                    Query insertQuery2 = em.createNativeQuery("insert into rdrecon(acno,dt,ValueDt,cramt,ty,trantype,PayBy,iy,details,TranDesc,auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                            + "values ('" + rdInterestDTO.getAcNo() + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                            + rdInterestDTO.getInterest() + ",0,8,3,1,'" + details + "',3,'Y','" + AuthBy + "','" + AuthBy + "'," + recNo + "," + trsNo
                            + ",'" + brcode + "','" + brcode + "')");
                    int var = insertQuery2.executeUpdate();
                    if (var <= 0) {
                        throw new ApplicationException("Problem In Insertion In rdrecon of Cr Type for Ac No:- '" + rdInterestDTO.getAcNo() + "'");
                    }
                    /**
                     * ************ TDS Deduction from account **************
                     */
                    double tdsAmt = 0;
                    if (tdsPostEnable.equalsIgnoreCase("Y")) {
                        List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '" + rdInterestDTO.getAcNo() + "' and VoucherNo = '" + 0 + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = 'Q'").getResultList();
                        if (acWiseTdsList.size() > 0) {
                            Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
                            tdsAmt = Double.parseDouble(acWiseTdsVect.get(0).toString());
                            totalTds = totalTds + Double.parseDouble(acWiseTdsVect.get(0).toString());
                            if (tdsAmt != 0) {
                                recNo = fts.getRecNo();

                                int tdRecon = em.createNativeQuery("insert into rdrecon(acno,dt,ValueDt,dramt,ty,trantype,PayBy,iy,details,TranDesc,auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                                        + "values ('" + rdInterestDTO.getAcNo() + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                                        + tdsAmt + ",1,2,3,1,'Tds Decucted for " + fromdt + "To " + todt + "',33,'Y','SYSTEM','" + AuthBy + "'," + recNo + "," + trsNo
                                        + ",'" + brcode + "','" + brcode + "')").executeUpdate();

                                if (tdRecon <= 0) {
                                    return "Error in tdRecon Insertion for TDS";
                                }

                                Query updateQuery = em.createNativeQuery("Update tds_reserve_history Set recovered='R', trsno = " + trsNo + ", recno = " + recNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                        + " where acno = '" + rdInterestDTO.getAcNo() + "' and VoucherNo = '0' and recovered ='NR' and todt<='" + todt + "' and intOpt = '" + intOption + "'");
                                int result = updateQuery.executeUpdate();
                                if (result < 0) {
                                    return "Error in updating tdshistory for tds ";
                                }
                                Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                        + "VALUES('" + rdInterestDTO.getAcNo() + "'," + 0 + "," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + todt + "','"
                                        + fromdt + "','" + intOption + "')").executeUpdate();
                                if (TDSHistory <= 0) {
                                    throw new ApplicationException("Data Not Saved For " + rdInterestDTO.getAcNo());
                                }
                            }
                        }
                    }
                    /**
                     * ***END of TDS Deduction from account **************
                     */
                    if (intPostFlag == 1) {
                        List chkList4 = em.createNativeQuery("Select Acno from reconbalan where acno='" + rdInterestDTO.getAcNo() + "' and substring(acno,1,2) = '" + brcode + "'").getResultList();
                        if (chkList4.size() > 0) {
                            Query updateQuery4 = em.createNativeQuery("update reconbalan Set balance=ifnull(balance,0)+" + rdInterestDTO.getInterest() + "-" + tdsAmt
                                    + ",dt=DATE_FORMAT(CURDATE(),'%Y%m%d') where acno='" + rdInterestDTO.getAcNo() + "' and substring(acno,1,2) = '" + brcode + "'");
                            int var3 = updateQuery4.executeUpdate();
                            if (var3 <= 0) {
                                throw new ApplicationException("Problem in data updation Recon Balan in for AcNo:- '" + glHeadInt + "'");
                            }
                        } else {
                            Query insertQuery3 = em.createNativeQuery("Insert reconbalan (Acno,Dt,balance)"
                                    + "values (" + "'" + rdInterestDTO.getAcNo() + "'" + "," + "DATE_FORMAT(CURDATE(),'%Y%m%d')" + "," + rdInterestDTO.getInterest() + "-" + tdsAmt + ")");
                            int var4 = insertQuery3.executeUpdate();
                            if (var4 <= 0) {
                                throw new ApplicationException("Problem in data insertion Recon Balan in for AcNo:- '" + glHeadInt + "'");
                            }
                        }
                    } else {
                        recNo = fts.getRecNo();
                        Query insertQuery20 = em.createNativeQuery("insert into rdrecon(acno,dt,ValueDt,Dramt,ty,trantype,PayBy,iy,details,TranDesc,"
                                + "auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                                + "values ('" + rdInterestDTO.getAcNo() + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d')," + rdInterestDTO.getInterest() + "-" + tdsAmt + ",1,8,3,1,'"
                                + details + "',3,'Y','" + AuthBy + "','" + AuthBy + "'," + recNo + "," + trsNo + ",'" + brcode + "','" + brcode + "')");
                        int var20 = insertQuery20.executeUpdate();
                        if (var20 <= 0) {
                            throw new ApplicationException("Problem In Insertion In rdrecon of Dr Type for Ac No:- '" + rdInterestDTO.getAcNo() + "'");
                        }
                    }
                }
                String glAccNo = null;
                /**
                 * *************Total TDS posting in GL Recon**************
                 */
                if (tdsPostEnable.equalsIgnoreCase("Y")) {
                    if (totalTds > 0) {
                        List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                                + "where TDS_Applicabledate<='" + todt + "')").getResultList();
                        if (!chk3.isEmpty()) {
                            Vector chk3V = (Vector) chk3.get(0);
                            glAccNo = brcode + chk3V.get(0).toString();
                        }
                        Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glAccNo + "'").executeUpdate();
                        if (reconBalan <= 0) {
                            return "Error in GL Balance Updation !!!";
                        }
                        if (totalTds > 0) {
                            recNo = fts.getRecNo();
                            Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                                    + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glAccNo + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                    + "" + totalTds + ",0,2, CONCAT('TDS POSTING TILL DT ' , date_format('" + todt + "','%Y%m%d')),'" + AuthBy + "','Y','" + AuthBy + "',now(),'" + recNo + "',"
                                    + "'" + trsNo + "',3,33,1,'" + brcode + "','" + brcode + "')").executeUpdate();
                            if (glrecon <= 0) {
                                return "Error in TDS Posting in GL !!!";
                            }
                        }
                    }
                }
            }
            return "true" + Float.toString(trsNo);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private List<RdInterestDTO> interestCalculate(String acType, String fromDate, String toDate, String brCode) throws ApplicationException {
        List<RdInterestDTO> rdIntList = new ArrayList<RdInterestDTO>();
        try {
            List accountList = em.createNativeQuery("Select AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt, AM.RdInstal, sum(ifnull(RP.Balance,0)) as Balance "
                    + " From accountmaster AM, rd_product RP Where AM.curbrcode =  '" + brCode + "' and AM.accttype ='" + acType
                    + "' And AM.AccStatus<>9 and AM.RdMatDate not between '" + fromDate + "' and '" + toDate + "' and AM.Acno = RP.acno"
                    + " And RP.MonthlastDt between '" + fromDate + "' and '" + toDate + "' group by AM.Acno, AM.CustName, AM.IntDeposit,"
                    + "AM.OpeningDt, AM.RdInstal order by AM.acno").getResultList();

            if (!accountList.isEmpty()) {
                for (int i = 0; i < accountList.size(); i++) {
                    Vector vect = (Vector) accountList.get(i);
                    RdInterestDTO rdIntDetail = new RdInterestDTO();

                    rdIntDetail.setAcNo(vect.get(0).toString());
                    rdIntDetail.setCustName(vect.get(1).toString());
                    rdIntDetail.setRoi(Double.parseDouble(vect.get(2).toString()));

                    rdIntDetail.setOpeningDt(vect.get(3).toString());
                    rdIntDetail.setInstallment(Double.parseDouble(vect.get(4).toString()));
                    rdIntDetail.setBalance(CbsUtil.round(Double.parseDouble(vect.get(5).toString()), 2));

                    double intAmt = CbsUtil.round(rdIntDetail.getBalance() * rdIntDetail.getRoi() / 1200, 0);
                    if (intAmt > 0) {
                        rdIntDetail.setInterest(intAmt);
                        rdIntList.add(rdIntDetail);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return rdIntList;
    }

    /**
     *
     * @param tmpPreDate
     * @param quarEndDt
     * @param brCode
     * @return
     * @throws ApplicationException
     */
    public List getFinYear(String tmpPreDate, String quarEndDt, String brCode) throws ApplicationException {
        List finYear = new ArrayList();
        try {
            finYear = em.createNativeQuery("Select f_year From yearend Where YearEndFlag='N' and brnCode='" + brCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finYear;
    }

    public List getRdAccountDetail(String acno, String brCode) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            String newAcno = fts.getNewAccountNumber(acno);
            List accountmasterList = em.createNativeQuery("select accStatus, accttype, curBrCode from accountmaster where acno = '" + newAcno + "'").getResultList();
            if (accountmasterList.isEmpty()) {
                throw new ApplicationException("Account No. Does Not Exist");
            }

            Vector statusVect = (Vector) accountmasterList.get(0);
            String acType = statusVect.get(1).toString();
            String curBrCode = statusVect.get(2).toString();

            if (!curBrCode.equalsIgnoreCase(brCode)) {
                throw new ApplicationException("This is not your branch's account Number");
            }
            if (Integer.parseInt(statusVect.get(0).toString()) == 9) {
                throw new ApplicationException("Account is closed");
            }

            List customermasterList = em.createNativeQuery("select DATE_FORMAT(CURDATE(),'%d/%m/%Y') S_Date ,DATE_FORMAT(a.RDmatdate,'%d/%m/%Y') RDmatdate,"
                    + "a.RDinstal,a.intDeposit,DATE_FORMAT(a.openingDT,'%d/%m/%Y') openingDT,c.custname from accountmaster a,"
                    + "customermaster c where a.acno = '" + newAcno + "' and substring(a.acno,5,6)=c.custno and c.actype='" + acType + "' and "
                    + "c.brncode = " + curBrCode).getResultList();
            if (customermasterList.isEmpty()) {
                throw new ApplicationException("Account No. Does Not Exist");
            }

            Vector customermasterVect = (Vector) customermasterList.get(0);
            resultList.add(newAcno);
            resultList.add(customermasterVect.get(0).toString());
            resultList.add(customermasterVect.get(1).toString());
            resultList.add(Float.parseFloat(customermasterVect.get(2).toString()));

            resultList.add(Float.parseFloat(customermasterVect.get(3).toString()));
            resultList.add(customermasterVect.get(4).toString());
            resultList.add(customermasterVect.get(5).toString());

            int monDiff = CbsUtil.monthDiff(sdf.parse(customermasterVect.get(4).toString()), sdf.parse(customermasterVect.get(1).toString()));
            resultList.add(monDiff);
            List rdList = em.createNativeQuery("select ifnull(sum(interest),0.0) from rd_interesthistory where acno = '" + newAcno + "'").getResultList();
            float interest = 0f;
            if (rdList.size() > 0) {
                Vector rdVect = (Vector) rdList.get(0);
                interest = Float.parseFloat(rdVect.get(0).toString());
            } else {
                interest = 0;
            }
            resultList.add(interest);
            resultList.add(Float.parseFloat(getAcctBalance(newAcno)));
            resultList.add(Float.parseFloat(sngGetPrevailingRoi(acType)));
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getAcctBalance(String AccountNo) throws ApplicationException {
        try {
            List tdList = em.createNativeQuery("SELECT balance FROM reconbalan WHERE ACNO = '" + AccountNo + "'").getResultList();
            if (tdList.size() > 0) {
                Vector tdVect = (Vector) tdList.get(0);
                return tdVect.get(0).toString();
            } else {
                return "0";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List rdIntCal(String openingDate, String matDt, Float netConRoi, Float install) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            long vDays = CbsUtil.dayDiff(ymd.parse(openingDate), ymd.parse(matDt));
            if (vDays < 91) {
                throw new ApplicationException("Sorry Cannot Calculate Interest. Please Verify the Maturity Date");
            }
            long n = vDays / 91;
            double ip = 4d;
            double i = netConRoi / ip;
            double c = ip / 12;

            double f = Math.pow((1 + i / 100), c) - 1;
            double rdPrin = install;
            double a1 = (Math.pow((1 + f), CbsUtil.monthDiff(ymd.parse(openingDate), ymd.parse(matDt))) - 1) / f;
            double a2 = a1 * (1 + f);

            double matAmt = a2 * install;
            double interest = (matAmt - (rdPrin * CbsUtil.monthDiff(ymd.parse(openingDate), ymd.parse(matDt))));

            interest = CbsUtil.round(interest, 4);
            double n5 = (vDays / 30) - (n * 3);

            resultList.add(matAmt);
            resultList.add(interest);
            resultList.add(n5);
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List rdInterestCalculation(String rflag, Float totinstall, Float netConRoi, String toDt, String fromDt, String matDt,
            String acno, Float install, Float penaltyAmt) throws ApplicationException {

        List resultList = new ArrayList();
        try {
            double a2 = 0d, interest = 0d;
            if (rflag.equalsIgnoreCase("1")) {
                a2 = totinstall;
                interest = 0;
            } else if (rflag.equalsIgnoreCase("2")) {
                String lDate = CbsUtil.monthAdd(toDt, 1);
                String lDate1 = lDate.substring(0, 6) + "01";

                lDate1 = CbsUtil.dateAdd(lDate1, -1);
                double lProduct = minBal1RD(fromDt, toDt, acno);
                interest = CbsUtil.round(lProduct * netConRoi / 1200, 0);
                a2 = totinstall + interest;
            } else if (rflag.equalsIgnoreCase("3")) {
                double n5 = 0d;
                List rdIntList = rdIntCal(fromDt, matDt, netConRoi, install);
                if (rdIntList.size() > 0) {
                    a2 = Double.parseDouble(rdIntList.get(0).toString());
                    interest = Double.parseDouble(rdIntList.get(1).toString());
                    n5 = Double.parseDouble(rdIntList.get(2).toString());
                }
                double r2 = Double.parseDouble(sngGetPrevailingRoi(fts.getAccountCode(acno)));
                double install2 = ((a2 * n5) + install * (n5 - 1)) * r2 / 1200;
                if (install2 > 0) {
                    interest = interest + install2;
                    a2 = a2 + install2;
                }
            } else if (rflag.equalsIgnoreCase("4")) {
                List rdIntList = rdIntCal(fromDt, matDt, netConRoi, install);
                if (rdIntList.size() > 0) {
                    a2 = Double.parseDouble(rdIntList.get(0).toString());
                    interest = Double.parseDouble(rdIntList.get(1).toString());
                }
            }

            double intBefore = interest;
            if (penaltyAmt > 0) {
                if (interest < penaltyAmt) {
                    throw new ApplicationException("Interest Amount is Less then Penalty Amount. Please Verify the Penalty Amount");
                }
                interest = interest - penaltyAmt;
                a2 = a2 - penaltyAmt;
            }
            double amtToPay = totinstall + interest;
            if (amtToPay > a2) {
                a2 = amtToPay;
            }
            double intAfter = interest;
            intAfter = CbsUtil.round(intAfter, 0);
            intBefore = CbsUtil.round(intBefore, 0);

            resultList.add(intBefore);
            resultList.add(intAfter);
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String sngGetPrevailingRoi(String acType) throws ApplicationException {
        try {
            List AccountTypeMasterList = em.createNativeQuery("select ifnull(minint,0) from accounttypemaster Where AcctCode = '" + acType + "'").getResultList();
            if (AccountTypeMasterList.isEmpty()) {
                throw new ApplicationException("Prevailing Roi does not exist");
            }
            Vector tdVect = (Vector) AccountTypeMasterList.get(0);
            return tdVect.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public int getStartIndex(List<RdIntDetail> list, Date dt) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                RdIntDetail obj = list.get(i);
                if (obj.getDate().getTime() >= dt.getTime()) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public int getEndIndex(List<RdIntDetail> list, Date dt) throws ApplicationException {
        try {
            if (isExist(list, dt)) {
                for (int i = 0; i < list.size(); i++) {
                    RdIntDetail obj = list.get(i);
                    if (obj.getDate().getTime() == dt.getTime()) {
                        return ++i;
                    }
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    RdIntDetail obj = list.get(i);
                    if (obj.getDate().getTime() > dt.getTime()) {
                        return i;
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public boolean isExist(List<RdIntDetail> list, Date dt) throws ApplicationException {
        try {
            for (RdIntDetail obj : list) {
                if (dt.getTime() == obj.getDate().getTime()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public double minBal1RD(String frDt, String toDt, String actno) throws ApplicationException {
        try {
            List<RdIntDetail> rdIntDetailList = new ArrayList<RdIntDetail>();
            List rdReconList = em.createNativeQuery("select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),DATE_FORMAT(dt,'%Y%m%d')"
                    + "from rdrecon where acno='" + actno + "' and dt >= '" + frDt + "' AND dt <='" + toDt + "' group by dt order by dt").getResultList();
            if (rdReconList.isEmpty()) {
                throw new ApplicationException("Data does not exist for interest calculation");
            }

            for (int i = 0; i < rdReconList.size(); i++) {
                RdIntDetail rdIntDetail = new RdIntDetail();
                Vector vect = (Vector) rdReconList.get(i);
                rdIntDetail.setDate(ymd.parse(vect.elementAt(1).toString()));
                rdIntDetail.setBalance(Double.parseDouble(vect.elementAt(0).toString()));
                rdIntDetailList.add(rdIntDetail);
            }

            Collections.sort(rdIntDetailList, new DateByComparator());
            double sbal = 0f;
            double totProd = 0f;
            String inDate = frDt;
            int ctr1 = 1;
            int ctr2 = CbsUtil.monthDiff(ymd.parse(frDt), ymd.parse(toDt));
            while (ctr1 <= ctr2) {
                String lDate1 = CbsUtil.monthAdd(inDate, ctr1);
                lDate1 = CbsUtil.dateAdd(lDate1, -1);
                frDt = CbsUtil.dateAdd(frDt, 10);
                List sbalList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))- sum(ifnull(dramt,0)),0) from rdrecon where "
                        + "acno='" + actno + "' and dt <'" + frDt + "'").getResultList();
                if (sbalList.size() > 0) {
                    Vector sbalVect = (Vector) sbalList.get(0);
                    sbal = Float.parseFloat(sbalVect.get(0).toString());
                }
                // double prod = minBal2RD(d1, lDate1, actno, sbal);

                double dbal1 = sbal;
                double dbal = 0d;

                int startIndex = getStartIndex(rdIntDetailList, ymd.parse(frDt));
                if (startIndex == -1) {
                    startIndex = 0;
                }
                int endIndex = getEndIndex(rdIntDetailList, ymd.parse(lDate1));
                if (endIndex == -1) {
                    endIndex = rdIntDetailList.size();
                }
                List<RdIntDetail> rdIntSubList = rdIntDetailList.subList(startIndex, endIndex);
                for (RdIntDetail rdIntDetail : rdIntSubList) {
                    dbal = rdIntDetail.getBalance();
                    dbal1 = dbal + dbal1;
                    if (dbal1 < sbal) {
                        sbal = CbsUtil.round(dbal, 2);
                    }
                }
                double prod;
                if (sbal < 0) {
                    prod = (float) 0;
                } else {
                    prod = sbal;
                }
                totProd = totProd + prod;
                frDt = CbsUtil.monthAdd(inDate, ctr1);
                ctr1 = ctr1 + 1;
            }
            return totProd;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public float rdIntPosting(double dint, double dprovamt, String datetmp, String orgbrnid, String destBrnid, Integer rdSimplepostflag,
            String enterby, String authBy, String acno, String gpostcheck, double tdsToBeDeducted, double closeActTdsToBeDeducted,
            String matDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        String msg = "";
        double intRemain = 0d;
        String detail;
        try {
            ut.begin();
            String acType = fts.getAccountCode(acno);
            List acctTypeList = em.createNativeQuery("SELECT GLHEADINT,GLHEADPROV FROM accounttypemaster WHERE ACCTCODE = '"
                    + acType + "'").getResultList();
            if (acctTypeList.isEmpty()) {
                throw new ApplicationException("You have not specified GL Heads for " + acType + " accounts");
            }
            Vector acctTypeVect = (Vector) acctTypeList.get(0);

            String glheadinttmp = acctTypeVect.get(0).toString();
            String glheadprov = acctTypeVect.get(1).toString();
            if (glheadinttmp.equalsIgnoreCase("")) {
                throw new ApplicationException("You have not specified GL Heads for " + acType + " accounts");
            }
            if (glheadprov.equalsIgnoreCase("")) {
                throw new ApplicationException("You have not specified GL Heads for " + acType + " accounts");
            }
            intRemain = CbsUtil.round(dint, 0) - CbsUtil.round(dprovamt, 0);
            String tmpglinterest = orgbrnid + glheadinttmp + "01";
            String tmpglprov = orgbrnid + glheadprov + "01";
            List frdtList = em.createNativeQuery("select ifnull(date_format(max(dt),'%Y%m%d'),'') from rd_interesthistory where acno = '" + acno + "'").getResultList();
            Vector dtVector = (Vector) frdtList.get(0);
            String rdfrDt = dtVector.get(0).toString();
            if (rdfrDt.equalsIgnoreCase("")) {
                rdfrDt = datetmp;
            }
            //For New Code tds posting
            String glTds = "";
            List tdsGlHead = em.createNativeQuery("select tds_glhead from tdsslab where tds_applicabledate=(select max(tds_applicabledate) from tdsslab)").getResultList();
            if (tdsGlHead.isEmpty()) {
                throw new ApplicationException("GL Head is not present in the TDS Slab");
            }
            Vector tGlhead = (Vector) tdsGlHead.get(0);
            String tdsGlHeads = tGlhead.get(0).toString();
            if (tGlhead.get(0) == null) {
                throw new ApplicationException("GL Head is not present in the TDS Slab");
            }
            glTds = orgbrnid + tdsGlHeads;

            float trsNo = fts.getTrsNo();
            if (intRemain >= 0) {
                if (rdSimplepostflag == 0 || rdSimplepostflag == 2) {
                    if (dint != 0) {
                        detail = "VCH INT TRANSFERRED";
                        msg = fts.ftsPosting43CBS(acno, 8, 0, Math.abs(dint), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "S");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }
                    }

                    //For New Code Rd Interest posting
                    String intOption = "";
                    if (intRemain != 0) {
                        Query insertQueryA = em.createNativeQuery("insert into rd_interesthistory(acno,dt,interest,vch_no,todt,from_dt,enterby,Remarks)"
                                + "values ('" + acno + "','" + datetmp + "'," + intRemain + ",0,'" + datetmp + "','" + rdfrDt + "','" + authBy + "','For Payment Interest Posting')");
                        int varAZ = insertQueryA.executeUpdate();
                        if (varAZ <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                    }
                    //End For New Code Rd Interest posting
                    //For New Code TDs posting
                    List selectQuery = em.createNativeQuery("Select TDSFlag From accountmaster where acno='" + acno + "' ").getResultList();
                    Vector tdsFlagCurrent = (Vector) selectQuery.get(0);
                    String tdsFlag = tdsFlagCurrent.get(0).toString();
                    if (tdsFlag.equals("Y") || tdsFlag.equals("C")) {
                        if (tdsToBeDeducted > 0 || tdsToBeDeducted != 0) {
                            Query insertQueryC = em.createNativeQuery("insert into tdshistory(acno,voucherno,tds,dt,todt,fromdt) values ('" + acno
                                    + "',0," + tdsToBeDeducted + ",'" + datetmp + "','" + matDt + "','" + datetmp + "')");
                            int varC = insertQueryC.executeUpdate();
                            if (varC <= 0) {
                                throw new ApplicationException("Problem in data insertion");
                            }
                            Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,"
                                    + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                                    + "VALUES('" + acno + "',0," + tdsToBeDeducted + ",'" + datetmp + "','" + matDt + "','" + datetmp
                                    + "','R'," + trsNo + "," + fts.getRecNo() + ",0,date_format(now(),'%Y%m%d'))");

                            int result = insertTdsQuery.executeUpdate();
                            if (result < 0) {
                                throw new ApplicationException("Error in updating tds_reserve_history for tds ");
                            }
                            double tdsAmt = tdsToBeDeducted;
                            float recNo = fts.getRecNo();
                            float rtNumber = 0;
                            if (closeActTdsToBeDeducted > 0) {
                                String finYr = autoTermDepositFacade.getFinYear(orgbrnid);
                                String frmDT = finYr + "0401";
                                String toDT = (Integer.parseInt(finYr) + 1) + "0331";
                                List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, frmDT, toDT, "NR", "Y");

                                autoTermDepositFacade.closeActTdsPosting(nrCloseList, frmDT, toDT, trsNo, recNo, datetmp, rtNumber, closeActTdsToBeDeducted, acno, intOption);
                                tdsAmt = tdsAmt + closeActTdsToBeDeducted;
                            }

                            String TDS = "TDS Deducted For Acno : " + acno + "/" + rtNumber;
                            msg = fts.ftsPosting43CBS(acno, 2, 1, Math.abs(tdsAmt), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, TDS,
                                    trsNo, 0.0f, 0, "", "Y", authBy, "A", 3, "", "", "", 0.0f, "", "", "", "", 0.0f, "N", "", "", "");

                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg.substring(4));
                            }

                            String TDS1 = "TDS Deducted For Acno " + glTds + "/" + rtNumber;
                            msg = fts.ftsPosting43CBS(glTds, 2, 0, Math.abs(tdsAmt), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, TDS1,
                                    trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg.substring(4));
                            }
                        }
                    }
                    //End For New Code TDs posting

                    if (intRemain != 0) {
                        detail = "VCH INT POSTED  " + acno;
                        msg = fts.ftsPosting43CBS(tmpglinterest, 8, 1, Math.abs(intRemain), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }
                    }
                    if (dprovamt != 0) {
                        detail = "VCH INT TRANSFERRED TO A/C NO" + acno;
                        msg = fts.ftsPosting43CBS(tmpglprov, 8, 1, Math.abs(dprovamt), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }

                    }
                } else {
                    if (intRemain != 0) {
                        detail = "VCH INT. POSTED TO A/C";
                        msg = fts.ftsPosting43CBS(acno, 8, 0, Math.abs(intRemain), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, 0.0f, 0, "", "Y", authBy, "A", 3, "", "", "", 0.0f, "", "", "", "", 0.0f, "N", "", "", "S");

                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }
                    }
                    if (intRemain != 0) {
                        detail = "VCH INT. POSTED TO A/C NO " + acno;
                        msg = fts.ftsPosting43CBS(tmpglinterest, 8, 1, Math.abs(intRemain), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }

                    }
                    //For New Code Rd Interest posting
                    String intOption = "";
                    if (intRemain > 0) {
                        Query insertQueryA = em.createNativeQuery("insert into rd_interesthistory(acno,dt,interest,vch_no,todt,from_dt,int_opt,enterby,Remarks)"
                                + "values ('" + acno + "','" + datetmp + "'," + intRemain + ",0,'" + datetmp + "','" + rdfrDt + "','"
                                + intOption + "','" + authBy + "','For Payment Interest Posting')");
                        int varAZ = insertQueryA.executeUpdate();
                        if (varAZ <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                    }
                    //End For New Code Rd Interest posting
                    //For New Code TDs posting
                    List selectQuery = em.createNativeQuery("Select TDSFlag From accountmaster where acno='" + acno + "' ").getResultList();
                    Vector tdsFlagCurrent = (Vector) selectQuery.get(0);
                    String tdsFlag = tdsFlagCurrent.get(0).toString();
                    if (tdsFlag.equals("Y") || tdsFlag.equals("C")) {
                        if (tdsToBeDeducted > 0 || tdsToBeDeducted != 0) {
                            Query insertQueryC = em.createNativeQuery("insert into tdshistory(acno,voucherno,tds,dt,todt,fromdt,intopt) values ('" + acno
                                    + "',0," + tdsToBeDeducted + ",'" + datetmp + "','" + matDt + "','" + datetmp + "','" + intOption + "')");
                            int varC = insertQueryC.executeUpdate();
                            if (varC <= 0) {
                                throw new ApplicationException("Problem in data insertion");
                            }
                            Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
                                    + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                                    + "VALUES('" + acno + "',0," + tdsToBeDeducted + ",'" + datetmp + "','" + matDt + "','" + datetmp + "','"
                                    + intOption + "','R'," + trsNo + "," + fts.getRecNo() + ",0,date_format(now(),'%Y%m%d'))");

                            int result = insertTdsQuery.executeUpdate();
                            if (result < 0) {
                                throw new ApplicationException("Error in updating tds_reserve_history for tds ");
                            }
                            double tdsAmt = tdsToBeDeducted;
                            float recNo = fts.getRecNo();
                            float rtNumber = 0;
                            if (closeActTdsToBeDeducted > 0) {
                                String finYr = autoTermDepositFacade.getFinYear(orgbrnid);
                                String frmDT = finYr + "0401";
                                String toDT = (Integer.parseInt(finYr) + 1) + "0331";
                                List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, frmDT, toDT, "NR", "Y");

                                autoTermDepositFacade.closeActTdsPosting(nrCloseList, frmDT, toDT, trsNo, recNo, datetmp, rtNumber, closeActTdsToBeDeducted, acno, intOption);
                                tdsAmt = tdsAmt + closeActTdsToBeDeducted;
                            }

                            String TDS = "TDS Deducted For Acno : " + acno + "/" + rtNumber + "/ " + intOption;
                            msg = fts.ftsPosting43CBS(acno, 2, 1, Math.abs(tdsAmt), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, TDS,
                                    trsNo, 0.0f, 0, "", "Y", authBy, "A", 3, "", "", "", 0.0f, "", "", "", "", 0.0f, "N", "", "", "");

                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg.substring(4));
                            }

                            String TDS1 = "TDS Deducted For Acno " + glTds + "/" + rtNumber + "/ " + intOption;
                            msg = fts.ftsPosting43CBS(glTds, 2, 0, Math.abs(tdsAmt), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, TDS1,
                                    trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg.substring(4));
                            }
                        }
                    }
                    //End For New Code TDs posting
                }
            } else {
                if (rdSimplepostflag == 0 || rdSimplepostflag == 2) {
                    if (dint != 0) {
                        detail = "VCH INT. TRANSFERRED TO A/C";
                        msg = fts.ftsPosting43CBS(acno, 8, 0, Math.abs(dint), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "S");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }

                    }
                    if (intRemain != 0) {
                        detail = "VCH INT REVERSAL FOR A/C NO " + acno;
                        msg = fts.ftsPosting43CBS(tmpglinterest, 8, 0, Math.abs(intRemain), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }
                    }
                    if (intRemain != 0) {
                        Query insertQueryA = em.createNativeQuery("insert into rd_interesthistory(acno,dt,interest,vch_no,todt,from_dt,enterby,Remarks)"
                                + "values ('" + acno + "','" + datetmp + "'," + intRemain + ",0,'" + datetmp + "','" + rdfrDt + "','" + authBy + "','For Payment Interest Posting')");
                        int varAZ = insertQueryA.executeUpdate();
                        if (varAZ <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                    }
                    if (dprovamt != 0) {
                        detail = "VCH INT. TRANSFERRED TO A/C NO " + acno;
                        msg = fts.ftsPosting43CBS(tmpglprov, 8, 1, Math.abs(dprovamt), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }
                    }
                } else {
                    if (intRemain != 0) {
                        detail = "VCH INT. REVERSAL";
                        msg = fts.ftsPosting43CBS(acno, 8, 1, Math.abs(intRemain), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "S");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }

                    }

                    if (intRemain != 0) {
                        Query insertQueryA = em.createNativeQuery("insert into rd_interesthistory(acno,dt,interest,vch_no,todt,from_dt,enterby,Remarks)"
                                + "values ('" + acno + "','" + datetmp + "'," + intRemain + ",0,'" + datetmp + "','" + rdfrDt + "','" + authBy + "','For Payment Interest Posting')");
                        int varAZ = insertQueryA.executeUpdate();
                        if (varAZ <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                    }
                    if (intRemain != 0) {
                        detail = "VCH INT. REVERSAL TRANSFERRED FROM A/C NO  " + acno;
                        msg = fts.ftsPosting43CBS(tmpglinterest, 8, 0, Math.abs(intRemain), datetmp, datetmp, enterby, orgbrnid, destBrnid, 3, detail,
                                trsNo, (float) 0.0, 0, "", "Y", authBy, "A", 3, "", "", "", (float) 0.0, "", "", "", "", (float) 0.0, "N", "", "", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg.substring(4));
                        }
                    }
                }
            }

            String query = "update td_payment_auth set auth='Y',authBy='" + authBy + "' where acno = '" + acno + "'";
            int rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data updation");
            }

            ut.commit();
            return trsNo;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List getRate(String fdDate, long inrange, Float inAmount) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Interest_Rate,Applicable_Date  from "
                    + "td_slab where FromDays <= " + inrange + " and ToDays >= " + inrange + " "
                    + "and fromamount<=" + inAmount + " and toamount>=" + inAmount + " and "
                    + "Applicable_Date = (select max(Applicable_Date) from td_slab where "
                    + "applicable_date <='" + fdDate + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getInstallment(String acNo) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select DATE_FORMAT(duedt, '%d/%m/%Y'), installamt, status,"
                    + "DATE_FORMAT(paymentdt, '%d/%m/%Y')  from rd_installment where acno='" + acNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List<RdInterestDTO> rdIntProvision(String acType, String toDate, String brCode) throws ApplicationException {
        try {
            List<RdInterestDTO> rdIntList = new ArrayList<RdInterestDTO>();
            List accountList = em.createNativeQuery("Select AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt, AM.RdInstal, sum(ifnull(RP.Balance,0)) as Balance "
                    + "From accountmaster AM, rd_product RP Where AM.curbrcode =  '" + brCode + "' and AM.accttype ='" + acType + "' "
                    + "And AM.AccStatus<>9 and AM.Acno = RP.acno and AM.OpeningDt <='" + toDate + "' and RP.dt <='" + toDate + "' "
                    + "group by AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt,AM.RdInstal order by AM.acno").getResultList();

            if (accountList.isEmpty()) {
                throw new ApplicationException("There is no account for interest calculation");
            } else {
                RdInterestDTO rdIntDetail;
                for (int i = 0; i < accountList.size(); i++) {
                    Vector vect = (Vector) accountList.get(i);
                    rdIntDetail = new RdInterestDTO();

                    rdIntDetail.setAcNo(vect.get(0).toString());
                    rdIntDetail.setCustName(vect.get(1).toString());
                    rdIntDetail.setRoi(Double.parseDouble(vect.get(2).toString()));

                    rdIntDetail.setOpeningDt(vect.get(3).toString());
                    rdIntDetail.setInstallment(Double.parseDouble(vect.get(4).toString()));
                    rdIntDetail.setBalance(CbsUtil.round(Double.parseDouble(vect.get(5).toString()), 2));

                    double intAmt = CbsUtil.round(rdIntDetail.getBalance() * rdIntDetail.getRoi() / 1200, 0);
                    if (intAmt > 0) {
                        rdIntDetail.setInterest(intAmt);
                        rdIntList.add(rdIntDetail);
                    }
                }
            }
            if (rdIntList.isEmpty()) {
                throw new ApplicationException("There is no account for interest calculation");
            } else {
                return rdIntList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public int rdPostFlag() throws ApplicationException {
        int postFlag;
        try {
            List resultlist = em.createNativeQuery("select rd_simplepostflag from td_parameterinfo where txnid = "
                    + " (select max(txnid) from td_parameterinfo)").getResultList();
            Vector postVect = (Vector) resultlist.get(0);
            postFlag = Integer.parseInt(postVect.get(0).toString());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return postFlag;
    }

    public List getAcctNature() throws ApplicationException {
        List accountResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctdesc from accounttypemaster where acctcode not in ('NP','PO','OF') order by acctcode");
            accountResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accountResult;
    }

    public List gridDetailTargetMaster(String accountType) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select acctdesc,acctnature,orgntype,orgncode,targetacno,targetamt,dtfrom,dtto,type,period,eneterby,authby,trantime from target_master where acctdesc ='" + accountType + "'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List Acctnature(String accountType) throws ApplicationException {
        try {

            List checkList;
            checkList = em.createNativeQuery("select acctnature from accounttypemaster where acctdesc ='" + accountType + "'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);

        }
    }

    public String saveRecordTargent(String accountType, String acctNature, String orgntype, String orgnCode, String targetAc, String tragetAmount, String frDt, String toDt, String monthlyYear, String actype, String userName, String todayDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            Query q1 = em.createNativeQuery("insert into target_master(acctdesc,acctnature,orgntype,orgncode,targetacno,targetamt,dtfrom,dtto,type,period,eneterby,authby,trantime ) values('" + accountType + "','" + acctNature + "','" + orgntype + "','" + orgnCode + "','" + targetAc + "','" + tragetAmount + "','" + frDt + "','" + toDt + "','" + actype + "','" + monthlyYear + "','" + userName + "','" + userName + "','" + todayDate + "')");
            int insertResult = q1.executeUpdate();
            if (insertResult <= 0) {
                throw new ApplicationException("Insertion problem in target_master.");
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

    public List<TdIntDetail> intCalcForTds(String acType, String fromDate, String toDate, String brCode) throws ApplicationException {
        List<TdIntDetail> rdIntList = new ArrayList<TdIntDetail>();
        try {
            String msg = checkMonthEndForRdProvision(fromDate, toDate, brCode, "Q");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            List accountList = em.createNativeQuery("Select ci.custid, AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt, AM.RdInstal, sum(ifnull(RP.Balance,0)) as Balance "
                    + " From accountmaster AM, rd_product RP, customerid ci Where AM.acno = ci.acno and AM.curbrcode =  '" + brCode + "' and AM.accttype ='" + acType
                    + "' And AM.AccStatus<>9 and AM.RdMatDate not between '" + fromDate + "' and '" + toDate + "' and AM.Acno = RP.acno"
                    + " And RP.MonthlastDt between '" + fromDate + "' and '" + toDate + "' and AM.tdsflag in ('Y','C') group by ci.custid,AM.Acno, AM.CustName, AM.IntDeposit,"
                    + "AM.OpeningDt, AM.RdInstal order by AM.acno").getResultList();

            for (int i = 0; i < accountList.size(); i++) {
                Vector vect = (Vector) accountList.get(i);
                TdIntDetail rdIntDetail = new TdIntDetail();
                rdIntDetail.setMsg("TRUE");

                rdIntDetail.setCustId(vect.get(0).toString());
                rdIntDetail.setAcno(vect.get(1).toString());
                rdIntDetail.setCustName(vect.get(2).toString());
                rdIntDetail.setRoi(Float.parseFloat(vect.get(3).toString()));


                rdIntDetail.setVoucherNo(0);
                rdIntDetail.setIntOpt("Q");

                double balance = CbsUtil.round(Double.parseDouble(vect.get(6).toString()), 2);
                rdIntDetail.setpAmt(balance);
                rdIntDetail.setFromDt(fromDate);

                rdIntDetail.setMatDt(toDate);

                rdIntDetail.setIntToAcno("");

                rdIntDetail.setToDt(toDate);
                rdIntDetail.setSeqno(0);

                rdIntDetail.setStatus("A");
                double intAmt = CbsUtil.round(balance * rdIntDetail.getRoi() / 1200, 0);
                if (intAmt > 0) {
                    rdIntDetail.setInterest(intAmt);
                    rdIntList.add(rdIntDetail);
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return rdIntList;
    }

    public List<TdIntDetail> intCalcRDWithTds(String acType, String fromDate, String toDate, String brCode) throws ApplicationException {
        List<TdIntDetail> rdIntList = new ArrayList<TdIntDetail>();
        try {
            String msg = checkMonthEndForRdProvision(fromDate, toDate, brCode, "Q");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            String matQueryBetDt = "";
//            System.out.println("Start>>>:"+ new Date() +"; Branch:"+brCode+"; acctType:" + acType + "; intOpt:Q; FDate:" + fromDate + "; TDate:" + toDate);

            String finStartDt = rbiReportFacade.getMinFinYear(toDate);
            if (toDate.substring(4, 6).equalsIgnoreCase("03")) {
                /*RD accounts which are matured between both date but not yet closed*/
                matQueryBetDt = " union "
                        + "Select ci.custid, AM.Acno as acno, AM.CustName, AM.IntDeposit as roi,AM.OpeningDt, AM.RdInstal, "
                        + " sum(ifnull(RP.Balance,0)) as Balance,ifnull(AM.TDSFLAG,'Y') as TDSFLAG, "
                        + " ifnull(cd.PAN_GIRNumber,'') as pan,  ifnull(cd.minorflag,'N') as minorflag, 'Y' as dayWise, "
                        + " ifnull(CUST_TYPE,'OT') as custType, date_format(cd.DateOfBirth,'%Y%m%d') asDateOfBirth, ifnull(cd.CustEntityType,'03') as CustEntityType     "
                        + " From accountmaster AM, rd_product RP, customerid ci, cbs_customer_master_detail cd  Where AM.acno = ci.acno  and ci.CustId = cast(cd.customerid as unsigned) and AM.curbrcode =  '" + brCode + "' and AM.accttype ='" + acType
                        + "' And AM.AccStatus<>9 and AM.RdMatDate between '" + fromDate + "' and '" + toDate + "' and AM.Acno = RP.acno "
                        + " And RP.MonthlastDt between '" + fromDate + "' and '" + toDate + "' "
                        + " /*and ci.custid  in (39152, 11429, 48078)*/ group by ci.custid,AM.Acno, AM.CustName, AM.IntDeposit,"
                        + " AM.OpeningDt, AM.RdInstal order by acno";
            } else {
                matQueryBetDt = " order by acno ";
            }

            String acQuery = "select  aa.CustId, aa.Acno as acno, aa.custName, aa.roi,aa.OpeningDt, aa.RdInstal, aa.Balance, "
                    + " aa.TDSFLAG, aa.pan,  aa.minorflag,  "
                    + " ifnull(gr.CustomerId,0)  as minorCustIdfrom,  "
                    + " ifnull(pro.CustId,'') as propCustId, ifnull(nrTds.nrTds,0), ifnull(vo.totalAcInt,0), "
                    + " ifnull(tdsVouch.totalTdsAcWise,0),  ifnull(bb.totalIntOnCustId,0), ifnull(tds.totalTdsCustId,0), "
                    + " ifnull(tdsVouch.closeAcTds,0), ifnull(nrTdsCustId.nrTdsCustIdWise,0), "
                    + " ifnull(gur.majorCustId,'') as majorCustId, ifnull(gur.pan,'') as majPan, aa.dayWise, aa.custType, "
                    + " aa.DateOfBirth, aa.CustEntityType, if(minorflag='N',aa.DateOfBirth,gur.majDob) as majDob "
                    + " from  "
                    + " (Select ci.custid, AM.Acno, AM.CustName, AM.IntDeposit as roi,AM.OpeningDt, AM.RdInstal, "
                    + " sum(ifnull(RP.Balance,0)) as Balance,ifnull(AM.TDSFLAG,'Y') as TDSFLAG, "
                    + " ifnull(cd.PAN_GIRNumber,'') as pan,  ifnull(cd.minorflag,'N') as minorflag, 'N' as dayWise, "
                    + " ifnull(CUST_TYPE,'OT') as custType, date_format(cd.DateOfBirth,'%Y%m%d') as DateOfBirth, ifnull(cd.CustEntityType,'03') as CustEntityType   "
                    + " From accountmaster AM, rd_product RP, customerid ci, cbs_customer_master_detail cd  Where AM.acno = ci.acno  and ci.CustId = cast(cd.customerid as unsigned) and AM.curbrcode =  '" + brCode + "' and AM.accttype ='" + acType
                    + "' And AM.AccStatus<>9 and AM.RdMatDate > '" + toDate + "' and AM.Acno = RP.acno "
                    + " And RP.MonthlastDt between '" + fromDate + "' and '" + toDate + "' "
                    + " /*and ci.custid  in (39152, 11429, 48078)*/ group by ci.custid,AM.Acno, AM.CustName, AM.IntDeposit,"
                    + " AM.OpeningDt, AM.RdInstal "
                    + matQueryBetDt
                    + " ) aa  "
                    + " left join  "
                    + " (select a.CustId as custId,a.acno,cast(ifnull(sum(a.interest),0) as decimal(25,2)) as totalAcInt from  "
                    + " (select ci.CustId,ti.acno, ifnull(sum(interest),0) as interest from customerid ci,rd_interesthistory ti where ti.acno=ci.acno and  "
                    + " ti.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId, ti.acno  "
                    + " ) a group by a.CustId, a.acno "
                    + " ) vo on aa.CustId = vo.CustId and aa.acno = vo.acno   "
                    + " left join   "
                    + " (select a.CustId as custId, ifnull(cast(sum(a.interest) as decimal(25,2)),0) as totalIntOnCustId from   "
                    + " (select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno   "
                    + " and (tv.cldt >='" + finStartDt + "')  "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId   "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,rd_interesthistory ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate >='" + finStartDt + "')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId   "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,dds_interesthistory ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate >='" + finStartDt + "')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "'  group by ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno   "
                    + " and (tv.cldt is null or tv.cldt ='')  "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,rd_interesthistory ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate is null or ac.closingdate ='')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,dds_interesthistory ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate is null or ac.closingdate ='')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "'  group by ci.CustId ) a group by a.CustId  "
                    + " ) bb on aa.CustId = bb.CustId   "
                    + " left join   "
                    + " (select a.CustId, ifnull(cast(sum(a.tds) as decimal(25,2)),0) as totalTdsCustId from   "
                    + " (select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno   "
                    + "  and (tv.cldt >='" + finStartDt + "')  "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between  '" + finStartDt + "' and '" + toDate + "'   "
                    + " group by  ci.CustId   "
                    + " union all    "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,accountmaster ac,tds_reserve_history ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate >='" + finStartDt + "')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "'   "
                    + " group by  ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno   "
                    + " and (tv.cldt is null or tv.cldt ='')  "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between  '" + finStartDt + "' and '" + toDate + "'   "
                    + " group by  ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,accountmaster ac,tds_reserve_history ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate is null or ac.closingdate ='')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "'   "
                    + " group by  ci.CustId   "
                    + " ) a group by  a.CustId ) tds on aa.CustId = tds.CustId  "
                    + " left join  "
                    + " (select a.CustId,a.acno, sum(a.tds) as totalTdsAcWise, sum(a.closeTds) as closeAcTds from  "
                    + " (select ci.CustId,ti.acno, ifnull(sum(tds),0) as tds, ifnull(sum(ti.closeAcTds),0) as closeTds from customerid ci,tds_reserve_history ti where ti.acno=ci.acno and ti.dt between  '" + finStartDt + "' and '" + toDate + "'  "
                    + " group by  ci.CustId, ti.acno  "
                    + " ) a group by  a.CustId, a.acno ) tdsVouch on aa.CustId = tdsVouch.CustId  and aa.acno = tdsVouch.acno  "
                    + " left join  "
                    + " (select count(gr.customerid) as customerid, gr.guardiancode from  cbs_cust_minorinfo gr  "
                    + " where (gr.guardiancode is not null and gr.guardiancode<> '')  group by gr.guardiancode) gr  "
                    + " on gr.guardiancode = aa.CustId   "
                    + " left join  "
                    + " (select a.CustId,a.acno,sum(a.tds) as nrTds from   "
                    + " (select ci.CustId,ti.acno, ifnull(sum(tds),0) as tds from customerid ci, tds_reserve_history ti where ti.acno=ci.acno    "
                    + " and ti.recovered ='NR' and ti.dt <='" + toDate + "'   "
                    + " group by  ci.CustId, ti.acno  ) a group by  a.CustId, a.acno) nrTds on aa.CustId = nrTds.CustId  and aa.acno = nrTds.acno  "
                    + " left join  "
                    + " (select a.CustId,a.acno,sum(a.tds) as nrTdsCustIdWise from   "
                    + " (select ci.CustId,ti.acno, ifnull(sum(tds),0) as tds from customerid ci, tds_reserve_history ti where ti.acno=ci.acno    "
                    + " and ti.recovered ='NR' and ti.dt <='" + toDate + "'   "
                    + " group by  ci.CustId ) a group by  a.CustId) nrTdsCustId on aa.CustId = nrTdsCustId.CustId   "
                    + " left join  "
                    + " (select distinct tm.acno, ci.CustId from customerid ci,td_accountmaster tm where tm.opermode = 8 and  "
                    + " ci.custid = tm.custid1  "
                    + " union all  "
                    + " select distinct tm.acno, ci.CustId from customerid ci,accountmaster tm where tm.opermode = 8 and  "
                    + " ci.custid = tm.custid1 and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))  "
                    + " ) pro on  pro.acno = aa.acno  "
                    + " left join "
                    + " (select  mn.CustomerId, ifnull(mn.guardiancode,'0')  as majorCustId, cm.PAN_GIRNumber as pan, date_format(cm.DateOfBirth,'%Y%m%d') as majDob from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                    + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '')) gur on gur.customerid = aa.custId "
                    + " order by aa.CustId,aa.acno";
//            System.out.println("acQuery>>>" + acQuery);            
            List accountList = em.createNativeQuery(acQuery).getResultList();
//            List accountList = em.createNativeQuery("Select ci.custid, AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt, AM.RdInstal, sum(ifnull(RP.Balance,0)) as Balance,ifnull(AM.TDSFLAG,'N'), ifnull(cd.PAN_GIRNumber,'') as pan   "
//                    + " From accountmaster AM, rd_product RP, customerid ci, cbs_customer_master_detail cd  Where AM.acno = ci.acno  and ci.CustId = cast(cd.customerid as unsigned) and AM.curbrcode =  '" + brCode + "' and AM.accttype ='" + acType
//                    + "' And AM.AccStatus<>9 and AM.RdMatDate not between '" + fromDate + "' and '" + toDate + "' and AM.Acno = RP.acno"
//                    + " And RP.MonthlastDt between '" + fromDate + "' and '" + toDate + "' and AM.tdsflag in ('Y','C') group by ci.custid,AM.Acno, AM.CustName, AM.IntDeposit,"
//                    + "AM.OpeningDt, AM.RdInstal order by AM.acno").getResultList();

            for (int i = 0; i < accountList.size(); i++) {
                Vector vect = (Vector) accountList.get(i);
                TdIntDetail rdIntDetail = new TdIntDetail();
                rdIntDetail.setMsg("TRUE");
                String custId = vect.get(0).toString();
                rdIntDetail.setCustId(custId);
                String accno = vect.get(1).toString();
                rdIntDetail.setAcno(accno);
                rdIntDetail.setCustName(vect.get(2).toString());
                rdIntDetail.setRoi(Float.parseFloat(vect.get(3).toString()));

                rdIntDetail.setVoucherNo(0);
                rdIntDetail.setIntOpt("Q");

                double balance = CbsUtil.round(Double.parseDouble(vect.get(6).toString()), 2);
                rdIntDetail.setpAmt(balance);
                rdIntDetail.setFromDt(fromDate);

                rdIntDetail.setMatDt(toDate);

                rdIntDetail.setIntToAcno("");

                rdIntDetail.setToDt(toDate);
                rdIntDetail.setSeqno(0);

                rdIntDetail.setStatus("A");
                rdIntDetail.setPropCustId("");
                rdIntDetail.setTdsFlag(vect.get(7).toString());
                rdIntDetail.setMinorInterest(0);
                rdIntDetail.setMajorInterest(0);
                String pan = vect.get(8).toString();
                if (ParseFileUtil.isValidPAN(pan) == true) {
                    pan = "Y";
                } else {
                    pan = "N";
                }
                rdIntDetail.setPan(pan);
                String minorFlag = vect.get(9).toString();
                rdIntDetail.setMinorFlag(minorFlag);
                int noOfMinorInMajor = Integer.parseInt(vect.get(10).toString());
                String propCustId = vect.get(11).toString();
                double unRecTds = Double.parseDouble(vect.get(12).toString());
                double intAcWise = Double.parseDouble(vect.get(13).toString());
                double tdsAcWise = Double.parseDouble(vect.get(14).toString());
                double totalIntPaid = Double.parseDouble(vect.get(15).toString());
                double totalTds = Double.parseDouble(vect.get(16).toString());
                double closeAcTds = Double.parseDouble(vect.get(17).toString());
                double unRecTdsCustIdWise = Double.parseDouble(vect.get(18).toString());
                String majorCustId = vect.get(19).toString();
                String majorPan = vect.get(20).toString();
                String dayWiseCalFlag = vect.get(21).toString();
                String custType = vect.get(22).toString();
                String dob = vect.get(23).toString();
                String custEntityType = vect.get(24).toString();
//                if (vect.get(25) == null) {
//                    System.out.println("Please check the DOB of customer " + custId + " Or Major DOB " + majorCustId);
//                    throw new ApplicationException("Please check the DOB of customer " + custId + " Or Major DOB " + majorCustId + " Or Minor Condition");
//                }
                String majDob = (vect.get(25) == null || vect.get(25).toString().equalsIgnoreCase("19000101")) ? "" : vect.get(25).toString();
                //Day wise calculation
                double intAmt = 0.0;
                if (dayWiseCalFlag.equalsIgnoreCase("Y")) {
                    List query = em.createNativeQuery("select ifnull(sum(FLOOR((a.Balance*a.Days*b.intdeposit)/36500)),0) as intAmt from "
                            + " rd_product a, accountmaster b where a.acno = b.acno and a.acno in ('" + accno + "') and dt between '" + fromDate + "' and '" + toDate + "'").getResultList();
                    if (!query.isEmpty()) {
                        Vector queryVect = (Vector) query.get(0);
                        intAmt = Double.parseDouble(queryVect.get(0).toString());
                    }
//                    System.out.println("CustId ==>>"+custId+"; AcNO:"+accno+"; Int: "+intAmt);
                } else {
                    intAmt = CbsUtil.round(balance * rdIntDetail.getRoi() / 1200, 0);
                }

                if (intAmt > 0) {
                    rdIntDetail.setInterest(intAmt);
                    double /*tds = 0d,*/ minorInt = 0d, majorInt = 0d;
//                            if (acWiseTdsList.size() > 0) {
//                                Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
//                                tds = Double.parseDouble(acWiseTdsVect.get(0).toString());
//                            }
                    String gurCustId = custId;
                    if (minorFlag.equalsIgnoreCase("Y")) {
                        List mjOMinFlagLst = em.createNativeQuery("select  ifnull(guardiancode,'0') from cbs_cust_minorinfo where customerid ='" + custId + "'").getResultList();
                        if (!mjOMinFlagLst.isEmpty()) {
                            for (int e = 0; e < mjOMinFlagLst.size(); e++) {
                                Vector mjOMinVec = (Vector) mjOMinFlagLst.get(e);
                                gurCustId = mjOMinVec.get(0).toString();
                                majorInt = majorInt + autoTermDepositFacade.getMajorOrMinorInt(accno, finStartDt, toDate);
                            }
                            if (pan.equalsIgnoreCase("N")) {
                                if (ParseFileUtil.isValidPAN(majorPan) == true) {
                                    pan = "Y";
                                } else {
                                    pan = "N";
                                }
                            }
                        }
                    } else if (!majorCustId.equalsIgnoreCase("")) {
                        System.out.println("MinorCustID==>" + custId + ": MajorCustId==>" + majorCustId);
                        List minorIntList = em.createNativeQuery("select sum(aa.interest) from "
                                + "(select ifnull(sum(interest),0) as interest from td_interesthistory where acno in "
                                + " (select ci.acno from customerid ci where ci.custid =" + majorCustId + " and substring(ci.acno,3,2) in "
                                + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                                + " and dt between '" + finStartDt + "' and '" + toDate + "' "
                                + "union all "
                                + "select ifnull(sum(interest),0) as interest from rd_interesthistory where acno in "
                                + " (select ci.acno from customerid ci where ci.custid = " + majorCustId + " and substring(ci.acno,3,2) in "
                                + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                                + " and dt between '" + finStartDt + "' and '" + toDate + "' "
                                + "union all "
                                + "select ifnull(sum(interest),0) as interest from dds_interesthistory where acno in "
                                + " (select ci.acno from customerid ci where ci.custid = " + majorCustId + " and substring(ci.acno,3,2) in "
                                + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                                + " and dt between '" + finStartDt + "' and '" + toDate + "') aa").getResultList();
                        majorInt = majorInt + Double.parseDouble(((Vector) minorIntList.get(0)).get(0).toString());
                        minorFlag = "Y";
                        if (ParseFileUtil.isValidPAN(majorPan) == true) {
                            pan = "Y";
                        } else {
                            pan = "N";
                        }
                    }
                    if (noOfMinorInMajor > 0) {
                        List majorFlagLst = em.createNativeQuery("select  ifnull(customerid,'0') from cbs_cust_minorinfo where guardiancode ='" + custId + "'").getResultList();
                        if (!majorFlagLst.isEmpty()) {
                            for (int e = 0; e < majorFlagLst.size(); e++) {
                                Vector mjOMinVec = (Vector) majorFlagLst.get(e);
                                String minorCustId = mjOMinVec.get(0).toString();
                                List minorIntList = em.createNativeQuery("select sum(aa.interest) from "
                                        + "(select ifnull(sum(interest),0) as interest from td_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                                        + " and dt between '" + finStartDt + "' and '" + toDate + "' "
                                        + "union all "
                                        + "select ifnull(sum(interest),0) as interest from rd_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                                        + " and dt between '" + finStartDt + "' and '" + toDate + "' "
                                        + "union all "
                                        + "select ifnull(sum(interest),0) as interest from dds_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                                        + " and dt between '" + finStartDt + "' and '" + toDate + "') aa").getResultList();

                                minorInt = minorInt + Double.parseDouble(((Vector) minorIntList.get(0)).get(0).toString());
                            }
                        }
                    }
                    rdIntDetail.setTotalInt(totalIntPaid);
                    rdIntDetail.setTotalTds(totalTds);
                    rdIntDetail.setTdsDeducted(tdsAcWise);
                    rdIntDetail.setTotalIntPaidVouchWise(intAcWise);
                    rdIntDetail.setMajorInterest(majorInt);
                    rdIntDetail.setUnRecoverTds(unRecTds);
                    rdIntDetail.setUnRecoverTdsCustId(unRecTdsCustIdWise);
                    rdIntDetail.setInterestWithMinMaj(totalIntPaid + minorInt + majorInt);
                    rdIntDetail.setMinorInterest(minorInt);
                    rdIntDetail.setMinorFlag(minorFlag.equalsIgnoreCase("Y") ? minorFlag : "");
                    rdIntDetail.setPan(pan);
                    rdIntDetail.setMajorCustId(gurCustId);
                    rdIntDetail.setPropCustId(propCustId);
                    rdIntDetail.setCloseAcTds(closeAcTds);
                    rdIntDetail.setCustType(custType);
                    rdIntDetail.setDob(dob);
                    rdIntDetail.setMajDob(majDob);
                    rdIntDetail.setCustEntityType(custEntityType);
                    rdIntList.add(rdIntDetail);
                }
            }
//            System.out.println("End>>>:" + new Date() + "; Branch:"+brCode+"; Size: " + rdIntList.size() + "; acctType:" + acType + "; intOpt:Q; FDate:" + fromDate + "; TDate:" + toDate);

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return rdIntList;
    }

    public List<TdIntDetail> rdProjectedIntCalc(String acType, String fromDate, String toDate, String brCode) throws ApplicationException {
        try {
            List<TdIntDetail> rdIntList = new ArrayList<TdIntDetail>();
            List accountList = em.createNativeQuery("select ci.custid,a.acno,a.custname,a.RDmatdate,a.rdinstal,a.intDeposit,a.openingdt, "
                    + "a.accstatus from accountmaster a, customerid ci where  a.acno = ci.acno and a.accttype='" + acType + "' and "
                    + "a.curbrcode = '" + brCode + "' and (closingdate is null or closingdate between '" + fromDate + "' and '" + toDate + "' )"
                    + "and a.tdsflag in ('Y') ").getResultList();

            TdIntDetail rdIntDetail;
            for (int j = 0; j < accountList.size(); j++) {
                Vector vect = (Vector) accountList.get(j);
                rdIntDetail = new TdIntDetail();

                rdIntDetail.setMsg("TRUE");

                rdIntDetail.setCustId(vect.get(0).toString());
                rdIntDetail.setAcno(vect.get(1).toString());
                rdIntDetail.setCustName(vect.get(2).toString());
                rdIntDetail.setVoucherNo(0);
                rdIntDetail.setIntOpt("Q");

                String rdMatDt = vect.get(3).toString();
                double rdInstall = Float.parseFloat(vect.get(4).toString());
                double roi = Float.parseFloat(vect.get(5).toString());
                String openingDt = vect.get(6).toString();
                int accStatus = Integer.parseInt(vect.get(7).toString());

                double interest = 0;
                if (accStatus == 9) {
                    List sbalList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from rdrecon where acno='" + vect.get(1).toString() + "' and dt "
                            + "between '" + fromDate + "' and '" + toDate + "' and trantype = 8").getResultList();
                    if (sbalList.size() > 0) {
                        Vector sbalVect = (Vector) sbalList.get(0);
                        interest = Float.parseFloat(sbalVect.get(0).toString());
                    }
                    if (interest > 0) {
                        rdIntDetail.setIntPaid(interest);
                        rdIntDetail.setStatus("C");
                        rdIntList.add(rdIntDetail);
                    }
                } else {
                    String frDt = fromDate;
                    String toDt = toDate;
                    if (ymd.parse(openingDt).getTime() > ymd.parse(fromDate).getTime()) {
                        frDt = openingDt;
                    }
                    if (ymd.parse(rdMatDt).getTime() < ymd.parse(toDate).getTime()) {
                        toDt = rdMatDt;
                    }

                    long vDays = CbsUtil.dayDiff(ymd.parse(frDt), ymd.parse(toDt));
                    if (vDays < 91) {
                        interest = 0;
                    }
                    float ip = 4;
                    double i = roi / ip;
                    float c = ip / 12;

                    double f = Math.pow((1 + i / 100), c) - 1;
                    double rdPrin = rdInstall;
                    double a1 = (Math.pow((1 + f), CbsUtil.monthDiff(ymd.parse(frDt), ymd.parse(toDt))) - 1) / f;
                    double a2 = a1 * (1 + f);

                    double matAmt = a2 * rdInstall;
                    interest = (matAmt - (rdPrin * CbsUtil.monthDiff(ymd.parse(frDt), ymd.parse(toDt))));
                    interest = CbsUtil.round(interest, 0);
                    if (interest > 0) {
                        rdIntDetail.setInterest(interest);
                        rdIntDetail.setStatus("A");
                        rdIntList.add(rdIntDetail);
                    }
                }
            }

            return rdIntList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<Double> getRdIntTds(String acNo, String frDt, String toDt, String brCode) throws ApplicationException {
        List<Double> result = new ArrayList<Double>();
        try {
            List mjOMinFlagLst = em.createNativeQuery("select b.customerid,minorflag from customerid a,cbs_customer_master_detail b where a.acno = '" + acNo + "' "
                    + "and a.custid = cast(b.customerid as unsigned) and SuspensionFlg not in('S','Y')").getResultList();
            if (mjOMinFlagLst.isEmpty()) {
                throw new ApplicationException("minorFlag is not Set For " + acNo);
            }
            Vector mjOMinVec = (Vector) mjOMinFlagLst.get(0);
            String custId = mjOMinVec.get(0).toString();
            String mFlag = mjOMinVec.get(1).toString();
            double totalIntAmt = 0d;
            double panId = 0;
//            List custIdList = em.createNativeQuery("select distinct guardiancode from cbs_cust_minorinfo where guardiancode = '" + custId + "' "
//                    + "union  "
//                    + "select customerid from cbs_cust_minorinfo where guardiancode = '" + custId + "'").getResultList();
            List custIdList = em.createNativeQuery("select guardiancode from cbs_cust_minorinfo where CustomerId = '" + custId + "'and guardiancode is not null and guardiancode <> ''\n"
                    + "union\n"
                    + "select  CustomerId from cbs_cust_minorinfo where guardiancode = (select guardiancode from cbs_cust_minorinfo where CustomerId = '" + custId + "' and guardiancode is not null and guardiancode <> '')\n"
                    + "union\n"
                    + "select distinct guardiancode from cbs_cust_minorinfo where guardiancode = '" + custId + "' \n"
                    + "union  \n"
                    + "select customerid from cbs_cust_minorinfo where guardiancode = '" + custId + "'").getResultList();

            if (custIdList.isEmpty()) {
                List totalIntList = em.createNativeQuery("select sum(intAmt) from( "
                        + "select custid,ifnull(sum(Interest),0) intAmt from td_interesthistory a,customerid b where a.acno = b.acno and custid = '" + custId + "' and dt between '" + frDt + "' and '" + toDt + "' "
                        + "union "
                        + "select custid,ifnull(sum(Interest),0) intAmt from rd_interesthistory a,customerid b where a.acno = b.acno and custid = '" + custId + "' and dt between '" + frDt + "' and '" + toDt + "' "
                        + "union "
                        + "select custid,ifnull(sum(Interest),0) intAmt from dds_interesthistory a,customerid b where a.acno = b.acno and custid = '" + custId + "' and dt between '" + frDt + "' and '" + toDt + "' "
                        + ")a").getResultList();
                Vector tVector = (Vector) totalIntList.get(0);
                double intAmt = Double.parseDouble(tVector.get(0).toString());
                totalIntAmt = totalIntAmt + intAmt;
            } else {
                for (int i = 0; i < custIdList.size(); i++) {
                    Vector cVector = (Vector) custIdList.get(i);
                    String cId = cVector.get(0).toString();
                    List totalIntList = em.createNativeQuery("select sum(intAmt) from( "
                            + "select custid,ifnull(sum(Interest),0) intAmt from td_interesthistory a,customerid b where a.acno = b.acno and custid = '" + cId + "' and dt between '" + frDt + "' and '" + toDt + "' "
                            + "union "
                            + "select custid,ifnull(sum(Interest),0) intAmt from rd_interesthistory a,customerid b where a.acno = b.acno and custid = '" + cId + "' and dt between '" + frDt + "' and '" + toDt + "' "
                            + "union "
                            + "select custid,ifnull(sum(Interest),0) intAmt from dds_interesthistory a,customerid b where a.acno = b.acno and custid = '" + cId + "' and dt between '" + frDt + "' and '" + toDt + "' "
                            + ")a").getResultList();
                    Vector tVector = (Vector) totalIntList.get(0);
                    double intAmt = Double.parseDouble(tVector.get(0).toString());
                    totalIntAmt = totalIntAmt + intAmt;
                }
                panId = 1;
            }
            double vchTds = 0d;
            double rdIntPaid = 0d;
            List vectObj2 = null;
            vectObj2 = em.createNativeQuery("select ifnull(sum(TDS),0) from tdshistory b ,accountmaster tdam where "
                    + "tdam.CurBrCode='" + brCode + "' and b.acno='" + acNo + "' and b.Acno = tdam.ACNO and b.fromdt between '" + frDt
                    + "' AND  '" + toDt + "'").getResultList();
            if (vectObj2.size() > 0) {
                Vector vect = (Vector) vectObj2.get(0);
                vchTds = Double.parseDouble(vect.get(0).toString());
            }
            vectObj2 = em.createNativeQuery("select ifnull(sum(Interest),0) from rd_interesthistory b ,accountmaster tdam where "
                    + "tdam.CurBrCode='" + brCode + "' and b.acno='" + acNo + "' and b.Acno = tdam.ACNO and b.dt between '" + frDt
                    + "' AND  '" + toDt + "'").getResultList();
            if (vectObj2.size() > 0) {
                Vector vect = (Vector) vectObj2.get(0);
                rdIntPaid = Double.parseDouble(vect.get(0).toString());
            }

            result.add(totalIntAmt);
            result.add(vchTds);
            result.add(rdIntPaid);
            result.add(panId);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return result;
    }

    public String saveRdrecord(double dint, double dprovamt, String datetmp, String orgbrnid,
            String destBrnid, Integer intOption, String enterby, String acno, String gpostcheck,
            double tdsToBeDeducted, double tdsDeducted, String formDt, String matDt,
            float netConRoi, double maturityAmt, float roi, float acROI, float penalty,
            double IntPaidCurrentYear, double closeActTdsToBeDeducted, double closeActTdsDeducted) throws ApplicationException {

        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            List acNoList = em.createNativeQuery("select * from td_payment_auth t where t.acno= '" + acno + "' and t.auth='N'").getResultList();
            if (!acNoList.isEmpty()) {
                ut.rollback();
                throw new ApplicationException("Account No. are pending for authorization of this customer. Please "
                        + "authorize these Account No. before making another payment for the same customer.");
            }
            List dtList = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and brncode='" + orgbrnid + "'").getResultList();
            Vector tempCurrent = (Vector) dtList.get(0);
            String curDt = tempCurrent.get(0).toString();
            String Status = "";
            if (intOption == 3) {
                Status = "P";
            } else if (intOption == 4) {
                Status = "M";
            } else {
                Status = "";
            }

            String query = "INSERT INTO td_payment_auth(ACNO,VoucherNo,ReceiptNo,IntOpt,Status,PrinAmt,RemainingInt,TdsToBeDed,TDSDeducted,IntPaid,FDDT,MatDt,"
                    + "FinalAmt,Penalty,ROI,ActualTotInt,EnterBy,Auth,AuthBy,TranTime,actualROI,NetConROI,ClActTdsToBeDeducted,ClActTdsDeducted,ClActTdsIntfinYear) "
                    + "VALUES('" + acno + "',0,0,'Q','" + Status + "'," + maturityAmt + ","
                    + dint + "," + tdsToBeDeducted + "," + tdsDeducted + "," + dprovamt + ",'" + formDt + "','" + matDt + "'," + 0 + "," + penalty
                    + "," + roi + "," + IntPaidCurrentYear + ",'" + enterby + "','N','',now()," + acROI + "," + netConRoi + "," + closeActTdsToBeDeducted + "," + closeActTdsDeducted
                    + ",0)";
            Query insertQuery = em.createNativeQuery(query);
            int varQ1 = insertQuery.executeUpdate();
            if (varQ1 <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String deleteRdrecord(String acNo) throws ApplicationException {
        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            String query = "delete from td_payment_auth where auth='N' and acno = '" + acNo + "'";
            int rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data deletion");
            }
            ut.commit();
            return "Account Detail successfully deleted.";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getRdVerifyDeleteAccountDetail(String acno, String brCode) throws ApplicationException {
        List resultList = new ArrayList();
        try {

            resultList = em.createNativeQuery("select DATE_FORMAT(CURDATE(),'%d/%m/%Y') S_Date ,DATE_FORMAT(a.RDmatdate,'%d/%m/%Y') RDmatdate,\n"
                    + "a.RDinstal,a.intDeposit,DATE_FORMAT(a.openingDT,'%d/%m/%Y') openingDT,c.custname,\n"
                    + "ROI ContractedROI,actualROI ApplicableRate,NetConROI NetContractedRoi,PrinAmt balance,RemainingInt IntAmtAfetrPenalty,IntPaid,\n"
                    + "TdsToBeDed,TDSDeducted,date_format(FDDT,'%d/%m/%Y') frDt,date_format(MatDt,'%d/%m/%Y') toDt,d.Penalty PenaltyAmt,"
                    + "ActualTotInt IntPaidCurrentYear,EnterBy,d.Status,ClActTdsToBeDeducted,ClActTdsDeducted from accountmaster a,\n"
                    + "customermaster c,td_payment_auth d where a.acno = '" + acno + "' and a.acno = d.acno\n"
                    + "and substring(a.acno,5,6)=c.custno and c.actype='" + acno.substring(2, 4) + "' and \n"
                    + "c.brncode = '" + brCode + "'").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return resultList;
    }

    public List getUnAuthRdAcNo(String orgBranch) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            resultList = em.createNativeQuery("select ACNO from td_payment_auth t where substring(t.acno,1,2) = '" + orgBranch + "' "
                    + "and t.auth='N' and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "'))").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultList;
    }

    public List isRdPaymentAcno(String acNo) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            resultList = em.createNativeQuery("select auth from td_payment_auth t where t.acno= '" + acNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultList;

    }

    public List<RdInterestDTO> individualRdIntCal(String acNo, String toDate) throws ApplicationException {
        try {
            List<RdInterestDTO> rdIntList = new ArrayList<RdInterestDTO>();
            List accountList = em.createNativeQuery("Select AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt, AM.RdInstal, sum(ifnull(RP.Balance,0)) as Balance,date_format(Rdmatdate,'%Y%m%d') rdMatDt \n"
                    + "From accountmaster AM, rd_product RP Where AM.Acno = '" + acNo + "' \n"
                    + "And AM.AccStatus<>9 and AM.Acno = RP.acno and AM.OpeningDt <='" + toDate + "' and RP.dt <='" + toDate + "' \n"
                    + "group by AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt,AM.RdInstal order by AM.acno").getResultList();

            if (accountList.isEmpty()) {
                throw new ApplicationException("There is no account for interest calculation");
            } else {
                RdInterestDTO rdIntDetail;
                for (int i = 0; i < accountList.size(); i++) {
                    Vector vect = (Vector) accountList.get(i);
                    rdIntDetail = new RdInterestDTO();

                    rdIntDetail.setAcNo(vect.get(0).toString());
                    rdIntDetail.setCustName(vect.get(1).toString());
                    rdIntDetail.setRoi(Double.parseDouble(vect.get(2).toString()));

                    rdIntDetail.setOpeningDt(vect.get(3).toString());
                    rdIntDetail.setInstallment(Double.parseDouble(vect.get(4).toString()));
                    rdIntDetail.setBalance(CbsUtil.round(Double.parseDouble(vect.get(5).toString()), 2));
                    rdIntDetail.setRdMatDt(vect.get(6).toString());

                    double intAmt = CbsUtil.round(rdIntDetail.getBalance() * rdIntDetail.getRoi() / 1200, 0);
                    if (intAmt > 0) {
                        rdIntDetail.setInterest(intAmt);
                        rdIntList.add(rdIntDetail);
                    }
                }
            }
            if (rdIntList.isEmpty()) {
                throw new ApplicationException("There is no account for interest calculation");
            } else {
                return rdIntList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List rdInterestCalculationProductWise(String rflag, Float totinstall, Float netConRoi, String toDt, String fromDt, String matDt,
            String acno, Float install, Float penaltyAmt, String rdMatDt, String provIntPaid) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            double interest = 0d;
            List rdList = em.createNativeQuery("select ifnull(sum(interest),0.0) from rd_interesthistory where acno = '" + acno + "'").getResultList();
            float totalIntPaid = 0f;
            if (rdList.size() > 0) {
                Vector rdVect = (Vector) rdList.get(0);
                totalIntPaid = Float.parseFloat(rdVect.get(0).toString());
            } else {
                totalIntPaid = 0;
            }
            // new code product wise int
            long tl = ymd.parse(toDt).getTime();
            long ml = ymd.parse(rdMatDt).getTime();
            if (tl == ml && rflag.equalsIgnoreCase("4")) {

                List rdIntList = rdIntCal(fromDt, matDt, netConRoi, install);
                if (rdIntList.size() > 0) {
                    interest = Double.parseDouble(rdIntList.get(1).toString());
                }
                // After maturity Date & till Date saving roi
                if (fts.getCodeForReportName("SAVING-INT-RDMAT") == 1) {
                    String intCode = fts.getCodeFromCbsParameterInfo("UND-INTEREST-CODE");
                    List<SavingIntRateChangePojo> resultListsb = sbRemote.getSavingRoiChangeDetail(intCode, rdMatDt, ymd.format(new Date()));
                    if (resultListsb.isEmpty()) {
                        throw new ApplicationException("There is no slab for saving interest calculation.");
                    }
                    List list1 = em.createNativeQuery("select cast(ifnull(balance,0) as decimal (14,2)) as balance from reconbalan where acno = '"
                            + acno + "'").getResultList();

                    Vector balVec = (Vector) list1.get(0);
                    double bal = Double.parseDouble(balVec.get(0).toString());
                    //  remainingInt = intAmt - intPaid;
                    double remainingInt = interest - Double.parseDouble(provIntPaid);
                    double rdTotalBal = bal + remainingInt;
                    for (int k = 0; k < resultListsb.size(); k++) {
                        SavingIntRateChangePojo obj = resultListsb.get(k);

                        String slabFrDt = obj.getFrDt();
                        String slabToDt = obj.getToDt();
                        double sbRoi = obj.getRoi();

                        Long savingDiff = CbsUtil.dayDiff(ymd.parse(slabFrDt), ymd.parse(slabToDt));
                        double savingInterest = 0;
                        if (savingDiff > 0) {
                            savingInterest = sbRoi * savingDiff.doubleValue() * rdTotalBal / 36500;
                        }
                        interest = interest + savingInterest;
                    }
                }
            } else if (tl == ml && rflag.equalsIgnoreCase("5")) {

                String toDtEnd = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(toDt.substring(4, 6)), Integer.parseInt(toDt.substring(0, 4))));
                List<RdInterestDTO> rdIntList = individualRdIntCal(acno, toDtEnd);
                double rdRoi = rdIntList.get(0).getRoi();
                double rdBal = rdIntList.get(0).getBalance();
                double intAmt = CbsUtil.round(rdBal * netConRoi / 1200, 0);
                interest = intAmt;

                List frdtList = em.createNativeQuery("select date_format(max(Dt),'%Y%m%d') frDt from rd_product where acno ='" + acno + "'").getResultList();
                Vector vtr = (Vector) frdtList.get(0);
                String frDt = vtr.get(0).toString();
                Float val[] = getRdProduct(acno, CbsUtil.dateAdd(frDt, 1), CbsUtil.dateAdd(toDt, -1));
                float rdPbal = val[0];
                float days = val[1];
                double rdProduct = rdPbal * days;
                intAmt = CbsUtil.round(rdProduct * netConRoi / 36500, 0);
                interest = interest + intAmt;
                // After maturity Date & till Date saving roi
                if (fts.getCodeForReportName("SAVING-INT-RDMAT") == 1) {
                    String intCode = fts.getCodeFromCbsParameterInfo("UND-INTEREST-CODE");
                    List<SavingIntRateChangePojo> resultListsb = sbRemote.getSavingRoiChangeDetail(intCode, rdMatDt, ymd.format(new Date()));
                    if (resultListsb.isEmpty()) {
                        throw new ApplicationException("There is no slab for saving interest calculation.");
                    }
                    List list1 = em.createNativeQuery("select cast(ifnull(balance,0) as decimal (14,2)) as balance from reconbalan where acno = '"
                            + acno + "'").getResultList();

                    Vector balVec = (Vector) list1.get(0);
                    double bal = Double.parseDouble(balVec.get(0).toString());
                    //  remainingInt = intAmt - intPaid;
                    double remainingInt = interest - Double.parseDouble(provIntPaid);
                    double rdTotalBal = bal + remainingInt;
                    for (int k = 0; k < resultListsb.size(); k++) {
                        SavingIntRateChangePojo obj = resultListsb.get(k);

                        String slabFrDt = obj.getFrDt();
                        String slabToDt = obj.getToDt();
                        double sbRoi = obj.getRoi();

                        Long savingDiff = CbsUtil.dayDiff(ymd.parse(slabFrDt), ymd.parse(slabToDt));
                        double savingInterest = 0;
                        if (savingDiff > 0) {
                            savingInterest = sbRoi * savingDiff.doubleValue() * rdTotalBal / 36500;
                        }
                        interest = interest + savingInterest;
                    }
                }
            } else {

                List<RdInterestDTO> rdIntList = individualRdIntCal(acno, toDt);
                double rdRoi = rdIntList.get(0).getRoi();
                double rdBal = rdIntList.get(0).getBalance();
                double intAmt = CbsUtil.round(rdBal * netConRoi / 1200, 0);
                interest = interest + intAmt;
                List frdtList = em.createNativeQuery("select date_format(max(Dt),'%Y%m%d') frDt from rd_product where acno ='" + acno + "'").getResultList();
                Vector vtr = (Vector) frdtList.get(0);
                String frDt = vtr.get(0).toString();
                Float val[] = getRdProduct(acno, CbsUtil.dateAdd(frDt, 1), CbsUtil.dateAdd(toDt, -1));
                float rdPbal = val[0];
                float days = val[1];
                double rdProduct = rdPbal * days;

                intAmt = CbsUtil.round(rdProduct * netConRoi / 36500, 0);
                interest = interest + intAmt;
            }
            //End new code product wise int

            double intBefore = interest;
            if (penaltyAmt > 0) {
                if (interest < penaltyAmt) {
                    throw new ApplicationException("Interest Amount is Less then Penalty Amount. Please Verify the Penalty Amount");
                }
                interest = interest - penaltyAmt;
            }

            double intAfter = interest;
            intAfter = CbsUtil.round(intAfter, 0);
            intBefore = CbsUtil.round(intBefore, 0);


            String finYr = autoTermDepositFacade.getFinYear(acno.substring(0, 2));
            String finfrmDT = finYr + "0401";
            String finToDT = (Integer.parseInt(finYr) + 1) + "0331";

            List<Double> list = getRdIntTds(acno, finfrmDT, finToDT, acno.substring(0, 2));
            double totalIntAmt = list.get(0);
            double vTds = list.get(1);
            double rdIntPaid = list.get(2);
            double panId = list.get(3);
            double tdsToBe = 0d;
            double intAmtFortds = 0d, intAmtForTdsCal = 0;
            double closeAcctTdsDeduted = 0d;
            intAmtFortds = rdIntPaid + intAfter;
            String strResult = autoTermDepositFacade.getTdsRateAndApplicableAmt(acno, toDt);
            String[] strRsArr = strResult.split(":");
            String tdsFlag = strRsArr[0];
            float tdsRate = Float.parseFloat(strRsArr[1]);
            float tdsApplicableAmt = Float.parseFloat(strRsArr[2]);
            if (panId == 1) { // Checking Minor/ Major Id
                String panNo = "";
                panNo = getMinorMajorPanId(acno);
                if (panNo.equalsIgnoreCase("")) {
                    tdsRate = 20;
                }
            }
            double remainingInt = intAfter - totalIntPaid;
            totalIntAmt = totalIntAmt + remainingInt;
            intAmtForTdsCal = rdIntPaid + remainingInt;
            if (totalIntAmt >= tdsApplicableAmt && tdsFlag.equalsIgnoreCase("Y")) {
                // tdsToBe = (rdIntPaid * tdsRate) / 100;
                tdsToBe = (intAmtForTdsCal * tdsRate) / 100;
                tdsToBe = tdsToBe - vTds;
            }

            double closeFinYearIntOfCustomer = 0;
            double closeFinYearTdsRecovered = 0;
            double closeAcctTdsToBeDeduted = 0d;

            if (autoTermDepositFacade.getCustomerFinYearTds(acno, finfrmDT, finToDT, "R", "") == 0) {
                double customerFinYearInt = autoTermDepositFacade.getFinYearIntOfCustomer(acno, finfrmDT, finToDT, "") + remainingInt;
                if (customerFinYearInt >= tdsApplicableAmt && tdsFlag.equalsIgnoreCase("Y")) {
                    double closeFinYearCalculatedTds = 0;
                    closeFinYearIntOfCustomer = autoTermDepositFacade.getFinYearIntOfCustomer(acno, finfrmDT, finToDT, "Y");
                    closeFinYearCalculatedTds = Math.round(closeFinYearIntOfCustomer * tdsRate / 100);
                    closeFinYearTdsRecovered = autoTermDepositFacade.getCustomerFinYearTds(acno, finfrmDT, finToDT, "R", "Y");
                    closeAcctTdsToBeDeduted = closeFinYearCalculatedTds - closeFinYearTdsRecovered;
                }
            } else {
                closeAcctTdsToBeDeduted = autoTermDepositFacade.getCustomerFinYearTds(acno, finfrmDT, finToDT, "NR", "Y");
                //closeAcctTdsDeduted = autoTermDepositFacade.getCustomerFinYearTds(acno, finfrmDT, finToDT, "R", "Y");
            }

            resultList.add(intBefore);
            resultList.add(intAfter);
            tdsToBe = CbsUtil.round(tdsToBe, 0);
            if (tdsToBe < 0) {
                tdsToBe = 0;
            }
            resultList.add(CbsUtil.round(tdsToBe, 0));
            resultList.add(vTds);
            resultList.add(rdIntPaid);
            resultList.add(CbsUtil.round(closeAcctTdsToBeDeduted, 0));
            resultList.add(CbsUtil.round(closeFinYearTdsRecovered, 0));

            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private String getMinorMajorPanId(String acNo) throws ApplicationException {
        String panNo = "";
        try {
            List panList = em.createNativeQuery("select PAN_GIRNumber,DateOfBirth from cbs_customer_master_detail a,(select cId from(\n"
                    + "select guardiancode cId from cbs_cust_minorinfo where CustomerId = (select custid from customerid where acno = '" + acNo + "') and guardiancode is not null and guardiancode <> ''\n"
                    + "union\n"
                    + "select guardiancode cId from cbs_cust_minorinfo where guardiancode = (select custid from customerid where acno = '" + acNo + "')\n"
                    + ")a)b where a.customerid = b.cId and (PAN_GIRNumber REGEXP '[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}')").getResultList();
            if (!panList.isEmpty()) {
                Vector panVector = (Vector) panList.get(0);
                panNo = panVector.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return panNo;
    }

    public Float[] getRdProduct(String acNo, String fromDate, String toDate) throws ApplicationException {
        String acno, openingdt, rdmatdt;
        float balance, days, interest;
        Long cal1 = Long.parseLong(fromDate);
        Long cal2 = Long.parseLong(toDate);
        Float rdArr[] = new Float[2];
        try {
            List list1 = em.createNativeQuery("select r.acno,ifnull(sum(ifnull(r.cramt,0)),0) - ifnull(sum(ifnull(r.dramt,0)),0),"
                    + "DATE_FORMAT(a.openingdt,'%Y%m%d'),DATE_FORMAT(a.rdmatdate,'%Y%m%d'),ifnull(result.interest,0)from rdrecon  r "
                    + "left join accountmaster a on r.acno=a.acno left join (select acno,sum(interest) as interest from rd_interesthistory "
                    + "where todt <= '" + toDate + "' group by acno) as result on result.acno=r.acno where a.accstatus <> 9 and "
                    + "r.dt<='" + toDate + "' and r.trantype <> 8 and r.acno ='" + acNo + "' group by r.acno,a.openingdt,"
                    + "a.rdmatdate,result.interest").getResultList();
            if (!list1.isEmpty()) {
                for (int i = 0; i < list1.size(); i++) {
                    Vector v1 = (Vector) list1.get(i);
                    acno = v1.get(0).toString();

                    balance = Float.parseFloat(v1.get(1).toString());
                    openingdt = v1.get(2).toString();
                    rdmatdt = v1.get(3).toString();
                    interest = Float.parseFloat(v1.get(4).toString());

                    Long cal3 = Long.parseLong(openingdt);
                    Long cal4 = Long.parseLong(rdmatdt);
                    if (cal3.compareTo(cal1) < 0 || cal3.compareTo(cal1) == 0) {
                        if (cal4.compareTo(cal1) < 0 || cal4.compareTo(cal1) == 0) {
                            days = 0;
                        } else if (cal4.compareTo(cal1) > 0 && (cal4.compareTo(cal2) < 0 || cal4.compareTo(cal2) == 0)) {
                            days = CbsUtil.dayDiff(ymd.parse(fromDate), ymd.parse(rdmatdt));
                        } else if (cal4.compareTo(cal2) > 0) {
                            days = CbsUtil.datePart("D", toDate);
                        } else {
                            days = 0;
                        }
                    } else if (cal3.compareTo(cal1) > 0 && (cal3.compareTo(cal2) < 0 || cal3.compareTo(cal2) == 0)) {
                        if (cal4.compareTo(cal2) < 0 || cal4.compareTo(cal2) == 0) {
                            days = CbsUtil.dayDiff(ymd.parse(fromDate), ymd.parse(rdmatdt));
                        } else if (cal4.compareTo(cal2) > 0) {
                            days = CbsUtil.dayDiff(ymd.parse(openingdt), ymd.parse(toDate));
                        } else {
                            days = 0;
                        }
                    } else {
                        days = 0;
                    }
                    if (balance < 0) {
                        balance = 0;
                    }
                    if (days != 0) {
                        balance = balance + interest;
                    } else {
                        balance = 0;
                    }
                    rdArr[0] = balance;
                    rdArr[1] = days;
                }
            }
            return rdArr;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
