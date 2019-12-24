/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.AccountStatusEnum;
import com.cbs.dao.master.ParameterinfoReportDAO;
import com.cbs.dao.neftrtgs.CbsNpciInwardDAO;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.NpciInwardDto;
import com.cbs.dto.other.NpciYojnaTo;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.entity.neftrtgs.CbsNpciInward;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.transaction.UserTransaction;
import siplmatcher.Siplmatcher;

@Stateless(mappedName = "NpciYojnaMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class NpciYojnaMgmtFacade implements NpciYojnaMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ddMMyy = new SimpleDateFormat("ddMMyy");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String uploadingOfYojnaSchemes(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt, String fileName) throws Exception {
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        CbsNpciInwardDAO cbsNpciInwardDAO = new CbsNpciInwardDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Date txnDt = dmy.parse(todayDt);
            String businessDt = ymd.format(txnDt);

            validateACHInward(inputList, businessDt, uploadingUserName, fileName);

            String fileType = fileName.split("-")[4].substring(0, 3); //Based on inward file naming convention.
            //Details of tansaction detail
            String details = "";
            if (fileType.equalsIgnoreCase("CDR")) {
                details = "Customer daily RUPAY";
            } else if (fileType.equalsIgnoreCase("CWR")) {
                details = "Customer weekly RUPAY";
            } else if (fileType.equalsIgnoreCase("CDA")) {
                details = "Customer daily AEPS";
            } else if (fileType.equalsIgnoreCase("CWA")) {
                details = "Customer weekly AEPS";
            }

            String fileTxnDates = "", fileRRNs = "", fileCardAadharNos = "";
            for (int i = 0; i < inputList.size(); i++) {
                NpciInwardDto dto = inputList.get(i);

                String fileTxnDate = ymd.format(ddMMyy.parse(dto.getUserName().trim().substring(0, 6)));
                String fileRRN = dto.getUserName().trim().substring(6).trim();
                if ((fileTxnDates.equals("") && fileRRNs.equals(""))) {
                    fileTxnDates = "\'" + fileTxnDate + "\'";
                    fileRRNs = "\'" + fileRRN + "\'";
                } else {
                    fileTxnDates = fileTxnDates + ",\'" + fileTxnDate + "\'";
                    fileRRNs = fileRRNs + ",\'" + fileRRN + "\'";
                }

                //Customer daily/weekly(CDR/CWR) rupay,Customer daily/weekly(CDA/CWA) aeps
                if (fileType.equalsIgnoreCase("CDR") || fileType.equalsIgnoreCase("CWR")
                        || fileType.equalsIgnoreCase("CDA") || fileType.equalsIgnoreCase("CWA")) {
                    String fileCardAadharNo = dto.getDestBankAcno().trim();
                    if (fileCardAadharNos.equals("")) {
                        fileCardAadharNos = "\'" + fileCardAadharNo + "\'";
                    } else {
                        fileCardAadharNos = fileCardAadharNos + ",\'" + fileCardAadharNo + "\'";
                    }
                }
            }
            //Preparation of accountlist
            List<NpciYojnaTo> accountList = getAccountDetails(fileType, fileTxnDates, fileRRNs, fileCardAadharNos);

            Float trsno = ftsRemote.getTrsNo();
            float recNo = ftsRemote.getRecNo();
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (int i = 0; i < inputList.size(); i++) {
                NpciInwardDto dto = inputList.get(i);

                CbsNpciInward object = new CbsNpciInward();

                object.setApbsTranCode(dto.getApbsTranCode().trim());
                object.setDestBankIin(dto.getDestBankIIN().trim());
                object.setDestActype(dto.getDestAcType().trim());
                object.setLedgerNo(dto.getLedgerNo().trim());
                object.setBeneAadhaarNo(dto.getBeneAadhaarNo().trim());

                object.setBeneName(dto.getBeneName().trim());
                object.setSponsorBankIin(dto.getSponsorBankIIN().trim());
                object.setUserNo(dto.getUserNumber().trim());
                object.setUserNameNarration(dto.getUserName().trim());
                object.setUserCreditReference(dto.getUserCreditRef().trim());

                object.setAmount(dto.getAmount().trim());
                object.setReservedOne("");
                object.setReservedTwo("");
                object.setReservedThree("");
                object.setDestBankAcno(dto.getDestBankAcno().trim());

                object.setReturnReasonCode("");
                object.setTranDate(txnDt);
                object.setTranTime(new Date());
                object.setValueDate(txnDt);

                object.setEnterBy(uploadingUserName.trim());
                object.setAuthBy(uploadingUserName.trim());
                object.setTrsNo(trsno.doubleValue());
                object.setSettlementDate(ymd.parse(dto.getSettlementDt()));
                object.setIwType("ACH");

                object.setAchSettlementCycle(dto.getSettlementCycle().trim());
                object.setAchControl2nd(dto.getControll2nd().trim());
                object.setAchControl5th(dto.getControll5th().trim());
                object.setAchControl7th(dto.getControll7th().trim());
                object.setAchControl8th(dto.getControll8th().trim());

                object.setAchControl10th(dto.getControll10th().trim());
                object.setAchItemSeqNo(dto.getItemSeqNo().trim());
                object.setAchChecksum(dto.getCheckSum().trim());
                object.setAchFiller(dto.getAchFiller().trim());
                object.setAchProductType(dto.getProductType().trim());
                object.setAchUmrn(dto.getUmrn().trim());
                object.setAchReservedFlag(dto.getReservedFlag().trim());
                object.setAchReservedReason(dto.getReservedReason().trim());
                object.setAchHeaderDestIin(dto.getHeaderDestIIN().trim());
                object.setCbsAcno("");
                object.setCbsAcname("");
                object.setFileName(fileName);

                try {
                    int index = getUniqueIdentityIndex(fileType, accountList, dto.getUserName().trim(), dto.getDestBankAcno().trim());
                    if (index == -1) {
                        object.setStatus("U");
                        object.setReason("No Such Account");
                    } else {
                        NpciYojnaTo findObj = accountList.get(index);
                        String acValidateMsg = validateAcNo(findObj, dto.getBeneName().trim());
                        if (!acValidateMsg.equalsIgnoreCase("true")) {
                            object.setStatus("U");
                            object.setReason(acValidateMsg);
                        } else {
                            String winnerAccount = findObj.getAcno().trim();
                            Double winningAmt = Double.parseDouble(formatter.format(new BigDecimal(dto.getAmount().trim()).divide(new BigDecimal("100")).doubleValue()));

                            Integer insertRs = em.createNativeQuery("insert into " + CbsUtil.getReconTableName(findObj.getActNature()) + "(acno,ty,dt,valuedt,dramt,cramt,balance,trantype,"
                                    + " details,iy,instno,instdt,enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,tokenpaidby,subtokenno,trantime,"
                                    + "org_brnid,dest_brnid,tran_id,term_id) values('" + winnerAccount + "',0,'" + businessDt + "','" + businessDt + "',0," + winningAmt + ", 0,2,"
                                    + "'" + details + "',51,'','19000101','" + uploadingUserName + "','Y'," + recNo + ",3,'System'," + trsno + ",73,"
                                    + 0 + ",'','A',now(),'" + uploadBrCode + "','" + winnerAccount.substring(0, 2) + "',0,'')").executeUpdate();

                            if (insertRs <= 0) {
                                throw new ApplicationException("Insertion Problem In Recons For A/c No-->" + winnerAccount);
                            }
                            Integer varupdateReconBalanList = em.createNativeQuery("Update " + CbsUtil.getReconBalanTableName(findObj.getActNature())
                                    + " set balance=ifnull(balance,0) + " + winningAmt + " ,dt=CURRENT_TIMESTAMP where acno='" + winnerAccount
                                    + "'").executeUpdate();
                            if (varupdateReconBalanList <= 0) {
                                throw new ApplicationException("ReconBalan is not updated");
                            }

                            recNo = recNo + 1;
                            object.setStatus("S");
                            object.setReason("");

                            object.setCbsAcno(winnerAccount);
                            String custName = findObj.getCustname().trim();
                            custName = custName.replaceAll("[\\W_]", " ");
                            custName = custName.length() > 100 ? custName.substring(0, 100) : custName;
                            object.setCbsAcname(custName);

                            totalAmount = totalAmount.add(new BigDecimal(winningAmt));

                            //Sms object creation
                            SmsToBatchTo to = new SmsToBatchTo();
                            to.setAcNo(winnerAccount);
                            to.setCrAmt(winningAmt);
                            to.setDrAmt(0d);
                            to.setTranType(2);
                            to.setTy(0);
                            to.setTxnDt(todayDt);
                            to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                            smsBatchList.add(to);
                        }
                    }
                } catch (Exception ex) {
                    object.setStatus("U");
                    object.setReason(ex.getMessage());
                }
                cbsNpciInwardDAO.save(object);
            }
            //GL account transaction
            String glAccount = "";
            Integer code = ftsRemote.getCodeForReportName("NPCI-YOJNA-HEAD");
            if (code == 1) {
                if (fileType.equalsIgnoreCase("CDR") || fileType.equalsIgnoreCase("CWR")) {
                    glAccount = ftsRemote.getCodeFromCbsParameterInfo("RUPAY-YOJNA-HEAD");
                } else if (fileType.equalsIgnoreCase("CDA") || fileType.equalsIgnoreCase("CWA")) {
                    glAccount = ftsRemote.getCodeFromCbsParameterInfo("AEPS-YOJNA-HEAD");
                }
            } else {
                glAccount = ftsRemote.getCodeFromCbsParameterInfo("NPCI-COMMON-YOJNA-HEAD");
            }
            if (glAccount.trim().equals("") || glAccount.trim().length() != 10) {
                throw new Exception("Please define yojna scheme head of 10 digit.");
            }
            glAccount = uploadBrCode + glAccount;

            if (totalAmount.compareTo(new BigDecimal(0)) == 1) {
                int glRs = em.createNativeQuery("insert into gl_recon(acno,ty,dt,valuedt,dramt,cramt,balance,trantype,details,iy,instno, "
                        + "instdt,enterby,auth,recno,payby,authby,trsno,trandesc,tokenno,tokenpaidby,subtokenno,trantime,org_brnid,dest_brnid,"
                        + "tran_id,term_id,adviceno,advicebrncode) values('" + glAccount + "',1,'" + businessDt + "','" + businessDt + "',"
                        + totalAmount.doubleValue() + ",0, 0,2,'" + details + "',51,'','19000101','" + uploadingUserName + "','Y'," + recNo
                        + ",3,'System'," + trsno + ",73,0,'','',now(),'" + uploadBrCode + "','" + uploadBrCode + "',0,'','','')").executeUpdate();
                if (glRs <= 0) {
                    throw new ApplicationException("Problem In Npci Yojna Scheme Entry");
                }
                String message = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0, totalAmount.doubleValue(), "Y", "Y");
                if (!message.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem In Npci Yojna Head Balance Updation.");
                }
            }
            int n = em.createNativeQuery("UPDATE reconvmast SET recno = " + recNo).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in updation in recno");
            }
            ut.commit();
            try {
                smsFacade.sendSmsToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In Npci Yojna Schemes." + e.getMessage());
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
        return "true";
    }

    private void validateACHInward(List<NpciInwardDto> inputList, String businessDt, String uploadingUserName,
            String fileName) throws Exception {
        try {
            if (inputList.isEmpty()) {
                throw new ApplicationException("There is no data found to upload !");
            }

            List list = em.createNativeQuery("select date from cbs_bankdays where date='" + businessDt + "' and "
                    + "daybeginflag = 'Y' and dayendflag = 'N'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Bank has not start the day.");
            }

            list = em.createNativeQuery("select npciusername from securityinfo where userid='" + uploadingUserName + "'").getResultList();
            Vector elem = (Vector) list.get(0);
            if (list.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to upload the files.");
            }

            list = em.createNativeQuery("select ifnull(file_name,'') from cbs_npci_inward where "
                    + "file_name='" + fileName + "'").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("This file has been already uploaded.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private List<NpciYojnaTo> getAccountDetails(String fileType, String fileTxnDates, String fileRRNs,
            String fileCardAadharNos) throws Exception {
        List<NpciYojnaTo> accountList = new ArrayList<NpciYojnaTo>();
        try {
            if (fileType.equalsIgnoreCase("CDR") || fileType.equalsIgnoreCase("CWR")) { //Customer daily/weekly rupay
                List list = em.createNativeQuery("select atm.from_account_number,atm.amount,atm.card_number,"
                        + "date_format(atm.tran_date,'%Y%m%d') as txn_date,atm.system_trace_number,atm.rrn,"
                        + "am.custname,am.accstatus,att.acctnature from atm_normal_transaction_parameter atm,"
                        + "accountmaster am,accounttypemaster att where atm.processing_code in('45','46','49') and "
                        + "lpad(atm.rrn,12,'0') in(" + fileRRNs + ") and atm.card_number in(" + fileCardAadharNos + ") and "
                        + "atm.tran_date in(" + fileTxnDates + ") and atm.in_process_flag='S' and "
                        + "atm.from_account_number=am.acno and att.acctcode = am.accttype").getResultList();
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    String atmTxnDt = ele.get(3).toString();
                    String systemTraceNumber = ele.get(4).toString();

                    List reversalChkList = em.createNativeQuery("select from_account_number from atm_reversal_transaction_parameter "
                            + "where original_system_trace_number='" + systemTraceNumber + "' and tran_date='" + atmTxnDt + "' and "
                            + "in_process_flag='S'").getResultList();
                    if (reversalChkList.isEmpty()) {
                        NpciYojnaTo to = new NpciYojnaTo();
                        to.setAcno(ele.get(0).toString());
                        to.setCustname(ele.get(6).toString());
                        to.setAmount(new BigDecimal(ele.get(1).toString()));
                        to.setCardNoAadharNo(ele.get(2).toString());
                        to.setTxnDt(atmTxnDt);
                        to.setUniqueNo(systemTraceNumber);
                        to.setAccountStatus(Integer.parseInt(ele.get(7).toString()));
                        to.setRrn(ele.get(5).toString());
                        to.setActNature(ele.get(8).toString());

                        accountList.add(to);
                    }
                }
                //For testing checking
//                for (int z = 0; z < accountList.size(); z++) {
//                    NpciYojnaTo to = accountList.get(z);
//                    System.out.println("Acno:-" + to.getAcno() + "::Amount:-" + to.getAmount());
//                }
            } else if (fileType.equalsIgnoreCase("CDA") || fileType.equalsIgnoreCase("CWA")) {  //Customer daily/weekly aeps
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return accountList;
    }

    private int getUniqueIdentityIndex(String fileType, List<NpciYojnaTo> accountList, String fileTxnDtAndRRN,
            String cardNoAadharNo) throws ApplicationException {
        try {
            String fileTxnDt = ymd.format(ddMMyy.parse(fileTxnDtAndRRN.substring(0, 6).trim()));
            String rrn = fileTxnDtAndRRN.substring(6).trim();

            int index = -1;
            for (int i = 0; i < accountList.size(); i++) {
                NpciYojnaTo to = accountList.get(i);
                //Customer daily/weekly(CDR/CWR) rupay, Customer daily/weekly(CDA/CWA) aeps
                if (fileType.equalsIgnoreCase("CDR") || fileType.equalsIgnoreCase("CWR")
                        || fileType.equalsIgnoreCase("CDA") || fileType.equalsIgnoreCase("CWA")) {
                    if (fileTxnDt.equalsIgnoreCase(to.getTxnDt()) && rrn.equalsIgnoreCase(to.getRrn())
                            && cardNoAadharNo.equalsIgnoreCase(to.getCardNoAadharNo())) {
                        index = i;
                        break;
                    }
                }
            }
            return index;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String validateAcNo(NpciYojnaTo findObject, String fileBeneficiaryName) throws ApplicationException {
        try {
            if (findObject == null) {
                return "No Such Account";
            }
            String custname = findObject.getCustname().trim();
            int accountStatus = findObject.getAccountStatus();

            boolean value = new Siplmatcher().equals(custname, fileBeneficiaryName);
            if (value == false) {
                return "Name Differ";
            }
            if (accountStatus == 9) {
                return "Account is Closed";
            } else if (accountStatus == 8) {
                return "Operation Stopped For This Account";
            } else if (accountStatus == 4) {
                return "Account has been Frozen";
            } else if (accountStatus == 2) {
                return "Account has been marked Inoperative";
            } else if (AccountStatusEnum.getAcStatusValue(String.valueOf(accountStatus)) == null) {
                return "Sorry,Invalid Account Status";
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getYojnaSeqNoForSettlementDate(String settlementDt, String function) throws Exception {
        try {
            String[] productArr = getYojnaProductType(function);
            List list = em.createNativeQuery("select distinct trs_no from cbs_npci_inward where "
                    + "settlement_date='" + settlementDt + "' and (substring(file_name,22,3)='" + productArr[0] + "' or "
                    + "substring(file_name,22,3)='" + productArr[1] + "')").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no Seq No on this settlement date.");
            }
            return list;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String[] getYojnaProductType(String function) throws Exception {
        String[] arr = new String[2];
        try {
            String productType1 = "", productType2 = "";
            if (function.equalsIgnoreCase("GENRUPAY") || function.equalsIgnoreCase("SHOWRUPAY")) {
                productType1 = "CDR";   //Customer daily rupay
                productType2 = "CWR";   //Customer weekly rupay
            } else if (function.equalsIgnoreCase("GENAEPS") || function.equalsIgnoreCase("SHOWAEPS")) {
                productType1 = "CDA";   //Customer daily aeps
                productType2 = "CWA";   //Customer weekly aeps
            }
            arr[0] = productType1;
            arr[1] = productType2;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }

    public String generateYojnaReturnFiles(String function, String fileGenDt, Double seqNo, String orgnBrCode,
            String userName, String todayDt) throws Exception {
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + userName + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();

            String[] productArr = getYojnaProductType(function);
            List dataList = em.createNativeQuery("select ach_header_dest_iin,ach_settlement_cycle,dest_actype,"
                    + "ledger_no,bene_name,user_name_narration,amount,ach_item_seq_no,ach_checksum,ach_filler,"
                    + "dest_bank_iin,dest_bank_acno,sponsor_bank_iin,user_no,user_credit_reference,"
                    + "ach_product_type,bene_aadhaar_no,ach_umrn,file_name,status,reason,cbs_acno,cbs_acname "
                    + "from cbs_npci_inward where settlement_date='" + fileGenDt + "' and trs_no=" + seqNo + " and "
                    + "(substring(file_name,22,3)='" + productArr[0] + "' or "
                    + "substring(file_name,22,3)='" + productArr[1] + "')").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the file.");
            }
            //Branchmaster IFSC code
            Map<String, String> ifscMap = getBranchIfscCode();

            //Required Header Values.
            String fileName = "", fileType = "", headerDestIIN = "", settlementCycle = "", headerId = "", detailId = "";
            String aadharLocation = "", bankCode = "", fileNo = "", genFileName = "", totalRecordNo = "";
            for (int h = 0; h < 1; h++) { //Only For First Iteration.
                Vector hVec = (Vector) dataList.get(0);
                headerDestIIN = hVec.get(0).toString();        //Make to desired length
                settlementCycle = hVec.get(1).toString();      //Make to desired length
                fileName = hVec.get(18).toString().trim();
                fileType = fileName.split("-")[4].substring(0, 3);
            }
            //Required Values Extraction.
            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("ACH-HI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::ACH-HI");
            }
            headerId = paramEntity.getCode().toString().trim(); //As it is

            paramEntity = paramDao.getCodeByReportName("ACH-DI");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 2) {
                throw new ApplicationException("Please define code for::ACH-DI");
            }
            detailId = paramEntity.getCode().toString().trim(); //As it is

            list = em.createNativeQuery("select iin,aadhar_location,npci_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
                    || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("")
                    || ele.get(2).toString().trim().equals("")
                    || ele.get(2).toString().trim().length() != 4) {
                throw new ApplicationException("Please define IIN, Aadhar Location and Bank Code.");
            }
            aadharLocation = ele.get(1).toString().trim();
            bankCode = ele.get(2).toString().trim();    //As it is

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + fileGenDt + "' and file_gen_type='" + fileType + "'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();  //Make to 6 digit.
            }
            genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                    + ymdOne.format(ymd.parse(fileGenDt)) + "-" + fileType + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-RTN.txt";

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                    + fileGenDt + "','" + genFileName + "','" + userName + "',now(),'"
                    + orgnBrCode + "','" + fileType + "'," + seqNo + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }

            totalRecordNo = String.valueOf(dataList.size()).trim(); //Make to desired digit.
            BigDecimal totalSubAmt = BigDecimal.ZERO;
            for (int i = 0; i < dataList.size(); i++) {
                ele = (Vector) dataList.get(i);
                BigDecimal individualAmt = new BigDecimal(ele.get(6).toString().trim()).divide(new BigDecimal(100));
                totalSubAmt = totalSubAmt.add(individualAmt);
            }
            String amtInPaisa = "";
            totalSubAmt = totalSubAmt.multiply(new BigDecimal(100));
            int dotIndex = totalSubAmt.toString().indexOf(".");
            if (dotIndex == -1) {
                amtInPaisa = ParseFileUtil.addTrailingZeros(totalSubAmt.toString().trim(), 13);
            } else {
                amtInPaisa = ParseFileUtil.addTrailingZeros(totalSubAmt.toString().substring(0, dotIndex).trim(), 13);
            }
            //Header Preparation.
            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            String header = headerId + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addSuffixSpaces("", 87)
                    + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addTrailingZeros(totalRecordNo, 9)
                    + amtInPaisa + ymdOne.format(ymd.parse(fileGenDt)) + ParseFileUtil.addSuffixSpaces("", 27)
                    + ParseFileUtil.addSuffixSpaces(headerDestIIN, 11) + ParseFileUtil.addTrailingZeros(settlementCycle, 2)
                    + ParseFileUtil.addSuffixSpaces("", 132) + "." + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String txnDtRrn = element.get(5).toString();
                String detailAmt = element.get(6).toString();
                String itemSeqNo = element.get(7).toString();
                String checkSum = element.get(8).toString();
                String destBankIfsc = element.get(10).toString();
                String cardAadhar = element.get(11) == null ? "" : element.get(11).toString().trim();
                String sponserBankIIN = element.get(12).toString();
                String userNumber = element.get(13).toString();
                String uniqueWinIDTxnAmt = element.get(14).toString();
                String productType = element.get(15).toString();
                String aadharNo = element.get(16).toString();
                String status = element.get(19).toString().trim();
                String reason = element.get(20).toString().trim();
                String acno = element.get(21).toString().trim();
                String custname = element.get(22).toString().trim();
                String reservedFlag = "1";  //For Success
                String reasonCode = "00";
                String acnoIfscMobile = "";
                String custNamePartOne = "";
                String custNamePartTwo = "";

                if (status.equalsIgnoreCase("U")) {
                    reservedFlag = "0";     //For Un-Success
                    reasonCode = getReturnReasonCode(reason);
                } else if (status.equalsIgnoreCase("S")) {
                    acnoIfscMobile = ParseFileUtil.addSuffixSpaces(acno, 17) + ParseFileUtil.addSuffixSpaces("", 1)
                            + ParseFileUtil.addSuffixSpaces(ifscMap.get(acno.substring(0, 2)), 11)
                            + ParseFileUtil.addSuffixSpaces("", 11); //We are providing the mobile no

                    //Customer name setting
                    if (custname.length() <= 15) {
                        custNamePartOne = custname;
                        custNamePartTwo = "";
                    } else if (custname.length() > 15) {
                        custNamePartOne = custname.substring(0, 15);
                        String tempName = custname.substring(15).trim();
                        if (tempName.length() <= 8) {
                            custNamePartTwo = tempName;
                        } else if (tempName.length() > 8) {
                            custNamePartTwo = tempName.substring(0, 8);
                        }
                    }
                }
                String individualStr = detailId + ParseFileUtil.addSuffixSpaces("", 9)
                        + ParseFileUtil.addSuffixSpaces("", 2) + ParseFileUtil.addSuffixSpaces("", 3)
                        + ParseFileUtil.addSuffixSpaces(custNamePartOne, 15) + ParseFileUtil.addSuffixSpaces(acnoIfscMobile, 40)
                        + ParseFileUtil.addSuffixSpaces("", 8) + ParseFileUtil.addSuffixSpaces(custNamePartTwo, 8)
                        + ParseFileUtil.addSuffixSpaces(txnDtRrn, 20) + ParseFileUtil.addSuffixSpaces("", 13)
                        + detailAmt + ParseFileUtil.addTrailingZeros(itemSeqNo, 10)
                        + ParseFileUtil.addTrailingZeros(checkSum, 10) + ParseFileUtil.addSuffixSpaces("", 7)
                        + ParseFileUtil.addSuffixSpaces(destBankIfsc, 11) + ParseFileUtil.addSuffixSpaces(cardAadhar, 35)
                        + ParseFileUtil.addSuffixSpaces(sponserBankIIN, 11) + ParseFileUtil.addSuffixSpaces(userNumber, 18)
                        + ParseFileUtil.addSuffixSpaces(uniqueWinIDTxnAmt, 30) + ParseFileUtil.addSuffixSpaces(productType, 3)
                        + ParseFileUtil.addTrailingZeros(aadharNo, 15) + ParseFileUtil.addSuffixSpaces("", 20) + reservedFlag
                        + reasonCode + "\n";

                fw.write(individualStr);
            }
            fw.close();
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

    public String getReturnReasonCode(String cbsMessage) throws ApplicationException {
        String reasonCode = "";
        try {
            cbsMessage = cbsMessage.trim().toLowerCase();
            if (cbsMessage.contains("account is closed")) {
                reasonCode = "01";
            } else if (cbsMessage.contains("no such account")) {
                reasonCode = "02";
            } else if (cbsMessage.contains("name differ")) {
                reasonCode = "65";
            } else if (cbsMessage.contains("account has been frozen")) {
                reasonCode = "68";
            } else if (cbsMessage.contains("operation stopped for this account")
                    || cbsMessage.contains("sorry,invalid account status")) {
                reasonCode = "70";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return reasonCode;
    }

    public Map<String, String> getBranchIfscCode() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List list = em.createNativeQuery("select brncode,ifnull(ifsc_code,'') as ifsc from "
                    + "branchmaster order by brncode").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define branchmaster");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String brncode = ele.get(0).toString().trim();
                brncode = brncode.length() == 2 ? brncode : CbsUtil.lPadding(2, Integer.parseInt(brncode));
                String ifsc = ele.get(1).toString().trim();

                map.put(brncode, ifsc);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return map;
    }

    public List<NpciFileDto> showYojnaFiles(String fileShowDt, Double seqNo) throws Exception {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = em.createNativeQuery("select distinct substring(file_name,22,3) as fileType from "
                    + "cbs_npci_inward where settlement_date='" + fileShowDt + "' and trs_no=" + seqNo + "").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to show the files.");
            }
            Vector ele = (Vector) list.get(0);
            String fileType = ele.get(0).toString().trim();

            list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                    + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + fileShowDt + "' and "
                    + "file_gen_type='" + fileType + "'and seq_no=" + seqNo + "").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                NpciFileDto dto = new NpciFileDto();
                ele = (Vector) list.get(i);

                dto.setFileNo(new BigInteger(ele.get(0).toString()));
                dto.setFileGenDt(ele.get(1).toString());
                dto.setFileName(ele.get(2).toString());
                dto.setFileGenBy(ele.get(3).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }
}
