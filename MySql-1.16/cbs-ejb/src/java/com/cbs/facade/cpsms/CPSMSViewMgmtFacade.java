/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cpsms;

import com.cbs.constant.CpsmsEnum;
import com.cbs.constant.CpsmsErrorEnum;
import com.cbs.dto.cpsms.CpsmsCommonDTO;
import com.cbs.dto.neftrtgs.NeftOwDetailsTO;
import com.cbs.dto.report.CPSMSBatchDetailPojo;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "CPSMSViewMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CPSMSViewMgmtFacade implements CPSMSViewMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileDetail(String dt, String brncode) throws Exception {
        List<CpsmsCommonDTO> dataList = new ArrayList<CpsmsCommonDTO>();
        try {
//            List list = em.createNativeQuery("select cd.header_messageid,cd.cpsms_batch_no,sum(cd.c4063_dr_amount) as "
//                    + "dr_amount,sum(cd.c4038_cr_amount) as cr_amount,substring(cd.c6021_agency_bank_acno,1,2) as "
//                    + "brncode from cpsms_header ch,cpsms_batch_detail cb,cpsms_detail cd where "
//                    + "cd.header_messageid=ch.messageid and cd.header_messageid=cb.header_messageid and "
//                    + "cd.cpsms_batch_no=cb.cpsms_batch_no and cb.header_messageid=ch.messageid and "
//                    + "ch.entry_date=cb.entry_date and ch.cbs_status=cb.cbs_status and "
//                    + "ch.entry_date='" + dt + "' and (ch.cbs_status in('" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "') "
//                    + "or ch.cbs_status in('" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "')) group by "
//                    + "cd.header_messageid,cd.cpsms_batch_no").getResultList();

            List list = em.createNativeQuery("select cd.header_messageid,cd.cpsms_batch_no,sum(cd.c4063_dr_amount) as "
                    + "dr_amount,sum(cd.c4038_cr_amount) as cr_amount,substring(cd.c6021_agency_bank_acno,1,2) as brncode "
                    + "from cpsms_header ch,cpsms_batch_detail cb,cpsms_detail cd where cd.header_messageid=ch.messageid and "
                    + "cd.header_messageid=cb.header_messageid and cd.cpsms_batch_no=cb.cpsms_batch_no and "
                    + "cb.header_messageid=ch.messageid and ch.entry_date=cb.entry_date and ch.entry_date='" + dt + "' and "
                    + "ch.cbs_status = '" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' and "
                    + "(cb.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' or "
                    + "cb.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "') group by "
                    + "cd.header_messageid,cd.cpsms_batch_no").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to process.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                CpsmsCommonDTO dto = new CpsmsCommonDTO();

                if (ele.get(4).toString().equalsIgnoreCase(brncode)) {
                    dto.setMessageId(ele.get(0).toString());
                    dto.setBatchNo(ele.get(1).toString());
                    dto.setTotalDebit(new BigDecimal(ele.get(2).toString()));
                    dto.setTotalCredit(new BigDecimal(ele.get(3).toString()));

                    dataList.add(dto);
                }
            }
            if (dataList.isEmpty()) {
                throw new Exception("There is no data to process.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileBatchDetail(String dt, String messageId, String batchNo) throws Exception {
        List<CpsmsCommonDTO> dataList = new ArrayList<CpsmsCommonDTO>();
        try {
            List list = em.createNativeQuery("select cd.C6021_Agency_Bank_Acno,cd.C6091_Sending_Agency_Acname,"
                    + "cd.Account_Type,cd.C4063_Dr_Amount from cpsms_header ch,cpsms_batch_detail cb,cpsms_detail cd "
                    + "where cd.header_messageid=ch.messageid and cd.header_messageid=cb.header_messageid and "
                    + "cd.cpsms_batch_no=cb.cpsms_batch_no and cb.header_messageid=ch.messageid and "
                    + "ch.entry_date=cb.entry_date and ch.cbs_status=cb.cbs_status and ch.entry_date='" + dt + "' and "
                    + "ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' and cd.account_type='DR' and "
                    + "cd.cpsms_batch_no='" + batchNo + "' and cd.header_messageid='" + messageId + "' "
                    + "union all "
                    + "select cd.C6061_Ben_Acno,cd.C6081_Ben_Name,cd.Account_Type,cd.C4038_Cr_Amount from cpsms_header ch,"
                    + "cpsms_batch_detail cb,cpsms_detail cd where cd.header_messageid=ch.messageid and "
                    + "cd.header_messageid=cb.header_messageid and cd.cpsms_batch_no=cb.cpsms_batch_no and "
                    + "cb.header_messageid=ch.messageid and ch.entry_date=cb.entry_date and ch.cbs_status=cb.cbs_status "
                    + "and ch.entry_date='" + dt + "' and ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' and "
                    + "cd.account_type='CR' and cd.cpsms_batch_no='" + batchNo + "' and "
                    + "cd.header_messageid='" + messageId + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                CpsmsCommonDTO dto = new CpsmsCommonDTO();

                dto.setAcno(ele.get(0).toString());
                dto.setName(ele.get(1).toString());
                dto.setAccountType(ele.get(2).toString());
                dto.setAmount(new BigDecimal(ele.get(3).toString()));

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileBatchDrDetail(String dt, String messageId, String batchNo) throws Exception {
        List<CpsmsCommonDTO> dataList = new ArrayList<CpsmsCommonDTO>();
        try {
//            List list = em.createNativeQuery("select cd.C5756_Agency_Bank_Branch_IFSC,cd.C6021_Agency_Bank_Acno,"
//                    + "cd.C6091_Sending_Agency_Acname,cd.C4063_Dr_Amount,C2020_Dr_Transaction_Id from cpsms_header ch,"
//                    + "cpsms_batch_detail cb,cpsms_detail cd where cd.header_messageid=ch.messageid and "
//                    + "cd.header_messageid=cb.header_messageid and cd.cpsms_batch_no=cb.cpsms_batch_no and "
//                    + "cb.header_messageid=ch.messageid and ch.entry_date=cb.entry_date and ch.cbs_status=cb.cbs_status "
//                    + "and ch.entry_date='" + dt + "' and (ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' "
//                    + "or ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "') and cd.account_type='DR' and "
//                    + "cd.cpsms_batch_no='" + batchNo + "' and cd.header_messageid='" + messageId + "'").getResultList();

            List list = em.createNativeQuery("select cd.C5756_Agency_Bank_Branch_IFSC,cd.C6021_Agency_Bank_Acno,"
                    + "cd.C6091_Sending_Agency_Acname,cd.C4063_Dr_Amount,C2020_Dr_Transaction_Id from cpsms_header ch,"
                    + "cpsms_batch_detail cb,cpsms_detail cd where cd.header_messageid=ch.messageid and "
                    + "cd.header_messageid=cb.header_messageid and cd.cpsms_batch_no=cb.cpsms_batch_no and "
                    + "cb.header_messageid=ch.messageid and ch.entry_date=cb.entry_date and ch.entry_date='" + dt + "' and "
                    + "ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' and "
                    + "(cb.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' or "
                    + "cb.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "') and cd.account_type='DR' and "
                    + "cd.cpsms_batch_no='" + batchNo + "' and cd.header_messageid='" + messageId + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                CpsmsCommonDTO dto = new CpsmsCommonDTO();

                dto.setIfsc(ele.get(0).toString());
                dto.setAcno(ele.get(1).toString());
                dto.setName(ele.get(2).toString());
                dto.setAmount(new BigDecimal(ele.get(3).toString()));
                dto.setTranId(ele.get(4).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileBatchCrDetail(String dt, String messageId, String batchNo) throws Exception {
        List<CpsmsCommonDTO> dataList = new ArrayList<CpsmsCommonDTO>();
        try {
//            List list = em.createNativeQuery("select cd.cpsmstranid_cr_tran_id,cd.c5569_ben_ifsc,cd.c6061_ben_acno,"
//                    + "cd.uid,cd.bankiin,cd.c4038_cr_amount,cd.c6081_ben_name from cpsms_header ch,cpsms_batch_detail cb,"
//                    + "cpsms_detail cd where cd.header_messageid=ch.messageid and cd.header_messageid=cb.header_messageid "
//                    + "and cd.cpsms_batch_no=cb.cpsms_batch_no and cb.header_messageid=ch.messageid and "
//                    + "ch.entry_date=cb.entry_date and ch.cbs_status=cb.cbs_status and ch.entry_date='" + dt + "' and "
//                    + "(ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' or ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "') and cd.account_type='CR' "
//                    + "and cd.cpsms_batch_no='" + batchNo + "' and cd.header_messageid='" + messageId + "'").getResultList();

            List list = em.createNativeQuery("select cd.cpsmstranid_cr_tran_id,cd.c5569_ben_ifsc,cd.c6061_ben_acno,"
                    + "cd.uid,cd.bankiin,cd.c4038_cr_amount,cd.c6081_ben_name from cpsms_header ch,cpsms_batch_detail cb,"
                    + "cpsms_detail cd where cd.header_messageid=ch.messageid and cd.header_messageid=cb.header_messageid and "
                    + "cd.cpsms_batch_no=cb.cpsms_batch_no and cb.header_messageid=ch.messageid and ch.entry_date=cb.entry_date "
                    + "and ch.entry_date='" + dt + "' and ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' "
                    + "and (cb.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' or "
                    + "cb.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "') and cd.account_type='CR' "
                    + "and cd.cpsms_batch_no='" + batchNo + "' and cd.header_messageid='" + messageId + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                CpsmsCommonDTO dto = new CpsmsCommonDTO();

                dto.setTranId(ele.get(0).toString());
                dto.setIfsc(ele.get(1).toString());
                dto.setAcno(ele.get(2).toString());
                dto.setUid(ele.get(3) == null ? "" : ele.get(3).toString());
                dto.setBankIIN(ele.get(4) == null ? "" : ele.get(4).toString());
                dto.setAmount(new BigDecimal(ele.get(5).toString()));
                dto.setName(ele.get(6).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    public List isIntraBankAccountNo(String acno) throws Exception {
        try {
            return em.createNativeQuery("select new_ac_no from cbs_acno_mapping where "
                    + "old_ac_no in('" + acno + "') or new_ac_no in('" + acno + "')").getResultList();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public CpsmsCommonDTO getPrintAdviceBatchDetail(String dt, String messageId, String batchNo) throws Exception {
        CpsmsCommonDTO dto = new CpsmsCommonDTO();
        try {
            List list = em.createNativeQuery("select cbs_status from cpsms_batch_detail where "
                    + "header_messageid='" + messageId + "' and cpsms_batch_no='" + batchNo + "' and "
                    + "entry_date='" + dt + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data for this messageid and batch.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                dto.setCbsStatus(ele.get(0).toString());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dto;
    }

    public List<String> retrievePaymentReceivedDates(String fileType, String brnCode) throws Exception {
        List<String> dateList = new ArrayList<String>();
        try {
            List list = null;
            if (fileType.equals("IBB")) { //Bulk
                list = em.createNativeQuery("select distinct(date_format(payment_file_received_dt,'%d/%m/%Y')) from "
                        + "cpsms_inter_batch_detail where outgoing_flag='R' and upload_branch_code='" + brnCode + "'").getResultList();
            } else if (fileType.equals("IBI")) { //Individual
                list = em.createNativeQuery("select distinct(date_format(dt,'%d/%m/%Y')) from neft_ow_details where "
                        + "success_to_failure_flag='R' and orgbrnid='" + brnCode + "' "
                        + "and (cpsms_messageid is not null and cpsms_messageid<>'')").getResultList();
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                dateList.add(ele.get(0).toString());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dateList;
    }

    public List<String> retrievePaymentReceivedMessageId(String fileType, String brnCode, String paymentReceivedDt) throws Exception {
        List<String> returnList = new ArrayList<String>();
        try {
            List list = null;
            if (fileType.equals("IBB")) { //Bulk
                list = em.createNativeQuery("select distinct(payment_file_messageid) from cpsms_inter_batch_detail "
                        + "where payment_file_received_dt='" + paymentReceivedDt + "' and outgoing_flag='R' and "
                        + "upload_branch_code='" + brnCode + "'").getResultList();
            } else if (fileType.equals("IBI")) { //Individual
                list = em.createNativeQuery("select distinct(cpsms_messageid) from neft_ow_details where "
                        + "dt='" + paymentReceivedDt + "' and success_to_failure_flag='R' and "
                        + "orgbrnid='" + brnCode + "' and (cpsms_messageid is not null and cpsms_messageid<>'')").getResultList();
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                returnList.add(ele.get(0).toString());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnList;
    }

    public List<String> retrievePaymentReceivedBatchNo(String fileType, String brnCode, String paymentReceivedDt,
            String messageId) throws Exception {
        List<String> returnList = new ArrayList<String>();
        try {
            List list = null;
            if (fileType.equals("IBB")) { //Bulk
                list = em.createNativeQuery("select distinct(payment_file_batch_no) from cpsms_inter_batch_detail "
                        + "where payment_file_received_dt='" + paymentReceivedDt + "' and outgoing_flag='R' and "
                        + "upload_branch_code='" + brnCode + "' and payment_file_messageid='" + messageId + "'").getResultList();
            } else if (fileType.equals("IBI")) { //Individual
                list = em.createNativeQuery("select distinct(cpsms_batch_no) from neft_ow_details "
                        + "where dt='" + paymentReceivedDt + "' and success_to_failure_flag='R' and "
                        + "orgbrnid='" + brnCode + "' and cpsms_messageid = '" + messageId + "'").getResultList();
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                returnList.add(ele.get(0).toString());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnList;
    }

    public List<NeftOwDetailsTO> getInterBankOutwardDetail(String fileType, String paymentReceivedDt, String messageId,
            String batchNo, String brnCode) throws Exception {
        List<NeftOwDetailsTO> returnList = new ArrayList<NeftOwDetailsTO>();
        try {
            List dataList = new ArrayList();
            String query = "select paymenttype,uniquecustomerrefno,beneficiaryname,txnamt,creditaccountno,"
                    + "outsidebankifsccode,debitaccountno,cmsbankrefno,utrno,status,reason,details,"
                    + "cpsmstranid_cr_tran_id,date_format(response_update_time,'%Y%m%d'),success_to_failure_flag,ifnull(debit_success_trsno,0) "
                    + "from neft_ow_details where orgbrnid='" + brnCode + "' and dt='" + paymentReceivedDt + "' and "
                    + "cpsms_messageid='" + messageId + "' and cpsms_batch_no='" + batchNo + "'";
            if (fileType.equals("IBB")) { //Bulk
                dataList = em.createNativeQuery(query + " and (success_to_failure_flag='' or success_to_failure_flag='R')").getResultList();
            } else if (fileType.equals("IBI")) { //Individual
                dataList = em.createNativeQuery(query + " and success_to_failure_flag='R'").getResultList();
            }
            for (int i = 0; i < dataList.size(); i++) {
                NeftOwDetailsTO dto = new NeftOwDetailsTO();
                Vector ele = (Vector) dataList.get(i);

                dto.setPaymentType(ele.get(0).toString());
                dto.setUniqueCustomerRefNo(ele.get(1).toString());
                dto.setBeneficiaryName(ele.get(2).toString());
                dto.setTxnAmt(new BigDecimal(ele.get(3).toString()));
                dto.setCreditAccountNo(ele.get(4).toString());
                dto.setOutsideBankIfscCode(ele.get(5).toString());
                dto.setDebitAccountNo(ele.get(6).toString());
                dto.setCmsBankRefNo(ele.get(7) == null ? "" : ele.get(7).toString());
                dto.setUtrNo(ele.get(8) == null ? "" : ele.get(8).toString());
                dto.setStatus(ele.get(9).toString());
                dto.setReason(ele.get(10) == null ? "" : ele.get(10).toString());
                dto.setDetails(ele.get(11) == null ? "" : ele.get(11).toString());
                dto.setCreditTranId(ele.get(12) == null ? "" : ele.get(12).toString());
                dto.setResponseUpdateTime(ele.get(13) == null ? "" : ele.get(13).toString());
                dto.setSuccessToFailureFlag(ele.get(14) == null ? "" : ele.get(14).toString());
                dto.setDebitSuccessTrsNo(ele.get(15) == null ? 0f : Float.parseFloat(ele.get(15).toString()));

                returnList.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnList;
    }

    public List<CpsmsCommonDTO> getPrintAdvicePaymentFileBatchDrDetailForIBResponse(String dt, String messageId, String batchNo) throws Exception {
        List<CpsmsCommonDTO> dataList = new ArrayList<CpsmsCommonDTO>();
        try {
            List list = em.createNativeQuery("select cd.C5756_Agency_Bank_Branch_IFSC,cd.C6021_Agency_Bank_Acno,"
                    + "cd.C6091_Sending_Agency_Acname,cd.C4063_Dr_Amount,C2020_Dr_Transaction_Id from cpsms_header ch,"
                    + "cpsms_batch_detail cb,cpsms_detail cd where cd.header_messageid=ch.messageid and "
                    + "cd.header_messageid=cb.header_messageid and cd.cpsms_batch_no=cb.cpsms_batch_no and "
                    + "cb.header_messageid=ch.messageid and ch.entry_date=cb.entry_date and ch.entry_date='" + dt + "' and "
                    + "(ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' or "
                    + "ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_04.getCode() + "' or "
                    + "ch.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_07.getCode() + "') and "
                    + "(cb.cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_03.getCode() + "') and "
                    + "cd.account_type='DR' and cd.cpsms_batch_no='" + batchNo + "' and "
                    + "cd.header_messageid='" + messageId + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                CpsmsCommonDTO dto = new CpsmsCommonDTO();

                dto.setIfsc(ele.get(0).toString());
                dto.setAcno(ele.get(1).toString());
                dto.setName(ele.get(2).toString());
                dto.setAmount(new BigDecimal(ele.get(3).toString()));
                dto.setTranId(ele.get(4).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    public List getcpsmsMassageId(String date) throws Exception {
        List list;
        try {
            list = em.createNativeQuery("select DISTINCT Header_MessageId from cpsms_batch_detail "
                    + " where Entry_Time='" + ymd.format(dmy.parse(date)) + "';").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public List getcpsmsBatchNo(String date, String cpsmsMsgId) throws Exception {
        List list;
        try {
            list = em.createNativeQuery("select DISTINCT CPSMS_Batch_No from cpsms_batch_detail where "
                    + "Header_MessageId='" + cpsmsMsgId + "' and Entry_Time='" + ymd.format(dmy.parse(date)) + "';").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;

    }

    public List<CPSMSBatchDetailPojo> getCPSMSBatchDetailReport(String date, String cpsmsMsgId, String cpsmsBatchNo) throws Exception {
        List<CPSMSBatchDetailPojo> dataList = new ArrayList<CPSMSBatchDetailPojo>();
        try {
            List list = em.createNativeQuery("select Account_Type,C4063_Dr_Amount,C5756_Agency_Bank_Branch_IFSC,C6021_Agency_Bank_Acno,"
                    + " C6091_Sending_Agency_Acname,C4038_Cr_Amount,C5569_Ben_IFSC,C6061_Ben_Acno,C6081_Ben_Name,ifnull(Cbs_Status,'')"
                    + " from cpsms_detail where Header_MessageId='" + cpsmsMsgId + "' and CPSMS_Batch_No='" + cpsmsBatchNo + "' "
                    + " and  Entry_Date='" + ymd.format(dmy.parse(date)) + "' order by Account_Type desc;").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to show.");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    CPSMSBatchDetailPojo dto = new CPSMSBatchDetailPojo();
                    dto.setTranType(ele.get(0).toString());
                    dto.setDebitAmt(new BigDecimal(ele.get(1).toString()));
                    dto.setDebitorIfsc(ele.get(2).toString());
                    dto.setDebitAccNo(ele.get(3).toString());
                    dto.setDebitName(ele.get(4).toString());
                    dto.setCraditAmt(new BigDecimal(ele.get(5).toString()));
                    dto.setBeneIfsc(ele.get(6).toString());
                    dto.setBeneAccNo(ele.get(7).toString());
                    dto.setBeneName(ele.get(8).toString());
                    String cbsStatus = ele.get(9).toString().trim();
                    cbsStatus = cbsStatus.equals("") ? cbsStatus : CpsmsErrorEnum.getValue(cbsStatus);
                    dto.setStatus(cbsStatus);
                    if (dto.getTranType().equalsIgnoreCase("DR")) {
                        dto.setAmmount(dto.getDebitAmt());
                    } else if (dto.getTranType().equalsIgnoreCase("CR")) {
                        dto.setAmmount(dto.getCraditAmt());
                    }
                    dataList.add(dto);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String[] getcpsmsBatchNoStatusAndTxnNo(String date, String cpsmsMsgId, String cpsmsBatchNo) throws Exception {

        String[] data = new String[2];
        try {
            List list = em.createNativeQuery("select Cbs_Status,Debit_Success_Trsno,CPSMS_Batch_No from cpsms_batch_detail"
                    + " where Header_MessageId='" + cpsmsMsgId + "' and CPSMS_Batch_No='" + cpsmsBatchNo + "'"
                    + " and  Entry_Date='" + ymd.format(dmy.parse(date)) + "' ;").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data .");
            } else {
                Vector ele = (Vector) list.get(0);
                data[0] = ele.get(0).toString();
                data[1] = ele.get(1).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


        return data;
    }
}
