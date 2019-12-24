/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.npa;

import com.cbs.dto.npa.NPAChargesPojo;
import com.cbs.dto.npa.NPAReminderPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueRemainderPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsBulkPostingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 * @author root
 */
@Stateless(mappedName = "NPAFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class NPAFacade implements NPAFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private CommonReportMethodsRemote common;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostingMgmtRem;
    @EJB
    private LoanInterestCalculationFacadeRemote loanInterestCalFcdRem;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
    @EJB
    private FtsBulkPostingFacadeRemote ftsBulkDrCr;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

    public int insertNPAChargeMaster(String acNature, String acType, String charge, String effFromDt, String effToDt, String glHead, double amount, String enterBy) throws ApplicationException {
        //acNature is not in use

        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        int flag = 0;
        int insertedCount = 0, ct = 0;
        String error = "";
        UserTransaction ut = context.getUserTransaction();
        Date currentDt = new Date();
        String currentDate = ymdFormat.format(currentDt);
        try {
            ut.begin();
            String chargeType = "NPA-REM-CHG", fixPercFlag = "F";
            String fromRange = "0", toRange = "99999999999";
            //CODE HAVE TO CHANGE, ACC TO REQUIREMENT
            String allAcountType = "";
            if (acNature.equalsIgnoreCase("0")) {
                List tempList1 = common.getAcctNatureOnlyDB();
                Vector tempVector;
                if (!tempList1.isEmpty()) {
                    for (int i = 0; i < tempList1.size(); i++) {
                        tempVector = (Vector) tempList1.get(i);
                        List tempList2 = common.getAcctTypeAsAcNatureOnlyDB(tempVector.get(0).toString());
                        if (!tempList2.isEmpty()) {
                            for (int j = 0; j < tempList2.size(); j++) {
                                Vector vtr = (Vector) tempList2.get(j);
                                //query here for update, firstly update if already exist
                                int updateFlag = 0;
                                List listForUpdate = getNPAforUpdate(chargeType, charge, vtr.get(0).toString(), "");
                                if (!listForUpdate.isEmpty()) {
                                    Vector vecUpdate = (Vector) listForUpdate.get(0);
                                    String eff_date = vecUpdate.get(7).toString().substring(0, 10).replace("-", "/");
                                    if (dmyFormat.parse(effFromDt.replace("-", "/")).compareTo(dmyFormat.parse(eff_date)) > 0) {
                                        updateFlag = 1;
                                        String query = "update cbs_charge_detail set eff_to_date = DATE_ADD('" + effFromDt + "', INTERVAL -1 DAY) where charge_type = '" + vecUpdate.get(0) + "' and charge_name = '" + vecUpdate.get(1) + "' and ac_type ='" + vecUpdate.get(2) + "' and "
                                                + "eff_date = '" + vecUpdate.get(7) + "' and cr_gl_head = '" + vecUpdate.get(8) + "' and creation_date = '" + vecUpdate.get(10) + "'";
                                        Query updateQuery = em.createNativeQuery(query);
                                        int res = updateQuery.executeUpdate();
                                        if (res < 1) {
                                            throw new ApplicationException("Updation problem in existing data on the besis of insertion data !");
                                        }
                                    } else {
                                        error += vtr.get(0).toString() + ",";
                                    }
                                }
                                if (updateFlag == 1 || listForUpdate.isEmpty()) {
                                    ct++;
                                    String query = "insert into cbs_charge_detail(CHARGE_TYPE, CHARGE_NAME, AC_TYPE, FROM_RANGE, TO_RANGE, FIX_PERC_FLAG, AMT, EFF_DATE, CR_GL_HEAD, ENTER_BY, CREATION_DATE, EFF_TO_DATE) "
                                            + "values ('" + chargeType + "','" + charge + "','" + vtr.get(0).toString() + "','" + fromRange + "','" + toRange + "','" + fixPercFlag + "','" + amount + "','" + effFromDt + "','" + glHead + "','" + enterBy + "',CURRENT_DATE(),'" + effToDt + "')";
//                                    System.out.println(query);
                                    Query insertQuery = em.createNativeQuery(query);
                                    int result = insertQuery.executeUpdate();
                                    if (result > 0) {
                                        insertedCount++;
                                    } else {
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (acType.equalsIgnoreCase("0")) {
                    List tempList2 = common.getAcctTypeAsAcNatureOnlyDB(acNature);
                    if (!tempList2.isEmpty()) {
                        for (int j = 0; j < tempList2.size(); j++) {
                            Vector vtr = (Vector) tempList2.get(j);
                            int updateFlag = 0;
                            List listForUpdate = getNPAforUpdate(chargeType, charge, vtr.get(0).toString(), "");
                            if (!listForUpdate.isEmpty()) {
                                Vector vecUpdate = (Vector) listForUpdate.get(0);
                                String eff_date = vecUpdate.get(7).toString().substring(0, 10).replace("-", "/");
                                if (dmyFormat.parse(effFromDt.replace("-", "/")).compareTo(dmyFormat.parse(eff_date)) > 0) {
                                    updateFlag = 1;
                                    String query = "update cbs_charge_detail set eff_to_date = DATE_ADD('" + effFromDt + "', INTERVAL -1 DAY) where charge_type = '" + vecUpdate.get(0) + "' and charge_name = '" + vecUpdate.get(1) + "' and ac_type ='" + vecUpdate.get(2) + "' and "
                                            + "eff_date = '" + vecUpdate.get(7) + "' and cr_gl_head = '" + vecUpdate.get(8) + "' and creation_date = '" + vecUpdate.get(10) + "'";//and eff_date <> CURRENT_DATE()
                                    Query updateQuery = em.createNativeQuery(query);
                                    int res = updateQuery.executeUpdate();
                                    if (res < 1) {
                                        throw new ApplicationException("Updation problem in existing data on the besis of insertion data !");
                                    }
                                } else {
                                    error += vtr.get(0).toString() + ",";
                                }
                            }
                            if (updateFlag == 1 || listForUpdate.isEmpty()) {
                                ct++;
                                String query = "insert into cbs_charge_detail(CHARGE_TYPE, CHARGE_NAME, AC_TYPE, FROM_RANGE, TO_RANGE, FIX_PERC_FLAG, AMT, EFF_DATE, CR_GL_HEAD, ENTER_BY, CREATION_DATE, EFF_TO_DATE) "
                                        + "values ('" + chargeType + "','" + charge + "','" + vtr.get(0).toString() + "','" + fromRange + "','" + toRange + "','" + fixPercFlag + "','" + amount + "','" + effFromDt + "','" + glHead + "','" + enterBy + "',CURRENT_DATE(),'" + effToDt + "')";
//                                System.out.println(query);
                                Query insertQuery = em.createNativeQuery(query);
                                int result = insertQuery.executeUpdate();
                                if (result > 0) {
                                    insertedCount++;
                                } else {
                                    //may be throw
                                }
                            }
                        }
                    }
                } else {
                    int updateFlag = 0;
                    List listForUpdate = getNPAforUpdate(chargeType, charge, acType, "");
                    if (!listForUpdate.isEmpty()) {
                        Vector vecUpdate = (Vector) listForUpdate.get(0);
                        String eff_date = vecUpdate.get(7).toString().substring(0, 10).replace("-", "/");
                        if (dmyFormat.parse(effFromDt.replace("-", "/")).compareTo(dmyFormat.parse(eff_date)) > 0) {
                            updateFlag = 1;
                            String query = "update cbs_charge_detail set eff_to_date = DATE_ADD('" + effFromDt + "', INTERVAL -1 DAY) where charge_type = '" + vecUpdate.get(0) + "' and charge_name = '" + vecUpdate.get(1) + "' and ac_type ='" + vecUpdate.get(2) + "' and "
                                    + "eff_date = '" + vecUpdate.get(7) + "' and cr_gl_head = '" + vecUpdate.get(8) + "' and creation_date = '" + vecUpdate.get(10) + "'";
                            Query updateQuery = em.createNativeQuery(query);
                            int res = updateQuery.executeUpdate();
                            if (res < 1) {
                                throw new ApplicationException("Updation problem in existing data on the besis of insertion data !");
                            }
                        } else {
                            error += acType + ",";
                        }
                    }
                    if (updateFlag == 1 || listForUpdate.isEmpty()) {
                        ct++;
                        String query = "insert into cbs_charge_detail(CHARGE_TYPE, CHARGE_NAME, AC_TYPE, FROM_RANGE, TO_RANGE, FIX_PERC_FLAG, AMT, EFF_DATE, CR_GL_HEAD, ENTER_BY, CREATION_DATE, EFF_TO_DATE) "
                                + "values ('" + chargeType + "','" + charge + "','" + acType + "','" + fromRange + "','" + toRange + "','" + fixPercFlag + "','" + amount + "','" + effFromDt + "','" + glHead + "','" + enterBy + "',CURRENT_DATE(),'" + effToDt + "')";
//                        System.out.println(query);
                        Query insertQuery = em.createNativeQuery(query);
                        int result = insertQuery.executeUpdate();
                        if (result > 0) {
                            insertedCount++;
                        } else {
                            //may be throw
                        }
                    }
                }
            }

            if (insertedCount != ct || insertedCount == 0) {
                ut.rollback();
                throw new ApplicationException("Problem Occured In NPA Charge Insertion ! " + error + " existing on this date.");
            } else {
                flag = insertedCount;
                ut.commit();
                //ut.rollback();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return flag;
    }

    public List getNPAforUpdate(String chargeType, String chargeName, String acType, String glHead) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select * from  cbs_charge_detail where charge_type ='" + chargeType + "' and charge_name = '" + chargeName + "' and ac_type = '" + acType + "' order by eff_to_date desc limit 1").getResultList();
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<NPAChargesPojo> getNPAChargesMaster(String branchCode) throws ApplicationException {
        List<NPAChargesPojo> npaPojoList = new ArrayList<NPAChargesPojo>();
        try {
            String query = "select ccd.charge_name, crrt.ref_desc as chargeDesc, ccd.ac_type, date_format(ccd.eff_date,'%d/%m/%Y') as eff_from_date, date_format(ccd.eff_to_date,'%d/%m/%Y')as eff_to_date, ccd.amt as amount, ccd.cr_gl_head as gl_head, g.acName as glHeadDesc, ccd.CREATION_DATE, ccd.ENTER_BY from cbs_charge_detail ccd"
                    + ",cbs_ref_rec_type crrt, gltable g "
                    + "where ccd.charge_type = 'NPA-REM-CHG' and crrt.ref_code = ccd.charge_name and crrt.ref_rec_no = '402' and ccd.cr_gl_head = substring(g.acno,3,8) and substring(g.acno,1,2) ='" + branchCode + "'";
            List selectList = em.createNativeQuery(query).getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            } else {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    NPAChargesPojo pojo = new NPAChargesPojo();
                    pojo.setSno(i + 1);
                    pojo.setFlag(false);
                    pojo.setCharge(vec.get(0).toString());
                    pojo.setChargeDesc(vec.get(1).toString());
                    pojo.setAcType(vec.get(2).toString());
                    pojo.setEffFromDt(vec.get(3).toString());
                    pojo.setEffToDt(vec.get(4).toString());
                    pojo.setAmount((Double) vec.get(5));
                    pojo.setGlHead(vec.get(6).toString());
                    pojo.setGlHeadDesc(vec.get(7).toString());
                    pojo.setEnterDate(vec.get(8).toString());
                    pojo.setEnterBy(vec.get(9).toString());
                    npaPojoList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return npaPojoList;
    }

    public String getGlName(String acno) throws ApplicationException {
        String glName = "";
        try {
            List list = em.createNativeQuery("select distinct acname from gltable where substring(acno,3,8)='" + acno + "'").getResultList();

            if (list.isEmpty()) {
                throw new ApplicationException("GL Head does not exist!");
            } else {
                Vector vtr = (Vector) list.get(0);
                glName = ((Object) vtr.get(0)).toString();
                return glName;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public boolean isExistCharge(NPAChargesPojo pojo) throws ApplicationException {
        try {
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return false;

    }

    public boolean isExistOverDueReminder(String acNo, String rem, String monthStartDt, String monthEndDt) throws ApplicationException {
        boolean flag = false;
        try {
            String query = "select * from cbs_overdue_reminder_charge where acNo = '" + acNo + "' and reminderNo = '" + rem + "' "
                    + " and dt between '"+monthStartDt+"' and '"+monthEndDt+"'";
            List list = em.createNativeQuery(query).getResultList();
            if (!list.isEmpty()) {
                flag = true;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return flag;
    }

    public boolean updateNPAChargeMaster(List<NPAChargesPojo> chargeList, int index, String effFromDt, String effToDt, String glHead, double amount, String updateBy, String updateDt) throws ApplicationException {
        boolean flag = false;
        int updateFlag = 0;
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDt = new Date();
        String currentDate = ymdFormat.format(currentDt);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String chargeType = "NPA-REM-CHG", fixPercFlag = "F";
            String fromRange = "0", toRange = "99999999999";
            String prevEfDate = chargeList.get(index).getEffFromDt();
            prevEfDate = prevEfDate.substring(6, 10) + "-" + prevEfDate.substring(3, 5) + "-" + prevEfDate.substring(0, 2);
            String prevEtDate = chargeList.get(index).getEffToDt();
            prevEtDate = prevEtDate.substring(6, 10) + "-" + prevEtDate.substring(3, 5) + "-" + prevEtDate.substring(0, 2);
            String updateDate = updateDt.substring(6, 10) + "-" + updateDt.substring(3, 5) + "-" + updateDt.substring(0, 2);
            String query = "";
            if (dmyFormat.parse(prevEfDate.replace("-", "/")).compareTo(dmyFormat.parse(effFromDt.replace("-", "/"))) == 0) {
                //if update on same fromdate then todate will be of (new insertion's fromdate)
                updateFlag = 1;
                query = "update cbs_charge_detail set eff_date ='" + effFromDt + "', eff_to_date = '" + effToDt + "', amt = '" + amount + "', cr_gl_head = '" + glHead + "', updated_by = '" + updateBy + "', update_date = '" + updateDate + "' "
                        + "where charge_type = '" + chargeType + "' and charge_name = '" + chargeList.get(index).getCharge() + "' and ac_type = '" + chargeList.get(index).getAcType() + "' and amt = '" + chargeList.get(index).getAmount() + "' and eff_date = '" + prevEfDate + "' and eff_to_date = '" + prevEtDate + "' "
                        + "and cr_gl_head = '" + chargeList.get(index).getGlHead() + "' and enter_by = '" + chargeList.get(index).getEnterBy() + "'  and creation_date = '" + chargeList.get(index).getEnterDate() + "'";

            } else if (dmyFormat.parse(currentDate).compareTo(dmyFormat.parse(effFromDt.replace("-", "/"))) <= 0 && dmyFormat.parse(prevEfDate.replace("-", "/")).compareTo(dmyFormat.parse(effFromDt.replace("-", "/"))) > 0) {
                //here updating formdate and to date will be old
                updateFlag = 2;
                query = "update cbs_charge_detail set eff_date ='" + effFromDt + "', eff_to_date = '" + prevEtDate + "', amt = '" + amount + "', cr_gl_head = '" + glHead + "', updated_by = '" + updateBy + "', update_date = '" + updateDate + "' "
                        + "where charge_type = '" + chargeType + "' and charge_name = '" + chargeList.get(index).getCharge() + "' and ac_type = '" + chargeList.get(index).getAcType() + "' and amt = '" + chargeList.get(index).getAmount() + "' and eff_date = '" + prevEfDate + "' and eff_to_date = '" + prevEtDate + "' "
                        + "and cr_gl_head = '" + chargeList.get(index).getGlHead() + "' and enter_by = '" + chargeList.get(index).getEnterBy() + "'  and creation_date = '" + chargeList.get(index).getEnterDate() + "'";

            } else {
                //here updating formdate an to date will be new of new insertion from date -1
                updateFlag = 3;
                query = "update cbs_charge_detail set eff_date ='" + effFromDt + "', eff_to_date = 'DATE_ADD('" + prevEfDate + "', INTERVAL -1 DAY)', amt = '" + amount + "', cr_gl_head = '" + glHead + "', updated_by = '" + updateBy + "', update_date = '" + updateDate + "' "
                        + "where charge_type = '" + chargeType + "' and charge_name = '" + chargeList.get(index).getCharge() + "' and ac_type = '" + chargeList.get(index).getAcType() + "' and amt = '" + chargeList.get(index).getAmount() + "' and eff_date = '" + prevEfDate + "' and eff_to_date = '" + prevEtDate + "' "
                        + "and cr_gl_head = '" + chargeList.get(index).getGlHead() + "' and enter_by = '" + chargeList.get(index).getEnterBy() + "'  and creation_date = '" + chargeList.get(index).getEnterDate() + "'";

            }
            Query updateQuery = em.createNativeQuery(query);
            int result = updateQuery.executeUpdate();
            if (result > 0 && updateFlag == 3) {
                query = "insert into cbs_charge_detail(CHARGE_TYPE, CHARGE_NAME, AC_TYPE, FROM_RANGE, TO_RANGE, FIX_PERC_FLAG, AMT, EFF_DATE, CR_GL_HEAD, ENTER_BY, CREATION_DATE, EFF_TO_DATE) "
                        + "values ('" + chargeType + "','" + chargeList.get(index).getCharge() + "','" + chargeList.get(index).getAcType() + "','" + fromRange + "','" + toRange + "','" + fixPercFlag + "','" + amount + "','" + effFromDt + "','" + glHead + "','" + updateBy + "',CURRENT_DATE(),'" + effToDt + "')";// here updateBy is use as enterBy
//                System.out.println(query);
                Query insertQuery = em.createNativeQuery(query);
                int res = insertQuery.executeUpdate();
                if (res > 0) {
                    flag = true;
                    ut.commit();
                }
            } else if (result > 0 && updateFlag == 1 || updateFlag == 2) {
                flag = true;
                ut.commit();
            } else {
                ut.rollback();
                throw new ApplicationException("Updation problem !");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return flag;
    }

    public int insertNPAOverDue(List<OverdueEmiReportPojo> list, double amount, String charge, String dt, boolean allChecked, String enterBy, String branchCode) throws ApplicationException {
        int result = 0, count = 0, unCheckedCount = 0, c = 0;
        String reconTable = "", msg1 = "", msg2 = "", msg3 = "",zeroReminderAllowed = "1";
        float trsNo, recNo;
        double taxAmt = 0d;
        double ttlTaxAmt = 0d;
        double ttlTaxAmtIgst = 0d;
        double glHeadAmount = 0;
        int listSize = list.size();
        String tempBd = ftsBulkDrCr.daybeginDate(branchCode);
        UserTransaction ut = context.getUserTransaction();
        try {
            List zeroEntryAllowed = em.createNativeQuery("SELECT ifnull(code,1) FROM parameterinfo_report WHERE reportname='OVERDUE-REMIN-0-ALLOWED'").getResultList();
            if (!zeroEntryAllowed.isEmpty()) {
                Vector zeroEntryAllowedVector = (Vector) zeroEntryAllowed.get(0);
                zeroReminderAllowed = zeroEntryAllowedVector.get(0).toString();
            }
            dt = dt.substring(6, 10) + "-" + dt.substring(3, 5) + "-" + dt.substring(0, 2);
            // Sort the list in accending order
            Collections.sort(list, new Comparator<OverdueEmiReportPojo>() {
                public int compare(OverdueEmiReportPojo o1, OverdueEmiReportPojo o2) {
                    return o1.getGlHead().compareTo(o2.getGlHead());
                }
            });
            ut.begin();
            if (zeroReminderAllowed.equalsIgnoreCase("0")) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isCheckBoxFlag() == false) {
                        list.remove(i);
                        i--;
                        unCheckedCount++;
                    }
                }
                for (OverdueEmiReportPojo obj : list) {
                    String query = "insert into cbs_overdue_reminder_charge(acNo, reminderNo, overDueAmt, dt, noOfEmiOverDue, chargeAmt, enterBy, enterDate, trsno)"
                            + "values ('" + obj.getAccountNumber() + "','" + charge + "','" + obj.getAmountOverdue() + "','" + dt + "','" + obj.getNoOfEmiOverdue() + "','" + obj.getAmount() + "','" + enterBy + "',now()," + 0 + ")";
                    Query insertQuery = em.createNativeQuery(query);
                    int res = insertQuery.executeUpdate();
                    if (res > 0) {
                        count++;
                    } else {
                        throw new ApplicationException("Problem Occured In cbs_overdue_reminder_charge Insertion !");
                    }
                }
                if (unCheckedCount == listSize) {
                    ut.rollback();
                    throw new ApplicationException("You have not selected any row !");
                } else {
                    result = 999999999;
                    ut.commit();
                }
            } else {
                List parameterinfoList = em.createNativeQuery("SELECT code FROM parameterinfo_report WHERE reportname='STAXMODULE_ACTIVE'").getResultList();
                if (parameterinfoList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in parameterinfo_report for tax module.");
                }
                Vector sevVector = (Vector) parameterinfoList.get(0);
                String staxModuleActive = sevVector.get(0).toString();
                Map<String, Double> map = new HashMap<String, Double>();
                double sPerc = 0;
                int rUpTo = 0;
                double sPercIgst = 0;
                int rUpToIgst = 0;
                if (staxModuleActive.equalsIgnoreCase("1")) {
                    map = ibRemote.getTaxComponentSlab(tempBd);
                    Set<Map.Entry<String, Double>> set = map.entrySet();
                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        sPerc = sPerc + Double.parseDouble(entry.getValue().toString());
                        rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                    }
                    map = ibRemote.getIgstTaxComponentSlab(tempBd);
                    Set<Map.Entry<String, Double>> set1 = map.entrySet();
                    Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                    while (it1.hasNext()) {
                        Map.Entry entry = it1.next();
                        sPercIgst = sPercIgst + Double.parseDouble(entry.getValue().toString());
                        rUpToIgst = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isCheckBoxFlag() == false) {
                        list.remove(i);
                        i--;
                        unCheckedCount++;
                    }
                }
//            if (allChecked == true) {
                //--All checked---
                trsNo = ftsPostingMgmtRem.getTrsNo();
                recNo = ftsPostingMgmtRem.getRecNo();
                String desc = "Over due charges for " + charge + " Reminder as on dated " + dt;
                for (OverdueEmiReportPojo obj : list) {
                    if (obj.getAmount().doubleValue() != 0) {// which NPA CHARGE IS ZERO NOT ALLOWED TO BE INSERT
                        //Debit 
                        msg1 = ftsPostingMgmtRem.insertRecons(common.getAcNatureByAcNo(obj.getAccountNumber()), obj.getAccountNumber(), 1, obj.getAmount().doubleValue(),
                                tempBd, tempBd, 2, desc,
                                enterBy, trsNo, "", recNo, "Y", enterBy, 124, 3, "", tempBd,
                                0f, "", "", 0, "", 0f, "", null, branchCode, branchCode, 0, "", "", "");
                        if (!msg1.equalsIgnoreCase("TRUE")) {
                            ut.rollback();
                            throw new ApplicationException(msg1);// pass own exception
                        } else {
                            glHeadAmount = glHeadAmount + obj.getAmount().doubleValue();
                            taxAmt = 0;
                            if (staxModuleActive.equalsIgnoreCase("1")) {
                                if (obj.getCustState().equalsIgnoreCase(obj.getBranchState())) {
                                    taxAmt = CbsUtil.round(((obj.getAmount().doubleValue() * sPerc) / 100), rUpTo);
                                    ttlTaxAmt = ttlTaxAmt + obj.getAmount().doubleValue();
                                } else {
                                    taxAmt = CbsUtil.round(((obj.getAmount().doubleValue() * sPercIgst) / 100), rUpToIgst);
                                    ttlTaxAmtIgst = ttlTaxAmtIgst + obj.getAmount().doubleValue();
                                }
                                recNo = ftsPostingMgmtRem.getRecNo();
                                String taxDetail = desc + ":" + (obj.getCustState().equalsIgnoreCase(obj.getBranchState()) ? "CGST:SGST" : "IGST");
                                String msg = ftsPostingMgmtRem.insertRecons(common.getAcNatureByAcNo(obj.getAccountNumber()), obj.getAccountNumber(), 1, taxAmt,
                                        tempBd, tempBd, 2, taxDetail, enterBy, trsNo, null,
                                        recNo, "Y", enterBy, 71, 3, null, null, (float) 0, null, "A", 0, null, null, null, null,
                                        branchCode, branchCode, 0, null, "", "");
                                if (!msg.equalsIgnoreCase("True")) {
                                    ut.rollback();
                                    throw new ApplicationException(msg);
                                }
                            }
                            msg3 = ftsPostingMgmtRem.updateBalance(common.getAcNatureByAcNo(obj.getAccountNumber()), obj.getAccountNumber(), 0, (obj.getAmount().doubleValue() + taxAmt), "Y", "N");
                            if (!msg3.equalsIgnoreCase("true")) {
                                ut.rollback();
                                throw new ApplicationException(msg3);
                            }
                            String query = "insert into cbs_overdue_reminder_charge(acNo, reminderNo, overDueAmt, dt, noOfEmiOverDue, chargeAmt, enterBy, enterDate, trsno)"
                                    + "values ('" + obj.getAccountNumber() + "','" + charge + "','" + obj.getAmountOverdue() + "','" + dt + "','" + obj.getNoOfEmiOverdue() + "','" + obj.getAmount() + "','" + enterBy + "',now()," + trsNo + ")";
                            Query insertQuery = em.createNativeQuery(query);
                            int res = insertQuery.executeUpdate();
                            if (res > 0) {
                                count++;
                            } else {
                                ut.rollback();
                                throw new ApplicationException("Problem Occured In cbs_overdue_reminder_charge Insertion !");
                            }
                        }
                    } else{
                        ut.rollback();
                        throw new ApplicationException("Please check the Charges!");
                    }
                }
                // Credit TO GL_RECON TABLE
                //IF List have multiple glhead in list
                Map<String, BigDecimal> mapHaveMultipleGl = new HashMap<String, BigDecimal>();
                for (OverdueEmiReportPojo overDuePojo : list) {
                    if (mapHaveMultipleGl.containsKey(overDuePojo.getGlHead())) { //Present
                        mapHaveMultipleGl.put(overDuePojo.getGlHead(), overDuePojo.getAmount().add(mapHaveMultipleGl.get(overDuePojo.getGlHead())));
                    } else { //Not Present
                        mapHaveMultipleGl.put(overDuePojo.getGlHead(), overDuePojo.getAmount());
                    }
                }
                if (ttlTaxAmt != 0) {
                    for (Map.Entry entry : mapHaveMultipleGl.entrySet()) {
//                        System.out.print(entry.getKey() + " : " + entry.getValue());
                        if (Double.parseDouble(entry.getValue().toString()) != 0 && ttlTaxAmt != 0) {
                            msg2 = ftsPostingMgmtRem.insertRecons("PO", branchCode + entry.getKey() + "01", 0, (Double.parseDouble(entry.getValue().toString())), tempBd, tempBd, 2,
                                    desc, enterBy, trsNo,
                                    "", recNo, "Y", enterBy, 124, 3, "", tempBd, 0f, "", "", 0, "",
                                    0f, "", null, branchCode, branchCode, 0, "", "", "");
                            if (!msg2.equalsIgnoreCase("TRUE")) {
                                ut.rollback();
                                throw new ApplicationException(msg2);// pass own exception
                            } else {
                                msg3 = ftsPostingMgmtRem.updateBalance("PO", branchCode + entry.getKey() + "01", (Double.parseDouble(entry.getValue().toString())), 0, "Y", "N");
                                if (!msg3.equalsIgnoreCase("true")) {
                                    ut.rollback();
                                    throw new ApplicationException(msg3);
                                }
                            }
                        }
                    }

                    double crAmt = 0d, drAmt = 0d;
                    if (staxModuleActive.equalsIgnoreCase("1")) {
                        double sTaxAmt = ttlTaxAmt;
                        map = ibRemote.getTaxComponent(sTaxAmt, tempBd);
                        Set<Map.Entry<String, Double>> set1 = map.entrySet();
                        Iterator<Map.Entry<String, Double>> it1 = set1.iterator();
                        while (it1.hasNext()) {
                            Map.Entry entry = it1.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = branchCode + keyArray[1];
                            String mainDetails = description.toUpperCase() + " " + desc;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());

                            String acNature = ftsPostingMgmtRem.getAcNatureByCode(ftsPostingMgmtRem.getAccountCode(taxHead));
                            //taxDesc = taxName + desc;
                            recNo = ftsPostingMgmtRem.getRecNo();
                            String msg = ftsPostingMgmtRem.insertRecons(acNature, taxHead, 0, taxAmount, tempBd, tempBd, 2, mainDetails, enterBy, trsNo, null, recNo, "Y", enterBy,
                                    71, 3, null, null, (float) 0, null, "A", 1, null, null, null, null, branchCode, branchCode, 0, null, "", "");
                            if (!msg.equalsIgnoreCase("True")) {
                                ut.rollback();
                                throw new ApplicationException(msg);
                            }
                            crAmt = taxAmount;
                            drAmt = (float) 0;
                            msg = ftsPostingMgmtRem.updateBalance(acNature, taxHead, crAmt, drAmt, "Y", "Y");
                            if (!msg.equalsIgnoreCase("True")) {
                                ut.rollback();
                                throw new ApplicationException(msg);
                            }
                        }
                        if (ttlTaxAmtIgst != 0) {
                            double sTaxAmtIgst = ttlTaxAmtIgst;
                            map = ibRemote.getIgstTaxComponent(sTaxAmtIgst, tempBd);
                            Set<Map.Entry<String, Double>> setIgst = map.entrySet();
                            Iterator<Map.Entry<String, Double>> itIgst = setIgst.iterator();
                            while (itIgst.hasNext()) {
                                Map.Entry entry = itIgst.next();
                                String[] keyArray = entry.getKey().toString().split(":");
                                String description = keyArray[0];
                                String taxHead = branchCode + keyArray[1];
                                String mainDetails = description.toUpperCase() + " " + desc;
                                double taxAmount = Double.parseDouble(entry.getValue().toString());

                                String acNature = ftsPostingMgmtRem.getAcNatureByCode(ftsPostingMgmtRem.getAccountCode(taxHead));
                                //taxDesc = taxName + desc;
                                recNo = ftsPostingMgmtRem.getRecNo();

                                String msg = ftsPostingMgmtRem.insertRecons(acNature, taxHead, 0, taxAmount, tempBd, tempBd, 2, mainDetails, enterBy, trsNo, null, recNo, "Y", enterBy,
                                        71, 3, null, null, (float) 0, null, "A", 1, null, null, null, null, branchCode, branchCode, 0, null, "", "");
                                if (!msg.equalsIgnoreCase("True")) {
                                    ut.rollback();
                                    throw new ApplicationException(msg);
                                }

                                crAmt = taxAmount;
                                drAmt = (float) 0;
                                msg = ftsPostingMgmtRem.updateBalance(acNature, taxHead, crAmt, drAmt, "Y", "Y");
                                if (!msg.equalsIgnoreCase("True")) {
                                    ut.rollback();
                                    throw new ApplicationException(msg);
                                }
                            }
                        }
                    }
                    if (unCheckedCount == listSize) {
                        ut.rollback();
                        throw new ApplicationException("You have not selected any row !");
                    } else {
                        result = (int) trsNo;
                        ut.commit();
                    }
                }
            }
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public List<NPAReminderPojo> printOverDueReminder(List<OverdueEmiReportPojo> list, String overDueRem, String dt, boolean allChecked, String enterBy, String branchCode) throws ApplicationException {
        List<NPAReminderPojo> remlist = new ArrayList<NPAReminderPojo>();
        NumberFormat formatter = new DecimalFormat("0.00");
        int unCheckedCount = 0;
        String outStandingAmt = "";
        dt = getSqlFormatDate(dt);
        int listSize = list.size();
        
        try {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isCheckBoxFlag() == false) {
                    list.remove(i);
                    i--;
                    unCheckedCount++;
                }
            }
            if (listSize == unCheckedCount) {
                throw new ApplicationException("You have not selected any row !");
            } else {
                if (overDueRem.equalsIgnoreCase("1") || overDueRem.equalsIgnoreCase("2")) {

                    String rem1Desc1 = "\tWe regret to bring to your kind notice that as a result of irregular/ non-payment of installments by you, in respect "
                            + "of your above loan, a sum of Rs. ";
                    String rem1Desc2 = "  has since become overdue. The said sum also includes the amount of installment for current month.\n \n "
                            + "\tYou are therefore, advised to pay the above stated dues immediately to reqularise the position of your account. Please treat this as urgent.";
                    String rem2Desc1 = "\tWe are constrained to note that despite our repeated requests / notices, you have not remitted us the required "
                            + "amount of Rs. ";//Overdue Amount
                    String rem2Desc2 = ". Since, this being a serious lapse on your part in meeting your commitments on time, we call upon you to settle the above "
                            + "dues of the Bank, without any further delay.";
                    String rem2Desc3 = "\n \n \tIn this regards, please note that if you fail to comply with our demand of settling the said dues, wihtin 10(ten) days from "
                            + "the receipt of this letter, we shall be left with no other alternative, but to initiate necessary proceedings against you and your sureties "
                            + "for the recovery of entire outstanding balance of Rs. ";//Out standing Amount
                    String rem2Desc4 = ", and other relevant dues, through Registrar Cooperative Societies, Delhi. \n\n\tPlease treat this as MOST URGENT.";
                    for (OverdueEmiReportPojo obj : list) {
                        if (overDueRem.equalsIgnoreCase("2")) {
                            outStandingAmt = loanInterestCalFcdRem.outStandingAsOnDate(obj.getAccountNumber(), dt);//pass here user date
                            if (outStandingAmt.contains("-")) {
                                outStandingAmt = outStandingAmt.substring(outStandingAmt.indexOf("-") + 1);
                            }
                        }
                        //granter
                        List granterInfo = getGranterInfo(obj.getAccountNumber());
                        //
                        String query = "select trim(ifnull(ccmd.CustFullName,'')), concat(trim(ifnull(ccmd.MailAddressLine1,'')),trim(ifnull(MailAddressLine2,''))) as custAdd,  trim(ifnull(ccmd.MailDistrict,'')) as dist, trim(ifnull(ccmd.MailPostalCode,'')), trim(ifnull(ccmd.MailVillage,'')), trim(ifnull(ccmd.MailStateCode,'')),  trim(ifnull(ccmd.mailblock,'')) from cbs_customer_master_detail ccmd where ccmd.customerid = (select custId from customerid where acno = '" + obj.getAccountNumber() + "')";
                        List selectList = em.createNativeQuery(query).getResultList();
                        NPAReminderPojo pojo = new NPAReminderPojo();
                        if (selectList.isEmpty()) {
                            throw new ApplicationException("Customer information not found!");
                        } else {
                            Vector vec = (Vector) selectList.get(0);
                            pojo.setAccountNumber(obj.getAccountNumber().substring(2, 4) + "/" + obj.getAccountNumber());
                            pojo.setCustomerName(vec.get(0).toString().trim());
                            pojo.setCustomerAddress(vec.get(1).toString().trim().concat(vec.get(4).toString().equalsIgnoreCase(null)?"":" "+ vec.get(4).toString()).concat(vec.get(6).toString().equalsIgnoreCase(null)?"":" "+ vec.get(6).toString()));
                            pojo.setCustomerCity(vec.get(2).toString().equalsIgnoreCase(null)?"":vec.get(2).toString().trim());
                            pojo.setCustomerState(vec.get(5).toString().equalsIgnoreCase(null)?"":vec.get(5).toString().trim());
                            pojo.setCustomerPin(vec.get(3).toString().equalsIgnoreCase(null)?"":vec.get(3).toString().trim());
                            pojo.setLoanSenctionDt(obj.getSanctionDate());
                            pojo.setLoanSenctionAmt(formatter.format(obj.getSanctionedamt()) + "");
                            pojo.setMonthlyInstAmt(formatter.format(obj.getInstallmentamt()) + "");
                            if (overDueRem.equalsIgnoreCase("1")) {
                                pojo.setDescription(rem1Desc1 + formatter.format(obj.getAmountOverdue()) + rem1Desc2);
                            } else {
                                pojo.setDescription(rem2Desc1 + formatter.format(obj.getAmountOverdue()) + rem2Desc2 + rem2Desc3 + formatter.format(Double.parseDouble(outStandingAmt)) + rem2Desc4);// code here for reminder 2
                            }
                            if (!granterInfo.isEmpty()) {
                                String granter1 = "Copy To :\n\n";
                                String granter2 = "Copy To :\n\n";
                                for (int i = 0; i < granterInfo.size(); i++) {
                                    Vector vec1 = (Vector) granterInfo.get(i);
                                    if(vec1.get(2).toString().equalsIgnoreCase("")){
                                        if ((i + 1) % 2 == 0) {
                                            granter1 += "Ref. No. R" + overDueRem + "/" + obj.getAccountNumber().trim() + "\n" + "Mr./Ms. " + vec1.get(0).toString().trim() + "\n" + vec1.get(1).toString().trim() + "\n";
                                        } else {
                                            granter2 += "Ref. No. R" + overDueRem + "/" + obj.getAccountNumber().trim() + "\n" + "Mr./Ms. " + vec1.get(0).toString().trim() + "\n" + vec1.get(1).toString().trim() + "\n";
                                        }
                                    } else {
                                        List gurInfo = em.createNativeQuery("select trim(ifnull(ccmd.CustFullName,'')), "
                                                + "concat(if(trim(ifnull(ccmd.MailAddressLine1,''))='', '',concat(trim(ifnull(ccmd.MailAddressLine1,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailAddressLine2,''))='', '',concat(trim(ifnull(ccmd.MailAddressLine2,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailVillage,''))='', '',concat(trim(ifnull(ccmd.MailVillage,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.mailblock,''))='', '',concat(trim(ifnull(ccmd.mailblock,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailDistrict,''))='', '',concat(trim(ifnull(ccmd.MailDistrict,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailStateCode,''))='', '',concat(trim(ifnull(ccmd.MailStateCode,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailPostalCode,''))='', '',concat(trim(ifnull(ccmd.MailPostalCode,'')),' '))) as custAdd "
                                                + " from "
                                                + "cbs_customer_master_detail ccmd where ccmd.customerid  = '"+vec1.get(2).toString()+"'").getResultList();
                                        if(!gurInfo.isEmpty()){
                                            Vector Vect2 = (Vector) gurInfo.get(0);
                                            if ((i + 1) % 2 == 0) {
                                                granter1 += "Ref. No. R" + overDueRem + "/" + obj.getAccountNumber().trim() + "\n" + "Mr./Ms. " + Vect2.get(0).toString().trim() + "\n" + Vect2.get(1).toString().trim() + "\n";
                                            } else {
                                                granter2 += "Ref. No. R" + overDueRem + "/" + obj.getAccountNumber().trim() + "\n" + "Mr./Ms. " + Vect2.get(0).toString().trim() + "\n" + Vect2.get(1).toString().trim() + "\n";
                                            }
                                        }
                                        
                                    }
                                }
                                pojo.setGranter1(granter1.trim());
                                pojo.setGranter2(granter2.trim());
                            }
                        }
                        remlist.add(pojo);
                    }
                } else {
                    // code for reminder 3
                    String rem3Desc1 = "\tWe are too constrained to remind you that despite our sending previous reminders at the time of writing, against the total outstanding "
                            + "loan of Rs. ";
                    String rem3Desc2 = " under the above loan, three month installments are in arrears in settlement of which and the amount due in "
                            + "current month please arrange to remit us immediately total amount as detailed below :- \n \n "
                            + "\t\tOverdue Loan Amount : Rs. ";
                    String rem3Desc3 = " \n \n \tPlease note that if you fail to remit the aforesaid amount with in 10 days after receiving of this letter, "
                            + "we shall regretfully be compelled to institute suitable proceeding for recovery of the entire loan amount and all other relevant dues from you "
                            + "as well as from your sureties, through the office of The Registrar Co-operative Societies, Parliament Street, New Delhi.";
                    for (OverdueEmiReportPojo obj : list) {
                        if (overDueRem.equalsIgnoreCase("3")) {
                            outStandingAmt = loanInterestCalFcdRem.outStandingAsOnDate(obj.getAccountNumber(), dt);//pass here user date
                            if (outStandingAmt.contains("-")) {
                                outStandingAmt = outStandingAmt.substring(outStandingAmt.indexOf("-") + 1);
                            }
                        }
                        //granter
                        List granterInfo = getGranterInfo(obj.getAccountNumber());
                        //
                        String query = "select trim(ccmd.CustFullName), concat(trim(ccmd.MailAddressLine1),trim(MailAddressLine2)) as custAdd,  trim(ccmd.MailDistrict) as dist, trim(ccmd.MailPostalCode), trim(ccmd.MailVillage),trim(ccmd.MailStateCode),  trim(ccmd.mailblock) from cbs_customer_master_detail ccmd where ccmd.customerid = (select custId from customerid where acno = '" + obj.getAccountNumber() + "')";
                        List selectList = em.createNativeQuery(query).getResultList();
                        NPAReminderPojo pojo = new NPAReminderPojo();
                        if (selectList.isEmpty()) {
                            throw new ApplicationException("Customer information not found!");
                        } else {
                            Vector vec = (Vector) selectList.get(0);
                            pojo.setAccountNumber(obj.getAccountNumber().substring(2, 4) + "/" +obj.getAccountNumber());
                            pojo.setCustomerName(vec.get(0).toString().trim());
                            pojo.setCustomerAddress(vec.get(1).toString().trim().concat(vec.get(4).toString().equalsIgnoreCase(null)?"":" "+ vec.get(4).toString()).concat(vec.get(6).toString().equalsIgnoreCase(null)?"":" "+ vec.get(6).toString()));
                            pojo.setCustomerCity(vec.get(2).toString().equalsIgnoreCase(null)?"":vec.get(2).toString().trim());
                            pojo.setCustomerState(vec.get(5).toString().equalsIgnoreCase(null)?"":vec.get(5).toString().trim());
                            pojo.setCustomerPin(vec.get(3).toString().equalsIgnoreCase(null)?"":vec.get(3).toString().trim());                            
                            pojo.setLoanSenctionDt(obj.getSanctionDate());
                            pojo.setLoanSenctionAmt(formatter.format(obj.getSanctionedamt()) + "");
                            pojo.setMonthlyInstAmt(formatter.format(obj.getInstallmentamt()) + "");
                            //if (overDueRem.equalsIgnoreCase("3")) {
                            pojo.setDescription(rem3Desc1 + formatter.format(Double.parseDouble(outStandingAmt)) + rem3Desc2 + formatter.format(obj.getAmountOverdue()) + rem3Desc3);
                            //}
                            if (!granterInfo.isEmpty()) {
                                String granter1 = "Copy To :\n\n";
                                String granter2 = "Copy To :\n\n";
                                for (int i = 0; i < granterInfo.size(); i++) {
                                    Vector vec1 = (Vector) granterInfo.get(i);
                                    if(vec1.get(2).toString().equalsIgnoreCase("")){
                                        if ((i + 1) % 2 == 0) {
                                            granter1 += "Ref. No. R" + overDueRem + "/" + obj.getAccountNumber().trim() + "\n" + "Mr./Ms. " + vec1.get(0).toString().trim() + "\n" + vec1.get(1).toString().trim() + "\n";
                                        } else {
                                            granter2 += "Ref. No. R" + overDueRem + "/" + obj.getAccountNumber().trim() + "\n" + "Mr./Ms. " + vec1.get(0).toString().trim() + "\n" + vec1.get(1).toString().trim() + "\n";
                                        }
                                    } else {
                                        List gurInfo = em.createNativeQuery("select trim(ifnull(ccmd.CustFullName,'')), "
                                                + "concat(if(trim(ifnull(ccmd.MailAddressLine1,''))='', '',concat(trim(ifnull(ccmd.MailAddressLine1,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailAddressLine2,''))='', '',concat(trim(ifnull(ccmd.MailAddressLine2,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailVillage,''))='', '',concat(trim(ifnull(ccmd.MailVillage,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.mailblock,''))='', '',concat(trim(ifnull(ccmd.mailblock,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailDistrict,''))='', '',concat(trim(ifnull(ccmd.MailDistrict,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailStateCode,''))='', '',concat(trim(ifnull(ccmd.MailStateCode,'')),' ')),"
                                                + "if(trim(ifnull(ccmd.MailPostalCode,''))='', '',concat(trim(ifnull(ccmd.MailPostalCode,'')),' '))) as custAdd "
                                                + " from "
                                                + "cbs_customer_master_detail ccmd where ccmd.customerid  = '"+vec1.get(2).toString()+"'").getResultList();
                                        if(!gurInfo.isEmpty()){
                                            Vector Vect2 = (Vector) gurInfo.get(0);
                                            if ((i + 1) % 2 == 0) {
                                                granter1 += "Ref. No. R" + overDueRem + "/" + obj.getAccountNumber().trim() + "\n" + "Mr./Ms. " + Vect2.get(0).toString().trim() + "\n" + Vect2.get(1).toString().trim() + "\n";
                                            } else {
                                                granter2 += "Ref. No. R" + overDueRem + "/" + obj.getAccountNumber().trim() + "\n" + "Mr./Ms. " + Vect2.get(0).toString().trim() + "\n" + Vect2.get(1).toString().trim() + "\n";
                                            }
                                        }
                                        
                                    }
                                }
                                pojo.setGranter1(granter1.trim());
                                pojo.setGranter2(granter2.trim());
                            }
                        }
                        remlist.add(pojo);
                    }
                }

            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return remlist;

    }
    
//  public List<OverdueRemainderPojo> getoverdueRemainderData(String acno , String dt ,String brnCode,String genDate,String UserName)throws ApplicationException {
//       List<OverdueRemainderPojo> remlist = new ArrayList<OverdueRemainderPojo>();
//        NumberFormat formatter = new DecimalFormat("0.00");
//         String outStandingAmt = "";
//       try{
//         List bnklist = em.createNativeQuery(" select BANK_CODE from mb_sms_sender_bank_detail ").getResultList();
//         Vector ele = (Vector) bnklist.get(0);
//         String bankCode = ele.get(0).toString().trim();
//         
//         List actypelist = em.createNativeQuery(" select AcctType from accounttypemaster where AcctCode = substring('"+acno+"',3,2)").getResultList();
//         Vector ele1 = (Vector) actypelist.get(0);
//         String acType = ele1.get(0).toString().trim();
//         String  yearCode= genDate.substring(8,10);
//         int code = Integer.parseInt(yearCode)+1;
//         String yearSession = genDate.substring(6,10)+"-" + code;
//          
//          
//         String query ="select trim(ccmd.CustFullName), concat(trim(ccmd.MailAddressLine1),trim(MailAddressLine2)) as custAdd, "
//                 + "trim(ccmd.MailDistrict) as dist, trim(ccmd.MailPostalCode), trim(ccmd.MailVillage),trim(ccmd.MailStateCode),  "
//                 + "trim(ccmd.mailblock) from cbs_customer_master_detail ccmd where ccmd.customerid = (select custId from customerid where "
//                 + "acno = '"+acno+"')";  
//         List selectList = em.createNativeQuery(query).getResultList();
//         if(selectList.isEmpty()){
//             throw new ApplicationException("There is no data for this account number");
//         }
//          Vector vec = (Vector) selectList.get(0);
//          String custName=vec.get(0).toString().trim();
//          String address = vec.get(1).toString().trim()+","+ vec.get(2).toString().trim();
//       
//          String query1 ="select ACNo,OpeningDt,ODLimit from accountmaster  where ACNo='"+acno+"' ";
//          List acList = em.createNativeQuery(query1).getResultList();
//          Vector vec1 = (Vector) acList.get(0);
//          String acNo = vec1.get(0).toString().trim();
//          String sancDt = vec1.get(1).toString().trim();
//          BigDecimal sanAmt =new BigDecimal(vec1.get(2).toString().trim());
//          
//          String query2 ="select acno,count(*) as noOfDueEmi,sum(INSTALLAMT) as emiDueAmt,sum(INTERESTAMT) as overdueIntrest,sum(PRINAMT) as totalDuePrincAmt from emidetails "
//                  + "where status = 'UNPAID' and acno ='"+acNo+"' and DUEDT<='"+dt+"' group by acno ";
//          List emiList = em.createNativeQuery(query2).getResultList();
//          if(emiList.isEmpty()){
//              throw new ApplicationException("There is no emi details for this account number. ");
//          }
//          Vector vec2 = (Vector) emiList.get(0);
//          String emiDue = vec2.get(1).toString().trim();
//          BigDecimal emiDueAmt =new BigDecimal(vec2.get(2).toString());
//          BigDecimal overDueInterest = new BigDecimal(vec2.get(3).toString());
//          BigDecimal totalDuePrincAmt = new BigDecimal(vec2.get(4).toString());
//          outStandingAmt = loanInterestCalFcdRem.outStandingAsOnDate(acno, dt);//pass here user date
//                            if (outStandingAmt.contains("-")) {
//                                outStandingAmt = outStandingAmt.substring(outStandingAmt.indexOf("-") + 1);
//                            }
//           String fileNo = "1";
//          List userList = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
//                    + "where file_gen_date='" +ymd.format(dmyFormat.parse(genDate))+ "' and "
//                    + "file_gen_type ='REFNO'").getResultList();
//           Vector elem = (Vector) userList.get(0);
//          
//            if (elem.get(0) != null) {
//                fileNo = elem.get(0).toString().trim();  //Make to 6 digit.
//            }                 
//         String RefNo = bankCode+"/"+acType+"/"+"Default"+"." +ParseFileUtil.addTrailingZeros(fileNo,3) +"/"+yearSession;
//         
//
//                            
//            OverdueRemainderPojo pojo = new OverdueRemainderPojo();
//            pojo.setAcNo(acNo);
//            pojo.setAddress(address);
//            pojo.setCustName(custName);
//            pojo.setAsOnDt(dt);
//            pojo.setLoanAmt(sanAmt);
//            pojo.setOutStandingBalance(new BigDecimal(outStandingAmt));
//            pojo.setOverdueEmi(Integer.parseInt(emiDue));
//            pojo.setOverdueIntrest(overDueInterest);
//            pojo.setPrincipleAmt(totalDuePrincAmt);
//            pojo.setSanctionDT(sancDt);
//            pojo.setTotalDue(emiDueAmt);
//            pojo.setRefno(RefNo);
//          
//            remlist.add(pojo);
//      
//      }catch(Exception ex){
//          throw new ApplicationException(ex.getMessage());
//      }
//       return remlist;
//  }   
  
  public List<OverdueRemainderPojo> getoverdueRemainderData(String acno , String dt ,String brnCode,String genDate,String UserName)throws ApplicationException {
       List<OverdueRemainderPojo> remlist = new ArrayList<OverdueRemainderPojo>();
        NumberFormat formatter = new DecimalFormat("0.00");
         String outStandingAmt = "";
         String totalDue1="";
       try{
         List bnklist = em.createNativeQuery(" select BANK_CODE from mb_sms_sender_bank_detail ").getResultList();
         Vector ele = (Vector) bnklist.get(0);
         String bankCode = ele.get(0).toString().trim();
         
         List actypelist = em.createNativeQuery(" select acctNature from accounttypemaster where AcctCode = substring('"+acno+"',3,2)").getResultList();
         Vector ele1 = (Vector) actypelist.get(0);
         String acType = ele1.get(0).toString().trim();
         String  yearCode= genDate.substring(8,10);
         int code = Integer.parseInt(yearCode)+1;
         String yearSession = genDate.substring(6,10)+"-" + code;
         
         String query ="select trim(ccmd.CustFullName), concat(trim(ccmd.MailAddressLine1),trim(MailAddressLine2)) as custAdd, "
                 + "trim(ccmd.MailDistrict) as dist, trim(ccmd.MailPostalCode), trim(ccmd.MailVillage),trim(ccmd.MailStateCode),  "
                 + "trim(ccmd.mailblock) from cbs_customer_master_detail ccmd where ccmd.customerid = (select custId from customerid where "
                 + "acno = '"+acno+"')";  
         List selectList = em.createNativeQuery(query).getResultList();
         if(selectList.isEmpty()){
             throw new ApplicationException("There is no data for this account number");
         }
          Vector vec = (Vector) selectList.get(0);
          String custName=vec.get(0).toString().trim();
          String address = vec.get(1).toString().trim()+","+ vec.get(2).toString().trim();
       
          String query1 ="select ACNo,OpeningDt,ODLimit from accountmaster  where ACNo='"+acno+"' ";
          List acList = em.createNativeQuery(query1).getResultList();
          Vector vec1 = (Vector) acList.get(0);
          String acNo = vec1.get(0).toString().trim();
          String sancDt = vec1.get(1).toString().trim();
          BigDecimal sanAmt =new BigDecimal(vec1.get(2).toString().trim());
          
          List acstatusquery = em.createNativeQuery("select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  "
                  + "from accountstatus ast, (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa,"
                  + "(select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '"+dt+"' "
                  + "and SPFLAG IN (11,12,13)  group by acno) b where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13) "
                  + "and aa.acno = '"+acNo+"' group by aa.acno,aa.effdt) c where ast.acno = c.aano and "
                  + "ast.effdt = c.aadt and ast.spno = c.sno ").getResultList();
          
          if(acstatusquery.isEmpty()){
           String query2 ="select acno,count(*) as noOfDueEmi,sum(INSTALLAMT) as emiDueAmt,sum(INTERESTAMT) as overdueIntrest,sum(PRINAMT) as totalDuePrincAmt from emidetails "
                  + "where status = 'UNPAID' and acno ='"+acNo+"' and DUEDT<='"+dt+"' group by acno ";
          List emiList = em.createNativeQuery(query2).getResultList();
          if(emiList.isEmpty()){
              throw new ApplicationException("There is no emi details for this account number. ");
          }
          Vector vec2 = (Vector) emiList.get(0);
          String emiDue = vec2.get(1).toString().trim();
          BigDecimal emiDueAmt =new BigDecimal(vec2.get(2).toString());
          BigDecimal overDueInterest = new BigDecimal(vec2.get(3).toString());
          BigDecimal totalDuePrincAmt = new BigDecimal(vec2.get(4).toString());
          outStandingAmt = loanInterestCalFcdRem.outStandingAsOnDate(acno, dt);//pass here user date
                            if (outStandingAmt.contains("-")) {
                                outStandingAmt = outStandingAmt.substring(outStandingAmt.indexOf("-") + 1);
                            }
           String fileNo = "1";
          List userList = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" +ymd.format(dmyFormat.parse(genDate))+ "' and "
                    + "file_gen_type ='REFNO'").getResultList();
           Vector elem = (Vector) userList.get(0);
          
            if (elem.get(0) != null) {
                fileNo = elem.get(0).toString().trim();  //Make to 6 digit.
            }                 
         String RefNo = bankCode+"/"+acType+"/"+"Default"+"." +ParseFileUtil.addTrailingZeros(fileNo,3) +"/"+yearSession;
         

                            
            OverdueRemainderPojo pojo = new OverdueRemainderPojo();
            pojo.setAcNo(acNo);
            pojo.setAddress(address);
            pojo.setCustName(custName);
            pojo.setAsOnDt(dt);
            pojo.setLoanAmt(sanAmt);
            pojo.setOutStandingBalance(new BigDecimal(outStandingAmt));
            pojo.setOverdueEmi(Integer.parseInt(emiDue));
            pojo.setOverdueIntrest(overDueInterest);
            pojo.setPrincipleAmt(totalDuePrincAmt);
            pojo.setSanctionDT(sancDt);
            pojo.setTotalDue(emiDueAmt);
            pojo.setRefno(RefNo);
          
            remlist.add(pojo);   
          }else{
            String query3= "select ifnull(sum(CRAMT-dramt),0) as intAmt from npa_recon where acno = '"+acNo+"' and dt<= '"+dt+"' ;";
            List interestList = em.createNativeQuery(query3).getResultList();
            if(interestList.isEmpty()){
             throw new ApplicationException("There is no emi details for this account number. ");
            }
            Vector vec3 = (Vector)interestList.get(0);
            String interestDue = vec3.get(0).toString();
            if (interestDue.contains("-")) {
                              String totalDue2 = interestDue.substring(interestDue.indexOf("-") + 1);
                               interestDue = totalDue2;
                            }
           String query5 = "select sum(INSTALLAMT) from emidetails where  DUEDT<=('"+dt+"') and acno='"+acNo+"' and STATUS ='UNPAID'";
            List totalPrinOverDue = em.createNativeQuery(query5).getResultList();
            if(totalPrinOverDue.isEmpty()){
                throw new ApplicationException("There is no principle Amount In EmiDetail for this account.");
            }
            double totalPrinAmt=0.0;
            Vector vec5 = (Vector) totalPrinOverDue.get(0);
            totalPrinAmt = Double.parseDouble(vec5.get(0).toString());
            
            double excessAmt =0.0;
            String query6 ="select excessamt from cbs_loan_emi_excess_details WHERE ACNO='"+acNo+"'";
            List excessAmtList = em.createNativeQuery(query6).getResultList();
            if(!excessAmtList.isEmpty()){
                Vector vec6 = (Vector) excessAmtList.get(0);
                excessAmt = Double.parseDouble(vec6.get(0).toString());
            }
            double totalDueAmt = totalPrinAmt-excessAmt ;
            double principleAmt =totalDueAmt - Double.parseDouble(interestDue);
            
            
            
             String query4 = "select acno,count(*) as noOfDueEmi from emidetails where status = 'UNPAID' and acno ='"+acNo+"' and DUEDT <='"+dt+"' group by acno";
             List noOFEmiList  = em.createNativeQuery(query4).getResultList();
            if(noOFEmiList.isEmpty()){
             throw new ApplicationException("There is no emi  for this account number. ");
            }
            int noOfDueEmi=0;
            Vector vec4 = (Vector)noOFEmiList.get(0);
            noOfDueEmi = Integer.parseInt(vec4.get(1).toString());
            
//           outStandingAmt = loanInterestCalFcdRem.outStandingAsOnDate(acno, dt);//pass here user date
//                            if (outStandingAmt.contains("-")) {
//                                outStandingAmt = outStandingAmt.substring(outStandingAmt.indexOf("-") + 1);
//                            }
           String fileNo = "1";
          List userList = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" +ymd.format(dmyFormat.parse(genDate))+ "' and "
                    + "file_gen_type ='REFNO'").getResultList();
           Vector elem = (Vector) userList.get(0);
          
            if (elem.get(0) != null) {
                fileNo = elem.get(0).toString().trim();  //Make to 6 digit.
            }                 
         String RefNo = bankCode+"/"+acType+"/"+"Default"+"." +ParseFileUtil.addTrailingZeros(fileNo,3) +"/"+yearSession;
         
            OverdueRemainderPojo pojo = new OverdueRemainderPojo();
            pojo.setAcNo(acNo);
            pojo.setAddress(address);
            pojo.setCustName(custName);
            pojo.setAsOnDt(dt);
            pojo.setLoanAmt(sanAmt);
            pojo.setOutStandingBalance(new BigDecimal(totalDueAmt));
            pojo.setOverdueEmi(noOfDueEmi);
            pojo.setOverdueIntrest(new BigDecimal(interestDue));
            pojo.setPrincipleAmt(new BigDecimal(principleAmt));
            pojo.setSanctionDT(sancDt);
            pojo.setTotalDue(new BigDecimal(totalDueAmt));
            pojo.setRefno(RefNo);
          
            remlist.add(pojo);  
          }
        }catch(Exception ex){
          throw new ApplicationException(ex.getMessage());
      }
       return remlist;
  }   
  
  public String insertionRefNo(String fileNo,String genDate,String refNo,String UserName,String brnCode) throws ApplicationException{
       UserTransaction ut = context.getUserTransaction();
    try{
        ut.begin();
        int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
            + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ",'"
            + ymd.format(dmyFormat.parse(genDate))  + "','" + refNo + "','" + UserName + "',now(),'"
            + brnCode + "','REFNO')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }
            ut.commit();
    }catch(Exception ex){
        
        throw new ApplicationException(ex.getMessage());
    }
    return "true";
  }

   
  public String getSqlFormatDate(String dt) {
        dt = dt.substring(6, 10) + "-" + dt.substring(3, 5) + "-" + dt.substring(0, 2);
        return dt;
    }
    // temprory using for granter

    public List<OverdueEmiReportPojo> getNPAOverDue(String branchCode) throws ApplicationException {
        try {
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return null;
    }

    public BigDecimal getAmount(String acNumber, String chargeName, String date, String glHead) throws ApplicationException {
        BigDecimal amount = new BigDecimal(BigInteger.ZERO);
        try {
            String query = "select ifnull((select amt from cbs_charge_detail where ac_type = substring('" + acNumber + "',3,2) and charge_name = '" + chargeName + "' and  '" + date + "' between eff_date and eff_to_date ),0)as tem";
            List list = em.createNativeQuery(query).getResultList();
            if (!list.isEmpty()) {
                Vector vec = (Vector) list.get(0);
                amount = BigDecimal.valueOf(Math.abs(Double.parseDouble(vec.get(0).toString())));
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return amount;
    }

    public String getGlHead(String acNumber, String chargeName, String date) throws ApplicationException {
        String glHeadNumber = "";
        try {
            String query = "select ifnull((select cr_gl_head from cbs_charge_detail where ac_type = substring('" + acNumber + "',3,2) and charge_name = '" + chargeName + "' and  '" + date + "' between  eff_date and eff_to_date ),0)as tem";
            List list = em.createNativeQuery(query).getResultList();
            if (!list.isEmpty()) {
                Vector vec = (Vector) list.get(0);
                glHeadNumber = vec.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return glHeadNumber;
    }

    public List getBankDistCityPin(String branchCode) throws ApplicationException {
        List list = new ArrayList();
        try {
            List selectList = em.createNativeQuery("SELECT br.city, br.State, br.pincode FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and br.brncode=" + branchCode).getResultList();

            if (!selectList.isEmpty()) {
                Vector ele = (Vector) selectList.get(0);
                list.add(ele.get(0));
                list.add(ele.get(1));
                list.add(ele.get(2));
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public List getGranterInfo(String acNo) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select name, address, ifnull(GAR_CUSTID,'') from loan_guarantordetails where acno = '" + acNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }
}
