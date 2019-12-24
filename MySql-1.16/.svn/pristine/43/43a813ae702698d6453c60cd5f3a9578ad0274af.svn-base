/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.sms.service;

import com.cbs.dao.sms.MbPushMsgTabDAO;
import com.cbs.dao.sms.MbSmsLogDAO;
import com.cbs.dto.sms.BulkSmsResponseTo;
import com.cbs.dto.sms.BulkSmsTo;
import com.cbs.entity.sms.MbPushMsgTab;
import com.cbs.entity.sms.MbSmsLog;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class SmsSenderImpl implements SmsSender {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    private Properties props = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
    SimpleDateFormat eymdms = new SimpleDateFormat("ddHHmmssSSS");

    @PostConstruct
    private void loadSmsConfig() {
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/sms.properties"));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The "
                    + "SMS Properties File. " + ex.getMessage());
        }
    }

    public void postSms(SmsMessage sms) {
        UserTransaction ut = context.getUserTransaction();
        try {
            String vendor = props.getProperty("sms.vendor");

            System.out.println("Sending SMS to " + vendor + "I n SmsSenderImpl in sendMessage() method-->Acno is::"
                    + sms.getActualAcNo() + " And Mobile is::" + sms.getMobileNo());
            ut.begin();
            MbPushMsgTabDAO mbPushMsgTabDAO = new MbPushMsgTabDAO(entityManager);
            String messageId = "";
            if (!(sms.getUserMsgId() == null || sms.getUserMsgId().equals(""))) {
                messageId = sms.getUserMsgId();
            } else {
                messageId = sms.getActualAcNo().substring(0, 10) + ymdms.format(new Date());
            }

            MbPushMsgTab entity = new MbPushMsgTab();
            entity.setMessageId(messageId);
            entity.setMobile("+" + sms.getMobileNo().trim());

            entity.setAcno(sms.getActualAcNo().trim());
            entity.setMessage(new String(sms.getMessage().trim().getBytes("ISO-8859-1"), "UTF-8"));
            entity.setMessageStatus(1);

            entity.setMessageType(sms.getMessageType().trim().equalsIgnoreCase("P") ? "PROMOTIONAL" : "TRANSACTIONAL");
            entity.setDt(new Date());

            entity.setEnterBy(sms.getUserName() == null ? "System" : sms.getUserName().trim());
            entity.setModuleName(sms.getModuleName());

            entity.setVendorTranid("");
            entity.setVendorStatus(0);
            mbPushMsgTabDAO.save(entity);

            String result = "", errorCode = "", errorMsg = "", tranId = "";
            if (vendor.equalsIgnoreCase("digialaya")) {
                result = sendSMSByGetMethod(sms);
                if (result.contains(",")) {
                    String result1 = result.split(",")[0].split(":")[0].trim();
                    String result2 = result.split(",")[1].split(":")[0].trim();

                    if (result1.equalsIgnoreCase("ContactNum") && result2.equalsIgnoreCase("TransId")) {
                        tranId = result.split(",")[1].split(":")[1].trim();
                    }
                } else {
                    errorCode = "";
                    errorMsg = result;
                }
            } else if (vendor.equalsIgnoreCase("gupshup")) {
                result = sendSMSByPostMethod(sms);
                String[] responseArr = result.split("\\|");

                if (!responseArr[0].trim().equalsIgnoreCase("success")) {
                    errorCode = responseArr[1].trim();
                    errorMsg = responseArr[2].trim();
                }
            }
            if (errorCode.equals("") && errorMsg.equals("")) {
                entity = mbPushMsgTabDAO.getEntityByMessageId(messageId);
                if (sms.getModuleName().equalsIgnoreCase("MB") || sms.getModuleName().equalsIgnoreCase("NO-CHG")) {
                    entity.setMessageStatus(4);
                } else {
                    entity.setMessageStatus(2);
                }
                entity.setVendorTranid(tranId);
                mbPushMsgTabDAO.update(entity);
            } else {
                MbSmsLogDAO mbSmsLogDAO = new MbSmsLogDAO(entityManager);

                MbSmsLog logEntity = new MbSmsLog();
                logEntity.setMobile("+" + sms.getMobileNo().trim());
                logEntity.setAcno(sms.getActualAcNo().trim());

                logEntity.setActualMessage(new String(sms.getMessage().trim().getBytes("ISO-8859-1"), "UTF-8"));
                logEntity.setErrorCode(errorCode);

                logEntity.setErrorMessage(errorMsg);
                logEntity.setTranTime(new Date());

                logEntity.setSmsVendor(vendor);
                logEntity.setModuleName(sms.getModuleName());
                mbSmsLogDAO.save(logEntity);
            }

            ut.commit();
        } catch (Exception e) {
            System.out.println("CCBL ERROR PRINTING>>>>>>>>>>" + "\n");
            e.printStackTrace();
            try {
                ut.commit();
                System.out.println("Problem In Sending SMS In SmsSenderImpl. Mobile::" + sms.getMobileNo()
                        + " And A/c is::" + sms.getActualAcNo() + " And Error is::" + e.getMessage());
            } catch (Exception ex) {
                System.out.println("Problem In Sending SMS In SmsSenderImpl. Mobile::" + sms.getMobileNo()
                        + " And A/c is::" + sms.getActualAcNo() + " And Error is::" + ex.getMessage());
            }
        }
    }

    public String sendSMSByPostMethod(SmsMessage sms) {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            //Setting of timeout
            int timeout = 30; // seconds
            HttpParams httpParams = httpclient.getParams();
            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            BufferedReader rd = null;

            HttpPost httpPost = new HttpPost(props.getProperty("sms.gateway.url"));

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            Set<String> propNames = props.stringPropertyNames();

            for (String keyName : propNames) {
                if (keyName.startsWith("sms.param.")) {

                    if (props.getProperty(keyName).equals("$mobile")) {
                        nameValuePairs.add(new BasicNameValuePair(keyName.substring(keyName.lastIndexOf(".") + 1), sms.getMobileNo().trim()));
                    } else if (props.getProperty(keyName).equals("$message")) {
                        nameValuePairs.add(new BasicNameValuePair(keyName.substring(keyName.lastIndexOf(".") + 1), sms.getMessage().trim()));
                    } else {
                        nameValuePairs.add(new BasicNameValuePair(keyName.substring(keyName.lastIndexOf(".") + 1), props.getProperty(keyName)));
                    }
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httpPost);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            System.out.println("Sending SMS In SmsSenderImpl. Mobile::" + sms.getMobileNo() + " And A/c is::" + sms.getActualAcNo());
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println("Vendor Response Is " + line);
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            System.out.println("Problem In Sending SMS In SmsSenderImpl. Mobile::" + sms.getMobileNo()
                    + " And A/c is::" + sms.getActualAcNo() + " And Error is::" + e.getMessage());
            return "fail";
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public String sendSMSByGetMethod(SmsMessage sms) {
        HttpClient client = new DefaultHttpClient();
        try {
            //Setting of timeout
            int timeout = 30; // seconds
            HttpParams httpParams = client.getParams();
            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            String keyName = "";
            String paramValue = "";
            String paramName = "";

            Set<Entry<Object, Object>> propEntrySet = props.entrySet();
            UriBuilder builder = UriBuilder.fromUri(props.getProperty("sms.gateway.url"));

            for (Entry<Object, Object> entry : propEntrySet) {
                keyName = entry.getKey().toString();

                if (keyName.startsWith("sms.param.")) {

                    paramName = keyName.substring(keyName.lastIndexOf(".") + 1);

                    paramValue = entry.getValue().toString();

                    if (paramValue.equalsIgnoreCase("$mobile")) {

                        builder.queryParam(paramName, sms.getMobileNo().trim().substring(2));
                    } else if (entry.getValue().toString().equalsIgnoreCase("$message")) {

                        if (props.getProperty("sms.template").equalsIgnoreCase("hi")) {
                            builder.queryParam(paramName, URLEncoder.encode(new String(sms.getMessage().trim().getBytes("ISO-8859-1"), "UTF-8"), "UTF-8"));
                        } else {
                            builder.queryParam(paramName, sms.getMessage().trim());
                        }
                    } else {
                        builder.queryParam(paramName, paramValue);
                    }
                }
            }
            System.out.println("The Build URI = " + builder.build().toString());
            HttpGet httpGet = new HttpGet(builder.build());

            HttpResponse response = client.execute(httpGet);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            List<String> responseList = new ArrayList<String>();
            while ((line = rd.readLine()) != null) {
                System.out.println("Vendor Response Is " + line);
                responseList.add(line);
            }
            return responseList.get(0);
        } catch (Exception e) {
            System.out.println("Problem In Sending SMS In SmsSenderImpl. Mobile::" + sms.getMobileNo()
                    + " And A/c is::" + sms.getActualAcNo() + " And Error is::" + e.getMessage());
            return "fail";
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

    @Override
    public void sendSmsBeyondCbs(SmsMessage sms) {
        UserTransaction ut = context.getUserTransaction();
        try {
            String vendor = props.getProperty("sms.vendor");

            System.out.println("Sending SMS to " + vendor + "In SmsSenderImpl in sendMessage() method-->RequestType is::"
                    + sms.getMessageType() + " And Mobile is::" + sms.getMobileNo());
            ut.begin();
            MbPushMsgTabDAO mbPushMsgTabDAO = new MbPushMsgTabDAO(entityManager);
            String messageId = String.valueOf(System.nanoTime());

            MbPushMsgTab entity = new MbPushMsgTab();
            entity.setMessageId(messageId);
            entity.setMobile("+" + sms.getMobileNo().trim());
            entity.setAcno("");
            entity.setMessage(new String(sms.getMessage().trim().getBytes("ISO-8859-1"), "UTF-8"));
            entity.setMessageStatus(1);
            entity.setMessageType("TRANSACTIONAL");
            entity.setDt(new Date());
            entity.setEnterBy(sms.getUserName() == null ? "System" : sms.getUserName().trim());
            entity.setModuleName(sms.getMessageType());
            entity.setVendorTranid("");
            entity.setVendorStatus(0);
            mbPushMsgTabDAO.save(entity);

            String result = "", errorCode = "", errorMsg = "", tranId = "";
            if (vendor.equalsIgnoreCase("digialaya")) {
                result = sendSMSByGetMethod(sms);
                if (result.contains(",")) {
                    String result1 = result.split(",")[0].split(":")[0].trim();
                    String result2 = result.split(",")[1].split(":")[0].trim();

                    if (result1.equalsIgnoreCase("ContactNum") && result2.equalsIgnoreCase("TransId")) {
                        tranId = result.split(",")[1].split(":")[1].trim();
                    }
                } else {
                    errorCode = "";
                    errorMsg = result;
                }
            } else if (vendor.equalsIgnoreCase("gupshup")) {
                result = sendSMSByPostMethod(sms);
                String[] responseArr = result.split("\\|");

                if (!responseArr[0].trim().equalsIgnoreCase("success")) {
                    errorCode = responseArr[1].trim();
                    errorMsg = responseArr[2].trim();
                }
            }
            if (errorCode.equals("") && errorMsg.equals("")) {
                entity = mbPushMsgTabDAO.getEntityByMessageId(messageId);
                entity.setMessageStatus(4);
                entity.setVendorTranid(tranId);
                mbPushMsgTabDAO.update(entity);
            } else {
                MbSmsLogDAO mbSmsLogDAO = new MbSmsLogDAO(entityManager);

                MbSmsLog logEntity = new MbSmsLog();
                logEntity.setMobile("+" + sms.getMobileNo().trim());
                logEntity.setAcno("");

                logEntity.setActualMessage(new String(sms.getMessage().trim().getBytes("ISO-8859-1"), "UTF-8"));
                logEntity.setErrorCode(errorCode);

                logEntity.setErrorMessage(errorMsg);
                logEntity.setTranTime(new Date());

                logEntity.setSmsVendor(vendor);
                logEntity.setModuleName(sms.getMessageType());
                mbSmsLogDAO.save(logEntity);
            }

            ut.commit();
        } catch (Exception e) {
            System.out.println("SMS Error In Outside Request>>>>>>>>>>" + "\n");
            e.printStackTrace();
            try {
                ut.commit();
                System.out.println("Problem In Sending Outside Request SMS In SmsSenderImpl. Request Type::" + sms.getMessageType()
                        + " And Id No is::" + sms.getIdNo() + " And Error is::" + e.getMessage());
            } catch (Exception ex) {
                System.out.println("Problem In Sending Outside Request SMS In SmsSenderImpl. Request Type::" + sms.getMessageType()
                        + " And Id No is::" + sms.getIdNo() + " And Error is::" + e.getMessage());
            }
        }
    }

    @Override
    public String postBulkSmsByGupshup(List<File> files, List<BulkSmsTo> bulkSmsList, String bulkMode, File originalFile,
            String userName, String orgnCode, String todayDt) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            for (File file : files) {
                result = sendSMSByPostMethodInGupshup(file);

                List list = entityManager.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                        + "where file_gen_date='" + ymd.format(dmy.parse(todayDt)) + "' and file_gen_type='BLKSMS'").getResultList();
                Vector ele = (Vector) list.get(0);
                String fileNo = "1";
                if (ele.get(0) != null) {
                    fileNo = ele.get(0).toString().trim();
                }

                String fileName = file.getName() + ":" + file.getName();
                if (bulkMode.equalsIgnoreCase("External")) {
                    fileName = originalFile.getName() + ":" + file.getName(); //Original:New
                }

                int n = entityManager.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,"
                        + "file_gen_by,file_gen_time,file_gen_brncode,file_gen_type,seq_no,date_of_file_gen,file_gen_seqn,"
                        + "premium_amount,utr) "
                        + "values (" + Integer.parseInt(fileNo) + ",'" + ymd.format(dmy.parse(todayDt)) + "','" + fileName + "'"
                        + ",'" + userName + "',current_timestamp(),'" + orgnCode + "','BLKSMS',0,'" + ymd.format(dmy.parse(todayDt)) + "',"
                        + "'',0,'')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Data Insertion");
                }

                n = entityManager.createNativeQuery("insert into cbs_file_response(file_no,file_gen_date,file_gen_type,"
                        + "error_code,error_msg) values(" + Integer.parseInt(fileNo) + ",'" + ymd.format(dmy.parse(todayDt)) + "',"
                        + "'BLKSMS','','" + result + "')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Response Updation.");
                }
            }

            //Loging of individual sended messages to mobile no.
            String mbPushMsgTabInsertQuery = "insert into mb_push_msg_tab (message_id, mobile, acno, message, "
                    + "message_status, message_type, dt, enter_by, module_name, vendor_tranid, vendor_status) values";
            String mbPushMsgTabDataQuery = "", messageId = "";

            for (int p = 0; p < bulkSmsList.size(); p++) {
                BulkSmsTo to = bulkSmsList.get(p);
                messageId = to.getUniqueMessageId();
                if (mbPushMsgTabDataQuery.equals("")) {
                    mbPushMsgTabDataQuery = "('" + messageId + "','" + to.getPhone() + "','" + to.getAccountNo() + "',"
                            + "'" + to.getMessage() + "',2,'" + to.getMessageType() + "',"
                            + "current_timestamp(),'" + userName + "','" + to.getModuleName() + "','',0)";
                } else {
                    mbPushMsgTabDataQuery = mbPushMsgTabDataQuery + ",('" + messageId + "','" + to.getPhone() + "',"
                            + "'" + to.getAccountNo() + "','" + to.getMessage() + "',2,'" + to.getMessageType() + "',"
                            + "current_timestamp(),'" + userName + "','" + to.getModuleName() + "','',0)";
                }
            }
            mbPushMsgTabInsertQuery = mbPushMsgTabInsertQuery + mbPushMsgTabDataQuery;
            int n = entityManager.createNativeQuery(mbPushMsgTabInsertQuery).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in individual sms account detail insertion");
            }
            ut.commit();
        } catch (Exception e) {
            System.out.println("Problem In Sending Bulk SMS " + e.getMessage());
            ut.rollback();
            throw new Exception(e.getMessage());
        }
        return result;
    }

    public String sendSMSByPostMethodInGupshup(File file) throws Exception {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(props.getProperty("sms.gateway.url"));

            MultipartEntity reqEntity = new MultipartEntity();

            reqEntity.addPart("method",new StringBody("xlsUpload"));
            reqEntity.addPart("loginid",new StringBody(props.getProperty("sms.param.loginid")));
            reqEntity.addPart("password",new StringBody(props.getProperty("sms.param.password")));
            reqEntity.addPart("filetype",new StringBody("xls"));
            reqEntity.addPart("v",new StringBody(props.getProperty("sms.param.v")));
            reqEntity.addPart("auth_scheme",new StringBody(props.getProperty("sms.param.auth_scheme")));
            reqEntity.addPart("msg_type",new StringBody(props.getProperty("sms.param.msg_type")));
            reqEntity.addPart(file.getName(),new FileBody(file));
            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity == null) {
                throw new Exception("There is no return response.");
            }
            result = EntityUtils.toString(resEntity);
            System.out.println("PAGE :" + result);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    @Override
    public String postBulkSmsByDigialaya(List<String> allXmlString, List<BulkSmsTo> bulkSmsList, String bulkMode, File originalFile,
            String userName, String orgnCode, String todayDt) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            List<String> resultList = new ArrayList<>();
            for (String xmlString : allXmlString) {
                result = sendSMSByPostMethodInDigialaya(xmlString);
                resultList.add(result);
            }
            if (bulkMode.equalsIgnoreCase("External")) {
                List list = entityManager.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                        + "where file_gen_date='" + ymd.format(dmy.parse(todayDt)) + "' and file_gen_type='BLKSMS'").getResultList();
                Vector ele = (Vector) list.get(0);
                String fileNo = "1";
                if (ele.get(0) != null) {
                    fileNo = ele.get(0).toString().trim();
                }

                String fileName = originalFile.getName() + ":" + originalFile.getName();
                int n = entityManager.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,"
                        + "file_gen_by,file_gen_time,file_gen_brncode,file_gen_type,seq_no,date_of_file_gen,file_gen_seqn,"
                        + "premium_amount,utr) "
                        + "values (" + Integer.parseInt(fileNo) + ",'" + ymd.format(dmy.parse(todayDt)) + "','" + fileName + "'"
                        + ",'" + userName + "',current_timestamp(),'" + orgnCode + "','BLKSMS',0,'" + ymd.format(dmy.parse(todayDt)) + "',"
                        + "'',0,'')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Data Insertion");
                }
            }
            //Loging of individual sended messages to mobile no.
            String mbPushMsgTabInsertQuery = "insert into mb_push_msg_tab (message_id, mobile, acno, message, "
                    + "message_status, message_type, dt, enter_by, module_name, vendor_tranid, vendor_status) values";
            String mbPushMsgTabDataQuery = "", messageId = "";

            for (int p = 0; p < bulkSmsList.size(); p++) {
                BulkSmsTo to = bulkSmsList.get(p);
                messageId = to.getUniqueMessageId();
                if (mbPushMsgTabDataQuery.equals("")) {
                    mbPushMsgTabDataQuery = "('" + messageId + "','" + to.getPhone() + "','" + to.getAccountNo() + "',"
                            + "'" + to.getMessage() + "',2,'" + to.getMessageType() + "',"
                            + "current_timestamp(),'" + userName + "','" + to.getModuleName() + "','',0)";
                } else {
                    mbPushMsgTabDataQuery = mbPushMsgTabDataQuery + ",('" + messageId + "','" + to.getPhone() + "',"
                            + "'" + to.getAccountNo() + "','" + to.getMessage() + "',2,'" + to.getMessageType() + "',"
                            + "current_timestamp(),'" + userName + "','" + to.getModuleName() + "','',0)";
                }
            }
            mbPushMsgTabInsertQuery = mbPushMsgTabInsertQuery + mbPushMsgTabDataQuery;
            int n = entityManager.createNativeQuery(mbPushMsgTabInsertQuery).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in individual sms account detail insertion");
            }
            //Update the transaction Id
            for (String digialayaResult : resultList) {
                List<BulkSmsResponseTo> responseList = getVendorResponse(digialayaResult);
                for (BulkSmsResponseTo to : responseList) {
                    int status = to.getStatus().equalsIgnoreCase("Success") ? 0 : 1;

                    List chkList = entityManager.createNativeQuery("select * from mb_push_msg_tab where "
                            + "message_id='" + to.getId() + "'").getResultList();
                    if (!chkList.isEmpty()) {
                        n = entityManager.createNativeQuery("update mb_push_msg_tab set vendor_tranid='" + to.getVendorResId() + "',"
                                + "vendor_status=" + status + " where message_id='" + to.getId() + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem in individual sms account detail updation");
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception e) {
            System.out.println("Problem In Sending Bulk SMS " + e.getMessage());
            ut.rollback();
            throw new Exception(e.getMessage());
        }
        return "Message has been sent successfully !";
    }

//    public String sendSMSByGetMethodInDigialaya(String allXmlString) throws Exception {
//        HttpClient client = new DefaultHttpClient();
//        try {
//            //Setting of timeout
//            int timeout = 30; // seconds
//            HttpParams httpParams = client.getParams();
//            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
//            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
//
//            UriBuilder builder = UriBuilder.fromUri(props.getProperty("sms.gateway.bulk.url"));
//
//            builder.queryParam("ServiceName", "SMSTRANS");
//            builder.queryParam("Message", allXmlString);
//
//            System.out.println("The Build URI = " + builder.build().toString());
//            HttpGet httpGet = new HttpGet(builder.build());
//
//            HttpResponse response = client.execute(httpGet);
//            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//            String line = "";
//            List<String> responseList = new ArrayList<>();
//            while ((line = rd.readLine()) != null) {
//                System.out.println("Vendor Response Is " + line);
//                responseList.add(line);
//            }
//            return responseList.get(0);
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        } finally {
//            client.getConnectionManager().shutdown();
//        }
//    }
    public String sendSMSByPostMethodInDigialaya(String allXmlString) throws Exception {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(props.getProperty("sms.gateway.bulk.url"));

            MultipartEntity reqEntity = new MultipartEntity();

            reqEntity.addPart("ServiceName", new StringBody("SMSTRANS"));
            reqEntity.addPart("Message", new StringBody(allXmlString));
            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            System.out.println("Status Line" + response.getStatusLine().toString());

            if (resEntity == null) {
                throw new Exception("There is no return response.");
            }
            result = EntityUtils.toString(resEntity);
            System.out.println("PAGE :" + result);
            result = result.substring(result.indexOf("<XMLRESPONSE>"), result.indexOf("<!DOCTYPE html>")).trim();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return result;
    }

    public List<BulkSmsResponseTo> getVendorResponse(String xmlRecords) throws Exception {
        List<BulkSmsResponseTo> responseList = new ArrayList<>();
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlRecords));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("SMS");

            for (int i = 0; i < nodes.getLength(); i++) {
                BulkSmsResponseTo to = new BulkSmsResponseTo();
                Element smsElement = (Element) nodes.item(i);

                to.setId(smsElement.getAttribute("ID"));

                NodeList responseNode = smsElement.getElementsByTagName("RESPONSE");
                Element responseElement = (Element) responseNode.item(0);

                to.setStatus(responseElement.getAttribute("STATUS"));
                to.setError(responseElement.getAttribute("ERROR"));
                to.setVendorResId(responseElement.getAttribute("RESPONSEID"));
                responseList.add(to);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return responseList;
    }
}
