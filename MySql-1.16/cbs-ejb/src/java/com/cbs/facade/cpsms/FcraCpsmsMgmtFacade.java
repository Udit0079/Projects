/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cpsms;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.cpsms.CPSMSMgmtFacade.AccStatus;
import com.cbs.facade.cpsms.CPSMSMgmtFacade.Gender;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.HoReconsilePojo;
import com.cbs.pojo.cpsms.accountvalidationresponse.AH;
import com.cbs.pojo.cpsms.accountvalidationresponse.AHDetails;
import com.cbs.pojo.cpsms.accountvalidationresponse.AcctResDetail;
import com.cbs.pojo.cpsms.accountvalidationresponse.MsgRes;
import com.cbs.pojo.cpsms.transactionsdatarequest.ObjectFactory;
import com.cbs.pojo.cpsms.transactionsdatarequest.TransactionDetails;
import com.cbs.pojo.cpsms.transactionsdatarequest.TransactionDetails.Account;
import com.cbs.pojo.cpsms.transactionsdatarequest.TransactionDetails.Account.Transaction.Narratives;
import com.cbs.pojo.cpsms.transactionsdatarequest.TransactionDetails.Account.Transaction.Narratives.Narration;
import com.cbs.utils.CbsUtil;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;

@Stateless(mappedName = "FcraCpsmsMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class FcraCpsmsMgmtFacade implements FcraCpsmsMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    private Properties props = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMddhhmmss");

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

    @Override
    public void accValQuickResponse() throws Exception {
        MsgRes msgRes = new MsgRes();
        List<HoReconsilePojo> msgReqMap = new ArrayList<>();
        try {
            UserTransaction ut = context.getUserTransaction();
            List acValQuickResList = em.createNativeQuery("select cd.MessageId, cd.Accountno, cd.Bsrcode, cd.entitycode,"
                    + "cd.accounttype, cd.statecode, cd.pan, ch.bankcode,cd.txnid,ch.RequestFileName,ch.entryDate from "
                    + "cpsms_acc_detail cd, cpsms_acc_header ch where cd.reqStatus ='VR' and "
                    + "cd.MessageId = ch.MessageId").getResultList();
            if (!acValQuickResList.isEmpty()) {
                ut.begin();
                List bnkCodeList = em.createNativeQuery("select Distinct bankCode,bankname from cpsms_acc_header").getResultList();
                if (!bnkCodeList.isEmpty()) {
                    Vector bnVec = (Vector) bnkCodeList.get(0);

                    String nextSeqNo = getMaxSeqNo("AV", ymd.format(new Date()));
                    String resFileName = bnVec.get(0).toString() + "AVQRES" + sdf.format(new Date()) + nextSeqNo + ".xml";

                    msgRes.setMessageId(bnVec.get(0).toString() + "AVQRES" + sdf.format(new Date()) + nextSeqNo);
                    msgRes.setSource(bnVec.get(0).toString());
//                    msgRes.setDestination("CPSMS");
                    msgRes.setDestination("FCRA");
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
//                                acResMsg.setAccountType(acVec.get(16).toString());
                                acResMsg.setAccountType("FR");

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

                    File file = new File(props.getProperty("fcraAcLocalValBkpResLocation") + resFileName);
                    JAXBContext jaxbContext = JAXBContext.newInstance(MsgRes.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(msgRes, file);

                    SftpUtil util = SftpUtilFactory.getSftpUtil();
                    SftpSession sftpSession = getSftpSession();

                    util.put(sftpSession, props.getProperty("fcraAcLocalValBkpResLocation") + resFileName, props.getProperty("fcraAcSftpValidateResLocation"));
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
        List<HoReconsilePojo> msgReqMap = new ArrayList<>();
        try {
            UserTransaction ut = context.getUserTransaction();
            List acValQuickResList = em.createNativeQuery("select cd.MessageId, cd.Accountno, cd.Bsrcode, cd.entitycode,"
                    + "cd.accounttype, cd.statecode, cd.pan, ch.bankcode,cd.txnid,ch.RequestFileName,ch.entryDate from "
                    + "cpsms_acc_detail cd, cpsms_acc_header ch where cd.reqStatus ='QR' and cd.MessageId = ch.MessageId").getResultList();
            if (!acValQuickResList.isEmpty()) {
                ut.begin();
                List bnkCodeList = em.createNativeQuery("select Distinct bankCode,bankname from cpsms_acc_header").getResultList();
                if (!bnkCodeList.isEmpty()) {
                    Vector bnVec = (Vector) bnkCodeList.get(0);

                    String nextSeqNo = getMaxSeqNo("AV", ymd.format(new Date()));
                    String resFileName = bnVec.get(0).toString() + "AVRES" + sdf.format(new Date()) + nextSeqNo + ".xml";

                    msgRes.setMessageId(bnVec.get(0).toString() + "AVRES" + sdf.format(new Date()) + nextSeqNo);
                    msgRes.setSource(bnVec.get(0).toString());
//                    msgRes.setDestination("CPSMS");
                    msgRes.setDestination("FCRA");
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
//                                acResMsg.setAccountType(acVec.get(16).toString());
                                acResMsg.setAccountType("FR");
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

                    File file = new File(props.getProperty("fcraAcLocalValBkpResLocation") + resFileName);
                    JAXBContext jaxbContext = JAXBContext.newInstance(MsgRes.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(msgRes, file);

                    SftpUtil util = SftpUtilFactory.getSftpUtil();
                    SftpSession sftpSession = getSftpSession();

                    util.put(sftpSession, props.getProperty("fcraAcLocalValBkpResLocation") + resFileName, props.getProperty("fcraAcSftpValidateResLocation"));
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
            List tranDailyList = em.createNativeQuery("select a.accno,a.pdt,a.rflg,a.bsrcd,a.bnkcd,a.bnkN,a.reqSt,a.valSt,"
                    + "a.trReq from (select cp.ano as accno,cp.dt as pdt,cr.datarequired as rflg,cr.BsrCode as bsrCd,"
                    + "ch.bankcode as bnkCd,ch.bankname as bnkN, cr.ReqStatus as reqSt, cr.valStatus as valSt, "
                    + "cr.tranReq as trReq from cpsms_acc_detail cr, (select distinct accountno as ano,max(entrydate) as dt,"
                    + "datarequired from cpsms_acc_detail group by accountno) cp, cpsms_acc_header ch where "
                    + "cp.ano = cr.accountno and cp.dt = cr.entrydate and cr.messageid = ch.messageid) a "
                    + "where a.rflg = 'F' and a.reqst ='RR' and a.valst ='V' and a.trReq ='D'").getResultList();
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
                            JAXBElement<String> trDrCrBnkNm = new ObjectFactory().createTransactionDetailsAccountTransactionDrCrBnkNm("");
                            JAXBElement<String> trDrCrBnkBrCd = new ObjectFactory().createTransactionDetailsAccountTransactionDrCrBnkBrCd("");

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
                            txn.getDateOrValueDateOrTransactionType().add(trDrCrBnkNm);
                            txn.getDateOrValueDateOrTransactionType().add(trDrCrBnkBrCd);
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
                        + " '" + bnkCode + "', 'FCRA', '" + bnkCode + "', '" + bnkName + "'," + tranDailyList.size() + ", now(), now(), 'SYSTEM')").executeUpdate();
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
//                txnDetails.setDestination("CPSMS");
                txnDetails.setDestination("FCRA");
                txnDetails.setBankCode(bnkCode);
                txnDetails.setBankName(bnkName);
                txnDetails.setRecordsCount(tranDailyList.size());
                txnDetails.getAccount();

                File file = new File(props.getProperty("fcraTxnDetailLocalLocation") + resFileName);
                JAXBContext jaxbContext = JAXBContext.newInstance(TransactionDetails.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(txnDetails, file);

                SftpUtil util = SftpUtilFactory.getSftpUtil();
                SftpSession sftpSession = getSftpSession();

                util.put(sftpSession, props.getProperty("fcraTxnDetailLocalLocation") + resFileName, props.getProperty("fcraTxnDetailLocation"));
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
            List tranDailyList = em.createNativeQuery("select a.accno,a.pdt,a.rflg,a.bsrcd,a.bnkcd,a.bnkN,a.reqSt,a.valSt,"
                    + "a.trReq,am.openingdt,a.txnid from (select cp.ano as accno,cp.dt as pdt,cr.datarequired as rflg,"
                    + "cr.BsrCode as bsrCd,ch.bankcode as bnkCd,ch.bankname as bnkN,cr.ReqStatus as reqSt, cr.valStatus as valSt, "
                    + "cr.tranReq as trReq,cr.txnid from cpsms_acc_detail cr, (select distinct accountno as ano,"
                    + "max(entrydate) as dt,datarequired from cpsms_acc_detail group by accountno) cp, cpsms_acc_header ch "
                    + "where cp.ano = cr.accountno and cp.dt = cr.entrydate and cr.messageid = ch.messageid) a, accountmaster am "
                    + "where a.accno = am.acno and a.rflg = 'F' and a.reqst ='RR' and a.valst ='V' and a.trReq ='H'").getResultList();
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

                    int res = em.createNativeQuery("update cpsms_acc_detail set tranReq='D' where txnid='" + tranVec.get(10).toString() + "'").executeUpdate();
                    if (res <= 0) {
                        ut.rollback();
                        throw new Exception("Problem in data updation in cpsms_acc_detail !!");
                    }
                }

                String resFileName = bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + ".xml";

                int resultHd = em.createNativeQuery("INSERT INTO cpsms_transaction_dispatch_header (MessageId,resAckMsgId,Source,Destination,BankCode,"
                        + "BankName,RecordsCount,EntryDate,EntryTime,EnterBy) VALUES ('" + bnkCode + "TRNREQ" + sdf.format(new Date()) + nextSeqNo + "','', "
                        + " '" + bnkCode + "', 'FCRA', '" + bnkCode + "', '" + bnkName + "'," + tranDailyList.size() + ", now(), now(), 'SYSTEM')").executeUpdate();
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
//                txnDetails.setDestination("CPSMS");
                txnDetails.setDestination("FCRA");
                txnDetails.setBankCode(bnkCode);
                txnDetails.setBankName(bnkName);
                txnDetails.setRecordsCount(tranDailyList.size());
                txnDetails.getAccount();

                File file = new File(props.getProperty("fcraTxnDetailHisLocalLocation") + resFileName);
                JAXBContext jaxbContext = JAXBContext.newInstance(TransactionDetails.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(txnDetails, file);

                SftpUtil util = SftpUtilFactory.getSftpUtil();
                SftpSession sftpSession = getSftpSession();

                util.put(sftpSession, props.getProperty("fcraTxnDetailHisLocalLocation") + resFileName, props.getProperty("fcraTxnDetailHisLocation"));
                util.disconnect(sftpSession);
                ut.commit();
            }
        } catch (Exception ex) {
            ut.rollback();
            throw new Exception(ex.getMessage());
        }
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

    private SftpSession getSftpSession() throws ApplicationException {
        SftpUtil util = SftpUtilFactory.getSftpUtil();
        SftpSession session = null;
        try {
            session = util.connectByPasswdAuth(props.getProperty("fcraCpsmsSftpHost"),
                    props.getProperty("fcraCpsmsSftpUser"), props.getProperty("fcraCpsmsSftpPassword"),
                    SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO);
        } catch (SftpException e) {
            throw new ApplicationException(e.getMessage());
        }
        return session;
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

    public List selectPendingBalance(String accNo) {
        List list = em.createNativeQuery("Select ifnull(sum(txninstamt),0) from clg_ow_shadowbal where acno='" + accNo + "'").getResultList();
        return list;
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
}
