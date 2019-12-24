/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cpsms;

import com.cbs.exception.ApplicationException;
import com.cbs.utils.Validator;
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

@Stateless(mappedName = "CPSMSCommonMgmtFacade")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class CPSMSCommonMgmtFacade implements CPSMSCommonMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

//    public String getMessageWiseSeqNo(String messageType, String curDt) throws Exception {
//        String nextSeqNo = "";
//        try {
//            List list = em.createNativeQuery("select ifnull(max(seqno),0)+1 from cpsms_payment_product_seq "
//                    + "where return_message_type='" + messageType + "' and dt='" + curDt + "'").getResultList();
//            Vector ele = (Vector) list.get(0);
//            nextSeqNo = ele.get(0).toString();
//
//            int n = em.createNativeQuery("insert into cpsms_payment_product_seq (return_message_type, dt, seqno) "
//                    + "values ('" + messageType + "', '" + curDt + "', " + Long.parseLong(nextSeqNo) + ")").executeUpdate();
//            if (n <= 0) {
//                throw new Exception("Problem in message seq maintenance.");
//            }
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
//        return nextSeqNo;
//    }
    public String getOutwardFileName(String destination, String paymentProduct, String owMessageType,
            String dt, String iwMessageId) throws Exception {
        String owFileName = "";
        try {
            List list = em.createNativeQuery("select ifnull(max(seqno),0)+1 from cpsms_payment_product_seq "
                    + "where return_message_type='" + owMessageType + "' and dt='" + dt + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            String nextSeqNo = ele.get(0).toString();

            owFileName = destination + paymentProduct + owMessageType + sdf.format(ymd.parse(dt)) + nextSeqNo + ".xml";

            int n = em.createNativeQuery("insert into cpsms_payment_product_seq (return_message_type,dt,seqno,"
                    + "iw_file_name_or_messageid,ow_file_name) values ('" + owMessageType + "', '" + dt + "', "
                    + "" + Long.parseLong(nextSeqNo) + ",'" + iwMessageId + ".xml" + "','" + owFileName + "')").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in message seq maintenance.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return owFileName;
    }

    public List<String> getCpsmsHeaderDetail(String messageId) throws Exception {
        List<String> dataList = new ArrayList<String>();
        try {
            List list = em.createNativeQuery("select paymentproduct,source,destination,BankCode,"
                    + "BankName,RecordsCount,Error_Code,Error_Desc,Cbs_Status,date_format(Entry_Date,'%Y%m%d') as "
                    + "Entry_Date from cpsms_header where messageid='" + messageId + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data for message id:" + messageId);
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                dataList.add(ele.get(0).toString());
                dataList.add(ele.get(1).toString());
                dataList.add(ele.get(2).toString());
                dataList.add(ele.get(3).toString());
                dataList.add(ele.get(4).toString());
                dataList.add(ele.get(5).toString());
                dataList.add(ele.get(6).toString());
                dataList.add(ele.get(7).toString());
                dataList.add(ele.get(8).toString());
                dataList.add(ele.get(9).toString());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    public String[] getCpsmsBankDetail() throws Exception {
        String[] arr = new String[2];
        try {
            List list = em.createNativeQuery("select a.code,b.code from (select code from cbs_parameterinfo "
                    + "where name='BsrBankCode') a,(select code from cbs_parameterinfo where name='BankName') b").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define 3 digit BSR Bank Code and Bank Name.");
            }
            Vector ele = (Vector) list.get(0);
            String bsrBankCode = ele.get(0).toString();
            if (bsrBankCode.equals("") || bsrBankCode.trim().length() != 3) {
                throw new Exception("Please define 3 digit BSR Bank Code.");
            }
            String bankName = ele.get(1).toString();
            if (bankName.equals("")) {
                throw new Exception("Please define Bank Name.");
            }
            arr[0] = bsrBankCode;
            arr[1] = bankName;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }

    public String[] getOwSponsorBankName() throws Exception {
        String[] arr = new String[2];
        try {
            List list = em.createNativeQuery("select neft_bank_name,ow_head from cbs_auto_neft_details where "
                    + "process='AUTO' and process_type='BT'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                arr[0] = ele.get(0).toString();
                arr[1] = ele.get(1).toString();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }

    public String getBankEmailId() throws Exception {
        String emailId = "";
        try {
            List list = em.createNativeQuery("select ifnull(bank_email,'') as bankEmail from mb_sms_sender_bank_detail").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define bank Email Id in Mb Sms Sender bank Detail Table.");
            }
            Vector ele = (Vector) list.get(0);
            emailId = ele.get(0).toString();
            if (emailId.equals("")) {
                throw new Exception("Please define bank Email Id in Mb Sms Sender bank Detail Table.");
            }
            if (!new Validator().validateEmail(emailId)) {
                throw new ApplicationException("Please fill proper email id in Mb Sms Sender bank Detail Table.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return emailId;
    }
}
