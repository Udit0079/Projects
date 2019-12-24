/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cpsms;

import com.cbs.constant.CbsConstant;
import com.cbs.constant.CpsmsEnum;
import com.cbs.constant.CpsmsErrorEnum;
import com.cbs.dto.cpsms.CpsmsCommonDTO;
import com.cbs.dto.neftrtgs.NeftOwDetailsTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.cpsms.accountvalidationresponse.AH;
import com.cbs.pojo.cpsms.accountvalidationresponse.AHDetails;
import com.cbs.pojo.cpsms.accountvalidationresponse.AcctResDetail;
import com.cbs.pojo.HoReconsilePojo;
import com.cbs.pojo.cpsms.initiatedpaymentdata.InitiatedPayments;
import com.cbs.utils.ParseFileUtil;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;
import siplmatcher.Siplmatcher;
import com.cbs.pojo.cpsms.accountvalidationresponse.MsgRes;
import com.cbs.pojo.cpsms.transactionsdatarequest.ObjectFactory;
import com.cbs.pojo.cpsms.transactionsdatarequest.TransactionDetails;
import com.cbs.pojo.cpsms.transactionsdatarequest.TransactionDetails.Account;
import com.cbs.pojo.cpsms.transactionsdatarequest.TransactionDetails.Account.Transaction.Narratives;
import com.cbs.pojo.cpsms.transactionsdatarequest.TransactionDetails.Account.Transaction.Narratives.Narration;
import com.cbs.utils.CbsUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.xml.bind.JAXBElement;

@Stateless(mappedName = "CPSMSMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CPSMSMgmtFacade implements CPSMSMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    CPSMSCommonMgmtFacadeRemote cpsmsCommonRemote;
    @EJB
    CPSMSViewMgmtFacadeRemote cpsmsViewRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    private Properties props = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMddhhmmss");

    /*If we maintain all sftp and cbs location as well as sftp user credential in a table
     * then we will modify the properties file style coding.
     */
    @PostConstruct
    private void loadConfig() {
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/wslocation.properties"));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The "
                    + "WSLOCATION Properties File. " + ex.getMessage());
        }
    }

