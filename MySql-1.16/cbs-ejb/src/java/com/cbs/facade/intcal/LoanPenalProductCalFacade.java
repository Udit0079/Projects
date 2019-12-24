/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.LoanMultiList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

/**
 *
 * @author admin
 */
@Stateless(mappedName = "LoanPenalProductCalFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LoanPenalProductCalFacade implements LoanPenalProductCalFacadeRemote {

    @EJB
    private LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPosting;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat mdy = new SimpleDateFormat("MMM dd yyyy");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public List<LoanMultiList> cbsLoanPenalCalculation(String intOpt, String acType, String acNo, String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException {
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        try {
//            fromDt = ymd.format(dmy.parse(fromDt));
//            toDt = ymd.format(dmy.parse(toDt));
            List<LoanMultiList> lmlTemp = new ArrayList<LoanMultiList>();
            List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
            List<LoanIntCalcList> intProductDetail2 = new ArrayList<LoanIntCalcList>();
            String acNature = "";

//            List checkEmiPenalPosted = em.createNativeQuery("select actype from parameterinfo_posthistory where purpose = 'EMI PENAL INT' and actype = '" + acType + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
//            if (checkEmiPenalPosted.size() > 0) {
//                throw new ApplicationException("Penal interest already posted");
//            }
            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Account nature does not exist for account code " + acType);
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }

            String securityPenalExist = "N";
            String odPenalExist = "N";
            String stockPenalExist = "N";
            String emiPenalExist = "N";
            
            List securityPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_SECURITY' ").getResultList();
            if(securityPenalExistList.size()>0){
                Vector securityPenalExistVect = (Vector) securityPenalExistList.get(0);
                securityPenalExist = securityPenalExistVect.get(0).toString();
            }
            
            List odPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_ODLIMIT' ").getResultList();
            if(odPenalExistList.size()>0){
                Vector odPenalExistVect = (Vector) odPenalExistList.get(0);
                odPenalExist = odPenalExistVect.get(0).toString();
            }  
            
            List stockPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_STOCK' ").getResultList();
            if(stockPenalExistList.size()>0){
                Vector stockPenalExistVect = (Vector) stockPenalExistList.get(0);
                stockPenalExist = stockPenalExistVect.get(0).toString();
            }
            List emiPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_EMI' ").getResultList();
            if(emiPenalExistList.size()>0){
                Vector emiPenalExistVect = (Vector) emiPenalExistList.get(0);
                emiPenalExist = emiPenalExistVect.get(0).toString();
            }
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            Date toDate = null;
            toDate = ymmd.parse(toDt);
            if (intOpt.equalsIgnoreCase("I")) {
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    if(securityPenalExist.equalsIgnoreCase("Y")){
                        List<LoanMultiList> resultList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                        if (resultList.size() > 0) {
//                            throw new ApplicationException("Data not found in SecurityPenalCalculation");
//                        } else {
                            lmlTemp = resultList;
//                            for (int i = 0; i < lmlTemp.size(); i++) {
                                LoanMultiList la1 = new LoanMultiList();
                                intProductDetail = lmlTemp.get(0).getLoanProductIntDetails();
                                intDetails = lmlTemp.get(0).getLoanIntDetails();
                                la1.setLoanIntDetails(intDetails);
                                la1.setLoanProductIntDetails(intProductDetail);
                                lml.add(la1);
//                            }
                        }
                    }
                    
                    if(stockPenalExist.equalsIgnoreCase("Y")){
                        List<LoanMultiList> resultList1 = stockPenalCalculation(acNo, fromDt, toDt, brnCode);
                        lmlTemp = new ArrayList<LoanMultiList>();
                        if (resultList1.size() > 0) {
//                        } else {
                            lmlTemp = resultList1;
//                            for (int i = 0; i < lmlTemp.size(); i++) {
                                LoanMultiList la2 = new LoanMultiList();
                                intProductDetail2 = lmlTemp.get(0).getLoanProductIntDetails();
                                intDetails = lmlTemp.get(0).getLoanIntDetails();
                                la2.setLoanIntDetails(intDetails);
                                la2.setLoanProductIntDetails(intProductDetail2);
                                lml.add(la2);
//                            }
                        }
                    }

                    if(odPenalExist.equalsIgnoreCase("Y")){
                        List<LoanMultiList> odResultList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                        lmlTemp = new ArrayList<LoanMultiList>();
                        if (odResultList.size() > 0) {
//                        } else {
                            lmlTemp = odResultList;
//                            for (int i = 0; i < lmlTemp.size(); i++) {
                                LoanMultiList la2 = new LoanMultiList();
                                intProductDetail2 = lmlTemp.get(0).getLoanProductIntDetails();
                                intDetails = lmlTemp.get(0).getLoanIntDetails();
                                la2.setLoanIntDetails(intDetails);
                                la2.setLoanProductIntDetails(intProductDetail2);
                                lml.add(la2);
//                            }
                        }
                    }
                    
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    List<LoanMultiList> resultList = new ArrayList<LoanMultiList>();
                    if(acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)){
                        if(emiPenalExist.equalsIgnoreCase("Y")){
                            resultList = repaymentPenalCalculationEMI(acNo, fromDt, toDt, brnCode);
                            lmlTemp = new ArrayList<LoanMultiList>();
                            if (resultList.size() > 0) {
//                            } else {
                                lmlTemp = resultList;
//                                for (int i = 0; i < lmlTemp.size(); i++) {
                                    LoanMultiList la3 = new LoanMultiList();
                                    intProductDetail = lmlTemp.get(0).getLoanProductIntDetails();
                                    intDetails = lmlTemp.get(0).getLoanIntDetails();
                                    la3.setLoanIntDetails(intDetails);
                                    la3.setLoanProductIntDetails(intProductDetail);
                                    lml.add(la3);
//                                }
                            }
                        }
                    }
                    if(acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)){
                        if(odPenalExist.equalsIgnoreCase("Y")){
                            resultList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                            lmlTemp = new ArrayList<LoanMultiList>();
                            if (resultList.size() > 0) {
//                            } else {
                                lmlTemp = resultList;
//                                for (int i = 0; i < lmlTemp.size(); i++) {
                                    LoanMultiList la3 = new LoanMultiList();
                                    intProductDetail = lmlTemp.get(0).getLoanProductIntDetails();
                                    intDetails = lmlTemp.get(0).getLoanIntDetails();
                                    la3.setLoanIntDetails(intDetails);
                                    la3.setLoanProductIntDetails(intProductDetail);
                                    lml.add(la3);
//                                }
                            }
                        }
                    }
                    
                    if(securityPenalExist.equalsIgnoreCase("Y")){
                        resultList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                        lmlTemp = new ArrayList<LoanMultiList>();
                        if (resultList.size() > 0) {
//                        } else {
                            lmlTemp = resultList;
//                            for (int i = 0; i < lmlTemp.size(); i++) {
                                LoanMultiList la4 = new LoanMultiList();
                                intProductDetail2 = lmlTemp.get(0).getLoanProductIntDetails();
                                intDetails = lmlTemp.get(0).getLoanIntDetails();
                                la4.setLoanIntDetails(intDetails);
                                la4.setLoanProductIntDetails(intProductDetail2);
                                lml.add(la4);
//                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return lml;
    }

    /*******************************  CHANGED ACCORDING TO PENAL PRODUCT CALCULATION ********************/
    public List<LoanMultiList> repaymentPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();

        String schemeCode;
        String intTableCode = null;
        int moratoriumPd;
        double accPrefDr = 0;
        double accPrefCr = 0;
        String rateCode = null;
        String disbType;
        String calcMethod = null;
        String calcOn;
        String intAppFreq = null;
        String calcLevel = null;
        String compoundFreq;
        int peggFreq = 0;
        int loanPdMonth;
        int loanPdDay;
        double rateOfInt = 0;
        double penalRoi;
        double sanctionLimit = 0;
        String firstDt = null;
        String lastDt = null;
        Date dmdAdjDt = null;
        Date dmdEffDt = null;
        double dmdAdjAmt = 0;
        double totalAmt = 0;
        double penalAmt = 0;
        double dmdAmt = 0;
        double overFlowAmt = 0;
        double closingBal = 0;
        double roi = 0;
        double product = 0;
        double penal = 0;
        try {
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, A.INT_CALC_UPTO_DT, ifnull(A.INT_COMP_TILL_DT,0), A.NEXT_INT_CALC_DT  from cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    String acno = (String) SecDetailsVect.get(0);
                    schemeCode = (String) SecDetailsVect.get(1);
                    intTableCode = (String) SecDetailsVect.get(2);
                    moratoriumPd = Integer.parseInt(SecDetailsVect.get(3).toString());
                    accPrefDr = Double.parseDouble(SecDetailsVect.get(4).toString());
                    accPrefCr = Double.parseDouble(SecDetailsVect.get(5).toString());
                    rateCode = (String) SecDetailsVect.get(6);
                    disbType = (String) SecDetailsVect.get(7);
                    calcMethod = (String) SecDetailsVect.get(8);
                    calcOn = (String) SecDetailsVect.get(9);
                    intAppFreq = (String) SecDetailsVect.get(10);
                    calcLevel = (String) SecDetailsVect.get(11);
                    compoundFreq = (String) SecDetailsVect.get(12);
                    peggFreq =  (SecDetailsVect.get(13) == null ? 0 : Integer.parseInt(SecDetailsVect.get(13).toString()));
                    loanPdMonth = Integer.parseInt(SecDetailsVect.get(14).toString());
                    loanPdDay = Integer.parseInt(SecDetailsVect.get(15).toString());
                    rateOfInt = Double.parseDouble(SecDetailsVect.get(16).toString());
                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());


                }
            }
            String custName = null;
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for account number " + acNo);
            }

            List getDmdList = em.createNativeQuery("select ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_SRL_NUM,DMD_EFF_DATE,DMD_OVDU_DATE,DMD_AMT from cbs_loan_dmd_table  where acno = '" + acNo + "' and DMD_EFF_DATE between '" + fromDt + "' and '" + toDt + "' and DEL_FLG = 'N' order by acno, DMD_EFF_DATE").getResultList();
            int noOfDays = 0;

            if (getDmdList.size() > 0) {
                for (int i = 0; i < getDmdList.size(); i++) {
                    LoanIntCalcList itProduct = new LoanIntCalcList();
                    try {
                        Vector getDmdVect = (Vector) getDmdList.get(i);
                        String dmdAcNo = getDmdVect.get(0).toString();
                        int dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                        String dmdFlowId = getDmdVect.get(2).toString();
                        int dmdSrNo = Integer.parseInt(getDmdVect.get(3).toString());
                        dmdEffDt = ymd.parse(getDmdVect.get(4).toString());
                        Date dmdOvrDt = ymd.parse(getDmdVect.get(5).toString());
                        dmdAmt = Double.parseDouble(getDmdVect.get(6).toString());
                        List getLoanAdditionalTrList = em.createNativeQuery("select OVERFLOW_AMT from cbs_la_additional_tr_details  where acno = '" + dmdAcNo + "' and REPAY_SCH_NO = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and INT_ROUTING_FLAG = 'L' and TR_REVERSAL_FLAG = 'N'").getResultList();
                        if (getLoanAdditionalTrList.size() > 0) {
                            Vector getLoanAdditionalTrVect = (Vector) getLoanAdditionalTrList.get(i);
                            overFlowAmt = Double.parseDouble(getLoanAdditionalTrVect.get(1).toString());
                        } else {
                            overFlowAmt = 0f;
                        }
                        List getDmdAdjList = em.createNativeQuery("select ADJ_DATE,ADJ_AMT from cbs_loan_dmd_adj_table  where acno = '" + dmdAcNo + "' and SHDL_NUM = " + dmdSchNo + " and  DMD_FLOW_ID = '" + dmdFlowId + "' and DMD_SRL_NUM = " + dmdSrNo + " and DEL_FLG = 'N'").getResultList();
                        if (getDmdAdjList.size() > 0) {
                            for (int j = 0; j <= getDmdAdjList.size(); j++) {
                                if (j == getDmdAdjList.size() - 1) {
                                    Vector getDmdAdjVect = (Vector) getDmdAdjList.get(j);
                                    dmdAdjDt = ymd.parse(getDmdAdjVect.get(0).toString());
                                    dmdAdjAmt = Double.parseDouble(getDmdAdjVect.get(1).toString());
                                    totalAmt = dmdAmt - dmdAdjAmt - overFlowAmt;
                                    if (dmdAdjDt.before(dmdEffDt) && totalAmt > 0) {
                                        firstDt = dmy.format(dmdEffDt);
                                        dmdAdjDt = ymd.parse(toDt);
                                        String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(dmdAdjDt));
                                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                            throw new ApplicationException("Penal Roi is not found");
                                        } else {
                                            roi = Double.parseDouble(intRoi);
                                        }

                                        Long day = (dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                        noOfDays = day.intValue() + 1;

                                        penalAmt = roi * noOfDays * totalAmt / 36500;
                                        product = product + dmdAmt;
                                        penal = penal + penalAmt;
                                        j++;
                                    } else if (dmdAdjDt.equals(dmdEffDt) && totalAmt > 0) {
                                        firstDt = dmy.format(dmdEffDt);
                                        dmdAdjDt = ymd.parse(toDt);
                                        String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(dmdAdjDt));
                                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                            throw new ApplicationException("Penal Roi is not found");
                                        } else {
                                            roi = Double.parseDouble(intRoi);
                                        }

                                        Long day = (dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                        noOfDays = day.intValue() + 1;
                                        penalAmt = roi * noOfDays * totalAmt / 36500;
                                        product = product + dmdAmt;
                                        penal = penal + penalAmt;
                                        j++;
                                    } else if (dmdAdjDt.after(dmdEffDt) && totalAmt > 0) {
                                        firstDt = dmy.format(dmdEffDt);
                                        dmdAdjDt = dmdAdjDt;
                                        String intRoi = getPenalROI(intTableCode, (dmdAmt - overFlowAmt), ymd.format(dmdAdjDt));
                                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                            throw new ApplicationException("Penal Roi is not found");
                                        } else {
                                            roi = Double.parseDouble(intRoi);
                                        }
                                        Long day = (dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                        noOfDays = day.intValue() + 1;
                                        penalAmt = roi * noOfDays * (dmdAmt - overFlowAmt) / 36500;
                                        product = product + dmdAmt;
                                        penal = penal + penalAmt;
                                    }
                                } else {
                                    if (dmdAdjDt.before(ymd.parse(toDt))) {
                                        lastDt = dmy.format(ymd.parse(toDt));
                                        String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(ymd.parse(toDt)));
                                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                            throw new ApplicationException("Penal Roi is not found");
                                        } else {
                                            roi = Double.parseDouble(intRoi);
                                        }
                                        Long day = (ymd.parse(toDt).getTime() - dmdAdjDt.getTime()) / (24 * 60 * 60 * 1000);
                                        noOfDays = day.intValue() + 1;
                                        penalAmt = roi * noOfDays * totalAmt / 36500;
                                        product = product + totalAmt;
                                        penal = penal + penalAmt;
                                    }
                                }
                                itProduct.setAcNo(acNo);
                                itProduct.setCustName(custName);
//                                itProduct.setFirstDt(dmy.format(firstDt));
//                                itProduct.setLastDt(dmy.format(lastDt));
                                itProduct.setFirstDt(dmy.format(dmy.parse(firstDt)));
                                itProduct.setLastDt(dmy.format(dmy.parse(lastDt)));
                                itProduct.setRoi(roi);
                                itProduct.setProduct(Double.parseDouble(formatter.format(new Double(totalAmt * noOfDays))));
                                itProduct.setClosingBal(noOfDays);
                                itProduct.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penalAmt)))));
                                itProduct.setIntTableCode(intTableCode);
                                itProduct.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                                intProductDetail.add(itProduct);
                            }

                        } else {
                            firstDt = dmy.format(dmdEffDt);
                            lastDt = dmy.format(ymd.parse(toDt));
                            dmdAdjDt = ymd.parse(toDt);
                            dmdAdjAmt = 0f;
                            totalAmt = dmdAmt - dmdAdjAmt - overFlowAmt;
                            String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(ymd.parse(toDt)));
                            if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                throw new ApplicationException("Penal Roi is not found");
                            } else {
                                roi = Double.parseDouble(intRoi);
                            }
                            Long day = (ymd.parse(toDt).getTime() - dmdAdjDt.getTime()) / (24 * 60 * 60 * 1000);
                            noOfDays = day.intValue() + 1;
                            penalAmt = roi * noOfDays * totalAmt / 36500;
                            product = product + totalAmt;
                            penal = penal + penalAmt;
                            itProduct.setAcNo(acNo);
                            itProduct.setCustName(custName);
                            itProduct.setFirstDt(firstDt);
                            itProduct.setLastDt(lastDt);
                            itProduct.setRoi(rateOfInt);
                            itProduct.setProduct(Double.parseDouble(formatter.format(new Double(totalAmt * noOfDays))));
                            itProduct.setClosingBal(noOfDays);
                            itProduct.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penalAmt)))));
                            itProduct.setIntTableCode(intTableCode);
                            itProduct.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                            intProductDetail.add(itProduct);
                        }
                        product = product + totalAmt;
                    } catch (Exception e) {
                        throw new ApplicationException(e);
                    }
                }
                NumberFormat formatter = new DecimalFormat("#0.00");
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penal)))));
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                intDetails.add(it);
                la.setLoanIntDetails(intDetails);
                la.setLoanProductIntDetails(intProductDetail);
                lml.add(la);
                //result = lml;
            } else {
                LoanIntCalcList itProduct = new LoanIntCalcList();
                itProduct.setAcNo(acNo);
                itProduct.setCustName(custName);
                itProduct.setFirstDt(firstDt);
                itProduct.setLastDt(firstDt);
                itProduct.setRoi(roi);
                itProduct.setProduct(Double.parseDouble(formatter.format(new Double(totalAmt * noOfDays))));
                itProduct.setClosingBal(noOfDays);
                itProduct.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penalAmt)))));
                itProduct.setIntTableCode(intTableCode);
                itProduct.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                intProductDetail.add(itProduct);
                product = product + totalAmt;

                NumberFormat formatter = new DecimalFormat("#0.00");
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penal)))));
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                intDetails.add(it);
                la.setLoanIntDetails(intDetails);
                la.setLoanProductIntDetails(intProductDetail);
                lml.add(la);
                //result = lml;
            }
            return lml;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<LoanMultiList> repaymentPenalCalculationEMI(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        try {
            List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it = new LoanIntCalcList();
            List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
            LoanMultiList la = new LoanMultiList();
            List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
            
            String intTableCode = "", schemeCode = "", lateFeeApp = "Y";
            Integer gracePdMonth = 0, gracePdDays= 0;
            String firstDt = dmy.format(ymmd.parse(fromDt));
            String lastDt = dmy.format(ymmd.parse(toDt));
            Date dmdAdjDt = null;
            Date dmdEffDt = null;
            double dmdAdjAmt = 0;
            double totalAmt = 0;
            double penalAmt = 0;
            double dmdAmt = 0;
            double overFlowAmt = 0;
            double closingBal = 0;
            double roi = 0;
            double product = 0;
            double penal = 0;
            int noOfDays = 0;

            List SecDetailsList = em.createNativeQuery("SELECT A.INTEREST_TABLE_CODE, A.SCHEME_CODE from cbs_loan_acc_mast_sec A, loan_appparameter B "
                    + "where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    intTableCode = SecDetailsVect.get(0).toString();                    
                    schemeCode = SecDetailsVect.get(1).toString();
                }
            }
            List emiLateApplicableList = em.createNativeQuery("select apply_late_fee_for_delayed_payment, grace_period_for_late_fee_months, "
                    + " grace_period_for_late_fee_days from cbs_scheme_loan_interest_details where scheme_code='" + schemeCode + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector emiLateApplicableVect = (Vector) emiLateApplicableList.get(i);
                    lateFeeApp   = emiLateApplicableVect.get(0).toString();
                    gracePdMonth = Integer.parseInt(emiLateApplicableVect.get(1).toString());
                    gracePdDays  = Integer.parseInt(emiLateApplicableVect.get(2).toString());
                }
            }
            String custName = null;
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for account number " + acNo);
            }
            
            /** Getting the Actual Demand Posted in Table*/
            List getNonPaidList = em.createNativeQuery("select DATE_FORMAT(ifnull(duedt,'"+ymmd.format(ymmd.parse(toDt)) +"'), '%Y%m%d') as duedt, DATE_FORMAT(ifnull(paymentdt,'"+ymmd.format(ymmd.parse(toDt)) +"'), '%Y%m%d') as paymentdt, installamt, status, excessamt from emidetails where "
                    + " acno='" + acNo + "' and status = 'unpaid' and DATE_FORMAT(duedt, '%Y%m%d')<='" + toDt+"' "
//                    + " and paymentdt is null "
//                    + " union all "
//                    + " select duedt, paymentdt, installamt, status, excessamt from emidetails where acno='" + acNo + "' "
//                    + " and status = 'unpaid' and duedt<='" + toDt+"' ' and paymentdt is not null "
                    + " union all "
                    + " select DATE_FORMAT(ifnull(duedt,'"+ymmd.format(ymmd.parse(toDt)) +"'), '%Y%m%d') as duedt, DATE_FORMAT(ifnull(paymentdt,'"+ymmd.format(ymmd.parse(toDt)) +"'), '%Y%m%d') as paymentdt, installamt, status, excessamt from emidetails where acno='" + acNo + "' "
                    + " and status = 'paid' and DATE_FORMAT(duedt, '%Y%m%d')<='" + toDt+"'  and DATE_FORMAT(paymentdt, '%Y%m%d') between '" + fromDt + "' and '" + toDt+"' "
                    + " order by duedt ").getResultList();
       
            if (getNonPaidList.size() > 0) {
                for (int i = 0; i < getNonPaidList.size(); i++) {
                    try {
                        LoanIntCalcList itProduct = new LoanIntCalcList();
                        Vector getDmdVect = (Vector) getNonPaidList.get(i);
                        String dueDt = getDmdVect.get(0).toString();
                        String actDueDt = getDmdVect.get(0).toString();
                        String pymtDt = getDmdVect.get(1).toString();
                        double installmentAmt = Double.parseDouble(getDmdVect.get(2).toString());
                        String status = getDmdVect.get(3).toString();
                        double excessAmt = Double.parseDouble(getDmdVect.get(4).toString());
                        noOfDays = 0;
                        System.out.println("acno:"+acNo+"; dueDt:"+dueDt+"; pymtDt:"+pymtDt);
                        if(status.equalsIgnoreCase("paid")){
                            if(ymmd.parse(pymtDt).after(ymmd.parse(dueDt))) {
                                totalAmt = installmentAmt;                                
                                String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(ymmd.parse(pymtDt)));
                                if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                    throw new ApplicationException("Penal Roi is not found");
                                } else {
                                    roi = Double.parseDouble(intRoi);
                                }

                                if(lateFeeApp.equalsIgnoreCase("Y")){
                                    dueDt = CbsUtil.monthAdd(ymmd.format(ymmd.parse(dueDt)), gracePdMonth);
                                    dueDt = CbsUtil.dateAdd(dueDt, gracePdDays);
                                    
                                } else{
                                    dueDt = ymmd.format(ymmd.parse(dueDt));
                                }   
//                                Long day = CbsUtil.dayDiff(ymd.parse(dueDt), ymd.parse(pymtDt));//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                Long day = CbsUtil.dayDiff(ymmd.parse(dueDt).before(ymmd.parse(fromDt))?ymmd.parse(fromDt):ymmd.parse(dueDt), ymmd.parse(pymtDt).after(ymmd.parse(toDt))?ymmd.parse(toDt):ymmd.parse(pymtDt));//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                if(day >0){
                                    noOfDays = day.intValue() + 1;

                                    penalAmt = roi * noOfDays * totalAmt / 36500;
                                    product = product + noOfDays * totalAmt;
                                    penal = penal + penalAmt;
                                }
                            } 
                        } else {
                            totalAmt = installmentAmt;                                
                            String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(ymmd.parse(pymtDt)));
                            if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                throw new ApplicationException("Penal Roi is not found");
                            } else {
                                roi = Double.parseDouble(intRoi);
                            }

                            if(lateFeeApp.equalsIgnoreCase("Y")){
                                dueDt = CbsUtil.monthAdd(ymmd.format(ymmd.parse(dueDt)), gracePdMonth);
                                dueDt = CbsUtil.dateAdd(dueDt, gracePdDays);

                            } else{
                                dueDt = ymmd.format(ymmd.parse(dueDt));
                            }   
