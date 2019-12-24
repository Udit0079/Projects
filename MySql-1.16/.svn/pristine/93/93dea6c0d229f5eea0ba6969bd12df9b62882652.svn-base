/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.sms;

import com.cbs.constant.CbsConstant;
import com.cbs.constant.SmsServicesEnum;
import com.cbs.dao.sms.MbChargeMasterDAO;
import com.cbs.dao.sms.MbPushMsgTabDAO;
import com.cbs.dao.sms.MbServicesDescriptionDAO;
import com.cbs.dao.sms.MbSubscriberServicesDAO;
import com.cbs.dao.sms.MbSubscriberTabDAO;
import com.cbs.dto.sms.SmsDetailsDto;
import com.cbs.entity.sms.MbChargeMaster;
import com.cbs.entity.sms.MbServicesDescription;
import com.cbs.entity.sms.MbSubscriberServices;
import com.cbs.entity.sms.MbSubscriberServicesPK;
import com.cbs.entity.sms.MbSubscriberTab;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.pojo.sms.MessageDetailPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.web.pojo.sms.AcSenderMsgPojo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

@Stateless(mappedName = "MessageDetailBean")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class MessageDetailBean implements MessageDetailBeanRemote {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    SmsManagementFacadeRemote smsRemote;
    @EJB
    private NpaReportFacadeRemote npaFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    @Override
    public List<MessageDetailPojo> getMessageDetail(String fromDate, String toDate) throws ApplicationException {
        List<MessageDetailPojo> list = new ArrayList<MessageDetailPojo>();
        MbPushMsgTabDAO pushDAO = new MbPushMsgTabDAO(entityManager);
        MbChargeMasterDAO chargeDAO = new MbChargeMasterDAO(entityManager);
        try {
            Date frmdt = dmy.parse(fromDate);
            Date todt = dmy.parse(toDate);
            List<String> messageList = pushDAO.getDistinctMessageType(frmdt, todt);
            if (!messageList.isEmpty()) {
                for (int i = 0; i < messageList.size(); i++) {
                    MessageDetailPojo obj = new MessageDetailPojo();
                    String messageType = messageList.get(i);
                    Integer noOfMsg = pushDAO.getTotalSendMessage(messageType, frmdt, todt);
                    MbChargeMaster entity = chargeDAO.getChargePerMessage();
                    double chargePerMessage = entity.getChargePerMessage();

                    obj.setMessageType(messageType);
                    obj.setTotalNoOfMessages(noOfMsg);
                    obj.setChargePerMessage(chargePerMessage);
                    obj.setTotalCharge(chargePerMessage * noOfMsg);

                    list.add(obj);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    @Override
    public List<SmsDetailsDto> getAcWiseSmsDetails(String reportType, String acno, String frDt, String toDt) throws ApplicationException {
        MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(entityManager);
        MbServicesDescriptionDAO mbServicesDescriptionDAO = new MbServicesDescriptionDAO(entityManager);
        MbSubscriberServicesDAO mbSubscriberServicesDAO = new MbSubscriberServicesDAO(entityManager);
        List<SmsDetailsDto> dataList = new ArrayList<SmsDetailsDto>();
        try {
            MbSubscriberTab subscriber = null;
            String mobile = "", status = "";
            if (!reportType.equalsIgnoreCase("Dt")) {
                subscriber = mbSubscriberTabDAO.getEntityByAcno(acno);
                mobile = subscriber.getMobileNo().substring(3);
                status = "In-Active";
                if (subscriber.getStatus().equals("1") && subscriber.getAuth().equalsIgnoreCase("Y")
                        && subscriber.getAuthStatus().equalsIgnoreCase("V")) {
                    status = "Active";
                }
            }
            if (reportType.equals("AR")) {
                SmsDetailsDto dto = new SmsDetailsDto();
                dto.setAcno(subscriber.getAcno());
                dto.setType(subscriber.getAcnoType());
                dto.setMobile(mobile);
                dto.setStatus(status);
                dto.setPin(subscriber.getPinNo());
                dto.setRegistrationDt(dmy.format(subscriber.getCreatedDate()));
                dto.setEnterBy(subscriber.getEnterBy());
                dto.setAuthBy(subscriber.getAuthBy());
                dto.setBillingAcno(subscriber.getBillDeductionAcno());

                dataList.add(dto);
            } else if (reportType.equals("AS")) {
                List<MbSubscriberServices> services = mbSubscriberServicesDAO.getEntityListByAcno(acno);
                for (MbSubscriberServices obj : services) {
                    MbSubscriberServicesPK pk = obj.getMbSubscriberServicesPK();
                    MbServicesDescription servDesc = mbServicesDescriptionDAO.getEntityByServiceCode((int) pk.getServices());

                    if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CASH_DEPOSIT.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CASH_WITHDRAWAL.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.XFER_DEPOSIT.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.XFER_WITHDRAWAL.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CLG_DEPOSIT.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CLG_WITHDRAWAL.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.INTEREST_CR.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.INTEREST_DR.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CHARGE_CR.getValue())
                            || servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CHARGE_DR.getValue())) {

                        SmsDetailsDto dto = new SmsDetailsDto();
                        dto.setAcno(subscriber.getAcno());
                        dto.setServiceName(servDesc.getServiceName());
                        dto.setMobile(mobile);
                        dto.setStatus(status);

                        if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CASH_DEPOSIT.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getCashCrLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CASH_WITHDRAWAL.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getCashDrLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.XFER_DEPOSIT.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getTrfCrLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.XFER_WITHDRAWAL.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getTrfDrLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CLG_DEPOSIT.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getClgCrLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CLG_WITHDRAWAL.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getClgDrLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.INTEREST_CR.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getInterestLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.INTEREST_DR.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getInterestLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CHARGE_CR.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getChargeLimit()));
                        } else if (servDesc.getServiceName().equalsIgnoreCase(SmsServicesEnum.CHARGE_DR.getValue())) {
                            dto.setLimit(new BigDecimal(subscriber.getChargeLimit()));
                        }
                        dataList.add(dto);
                    }
                }
            } else if (reportType.equals("TSA")) {
                SmsDetailsDto dto = new SmsDetailsDto();
                dto.setMobile(mobile);
                dto.setStatus(status);
                dto.setAcno(acno);

                Integer totalMsgNo = npaFacade.getTotalSendMessageByAcno(acno, "TRANSACTIONAL", ymd.format(dmy.parse(frDt)),
                        CbsUtil.dateAdd(ymd.format(dmy.parse(toDt)), 1));

                dto.setMsgType("TRANSACTIONAL");
                dto.setTotalMsgNo(new BigInteger(totalMsgNo.toString()));

                dataList.add(dto);

                dto = new SmsDetailsDto();
                dto.setMobile(mobile);
                dto.setStatus(status);
                dto.setAcno(acno);

                totalMsgNo = npaFacade.getTotalSendMessageByAcno(acno, "PROMOTIONAL", ymd.format(dmy.parse(frDt)),
                        CbsUtil.dateAdd(ymd.format(dmy.parse(toDt)), 1));

                dto.setMsgType("PROMOTIONAL");
                dto.setTotalMsgNo(new BigInteger(totalMsgNo.toString()));

                dataList.add(dto);
            } else if (reportType.equals("ASA") || reportType.equals("Dt")) {
                String acNoQuery = "";
                if (reportType.equals("ASA")) {
                    acNoQuery = "and acno='" + acno + "'";
                } else {
                    acNoQuery = "";
                }
                List list = entityManager.createNativeQuery("select message,message_type,date_format(dt,'%d/%m/%Y'),acno,mobile from mb_push_msg_tab "
                        + "where dt>='" + ymd.format(dmy.parse(frDt)) + "' and "
                        + "dt<'" + CbsUtil.dateAdd(ymd.format(dmy.parse(toDt)), 1) + "' " + acNoQuery + " and message_status "
                        + "in(2,3,4) order by acno").getResultList();
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        SmsDetailsDto dto = new SmsDetailsDto();
                        Vector element = (Vector) list.get(i);
                        dto.setAcno(element.get(3).toString());
                        dto.setMobile(element.get(4).toString().substring(3));
                        dto.setStatus(status);
                        dto.setMessage(element.get(0).toString());
                        dto.setMsgType(element.get(1).toString());
                        dto.setRegistrationDt(element.get(2).toString());

                        dataList.add(dto);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
    
    @Override
    public List<AcSenderMsgPojo> getAccountSmsDetail(String frdt,String todt,String acno,String detailType) throws ApplicationException{
        List list =new ArrayList();
        List<AcSenderMsgPojo> getList = new ArrayList<>();
        try{
            if(detailType.equalsIgnoreCase("DL")){
                list = entityManager.createNativeQuery("select MESSAGE_ID,MOBILE,ACNO,MESSAGE,MESSAGE_STATUS,MESSAGE_TYPE,DT,ENTER_BY from  mb_push_msg_tab "
                        + "where( DT Between '" + ymd.format(dmy.parse(frdt)) + "' and '" + ymd.format(dmy.parse(todt)) + "') and  ACNO='"+acno+"' and MESSAGE_STATUS in(2,3,4)").getResultList();
                if(list.isEmpty()){
                    throw new ApplicationException("There is no Data.");
                }
                for(int i=0; i<list.size();i++){
                    Vector ele = (Vector) list.get(i);
                    AcSenderMsgPojo data = new AcSenderMsgPojo();
                    data.setSrNo(new BigInteger(String.valueOf(i)).add(BigInteger.ONE));
                    data.setMsgId(ele.get(0).toString().trim());
                    data.setMobno(ele.get(1).toString().trim());
                    data.setAcno(ele.get(2).toString().trim());
                    data.setMsg(ele.get(3).toString().trim());
                    data.setMsgStatus(ele.get(4).toString().trim());
                    data.setMsgType(ele.get(5).toString().trim());
//                    data.setDt(dmy.format(ymd.parse(ele.get(6).toString().trim())));
                    data.setDt(ele.get(6).toString().trim());
                    data.setEnterBy(ele.get(7).toString().trim());
                    
                    getList.add(data);
                }
            }else if(detailType.equalsIgnoreCase("UL")){
                list = entityManager.createNativeQuery("select MESSAGE_ID,MOBILE,ACNO,MESSAGE,MESSAGE_STATUS,MESSAGE_TYPE,DT,ENTER_BY from  mb_push_msg_tab "
                        + "where( DT Between '" + ymd.format(dmy.parse(frdt)) + "' and '" + ymd.format(dmy.parse(todt)) + "') and ACNO='"+acno+"' and MESSAGE_STATUS=1").getResultList();
                if(list.isEmpty()){
                    throw new ApplicationException("There is no Data.");
                }
                 for(int i=0; i<list.size();i++){
                    Vector ele = (Vector) list.get(i);
                    AcSenderMsgPojo data = new AcSenderMsgPojo();
                    data.setSrNo(new BigInteger(String.valueOf(i)).add(BigInteger.ONE));
                    data.setMsgId(ele.get(0).toString().trim());
                    data.setMobno(ele.get(1).toString().trim());
                    data.setAcno(ele.get(2).toString().trim());
                    data.setMsg(ele.get(3).toString().trim());
                    data.setMsgStatus(ele.get(4).toString().trim());
                    data.setMsgType(ele.get(5).toString().trim());
//                    data.setDt(dmy.format(ymd.parse(ele.get(6).toString().trim())));
                    data.setDt(ele.get(6).toString().trim());
                    data.setEnterBy(ele.get(7).toString().trim());
                    
                    getList.add(data);
            }
          }
         }catch(Exception ex){
            throw new ApplicationException(ex.getMessage());
        }
        return getList;
    }

    @Override
    public List getMobileDetail(String brCode, String msgType) throws ApplicationException {
        List list = new ArrayList();
        try {
            if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("AL")) {
                list = entityManager.createNativeQuery("select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) "
                        + "as mobile from cbs_customer_master_detail c,customerid i,accountmaster a where "
                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and "
                        + "length(ltrim(rtrim(c.mobilenumber)))=10 and a.acno not in(select acno from mb_subscriber_tab "
                        + "where status=1 and auth='Y' and auth_status='V') group by c.customerid "
                        + "union all "
                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobile from "
                        + "cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid "
                        + "and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 and "
                        + "a.acno not in(select acno from mb_subscriber_tab where status=1 and auth='Y' and "
                        + "auth_status='V') group by c.customerid "
                        + "union all "
                        + "select '',acno,mobile_no from mb_subscriber_tab where status=1 and auth='Y' and "
                        + "auth_status='V'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("AL")) {
                list = entityManager.createNativeQuery("select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) "
                        + "as mobile from cbs_customer_master_detail c,customerid i,accountmaster a where "
                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and "
                        + "length(ltrim(rtrim(c.mobilenumber)))=10 and a.curbrcode='" + brCode + "' and a.acno "
                        + "not in(select acno from mb_subscriber_tab where status=1 and auth='Y' and auth_status='V' "
                        + "and substring(acno,1,2)='" + brCode + "') group by c.customerid "
                        + "union all "
                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobile from "
                        + "cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid "
                        + "and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 and "
                        + "a.curbrcode='" + brCode + "' and a.acno not in(select acno from mb_subscriber_tab "
                        + "where status=1 and auth='Y' and auth_status='V' and substring(acno,1,2)='" + brCode + "') group "
                        + "by c.customerid "
                        + "union all "
                        + "select '',acno,mobile_no from mb_subscriber_tab where status=1 and auth='Y' and "
                        + "auth_status='V' and substring(acno,1,2)='" + brCode + "'").getResultList();
            } else if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("AR")) {
                list = entityManager.createNativeQuery("select '',acno,mobile_no from mb_subscriber_tab where "
                        + "status=1 and auth='Y' and auth_status='V'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("AR")) {
                list = entityManager.createNativeQuery("select '',acno,mobile_no from mb_subscriber_tab where "
                        + "status=1 and auth='Y' and auth_status='V' and substring(acno,1,2)='" + brCode + "'").getResultList();
            } else if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("ST")) {
                list = entityManager.createNativeQuery("select '',acno,mobile_no from mb_subscriber_tab where "
                        + "status=1 and auth='Y' and auth_status='V' and acno_type='STAFF'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("ST")) {
                list = entityManager.createNativeQuery("select '',acno,mobile_no from mb_subscriber_tab where "
                        + "status=1 and auth='Y' and auth_status='V' and acno_type='STAFF' and "
                        + "substring(acno,1,2)='" + brCode + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    @Override
    public String getMobileNumberByAcno(String acno) throws ApplicationException {
        String mobileNo = "";
        try {
            String nature = ftsRemote.getAccountNature(acno);
            List list = null;
            Vector ele = null;
            if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                list = entityManager.createNativeQuery("select ifnull(mobilenumber,'') as mobileNo from "
                        + "cbs_customer_master_detail c,customerid i,td_accountmaster a where "
                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and "
                        + "a.acno='" + acno + "'").getResultList();
            } else {
                list = entityManager.createNativeQuery("select ifnull(mobilenumber,'') as mobileNo from "
                        + "cbs_customer_master_detail c,customerid i,accountmaster a where "
                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and "
                        + "a.acno='" + acno + "'").getResultList();
            }
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
                mobileNo = ele.get(0).toString().trim();
                mobileNo = mobileNo.length() != 10 ? "" : mobileNo;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return mobileNo;
    }

    @Override
    public List sendBirthDaySms(String birthDay, String birthMonth) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = entityManager.createNativeQuery("select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) "
                    + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,"
                    + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster "
                    + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and "
                    + "length(ltrim(rtrim(c.mobilenumber)))=10 and month(c.DateOfBirth) ='" + birthMonth + "' and "
                    + "day(c.DateOfBirth)='" + birthDay + "' and c.DateOfBirth<>'19000101' and (c.title is not null and c.title<>'M/S' and c.title<>'') "
                    + "group by c.customerid "
                    + "union "
                    + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,"
                    + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') "
                    + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where "
                    + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and "
                    + "length(ltrim(rtrim(c.mobilenumber)))=10 and month(c.DateOfBirth) ='" + birthMonth + "' and "
                    + "day(c.DateOfBirth)='" + birthDay + "' and c.DateOfBirth<>'19000101' and (c.title is not null and c.title<>'M/S' and c.title<>'') "
                    + "group by c.customerid").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }
    

//    @Override
//    public List sendKycExpAlertSms(String days,String month) throws ApplicationException {
//        List list = new ArrayList();
//        try{
//            list =entityManager.createNativeQuery("select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,"
//                    + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name ,"
//                    + "c.CustUpdationDate from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid "
//                    + "and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 and month(c.CustUpdationDate)='"+month+"' "
//                    + "and day(c.CustUpdationDate)='"+days+"' group by c.customerid "
//                    + "union "
//                    + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,"
//                    + "ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name ,c.CustUpdationDate from "
//                    + "cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno "
//                    + "and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 and month(c.CustUpdationDate)='"+month+"' and "
//                    + "day(c.CustUpdationDate)='"+days+"'group by c.customerid").getResultList();
//            
//        }catch(Exception ex){
//            throw new ApplicationException(ex.getMessage());
//        }
//        return list;
//    }
    
       
    @Override
    public List sendKycExpAlertSms(String date) throws ApplicationException {
        List list = new ArrayList();
        try{
            list =entityManager.createNativeQuery("select aa.customerid,aa.acno,aa.mobileNo,aa.first_name,aa.middle_name,aa.last_name,aa.CustUpdationDate from"
                    + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,"
                    + "ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name ,c.CustUpdationDate from cbs_customer_master_detail c,"
                    + "customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 "
                    + "and cast(c.CustUpdationDate as date) ='"+date+"' group by i.custid "
                    + "union "
                    + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,"
                    + "ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name ,c.CustUpdationDate from cbs_customer_master_detail c,"
                    + "customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 "
                    + "and  cast(c.CustUpdationDate as date) ='"+date+"' group by i.custid ) aa group by aa.customerid").getResultList();
            
        }catch(Exception ex){
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    @Override
    public String getCustomerIdByAccountNo(String acno) throws ApplicationException {
        String customerId = "";
        try {
            List list = entityManager.createNativeQuery("select cm.customerid from cbs_customer_master_detail cm,"
                    + "customerid ci where cm.customerid=ci.custid and ci.acno='" + acno + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                customerId = ele.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return customerId;
    }

//    @Override
//    public List getProductWiseDetail(String brCode, String msgType, String filterType, String acctnature, String acctcode) throws ApplicationException {
//        List list1 = new ArrayList();
//        try {
//            if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("AR") && acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select mb.acno,mb.mobile_no from mb_subscriber_tab mb,accounttypemaster att where \n"
//                        + "mb.status ='1' and mb.auth='Y' and mb.auth_status='V' and substring(acno,3,2)=att.acctcode and att.acctcode in\n"
//                        + "(select acctcode from accounttypemaster where acctnature='" + acctnature + "')").getResultList();
//            } else if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("AR") && !acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select mb.acno,mb.mobile_no from mb_subscriber_tab mb,accounttypemaster att where \n"
//                        + "mb.status ='1' and mb.auth='Y' and mb.auth_status='V' and substring(acno,3,2)=att.acctcode and att.acctcode='" + acctcode + "'").getResultList();
//            } else if (!brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("AR") && acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select mb.acno,mb.mobile_no from mb_subscriber_tab mb,accounttypemaster att where \n"
//                        + "mb.status ='1' and mb.auth='Y' and mb.auth_status='V' and substring(acno,3,2)=att.acctcode and att.acctcode in\n"
//                        + "(select acctcode from accounttypemaster where acctnature='" + acctnature + "') and substring(acno,1,2)='06' ").getResultList();
//            } else if (!brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("AR") && !acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select mb.acno,mb.mobile_no from mb_subscriber_tab mb,accounttypemaster att where \n"
//                        + "mb.status ='1' and mb.auth='Y' and mb.auth_status='V' and substring(acno,3,2)=att.acctcode and att.acctcode='" + acctcode + "'\n"
//                        + "and substring(acno,1,2)='06'").getResultList();
//            } else if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("NR") && acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select ab.acno,ab.mobileno from\n"
//                        + "(select al.customerid,al.acno,al.mobileno from\n"
//                        + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) \n"
//                        + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,\n"
//                        + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster \n"
//                        + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid \n"
//                        + "union \n"
//                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,\n"
//                        + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') \n"
//                        + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where \n"
//                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid) al left join mb_subscriber_tab mb\n"
//                        + "on al.acno=mb.acno where mb.acno is null) ab,accounttypemaster att where substring(ab.acno,3,2)=att.acctcode\n"
//                        + "and att.acctcode in(select acctcode from accounttypemaster where acctnature='" + acctnature + "')").getResultList();
//            } else if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("NR") && !acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select ab.acno,ab.mobileno from\n"
//                        + "(select al.customerid,al.acno,al.mobileno from\n"
//                        + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) \n"
//                        + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,\n"
//                        + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster \n"
//                        + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid \n"
//                        + "union \n"
//                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,\n"
//                        + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') \n"
//                        + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where \n"
//                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid) al left join mb_subscriber_tab mb\n"
//                        + "on al.acno=mb.acno where mb.acno is null) ab,accounttypemaster att where substring(ab.acno,3,2)=att.acctcode\n"
//                        + "and att.acctcode='" + acctcode + "'").getResultList();
//            } else if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("NR") && acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select ab.acno,ab.mobileno from\n"
//                        + "(select al.customerid,al.acno,al.mobileno from\n"
//                        + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) \n"
//                        + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,\n"
//                        + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster \n"
//                        + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid \n"
//                        + "union \n"
//                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,\n"
//                        + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') \n"
//                        + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where \n"
//                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid) al left join mb_subscriber_tab mb\n"
//                        + "on al.acno=mb.acno where mb.acno is null) ab,accounttypemaster att where substring(ab.acno,3,2)=att.acctcode\n"
//                        + "and att.acctcode in(select acctcode from accounttypemaster where acctnature='" + acctnature + "')").getResultList();
//            } else if (!brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("NR") && !acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select ab.acno,ab.mobileno from\n"
//                        + "(select al.customerid,al.acno,al.mobileno from\n"
//                        + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) \n"
//                        + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,\n"
//                        + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster \n"
//                        + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid \n"
//                        + "union \n"
//                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,\n"
//                        + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') \n"
//                        + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where \n"
//                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid) al left join mb_subscriber_tab mb\n"
//                        + "on al.acno=mb.acno where mb.acno is null) ab,accounttypemaster att where substring(ab.acno,3,2)=att.acctcode\n"
//                        + "and att.acctcode='10' and substring(ab.acno,1,2)='06'").getResultList();
//            } else if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("ALL") && acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select al.acno,al.mobileno from\n"
//                        + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) \n"
//                        + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,\n"
//                        + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster \n"
//                        + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid \n"
//                        + "union \n"
//                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,\n"
//                        + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') \n"
//                        + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where \n"
//                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid) al,accounttypemaster att where substring(al.acno,3,2)=att.acctcode\n"
//                        + "and att.acctcode in(select acctcode from accounttypemaster where acctnature='" + acctnature + "')").getResultList();
//            } else if (brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("ALL") && !acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select al.acno,al.mobileno from\n"
//                        + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) \n"
//                        + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,\n"
//                        + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster \n"
//                        + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid \n"
//                        + "union \n"
//                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,\n"
//                        + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') \n"
//                        + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where \n"
//                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid) al,accounttypemaster att where substring(al.acno,3,2)=att.acctcode\n"
//                        + "and att.acctcode='" + acctcode + "'").getResultList();
//            } else if (!brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("ALL") && acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select al.acno,al.mobileno from\n"
//                        + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) \n"
//                        + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,\n"
//                        + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster \n"
//                        + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid \n"
//                        + "union \n"
//                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,\n"
//                        + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') \n"
//                        + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where \n"
//                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid) al,accounttypemaster att where substring(al.acno,3,2)=att.acctcode\n"
//                        + "and att.acctcode in(select acctcode from accounttypemaster where acctnature='" + acctnature + "') and substring(al.acno,1,2)='06'").getResultList();
//            } else if (!brCode.equalsIgnoreCase("A") && msgType.equalsIgnoreCase("PW") && filterType.equalsIgnoreCase("ALL") && !acctcode.equalsIgnoreCase("ALL")) {
//                list1 = entityManager.createNativeQuery("select al.acno,al.mobileno from\n"
//                        + "(select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) \n"
//                        + "as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,\n"
//                        + "ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster \n"
//                        + "a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid \n"
//                        + "union \n"
//                        + "select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,\n"
//                        + "ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') \n"
//                        + "as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where \n"
//                        + "c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and \n"
//                        + "length(ltrim(rtrim(c.mobilenumber)))=10 \n"
//                        + "group by c.customerid) al,accounttypemaster att where substring(al.acno,3,2)=att.acctcode\n"
//                        + "and att.acctcode='" + acctcode + "' and substring(al.acno,1,2)='06'").getResultList();
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return list1;
//    }
    @Override
    public List getProductWiseDetail(String brCode, String filterType, String acctnature,
            String acctcode) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            if (brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("AR") && acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',mb.acno,mb.mobile_no from mb_subscriber_tab mb,"
                        + "accounttypemaster att where mb.status ='1' and mb.auth='Y' and mb.auth_status='V' and "
                        + "substring(acno,3,2)=att.acctcode and att.acctcode in (select acctcode from accounttypemaster "
                        + "where acctnature='" + acctnature + "')").getResultList();
            } else if (brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("AR") && !acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',mb.acno,mb.mobile_no from mb_subscriber_tab mb,"
                        + "accounttypemaster att where mb.status ='1' and mb.auth='Y' and mb.auth_status='V' and "
                        + "substring(acno,3,2)=att.acctcode and att.acctcode='" + acctcode + "'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("AR") && acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',mb.acno,mb.mobile_no from mb_subscriber_tab mb,"
                        + "accounttypemaster att where mb.status ='1' and mb.auth='Y' and mb.auth_status='V' and "
                        + "substring(acno,3,2)=att.acctcode and att.acctcode in (select acctcode from accounttypemaster "
                        + "where acctnature='" + acctnature + "') and substring(acno,1,2)='" + brCode + "'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("AR") && !acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',mb.acno,mb.mobile_no from mb_subscriber_tab mb,"
                        + "accounttypemaster att where mb.status ='1' and mb.auth='Y' and mb.auth_status='V' and "
                        + "substring(acno,3,2)=att.acctcode and att.acctcode='" + acctcode + "' and "
                        + "substring(acno,1,2)='" + brCode + "'").getResultList();
            } else if (brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("NR") && acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',ab.acno,ab.mobileno from (select al.customerid,al.acno,al.mobileno from (select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid union select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid) al left join mb_subscriber_tab mb on al.acno=mb.acno where mb.acno is null) ab,accounttypemaster att where substring(ab.acno,3,2)=att.acctcode and att.acctcode in(select acctcode from accounttypemaster where acctnature='" + acctnature + "')").getResultList();
            } else if (brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("NR") && !acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',ab.acno,ab.mobileno from (select al.customerid,al.acno,al.mobileno from (select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid union select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid) al left join mb_subscriber_tab mb on al.acno=mb.acno where mb.acno is null) ab,accounttypemaster att where substring(ab.acno,3,2)=att.acctcode and att.acctcode='" + acctcode + "'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("NR") && acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',ab.acno,ab.mobileno from (select al.customerid,al.acno,al.mobileno from (select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid union select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid) al left join mb_subscriber_tab mb on al.acno=mb.acno where mb.acno is null) ab,accounttypemaster att where substring(ab.acno,3,2)=att.acctcode and att.acctcode in(select acctcode from accounttypemaster where acctnature='" + acctnature + "') and substring(ab.acno,1,2)='" + brCode + "'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("NR") && !acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',ab.acno,ab.mobileno from (select al.customerid,al.acno,al.mobileno from (select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid union select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid) al left join mb_subscriber_tab mb on al.acno=mb.acno where mb.acno is null) ab,accounttypemaster att where substring(ab.acno,3,2)=att.acctcode and att.acctcode='" + acctcode + "' and substring(ab.acno,1,2)='" + brCode + "'").getResultList();
            } else if (brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("ALL") && acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',al.acno,al.mobileno from (select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid union select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid) al,accounttypemaster att where substring(al.acno,3,2)=att.acctcode and att.acctcode in(select acctcode from accounttypemaster where acctnature='" + acctnature + "')").getResultList();
            } else if (brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("ALL") && !acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',al.acno,al.mobileno from (select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid union select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid) al,accounttypemaster att where substring(al.acno,3,2)=att.acctcode and att.acctcode='" + acctcode + "'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("ALL") && acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',al.acno,al.mobileno from (select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid union select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid) al,accounttypemaster att where substring(al.acno,3,2)=att.acctcode and att.acctcode in(select acctcode from accounttypemaster where acctnature='" + acctnature + "') and substring(al.acno,1,2)='" + brCode + "'").getResultList();
            } else if (!brCode.equalsIgnoreCase("A") && filterType.equalsIgnoreCase("ALL") && !acctcode.equalsIgnoreCase("ALL")) {
                dataList = entityManager.createNativeQuery("select '',al.acno,al.mobileno from (select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid union select c.customerid,a.acno,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobileNo,ifnull(c.custname,'') as first_name,ifnull(c.middle_name,'') as middle_name,ifnull(c.last_name,'') as last_name from cbs_customer_master_detail c,customerid i,td_accountmaster a where c.customerid=i.custid and i.acno=a.acno and a.accstatus=1 and length(ltrim(rtrim(c.mobilenumber)))=10 group by c.customerid) al,accounttypemaster att where substring(al.acno,3,2)=att.acctcode and att.acctcode='" + acctcode + "' and substring(al.acno,1,2)='" + brCode + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
}
