/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.BankLevelSTRPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
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
@Stateless(mappedName = "StrAlertFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class StrAlertFacade implements StrAlertFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private CommonReportMethodsRemote commonReport;
    SimpleDateFormat ymd_Format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat yMMd = new SimpleDateFormat("dd/MM/yyyy");
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public String getStrAlert(String custid, String dt, String ty, String tranType, String amt) throws ApplicationException {
        String msg = "", strEnable = "N";
        try {
//            System.out.println("start Time:?????"+new Date());
            List strEnableList = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo where NAME = 'STR-ENABLE' ").getResultList();
            if (!strEnableList.isEmpty()) {
                Vector strVect = (Vector) strEnableList.get(0);
                strEnable = strVect.get(0).toString();
            }
            if (strEnable.equalsIgnoreCase("Y")) {
                /*CASH*/
                /*TM1.1 High value cash deposits in a day*/
                if (ty.equalsIgnoreCase("0") && tranType.equalsIgnoreCase("0")) {
                    String query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'TM1.1' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and levelid = 'C' and alerttype = 'TM1.1' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";
//                System.out.println("TM1.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM1.1! High value cash deposits in a day";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }

                    /*TM2.1 High value cash deposits in a month*/
                    query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'TM2.1' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and levelid = 'C' and alerttype = 'TM2.1' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";
//                System.out.println("TM2.1:query>>" + query);
                    resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM2.1! High value cash deposits in a month";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }


                if (ty.equalsIgnoreCase("1") && tranType.equalsIgnoreCase("0")) {
                    /*TM1.2 High value cash withdrawals in a day*/
                    String query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'TM1.2' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and levelid = 'C' and alerttype = 'TM1.2' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";
//                System.out.println("TM1.2:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM1.2! High value cash withdrawals in a day";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }

                    /*TM2.2! High value cash withdrawals in a month*/
                    query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType = 0 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'TM2.2' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and levelid = 'C' and alerttype = 'TM2.2' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";
//                System.out.println("TM2.2:query>>" + query);
                    resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM2.2! High value cash withdrawals in a month";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }
                /*NON-CASH*/
                /*TM1.3 High value non-cash deposit in a day*/
                if (ty.equalsIgnoreCase("0") && !tranType.equalsIgnoreCase("0")) {
                    String query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'TM1.3' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and levelid = 'C' and alerttype = 'TM1.3' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";
//                System.out.println("TM1.3:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM1.3! High value non-cash deposit in a day";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }

                    /*TM2.3! High value non-cash deposit in a month*/
                    query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 0 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'TM2.3' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and levelid = 'C' and alerttype = 'TM2.3' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";
//                System.out.println("TM2.3:query>>" + query);
                    resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM2.3! High value non-cash deposit in a month";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }


                if (ty.equalsIgnoreCase("1") && !tranType.equalsIgnoreCase("0")) {
                    /*TM1.4 High value non-cash withdrawals in a day*/
                    String query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt = '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'TM1.4' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and levelid = 'C' and alerttype = 'TM1.4' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";
//                System.out.println("TM1.4:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM1.4! High value non-cash withdrawals in a day";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }

                    /*TM2.4! High value non-cash withdrawals in a month*/
                    query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' and ty = 1 and TranType <> 0 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'TM2.4' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and levelid = 'C' and alerttype = 'TM2.4' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";
//                System.out.println("TM2.4:query>>" + query);
                    resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM2.4! High value non-cash withdrawals in a month";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TM3.1 Sudden high value transaction for the client*/
                if (ty.equalsIgnoreCase("0")) {
                    String query = "select distinct acc.custId, acc.cramt, acc.acctCategory, if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as percentageDefined, cast(((" + amt + "*100)/acc.cramt) as decimal(25,2)) as percentage from cbs_alert_indicater_info rt, "
                            + " (select distinct aa.CustId as custId, max(dd.cramt) as cramt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(max(ifnull(cramt,0)),0) as cramt from recon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(cramt,0)),0) as cramt from ca_recon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(cramt,0)),0) as cramt from loan_recon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(cramt,0)),0) as cramt from td_recon where acno in "
                            + " (select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(cramt,0)),0) as cramt from rdrecon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  select acno, ifnull(max(ifnull(cramt,0)),0) as cramt from of_recon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  )dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) acc "
                            + " where rt.alertType = 'TM3.1'  and rt.levelId = 'B' "
                            + " and txnid = (select max(txnid) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TM3.1' and enterDate<='" + dt + "') "
                            + " and cast(if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as decimal(25,2)) < cast(((" + amt + "*100)/acc.cramt) as decimal(25,2))";
//                System.out.println("TM3.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM3.1! Sudden high value transaction for the client";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }
                if (ty.equalsIgnoreCase("1")) {
                    String query = "select distinct acc.custId, acc.dramt, acc.acctCategory, if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as percentageDefined, cast(((" + amt + "*100)/acc.dramt) as decimal(25,2)) as percentage from cbs_alert_indicater_info rt, "
                            + " (select distinct aa.CustId as custId, max(dd.dramt) as dramt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(max(ifnull(dramt,0)),0) as dramt from recon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(dramt,0)),0) as dramt from ca_recon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(dramt,0)),0) as dramt from loan_recon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(dramt,0)),0) as dramt from td_recon where acno in "
                            + " (select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(dramt,0)),0) as dramt from ddstransaction where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  "
                            + " select acno, ifnull(max(ifnull(dramt,0)),0) as dramt from rdrecon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  "
                            + " union all  select acno, ifnull(max(ifnull(dramt,0)),0) as dramt from of_recon where acno in "
                            + " (select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt <= '" + dt + "' group by acno  )dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) acc "
                            + " where rt.alertType = 'TM3.1'  and rt.levelId = 'B' "
                            + " and txnid = (select max(txnid) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TM3.1' and enterDate<='" + dt + "') "
                            + " and cast(if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as decimal(25,2)) < cast(((" + amt + "*100)/acc.dramt) as decimal(25,2))";
//                System.out.println("TM3.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM3.1! Sudden high value transaction for the client";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }
                /*TM3.2 Sudden increase in value of transactions in a month for the client*/
                if (ty.equalsIgnoreCase("0")) {
                    String query = "select distinct acc.custId, acc.avgCrAmt, mbal.monthCrAmt, acc.acctCategory, if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as percentageDefined, cast(((mbal.monthCrAmt*100)/acc.avgCrAmt) as decimal(25,2)) as percentage from cbs_alert_indicater_info rt, "
                            + " (select distinct aa.CustId as custId, cast(sum(dd.cramt)/(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') as decimal(25,2)) as avgCrAmt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(cramt,0) as cramt, dt from recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "' and ty = 0  and trandesc in (0,1,2,66,70) group by acno,dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from ca_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from loan_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from td_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from ddstransaction where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from rdrecon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from of_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  )dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) acc, "
                            + " (select distinct aa.CustId as custId, cast(sum(dd.cramt) as decimal(25,2)) as monthCrAmt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(cramt,0) as cramt from recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "' and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from ca_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from loan_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from td_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from ddstransaction where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from rdrecon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from of_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt)dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) mbal "
                            + " where mbal.custId =acc.custId and rt.alertType = 'TM3.2'  and rt.levelId = 'B' and "
                            + " txnid = (select max(txnid) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TM3.2' and enterDate<='" + dt + "') "
                            + " and cast(if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as decimal(25,2)) < cast(((mbal.monthCrAmt*100)/acc.avgCrAmt) as decimal(25,2))";
//                System.out.println("TM3.2:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM3.2! Sudden increase in value of transactions in a month for the client";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }
                if (ty.equalsIgnoreCase("1")) {
                    String query = "select distinct acc.custId, acc.avgDrAmt, mbal.monthDrAmt, acc.acctCategory, if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as percentageDefined, cast(((mbal.monthDrAmt*100)/acc.avgDrAmt) as decimal(25,2)) as percentage from cbs_alert_indicater_info rt, "
                            + " (select distinct aa.CustId as custId, cast(sum(dd.dramt)/(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') as decimal(25,2)) as avgDrAmt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(dramt,0) as dramt, dt from recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "' and ty = 1  and trandesc in (0,1,2,66,70) group by acno,dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from ca_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from loan_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from td_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from ddstransaction where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from rdrecon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from of_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.2' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  )dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) acc, "
                            + " (select distinct aa.CustId as custId, cast(sum(dd.dramt) as decimal(25,2)) as monthDrAmt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(dramt,0) as dramt from recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "' and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt"
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from ca_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from loan_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from td_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from ddstransaction where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from rdrecon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from of_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as dramt)dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) mbal "
                            + " where mbal.custId =acc.custId and rt.alertType = 'TM3.2'  and rt.levelId = 'B' and "
                            + " txnid = (select max(txnid) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TM3.2' and enterDate<='" + dt + "') "
                            + " and cast(if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as decimal(25,2)) < cast(((mbal.monthDrAmt*100)/acc.avgDrAmt) as decimal(25,2))";
//                System.out.println("TM3.2:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM3.2! Sudden increase in value of transactions in a month for the client";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TM3.3 Sudden increase in number of transactions in a month for the client*/
                if (ty.equalsIgnoreCase("0")) {
                    String query = "select distinct acc.custId, acc.avgCrAmt, mbal.monthCrAmt, acc.acctCategory, if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as percentageDefined, cast(((mbal.monthCrAmt*100)/acc.avgCrAmt) as decimal(25,2)) as percentage from cbs_alert_indicater_info rt, "
                            + " (select distinct aa.CustId as custId, cast(count(dd.cramt)/(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') as decimal(25,2)) as avgCrAmt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(cramt,0) as cramt, dt from recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "' and ty = 0  and trandesc in (0,1,2,66,70) group by acno,dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from ca_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from loan_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from td_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from ddstransaction where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from rdrecon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt, dt from of_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  )dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) acc, "
                            + " (select distinct aa.CustId as custId, cast(count(dd.cramt) as decimal(25,2)) as monthCrAmt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(cramt,0) as cramt from recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "' and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt"
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from ca_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from loan_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from td_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from ddstransaction where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from rdrecon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(cramt,0) as cramt from of_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 0  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt)dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) mbal "
                            + " where mbal.custId =acc.custId and rt.alertType = 'TM3.3'  and rt.levelId = 'B' and "
                            + " txnid = (select max(txnid) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TM3.3' and enterDate<='" + dt + "') "
                            + " and cast(if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as decimal(25,2)) < cast(((mbal.monthCrAmt*100)/acc.avgCrAmt) as decimal(25,2))";
//                System.out.println("TM3.3:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM3.3! Sudden increase in number of transactions in a month for the client";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }
                if (ty.equalsIgnoreCase("1")) {
                    String query = "select distinct acc.custId, acc.avgDrAmt, mbal.monthDrAmt, acc.acctCategory, if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as percentageDefined, cast(((mbal.monthDrAmt*100)/acc.avgDrAmt) as decimal(25,2)) as percentage from cbs_alert_indicater_info rt, "
                            + " (select distinct aa.CustId as custId, cast(count(dd.dramt)/(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') as decimal(25,2)) as avgDrAmt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(dramt,0) as dramt, dt from recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "' and ty = 1  and trandesc in (0,1,2,66,70) group by acno,dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from ca_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from loan_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from td_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from ddstransaction where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from rdrecon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt, dt from of_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (SELECT date_format(ADDDATE('" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TM3.3' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and  '" + CbsUtil.dateAdd(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)), -1) + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  )dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) acc, "
                            + " (select distinct aa.CustId as custId, cast(count(dd.dramt) as decimal(25,2)) as monthDrAmt, cc.acctCategory as acctCategory from customerid aa, accountmaster cc,  "
                            + " (select acno, ifnull(dramt,0) as dramt from recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "' and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from ca_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from loan_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from td_recon where acno in "
                            + " ( select distinct a.Acno from customerid a, td_accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from ddstransaction where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from rdrecon where acno in "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt  "
                            + " union all  "
                            + " select acno, ifnull(dramt,0) as dramt from of_recon where acno in "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and  "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'  and ty = 1  and trandesc in (0,1,2,66,70) group by acno, dt "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as dramt)dd "
                            + " where aa.Acno = cc.ACNo and  aa.Acno = dd.acno ) mbal "
                            + " where mbal.custId =acc.custId and rt.alertType = 'TM3.3'  and rt.levelId = 'B' and "
                            + " txnid = (select max(txnid) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TM3.3' and enterDate<='" + dt + "') "
                            + " and cast(if(rt.noOfTxn='[Z]',100, rt.noOfTxn) as decimal(25,2)) < cast(((mbal.monthDrAmt*100)/acc.avgDrAmt) as decimal(25,2))";
//                System.out.println("TM3.3:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM3.3! Sudden increase in number of transactions in a month for the client";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TM4.1 High value transactions in a new account*/
                if (ty.equalsIgnoreCase("0") || ty.equalsIgnoreCase("1")) {
                    String query = "select a.acno, c.OpeningDt,TIMESTAMPDIFF(MONTH, c.OpeningDt, '" + dt + "') as monthDiff, a.amt, "
                            + " if(b.fromAmt='[X]',0,b.fromAmt) as amtGreaterThan, if(b.dayMonth='[Y]',0,b.dayMonth) as noOfMonth from "
                            + " (select CONVERT('" + custid + "' USING utf8) as acno, cast(CONVERT(" + amt + " USING utf8) as decimal(25,2)) as amt) a,   "
                            + " cbs_alert_indicater_info b, " + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + " c where a.acno = c.acno and "
                            + " b.alertType = 'TM4.1' and b.enterdate <='" + dt + "' "
                            + " and TIMESTAMPDIFF(MONTH, c.OpeningDt, '" + dt + "')<if(b.dayMonth='[Y]',0,b.dayMonth) "
                            + " and a.amt>if(b.fromAmt='[X]',0,b.fromAmt)";
//                System.out.println("TM4.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM4.1! High value transactions in a new account";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TM4.2 High activity in a new account*/
                if (ty.equalsIgnoreCase("0") || ty.equalsIgnoreCase("1")) {
                    String query = "select a.acno, c.OpeningDt,TIMESTAMPDIFF(MONTH, c.OpeningDt, '" + dt + "') as monthDiff, a.amt,"
                            + " if(b.noOfTxn='[N]',0,b.noOfTxn) as amtGreaterThan, if(b.dayMonth='[Y]',0,b.dayMonth) as noOfMonth from "
                            + " (select acno, count(dt)+1 as amt from " + CbsUtil.getReconTableName(commonReport.getAcNatureByAcNo(custid)) + " where acno = '" + custid + "' and trandesc in (0,1,2,66,70) and "
                            + " dt<='" + dt + "' ) a, cbs_alert_indicater_info b, " + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + " c where a.acno = c.acno "
                            + " and b.alertType = 'TM4.2' and b.enterdate <='" + dt + "' "
                            + " and TIMESTAMPDIFF(MONTH, c.OpeningDt, '" + dt + "')<if(b.dayMonth='[Y]',0,b.dayMonth) "
                            + " and a.amt>if(b.noOfTxn='[N]',0,b.noOfTxn)";
//                System.out.println("TM4.2:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM4.2! High activity in a new account";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TM5.1 High value transactions in a dormant account*/
                if (ty.equalsIgnoreCase("0") || ty.equalsIgnoreCase("1")) {
                    String query = "select aa.acno, cc.amt, date_format(aa.dorEffDt,'%Y%m%d') as dorEffDt, aa.npaSpflag, aa.npaSno, date_format(bb.operEffDt,'%Y%m%d') as operEffDt, "
                            + " bb.operStatus, bb.operSno, TIMESTAMPDIFF(DAY, bb.operEffDt, '" + dt + "') as operDiff, if(dd.dayMonth='[Y]',0,dd.dayMonth) as masterDayDiff  from "
                            + " (select a.acno, npa.npaEffDt as dorEffDt, npa.npaSpflag, c.sno as npaSno     from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag, c.sno  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <= '" + dt + "' and acno = '" + custid + "' and SPFLAG IN (2)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' and acno = '" + custid + "' and SPFLAG IN (2) group by acno) c , "
                            + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + "  ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + dt + "' ) aa, "
                            + " (select a.acno, npa.npaEffDt as operEffDt , ac.accstatus as operStatus , npa.sno as operSno  from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag, c.sno  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <= '" + dt + "' and acno = '" + custid + "' and SPFLAG IN (1)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (1) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' and acno = '" + custid + "' group by acno) c , "
                            + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + "  ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (1) and ac.accstatus = 1 and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + dt + "' ) bb, "
                            + " (select CONVERT('" + custid + "' USING utf8) as acno, cast(CONVERT(" + amt + " USING utf8) as decimal(25,2)) as amt) cc,   cbs_alert_indicater_info dd"
                            + " where aa.acno = bb.acno and aa.acno = cc.acno and aa.dorEffDt<=bb.operEffDt and aa.npaSno<bb.operSno "
                            + " and dd.alertType = 'TM5.1' and dd.enterdate <='" + dt + "'  and TIMESTAMPDIFF(DAY, bb.operEffDt, '" + dt + "')<=if(dd.dayMonth='[Y]',0,dd.dayMonth)"
                            + " and cc.amt > if(dd.fromAmt='[X]',0,dd.fromAmt)";
//                System.out.println("TM5.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM5.1! High value transactions in a dormant account";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TM5.2 Sudden activity in a dormant account*/
                if (ty.equalsIgnoreCase("0") || ty.equalsIgnoreCase("1")) {
                    String query = "select aa.acno, cc.amt, date_format(aa.dorEffDt,'%Y%m%d') as dorEffDt, aa.npaSpflag, aa.npaSno, date_format(bb.operEffDt,'%Y%m%d') as operEffDt, "
                            + " bb.operStatus, bb.operSno, TIMESTAMPDIFF(DAY, bb.operEffDt, '" + dt + "') as operDiff, if(dd.dayMonth='[Y]',0,dd.dayMonth) as masterDayDiff  from "
                            + " (select a.acno, npa.npaEffDt as dorEffDt, npa.npaSpflag, c.sno as npaSno     from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag, c.sno  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <= '" + dt + "' and acno = '" + custid + "' and SPFLAG IN (2)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' and acno = '" + custid + "' and SPFLAG IN (2) group by acno) c , "
                            + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + "  ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + dt + "' ) aa, "
                            + " (select a.acno, npa.npaEffDt as operEffDt , ac.accstatus as operStatus , npa.sno as operSno  from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag, c.sno  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <= '" + dt + "' and acno = '" + custid + "' and SPFLAG IN (1)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (1) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' and acno = '" + custid + "' group by acno) c , "
                            + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + "  ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (1) and ac.accstatus = 1 and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + dt + "' ) bb, "
                            + " (select acno, count(dt)+1 as amt from " + CbsUtil.getReconTableName(commonReport.getAcNatureByAcNo(custid)) + " where acno = '" + custid + "' and trandesc in (0,1,2,66,70) and "
                            + " dt between  "
                            + " (select date_format(npa.npaEffDt,'%Y%m%d')   from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag, c.sno  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <= '" + dt + "' and acno = '" + custid + "' and SPFLAG IN (1)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (1) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + dt + "' and acno = '" + custid + "' group by acno) c , "
                            + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + "  ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (1) and ac.accstatus = 1 and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + dt + "' ) "
                            + " and '" + dt + "' ) cc,   cbs_alert_indicater_info dd"
                            + " where aa.acno = bb.acno and aa.acno = cc.acno and aa.dorEffDt<=bb.operEffDt and aa.npaSno<bb.operSno "
                            + " and dd.alertType = 'TM5.1' and dd.enterdate <='" + dt + "'  and TIMESTAMPDIFF(DAY, bb.operEffDt, '" + dt + "')<=if(dd.dayMonth='[Y]',0,dd.dayMonth)"
                            + " and cc.amt > if(dd.noOfTxn='[N]',0,dd.fromAmt)";
//                System.out.println("TM5.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM5.1! High value transactions in a dormant account";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TM6.1 High value cash transactions inconsistent with profile*/
                if (tranType.equalsIgnoreCase("0")) {
                    String query = "select distinct a.customerid, if(b.fromAmt='[X]',0,b.fromAmt) as fromAmt  from ("
                            + " select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in  "
                            + " (select custId from customerid  where Acno = '" + custid + "') "
                            + " union all "
                            + " select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')) a, "
                            + " cbs_alert_indicater_info b where  b.alertType = 'TM6.1' and b.enterdate <='" + dt + "' "
                            + " and if(b.fromAmt='[X]',0,b.fromAmt)<" + amt;
//                System.out.println("TM6.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM6.1! High value cash transactions inconsistent with profile";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TM6.2 High cash activity inconsistent with profile*/
                if (tranType.equalsIgnoreCase("0")) {
                    String query = "select distinct a.customerid, if(b.fromAmt='[X]',0,b.fromAmt) as fromAmt  from ("
                            + " select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in  "
                            + " (select custId from customerid  where Acno = '" + custid + "') "
                            + " union all "
                            + " select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')) a, "
                            + " cbs_alert_indicater_info b where  b.alertType = 'TM6.2' and b.enterdate <='" + dt + "' "
                            + " and if(b.fromAmt='[X]',0,b.fromAmt)< "
                            + " (select sum(a.cnt)+1 from "
                            + " (select acno, count(dt) as cnt from recon where acno in  "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where a.CustId in  "
                            + " (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and   "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'   and TranType = 0 group by acno,dt  "
                            + " union all   "
                            + " select acno,  count(dt) as cnt from ca_recon where acno in  "
                            + " ( select distinct a.Acno from customerid a, accountmaster c where  a.CustId  in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and   "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'    and TranType = 0  group by acno, dt   "
                            + " union all   "
                            + " select acno,  count(dt) as cnt from loan_recon where acno in  "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and   "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'    and TranType = 0  group by acno, dt   "
                            + " union all   "
                            + " select acno,  count(dt) as cnt from td_recon where acno in  "
                            + " ( select distinct a.Acno from customerid a,  td_accountmaster c where  a.CustId  in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and   "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'    and TranType = 0  group by acno, dt   "
                            + " union all   "
                            + " select acno,  count(dt) as cnt from ddstransaction where acno in  "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and   "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'    and TranType = 0  group by acno, dt  "
                            + " union all   "
                            + " select acno,  count(dt) as cnt from rdrecon where acno in  "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and   "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'    and TranType = 0  group by acno, dt   "
                            + " union all   "
                            + " select acno,  count(dt) as cnt from of_recon where acno in  "
                            + " ( select distinct a.Acno from customerid a,  accountmaster c where  a.CustId  in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and   "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and  '" + dt + "'    and TranType = 0  group by acno, dt  )a) ";
//                System.out.println("TM6.2:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TM6.2! High cash activity inconsistent with profile";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TY1.1 Splitting of cash deposits just below INR 10,00,000 in multiple accounts in a month.*/
                if (ty.equalsIgnoreCase("0") && tranType.equalsIgnoreCase("0")) {
                    String query = "select mm.CustId, sum(mm.cnt), sum(mm.cramt) from "
                            + " (select b.CustId as CustId, a.acno, count(a.dt) as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from ca_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "' "
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',900000, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt,  cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from recon a, customerid b where a.ACNo = b.Acno and a.dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between  "
                            + " (select if(fromAmt='[X1]',900000, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,2  as tab from rdrecon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',900000, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,3 as tab from td_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0  group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',900000, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,4 as tab from loan_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',900000, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from ddstransaction a, customerid b where a.ACNo = b.Acno and a.dt between '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',900000, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') "
                            + " union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,6 as tab from of_recon a, customerid b where a.ACNo = b.Acno and a.dt between  '" + CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(dt)) + "' and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',900000, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') "
                            + "  union all "
                            + " select aa.CustId, aa.acno, aa.cnt , cast(sum(aa.cramt) as decimal(25,2)) as cramt,7 as tab from  "
                            + " (select (select CustId from customerid  where Acno = '" + custid + "') as custId,'" + custid + "' as acno,  1 as cnt, " + amt + " as cramt) aa group by aa.acno having cast(sum(aa.cramt) as decimal(25,2)) between "
                            + " (select if(fromAmt='[X1]',900000, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "')  "
                            + " )mm group by mm.CustId having sum(mm.cnt) >(select if(noOfTxn='[N]',0,noOfTxn) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.1' and enterDate<='" + dt + "')";

//                System.out.println("TY1.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TY1.1! Splitting of cash deposits just below INR 10,00,000 in multiple accounts in a month.";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TY1.2 Splitting of cash deposits just below INR 50,000.00*/
                if (ty.equalsIgnoreCase("0") && tranType.equalsIgnoreCase("0")) {
                    String query = " select  aa.acno, sum(aa.cnt), sum(aa.cramt) from "
                            + " (select  a.acno, count(a.dt)  as cnt,  cast(sum(cramt) as decimal(25,2)) as cramt  from " + CbsUtil.getReconTableName(commonReport.getAcNatureByAcNo(custid)) + " a where a.dt between (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' "
                            + " and a.acno = '" + custid + "' and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',40000,fromAmt) from cbs_alert_indicater_info  where levelid = 'B' and alerttype = 'TY1.2' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',49999,toAmt)   from cbs_alert_indicater_info  where levelid = 'B' and alerttype = 'TY1.2' and enterDate<='" + dt + "') "
                            + "  union all"
                            + " select a.acno, a.cnt, a.cramt from  "
                            + " (select CONVERT('" + custid + "' USING utf8) as acno, 1 as cnt, cast(CONVERT(" + amt + " USING utf8) as decimal(25,2)) as cramt ) a group by a.acno having a.cramt between "
                            + " (select if(fromAmt='[X1]',40000,fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.2' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',49999,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.2' and enterDate<='" + dt + "') "
                            + " ) aa "
                            + " group by aa.acno having  sum(aa.cnt)> "
                            + " (select if(noOfTxn='[N]',0,noOfTxn) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.2' and enterDate<='" + dt + "')";

//                System.out.println("TY1.2:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TY1.2! Splitting of cash deposits just below INR 50,000.00";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TY1.5 Frequent low cash deposits.*/
                if (ty.equalsIgnoreCase("0") && tranType.equalsIgnoreCase("0")) {
                    String query = "select mm.CustId, sum(mm.cnt), sum(mm.cramt) from "
                            + " (select b.CustId as CustId, a.acno, count(a.dt) as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from ca_recon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.5' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' "
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt,  cast(sum(cramt) as decimal(25,2)) as cramt,1 as tab from recon a, customerid b where a.ACNo = b.Acno and a.dt between (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.5' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between  "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,2  as tab from rdrecon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.5' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,3 as tab from td_recon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.5' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0  group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,4 as tab from loan_recon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.5' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,5 as tab from ddstransaction a, customerid b where a.ACNo = b.Acno and a.dt between (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.5' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') "
                            + " union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(cramt) as decimal(25,2)) as cramt,6 as tab from of_recon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.5' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=0 and a.trantype=0 group by  a.acno, a.dt having cramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') "
                            + "  union all "
                            + " select aa.CustId, aa.acno, aa.cnt , cast(sum(aa.cramt) as decimal(25,2)) as cramt,7 as tab from  "
                            + " (select (select CustId from customerid  where Acno = '" + custid + "') as custId,'" + custid + "' as acno,  1 as cnt, " + amt + " as cramt) aa group by aa.acno having cast(sum(aa.cramt) as decimal(25,2)) between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "')  "
                            + " )mm group by mm.CustId having sum(mm.cnt) >(select if(noOfTxn='[N]',0,noOfTxn) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.5' and enterDate<='" + dt + "')";

//                System.out.println("TY1.5:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TY1.5! Frequent low cash deposits";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TY1.6 Frequent low cash withdrawls*/
                if (ty.equalsIgnoreCase("1") && tranType.equalsIgnoreCase("0")) {
                    String query = "select mm.CustId, sum(mm.cnt), sum(mm.dramt) from "
                            + " (select b.CustId as CustId, a.acno, count(a.dt) as cnt, cast(sum(dramt) as decimal(25,2)) as dramt,1 as tab from ca_recon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.6' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' "
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=1 and a.trantype=0 group by a.acno, a.dt having dramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt,  cast(sum(dramt) as decimal(25,2)) as dramt,1 as tab from recon a, customerid b where a.ACNo = b.Acno and a.dt between (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.6' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=1 and a.trantype=0 group by  a.acno, a.dt having dramt between  "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(dramt) as decimal(25,2)) as dramt,2  as tab from rdrecon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.6' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=1 and a.trantype=0 group by  a.acno, a.dt having dramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(dramt) as decimal(25,2)) as dramt,3 as tab from td_recon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.6' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=1 and a.trantype=0  group by  a.acno, a.dt having dramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(dramt) as decimal(25,2)) as dramt,4 as tab from loan_recon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.6' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=1 and a.trantype=0 group by  a.acno, a.dt having dramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') "
                            + "  union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(dramt) as decimal(25,2)) as dramt,5 as tab from ddstransaction a, customerid b where a.ACNo = b.Acno and a.dt between (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.6' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=1 and a.trantype=0 group by  a.acno, a.dt having dramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') "
                            + " union all "
                            + "  select b.CustId as CustId, a.acno, count(a.dt)  as cnt, cast(sum(dramt) as decimal(25,2)) as dramt,6 as tab from of_recon a, customerid b where a.ACNo = b.Acno and a.dt between  (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY1.6' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "'"
                            + "  and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + "  and a.ty=1 and a.trantype=0 group by  a.acno, a.dt having dramt between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') "
                            + "  union all "
                            + " select aa.CustId, aa.acno, aa.cnt , cast(sum(aa.dramt) as decimal(25,2)) as dramt,7 as tab from  "
                            + " (select (select CustId from customerid  where Acno = '" + custid + "') as custId,'" + custid + "' as acno,  1 as cnt, " + amt + " as dramt) aa group by aa.acno having cast(sum(aa.dramt) as decimal(25,2)) between "
                            + " (select if(fromAmt='[X1]',0, fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "') and "
                            + " (select if(toAmt  ='[X2]',99999999999.99,toAmt)   from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "')  "
                            + " )mm group by mm.CustId having sum(mm.cnt) >(select if(noOfTxn='[N]',0,noOfTxn) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY1.6' and enterDate<='" + dt + "')";

//                System.out.println("TY1.6:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TY1.6! Frequent low cash withdrawls";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TY5.6 Large repetitive card usage at the same merchant
                 select  a.card, a.dt, a.merchant, a.acno, sum(a.amt), sum(a.noOfTran) from 
                 (select  card_number as card, TRAN_DATE as dt, trim(TERMINAL_LOCATION) as merchant, FROM_ACCOUNT_NUMBER as acno, sum(amount) as amt, count(TRAN_TIME) as noOfTran
                 from atm_normal_transaction_parameter where TRAN_DATE  between 
                 (SELECT date_format(ADDDATE('20170331', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from 
                 cbs_alert_indicater_info where alertType = 'TY5.6' and enterdate <='20170331') DAY) ,'%Y%m%d'))
                 and '20170331' group by card_number, TRAN_DATE, trim(TERMINAL_LOCATION)
                 ) a group by a.card, a.merchant
                 having  
                 sum(a.amt) >(select ifnull(if(fromAmt = '[X]',0,fromAmt),0) from cbs_alert_indicater_info where alertType = 'TY5.6' and enterdate <='20170331') and 
                 sum(a.noOfTran)>(select ifnull(if(noOfTxn = '[N]',0,noOfTxn),0) from cbs_alert_indicater_info where alertType = 'TY5.6' and enterdate <='20170331') 
                 */


                /*TY7.1! Repayment of loan in cash*/
                if (ty.equalsIgnoreCase("0") && tranType.equalsIgnoreCase("1")) {
//                String query = "select acno, cast(sum(cramt) as decimal(25,2))+"+amt+" as cramt from loan_recon where acno = '"+custid+"' "
//                        + " and dt between (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY7.1' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and '"+dt+"' " 
//                        + " and ty = 0 and trantype = 0 and trandesc = 1 group by acno having " 
//                        + " cast(sum(cramt) as decimal(25,2))> " 
//                        + " (select ifnull(if(fromAmt = '[X]',0,fromAmt),0) from cbs_alert_indicater_info where alertType = 'TY7.1' and enterdate <='" + dt + "')";

                    String query = "select mm.CustId,  sum(mm.cramt) from "
                            + " (select b.CustId as CustId, a.acno, cast(sum(cramt) as decimal(25,2)) as cramt from loan_recon a, customerid b where a.ACNo = b.Acno and a.dt "
                            + " between (SELECT date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY7.1' and enterdate <='" + dt + "') MONTH) ,'%Y%m%d')) and '" + dt + "'"
                            + " and b.CustId in (select custId from customerid  where Acno = '" + custid + "') "
                            + " and a.ty=0 and a.trantype=0 group by  a.acno having cramt > (select if(fromAmt='[X]',0,fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY7.1' and enterDate<='" + dt + "') "
                            + " union all  "
                            + " select aa.CustId, aa.acno, cast(sum(aa.cramt) as decimal(25,2)) as cramt  from  "
                            + " (select (select CustId from customerid  where Acno = '" + custid + "') as custId,'" + custid + "' as acno,  " + amt + " as cramt) aa group by aa.acno "
                            + " having cast(sum(aa.cramt) as decimal(25,2)) > (select if(fromAmt='[X]',0,fromAmt) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY7.1' and enterDate<='" + dt + "') "
                            + " )mm group by mm.CustId having sum(mm.cramt) >(select if(noOfTxn='[N]',0,noOfTxn) from cbs_alert_indicater_info where levelid = 'B' and alerttype = 'TY7.1' and enterDate<='" + dt + "')";

//                System.out.println("TY7.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TY7.1! Repayment of loan in cash";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*TY7.3 High number of cheque leaves*/
                if (commonReport.getAcNatureByAcNo(custid).equalsIgnoreCase("SB")) {
                    if (ty.equalsIgnoreCase("1") && tranType.equalsIgnoreCase("0")) {
                        String query = "select acno, count(acno)  from chbook_sb  where acno = '" + custid + "' and "
                                + " IssueDt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY7.3' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) "
                                + " and '" + dt + "' group by acno having count(acno)>(select ifnull(if(fromAmt = '[X1]',0,fromAmt),0) from cbs_alert_indicater_info where alertType = 'TY7.3' and enterdate <='" + dt + "')";

//                    System.out.println("TY7.3:query>>" + query);
                        List resultList = em.createNativeQuery(query).getResultList();
                        if (!resultList.isEmpty()) {
                            msg = "TY7.3! High number of cheque leaves";
//                        System.out.println("End Time:?????"+new Date());
                            return msg;
                        }
                    }
                } else if (commonReport.getAcNatureByAcNo(custid).equalsIgnoreCase("CA")) {
                    if (ty.equalsIgnoreCase("1") && tranType.equalsIgnoreCase("0")) {
                        String query = "select acno, count(acno)  from chbook_ca  where acno = '" + custid + "' and "
                                + " IssueDt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY7.3' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) "
                                + " and '" + dt + "' group by acno having count(acno)>(select ifnull(if(fromAmt = '[X1]',0,fromAmt),0) from cbs_alert_indicater_info where alertType = 'TY7.3' and enterdate <='" + dt + "')";

//                    System.out.println("TY7.3:query>>" + query);
                        List resultList = em.createNativeQuery(query).getResultList();
                        if (!resultList.isEmpty()) {
                            msg = "TY7.3! High number of cheque leaves";
//                        System.out.println("End Time:?????"+new Date());
                            return msg;
                        }
                    }
                }

                /*TY7.4 Frequent locker operations*/
                if (ty.equalsIgnoreCase("1") && tranType.equalsIgnoreCase("0")) {
                    String query = "select acno, count(acno) from locker_operation where acno = '" + custid + "' and "
                            + " opdate between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'TY7.4' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) "
                            + " and '" + dt + "' group by acno having count(acno)>(select ifnull(if(fromAmt = '[X]',0,fromAmt),0) from cbs_alert_indicater_info where alertType = 'TY7.4' and enterdate <='" + dt + "')";

//                System.out.println("TY7.4:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "TY7.4! Frequent locker operations";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }


                /*RM1.1 High value transactions by high risk customers*/
                if (ty.equalsIgnoreCase("0") || ty.equalsIgnoreCase("1")) {
                    String query = "select customerid, OperationalRiskRating, " + amt + " from cbs_customer_master_detail where "
                            + " CustomerId in (select custid from customerid where acno = '" + custid + "') "
                            + " and OperationalRiskRating = '01' and " + amt + " >(select ifnull(if(fromAmt = '[X]',0,fromAmt),0) from cbs_alert_indicater_info "
                            + " where alertType = 'RM1.1' and enterdate <='" + dt + "')";

//                System.out.println("RM1.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "RM1.1! High value transactions by high risk customers";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }

                /*RM1.2 high value cash transactions in NPO*/
                if (ty.equalsIgnoreCase("0") && tranType.equalsIgnoreCase("0")) {
                    String query = "select a.acno, cast(sum(b.cramt) as decimal(25,2))+" + amt + " as cramt from " + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + " a, " + CbsUtil.getReconTableName(commonReport.getAcNatureByAcNo(custid)) + " b where a.acno = b.acno and b.acno = '"+ custid +"' and b.dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM1.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d'))  and '" + dt + "' "
                            + " and b.ty = 0 and trantype = 0 and a.acctCategory in ('TG','NGO') "
                            + " group by acno having cast(sum(b.cramt) as decimal(25,2))> "
                            + " (select ifnull(if(fromAmt = '[X]',0,fromAmt),0) from cbs_alert_indicater_info where alertType = 'RM1.2' and enterdate <='" + dt + "')";

//                System.out.println("RM1.1:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "RM1.2! High value cash transactions in NPO";
//                    System.out.println("End Time:?????"+new Date());
                        return msg;
                    }
                }
                /*RM2.2 High value inward remittance*/
                if (ty.equalsIgnoreCase("0") && tranType.equalsIgnoreCase("1")) {
                    String query = "select distinct aa.CustId, sum(dd.cramt), bb.fromAmt, bb.toAmt,cc.acctCategory from customerid aa, cbs_alert_indicater_info bb, accountmaster cc, "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select custId from customerid  where Acno = '" + custid + "')  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select custId from customerid  where Acno = '" + custid + "') and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd "
                            + " where aa.CustId = bb.custId and aa.Acno = cc.ACNo and  aa.acno = dd.acno and bb.alertType = 'RM2.2' "
                            + " and bb.txnid = (select max(txnid) from cbs_alert_indicater_info where CustId  in (select custId from customerid  where Acno = '" + custid + "') and alerttype = 'RM2.2' and enterDate<='" + dt + "') "
                            + " group by aa.CustId having sum(dd.cramt)> if(cc.acctCategory = 'IND',bb.fromAmt,bb.toAmt) ";

//                    System.out.println("RM2.2:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "RM2.2! High value inward remittance";
//                        System.out.println("End Time:?????" + new Date());
                        return msg;
                    }
                }

                /*RM2.3 Inward remittance in a new accounts*/
                if (ty.equalsIgnoreCase("0") && tranType.equalsIgnoreCase("1")) {
                    String query = "select a.acno, c.OpeningDt,TIMESTAMPDIFF(MONTH, c.OpeningDt, '" + dt + "') as monthDiff, a.amt, "
                            + " if(b.fromAmt='[X]',0,b.fromAmt) as amtGreaterThan, if(b.dayMonth='[Y]',0,b.dayMonth) as noOfMonth from "
                            + " (select CONVERT('" + custid + "' USING utf8) as acno, cast(CONVERT(" + amt + " USING utf8) as decimal(25,2)) as amt) a,   "
                            + " cbs_alert_indicater_info b, " + CbsUtil.getAccMasterTableName(commonReport.getAcNatureByAcNo(custid)) + " c where a.acno = c.acno and "
                            + " b.alertType = 'RM2.3' and b.enterdate <='" + dt + "' "
                            + " and TIMESTAMPDIFF(MONTH, c.OpeningDt, '" + dt + "')<if(b.dayMonth='[Y]',0,b.dayMonth) "
                            + " and a.amt>if(b.fromAmt='[X]',0,b.fromAmt)";
//                    System.out.println("RM2.3:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "RM2.3! Inward remittance in a new accounts";
//                        System.out.println("End Time:?????" + new Date());
                        return msg;
                    }
                }

                /*RM2.4 Inward remittance inconsistent with client profile*/
                if (tranType.equalsIgnoreCase("0")) {
                    String query = "select distinct a.customerid, if(b.fromAmt='[X]',0,b.fromAmt) as fromAmt  from ("
                            + " select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in  "
                            + " (select custId from customerid  where Acno = '" + custid + "') "
                            + " union all "
                            + " select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in  "
                            + " (select custId from customerid  where Acno = '" + custid + "')) a, "
                            + " cbs_alert_indicater_info b where  b.alertType = 'RM2.4' and b.enterdate <='" + dt + "' "
                            + " and if(b.fromAmt='[X]',0,b.fromAmt)<"
                            + " (select sum(dd.cramt) from "
                            + " (select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "')) and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "')) and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "'))  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ca_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "')) and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "'))  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from loan_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "')) and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, td_accountmaster c where a.CustId = b.custId  and a.CustId  in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "'))  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from td_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "')) and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "'))  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from ddstransaction where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "')) and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "'))  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from rdrecon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "')) and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId  and a.CustId  in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "'))  and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 1 "
                            + " group by acno "
                            + " union all "
                            + " select acno, ifnull(sum(ifnull(cramt,0)),0) as cramt from of_recon where acno in ("
                            + " select distinct a.Acno from customerid a, cbs_alert_indicater_info b, accountmaster c where a.CustId = b.custId and a.CustId in (select customerid from cbs_customer_master_detail where minorflag = 'Y' and customerid in (select custId from customerid  where Acno = '" + custid + "') union all select customerid from cbs_cust_misinfo where  OccupationCode in (6,7,8,17) and customerid in (select custId from customerid  where Acno = '" + custid + "')) and a.Acno = c.ACNo and "
                            + " (c.ClosingDate is null or c.ClosingDate = '' or c.ClosingDate>'" + dt + "')) and dt between (select date_format(ADDDATE('" + dt + "', INTERVAL -(select ifnull(if(dayMonth='[Y]',0,dayMonth),0) from cbs_alert_indicater_info where alertType = 'RM2.2' and enterdate <='" + dt + "') DAY) ,'%Y%m%d')) and '" + dt + "' and ty = 0 and TranType = 2 and trandesc = 66 "
                            + " group by acno "
                            + " union all "
                            + " select '" + custid + "' as acno, " + amt + " as cramt) dd )";
//                    System.out.println("TM2.4:query>>" + query);
                    List resultList = em.createNativeQuery(query).getResultList();
                    if (!resultList.isEmpty()) {
                        msg = "RM2.4! Inward remittance inconsistent with client profile";
//                        System.out.println("End Time:?????" + new Date());
                        return msg;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    // Added by Manish Kumar
    public List<BankLevelSTRPojo> getBankLevelStr() throws ApplicationException {
        List<BankLevelSTRPojo> strPojoList = new ArrayList<BankLevelSTRPojo>();
        try {
//            List selectList = em.createNativeQuery("select distinct a.REF_CODE,a.REF_DESC,  COALESCE((select distinct REF_DESC from cbs_ref_rec_type where ref_rec_no = '365' and "
//                +"REF_CODE  = b.SSS_GNO),'') from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.ss_GNO and a.ref_rec_no = '352' "
//                +"and b.GNO in ( 'IAI','RFA') and ssss_gno = 'B' order by a.REF_CODE,a.REF_DESC, b.order_by").getResultList();
//            System.out.println("select t1.ref_code as alertCode ,t1.ref_desc as alertIndicator,t1.alertDesc, "
//                    + "COALESCE(c.fromAmt,'') as fromAmt, COALESCE(c.toAmt,'') as toAmt, COALESCE(c.noOfTxn,'') as noOfTxn, COALESCE(c.dayMonth,'')as dayMonth  from "
//                    + "(select distinct a.REF_CODE,a.REF_DESC,  COALESCE((select distinct REF_DESC from cbs_ref_rec_type where ref_rec_no = '365' and "
//                    + "REF_CODE  = b.SSS_GNO),'') as alertDesc from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.ss_GNO and a.ref_rec_no = '352' "
//                    + "and b.GNO in ( 'IAI','RFA') and ssss_gno = 'B' order by a.REF_CODE,a.REF_DESC, b.order_by ) t1 left join cbs_alert_indicater_info c "
//                    + "on c.alertType = t1.ref_code and c.levelId = 'B' and c.enterDate <= curdate()");

            List selectList = em.createNativeQuery("select t1.ref_code as alertCode ,t1.ref_desc as alertIndicator,t1.alertDesc, "
                    + "COALESCE(c.fromAmt,'') as fromAmt, COALESCE(c.toAmt,'') as toAmt, COALESCE(c.noOfTxn,'') as noOfTxn, COALESCE(c.dayMonth,'')as dayMonth  from "
                    + "(select distinct a.REF_CODE,a.REF_DESC,  COALESCE((select distinct REF_DESC from cbs_ref_rec_type where ref_rec_no = '365' and "
                    + "REF_CODE  = b.SSS_GNO),'') as alertDesc from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.ss_GNO and a.ref_rec_no = '352' "
                    + "and b.GNO in ( 'IAI','RFA') and ssss_gno = 'B' order by a.REF_CODE,a.REF_DESC, b.order_by ) t1 left join cbs_alert_indicater_info c "
                    + "on c.alertType = t1.ref_code and c.levelId = 'B' and c.enterDate <= curdate()").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("STR not found !");
            } else {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    BankLevelSTRPojo pojo = new BankLevelSTRPojo();
                    pojo.setSno(i + 1);
                    pojo.setAlertType(vec.get(0).toString().toUpperCase());
                    String desc = "";
                    if (vec.get(2).toString().equals("")) {
                        desc = vec.get(1).toString();
                    } else {
                        desc = vec.get(1).toString() + " {" + vec.get(2).toString() + "}";
                    }
                    pojo.setDescription(desc);
                    pojo.setFromAmt(vec.get(3).toString());
                    pojo.setToAmt(vec.get(4).toString());
                    pojo.setNoOfTxn(vec.get(5).toString());
                    pojo.setDayMonth(vec.get(6).toString());
                    pojo.setCheckBox(false);
                    strPojoList.add(pojo);
                }

            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return strPojoList;
    }

    public int insertCBSAlertIndicator(List<BankLevelSTRPojo> strList, String enterBy, String branchCode, boolean checkBox) throws Exception {
        int count = 0, i = 0, unCheckedCount = 0;
        Pattern pattern = Pattern.compile("\\d+");
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            for (BankLevelSTRPojo obj : strList) {
                if (obj.isCheckBox() == true) {

                    if (!obj.getFromAmt().trim().isEmpty()) {

                        if (!pattern.matcher(obj.getFromAmt().trim()).matches()) {
                            throw new ApplicationException("Only Numeric Value is allowed ! Error in SNo. " + (i + 1) + " in 'Form' column.");
                        }
                    }
                    if (!obj.getToAmt().trim().isEmpty()) {
                        if (!pattern.matcher(obj.getToAmt().trim()).matches()) {
                            throw new ApplicationException("Only Numeric Value is allowed ! Error in SNo. " + (i + 1) + ", in 'To' column.");
                        }
                    }
                    if (!obj.getNoOfTxn().trim().isEmpty()) {
                        if (!pattern.matcher(obj.getNoOfTxn().trim()).matches()) {
                            throw new ApplicationException("Only Numeric Value is allowed ! Error in SNo. " + (i + 1) + ", in 'No. Of Txn' column.");
                        }
                    }
                    if (!obj.getDayMonth().trim().isEmpty()) {
                        if (!pattern.matcher(obj.getDayMonth().trim()).matches()) {
                            throw new ApplicationException("Only Numeric Value is allowed ! Error in SNo. " + (i + 1) + ", in 'Day or Month' Form column.");
                        }
                    }

                    Query insertCKYCRRequest = em.createNativeQuery("update cbs_alert_indicater_info set fromAmt ='" + obj.getFromAmt() + "', toAmt ='" + obj.getToAmt() + "', noOfTxn ='" + obj.getNoOfTxn() + "', dayMonth = '" + obj.getDayMonth() + "', enterBy ='" + enterBy + "', enterDate = curdate(), orgBrnid = '" + branchCode + "' "
                            + "where levelId = 'B' and alertType = '" + obj.getAlertType() + "'");
                    int result = insertCKYCRRequest.executeUpdate();
                    if (result <= 0) {
                        throw new ApplicationException("Problem Occured In STR CBS Alert Indicator updation !");
                    } else {
                        count++;
                    }
                } else {
                    unCheckedCount++;
                }
                i++;
            }
            if (unCheckedCount == strList.size()) {
                throw new ApplicationException("You have not selected any row !");
            }
            ut.commit();

        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return count;

    }

    //-------
    public List getCustDataById(String custid) throws ApplicationException {
        try {
            return em.createNativeQuery("select custname,date_format(DateOfBirth,'%d/%m/%Y'),mothername from cbs_customer_master_detail where customerid = '" + custid + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String getSaveAlertIndicator(String custid, String dt, String alertCide, String branch, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int insertQuery = em.createNativeQuery("INSERT INTO cbs_str_detail (acno, batch_no, recno, dt, orgnbrncode, destbrncode, alert_code, enter_by, trantime, auth_by, auth_status) "
                    + "VALUES ('" + custid + "', 0, 0, curdate(), '" + branch + "', '" + branch + "', '" + alertCide + "', '" + userName + "', now(), 'System', 'N')").executeUpdate();
            if (insertQuery <= 0) {
                throw new ApplicationException("Inserttion Problem");
            }
            ut.commit();
            return "true";

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getRfaAlertList(String custId) throws ApplicationException {
        try {

            List list = em.createNativeQuery("select * from cbs_alert_indicater_info where levelId = 'C' and custid = '" + custId + "'").getResultList();
            if (list.isEmpty()) {

                return em.createNativeQuery("select distinct t1.ref_code as alertCode ,concat(t1.ref_code,'  ',t1.ref_desc,' ',t1.alertDesc), "
                        + "COALESCE(c.fromAmt,'') as fromAmt, COALESCE(c.toAmt,'') as toAmt, COALESCE(c.noOfTxn,'') as noOfTxn, COALESCE(c.dayMonth,'')as dayMonth  from "
                        + "(select distinct a.REF_CODE,a.REF_DESC,  COALESCE((select distinct REF_DESC from cbs_ref_rec_type where ref_rec_no = '365' and "
                        + "REF_CODE  = b.SSS_GNO),'') as alertDesc from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.ss_GNO and a.ref_rec_no = '352' "
                        + "and b.GNO in ( 'RFA') and ssss_gno = 'C' order by a.REF_CODE,a.REF_DESC, b.order_by ) t1 left join cbs_alert_indicater_info c "
                        + "on c.alertType = t1.ref_code and c.levelId = 'CM' and c.enterDate <= curdate() group by t1.ref_code").getResultList();

            } else {
                return em.createNativeQuery("select distinct t1.ref_code as alertCode ,concat(t1.ref_code,'  ',t1.ref_desc,' ',t1.alertDesc), "
                        + "COALESCE(c.fromAmt,'') as fromAmt, COALESCE(c.toAmt,'') as toAmt, COALESCE(c.noOfTxn,'') as noOfTxn, COALESCE(c.dayMonth,'')as dayMonth  from "
                        + "(select distinct a.REF_CODE,a.REF_DESC,  COALESCE((select distinct REF_DESC from cbs_ref_rec_type where ref_rec_no = '365' and "
                        + "REF_CODE  = b.SSS_GNO),'') as alertDesc from cbs_ref_rec_type a, cbs_ref_rec_mapping b where a.REF_CODE = b.ss_GNO and a.ref_rec_no = '352' "
                        + "and b.GNO in ( 'RFA') and ssss_gno = 'C' order by a.REF_CODE,a.REF_DESC, b.order_by ) t1 left join cbs_alert_indicater_info c "
                        + "on c.alertType = t1.ref_code and c.levelId = 'C' and c.custid ='" + custId + "' and c.enterDate <= curdate() group by t1.ref_code").getResultList();
            }

//            return em.createNativeQuery("select a.ref_code,concat(a.REF_DESC,' ( ',b.REF_DESC,' ) ') from cbs_ref_rec_type a,cbs_ref_rec_type b,cbs_ref_rec_mapping c "
//                    + "where a.ref_rec_no='352' and b.ref_rec_no='365'and c.gno = 'rfa' and a.REF_CODE = c.SS_GNO and b.REF_CODE = c.SSS_GNO "
//                    + "and c.SSSS_GNO = 'C' order by a.ref_code").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getCustIdLevelStr(String custid, List<BankLevelSTRPojo> strList, String branch, String userName, boolean checkBox) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select * from cbs_alert_indicater_info where levelId = 'C' and custid = '" + custid + "'").getResultList();
            for (BankLevelSTRPojo pojo : strList) {
                Pattern pattern = Pattern.compile("\\d+");
                int insertQuery = 0;

                if (!(pojo.getFromAmt().trim().isEmpty() || pojo.getFromAmt().trim().contains("X") || pojo.getFromAmt().trim().contains("Y"))) {
                    if (!pattern.matcher(pojo.getFromAmt().trim()).matches()) {
                        throw new ApplicationException("Only Numeric Value is allowed !");
                    }
                }
                if (!(pojo.getToAmt().trim().isEmpty() || pojo.getToAmt().trim().contains("X") || pojo.getToAmt().trim().contains("Y"))) {
                    if (!pattern.matcher(pojo.getToAmt().trim()).matches()) {
                        throw new ApplicationException("Only Numeric Value is allowed !");
                    }
                }

                if (!(pojo.getNoOfTxn().trim().isEmpty() || pojo.getNoOfTxn().trim().contains("X") || pojo.getNoOfTxn().trim().contains("Y"))) {
                    if (!pattern.matcher(pojo.getNoOfTxn().trim()).matches()) {
                        throw new ApplicationException("Only Numeric Value is allowed !");
                    }
                }
                if (!(pojo.getDayMonth().trim().isEmpty() || pojo.getDayMonth().trim().contains("X") || pojo.getDayMonth().trim().contains("Y"))) {
                    if (!pattern.matcher(pojo.getDayMonth().trim()).matches()) {
                        throw new ApplicationException("Only Numeric Value is allowed !");
                    }
                }
                if (list.isEmpty()) {
                    insertQuery = em.createNativeQuery("INSERT INTO cbs_alert_indicater_info (levelId, alertType, custId, fromAmt, toAmt, noOfTxn, dayMonth, enterBy, enterDate, orgBrnid) "
                            + "VALUES ('C', '" + pojo.getAlertType() + "', '" + custid + "', '" + pojo.getFromAmt() + "', '" + pojo.getToAmt() + "', '" + pojo.getNoOfTxn() + "', '" + pojo.getDayMonth() + "', '" + userName + "', curdate(), '" + branch + "')").executeUpdate();
                } else {
                    insertQuery = em.createNativeQuery("update cbs_alert_indicater_info set fromAmt ='" + pojo.getFromAmt() + "', toAmt ='" + pojo.getToAmt() + "', noOfTxn ='" + pojo.getNoOfTxn() + "', dayMonth = '" + pojo.getDayMonth() + "', enterBy ='" + userName + "', enterDate = curdate(), orgBrnid = '" + branch + "' "
                            + "where custid = '" + custid + "' and levelId = 'C' and alertType = '" + pojo.getAlertType() + "'").executeUpdate();
                }
                if (insertQuery <= 0) {
                    throw new ApplicationException("Inserttion Problem");
                }
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }

        }

    }

    public String getBranchByCustId(String custid) throws ApplicationException {
        String brCode = "";
        try {
            List result = em.createNativeQuery("select primarybrcode from cbs_customer_master_detail where customerid='" + custid + "'").getResultList();
            if (!result.isEmpty()) {
                Vector vtr = (Vector) result.get(0);
                brCode = vtr.get(0).toString();
            }
            return brCode;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