//                                Long day = CbsUtil.dayDiff(ymd.parse(dueDt), ymd.parse(pymtDt));//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                            Long day = CbsUtil.dayDiff(ymmd.parse(dueDt).before(ymmd.parse(fromDt))?ymmd.parse(fromDt):ymmd.parse(dueDt), ymmd.parse(pymtDt).after(ymmd.parse(toDt))?ymmd.parse(toDt):ymmd.parse(pymtDt));//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);

                            if(day>0){
                                noOfDays = day.intValue() + 1;

                                penalAmt = roi * noOfDays * totalAmt / 36500;
                                product = product + noOfDays * totalAmt;
                                penal = penal + penalAmt;
                            }
                        }
                        itProduct.setAcNo(acNo);
                        itProduct.setCustName(custName);
//                                itProduct.setFirstDt(dmy.format(firstDt));
//                                itProduct.setLastDt(dmy.format(lastDt));
                        itProduct.setFirstDt(dmy.format(ymmd.parse(dueDt).before(ymmd.parse(fromDt))?ymmd.parse(fromDt):ymmd.parse(dueDt)));
                        itProduct.setLastDt(dmy.format(ymmd.parse(pymtDt).after(ymmd.parse(toDt))?ymmd.parse(toDt):ymmd.parse(pymtDt)));
                        itProduct.setRoi(roi);
                        itProduct.setProduct(Double.parseDouble(formatter.format(new Double(totalAmt * noOfDays))));
                        itProduct.setClosingBal(noOfDays);
                        itProduct.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penalAmt)))));
                        itProduct.setIntTableCode(intTableCode);
                        itProduct.setDetails("Repayment penal of EMI Dut Dt " + dmy.format(ymmd.parse(actDueDt))+" with Grace Perid:(Month):"+gracePdMonth+",(Day):"+gracePdDays);// + " to " + dmy.format(ymd.parse(pymtDt)));
                        intProductDetail.add(itProduct);
                        
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        it.setAcNo(acNo);
                        it.setCustName(custName);
                        it.setClosingBal(closingBal);
                        it.setFirstDt(dmy.format(ymmd.parse(dueDt)));
                        it.setLastDt(dmy.format(ymmd.parse(pymtDt)));
                        it.setProduct(Double.parseDouble(formatter.format(new Double(totalAmt * noOfDays))));
                        it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penalAmt)))));
                        it.setRoi(roi);
                        it.setIntTableCode(intTableCode);
                        it.setDetails("Repayment penal of EMI Dut Dt " + dmy.format(ymmd.parse(dueDt)));
                        intDetails.add(it);
                        la.setLoanIntDetails(intDetails);
                        la.setLoanProductIntDetails(intProductDetail);
                        lml.add(la);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                        throw new ApplicationException(ex.getMessage());
                    }
                }
                
            } else {
                //NumberFormat formatter = new DecimalFormat("#0.00");
                LoanIntCalcList itProduct = new LoanIntCalcList();
                itProduct.setAcNo(acNo);
                itProduct.setCustName(custName);
                itProduct.setFirstDt(fromDt);
                itProduct.setLastDt(toDt);
                itProduct.setRoi(roi);
                itProduct.setProduct(totalAmt * noOfDays);
                itProduct.setClosingBal(noOfDays);
                itProduct.setTotalInt(penalAmt);
                itProduct.setIntTableCode(intTableCode);
                itProduct.setDetails("Repayment penal between " + fromDt + " to " + toDt);
                intProductDetail.add(itProduct);
                product = product + totalAmt;

                NumberFormat formatter = new DecimalFormat("#0.00");
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(fromDt);
                it.setLastDt(toDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(totalAmt * noOfDays))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(penalAmt))));
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Repayment penal between " + fromDt + " to " + toDt);
                intDetails.add(it);
                la.setLoanIntDetails(intDetails);
                la.setLoanProductIntDetails(intProductDetail);
                lml.add(la);
            }
            return lml;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
    
    /*******************************************************************************************/
    public List<LoanMultiList> securityPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();

        String schemeCode;
        String intTableCode = "";
        int moratoriumPd;
        double accPrefDr = 0;
        double accPrefCr = 0;
        String rateCode = "";
        String disbType;
        String calcMethod = "";
        String calcOn;
        String intAppFreq = "";
        String calcLevel = "";
        String compoundFreq;
        int peggFreq = 0;
        int loanPdMonth;
        int loanPdDay;
        double rateOfInt = 0;
        double penalRoi;
        double sanctionLimit = 0;
        String firstDisbDt = "";
        String acNature = "";
        String firstDt = "";
        String lastDt = "";
        double totalPenalAmt = 0f;
        double product = 0d;
        String custName = "";
        try {
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for account number " + acNo);
            }
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, A.INT_CALC_UPTO_DT, ifnull(A.INT_COMP_TILL_DT,0), A.NEXT_INT_CALC_DT  from cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    String acno = (String) SecDetailsVect.get(0);
                    schemeCode = (String) SecDetailsVect.get(1);
                    intTableCode = (String) SecDetailsVect.get(2);
                    moratoriumPd = Integer.parseInt(SecDetailsVect.get(3).toString());
                    accPrefDr = Double.parseDouble(SecDetailsVect.get(4).toString());
                    accPrefCr = Double.parseDouble(SecDetailsVect.get(5).toString());
                    rateCode = (String) SecDetailsVect.get(6);
                    disbType = (String) SecDetailsVect.get(7);
                    calcMethod = (String) SecDetailsVect.get(8);
                    calcOn = (String) SecDetailsVect.get(9);
                    intAppFreq = (String) SecDetailsVect.get(10);
                    calcLevel = (String) SecDetailsVect.get(11);
                    compoundFreq = (String) SecDetailsVect.get(12);
                    peggFreq =  (SecDetailsVect.get(13) == null ? 0 : Integer.parseInt(SecDetailsVect.get(13).toString()));
                    loanPdMonth = Integer.parseInt(SecDetailsVect.get(14).toString());
                    loanPdDay = Integer.parseInt(SecDetailsVect.get(15).toString());
                    rateOfInt = Double.parseDouble(SecDetailsVect.get(16).toString());
                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                }
            }
            double outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, toDt));
            if (outstanding < 0) {
                outstanding = outstanding * -1;
            } else {
                outstanding = outstanding * 1;
            }
            acNature = ftsPosting.getAccountNature(acNo);

            double matValue = 0;
            double margine;
            double dpLimit = 0;
            double totalDpLimit = 0;
            double closingBal = 0;
            double roi = 0;
            double noOfDays = 0;
            long penalAmt = 0;
            if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                    || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                List margineList = em.createNativeQuery("Select MatValue,Margin From loansecurity  Where acno='" + acNo + "'  and UPPER(status) = 'ACTIVE' Order By acno,Sno").getResultList();
                if (margineList.size() > 0) {
                    for (int i = 0; i < margineList.size(); i++) {
                        Vector margineVect = (Vector) margineList.get(0);
                        matValue = Double.parseDouble(margineVect.get(0).toString());
                        margine = Double.parseDouble(margineVect.get(1).toString());
                        dpLimit = matValue - (matValue * margine / 100);
                        totalDpLimit = totalDpLimit + dpLimit;
                    }
                } else {
                    dpLimit = 0f;
                    totalDpLimit = totalDpLimit + dpLimit;
                }
                if (outstanding > totalDpLimit) {
                    LoanIntCalcList itProduct = new LoanIntCalcList();
                    try {
                        outstanding = outstanding - dpLimit;
                        if (outstanding > 0) {
                            String intRoi = getPenalROI(intTableCode, outstanding, toDt);
                            if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                throw new ApplicationException("Penal Roi is not found");
                            } else {
                                roi = Double.parseDouble(intRoi);
                            }
                        }
                        Date frDt = ymd.parse(fromDt);
                        Date tillDt = ymd.parse(toDt);
                        Long day = (tillDt.getTime() - frDt.getTime()) / (24 * 60 * 60 * 1000);
                        noOfDays = day.doubleValue() + 1;
                        penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
                        totalPenalAmt = totalPenalAmt + penalAmt;

                        itProduct.setAcNo(acNo);
                        itProduct.setCustName(custName);
                        itProduct.setFirstDt(dmy.format(frDt));
                        itProduct.setLastDt(dmy.format(tillDt));
                        itProduct.setRoi(roi);
                        itProduct.setProduct(outstanding * noOfDays);
                        itProduct.setClosingBal(noOfDays);
                        itProduct.setTotalInt(penalAmt);
                        itProduct.setIntTableCode(intTableCode);
                        itProduct.setDetails("Security penal between " + dmy.format(frDt) + " to " + dmy.format(tillDt));
                        intProductDetail.add(itProduct);
                        product = product + outstanding;

                        NumberFormat formatter = new DecimalFormat("#0.00");
                        it.setAcNo(acNo);
                        it.setCustName(custName);
                        it.setClosingBal(closingBal);
                        it.setFirstDt(dmy.format(frDt));
                        it.setLastDt(dmy.format(tillDt));
                        it.setProduct(Double.parseDouble(formatter.format(new Double(outstanding))));
                        it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                        it.setRoi(roi);
                        it.setIntTableCode(intTableCode);
                        it.setDetails("Security penal between " + dmy.format(frDt) + " to " + dmy.format(tillDt));
                        intDetails.add(it);
                        la.setLoanIntDetails(intDetails);
                        la.setLoanProductIntDetails(intProductDetail);
                        lml.add(la);
//                        result = lml;
                    } catch (ParseException ex) {
                        Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    LoanIntCalcList itProduct = new LoanIntCalcList();
                    itProduct.setAcNo(acNo);
                    itProduct.setCustName(custName);
                    itProduct.setFirstDt(fromDt);
                    itProduct.setLastDt(toDt);
                    itProduct.setRoi(roi);
                    itProduct.setProduct(outstanding * noOfDays);
                    itProduct.setClosingBal(noOfDays);
                    itProduct.setTotalInt(penalAmt);
                    itProduct.setIntTableCode(intTableCode);
                    itProduct.setDetails("Security penal between " + fromDt + " to " + toDt);
                    intProductDetail.add(itProduct);
                    product = product + outstanding;

                    NumberFormat formatter = new DecimalFormat("#0.00");
                    it.setAcNo(acNo);
                    it.setCustName(custName);
                    it.setClosingBal(closingBal);
                    it.setFirstDt(fromDt);
                    it.setLastDt(toDt);
                    it.setProduct(Double.parseDouble(formatter.format(new Double(outstanding))));
                    it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                    it.setRoi(roi);
                    it.setIntTableCode(intTableCode);
                    it.setDetails("Security penal between " + fromDt + " to " + toDt);
                    intDetails.add(it);
                    la.setLoanIntDetails(intDetails);
                    la.setLoanProductIntDetails(intProductDetail);
                    lml.add(la);
                    //result = lml;
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List entryDtList = em.createNativeQuery("Select entryDate From loansecurity Where acno='" + acNo + "'  and UPPER(status) = 'ACTIVE' and EntryDate between '" + fromDt + "' and '" + toDt + "' group by entrydate").getResultList();
                try {
                    String a[][] = new String[entryDtList.size()][2];
                    ArrayList datesFrom = new ArrayList();
                    datesFrom.add(ymd.format(ymd.parse(fromDt)));
                    if (!entryDtList.isEmpty()) {
                        for (int i = 0; i < entryDtList.size(); i++) {
                            Vector entryDtVect = (Vector) entryDtList.get(i);
                            a[i][0] = entryDtVect.get(0).toString();
                            if (i == 0) {
                                /*=======Getting the ROI Change Date Between FromDt and  i Position Date=======================*/
                                if (ymmd.parse(fromDt).equals(ymd.parse(entryDtVect.get(0).toString()))) {
                                    if (i == entryDtList.size() - 1) {
                                        a[i][1] = ymd.format(ymmd.parse(toDt));
                                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                        datesFrom.add(a[i][1]);
                                    }
                                } else {
                                    datesFrom.add(a[i][0]);
                                    if (i == entryDtList.size() - 1) {
                                        a[i][1] = ymd.format(ymmd.parse(toDt));
                                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                        datesFrom.add(a[i][1]);
                                    }
                                }
                            } else if (i > 0 && i < entryDtList.size() - 1) {
                                a[i - 1][1] = a[i][0];
                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                datesFrom.add(a[i][0]);
                            } else if (i == entryDtList.size() - 1) {
                                a[i - 1][1] = a[i][0];
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                datesFrom.add(a[i][0]);
                                if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
                                    /*=======Getting the ROI Change Date in Current i Position=======================*/
                                    datesFrom.add(a[i][1]);
                                }
                            }
                        }
                        Collections.sort(datesFrom);
                    } else {
                        datesFrom.add(ymd.format(ymd.parse(toDt)));
                        Collections.sort(datesFrom);
                    }

                    String b[][] = new String[datesFrom.size()][2];
                    if (!datesFrom.isEmpty()) {
                        for (int i = 0; i < datesFrom.size(); i++) {
                            if (i == 0) {
                                b[i][0] = datesFrom.get(i).toString();
                                firstDisbDt = datesFrom.get(i).toString();
                            } else if (i > 0 && i < datesFrom.size() - 1) {
                                b[i][0] = datesFrom.get(i).toString();
                                b[i - 1][1] = ymd.format(ymmd.parse(dateAdd(ymmd.format(ymd.parse(b[i][0])), -1)));
                            } else if (i == datesFrom.size() - 1) {
                                b[i - 1][1] = datesFrom.get(i).toString();
                            }
                        }
                    }

                    product = 0;
                    LoanIntCalcList itProduct = new LoanIntCalcList();
                    for (int i = 0; i < b.length - 1; i++) {
                        String fDate = b[i][0].toString();
                        String tDate = b[i][1].toString();
                        List margineList = em.createNativeQuery("Select MatValue,Margin From loansecurity Where acno='" + acNo + "' and UPPER(status) = 'ACTIVE' and EntryDate between '" + fDate + "' and '" + tDate + "' Order By acno,Sno").getResultList();
                        if (margineList.size() > 0) {
                            for (int j = 0; j < margineList.size(); j++) {
                                Vector margineVect = (Vector) margineList.get(0);
                                matValue = Double.parseDouble(margineVect.get(0).toString());
                                margine = Double.parseDouble(margineVect.get(1).toString());
                                dpLimit = matValue - (matValue * margine / 100);
                                totalDpLimit = totalDpLimit + dpLimit;
                            }
                        } else {
                            dpLimit = 0f;
                            totalDpLimit = totalDpLimit + dpLimit;
                        }
                        outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, toDt));
                        if (outstanding < 0) {
                            outstanding = outstanding * -1;
                        } else {
                            outstanding = outstanding * 1;
                        }

                        if (outstanding > totalDpLimit) {

                            try {
                                outstanding = outstanding - dpLimit;
                                if (outstanding > 0) {
                                    String intRoi = getPenalROI(intTableCode, outstanding, toDt);
                                    if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                        it.setFlag("false;" + intRoi);
                                        intProductDetail.add(it);
                                        la.setLoanIntDetails(intDetails);
                                        la.setLoanProductIntDetails(intProductDetail);
                                        lml.add(la);
                                        //result = lml;
                                        return lml;
                                    } else {
                                        roi = Double.parseDouble(intRoi);
                                    }
                                }
                                Date frDt = ymd.parse(fDate);
                                Date tillDt = ymd.parse(tDate);
                                Long day = (tillDt.getTime() - frDt.getTime()) / (24 * 60 * 60 * 1000);
                                noOfDays = day.intValue() + 1;

                                penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
                                totalPenalAmt = totalPenalAmt + penalAmt;
                                product = product + outstanding * noOfDays;
                                if (i == 0) {
                                    firstDt = dmy.format(frDt);
                                }
                                if (i == b.length - 2) {
                                    lastDt = dmy.format(tillDt);
                                    closingBal = outstanding;
                                }

                                itProduct.setAcNo(acNo);
                                itProduct.setCustName(custName);
                                itProduct.setFirstDt(firstDt);
                                itProduct.setLastDt(lastDt);
                                itProduct.setRoi(roi);
                                itProduct.setProduct(outstanding * noOfDays);
                                itProduct.setClosingBal(noOfDays);
                                itProduct.setTotalInt(penalAmt);
                                itProduct.setIntTableCode(intTableCode);
                                itProduct.setDetails("Security penal between " + firstDt + " to " + lastDt);
                                intProductDetail.add(itProduct);
                                product = product + outstanding;
                            } catch (ParseException ex) {
                                Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    NumberFormat formatter = new DecimalFormat("#0.00");
                    it.setAcNo(acNo);
                    it.setCustName(custName);
                    it.setClosingBal(closingBal);
                    it.setFirstDt(firstDt);
                    it.setLastDt(lastDt);
                    it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                    it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                    it.setRoi(roi);
                    it.setIntTableCode(intTableCode);
                    it.setDetails("Security penal between " + firstDt + " to " + lastDt);
                    intDetails.add(it);
                    la.setLoanIntDetails(intDetails);
                    la.setLoanProductIntDetails(intProductDetail);
                    lml.add(la);
                    //result = lml;
                } catch (Exception e) {
                    throw new ApplicationException(e.getMessage());
                }
            }
            return lml;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    /*******************************************************************************************/
    public List<LoanMultiList> odPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();
        
        String intTableCode = "";
        String acNature = "";
        String firstDt = "";
        String lastDt = "";
        String intAppFreq = "";
        double totalPenalAmt = 0d, subsidyAmt = 0d, product = 0d;
        String subsidyExpDt = "";
        String custName = null;
            
        try {
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for account number " + acNo);
            }
            List SecDetailsList = em.createNativeQuery("SELECT A.INTEREST_TABLE_CODE, A.INT_APP_FREQ, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,'') from cbs_loan_acc_mast_sec A, loan_appparameter B "
                    + "where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();

            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account No Does Not Exists in Secondary Details table of Loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    intTableCode = (String) SecDetailsVect.get(0);
                    intAppFreq = (String) SecDetailsVect.get(1);
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(2).toString());
                    subsidyExpDt = SecDetailsVect.get(3).toString();
                }
            }
            acNature = ftsPosting.getAccountNature(acNo);
            String[][] b = loanInterestCalculationBean.createFromDtArray(acNo, fromDt, toDt, intAppFreq, acNature, intTableCode,"N","Y");
            
            for (int j = 0; j < b.length - 1; j++) {
                String fDate = b[j][0].toString();
                String tDate = b[j][1].toString();
                double outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, tDate));
                /*
                     * Did the implementation of Subsidy Amount 
                     */
                if ((subsidyAmt != 0)) {
                    if (ymd.parse(subsidyExpDt).after(ymd.parse(fDate)) || ymd.parse(subsidyExpDt).equals(ymd.parse(fDate))) {
                        if(outstanding<0){
                            outstanding = outstanding + subsidyAmt;                        
                        }                        
                    }
                }
                if (outstanding < 0) {
                    outstanding = outstanding * -1;
                } else {
                    outstanding = outstanding * 1;
                }
                
                double matValue = 0;
                double margine;
                double dpLimit = 0;
                double adhocLimit = 0;
                double totalDpLimit = 0;
                double closingBal = 0;
                double roi = 0;
                double noOfDays = 0;
                long penalAmt = 0;
                if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List margineList = em.createNativeQuery("select ifnull(acLimit,0), IFNULL(Adhocapplicabledt,'1900-01-01'), IFNULL(adhoctilldt,'1900-01-01'), ifnull(Adhoclimit,0) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + fDate + "' and txnid = (select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + fDate + "')").getResultList();
                    if (margineList.isEmpty()) {
                        margineList = em.createNativeQuery("Select IFNULL(odlimit,0), IFNULL(Adhocapplicabledt,'1900-01-01'), IFNULL(AdhocExpiry,'1900-01-01'), ifnull(Adhoclimit,0) From loan_appparameter  Where acno='" + acNo + "' ").getResultList();
                    }
                    if (margineList.size() > 0) {
                        for (int i = 0; i < margineList.size(); i++) {
                            Vector margineVect = (Vector) margineList.get(0);
                            matValue = Double.parseDouble(margineVect.get(0).toString());
                            if (Double.parseDouble(margineVect.get(3).toString()) > 0) {
                                if ((ymd.parse(fDate).after(ymd.parse(margineVect.get(1).toString()))) && (!ymd.parse(fDate).after(ymd.parse(margineVect.get(2).toString())))) {
                                    adhocLimit = Double.parseDouble(margineVect.get(3).toString());
                                }
                            }
    //                        margine = Double.parseDouble(margineVect.get(1).toString());
                            dpLimit = matValue + adhocLimit;//- (matValue * margine / 100);
                            totalDpLimit = totalDpLimit + dpLimit;
                        }
                    } else {
                        dpLimit = 0f;
                        totalDpLimit = totalDpLimit + dpLimit;
                    }
                    if (outstanding > totalDpLimit) {
                        LoanIntCalcList itProduct = new LoanIntCalcList();
                        try {
                            outstanding = outstanding - totalDpLimit;
                            if (outstanding > 0) {
                                String intRoi = getPenalROI(intTableCode, outstanding, toDt);
                                if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                    throw new ApplicationException("Roi does not exist");
                                } else {
                                    roi = Double.parseDouble(intRoi);
                                }
                            }
                            System.out.println("fDate:"+fDate+";tDate:"+tDate);
                            Date frDt = ymd.parse(fDate);
                            Date tillDt = ymd.parse(tDate);
                            System.out.println("frDt:"+frDt+";tillDt:"+tillDt);
                            Long day = CbsUtil.dayDiff(frDt,tillDt);//(tillDt.getTime() - frDt.getTime()) / (24 * 60 * 60 * 1000);
                            noOfDays = day.intValue() + 1;
                            penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
                            totalPenalAmt = totalPenalAmt + penalAmt;

                            itProduct.setAcNo(acNo);
                            itProduct.setCustName(custName);
                            itProduct.setFirstDt(dmy.format(frDt));
                            itProduct.setLastDt(dmy.format(tillDt));
                            itProduct.setRoi(roi);
                            itProduct.setProduct(outstanding * noOfDays);
                            itProduct.setClosingBal(noOfDays);
                            itProduct.setTotalInt(penalAmt);
                            itProduct.setIntTableCode(intTableCode);
                            itProduct.setDetails("Over Limit penal between " + dmy.format(frDt) + " to " + dmy.format(tillDt));
                            intProductDetail.add(itProduct);
                            product = product + outstanding;

                            NumberFormat formatter = new DecimalFormat("#0.00");
                            it.setAcNo(acNo);
                            it.setCustName(custName);
                            it.setClosingBal(closingBal);
                            it.setFirstDt(dmy.format(frDt));
                            it.setLastDt(dmy.format(tillDt));
                            it.setProduct(Double.parseDouble(formatter.format(new Double(outstanding))));
                            it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                            it.setRoi(roi);
                            it.setIntTableCode(intTableCode);
                            it.setDetails("Security penal between " + dmy.format(frDt) + " to " + dmy.format(tillDt));
                            intDetails.add(it);
                            la.setLoanIntDetails(intDetails);
                            la.setLoanProductIntDetails(intProductDetail);
                            lml.add(la);
    //                        result = lml;
                        } catch (ParseException ex) {
                            Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        LoanIntCalcList itProduct = new LoanIntCalcList();
                        itProduct.setAcNo(acNo);
                        itProduct.setCustName(custName);
                        itProduct.setFirstDt(fromDt);
                        itProduct.setLastDt(toDt);
                        itProduct.setRoi(roi);
                        itProduct.setProduct(outstanding * noOfDays);
                        itProduct.setClosingBal(noOfDays);
                        itProduct.setTotalInt(penalAmt);
                        itProduct.setIntTableCode(intTableCode);
                        itProduct.setDetails("Over limit penal between " + fromDt + " to " + toDt);
                        intProductDetail.add(itProduct);
                        product = product + outstanding;

                        NumberFormat formatter = new DecimalFormat("#0.00");
                        it.setAcNo(acNo);
                        it.setCustName(custName);
                        it.setClosingBal(closingBal);
                        it.setFirstDt(fromDt);
                        it.setLastDt(toDt);
                        it.setProduct(Double.parseDouble(formatter.format(new Double(outstanding))));
                        it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                        it.setRoi(roi);
                        it.setIntTableCode(intTableCode);
                        it.setDetails("Over limit penal between " + fromDt + " to " + toDt);
                        intDetails.add(it);
                        la.setLoanIntDetails(intDetails);
                        la.setLoanProductIntDetails(intProductDetail);
                        lml.add(la);
                        //result = lml;
                    }
                } 
            }
