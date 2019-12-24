/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.sms;

import com.cbs.constant.PullSmsCode;
import com.cbs.dao.sms.MbPullMsgTabDAO;
import com.cbs.dao.sms.MbSubscriberServicesDAO;
import com.cbs.dao.sms.MbSubscriberTabDAO;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.entity.sms.MbPullMsgTab;
import com.cbs.entity.sms.MbPullMsgTabPK;
import com.cbs.entity.sms.MbSubscriberServices;
import com.cbs.entity.sms.MbSubscriberTab;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.sms.service.SmsMessage;
import com.cbs.sms.service.SmsSender;
import com.cbs.sms.service.SmsType;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "RequestReceiverFacade")
public class RequestReceiverFacade implements RequestReceiverFacadeRemote {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    SmsManagementFacadeRemote sms;
    @EJB
    TransactionManagementFacadeRemote txnRemote;
    @EJB
    CommonReportMethodsRemote commonReport;
    @EJB
    SmsSender smsSender;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.##");

    @Override
    public void processOnRequestMessage(String mobileNo, String message) throws ApplicationException {
        MbPullMsgTabDAO mbPullMsgTabDAO = new MbPullMsgTabDAO(entityManager);
        MbSubscriberServicesDAO mbSubscriberServicesDAO = new MbSubscriberServicesDAO(entityManager);
        //mobileNo--> 919818101010, message--> BAL(For Balance)
        try {
            //Insertion Into mb_pull_msg_tab Table.
            Long pullMaxTxnId = mbPullMsgTabDAO.getMaxTxnId();
            MbPullMsgTab mbPullMsgTab = new MbPullMsgTab();
            MbPullMsgTabPK mbPullMsgTabPK = new MbPullMsgTabPK();

            mbPullMsgTabPK.setTxnId(pullMaxTxnId);
            mbPullMsgTabPK.setReceivedDt(new Date());
            mbPullMsgTab.setMbPullMsgTabPK(mbPullMsgTabPK);

            mbPullMsgTab.setMobile(mobileNo);
            mbPullMsgTab.setMessage(message);
            mbPullMsgTab.setMessageStatus(1);

            mbPullMsgTabDAO.save(mbPullMsgTab);

            //Mobile Verification.
            MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
            List<MbSubscriberTab> resultList = mbSubscriberTabDAO.getAllAccountByMobile(mobileNo);
            if (!resultList.isEmpty()) {
                List<MbSmsSenderBankDetailTO> bankTo = sms.getBankAndSenderDetail();
                Integer serviceKey = Integer.parseInt(PullSmsCode.getServiceKey(message));
                for (MbSubscriberTab obj : resultList) {
                    String acno = obj.getAcno();
                    MbSubscriberServices mbSubService = mbSubscriberServicesDAO.getEntityByAcnoAndService(acno, serviceKey);
                    if (mbSubService != null) {
                        //Balance In Account Request.
                        if (message.equals("BAL")) {
                            String balance = getBal(acno).toString();
                            String balMsg = "Your available balance to a/c XXXX" + acno.substring(4, 10) + "XX is Rs. "
                                    + balance + " Thanks, " + bankTo.get(0).getTemplateBankName().trim() + ".";
                            //Sms Sending
                            try {
                                //sms.sendPromotionalSms(acno, balMsg, "+" + mobileNo, bankTo.get(0).getTemplateBankName());
                                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                                tSmsRequestTo.setMsgType("PAT");
                                tSmsRequestTo.setTemplate(SmsType.PROMOTIONAL);
                                tSmsRequestTo.setAcno(acno);
                                tSmsRequestTo.setPromoMobile("+" + mobileNo);
                                tSmsRequestTo.setBankName(bankTo.get(0).getTemplateBankName());
                                tSmsRequestTo.setPromoMsg(balMsg);

                                sms.sendSms(tSmsRequestTo);
                                //Pull Updation.
                                updateMbPullMsgTabObj(pullMaxTxnId);
                            } catch (Exception ex) {
                                System.out.println("Error In Sending Request SMS For Balance " + ex.getMessage());
                                throw new ApplicationException("Error In Sending Request SMS For Balance " + ex.getMessage());
                            }
                        }
                        //Last Three Transaction Statement Request.
                        if (message.equals("STM")) {
                            String stmtMsg = fts.getMiniStatement(acno, bankTo.get(0).getTemplateBankName().trim());
                            if (!stmtMsg.equalsIgnoreCase("")) {
                                //Sms Sending
                                try {
                                    //sms.sendPromotionalSms(acno, stmtMsg, "+" + mobileNo, bankTo.get(0).getTemplateBankName());
                                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                                    tSmsRequestTo.setMsgType("PAT");
                                    tSmsRequestTo.setTemplate(SmsType.PROMOTIONAL);
                                    tSmsRequestTo.setAcno(acno);
                                    tSmsRequestTo.setPromoMobile("+" + mobileNo);
                                    tSmsRequestTo.setBankName(bankTo.get(0).getTemplateBankName());
                                    tSmsRequestTo.setPromoMsg(stmtMsg);

                                    sms.sendSms(tSmsRequestTo);
                                    //Pull Updation.
                                    updateMbPullMsgTabObj(pullMaxTxnId);
                                } catch (Exception ex) {
                                    System.out.println("Error In Sending Request SMS For Mini Statement " + ex.getMessage());
                                    throw new ApplicationException("Error In Sending Request SMS Mini Statement " + ex.getMessage());
                                }
                            }
                        }
                    } else {
                        //Customer is not availing the particular service.
                        try {
                            String notAvailMsg = "Please contact from your bank to avail this service.";
                            //sms.sendPromotionalSms(acno, notAvailMsg, "+" + mobileNo, bankTo.get(0).getTemplateBankName());
                            TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                            tSmsRequestTo.setMsgType("PAT");
                            tSmsRequestTo.setTemplate(SmsType.PROMOTIONAL);
                            tSmsRequestTo.setAcno(acno);
                            tSmsRequestTo.setPromoMobile("+" + mobileNo);
                            tSmsRequestTo.setBankName(bankTo.get(0).getTemplateBankName());
                            tSmsRequestTo.setPromoMsg(notAvailMsg);

                            sms.sendSms(tSmsRequestTo);
                            //Pull Updation.
                            updateMbPullMsgTabObj(pullMaxTxnId);
                        } catch (Exception ex) {
                            System.out.println("Error In Sending Request SMS " + ex.getMessage());
                            throw new ApplicationException("Error In Sending Request SMS " + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Problem In processOnRequestMessage() Method " + ex.getMessage());
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void updateMbPullMsgTabObj(Long txnId) {
        MbPullMsgTabDAO mbPullMsgTabDAO = new MbPullMsgTabDAO(entityManager);
        try {
            MbPullMsgTab entity = mbPullMsgTabDAO.getEntityByTxnId(txnId);
            if (entity != null) {
                entity.setMessageStatus(2);
                mbPullMsgTabDAO.update(entity);
            }
        } catch (Exception ex) {
            System.out.println("Problem In updateMbPullMsgTabObj() Method " + ex.getMessage());
        }
    }

    public BigDecimal getBal(String acno) throws Exception {
        BigDecimal bal = new BigDecimal(0);
        try {
            //Pending Balance.
            List pendingList = txnRemote.selectPendingBalance(acno);
            Vector element = (Vector) pendingList.get(0);
            Double pendingBal = Double.parseDouble(element.get(0).toString());
            //Actual Ledger Balance.
            Double ledgerBal = commonReport.getBalanceOnDate(acno, ymd.format(new Date()));
            //Actual Balance.
            bal = new BigDecimal(formatter.format(ledgerBal - pendingBal));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return bal;
    }

    @Override
    public void missedCallHandler(String mobileNo, String channelId, String circle, String operator,
            String dateTime) throws ApplicationException {
        MbSubscriberServicesDAO mbSubscriberServicesDAO = new MbSubscriberServicesDAO(entityManager);
        try {
            List rsList = entityManager.createNativeQuery("select ifnull(max(txnid),0)+1 from missed_call_details").getResultList();
            long txnId = 0;
            if (!rsList.isEmpty()) {
                Vector verLst = (Vector) rsList.get(0);
                txnId = Long.parseLong(verLst.get(0).toString());
            }
            int rs = entityManager.createNativeQuery("INSERT INTO missed_call_details(txnid, mobileno, channelid, circle, "
                    + "operator, missedcalltime, status,ReceivedTime) VALUES (" + txnId + ",'" + mobileNo + "', '" + channelId + "', "
                    + "'" + circle + "', '" + operator + "','" + dateTime + "',1,now())").executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in missed call details insertion.");
            }

            System.out.println("Data has been inserted in CBS-->mobileNo" + mobileNo + ":-->channelId" + channelId);
            rsList = entityManager.createNativeQuery("select code from cbs_parameterinfo where name "
                    + "in('MISCALL-REQUEST')").getResultList();
            if (rsList.isEmpty()) {
                throw new ApplicationException("Please define MISCALL-REQUEST parameter..");
            }
            Vector ele = (Vector) rsList.get(0);
            String miscallRequest = ele.get(0).toString().trim();

            MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
            List<MbSubscriberTab> resultList = mbSubscriberTabDAO.getAllAccountByMobile("91" + mobileNo);

            List<MbSmsSenderBankDetailTO> bankTo = sms.getBankAndSenderDetail();
            for (MbSubscriberTab obj : resultList) {
                String acno = obj.getAcno();
                MbSubscriberServices mbSubService = mbSubscriberServicesDAO.getEntityByAcnoAndService(acno, 20);
                if (mbSubService != null) {
                    String balMsg = "";
                    if (miscallRequest.equalsIgnoreCase("STMT")) {
                        balMsg = fts.getMiniStatement(acno, bankTo.get(0).getTemplateBankName().trim());
                        if (balMsg.trim().equals("")) {
                            continue;
                        }
                    } else if (miscallRequest.equalsIgnoreCase("BAL")) {
                        //Balance In Account Request.
                        String balance = getBal(acno).toString();
                        balMsg = "Your available balance to A/c XXXX" + acno.substring(4, 10) + "XX is Rs. "
                                + balance + " .Thanks, " + bankTo.get(0).getTemplateBankName().trim() + ".";
                    }
                    System.out.println("Bal Msg Is-->" + balMsg);
                    //Sms Sending
                    try {
                        TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                        tSmsRequestTo.setMsgType("PAT");
                        tSmsRequestTo.setTemplate(SmsType.PROMOTIONAL);
                        tSmsRequestTo.setAcno(acno);

                        tSmsRequestTo.setPromoMobile("+91" + mobileNo);
                        tSmsRequestTo.setBankName(bankTo.get(0).getTemplateBankName());
                        tSmsRequestTo.setPromoMsg(balMsg);

                        sms.sendSms(tSmsRequestTo);
                        System.out.println("SMS has been sent for-->mobileNo" + mobileNo + ":-->channelId" + channelId);
                        //Pull Updation.
                        rs = entityManager.createNativeQuery("update missed_call_details set Status =2 where txnid = " + txnId).executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("Problem in missed call details updation.");
                        }
                        System.out.println("Status has been updated for-->mobileNo" + mobileNo + ":-->channelId" + channelId);
                    } catch (Exception ex) {
                        System.out.println("Error In Sending Request SMS For Balance " + ex.getMessage());
                        throw new ApplicationException("Error In Sending Request SMS For Balance " + ex.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem In missedCallHandler() Method " + e.getMessage());
            throw new ApplicationException("Error In Sending Request SMS For Balance " + e.getMessage());
        }
    }

    /**
     *
     * @param mobileNo 91XXXXXXXXXX
     * @param message
     * @throws Exception Here AN is used for Aadhaar Registration Request.
     */
    @Override
    public void onlineAadhaarRegistration(String mobileNo, String message) throws Exception {
        try {
            List<MbSmsSenderBankDetailTO> bankTo = sms.getBankAndSenderDetail();

            List list = entityManager.createNativeQuery("select m.customerid,a.acno from cbs_customer_master_detail m,"
                    + "customerid i,accountmaster a where m.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and "
                    + "a.acno='" + message.substring(13).trim() + "' "
                    + "union all "
                    + "select m.customerid,a.acno from cbs_customer_master_detail m,customerid i,td_accountmaster a "
                    + "where m.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and "
                    + "a.acno='" + message.substring(13).trim() + "'").getResultList();
            if (!list.isEmpty()) {
                list = entityManager.createNativeQuery("select ifnull(max(request_no),0)+1 from cbs_third_party_request where "
                        + "request_type='AN' and id_no='" + mobileNo + "'").getResultList();
                Vector ele = (Vector) list.get(0);
                long requestNo = Long.parseLong(ele.get(0).toString());

                int n = entityManager.createNativeQuery("insert into cbs_third_party_request(request_type,id_no,"
                        + "request_no,request,status,received_time) values('AN','" + mobileNo + "'," + requestNo + ","
                        + "'" + message + "',1,now())").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Aadhaar Registration Logging.");
                }

                String smsMessage = "Thanks for your Aadhaar registration. Aadhaar No is:" + message.substring(0, 2).trim() + "XXXXXXXX" + message.substring(10, 12).trim() + "." + bankTo.get(0).getTemplateBankName();

                SmsMessage requestTo = new SmsMessage();
                requestTo.setMessageType("AN");
                requestTo.setIdNo(mobileNo); //It can be any id no
                requestTo.setMobileNo(mobileNo); //10 digit
                requestTo.setMessage(smsMessage);
                requestTo.setUserName("System");

                smsSender.sendSmsBeyondCbs(requestTo);

                n = entityManager.createNativeQuery("update cbs_third_party_request set status=2 where request_type='AN' and "
                        + "id_no='" + mobileNo + "' and request_no=" + requestNo + "").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Aadhaar Registration Status Updation.");
                }
            } else {
                String notAvailMsg = "Please contact from your bank to avail this service.";

                SmsMessage requestTo = new SmsMessage();
                requestTo.setMessageType("AN");
                requestTo.setIdNo(mobileNo); //It can be any id no
                requestTo.setMobileNo(mobileNo); //10 digit
                requestTo.setMessage(notAvailMsg);
                requestTo.setUserName("System");

                smsSender.sendSmsBeyondCbs(requestTo);
            }
        } catch (Exception ex) {
            throw new Exception("Proble In onlineAadhaarRegistration Facade Method-->" + ex.getMessage());
        }
    }
}
