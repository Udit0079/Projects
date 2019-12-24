package com.cbs.facade.report;

import com.cbs.dto.report.ClearingOperationReportPojo;
import com.cbs.dto.report.DemandDraftPayOrderPojo;
import com.cbs.dto.report.InwardChequeReturnPojo;
import com.cbs.dto.report.InwardClearingChequeReturnPojo;
import com.cbs.dto.report.InwardClearingPojo;
import com.cbs.dto.report.InwardClearingTodayDatePojo;
import com.cbs.dto.report.OutwardClearingBankwiseReportPojo;
import com.cbs.dto.report.OutwardClearingBatchReportPojo;
import com.cbs.dto.report.OutwardClearingEnteredReportPojo;
import com.cbs.dto.report.OutwardClearingSummaryReportPojo;
import com.cbs.dto.report.ho.InoutWardClearingPassReturnPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.pojo.CtsinwardinfoPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zeeshan Waris
 */
@Stateless(mappedName = "ClgReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ClgReportFacade implements ClgReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat y_m_dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;

    /**
     * ************************** INWARD CLEARING REPORT
     * ***************************
     */
    /**
     *
     * @param emFlag
     * @param date
     * @param brnCode
     * @param repName
     * @param repType
     * @return
     */
    @Override
    public List<InwardClearingTodayDatePojo> inwardClearingTodayDate(String emFlag, String date, String brnCode, String repName, String repType) {
        List<InwardClearingTodayDatePojo> inwardClearingTodayDatePojos = new ArrayList<InwardClearingTodayDatePojo>();
        try {
            repType = repType == null ? "" : repType;
            String bnkName = null, bnkAddress = null, alpha = "";
            List objBan = common.getBranchNameandAddress(brnCode);
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            List toDtList = em.createNativeQuery("SELECT brncode FROM branchmaster WHERE BRNCODE = " + Integer.parseInt(brnCode)).getResultList();
            if (!toDtList.isEmpty()) {
                Vector balanceVect = (Vector) toDtList.get(0);
                alpha = balanceVect.get(0).toString();
            }
            List result = new ArrayList();
            if (repName.equalsIgnoreCase("I/W CLG PASSED")) {
                if (repType.equalsIgnoreCase("CTS")) {
                    result = em.createNativeQuery("SELECT ifnull(r.acno,'') as acno,ifnull(r.custname,'') as custname,CAST(r.dramt AS decimal(25,2)),"
                            + "ifnull(r.instno,'') as instno,ifnull(a.acctnature,'')  as acctnature from recon_clg_d r, accounttypemaster a  WHERE "
                            + "a.acctcode=substring(r.acno,3,2) AND r.TRANTYPE=1 AND r.TY=1 AND  r.screenflag='T' AND r.org_brnid='" + brnCode
                            + "' AND TRANDESC=64 and upper(Term_id)='CTS' AND r.DT='" + date + "' AND (rtrim(ltrim(r.details)) <>'Cheque Return.' "
                            + "AND r.ACNO <> (SELECT glclgret FROM parameterinfo_clg where circletype= 'A')) order by acno").getResultList();
                } else {
                    result = em.createNativeQuery("SELECT ifnull(r.acno,'') as acno,ifnull(r.custname,'') as custname,CAST(r.dramt AS decimal(25,2)),"
                            + "ifnull(r.instno,'') as instno,ifnull(a.acctnature,'')  as acctnature  from recon_clg_d r, accounttypemaster a  WHERE "
                            + "a.acctcode=substring(r.acno,3,2) AND r.TRANTYPE=1 AND r.TY=1 AND  r.screenflag='T'   AND r.org_brnid='" + brnCode
                            + "'  AND TRANDESC<>64 and upper(Term_id)<>'CTS' AND r.DT='" + date + "' AND (rtrim(ltrim(r.details)) <>'Cheque Return.' "
                            + "AND r.ACNO <> (SELECT glclgret FROM parameterinfo_clg where circletype= 'A')) order by acno").getResultList();
                }
                if (result.size() > 0) {
                    for (int j = 0; j < result.size(); j++) {
                        Vector record = (Vector) result.get(j);
                        InwardClearingTodayDatePojo balCert = new InwardClearingTodayDatePojo();
                        balCert.setAcno(record.get(0).toString());
                        balCert.setAcName(record.get(1).toString());
                        balCert.setDrbal(new BigDecimal(record.get(2).toString()));
                        balCert.setChqno(record.get(3).toString());
                        balCert.setAcType(record.get(4).toString());
                        balCert.setBnkAdd(bnkAddress);
                        balCert.setBnkName(bnkName);
                        balCert.setBrncode(Integer.parseInt(alpha));
                        inwardClearingTodayDatePojos.add(balCert);
                    }
                }
            }
            return inwardClearingTodayDatePojos;
        } catch (Exception e) {
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    /**
     *
     * @param emFlag
     * @param date
     * @param brnCode
     * @param repName
     * @param repType
     * @return
     */
    @Override
    public List<InwardClearingPojo> inwardClearing(String emFlag, String date, String brnCode, String repName, String repType) {
        List<InwardClearingPojo> inwardClearingPojos = new ArrayList<InwardClearingPojo>();
        try {
            repType = repType == null ? "" : repType;
            String bnkName = null, bnkAddress = null, alpha = "";
            List objBan = common.getBranchNameandAddress(brnCode);
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            List toDtList = em.createNativeQuery("SELECT brncode FROM branchmaster WHERE BRNCODE = " + Integer.parseInt(brnCode)).getResultList();
            if (!toDtList.isEmpty()) {
                Vector balanceVect = (Vector) toDtList.get(0);
                alpha = balanceVect.get(0).toString();
            }
            List result = new ArrayList();
            if (repName.equalsIgnoreCase("I/W CLG PASSED")) {
                if (repType.equalsIgnoreCase("CTS")) {
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from td_recon d,td_accountmaster a where "
                            + "substring(d.Acno,3,2)=a.Accttype and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') "
                            + "AND trantype=1 AND TY=1 AND TRANDESC=64 AND upper(Term_id)='CTS' AND details<>'Cheque Return.' AND d.acno = a.acno "
                            + "and a.CurBrCode =  '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from of_recon d,td_accountmaster a where "
                            + "substring(d.tdAcno,3,2)=a.Accttype and d.tdAcno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') "
                            + "AND trantype=1 AND TY=1 AND TRANDESC=64 AND UPPER(Term_id)='CTS' AND details<>'Cheque Return.' AND d.acno = a.acno and "
                            + "a.CurBrCode  = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.acname,d.DRAMT,d.instno from gl_recon d,gltable a where d.Acno=a.acno and dt='"
                            + date + "' and trantype=1 AND TY=1 AND TRANDESC=64 AND UPPER(Term_id)='CTS' AND d.ACNO<> (select glclgret from "
                            + "parameterinfo_clg where circletype= '" + emFlag + "' AND SUBSTRING(GlClgRet,1,2)='" + brnCode + "') and auth='Y' AND "
                            + "(d.instno IS NOT NULL OR d.instno<>'') AND SUBSTRING(d.acno,1,2) = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from rdrecon d,accountmaster a where substring(d.Acno,3,2)="
                            + "a.Accttype and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 "
                            + "AND TRANDESC=64 AND UPPER(Term_id)='CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from recon d,accountmaster a where substring(d.Acno,3,2)="
                            + "a.Accttype and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 "
                            + "AND TRANDESC=64 AND UPPER(Term_id)='CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from loan_recon d,accountmaster a where substring(d.Acno,3,2)="
                            + "a.Accttype and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 AND TRANDESC=64 "
                            + "AND UPPER(Term_id)='CTS' AND details<>'Cheque Return.' and auth='Y' AND IY NOT IN (9999) AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from ca_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 AND TRANDESC=64 AND "
                            + "UPPER(Term_id)='CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from ddstransaction d,accountmaster a where substring(d.Acno,3,2)="
                            + "a.Accttype and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 AND TRANDESC=64 "
                            + "AND UPPER(Term_id)='CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                } else {
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from td_recon d,td_accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 AND TRANDESC<>64 AND "
                            + "UPPER(Term_id)<>'CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from of_recon d,td_accountmaster a where substring(d.tdAcno,3,2)="
                            + "a.Accttype and d.tdAcno=a.acno and dt='" + date + "' and d.instno IS NOT NULL OR d.instno<>'' AND trantype=1 AND TY=1 AND TRANDESC<>64 "
                            + "AND UPPER(Term_id)<>'CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.acname,d.DRAMT,d.instno from gl_recon d,gltable a where "
                            + "d.Acno=a.acno and dt='" + date + "' and trantype=1 AND TY=1 AND TRANDESC<>64 AND UPPER(Term_id)<>'CTS' "
                            + "AND d.ACNO<> (select glclgret from parameterinfo_clg where circletype= '" + emFlag + "')"
                            + "and auth='Y' AND (d.instno IS NOT NULL OR d.instno<>'')"
                            + "AND SUBSTRING(d.acno,1,2) = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from rdrecon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 "
                            + "AND TRANDESC<>64 AND UPPER(Term_id)<>'CTS' AND details<>'Cheque Return.' AND  a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from recon d,accountmaster a where substring(d.Acno,3,2)="
                            + "a.Accttype and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 "
                            + "AND TRANDESC<>64 AND UPPER(Term_id)<>'CTS' AND details<>'Cheque Return.' AND  a.CurBrCode = '" + brnCode + "' order by "
                            + "d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from loan_recon d,accountmaster a where substring(d.Acno,3,2)="
                            + "a.Accttype and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 AND "
                            + "TRANDESC<>64 AND UPPER(Term_id)<>'CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from ca_recon d,accountmaster a where substring(d.Acno,3,2)=a.Accttype and "
                            + "d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 AND TRANDESC<>64 AND "
                            + "UPPER(Term_id)<>'CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                    result = em.createNativeQuery("select d.acno,a.custname,d.DRAMT,d.instno from ddstransaction d,accountmaster a where substring(d.Acno,3,2)=a.Accttype "
                            + "and d.Acno=a.acno and dt='" + date + "' and (d.instno IS NOT NULL OR d.instno<>'') AND trantype=1 AND TY=1 AND TRANDESC<>64 "
                            + "AND UPPER(Term_id)<>'CTS' AND details<>'Cheque Return.' AND a.CurBrCode = '" + brnCode + "' order by d.acno").getResultList();
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            InwardClearingPojo balCert = new InwardClearingPojo();
                            balCert.setAcno(record.get(0).toString());
                            balCert.setAcType(record.get(0).toString().substring(2, 4));
                            balCert.setBnkAdd(bnkAddress);
                            balCert.setBnkName(bnkName);
                            balCert.setBrncode(Integer.parseInt(alpha));
                            balCert.setAcName(record.get(1).toString());
                            balCert.setDrbal(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                            balCert.setChqno(record.get(3).toString());
                            inwardClearingPojos.add(balCert);
                        }
                    }
                }
            }
            return inwardClearingPojos;
        } catch (Exception e) {
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    @Override
    public List<OutwardClearingBankwiseReportPojo> getOutwardClearingBankwiseReport(String date, String reportName, String emFlag, String brCode) throws ApplicationException {
        List<OutwardClearingBankwiseReportPojo> returnList = new ArrayList<OutwardClearingBankwiseReportPojo>();
        String bankName = "";
        String bankAddress = "";
        String instNo = "";
        double instAmount = 0;
        String status = "";
        try {
            List tempList = null;
            Vector ele;
            String maxDate = null;
            if (reportName.equalsIgnoreCase("O/W Clg Entered") || reportName.equalsIgnoreCase("O/W CLG BANKWISE") || reportName.equalsIgnoreCase("O/W CLG SUMMARY")) {
                if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                    tempList = em.createNativeQuery("SELECT MAX(DATE) FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE='" + brCode + "'").getResultList();
                    if (!tempList.isEmpty()) {
                        ele = (Vector) tempList.get(0);
                        if (ele != null || ele.get(0) != null || ele.get(0).toString().length() == 8) {
                            maxDate = ele.get(0).toString();
                        }
                    }
                    int dtdiff = (int) CbsUtil.dayDiff(ymdFormat.parse(date), ymdFormat.parse(maxDate));
                    if (dtdiff <= 0) {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,SUBSTRING(ifnull(txnbankaddress,''),1,25) as txnbankaddress,ifnull(txninstno,'') as txninstno,ifnull(txninstamt,0) as txninstamt,txnstatus=case ifnull(txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'E' then 'Entered' Else 'Others' end from  outward_clearing_after1day where emflag =  '" + emFlag + "'  and txndate= '" + date + "' and orgnbrcode='" + brCode + "' order by txnbankname,txninstamt,txninstno").getResultList();
                        } else {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,SUBSTRING(ifnull(txnbankaddress,''),1,25) as txnbankaddress,ifnull(txninstno,'') as txninstno,ifnull(txninstamt,0) as txninstamt,txnstatus=case ifnull(txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'E' then 'Entered' Else 'Others' end from  clg_localchq where emflag =  '" + emFlag + "'  and txndate= '" + date + "' and orgnbrcode='" + brCode + "' order by txnbankname,txninstamt,txninstno").getResultList();
                        }
                    } else if (dtdiff == 1) {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,SUBSTRING(ifnull(txnbankaddress,''),1,25) as txnbankaddress,ifnull(txninstno,'') as txninstno,ifnull(txninstamt,0) as txninstamt,txnstatus=case ifnull(txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'E' then 'Entered' Else 'Others' end from  outward_clearing_after1days where emflag =  '" + emFlag + "'  and txndate= '" + date + "' and orgnbrcode='" + brCode + "' order by txnbankname,txninstamt,txninstno").getResultList();
                        } else {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,SUBSTRING(ifnull(txnbankaddress,''),1,25) as txnbankaddress,ifnull(txninstno,'') as txninstno,ifnull(txninstamt,0) as txninstamt,txnstatus=case ifnull(txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'E' then 'Entered' Else 'Others' end from  clg_localchq where emflag =  '" + emFlag + "'  and txndate= '" + date + "' order by txnbankname,txninstamt,txninstno").getResultList();
                        }
                    } else {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,SUBSTRING(ifnull(txnbankaddress,''),1,25) as txnbankaddress,ifnull(txninstno,'') as txninstno,ifnull(txninstamt,0) as txninstamt,txnstatus=case ifnull(txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'E' then 'Entered' Else 'Others' end from  outward_clearing_after2days where emflag =  '" + emFlag + "'  and txndate= '" + date + "' and orgnbrcode='" + brCode + "' order by txnbankname,txninstamt,txninstno").getResultList();
                        } else {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,SUBSTRING(ifnull(txnbankaddress,''),1,25) as txnbankaddress,ifnull(txninstno,'') as txninstno,ifnull(txninstamt,0) as txninstamt,txnstatus=case ifnull(txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'E' then 'Entered' Else 'Others' end from  clg_localchq where emflag =  '" + emFlag + "'  and txndate= '" + date + "' and orgnbrcode='" + brCode + "' order by txnbankname,txninstamt,txninstno").getResultList();
                        }
                    }
                }
                for (int i = 0; i < tempList.size(); i++) {
                    ele = (Vector) tempList.get(i);
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        bankAddress = ele.get(1).toString();
                    }
                    if (ele.get(2) != null) {
                        instNo = ele.get(2).toString();
                    }
                    if (ele.get(3) != null || !ele.get(3).toString().equalsIgnoreCase("")) {
                        instAmount = Double.parseDouble(ele.get(3).toString());
                    }
                    if (ele.get(4) != null) {
                        status = ele.get(4).toString();
                    }
                    OutwardClearingBankwiseReportPojo pojo = new OutwardClearingBankwiseReportPojo();
                    pojo.setBankAddress(bankAddress);
                    pojo.setBankName(bankName);
                    pojo.setInstAmount(new BigDecimal(instAmount));
                    pojo.setInstNo(Integer.parseInt(instNo));
                    pojo.setStatus(status);
                    returnList.add(pojo);
                }
            }
            /*              Code Added By Nishant Kansal               */
            String bnkName = "", bnkAdd = "";
            List dataList1 = common.getBranchNameandAddress(brCode);
            if (dataList1.size() > 0) {
                bnkName = (String) dataList1.get(0);
                bnkAdd = (String) dataList1.get(1);
            }
            if (returnList.size() > 0) {
                for (OutwardClearingBankwiseReportPojo object : returnList) {
                    object.setBnkName(bnkName);
                    object.setBnkAdd(bnkAdd);
                    object.setBrncode(Integer.parseInt(brCode));
                }
            } else {
                throw new ApplicationException("No detail exists !");
            }
            /*              End Of Code Added By Nishant Kansal               */
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    @Override
    public List<OutwardClearingSummaryReportPojo> getOutwardClearingSummaryReport(String date, String reportName, String emFlag, String brCode) throws ApplicationException {
        List<OutwardClearingSummaryReportPojo> returnList = new ArrayList<OutwardClearingSummaryReportPojo>();
        String bankName = null;
        int chequeNo = 0;
        double totalAmount = 0;
        try {
            List tempList = null;
            Vector ele;
            String maxDate = null;
            if (reportName.equalsIgnoreCase("O/W Clg Entered") || reportName.equalsIgnoreCase("O/W CLG BANKWISE") || reportName.equalsIgnoreCase("O/W CLG SUMMARY")) {
                if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                    tempList = em.createNativeQuery("SELECT MAX(DATE) FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE='" + brCode + "'").getResultList();
                    if (!tempList.isEmpty()) {
                        ele = (Vector) tempList.get(0);
                        if (ele != null || ele.get(0) != null || ele.get(0).toString().length() == 8) {
                            maxDate = ele.get(0).toString();
                        }
                    }
                    int dtdiff = (int) CbsUtil.dayDiff(ymdFormat.parse(date), ymdFormat.parse(maxDate));
                    if (dtdiff <= 0) {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,ifnull(count(txninstno),0) as chqno,ifnull(sum(txninstamt),0) as totalamt from outward_clearing_after1day where emflag= '" + emFlag + "' and txndate= '" + date + "' and orgnbrcode='" + brCode + "' group by txnbankname").getResultList();
                        } else {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,ifnull(count(txninstno),0) as chqno,ifnull(sum(txninstamt),0) as totalamt from clg_Localchq where emflag= '" + emFlag + "' and txndate= '" + date + "' and orgnbrcode='" + brCode + "' group by txnbankname").getResultList();
                        }
                    } else if (dtdiff == 1) {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,ifnull(count(txninstno),0) as chqno,ifnull(sum(txninstamt),0) as totalamt from outward_clearing_after1days where emflag= '" + emFlag + "' and txndate= '" + date + "' and orgnbrcode='" + brCode + "' group by txnbankname").getResultList();
                        } else {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,ifnull(count(txninstno),0) as chqno,ifnull(sum(txninstamt),0) as totalamt from clg_Localchq where emflag= '" + emFlag + "' and txndate= '" + date + "' and orgnbrcode='" + brCode + "' group by txnbankname").getResultList();
                        }
                    } else {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,ifnull(count(txninstno),0) as chqno,ifnull(sum(txninstamt),0) as totalamt from outward_clearing_after2days where emflag= '" + emFlag + "' and txndate= '" + date + "' and orgnbrcode='" + brCode + "' group by txnbankname").getResultList();
                        } else {
                            tempList = em.createNativeQuery("SELECT SUBSTRING(ifnull(txnbankname,''),1,25) as txnbankname,ifnull(count(txninstno),0) as chqno,ifnull(sum(txninstamt),0) as totalamt from clg_Localchq where emflag= '" + emFlag + "' and txndate= '" + date + "' and orgnbrcode='" + brCode + "' group by txnbankname").getResultList();
                        }
                    }
                }
                for (int i = 0; i < tempList.size(); i++) {
                    ele = (Vector) tempList.get(i);
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        chequeNo = Integer.parseInt(ele.get(1).toString());
                    }
                    if (ele.get(2) != null || !ele.get(2).toString().equalsIgnoreCase("")) {
                        totalAmount = Double.parseDouble(ele.get(2).toString());
                    }
                    OutwardClearingSummaryReportPojo pojo = new OutwardClearingSummaryReportPojo();
                    pojo.setBankName(bankName);
                    pojo.setChequeNo(chequeNo);
                    pojo.setTotalAmount(new BigDecimal(totalAmount));
                    returnList.add(pojo);
                }

                /*              Code Added By Nishant Kansal               */
                String bnkName = "", bnkAdd = "";
                List dataList1 = common.getBranchNameandAddress(brCode);
                if (dataList1.size() > 0) {
                    bnkName = (String) dataList1.get(0);
                    bnkAdd = (String) dataList1.get(1);
                }
                if (returnList.size() > 0) {
                    for (OutwardClearingSummaryReportPojo object : returnList) {
                        object.setBnkName(bnkName);
                        object.setBnkAdd(bnkAdd);
                        object.setBrncode(Integer.parseInt(brCode));
                    }
                } else {
                    throw new ApplicationException("No detail exists !");
                }
                /*              End Of Code Added By Nishant Kansal               */
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List<OutwardClearingEnteredReportPojo> getOutwardClearingEnteredReport(String date, String reportName, String emFlag, String brCode, boolean hdfcFlag, String orderBy) throws ApplicationException {
        List<OutwardClearingEnteredReportPojo> returnList = new ArrayList<OutwardClearingEnteredReportPojo>();
        Vector ele;
        String acNo = "";
        String custName = "";
        int voucherNo = 0;
        String bankName = "";
        String bankAddress = "";
        String instNo = "";
        String instDate = "";
        double instAmount = 0;
        String status = "";
        String micrCode = "";
        String maxDate = null;
        List tempList = null;
        String acType = "";
        try {
            int iBrCode = Integer.parseInt(brCode);
            if (orderBy.equalsIgnoreCase("AcType")) {
                orderBy = "actype ";
            } else if (orderBy.equalsIgnoreCase("AcNo")) {
                orderBy = "o.acno ";
            } else if (orderBy.equalsIgnoreCase("VchNo")) {
                orderBy = "o.VTOT ";
            } else if (orderBy.equalsIgnoreCase("Amt")) {
                orderBy = "o.txninstamt ";
            }
            String bnkCode = ftsPosting.getBankCode();
            String branch = "";
            if (bnkCode.equalsIgnoreCase("rcbl")) {
                branch = "and (o.orgnbrcode='" + brCode + "' or o.destbrcode = '" + brCode + "')";
            } else {
                branch = "and o.orgnbrcode = '" + brCode + "'";
            }
            if (hdfcFlag && (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G"))) {

                tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) as "
                        + "custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,substring(ifnull(o.txnbankaddress,'N.A'),1,30)"
                        + "as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) "
                        + "as txninstamt,txnstatus=case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' then 'SECOND DAY' "
                        + "when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype, concat(cast(o.areacode as char(3)), "
                        + "cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, atm.acctType as actype From outward_clearing_after1day "
                        + "o left join accountmaster a on a.acno=o.acno left join td_accountmaster t on t.acno=o.acno left join gltable gl on gl.acno=o.acno "
                        + "left join accounttypemaster atm on substring(o.acno,3,2) = atm.acctCode where o.emflag = '" + emFlag + "' and o.txndate ='" + date
                        + "' " + branch + " and o.bnkcode=(select code from parameterinfo_report where reportname='IW_OW_REPORT') "
                        + "ORDER BY " + orderBy).getResultList();
            } else {
                if (reportName.equalsIgnoreCase("O/W Clg Entered")) {
                    tempList = em.createNativeQuery("select max(date) from bankdays where dayendflag = 'N' and brncode='" + brCode + "'").getResultList();
                    if (!tempList.isEmpty()) {
                        ele = (Vector) tempList.get(0);
                        if (ele != null || ele.get(0) != null || ele.get(0).toString().length() == 8) {
                            maxDate = ele.get(0).toString();
                        }
                    }
                    int dtdiff = (int) CbsUtil.dayDiff(ymdFormat.parse(date), ymdFormat.parse(maxDate));
                    if (dtdiff <= 0) {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            /**
                             * **Old Code *******
                             */
                            /*New Changed Code As Per Jamshedpur Requirement of Merged */
                            int mergedCode = MergedRepType();

                            if (mergedCode == 0) {
                                tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) "
                                        + "as custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                                        + "substring(ifnull(o.txnbankaddress,'N.A'),1,30)as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                                        + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                                        + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' "
                                        + "then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype, "
                                        + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))), "
                                        + "atm.acctType as actype  From outward_clearing_after1day o left join accountmaster a on a.acno=o.acno left join "
                                        + "td_accountmaster t on t.acno=o.acno left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm on "
                                        + "substring(o.acno,3,2) = atm.acctCode where o.emflag = '" + emFlag + "' and o.txndate ='" + date + "' "
                                        + "" + branch + " and o.bnkcode<>(select code from parameterinfo_report where "
                                        + "ReportName='IW_OW_REPORT') ORDER BY " + orderBy).getResultList();
                            } else {
                                tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) as "
                                        + "custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                                        + "substring(ifnull(o.txnbankaddress,'N.A'),1,30)as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                                        + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                                        + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' "
                                        + "then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype, "
                                        + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, "
                                        + "atm.acctType as actype From outward_clearing_after1day o left join accountmaster a on a.acno=o.acno left join "
                                        + "td_accountmaster t on t.acno=o.acno left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm on "
                                        + "substring(o.acno,3,2) = atm.acctCode where o.emflag = '" + emFlag + "' and o.txndate ='" + date + "' " + branch + " ORDER BY " + orderBy).getResultList();
                            }
                            /*New Code End*/
                        } else {
                            tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) "
                                    + "as custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                                    + "substring(ifnull(o.txnbankaddress,'N.A'),1,30)as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                                    + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                                    + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' "
                                    + "then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype, "
                                    + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, atm.acctType as actype"
                                    + " From clg_localchq o left join accountmaster a on a.acno=o.acno left join td_accountmaster t on t.acno=o.acno "
                                    + "left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm on substring(o.acno,3,2) = atm.acctCode where "
                                    + "o.emflag = '" + emFlag + "' and o.txndate ='" + date + "' " + branch + " ORDER BY " + orderBy).getResultList();
                        }
                    } else if (dtdiff == 1) {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) as "
                                    + "custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                                    + "substring(ifnull(o.txnbankaddress,'N.A'),1,30)as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                                    + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                                    + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' "
                                    + "then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype, "
                                    + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, "
                                    + "atm.acctType as actype  From outward_clearing_after1day o left join accountmaster a on a.acno=o.acno left join "
                                    + "td_accountmaster t on t.acno=o.acno left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm "
                                    + "on substring(o.acno,3,2) = atm.acctCode where o.emflag = '" + emFlag + "' and o.txndate ='" + date + "' "
                                    + "" + branch + " ORDER BY " + orderBy/*+" o.txntype,substring(o.acno,3,2), o.VTOT"*/).getResultList();
                        } else {
                            tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) as "
                                    + "custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                                    + "substring(ifnull(o.txnbankaddress,'N.A'),1,30)as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                                    + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                                    + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' "
                                    + "then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype ,"
                                    + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, atm.acctType as actype"
                                    + "From clg_localchq o left join accountmaster a on a.acno=o.acno left join td_accountmaster t on t.acno=o.acno left join "
                                    + "gltable gl on gl.acno=o.acno  left join accounttypemaster atm on substring(o.acno,3,2) = atm.acctCode where o.emflag = '"
                                    + emFlag + "' and o.txndate ='" + date + "' " + branch + " ORDER BY " + orderBy).getResultList();
                        }
                    } else {
                        if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                            tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) as"
                                    + " custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                                    + "substring(ifnull(o.txnbankaddress,'N.A'),1,30)as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                                    + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                                    + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' "
                                    + "then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype,"
                                    + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, "
                                    + "atm.acctType as actype  From outward_clearing_after2days o left join accountmaster a on a.acno=o.acno left join "
                                    + "td_accountmaster t on t.acno=o.acno left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm on "
                                    + "substring(o.acno,3,2) = atm.acctCode where o.emflag = '" + emFlag + "' and o.txndate ='" + date + "' "
                                    + "" + branch + " ORDER BY " + orderBy/*+" o.txntype,substring(o.acno,3,2), o.VTOT"*/).getResultList();
                        } else {
                            tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) as name"
                                    + ",ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                                    + "substring(ifnull(o.txnbankaddress,'N.A'),1,30) as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                                    + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                                    + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' "
                                    + "when 'D' then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype,"
                                    + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode,"
                                    + "atm.acctType as actype From clg_localchq o left join accountmaster a on a.acno=o.acno left join td_accountmaster t "
                                    + "on t.acno=o.acno left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm on substring(o.acno,3,2) = atm.acctCode "
                                    + "where o.emflag = '" + emFlag + "' and o.txndate ='" + date + "' " + branch + " ORDER BY " + orderBy).getResultList();
                        }
                    }
                } else if (reportName.equalsIgnoreCase("O/W Clg Passed")) {
                    tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) as custname,"
                            + "ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,substring(ifnull(o.txnbankaddress,'N.A'),1,30)"
                            + "as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,"
                            + "ifnull(o.txninstamt,0) as txninstamt,case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' "
                            + "then 'Hold' when 'D' then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype,"
                            + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, "
                            + "atm.acctType as actype From outward_clearing_after2days o left join accountmaster a on a.acno=o.acno left join td_accountmaster t on "
                            + "t.acno=o.acno left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm on substring(o.acno,3,2) = atm.acctCode where "
                            + "o.emflag = '" + emFlag + "' and o.txnstatus='P' and date_format(o.dt,'%Y%m%d')='" + date + "' " + branch + " ORDER BY " + orderBy).getResultList();
                } else if (reportName.equalsIgnoreCase("O/W CLG RETURNED CHEQUES")) {
                    tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) "
                            + "as custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                            + "substring(ifnull(o.txnbankaddress,'N.A'),1,30)as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                            + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                            + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' "
                            + "then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype,"
                            + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, "
                            + "atm.acctType as actype  From outward_clearing_after2days o left join accountmaster a on a.acno=o.acno left join td_accountmaster t on "
                            + "t.acno=o.acno left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm on substring(o.acno,3,2) = atm.acctCode "
                            + "where o.emflag = '" + emFlag + "' and o.txnstatus='R' and date_format(o.dt,'%Y%m%d')='" + date + "' " + branch + " ORDER BY " + orderBy).getResultList();
                } else if (reportName.equalsIgnoreCase("O/W CLG HELD CHEQUES")) {
                    tempList = em.createNativeQuery("select ifnull(o.acno,'') as acno,substring(ifnull(ifnull(a.custname,t.custname),gl.acname),1,40) "
                            + "as custname,ifnull(o.vtot,0) as vtot,substring(ifnull(o.txnbankname,'N.A'),1,30) as  txnbankname ,"
                            + "substring(ifnull(o.txnbankaddress,'N.A'),1,30)as txnbankaddress,ifnull(o.txninstno,'N.A') as txninstno,"
                            + "date_format(ifnull(o.txninstdate,''),'%d/%m/%Y') as txninstdate,ifnull(o.txninstamt,0) as txninstamt,"
                            + "case ifnull(o.txnstatus,'') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' then 'Hold' when 'D' "
                            + "then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end,ifnull(o.txntype,0) as txntype,"
                            + "concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)), cast(o.branchcode as char(3))) as micrcode, "
                            + "atm.acctType as actype  From outward_clearing_after2days o left join accountmaster a on a.acno=o.acno left join "
                            + "td_accountmaster t on t.acno=o.acno left join gltable gl on gl.acno=o.acno  left join accounttypemaster atm on "
                            + "substring(o.acno,3,2) = atm.acctCode where o.emflag = '" + emFlag + "' and o.txnstatus='H' and o.txndate='" + date
                            + "' " + branch + " ORDER BY " + orderBy).getResultList();
                }
            }
            for (int i = 0; i < tempList.size(); i++) {
                ele = (Vector) tempList.get(i);
                if (ele.get(0) != null) {
                    acNo = ele.get(0).toString();
                }
                String reasons = "";
                if (reportName.equalsIgnoreCase("O/W CLG RETURNED CHEQUES")) {
                    List list = em.createNativeQuery("select reasonforcancel from clg_returnedchq  where acno = '" + acNo + "'and txnstatus='R' and date_format(dt,'%Y%m%d')='" + date + "' and TxnInstNo = '" + ele.get(5).toString() + "'").getResultList();
                    Vector vtr = (Vector) list.get(0);
                    reasons = vtr.get(0).toString();
                }
                if (ele.get(1) != null) {
                    custName = ele.get(1).toString();
                }
                if (ele.get(2) != null || !ele.get(2).toString().equalsIgnoreCase("")) {
                    voucherNo = Integer.parseInt(ele.get(2).toString());
                }
                if (ele.get(3) != null) {
                    bankName = ele.get(3).toString();
                }
                if (ele.get(4) != null) {
                    bankAddress = ele.get(4).toString();
                }
                if (ele.get(5) != null) {
                    instNo = ele.get(5).toString();
                }
                if (ele.get(6) != null) {
                    instDate = ele.get(6).toString();
                }
                if (ele.get(7) != null || !ele.get(7).toString().equalsIgnoreCase("")) {
                    instAmount = Double.parseDouble(ele.get(7).toString());
                }
                if (ele.get(8) != null) {
                    status = ele.get(8).toString();
                }
                if (ele.get(10) != null) {
                    micrCode = ele.get(10).toString();
                }
                if (ele.get(11) != null) {
                    acType = ele.get(11).toString();
                }
                OutwardClearingEnteredReportPojo pojo = new OutwardClearingEnteredReportPojo();
                pojo.setsNo(i + 1);
                pojo.setAcNo(acNo);
                pojo.setReasons(reasons);
                pojo.setBankAddress(bankAddress);
                pojo.setBankName(bankName);
                pojo.setCustName(custName);
                pojo.setInstAmount(BigDecimal.valueOf(Double.parseDouble(new DecimalFormat("#.##").format(instAmount))));
                pojo.setInstDate(instDate);
                pojo.setInstNo(instNo);
                pojo.setMicrCode(micrCode);
                pojo.setStatus(status);
                pojo.setVoucherNo(voucherNo);
                pojo.setVchAmount(BigDecimal.valueOf(Double.parseDouble(new DecimalFormat("#.##").format(getVchAmount(tempList, voucherNo)))));
                pojo.setActype(acType);
                returnList.add(pojo);
            }

            /*              Code Added By Nishant Kansal               */
            String bnkName = "", bnkAdd = "";
            List dataList1 = common.getBranchNameandAddress(brCode);
            if (dataList1.size() > 0) {
                bnkName = (String) dataList1.get(0);
                bnkAdd = (String) dataList1.get(1);
            }
            if (returnList.size() > 0) {
                for (OutwardClearingEnteredReportPojo object : returnList) {
                    object.setBnkName(bnkName);
                    object.setBnkAdd(bnkAdd);
                    //object.setActype(object.getAcNo().substring(2, 4));
                    object.setBrncode(Integer.parseInt(brCode));
                }
            } else {
                throw new ApplicationException("No detail exists !");
            }
            /*              End Of Code Added By Nishant Kansal               */
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    private double getVchAmount(List resultList, int vchNo) throws ApplicationException {
        try {
            Vector ele;
            int voucherNo = 0;
            double vchAmount = 0;
            for (int i = 0; i < resultList.size(); i++) {
                ele = (Vector) resultList.get(i);
                voucherNo = Integer.parseInt(ele.get(2).toString());
                if (voucherNo == vchNo) {
                    vchAmount = vchAmount + Double.parseDouble(ele.get(7).toString());
                }
            }
            return vchAmount;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<OutwardClearingBatchReportPojo> getOutwardClearingBasedOnBatchReport(String date, String emFlag, String brCode, String orderBy) throws ApplicationException {
        List<OutwardClearingBatchReportPojo> returnList = new ArrayList<OutwardClearingBatchReportPojo>();
        List tempList = null;
        Vector ele;
        String acNo = "";
        String custName = "";
        int voucherNo = 0;
        String bankName = "";
        String bankAddress = "";
        String instNo = "";
        String instDate = "";
        double instAmount = 0;
        String status = "";
        String micrCode = "999999999";
        try {
            int iBrCode = Integer.parseInt(brCode);
            tempList = em.createNativeQuery("SELECT concat(CAST(ifnull(micr,999) AS char(3)), CAST(ifnull(micrCODE,999) AS char(3)),CAST(ifnull(BRANCHCODE,999) AS char(3))) "
                    + "from bnkadd where alphacode=(select alphacode from branchmaster where brncode="
                    + iBrCode + ")").getResultList();
            if (!tempList.isEmpty()) {
                ele = (Vector) tempList.get(0);
                if (ele != null || ele.get(0) != null || ele.get(0).toString().length() == 9) {
                    micrCode = ele.get(0).toString();
                }
            }
            if (orderBy.equalsIgnoreCase("AcType")) {
                orderBy = "substring(o.acno,3,2) ";
            } else if (orderBy.equalsIgnoreCase("AcNo")) {
                orderBy = "o.acno ";
            } else if (orderBy.equalsIgnoreCase("VchNo")) {
                orderBy = "o.VTOT ";
            } else if (orderBy.equalsIgnoreCase("Amt")) {
                orderBy = "o.txninstamt ";
            }
            tempList = em.createNativeQuery("SELECT ifnull(o.acno, '') as acno, substring(ifnull(ifnull(a.custname, t.custname), gl.acname), 1, 40) as custname, "
                    + "ifnull(o.vtot, 0) as vtot, substring(ifnull(o.txnbankname, 'N.A'), 1, 30) as txnbankname, substring(ifnull(o.txnbankaddress, 'N.A'), 1, 30) "
                    + "as txnbankaddress, ifnull(o.txninstno, 'N.A') as txninstno, date_format( ifnull(o.txninstdate, ''), '%d/%m/%Y') as txninstdate, "
                    + "ifnull(o.txninstamt, 0) as txninstamt,case ifnull(o.txnstatus, '') when 'P' then 'Passed' when 'R' then 'Returned' when 'H' "
                    + "then 'Hold' when 'D' then 'SECOND DAY' when 'E' then 'Entered' Else 'Others' end, concat(cast(o.areacode as char(3)), cast(o.bnkcode as char(3)) "
                    + ", cast(o.branchcode as char(3))) as micrcode From outward_clearing_after1day o left join accountmaster a on a.acno=o.acno left join td_accountmaster t on "
                    + "t.acno=o.acno left join gltable gl on gl.acno=o.acno where o.emflag ='" + emFlag + "' AND o.txndate ='" + date + "' and o.orgnbrcode='"
                    + brCode + "' and o.bnkcode<>240 order by " + orderBy).getResultList();
            for (int i = 0; i < tempList.size(); i++) {
                ele = (Vector) tempList.get(i);
                if (ele.get(0) != null) {
                    acNo = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    custName = ele.get(1).toString();
                }
                if (ele.get(2) != null || !ele.get(2).toString().equalsIgnoreCase("")) {
                    voucherNo = Integer.parseInt(ele.get(2).toString());
                }
                if (ele.get(3) != null) {
                    bankName = ele.get(3).toString();
                }
                if (ele.get(4) != null) {
                    bankAddress = ele.get(4).toString();
                }
                if (ele.get(5) != null) {
                    instNo = ele.get(5).toString();
                }
                if (ele.get(6) != null) {
                    instDate = ele.get(6).toString();
                }
                if (ele.get(7) != null || !ele.get(7).toString().equalsIgnoreCase("")) {
                    instAmount = Double.parseDouble(ele.get(7).toString());
                }
                if (ele.get(8) != null) {
                    status = ele.get(8).toString();
                }
                if (ele.get(9) != null) {
                    micrCode = ele.get(9).toString();
                }
                OutwardClearingBatchReportPojo pojo = new OutwardClearingBatchReportPojo();
                pojo.setAcNo(acNo);
                pojo.setBankAddress(bankAddress);
                pojo.setBankName(bankName);
                pojo.setCustName(custName);
                pojo.setInstAmount(BigDecimal.valueOf(Double.parseDouble(new DecimalFormat("#.##").format(instAmount))));
                pojo.setInstDate(instDate);
                pojo.setInstNo(instNo);
                pojo.setMicrCode(micrCode);
                pojo.setStatus(status);
                pojo.setVoucherNo(voucherNo);
                returnList.add(pojo);
            }

            /*              Code Added By Nishant Kansal               */
            String bnkName = "", bnkAdd = "";
            List dataList1 = common.getBranchNameandAddress(brCode);
            if (dataList1.size() > 0) {
                bnkName = (String) dataList1.get(0);
                bnkAdd = (String) dataList1.get(1);
            }
            List paramCodeLt = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME='CLGBATCH'").getResultList();
            int batchCount = 10000;
            if (!paramCodeLt.isEmpty()) {
                Vector paramCodeLtVec = (Vector) paramCodeLt.get(0);
                batchCount = Integer.parseInt(paramCodeLtVec.get(0).toString());
            }
            if (returnList.size() > 0) {
                int i = 1, batchNo = 1;
                for (OutwardClearingBatchReportPojo object : returnList) {
                    object.setBnkName(bnkName);
                    object.setBnkAdd(bnkAdd);
                    object.setActype(object.getAcNo().substring(2, 4));
                    object.setBrncode(Integer.parseInt(brCode));
                    if (i > batchCount) {
                        batchNo = batchNo + 1;
                        i = 1;
                    }
                    object.setBatchNo(batchNo);
                    i = i + 1;
                }
            } else {
                throw new ApplicationException("No detail exists !");
            }
            /*              End Of Code Added By Nishant Kansal               */
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List circleType() {
        List circleType = em.createNativeQuery("select circletype, circledesc, circlemicr from parameterinfo_clg "
                + "order by circletype").getResultList();
        return circleType;
    }

    public List<InwardClearingChequeReturnPojo> getInwardClearingCheque(String frmdt, String todt, String branchCode, String repoertType) throws ApplicationException {
        List<InwardClearingChequeReturnPojo> returnList = new ArrayList<InwardClearingChequeReturnPojo>();
        String acno, instNo, instDt, details;
        String custName, fatherName, prAddress;
        double amt = 0d;
        try {
            List result = new ArrayList();
            String tableName = "cts_clg_in_entry_history";
            String curDt = ymdFormat.format(new Date());
            if (ymdFormat.parse(frmdt).getTime() == ymdFormat.parse(curDt).getTime()) {
                tableName = "cts_clg_in_entry";
            }
            String brnCode = "";
            if (branchCode.equalsIgnoreCase("0A")) {
                brnCode = "90";
            } else {
                brnCode = branchCode;
            }

            List list = em.createNativeQuery("select bankname,BranchName from bnkadd a,branchmaster b where a.alphacode = b.AlphaCode and b.brncode = '" + brnCode + "'").getResultList();
            Vector vtr = (Vector) list.get(0);
            String bankName = vtr.get(0).toString();
            String branchName = vtr.get(1).toString();

            if (repoertType.equalsIgnoreCase("Outward")) {
                if (branchCode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select c.acno,c.TxnInstNo,c.TxnInstAmt,date_format(c.dt,'%d/%m/%Y'),c.reasonforcancel,c.TxnBankName, "
                            + "c.TxnBankAddress ,concat(ifnull(b.title,''),' ',ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,b.fathername,b.PerAddressLine1,b.PerAddressLine2,b.PerVillage,b.PerPostalCode  "
                            + "from clg_returnedchq c, cbs_customer_master_detail b , accountmaster a,customerid d  "
                            + "where cast(b.customerid as unsigned) = d.custid and c.Acno = a.ACNo and c.Acno = d.ACNo  "
                            + "and c.Txnstatus = 'R'  "
                            + "and date_format(c.dt,'%Y%m%d') between '" + frmdt + "'and '" + todt + "' "
                            + "union all  "
                            + "select c.acno,c.TxnInstNo,c.TxnInstAmt,date_format(c.dt,'%d/%m/%Y'),c.reasonforcancel,c.TxnBankName, "
                            + "c.TxnBankAddress ,a.acname,'' as fathername,'' as PerAddressLine1,'' as PerAddressLine2,'' as PerVillage, b.Pincode as PerPostalCode  "
                            + "from clg_returnedchq c, gltable a, branchmaster b "
                            + "where  c.Acno = a.ACNo  and b.BrnCode = cast(substring(c.acno,1,2) as unsigned)  "
                            + "and c.Txnstatus = 'R' "
                            + "and date_format(c.dt,'%Y%m%d') between '" + frmdt + "'and '" + todt + "'").getResultList();
                } else {
                    result = em.createNativeQuery("select c.acno,c.TxnInstNo,c.TxnInstAmt,date_format(c.dt,'%d/%m/%Y'),c.reasonforcancel,c.TxnBankName, "
                            + "c.TxnBankAddress ,concat(ifnull(b.title,''),' ',ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,b.fathername,b.PerAddressLine1,b.PerAddressLine2,b.PerVillage,b.PerPostalCode  "
                            + "from clg_returnedchq c, cbs_customer_master_detail b , accountmaster a,customerid d  "
                            + "where cast(b.customerid as unsigned) = d.custid and c.Acno = a.ACNo and c.Acno = d.ACNo  "
                            + "and c.Txnstatus = 'R' and c.orgnbrcode = '" + brnCode + "' "
                            + "and date_format(c.dt,'%Y%m%d') between '" + frmdt + "'and '" + todt + "' "
                            + "union all  "
                            + "select c.acno,c.TxnInstNo,c.TxnInstAmt,date_format(c.dt,'%d/%m/%Y'),c.reasonforcancel,c.TxnBankName, "
                            + "c.TxnBankAddress ,a.acname,'' as fathername,'' as PerAddressLine1,'' as PerAddressLine2,'' as PerVillage, b.Pincode as PerPostalCode  "
                            + "from clg_returnedchq c, gltable a, branchmaster b "
                            + "where  c.Acno = a.ACNo  and b.BrnCode = cast(substring(c.acno,1,2) as unsigned)  "
                            + "and c.Txnstatus = 'R' and c.orgnbrcode = '" + brnCode + "' "
                            + "and date_format(c.dt,'%Y%m%d') between '" + frmdt + "'and '" + todt + "'").getResultList();
                }
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector resV = (Vector) result.get(i);
                        InwardClearingChequeReturnPojo pojo = new InwardClearingChequeReturnPojo();
                        pojo.setAcNo(resV.get(0).toString());
                        pojo.setChecque(resV.get(1).toString());
                        pojo.setAmt(new BigDecimal(resV.get(2).toString()));
                        pojo.setInstDt(resV.get(3).toString());
                        pojo.setDetails(resV.get(4).toString());
                        pojo.setBankName(resV.get(5).toString());
                        pojo.setBranchName(resV.get(6).toString());
                        pojo.setCustName(resV.get(7).toString());
                        pojo.setFatherName(resV.get(8).toString());
                        pojo.setAddress(resV.get(9).toString());
                        pojo.setAddress2(resV.get(10).toString());
                        pojo.setPerVillage(resV.get(11).toString());
                        pojo.setPerPostalCode(resV.get(12).toString());
                        pojo.setOwnBankName(bankName);
                        pojo.setOwnBranchName(branchName);
                        returnList.add(pojo);
                    }
                }
            } else {
                if (branchCode.equalsIgnoreCase("0A")) {
//                    result = em.createNativeQuery("select c.acno,c.inst_no,c.inst_amt,date_format(c.dt,'%d/%m/%Y'),c.userdetails,b.custname,b.fathername,b.PrAddress from "
//                            + tableName + " c, customermaster b where substring(c.acno,1,2) = b.brncode and  substring(c.acno,3,2) = b.acType and "
//                            + "substring(c.acno,11,2) = b.agcode and substring(c.acno,5,6) = b.custno and dt between '" + frmdt + "'and '" + todt
//                            + "' and c.status='4'").getResultList();
                    result = em.createNativeQuery("select c.acno,c.inst_no,c.inst_amt,date_format(c.dt,'%d/%m/%Y'),c.userdetails,d.custname,"
                            + "d.fathername,d.PerAddressLine1,d.PerAddressLine2,d.PerVillage,d.PerPostalCode,c.BANK_NAME,c.BANK_ADD "
                            + "from " + tableName + " c, customerid b,cbs_customer_master_detail d where b.custid = cast(d.customerid as unsigned) "
                            + "and c.Acno = b.ACNo and substring(c.acno,1,2) = d.primarybrcode "
                            + "and dt between '" + frmdt + "'and '" + todt + "' and c.status='4'").getResultList();
                } else {
//                    result = em.createNativeQuery("select c.acno,c.inst_no,c.inst_amt,date_format(c.dt,'%d/%m/%Y'),c.userdetails,b.custname,b.fathername,b.PrAddress "
//                            + "from " + tableName + " c, customermaster b where substring(c.acno,1,2) = b.brncode and  substring(c.acno,3,2) = b.acType "
//                            + "and substring(c.acno,11,2) = b.agcode and substring(c.acno,5,6) = b.custno and substring(c.acno,1,2) = '" + branchCode
//                            + "' and dt between '" + frmdt + "'and '" + todt + "' and c.status='4'").getResultList();
                    result = em.createNativeQuery("select c.acno,c.inst_no,c.inst_amt,date_format(c.dt,'%d/%m/%Y'),c.userdetails,d.custname,"
                            + "d.fathername,d.PerAddressLine1,d.PerAddressLine2,d.PerVillage,d.PerPostalCode,c.BANK_NAME,c.BANK_ADD "
                            + "from " + tableName + " c, customerid b,cbs_customer_master_detail d where b.custid = cast(d.customerid as unsigned) "
                            + "and c.Acno = b.ACNo and substring(c.acno,1,2) = d.primarybrcode and substring(c.acno,1,2) = '" + branchCode + "' "
                            + "and dt between '" + frmdt + "'and '" + todt + "' and c.status='4'").getResultList();
                }
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector resV = (Vector) result.get(i);
                        acno = resV.get(0).toString();
                        instNo = resV.get(1).toString();
                        amt = Double.parseDouble(resV.get(2).toString());
                        instDt = resV.get(3).toString();
                        details = resV.get(4).toString();
                        custName = resV.get(5).toString();
                        fatherName = resV.get(6).toString();
                        prAddress = resV.get(7).toString();

                        InwardClearingChequeReturnPojo pojo = new InwardClearingChequeReturnPojo();
                        pojo.setAcNo(acno);
                        pojo.setAddress(prAddress);
                        pojo.setAmt(BigDecimal.valueOf(amt));
                        pojo.setChecque(instNo);
                        pojo.setCustName(custName);
                        pojo.setFatherName(fatherName);
                        pojo.setInstDt(instDt);
                        pojo.setDetails(details);

                        pojo.setAddress2(resV.get(8).toString());
                        pojo.setPerVillage(resV.get(9).toString());
                        pojo.setPerPostalCode(resV.get(10).toString());
                        pojo.setBankName(resV.get(11).toString());
                        pojo.setBranchName(resV.get(12).toString());
                        pojo.setOwnBankName(bankName);
                        pojo.setOwnBranchName(branchName);

                        returnList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;

    }

    public int MergedRepType() throws ApplicationException {
        int retVal = 0;
        List tempList = null;
        try {
            tempList = em.createNativeQuery("SELECT code FROM parameterinfo_report WHERE upper(Ltrim(RTrim(ReportName)))= 'HDFC_MERGED'").getResultList();
            if (!tempList.isEmpty()) {
                Vector ele = (Vector) tempList.get(0);
                retVal = Integer.parseInt(ele.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return retVal;

    }

    public List<InwardChequeReturnPojo> getInwardChequeReturn(String frmdt, String todt, String branchCode) throws ApplicationException {
        List<InwardChequeReturnPojo> chequeReturnList = new ArrayList<InwardChequeReturnPojo>();
        List result = new ArrayList();
        String chequeNo = "";
        try {
            String tableName = "cts_clg_in_entry_history";
            String curDt = ymdFormat.format(new Date());
            if (ymdFormat.parse(frmdt).getTime() == ymdFormat.parse(curDt).getTime()) {
                tableName = "cts_clg_in_entry";
            }
            if (branchCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select c.acno,b.custname,c.inst_no,c.favor_of,c.inst_dt from " + tableName + " c, customermaster b "
                        + "where substring(c.acno,1,2) = b.brncode and  substring(c.acno,3,2) = b.acType and substring(c.acno,11,2) = b.agcode "
                        + "and substring(c.acno,5,6) = b.custno and dt between '" + frmdt + "'and '" + todt + "' and c.status='4'order by c.inst_dt").getResultList();
            } else {
                result = em.createNativeQuery("select c.acno,b.custname,c.inst_no,c.favor_of,c.inst_dt from " + tableName + " c, customermaster b "
                        + "where substring(c.acno,1,2) = b.brncode and  substring(c.acno,3,2) = b.acType and substring(c.acno,11,2) = b.agcode "
                        + "and substring(c.acno,5,6) = b.custno and substring(c.acno,1,2) = '" + branchCode + "' and dt between '" + frmdt + "'"
                        + "and '" + todt + "' and c.status='4'order by c.inst_dt").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    InwardChequeReturnPojo pojo = new InwardChequeReturnPojo();
                    chequeNo = "0000" + ftsPosting.lPading(vtr.get(2).toString(), 6, "0");
                    pojo.setAcno(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setChequeNo(chequeNo);
                    pojo.setChequeFavo(vtr.get(3).toString());
                    pojo.setDate(vtr.get(4).toString().substring(8, 10) + "/" + vtr.get(4).toString().substring(5, 7) + "/" + vtr.get(4).toString().substring(0, 4));
                    chequeReturnList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return chequeReturnList;
    }

    public List<DemandDraftPayOrderPojo> getDemandDraftPayOrderDetail(String frmdt, String todt, String branchCode, String modeType) throws ApplicationException {
        List<DemandDraftPayOrderPojo> ddPoList = new ArrayList<DemandDraftPayOrderPojo>();
        List result = new ArrayList();
        String ddPoNo = "";

        try {
            if (branchCode.equalsIgnoreCase("0A")) {
                if (modeType.equalsIgnoreCase("ALL")) {
                    result = em.createNativeQuery("select bi.dt,bi.instno,bi.infavourof,bi.amount,bi.payableat,bi.custname,bi.trantype from bill_po bi where bi.dt between '" + frmdt + "' and '" + todt + "'").getResultList();
                } else {
                    result = em.createNativeQuery("select bi.dt,bi.instno,bi.infavourof,bi.amount,bi.payableat,bi.custname,bi.trantype from bill_po bi where bi.TRANTYPE ='" + modeType + "' and bi.dt between '" + frmdt + "' and '" + todt + "'").getResultList();
                }

            } else {
                if (modeType.equalsIgnoreCase("ALL")) {
                    result = em.createNativeQuery("select bi.dt,bi.instno,bi.infavourof,bi.amount,bi.payableat,bi.custname,bi.trantype from bill_po bi where "
                            + "substring(bi.acno,1,2)='" + branchCode + "' and bi.dt between '" + frmdt + "' and '" + todt + "'").getResultList();
                } else {
                    result = em.createNativeQuery("select bi.dt,bi.instno,bi.infavourof,bi.amount,bi.payableat,bi.custname,bi.trantype from bill_po bi where substring(bi.acno,1,2)='" + branchCode + "' "
                            + "and bi.trantype = '" + modeType + "'and bi.dt between '" + frmdt + "' and '" + todt + "'").getResultList();
                }
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    DemandDraftPayOrderPojo pojo = new DemandDraftPayOrderPojo();
                    ddPoNo = "0000" + ftsPosting.lPading(vtr.get(1).toString(), 6, "0");
                    pojo.setDate(vtr.get(0).toString().substring(8, 10) + "/" + vtr.get(0).toString().substring(5, 7) + "/" + vtr.get(0).toString().substring(0, 4));
                    pojo.setDdNoPoNo(ddPoNo);
                    pojo.setChequeFavo(vtr.get(2).toString());
                    pojo.setAmount(new BigDecimal(vtr.get(3).toString()));
                    pojo.setPayable(vtr.get(4).toString());
                    pojo.setCustName(vtr.get(5).toString());
                    if (modeType.equalsIgnoreCase("0")) {
                        pojo.setMode("Cash");
                    } else if (modeType.equalsIgnoreCase("1")) {
                        pojo.setMode("Clearing");

                    } else if (modeType.equalsIgnoreCase("2")) {
                        pojo.setMode("Transfer");

                    } else {
                        pojo.setMode("ALL");
                    }
                    ddPoList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return ddPoList;
    }

    public List<ClearingOperationReportPojo> getClearingOperationData(String frmdt, String todt, String branchCode) throws ApplicationException {
        List<ClearingOperationReportPojo> dataList = new ArrayList<ClearingOperationReportPojo>();
        List result1 = new ArrayList();
        List result2 = new ArrayList();
        List result3 = new ArrayList();
        List result4 = new ArrayList();
        double instno = 0d, txninstno = 0d;
        double instamt = 0d, txninstamt = 0d;
        double instnoPer = 0d, instamtPer = 0d;

        int nodays;

        double cpInstno = 0d, cpInstamt = 0d;
        double cpTxninstno = 0d, cpTxninstamt = 0d;
        double cpInstnoPer = 0d, cpInstamtPer = 0d;
        String date = null;
        int dt = 0;
        try {
            nodays = (int) CbsUtil.dayDiff(ymdFormat.parse(frmdt), ymdFormat.parse(todt));
            for (int i = 0; i <= nodays; i++) {
                dt = dt + 1;
                if (dt == 1) {
                    date = frmdt;
                } else {
                    frmdt = CbsUtil.dateAdd(frmdt, 1);
                }

                ClearingOperationReportPojo pojo = new ClearingOperationReportPojo();

                if (dt == 1) {
                    result1 = em.createNativeQuery("select sum(instno),cast(sum(instamt) as decimal(25,2)),dt from ((select count(txninstno) as instno, "
                            + "sum(txninstamt) as  instamt, date_format(txndate,'%Y%m%d') as dt from clg_in_history where date_format(txndate,'%Y%m%d') = "
                            + "'" + date + "' and substring(acno,1,2)='" + branchCode + "' group by date_format(txndate,'%Y%m%d')) "
                            + "Union "
                            + "(select count(txninstno) as instno ,sum(txninstamt) as  instamt,date_format(txndate,'%Y%m%d') as dt from clg_in_returned "
                            + "where date_format(txndate,'%Y%m%d') = '" + date + "'and substring(acno,1,2)='" + branchCode + "' group by "
                            + "date_format(txndate,'%Y%m%d'))) as tmp group by dt order by dt").getResultList();
                } else {
                    result1 = em.createNativeQuery("select sum(instno),cast(sum(instamt)as decimal(25,2)),dt from ((select count(txninstno) as instno, "
                            + "sum(txninstamt) as instamt,date_format(txndate,'%Y%m%d') as dt from clg_in_history where date_format(txndate,'%Y%m%d')= '"
                            + frmdt + "'and substring(acno,1,2)='" + branchCode + "' group by date_format(txndate,'%Y%m%d')) Union "
                            + "(select count(txninstno) as instno ,sum(txninstamt) as instamt ,date_format(txndate,'%Y%m%d') as dt from clg_in_returned "
                            + "where date_format(txndate,'%Y%m%d') = '" + frmdt + "'and substring(acno,1,2)='" + branchCode + "' group by "
                            + "date_format(txndate,'%Y%m%d'))) as tmp group by dt order by dt").getResultList();
                }

                if (!result1.isEmpty()) {

                    Vector ele1 = (Vector) result1.get(0);
                    instno = Double.parseDouble(ele1.get(0).toString());
                    instamt = Double.parseDouble(ele1.get(1).toString());
                }
                if (dt == 1) {
                    result2 = em.createNativeQuery("select count(txninstno),cast(sum(txninstamt)as decimal(25,2)),date_format(txndate,'%Y%m%d') from "
                            + "clg_in_returned where date_format(txndate,'%Y%m%d') = '" + frmdt + "'and substring(acno,1,2)='" + branchCode + "' group by "
                            + "date_format(txndate,'%Y%m%d')").getResultList();
                } else {
                    result2 = em.createNativeQuery("select count(txninstno),cast(sum(txninstamt)as decimal(25,2)),date_format(txndate,'%Y%m%d') from "
                            + "clg_in_returned where date_format(txndate,'%Y%m%d') = '" + frmdt + "' and substring(acno,1,2)='" + branchCode + "' group by "
                            + "date_format(txndate,'%Y%m%d')").getResultList();
                }

                if (!result2.isEmpty()) {
                    Vector ele2 = (Vector) result2.get(0);
                    txninstno = Double.parseDouble(ele2.get(0).toString());
                    txninstamt = Double.parseDouble(ele2.get(1).toString());
                }

                instnoPer = (txninstno / instno) * 100;
                instamtPer = (txninstamt / instamt) * 100;
                /*
                 * another part of report
                 */
                if (dt == 1) {
                    result3 = em.createNativeQuery("select sum(instno),cast(sum(instamt)as decimal(25,2)),dt from ((select count(txninstno) as instno, "
                            + "sum(txninstamt) as instamt,date_format(txndate,'%Y%m%d') as dt from clg_ow_history where date_format(txndate,'%Y%m%d') = '"
                            + frmdt + "' and orgnbrcode = '" + branchCode + "' group by date_format(txndate,'%Y%m%d')) Union "
                            + "(select count(txninstno) as instno, sum(txninstamt) as instamt, date_format(txndate,'%Y%m%d') as dt from clg_returnedchq "
                            + "where date_format(txndate,'%Y%m%d') = '" + frmdt + "' and orgnbrcode = '" + branchCode + "' group by date_format(txndate,'%Y%m%d'))) as"
                            + "tmp group by dt order by dt").getResultList();
                } else {
                    result3 = em.createNativeQuery("select sum(instno),cast(sum(instamt) as decimal(25,2)),dt from ((select count(txninstno) instno, "
                            + "sum(txninstamt) instamt,date_format(txndate,'%Y%m%d') as dt from clg_ow_history where date_format(txndate,'%Y%m%d') = '"
                            + frmdt + "'and orgnbrcode = '" + branchCode + "' group by date_format(txndate,'%Y%m%d')) Union "
                            + "(select count(txninstno), sum(txninstamt), date_format(txndate,'%Y%m%d') from clg_returnedchq where "
                            + "date_format(txndate,'%Y%m%d') = '" + frmdt + "' and orgnbrcode = '" + branchCode + "' group by "
                            + "date_format(txndate,'%Y%m%d'))) as tmp group by dt order by dt").getResultList();
                }

                if (!result3.isEmpty()) {
                    Vector ele1 = (Vector) result3.get(0);
                    cpInstno = Double.parseDouble(ele1.get(0).toString());
                    cpInstamt = Double.parseDouble(ele1.get(1).toString());
                }
                if (dt == 1) {
                    result4 = em.createNativeQuery("select count(txninstno),cast(sum(txninstamt)as decimal(25,2)),date_format(txndate,'%Y%m%d') "
                            + "from clg_returnedchq where date_format(txndate,'%Y%m%d')= '" + frmdt + "' and orgnbrcode = '" + branchCode
                            + "' group by date_format(txndate,'%Y%m%d') order by date_format(txndate,'%Y%m%d') ").getResultList();
                } else {
                    result4 = em.createNativeQuery("select count(txninstno),cast(sum(txninstamt)as decimal(25,2)),date_format(txndate,'%Y%m%d') "
                            + "from clg_returnedchq where date_format(txndate,'%Y%m%d')= '" + frmdt + "'and orgnbrcode = '" + branchCode
                            + "' group by date_format(txndate,'%Y%m%d') order by date_format(txndate,'%Y%m%d') ").getResultList();
                }

                if (!result4.isEmpty()) {
                    Vector ele2 = (Vector) result4.get(0);
                    cpTxninstno = Double.parseDouble(ele2.get(0).toString());
                    cpTxninstamt = Double.parseDouble(ele2.get(1).toString());
                }

                cpInstnoPer = (cpTxninstno / cpInstno) * 100;
                cpInstamtPer = (cpTxninstamt / cpInstamt) * 100;

                if (result1.isEmpty()) {
                    pojo.setcRecevNo(0);
                    pojo.setcRecevAmt(0);
                } else {
                    pojo.setcRecevNo(instno);
                    pojo.setcRecevAmt(instamt);
                }

                if (result2.isEmpty()) {
                    pojo.setcRetrnNo(0);
                    pojo.setcRetrnAmt(0);
                } else {
                    pojo.setcRetrnNo(txninstno);
                    pojo.setcRetrnAmt(txninstamt);
                }

                if (result1.isEmpty() || result2.isEmpty()) {
                    pojo.setcRecevRetrnNoPercent(0);
                    pojo.setcRecevRetrnAmtPercent(0);
                } else {
                    pojo.setcRecevRetrnNoPercent(instnoPer);
                    pojo.setcRecevRetrnAmtPercent(instamtPer);
                }
                if (result3.isEmpty()) {
                    pojo.setcPrsntNo(0);
                    pojo.setcPrsntAmt(0);
                } else {
                    pojo.setcPrsntNo(cpInstno);
                    pojo.setcPrsntAmt(cpInstamt);
                }

                if (result4.isEmpty()) {
                    pojo.setChRetrnNo(0);
                    pojo.setChRetrnAmt(0);
                } else {
                    pojo.setChRetrnNo(cpTxninstno);
                    pojo.setChRetrnAmt(cpTxninstamt);
                }

                if (result3.isEmpty() || result4.isEmpty()) {
                    pojo.setcPrsntRetrnNoPercent(0);
                    pojo.setcPrsntRetrnAmtPercent(0);
                } else {
                    pojo.setcPrsntRetrnNoPercent(cpInstnoPer);
                    pojo.setcPrsntRetrnAmtPercent(cpInstamtPer);
                }

                pojo.setTxnDate(frmdt.substring(6, 8) + "/" + frmdt.substring(4, 6) + "/" + frmdt.substring(0, 4));
                dataList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<InoutWardClearingPassReturnPojo> getInoutwardChkPassReturn(String repBased, String repType, String branchCode, String frmdt, String todt, String acWise, String accountNo) throws ApplicationException {
        List<InoutWardClearingPassReturnPojo> dataList = new ArrayList<InoutWardClearingPassReturnPojo>();
        List result1 = new ArrayList();
        List result2 = new ArrayList();
        List result3 = new ArrayList();
        float totalChk = 0, totalRchk = 0, totalPchk = 0;
        double totalAmtOfTotalChk = 0d, totalAmtOfTotalPassChk = 0d, totalAmtOfTotalReturnChk = 0d;

        try {
            String account = "";
            if (repType.equalsIgnoreCase("Inward Clearing Cheques")) {
                if (acWise.equalsIgnoreCase("AL")) {
                    account = "";
                } else {
                    account = "and acno ='" + accountNo + "'";
                }
            } else {
                if (acWise.equalsIgnoreCase("AL")) {
                    account = "";
                } else {
                    account = "and c.acno = '" + accountNo + "'";
                }
            }

            if (repBased.equalsIgnoreCase("Detail")) {
                if (repType.equalsIgnoreCase("Inward Clearing Cheques")) {
                    if (branchCode.equalsIgnoreCase("0A")) {
                        result1 = em.createNativeQuery("select ACNO,CUSTNAME,INST_NO,INST_AMT,INST_DT,FAVOR_OF,STATUS,USERDETAILS,date_format(DT,'%d/%m/%Y') valueDt from cts_clg_in_entry_history where date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status in(2,4) " + account + " order by STATUS,INST_DT,ACNO").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select ACNO,CUSTNAME,INST_NO,INST_AMT,INST_DT,FAVOR_OF,STATUS,USERDETAILS,date_format(DT,'%d/%m/%Y') valueDt from cts_clg_in_entry_history where substring(acno,1,2) = '" + branchCode + "' and date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status in(2,4) " + account + " order by STATUS,INST_DT,ACNO").getResultList();
                    }
                } else {
                    if (branchCode.equalsIgnoreCase("0A")) {
//                        result1 = em.createNativeQuery("select Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName from clg_ow_history where TxnDate between '" + frmdt + "' and '" + todt + "' "
//                                + "union all "
//                                + "select  Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName from clg_returnedchq where TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'order by Acno,Txnstatus,TxnDate").getResultList();

                        result1 = em.createNativeQuery("select c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,custname from clg_ow_history c,accountmaster a where TxnDate between '" + frmdt + "' and '" + todt + "' and c.Acno = a.ACNo " + account + " "
                                + "union all "
                                + "select  c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,custname from clg_returnedchq c,accountmaster a where TxnDate between '" + frmdt + "' and '" + todt + "'  and c.acno = a.acno " + account + " "
                                + "and Txnstatus = 'R' "
                                + "union all "
                                + "select c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,custname from clg_ow_history c,td_accountmaster a where TxnDate between '" + frmdt + "' and '" + todt + "' and c.Acno = a.ACNo " + account + " "
                                + "union all "
                                + "select  c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,custname from clg_returnedchq c,td_accountmaster a where TxnDate between '" + frmdt + "' and '" + todt + "'  and c.acno = a.acno " + account + " "
                                + "and Txnstatus = 'R' "
                                + "union all "
                                + "select c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,acname from clg_ow_history c,gltable a where TxnDate between '" + frmdt + "' and '" + todt + "' and c.Acno = a.ACNo " + account + " "
                                + "union all "
                                + "select  c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,acname from clg_returnedchq c,gltable a where TxnDate between '" + frmdt + "' and '" + todt + "'  and c.acno = a.acno " + account + " "
                                + "and Txnstatus = 'R' order by 5,4").getResultList();
                    } else {
//                        result1 = em.createNativeQuery("select Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName from clg_ow_history where substring(acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' "
//                                + "union all "
//                                + "select  Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName from clg_returnedchq where substring(acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'order by Acno,Txnstatus,TxnDate").getResultList();
                        result1 = em.createNativeQuery("select c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,custname from clg_ow_history c,accountmaster a where substring(c.acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' and c.Acno = a.ACNo " + account + " "
                                + "union all "
                                + "select  c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,custname from clg_returnedchq c,accountmaster a where substring(c.acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "'  and c.acno = a.acno " + account + " "
                                + "and Txnstatus = 'R' "
                                + "union all "
                                + "select c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,custname from clg_ow_history c,td_accountmaster a where substring(c.acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' and c.Acno = a.ACNo " + account + " "
                                + "union all "
                                + "select  c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,custname from clg_returnedchq c,td_accountmaster a where substring(c.acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "'  and c.acno = a.acno " + account + " "
                                + "and Txnstatus = 'R' "
                                + "union all "
                                + "select c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,acname from clg_ow_history c,gltable a where substring(c.acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' and c.Acno = a.ACNo " + account + " "
                                + "union all "
                                + "select  c.Acno,TxnInstNo,TxnInstAmt,TxnDate,Txnstatus,TxnBankName,acname from clg_returnedchq c,gltable a where substring(c.acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "'  and c.acno = a.acno " + account + " "
                                + "and Txnstatus = 'R' order by 5,4").getResultList();
                    }
                }
                if (!result1.isEmpty()) {
                    for (int i = 0; i < result1.size(); i++) {
                        Vector ele = (Vector) result1.get(i);
                        InoutWardClearingPassReturnPojo pojo = new InoutWardClearingPassReturnPojo();
                        String acNo = ele.get(0).toString();
                        if (repType.equalsIgnoreCase("Inward Clearing Cheques")) {
                            pojo.setAcno(acNo);
                            pojo.setCustName(ele.get(1).toString());
                            pojo.setInstNo(ele.get(2).toString());
                            pojo.setInstAmt(Double.parseDouble(ele.get(3).toString()));
                            pojo.setInstDt(ele.get(4).toString().substring(8, 10) + "/" + ele.get(4).toString().substring(5, 7) + "/" + ele.get(4).toString().substring(0, 4));
                            pojo.setFavOf(ele.get(5).toString());
                            pojo.setUserStatus(ele.get(6).toString());
                            if (ele.get(6).toString().equalsIgnoreCase("2")) {
                                pojo.setUserStatusDesc("Passed");
                            } else {
                                pojo.setUserStatusDesc("Returend");
                            }
                            pojo.setUsreDetail(ele.get(7).toString());
                            pojo.setValueDt(ele.get(8).toString());
                            pojo.setRepType("Inward Clearing Cheques");
                            dataList.add(pojo);
                        } else {
                            pojo.setAcno(acNo);

//                           if(acNo.equalsIgnoreCase("010620160201")){
//                                System.out.print("AcNo - - - > " + acNo);
//                           }

//                            String name = "";
//                            List nameList = em.createNativeQuery("select custname from accountmaster where acno = '" + acNo + "' "
//                                    + "union "
//                                    + "select custname from td_accountmaster where acno = '" + acNo + "' "
//                                    + "union "
//                                    + "select AcName from gltable where acno = '" + acNo + "'").getResultList();
//                            if (!nameList.isEmpty()) {
//                                Vector ele1 = (Vector) nameList.get(0);
//                                name = ele1.get(0).toString();
//                            }

                            pojo.setCustName(ele.get(6).toString());
                            pojo.setInstNo(ele.get(1).toString());
                            pojo.setInstAmt(Double.parseDouble(ele.get(2).toString()));
                            pojo.setInstDt(ele.get(3).toString().substring(8, 10) + "/" + ele.get(3).toString().substring(5, 7) + "/" + ele.get(3).toString().substring(0, 4));
                            pojo.setFavOf(ele.get(5).toString());
                            pojo.setUserStatus(ele.get(4).toString());
                            if (ele.get(4).toString().equalsIgnoreCase("C")) {
                                pojo.setUsreDetail("Passed");
                                pojo.setUserStatusDesc("Passed");
                            } else {
                                pojo.setUsreDetail("Returend");
                                pojo.setUserStatusDesc("Returend");
                            }
                            dataList.add(pojo);
                        }
                    }
                }
            } else if (repBased.equalsIgnoreCase("Summary")) {

                if (repType.equalsIgnoreCase("Inward Clearing Cheques")) {
                    if (branchCode.equalsIgnoreCase("0A")) {
                        result1 = em.createNativeQuery("select count(*),ifnull(cast(sum(INST_AMT)as decimal(14,2)),0) from cts_clg_in_entry_history where date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "'").getResultList();
                        Vector vtr1 = (Vector) result1.get(0);
                        totalChk = Integer.parseInt(vtr1.get(0).toString());
                        totalAmtOfTotalChk = Double.parseDouble(vtr1.get(1).toString());

                        result2 = em.createNativeQuery("select count(*),ifnull(cast(sum(INST_AMT)as decimal(14,2)),0) from cts_clg_in_entry_history where date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status = 2").getResultList();
                        Vector vtr2 = (Vector) result2.get(0);
                        totalPchk = Integer.parseInt(vtr2.get(0).toString());
                        totalAmtOfTotalPassChk = Double.parseDouble(vtr2.get(1).toString());

                        result3 = em.createNativeQuery("select count(*),ifnull(cast(sum(INST_AMT)as decimal(14,2)),0) from cts_clg_in_entry_history where date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status = 4").getResultList();
                        Vector vtr3 = (Vector) result3.get(0);
                        totalRchk = Integer.parseInt(vtr3.get(0).toString());
                        totalAmtOfTotalReturnChk = Double.parseDouble(vtr3.get(0).toString());

                    } else {
                        result1 = em.createNativeQuery("select count(*),ifnull(cast(sum(INST_AMT)as decimal(14,2)),0) from cts_clg_in_entry_history where substring(acno,1,2) = '" + branchCode + "' and date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "'").getResultList();
                        Vector vtr1 = (Vector) result1.get(0);
                        totalChk = Integer.parseInt(vtr1.get(0).toString());
                        totalAmtOfTotalChk = Double.parseDouble(vtr1.get(1).toString());

                        result2 = em.createNativeQuery("select count(*),ifnull(cast(sum(INST_AMT)as decimal(14,2)),0) from cts_clg_in_entry_history where substring(acno,1,2) = '" + branchCode + "' and date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status = 2").getResultList();
                        Vector vtr2 = (Vector) result2.get(0);
                        totalPchk = Integer.parseInt(vtr2.get(0).toString());
                        totalAmtOfTotalPassChk = Double.parseDouble(vtr2.get(1).toString());

                        result3 = em.createNativeQuery("select count(*),ifnull(cast(sum(INST_AMT)as decimal(14,2)),0) from cts_clg_in_entry_history where substring(acno,1,2) = '" + branchCode + "' and date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status = 4").getResultList();
                        Vector vtr3 = (Vector) result3.get(0);
                        totalRchk = Integer.parseInt(vtr3.get(0).toString());
                        totalAmtOfTotalReturnChk = Double.parseDouble(vtr3.get(1).toString());
                    }

                    InoutWardClearingPassReturnPojo pojo = new InoutWardClearingPassReturnPojo();
                    pojo.setNoOfTotalChk(totalChk);
                    pojo.setNoOfPassChk(totalPchk);
                    pojo.setNoOfReturnChk(totalRchk);
                    pojo.setNoOfPassChkPercent((totalPchk / totalChk) * 100);
                    pojo.setNoOfReturnChkPercent((totalRchk / totalChk) * 100);

                    pojo.setTotalAmtOfTotalChk(totalAmtOfTotalChk);
                    pojo.setTotalAmtOfTotalPassChk(totalAmtOfTotalPassChk);
                    pojo.setTotalAmtOfTotalReturnChk(totalAmtOfTotalReturnChk);
                    pojo.setTotalAmtOfTotalPassChkPer((totalAmtOfTotalPassChk / totalAmtOfTotalChk) * 100);
                    pojo.setTotalAmtOfTotalReturnChkPer((totalAmtOfTotalReturnChk / totalAmtOfTotalChk) * 100);
                    dataList.add(pojo);

                } else {
                    if (branchCode.equalsIgnoreCase("0A")) {
                        result1 = em.createNativeQuery("select count(*),ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) from clg_ow_history where TxnDate between '" + frmdt + "' and '" + todt + "'").getResultList();
                        Vector vtr1 = (Vector) result1.get(0);
                        totalPchk = Integer.parseInt(vtr1.get(0).toString());
                        totalAmtOfTotalPassChk = Double.parseDouble(vtr1.get(1).toString());

                        result2 = em.createNativeQuery("select count(*),ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) from clg_returnedchq where TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'").getResultList();
                        Vector vtr2 = (Vector) result2.get(0);
                        totalRchk = Integer.parseInt(vtr2.get(0).toString());
                        totalAmtOfTotalReturnChk = Double.parseDouble(vtr2.get(1).toString());

                        totalChk = totalPchk + totalRchk;
                        totalAmtOfTotalChk = totalAmtOfTotalPassChk + totalAmtOfTotalReturnChk;
                    } else {
                        result1 = em.createNativeQuery("select count(*),ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) from clg_ow_history where substring(acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "'").getResultList();
                        Vector vtr1 = (Vector) result1.get(0);
                        totalPchk = Integer.parseInt(vtr1.get(0).toString());
                        totalAmtOfTotalPassChk = Double.parseDouble(vtr1.get(1).toString());

                        result2 = em.createNativeQuery("select count(*),ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) from clg_returnedchq where substring(acno,1,2) = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'").getResultList();
                        Vector vtr2 = (Vector) result2.get(0);
                        totalRchk = Integer.parseInt(vtr2.get(0).toString());
                        totalAmtOfTotalReturnChk = Double.parseDouble(vtr2.get(1).toString());

                        totalChk = totalPchk + totalRchk;
                        totalAmtOfTotalChk = totalAmtOfTotalPassChk + totalAmtOfTotalReturnChk;
                    }
                    InoutWardClearingPassReturnPojo pojo = new InoutWardClearingPassReturnPojo();
                    pojo.setNoOfTotalChk(totalChk);
                    pojo.setNoOfPassChk(totalPchk);
                    pojo.setNoOfReturnChk(totalRchk);
                    pojo.setNoOfPassChkPercent((totalPchk / totalChk) * 100);
                    pojo.setNoOfReturnChkPercent((totalRchk / totalChk) * 100);

                    pojo.setTotalAmtOfTotalChk(totalAmtOfTotalChk);
                    pojo.setTotalAmtOfTotalPassChk(totalAmtOfTotalPassChk);
                    pojo.setTotalAmtOfTotalReturnChk(totalAmtOfTotalReturnChk);
                    pojo.setTotalAmtOfTotalPassChkPer((totalAmtOfTotalPassChk / totalAmtOfTotalChk) * 100);
                    pojo.setTotalAmtOfTotalReturnChkPer((totalAmtOfTotalReturnChk / totalAmtOfTotalChk) * 100);
                    dataList.add(pojo);
                }
            } else if (repBased.equalsIgnoreCase("Inward Date Wise") && repType.equalsIgnoreCase("Inward Clearing Cheques")) {
                if (branchCode.equalsIgnoreCase("0A")) {
                    result1 = em.createNativeQuery("select  a.InstNo,a.InstAmt,a.valueDt,ifnull(b.InstNo,0),ifnull(b.InstAmt,0),b.valueDt from\n"
                            + "(select count(INST_NO) InstNo,cast(sum(INST_AMT) as decimal(25,2)) InstAmt,date_format(inst_dt,'%d/%m/%Y') InstDt,date_format(DT,'%d/%m/%Y') valueDt from cts_clg_in_entry_history where date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status = 2 group by date_format(DT,'%Y%m%d') order by date_format(DT,'%Y%m%d'))a\n"
                            + "left join\n"
                            + "(select count(INST_NO) InstNo,cast(sum(INST_AMT) as decimal(25,2)) InstAmt,date_format(inst_dt,'%d/%m/%Y') InstDt,date_format(DT,'%d/%m/%Y') valueDt from cts_clg_in_entry_history where date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status = 4 group by date_format(DT,'%Y%m%d') order by date_format(DT,'%Y%m%d'))b\n"
                            + "on  a.InstDt = b.InstDt order by 3").getResultList();
                } else {
                    result1 = em.createNativeQuery("select  a.InstNo,a.InstAmt,a.valueDt,ifnull(b.InstNo,0),ifnull(b.InstAmt,0),b.valueDt from\n"
                            + "(select count(INST_NO) InstNo,cast(sum(INST_AMT) as decimal(25,2)) InstAmt,date_format(inst_dt,'%d/%m/%Y') InstDt,date_format(DT,'%d/%m/%Y') valueDt from cts_clg_in_entry_history where dest_branch ='" + branchCode + "' and date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status = 2 group by date_format(DT,'%Y%m%d') order by date_format(DT,'%Y%m%d'))a\n"
                            + "left join\n"
                            + "(select count(INST_NO) InstNo,cast(sum(INST_AMT) as decimal(25,2)) InstAmt,date_format(inst_dt,'%d/%m/%Y') InstDt,date_format(DT,'%d/%m/%Y') valueDt from cts_clg_in_entry_history where dest_branch ='" + branchCode + "' and date_format(DT,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' and status = 4 group by date_format(DT,'%Y%m%d') order by date_format(DT,'%Y%m%d'))b\n"
                            + "on  a.InstDt = b.InstDt order by 3").getResultList();
                }
            } else if (repBased.equalsIgnoreCase("Outward Date Wise") && repType.equalsIgnoreCase("Outward Clearing Cheques")) {
                if (branchCode.equalsIgnoreCase("0A")) {
                    result1 = em.createNativeQuery("select  a.InstNo1,a.InstAmt1,a.InstDt1,b.InstNo2,b.InstAmt2,b.InstDt2  from (\n"
                            + "(select count(*) InstNo1,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt1,TxnDate InstDt1 from clg_ow_history where TxnDate between '" + frmdt + "' and '" + todt + "' group by TxnDate order by TxnDate)a,\n"
                            + "(select count(*) InstNo2,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt2,TxnDate InstDt2 from clg_returnedchq where TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'group by TxnDate order by TxnDate)b\n"
                            + ")where a.InstDt1 = b.InstDt2\n"
                            + "union all\n"
                            + "select count(*) InstNo1,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt1,TxnDate InstDt1,0 InstNo2,0 InstAmt2,''InstDt2 from clg_ow_history where TxnDate between '" + frmdt + "' and '" + todt + "' and TxnDate not in(select a.InstDt  from (\n"
                            + "(select count(*) InstNo,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt,TxnDate InstDt from clg_ow_history where TxnDate between '" + frmdt + "' and '" + todt + "' group by TxnDate order by TxnDate)a,\n"
                            + "(select count(*) InstNo,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt,TxnDate InstDt from clg_returnedchq where TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'group by TxnDate order by TxnDate)b\n"
                            + ")where a.InstDt = b.InstDt\n"
                            + ")group by  TxnDate\n"
                            + "union all\n"
                            + "select 0 InstNo1,0 InstAmt1,''InstDt1,count(*) InstNo2,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt2,TxnDate InstDt2 from clg_returnedchq where TxnDate between '" + frmdt + "' and '" + todt + "' and TxnDate not in(select a.InstDt  from (\n"
                            + "(select count(*) InstNo,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt,TxnDate InstDt from clg_ow_history where TxnDate between '" + frmdt + "' and '" + todt + "' group by TxnDate order by TxnDate)a,\n"
                            + "(select count(*) InstNo,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt,TxnDate InstDt from clg_returnedchq where TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'group by TxnDate order by TxnDate)b\n"
                            + ")where a.InstDt = b.InstDt\n"
                            + ")group by  TxnDate\n"
                            + "order by 6").getResultList();

                } else {
                    result1 = em.createNativeQuery("select  a.InstNo1,a.InstAmt1,a.InstDt1,b.InstNo2,b.InstAmt2,b.InstDt2  from (\n"
                            + "(select count(*) InstNo1,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt1,TxnDate InstDt1 from clg_ow_history where orgnbrcode = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' group by TxnDate order by TxnDate)a,\n"
                            + "(select count(*) InstNo2,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt2,TxnDate InstDt2 from clg_returnedchq where orgnbrcode = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'group by TxnDate order by TxnDate)b\n"
                            + ")where a.InstDt1 = b.InstDt2\n"
                            + "union all\n"
                            + "select count(*) InstNo1,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt1,TxnDate InstDt1,0 InstNo2,0 InstAmt2,''InstDt2 from clg_ow_history where orgnbrcode = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' \n"
                            + "and TxnDate not in(select a.InstDt  from (\n"
                            + "(select count(*) InstNo,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt,TxnDate InstDt from clg_ow_history where TxnDate between '" + frmdt + "' and '" + todt + "' group by TxnDate order by TxnDate)a,\n"
                            + "(select count(*) InstNo,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt,TxnDate InstDt from clg_returnedchq where TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'group by TxnDate order by TxnDate)b\n"
                            + ")where a.InstDt = b.InstDt\n"
                            + ")group by  TxnDate\n"
                            + "union all\n"
                            + "select 0 InstNo1,0 InstAmt1,''InstDt1,count(*) InstNo2,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt2,TxnDate InstDt2 from clg_returnedchq where orgnbrcode = '" + branchCode + "' and TxnDate between '" + frmdt + "' and '" + todt + "' \n"
                            + "and TxnDate not in(select a.InstDt  from (\n"
                            + "(select count(*) InstNo,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt,TxnDate InstDt from clg_ow_history where TxnDate between '" + frmdt + "' and '" + todt + "' group by TxnDate order by TxnDate)a,\n"
                            + "(select count(*) InstNo,ifnull(cast(sum(TxnInstAmt)as decimal(14,2)),0) InstAmt,TxnDate InstDt from clg_returnedchq where TxnDate between '" + frmdt + "' and '" + todt + "' and Txnstatus = 'R'group by TxnDate order by TxnDate)b\n"
                            + ")where a.InstDt = b.InstDt\n"
                            + ")group by  TxnDate\n"
                            + "order by 6").getResultList();

                }
            }

            if (repBased.equalsIgnoreCase("Inward Date Wise") && repType.equalsIgnoreCase("Inward Clearing Cheques")
                    || repBased.equalsIgnoreCase("Outward Date Wise") && repType.equalsIgnoreCase("Outward Clearing Cheques")) {
                if (!result1.isEmpty()) {
                    for (int i = 0; i < result1.size(); i++) {
                        Vector prVector = (Vector) result1.get(i);
                        InoutWardClearingPassReturnPojo pojo = new InoutWardClearingPassReturnPojo();
                        int pInstNo = Integer.parseInt(prVector.get(0).toString());
                        BigDecimal pInstAmt = new BigDecimal(prVector.get(1).toString());

                        String pInstDt = prVector.get(2).toString();
                        int rInstNo = Integer.parseInt(prVector.get(3).toString());
                        BigDecimal rInstAmt = new BigDecimal(prVector.get(4).toString());
                        String rInstDt = prVector.get(5).toString();
                        if (repBased.equalsIgnoreCase("Outward Date Wise") && repType.equalsIgnoreCase("Outward Clearing Cheques")) {
                            if (pInstDt.equalsIgnoreCase("")) {
                                pojo.setDate(dmy.format(y_m_dFormat.parse(rInstDt)));
                            } else {
                                pojo.setDate(dmy.format(y_m_dFormat.parse(pInstDt)));
                            }
                        } else {
                            if (pInstDt.equalsIgnoreCase("")) {
                                pojo.setDate(rInstDt);
                            } else {
                                pojo.setDate(pInstDt);
                            }
                        }
                        pojo.setpNo_Chqs(pInstNo+rInstNo);
                        pojo.setpAmount(pInstAmt.add(rInstAmt));
                        pojo.setRetd_Chqs(rInstNo);
                        pojo.setrAmount(rInstAmt);
                        pojo.setChqs_Retd(((double) rInstNo / (double) pojo.getpNo_Chqs()) * 100);
                        pojo.setAmt_Retd((rInstAmt.doubleValue() / pojo.getpAmount().doubleValue()) * 100);
                        dataList.add(pojo);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    // Added by Manish Kumar
    public List getBranchList() throws ApplicationException {
        List branchList = new ArrayList();
        try {
            branchList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode, BranchName from branchmaster where BrnCode <> '90' order by BranchName ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return branchList;
    }
    //---
    

    public List<CtsinwardinfoPojo> getctsinwardinfoData(String branchCode, String toDate) throws ApplicationException {
        List<CtsinwardinfoPojo> dataList = new ArrayList<CtsinwardinfoPojo>();
        try {
            String table;
            String currentDate = ymdFormat.format(new Date());

            if (currentDate.compareTo(toDate) == 0) {
                table = "cts_upload_txt_cell";
            } else {
                table = "cts_upload_txt_cell_history";
            }

            List result = new ArrayList();
            if (branchCode.equalsIgnoreCase("A")) {
                result = em.createNativeQuery("select a.pbankcode,a.dbankcode,date_format(a.clgdt,'%d%m%Y'),a.chqno,a.amount,a.rbirefno,a.tc from " + table + " a where a.dt =" + toDate + " ").getResultList();
            } else {
                result = em.createNativeQuery("select a.pbankcode,a.dbankcode,date_format(a.clgdt,'%d%m%Y'),a.chqno,a.amount,a.rbirefno,a.tc from " + table + " a where a.dt= " + toDate + " and a.branchcode = " + Integer.parseInt(branchCode) + " ").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    CtsinwardinfoPojo pojo = new CtsinwardinfoPojo();
                    pojo.setsrNo(i + 1);
                    pojo.setpresentingBankCode(vtr.get(0).toString());
                    pojo.setdraweeBankCode(vtr.get(1).toString());
                    pojo.setclearingDate(vtr.get(2).toString());
                    pojo.setchqNo(vtr.get(3).toString());
                    pojo.setamount(Double.parseDouble(vtr.get(4).toString()));
                    pojo.setsequenceNo(vtr.get(5).toString());
                    pojo.settC(vtr.get(6).toString());

                    dataList.add(pojo);
                }
            }


        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }
}
