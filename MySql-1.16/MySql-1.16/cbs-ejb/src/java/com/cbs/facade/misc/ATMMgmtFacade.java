/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.constant.ATMProcessingCodeEnum;
import com.cbs.dto.misc.ATMReconsilationDTO;
import com.cbs.dto.neftrtgs.RefundPojo;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "ATMMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ATMMgmtFacade implements ATMMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    CommonReportMethodsRemote commonReportRemote;

    @EJB
    private SmsManagementFacadeRemote smsFacade;

    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dMMMy = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat dMMMyy = new SimpleDateFormat("dd-MMM-yy");
    SimpleDateFormat ddMMyy = new SimpleDateFormat("ddMMyyhhmm");
    NumberFormat amountFormatter = new DecimalFormat("#.00");

    @Override
    public Map<String, String> getCbsChargeDetail(String chargeType, String effectiveDt) throws Exception {
        Map<String, String> chargeDetail = new HashMap<>();
        try {
            List list = em.createNativeQuery("select c.charge_name,c.cr_gl_head,c.amt,g.acname from "
                    + "cbs_charge_detail c,gltable g where c.charge_type='" + chargeType + "' and "
                    + "c.eff_date=(select max(eff_date) from cbs_charge_detail where "
                    + "charge_type='" + chargeType + "' and eff_date<='" + effectiveDt + "') and "
                    + "c.cr_gl_head=substring(g.acno,3,10) group by charge_name").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data regarding the charge type-->" + chargeType);
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                chargeDetail.put(ele.get(0).toString().trim(), ele.get(1).toString().trim() + ":"
                        + ele.get(3).toString().trim() + ":" + ele.get(2).toString().trim());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return chargeDetail;
    }

    @Override
    public List<ATMReconsilationDTO> getAllTxnsToReconsile(String txnDt) throws Exception {
        List<ATMReconsilationDTO> returnList = new ArrayList<>();
        try {
            Map<String, String> chargeDetail = new HashMap<>();

            List list = em.createNativeQuery("select c.charge_name,c.cr_gl_head,c.amt,g.acname from cbs_charge_detail c,"
                    + "gltable g where c.charge_type in('ATM') and c.eff_date=(select max(eff_date) from cbs_charge_detail "
                    + "where charge_type in('ATM') and eff_date<='" + txnDt + "') and c.cr_gl_head=substring(g.acno,3,10) "
                    + "group by charge_name "
                    + "union all "
                    + "select c.charge_name,c.cr_gl_head,c.amt,g.acname from cbs_charge_detail c,gltable g where "
                    + "c.charge_type in('SERVICE-TAX') and c.eff_date=(select max(eff_date) from cbs_charge_detail "
                    + "where charge_type in('SERVICE-TAX') and eff_date<='" + txnDt + "') and c.cr_gl_head=substring(g.acno,3,10) "
                    + "group by charge_name "
                    + "union all select c.charge_name,c.cr_gl_head,c.amt,g.acname from cbs_charge_detail c,gltable g "
                    + "where c.charge_type in('SERVICE-TAX-IGST-INPCR') and c.eff_date=(select max(eff_date) from "
                    + "cbs_charge_detail where charge_type in('SERVICE-TAX-IGST-INPCR') and eff_date<='" + txnDt + "') and "
                    + "c.cr_gl_head=substring(g.acno,3,10) group by charge_name").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define charges detail for atm reconsilation.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                chargeDetail.put(ele.get(0).toString().trim(), ele.get(1).toString().trim() + ":"
                        + ele.get(3).toString().trim() + ":" + ele.get(2).toString().trim());
            }

            List dataList = em.createNativeQuery("select processing_code,count(*) as txn_count from atm_off_us_transaction_parameter "
                    + "where tran_date='" + txnDt + "' and in_process_flag='S' and reversal_indicator=0 and "
                    + "processing_code='" + ATMProcessingCodeEnum.ACQUIRER_WITHDRAWAL.getCode() + "' and system_trace_number "
                    + "not in(select original_system_trace_number from atm_off_us_transaction_parameter where "
                    + "tran_date='" + txnDt + "' and in_process_flag='S' and reversal_indicator=1 and "
                    + "processing_code='" + ATMProcessingCodeEnum.ACQUIRER_WITHDRAWAL.getCode() + "') group by processing_code "
                    + "union all "
                    + "select processing_code,count(*) as txn_count from atm_normal_transaction_parameter where "
                    + "tran_date='" + txnDt + "' and in_process_flag='S' and "
                    + "processing_code in('" + ATMProcessingCodeEnum.ISSUER_CASH_WITHDRAWAL.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.ISSUER_BALANCE_INQUIRY.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.ISSUER_MINI_STATEMENT.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.CASH_AT_POS.getCode() + "','" + ATMProcessingCodeEnum.MERCHANT_SALE.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.POS_BALANCE_INQUIRY.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.POS_MINI_STATEMENT.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.ECOM_WITHDRAWAL.getCode() + "') and system_trace_number "
                    + "not in(select original_system_trace_number from atm_reversal_transaction_parameter where "
                    + "tran_date='" + txnDt + "' and in_process_flag='S' and processing_code "
                    + "in('" + ATMProcessingCodeEnum.ISSUER_CASH_WITHDRAWAL.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.ISSUER_BALANCE_INQUIRY.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.ISSUER_MINI_STATEMENT.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.CASH_AT_POS.getCode() + "','" + ATMProcessingCodeEnum.MERCHANT_SALE.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.POS_BALANCE_INQUIRY.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.POS_MINI_STATEMENT.getCode() + "',"
                    + "'" + ATMProcessingCodeEnum.ECOM_WITHDRAWAL.getCode() + "')) group by processing_code").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("There is no data to do the reconsilation.");
            }
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);

                String chgDetail = chargeDetail.get(ele.get(0).toString().trim());
                if (chgDetail == null) {
                    continue;
                }

                ATMReconsilationDTO dto = new ATMReconsilationDTO();
                dto.setProcessingCode(ele.get(0).toString().trim());
                dto.setRequestType(ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()));
                dto.setNoOfTxn(Integer.parseInt(ele.get(1).toString().trim()));

                String[] chgArray = chgDetail.split(":");

                double chgAmt = CbsUtil.round((dto.getNoOfTxn() * Double.parseDouble(chgArray[2])), 2);
                dto.setTotalCharge(new BigDecimal(amountFormatter.format(chgAmt)));

                dto.setChargeHead(chgArray[0] + "   " + "(" + chgArray[1] + ")");

                BigDecimal payableSgst = new BigDecimal("0.00"), payableCgst = new BigDecimal("0.00"), inpCrIgst = new BigDecimal("0.00");
                //SCGT(Payable) and CGST(Payable) will apply on Income(Acquirer). While on expenditure, Input Credit IGST will apply
                dto.setTotalPayableSgst(payableSgst);
                dto.setTotalPayableCgst(payableCgst);
                dto.setTotalInpurCreditIgst(inpCrIgst);

                if (dto.getProcessingCode().equalsIgnoreCase(ATMProcessingCodeEnum.ACQUIRER_WITHDRAWAL.getCode())) {
                    if (chargeDetail.get("SGST") == null || chargeDetail.get("CGST") == null) {
                        continue;
                    }

                    chgAmt = CbsUtil.round((((dto.getTotalCharge().
                            multiply(new BigDecimal(chargeDetail.get("SGST").split(":")[2]))).
                            divide(new BigDecimal("100"))).doubleValue()), 2);
                    dto.setTotalPayableSgst(new BigDecimal(amountFormatter.format(chgAmt)));
                    dto.setPayableSgstHead(chargeDetail.get("SGST").split(":")[0]);

                    chgAmt = CbsUtil.round((((dto.getTotalCharge().
                            multiply(new BigDecimal(chargeDetail.get("CGST").split(":")[2]))).
                            divide(new BigDecimal("100"))).doubleValue()), 2);
                    dto.setTotalPayableCgst(new BigDecimal(amountFormatter.format(chgAmt)));
                    dto.setPayableCgstHead(chargeDetail.get("CGST").split(":")[0]);
                } else {
                    if (chargeDetail.get("I-GST") == null) { //Check Here
                        continue;
                    }

                    chgAmt = CbsUtil.round((((dto.getTotalCharge().
                            multiply(new BigDecimal(chargeDetail.get("I-GST").split(":")[2]))).
                            divide(new BigDecimal("100"))).doubleValue()), 2);
                    dto.setTotalInpurCreditIgst(new BigDecimal(amountFormatter.format(chgAmt)));
                    dto.setInpCrIgstHead(chargeDetail.get("I-GST").split(":")[0]);
                }
                returnList.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnList;
    }

    @Override
    public String postATMReconsilationData(List<ATMReconsilationDTO> gridDetail, String txnDt, String todayDt,
            String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        float trsno = 0;
        try {
            //There is no balance updation. Because all are the gl head.
            ut.begin();
            List list = em.createNativeQuery("select acno from gl_recon where trandesc=74 and valuedt='" + txnDt + "'").getResultList();
            if (!list.isEmpty()) {
                throw new Exception("Data has been already posted for date: " + dmy.format(ymd.parse(txnDt)));
            }

            String alphaCode = commonReportRemote.getBrncodeByAlphacode("HO");

            list = em.createNativeQuery("select code from cbs_parameterinfo where name='ATM-BANKER-HEAD'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define banker head for atm reconsilation.");
            }
            Vector elem = (Vector) list.get(0);
            String hdfcBankerHead = elem.get(0).toString().trim();
            if (hdfcBankerHead == null || hdfcBankerHead.equals("") || hdfcBankerHead.length() != 10) {
                throw new Exception("Please define proper HDFC Banker Head.");
            }
            hdfcBankerHead = alphaCode + hdfcBankerHead;
            String bankerHeadAccount = hdfcBankerHead;

            trsno = ftsRemote.getTrsNo();

            String glReconInsertQuery = "insert into gl_recon(acno,ty,dt,valuedt,dramt,cramt,balance,trantype,details,"
                    + "iy,instno,instdt,enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,tokenpaidby,subtokenno,"
                    + "trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,advicebrncode) values";
            String glReconDataQuery = "";
            float recNo = ftsRemote.getRecNo();

            for (ATMReconsilationDTO dto : gridDetail) {
                if (dto.getProcessingCode().equalsIgnoreCase(ATMProcessingCodeEnum.ACQUIRER_WITHDRAWAL.getCode())) {
                    double formattedAmount = Double.parseDouble(amountFormatter.format(CbsUtil.round((((dto.getTotalCharge().
                            add(dto.getTotalPayableSgst())).add(dto.getTotalPayableCgst())).doubleValue()), 2)));
                    if (formattedAmount != 0) {
                        if (glReconDataQuery.equals("")) {
                            glReconDataQuery = "('" + hdfcBankerHead + "',1,'" + todayDt + "','" + txnDt + "'," + formattedAmount + ","
                                    + "0,0,2,'HDFC Banker Head Entry For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "','Y'," + recNo + ",3,"
                                    + "'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "','" + alphaCode + "',0,'','','')";
                        } else {
                            recNo = recNo + 1;
                            hdfcBankerHead = bankerHeadAccount;
                            glReconDataQuery = glReconDataQuery + "," + "('" + hdfcBankerHead + "',1,'" + todayDt + "','" + txnDt + "'," + formattedAmount + ","
                                    + "0,0,2,'HDFC Banker Head Entry For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "','Y'," + recNo + ",3,"
                                    + "'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "','" + alphaCode + "',0,'','','')";
                        }

                        hdfcBankerHead = alphaCode + dto.getPayableSgstHead();
                        if (hdfcBankerHead == null || hdfcBankerHead.equals("") || hdfcBankerHead.length() != 12) {
                            throw new Exception("Please define proper SGST Head.");
                        }
                        formattedAmount = Double.parseDouble(amountFormatter.format(CbsUtil.round(dto.getTotalPayableSgst().doubleValue(), 2)));
                        if (formattedAmount != 0) {
                            recNo = recNo + 1;
                            glReconDataQuery = glReconDataQuery + "," + "('" + hdfcBankerHead + "',0,'" + todayDt + "','" + txnDt + "',"
                                    + "0," + formattedAmount + ",0,2,'SGST Entry For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "',"
                                    + "'Y'," + recNo + ",3,'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "',"
                                    + "'" + alphaCode + "',0,'','','')";
                        }

                        hdfcBankerHead = alphaCode + dto.getPayableCgstHead();
                        if (hdfcBankerHead == null || hdfcBankerHead.equals("") || hdfcBankerHead.length() != 12) {
                            throw new Exception("Please define proper CGST Head.");
                        }
                        formattedAmount = Double.parseDouble(amountFormatter.format(CbsUtil.round(dto.getTotalPayableCgst().doubleValue(), 2)));
                        if (formattedAmount != 0) {
                            recNo = recNo + 1;
                            glReconDataQuery = glReconDataQuery + "," + "('" + hdfcBankerHead + "',0,'" + todayDt + "','" + txnDt + "',"
                                    + "0," + formattedAmount + ",0,2,'CGST Entry For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "',"
                                    + "'Y'," + recNo + ",3,'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "',"
                                    + "'" + alphaCode + "',0,'','','')";
                        }

                        hdfcBankerHead = alphaCode + dto.getChargeHead().trim().substring(0, 10);
                        if (hdfcBankerHead == null || hdfcBankerHead.equals("") || hdfcBankerHead.length() != 12) {
                            throw new Exception("Please define proper charge head for: " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()));
                        }
                        formattedAmount = Double.parseDouble(amountFormatter.format(CbsUtil.round(dto.getTotalCharge().doubleValue(), 2)));
                        if (formattedAmount != 0) {
                            recNo = recNo + 1;
                            glReconDataQuery = glReconDataQuery + "," + "('" + hdfcBankerHead + "',0,'" + todayDt + "','" + txnDt + "',"
                                    + "0," + formattedAmount + ",0,2,'Charge Entry For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "',"
                                    + "'Y'," + recNo + ",3,'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "',"
                                    + "'" + alphaCode + "',0,'','','')";
                        }
                    }
                } else {
                    double formattedAmount = Double.parseDouble(amountFormatter.format(CbsUtil.round(((dto.getTotalCharge().
                            add(dto.getTotalInpurCreditIgst())).doubleValue()), 2)));
                    if (formattedAmount != 0) {
                        if (glReconDataQuery.equals("")) {
                            glReconDataQuery = "('" + hdfcBankerHead + "',0,'" + todayDt + "','" + txnDt + "',0,"
                                    + "" + formattedAmount + ",0,2,'HDFC Banker Head Entry For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "','Y'," + recNo + ",3,"
                                    + "'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "','" + alphaCode + "',0,'','','')";
                        } else {
                            recNo = recNo + 1;
                            hdfcBankerHead = bankerHeadAccount;
                            glReconDataQuery = glReconDataQuery + "," + "('" + hdfcBankerHead + "',0,'" + todayDt + "','" + txnDt + "',0,"
                                    + "" + formattedAmount + ",0,2,'HDFC Banker Head Entry For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "','Y'," + recNo + ",3,"
                                    + "'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "','" + alphaCode + "',0,'','','')";
                        }

                        hdfcBankerHead = alphaCode + dto.getChargeHead().trim().substring(0, 10);
                        if (hdfcBankerHead == null || hdfcBankerHead.equals("") || hdfcBankerHead.length() != 12) {
                            throw new Exception("Please define proper charge head for: " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()));
                        }
                        //formattedAmount = Double.parseDouble(amountFormatter.format(CbsUtil.round(dto.getTotalCharge().doubleValue(), 2)));
                        if (formattedAmount != 0) {
                            recNo = recNo + 1;
                            glReconDataQuery = glReconDataQuery + "," + "('" + hdfcBankerHead + "',1,'" + todayDt + "','" + txnDt + "',"
                                    + "" + formattedAmount + ",0,0,2,'Charges Entry For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "',"
                                    + "'Y'," + recNo + ",3,'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "',"
                                    + "'" + alphaCode + "',0,'','','')";
                        }

                        hdfcBankerHead = alphaCode + dto.getChargeHead().trim().substring(0, 10);
                        if (hdfcBankerHead == null || hdfcBankerHead.equals("") || hdfcBankerHead.length() != 12) {
                            throw new Exception("Please define proper charge head for: " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()));
                        }
                        formattedAmount = Double.parseDouble(amountFormatter.format(CbsUtil.round(dto.getTotalInpurCreditIgst().doubleValue() / 2, 2)));
                        if (formattedAmount != 0) {
                            recNo = recNo + 1;
                            glReconDataQuery = glReconDataQuery + "," + "('" + hdfcBankerHead + "',0,'" + todayDt + "','" + txnDt + "',"
                                    + "0," + formattedAmount + ",0,2,'IGST Input credit reversal" + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "',"
                                    + "'Y'," + recNo + ",3,'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "',"
                                    + "'" + alphaCode + "',0,'','','')";
                        }

                        hdfcBankerHead = alphaCode + dto.getInpCrIgstHead();
                        if (hdfcBankerHead == null || hdfcBankerHead.equals("") || hdfcBankerHead.length() != 12) {
                            throw new Exception("Please define proper input credit head.");
                        }
                        if (formattedAmount != 0) {
                            recNo = recNo + 1;
                            glReconDataQuery = glReconDataQuery + "," + "('" + hdfcBankerHead + "',1,'" + todayDt + "','" + txnDt + "',"
                                    + "" + formattedAmount + ",0,0,2,'IGST Input credit For " + ATMProcessingCodeEnum.getDescription(dto.getProcessingCode()) + "',0,'','19000101','" + userName + "',"
                                    + "'Y'," + recNo + ",3,'" + userName + "'," + trsno + ",74,0,'','',now(),'" + alphaCode + "',"
                                    + "'" + alphaCode + "',0,'','','')";
                        }

                    }
                }
            }

            glReconInsertQuery = glReconInsertQuery + glReconDataQuery;

            System.out.println("Query Is-->" + glReconInsertQuery);

            int n = em.createNativeQuery(glReconInsertQuery).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in posting of reconsilation entry.");
            }

            ftsRemote.updateRecNo(recNo);
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return "true:" + trsno;
    }

    @Override
    public String refundFileProcessing(InputStream is, String userName, String orgBrCode, String fileName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List<RefundPojo> beanList = new CsvToBeanBuilder(new InputStreamReader(is)).withSeparator(',').withQuoteChar('"').withType(RefundPojo.class).build().parse();
            String /*tranDates = "",*/ rrns = "", stns = "";
            for (RefundPojo pojo : beanList) {

                if (pojo.getFunctionCode().equals("262")) {

                    String[] rrnData = pojo.getAcquirerReferenceData().split("-");

                    if (rrns.equals("") && stns.equals("")) {
//                        tranDates = "\'" + ymd.format(pojo.getTransactionDate().length()==11?dMMMy.parse(pojo.getTransactionDate()):dMMMyy.parse(pojo.getTransactionDate())) + "\'";
                        rrns = "\'" + rrnData[1].trim() + "\'";
                        stns = "\'" + rrnData[1].trim().substring(6) + "\'";
                    } else {
  //                      tranDates = tranDates + ",\'" + ymd.format(pojo.getTransactionDate().length()==11?dMMMy.parse(pojo.getTransactionDate()):dMMMyy.parse(pojo.getTransactionDate())) + "\'";
                        rrns = rrns + ",\'" + rrnData[1].trim() + "\'";
                        stns = stns + ",\'" + rrnData[1].trim().substring(6) + "\'";
                    }
                }
            }

            List dataList = em.createNativeQuery("select acno,purpose from abb_parameter_info where purpose in( 'INTERSOLE ACCOUNT')").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define Intersole A/c in abb parameter info");
            }

            Vector dataVec = (Vector) dataList.get(0);
            String isoAccount = dataVec.get(0).toString();

            //ATM POS HEAD
            String nfsHead = ftsRemote.getCodeFromCbsParameterInfo("ATM-POS-HEAD ");
            nfsHead = orgBrCode + nfsHead;

            dataList = em.createNativeQuery("select alphacode from branchmaster where brncode = '" + Integer.parseInt(orgBrCode) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define alphacode for-->" + orgBrCode);
            }
            dataVec = (Vector) dataList.get(0);
            String orgnAlphaCode = dataVec.get(0).toString();

            BigDecimal totalRefundAmt = BigDecimal.ZERO;

            List<SmsToBatchTo> smsList = new ArrayList<>();

