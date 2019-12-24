/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.TdIntDetail;
import com.cbs.dto.TempProjIntDetail;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.TdInterestSmsTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.pojo.CustIdWiseCloseAcIntTds;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
 * @author Sudhir
 */
@Stateless(mappedName = "TDInterestCalulationFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TDInterestCalulationFacade implements TDInterestCalulationFacadeRemote {

    @EJB
    private TdInterestCalFacadeRemote intCal;
    @EJB
    FtsPostingMgmtFacadeRemote ftsMethods;
    @EJB
    MessageDetailBeanRemote messageDetailBeanRemote;
    @EJB
    SmsManagementFacadeRemote smsRemote;
    @EJB
    private RDIntCalFacadeRemote rdIntCalFacade;
    @EJB
    DDSManagementFacadeRemote ddsFacade;
    @EJB
    TermDepositeCalculationManagementFacadeRemote tdCalcMgmtFacade;
    @EJB
    RbiReportFacadeRemote rbiReportFacade;
    @EJB
    TdReceiptManagementFacadeRemote tdRcptMgmtRemote;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List acctTypeCombo() throws ApplicationException {
        try {
            Query selectQuery = em.createNativeQuery("select Acctcode From accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')");
            return selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List setGLAcNoAndInterestOption(String acType) throws ApplicationException {
        List actype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select acctdesc, acctNature, GLHeadInt,Productcode,GLHeadProv From accounttypemaster Where acctcode='" + acType + "'");
            actype = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return actype;
    }

    public List<TdIntDetail> tdInterestCalculationCover(String brnCode, String tmpFDate, String tempDate, String acctType, String intOpt, String user,
            String tmpAcNo, String date, String brCode) throws ApplicationException {
        try {
            List<TdIntDetail> tdIntDetailListAll = new ArrayList<TdIntDetail>();
            double tdsApplicableAmt = 0d, taxWithPan = 0d, taxWithoutPan = 0d, tdsApplicableAmtForSrCtzen = 0d;
            double dblBenifitAge = ftsMethods.getCodeForReportName("SR-CITIZEN-AGE").doubleValue();
            List selectQuer65 = em.createNativeQuery("SELECT ifnull(TDS_AMOUNT,-1),ifnull(TDS_RATE *(1 + TDS_SURCHARGE /100.0),-1),tdsRate_pan, ifnull(Srctzn_Tds_Amount,0) FROM tdsslab "
                    + "WHERE TYPE=1 AND TDS_APPLICABLEDATE IN(SELECT MAX(TDS_APPLICABLEDATE) FROM tdsslab WHERE TDS_APPLICABLEDATE<='" + tempDate
                    + "' AND TYPE=1)").getResultList();
            if (!selectQuer65.isEmpty()) {
                Vector v65 = (Vector) selectQuer65.get(0);
                tdsApplicableAmt = Double.parseDouble(v65.get(0).toString());
                tdsApplicableAmtForSrCtzen = Double.parseDouble(v65.get(3).toString());
                if (Double.parseDouble(v65.get(1).toString()) == 0 || Double.parseDouble(v65.get(2).toString()) == 0) {
                    return setMessage(tdIntDetailListAll, "Tds applicable rate does not exist in tds slab. So please fill tds slab.");
                } else {
                    taxWithPan = Double.parseDouble(v65.get(1).toString());
                    taxWithoutPan = Double.parseDouble(v65.get(2).toString());
                }
            } else {
                return setMessage(tdIntDetailListAll, "Tds applicable amount does not exist in tds slab. So please fill tds slab.");
            }
            String tdsPostEnable = "";
            List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
            if (!tdsPostEnableList.isEmpty()) {
                Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                tdsPostEnable = tdsPostEnableVect.get(0).toString();
                if (tdsPostEnable.equalsIgnoreCase("Y")) {
                    return setMessage(tdIntDetailListAll, "Please OFF the parameter of TDS reserve");
                }
            }

            double tdsDiff = 0;
            List tdsDiffList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_DIFF_AMT_FOR_ACTUAL'  ").getResultList();
            if (!tdsDiffList.isEmpty()) {
                Vector tdsDiffVect = (Vector) tdsDiffList.get(0);
                tdsDiff = Double.parseDouble(tdsDiffVect.get(0).toString());
            }

            List brnList;
            if (brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90")) {
                brnList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode from branchmaster where BrnCode <> 99 and (closedate is null or closedate='' or closedate>'" + tempDate + "' ) order by BrnCode ").getResultList();
            } else {
                brnList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode from branchmaster where BrnCode ='" + brnCode + "' and (closedate is null or closedate='' or closedate>'" + tempDate + "') order by BrnCode ").getResultList();
            }

            for (int z = 0; z < brnList.size(); z++) {
                Vector brnVect = (Vector) brnList.get(z);
                brCode = brnVect.get(0).toString();

                List<TdIntDetail> tdIntDetailList = tdInterestCalculationWithTds(tmpFDate, tempDate, acctType, intOpt, user, "", date, brCode);
                if (tdIntDetailList.size() == 1 && !(tdIntDetailList.get(0).getMsg().equalsIgnoreCase("TRUE")
                        || tdIntDetailList.get(0).getMsg().equalsIgnoreCase("Transaction does not exists"))) {
                    return tdIntDetailList;
                } else {
                    if (tdIntDetailList.size() == 1) {
                        if (!(tdIntDetailList.get(0).getMsg().equalsIgnoreCase("Transaction does not exists"))) {
                            tdIntDetailListAll.addAll(tdIntDetailList);
                        }
                    } else {
                        tdIntDetailListAll.addAll(tdIntDetailList);
                    }
                }

                if ((tempDate.substring(4, 6).equalsIgnoreCase("03") || tempDate.substring(4, 6).equalsIgnoreCase("06")
                        || tempDate.substring(4, 6).equalsIgnoreCase("09") || tempDate.substring(4, 6).equalsIgnoreCase("12"))) {

                    Query selectRdQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.RECURRING_AC + "') and productcode is not null ");
                    List resultRdList = selectRdQuery.getResultList();
                    if (!resultRdList.isEmpty()) {
                        for (int i = 0; i < resultRdList.size(); i++) {
                            Vector v = (Vector) resultRdList.get(i);
                            List<TdIntDetail> rdIntListsubList = rdIntCalFacade.intCalcRDWithTds(v.get(0).toString(), CbsUtil.getQuarterFirstAndLastDate(tempDate.substring(4, 6), tempDate.substring(0, 4), "F"), tempDate, brCode);
                            if (!rdIntListsubList.isEmpty()) {
                                if (rdIntListsubList.size() == 1 && !rdIntListsubList.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                                    return rdIntListsubList;
                                } else {
                                    tdIntDetailListAll.addAll(rdIntListsubList);
                                }
                            }
                        }
                    }
                    List resultDsList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.DEPOSIT_SC + "') and ((productcode is not null) and  (productcode<>''))").getResultList();
                    if (!resultDsList.isEmpty()) {
                        for (int i = 0; i < resultDsList.size(); i++) {
                            String firstDt = CbsUtil.getQuarterFirstAndLastDate(tempDate.substring(4, 6), tempDate.substring(0, 4), "F");
                            List<TdIntDetail> ddsIntDetailList = ddsFacade.intCalcForTds(firstDt, tempDate, ((Vector) resultDsList.get(i)).get(0).toString(), brCode);
                            if (ddsIntDetailList.size() == 1 && !(ddsIntDetailList.get(0).getMsg().equalsIgnoreCase("TRUE")
                                    || ddsIntDetailList.get(0).getMsg().equalsIgnoreCase("Transaction does not exists"))) {
                                return ddsIntDetailList;
                            } else {
                                if (ddsIntDetailList.size() == 1) {
                                    if (!(ddsIntDetailList.get(0).getMsg().equalsIgnoreCase("Transaction does not exists"))) {
                                        tdIntDetailListAll.addAll(ddsIntDetailList);
                                    }
                                } else {
                                    tdIntDetailListAll.addAll(ddsIntDetailList);
                                }
                            }
                        }
                    }
                }
            }
            //            System.out.println("??????????????????Start:" + new Date());
            int l = 0;

            Collections.sort(tdIntDetailListAll, new CustIdByComparator());
            Collections.sort(tdIntDetailListAll, new MajorCustIdByComparator());
            /*Start Getting Current Interest */
            //Current Total Int in Calc of all custid in Major CustID
            Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                if (map.containsKey(tdIntDetail.getMajorCustId())) { //Present
                    map.put(tdIntDetail.getMajorCustId(), new BigDecimal(tdIntDetail.getInterest()).add(map.get(tdIntDetail.getMajorCustId())));
                } else { //Not Present
                    map.put(tdIntDetail.getMajorCustId(), new BigDecimal(tdIntDetail.getInterest()));
                }
            }
            //Individual Wise Current Int
            Map<String, BigDecimal> mapCurrCustIdCurrInt = new HashMap<String, BigDecimal>();
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                if (mapCurrCustIdCurrInt.containsKey(tdIntDetail.getCustId())) { //Present
                    mapCurrCustIdCurrInt.put(tdIntDetail.getCustId(), new BigDecimal(tdIntDetail.getInterest()).add(mapCurrCustIdCurrInt.get(tdIntDetail.getCustId())));
                } else { //Not Present
                    mapCurrCustIdCurrInt.put(tdIntDetail.getCustId(), new BigDecimal(tdIntDetail.getInterest()));
                }
            }
            /**
             * ******End Getting Current Interest*******
             */

            /*Start Getting Previous Interest Posted*/
            //Major with Minor Total Previous Interest Posted
            Map<String, BigDecimal> mapMajorCustIdWiseTotalIntPre = new HashMap<String, BigDecimal>();
//            BigDecimal totAmt = new BigDecimal(0);
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                if (!mapMajorCustIdWiseTotalIntPre.containsKey(tdIntDetail.getMajorCustId().concat(":").concat(tdIntDetail.getCustId()))) {
                    mapMajorCustIdWiseTotalIntPre.put(tdIntDetail.getMajorCustId().concat(":").concat(tdIntDetail.getCustId()), new BigDecimal(tdIntDetail.getTotalInt() <= 0.0 ? 0 : tdIntDetail.getTotalInt()));
                }
            }
            //Map for total Previous Int on Major CustId
            Map<String, BigDecimal> grpByMaj = new HashMap<>();
            Set<Entry<String, BigDecimal>> set = mapMajorCustIdWiseTotalIntPre.entrySet();
            Iterator<Entry<String, BigDecimal>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, BigDecimal> entry = it.next();
