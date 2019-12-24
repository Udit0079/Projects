/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.NpciReturnConstant;
import com.cbs.dto.master.BillTypeMasterTO;
import com.cbs.dto.npci.cts.FileHeaderDTO;
import com.cbs.dto.npci.cts.ItemDTO;
import com.cbs.dto.npci.cts.reverse.ReturnDTO;
import com.cbs.dto.report.CTSUploadReportPojo;
import com.cbs.dto.report.CtsChequeStatusReportPojo;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ckycr.CkycrCommonMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.ParseFileUtil;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@Stateless(mappedName = "NpciClearingMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class NpciClearingMgmtFacade implements NpciClearingMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private CommonReportMethodsRemote commonReportRemote;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private SmsManagementFacadeRemote smsRemote;
    @EJB
    private CkycrCommonMgmtFacadeRemote ckycrCommonMgmtRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat txnIdDateFormat = new SimpleDateFormat("yyyyMMddhhmmssms");
    SimpleDateFormat dmyOne = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");
    private Map<String, String> transCodeMap = null;
    private Map<String, String> branchDetailMap = null;
    private Map<String, String> micrAndBranchCodeMap = null;

    @PostConstruct
    private void loadConfig() {
        try {
            transCodeMap = getTransCodeMap();
            branchDetailMap = getBranchDetailInMap();
            micrAndBranchCodeMap = getMicrAndBranchCode();
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization" + ex.getMessage());
        }
    }

    public Integer getCbsGeneratedScheduleNo(String curDt, String branchCode, String clearingType) throws Exception {
        try {
            List list = em.createNativeQuery("select ifnull(max(schedule_no),0)+1 from cts_clg_in_entry "
                    + "where dt='" + curDt + "' and orgn_branch='" + branchCode + "' and "
                    + "em_flag='" + clearingType + "'").getResultList();
            Vector element = (Vector) list.get(0);
            return Integer.parseInt(element.get(0).toString());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public boolean isFileUploaded(String fileType, String fileName) throws Exception {
        boolean result = false;
        try {
            List list = null;
            if (fileType.equalsIgnoreCase("xml")) {
                list = em.createNativeQuery("select txn_id from cts_clg_in_entry where "
                        + "binary_data_file_name='" + fileName + "'").getResultList();
            } else if (fileType.equalsIgnoreCase("img")) {
                list = em.createNativeQuery("select txn_id from cts_clg_in_entry where "
                        + "binary_img_file_name='" + fileName + "'").getResultList();
            }
            if (!list.isEmpty()) {
                result = true;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    @Override
    public String uploadClearingData(FileHeaderDTO pxfDTO, String xmlFileName, String imgFileName,
            Integer scheduleNo, String requestAddr, String userName, String todayDt, String orgnCode) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            //Map of total instrument of which detail is matched
            Map<Integer, String> ifMap = new HashMap<Integer, String>();
            int mapSeqNo = 0;
            //PO Detail
            BillTypeMasterTO billTO = getBillTypeDetail("PO");
            int vchNo = 0;

            String ctsInsertQuery = "insert into cts_clg_in_entry(txn_id,batch_no,acno,inst_no,inst_amt,inst_dt,"
                    + "favor_of,bank_name,bank_add,remarks,reason_for_cancel,enter_by,auth_by,status,seq_no,"
                    + "orgn_branch,dest_branch,dt,tran_time,pay_by,iy,screen_flag,details,em_flag,vch_no,"
                    + "second_auth_by,auth,circle_type,substatus,img_code,custname,oper_mode,userdetails,"
                    + "prbankcode,rbirefno,schedule_no,fh_creation_date,fh_file_id,fh_settlement_date,"
                    + "item_payor_bank_routno,item_seq_no,item_trans_code,item_san,binary_data_file_name,"
                    + "binary_img_file_name,fh_vno,fh_test_file_indicator,item_prment_date,item_cycleno,"
                    + "addenda_bofdroutno,addenda_bofdbusdate,addenda_depositoracct,addenda_ifsc,modified_flag,"
                    + "doc_type,image_ref_data) values";
            String ctsDataQuery = "";
            for (int x = 0; x < pxfDTO.getItems().size(); x++) { //For all items
                vchNo = vchNo + 1;
                ItemDTO item = pxfDTO.getItems().get(x);
                System.out.println("Item Seq No Is-->" + item.getItemSeqNo());

                String[] actDetail = {};
                
                if (ftsRemote.getCodeForReportName("CTS-BY-MICR") == 1) {
                    actDetail = getMICRDetail(item.getTransCode(), item.getSerialNo(), billTO, item.getPayorBankRoutNo().trim());
                } else {
                    actDetail = getSANDetail(item.getTransCode(), item.getAccountNo(), item.getSerialNo(), billTO, item.getPayorBankRoutNo());
                }

                int status = 1;
                String details = "Uploaded";
                if (actDetail[5] != null && !actDetail[5].equals("") && (actDetail[5].equalsIgnoreCase("SB")
                        || actDetail[5].equalsIgnoreCase("CA")) && (actDetail[0] != null && !actDetail[0].equals(""))) {
                    if (actDetail[6].equalsIgnoreCase("S")) {
                        status = 3;
                        details = "Stop Payment";
                    } else if (actDetail[6].equalsIgnoreCase("U")) {
                        status = 3;
                        details = "Already Used";
                    } else if (isBalanceIF(actDetail[0], item.getAmount(), userName).equalsIgnoreCase("IF")) {
                        status = 3;
//                        details = "Balance Insufficient";
                        details = "Balance Exceeds";
                        mapSeqNo = mapSeqNo + 1;
                        ifMap.put(mapSeqNo, actDetail[0] + ":" + item.getSerialNo().trim() + ":" + item.getAmount().toString());
                    }
                }

                String instDt = actDetail[4].equals("") ? ymd.format(dmy.parse(item.getPresentmentDate())) : actDetail[4];
                String destBranchCode = "";
                if (ftsRemote.getCodeForReportName("CTS-BY-MICR") == 1) {
                    destBranchCode = getMicrBrCode(item.getPayorBankRoutNo().trim());
                } else {
                    destBranchCode = "0" + Integer.parseInt(item.getAccountNo().substring(0, 1));
                    if ((actDetail[0] != null && !actDetail[0].equals("")) && actDetail[0].substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                        destBranchCode = actDetail[0].substring(0, 2);
                    }
                }

                if (ctsDataQuery.equals("")) {
                    ctsDataQuery = "('" + requestAddr + txnIdDateFormat.format(new Date()) + String.valueOf(x) + "',0,"
                            + "'" + actDetail[0] + "','" + item.getSerialNo() + "'," + item.getAmount() + ",'" + instDt + "',"
                            + "'" + item.getUserField() + "','','','',0,'" + userName + "',''," + status + ","
                            + "" + Double.parseDouble(actDetail[3]) + ",'" + orgnCode + "','" + destBranchCode + "',"
                            + "'" + ymd.format(new Date()) + "',current_timestamp,1,0,'C','" + details + "',"
                            + "'" + item.getClearingType() + "'," + vchNo + ",'CTS_USER','','A','U',"
                            + "'" + item.getItemSeqNo() + "','" + actDetail[1] + "','" + actDetail[2] + "','ENTERED',"
                            + "'" + item.getPresentingBankRoutNo() + "','" + item.getItemSeqNo() + "'," + scheduleNo + ","
                            + "'" + ymd.format(dmy.parse(pxfDTO.getCreationDate())) + "','" + pxfDTO.getFileId() + "',"
                            + "'" + ymd.format(dmy.parse(pxfDTO.getSettlementDate())) + "','" + item.getPayorBankRoutNo() + "',"
                            + "'" + item.getItemSeqNo() + "','" + item.getTransCode() + "','" + item.getAccountNo() + "',"
                            + "'" + xmlFileName + "','" + imgFileName + "','" + pxfDTO.getVersionNo() + "',"
                            + "'" + pxfDTO.getTestFileIndicator() + "','" + ymd.format(dmy.parse(item.getPresentmentDate())) + "',"
                            + "'" + item.getCycleNo() + "','" + item.getAddendABofdRoutNo() + "',"
                            + "'" + ymd.format(dmy.parse(item.getAddendABofdBusDate())) + "',"
                            + "'" + item.getAddendADepositorAcct() + "','" + item.getAddendAIfsc() + "','','" + item.getDocType() + "'"
                            + ",'" + item.getItemImages().get(0).getImageReferenceData() + "')";
                } else {
                    ctsDataQuery = ctsDataQuery + "," + "('" + requestAddr + txnIdDateFormat.format(new Date())
                            + String.valueOf(x) + "',0,'" + actDetail[0] + "','" + item.getSerialNo() + "',"
                            + "" + item.getAmount() + ",'" + instDt + "','" + item.getUserField() + "','','','',0,"
                            + "'" + userName + "',''," + status + "," + Double.parseDouble(actDetail[3]) + ","
                            + "'" + orgnCode + "','" + destBranchCode + "','" + ymd.format(new Date()) + "',current_timestamp,"
                            + "1,0,'C','" + details + "','" + item.getClearingType() + "'," + vchNo + ",'CTS_USER','','A',"
                            + "'U','" + item.getItemSeqNo() + "','" + actDetail[1] + "','" + actDetail[2] + "','ENTERED',"
                            + "'" + item.getPresentingBankRoutNo() + "','" + item.getItemSeqNo() + "'," + scheduleNo + ","
                            + "'" + ymd.format(dmy.parse(pxfDTO.getCreationDate())) + "','" + pxfDTO.getFileId() + "',"
                            + "'" + ymd.format(dmy.parse(pxfDTO.getSettlementDate())) + "','" + item.getPayorBankRoutNo() + "',"
                            + "'" + item.getItemSeqNo() + "','" + item.getTransCode() + "','" + item.getAccountNo() + "',"
                            + "'" + xmlFileName + "','" + imgFileName + "','" + pxfDTO.getVersionNo() + "',"
                            + "'" + pxfDTO.getTestFileIndicator() + "','" + ymd.format(dmy.parse(item.getPresentmentDate())) + "',"
                            + "'" + item.getCycleNo() + "','" + item.getAddendABofdRoutNo() + "',"
                            + "'" + ymd.format(dmy.parse(item.getAddendABofdBusDate())) + "',"
                            + "'" + item.getAddendADepositorAcct() + "','" + item.getAddendAIfsc() + "','','" + item.getDocType() + "'"
                            + ",'" + item.getItemImages().get(0).getImageReferenceData() + "')";
                }
            }
            //Inserting all cts data
            ctsInsertQuery = ctsInsertQuery + ctsDataQuery;
            int n = em.createNativeQuery(ctsInsertQuery).executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in data uploading");
            }
            //Sending Balance IF SMS
            try {
                sendingIFSms(ifMap, userName);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Problem in sending the balance IF sms.");
            }
            ut.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return "true";
    }

    public void sendingIFSms(Map ifMap, String userName) throws Exception {
        try {
            List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
            String templateBankName = bankTo.get(0).getTemplateBankName().trim();

            Set<Entry<Integer, String>> set = ifMap.entrySet();
            Iterator<Entry<Integer, String>> it = set.iterator();
            while (it.hasNext()) {
                Entry entry = it.next();
                String[] arr = entry.getValue().toString().split(":");
                System.out.println("A/c-->" + arr[0] + "    " + "ChqNo-->" + arr[1] + "Amount-->" + arr[2]);

                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                tSmsRequestTo.setMsgType("PAT");
                tSmsRequestTo.setTemplate(SmsType.CTS_CHEQUE);
                tSmsRequestTo.setAcno(arr[0].trim());
                tSmsRequestTo.setPromoMobile(getMobileByAccount(arr[0].trim()));
                tSmsRequestTo.setBankName(templateBankName);
                tSmsRequestTo.setFirstCheque(arr[1].trim());
                tSmsRequestTo.setAmount(Double.parseDouble(arr[2]));
                smsRemote.sendSms(tSmsRequestTo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public String isBalanceIF(String acno, double amount, String userName) throws Exception {
        String result = "NOT-IF";
        if (ftsRemote.getAccountNature(acno).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            result = ftsRemote.checkBalForOdLimit(acno, amount, userName); //1
        } else if (ftsRemote.getAccountNature(acno).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
            result = ftsRemote.checkBalance(acno, amount, userName); //true
        }
        if (!(result.equalsIgnoreCase("1") || result.equalsIgnoreCase("true"))) {
            result = "IF";
        }
        return result;
    }

    public String getMobileByAccount(String accountNo) throws Exception {
        String mobileNo = "";
        try {
            List smsData = em.createNativeQuery("select mobile_no from mb_subscriber_tab where acno='" + accountNo + "' and "
                    + "status=1 and auth='Y' and auth_status='V'").getResultList();
            if (!smsData.isEmpty()) {
                Vector smsEle = (Vector) smsData.get(0);
                mobileNo = smsEle.get(0).toString().trim();
            } else {
                smsData = em.createNativeQuery("select ifnull(mobilenumber,'') as mobile from "
                        + "cbs_customer_master_detail cu,customerid id where cu.customerid=id.custid "
                        + "and id.acno='" + accountNo + "'").getResultList();
                Vector smsEle = (Vector) smsData.get(0);
                mobileNo = "+91" + smsEle.get(0).toString().trim();
            }
            mobileNo = mobileNo.trim().length() != 13 ? "" : mobileNo.trim();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return mobileNo;
    }

    @Override
    public String[] getMICRDetail(String transCode, String serialNo, BillTypeMasterTO billTO, String payorBankRoutNo) throws Exception {
        String[] sanDetail = new String[7];
        List acctDetail = new ArrayList();
        String nature = transCodeMap.get(transCode);
        String micrBrCode =getMicrBrCode(payorBankRoutNo);
        
        if (nature != null && nature.equalsIgnoreCase("SB")) {
            acctDetail = em.createNativeQuery("select a.acno,ifnull(a.custname,'') as CustName,c.description,q.statusflag from accountmaster a, "
                    + "codebook c,chbook_sb q where a.acno=q.acno and a.curbrcode ='" + micrBrCode
                    + "' and q.chqno='" + serialNo + "' and c.groupcode=4 and a.opermode=c.code").getResultList();

        } else if (nature != null && nature.equalsIgnoreCase("CA")) {

            acctDetail = em.createNativeQuery("select a.acno,ifnull(a.custname,'') as CustName,c.description,q.statusflag from accountmaster a, "
                    + "codebook c,chbook_ca q where a.acno=q.acno and a.curbrcode ='" + micrBrCode
                    + "' and q.chqno='" + serialNo + "' and c.groupcode=4 and a.opermode=c.code").getResultList();

        } else if (nature != null && nature.equalsIgnoreCase("PO")) {

            String poHead = micrBrCode + billTO.getGlHead() + "01";

            acctDetail = em.createNativeQuery("select ifnull(b.acno,''),ifnull(b.custname,''),b.seqno,"
                    + "date_format(validationdt,'%Y%m%d') from bill_po b,chbook_bill c where b.billtype=c.inst_type and "
                    + "b.instno=c.ddno and cast(c.brncode as unsigned)=cast(substring(b.acno,1,2) as unsigned) and "
                    + "c.inst_type='PO' and c.statusflag='U' and c.ddno='" + serialNo + "' and c.brncode in(" + micrBrCode + ") "
                    + "and b.acno ='" + poHead + "'").getResultList();
        }

        String txnAcno = "", custName = "", operMode = "", seqNo = "0.0", validationDt = "", varNature = "", chqStatus = "";
        if (!acctDetail.isEmpty()) {
            Vector element = (Vector) acctDetail.get(0);
            if (nature != null && (nature.equalsIgnoreCase("SB") || nature.equalsIgnoreCase("CA"))) {
                txnAcno = element.get(0).toString();
                custName = element.get(1).toString();
                custName = custName.replaceAll("[\\W_]", " ");
                custName = custName.length() > 100 ? custName.substring(0, 100) : custName;
                operMode = element.get(2).toString();
                seqNo = "0.0";
                validationDt = "";
                varNature = nature;
                chqStatus = element.get(3).toString();
            } else if (nature != null && nature.equalsIgnoreCase("PO")) {
                txnAcno = element.get(0).toString();
                custName = element.get(1).toString();
                operMode = "";
                seqNo = element.get(2).toString();
                validationDt = element.get(3).toString();
            }
        }

        sanDetail[0] = txnAcno;
        sanDetail[1] = custName;
        sanDetail[2] = operMode;
        sanDetail[3] = seqNo;
        sanDetail[4] = validationDt;
        sanDetail[5] = varNature;
        sanDetail[6] = chqStatus;

        return sanDetail;
    }

    private String getMicrBrCode(String payorBankRoutNo)throws ApplicationException{
        String micrBrCode = "";
        Set<Entry<String, String>> set = micrAndBranchCodeMap.entrySet();
        Iterator<Entry<String, String>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            String value = entry.getValue().trim();
            String[] keyArr = entry.getKey().trim().split(":");
            if (keyArr[1].trim().equals(payorBankRoutNo)) {
                micrBrCode = value;
                break;
            }
        }
        return micrBrCode;
    }
    
    @Override
    public String[] getSANDetail(String transCode, String san, String serialNo, BillTypeMasterTO billTO, String payorBankRoutNo) throws Exception {
        String[] sanDetail = new String[7];
        List acctDetail = new ArrayList();
        String nature = transCodeMap.get(transCode);

        if (nature != null && nature.equalsIgnoreCase("SB")) {
            acctDetail = em.createNativeQuery("select a.acno,ifnull(a.custname,'') as CustName,c.description,q.statusflag "
                    + "from accountmaster a,codebook c,chbook_sb q where a.acno=q.acno and cast(a.curbrcode as "
                    + "unsigned)=" + Integer.parseInt(san.substring(0, 1)) + " and q.chqno='" + serialNo + "' "
                    + "and q.chqsrno='" + san.trim() + "' and c.groupcode=4 and a.opermode=c.code").getResultList();
        } else if (nature != null && nature.equalsIgnoreCase("CA")) {

            acctDetail = em.createNativeQuery("select a.acno,ifnull(a.custname,'') as CustName,c.description,q.statusflag "
                    + "from accountmaster a,codebook c,chbook_ca q where a.acno=q.acno and cast(a.curbrcode as "
                    + "unsigned)=" + Integer.parseInt(san.substring(0, 1)) + " and q.chqno='" + serialNo + "' "
                    + "and q.chqsrno='" + san.trim() + "' and c.groupcode=4 and a.opermode=c.code").getResultList();
        } else if (nature != null && nature.equalsIgnoreCase("PO")) {
            String micrBrnCode = "", poHead = "";
            Set<Entry<String, String>> set = micrAndBranchCodeMap.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, String> entry = it.next();
                String value = entry.getValue().trim();
                if (entry.getKey().trim().contains(payorBankRoutNo.trim())) {
                    if (micrBrnCode.equals("") && poHead.equals("")) {
                        micrBrnCode = value;
                        poHead = value + billTO.getGlHead() + "01";
                    } else if (!micrBrnCode.equals("") && !poHead.equals("")) {
                        micrBrnCode = micrBrnCode + "," + value;
                        poHead = poHead + "," + value + billTO.getGlHead() + "01";
                    }
                }
            }
            acctDetail = em.createNativeQuery("select ifnull(b.acno,''),ifnull(b.custname,''),b.seqno,"
                    + "date_format(validationdt,'%Y%m%d') from bill_po b,chbook_bill c where b.billtype=c.inst_type and "
                    + "b.instno=c.ddno and cast(c.brncode as unsigned)=cast(substring(b.acno,1,2) as unsigned) and "
                    + "c.inst_type='PO' and c.statusflag='U' and c.ddno='" + serialNo + "' and c.brncode in(" + micrBrnCode + ") "
                    + "and c.series='" + san.trim() + "' and b.acno in(" + poHead + ")").getResultList();
        }

        String txnAcno = "", custName = "", operMode = "", seqNo = "0.0", validationDt = "", varNature = "", chqStatus = "";
        if (!acctDetail.isEmpty()) {
            Vector element = (Vector) acctDetail.get(0);
            if (nature != null && (nature.equalsIgnoreCase("SB") || nature.equalsIgnoreCase("CA"))) {
                txnAcno = element.get(0).toString();
                custName = element.get(1).toString();
                custName = custName.replaceAll("[\\W_]", " ");
                custName = custName.length() > 100 ? custName.substring(0, 100) : custName;
                operMode = element.get(2).toString();
                seqNo = "0.0";
                validationDt = "";
                varNature = nature;
                chqStatus = element.get(3).toString();
            } else if (nature != null && nature.equalsIgnoreCase("PO")) {
                txnAcno = element.get(0).toString();
                custName = element.get(1).toString();
                operMode = "";
                seqNo = element.get(2).toString();
                validationDt = element.get(3).toString();
            }
        }

        sanDetail[0] = txnAcno;
        sanDetail[1] = custName;
        sanDetail[2] = operMode;
        sanDetail[3] = seqNo;
        sanDetail[4] = validationDt;
        sanDetail[5] = varNature;
        sanDetail[6] = chqStatus;

        return sanDetail;
    }

    public List getAllScheduleNos(String dt, String branchCode, String clearingType) throws Exception {
        try {
            return em.createNativeQuery("select distinct schedule_no from cts_clg_in_entry "
                    + "where dt='" + dt + "' and orgn_branch='" + branchCode + "' and "
                    + "em_flag='" + clearingType + "'").getResultList();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public BillTypeMasterTO getBillTypeDetail(String billCode) throws Exception {
        BillTypeMasterTO billTypeMasterTO = new BillTypeMasterTO();

        List list = em.createNativeQuery("select glhead,instdesc from billtypemaster where "
                + "instcode='" + billCode + "'").getResultList();
        Vector ele = (Vector) list.get(0);

        billTypeMasterTO.setGlHead(ele.get(0).toString());
        billTypeMasterTO.setInstDesc(ele.get(1).toString());

        return billTypeMasterTO;
    }

    public List<CtsChequeStatusReportPojo> getctsUploadDataToEdit(String date, String chequeType, String scheduleNo) throws ApplicationException {
        List< CtsChequeStatusReportPojo> pojoList = new ArrayList<CtsChequeStatusReportPojo>();
        try {
            String query = "select ACNO,CUSTNAME,INST_NO,INST_AMT,date_format(INST_DT,'%d/%m/%Y'),FAVOR_OF,"
                    + " TXN_ID,BATCH_NO,ITEM_SAN,EM_FLAG,DT,SCHEDULE_NO,ITEM_SEQ_NO,MODIFIED_FLAG,ITEM_TRANS_CODE,ITEM_PAYOR_BANK_ROUTNO, "
                    + " img_code from cts_clg_in_entry "
                    + " where dt='" + ymd.format(dmyOne.parse(date)) + "' and em_flag='" + chequeType + "' "
                    + " and SCHEDULE_NO='" + scheduleNo + "' and status in ('1','3') order by INST_NO ";
            List list = em.createNativeQuery(query).getResultList();
            for (int i = 0; i < list.size(); i++) {
                CtsChequeStatusReportPojo pojo = new CtsChequeStatusReportPojo();
                Vector ele = (Vector) list.get(i);
                pojo.setAcno(ele.get(0).toString().trim());
                pojo.setCustName(ele.get(1).toString().trim());
                pojo.setInstNo(ele.get(2).toString().trim());
                pojo.setInstAmt(ele.get(3).toString().trim());
                pojo.setInstDt(ele.get(4).toString().trim());
                pojo.setFavourOf(ele.get(5).toString().trim());
                pojo.setTxnId(ele.get(6).toString().trim());
                pojo.setBatchNo(new BigInteger(ele.get(7).toString().trim()));
                pojo.setItemSAN(ele.get(8).toString().trim());
                pojo.setEmFlag(ele.get(9).toString().trim()); //as checqe Type
                pojo.setDt(ele.get(10).toString().trim());
                pojo.setScheduleNo(ele.get(11).toString().trim());
                pojo.setItemSeqNo(ele.get(12).toString().trim());
                pojo.setModifyFlag(ele.get(13).toString().trim());
                pojo.setItemTransCode(ele.get(14).toString().trim());
                pojo.setItemPayorBnkRoutNo(ele.get(15).toString().trim());
                pojo.setImageCode(ele.get(16).toString().trim());
                pojoList.add(pojo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return pojoList;
    }

    public String updateCTSUploadData(CtsChequeStatusReportPojo ctsUploadData) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String selectQuery = "select TXN_ID, BATCH_NO, ACNO, INST_NO, INST_AMT, INST_DT, FAVOR_OF, BANK_NAME,"
                    + " BANK_ADD, REMARKS, REASON_FOR_CANCEL, ENTER_BY, AUTH_BY, STATUS, SEQ_NO, ORGN_BRANCH, DEST_BRANCH,"
                    + " DT, TRAN_TIME, PAY_BY, IY, SCREEN_FLAG, DETAILS, EM_FLAG, VCH_NO, SECOND_AUTH_BY, AUTH, CIRCLE_TYPE,"
                    + " SUBSTATUS, IMG_CODE, CUSTNAME, OPER_MODE, USERDETAILS, PRBANKCODE, rbirefno, SCHEDULE_NO, "
                    + " FH_CREATION_DATE, FH_FILE_ID, FH_SETTLEMENT_DATE, ITEM_PAYOR_BANK_ROUTNO, ITEM_SEQ_NO, ITEM_TRANS_CODE,"
                    + " ITEM_SAN, BINARY_DATA_FILE_NAME, BINARY_IMG_FILE_NAME, FH_VNO, FH_TEST_FILE_INDICATOR, ITEM_PRMENT_DATE,"
                    + " ITEM_CYCLENO, ADDENDA_BOFDROUTNO, ADDENDA_BOFDBUSDATE, ADDENDA_DEPOSITORACCT, ADDENDA_IFSC, MODIFIED_FLAG, DOC_TYPE"
                    + " from cts_clg_in_entry  where TXN_ID='" + ctsUploadData.getTxnId() + "' and BATCH_NO='" + ctsUploadData.getBatchNo() + "'";
            List list = em.createNativeQuery(selectQuery).getResultList();
            String hisInsertQuery = "";
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    hisInsertQuery = "INSERT INTO cts_clg_in_entry_history (TXN_ID, BATCH_NO, ACNO, INST_NO, INST_AMT, INST_DT,"
                            + " FAVOR_OF, BANK_NAME, BANK_ADD, REMARKS, REASON_FOR_CANCEL, ENTER_BY, AUTH_BY, STATUS,"
                            + " SEQ_NO, ORGN_BRANCH, DEST_BRANCH, DT, TRAN_TIME, PAY_BY, IY, SCREEN_FLAG, DETAILS,"
                            + " EM_FLAG, VCH_NO, SECOND_AUTH_BY, AUTH, CIRCLE_TYPE, SUBSTATUS, IMG_CODE, CUSTNAME, OPER_MODE,"
                            + " USERDETAILS, PRBANKCODE, rbirefno, SCHEDULE_NO, FH_CREATION_DATE, FH_FILE_ID, FH_SETTLEMENT_DATE,"
                            + " ITEM_PAYOR_BANK_ROUTNO, ITEM_SEQ_NO, ITEM_TRANS_CODE, ITEM_SAN, BINARY_DATA_FILE_NAME, "
                            + " BINARY_IMG_FILE_NAME, FH_VNO, FH_TEST_FILE_INDICATOR, ITEM_PRMENT_DATE, ITEM_CYCLENO, "
                            + " ADDENDA_BOFDROUTNO, ADDENDA_BOFDBUSDATE, ADDENDA_DEPOSITORACCT, ADDENDA_IFSC, MODIFIED_FLAG, DOC_TYPE)"
                            + " VALUES('" + ele.get(0) + "','" + ele.get(1) + "','" + ele.get(2) + "',"
                            + "'" + ele.get(3) + "','" + ele.get(4) + "','" + ele.get(5) + "','" + ele.get(6) + "','" + ele.get(7) + "',"
                            + "'" + ele.get(8) + "','" + ele.get(9) + "','" + ele.get(10) + "','" + ele.get(11) + "','" + ele.get(12) + "',"
                            + "'10','" + ele.get(14) + "','" + ele.get(15) + "','" + ele.get(16) + "','" + ele.get(17) + "',"
                            + "'" + ele.get(18) + "','" + ele.get(19) + "','" + ele.get(20) + "','" + ele.get(21) + "','" + ele.get(22) + "',"
                            + "'" + ele.get(23) + "','" + ele.get(24) + "','" + ele.get(25) + "','" + ele.get(26) + "',"
                            + "'" + ele.get(27) + "','" + ele.get(28) + "','" + ele.get(29) + "','" + ele.get(30) + "','" + ele.get(31) + "',"
                            + "'" + ele.get(32) + "','" + ele.get(33) + "','" + ele.get(34) + "','" + ele.get(35) + "','" + ele.get(36) + "',"
                            + "'" + ele.get(37) + "'," + "'" + ele.get(38) + "','" + ele.get(39) + "','" + ele.get(40) + "','" + ele.get(41) + "',"
                            + "'" + ele.get(42) + "','" + ele.get(43) + "','" + ele.get(44) + "','" + ele.get(45) + "','" + ele.get(46) + "'"
                            + ",'" + ele.get(47) + "','" + ele.get(48) + "','" + ele.get(49) + "','" + ele.get(50) + "','" + ele.get(51) + "',"
                            + "'" + ele.get(52) + "','" + ele.get(53) + "','" + ele.get(54) + "');";
                    int k = em.createNativeQuery(hisInsertQuery).executeUpdate();
                    if (k <= 0) {
                        throw new ApplicationException("Problem in insertion in History.");
                    }
                    int n = em.createNativeQuery("UPDATE cts_clg_in_entry SET ITEM_SAN='" + ctsUploadData.getItemSAN() + "',"
                            + " INST_NO='" + ctsUploadData.getInstNo() + "',INST_DT='" + new SimpleDateFormat("yyyy-MM-dd").format(dmyOne.parse(ctsUploadData.getInstDt())) + "',"
                            + " FAVOR_OF='" + ctsUploadData.getFavourOf() + "' ,ENTER_BY='" + ctsUploadData.getEnterBy() + "', "
                            + " ACNO='" + ctsUploadData.getAcno() + "', SEQ_NO='" + ctsUploadData.getSeqNo() + "',"
                            + " CUSTNAME='" + ctsUploadData.getCustName() + "',"
                            + " OPER_MODE='" + ctsUploadData.getOperMode() + "',DEST_BRANCH='" + ctsUploadData.getDestBranch() + "',"
                            + " MODIFIED_FLAG='Y',TRAN_TIME=now() ,ITEM_TRANS_CODE='" + ctsUploadData.getItemTransCode() + "', "
                            + " ITEM_PAYOR_BANK_ROUTNO='" + ctsUploadData.getItemPayorBnkRoutNo() + "' WHERE TXN_ID='" + ctsUploadData.getTxnId() + "' and BATCH_NO='" + ctsUploadData.getBatchNo() + "'"
                            + " and status in ('1','3')").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem updation.");
                    }
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
        return "Data Successfully Updated";
    }

    public List<CTSUploadReportPojo> getctsUploadDataToReport(String date, String chequeType, String scheduleNo, String branchCode) throws ApplicationException {
        List<CTSUploadReportPojo> dataList = new ArrayList<CTSUploadReportPojo>();
        try {
            String query = "select  branchcode, count(branchcode) as count,sum(INST_AMT) as amt, branchName from("
                    + "select ifnull(substring(acno,1,2),'') as branchcode,INST_AMT,ifnull(BranchName,'')"
                    + " ,IF(ifnull(substring(acno,1,2),'')='','No Match Found',BranchName) as branchName from cts_clg_in_entry"
                    + " left join branchmaster on ifnull(substring(acno,1,2),'')= LPAD(BrnCode,2,'0') where dt='" + ymd.format(dmyOne.parse(date)) + "' "
                    + " and SCHEDULE_NO='" + scheduleNo + "' and EM_FLAG='" + chequeType + "' and orgn_branch='" + branchCode + "'"
                    + " )as t GROUP BY branchcode;";
            List list = em.createNativeQuery(query).getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                CTSUploadReportPojo data = new CTSUploadReportPojo();
                data.setBranchCode(ele.get(0).toString().trim());
                data.setCount(ele.get(1).toString().trim());
                data.setSumOfAmt(new BigDecimal(ele.get(2).toString().trim()));
                data.setBranchName(ele.get(3).toString().trim());
                dataList.add(data);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public List<ReturnDTO> getCtsReturnData(String fileGenDt, String clgType, String scheduleNo, String orgnCode) throws Exception {
        List<ReturnDTO> returnData = new ArrayList<ReturnDTO>();
        try {
            List returnDataList = em.createNativeQuery("select txn_id,ifnull(acno,'') as acno,ifnull(inst_no,'') as chequeNo,"
                    + "inst_amt,date_format(inst_dt,'%d%m%Y'),ifnull(favor_of,'') as favourof,ifnull(em_flag,'') "
                    + "as clearingType,ifnull(custname,'') as custname,ifnull(oper_mode,'') as operMode,"
                    + "ifnull(userdetails,'') as userDetails,ifnull(prbankcode,'') as prBankCode,"
                    + "date_format(fh_creation_date,'%d%m%Y') as fhCreationDt,ifnull(fh_file_id,'') "
                    + "as fileId,date_format(fh_settlement_date,'%d%m%Y') as fhSettlementDt,"
                    + "ifnull(item_payor_bank_routno,'') as itemPayorBankIfsc,ifnull(item_seq_no,'') as itemSeqNo,"
                    + "ifnull(item_trans_code,'') as itemTransCode,ifnull(item_san,'') as itemSan,"
                    + "ifnull(binary_data_file_name,'') as binaryDataFileName,ifnull(fh_vno,'') as fhVNo,"
                    + "ifnull(fh_test_file_indicator,'') as testIndicator,date_format(item_prment_date,'%d%m%Y') "
                    + "as presentMentDt,ifnull(item_cycleno,'') as itemCycleNo,ifnull(addenda_bofdroutno,''),"
                    + "date_format(addenda_bofdbusdate,'%d%m%Y'),ifnull(addenda_depositoracct,''),ifnull(addenda_ifsc,''),"
                    + "ifnull(modified_flag,'') as modified_flag,ifnull(details,''),ifnull(schedule_no,0),date_format(dt,'%Y%m%d') "
                    + "from cts_clg_in_entry where dt='" + fileGenDt + "' and em_flag='" + clgType + "' and "
                    + "schedule_no=" + Integer.parseInt(scheduleNo) + " and status=4 and "
                    + "orgn_branch='" + orgnCode + "'").getResultList();
            if (returnDataList.isEmpty()) {
                throw new Exception("There is no data to generate the return file");
            }
            Vector mainElement = null;
            for (int i = 0; i < returnDataList.size(); i++) {
                mainElement = (Vector) returnDataList.get(i);
                Vector historyElement = null;
                String modifiedFlag = mainElement.get(27).toString();
                if (modifiedFlag.equalsIgnoreCase("Y")) {
                    List hisList = em.createNativeQuery("select txn_id,ifnull(acno,'') as acno,ifnull(inst_no,'') as chequeNo,"
                            + "inst_amt,date_format(inst_dt,'%d%m%Y'),ifnull(favor_of,'') as favourof,ifnull(em_flag,'') as "
                            + "clearingType,ifnull(custname,'') as custname,ifnull(oper_mode,'') as operMode,"
                            + "ifnull(userdetails,'') as userDetails,ifnull(prbankcode,'') as prBankCode,"
                            + "date_format(fh_creation_date,'%d%m%Y') as fhCreationDt,ifnull(fh_file_id,'') as "
                            + "fileId,date_format(fh_settlement_date,'%d%m%Y') as fhSettlementDt,"
                            + "ifnull(item_payor_bank_routno,'') as itemPayorBankIfsc,ifnull(item_seq_no,'') as itemSeqNo,"
                            + "ifnull(item_trans_code,'') as itemTransCode,ifnull(item_san,'') as itemSan,"
                            + "ifnull(binary_data_file_name,'') as binaryDataFileName,ifnull(fh_vno,'') as fhVNo,"
                            + "ifnull(fh_test_file_indicator,'') as testIndicator,date_format(item_prment_date,'%d%m%Y') "
                            + "as presentMentDt,ifnull(item_cycleno,'') as itemCycleNo,ifnull(addenda_bofdroutno,''),"
                            + "date_format(addenda_bofdbusdate,'%d%m%Y'),ifnull(addenda_depositoracct,''),"
                            + "ifnull(addenda_ifsc,''),ifnull(modified_flag,'') as modified_flag,ifnull(details,''),"
                            + "ifnull(schedule_no,0),date_format(dt,'%Y%m%d') from cts_clg_in_entry_history where "
                            + "dt='" + fileGenDt + "' and em_flag='" + clgType + "' and "
                            + "schedule_no=" + Integer.parseInt(scheduleNo) + " and orgn_branch='" + orgnCode + "' and "
                            + "txn_id='" + mainElement.get(0).toString() + "' and id in(select min(id) from "
                            + "cts_clg_in_entry_history where dt='" + fileGenDt + "' and em_flag='" + clgType + "' and "
                            + "schedule_no=" + Integer.parseInt(scheduleNo) + " and orgn_branch='" + orgnCode + "' and "
                            + "txn_id='" + mainElement.get(0).toString() + "')").getResultList();
                    if (hisList.isEmpty()) {
                        throw new Exception("Modified entry should be maintain in the history detail.");
                    }
                    historyElement = (Vector) hisList.get(0);
                }

                //Return object creation, Use History Object for modified data
                ReturnDTO dto = new ReturnDTO();

                dto.setTxnId(mainElement.get(0).toString());
                dto.setAcno(historyElement == null ? mainElement.get(1).toString() : historyElement.get(1).toString());
                dto.setChqNo(historyElement == null ? mainElement.get(2).toString() : historyElement.get(2).toString());
//                dto.setInstAmt(new BigDecimal(mainElement.get(3).toString()));
                System.out.println("Val Is-->" + getClearingTxnAmount(Double.parseDouble(mainElement.get(3).toString())));
                dto.setInstAmt(new BigDecimal(getClearingTxnAmount(Double.parseDouble(mainElement.get(3).toString()))));

                dto.setInstDt(dmyOne.format(dmy.parse(historyElement == null ? mainElement.get(4).toString() : historyElement.get(4).toString())));
                dto.setInFavourOf(historyElement == null ? mainElement.get(5).toString() : historyElement.get(5).toString());
                dto.setClgType(mainElement.get(6).toString());
                dto.setCustName(historyElement == null ? mainElement.get(7).toString() : historyElement.get(7).toString());
                dto.setOperMode(historyElement == null ? mainElement.get(8).toString() : historyElement.get(8).toString());
                dto.setUserDetail(mainElement.get(9).toString());
                dto.setPrBankCode(mainElement.get(10).toString());
                dto.setCreationDt(mainElement.get(11).toString());
                dto.setFileId(mainElement.get(12).toString());
                dto.setSettlementDt(mainElement.get(13).toString());
//                dto.setItemPayorBankIfsc(historyElement.get(14).toString());
                dto.setItemPayorBankIfsc(historyElement == null ? mainElement.get(14).toString() : historyElement.get(14).toString());
                dto.setItemSeqNo(mainElement.get(15).toString());
                dto.setItemTransCode(historyElement == null ? mainElement.get(16).toString() : historyElement.get(16).toString());
                dto.setItemSan(historyElement == null ? mainElement.get(17).toString() : historyElement.get(17).toString());
                dto.setBinaryDataFileName(mainElement.get(18).toString());
                dto.setVersionNo(mainElement.get(19).toString());
                dto.setTestIndicator(mainElement.get(20).toString());
                dto.setPresentmentDt(mainElement.get(21).toString());
                dto.setItemCycleNo(mainElement.get(22).toString());
                dto.setAddendRoute(mainElement.get(23).toString());
                dto.setAddendBusDt(mainElement.get(24).toString());
                dto.setAddendAccount(mainElement.get(25).toString());
                dto.setAddendIfsc(mainElement.get(26).toString());
                dto.setModifyFlag(mainElement.get(27).toString());
                dto.setDetails(mainElement.get(28).toString());
                dto.setScheduleNo(mainElement.get(29).toString());
                dto.setTxnDt(mainElement.get(30).toString());

                returnData.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnData;
    }

    public String getClearingTxnAmount(double value) {
        DecimalFormat formatter;
        if (value - (int) value > 0.0) {
            formatter = new DecimalFormat("0.00");
        } else {
            formatter = new DecimalFormat("0");
        }
        return formatter.format(value);
    }

    @Override
    public List<ReturnDTO> getCtsArchivingData(String frdt, String todt, String chequeNo, String fileType) throws Exception {
        List<ReturnDTO> getList = new ArrayList<>();
        try {
            if (fileType.equalsIgnoreCase("CH")) {
                List list = em.createNativeQuery("select b.itemSeqNo,b.acno,b.chequeNo,b.instdt,b.inst_amt,b.InFavourOf,"
                        + "b.prBankCode,b.imgFile,b.dataFile from (select a.itemSeqNo,a.acno,a.chequeNo,a.instdt,"
                        + "a.inst_amt,a.InFavourOf,a.prBankCode,a.imgFile,a.dataFile, min(a.id) from (select ifnull(item_seq_no,'') "
                        + "as itemSeqNo,ifnull(acno,'') as acno,ifnull(inst_no,'') as chequeNo,date_format(inst_dt,'%d%m%Y') as "
                        + "instdt,inst_amt,ifnull(favor_of,'') as InFavourOf,ifnull(prbankcode,'') as prBankCode,"
                        + "ifnull(binary_img_file_name,'') as imgFile,ifnull(binary_data_file_name,'') as dataFile,id "
                        + "from cts_clg_in_entry_history where inst_no='" + chequeNo + "' and item_seq_no is not null) a "
                        + "group by a.itemSeqNo) b "
                        + "union "
                        + "select ifnull(item_seq_no,'') as itemSeqNo,ifnull(acno,'') as acno,ifnull(inst_no,'') as "
                        + "chequeNo,date_format(inst_dt,'%d%m%Y') as instdt,inst_amt,ifnull(favor_of,'') as InFavourOf,"
                        + "ifnull(prbankcode,'') as prBankCode,ifnull(binary_img_file_name,'') as imgFile,"
                        + "ifnull(binary_data_file_name,'') as dataFile from cts_clg_in_entry where inst_no='" + chequeNo + "' and "
                        + "modified_flag=''").getResultList();
                if (list.isEmpty()) {
                    throw new Exception("There is no data found");
                }
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    ReturnDTO obj = new ReturnDTO();
                    obj.setSrNo(new BigInteger(String.valueOf(i)).add(BigInteger.ONE));
                    obj.setItemSeqNo(ele.get(0).toString().trim());
                    obj.setAcno(ele.get(1).toString().trim());
                    obj.setChqNo(ele.get(2).toString().trim());
                    obj.setInstDt(dmyOne.format(dmy.parse(ele.get(3).toString().trim())));
                    obj.setInstAmt(new BigDecimal(ele.get(4).toString().trim()));
                    obj.setInFavourOf(ele.get(5).toString().trim());
                    obj.setPrBankCode(ele.get(6).toString().trim());
                    obj.setBinaryImgFileName(ele.get(7).toString().trim());
                    obj.setBinaryDataFileName(ele.get(8).toString().trim());

                    getList.add(obj);
                }
            }

            if (fileType.equalsIgnoreCase("DT")) {
                List list = em.createNativeQuery("select b.itemSeqNo,b.acno,b.chequeNo,b.instdt,b.inst_amt,b.InFavourOf,b.prBankCode,b.imgFile,b.dataFile from (select a.itemSeqNo,a.acno,a.chequeNo,a.instdt,a.inst_amt,a.InFavourOf,a.prBankCode,a.imgFile,a.dataFile, min(a.id) from (select ifnull(item_seq_no,'') as itemSeqNo,ifnull(acno,'') as acno,ifnull(inst_no,'') as chequeNo,date_format(inst_dt,'%Y%m%d') as instdt,inst_amt,ifnull(favor_of,'') as InFavourOf,ifnull(prbankcode,'') as prBankCode,ifnull(binary_img_file_name,'') as imgFile,ifnull(binary_data_file_name,'') as dataFile, id from cts_clg_in_entry_history where DT Between '" + ymd.format(dmyOne.parse(frdt)) + "'and '" + ymd.format(dmyOne.parse(todt)) + "') a group by a.itemSeqNo) b union select ifnull(item_seq_no,'') as itemSeqNo,ifnull(acno,'') as acno,ifnull(inst_no,'') as chequeNo,date_format(inst_dt,'%Y%m%d') as instdt,inst_amt,ifnull(favor_of,'') as InFavourOf,ifnull(prbankcode,'') as prBankCode,ifnull(binary_img_file_name,'') as imgFile,ifnull(binary_data_file_name,'') as dataFile from cts_clg_in_entry where DT Between '" + ymd.format(dmyOne.parse(frdt)) + "' and '" + ymd.format(dmyOne.parse(todt)) + "' and modified_flag=''").getResultList();
                if (list.isEmpty()) {
                    throw new Exception("There is No list between these Date");
                }
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    ReturnDTO obj = new ReturnDTO();
                    obj.setSrNo(new BigInteger(String.valueOf(i)).add(BigInteger.ONE));
                    obj.setItemSeqNo(ele.get(0).toString().trim());
                    obj.setAcno(ele.get(1).toString().trim());
                    obj.setChqNo(ele.get(2).toString().trim());
                    obj.setInstDt(dmyOne.format(ymd.parse(ele.get(3).toString().trim())));
                    obj.setInstAmt(new BigDecimal(ele.get(4).toString().trim()));
                    obj.setInFavourOf(ele.get(5).toString().trim());
                    obj.setPrBankCode(ele.get(6).toString().trim());
                    obj.setBinaryImgFileName(ele.get(7).toString().trim());
                    obj.setBinaryDataFileName(ele.get(8).toString().trim());

                    getList.add(obj);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return getList;

    }

    @Override
    public List<String> updateOutwardClgReturn(List<com.cbs.dto.npci.cts.ow.reverse.FileHeader.Item> items,
            String userName, String todayDt, String mode) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        List<String> returnList = new ArrayList<>();
        try {
            ut.begin();
            for (int i = 0; i < items.size(); i++) {
                com.cbs.dto.npci.cts.ow.reverse.FileHeader.Item item = items.get(i);
//                System.out.println("ItemSeqNo->" + item.getItemSeqNo() + "::InstNo->" + item.getSerialNo()
//                        + "::PayorBankRoutNo->" + item.getPayorBankRoutNo() + "::TransactionCode->"
//                        + item.getTransCode() + "::Amount->" + Double.parseDouble(item.getAmount()));
                List list = em.createNativeQuery("select acno from clg_ow_shadowbal where "
                        + "txninstno='" + item.getSerialNo().trim() + "' and "
                        + "areacode='" + item.getPayorBankRoutNo().trim().substring(0, 3) + "' and "
                        + "bnkcode='" + item.getPayorBankRoutNo().trim().substring(3, 6) + "' and "
                        + "branchcode='" + item.getPayorBankRoutNo().trim().substring(6) + "' and "
                        + "transactioncode='" + item.getTransCode().trim() + "' and "
                        + "txninstamt=" + (Double.parseDouble(item.getAmount()) / 100) + "").getResultList();
                if (list.isEmpty()) {
                    returnList.add(item.getItemSeqNo().trim());
                } else {
                    int n = em.createNativeQuery("INSERT INTO clg_ow_shadowbal_his(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,"
                            + "TxnInstAmt,Txnstatus,TxnInstDate,TxnBankName,TxnBankAddress,AreaCode,BnkCode,BranchCode,"
                            + "remarks,ReasonForCancel,vtot,EMFlag,EnterBy,AuthBy,BillType,AlphaCode,BcBpNo,Fyear,IY,"
                            + "OBCFLAG,TranDesc,Sbal,dt,DrwAcno,DrwName,TerminalId,VerifiedBy,orgnbrcode,destbrcode,"
                            + "SanNo,TransactionCode,AccountName,CtsReturnCode,Update_By,Update_Date,Update_Mode) "
                            + "select Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,Txnstatus,TxnInstDate,TxnBankName,"
                            + "TxnBankAddress,AreaCode,BnkCode,BranchCode,remarks,ReasonForCancel,vtot,EMFlag,EnterBy,"
                            + "AuthBy,BillType,AlphaCode,BcBpNo,Fyear,IY,OBCFLAG,TranDesc,Sbal,dt,DrwAcno,DrwName,"
                            + "TerminalId,VerifiedBy,orgnbrcode,destbrcode,SanNo,TransactionCode,AccountName,"
                            + "CtsReturnCode,'" + userName + "',now(),'" + mode + "' from clg_ow_shadowbal where "
                            + "TxnInstNo='" + item.getSerialNo().trim() + "' and AreaCode='" + item.getPayorBankRoutNo().trim().
                            substring(0, 3) + "' and BnkCode='" + item.getPayorBankRoutNo().trim().substring(3, 6) + "' and "
                            + "BranchCode='" + item.getPayorBankRoutNo().trim().substring(6) + "' and "
                            + "TransactionCode='" + item.getTransCode().trim() + "' and TxnInstAmt="
                            + (Double.parseDouble(item.getAmount()) / 100) + "").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in history maintainance of return updation "
                                + "for item seq no->" + item.getItemSeqNo().trim());
                    }
                    n = em.createNativeQuery("update clg_ow_shadowbal set ctsreturncode = '" + item.getReturnReason().trim() + "' "
                            + "where txninstno = '" + item.getSerialNo().trim() + "' and "
                            + "areacode = '" + item.getPayorBankRoutNo().trim().substring(0, 3) + "' and "
                            + "bnkcode = '" + item.getPayorBankRoutNo().trim().substring(3, 6) + "' and "
                            + "branchcode = '" + item.getPayorBankRoutNo().trim().substring(6) + "' and "
                            + "transactioncode = '" + item.getTransCode().trim() + "' and "
                            + "txninstamt = '" + (Double.parseDouble(item.getAmount()) / 100) + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in return updation for item seq no->" + item.getItemSeqNo().trim());
                    }
                }
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
        return returnList;
    }

    public Map<String, String> getTransCodeMap() throws Exception {
        //Fetching The Transaction Code
//        List list = em.createNativeQuery("select ifnull(ref_code,''),ifnull(ref_desc,'') as DESCRIPTION from "
//                + "cbs_ref_rec_type where ref_rec_no='015'").getResultList();
        List list = em.createNativeQuery("select ifnull(ref_code,''),ifnull(ref_desc,'') as DESCRIPTION from "
                + "cbs_ref_rec_type where ref_rec_no='017'").getResultList();
        if (list.isEmpty()) {
            throw new Exception("Please define transaction code in CBS REF REC TYPE for 015");
        }
        Map<String, String> transCodeMap = new HashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            Vector ele = (Vector) list.get(i);
//            transCodeMap.put(ele.get(0).toString(), ele.get(1).toString());
            transCodeMap.put(ele.get(1).toString(), ele.get(0).toString());
        }
        return transCodeMap;
    }

    @Override
    public Map getBranchDetailInMap() throws Exception {
        Map<String, String> brMap = new HashMap<String, String>();
        List list = em.createNativeQuery("select brncode,alphacode from branchmaster order by brncode").getResultList();
        if (list.isEmpty()) {
            throw new Exception("There is no data in branchmaster");
        }
        for (int i = 0; i < list.size(); i++) {
            Vector ele = (Vector) list.get(i);
            brMap.put(ele.get(0).toString(), ele.get(0).toString());
        }
        return brMap;
    }

    public Map<String, String> getMicrAndBranchCode() throws Exception {
        List list = em.createNativeQuery("select bn.alphacode,concat(ifnull(lpad(bn.micr,3,'0'),'000'),"
                + "ifnull(lpad(bn.micrcode,3,'0'),'000'),ifnull(lpad(bn.branchcode,3,'0'),'000')) as micr,"
                + "bm.brncode from bnkadd bn,branchmaster bm where bn.alphacode=bm.alphacode "
                + "order by bm.brncode").getResultList();
        if (list.isEmpty()) {
            throw new Exception("Please define bnkadd");
        }
        Map<String, String> micrBranchMap = new HashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            Vector ele = (Vector) list.get(i);
            micrBranchMap.put(ele.get(0).toString().trim() + ":" + ele.get(1).toString().trim(),
                    ele.get(2).toString().trim().length() < 2 ? "0" + ele.get(2).toString().trim() : ele.get(2).toString().trim());
        }
        return micrBranchMap;
    }

    @Override
    public int getInstrumentStatus(String date, String emFlag, int scheduleNo, String txnId, String itemSeqNo) throws Exception {
        int status = 0;
        try {
            List list = em.createNativeQuery("select status from cts_clg_in_entry where dt='" + date + "' and "
                    + "em_flag='" + emFlag + "' and schedule_no=" + scheduleNo + " and txn_id='" + txnId + "' and "
                    + "item_seq_no='" + itemSeqNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no instrument to modify.");
            }
            Vector ele = (Vector) list.get(0);
            status = Integer.parseInt(ele.get(0).toString());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return status;
    }

    @Override
    public String generateCtsZipReturnXml(List<ReturnDTO> returnData) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String zipFileName = "";
        try {
            ut.begin();
            //Required Folders
            File writeFolder = new File("/install/ATM/");
            if (!writeFolder.exists()) {
                writeFolder.mkdir();
            }
            String zipFolderName = "Today-" + ymd.format(new Date()) + "-Return";
            File zipDirectory = new File(writeFolder + "/" + zipFolderName);
            if (!zipDirectory.exists()) {
                zipDirectory.mkdirs(); //Where all files will collect
            }
            //Filename prparation
            ReturnDTO returnObj = returnData.get(0);
            String bankMicr = returnObj.getBinaryDataFileName().trim().split("\\_")[1].trim();

            for (int i = 0; i < returnData.size(); i++) {
                com.cbs.dto.npci.cts.reverse.FileHeader returnHeader = new com.cbs.dto.npci.cts.reverse.FileHeader();
                returnHeader.setVersionNumber(returnObj.getVersionNo());
                returnHeader.setTestFileIndicator(returnObj.getTestIndicator());
                String creationDt = dmy.format(new Date());
                returnHeader.setCreationDate(creationDt);
                String creationTime = hhmmss.format(new Date());
                returnHeader.setCreationTime(creationTime);
                returnHeader.setFileID("1");

                com.cbs.dto.npci.cts.reverse.FileHeader.FileSummary fileSummary = new com.cbs.dto.npci.cts.reverse.FileHeader.FileSummary();
                fileSummary.setTotalItemCount("1");

                List<com.cbs.dto.npci.cts.reverse.FileHeader.Item> items = returnHeader.getItem();

                returnObj = returnData.get(i);
                com.cbs.dto.npci.cts.reverse.FileHeader.Item item = new com.cbs.dto.npci.cts.reverse.FileHeader.Item();

                item.setItemSeqNo(returnObj.getItemSeqNo().trim());
                item.setPayorBankRoutNo(returnObj.getItemPayorBankIfsc().trim());

                String chqAmt = returnObj.getInstAmt().multiply(new BigDecimal(100)).toString();
                chqAmt = chqAmt.contains(".") ? chqAmt.substring(0, chqAmt.indexOf(".")) : chqAmt;

                item.setAmount(chqAmt);
                item.setSerialNo(returnObj.getChqNo().trim());
                item.setTransCode(returnObj.getItemTransCode().trim());
                item.setPresentingBankRoutNo(returnObj.getPrBankCode().trim());
                item.setPresentmentDate(returnObj.getPresentmentDt().trim());
                item.setCycleNo(returnObj.getItemCycleNo().trim());
                item.setClearingType(returnObj.getClgType().trim());
                item.setReturnReason(returnObj.getUserDetail().trim().length() < 2 ? "0" + returnObj.getUserDetail().trim() : returnObj.getUserDetail().trim());
                if (Integer.parseInt(returnObj.getUserDetail().trim()) == Integer.parseInt(NpciReturnConstant.NACH_CLEARING_RETURN_88.getCode())) {
                    String reasonComment = returnObj.getDetails().trim();
                    reasonComment = reasonComment.replaceAll("[\\W_]", " ");
                    reasonComment = reasonComment.length() > 25 ? reasonComment.substring(0, 25) : reasonComment;

                    item.setReturnReasonComment(reasonComment.trim());
                } else {
                    String reasonComment = commonReportRemote.getCodeBookDescription(13, Integer.parseInt(returnObj.getUserDetail().trim()));
                    reasonComment = reasonComment.replaceAll("[\\W_]", " ");
                    reasonComment = reasonComment.length() > 25 ? reasonComment.substring(0, 25) : reasonComment;

                    item.setReturnReasonComment(reasonComment.trim());
                }
                com.cbs.dto.npci.cts.reverse.FileHeader.Item.AddendA addendA = new com.cbs.dto.npci.cts.reverse.FileHeader.Item.AddendA();
                addendA.setBOFDRoutNo(returnObj.getAddendRoute().trim());
                addendA.setBOFDBusDate(returnObj.getAddendBusDt().trim());
                addendA.setDepositorAcct((returnObj.getAddendAccount().trim() == null || returnObj.getAddendAccount().trim().equals("")) ? "1" : returnObj.getAddendAccount().trim());
                addendA.setIFSC(returnObj.getAddendIfsc().trim());
                item.setAddendA(addendA);

                items.add(item);

                String returnAmount = returnObj.getInstAmt().multiply(new BigDecimal(100)).toString();
                returnAmount = returnAmount.contains(".") ? returnAmount.substring(0, returnAmount.indexOf(".")) : returnAmount;
                fileSummary.setTotalAmount(returnAmount);
                returnHeader.setFileSummary(fileSummary);

                File generatedXmlFile = new File(zipDirectory + File.separator + "RRF_" + bankMicr + "_" + creationDt + "_" + creationTime + "_1.XML");
                JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.dto.npci.cts.reverse.FileHeader.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                jaxbMarshaller.marshal(returnHeader, generatedXmlFile);
                //Writting done file
                FileWriter doneXmlFile = new FileWriter(zipDirectory + File.separator + "RRF_" + bankMicr + "_" + creationDt + "_" + creationTime + "_1.XML.done");
                doneXmlFile.write(" ");
                doneXmlFile.close();
                Thread.sleep(2000);
            }
            ckycrCommonMgmtRemote.addToZipWithOutFolder(zipDirectory + "/", writeFolder + "/", zipFolderName + ".zip");
            File zipFolderToDelete = new File(zipDirectory + "/");
            if (zipFolderToDelete.exists()) {
                ckycrCommonMgmtRemote.delete(zipFolderToDelete);
            }
            zipFileName = zipDirectory.getName() + ".zip";
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return zipFileName;
    }

    @Override
    public String generateCtsReturnXml(List<ReturnDTO> returnData) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String xmlFileName = "";
        try {
            ut.begin();
            //Filename prparation
            ReturnDTO returnObj = returnData.get(0);
            String bankMicr = returnObj.getBinaryDataFileName().trim().split("\\_")[1].trim();

            com.cbs.dto.npci.cts.reverse.FileHeader returnHeader = new com.cbs.dto.npci.cts.reverse.FileHeader();
            returnHeader.setVersionNumber(returnObj.getVersionNo());
            returnHeader.setTestFileIndicator(returnObj.getTestIndicator());
            String creationDt = dmy.format(new Date());
            returnHeader.setCreationDate(creationDt);
            String creationTime = hhmmss.format(new Date());
            returnHeader.setCreationTime(creationTime);
            returnHeader.setFileID("1");

            com.cbs.dto.npci.cts.reverse.FileHeader.FileSummary fileSummary = new com.cbs.dto.npci.cts.reverse.FileHeader.FileSummary();
            fileSummary.setTotalItemCount(String.valueOf(returnData.size()));

            List<com.cbs.dto.npci.cts.reverse.FileHeader.Item> items = returnHeader.getItem();

            BigDecimal totalReturnAmount = BigDecimal.ZERO;
            for (int i = 0; i < returnData.size(); i++) {
                returnObj = returnData.get(i);
                com.cbs.dto.npci.cts.reverse.FileHeader.Item item = new com.cbs.dto.npci.cts.reverse.FileHeader.Item();

                item.setItemSeqNo(returnObj.getItemSeqNo().trim());
                item.setPayorBankRoutNo(returnObj.getItemPayorBankIfsc().trim());

                String chqAmt = returnObj.getInstAmt().multiply(new BigDecimal(100)).toString();
                chqAmt = chqAmt.contains(".") ? chqAmt.substring(0, chqAmt.indexOf(".")) : chqAmt;

                item.setAmount(chqAmt);
                item.setSerialNo(returnObj.getChqNo().trim());
                item.setTransCode(returnObj.getItemTransCode().trim());
                item.setPresentingBankRoutNo(returnObj.getPrBankCode().trim());
                item.setPresentmentDate(returnObj.getPresentmentDt().trim());
                item.setCycleNo(returnObj.getItemCycleNo().trim());
                item.setClearingType(returnObj.getClgType().trim());
                item.setReturnReason(returnObj.getUserDetail().trim().length() < 2 ? "0" + returnObj.getUserDetail().trim() : returnObj.getUserDetail().trim());
                if (Integer.parseInt(returnObj.getUserDetail().trim()) == Integer.parseInt(NpciReturnConstant.NACH_CLEARING_RETURN_88.getCode())) {
                    String reasonComment = returnObj.getDetails().trim();
                    reasonComment = reasonComment.replaceAll("[\\W_]", " ");
                    reasonComment = reasonComment.length() > 25 ? reasonComment.substring(0, 25) : reasonComment;

                    item.setReturnReasonComment(reasonComment.trim());
                } else {
                    String reasonComment = commonReportRemote.getCodeBookDescription(13, Integer.parseInt(returnObj.getUserDetail().trim()));
                    reasonComment = reasonComment.replaceAll("[\\W_]", " ");
                    reasonComment = reasonComment.length() > 25 ? reasonComment.substring(0, 25) : reasonComment;

                    item.setReturnReasonComment(reasonComment.trim());
                }
                com.cbs.dto.npci.cts.reverse.FileHeader.Item.AddendA addendA = new com.cbs.dto.npci.cts.reverse.FileHeader.Item.AddendA();
                addendA.setBOFDRoutNo(returnObj.getAddendRoute().trim());
                addendA.setBOFDBusDate(returnObj.getAddendBusDt().trim());
                addendA.setDepositorAcct((returnObj.getAddendAccount().trim() == null || returnObj.getAddendAccount().trim().equals("")) ? "1" : returnObj.getAddendAccount().trim());
                addendA.setIFSC(returnObj.getAddendIfsc().trim());
                item.setAddendA(addendA);

                items.add(item);

                totalReturnAmount = totalReturnAmount.add(returnObj.getInstAmt());
            }

            String returnAmount = totalReturnAmount.multiply(new BigDecimal(100)).toString();
            returnAmount = returnAmount.contains(".") ? returnAmount.substring(0, returnAmount.indexOf(".")) : returnAmount;
            fileSummary.setTotalAmount(returnAmount);
            returnHeader.setFileSummary(fileSummary);

            //File writing
            File writeFolder = new File("/install/ATM/");
            if (!writeFolder.exists()) {
                writeFolder.mkdir();
            }
            File generatedXmlFile = new File(writeFolder + File.separator + "RRF_" + bankMicr + "_" + creationDt + "_" + creationTime + "_1.XML");
            JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.dto.npci.cts.reverse.FileHeader.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            jaxbMarshaller.marshal(returnHeader, generatedXmlFile);
            xmlFileName = generatedXmlFile.getName();
            //Writting done file
            FileWriter doneXmlFile = new FileWriter(writeFolder + File.separator + "RRF_" + bankMicr + "_" + creationDt + "_" + creationTime + "_1.XML.done");
            doneXmlFile.write(" ");
            doneXmlFile.close();

            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return xmlFileName;
    }

    @Override
    public List<ReturnDTO> getNpciManualReturnData(String fileGenDt, String clgType, String scheduleNo, String orgnCode) throws Exception {
        List<ReturnDTO> returnData = new ArrayList<>();
        try {
            List returnDataList = em.createNativeQuery("select txn_id,ifnull(acno,'') as acno,ifnull(inst_no,'') as chequeNo,"
                    + "inst_amt,date_format(inst_dt,'%d%m%Y'),ifnull(favor_of,'') as favourof,ifnull(em_flag,'') "
                    + "as clearingType,ifnull(custname,'') as custname,ifnull(oper_mode,'') as operMode,"
                    + "ifnull(userdetails,'') as userDetails,ifnull(prbankcode,'') as prBankCode,"
                    + "date_format(fh_creation_date,'%d%m%Y') as fhCreationDt,ifnull(fh_file_id,'') "
                    + "as fileId,date_format(fh_settlement_date,'%d%m%Y') as fhSettlementDt,"
                    + "ifnull(item_payor_bank_routno,'') as itemPayorBankIfsc,ifnull(item_seq_no,'') as itemSeqNo,"
                    + "ifnull(item_trans_code,'') as itemTransCode,ifnull(item_san,'') as itemSan,"
                    + "ifnull(binary_data_file_name,'') as binaryDataFileName,ifnull(fh_vno,'') as fhVNo,"
                    + "ifnull(fh_test_file_indicator,'') as testIndicator,date_format(item_prment_date,'%d%m%Y') "
                    + "as presentMentDt,ifnull(item_cycleno,'') as itemCycleNo,ifnull(addenda_bofdroutno,''),"
                    + "date_format(addenda_bofdbusdate,'%d%m%Y'),ifnull(addenda_depositoracct,''),ifnull(addenda_ifsc,''),"
                    + "ifnull(modified_flag,'') as modified_flag,ifnull(details,''),ifnull(schedule_no,0),date_format(dt,'%Y%m%d'),ifnull(image_ref_data,'') "
                    + "from cts_clg_in_entry where dt='" + fileGenDt + "' and em_flag='" + clgType + "' and "
                    + "schedule_no=" + Integer.parseInt(scheduleNo) + " and status=4 and "
                    + "orgn_branch='" + orgnCode + "'").getResultList();
            if (returnDataList.isEmpty()) {
                throw new Exception("There is no data to generate the return file");
            }
            Vector mainElement = null;
            for (int i = 0; i < returnDataList.size(); i++) {
                mainElement = (Vector) returnDataList.get(i);
                Vector historyElement = null;
                String modifiedFlag = mainElement.get(27).toString();
                if (modifiedFlag.equalsIgnoreCase("Y")) {
                    List hisList = em.createNativeQuery("select txn_id,ifnull(acno,'') as acno,ifnull(inst_no,'') as chequeNo,"
                            + "inst_amt,date_format(inst_dt,'%d%m%Y'),ifnull(favor_of,'') as favourof,ifnull(em_flag,'') as "
                            + "clearingType,ifnull(custname,'') as custname,ifnull(oper_mode,'') as operMode,"
                            + "ifnull(userdetails,'') as userDetails,ifnull(prbankcode,'') as prBankCode,"
                            + "date_format(fh_creation_date,'%d%m%Y') as fhCreationDt,ifnull(fh_file_id,'') as "
                            + "fileId,date_format(fh_settlement_date,'%d%m%Y') as fhSettlementDt,"
                            + "ifnull(item_payor_bank_routno,'') as itemPayorBankIfsc,ifnull(item_seq_no,'') as itemSeqNo,"
                            + "ifnull(item_trans_code,'') as itemTransCode,ifnull(item_san,'') as itemSan,"
                            + "ifnull(binary_data_file_name,'') as binaryDataFileName,ifnull(fh_vno,'') as fhVNo,"
                            + "ifnull(fh_test_file_indicator,'') as testIndicator,date_format(item_prment_date,'%d%m%Y') "
                            + "as presentMentDt,ifnull(item_cycleno,'') as itemCycleNo,ifnull(addenda_bofdroutno,''),"
                            + "date_format(addenda_bofdbusdate,'%d%m%Y'),ifnull(addenda_depositoracct,''),"
                            + "ifnull(addenda_ifsc,''),ifnull(modified_flag,'') as modified_flag,ifnull(details,''),"
                            + "ifnull(schedule_no,0),date_format(dt,'%Y%m%d'),ifnull(image_ref_data,'') from cts_clg_in_entry_history where "
                            + "dt='" + fileGenDt + "' and em_flag='" + clgType + "' and "
                            + "schedule_no=" + Integer.parseInt(scheduleNo) + " and orgn_branch='" + orgnCode + "' and "
                            + "txn_id='" + mainElement.get(0).toString() + "' and id in(select min(id) from "
                            + "cts_clg_in_entry_history where dt='" + fileGenDt + "' and em_flag='" + clgType + "' and "
                            + "schedule_no=" + Integer.parseInt(scheduleNo) + " and orgn_branch='" + orgnCode + "' and "
                            + "txn_id='" + mainElement.get(0).toString() + "')").getResultList();
                    if (hisList.isEmpty()) {
                        throw new Exception("Modified entry should be maintain in the history detail.");
                    }
                    historyElement = (Vector) hisList.get(0);
                }

                //Return object creation, Use History Object for modified data
                ReturnDTO dto = new ReturnDTO();

                dto.setTxnId(mainElement.get(0).toString());
                dto.setAcno(historyElement == null ? mainElement.get(1).toString() : historyElement.get(1).toString());
                dto.setChqNo(historyElement == null ? mainElement.get(2).toString() : historyElement.get(2).toString());
                dto.setInstAmt(historyElement == null ? new BigDecimal(mainElement.get(3).toString()) : new BigDecimal(historyElement.get(3).toString()));
                dto.setInstDt(historyElement == null ? mainElement.get(4).toString() : historyElement.get(4).toString());
                dto.setInFavourOf(historyElement == null ? mainElement.get(5).toString() : historyElement.get(5).toString());
                dto.setClgType(mainElement.get(6).toString());
                dto.setCustName(historyElement == null ? mainElement.get(7).toString() : historyElement.get(7).toString());
                dto.setOperMode(historyElement == null ? mainElement.get(8).toString() : historyElement.get(8).toString());
                dto.setUserDetail(mainElement.get(9).toString());
                dto.setPrBankCode(mainElement.get(10).toString());
                dto.setCreationDt(mainElement.get(11).toString());
                dto.setFileId(mainElement.get(12).toString());
                dto.setSettlementDt(mainElement.get(13).toString());
                dto.setItemPayorBankIfsc(historyElement == null ? mainElement.get(14).toString() : historyElement.get(14).toString());
                dto.setItemSeqNo(mainElement.get(15).toString());
                dto.setItemTransCode(historyElement == null ? mainElement.get(16).toString() : historyElement.get(16).toString());
                dto.setItemSan(historyElement == null ? mainElement.get(17).toString() : historyElement.get(17).toString());
                dto.setBinaryDataFileName(mainElement.get(18).toString());
                dto.setVersionNo(mainElement.get(19).toString());
                dto.setTestIndicator(mainElement.get(20).toString());
                dto.setPresentmentDt(mainElement.get(21).toString());
                dto.setItemCycleNo(mainElement.get(22).toString());
                dto.setAddendRoute(mainElement.get(23).toString());
                dto.setAddendBusDt(mainElement.get(24).toString());
                dto.setAddendAccount(mainElement.get(25).toString());
                dto.setAddendIfsc(mainElement.get(26).toString());
                dto.setModifyFlag(mainElement.get(27).toString());
                dto.setDetails(mainElement.get(28).toString());
                dto.setScheduleNo(mainElement.get(29).toString());
                dto.setTxnDt(mainElement.get(30).toString());
                dto.setImageReferenceData(mainElement.get(31).toString());

                returnData.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return returnData;
    }

    @Override
    public String generateCtsReturnTxt(List<ReturnDTO> returnData) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String txtFileName = "";
        try {
            ut.begin();
            //File writing
            File writeFolder = new File("/install/ATM/");
            if (!writeFolder.exists()) {
                writeFolder.mkdir();
            }

            List list = em.createNativeQuery("select code from cbs_parameterinfo where name='CTS-IW-SPONSOR-ACCOUNT'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define CTS IW SPONSOR ACCOUNT.");
            }
            Vector ele = (Vector) list.get(0);
            String ctsIwSponsorAccount = ele.get(0).toString().trim();
            if (ctsIwSponsorAccount == null || ctsIwSponsorAccount.equals("")) {
                throw new Exception("Please define CTS IW SPONSOR ACCOUNT.");
            }

            ReturnDTO returnObj = returnData.get(0);
            String fileName = "Iw_Return_" + ymd.format(new Date()) + "_" + returnObj.getClgType() + returnObj.getScheduleNo() + ".txt";
            FileWriter fw = new FileWriter(writeFolder + "/" + fileName);
            for (ReturnDTO obj : returnData) {
                String chqAmt = obj.getInstAmt().multiply(new BigDecimal(100)).toString();
                chqAmt = chqAmt.contains(".") ? chqAmt.substring(0, chqAmt.indexOf(".")).trim() : chqAmt.trim();

                String payeeName = obj.getInFavourOf().length() > 50 ? obj.getInFavourOf().substring(0, 50).trim()
                        : obj.getInFavourOf().trim();

                String reasonCode = getSpecificMappedReason(obj.getUserDetail().trim());
                reasonCode = reasonCode.length() < 2 ? "0" + reasonCode : reasonCode;

                String singleInstrument = obj.getPrBankCode() + obj.getItemPayorBankIfsc() + obj.getTxnDt()
                        + ParseFileUtil.addTrailingZeros(chqAmt, 17) + ParseFileUtil.addTrailingZeros(obj.getChqNo().trim(), 8)
                        + obj.getItemSeqNo().substring(4).trim() + ParseFileUtil.addTrailingSpaces(obj.getItemTransCode(), 3)
                        + ParseFileUtil.addSuffixSpaces(ctsIwSponsorAccount, 16) + obj.getInstDt()
                        + ParseFileUtil.addSuffixSpaces(payeeName, 50) + "I" + reasonCode + ParseFileUtil.addTrailingZeros("", 19)
                        + obj.getPresentmentDt() + obj.getPrBankCode() + ParseFileUtil.addTrailingZeros("", 2)
                        + obj.getItemSeqNo().trim() + ParseFileUtil.addSuffixSpaces("", 25) + "\r\n";

                fw.write(singleInstrument);
            }
            fw.close();
            txtFileName = fileName;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return txtFileName;
    }

    public String getSpecificMappedReason(String npciReturnCode) throws Exception {
        try {
            List list = em.createNativeQuery("select ss_gno from cbs_ref_rec_mapping where gno='204' "
                    + "and s_gno='" + npciReturnCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please map reason for code:" + npciReturnCode);
            }
            Vector ele = (Vector) list.get(0);
            return ele.get(0).toString().trim();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