//            List acNoList = em.createNativeQuery("select from_account_number,SYSTEM_TRACE_NUMBER, rrn, accstatus from atm_normal_transaction_parameter, "
//                    + "accountmaster where from_account_number = acno and tran_date in (" + tranDates + ") and SYSTEM_TRACE_NUMBER in (" + stns + ") and "
//                    + "rrn in (" + rrns + ") and IN_PROCESS_FLAG = 'S' and SYSTEM_TRACE_NUMBER not in (select ORIGINAL_SYSTEM_TRACE_NUMBER from "
//                    + "atm_reversal_transaction_parameter where TRAN_DATE in (" + tranDates + ")  and IN_PROCESS_FLAG = 'S')").getResultList();
            
             List acNoList = em.createNativeQuery("select from_account_number,SYSTEM_TRACE_NUMBER, rrn, accstatus from atm_normal_transaction_parameter, "
                    + "accountmaster where from_account_number = acno and rrn in (" + rrns + ") and IN_PROCESS_FLAG = 'S' and "
                     + "rrn not in (select rrn from atm_reversal_transaction_parameter where IN_PROCESS_FLAG = 'S')").getResultList();


            String query = "INSERT INTO atm_dispute_details (ReportDate, DisputeRaiseDate, DisputeRaisedSettlementDate, CaseNumber, FunctionCode, \n"
                    + "FunctionCodeAndDescription, PrimaryAccountNumber, ProcessingCode, TransactionDate, TransactionAmount, TransactionCurrencyCode, \n"
                    + "SettlementAmount, SettlementCurrencyCode, TransactionSettlementDate, AmountsAdditional, ControlNumber, DisputeOriginatorPID, \n"
                    + "DisputeDestinationPID, AcquirerReferenceDataRRN, ApprovalCode, OriginatorPoint, POSEntryMode, POSConditionCode, AcquirerInstitutionIDCode, \n"
                    + "AcquirerNameAndCountry, IssuerInstitutionIDCode, IssuerNameAndCountry, CardType, CardBrand, CardAcceptorTerminalID, CardAcceptorName, \n"
                    + "CardAcceptorLocationAddress, CardAcceptorCountryCode, CardAcceptorBusinessCode, DisputeReasonCode, DisputeReasonCodeDescription,\n"
                    + " DisputeAmount, FullPartialIndicator, DisputeMemberMessageText, DisputeDocumentIndicator, DocumentAttachedDate, MTI, IncentiveAmount, \n"
                    + " TierCodeOfNonFulfillment, TierCodeOfFulfillment, DeadlineDate, DaysToAct, DirectionOfDisputeInwardOutward, ProcessTime, fileName, trsno,reason) values";

            float trsNo = ftsRemote.getTrsNo();
            float recNo = ftsRemote.getRecNo();

            for (RefundPojo pojo : beanList) {
                query = query + "( '" + pojo.getReportDate() + "', '" + pojo.getDisputeRaiseDate() + "', '" + pojo.getDisputeRaisedSettlementDate() + "', '"
                        + pojo.getCaseNumber() + "', '" + pojo.getFunctionCode() + "', '" + pojo.getFunctionCodeAndDescription() + "', '" + pojo.getPrimaryAccountNumber() + "', '"
                        + pojo.getProcessingCode() + "', '" + pojo.getTransactionDate() + "'," + new BigDecimal(pojo.getTransactionAmount()) + ", '"
                        + pojo.getTransactionCurrencyCode() + "', " + new BigDecimal(pojo.getSettlementAmount()) + ", '" + pojo.getSettlementCurrencyCode() + "', '"
                        + pojo.getTransactionSettlementDate() + "'," + new BigDecimal(pojo.getAmountsAdditional()) + ", '" + stripLeadingAndTrailingQuotes(pojo.getControlNumber()) + "', '"
                        + pojo.getDisputeOriginatorPID() + "', '" + pojo.getDisputeDestinationPID() + "', '" + pojo.getAcquirerReferenceData() + "', '"
                        + pojo.getApprovalCode() + "', '" + pojo.getOriginatorPoint() + "', " + pojo.getPosEntryMode() + ", " + pojo.getPosConditionCode() + ", '"
                        + pojo.getAcquirerInstitutionIDCode() + "', '" + pojo.getAcquirerNameAndCountry() + "', '" + pojo.getIssuerInstitutionIDCode() + "', '"
                        + pojo.getIssuerNameAndCountry() + "', '" + pojo.getCardType() + "', '" + pojo.getCardBrand() + "', '" + pojo.getCardAcceptorTerminalID() + "', '"
                        + pojo.getCardAcceptorName() + "', '" + pojo.getCardAcceptorLocation() + "', '" + pojo.getCardAcceptorCountryCode() + "', '"
                        + pojo.getCardAcceptorBusinessCode() + "', '" + pojo.getDisputeReasonCode() + "', '" + pojo.getDisputeReasonCodeDescription() + "',"
                        + new BigDecimal(pojo.getDisputeAmount()) + ", '" + pojo.getFullOrPartialIndicator() + "', '" + pojo.getDisputeMemberMessageText() + "', '"
                        + pojo.getDisputeDocumentIndicator() + "', '" + pojo.getDocumentAttachedDate() + "', '" + pojo.getMti() + "'," + new BigDecimal(pojo.getIncentiveAmount().equals("") ? "0" : pojo.getIncentiveAmount()) + ",'"
                        + pojo.getTierCodeOfNonFulfillment() + "', '" + pojo.getTierCodeOfFulfillment() + "', '" + pojo.getDeadlineDate() + "',"
                        + Integer.parseInt(pojo.getDaysToAct().equals("") ? "0" : pojo.getDaysToAct()) + ", '" + pojo.getDirectionOfDispute() + "', now(),'"
                        + fileName + "',";

                if (pojo.getFunctionCode().equals("262")) {
                    String[] rrnData = pojo.getAcquirerReferenceData().split("-");
                    if (!isDuplicateTxn(stripLeadingAndTrailingQuotes(pojo.getControlNumber()))) {
                        int index = getAcNoIndex(acNoList, rrnData[1].trim(), rrnData[1].trim().substring(6));
                        if (index >= 0) {
                            Vector vect = (Vector) acNoList.get(index);

                            String crAcNo = vect.get(0).toString();
                            String acStatus = vect.get(3).toString();
                            String details = "Refund Processing against the RRN " + rrnData[1].trim();

                            if (acStatus.equals("1")) {
                                totalRefundAmt = totalRefundAmt.add(new BigDecimal(pojo.getDisputeAmount()));
                                String result = insertRefundTxn(trsNo, recNo, userName, orgBrCode, crAcNo, isoAccount, orgnAlphaCode, details,
                                        Double.parseDouble(pojo.getDisputeAmount()));
                                recNo = recNo + 1;
                                if (!result.equals("true")) {
                                    throw new ApplicationException("Problem in data insertion for " + crAcNo);
                                }
                                query = query + trsNo + ",'Success'), ";
                            } else {
                                query = query + "0,'Account does not operative'), ";
                            }
                        } else {
                            query = query + "0,'Either relative POS/e-Com transaction or Account does not exist'), ";
                        }
                    }else{
                        query = query + "0,'Duplicate Control Number'), ";
                    }
                } else {
                    query = query + "0,'This is not for refund'), ";
                }
            }

            if (totalRefundAmt.doubleValue() > 0) {
                recNo = recNo + 1;
                int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, trantype,details, iy, instno, instdt,"
                        + "enterby, auth, recno,payby,authby, trsno, trandesc, tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,"
                        + "term_id,adviceno,advicebrncode) values('" + nfsHead + "',1,'" + ymd.format(new Date()) + "','" + ymd.format(new Date())
                        + "'," + totalRefundAmt + ",0, 0,2,'POS/eCom refund process',0,'','19000101','" + userName + "'," + "'Y'," + recNo
                        + ",3,'System'," + trsNo + ",135,0,'','',now(),'" + orgBrCode + "'," + "'" + orgBrCode + "',0,'','','' )").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + nfsHead);
                }
                String result = ftsRemote.updateBalance("PO", nfsHead, 0, totalRefundAmt.doubleValue(), "Y", "Y");
                if (!result.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Balance Updation Problem For A/c No-->" + nfsHead);
                }
            }
            int i = em.createNativeQuery(stripLeadingAndTrailingComa(query)).executeUpdate();
            if (i <= 0) {
                throw new ApplicationException("Problem in Data Insertion");
            }

            ftsRemote.updateRecNo(recNo);
            ut.commit();