//                totAmt = totAmt.add(entry.getValue());
                String keyStr = entry.getKey().split(":")[0];
                if (grpByMaj.containsKey(keyStr)) {
                    grpByMaj.put(keyStr, entry.getValue().add(grpByMaj.get(keyStr)));
                } else {
                    grpByMaj.put(keyStr, entry.getValue());
                }
            }

            //Major with Minor Total Previous Interest Posted
            Map<String, BigDecimal> mapMajorCustIdWiseTotalTdsPre = new HashMap<String, BigDecimal>();
//            BigDecimal totAmt = new BigDecimal(0);
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                if (!mapMajorCustIdWiseTotalTdsPre.containsKey(tdIntDetail.getMajorCustId().concat(":").concat(tdIntDetail.getCustId()))) {
                    mapMajorCustIdWiseTotalTdsPre.put(tdIntDetail.getMajorCustId().concat(":").concat(tdIntDetail.getCustId()), new BigDecimal(tdIntDetail.getTotalTds() <= 0.0 ? 0 : tdIntDetail.getTotalTds()));
                }
            }
            //Map for total TDS Deducted Major and Minor CustId
            Map<String, BigDecimal> grpByTotalTds = new HashMap<>();
            Set<Entry<String, BigDecimal>> setTotalTds = mapMajorCustIdWiseTotalTdsPre.entrySet();
            Iterator<Entry<String, BigDecimal>> itTotalTds = setTotalTds.iterator();
            while (itTotalTds.hasNext()) {
                Entry<String, BigDecimal> entry = itTotalTds.next();
//                totAmt = totAmt.add(entry.getValue());
                String keyStr = entry.getKey().split(":")[0];
                if (grpByTotalTds.containsKey(keyStr)) {
                    grpByTotalTds.put(keyStr, entry.getValue().add(grpByTotalTds.get(keyStr)));
                } else {
                    grpByTotalTds.put(keyStr, entry.getValue());
                }
            }
            //Total Int Paid to Cust ID
            Map<String, BigDecimal> mapCurrCustIdWiseTotalIntPre = new HashMap<String, BigDecimal>();
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                if (!mapCurrCustIdWiseTotalIntPre.containsKey(tdIntDetail.getCustId())) {
                    mapCurrCustIdWiseTotalIntPre.put(tdIntDetail.getCustId(), new BigDecimal(tdIntDetail.getTotalInt() <= 0.0 ? 0 : tdIntDetail.getTotalInt()));
                }
            }
            /**
             * *****End Getting Previous Interest Posted******
             */
            Map<String, BigDecimal> mapCustIdAcVchWiseCloseAcTds = new HashMap<String, BigDecimal>();
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                if (!mapCustIdAcVchWiseCloseAcTds.containsKey(tdIntDetail.getCustId())) {
                    mapCustIdAcVchWiseCloseAcTds.put(tdIntDetail.getCustId(), new BigDecimal(tdIntDetail.getCloseAcTds() <= 0.0 ? 0 : tdIntDetail.getCloseAcTds()));
                }
            }

            Map<String, BigDecimal> mapCustIdAcNoOfAc = new HashMap<String, BigDecimal>();
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                if (mapCustIdAcNoOfAc.containsKey(tdIntDetail.getMajorCustId())) { //Present
                    mapCustIdAcNoOfAc.put(tdIntDetail.getMajorCustId(), new BigDecimal(1).add(mapCustIdAcNoOfAc.get(tdIntDetail.getMajorCustId())));
                } else { //Not Present
                    mapCustIdAcNoOfAc.put(tdIntDetail.getMajorCustId(), new BigDecimal(1));
                }
            }

            Map<String, String> mapMajorIdSrCitizen = new HashMap<String, String>();
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                String srCiznFlag = "N";
                if (tdIntDetail.getCustType().equalsIgnoreCase("SC") && tdIntDetail.getCustEntityType().equalsIgnoreCase("01")) {
                    srCiznFlag = "Y";
                } else {
                    /*Cust Type (01)= INDIVIDUAL && AGE>=60*/
                    if (tdIntDetail.getMajDob() != "") {
//                        System.out.println("CustId:"+tdIntDetail.getMajDob());
                        if ((tdIntDetail.getCustEntityType().equalsIgnoreCase("01")) && (CbsUtil.yearDiff(ymd.parse(tdIntDetail.getMajDob()), ymd.parse(tempDate)) >= dblBenifitAge)) {
                            srCiznFlag = "Y";
                        } else {
                            srCiznFlag = "N";
                        }
                    } else {
                        srCiznFlag = "N";
                    }
                }
                if (srCiznFlag.equalsIgnoreCase("Y") && !mapMajorIdSrCitizen.containsKey(tdIntDetail.getCustId())) {
                    mapMajorIdSrCitizen.put(tdIntDetail.getCustId(), srCiznFlag);
                }
            }

            String finStartDt = rbiReportFacade.getMinFinYear(tempDate);
            List closeAcIntList = em.createNativeQuery("select aa.custid, sum(ifnull(aa.interest,0)), ifnull(sum(bb.tds),0) as tds, ifnull(cc.closeAcTds,0)  as closetds from "
                    + " (select ci.custid, b.intopt,  b.acno, b.voucherno, sum(ifnull(b.interest,0)) as interest,  substring(am.custName,1,28), "
                    + " b.fromDT, b.todt, c.status from td_interesthistory b, td_accountmaster am, td_vouchmst c, customerid ci where am.acno = ci.acno and "
                    + " b.acno = am.acno and b.acno = c.acno and b.voucherno = c.voucherno  and b.dt between '" + finStartDt + "' and  '" + tempDate + "' "
                    + " and ( c.ClDt between '" + finStartDt + "' and  '" + tempDate + "') and am.tdsflag in ('Y','C') "
                    + " /*and ci.custid not in (select distinct custid from tdshistory a, customerid b where a.Acno = b.Acno and a.dt between '" + finStartDt + "' and  '" + date + "')*/ "
                    + " group by ci.custid, b.intopt, b.acno, b.voucherno "
                    + " union all  "
                    + " select ci.custid,  if(ifnull(b.int_opt,'Q')='','Q',ifnull(b.int_opt,'Q')) as intopt, b.acno, b.vch_no as voucherno, sum(ifnull(b.interest,0))  as interest, substring(am.custName,1,28), "
                    + " b.from_dt as fromDT, b.ToDt as todt, 'C' as status from rd_interesthistory b, accountmaster am, customerid ci "
                    + " where am.acno = ci.acno and b.acno = am.acno  and b.dt between '" + finStartDt + "' and  '" + tempDate + "' "
                    + " and (am.closingDate  between '" + finStartDt + "' and  '" + tempDate + "') and am.tdsflag in ('Y','C') "
                    + " /*and ci.custid not in (select distinct custid from tdshistory a, customerid b where a.Acno = b.Acno and a.dt between '" + finStartDt + "' and  '" + date + "')*/ "
                    + " group by ci.custid, intopt, b.acno, voucherno "
                    + " union all "
                    + " select  ci.custid, 'Q' as intopt, b.acno, '0' as voucherno, sum(ifnull(b.interest,0)) as interest,  substring(am.custName,1,28), "
                    + " b.fromdate as fromDT, b.ToDt as todt, 'C' as status from dds_interesthistory b, accountmaster am, customerid ci "
                    + " where am.acno = ci.acno and b.acno = am.acno and  b.dt between '" + finStartDt + "' and  '" + tempDate + "' "
                    + " and (am.closingDate between '" + finStartDt + "' and  '" + tempDate + "') and am.tdsflag in ('Y','C') "
                    + " /*and ci.custid not in (select distinct custid from tdshistory a, customerid b where a.Acno = b.Acno and a.dt between '" + finStartDt + "' and  '" + date + "')*/ "
                    + " group by ci.custid, b.acno order by custid, intopt, acno, voucherno) aa  "
                    + " left join "
                    + " (select acno, voucherno, sum(ifnull(tds,0)) as tds from tdshistory where dt between '" + finStartDt + "' and  '" + tempDate + "' group by  acno, voucherno) bb "
                    + " on  aa.acno= bb.acno and aa.voucherno = bb.voucherno  "
                    + "  left join "
                    + " (select b.CustId, sum(ifnull(a.closeAcTds,0)) as closeAcTds from tds_reserve_history a, "
                    + " customerid b where a.Acno = b.Acno and a.dt between '" + finStartDt + "' and  '" + tempDate + "' "
                    + " group by  b.CustId) cc "
                    + " on aa.custid = cc.CustId group by aa.custid").getResultList();
            Map<String, CustIdWiseCloseAcIntTds> mapCustIdWiseCloseInt = new HashMap<String, CustIdWiseCloseAcIntTds>();
            CustIdWiseCloseAcIntTds pojo;
            if (!closeAcIntList.isEmpty()) {
                for (int e = 0; e < closeAcIntList.size(); e++) {
                    Vector closeVect = (Vector) closeAcIntList.get(e);
                    pojo = new CustIdWiseCloseAcIntTds();
                    pojo.setCloseAcInterest(Double.parseDouble(closeVect.get(1).toString()));
                    pojo.setCloseAcTdsDeducted(Double.parseDouble(closeVect.get(2).toString()));
                    pojo.setCloseTdsDedAnotherAc(Double.parseDouble(closeVect.get(3).toString()));
                    mapCustIdWiseCloseInt.put(closeVect.get(0).toString(), pojo);
                }
            }
            HashMap<String, BigDecimal> mapNegativeTdsCustId = new HashMap<String, BigDecimal>();
            HashMap<String, BigDecimal> mapTotalTdsCustId = new HashMap<String, BigDecimal>();
            HashMap<String, BigDecimal> mapExcessTdsCustId = new HashMap<String, BigDecimal>();
            HashMap<String, BigDecimal> mapProjectedTdsCustId = new HashMap<String, BigDecimal>();
            HashMap<String, BigDecimal> mapPostiveTotalTdsCustId = new HashMap<String, BigDecimal>();
            String currentCustId, preCustId = null;
            for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                double tdsCal = 0d, tdsToBeDucteded = 0d, propInt = 0d;
                //Current Interest as per custId wise
