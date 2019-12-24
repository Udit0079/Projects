/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.investment;

import com.cbs.dao.ho.investment.InvestmentReportMgmtDAO;
import com.cbs.dto.report.InvestmentMutualFundPojo;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.GoiReportPojo;
import com.cbs.pojo.BucketWiseInvestmentPojo;
import com.cbs.pojo.CallMoneydetailPojo;
import com.cbs.pojo.DailyCallMoneyPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "InvestmentReportMgmtFacade")
public class InvestmentReportMgmtFacade implements InvestmentReportMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager entityManager;
    InvestmentReportMgmtDAO dao = null;
    NumberFormat formatter = new DecimalFormat("#0.00");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sql = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     *
     * @param reportType
     * @param fromDt
     * @param toDt
     * @return
     * @throws ApplicationException
     */
    public List<InvestmentMaster> getDueReportBetweenRange(String reportType, Date fromDt, Date toDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getDueReportBetweenRange(reportType, fromDt, toDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getDueReportTillDate(String reportType, Date tilldate) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getDueReportTillDate(reportType, tilldate));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getSellarName() throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getSellarName());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getAllReportTypeAllSellarName(Date frDt, Date asOnDt, String sType, String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getAllReportTypeAllSellarName(frDt, asOnDt, sType, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getAllReportTypeIndivisualSellarName(String sellar, Date frDt, Date asOnDt, String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getAllReportTypeIndivisualSellarName(sellar, frDt, asOnDt, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getIndivisualReportTypeAllSellarName(String repType, Date asOnDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getIndivisualReportTypeAllSellarName(repType, asOnDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getIndivisualReportTypeAllSellarNameGoi(String repType, Date frDt, Date asOnDt, String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getIndivisualReportTypeAllSellarNameGoi(repType, frDt, asOnDt, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getIndivisualReportTypeAllSellarNameAndMArking(String repType, String marking, Date asOnDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getIndivisualReportTypeAllSellarNameAndMArking(repType, marking, asOnDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    @Override
    public List<GoiReportPojo> getIndivisualReportTypeAllSellarNameAndMArking1(String repType, String marking, String asOnDt) throws ApplicationException {
        List<GoiReportPojo> list = new ArrayList();

        List distCtrlLst = entityManager.createNativeQuery("select CONTROLNO from investment_master where sectype='" + repType + "' and settledt<='" + asOnDt + "' "
                + "and matdt>='" + asOnDt + "' and (closedt is null or closedt>'" + asOnDt + "')").getResultList();
        if (!distCtrlLst.isEmpty()) {
            for (int c = 0; c < distCtrlLst.size(); c++) {

                Vector vec = (Vector) distCtrlLst.get(c);
                int cNo = Integer.parseInt(vec.get(0).toString());
//              List maxDtLst = entityManager.createNativeQuery("select ifnull(date_format(max(updatedate),'%Y%m%d'),'19000101'),ifnull(max(txnid),0) "
//                      + " from investment_master_his where controlno = "+ cNo +" and date_format(updatedate,'%Y%m%d') <='"+ asOnDt +"'").getResultList();
                List maxDtLst = entityManager.createNativeQuery("select a.adt,ifnull(b.bno,0) from (select ifnull(date_format(max(updatedate),'%Y%m%d'),'19000101') "
                        + " as adt from investment_master_his where controlno = " + cNo + " and date_format(updatedate,'%Y%m%d') <='" + asOnDt + "') a left join "
                        + " (select ifnull(date_format(max(updatedate),'%Y%m%d'),'19000101') as bdt,ifnull(max(txnid),0) as bno from investment_master_his "
                        + " where controlno = " + cNo + " and date_format(updatedate,'%Y%m%d') <='" + asOnDt + "' group by date_format(updatedate,'%Y%m%d')) b "
                        + " on a.adt  = b.bdt").getResultList();
                Vector vec1 = (Vector) maxDtLst.get(0);
                String mDt = vec1.get(0).toString();
                int txnId = Integer.parseInt(vec1.get(1).toString());
                List l;
                if (mDt.equalsIgnoreCase("19000101")) {
                    List minDtLst = entityManager.createNativeQuery("select ifnull(date_format(min(updatedate),'%Y%m%d'),'19000101'),ifnull(max(txnid),0) "
                            + " from investment_master_his where controlno = " + cNo + " and date_format(updatedate,'%Y%m%d') >='" + asOnDt + "'").getResultList();
                    Vector vecMin = (Vector) minDtLst.get(0);
                    String minDt = vecMin.get(0).toString();
                    int txnId1 = Integer.parseInt(vecMin.get(1).toString());
                    if (minDt.equalsIgnoreCase("19000101")) {
                        l = entityManager.createNativeQuery("select m.CONTROLNO,m.SECTYPE,m.SECDESC,m.INVSTCODE,m.TRANSNO,m.ROI,m.TRANSDT,"
                                + " Date_format(m.SETTLEDT,'%d/%m/%Y'),m.PD_DAY,m.PD_MON,m.PD_YEAR,Date_format(m.MATDT,'%d/%m/%Y'),m.SELLERNAME,"
                                + " m.BUYERACNO,m.BROKERNAME,m.BROKERACNO,m.FACEVALUE,m.BOOKVALUE,m.INTOPT,m.BROKERAGE,m.ACCDINT,m.ENTERBY,m.AUTH,m.AUTHBY,"
                                + " m.DT,m.LASTUPDATEBY,m.LASTUPDATEDT,m.CLOSEDT,m.NOOFSHARES,ifnull(m.PRICEGSEC,0.0),m.CLOSEPRICEGSEC,m.CONTPERSONNM,"
                                + " m.CONTPERSPHONE,m.VOUCHNO,m.ytm,m.marking from investment_master m where m.marking = '" + marking + "' and "
                                + " m.sectype='" + repType + "' and m.settledt<='" + asOnDt + "' and m.matdt>='" + asOnDt + "' "
                                + " and (m.closedt is null or m.closedt>'" + asOnDt + "') and m.CONTROLNO = '" + cNo + "'").getResultList();
                    } else {
                        l = entityManager.createNativeQuery("select e.CONTROLNO,e.SECTYPE,e.SECDESC,e.INVSTCODE,e.TRANSNO,e.ROI,e.TRANSDT,"
                                + " Date_format(e.SETTLEDT,'%d/%m/%Y'),e.PD_DAY,e.PD_MON,e.PD_YEAR,Date_format(e.MATDT,'%d/%m/%Y'),e.SELLERNAME,"
                                + " e.BUYERACNO,e.BROKERNAME, e.BROKERACNO,e.FACEVALUE,e.BOOKVALUE,e.INTOPT,e.BROKERAGE,e.ACCDINT,e.ENTERBY,e.AUTH,e.AUTHBY,"
                                + " e.DT,e.LASTUPDATEBY,e.LASTUPDATEDT,e.CLOSEDT,e.NOOFSHARES,ifnull(e.PRICEGSEC,0.0), e.CLOSEPRICEGSEC,e.CONTPERSONNM,"
                                + " e.CONTPERSPHONE,e.VOUCHNO,e.ytm,f.previousMarking from investment_master e,investment_master_his f "
                                + " where date_format(f.updateDate,'%Y%m%d')='" + minDt + "' and txnid = '" + txnId1 + "' and e.sectype='" + repType + "' and e.settledt<='" + asOnDt + "' and e.matdt>='" + asOnDt + "' "
                                + " and (e.closedt is null or e.closedt>'" + asOnDt + "') and f.previousMarking='" + marking + "' "
                                + " and e.CONTROLNO = '" + cNo + "' and e.controlno = f.controlno group by e.controlno ").getResultList();
                    }
                } else {
                    l = entityManager.createNativeQuery("select e.CONTROLNO,e.SECTYPE,e.SECDESC,e.INVSTCODE,e.TRANSNO,e.ROI,e.TRANSDT,"
                            + " Date_format(e.SETTLEDT,'%d/%m/%Y'),e.PD_DAY,e.PD_MON,e.PD_YEAR,Date_format(e.MATDT,'%d/%m/%Y'),e.SELLERNAME,"
                            + " e.BUYERACNO,e.BROKERNAME, e.BROKERACNO,e.FACEVALUE,e.BOOKVALUE,e.INTOPT,e.BROKERAGE,e.ACCDINT,e.ENTERBY,e.AUTH,e.AUTHBY,"
                            + " e.DT,e.LASTUPDATEBY,e.LASTUPDATEDT,e.CLOSEDT,e.NOOFSHARES,ifnull(e.PRICEGSEC,0.0), e.CLOSEPRICEGSEC,e.CONTPERSONNM,"
                            + " e.CONTPERSPHONE,e.VOUCHNO,e.ytm,f.currentMarking from investment_master e,investment_master_his f "
                            + " where date_format(f.updateDate,'%Y%m%d')='" + mDt + "' and txnid = '" + txnId + "' and e.sectype='" + repType + "' and e.settledt<='" + asOnDt + "' and e.matdt>='" + asOnDt + "' "
                            + " and (e.closedt is null or e.closedt>'" + asOnDt + "') and f.currentMarking='" + marking + "' "
                            + " and e.CONTROLNO = '" + cNo + "' and e.controlno = f.controlno group by e.controlno ").getResultList();
                }
                if (!l.isEmpty()) {
                    for (int i = 0; i < l.size(); i++) {
                        GoiReportPojo grp = new GoiReportPojo();
                        Vector vec2 = (Vector) l.get(i);
                        grp.setCtrlNo(Integer.parseInt(vec2.get(0).toString()));
                        grp.setSecDtl(vec2.get(2).toString());
                        grp.setSellarName(vec2.get(12).toString());
                        grp.setSettleDt(vec2.get(7).toString());
                        grp.setMatDt(vec2.get(11).toString());
                        grp.setFaceValue(Double.valueOf(vec2.get(16).toString()));
                        grp.setBookvalue(Double.valueOf(vec2.get(17).toString()));
                        grp.setYtm(Double.valueOf(vec2.get(34).toString()));
                        grp.setMarking(vec2.get(35).toString());
                        grp.setRate(Double.valueOf(vec2.get(29).toString()));
                        list.add(grp);
                    }
                }
            }
        }
//       List l = entityManager.createNativeQuery("select m.CONTROLNO,m.SECTYPE,m.SECDESC,m.INVSTCODE,m.TRANSNO,m.ROI,m.TRANSDT,Date_format(m.SETTLEDT,'%d/%m/%Y'),m.PD_DAY,m.PD_MON,m.PD_YEAR,Date_format(m.MATDT,'%d/%m/%Y'),m.SELLERNAME,m.BUYERACNO,m.BROKERNAME," +
//       "m.BROKERACNO,m.FACEVALUE,m.BOOKVALUE,m.INTOPT,m.BROKERAGE,m.ACCDINT,m.ENTERBY,m.AUTH,m.AUTHBY,m.DT,m.LASTUPDATEBY,m.LASTUPDATEDT,m.CLOSEDT,m.NOOFSHARES,ifnull(m.PRICEGSEC,0.0)," +
//       "m.CLOSEPRICEGSEC,m.CONTPERSONNM,m.CONTPERSPHONE,m.VOUCHNO,m.ytm,m.marking from investment_master m where m.marking = '"+marking+"'  and   m.sectype='"+repType+"' and m.settledt<='"+asOnDt+"' and m.matdt>='"+asOnDt+"' and (m.closedt is null or m.closedt>'"+asOnDt+"')" +
//       "union " +
//       "select e.CONTROLNO,e.SECTYPE,e.SECDESC,e.INVSTCODE,e.TRANSNO,e.ROI,e.TRANSDT,Date_format(e.SETTLEDT,'%d/%m/%Y'),e.PD_DAY,e.PD_MON,e.PD_YEAR,Date_format(e.MATDT,'%d/%m/%Y'),e.SELLERNAME,e.BUYERACNO,e.BROKERNAME," +
//       "e.BROKERACNO,e.FACEVALUE,e.BOOKVALUE,e.INTOPT,e.BROKERAGE,e.ACCDINT,e.ENTERBY,e.AUTH,e.AUTHBY,e.DT,e.LASTUPDATEBY,e.LASTUPDATEDT,e.CLOSEDT,e.NOOFSHARES,ifnull(e.PRICEGSEC,0.0)," +
//       "e.CLOSEPRICEGSEC,e.CONTPERSONNM,e.CONTPERSPHONE,e.VOUCHNO,e.ytm,f.previousMarking from investment_master e ,investment_master_his f where f.updateDate>='"+asOnDt+"' and   e.sectype='"+repType+"' and e.settledt<='"+asOnDt+"'" +
//       "and e.matdt>='"+asOnDt+"' and (e.closedt is null or e.closedt>'"+asOnDt+"') and f.previousMarking='"+marking+"' and e.marking=f.currentMarking " +
//       "and e.lastupdatedt >='"+asOnDt+"' group by e.controlno ").getResultList();

        return list;
    }

    public List<InvestmentMaster> getIndivisualReportTypeReportTypeIndivisualSellarName(String repType, String sellar, Date frDt, Date asOnDt, String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getIndivisualReportTypeReportTypeIndivisualSellarName(repType, sellar, frDt, asOnDt, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getSName(String repType) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getSName(repType));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getAllInvestmentMasterSecurityMaster(String repType, Date maxTillDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getAllInvestmentMasterSecurityMaster(repType, maxTillDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getInvestmentMasterSecurityMaster(String repType, String sellerName, Date frDt, Date maxTillDt, String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getInvestmentMasterSecurityMaster(repType, sellerName, frDt, maxTillDt, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getAllInvestmentMasterSecurityMasterGoiInt(String repType, Date frDt, Date maxTillDt, String status) throws ApplicationException {

        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getAllInvestmentMasterSecurityMasterGoiInt(repType, frDt, maxTillDt, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDatesAndDetailsForInterestRecreport() throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDatesAndDetailsForInterestRecreport());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportActive(Date frDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDueDateReportActive(frDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportActive1(Date frDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDueDateReportActive1(frDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportRenewed() throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDueDateReportRenewed());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportClose(Date frDt, Date toDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDueDateReportClose(frDt, toDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportActiveSellarName(String sellerName) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDueDateReportActiveSellarName(sellerName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getAllDueReportBetweenRange(String reportType, Date fromDt, Date toDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getAllDueReportBetweenRange(reportType, fromDt, toDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getAllDueReportTillDate(String reportType, Date tilldate) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getAllDueReportTillDate(reportType, tilldate));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getInvestMastSecAll(String sellerName, Date frDt, Date maxTillDt, String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getInvestMastSecAll(sellerName, frDt, maxTillDt, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getAllInvestMastSec(Date frDt, Date maxTillDt, String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getAllInvestMastSec(frDt, maxTillDt, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getAllInvestmentMasterSecurityMasterAccrPost(String repType, Date maxTillDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getAllInvestmentMasterSecurityMasterAccrPost(repType, maxTillDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<BucketWiseInvestmentPojo> getBucketWiseInvestmentdata(String report, String toDt) throws ApplicationException {
        List<BucketWiseInvestmentPojo> dataList = new ArrayList<BucketWiseInvestmentPojo>();

        try {
            List result = entityManager.createNativeQuery("select bucket_no,bucket_desc,bucket_start_day,bucket_end_day from cbs_alm_bucket_master where profile_parameter = 'l'").getResultList();
            if (!result.isEmpty()) {
                BucketWiseInvestmentPojo pojo = new BucketWiseInvestmentPojo();
                String dt1, dt2 = null, dt3;
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    String bktNo = vtr.get(0).toString();
                    String bktDesc = vtr.get(1).toString();
                    Integer bktStartDay = Integer.parseInt(vtr.get(2).toString());
                    Integer bktEndDay = Integer.parseInt(vtr.get(3).toString());
                    if (bktNo.equalsIgnoreCase("1")) {
                        //dt1 = CbsUtil.dateAdd(toDt, bktStartDay);
                        dt1 = toDt;
                        dt2 = CbsUtil.dateAdd(dt1, (bktEndDay - bktStartDay));
                    } else {
                        dt3 = CbsUtil.dateAdd(dt2, 1);
                        dt1 = dt3;
                        dt2 = CbsUtil.dateAdd(dt3, (bktEndDay - bktStartDay));
                    }

                    List result1 = new ArrayList();
                    if (report.equalsIgnoreCase("F")) {
                        result1 = entityManager.createNativeQuery("select ifnull(sum(a.tot_amt_int_rec),0),ifnull(sum(b.face_value),0) from investment_fdr_dates a,"
                                + "investment_fdr_details b where a.mat_dt between '" + dt1 + "' and '" + dt2 + "' AND (a.CLOSED_DT is null or a.CLOSED_DT> '" + toDt + "') and a.ctrl_no = b.ctrl_no").getResultList();
                        // result1 = em.createNativeQuery("select ifnull(sum(amt_int_trec),0) from investment_fdr_dates where mat_dt between '" + dt1 + "' and '" + dt2 + "'").getResultList();
                    } else {
                        result1 = entityManager.createNativeQuery("select ifnull(sum(a.tot_amt_int_accr),0),ifnull(sum(b.facevalue),0) from investment_goidates a,investment_master b where a.matdt between '" + dt1 + "' and '" + dt2 + "' and a.ctrlno = b.controlno").getResultList();
                        // result1 = em.createNativeQuery("select ifnull(sum(tot_amt_int_accr),0) from investment_goidates where matdt between '" + dt1 + "' and '" + dt2 + "'").getResultList();
                    }

                    if (!result1.isEmpty()) {
                        Vector ele = (Vector) result1.get(0);
                        BigDecimal fdAmt = new BigDecimal(ele.get(1).toString());
                        BigDecimal intAmt = new BigDecimal(ele.get(0).toString());
                        if (bktNo.equalsIgnoreCase("1")) {
                            pojo.setBkt1FdAmt(fdAmt);
                            pojo.setBkt1IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("2")) {
                            pojo.setBkt2FdAmt(fdAmt);
                            pojo.setBkt2IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("3")) {
                            pojo.setBkt3FdAmt(fdAmt);
                            pojo.setBkt3IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("4")) {
                            pojo.setBkt4FdAmt(fdAmt);
                            pojo.setBkt4IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("5")) {
                            pojo.setBkt5FdAmt(fdAmt);
                            pojo.setBkt5IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("6")) {
                            pojo.setBkt6FdAmt(fdAmt);
                            pojo.setBkt6IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("7")) {
                            pojo.setBkt7FdAmt(fdAmt);
                            pojo.setBkt7IntAmt(intAmt);
                        } else if (bktNo.equalsIgnoreCase("8")) {
                            pojo.setBkt8FdAmt(fdAmt);
                            pojo.setBkt8IntAmt(intAmt);
                        }
                    }
                    pojo.setIntType("Int Accured Amount");
                    if (report.equalsIgnoreCase("F")) {
                        pojo.setRepType("FD");
                    } else {
                        pojo.setRepType("Security");
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<CallMoneydetailPojo> getCallMoneydata(String fromDt, String toDt) throws ApplicationException {
        List<CallMoneydetailPojo> dataList = new ArrayList<CallMoneydetailPojo>();
        try {
            List result = entityManager.createNativeQuery("select ctrl_no,date_format(deal_dt,'%d/%m/%y'),no_of_days,date_format(completion_dt,'%d/%m/%y'),"
                    + "roi,int_amt,(amt+int_amt) matamt,amt BookValue from investment_call_master "
                    + "where deal_dt between '" + fromDt + "' and '" + toDt + "'").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    CallMoneydetailPojo pojo = new CallMoneydetailPojo();
                    pojo.setCtrNo(vtr.get(0).toString());
                    pojo.setDate(vtr.get(1).toString());
                    pojo.setPeriod(vtr.get(2).toString());
                    pojo.setDateOfMat(vtr.get(3).toString());
                    pojo.setRoi(Double.parseDouble(vtr.get(4).toString()));
                    pojo.setInttAmt(new BigDecimal(vtr.get(5).toString()));
                    pojo.setMatAmt(new BigDecimal(vtr.get(6).toString()));
                    pojo.setBookVal(new BigDecimal(vtr.get(7).toString()));
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<String> getBankName() throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (dao.getBankName());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDatesAndDetailsForInterestRecreportBankWise(String bnkName, String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDatesAndDetailsForInterestRecreportBankWise(bnkName, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDatesAndDetailsForInterestRecreportAll(String status) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDatesAndDetailsForInterestRecreportAll(status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDatesAndDetailsForInterestRecreportBankAll(String bnkName) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDatesAndDetailsForInterestRecreportBankAll(bnkName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<DailyCallMoneyPojo> getDailyCallMoneydata(String firstAltDt, String secondAltDt, String reportIns) throws ApplicationException {
        List<DailyCallMoneyPojo> dataList = new ArrayList<DailyCallMoneyPojo>();
        double borrowedAmt = 0d, bRangeIntRateMin = 0d, bRangeIntRateMax = 0d;
        double lentAmt = 0d, totalLentAmt = 0d, lRangeIntRateMin = 0d, totalLRangeIntRateMin = 0d, lRangeIntRateMax = 0d;
        double b1 = 0d, b2 = 0d, b3 = 0d, b4 = 0d;
        List<Double> maxMin = new ArrayList<Double>();
        try {

            List result = entityManager.createNativeQuery("select date_format(deal_dt,'%d/%m/%y'),cast(amt as decimal(25,2)),roi BookValue from investment_call_master where deal_dt between '" + firstAltDt + "' and '" + secondAltDt + "' order by deal_dt").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    int resultsize = result.size();
                    Vector vtr = (Vector) result.get(i);
                    DailyCallMoneyPojo pojo = new DailyCallMoneyPojo();
                    pojo.setDate(vtr.get(0).toString());
                    lentAmt = Double.parseDouble(vtr.get(1).toString());
                    lRangeIntRateMin = Double.parseDouble(vtr.get(2).toString());
                    lRangeIntRateMax = Double.parseDouble(vtr.get(2).toString());
                    maxMin.add(lRangeIntRateMin);
                    if (reportIns.equalsIgnoreCase("R")) {
                    } else if (reportIns.equalsIgnoreCase("T")) {
                        lentAmt = lentAmt / 1000;
                    } else if (reportIns.equalsIgnoreCase("L")) {
                        lentAmt = lentAmt / 100000;
                    } else if (reportIns.equalsIgnoreCase("C")) {
                        lentAmt = lentAmt / 10000000;
                    }
                    pojo.setBorrowedAmt(borrowedAmt);
                    pojo.setbRangeIntRateMin(bRangeIntRateMin);
                    pojo.setbRangeIntRateMax(bRangeIntRateMax);
                    pojo.setLentAmt(lentAmt);
                    pojo.setLentRangeIntRateMin(lRangeIntRateMin);
                    pojo.setLentRangeIntRateMax(lRangeIntRateMax);
                    totalLentAmt = totalLentAmt + lentAmt;
                    totalLRangeIntRateMin = totalLRangeIntRateMin + lRangeIntRateMin;
                    dataList.add(pojo);
                }

//                DailyCallMoneyPojo pojo1 = new DailyCallMoneyPojo();
//                Collections.sort(maxMin);
//                double min = maxMin.get(0);
//                pojo1.setLentMinRate(min);
//                Collections.reverse(maxMin);
//                double max = maxMin.get(0);
//
//                pojo1.setLentMaxRate(max);
//                pojo1.setB1(b1);
//                pojo1.setB1(b2);
//                pojo1.setB1(b3);
//                pojo1.setB1(b4);
//                pojo1.setLentAvRate((min + max) / 2);
//                pojo1.setLentDaily(totalLentAmt / 14);
//                dataList.add(pojo1);

            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<DailyCallMoneyPojo> getDailyCallMoneyAvgData(String firstAltDt, String secondAltDt, String reportIns) throws ApplicationException {
        List<DailyCallMoneyPojo> dataList = new ArrayList<DailyCallMoneyPojo>();

        double lentAmt = 0d, totalLentAmt = 0d, lRangeIntRateMin = 0d, totalLRangeIntRateMin = 0d, lRangeIntRateMax = 0d;
        double b1 = 0d, b2 = 0d, b3 = 0d, b4 = 0d;
        List<Double> maxMin = new ArrayList<Double>();
        try {

            List result = entityManager.createNativeQuery("select date_format(deal_dt,'%d/%m/%y'),cast(amt as decimal(25,2)),roi BookValue from investment_call_master where deal_dt between '" + firstAltDt + "' and '" + secondAltDt + "' order by deal_dt").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    DailyCallMoneyPojo pojo = new DailyCallMoneyPojo();
                    pojo.setDate(vtr.get(0).toString());
                    lentAmt = Double.parseDouble(vtr.get(1).toString());
                    lRangeIntRateMin = Double.parseDouble(vtr.get(2).toString());
                    lRangeIntRateMax = Double.parseDouble(vtr.get(2).toString());
                    maxMin.add(lRangeIntRateMin);
                    if (reportIns.equalsIgnoreCase("R")) {
                    } else if (reportIns.equalsIgnoreCase("T")) {
                        lentAmt = lentAmt / 1000;
                    } else if (reportIns.equalsIgnoreCase("L")) {
                        lentAmt = lentAmt / 100000;
                    } else if (reportIns.equalsIgnoreCase("C")) {
                        lentAmt = lentAmt / 10000000;
                    }
                    totalLentAmt = totalLentAmt + lentAmt;
                    totalLRangeIntRateMin = totalLRangeIntRateMin + lRangeIntRateMin;
                }

                DailyCallMoneyPojo pojo1 = new DailyCallMoneyPojo();
                pojo1.setB1(b1);
                pojo1.setB1(b2);
                pojo1.setB1(b3);
                pojo1.setB1(b4);

                Collections.sort(maxMin);
                double min = maxMin.get(0);
                pojo1.setLentMinRate(min);
                Collections.reverse(maxMin);
                double max = maxMin.get(0);
                pojo1.setLentMaxRate(max);
                pojo1.setLentAvRate((min + max) / 2);
                pojo1.setLentDaily(Double.parseDouble(formatter.format(totalLentAmt / 14)));
                dataList.add(pojo1);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public double getAmortAmt(Integer ctrlNo, String tillDt) throws ApplicationException {
        double amortAmt = 0;
        try {
            List amortList = entityManager.createNativeQuery("select ifnull(sum(AMORT_AMT),0) from investment_amortization_details where CTRL_NO = " + ctrlNo + " and status = 'p' and date(LAST_UPDATE_DT) <= '" + tillDt + "'").getResultList();
            if (!amortList.isEmpty()) {
                Vector ele = (Vector) amortList.get(0);
                amortAmt = Double.parseDouble(ele.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return amortAmt;
    }

    public double getAmortAmtBetweenDate(Integer ctrlNo, String fromDt, String tillDt) throws ApplicationException {
        double amortAmt = 0;
        try {
            List amortList = entityManager.createNativeQuery("select ifnull(sum(AMORT_AMT),0) from investment_amortization_details where CTRL_NO = " + ctrlNo + " and status = 'p' and LAST_UPDATE_DT between '" + fromDt + "'  and '" + tillDt + "'").getResultList();
            if (!amortList.isEmpty()) {
                Vector ele = (Vector) amortList.get(0);
                amortAmt = Double.parseDouble(ele.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return amortAmt;
    }

    public String getMarkByControlNo(Integer ctrlNo, String secType, String tillDt) throws ApplicationException {
        List list = new ArrayList();
        String marked = "";
        try {
            list = entityManager.createNativeQuery("select currentMarking from investment_master_his where CONTROLNO = " + ctrlNo + " and SECTYPE = '" + secType + "' "
                    + "and date_format(updateDate,'%Y%m%d') =(select date_format(max(updateDate),'%Y%m%d') from investment_master_his "
                    + "where CONTROLNO = " + ctrlNo + " and SECTYPE = '" + secType + "' and date_format(updateDate,'%Y%m%d') <= '" + tillDt + "')").getResultList();
            if (list.isEmpty()) {
                list = entityManager.createNativeQuery("select previousMarking from investment_master_his where CONTROLNO = " + ctrlNo + " and SECTYPE = '" + secType + "' "
                        + "and txnId = (select min(txnId) from investment_master_his where CONTROLNO = " + ctrlNo + " and SECTYPE = '" + secType + "')").getResultList();
            }
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                marked = vtr.get(0).toString();
            }
            return marked;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getBankingType() throws ApplicationException {
        try {
            List banklist = entityManager.createNativeQuery("select distinct buy_dr_ac_no,acname from investment_mutual_fund_detail a,gltable b where a.buy_dr_ac_no = b.acno").getResultList();
            return banklist;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List<InvestmentMutualFundPojo> getInvestmentFundData(String reportStaus, String bankType, String frDate, String toDate) throws ApplicationException {
        List<InvestmentMutualFundPojo> listData = new ArrayList<InvestmentMutualFundPojo>();
        try {
            List list = new ArrayList();

            String dateFilter = "";
            String status = "";
            if (reportStaus.equalsIgnoreCase("ALL")) {
                dateFilter = "and  date_format(c.GENERATE_DATE,'%Y%m%d') between '" + frDate + "' and '" + toDate + "'order by c.CONTROL_NO";
                status = "";
            } else if (reportStaus.equalsIgnoreCase("A")) {
                dateFilter = "and  date_format(c.GENERATE_DATE,'%Y%m%d') between '" + frDate + "' and '" + toDate + "'    order by c.CONTROL_NO";
                status = "and c.STATUS in ('A','P','L')";
            } else {
                dateFilter = "and  date_format(c.GENERATE_DATE,'%Y%m%d') between '" + frDate + "' and '" + toDate + "'    order by c.CONTROL_NO";
                status = "and c.STATUS in ('R','P','L')";
            }

            if (reportStaus.equalsIgnoreCase("ALL")) {
                if (bankType.equalsIgnoreCase("ALL")) {
                    list = entityManager.createNativeQuery("  select c.CONTROL_NO,c.BUY_DR_AC_NO,c.BUY_CR_AC_NO,c.REDEEM_DR_AC_NO, c.REDEEM_CR_AC_NO,c.DAYS,c.AMOUNT,c.PROFIT_AMT,\n"
                            + " date_format(c.GENERATE_DATE,'%d/%m/%Y'),ifnull(date_format(c.GENERATE_DATE,'%d/%m/%Y') ,'') as RedeemDate ,b.AcName, \n"
                            + "  c.TOTAL_REDEEMED_PARTIAL_AMT as redeemAMT,c.TOTAL_REMAINING_AMT as balanceAmt ,c.REMARK from investment_mutual_fund_detail a,\n"
                            + " gltable b,investment_mutual_auth_detail c  where a.BUY_DR_AC_NO = c.BUY_DR_AC_NO and a.BUY_DR_AC_NO= b.acno and c.ENTRY_STATUS ='V' \n"
                            + " and a.CONTROL_NO=c.CONTROL_NO  " + status + "\n"
                            + "" + dateFilter + " ").getResultList();
                } else {
                    list = entityManager.createNativeQuery("select c.CONTROL_NO,c.BUY_DR_AC_NO,c.BUY_CR_AC_NO,c.REDEEM_DR_AC_NO, c.REDEEM_CR_AC_NO,c.DAYS,c.AMOUNT,c.PROFIT_AMT,\n"
                            + " date_format(c.GENERATE_DATE,'%d/%m/%Y'),ifnull(date_format(c.GENERATE_DATE,'%d/%m/%Y') ,'') as RedeemDate ,b.AcName, \n"
                            + "  c.TOTAL_REDEEMED_PARTIAL_AMT as redeemAMT,c.TOTAL_REMAINING_AMT as balanceAmt ,c.REMARK from investment_mutual_fund_detail a,\n"
                            + " gltable b,investment_mutual_auth_detail c  where a.BUY_DR_AC_NO = c.BUY_DR_AC_NO and a.BUY_DR_AC_NO= b.acno and c.BUY_DR_AC_NO = '" + bankType + "'  and c.ENTRY_STATUS ='V' \n"
                            + " and a.CONTROL_NO=c.CONTROL_NO  " + status + "\n"
                            + "" + dateFilter + " ").getResultList();
                }
            } else if (reportStaus.equalsIgnoreCase("A")) {
                if (bankType.equalsIgnoreCase("ALL")) {
                    list = entityManager.createNativeQuery(" select c.CONTROL_NO,c.BUY_DR_AC_NO,c.BUY_CR_AC_NO,c.REDEEM_DR_AC_NO, c.REDEEM_CR_AC_NO,c.DAYS,c.AMOUNT,c.PROFIT_AMT,\n"
                            + " date_format(c.GENERATE_DATE,'%d/%m/%Y'),ifnull(date_format(c.GENERATE_DATE,'%d/%m/%Y') ,'') as RedeemDate ,b.AcName, \n"
                            + "  c.TOTAL_REDEEMED_PARTIAL_AMT as redeemAMT,c.TOTAL_REMAINING_AMT as balanceAmt ,c.REMARK from investment_mutual_fund_detail a,\n"
                            + " gltable b,investment_mutual_auth_detail c  where a.BUY_DR_AC_NO = c.BUY_DR_AC_NO and a.BUY_DR_AC_NO= b.acno and c.ENTRY_STATUS ='V' \n"
                            + " and a.CONTROL_NO=c.CONTROL_NO  " + status + "\n"
                            + "" + dateFilter + " ").getResultList();
                } else {
                    list = entityManager.createNativeQuery("select c.CONTROL_NO,c.BUY_DR_AC_NO,c.BUY_CR_AC_NO,c.REDEEM_DR_AC_NO, c.REDEEM_CR_AC_NO,c.DAYS,c.AMOUNT,c.PROFIT_AMT,\n"
                            + " date_format(c.GENERATE_DATE,'%d/%m/%Y'),ifnull(date_format(c.GENERATE_DATE,'%d/%m/%Y') ,'') as RedeemDate ,b.AcName, \n"
                            + "  c.TOTAL_REDEEMED_PARTIAL_AMT as redeemAMT,c.TOTAL_REMAINING_AMT as balanceAmt ,c.REMARK from investment_mutual_fund_detail a,\n"
                            + " gltable b,investment_mutual_auth_detail c  where a.BUY_DR_AC_NO = c.BUY_DR_AC_NO and a.BUY_DR_AC_NO= b.acno and c.BUY_DR_AC_NO = '" + bankType + "'  and c.ENTRY_STATUS ='V' \n"
                            + " and a.CONTROL_NO=c.CONTROL_NO  " + status + "\n"
                            + "" + dateFilter + "").getResultList();
                }
            } else if (reportStaus.equalsIgnoreCase("R")) {
                if (bankType.equalsIgnoreCase("ALL")) {
                    list = entityManager.createNativeQuery("   select c.CONTROL_NO,c.BUY_DR_AC_NO,c.BUY_CR_AC_NO,c.REDEEM_DR_AC_NO, c.REDEEM_CR_AC_NO,c.DAYS,c.AMOUNT,c.PROFIT_AMT,\n"
                            + " date_format(c.GENERATE_DATE,'%d/%m/%Y'),ifnull(date_format(c.GENERATE_DATE,'%d/%m/%Y') ,'') as RedeemDate ,b.AcName, \n"
                            + "  c.TOTAL_REDEEMED_PARTIAL_AMT as redeemAMT,c.TOTAL_REMAINING_AMT as balanceAmt ,c.REMARK from investment_mutual_fund_detail a,\n"
                            + " gltable b,investment_mutual_auth_detail c  where a.BUY_DR_AC_NO = c.BUY_DR_AC_NO and a.BUY_DR_AC_NO= b.acno and c.ENTRY_STATUS ='V' \n"
                            + " and a.CONTROL_NO=c.CONTROL_NO  " + status + "\n"
                            + "" + dateFilter + " ").getResultList();
                } else {
                    list = entityManager.createNativeQuery("select c.CONTROL_NO,c.BUY_DR_AC_NO,c.BUY_CR_AC_NO,c.REDEEM_DR_AC_NO, c.REDEEM_CR_AC_NO,c.DAYS,c.AMOUNT,c.PROFIT_AMT,\n"
                            + " date_format(c.GENERATE_DATE,'%d/%m/%Y'),ifnull(date_format(c.GENERATE_DATE,'%d/%m/%Y') ,'') as RedeemDate ,b.AcName, \n"
                            + "  c.TOTAL_REDEEMED_PARTIAL_AMT as redeemAMT,c.TOTAL_REMAINING_AMT as balanceAmt ,c.REMARK from investment_mutual_fund_detail a,\n"
                            + " gltable b,investment_mutual_auth_detail c  where a.BUY_DR_AC_NO = c.BUY_DR_AC_NO and a.BUY_DR_AC_NO= b.acno and c.BUY_DR_AC_NO = '" + bankType + "'  and c.ENTRY_STATUS ='V' \n"
                            + " and a.CONTROL_NO=c.CONTROL_NO  " + status + "\n"
                            + "" + dateFilter + "").getResultList();
                }
            }




//            if (reportStaus.equalsIgnoreCase("ALL")) {
//                if (bankType.equalsIgnoreCase("ALL")) {
//                    list = entityManager.createNativeQuery(" select c.CONTROL_NO,c.BUY_DR_AC_NO,c.BUY_CR_AC_NO,c.REDEEM_DR_AC_NO,\n" +
//                   "c.REDEEM_CR_AC_NO,c.DAYS,c.AMOUNT,c.PROFIT_AMT,date_format(c.GENERATE_DATE,'%d/%m/%Y'),ifnull(date_format(c.GENERATE_DATE,'%d/%m/%Y') ,'') as RedeemDate\n" +
//                   ",b.AcName, a.TOTAL_REDEEMED_PARTIAL_AMT as redeemAMT , a.TOTAL_REMAINING_AMT as balanceAmt \n" +
//                   "from investment_mutual_fund_detail a,gltable b,investment_mutual_auth_detail c where a.buy_dr_ac_no = b.acno  and c.BUY_DR_AC_NO = b.acno and c.STATUS ='"+status+"'  and  " + dateFilter + "").getResultList();
//                } else {
//                    list = entityManager.createNativeQuery("select control_no,buy_dr_ac_no,buy_cr_ac_no,redeem_dr_ac_no,"
//                            + "redeem_cr_ac_no,days,amount,profit_amt, date_format(generate_date,'%d/%m/%Y'),ifnull(date_format(REDEEM_DATE,'%d/%m/%Y'),''),b.AcName , a.PARTIAL_REDEEM_AMT as redeemAMT , a.TOTAL_REMAINING_AMT as balanceAmt  "
//                            + "from investment_mutual_fund_detail a,gltable b where buy_dr_ac_no = '" + bankType + "' and a.buy_dr_ac_no = b.acno "
//                            + "and " + dateFilter + "").getResultList();
//                }
//            } else {
//                if (bankType.equalsIgnoreCase("ALL")) {
//                    list = entityManager.createNativeQuery("select control_no,buy_dr_ac_no,buy_cr_ac_no,redeem_dr_ac_no,"
//                            + "redeem_cr_ac_no,days,amount,profit_amt,date_format(generate_date,'%d/%m/%Y'),ifnull(date_format(REDEEM_DATE,'%d/%m/%Y'),''),b.AcName , a.PARTIAL_REDEEM_AMT as redeemAMT , a.TOTAL_REMAINING_AMT as balanceAmt  "
//                            + "from investment_mutual_fund_detail a,gltable b where a.buy_dr_ac_no = b.acno "
//                            + "and " + dateFilter + "").getResultList();
//                } else {
//                    list = entityManager.createNativeQuery("select control_no,buy_dr_ac_no,buy_cr_ac_no,redeem_dr_ac_no,"
//                            + "redeem_cr_ac_no,days,amount,profit_amt, date_format(generate_date,'%d/%m/%Y'),ifnull(date_format(REDEEM_DATE,'%d/%m/%Y'),''),b.AcName ,a.PARTIAL_REDEEM_AMT as redeemAMT , a.TOTAL_REMAINING_AMT as balanceAmt  "
//                            + "from investment_mutual_fund_detail a,gltable b where buy_dr_ac_no = '" + bankType + "' and a.buy_dr_ac_no = b.acno "
//                            + "and " + dateFilter + "").getResultList();
//                }
//            }

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    int resultsize = list.size();
                    Vector vtr = (Vector) list.get(i);
                    InvestmentMutualFundPojo pojo = new InvestmentMutualFundPojo();
                    pojo.setControlNo(vtr.get(0).toString());
                    pojo.setDrAcNo(vtr.get(1).toString());
                    pojo.setCrAcNo(vtr.get(2).toString());
                    pojo.setRedeemDrAcNo(vtr.get(3).toString());
                    pojo.setRedeemCrAcNo(vtr.get(4).toString());
                    pojo.setDays(vtr.get(5).toString());
                    pojo.setAmount(new BigDecimal(vtr.get(6).toString()));
                    pojo.setProfitAmount(new BigDecimal(vtr.get(7).toString()).abs());
                    pojo.setGeneralDt(vtr.get(8).toString());
                    pojo.setRedeemDt(vtr.get(9).toString());
                    pojo.setBnkName(vtr.get(10).toString().trim());
                    pojo.setRedeemAmt(vtr.get(11).toString());
                    pojo.setBalanceAmt(vtr.get(12).toString());
                    pojo.setRemarks(vtr.get(13).toString());
                    listData.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return listData;
    }

    public List<Object[]> getDueDateReportObtained(Date frDt, Date toDt) throws ApplicationException {
        dao = new InvestmentReportMgmtDAO(entityManager);
        try {
            return (List<Object[]>) (dao.getDueDateReportObtained(frDt, toDt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public double getReceviableAmt(Integer ctrlNo, String tillDt) throws ApplicationException {
        double recvAmt = 0;
        try {
            List receAmtList = entityManager.createNativeQuery("select ifnull(sum(dr_amt - cr_amt),0) from fdr_recon where ctrl_no = '" + ctrlNo + "' "
                    + " and trantype = 8 and dt <='" + tillDt + "'").getResultList();
            if (!receAmtList.isEmpty()) {
                Vector ele = (Vector) receAmtList.get(0);
                recvAmt = Double.parseDouble(ele.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return recvAmt;
    }

    public double getReceGoiAmt(Integer ctrlNo, String tillDt) throws ApplicationException {
        double receAmt = 0;
        try {
            List amortList = entityManager.createNativeQuery("select ifnull(sum(cramt - dramt),0) from goi_recon where CTRLNO = " + ctrlNo + " "
                    + " and trantype = 8 and dt <= '" + tillDt + "'").getResultList();
            if (!amortList.isEmpty()) {
                Vector ele = (Vector) amortList.get(0);
                receAmt = Double.parseDouble(ele.get(0).toString());
                if (receAmt < 0) {
                    receAmt = 0;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return receAmt;
    }

    public List getRecivableDate(Integer ctrlNo) throws ApplicationException {
        try {
            List DateList = entityManager.createNativeQuery("select INTPDT1,INTPDT2 from investment_goidates where CTRLNO = '" + ctrlNo + "'").getResultList();
            return DateList;

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
