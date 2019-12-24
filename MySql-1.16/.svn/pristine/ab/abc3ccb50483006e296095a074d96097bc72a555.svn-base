/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import com.cbs.constant.AccountStatusEnum;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.master.AbbParameterInfoDAO;
import com.cbs.dao.neftrtgs.NeftOwDetailsDAO;
import com.cbs.dao.neftrtgs.NeftRtgsLoggingDAO;
import com.cbs.dao.neftrtgs.NeftRtgsStatusDAO;
import com.cbs.dao.sms.MbSmsSenderBankDetailDAO;
import com.cbs.dto.NeftRtgsMismatchPojo;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.entity.master.AbbParameterInfo;
import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.entity.neftrtgs.NeftRtgsLogging;
import com.cbs.entity.neftrtgs.NeftRtgsLoggingPK;
import com.cbs.entity.neftrtgs.NeftRtgsStatus;
import com.cbs.entity.neftrtgs.NeftRtgsStatusPK;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.ibl.pojo.PaymentRequest;
import com.cbs.ibl.pojo.PaymentResponse;
import com.cbs.ibl.util.IblUtil;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.pojo.neftrtgs.HDFCInwardNEFTReturnPojo;
import com.cbs.pojo.neftrtgs.NeftRtgsReportPojo;
import com.cbs.sms.service.PropertyContainer;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.Validator;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
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
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@Stateless(mappedName = "UploadNeftRtgsMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class UploadNeftRtgsMgmtFacade implements UploadNeftRtgsMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private InterBranchTxnFacadeRemote ibtRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private TxnAuthorizationManagementFacadeRemote txnAuth;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdhh = new SimpleDateFormat("dd-MMM-yy-hh:mm:ss");
    SimpleDateFormat ddMMMyyyy = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");

    public String neftRtgsUploadProcess(List<ExcelReaderPojo> inputList, String orgBrCode,
            String user, String iwHead, String process, String neftBankName) throws ApplicationException {
        System.out.println("Start Time Is-->" + new Date());
        Integer custNameVal = 0, ifscCodeVal = 0;
        BigDecimal totalAmount = new BigDecimal("0");
        List<ExcelReaderPojo> neftProcessList = new ArrayList<ExcelReaderPojo>();
        List<TransferSmsRequestTo> smsBatchList = new ArrayList<TransferSmsRequestTo>();
        UserTransaction ut = context.getUserTransaction();
        try {
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found in uploaded file !");
            }
            //Retrieving bank code
            List bankList = entityManager.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
            if (bankList.isEmpty()) {
                throw new ApplicationException("Please define bank code !");
            }
            Vector bankVec = (Vector) bankList.get(0);
            String bankCode = bankVec.get(0).toString();
            //Creating all utrs and beneficiary a/c.
            String acctDetail = "", utrNos = "";
            for (int p = 0; p < inputList.size(); p++) {
                ExcelReaderPojo pojo = inputList.get(p);
                String pojoUtr = stripLeadingAndTrailingQuotes(pojo.getUtr());
                String pojoBenAccount = stripLeadingAndTrailingQuotes(pojo.getBeneAccount());
                if (acctDetail.equals("") && utrNos.equals("")) {
                    if (bankCode.toLowerCase().equalsIgnoreCase("ccbl") && pojoUtr.equals("0")) {
                        pojoUtr = bankCode.toUpperCase() + ymdms.format(new Date()) + String.valueOf(p);

                        utrNos = "\'" + pojoUtr + "\'";
                        acctDetail = "\'" + pojoBenAccount + "\'";

                        pojo.setUtr(pojoUtr);
                        neftProcessList.add(pojo);
                    } else {
                        utrNos = "\'" + pojoUtr + "\'";
                        acctDetail = "\'" + pojoBenAccount + "\'";
                        neftProcessList.add(pojo);
                    }
                } else {
                    if (bankCode.toLowerCase().equalsIgnoreCase("ccbl") && pojoUtr.equals("0")) {
                        pojoUtr = bankCode.toUpperCase() + ymdms.format(new Date()) + String.valueOf(p);

                        utrNos = utrNos + ",\'" + pojoUtr + "\'";
                        acctDetail = acctDetail + ",\'" + pojoBenAccount + "\'";

                        pojo.setUtr(pojoUtr);
                        neftProcessList.add(pojo);
                    } else {
                        if (!utrNos.contains(pojoUtr)) {
                            utrNos = utrNos + ",\'" + pojoUtr + "\'";
                            acctDetail = acctDetail + ",\'" + pojoBenAccount + "\'";
                            neftProcessList.add(pojo);
                        }
                    }
                }
            }

            List accountExistDetailList = getAccountDetails(acctDetail);

            //Extracting Required Values
            List paramList = entityManager.createNativeQuery("select ifnull((select code from parameterinfo_report "
                    + "where reportname='NEFT-RTGS-CUSTNAME'),'') as code union all select ifnull((select code from "
                    + "parameterinfo_report where reportname='NEFT-RTGS-IFSC'), '') as code").getResultList();
            if (!paramList.isEmpty()) {
                for (int i = 0; i < paramList.size(); i++) {
                    Vector paramVec = (Vector) paramList.get(i);
                    if (i == 0 && !paramVec.get(0).toString().trim().equals("")) {
                        custNameVal = Integer.parseInt(paramVec.get(0).toString().trim());
                    }
                    if (i == 1 && !paramVec.get(0).toString().trim().equals("")) {
                        ifscCodeVal = Integer.parseInt(paramVec.get(0).toString().trim());
                    }
                }
            }

            List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
            if (bankTo.isEmpty() || bankTo.get(0).getTemplateBankName() == null) {
                throw new ApplicationException("Please define template bank name.");
            }
            String templateBankName = bankTo.get(0).getTemplateBankName().trim();

            List dataList = entityManager.createNativeQuery("select acno from abb_parameter_info "
                    + "where purpose = 'INTERSOLE ACCOUNT'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define Intersole A/c in abb parameter info");
            }
            Vector dataVec = (Vector) dataList.get(0);
            String isoAccount = dataVec.get(0).toString();

            dataList = entityManager.createNativeQuery("select alphacode from branchmaster where "
                    + "brncode = '" + Integer.parseInt(orgBrCode) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define alphacode for-->" + orgBrCode);
            }
            dataVec = (Vector) dataList.get(0);
            String orgnAlphaCode = dataVec.get(0).toString();

            ut.begin();
            List utrList = entityManager.createNativeQuery("select utr from neft_rtgs_status "
                    + "where utr in (" + utrNos + ")").getResultList();

            Float trsNo = ftsRemote.getTrsNo();
            //String businessDt = ymd.format(new Date());
             String businessDt = commonReport.getBusinessDate();
            for (int i = 0; i < neftProcessList.size(); i++) {
                ExcelReaderPojo pojo = neftProcessList.get(i);
                //businessDt = (pojo.getTranDate() != null && !pojo.getTranDate().equals("")) ? pojo.getTranDate() : businessDt;
                String rs = checkUtrDuplicate(utrList, stripLeadingAndTrailingQuotes(pojo.getUtr()));
                if (rs.equalsIgnoreCase("duplicate")) {
                    ibtRemote.insertIntoNeftStatusTable("duplicate", process, neftBankName, trsNo, user, pojo);
                    continue;
                }

                String acValMsg = validateAcNo(accountExistDetailList, pojo, ifscCodeVal, custNameVal);
                if (!acValMsg.substring(0, 4).equalsIgnoreCase("true")) {
                    ibtRemote.insertIntoNeftStatusTable(acValMsg, process, neftBankName, trsNo, user, pojo);
                    continue;
                }
                pojo.setBeneAccount(acValMsg.substring(4));
                String result = ibtRemote.insertNeftBulkTransaction(process, neftBankName, trsNo, user,
                        orgBrCode, pojo, isoAccount, orgnAlphaCode);
                if (!result.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem In Inward Neft Transaction Processing.");
                }

                totalAmount = totalAmount.add(pojo.getAmount());
                //Adding Object For Sms
                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                        == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
                if (templateType.equalsIgnoreCase("en") || templateType.equalsIgnoreCase("indr")) {
                    if (pojo.getSenderName() == null || pojo.getSenderName().equals("")) {
                        tSmsRequestTo.setTemplate(SmsType.INWARD_NEFT_RTGS);
                    } else {
                        tSmsRequestTo.setTemplate(SmsType.INWARD_NEFT_RTGS_OTH);
                        tSmsRequestTo.setLastCheque(pojo.getSenderName().trim());
                    }
                } else {
                    tSmsRequestTo.setTemplate(SmsType.INWARD_NEFT_RTGS);
                }

                tSmsRequestTo.setMsgType("T");
//                tSmsRequestTo.setTemplate(SmsType.INWARD_NEFT_RTGS);
                tSmsRequestTo.setAcno(pojo.getBeneAccount());
                tSmsRequestTo.setTranType(2);
                tSmsRequestTo.setTy(0);
                tSmsRequestTo.setAmount(pojo.getAmount().doubleValue());
                tSmsRequestTo.setDate(dmy.format(new Date()));
                tSmsRequestTo.setFirstCheque(pojo.getUtr());
                tSmsRequestTo.setBankName(templateBankName);

                smsBatchList.add(tSmsRequestTo);
            }
            //Performing General Ledger Transaction of Debit
            if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
                Float recNo = ftsRemote.getRecNo();
//                int n = entityManager.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, "
//                        + "balance, trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, "
//                        + "trandesc, tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,"
//                        + "adviceno,advicebrncode) values('" + iwHead + "',1,'" + ymd.format(new Date()) + "',"
//                        + "'" + ymd.format(new Date()) + "'," + totalAmount.doubleValue() + ",0, 0,2,"
//                        + "'" + dmy.format(new Date()) + " Neft-Rtgs GL Entry" + "',0,'','19000101','" + user + "',"
//                        + "'Y'," + recNo + ",3,'" + user + "'," + trsNo + ",66,0,'','',now(),'" + orgBrCode + "',"
//                        + "'" + orgBrCode + "',0,'','','' )").executeUpdate();

                int n = entityManager.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, "
                        + "balance, trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, "
                        + "trandesc, tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,"
                        + "adviceno,advicebrncode) values('" + iwHead + "',1,'" + businessDt + "',"
                        + "'" + businessDt + "'," + totalAmount.doubleValue() + ",0, 0,2,"
                        + "'" + dmy.format(ymd.parse(businessDt)) + " Neft-Rtgs GL Entry" + "',0,'','19000101','" + user + "',"
                        + "'Y'," + recNo + ",3,'" + user + "'," + trsNo + ",66,0,'','',now(),'" + orgBrCode + "',"
                        + "'" + orgBrCode + "',0,'','','' )").executeUpdate();

                if (n <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + iwHead);
                }

                List list = entityManager.createNativeQuery("select acno from reconbalan "
                        + "where acno='" + iwHead + "'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define head in reconbalan-->" + iwHead);
                }

                n = entityManager.createNativeQuery("update reconbalan set "
                        + "balance=balance-" + totalAmount.doubleValue() + ",dt=CURRENT_TIMESTAMP "
                        + "where acno='" + iwHead + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Balance Updation Problem For A/c No-->" + iwHead);
                }
            }
            //Deaf updation
            for (TransferSmsRequestTo to : smsBatchList) {
                ftsRemote.lastTxnDateUpdation(to.getAcno());
            }
            //Deaf updation end here
            ut.commit();
            try {
                smsFacade.trfSmsRequestToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending In Upload Neft Rtgs." + e.getMessage());
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        System.out.println("End Time Is-->" + new Date());
        return "true";
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

//    public List<NeftRtgsReportPojo> getNeftRtgsReportStatus(Date frDt, Date toDt, String status, String orgncode,
//            String processType, String neftbank) throws ApplicationException {
//        List<NeftRtgsReportPojo> resultList = new ArrayList<NeftRtgsReportPojo>();
//        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
//        try {
//            String alphaCode = commonReport.getAlphacodeByBrncode(orgncode);
//            List<NeftRtgsStatus> entityList = new ArrayList<NeftRtgsStatus>();
//            if (status.equalsIgnoreCase("All")) {
//                entityList = neftRtgsStatusDAO.getStatusEntityByDate(frDt, toDt, processType, neftbank);
//            } else if (status.equalsIgnoreCase("This Utr Already Processed.")) {
//                entityList = neftRtgsStatusDAO.getUtrAlreadyProcessed(frDt, toDt, status, processType, neftbank);
//            } else {
//                if (alphaCode.equalsIgnoreCase("HO")) {
//                    entityList = neftRtgsStatusDAO.getStatusEntityByDateAndStatus(frDt, toDt, status, processType, neftbank);
//                } else {
//                    SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//                    List list = entityManager.createNativeQuery("select ifnull(utr,'') as utr,bene_account,bene_name,"
//                            + "ifnull(sender_name,'') as sender_name,amount,status,ifnull(reason,'') as reason,"
//                            + "narration,ifnull(sender_account,'') as sender_account,sender_ifsc,ifnull(details,'') "
//                            + "as details,tran_time from neft_rtgs_status n,cbs_acno_mapping m,accountmaster a "
//                            + "where n.dt between '" + ymd.format(frDt) + "' and '" + ymd.format(toDt) + "' and "
//                            + "n.status='SUCCESS' and process='" + processType + "' and neft_bank_name='" + neftbank + "' and "
//                            + "n.reason<>'THIS UTR ALREADY PROCESSED.' and (n.bene_account=m.old_ac_no or "
//                            + "n.bene_account=m.new_ac_no) and m.new_ac_no=a.acno and "
//                            + "a.curbrcode='" + orgncode + "'").getResultList();
//                    for (int i = 0; i < list.size(); i++) {
//                        NeftRtgsStatus tempObj = new NeftRtgsStatus();
//                        Vector ele = (Vector) list.get(i);
//
//                        tempObj.setUtr(ele.get(0).toString().trim());
//                        tempObj.setBeneAccount(ele.get(1).toString().trim());
//                        tempObj.setBeneName(ele.get(2).toString().trim());
//                        tempObj.setSenderName(ele.get(3).toString().trim());
//                        tempObj.setAmount(new BigDecimal(ele.get(4).toString().trim()));
//                        tempObj.setStatus("SUCCESS");
//                        tempObj.setReason(ele.get(6).toString().trim());
//                        tempObj.setNarration(ele.get(7).toString().trim());
//                        tempObj.setSenderAccount(ele.get(8).toString().trim());
//                        tempObj.setSenderIfsc(ele.get(9).toString().trim());
//                        tempObj.setDetails(ele.get(10).toString().trim());
//                        tempObj.setTranTime(sqlFormat.parse(ele.get(11).toString().trim()));
//
//                        entityList.add(tempObj);
//                    }
//                }
//            }
//
//            System.out.println("Size of entityList-->" + entityList.size());
//            for (int i = 0; i < entityList.size(); i++) {
//                NeftRtgsStatus entity = entityList.get(i);
//                System.out.println("Account No Is-->" + entity.getBeneAccount().trim());
//            }
//            if (entityList.isEmpty()) {
//                throw new ApplicationException("Data does not exist");
//            }
//            for (int i = 0; i < entityList.size(); i++) {
//                NeftRtgsReportPojo pojo = new NeftRtgsReportPojo();
//                NeftRtgsStatus entity = entityList.get(i);
//                pojo.setUtrNo(entity.getUtr());
//                pojo.setBeneAccount(entity.getBeneAccount());
//                pojo.setBeneName(entity.getBeneName());
//                pojo.setSenderName(entity.getSenderName() == null ? "" : entity.getSenderName());
//                pojo.setAmount(entity.getAmount());
//                if (entity.getStatus().equalsIgnoreCase("Unsuccess")
//                        || entity.getStatus().equalsIgnoreCase("Mismatch")
//                        || entity.getStatus().equalsIgnoreCase("Return")
//                        || entity.getStatus().equalsIgnoreCase("Sent IDBI")
//                        || entity.getStatus().equalsIgnoreCase("Sponsor")) {
//                    pojo.setNarration(entity.getReason());
//                } else {
//                    pojo.setNarration(entity.getNarration());
//                }
//                pojo.setSenderAccount(entity.getSenderAccount());
//                pojo.setSenderIfsc(entity.getSenderIfsc());
//                pojo.setDetails(entity.getDetails());
//                pojo.setTranDt(entity.getTranTime());
//
//                List bankList = commonReport.getBranchNameandAddress(orgncode);
//
//                pojo.setBranchName(bankList.get(0).toString());
//                pojo.setBranchAdd(bankList.get(1).toString());
//                resultList.add(pojo);
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex);
//        }
//        return resultList;
//    }
    @Override
    public List<NeftRtgsReportPojo> getNeftRtgsReportStatus(Date frDt, Date toDt, String status, String orgncode,
            String processType, String neftbank) throws ApplicationException {
        List<NeftRtgsReportPojo> resultList = new ArrayList<>();
        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
        try {
            String alphaCode = commonReport.getAlphacodeByBrncode(orgncode);
            List<NeftRtgsStatus> entityList = new ArrayList<>();
            if (alphaCode.equalsIgnoreCase("HO")) {
                if (status.equalsIgnoreCase("All")) {
                    entityList = neftRtgsStatusDAO.getStatusEntityByDate(frDt, toDt, processType, neftbank);
                } else if (status.equalsIgnoreCase("This Utr Already Processed.")) {
                    entityList = neftRtgsStatusDAO.getUtrAlreadyProcessed(frDt, toDt, status, processType, neftbank);
                } 
                 else if (status.equalsIgnoreCase("Processed Mismatch")) {
                    entityList = neftRtgsStatusDAO.getProcessedMismatch(frDt, toDt, status, processType, neftbank);
                } 
                else {
                    entityList = neftRtgsStatusDAO.getStatusEntityByDateAndStatus(frDt, toDt, status, processType, neftbank);
                }
            } else {
                if (status.equalsIgnoreCase("Success")) {
                    SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    List list = entityManager.createNativeQuery("select ifnull(utr,'') as utr,bene_account,bene_name,"
                            + "ifnull(sender_name,'') as sender_name,amount,status,ifnull(reason,'') as reason,"
                            + "narration,ifnull(sender_account,'') as sender_account,sender_ifsc,ifnull(details,'') "
                            + "as details,tran_time from neft_rtgs_status n,cbs_acno_mapping m,accountmaster a "
                            + "where n.dt between '" + ymd.format(frDt) + "' and '" + ymd.format(toDt) + "' and "
                            + "n.status='SUCCESS' and process='" + processType + "' and neft_bank_name='" + neftbank + "' and "
                            + "n.reason<>'THIS UTR ALREADY PROCESSED.' and (n.bene_account=m.old_ac_no or "
                            + "n.bene_account=m.new_ac_no) and m.new_ac_no=a.acno and "
                            + "a.curbrcode='" + orgncode + "'").getResultList();
                    for (int i = 0; i < list.size(); i++) {
                        NeftRtgsStatus tempObj = new NeftRtgsStatus();
                        Vector ele = (Vector) list.get(i);

                        tempObj.setUtr(ele.get(0).toString().trim());
                        tempObj.setBeneAccount(ele.get(1).toString().trim());
                        tempObj.setBeneName(ele.get(2).toString().trim());
                        tempObj.setSenderName(ele.get(3).toString().trim());
                        tempObj.setAmount(new BigDecimal(ele.get(4).toString().trim()));
                        tempObj.setStatus("SUCCESS");
                        tempObj.setReason(ele.get(6).toString().trim());
                        tempObj.setNarration(ele.get(7).toString().trim());
                        tempObj.setSenderAccount(ele.get(8).toString().trim());
                        tempObj.setSenderIfsc(ele.get(9).toString().trim());
                        tempObj.setDetails(ele.get(10).toString().trim());
                        tempObj.setTranTime(sqlFormat.parse(ele.get(11).toString().trim()));

                        entityList.add(tempObj);
                    }
                } else if (status.equalsIgnoreCase("Unsuccess")) {
                    entityList = neftRtgsStatusDAO.getStatusEntityByDateAndStatus(frDt, toDt, status, processType, neftbank);
                }
            }

            if (entityList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            for (int i = 0; i < entityList.size(); i++) {
                NeftRtgsReportPojo pojo = new NeftRtgsReportPojo();
                NeftRtgsStatus entity = entityList.get(i);
                pojo.setUtrNo(entity.getUtr());
                pojo.setBeneAccount(entity.getBeneAccount());
                pojo.setBeneName(entity.getBeneName());
                pojo.setSenderName(entity.getSenderName() == null ? "" : entity.getSenderName());
                pojo.setAmount(entity.getAmount());
                if (entity.getStatus().equalsIgnoreCase("Unsuccess")
                        || entity.getStatus().equalsIgnoreCase("Mismatch")
                        || entity.getStatus().equalsIgnoreCase("Return")
                        || entity.getStatus().equalsIgnoreCase("Sent IDBI")
                        || entity.getStatus().equalsIgnoreCase("Sponsor")) {
                    pojo.setNarration(entity.getReason());
                } else {
                    pojo.setNarration(entity.getNarration());
                }
                pojo.setSenderAccount(entity.getSenderAccount());
                pojo.setSenderIfsc(entity.getSenderIfsc());
                pojo.setDetails(entity.getDetails());
                pojo.setTranDt(entity.getTranTime());

                List bankList = commonReport.getBranchNameandAddress(orgncode);

                pojo.setBranchName(bankList.get(0).toString());
                pojo.setBranchAdd(bankList.get(1).toString());
                resultList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<NeftRtgsStatus> getMismatchData(Date currentDt, String status,
            String processType, String neftBankName) throws ApplicationException {
        List<NeftRtgsStatus> resultList = new ArrayList<NeftRtgsStatus>();
        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
        try {
            resultList = neftRtgsStatusDAO.getEntityByCurrentDateAndStatus(currentDt,
                    status, processType, neftBankName);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public String processMismatchData(List<NeftRtgsMismatchPojo> processList, String status, String orgBrCode,
            String user, String leafStatus, String processType,
            String neftBankName) throws ApplicationException {
        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
        UserTransaction ut = context.getUserTransaction();

        String message = "unsuccess", valueDt = "";
        BigDecimal totalAmount = new BigDecimal("0");
        try {
            ut.begin();
            if (processList.isEmpty()) {
                throw new ApplicationException("There is no data to process.");
            }
            Float trsno = ftsRemote.getTrsNo();
            NeftRtgsMismatchPojo pojo = processList.get(0);
            NeftRtgsStatus statusObj = neftRtgsStatusDAO.findByUtrNoAndStatus(pojo.getUtr(), leafStatus);
            if (statusObj != null) {
                if (status.equals("P")) {
                    if (statusObj.getStatus().equalsIgnoreCase("Success")
                            || statusObj.getStatus().equalsIgnoreCase("Unsuccess")
                            || statusObj.getStatus().equalsIgnoreCase("Process")) {
                        throw new ApplicationException("This UTR is already processed Or in Process. Please refresh the form "
                                + "and check the report");
                    }
                    if (!statusObj.getStatus().equalsIgnoreCase("Mismatch")) {
                        throw new ApplicationException("This UTR is already processed Or in Process. Please refresh the "
                                + "form and check the report");
                    }
                    statusObj.setStatus("Process");
                    neftRtgsStatusDAO.update(statusObj);

                    Float recNo = ftsRemote.getRecNo();
                    BigDecimal amount = pojo.getAmount();
                    valueDt = pojo.getValueDt();
                    String details = pojo.getTxnType() + "-" + pojo.getUtr() + "-" + pojo.getSenderName();

                    String newAccountNo = "";
                    try {
                        newAccountNo = ftsRemote.getNewAccountNumber(pojo.getBeneAccount());
                    } catch (Exception ex) {
                        throw new Exception("New Account Not Found For Acno-->" + pojo.getBeneAccount());
                    }

                    String accProcessMessage = ibtRemote.cbsPostingSx(newAccountNo, 0, valueDt,
                            amount.doubleValue(), 0f, 2, details, 0f, "A", "", "", 3, 0f, recNo, 66,
                            newAccountNo.substring(0, 2), orgBrCode, user, user, trsno, "", "");
                    if (accProcessMessage.substring(0, 4).equalsIgnoreCase("true")) {
                        statusObj.setStatus("Success");
                        statusObj.setDetails("Mismatch");
                        statusObj.setAuthBy(user);

                        totalAmount = totalAmount.add(amount);
                        ftsRemote.lastTxnDateUpdation(newAccountNo);
                    } else {
                        statusObj.setStatus("Unsuccess");
                        statusObj.setDetails("");
                        statusObj.setAuthBy(user);
                        statusObj.setReason(accProcessMessage);
                    }
                    neftRtgsStatusDAO.update(statusObj);
                    //Performing General Ledger Transaction of Debit
                    Float recno = ftsRemote.getRecNo();
                    String glAccount = "";
                    List iwList = entityManager.createNativeQuery("select iw_head from cbs_auto_neft_details "
                            + "where process='" + processType + "' and neft_bank_name='" + neftBankName + "'").getResultList();
                    if (iwList.isEmpty()) {
                        throw new ApplicationException("Please fill details in auto neft "
                                + "table for process::" + processType + " and bank::" + neftBankName);
                    }
                    Vector iwVec = (Vector) iwList.get(0);
                    if (iwVec.get(0) == null || iwVec.get(0).toString().equals("")
                            || iwVec.get(0).toString().trim().length() != 12) {
                        throw new ApplicationException("Please fill proper inward head in auto neft details.");
                    }
                    glAccount = iwVec.get(0).toString().trim();

                    if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
                        message = ibtRemote.cbsPostingCx(glAccount, 1, valueDt, totalAmount.doubleValue(),
                                0f, 2, dmy.format(new Date()) + " Neft-Rtgs GL Entry", 0f, "A", "", "", 3, 0f, recno,
                                66, orgBrCode, orgBrCode, user, user, trsno, "", "");
                        if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(message);
                        }

                        message = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount),
                                glAccount, 0, totalAmount.doubleValue(), "Y", "Y");
                        if (!message.equalsIgnoreCase("true")) {
                            throw new ApplicationException(message);
                        }
                    }
                } else if (status.equals("R")) {
                    statusObj.setStatus("Unsuccess");
                    statusObj.setDetails("Mismatch");
                    statusObj.setAuthBy(user);
                    neftRtgsStatusDAO.update(statusObj);
                }
            }
            ut.commit();
            //Sms Sending
            if (statusObj != null && status.equalsIgnoreCase("P") && totalAmount.compareTo(new BigDecimal(0)) == 1) {
                try {
//                    smsFacade.sendTransactionalSms(SmsType.TRANSFER_DEPOSIT, ftsRemote.getNewAccountNumber(statusObj.getBeneAccount()), 2, 0,
//                            statusObj.getAmount().doubleValue(), dmy.format(new Date()));

                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                    String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                            == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
                    if (templateType.equalsIgnoreCase("en") || templateType.equalsIgnoreCase("indr")) {
                        if (statusObj.getSenderName() == null || statusObj.getSenderName().equals("")) {
                            tSmsRequestTo.setTemplate(SmsType.INWARD_NEFT_RTGS);
                        } else {
                            tSmsRequestTo.setTemplate(SmsType.INWARD_NEFT_RTGS_OTH);
                            tSmsRequestTo.setLastCheque(statusObj.getSenderName().trim());
                        }
                    } else {
                        tSmsRequestTo.setTemplate(SmsType.INWARD_NEFT_RTGS);
                    }

                    tSmsRequestTo.setMsgType("T");
                    tSmsRequestTo.setAcno(ftsRemote.getNewAccountNumber(statusObj.getBeneAccount()));
                    tSmsRequestTo.setTranType(2);
                    tSmsRequestTo.setTy(0);
                    tSmsRequestTo.setAmount(statusObj.getAmount().doubleValue());
                    tSmsRequestTo.setDate(dmy.format(new Date()));
                    tSmsRequestTo.setFirstCheque(statusObj.getUtr());
                    smsFacade.sendSms(tSmsRequestTo);
                } catch (Exception ex) {
                    System.out.println("Error SMS Sending In Mismatch Processing-->A/c is::"
                            + statusObj.getBeneAccount() + " And Amount is::"
                            + statusObj.getAmount().doubleValue() + "\n" + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "success";
    }

    public List<MbSmsSenderBankDetail> getBankCode() throws ApplicationException {
        MbSmsSenderBankDetailDAO mbSmsSenderBankDetailDAO = new MbSmsSenderBankDetailDAO(entityManager);
        try {
            List<MbSmsSenderBankDetail> resultList = mbSmsSenderBankDetailDAO.getBankAndSenderDetail();
            return resultList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String csvUploadProcess(List<ExcelReaderPojo> inputList, String orgBrCode,
            String user) throws ApplicationException {
        String message = "unsuccess";
        NeftRtgsLoggingDAO neftRtgsLoggingDAO = new NeftRtgsLoggingDAO(entityManager);
        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
        BigDecimal totalAmount = new BigDecimal("0");
        Float trsno = ftsRemote.getTrsNo();
        String valueDt = "";
        try {
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found in excel file !");
            }
            for (int i = 0; i < inputList.size(); i++) {
                ExcelReaderPojo pojo = inputList.get(i);
                List<NeftRtgsLogging> loggingList = neftRtgsLoggingDAO.findByUtrNo(pojo.getUtr().trim());
                if (!loggingList.isEmpty()) {
                    message = "duplicate";
                } else {
                    message = "unsuccess";
                }
                NeftRtgsLogging neftRtgsLogging = new NeftRtgsLogging();
                NeftRtgsLoggingPK neftRtgsLoggingPK = new NeftRtgsLoggingPK();
                neftRtgsLoggingPK.setTxnId(neftRtgsLoggingDAO.getMaxTxnId());
                neftRtgsLoggingPK.setDt(ymd.parse(ymd.format(new Date())));
                neftRtgsLogging.setNeftRtgsLoggingPK(neftRtgsLoggingPK);

                neftRtgsLogging.setCoopBankAccountNo(pojo.getCopBankAccNo().trim());
                neftRtgsLogging.setUtr(pojo.getUtr().trim());
                neftRtgsLogging.setIfscCode(pojo.getIfsccode());
                neftRtgsLogging.setBeneAccount(pojo.getBeneAccount().trim());
                neftRtgsLogging.setReceiverIfsc(pojo.getReceiverIfsc());

                neftRtgsLogging.setBeneName(pojo.getBeneName().trim());
                neftRtgsLogging.setBeneAddress(pojo.getBeneAdd().trim());
                neftRtgsLogging.setAmount(pojo.getAmount());
                neftRtgsLogging.setTimestamp(ddMMMyyyy.parse(pojo.getTimestamp().trim()));
                neftRtgsLogging.setSenderAccount(pojo.getSenderAcc().trim());

                neftRtgsLogging.setSenderName(pojo.getSenderName().trim());
                neftRtgsLogging.setSenderIfsc(pojo.getSenderIfsc().trim());
                neftRtgsLogging.setSenderAddress1(pojo.getSenderAddOne().trim());
                neftRtgsLogging.setSenderAddress2(pojo.getSenderAddTwo());
                neftRtgsLogging.setTxnType(pojo.getTxnType());

                neftRtgsLogging.setTranTime(new Date());
                neftRtgsLogging.setEnterBy(user);
                neftRtgsLogging.setAuthBy(user);
                neftRtgsLogging.setValueDt(ddMMMyyyy.parse(pojo.getTimestamp().trim()));
                neftRtgsLoggingDAO.save(neftRtgsLogging);

                /**
                 * Neft-Rtgs Status Table Processing
                 */
                String utr = stripLeadingAndTrailingQuotes(pojo.getUtr());
                String beneAccount = stripLeadingAndTrailingQuotes(pojo.getBeneAccount());
                String beneName = stripLeadingAndTrailingQuotes(pojo.getBeneName());
                String receiverIfsc = stripLeadingAndTrailingQuotes(pojo.getReceiverIfsc());
                BigDecimal amount = pojo.getAmount();

                String senderAccount = stripLeadingAndTrailingQuotes(pojo.getSenderAcc());
                String senderName = stripLeadingAndTrailingQuotes(pojo.getSenderName());
                String senderIfsc = stripLeadingAndTrailingQuotes(pojo.getSenderIfsc());
                String txnType = stripLeadingAndTrailingQuotes(pojo.getTxnType());

                String enterby = user;
                String authBy = user;
                String details = txnType + "-" + utr + "-" + senderName;
                Float recNo = ftsRemote.getRecNo();
                valueDt = ymd.format(ddMMMyyyy.parse(pojo.getTimestamp().trim()));

                /**
                 * Performing Account Holder Transaction of Credit
                 */
                NeftRtgsStatus neftRtgsStatus = new NeftRtgsStatus();
                NeftRtgsStatusPK neftRtgsStatusPK = new NeftRtgsStatusPK();
                neftRtgsStatusPK.setTxnId(neftRtgsStatusDAO.getMaxTxnId());
                neftRtgsStatusPK.setDt(ymd.parse(ymd.format(new Date())));
                neftRtgsStatus.setNeftRtgsStatusPK(neftRtgsStatusPK);

                neftRtgsStatus.setUtr(utr);
                neftRtgsStatus.setBeneAccount(beneAccount);
                neftRtgsStatus.setBeneName(beneName);
                neftRtgsStatus.setReceiverIfsc(receiverIfsc);
                neftRtgsStatus.setAmount(amount);

                neftRtgsStatus.setSenderAccount(senderAccount);
                neftRtgsStatus.setSenderName(senderName);
                neftRtgsStatus.setSenderIfsc(senderIfsc);
                neftRtgsStatus.setTxnType(txnType);
                neftRtgsStatus.setNarration(details);

                neftRtgsStatus.setTranTime(new Date());
                neftRtgsStatus.setEnterBy(enterby);
                neftRtgsStatus.setAuthBy(authBy);
                neftRtgsStatus.setValueDt(ddMMMyyyy.parse(pojo.getTimestamp().trim()));
                if (!message.equals("duplicate")) {
                    try {
                        String accProcessMessage = ibtRemote.cbsPostingSx(beneAccount, 0, valueDt, amount.doubleValue(), 0f, 2, details, 0f, "A", "", "", 3, 0f, recNo, 66, beneAccount.substring(0, 2), orgBrCode, enterby, authBy, trsno, "", "");

                        if (accProcessMessage.substring(0, 4).equalsIgnoreCase("true")) {
                            neftRtgsStatus.setStatus("Success");
                            neftRtgsStatus.setDetails("");
                            neftRtgsStatus.setReason("");
                            totalAmount = totalAmount.add(amount);
                        } else {
                            neftRtgsStatus.setStatus("Unsuccess");
                            neftRtgsStatus.setDetails("");
                            neftRtgsStatus.setReason(accProcessMessage);
                        }
                    } catch (Exception ex) {
                        neftRtgsStatus.setStatus("Unsuccess");
                        neftRtgsStatus.setDetails("");
                        neftRtgsStatus.setReason(ex.getMessage());
                    }
                } else {
                    if (message.equalsIgnoreCase("duplicate")) {
                        neftRtgsStatus.setStatus("Unsuccess");
                        neftRtgsStatus.setDetails("");
                        neftRtgsStatus.setReason("THIS UTR ALREADY PROCESSED.");
                    }
                }
                neftRtgsStatusDAO.save(neftRtgsStatus);
            }
            /**
             * Performing General Ledger Transaction of Debit
             */
            Float recno = ftsRemote.getRecNo();
            String glAccount = "";
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(entityManager);
            List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NEFT-RTGS");
            if (!abbParameterInfoList.isEmpty()) {
                for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                    glAccount = orgBrCode + abbPojo.getAcno();
                }
            }
            if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
                message = ibtRemote.cbsPostingCx(glAccount, 1, valueDt, totalAmount.doubleValue(), 0f, 2, dmy.format(new Date()) + " Neft-Rtgs GL Entry", 0f, "A", "", "", 3, 0f, recno, 66, orgBrCode, orgBrCode, user, user, trsno, "", "");
                if (message.substring(0, 4).equalsIgnoreCase("true")) {
                    message = "success";
                }
            }
            return message;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<CbsAutoNeftDetails> getAutoNeftDetailsByProcess(String process) throws ApplicationException {
        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
        try {
            return neftOwDetailsDAO.getAutoNeftDetailsByProcess(process);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<CbsAutoNeftDetails> getNeftMisMatchBankName(String process) throws ApplicationException {
        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
        try {
            return neftOwDetailsDAO.getNeftMisMatchBankName(process);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public CbsAutoNeftDetails getNeftDetailsByNefBankNameAndProcess(String neftBankName,
            String process) throws ApplicationException {
        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
        try {
            return neftOwDetailsDAO.getNeftDetailsByNefBankNameAndProcess(neftBankName, process);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAccountDetails(String acNoList) throws ApplicationException {
        try {
            Query query = entityManager.createNativeQuery("select ifnull(bm.ifsc_code,''), att.acctNature,am.accStatus,am.acno, am.custname,cam.old_ac_no from branchmaster bm, accounttypemaster att, accountmaster am , cbs_acno_mapping cam "
                    + "where att.acctCode = am.accttype and cast(am.curBrCode as unsigned) = bm.brnCode and am.acno =cam.new_ac_no and (cam.old_ac_no in (" + acNoList + ") or cam.new_ac_no in (" + acNoList + "))"
                    + "union all "
                    + "select ifnull(bm.ifsc_code,''), att.acctnature,am.accstatus,am.acno, am.custname,cam.old_ac_no from branchmaster bm, accounttypemaster att, td_accountmaster am , cbs_acno_mapping cam "
                    + "where att.acctcode = am.accttype and cast(am.curbrcode as unsigned)= bm.brncode and am.acno=cam.new_ac_no and (cam.old_ac_no in (" + acNoList + ") or cam.new_ac_no in (" + acNoList + ")) "
                    + "union all "
                    + "select ifnull(bm.ifsc_code,''), att.acctnature,am.postflag,am.acno, am.acname,cam.old_ac_no from branchmaster bm, accounttypemaster att, gltable am , cbs_acno_mapping cam "
                    + "where att.acctcode = substring(am.acno,3,2) and substring(acno,1,2)= bm.brncode and am.acno=cam.new_ac_no and (cam.old_ac_no in (" + acNoList + ") or cam.new_ac_no in (" + acNoList + ")) "
                    + "union all "
                    + "select ifnull(bm.ifsc_code,''),att.acctnature,am.accstatus,am.acno, am.custname,cam.old_ac_no from branchmaster bm, accounttypemaster att, fidility_accountmaster am , cbs_acno_mapping cam "
                    + "where att.acctcode = am.accttype and cast(am.brncode as unsigned)= bm.brncode and am.acno=cam.new_ac_no and (cam.old_ac_no in (" + acNoList + ") or cam.new_ac_no in (" + acNoList + "))");
            return query.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String validateAcNo(List existAcnoList, ExcelReaderPojo pojo, int ifscCodeVal,
            int custCodeVal) throws ApplicationException {
        try {
            if (existAcnoList.isEmpty()) {
                return "No Mapped Account";
            }
            int index = -1;
            Vector ele;
            for (int i = 0; i < existAcnoList.size(); i++) {
                ele = (Vector) existAcnoList.get(i);
                if (stripLeadingAndTrailingQuotes(pojo.getBeneAccount()).equalsIgnoreCase(ele.get(3).toString())
                        || stripLeadingAndTrailingQuotes(pojo.getBeneAccount()).equalsIgnoreCase(ele.get(5).toString())) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                return "A/c number does not exist";
            }
            ele = (Vector) existAcnoList.get(index);
            String ifscCode = ele.get(0).toString().trim();

            String nature = ele.get(1).toString().trim();
            int accPostFlag = Integer.parseInt(ele.get(2).toString().trim());
            String newAcno = ele.get(3).toString().trim();
            String custName = ele.get(4).toString().trim();

            if (nature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (accPostFlag == 99) {
                    return "GL head not in use";
                }
            } else {
                if (accPostFlag == 9) {
                    return "Account is Closed";
                } else if (accPostFlag == 8) {
                    return "Operation Stopped For This Account";
                } else if (accPostFlag == 4) {
                    return "Account has been Frozen";
                } else if (accPostFlag == 2) {
                    return "Account Has been marked Inoperative";
                } else if (AccountStatusEnum.getAcStatusValue(String.valueOf(accPostFlag)) == null) {
                    return "Sorry,Invalid Account Status";
                }
            }
            
            String receiverIfscCode = stripLeadingAndTrailingQuotes(pojo.getReceiverIfsc());
            if (ifscCodeVal == 1 && !receiverIfscCode.equalsIgnoreCase(ifscCode)) {
                return "ifsc:" + ifscCode;
            }
            
            String beneficiaryName = stripLeadingAndTrailingQuotes(pojo.getBeneName());
            if (custCodeVal == 1 && !beneficiaryName.equalsIgnoreCase(custName)) {
                return "custname:" + custName;
            }
            return "true" + newAcno;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private String checkUtrDuplicate(List utrList, String utrNo) throws ApplicationException {
        try {
            Vector ele;
            int index = -1;
            for (int i = 0; i < utrList.size(); i++) {
                ele = (Vector) utrList.get(i);
                if (utrNo.equalsIgnoreCase(ele.get(0).toString())) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                return "true";
            }
            return "duplicate";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
//    public String validateAcInBulkCredit(String acctNo) throws ApplicationException {
//        try {
//            List dataList = entityManager.createNativeQuery("select att.acctnature,am.accstatus from accounttypemaster att, "
//                    + "accountmaster am where att.acctcode = am.accttype and am.acno='" + acctNo + "' union all "
//                    + "select att.acctnature,am.accstatus from accounttypemaster att, td_accountmaster am where "
//                    + "att.acctcode = am.accttype and am.acno='" + acctNo + "' union all select att.acctnature,"
//                    + "am.postflag from accounttypemaster att, gltable am where att.acctcode = substring(am.acno,3,2) "
//                    + "and am.acno='" + acctNo + "' union all select att.acctnature,am.accstatus from accounttypemaster "
//                    + "att, fidility_accountmaster am where att.acctcode = am.accttype "
//                    + "and am.acno='" + acctNo + "'").getResultList();
//            if (dataList.isEmpty()) {
//                return "A/c number does not exist";
//            }
//            Vector ele = (Vector) dataList.get(0);
//            if (ele.get(0) == null || ele.get(1) == null) {
//                return "A/c nature and/or status can not be null";
//            }
//
//            String nature = ele.get(0).toString().trim();
//            Integer accPostFlag = Integer.parseInt(ele.get(1).toString().trim());
//            if (nature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                if (accPostFlag == 99) {
//                    return "GL head not in use";
//                }
//            } else {
//                if (accPostFlag == 9) {
//                    return "Account is Closed";
//                } else if (accPostFlag == 8) {
//                    return "Operation Stopped For This Account";
//                } else if (accPostFlag == 4) {
//                    return "Account has been Frozen";
//                } else if (AccountStatusEnum.getAcStatusValue(String.valueOf(accPostFlag)) == null) {
//                    return "Sorry,Invalid Account Status";
//                }
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        return "true";
//    }

    public List<HDFCInwardNEFTReturnPojo> getHDFCInwardNEFTReturn(String toDt) throws ApplicationException {
        List<HDFCInwardNEFTReturnPojo> hdfcInwordNeft = new ArrayList<HDFCInwardNEFTReturnPojo>();
        MbSmsSenderBankDetailDAO smsDao = new MbSmsSenderBankDetailDAO(entityManager);
        List result = new ArrayList();
        try {
            List<MbSmsSenderBankDetail> smsList = smsDao.getBankAndSenderDetail();
            if (smsList.get(0).getBankEmail() == null || smsList.get(0).getBankEmail().equals("")) {
                throw new ApplicationException("Please fill bank email Id in mb_sms_sender_bank_detail table.");
            }
            if (!new Validator().validateEmail(smsList.get(0).getBankEmail())) {
                throw new ApplicationException("Please fill proper email id.");
            }
            result = entityManager.createNativeQuery("select UTR,AMOUNT,DATE_FORMAT(VALUE_DT,'%d-%m-%Y'),SENDER_ACCOUNT,SENDER_NAME,SUBSTR(COALESCE(IW_FILE_NAME,''),1,11),SENDER_ACCOUNT,BENE_ACCOUNT,BENE_NAME,SENDER_IFSC,REASON from \n"
                    + "neft_rtgs_status where dt='" + toDt + "' and status='Unsuccess' and reason<>'THIS UTR ALREADY PROCESSED.'").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector datav = (Vector) result.get(i);
                    HDFCInwardNEFTReturnPojo pojo = new HDFCInwardNEFTReturnPojo();
                    pojo.setTransactionRefNo(datav.get(0).toString());
                    pojo.setAmount(new BigDecimal(datav.get(1).toString()));
                    pojo.setValueDate(datav.get(2).toString());
                    pojo.setRemitterAccountNo(datav.get(3).toString());
                    pojo.setRemitterName(datav.get(4).toString());
                    pojo.setiFSCCode(datav.get(5).toString());
                    pojo.setDebitAccount(datav.get(6).toString());
                    pojo.setBankAccountNumber(datav.get(7).toString());
                    pojo.setBeneficiaryName(datav.get(8).toString());
                    pojo.setRemittanceDetails(datav.get(9).toString());
                    pojo.setOriginatorOfRemmittance(datav.get(10).toString().toUpperCase());
                    pojo.setEmailIDMobnumber(smsList.get(0).getBankEmail());

                    hdfcInwordNeft.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return hdfcInwordNeft;
    }

    @Override
    public String getAxisInwHead(String process, String neftBankName) throws Exception {
        String iwHead = "";
        try {
            List list = entityManager.createNativeQuery("select iw_head from cbs_auto_neft_details where "
                    + "process='" + process + "' and process_type in('IW','BT') and "
                    + "neft_bank_name='" + neftBankName + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                iwHead = ele.get(0).toString();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return iwHead;
    }

    @Override
    public List<NeftRtgsStatus> getUnSuccessEntity(String process, String neftBankName) throws ApplicationException {
        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
        List<NeftRtgsStatus> entityList = new ArrayList<>();
        try {
            entityList = neftRtgsStatusDAO.getUnSuccessEntity(process, neftBankName);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entityList;
    }

    @Override
    public String generateIblIwReturnFiles(List<NeftRtgsMismatchPojo> processList, String leafStatus) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            System.out.println("In IBL Iw Return Processing=========>");
            if (processList.isEmpty() || processList.size() > 1) {
                throw new Exception("Please select a row from table to return.");
            }

            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);

            CbsAutoNeftDetails neftAutoObj = neftOwDetailsDAO.getNeftDetailsByNefBankNameAndProcessAndProcessType("IBL", "AUTO", "BT");
            if (neftAutoObj == null) {
                throw new ApplicationException("Please define auto details for inward return..");
            }

            List<NeftRtgsStatus> returnList = new ArrayList<>();
            NeftRtgsStatus statusObj = neftRtgsStatusDAO.findByUtrNoStatusAndReason(processList.get(0).getUtr(), leafStatus);
            if (statusObj == null) {
                throw new ApplicationException("There is no such inward request to return.");
            }
            returnList.add(statusObj);

            String parentDrAccount = "";

            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(entityManager);
            List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NEFT-OW-DEBIT-ACCOUNT");
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("NEFT-OW-DEBIT-ACCOUNT head is not defined in ABB Parameterinfo");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                parentDrAccount = abbPojo.getAcno();
            }
            List dataList = entityManager.createNativeQuery("select * from cbs_parameterinfo where name "
                    + "in('IBL_CHECKER_ID','IBL_MAKER_ID','IBL_CLIENT_ID')").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("Please define the IBL CHECKER ID,IBL MAKER ID and IBL CLIENT ID.");
            }
            String checkerId = "", makerId = "", clientId = "";
            for (int i = 0; i < dataList.size(); i++) {
                Vector vect = (Vector) dataList.get(i);
                if (vect.get(0).toString().equalsIgnoreCase("IBL_CHECKER_ID")) {
                    checkerId = vect.get(1).toString();
                }

                if (vect.get(0).toString().equalsIgnoreCase("IBL_MAKER_ID")) {
                    makerId = vect.get(1).toString();
                }

                if (vect.get(0).toString().equalsIgnoreCase("IBL_CLIENT_ID")) {
                    clientId = vect.get(1).toString();
                }
            }

            String wsUrl = IblUtil.createIblWsUrl(neftAutoObj.getHostName(), neftAutoObj.getUserName(), neftAutoObj.getPassword());
            PaymentRequest pymtReq;
            PaymentRequest.Transaction txn;
            for (NeftRtgsStatus owObj : returnList) {
                pymtReq = new PaymentRequest();
                txn = new PaymentRequest.Transaction();

                txn.setCustomerRefNum(String.valueOf(owObj.getNeftRtgsStatusPK().getTxnId()));
                String rtgsMinimumAmount = ftsRemote.getCodeFromCbsParameterInfo("RTGS-AMOUNT-LIMIT");
                txn.setTranType(owObj.getAmount().compareTo(new BigDecimal(rtgsMinimumAmount)) == 1 ? "RTGS" : "NEFT");
                txn.setValueDate(ddMMMyyyy.format(new Date()));
                txn.setDebitAccount(parentDrAccount);

                txn.setAmount(owObj.getAmount().toString().trim());
                txn.setBENEACNO(owObj.getSenderAccount().trim());
                txn.setBENEBANK("");
                txn.setBENEBRANCH("");

                txn.setBENEIFSCCODE((owObj.getSenderIfsc() == null || owObj.getSenderIfsc().trim().equals("")) ? "" : owObj.getSenderIfsc().trim());
                txn.setBenName((owObj.getSenderName() == null || owObj.getSenderName().trim().equals("")) ? "" : owObj.getSenderName().trim());
                txn.setBeneEmailId(""); //There were value in outward. So check it

                txn.setBeneMMId("");
                txn.setBeneMobileNo(""); //There were value in outward. So check it
                txn.setCheckerId(checkerId);
                txn.setMakerId(makerId);

                txn.setReserve1("");
                txn.setReserve2("");
                txn.setReserve3("");
                pymtReq.setTransaction(txn);

                String soapInput = IblUtil.createSOAPRequest(IblUtil.createPymtReq(clientId, IblUtil.getXmlStringWithoutPrifix(pymtReq)));
                System.out.println(soapInput);
                //Logging the payment request
                File owLogDirectory = new File(new File(neftAutoObj.getOwLocalFileBackupPath().trim()) + "/"
                        + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + ymd.format(new Date()) + "/");
                if (!owLogDirectory.exists()) {
                    owLogDirectory.mkdirs();
                }

                FileWriter fw = new FileWriter(owLogDirectory + "/" + String.valueOf(owObj.getNeftRtgsStatusPK().getTxnId()) + "_IWRET.txt");
                fw.write(soapInput);
                fw.close();

                Document responseDoc = IblUtil.executeWSOperation(wsUrl, "ProcessTxnInXml", soapInput);
                //Logging payment acknowledgement
                H2HMgmtFacade.xmlDocumentToString(responseDoc, owLogDirectory, String.valueOf(owObj.getNeftRtgsStatusPK().getTxnId()), "ACK");

                NodeList nodeList = responseDoc.getElementsByTagName("PaymentResponse");
                JAXBContext jaxbContext = JAXBContext.newInstance(PaymentResponse.class);

                Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
                PaymentResponse res = (PaymentResponse) unMarshaller.unmarshal(nodeList.item(0));
                System.out.println("Ref no = " + res.getTransaction().getCustomerRefNo());
                System.out.println("IBL Ref no = " + res.getTransaction().getIBLRefNo());
                System.out.println("IBL Status Code = " + res.getTransaction().getStatusCode());
                System.out.println("IBL Status = " + res.getTransaction().getStatusDesc());

                if (res.getTransaction().getStatusCode().equals("R000")) {
                    owObj.setStatus("Sponsor");
                    neftRtgsStatusDAO.update(owObj);
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "success";
    }
}