//                System.out.println("CustId:" + tdIntDetail.getCustId());
                double currentTotalInt = map.get(tdIntDetail.getMajorCustId()).doubleValue();//tdCalcMgmtFacade.getInterestSum(tdIntDetailListAll, tdIntDetail.getCustId());
                if (!tdIntDetail.getPropCustId().equalsIgnoreCase("")) {

                    propInt = map.get(tdIntDetail.getPropCustId()) == null ? 0 : map.get(tdIntDetail.getPropCustId()).doubleValue(); // Null handling pgadh
                    // propInt = map.get(tdIntDetail.getPropCustId()).doubleValue();//tdCalcMgmtFacade.getInterestSum(tdIntDetailListAll, tdIntDetail.getPropCustId());
                } else {
                    propInt = 0;
                }
//                BigDecimal totAmt = new BigDecimal(0);

//                System.out.println("Total Amount Is-->" + totAmt);
                //Total Interest in Current Fin Year as per CustId
                double totalIntOnCustId = grpByMaj.get(tdIntDetail.getMajorCustId()).doubleValue();//mapMajorCustIdWiseTotalIntPre.get(tdIntDetail.getMajorCustId()).doubleValue();//tdIntDetail.getTotalInt();
                double totalCurrCustIdTotalInt = mapCurrCustIdWiseTotalIntPre.get(tdIntDetail.getCustId()).doubleValue();//tdIntDetail.getTotalInt();
                double minorAccInt = tdIntDetail.getMinorInterest();
                double majorAccInt = tdIntDetail.getMajorInterest();
                //Vouch wise current Interest
                double currentInt = tdIntDetail.getInterest();
                //vouch Wise Previous Interest
                double preIntVouchWise = tdIntDetail.getTotalIntPaidVouchWise();
                //CustId Wise Total TDS 
                double totalTds = grpByTotalTds.get(tdIntDetail.getMajorCustId()).doubleValue();//tdIntDetail.getTotalTds();
                //CustId Wise Decucted TDS 
                double tdsDeducted = tdIntDetail.getTdsDeducted();
                double closeAcTds = tdIntDetail.getCloseAcTds();
                double closeIntOfCustId = 0.0, closeAcIntOver = 0.0, closeTdsRecWiseShifted = 0.0, closeAcTdsDeducted = 0.0, closeTdsFromAnotherAc = 0.0, closIntAcno1 = 0.0;
                /* Senior Citizen Handling*/
                String dob = tdIntDetail.getDob();
                String majDob = tdIntDetail.getMajDob();
                String custType = tdIntDetail.getCustType();
                String custEntityType = ((tdIntDetail.getCustEntityType() == null) ? "03" : tdIntDetail.getCustEntityType());
                String srCiznFlag = "N";
                if (custType.equalsIgnoreCase("SC") && custEntityType.equalsIgnoreCase("01")) {
                    srCiznFlag = "Y";
                } else {
                    /*Cust Type (01)= INDIVIDUAL && AGE>=60*/
                    if (tdIntDetail.getMajDob() != "") {
                        if ((custEntityType.equalsIgnoreCase("01")) && (CbsUtil.yearDiff(ymd.parse(majDob), ymd.parse(tempDate)) >= dblBenifitAge)) {
                            srCiznFlag = "Y";
                        } else {
                            srCiznFlag = "N";
                        }
                    } else {
                        srCiznFlag = "N";
                    }
                }
                if (mapMajorIdSrCitizen.containsKey(tdIntDetail.getMajorCustId())) {
                    srCiznFlag = mapMajorIdSrCitizen.get(tdIntDetail.getMajorCustId());
                }
                if (tdIntDetail.getTdsFlag().equalsIgnoreCase("Y")) {
                    if (mapCustIdWiseCloseInt.containsKey(tdIntDetail.getCustId())) { //Present 
                        //if (tdIntDetail.getCustId().equalsIgnoreCase("10080")) {
                        //System.out.println("hi");
                        //}
                        closeIntOfCustId = mapCustIdWiseCloseInt.get(tdIntDetail.getCustId()).getCloseAcInterest();
                        closeAcTdsDeducted = mapCustIdWiseCloseInt.get(tdIntDetail.getCustId()).getCloseAcTdsDeducted();
                        closeTdsFromAnotherAc = mapCustIdWiseCloseInt.get(tdIntDetail.getCustId()).getCloseTdsDedAnotherAc();
                        if (closeAcTdsDeducted == 0 && closeTdsFromAnotherAc > 0) {
                            closeAcTdsDeducted = closeTdsFromAnotherAc;
                        } else if (closeAcTdsDeducted > 0 && closeTdsFromAnotherAc > 0) {
                            closeAcTdsDeducted = closeAcTdsDeducted + closeTdsFromAnotherAc;
                        }
                        double closeAcTdsCheck = ((closeIntOfCustId) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100);
                        if (Math.round(closeAcTdsCheck) > (closeAcTdsDeducted)) {
                            closeIntOfCustId = (Math.round(closeAcTdsCheck) - closeAcTdsDeducted) * 100 / ((tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan));
                        } else {
                            closeIntOfCustId = 0;
                        }
                        closIntAcno1 = closeIntOfCustId;
                        mapCustIdWiseCloseInt.remove(tdIntDetail.getCustId());
                    }
                }
                // if greate then applicable amt                
                if ((currentTotalInt + totalIntOnCustId + propInt /*+ minorAccInt + majorAccInt*/ >= (srCiznFlag.equalsIgnoreCase("Y") ? tdsApplicableAmtForSrCtzen : tdsApplicableAmt)) && tdIntDetail.getTdsFlag().equalsIgnoreCase("Y")) {
                    double totalProjectedTds = Math.ceil(((totalIntOnCustId + currentTotalInt) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100));
                    double totalCurrentTds = Math.ceil(((currentTotalInt) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100));
                    double closAcTdsProjected = Math.ceil(((closeIntOfCustId) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100));
                    if (((totalProjectedTds < totalTds) || (closAcTdsProjected <= closeAcTdsDeducted)) && (closeIntOfCustId > 0)) {
                        closeIntOfCustId = 0;
//                        System.out.println("CustID:"+tdIntDetail.getCustId()+"; Acno:"+tdIntDetail.getAcno()
//                                +"; VoucherNo:"+tdIntDetail.getVoucherNo()+"; totalProjectedTds:"+totalProjectedTds
//                                +"; totalTds:"+totalTds+"; closAcTdsProjected:"+closAcTdsProjected+"; closeIntOfCustId:"+closeIntOfCustId);
                    }
                    if (((totalProjectedTds >= totalTds) || (closAcTdsProjected > 0)) && ((closIntAcno1 > 0) && (totalProjectedTds > totalTds))) {
                        closeIntOfCustId = closIntAcno1;
//                        System.out.println("CustID<<<<<:"+tdIntDetail.getCustId()+"; Acno:"+tdIntDetail.getAcno()
//                                +"; VoucherNo:"+tdIntDetail.getVoucherNo()+"; totalProjectedTds:"+totalProjectedTds
//                                +"; totalTds:"+totalTds+"; closAcTdsProjected:"+closAcTdsProjected+"; closeIntOfCustId:"+closeIntOfCustId);
                    }
                    if ((totalProjectedTds >= totalTds) && (closeIntOfCustId > 0)) {
                        double curVchTds = Math.ceil(((currentInt) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100));
                        double closeIntOfCustId1 = (totalProjectedTds - totalTds - curVchTds) * 100 / ((tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan));
                        if (closeIntOfCustId1 > 0) {
                            if (closeIntOfCustId1 <= closeIntOfCustId) {
                                closeIntOfCustId = closeIntOfCustId1;
                            }
                        } else {
                            closeIntOfCustId = 0;
                        }
//                        System.out.println("CustID>>>>:" + tdIntDetail.getCustId() + "; Acno:" + tdIntDetail.getAcno()
//                                + "; VoucherNo:" + tdIntDetail.getVoucherNo() + "; totalProjectedTds:" + totalProjectedTds
//                                + "; totalTds:" + totalTds + "; closAcTdsProjected:" + closAcTdsProjected + "; closeIntOfCustId:" + closeIntOfCustId);
                    }
                    tdsCal = Math.ceil(((currentInt + preIntVouchWise + closeIntOfCustId) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100));
                    tdsToBeDucteded = tdsCal - tdsDeducted + closeAcTds;
                    double currTds = closeAcTds + Math.ceil(((currentInt + closeIntOfCustId) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100));
                    if ((currTds - tdsToBeDucteded) > 0) {
                        if ((Math.abs(currTds - tdsToBeDucteded) < tdsDiff) && preIntVouchWise > 0) {
//                            System.out.println("custId:" + tdIntDetail.getCustId() + "; AcNo:" + tdIntDetail.getAcno() + "; VchNO:" + tdIntDetail.getVoucherNo() + "; CurTds:" + currTds + "; tdsToBeDed:" + tdsToBeDucteded);
                            tdsToBeDucteded = currTds;
                        }
                    }

                    if (tdsToBeDucteded < 0) {
                        if (mapNegativeTdsCustId.containsKey(tdIntDetail.getMajorCustId())) { //Present
                            mapNegativeTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(tdsToBeDucteded).add(mapNegativeTdsCustId.get(tdIntDetail.getMajorCustId())));
                        } else { //Not Present
                            mapNegativeTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(tdsToBeDucteded));
                        }
                    }
                    /*Projected TDS Mapping*/
                    if (!mapProjectedTdsCustId.containsKey(tdIntDetail.getMajorCustId())) { //Present
                        mapProjectedTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(totalProjectedTds));
                    }
                    if (closeIntOfCustId != 0 && ((mapCustIdAcNoOfAc.containsKey(tdIntDetail.getCustId())) && mapCustIdAcNoOfAc.get(tdIntDetail.getCustId()).doubleValue() > 1)) {
                        if (tdsToBeDucteded > currentInt) {
//                            closeAcIntOver = Math.ceil(((closeIntOfCustId) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100), 0);
                            closeAcIntOver = tdsToBeDucteded - currentInt;
                            closeAcIntOver = (closeAcIntOver * 100 / (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan));
                            if (closeAcIntOver >= closeIntOfCustId) {
                                closeTdsRecWiseShifted = Math.ceil(((closeIntOfCustId) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100));
                                closeAcIntOver = closeIntOfCustId;
                            } else {
                                closeTdsRecWiseShifted = Math.ceil(((closeAcIntOver) * (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) / 100));
                            }
//                            mapCustIdWiseCloseInt.put(tdIntDetail.getCustId(), new BigDecimal(closeAcIntOver).abs());
                            pojo = new CustIdWiseCloseAcIntTds();
                            pojo.setCloseAcInterest(closeAcIntOver);
                            pojo.setCloseAcTdsDeducted(0);
                            mapCustIdWiseCloseInt.put(tdIntDetail.getCustId().toString(), pojo);
//                            System.out.println("CustID:"+tdIntDetail.getCustId()+"; Acno:"+tdIntDetail.getAcno()+"; VoucherNo:"+tdIntDetail.getVoucherNo()+"; closeAcIntOver:"+closeAcIntOver+"; closeTdsRecWiseShifted:"+closeTdsRecWiseShifted);
                        }
                        BigDecimal noOfAcNo = mapCustIdAcNoOfAc.get(tdIntDetail.getCustId()).subtract(new BigDecimal(1));
                        mapCustIdAcNoOfAc.remove(tdIntDetail.getCustId());
                        mapCustIdAcNoOfAc.put(tdIntDetail.getCustId(), noOfAcNo);
                    }
                }

                if (mapTotalTdsCustId.containsKey(tdIntDetail.getMajorCustId())) { //Present
                    mapTotalTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal((tdsToBeDucteded > 0 ? tdsToBeDucteded : 0) - closeTdsRecWiseShifted).add(mapTotalTdsCustId.get(tdIntDetail.getMajorCustId())));
                } else { //Not Present
                    mapTotalTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(totalTds + (tdsToBeDucteded > 0 ? tdsToBeDucteded : 0) - closeTdsRecWiseShifted));
                }

                tdIntDetailListAll.get(l).setTdsDeducted(tdsDeducted - closeAcTds);
                tdIntDetailListAll.get(l).setCloseAcIntPaid(closeIntOfCustId - closeAcIntOver);
                tdIntDetailListAll.get(l).setTdsPer(tdIntDetail.getTdsFlag().equalsIgnoreCase("Y") ? (tdIntDetail.getPan().equalsIgnoreCase("Y") ? taxWithPan : taxWithoutPan) : 0);
                tdIntDetailListAll.get(l).setTotalInt(totalCurrCustIdTotalInt);
                tdIntDetailListAll.get(l).setTds(tdsToBeDucteded - closeTdsRecWiseShifted);
                tdIntDetailListAll.get(l).setCustIdWiseCurrentInt(currentTotalInt);
                tdIntDetailListAll.get(l).setInterestWithMinMaj(totalIntOnCustId /*+ propInt + minorAccInt + majorAccInt*/);
                tdIntDetailListAll.get(l).setPropInt(propInt);
                if ((tdsToBeDucteded - closeTdsRecWiseShifted) > 0) {
                    if (mapPostiveTotalTdsCustId.containsKey(tdIntDetail.getMajorCustId())) { //Present
                        mapPostiveTotalTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(tdsToBeDucteded - closeTdsRecWiseShifted).add(mapPostiveTotalTdsCustId.get(tdIntDetail.getMajorCustId())));
                    } else { //Not Present
                        mapPostiveTotalTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(tdsToBeDucteded - closeTdsRecWiseShifted));
                    }
                }
                l = l + 1;
            }