//            try {
//                smsFacade.sendSmsToBatch(smsList);
//            } catch (ApplicationException e) {
//                System.out.println("Problem In SMS Sending In Upload Neft Rtgs." + e.getMessage());
//            }
            if (totalRefundAmt.doubleValue() <= 0) {
                return "File has been uploaded successfully. Please Check the Report";
            } else {
                return "File has been uploaded successfully and the Batch No = " + trsNo;
            }

        } catch (NotSupportedException | SystemException | ApplicationException | NumberFormatException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException | SecurityException | SystemException | ApplicationException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    private int getAcNoIndex(List acNoList, String rrn, String stn) {
        for (int i = 0; i < acNoList.size(); i++) {
            Vector elem = (Vector) acNoList.get(i);
            if (rrn.equals(elem.get(2).toString()) && stn.equals(elem.get(1).toString())) {
                return i;
            }
        }
        return -1;
    }

    public String stripLeadingAndTrailingQuotes(String str) {
        str = str.trim();
        if (str.startsWith("'")) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith("'")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public String stripLeadingAndTrailingComa(String str) {
        str = str.trim();
        if (str.startsWith(",")) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private boolean isDuplicateTxn(String ctrlNo) throws ApplicationException{
        List dataList = em.createNativeQuery("select controlnumber from atm_dispute_details where controlnumber = '" + ctrlNo
                + "' and reason = 'Success' and trsno <> 0").getResultList();
        if (!dataList.isEmpty()) {
            return true;
        }
        return false;
    }

    public String insertRefundTxn(float trsNo, float recNo, String user, String orgnBrCode, String partyAc, String isoAccount,
            String orgnAlphaCode, String details, double amount) throws ApplicationException {
        String consoleGlHead = "", orgnIsoAcNo = "";
        try {

            consoleGlHead = partyAc.substring(0, 2).concat(isoAccount);
            orgnIsoAcNo = orgnBrCode.concat(isoAccount);

            String narration = "AT " + orgnAlphaCode + ": " + details;

            List dataList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '"
                    + partyAc.substring(2, 4) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define Intersole A/c in abb parameter info");
            }
            Vector dataVec = (Vector) dataList.get(0);
            String partyNature = dataVec.get(0).toString();

            String partyBrCode = partyAc.substring(0, 2);
            //Party A/c

            String result = ftsRemote.insertRecons(partyNature, partyAc, 0, amount,
                    ymd.format(new Date()), ymd.format(new Date()), 2, narration, user, trsNo, "",
                    recNo, "Y", "System", 135, 3, "", "", 0f, "", "A", 0,
                    "", 0f, "", null, orgnBrCode, partyBrCode, 0, "", "", "");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Insertion Problem In Recons For A/c No-->" + partyAc);
            }

            result = ftsRemote.updateBalance(partyNature, partyAc, amount, 0, "Y", "Y");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Balance Updation Problem For A/c No-->" + partyAc);
            }
            ftsRemote.lastTxnDateUpdation(partyAc);
            //Party Branch ISO.
            recNo = recNo + 1;
            int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, "
                    + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                    + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                    + "advicebrncode) values('" + consoleGlHead + "',1,'" + ymd.format(new Date()) + "',"
                    + "'" + ymd.format(new Date()) + "'," + amount + ",0, 0,2,'" + narration + "',"
                    + "0,'','19000101','" + user + "','Y'," + recNo + ",3,'System',"
                    + "" + trsNo + ",135,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
            }

            //Origin Branch ISO.
            recNo = recNo + 1;
            n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, "
                    + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                    + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                    + "advicebrncode) values('" + orgnIsoAcNo + "',0,'" + ymd.format(new Date()) + "',"
                    + "'" + ymd.format(new Date()) + "',0," + amount + ", 0,2,'" + narration + "',"
                    + "0,'','19000101','" + user + "','Y'," + recNo + ",3,'System',"
                    + "" + trsNo + ",135,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }
}