//            else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                List entryDtList = em.createNativeQuery("Select entryDate From LoanSecurity Where acno='" + acNo + "'  and UPPER(status) = 'ACTIVE' and EntryDate between '" + fromDt + "' and '" + toDt + "' group by entrydate").getResultList();
//                try {
//                    String a[][] = new String[entryDtList.size()][2];
//                    ArrayList datesFrom = new ArrayList();
//                    datesFrom.add(ymd.format(ymd.parse(fromDt)));
//                    if (!entryDtList.isEmpty()) {
//                        for (int i = 0; i < entryDtList.size(); i++) {
//                            Vector entryDtVect = (Vector) entryDtList.get(i);
//                            a[i][0] = entryDtVect.get(0).toString();
//                            if (i == 0) {
//                                /*=======Getting the ROI Change Date Between FromDt and  i Position Date=======================*/
//                                if (ymmd.parse(fromDt).equals(ymd.parse(entryDtVect.get(0).toString()))) {
//                                    if (i == entryDtList.size() - 1) {
//                                        a[i][1] = ymd.format(ymmd.parse(toDt));
//                                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
//                                        datesFrom.add(a[i][1]);
//                                    }
//                                } else {
//                                    datesFrom.add(a[i][0]);
//                                    if (i == entryDtList.size() - 1) {
//                                        a[i][1] = ymd.format(ymmd.parse(toDt));
//                                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
//                                        datesFrom.add(a[i][1]);
//                                    }
//                                }
//                            } else if (i > 0 && i < entryDtList.size() - 1) {
//                                a[i - 1][1] = a[i][0];
//                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
//                                datesFrom.add(a[i][0]);
//                            } else if (i == entryDtList.size() - 1) {
//                                a[i - 1][1] = a[i][0];
//                                a[i][1] = ymd.format(ymmd.parse(toDt));
//                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
//                                datesFrom.add(a[i][0]);
//                                if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
//                                    /*=======Getting the ROI Change Date in Current i Position=======================*/
//                                    datesFrom.add(a[i][1]);
//                                }
//                            }
//                        }
//                        Collections.sort(datesFrom);
//                    } else {
//                        datesFrom.add(ymd.format(ymd.parse(toDt)));
//                        Collections.sort(datesFrom);
//                    }
//
//                    String b[][] = new String[datesFrom.size()][2];
//                    if (!datesFrom.isEmpty()) {
//                        for (int i = 0; i < datesFrom.size(); i++) {
//                            if (i == 0) {
//                                b[i][0] = datesFrom.get(i).toString();
//                                firstDisbDt = datesFrom.get(i).toString();
//                            } else if (i > 0 && i < datesFrom.size() - 1) {
//                                b[i][0] = datesFrom.get(i).toString();
//                                b[i - 1][1] = ymd.format(ymmd.parse(dateAdd(ymmd.format(ymd.parse(b[i][0])), -1)));
//                            } else if (i == datesFrom.size() - 1) {
//                                b[i - 1][1] = datesFrom.get(i).toString();
//                            }
//                        }
//                    }
//
//                    product = 0;
//                    LoanIntCalcList itProduct = new LoanIntCalcList();
//                    for (int i = 0; i < b.length - 1; i++) {
//                        String fDate = b[i][0].toString();
//                        String tDate = b[i][1].toString();
//                        List margineList = em.createNativeQuery("Select MatValue,Margin From LoanSecurity Where acno='" + acNo + "' and UPPER(status) = 'ACTIVE' and EntryDate between '" + fDate + "' and '" + tDate + "' Order By acno,Sno").getResultList();
//                        if (margineList.size() > 0) {
//                            for (int j = 0; j < margineList.size(); j++) {
//                                Vector margineVect = (Vector) margineList.get(0);
//                                matValue = Double.parseDouble(margineVect.get(0).toString());
//                                margine = Double.parseDouble(margineVect.get(1).toString());
//                                dpLimit = matValue - (matValue * margine / 100);
//                                totalDpLimit = totalDpLimit + dpLimit;
//                            }
//                        } else {
//                            dpLimit = 0f;
//                            totalDpLimit = totalDpLimit + dpLimit;
//                        }
//                        outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, toDt));
//                        if (outstanding < 0) {
//                            outstanding = outstanding * -1;
//                        } else {
//                            outstanding = outstanding * 1;
//                        }
//
//                        if (outstanding > totalDpLimit) {
//
//                            try {
//                                outstanding = outstanding - dpLimit;
//                                if (outstanding > 0) {
//                                    String intRoi = getPenalROI(intTableCode, outstanding, toDt);
//                                    if (intRoi.equalsIgnoreCase("Data does not exists")) {
//                                        it.setFlag("false;" + intRoi);
//                                        intProductDetail.add(it);
//                                        la.setLoanIntDetails(intDetails);
//                                        la.setLoanProductIntDetails(intProductDetail);
//                                        lml.add(la);
//                                        //result = lml;
//                                        return lml;
//                                    } else {
//                                        roi = Double.parseDouble(intRoi);
//                                    }
//                                }
//                                Date frDt = ymd.parse(fDate);
//                                Date tillDt = ymd.parse(tDate);
//                                Long day = (tillDt.getTime() - frDt.getTime()) / (24 * 60 * 60 * 1000);
//                                noOfDays = day.intValue() + 1;
//
//                                penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
//                                totalPenalAmt = totalPenalAmt + penalAmt;
//                                product = product + outstanding * noOfDays;
//                                if (i == 0) {
//                                    firstDt = dmy.format(frDt);
//                                }
//                                if (i == b.length - 2) {
//                                    lastDt = dmy.format(tillDt);
//                                    closingBal = outstanding;
//                                }
//
//                                itProduct.setAcNo(acNo);
//                                itProduct.setCustName(custName);
//                                itProduct.setFirstDt(firstDt);
//                                itProduct.setLastDt(lastDt);
//                                itProduct.setRoi(roi);
//                                itProduct.setProduct(outstanding * noOfDays);
//                                itProduct.setClosingBal(noOfDays);
//                                itProduct.setTotalInt(penalAmt);
//                                itProduct.setIntTableCode(intTableCode);
//                                itProduct.setDetails("Security penal between " + firstDt + " to " + lastDt);
//                                intProductDetail.add(itProduct);
//                                product = product + outstanding;
//                            } catch (ParseException ex) {
//                                Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    }
//
//                    NumberFormat formatter = new DecimalFormat("#0.00");
//                    it.setAcNo(acNo);
//                    it.setCustName(custName);
//                    it.setClosingBal(closingBal);
//                    it.setFirstDt(firstDt);
//                    it.setLastDt(lastDt);
//                    it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
//                    it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
//                    it.setRoi(roi);
//                    it.setIntTableCode(intTableCode);
//                    it.setDetails("Security penal between " + firstDt + " to " + lastDt);
//                    intDetails.add(it);
//                    la.setLoanIntDetails(intDetails);
//                    la.setLoanProductIntDetails(intProductDetail);
//                    lml.add(la);
//                    //result = lml;
//                } catch (Exception e) {
//                    throw new ApplicationException(e.getMessage());
//                }
//            }
            return lml;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<LoanMultiList> stockPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();

        String schemeCode;
        String intTableCode = null;
        int moratoriumPd;
        double accPrefDr = 0;
        double accPrefCr = 0;
        String rateCode = null;
        String disbType;
        String calcMethod = null;
        String calcOn;
        String intAppFreq = null;
        String calcLevel = null;
        String compoundFreq;
        int peggFreq = 0;
        int loanPdMonth;
        int loanPdDay;
        double rateOfInt = 0;
        double penalRoi;
        double sanctionLimit = 0;
        String acNature = null;
        String firstDt = null;
        String lastDt = null;
        double totalPenalAmt = 0d;
        double closingBal = 0d;
        String details = "";
        String custName = null;
        double product = 0;
        double roi = 0;
        double outstanding = 0f;

        try {
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, A.INT_CALC_UPTO_DT, ifnull(A.INT_COMP_TILL_DT,0), A.NEXT_INT_CALC_DT  from cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    String acno = (String) SecDetailsVect.get(0);
                    schemeCode = (String) SecDetailsVect.get(1);
                    intTableCode = (String) SecDetailsVect.get(2);
                    moratoriumPd = Integer.parseInt(SecDetailsVect.get(3).toString());
                    accPrefDr = Double.parseDouble(SecDetailsVect.get(4).toString());
                    accPrefCr = Double.parseDouble(SecDetailsVect.get(5).toString());
                    rateCode = (String) SecDetailsVect.get(6);
                    disbType = (String) SecDetailsVect.get(7);
                    calcMethod = (String) SecDetailsVect.get(8);
                    calcOn = (String) SecDetailsVect.get(9);
                    intAppFreq = (String) SecDetailsVect.get(10);
                    calcLevel = (String) SecDetailsVect.get(11);
                    compoundFreq = (String) SecDetailsVect.get(12);
                    peggFreq =  (SecDetailsVect.get(13) == null ? 0 : Integer.parseInt(SecDetailsVect.get(13).toString()));
                    loanPdMonth = Integer.parseInt(SecDetailsVect.get(14).toString());
                    loanPdDay = Integer.parseInt(SecDetailsVect.get(15).toString());
                    rateOfInt = Double.parseDouble(SecDetailsVect.get(16).toString());
                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                }
            }

            acNature = ftsPosting.getAccountNature(acNo);
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for account number " + acNo);
            }
            double noOfDays = 0;
            double penalAmt = 0;
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List entryDtList = em.createNativeQuery("Select entryDate, receivedSTMDt From loan_stockstm Where acno='" + acNo + "'  and UPPER(status) = 'ACTIVE' and EntryDate between '" + fromDt + "' and '" + toDt + "' Order By acno,entryDate").getResultList();
                try {
                    ArrayList datesFrom = new ArrayList();
                    datesFrom.add(ymd.format(ymmd.parse(fromDt)));
                    if (entryDtList.size() > 0) {

                        for (int i = 0; i < entryDtList.size(); i++) {
                            LoanIntCalcList itProduct = new LoanIntCalcList();
                            Vector entryDtVect = (Vector) entryDtList.get(i);
                            Date entryDt = ymd.parse(entryDtVect.get(0).toString());
                            Date receivedDt = ymd.parse(entryDtVect.get(1).toString());
                            if (receivedDt.after(entryDt)) {
                                outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, toDt));
                                if (outstanding < 0) {
                                    outstanding = outstanding * -1;
                                } else {
                                    outstanding = outstanding * 1;
                                }
                                product = product + outstanding;
                                if (outstanding > 0) {
                                    String intRoi = getPenalROI(intTableCode, outstanding, entryDtVect.get(1).toString());
                                    if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                        throw new ApplicationException("Penal Roi is not found");
                                    } else {
                                        roi = Double.parseDouble(intRoi);
                                    }
                                }
                                //String endofline = System.getProperty("line.separator");
                                details = details.concat(" Stock Penal between " + dmy.format(entryDt) + " and " + dmy.format(receivedDt));
                                if (i == 0) {
                                    firstDt = dmy.format(entryDt);
                                }
                                if (i == entryDtList.size() - 1) {
                                    lastDt = dmy.format(receivedDt);
                                    closingBal = outstanding;
                                }

                                Long day = (receivedDt.getTime() - entryDt.getTime()) / (24 * 60 * 60 * 1000);
                                noOfDays = day.doubleValue();
                                penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
                                totalPenalAmt = totalPenalAmt + penalAmt;
                            }
                            itProduct.setAcNo(acNo);
                            itProduct.setCustName(custName);
                            itProduct.setFirstDt(firstDt);
                            itProduct.setLastDt(dmy.format(receivedDt));
                            itProduct.setRoi(roi);
                            itProduct.setProduct(outstanding * noOfDays);
                            itProduct.setClosingBal(noOfDays);
                            itProduct.setTotalInt(penalAmt);
                            itProduct.setIntTableCode(intTableCode);
                            itProduct.setDetails(details);
                            intProductDetail.add(itProduct);
                            product = product + outstanding;

                        }
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        it.setAcNo(acNo);
                        it.setCustName(custName);
                        it.setClosingBal(closingBal);
                        it.setFirstDt(firstDt);
                        it.setLastDt(lastDt);
                        it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                        it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                        it.setRoi(roi);
                        it.setIntTableCode(intTableCode);
                        it.setDetails(details);
                        intDetails.add(it);
                        la.setLoanIntDetails(intDetails);
                        la.setLoanProductIntDetails(intProductDetail);
                        lml.add(la);
                        //result = lml;

                    } else {
                        LoanIntCalcList itProduct = new LoanIntCalcList();
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        itProduct.setAcNo(acNo);
                        itProduct.setCustName(custName);
                        itProduct.setFirstDt(firstDt);
                        itProduct.setLastDt(lastDt);
                        itProduct.setRoi(roi);
                        itProduct.setProduct(outstanding * noOfDays);
                        itProduct.setClosingBal(noOfDays);
                        itProduct.setTotalInt(penalAmt);
                        itProduct.setIntTableCode(intTableCode);
                        itProduct.setDetails(details);
                        intProductDetail.add(itProduct);
                        product = product + outstanding;

                        it.setAcNo(acNo);
                        it.setCustName(custName);
                        it.setClosingBal(closingBal);
                        it.setFirstDt(firstDt);
                        it.setLastDt(lastDt);
                        it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                        it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                        it.setRoi(roi);
                        it.setIntTableCode(intTableCode);
                        it.setDetails(details);
                        intDetails.add(it);

                        la.setLoanIntDetails(intDetails);
                        la.setLoanProductIntDetails(intProductDetail);
                        lml.add(la);
                        //result = lml;
                    }
                } catch (Exception e) {
                    throw new ApplicationException(e);
                }
            } else {
                LoanIntCalcList itProduct = new LoanIntCalcList();
                NumberFormat formatter = new DecimalFormat("#0.00");

                itProduct.setAcNo(acNo);
                itProduct.setCustName(custName);
                itProduct.setFirstDt(firstDt);
                itProduct.setLastDt(lastDt);
                itProduct.setRoi(roi);
                itProduct.setProduct(Math.round(outstanding * noOfDays));
                itProduct.setClosingBal(noOfDays);
                itProduct.setTotalInt(penalAmt);
                itProduct.setIntTableCode(intTableCode);
                itProduct.setDetails(details);
                intProductDetail.add(itProduct);
                product = product + outstanding;

                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails(details);
                intDetails.add(it);

                la.setLoanIntDetails(intDetails);
                la.setLoanProductIntDetails(intProductDetail);
                lml.add(la);
                //result = lml;
                it.setFlag("false;");
                intProductDetail.add(it);
                la.setLoanIntDetails(intDetails);
                la.setLoanProductIntDetails(intProductDetail);
                lml.add(la);
                //result = lml;
                return lml;
            }
            return lml;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    //********************* GET LATEST PENAL ROI ************************//
    public String getPenalROI(String intTableCode, double amt, String date) throws ApplicationException {
        String result = null;
        int intVerNo;
        double basePerDr;
        double basePerCr;
        String intMastTblCod;
        Date stDt;
        Date toDt;
        double roi;
        try {
            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();
            List laIntCodeMastHistList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master_history where INTEREST_CODE='" + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                basePerDr = Double.parseDouble(laIntCodeMastVect.get(1).toString());
                basePerCr = Double.parseDouble(laIntCodeMastVect.get(2).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(3);
                stDt = (Date) laIntCodeMastVect.get(4);
                toDt = (Date) laIntCodeMastVect.get(5);
            } else if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(0).toString());
                basePerDr = Double.parseDouble(laIntCodeMastHistVect.get(1).toString());
                basePerCr = Double.parseDouble(laIntCodeMastHistVect.get(2).toString());
                intMastTblCod = (String) laIntCodeMastHistVect.get(3);
                stDt = (Date) laIntCodeMastHistVect.get(4);
                toDt = (Date) laIntCodeMastHistVect.get(5);
            } else {
                result = "Data does not exists";
                return result;
            }

            double intPerDr;
            double intPerCr;
            Date stDtIntMast;
            Date toDtIntMast;
            List laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from cbs_loan_interest_master where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();
            List laIntMastHistList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from cbs_loan_interest_master_history where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            if (!laIntMastList.isEmpty()) {
                Vector laIntMastVect = (Vector) laIntMastList.get(0);
                intPerDr = Double.parseDouble(laIntMastVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastVect.get(1).toString());
                stDtIntMast = (Date) laIntMastVect.get(2);
                toDtIntMast = (Date) laIntMastVect.get(3);
            } else if (!laIntMastHistList.isEmpty()) {
                Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                intPerDr = Double.parseDouble(laIntMastHistVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastHistVect.get(1).toString());
                stDtIntMast = (Date) laIntMastHistVect.get(2);
                toDtIntMast = (Date) laIntMastHistVect.get(3);
            } else {
                result = "Data does not exists";
                return result;
            }

            String nrIntIndi;
            double nrIntPer;
            List laIntSlabMastList = em.createNativeQuery("SELECT PEENAL_INTEREST_INDICATOR, PEENAL_INTEREST_PERCENTAGE from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT AND INTEREST_VERSION_NO =" + intVerNo).getResultList();
            List laIntSlabMastHistList = em.createNativeQuery("SELECT PEENAL_INTEREST_INDICATOR, PEENAL_INTEREST_PERCENTAGE from cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            if (!laIntSlabMastList.isEmpty()) {
                Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                nrIntIndi = (String) laIntSlabMastVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastVect.get(1).toString());
            } else if (!laIntSlabMastHistList.isEmpty()) {
                Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastHistVect.get(1).toString());
            } else {
                result = "Data does not exists";
                return result;
            }

            if (nrIntIndi.equalsIgnoreCase("F")) {
                roi = nrIntPer + intPerDr - intPerCr;
                result = new Double(roi).toString();
            } else if (nrIntIndi.equalsIgnoreCase("D")) {
                roi = (double) 0.0;
                result = new Double(roi).toString();
            } else if (nrIntIndi.equalsIgnoreCase("N")) {
                roi = nrIntPer;
                result = new Double(roi).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException {
        try {
            List getMaxToDtList = em.createNativeQuery("select ifnull(date_format(max(TODT), '%Y%m%d'),'') from  cbs_loan_interest_post_ac_wise where acno = '" + acNo + "' and BRNCODE = '" + brnCode + "' and POST_FLAG = 'Y' and FLAG = 'P'").getResultList();
            String toDt = "";
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                toDt = getMaxToDtVect.get(0).toString();
                if (toDt.equals("")) {
                    String acNature = ftsPosting.getAccountNature(acNo);

                    List selectQuery = em.createNativeQuery("select acno,custname,date_format(openingdt,'%d/%m/%Y'),accstatus from accountmaster where acno='" + acNo + "'").getResultList();
                    if (selectQuery.isEmpty()) {
                        throw new ApplicationException("Account number does not exist");
                    }

                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List getMindtAcWiseList = em.createNativeQuery("select date_format(min(dt), '%Y%m%d') from ca_recon where acno = '" + acNo + "' and ty = 1 and org_brnid = '" + brnCode + "'").getResultList();
                        if (getMindtAcWiseList.size() > 0) {
                            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
                            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                                throw new ApplicationException("From date is not found");
                            } else {
                                String fromDt = getMinDtAcWiseVect.get(0).toString();
                                toDt = fromDt;
                            }
                        } else {
                            throw new ApplicationException("From date is not found");
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                            || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        List getMindtAcWiseList = em.createNativeQuery("select date_format(min(dt), '%Y%m%d') from loan_recon where acno = '" + acNo + "' and ty = 1 and org_brnid = '" + brnCode + "'").getResultList();
                        if (getMindtAcWiseList.size() > 0) {
                            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
                            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                                throw new ApplicationException("From date is not found");
                            } else {
                                String fromDt = getMinDtAcWiseVect.get(0).toString();
                                toDt = fromDt;
                            }
                        } else {
                            throw new ApplicationException("From date is not found");
                        }
                    }
                } else {
                    toDt = dateAdd(toDt, 1);
                }
            }
            return toDt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String dateAdd(String dt, int noOfDays) throws ApplicationException {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.DATE, noOfDays);
            dt = ymmd.format(calendar.getTime());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return dt;
    }
}
