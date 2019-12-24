/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.sms;

import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.sms.service.SmsMessage;
import com.cbs.sms.service.SmsSender;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TimerServiceFacade implements TimerServiceFacadeRemote {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    SmsManagementFacadeRemote smsRemote;
    @EJB
    SmsSender smsSender;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dhmsss = new SimpleDateFormat("ddHHmmssSSS");

    public boolean isNewGl() throws ApplicationException {
        try {
            List userDataList = entityManager.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE "
                    + "REPORTNAME='GL PATTERN'").getResultList();
            if (userDataList.isEmpty()) {
                throw new ApplicationException("Code does not exist.");
            } else {
                Vector userDataVect = (Vector) userDataList.get(0);
                String glP = userDataVect.get(0).toString();
                //if 1 then new other wise old;
                if (glP.equalsIgnoreCase("1")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isSMSModuleOn() throws ApplicationException {
        try {
            List list = entityManager.createNativeQuery("SELECT code from parameterinfo_report where "
                    + "reportname='SMS'").getResultList();
            if (list.isEmpty()) {
                return false;
            } else {
                Vector element = (Vector) list.get(0);
                if (element.get(0) != null) {
                    if (element.get(0).toString().equals("1")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isAtmSmsModuleOn() throws ApplicationException {
        try {
            List list = entityManager.createNativeQuery("SELECT code from parameterinfo_report where "
                    + "reportname='ATM-SMS'").getResultList();
            if (list.isEmpty()) {
                return false;
            } else {
                Vector element = (Vector) list.get(0);
                if (element.get(0) != null) {
                    if (element.get(0).toString().equals("1")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void sendMessage() throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List<TransferSmsRequestTo> smsList = new ArrayList<>();
        try {
            ut.begin();
            synchronized (TimerServiceFacade.class) {
                String query = "";
                int smsCode = fts.getCodeForReportName("ATM-SMS");
                if (smsCode == 1) {
                    query = "select ifnull(message_id,''),ifnull(acno,''),ifnull(amount,0),"
                            + "ifnull(module_name,''),ifnull(txn_type,''),ifnull(balance,0),ifnull(ref_no,''),ifnull(details,'') "
                            + "from mb_sms_detail where msg_status=1 and module_name='ATM'";
                }
                smsCode = fts.getCodeForReportName("IMPS-SMS");
                if (smsCode == 1) {
                    if (query.equals("")) {
                        query = "select ifnull(message_id,''),ifnull(acno,''),ifnull(amount,0),"
                                + "ifnull(module_name,''),ifnull(txn_type,''),ifnull(balance,0),ifnull(ref_no,''),ifnull(details,'') "
                                + "from mb_sms_detail where msg_status=1 and module_name='IMPS'";
                    } else {
                        query = query + " union all " + "select ifnull(message_id,''),ifnull(acno,''),ifnull(amount,0),"
                                + "ifnull(module_name,''),ifnull(txn_type,''),ifnull(balance,0),ifnull(ref_no,''),ifnull(details,'') "
                                + "from mb_sms_detail where msg_status=1 and module_name='IMPS'";
                    }
                }

//                List list = entityManager.createNativeQuery("select ifnull(message_id,''),ifnull(acno,''),ifnull(amount,0),"
//                        + "ifnull(module_name,''),ifnull(txn_type,''),ifnull(balance,0),ifnull(ref_no,''),ifnull(details,'') "
//                        + "from mb_sms_detail where msg_status=1").getResultList();

                List list = new ArrayList();
                if (query != null && !query.trim().equals("")) {
                    list = entityManager.createNativeQuery(query).getResultList();
                }
                System.out.println("ATM List Size-->" + list.size());
                if (!list.isEmpty()) {
                    List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                    String templateBankName = bankTo.get(0).getTemplateBankName().trim();
                    for (int i = 0; i < list.size(); i++) {
                        Vector ele = (Vector) list.get(i);
                        String msgId = ele.get(0).toString();
                        String acno = ele.get(1).toString();
                        Double amount = Double.parseDouble(ele.get(2).toString());
                        String moduleName = ele.get(3).toString();
                        String txnType = ele.get(4).toString();
                        String balance = ele.get(5).toString();
                        String refNo = ele.get(6).toString();
                        String details = ele.get(7).toString();

                        if (msgId.equals("") || acno.equals("") || amount == 0
                                || moduleName.equals("") || txnType.equals("")
                                || refNo.equals("") || details.equals("")) {
                            continue;
                        }
                        //Extracting Mobile No
                        String mobileNo = "";
                        List smsData = entityManager.createNativeQuery("select mobile_no from mb_subscriber_tab "
                                + "where acno='" + acno + "' and status=1 and auth='Y' and auth_status='V'").getResultList();
                        if (!smsData.isEmpty()) {
                            Vector smsEle = (Vector) smsData.get(0);
                            mobileNo = smsEle.get(0).toString().trim();
                        } else {
                            smsData = entityManager.createNativeQuery("select ifnull(mobilenumber,'') as mobile from "
                                    + "cbs_customer_master_detail cu,customerid id where cu.customerid=id.custid "
                                    + "and id.acno='" + acno + "'").getResultList();
                            if (!smsData.isEmpty()) {
                                Vector smsEle = (Vector) smsData.get(0);
                                mobileNo = "+91" + smsEle.get(0).toString().trim();
                            }
                        }
                        mobileNo = mobileNo.trim().length() != 13 ? "" : mobileNo.trim();

                        int msgStatus = 4;
                        if (mobileNo.length() == 13) {
                            TransferSmsRequestTo trfSmsTo = new TransferSmsRequestTo();
                            trfSmsTo.setAcno(acno);
                            trfSmsTo.setPromoMobile(mobileNo);
                            trfSmsTo.setBankName(templateBankName);
                            trfSmsTo.setAmount(amount);
                            trfSmsTo.setUserMsgId(msgId);
                            trfSmsTo.setDate(dmy.format(new Date()));
                            trfSmsTo.setModuleName(moduleName);
                            trfSmsTo.setTxnType(txnType);
                            trfSmsTo.setAtmBal(balance);
                            trfSmsTo.setFirstCheque(refNo);
                            trfSmsTo.setLastCheque(details);
                            smsList.add(trfSmsTo);

                            msgStatus = 2;
                        }
                        //Updating the status
                        int n = entityManager.createNativeQuery("update mb_sms_detail set msg_status=" + msgStatus + " where "
                                + "message_id='" + msgId + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem in status updation in mb_sms_detail");
                        }
                    }
                }
            }
            ut.commit();
            //Sms Sending
            try {
                smsRemote.sendAtmSms(smsList);
            } catch (Exception ex) {
                System.out.println("Problem in sending the ATM sms" + ex.getMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                System.out.println("Error in ATM SMS-->" + e.getMessage());
            }
        }
    }

    @Override
    public void sendEODMessage() throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            synchronized (TimerServiceFacade.class) {
                List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                List list = entityManager.createNativeQuery("select daybeginflag,dayendflag from cbs_bankdays "
                        + "where date='" + ymd.format(new Date()) + "'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define central SOD/EOD data.");
                }
                Vector ele = (Vector) list.get(0);
                String dayBeginFlag = ele.get(0).toString().trim();
                String dayEndFlag = ele.get(1).toString().trim();

                if ((dayBeginFlag.equalsIgnoreCase("Y") && dayEndFlag.equalsIgnoreCase("Y"))
                        || (dayBeginFlag.equalsIgnoreCase("H") && dayEndFlag.equalsIgnoreCase("Y"))) {
                    throw new ApplicationException("Dayend/Holiday has been already done.");
                }

                list = entityManager.createNativeQuery("select acno from mb_push_msg_tab where module_name='EOD-SMS' and "
                        + "date_format(dt,'%Y%m%d')='" + ymd.format(new Date()) + "'").getResultList();
                if (!list.isEmpty()) {
                    throw new ApplicationException("EOD Sms has been already sent.");
                }

                list = entityManager.createNativeQuery("select mobile,sms_hour,sms_minute from day_end_sms").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("There is no parameter to send the EOD SMS");
                }
                ele = (Vector) list.get(0);
                String mobile = ele.get(0).toString();
                int hour = Integer.parseInt(ele.get(1).toString());
                int minute = Integer.parseInt(ele.get(2).toString());

                //Extracting current hour and minute.
                int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int curMinute = Calendar.getInstance().get(Calendar.MINUTE);

                if (curHour >= hour && curMinute >= minute) {
                    String promoMsg = "Bank day end has not been done. " + templateBankName;

                    String[] arr = mobile.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        System.out.println("Mobile No Is-->" + arr[i].trim());
                        String smsMobile = "91" + arr[i].trim();
                        if (smsMobile.length() == 12) {
                            SmsMessage requestTo = new SmsMessage();
                            requestTo.setMessageType("EOD-SMS");
                            requestTo.setMobileNo(smsMobile); //10 digit
                            requestTo.setMessage(promoMsg);
                            requestTo.setUserName("System");

                            smsSender.sendSmsBeyondCbs(requestTo);
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception ex) {
//            ex.printStackTrace();
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                System.out.println("Error in EOD SMS-->" + e.getMessage());
            }
        }
    }
}