//            System.out.println("Start:"+new Date());
            /*Adjusting the -ive TDS into same custID's other receipt*/
            if (tempDate.substring(4, 6).equalsIgnoreCase("03")) {
                /*Makeing the excess tds deducted map*/
                for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                    if (mapProjectedTdsCustId.containsKey(tdIntDetail.getMajorCustId())) {
                        double projectedTds = mapProjectedTdsCustId.get(tdIntDetail.getMajorCustId()).doubleValue();
                        double totalPrevTds = grpByTotalTds.get(tdIntDetail.getMajorCustId()).doubleValue();
                        double currTdsDeducted = mapPostiveTotalTdsCustId.containsKey(tdIntDetail.getMajorCustId()) ? mapPostiveTotalTdsCustId.get(tdIntDetail.getMajorCustId()).doubleValue() : 0;
                        if (projectedTds < (totalPrevTds + currTdsDeducted)) {
                            if (!mapExcessTdsCustId.containsKey(tdIntDetail.getMajorCustId())) { //Present
//                                System.out.println("CustID>>>>:" + tdIntDetail.getCustId() + "; Acno:" + tdIntDetail.getAcno()
//                                        + "; VoucherNo:" + tdIntDetail.getVoucherNo() + "; totalProjectedTds:" + projectedTds
//                                        + "; totalTdsDeducted:" + totalPrevTds + "; currTdsDeducted:" + currTdsDeducted);
                                mapExcessTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(projectedTds - (currTdsDeducted + totalPrevTds)));
                                if (mapNegativeTdsCustId.containsKey(tdIntDetail.getMajorCustId())) {
                                    mapNegativeTdsCustId.remove(tdIntDetail.getMajorCustId());
                                }
                            }
                        }
                    }
                }

                l = 0;
                String preCustIdCnt = "";
                /* Comment regarding Excess TDS Shifted 
                 * if (!mapExcessTdsCustId.isEmpty()) {
                 for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                 if (mapExcessTdsCustId.containsKey(tdIntDetail.getMajorCustId())) {
                 //                        if(tdIntDetail.getCustId().equalsIgnoreCase("10086")){
                 //                            System.out.println("hi");
                 //                        }
                 //                            if (mapNegativeTdsCustId.containsKey(tdIntDetail.getMajorCustId())) {
                 //                                mapNegativeTdsCustId.remove(tdIntDetail.getMajorCustId());
                 //                            }
                 double custIdNegTds = mapExcessTdsCustId.get(tdIntDetail.getMajorCustId()).doubleValue();
                 double totalInt = tdIntDetail.getInterestWithMinMaj() + tdIntDetail.getCustIdWiseCurrentInt();
                 double tdsPer = tdIntDetail.getTdsPer();
                 double projTds = Math.ceil(((totalInt) * (tdsPer) / 100));
                 double totalTdsCalc = mapTotalTdsCustId.get(tdIntDetail.getMajorCustId()).doubleValue();
                 if (!preCustIdCnt.equalsIgnoreCase(tdIntDetail.getMajorCustId())) {
                 if (totalTdsCalc >= projTds) {
                 custIdNegTds = projTds - totalTdsCalc;
                 }
                 }
                 double tdsAmt = tdIntDetail.getTds();
                 if (custIdNegTds < 0) {
                 if (tdsAmt > 0) {
                 if (tdsAmt >= Math.abs(custIdNegTds)) {
                 tdIntDetailListAll.get(l).setTds(tdsAmt + custIdNegTds);
                 tdIntDetailListAll.get(l).setTdsExtraShift(Math.abs(custIdNegTds));
                 custIdNegTds = 0;
                 mapExcessTdsCustId.remove(tdIntDetail.getMajorCustId());
                 } else {
                 tdIntDetailListAll.get(l).setTds(0);
                 tdIntDetailListAll.get(l).setTdsExtraShift(Math.abs(custIdNegTds));
                 custIdNegTds = custIdNegTds + tdsAmt;
                 tdIntDetailListAll.get(l).setTdsExtraShift(tdsAmt);
                 mapExcessTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(custIdNegTds));
                 }
                 }
                 }
                 preCustIdCnt = tdIntDetail.getMajorCustId();
                 }
                 l = l + 1;
                 }
                 }
                 l = 0;*/
                if (!mapNegativeTdsCustId.isEmpty()) {
                    for (TdIntDetail tdIntDetail : tdIntDetailListAll) {
                        if (mapNegativeTdsCustId.containsKey(tdIntDetail.getMajorCustId())) {
//                        if(tdIntDetail.getCustId().equalsIgnoreCase("10086")){
//                            System.out.println("hi");
//                        }
                            double custIdNegTds = mapNegativeTdsCustId.get(tdIntDetail.getMajorCustId()).doubleValue();
                            double totalInt = tdIntDetail.getInterestWithMinMaj() + tdIntDetail.getCustIdWiseCurrentInt();
                            double tdsPer = tdIntDetail.getTdsPer();
                            double projTds = Math.ceil(((totalInt) * (tdsPer) / 100));
                            double totalTdsCalc = mapTotalTdsCustId.get(tdIntDetail.getMajorCustId()).doubleValue();
                            //double currInt = tdIntDetail.getInterest();
                            if (!preCustIdCnt.equalsIgnoreCase(tdIntDetail.getMajorCustId())) {
                                if (totalTdsCalc >= projTds) {
                                    custIdNegTds = projTds - totalTdsCalc;
                                }
//                                else {
//                                    custIdNegTds =  projTds - totalTdsCalc;
//                                }
                            }
                            double tdsAmt = tdIntDetail.getTds();
                            if (custIdNegTds < 0) {
                                if (tdsAmt > 0) {
                                    if (tdsAmt >= Math.abs(custIdNegTds)) {
                                        tdIntDetailListAll.get(l).setTds(tdsAmt + custIdNegTds);
                                        tdIntDetailListAll.get(l).setTdsExtraShift(Math.abs(custIdNegTds));
                                        custIdNegTds = 0;
                                        mapNegativeTdsCustId.remove(tdIntDetail.getMajorCustId());
                                    } else {
                                        tdIntDetailListAll.get(l).setTds(0);
                                        tdIntDetailListAll.get(l).setTdsExtraShift(Math.abs(custIdNegTds));
                                        custIdNegTds = custIdNegTds + tdsAmt;
                                        tdIntDetailListAll.get(l).setTdsExtraShift(tdsAmt);
                                        mapNegativeTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(custIdNegTds));
                                    }
                                }
                            }
//                            else if (custIdNegTds > 0 && tdsAmt < 0) {
//                                System.out.println("Major CustId:::"+tdIntDetail.getMajorCustId()+"; Minor CustId:"+tdIntDetail.getCustId());
//                                if (currInt>=custIdNegTds) {
//                                    tdIntDetailListAll.get(l).setTds(custIdNegTds);
//                                    tdIntDetailListAll.get(l).setTdsExtraShift(0);
//                                    custIdNegTds = 0;
//                                    mapNegativeTdsCustId.remove(tdIntDetail.getMajorCustId());
//                                } else {
//                                    tdIntDetailListAll.get(l).setTds(currInt);
//                                    tdIntDetailListAll.get(l).setTdsExtraShift(0);
//                                    custIdNegTds = custIdNegTds - currInt;                                    
//                                    mapNegativeTdsCustId.put(tdIntDetail.getMajorCustId(), new BigDecimal(custIdNegTds));
//                                }
//                            }
                            preCustIdCnt = tdIntDetail.getMajorCustId();
                        }
                        l = l + 1;
                    }
                }
            }
