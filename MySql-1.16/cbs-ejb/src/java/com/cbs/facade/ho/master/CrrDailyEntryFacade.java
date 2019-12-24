/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
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
 * @author admin
 */
@Stateless(mappedName = "CrrDailyEntryFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CrrDailyEntryFacade implements CrrDailyEntryFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();
    //private int gno;

    /**
     *
     * @return @throws ApplicationException Start CRR Daily Updation Master
     */
    public List getTableDetails() throws ApplicationException {
        try {
            return em.createNativeQuery("select acdesc from ho_dailyupdentry").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveRecordCrrDaily(String acDescription, String acNo, String BalTp) throws ApplicationException {
        String message = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (acNo.equals(BalTp)) {
                ut.rollback();
                throw new ApplicationException("From GL Head and To GL Head cannot be same !");
            }
            List l2 = em.createNativeQuery("select * from ho_dailyupdm where Facno='" + acNo + "' and Tacno='" + BalTp + "'").getResultList();
            if (!l2.isEmpty()) {
                throw new ApplicationException("Entered GL Head has been already used, select different GL Heads !");
            }
            List l1 = em.createNativeQuery("select * from ho_dailyupdm where acdesc='" + acDescription + "' and Facno='" + acNo + "' and Tacno='" + BalTp + "'").getResultList();
            if (!l1.isEmpty()) {
                throw new ApplicationException("There is duplicate entry in Ho_Dailyupdm table !");
            }
            Query insertRecon = em.createNativeQuery("insert into ho_dailyupdm(acdesc,Facno,Tacno) values('" + acDescription + "','" + acNo + "','" + BalTp + "')");
            int result = insertRecon.executeUpdate();
            if (result > 0) {
                ut.commit();
                message = "Data has been saved successfully !";
            } else {
                throw new ApplicationException("Data was not saved !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return message;
    }

    public List gridLoad(String acdesc) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from ho_dailyupdm where acdesc='" + acdesc + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getAcName(String acNo) throws ApplicationException {
        String glAcno = "";
        try {
            List acNameList = em.createNativeQuery("select acname from gltable where acno='" + acNo + "'").getResultList();
            if (!acNameList.isEmpty()) {
                Vector acnoVec = (Vector) acNameList.get(0);
                glAcno = acnoVec.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return glAcno;
    }

    public List beforeUpdate(String acName, String facNo, String tacNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from ho_dailyupdm where acdesc='" + acName + "' and Facno='" + facNo + "' and Tacno='" + tacNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteRecord(String acdesc, String Facno, String Tacno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            Query insertRecon = em.createNativeQuery("delete from ho_dailyupdm where acdesc='" + acdesc + "' and FACNO ='" + Facno + "' AND TACNO = '" + Tacno + "'");
            int result = insertRecon.executeUpdate();
            if (result < 1) {
                throw new ApplicationException("No data corresponding to this account description !");
            } else {
                ut.commit();
                msg = "Data has been successfully deleted !";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return msg;
    }

    public String update(String acName, String facno, String tacno, String newFacno, String newTacno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            Query insertRecon = em.createNativeQuery("update ho_dailyupdm set facno='" + facno + "' ,tacno='" + tacno + "' where acdesc='" + acName + "'  AND FACNO='" + newFacno + "' AND TACNO='" + newTacno + "'");
            int result = insertRecon.executeUpdate();
            if (result < 1) {
                ut.rollback();
                throw new ApplicationException("transaction has been rollbacked !");
            } else {
                ut.commit();
                msg = "Data has been successfully updated !";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return msg;
    }

    public List fillAcDescAndAlternateColumn() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acdesc),gno from ho_crr_form9 where ifnull(acdesc,'')<>'' order by gno,acdesc").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List acnoItem() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT DISTINCT(ACNAME),SUBSTRING(ACNO,3,8) FROM gltable ORDER BY ACNAME,SUBSTRING(ACNO,3,8)").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getGnoAndFlag(String acdesc) throws ApplicationException {
        try {
            return em.createNativeQuery("select gno,flag1 from ho_crr_form9 where acdesc='" + acdesc + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveIndivisual(String acdesc, String acnoItem, String reportView, String altOption, String altColValue) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        Integer gno = 0, flag = 0, chkgno = 0;
        List result = new ArrayList();
        try {
            ut.begin();
            int firstHifenIndex = acnoItem.indexOf("*");
            String acnoItemDesc = acnoItem.substring(0, firstHifenIndex);
            String acnoItemAccount = acnoItem.substring(firstHifenIndex + 3);

            Query q4 = null;
            if (altOption.equalsIgnoreCase("Non-Alternate")) {
                result = em.createNativeQuery("select tacno,facno,acdesc from ho_crr_form91 where tacno='" + acnoItemAccount + "' and facno='" + acnoItemAccount + "' and acdesc ='" + acdesc + "'").getResultList();
                if (!result.isEmpty()) {
                    throw new ApplicationException("Account number already exist !");
                }

                result = getGnoAndFlag(acdesc);
                Vector element = (Vector) result.get(0);
                chkgno = Integer.parseInt(element.get(0).toString());
                flag = Integer.parseInt(element.get(1).toString());

                if (reportView.trim().equalsIgnoreCase("Not Visible On Report")) {
                    q4 = em.createNativeQuery("insert into ho_crr_form91 (facno,tacno,acdesc,amt,gno,flag1,flag2,subtotal,altercol) values ('" + acnoItemAccount + "','" + acnoItemAccount + "', '" + acdesc + "',0," + chkgno + "," + flag + ",0,0,0)");
                } else if (reportView.trim().equalsIgnoreCase("Visible On Report")) {
                    q4 = em.createNativeQuery("insert into ho_crr_form91(facno,tacno,acdesc,amt,gno,flag1,flag2,subtotal,altercol) values ('" + acnoItemAccount + "','" + acnoItemAccount + "','" + acnoItemDesc + "',0," + chkgno + "," + flag + ",0,0,0)");
                }
            } else if (altOption.equalsIgnoreCase("Alternate")) {
                result = getGnoAndFlag(altColValue);
                Vector element = (Vector) result.get(0);
                gno = Integer.parseInt(element.get(0).toString());

                result = em.createNativeQuery("select tacno,facno,acdesc from ho_crr_form91 where tacno='" + acnoItemAccount + "' and facno='" + acnoItemAccount + "' and acdesc ='" + acdesc + "' and altercol=" + gno + "").getResultList();
                if (!result.isEmpty()) {
                    throw new ApplicationException("Account number already exist !");
                }
                q4 = em.createNativeQuery("update ho_crr_form91 set altercol= " + gno + " where acdesc='" + acdesc + "' and facno='" + acnoItemAccount + "'");
            }

            int queryResult = q4.executeUpdate();
            if (queryResult > 0) {
                ut.commit();
                return "true";
            } else {
                throw new ApplicationException("Data was not saved !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String deleteIndivisual(String acDesc, String accountNo, String accountDesc, String username) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List resultList = new ArrayList();
        Integer gno = 0;
        try {
            ut.begin();
            resultList = getGnoAndFlag(acDesc);
            Vector element = (Vector) resultList.get(0);
            gno = Integer.parseInt(element.get(0).toString());

            resultList = em.createNativeQuery("select tacno,gno from ho_crr_form91 where tacno='" + accountNo + "' and gno=" + gno + "").getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Account number does not exist !");
            }

            Query insertQuery = em.createNativeQuery("insert into ho_crr_form91_his(facno,tacno,acdesc,amt,gno,flag1,flag2,subtotal,altercol) select facno,tacno,acdesc,amt,gno,flag1,flag2,subtotal,altercol from ho_crr_form91 where tacno='" + accountNo + "'");
            int a = insertQuery.executeUpdate();
            if (a <= 0) {
                throw new ApplicationException("Unable to maintain history before deletion !");
            }

            Query deleteQuery = em.createNativeQuery("delete from ho_crr_form91 where tacno='" + accountNo + "'and gno=" + gno + "");
            a = deleteQuery.executeUpdate();
            if (a > 0) {
                ut.commit();
                return "true";
            } else {
                throw new ApplicationException("Record is not successfully deleted !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String saveSeries(String acdesc, String fromHead, String toHead, String isAlternate, String alternateValue) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List resultList = new ArrayList();
        String message = "";
        Integer acdescGno = 0, gno = 0, flag = 0;
        try {
            ut.begin();

            if (isAlternate.equalsIgnoreCase("Non-Alternate")) {
                resultList = getGnoAndFlag(acdesc);
                Vector element = (Vector) resultList.get(0);
                acdescGno = Integer.parseInt(element.get(0).toString());
                flag = Integer.parseInt(element.get(1).toString());

                resultList = em.createNativeQuery("SELECT * FROM ho_crr_form91 WHERE FACNO='" + fromHead + "' AND TACNO='" + toHead + "' AND ACDESC='" + acdesc + "' AND GNO=" + acdescGno + "").getResultList();
                if (!resultList.isEmpty()) {
                    throw new ApplicationException("Account number already exist !");
                }

                Query query = em.createNativeQuery("INSERT INTO ho_crr_form91(FACNO,TACNO,ACDESC,GNO,FLAG1,FLAG2,SUBTOTAL,ALTERCOL) VALUES ('" + fromHead + "','" + toHead + "','" + acdesc + "'," + acdescGno + "," + flag + ",0,0,0)");
                int a = query.executeUpdate();
                if (a > 0) {
                    ut.commit();
                    message = "true";
                } else {
                    throw new ApplicationException("Data was not saved properly !");
                }
            } else if (isAlternate.equalsIgnoreCase("Alternate")) {
                resultList = getGnoAndFlag(alternateValue);
                Vector element = (Vector) resultList.get(0);
                gno = Integer.parseInt(element.get(0).toString());

                resultList = em.createNativeQuery("SELECT * FROM ho_crr_form91 WHERE FACNO='" + fromHead + "' AND TACNO='" + toHead + "' AND ACDESC='" + acdesc + "' AND GNO=" + acdescGno + " AND ALTERCOL=" + gno + "").getResultList();
                if (!resultList.isEmpty()) {
                    throw new ApplicationException("Account number already exist !");
                }

                Query query = em.createNativeQuery("UPDATE ho_crr_form91 SET ALTERCOL= " + gno + " WHERE ACDESC='" + acdesc + "' AND FACNO='" + fromHead + "' AND TACNO='" + toHead + "'");
                int a = query.executeUpdate();
                if (a > 0) {
                    ut.commit();
                    message = "true";
                } else {
                    throw new ApplicationException("Data was not updated properly !");
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return message;
    }

    public String deleteSeries(String acdesc, String fromHead, String toHead) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List resultList = new ArrayList();
        try {
            ut.begin();
            Query query;
            if (fromHead == null || fromHead.equals("") || toHead == null || toHead.equalsIgnoreCase("")) {
                resultList = em.createNativeQuery("SELECT ACDESC FROM ho_crr_form91 WHERE ACDESC='" + acdesc + "'").getResultList();
                if (resultList.isEmpty()) {
                    throw new ApplicationException("Account does not exist !");
                }
                query = em.createNativeQuery("DELETE FROM ho_crr_form91 WHERE ACDESC='" + acdesc + "'");
            } else {
                resultList = em.createNativeQuery("SELECT ACDESC FROM ho_crr_form91 WHERE ACDESC='" + acdesc + "' AND FACNO='" + fromHead + "' AND TACNO='" + toHead + "'").getResultList();
                if (resultList.isEmpty()) {
                    throw new ApplicationException("Account does not exist !");
                }
                query = em.createNativeQuery("DELETE FROM ho_crr_form91 WHERE ACDESC='" + acdesc + "' AND FACNO='" + fromHead + "' AND TACNO='" + toHead + "'");
            }

            int a = query.executeUpdate();
            if (a > 0) {
                ut.commit();
                return "true";
            } else {
                throw new ApplicationException("Data was not deleted properly !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String dailyUpdate(String fromDt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query updtTbl = em.createNativeQuery("Update ho_dailyupdm Set Amt =0");
            Integer countRow = updtTbl.executeUpdate();

            Query updtTbl1 = em.createNativeQuery("Update ho_dailyupdm Set Amt = (Select Sum (ifnull(cash_cr_amt,0))-Sum(ifnull(cash_dr_Amt,0)) From ho_consolidate_entry Where substring(Acno,3,8) Between ho_dailyupdm.FAcno and ho_dailyupdm.TAcno And Dt ='" + fromDt + "') where acdesc<>'cadl' and acdesc<>'ca_sbi' and  acdesc<>'othertl' and  acdesc<>'fdtl'");
            Integer countRow1 = updtTbl1.executeUpdate();

            Query updtTbl2 = em.createNativeQuery("Update ho_dailyupdm Set Amt = (Select sum(ifnull(cash_cr_amt,0)) From ho_consolidate_entry Where substring(Acno,3,8) Between ho_dailyupdm.FAcno and ho_dailyupdm.TAcno And Dt='" + fromDt + "') where acdesc='CADL'");
            Integer countRow2 = updtTbl2.executeUpdate();

            Query updtTbl3 = em.createNativeQuery("Update ho_dailyupdm Set Amt = (Select sum(ifnull(cash_cr_amt,0)) From ho_consolidate_entry Where substring(Acno,3,8) Between ho_dailyupdm.FAcno and ho_dailyupdm.TAcno And Dt='" + fromDt + "') where acdesc='CADL'");
            Integer countRow3 = updtTbl3.executeUpdate();

            Query updtTbl4 = em.createNativeQuery("Update ho_dailyupdm Set Amt = (Select sum(ifnull(cash_dr_Amt,0)) From ho_consolidate_entry Where substring(Acno,3,8) Between ho_dailyupdm.FAcno and ho_dailyupdm.TAcno And Dt ='" + fromDt + "') where acdesc='CA_SBI'");
            Integer countRow4 = updtTbl4.executeUpdate();

            Query updtTbl5 = em.createNativeQuery("Update ho_dailyupdm Set Amt = (Select sum(ifnull(cash_cr_amt,0)) From ho_consolidate_entry Where substring(Acno,3,8) Between ho_dailyupdm.FAcno and ho_dailyupdm.TAcno And Dt ='" + fromDt + "') where acdesc='BORROWDL'");
            Integer countRow5 = updtTbl5.executeUpdate();

            Query updtTbl6 = em.createNativeQuery("Update ho_dailyupdm Set Amt = (Select Sum (ifnull(cash_cr_amt,0))-Sum(ifnull(cash_dr_Amt,0)) From ho_consolidate_entry Where substring(Acno,3,8) Between ho_dailyupdm.FAcno and ho_dailyupdm.TAcno and Dt ='" + fromDt + "') where acdesc='othertl'");
            Integer countRow6 = updtTbl6.executeUpdate();

            Query updtTbl7 = em.createNativeQuery("Update ho_dailyupdm Set Amt = (Select Sum (ifnull(cash_cr_amt,0))-Sum(ifnull(cash_dr_Amt,0)) From ho_consolidate_entry Where substring(Acno,3,8) Between ho_dailyupdm.FAcno and ho_dailyupdm.TAcno and Dt ='" + fromDt + "') where acdesc='fdtl'");
            Integer countRow7 = updtTbl7.executeUpdate();
            if (countRow > 0 && countRow1 > 0 && countRow2 > 0 && countRow3 > 0 && countRow4 > 0 && countRow5 > 0 && countRow6 > 0 && countRow7 > 0) {
                ut.commit();
            } else {
                throw new ApplicationException("updation problem in HO_Dailyupdm table !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return "true";
    }

    public List getDateData() throws ApplicationException {
        try {
            return em.createNativeQuery("Select DATE_FORMAT(max(RepFriDate),'%d/%m/%Y') From ho_reportingfriday where balance<>0").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String dataUpdate(String fromDt, String toDt, String enterBy, String orgbrcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String yearbalDt = "", yearbalFromHead = "", yearbalToHead = "";
            Float yearbalNetAmt = 0f;

            List InvestList = em.createNativeQuery("select * From ho_invest_yearbeg Where dt in (select max(dt) From ho_invest_yearbeg Where Dt<= '" + fromDt + "')").getResultList();
            if (InvestList.isEmpty()) {
                yearbalDt = "19000101";
                yearbalFromHead = "06003108";
                yearbalToHead = "06003108";
                yearbalNetAmt = 0f;
            } else {
                Vector vrForInvestList = (Vector) InvestList.get(0);
                yearbalDt = vrForInvestList.get(0).toString();
                yearbalFromHead = vrForInvestList.get(1).toString();
                yearbalToHead = vrForInvestList.get(2).toString();
                yearbalNetAmt = Float.parseFloat(vrForInvestList.get(3).toString()) - Float.parseFloat(vrForInvestList.get(4).toString());
            }
            /**
             * Get the Max RepFriDate from HO_ReportingFriday
             */
            List RepDTList = em.createNativeQuery("Select DATE_FORMAT(max(RepFriDate),'%Y%m%d') From ho_reportingfriday Where balance<>0").getResultList();
            if (RepDTList.isEmpty()) {
                throw new ApplicationException("Corrupt data in reportingfriday, with minimum friday balance 0");
            }
            Vector vrForRepDTList = (Vector) RepDTList.get(0);
            String chkTransdate = vrForRepDTList.get(0).toString();
            /**
             * Get the Difference of Passed To Date With the Current Date
             */
            Date curDt = ymd.parse(ymd.format(dt));
            if (curDt.compareTo(ymd.parse(toDt)) < 0) {
                throw new ApplicationException("Entered To Date cannot be greater than system date !");
            }
            /**
             * Get the Max RepFriDate from HO_ReportingFriday
             */
            if (ymd.parse(toDt).compareTo(ymd.parse(chkTransdate)) > 14) {
                throw new ApplicationException("There is more than 14 days difference between max friday and to date !");
            }

            /**
             * Validation of From Date with To Date
             */
            if (ymd.parse(toDt).compareTo(ymd.parse(fromDt)) < 0) {
                throw new ApplicationException("From Date cannot be greater than To Date !");
            }
            /**
             * Validation of month of both from date and to date that should be
             * of the same month.
             */
            Integer frMon = CbsUtil.datePart("M", fromDt);
            Integer toMon = CbsUtil.datePart("M", toDt);
            Integer frYr = CbsUtil.datePart("Y", fromDt);
            Integer toYr = CbsUtil.datePart("Y", toDt);
            if ((toMon != frMon) || (toYr != frYr)) {
                throw new ApplicationException("Please enter dates between a month !");
            }

            for (Integer i = 0; i <= 32; i++) {
                String mydate = CbsUtil.dateAdd(fromDt, i);
                /**
                 * Validation of From Date with To Date.
                 */
                long dtDiffComp = CbsUtil.dayDiff(ymd.parse(mydate), ymd.parse(toDt));
                if (dtDiffComp < 0) {
                    ut.commit();
                    return "Updation has been completed !";
                }

                String result = dailyUpdate(mydate, enterBy);
                if (result.equalsIgnoreCase("true")) {
                    List shPTpList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='borrowtl'").getResultList();
                    Vector vrshPTp = (Vector) shPTpList.get(0);
                    Double amt9 = Double.parseDouble(vrshPTp.get(0).toString());
                    Double balance = (Double) 0.0;
                    Double borrowtlAmt = (Double) 0.0;
                    Double fdtlAmt = (Double) 0.0;
                    Double othertlAmt = (Double) 0.0;
                    Double balance16 = (Double) 0.0;
                    Double crtotal = (Double) 0.0;
                    Double balance23 = (Double) 0.0;
                    Double balance25 = (Double) 0.0;

                    if (Math.signum(amt9) == -1.0) {
                        crtotal = crtotal + amt9;
                        borrowtlAmt = Math.abs((crtotal) / 1000);
                    }

                    List bnlDlOthList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='bankdlothers'").getResultList();
                    Vector vrbnlDlOth = (Vector) bnlDlOthList.get(0);
                    Double amt1 = Double.parseDouble(vrbnlDlOth.get(0).toString());
                    Double balance1 = Math.abs(amt1 / 1000);
                    Double bankDlOthers = balance1;

                    List caDlList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='cadl'").getResultList();
                    Vector vrcaDl = (Vector) caDlList.get(0);
                    Double amt3 = Double.parseDouble(vrcaDl.get(0).toString());
                    Double caDlamt = Math.abs(amt3 / 1000);

                    List sbTlList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='sbtl'").getResultList();
                    Vector vrsbTl = (Vector) sbTlList.get(0);
                    Double amt8 = Double.parseDouble(vrsbTl.get(0).toString());
                    Double sbtlAmt = amt8;

                    List sbDlList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='SBDL'").getResultList();
                    Vector vrsbDl = (Vector) sbDlList.get(0);
                    Double amt4 = Double.parseDouble(vrsbDl.get(0).toString());
                    Double sbdlAmt = Math.abs(amt4 / 1000);

                    List borWlList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='borrowdl'").getResultList();
                    Vector vrborWl = (Vector) borWlList.get(0);
                    Double amt5 = Double.parseDouble(vrborWl.get(0).toString());
                    Double borrowdlAmt = Math.abs(amt5 / 1000);

                    List othDlList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='otherdl'").getResultList();
                    Vector vrothDl = (Vector) othDlList.get(0);
                    Double amt6 = Double.parseDouble(vrothDl.get(0).toString());
                    Double otherdlAmt = Math.abs(amt6 / 1000);

                    List fdTlList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='fdtl'").getResultList();
                    Vector vrfdTl = (Vector) fdTlList.get(0);
                    Double amt7 = Double.parseDouble(vrfdTl.get(0).toString());

                    if ((amt7) <= 0.0) {
                        fdtlAmt = 0.0;
                    } else {
                        fdtlAmt = Math.abs(amt7 / 1000);
                    }

                    List othTlList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='othertl'").getResultList();
                    Vector vrothTl = (Vector) othTlList.get(0);
                    Double amt10 = Double.parseDouble(vrothTl.get(0).toString());

                    if ((amt10) <= 0.0) {
                        othertlAmt = 0.0;
                    } else {
                        othertlAmt = Math.abs(amt10 / 1000);
                    }

                    List caSbiList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='ca_sbi'").getResultList();
                    Vector vrcaSbi = (Vector) caSbiList.get(0);
                    Double amt11 = Double.parseDouble(vrcaSbi.get(0).toString());
                    Double caSbiAmt = Math.abs(amt11 / 1000);

                    List othSbiList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='other_sbi'").getResultList();
                    Vector vrothSbi = (Vector) othSbiList.get(0);
                    Double amt12 = Double.parseDouble(vrothSbi.get(0).toString());
                    Double otherSbiAmt = Math.abs(amt12 / 1000);

                    List callMList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='callmoney'").getResultList();
                    Vector vrcallM = (Vector) callMList.get(0);
                    Double amt13 = Double.parseDouble(vrcallM.get(0).toString());
                    Double callMoneyAmt = Math.abs(amt13 / 1000);

                    Double balance14 = 0.0;

                    List othAstList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='otherassets'").getResultList();
                    Vector vrothAst = (Vector) othAstList.get(0);
                    Double amt15 = Double.parseDouble(vrothAst.get(0).toString());
                    Double otherassetsAmt = Math.abs(amt15 / 1000);

                    Double Tot1 = (balance + balance1 + bankDlOthers);
                    Double tot2 = (caDlamt + sbdlAmt + borrowdlAmt + otherdlAmt + fdtlAmt + sbtlAmt + borrowtlAmt + othertlAmt);
                    Double tot3 = (caSbiAmt + otherSbiAmt + callMoneyAmt + balance14 + otherassetsAmt);

                    if ((Tot1 - tot3) > 0) {
                        balance16 = (Tot1 - tot3) + tot2;
                    } else {
                        balance16 = tot2;
                    }

                    List cshHList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='cashinhand'").getResultList();
                    Vector vrcshH = (Vector) cshHList.get(0);
                    Double camt = Double.parseDouble(vrcshH.get(0).toString());
                    Double cashinhandAmt = Math.abs(camt / 1000);

                    List caRbiList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='ca_rbi'").getResultList();
                    Vector vrcaRbi = (Vector) caRbiList.get(0);
                    Double amt18 = Double.parseDouble(vrcaRbi.get(0).toString());
                    Double carRbiAmt = Math.abs(amt18 / 1000);

                    List cstCoopList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='cast_coop'").getResultList();
                    Vector vrcstCoop = (Vector) cstCoopList.get(0);
                    Double amt19 = Double.parseDouble(vrcstCoop.get(0).toString());
                    Double castcoopiAmt = Math.abs(amt19 / 1000);

                    List cntCoopList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='cacent_coop'").getResultList();
                    Vector vrcntCoop = (Vector) cntCoopList.get(0);
                    Double amt20 = Double.parseDouble(vrcntCoop.get(0).toString());
                    Double cacentcoopAmt = Math.abs(amt20 / 1000);

                    List othCoopList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='otherst_coop'").getResultList();
                    Vector vrothCoop = (Vector) othCoopList.get(0);
                    Double amt21 = Double.parseDouble(vrothCoop.get(0).toString());
                    Double otherstcoopAmt = Math.abs(amt21 / 1000);

                    List othCntCoopList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='othercent_coop'").getResultList();
                    Vector vrothCntCoop = (Vector) othCntCoopList.get(0);
                    Double amt22 = Double.parseDouble(vrothCntCoop.get(0).toString());
                    Double otherstcentcoopAmt = Math.abs(amt22 / 1000);

                    List repBalList = em.createNativeQuery("Select ifnull(balance,0) from ho_reportingfriday Where RepFriDate='" + chkTransdate + "'").getResultList();
                    Vector vrrepBal = (Vector) repBalList.get(0);
                    Double repBalance = Double.parseDouble(vrrepBal.get(0).toString());

                    if (repBalance <= 0.0) {
                        balance23 = 0.0;
                    } else {
                        balance23 = repBalance;
                    }

                    Double tot5 = cashinhandAmt;
                    Double tot6 = (carRbiAmt + castcoopiAmt + cacentcoopAmt);
                    Double tot8 = (caSbiAmt - balance);
                    Double balance24 = (tot5 + tot6 + tot8);

                    List goldList = em.createNativeQuery("select ifnull(sum(amt),0) from ho_dailyupdm where acdesc='gold'").getResultList();
                    Vector vrgold = (Vector) goldList.get(0);
                    Double amt25 = Double.parseDouble(vrgold.get(0).toString());

                    if (amt25 != 0.0) {
                        balance25 = Math.abs(amt25 / 1000);
                    } else {
                        balance25 = caSbiAmt;
                    }

                    List invMstList = em.createNativeQuery("Select ifnull(FaceValue,0),ifnull(BookValue,0) From investment_master Where SettleDt between '" + yearbalDt + "' and '" + mydate + "' and (ifnull(CloseDt,'')='' or CloseDt>= '" + mydate + "') and SecType in (Select Description From codebook Where GroupCode=52 and code in (1))").getResultList();
                    Integer lstS = invMstList.size();
                    Double balance26 = 0.0;
                    if (lstS > 0) {
                        for (Integer j = 0; j <= lstS; j++) {
                            Vector vrinvM = (Vector) invMstList.get(j);
                            Double fVal = Double.parseDouble(vrinvM.get(0).toString());
                            Double bVal = Double.parseDouble(vrinvM.get(0).toString());
                            if (fVal > bVal) {
                                balance26 = balance26 + bVal;
                            } else {
                                balance26 = balance26 + fVal;
                            }
                        }
                    } else {
                        balance26 = 0.0;
                    }

                    balance26 = balance26 + (yearbalNetAmt * 100000);
                    List l1 = em.createNativeQuery("select * from ho_crr_liab where dt='" + mydate + "'").getResultList();
                    if (!l1.isEmpty()) {
                        Query crrLb = em.createNativeQuery("delete from ho_crr_liab where dt='" + mydate + "'");
                        Integer delCrr = crrLb.executeUpdate();
                        if (delCrr <= 0) {
                            throw new ApplicationException("Data has not been deleted from HO CRR LIAB table. !");
                        }
                    }

                    List l2 = em.createNativeQuery("select * from ho_crr_assets where dt='" + mydate + "'").getResultList();
                    if (!l2.isEmpty()) {
                        Query crrAst = em.createNativeQuery("delete from ho_crr_assets where dt='" + mydate + "'");
                        Integer delAst = crrAst.executeUpdate();
                        if (delAst <= 0) {
                            throw new ApplicationException("Data has not been deleted from HO CRR ASSETS table. !");
                        }
                    }

                    Query crrInst = em.createNativeQuery("insert into ho_crr_liab (DT,BCODE,BRNO,BANKDLCA,BANKDLOTHERS,BANKTL,CADL,SBDL,BORROWDL,OTHERDL,TOTALDL,FDTL,SBTL,BORROWTL,OTHERTL,TOTALTL,TOTAL,ENTERBY) values (" + "'" + mydate + "'" + "," + "'HO'" + "," + 1 + "," + balance + "," + balance1 + "," + bankDlOthers + "," + caDlamt + "," + sbdlAmt + "," + borrowdlAmt + "," + otherdlAmt + "," + (caDlamt + sbdlAmt + borrowdlAmt + otherdlAmt) + "," + fdtlAmt + "," + sbtlAmt + "," + borrowtlAmt + "," + othertlAmt + "," + (fdtlAmt + sbtlAmt + borrowtlAmt + othertlAmt) + "," + balance16 + "," + "'" + enterBy + "'" + ")");
                    Integer crrInstV = crrInst.executeUpdate();
                    if (crrInstV <= 0) {
                        throw new ApplicationException("Data has not been inserted into HO CRR LIAB table. !");
                    }

                    Query crrAstI = em.createNativeQuery("insert into ho_crr_assets (DT,BCODE,BRNO,CA_RBI,CA_SBI,CAST_COOP,CACENT_COOP,OTHERST_COOP,OTHERCENT_COOP,OTHER_SBI,ADVANCES,CALLMONEY,OTHERASSETS,CASHINHAND,GOLD,APPSECURITIES,CASHRESERVE,ENTERBY) values (" + "'" + mydate + "'" + "," + "'HO'" + "," + 1 + "," + carRbiAmt + "," + caSbiAmt + "," + castcoopiAmt + "," + cacentcoopAmt + "," + otherstcoopAmt + "," + otherstcentcoopAmt + "," + otherSbiAmt + "," + balance14 + "," + callMoneyAmt + "," + otherassetsAmt + "," + cashinhandAmt + "," + balance25 + "," + balance26 + "," + balance24 + "," + "'" + enterBy + "'" + ")");
                    Integer crrAstV = crrAstI.executeUpdate();
                    if (crrAstV <= 0) {
                        throw new ApplicationException("Data has not been inserted into HO CRR ASSETS table. !");
                    }

                    List dtHoList = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYBEGINFLAG='H' AND DATE=DATE_FORMAT(DATE_ADD('" + mydate + "',INTERVAL 1 DAY),'%Y%m%d') AND BRNCODE='" + orgbrcode + "'").getResultList();
                    if (dtHoList.size() > 0) {
                        Query crrInstH = em.createNativeQuery("insert into ho_crr_liab (DT,BCODE,BRNO,BANKDLCA,BANKDLOTHERS,BANKTL,CADL,SBDL,BORROWDL,OTHERDL,TOTALDL,FDTL,SBTL,BORROWTL,OTHERTL,TOTALTL,TOTAL,ENTERBY) select '" + mydate + "'" + ",BCODE,BRNO,BANKDLCA,BANKDLOTHERS,BANKTL,CADL,SBDL,BORROWDL,OTHERDL,TOTALDL,FDTL,SBTL,BORROWTL,OTHERTL,TOTALTL,TOTAL,ENTERBY");
                        Integer crrInH = crrInstH.executeUpdate();
                        if (crrInH <= 0) {
                            throw new ApplicationException("Data has not been inserted into HO CRR LIAB table. !");
                        }
                        Query crrAstH = em.createNativeQuery("insert into ho_crr_assets (DT,BCODE,BRNO,CA_RBI,CA_SBI,CAST_COOP,CACENT_COOP,OTHERST_COOP,OTHERCENT_COOP,OTHER_SBI,ADVANCES,CALLMONEY,OTHERASSETS,CASHINHAND,GOLD,APPSECURITIES,CASHRESERVE,ENTERBY) select '" + mydate + "'" + ",BCODE,BRNO,CA_RBI,CA_SBI,CAST_COOP,CACENT_COOP,OTHERST_COOP,OTHERCENT_COOP,OTHER_SBI,ADVANCES,CALLMONEY,OTHERASSETS,CASHINHAND,GOLD,APPSECURITIES,CASHRESERVE,ENTERBY");
                        Integer crrIH = crrAstH.executeUpdate();

                        if (crrIH <= 0) {
                            throw new ApplicationException("Data has not been inserted into HO CRR ASSETS table. !");
                        }
                    }
                    ut.commit();
                } else {
                    throw new ApplicationException("Data has not been updated !");
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return "Data has been updated successfully !";
    }
    private int resInsert;
    private String sno;
    private double Amount;

    public List getTableDetailsSavingDeposit() throws ApplicationException {
        List result = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select DATE_FORMAT(INTDT,'%d/%m/%Y'),SBTL,ENTERBY,sno from ho_crr_sbtl");
            result = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String saveRecord(String dateOfEffect, double SBTL, String enterBy) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String message = "";
        int sno1;
        try {
            ut.begin();
            Query q = em.createNativeQuery("select ifnull(max(sno),0) from ho_crr_sbtl");
            List l = q.getResultList();
            Vector v = (Vector) l.get(0);
            sno1 = Integer.parseInt(v.get(0).toString());
            sno1 = sno1 + 1;
            if (SBTL <= 0.0) {
                throw new ApplicationException("Please fill the data correctly...");
            } else {
                Query insertRecon = em.createNativeQuery("Insert into ho_crr_sbtl(SNO,SBTL,INTDT,ENTERBY) values(" + sno1 + "," + SBTL + ",'" + dateOfEffect + "','" + enterBy + "')");
                int result = insertRecon.executeUpdate();
                if (result > 0) {
                    ut.commit();
                    message = "Data has been successfully saved";
                } else {
                    throw new ApplicationException("transaction has been rollbacked");
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
        return message;

    }

    public String setRowUpdate(String dateOfEffect, double SBTL, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String message = "";
        dateOfEffect = dateOfEffect.substring(6, 10) + dateOfEffect.substring(3, 5) + dateOfEffect.substring(0, 2);
        try {
            ut.begin();
            Query insertRecon = em.createNativeQuery("insert into ho_crr_sbtl_his(SNo,SBTL,IntDt,EnterBy,Authby,TranTime,Auth,UpDateby) Select Sno,SBTL,IntDt,Enterby,AuthBy,TranTime,Auth,'" + enterBy + "'from ho_crr_sbtl where intdt ='" + dateOfEffect + "'");
            int result = insertRecon.executeUpdate();
            if (result > 0) {
                Query updateRecon = em.createNativeQuery("Update ho_crr_sbtl Set SBTL=" + SBTL + "where intdt = " + "'" + dateOfEffect + "'");
                int result1 = updateRecon.executeUpdate();
                if (result1 > 0) {
                    ut.commit();
                    message = "Data has been successfully updated";
                } else {
                    throw new ApplicationException("transaction has been rollbacked");
                }
            } else {
                throw new ApplicationException("transaction has been rollbacked");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ea) {
                throw new ApplicationException(ea.getMessage());
            }
        }
        return message;

    }

    /**
     * *******
     *
     *
     * Start HoPLMasterBean code
     *
     *
     *
     *****
     */
    public List getSubGroupCode(String classify, int groupcode) throws ApplicationException {
        List list1 = new ArrayList();
        try {
            list1 = em.createNativeQuery("select ifnull(max(subgroupcode),999999) from ho_fincomp_plmast where groupcode=" + groupcode + " and classification='" + classify + "'").getResultList();
            return list1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAcNo(String acno) throws ApplicationException {
        List list2 = new ArrayList();
        try {
            Query q = em.createNativeQuery("select acname from gltable where acno='" + acno + "'");
            list2 = q.getResultList();
            return list2;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String save(String classifi, int grcode, int subgrcode, String code, String grpdesc, String lastupdate, String mode, String subgrpdesc) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List resultSet = em.createNativeQuery("select * from ho_fincomp_plmast where groupcode=" + grcode + " and subgroupcode=" + subgrcode + "").getResultList();
            if (!resultSet.isEmpty()) {
                return "Error!! Entered group code and sub group code already exist";
            }
            Query q3 = em.createNativeQuery("insert into ho_fincomp_plmast (Classification,GroupCode,SubGroupCode,SubGLCode,GroupDescription,LastupdatedBy,MODE,SubGroupDescription) values ('" + classifi + "'," + grcode + "," + subgrcode + ",'" + code + "','" + grpdesc + "','" + lastupdate + "','" + mode + "','" + subgrpdesc + "')");
            int a = q3.executeUpdate();
            if (a > 0) {
                ut.commit();
                return "Data saved successfully";
            } else {
                ut.rollback();
                return "Error!! Data not saved successfully";
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

    public String deleteFincompPlMast(int groupCode, int subGroupCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            List resultSet = em.createNativeQuery("select * from ho_fincomp_plmast where groupcode=" + groupCode + " and subgroupcode=" + subGroupCode + "").getResultList();
            if (resultSet.isEmpty()) {
                return "Error!! No such row Corressponding to group code and sub group code exists";
            }
            ut.begin();
            int effectedRow = em.createNativeQuery("delete from ho_fincomp_plmast where groupcode=" + groupCode + " and subgroupcode=" + subGroupCode + "").executeUpdate();
            if (effectedRow > 0) {
                ut.commit();
                return "Row deletion successful";
            } else {
                ut.rollback();
                return "Deletion not successful";
            }
        } catch (Exception e) {
            throw new ApplicationException();
        }
    }

    public String getGroupDescription(int groupCode, int subGroupCode) throws ApplicationException {
        List selectGrpDesc = em.createNativeQuery("select GroupDescription from ho_fincomp_plmast where GroupCode=" + groupCode + "").getResultList();
        try {
            Vector v = (Vector) selectGrpDesc.get(0);
            return v.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * *****
     *
     *
     * Start code UpdatePlMasterBean 
     ********
     */
    public List getgrpcode() throws ApplicationException {
        try {
            Query q1 = em.createNativeQuery("select distinct(groupcode) from ho_fincomp_plmast");
            List result = q1.getResultList();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getsubgrpcode(String grpcode) throws ApplicationException {
        try {
            Query q1 = em.createNativeQuery("select distinct(subgroupcode) from ho_fincomp_plmast where groupcode='" + grpcode + "'");
            List result = q1.getResultList();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getGrpDesc(String grpcode, String subgrpcode) throws ApplicationException {
        try {
            Query q1 = em.createNativeQuery("select groupdescription from ho_fincomp_plmast where groupcode='" + grpcode + "' and subgroupcode='" + subgrpcode + "'");
            List result = q1.getResultList();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List subGlCode(String grpcode, String subgrpcode, String grpdesc) throws ApplicationException {
        try {
            Query q1 = em.createNativeQuery("select subglcode from ho_fincomp_plmast where groupcode='" + grpcode + "' and subgroupcode='" + subgrpcode + "' and groupdescription='" + grpdesc + "'");
            List result = q1.getResultList();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List subgrpdesc(String grpcode, String subgrpcode, String grpdesc, String subglcode) throws ApplicationException {
        try {
            Query q1 = em.createNativeQuery("select subgroupdescription from ho_fincomp_plmast where groupcode='" + grpcode + "' and subgroupcode='" + subgrpcode + "' and groupdescription='" + grpdesc + "' and subglcode='" + subglcode + "'");
            List result = q1.getResultList();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String updatePlMaster(String input, String grpcode, String subgrpcode, String grpdesc, String subglcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q1 = em.createNativeQuery("update ho_fincomp_plmast set subgroupdescription='" + input + "' where groupcode='" + grpcode + "' and subgroupcode='" + subgrpcode + "' and groupdescription='" + grpdesc + "' and subglcode='" + subglcode + "'");
            int int1 = q1.executeUpdate();
            if (int1 > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
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

    public String updatedesc(String grpcode, String subgrpcode, String grpdesc) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q1 = em.createNativeQuery("update ho_fincomp_plmast set groupdescription='" + grpdesc + "' where groupcode='" + grpcode + "' and subgroupcode='" + subgrpcode + "'");
            int int1 = q1.executeUpdate();
            if (int1 > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
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

    public List selectFincompMast() throws ApplicationException {
        List resultList = new ArrayList();
        try {
            resultList = em.createNativeQuery("select * from ho_fincomp_plmast").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    public String saveCrrSlr(String wefDate, String CrrPerc, String SlrPerc) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            List resultSet = em.createNativeQuery("select * from ho_crr_parameter where WefDate = '" + wefDate + "' ").getResultList();
            if (!resultSet.isEmpty()) {
                ut.rollback();
                return "Data already Exist in the table!!";
            }
            Query q3 = em.createNativeQuery("INSERT INTO ho_crr_parameter (WefDate, CrrPerc, SlrPerc) VALUES ('" + wefDate + "', " + CrrPerc + "," + SlrPerc + ")");
            int a = q3.executeUpdate();
            if (a > 0) {
                ut.commit();
                return msg = "true";
            } else {
                ut.rollback();
                return "Error!! Data not saved successfully";
            }
        } catch (Exception e) {

            throw new ApplicationException(e);
        }
    }

    public List gethoCrrSlrData() throws ApplicationException {
        try {
            return em.createNativeQuery("select date_format(WefDate,'%d/%m/%Y'),CrrPerc,SlrPerc from ho_crr_parameter order by WefDate desc").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    /**
     * ******
     *
     *
     *
     *********
     */
}