//    public void cpsmsProcess() {
//        System.out.println("In cpsmsProcess method()");
//        try {
//            Thread.sleep(5000);
//            try {
//                fetchPrintAdviceFileOne();
//            } catch (Exception e) {
//                System.out.println("Problem in fetching the [File No. 1]......" + e.getMessage());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("Problem in cpsmsProcess() method......" + ex.getMessage());
//        }
//    }
//    private void fetchPrintAdviceFileOne() throws Exception {
//        try {
//            SftpUtil util = SftpUtilFactory.getSftpUtil();
//            SftpSession session = getSftpSession();
//
//            String iwSftpPayReqLocation = props.getProperty("iwSftpPayReqLocation");
//            String iwPayReqLocation = props.getProperty("iwPayReqLocation");
//            String iwSftpPayReqLocationDirectory = iwSftpPayReqLocation.substring(0, iwSftpPayReqLocation.lastIndexOf("/") + 1);
//            if (isFileExist(util, session, iwSftpPayReqLocationDirectory)) {
//                util.get(session, iwSftpPayReqLocation, iwPayReqLocation);
//                File dir = new File(iwPayReqLocation);
//                File[] files = dir.listFiles();
//                for (int i = 0; i < files.length; i++) {
//                    util.rm(session, iwSftpPayReqLocationDirectory + files[i].getName());
//                }
//            }
//            util.disconnect(session);
//            /*What will be the scheduled time for e-Payment ? Or you can say what will be the cut-off time
//             to fetch the payment request file.*/
//            parsePaymentRequestFile();
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
//    }
//    private void parsePaymentRequestFile() throws Exception {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            File dir = new File(props.getProperty("iwPayReqLocation"));
//            File[] files = dir.listFiles();
//            for (File file : files) {
//                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
//                String fileContentType = mimeTypesMap.getContentType(file.getName());
//                System.out.println("File Content Type = " + fileContentType);
//                if (fileContentType.equalsIgnoreCase("application/octet-stream")) {
//                    String className = CpsmsEnum.PRINT_ADVICE_FILE_ONE.getValue();
//
//                    Class comingMsg = Class.forName(className);
//                    JAXBContext jaxbContext = JAXBContext.newInstance(comingMsg);
//                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//
//                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//                    docFactory.setNamespaceAware(true);
//                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//                    Document newDoc = docBuilder.parse(new File(props.getProperty("iwPayReqLocation") + file.getName()));
//                    NodeList nodeList = newDoc.getElementsByTagName("Payments");
//
//                    for (int i = 0; i < nodeList.getLength(); i++) {
//                        com.cbs.pojo.cpsms.paymentrequest.Payments payment = (com.cbs.pojo.cpsms.paymentrequest.Payments) jaxbUnmarshaller.unmarshal(nodeList.item(i));
//                        System.out.println("MessageId-->" + payment.getMessageId());
//                        //Checking of duplicate file.
//                        List list = em.createNativeQuery("select MessageId from cpsms_header where MessageId='" + payment.getMessageId() + "'").getResultList();
//                        if (!list.isEmpty()) {
//                            //Generate and send the negative ack of duplicate file
//                            createAckFileToPaymentRequest(payment, CpsmsErrorEnum.PRINT_ADVICE_ERROR_01.getCode(), file.getName());
//                            ut.commit();
//                            return;
//                        } else {
//                            List<String> allBatches = new ArrayList<String>();
//                            Map<String, String> amtMap = new HashMap<String, String>();
//                            for (BatchDetails batch : payment.getBatchDetails()) {
//                                BigDecimal totalDebitAmount = new BigDecimal("0");
//                                BigDecimal totalCreditAmount = new BigDecimal("0");
//                                //Extracting batch debit amount
//                                DebitAccounts debitAccounts = batch.getDebitAccounts();
//                                for (DebitAccount debitAccount : debitAccounts.getDebitAccount()) {
//                                    totalDebitAmount = totalDebitAmount.add(new BigDecimal(debitAccount.getC4063()));
//                                }
//                                //Extracting batch debit amount
//                                CreditAccounts creditAccounts = batch.getCreditAccounts();
//                                for (CreditAccount creditAccount : creditAccounts.getCreditAccount()) {
//                                    totalCreditAmount = totalCreditAmount.add(new BigDecimal(creditAccount.getC4038()));
//                                }
//                                amtMap.put(batch.getCPSMSBatchNo(), totalDebitAmount.toString() + ":" + totalCreditAmount.toString());
//                                //Extracting all batch nos
//                                allBatches.add(batch.getCPSMSBatchNo());
//                            }
//                            Set<String> duplicateSet = CbsUtil.findDuplicates(allBatches);
//                            if (duplicateSet.size() > 0) {
//                                //Generate and send the negative ack of duplicate batch
//                                createAckFileToPaymentRequest(payment, CpsmsErrorEnum.PRINT_ADVICE_ERROR_02.getCode(), file.getName());
//                                ut.commit();
//                                return;
//                            } else {
//                                //Matching of batch credit and debit amount
//                                Set<Entry<String, String>> set = amtMap.entrySet();
//                                Iterator<Entry<String, String>> it = set.iterator();
//                                while (it.hasNext()) {
//                                    Entry<String, String> entry = it.next();
//                                    String[] arr = entry.getValue().split(":");
//                                    if (new BigDecimal(arr[0]).compareTo(new BigDecimal(arr[1])) != 0) {
//                                        //Generate and send the negative ack of batch credit and debit amount does not match
//                                        createAckFileToPaymentRequest(payment, CpsmsErrorEnum.PRINT_ADVICE_ERROR_03.getCode(), file.getName());
//                                        ut.commit();
//                                        return;
//                                    }
//                                }
//                                //Now logging of incoming data batch wise
//                                int n = em.createNativeQuery("INSERT INTO cpsms_header(MessageId,PaymentProduct,Source,"
//                                        + "Destination,BankCode,BankName,RecordsCount,Error_Code,Error_Desc,Cbs_Status,"
//                                        + "Entry_Date,Entry_Time,Entry_By,Request_File_Name) VALUES('" + payment.getMessageId() + "',"
//                                        + "'" + payment.getPaymentProduct() + "','" + payment.getSource() + "',"
//                                        + "'" + payment.getDestination() + "','" + payment.getBankCode() + "',"
//                                        + "'" + payment.getBankName() + "','" + payment.getRecordsCount().toString() + "',"
//                                        + "'','','','" + ymd.format(new Date()) + "',current_timestamp,'System',"
//                                        + "'" + file.getName().substring(0, file.getName().indexOf(".")) + "')").executeUpdate();
//                                if (n <= 0) {
//                                    throw new Exception("Problem in data insertion in cpsms_header");
//                                }
//                                for (BatchDetails batch : payment.getBatchDetails()) {
//                                    String batchCorpId = batch.getCorporateId() == null ? "" : batch.getCorporateId();
//                                    String batchTime = batch.getC3535() == null ? "" : batch.getC3535().toString();
//                                    n = em.createNativeQuery("INSERT INTO cpsms_batch_detail(CorporateId,CPSMS_Batch_No,"
//                                            + "C3535_Batch_Time,C1106_Batch_Credit_No,Header_MessageId,Error_Code,Cbs_Status,"
//                                            + "Entry_Date,Entry_Time,Entry_By) VALUES('" + batchCorpId + "',"
//                                            + "'" + batch.getCPSMSBatchNo() + "','" + batchTime + "',"
//                                            + "'" + batch.getC1106().toString() + "','" + payment.getMessageId() + "',"
//                                            + "'','','" + ymd.format(new Date()) + "',current_timestamp,'System')").executeUpdate();
//                                    if (n <= 0) {
//                                        throw new Exception("Problem in data insertion in cpsms_batch_detail");
//                                    }
//
//                                    List<DebitAccount> debitAccountList = batch.getDebitAccounts().getDebitAccount();
//                                    for (DebitAccount debitAccount : debitAccountList) {
//                                        String C7002_F = "", C7002_S = "", C7002_T = "", C7002_R = "";
//                                        if (debitAccount.getFileOrgtr() != null) {
//                                            if (!debitAccount.getFileOrgtr().getC7002().isEmpty()) {
//                                                List<String> strList = debitAccount.getFileOrgtr().getC7002();
//                                                int listSize = strList.size();
//                                                C7002_F = listSize >= 1 ? strList.get(0) : "";
//                                                C7002_S = listSize >= 2 ? strList.get(1) : "";
//                                                C7002_T = listSize >= 3 ? strList.get(2) : "";
//                                                C7002_R = listSize >= 4 ? strList.get(3) : "";
//                                            }
//                                        }
//                                        n = em.createNativeQuery("INSERT INTO cpsms_detail(Header_MessageId,CPSMS_Batch_No,"
//                                                + "Account_Type,C2020_Dr_Transaction_Id,C4063_Dr_Amount,C3380_Settlement_Date,"
//                                                + "C5756_Agency_Bank_Branch_IFSC,C6021_Agency_Bank_Acno,C6091_Sending_Agency_Acname,"
//                                                + "C5629_Sender_Mobile,C7002_F,C7002_S,C7002_T,C7002_R,C4038_Cr_Amount,"
//                                                + "C5569_Ben_IFSC,C6061_Ben_Acno,UID,BankIIN,C6081_Ben_Name,C5565_Ben_Add,"
//                                                + "CPSMSTranId_Cr_Tran_Id,PmtMtd_Payment_Mode,C7495_F,C7495_S,C7495_T,C7495_R,"
//                                                + "C7495_V,C7495_X,Flag,Error_Code,Cbs_Reason,Cbs_Status,Entry_Date,Entry_Time,Entry_By) "
//                                                + "VALUES('" + payment.getMessageId() + "','" + batch.getCPSMSBatchNo() + "',"
//                                                + "'DR','" + debitAccount.getC2020() + "'," + new BigDecimal(debitAccount.getC4063()) + ","
//                                                + "'" + debitAccount.getC3380().toString() + "','" + debitAccount.getC5756() + "',"
//                                                + "'" + debitAccount.getC6021() + "','" + debitAccount.getC6091() + "',"
//                                                + "'" + debitAccount.getC5629() + "','" + C7002_F + "','" + C7002_S + "',"
//                                                + "'" + C7002_T + "','" + C7002_R + "'," + new BigDecimal("0") + ",'','',"
//                                                + "'','','','','','','','','','','','','','','','','" + ymd.format(new Date()) + "',"
//                                                + "current_timestamp,'System')").executeUpdate();
//                                        if (n <= 0) {
//                                            throw new Exception("Problem in data insertion in cpsms_detail");
//                                        }
//                                    }
//
//                                    List<CreditAccount> creditAccountList = batch.getCreditAccounts().getCreditAccount();
//                                    for (CreditAccount creditAccount : creditAccountList) {
//                                        String uid = creditAccount.getUID() == null ? "" : creditAccount.getUID();
//                                        String bankIIN = creditAccount.getBankIIN() == null ? "" : creditAccount.getBankIIN();
//                                        String receiverAddress = creditAccount.getC5565() == null ? "" : creditAccount.getC5565();
//
//                                        String C7495_F = "", C7495_S = "", C7495_T = "", C7495_R = "", C7495_V = "", C7495_X = "";
//                                        if (creditAccount.getRmtInf() != null) {
//                                            if (!creditAccount.getRmtInf().getC7495().isEmpty()) {
//                                                List<String> strList = creditAccount.getRmtInf().getC7495();
//                                                int listSize = strList.size();
//                                                C7495_F = listSize >= 1 ? strList.get(0) : "";
//                                                C7495_S = listSize >= 2 ? strList.get(1) : "";
//                                                C7495_T = listSize >= 3 ? strList.get(2) : "";
//                                                C7495_R = listSize >= 4 ? strList.get(3) : "";
//                                                C7495_V = listSize >= 5 ? strList.get(4) : "";
//                                                C7495_X = listSize >= 6 ? strList.get(5) : "";
//                                            }
//                                        }
//
//                                        n = em.createNativeQuery("INSERT INTO cpsms_detail(Header_MessageId,CPSMS_Batch_No,"
//                                                + "Account_Type,C2020_Dr_Transaction_Id,C4063_Dr_Amount,C3380_Settlement_Date,"
//                                                + "C5756_Agency_Bank_Branch_IFSC,C6021_Agency_Bank_Acno,C6091_Sending_Agency_Acname,"
//                                                + "C5629_Sender_Mobile,C7002_F,C7002_S,C7002_T,C7002_R,C4038_Cr_Amount,"
//                                                + "C5569_Ben_IFSC,C6061_Ben_Acno,UID,BankIIN,C6081_Ben_Name,C5565_Ben_Add,"
//                                                + "CPSMSTranId_Cr_Tran_Id,PmtMtd_Payment_Mode,C7495_F,C7495_S,C7495_T,C7495_R,"
//                                                + "C7495_V,C7495_X,Flag,Error_Code,Cbs_Reason,Cbs_Status,Entry_Date,Entry_Time,Entry_By) "
//                                                + "VALUES('" + payment.getMessageId() + "','" + batch.getCPSMSBatchNo() + "',"
//                                                + "'CR',''," + new BigDecimal("0") + ",'" + ymd.format(new Date()) + "','',"
//                                                + "'','','','','','',''," + new BigDecimal(creditAccount.getC4038()) + ","
//                                                + "'" + creditAccount.getC5569() + "','" + creditAccount.getC6061() + "',"
//                                                + "'" + uid + "','" + bankIIN + "','" + creditAccount.getC6081() + "',"
//                                                + "'" + receiverAddress + "','" + creditAccount.getCPSMSTranId() + "',"
//                                                + "'" + creditAccount.getPmtMtd() + "','" + C7495_F + "','" + C7495_S + "',"
//                                                + "'" + C7495_T + "','" + C7495_R + "','" + C7495_V + "','" + C7495_X + "',"
//                                                + "'','','','','" + ymd.format(new Date()) + "',current_timestamp,'System')").executeUpdate();
//                                        if (n <= 0) {
//                                            throw new Exception("Problem in data insertion in cpsms_detail");
//                                        }
//                                    }
//                                }
//                                //For success acknowledgement.
//                                //Generate and send success acknowledgement
//                                createAckFileToPaymentRequest(payment, CpsmsErrorEnum.PRINT_ADVICE_PAY_REQ_RECEIVED.getCode(), file.getName());
//                                n = em.createNativeQuery("update cpsms_header set cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' where "
//                                        + "messageid='" + payment.getMessageId() + "'").executeUpdate();
//                                if (n <= 0) {
//                                    throw new Exception("Problem In acknowledgement updation in cpsms_header.");
//                                }
//                                n = em.createNativeQuery("update cpsms_batch_detail set cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' where "
//                                        + "header_messageid='" + payment.getMessageId() + "'").executeUpdate();
//                                if (n <= 0) {
//                                    throw new Exception("Problem In acknowledgement updation in cpsms_batch_detail.");
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    System.out.println("Invalid File Extension !");
//                }
//            }
//            ut.commit();
//        } catch (Exception ex) {
//            try {
//                ut.rollback();
//                throw new Exception(ex.getMessage());
//            } catch (Exception e) {
//                throw new Exception(e.getMessage());
//            }
//        }
//    }
//    private void createAckFileToPaymentRequest(com.cbs.pojo.cpsms.paymentrequest.Payments payment,
//            String errorType, String paymentFileName) throws Exception {
//        try {
//            String[] arr = cpsmsCommonRemote.getCpsmsBankDetail();
//            String nextSeqNo = cpsmsCommonRemote.getMessageWiseSeqNo(CpsmsEnum.RETURN_MESSAGE_TYPE_01.getCode(), ymd.format(new Date()));
//
//            //Acknowledgement File Name.Here we have to check that name of incoming payment file 
//            String ackFileName = payment.getDestination() + payment.getPaymentProduct().toUpperCase()
//                    + CpsmsEnum.RETURN_MESSAGE_TYPE_01.getCode() + sdf.format(new Date()) + nextSeqNo + ".xml"; //This filename will change if required.
//
//            Acknowledgement ack = new Acknowledgement();
//            //Creating acknowledgement object
//            ack.setMessageId(payment.getDestination() + payment.getPaymentProduct().toUpperCase()
//                    + CpsmsEnum.RETURN_MESSAGE_TYPE_01.getCode() + sdf.format(new Date()) + nextSeqNo);
//            ack.setPaymentProduct(payment.getPaymentProduct().toUpperCase());
//            ack.setSource(payment.getDestination());
//            ack.setDestination("CPSMS");
//            ack.setBankCode(payment.getDestination());
//            ack.setBankName(arr[1]);
//            ack.setOriginalMessageId(payment.getMessageId());
//            ack.setRecordsCount(String.valueOf(payment.getRecordsCount()));
//            ack.setRecordsFound(String.valueOf(payment.getBatchDetails().size()));
//            if (errorType.equalsIgnoreCase("R")) {
//                ack.setResponseCode("S");
//                ack.setErrorCode("");
//            } else {
//                ack.setResponseCode("E");
//                ack.setErrorCode(errorType);
//            }
//
//            //Writing of acknowledgement file
//            File file = new File(props.getProperty("owAckLocation") + ackFileName);
//            JAXBContext jaxbContext = JAXBContext.newInstance(Acknowledgement.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            jaxbMarshaller.marshal(ack, file);
//            //Push acknowledgement file to sftp server location and make backup of this ack file on cbs
//            SftpUtil util = SftpUtilFactory.getSftpUtil();
//            SftpSession session = getSftpSession();
//
//            util.put(session, props.getProperty("owAckLocation") + ackFileName, props.getProperty("owSftpAckLocation"));
//            backupAndRemoveOfIndFile(props.getProperty("owAckLocation"), props.getProperty("owAckBkpLocation"), ackFileName);
//            util.disconnect(session);
//            //Print advice inward payment file1 backup
//            if (!errorType.equalsIgnoreCase("R")) { //In case of error only
//                backupAndRemoveOfIndFile(props.getProperty("iwPayReqLocation"), props.getProperty("iwBkpPayReqLocation"), paymentFileName);
//            } else {
//                backupAndRemoveOfIndFile(props.getProperty("iwPayReqLocation"), props.getProperty("iwPayReqProcessLocation"), paymentFileName);
//            }
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
//    }
    public String printAdviceBatchReturnProcessing(String dt, String messageId, String batchNo) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            //Print Advice Mismatch Case
            generatePrintAdviceInitiatedPaymentMessage(dt, messageId, batchNo, CpsmsErrorEnum.ERROR_999.getCode(), "");
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return "true";
    }

    //Here same error code is applyed to debit and credit transaction
    public String generatePrintAdviceInitiatedPaymentMessage(String dt, String messageId, String batchNo,
            String errorCode, String cbsTranId) throws Exception {
        try {
            Date curDt = new Date();
            List<String> strList = cpsmsCommonRemote.getCpsmsHeaderDetail(messageId);
            String[] arr = cpsmsCommonRemote.getCpsmsBankDetail();
//            String nextSeqNo = cpsmsCommonRemote.getMessageWiseSeqNo(CpsmsEnum.RETURN_MESSAGE_TYPE_02.getCode(),
//                    dt);
//            String initiatedPaymentFileName = strList.get(2) + strList.get(0) + CpsmsEnum.RETURN_MESSAGE_TYPE_02.getCode()
//                    + sdf.format(ymd.parse(dt)) + nextSeqNo + ".xml"; //This filename will change if required.

            String initiatedPaymentFileName = cpsmsCommonRemote.getOutwardFileName(strList.get(2), strList.get(0), CpsmsEnum.RETURN_MESSAGE_TYPE_02.getCode(), ymd.format(curDt), messageId); //This filename will change if required.

            InitiatedPayments initiatedPayments = new InitiatedPayments();

            initiatedPayments.setMessageId(initiatedPaymentFileName.substring(0, initiatedPaymentFileName.indexOf(".")));
            initiatedPayments.setPaymentProduct(strList.get(0));
            initiatedPayments.setSource(strList.get(2));
            initiatedPayments.setDestination("CPSMS");
            initiatedPayments.setBankCode(strList.get(2));
            initiatedPayments.setBankName(arr[1]);
            initiatedPayments.setRecordsCount(new Byte("1")); //Since we are processing only one batch at a time.

            InitiatedPayments.BatchDetails batchDetail = new InitiatedPayments.BatchDetails();
            batchDetail.setCPSMSBatchNo(batchNo);

            //DebitTransactions
            InitiatedPayments.BatchDetails.DebitTransactions debitTransactions = new InitiatedPayments.BatchDetails.DebitTransactions();
            List<CpsmsCommonDTO> drDetailList = cpsmsViewRemote.getPrintAdvicePaymentFileBatchDrDetail(dt, messageId, batchNo);
            if (drDetailList.isEmpty()) {
                throw new Exception("ERROR_1:There is no data found for debit transactions.");
            }

            BigDecimal totalDrAmount = new BigDecimal("0");
            List<InitiatedPayments.BatchDetails.DebitTransactions.Debit> debitList = debitTransactions.getDebit();
            for (CpsmsCommonDTO dto : drDetailList) {
                totalDrAmount = totalDrAmount.add(dto.getAmount());
                InitiatedPayments.BatchDetails.DebitTransactions.Debit debitObj = new InitiatedPayments.BatchDetails.DebitTransactions.Debit();
                debitObj.setC2006(dto.getTranId());
                debitObj.setC2020("0"); //Since there is no transaction
                if (!(cbsTranId == null || cbsTranId.equals(""))) {
                    debitObj.setC2020(cbsTranId.contains(".") ? cbsTranId.substring(0, cbsTranId.indexOf(".")) : cbsTranId);
                }
//                debitObj.setC3501(Long.parseLong(ymdhms.format(ymd.parse(dt))));
                debitObj.setC3501(Long.parseLong(ymdhms.format(curDt)));
                debitObj.setC4063(dto.getAmount().floatValue());
                debitObj.setC6346(errorCode);

                debitList.add(debitObj);
            }
            debitTransactions.setC5756(drDetailList.get(0).getIfsc());    //Since for a batch in case of debit ifsc will be same.
            debitTransactions.setC6021(drDetailList.get(0).getAcno());    //Since for a batch in case of debit debit a/c will be same.
            debitTransactions.setC5185(new Byte(String.valueOf(drDetailList.size())));
            debitTransactions.setC4063(totalDrAmount.floatValue());

            batchDetail.setDebitTransactions(debitTransactions);

            //CreditTransactions
            InitiatedPayments.BatchDetails.CreditTransactions creditTransactions = new InitiatedPayments.BatchDetails.CreditTransactions();
            List<CpsmsCommonDTO> crDetailList = cpsmsViewRemote.getPrintAdvicePaymentFileBatchCrDetail(dt, messageId, batchNo);
            if (crDetailList.isEmpty()) {
                throw new Exception("ERROR_2:There is no data found for credit transactions.");
            }

//            creditTransactions.setC5185(new Byte(String.valueOf(crDetailList.size())));
            creditTransactions.setC5185(new Integer(String.valueOf(crDetailList.size())));
            List<InitiatedPayments.BatchDetails.CreditTransactions.Credit> creditList = creditTransactions.getCredit();
            for (CpsmsCommonDTO dto : crDetailList) {
                InitiatedPayments.BatchDetails.CreditTransactions.Credit creditObj = new InitiatedPayments.BatchDetails.CreditTransactions.Credit();
                creditObj.setC2006(dto.getTranId());
                creditObj.setC5569(dto.getIfsc());
                creditObj.setC6061(dto.getAcno());
                creditObj.setUID(dto.getUid());
                creditObj.setBankIIN(dto.getBankIIN());
                creditObj.setC2020("0"); //Since there is no transaction was performed
                if (!(cbsTranId == null || cbsTranId.equals(""))) {
                    creditObj.setC2020(cbsTranId.contains(".") ? cbsTranId.substring(0, cbsTranId.indexOf(".")) : cbsTranId);
                }
                creditObj.setC3501(Long.parseLong(ymdhms.format(new Date())));
                creditObj.setC4038(dto.getAmount().floatValue());
                creditObj.setC6346(errorCode);
                creditObj.setC3380(Integer.parseInt(ymd.format(curDt))); //This values can be modified
                creditObj.setC3375(Integer.parseInt(ymd.format(curDt))); //This values can be modified
                creditObj.setC3381(Integer.parseInt(ymd.format(curDt))); //This values can be modified

                creditList.add(creditObj);
            }

            batchDetail.setCreditTransactions(creditTransactions);
            initiatedPayments.setBatchDetails(batchDetail);

            //Getting connection from SFTP server
            SftpUtil util = SftpUtilFactory.getSftpUtil();
            SftpSession session = getSftpSession();

//            int x = 1 / 0;

            //Writing of payment initiated file
            File file = new File(props.getProperty("iniPayCbsLocation") + initiatedPaymentFileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(InitiatedPayments.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(initiatedPayments, file);

            //Push initiated payment file to sftp server
            util.put(session, props.getProperty("iniPayCbsLocation") + initiatedPaymentFileName, props.getProperty("iniPaySftpLocation"));
            util.disconnect(session);

            //Cbs status updation for batch.
            String cbsStatus = errorCode;
            if (errorCode.equalsIgnoreCase("R00")) { //Payment initiated file of success debit transaction of print advice has been sent.
                cbsStatus = CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode();
            }
            int n = em.createNativeQuery("update cpsms_batch_detail set cbs_status='" + cbsStatus + "' "
                    + "where header_messageid='" + messageId + "' and cpsms_batch_no='" + batchNo + "' and "
                    + "entry_date='" + dt + "'").executeUpdate();
            if (n <= 0) {
                throw new Exception("ERROR_3:Problem in cpsms batch detail updation.");
            }
            //Update of cbs status in cpsms_header if required.
//            List list = em.createNativeQuery("select cpsms_batch_no from cpsms_batch_detail where "
//                    + "entry_date='" + dt + "' and header_messageid='" + messageId + "' and "
//                    + "cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "'").getResultList();

            List list = em.createNativeQuery("select cpsms_batch_no from cpsms_batch_detail where entry_date='" + dt + "' and "
                    + "header_messageid='" + messageId + "' and (cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' or cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "')").getResultList();

            if (list.isEmpty()) {
//                if (!errorCode.equalsIgnoreCase("R00")) { //Payment initiated file of success debit transaction of print advice has been sent
//                    cbsStatus = CpsmsEnum.PRINT_ADVICE_CBS_STATUS_04.getCode();
//                }
                n = em.createNativeQuery("update cpsms_header set cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_04.getCode() + "' "
                        + "where messageid='" + messageId + "' and entry_date='" + dt + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("ERROR_4:Problem in cpsms_header updation.");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "true";
    }

    public String printAdviceBatchProcessing(String dt, String messageId, String batchNo, String userName,
            String orgnBrCode) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Float trsno = 0f;
            Date curDt = new Date();
            //Debit transaction initiated.
            List<CpsmsCommonDTO> batchDrList = cpsmsViewRemote.getPrintAdvicePaymentFileBatchDrDetail(dt, messageId, batchNo);
            if (batchDrList.isEmpty()) {
                throw new Exception("ERROR_5:There is no debit transaction detail.");
            }
            //Since we assume that in a batch agency debit account no will be same.
            String result = ftsRemote.ftsAcnoValidate(batchDrList.get(0).getAcno(), 1, ""); //Just debit a/c no validation
            if (result.equalsIgnoreCase("true")) {
                boolean value = new Siplmatcher().equals(ftsRemote.getCustName(batchDrList.get(0).getAcno()), batchDrList.get(0).getName()); //Debit Name Validation
                if (value == true) {
                    BigDecimal totalDrAmount = new BigDecimal("0");
                    String cbsTransactionId = "";
                    for (CpsmsCommonDTO dto : batchDrList) {
                        totalDrAmount = totalDrAmount.add(dto.getAmount());
                        cbsTransactionId = cbsTransactionId + ParseFileUtil.addTrailingZeros(dto.getTranId(), 16);
                    }
                    result = ftsRemote.checkBalance(batchDrList.get(0).getAcno(), totalDrAmount.doubleValue(), userName); //Debit amount validation.
                    if (result.equalsIgnoreCase("true")) {
                        //First of all perform agency debit transation
                        try {
//                            ut.begin();
                            CpsmsCommonDTO dto = cpsmsViewRemote.getPrintAdviceBatchDetail(dt, messageId, batchNo);
                            if (dto == null) {
                                throw new Exception("ERROR_6:There is no batch detail found.");
                            }
                            if (!dto.getCbsStatus().equalsIgnoreCase(CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode())) {
                                cbsTransactionId = "CPSMS:-Dr Tran Id:" + cbsTransactionId + ":-Batch No:" + ParseFileUtil.addTrailingZeros(batchNo, 16) + ":-MessageId:" + messageId; //Min length will be of 48 length
                                trsno = ftsRemote.getTrsNo();
                                String debitAcnoNature = ftsRemote.getAccountNature(batchDrList.get(0).getAcno());

                                result = ftsRemote.insertRecons(debitAcnoNature, batchDrList.get(0).getAcno(), 1,
                                        totalDrAmount.doubleValue(), ymd.format(curDt), ymd.format(curDt), 2, cbsTransactionId, userName,
                                        trsno, "", ftsRemote.getRecNo(), "Y", userName, 72, 3, "", "", 0f, "", "",
                                        0, "", 0f, "", "", batchDrList.get(0).getAcno().substring(0, 2),
                                        batchDrList.get(0).getAcno().substring(0, 2), 0, "", "", "");
                                if (!result.equalsIgnoreCase("true")) {
                                    throw new Exception("ERROR_7:" + result);
                                }
                                result = ftsRemote.updateBalance(debitAcnoNature, batchDrList.get(0).getAcno(), 0d,
                                        totalDrAmount.doubleValue(), "Y", "Y");
                                if (!result.equalsIgnoreCase("true")) {
                                    throw new Exception("ERROR_8:" + result);
                                }
                                //Sending success debit transaction execution file.
                                String errorCode = "R00"; //For success debit transaction execution
                                generatePrintAdviceInitiatedPaymentMessage(dt, messageId, batchNo, errorCode, trsno.toString());
                                int z = em.createNativeQuery("update cpsms_batch_detail set debit_success_trsno=" + trsno + " where "
                                        + "header_messageid='" + messageId + "' and cpsms_batch_no='" + batchNo + "'").executeUpdate();
                                if (z <= 0) {
                                    throw new Exception("ERROR_9:There is issue in batch no updation.");
                                }
                            }
                            ut.commit();
                        } catch (Exception ex) {
//                            ut.rollback();
                            throw new Exception(ex.getMessage());
                        }
                        ut.begin();

                        //Preliminary Data
                        List batchList = em.createNativeQuery("select debit_success_trsno from cpsms_batch_detail where "
                                + "header_messageid='" + messageId + "' and cpsms_batch_no='" + batchNo + "'").getResultList();
                        if (batchList.isEmpty()) {
                            throw new Exception("ERROR_10:There is no success debit batch found.");
                        }
                        Vector batchVec = (Vector) batchList.get(0);
                        trsno = Float.parseFloat(batchVec.get(0).toString());

                        List isoList = em.createNativeQuery("select acno from abb_parameter_info "
                                + "where purpose = 'INTERSOLE ACCOUNT'").getResultList();
                        if (isoList.isEmpty()) {
                            throw new ApplicationException("ERROR_8:Please define Intersole A/c in abb parameter info");
                        }
                        Vector dataVec = (Vector) isoList.get(0);
                        String isoAccount = dataVec.get(0).toString();

                        List<String> cpsmsHeaderDetailList = cpsmsCommonRemote.getCpsmsHeaderDetail(messageId);
                        String[] cpsmsBankDetailArray = cpsmsCommonRemote.getCpsmsBankDetail();

                        //Now credit transaction processing.
                        List<CpsmsCommonDTO> crDetailList = cpsmsViewRemote.getPrintAdvicePaymentFileBatchCrDetail(dt, messageId, batchNo);
                        if (crDetailList.isEmpty()) {
                            throw new Exception("ERROR_11:There is no data found for credit transactions.");
                        }
                        //First we have to create two separate list for Intra and Inter bank beneficiaries
                        List<List<CpsmsCommonDTO>> combinedIntraAndInterBankList = getBeneficiaryForIntraAndInterBank(crDetailList);
                        if (!(combinedIntraAndInterBankList == null || combinedIntraAndInterBankList.isEmpty())) {
                            List<CpsmsCommonDTO> intraBankList = (combinedIntraAndInterBankList.get(0) == null || combinedIntraAndInterBankList.get(0).isEmpty()) ? new ArrayList<CpsmsCommonDTO>() : combinedIntraAndInterBankList.get(0);
                            List<CpsmsCommonDTO> interBankList = (combinedIntraAndInterBankList.get(1) == null || combinedIntraAndInterBankList.get(1).isEmpty()) ? new ArrayList<CpsmsCommonDTO>() : combinedIntraAndInterBankList.get(1);
                            //Intra Bank Processing
                            if (!(intraBankList == null || intraBankList.isEmpty())) {
                                crDetailList = intraBankCreditTxnProcessing(intraBankList, dt, messageId, batchNo,
                                        trsno.toString(), userName, orgnBrCode, isoAccount);

                                if (!crDetailList.isEmpty()) {
                                    //Final response file generation.
                                    intraBankFinalResponseGeneration(batchDrList, crDetailList, cpsmsHeaderDetailList,
                                            cpsmsBankDetailArray, dt, messageId, batchNo, trsno.toString(), userName, orgnBrCode);
                                }
                            }
                            //Inter Bank Processing
                            if (!(interBankList == null || interBankList.isEmpty())) {
                                generateInterBankOwTxns(interBankList, dt, messageId, batchNo, batchDrList.get(0).getAcno(),
                                        trsno.toString(), userName, orgnBrCode, isoAccount);
                                //New addition on 14/02/2017
                                //Cbs status updation for batch.
                                List chqList = em.createNativeQuery("select cpsms_batch_no from cpsms_batch_detail where "
                                        + "header_messageid='" + messageId + "' and cpsms_batch_no='" + batchNo + "' and "
                                        + "entry_date='" + dt + "'").getResultList();
                                if (!chqList.isEmpty()) {
                                    int n = em.createNativeQuery("update cpsms_batch_detail set cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_03.getCode() + "' "
                                            + "where header_messageid='" + messageId + "' and cpsms_batch_no='" + batchNo + "' and "
                                            + "entry_date='" + dt + "'").executeUpdate();
                                    if (n <= 0) {
                                        throw new Exception("Problem in cpsms batch detail updation.");
                                    }
                                }
                                //Updation of cbs status in cpsms_header if required.
                                List list = em.createNativeQuery("select cpsms_batch_no from cpsms_batch_detail where "
                                        + "entry_date='" + dt + "' and header_messageid='" + messageId + "' and "
                                        + "(cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' or "
                                        + "cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "')").getResultList();
                                if (list.isEmpty()) {
                                    int n = em.createNativeQuery("update cpsms_header set cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_07.getCode() + "' "
                                            + "where messageid='" + messageId + "' and entry_date='" + dt + "'").executeUpdate();
                                    if (n <= 0) {
                                        throw new Exception("Problem in cpsms_header updation.");
                                    }
                                }
                                //New addition on 14/02/2017 End Here
                            }
                        }
                    } else {
                        //Send Negative Initiated Payment File No.3 For Debit Amount.
                        String errorCode = getCpsmsErrorCode(result);
                        generatePrintAdviceInitiatedPaymentMessage(dt, messageId, batchNo, errorCode, "");
                    }
                } else {
                    //Send Negative Initiated Payment File No.3 For Debit A/c Name Mismatch.
                    String errorCode = getCpsmsErrorCode("name differes");
                    generatePrintAdviceInitiatedPaymentMessage(dt, messageId, batchNo, errorCode, "");
                }
            } else {
                //Send Negative Initiated Payment File No.3 For Invalid Account.
                String errorCode = getCpsmsErrorCode(result);
                generatePrintAdviceInitiatedPaymentMessage(dt, messageId, batchNo, errorCode, "");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return "true";
    }

    public List<CpsmsCommonDTO> intraBankCreditTxnProcessing(List<CpsmsCommonDTO> crDetailList, String dt, String messageId,
            String batchNo, String trsNo, String userName, String orgnBrCode, String isoAccountNo) throws Exception {
        List<CpsmsCommonDTO> returnList = new ArrayList<CpsmsCommonDTO>();
        String consoleGlHead = "", orgnIsoAcNo = "";
        try {
            Float recNo = ftsRemote.getRecNo();
            Date curDt = new Date();

            for (CpsmsCommonDTO dto : crDetailList) {
//                List resultList = em.createNativeQuery("select new_ac_no from cbs_acno_mapping where "
//                        + "old_ac_no='" + dto.getAcno().trim() + "' or new_ac_no='" + dto.getAcno().trim() + "'").getResultList();
//                if (!resultList.isEmpty()) {
                String result = ftsRemote.ftsAcnoValidate(dto.getAcno().trim(), 0, "");
                if (!result.equalsIgnoreCase("true")) {
                    returnList.add(retrieveCrObject(dto, "0", ymdhms.format(curDt), ymd.format(curDt),
                            ymd.format(curDt), ymd.format(curDt), "F", result));
                } else {
                    Boolean value = new Siplmatcher().equals(ftsRemote.getCustName(dto.getAcno().trim()), dto.getName());
                    if (value == false) {
                        returnList.add(retrieveCrObject(dto, "0", ymdhms.format(curDt), ymd.format(curDt),
                                ymd.format(curDt), ymd.format(curDt), "F", "name differes"));
                    } else {
                        String partyNature = ftsRemote.getAccountNature(dto.getAcno().trim());
                        Float txnRecNo = recNo + 1;
                        recNo = recNo + 1; //just to increment the recno so do not delete it.
                        String details = "CPSMS:-Cr Tran Id:" + ParseFileUtil.addTrailingZeros(dto.getTranId(), 16) + ":-Batch No:" + ParseFileUtil.addTrailingZeros(batchNo, 16) + ":-MessageId:" + messageId;
                        //Emi updation if exists for that a/c
                        try {
                            if (partyNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                                    || partyNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                                    || partyNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                int intPostOnDeposit = ftsRemote.getCodeForReportName("INT_POST_DEPOSIT");
                                if (intPostOnDeposit == 0) {
                                    String msg = ftsRemote.npaRecoveryUpdation(Float.parseFloat(trsNo), partyNature,
                                            dto.getAcno().trim(), ymd.format(curDt), dto.getAmount().doubleValue(), orgnBrCode,
                                            dto.getAcno().trim().substring(0, 2), userName);
                                    if (!msg.equalsIgnoreCase("true")) {
                                        throw new ApplicationException("Problem in npa recovery updation-->" + dto.getAcno().trim());
                                    }
                                }

                                recNo = recNo + 1;
                                List list = em.createNativeQuery("select acno from emidetails where "
                                        + "acno='" + dto.getAcno().trim() + "' and status='Unpaid'").getResultList();
                                if (!list.isEmpty()) {
                                    result = ftsRemote.loanDisbursementInstallment(dto.getAcno().trim(), dto.getAmount().doubleValue(), 0,
                                            "System", ymd.format(curDt), recNo, 1, "Through CPSMS");
                                    if (!result.equalsIgnoreCase("true")) {
                                        throw new ApplicationException("Emi details updation issue-->" + dto.getAcno().trim());
                                    }
                                }
                            }
                            //End Here
                            if (!dto.getAcno().trim().substring(0, 2).equalsIgnoreCase(orgnBrCode)) {//Remote branch transaction
                                //Party Branch ISO.
                                recNo = recNo + 1;
                                consoleGlHead = dto.getAcno().trim().substring(0, 2) + isoAccountNo;
                                int n = em.createNativeQuery("insert into gl_recon(acno,ty,dt,valuedt,dramt,cramt,balance,trantype,"
                                        + "details,iy,instno,instdt,enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,"
                                        + "tokenpaidby,subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                                        + "advicebrncode) values('" + consoleGlHead + "',1,'" + ymd.format(curDt) + "',"
                                        + "'" + ymd.format(curDt) + "'," + dto.getAmount().doubleValue() + ",0, 0,2,"
                                        + "'" + details + "',0,'','19000101','" + userName + "','Y'," + recNo + ","
                                        + "3,'" + userName + "'," + Float.parseFloat(trsNo) + ",72,0,'','',now(),"
                                        + "'" + orgnBrCode + "','" + dto.getAcno().trim().substring(0, 2) + "',0,"
                                        + "'','','' )").executeUpdate();
                                if (n <= 0) {
                                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
                                }

                                //Origin Branch ISO.
                                recNo = recNo + 1;
                                orgnIsoAcNo = orgnBrCode + isoAccountNo;
                                n = em.createNativeQuery("insert into gl_recon(acno,ty,dt,valuedt,dramt,cramt,balance,trantype,"
                                        + "details,iy,instno,instdt,enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,"
                                        + "tokenpaidby,subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                                        + "advicebrncode) values('" + orgnIsoAcNo + "',0,'" + ymd.format(curDt) + "',"
                                        + "'" + ymd.format(curDt) + "',0," + dto.getAmount().doubleValue() + ", 0,2,"
                                        + "'" + details + "',0,'','19000101','" + userName + "','Y'," + recNo + ","
                                        + "3,'" + userName + "'," + Float.parseFloat(trsNo) + ",72,0,'','',now(),"
                                        + "'" + orgnBrCode + "','" + dto.getAcno().trim().substring(0, 2) + "',"
                                        + "0,'','','' )").executeUpdate();
                                if (n <= 0) {
                                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
                                }
                            }
                        } catch (Exception ex) {
//                                returnList.add(retrieveCrObject(dto, "0", ymdhms.format(ymd.parse(dt)), dt,
//                                        dt, dt, "F", "cbs processing " + ex.getMessage()));
//                                continue;

                            throw new Exception(ex.getMessage());
                        }
                        //Emi updation end here

                        result = ftsRemote.insertRecons(partyNature, dto.getAcno().trim(), 0, dto.getAmount().doubleValue(),
                                ymd.format(curDt), ymd.format(curDt), 2, details, userName, Float.parseFloat(trsNo), "",
                                txnRecNo, "Y", userName, 72, 3, "", "", 0f, "", "", 0,
                                "", 0f, "", null, orgnBrCode, dto.getAcno().trim().substring(0, 2), 0, "", "", "");
                        if (!result.equalsIgnoreCase("true")) {
                            returnList.add(retrieveCrObject(dto, "0", ymdhms.format(curDt), ymd.format(curDt),
                                    ymd.format(curDt), ymd.format(curDt), "F", result));
                            continue;
                        }
                        result = ftsRemote.updateBalance(partyNature, dto.getAcno().trim(), dto.getAmount().doubleValue(), 0, "Y", "Y");
                        if (!result.equalsIgnoreCase("true")) {
                            returnList.add(retrieveCrObject(dto, "0", ymdhms.format(curDt), ymd.format(curDt),
                                    ymd.format(curDt), ymd.format(curDt), "F", result));
                            continue;
                        }
                        //For successful credit transaction
                        returnList.add(retrieveCrObject(dto, txnRecNo.toString(), ymdhms.format(curDt), ymd.format(curDt),
                                ymd.format(curDt), ymd.format(curDt), "S", ""));
                    }
                }
//                }
            }
            //Updation of recno
            ftsRemote.updateRecNo(recNo);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnList;
    }

    public String intraBankFinalResponseGeneration(List<CpsmsCommonDTO> drDetailList, List<CpsmsCommonDTO> crDataList,
            List<String> cpsmsHeaderDetailList, String[] cpsmsBankDetailArray, String dt, String messageId,
            String batchNo, String trsNo, String userName, String orgnBrCode) throws Exception {
        try {
            Date curDt = new Date();
            Float recNo = ftsRemote.getRecNo();
            String initiatedPaymentFileName = cpsmsCommonRemote.getOutwardFileName(cpsmsHeaderDetailList.get(2),
                    cpsmsHeaderDetailList.get(0), CpsmsEnum.RETURN_MESSAGE_TYPE_02.getCode(), ymd.format(curDt), messageId);

            InitiatedPayments initiatedPayments = new InitiatedPayments();

            initiatedPayments.setMessageId(initiatedPaymentFileName.substring(0, initiatedPaymentFileName.indexOf(".")));
            initiatedPayments.setPaymentProduct(cpsmsHeaderDetailList.get(0));
            initiatedPayments.setSource(cpsmsHeaderDetailList.get(2));
            initiatedPayments.setDestination("CPSMS");
            initiatedPayments.setBankCode(cpsmsHeaderDetailList.get(2));
            initiatedPayments.setBankName(cpsmsBankDetailArray[1]);
            initiatedPayments.setRecordsCount(new Byte("1")); //Since we are processing only one batch at a time.

            InitiatedPayments.BatchDetails batchDetail = new InitiatedPayments.BatchDetails();
            batchDetail.setCPSMSBatchNo(batchNo);

            //DebitTransactions
            String debitAcNo = "";
            InitiatedPayments.BatchDetails.DebitTransactions debitTransactions = new InitiatedPayments.BatchDetails.DebitTransactions();

            BigDecimal totalDrAmount = new BigDecimal("0");
            List<InitiatedPayments.BatchDetails.DebitTransactions.Debit> debitList = debitTransactions.getDebit();
            for (CpsmsCommonDTO dto : drDetailList) {
                totalDrAmount = totalDrAmount.add(dto.getAmount());
                InitiatedPayments.BatchDetails.DebitTransactions.Debit debitObj = new InitiatedPayments.BatchDetails.DebitTransactions.Debit();
                debitObj.setC2006(dto.getTranId());
                debitObj.setC2020(trsNo.contains(".") ? trsNo.substring(0, trsNo.indexOf(".")) : trsNo);
                debitObj.setC3501(Long.parseLong(ymdhms.format(curDt)));
                debitObj.setC4063(dto.getAmount().floatValue());
                debitObj.setC6346("R00"); //Since debit transactions are already successful. 

                debitList.add(debitObj);
            }
            debitTransactions.setC5756(drDetailList.get(0).getIfsc());    //Since for a batch in case of debit ifsc will be same.
            debitTransactions.setC6021(drDetailList.get(0).getAcno());    //Since for a batch in case of debit debit a/c will be same.
            debitAcNo = drDetailList.get(0).getAcno();
            debitTransactions.setC5185(new Byte(String.valueOf(drDetailList.size())));
            debitTransactions.setC4063(totalDrAmount.floatValue());

            batchDetail.setDebitTransactions(debitTransactions);

            //CreditTransactions
            InitiatedPayments.BatchDetails.CreditTransactions creditTransactions = new InitiatedPayments.BatchDetails.CreditTransactions();

//            creditTransactions.setC5185(new Byte(String.valueOf(crDataList.size())));
            creditTransactions.setC5185(new Integer(String.valueOf(crDataList.size())));
            List<InitiatedPayments.BatchDetails.CreditTransactions.Credit> creditList = creditTransactions.getCredit();
            for (CpsmsCommonDTO dto : crDataList) {
                InitiatedPayments.BatchDetails.CreditTransactions.Credit creditObj = new InitiatedPayments.BatchDetails.CreditTransactions.Credit();
                creditObj.setC2006(dto.getTranId());
                creditObj.setC5569(dto.getIfsc());
                creditObj.setC6061(dto.getAcno());
                creditObj.setUID(dto.getUid());
                creditObj.setBankIIN(dto.getBankIIN());
                creditObj.setC2020(dto.getBankTxnId().contains(".") ? dto.getBankTxnId().substring(0, dto.getBankTxnId().indexOf(".")) : dto.getBankTxnId());

                creditObj.setC3501(Long.parseLong(ymdhms.format(new Date())));  //This values can be modified
                creditObj.setC4038(dto.getAmount().floatValue());
                creditObj.setC6346(dto.getResonCode());
                creditObj.setC3380(Integer.parseInt(ymd.format(curDt))); //This values can be modified
                creditObj.setC3375(Integer.parseInt(ymd.format(curDt))); //This values can be modified
                creditObj.setC3381(Integer.parseInt(ymd.format(curDt))); //This values can be modified

                creditList.add(creditObj);
                //Reversal entry of non credited a/c
                if (!dto.getResonCode().equalsIgnoreCase("R00") && !dto.getCbsReason().equals("")) {
                    String partyNature = ftsRemote.getAccountNature(debitAcNo);
                    recNo = recNo + 1;
                    String details = ParseFileUtil.addTrailingZeros(dto.getTranId(), 16) + ParseFileUtil.addTrailingZeros(batchNo, 16) + messageId;
                    String result = ftsRemote.insertRecons(partyNature, debitAcNo, 0, dto.getAmount().doubleValue(),
                            ymd.format(curDt), ymd.format(curDt), 2, details, userName, Float.parseFloat(trsNo), "",
                            recNo, "Y", userName, 72, 3, "", "", 0f, "", "", 0,
                            "", 0f, "", null, orgnBrCode, orgnBrCode, 0, "", "", "");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new Exception(result);
                    }
                    result = ftsRemote.updateBalance(partyNature, debitAcNo, dto.getAmount().doubleValue(), 0, "Y", "Y");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new Exception(result);
                    }

//                    if (partyNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
//                            || partyNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
//                            || partyNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//                        int intPostOnDeposit = ftsRemote.getCodeForReportName("INT_POST_DEPOSIT");
//                        if (intPostOnDeposit == 0) {
//                            String msg = ftsRemote.npaRecoveryUpdation(Float.parseFloat(trsNo), partyNature, debitAcNo,
//                                    dt, dto.getAmount().doubleValue(), orgnBrCode, orgnBrCode, userName);
//                            if (!msg.equalsIgnoreCase("true")) {
//                                throw new ApplicationException("Problem in npa recovery updation");
//                            }
//                        }
//                        recNo = recNo + 1;
//                        List list = em.createNativeQuery("select acno from emidetails where "
//                                + "acno='" + debitAcNo + "' and status='Unpaid'").getResultList();
//                        if (!list.isEmpty()) {
//                            result = ftsRemote.loanDisbursementInstallment(debitAcNo, dto.getAmount().doubleValue(), 0,
//                                    "System", dt, recNo, 1, "Through CPSMS Reversal");
//                            if (!result.equalsIgnoreCase("true")) {
//                                throw new ApplicationException("Emi details updation issue");
//                            }
//                        }
//                    }
                }
                //Flag,Error Code,Cbs Reason Updation
                int n = em.createNativeQuery("update cpsms_detail set flag='" + dto.getFlag() + "',"
                        + "cbs_status='" + dto.getResonCode() + "',cbs_reason='" + dto.getCbsReason() + "' where "
                        + "header_messageid = '" + messageId + "' and cpsms_batch_no='" + batchNo + "' and "
                        + "cpsmstranid_cr_tran_id='" + dto.getTranId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in cpsms_detail table updation.");
                }
            }
            //Updation of recno
            ftsRemote.updateRecNo(recNo);

            batchDetail.setCreditTransactions(creditTransactions);
            initiatedPayments.setBatchDetails(batchDetail);

            //Getting connection
            SftpUtil util = SftpUtilFactory.getSftpUtil();
            SftpSession session = getSftpSession();

            //Writing of initiated payment file
            File file = new File(props.getProperty("iniPayCbsLocation") + initiatedPaymentFileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(InitiatedPayments.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(initiatedPayments, file);
            //Push initiated payment file to sftp server location and make backup of this ack file on cbs


            util.put(session, props.getProperty("iniPayCbsLocation") + initiatedPaymentFileName, props.getProperty("iniPaySftpLocation"));
            util.disconnect(session);
            //Cbs status updation for batch.
            int n = em.createNativeQuery("update cpsms_batch_detail set cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_03.getCode() + "' "
                    + "where header_messageid='" + messageId + "' and cpsms_batch_no='" + batchNo + "' and "
                    + "entry_date='" + dt + "'").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in cpsms batch detail updation.");
            }
            //Updation of cbs status in cpsms_header if required.
            List list = em.createNativeQuery("select cpsms_batch_no from cpsms_batch_detail where "
                    + "entry_date='" + dt + "' and header_messageid='" + messageId + "' and "
                    + "(cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_01.getCode() + "' or "
                    + "cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_06.getCode() + "')").getResultList();
            if (list.isEmpty()) {
                n = em.createNativeQuery("update cpsms_header set cbs_status='" + CpsmsEnum.PRINT_ADVICE_CBS_STATUS_07.getCode() + "' "
                        + "where messageid='" + messageId + "' and entry_date='" + dt + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in cpsms_header updation.");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "true";
    }

    public String generateInterBankOwTxns(List<CpsmsCommonDTO> interBankList, String paymentReceivedDt,
            String paymentMessageId, String paymentBatchNo, String debitAcno, String debitBatchNo,
            String userName, String orgnBrCode, String isoAccountNo) throws Exception {
        SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
        SimpleDateFormat dhmsss = new SimpleDateFormat("ddHHmmssSSS");
        try {
            Date curDt = new Date();
            Float recNo = ftsRemote.getRecNo();
            String[] arr = cpsmsCommonRemote.getOwSponsorBankName(); //We need here value or not but it should not throw
            if (arr[0] != null && arr[1] != null) {    //H2H Outward is running
                if (arr[1].trim().length() != 12) {
                    throw new Exception("Please define proper outward head.");
                }
                String owSponsorBankName = arr[0];
                String owHead = arr[1].trim();

                String uCRefNo = "", paymentType = "", senderCommModeType = "", senderCommMode = "", remmitInfo = "";
                String beneficiaryCode = "", beneficiaryEmailId = "", beneficiaryMobileNo = "";
                BigDecimal totalInterBankCrAmount = new BigDecimal(0);

                if (owSponsorBankName.equalsIgnoreCase("idbi")) {
                    senderCommModeType = "EML";
                    senderCommMode = cpsmsCommonRemote.getBankEmailId();
                }
                if (owSponsorBankName.equalsIgnoreCase("axis")) {
                    beneficiaryCode = debitAcno.substring(0, 2);
                }
                for (CpsmsCommonDTO dto : interBankList) {
                    if (owSponsorBankName.equalsIgnoreCase("icici")) {
                        uCRefNo = debitAcno.substring(0, 10) + ymdms.format(new Date());
                    } else if (owSponsorBankName.equalsIgnoreCase("axis")) {
                        uCRefNo = dhmsss.format(new Date());
                    } else {
                        uCRefNo = ymdms.format(new Date());
                    }

                    paymentType = "N";
                    remmitInfo = "NEFT";
                    BigDecimal creditAmount = dto.getAmount();
                    if (creditAmount.compareTo(new BigDecimal(ftsRemote.getCodeFromCbsParameterInfo("RTGS-AMOUNT-LIMIT"))) >= 0) {
                        paymentType = "R";
                        remmitInfo = "RTGS";
                    }

                    //Note- In case of idbi- senderCommModeType,senderCommMode,remmitInfo is mandatory.
                    //Note- In case of axis- beneficiaryCode is mandatory.
                    //Note- Currently we did not consider the SBI Bank.
                    int n = em.createNativeQuery("insert into neft_ow_details (paymenttype, uniquecustomerrefno, "
                            + "beneficiaryname, beneficiarycode, txnamt, paymentdate, creditaccountno, outsidebankifsccode, "
                            + "debitaccountno, beneficiaryemailid, beneficiarymobileno, cmsbankrefno, utrno, instno, "
                            + "instdate, dt, trantime, status, reason, details, orgbrnid, destbrnid, auth, enterby, authby, "
                            + "chargetype, chargeamount, file_name, sender_comm_mode_type, sender_comm_mode, remmit_info, "
                            + "outward_file_name, cpsms_messageid, cpsms_batch_no, cpsmstranid_cr_tran_id, debit_success_trsno,"
                            + "response_update_time,success_to_failure_flag,debit_amount,trs_no) "
                            + "values ('" + paymentType + "', '" + uCRefNo + "', '" + dto.getName().trim() + "', "
                            + "'" + beneficiaryCode + "', " + creditAmount + ", "
                            + "'" + dmy.format(curDt) + "', '" + dto.getAcno().trim() + "', "
                            + "'" + dto.getIfsc().trim() + "', '" + debitAcno + "', '" + beneficiaryEmailId + "', "
                            + "'" + beneficiaryMobileNo + "', '', '', '', '" + ymd.format(curDt) + "', "
                            + "'" + ymd.format(curDt) + "', current_timestamp(), 'P', '', 'Verified', '" + orgnBrCode + "', "
                            + "'" + orgnBrCode + "', 'Y', '" + userName + "', 'System', '', 0.00, '', "
                            + "'" + senderCommModeType + "', '" + senderCommMode + "', '" + remmitInfo + "', '', "
                            + "'" + paymentMessageId + "', '" + paymentBatchNo + "', '" + dto.getTranId().trim() + "', "
                            + "" + debitBatchNo + ",now(),'',0,0)").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("ERROR_12:Problem in outward entry generation.");
                    }
                    totalInterBankCrAmount = totalInterBankCrAmount.add(creditAmount);
                }
                //Credit to outward gl head
                recNo = recNo + 1;
                String details = "CPSMS:-Batch No:" + ParseFileUtil.addTrailingZeros(paymentBatchNo, 16) + ":-MessageId:" + paymentMessageId;
                String result = ftsRemote.insertRecons("PO", owHead, 0, totalInterBankCrAmount.doubleValue(),
                        ymd.format(curDt), ymd.format(curDt), 2, details, userName, Float.parseFloat(debitBatchNo), "",
                        recNo, "Y", "System", 72, 3, "", "", 0f, "", "", 0,
                        "", 0f, "", null, orgnBrCode, owHead.substring(0, 2), 0, "", "", "");
                if (!result.equalsIgnoreCase("true")) {
                    throw new Exception(result);
                }
                result = ftsRemote.updateBalance("PO", owHead, totalInterBankCrAmount.doubleValue(), 0, "Y", "Y");
                if (!result.equalsIgnoreCase("true")) {
                    throw new Exception(result);
                }
                //Gl Branch ISO
                recNo = recNo + 1;
                String consoleGlHead = owHead.substring(0, 2) + isoAccountNo;
                int n = em.createNativeQuery("insert into gl_recon(acno,ty,dt,valuedt,dramt,cramt,balance,trantype,"
                        + "details,iy,instno,instdt,enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,"
                        + "tokenpaidby,subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                        + "advicebrncode) values('" + consoleGlHead + "',1,'" + ymd.format(curDt) + "',"
                        + "'" + ymd.format(curDt) + "'," + totalInterBankCrAmount.doubleValue() + ",0, 0,2,"
                        + "'" + details + "',0,'','19000101','" + userName + "','Y'," + recNo + ","
                        + "3,'System'," + Float.parseFloat(debitBatchNo) + ",72,0,'','',now(),"
                        + "'" + orgnBrCode + "','" + owHead.trim().substring(0, 2) + "',0,"
                        + "'','','' )").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
                }

                //Origin Branch ISO.
                recNo = recNo + 1;
                String orgnIsoAcNo = orgnBrCode + isoAccountNo;
                n = em.createNativeQuery("insert into gl_recon(acno,ty,dt,valuedt,dramt,cramt,balance,trantype,"
                        + "details,iy,instno,instdt,enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,"
                        + "tokenpaidby,subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                        + "advicebrncode) values('" + orgnIsoAcNo + "',0,'" + ymd.format(curDt) + "',"
                        + "'" + ymd.format(curDt) + "',0," + totalInterBankCrAmount.doubleValue() + ", 0,2,"
                        + "'" + details + "',0,'','19000101','" + userName + "','Y'," + recNo + ","
                        + "3,'System'," + Float.parseFloat(debitBatchNo) + ",72,0,'','',now(),"
                        + "'" + orgnBrCode + "','" + owHead.trim().substring(0, 2) + "',"
                        + "0,'','','' )").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
                }
            } else {    //No H2H Process
                String partyNature = ftsRemote.getAccountNature(debitAcno);

                String uCRefNo = "", paymentType = "", senderCommModeType = "", senderCommMode = "", remmitInfo = "";
                String beneficiaryCode = "", beneficiaryEmailId = "", beneficiaryMobileNo = "";
                for (CpsmsCommonDTO dto : interBankList) {
                    recNo = recNo + 1;
                    String details = "CPSMS:-Cr Tran Id:" + ParseFileUtil.addTrailingZeros(dto.getTranId(), 16) + ":-Batch No:"
                            + ParseFileUtil.addTrailingZeros(paymentBatchNo, 16) + ":-MessageId:" + paymentMessageId;
                    String result = ftsRemote.insertRecons(partyNature, debitAcno, 0, dto.getAmount().doubleValue(),
                            ymd.format(curDt), ymd.format(curDt), 2, details, userName, Float.parseFloat(debitBatchNo), "",
                            recNo, "Y", userName, 72, 3, "", "", 0f, "", "", 0,
                            "", 0f, "", null, orgnBrCode, orgnBrCode, 0, "", "", "");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new Exception(result);
                    }
                    result = ftsRemote.updateBalance(partyNature, debitAcno, dto.getAmount().doubleValue(), 0, "Y", "Y");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new Exception(result);
                    }

                    uCRefNo = ymdms.format(new Date());
                    paymentType = "N";
                    remmitInfo = "NEFT";
                    BigDecimal creditAmount = dto.getAmount();
                    if (creditAmount.compareTo(new BigDecimal(ftsRemote.getCodeFromCbsParameterInfo("RTGS-AMOUNT-LIMIT"))) >= 0) {
                        paymentType = "R";
                        remmitInfo = "RTGS";
                    }

                    int n = em.createNativeQuery("insert into neft_ow_details (paymenttype, uniquecustomerrefno, "
                            + "beneficiaryname, beneficiarycode, txnamt, paymentdate, creditaccountno, outsidebankifsccode, "
                            + "debitaccountno, beneficiaryemailid, beneficiarymobileno, cmsbankrefno, utrno, instno, "
                            + "instdate, dt, trantime, status, reason, details, orgbrnid, destbrnid, auth, enterby, authby, "
                            + "chargetype, chargeamount, file_name, sender_comm_mode_type, sender_comm_mode, remmit_info, "
                            + "outward_file_name, cpsms_messageid, cpsms_batch_no, cpsmstranid_cr_tran_id, debit_success_trsno,"
                            + "response_update_time,success_to_failure_flag,debit_amount,trs_no) "
                            + "values ('" + paymentType + "', '" + uCRefNo + "', '" + dto.getName().trim() + "', "
                            + "'" + beneficiaryCode + "', " + creditAmount + ", "
                            + "'" + dmy.format(curDt) + "', '" + dto.getAcno().trim() + "', "
                            + "'" + dto.getIfsc().trim() + "', '" + debitAcno + "', '" + beneficiaryEmailId + "', "
                            + "'" + beneficiaryMobileNo + "', '', '', '', '" + ymd.format(curDt) + "', "
                            + "'" + ymd.format(curDt) + "', current_timestamp(), 'U', 'H2H OW NOT RUNNING', 'Un-Paid', '" + orgnBrCode + "', "
                            + "'" + orgnBrCode + "', 'Y', '" + userName + "', 'System', '', 0.00, '', "
                            + "'" + senderCommModeType + "', '" + senderCommMode + "', '" + remmitInfo + "', '', "
                            + "'" + paymentMessageId + "', '" + paymentBatchNo + "', '" + dto.getTranId().trim() + "', "
                            + "" + debitBatchNo + ",now(),'',0,0)").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("ERROR_13:Problem in outward entry generation.");
                    }
                }
            }
            //Updation of recno
            ftsRemote.updateRecNo(recNo);
            //cpsms_inter_batch_detail table insertion.
            int n = em.createNativeQuery("insert into cpsms_inter_batch_detail(payment_file_received_dt,payment_file_messageid,"
                    + "payment_file_batch_no,outgoing_flag,upload_branch_code) values('" + ymd.format(curDt) + "','" + paymentMessageId + "',"
                    + "'" + paymentBatchNo + "','R','" + orgnBrCode + "')").executeUpdate();
            if (n <= 0) {
                throw new Exception("ERROR_14:Problem in cpsms_inter_batch_detail insertion.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "true";
    }

    public CpsmsCommonDTO retrieveCrObject(CpsmsCommonDTO dto, String bankTxnId, String txnInitiatedDate,
            String settlementDate, String remittanceInitiatedDate, String originalSettlementDate, String flag,
            String reason) throws Exception {
        CpsmsCommonDTO obj = new CpsmsCommonDTO();
        try {
            obj.setTranId(dto.getTranId());
            obj.setIfsc(dto.getIfsc());
            obj.setAcno(dto.getAcno());
            obj.setUid(dto.getUid());
            obj.setBankIIN(dto.getBankIIN());
            obj.setAmount(dto.getAmount());
            obj.setBankTxnId(bankTxnId);
            obj.setTxnInitiatedDate(txnInitiatedDate);
            obj.setSettlementDate(settlementDate);
            obj.setRemittanceInitiatedDate(remittanceInitiatedDate);
            obj.setOriginalSettlementDate(originalSettlementDate);
            obj.setFlag(flag); //S-For Success, F-For Failure
            obj.setResonCode((reason == null || reason.equals("")) ? "R00" : getCpsmsErrorCode(reason)); //R00 for successful
            obj.setCbsReason((reason == null || reason.equals("")) ? "" : reason);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return obj;
    }

    public List<List<CpsmsCommonDTO>> getBeneficiaryForIntraAndInterBank(List<CpsmsCommonDTO> crDetailList) throws Exception {
        List<List<CpsmsCommonDTO>> returnList = new ArrayList<List<CpsmsCommonDTO>>();
        List<CpsmsCommonDTO> intraBankList = new ArrayList<CpsmsCommonDTO>();
        List<CpsmsCommonDTO> interBankList = new ArrayList<CpsmsCommonDTO>();
        try {
            for (CpsmsCommonDTO dto : crDetailList) {
                List list = cpsmsViewRemote.isIntraBankAccountNo(dto.getAcno().trim());
                if (list.isEmpty()) { //Inter Bank Beneficiary
                    interBankList.add(dto);
                } else { //Intra Bank Beneficiary
                    intraBankList.add(dto);
                }
            }
            returnList.add(intraBankList);
            returnList.add(interBankList);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnList;
    }

    //This method will either commit or rollback.
    public String generateInterBankResponse(String fileType, String paymentReceivedDt, String messageId, String batchNo,
            List<NeftOwDetailsTO> batchDetailList, String userName, String brnCode) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Boolean isRespondedBatch = true;
            for (NeftOwDetailsTO obj : batchDetailList) {
                if (!(obj.getStatus().trim().equalsIgnoreCase("S") || obj.getStatus().trim().equalsIgnoreCase("U"))) {
                    isRespondedBatch = false;
                    break;
                }
            }
            if (isRespondedBatch == false) {
                throw new Exception("Please verify that all transactions are not either successful or failed.");
            }
            List<String> cpsmsHeaderDetailList = cpsmsCommonRemote.getCpsmsHeaderDetail(messageId);
            String[] cpsmsBankDetailArray = cpsmsCommonRemote.getCpsmsBankDetail();

            String initiatedPaymentFileName = cpsmsCommonRemote.getOutwardFileName(cpsmsHeaderDetailList.get(2),
                    cpsmsHeaderDetailList.get(0), CpsmsEnum.RETURN_MESSAGE_TYPE_02.getCode(), ymd.format(new Date()), messageId);

            InitiatedPayments initiatedPayments = new InitiatedPayments();

            initiatedPayments.setMessageId(initiatedPaymentFileName.substring(0, initiatedPaymentFileName.indexOf(".")));
            initiatedPayments.setPaymentProduct(cpsmsHeaderDetailList.get(0));
            initiatedPayments.setSource(cpsmsHeaderDetailList.get(2));
            initiatedPayments.setDestination("CPSMS");
            initiatedPayments.setBankCode(cpsmsHeaderDetailList.get(2));
            initiatedPayments.setBankName(cpsmsBankDetailArray[1]);
            initiatedPayments.setRecordsCount(new Byte("1")); //Since we are processing only one batch at a time.

            InitiatedPayments.BatchDetails batchDetail = new InitiatedPayments.BatchDetails();
            batchDetail.setCPSMSBatchNo(batchNo);

            //DebitTransactions
            BigDecimal totalDrAmount = new BigDecimal("0");

            InitiatedPayments.BatchDetails.DebitTransactions debitTransactions = new InitiatedPayments.BatchDetails.DebitTransactions();
            List<CpsmsCommonDTO> drDetailList = cpsmsViewRemote.getPrintAdvicePaymentFileBatchDrDetailForIBResponse(paymentReceivedDt, messageId, batchNo);
            if (drDetailList.isEmpty()) {
                throw new Exception("There is no data found for debit transactions.");
            }
            String trsNo = batchDetailList.get(0).getDebitSuccessTrsNo().toString();

            List<InitiatedPayments.BatchDetails.DebitTransactions.Debit> debitList = debitTransactions.getDebit();
            for (CpsmsCommonDTO dto : drDetailList) {
                totalDrAmount = totalDrAmount.add(dto.getAmount());
                InitiatedPayments.BatchDetails.DebitTransactions.Debit debitObj = new InitiatedPayments.BatchDetails.DebitTransactions.Debit();
                debitObj.setC2006(dto.getTranId());
                debitObj.setC2020(trsNo.contains(".") ? trsNo.substring(0, trsNo.indexOf(".")) : trsNo);
                debitObj.setC3501(Long.parseLong(ymdhms.format(ymd.parse(paymentReceivedDt))));
                debitObj.setC4063(dto.getAmount().floatValue());
                debitObj.setC6346("R00"); //Since debit transactions are already successful. 

                debitList.add(debitObj);
            }
            debitTransactions.setC5756(drDetailList.get(0).getIfsc());    //Since for a batch in case of debit ifsc will be same.
            debitTransactions.setC6021(drDetailList.get(0).getAcno());    //Since for a batch in case of debit debit a/c will be same.
            debitTransactions.setC5185(new Byte(String.valueOf(drDetailList.size())));
            debitTransactions.setC4063(totalDrAmount.floatValue());

            batchDetail.setDebitTransactions(debitTransactions);

            //CreditTransactions
            InitiatedPayments.BatchDetails.CreditTransactions creditTransactions = new InitiatedPayments.BatchDetails.CreditTransactions();

//            creditTransactions.setC5185(new Byte(String.valueOf(batchDetailList.size())));
            creditTransactions.setC5185(new Integer(String.valueOf(batchDetailList.size())));
            List<InitiatedPayments.BatchDetails.CreditTransactions.Credit> creditList = creditTransactions.getCredit();
            for (NeftOwDetailsTO dto : batchDetailList) {
                InitiatedPayments.BatchDetails.CreditTransactions.Credit creditObj = new InitiatedPayments.BatchDetails.CreditTransactions.Credit();
                creditObj.setC2006(dto.getCreditTranId());
                creditObj.setC5569(dto.getOutsideBankIfscCode());
                creditObj.setC6061(dto.getCreditAccountNo());
                creditObj.setUID("");
                creditObj.setBankIIN("");
                creditObj.setC2020(dto.getUtrNo().equals("") ? dto.getCmsBankRefNo() : dto.getUtrNo());

                creditObj.setC3501(Long.parseLong(ymdhms.format(ymd.parse(paymentReceivedDt))));  //This values can be modified
                creditObj.setC4038(dto.getTxnAmt().floatValue());

                String reasonCode = "R00", reason = "";
                if (dto.getStatus().trim().equalsIgnoreCase("S")) {
                    creditObj.setC6346("R00");
                } else {
                    reason = dto.getReason().trim();
                    reasonCode = getInterBankCpsmsErrorCode(dto.getReason().trim());
                    creditObj.setC6346(reasonCode);
                }
                creditObj.setC3380(Integer.parseInt(dto.getResponseUpdateTime())); //This values can be modified
                creditObj.setC3375(Integer.parseInt(paymentReceivedDt)); //This values can be modified
                creditObj.setC3381(Integer.parseInt(dto.getResponseUpdateTime())); //This values can be modified

                creditList.add(creditObj);

                //Flag,Error Code,Cbs Reason Updation
                String flag = dto.getStatus().trim().equalsIgnoreCase("S") ? "S" : "F";

                int n = em.createNativeQuery("update cpsms_detail set flag='" + flag + "',"
                        + "cbs_status='" + reasonCode + "',cbs_reason='" + reason + "' where "
                        + "header_messageid = '" + messageId + "' and cpsms_batch_no='" + batchNo + "' and "
                        + "cpsmstranid_cr_tran_id='" + dto.getCreditTranId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in cpsms_detail table updation.");
                }
                //success_to_failure_flag updation
                if (dto.getSuccessToFailureFlag().equalsIgnoreCase("R")) {
                    n = em.createNativeQuery("update neft_ow_details set success_to_failure_flag='Y' where "
                            + "uniquecustomerrefno='" + dto.getUniqueCustomerRefNo() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in neft_ow_details table success_to_failure_flag updation.");
                    }
                }
            }
            batchDetail.setCreditTransactions(creditTransactions);
            initiatedPayments.setBatchDetails(batchDetail);
            //Updation of inter bank response flag generation.
            if (fileType.equals("IBB")) {
                int n = em.createNativeQuery("update cpsms_inter_batch_detail set outgoing_flag='Y' where "
                        + "payment_file_received_dt='" + paymentReceivedDt + "' and payment_file_messageid='" + messageId + "' and "
                        + "payment_file_batch_no='" + batchNo + "' and upload_branch_code='" + brnCode + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in cpsms_inter_batch_detail table outgoing_flag updation.");
                }
            }
            //Getting connection
            SftpUtil util = SftpUtilFactory.getSftpUtil();
            SftpSession session = getSftpSession();

            //Writing of initiated payment file
            File file = new File(props.getProperty("iniPayCbsLocation") + initiatedPaymentFileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(InitiatedPayments.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(initiatedPayments, file);
            //Push initiated payment file to sftp server location and make backup of this ack file on cbs
            util.put(session, props.getProperty("iniPayCbsLocation") + initiatedPaymentFileName, props.getProperty("iniPaySftpLocation"));
            util.disconnect(session);
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return "true";
    }

    public String getInterBankCpsmsErrorCode(String cbsErrorMessage) throws Exception {
        //We have to add error list provided by CPSMS here.
        try {
            cbsErrorMessage = cbsErrorMessage.trim().toLowerCase();
            if (cbsErrorMessage.contains("account does not exist") || cbsErrorMessage.contains("ac does not exist")
                    || cbsErrorMessage.contains("acc does not exist") || cbsErrorMessage.contains("a/c does not exist")
                    || cbsErrorMessage.contains("ac unavailable") || cbsErrorMessage.contains("acc unavailable")
                    || cbsErrorMessage.contains("account unavailable") || cbsErrorMessage.contains("invalid account number")
                    || cbsErrorMessage.contains("incorrect account number") || cbsErrorMessage.contains("incorrect creditor account")
                    || cbsErrorMessage.contains("invalid account no") || cbsErrorMessage.contains("invalid account")
                    || cbsErrorMessage.contains("bene account no not valid") || cbsErrorMessage.contains("invalid ac")
                    || cbsErrorMessage.contains("account stopped") || cbsErrorMessage.contains("ac stopped")
                    || cbsErrorMessage.contains("no such account")) {
                return CpsmsErrorEnum.ERROR_R03.getCode();
            } else if (cbsErrorMessage.contains("acc closed") || cbsErrorMessage.contains("account closed")
                    || cbsErrorMessage.contains("ac closed")) {
                return CpsmsErrorEnum.ERROR_R01.getCode();
            } else if (cbsErrorMessage.contains("name differ")) {
                return CpsmsErrorEnum.ERROR_R05.getCode();
            } else if (cbsErrorMessage.contains("no such account type")) {
                return CpsmsErrorEnum.ERROR_R04.getCode();
            } else {
                return CpsmsErrorEnum.ERROR_R11.getCode();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String getCpsmsErrorCode(String cbsErrorMessage) throws Exception {
        //We have to add error list provided by CPSMS here.
        try {
            cbsErrorMessage = cbsErrorMessage.trim().toLowerCase();
            if (cbsErrorMessage.contains("invalid account nature")
                    || cbsErrorMessage.contains("no number exist")
                    || cbsErrorMessage.contains("account number does not exist or not authorized!")
                    || cbsErrorMessage.contains("account number does not exist !")
                    || cbsErrorMessage.contains("sorry,invalid account status")
                    || cbsErrorMessage.contains("invalid a/c no")
                    || cbsErrorMessage.contains("invalid accountnature of a/c no")) {
                return CpsmsErrorEnum.ERROR_R03.getCode();
            } else if (cbsErrorMessage.contains("account is closed")) {
                return CpsmsErrorEnum.ERROR_R01.getCode();
            } else if (cbsErrorMessage.contains("operation stopped for this account")
                    || cbsErrorMessage.contains("withdrawal stopped for this account")
                    || cbsErrorMessage.contains("account has been frozen")) {
                return CpsmsErrorEnum.ERROR_R09.getCode();
            } else if (cbsErrorMessage.contains("account is marked as inoperative")) {
                return CpsmsErrorEnum.ERROR_R11.getCode();
            } else if (cbsErrorMessage.contains("name differes")) {
                return CpsmsErrorEnum.ERROR_R05.getCode();
            } else if (cbsErrorMessage.contains("invalid transaction mode for account no")
                    || cbsErrorMessage.contains("problem in recno generation for account no")
                    || cbsErrorMessage.contains("insertion problem in recons for a/c no")
                    || cbsErrorMessage.contains("cbs processing")
                    || cbsErrorMessage.contains("reconbalan is not updated")
                    || cbsErrorMessage.contains("data is not inserted in reconbalan")
                    || cbsErrorMessage.contains("ca_reconbalan is not updated")
                    || cbsErrorMessage.contains("data is not inserted in ca_reconbalan")
                    || cbsErrorMessage.contains("tdreconbalan is not updated")
                    || cbsErrorMessage.contains("data is not inserted in tdreconbalan")) {
                return CpsmsErrorEnum.ERROR_R11.getCode();
            } else if (cbsErrorMessage.contains("balance exceeds")
                    || cbsErrorMessage.contains("balance exceeds the lien value")) {
                return CpsmsErrorEnum.ERROR_BALANCE_EXCEED.getCode();
            } else {
                return CpsmsErrorEnum.ERROR_R11.getCode();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private SftpSession getSftpSession() throws ApplicationException {
        SftpUtil util = SftpUtilFactory.getSftpUtil();
        SftpSession session = null;
        try {
            session = util.connectByPasswdAuth(props.getProperty("cpsmsSftpHost"),
                    props.getProperty("cpsmsSftpUser"), props.getProperty("cpsmsSftpPassword"),
                    SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO);
        } catch (SftpException e) {
            throw new ApplicationException(e.getMessage());
        }
        return session;
    }

    public boolean isCPSMSModuleOn() throws ApplicationException {
        try {
            List list = em.createNativeQuery("SELECT code from parameterinfo_report "
                    + "where reportname='CPSMS'").getResultList();
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
//    private Node getNodeForRootElement(String rootElementName) throws ApplicationException {
//        try {
//            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//            //root elements
//            Document doc = docBuilder.newDocument();
//            Element rootElement = doc.createElement(rootElementName);
//            doc.appendChild(rootElement);
//            Node node = doc.getFirstChild();
//            return node;
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//    }
//    private void backupAndRemoveOfIndFile(String localDir, String localBkpDir, String fileName) throws IOException {
//        try {
//            FileInputStream fis = null;
//            FileOutputStream fos = null;
//
//            File file = new File(localDir + fileName);
//            fis = new FileInputStream(file);
//            fos = new FileOutputStream(localBkpDir + file.getName());
//
//            byte[] buffer = new byte[1024];
//            int length;
//            //Copy the file content in bytes 
//            while ((length = fis.read(buffer)) > 0) {
//                fos.write(buffer, 0, length);
//            }
//            //Delete the original file
//            file.delete();
//            fos.close();
//            fis.close();
//        } catch (Exception ex) {
//            throw new IOException(ex.getMessage());
//        }
//    }
//    private void createBackupAndThenRemoveFile(String localIwFileDir, String localIwBackupDir) throws IOException {
//        try {
//            FileInputStream fis = null;
//            FileOutputStream fos = null;
//
//            File dir = new File(localIwFileDir);
//            File[] files = dir.listFiles();
//            for (File file : files) {
//                fis = new FileInputStream(file);
//                fos = new FileOutputStream(localIwBackupDir + file.getName());
//
//                byte[] buffer = new byte[1024];
//                int length;
//                //Copy the file content in bytes 
//                while ((length = fis.read(buffer)) > 0) {
//                    fos.write(buffer, 0, length);
//                }
//                //Delete the original file
//                file.delete();
//                fos.close();
//                fis.close();
//            }
//        } catch (Exception ex) {
//            throw new IOException(ex.getMessage());
//        }
//    }
//    private boolean isFileExist(SftpUtil util, SftpSession session, String path) throws ApplicationException {
//        try {
//            SftpResult result = util.ls(session, path);
//            if (result.getSuccessFlag()) {
//                List<SftpFile> list = (List<SftpFile>) result.getExtension();
//                if (list.size() > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//    }
    //    public boolean isIntraBankCredits(List<CpsmsCommonDTO> crDetailList) throws Exception {
//        try {
//            String allAcno = "";
//            for (CpsmsCommonDTO dto : crDetailList) {
//                if (allAcno.equals("")) {
//                    allAcno = "\'" + dto.getAcno() + "\'";
//                } else {
//                    allAcno = allAcno + ",\'" + dto.getAcno() + "\'";
//                }
//            }
//            List list = cpsmsViewRemote.isIntraBankAccountNo(allAcno);
//            if (crDetailList.size() != list.size()) {
//                return false; //Inter Bank A/c
//            }
//        } catch (Exception ex) {
//            throw new Exception(ex.getMessage());
//        }
//        return true;
//    }

    @Override
    public void accValQuickResponse() throws Exception {
        MsgRes msgRes = new MsgRes();
        List<HoReconsilePojo> msgReqMap = new ArrayList<HoReconsilePojo>();
        try {
            UserTransaction ut = context.getUserTransaction();
            List acValQuickResList = em.createNativeQuery("select cd.MessageId, cd.Accountno, cd.Bsrcode, cd.entitycode,"
                    + "cd.accounttype, cd.statecode, cd.pan, ch.bankcode,cd.txnid,ch.RequestFileName,ch.entryDate from cpsms_acc_detail cd, cpsms_acc_header ch where "
                    + "cd.reqStatus ='VR' and cd.MessageId = ch.MessageId").getResultList();
            if (!acValQuickResList.isEmpty()) {
                ut.begin();
                List bnkCodeList = em.createNativeQuery("select Distinct bankCode,bankname from cpsms_acc_header").getResultList();
                if (!bnkCodeList.isEmpty()) {
                    Vector bnVec = (Vector) bnkCodeList.get(0);

                    String nextSeqNo = getMaxSeqNo("AV", ymd.format(new Date()));
                    String resFileName = bnVec.get(0).toString() + "AVQRES" + sdf.format(new Date()) + nextSeqNo + ".xml";

                    msgRes.setMessageId(bnVec.get(0).toString() + "AVQRES" + sdf.format(new Date()) + nextSeqNo);
                    msgRes.setSource(bnVec.get(0).toString());
                    msgRes.setDestination("CPSMS");
                    msgRes.setBankCode(bnVec.get(0).toString());
                    msgRes.setBankName(bnVec.get(1).toString());
                    msgRes.setRecordsCount(acValQuickResList.size());
                    msgRes.setXmlns("http://cpsms.com/AccountValidationQuickResponse");

                    AHDetails ahDtl;
                    AH ah;
                    List<AcctResDetail> aclist = new ArrayList<AcctResDetail>();
                    for (int i = 0; i < acValQuickResList.size(); i++) {
                        Vector ele = (Vector) acValQuickResList.get(i);
                        String reqMId = ele.get(0).toString();
                        String bsrCd = ele.get(2).toString();
                        String entCode = ele.get(3).toString();
                        String txid = ele.get(8).toString();

                        int index = getIndex(msgReqMap, reqMId);
                        if (index < 0) {
                            HoReconsilePojo msgPojo = new HoReconsilePojo();
                            msgPojo.setBaseBranch(reqMId);
                            msgPojo.setRespondingBranch(ele.get(9).toString());
                            msgPojo.setTranDt(ele.get(10).toString());
                            msgReqMap.add(msgPojo);
                        }

                        AcctResDetail acResMsg = new AcctResDetail();
                        List<AH> ahDtlList = new ArrayList<AH>();

                        List acDetail = getAccountDetail(ele.get(1).toString());

                        acResMsg.setReqMsgId(reqMId);
                        acResMsg.setAccountNo(ele.get(1).toString());
                        if (acDetail.isEmpty()) {
                            acResMsg.setAcctValidity("I");
                            acResMsg.setAcctStatus("");
                            acResMsg.setAccountType("");
                            acResMsg.setBsrCode(bsrCd);
                            acResMsg.setIfscCode("");
                            acResMsg.setEntityCode(entCode);
                            acResMsg.setMicrCode("");
                            ahDtl = new AHDetails();
                            ah = new AH();
                            ahDtlList.add(ah);

                            ahDtl.setAhDtl(ahDtlList);
                            acResMsg.setAhDetails(ahDtl);

                            int valRes = em.createNativeQuery("update cpsms_acc_detail set valStatus='I' where txnid='" + txid + "'").executeUpdate();
                            if (valRes <= 0) {
                                ut.rollback();
                                throw new Exception("Problem in cpsms_acc_detail data updation !!");
                            }
                        } else {
                            for (int k = 0; k < acDetail.size(); k++) {
                                Vector acVec = (Vector) acDetail.get(k);
                                acResMsg.setAcctValidity("V");
                                acResMsg.setAcctStatus(AccStatus.getStatus(Integer.parseInt(acVec.get(12).toString())));
                                acResMsg.setAccountType(acVec.get(16).toString());

                                acResMsg.setBsrCode(bsrCd);
                                acResMsg.setIfscCode(acVec.get(0).toString());
                                acResMsg.setEntityCode(entCode);
                                acResMsg.setMicrCode(acVec.get(18).toString());
                                ahDtl = new AHDetails();
                                ah = new AH();
                                ah.setName(acVec.get(1).toString());
                                ah.setsName(acVec.get(2).toString());
                                ah.setGender(Gender.getStatus(acVec.get(3).toString()));
                                ah.setUid(acVec.get(4).toString());
                                ah.setMobile(acVec.get(5).toString());

                                ah.setPinCode(acVec.get(6).toString());
                                ah.setAdd1(acVec.get(7).toString());
                                ah.setAdd2(acVec.get(8).toString());

                                ah.setDistrict(acVec.get(9).toString().equalsIgnoreCase("") ? "" : refDesc("001", acVec.get(9).toString()));
                                ah.setState(acVec.get(10).toString().equalsIgnoreCase("") ? "" : refDesc("002", acVec.get(10).toString()));

                                ah.setPan(acVec.get(11).toString());
                                ah.setTan(acVec.get(17).toString());
                                ahDtlList.add(ah);
                                if (!acVec.get(13).toString().equalsIgnoreCase("1")) {
                                    List jtDetailLsit = getJtDetails(ele.get(1).toString());
                                    for (int l = 0; l < jtDetailLsit.size(); l++) {
                                        Vector jtVec = (Vector) jtDetailLsit.get(l);
                                        ah = new AH();
                                        ah.setName(jtVec.get(0).toString());
                                        ah.setsName(jtVec.get(1).toString());
                                        ah.setGender(Gender.getStatus(jtVec.get(2).toString()));
                                        ah.setUid(jtVec.get(3).toString());
                                        ah.setMobile(jtVec.get(4).toString());
                                        ah.setPinCode(jtVec.get(5).toString());
                                        ah.setAdd1(jtVec.get(6).toString());
                                        ah.setAdd2(jtVec.get(7).toString());
                                        ah.setDistrict(jtVec.get(8).toString().equalsIgnoreCase("") ? "" : refDesc("001", jtVec.get(8).toString()));
                                        ah.setState(jtVec.get(9).toString().equalsIgnoreCase("") ? "" : refDesc("002", jtVec.get(9).toString()));

                                        ah.setPan(jtVec.get(10).toString());
                                        ah.setTan(jtVec.get(11).toString());
                                        ahDtlList.add(ah);
                                    }
                                }
                                ahDtl.setAhDtl(ahDtlList);
                                acResMsg.setAhDetails(ahDtl);
                            }
                            int valRes = em.createNativeQuery("update cpsms_acc_detail set valStatus='V' where txnid='" + txid + "'").executeUpdate();
                            if (valRes <= 0) {
                                ut.rollback();
                                throw new Exception("Problem in cpsms_acc_detail data updation !!");
                            }
                        }
                        aclist.add(acResMsg);
                        msgRes.setAccountList(aclist);

                        int res = em.createNativeQuery("update cpsms_acc_detail set reqStatus='QR' where txnid='" + txid + "'").executeUpdate();
                        if (res <= 0) {
                            ut.rollback();
                            throw new Exception("Problem in cpsms_acc_detail data updation !!");
                        }
                    }

                    for (HoReconsilePojo disVec : msgReqMap) {
                        String reqMsgIdInsrt = disVec.getBaseBranch();
                        String refFile = disVec.getRespondingBranch();
                        String entDate = disVec.getTranDt();

                        int result = em.createNativeQuery("INSERT INTO cpsms_request_response_mapping (RequestType,"
                                + "MessageId,RequestFileName,ResMessageId,ResponseFileName,ReqFileDate,ResFileDate,processDate,"
                                + "seqNo) VALUES ('AV', '" + reqMsgIdInsrt + "', '" + refFile + "', "
                                + "'" + bnVec.get(0).toString() + "AVQRES" + sdf.format(new Date()) + nextSeqNo + "', "
                                + "'" + bnVec.get(0).toString() + "AVQRES" + sdf.format(new Date()) + nextSeqNo + "', "
                                + "'" + entDate + "', now(), now(), " + nextSeqNo + ")").executeUpdate();
                        if (result <= 0) {
                            ut.rollback();
                            throw new ApplicationException("Problem In Table Insertion");
                        }
                    }

                    File file = new File(props.getProperty("acLocalValBkpResLocation") + resFileName);
                    JAXBContext jaxbContext = JAXBContext.newInstance(MsgRes.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(msgRes, file);

                    SftpUtil util = SftpUtilFactory.getSftpUtil();
                    SftpSession sftpSession = getSftpSession();

                    util.put(sftpSession, props.getProperty("acLocalValBkpResLocation") + resFileName, props.getProperty("acSftpValidateResLocation"));
                    util.disconnect(sftpSession);

                    ut.commit();
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public void accValRegularResponse() throws Exception {
        MsgRes msgRes = new MsgRes();
        List<HoReconsilePojo> msgReqMap = new ArrayList<HoReconsilePojo>();
        try {
            UserTransaction ut = context.getUserTransaction();
            List acValQuickResList = em.createNativeQuery("select cd.MessageId, cd.Accountno, cd.Bsrcode, cd.entitycode,"
                    + "cd.accounttype, cd.statecode, cd.pan, ch.bankcode,cd.txnid,ch.RequestFileName,ch.entryDate from cpsms_acc_detail cd, cpsms_acc_header ch where "
                    + "cd.reqStatus ='QR' and cd.MessageId = ch.MessageId").getResultList();
            if (!acValQuickResList.isEmpty()) {
                ut.begin();
                List bnkCodeList = em.createNativeQuery("select Distinct bankCode,bankname from cpsms_acc_header").getResultList();
                if (!bnkCodeList.isEmpty()) {
                    Vector bnVec = (Vector) bnkCodeList.get(0);

                    String nextSeqNo = getMaxSeqNo("AV", ymd.format(new Date()));
                    String resFileName = bnVec.get(0).toString() + "AVRES" + sdf.format(new Date()) + nextSeqNo + ".xml";

                    msgRes.setMessageId(bnVec.get(0).toString() + "AVRES" + sdf.format(new Date()) + nextSeqNo);
                    msgRes.setSource(bnVec.get(0).toString());
                    msgRes.setDestination("CPSMS");
                    msgRes.setBankCode(bnVec.get(0).toString());
                    msgRes.setBankName(bnVec.get(1).toString());
                    msgRes.setRecordsCount(acValQuickResList.size());
                    msgRes.setXmlns("http://cpsms.com/AccountValidationRegResponse");

                    AHDetails ahDtl;
                    AH ah;
                    List<AcctResDetail> aclist = new ArrayList<AcctResDetail>();
                    for (int i = 0; i < acValQuickResList.size(); i++) {
                        Vector ele = (Vector) acValQuickResList.get(i);
                        String reqMId = ele.get(0).toString();
                        String bsrCd = ele.get(2).toString();
                        String entCode = ele.get(3).toString();
                        String txid = ele.get(8).toString();

                        int index = getIndex(msgReqMap, reqMId);
                        if (index < 0) {
                            HoReconsilePojo msgPojo = new HoReconsilePojo();
                            msgPojo.setBaseBranch(reqMId);
                            msgPojo.setRespondingBranch(ele.get(9).toString());
                            msgPojo.setTranDt(ele.get(10).toString());
                            msgReqMap.add(msgPojo);
                        }

                        AcctResDetail acResMsg = new AcctResDetail();
                        List<AH> ahDtlList = new ArrayList<AH>();
                        List acDetail = getAccountDetail(ele.get(1).toString());

                        acResMsg.setReqMsgId(reqMId);
                        acResMsg.setAccountNo(ele.get(1).toString());
                        if (acDetail.isEmpty()) {
                            acResMsg.setAcctValidity("I");
                            acResMsg.setAcctStatus("");
                            acResMsg.setAccountType("");
                            acResMsg.setBsrCode(bsrCd);
                            acResMsg.setIfscCode("");
                            acResMsg.setEntityCode(entCode);
                            acResMsg.setMicrCode("");
                            acResMsg.setAccountOpenDate("");
                            acResMsg.setAccountCloseDate("");
                            acResMsg.setAccountCategory("");

                            acResMsg.setMasterAccNo("");
                            acResMsg.setMajorHead("");
                            acResMsg.setMinorHead("");
                            acResMsg.setPao_ddoCode("");
                            acResMsg.setTreasuryCode("");

                            acResMsg.setSmsEnabled("");
                            acResMsg.setActiveChannels("");
                            acResMsg.setFiAccount("");
                            acResMsg.setZeroBalanceAccount("");
                            acResMsg.setDailyCL("");
                            acResMsg.setPeriodicCL("");
                            acResMsg.setPeriodicCLUnit("");
                            acResMsg.setRegSanctionedCL("");
                            acResMsg.setRegSanctionedCLExpiryDate("");
                            acResMsg.setActiveSweep("");
                            acResMsg.setConnectedSweepAccount("");

                            ahDtl = new AHDetails();
                            ah = new AH();
                            ahDtlList.add(ah);

                            ahDtl.setAhDtl(ahDtlList);
                            acResMsg.setAhDetails(ahDtl);
                        } else {
                            for (int k = 0; k < acDetail.size(); k++) {
                                Vector acVec = (Vector) acDetail.get(k);
                                acResMsg.setAcctValidity("V");
                                acResMsg.setAcctStatus(AccStatus.getStatus(Integer.parseInt(acVec.get(12).toString())));
                                acResMsg.setAccountType(acVec.get(16).toString());
                                acResMsg.setBsrCode(bsrCd);
                                acResMsg.setIfscCode(acVec.get(0).toString());
                                acResMsg.setEntityCode(entCode);
                                acResMsg.setMicrCode(acVec.get(18).toString());
                                acResMsg.setAccountOpenDate(acVec.get(19).toString());
                                acResMsg.setAccountCloseDate(acVec.get(20).toString());
                                acResMsg.setAccountCategory(acVec.get(15).toString());
                                acResMsg.setMasterAccNo("");
                                acResMsg.setMajorHead("");
                                acResMsg.setMinorHead("");
                                acResMsg.setPao_ddoCode("");
                                acResMsg.setTreasuryCode("");

                                acResMsg.setSmsEnabled(smsEnable(ele.get(1).toString()));
                                acResMsg.setActiveChannels(acVec.get(21).toString().equalsIgnoreCase("1") ? "C" : "");
                                acResMsg.setFiAccount("N");
                                acResMsg.setZeroBalanceAccount("N");
                                acResMsg.setDailyCL("0");
                                acResMsg.setPeriodicCL("");
                                acResMsg.setPeriodicCLUnit("");
                                acResMsg.setRegSanctionedCL("0");
                                acResMsg.setRegSanctionedCLExpiryDate("");
                                acResMsg.setActiveSweep("N");
                                acResMsg.setConnectedSweepAccount("");

                                ahDtl = new AHDetails();
                                ah = new AH();
                                ah.setName(acVec.get(1).toString());
                                ah.setsName(acVec.get(2).toString());
                                ah.setGender(Gender.getStatus(acVec.get(3).toString()));
                                ah.setUid(acVec.get(4).toString());
                                ah.setMobile(acVec.get(5).toString());

                                ah.setAdd1(acVec.get(7).toString());
                                ah.setAdd2(acVec.get(8).toString());
                                ah.setDistrict(acVec.get(9).toString().equalsIgnoreCase("") ? "" : refDesc("001", acVec.get(9).toString()));
                                ah.setState(acVec.get(10).toString().equalsIgnoreCase("") ? "" : refDesc("002", acVec.get(10).toString()));

                                ah.setPinCode(acVec.get(6).toString());
                                ah.setPan(acVec.get(11).toString());
                                ah.setTan(acVec.get(17).toString());

                                ahDtlList.add(ah);
                                if (!acVec.get(13).toString().equalsIgnoreCase("1")) {
                                    List jtDetailLsit = getJtDetails(ele.get(1).toString());
                                    for (int l = 0; l < jtDetailLsit.size(); l++) {
                                        Vector jtVec = (Vector) jtDetailLsit.get(l);
                                        ah = new AH();
                                        ah.setName(jtVec.get(0).toString());
                                        ah.setsName(jtVec.get(1).toString());
                                        ah.setGender(Gender.getStatus(jtVec.get(2).toString()));
                                        ah.setUid(jtVec.get(3).toString());
                                        ah.setMobile(jtVec.get(4).toString());
                                        ah.setPinCode(jtVec.get(5).toString());
                                        ah.setAdd1(jtVec.get(6).toString());
                                        ah.setAdd2(jtVec.get(7).toString());
                                        ah.setDistrict(jtVec.get(8).toString().equalsIgnoreCase("") ? "" : refDesc("001", jtVec.get(8).toString()));
                                        ah.setState(jtVec.get(9).toString().equalsIgnoreCase("") ? "" : refDesc("002", jtVec.get(9).toString()));
                                        ah.setPan(jtVec.get(10).toString());
                                        ah.setTan(jtVec.get(11).toString());
                                        ahDtlList.add(ah);
                                    }
                                }
                                ahDtl.setAhDtl(ahDtlList);
                                acResMsg.setAhDetails(ahDtl);
                            }
                        }
                        aclist.add(acResMsg);
                        msgRes.setAccountList(aclist);

                        int res = em.createNativeQuery("update cpsms_acc_detail set reqStatus='RR' where txnid='" + txid + "'").executeUpdate();
                        if (res <= 0) {
                            ut.rollback();
                            throw new Exception("Problem in data updation in cpsms_acc_detail !!");
                        }
                    }

                    for (HoReconsilePojo disVec : msgReqMap) {
                        String reqMsgIdInsrt = disVec.getBaseBranch();
                        String refFile = disVec.getRespondingBranch();
                        String entDate = disVec.getTranDt();

                        int result = em.createNativeQuery("INSERT INTO cpsms_request_response_mapping (RequestType,"
                                + "MessageId,RequestFileName,ResMessageId,ResponseFileName,ReqFileDate,ResFileDate,processDate,"
                                + "seqNo) VALUES ('AV', '" + reqMsgIdInsrt + "', '" + refFile + "', "
                                + "'" + bnVec.get(0).toString() + "AVRES" + sdf.format(new Date()) + nextSeqNo + "', "
                                + "'" + bnVec.get(0).toString() + "AVRES" + sdf.format(new Date()) + nextSeqNo + "', "
                                + "'" + entDate + "', now(), now(), " + nextSeqNo + ")").executeUpdate();
                        if (result <= 0) {
                            ut.rollback();
                            throw new ApplicationException("Problem In Table Insertion");
                        }
                    }

                    File file = new File(props.getProperty("acLocalValBkpResLocation") + resFileName);
                    JAXBContext jaxbContext = JAXBContext.newInstance(MsgRes.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(msgRes, file);

                    SftpUtil util = SftpUtilFactory.getSftpUtil();
                    SftpSession sftpSession = getSftpSession();

                    util.put(sftpSession, props.getProperty("acLocalValBkpResLocation") + resFileName, props.getProperty("acSftpValidateResLocation"));
                    util.disconnect(sftpSession);

                    ut.commit();
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public void generateDailyTransactionDetail() throws Exception {
        TransactionDetails txnDetails = new TransactionDetails();
        NumberFormat df = new DecimalFormat("0.00");
        UserTransaction ut = context.getUserTransaction();
        try {
            Date curDt = new Date();
            List tranDailyList = em.createNativeQuery("select a.accno,a.pdt,a.rflg,a.bsrcd,a.bnkcd,a.bnkN,a.reqSt,a.valSt,a.trReq from (select cp.ano as "
                    + " accno,cp.dt as pdt,cr.datarequired as rflg,cr.BsrCode as bsrCd,ch.bankcode as bnkCd,ch.bankname as bnkN, "
                    + " cr.ReqStatus as reqSt, cr.valStatus as valSt, cr.tranReq as trReq from cpsms_acc_detail cr, (select distinct accountno as ano,"
                    + " max(entrydate) as dt,datarequired from cpsms_acc_detail group by accountno) cp, cpsms_acc_header ch where "
                    + " cp.ano = cr.accountno and cp.dt = cr.entrydate and cr.messageid = ch.messageid) a where a.rflg = 'Y' "
                    + " and a.reqst ='RR' and a.valst ='V' and a.trReq ='D'").getResultList();
            if (!tranDailyList.isEmpty()) {
                ut.begin();
                String nextSeqNo = getMaxSeqNo("TRN", ymd.format(new Date()));
                String bnkCode = "", bnkName = "";

                List<Account> accounts = txnDetails.getAccount();
                for (int i = 0; i < tranDailyList.size(); i++) {
                    Account account = new Account();
                    Vector tranVec = (Vector) tranDailyList.get(i);
                    String accTranNo = tranVec.get(0).toString();
                    String bsrCode = tranVec.get(3).toString();
                    bnkCode = tranVec.get(4).toString();
                    bnkName = tranVec.get(5).toString();
                    String finOpDate = "";
                    List tranExistList = em.createNativeQuery("select accountno,date_format(max(sendDate),'%Y%m%d') from cpsms_transaction_dispatch_detail "
                            + " where accountno ='" + accTranNo + "' and msgstatus ='S' group by accountno").getResultList();
                    if (!tranExistList.isEmpty()) {
                        Vector tranExVec = (Vector) tranExistList.get(0);
                        finOpDate = tranExVec.get(1).toString();
                        finOpDate = CbsUtil.dateAdd(finOpDate, 1);
                    }

                    String openBal = String.valueOf(commonReport.getBalanceOnDate(accTranNo, finOpDate));
                    String closeBal = String.valueOf(commonReport.getBalanceOnDate(accTranNo, ymd.format(curDt)));
                    String shadowClBal;
                    List shadowClList = selectPendingBalance(accTranNo);
                    Vector vecForShadowClBal = (Vector) shadowClList.get(0);
                    if (vecForShadowClBal.get(0).toString().equalsIgnoreCase("0.0") || vecForShadowClBal.get(0).toString().equalsIgnoreCase("0")) {
                        shadowClBal = "0";
                    } else {
                        shadowClBal = df.format(Double.parseDouble(vecForShadowClBal.get(0).toString()));
                    }

                    String micrCd = "";
                    List acMicr = getAccountDetail(accTranNo);
                    if (!acMicr.isEmpty()) {
                        for (int j = 0; j < acMicr.size(); j++) {
                            Vector mVec = (Vector) acMicr.get(j);
                            micrCd = mVec.get(18).toString();
                        }
                    }

                    List acDetail = getAccountTxnDetail(accTranNo, finOpDate, ymd.format(curDt));
                    if (!acDetail.isEmpty()) {
                        String postTrnBal = openBal;

                        for (int k = 0; k < acDetail.size(); k++) {
                            Vector txnVec = (Vector) acDetail.get(k);
                            String txnTrnTime = txnVec.get(0).toString();
                            String txnValDate = txnVec.get(1).toString();
                            String txnTrnType = txnVec.get(2).toString();
                            String txnTrnDesc = txnVec.get(3).toString();
                            String txnTranType = txnVec.get(4).toString();
                            String txnInstNo = txnVec.get(5).toString();
                            String txnInstDate = txnVec.get(6).toString();
                            String txnAmount = txnVec.get(7).toString();
                            String txnDetail = txnVec.get(8).toString();
                            String txnRecNo = txnVec.get(9).toString();

                            new ObjectFactory().createTransactionDetailsAccountTransaction();

                            List<TransactionDetails.Account.Transaction> transactions = account.getTransaction();
                            TransactionDetails.Account.Transaction txn = new TransactionDetails.Account.Transaction();

                            JAXBElement<String> trDate = new ObjectFactory().createTransactionDetailsAccountTransactionDate(txnTrnTime);
                            JAXBElement<String> trValDate = new ObjectFactory().createTransactionDetailsAccountTransactionValueDate(txnValDate);
                            JAXBElement<String> trTxnType = new ObjectFactory().createTransactionDetailsAccountTransactionTransactionType(txnTrnType);

                            String txnCat = "";
                            if (txnTranType.equalsIgnoreCase("8")) {
                                txnCat = "IC";
                            } else if (txnTranType.equalsIgnoreCase("2") && txnTrnDesc.equalsIgnoreCase("33")) {
                                txnCat = "BC";
                            }

                            JAXBElement<String> trTxnCat = new ObjectFactory().createTransactionDetailsAccountTransactionTransactionCategory(txnCat);

                            String txnChType = "O";
                            if (txnTranType.equalsIgnoreCase("1")) {
                                txnChType = "C";
                            } else if (txnTranType.equalsIgnoreCase("2") && txnTrnDesc.equalsIgnoreCase("70")) {
                                txnChType = "A";
                            } else if ((txnTranType.equalsIgnoreCase("1") || txnTranType.equalsIgnoreCase("2"))
                                    && txnDetail.contains("ECS")) {
                                txnChType = "E";
                            } else if (txnTranType.equalsIgnoreCase("2") && txnTrnDesc.equalsIgnoreCase("66")) {
                                if (Double.parseDouble(txnAmount) > 200000.0) {
                                    txnChType = "R";
                                } else {
                                    txnChType = "N";
                                }
                            } else if (txnTranType.equalsIgnoreCase("0")) {
                                txnChType = "K";
                            }

                            JAXBElement<String> trChanType = new ObjectFactory().createTransactionDetailsAccountTransactionChannelType(txnChType);

                            JAXBElement<String> trInstNo = new ObjectFactory().createTransactionDetailsAccountTransactionInstrumentNo(txnInstNo);

                            if (txnInstDate.equalsIgnoreCase("01/01/1900")) {
                                txnInstDate = "";
                            }

                            JAXBElement<String> trInstDate = new ObjectFactory().createTransactionDetailsAccountTransactionInstrumentDate(txnInstDate);

                            JAXBElement<String> trAmt = new ObjectFactory().createTransactionDetailsAccountTransactionAmount(txnAmount);

                            if (txnTrnType.equalsIgnoreCase("Dr")) {
                                postTrnBal = String.valueOf(Double.parseDouble(postTrnBal) - Double.parseDouble(txnAmount));
                            } else {
                                postTrnBal = String.valueOf(Double.parseDouble(postTrnBal) + Double.parseDouble(txnAmount));
                            }

                            JAXBElement<String> trPostTxnBal = new ObjectFactory().createTransactionDetailsAccountTransactionPostTranBal(postTrnBal);
                            JAXBElement<String> trRemarks = new ObjectFactory().createTransactionDetailsAccountTransactionRemarks(txnDetail);

                            JAXBElement<String> trTxnRefNo = new ObjectFactory().createTransactionDetailsAccountTransactionTranRefNo("");

                            JAXBElement<String> trDrCrAccNo = new ObjectFactory().createTransactionDetailsAccountTransactionDrCrAccountNumber("");
                            JAXBElement<String> trDrCrAccName = new ObjectFactory().createTransactionDetailsAccountTransactionDrCrAccountName("");

                            JAXBElement<String> trUniTxnNo = new ObjectFactory().createTransactionDetailsAccountTransactionUniqueTransactionNumber(txnRecNo);

                            JAXBElement<String> trCpId = new ObjectFactory().createTransactionDetailsAccountTransactionCPSMSTransactionId("");
                            Narratives narratives = new Narratives();
                            List<Narration> narrations = narratives.getNarration();
                            Narration narration = new Narration();
                            narration.setSrNo(Integer.parseInt("1"));
                            narration.setText("");
                            narrations.add(narration);

                            Narration narration1 = new Narration();
                            narration1.setSrNo(Integer.parseInt("2"));
                            narration1.setText("");
                            narrations.add(narration1);

                            Narration narration2 = new Narration();
                            narration2.setSrNo(Integer.parseInt("3"));
                            narration2.setText("");
                            narrations.add(narration2);

                            narratives.getNarration();

                            JAXBElement<Narratives> s3 = new ObjectFactory().createTransactionDetailsAccountTransactionNarratives(narratives);

                            txn.getDateOrValueDateOrTransactionType().add(trDate);
                            txn.getDateOrValueDateOrTransactionType().add(trValDate);
                            txn.getDateOrValueDateOrTransactionType().add(trTxnType);
                            txn.getDateOrValueDateOrTransactionType().add(trTxnCat);
                            txn.getDateOrValueDateOrTransactionType().add(trChanType);
                            txn.getDateOrValueDateOrTransactionType().add(trInstNo);
                            txn.getDateOrValueDateOrTransactionType().add(trInstDate);
                            txn.getDateOrValueDateOrTransactionType().add(trAmt);
                            txn.getDateOrValueDateOrTransactionType().add(trPostTxnBal);
                            txn.getDateOrValueDateOrTransactionType().add(trRemarks);
                            txn.getDateOrValueDateOrTransactionType().add(trDrCrAccNo);
                            txn.getDateOrValueDateOrTransactionType().add(trDrCrAccName);
                            txn.getDateOrValueDateOrTransactionType().add(trUniTxnNo);
                            txn.getDateOrValueDateOrTransactionType().add(trTxnRefNo);
                            txn.getDateOrValueDateOrTransactionType().add(trCpId);
                            txn.getDateOrValueDateOrTransactionType().add(s3);

                            transactions.add(txn);
                        }
                    }
                    account.setAccountNo(accTranNo);
                    account.setBSRCode(bsrCode);
                    account.setMICRCode(micrCd);
                    account.setOpeningBalance(openBal);
                    account.setShadowOpeningBalance(shadowClBal);
                    account.setClosingBalance(closeBal);
                    account.setShadowClosingBalance(shadowClBal);

                    if (finOpDate.equalsIgnoreCase("")) {
                        account.setOpeningBalanceDate("");
                    } else {
                        account.setOpeningBalanceDate(dmy.format(ymd.parse(finOpDate)));
                    }

                    account.setClosingBalanceDate(dmy.format(curDt));
                    account.setTransactionsCount(acDetail.size());
                    accounts.add(account);

                    int result = em.createNativeQuery("INSERT INTO cpsms_transaction_dispatch_detail (MessageId,AccountNo,BsrCode,"
                            + "BatchType,MsgStatus,sendDate,EntryDate,EntryTime,EnterBy) VALUES ("
                            + " '" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "', '" + accTranNo + "', "
                            + "'" + bsrCode + "','D','D','" + ymd.format(new Date()) + "', now(), now(), 'SYSTEM')").executeUpdate();
                    if (result <= 0) {
                        throw new ApplicationException("Problem In Table Insertion");
                    }
                }

                String resFileName = bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + ".xml";

                int resultHd = em.createNativeQuery("INSERT INTO cpsms_transaction_dispatch_header (MessageId,resAckMsgId,Source,Destination,BankCode,"
                        + "BankName,RecordsCount,EntryDate,EntryTime,EnterBy) VALUES ('" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "','', "
                        + " '" + bnkCode + "', 'CPSMS', '" + bnkCode + "', '" + bnkName + "'," + tranDailyList.size() + ", now(), now(), 'SYSTEM')").executeUpdate();
                if (resultHd <= 0) {
                    throw new ApplicationException("Problem In Table Insertion");
                }

                int result = em.createNativeQuery("INSERT INTO cpsms_request_response_mapping (RequestType,"
                        + "MessageId,RequestFileName,ResMessageId,ResponseFileName,ReqFileDate,ResFileDate,processDate,"
                        + "seqNo) VALUES ('TRN', '" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "', '" + resFileName + "', "
                        + "'" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "', "
                        + "'" + resFileName + "', "
                        + "'" + ymd.format(new Date()) + "', now(), now(), " + nextSeqNo + ")").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Insertion");
                }

                txnDetails.setMessageId(bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo);
                txnDetails.setSource(bnkCode);
                txnDetails.setDestination("CPSMS");
                txnDetails.setBankCode(bnkCode);
                txnDetails.setBankName(bnkName);
                txnDetails.setRecordsCount(tranDailyList.size());
                txnDetails.getAccount();

                File file = new File(props.getProperty("txnDetailLocalLocation") + resFileName);
                JAXBContext jaxbContext = JAXBContext.newInstance(TransactionDetails.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(txnDetails, file);

                SftpUtil util = SftpUtilFactory.getSftpUtil();
                SftpSession sftpSession = getSftpSession();

                util.put(sftpSession, props.getProperty("txnDetailLocalLocation") + resFileName, props.getProperty("txnDetailLocation"));
                util.disconnect(sftpSession);
                ut.commit();
            }
        } catch (Exception ex) {
            ut.rollback();
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public void generateTransactionHistoryDetail() throws Exception {
        TransactionDetails txnDetails = new TransactionDetails();
        NumberFormat df = new DecimalFormat("0.00");
        UserTransaction ut = context.getUserTransaction();
        try {
            Date curDt = new Date();
            List tranDailyList = em.createNativeQuery("select a.accno,a.pdt,a.rflg,a.bsrcd,a.bnkcd,a.bnkN,a.reqSt,a.valSt,a.trReq,am.openingdt from (select cp.ano as "
                    + " accno,cp.dt as pdt,cr.datarequired as rflg,cr.BsrCode as bsrCd,ch.bankcode as bnkCd,ch.bankname as bnkN, "
                    + " cr.ReqStatus as reqSt, cr.valStatus as valSt, cr.tranReq as trReq from cpsms_acc_detail cr, (select distinct accountno as ano,"
                    + " max(entrydate) as dt,datarequired from cpsms_acc_detail group by accountno) cp, cpsms_acc_header ch where "
                    + " cp.ano = cr.accountno and cp.dt = cr.entrydate and cr.messageid = ch.messageid) a, accountmaster am where a.accno = am.acno and a.rflg = 'Y' "
                    + " and a.reqst ='RR' and a.valst ='V' and a.trReq ='H'").getResultList();
            if (!tranDailyList.isEmpty()) {
                ut.begin();
                String nextSeqNo = getMaxSeqNo("TRN", ymd.format(new Date()));
                String bnkCode = "", bnkName = "", finOpDate = "";

                List<Account> accounts = txnDetails.getAccount();
                for (int i = 0; i < tranDailyList.size(); i++) {

                    finOpDate = ftsRemote.getCodeFromCbsParameterInfo("CPSMS_TRN_HIS_DATE");

                    Account account = new Account();
                    Vector tranVec = (Vector) tranDailyList.get(i);
                    String accTranNo = tranVec.get(0).toString();
                    String bsrCode = tranVec.get(3).toString();
                    bnkCode = tranVec.get(4).toString();
                    bnkName = tranVec.get(5).toString();

                    if (ymd.parse(finOpDate).compareTo(ymd.parse(tranVec.get(9).toString())) > 0) {
                    } else {
                        finOpDate = tranVec.get(9).toString();
                    }

                    List tranExistList = em.createNativeQuery("select accountno,max(sendDate) from cpsms_transaction_dispatch_detail "
                            + " where accountno ='" + accTranNo + "' and msgstatus ='S' group by accountno").getResultList();
                    if (!tranExistList.isEmpty()) {
                        Vector tranExVec = (Vector) tranExistList.get(0);
                        finOpDate = tranExVec.get(1).toString();
                        finOpDate = CbsUtil.dateAdd(finOpDate, 1);
                    }

                    String openBal = String.valueOf(commonReport.getBalanceOnDate(accTranNo, finOpDate));
                    String closeBal = String.valueOf(commonReport.getBalanceOnDate(accTranNo, ymd.format(curDt)));
                    String shadowClBal;
                    List shadowClList = selectPendingBalance(accTranNo);
                    Vector vecForShadowClBal = (Vector) shadowClList.get(0);
                    if (vecForShadowClBal.get(0).toString().equalsIgnoreCase("0.0") || vecForShadowClBal.get(0).toString().equalsIgnoreCase("0")) {
                        shadowClBal = "0";
                    } else {
                        shadowClBal = df.format(Double.parseDouble(vecForShadowClBal.get(0).toString()));
                    }

                    String micrCd = "";
                    List acMicr = getAccountDetail(accTranNo);
                    if (!acMicr.isEmpty()) {
                        for (int j = 0; j < acMicr.size(); j++) {
                            Vector mVec = (Vector) acMicr.get(j);
                            micrCd = mVec.get(18).toString();
                        }
                    }

                    List acDetail = getAccountTxnDetail(accTranNo, finOpDate, ymd.format(curDt));
                    if (!acDetail.isEmpty()) {
                        String postTrnBal = openBal;

                        for (int k = 0; k < acDetail.size(); k++) {
                            Vector txnVec = (Vector) acDetail.get(k);
                            String txnTrnTime = txnVec.get(0).toString();
                            String txnValDate = txnVec.get(1).toString();
                            String txnTrnType = txnVec.get(2).toString();
                            String txnTrnDesc = txnVec.get(3).toString();
                            String txnTranType = txnVec.get(4).toString();
                            String txnInstNo = txnVec.get(5).toString();
                            String txnInstDate = txnVec.get(6).toString();
                            String txnAmount = txnVec.get(7).toString();
                            String txnDetail = txnVec.get(8).toString();
                            String txnRecNo = txnVec.get(9).toString();

                            new ObjectFactory().createTransactionDetailsAccountTransaction();

                            List<TransactionDetails.Account.Transaction> transactions = account.getTransaction();
                            TransactionDetails.Account.Transaction txn = new TransactionDetails.Account.Transaction();

                            JAXBElement<String> trDate = new ObjectFactory().createTransactionDetailsAccountTransactionDate(txnTrnTime);
                            JAXBElement<String> trValDate = new ObjectFactory().createTransactionDetailsAccountTransactionValueDate(txnValDate);
                            JAXBElement<String> trTxnType = new ObjectFactory().createTransactionDetailsAccountTransactionTransactionType(txnTrnType);

                            String txnCat = "";
                            if (txnTranType.equalsIgnoreCase("8")) {
                                txnCat = "IC";
                            } else if (txnTranType.equalsIgnoreCase("2") && txnTrnDesc.equalsIgnoreCase("33")) {
                                txnCat = "BC";
                            }

                            JAXBElement<String> trTxnCat = new ObjectFactory().createTransactionDetailsAccountTransactionTransactionCategory(txnCat);

                            String txnChType = "O";
                            if (txnTranType.equalsIgnoreCase("1")) {
                                txnChType = "C";
                            } else if (txnTranType.equalsIgnoreCase("2") && txnTrnDesc.equalsIgnoreCase("70")) {
                                txnChType = "A";
                            } else if ((txnTranType.equalsIgnoreCase("1") || txnTranType.equalsIgnoreCase("2"))
                                    && txnDetail.contains("ECS")) {
                                txnChType = "E";
                            } else if (txnTranType.equalsIgnoreCase("2") && txnTrnDesc.equalsIgnoreCase("66")) {
                                if (Double.parseDouble(txnAmount) > 200000.0) {
                                    txnChType = "R";
                                } else {
                                    txnChType = "N";
                                }
                            } else if (txnTranType.equalsIgnoreCase("0")) {
                                txnChType = "K";
                            }

                            JAXBElement<String> trChanType = new ObjectFactory().createTransactionDetailsAccountTransactionChannelType(txnChType);

                            JAXBElement<String> trInstNo = new ObjectFactory().createTransactionDetailsAccountTransactionInstrumentNo(txnInstNo);

                            if (txnInstDate.equalsIgnoreCase("01/01/1900")) {
                                txnInstDate = "";
                            }

                            JAXBElement<String> trInstDate = new ObjectFactory().createTransactionDetailsAccountTransactionInstrumentDate(txnInstDate);

                            JAXBElement<String> trAmt = new ObjectFactory().createTransactionDetailsAccountTransactionAmount(txnAmount);

                            if (txnTrnType.equalsIgnoreCase("Dr")) {
                                postTrnBal = String.valueOf(Double.parseDouble(postTrnBal) - Double.parseDouble(txnAmount));
                            } else {
                                postTrnBal = String.valueOf(Double.parseDouble(postTrnBal) + Double.parseDouble(txnAmount));
                            }

                            JAXBElement<String> trPostTxnBal = new ObjectFactory().createTransactionDetailsAccountTransactionPostTranBal(postTrnBal);
                            JAXBElement<String> trRemarks = new ObjectFactory().createTransactionDetailsAccountTransactionRemarks(txnDetail);

                            JAXBElement<String> trTxnRefNo = new ObjectFactory().createTransactionDetailsAccountTransactionTranRefNo("");

                            JAXBElement<String> trDrCrAccNo = new ObjectFactory().createTransactionDetailsAccountTransactionDrCrAccountNumber("");
                            JAXBElement<String> trDrCrAccName = new ObjectFactory().createTransactionDetailsAccountTransactionDrCrAccountName("");

                            JAXBElement<String> trUniTxnNo = new ObjectFactory().createTransactionDetailsAccountTransactionUniqueTransactionNumber(txnRecNo);

                            JAXBElement<String> trCpId = new ObjectFactory().createTransactionDetailsAccountTransactionCPSMSTransactionId("");
                            Narratives narratives = new Narratives();
                            List<Narration> narrations = narratives.getNarration();
                            Narration narration = new Narration();
                            narration.setSrNo(Integer.parseInt("1"));
                            narration.setText("");
                            narrations.add(narration);

                            Narration narration1 = new Narration();
                            narration1.setSrNo(Integer.parseInt("2"));
                            narration1.setText("");
                            narrations.add(narration1);

                            Narration narration2 = new Narration();
                            narration2.setSrNo(Integer.parseInt("3"));
                            narration2.setText("");
                            narrations.add(narration2);

                            narratives.getNarration();

                            JAXBElement<Narratives> s3 = new ObjectFactory().createTransactionDetailsAccountTransactionNarratives(narratives);

                            txn.getDateOrValueDateOrTransactionType().add(trDate);
                            txn.getDateOrValueDateOrTransactionType().add(trValDate);
                            txn.getDateOrValueDateOrTransactionType().add(trTxnType);
                            txn.getDateOrValueDateOrTransactionType().add(trTxnCat);
                            txn.getDateOrValueDateOrTransactionType().add(trChanType);
                            txn.getDateOrValueDateOrTransactionType().add(trInstNo);
                            txn.getDateOrValueDateOrTransactionType().add(trInstDate);
                            txn.getDateOrValueDateOrTransactionType().add(trAmt);
                            txn.getDateOrValueDateOrTransactionType().add(trPostTxnBal);
                            txn.getDateOrValueDateOrTransactionType().add(trRemarks);
                            txn.getDateOrValueDateOrTransactionType().add(trDrCrAccNo);
                            txn.getDateOrValueDateOrTransactionType().add(trDrCrAccName);
                            txn.getDateOrValueDateOrTransactionType().add(trUniTxnNo);
                            txn.getDateOrValueDateOrTransactionType().add(trTxnRefNo);
                            txn.getDateOrValueDateOrTransactionType().add(trCpId);
                            txn.getDateOrValueDateOrTransactionType().add(s3);

                            transactions.add(txn);
                        }
                    }
                    account.setAccountNo(accTranNo);
                    account.setBSRCode(bsrCode);
                    account.setMICRCode(micrCd);
                    account.setOpeningBalance(openBal);
                    account.setShadowOpeningBalance(shadowClBal);
                    account.setClosingBalance(closeBal);
                    account.setShadowClosingBalance(shadowClBal);
                    account.setOpeningBalanceDate(dmy.format(ymd.parse(finOpDate)));
                    account.setClosingBalanceDate(dmy.format(curDt));
                    account.setTransactionsCount(acDetail.size());
                    accounts.add(account);

                    int result = em.createNativeQuery("INSERT INTO cpsms_transaction_dispatch_detail (MessageId,AccountNo,BsrCode,"
                            + "BatchType,MsgStatus,sendDate,EntryDate,EntryTime,EnterBy) VALUES ("
                            + " '" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "', '" + accTranNo + "', "
                            + "'" + bsrCode + "','H','D','" + ymd.format(new Date()) + "', now(), now(), 'SYSTEM')").executeUpdate();
                    if (result <= 0) {
                        throw new ApplicationException("Problem In Table Insertion");
                    }
                }

                String resFileName = bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + ".xml";

                int resultHd = em.createNativeQuery("INSERT INTO cpsms_transaction_dispatch_header (MessageId,resAckMsgId,Source,Destination,BankCode,"
                        + "BankName,RecordsCount,EntryDate,EntryTime,EnterBy) VALUES ('" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "','', "
                        + " '" + bnkCode + "', 'CPSMS', '" + bnkCode + "', '" + bnkName + "'," + tranDailyList.size() + ", now(), now(), 'SYSTEM')").executeUpdate();
                if (resultHd <= 0) {
                    throw new ApplicationException("Problem In Table Insertion");
                }

                int result = em.createNativeQuery("INSERT INTO cpsms_request_response_mapping (RequestType,"
                        + "MessageId,RequestFileName,ResMessageId,ResponseFileName,ReqFileDate,ResFileDate,processDate,"
                        + "seqNo) VALUES ('TRN', '" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "', '" + resFileName + "', "
                        + "'" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "', "
                        + "'" + resFileName + "', "
                        + "'" + ymd.format(new Date()) + "', now(), now(), " + nextSeqNo + ")").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Insertion");
                }

                txnDetails.setMessageId(bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo);
                txnDetails.setSource(bnkCode);
                txnDetails.setDestination("CPSMS");
                txnDetails.setBankCode(bnkCode);
                txnDetails.setBankName(bnkName);
                txnDetails.setRecordsCount(tranDailyList.size());
                txnDetails.getAccount();

                File file = new File(props.getProperty("txnDetailHisLocalLocation") + resFileName);
                JAXBContext jaxbContext = JAXBContext.newInstance(TransactionDetails.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(txnDetails, file);

                SftpUtil util = SftpUtilFactory.getSftpUtil();
                SftpSession sftpSession = getSftpSession();

                util.put(sftpSession, props.getProperty("txnDetailHisLocalLocation") + resFileName, props.getProperty("txnDetailHisLocation"));
                util.disconnect(sftpSession);
                ut.commit();
            }
        } catch (Exception ex) {
            ut.rollback();
            throw new Exception(ex.getMessage());
        }
    }

    public int getIndex(List<HoReconsilePojo> list, String msgId) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getBaseBranch().equals(msgId)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public enum Gender {

        MALE("M", "M"), FEMALE("F", "F"), COMPANY("C", "O"), OTH("O", "O"), BLANK("", "O"),
        ZERO("0", "O"), TRANS("T", "T");
        private String id;
        private String value;

        private Gender(String value, String id) {
            this.value = value;
            this.id = id;
        }

        public String getValue() {
            return this.value;
        }

        public String getId() {
            return id;
        }

        public static String getStatus(String id) {
            for (Gender status : values()) {
                if (status.id.equalsIgnoreCase(id)) {
                    return status.value;
                }
            }
            return null;
        }
    }

    public enum AccStatus {

        ACTIVE("A", 1), INACTIVE("I", 2), SF("I", 3), FROZEN("I", 4), OP_ST("I", 5),
        DECREED("I", 6), DEBIT_FREEZE("DF", 7), FREEZE("I", 8), CLOSED("C", 9), LN_MARK("DF", 10),
        SUB("I", 11), DOUBT("I", 12), LOSS("I", 13), PROTESTED("I", 14), DEAF("I", 15);
        private int id;
        private String value;

        private AccStatus(String value, int id) {
            this.value = value;
            this.id = id;
        }

        public String getValue() {
            return this.value;
        }

        public int getId() {
            return id;
        }

        public static String getStatus(int id) {
            for (AccStatus status : values()) {
                if (status.id == id) {
                    return status.value;
                }
            }
            return null;
        }
    }

    public List getJtDetails(String acno) throws Exception {
        List jtList = new ArrayList();
        try {
            List rsList = em.createNativeQuery("select ifnull(custid1,''),ifnull(custid2,''),ifnull(custid3,''),ifnull(custid4,'') from "
                    + "accountmaster where acno='" + acno + "'").getResultList();
            if (!rsList.isEmpty()) {
                Vector jVec = (Vector) rsList.get(0);
                String selectedValues = "", cuId1, cuId2, cuId3, cuId4;
                cuId1 = jVec.get(0).toString();
                cuId2 = jVec.get(1).toString();
                cuId3 = jVec.get(2).toString();
                cuId4 = jVec.get(3).toString();

                if (!cuId1.equalsIgnoreCase("")) {
                    selectedValues = cuId1;
                }

                if (!cuId2.equalsIgnoreCase("")) {
                    selectedValues = selectedValues + "," + cuId2;
                }

                if (!cuId3.equalsIgnoreCase("")) {
                    selectedValues = selectedValues + "," + cuId3;
                }

                if (!cuId4.equalsIgnoreCase("")) {
                    selectedValues = selectedValues + "," + cuId4;
                }

                jtList = em.createNativeQuery("select trim(concat(cm.custname,ifnull(cm.middle_name,''),ifnull(last_name,''))) as cuname,ifnull(cm.shortname,''),ifnull(cm.gender,''),ifnull(cm.AADHAAR_NO,''),substring(cm.mobilenumber,1,10),"
                        + "cm.PerPostalCode,cm.peraddressline1,cm.peraddressline2,cm.percitycode,cm.perstatecode,cm.PAN_GIRNumber,ifnull(cm.tan,'') from cbs_customer_master_detail cm where "
                        + "cm.customerid  in (" + selectedValues + ")").getResultList();
            }
        } catch (Exception ex) {
            System.out.println("Error in getJtDetails ->>>>>" + ex.getMessage() + " " + acno);
            throw new Exception(ex.getMessage());
        }
        return jtList;
    }

    public List getAccountDetail(String acNo) throws Exception {
        List rsList = new ArrayList();
        try {
            rsList = em.createNativeQuery("select bm.ifsc_code,cm.custname,ifnull(cm.shortname,''),ifnull(cm.gender,''),ifnull(cm.AADHAAR_NO,''),"
                    + "substring(cm.mobilenumber,1,10),ifnull(cm.PerPostalCode,''),cm.peraddressline1,cm.peraddressline2,ifnull(cm.percitycode,''),ifnull(cm.perstatecode,''),"
                    + "cm.PAN_GIRNumber,ac.accstatus, ac.opermode,ac.acno,ac.acctCategory,am.pfms_actype,ifnull(cm.tan,''),"
                    + "concat(LPAD(ifnull(cast(b.micr as char),'0'),3,'0'),LPAD(ifnull(cast(b.micrcode as char),'0'),3,'0'),"
                    + "LPAD(ifnull(cast(b.branchcode as char),'0'),3,'0')),date_format(ac.openingdt,'%d/%m/%Y'),ifnull(date_format(ac.closingdate,'%d/%m/%Y'),''),ifnull(ac.chequebook,'') from branchmaster bm,accountmaster ac, "
                    + "cbs_customer_master_detail cm, customerid ci, accounttypemaster am,bnkadd b where ac.acno = ci.acno and ci.custid = cm.customerid and "
                    + "cast(ac.CurBrCode as unsigned) = bm.brncode and ac.accttype = am.acctcode and bm.alphacode = b.alphacode and ac.acno ='" + acNo + "'").getResultList();
        } catch (Exception ex) {
            System.out.println("Error in getAccountDetail ->>>>>" + ex.getMessage());
            throw new Exception(ex.getMessage());
        }
        return rsList;
    }

    public List getAccountTxnDetail(String acNo, String frDt, String toDt) throws Exception {
        List txnList = new ArrayList();
        try {
            String actNature = ftsRemote.getAccountNature(acNo);
            String tableName = commonReport.getTableName(actNature);
            txnList = em.createNativeQuery("select date_format(trantime,'%d/%m/%Y %H:%i:%S'),date_format(valuedt,'%d/%m/%Y'),case ty when 1 then 'Dr' else 'Cr' end as ty,trandesc,"
                    + " trantype,ifnull(instno,''),ifnull(date_format(instdt,'%d/%m/%Y'),''),case ty when 1 then dramt else cramt end as amount,ifnull(details,''),"
                    + " recno from " + tableName + " where acno = '" + acNo + "' and dt between '" + frDt + "' and '" + toDt + "'").getResultList();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return txnList;
    }

    public String getMaxSeqNo(String requestType, String curDt) throws Exception {
        String nextSeqNo = "";
        try {
            List list = em.createNativeQuery("select ifnull(max(seqno),0)+1 from cpsms_request_response_mapping "
                    + "where RequestType= '" + requestType + "' and processDate ='" + curDt + "'").getResultList();
            if (!list.isEmpty()) {
                Vector mVec = (Vector) list.get(0);
                nextSeqNo = mVec.get(0).toString();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return nextSeqNo;
    }

    public String smsEnable(String acno) throws ApplicationException {
        String smsStatus = "N";
        try {
            List mobileList = em.createNativeQuery("select mobile_no from mb_subscriber_tab "
                    + "where acno='" + acno + "'").getResultList();
            if (!mobileList.isEmpty()) {
                smsStatus = "Y";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return smsStatus;
    }

    public String refDesc(String refNo, String refCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select REF_DESC from cbs_ref_rec_type where REF_REC_NO = '" + refNo + "' and REF_CODE = '" + refCode + "'").getResultList();
            Vector vtr = (Vector) list.get(0);
            String desc = vtr.get(0).toString();
            return desc;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectPendingBalance(String accNo) {
        List list = em.createNativeQuery("Select ifnull(sum(txninstamt),0) from clg_ow_shadowbal where acno='" + accNo + "'").getResultList();
        return list;
    }
}