//            System.out.println("??????????????????End:" + new Date());
            return tdIntDetailListAll;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    class CustIdByComparator implements Comparator<TdIntDetail> {

        public int compare(TdIntDetail tdIntDetailObj1, TdIntDetail tdIntDetailObj2) {
            Long custId1 = Long.parseLong(tdIntDetailObj1.getCustId());
            Long custId2 = Long.parseLong(tdIntDetailObj2.getCustId());
            return custId1.compareTo(custId2);
        }
    }

    class MajorCustIdByComparator implements Comparator<TdIntDetail> {

        public int compare(TdIntDetail tdIntDetailObj1, TdIntDetail tdIntDetailObj2) {
            Long custId1 = Long.parseLong(tdIntDetailObj1.getMajorCustId());
            Long custId2 = Long.parseLong(tdIntDetailObj2.getMajorCustId());
            return custId1.compareTo(custId2);
        }
    }

    public List<TdIntDetail> tdInterestCalculation(String tmpFDate, String tempDate, String acctType, String intOpt, String user,
            String tmpAcNo, String date, String brCode) throws ApplicationException {
        List<TdIntDetail> tdIntDetailList = new ArrayList<TdIntDetail>();
        try {
            if (tmpAcNo == null) {
                return setMessage(tdIntDetailList, "Account no is blank");
            }
            List chk1 = em.createNativeQuery("Select ifnull(date_format(max(TD_TillDate),'%Y%m%d'),'') From td_intflag Where IntOpt='" + intOpt + "' And acType='"
                    + acctType + "' AND brncode='" + brCode + "'").getResultList();
            if (!chk1.isEmpty()) {
                Vector chk1V = (Vector) chk1.get(0);
                String maxTillDate = chk1V.get(0).toString();
                if (maxTillDate.equals("")) {
                    return setMessage(tdIntDetailList, "Td last interest posting date does not exist.");
                }
                long dtDiff = CbsUtil.dayDiff(ymd.parse(maxTillDate), ymd.parse(tempDate));
                if (dtDiff <= 0) {
                    return setMessage(tdIntDetailList, "Interest already posted");
                }
            }
            if (tempDate.substring(4, 6).equalsIgnoreCase("03") || tempDate.substring(4, 6).equalsIgnoreCase("06")
                    || tempDate.substring(4, 6).equalsIgnoreCase("09") || tempDate.substring(4, 6).equalsIgnoreCase("12")) {
                String tdsPostEnable = "";
                List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
                if (!tdsPostEnableList.isEmpty()) {
                    Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                    tdsPostEnable = tdsPostEnableVect.get(0).toString();
                    if (tdsPostEnable.equalsIgnoreCase("Y")) {
                        String acNature = ftsMethods.getAcNatureByCode(acctType);
                        List tdsPostList = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'TDS POSTED' and todt = '" + tempDate + "' and brncode = '" + brCode + "' and actype in ('" + acNature + "')").getResultList();
                        if (tdsPostList.isEmpty()) {
                            throw new ApplicationException("Please post/reserve the TDS first.");
                        }
                    }
                }
            }

            tdIntDetailList = intCal.interestCalculation(tmpFDate, tempDate, intOpt, acctType, brCode);
            if (tdIntDetailList.isEmpty()) {
                return setMessage(tdIntDetailList, "Transaction does not exists");
            } else {
                return tdIntDetailList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

//    public String tdInterestPosting(List<TdIntDetail> tdIntDetailsList, String toDate, String date, String acctType, String tmpAcNo,
//            String intOpt, String user, String brCode) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            
//            if(toDate.substring(4,6).equalsIgnoreCase("03")||toDate.substring(4,6).equalsIgnoreCase("06")
//                        ||toDate.substring(4,6).equalsIgnoreCase("09")||toDate.substring(4,6).equalsIgnoreCase("12")){
//                String tdsPostEnable = "";
//                List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
//                if (!tdsPostEnableList.isEmpty()) {
//                    Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
//                    tdsPostEnable = tdsPostEnableVect.get(0).toString(); 
//                    if(tdsPostEnable.equalsIgnoreCase("Y")) {
//                        String acNature = ftsMethods.getAcNatureByCode(acctType);
//                        List tdsPostList = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'TDS POSTED' and todt = '" + toDate + "' and brncode = '" + brCode + "' and actype in ('" + acNature + "')").getResultList();
//                            if (tdsPostList.isEmpty()) {
//                            throw new ApplicationException("Please did not reserve the TDS.");
//                        }
//                    }
//                }
//            }        
//            
//            String message = intCal.tdInterestPosting(tdIntDetailsList, toDate, date, acctType, tmpAcNo, intOpt, user, brCode);
//            if (!message.equalsIgnoreCase("Yes")) {
//                ut.rollback();
//                return message;
//            } else {
//                ut.commit();
//                return message;
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }
    public List<TdIntDetail> tdInterestCalculationWithTds(String tmpFDate, String tempDate, String accType, String intopt, String user,
            String tmpAcNo, String date, String brCode) throws ApplicationException {
        List<TdIntDetail> tdIntDetailList = new ArrayList<>();
//        System.out.println("Start>>>:"+ new Date() +"; Branch:"+brCode+"; acctType:" + acctType + "; intOpt:" + intOpt + "; FDate:" + tmpFDate + "; TDate:" + tempDate);
        try {
//            if (tmpAcNo == null) {
//                return setMessage(tdIntDetailList, "Account no is blank");
//            }

            if (accType.equalsIgnoreCase("ALL") && intopt.equalsIgnoreCase("A")) {
                List acTypeList = acctTypeCombo();
                if (!acTypeList.isEmpty()) {
                    for (int i = 0; i < acTypeList.size(); i++) {
                        String acctType = ((Vector) acTypeList.get(i)).get(0).toString();
                        List productCodeList = setGLAcNoAndInterestOption(acctType);
                        Vector ele = (Vector) productCodeList.get(0);
                        //String glHead = brCode + ele.get(2).toString() + "01";
                        String intOpt = ele.get(3).toString();
                        for (int j = 0; j < intOpt.length(); j++) {
                            List chk1 = em.createNativeQuery("Select ifnull(date_format(max(TD_TillDate),'%Y%m%d'),'') From td_intflag Where IntOpt='" + String.valueOf(intOpt.charAt(j)) + "' And acType='"
                                    + acctType + "' AND brncode='" + brCode + "'").getResultList();
                            if (!chk1.isEmpty()) {
                                Vector chk1V = (Vector) chk1.get(0);
                                String maxTillDate = chk1V.get(0).toString();
                                if (maxTillDate.equals("")) {
                                    return setMessage(tdIntDetailList, "Td last interest posting date does not exist: AcType:" + acctType + "; IntOpt:" + String.valueOf(intOpt.charAt(j)) + "; Brnch:" + brCode);
                                }
                                long dtDiff = CbsUtil.dayDiff(ymd.parse(maxTillDate), ymd.parse(tempDate));
                                if (dtDiff <= 0) {
                                    return setMessage(tdIntDetailList, "Interest already posted: AcType:" + acctType + "; IntOpt:" + String.valueOf(intOpt.charAt(j)) + "; Brnch:" + brCode);
                                }
                            }
                        }
                    }
                }
            }

            if (tempDate.substring(4, 6).equalsIgnoreCase("03")) {
                intopt = "'M','Q','H','Y','C','S'";
            } else if (tempDate.substring(4, 6).equalsIgnoreCase("09")) {
                intopt = "'M','Q','H','C','S'";
            } else if (tempDate.substring(4, 6).equalsIgnoreCase("06") || tempDate.substring(4, 6).equalsIgnoreCase("12")) {
                intopt = "'M','Q','C','S'";
            } else {
                intopt = "'M'";
            }
            tdIntDetailList = intCal.interestCalculationWithTds(tmpFDate, tempDate, intopt, accType, brCode);
//            System.out.println("End>>>:" + new Date() + "; Branch:"+brCode+"; Size: " + tdIntDetailList.size() + "; acctType:" + acctType + "; intOpt:" + intOpt + "; FDate:" + tmpFDate + "; TDate:" + tempDate);
            if (tdIntDetailList.isEmpty()) {
                return setMessage(tdIntDetailList, "Transaction does not exists");
            } else {
                return tdIntDetailList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String tdInterestPosting(List<TdIntDetail> tdIntDetailsList, String toDate, String date, String acctType, String tmpAcNo,
            String intOpt, String user, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            if (toDate.substring(4, 6).equalsIgnoreCase("03") || toDate.substring(4, 6).equalsIgnoreCase("06")
                    || toDate.substring(4, 6).equalsIgnoreCase("09") || toDate.substring(4, 6).equalsIgnoreCase("12")) {
                String tdsPostEnable = "";
                List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
                if (!tdsPostEnableList.isEmpty()) {
                    Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                    tdsPostEnable = tdsPostEnableVect.get(0).toString();
                    if (tdsPostEnable.equalsIgnoreCase("Y")) {
                        String acNature = ftsMethods.getAcNatureByCode(acctType);
                        List tdsPostList = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'TDS POSTED' and todt = '" + toDate + "' and brncode = '" + brCode + "' and actype in ('" + acNature + "')").getResultList();
                        if (tdsPostList.isEmpty()) {
                            throw new ApplicationException("Please did not reserve the TDS.");
                        }
                    }
                }
            }

//            String message = intCal.tdInterestPosting(tdIntDetailsList, toDate, date, acctType, tmpAcNo, intOpt, user, brCode);
//            if (!message.equalsIgnoreCase("Yes")) {
//                ut.rollback();
//                return message;
//            } else {
//                ut.commit();
//                return message;
//            }
            Map<String, Object> map = intCal.tdInterestPosting(tdIntDetailsList, toDate, date, acctType, tmpAcNo, intOpt, user, brCode);
            String message = map.get("msg").toString();
            List<TdInterestSmsTo> intSmsList = (List<TdInterestSmsTo>) map.get("list");
            if (!message.equalsIgnoreCase("Yes")) {
                ut.rollback();
                return message;
            } else {
                ut.commit();
                //Sending Td Interest Sms
                int code = ftsMethods.getCodeForReportName("TD-MQ-INT-SMS");
                if (code == 1) {
                    List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                    String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                    List<TransferSmsRequestTo> smsSenderList = new ArrayList<TransferSmsRequestTo>();
                    try {
                        for (TdInterestSmsTo to : intSmsList) {
                            String mobileNo = messageDetailBeanRemote.getMobileNumberByAcno(to.getTdAcno());
                            if (mobileNo.length() == 10) {
                                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();

                                tSmsRequestTo.setMsgType("PAT");
                                tSmsRequestTo.setTemplate(SmsType.TD_MONTHLY_QUARTERLY_INTEREST);
                                tSmsRequestTo.setAcno(to.getTdAcno());
                                tSmsRequestTo.setAmount(to.getInterest().doubleValue());
                                tSmsRequestTo.setDate(dmy.format(new Date()));
                                tSmsRequestTo.setFirstCheque("XXXX" + to.getPrimaryAcno().substring(4, 10) + "XX");
                                tSmsRequestTo.setPromoMobile("+91" + mobileNo);
                                tSmsRequestTo.setBankName(templateBankName);

                                smsSenderList.add(tSmsRequestTo);
                            }
                        }
                        smsRemote.trfSmsRequestToBatch(smsSenderList);
                    } catch (Exception ex) {
                        System.out.println("Problem In Sending The Td Interest SMS.");
                    }
                }
                //End here
                return message;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private List<TdIntDetail> setMessage(List<TdIntDetail> tdIntDetailList, String msg) throws ApplicationException {
        try {
            TdIntDetail tdIntDetail = new TdIntDetail();
            tdIntDetail.setMsg(msg);
            tdIntDetailList.add(tdIntDetail);
            return tdIntDetailList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String tdInterestPostingWithTds(List<TdIntDetail> tdIntDetailsList, String toDate, String fromDt, String acctType, String tmpAcNo,
            String intOpt, String user, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String message = "No";
            if (acctType.equalsIgnoreCase("ALL")) {
                int code = ftsMethods.getCodeForReportName("TD-MQ-INT-SMS");
                List<TransferSmsRequestTo> smsSenderList = new ArrayList<TransferSmsRequestTo>();
                //Getting the Distinct Branch form of list
                Map<String, String> mapBranch = new HashMap<String, String>();
                for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                    if (!mapBranch.containsKey(tdIntDetail.getAcno().substring(0, 2))) { //Present                        
                        mapBranch.put(tdIntDetail.getAcno().substring(0, 2), tdIntDetail.getAcno().substring(0, 2));
                    }
                }
                Set<Map.Entry<String, String>> setBranch = mapBranch.entrySet();
                Iterator<Map.Entry<String, String>> itBranch = setBranch.iterator();
                while (itBranch.hasNext()) {
                    Map.Entry<String, String> entryBranch = itBranch.next();
//                    System.out.println("ImntOptiiiii-->" + entryBranch.getKey() + "::Amount-->" + entryBranch.getValue());
                    String branchCode = entryBranch.getValue();
                    //Getting the Distinct value of INT OPT with multiple ACCT TYPE in the form of list
                    Map<String, List> mapAcTypeIntOpt = new HashMap<String, List>();
                    for (TdIntDetail tdIntDetail1 : tdIntDetailsList) {
                        if (mapAcTypeIntOpt.containsKey(tdIntDetail1.getIntOpt()) && entryBranch.getValue().equalsIgnoreCase(tdIntDetail1.getAcno().substring(0, 2))) { //Present
                            List<String> mapValues = mapAcTypeIntOpt.get(tdIntDetail1.getIntOpt());
                            for (int i = 0; i < mapValues.size(); i++) {
//                        String value = mapValues.get(i).toString();
                                if (!mapValues.contains(tdIntDetail1.getAcno().substring(2, 4))) {
                                    mapValues.add(tdIntDetail1.getAcno().substring(2, 4));
                                    mapAcTypeIntOpt.put(tdIntDetail1.getIntOpt(), mapValues);
                                }
                            }
                        } else { //Not Present
                            if (entryBranch.getValue().equalsIgnoreCase(tdIntDetail1.getAcno().substring(0, 2))) {
                                ArrayList<String> a = new ArrayList();
                                a.add(tdIntDetail1.getAcno().substring(2, 4));
                                mapAcTypeIntOpt.put(tdIntDetail1.getIntOpt(), a);
                            }
                        }
                    }
                    Set<Map.Entry<String, List>> set = mapAcTypeIntOpt.entrySet();
                    Iterator<Map.Entry<String, List>> it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, List> entryIntOpt = it.next();
//                        System.out.println("ImntOpt-->" + entry.getKey() + "::Value-->" + entry.getValue() + "::Size-->" + entry.getValue().size());
                        for (int l = 0; l < entryIntOpt.getValue().size(); l++) {
                            String acType = entryIntOpt.getValue().get(l).toString();
                            String intOption = entryIntOpt.getKey().toString();
                            String glAccNo = "";

                            //Getting the GL head as per Account Type
                            List tempList = setGLAcNoAndInterestOption(acType);
                            if (!tempList.isEmpty()) {
                                Vector ele = (Vector) tempList.get(0);
                                glAccNo = branchCode + ele.get(2).toString() + "01";
//                                System.out.println("GL Head:" + glAccNo);
                            }

                            //Getting the value from Mail list as per accountType and Int Option 
                            List<TdIntDetail> tdIntCalListAcTypeWise = new ArrayList<TdIntDetail>();
                            for (int m = 0; m < tdIntDetailsList.size(); m++) {
                                if (tdIntDetailsList.get(m).getAcno().substring(0, 2).equalsIgnoreCase(branchCode) && tdIntDetailsList.get(m).getAcno().substring(2, 4).equalsIgnoreCase(acType) && tdIntDetailsList.get(m).getIntOpt().equalsIgnoreCase(intOption)) {
                                    tdIntCalListAcTypeWise.add(tdIntDetailsList.get(m));
                                }
                            }

                            String acNature = ftsMethods.getAcNatureByCode(acType);
//                            System.out.println(">>>Branch:" + branchCode + ";AcNature:" + acNature + ";acType:" + acType + "; intOpt:" + intOpt + "; List Size:" + tdIntCalListAcTypeWise.size());

                            if (acNature.equalsIgnoreCase("FD") || acNature.equalsIgnoreCase("MS")) {
                                Map<String, Object> map = intCal.tdInterestPostingWithTds(tdIntCalListAcTypeWise, toDate, fromDt, acType, glAccNo, intOption, user, branchCode);
                                message = map.get("msg").toString();
//                                List<TdInterestSmsTo> intSmsList = new ArrayList<TdInterestSmsTo>();
                                List<TdInterestSmsTo> intSmsList = (List<TdInterestSmsTo>) map.get("list");
                                if (!message.equalsIgnoreCase("Yes")) {
                                    ut.rollback();
                                    return message;
                                } else {
                                    if (code == 1) {
                                        List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                                        String templateBankName = bankTo.get(0).getTemplateBankName().trim();
                                        try {
                                            for (TdInterestSmsTo to : intSmsList) {
                                                String mobileNo = messageDetailBeanRemote.getMobileNumberByAcno(to.getTdAcno());
                                                if (mobileNo.length() == 10) {
                                                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                                                    tSmsRequestTo.setMsgType("PAT");
                                                    tSmsRequestTo.setTemplate(SmsType.TD_MONTHLY_QUARTERLY_INTEREST);
                                                    tSmsRequestTo.setAcno(to.getTdAcno());
                                                    tSmsRequestTo.setAmount(to.getInterest().doubleValue());
                                                    tSmsRequestTo.setDate(dmy.format(new Date()));
                                                    tSmsRequestTo.setFirstCheque("XXXX" + to.getPrimaryAcno().substring(4, 10) + "XX");
                                                    tSmsRequestTo.setPromoMobile("+91" + mobileNo);
                                                    tSmsRequestTo.setBankName(templateBankName);
                                                    tSmsRequestTo.setModuleName("NO-CHG");
                                                    smsSenderList.add(tSmsRequestTo);
                                                }
                                            }
                                        } catch (Exception ex) {
                                            System.out.println("Problem In Sending The Td Interest SMS.");
                                        }
                                    }
                                }
                            } else if (acNature.equalsIgnoreCase("RD")) {
                                String firstQDt = CbsUtil.getQuarterFirstAndLastDate(toDate.substring(4, 6), toDate.substring(0, 4), "F");
                                String rdIntPostingMsg = intCal.rdInterestPostingWithTds(tdIntCalListAcTypeWise, acType, user, firstQDt, toDate, branchCode, intOption);
                                if (rdIntPostingMsg.contains("true")) {
                                    message = "Yes";
                                } else {
                                    ut.rollback();
                                    return rdIntPostingMsg;
                                }
                            } else if (acNature.equalsIgnoreCase("DS")) {
                                String firstQDt = CbsUtil.getQuarterFirstAndLastDate(toDate.substring(4, 6), toDate.substring(0, 4), "F");
                                String rdIntPostingMsg = intCal.ddsInterestPostingWithTds(tdIntCalListAcTypeWise, acType, firstQDt, toDate, branchCode, user);
                                if (rdIntPostingMsg.contains("true")) {
                                    message = "Yes";
                                } else {
                                    ut.rollback();
                                    return rdIntPostingMsg;
                                }
                            }
                        }
                    }
                }
                if (message.equalsIgnoreCase("Yes")) {
                    ut.commit();
                    try {
                        if (!smsSenderList.isEmpty()) {
                            smsRemote.trfSmsRequestToBatch(smsSenderList);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("Problem In Sending The Td Interest SMS.");
                    }
                }
//                return message;
            }
            return message;
//            else {
//                Map<String, Object> map = intCal.tdInterestPosting(tdIntDetailsList, toDate, fromDt, acctType, tmpAcNo, intOpt, user, brCode);
//                message = map.get("msg").toString();
//                List<TdInterestSmsTo> intSmsList = (List<TdInterestSmsTo>) map.get("list");
//                if (!message.equalsIgnoreCase("Yes")) {
//                    ut.rollback();
//                    return message;
//                } else {
//                    ut.commit();
//                    //Sending Td Interest Sms
//                    int code = ftsMethods.getCodeForReportName("TD-MQ-INT-SMS");
//                    if (code == 1) {
//                        List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
//                        String templateBankName = bankTo.get(0).getTemplateBankName().trim();
//
//                        List<TransferSmsRequestTo> smsSenderList = new ArrayList<TransferSmsRequestTo>();
//                        try {
//                            for (TdInterestSmsTo to : intSmsList) {
//                                String mobileNo = messageDetailBeanRemote.getMobileNumberByAcno(to.getTdAcno());
//                                if (mobileNo.length() == 10) {
//                                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
//
//                                    tSmsRequestTo.setMsgType("PAT");
//                                    tSmsRequestTo.setTemplate(SmsType.TD_MONTHLY_QUARTERLY_INTEREST);
//                                    tSmsRequestTo.setAcno(to.getTdAcno());
//                                    tSmsRequestTo.setAmount(to.getInterest().doubleValue());
//                                    tSmsRequestTo.setDate(dmy.format(new Date()));
//                                    tSmsRequestTo.setFirstCheque("XXXX" + to.getPrimaryAcno().substring(4, 10) + "XX");
//                                    tSmsRequestTo.setPromoMobile("+91" + mobileNo);
//                                    tSmsRequestTo.setBankName(templateBankName);
//
//                                    smsSenderList.add(tSmsRequestTo);
//                                }
//                            }
//                            smsRemote.trfSmsRequestToBatch(smsSenderList);
//                        } catch (Exception ex) {
//                            System.out.println("Problem In Sending The Td Interest SMS.");
//                        }
//                    }
//                    //End here
//                    return message;
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public List acctTypeLoad() throws ApplicationException {
        List acctType = new ArrayList();
        try {
            acctType = em.createNativeQuery("Select AcctCode,AcctDesc From accounttypemaster Where AcctNature In ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "') Order by acctCode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acctType;
    }

    public List finYear(String brCode) throws ApplicationException {
        List finYear = new ArrayList();
        try {
            finYear = em.createNativeQuery("Select Min(F_Year) From yearend Where YearEndFlag='N' and brnCode='" + brCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finYear;
    }

    public List acNat(String acDesc) throws ApplicationException {
        List acNat = new ArrayList();
        try {
            acNat = em.createNativeQuery("select acctCode, acctNature, GLHeadInt from accounttypemaster where acctDesc='" + acDesc + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acNat;
    }

    public List<TempProjIntDetail> projectedIntCalTD(String intopt, String acctype, String brcode, String tillDate) throws ApplicationException {
        List<TempProjIntDetail> tempProjIntDetail = new ArrayList<TempProjIntDetail>();
        String tillDt = "", tmpFDate = "", tmpdate = "";
        try {
            List intFlgList = em.createNativeQuery("SELECT ifnull(date_format(MAX(TD_TILLDATE),'%Y%m%d'),'') FROM td_intflag WHERE ACTYPE='" + acctype + "' AND INTOPT='" + intopt + "' AND BRNCODE='" + brcode + "'").getResultList();
            if (intFlgList.size() > 0) {
                Vector element = (Vector) intFlgList.get(0);
                tillDt = element.get(0).toString();
            } else {
                throw new ApplicationException("Till date not exist.");
            }

            List yearEndList = em.createNativeQuery("select min(mindate),min(maxdate) from yearend where yearendflag = 'N' and brncode = '" + brcode + "'").getResultList();
            if (yearEndList.size() > 0) {
                Vector element = (Vector) yearEndList.get(0);
                tmpFDate = element.get(0).toString();
                tmpdate = tillDate;
            } else {
                throw new ApplicationException("Date in year end does not exist.");
            }
            List<TdIntDetail> tdIntDetailList = intCal.interestCalculation(tmpFDate, tmpdate, intopt, acctype, brcode);
            if (tdIntDetailList.size() > 0) {
                for (int i = 0; i < tdIntDetailList.size(); i++) {
                    TdIntDetail tdIntDetail = tdIntDetailList.get(i);
                    TempProjIntDetail tableObj = new TempProjIntDetail();
                    tableObj.setAcno(tdIntDetail.getAcno());
                    tableObj.setCustname(tdIntDetail.getCustName());
                    tableObj.setRoi(tdIntDetail.getRoi());
                    tableObj.setMatDt(tdIntDetail.getMatDt());
                    tableObj.setInt2(tdIntDetail.getInterest());
                    tableObj.setCumuPrinamt(tdIntDetail.getCumuprinamt());
                    tableObj.setVoucherNo(tdIntDetail.getVoucherNo());
                    tempProjIntDetail.add(tableObj);
                }
                for (int i = 0; i < tempProjIntDetail.size(); i++) {
                    TempProjIntDetail tableObj = tempProjIntDetail.get(i);
                    String acno = tableObj.getAcno();

                    List int1List = em.createNativeQuery("select ifnull(sum(a.interest),0) from td_interesthistory a where a.acno='"
                            + acno + "' and voucherno=" + tableObj.getVoucherNo() + " AND a.DT BETWEEN '" + tmpFDate + "' AND '"
                            + tillDt + "' ").getResultList();
                    Vector element = (Vector) int1List.get(0);

                    int seqno = 0;
                    String fyear = "";
//                    List form15hList = em.createNativeQuery("select seqno,fyear from td_form15h where acno = '" + acno + "'").getResultList();
//                    if (!form15hList.isEmpty()) {
//                        Vector form15Element = (Vector) form15hList.get(0);
//                        seqno = Integer.parseInt(form15Element.get(0).toString());
//                        fyear = form15Element.get(1).toString();
//                    }

                    double int1 = Double.parseDouble(element.get(0).toString());
                    double int3 = int1 + tableObj.getInt2();

                    tableObj.setInt1(int1);
                    tableObj.setInt3(int3);
                    tableObj.setfAmt(tableObj.getCumuPrinamt());
                    tableObj.setTds1(seqno);
                    tableObj.setTd2(fyear);
                    tableObj.setMsg("true");
                }
                return tempProjIntDetail;
            } else {
                throw new ApplicationException("Data does not exist");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List finYearTD(String brCode) throws ApplicationException {
        List lst = new ArrayList();
        List finYear = new ArrayList();
        try {
            lst = em.createNativeQuery("Select Min(F_Year) From yearend Where YearEndFlag='N' and brnCode='" + brCode + "'").getResultList();
            Vector element = (Vector) lst.get(0);
            String fYr = element.get(0).toString();

            finYear = em.createNativeQuery("Select substring(mindate,1,4),substring(maxdate,1,4) From yearend Where f_year ='" + fYr + "' and brncode ='" + brCode + "'").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finYear;
    }

    public List<TdIntDetail> tdProvInterestCalculation(String tmpFDate, String tempDate, String acctType, String intOpt, String user,
            String tmpAcNo, String date, String brCode) throws ApplicationException {
        List<TdIntDetail> tdIntDetailList = new ArrayList<TdIntDetail>();
        try {
            if (tmpAcNo == null) {
                return setMessage(tdIntDetailList, "Account no is blank");
            }
            List chk1 = em.createNativeQuery("Select ifnull(date_format(max(TD_TillDate),'%Y%m%d'),'') From td_intflag Where IntOpt='" + intOpt + "' And acType='"
                    + acctType + "' AND brncode='" + brCode + "'").getResultList();
            if (!chk1.isEmpty()) {
                Vector chk1V = (Vector) chk1.get(0);
                String maxTillDate = chk1V.get(0).toString();
                if (maxTillDate.equals("")) {
                    return setMessage(tdIntDetailList, "Td last interest posting date does not exist.");
                }
                long dtDiff = CbsUtil.dayDiff(ymd.parse(maxTillDate), ymd.parse(tempDate));
                if (dtDiff <= 0) {
                    tdIntDetailList = intCal.provInterestCalculation(tmpFDate, tempDate, intOpt, acctType, brCode);
                } else {
                    return setMessage(tdIntDetailList, "Please Post Interest First.");
                }
            } else {
                return setMessage(tdIntDetailList, "Please Post Interest First.");
            }

            if (tdIntDetailList.isEmpty()) {
                return setMessage(tdIntDetailList, "Transaction does not exists");
            } else {
                return tdIntDetailList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
//    public String postProvInterestTd(String tdIntHead,String tdProvIntHead, double amt,String enterBy, String orgnBrCode) throws ApplicationException {
//        Float recno;
//        Float trsno;
//        try {
//            recno = ftsMethods.getRecNo();
//            trsno = ftsMethods.getTrsNo();
//
//            ftsMethods.updateBalance("PO", tdIntHead, 0, amt, "", "");
//            ftsMethods.updateBalance("PO", tdProvIntHead, amt, 0, "", "");
//
//            Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,dramt,ty,trantype,details,auth,authby,enterBy,"
//                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + tdIntHead + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
//                    + amt + ",1,8,'Provisional Interest Amount','Y','System','" + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode
//                    + "','" + orgnBrCode + "')").executeUpdate();
//            if (reconBalanL <= 0) {
//                return "Error in gl_recon Insertion !!!";
//            }
//
//            recno = ftsMethods.getRecNo();
//            
//            reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,cramt,ty,trantype,details,auth,authby,enterBy,"
//                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + tdProvIntHead + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
//                    + amt + ",0,8,'Provisional Interest Amount','Y','System','" + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode
//                    + "','" + orgnBrCode + "')").executeUpdate();
//            if (reconBalanL <= 0) {
//                return "Error in gl_recon Insertion !!!";
//            }
//            
//            return "Yes";
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }

    @Override
    public List<TdIntDetail> tdProvInterestMonthlyData(String frDt, String toDt, String intopt, String acctype, String brCode) throws ApplicationException {
        List<TdIntDetail> dataList = new ArrayList<>();

        try {

            List resultList = em.createNativeQuery("select tm.seqno,tm.acno, am.custName,cast(tm.voucherno as unsigned),tm.roi,date_format(tm.nextintpaydt,'%Y%m%d'),date_format(tm.matdt,'%Y%m%d'), "
                    + "tm.prinamt,ifnull(tm.years,0), ifnull(tm.months,0),ifnull(tm.days,0),tm.cumuprinamt,tm.inttoAcno,tm.TDSDeducted, date_format(tm.FDDT,'%Y%m%d'),tm.period "
                    + "From td_vouchmst tm, td_accountmaster am "
                    + "Where am.acno=tm.acno and tm.intopt='" + intopt + "' and tm.status<>'C'  and am.accttype='" + acctype + "' "
                    + "AND tm.matdt>='" + frDt + "' and tm.roi<>0 and am.CurBrCode='" + brCode + "'").getResultList();

            String fromdt = frDt;
            String toDate = toDt;

            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vtr = (Vector) resultList.get(i);
                    TdIntDetail pojo = new TdIntDetail();
                    String acno = vtr.get(1).toString();
                    String custName = vtr.get(2).toString();
                    String voucherno = vtr.get(3).toString();
                    String roi = vtr.get(4).toString();
                    String nextintpaydt = vtr.get(5).toString();

                    String matdt = vtr.get(6).toString();
                    String prinamt = vtr.get(7).toString();
                    String years = vtr.get(8).toString();
                    String months = vtr.get(9).toString();
                    String days = vtr.get(10).toString();
                    String cumuprinamt = vtr.get(11).toString();

                    String inttoAcno = vtr.get(12).toString();
                    String TDSDeducted = vtr.get(13).toString();
                    String fddt = vtr.get(14).toString();
                    String period = vtr.get(15).toString();
                    String bAInt = "0";

                    if (ymd.parse(fromdt).before(ymd.parse(fddt))) {
                        fromdt = fddt;
                    } else {
                        fromdt = fromdt;
                    }
                    if (ymd.parse(toDate).after(ymd.parse(matdt))) {
                        toDate = matdt;
                    } else {
                        toDate = toDate;
                    }

                    if ((intopt.equalsIgnoreCase("S")) || (intopt.equalsIgnoreCase("Simple")) || (intopt.equalsIgnoreCase("Y"))) {
                        bAInt = tdRcptMgmtRemote.orgFDInterestSimple15gh(intopt, Float.parseFloat(roi), fromdt, toDate, Double.parseDouble(prinamt), period, acno.substring(0, 2));
                    } else {
                        bAInt = tdRcptMgmtRemote.orgFDInterest(intopt, Float.parseFloat(roi), fromdt, toDate, Double.parseDouble(cumuprinamt), period, acno.substring(0, 2));
                    }

                    fromdt = frDt;
                    toDate = toDt;

                    pojo.setAcno(acno);
                    pojo.setCustName(custName);
                    pojo.setVoucherNo(Integer.parseInt(voucherno));
                    pojo.setRoi(Float.parseFloat(roi));
                    pojo.setpAmt(Double.parseDouble(prinamt));
                    pojo.setCumuprinamt(Float.parseFloat(cumuprinamt));
                    pojo.setInterest(Double.parseDouble(bAInt));
                    pojo.setFdDt(dmy.format(ymd.parse(fddt)));
                    pojo.setMatDt(dmy.format(ymd.parse(matdt)));
                    dataList.add(pojo);

                }
            }
            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
